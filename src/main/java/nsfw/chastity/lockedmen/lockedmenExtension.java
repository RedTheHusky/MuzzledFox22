package nsfw.chastity.lockedmen;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.Cookie;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.lcText2Json;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;
import restraints.in.iRdStr;
import restraints.rdAuth;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class lockedmenExtension {
    Logger logger = Logger.getLogger("lockedmenExtension");

    lcGlobalHelper global;


    public CommandEvent gCommandEvent;
    public GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
    public String gRawForward="";public boolean gIsForward=false, gIsOverride =false;
    public Guild gGuild; public User gUser;
    public Member gMember; public TextChannel gTextChannel;public Member gTarget;
    public String gArgs=""; public String[] gItems=null;
    public  Message gMessage;
    public  SlashCommandEvent gSlashCommandEvent;
    public void launch(lcGlobalHelper global,CommandEvent ev){
        String fName="[runLocal]";
        logger.info(".run build");
        try {
            this.global =global;
            gCommandEvent =ev;
            gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter= this.global.waiter;
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gArgs= gCommandEvent.getArgs();
            updateIsAdult();
            gMessage=ev.getMessage();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());

        }
    }
    public void launch(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[runLocal]";
        logger.info(".run build");
        try {
            this.global =global;
            gSlashCommandEvent = ev;
            try {
                gUser = ev.getUser();gMember=ev.getMember();
                logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                gWaiter= this.global.waiter;
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            updateIsAdult();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public EventWaiter gWaiter;
    public String sRTitle="",gCommand="",llPrefixStr="<@bot>";
    public void setTitleStr(String title){
        String fName="[setTitleStr]";
        logger.info(fName+".title="+title);
        sRTitle=title;
        String a=rdAuth.quickAlias;
    }
    public void setCommandStr(String command){
        String fName="[setCommandStr]";
        logger.info(fName+".command="+command);
        gCommand=command;

    }
    @Deprecated
    @DeprecatedSince("12/29/2021")
    public void setPrefixStr(String prefix){
        String fName="[setCommandStr]";
        logger.info(fName+".prefix="+prefix);
        //llPrefixStr=prefix;
    }
    public boolean isAdult=false;
    public boolean updateIsAdult(){
        String fName="[updateIsAdult]";
        logger.info(fName);
        if(gTextChannel.isNSFW()){
            logger.info(fName+"channel is nsfw"); isAdult=true; return true;
        }
        if(lsGuildHelper.lsIsGuildNSFW(global,gGuild)){
            logger.info(fName+"guild is nsfw"); isAdult=true; return true;
        }
        logger.info(fName+"is sfw");
        return false;
    }
    public  void blocked(){
        String fName = "[blocked]";
        EmbedBuilder embedBuilder=lsMessageHelper.lsErrorEmbed(sRTitle,"Require NSFW channel or server.", llColors.llColorRed);
        sendOrUpdatePrivateEmbed(embedBuilder);
        logger.info(fName);
    }
    public  void blockedDM(){
        String fName = "[blocked]";
        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Require NSFW channel or server.", llColors.llColorRed);
        logger.info(fName);
    }




    public lcBasicFeatureControl gBasicFeatureControl=new lcBasicFeatureControl();
    public void loadBasic(String name){
        String fName="[runLocal]";
        gBasicFeatureControl=new lcBasicFeatureControl(gGuild,name, global);
        gBasicFeatureControl.initProfile();
    }
    public void setEnable(boolean enable) {
        String fName = "[setEnable]";
        try {
            logger.info(fName + "enable=" + enable);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            gBasicFeatureControl.setEnable(enable);
            if(enable){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void getChannels(int type, boolean toDM) {
        String fName = "[setChannel]";
        try {
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long> list=gBasicFeatureControl.getAllowedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void getRoles(int type, boolean toDM) {
        String fName = "[getRoles]";
        try {
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed roles list: "+ lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public boolean checkIFChannelsAreNSFW(List<TextChannel>textChannels) {
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
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return false;
        }
    }
    public void setChannel(int type, int action, Message message) {
        String fName = "[setChannel]";
        try {
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update as all channels are required to be NSFW!");
                        return;
                    }
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    if(!checkIFChannelsAreNSFW(textChannels)){
                        logger.warn(fName+"failed as not all are nsfw");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update as all channels are required to be NSFW!");
                        return;
                    }
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void setRole(int type, int action, Message message) {
        String fName = "[setRole]";
        try {
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

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
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void menuGuild(){
        String fName="[menuGuild]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(sRTitle+" Options");
            embed.addField("Enable","Select "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
            embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
            embed.addField("Help","Select "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gBasicFeatureControl.getEnable()){
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            lsMessageHelper.lsMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setEnable(true);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setEnable(false);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                getChannels(1,true);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                getChannels(-1,true);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                getRoles(1,true);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                getRoles(-1,true);
                            }
                            else{
                                menuGuild();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public boolean isCommand2BasicFeatureControl(String []items) {
        String fName = "[isCommand2BasicFeatureControl]";
        try {
            if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
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
                            getChannels(type,false);return true;
                        }else{
                            setChannel(type,action, gCommandEvent.getMessage());
                        }
                    }
                    else if(group==2){
                        if(action==0){
                            getRoles(type,false);return true;
                        }else{
                            setRole(type,action, gCommandEvent.getMessage());
                        }
                    }
                }else{
                    menuGuild();return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return false;
        }
    }

    public boolean checkIFAllowed2UseCommand1_slash() {
        String fName = "[checkIFAllowed2UseCommand1]";
        try {
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return false;
            }
            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                logger.info(fName+"its not allowed by channel");
                return false;
            }
            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                logger.info(fName+"its not allowed by roles");
                return false;
            }
            return  true;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return true;
        }
    }
    public boolean checkIFAllowed2UseCommand2_text() {
        String fName = "[checkIFAllowed2UseCommand2]";
        try {
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                logger.info(fName+"its not allowed by channel");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                logger.info(fName+"its not allowed by roles");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            return  true;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return true;
        }
    }
    public boolean check4TargetinItems() {
        String fName = "[check4TargetinItems]";
        try {
            if((gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")) {
                logger.info(fName + ".detect mention characters");
                List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
                if (mentions.isEmpty()) {
                    logger.warn(fName + ".zero member mentions in message>check itemns[0]");
                    gTarget = lsMemberHelper.lsGetMember(gGuild, gItems[0]);
                } else {
                    logger.info(fName + ".member mentions in message");
                    gTarget = mentions.get(0);
                }
                if (gTarget == null) {
                    logger.warn(fName + ".zero member mentions");
                } else if (gTarget.getIdLong() == gUser.getIdLong()) {
                    logger.warn(fName + ".target cant be the gUser");
                    gItems = lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                    gTarget = null;
                    //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                }
            }
            if(gTarget==null)return false;
            return true;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return false;
        }
    }

    InteractionHook gCurrentInteractionHook=null,gComponentInteractionHook=null,gSlashInteractionHook=null;
    public void deferReplySet(SelectionMenuEvent selectionMenuEvent){
        String fName="[deferReplySet -selectionMenuEvent]";
        try{
            logger.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(selectionMenuEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(ButtonClickEvent buttonClickEvent){
        String fName="[deferReplySet -buttonClickEvent]";
        try{
            logger.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(buttonClickEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(SlashCommandEvent slashCommandEvent){
        String fName="[deferReplySet -slashCommandEvent]";
        try{
            logger.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(slashCommandEvent,true);
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void replywPleaseWait(SelectionMenuEvent selectionMenuEvent){
        String fName="[replywPleaseWait -selectionMenuEvent]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=selectionMenuEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void replywPleaseWait(ButtonClickEvent buttonClickEvent){
        String fName="[replywPleaseWait -buttonClickEvent]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=buttonClickEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void replywPleaseWait(SlashCommandEvent slashCommandEvent){
        String fName="[replywPleaseWait -slashCommandEvent]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=slashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyCheckDm(){
        String fName="[slashReplyPleaseWait]";
        try{
            logger.info(fName);
            if(gSlashCommandEvent==null){logger.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){logger.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strOpeningDMMenu).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyPleaseWait(){
        String fName="[slashReplyPleaseWait]";
        try{
            logger.info(fName);
            if(gSlashCommandEvent==null){logger.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){logger.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow>actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description,Color color){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            return  sendPrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            Message message=null;
            if(interactionHook!=null){
                if(interactionHook.getInteraction().isAcknowledged()){
                    message=lsMessageHelper.lsSendEphemeralEmbed(interactionHook,embedBuilder);
                }else{
                    message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
                }
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            logger.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, List<net.dv8tion.jda.api.interactions.components.ActionRow>actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            Message message=null;
            if(interactionHook!=null){
                if(interactionHook.getInteraction().isAcknowledged()){
                    message=lsMessageHelper.lsSendEphemeralEmbed(interactionHook,embedBuilder,actionRows);
                }else{
                    message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder,actionRows);
                }
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder,actionRows);
            }
            logger.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendPrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow>actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            return  sendOrUpdatePrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            logger.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, List<net.dv8tion.jda.api.interactions.components.ActionRow>actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder,actionRows);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder,actionRows);
            }
            logger.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            logger.info(fName);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(embedBuilder,interactionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            logger.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            logger.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            logger.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message removeAction(Message message){
        String fName="[removeAction]";
        try{
            logger.info(fName);
            message.clearReactions().complete();
            message=message.editMessageComponents().complete();
            return message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String textAdd(String source,String add){
        String fName="[stextAdd]";
        try{
            logger.info(fName);
            if(!source.isBlank())source+="\n"+add;
            return  source;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();

    public class Session{
        Logger logger = Logger.getLogger("lockedmenExtension.session");
        public Session(){}
        private lcText2Json text2JsonConfig=null;
        private  JSONObject jsonSession=new JSONObject();
        public boolean readConfig() {
            String fName="[readFile]";
            logger.info(fName);
            try {
                File file1;
                InputStream fileStream=null;
                try {
                    logger.info(fName+"path="+iLockedmen.gConfigFilePath);
                    file1=new File(iLockedmen.gConfigFilePath);
                    if(file1.exists()){
                        logger.info(fName+".file1 exists");
                        fileStream = new FileInputStream(file1);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                text2JsonConfig=new lcText2Json();
                if(fileStream!=null){
                    if(!text2JsonConfig.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        return false;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2JsonConfig.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2JsonConfig.jsonObject.toString());
                try {
                    String kesSession=iLockedmen.Session.session;
                    if(text2JsonConfig.jsonObject.has(kesSession)&&!text2JsonConfig.jsonObject.isNull(kesSession)){
                        jsonSession=text2JsonConfig.jsonObject.getJSONObject(kesSession);
                        logger.info(fName + ".jsonSession=" + jsonSession.toString());
                        return  true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.warn(fName+".failed to get session info");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getSessionJson() {
            String fName="[getSessionJson]";
            logger.info(fName);
            try {
                return jsonSession;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_ASPSESSIONIDCWBDABSS() {
            String fName="[getCookieString_ASPSESSIONIDCWBDABSS]";
            logger.info(fName);
            try {
               JSONObject jsonObject=getSessionJson();
               if(jsonObject==null)throw  new Exception("json is null");
               String value=jsonObject.getString(iLockedmen.Session.Cookies.ASPSESSIONIDCWBDABSS);
               logger.info(fName + ".value=" + value);
               return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_l5FuserGUID() {
            String fName="[getCookieString_l5FuserGUID]";
            logger.info(fName);
            try {
                JSONObject jsonObject=getSessionJson();
                if(jsonObject==null)throw  new Exception("json is null");
                String value=jsonObject.getString(iLockedmen.Session.Cookies.l5FuserGUID);
                logger.info(fName + ".value=" + value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_lockedMEN() {
            String fName="[getCookieString_lockedMEN]";
            logger.info(fName);
            try {
                JSONObject jsonObject=getSessionJson();
                if(jsonObject==null)throw  new Exception("json is null");
                String value=jsonObject.getString(iLockedmen.Session.Cookies.lockedMEN);
                logger.info(fName + ".value=" + value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_wwf10lVisit() {
            String fName="[getCookieString_wwf10lVisit]";
            logger.info(fName);
            try {
                JSONObject jsonObject=getSessionJson();
                if(jsonObject==null)throw  new Exception("json is null");
                String value=jsonObject.getString(iLockedmen.Session.Cookies.wwf10lVisit);
                logger.info(fName + ".value=" + value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_wwf10sID() {
            String fName="[getCookieString_wwf10sID]";
            logger.info(fName);
            try {
                JSONObject jsonObject=getSessionJson();
                if(jsonObject==null)throw  new Exception("json is null");
                String value=jsonObject.getString(iLockedmen.Session.Cookies.wwf10sID);
                logger.info(fName + ".value=" + value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getCookieString_mutePM() {
            String fName="[getCookieString_mutePM]";
            logger.info(fName);
            try {
                JSONObject jsonObject=getSessionJson();
                if(jsonObject==null)throw  new Exception("json is null");
                String value=jsonObject.getString(iLockedmen.Session.Cookies.mutePM);
                logger.info(fName + ".value=" + value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_ASPSESSIONIDCWBDABSS() {
            String fName="[getCookie_ASPSESSIONIDCWBDABSS]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.ASPSESSIONIDCWBDABSS,getCookieString_ASPSESSIONIDCWBDABSS());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_l5FuserGUID() {
            String fName="[getCookie_l5FuserGUID]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.l5FuserGUID,getCookieString_l5FuserGUID());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_lockedMEN() {
            String fName="[getCookie_lockedMEN]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.lockedMEN,getCookieString_lockedMEN());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_wwf10lVisit() {
            String fName="[getCookie_wwf10lVisit]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.wwf10lVisit,getCookieString_wwf10lVisit());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_wwf10sID() {
            String fName="[getCookie_wwf10sID]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.wwf10sID,getCookieString_wwf10sID());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Cookie getCookie_mutePM() {
            String fName="[getCookie_mutePM]";
            logger.info(fName);
            try {
                Cookie cookie=new Cookie(iLockedmen.Session.Cookies.mutePM,getCookieString_mutePM());
                logger.info(fName + ".name=" + cookie.getName()+", value="+cookie.getValue());
                return  cookie;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<Cookie> getCookies() {
            String fName="[getCookies]";
            logger.info(fName);
            try {
                List<Cookie>list=new ArrayList<>();
                Cookie cookie=null;
                cookie=getCookie_ASPSESSIONIDCWBDABSS();
                if(cookie!=null)list.add(cookie);
                cookie=getCookie_l5FuserGUID();
                if(cookie!=null)list.add(cookie);
                cookie=getCookie_lockedMEN();
                if(cookie!=null)list.add(cookie);
                cookie=getCookie_wwf10lVisit();
                if(cookie!=null)list.add(cookie);
                cookie=getCookie_wwf10sID();
                if(cookie!=null)list.add(cookie);
                cookie=getCookie_mutePM();
                if(cookie!=null)list.add(cookie);
                return  list;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    Session session=new Session();
    cLockedmenHomepage lockedmenHomepage=new cLockedmenHomepage();

    public boolean loadDefaultSettings(){
        String fName="[getSessionJson]";
        logger.info(fName);
        try {
            if(!session.readConfig())throw  new Exception("Failed to read");
            if(lockedmenHomepage.setCookies(session.getCookies())==null)throw  new Exception("Failed to set cookies");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }


}
