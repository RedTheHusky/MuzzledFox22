package nsfw;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsChannelHelper;
import models.ls.lsRoleHelper;
import models.ls.lsUnicodeEmotes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diaperInteractive extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
        Logger logger = Logger.getLogger(getClass()); String cName="[diaperInteractive]";
        String gTable ="diaperInteractive";
        lcGlobalHelper gGlobal;
        String sRTitle="Interactive Diaper";
        JSONObject rfEntries;
    public diaperInteractive(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Interactive_Diaper_Legacy";
        this.help = "Diaper fun module. (Old version)";
        this.aliases = new String[]{"diaperold","diaperoldplay"};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
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
    CommandEvent gEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel sessionChannel;
    TextChannel gTextChannel;
    lcJSONUserProfile gUserProfile;
    Member gTarget;
    boolean gIsOverride=false;
    public runLocal(CommandEvent ev) {
        logger.info(".run build");
        gEvent = ev;
    }
    lcBDSMGuildProfiles gBDSMCommands;
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        try {
            String[] items;
            boolean isInvalidCommand = true;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);gBDSMCommands.diaper.init();
            if(gEvent.getArgs().isEmpty()){
                logger.info(fName+".Args=0");
                help("main"); isInvalidCommand=false;
                //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
            }else {
                logger.info(fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                logger.info(fName + ".items.size=" + items.length);
                logger.info(fName + ".items[0]=" + items[0]);
                if(items[0].equalsIgnoreCase("guild")&&(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                    isInvalidCommand=false;
                    loadValues();
                    if(items.length<3){
                        menuGuild();
                    }else{
                        if(items[1].equalsIgnoreCase("exceptionmembers")){
                            switch (items[2].toLowerCase()){
                                case "add":
                                    setupAdd2Member();isInvalidCommand = false;
                                    break;
                                case "rem":
                                case "remove":
                                    setupRem2Member();isInvalidCommand = false;
                                    break;
                                case "list":
                                    setupViewList(iRestraints.fieldBannedUsers);isInvalidCommand = false;
                                    break;
                            }
                        }else
                        if(items[1].equalsIgnoreCase("allowedchannels")){
                            switch (items[2].toLowerCase()){
                                case "add":
                                    setupAdd2Channel(true);isInvalidCommand = false;
                                    break;
                                case "rem":
                                case "remove":
                                    setupRem2Channel(true);isInvalidCommand = false;
                                    break;
                                case "list":
                                    setupViewList(iRestraints.fieldAllowedChannels);isInvalidCommand = false;
                                    break;
                            }
                        }else
                        if(items[1].equalsIgnoreCase("commandroles")){
                            switch (items[2].toLowerCase()){
                                case "add":
                                    setupAdd2Role(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                    break;
                                case "rem":
                                case "remove":
                                    setupRem2Role(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                    break;
                                case "list":
                                    setupViewList(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                    break;
                            }
                        }else
                        if(items[1].equalsIgnoreCase("targetroles")){
                            switch (items[2].toLowerCase()){
                                case "add":
                                    setupAdd2Role(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                    break;
                                case "rem":
                                case "remove":
                                    setupRem2Role(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                    break;
                                case "list":
                                    setupViewList(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                    break;
                            }
                        }
                    }
                }
                loadValues();
                if(!vEnabled){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Its disabled!",llColorRed_Cinnabar);isInvalidCommand = false;
                }else
                if(!gBDSMCommands.diaper.isAllowedChannel4Command(gTextChannel)){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not allowed channel!",llColorRed_Cinnabar);isInvalidCommand = false;
                }else
                if(!gBDSMCommands.diaper.hasPermission2UseCommand(gMember)){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                }else
                if(gBDSMCommands.diaper.isMemberBanned2UseCommand(gMember)){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                }
                if(isInvalidCommand&&isTargeted()){
                    if((gTarget!=null&&!gBDSMCommands.diaper.hasPermission2TargetCommand(gTarget))){
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Member "+gTarget.getAsMention()+" can't be targeted!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else if(!gBDSMCommands.diaper.hasPermission2TargetCommand(gMember)){
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Member "+gMember.getAsMention()+" can't be targeted!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }
                    if(items.length >= 5&&isInvalidCommand){
                        if(items[1].equalsIgnoreCase("set")){
                            setField(items[2],items[3],items[4]);isInvalidCommand = false;
                        }
                    }
                    if(items.length>=3&&isInvalidCommand){
                        if(items[1].equalsIgnoreCase("caretaker")){
                            if(items[2].equalsIgnoreCase("release")){
                                releaseKeyHolder();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("accept")){
                                acceptKeyHolder();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("reject")){
                                rejectKeyHolder();isInvalidCommand = false;
                            }
                        }
                        if(items[1].equalsIgnoreCase("setup")){
                            if(items[2].equalsIgnoreCase("wetenable")){
                                setupWetEnable(true);isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("wetdisable")){
                                setupWetEnable(false);isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("messenable")){
                                setupMessEnable(true);isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("messdisable")){
                                setupMessEnable(false);isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("wetchance")){
                                if(items.length>=4){
                                    setupWetChance(items[3]);isInvalidCommand = false;
                                }
                            }
                            if(items[2].equalsIgnoreCase("messchance")){
                                if(items.length>=4){
                                    setupMessChance(items[3]);isInvalidCommand = false;
                                }
                            }
                            if(items[2].equalsIgnoreCase(accessPublic)){
                                setupAccess(items[2]);isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase(accessPrivate)){
                                setupAccess(items[2]);isInvalidCommand = false;
                            }
                        }
                    }
                    if(items.length>=2&&isInvalidCommand){
                        if(items[1].equalsIgnoreCase("verify")||items[1].equalsIgnoreCase("check")){
                            actionVerify();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("spank")){
                            actionSpank();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("tickle")){
                            actionTickle();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("change")){
                            actionChange();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("status")||items[1].equalsIgnoreCase("display")||items[1].equalsIgnoreCase("property")||items[1].equalsIgnoreCase("show")){
                            display();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("lock")){
                            diaperLock(true);isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("unlock")){
                            diaperLock(false);isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase("setup")){
                            menuSetup();isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase(accessPublic)){
                            setupAccess(items[1]);isInvalidCommand = false;
                        }
                        if(items[1].equalsIgnoreCase(accessPrivate)){
                            setupAccess(items[1]);isInvalidCommand = false;
                        }
                    }
                }else{
                    if(items[0].equalsIgnoreCase("clear")){
                        clearUser();isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("clearall")){
                        clearAll();isInvalidCommand=false;
                    }
                    if(items.length >= 4){
                        if(items[0].equalsIgnoreCase("set")){
                            setField(items[1],items[2],items[3]);isInvalidCommand = false;
                        }
                    }
                    if(items.length>=2&&isInvalidCommand){
                        if(items[0].equalsIgnoreCase("help")){
                            if(items[1].equalsIgnoreCase("main")||items[1].equalsIgnoreCase("setup")||items[1].equalsIgnoreCase("caretaker")){
                                help(items[1]);isInvalidCommand = false;
                            }
                        }
                        if(items[0].equalsIgnoreCase("caretaker")){
                            if(items[1].equalsIgnoreCase("add")){
                                addKeyHolder();isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("remove")){
                                removeKeyHolder();isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("runaway")){
                                runnaway();isInvalidCommand = false;
                            }
                        }
                        if(items[0].equalsIgnoreCase(accessPublic)){
                            setupAccess(items[0]);isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase(accessPrivate)){
                            setupAccess(items[0]);isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("setup")){
                            if(items[1].equalsIgnoreCase(accessPublic)){
                                setupAccess(items[1]);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase(accessPrivate)){
                                setupAccess(items[1]);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("wetenable")){
                                setupWetEnable(true);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("wetdisable")){
                                setupWetEnable(false);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("messenable")){
                                setupMessEnable(true);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("messdisable")){
                                setupMessEnable(false);isInvalidCommand = false;
                            }
                            else if(items[1].equalsIgnoreCase("wetchance")){
                                if(items.length>=3){
                                    setupWetChance(items[2]);isInvalidCommand = false;
                                }
                            }
                            else if(items[1].equalsIgnoreCase("messchance")){
                                if(items.length>=3){
                                    setupMessChance(items[2]);isInvalidCommand = false;
                                }
                            }
                            else if(items[1].equalsIgnoreCase("runaway")){
                                runnaway();isInvalidCommand = false;
                            }
                        }
                    }
                    if(items.length>=1&&isInvalidCommand){
                        if(items[0].equalsIgnoreCase("setup")){
                            menuSetup();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("status")||items[0].equalsIgnoreCase("display")||items[0].equalsIgnoreCase("property")||items[0].equalsIgnoreCase("show")){
                            display();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("verify")||items[0].equalsIgnoreCase("check")){
                            actionVerify();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("spank")){
                            actionSpank();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("tickle")){
                            actionTickle();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("change")){
                            actionChange();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("pee")){
                            selfWet();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("mess")){
                            selfMessy();isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("lock")){
                            diaperLock(true);isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("unlock")){
                            diaperLock(false);isInvalidCommand = false;
                        }
                    }
                }
            }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
            if(isInvalidCommand){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        logger.info(".run ended");
    }
    private void clearUser(){
        String fName="[clearUser]";
        logger.info(fName);
        gGlobal.clearUserProfile(profileName,gUser,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void clearAll(){
        String fName="[clearAll]";
        logger.info(fName);
        gGlobal.clearUserProfile(profileName,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void display(){
        String fName = "[display]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        EmbedBuilder embed=new EmbedBuilder();
        //embed.addField("","",false);
        embed.addField("Access",access,true);
        if(!caretakerID.isEmpty()&&caretakerAccepted){
            Member caretaker=gGuild.getMemberById(caretakerID);
            if(caretaker==null){
                embed.addField("Caretaker","(not a member)",true);
            }else{
                embed.addField("Caretaker",caretaker.getAsMention(),true);
            }
        }else
        if(!caretakerID.isEmpty()){
            Member caretaker=gGuild.getMemberById(caretakerID);
            if(caretaker==null){
                embed.addField("Caretaker","(not a member)[requirement]",true);
            }else{
                embed.addField("Caretaker",caretaker.getAsMention()+"[requirement]",true);
            }
        }else{
            embed.addField("Caretaker","(N/A)",true);
        }
        if(diaperLocked){
            embed.addField("Locked","Yes",true);
        }else{
            embed.addField("Locked","No",true);
        }
        if(wetEnabled){
            embed.addField("Wet","Level: "+wetLevel+"/6\nChance: 1 out of "+wetChance,false);
        }else{
            embed.addField("Wet","Disabled",false);
        }
        if(messyEnabled){
            embed.addField("Messy","Level: "+messyLevel+"/6\nChance: 1 out of "+messyChance,false);
        }else{
            embed.addField("Messy","Disabled",false);
        }
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
    }
    private void addKeyHolder(){
        String fName = "[addKeyHolder]";
        logger.info(fName);
        logger.info(fName + ".me");
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!caretakerID.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Already have a keyholder", llColorRed); return;
        }
        List<Member> members=gEvent.getMessage().getMentionedMembers();
        if(members.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"No mention!", llColorRed); return;
        }
        Member target=members.get(0);
        if(target.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't name yourself as keyholder!", llColorRed); return;
        }
        caretakerID=target.getId();
        putFieldEntry(fieldCaretaker, keyCaretakerAccepted,false);
        putFieldEntry(fieldCaretaker, keyCaretakerId,target.getId());
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessage(gUser,sRTitle,"Added "+target.getAsMention()+" as your caretaker. They need to confirm it!", llColorPurple1);
        Message message=llSendQuickEmbedMessageResponse(target.getUser(),sRTitle,gUser.getAsMention()+" added you as their caretaker! Please confirm or deny it. \nAccept:!>diaper "+gUser.getAsMention()+" accept\nReject !>diaper "+gUser.getAsMention()+" reject. Or use the reactions.", llColorPurple1);
        message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
        message.addReaction(lsUnicodeEmotes.unicode_ThumbsDown).queue();
        waitForKeyholderResponse(message,target);
    }
    private void waitForKeyholderResponse(Message message,Member keyholder){
        String fName = "[waitForKeyholderResponse]";
        logger.info(fName);
        try{
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(keyholder.getId())),
                    e -> {
                        String codepoints = e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName + "codepoints=" + codepoints);
                        if(caretakerID.isEmpty()||!keyholder.getId().equalsIgnoreCase(caretakerID)){
                            llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"Denied! You are not their caretaker!\nIts possible they change it or removed it!", llColorRed); return;
                        }
                        if (codepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)) {
                            llMessageDelete(e.getChannel(), e.getMessageId());
                            putFieldEntry(fieldCaretaker, keyCaretakerAccepted,true);
                            if(!saveProfile()){
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            if(caretakerAccepted){
                                llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"Already accepted!", llColorRed); return;
                            }
                            llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"You grabbed "+gUser.getAsMention()+"s key that they offered it to you.", llColorGreen1);
                            llSendQuickEmbedMessage(gUser,sRTitle,keyholder.getAsMention()+" has accepted your key offering.", llColorGreen1);
                        }else
                        if (codepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_ThumbsDown)) {
                            putFieldEntry(fieldCaretaker, keyCaretakerAccepted,false);
                            putFieldEntry(fieldCaretaker, keyCaretakerId,"");
                            if(!saveProfile()){
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+"'s offering.", llColorPurple1);
                            llSendQuickEmbedMessage(gUser,sRTitle,keyholder.getAsMention()+" has rejected your key offering.", llColorPurple1);
                        }else{
                            waitForKeyholderResponse(message,keyholder);
                        }
                    },5, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout for quick react. Us text command in guild.", llColorRed);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void removeKeyHolder(){
        String fName = "[removeKeyHolder]";
        logger.info(fName);
         logger.info(fName + ".me");
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
         loadDUserValues();
        if(!caretakerID.isEmpty()&&caretakerAccepted){
            llSendQuickEmbedMessage(gUser,sRTitle,"Only your caretaker can do this. You can only remove caretaker who did not accepted your offer.", llColorRed); return;
        }
        if(caretakerID.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"No caretaker to remove!", llColorRed); return;
        }
        caretakerID="";
        putFieldEntry(fieldCaretaker, keyCaretakerAccepted,false);
        putFieldEntry(fieldCaretaker, keyCaretakerId,"");
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}

        llSendQuickEmbedMessage(gUser,sRTitle,"Removed your potential caretaker.", llColorPurple1);
        llSendQuickEmbedMessage(Objects.requireNonNull(gGuild.getMemberById(caretakerID)).getUser(),sRTitle,gUser.getAsMention()+" removed you as their potential caretaker.", llColorPurple1);
    }
    private void acceptKeyHolder(){
        String fName = "[acceptKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't use yourself as caretaker!", llColorRed); return;
        }
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
         loadDUserValues();
         if(caretakerID.isEmpty()||!gUser.getId().equalsIgnoreCase(caretakerID)){
             llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their caretaker!", llColorRed); return;
         }
        if(caretakerAccepted){
            llSendQuickEmbedMessage(gUser,sRTitle,"Already accepted!", llColorRed); return;
        }
         caretakerAccepted=true;
         putFieldEntry(fieldCaretaker, keyCaretakerAccepted,true);
         if(!saveProfile()){
             llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
         llSendQuickEmbedMessage(gUser,sRTitle,"You grabbed "+gTarget.getAsMention()+"s key that they offered it to you.", llColorGreen1);
         llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has accepted your key offering.", llColorGreen1);

    }
    private void rejectKeyHolder(){
        String fName = "[rejectKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't use yourself as caretaker!", llColorRed); return;
        }
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(caretakerID.isEmpty()||!gUser.getId().equalsIgnoreCase(caretakerID)){
            llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their caretaker!", llColorRed); return;
        }
        caretakerAccepted=false;
        putFieldEntry(fieldCaretaker, keyCaretakerAccepted,false);
        putFieldEntry(fieldCaretaker, keyCaretakerId,"");
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessage(gUser,sRTitle,"You rejected "+gTarget.getAsMention()+"'s offering.", llColorPurple1);
        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has rejected your key offering.", llColorPurple1);
    }
    private void releaseKeyHolder(){
        String fName = "[releaseKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You can't use yourself as caretaker!", llColorRed); return;
        }
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(caretakerID.isEmpty()||!gUser.getId().equalsIgnoreCase(caretakerID)){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Denied! You are not their caretaker!", llColorRed); return;
        }
        putFieldEntry(fieldCaretaker, keyCaretakerAccepted,false);
        putFieldEntry(fieldCaretaker, keyCaretakerId,"");
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You gave back the key to "+gTarget.getAsMention()+".", llColorPurple1);
        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has given back your key.", llColorPurple1);

    }
    private void actionVerify(){
        String fName = "[diaperVerify]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        Boolean getWet=randomChance(wetChance);
        Boolean getMessy=randomChance(messyChance);
        logger.info(fName+"getWet="+getWet);logger.info(fName+"getMessy="+getMessy);
        String desc;
        Color color= llColorBlue1;
        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " checks their own diaper.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");return; }
            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper.";
        }
        boolean increased=false;
        if(wetEnabled&&getWet&&messyEnabled&&getMessy){
            logger.info(fName+"increase:wm");
            wetLevel++; messyLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
        }else
        if(wetEnabled&&getWet){
            logger.info(fName+"increase:w");
            wetLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
        }else
        if(messyEnabled&&getMessy){
            logger.info(fName+"increase:m");
            messyLevel++;increased=true;
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"wetEnabled="+wetEnabled);
        logger.info(fName+"wetLevel="+wetLevel);
        logger.info(fName+"messyEnabled="+messyEnabled);
        logger.info(fName+"messyLevel="+messyLevel);
        logger.info(fName+"increased="+increased);
       if(increased){
           if(!saveProfile()){
               llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
       }

        if((!wetEnabled&&!messyEnabled)||(wetLevel==0&&messyLevel==0)){
            desc += " Its dry like the desert and fresh like a pine forest.";
            color= llColorGreen1;
        }
        actionPosting(desc,color);
    }
    private void actionTickle(){
        String fName = "[actionTickle]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        boolean getWet=randomChance(wetChance);
        boolean getMessy=randomChance(messyChance);
        logger.info(fName+"getWet="+getWet);logger.info(fName+"getMessy="+getMessy);
        String desc;
        Color color= llColorBlue1;

        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " starts tickling themselves.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");return; }
            desc = gUser.getAsMention() + " jumps at " + gTarget.getAsMention() + " and starts tickling them.";
        }
        boolean increased=false;
        if(wetEnabled&&getWet&&messyEnabled&&getMessy){
            logger.info(fName+"increase:wm");
            wetLevel++; messyLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
            desc +=" Oh my, maybe the the little tickling was too much for them! It seams they had both little and big accident.";
        }else
        if(wetEnabled&&getWet){
            logger.info(fName+"increase:w");
            wetLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
            desc +=" Oh my, maybe the little tickling was too much for them! It seams they had a little accident.";
        }else
        if(messyEnabled&&getMessy){
            logger.info(fName+"increase:m");
            messyLevel++;increased=true;
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
            desc +=" Oh my, maybe the little tickling was too much for them! It seams they had a big accident.";
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"wetEnabled="+wetEnabled);
        logger.info(fName+"wetLevel="+wetLevel);
        logger.info(fName+"messyEnabled="+messyEnabled);
        logger.info(fName+"messyLevel="+messyLevel);
        logger.info(fName+"increased="+increased);
        if(increased){
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        }
        actionPosting(desc,color);
    }
    private void actionSpank(){
        String fName = "[actionSpank]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        Boolean getWet=randomChance(wetChance);
        Boolean getMessy=randomChance(messyChance);
        logger.info(fName+"getWet="+getWet);logger.info(fName+"getMessy="+getMessy);
        String desc;
        Color color= llColorBlue1;
        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " spanks their own diapered butt.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");return; }
            desc = gUser.getAsMention() + " spanks " + gTarget.getAsMention() + " diapered butt.";
        }
        boolean increased=false;
        if(wetEnabled&&getWet&&messyEnabled&&getMessy){
            logger.info(fName+"increase:wm");
            wetLevel++; messyLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
            desc +=" Oh my, maybe the spanking was too much for them! It seams they had both little and big accident.";
        }else
        if(wetEnabled&&getWet){
            logger.info(fName+"increase:w");
            wetLevel++; increased=true;
            putFieldEntry(fieldWet,keyLevel,wetLevel);
            desc +=" Oh my, maybe the spanking was too much for them! It seams they had a little accident.";
        }else
        if(messyEnabled&&getMessy){
            logger.info(fName+"increase:m");
            messyLevel++;increased=true;
            putFieldEntry(fieldMessy,keyLevel,messyLevel);
            desc +=" Oh my, maybe the spanking was too much for them! It seams they had a big accident.";
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"wetEnabled="+wetEnabled);
        logger.info(fName+"wetLevel="+wetLevel);
        logger.info(fName+"messyEnabled="+messyEnabled);
        logger.info(fName+"messyLevel="+messyLevel);
        logger.info(fName+"increased="+increased);
        if(increased){
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        }
        actionPosting(desc,color);
    }
    private void actionPosting(String desc, Color color){
        String fName = "[actionPosting]";
        logger.info(fName);
        Boolean getWet=randomChance(wetChance);
        Boolean getMessy=randomChance(messyChance);
        logger.info(fName+"getWet="+getWet);logger.info(fName+"getMessy="+getMessy);
        if(wetEnabled){
            switch (wetLevel) {
                case 1:
                    desc += " They are a bit wet between their legs.";
                    break;
                case 2:
                    desc += " They are visible wet between their legs. Luckily the diaper is holding it without any issues.";
                    break;
                case 3:
                    desc += " Their diaper is soggy, no way to hide that they peed themselves heavily. Luckily the diaper is holding it without any issues.";
                    break;
                case 4:
                    desc += " The diaper is full, it can't hold any more. Its advised to change it.";
                    break;
                case 5:
                    desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Its advised to change it.";
                    break;
                case 6:
                    desc += " The diaper got so full that a puddle has formed bellow their legs.";
                    break;
                default:
                    break;
            }
        }
        if(messyEnabled){
            switch (messyLevel) {
                case 1:
                    desc += " They got a bit of stink.";
                    break;
                case 2:
                    desc += " A little bulge starting to appear at their back.";
                    break;
                case 3:
                    desc += " That bulge got a bit bigger enough to see the brown color barely.";
                    break;
                case 4:
                    desc += " Its starting to get very obvious and stinky.";
                    break;
                case 5:
                    desc += " No way they can hide their stink and the spot on their back of their diapers.";
                    break;
                case 6:
                    desc += " Fully messy and stinky. Its time to call the hazmat unit!";
                    break;
                default:
                    break;
            }
        }
        if((!wetEnabled&&!messyEnabled)||(wetLevel==0&&messyLevel==0)){
            color= llColorGreen1;
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc);
        embed.setColor(color);
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
    }
    private void actionChange(){
        String fName = "[actionChange]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(gTarget==null){
            logger.info(fName+"self");
            if(!gIsOverride&&!caretakerID.isEmpty()&&caretakerAccepted&&diaperLocked){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Only your caretaker can change your diaper to a fresh clean one.", llColorRed); return;
            }
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");return; }
        }
        if(!gIsOverride&&diaperLocked){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Whops, the dipaer seams to be locked. The padlock needs to be removed before it can be changed to a new fresh one.", llColorRed); return;
        }
        putFieldEntry(fieldWet,keyLevel,0); putFieldEntry(fieldMessy,keyLevel,0);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(gTarget==null){
            logger.info(fName+"self");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Changed your diaper to a new fresh & dry one.", llColorGreen1);
        }else{
            logger.info(fName+"target");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Changed "+gTarget.getAsMention()+"'s diaper to a new fresh & dry one.", llColorGreen1);
        }
    }
    private void selfWet(){
        String fName = "[selfWet]";
        logger.info(fName);
        String desc;
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Can only be done by the wearer!", llColorRed); return;
        }
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        logger.info(fName+"wetEnabled="+wetEnabled);
        logger.info(fName+"wetLevel="+wetLevel);
        if(!wetEnabled){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Sorry, wetting is disabled!", llColorRed); return;
        }
        wetLevel++;
        putFieldEntry(fieldWet,keyLevel,wetLevel);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}

        desc=gUser.getAsMention()+ "wets themself. ";
        switch (wetLevel) {
            case 1:
                desc += " The warmth barely and quietly spreads. Nobody would notice it.";
                break;
            case 2:
                desc += " Still quiet but a small spot appearing on their diaper.";
                break;
            case 3:
                desc += " Their diaper starting to get soggy, much more visible and loud than before.";
                break;
            case 4:
                desc += " The diaper is full, it can't hold any more and its to puffy to hide it at all.";
                break;
            case 5:
                desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                break;
            case 6:
                desc += " The diaper reached its limit";
                break;
            default:
                break;
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc);
        embed.setColor(llColorBlue1);
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
    }
    private void selfMessy(){
        String fName = "[selfMessy]";
        logger.info(fName);
        String desc="";
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Can only be done by the wearer!", llColorRed); return;
        }
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        logger.info(fName+"messyEnabled="+messyEnabled);
        logger.info(fName+"messyLevel="+messyLevel);
        if(!messyEnabled){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Sorry, messing is disabled!", llColorRed); return;
        }
        messyLevel++;
        putFieldEntry(fieldMessy,keyLevel,messyLevel);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}

        desc=gUser.getAsMention()+ "mess themself. ";
        switch (messyLevel) {
            case 1:
                desc += " Mostly its gas.";
                break;
            case 2:
                desc += " A bit of dark spot appearing on their diapers butt.";
                break;
            case 3:
                desc += " Bulging and getting darker their diapered butt.";
                break;
            case 4:
                desc += " Very clear they have no potty training and need a change.";
                break;
            case 5:
                desc += " So full that sitting down would cause it to explode in a brown mess.";
                break;
            case 6:
                desc += " The diaper reached its limit. Call the hazmat team.";
                break;
            default:
                break;
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc);
        embed.setColor(llColorBlue1);
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
    }
    private void setupWetEnable(Boolean enable){
        String fName = "[setupWetEnable]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        logger.info(fName+"enable="+enable);
        if(enable==wetEnabled&&wetEnabled){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==wetEnabled&&!wetEnabled){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        putFieldEntry(fieldWet,keyEnabled,enable);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Wetness enabled for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
        }else{
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Wetness disabled for "+gUserProfile.getUser().getAsMention(), llColorRed);
        }
    }
    private void setupMessEnable(Boolean enable){
        String fName = "[setupMessEnable]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        logger.info(fName+"enable="+enable);
        if(enable==messyEnabled&&messyEnabled){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==messyEnabled&&!messyEnabled){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        putFieldEntry(fieldMessy,keyEnabled,enable);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Messy enabled for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
        }else{
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Messy disabled for "+gUserProfile.getUser().getAsMention(), llColorRed);
        }
    }
    private void setupWetChance(String option){
        String fName = "[setupWetChance]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
        }
        logger.info(fName+"option="+option);
        int number;
        if(option.isEmpty()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        putFieldEntry(fieldWet,keyChance,number);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"1 out of "+number+" chance for "+gUserProfile.getUser().getAsMention()+" wet themself when performing actions!", llColorGreen1);
        }else{
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Chance for "+gUserProfile.getUser().getAsMention()+" wet theemself is disabled.", llColorRed);
        }
    }
    private void setupMessChance(String option){
        String fName = "[setupMessChance]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
        }
        logger.info(fName+"option="+option);
        int number=0;
        if(option.isEmpty()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        putFieldEntry(fieldMessy,keyChance,number);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"1 out of "+number+" chance for "+gUserProfile.getUser().getAsMention()+" mess themself when performing actions!", llColorGreen1);
        }else{
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Chance of  "+gUserProfile.getUser().getAsMention()+" messing themself is disabled", llColorRed);
        }
    }
    private void setupAccess(String option){
        String fName = "[setupAccess]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
        }
        logger.info(fName+"option="+option);
        if(option.equalsIgnoreCase(accessPublic)){
            putFieldEntry(fieldProfile,keyProfileAccess,accessPublic);
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUserProfile.getUser().getAsMention()+" access is set to public.", llColorGreen1);
        }else
        if(option.equalsIgnoreCase(accessPrivate)){
            putFieldEntry(fieldProfile,keyProfileAccess,accessPrivate);
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUserProfile.getUser().getAsMention()+" access is set to private.", llColorGreen1);
        }
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
    }
    private void runnaway(){
        String fName = "[runnaway]";
        logger.info(fName);
        try {
            if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
            gUserProfile=runawayUserProfile(gUserProfile);
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Has runaway.", llColorRed);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private void diaperLock(Boolean locked){
        String fName = "[diaperLock]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
        }
        logger.info(fName+"locked="+locked);
        if(locked==diaperLocked&&diaperLocked){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already locked!", llColorPurple1);return;
        }
        if(locked==diaperLocked&&!diaperLocked){
            logger.info(fName+"same");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "It's already unlocked!", llColorPurple1);return;
        }
        putFieldEntry(fieldProfile,keyProfileLocked,locked);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(locked){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Locking "+gUserProfile.getUser().getAsMention()+" diapers with a padlock so they cant chance without unlocking first!", llColorRed);
        }else{
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Unlocking  "+gUserProfile.getUser().getAsMention()+" diapers.", llColorGreen1);
        }
    }
    private void setField(String field, String key, String value){
        String fName = "[diaperLock]";
        logger.info(fName);
        if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
        loadDUserValues();
        if(!llMemberIsStaff(gMember)){logger.info(fName+"denied");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Denied! Staff only!", llColorRed);return;}
        logger.info(fName+"field="+field);
        logger.info(fName+"key="+key);
        logger.info(fName+"value="+value);
        if(!field.equals(fieldProfile) && !field.equals(fieldWet) && !field.equals(fieldMessy) && !field.equals(fieldCaretaker)){
            logger.info(fName+"invalid field");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Invalid field: "+field, llColorRed);return;
        }
        if(!key.equals(keyProfileLocked) || !key.equals(keyProfileAccess) || !key.equals(keyLevel) || !key.equals(keyEnabled) || !key.equals(keyChance) || !key.equals(keyCaretakerId) || !key.equals(keyCaretakerAccepted)){
            logger.info(fName+"invalid key");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Invalid key: "+key, llColorRed);return;
        }
        putFieldEntry(field,key,value);
        if(!saveProfile()){
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Updated for "+gUserProfile.getUser().getAsMention()+": "+field+"."+key+"="+value, llColorGreen1);
    }
    private Boolean accessApprove4Setup(User user){
        String fName = "[accessApprove4Action]";
        logger.info(fName);
        logger.info(fName+"user:"+user.getId()+"|"+user.getName());
        if(gTarget==null) {
            logger.info(fName+"self");
            if(caretakerID.isEmpty()||!caretakerAccepted){
                logger.info(fName+"no caretaker"); return true;
            }
            if(caretakerID.equalsIgnoreCase(gUser.getId())){
                logger.info(fName+"equals"); return true;
            }
            logger.info(fName+"has caretaker");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Denied! Only your caretaker has access to setup!", llColorRed); return false;
        }else{
            logger.info(fName+"target");
            if(caretakerID.isEmpty()||!caretakerAccepted){
                logger.info(fName+"no caretaker"); llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Denied! You need to be "+gTarget.getAsMention()+"'s caretaker to access setup.", llColorRed);return false;
            }
            if(caretakerID.equalsIgnoreCase(gUser.getId())){
                logger.info(fName+"equals"); return true;
            }
            logger.info(fName+"not equals"); llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Denied! You need to be "+gTarget.getAsMention()+"'s caretaker to access setup.", llColorRed);return false;
        }
    }
    private Boolean accessApprove4Action(){
        String fName = "[accessApprove4Action]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName+"me>true as wearer should be able to do them");
            return true;
        }else{
            logger.info(fName+"target");
            logger.info(fName+"accessAction="+access);
            if(access.equalsIgnoreCase(accessPrivate)){
                if(caretakerID.isEmpty()||!gUser.getId().equalsIgnoreCase(caretakerID)||caretakerAccepted){
                    logger.info(fName+"invalid");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Denied! Only caretaker can access it while the access mode is private.\nIf you want to access it, "+gTarget.getAsMention()+" should change the mood (found in setup or use help) or add you as caretaker.", llColorRed);
                    return false;
                }
                logger.info(fName+"match keyholder");
                return true;
            }else
            if(access.equalsIgnoreCase(accessPublic)){
                logger.info(fName+"access set to public");
                return true;
            }
            logger.info(fName+"invalid access");
            return false;
        }
    }
    private Boolean isTargeted(){
        String fName = "[isTargeted]";
        logger.info(fName);
        try{ String[] items = gEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            logger.info(fName + ".items[0]=" + items[0]);
            if((items[0].contains("<@")||items[0].contains("<@!")||items[0].contains("<!@"))&&items[0].contains(">")){
                String tmp=items[0].replace("<!@","").replace("<@!","").replace("<@","").replace(">","");
                Member m=gGuild.getMemberById(tmp);
                if(m!=null){
                    if(m.getId().equals(gUser.getId())){
                        logger.info(fName + ".target same");
                        return false;
                    }
                    logger.info(fName + ".target ok");
                    gTarget=m;
                    return true;
                }
            }
            logger.info(fName + ".target none");
            return false;
        }
        catch(Exception ex){
            logger.error(fName + ".exception: "+ex);
            return false;
        }
    }
    private void menuSetup(){
        String fName="[menuMain]";
        logger.info(fName);
        try{
            if (!loadedProfile()) { llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to load "+gUserProfile.getUser().getAsMention()+" profile!", llColorRed);return; }
            loadDUserValues();
            if(!gIsOverride&&!accessApprove4Setup(gUser)){logger.info(fName+"denied");return;}
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            embed.addField("Access","Select :red_car: for private, :bus: for public. Currently: "+access,false);
            embed.addField("Wetness","Select :one: to enable or :two: to disable wetness. Currently: "+wetEnabled,false);
            embed.addField("Messiness","Select :three: to enable or :four: to disable messiness.Currently: "+messyEnabled,false);
            embed.addField("Wetness chance","Select :five: to decrease or :six: to increase.Currently: "+wetChance,false);
            embed.addField("Messiness chance","Select :seven: to decrease or :eight: to increase.Currently: "+messyChance,false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(lsUnicodeEmotes.unicodeEmote_One).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Two).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Three).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Four).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Five).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Six).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Seven).queue();
            message.addReaction(lsUnicodeEmotes.unicodeEmote_Eight).queue();
            message.addReaction(gGlobal.emojis.getEmoji("car")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("bus")).queue();
            message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_One)){
                            if(wetEnabled){
                                logger.info(fName+"same");
                                llSendQuickEmbedMessage(gUser, sRTitle, "It's already enabled!", llColorPurple1);return;
                            }
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            putFieldEntry(fieldWet,keyEnabled,true);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            if(gUserProfile.getUser()!=gUser){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Wetness enabled for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Wetness enabled.", llColorGreen1);
                            }else{
                                llSendQuickEmbedMessage(gUser,sRTitle,"Wetness enabled.", llColorGreen1);
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Two)){
                            if(!wetEnabled){
                                logger.info(fName+"same");
                                llSendQuickEmbedMessage(gUser, sRTitle, "It's already disabled!", llColorPurple1);return;
                            }
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            putFieldEntry(fieldWet,keyEnabled,false);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            if(gUserProfile.getUser()!=gUser){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Wetness disabled for "+gUserProfile.getUser().getAsMention(), llColorRed_Cinnabar);
                                llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Wetness disabled.", llColorRed_Cinnabar);
                            }else{
                                llSendQuickEmbedMessage(gUser,sRTitle,"Wetness disabled.", llColorRed_Cinnabar);
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                            if(messyEnabled){
                                logger.info(fName+"same");
                                llSendQuickEmbedMessage(gUser, sRTitle, "It's already enabled!", llColorPurple1);return;
                            }
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            putFieldEntry(fieldMessy,keyEnabled,true);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            if(gUserProfile.getUser()!=gUser){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Messiness enabled for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Messiness enabled.", llColorGreen1);
                            }else{
                                llSendQuickEmbedMessage(gUser,sRTitle,"Messiness enabled.", llColorGreen1);
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                            if(!messyEnabled){
                                logger.info(fName+"same");
                                llSendQuickEmbedMessage(gUser, sRTitle, "It's already disabled!", llColorPurple1);return;
                            }
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            putFieldEntry(fieldMessy,keyEnabled,false);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            if(gUserProfile.getUser()!=gUser){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Messiness disabled for "+gUserProfile.getUser().getAsMention(), llColorRed_Cinnabar);
                                llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Messiness disabled.", llColorRed_Cinnabar);
                            }else{
                                llSendQuickEmbedMessage(gUser,sRTitle,"Messiness disabled.", llColorRed_Cinnabar);
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Five)){
                            int tmp=wetChance;
                            tmp--;
                            if(tmp>=0&&tmp<=25){
                                wetChance=tmp;
                                putFieldEntry(fieldWet,keyChance,wetChance);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Six)){
                            int tmp=wetChance;
                            tmp++;
                            if(tmp>=0&&tmp<=25){
                                wetChance=tmp;
                                putFieldEntry(fieldWet,keyChance,wetChance);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Seven)){
                            int tmp=messyChance;
                            tmp--;
                            if(tmp>=0&&tmp<=25){
                                messyChance=tmp;
                                putFieldEntry(fieldMessy,keyChance,messyChance);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            }
                        }
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Eight)){
                            int tmp=messyChance;
                            tmp++;
                            if(tmp>=0&&tmp<=25){
                                messyChance=tmp;
                                putFieldEntry(fieldMessy,keyChance,messyChance);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            }
                        }
                        if(name.contains(gGlobal.emojis.getEmoji("car"))){
                           setupAccess(accessPrivate);
                        }
                        if(name.contains(gGlobal.emojis.getEmoji("bus"))){
                            setupAccess(accessPublic);
                        }
                        if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                            llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            return;
                        }
                        menuSetup();
                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    Calendar gCalendarToday;
    private void getTodayDate(){
        String fName="[getTodayDate]";
        logger.info(fName);
        gCalendarToday = Calendar.getInstance();
        logger.info(fName + ".today:"+gCalendarToday.get(gCalendarToday.YEAR)+"|"+gCalendarToday.get(gCalendarToday.MONTH)+"|"+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"@"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY)+gCalendarToday.get(gCalendarToday.MINUTE));
    }

    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+"diaperold <@target>";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPink1);
        embed.setTitle(sRTitle);
        if(command.equalsIgnoreCase("caretaker")){
            quickSummonWithSpace=llPrefixStr+"diaper caretaker";
            embed.addField("Add (Wearer side)","`" + quickSummonWithSpace + " add <@User>  gives @User a caretaker offer. They still need to accept it",false);
            embed.addField("Remove (Wearer side)","`" + quickSummonWithSpace + " remove ` removes the suggested caretaker, works while they did nto accept it.",false);
            quickSummonWithSpace=llPrefixStr+"diaper <@Wearer> caretaker";
            embed.addField("Accept (Caretaker side)","`" + quickSummonWithSpace + " accept` accepts the caretaker offer from wearer.",false);
            embed.addField("Reject (Caretaker side)","`" + quickSummonWithSpace + " reject` rejects the caretaker offer from wearer.",false);
            embed.addField("Release (Caretaker side)","`" + quickSummonWithSpace + " release` releases @wearer from you.",false);
            embed.addField("Runaway","`"+llPrefixStr+"diaper caretaker runaway` resets the profile and removes the caretaker.",false);

        }else
        if(command.equalsIgnoreCase("setup")){
            quickSummonWithSpace=llPrefixStr+"diaper <@wearer> setup";
            embed.addField("Wetness","`" + quickSummonWithSpace + " wetenable/wetdisable` enable or disable for wearer to get wet"+"\n`" + quickSummonWithSpace + " wetchance [0-25]` changes the chance for the wearer to get wet from action, 0 to disable it",false);
            embed.addField("Messiness","`" + quickSummonWithSpace + " messenable/messdisable` enable or disable for wearer to get messy"+"\n`" + quickSummonWithSpace + " messchance [0-25]` changes the chance for the wearer to get mess from action, 0 to disable it",false);
        }else{
            embed.addField("Display","`" + quickSummonWithSpace + " display` display the wearer diaper status",false);
            embed.addField("Check","`" + quickSummonWithSpace + " check` checks the diaper if its wet&messy",false);
            embed.addField("Change","`" + quickSummonWithSpace + " change` changes the wearer diaper",false);
            embed.addField("Lock/Unlock","`" + quickSummonWithSpace + " lock/unlock` locking or unlocking wearer diaper",false);
            embed.addField("Access","`" + quickSummonWithSpace + " setup "+accessPublic+"/"+accessPrivate+"` to set the diaper access.",false);
            embed.addField("Actions for user\\wearer","`" + quickSummonWithSpace + " span\\tickle` spanks the wearer diapered butt or tickle them.",false);
            quickSummonWithSpace=llPrefixStr+"diaper";
            embed.addField("Actions for wearer","`" + quickSummonWithSpace + " pee\\mess`, wearer pees or messes themself",false);
            embed.addField("!","<@wearer> is an optional value, used only if trying to traget somebody else and not yourself",false);
            quickSummonWithSpace=llPrefixStr+"diaper ";
            embed.addField("Setup","`" + quickSummonWithSpace + " setup` to access the menu"+"\n`" + quickSummonWithSpace + " help setup` info on how to access wearer diaper setup",false);
            embed.addField("Caretaker","\n`" + quickSummonWithSpace + " help caretaker` info on how to access craetaker options",false);
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                embed.addField("Guild Setup","To access the guild setup `"+llPrefixStr+"diaper guild`"+"\nTo add elements or view the list `"+llPrefixStr+"diaper guild commandroles|targetroles|allowedchannels|exceptionmembers add|rem|list <@mention>`\n\tcommandroles, list of roles who can give diaper commands\n\ttargetroles, list of roles who can get targeted with diaper commands\n\tallowedchannels, channels where the diaper has effect, if used the diaper wont work on channels not part of the list\n\texceptionmembers, members who the diaper effect does not work \n\t!!!empty list means no restrictions, it means everyone has access" , false);
            }
        }
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
    }
    String fieldProfile="profile";
    String keyProfileLocked ="locked";
    String keyProfileAccess ="access";String accessPrivate="private";String accessPublic="public";

    String fieldCaretaker ="caretaker";
    String keyCaretakerId ="id";
    String keyCaretakerAccepted ="accepted";

    String keyEnabled ="enabled";
    String keyChance ="chance";
    String keyLevel ="level";
    String fieldWet="wet";
    String fieldMessy="messy";
    private lcJSONUserProfile runawayUserProfile(lcJSONUserProfile userProfile){
        String fName="[runawayUserProfile]";
        logger.info(fName+".safety check");
        userProfile.jsonObject =new JSONObject();
        String field;
        field=fieldProfile;
        userProfile.safetyCreateFieldEntry(field);
        userProfile.safetyPutFieldEntry(field, keyProfileLocked,false);
        userProfile.safetyPutFieldEntry(field, keyProfileAccess,accessPrivate);
        field=fieldCaretaker;
        userProfile.safetyCreateFieldEntry(field);
        userProfile.safetyPutFieldEntry(field, keyCaretakerId,"");
        userProfile.safetyPutFieldEntry(field, keyCaretakerAccepted,false);
        field=fieldWet;
        userProfile.safetyCreateFieldEntry(field);
        userProfile.safetyPutFieldEntry(field, keyEnabled,true);
        userProfile.safetyPutFieldEntry(field, keyChance,4);
        userProfile.safetyPutFieldEntry(field, keyLevel,0);
        field=fieldMessy;
        userProfile.safetyCreateFieldEntry(field);
        userProfile.safetyPutFieldEntry(field, keyEnabled,false);
        userProfile.safetyPutFieldEntry(field, keyChance,4);
        userProfile.safetyPutFieldEntry(field, keyLevel,0);
        return  userProfile;
    }
    private void safetyUserProfileEntry(){
        String fName="[safetyUserProfileEntry]";
        logger.info(fName+".safety check");
        String field;
        field=fieldProfile;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field, keyProfileLocked,false);
        gUserProfile.safetyPutFieldEntry(field, keyProfileAccess,accessPrivate);
        field=fieldCaretaker;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field, keyCaretakerId,"");
        gUserProfile.safetyPutFieldEntry(field, keyCaretakerAccepted,false);
        field=fieldWet;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
        gUserProfile.safetyPutFieldEntry(field, keyChance,4);
        gUserProfile.safetyPutFieldEntry(field, keyLevel,0);
        field=fieldMessy;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field, keyEnabled,false);
        gUserProfile.safetyPutFieldEntry(field, keyChance,4);
        gUserProfile.safetyPutFieldEntry(field, keyLevel,0);
    }


    private Boolean putFieldEntry(String field,String name, Object value){
        String fName="[putFieldEntry]";
        logger.info(fName+".field="+field);
        logger.info(fName+".name="+name);
        logger.info(fName+".value="+value);
        if(name.equalsIgnoreCase(keyLevel)){
            int i= (int) value;
            if(i>6){value=6;}
            if(i<0){value=0;}
        }
        if(name.equalsIgnoreCase(keyChance)){
            int i= (int) value;
            if(i<0){value=0;}
        }
        if(gUserProfile.putFieldEntry(field,name,value)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }

    private Object getFieldEntry(String field,String name){
        String fName="[getFieldEntry]";
        logger.info(fName+".field="+field);
        logger.info(fName+".name="+name);
        Object tmp=gUserProfile.getFieldEntry(field,name);
        if(tmp!=null){
            logger.info(fName + ".success");
            return tmp;
        }
        logger.warn
                (fName + ".failed");
        return null;
    }
    private Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(gTable)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }
    String profileName="diaperinteraction";
    private Boolean loadedProfile(){
        String fName="[loadedProfile]";
        logger.info(fName);
        User user;
        if(gTarget==null){
            user=gUser;
        }else {
            user=gTarget.getUser();
        }
        logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
        gUserProfile=gGlobal.getUserProfile(profileName,user,gGuild);
        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild);
            if(gUserProfile.getProfile(gTable)){
                logger.info(fName + ".has sql entry");
            }
        }
        safetyUserProfileEntry();gGlobal.putUserProfile(gUserProfile,profileName);
        if(!gUserProfile.isUpdated){
            logger.info(fName + ".no update>ignore");return true;
        }
        if(!saveProfile()){ logger.error(fName+".failed to write in Db");
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed);}
        return true;
    }
    private Boolean randomChance(int i){
        String fName="[randomChance]";
        logger.info(fName + ".i="+i);
        if(i<=0){
            logger.info(fName + ".result:disabled");return false;
        }
        Random rand = new Random();
        int number = rand.nextInt(i);
        logger.info(fName + ".number="+number);
        if(number==0){
            logger.info(fName + ".result:true");return true;
        }
        logger.info(fName + ".result:false");return false;
    }

    String caretakerID=""; Boolean caretakerAccepted =false;String access="";Boolean diaperLocked=false;
    Boolean wetEnabled=false; int wetChance=0; int wetLevel=0;
    Boolean messyEnabled=false; int messyChance=0; int messyLevel=0;

    private void loadDUserValues(){
        String fName = "[loadDUserValues]";
        logger.info(fName);

        caretakerID= (String) getFieldEntry(fieldCaretaker, keyCaretakerId);
        caretakerAccepted = (Boolean)getFieldEntry(fieldCaretaker, keyCaretakerAccepted);

        diaperLocked= (Boolean)getFieldEntry(fieldProfile,keyProfileLocked);
        access= (String) getFieldEntry(fieldProfile, keyProfileAccess);

        wetEnabled= (Boolean)getFieldEntry(fieldWet,keyEnabled);
        wetChance= Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getFieldEntry(fieldWet, keyChance)).toString()));
        wetLevel= Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getFieldEntry(fieldWet, keyLevel)).toString()));

        messyEnabled= (Boolean)getFieldEntry(fieldMessy,keyEnabled);
        messyChance= Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getFieldEntry(fieldMessy, keyChance)).toString()));
        messyLevel= Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(getFieldEntry(fieldMessy, keyLevel)).toString()));
    }

    private Boolean IsStringAMember(String str){
        String fName = "[IsStringAMember]";
        logger.info(fName+"str="+str);
        try{
            Member u=gGuild.getMemberById(str);
            if(u!=null){logger.info(fName+".is not null");return true;}
            logger.info(fName+".is null");return false;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    String strGuildSetup="Gag Setup";
    private void menuGuild(){
        String fName="[menuGuild]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="Select one option";
            if(vEnabled){
                desc+="\n:one: to disable this branch";
            }else{
                desc+="\n:one: to enable this branch";
            }
            desc+="\n:three: Clear channels list";
            desc+="\n:four: Clear banned users list";
            desc+="\n:six: Reset Restrictions";
            embed.setTitle(strGuildSetup);embed.setColor(llColorPurple2);
            embed.setDescription(desc);
            embed.addField("Done","Select :white_check_mark: to finish.",false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(gGlobal.emojis.getEmoji("one")).queue();
            //message.addReaction(gGlobal.emojis.getEmoji("two")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("three")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("four")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("six")).queue();
            message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_One)){
                            logger.warn(fName+"trigger="+name);
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldEnabled,!vEnabled);
                            logger.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                            if(saveServerProfile()){
                                String t;
                                if(!vEnabled){
                                    t="enable";
                                }else{
                                    t="disable";
                                }
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Updated to "+t,llColorGreen2);
                            }else{
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to update",llColorRed_Imperial);
                            }
                        }else
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                            logger.warn(fName+"trigger="+name);
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldAllowedChannels,new JSONArray());
                            logger.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                            if(saveServerProfile()){
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Cleared channels list",llColorGreen2);
                            }else{
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to cleared channels list",llColorRed_Imperial);
                            }
                        }else
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                            logger.warn(fName+"trigger="+name);
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldBannedUsers,new JSONArray());
                            logger.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                            if(saveServerProfile()){
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Cleared forbidden users list",llColorGreen2);
                            }else{
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to cleared forbidden members list",llColorRed_Imperial);
                            }
                        }else
                        if(name.contains(lsUnicodeEmotes.unicodeEmote_Six)){
                            logger.warn(fName+"trigger="+name);
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldAllowedChannels,new JSONArray());
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldBannedUsers,new JSONArray());
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldCommandAllowedRoles,new JSONArray());
                            gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldTargetAllowedRoles,new JSONArray());
                            logger.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                            if(saveServerProfile()){
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Reset restrictions done",llColorGreen2);
                            }else{
                                llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to clear restrictions",llColorRed_Imperial);
                            }
                        }else
                        if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                            logger.warn(fName+"trigger="+name);
                            llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                        }else{
                            menuGuild();
                        }
                        llMessageDelete(e.getChannel(),e.getMessageId());
                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessage(gUser,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Channel(boolean isAllowed){
        String fName="[setupAdd2Channel]";
        logger.info(fName);
        try{
            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            JSONArray array;String field="";
          
            field=iRestraints.fieldAllowedChannels;
           
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
            for(TextChannel channel:channels){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(channel.getId())){
                        has=true;
                    }
                    i++;
                }
                if(!has){
                    array.put(channel.getId());
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated channels list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update channels list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Channel(boolean isAllowed){
        String fName="[setupRem2Channel]";
        logger.info(fName);
        try{
            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            JSONArray array;String field="";

            field=iRestraints.fieldAllowedChannels;

            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
            for(TextChannel channel:channels){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(channel.getId())){
                        has=true;
                    }else{
                        i++;
                    }
                }
                if(has){
                    array.remove(i);
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated channels list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update channels list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Member(){
        String fName="[setupAdd2Member]";
        logger.info(fName);
        try{
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( iRestraints.fieldBannedUsers)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  iRestraints.fieldBannedUsers)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( iRestraints.fieldBannedUsers);
            for(Member cmember:members){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                        has=true;
                    }
                    i++;
                }
                if(!has){
                    array.put(cmember.getId());
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated members list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update members list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Member(){
        String fName="[setupRem2Channel]";
        logger.info(fName);
        try{
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( iRestraints.fieldBannedUsers)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  iRestraints.fieldBannedUsers)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( iRestraints.fieldBannedUsers);
            for(Member cmember:members){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                        has=true;
                    }else{
                        i++;
                    }
                }
                if(has){
                    array.remove(i);
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated members list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update members list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupViewList(String field){
        String fName="[setupViewList]";
        logger.info(fName);
        try{
            logger.info(fName+"field="+field);
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                logger.info(fName+"null");
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in allowed channels list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in blocked users list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in command allowed roles list! Everyone can use the diaper commands.", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                return;
            }
            logger.info(fName+"step 2");
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
            if(array.isEmpty()){
                logger.info(fName+"empty");
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in allowed channels list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in blocked users list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in command allowed roles list! Everyone can use the diaper commands.", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                return;
            }
            String tmp="";
            for(int i=0;i<array.length();i++){
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    tmp+=" "+ lsChannelHelper.lsGetChannelMentionById(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    tmp+=" "+llGetMemberMention(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    tmp+=" "+ lsRoleHelper.lsGetRoleMentionByID(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    tmp+=" "+lsRoleHelper.lsGetRoleMentionByID(gGuild,array.getString(i));
            }
            if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Allowed channels list: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Blocked users list: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Allowed command roles: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Allowed target roles: "+tmp, llColorBlue3);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Role(String field){
        String fName="[setupAdd2Role]";
        logger.info(fName);
        try{
            logger.info(fName+" field="+ field);
            List<Role>roles=gEvent.getMessage().getMentionedRoles();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has(  field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put(  field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray(  field);
            for(Role role:roles){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(role.getId())){
                        has=true;
                    }
                    i++;
                }
                if(!has){
                    array.put(role.getId());
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated roles list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update roles list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Role(String field){
        String fName="[setupRem2Channel]";
        logger.info(fName);
        try{
            logger.info(fName+" field="+ field);
            List<Role>roles=gEvent.getMessage().getMentionedRoles();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
            for(Role role:roles){
                boolean has=false;int i=0;
                while(i<array.length()&&!has){
                    if(array.getString(i).equalsIgnoreCase(role.getId())){
                        has=true;
                    }else{
                        i++;
                    }
                }
                if(has){
                    array.remove(i);
                }
            }
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Updated roles list", llColorGreen2);
            }else{
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Failed to update roles list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    Boolean vEnabled=false;JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
    private void loadValues(){
        String fName = "[loadValues]";
        logger.info(fName);
        logger.info(fName+"json="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldEnabled)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldEnabled)){
            vEnabled = (Boolean)gBDSMCommands.diaper.gProfile.getFieldEntry(iRestraints.fieldEnabled);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldBannedUsers)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldBannedUsers)){
            vBannedUsers= (JSONArray) gBDSMCommands.diaper.gProfile.getFieldEntry(iRestraints.fieldBannedUsers);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldAllowedChannels)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldAllowedChannels)){
            vAllowedChannels= (JSONArray) gBDSMCommands.diaper.gProfile.getFieldEntry(iRestraints.fieldAllowedChannels);
        }



    }
    private Boolean saveServerProfile(){
        String fName="[saveServerProfile]";
        logger.info(fName);
        gGlobal.putGuildSettings(gGuild,gBDSMCommands.diaper.gProfile);
        if(gBDSMCommands.diaper.gProfile.saveProfile()){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }

    ////////////////////////
}

    public diaperInteractive(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        Runnable r = new runUtility(global,guild,user,command);
        new Thread(r).start();
    }
    protected class runUtility implements Runnable {
        String cName = "[runReset]";
        lcGlobalHelper gGlobal;
        Guild gGuild;
        User gUser;
        String gCommand;

        public runUtility(lcGlobalHelper global, Guild g, User user,String command) {
            logger.info(".run build");
            gGlobal = global;
            gUser=user;
            gGuild = g;
            gCommand=command;

        }
        lcJSONUserProfile gUserProfile;
        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                if(gCommand.equalsIgnoreCase("reset")){
                    getProfile();
                    gUserProfile.jsonObject =new JSONObject();
                    saveProfile();
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String profileName="diaperinteraction";
        private Boolean getProfile(){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
            gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
            if(gUserProfile.getProfile(gTable)){
                logger.info(fName + ".has sql entry");  return true;
            }
            return false;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            logger.info(fName);
            gUserProfile.isUpdated=true;
            logger.info(fName + ".json="+gUserProfile.jsonObject.toString());
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(gTable)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        
    }
}
