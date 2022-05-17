package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;
import restraints.in.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.rdExtension;

import java.util.Arrays;
import java.util.List;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdLeash extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdLeash]";
	String sMainRTitle ="Leash";String gQuickPrefix="leash";
    public rdLeash(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Leash-DiscordRestraints";
        this.help = "rdLeash";
        this.aliases = new String[]{"leash"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdLeash(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdLeash(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdLeash(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdLeash(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
    protected class runLocal extends rdExtension implements Runnable, iLeash {
        String cName="[runLocal]";
        public runLocal(CommandEvent ev){
            logger.info(".run build");
            launch(gGlobal,ev);
            gBDSMCommands.muzzle.init();
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            gBDSMCommands.muzzle.init();
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            gBDSMCommands.muzzle.init();
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
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
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"rdLeash_commands",gGlobal);
                setTitleStr(rdLeash.this.sMainRTitle);setPrefixStr(rdLeash.this.llPrefixStr);//setCommandStr(rdLeash.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                lsUsefullFunctions.setThreadName4Display("rdLeash");
                gBasicFeatureControl.initProfile();
                if(!gBDSMCommands.restraints.getEnable()) {
                    logger.info(fName + "its disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                    logger.info(fName+"its not allowed by channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                    logger.info(fName+"its not allowed by roles");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                gBDSMCommands.collar.init();
                updateIsAdult();

                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    String[] items;
                    boolean isInvalidCommand=true;
                    if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        rHelp("main");isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}

                    boolean isInvalidCommand = true;
                    if(gCommandEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        rHelp("main");
                        isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        if(gCommandEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                        gItems = gCommandEvent.getArgs().split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;
                            llSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
                        }
                        else if(gItems[0].equalsIgnoreCase("guild")||gItems[0].equalsIgnoreCase("server")){
                            if(gItems.length>2){
                                // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                int group=0,type=0,action=0;
                                switch (gItems[1].toLowerCase()){
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
                                switch (gItems[2].toLowerCase()){
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
                                        getChannels(type,false);isInvalidCommand=false;
                                    }else{
                                        setChannel(type,action,gCommandEvent.getMessage());
                                    }
                                }
                                else if(group==2){
                                    if(action==0){
                                        getRoles(type,false);isInvalidCommand=false;
                                    }else{
                                        setRole(type,action,gCommandEvent.getMessage());
                                    }
                                }
                            }else{
                                menuGuild();isInvalidCommand=false;
                            }
                        }
                        else if(!gBasicFeatureControl.getEnable()){
                            logger.info(fName+"its disabled");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                            logger.info(fName+"its not allowed by channel");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                            logger.info(fName+"its not allowed by roles");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
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
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                logger.warn(fName+".blank command");
                            }
                            else if(gItems[0].equalsIgnoreCase(vRed)){
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cLEASH.release();});}
                            else if(gItems[0].equalsIgnoreCase(nTo)||gItems[0].equalsIgnoreCase(nRelease)){
                                rLeash(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                        }
                    }
                    if(isInvalidCommand){
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
            logger.info(".run ended");
        }
        private boolean quickCommands(String item1, String item2,Member target){
            String fName="[quickCommands]";
            logger.info(fName+"item1="+item1);
            logger.info(fName+"item2="+item2);
            logger.info(fName+"target="+target.getId());
            if(item1.equalsIgnoreCase(nTo)||item1.equalsIgnoreCase(nRelease)){
                rLeash(target,item1.toLowerCase());
                return false;
            }
            if(item2.equalsIgnoreCase(nTo)||item2.equalsIgnoreCase(nRelease)){
                rLeash(target,item2.toLowerCase());
                return false;
            }
            return true;
        }
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            String desc;
            String quickSummonWithSpace2=llPrefixStr+"leash <@Pet> ";
            String newLine="\n  `", endLine="`";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            embed.addField(strSupportTitle,strSupport,false);
            desc="";
            embed.addField("What is this?","Its a social fun interactive leash.",false);
            desc+=newLine+quickSummonWithSpace2+nRelease+endLine+ " to unclip the leash";
            desc+=newLine+quickSummonWithSpace2+nTo+" <@target>"+endLine+" to leash it to somebody";
            embed.addField("Commands",desc,false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private void rLeash(String command) {
            String fName = "[rLeash]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(gNewUserProfile.isPetDenied2HaveAccessIfRestrained()){
                if(!gIsOverride&&gNewUserProfile.isArmsStraitjacketRestriction()){
                    logger.info(fName + ".can't restrain wile jacket is on");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed_PetDenied), llColorRed);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.isMittsNewRestriction()){
                    logger.info(fName + ".can't restrain wile mitts are on");
                    if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                        logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                        iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                    }else{
                        logger.info(fName + ".default message");
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                    }
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.isMittsNewRestriction()){
                    logger.info(fName + ".can't restrain wile cuffs are on");
                    llSendQuickEmbedMessage(gUser,sRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                    return;
                }
            }

            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your leash due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your leash due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccess()==ACCESSLEVELS.Protected){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your leash due to access set to public. While public everyone else can access it except you.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.isArmsStraitjacketRestriction()){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iStraitjacket.strCantGrabAnything), llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.isArmCuffsNewRestriction()){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                llSendQuickEmbedMessage(gUser,sRTitle,"How do you think you will manipulate your leash while you already have your arms tied?", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.isMittsNewRestriction()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccess()==ACCESSLEVELS.Exposed&&gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locked and access exposed");
                llSendQuickEmbedMessage(gUser,sRTitle,"You dont have the key to do that!", llColorRed);
                userNotificationLeash(actionStruggle,  gUser.getAsMention()+" tries to play with their leash but fail as they dont have the key to do that.");return;
            }else
            if(command.equalsIgnoreCase(nRelease)){
                if(gNewUserProfile.cLEASH.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't untie while locked");
                        llSendQuickEmbedMessage(gUser,sRTitle,"You can't untie your leash while locked with padlock.", llColorRed);return;
                    }else{
                        gBDSMCommands.collar.remLeash(gMember);
                        llSendQuickEmbedMessage(gUser,sRTitle,"You removed your leash.", llColorRed);
                        long from=gNewUserProfile.cLEASH.getLeash2Member();
                        if(from==0){
                            Member mfrom=lsMemberHelper.lsGetMember(gGuild,from);
                            if(mfrom!=null){
                                userNotificationLeash(actionUnSecured, gUser.getAsMention()+" managed to remove their leash from "+mfrom.getUser().getName()+" Bad pup for doing that.");
                            }else{
                                userNotificationLeash(actionUnSecured, gUser.getAsMention()+" managed to remove their leash. Bad pup for doing that.");
                            }
                        }else{
                            userNotificationLeash(actionUnSecured, gUser.getAsMention()+" managed to remove their leash. Bad pup for doing that.");
                        }
                        gNewUserProfile.cLEASH.release();
                    }
                }else{
                    logger.info(fName + ".wrist not restrained");
                    llSendQuickEmbedMessage(gUser,sRTitle,"You are not leashed, silly.", llColorPurple1);return;
                }
            }else
            {
                List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                if(mentions.isEmpty()){
                    logger.info(fName + ".no mention");
                    llSendQuickEmbedMessage(gUser,sRTitle,"You need to mention somebody!", llColorRed);return;
                }
                Member mention=mentions.get(0);logger.info(fName + ".mention:"+mention.getId()+"|"+mention.getUser().getName());
                gNewUserProfile.cLEASH.leashedTo(mention);
                gBDSMCommands.collar.addLeash(gMember,mention,gTextChannel);
                llSendQuickEmbedMessage(gUser,sRTitle,"You leash yourself to "+mention.getUser().getName(), llColorOrange_Bittersweet);
                userNotificationLeash(actionSecured, gUser.getAsMention()+" leashes themself to "+mention.getUser().getName());
            }
            saveProfile();
        }
        Message messageOwner=null;User messageOwnerUser=null;
        private void rLeash(Member mtarget, String command) {
            String fName = "[rLeash]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their leash due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their leash due to their access setting.", llColorRed);return;
            }else
                if(command.equalsIgnoreCase(nRelease)){
                    if(gNewUserProfile.cLEASH.isOn()){
                        if(!gIsOverride&&gNewUserProfile.cLEASH.isOn()){
                            logger.info(fName + ".can't untie while locked");
                            llSendQuickEmbedMessage(gUser,sRTitle,"You can't untie their leash while locked with padlock.", llColorRed);return;
                        }else{
                            rLeashSave4target(target,command);
                        }
                    }else{
                        logger.info(fName + ".wrist not restrained");
                        llSendQuickEmbedMessage(gUser,sRTitle,"They are not leashed, silly.", llColorPurple1);return;
                    }
                }else {
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your leash status.");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's leash status.");
                        gAskHandlingHelper.doAsk(() -> {rLeashSave4target(target,command);});
                        return;
                    }
                    rLeashSave4target(target,command);
                }

        }
        private void rLeashSave4target(User target, String command) {
            String fName = "[rLeash]";
            logger.info(fName);
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(nRelease)){
                gBDSMCommands.collar.remLeash(target);
                llSendQuickEmbedMessage(gUser,sRTitle,"You remove their leash.", llColorRed);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" removes your leash.", llColorRed);
                long from=gNewUserProfile.cLEASH.getLeash2Member();
                if(from>0){
                    Member mfrom=lsMemberHelper.lsGetMember(gGuild,from);
                    if(mfrom!=null){
                        userNotificationLeash(actionUnSecured, gUser.getAsMention()+"unclips "+target.getAsMention()+" leash from "+mfrom.getUser().getName()+".");
                    }else{
                        userNotificationLeash(actionUnSecured, gUser.getAsMention()+"unclips "+target.getAsMention()+" leash.");
                    }
                }else{
                    userNotificationLeash(actionUnSecured, gUser.getAsMention()+"unclips "+target.getAsMention()+" leash.");
                }
                gNewUserProfile.cLEASH.release();
            }else {
                List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                if(mentions.isEmpty()){
                    logger.info(fName + ".no mention");
                    llSendQuickEmbedMessage(gUser,sRTitle,"You need to mention somebody!", llColorRed);return;
                }
                Member mention;
                if(mentions.size()==1){
                    logger.info(fName + ".self");
                    gNewUserProfile.cLEASH.leashedTo(gMember);
                    gBDSMCommands.collar.addLeash(target,gUser,gTextChannel);
                    llSendQuickEmbedMessage(gUser,sRTitle,"You leash "+target.getAsMention()+" to yourself.", llColorOrange_Bittersweet);
                    llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" leashes you to themself", llColorOrange_Bittersweet);
                    userNotificationLeash(actionSecured, gUser.getAsMention()+" leashes "+target.getAsMention()+" to themself.");
                }else {
                    logger.info(fName + ".to somebody");
                    mention=mentions.get(1);logger.info(fName + ".mention:"+mention.getId()+"|"+mention.getUser().getName());
                    gNewUserProfile.cLEASH.leashedTo(mention);
                    gBDSMCommands.collar.addLeash(target,mention.getUser(),gTextChannel);
                    llSendQuickEmbedMessage(gUser,sRTitle,"You leash "+target.getAsMention()+" to "+mention.getUser().getName(), llColorOrange_Bittersweet);
                    llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" leashes you to "+mention.getUser().getName(), llColorOrange_Bittersweet);
                    llSendQuickEmbedMessage(mention.getUser(),sRTitle,gUser.getAsMention()+" leashes "+target.getAsMention()+" to you", llColorOrange_Bittersweet);
                    userNotificationLeash(actionSecured, gUser.getAsMention()+" leashes "+target.getAsMention()+" to "+mention.getUser().getName());
                }

            }

            saveProfile();
        }

        private void userNotificationLeash(int action, String desc){
            String fName="[userNotificationGag]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nLeash;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                        if(isAdult&&action==actionSecured){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionUnSecured){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlUnSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlUnSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionStruggle){
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
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }






}}
