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
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.HOODLEVELS;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdHood extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Hood";String gCommand="hood";
    public rdHood(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Hood-DiscordRestraints";
        this.help = "rdHood";
        this.aliases = new String[]{gCommand,"hoods"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdHood(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdHood(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
    protected class runLocal extends rdExtension implements Runnable,iHood {
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
        public runLocal(GuildMessageReactionAddEvent ev) {
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
            this.sRTitle=rdHood.this.sMainRTitle;
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
                setTitleStr(rdHood.this.sMainRTitle);setPrefixStr(rdHood.this.llPrefixStr);setCommandStr(rdHood.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdHood_commands");
                lsUsefullFunctions.setThreadName4Display("rdHood");
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
                }
                else if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }
                else if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult){blocked();return;}
                    else if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
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
                    if(!isAdult){blocked();return;}
                    else if(!isAdult&&bdsmRestriction==1){blocked();return;}
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
                        if(gArgs!=null&&gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        assert gArgs != null;
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;}
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
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cHOOD.release();});}
                            else if(gItems[0].equalsIgnoreCase(vOn)||gItems[0].equalsIgnoreCase(vOff)){
                                rHood(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(HOODLEVELS.Gwen.getName())||gItems[0].equalsIgnoreCase(HOODLEVELS.OpenFace.getName())||gItems[0].equalsIgnoreCase(HOODLEVELS.Puppy.getName())||gItems[0].equalsIgnoreCase(HOODLEVELS.Bondage.getName())){
                                rHood(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(HOODLEVELS.Kitty.getName())||gItems[0].equalsIgnoreCase(HOODLEVELS.Drone.getName())){
                                rHood(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(HOODLEVELS.Cow.getName())||gItems[0].equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                                rHood(gItems[0].toLowerCase());isInvalidCommand=false;}
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
                rHood(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOn)||item2.equalsIgnoreCase(vOff)){
                rHood(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(HOODLEVELS.Bondage.getName())||item1.equalsIgnoreCase(HOODLEVELS.Gwen.getName())||item1.equalsIgnoreCase(HOODLEVELS.OpenFace.getName())||item1.equalsIgnoreCase(HOODLEVELS.Puppy.getName())||item1.equalsIgnoreCase(HOODLEVELS.Kitty.getName())||item1.equalsIgnoreCase(HOODLEVELS.Drone.getName())||item1.equalsIgnoreCase(HOODLEVELS.Cow.getName())||item1.equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                rHood(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(HOODLEVELS.Bondage.getName())||item2.equalsIgnoreCase(HOODLEVELS.Gwen.getName())||item2.equalsIgnoreCase(HOODLEVELS.OpenFace.getName())||item2.equalsIgnoreCase(HOODLEVELS.Puppy.getName())||item2.equalsIgnoreCase(HOODLEVELS.Kitty.getName())||item2.equalsIgnoreCase(HOODLEVELS.Drone.getName())||item2.equalsIgnoreCase(HOODLEVELS.Cow.getName())||item2.equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                rHood(target,item2.toLowerCase()); return false;}
            return true;
        }
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            String desc="N/A";
            String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> hood ";
            String quickSummonWithSpace2=llPrefixStr+"hood <@Pet> ";
            String newLine="\n  `", spacingDual="` , `" , endLine="`";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            embed.addField(strSupportTitle,strSupport,false);
            embed.addField("OwO","Thick hood that covers the wearer's head.",false);
            desc=newLine+quickSummonWithSpace2+"on"+endLine;
            desc+=newLine+quickSummonWithSpace2+"off"+endLine;
            desc+=newLine+quickSummonWithSpace2+HOODLEVELS.OpenFace.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+HOODLEVELS.Puppy.getName()+spacingDual+quickSummonWithSpace2+HOODLEVELS.Kitty.getName()+spacingDual+quickSummonWithSpace2+HOODLEVELS.Pony.getName()+spacingDual+quickSummonWithSpace2+HOODLEVELS.Cow.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+HOODLEVELS.Gwen.getName()+endLine+" open faced hood that covers the wear's mouth and jaw preventing talking";
            desc+=newLine+quickSummonWithSpace2+HOODLEVELS.Bondage.getName()+endLine+" a thick hood that covers the wearer's head completely with panels that can open and expose the wearer's eyes and mouth";
            desc+=newLine+quickSummonWithSpace2+HOODLEVELS.Drone.getName()+endLine+" thick enclosed hood, hiding their facial identity and forcing them to behave like a good drone";
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
    
        private void rHood(String command) {
            String fName = "[rHood]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cHOOD.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their hood as its part of the suit.", llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your hood as its part of the suit.", llColorRed);
                return;
            }else
            if(gNewUserProfile.isPetDenied2HaveAccessIfRestrained()){
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't restrain wile jacket is on");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed_PetDenied), llColorRed);
                    return;
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
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                    logger.info(fName + ".can't restrain wile cuffs are on");
                    sendOrUpdatePrivateEmbed(sRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                    return;
                }
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your hood due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your hood due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your hood due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(vOn)){
                if(!gIsOverride&&gNewUserProfile.cHOOD.isOn()){
                    logger.info(fName + ".hood is already on");
                    sendOrUpdatePrivateEmbed(sRTitle,"The hood is already on, silly.", llColorPurple1);
                }else{
                    gNewUserProfile.cHOOD.setOn( true);
                    if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().isBlank()){gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());}
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());}
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Gwen.getName())){
                        if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                            putFieldEntryGag(nOn, true);putFieldEntryGag(nLevel, GAGLEVELS.Severe.getName());
                        }
                    }
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Puppy.getName())){
                        if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                            putFieldEntryGag(nOn, true);putFieldEntryGag(nLevel, GAGLEVELS.Puppy.getName());
                        }
                    }
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Kitty.getName())){
                        if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                            putFieldEntryGag(nOn, true);putFieldEntryGag(nLevel, GAGLEVELS.Kitty.getName());
                        }
                    }
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Cow.getName())){
                        if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                            putFieldEntryGag(nOn, true);putFieldEntryGag(nLevel, GAGLEVELS.Cow.getName());
                        }
                    }
                    else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                        if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                            putFieldEntryGag(nOn, true);putFieldEntryGag(nLevel, GAGLEVELS.Pony.getName());
                        }
                    }
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put  hood over your head.", llColorBlue1);
                    userNotificationHood( actionPutOn,gUser.getAsMention()+" has put hood on their head.");
                }
            }else
            if(!gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(command)){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,"The hood is already on, silly.", llColorPurple1);
                return;
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Bondage.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Bondage.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put a thick hood that covers your head completely with panels that can open and expose the wearer's eyes and mouth.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a thick hood that covers their head completely with panels that can open and expose the wearer's eyes and mouth.");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Puppy.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Puppy.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Puppy.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put puppy hood over your head, making your look like a puppy.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put puppy hood on their head, making them look like a puppy."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Kitty.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Kitty.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Kitty.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put a kitty hood over your head as you start to meow.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a kitty hood on their head as they start to meow."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Drone.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Drone.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.DroneMask.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put a drone hood over your head, hiding your identity as you're nothing more an object to be used.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a drone hood on their head, hiding their identity as they nothing more than an object to use."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.OpenFace.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put an open face hood over your head, allow free use of your jaw.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put open face hood over their head, allow free use of jaw.");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Gwen.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Gwen.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Severe.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put an gwen hood over your head, covering your mouth and jaw preventing talking.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put gwen hood over their head, covering their wear's mouth and jaw preventing talking."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Pony.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Pony.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put an pony hood over your head, covering your mouth with a bit-gag like those used on horses", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put pony hood over their head, covering their mouth with a bit-gag."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Cow.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Cow.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Cow.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put an cow hood over your head to represent yourself as a milking product.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a cow hood over their head, to represent themselfs a milking product."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cHOOD.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        logger.info(fName + ".can't take off do to jacket");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iStraitjacket.strCantGrabAnything), llColorRed);
                        userNotificationHood( actionStruggle, gUser.getAsMention()+" attempted to take off their hood but the straitjacket sleeves are preventing it.");
                    }
                    else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        userNotificationHood( actionStruggle,gUser.getAsMention()+" struggled to take off the hood from their head but failed do to its locked with a padlock.");
                    }else{
                        gNewUserProfile.cHOOD.setOn( false);
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed to pull the hood off from your head.", llColorBlue1);
                        userNotificationHood( actionTakeOff, gUser.getAsMention()+" managed to take off their hood. Someone must have forgot to secure it.");
                    }
                }else{
                    logger.info(fName + ".hood is not on");
                    sendOrUpdatePrivateEmbed(sRTitle,"The hood are not on, silly.", llColorPurple1);
                }
            }
            saveProfile();
        }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void rHood(Member mtarget, String command) {
            String fName = "[rHood]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }
            if(!gIsOverride&& iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cHOOD.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their hood as its part of the suit.", llColorRed);
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their hood as its part of the suit.", llColorRed);
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their hood due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their hood due to their access setting.", llColorRed);
            }else
            if(command.equalsIgnoreCase("on")){
                if(!gIsOverride&&gNewUserProfile.cHOOD.isOn()){
                    logger.info(fName + ".hood is already on");
                    sendOrUpdatePrivateEmbed(sRTitle,"The hood is already on.", llColorPurple1);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place a hood around your head.");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place a hood around !TARGET's head.");
                        gAskHandlingHelper.doAsk(() -> {rHoodSave4target(mtarget,command);});
                        return;
                    }
                    rHoodSave4target(mtarget,command);
                }
            }
            else if(!gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(command)){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,"The hood is already on, silly.", llColorPurple1);
            }
            else if(command.equalsIgnoreCase(HOODLEVELS.Bondage.getName())||command.equalsIgnoreCase(HOODLEVELS.Puppy.getName())||command.equalsIgnoreCase(HOODLEVELS.Kitty.getName())
            ||command.equalsIgnoreCase(HOODLEVELS.Drone.getName())||command.equalsIgnoreCase(HOODLEVELS.OpenFace.getName())||command.equalsIgnoreCase(HOODLEVELS.Gwen.getName())
            ||command.equalsIgnoreCase(HOODLEVELS.Pony.getName())||command.equalsIgnoreCase(HOODLEVELS.Cow.getName())){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place a hood around your head.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place a hood around !TARGET's head.");
                    gAskHandlingHelper.doAsk(() -> {rHoodSave4target(mtarget,command);});
                    return;
                }
                rHoodSave4target(mtarget,command);
            }
            else if(command.equalsIgnoreCase("off")){
                if(gNewUserProfile.cHOOD.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    }else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take the hood off around your head.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take the hood off around !TARGET's head.");
                            gAskHandlingHelper.doAsk(() -> {rHoodSave4target(mtarget,command);});
                            return;
                        }
                        rHoodSave4target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".hood is not on");
                    llSendQuickEmbedMessage(target,sMainRTitle,"The hood are not on.", llColorPurple1);
                }
            }
        }
        private void rHoodSave4target(Member mtarget, String command) {
            String fName = "[rHood]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);

            if(command.equalsIgnoreCase("on")){
                gNewUserProfile.cHOOD.setOn( true);
                if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().isBlank()){gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());}
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());}
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Gwen.getName())){
                    if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                        putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Severe.getName());
                    }
                }
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                    if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                        putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Pony.getName());
                    }
                }
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Puppy.getName())){
                    if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                        putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Puppy.getName());
                    }
                }
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Kitty.getName())){
                    if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                        putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Kitty.getName());
                    }
                }
                else if(gIsOverride&&gNewUserProfile.cHOOD.getLevelAsString().equalsIgnoreCase(HOODLEVELS.Cow.getName())){
                    if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                        putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Cow.getName());
                    }
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put hood over "+target.getAsMention()+"'s head.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" places the hood over your head..", llColorBlue1);
                userNotificationHood(actionPutOn, gUser.getAsMention()+" places hood over "+target.getAsMention()+"'s head'");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Bondage.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Bondage.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a thick hood over "+target.getAsMention()+" head that covers their head completely with panels that can open and expose the wearer's eyes and mouth.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" puts a thick hood that covers your head completely with panels that can open and expose the wearer's eyes and mouth.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a thick hood over "+target.getAsMention()+" head that covers their head completely with panels that can open and expose the wearer's eyes and mouth.");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Puppy.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Puppy.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Puppy.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put puppy hood over "+target.getAsMention()+" head, making them look like a puppy.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put puppy hood over your head, making your look like a puppy.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put puppy hood on "+target.getAsMention()+" head, making them look like a puppy."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Kitty.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Kitty.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Kitty.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put a kitty hood over "+target.getAsMention()+" head as they start to meow.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put a kitty hood over your head as you start to meow.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a kitty hood on "+target.getAsMention()+" head as they start to meow."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Drone.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Drone.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.DroneMask.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put a drone hood over "+target.getAsMention()+" head, hiding their identity as they're nothing more an object to be used.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put a drone hood over your head, hiding your identity as you're nothing more an object to be used.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put a drone hood on "+target.getAsMention()+" head, hiding their identity as they nothing more than an object to use."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.OpenFace.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.OpenFace.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put an open face hood over "+target.getAsMention()+" head, allow free use of their jaw.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put an open face hood over your head, allow free use of your jaw.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put open face hood over "+target.getAsMention()+" head, allow free use of jaw.");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Gwen.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Gwen.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Severe.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put an gwen hood over "+target.getAsMention()+" head, covering their mouth and jaw preventing talking.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put an gwen hood over your head, covering your mouth and jaw preventing talking.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put gwen hood over "+target.getAsMention()+" head, covering their wear's mouth and jaw preventing talking."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Pony.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Pony.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Pony.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put an pony hood over "+target.getAsMention()+" head, perfect headwear to work the farm.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put an pony hood over your head, perfect headwear to work the farm.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put pony hood over "+target.getAsMention()+" head, perfect headwear to work the farm."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase(HOODLEVELS.Cow.getName())){
                gNewUserProfile.cHOOD.setOn( true);gNewUserProfile.cHOOD.setLevel( HOODLEVELS.Cow.getName());
                if(gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)&& gBDSMCommands.muzzle.hasPermission2TargetGagCommand(mtarget)) {
                    putFieldEntryGag(nOn,true);putFieldEntryGag(nLevel,GAGLEVELS.Cow.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,"You put an cow hood over "+target.getAsMention()+" head, marking them as a milking product.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" put an cow hood over your head, marking you as a milking product.", llColorBlue1);
                userNotificationHood( actionPutOn,gUser.getAsMention()+" has put cow hood over "+target.getAsMention()+" head, marking them as a milking product."+"\n(Type `"+nGagSafeword+"` to ungag");
            }else
            if(command.equalsIgnoreCase("off")){
                gNewUserProfile.cHOOD.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You pull the hood off "+target.getAsMention()+"'s head.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" pulls the hood off your head..", llColorBlue1);
                userNotificationHood(actionTakeOff,  gUser.getAsMention()+" pulls the hood off "+target.getAsMention()+"'s head'");
            }
            saveProfile();
        }
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
        String gCommandFileMainPath =gFileMainPath+"hood/menuLevels.json";
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(gNewUserProfile.gUserProfile.jsonObject.has(nHood)&&gNewUserProfile.cHOOD.isOn()){
                    String level=gNewUserProfile.cHOOD.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+HOODLEVELS.Puppy.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+HOODLEVELS.Kitty.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+HOODLEVELS.Pony.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+HOODLEVELS.Drone.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+HOODLEVELS.Cow.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+HOODLEVELS.Gwen.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+HOODLEVELS.OpenFace.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+HOODLEVELS.Bondage.getName();
                embed.addField(" ", "Please select a hood option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isWearerDenied0withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cHOOD.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cHOOD.isOn()))desc=iRdStr.strRestraintLocked;
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
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_hood");
                    if(gNewUserProfile.cHOOD.isOn()){
                        switch (gNewUserProfile.cHOOD.getLevel()){
                            case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Kitty:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Pony:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Drone:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case Cow:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Gwen:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case OpenFace:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case Bondage:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                        }
                        if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())messageComponentManager.selectContainer.setDisabled();

                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
                    }
                    if(gNewUserProfile.isWearerDenied0withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cHOOD.isOn())){
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
                if(gNewUserProfile.gUserProfile.jsonObject.has(nHood)&&gNewUserProfile.cHOOD.isOn()){
                    String level=gNewUserProfile.cHOOD.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+HOODLEVELS.Puppy.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+HOODLEVELS.Kitty.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+HOODLEVELS.Pony.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+HOODLEVELS.Drone.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+HOODLEVELS.Cow.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+HOODLEVELS.Gwen.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+HOODLEVELS.OpenFace.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+HOODLEVELS.Bondage.getName();
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a hood option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gNewUserProfile.cHOOD.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Puppy)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Kitty)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Pony)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Drone)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Cow)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Gwen)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.OpenFace)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    if(gNewUserProfile.cHOOD.getLevel()!=HOODLEVELS.Bondage)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_hood");
                    if(gNewUserProfile.cHOOD.isOn()){
                        switch (gNewUserProfile.cHOOD.getLevel()){
                            case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Kitty:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Pony:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Drone:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case Cow:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Gwen:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case OpenFace:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case Bondage:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember))messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
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
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        rHood(level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value){
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=HOODLEVELS.Puppy.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=HOODLEVELS.Kitty.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=HOODLEVELS.Pony.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=HOODLEVELS.Drone.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=HOODLEVELS.Cow.getName();break;
                                    case lsUnicodeEmotes.aliasSix:level=HOODLEVELS.Gwen.getName();break;
                                    case lsUnicodeEmotes.aliasSeven:level=HOODLEVELS.OpenFace.getName();break;
                                    case lsUnicodeEmotes.aliasEight:level=HOODLEVELS.Bondage.getName();break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rHood(level);
                                    }
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=HOODLEVELS.Puppy.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=HOODLEVELS.Kitty.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=HOODLEVELS.Pony.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=HOODLEVELS.Drone.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=HOODLEVELS.Cow.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){level=HOODLEVELS.Gwen.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){level=HOODLEVELS.OpenFace.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){level=HOODLEVELS.Bondage.getName();}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rHood(level);
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
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        rHood(gNewUserProfile.getMember(),level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value){
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=HOODLEVELS.Puppy.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=HOODLEVELS.Kitty.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=HOODLEVELS.Pony.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=HOODLEVELS.Drone.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=HOODLEVELS.Cow.getName();break;
                                    case lsUnicodeEmotes.aliasSix:level=HOODLEVELS.Gwen.getName();break;
                                    case lsUnicodeEmotes.aliasSeven:level=HOODLEVELS.OpenFace.getName();break;
                                    case lsUnicodeEmotes.aliasEight:level=HOODLEVELS.Bondage.getName();break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rHood(gNewUserProfile.getMember(),level);
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=HOODLEVELS.Puppy.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=HOODLEVELS.Kitty.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=HOODLEVELS.Pony.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=HOODLEVELS.Drone.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=HOODLEVELS.Cow.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){level=HOODLEVELS.Gwen.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){level=HOODLEVELS.OpenFace.getName();}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){level=HOODLEVELS.Bondage.getName();}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rHood(gNewUserProfile.getMember(),level);
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
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
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

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void userNotificationHood(int action,String desc){
                String fName="[userNotificationLegCuffs]";
                logger.info(fName+"action="+action);
                logger.info(fName+"desc="+desc);
                try {
                    if(gBDSMCommands.restraints.getNotificationDisabled()){
                        logger.warn(fName+"notification disabled");
                        return;
                    }
                    String field=nHood;
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
                                    llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
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


        private void putFieldEntryGag(String name, Object value){
            String fName="[putFieldEntry]";
            logger.info(fName+".field="+ nGag);
            logger.info(fName+".name="+name);
            logger.info(fName+".value="+value);
            gNewUserProfile.gUserProfile.putFieldEntry(nGag,name,value);
        }

}}
