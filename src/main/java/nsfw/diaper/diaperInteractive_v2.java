package nsfw.diaper;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.in.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diaperInteractive_v2 extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper,iDiaperInteractive {
        Logger logger = Logger.getLogger(getClass()); String cName="[diaperInteractive2]";

      
        lcGlobalHelper gGlobal;


    public diaperInteractive_v2(lcGlobalHelper global){
        String fName="[constructor2]";
        logger.info(fName);
        gGlobal=global;
        if(gGlobal==null){
            logger.warn("global is null at 00");
        }
        this.name = "Interactive_Diaper";
        this.help = "Diaper fun module";
        this.aliases = new String[]{"diaper","diaperplay"};

        this.guildOnly = true;this.category= llCommandCategory_Between;
    }
    public diaperInteractive_v2(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
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

protected class runLocal extends  dExtension implements Runnable  {
    String cName = "[runLocal]";
    public runLocal(CommandEvent ev) {
        logger.info(".run build");
        launch(gGlobal,sRTitle,ev);
    }
    public runLocal(SlashCommandEvent ev){
        loggerExt.info(".run build");String fName="runLocal";
        launch(gGlobal,ev,sRTitle);
    }
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        try {
            if(gSlashCommandEvent!=null){
                logger.info(cName + fName + "slash@");
                rSlashNT();
            }else{
                boolean isInvalidCommand = true;
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    //help("main");
                    dmMenu(); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
                    if(gCommandEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                    logger.info(fName + ".items.size=" + gItems.length);
                    logger.info(fName + ".items[0]=" + gItems[0]);
                    if(gItems[0].equalsIgnoreCase("guild")&&(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                        new diSetup(global,gCommandEvent);isInvalidCommand=false;
                        return;
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
                        if(gItems.length==1){
                            dmMenu();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("set")&&gItems.length>=5){
                            setField(gItems[2],gItems[3],gItems[4]);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("caretaker")||gItems[1].equalsIgnoreCase("auth")){
                            new diSetup(global,gCommandEvent);isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase("setup")){
                            if(gItems.length>=3&&(gItems[2].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Private.getString()))){
                                new diSetup(global,gCommandEvent);
                            }else{
                                new diSetup(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("access")){
                            new diSetup(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("diaper")||gItems[1].equalsIgnoreCase("type")||gItems[1].equalsIgnoreCase("diapers")||gItems[1].equalsIgnoreCase("types")){
                            new diDiaper(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("suit")||gItems[1].equalsIgnoreCase("suits")||gItems[1].equalsIgnoreCase("onesie")||gItems[1].equalsIgnoreCase("onesies")){
                            new diSuit(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("verify")||gItems[1].equalsIgnoreCase("check")){
                            actionVerify();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("spank")){
                            actionSpank();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("tickle")){
                            actionTickle();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("pat")){
                            actionFirmPat();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("swat")){
                            actionSwat();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("change")){
                            actionChange();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("status")||gItems[1].equalsIgnoreCase("display")||gItems[1].equalsIgnoreCase("property")||gItems[1].equalsIgnoreCase("show")){
                            display();isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("lock")){
                            new diSetup(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("unlock")){
                            new diSetup(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[1].equalsIgnoreCase("0")||gItems[1].equalsIgnoreCase("1")||gItems[1].equalsIgnoreCase("2")||gItems[1].equalsIgnoreCase("3")
                                ||gItems[1].equalsIgnoreCase("4")||gItems[1].equalsIgnoreCase("5")||gItems[1].equalsIgnoreCase("6")||gItems[1].equalsIgnoreCase("7")
                                ||gItems[1].equalsIgnoreCase("8")||gItems[1].equalsIgnoreCase("9")) {
                            actionCustom(Integer.parseInt(gItems[1]));
                            isInvalidCommand = false;

                        }else{
                            dmMenu(); isInvalidCommand = false;
                        }
                    }
                    if(isInvalidCommand){
                        if(gItems[0].equalsIgnoreCase("clear")){
                            clearUser();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("clearall")){
                            clearAll();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("set")&&gItems.length >= 4){
                            setField(gItems[1],gItems[2],gItems[3]);isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("help")){
                            if(gItems.length>=2&&(gItems[1].equalsIgnoreCase("main")||gItems[1].equalsIgnoreCase("setup")||gItems[1].equalsIgnoreCase("caretaker"))){
                                help(gItems[1]);
                            }else{
                                help("main");
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("caretaker")||gItems[0].equalsIgnoreCase("auth")){
                            new diSetup(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("setup")){
                            if(gItems.length>=2&&(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString()))){
                                new diSetup(global,gCommandEvent);
                            }
                            else{
                                new diSetup(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("access")){
                            new diSetup(global,gCommandEvent);isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("diaper")||gItems[0].equalsIgnoreCase("type")||gItems[0].equalsIgnoreCase("diapers")||gItems[0].equalsIgnoreCase("types")){
                            if(iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                new diDiaper(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("suit")||gItems[0].equalsIgnoreCase("suits")||gItems[0].equalsIgnoreCase("onesie")||gItems[0].equalsIgnoreCase("onesies")){
                            if(iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                new diSuit(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("status")||gItems[0].equalsIgnoreCase("display")||gItems[0].equalsIgnoreCase("property")||gItems[0].equalsIgnoreCase("show")){
                            display();isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("verify")||gItems[0].equalsIgnoreCase("check")){
                            actionVerify();isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("spank")){
                            if(iDiaperInteractive.doPrevented2Action2ArmsRestrained(global, gMember)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionSpank();
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("tickle")){
                            if(iDiaperInteractive.doPrevented2Action2ArmsRestrained(global, gMember)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionTickle();
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("pat")){
                            if(iDiaperInteractive.doPrevented2Action2ArmsRestrained(global, gMember)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionFirmPat();
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("swat")){
                            if(iDiaperInteractive.doPrevented2Action2ArmsRestrained(global, gMember)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionSwat();
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("change")){
                            if(iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionChange();
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("pee")){
                            selfWet();isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("mess")){
                            selfMessy();isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("lock")){
                            if(iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                new diSetup(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("unlock")){
                            if(iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                new diSetup(global,gCommandEvent);
                            }
                            isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("0")||gItems[0].equalsIgnoreCase("1")||gItems[0].equalsIgnoreCase("2")||gItems[0].equalsIgnoreCase("3")
                                ||gItems[0].equalsIgnoreCase("4")||gItems[0].equalsIgnoreCase("5")||gItems[0].equalsIgnoreCase("6")||gItems[0].equalsIgnoreCase("7")
                                ||gItems[0].equalsIgnoreCase("8")||gItems[0].equalsIgnoreCase("9")) {
                            actionCustom(Integer.parseInt(gItems[0]));
                            isInvalidCommand = false;

                        }else{
                            dmMenu(); isInvalidCommand = false;
                        }
                    }
                }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gCommandEvent);*/
                if(isInvalidCommand){
                    llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
                }
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
        global.clearUserProfile(profileName,gUser,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void clearAll(){
        String fName="[clearAll]";
        logger.info(fName);
        global.clearUserProfile(profileName,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void display(){
        String fName = "[display]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed( sRTitle, "Failed to load  profile!", llColorRed);return; }

        EmbedBuilder embed=new EmbedBuilder(); embed.setColor(llColorOrange);
        embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s diaper");
        embed.addField("Access",gNewUserProfile.cProfile.getAccessAsString(),true);
        if(!gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()&&gNewUserProfile.cCaretaker.isAccepted()){
            Member caretaker=gGuild.getMemberById(gNewUserProfile.cCaretaker.getCaretakerID());
            if(caretaker==null){
                embed.addField("Caretaker","(not a member)",true);
            }else{
                embed.addField("Caretaker",caretaker.getAsMention(),true);
            }
        }else
        if(!gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()){
            Member caretaker=gGuild.getMemberById(gNewUserProfile.cCaretaker.getCaretakerID());
            if(caretaker==null){
                embed.addField("Caretaker","(not a member)[requirement]",true);
            }else{
                embed.addField("Caretaker",caretaker.getAsMention()+"[requirement]",true);
            }
        }else{
            embed.addField("Caretaker","(N/A)",true);
        }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            embed.addField("Diaper","(not wearing)",true);
        }else{
            embed.addField("Diaper",gNewUserProfile.cDIAPER.getName(),true);
        }
        if(!gNewUserProfile.cSuit.isEnabled()){
            embed.addField("Onesie","(not wearing)",true);
        }else{
            embed.addField("Onesie",gNewUserProfile.cSuit.getTypeAsString(),true);
        }
        if(gNewUserProfile.cProfile.isLocked()){
            /*if(iDiaperInteractive.sIsTimeLocked(gUserProfile,gGlobal)){
                embed.addField("Locked","Yes\nTimelocked: "+ lsUsefullFunctions.displayDuration(iDiaperInteractive.sTimeLockedGetRemaning(gUserProfile)),true);
            }else{
                embed.addField("Locked","Yes",true);
            }*/
            embed.addField("Locked","Needs fixxing",true);
        }else{
            embed.addField("Locked","No",true);
        }

        if(gNewUserProfile.cWet.isEnabled()){
            embed.addField("Wet","Level: "+gNewUserProfile.cWet.getLevel()+"/"+gNewUserProfile.cDIAPER.getMaxLevel()+"\nChance: 1 out of "+gNewUserProfile.cWet.getChance(),false);
        }else{
            embed.addField("Wet","Disabled",false);
        }
        if(gNewUserProfile.cMessy.isEnabled()){
            embed.addField("Messy","Level: "+gNewUserProfile.cMessy.getLevel()+"/6\nChance: 1 out of "+gNewUserProfile.cMessy.getChance(),false);
        }else{
            embed.addField("Messy","Disabled",false);
        }
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle, "Diaper checked", llColorBlue1);
        sendEmbed(embed);
    }


    private void actionVerify(){
        String fName = "[diaperVerify]";
        logger.info(fName);
        if (!getProfile()) {
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to load profile!", llColorRed);return;
        }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't do this action to !WEARER without them having a diaper on."), llColorRed);
            }
            return;
        }
        Boolean getWet=randomChance(gNewUserProfile.cWet.getChance());
        Boolean getMessy=randomChance(gNewUserProfile.cMessy.getChance());
        logger.info(fName+"getWet="+getWet);
        logger.info(fName+"getMessy="+getMessy);
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
        if(gNewUserProfile.cWet.isEnabled()&&getWet&&gNewUserProfile.cMessy.isEnabled()&&getMessy){
            logger.info(fName+"increase:wm");increased=true;
            gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
            gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
        }else
        if(gNewUserProfile.cWet.isEnabled()&&getWet){
            logger.info(fName+"increase:w");increased=true;
            gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
        }else
        if(gNewUserProfile.cMessy.isEnabled()&&getMessy){
            logger.info(fName+"increase:m");;increased=true;
            gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel()="+gNewUserProfile.cWet.getLevel());
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        logger.info(fName+"increased="+increased);
       if(increased){
           if(!saveProfile()){
               sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       }

        if((!gNewUserProfile.cWet.isEnabled()&&!gNewUserProfile.cMessy.isEnabled())||(gNewUserProfile.cWet.getLevel()==0&&gNewUserProfile.cMessy.getLevel()==0)){
            desc += " Its dry like the desert and fresh like a pine forest.";
            color= llColorGreen1;
        }
        if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
            sendOrUpdatePrivateEmbed(sRTitle,"Verified your diaper", llColorRed);
        }else{
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Verified !TARGET's diaper"), llColorRed);
        }

        actionPosting(desc,color);
    }
    private void actionSwat(){
        String fName = "[actionSwat]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle,"Failed to load profile!", llColorRed);return; }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action to "+gNewUserProfile.getMember().getAsMention()+" without them having a diaper on.", llColorRed);
            }
            return;
        }
        loadDUserValues();

        String desc;
        Color color= llColorBlue1;

        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " swats their own butt to quickly check if they diapered.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied",llColorRed);
            return; }
            desc = gUser.getAsMention() + " swats " + gTarget.getAsMention() + " butt to quickly check if they still diapered.";
        }
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel()="+gNewUserProfile.cWet.getLevel());
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        if(gNewUserProfile.cMessy.isEnabled()){
            switch (gNewUserProfile.cMessy.getLevel()) {
                case 1:
                    desc += " It feels not used, yet.";
                    break;
                case 2:
                case 3:
                    color=llColorOrange_Carrot;
                    desc += " A little squishy bulge can be felt behind their butt.";
                    break;
                case 4:
                case 5:
                    color=llColorOrange_Burnt;
                    desc += " That bulge got a bit bigger and more squishy.";
                    break;
                default:
                    color=llColorOrange_Alloy;
                    desc += " The bulge and squishines is very obvious. They stinky butt who needs a diaper change.";
                    break;
            }
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc);
        embed.setColor(color);
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"Swapped",llColorBlue1);
        sendEmbed(embed);
    }
    private void actionFirmPat(){
        String fName = "[actionFirmPat]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle,"Failed to load  profile!", llColorRed);return; }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed( sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed( sRTitle,"Can't do this action to "+gNewUserProfile.getMember().getAsMention()+" without them having a diaper on.", llColorRed);
            }
            return;
        }
        loadDUserValues();

        String desc;
        Color color= llColorBlue1;
        EmbedBuilder embed= new EmbedBuilder();

        if(gTarget==null){
            logger.info(fName+"self");
            embed.setDescription("Can't firm pat yourself!");
            embed.setColor(llColorRed_Salmon);
            sendEmbed(embed);
            sendOrUpdatePrivateEmbed( sRTitle,"Firm pat given",llColorBlue1);
            return;
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied",llColorRed);
                return; }
            desc = gUser.getAsMention() + " firmly pats " + gTarget.getAsMention() + " diapered butt as to remind them that they're";
        }
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel()="+gNewUserProfile.cWet.getLevel());
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        if(gNewUserProfile.cWet.isEnabled()){
            switch (gNewUserProfile.cWet.getLevel()) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3:
                    color=llColorYellow_Canary;
                    desc += " wet";
                    break;
                case 4:
                case 5:
                    color=llColorYellow_Mellow;
                    desc += " soggy";
                    break;
                default:
                    color=llColorYellow_Cyber;
                    desc += " incontinence";
                    break;
            }
        }
        if(gNewUserProfile.cMessy.isEnabled()){
            switch (gNewUserProfile.cMessy.getLevel()) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3:
                    color=llColorOrange_Carrot;
                    desc += " stinky";
                    break;
                default:
                    color=llColorOrange_Burnt;
                    desc += " messy";
                    break;

            }
        }
        desc+=" diapered butt bab.";
        embed.setDescription(desc);
        embed.setColor(color);
        sendEmbed(embed);
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"Firm pat given",llColorBlue1);
    }
    private void actionTickle(){
        String fName = "[actionTickle]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action to "+gNewUserProfile.getMember().getAsMention()+" without them having a diaper on.", llColorRed);
            }
            return;
        }
        loadDUserValues();
        boolean getWet=randomChance(gNewUserProfile.cWet.getChance());
        boolean getMessy=randomChance(gNewUserProfile.cMessy.getChance());
        logger.info(fName+"getWet="+getWet);
        logger.info(fName+"getMessy="+getMessy);
        String desc;
        Color color= llColorBlue1;

        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " starts tickling themselves.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied",llColorRed);
            return; }
            desc = gUser.getAsMention() + " jumps at " + gTarget.getAsMention() + " and starts tickling them.";
        }
        boolean increased=false;
        if(gNewUserProfile.cWet.isEnabled()&&getWet&&gNewUserProfile.cMessy.isEnabled()&&getMessy&&gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()&&gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
            logger.info(fName+"increase:wm");
            increased=true;
            gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
            gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
            desc +=" Oh my, maybe the the little tickling was too much for them! It seems they had both little and big accident.";
        }else
        if(gNewUserProfile.cWet.isEnabled()&&getWet&&gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()){
            logger.info(fName+"increase:w");
            increased=true;
            gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
            desc +=" Oh my, maybe the little tickling was too much for them! It seems they had a little accident.";
        }else
        if(gNewUserProfile.cMessy.isEnabled()&&getMessy&&gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
            logger.info(fName+"increase:m");
            increased=true;
            gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
            desc +=" Oh my, maybe the little tickling was too much for them! It seems they had a big accident.";
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel()="+gNewUserProfile.cWet.getLevel());
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        logger.info(fName+"increased="+increased);
        if(increased){
            if(!saveProfile()){
                sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        }
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"Titled",llColorBlue1);
        actionPosting(desc,color);
    }
    private void actionSpank(){
        String fName = "[actionSpank]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle,"Failed to load  profile!", llColorRed);return; }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action to "+gNewUserProfile.getMember().getAsMention()+" without them having a diaper on.", llColorRed);
            }
            return;
        }
        loadDUserValues();
        Boolean getWet=randomChance(gNewUserProfile.cWet.getChance());
        Boolean getMessy=randomChance(gNewUserProfile.cMessy.getChance());
        logger.info(fName+"getWet="+getWet);
        logger.info(fName+"getMessy="+getMessy);
        String desc;
        Color color= llColorBlue1;
        if(gTarget==null){
            logger.info(fName+"self");
            desc=gUser.getAsMention() + " spanks their own diapered butt.";
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied",llColorRed);return; }
            desc = gUser.getAsMention() + " spanks " + gTarget.getAsMention() + " diapered butt.";
        }
        boolean increased=false;
        if(gNewUserProfile.cWet.isEnabled()&&getWet&&gNewUserProfile.cMessy.isEnabled()&&getMessy&&gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()&&gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
            logger.info(fName+"increase:wm");
            if(gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()){
                gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);increased=true;
            }
            if(gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
                gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);increased=true;
            }
            desc +=" Oh my, maybe the spanking was too much for them! It seems they had both little and big accident.";
        }else
        if(gNewUserProfile.cWet.isEnabled()&&getWet&&gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()){
            logger.info(fName+"increase:w");
            if(gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()){
                gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
                increased=true;
            }
            desc +=" Oh my, maybe the spanking was too much for them! It seems they had a little accident.";
        }else
        if(gNewUserProfile.cMessy.isEnabled()&&getMessy&&gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
            logger.info(fName+"increase:m");
            if(gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
                gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
                increased=true;
            }
            desc +=" Oh my, maybe the spanking was too much for them! It seems they had a big accident.";
        }else{
            logger.info(fName+"increase:n/a");
        }
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel()="+gNewUserProfile.cWet.getLevel());
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        logger.info(fName+"increased="+increased);
        if(increased){
            if(!saveProfile()){
                sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        }
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"Spanked",llColorBlue1);
        actionPosting(desc,color);
    }
    private void actionPosting(String desc, Color color){
        String fName = "[actionPosting]";
        logger.info(fName);
        Boolean getWet=randomChance(gNewUserProfile.cWet.getChance());
        Boolean getMessy=randomChance(gNewUserProfile.cMessy.getChance());
        logger.info(fName+"getWet="+getWet);
        logger.info(fName+"getMessy="+getMessy);
        if(gNewUserProfile.cWet.isEnabled()){
            switch (gNewUserProfile.cDIAPER.getType()) {
                case SwimDiapers:
                    desc += " How embarrassing wetting yourself. This diapers are not designed to hold liquids at all, so all your peeing is wetting the floor visible as clear as day.";
                    break;
                case WhitePlastic:
                    switch (gNewUserProfile.cWet.getLevel()) {
                        case 1:
                            desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                            break;
                        case 2:
                            desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                            break;
                        case 3:
                            desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                            break;
                        case 4:
                            desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                            break;
                        case 5:
                            desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, extra plastic layer preventing accidents, at least for now.";
                            break;
                        case 6:
                            desc += " The diaper starting to get full, hard to move as its slowly inflating like a water balloon.";
                            break;
                        case 7:
                            desc += " The diaper is 90% full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                            break;
                        case 8:
                            desc += " The diaper is full that it starting to leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                            break;
                        default:
                            break;
                    }
                    break;
                case PinkPlastic:
                    switch (gNewUserProfile.cWet.getLevel()) {
                        case 1:
                            desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                            break;
                        case 2:
                            desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                            break;
                        case 3:
                            desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                            break;
                        case 4:
                            desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                            break;
                        case 5:
                            desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, extra plastic layer preventing accidents, at least for now.";
                            break;
                        case 6:
                            desc += " The diaper starting to get full, hard to move as its slowly inflating like a pink water balloon.";
                            break;
                        case 7:
                            desc += " The diaper is 90% full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                            break;
                        case 8:
                            desc += " The diaper is full that it starting to leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is. At least the pink color makes teh wearer look cute.";
                            break;
                        default:
                            break;
                    }
                    break;
                case Peekabu:
                    switch (gNewUserProfile.cWet.getLevel()) {
                        case 2:
                            desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                            break;
                        case 3:
                            desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                            break;
                        case 4:
                            desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                            break;
                        case 5:
                            desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, be glad peekabu diapers can hold double.";
                            break;
                        case 6:
                            desc += " The diaper starting to get full, hard to move as its all puffy.";
                            break;
                        case 7:
                            desc += " The diaper is half full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                            break;
                        case 8:
                            desc += " The diaper is 3/4 full as its not giving up holding all that liquid. The warmth between the legs intensifies as it spreads behind too.";
                            break;
                        case 9:
                            desc += " The diaper is full, it can't hold any more as the warmth reaches behind, fully covering that padded butt.";
                            break;
                        case 10:
                            desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                            break;
                        case 11:
                            desc += " The diaper reached its limit.";
                            break;
                    }
                    break;
                case Briefs4Overnight:
                    switch (gNewUserProfile.cWet.getLevel()) {
                        case 1:
                            desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                            break;
                        case 2:
                            desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                            break;
                        case 3:
                            desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and noisy when moving.";
                            break;
                        case 4:
                            desc += " Their diaper starting to get soggy, much more visible and the warmth can be felt when touched, but still holding it nicely.";
                            break;
                        case 5:
                            desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, be glad night time diapers can hold double.";
                            break;
                        case 6:
                            desc += " The diaper starting to get full, puffy&warm.";
                            break;
                        case 7:
                            desc += " The diaper is half full, inflated as it tries to hold all the liquid.";
                            break;
                        case 8:
                            desc += " The diaper is 3/4 full as its not giving up holding all that liquid. The warmth between the legs intensifies as it spreads behind too.";
                            break;
                        case 9:
                            desc += " The diaper is full, it can't hold any more as the warmth reaches behind, fully covering that padded butt.";
                            break;
                        case 10:
                            desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                            break;
                        case 11:
                            desc += " The diaper reached its limit";
                            break;
                        default:
                            break;
                    }
                    break;
                case BriefsWithPlasticBacking:
                    switch (gNewUserProfile.cWet.getLevel()) {
                        case 1:
                            desc += " The warmth barely spreads. Nobody will notice it.";
                            break;
                        case 2:
                            desc += " The warmth between legs spreads more but its holding it without any issues.";
                            break;
                        case 3:
                            desc += " The warmth between legs spreads even more. Squishy sounds accompanied every time it moves as it reach its limit";
                            break;
                        case 4:
                            desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                            break;
                        default:
                            break;
                    }
                    break;
                case BriefsWithClothBacking:
                    switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely spreads. A visible spot appeared where they had their accident.";
                        break;
                    case 2:
                        desc += " The warmth between legs spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads even more. Squishy sounds accompanied every time it moves as it reach its limit";
                        break;
                    case 4:
                        desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    default:
                        break;
                    }
                    break;
                default:
                    switch (gNewUserProfile.cWet.getLevel()) {
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

        }
        if(gNewUserProfile.cMessy.isEnabled()){
            switch (gNewUserProfile.cMessy.getLevel()) {
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
        if((!gNewUserProfile.cWet.isEnabled()&&!gNewUserProfile.cMessy.isEnabled())||(gNewUserProfile.cWet.getLevel()==0&&gNewUserProfile.cMessy.getLevel()==0)){
            color= llColorGreen1;
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc).setTitle(sRTitle);
        embed.setColor(color);
        sendEmbed(embed);
    }
    private void actionChange(){
        String fName = "[actionChange]";
        logger.info(fName);
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle,"Failed to l profile!", llColorRed);return; }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            }else{
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't do this action to !WEARER without them having a diaper on."), llColorRed);
            }
            return;
        }
        loadDUserValues();
        if(gTarget==null){
            logger.info(fName+"self");
            if(!gIsOverride&&!gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()&&gNewUserProfile.cCaretaker.isAccepted()&&gNewUserProfile.cProfile.isLocked()){
                sendOrUpdatePrivateEmbed(sRTitle,"Only your caretaker can change your diaper to a fresh clean one.", llColorRed); return;
            }
        }else{
            logger.info(fName+"target");
            if(!accessApprove4Action()){ logger.info(fName+"denied");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed);
                return; }
        }
        if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
            sendOrUpdatePrivateEmbed(sRTitle,"Whoops diaper seems to be locked. The padlock needs to be removed before it can be changed to a new fresh one.", llColorRed); return;
        }
        gNewUserProfile.cWet.setLevel(0);
        gNewUserProfile.cMessy.setLevel(0);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(gTarget==null){
            logger.info(fName+"self");
            if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Changed your diaper to a new fresh & dry one.", llColorGreen1);
            sendEmbed(lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("!WEARER changes their diaper to a new fresh & dry one."), llColorGreen1));
        }else{
            logger.info(fName+"target");
            if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Changed !TARGET's diaper to a new fresh & dry one.", llColorGreen1);
            sendEmbed(lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("!USER changes !WEARER's diaper to a new fresh & dry one."), llColorGreen1));
        }
    }
    private void selfWet(){
        String fName = "[selfWet]";
        logger.info(fName);
        String desc;
        if (!getProfile()) { sendOrUpdatePrivateEmbed(sRTitle,"Failed to load  profile!", llColorRed);return; }
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
            sendOrUpdatePrivateEmbed(sRTitle,"Can only be done by the wearer!", llColorRed); return;
        }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            return;
        }
        loadDUserValues();
        logger.info(fName+"gNewUserProfile.cWet.isEnabled()="+gNewUserProfile.cWet.isEnabled());
        logger.info(fName+"gNewUserProfile.cWet.getLevel().begin="+gNewUserProfile.cWet.getLevel());
        if(!gNewUserProfile.cWet.isEnabled()){
            sendOrUpdatePrivateEmbed(sRTitle,"Sorry, wetting is disabled!", llColorRed); return;
        }

        logger.info(fName+"gNewUserProfile.cDIAPER.getMaxLevel()="+gNewUserProfile.cDIAPER.getMaxLevel());
        if(gNewUserProfile.cWet.getLevel()<gNewUserProfile.cDIAPER.getMaxLevel()){
            gNewUserProfile.cWet.setLevel(gNewUserProfile.cWet.getLevel()+1);
            logger.info(fName+"gNewUserProfile.cWet.getLevel().increased="+gNewUserProfile.cWet.getLevel());
        }
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}

        desc=gUser.getAsMention()+ "wets themself. ";
        switch (gNewUserProfile.cDIAPER.getType()){
            case SwimDiapers:
                desc += " How embarrassing wetting yourself. This diapers are not designed to hold liquids at all, so all your peeing is wetting the floor visible as clear as day.";
                break;
            case WhitePlastic:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                        break;
                    case 2:
                        desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                        break;
                    case 4:
                        desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                        break;
                    case 5:
                        desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, extra plastic layer preventing accidents, at least for now.";
                        break;
                    case 6:
                        desc += " The diaper starting to get full, hard to move as its slowly inflating like a water balloon.";
                        break;
                    case 7:
                        desc += " The diaper is 90% full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                        break;
                    case 8:
                        desc += " The diaper is full that it starting to leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    default:
                        break;
                }
                break;
            case PinkPlastic:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                        break;
                    case 2:
                        desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                        break;
                    case 4:
                        desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                        break;
                    case 5:
                        desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, extra plastic layer preventing accidents, at least for now.";
                        break;
                    case 6:
                        desc += " The diaper starting to get full, hard to move as its slowly inflating like a pink water balloon.";
                        break;
                    case 7:
                        desc += " The diaper is 90% full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                        break;
                    case 8:
                        desc += " The diaper is full that it starting to leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is. At least the pink color makes teh wearer look cute.";
                        break;
                    default:
                        break;
                }
                break;
            case Peekabu:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                        break;
                    case 2:
                        desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and anyone putting their hands there can feel the warmth.";
                        break;
                    case 4:
                        desc += " Their diaper starting to get soggy, much more visible but still holding it nicely.";
                        break;
                    case 5:
                        desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, be glad peekabu diapers can hold double.";
                        break;
                    case 6:
                        desc += " The diaper starting to get full, hard to move as its all puffy.";
                        break;
                    case 7:
                        desc += " The diaper is half full, starting to get more hard to move, inflated as it tries to hold all the liquid.";
                        break;
                    case 8:
                        desc += " The diaper is 3/4 full as its not giving up holding all that liquid. The warmth between the legs intensifies as it spreads behind too.";
                        break;
                    case 9:
                        desc += " The diaper is full, it can't hold any more as the warmth reaches behind, fully covering that padded butt.";
                        break;
                    case 10:
                        desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    case 11:
                        desc += " The diaper reached its limit";
                        break;
                    default:
                        break;
                }
                break;
            case Briefs4Overnight:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely and quietly spreads. Nobody will notice it.";
                        break;
                    case 2:
                        desc += " The warmth between legs a bit spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads more but its holding it without any issues. A bit visible between their legs and noisy when moving.";
                        break;
                    case 4:
                        desc += " Their diaper starting to get soggy, much more visible and the warmth can be felt when touched, but still holding it nicely.";
                        break;
                    case 5:
                        desc += " Their diaper soggy, no way for them to hide that fact. Yet the diaper can take more, be glad night time diapers can hold double.";
                        break;
                    case 6:
                        desc += " The diaper starting to get full, puffy&warm.";
                        break;
                    case 7:
                        desc += " The diaper is half full, inflated as it tries to hold all the liquid.";
                        break;
                    case 8:
                        desc += " The diaper is 3/4 full as its not giving up holding all that liquid. The warmth between the legs intensifies as it spreads behind too.";
                        break;
                    case 9:
                        desc += " The diaper is full, it can't hold any more as the warmth reaches behind, fully covering that padded butt.";
                        break;
                    case 10:
                        desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    case 11:
                        desc += " The diaper reached its limit";
                        break;
                    default:
                        break;
                }
                break;
            case BriefsWithPlasticBacking:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely spreads. Nobody will notice it.";
                        break;
                    case 2:
                        desc += " The warmth between legs spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads even more. Squishy sounds accompanied every time it moves as it reach its limit";
                        break;
                    case 4:
                        desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    default:
                        break;
                }
                break;
            case BriefsWithClothBacking:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely spreads. A visible spot appeared where they had their accident.";
                        break;
                    case 2:
                        desc += " The warmth between legs spreads more but its holding it without any issues.";
                        break;
                    case 3:
                        desc += " The warmth between legs spreads even more. Squishy sounds accompanied every time it moves as it reach its limit";
                        break;
                    case 4:
                        desc += " The diaper is so full that it starts leaking, messing their fur down their leg. Visible dripping ensures that everyone can see just how full it is.";
                        break;
                    default:
                        break;
                }
                break;
            default:
                switch (gNewUserProfile.cWet.getLevel()) {
                    case 1:
                        desc += " The warmth barely and quietly spreads. Nobody will notice it.";
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
        }

        if(gNewUserProfile.cDIAPER.getType()==DIAPERTYPE.Pikachu){
            desc+="\nA short burst of zapping can be felt between the legs. After the initial burst it will still continue but weaker and fading away with each seconds.";
        }
        EmbedBuilder embed= new EmbedBuilder();
        embed.setDescription(desc).setTitle(sRTitle);
        embed.setColor(llColorBlue1);
        sendOrUpdatePrivateEmbed(embed);
        sendEmbed(embed);
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"self wetted",llColorBlue1);
    }
    private void selfMessy(){
        String fName = "[selfMessy]";
        logger.info(fName);
        String desc="";
        if (!getProfile()) { sendOrUpdatePrivateEmbed( sRTitle, "Failed to load  profile!", llColorRed);return; }
        if(gTarget==null){
            logger.info(fName+"self");
        }else{
            logger.info(fName+"target");
            sendOrUpdatePrivateEmbed( sRTitle,"Can only be done by the wearer!", llColorRed); return;
        }
        if(!gNewUserProfile.cDIAPER.isEnabled()){
            logger.info(fName + ".no diaper on");
            sendOrUpdatePrivateEmbed( sRTitle,"Can't do this action without first having a diaper on.", llColorRed);
            return;
        }
        loadDUserValues();
        logger.info(fName+"gNewUserProfile.cMessy.isEnabled()="+gNewUserProfile.cMessy.isEnabled());
        logger.info(fName+"gNewUserProfile.cMessy.getLevel()="+gNewUserProfile.cMessy.getLevel());
        if(!gNewUserProfile.cMessy.isEnabled()){
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"Sorry, messing is disabled!", llColorRed); return;
        }
        if(gNewUserProfile.cMessy.getLevel()<maxMessyLevel){
            gNewUserProfile.cMessy.setLevel(gNewUserProfile.cMessy.getLevel()+1);
        }
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed( sRTitle,"Failed to write in Db!", llColorRed); return;}

        desc=gUser.getAsMention()+ "mess themself. ";
        switch (gNewUserProfile.cMessy.getLevel()) {
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
        sendEmbed(embed);
        if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"self messy",llColorBlue1);
    }
    private void actionCustom(int index){
        String fName = "[actionCustom]";
        logger.info(fName);
        try {
            if (!getProfile()) {  sendOrUpdatePrivateEmbed( sRTitle,"Failed to load profile!", llColorRed);return; }
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorYellow_Mellow);
            if(gNewUserProfile.cCustomAction.isEmpty()){
                logger.info(fName+". Custom action list is empty");
                //embed.setDescription("Custom action list is empty");
                //llSendMessage(gTextChannel,embed);
                sendOrUpdatePrivateEmbed( sRTitle,"Custom action list is empty",llColorRed);
                return;
            }else
            if(index<0||index>=gNewUserProfile.cCustomAction.size()){
                logger.info(fName+". Invalid index");
                //embed.setDescription("Invalid index");
                //llSendMessage(gTextChannel,embed);
                sendOrUpdatePrivateEmbed( sRTitle,"Invalid index",llColorRed);
                return;
            }
            else {
                entityCustomActions.ACTION action=gNewUserProfile.cCustomAction.getAction(index);
                logger.info(fName+". ACTION="+ action.getJSON());
                String text="";
                if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                    logger.info(fName+". self");
                    text=action.getText1();

                }else{
                    logger.info(fName+". targeted");
                    text=action.getText2();
                }
                logger.info(fName+". text.before="+ text);
                text=text.replaceAll("!USER",gUser.getAsMention()).replaceAll("!WEARER",gNewUserProfile.getMember().getAsMention());
                if(gNewUserProfile.cCaretaker.getCaretakerID()!=null&&!gNewUserProfile.cCaretaker.getCaretakerID().isBlank()){
                    Member caretaker= lsMemberHelper.lsGetMember(gGuild,gNewUserProfile.cCaretaker.getCaretakerID());
                    if(caretaker!=null){
                        text=text.replaceAll("!CARETAKER",caretaker.getAsMention());
                    }else{
                        text=text.replaceAll("!CARETAKER","<@"+gNewUserProfile.cCaretaker.getCaretakerID()+">");
                    }
                }
                logger.info(fName+". text.after="+ text);
                embed.setDescription( text);
                sendEmbed(embed);
                if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed( sRTitle,"Done",llColorBlue1);
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }



    private void setField(String field, String key, String value){
        String fName = "[setField]";
        logger.info(fName);
        if (!getProfile()) { llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel, sRTitle, "Failed to load "+gNewUserProfile.getMember().getAsMention()+" profile!", llColorRed);return; }
        if(!llMemberIsStaff(gMember)){
            logger.info(fName+"denied");
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel, sRTitle, "Denied! Staff only!", llColorRed);return;}
        logger.info(fName+"field="+field);
        logger.info(fName+"key="+key);
        logger.info(fName+"value="+value);
        if(!field.equals(fieldProfile) && !field.equals(fieldWet) && !field.equals(fieldMessy) && !field.equals(fieldCaretaker)){
            logger.info(fName+"invalid field");
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel, sRTitle, "Invalid field: "+field, llColorRed);return;
        }
        if(!key.equals(keyProfileLocked) || !key.equals(keyProfileAccess) || !key.equals(keyLevel) || !key.equals(keyEnabled) || !key.equals(keyChance) || !key.equals(keyCaretakerId) || !key.equals(keyCaretakerAccepted)){
            logger.info(fName+"invalid key");
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel, sRTitle, "Invalid key: "+key, llColorRed);return;
        }
        putFieldEntry(field,key,value);
        if(!gNewUserProfile.saveJSON()){
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"Updated for "+gNewUserProfile.getMember().getAsMention()+": "+field+"."+key+"="+value, llColorGreen1);
    }
    private Boolean accessApprove4Action(){
        String fName = "[accessApprove4Action]";
        logger.info(fName);
        if(!gIsOverride){
            logger.info(fName+"is override>true");
            return true;
        }
        if(gTarget==null) {
            logger.info(fName+"me>true as wearer should be able to do them");
            return true;
        }else{
            logger.info(fName+"target");
            logger.info(fName+"accessAction="+gNewUserProfile.cProfile.getAccessAsString());
            if(gNewUserProfile.cProfile.getAccessAsString().equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gNewUserProfile.cProfile.getAccessAsString().equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gNewUserProfile.cProfile.getAccessAsString().equalsIgnoreCase(ACCESSLEVEL.Protected.getString())){
                if(gNewUserProfile.cCaretaker.getCaretakerID().isBlank()||gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()||!gUser.getId().equalsIgnoreCase(gNewUserProfile.cCaretaker.getCaretakerID())||!gNewUserProfile.cCaretaker.isAccepted()){
                    logger.info(fName+"invalid");
                    llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"Denied! Only caretaker can access it while the access mode is private.\nIf you want to access it, "+gTarget.getAsMention()+" should change the mood (found in setup or use help) or add you as caretaker.", llColorRed);
                    return false;
                }
                logger.info(fName+"match keyholder");
                return true;
            }else
            if(gNewUserProfile.cProfile.getAccessAsString().equalsIgnoreCase(ACCESSLEVEL.Public.getString())){
                logger.info(fName+"access set to public");
                return true;
            }else
            if(gNewUserProfile.cProfile.getAccessAsString().equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())){
                logger.info(fName+"access set exposed");
                return true;
            }
            logger.info(fName+"invalid access");
            llSendQuickEmbedMessageWithDelete(global,gMember,gTextChannel,sRTitle,"Denied!", llColorRed);

            return false;
        }
    }
    private Boolean isTargeted(){
        String fName = "[isTargeted]";
        logger.info(fName);
        try{ gItems = gCommandEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + gItems.length);
            logger.info(fName + ".items[0]=" + gItems[0]);
            if((gItems[0].contains("<@")||gItems[0].contains("<@!")||gItems[0].contains("<!@"))&&gItems[0].contains(">")){
                String tmp=gItems[0].replace("<!@","").replace("<@!","").replace("<@","").replace(">","");
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
        String quickSummonWithSpace="<@Bot>diaper <@wearer>";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPink1);
        embed.setTitle(sRTitle);
        if(command.equalsIgnoreCase("caretaker")){
            quickSummonWithSpace=llPrefixStr+"diaper caretaker";
            embed.addField("Add (Wearer side)","`" + quickSummonWithSpace + " add <@User>`  gives @User a caretaker offer. They still need to accept it",false);
            embed.addField("Remove (Wearer side)","`" + quickSummonWithSpace + " remove ` removes the suggested caretaker, works while they did nto accept it.",false);
            embed.addField("Runaway (Wearer side)","`"+quickSummonWithSpace+" runaway` resets the profile and removes the caretaker.",false);
            quickSummonWithSpace=llPrefixStr+"diaper <@wearer> caretaker";
            embed.addField("Accept (Caretaker side)","`" + quickSummonWithSpace + " accept` accepts the caretaker offer from wearer.",false);
            embed.addField("Reject (Caretaker side)","`" + quickSummonWithSpace + " reject` rejects the caretaker offer from wearer.",false);
            embed.addField("Release (Caretaker side)","`" + quickSummonWithSpace + " release` releases @wearer from you.",false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
        }else
        if(command.equalsIgnoreCase("setup")){
            quickSummonWithSpace=llPrefixStr+"diaper <@wearer> setup";
            embed.addField("Wetness","`" + quickSummonWithSpace + " wetenable/wetdisable` enable or disable for wearer to get wet"+"\n`" + quickSummonWithSpace + " wetchance [0-25]` changes the chance for the wearer to get wet from action, 0 to disable it",false);
            embed.addField("Messiness","`" + quickSummonWithSpace + " messenable/messdisable` enable or disable for wearer to get messy"+"\n`" + quickSummonWithSpace + " messchance [0-25]` changes the chance for the wearer to get mess from action, 0 to disable it",false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
        }else{
            embed.addField("Display","`" + quickSummonWithSpace + " display` display the wearer diaper status",false);
            embed.addField("Check","`" + quickSummonWithSpace + " check` checks the diaper if its wet&messy",false);
            embed.addField("Change","`" + quickSummonWithSpace + " change` changes the wearer diaper",false);
            embed.addField("Lock/Unlock","`" + quickSummonWithSpace + " lock/unlock` locking or unlocking wearer diaper",false);
            embed.addField("Access","`" + quickSummonWithSpace + " setup "+ACCESSLEVEL.Public.getString()+"/"+ACCESSLEVEL.Private.getString()+"` to set the diaper access.",false);
            embed.addField("Actions for user\\wearer","`" + quickSummonWithSpace + " spank\\tickle\\pat\\swat`",false);
            embed.addField("Actions for wearer","`" + quickSummonWithSpace + " pee\\mess`, wearer pees or messes themself",false);
            embed.addField("Diaper Types","`" + quickSummonWithSpace + " type` to access the menu of different diaper types.",false);
            embed.addField("Onesie Types","`" + quickSummonWithSpace + " onesies` to access the menu of different onesies types.",false);
            embed.addField("Setup","`" + quickSummonWithSpace + " setup` to access the menu"+"\n`" +llPrefixStr+"diaper help setup` info on how to access wearer diaper setup",false);
            embed.addField("Caretaker","\n`" +llPrefixStr+"diaper help caretaker` info on how to access caretaker options",false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                embed.addField("Guild Setup","To access the guild setup `"+llPrefixStr+"diaper guild`"+"\nTo add elements or view the list `"+llPrefixStr+"diaper guild commandroles|targetroles|allowedchannels|exceptionmembers add|rem|list <@mention>`\n\tcommandroles, list of roles who can give diaper commands\n\ttargetroles, list of roles who can get targeted with diaper commands\n\tallowedchannels, channels where the diaper has effect, if used the diaper wont work on channels not part of the list\n\texceptionmembers, members who the diaper effect does not work \n\t!!!empty list means no restrictions, it means everyone has access" , false);
            }
        }
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }else
        if(llSendMessageStatus(gUser,embed)){
            llSendMessageWithDelete(global,gTextChannel," I sent you a list of commands in DMs");
        }else{
            llSendMessageStatus(gTextChannel,embed);
        }
    }
    String gCommandFileMainPath =gFileMainPath+"menuMain.json";
    private void dmMenu() {
        String fName = "[dmMenu]";
        logger.info(fName);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+"diaper <@wearer>";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPink1);
        getProfile();
        if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
            embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+ sRTitle+" Menu");
        }else{
            embed.setTitle(sRTitle+" Menu");
        }
        embed.addField(":eye: Display","Display the wearer diaper status.\n`" + quickSummonWithSpace + " display`",false);
        embed.addField(":mag: Check","Checks the diaper if its wet&messy\n`" + quickSummonWithSpace + " check`",false);
        embed.addField(":roll_of_paper: Change","Changes the wearer diaper\n`" + quickSummonWithSpace + " change` ",false);
        embed.addField(":lock::unlock: Lock/Unlock","Locking or unlocking wearer diaper\n`" + quickSummonWithSpace + " lock/unlock` ",false);
        embed.addField("Actions for user\\wearer","`" + quickSummonWithSpace + " spank\\tickle\\pat\\swat`",false);
        embed.addField(":droplet::poop: Actions for wearer","`" + quickSummonWithSpace + " pee\\mess`, wearer pees or messes themself",false);
        embed.addField(":paperclip: Diaper Types","Access the menu to select a diaper type.\n`" + quickSummonWithSpace + " type`.",false);
        embed.addField(":dress: Onesie Types","Access the menu to select different onesies types.\n`" + quickSummonWithSpace + " onesies`",false);
        StringBuilder desc3=new StringBuilder();
        try {
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField(":person_running: Custom action","(is empty)\nMenu for adding custom actions.",false);
            }else{
                fillNumbersList();
                int i=0;
                while(i<gNewUserProfile.cCustomAction.size()&&i<10) {
                    try {
                        entityCustomActions.ACTION action=gNewUserProfile.cCustomAction.getAction(i);
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append(action.getName());
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append("(error)");
                    }
                    i++;
                }
                embed.addField(":person_running: Custom action",desc3.toString()+"\nMenu for adding custom actions.",false);
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            embed.addField(":person_running: Custom action","(error)\nMenu for adding custom actions.",false);
        }
        embed.addField(":alarm_clock: Timelock","Lock their diapers for n duration!",false);
        embed.addField(":wrench: Setup","Extra options such as authorization, diaper wet level ect`" + quickSummonWithSpace + " setup`.\n`!>diaper help caretaker` info on how to access caretaker options.",false);
        embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
         if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
             embed.addField("Guild Setup","To access the guild setup `"+llPrefixStr+"diaper guild`"+"\nTo add elements or view the list `"+llPrefixStr+"diaper guild commandroles|targetroles|allowedchannels|exceptionmembers add|rem|list <@mention>`\n\tcommandroles, list of roles who can give diaper commands\n\ttargetroles, list of roles who can get targeted with diaper commands\n\tallowedchannels, channels where the diaper has effect, if used the diaper wont work on channels not part of the list\n\texceptionmembers, members who the diaper effect does not work \n\t!!!empty list means no restrictions, it means everyone has access" , false);
         }
        /*if(llSendMessageStatus(gUser,embed)){
            llSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            llSendMessageStatus(gTextChannel,embed);
        }*/
        Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
        /*llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasMag));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasRoleOfPaper));
        if(gNewUserProfile.cProfile.isLocked()){
            llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
        }else{
            llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
        }
        if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
            llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasDroplet));
            llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasPop));
        }
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasDress));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning));
        if(gNewUserProfile.cCustomAction.size()>=1)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
        if(gNewUserProfile.cCustomAction.size()>=2)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
        if(gNewUserProfile.cCustomAction.size()>=3)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
        if(gNewUserProfile.cCustomAction.size()>=4)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
        if(gNewUserProfile.cCustomAction.size()>=5)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
        if(gNewUserProfile.cCustomAction.size()>=6)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
        if(gNewUserProfile.cCustomAction.size()>=7)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
        if(gNewUserProfile.cCustomAction.size()>=8)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
        if(gNewUserProfile.cCustomAction.size()>=9)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
        if(gNewUserProfile.cCustomAction.size()>=10)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock));
        llMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasWrench));*/
        messageComponentManager.loadMessageComponents(gCommandFileMainPath);
        logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
        try {

            lcMessageBuildComponent.Button buttonChangeDiaper=messageComponentManager.messageBuildComponents.getComponent(0).getButtonById("roll_of_paper");
            lcMessageBuildComponent.Button buttonLockUnlock=messageComponentManager.messageBuildComponents.getComponent(0).getButtonById("!lock/unlock");
            lcMessageBuildComponent.Button buttonDiaper=messageComponentManager.messageBuildComponents.getComponent(0).getButtonById("paperclip");

            if(gNewUserProfile.cProfile.isLocked()){
                buttonChangeDiaper.setDisable();
                buttonDiaper.setDisable();
                buttonLockUnlock.setLabel("🔓Unlock").setCustomId("unlock");
            }else{
                buttonLockUnlock.setLabel("🔒Lock").setCustomId("lock");
            }
            lcMessageBuildComponent rowSelfAction=messageComponentManager.messageBuildComponents.getComponent(1);
            lcMessageBuildComponent.Button buttonPee=messageComponentManager.messageBuildComponents.getComponent(1).getButtonById("droplet");
            lcMessageBuildComponent.Button buttonPoop=messageComponentManager.messageBuildComponents.getComponent(1).getButtonById("poop");
            if(gNewUserProfile.gMember.getIdLong()!=gMember.getIdLong()){
                rowSelfAction.setIgnored();
            }else{
                if(!gNewUserProfile.cWet.isEnabled())buttonPee.setDisable();
                if(!gNewUserProfile.cMessy.isEnabled())buttonPoop.setDisable();
            }
            if(!gNewUserProfile.cDIAPER.isEnabled()){
                rowSelfAction.setIgnored();
                messageComponentManager.messageBuildComponents.getComponent(2).setIgnored();
            }
            lcMessageBuildComponent rowUtil=messageComponentManager.messageBuildComponents.getComponent(3);
            if(!(gNewUserProfile.gMember.getIdLong()==gMember.getIdLong()||gNewUserProfile.hasUserOwnerAccess(gUser)||gNewUserProfile.hasUserSecOwnerAccess(gUser))){
                rowUtil.getButtonById("alarm_clock").setDisable();
                rowUtil.getButtonById("wrench").setDisable();
            }
            logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
            message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=sendOrUpdatePrivateEmbed(embed);
        }
        dmMenuListener(message);
    }
    private void dmMenuListener(Message message) {
        String fName = "[dmMenu]";
        global.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                e -> {
                    if(gCurrentInteractionHook!=null)deferReplySet(e);
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);

                        switch (id){
                            case lsUnicodeEmotes.aliasWhiteCheckMark:
                                logger.info(fName+"close");
                                return;
                            case lsUnicodeEmotes.aliasInformationSource:
                                sendOrUpdatePrivateEmbed(sRTitle,"Opening help",llColorBlue1);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                help("main");
                                break;
                            case lsUnicodeEmotes.aliasWrench:
                                if(gSlashCommandEvent!=null){
                                    logger.info(".is slash for "+id);
                                    sendOrUpdatePrivateEmbed(strTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(gTarget!=null){
                                        new diSetup(global,gCurrentInteractionHook,"setup",gTarget);
                                    }else{
                                        new diSetup(global,gCurrentInteractionHook,"setup");
                                    }
                                }else{
                                    if(gTarget!=null){
                                        new diSetup(global,gCommandEvent,"setup",gTarget);
                                    }else{
                                        new diSetup(global,gCommandEvent,"setup");
                                    }
                                }

                                break;
                            case lsUnicodeEmotes.aliasAlarmClock:
                                if(gSlashCommandEvent!=null){
                                    logger.info(".is slash for "+id);
                                    sendOrUpdatePrivateEmbed(strTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(gTarget!=null){
                                        new diTimelock(global,gCurrentInteractionHook,"menu",gTarget);
                                    }else{
                                        new diTimelock(global,gCurrentInteractionHook,"menu");
                                    }
                                }else{
                                    if(gTarget!=null){
                                        new diTimelock(global,gCommandEvent,"menu",gTarget);
                                    }else{
                                        new diTimelock(global,gCommandEvent,"menu");
                                    }
                                }
                                break;
                            case lsUnicodeEmotes.aliasPersonRunning:
                                if(gSlashCommandEvent!=null){
                                    logger.info(".is slash for "+id);
                                    sendOrUpdatePrivateEmbed(strTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(gTarget!=null){
                                        new diSetup(global,gCurrentInteractionHook,"actions",gTarget);
                                    }else{
                                        new diSetup(global,gCurrentInteractionHook,"actions");
                                    }
                                }else{
                                    if(gTarget!=null){
                                        new diSetup(global,gCommandEvent,"actions",gTarget);
                                    }else{
                                        new diSetup(global,gCommandEvent,"actions");
                                    }
                                }
                                break;
                            case lsUnicodeEmotes.aliasLock:
                                if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                    logger.info(".prevented do to restraints");
                                    sendOrUpdatePrivateEmbed(strTitle,"Denied to do restraints!",llColorRed);
                                    return;
                                }else {
                                    if(gSlashCommandEvent!=null){
                                        logger.info(".is slash for "+id);
                                        if (gTarget != null) {
                                            new diSetup(global, gCurrentInteractionHook, "lock", gTarget);
                                        } else {
                                            new diSetup(global, gCurrentInteractionHook, "lock");
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        dmMenu();
                                    }else{
                                        if (gTarget != null) {
                                            new diSetup(global, gCommandEvent, "lock", gTarget);
                                        } else {
                                            new diSetup(global, gCommandEvent, "lock");
                                        }
                                    }
                                }
                                break;
                            case lsUnicodeEmotes.aliasUnlock:
                                if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                    sendOrUpdatePrivateEmbed(strTitle,"Denied to do restraints!",llColorRed);
                                    return;
                                }else {
                                    if(gSlashCommandEvent!=null){
                                        logger.info(".is slash for "+id);
                                        if (gTarget != null) {
                                            new diSetup(global, gCurrentInteractionHook, "unlock", gTarget);
                                        } else {
                                            new diSetup(global, gCurrentInteractionHook, "unlock");
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        dmMenu();
                                    }else{
                                        if (gTarget != null) {
                                            new diSetup(global, gCommandEvent, "unlock", gTarget);
                                        } else {
                                            new diSetup(global, gCommandEvent, "unlock");
                                        }
                                    }
                                }
                                break;
                            case lsUnicodeEmotes.aliasDress:
                                if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                    logger.info(".prevented do to restraints");
                                    sendOrUpdatePrivateEmbed(strTitle,"Denied to do restraints!",llColorRed);
                                    return;
                                }else{
                                    if(gSlashCommandEvent!=null){
                                        logger.info(".is slash for "+id);
                                        sendOrUpdatePrivateEmbed(strTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        if(gTarget!=null){
                                            new diSuit(global,gCurrentInteractionHook,"",gTarget);
                                        }else{
                                            new diSuit(global,gCurrentInteractionHook,"");
                                        }
                                    }else{
                                        if(gTarget!=null){
                                            new diSuit(global,gCommandEvent,gTarget);
                                        }else{
                                            new diSuit(global,gCommandEvent);
                                        }
                                    }
                                }
                                break;
                            case lsUnicodeEmotes.aliasPaperClip:
                                if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                    logger.info(".prevented do to restraints");
                                    sendOrUpdatePrivateEmbed(strTitle,"Denied to do restraints!",llColorRed);
                                    return;
                                }else{
                                    if(gSlashCommandEvent!=null){
                                        logger.info(".is slash for "+id);
                                        sendOrUpdatePrivateEmbed(strTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        if(gTarget!=null){
                                            new diDiaper(global,gCurrentInteractionHook,"",gTarget);
                                        }else{
                                            new diDiaper(global,gCurrentInteractionHook,"");
                                        }
                                    }else{
                                        if(gTarget!=null){
                                            new diDiaper(global,gCommandEvent,gTarget);
                                        }else{
                                            new diDiaper(global,gCommandEvent);
                                        }
                                    }

                                }
                                break;
                            case lsUnicodeEmotes.aliasRoleOfPaper:
                                if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                    logger.info(".prevented do to restraints");
                                    sendOrUpdatePrivateEmbed(strTitle,"Denied to do restraints!",llColorRed);
                                    return;
                                }else{
                                    actionChange();
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    dmMenu();
                                }
                                break;
                            case lsUnicodeEmotes.aliasDroplet:
                                selfWet();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasPop:
                                selfMessy();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasHand:
                                actionFirmPat();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasRollingOnTheFloorLaughing:
                                actionTickle();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasCallMeHand:
                                actionSpank();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasStraightRuler:
                                actionSwat();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasMag:
                                actionVerify();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasEye:
                                display();
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasZero:
                                actionCustom(0);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                actionCustom(1);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                actionCustom(2);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                actionCustom(3);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasFour:
                                actionCustom(4);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasFive:
                                actionCustom(5);
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasSix:
                                actionCustom(6);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasSeven:
                                actionCustom(7);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasEight:
                                actionCustom(8);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            case lsUnicodeEmotes.aliasNine:
                                actionCustom(9);
                                gCurrentInteractionHook=gComponentInteractionHook;
                                dmMenu();
                                break;
                            }

                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                            return;
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWrench))){
                            if(gTarget!=null){
                                new diSetup(global,gCommandEvent,"setup",gTarget);
                            }else{
                                new diSetup(global,gCommandEvent,"setup");
                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock))){
                            if(gTarget!=null){
                                new diTimelock(global,gCommandEvent,"menu",gTarget);
                            }else{
                                new diTimelock(global,gCommandEvent,"menu");
                            }

                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                            if(gTarget!=null){
                                new diSetup(global,gCommandEvent,"actions",gTarget);
                            }else{
                                new diSetup(global,gCommandEvent,"actions");
                            }

                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                            if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                if(gTarget!=null){
                                    new diSetup(global,gCommandEvent,"lock",gTarget);
                                }else{
                                    new diSetup(global,gCommandEvent,"lock");
                                }

                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                            if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                if(gTarget!=null){
                                    new diSetup(global,gCommandEvent,"unlock",gTarget);
                                }else{
                                    new diSetup(global,gCommandEvent,"unlock");
                                }
                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasDress))){
                            if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                if(gTarget!=null){
                                    new diSuit(global,gCommandEvent,gTarget);
                                }else{
                                    new diSuit(global,gCommandEvent);
                                }
                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip))){
                            if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                if(gTarget!=null){
                                    new diDiaper(global,gCommandEvent,gTarget);
                                }else{
                                    new diDiaper(global,gCommandEvent);
                                }

                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasRoleOfPaper))){
                            if((gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
                                logger.info(".prevented do to restraints");
                                return;
                            }else{
                                actionChange();
                            }
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasDroplet))){
                            selfWet();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasPop))){
                            selfMessy();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasMag))){
                            actionVerify();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                            display();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                            actionCustom(0);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                            actionCustom(1);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                            actionCustom(2);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                            actionCustom(3);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                            actionCustom(4);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                            actionCustom(5);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                            actionCustom(6);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                            actionCustom(7);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                            actionCustom(8);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                            actionCustom(9);
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasHand))){
                            actionFirmPat();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasRollingOnTheFloorLaughing))){
                            actionTickle();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasCallMeHand))){
                            actionSpank();
                        }
                        else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasStraightRuler))){
                            actionSwat();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    JSONArray numbersStrList=new JSONArray();
    private void fillNumbersList(){
        String fName="[fillNumbersList]";
        logger.info(fName);
        try{
            numbersStrList=new JSONArray();
            numbersStrList.put(lsUnicodeEmotes.aliasZero);
            numbersStrList.put(lsUnicodeEmotes.aliasOne);
            numbersStrList.put(lsUnicodeEmotes.aliasTwo);
            numbersStrList.put(lsUnicodeEmotes.aliasThree);
            numbersStrList.put(lsUnicodeEmotes.aliasFour);
            numbersStrList.put(lsUnicodeEmotes.aliasFive);
            numbersStrList.put(lsUnicodeEmotes.aliasSix);
            numbersStrList.put(lsUnicodeEmotes.aliasSeven);
            numbersStrList.put(lsUnicodeEmotes.aliasEight);
            numbersStrList.put(lsUnicodeEmotes.aliasNine);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    private Boolean putFieldEntry(String field,String name, Object value){
        String fName="[putFieldEntry]";
        logger.info(fName+".field="+field);
        logger.info(fName+".name="+name);
        logger.info(fName+".value="+value);
        if(name.equalsIgnoreCase(keyLevel)){
            int i= (int) value;
            if(i<0){value=0;}
        }
        if(name.equalsIgnoreCase(keyChance)){
            int i= (int) value;
            if(i<0){value=0;}
        }
        if(gNewUserProfile.gUserProfile.putFieldEntry(field,name,value)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }

    

    
    

    private void getMainJsons(){
        String fName="[getMainJsons]";
        logger.info(fName);
        //if(CUSTOMACTIONS.has(keyActions))ACTIONS=CUSTOMACTIONS.getJSONArray(keyActions);
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


    private void rSlashNT() {
        String fName = "[rSlashNT]";
        logger.info(fName);
        Member member=null;
        boolean subdirProvided=false;
        String actionValue="";boolean actionProvided=false;
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setColor(llColorRed_Barn);
        slashReplyPleaseWait();
        gCurrentInteractionHook=gSlashInteractionHook;
        loadValues();
        for(OptionMapping option:gSlashCommandEvent.getOptions()){
            switch (option.getName()){
                case llCommonKeys.SlashCommandReceive.subdir:
                    subdirProvided=true;
                    break;
                case llCommonKeys.SlashCommandReceive.user:
                    if(option.getType()== OptionType.USER){
                        member=option.getAsMember();
                    }
                    break;
                case "action":
                    if(option.getType()== OptionType.STRING){
                        actionValue=option.getAsString();
                        actionProvided=true;
                    }
                    break;
            }
        }
        if(member!=null&&gMember.getIdLong()!=member.getIdLong()){
            gTarget= member;
            logger.info(fName + ".target="+gTarget.getId());
        }else{
            logger.info(fName + ".target=author");
        }
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        String subcommand=gSlashCommandEvent.getSubcommandName();
        if(subcommand==null)subcommand="";
        logger.info(fName + ".subcommand="+subcommand);
        switch (subcommand){
            case  "menu":
                dmMenu();
                break;
            case "quick":
                if(!actionProvided){
                    sendOrUpdatePrivateEmbed( sRTitle,"Invalid command",llColorRed);
                    return;
                }

                if(gTarget==null){
                    switch (actionValue){
                        case "check":
                            actionVerify();
                            break;
                        case "display":
                            display();
                            break;
                        case "change":
                            actionChange();
                            break;
                        case "lock/unlock":

                            break;
                        case "pat":
                            actionFirmPat();
                            break;
                        case "tickle":
                            actionTickle();
                            break;
                        case "pee":
                            selfWet();
                          break;
                        case "poop":
                            selfMessy();
                            break;
                        case "spank":
                            actionSpank();
                            break;
                        case "swat":
                            actionSwat();
                            break;
                        default:
                            sendOrUpdatePrivateEmbed( sRTitle,"Invalid command",llColorRed);
                            return;
                    }
                }else{
                    switch (actionValue){
                        case "check":
                            actionVerify();
                            break;
                        case "display":
                            display();
                            break;
                        case "change":
                            actionChange();
                            break;
                        case "lock/unlock":
                            break;
                        case "pee":
                            selfWet();
                            break;
                        case "poop":
                            selfMessy();
                            break;
                        default:
                            sendOrUpdatePrivateEmbed( sRTitle,"Invalid command",llColorRed);
                            return;
                    }
                }
                break;
            default:
                sendOrUpdatePrivateEmbed( sRTitle,"Invalid command",llColorRed);
        }




    }

    

    Boolean vEnabled=false;JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
    private void loadValues(){
        String fName = "[loadValues]";
        logger.info(fName);
        logger.info(fName+"json="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldEnabled)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldEnabled)){
            vEnabled = gBDSMCommands.diaper.gProfile.getFieldEntryAsBoolean(iRestraints.fieldEnabled);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldBannedUsers)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldBannedUsers)){
            vBannedUsers= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldBannedUsers);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldAllowedChannels)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldAllowedChannels)){
            vAllowedChannels= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldAllowedChannels);
        }
    }


    ////////////////////////
}

    public diaperInteractive_v2(lcGlobalHelper global, Guild guild, User user, String command){
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
