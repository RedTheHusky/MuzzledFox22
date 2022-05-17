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
import restraints.models.enums.STRAITJACKETARMSLEVEL;
import restraints.models.enums.STRAITJACKETCROTCHSLEVEL;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdStraitjacket extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iStraitjacket {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String quickAlias="sj",gCommand="sj";
	String sMainRTitle ="Straitjacket";
    public rdStraitjacket(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Straitjacket-DiscordRestraints";
        this.help = "rdStraitjacket";
        this.aliases = new String[]{"sj","straitjacket","jacket"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdStraitjacket(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdStraitjacket(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
        public runLocal(GuildMessageReactionAddEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
        }
        public runLocal(SlashCommandEvent ev) {
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
                setTitleStr(rdStraitjacket.this.sMainRTitle);setPrefixStr(rdStraitjacket.this.llPrefixStr);setCommandStr(rdStraitjacket.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdStraitjacket_commands");
                lsUsefullFunctions.setThreadName4Display("rdStraitjacket");
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
                else if(gSlashCommandEvent!=null){
                    logger.info(cName + fName + "slash@");
                    /*
                     if(!checkIFAllowed2UseCommand1_slash()){
                        return;
                    }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                     */
                    rSlashNT();
                }
                else if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
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
                        if(gItems[0].equalsIgnoreCase("help")){rHelp("main");isInvalidCommand=false;}
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
                                if(gItems.length>=5){
                                    isInvalidCommand= quickCommands(gItems[3], gItems[4], gTarget);
                                }
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
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cSTRAITJACKET.release();});}
                            else if(gItems[0].equalsIgnoreCase(vOn)){rStraitjacket(vOn);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(vOff)){rStraitjacket(vOff);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandStrapArms)||gItems[0].equalsIgnoreCase("straparm")){rStraitjacket(commandStrapArms);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandUnStrapArms)||gItems[0].equalsIgnoreCase("unstraparm")){rStraitjacket(commandUnStrapArms);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandReverseStrapArms)){rStraitjacket(commandReverseStrapArms);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandStrapCrotch)){rStraitjacket(commandStrapCrotch);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandStrapMonoCrotch)){rStraitjacket(commandStrapMonoCrotch);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandStrapTripleCrotch)){rStraitjacket(commandStrapTripleCrotch);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(commandUnStrapCrotch)){rStraitjacket(commandUnStrapCrotch);isInvalidCommand=false;}
                            else  if(gItems[0].equalsIgnoreCase(commandStrap)){
                                if(gItems.length>=2){
                                    if(gItems[1].equalsIgnoreCase(commandArms)||gItems[1].equalsIgnoreCase(commandArm)){rStraitjacket(commandStrapArms);isInvalidCommand=false;}
                                    if(gItems[1].equalsIgnoreCase(commandCrotch)){rStraitjacket(commandStrapCrotch);isInvalidCommand=false;}
                                }else{
                                    logger.warn(fName+".invalid args length");
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase(commandUnStrap)){
                                if(gItems.length>=2){
                                    if(gItems[1].equalsIgnoreCase(commandArms)||gItems[1].equalsIgnoreCase(commandArm)){rStraitjacket(commandUnStrapArms);isInvalidCommand=false;}
                                    if(gItems[1].equalsIgnoreCase(commandCrotch)){rStraitjacket(commandUnStrapCrotch);isInvalidCommand=false;}
                                }else{
                                    logger.warn(fName+".invalid args length");
                                }
                            }
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
            if(item1.equalsIgnoreCase(vOn)){rStraitjacket(target,vOn);return false;}
            else if(item1.equalsIgnoreCase(vOff)){rStraitjacket(target,vOff);return false;}
            else if(item1.equalsIgnoreCase(commandStrapArms)||item1.equalsIgnoreCase("straparm")){rStraitjacket(target,commandStrapArms);return false;}
            else if(item1.equalsIgnoreCase(commandReverseStrapArms)){rStraitjacket(target,commandReverseStrapArms);return false;}
            else if(item1.equalsIgnoreCase(commandUnStrapArms)||item1.equalsIgnoreCase("unstraparm")){rStraitjacket(target,commandUnStrapArms);return false;}
            else if(item1.equalsIgnoreCase(commandStrapCrotch)){rStraitjacket(target,commandStrapCrotch);return false;}
            else if(item1.equalsIgnoreCase(commandStrapMonoCrotch)){rStraitjacket(target,commandStrapMonoCrotch);return false;}
            else if(item1.equalsIgnoreCase(commandStrapTripleCrotch)){rStraitjacket(target,commandStrapTripleCrotch);return false;}
            else if(item1.equalsIgnoreCase(commandUnStrapCrotch)){rStraitjacket(target,commandUnStrapCrotch);return false;}
            else if(item1.equalsIgnoreCase(commandStrap)){
                    if(item2.equalsIgnoreCase(commandArms)||item2.equalsIgnoreCase(commandArm)){rStraitjacket(target,commandStrapArms);return false;}
                    if(item2.equalsIgnoreCase(commandCrotch)){rStraitjacket(target,commandStrapCrotch);return false;}
            }
            else if(item1.equalsIgnoreCase(commandUnStrap)){
                    if(item2.equalsIgnoreCase(commandArms)||item2.equalsIgnoreCase(commandArm)){rStraitjacket(target,commandUnStrapArms);return false;}
                    if(item2.equalsIgnoreCase(commandCrotch)){rStraitjacket(target,commandUnStrapCrotch);return false;}
            }
            return true;
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc;
        String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> ";
        String quickSummonWithSpace2=llPrefixStr+quickAlias+" <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Strict straitjacket for times when maximum restraining is needed.",false);
        desc="Places the sub inside a straitjacket";
        desc+=newLine+quickSummonWithSpace2+vOn+endLine+" puts on jacket";
        desc+=newLine+quickSummonWithSpace2+vOff+endLine+" takes off jacket";
        embed.addField("General",desc, false);
        desc="Strap the subs arms";
        desc+=newLine+quickSummonWithSpace2+commandStrapArms+endLine+" straps the arms";
        desc+=newLine+quickSummonWithSpace2+commandUnStrapArms+endLine+" unstraps the arms";
        embed.addField("Arm Strap",desc, false);
        desc="Strap the crotch strap, the one between their legs";
        desc+=newLine+quickSummonWithSpace2+commandStrapCrotch+endLine+" straps the crotch strap";
        desc+=newLine+quickSummonWithSpace2+commandUnStrapCrotch+endLine+" unstraps the crotch strap";
        embed.addField("Crotch Strap",desc, false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
   
    private boolean rStraitjacket(String command){
        String fName="[rStraitjacket]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}

        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your straitjacket due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your straitjacket due to access set to owner. Only your owner and sec-owners have access", llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your straitjacket due to access set to public. While public everyone else can access it except you.", llColorRed);
            return false;
        }
        if(!gIsOverride&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equals(nNone)){
            logger.info(fName + ".can't restrain jacket if the cuffs are on");
            sendOrUpdatePrivateEmbed(sRTitle,"How do you think you will use the straitjacket while you already have your arms tied?", llColorRed);
            return false;
        }
        if(command.equalsIgnoreCase(vOn)){
            if(gIsOverride||gNewUserProfile.cSTRAITJACKET.isOn()){
                logger.info(fName + ".jacket is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The jacket is already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cSTRAITJACKET.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put on your jacket.", llColorBlue1);
                userNotificationStraitjacket(actionPutOn, gUser.getAsMention()+" has put on their straitjacket.");
            }
        }
        else if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                    return false;
                }
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    userNotificationStraitjacket(actionStruggle,gUser.getAsMention()+" struggled to take off the straitjacket but failed due to its locked with a padlock.");
                }
                else if(!gIsOverride&&(gNewUserProfile.cSTRAITJACKET.isArmStrapped()||gNewUserProfile.cSTRAITJACKET.isCrotchStrapped())){
                    logger.info(fName + ".can't take off do to straps");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Can't take it off while strapped in it!", llColorRed);
                    userNotificationStraitjacket(actionStruggle,gUser.getAsMention()+" struggled to take off the straitjacket but the straps are preventing it.");
                }else{
                    gNewUserProfile.cSTRAITJACKET.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed to pull off your straitjacket.", llColorBlue1);
                    userNotificationStraitjacket(actionTakeOff,gUser.getAsMention()+" managed to take off their straitjacket. Someone must have forgot to secure it.");
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapArms)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                gNewUserProfile.cSTRAITJACKET.setArmsStrapped( true); gNewUserProfile.cSTRAITJACKET.setArmsLevel( STRAITJACKETARMSLEVEL.Front.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Somehow you managed to strap your arms. It must have been hard.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,"Somehow "+gUser.getAsMention()+" managed to strap their arms. It must have been hard.");
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandReverseStrapArms)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                gNewUserProfile.cSTRAITJACKET.setArmsStrapped( true);gNewUserProfile.cSTRAITJACKET.setArmsLevel( STRAITJACKETARMSLEVEL.Reverse.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Somehow you managed to strap your arms behind your back. It must have been hard.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,"Somehow "+gUser.getAsMention()+" managed to strap their arms behind your back. It must have been hard.");
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapCrotch)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            else if(gNewUserProfile.cSTRAITJACKET.isOn()){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Double.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Both crotch straps strapped.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,"Somehow "+gUser.getAsMention()+" managed to secure their both crotch straps. No way to pull it above ones head, at least its comfortable.");
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapMonoCrotch)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            else if(gNewUserProfile.cSTRAITJACKET.isOn()){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Mono.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Crotch strap strapped.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,"Somehow "+gUser.getAsMention()+" managed to strap their crotch strap. Being just one strap, its going to be uncomfortable if they struggle much.");
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapTripleCrotch)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            else if(gNewUserProfile.cSTRAITJACKET.isOn()){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Triple.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"All 3 crotch straps strapped.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,"Somehow "+gUser.getAsMention()+" managed to strap all 3 of their crotch straps. Not only extra secure, but the middle strap will pull every time they struggle.");
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnStrapArms)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            if(gNewUserProfile.cSTRAITJACKET.isOn()) {
                if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                        logger.info(fName + ".permalock");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                        return false;
                    }
                    if(gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't unstrap do to locked");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Can't unstrap your arms while its locked!", llColorRed);
                        userNotificationStraitjacket(actionStruggle,gUser.getAsMention()+" struggled to unstrap their arms but failed due to its locked with a padlock.");
                    }else{
                        gNewUserProfile.cSTRAITJACKET.setArmsStrapped( false);gNewUserProfile.cSTRAITJACKET.setArmsLevel( "");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed somehow to unstrap your arms. It must have been really hard.", llColorBlue1);
                        userNotificationStraitjacket(actionUnSecured, gUser.getAsMention()+" wiggled enought to unstrap their arms strap. Somebody failed to secure it");
                    }
                }else{
                    logger.info(fName + ".arms not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstrap that is not strapped. You must be going crazy.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnStrapCrotch)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");

                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);
                return false;
            }
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                        logger.info(fName + ".permalock");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                        return false;
                    }
                    if(gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        userNotificationStraitjacket(actionStruggle,gUser.getAsMention()+" struggled to unstrap their crotch but failed due to its locked with a padlock.");
                    }
                    else if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                        logger.info(fName + ".arms strapped> cant take off crotch");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Can't unstrap crotch straps while arms strapped!", llColorRed);
                        userNotificationStraitjacket(actionStruggle,gUser.getAsMention()+" tried to unstrap the crotch strap forgetting that their arms was strapped");
                    }else{
                       gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( false);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( "");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Unstrapped crotch strap.", llColorBlue1);
                        userNotificationStraitjacket(actionUnSecured,gUser.getAsMention()+" managed to unstrap their crotch strap.");
                    }
                }else{
                    logger.info(fName + ".crotch not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstrap that is not strapped. You must be going crazy.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStitch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(!gNewUserProfile.cSTRAITJACKET.isStiched()){
                        gNewUserProfile.cSTRAITJACKET.setStiched(true);
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iStraitjacket.strSoloStiching1), llColorBlue1);
                        userNotificationStraitjacket(actionUnSecured,stringReplacer(iStraitjacket.strSoloStiching2));
                    }else{
                        logger.info(fName + ".stitched");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't stitch that is already stitched. You must be going crazy.", llColorPurple1);
                    }
                }else{
                    logger.info(fName + ".crotch not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't stitch that is not strapped. You must be going crazy.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnstitch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(gNewUserProfile.cSTRAITJACKET.isStiched()){
                        gNewUserProfile.cSTRAITJACKET.setStiched(false);
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iStraitjacket.strSoloUnStiching1), llColorBlue1);
                        userNotificationStraitjacket(actionUnSecured,stringReplacer(iStraitjacket.strSoloUnStiching2));
                    }else{
                        logger.info(fName + ".not stiched");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't unstitch that is not stitched. You must be going crazy.", llColorPurple1);
                    }
                }else{
                    logger.info(fName + ". not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstitch that is not strapped. You must be going crazy.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strJacketNotOn, llColorPurple1);
            }
        }
        saveProfile();
        return true;
    }
    private boolean rStraitjacket(Member mtarget,String command){
        String fName="[rStraitjacket]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
            logger.info(fName + ".isSameConfinement>return");
            sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
            return false;
        }
        if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

            }
            return false;
        }
        if(!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their straitjacket due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their straitjacket due to their access setting.", llColorRed);return false;
        }else
        if(!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equals(nNone)){
            logger.info(fName + ".can't restrain jacket if the cuffs are on");
            sendOrUpdatePrivateEmbed(sRTitle,"How do you think you will use the straitjacket while they already have their arms tied with cuffs?", llColorRed);
            return false;
        }
        if(command.equalsIgnoreCase(vOn)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                logger.info(fName + ".jacket is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The jacket is already on.", llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you inside a straitjacket.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET inside a straitjacket.");
                    gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                    return false;
                }
                rStraitjacketSave4Target(mtarget,command);
            }
        }
        else if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                    return false;
                }
                if(gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                }
                else if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()||gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                    logger.info(fName + ".can't take off do to straps");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't take it off while strapped secured!", llColorRed);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take the straitjacket off you..");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take the straitjacket off !TARGET.");
                        gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                        return false;
                    }
                    rStraitjacketSave4Target(mtarget,command);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapArms)||command.equalsIgnoreCase(commandReverseStrapArms)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can secure your straitjacket arms straps.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can secure !TARGET 's straitjacket arms straps.");
                    gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                    return false;
                }
                rStraitjacketSave4Target(mtarget,command);
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStrapCrotch)||command.equalsIgnoreCase(commandStrapMonoCrotch)||command.equalsIgnoreCase(commandStrapTripleCrotch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can secure your straitjacket crotch straps.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can secure !TARGET 's straitjacket crotch straps.");
                    gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                    return false;
                }
                rStraitjacketSave4Target(mtarget,command);
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnStrapArms)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()) {
                if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                        logger.info(fName + ".permalock");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                        return false;
                    }
                    if(gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't unstrap do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't unstrap "+target.getAsMention()+"'s arms while its locked!", llColorRed);
                    }else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can release your straitjacket arms straps.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can release !TARGET 's straitjacket arms straps.");
                            gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                            return false;
                        }
                        rStraitjacketSave4Target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".arms not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstrap that is not strapped.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnStrapCrotch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                        logger.info(fName + ".permalock");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                        return false;
                    }
                    if(gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    }
                    else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can release your straitjacket crotch straps.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can release !TARGET 's straitjacket crotch straps.");
                            gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                            return false;
                        }
                        rStraitjacketSave4Target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".crotch not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstrap that is not strapped.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandStitch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(!gNewUserProfile.cSTRAITJACKET.isStiched()){
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can secure your straitjacket by stitching it.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can secure !TARGET 's straitjacket by stitching it.");
                            gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                            return false;
                        }
                        rStraitjacketSave4Target(mtarget,command);
                    }else{
                        logger.info(fName + ".stitched");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't stitch that is already stitched. You must be going crazy.", llColorPurple1);
                    }
                }else{
                    logger.info(fName + ".crotch not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't stitch that is not strapped. You must be going crazy.", llColorPurple1);
                }

            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        else if(command.equalsIgnoreCase(commandUnstitch)){
            if(gNewUserProfile.cSTRAITJACKET.isOn()){
                if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                    if(gNewUserProfile.cSTRAITJACKET.isStiched()){
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can undo your straitjacket stitches.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can undo !TARGET 's straitjacket stitches.");
                            gAskHandlingHelper.doAsk(() -> {rStraitjacketSave4Target(mtarget,command);});
                            return false;
                        }
                        rStraitjacketSave4Target(mtarget,command);
                    }else{
                        logger.info(fName + ".not stiched");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't unstitch that is not stitched. You must be going crazy.", llColorPurple1);
                    }
                }else{
                    logger.info(fName + ". not strapped");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't unstitch that is not strapped. You must be going crazy.", llColorPurple1);
                }
            }else{
                logger.info(fName + ".jacket is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The straitjacket is not on.", llColorPurple1);
            }
        }
        return true;
    }
        private void rStraitjacketSave4Target(Member mtarget,String command){
            String fName="[rStraitjacketSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(vOn)){
                gNewUserProfile.cSTRAITJACKET.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You put on "+target.getAsMention()+" a straitjacket.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" places you in a straitjacket.", llColorBlue1);
                userNotificationStraitjacket(actionPutOn, gUser.getAsMention()+" places "+target.getAsMention()+" in a straitjacket.");
            }else
            if(command.equalsIgnoreCase(vOff)){
                gNewUserProfile.cSTRAITJACKET.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You pull the straitjacket off "+target.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" pulls the straitjacket off.", llColorBlue1);
                userNotificationStraitjacket(actionTakeOff,gUser.getAsMention()+" pulls off "+target.getAsMention()+"'s straitjacket.");
            }else
            if(command.equalsIgnoreCase(commandStrapArms)){
                gNewUserProfile.cSTRAITJACKET.setArmsStrapped( true);gNewUserProfile.cSTRAITJACKET.setArmsLevel( STRAITJACKETARMSLEVEL.Front.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You secure "+target.getAsMention()+"'s straitjacket arm straps.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" secures your straitjacket arm straps.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,gUser.getAsMention()+" secures "+target.getAsMention()+"'s straitjacket arm straps.");
            }else
            if(command.equalsIgnoreCase(commandReverseStrapArms)){
                gNewUserProfile.cSTRAITJACKET.setArmsStrapped( true);gNewUserProfile.cSTRAITJACKET.setArmsLevel( STRAITJACKETARMSLEVEL.Reverse.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You secure "+target.getAsMention()+"'s straitjacket arm straps, with their arms behind their back.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" secures your straitjacket arm straps, with their arms behind their back.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,gUser.getAsMention()+" secures "+target.getAsMention()+"'s straitjacket arm straps, with their arms behind their back.");
            }else
            if(command.equalsIgnoreCase(commandStrapCrotch)){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Double.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You secure "+target.getAsMention()+"'s straitjacket crotch straps.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" secures your straitjacket crotch straps.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,gUser.getAsMention()+" secures "+target.getAsMention()+"'s straitjacket crotch straps.");
            }else
            if(command.equalsIgnoreCase(commandStrapMonoCrotch)){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Mono.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You secure "+target.getAsMention()+"'s straitjacket crotch strap. If they struggle its going to dig between their checks.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" secures your straitjacket crotch strap. If you struggle its going to dig between your checks.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,gUser.getAsMention()+" secures "+target.getAsMention()+"'s straitjacket crotch strap. If they struggle its going to dig between their checks.");
            }else
            if(command.equalsIgnoreCase(commandStrapTripleCrotch)){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( true);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( STRAITJACKETCROTCHSLEVEL.Triple.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You secure "+target.getAsMention()+"'s straitjacket crotch straps. With 3 straps its going to be hard for them to break free.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" secures your straitjacket crotch straps. With 3 straps its going to be hard for you to break free.", llColorBlue1);
                userNotificationStraitjacket(actionSecured,gUser.getAsMention()+" secures "+target.getAsMention()+"'s straitjacket crotch straps. With 3 straps its going to be hard for them to break free.");
            }else
            if(command.equalsIgnoreCase(commandUnStrapArms)){
                gNewUserProfile.cSTRAITJACKET.setArmsStrapped( false);gNewUserProfile.cSTRAITJACKET.setArmsLevel("");
                sendOrUpdatePrivateEmbed(sRTitle,"You untie "+target.getAsMention()+"'s straitjacket arm straps.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" unties your straitjacket arm straps.", llColorBlue1);
                userNotificationStraitjacket(actionUnSecured,gUser.getAsMention()+" unties "+target.getAsMention()+"'s straitjacket arm straps.");
            }else
            if(command.equalsIgnoreCase(commandUnStrapCrotch)){
               gNewUserProfile.cSTRAITJACKET.setCrotchStrapped( false);gNewUserProfile.cSTRAITJACKET.setCrotchLevel( "");
                sendOrUpdatePrivateEmbed(sRTitle,"You unties "+target.getAsMention()+"'s straitjacket crotch straps.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" unties your straitjacket crotch straps.", llColorBlue1);
                userNotificationStraitjacket(actionUnSecured,gUser.getAsMention()+" unties "+target.getAsMention()+"'s straitjacket crotch straps.");
            }else
            if(command.equalsIgnoreCase(commandStitch)){
                gNewUserProfile.cSTRAITJACKET.setStiched( true);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strTargetStiching1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iStraitjacket.strTargetStiching2), llColorBlue1);
                userNotificationStraitjacket(actionSecured,stringReplacer(iStraitjacket.strTargetStiching3));
            }
            if(command.equalsIgnoreCase(commandUnstitch)){
                gNewUserProfile.cSTRAITJACKET.setStiched(false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strTargetUnStiching1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iStraitjacket.strTargetUnStiching2), llColorBlue1);
                userNotificationStraitjacket(actionSecured,stringReplacer(iStraitjacket.strTargetUnStiching3));
            }
            saveProfile();
            logger.info(fName+"isMenuLevel="+isMenuLevel);
            menuLevels(mtarget);
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

        boolean isMenuLevel=false;
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try {
                if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
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
                } else {
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
                    menuLevelsWearer();
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        String gCommandFileMainPath =gFileMainPath+"sj/menuLevels.json";
        private void menuLevelsWearer(){
            String fName="[menuLevelsWearer]";
            logger.info(fName);
            try {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc = "",desc2="";
                embed.setColor(llColorOrange);
                embed.setTitle(sRTitle + " Options");
                embed.addField(strSupportTitle,strSupport,false);
                if (gNewUserProfile.cSTRAITJACKET.isOn()) {
                    String armstrap = "arms:free", crotchstrap = "crotch:free";
                    if (gNewUserProfile.cSTRAITJACKET.isArmStrapped()) {
                        armstrap = "arms: secured";
                        if(gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                            armstrap="arms: front secured";
                        }
                        if(gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                            armstrap="arms: reverse secured";
                        }
                    }
                    if (gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()) {
                        crotchstrap = "crotch: secured";
                        if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                            crotchstrap="crotch: double strap secured";
                        }
                        else if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                            crotchstrap="crotch: single strap secured";
                        }
                        else if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                            crotchstrap="crotch: triple strap secured";
                        }
                    }
                    embed.addField("Currently", "Wearing\n" + armstrap + "\n" + crotchstrap, true);
                } else {
                    embed.addField("Currently","(not wearing)",true);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" front strap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" reverse strap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" unstrap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" central strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" double strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" triple strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" unstrap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread)+" stitch straps";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors)+" unstitch straps";
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a straitjacket option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isWearerDenied1withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cSTRAITJACKET.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cSTRAITJACKET.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrained())desc+= iRdStr.strRestraintArmsCuffs;
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
                logger.warn(fName+"nOn="+gNewUserProfile.cSTRAITJACKET.isOn());
                logger.warn(fName+"nStrapArms="+gNewUserProfile.cSTRAITJACKET.isArmStrapped()+", nStrapArmsLevel="+gNewUserProfile.cSTRAITJACKET.getArmLevelAsString());
                logger.warn(fName+"nStrapCrotch="+gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()+",nStrapCrotchLevel="+gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString());
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(4,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_sj");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent.SelectMenu selectContainer1=messageComponentManager.messageBuildComponent_Select.getSelectById("select_crotch");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent.SelectMenu selectContainer2=messageComponentManager.messageBuildComponent_Select.getSelectById("select_arms");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent.SelectMenu selectContainer3=messageComponentManager.messageBuildComponent_Select.getSelectById("select_stich");
                    if (!gNewUserProfile.cSTRAITJACKET.isOn()){
                        selectContainer1.setDisabled();selectContainer2.setDisabled();selectContainer3.setDisabled();
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    }else{
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        //ARMS
                        if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                            selectContainer0.setDisabled();
                            switch (gNewUserProfile.cSTRAITJACKET.getArmLevel()){
                                case Front:selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case Reverse:selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            }
                            if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())selectContainer2.setDisabled();
                            else if (gNewUserProfile.cSTRAITJACKET.isStiched()) selectContainer2.setDisabled();
                        }else{
                            selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                        }

                        //CROTCH
                        if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                            selectContainer0.setDisabled();
                            switch (gNewUserProfile.cSTRAITJACKET.getCrotchLevel()) {
                                case Mono:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                                case Double: selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                                case Triple: selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            }
                            if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())selectContainer1.setDisabled();
                            if (gNewUserProfile.cSTRAITJACKET.isStiched()) selectContainer1.setDisabled();

                        }else{
                            selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);
                            if (gNewUserProfile.isMittsNewRestriction()) selectContainer1.setDisabled();
                            else if (gNewUserProfile.cSTRAITJACKET.isArmStrapped()) selectContainer1.setDisabled();
                        }


                        //STITCHING
                        if (gNewUserProfile.cSTRAITJACKET.isArmStrapped() && gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()) {
                            if (gNewUserProfile.cSTRAITJACKET.isStiched()) {
                                selectContainer3.setDefaultOptionByValue(lsUnicodeEmotes.aliasSpoolOfThread);
                                if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())selectContainer3.setDisabled();

                            } else {
                                selectContainer3.setDefaultOptionByValue(lsUnicodeEmotes.aliasScissors);
                            }
                        }
                    }
                    if(!(gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cARMCUFFS.areArmsRestrained()&&!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cSUIT.isBDSMSuitOn()))) {
                        selectContainer0.setDisabled();selectContainer1.setDisabled();selectContainer2.setDisabled();selectContainer3.setDisabled();
                    }

                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
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
        private void menuLevelsWearerListener(Message message){
            String fName="[menuLevelsWearerListener]";
            logger.info(fName);
            try {
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
                                    }else
                                    {
                                        isMenuLevel=true;rStraitjacket(level);
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
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level=vOff;break;
                                    case lsUnicodeEmotes.aliasOne:level=vOn;break;
                                    case lsUnicodeEmotes.aliasTwo:level=commandStrapArms;break;
                                    case lsUnicodeEmotes.aliasThree:level=commandReverseStrapArms;break;
                                    case lsUnicodeEmotes.aliasFour:level=commandUnStrapArms;break;
                                    case lsUnicodeEmotes.aliasFive:level=commandStrapMonoCrotch;break;
                                    case lsUnicodeEmotes.aliasSix:level=commandStrapCrotch;break;
                                    case lsUnicodeEmotes.aliasSeven:level=commandStrapTripleCrotch;break;
                                    case lsUnicodeEmotes.aliasEight:level=commandUnStrapCrotch;break;
                                    case lsUnicodeEmotes.aliasSpoolOfThread:level=commandStitch;break;
                                    case lsUnicodeEmotes.aliasScissors:level=commandUnstitch;break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    isMenuLevel=true;result=rStraitjacket(level);
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=vOff;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=vOn;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=commandStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=commandReverseStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=commandUnStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=commandStrapMonoCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){level=commandStrapCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){level=commandStrapTripleCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){level=commandUnStrapCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread))){level=commandStitch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){level=commandUnstitch;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else
                                    {
                                        isMenuLevel=true;rStraitjacket(level);
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
        private void menuLevelsSomebody(){
            String fName="[menuLevelsSomebody]";
            logger.info(fName);
            try {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc = "";
                embed.setColor(llColorOrange);
                embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+ sRTitle + " Options");
                embed.addField(strSupportTitle,strSupport,false);
                if (gNewUserProfile.cSTRAITJACKET.isOn()) {
                    String armstrap = "arms:free", crotchstrap = "crotch:free";
                    if (gNewUserProfile.cSTRAITJACKET.isArmStrapped()) {
                        armstrap = "arms: secured";
                        if(gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                            armstrap="arms: front secured";
                        }
                        if(gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                            armstrap="arms: reverse secured";
                        }
                    }
                    if (gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()) {
                        crotchstrap = "crotch: secured";
                        if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                            crotchstrap="crotch: double strap secured";
                        }
                        else if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                            crotchstrap="crotch: single strap secured";
                        }
                        else if(gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                            crotchstrap="crotch: triple strap secured";
                        }
                    }
                    embed.addField("Currently", "Wearing\n" + armstrap + "\n" + crotchstrap, true);
                } else {
                    embed.addField("Currently","(not wearing)",true);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" front strap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" reverse strap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" unstrap arms";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" central strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" double strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" triple strap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" unstrap crotch";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread)+" stitch straps";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors)+" unstitch straps";
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a straitjacket option for " +gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.cENCASE.isEncased()||gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                    desc="";
                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc+=iRdStr.strRestraintEncased;
                    }
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                        desc+=iRdStr.strRestraintArmsCuffs;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraints+desc,false);
                }
                Message message=null;
                //llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                logger.warn(fName+"nOn="+gNewUserProfile.cSTRAITJACKET.isOn());
                logger.warn(fName+"nStrapArms="+gNewUserProfile.cSTRAITJACKET.isArmStrapped()+", nStrapArmsLevel="+gNewUserProfile.cSTRAITJACKET.getArmLevelAsString());
                logger.warn(fName+"nStrapCrotch="+gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()+",nStrapCrotchLevel="+gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString());
                /*if(gNewUserProfile.allow2BypassNewRestrictions()||!gNewUserProfile.cENCASE.isEncased()){
                    logger.info(fName+"options:1");
                    if(gNewUserProfile.cSTRAITJACKET.isOn()&&!gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&!gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(!gNewUserProfile.cSTRAITJACKET.isOn()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(gNewUserProfile.cSTRAITJACKET.isOn()){
                        //ARMS
                        if(!gNewUserProfile.cARMCUFFS.isOn()&&!gNewUserProfile.cSTRAITJACKET.isStiched()){
                            if(!gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            }else{
                                if(!gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                                }
                                if(!gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                                }
                            }
                            if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&!gNewUserProfile.cSTRAITJACKET.isStiched()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        }
                        //CROTCH
                        if(!gNewUserProfile.cSTRAITJACKET.isStiched()){
                            if(!gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                            }else{
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                                }
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));}
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));}
                            }
                            if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&!gNewUserProfile.cSTRAITJACKET.isStiched()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));

                        }
                        //STITCHING
                        if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                            if(gNewUserProfile.cSTRAITJACKET.isStiched()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                            }else{
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread));
                            }
                        }
                    }
                }else{
                    logger.info(fName+"options:2");
                    if(gNewUserProfile.cSTRAITJACKET.isOn()){
                        //ARMS
                        if(!gNewUserProfile.cARMCUFFS.isOn()&&!gNewUserProfile.cSTRAITJACKET.isStiched()){
                            if(!gNewUserProfile.cSTRAITJACKET.isArmStrapped()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            }else{
                                if(!gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                                }
                                if(!gNewUserProfile.cSTRAITJACKET.getArmLevelAsString().equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                                }
                            }
                            if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&!gNewUserProfile.cSTRAITJACKET.isStiched()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        }
                        //CROTCH
                        if(!gNewUserProfile.cSTRAITJACKET.isStiched()){
                            if(!gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                            }else{
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                                }
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));}
                                if(!gNewUserProfile.cSTRAITJACKET.getCrotchLevelAsString().equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                                    llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));}
                            }
                            if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()&&!gNewUserProfile.cSTRAITJACKET.isStiched()) llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                        }
                        //STITCHING
                        if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                            if(gNewUserProfile.cSTRAITJACKET.isStiched()){
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                            }else{
                                llMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread));
                            }
                        }
                    }
                }*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(4,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_sj");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent.SelectMenu selectContainer1=messageComponentManager.messageBuildComponent_Select.getSelectById("select_crotch");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent.SelectMenu selectContainer2=messageComponentManager.messageBuildComponent_Select.getSelectById("select_arms");
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent.SelectMenu selectContainer3=messageComponentManager.messageBuildComponent_Select.getSelectById("select_stich");
                    if (!gNewUserProfile.cSTRAITJACKET.isOn()){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                        selectContainer1.setDisabled();selectContainer2.setDisabled();selectContainer3.setDisabled();
                    }
                    else if(gNewUserProfile.cSTRAITJACKET.isOn()&&!gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&!gNewUserProfile.cSTRAITJACKET.isCrotchStrapped())  selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                    else if(gNewUserProfile.cSTRAITJACKET.isOn()){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        //ARMS
                        if(!gNewUserProfile.cSTRAITJACKET.isArmStrapped())selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                        else{
                            selectContainer0.setDisabled();
                            if(gNewUserProfile.cSTRAITJACKET.getArmLevel()==STRAITJACKETARMSLEVEL.Front) selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                            else if(gNewUserProfile.cSTRAITJACKET.getArmLevel()==STRAITJACKETARMSLEVEL.Reverse) selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                        }

                        if(gNewUserProfile.cARMCUFFS.isOn()||gNewUserProfile.cSTRAITJACKET.isStiched())selectContainer2.setDisabled();
                        //CROTCH
                        if(!gNewUserProfile.cSTRAITJACKET.isCrotchStrapped())selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);
                        else {
                            selectContainer0.setDisabled();
                            if (gNewUserProfile.cSTRAITJACKET.getCrotchLevel() == STRAITJACKETCROTCHSLEVEL.Mono)
                                selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);
                            else if (gNewUserProfile.cSTRAITJACKET.getCrotchLevel() == STRAITJACKETCROTCHSLEVEL.Double)
                                selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);
                            else if (gNewUserProfile.cSTRAITJACKET.getCrotchLevel() == STRAITJACKETCROTCHSLEVEL.Triple)
                                selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);
                        }
                        if(gNewUserProfile.cSTRAITJACKET.isStiched())selectContainer1.setDisabled();
                        //STITCHING
                        if(gNewUserProfile.cSTRAITJACKET.isStiched()){
                            selectContainer3.setDefaultOptionByValue(lsUnicodeEmotes.aliasSpoolOfThread);
                        }else{
                            selectContainer3.setDefaultOptionByValue(lsUnicodeEmotes.aliasScissors);
                        }
                        if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()&&gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()){
                            selectContainer3.setDisabled();
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember)){
                            if(gNewUserProfile.cSTRAITJACKET.isOn()) selectContainer0.setDisabled();
                            if(gNewUserProfile.cSTRAITJACKET.isArmStrapped()) selectContainer2.setDisabled();
                            if(gNewUserProfile.cSTRAITJACKET.isCrotchStrapped()) selectContainer1.setDisabled();
                        }
                    }
                    if(!gNewUserProfile.allow2BypassNewRestrictions()&&gNewUserProfile.cENCASE.isEncased()){
                        logger.info(fName+"options:1");
                        selectContainer0.setDisabled();selectContainer1.setDisabled();selectContainer2.setDisabled();selectContainer3.setDisabled();
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
        private void menuLevelsSomebodyListener(Message message){
            String fName="[menuLevelsSomebodyListener]";
            logger.info(fName);
            try {
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
                                    }else {
                                        isMenuLevel=true;rStraitjacket(gNewUserProfile.getMember(),level);
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
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level=vOff;break;
                                    case lsUnicodeEmotes.aliasOne:level=vOn;break;
                                    case lsUnicodeEmotes.aliasTwo:level=commandStrapArms;break;
                                    case lsUnicodeEmotes.aliasThree:level=commandReverseStrapArms;break;
                                    case lsUnicodeEmotes.aliasFour:level=commandUnStrapArms;break;
                                    case lsUnicodeEmotes.aliasFive:level=commandStrapMonoCrotch;break;
                                    case lsUnicodeEmotes.aliasSix:level=commandStrapCrotch;break;
                                    case lsUnicodeEmotes.aliasSeven:level=commandStrapTripleCrotch;break;
                                    case lsUnicodeEmotes.aliasEight:level=commandUnStrapCrotch;break;
                                    case lsUnicodeEmotes.aliasSpoolOfThread:level=commandStitch;break;
                                    case lsUnicodeEmotes.aliasScissors:level=commandUnstitch;break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    isMenuLevel=true;result=rStraitjacket(gNewUserProfile.getMember(),level);
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
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=vOff;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=vOn;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=commandStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=commandReverseStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=commandUnStrapArms;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=commandStrapMonoCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){level=commandStrapCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){level=commandStrapTripleCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){level=commandUnStrapCrotch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread))){level=commandStitch;}
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){level=commandUnstitch;}
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else {
                                        isMenuLevel=true;rStraitjacket(gNewUserProfile.getMember(),level);
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
        private void userNotificationStraitjacket(int action,String desc){
        String fName="[userNotificationStraitjacket]";
        logger.info(fName+"action="+action);
        logger.info(fName+"desc="+desc);
        try {
            if(gBDSMCommands.restraints.getNotificationDisabled()){
                logger.warn(fName+"notification disabled");
                return;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                    if(isAdult&&action==actionPutOn){
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlOn().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlOn();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlSecure().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlSecure();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gTextChannel,embed);
                        }else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }else
                    if(isAdult&&action==actionSecured){
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlSecure().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlSecure();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlOn().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlOn();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gTextChannel,embed);
                        }else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }else
                    if(isAdult&&action==actionUnSecured){
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlUnSecure().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlUnSecure();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlOff().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlOff();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }else
                    if(isAdult&&action==actionTakeOff){
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlOff().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlOff();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlUnSecure().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlUnSecure();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }else
                    if(isAdult&&action==actionStruggle){
                        if(!gNewUserProfile.cSTRAITJACKET.getImageUrlStruggle().isEmpty()){
                            String url=gNewUserProfile.cSTRAITJACKET.getImageUrlStruggle();
                            EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                            embed.setImage(url);
                            embed.setDescription(desc);
                            llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                        }else {
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }else{
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                    }
                }
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


    }}
