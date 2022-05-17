package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.ENCASELEVELS;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdEncase extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iEncase{
   lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdEncase]";
	String sMainRTitle ="Encase [WIP]",gCommand="encase";
    public rdEncase(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Encase-DiscordRestraints";
        this.help = "rdEncase";
        this.aliases = new String[]{gCommand,"rd"+gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdEncase(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEncase(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            String fName="[runLocal]";
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            String fName="[runLocal]";
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
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
                setTitleStr(rdEncase.this.sMainRTitle);setPrefixStr(rdEncase.this.llPrefixStr);setCommandStr(rdEncase.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdEncase_commands");
                lsUsefullFunctions.setThreadName4Display("rdEncase");
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
                }
                else
                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    String[] items;
                    boolean isInvalidCommand=true;
                    if(!checkIFAllowed2UseCommand2_text()){
                        return;
                    }
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
                        if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        else {menuLevels(null);isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                        else if(isCommand2BasicFeatureControl(gItems)){
                            isInvalidCommand=false;
                        }
                        else if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuLevels(gTarget);isInvalidCommand=false;
                            }else{
                                switch (gItems[1].toLowerCase()){
                                    case iEncase.commandCage:
                                    case iEncase.commandGibbet:
                                    case iEncase.commandGlass:
                                    case iEncase.commandMummy:
                                    case iEncase.commandPole:
                                    case iEncase.commandRelease:
                                    case iEncase.commandRubber:
                                        rEncase(gTarget,gItems[1],"");isInvalidCommand=false;
                                    default:{
                                        if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember))
                                                &&(gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)||gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement))
                                        )
                                        {
                                            rEncase(gTarget, gItems[1],"");
                                        }
                                    }
                                }
                            }
                            if(isInvalidCommand&&gTarget!=null){
                                menuLevels(gTarget);isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }else{
                                switch (gItems[0].toLowerCase()){
                                    case vRed:
                                        isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cENCASE.release();});
                                        break;
                                    case iEncase.commandCage:
                                    case iEncase.commandGibbet:
                                    case iEncase.commandGlass:
                                    case iEncase.commandMummy:
                                    case iEncase.commandPole:
                                    case iEncase.commandRelease:
                                    case iEncase.commandRubber:
                                        rEncase(gItems[0],"");isInvalidCommand=false;
                                    default:{
                                        if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember))
                                                &&(gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement))
                                        )
                                        {
                                            rEncase(gItems[0],"");
                                        }
                                    }
                                }
                            }

                            if(isInvalidCommand){
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
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace2=llPrefixStr+"encase <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        desc+=newLine+quickSummonWithSpace2+commandRelease+endLine;
        desc+=newLine+quickSummonWithSpace2+commandMummy+endLine;
        desc+=newLine+quickSummonWithSpace2+commandGibbet+endLine;
        desc+=newLine+quickSummonWithSpace2+commandRubber+endLine;
        desc+=newLine+quickSummonWithSpace2+commandGlass+endLine;
        desc+=newLine+quickSummonWithSpace2+commandCage+endLine;
        desc+=newLine+quickSummonWithSpace2+commandPole+endLine;
        embed.addField("Commands",desc,false);
        desc+="\n• limits @PET's own access to restraints option";
        embed.addField("Effects",desc,false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    
    private void rEncase(String command, String value) {
        String fName = "[rEncase]";
        logger.info(fName);
        logger.info(fName + ".command=" + command+", value="+value);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
         if(gNewUserProfile.isPetDenied2HaveAccessIfRestrained()){
             if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                 logger.info(fName + ".can't restrain wile jacket is on");
                 llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed_PetDenied), llColorRed);
                 return;
             }
             if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                 logger.info(fName + ".can't restrain wile mitts are on");
                 if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                     logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                     iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                 }else{
                     logger.info(fName + ".default message");
                     llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                 }
                 return;
             }
             if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                 logger.info(fName + ".can't restrain wile cuffs are on");
                 llSendQuickEmbedMessage(gUser,sMainRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                 return;
             }
         }
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            llSendQuickEmbedMessage(gUser,sMainRTitle, strAccessSet2Pet, llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            llSendQuickEmbedMessage(gUser,sMainRTitle, strAccessSet2Public, llColorRed);
            return;
        }

        if(command.equalsIgnoreCase(commandRelease)){
            gNewUserProfile.cENCASE.release();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloRelease1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloRelease1),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandMummy)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.MUMMY);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloMummy1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloMummy2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandGibbet)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GIBBET);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloGibbet1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloGibbet2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandRubber)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.RUBBER);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloRubber1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloRubber2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandGlass)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GLASS);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloGlass1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloGlass2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandCage)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.CAGE);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloCage1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloCage2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandPole)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.POLE);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloPole1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloPole2),llColors.llColorBlue1);
        }
        else if(command.equalsIgnoreCase(commandVacbed)){
            gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.VACBED);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strSoloPole1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strSoloPole2),llColors.llColorBlue1);
        }
        else if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember))
                &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement))
        ){
            gNewUserProfile.cENCASE.setOn(true);
            if(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)){
                gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GLUE);
                iRDPatreon.patreonUser_118178462149115913_encase.sendApplyingGlueWearer4RD(gNewUserProfile.gUserProfile,gMember,gTextChannel);
            }
            if(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement)){
                gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.CEMENT);
                iRDPatreon.patreonUser_118178462149115913_encase.sendApplyingCementWearer4RD(gNewUserProfile.gUserProfile,gMember,gTextChannel);
            }
            //gNewUserProfile=iRDPatreon.patreonUser_118178462149115913_suit.applyAdditionalRestraints(gNewUserProfile);
        }
        saveProfile();
        if(isMenu){
            menuLevelsWearer();
        }
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private void rEncase(Member mtarget, String command, String value) {
        String fName = "[rEncase]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command=" + command+", value="+value);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
            }
            return;
        }
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            llSendQuickEmbedMessage(gUser,sMainRTitle,strCantDueLockedTarget.replaceAll("!LOCKER",gNewUserProfile.cLOCK.getUserWhoLockedPet()), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            llSendQuickEmbedMessage(gUser,sMainRTitle,strCantDueAccess, llColorRed);return;
        }
        
        
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your encasement!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's encasement!");
                gAskHandlingHelper.doAsk(() -> {rConfine_Save4Target(mtarget,command);});
                return;
            }
            rConfine_Save4Target(mtarget,command);
        
    }
        private void rConfine_Save4Target(Member target, String command) {
            String fName = "[rConfine_Save4Target]";
            logger.info(fName);
            logger.info(fName + ".target="+target.getUser().getName()+"("+target.getId()+")");
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(commandRelease)){
                gNewUserProfile.cENCASE.release();
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetRelease1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetRelease2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetRelease3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandMummy)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.MUMMY);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetMummy1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetMummy2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetMummy3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandGibbet)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GIBBET);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetGibbet1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetGibbet2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetGibbet3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandRubber)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.RUBBER);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetRubber1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetRubber2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetRubber3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandGlass)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GLASS);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetGlass1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetGlass2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetGlass3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandCage)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.CAGE);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetCage1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetCage2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetCage3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandPole)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.POLE);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetPole1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetPole2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetPole3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandVacbed)){
                gNewUserProfile.cENCASE.setOn(true);gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.VACBED);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iEncase.strTargetPole1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iEncase.strTargetPole2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iEncase.strTargetPole3),llColors.llColorBlue1);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement))
            ){
                gNewUserProfile.cENCASE.setOn(true);
                if(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameGlue)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandGlue)){
                    gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.GLUE);
                    iRDPatreon.patreonUser_118178462149115913_encase.sendApplyingGlueUser4RD(gNewUserProfile.gUserProfile,gNewUserProfile.getMember().getUser(),gMember,gTextChannel);
                }
                if(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.nameCement)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_encase.commandCement)){
                    gNewUserProfile.cENCASE.setLevel(ENCASELEVELS.CEMENT);
                    iRDPatreon.patreonUser_118178462149115913_encase.sendApplyingCementUser4RD(gNewUserProfile.gUserProfile,gNewUserProfile.getMember().getUser(),gMember,gTextChannel);
                }
                //gNewUserProfile=iRDPatreon.patreonUser_118178462149115913_suit.applyAdditionalRestraints(gNewUserProfile);
            }
            saveProfile();
            if(isMenu){
                menuLevelsSomebody();
            }
        }
        boolean isMenu=false;
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    if(!gIsOverride&&gNewUserProfile.cCONFINE.isAuthorConfinedAndNotSameConfinment(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                        logger.info(fName + ".confinement exception");
                        if(gNewUserProfile.cCONFINE.isConfined()){
                            llSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                        }else{
                            llSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetIsNotLockedWithYou),llColorRed);
                        }
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
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
                isMenu=true;
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(gNewUserProfile.cENCASE.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cENCASE.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                embed.addField("Currently",desc,false);
                desc="";

                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" mummy";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" gibbet";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" rubber";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" glass";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" cage";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)+" pole";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" vacbed";
                if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" cement";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" glue";
                }
                embed.addField(" ", "Please select a confine option :"+desc, false);
                embed.addField("Note", "Encasing yourself can prevent you from un-encasing.", false);
                if(gNewUserProfile.isWearerDenied0()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        desc+="\n•  straitjacket arms restraint";
                    }
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()){
                        desc+="\n•  arms cuffs";
                    }
                    if(gNewUserProfile.cMITTS.isOn()){
                        desc+=iRdStr.strRestraintMitts;
                    }
                    if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                        if(gNewUserProfile.cSUIT.getSuitType()== SUITTYPE.Bitchsuit){
                            desc+=iRdStr.strRestraintBDSMSuitBitchsuit;
                        }
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack){
                            desc+=iRdStr.strRestraintBDSMSuitHogsack;
                        }
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack){
                            desc+=iRdStr.strRestraintBDSMSuitSleepsack;
                        }
                    }
                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc+=iRdStr.strRestraintEncased;
                    }
                    desc+=iRdStr.strRestraintOnlyIf;

                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc2=iRdStr.strRestrainedYouEncased;
                        desc2+=" How do you think you can free yourself?";
                    }else{
                        desc2=iRdStr.strRestrainedYou;
                        desc2+=" How do you think you can encase yourself?";
                    }
                    embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);

                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(!gNewUserProfile.isWearerDenied0withException()){
                    if(gNewUserProfile.cENCASE.isOn()){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.MUMMY)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GIBBET) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.RUBBER) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GLASS) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.CAGE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.POLE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.VACBED) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                        if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                            if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.CEMENT)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GLUE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                        }
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                        if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                        }
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandRelease;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandMummy;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGibbet;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandRubber;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandGlass;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandCage;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandPole;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){level=commandVacbed;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){level=iRDPatreon.patreonUser_118178462149115913_encase.commandCement;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG))){level=iRDPatreon.patreonUser_118178462149115913_encase.commandGlue;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rEncase(level,"");
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

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
                isMenu=true;
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(gNewUserProfile.cENCASE.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cENCASE.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                embed.addField("Currently",desc,false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" mummy";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" gibbet";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" rubber";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" glass";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" cage";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)+" pole";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" vacbed";
                if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" cement";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" glue";
                }
                embed.addField(" ", "Please select a confine option for "+gNewUserProfile.getMember().getAsMention()+" :"+desc, false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(gNewUserProfile.cENCASE.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.MUMMY)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GIBBET) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.RUBBER) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GLASS) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.CAGE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.POLE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.VACBED) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.CEMENT)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        if(gNewUserProfile.cENCASE.getLevel()!=ENCASELEVELS.GLUE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                    }
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_encase.userID||lsMemberIsBotOwner(gMember)){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandRelease;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandMummy;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGibbet;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandRubber;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandGlass;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandCage;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandPole;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){level=commandVacbed;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){level=iRDPatreon.patreonUser_118178462149115913_encase.commandCement;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG))){level=iRDPatreon.patreonUser_118178462149115913_encase.commandGlue;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rEncase(gNewUserProfile.getMember(),level,"");
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                User reqUser=null;
                boolean subdirProvided=false;
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()== OptionType.USER){
                                reqUser=option.getAsUser();
                            }
                            break;
                    }
                }
                if(reqUser!=null&&gMember.getIdLong()!=reqUser.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,reqUser);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                slashReplyCheckDm();
                menuLevels(gTarget);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
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


   
        private void putFieldEntry(String name, Object value){
            String fName="[putFieldEntry]";
            logger.info(fName+".field="+ nBlindfold);
            logger.info(fName+".name="+name);
            logger.info(fName+".value="+value);
            gNewUserProfile.gUserProfile.putFieldEntry(nBlindfold,name,value);
            gNewUserProfile.cBLINDFOLD.put(name,value);
        }

}}
