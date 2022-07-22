package restraints.models;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.helper.lcSendMessageHelper;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.applicationcommand.lcApplicationInteractionMessage;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMessageHelper;
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
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;
import restraints.in.iRdStr;
import restraints.in.iRestraints;
import restraints.models.entity.entityRDUserProfile;
import restraints.rdAuth;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class rdExtension {
    Logger logger = Logger.getLogger("rdExt");
    
    lcGlobalHelper gGlobal;

    protected  rdExtension(){
       
    }
    public CommandEvent gCommandEvent;
    public GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
    public String gRawForward="";public boolean gIsForward=false, gIsOverride =false;
    public Guild gGuild; public User gUser;
    public Member gMember; public TextChannel gTextChannel;public Member gTarget;
    public String gArgs=""; public String[] gItems=null;
    public lcBDSMGuildProfiles gBDSMCommands;
    public  Message gMessage;
    public  SlashCommandEvent gSlashCommandEvent;
    public InteractionHook gSlashInteractionHook =null;
    public void launch(lcGlobalHelper global,CommandEvent ev){
        String fName="[runLocal]";
        logger.info(".run build CommandEvent");
        try {
            gGlobal=global;
            gCommandEvent =ev;
            gUser = ev.getAuthor();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(gCommandEvent.isFromType(ChannelType.TEXT)){
                gMember= ev.getMember(); gGuild = ev.getGuild();
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = ev.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gArgs= ev.getArgs();
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gMessage=ev.getMessage();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());

        }
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,boolean isForward,String forward){
        String fName="[runLocal]";
        logger.info(".run build CommandEvent forward");
        try {
            gGlobal=global;
            gCommandEvent =ev;gIsForward=isForward;gRawForward=forward;
            gUser = ev.getAuthor();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromType(ChannelType.TEXT)){
                gMember= ev.getMember();gGuild = ev.getGuild();
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = ev.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gArgs= ev.getArgs();
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gMessage=ev.getMessage();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,boolean isForward,String forward,Member target){
        String fName="[runLocal]";
        logger.info(".run build CommandEvent target");
        try {
            gGlobal=global;
            gCommandEvent =ev;gIsForward=isForward;gRawForward=forward;
            gUser = ev.getAuthor();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromType(ChannelType.TEXT)){
                gMember= ev.getMember(); gGuild = ev.getGuild();
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = ev.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gArgs= ev.getArgs();
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gTarget=target;
            logger.info(fName + ".gTarget:" + gTarget.getId());
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gMessage=ev.getMessage();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,String forward,Guild guild,TextChannel textChannel,Member author, Member target){
        String fName="[launch]";
        logger.info(".run build forward target");
        try {
            gGlobal=global;
            gRawForward=forward;gIsForward=true;
            gUser = author.getUser();gMember= author;
            gGuild = guild;
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gTextChannel = textChannel;
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gTarget=target;
            if(gTarget!=null)logger.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gTarget=target;
            logger.info(fName + ".gTarget:" + gTarget.getId());
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    @Deprecated
    @DeprecatedSince("11/24/21")
    public void launch(lcGlobalHelper global,lcSlashInteractionReceive ev){
        String fName="[runLocal]";
        logger.info(".run build lcSlashInteractionReceive");
        try {
            gGlobal=global;
            gInteractionCreate = ev;
            gUser = ev.getUser();gMember=ev.getMember();
            gGuild = ev.getGuild();
            gTextChannel =ev.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,GuildMessageReactionAddEvent ev){
        logger.info(".run build GuildMessageReactionAddEvent");String fName="[runLocal]";
        try {
            gGlobal=global;
            gGuildMessageReactionAddEvent=ev;
            gUser = ev.getUser();gMember=ev.getMember();
            gGuild = ev.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gTextChannel = ev.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gMessage=lsMessageHelper.lsGetMessageById(gTextChannel,ev.getMessageId());
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[runLocal]";
        logger.info(".run build SlashCommandEvent");
        try {
            gGlobal=global;
            gSlashCommandEvent = ev;
            gUser = ev.getUser();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()) {
                gGuild = ev.getGuild();
                gTextChannel = ev.getTextChannel();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                gMember = ev.getMember();
            }
            gWaiter=gGlobal.waiter;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev){
        String fName="[runLocal]";
        logger.info(".run build lUserCommand");
        try {
            gGlobal=global;
            glUsercommand = ev;
            gUser = ev.getUser();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                gMember=ev.getMember();
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,Guild guild,User user,String command){
        String fName="[runLocal]";
        logger.info(".run build command");
        try {
            gGlobal=global;
            gUser = user;
            gGuild = guild;
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gWaiter=gGlobal.waiter;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
            gRawForward=command;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
        String fName="[runLocal]";
        logger.info(".run build lUserCommand");
        try {
            gGlobal=global;
            glUsercommand =ev;gIsForward=isForward;gRawForward=forward;
            gUser = ev.getUser();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                gMember=ev.getMember();
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
        String fName="[runLocal]";
        logger.info(".run build lUserCommand target");
        try {
            gGlobal=global;
            glUsercommand =ev;gIsForward=isForward;gRawForward=forward;
            gUser = ev.getUser();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                gMember=ev.getMember();
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
            gWaiter=gGlobal.waiter;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gTarget=target;
            logger.info(fName + ".gTarget:" + gTarget.getId());
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,InteractionHook interactionHook,boolean isForward,String forward){
        String fName="[runLocal]";
        logger.info(".run build InteractionHook");
        try {
            gGlobal=global;
            gCurrentInteractionHook=interactionHook;
            logger.info(fName+".interactionHook.Interaction.id="+interactionHook.getInteraction().getId());
            Interaction interaction=interactionHook.getInteraction();
            if(interaction!=null){
                gUser = interaction.getUser();
                logger.info(fName + ".gUser:" + gUser.getId());
                gMember=interaction.getMember();
                logger.info(fName + ".gMember:"+gMember.getId());
                if(interaction.isFromGuild()){
                    gGuild =interaction.getGuild();
                    logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                    gTextChannel = interaction.getTextChannel();
                    logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                }
            }
            gWaiter=gGlobal.waiter;
            gRawForward=forward;gIsForward=isForward;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    public void launch(lcGlobalHelper global,InteractionHook interactionHook,boolean isForward,String forward,Member target){
        String fName="[runLocal]";
        logger.info(".run build InteractionHook target");
        try {
            gGlobal=global;
            gIsForward=isForward;gRawForward=forward;
            gCurrentInteractionHook=interactionHook;
            Interaction interaction=interactionHook.getInteraction();
            if(interaction!=null){
                gUser = interaction.getUser();
                logger.info(fName + ".gUser:" + gUser.getId());
                gMember=interaction.getMember();
                logger.info(fName + ".gMember:"+gMember.getId());
                if(interaction.isFromGuild()){
                    gGuild =interaction.getGuild();
                    logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                    gTextChannel = interaction.getTextChannel();
                    logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                }
            }
            gWaiter=gGlobal.waiter;
            gRawForward=forward;gIsForward=isForward;
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gTarget=target;
            logger.info(fName + ".gTarget:" + gTarget.getId());
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
        }
    }
    lcApplicationInteractionReceive.lMessageCommand gMessageCommand;
    public void launch(lcGlobalHelper global,lcApplicationInteractionReceive.lMessageCommand ev){
        String fName="[runLocal]";
        logger.info(".run build lcApplicationInteractionReceive.lMessageCommand");
        try {
            gGlobal=global;
            gMessageCommand =ev;
            gUser = gMessageCommand.getUser();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(gMessageCommand.isFromGuild()){
                gGuild = gMessageCommand.getGuild();
                gTextChannel = gMessageCommand.getTextChannel();
                gMember= gMessageCommand.getMember();
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }else{
                logger.warn(fName + "not from guild");
            }
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
            gBDSMCommands.restraints.init(); gBDSMCommands.collar.init();
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
    public boolean isAdult=false;  public int bdsmRestriction=0;
    public void updateIsAdult(){
        String fName="[updateIsAdult]";
        logger.info(fName);
        if(gTextChannel.isNSFW()){
            logger.info(fName+"channel is nsfw"); isAdult=true; return;
        }
        if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
            logger.info(fName+"guild is adult"); isAdult=true; return;
        }
        bdsmRestriction=gBDSMCommands.getBDSMRestriction();
        logger.info(fName+"is safe");
    }
    public lcSlashInteractionReceive gInteractionCreate;
    public lcApplicationInteractionReceive.lUserCommand glUsercommand;
    public lcApplicationInteractionMessage glInteractionResponse;
    public  void blocked(){
        String fName = "[blocked]";
        EmbedBuilder embedBuilder=lsMessageHelper.lsErrorEmbed(sRTitle,"Require NSFW channel or server.", llColors.llColorRed);
        sendPrivateEmbed(embedBuilder);
        logger.info(fName);
    }
    public  void blockedDM(){
        String fName = "[blocked]";
        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Require NSFW channel or server.", llColors.llColorRed);
        logger.info(fName);
    }

    public  Member gProfileMember=null;
    public entityRDUserProfile gNewUserProfile=new entityRDUserProfile();
    public Boolean getProfile(){
        String fName="[getProfile]";
        logger.info(fName);
        if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
            return  getProfile(gTarget);
        }else
        if(gTarget!=null&&gTarget.getIdLong()==gMember.getIdLong()){
            gTarget=null;
            return getProfile(gMember);
        }else{
            return getProfile(gMember);
        }
    }
    public Boolean getProfile(Member member){
        String fName="[getProfile]";
        logger.info(fName);
        if(member==null)logger.error("member is null!");
        logger.info(fName + ".member:"+ member.getId());
        if(gNewUserProfile!=null&&gNewUserProfile.getMember()==member&&gNewUserProfile.gUserProfile.isProfile()){
            logger.info(fName + ".same member>skip");
            logger.info(fName + ".json="+gNewUserProfile.gUserProfile.jsonObject.toString());
            gProfileMember=member;
            return true;
        }
        if(gNewUserProfile.build(gGlobal,member,gBDSMCommands)){
            gProfileMember=member;
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
    public Boolean saveJSON(){
        String fName="[saveJSON]";
        logger.info(fName);
        boolean result=gNewUserProfile.saveJSON();
        if(result)logger.info(fName + ".result=true");
        else logger.warn(fName + ".result=false");
        return result;
    }



    public lcBasicFeatureControl gBasicFeatureControl=new lcBasicFeatureControl();
    public void loadBasic(String name){
        String fName="[runLocal]";
        gBasicFeatureControl=new lcBasicFeatureControl(gGuild,name,gGlobal);
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
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
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

    public boolean checkIFAllowed2UseCommand0() {
        String fName = "[checkIFAllowed2UseCommand0]";
        try {
            if(!gBDSMCommands.restraints.getEnable()) {
                logger.info(fName + "its disabled");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                logger.info(fName+"its not allowed by channel");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                logger.info(fName+"its not allowed by roles");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                return false;
            }
            return  true;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return true;
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


    public boolean isAskMode() {
        String fName = "[isAskMode]";
        try {
            if(iRestraints.isSAccessAsk(gNewUserProfile.gUserProfile)&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                logger.info(fName + ".ask mode");
            }
            return true;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            return false;
        }
    }
    public boolean toMessageSelfCommandAction=true;

    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();

    public AskHandlingHelper gAskHandlingHelper =new AskHandlingHelper();
    public class AskHandlingHelper {
        String cName="[AskOwnerAsWell]";
        public AskHandlingHelper(){initStrMap();}
        Message messageOwner=null, messageAuth =null,messageTarget=null;
        User userOwner=null,userAuth=null,userTarget=null;
        Member memberOwner=null,memberAuth=null,memberTarget=null;
        Map<String, String>strMap=new HashMap<>();
        public  final String user_ask_info="user_ask_info",target_ask="target_ask",owner_ask="owner_ask",
                user_approve_byowner="user_approve_byowner",owner_approve="owner_approve",
                user_approve_bytarget="user_approve_bytarget",target_approve="target_approve",owner_approve_bytarget="owner_approve_bytarget",
                user_rejected_byowner="user_rejected_byowner",owner_rejected="owner_rejected",target_rejected_byowner="target_rejected_byowner",
                user_rejected_bytarget="user_rejected_bytarget",target_rejected="target_rejected",owner_rejected_bytarget="owner_rejected_bytarget",
                user_rejected_timeout="user_rejected_timeout";
        public AskHandlingHelper initStrMap() {
            String fName = "[initStrMap]";
            try {
                strMap.put(user_ask_info,"!TARGET"+iRestraints.accessisaskwait1);
                strMap.put(user_approve_byowner,iRestraints.strAskApprovedByOwner1);strMap.put(owner_approve,iRestraints.strAskApprovedByOwner3);
                strMap.put(owner_approve_bytarget,iRestraints.strAskApprovedByTarget3);
                strMap.put(user_rejected_byowner,iRestraints.strAskRejectedByOwner1);strMap.put(owner_rejected,iRestraints.strAskRejectedByOwner3);strMap.put(target_rejected_byowner,iRestraints.strAskRejectedByOwner2);
                strMap.put(user_rejected_bytarget,"!TARGET"+iRestraints.rejectedyourrequestofUpdate);strMap.put(owner_rejected_bytarget,iRestraints.strAskRejectedByTarget3);strMap.put(target_rejected,iRestraints.yourejectedtheirrequest);
                strMap.put(user_rejected_timeout,iRestraints.strWaitingPeriodImeout);
                return  this;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getStr(String key) {
            String fName = "[getStr]";
            try {
                logger.info(cName+fName+"key="+key);
                String value=strMap.get(key);
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public AskHandlingHelper setStr(String key,String value) {
            String fName = "[setStr]";
            try {
                logger.info(cName+fName+"key="+key+", value="+value);
                strMap.put(key,value);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isAsk() {
            String fName = "[isAsk]";
            try {
                memberTarget=gNewUserProfile.getMember();userTarget=memberTarget.getUser();
                userAuth=gUser;memberAuth=gMember;
                boolean value=false;
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(userAuth)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(userAuth)&&!gIsOverride){
                    logger.info(cName+fName + ".is ask");
                    value=true;
                }
                logger.info(cName+fName + ".value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                if(gTextChannel!=null&&gUser!=null&&sRTitle!=null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
                return false;
            }
        }
        String gAskFlePath=iRestraints.gFileMainPath+"ask.json";
        public boolean doAsk(Runnable action) {
            String fName = "[doAsk]";
            try {
                logger.info(cName+fName + ".do ask");
                memberTarget=gNewUserProfile.getMember();userTarget=memberTarget.getUser();
                userAuth=gUser;memberAuth=gMember;
                messageComponentManager.loadMessageComponents(gAskFlePath);
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                messageAuth =lsMessageHelper.lsSendQuickEmbedMessageResponse(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_ask_info)), llColors.llColorPurple2);
                //messageTarget=lsMessageHelper.lsSendQuickEmbedMessageResponse(userTarget,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_ask)),llColors.llColorPurple2);
                EmbedBuilder embedBuilderTarget=new EmbedBuilder(), embedBuilderOwner=new EmbedBuilder();
                embedBuilderTarget.setTitle(sRTitle).setColor(llColors.llColorPurple2);
                embedBuilderTarget.setDescription(iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_ask)));
                messageTarget=userTarget.openPrivateChannel().complete().sendMessageEmbeds(embedBuilderTarget.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                lsMessageHelper.lsMessageAddReactions(messageTarget,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYes));
                lsMessageHelper.lsMessageAddReactions(messageTarget,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo2));
                if(gNewUserProfile.isOwnerAskedAsWell()){
                    try {
                        embedBuilderOwner.setTitle(sRTitle).setColor(llColors.llColorPurple2);
                        userOwner=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                        embedBuilderOwner.setDescription(iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_ask)));
                        //messageOwner=lsMessageHelper.lsSendQuickEmbedMessageResponse(userOwner,sRTitle, iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_ask)), llColors.llColorPurple2);
                        messageOwner=userOwner.openPrivateChannel().complete().sendMessageEmbeds(embedBuilderOwner.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYes));
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo2));
                    } catch (Exception e) {
                        logger.error(cName+fName+"exception:"+e);
                        logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        if(gTextChannel!=null&&gUser!=null&&sRTitle!=null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
                    }
                }
                doAskListener(action);
                return true;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                if(gTextChannel!=null&&gUser!=null&&sRTitle!=null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
                return false;
            }
        }
        private  void doAskListener(Runnable action) {
            String fName = "[doAskListener]";
            try {
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (
                                (e.getMessageIdLong()==messageTarget.getIdLong()&&e.getUserIdLong()==userTarget.getIdLong())
                                        ||
                                        (messageOwner!=null&&userOwner!=null&&e.getMessageIdLong()==messageOwner.getIdLong()&&e.getUserIdLong()==userOwner.getIdLong())
                        ),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(cName+fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))||name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYes))) {
                                    logger.info(cName+fName + ".approved");
                                    action.run();
                                    if(userOwner!=null&&e.getUserIdLong()==userOwner.getIdLong()){
                                        logger.info(cName+fName + ".owner");
                                        lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_approve_byowner)),llColors.llColorGreen1);
                                        lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_approve)),llColors.llColorGreen1);
                                    }else{
                                        logger.info(cName+fName + ".user");
                                        if(messageOwner!=null&&userOwner!=null)lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_approve_bytarget)),llColors.llColorGreen1);
                                    }

                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))||name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo2))){
                                    logger.info(cName+fName + ".reject");
                                    if(userOwner!=null&&e.getUserIdLong()==userOwner.getIdLong()){
                                        logger.info(cName+fName + ".owner");
                                        lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_rejected_byowner)), llColors.llColorRed_Cinnabar);
                                        lsMessageHelper.lsSendQuickEmbedMessage(userTarget,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_rejected)),llColors.llColorRed_Cinnabar);
                                        lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_rejected_byowner)),llColors.llColorRed_Cinnabar);
                                    }else{
                                        logger.info(cName+fName + ".user");
                                        lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_rejected_bytarget)), llColors.llColorRed_Cinnabar);
                                        lsMessageHelper.lsSendQuickEmbedMessage(userTarget,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_rejected)),llColors.llColorRed_Cinnabar);
                                        if(messageOwner !=null&&userOwner!=null)lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_rejected_bytarget)),llColors.llColorRed_Cinnabar);
                                    }
                                }
                                lsMessageHelper.lsMessageDelete(messageTarget);lsMessageHelper.lsMessageDelete(messageAuth);
                                if(messageOwner !=null)lsMessageHelper.lsMessageDelete(messageOwner);
                            }catch (Exception e3){
                                logger.error(cName+fName + ".exception=" + e3);
                                logger.error(cName+fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(messageTarget);lsMessageHelper.lsMessageDelete(messageAuth);
                                if(messageOwner !=null)lsMessageHelper.lsMessageDelete(messageOwner);
                            }
                        },iRestraints.intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            logger.info(cName+fName + ".timeout");
                            /*lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_rejected_timeout)), llColors.llColorRed_Cinnabar);*/
                            lsMessageHelper.lsMessageDelete(messageTarget);lsMessageHelper.lsMessageDelete(messageAuth);
                            if(messageOwner !=null)lsMessageHelper.lsMessageDelete(messageOwner);
                        });
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (
                                (e.getMessageIdLong()==messageTarget.getIdLong()&&e.getUser().getIdLong()==userTarget.getIdLong())
                                        ||
                                        (messageOwner!=null&&userOwner!=null&&e.getMessageIdLong()==messageOwner.getIdLong()&&e.getUser().getIdLong()==userOwner.getIdLong())
                                ),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                lsMessageHelper.lsMessageDelete(messageTarget);lsMessageHelper.lsMessageDelete(messageAuth);
                                if(messageOwner !=null)lsMessageHelper.lsMessageDelete(messageOwner);
                                switch (id){
                                    case lsUnicodeEmotes.aliasYes:case lsUnicodeEmotes.aliasGreenCircle:case lsUnicodeEmotes.aliasGreenApple:
                                        logger.info(cName+fName + ".approved");
                                        action.run();
                                        if(userOwner!=null&&e.getUser().getIdLong()==userOwner.getIdLong()){
                                            logger.info(cName+fName + ".owner");
                                            lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_approve_byowner)),llColors.llColorGreen1);
                                            lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_approve)),llColors.llColorGreen1);
                                        }else{
                                            logger.info(cName+fName + ".user");
                                            if(messageOwner!=null&&userOwner!=null)lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_approve_bytarget)),llColors.llColorGreen1);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasNo: case lsUnicodeEmotes.aliasNo2:case lsUnicodeEmotes.aliasRedCircle:case lsUnicodeEmotes.aliasApple:
                                        logger.info(cName+fName + ".rejected");
                                        if(userOwner!=null&&e.getUser().getIdLong()==userOwner.getIdLong()){
                                            logger.info(cName+fName + ".owner");
                                            lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_rejected_byowner)), llColors.llColorRed_Cinnabar);
                                            lsMessageHelper.lsSendQuickEmbedMessage(userTarget,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_rejected)),llColors.llColorRed_Cinnabar);
                                            lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_rejected_byowner)),llColors.llColorRed_Cinnabar);
                                        }else{
                                            logger.info(cName+fName + ".user");
                                            lsMessageHelper.lsSendQuickEmbedMessage(userAuth,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(user_rejected_bytarget)), llColors.llColorRed_Cinnabar);
                                            lsMessageHelper.lsSendQuickEmbedMessage(userTarget,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(target_rejected)),llColors.llColorRed_Cinnabar);
                                            if(messageOwner !=null&&userOwner!=null)lsMessageHelper.lsSendQuickEmbedMessage(userOwner,sRTitle,iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,memberAuth,getStr(owner_rejected_bytarget)),llColors.llColorRed_Cinnabar);
                                        }
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(cName+fName + ".exception=" + e3);
                                logger.error(cName+fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(messageTarget);lsMessageHelper.lsMessageDelete(messageAuth);
                                if(messageOwner !=null)lsMessageHelper.lsMessageDelete(messageOwner);
                            }
                        },iRestraints.intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                if(gTextChannel!=null&&gUser!=null&&sRTitle!=null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
    }

    public ModuleSoloRedHelper gmoduleSoloRedHelper=new ModuleSoloRedHelper();

    public class ModuleSoloRedHelper implements llMessageHelper{
        public  ModuleSoloRedHelper(){}
        String gModulesRedSafewordMenuPath=iRestraints.gFileMainPath+"safeword.json";
        public boolean overrideYes=false;
        public String embedDescriptionAsk="", embedDescriptionYes="",embedDescriptionNo="";
        public Color colorAsk=llColors.llColorBlue1,colorYes=llColors.llColorGreen2,colorNo=llColors.llColorRed_Barn;
        public Runnable action=null;
        public long timeOutValue =5;
        public  TimeUnit timeOutUnit=TimeUnit.MINUTES;
        public void doAsk(String descriptionAsk,String descriptionYes,Runnable action){
            String fName="[doAsk]";
            logger.info(fName);
            try {
                embedDescriptionAsk=descriptionAsk;embedDescriptionYes=descriptionYes;
                this.action=action;
                doAsk();
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }

        }
        private void doAsk(){
            String fName="[doAsk]";
            logger.info(fName);
            try {
                logger.info(fName+"overrideYes="+overrideYes+", embedDescriptionAsk="+embedDescriptionAsk);
                if(!getProfile()){logger.error(fName + ".can't get profile");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Can't get user profile");
                    return;
                }
                if(overrideYes){
                    logger.info(fName + ".do action by override");
                    action.run();
                    saveProfile();
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(colorAsk);
                embed.setDescription(embedDescriptionAsk);
                Message message=null;
                messageComponentManager.loadMessageComponents(gModulesRedSafewordMenuPath);
                try {
                    logger.info(fName+"component.post="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message= llSendMessageResponse(gUser,embed);
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
                rRedListener(message);
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }

        }
        private void rRedListener(Message message){
            String fName="[rRedListener]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasUnlock)){
                                logger.info(fName + ".do action by buttton press");
                                respondYes();
                            };
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    }, timeOutValue, timeOutUnit, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                logger.info(fName + ".do action by reaction press");
                                respondYes();
                            }
                            llMessageDelete(message);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    }, timeOutValue, timeOutUnit, () -> llMessageDelete(message));
        }
        private void respondYes(){
            String fName="[respondYes]";
            logger.info(fName);
            try {
                logger.info(fName+"do action");
                action.run();
                logger.info(fName+"action done");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(colorYes);
                embed.setDescription(embedDescriptionYes);
                Message message=llSendMessageResponse(gUser,embed);
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }

        }
    }

    public InteractionHook gCurrentInteractionHook,gComponentInteractionHook;
    public lcSendMessageHelper messageHelper=new lcSendMessageHelper();
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
                logger.info(fName+"has interactionHook");
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder,actionRows);
            }
            if(message==null){
                logger.info(fName+"has message");
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
            if(message==null&&toMessageSelfCommandAction){
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
    public String stringReplacer(String source){
        String fName="[stringReplacer]";
        logger.info(fName + ".executed");
        try {
            String lockedBy=gNewUserProfile.cLOCK.getUserWhoLockedPet();
            String user=gMember.getAsMention();
            String target=gNewUserProfile.gUserProfile.getUser().getAsMention();
            source=source.replaceAll("!USER",user).replaceAll("!TARGET",target).replaceAll("!WEARER",target).replaceAll("!LOCKER",lockedBy);
            if(gNewUserProfile.isSpeciesHuman()){
                source=source.replaceAll("paws","hands").replaceAll("paw","hand");
            }
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacer(Member author,String source){
        String fName="[stringReplacer]";
        logger.info(fName + ".executed");
        try {
            String lockedBy=gNewUserProfile.cLOCK.getUserWhoLockedPet();
            String user=author.getAsMention();
            String target=gNewUserProfile.gUserProfile.getUser().getAsMention();
            source=source.replaceAll("!USER",user).replaceAll("!TARGET",target).replaceAll("!WEARER",target).replaceAll("!LOCKER",lockedBy);
            if(gNewUserProfile.isSpeciesHuman()){
                source=source.replaceAll("paws","hands").replaceAll("paw","hand");
            }
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
}
