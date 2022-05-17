package nsfw.diaper;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import org.apache.log4j.Logger;
import restraints.in.iRdStr;
import restraints.in.iRestraints;
import restraints.models.entityRDUserProfile;
import restraints.models.lcBDSMGuildProfiles;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class dExtension {
    Logger loggerExt = Logger.getLogger("diaperExtension");

    lcGlobalHelper global;

    protected dExtension(){
       
    }
    public CommandEvent gCommandEvent;public SlashCommandEvent gSlashCommandEvent;
    public GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
    public String gRawForward="";public boolean gIsForward=false, gIsOverride =false;
    public Guild gGuild; public User gUser;
    public Member gMember; public TextChannel gTextChannel;public Member gTarget;
    public String gArgs="",gForward=""; public String[] gItems=null;

    public  Message gMessage;
    public EventWaiter gWaiter;
    public String gTitle="";
    lcBDSMGuildProfiles gBDSMCommands;

    public void launch(lcGlobalHelper global,CommandEvent ev){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,boolean isForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=isForward;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,boolean isForward,Member target){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=isForward;
        gTarget=target;if(gTarget!=null) loggerExt.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,boolean isForward,String strForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        gIsForward=isForward;gForward=strForward;
        updateIsAdult();
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,String strForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,CommandEvent ev,String strForward,Member target){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        gTarget=target;if(gTarget!=null) loggerExt.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev,boolean isForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=isForward;
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev,boolean isForward,Member target){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=isForward;
        gTarget=target;if(gTarget!=null) loggerExt.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev,boolean isForward,String strForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        gIsForward=isForward;gForward=strForward;
        updateIsAdult();
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev,String strForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,String title,CommandEvent ev,String strForward,Member target){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCommandEvent =ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gWaiter= this.global.waiter;
        gTextChannel = gCommandEvent.getTextChannel();
        loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs= gCommandEvent.getArgs();
        gMessage=ev.getMessage();
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        gTarget=target;if(gTarget!=null) loggerExt.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,InteractionHook interactionHook,String title,String strForward){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCurrentInteractionHook=interactionHook;
        Interaction interaction=gCurrentInteractionHook.getInteraction();
        gUser = interaction.getUser();gMember= interaction.getMember();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        if(interaction.isFromGuild()){
            gGuild =interaction.getGuild();
            loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = interaction.getTextChannel();
            loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        gWaiter= this.global.waiter;
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
    }
    public void launch(lcGlobalHelper global,InteractionHook interactionHook,String title,String strForward,Member target){
        String fName="[runLocal]";
        loggerExt.info(".run build");
        this.global =global;
        gCurrentInteractionHook=interactionHook;
        Interaction interaction=gCurrentInteractionHook.getInteraction();
        gUser = interaction.getUser();gMember= interaction.getMember();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        if(interaction.isFromGuild()){
            gGuild =interaction.getGuild();
            loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = interaction.getTextChannel();
            loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        gWaiter= this.global.waiter;
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        updateIsAdult();gIsForward=true;gForward=strForward;
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
        gTarget=target;
        loggerExt.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
    }

    public void launch(lcGlobalHelper global,SlashCommandEvent ev,String title){
        String fName="[runLocal]";
        this.global =global;
        gSlashCommandEvent =ev;
        gUser = gSlashCommandEvent.getUser();
        loggerExt.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        gWaiter= this.global.waiter;
        gBDSMCommands =new lcBDSMGuildProfiles(this.global,gGuild);gBDSMCommands.diaper.init();
        gTitle=title;
        messageComponentManager.set(this.global,gTextChannel,gTitle,gUser);
        if(gSlashCommandEvent.isFromGuild()){
            gGuild = gSlashCommandEvent.getGuild();
            gMember=gSlashCommandEvent.getMember();
            loggerExt.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gSlashCommandEvent.getTextChannel();
            loggerExt.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            updateIsAdult();
        }
    }

    entityDiaperUserProfile gNewUserProfile=new entityDiaperUserProfile();
    /*String caretakerID=""; Boolean caretakerAccepted =false;String access="";Boolean diaperLocked=false;
    Boolean wetEnabled=false; int wetChance=0; int wetLevel=0,wetMaxLevel=6;
    Boolean messyEnabled=false; int messyChance=0; int messyLevel=0;
    Boolean diaperOn=false;String diaperType="",diaperName="";
    Boolean suitOn=false;String suitType="",suitName="";*/
    public boolean getProfile(){
        String fName="[getProfile]";
        loggerExt.info(fName);
        if(gNewUserProfile==null||!gNewUserProfile.isProfile()){
            if(!loadedProfile()){
                loggerExt.warn(fName + ".failed to load profile");
                return false;
            }
        }
        return true;
    }
    public boolean loadedProfile(){
        String fName="[loadedProfile]";
        loggerExt.info(fName);
        Member member;
        if(gTarget==null){
            member=gMember;
        }else {
            member=gTarget;
        }
        loggerExt.info(fName + ".member:"+ member.getId()+"|"+member.getUser().getName());
        if(global ==null){
            loggerExt.warn("global is null at 55");
        }
        gNewUserProfile=new entityDiaperUserProfile(global,member);
        return gNewUserProfile.isProfile();
    }
    public boolean loadDUserValues(){
        String fName = "[loadDUserValues]";
        loggerExt.info(fName);
        try {

            /*caretakerID= gNewUserProfile.cCaretaker.getCaretakerID();
            caretakerAccepted= gNewUserProfile.cCaretaker.isAccepted();
            loggerExt.info(fName+"caretakerID="+caretakerID+", caretakerAccepted="+caretakerAccepted);
            diaperLocked= gNewUserProfile.cProfile.isLocked();
            access=gNewUserProfile.cProfile.getAccessAsString();
            loggerExt.info(fName+"diaperLocked="+diaperLocked+", access="+access);
            wetEnabled=gNewUserProfile.cWet.isEnabled();
            wetChance=gNewUserProfile.cWet.getChance();
            wetLevel= gNewUserProfile.cWet.getLevel();
            loggerExt.info(fName+"wetEnabled="+wetEnabled+", wetChance="+wetChance+", wetLevel="+wetLevel);
            messyEnabled= gNewUserProfile.cMessy.isEnabled();
            messyChance=gNewUserProfile.cMessy.getChance();
            messyLevel= gNewUserProfile.cMessy.getLevel();
            loggerExt.info(fName+"messyEnabled="+messyEnabled+", messyChance="+messyChance+", messyLevel="+messyLevel);
            diaperOn=gNewUserProfile.cDIAPER.isEnabled();
            diaperType=gNewUserProfile.cDIAPER.getTypeAsString();diaperName=gNewUserProfile.cDIAPER.getName();
            loggerExt.info(fName+"diaperOn="+diaperOn+", diaperType="+diaperType+", diaperName="+diaperName);
            wetMaxLevel=gNewUserProfile.cDIAPER.getMaxLevel();
            loggerExt.info(fName+" wetMaxLevel="+wetMaxLevel);
            suitOn=gNewUserProfile.cSuit.isEnabled();
            suitType=gNewUserProfile.cSuit.getTypeAsString();suitName=gNewUserProfile.cSuit.getName();
            loggerExt.info(fName+"suitOn="+diaperOn+", suitType="+diaperType+", suitName="+suitName);*/
            return true;
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        loggerExt.info(fName);
        return gNewUserProfile.saveProfile();
    }

    boolean isAdult=false;
    int bdsmRestriction=0;
    private void updateIsAdult(){
        String fName="[updateIsAdult]";
        loggerExt.info(fName);
        if(gTextChannel==null){
            loggerExt.error(fName+"warning, channel is NULL!");
        }else
        if(gTextChannel.isNSFW()){
            loggerExt.info(fName+"channel is nsfw"); isAdult=true; return;
        }
        if(lsGuildHelper.lsIsGuildNSFW(global,gGuild)){
            loggerExt.info(fName+"guild is adult"); isAdult=true; return;
        }
        bdsmRestriction=gBDSMCommands.getBDSMRestriction();
        loggerExt.info(fName+"is safe");
    }

    String gFileMainPath="resources/json/diaper/";
    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    public Message sendEmbed(String title,String desc, Color color){
        String fName="[sendEmbed -values]";
        loggerExt.info(fName + ".executed");
        try {
            return sendEmbed(lsMessageHelper.lsGenerateEmbed(title,desc,color));
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendEmbed(EmbedBuilder embedBuilder){
        String fName="[sendEmbed -embed]";
        loggerExt.info(fName + ".executed");
        try {
            return lsMessageHelper.lsSendQuickEmbedMessageWithDeleteResponse(global,gMember,gTextChannel, embedBuilder);
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String stringReplacer(String source){
        String fName="[stringReplacer]";
        loggerExt.info(fName + ".executed");
        try {
			String lockedBy="",user="",target="";
            lockedBy= gNewUserProfile.getUserWhoLockedPet();
            user=gMember.getAsMention();
            target=gNewUserProfile.getMember().getAsMention();
            return stringReplacer(source,user,target,lockedBy);
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    public String stringReplacer(Member author, String source){
        String fName="[stringReplacer]";
        Logger logger = Logger.getLogger(getClass());
        loggerExt.info(fName + ".executed");
        try {
			String lockedBy="",user="",target="";
            lockedBy= gNewUserProfile.getUserWhoLockedPet();
            if(author!=null)user=author.getAsMention();
            target=gNewUserProfile.getMember().getAsMention();
            return stringReplacer(source,user,target,lockedBy);
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
	public String stringReplacer(String source,String user,String target,String lockedBy){
        String fName="[stringReplacer]";
        Logger logger = Logger.getLogger(getClass());
        loggerExt.info(fName + ".executed");
        try {
			if(user!=null&&!user.isBlank())
			    source=source.replaceAll("!USER",user);
			else  source=source.replaceAll("!USER","!USER_NULL");
			if(target!=null&&!target.isBlank())
			    source=source.replaceAll("!TARGET",target).replaceAll("!WEARER",target);
			else source=source.replaceAll("!TARGET","!TARGET_NULL").replaceAll("!WEARER","!WEARER_NULL");
			if(lockedBy==null) source=source.replaceAll("!LOCKER",lockedBy);
			else source=source.replaceAll("!LOCKER","!LOCKER_NULL");
            return  source;
        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }

    public InteractionHook  gCurrentInteractionHook,gComponentInteractionHook,gSlashInteractionHook;
    public void deferReplySet(SelectionMenuEvent selectionMenuEvent){
        String fName="[deferReplySet -selectionMenuEvent]";
        try{
            loggerExt.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(selectionMenuEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(ButtonClickEvent buttonClickEvent){
        String fName="[deferReplySet -buttonClickEvent]";
        try{
            loggerExt.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(buttonClickEvent,true);
            if(interactionHook!=null){
                gComponentInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void deferReplySet(SlashCommandEvent slashCommandEvent){
        String fName="[deferReplySet -slashCommandEvent]";
        try{
            loggerExt.info(fName);
            InteractionHook interactionHook=lsMessageHelper.lsDeferReply(slashCommandEvent,true);
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyCheckDm(){
        String fName="[slashReplyPleaseWait]";
        try{
            loggerExt.info(fName);
            if(gSlashCommandEvent==null){loggerExt.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){loggerExt.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strOpeningDMMenu).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void slashReplyPleaseWait(){
        String fName="[slashReplyPleaseWait]";
        try{
            loggerExt.info(fName);
            if(gSlashCommandEvent==null){loggerExt.warn(fName+" not a slash as gSlashCommandEvent is null"); return;}
            if(gSlashInteractionHook !=null){loggerExt.warn(fName+" gInteractionHook already defined");return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            if(interactionHook!=null){
                gSlashInteractionHook=interactionHook;
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder, java.util.List<ActionRow> actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendPrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description,Color color){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendPrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
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
            loggerExt.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, java.util.List<ActionRow> actionRows){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
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
            loggerExt.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendPrivateEmbed]";
        try{
            loggerExt.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendPrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder, java.util.List<ActionRow> actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook,actionRows);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendOrUpdatePrivateEmbed(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            loggerExt.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Message sendOrUpdatePrivateEmbed(EmbedBuilder embedBuilder,InteractionHook interactionHook, List<ActionRow> actionRows){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder,actionRows);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder,actionRows);
            }
            loggerExt.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color){
        String fName="[sendOrUpdatePrivateEmbed]";
        try{
            loggerExt.info(fName);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(title,description,color,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(String title, String description,Color color,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExt.info(fName);
            EmbedBuilder embedBuilder=lsMessageHelper.lsGenerateEmbed(title,description,color);
            return  sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(embedBuilder,interactionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExt.info(fName);
            return  sendOrUpdatePrivateEmbed(embedBuilder,gCurrentInteractionHook);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(EmbedBuilder embedBuilder,InteractionHook interactionHook){
        String fName="[sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction]";
        try{
            loggerExt.info(fName);
            Message message=null;
            if(interactionHook!=null){
                message=lsMessageHelper.lsEditOriginEmbed(interactionHook,embedBuilder);
            }
            if(message==null){
                message=lsMessageHelper.lsSendEmbed(gUser,embedBuilder);
            }
            loggerExt.info(fName+"message="+message.getIdLong());
            return  message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message removeAction(Message message){
        String fName="[removeAction]";
        try{
            loggerExt.info(fName);
            message.clearReactions().complete();
            message=message.editMessageComponents().complete();
            return message;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String textAdd(String source,String add){
        String fName="[stextAdd]";
        try{
            loggerExt.info(fName);
            if(!source.isBlank())source+="\n"+add;
            return  source;
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
}
