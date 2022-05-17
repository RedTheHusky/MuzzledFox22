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
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.CONFINELEVELS;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdConfine extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iConfine {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdConfine]";
	String sMainRTitle ="Confine [WIP]",gCommand="confine";
    public rdConfine(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Confine-DiscordRestraints";
        this.help = "rdConfine";
        this.aliases = new String[]{gCommand,"rd"+gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdConfine(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdConfine(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdConfine(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdConfine(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdConfine(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
        public runLocal(GuildMessageReactionAddEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
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
                setTitleStr(rdConfine.this.sMainRTitle);setPrefixStr(rdConfine.this.llPrefixStr);setCommandStr(rdConfine.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdConfine_commands");
                lsUsefullFunctions.setThreadName4Display("rdConfine");
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
                            logger.info(fName+".detect mention characters");
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuLevels(gTarget);isInvalidCommand=false;
                            }else{
                                if(gItems.length==3){
                                    switch (gItems[1].toLowerCase()){
                                        case iConfine.commandRelease:
                                        case iConfine.commandCell:
                                        case iConfine.commandCircle:
                                        case iConfine.commandPadded:
                                        case iConfine.commandPit:
                                        case iConfine.commandSack:
                                        case iConfine.commandID:
                                            rConfine(gTarget,gItems[1],gItems[2]);isInvalidCommand=false;
                                    }
                                }
                                else {
                                    switch (gItems[1].toLowerCase()){
                                        case iConfine.commandRelease:
                                        case iConfine.commandCell:
                                        case iConfine.commandCircle:
                                        case iConfine.commandPadded:
                                        case iConfine.commandPit:
                                        case iConfine.commandSack:
                                        case iConfine.commandID:
                                            rConfine(gTarget,gItems[1],"");isInvalidCommand=false;
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
                                if(gItems.length==2){
                                    switch (gItems[0].toLowerCase()){
                                        case iConfine.commandRelease:
                                        case iConfine.commandCell:
                                        case iConfine.commandCircle:
                                        case iConfine.commandPadded:
                                        case iConfine.commandPit:
                                        case iConfine.commandSack:
                                        case iConfine.commandID:
                                            rConfine(gItems[0],gItems[1]);isInvalidCommand=false;
                                    }
                                }
                                else {
                                    switch (gItems[0].toLowerCase()){
                                        case vRed:
                                            isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cBLINDFOLD.release();});
                                            break;
                                        case iConfine.commandRelease:
                                        case iConfine.commandCell:
                                        case iConfine.commandCircle:
                                        case iConfine.commandPadded:
                                        case iConfine.commandPit:
                                        case iConfine.commandSack:
                                        case iConfine.commandID:
                                            rConfine(gItems[0],"");isInvalidCommand=false;
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
        String quickSummonWithSpace2=llPrefixStr+"confine <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        desc+=newLine+quickSummonWithSpace2+iConfine.commandRelease+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandCell+" <id>"+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandCircle+" <id>"+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandPadded+" <id>"+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandPit+" <id>"+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandSack+" <id>"+endLine;
        desc+=newLine+quickSummonWithSpace2+iConfine.commandID+endLine;
        embed.addField("Commands",desc,false);
        desc="";
        desc+="\n• others have limited interaction to @PET's restraints if their confine (level#id) is not equal with the @PET";
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
    
    private void rConfine(String command, String value) {
        String fName = "[rConfine]";
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
            gNewUserProfile.cCONFINE.release();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloRelease1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloRelease2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandCell)){
            gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.CELL);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloCell1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloCell2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandPadded)){
            gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.PADDED);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloPadded1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloPadded2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandSack)){
            gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.SACK);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloSack1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloSack2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandCircle)){
            gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.CIRCLE);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloCircle1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloCircle2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandPit)){
            gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.PIT);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloPit1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strSoloPit2),llColors.llColorBlue1);
        }
        if(command.equalsIgnoreCase(commandIDClear)) {
            gNewUserProfile.cCONFINE.setID("0");
        }
        if(command.equalsIgnoreCase(commandIDSet)){
            gNewUserProfile.cCONFINE.setID(value);
        }
        if(command.equalsIgnoreCase(commandID)){
            setID();return;
        }
        saveProfile();
        if(isMenu){
            menuLevelsWearer();
        }
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private void rConfine(Member mtarget, String command, String value) {
        String fName = "[rConfine]";
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

        if(command.equalsIgnoreCase(commandIDClear)) {
            gNewUserProfile.cCONFINE.setID("0");
            saveProfile();
            if(isMenu){
                menuLevelsSomebody();
            }
        }
        else if(command.equalsIgnoreCase(commandIDSet)){
            gNewUserProfile.cCONFINE.setID(value);
            saveProfile();
            if(isMenu){
                menuLevelsSomebody();
            }
        }
        else if(command.equalsIgnoreCase(commandID)){
           setID(mtarget);return;
        }
        else{
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your confinement!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's confinement!");
                gAskHandlingHelper.doAsk(() -> {rConfine_Save4Target(mtarget,command);});
                return;
            }
            rConfine_Save4Target(mtarget,command);
        }
    }
        private void rConfine_Save4Target(Member target, String command) {
            String fName = "[rConfine_Save4Target]";
            logger.info(fName);
            logger.info(fName + ".target="+target.getUser().getName()+"("+target.getId()+")");
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(commandRelease)){
                gNewUserProfile.cCONFINE.release();
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetRelease1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetRelease2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetRelease3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandCell)){
                gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.CELL);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCell1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCell2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCell3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandPadded)){
                gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.PADDED);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPadded1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPadded2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPadded3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandSack)){
                gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.SACK);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetSack1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetSack2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetSack3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandCircle)){
                gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.CIRCLE);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCircle1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCircle2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetCircle3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandPit)){
                gNewUserProfile.cCONFINE.setOn(true);gNewUserProfile.cCONFINE.setLevel(CONFINELEVELS.PIT);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPit1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPit2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sMainRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetPit3),llColors.llColorBlue1);
            }
            saveProfile();
            if(isMenu){
                menuLevelsSomebody();
            }
        }
        private void setID(){
            String fName = "[setID]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                Message entityMessage=llSendQuickEmbedMessageResponse(gUser,sMainRTitle,"Enter #ID...", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(entityMessage);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    if(isMenu){
                                        menuLevelsSomebody();
                                    }
                                    return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    rConfine(commandIDClear,"");return;
                                }
                                else{
                                    rConfine(commandIDSet,content);return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(entityMessage);
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(entityMessage);
                        });
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setID(Member mtarget){
            String fName = "[setID]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,"Can't manipulate their id due to their access setting.", llColorRed);return;
                }
                Message entityMessage=llSendQuickEmbedMessageResponse(gUser,sMainRTitle,"Enter #ID...", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(entityMessage);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    if(isMenu){
                                        menuLevelsSomebody();
                                    }
                                    return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    rConfine(mtarget,commandIDClear,"");return;
                                }
                                else{
                                    rConfine(mtarget,commandIDSet,content);return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(entityMessage);
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(entityMessage);
                        });
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        boolean isMenu=false;
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                if(mtarget!=null){
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
                if(gNewUserProfile.cCONFINE.isBlanIDk()){
                    desc+="id: (blank)";
                }else{
                    desc+="id: "+gNewUserProfile.cCONFINE.getID();
                }
                if(gNewUserProfile.cCONFINE.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cCONFINE.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                embed.addField("Currently",desc,false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" cell";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" padded";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" circle";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" pit";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" set #ID";
                embed.addField(" ", "Please select a confine option :"+desc, false);
                if(gNewUserProfile.isWearerDenied0()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        desc+=iRdStr.strRestraintJacketArms;
                    }
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()){
                        desc+=iRdStr.strRestraintArmsCuffs;
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
                    }else{
                        desc2=iRdStr.strRestrainedYou;
                    }
                    if(gNewUserProfile.cGAG.isOn()){
                        desc2+=" How do you think you can manage the cage lock?";
                    }else{
                        desc2+=" How do you think you can lock the cage lock?";
                    }
                    embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(!gNewUserProfile.isWearerDenied0withException()){
                    if(gNewUserProfile.cCONFINE.isOn()){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                        if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.CELL)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.PADDED) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.CIRCLE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.PIT) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandCell;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandPadded;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCircle;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandPit;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){level=commandID;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rConfine(level,"");
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
                if(gNewUserProfile.cCONFINE.isBlanIDk()){
                    desc+="id: (blank)";
                }else{
                    desc+="id: "+gNewUserProfile.cCONFINE.getID();
                }
                if(gNewUserProfile.cCONFINE.isOn()){
                    desc+="\nlevel: "+gNewUserProfile.cCONFINE.getLevelAsString();
                }else{
                    desc+="\nlevel: (not confined)";
                }
                embed.addField("Currently",desc,false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" release";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" cell";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" padded";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" circle";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" pit";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" set #ID";
                embed.addField(" ", "Please select a confine option for "+gNewUserProfile.getMember().getAsMention()+" :"+desc, false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                if(gNewUserProfile.cCONFINE.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.CELL)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.PADDED) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.CIRCLE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(gNewUserProfile.cCONFINE.getLevel()!=CONFINELEVELS.PIT) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandCell;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandPadded;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCircle;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandPit;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){level=commandID;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rConfine(gNewUserProfile.getMember(),level,"");
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
