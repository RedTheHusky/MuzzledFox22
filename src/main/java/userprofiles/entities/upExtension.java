package userprofiles.entities;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import org.apache.log4j.Logger;
import restraints.in.iRdStr;
import restraints.models.lcBDSMGuildProfiles;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class upExtension {
    Logger loggerExtension = Logger.getLogger("userprofiles.upExtension");
    
    lcGlobalHelper gGlobal;

    protected upExtension(){
       
    }
    public CommandEvent gCommandEvent;public SlashCommandEvent gSlashCommandEvent;
    public GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
    public String gRawForward="";public boolean gIsForward=false, gIsOverride =false;
    public Guild gGuild; public User gUser;
    public Member gMember; public TextChannel gTextChannel;public Member gTarget;
    public String gArgs=""; public String[] gItems=null;

    public  Message gMessage;
    public EventWaiter gWaiter;

    public void launch(lcGlobalHelper global,CommandEvent ev){
        String fName="[runLocal]";
        loggerExtension.info(".run build");
        try {
            loggerExtension.warn("hellow!");
            gGlobal=global;
            gCommandEvent =ev;
            gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            loggerExtension.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            loggerExtension.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gTextChannel = gCommandEvent.getTextChannel();
            loggerExtension.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gArgs= gCommandEvent.getArgs();
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gMessage=ev.getMessage();
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,null,e.toString());

        }
    }
    public void launch(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[runLocal]";
        loggerExtension.info(".run build");
        try {
            gGlobal=global;
            gSlashCommandEvent =ev;
            gUser = gSlashCommandEvent.getUser();gMember= gSlashCommandEvent.getMember();
            gGuild =gSlashCommandEvent.getGuild();
            loggerExtension.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            loggerExtension.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gTextChannel = gSlashCommandEvent.getTextChannel();
            loggerExtension.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,null,e.toString());

        }
    }
    public void launch(lcGlobalHelper global,Guild guild, User user, String command){
        String fName="[runLocal]";
        loggerExtension.info(".run build");
        try {
            gGlobal=global;
            gUser = user;
            gGuild = guild;
            loggerExtension.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            loggerExtension.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gRawForward=command;
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,null,e.toString());

        }
    }
    public lcJSONUserProfile gUserProfile;
    public String profileName="";

    public boolean isBooster=false;
    public  boolean isNsfwCommand=false;
    public boolean isNSFW(){
        String fName = "[isNSFW]";
        if(gTextChannel.isNSFW()){
            return true;
        }
        /*if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
            return true;
        }*/
        return false;
    }

    public lcBDSMGuildProfiles gBDSMCommands;public lcJSONUserProfile gGagUserProfile;


    public lcBasicFeatureControl gBasicFeatureControl;
    public String gTitle ="",llPrefixStr="",KeyTag="";
    public  void setEnable(boolean enable) {
        String fName = "[setEnable]";
        try {
            loggerExtension.info(fName + "enable=" + enable);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                loggerExtension.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            gBasicFeatureControl.setEnable(enable);
            if(enable){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
            }
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }
    public  void getChannels(int type, boolean toDM) {
        String fName = "[setChannel]";
        try {
            loggerExtension.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                loggerExtension.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                loggerExtension.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }
    public  void getRoles(int type, boolean toDM) {
        String fName = "[getRoles]";
        try {
            loggerExtension.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                loggerExtension.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Allowed roles list: "+ lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                loggerExtension.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }
    public  void setChannel(int type, int action, Message message) {
        String fName = "[setChannel]";
        try {
            loggerExtension.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                loggerExtension.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result=false;
            if(type==1){
                loggerExtension.info(fName+"allowed");
                if(action==1){
                    loggerExtension.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    loggerExtension.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    loggerExtension.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    loggerExtension.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                loggerExtension.info(fName+"denied");
                if(action==1){
                    loggerExtension.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    loggerExtension.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    loggerExtension.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    loggerExtension.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }
    public  void setRole(int type, int action, Message message) {
        String fName = "[setRole]";
        try {
            loggerExtension.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                loggerExtension.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result=false;
            if(type==1){
                loggerExtension.info(fName+"allowed");
                if(action==1){
                    loggerExtension.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    loggerExtension.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    loggerExtension.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    loggerExtension.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                loggerExtension.info(fName+"denied");
                if(action==1){
                    loggerExtension.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    loggerExtension.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    loggerExtension.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        loggerExtension.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    loggerExtension.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        loggerExtension.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        loggerExtension.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            loggerExtension.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }
    public  void menuGuild(){
        String fName="[menuGuild]";
        loggerExtension.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(gTitle +" Options");
            embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
            embed.addField("Allowed channels","Commands:`"+llPrefixStr+KeyTag+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llPrefixStr+KeyTag+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llPrefixStr+KeyTag+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llPrefixStr+KeyTag+" server blockroles :four:/list|add|rem|set|clear`",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if(gBasicFeatureControl.getEnable()){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExtension.warn(fName+"name="+name);
                            lsMessageHelper.lsMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
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
                            loggerExtension.error(fName + ".exception=" + e3);
                            loggerExtension.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e.toString());
        }
    }



    lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    public InteractionHook gCurrentInteractionHook,gComponentInteractionHook,gSlashInteractionHook;
    public void deferReplySet(SelectionMenuEvent selectionMenuEvent){
        String fName="[deferReplySet -selectionMenuEvent]";
        try{
            loggerExtension.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(selectionMenuEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(ButtonClickEvent buttonClickEvent){
        String fName="[deferReplySet -buttonClickEvent]";
        try{
            loggerExtension.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(buttonClickEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(SlashCommandEvent slashCommandEvent){
        String fName="[deferReplySet -slashCommandEvent]";
        try{
            loggerExtension.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(slashCommandEvent,true);
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyCheckDm(){
        String fName="[slashReplyPleaseWait]";
        try{
            loggerExtension.info(fName);
            if(gSlashCommandEvent==null){loggerExtension.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){loggerExtension.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strOpeningDMMenu).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyPleaseWait(){
        String fName="[slashReplyPleaseWait]";
        try{
            loggerExtension.info(fName);
            if(gSlashCommandEvent==null){loggerExtension.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){loggerExtension.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder, java.util.List<ActionRow> actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description, Color color){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendPrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
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
            loggerExtension.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, java.util.List<ActionRow> actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
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
            loggerExtension.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExtension.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendPrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder, java.util.List<ActionRow> actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendOrUpdatePrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            loggerExtension.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, List<ActionRow> actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder,actionRows);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder,actionRows);
            }
            loggerExtension.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExtension.info(fName);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExtension.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExtension.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExtension.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            loggerExtension.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message removeAction(Message message){
        String fName="[removeAction]";
        try{
            loggerExtension.info(fName);
            message.clearReactions().complete();
            message=message.editMessageComponents().complete();
            return message;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String textAdd(String source,String add){
        String fName="[stextAdd]";
        try{
            loggerExtension.info(fName);
            if(!source.isBlank())source+="\n"+add;
            return  source;
        } catch (Exception e) {
            loggerExtension.error(fName+".exception=" + e);
            loggerExtension.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
}
