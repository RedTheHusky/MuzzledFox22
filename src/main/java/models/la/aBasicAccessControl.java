package models.la;

import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class aBasicAccessControl {
    public lcBasicFeatureControl gBasicFeatureControl;
    Logger logger;Guild guild;
    Member member;User user;TextChannel textChannel;
    String title,strCommand;lcGlobalHelper global;
    public void buildBasicFeatureControl(lcGlobalHelper global, Logger logger, String name, CommandEvent event) throws Exception {
        String fName = "[buildBasicFeatureControl]";
        if(logger==null)throw  new Exception("Logger cant be null!");this.logger=logger;
        if(event==null)throw  new Exception("Event cant be null!");
        if(global==null)throw  new Exception("Global cant be null!");this.global=global;
        if(name==null||name.isBlank())throw  new Exception("Name cant be null/blank!");
        user = event.getAuthor();
        logger.info(fName + ".user:" + user.getId() + "|" + user.getName());
        if(event.isFromType(ChannelType.TEXT)){
            member=event.getMember();
            guild = event.getGuild();
            logger.info(fName + ".guild:" + guild.getId() + "|" + guild.getName()); 
            textChannel = event.getTextChannel();
            logger.info(fName + ".textChannel:" + textChannel.getId() + "|" + textChannel.getName());
            gBasicFeatureControl=new lcBasicFeatureControl(guild,"chuckNorris_Joke",global);
            gBasicFeatureControl.initProfile();
        }
        
    }
    public void setString4BasicFeatureControl(String title,String command){
        String fName = "[setString4BasicFeatureControl]";
        this.title=title;this.strCommand=command;

    }
    public void setEnable(boolean enable) {
        String fName = "[setEnable]";
        logger.info(fName + "enable=" + enable);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(member)&&!lsGlobalHelper.slMemberIsBotOwner(member)){
            logger.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
            return;
        }
        gBasicFeatureControl.setEnable(enable);
        if(enable){
            lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
        }else{
            lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
        }
    }
    public void getChannels(int type, boolean toDM) {
        String fName = "[setChannel]";
        logger.info(fName + "type=" +type+", toDM="+toDM);
        if(type==1){
            logger.info(fName+"allowed");
            List<Long> list=gBasicFeatureControl.getAllowedChannelsAsLong();
            if(!list.isEmpty()){
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }
            }else{
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
        if(type==-1){
            logger.info(fName+"denied");
            List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
            if(!list.isEmpty()){
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }
            }else{
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
    }
    public void getRoles(int type, boolean toDM) {
        String fName = "[getRoles]";
        logger.info(fName + "type=" +type+", toDM="+toDM);
        if(type==1){
            logger.info(fName+"allowed");
            List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
            if(!list.isEmpty()){
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Allowed roles list: "+ lsRoleHelper.lsGetRolesMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }
            }else{
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
        if(type==-1){
            logger.info(fName+"denied");
            List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
            if(!list.isEmpty()){
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",guild), llColors.llColorOrange_Bittersweet);
                }
            }else{
                if(toDM){
                    lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
    }
    public void setChannel(int type, int action, Message message) {
        String fName = "[setChannel]";
        logger.info(fName + "type=" +type+", action="+action);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(member)&&!lsGlobalHelper.slMemberIsBotOwner(member)){
            logger.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                logger.info(fName+"set");
                if(!gBasicFeatureControl.clearAllowedChannels()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addAllowedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    logger.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);

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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                logger.info(fName+"clear");
                if(!gBasicFeatureControl.clearAllowedChannels()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                logger.info(fName+"set");
                if(!gBasicFeatureControl.clearDeniedChannels()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                List<TextChannel>textChannels=message.getMentionedChannels();
                for(TextChannel textChannel:textChannels){
                    result=gBasicFeatureControl.addDeniedChannel(textChannel);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    logger.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);

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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                logger.info(fName+"clear");
                if(!gBasicFeatureControl.clearDeniedChannels()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
            }
        }
    }
    public void setRole(int type, int action, Message message) {
        String fName = "[setRole]";
        logger.info(fName + "type=" +type+", action="+action);
        if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(member)&&!lsGlobalHelper.slMemberIsBotOwner(member)){
            logger.info(fName+"denied");
            lsMessageHelper.lsSendQuickEmbedMessage(user,title,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                logger.info(fName+"set");
                if(!gBasicFeatureControl.clearAllowedRoles()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addAllowedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    logger.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);

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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                logger.info(fName+"clear");
                if(!gBasicFeatureControl.clearAllowedRoles()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==2){
                logger.info(fName+"set");
                if(!gBasicFeatureControl.clearDeniedRoles()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                List<Role>roles=message.getMentionedRoles();
                for(Role role:roles){
                    result=gBasicFeatureControl.addDeniedRole(role);
                    if(!updated&&result)updated=true;
                }
                if(!updated){
                    logger.warn(fName+"failed to update");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);

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
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to update!");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",guild), llColors.llColorOrange_Bittersweet);
            }
            if(action==-2){
                logger.info(fName+"clear");
                if(!gBasicFeatureControl.clearDeniedRoles()){
                    logger.warn(fName+"failed to clear");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to set");
                    return;
                }
                if(!gBasicFeatureControl.saveProfile()){
                    logger.warn(fName+"failed to save");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,"Failed to save");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
            }
        }
    }
    public void menuGuild(){
        String fName="[menuGuild]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(title+" Options");
            embed.addField("Enable","Select "+global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
            embed.addField("Allowed channels","Commands:`"+ llGlobalHelper.llPrefixStr+strCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llGlobalHelper.llPrefixStr+strCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llGlobalHelper.llPrefixStr+strCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llGlobalHelper.llPrefixStr+strCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
            embed.addField("Help","Select "+global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(user,embed);
            lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gBasicFeatureControl.getEnable()){
                lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()== user.getIdLong()),
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
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> {
                        logger.info(fName+"timeout");
                        lsMessageHelper.lsMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel, user,title,e.toString());
        }
    }
}
