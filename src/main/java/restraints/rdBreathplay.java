package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.BREATHPLAYLEVELS;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdBreathplay extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iBreathplay{
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdBreathplay]";
	String sMainRTitle ="Breathplay [WIP]",gCommand="breathplay";
    public rdBreathplay(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Breathplay-DiscordRestraints";
        this.help = "rdBreathplay";
        this.aliases = new String[]{gCommand,"rd"+gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public rdBreathplay(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdBreathplay(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdBreathplay(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdBreathplay(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdBreathplay(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            String fName="[runLocal]";
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            String fName="[runLocal]";
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
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
                setTitleStr(rdBreathplay.this.sMainRTitle);setPrefixStr(rdBreathplay.this.llPrefixStr);setCommandStr(rdBreathplay.this.gCommand);
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
                if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }else
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
                    String[] items;
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
                        items = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);
                        if(items[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                        else if(isCommand2BasicFeatureControl(items)){isInvalidCommand=false;}
                        else if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                           if(items.length<2){
                                logger.warn(fName+".invalid args length");
                                menuLevels(gTarget);isInvalidCommand=false;
                            }else{
                                switch (items[1].toLowerCase()){
                                    case iBreathplay.commandTape:
                                    case iBreathplay.commandRope:
                                    case iBreathplay.commandHanging:
                                    case iBreathplay.commandBag:
                                    case iBreathplay.commandRelease:
                                        rEncase(gTarget,items[1],"");isInvalidCommand=false;
                                }
                            }
                           if(isInvalidCommand&&gTarget!=null){
                                menuLevels(gTarget);isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            if(items==null||items.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }else{
                                switch (items[0].toLowerCase()){
                                    case vRed:
                                        isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cBREATPLAY.release();});
                                        break;
                                    case iBreathplay.commandBag:
                                    case iBreathplay.commandHanging:
                                    case iBreathplay.commandHood:
                                    case iBreathplay.commandRope:
                                    case iBreathplay.commandTape:
                                    case iBreathplay.commandRelease:
                                        rEncase(items[1],"");isInvalidCommand=false;
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
        desc+=newLine+quickSummonWithSpace2+ commandTape +endLine;
        desc+=newLine+quickSummonWithSpace2+ commandRope +endLine;
        desc+=newLine+quickSummonWithSpace2+ commandBag +endLine;
        desc+=newLine+quickSummonWithSpace2+ commandHood +endLine;
        desc+=newLine+quickSummonWithSpace2+ commandHanging +endLine;
        
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
            gNewUserProfile.cBREATPLAY.release();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloRelease1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloRelease1),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandTape)){
            gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.TAPE);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloTape1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloTape2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandRope)){
            gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.ROPE);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloRope1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloRope2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandBag)){
            gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.BAG);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloBag1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloBag2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandHood)){
            gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.HOOD);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloHood1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloHood2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandHanging)){
            gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.HANGING);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strSoloHanging1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strSoloHanging2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandSetPostCount)){
            int i=Integer.parseInt(value);
            gNewUserProfile.cBREATPLAY.setCount(i);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"Set your breath count to "+i+".",llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandAddBreath)){
            gNewUserProfile.cBREATPLAY.incCount();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"Increased your breath to "+gNewUserProfile.cBREATPLAY.getCount()+".",llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandSubBreath)){
            gNewUserProfile.cBREATPLAY.decCount();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"De-increased your breath to "+gNewUserProfile.cBREATPLAY.getCount()+".",llColors.llColorBlue1);
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
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your breath situation!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's breath situation!");
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
                gNewUserProfile.cBREATPLAY.release();
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetRelease1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetRelease2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetRelease3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandTape)){
                gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.TAPE);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetTape1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetTape2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetTape3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandRope)){
                gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.ROPE);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetRope1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetRope2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetRope3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandBag)){
                gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.BAG);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetBag1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetBag2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetBag3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandHood)){
                gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.HOOD);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetHood1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetHood2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetHood3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandHanging)){
                gNewUserProfile.cBREATPLAY.setOn(true);gNewUserProfile.cBREATPLAY.setLevel(BREATHPLAYLEVELS.HANGING);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer(iBreathplay.strTargetHanging1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer(iBreathplay.strTargetHanging2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,stringReplacer(iBreathplay.strTargetHanging3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandAddBreath)){
                gNewUserProfile.cBREATPLAY.incCount();
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("Increased !TARGET breath to "+gNewUserProfile.cBREATPLAY.getCount()+"."),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandSubBreath)){
                gNewUserProfile.cBREATPLAY.decCount();
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("De-increased !TARGET breath to "+gNewUserProfile.cBREATPLAY.getCount()+"."),llColors.llColorBlue1);
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
                if(gNewUserProfile.cBREATPLAY.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cBREATPLAY.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                int remaning=gNewUserProfile.cBREATPLAY.getRemaining();
                int count=gNewUserProfile.cBREATPLAY.getCount();
                desc+="\nBreaths: ";
                if(count<=0){
                    desc+="invalid count "+count;
                }else
                if(remaning>0){
                    desc+=remaning+"/"+count;
                }else{
                    desc+="used up all "+count;
                }

                embed.addField("Currently",desc,false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" tape";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" rope";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" bag";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" hood";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" hanging";
                embed.addField(" ", "Please select a type option :"+desc, false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp)+" +1 breath";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown)+" -1 breath";
                embed.addField(" ", "Please select a breath option :"+desc, false);
                embed.addField("Note", "Encasing yourself can prevent you from un-encasing.", false);
                if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()||gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()||gNewUserProfile.cBREATPLAY.isEncased()||gNewUserProfile.cSUIT.isBDSMSuitOn()||gNewUserProfile.cMITTS.isOn()){
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
                    if(gNewUserProfile.cBREATPLAY.isEncased()){
                        desc+=iRdStr.strRestraintEncased;
                    }
                    desc+=iRdStr.strRestraintOnlyIf;

                    if(gNewUserProfile.cBREATPLAY.isEncased()){
                        desc2=iRdStr.strRestrainedYouEncased;
                        desc2+=" How do you think you can free yourself?";
                    }else{
                        desc2=iRdStr.strRestrainedYou;
                        desc2+=" How do you think you can bag yourself?";
                    }
                    embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);

                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()&&!gNewUserProfile.cBREATPLAY.isEncased()&&!gNewUserProfile.cSUIT.isBDSMSuitOn()&&!gNewUserProfile.cMITTS.isOn())){
                    if(gNewUserProfile.cBREATPLAY.isOn()){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                        if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.TAPE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.ROPE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.BAG) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.HOOD) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.HANGING) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown));
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level= commandTape;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level= commandRope;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level= commandBag;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level= commandHood;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level= commandHanging;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp))){level= commandAddBreath;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown))){level= commandSubBreath;}
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
                if(gNewUserProfile.cBREATPLAY.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cBREATPLAY.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                int remaning=gNewUserProfile.cBREATPLAY.getRemaining();
                int count=gNewUserProfile.cBREATPLAY.getCount();
                desc+="\nBreaths: ";
                if(count<=0){
                    desc+="invalid count "+count;
                }else
                if(remaning>0){
                    desc+=remaning+"/"+count;
                }else{
                    desc+="used up all "+count;
                }

                embed.addField("Currently",desc,false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" tape";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" rope";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" bag";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" hood";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" hanging";
                embed.addField(" ", "Please select a type:"+desc, false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp)+" +1 breath";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown)+" -1 breath";
                embed.addField(" ", "Please select a breath option :"+desc, false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(gNewUserProfile.cBREATPLAY.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.TAPE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.ROPE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.BAG) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.HOOD) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(gNewUserProfile.cBREATPLAY.getLevel()!=BREATHPLAYLEVELS.HANGING) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level= commandTape;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level= commandRope;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level= commandBag;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level= commandHood;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level= commandHanging;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsUp))){level= commandAddBreath;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown))){level= commandSubBreath;}
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
        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            User user=null;
            boolean subdirProvided=false;
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColorRed_Barn);
            //gInteractionHook=gSlashCommandEvent.deferReply(true).complete();
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
            if(subdirProvided){
                embedBuilder.setColor(llColorBlue1);
                embedBuilder.setDescription(iRdStr.strOpeningMenu);
                gSlashInteractionHook =gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                menuLevels(gTarget);
                return;
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
