package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.BLINDFOLDLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdBlindfold extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iBlindfold {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
	String sMainRTitle ="Blindfold",gCommand="blindfold";
    public rdBlindfold(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Blindfold-DiscordRestraints";
        this.help = "rdBlindfold";
        this.aliases = new String[]{"blindfold","blindfolds"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdBlindfold(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdBlindfold(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd,target);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent gEvent) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(gEvent);
        new Thread(r).start();
    }
    protected class runLocal extends rdExtension implements Runnable {
        String cName="[runLocal]";
        public runLocal(CommandEvent ev){
            launch(gGlobal,ev);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward,target);
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                setTitleStr(rdBlindfold.this.sMainRTitle);setPrefixStr(rdBlindfold.this.llPrefixStr);setCommandStr(rdBlindfold.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdBlindfold_commands");
                lsUsefullFunctions.setThreadName4Display("rdBlindfold");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuLevels(gTarget);
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuLevels(gTarget);
                            break;
                    }
                }else if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }else
                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand=true;
                    if(!checkIFAllowed2UseCommand2_text()){ return; }
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        menuLevels(gTarget);isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand=true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        if(!checkIFAllowed2UseCommand2_text()){ return; }
                        else {menuLevels(null);isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".gItems.size=" + gItems.length);
                        logger.info(fName + ".gItems[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                        else if(isCommand2BasicFeatureControl(gItems)){isInvalidCommand=false;}
                        else if(!checkIFAllowed2UseCommand2_text()){ return; }
                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuLevels(gTarget);isInvalidCommand=false;
                            }else{
                                if(gItems.length>=4){
                                    isInvalidCommand= quickCommands(gItems[2], gItems[3], gTarget);
                                }
                                if(isInvalidCommand&&gItems.length>=3){
                                    isInvalidCommand= quickCommands(gItems[1], gItems[2], gTarget);
                                }
                                if(isInvalidCommand){
                                    isInvalidCommand= quickCommands(gItems[1], "", gTarget);
                                }
                            }
                            if(isInvalidCommand&&gTarget!=null){
                                menuLevels(gTarget);isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(vRed)){
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cBLINDFOLD.release();});}
                            else if(gItems[0].equalsIgnoreCase(vOn)||gItems[0].equalsIgnoreCase(vOff)){
                                rBlindfold(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())||gItems[0].equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())){
                                rBlindfold(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else{
                                menuLevels(null);isInvalidCommand=false;
                            }
                        }
                    }
                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sMainRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
            logger.info(".run ended");
        }
        private boolean quickCommands(String item1, String item2,Member target){
            String fName="[quickCommands]";
            logger.info(fName+"item1="+item1);
            logger.info(fName+"item2="+item2);
            logger.info(fName+"target="+target.getId());
            if(item1.equalsIgnoreCase(vOn)||item1.equalsIgnoreCase(vOff)){
                rBlindfold(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOn)||item2.equalsIgnoreCase(vOff)){
                rBlindfold(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())||item1.equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())){
                rBlindfold(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())||item2.equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())){
                rBlindfold(target,item2.toLowerCase()); return false;}
            return true;
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> blindfold ";
        String quickSummonWithSpace2=llPrefixStr+"blindfold <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Robs the wearer of all sight. Fictional, make belief, to be in character.",false);
        desc=newLine+quickSummonWithSpace2+"on"+endLine;
        desc+=newLine+quickSummonWithSpace2+"off"+endLine;
        desc+=newLine+quickSummonWithSpace2+BLINDFOLDLEVELS.Paneled.getName()+spacingDual+quickSummonWithSpace2+BLINDFOLDLEVELS.Contacts.getName()+endLine;
        embed.addField("Commands",desc,false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    
    private boolean rBlindfold(String command) {
        String fName = "[rBlindfold]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cBLINDFOLD.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle, stPermaLock, llColorRed);
            return false;
        }else
         if(gNewUserProfile.isPetDenied2HaveAccessIfRestrained()){
             if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                 logger.info(fName + ".can't restrain wile jacket is on");
                 sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed_PetDenied), llColorRed);
                 return false;
             }
             if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                 logger.info(fName + ".can't restrain wile mitts are on");
                 if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                     logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                     iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                 }else{
                     logger.info(fName + ".default message");
                     sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                 }
                 return false;
             }
             if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                 logger.info(fName + ".can't restrain wile cuffs are on");
                 sendOrUpdatePrivateEmbed(sRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                 return false;
             }
         }

        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(strLocked), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle, strAccessSet2Pet, llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle, strAccessSet2Public, llColorRed);
            return false;
        }else
        if(command.equalsIgnoreCase(vOn)){
            if(!gIsOverride&&gNewUserProfile.cBLINDFOLD.isOn()){
                logger.info(fName + ".blindfold is already on");
                sendOrUpdatePrivateEmbed(sRTitle, strIsAlreadyOn, llColorPurple1);
            }else{
                gNewUserProfile.cBLINDFOLD.setOn( true);
                if(gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().isBlank()){gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());}
                else if(gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());}
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutBlindolfOn, llColorBlue1);
                userNotificationBlindfold( actionPutOn,stringReplacer(srtPutBlindfoldOnMention));
            }
        }else
        if(command.equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())){
            if(!gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
            }else{
                gNewUserProfile.cBLINDFOLD.setOn( true);gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutPannel, llColorBlue1);
                userNotificationBlindfold( actionPutOn,stringReplacer(strPutPannelMention));
            }
        }else
        if(command.equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())){
            if(!gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
            }else{
                gNewUserProfile.cBLINDFOLD.setOn( true);gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Contacts.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutContacts, llColorBlue1);
                userNotificationBlindfold( actionPutOn,stringReplacer(iBlindfold.strPutContactsMention));
            }
        }else
        if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cBLINDFOLD.isOn()){
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed), llColorRed);
                    userNotificationBlindfold( actionStruggle, stringReplacer(iBlindfold.strCantTakeOffDue2Jacket));
                }
                else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    userNotificationBlindfold( actionStruggle,stringReplacer(iBlindfold.strCantTakeOffDueLocked));
                }else{
                    gNewUserProfile.cBLINDFOLD.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strTakeOff, llColorBlue1);
                    userNotificationBlindfold( actionTakeOff, stringReplacer(iBlindfold.strTakeOffMention));
                }
            }else{
                logger.info(fName + ".blindfold is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strNotOn, llColorPurple1);
            }
        }
        saveProfile();
        return true;
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private boolean rBlindfold(Member mtarget, String command) {
        String fName = "[rBlindfold]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile");return false;}
        if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
            logger.info(fName + ".isSameConfinement>return");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iConfine.strYouAreNotInsameCell),llColorRed);
            return false;
        }
        if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
            }
            return false;
        }
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cBLINDFOLD.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantDuePermalockTarget),llColorRed);
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantDueLockedTarget), llColorRed);
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueAccess, llColorRed);
        }else
        if(command.equalsIgnoreCase("on")){
            if(!gIsOverride&&gNewUserProfile.cBLINDFOLD.isOn()){
                logger.info(fName + ".blindfold is already on");
                sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking to add blindfold you!");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to add blindfold !TARGET!");
                    gAskHandlingHelper.doAsk(() -> {rBlindfold_Save4Target(target,command);});
                    return false;
                }
                rBlindfold_Save4Target(target,command);
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
        }else
        if(command.equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())||command.equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking to add blindfold you!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to add blindfold !TARGET!");
                gAskHandlingHelper.doAsk(() -> {rBlindfold_Save4Target(target,command);});
                return false;
            }
            rBlindfold_Save4Target(target,command);
        }else
        if(command.equalsIgnoreCase("off")){
            if(gNewUserProfile.cBLINDFOLD.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking to remove blindfold you!");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to remove blindfold !TARGET!");
                        gAskHandlingHelper.doAsk(() -> {rBlindfold_Save4Target(target,command);});
                        return false;
                    }
                    rBlindfold_Save4Target(target,command);
                }
            }else{
                logger.info(fName + ".blindfold is not on");
                llSendQuickEmbedMessage(target,sMainRTitle,"The blindfold are not on.", llColorPurple1);
            }
        }
        return true;
    }
        private void rBlindfold_Save4Target(User target,String command) {
            String fName = "[rBlindfold_Save4Target]";
            logger.info(fName);
            logger.info(fName + ".target="+target.getName()+"("+target.getId()+")");
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase("on")){
                gNewUserProfile.cBLINDFOLD.setOn( true);
                if(gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().isBlank()){gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());}
                else if(gIsOverride&&gNewUserProfile.cBLINDFOLD.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());}
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strPutTargetBlindfold1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strPutTargetBlindfold2), llColorBlue1);
                userNotificationBlindfold(actionPutOn, stringReplacer(strPutTargetBlindfold3));
            }else
            if(command.equalsIgnoreCase(BLINDFOLDLEVELS.Paneled.getName())){
                gNewUserProfile.cBLINDFOLD.setOn( true);gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Paneled.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strPutTargetPannel1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strPutTargetPannel2), llColorBlue1);
                userNotificationBlindfold(actionPutOn, stringReplacer(strPutTargetPannel3));
            }else
            if(command.equalsIgnoreCase(BLINDFOLDLEVELS.Contacts.getName())){
                gNewUserProfile.cBLINDFOLD.setOn( true);gNewUserProfile.cBLINDFOLD.setLevel( BLINDFOLDLEVELS.Contacts.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strPutTargetContacts1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strPutTargetContacts2), llColorBlue1);
                userNotificationBlindfold(actionPutOn, stringReplacer(strPutTargetContacts3));
            }else
            if(command.equalsIgnoreCase("off")){
                gNewUserProfile.cBLINDFOLD.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTakeOffTarget1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTakeOffTarget2), llColorBlue1);
                userNotificationBlindfold(actionTakeOff, stringReplacer(strTakeOffTarget3));
            }
            saveProfile();
        }
        String gCommandFileMainPath =gFileMainPath+"blindfold/menuLevels.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    if(!gIsOverride&&gNewUserProfile.cCONFINE.isAuthorConfinedAndNotSameConfinment(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                        logger.info(fName + ".confinement exception");
                        if(gNewUserProfile.cCONFINE.isConfined()){
                            sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                        }else{
                            sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetIsNotLockedWithYou),llColorRed);
                        }
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                        }
                        return;
                    }
                    menuLevelsSomebody();
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(gNewUserProfile.cBLINDFOLD.isOn()){
                    String level=gNewUserProfile.cBLINDFOLD.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+BLINDFOLDLEVELS.Paneled.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+BLINDFOLDLEVELS.Contacts.getName();
                embed.addField(" ", "Please select a blindfold option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                if(gNewUserProfile.isWearerDenied0withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cBLINDFOLD.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cBLINDFOLD.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar())desc+= iRdStr.strRestraintArmsCuffs;
                    if(gNewUserProfile.cMITTS.isOn())desc+=iRdStr.strRestraintMitts;
                    if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                        if(gNewUserProfile.cSUIT.getSuitType()== SUITTYPE.Bitchsuit){
                            desc+=iRdStr.strRestraintBDSMSuitBitchsuit;
                        }else
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack){
                            desc+=iRdStr.strRestraintBDSMSuitHogsack;
                        }else
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack){
                            desc+=iRdStr.strRestraintBDSMSuitSleepsack;
                        }
                    }
                    if(gNewUserProfile.cENCASE.isEncased())desc+=iRdStr.strRestraintEncased;
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraints+desc,false);
                }
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.selectContainer= messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if(gNewUserProfile.cBLINDFOLD.isOn()){
                        if(gNewUserProfile.cBLINDFOLD.getLevel()==BLINDFOLDLEVELS.Paneled)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                        if(gNewUserProfile.cBLINDFOLD.getLevel()==BLINDFOLDLEVELS.Contacts)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                        if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    }
                    if(gNewUserProfile.isWearerDenied0withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cBLINDFOLD.isOn())){
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsWearerListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsSomebody(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(gNewUserProfile.cBLINDFOLD.isOn()){
                    String level=gNewUserProfile.cBLINDFOLD.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+BLINDFOLDLEVELS.Paneled.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+BLINDFOLDLEVELS.Contacts.getName();
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a blindfold option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.selectContainer= messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if(gNewUserProfile.cBLINDFOLD.isOn()){
                        switch (gNewUserProfile.cBLINDFOLD.getLevel()){
                            case Paneled:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Contacts:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                        }
                       if(gNewUserProfile.isLocked_checkMember(gMember))messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsSomebodyListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource))level="information_source";
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        rHelp("main");
                                    }else{
                                        rBlindfold(level);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES,() -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String>values=e.getValues();
                                logger.warn(fName+"vvalues="+values.toString());
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                String level="";
                                llMessageDelete(message);
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level="on";break;
                                    case lsUnicodeEmotes.aliasTwo:level=BLINDFOLDLEVELS.Paneled.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=BLINDFOLDLEVELS.Contacts.getName();break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    result=rBlindfold(level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level="off";}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level="on";}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=BLINDFOLDLEVELS.Paneled.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=BLINDFOLDLEVELS.Contacts.getName();}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rBlindfold(level);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsSomebodyListener(Message message){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource))level="information_source";
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        rHelp("main");
                                    }else{
                                        rBlindfold(gNewUserProfile.getMember(),level);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String>values=e.getValues();
                                logger.warn(fName+"vvalues="+values.toString());
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level="on";break;
                                    case lsUnicodeEmotes.aliasTwo:level=BLINDFOLDLEVELS.Paneled.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=BLINDFOLDLEVELS.Contacts.getName();break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        result=rBlindfold(gNewUserProfile.getMember(),level);
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsSomebody();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {

                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level="off";}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level="on";}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=BLINDFOLDLEVELS.Paneled.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=BLINDFOLDLEVELS.Contacts.getName();}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rBlindfold(gNewUserProfile.getMember(),level);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            User user=null;
            boolean subdirProvided=false;
            slashReplyPleaseWait();
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsUser();
                        }
                        break;
                    case llCommonKeys.SlashCommandReceive.subdir:
                        subdirProvided=true;
                        break;

                }
            }
            if(user!=null&&gMember.getIdLong()!=user.getIdLong()){
                gTarget=lsMemberHelper.lsGetMember(gGuild,user);
                if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
            }else{
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            }
            gCurrentInteractionHook=gSlashInteractionHook;
            menuLevels(gTarget);
        }
    private void userNotificationBlindfold(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nBlindfold;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                        if(isAdult&&action==actionPutOn){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlOn)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlOn)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOn).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOn);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionTakeOff){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlOff)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlOff)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOff).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOff);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(action==actionStruggle){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlStruggle)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlStruggle)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gTextChannel,embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        } else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }
                }
            }catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), gUser,sMainRTitle, e.toString());
            }
        }


    }}
