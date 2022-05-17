package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityChannel extends Command implements  llGlobalHelper, llMemberHelper, llCommonKeys {
    String cName="[utilityChannel]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    public utilityChannel(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "Channel-Utility";
        this.help = "Utility commands for channels.";
        this.aliases = new String[]{"channel"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;private Message gMessage;
        String gTitle="Channel Utility";
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(fName);
            boolean isInvalidCommand = true;
            try {
                prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    String []items = gCommandEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")){
                        help( "main");isInvalidCommand=false;
                    }else
                    if(isCategoryChannelMentioned(items[0])){
                        if(items.length>=2&&items[0].equalsIgnoreCase("setname")){
                            setName();isInvalidCommand=false;
                        }else{
                            infoCategoryChannel();isInvalidCommand=false;
                        }
                    }else
                    if(isTextChannelMentioned(items[0])){
                        if(items.length>=2){
                            switch (items[1].toLowerCase()){
                                case "setname":
                                    setName();isInvalidCommand=false;
                                    break;
                                case "settopic":
                                    setTopic();isInvalidCommand=false;
                                    break;
                                case "setnsfw":
                                    if(items.length>=3){
                                        switch (items[2].toLowerCase()){
                                            case "yes":
                                            case "true":
                                            case "1":
                                                setNSFW(true);isInvalidCommand=false;
                                                break;
                                            case "no":
                                            case "false":
                                            case "0":
                                                setNSFW(false);isInvalidCommand=false;
                                                break;
                                        }
                                    }
                                    break;
                            }
                        }else{
                            infoTextChannel();isInvalidCommand=false;
                        }
                    }else
                    if(isVoiceChannelMentioned(items[0])){
                        if(items.length>=2){
                            if(items[1].equalsIgnoreCase("setname")){
                                setName();isInvalidCommand=false;
                            }else
                            if(items.length>=3&&lsUsefullFunctions.isStringJustInteger(items[2])){
                                switch (items[1].toLowerCase()){
                                    case "setbit":
                                    case "setbitrate":
                                        setBitrate(lsUsefullFunctions.getStringJustInteger(items[2]));isInvalidCommand=false;
                                        break;
                                    case "setuserlimit":
                                    case "setlimit":
                                        setUserLimit(lsUsefullFunctions.getStringJustInteger(items[2]));isInvalidCommand=false;
                                        break;
                                }
                            }
                        }else{
                            infoVoiceChannel();isInvalidCommand=false;
                        }
                    }else{
                        if(gCommandEvent.getMessage().getMentionedChannels().isEmpty()){
                            mentionedTextChannel=gTextChannel;
                            switch (items[0].toLowerCase()){
                                case "json":
                                    if(items.length>=2){
                                        switch (items[1].toLowerCase()){
                                            case "simple":
                                                sendChannelsJsonFile();isInvalidCommand=true;
                                                break;
                                            case "multi":
                                                sendChannelsJsonZipFile();isInvalidCommand=true;
                                                break;
                                        }
                                    }else{
                                        sendChannelsJsonFile();isInvalidCommand=true;
                                    }
                                case "setname":
                                    setName();isInvalidCommand=false;
                                    break;
                                case "settopic":
                                    setTopic();isInvalidCommand=false;
                                    break;
                                case "setnsfw":
                                    if(items.length>=2){
                                        switch (items[1].toLowerCase()){
                                            case "yes":
                                            case "true":
                                            case "1":
                                                setNSFW(true);isInvalidCommand=false;
                                                break;
                                            case "no":
                                            case "false":
                                            case "0":
                                                setNSFW(false);isInvalidCommand=false;
                                                break;
                                        }
                                    }
                                    break;
                            }
                        }else{
                            if(items[0].equalsIgnoreCase("setnsfw")){
                                if(items.length>=2){
                                    switch (items[1].toLowerCase()){
                                        case "yes":
                                        case "true":
                                        case "1":
                                            setMultiNSFW(true);isInvalidCommand=false;
                                            break;
                                        case "no":
                                        case "false":
                                        case "0":
                                            setMultiNSFW(false);isInvalidCommand=false;
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
                logger.info(fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(),llColors.llColorRed);
            }
        }
        String prefix2="";
        private void help(String command){
        String fName="help";
        logger.info(fName + ".command:"+command);
        String quickSummonWithSpace=prefix2+"channel ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle); embed.setColor(llColors.llColorBlue1);
        embed.addField("Info","`"+quickSummonWithSpace+" [mention, name or id text channel]`  get basic info of channel",false);

        if(llMemberHasPermission_MANAGECHANNEL(gMember)){
            embed.addField("Text Channel","`"+quickSummonWithSpace+" [mention, name or id text channel] setnsfw <1|0>`  sets nsfw flag of channel."
                    +"\n`"+quickSummonWithSpace+" [mention, name or id text channel] setname ` sets channel name."
                    +"\n`"+quickSummonWithSpace+" [mention, name or id text channel] settopic ` sets channel topic."
                    +"\n`"+quickSummonWithSpace+" setnsfw <1|0> [mentioned channels]` sets multiple channels nsfw flag.",false);
            embed.addField("Voice Channel","`"+quickSummonWithSpace+" [name or id voice channel] setbitrate [number]`  sets channel bit rate."
                    +"\n`"+quickSummonWithSpace+" [mention, name or id text channel] setlimit ` sets channel user limit."
                    +"\n`"+quickSummonWithSpace+" [mention, name or id text channel] setname ` sets channel name.",false);
            embed.addField("Send JSON","`"+quickSummonWithSpace+" json [simple|multi]` sends a json of the server's channels.",false);
        }
        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
        lsMessageHelper.lsSendMessage(gUser,embed);
    }
        TextChannel mentionedTextChannel=null;
        VoiceChannel mentionedVoiceChannel=null;
        net.dv8tion.jda.api.entities.Category mentionedCategoryChannel=null;
        GuildChannel mentionedGuildChannel=null;
        private boolean isTextChannelMentioned(String nameOrid){
            String fName="isTextChannelMentioned";
            logger.info(fName);
            try {
                List<TextChannel>textChannels= gCommandEvent.getMessage().getMentionedChannels();
                /*if(!textChannels.isEmpty()){
                    mentionedTextChannel=textChannels.get(0);mentionedGuildChannel=gGuild.getGuildChannelById(mentionedTextChannel.getId());logger.info(fName+"=true");return true;
                }*/
                logger.info(fName+".nameOrid="+nameOrid);
                TextChannel textChannel=lsMessageHelper.lsGetFirstTextChannelByName(gGuild,nameOrid);
                if(textChannel!=null){
                    mentionedTextChannel=textChannel;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedTextChannel.getId());logger.info(fName+"=true");return true;
                }
                textChannel=lsMessageHelper.lsGetTextChannelById(gGuild,nameOrid);
                if(textChannel!=null){
                    mentionedTextChannel=textChannel;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedTextChannel.getId());logger.info(fName+"=true");return true;
                }
                logger.info(fName+"=false");return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName+"=false");return false;
            }
        }
        private boolean isVoiceChannelMentioned(String nameOrid){
            String fName="isVoiceChannelMentioned";
            logger.info(fName);
            try {
                logger.info(fName+".nameOrid="+nameOrid);
                VoiceChannel voiceChannel=lsChannelHelper.lsGetVoiceChannelById(gGuild,nameOrid);
                if(voiceChannel!=null){
                    mentionedVoiceChannel=voiceChannel;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedVoiceChannel.getId()); logger.info(fName+"=true");return true;
                }
                voiceChannel=lsChannelHelper.lsGetFirstVoiceChannelByName(gGuild,nameOrid);
                if(voiceChannel!=null){
                    mentionedVoiceChannel=voiceChannel;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedVoiceChannel.getId());logger.info(fName+"=true");return true;
                }
                logger.info(fName+"=false"); return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName+"=false");return false;
            }
        }
        private boolean isCategoryChannelMentioned(String nameOrid){
            String fName="isCategoryChannelMentioned";
            logger.info(fName);
            try {
                logger.info(fName+".nameOrid="+nameOrid);
                net.dv8tion.jda.api.entities.Category category=lsChannelHelper.lsGetCategoryById(gGuild,nameOrid);
                if(category!=null){
                    mentionedCategoryChannel=category;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedCategoryChannel.getId()); logger.info(fName+"=true");return true;
                }
                category=lsChannelHelper.lsGetFirstCategoryChannelByName(gGuild,nameOrid);
                if(category!=null){
                    mentionedCategoryChannel=category;mentionedGuildChannel=gGuild.getGuildChannelById(mentionedCategoryChannel.getId());logger.info(fName+"=true");return true;
                }
                logger.info(fName+"=false"); return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName+"=false");return false;
            }
        }
        private void infoTextChannel(){
            String fName="infoTextChannel";
            logger.info(fName);
            try {
               if(mentionedTextChannel==null){
                   return;
               }
               String id=mentionedTextChannel.getId();
               String name=mentionedTextChannel.getName();
               String topic=mentionedTextChannel.getTopic();
               net.dv8tion.jda.api.entities.Category category=mentionedTextChannel.getParent();
               int slowmode=mentionedTextChannel.getSlowmode();
               boolean nsfw=mentionedTextChannel.isNSFW();
               boolean cantalk=mentionedTextChannel.canTalk(gMember);
               OffsetDateTime timecreated=mentionedTextChannel.getTimeCreated();
               List<PermissionOverride> permissionOverrides = null;
               try {
                    permissionOverrides = mentionedTextChannel.getPermissionOverrides();
               }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
               }
               EmbedBuilder embedBuilder=new EmbedBuilder();
               embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorPurple2);
               embedBuilder.addField("Id",id,true);
               embedBuilder.addField("name",name,true);
                if(category!=null)embedBuilder.addField("Parent",category.getName()+"("+category.getId()+")",true);
                if(slowmode>0){embedBuilder.addField("Slowmode", String.valueOf(slowmode),true);}else{
                    embedBuilder.addField("Slowmode", "N/A",true);
                }
                if(nsfw){embedBuilder.addField("NSFW", "Yes",true);}else{
                    embedBuilder.addField("NSFW", "No",true);
                }
                if(cantalk){embedBuilder.addField("Can Talk", "Yes, "+gMember.getAsMention()+" can talk.",true);}else{
                    embedBuilder.addField("Can Talk", "No, "+gMember.getAsMention()+" is not allowed to talk.",true);
                }
                embedBuilder.addField("Created", lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timecreated),true);
                if(permissionOverrides!=null&&!permissionOverrides.isEmpty())embedBuilder.addField("Overrides", String.valueOf(permissionOverrides.size()),true);
                if(topic!=null&&!topic.isBlank())embedBuilder.addField("Topic",topic,false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void infoVoiceChannel(){
            String fName="infoVoiceChannel";
            logger.info(fName);
            try {
                if(mentionedVoiceChannel==null){
                    return;
                }
                String id=mentionedVoiceChannel.getId();
                String name=mentionedVoiceChannel.getName();
                net.dv8tion.jda.api.entities.Category category=mentionedVoiceChannel.getParent();
                OffsetDateTime timecreated=mentionedVoiceChannel.getTimeCreated();
                List<PermissionOverride> permissionOverrides = null;
                int bitrate=mentionedVoiceChannel.getBitrate();
                int userlimit=mentionedVoiceChannel.getUserLimit();
                try {
                    permissionOverrides = mentionedVoiceChannel.getPermissionOverrides();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorPurple2);
                embedBuilder.addField("Id",id,true);
                embedBuilder.addField("name",name,true);
                if(category!=null)embedBuilder.addField("Parent",category.getName()+"("+category.getId()+")",true);
                if(userlimit>0){embedBuilder.addField("User Limit", String.valueOf(userlimit),true);}else{
                    embedBuilder.addField("User Limit", "N/A",true);
                }
                if(bitrate>0){embedBuilder.addField("Bitrate", String.valueOf(bitrate),true);}else{
                    embedBuilder.addField("Bitrate", "N/A",true);
                }
                embedBuilder.addField("Created", lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timecreated),true);
                if(permissionOverrides!=null&&!permissionOverrides.isEmpty())embedBuilder.addField("Overrides", String.valueOf(permissionOverrides.size()),true);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void infoCategoryChannel(){
            String fName="infoVoiceChannel";
            logger.info(fName);
            try {
                if(mentionedCategoryChannel==null){
                    return;
                }
                String id=mentionedCategoryChannel.getId();
                String name=mentionedCategoryChannel.getName();
                List<TextChannel>textChannels=mentionedCategoryChannel.getTextChannels();
                List<VoiceChannel>voiceChannels=mentionedCategoryChannel.getVoiceChannels();
                OffsetDateTime timecreated=mentionedVoiceChannel.getTimeCreated();
                List<PermissionOverride> permissionOverrides = null;
                try {
                    permissionOverrides = mentionedCategoryChannel.getPermissionOverrides();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorPurple2);
                embedBuilder.addField("Id",id,true);
                embedBuilder.addField("name",name,true);
                if(!textChannels.isEmpty()){embedBuilder.addField("Text Channels", String.valueOf(textChannels.size()),true);}else{
                    embedBuilder.addField("Text Channels", "N/A",true);
                }
                if(!voiceChannels.isEmpty()){embedBuilder.addField("Voice Channels", String.valueOf(voiceChannels.size()),true);}else{
                    embedBuilder.addField("Voice Channels", "N/A",true);
                }
                embedBuilder.addField("Created", lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timecreated),true);
                if(permissionOverrides!=null&&!permissionOverrides.isEmpty())embedBuilder.addField("Overrides", String.valueOf(permissionOverrides.size()),true);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void setName(){
            String fName="[setName]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                Message message = null;
                if(mentionedCategoryChannel!=null){
                    message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the new name for the category "+mentionedCategoryChannel.getName().toUpperCase()+" or type `!cancel`.",llColors.llColorBlue1);
                }else
                if(mentionedTextChannel!=null){
                    message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the new name for the text channel "+mentionedTextChannel.getAsMention()+" or type `!cancel`.",llColors.llColorBlue1);
                }else
                if(mentionedVoiceChannel!=null){
                    message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the new name for the voice channel "+mentionedVoiceChannel.getName()+" or type `!cancel`.",llColors.llColorBlue1);
                }else{
                    return;
                }
                Message finalMessage = message;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            lsMessageHelper.lsMessageDelete(finalMessage);
                            if(content.equalsIgnoreCase("!cancel")){
                               return;
                            }
                            mentionedGuildChannel.getManager().setName(content).complete();
                            if(mentionedCategoryChannel!=null){
                                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+mentionedCategoryChannel.getName()+" name.", llColors.llColorPurple2);
                            }else
                            if(mentionedTextChannel!=null){
                                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+mentionedTextChannel.getAsMention()+" name.", llColors.llColorPurple2);
                            }else
                            if(mentionedVoiceChannel!=null){
                                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+mentionedVoiceChannel.getName()+" name.", llColors.llColorPurple2);
                            }

                        },3, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(finalMessage);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setTopic(){
            String fName="[setTopic]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                Message message = null;
                if(mentionedTextChannel!=null){
                    message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the new topic for the text channel "+mentionedTextChannel.getAsMention()+" or type `!cancel`.",llColors.llColorBlue1);
                }else{
                    return;
                }
                Message finalMessage = message;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            lsMessageHelper.lsMessageDelete(finalMessage);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            mentionedTextChannel.getManager().setTopic(content).complete();
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+mentionedTextChannel.getAsMention()+" topic.", llColors.llColorPurple2);
                        },3, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(finalMessage);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setNSFW(boolean preference){
            String fName="[setNSFW]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }

                if(mentionedTextChannel==null){
                    logger.warn(fName+"no channel");
                    return;
                }
                logger.info(fName+"preference="+preference);
                mentionedTextChannel.getManager().setNSFW(preference).complete();
                if (preference) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Channel "+mentionedTextChannel.getAsMention()+" nsfw flag set to true.",llColors.llColorPurple2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Channel "+mentionedTextChannel.getAsMention()+" nsfw flag set to false.",llColors.llColorPurple2);
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setUserLimit(int preference){
            String fName="[setUserLimit]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }

                if(mentionedVoiceChannel==null){
                    logger.warn(fName+"no channel");
                    return;
                }
                logger.info(fName+"preference="+preference);
                if(preference<0||preference>99){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Invalid user limit: "+preference, llColors.llColorRed);return;
                }
                mentionedVoiceChannel.getManager().setUserLimit(preference).complete();

                if (preference>0) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Channel "+mentionedVoiceChannel.getName()+" user limit field set to "+preference+".",llColors.llColorPurple2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Channel "+mentionedVoiceChannel.getName()+" user limit field turned off.",llColors.llColorPurple2);
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setBitrate(int preference){
            String fName="[setBitrate]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }

                if(mentionedVoiceChannel==null){
                    logger.warn(fName+"no channel");
                    return;
                }
                logger.info(fName+"preference="+preference);
                if(preference<8||preference>384){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Invalid bitrate: "+preference, llColors.llColorRed);return;
                }
                mentionedVoiceChannel.getManager().setBitrate(preference).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Channel "+mentionedVoiceChannel.getName()+" bitrate set to "+preference+".",llColors.llColorPurple2);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }

        private void setMultiNSFW(boolean preference){
            String fName="[setMultiNSFW]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                
                logger.info(fName+"preference="+preference);
                List<TextChannel>success=new ArrayList<>(); List<TextChannel>failed=new ArrayList<>();
                List<TextChannel>textChannels= gCommandEvent.getMessage().getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    try {
                        logger.info(fName+"textChannel:"+textChannel.getId()+"|"+textChannel.getName());
                        textChannel.getManager().setNSFW(preference).complete();
                        success.add(textChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed.add(textChannel);
                    }
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorPurple2);
                embedBuilder.addField("Task:","Set NSFW to "+preference,false);
                if(!success.isEmpty()){
                    StringBuilder tmp= new StringBuilder();
                    for(TextChannel channel:success){
                        if(!tmp.toString().isBlank()){
                            tmp.append(", ").append(channel.getAsMention());
                        }else{
                            tmp = new StringBuilder(channel.getAsMention());
                        }
                    }
                    embedBuilder.addField("Success", tmp.toString(),false);
                }
                if(!failed.isEmpty()){
                    StringBuilder tmp= new StringBuilder();
                    for(TextChannel channel:failed){
                        if(!tmp.toString().isBlank()){
                            tmp.append(", ").append(channel.getAsMention());
                        }else{
                            tmp = new StringBuilder(channel.getAsMention());
                        }
                    }
                    embedBuilder.addField("Failed", tmp.toString(),false);
                }
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void sendChannelsJsonFile(){
            String fName = "[sendChannelsFile]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONArray array=new JSONArray();
                List<GuildChannel>channels=gGuild.getChannels();
                logger.info(fName+"channels.Size="+channels.size());
                for(GuildChannel channel : channels){
                    try {
                        JSONObject jsonChannel= lsJson4Entity.llGetChannelJsonEntry(channel);
                        if(jsonChannel!=null)array.put(jsonChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Channels", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendChannelsJsonZipFile(){
            String fName = "[sendChannelsZipFile]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension=".txt";

                List<GuildChannel>channels=gGuild.getChannels();
                logger.info(fName+"channels.Size="+channels.size());
                for(GuildChannel channel : channels){
                    try {
                        JSONObject jsonChannel= lsJson4Entity.llGetChannelJsonEntry(channel);
                        if(jsonChannel!=null) {
                            String name=entryName.replaceAll("%id", channel.getId()) + entryExtension;
                            zipFile.addEntity(name,jsonChannel);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }

                InputStream targetStream = zipFile.getInputStream();
                String fileName="Channels", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }

    
  //runLocal  
    }
}
