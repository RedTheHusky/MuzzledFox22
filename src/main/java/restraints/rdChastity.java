package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.emotes.lcEmote;
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
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.CHASTITYLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdChastity extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iRChastity {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Chastity",gCommand="rchastity";
    public rdChastity(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Chastity-DiscordRestraints";
        this.help = "rdChastity";
        this.aliases = new String[]{"rchastity"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdChastity(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdChastity(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            logger.info(".run build");
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
                setTitleStr(rdChastity.this.sMainRTitle);setPrefixStr(rdChastity.this.llPrefixStr);setCommandStr(rdChastity.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdChastity_commands");
                lsUsefullFunctions.setThreadName4Display("rdChastity");
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
                }else if(gGuildMessageReactionAddEvent!=null){
                    Message message=gGuildMessageReactionAddEvent.retrieveMessage().complete();
                    logger.info(fName + ".message.id=" +message.getId());
                    User messageUser=message.getAuthor();logger.info(fName + "message.user=" + messageUser);
                    Member messageMember=message.getMember();logger.info(fName + "messageMember:" + messageMember);
                    MessageReaction.ReactionEmote reactionEmote=gGuildMessageReactionAddEvent.getReactionEmote();
                    String emoji="";
                    if(reactionEmote.isEmoji()){
                        emoji=reactionEmote.getEmoji();
                    }

                    Emote emote=null;
                    if(reactionEmote.isEmote()){
                        emote=reactionEmote.getEmote();
                    }
                    logger.info(fName + ".emoji=" +emoji);
                    if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                        logger.info(fName + ".go for zap");
                        if(!getProfile(messageMember)){logger.error(fName + ".can't get profile"); return;}
                        /*if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                            logger.info(fName + ".can't use do to locked by not you");
                            return;
                        }else*/
                        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                            logger.info(fName + ".can't use do to access protected");
                            return;
                        }else
                        if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                            logger.info(fName + ".chastity not on");
                            return;
                        }else
                        if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isShockEnabled()){
                            logger.info(fName + ".chastity.Shock not on");
                            return;
                        }else
                        if(gMember==messageMember){
                            llSendQuickEmbedMessage(gTextChannel,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s chastity device as they get zapped by their own paws."), llColorPurple1);
                        }else{
                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sMainRTitle,"You feel 2 second intense zap at your locked up sex, as "+gMember.getAsMention()+" pushes the zap button.", llColorPurple1);
                            llSendQuickEmbedMessage(gTextChannel,sMainRTitle,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s chastity device as they get zapped by "+gMember.getAsMention()+".", llColorPurple1);
                        }
                        new rdPishock(gGlobal, iPishock.strRdAction2,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                    }
                    lcEmote emoteWand=new lcEmote(gGlobal.getGuild(llGlobalHelper.llGuildKeyBotHelper),"wand",true);

                    if(emoji.equalsIgnoreCase(gGlobal.emojis.getEmoji("gift_heart"))||(emote!=null&&emoteWand.getEmote()!=null&&emote.getIdLong()==emoteWand.getEmote().getIdLong())){
                        if(gNewUserProfile.gUserProfile.jsonObject.has(nSuit)&&gNewUserProfile.gUserProfile.jsonObject.has(nChastity))
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn)&&gNewUserProfile.cCHASTITY.isOn()){

                            switch (getGender()){
                                case vGenderMale:
                                    sendOrUpdatePrivateEmbed(null,"The suit bulge vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"", llColors.llColorBlue1);
                                    lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gMember.getAsMention()+"'s bulge.",llColors.llColorRed_Cinnabar);
                                    break;
                                case vGenderFemale:
                                    sendOrUpdatePrivateEmbed(null,"The suit sex vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"",llColors.llColorBlue1);
                                    lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gMember.getAsMention()+"'s sex.",llColors.llColorRed_Cinnabar);
                                    break;
                                default:
                                    sendOrUpdatePrivateEmbed(null,"The suit bulge/sex vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"",llColors.llColorBlue1);
                                    lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gMember.getAsMention()+"'s bulge/sex.",llColors.llColorRed_Cinnabar);
                                    break;
                            }
                        }
                    }
                }else
                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult){blocked();return;}
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
                        logger.info(fName + ".gItems.size=" + gItems.length);
                        logger.info(fName + ".gItems[0]=" + gItems[0]);
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
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cCHASTITY.release();});}
                            else if(gItems[0].equalsIgnoreCase(vOn)||gItems[0].equalsIgnoreCase(vOff)){
                                rChastity(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(levelChastityShockAdd)||gItems[0].equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())||gItems[0].equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())||gItems[0].equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())||gItems[0].equalsIgnoreCase(CHASTITYLEVELS.Null.getName())||gItems[0].equalsIgnoreCase(levelChastityShockRem)){
                                rChastity(gItems[0].toLowerCase());isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase(levelChastityZap)||gItems[0].equalsIgnoreCase(levelChastityPunish)||gItems[0].equalsIgnoreCase(levelChastityWarn)||gItems[0].equalsIgnoreCase(levelChastityReward)){
                                rZap(gItems[0].toLowerCase());isInvalidCommand=false;}
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
        private int getGender(){
            String fName="[getGender]";
            logger.info(fName );
            try {
                if(gNewUserProfile.gUserProfile.jsonObject.has(nGender)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nGender)){
                    int i=gNewUserProfile.gUserProfile.jsonObject.getInt(nGender);
                    if(0<=i&&i<=2){
                        return  i;
                    }
                }
                return  0;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                return  0;
            }
        }
        private boolean quickCommands(String item1, String item2,Member target){
            String fName="[quickCommands]";
            logger.info(fName+"item1="+item1);
            logger.info(fName+"item2="+item2);
            logger.info(fName+"target="+target.getId());
            if(item1.equalsIgnoreCase(vOn)||item1.equalsIgnoreCase(vOff)){
                rChastity(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOn)||item2.equalsIgnoreCase(vOff)){
                rChastity(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())||item1.equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())||item1.equalsIgnoreCase(levelChastityShockAdd)||item1.equalsIgnoreCase(levelChastityShockRem)||item1.equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())||item1.equalsIgnoreCase(CHASTITYLEVELS.Null.getName())){
                rChastity(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())||item2.equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())||item2.equalsIgnoreCase(levelChastityShockAdd)||item2.equalsIgnoreCase(levelChastityShockRem)||item2.equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())||item2.equalsIgnoreCase(CHASTITYLEVELS.Null.getName())){
                rChastity(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(levelChastityZap)||item1.equalsIgnoreCase(levelChastityWarn)||item1.equalsIgnoreCase(levelChastityPunish)||item1.equalsIgnoreCase(levelChastityReward)){
                rZap(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(levelChastityZap)||item2.equalsIgnoreCase(levelChastityWarn)||item2.equalsIgnoreCase(levelChastityPunish)||item2.equalsIgnoreCase(levelChastityReward)){
                rZap(target,item2.toLowerCase()); return false;}
            return true;
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="";
        String quickSummonWithSpace2=llPrefixStr+"rchastity <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Placing a lock over the subs reproductive organ.",false);
        desc=newLine+quickSummonWithSpace2+"on"+endLine;
        desc+=newLine+quickSummonWithSpace2+"off"+endLine;
        desc+=newLine+quickSummonWithSpace2+CHASTITYLEVELS.Cage.getName()+spacingDual+quickSummonWithSpace2+CHASTITYLEVELS.Belt.getName()+spacingDual+quickSummonWithSpace2+CHASTITYLEVELS.Udder.getName()+spacingDual+quickSummonWithSpace2+CHASTITYLEVELS.Null.getName()+endLine;
        desc+=newLine+quickSummonWithSpace2+levelChastityShockAdd+spacingDual+quickSummonWithSpace2+levelChastityShockRem+endLine+" too add/remove the shock device";
        desc+=newLine+quickSummonWithSpace2+levelChastityWarn+spacingDual+quickSummonWithSpace2+levelChastityZap+quickSummonWithSpace2+levelChastityPunish+quickSummonWithSpace2+levelChastityReward+endLine;
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
    
    private void rChastity(String command) {
        String fName = "[rChastity]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)&&!command.equalsIgnoreCase(levelChastityShockAdd)&&!command.equalsIgnoreCase(levelChastityShockRem)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their chastity as its part of the suit.", llColorRed);
            return;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueSuit, llColorRed);
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
                sendOrUpdatePrivateEmbed(sRTitle,iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                return;
            }
        }
        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueLocked.replaceAll("!LOCKER",gNewUserProfile.cLOCK.getUserWhoLockedPet()), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueAccess2Pet, llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,strCantAccess2Public, llColorRed);
            return;
        }else
        if(command.equalsIgnoreCase(vOn)){
            if(!gIsOverride&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".chastity is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The chastity is already on, silly.", llColorPurple1);
            }else{
                gNewUserProfile.cCHASTITY.setOn( true);
                if(gNewUserProfile.cCHASTITY.getLevelAsString().isBlank()||gNewUserProfile.cCHASTITY.getLevelAsString().equalsIgnoreCase(nNone)){
                    gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Belt.getName());
                }
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutCageOn, llColorBlue1);
                userNotificationChastity( actionPutOn,strPutCageOnMention.replaceAll("!USER",gUser.getAsMention()));
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cCHASTITY.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
            return;
        }else
        if(command.equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                return;
            }
            gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Cage.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutCageOn2, llColorBlue1);
            userNotificationChastity( actionPutOn,strPutCageOn2Mention.replaceAll("!USER",gUser.getAsMention()));
        }else
        if(command.equalsIgnoreCase(levelChastityShockAdd)){
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strNoDevice, llColorPurple1);
            }else
            if(!gIsOverride&&gNewUserProfile.cCHASTITY.isShockEnabled()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strShockAlreadyOn, llColorPurple1);
            }else{
                gNewUserProfile.cCHASTITY.setShockEnabled( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strShockAdd, llColorBlue1);
                userNotificationChastity( actionPutOn,strShockAddMention.replaceAll("!USER",gUser.getAsMention()));
            }
        }else
        if(command.equalsIgnoreCase(levelChastityShockRem)){
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strNoDevice, llColorPurple1);
            }else
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isShockEnabled()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strAlreadyOff, llColorPurple1);
            }else{
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isShockEnabled()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                    return;
                }
                gNewUserProfile.cCHASTITY.setShockEnabled( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strShockRem, llColorBlue1);
                userNotificationChastity( actionTakeOff,strShockRemMention.replaceAll("!USER",gUser.getAsMention()));
            }
        }else
        if(command.equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                return;
            }
            gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Belt.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutBeltOn, llColorBlue1);
            userNotificationChastity( actionPutOn,strPutBeltOnMention.replaceAll("!USER",gUser.getAsMention()));
        }else
        if(command.equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                return;
            }
            gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Udder.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutUderOn, llColorBlue1);
            userNotificationChastity( actionPutOn,strPutUderOnMention.replaceAll("!USER",gUser.getAsMention()));
        }else
        if(command.equalsIgnoreCase(CHASTITYLEVELS.Null.getName())){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                return;
            }
            gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Null.getName());
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strPutNullOn, llColorBlue1);
            userNotificationChastity( actionPutOn,strPutNullOnMention.replaceAll("!USER",gUser.getAsMention()));
        }else
        if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cCHASTITY.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,strCantDuePermanent, llColorRed);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed), llColorRed);
                    userNotificationChastity( actionStruggle, strCantOffDueJacket.replaceAll("!USER",gUser.getAsMention()));
                }
                else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    userNotificationChastity( actionStruggle,strCantOffDueLocked.replaceAll("!USER",gUser.getAsMention()));
                }else{
                    gNewUserProfile.cCHASTITY.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,strTakeOff, llColorBlue1);
                    userNotificationChastity( actionTakeOff, strTakeOffMention.replaceAll("!USER",gUser.getAsMention()));
                }
            }else{
                logger.info(fName + ".chastity is not on");
                sendOrUpdatePrivateEmbed(sRTitle,strAlreadyOff, llColorPurple1);
            }
        }
        saveProfile();
    }
        private void rZap(String command) {
            String fName = "[rZap]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapNoDevice.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);return;
            }else
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isShockEnabled()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapNoShock.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);return;
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
            if(command.equalsIgnoreCase(levelChastityWarn)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapWarn.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction10,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityZap)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapZap.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityPunish)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapPunish.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityReward)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,strZapReward.replaceAll("!USER",gUser.getAsMention()), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction6,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                new ChastityEmlalock(gGlobal, iChastityEmlalock.commandReward,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }
        }
   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private void rChastity(Member mtarget, String command) {
        String fName = "[rChastity]";
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
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)&&!command.equalsIgnoreCase(levelChastityShockAdd)&&!command.equalsIgnoreCase(levelChastityShockRem)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their chastity as its part of the suit.", llColorRed);
            return;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDuePartSuit, llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueLockedTarget.replaceAll("!LOCKER",gNewUserProfile.cLOCK.getUserWhoLockedPet()), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueSettingsTarget, llColorRed);return;
        }else
        if(command.equalsIgnoreCase("on")){
            if(!gIsOverride&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".chastity is already on");
                sendOrUpdatePrivateEmbed(sRTitle,"The chastity are already on.", llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you to set up chastity!");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to set up chastity for !TARGET!");
                    gAskHandlingHelper.doAsk(() -> {rChastitySave4Target(mtarget,command);});
                    return;
                }
                rChastitySave4Target(mtarget,command);
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cCHASTITY.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,strIsAlreadyOn, llColorPurple1);
            return;
        }else
        if(command.equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())||command.equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())||command.equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())||command.equalsIgnoreCase(CHASTITYLEVELS.Null.getName())){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can set up chastity!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can set up chastity for !TARGET!");
                gAskHandlingHelper.doAsk(() -> {rChastitySave4Target(mtarget,command);});
                return;
            }
            rChastitySave4Target(mtarget,command);
        }else
        if(command.equalsIgnoreCase(levelChastityShockAdd)){
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strNoDevice, llColorPurple1);
            }else
            if(!gIsOverride&&gNewUserProfile.cCHASTITY.isShockEnabled()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strShockAlreadyOn, llColorPurple1);
            }else{
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can enable chastity shock!");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can enable chastity shock for !TARGET!");
                    gAskHandlingHelper.doAsk(() -> {rChastitySave4Target(mtarget,command);});
                    return;
                }
                rChastitySave4Target(mtarget,command);
            }
        }else
        if(command.equalsIgnoreCase(levelChastityShockRem)){
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strNoDevice, llColorPurple1);
            }else
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isShockEnabled()){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,strShockAlreadyOff, llColorPurple1);
            }else{
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cCHASTITY.isShockEnabled()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantPermalockTarget), llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can disable chastity shock!");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can disable chastity shock for !TARGET!");
                    gAskHandlingHelper.doAsk(() -> {rChastitySave4Target(mtarget,command);});
                    return;
                }
                rChastitySave4Target(mtarget,command);
            }
        }else
        if(command.equalsIgnoreCase("off")){
            if(gNewUserProfile.cCHASTITY.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantPermalockTarget), llColorRed);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take of your chastity !");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take off !TARGET chastity!");
                        gAskHandlingHelper.doAsk(() -> {rChastitySave4Target(mtarget,command);});
                        return;
                    }
                    rChastitySave4Target(mtarget,command);
                }
            }else{
                logger.info(fName + ".chastity is not on");
                llSendQuickEmbedMessage(target,sMainRTitle,strAlreadyOff, llColorPurple1);
            }
        }

    }
        private void rZap(Member mtarget, String command) {
            String fName = "[rZap]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
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
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDueLocked2.replaceAll("!LOCKER",gNewUserProfile.cLOCK.getUserWhoLockedPet()), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,strCantDueSettings, llColorRed);return;
            }else
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isOn()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetNoDevice), llColorBlue1);return;
            }else
            if(!gIsOverride&&!gNewUserProfile.cCHASTITY.isShockEnabled()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetNoShock), llColorBlue1);return;
            }else
            if(command.equalsIgnoreCase(levelChastityWarn)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetWarn), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction10,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityZap)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetZap), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityPunish)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetPunish), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(levelChastityReward)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,null,stringReplacer(strZapTargetReward), llColorBlue1);
                new rdPishock(gGlobal,iPishock.strRdAction6,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                new ChastityEmlalock(gGlobal, iChastityEmlalock.commandReward,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }
        }
        private void rChastitySave4Target(Member mtarget, String command) {
            String fName = "[rChastitySave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase("on")){
                gNewUserProfile.cCHASTITY.setOn( true);
                if(gNewUserProfile.cCHASTITY.getLevelAsString().isBlank()||gNewUserProfile.cCHASTITY.getLevelAsString().equalsIgnoreCase(nNone)){
                    gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Belt.getName());
                }
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetPuttingNeutral1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetPuttingNeutral2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetPuttingNeutral3));
            }else
            if(command.equalsIgnoreCase(CHASTITYLEVELS.Cage.getName())){
                gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Cage.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetPuttingCage1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetPuttingCage2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetPuttingCage3));
            }else
            if(command.equalsIgnoreCase(CHASTITYLEVELS.Belt.getName())){
                gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Belt.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetPuttingBelt1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetPuttingBelt2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetPuttingBelt3));
            }else
            if(command.equalsIgnoreCase(CHASTITYLEVELS.Udder.getName())){
                gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Udder.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetPuttingUdder1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetPuttingUdder2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetPuttingUdder3));
            }else
            if(command.equalsIgnoreCase(CHASTITYLEVELS.Null.getName())){
                gNewUserProfile.cCHASTITY.setOn( true);gNewUserProfile.cCHASTITY.setLevel( CHASTITYLEVELS.Null.getName());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetPuttingNull1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetPuttingNull2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetPuttingNull3));}else
            if(command.equalsIgnoreCase(levelChastityShockAdd)) {
                gNewUserProfile.cCHASTITY.setShockEnabled( true);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetShockAdd1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetShockAdd2), llColorBlue1);
                userNotificationChastity(actionPutOn, stringReplacer(strTargetShockAdd3));}else
            if(command.equalsIgnoreCase(levelChastityShockRem)){
                gNewUserProfile.cCHASTITY.setShockEnabled( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetShockRem1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetShockRem2), llColorBlue1);
                userNotificationChastity(actionTakeOff, stringReplacer(strTargetShockRem3));}else
            if(command.equalsIgnoreCase("off")){
                gNewUserProfile.cCHASTITY.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strTargetTakingOff1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(strTargetTakingOff2), llColorBlue1);
                userNotificationChastity(actionTakeOff, stringReplacer(strTargetTakingOff3));
            }
            saveProfile();
        }
        
        boolean isMenuLevel=false;
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
        String gCommandFileMainPath =gFileMainPath+"chastity/menuLevels.json";
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle,strSupport,false);
                embed.setTitle(sRTitle+" Options");
                if(gNewUserProfile.gUserProfile.jsonObject.has(nChastity)&&gNewUserProfile.cCHASTITY.isOn()){
                    String level=gNewUserProfile.cCHASTITY.getLevelAsString();
                    boolean shock=gNewUserProfile.cCHASTITY.isShockEnabled();
                    if(shock){
                        embed.addField("Currently",level+"+shock device",false);
                    }else{
                        embed.addField("Currently",level,false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+CHASTITYLEVELS.Cage.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CHASTITYLEVELS.Belt.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CHASTITYLEVELS.Udder.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CHASTITYLEVELS.Null.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+" "+levelChastityShockAdd+", adds the shock device to the cage or belt";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+" "+levelChastityShockRem+", removes the shock device from cage or belt";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+levelChastityWarn+", the shock device beeps warning them";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+levelChastityZap+", the shock device zaps them, mild discomfort";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+levelChastityPunish+", the shock device shocks them painfully";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+levelChastityReward+", the shock device rewards them with a gentle and teasing shock, not enough for orgasm";
                embed.addField(" ", "Please select a chastity option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                try {
                    desc="\n**What is PiShock?** "+iPishock.strWhatIsDetails2;
                    StringBuilder desc3=new StringBuilder();
                    JSONObject shocker=gNewUserProfile.cPISHOCK.getChastityShockerJSON();
                    JSONArray tasks=new JSONArray();
                    if(gNewUserProfile.cPISHOCK.isShock4ChastityEnabled()){
                        desc+="\nEnabled";
                    }else{
                        desc+="\nDisabled";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn !error!";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish !error!";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                            i++;
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward !error!";
                    }
                    desc+="\nGo to `"+llPrefixStr+"pishock`";
                    embed.addField("PiShock",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(gNewUserProfile.isWearerDenied0ChastitywithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cCHASTITY.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cCHASTITY.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageChastity())desc+= iRdStr.strRestraintArmsCuffs;
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
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.selectContainer= messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    messageComponentManager.buttonContainer= messageComponentManager.messageBuildComponents.getComponent(1).getButtonsContainer();
                    if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                        messageComponentManager.buttonContainer.getById("!shockerenabled_id").setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+"Shock off").setCustomId(lsUnicodeEmotes.aliasArrowDoubleDown);
                    }else{
                        messageComponentManager.buttonContainer.getById("!shockerenabled_id").setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+"Shock on").setCustomId(lsUnicodeEmotes.aliasArrowDoubleUp);
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolA).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolB).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                    }
                    if(gNewUserProfile.cCHASTITY.isOn()){
                        if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Cage)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasOne).setDefault(true);
                        if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Belt)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasTwo).setDefault(true);
                        if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Udder)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasThree).setDefault(true);
                        if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Null)messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasFour).setDefault(true);

                    }else{
                        messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasZero).setDefault(true);
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolA).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolB).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                        if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                            messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleDown,true).setDisable();

                        }else{
                            messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleUp,true).setDisable();
                        }
                    }
                    if(gNewUserProfile.isWearerDenied0ChastitywithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cCHASTITY.isOn())){
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolA).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolB).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleUp,true).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleDown,true).setDisable();
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
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle,strSupport,false);
                embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                if(gNewUserProfile.gUserProfile.jsonObject.has(nChastity)&&gNewUserProfile.cCHASTITY.isOn()){
                    String level=gNewUserProfile.cCHASTITY.getLevelAsString();
                    boolean shock=gNewUserProfile.cCHASTITY.isShockEnabled();
                    if(shock){
                        embed.addField("Currently",level+"+shock device",false);
                    }else{
                        embed.addField("Currently",level,false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+CHASTITYLEVELS.Cage.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CHASTITYLEVELS.Belt.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CHASTITYLEVELS.Udder.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CHASTITYLEVELS.Null.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+" "+levelChastityShockAdd+", adds the shock device to the cage or belt";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+" "+levelChastityShockRem+", removes the shock device from cage or belt";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+levelChastityWarn+", the shock device beeps warning them";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+levelChastityZap+", the shock device zaps them, mild discomfort";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+levelChastityPunish+", the shock device shocks them painfully";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+levelChastityReward+", the shock device rewards them with a gentle and teasing shock, not enough for orgasm";
                embed.addField(" ", "Please select a chastity option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                try {
                    desc="\n**What is PiShock?** "+iPishock.strWhatIsDetails2;
                    StringBuilder desc3=new StringBuilder();
                    JSONObject shocker=gNewUserProfile.cPISHOCK.getChastityShockerJSON();
                    JSONArray tasks=new JSONArray();
                    if(gNewUserProfile.cPISHOCK.isShock4ChastityEnabled()){
                        desc+="\nEnabled";
                    }else{
                        desc+="\nDisabled";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn !error!";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish !error!";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=shocker.getJSONObject(nPishockActions).getJSONArray(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode);
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                                desc3.append("\ntype=").append(mode).append(iPishock.strDuration45).append(duration).append(iPishock.strItensity45).append(itensity);
                            }
                            i++;
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward !error!";
                    }
                    desc+="\nGo to `"+llPrefixStr+"pishock`";
                    embed.addField("PiShock",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                /*if(gNewUserProfile.cCHASTITY.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Cage)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Belt)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Udder)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(gNewUserProfile.cCHASTITY.getLevel()==CHASTITYLEVELS.Null)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(!gNewUserProfile.cCHASTITY.isShockEnabled())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp));
                    if(gNewUserProfile.cCHASTITY.isShockEnabled())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown));
                    if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                    }
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select= messageComponentManager.messageBuildComponents.getComponent(0);
                    messageComponentManager.buttonContainer= messageComponentManager.messageBuildComponents.getComponent(1).getButtonsContainer();
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_cage");
                    if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                        messageComponentManager. buttonContainer.getById("!shockerenabled_id").setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+"Shock off").setCustomId(lsUnicodeEmotes.aliasArrowDoubleDown);
                        if(gNewUserProfile.isLocked_checkMember(gMember)){
                            messageComponentManager.buttonContainer.setDisable();
                        }
                    }else{
                        messageComponentManager.buttonContainer.getById("!shockerenabled_id").setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+"Shock on").setCustomId(lsUnicodeEmotes.aliasArrowDoubleUp);
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolA).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolB).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                    }
                    if(gNewUserProfile.cCHASTITY.isOn()){
                        switch (gNewUserProfile.cCHASTITY.getLevel()){
                            case Cage:messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasOne).setDefault(true);break;
                            case Belt:messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasTwo).setDefault(true);break;
                            case Udder:messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasThree).setDefault(true);break;
                            case Null:messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasFour).setDefault(true);break;
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember)){
                            messageComponentManager.selectContainer.setDisabled();
                        }
                    }else{
                        messageComponentManager.selectContainer.getOptionByValue(lsUnicodeEmotes.aliasZero).setDefault(true);
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolA).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolB).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleUp,true).setDisable();
                        messageComponentManager.buttonContainer.getById(lsUnicodeEmotes.aliasArrowDoubleDown,true).setDisable();
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
                                String level="",category="";
                                category="shock";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasArrowDoubleUp:category="cage";level=levelChastityShockAdd;break;
                                    case lsUnicodeEmotes.aliasArrowDoubleDown:category="cage";level=levelChastityShockRem;break;
                                    case lsUnicodeEmotes.aliasSymbolA:level=levelChastityWarn;break;
                                    case lsUnicodeEmotes.aliasSymbolB:level=levelChastityZap;break;
                                    case lsUnicodeEmotes.aliasSymbolC:level=levelChastityPunish;break;
                                    case lsUnicodeEmotes.aliasSymbolD:level=levelChastityReward;break;
                                }

                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else
                                    if(category.equalsIgnoreCase("cage")){
                                        rChastity(level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
                                    }
                                    if(category.equalsIgnoreCase("shock")){
                                        rZap(level);
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
                                String level="",category="";
                                category="cage";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=CHASTITYLEVELS.Cage.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=CHASTITYLEVELS.Belt.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=CHASTITYLEVELS.Udder.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=CHASTITYLEVELS.Null.getName();break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else
                                    if(category.equalsIgnoreCase("cage")){
                                        isMenuLevel=true;rChastity(level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
                                    }
                                    if(category.equalsIgnoreCase("shock")){
                                        isMenuLevel=true;rZap(level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer();
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
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {

                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="",category="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="cage";level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="cage";level=CHASTITYLEVELS.Cage.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="cage";level=CHASTITYLEVELS.Belt.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="cage";level=CHASTITYLEVELS.Udder.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="cage";level=CHASTITYLEVELS.Null.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){category="cage";level=levelChastityShockAdd;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){category="cage";level=levelChastityShockRem;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="shock";level=levelChastityWarn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="shock";level=levelChastityZap;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category="shock";level=levelChastityPunish;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category="shock";level=levelChastityReward;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else
                                        if(category.equalsIgnoreCase("cage")){
                                            isMenuLevel=true;rChastity(level);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsWearer();
                                        }
                                        if(category.equalsIgnoreCase("shock")){
                                            isMenuLevel=true;rZap(level);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsWearer();
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
                                    String level="",category="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="cage";level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="cage";level=CHASTITYLEVELS.Cage.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="cage";level=CHASTITYLEVELS.Belt.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="cage";level=CHASTITYLEVELS.Udder.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="cage";level=CHASTITYLEVELS.Null.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){category="cage";level=levelChastityShockAdd;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){category="cage";level=levelChastityShockRem;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="shock";level=levelChastityWarn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="shock";level=levelChastityZap;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category="shock";level=levelChastityPunish;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category="shock";level=levelChastityReward;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else
                                        if(category.equalsIgnoreCase("cage")){
                                            isMenuLevel=true;rChastity(level);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsWearer();
                                        }
                                        if(category.equalsIgnoreCase("shock")){
                                            isMenuLevel=true;rZap(level);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsWearer();
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
                                String level="",category="";
                                category="shock";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasArrowDoubleUp:category="cage";level=levelChastityShockAdd;break;
                                    case lsUnicodeEmotes.aliasArrowDoubleDown:category="cage";level=levelChastityShockRem;break;
                                    case lsUnicodeEmotes.aliasSymbolA:level=levelChastityWarn;break;
                                    case lsUnicodeEmotes.aliasSymbolB:level=levelChastityZap;break;
                                    case lsUnicodeEmotes.aliasSymbolC:level=levelChastityPunish;break;
                                    case lsUnicodeEmotes.aliasSymbolD:level=levelChastityReward;break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else
                                    if(category.equalsIgnoreCase("cage")){
                                        isMenuLevel=true;rChastity(gNewUserProfile.getMember(),level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
                                    }
                                    if(category.equalsIgnoreCase("shock")){
                                        rZap(gNewUserProfile.getMember(),level);
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
                                String level="",category="";
                                category="cage";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=CHASTITYLEVELS.Cage.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=CHASTITYLEVELS.Belt.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=CHASTITYLEVELS.Udder.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=CHASTITYLEVELS.Null.getName();break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else
                                    if(category.equalsIgnoreCase("cage")){
                                        isMenuLevel=true;rChastity(gNewUserProfile.getMember(),level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
                                    }
                                    if(category.equalsIgnoreCase("shock")){
                                        isMenuLevel=true;rZap(gNewUserProfile.getMember(),level);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsSomebody();
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
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="",category="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="cage";level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="cage";level=CHASTITYLEVELS.Cage.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="cage";level=CHASTITYLEVELS.Belt.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="cage";level=CHASTITYLEVELS.Udder.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="cage";level=CHASTITYLEVELS.Null.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){category="cage";level=levelChastityShockAdd;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){category="cage";level=levelChastityShockRem;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="shock";level=levelChastityWarn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="shock";level=levelChastityZap;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category="shock";level=levelChastityPunish;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category="shock";level=levelChastityReward;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else
                                        if(category.equalsIgnoreCase("cage")){
                                            isMenuLevel=true;rChastity(gNewUserProfile.getMember(),level);menuLevels(gNewUserProfile.getMember());
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsSomebody();
                                        }
                                        if(category.equalsIgnoreCase("shock")){
                                            isMenuLevel=true;rZap(gNewUserProfile.getMember(),level);menuLevels(gNewUserProfile.getMember());
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsSomebody();
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
                                    String level="",category="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="cage";level="off";}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="cage";level=CHASTITYLEVELS.Cage.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="cage";level=CHASTITYLEVELS.Belt.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="cage";level=CHASTITYLEVELS.Udder.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="cage";level=CHASTITYLEVELS.Null.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){category="cage";level=levelChastityShockAdd;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){category="cage";level=levelChastityShockRem;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="shock";level=levelChastityWarn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="shock";level=levelChastityZap;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category="shock";level=levelChastityPunish;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category="shock";level=levelChastityReward;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else
                                        if(category.equalsIgnoreCase("cage")){
                                            isMenuLevel=true;rChastity(gNewUserProfile.getMember(),level);menuLevels(gNewUserProfile.getMember());
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsSomebody();
                                        }
                                        if(category.equalsIgnoreCase("shock")){
                                            isMenuLevel=true;rZap(gNewUserProfile.getMember(),level);menuLevels(gNewUserProfile.getMember());
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                            menuLevelsSomebody();
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
        private void userNotificationChastity(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands!=null&&gBDSMCommands.restraints!=null){
                    if(gBDSMCommands.restraints.getNotificationDisabled()){
                        logger.warn(fName+"notification disabled");
                        return;
                    }
                }else{
                    logger.warn(fName+"gBDSMCommands or restraints is null");
                }

                String field=nChastity;
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
