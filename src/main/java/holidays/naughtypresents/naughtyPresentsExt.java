package holidays.naughtypresents;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;
import models.ls.lsMessageHelper;
import restraints.models.entity.entityRDUserProfile;
import restraints.models.lcBDSMGuildProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class naughtyPresentsExt {
    Logger loggerExt = Logger.getLogger(getClass());

    lcGlobalHelper gGlobal;

    protected naughtyPresentsExt(){
       
    }
    public CommandEvent gCommandEvent;public SlashCommandEvent gSlashCommandEvent;
    InteractionHook gInteractionHook=null; Message gResturnedMessage=null;
    public GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
    public String gRawForward="";public boolean gIsForward=false, gIsOverride =false;
    public Guild gGuild; public User gUser;
    public Member gMember; public TextChannel gTextChannel;public Member gTarget;
    public String gArgs=""; public String[] gItems=null;

    public  Message gMessage;
    public EventWaiter gWaiter;

    public void launch(lcGlobalHelper global,CommandEvent ev){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        gGlobal=global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter=gGlobal.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
    }
    public void launch(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        gGlobal=global;
        gSlashCommandEvent =ev;
        gUser = gSlashCommandEvent.getUser();gMember= gSlashCommandEvent.getMember();
        gGuild =gSlashCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter=gGlobal.waiter;
        gTextChannel = gSlashCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
    }

    public boolean isNSFW(){
        String fName = "[isNSFW]";
        if(gTextChannel.isNSFW()){
            return true;
        }
        return false;
    }
    private void blocked(){
        String fName = "[blocked]";
        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel!",llColors.llColorRed);
        loggerExt.info(fName);
    }

    public lcBasicFeatureControl gBasicFeatureControl=new lcBasicFeatureControl();
    public String gTitle ="",llPrefixStr="",gCommand="";
    public  void setEnable(boolean enable) {
        String fName = "[setEnable]";
        loggerExt.info(fName + "enable=" + enable);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
            loggerExt.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
            return;
        }
        gBasicFeatureControl.setEnable(enable);
        if(enable){
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
        }else{
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
        }
    }
    public  void getChannels(int type, boolean toDM) {
        String fName = "[setChannel]";
        loggerExt.info(fName + "type=" +type+", toDM="+toDM);
        if(type==1){
            loggerExt.info(fName+"allowed");
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
            loggerExt.info(fName+"denied");
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
    }
    public  void getRoles(int type, boolean toDM) {
        String fName = "[getRoles]";
        loggerExt.info(fName + "type=" +type+", toDM="+toDM);
        if(type==1){
            loggerExt.info(fName+"allowed");
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
            loggerExt.info(fName+"denied");
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
    }
    public  void setChannel(int type, int action, Message message) {
        String fName = "[setChannel]";
        loggerExt.info(fName + "type=" +type+", action="+action);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
            loggerExt.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
            return;
        }
        boolean updated=false, result=false;
        if(type==1){
            loggerExt.info(fName+"allowed");
            if(action==1){
                loggerExt.info(fName+"add");
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addAllowedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                loggerExt.info(fName+"set");
                if(!gBasicFeatureControl.clearAllowedChannels()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addAllowedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

            }
            if(action==-1){
                loggerExt.info(fName+"rem");
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.remAllowedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                loggerExt.info(fName+"clear");
                if(!gBasicFeatureControl.clearAllowedChannels()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
            }
        }
        if(type==-1){
            loggerExt.info(fName+"denied");
            if(action==1){
                loggerExt.info(fName+"add");
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addDeniedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                loggerExt.info(fName+"set");
                if(!gBasicFeatureControl.clearDeniedChannels()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addDeniedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

            }
            if(action==-1){
                loggerExt.info(fName+"rem");
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.remDeniedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                loggerExt.info(fName+"clear");
                if(!gBasicFeatureControl.clearDeniedChannels()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
            }
        }
    }
    public  void setRole(int type, int action, Message message) {
        String fName = "[setRole]";
        loggerExt.info(fName + "type=" +type+", action="+action);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
            loggerExt.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
            return;
        }
        boolean updated=false, result=false;
        if(type==1){
            loggerExt.info(fName+"allowed");
            if(action==1){
                loggerExt.info(fName+"add");
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addAllowedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                loggerExt.info(fName+"set");
                if(!gBasicFeatureControl.clearAllowedRoles()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addAllowedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

            }
            if(action==-1){
                loggerExt.info(fName+"rem");
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.remAllowedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                loggerExt.info(fName+"clear");
                if(!gBasicFeatureControl.clearAllowedRoles()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
            }
        }
        if(type==-1){
            loggerExt.info(fName+"denied");
            if(action==1){
                loggerExt.info(fName+"add");
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addDeniedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                loggerExt.info(fName+"set");
                if(!gBasicFeatureControl.clearDeniedRoles()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addDeniedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

            }
            if(action==-1){
                loggerExt.info(fName+"rem");
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.remDeniedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    loggerExt.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                loggerExt.info(fName+"clear");
                if(!gBasicFeatureControl.clearDeniedRoles()){
                    loggerExt.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    loggerExt.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
            }
        }
    }
    public  void menuGuild(){
        String fName="[menuGuild]";
        loggerExt.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColors.llColorBlue1);
        embed.setTitle(gTitle +" Options");
        embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
        embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
        embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
        embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
        embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
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
                        loggerExt.warn(fName+"name="+name);
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
                        loggerExt.error(fName + ".exception=" + e3);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser, gTitle,e3.toString());
                        lsMessageHelper.lsMessageDelete(message);
                    }
                },5, TimeUnit.MINUTES, () -> {
                    lsMessageHelper.lsMessageDelete(message);
                });
    }
    public void loadBasic(String name){
        String fName="[runLocal]";
        gBasicFeatureControl=new lcBasicFeatureControl(gGuild,name,gGlobal);
        gBasicFeatureControl.initProfile();
    }
    public boolean isCommand2BasicFeatureControl(String []items) {
        String fName = "[isCommand2BasicFeatureControl]";
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
    }
    public boolean checkIFAllowed2UseCommand2_text() {
        String fName = "[checkIFAllowed2UseCommand2]";
        if(!gBasicFeatureControl.getEnable()){
            loggerExt.info(fName+"its disabled");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
            return false;
        }
        else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
            loggerExt.info(fName+"its not allowed by channel");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This  command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
            return false;
        }
        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
            loggerExt.info(fName+"its not allowed by roles");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
            return false;
        }
        return  true;
    }

    lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    public void setTitleStr(String title){
        String fName="[setTitleStr]";
        loggerExt.info(fName+".title="+title);
        gTitle=title;
    }
    public void setCommandStr(String command){
        String fName="[setCommandStr]";
        loggerExt.info(fName+".command="+command);
        gCommand=command;
    }
    public void setPrefixStr(String prefix){
        String fName="[setCommandStr]";
        loggerExt.info(fName+".prefix="+prefix);
        llPrefixStr=prefix;
    }
    public boolean check4TargetinItems() {
        String fName = "[check4TargetinItems]";
        if((gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")) {
            loggerExt.info(fName + ".detect mention characters");
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            if (mentions.isEmpty()) {
                loggerExt.warn(fName + ".zero member mentions in message>check itemns[0]");
                gTarget = lsMemberHelper.lsGetMember(gGuild, gItems[0]);
            } else {
                loggerExt.info(fName + ".member mentions in message");
                gTarget = mentions.get(0);
            }
            if (gTarget == null) {
                loggerExt.warn(fName + ".zero member mentions");
            } else if (gTarget.getIdLong() == gUser.getIdLong()) {
                loggerExt.warn(fName + ".target cant be the gUser");
                gItems = lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                gTarget = null;
                //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
            }
        }
        if(gTarget==null)return false;
        return true;
    }
    public void sendEmbedError(String description){
        String fName="[sendEmbedError]";
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColors.llColorRed_Chili).setThumbnail(lsAssets.errorImage);
            if(description!=null&&!description.isBlank())embedBuilder.setDescription(description);
            else embedBuilder.setDescription("(error)");
            if(gTitle!=null&&!gTitle.isBlank())embedBuilder.setTitle(gTitle);
            sendEmbed(embedBuilder);
            if(gResturnedMessage!=null&gResturnedMessage.isFromGuild()){
                autoDeleteResturnedMessage();
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    public void sendEmbedErrorDM(String description){
        String fName="[sendEmbedError]";
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColors.llColorRed_Chili).setThumbnail(lsAssets.errorImage);
            if(description!=null&&!description.isBlank())embedBuilder.setDescription(description);
            else embedBuilder.setDescription("(error)");
            if(gTitle!=null&&!gTitle.isBlank())embedBuilder.setTitle(gTitle);
            sendEmbed(embedBuilder);
            lsMessageHelper.lsSendMessage(gUser,embedBuilder);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    public void sendEmbed(EmbedBuilder embedBuilder){
        String fName="[sendEmbed]";
        try{
            if(gInteractionHook!=null)gResturnedMessage=gInteractionHook.editOriginalEmbeds(embedBuilder.build()).complete();
            else if(gSlashCommandEvent!=null){
                gInteractionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                gResturnedMessage=gInteractionHook.retrieveOriginal().complete();
            }
            else gResturnedMessage=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    public void sendEmbedTest(){
        String fName="[sendEmbedTest]";
        try{
            loggerExt.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription("test").setTitle(gTitle);
            if(gResturnedMessage!=null)gResturnedMessage=gResturnedMessage.editMessageEmbeds(embedBuilder.build()).complete();
            else if(gInteractionHook!=null)gResturnedMessage=gInteractionHook.editOriginalEmbeds(embedBuilder.build()).complete();
            else if(gSlashCommandEvent!=null)gInteractionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
            else lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            loggerExt.info(fName+"done");
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    public void sdeleteorNot(Message message){
        String fName="[sdeleteorNot]";
        try{

            if(gInteractionHook!=null)loggerExt.info(fName+"keep");
            else {
                loggerExt.info(fName+"delete");
                lsMessageHelper.lsMessageDelete(message);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }

    entity4NaughtyPresents entityGeneral=new entity4NaughtyPresents();
    entityUserProfile4NaughtyPresents entityUser=new entityUserProfile4NaughtyPresents();
    entityRDUserProfile entityRDUser=new entityRDUserProfile();
    public lcBDSMGuildProfiles gBDSMCommands=new lcBDSMGuildProfiles();
    public void loadMainProfiles(){
        String fName="[loadMainProfiles]";
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        entityGeneral.gGlobal=gGlobal;
        if(!entityGeneral.getCache()){
            loggerExt.info(fName+"mainjson not in cache -> get from file");
            if(entityGeneral.readFile()){
                if(!entityGeneral.setCache()){
                    loggerExt.warn(fName+"failed to cache");
                }
            }else{
                loggerExt.warn(fName+"failed to read");
            }
        }else{
            loggerExt.info(fName+"mainjson is in cache");
        }
        gBDSMCommands=new lcBDSMGuildProfiles(gGlobal,gGuild);
        gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
    }
    public void loadUserProfiles(){
        String fName="[loadUserProfiles]";
        entityGeneral.gGlobal=gGlobal;
        if(gTarget==null){
            loggerExt.info(fName+"self");
            entityUser.build(gGlobal,gMember);
            entityRDUser.build(gGlobal,gMember,gBDSMCommands);
        }else{
            loggerExt.info(fName+"target");
            entityUser.build(gGlobal,gTarget);
            entityRDUser.build(gGlobal,gTarget,gBDSMCommands);
        }
    }

    public String stringReplacer2(String source,String user_id,String target_id){
        String fName="[stringReplacer2]";
        Logger logger = Logger.getLogger(getClass());
        logger.info(fName + ".executed");
        try {
            String user_str="",target_str="";
            User user=lsUserHelper.lsGetUser(gGuild,user_id);
            User target=lsUserHelper.lsGetUser(gGuild,target_id);
            if(user!=null){
                user_str=user.getAsMention();
            }else{
                user_str="<@"+user_id+">";
            }
            if(target!=null){
                target_str=target.getAsMention();
            }else{
                target_str="<@"+target_id+">";
            }
            return stringReplacer1(source,user_str,target_str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacer2(String source,String user_id){
        String fName="[stringReplacer2]";
        Logger logger = Logger.getLogger(getClass());
        logger.info(fName + ".executed");
        try {
            String user_str="",target_str="";
            User user=lsUserHelper.lsGetUser(gGuild,user_id);
            if(user!=null){
                user_str=user.getAsMention();
            }else{
                user_str="<@"+user_id+">";
            }
            return stringReplacer1(source,user_str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacer1(String source,String user,String target){
        String fName="[stringReplacer1]";
        Logger logger = Logger.getLogger(getClass());
        logger.info(fName + ".executed");
        try {
            if(user!=null&&!user.isBlank())source=source.replaceAll("!USER",user);
            if(target!=null&&!target.isBlank())source=source.replaceAll("!TARGET",target);
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacerEventName(String source,String event){
        String fName="[stringReplacerEventName]";
        Logger logger = Logger.getLogger(getClass());
        logger.info(fName + ".executed");
        try {
            if(event!=null&&!event.isBlank())source=source.replaceAll("!EVENT",event);
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacer1(String source,String user){
        String fName="[stringReplacer1]";
        Logger logger = Logger.getLogger(getClass());
        logger.info(fName + ".executed");
        try {
            if(user!=null&&!user.isBlank())source=source.replaceAll("!USER",user);
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public void autoDeleteSlashMessage(){
        String fName="[autoDeleteSlashMessage]";
        try{
            Message message=gInteractionHook.retrieveOriginal().complete();
            autoDeleteMessage(message);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void autoDeleteResturnedMessage(){
        String fName="[autoDeleteResturnedMessage]";
        try{
            Message message=gResturnedMessage;
            autoDeleteMessage(message);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void autoDeleteMessage(Message message){
        String fName="[autoDeleteMessage]";
        try{
            if(message==null)throw  new Exception(fName+"Unkniown message");
            gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        lsMessageHelper.lsMessageDelete(message);
                    },1, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);
                        loggerExt.info(fName+lsGlobalHelper.timeout_reaction_add);});
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
