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
import restraints.PostSynthesizer.rdVoicePostSynthesizer;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.EARMUFFSLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdEars extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Ears";String gCommand ="ears";
    public rdEars(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Ears-DiscordRestraints";
        this.help = "rdEars";
        this.aliases = new String[]{gCommand,"ear","rears","rear"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdEars(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdEars(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
    protected class runLocal extends rdExtension implements Runnable, iEar {
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
                setTitleStr(rdEars.this.sMainRTitle);setPrefixStr(rdEars.this.llPrefixStr);setCommandStr(rdEars.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdEars_commands");
                lsUsefullFunctions.setThreadName4Display("rdEars");
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
                        gItems =gArgs.split("\\s+");
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
                                if(gItems.length>=4){
                                    isInvalidCommand= quickCommands(gItems[2], gItems[3], gTarget);
                                }
                                if(isInvalidCommand&&gItems.length>=3){
                                    if(gItems[1].equalsIgnoreCase(optionVoice)&&(gItems[2].equalsIgnoreCase(optionEnable)||gItems[2].equalsIgnoreCase(optionDisable))){
                                        rVoiceRestriction(gTarget,gItems[1],gItems[2]);isInvalidCommand=false;
                                    }else{
                                        isInvalidCommand= quickCommands(gItems[1], gItems[2], gTarget);
                                    }
                                }
                                if(isInvalidCommand){
                                    if(gItems[1].equalsIgnoreCase(optionVoiceDeafen)||gItems[1].equalsIgnoreCase(optionVoiceUndeafen)){
                                        rVoiceRestriction(gTarget,".",gItems[1]);isInvalidCommand=false;
                                    }else{
                                        isInvalidCommand= quickCommands(gItems[1], "", gTarget);
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
                            }
                            else if(gItems[0].equalsIgnoreCase(vRed)){
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cEAR.release();});}
                            else if(gItems.length>1&&gItems[0].equalsIgnoreCase(optionVoice)&&(gItems[1].equalsIgnoreCase(optionEnable)||gItems[1].equalsIgnoreCase(optionDisable))){
                                rVoiceRestriction(gItems[0],gItems[1]);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(optionVoiceDeafen)||gItems[0].equalsIgnoreCase(optionVoiceUndeafen)){
                                rVoiceRestriction(".",gItems[1]);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(vOn)||gItems[0].equalsIgnoreCase(vOff)){
                                rEars(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())||gItems[0].equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())){
                                rEars(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else{
                                menuLevels(null);isInvalidCommand=false;
                            }
                        }
                    }

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
                rEars(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOn)||item2.equalsIgnoreCase(vOff)){
                rEars(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())||item1.equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())){ rEars(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())||item2.equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())){ rEars(target,item2.toLowerCase()); return false;}
            return true;
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> ears ";
        String quickSummonWithSpace2=llPrefixStr+"ears <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();

        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Robs the wearer of all sounds. Fictional, make belief, to be in character.",false);
        desc=newLine+quickSummonWithSpace2+"on"+spacingDual+quickSummonWithSpace2+"off"+endLine;
        desc+=newLine+quickSummonWithSpace2+EARMUFFSLEVELS.Muffs.getName()+endLine+" a pair of heavy muffs that block all sounds";
        desc+=newLine+quickSummonWithSpace2+EARMUFFSLEVELS.Plugs.getName()+endLine+" a pair of plugs that push into the ear and block all sounds";
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
    
    private boolean rEars(String command) {
        String fName = "[rEars]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cEAR.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate restraints as they permanently locked!", llColorRed);
            return false;
        }
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
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't manipulate your ear muffs due to they locked by !LOCKER"), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your ear muffs due to access set to owner. Only your owner and sec-owners have access", llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your ear muffs due to access set to public. While public everyone else can access it except you.", llColorRed);
            return false;
        }else
        if(command.equalsIgnoreCase(vOn)){
            if(!gIsOverride&&gNewUserProfile.cEAR.isOn()){
                logger.info(fName + ".ear muffs is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The ear muffs are already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cEAR.setOn( true);
                if(gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().isBlank()){gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());}
                else if(gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());}
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put ear muffs over your ears.", llColorBlue1);
                userNotificationEarMuffs( actionPutOn,stringReplacer("!USER has put ear muffs on their ears."));
                voiceChannelRestriction(1);
            }
        }else
         if(!gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,"The ear muffs are already on, silly.", llColorPurple1);return false;
        }else
        if(command.equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())){
            gNewUserProfile.cEAR.setOn( true);gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put a pair of heavy muffs over your ear that block all sounds.", llColorBlue1);
            userNotificationEarMuffs( actionPutOn,stringReplacer("!USER has put a pair of heavy muffs over their ear that block all sounds."));
            voiceChannelRestriction(1);
        }else
        if(command.equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())){
            gNewUserProfile.cEAR.setOn( true);gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Plugs.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put a pair of plugs that push into your ear and block all sounds.", llColorBlue1);
            userNotificationEarMuffs( actionPutOn,stringReplacer("!USER has put a pair of plugs that push into their ear and block all sounds."));
            voiceChannelRestriction(1);
        }else
        if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cEAR.isOn()){
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed), llColorRed);
                    return false;
                }
                else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    userNotificationEarMuffs( actionStruggle,stringReplacer("!USER struggled to take off the ear muffs but failed due to its locked with a padlock."));
                }else{
                    gNewUserProfile.cEAR.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed to pull the ear muffs off, now you can hear again.", llColorBlue1);
                    userNotificationEarMuffs( actionTakeOff, stringReplacer("!USER to take off their ear muffs. Someone must have forgot to secure it."));
                    voiceChannelRestriction(-1);
                }
            }else{
                logger.info(fName + ".ear muffs is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The ear muffs are not on, silly.", llColorPurple1);
            }
        }
        saveProfile();
        return true;
    }
        private void rVoiceRestriction(String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restrictions as access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restrictions due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(optionEnable)||command.equalsIgnoreCase(optionVoiceDeafen)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,true);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled earmuffs voice deafen.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionDisable)||command.equalsIgnoreCase(optionVoiceUndeafen)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled earmuffs voice deafen.", llColorBlue3);
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
        }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    boolean isMenu=false;
    private boolean rEars(Member mtarget, String command) {
        String fName = "[rEars]";
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
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cEAR.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate !WEARER's restraints as they permanently locked!", llColorRed);
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their ear muffs due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their ear muffs due to their access setting.", llColorRed);
        }else
        if(command.equalsIgnoreCase("on")){
            if(!gIsOverride&&gNewUserProfile.cEAR.isOn()){
                logger.info(fName + ".ear muffs is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The ear muffs are already on.", llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place earmuffs around your ears!");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place earmuffs around !TARGET's ears!");
                    gAskHandlingHelper.doAsk(() -> {rEarsSafe4Target(target,command);});
                    return false;
                }
                rEarsSafe4Target(target,command);
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,"The ear muffs are already on, silly.", llColorPurple1);
        }else
        if(command.equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place earmuffs around your ears!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place earmuffs around !TARGET's ears!");
                gAskHandlingHelper.doAsk(() -> {rEarsSafe4Target(target,command);});
                return false;
            }
            rEarsSafe4Target(target,command);
        }else
        if(command.equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place earmuffs around your ears!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place earmuffs around !TARGET's ears!");
                gAskHandlingHelper.doAsk(() -> {rEarsSafe4Target(target,command);});
                return false;
            }
            rEarsSafe4Target(target,command);
        }else
        if(command.equalsIgnoreCase("off")){
            if(gNewUserProfile.cEAR.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take off the earmuffs around your ears!");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take off the earmuffs around !TARGET's ears!");
                        gAskHandlingHelper.doAsk(() -> {rEarsSafe4Target(target,command);});
                        return false;
                    }
                    rEarsSafe4Target(target,command);
                }
            }else{
                logger.info(fName + ".ear muffs is not on");
                llSendQuickEmbedMessage(target,sMainRTitle,"The ear muffs are not on.", llColorPurple1);
            }
        }
        return true;
    }
        private void rEarsSafe4Target(User target, String command) {
            String fName = "[rEarsSafe4Target]";
            logger.info(fName);
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase("on")){
                gNewUserProfile.cEAR.setOn( true);
                if(gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().isBlank()){gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());}
                else if(gIsOverride&&gNewUserProfile.cEAR.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());}
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You put ear muffs over !WEARER's head."), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER places the ear muffs over your head."), llColorBlue1);
                userNotificationEarMuffs(actionPutOn, stringReplacer("!USER places ear muffs over !WEARER's head.'"));
                voiceChannelRestriction(1);
            }else
            if(command.equalsIgnoreCase(EARMUFFSLEVELS.Muffs.getName())){
                gNewUserProfile.cEAR.setOn( true);gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Muffs.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You put a pair of heavy muffs over !WEARER ear that block all sounds."), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER puts a pair of heavy muffs over your ear that block all sounds."), llColorBlue1);
                userNotificationEarMuffs( actionPutOn,stringReplacer("!USER has put a pair of heavy muffs over !WEARER ear that block all sounds."));
                voiceChannelRestriction(1);
            }else
            if(command.equalsIgnoreCase(EARMUFFSLEVELS.Plugs.getName())){
                gNewUserProfile.cEAR.setOn( true);gNewUserProfile.cEAR.setLevel( EARMUFFSLEVELS.Plugs.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You put a pair of plugs that push into !WEARER ear and block all sounds."), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER puts a pair of plugs that push into your ear and block all sounds."), llColorBlue1);
                userNotificationEarMuffs( actionPutOn,stringReplacer("!USER has put a pair of plugs that push into !WEARER ear and block all sounds."));
                voiceChannelRestriction(1);
            }else
            if(command.equalsIgnoreCase("off")){
                gNewUserProfile.cEAR.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You pull the ear muffs off !WEARER's ears."), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER pulls the ear muffs off your ears"), llColorBlue1);
                userNotificationEarMuffs(actionTakeOff,  stringReplacer("!USER pulls the ear muffs off !WEARER's ears."));
                voiceChannelRestriction(-1);
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
                            sendOrUpdatePrivateEmbed(sRTitle, iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

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
        String gCommandFileMainPath =gFileMainPath+"ears/menuLevels.json";
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                isMenu=true;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nEarMuffs)&&gNewUserProfile.cEAR.isOn()){
                    String level=gNewUserProfile.cEAR.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+EARMUFFSLEVELS.Muffs.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+EARMUFFSLEVELS.Plugs.getName();
                embed.addField(" ", "Please select a ears muffs option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                try {
                    desc="Deafening user in servers voice channel during earmuffing.";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_ListenEnabled)){
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound)+" disable";
                    }else{
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute)+" enable";
                    }
                    embed.addField("Voice Channel Restriction", desc, false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isWearerDenied0EarwithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cEAR.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cEAR.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageEar())desc+= iRdStr.strRestraintArmsCuffs;
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
                    logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,2);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_level");
                    if(gNewUserProfile.cEAR.isOn()){
                        messageComponentManager.selectContainer.remOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cEAR.getLevel()==EARMUFFSLEVELS.Muffs)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasTwo).setDefault();
                        if(gNewUserProfile.cEAR.getLevel()==EARMUFFSLEVELS.Plugs)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasThree).setDefault();
                    }else{
                        messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasZero).setDefault();
                    }
                    if(gNewUserProfile.isWearerDenied0EarwithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cEAR.isOn())){
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    try {
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_ListenEnabled)){
                            messageComponentManager.messageBuildComponent_Button.getButtonById("!sound/mute").setLabel("🔊Voice").setCustomId("sound");
                        }else{
                            messageComponentManager.messageBuildComponent_Button.getButtonById("!sound/mute").setLabel("🔇Mute").setCustomId("mute");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
        private void menuLevelsSomebody(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                isMenu=true;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nEarMuffs)&&gNewUserProfile.cEAR.isOn()){
                    String level=gNewUserProfile.cEAR.getLevelAsString();
                    embed.addField("Currently",level,false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }

                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+EARMUFFSLEVELS.Muffs.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+EARMUFFSLEVELS.Plugs.getName();
                embed.addField(" ", "Please select a ears muffs option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                try {
                    desc="Deafening user in servers voice channel during earmuffing.";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_ListenEnabled)){
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound)+" disable";
                    }else{
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute)+" enable";
                    }
                    embed.addField("Voice Channel Restriction", desc, false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                //embed.setDescription(desc);
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gNewUserProfile.cEAR.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(gNewUserProfile.cEAR.getLevel()!=EARMUFFSLEVELS.Muffs)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(gNewUserProfile.cEAR.getLevel()!=EARMUFFSLEVELS.Plugs)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                try {
                    if(gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_ListenEnabled)){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound));
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute));
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,2);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_level");
                    if(gNewUserProfile.cEAR.isOn()){
                        messageComponentManager.selectContainer.remOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cEAR.getLevel()==EARMUFFSLEVELS.Muffs)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasTwo).setDefault();
                        if(gNewUserProfile.cEAR.getLevel()==EARMUFFSLEVELS.Plugs)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasThree).setDefault();
                        if(gNewUserProfile.isLocked_checkMember(gMember))messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasZero).setDefault();
                    }
                    try {
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_ListenEnabled)){
                            messageComponentManager.messageBuildComponent_Button.getButtonById("!sound/mute").setLabel("🔊Voice").setCustomId("sound");
                        }else{
                            messageComponentManager.messageBuildComponent_Button.getButtonById("!sound/mute").setLabel("🔇Mute").setCustomId("mute");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasMute:
                                        rVoiceRestriction("",optionVoiceDeafen);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
                                        return;
                                    case lsUnicodeEmotes.aliasSound:
                                        rVoiceRestriction("",optionVoiceUndeafen);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
                                        return;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        rEars(level);
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
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level="on";break;
                                    case lsUnicodeEmotes.aliasTwo:level=EARMUFFSLEVELS.Muffs.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=EARMUFFSLEVELS.Plugs.getName();break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    result=rEars(level);
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
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level="on";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=EARMUFFSLEVELS.Muffs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=EARMUFFSLEVELS.Plugs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        rVoiceRestriction("",optionVoiceDeafen);
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))) {
                                        rVoiceRestriction("",optionVoiceUndeafen);
                                        return;
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rEars(level);
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
                }else{
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=EARMUFFSLEVELS.Muffs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=EARMUFFSLEVELS.Plugs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        rVoiceRestriction("",optionVoiceDeafen);
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))) {
                                        rVoiceRestriction("",optionVoiceUndeafen);
                                        return;
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rEars(level);
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
                }
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
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasMute:
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceDeafen);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
                                        return;
                                    case lsUnicodeEmotes.aliasSound:
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceUndeafen);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
                                        return;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        rEars(gNewUserProfile.getMember(),level);
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
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level="on";break;
                                    case lsUnicodeEmotes.aliasTwo:level=EARMUFFSLEVELS.Muffs.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=EARMUFFSLEVELS.Plugs.getName();break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    result=rEars(gNewUserProfile.getMember(),level);
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
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {

                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level="on";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=EARMUFFSLEVELS.Muffs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=EARMUFFSLEVELS.Plugs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceDeafen);
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))) {
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceUndeafen);
                                        return;
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rEars(gNewUserProfile.getMember(),level);
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
                }else{
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=EARMUFFSLEVELS.Muffs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=EARMUFFSLEVELS.Plugs.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceDeafen);
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))) {
                                        rVoiceRestriction(gNewUserProfile.getMember(),"",optionVoiceUndeafen);
                                        return;
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rEars(gNewUserProfile.getMember(),level);
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
                }
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
        private void rVoiceRestriction(Member mtarget,String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their ear muffs due to their access setting.", llColorRed);return;
            }
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your voice restriction.");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update your voice restriction.");
                gAskHandlingHelper.doAsk(() -> {rVoiceRestriction4Save(mtarget,category,command);});
                return;
            }
            rVoiceRestriction4Save(mtarget,category,command);
        }
        private void rVoiceRestriction4Save(Member mtarget,String category,String command){
            String fName="[rVoiceRestriction4Save]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(command.equalsIgnoreCase(optionEnable)||command.equalsIgnoreCase(optionVoiceDeafen)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,true);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled earmuffs voice deafen for !WEARER.", llColorBlue3);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER enabled earmuffs voice deafen for you."), llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionDisable)||command.equalsIgnoreCase(optionVoiceUndeafen)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled earmuffs voice deafen for !WEARER.", llColorBlue3);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer("!USER disabled earmuffs voice deafen for you."), llColorBlue3);
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
        }
        private void voiceChannelRestriction(int action){
            String fName = "[voiceChannelRestriction]";
            try {
                new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    private void userNotificationEarMuffs(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nEarMuffs;
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
            }
        }


    }}
