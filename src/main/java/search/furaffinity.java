package search;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.helper.lcSendMessageHelper;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import org.apache.log4j.Logger;
import search.entities.lcFURAFFINITY;
import util.HelpCommand;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class furaffinity extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, lcFURAFFINITY.INTERFACE {
    String gUrlSearch ="https://www.furaffinity.net/search/";
    String gUrlView ="https://www.furaffinity.net/view/%/";
    String gUrlUser="https://www.furaffinity.net/user/%/";
    String gUrlUserGallery="https://www.furaffinity.net/gallery/%/";
    Logger logger = Logger.getLogger(getClass()); 
    lcGlobalHelper gGlobal;
    String gTitle="FurAffinity-ImageSearcher",gCommand="fa";
    public furaffinity(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Get images from FurAffinity";
        this.aliases = new String[]{gCommand,"furaffinity","faffinity"};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
    }
    public furaffinity(lcGlobalHelper g, SlashCommandEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
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
        String cName = "[runLocal]";
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;CommandEvent gEvent;
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        SlashCommandEvent gSlashCommandEvent;
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
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"furaffinity",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand = true;
                /*if(!isNSFW()){
                    blocked();return;
                }*/
                if(gSlashCommandEvent!=null){
                    messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                    slash();
                }else{
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");
                    }else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);
                        if(items[0].equalsIgnoreCase("help")){
                            help("main");
                        }
                        else if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
                            if(items.length>2){
                                // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                int group=0,type=0,action=0;
                                switch (items[1].toLowerCase()){
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
                                switch (items[2].toLowerCase()){
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
                        if(items.length>=3&&items[0].equalsIgnoreCase("gallery")){
                            doUserGallery(items[1],items[2]);
                        }else
                        if(items.length==2&&items[0].equalsIgnoreCase("gallery")){
                            doUserGallery(items[1],"0");
                        }else
                        if(items.length>=2&&items[0].equalsIgnoreCase("user")){
                            getUserPage(items[1]);
                        }else
                        if(items.length>=2&&items[0].equalsIgnoreCase("view")){
                            getViewPage_v2(items[1]);
                        }else
                        if(gEvent.getArgs().replaceAll("\\s+","").toLowerCase().contains("https:")||gEvent.getArgs().replaceAll("\\s+","").toLowerCase().contains("http:")){
                            getViewPage_v2(gEvent.getArgs());
                        }
                        else{
                            doSearch(gEvent.getArgs());
                        }
                        llMessageDelete(gEvent);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(".run ended");
        }
        lcFURAFFINITY furaffinity=new lcFURAFFINITY(gGlobal);
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
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

        JSONObject options;
        private void setOptions(String message) {
            String fName = "[setOptions]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                //options.put(keyRatingAdult,valueOn);options.put(keyRatingMature,valueOn);options.put(keyRatingGeneral,valueOn);
                options.put(keyPage,valueFirstPage);options.put(keyResetPage, valueDefaultResetPage);options.put(keyRange,valueRangeAll);
                options.put(keyOne,valueFirstOne);options.put(keyMode,valueModeExtended);options.put(keyTypeArt,valueOn);
                options.put(keyDoSearch,valueDoSearch); options.put(keyOrderBy,valueOrderByPopularity);options.put(keyOrderDirection,valueDesc);
                if(message.toLowerCase().contains("showrating")){showRating=true;}
                if(message.toLowerCase().contains(valueGeneral)||message.toLowerCase().contains(valueMature)||message.toLowerCase().contains(valueAdult)){
                    if(message.toLowerCase().contains(valueGeneral)){options.put(keyRatingGeneral,valueOn);}
                    if(message.toLowerCase().contains(valueMature)){options.put(keyRatingMature,valueOn);}
                    if(message.toLowerCase().contains(valueAdult)){options.put(keyRatingAdult,valueOn);}
                }else{
                    options.put(keyRatingGeneral,valueOn);
                }
                if(message.toLowerCase().contains(valueAsc)){options.put(keyOrderDirection,valueAsc);}
                if(message.toLowerCase().contains(valueOrderByDate)){options.put(keyOrderBy,valueOrderByDate);}
                if(message.toLowerCase().contains(valueOrderByRelevancy)){options.put(keyOrderBy,valueOrderByRelevancy);}
                if(message.toLowerCase().contains(valueModeAll)){options.put(keyMode,valueModeAll);}
                if(message.toLowerCase().contains(valueModeAny)){options.put(keyMode,valueModeAny);}
                if(message.toLowerCase().contains(valueRangeDay)){options.put(keyRange,valueRangeDay);}
                if(message.toLowerCase().contains(valueRange3Days)||message.toLowerCase().contains("3 days")||message.toLowerCase().contains("days")){options.put(keyRange,valueRange3Days);}
                if(message.toLowerCase().contains(valueRangeWeek)){options.put(keyRange,valueRangeWeek);}
                if(message.toLowerCase().contains(valueRangeMonth)){options.put(keyRange,valueRangeMonth);}
                String convertedMessage=message.toLowerCase().replaceAll(valueMature,"").replaceAll(valueAdult,"").replaceAll(valueGeneral,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(valueAsc,"").replaceAll(valueDesc,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(valueOrderByDate,"").replaceAll(valueOrderByPopularity,"").replaceAll(valueOrderByRelevancy,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(valueModeAll,"").replaceAll(valueModeAny,"").replaceAll(valueModeExtended,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(valueRangeAll,"").replaceAll(valueRange3Days,"").replaceAll(valueRangeDay,"").replaceAll(valueRangeMonth,"").replaceAll(valueRangeWeek,"").replaceAll("days","").replaceAll("3 days","");
                String begin="page(",textShadow;int i,j,l;
                if(convertedMessage.contains(begin)){
                    logger.info(fName+"found=page");
                    i=convertedMessage.indexOf(begin);
                    l=begin.length();
                    j=convertedMessage.indexOf(")");
                    options.put(keyPage,convertedMessage.substring(i+l,j));
                    StringBuffer stringBuffer = new StringBuffer(convertedMessage);
                    stringBuffer.replace( i ,j+1,"");
                    logger.info(fName+"page="+options.getString(keyPage));
                    convertedMessage=stringBuffer.toString();
                    logger.info(fName+"convertedMessage="+convertedMessage);
                    options.remove(keyOne);options.remove(keyResetPage);
                }
                options.put(keyQ,convertedMessage);
                logger.info(fName+"options="+options);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private int getRandomSlot(int i){
            Random rand = new Random();// Generate random integers in range 0 to i
            int j=rand.nextInt(i);
            if(j<=0)j=1;
            return j;
        }


       private JSONObject convertText2JSONItem(String text){
            String fName = "[convertText2JSONItem]"; logger.info(fName);options=new JSONObject();
            try{
                JSONObject jsonObject=new JSONObject();
                text="."+text;
                logger.info(fName+".text="+text);
                int i=-1,j=-1,l=1;
                String textShadow="";
                String begin="id=\"sid-";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\" class=");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemId,textShadow.substring(0,j));
                    }
                }
                begin="src=\"//";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\"");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemPreview,textShadow.substring(0,j));
                    }
                }
                if(text.contains("r-general")){ jsonObject.put(keyItemRating,0);}
                else if(text.contains("r-mature")){ jsonObject.put(keyItemRating,1);}
                else if(text.contains("r-adult")){ jsonObject.put(keyItemRating,2);}
                else { jsonObject.put(keyItemRating,-1);}
                if(text.contains("t-image")){ jsonObject.put(keyItemType,"image");}
                else{jsonObject.put(keyItemType,"invalid");}

                begin="href=\"/view/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf( begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemPage, textShadow.substring(0, j));
                    }
                    begin="/\" title=\"";
                    if( textShadow.contains(begin)&& textShadow.contains("href=\"/user/")){
                        i= textShadow.indexOf(begin);
                        int i2= textShadow.indexOf("href=\"/user/");
                        logger.info(fName+".i<i2: "+i+" < "+i2);
                        if(i<i2){
                            l=begin.length();
                            textShadow= textShadow.substring(i+l);
                            j=textShadow.indexOf("\"");
                            if(i>0&&j>0) {
                                jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                            }
                        }else{
                            logger.warn(fName+".invalid i<i2: "+i+" < "+i2);
                        }
                    }else{
                        logger.warn(fName+".no author found");
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                        }
                    }
                }
                begin="href=\"/user/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemUser, textShadow.substring(0, j));
                    }
                    begin="title=\"";
                    if( textShadow.contains(begin)){
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemUserName, textShadow.substring(0, j));
                        }
                    }
                }
                logger.info(fName+".json ="+jsonObject);
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }

        lcFURAFFINITY.GalleryImages galleryImages=null;
        int selectedIndex=-1;
        private void doSearch(String message) {
            String fName = "[doSearch]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                setOptions(message);
                galleryImages=furaffinity.getGallerySearch(options);
                if(galleryImages.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);
                    return;
                }
                int i=0;
                logger.info(fName+"galleryImages.length="+galleryImages.length());
                if(galleryImages.length()>1){i=getRandomSlot(galleryImages.length());}
                selectedIndex=i;
                displayImage();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        boolean showRating=false;
        private void postImage(){
            String fName="[postImage]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorOrange_webcolor);
                String title="####";
                int index=selectedIndex;
                lcFURAFFINITY.GalleryImages.GalleryImage galleryImage=galleryImages.getImage(index);
                if(!galleryImage.getTitle().isBlank()){
                    title=galleryImage.getTitle();
                }

                String username="####";
                if(!galleryImage.getUserName().isBlank()){
                    username=galleryImage.getUserName();
                }

                int rating=-1;
                if(galleryImage.getRating()>=0){
                    rating=galleryImage.getRating();
                    if(rating==1){
                        if(showRating)embed.addField("Rating","Mature",false);
                        embed.setColor(llColorBlue_Fa);}
                    else if(rating==2){
                        if(showRating)embed.addField("Rating","Adult",false);
                        embed.setColor(llColorRed_Fa);}
                    else{
                        if(showRating)embed.addField("Rating","General",false);
                        embed.setColor(llColorGray_Fa);}
                }
                String image="";
                if(!galleryImage.getPreview().isBlank()){
                    logger.info(fName+"keyItemPreview="+galleryImage.getPreview());
                    image=galleryImage.getPreview();
                }
                if(galleryImage.getJson().has(keyItemPage)){
                    String url=gUrlView.replaceAll("%",galleryImage.getJson().getString(keyItemPage));
                    logger.info(fName+"url="+url);
                    Unirest a= new Unirest();
                    HttpResponse<String> jsonResponse =a.get(url)
                            .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                            .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                            .header("cookie", lcFURAFFINITY.Config.getCookie(gGlobal))
                            .header("dnt","1")
                            .header("referer:","https://www.furaffinity.net/browse/")
                            .header("sec-fetch-dest","document")
                            .header("sec-fetch-mode","navigate")
                            .header("sec-fetch-site:","same-origin")
                            .header("sec-fetch-user","?1")
                            .header("upgrade-insecure-requests","1")
                            .asString();
                    logger.info(fName+".status ="+jsonResponse.getStatus());
                    if(jsonResponse.getStatus()<=200&&jsonResponse.getStatus()<300){
                        String body=jsonResponse.getBody();
                        //logger.info(fName+".body ="+body);
                        String textShadow="";
                        int itemsI=-1,itemsJ=-1,l;
                        String begin="img id=\"submissionImg";
                        if(body.contains(begin)){
                            logger.info(fName+".found:"+ begin);
                            itemsI=body.indexOf(begin);
                            l=begin.length();
                            textShadow=body.substring(itemsI);
                            itemsJ=textShadow.indexOf("\">");
                            begin="src=\"//";
                            if( textShadow.contains(begin)){
                                logger.info(fName+".found:"+ begin);
                                itemsI= textShadow.indexOf(begin);
                                l=begin.length();
                                textShadow= textShadow.substring(itemsI+l);
                                itemsJ=textShadow.indexOf("\"");
                                if(itemsI>0&&itemsJ>0) {
                                    image=textShadow.substring(0, itemsJ);
                                    logger.info(fName+"imageGot="+image);
                                }
                            }
                        }
                    }
                }
                logger.info(fName+"setImage="+image);
                boolean hasImage=false;
                if(rating==0||gTextChannel.isNSFW()){
                    if(galleryImage.getJson().has(keyItemPage)){
                        embed.setTitle(title,gUrlView.replaceAll("%",galleryImage.getJson().getString(keyItemPage)));
                    }else{
                        embed.setTitle(title);
                    }
                    if(!galleryImage.getUserAsString().isBlank()){
                        //embed.addField("Author","["+username+"]("+gUrlUser.replaceAll("%",galleryImage.getUserAsString())+")",false);
                        lcFURAFFINITY.USER user=galleryImage.getUser();
                        if(user!=null&&user.getImage()!=null&&!user.getImage().isBlank()){
                            embed.setAuthor(username,gUrlUser.replaceAll("%",galleryImage.getUserAsString()),user.getImage());
                        }else{
                            embed.setAuthor(username,gUrlUser.replaceAll("%",galleryImage.getUserAsString()));
                        }
                    }else{
                        //embed.addField("Author",username,false);
                        embed.setAuthor(username);
                    }

                    embed.setImage("http://"+image);
                    hasImage=true;
                }else{
                    logger.warn(fName+"channel not nsfw");
                    embed.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                }
                Message message=llSendMessageResponse(gTextChannel,embed);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void displayImage(){
            String fName="[displayImage]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorOrange_webcolor);
                String title="####";
                int index=selectedIndex;
                lcFURAFFINITY.GalleryImages.GalleryImage galleryImage=galleryImages.getImage(index);
                if(!galleryImage.getTitle().isBlank()){
                    title=galleryImage.getTitle();
                }

                String username="####";
                if(!galleryImage.getUserName().isBlank()){
                    username=galleryImage.getUserName();
                }

                int rating=-1;
                if(galleryImage.getRating()>=0){
                    rating=galleryImage.getRating();
                    if(rating==1){
                        if(showRating)embed.addField("Rating","Mature",false);
                        embed.setColor(llColorBlue_Fa);}
                    else if(rating==2){
                        if(showRating)embed.addField("Rating","Adult",false);
                        embed.setColor(llColorRed_Fa);}
                    else{
                        if(showRating)embed.addField("Rating","General",false);
                        embed.setColor(llColorGray_Fa);}
                }
                String image="";
                if(!galleryImage.getPreview().isBlank()){
                    logger.info(fName+"keyItemPreview="+galleryImage.getPreview());
                    image=galleryImage.getPreview();
                }
                if(galleryImage.getJson().has(keyItemPage)){
                    String url=gUrlView.replaceAll("%",galleryImage.getJson().getString(keyItemPage));
                    logger.info(fName+"url="+url);
                    Unirest a= new Unirest();
                    HttpResponse<String> jsonResponse =a.get(url)
                            .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                            .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                            .header("cookie", lcFURAFFINITY.Config.getCookie(gGlobal))
                            .header("dnt","1")
                            .header("referer:","https://www.furaffinity.net/browse/")
                            .header("sec-fetch-dest","document")
                            .header("sec-fetch-mode","navigate")
                            .header("sec-fetch-site:","same-origin")
                            .header("sec-fetch-user","?1")
                            .header("upgrade-insecure-requests","1")
                            .asString();
                    logger.info(fName+".status ="+jsonResponse.getStatus());
                    if(jsonResponse.getStatus()<=200&&jsonResponse.getStatus()<300){
                        String body=jsonResponse.getBody();
                        //logger.info(fName+".body ="+body);
                        String textShadow="";
                        int itemsI=-1,itemsJ=-1,l;
                        String begin="img id=\"submissionImg";
                        if(body.contains(begin)){
                            logger.info(fName+".found:"+ begin);
                            itemsI=body.indexOf(begin);
                            l=begin.length();
                            textShadow=body.substring(itemsI);
                            itemsJ=textShadow.indexOf("\">");
                            begin="src=\"//";
                            if( textShadow.contains(begin)){
                                logger.info(fName+".found:"+ begin);
                                itemsI= textShadow.indexOf(begin);
                                l=begin.length();
                                textShadow= textShadow.substring(itemsI+l);
                                itemsJ=textShadow.indexOf("\"");
                                if(itemsI>0&&itemsJ>0) {
                                    image=textShadow.substring(0, itemsJ);
                                    logger.info(fName+"imageGot="+image);
                                }
                            }
                        }
                    }
                }
                logger.info(fName+"setImage="+image);
                boolean hasImage=false;
                if(rating==0||gTextChannel.isNSFW()){
                    if(galleryImage.getJson().has(keyItemPage)){
                        embed.setTitle(title,gUrlView.replaceAll("%",galleryImage.getJson().getString(keyItemPage)));
                    }else{
                        embed.setTitle(title);
                    }
                    if(!galleryImage.getUserAsString().isBlank()){
                        //embed.addField("Author","["+username+"]("+gUrlUser.replaceAll("%",galleryImage.getUserAsString())+")",false);
                        lcFURAFFINITY.USER user=galleryImage.getUser();
                        if(user!=null&&user.getImage()!=null&&!user.getImage().isBlank()){
                            embed.setAuthor(username,gUrlUser.replaceAll("%",galleryImage.getUserAsString()),user.getImage());
                        }else{
                            embed.setAuthor(username,gUrlUser.replaceAll("%",galleryImage.getUserAsString()));
                        }
                    }else{
                        //embed.addField("Author",username,false);
                        embed.setAuthor(username);
                    }

                    embed.setImage("http://"+image);
                    hasImage=true;
                }else{
                    logger.warn(fName+"channel not nsfw");
                    embed.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                }
                //embed.addField("Important","Report it if it violates rules 2 our staff!",false);
                Message message=null;
                if(gSlashCommandEvent==null){
                    message=llSendMessageResponse(gTextChannel,embed);
                    if(index!=0){lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton));}
                    if(index>0){lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton));}
                    if(hasImage)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton));
                    if(index<galleryImages.length()-1){lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton));}
                    if(index!=galleryImages.length()-1){lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton));}
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMag));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }else{
                    messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                    try {
                        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                        lcMessageBuildComponent componentNavigator=messageComponentManager.messageBuildComponents.getComponent(0);
                        lcMessageBuildComponent.Button buttonNavFirst=componentNavigator.getButtonAt4(0);
                        lcMessageBuildComponent.Button buttonNavBack=componentNavigator.getButtonAt4(1);
                        lcMessageBuildComponent.Button buttonNavNext=componentNavigator.getButtonAt4(2);
                        lcMessageBuildComponent.Button buttonNavLast=componentNavigator.getButtonAt4(3);
                        lcMessageBuildComponent componentOption=messageComponentManager.messageBuildComponents.getComponent(1);
                        lcMessageBuildComponent.Button buttonPost=componentOption.getButtonAt4(0);
                        if(index<=1)buttonNavFirst.setDisable();
                        if(index==0)buttonNavBack.setDisable();
                        if(!hasImage)buttonPost.setDisable();
                        if(index>=galleryImages.length()-1)buttonNavNext.setDisable();
                        if(index>=galleryImages.length()-2)buttonNavLast.setDisable();
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
                }
                displayImageListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void displayImageListener(Message message){
            String fName="[displayImageListener]";
            logger.info(fName);
            try{
                gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null){
                                gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                            }
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                switch (id){
                                    case lsUnicodeEmotes.aliasReverseButton:
                                        if(selectedIndex>0){
                                            selectedIndex--;
                                            if(gCurrentInteractionHook!=null) {
                                                messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Going back",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                                gCurrentInteractionHook=gComponentInteractionHook;
                                            }
                                            displayImage();
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasPlayButton:
                                        if((selectedIndex+1)<galleryImages.length()) {
                                            selectedIndex++;
                                            if(gCurrentInteractionHook!=null) {
                                                messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Going next",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                                gCurrentInteractionHook=gComponentInteractionHook;
                                            }
                                            displayImage();
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasLastTrackButton:
                                        selectedIndex=0;
                                        if(gCurrentInteractionHook!=null) {
                                            messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Going to first",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        displayImage();
                                    break;
                                    case lsUnicodeEmotes.aliasNextTrackButton:
                                        selectedIndex=galleryImages.length()-1;
                                        if(gCurrentInteractionHook!=null) {
                                            messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Going to last",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        displayImage();
                                        break;
                                    case lsUnicodeEmotes.aliasMag:
                                        if(gCurrentInteractionHook!=null) {
                                            messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"More from artist",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        doUserGallery(galleryImages.getImage(selectedIndex).getUserAsString(),"1");
                                        break;
                                    case lsUnicodeEmotes.aliasDownwardsButton:
                                        if(gCurrentInteractionHook!=null) {
                                            messageHelper.clear().clearActionRows().setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Postsing to everyone",llColorPurple2).build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        postImage();
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },  5, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
                if(message.isFromGuild()){
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    logger.info(fName+"nameCode="+nameCode);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if(selectedIndex>0){
                                            selectedIndex--;
                                            displayImage();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageClearReactions(e.getChannel(),e.getMessageId());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((selectedIndex+1)<galleryImages.length()) {
                                            selectedIndex++;
                                            displayImage();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedIndex=0;
                                        displayImage();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedIndex=galleryImages.length()-1;
                                        displayImage();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMag))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        doUserGallery(galleryImages.getImage(selectedIndex).getUserAsString(),"1");
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        getUserPage(galleryImages.getImage(selectedIndex).getUserAsString());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                        logger.info(fName+"do=delete");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                    }else{
                                        logger.info(fName+"do=invalid");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                llMessageDelete(message);
                            });
                }else{
                    gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    logger.info(fName+"nameCode="+nameCode);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if(selectedIndex>0){
                                            selectedIndex--;
                                            displayImage();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        postImage();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((selectedIndex+1)<galleryImages.length()) {
                                            selectedIndex++;
                                            displayImage();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedIndex=0;
                                        displayImage();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedIndex=galleryImages.length()-1;
                                        displayImage();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMag))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        doUserGallery(galleryImages.getImage(selectedIndex).getUserAsString(),"1");
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        getUserPage(galleryImages.getImage(selectedIndex).getUserAsString());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                        logger.info(fName+"do=delete");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                    }else{
                                        logger.info(fName+"do=invalid");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                llMessageDelete(message);
                            });
                }

                 logger.info(fName+"wait created");
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

        private void getViewPage_v2(String address) {
            String fName = "[getViewPage]";
            try {
                logger.info(fName+".address ="+address);
                //https://www.furaffinity.net/view/%id/

                String viewId=furaffinity.getViewIDbyAddress(address);
                if(viewId==null||viewId.isBlank()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid address", llColorRed_Chili);
                    return;
                }
                String url= lcFURAFFINITY.INTERFACE.gUrlFapi.replaceAll("%",viewId);
                logger.info(fName+".url ="+url);
                HttpResponse<JsonNode>jsonResponse=furaffinity.reqGetSubmissionView(url);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                String image="",rating="",title="",author="",authorAvatar="";
                if(body.has(keyAuthor)&&!body.isNull(keyAuthor))author=body.getString(keyAuthor);
                if(body.has(keyImageUrl)&&!body.isNull(keyImageUrl))image=body.getString(keyImageUrl);
                if(body.has(keyAvatar)&&!body.isNull(keyAvatar))authorAvatar=body.getString(keyAvatar);
                if(body.has(keyTitle)&&!body.isNull(keyTitle))title=body.getString(keyTitle);
                if(body.has(keyRating)&&!body.isNull(keyRating))rating=body.getString(keyRating);
                if(!image.isBlank()){
                    logger.info(fName+".image ="+image);
                    if(!rating.isBlank()){
                        logger.info(fName+".rating ="+rating);
                        if(rating.equalsIgnoreCase("mature")) embedBuilder.addField("Rating","Mature",false);
                        else if(rating.equalsIgnoreCase("adult")) embedBuilder.addField("Rating","Adult",false);
                        else if(rating.equalsIgnoreCase("general"))embedBuilder.addField("Rating","General",false);
                    }
                    if(rating.equalsIgnoreCase("general")||gTextChannel.isNSFW()){
                        if(!title.isBlank()){logger.info(fName+".title ="+title);embedBuilder.setTitle(title,gUrlView.replaceAll("%",viewId));}
                        if(!author.isBlank()){
                            logger.info(fName+".author ="+author);
                            if (authorAvatar.isBlank()) {
                                embedBuilder.setAuthor(author, gUrlUser.replaceAll("%", author.toLowerCase()), gLogo);
                            } else {
                                embedBuilder.setAuthor(author, gUrlUser.replaceAll("%", author.toLowerCase()), authorAvatar);
                            }
                        }
                        embedBuilder.setImage(image); embedBuilder.setColor(llColorPurple2);
                    }else{
                        logger.warn(fName+"channel not nsfw"); embedBuilder.setColor(llColorRed_Imperial);
                        embedBuilder.setDescription("**Attention** The NSFW image can only be displayed on NSFW channels!");
                    }
                }else{
                    embedBuilder.setDescription("No image found in submission!");embedBuilder.setColor(llColorRed_Cinnabar);
                }
                //embedBuilder.addField("Important","Report it if it violates rules 2 our staff!",false);
                logger.info(fName+".post message");
                Message message=llSendMessageResponse(gTextChannel,embedBuilder);

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }

        private void doUserGallery(String message,String page) {
            String fName = "[doUserGallery]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                logger.info(fName+"page="+page);
                if(Integer.parseInt(page)>1){
                    galleryImages=furaffinity.getGalleryUser(message,Integer.parseInt(page));
                }else{
                    galleryImages=furaffinity.getGalleryUser(message,0);
                }
                if(galleryImages.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+galleryImages.length());
                if(galleryImages.length()>1){i=getRandomSlot(galleryImages.length());}
                selectedIndex=i;
                displayImage();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }


        private void getUserPage(String name) {
            String fName = "[getUserPage]";
            try {
                logger.info(fName+".name ="+name);
                String url=gUrlUser.replaceAll("%",name);
                logger.info(fName+".url ="+url);
                lcFURAFFINITY.USER user=furaffinity.getUserProfile(name);
                if(user.getStatus()>299){
                    logger.error(fName+".invalid status"); return ;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.addField("User",name,false);
                if(user.getImage()!=null&&!user.getImage().isBlank()) embedBuilder.setThumbnail(user.getImage());
                //if(user.getAccountStatus()!=null&&!user.getAccountStatus().isBlank())embedBuilder.addField("Account status",user.getAccountStatus(),false);
                String links="[Profile](https://www.furaffinity.net/user/%/) [Gallery](https://www.furaffinity.net/gallery/%/) [Scraps](https://www.furaffinity.net/scraps/%/) [Favorites](https://www.furaffinity.net/favorites/%/) [Journals](https://www.furaffinity.net/journals/%/)";
                embedBuilder.addField("Links",links.replaceAll("%",name),false);
                embedBuilder.setColor(llColorBlue3);
                logger.info(fName+".post message");
                llSendMessage(gTextChannel,embedBuilder);

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);return ;
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
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle);
            embedBuilder.addField("Search Submissions" ,"`"+llPrefixStr+"fa [tags] <options>` Options are optional search parameter",false);
            embedBuilder.addField("Search Options" ,"\nRange:all, day, days (3 days), week, monthOrder By: relevancy, date, popularity\nOrder Direction: asc, desc\nMode: extended, all, any",false);
            embedBuilder.addField("View Submission" ,"`"+llPrefixStr+"fa view <id>` to display the submission image.",false);
            embedBuilder.addField("User Gallery" ,"`"+llPrefixStr+"fa gallery <username> <optional page nr>` to display user gallery. You can change page by tying a number after the name.",false);
            embedBuilder.addField("User Profile" ,"`"+llPrefixStr+"fa user <username>` to display user simple profile",false);
            embedBuilder.addField("NSFW content","They can only be displayed in NSFW channels.",false);
            embedBuilder.addField("In case of displaying images against rules","Please report to a staff to have it deleted.",false);

            embedBuilder.setColor(llColorPurple2);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+llPrefixStr+gCommand+" guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }

        lcSendMessageHelper messageHelper=new lcSendMessageHelper();
        public InteractionHook gCurrentInteractionHook,gComponentInteractionHook;//slash and button interaction hook
        public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager(); //class to help managing components for message, loads the components from json file
        String gCommandFileMainPath ="resources/json/search/imagegallerynavigator.json";
        public void slash() {
            String fName="[slash]";
            logger.info(".start");
            try{
                gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
                String subcommand=gSlashCommandEvent.getSubcommandName(), subcommandGroup=gSlashCommandEvent.getSubcommandGroup();
                messageHelper.setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook);
                boolean optionImageProv=false;String optionImageValue="";
                boolean optionAuthorProv=false;String optionAuthorValue="";
                boolean optionGeneralProv=false,optionGeneralValue=false;
                boolean optionMatureProv=false,optionMatureValue=false;
                boolean optionAdultProv=false,optionAdultValue=false;
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case "image":
                            optionImageProv=true;optionImageValue=option.getAsString();
                            break;
                        case "author":
                            optionAuthorProv=true;optionAuthorValue=option.getAsString();
                            break;
                        case "general":
                            optionGeneralProv=true;optionGeneralValue=option.getAsBoolean();
                            break;
                        case "mature":
                            optionMatureProv=true;optionMatureValue=option.getAsBoolean();
                            break;
                        case "adult":
                            optionAdultProv=true;optionAdultValue=option.getAsBoolean();
                            break;
                    }
                }
                if(subcommandGroup==null)subcommandGroup="";
                if(subcommand==null)subcommand="";
                logger.info(fName+"subcommandGroup="+subcommandGroup+", subcommand="+subcommand);
                if(optionImageProv&&optionAuthorProv){
                    messageHelper.setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Can only have image or author, not both!", llColorRed).build()).send();
                }
                else if(optionImageProv){
                    options=new JSONObject();
                    logger.info(fName + "optionImageValue=" + optionImageValue);
                    options.put(keyQ,optionImageValue);
                    //options.put(keyRatingAdult,valueOn);options.put(keyRatingMature,valueOn);options.put(keyRatingGeneral,valueOn);
                    options.put(keyPage, valueFirstPage);
                    options.put(keyResetPage, valueDefaultResetPage);
                    options.put(keyRange, valueRangeAll);
                    options.put(keyOne, valueFirstOne);
                    options.put(keyMode, valueModeExtended);
                    options.put(keyTypeArt, valueOn);
                    options.put(keyDoSearch, valueDoSearch);
                    options.put(keyOrderBy, valueOrderByPopularity);
                    options.put(keyOrderDirection, valueDesc);
                    if(optionGeneralProv||optionMatureProv||optionAdultProv){
                        if (optionGeneralProv&&optionGeneralValue) {
                            options.put(keyRatingGeneral, valueOn);
                        }
                        if (optionMatureProv&&optionMatureValue) {
                            options.put(keyRatingMature, valueOn);
                        }
                        if (optionAdultProv&&optionAdultValue) {
                            options.put(keyRatingAdult, valueOn);
                        }
                    }else{
                        if(gTextChannel.isNSFW()){
                            options.put(keyRatingMature, valueOn);options.put(keyRatingAdult, valueOn);
                        }else{
                            options.put(keyRatingGeneral, valueOn);
                        }
                    }
                    galleryImages=furaffinity.getGallerySearch(options);
                    if(galleryImages.isEmpty()){
                        messageHelper.setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Nothing found!", llColorRed).build()).send();
                        return;
                    }
                    int i=0;
                    logger.info(fName+"galleryImages.length="+galleryImages.length());
                    if(galleryImages.length()>1){i=getRandomSlot(galleryImages.length());}
                    selectedIndex=i;
                    displayImage();
                }else if(optionAuthorProv){
                    messageHelper.setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Not implemented!", llColorRed).build()).send();
                }else{
                    messageHelper.setEmbed(lsMessageHelper.lsGenerateEmbed(gTitle,"Invalid option!", llColorRed).build()).send();
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
    }










    static public JSONObject getImageJSON(lcGlobalHelper global,String address) {
        String fName = "[getImageJSON]"; Logger logger = Logger.getLogger(furaffinity.class);
        try {
            lcFURAFFINITY furaffinity=new lcFURAFFINITY(global);
            return  furaffinity.getViewJson(address);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }


}
