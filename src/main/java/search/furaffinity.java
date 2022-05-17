package search;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
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
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import search.entities.lcFURAFFINITY;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class furaffinity extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, lcFURAFFINITY.INTERFACE {
    String gUrlSearch ="https://www.furaffinity.net/search/";
    String gUrlView ="https://www.furaffinity.net/view/%/";
    String gUrlUser="https://www.furaffinity.net/user/%/";
    String gUrlUserGallery="https://www.furaffinity.net/gallery/%/";
    Logger logger = Logger.getLogger(getClass()); 
    lcGlobalHelper gGlobal;CommandEvent gEvent;
    String gTitle="FurAffinity-ImageSearcher",gCommand="fa";
    User gUser;
    Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
    EventWaiter gWaiter;
    public furaffinity(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;gWaiter=global.waiter;
        this.name = gTitle;
        this.help = "Get images from FurAffinity";
        this.aliases = new String[]{gCommand,"furaffinity","faffinity"};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
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

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"furaffinity",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand = true;
               if(!isNSFW()){
                    blocked();return;
                }
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
            /*isInvalidCommand=false;
            logger.info(fName+".deleting op message");
            llQuckCommandMessageDelete(gEvent);
            if(isInvalidCommand){
                llQuickEmbedChannelResponse(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }*/
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
        private void getHtmlGallerySearch() {
            String fName = "[getHtmlGallerySearch]";
            try {
                Unirest a= new Unirest();
                //a.config().verifySsl(false);
                //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
                Map<String,Object> mapOptions=new LinkedHashMap<>();
                Iterator<String>keys=options.keys();
                while(keys.hasNext()){
                    String key=keys.next();
                    Object value=options.get(key);
                    mapOptions.put(key,value);
                }
                int i=0;
                for (Map.Entry<String, Object> entry : mapOptions.entrySet()) {
                    logger.info(fName+".mapOptions["+i+"]="+entry.getKey()+ ":" + entry.getValue().toString());i++;
                }
                if( options.has(keyQ)){
                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>"+options.getString(keyQ));
                }else{
                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>undefined");
                }

            /*Cookie cookie__cfduid_2=new Cookie("__cfduid","d887866db9e43b0f6262bffb8ecc44f901593255168");
            Cookie cookiea=new Cookie("a","5e16e67e-0ab2-44a3-9769-3235eebdc393");
            Cookie cookie__qca=new Cookie("__qca","P0-487465766-1593253203374");
            Cookie cookie__gads=new Cookie("__gads","ID=6ede4f83e6fdeae2:T=1593253203:S=ALNI_MbzxADPN8g6RHuFv-hBKPQ96JPpmg");
            Cookie cookie__cfduid=new Cookie("__cfduid","deeaef1de440c69a982b08688bcb8c0ad1593253201");
            Cookie cookieb=new Cookie("b","6c76433b-5c4f-412a-92eb-c76d2df1a985");
            Cookie cookiemc=new Cookie("mc","5ef72501-b2689-4634a-072a7");
            Cookie cookieOAID=new Cookie("OAID","6920ed3e677437cdee26b17397f58307");
            Cookie cookiecc=new Cookie("cc","1");
            Cookie cookiesz=new Cookie("sz","1311x627");

            cookieOAID.setDomain("rv.furaffinity.net");cookie__cfduid_2.setDomain(".facdn.net");cookiemc.setDomain(".quantserve.com");
            cookiecc.setDomain("www.furaffinity.net");cookiesz.setDomain("www.furaffinity.net");
            cookiea.setDomain(".furaffinity.net");cookie__qca.setDomain(".furaffinity.net");cookie__gads.setDomain(".furaffinity.net");cookie__cfduid.setDomain(".furaffinity.net");cookieb.setDomain(".furaffinity.net");

            cookie__cfduid.setHttpOnly(true);cookie__cfduid_2.setHttpOnly(true);cookiea.setHttpOnly(true);cookieb.setHttpOnly(true);*/

                HttpResponse<String> jsonResponse =a.post(gUrlSearch)
                        .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("cookie","__cfduid=deeaef1de440c69a982b08688bcb8c0ad1593253201; b=6c76433b-5c4f-412a-92eb-c76d2df1a985; __gads=ID=6ede4f83e6fdeae2:T=1593253203:S=ALNI_MbzxADPN8g6RHuFv-hBKPQ96JPpmg; __qca=P0-487465766-1593253203374; cc=1; a=5e16e67e-0ab2-44a3-9769-3235eebdc393; sz=1311x627")
                        .header("dnt","1")
                        .header("origin","https://www.furaffinity.net")
                        .header("referer:","https://www.furaffinity.net/search")
                        .header("sec-fetch-dest","document")
                        .header("sec-fetch-mode","navigate")
                        .header("sec-fetch-site:","same-origin")
                        .header("sec-fetch-user","?1")
                        .header("upgrade-insecure-requests","1")
                        /*.cookie(cookiea)
                        .cookie(cookie__qca)
                        .cookie(cookie__gads)
                        .cookie(cookie__cfduid).cookie(cookie__cfduid_2)
                        .cookie(cookieb)
                        .cookie(cookiemc)
                        .cookie(cookieOAID)
                        .cookie(cookiecc)
                        .cookie(cookiesz)*/
                        .queryString(mapOptions)
                        .asString();
                logger.info(fName+".headers ="+jsonResponse.getHeaders().toString());
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status"); return ;
                }
                String body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                String text="", textShadow="";
                int itemsI=-1,itemsJ=-1;
                if(body.contains("gallery-search-results")){
                    itemsI=body.indexOf("gallery-search-results");
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("/section");
                    if(itemsI>0&&itemsJ>0){
                        text=textShadow.substring(0,itemsJ-1);
                    }
                }
                text=text.replaceAll("</figure><!--","").replaceAll("-->","").replaceFirst("<figure","");
                logger.info(fName+".gallery-search-results ="+text);
                String [] items = text.split("<figure");
                jsonItems=new JSONArray();
                for(String item : items){
                    JSONObject jsonObject=convertText2JSONItem(item);
                    if(jsonObject.getString(keyItemType).equalsIgnoreCase("image")){
                        jsonItems.put(jsonObject);
                    }
                }

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);return ;
            }
        }
        JSONArray jsonItems=new JSONArray();
        JSONObject selectedItem=new JSONObject();
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


        private void doSearch(String message) {
            String fName = "[doSearch]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                setOptions(message);
                getHtmlGallerySearch();
                if(jsonItems.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);return;
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+jsonItems.length());
                if(jsonItems.length()>1){i=getRandomSlot(jsonItems.length());}
                selectedItem=jsonItems.getJSONObject(i);
                selectedItem.put(keyIndex,i);
                reactionMenu();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private void reactionMenu(){
            String fName="[reactionMenu]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String title="####";
                int index=-1;
                if(selectedItem.has(keyIndex)){
                    index=selectedItem.getInt(keyIndex);
                }
                if(selectedItem.has(keyItemTitle)){
                    title=selectedItem.getString(keyItemTitle);
                }

                String username="####";
                if(selectedItem.has(keyItemUserName)){
                    username=selectedItem.getString(keyItemUserName);
                }

                int rating=-1;
                if(selectedItem.has(keyItemRating)){
                    rating=selectedItem.getInt(keyItemRating);
                    if(rating==1) embed.addField("Rating","Mature",false);
                    else if(rating==2) embed.addField("Rating","Adult",false);
                    else embed.addField("Rating","General",false);
                }
                String image="";
                if(selectedItem.has(keyItemPreview)){
                    logger.info(fName+"keyItemPreview="+selectedItem.getString(keyItemPreview));
                    image=selectedItem.getString(keyItemPreview);
                }
                if(selectedItem.has(keyItemPage)){
                    String url=gUrlView.replaceAll("%",selectedItem.getString(keyItemPage));
                    logger.info(fName+"url="+url);
                    Unirest a= new Unirest();
                    HttpResponse<String> jsonResponse =a.get(url)
                            .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                            .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                            .header("cookie","__cfduid=deeaef1de440c69a982b08688bcb8c0ad1593253201; b=6c76433b-5c4f-412a-92eb-c76d2df1a985; __gads=ID=6ede4f83e6fdeae2:T=1593253203:S=ALNI_MbzxADPN8g6RHuFv-hBKPQ96JPpmg; __qca=P0-487465766-1593253203374; cc=1; a=5e16e67e-0ab2-44a3-9769-3235eebdc393; sz=1311x627")
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
                if(rating==0||gTextChannel.isNSFW()){
                    if(selectedItem.has(keyItemPage)){
                        embed.setTitle(title,gUrlView.replaceAll("%",selectedItem.getString(keyItemPage)));
                    }else{
                        embed.setTitle(title);
                    }
                    if(selectedItem.has(keyItemUser)){
                        embed.addField("Author","["+username+"]("+gUrlUser.replaceAll("%",selectedItem.getString(keyItemUser))+")",false);
                    }else{
                        embed.addField("Author",username,false);
                    }
                    embed.setImage("http://"+image);
                }else{
                    logger.warn(fName+"channel not nsfw");
                    embed.setDescription("**Attention** The NSFW image can only be displayed on NSFW channels!");
                }
                //embed.addField("Important","Report it if it violates rules 2 our staff!",false);
                Message message=llSendMessageResponse(gTextChannel,embed);
                if(jsonItems.length()>1){
                    if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                    if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                    if(index<jsonItems.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton)).queue();}
                    if(index!=jsonItems.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                    int finalIndex = index;
                    logger.info(fName+"prepare wait");
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    logger.info(fName+"nameCode="+nameCode);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex-1)>=0){
                                            selectedItem=jsonItems.getJSONObject(finalIndex-1);
                                            selectedItem.put(keyIndex,finalIndex-1);
                                            reactionMenu();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageClearReactions(e.getChannel(),e.getMessageId());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex+1)<jsonItems.length()) {
                                            selectedItem = jsonItems.getJSONObject(finalIndex + 1);
                                            selectedItem.put(keyIndex, finalIndex + 1);
                                            reactionMenu();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = jsonItems.getJSONObject(0);
                                        selectedItem.put(keyIndex,0);
                                        reactionMenu();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = jsonItems.getJSONObject(jsonItems.length()-1);
                                        selectedItem.put(keyIndex,jsonItems.length()-1);
                                        reactionMenu();
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
                            },1, TimeUnit.MINUTES, () -> {
                                llMessageDelete(message);
                            });
                    logger.info(fName+"wait created");
                }
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
                    getHtmlGalleryUser(message,Integer.parseInt(page));
                }else{
                    getHtmlGalleryUser(message,0);
                }
                if(jsonItems.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+jsonItems.length());
                if(jsonItems.length()>1){i=getRandomSlot(jsonItems.length());}
                selectedItem=jsonItems.getJSONObject(i);
                selectedItem.put(keyIndex,i);
                reactionMenu();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private void getHtmlGalleryUser(String name,int page) {
            String fName = "[getGallerySearch]";
            try {
                logger.info(fName+".name ="+name);
                logger.info(fName+".page ="+page);
                HttpResponse<String>stringHttpResponse=furaffinity.reqGetUserGallery(name,page);
                if(stringHttpResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid status!",llColorRed);return ;
                }
                String body=stringHttpResponse.getBody();
                //logger.info(fName+".body ="+body);
                String text="", textShadow="";
                int itemsI=-1,itemsJ=-1;
                if(body.contains("gallery-gallery")){
                    logger.info(fName+".found="+"gallery-gallery");
                    itemsI=body.indexOf("gallery-gallery");
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("/section");
                    if(itemsI>0&&itemsJ>0){
                        text=textShadow.substring(0,itemsJ-1);
                    }
                }
                text=text.replaceAll("</figure><!--","").replaceAll("-->","").replaceFirst("<figure","");
                logger.info(fName+".gallery-gallery ="+text);
                String [] items = text.split("<figure");
                jsonItems=new JSONArray();
                for(String item : items){
                    JSONObject jsonObject=convertText2JSONItem(item);
                    if(jsonObject.getString(keyItemType).equalsIgnoreCase("image")){
                        jsonItems.put(jsonObject);
                    }
                }

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);return ;
            }
        }

        private void getUserPage(String name) {
            String fName = "[getUserPage]";
            try {
                logger.info(fName+".name ="+name);
                Unirest a= new Unirest();
                String url=gUrlUser.replaceAll("%",name);
                logger.info(fName+".url ="+url);
                HttpResponse<String>stringHttpResponse=furaffinity.reqGetUserProfile(name);
                logger.info(fName+".status ="+stringHttpResponse.getStatus());
                if(stringHttpResponse.getStatus()>299){
                    logger.error(fName+".invalid status"); return ;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.addField("User",name,false);
                String body=stringHttpResponse.getBody();
                logger.info(fName+".body ="+body);
                String textShadow="";
                int itemsI=-1,itemsJ=-1,l;
                String begin="";

                String image="";
                begin="img class=\"user-nav-avatar";
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
                            image="https://"+textShadow.substring(0, itemsJ);
                            logger.info(fName+"imageGot="+image);
                            embedBuilder.setThumbnail(image);
                        }
                    }
                }

                String member="";
                begin="div id=\"user-profile";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    itemsI=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(itemsI);
                    //itemsJ=textShadow.indexOf(">");
                    begin="title=\"";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        itemsI= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(itemsI+l);
                        itemsJ=textShadow.indexOf("\"");
                        if(itemsI>0&&itemsJ>0) {
                            member=textShadow.substring(0, itemsJ);
                            logger.info(fName+"member="+member);
                            member=member.replace("Account status: ","");
                            embedBuilder.addField("Account status",member,false);
                        }
                    }
                }
                String links="[Profile](https://www.furaffinity.net/user/%/)\n[Gallery](https://www.furaffinity.net/gallery/%/)\n[Scraps](https://www.furaffinity.net/scraps/%/)\n[Favorites](https://www.furaffinity.net/favorites/%/)\n[Journals](https://www.furaffinity.net/journals/%/)";
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
