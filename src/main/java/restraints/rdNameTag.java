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
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.in.iStraitjacket;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.rdExtension;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdNameTag extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="NameTag",gCommand="rdname";
    public rdNameTag(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "NameTag-DiscordRestraints";
        this.help = "rdMitts";
        this.aliases = new String[]{"rdname","nametag"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdNameTag(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdNameTag(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
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
        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build");
            launch(gGlobal,ev);
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
            String fName="[run]";
            logger.info(".run start");
            try {
                setTitleStr(rdNameTag.this.sMainRTitle);setPrefixStr(rdNameTag.this.llPrefixStr);setCommandStr(rdNameTag.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdNameTag_commands");
                lsUsefullFunctions.setThreadName4Display("rdNameTag");
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
                        rHelp("main");isInvalidCommand=false;
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
                                if(gItems[1].equalsIgnoreCase("view")){
                                    viewNameTag(gTarget);isInvalidCommand=false;}
                                else if(gItems[1].equalsIgnoreCase("setavatar")||gItems[1].equalsIgnoreCase("setavatar2")||gItems[1].equalsIgnoreCase("clearavatar")){
                                    setNameTag(gTarget,gItems[1]);isInvalidCommand=false;}
                                else if(gItems[1].equalsIgnoreCase("setsoft")||gItems[1].equalsIgnoreCase("sethard")||gItems[1].equalsIgnoreCase("clearsoft")||gItems[1].equalsIgnoreCase("clearhard")){
                                    setNameTag(gTarget,gItems[1].toLowerCase());isInvalidCommand=false;}
                                else if(gItems[1].equalsIgnoreCase("on")){
                                    enableNameTag(gTarget,"on");isInvalidCommand=false;}
                                else if(gItems[1].equalsIgnoreCase("off")){
                                    enableNameTag(gTarget,"off");isInvalidCommand=false;}
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("view")){
                                viewNameTag(gMember);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase("on")){
                                enableNameTag("on");isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase("off")){
                                enableNameTag("off");isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase("setavatar")||gItems[0].equalsIgnoreCase("setavatar2")||gItems[0].equalsIgnoreCase("clearavatar")){
                                setNameTag(gItems[0]);isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase("setsoft")||gItems[0].equalsIgnoreCase("sethard")||gItems[0].equalsIgnoreCase("clearsoft")||gItems[0].equalsIgnoreCase("clearhard")){
                                setNameTag(gItems[0].toLowerCase());isInvalidCommand=false;}

                        }
                    }
                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
            logger.info(".run ended");
        }
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            String desc="N/A";
            String quickSummonWithSpace=llPrefixStr+"nametag <@Pet>";
            String newLine="\n  `", spacingDual="` , `" , endLine="`";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            embed.addField(strSupportTitle,strSupport,false);
            embed.addField("What is this?","Its a nametag command for the bdsm system.",false);
            embed.addField("View","`"+quickSummonWithSpace+" view`",false);
            embed.addField("On/Off","`"+quickSummonWithSpace+" <on/off>` turns on/off for user. Turning it off will reset changes.",false);
            embed.addField("Set Soft NameTag","`"+quickSummonWithSpace+" setsoft` allows you to set a soft nametag, will only apply to the webhook created for the gag effect."+newLine+quickSummonWithSpace+" clearsoft` clears the soft nametag.",false);
            embed.addField("Set Hard NameTag","`"+quickSummonWithSpace+" sethard` allows you to set a hard nametag & their nicknames. This will also change their nicknames!"+newLine+quickSummonWithSpace+" clearhard` clears the hard nametag and rests back their nickname.",false);
            embed.addField("Set Avatar","`"+quickSummonWithSpace+" setavatar|setavatar2|clearavatar` allows you to set or clear the avatar used for the gag.",false);
            embed.addField("Important when setting avatar","Do to Discord explicit filter, it can fail while trying to set it in DM. As such use the `setavatar2` to ask ou to upload it in text channel.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private void enableNameTag(String command) {
            String fName = "[enableNameTag]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase("on")){
                if(!gIsOverride&&gNewUserProfile.cNAMETAG.isOn()){
                    logger.info(fName + ".mitts is already on");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The nametag is already on, silly.", llColorPurple1);
                }else{
                    boolean hasHardName=false,hasSoftName=false;
                    if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                        logger.info(fName+".has nHardName");
                        hasHardName=true;
                    }
                    if(!gNewUserProfile.cNAMETAG.getSoftName().isBlank()){
                        logger.info(fName+".has nSoftName");
                        hasSoftName=true;
                    }
                    if(!hasHardName&&!hasSoftName){
                        logger.warn(fName+".no name set>ignore");
                        llSendQuickEmbedMessage(gUser,sRTitle,"You have no soft/hard nametage set. Set one before enabling it.", llColorRed_Crayola); return;
                    }
                    if(hasHardName){
                        if(!enableNickName()){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to change nickname!", llColorRed_Blood);
                        }
                    }
                    gNewUserProfile.cNAMETAG.setOn(true);
                    llSendQuickEmbedMessage(gUser,sRTitle,"You place a nametag over your neck.", llColorBlue1);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle, gUser.getAsMention()+" has put their nametag over their neck.",llColorBlue1);
                }
            }else
            if(command.equalsIgnoreCase("off")){
                if(gNewUserProfile.cNAMETAG.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                        logger.info(fName + ".can't take off do to jacket");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't take it off while you'r wearing the mitts!", llColorRed);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle, gUser.getAsMention()+" attempted to take off their nametag but the mitts are preventing it.",llColorBlue1);
                    }
                    else if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        logger.info(fName + ".can't take off do to jacket");
                        llSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iStraitjacket.strCantGrabAnything), llColorRed);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle, gUser.getAsMention()+" attempted to take off their nametag but the straitjacket sleeves are preventing it.",llColorBlue1);
                    }
                    else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        llSendQuickEmbedMessage(gUser,sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" struggled to take off their nametag from their neck but failed do to its locked with a padlock.",llColorBlue1);
                    }else{
                        boolean hasHardName=false;
                        if(!gNewUserProfile.cNAMETAG.getNewName().isEmpty()&&!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                            logger.info(fName+".has nHardName");
                            hasHardName=true;
                        }
                        if(hasHardName){
                            if(!disableNickName()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to change nickname!", llColorRed_Blood);
                            }
                        }
                        gNewUserProfile.cNAMETAG.setOn(false);
                        llSendQuickEmbedMessage(gUser,sRTitle,"You managed to pull the nametag off your neck, now you look like afree fur.", llColorBlue1);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle, gUser.getAsMention()+" managed to take off their nametag from their neck. Someone must have forgot to secure it.",llColorBlue1);
                    }
                }else{
                    logger.info(fName + ".mitts is not on");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The nametag is not on, silly.", llColorPurple1);
                }
            }
            saveProfile();
        }
        private void setNameTag(String command) {
            String fName = "[setNameTag]";
            logger.info(fName+".command="+ command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to access set to owner. Only your owner and sec-owners have access", llColorRed);
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your nametag due to access set to public. While public everyone else can access it except you.", llColorRed);
            }else
            if(command.equalsIgnoreCase("setsoft")){
                inputName(false,false);
            }else
            if(command.equalsIgnoreCase("sethard")){
                inputName(true,false);
            }else
            if(command.equalsIgnoreCase("clearsoft")){
                if(deleteSoftName()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You cleared your soft nametag.", llColorBlue1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to clear your soft nametag!", llColorRed_Blood);
                }
            }else
            if(command.equalsIgnoreCase("clearhard")){
                if(deleteHardName()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You cleared your hard nametag.", llColorBlue1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to clear your hard nametag!", llColorRed_Blood);
                }
            }else
            if(command.equalsIgnoreCase("setavatar")){
                requireAvatarUpdate();
            }else
            if(command.equalsIgnoreCase("setavatar2")){
                requireAvatarTextChannelUpdate();
            }else
            if(command.equalsIgnoreCase("clearavatar")){
                if(clearAvatar()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Avatar cleared.", llColorGreen2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to clear avatar!", llColorRed);
                }
            }
        }
        private void viewNameTag(Member member) {
            String fName = "[viewNameTag]";
            logger.info(fName+".member="+ member.getId());
            if(!getProfile(member)){logger.error(fName + ".can't get profile"); return;}
            EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorGreen1);
            embedBuilder.setTitle("NameTag for"+member.getUser().getName()+"#"+member.getUser().getDiscriminator());
            embedBuilder.addField("Enabled", String.valueOf(gNewUserProfile.cNAMETAG.isOn()),false);
            if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                logger.info(fName+".has nHardName");
                embedBuilder.addField("Hard NameTag",gNewUserProfile.cNAMETAG.getNewName(),false);
                if(!gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                    embedBuilder.addField("Old NameTag",gNewUserProfile.cNAMETAG.getOldName(),false);
                }
            }
            if(!gNewUserProfile.cNAMETAG.getSoftName().isBlank()){
                logger.info(fName+".has nSoftName");
                embedBuilder.addField("Soft NameTag",gNewUserProfile.cNAMETAG.getSoftName(),false);
            }
            llSendMessageWithDelete(gGlobal,gTextChannel,embedBuilder);
        }
        Message messageOwner=null;User messageOwnerUser=null;
        private void inputName(boolean isHardName, boolean isTargeted){
            String fName="[inputName]";
            logger.info(fName);
            try{
                logger.info(fName+"isHardName="+isHardName+", isTargeted="+isTargeted);
                if(isTargeted){
                    if(isHardName){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Please enter the hard nametag for "+gNewUserProfile.gUserProfile.getMember().getAsMention()+" or type `!cancel` to abort it.", llColorBlue1);
                    }else{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Please enter the soft nametag for "+gNewUserProfile.gUserProfile.getMember().getAsMention()+" or type `!cancel` to abort it.", llColorBlue1);
                    }
                }else{
                    if(isHardName){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Please enter the hard nametag or type `!cancel` to abort it.", llColorBlue1);
                    }else{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Please enter the soft nametag or type `!cancel` to abort it.", llColorBlue1);
                    }
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                logger.info(fName+".content.length="+content.length());
                                if(content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                    return;
                                }
                                if(content.length()>32){
                                    logger.warn(fName+".over the limit");
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Nametag can only be max 32 long", llColorRed);
                                    return;
                                }
                                if(content.length()<1){
                                    logger.warn(fName+".too low");
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Nametag must be provided", llColorRed);
                                    return;
                                }
                                if(isHardName){
                                    if(isTargeted){
                                        requestPermission2UpdateHardName(content);
                                    }else{
                                        if(updateHardName(content)){
                                            llSendQuickEmbedMessage(gUser,sRTitle,"You updated your hard nametag&nickname to: "+content, llColorBlue1);
                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,"Error updating hard nametag&nickname!", llColorRed_Blood);
                                        }
                                    }
                                }else{
                                    if(isTargeted){
                                        if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                                            requestPermission2UpdateSoftName(content);
                                        }else
                                        if(updateSoftName(content)){
                                            llSendQuickEmbedMessage(gUser,sRTitle,"You updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s soft nametag to: "+content, llColorBlue1);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" updated your soft nametag to: "+content, llColorBlue1);
                                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s soft nametag to: "+content,llColorBlue1);
                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,"Error updating "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s soft nametag!", llColorRed_Blood);
                                        }
                                    }else{
                                        if(updateSoftName(content)){
                                            llSendQuickEmbedMessage(gUser,sRTitle,"You updated your soft nametag to: "+content, llColorBlue1);
                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,"Error updating soft nametag!", llColorRed_Blood);
                                        }
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            }
                        },3, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private boolean updateSoftName(String content){
            String fName="[updateSoftName]";
            logger.info(fName);
            try{
                logger.info(fName+".content=" + content);
                gNewUserProfile.cNAMETAG.setOn(true);gNewUserProfile.cNAMETAG.setSoftName( content);
                return saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
        private void requireAvatarUpdate(){
            String fName="[requireAvatarUpdate]";
            logger.info(fName);
            try{
                Message message= lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,"Please submit an image file to use as your avatar for the gag.", llColors.llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The attachment is not an image!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image height or width is above 1024px!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if(updateAvatar(attachment)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Avatar updated.", llColorGreen2);
                                    }else{
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to update avatar!", llColorRed);
                                    }
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llSendQuickEmbedMessage(gUser,sRTitle,"No attachkments!", llColorRed);llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void requireAvatarTextChannelUpdate(){
            String fName="[requireAvatarTextChannelUpdate]";
            logger.info(fName);
            try{
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,gMember.getAsMention()+", please submit an image file to use as your avatar for the gag.");
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser)&&e.getChannel()==gTextChannel,
                        e -> {
                            try {
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The attachment is not an image!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image height or width is above 1024px!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }

                                    if(updateAvatar(attachment)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Avatar updated.", llColorGreen2);
                                    }else{
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to update avatar!", llColorRed);
                                    }
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"No attachkments!", llColorRed);llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private boolean updateAvatar(Message.Attachment attachment){
            String fName="[updateAvatar]";
            logger.info(fName);
            try{
                String url=attachment.getUrl();
                String name=attachment.getFileName();
                logger.info(fName+".posted.url=" + url);
                logger.info(fName+".posted.name=" + name);
                logger.info(fName+"uploading to muzzled fox server");
                InputStream is= lsStreamHelper.llGetInputStream4WebFile(url);
                Guild botHelper=gGlobal.getGuild(llGlobalHelper.llGuildKeyBotHelper);
                List<TextChannel>textChannels=botHelper.getTextChannelsByName("rd_avatars",false);
                TextChannel textChannel=textChannels.get(0);
                Message message=null;
                if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                    message=textChannel.sendMessage("Avatar\nfor: "+gNewUserProfile.gUserProfile.getUser().getName()+"#"+gNewUserProfile.gUserProfile.getUser().getDiscriminator()+"("+gNewUserProfile.gUserProfile.getUser().getIdLong()+")\nat guild: "+gGuild.getName()+"("+gGuild.getId()+")"+"\nby: self").addFile(is,name).complete();
                }else{
                    message=textChannel.sendMessage("Avatar\nfor: "+gNewUserProfile.gUserProfile.getUser().getName()+"#"+gNewUserProfile.gUserProfile.getUser().getDiscriminator()+"("+gNewUserProfile.gUserProfile.getUser().getIdLong()+")\nat guild: "+gGuild.getName()+"("+gGuild.getId()+")"+"\nby: "+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gUser.getIdLong()+")").addFile(is,name).complete();
                }
                Message.Attachment redirectedAttachment=message.getAttachments().get(0);
                logger.info(fName+".redirectedAttachment.url=" + redirectedAttachment.getUrl());
                logger.info(fName+".redirectedAttachment.name=" + redirectedAttachment.getFileName());
                gNewUserProfile.cNAMETAG.setOn(true);gNewUserProfile.cNAMETAG.setAvatar( redirectedAttachment.getUrl());
                return saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
        private boolean clearAvatar(){
            String fName="[clearAvatar]";
            logger.info(fName);
            try{
                gNewUserProfile.cNAMETAG.setAvatar( "");
                return saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
        private boolean updateHardName(String content){
            String fName="[updateHardName]";
            logger.info(fName);
            try{
                logger.info(fName+".content=" + content);
                boolean result=true;
                if(gNewUserProfile.cNAMETAG.getOldName().isEmpty()||gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                    logger.info(fName+".no old name>create");
                    String nickname=gNewUserProfile.gUserProfile.getMember().getNickname();
                    if(nickname!=null&&!nickname.isEmpty()&&!nickname.isBlank()){
                        logger.info(fName+".has nickname>save");
                        logger.info(fName+".nickname="+nickname);
                        gNewUserProfile.cNAMETAG.setOldName( nickname);
                    }
                }
                try {
                    gNewUserProfile.cNAMETAG.setOn(true);gNewUserProfile.cNAMETAG.setNewName( content);saveProfile();
                    if(!saveProfile())return false;
                    if(gUser!=gNewUserProfile.gUserProfile.getUser()){
                        try {
                            gNewUserProfile.gUserProfile.getMember().modifyNickname(content).reason("hard nametag update by "+gUser.getName()+"#"+gUser.getDiscriminator()).complete();
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            gNewUserProfile.gUserProfile.getMember().modifyNickname(content).reason("hard nametag update by ####").complete();
                        }
                    }else{
                        gNewUserProfile.gUserProfile.getMember().modifyNickname(content).reason("hard nametag update").complete();
                    }

                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,e.toString(),llColorRed_Cinnabar);return false;
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;

            }
        }
        private boolean enableNickName(){
            String fName="[enableNickName]";
            logger.info(fName);
            try{
                logger.info(fName);
                boolean hasHardName=false;String hardName=null;
                if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                    hardName=gNewUserProfile.cNAMETAG.getNewName();
                    logger.info(fName+".has hard nametag="+name);
                    hasHardName=true;
                }
                logger.info(fName + "hasHardName="+hasHardName);
                if(!hasHardName){
                    logger.warn(fName + ".null");
                    return false;
                }
                if(gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                    logger.info(fName+".no old name>create");
                    String nickname=gNewUserProfile.gUserProfile.getMember().getNickname();
                    if(nickname!=null&&!nickname.isEmpty()&&!nickname.isBlank()){
                        logger.info(fName+".has nickname>save");
                        logger.info(fName+".nickname="+nickname);
                        gNewUserProfile.cNAMETAG.setOldName( nickname);
                        if(!saveProfile())return false;
                    }
                }
                try {
                    gNewUserProfile.gUserProfile.getMember().modifyNickname(hardName).reason("re-enabled hard nametag").complete();
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,e.toString(),llColorRed_Cinnabar);return false;
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
        private boolean disableNickName(){
            String fName="[disableNickName]";
            logger.info(fName);
            try{
                logger.info(fName);
                boolean hasOldName=false;String oldName=null;
                if(!gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                    logger.info(fName+".no old name>create");
                    oldName=gNewUserProfile.cNAMETAG.getOldName();
                    hasOldName=true;
                }
                if(!hasOldName){return false;}
                try {
                    gNewUserProfile.gUserProfile.getMember().modifyNickname(oldName).reason("disabling nametag, restoring old name").complete();
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,e.toString(),llColorRed_Cinnabar);return false;
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
        private boolean deleteHardName(){
            String fName="[deleteHardName]";
            logger.info(fName);
            try{
                logger.info(fName);
                boolean result=true;
                gNewUserProfile.cNAMETAG.setNewName( "");
                if(gNewUserProfile.cNAMETAG.getSoftName().isBlank()){
                    logger.info(fName+".no nSoftName");
                    gNewUserProfile.cNAMETAG.setOn(false);
                }
                if(!gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                    logger.info(fName+".has old name>restore");
                    String oldNickName=gNewUserProfile.cNAMETAG.getOldName();
                    logger.info(fName+".oldNickName="+oldNickName);
                    try {
                        gNewUserProfile.gUserProfile.getMember().modifyNickname(oldNickName).reason("deleting hard nametag, restoring old name").complete(); gNewUserProfile.cNAMETAG.setOldName( "");
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,e.toString(),llColorRed_Cinnabar);result=false;
                    }
                }else{
                    logger.info(fName+".no old name");
                    try {
                        gNewUserProfile.gUserProfile.getMember().modifyNickname(null).reason("deleting hard nametag, restoring old name").complete();
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,e.toString(),llColorRed_Cinnabar);result=false;
                    }
                }
                logger.info(fName+".result="+result);return result&&saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean deleteSoftName(){
            String fName="[deleteSoftName]";
            logger.info(fName);
            try{
                logger.info(fName);
                gNewUserProfile.cNAMETAG.setSoftName( "");
                if(gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                    logger.info(fName+".no nHardName");
                    gNewUserProfile.cNAMETAG.setOn(false);
                }
                return saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        private void enableNameTag(Member mtarget, String command) {
            String fName = "[enableNameTag]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getEffectiveName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+mtarget.getAsMention()+" nametag due to their access setting.", llColorRed);return;
            }else
            if(command.equalsIgnoreCase("on")){
                if(!gIsOverride&&gNewUserProfile.cNAMETAG.isOn()){
                    logger.info(fName + ".mitts is already on");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The nametag are already on "+mtarget.getAsMention()+".", llColorPurple1);
                }else{
                    boolean hasHardName=false,hasSoftName=false;
                    if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                        logger.info(fName+".no nHardName");
                        hasHardName=true;
                    }
                    if(!gNewUserProfile.cNAMETAG.getSoftName().isBlank()){
                        logger.info(fName+".no nSoftName");
                        hasSoftName=true;
                    }
                    if(!hasHardName&&!hasSoftName){
                        logger.warn(fName+".no name set>ignore");
                        llSendQuickEmbedMessage(gUser,sRTitle,mtarget.getAsMention()+" has no soft/hard nametage set. Set one before enabling it.", llColorRed_Crayola);return;
                    }
                    if(hasHardName){
                        if(!enableNickName()){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to change nickname for "+mtarget.getAsMention()+"!", llColorRed_Blood);
                        }
                    }
                    gNewUserProfile.cNAMETAG.setOn(true);
                    llSendQuickEmbedMessage(gUser,sRTitle,"You put a nametag around "+mtarget.getAsMention()+"'s neck.", llColorBlue1);
                    llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getAsMention()+" places a nametag around your neck.", llColorBlue1);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" places nametag around "+mtarget.getAsMention()+"'s neck'",llColorBlue1);
                }
            }else
            if(command.equalsIgnoreCase("off")){
                if(gNewUserProfile.cNAMETAG.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't take it off from "+mtarget.getAsMention()+" while its locked.", llColorRed);
                    }else{
                        boolean hasHardName=false;
                        if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                            logger.info(fName+".no nHardName");
                            hasHardName=true;
                        }
                        if(hasHardName){
                            if(!disableNickName()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to change nickname for "+mtarget.getAsMention()+"!", llColorRed_Blood);
                            }
                        }
                        gNewUserProfile.cNAMETAG.setOn(false);
                        llSendQuickEmbedMessage(gUser,sRTitle,"You take the nametag off "+mtarget.getAsMention()+"'s neck.", llColorBlue1);
                        llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getAsMention()+" takes the nametag away.", llColorBlue1);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,  gUser.getAsMention()+" takes the nametag off "+mtarget.getAsMention()+"'s neck.",llColorBlue1);
                    }
                }else{
                    logger.info(fName + ".mitts is not on");
                    llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,"The nametag is not on for "+mtarget.getAsMention()+".", llColorPurple1);
                }
            }
            saveProfile();
        }
        private void setNameTag(Member mtarget, String command) {
            String fName = "[setNameTag]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getEffectiveName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}

            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their nametags due to their access setting.", llColorRed);return;
            }else
            if(command.equalsIgnoreCase("setsoft")){
                inputName(false,true);
            }else
            if(command.equalsIgnoreCase("sethard")){
                inputName(true,true);
            }else
            if(command.equalsIgnoreCase("clearsoft")){
                if(deleteSoftName()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You cleared "+mtarget.getAsMention()+"'s soft nametag.", llColorBlue1);
                    llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getAsMention()+" cleared your soft nametag.", llColorBlue1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to cleared "+mtarget.getAsMention()+"'s soft nametag!", llColorRed_Blood);
                }
            }else
            if(command.equalsIgnoreCase("clearhard")){
                if(deleteHardName()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You cleared "+mtarget.getAsMention()+"'s hard nametag.", llColorBlue1);
                    llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getAsMention()+" cleared your hard nametag.", llColorBlue1);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" cleared "+mtarget.getAsMention()+"'s hard nametag.", llColorBlue1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to cleared "+mtarget.getAsMention()+"'s hard nametag!", llColorRed_Blood);
                }
            }else
            if(command.equalsIgnoreCase("setavatar")){
                requireTargetAvatarUpdate();
            }else
            if(command.equalsIgnoreCase("setavatar2")){
                requireTargetAvatarTextChannelUpdate();
            }else
            if(command.equalsIgnoreCase("clearavatar")){
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    requestPermission2ClearAvatar();
                }else{
                    if(clearAvatar()){
                        llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" avatar got cleared.", llColorBlue1);
                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"Your avatar got cleared by "+gUser.getAsMention()+".", llColorBlue1);
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" cleared "+gNewUserProfile.gUserProfile.getMember().getAsMention()+" avatar.",llColorBlue1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to clear avatar!", llColorRed);
                    }
                }
            }
            saveProfile();
        }
        private void requireTargetAvatarUpdate(){
            String fName="[requireTargetAvatarUpdate]";
            logger.info(fName);
            try{
                Message message= lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,"Please submit an image file to use as "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s avatar for the gag.", llColors.llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The attachment is not an image!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image height or width is above 1024px!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    String imageUrl=attachment.getUrl();
                                    if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                                        requestPermission2UpdateAvatar(attachment);
                                    }else{
                                        if(updateAvatar(attachment)){
                                            llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" avatar got updated.", llColorBlue1);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You avatar got updated by "+gUser.getAsMention()+".", llColorBlue1);
                                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s avatar to: "+imageUrl,llColorBlue1);
                                        }else{
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to update avatar!", llColorRed);
                                        }
                                    }
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llSendQuickEmbedMessage(gUser,sRTitle,"No attachkments!", llColorRed);llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void requireTargetAvatarTextChannelUpdate(){
            String fName="[requireTargetAvatarTextChannelUpdate]";
            logger.info(fName);
            try{
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,gUser.getAsMention()+", please submit an image file to use as "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s avatar for the gag.");
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser)&&e.getChannel()==gTextChannel,
                        e -> {
                            try {
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The attachment is not an image!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image height or width is above 1024px!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    String imageUrl=attachment.getUrl();
                                    if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                                        requestPermission2UpdateAvatar(attachment);
                                    }else{
                                        if(updateAvatar(attachment)){
                                            llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" avatar got updated.", llColorBlue1);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You avatar got updated by "+gUser.getAsMention()+".", llColorBlue1);
                                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s avatar to: "+imageUrl,llColorBlue1);
                                        }else{
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "Failed to update avatar!", llColorRed);
                                        }
                                    }
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"No attachkments!", llColorRed);llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void requestPermission2UpdateHardName(String content){
            String fName="[requestPermission2UpdateHardName]";
            logger.info(fName);
            try{
                logger.info(fName+".content=" + content);
                Message messageSender=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please wait while "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" accepts/rejects your request for updating hard nametag.", llColorGreen2);
                Message message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" is trying to change your nickname to "+content+"\nDo you allow it?", llColorBlue1);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicodeEmote_X_Red)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" rejected your hard nametag update!", llColorBlue1);
                                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+" hard nametag update!", llColorBlue1);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    if(updateHardName(content)){
                                        llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" accepted your hard nametag update!", llColorBlue1);
                                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You accepted "+gUser.getAsMention()+" hard nametag update!", llColorBlue1);
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s hard nametag to: "+content,llColorBlue1);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,sRTitle,"Error updating "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s hard nametag&nickname!", llColorRed_Blood);
                                    }
                                }else{
                                    requestPermission2UpdateHardName(content);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);llMessageDelete(messageSender);
                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, "Timeout for accepting updating hard nametag from:"+gUser.getAsMention()+"!", llColorRed);
                            llSendQuickEmbedMessage(gUser, sRTitle, gNewUserProfile.gUserProfile.getUser().getAsMention()+" was too slow to respond!", llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void requestPermission2UpdateSoftName(String content){
            String fName="[requestPermission2UpdateHardName]";
            logger.info(fName);
            try{
                logger.info(fName+".content=" + content);
                Message messageSender=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please wait while "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" accepts/rejects your request for updating soft nametag.", llColorGreen2);
                Message message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" is trying to change your softname to "+content+"\nDo you allow it?", llColorBlue1);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicodeEmote_X_Red)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" rejected your soft nametag update!", llColorBlue1);
                                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+" soft nametag update!", llColorBlue1);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    if(updateSoftName(content)){
                                        llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" accepted your soft nametag update!", llColorBlue1);
                                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You accepted "+gUser.getAsMention()+" soft nametag update!", llColorBlue1);
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s soft nametag to: "+content,llColorBlue1);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,sRTitle,"Error updating "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s soft name!", llColorRed_Blood);
                                    }
                                }else{
                                    requestPermission2UpdateHardName(content);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);llMessageDelete(messageSender);
                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, "Timeout for accepting updating soft nametag from:"+gUser.getAsMention()+"!", llColorRed);
                            llSendQuickEmbedMessage(gUser, sRTitle, gNewUserProfile.gUserProfile.getUser().getAsMention()+" was too slow to respond!", llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void requestPermission2UpdateAvatar(Message.Attachment attachment){
            String fName="[requestPermission2UpdateAvatar]";
            logger.info(fName);
            try{
                logger.info(fName+".attachment=" + attachment.getUrl());
                Message messageSender=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please wait while "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" accepts/rejects your request for updating their avatar.", llColorGreen2);
                Message message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" is trying to change your avatar to "+attachment.getUrl()+"\nDo you allow it?", llColorBlue1);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicodeEmote_X_Red)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" rejected your avatar update!", llColorBlue1);
                                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+" avatar update!", llColorBlue1);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    if(updateAvatar(attachment)){
                                        llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" accepted your avatar update!", llColorBlue1);
                                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You accepted "+gUser.getAsMention()+" avatar update!", llColorBlue1);
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" updated "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s avatar to: "+attachment.getUrl(),llColorBlue1);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,sRTitle,"Error updating "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s avatar!", llColorRed_Blood);
                                    }
                                }else{
                                    requestPermission2UpdateAvatar(attachment);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);llMessageDelete(messageSender);
                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, "Timeout for accepting updating soft nametag from:"+gUser.getAsMention()+"!", llColorRed);
                            llSendQuickEmbedMessage(gUser, sRTitle, gNewUserProfile.gUserProfile.getUser().getAsMention()+" was too slow to respond!", llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void requestPermission2ClearAvatar(){
            String fName="[requestPermission2UpdateAvatar]";
            logger.info(fName);
            try{
                Message messageSender=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please wait while "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" accepts/rejects your request for clearing their avatar.", llColorGreen2);
                Message message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" is trying to clear your avatar.\nDo you allow it?", llColorBlue1);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicodeEmote_X_Red)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" rejected your avatar clear!", llColorBlue1);
                                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+" avatar clear!", llColorBlue1);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messageSender);
                                    if(clearAvatar()){
                                        llSendQuickEmbedMessage(gUser,sRTitle,gNewUserProfile.gUserProfile.getMember().getAsMention()+" accepted your avatar clear!", llColorBlue1);
                                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You accepted "+gUser.getAsMention()+" avatar clear!", llColorBlue1);
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" cleared "+gNewUserProfile.gUserProfile.getMember().getAsMention()+" avatar.",llColorBlue1);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,sRTitle,"Error updating "+gNewUserProfile.gUserProfile.getMember().getAsMention()+"'s avatar!", llColorRed_Blood);
                                    }
                                }else{
                                    requestPermission2ClearAvatar();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);llMessageDelete(messageSender);
                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, "Timeout for accepting updating soft nametag from:"+gUser.getAsMention()+"!", llColorRed);
                            llSendQuickEmbedMessage(gUser, sRTitle, gNewUserProfile.gUserProfile.getUser().getAsMention()+" was too slow to respond!", llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String gCommandFileMainPath =gFileMainPath+"tags/menuLevels.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(mtarget!=null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }

                embed.addField("Enabled", String.valueOf(gNewUserProfile.cNAMETAG.isOn()),false);

                if(!gNewUserProfile.cNAMETAG.getNewName().isBlank()){
                    logger.info(fName+".has nHardName");
                    embed.addField("Hard NameTag",gNewUserProfile.cNAMETAG.getNewName(),false);
                    if(!gNewUserProfile.cNAMETAG.getOldName().isBlank()){
                        embed.addField("Old NameTag",gNewUserProfile.cNAMETAG.getOldName(),false);
                    }
                }
                if(!gNewUserProfile.cNAMETAG.getSoftName().isBlank()){
                    logger.info(fName+".has nSoftName");
                    embed.addField("Soft NameTag",gNewUserProfile.cNAMETAG.getSoftName(),false);
                }

                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" on";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" setsoft";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" sethard";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" clearsoft";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" clearhard";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" setavatar";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" setavatar2, backup if the DM does not work do to Discord explicit filter.";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" clearavatar";
                //embed.setDescription(desc);
                if(gNewUserProfile.gUserProfile.getUser()==gUser||mtarget==null){
                    embed.addField(" ", "Please select a nametag option :"+desc, false);
                }else{
                    embed.addField(" ", "Please select a nametag option for " + mtarget.getAsMention() + ":"+desc, false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)).queue();*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {

                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuLevelsListener(message,mtarget);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsListener(Message message,Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.info(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level="on";break;
                                    case lsUnicodeEmotes.aliasTwo:level="setsoft";break;
                                    case lsUnicodeEmotes.aliasThree:level="sethard";break;
                                    case lsUnicodeEmotes.aliasFour:level="clearsoft";break;
                                    case lsUnicodeEmotes.aliasFive:level="clearhard";break;
                                    case lsUnicodeEmotes.aliasSix:level="setavatar";break;
                                    case lsUnicodeEmotes.aliasSeven:level="setavatar2";break;
                                    case lsUnicodeEmotes.aliasEight:level="clearavatar";break;
                                }
                                logger.info(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else
                                    if(mtarget!=null){
                                        if(level.equalsIgnoreCase("on")||level.equalsIgnoreCase("off")){
                                            enableNameTag(mtarget,level);
                                        }else{
                                            setNameTag(mtarget,level);
                                        }
                                    }else{
                                        if(level.equalsIgnoreCase("on")||level.equalsIgnoreCase("off")){
                                            enableNameTag(level);
                                        }else{
                                            setNameTag(level);
                                        }
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                String emoji=e.getReactionEmote().getEmoji();
                                String codepoints=e.getReactionEmote().getAsCodepoints();
                                logger.info(fName+"name="+name+", emoji="+emoji+", codepoints="+codepoints);
                                String level="";
                                logger.info(fName+"gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)="+gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasTwo));
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasZero))){level="off";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasOne))){level="on";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasTwo))){level="setsoft";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasThree))){level="sethard";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasFour))){level="clearsoft";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasFive))){level="clearhard";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasSix))){level="setavatar";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasSeven))){level="setavatar2";}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasEight))){level="clearavatar";}
                                logger.info(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else
                                    if(mtarget!=null){
                                        if(level.equalsIgnoreCase("on")||level.equalsIgnoreCase("off")){
                                            enableNameTag(mtarget,level);
                                        }else{
                                            setNameTag(mtarget,level);
                                        }
                                    }else{
                                        if(level.equalsIgnoreCase("on")||level.equalsIgnoreCase("off")){
                                            enableNameTag(level);
                                        }else{
                                            setNameTag(level);
                                        }
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

    }
}
