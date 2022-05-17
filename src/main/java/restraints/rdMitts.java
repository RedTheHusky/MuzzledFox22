package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.discordentities.lcMyEmbedBuilder;
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
import restraints.models.enums.MITTSLEVELS;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdMitts extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints,iMitts {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Mitts",gCommand="mitts";
    public rdMitts(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Mitts-DiscordRestraints";
        this.help = "rdMitts";
        this.aliases = new String[]{"mitt","mitts"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdMitts(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdMitts(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            logger.info(".run build CommandEvent");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build CommandEvent forward");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build CommandEvent target ");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
            logger.info(cName + ".run build GuildMessageReactionAddEvent");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build lUserCommand");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build lUserCommand target");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build SlashCommandEvent");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward){
            logger.info(".run build interactionHook ");
            launch(gGlobal,interactionHook,isForward,forward);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward, Member target){
            logger.info(".run build interactionHook target");
            launch(gGlobal,interactionHook,isForward,forward,target);
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                setTitleStr(rdMitts.this.sMainRTitle);setPrefixStr(rdMitts.this.llPrefixStr);setCommandStr(rdMitts.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdMitts_commands");
                lsUsefullFunctions.setThreadName4Display("rdMitts");
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
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cMITTS.release();});}
                            else if(gItems[0].equalsIgnoreCase(vOn)||gItems[0].equalsIgnoreCase(vOff)){rMittens(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(MITTSLEVELS.General.getName())||gItems[0].equalsIgnoreCase(MITTSLEVELS.Puppy.getName())||gItems[0].equalsIgnoreCase(MITTSLEVELS.Pony.getName())){rMittens(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if((gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.command)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name))&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||lsMemberIsBotOwner(gMember))){rMittens(iRDPatreon.patreonUser_239748154046545920_mitts.command);isInvalidCommand=false;}
                            else{
                                menuLevels(null);isInvalidCommand=false;
                            }
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
        private boolean quickCommands(String item1, String item2,Member target){
            String fName="[quickCommands]";
            logger.info(fName+"item1="+item1);
            logger.info(fName+"item2="+item2);
            logger.info(fName+"target="+target.getId());
            if(item1.equalsIgnoreCase(vOn)||item1.equalsIgnoreCase(vOff)){rMittens(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOn)||item2.equalsIgnoreCase(vOff)){rMittens(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(MITTSLEVELS.General.getName())||item1.equalsIgnoreCase(MITTSLEVELS.Pony.getName())||item1.equalsIgnoreCase(MITTSLEVELS.Puppy.getName())){rMittens(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(MITTSLEVELS.General.getName())||item2.equalsIgnoreCase(MITTSLEVELS.Pony.getName())||item2.equalsIgnoreCase(MITTSLEVELS.Puppy.getName())){rMittens(target,item2.toLowerCase()); return false;}
            if(gUser.getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID || target.getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID||lsMemberIsBotOwner(gMember)){
                if(item1.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name)||item2.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name)){rMittens(target,item1.toLowerCase()); return false;}
            }

            return true;
        }

    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> mitts ";
        String quickSummonWithSpace2=llPrefixStr+"mitts <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Pouches that trap the wearer's hands turning them into useless stumps.",false);
        desc=newLine+quickSummonWithSpace2+"on"+spacingDual+quickSummonWithSpace2+"off"+endLine;
        desc+=newLine+quickSummonWithSpace2+MITTSLEVELS.General.getName()+spacingDual+quickSummonWithSpace2+MITTSLEVELS.Pony.getName()+spacingDual+quickSummonWithSpace2+MITTSLEVELS.Puppy.getName()+endLine;
        desc+="\n**Note**\n  The sub can't take off the mitts if locked!\n  The sub can't undo other restraints while the mitts are on!";
        embed.addField("Commands",desc,false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    
    private boolean rMittens(String command) {
        String fName = "[rMittens]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        lcMyEmbedBuilder embedBuilder=new lcMyEmbedBuilder();
        embedBuilder.setTitle(sRTitle);
        embedBuilder.setColor(intColorRed);

        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cMITTS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
            return false;
        }else
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their mitts as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your mitts as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your mitts due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your mitts due to access set to owner. Only your owner and sec-owners have access", llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your mitts due to access set to public. While public everyone else can access it except you.", llColorRed);
            return false;
        }else
        if(command.equalsIgnoreCase(vOn)){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".mitts is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cMITTS.setOn( true);
                if(gIsOverride&&gNewUserProfile.cMITTS.getLevelAsString().isBlank()){gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());}
                else if(gIsOverride&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());}
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put  mittens over your paws.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put mitten on their paws.");
            }
        }else
        if(command.equalsIgnoreCase(MITTSLEVELS.General.getName())){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.General.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put  mittens over your paws.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put mitten on their paws.");
            }
        }else
        if(command.equalsIgnoreCase(MITTSLEVELS.Puppy.getName())){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.Puppy.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.Puppy.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put puppy mittens over your paws, making your hands helpless like a puppy.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put puppy mitten on their paws, making their hands helpless like a puppy.");
            }
        }else
        if(command.equalsIgnoreCase(MITTSLEVELS.Pony.getName())){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.Pony.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.Pony.getName());
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You put pony mittens over your paws, preparing you to drag a cart.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put pony mitten on their paws, preparing them to drag a cart.");
            }
        }else
        if(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.command)){
            gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( iRDPatreon.patreonUser_239748154046545920_mitts.level);
            iRDPatreon.patreonUser_239748154046545920_mitts.sendApplyingWearer(gNewUserProfile.gUserProfile,gMember,gTextChannel);
        }else
        if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cMITTS.isOn()){
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItfOffStraitjacket, llColorRed);
                    userNotificationMitts( actionStruggle, gUser.getAsMention()+" attempted to take off their mittens but the straitjacket sleeves are preventing it.");
                    return false;
                }
                else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    if(gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase( iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                        iRDPatreon.patreonUser_239748154046545920_mitts.cantTakeOffAsItsLocked(gMember, sRTitle,gTextChannel);
                    }else{
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        userNotificationMitts( actionStruggle,gUser.getAsMention()+" struggled to take off the mittens from their paws but failed do to its locked with a padlock.");
                    }
                    return false;
                }else{
                    gNewUserProfile.cMITTS.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed to pull the mittens off from your paws, now you can use your fingers again", llColorBlue1);
                    userNotificationMitts( actionTakeOff, gUser.getAsMention()+" managed to take off their mittens. Someone must have forgot to secure it.");
                }
            }else{
                logger.info(fName + ".mitts is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are not on, silly.", llColorPurple1);
            }
        }
        saveProfile();
        return true;
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private boolean rMittens(Member mtarget,String command) {
        String fName = "[rMittens]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
            logger.info(fName + ".isSameConfinement>return");
            sendOrUpdatePrivateEmbed(sRTitle, iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
            return false;
        }
        if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

            }
            return false;
        }
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cMITTS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
            return false;
        }else
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their mitts as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their mitts as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their mitts due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their mitts due to their access setting.", llColorRed);
            return false;
        }else
        if(command.equalsIgnoreCase("on")){
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".mitts is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on.", llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place mitts onto your paws.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place mitts onto !TARGET's paws.");
                    gAskHandlingHelper.doAsk(() -> {rMittensSave4Target(target,command);});
                    return false;
                }
                return rMittensSave4Target(target,command);
            }
        }
        else if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,"The mittens are already on, silly.", llColorPurple1);
        }
        else if(command.equalsIgnoreCase(MITTSLEVELS.General.getName())||command.equalsIgnoreCase(MITTSLEVELS.Puppy.getName())||command.equalsIgnoreCase(MITTSLEVELS.Pony.getName())){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place mitts onto your paws.");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place mitts onto !TARGET's paws.");
                gAskHandlingHelper.doAsk(() -> {rMittensSave4Target(target,command);});
                return false;
            }
            return rMittensSave4Target(target,command);
        }
        else if(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.command)){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place mitts onto your paws.");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place mitts onto !TARGET's paws.");
                gAskHandlingHelper.doAsk(() -> {rMittensSave4Target(target,command);});
                return false;
            }
            return rMittensSave4Target(target,command);
        }
        else if(command.equalsIgnoreCase("off")){
            if(gNewUserProfile.cMITTS.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take off the mitts from your paws.");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take off the mitts from !TARGET's paws.");
                        gAskHandlingHelper.doAsk(() -> {rMittensSave4Target(target,command);});
                        return false;
                    }
                    return rMittensSave4Target(target,command);
                }
            }else{
                logger.info(fName + ".mitts is not on");
                llSendQuickEmbedMessage(target,sRTitle,"The mittens are not on.", llColorPurple1);
            }
        }
        return  true;
    }
        private boolean rMittensSave4Target(User target,String command) {
            String fName = "[rMittensSave4Target]";
            logger.info(fName);
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);

            if(command.equalsIgnoreCase("on")){
                gNewUserProfile.cMITTS.setOn( true);
                if(gIsOverride&&gNewUserProfile.cMITTS.getLevelAsString().isBlank()){gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());}
                else if(gIsOverride&&gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(nNone)){gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());}
                sendOrUpdatePrivateEmbed(sRTitle,"You put mittens over "+target.getAsMention()+"'s paws.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" places the mittens over your paws..", llColorBlue1);
                userNotificationMitts(actionPutOn, gUser.getAsMention()+" places mittens over "+target.getAsMention()+"'s paws'");
            }else
            if(command.equalsIgnoreCase(MITTSLEVELS.General.getName())){
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.General.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put mittens over "+target.getAsMention()+" paws.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts mittens over your paws.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put mitten on "+target.getAsMention()+" paws.");
            }else
            if(command.equalsIgnoreCase(MITTSLEVELS.Puppy.getName())){
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.Puppy.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put puppy mittens over "+target.getAsMention()+" paws, making their hands helpless like a puppy.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts mittens over your paws, making your hands helpless like a puppy.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put puppy mitten on "+target.getAsMention()+" paws, making their hands helpless like a puppy.");
            }else
            if(command.equalsIgnoreCase(MITTSLEVELS.Pony.getName())){
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( MITTSLEVELS.Pony.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put pony mittens over "+target.getAsMention()+" paws, preparing them to drag a cart.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts mittens over your paws, preparing you to drag a cart.", llColorBlue1);
                userNotificationMitts( actionPutOn,gUser.getAsMention()+" has put pony mitten on "+target.getAsMention()+" paws, preparing them to drag a cart.");
            }else
            if(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.command)){
                gNewUserProfile.cMITTS.setOn( true);gNewUserProfile.cMITTS.setLevel( iRDPatreon.patreonUser_239748154046545920_mitts.level);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendApplyingUser(gNewUserProfile.gUserProfile,target,gMember,gTextChannel);
            }else
            if(command.equalsIgnoreCase("off")){
                gNewUserProfile.cMITTS.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You pull the mittens off "+target.getAsMention()+"'s paws.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" pulls the mittens off your paws..", llColorBlue1);
                userNotificationMitts(actionTakeOff,  gUser.getAsMention()+" pulls the mittens off "+target.getAsMention()+"'s paws'");
            }
            saveProfile();
            return true;
        }
        String gCommandFileMainPath =gFileMainPath+"mitts/menuLevels.json";
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
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
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
                if(gNewUserProfile.gUserProfile.jsonObject.has(nMitts)&&gNewUserProfile.cMITTS.isOn()){
                    embed.addField("Currently",gNewUserProfile.cMITTS.getLevelAsString(),false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+MITTSLEVELS.General.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+MITTSLEVELS.Puppy.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+MITTSLEVELS.Pony.getName();
                if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar)+" "+ iRDPatreon.patreonUser_239748154046545920_mitts.name;
                }
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a mitts option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
               embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isWearerDenied0MittswithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cMITTS.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cMITTS.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageMitts())desc+= iRdStr.strRestraintArmsCuffs;
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
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_mitts");
                    if(!gNewUserProfile.cMITTS.isOn()){messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);}
                    else{
                        switch (gNewUserProfile.cMITTS.getLevel()){
                            case General:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Pony:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                        }
                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                            if(gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                                messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasStar);
                            }
                        }else{
                            messageComponentManager.selectContainer.remOptionByValue(lsUnicodeEmotes.aliasStar);
                        }
                        if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions()){messageComponentManager.selectContainer.setDisabled();}
                    }
                    if(gNewUserProfile.isWearerDenied0MittswithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cMITTS.isOn())){
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
                menuLevelsWearerListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
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
                if(gNewUserProfile.gUserProfile.jsonObject.has(nMitts)&&gNewUserProfile.cMITTS.isOn()){
                    embed.addField("Currently",gNewUserProfile.cMITTS.getLevelAsString(),false);
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+MITTSLEVELS.General.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+MITTSLEVELS.Puppy.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+MITTSLEVELS.Pony.getName();
                if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar)+" "+ iRDPatreon.patreonUser_239748154046545920_mitts.name;
                }
                //embed.setDescription(desc);
                embed.addField(" ", "Please select a mitts option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);

                embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);

                if(gNewUserProfile.isLocked_checkMember(gMember)||gNewUserProfile.cSTRAITJACKET.areArmsRestrained()||gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageMitts()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        desc+=iRdStr.strRestraintJacketArms;
                    }
                    else if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageMitts()){
                        desc+=iRdStr.strRestraintArmsCuffs;
                    }
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)).queue();
                if(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageMitts()) {
                    if (gNewUserProfile.cMITTS.isOn())
                        message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)).queue();
                    if (!gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.General.getName()) || !gNewUserProfile.cMITTS.isOn())
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if (!gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.Puppy.getName()) || !gNewUserProfile.cMITTS.isOn())
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if (!gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(MITTSLEVELS.Pony.getName()) || !gNewUserProfile.cMITTS.isOn())
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if (gMember.getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID || gNewUserProfile.gUserProfile.getUser().getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID)
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
                }*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_mitts");
                    if(!gNewUserProfile.cMITTS.isOn()){messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);}
                    else{
                        if(gNewUserProfile.cMITTS.getLevel()==MITTSLEVELS.General)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cMITTS.getLevel()==MITTSLEVELS.Puppy)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                        if(gNewUserProfile.cMITTS.getLevel()==MITTSLEVELS.Pony)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                            if(gNewUserProfile.cMITTS.getLevelAsString().equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                                messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasStar);
                            }
                        }else{
                            messageComponentManager.selectContainer.remOptionByValue(lsUnicodeEmotes.aliasStar);
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember)){messageComponentManager.selectContainer.setDisabled();}
                    }
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageMitts()) {
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                if (gMember.getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID || gNewUserProfile.gUserProfile.getUser().getIdLong() == iRDPatreon.patreonUser_239748154046545920_mitts.userID)
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
                menuLevelsSomebodyListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
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
                                        rMittens(level);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
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
                                    case lsUnicodeEmotes.aliasOne:level=MITTSLEVELS.General.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=MITTSLEVELS.Puppy.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=MITTSLEVELS.Pony.getName();break;
                                    case lsUnicodeEmotes.aliasStar:
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                        break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    result=rMittens(level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=MITTSLEVELS.General.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=MITTSLEVELS.Puppy.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=MITTSLEVELS.Pony.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rMittens(level);
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=MITTSLEVELS.General.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=MITTSLEVELS.Puppy.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=MITTSLEVELS.Pony.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rMittens(level);
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
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
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
                                        rMittens(gNewUserProfile.getMember(),level);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
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
                                    case lsUnicodeEmotes.aliasOne:level=MITTSLEVELS.General.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=MITTSLEVELS.Puppy.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=MITTSLEVELS.Pony.getName();break;
                                    case lsUnicodeEmotes.aliasStar:
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                        break;
                                }
                                boolean result=false;
                                if(!level.isBlank()){
                                    result=rMittens(gNewUserProfile.getMember(),level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsSomebody();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=MITTSLEVELS.General.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=MITTSLEVELS.Puppy.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=MITTSLEVELS.Pony.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rMittens(gNewUserProfile.getMember(),level);
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=MITTSLEVELS.General.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=MITTSLEVELS.Puppy.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=MITTSLEVELS.Pony.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){
                                        if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_mitts.userID){
                                            level= iRDPatreon.patreonUser_239748154046545920_mitts.name;
                                        }
                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rMittens(gNewUserProfile.getMember(),level);
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
                }
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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
    private void userNotificationMitts(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nMitts;
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
