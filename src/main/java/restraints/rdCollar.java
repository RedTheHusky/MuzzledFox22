package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Red;
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
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.COLLARLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdCollar extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Collar";
    String gQuickPrefix="collar",gCommand="collar";
    public rdCollar(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Collar-DiscordRestraints";
        this.help = "rdEars";
        this.aliases = new String[]{gQuickPrefix,"collars"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdCollar(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }

    public rdCollar(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCollar(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
    protected class runLocal extends rdExtension implements Runnable,iCollar {
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
                setTitleStr(rdCollar.this.sMainRTitle);setPrefixStr(rdCollar.this.llPrefixStr);setCommandStr(rdCollar.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdCollar_commands");
                lsUsefullFunctions.setThreadName4Display("rdCollar");
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
                    /*if(!checkIFAllowed2UseCommand1_slash()){
                        return;
                    }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;*/
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
                    if(gGuildMessageReactionAddEvent!=null){
                        Message message=gGuildMessageReactionAddEvent.retrieveMessage().complete();
                        logger.info(fName + ".message.id=" +message.getId());
                        User messageUser=message.getAuthor();logger.info(fName + "message.user=" + messageUser);
                        Member messageMember=message.getMember();logger.info(fName + "messageMember:" + messageMember);
                        MessageReaction.ReactionEmote reactionEmote=gGuildMessageReactionAddEvent.getReactionEmote();
                        if(reactionEmote.isEmote()){
                            logger.info(fName + ".its emote>ignore");
                            return;
                        }
                        String emoji=reactionEmote.getEmoji();
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
                            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isOn()){
                                logger.info(fName + ".collar not on");
                                return;
                            }else
                            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isShockeEnabled()){
                                logger.info(fName + ".collar not on");
                                return;
                            }else
                            if(gMember==messageMember){
                                llSendQuickEmbedMessage(gTextChannel,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get zapped by their own paws."), llColorPurple1);
                            }else{
                                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You feel 2 second intense zap as "+gMember.getAsMention()+" pushes the zap button.", llColorPurple1);
                                llSendQuickEmbedMessage(gTextChannel,sRTitle,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get zapped by "+gMember.getAsMention()+".", llColorPurple1);
                            }
                            new rdPishock(gGlobal, iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                        }
                    }else{
                        logger.info(cName + fName + "basic@");
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
                                    isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cCOLLAR.release();});}
                                else if(gItems[0].equalsIgnoreCase(vOff)){
                                    rCollar(gItems[0].toLowerCase());isInvalidCommand=false;}
                                else if(gItems[0].equalsIgnoreCase(nBadWords)||gItems[0].equalsIgnoreCase("badword")){
                                    rShockCollar();isInvalidCommand=false;}
                                else if(gItems[0].equalsIgnoreCase(nEnforcedWords)||gItems[0].equalsIgnoreCase("enforce")){
                                    rShockCollar();isInvalidCommand=false;}
                                else if(gItems[0].equalsIgnoreCase("shock")){
                                    List<Role>mentionedroles=gCommandEvent.getMessage().getMentionedRoles();
                                    if(mentionedroles.isEmpty()){
                                        if(gItems.length<2){
                                            rShockCollar();isInvalidCommand=false;
                                        }
                                        else if(gItems[1].equalsIgnoreCase("enable")||gItems[1].equalsIgnoreCase("on")){
                                            rShockDevice(vOn);isInvalidCommand=false;
                                        }
                                        else if(gItems[1].equalsIgnoreCase("disable")||gItems[1].equalsIgnoreCase("off")){
                                            rShockDevice(vOff);isInvalidCommand=false;
                                        }
                                        else if(gItems[1].equalsIgnoreCase("bad")||gItems[1].equalsIgnoreCase("badword")||gItems[1].equalsIgnoreCase("badwords")){
                                            if(gItems.length<3){
                                                rShockCollar();isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("add")){
                                                if(getProfile()) menuBadwordAddText();isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("remove")&&gItems.length>3){
                                                if(getProfile()) {
                                                    PARAMETERS.put(keyCommand,iRestraints.vAdd);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,Integer.valueOf(gItems[3]));
                                                    rWords();
                                                }
                                                isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("list")){
                                                if(getProfile()) menuBadwordViewText(0);isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("clear")){
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This command is not implemented", llColors_Red.llColorRed);
                                            }
                                        }
                                        else if(gItems[1].equalsIgnoreCase("enforce")||gItems[1].equalsIgnoreCase("enforceword")||gItems[1].equalsIgnoreCase("enforcewords")){
                                            if(gItems.length<3){
                                                rShockCollar();isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("add")){
                                                if(getProfile()) menuEnforcedAddText();isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("remove")&&gItems.length>3){
                                                if(getProfile()) {
                                                    PARAMETERS.put(keyCommand,iRestraints.vAdd);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,Integer.valueOf(gItems[3]));
                                                    rWords();
                                                }
                                                isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("list")){
                                                if(getProfile()) menuEnforcedViewText(0);isInvalidCommand=false;
                                            }
                                            else if(gItems[2].equalsIgnoreCase("clear")){
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This command is not implemented", llColors_Red.llColorRed);

                                            }
                                        }
                                        else if(gItems[1].equalsIgnoreCase("server")){
                                            guildMenuShockCollar();isInvalidCommand=false;
                                        }
                                        if(isInvalidCommand){
                                            rShockCollar();isInvalidCommand=false;
                                        }
                                    }else{
                                        roleMenuShockCollar(mentionedroles.get(0));isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[0].equalsIgnoreCase("warn")||gItems[0].equalsIgnoreCase(lsUnicodeEmotes.aliasZap)||gItems[0].equalsIgnoreCase("punish")){
                                    rActionShock(gItems[0]);isInvalidCommand=false;}
                                else if(gItems[0].equalsIgnoreCase(COLLARLEVELS.Leather.getName())||gItems[0].equalsIgnoreCase(COLLARLEVELS.Latex.getName())||gItems[0].equalsIgnoreCase(COLLARLEVELS.Rubber.getName())||gItems[0].equalsIgnoreCase(COLLARLEVELS.Chain.getName())||gItems[0].equalsIgnoreCase(COLLARLEVELS.Iron.getName())){
                                    rCollar(gItems[0].toLowerCase());isInvalidCommand=false;}
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
            if(item1.equalsIgnoreCase(nBadWords)||item2.equalsIgnoreCase(nBadWords)){
                rShockCollar(target); return false;}
            if(item1.equalsIgnoreCase("badword")||item2.equalsIgnoreCase("badword")){
                rShockCollar(target); return false;}
            if(item1.equalsIgnoreCase(nEnforcedWords)||item2.equalsIgnoreCase("enforce")){
                rShockCollar(); return false;}
            if(item1.equalsIgnoreCase("shock")||item2.equalsIgnoreCase("shock")){
                rShockCollar();return false;}
            if(item1.equalsIgnoreCase("warn")||item2.equalsIgnoreCase("warn")){
                rActionShock(target,"warn");return false;}
            if(item1.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)||item2.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)){
                rActionShock(target,"zap");return false;}
            if(item1.equalsIgnoreCase("punish")||item2.equalsIgnoreCase("punish")){
                rActionShock(target,"punish");return false;}
            if(item1.equalsIgnoreCase(vOff)){
                rCollar(target,item1.toLowerCase()); return false;}
            if(item2.equalsIgnoreCase(vOff)){
                rCollar(target,item2.toLowerCase()); return false;}
            if(item1.equalsIgnoreCase(COLLARLEVELS.Leather.getName())||item1.equalsIgnoreCase(COLLARLEVELS.Latex.getName())||item1.equalsIgnoreCase(COLLARLEVELS.Rubber.getName())||item1.equalsIgnoreCase(COLLARLEVELS.Chain.getName())||item1.equalsIgnoreCase(COLLARLEVELS.Iron.getName())){rCollar(target,item1.toLowerCase());return false;}
            if(item2.equalsIgnoreCase(COLLARLEVELS.Leather.getName())||item2.equalsIgnoreCase(COLLARLEVELS.Latex.getName())||item2.equalsIgnoreCase(COLLARLEVELS.Rubber.getName())||item2.equalsIgnoreCase(COLLARLEVELS.Chain.getName())||item2.equalsIgnoreCase(COLLARLEVELS.Iron.getName())){rCollar(target,item2.toLowerCase());return false;}
            return true;
        }

    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="";
        String quickSummonWithSpace2=llPrefixStr+gQuickPrefix+" <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Have them proudly wear a symbol of submission or mark them as the toys they are.",false);
        desc=newLine+quickSummonWithSpace2+"off"+endLine;
        desc+=newLine+quickSummonWithSpace2+COLLARLEVELS.Leather.getName()+"|"+COLLARLEVELS.Latex.getName()+"|"+COLLARLEVELS.Rubber.getName()+"|"+COLLARLEVELS.Chain.getName()+"|"+COLLARLEVELS.Iron.getName()+endLine+"";
        embed.addField("Styles","Different style(level) collars:"+desc,false);
        desc+=newLine+quickSummonWithSpace2+"shock"+endLine+" To access the shock function menu.";
        desc+="\n\tbad-words: words that they cant say, gets shocked if they say it\n\tenforced-words: words that they should say, gets shocked if they dont say it";
        desc+=newLine+quickSummonWithSpace2+"warn"+endLine+" to deliver a warning";
        desc+=newLine+quickSummonWithSpace2+"zap"+endLine+" to deliver a short burst of shock";
        desc+=newLine+quickSummonWithSpace2+"punish"+endLine+" to deliver a long painful burst of shock";
        embed.addField("Shock function",desc,false);
        desc="This affects at a role level";
        desc+=newLine+quickSummonWithSpace2+"shock @Role"+endLine+" To access the shock function menu for roles.";
        desc+="\nallows enable or disable shock function for all members who have @Role";
        desc+="\nallows setting up @Role level bad-words and enforced-words";
        embed.addField("Role Shock function",desc,false);
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
    
    private boolean rCollar(String command) {
        String fName = "[rCollar]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cCOLLAR.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate restraints as they permanently locked!", llColorRed);
            return false;
        }else
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar as its part of the suit.", llColorRed);
            return false;
        }else
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
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                }
                return false;
            }
            if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                logger.info(fName + ".can't restrain wile cuffs are on");
                sendOrUpdatePrivateEmbed(sRTitle,iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                return false;
            }
        }
        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to owner. Only your owner and sec-owners have access", llColorRed);
            return false;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to public. While public everyone else can access it except you.", llColorRed);
            return false;
        }else
         if(!gIsOverride&& gNewUserProfile.cCOLLAR.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,"The collar is already on, silly.", llColorPurple1);return false;
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Leather.getName())){
           gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Leather.getName());
            if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You put a leather collar around your neck. Good boy, you're ready for walk.", llColorBlue1);
            userNotificationCollar( actionPutOn,gUser.getAsMention()+" has put a leather collar around their neck. Good boy, they ready for walk.");
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Latex.getName())){
           gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Latex.getName());
            if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You put a latex collar around your neck. Time to go out to the night club and have it used for what it was designed for.", llColorBlue1);
            userNotificationCollar( actionPutOn,gUser.getAsMention()+" has put a latex collar around their neck. Time to go out to the night club and have it used for what it was designed for.");
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Rubber.getName())){
           gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Rubber.getName());
            if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You put a rubber collar around your neck. Ready to serve and follow orders.", llColorBlue1);
            userNotificationCollar( actionPutOn,gUser.getAsMention()+" has put a rubber collar around their neck. Ready to serve and follow orders.");
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Chain.getName())){
           gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Chain.getName());
            if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You put a chain collar around your neck. Showing off the desire to be owned.", llColorBlue1);
            userNotificationCollar( actionPutOn,gUser.getAsMention()+" has put a chain collar around their neck. Showing off the desire to be owned.");
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Iron.getName())){
           gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Iron.getName());
            if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You put an iron collar around your neck. Like the slaves they used to wear as they been sold, owned and given orders.", llColorBlue1);
            userNotificationCollar( actionPutOn,gUser.getAsMention()+" has put an iron collar around their neck. Like the slaves they used to wear as they been sold, owned and given orders.");
        }else
        if(command.equalsIgnoreCase(vOff)){
            if(gNewUserProfile.cCOLLAR.isOn()){
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantGrabAnything), llColorRed);
                    return false;
                }
                else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    userNotificationCollar( actionStruggle,gUser.getAsMention()+" struggled to take off the collar but failed due to its locked with a padlock.");
                }else{
                   gNewUserProfile.cCOLLAR.setOn( false);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You managed to pull the collar off.", llColorBlue1);
                    userNotificationCollar( actionTakeOff, gUser.getAsMention()+" managed to take off their collar. Someone must have forgot to secure it.");
                }
            }else{
                logger.info(fName + ".ear muffs is not on");
                sendOrUpdatePrivateEmbed(sRTitle,"The collar is not on, silly.", llColorPurple1);
                return false;
            }
        }
        saveProfile();
       return  true;
    }
        final String cExceptionGuildOn="eg1", cExceptionGuildOff ="eg0",cExceptionUserOn="eu1", cExceptionUserOff ="ef0";
        private boolean rShockDevice(String command) {
            String fName = "[rShockDevice]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return false;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to public. While public everyone else can access it except you.", llColorRed);
                return false;
            }
            else if(command.equalsIgnoreCase(vOn)){
               gNewUserProfile.cCOLLAR.setShockeEnabled( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have turned it on. You will be shocked for saying a badword.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(vOff)){
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
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                        }
                        return false;
                    }
                    if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                        logger.info(fName + ".can't restrain wile cuffs are on");
                        sendOrUpdatePrivateEmbed(sRTitle,iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                        return false;
                    }
                }
               gNewUserProfile.cCOLLAR.setShockeEnabled( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have turned it off. You wont be shocked.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                gNewUserProfile.cCOLLAR.setAutoShockException4GuildGagException( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You are using the server gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                gNewUserProfile.cCOLLAR.setAutoShockException4GuildGagException( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You are not using the server gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                gNewUserProfile.cCOLLAR.setAutoShockException4UserGagException( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You are using the user gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                gNewUserProfile.cCOLLAR.setAutoShockException4UserGagException( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You are not using the user gag exception for the shock collar.",llColorPurple2);
            }
            saveProfile();
            return true;
        }
        private boolean rActionShock(String command) {
            String fName = "[rEars]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isOn()){
                logger.info(fName + ".collar not on");
                sendOrUpdatePrivateEmbed(sRTitle,"Not wearing collar.", llColorPurple1);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isShockeEnabled()){
                logger.info(fName + ".collar not on");
                sendOrUpdatePrivateEmbed(sRTitle,"Shock device is not turned on.", llColorPurple1);
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
                        iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                    }else{
                        logger.info(fName + ".default message");
                        sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                    }
                    return false;
                }
                if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                    logger.info(fName + ".can't restrain wile cuffs are on");
                    sendOrUpdatePrivateEmbed(sRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                }
            }
            else if(command.equalsIgnoreCase("warn")){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"A beep is heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as "+gMember.getAsMention()+" warns themselves.", llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction9,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }
            else if(command.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get zapped by their own paws."), llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }
            else if(command.equalsIgnoreCase("punish")){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"10 seconddischarge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get punished by their own paws."), llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                if(iChastityEmlalock.isInChastity(gNewUserProfile.gUserProfile)){
                    new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                }
            }
            return true;

        }
        JSONObject PARAMETERS=new JSONObject();String keyCommand="command",keyAtr="atr",keyValue="value";
        private boolean rWords() {
            String fName = "[rWords]";
            logger.info(fName);

            logger.info(fName + ". PARAMETERS="+ PARAMETERS.toString());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return false;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to public. While public everyone else can access it except you.", llColorRed);
                return false;
            }
            if( PARAMETERS.optString(keyAtr).equalsIgnoreCase(iRestraints.nBadWords)){
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vAdd)&&!PARAMETERS.optString(keyValue).isBlank()){
                    gNewUserProfile.cCOLLAR.putBadWords(PARAMETERS.getString(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have added "+PARAMETERS.getString(keyValue)+" as bad-word.",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vRem)&&PARAMETERS.optInt(keyValue,-1)>-1){
                    String text= gNewUserProfile.cCOLLAR.getText4BadWords(PARAMETERS.getInt(keyValue));
                    gNewUserProfile.cCOLLAR.remBadWords(PARAMETERS.getInt(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed "+text+" from bad-word.",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vClear)){
                    gNewUserProfile.cCOLLAR.clearBadWords();
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed all lines from bad-word.",llColorPurple2);
                }
            }
            if( PARAMETERS.optString(keyAtr).equalsIgnoreCase(iRestraints.nEnforcedWords)){
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vAdd)&&!PARAMETERS.optString(keyValue).isBlank()){
                    gNewUserProfile.cCOLLAR.putEnforcedWords(PARAMETERS.getString(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have added "+PARAMETERS.getString(keyValue)+" as enforce-word.",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vRem)&&PARAMETERS.optInt(keyValue,-1)>-1){
                    String text= gNewUserProfile.cCOLLAR.getText4EnforcedWords(PARAMETERS.getInt(keyValue));
                    gNewUserProfile.cCOLLAR.remEnforcedWords(PARAMETERS.getInt(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed "+text+" from enforce-word.",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vClear)){
                    gNewUserProfile.cCOLLAR.clearEnforcedWords();
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed all lines from enforce-word.",llColorPurple2);
                }
            }
            return true;
        }
   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private boolean rCollar(Member mtarget, String command) {
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
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
            }
            return false;
        }
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cCOLLAR.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
            return false;
        }else
        if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
            logger.info(fName + ".can't use do to special suit");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar as its part of the suit.", llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return false;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);
            return false;
        }else
        if(!gIsOverride&& gNewUserProfile.cCOLLAR.getLevelAsString().equalsIgnoreCase(command)){
            logger.info(fName + ".same level");
            sendOrUpdatePrivateEmbed(sRTitle,"The collar is already on, silly.", llColorPurple1);
            return false;
        }else
        if(command.equalsIgnoreCase(COLLARLEVELS.Leather.getName())||command.equalsIgnoreCase(COLLARLEVELS.Latex.getName())||command.equalsIgnoreCase(COLLARLEVELS.Rubber.getName())||command.equalsIgnoreCase(COLLARLEVELS.Chain.getName())||command.equalsIgnoreCase(COLLARLEVELS.Iron.getName())){
            if(gAskHandlingHelper.isAsk()){
                logger.info(fName + ".requesting update restraint");
                gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place a collar around your neck!");
                gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place a collar around !TARGET's neck'!");
                gAskHandlingHelper.doAsk(() -> {rSafe4Target(mtarget,command);});
                return true;
            }
            rSafe4Target(mtarget,command);
            return true;
        }else
        if(command.equalsIgnoreCase("off")){
            if(gNewUserProfile.cCOLLAR.isOn()){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't take off do to locked");
                    sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    return false;
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take the collar off around your neck!");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can take the collar off around !TARGET's neck'!");
                        gAskHandlingHelper.doAsk(() -> {rSafe4Target(mtarget,command);});
                        return true;
                    }
                    rSafe4Target(mtarget,command);
                    return true;
                }
            }else{
                logger.info(fName + ".ear muffs is not on");
                llSendQuickEmbedMessage(target,sRTitle,"The collar is not on.", llColorPurple1);
                return false;
            }
        }
        return false;
    }
        private boolean rWords(Member mtarget) {
            String fName = "[rWords]";
            logger.info(fName);
            logger.info(fName + ". PARAMETERS="+ PARAMETERS.toString());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);return false;
            }
            if( PARAMETERS.optString(keyAtr).equalsIgnoreCase(iRestraints.nBadWords)){
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vAdd)&&!PARAMETERS.optString(keyValue).isBlank()){
                    gNewUserProfile.cCOLLAR.putBadWords(PARAMETERS.getString(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have added "+PARAMETERS.getString(keyValue)+" as bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has added "+PARAMETERS.getString(keyValue)+" as bad-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+PARAMETERS.getString(keyValue)+" as bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vRem)&&PARAMETERS.optInt(keyValue,-1)>-1){
                    String text= gNewUserProfile.cCOLLAR.getText4BadWords(PARAMETERS.getInt(keyValue));
                    gNewUserProfile.cCOLLAR.remBadWords(PARAMETERS.getInt(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed "+text+" from bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has removed "+text+" from bad-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+text+" from bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);

                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vClear)){
                    gNewUserProfile.cCOLLAR.clearBadWords();
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed all lines from bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has removed all lines from bad-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed all lines from bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);

                }
            }
            if( PARAMETERS.optString(keyAtr).equalsIgnoreCase(iRestraints.nEnforcedWords)){
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vAdd)&&!PARAMETERS.optString(keyValue).isBlank()){
                    gNewUserProfile.cCOLLAR.putEnforcedWords(PARAMETERS.getString(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have added "+PARAMETERS.getString(keyValue)+" as bad-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has added "+PARAMETERS.getString(keyValue)+" as enforce-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+PARAMETERS.getString(keyValue)+" as enforce-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vRem)&&PARAMETERS.optInt(keyValue,-1)>-1){
                    String text= gNewUserProfile.cCOLLAR.getText4EnforcedWords(PARAMETERS.getInt(keyValue));
                    gNewUserProfile.cCOLLAR.remEnforcedWords(PARAMETERS.getInt(keyValue));
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed "+text+" from enforce-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has removed "+text+" from enforce-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+text+" from enforce-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                }
                if(PARAMETERS.optString(keyCommand).equalsIgnoreCase(iRestraints.vClear)){
                    gNewUserProfile.cCOLLAR.clearEnforcedWords();
                    if(!saveProfile()){return false;}
                    sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have removed all lines from enforce-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has removed all lines from enforce-word for you.",llColorPurple2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed all lines from enforce-word for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".",llColorPurple2);
                }
            }
            return true;
        }
        private boolean rShockDevice(Member mtarget, String command) {
            String fName = "[rShockDevice]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                }
                return false;
            }
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);return false;
            }
            else if(command.equalsIgnoreCase(vOff)||command.equalsIgnoreCase(vOn)){
                if(gNewUserProfile.cCOLLAR.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                    }else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they update your collar shockign device!");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's shocking device!");
                            gAskHandlingHelper.doAsk(() -> {rShockDevice4Target(mtarget,command);});
                            return true;
                        }
                        rShockDevice4Target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".already off");
                    llSendQuickEmbedMessage(target,sRTitle,"The shock device is already deactivated.", llColorPurple1);
                }
            }
            return true;
        }
        private boolean rActionShock(Member mtarget, String command) {
            String fName = "[rEars]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                }
                return false;
            }
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);
            }else
            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isOn()){
                logger.info(fName + ".collar not on");
                sendOrUpdatePrivateEmbed(sRTitle,"Not wearing collar.", llColorPurple1);
            }else
            if(!gIsOverride&&!gNewUserProfile.cCOLLAR.isShockeEnabled()){
                logger.info(fName + ".collar not on");
                sendOrUpdatePrivateEmbed(sRTitle,"Shock device is not turned on.", llColorPurple1);
            }else
            if(command.equalsIgnoreCase("warn")){
                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"A beep is heard from your collar as "+gMember.getAsMention()+" warns you.", llColorPurple1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"A beep is heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as "+gMember.getAsMention()+" warns them.", llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction9,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)){
                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You feel 2 second intense zap as "+gMember.getAsMention()+" pushes the zap button.", llColorPurple1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"2 second discharge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get zapped by "+gMember.getAsMention()+".", llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
            }else
            if(command.equalsIgnoreCase("punish")){
                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,"You feel 10 second painfull shock as "+gMember.getAsMention()+" teaches you a lesson.", llColorPurple1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"10 seconddischarge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get punished by "+gMember.getAsMention()+".", llColorPurple1);
                new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                if(iChastityEmlalock.isInChastity(gNewUserProfile.gUserProfile)){
                    new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                }
            }
            return true;
        }
        private boolean rSafe4Target(Member mtarget, String command) {
            String fName = "[rSafe4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cCOLLAR.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+mtarget.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their locks do to their access setting.", llColorRed);return false;
            }else
            if(command.equalsIgnoreCase(COLLARLEVELS.Leather.getName())){
               gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Leather.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a leather collar "+target.getAsMention()+"'s neck. Good boy, you're ready for walk.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts a leather collar around your neck. Good boy, you're ready for walk.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" puts a leather collar around "+target.getAsMention()+"'s neck. Good boy, you're ready for walk.");
            }else
            if(command.equalsIgnoreCase(COLLARLEVELS.Latex.getName())){
               gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Latex.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a latex collar "+target.getAsMention()+"'s neck. Time to go out to the night club.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts a latex collar around your neck. Time to go out to the night club.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" puts a latex collar around "+target.getAsMention()+"'s neck. Time to go out to the night club.");
            }else
            if(command.equalsIgnoreCase(COLLARLEVELS.Rubber.getName())){
               gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Rubber.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a rubber collar "+target.getAsMention()+"'s neck. Ready to serve and follow orders.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts a rubber collar around your neck. Ready to serve and follow orders.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" puts a rubber collar around "+target.getAsMention()+"'s neck. Ready to serve and follow orders.");
            }else
            if(command.equalsIgnoreCase(COLLARLEVELS.Chain.getName())){
               gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Chain.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a chain collar "+target.getAsMention()+"'s neck. Showing off the desire to be owned.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts a chain collar around your neck. Showing off the desire to be owned.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" puts a chain collar around "+target.getAsMention()+"'s neck. Showing off the desire to be chained to be owned.");
            }else
            if(command.equalsIgnoreCase(COLLARLEVELS.Iron.getName())){
               gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel( COLLARLEVELS.Iron.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put a iron collar "+target.getAsMention()+"'s neck. Like the slaves they used to wear as they been sold, owned and given orders.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" puts a iron collar around your neck. Like the slaves they used to wear as they been sold, owned and given orders.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" puts a iron collar around "+target.getAsMention()+"'s neck. Like the slaves they used to wear as they been sold, owned and given orders.");
            }else
            if(command.equalsIgnoreCase(vOff)){
               gNewUserProfile.cCOLLAR.setOn( false); gNewUserProfile.cCOLLAR.setLevel( nNone);
                sendOrUpdatePrivateEmbed(sRTitle,"You pull the collar off "+target.getAsMention()+"'s neck.", llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" pulls the collar off your neck.", llColorBlue1);
                userNotificationCollar(actionTakeOff,  gUser.getAsMention()+" pulls the collar off "+target.getAsMention()+"'s neck.");
            }
            saveProfile();
            return  true;
        }
        private boolean rShockDevice4Target(Member mtarget, String command) {
            String fName = "[rShockDevice4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                }
                return false;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cCOLLAR.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+mtarget.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return false;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their locks do to their access setting.", llColorRed);return false;
            }
            else if(command.equalsIgnoreCase(vOff)){
               gNewUserProfile.cCOLLAR.setShockeEnabled( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have turned it off for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+". They wont be shocked for saying a badword.",llColorPurple2);
                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has turned it off for you. You wont be shocked for saying a badword.",llColorPurple2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has turned it off for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+". They wont be shocked for saying a badword.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(vOn)){
               gNewUserProfile.cCOLLAR.setShockeEnabled( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You have turned it on for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+". They will be shocked for saying a badword.",llColorPurple2);
                llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), gTitleShockSystem,gUser.getAsMention()+" has turned it on for you. You will be shocked for saying a badword.",llColorPurple2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has turned it on for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+". They will be shocked for saying a badword.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                gNewUserProfile.cCOLLAR.setAutoShockException4GuildGagException( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You set "+mtarget.getAsMention()+" to use the server gag exception for the shock collar.",llColorPurple2);
                llSendQuickEmbedMessage(target, gTitleShockSystem,gUser.getAsMention()+" set that you use the server gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                gNewUserProfile.cCOLLAR.setAutoShockException4GuildGagException( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You set "+mtarget.getAsMention()+" to not use the server gag exception for the shock collar.",llColorPurple2);
                llSendQuickEmbedMessage(target, gTitleShockSystem,gUser.getAsMention()+" set that you don't use the server gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                gNewUserProfile.cCOLLAR.setAutoShockException4UserGagException( true);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You set "+mtarget.getAsMention()+" to use the user gag exception for the shock collar.",llColorPurple2);
                llSendQuickEmbedMessage(target, gTitleShockSystem,gUser.getAsMention()+" set that you use the user gag exception for the shock collar.",llColorPurple2);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                gNewUserProfile.cCOLLAR.setAutoShockException4UserGagException( false);
                sendOrUpdatePrivateEmbed(gTitleShockSystem,"You set "+mtarget.getAsMention()+" to not use the user gag exception for the shock collar.",llColorPurple2);
                llSendQuickEmbedMessage(target, gTitleShockSystem,gUser.getAsMention()+" set that you don't use the server gag exception for the shock collar.",llColorPurple2);
            }
                saveProfile();
            return  true;
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
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
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
                embed.addField(strTestSubjectTitle,strTestSubjectDesc,false);
                embed.addField(strSupportTitle,strSupport,false);
                embed.addField(" ","Please select a collar options.",false);
                if(gNewUserProfile.cCOLLAR.isOn()){
                    String level= gNewUserProfile.cCOLLAR.getLevelAsString();
                    embed.addField("Currently",level,false);
                    if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        embed.addField("Shock System","enabled",false);
                    }else{
                        embed.addField("Shock System","disabled",false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+COLLARLEVELS.Leather.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+COLLARLEVELS.Latex.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+COLLARLEVELS.Rubber.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+COLLARLEVELS.Chain.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+COLLARLEVELS.Iron.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" for collar shock system";
                embed.addField(" ", "Please select a collar option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                try {
                    desc="Status: ";
                    StringBuilder desc3=new StringBuilder();
                    JSONObject shocker=gNewUserProfile.cPISHOCK.getCollarShockerJSON();
                    JSONArray tasks=new JSONArray();
                    if(gNewUserProfile.cPISHOCK.isShock4CollarEnabled()){
                        desc+="Enabled";
                    }else{
                        desc+="Disabled";
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
                if(gNewUserProfile.isWearerDenied0CollarwithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cCOLLAR.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cCOLLAR.isOn()))desc=iRdStr.strRestraintLocked;
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
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,3);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_collar");
                    if(gNewUserProfile.cCOLLAR.isOn()) {
                        switch (gNewUserProfile.cCOLLAR.getLevel()){
                            case Leather: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Latex:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Rubber:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Chain: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case Iron: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                        }
                        if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.allow2BypassNewRestrictions())messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
                    }
                    if(gNewUserProfile.isWearerDenied0CollarwithException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cCOLLAR.isOn())){
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
                embed.addField(strTestSubjectTitle,strTestSubjectDesc,false);
                embed.addField(strSupportTitle,strSupport,false);
                embed.addField(" ","Please select a collar option for: "+gNewUserProfile.getMember().getAsMention()+".",false);
                if(gNewUserProfile.cCOLLAR.isOn()){
                    String level= gNewUserProfile.cCOLLAR.getLevelAsString();
                    embed.addField("Currently",level,false);
                    if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        embed.addField("Shock System","enabled",false);
                    }else{
                        embed.addField("Shock System","disabled",false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }

                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" off";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+COLLARLEVELS.Leather.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+COLLARLEVELS.Latex.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+COLLARLEVELS.Rubber.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+COLLARLEVELS.Chain.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+COLLARLEVELS.Iron.getName();
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" for collar shock system";
                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning)+" for PiShock collar linking";
                //desc+="\nCheck out the command `"+llPrefixStr+"pishock` for PiShock options.";
                embed.setDescription(desc);

                try {
                    desc="Status: ";
                    StringBuilder desc3=new StringBuilder();
                    JSONObject shocker=gNewUserProfile.cPISHOCK.getCollarShockerJSON();
                    JSONArray tasks=new JSONArray();
                    if(gNewUserProfile.cPISHOCK.isShock4CollarEnabled()){
                        desc+="Enabled";
                    }else{
                        desc+="Disabled";
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
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gNewUserProfile.cCOLLAR.isOn()) {
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(gNewUserProfile.cCOLLAR.getLevel()!=COLLARLEVELS.Leather)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(gNewUserProfile.cCOLLAR.getLevel()!=COLLARLEVELS.Latex)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(gNewUserProfile.cCOLLAR.getLevel()!=COLLARLEVELS.Rubber)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(gNewUserProfile.cCOLLAR.getLevel()!=COLLARLEVELS.Chain)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(gNewUserProfile.cCOLLAR.getLevel()!=COLLARLEVELS.Iron)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                // lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning)).queue();
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,3);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_collar");
                    if(gNewUserProfile.cCOLLAR.isOn()) {
                        switch (gNewUserProfile.cCOLLAR.getLevel()){
                            case Leather: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Latex:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Rubber:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Chain: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case Iron: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember))messageComponentManager.selectContainer.setDisabled();
                    }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
                    }
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        messageComponentManager.selectContainer.setDisabled();
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
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasZap:level=nBadWords;break;
                                    case lsUnicodeEmotes.aliasCloudLightning:level="pi";break;
                                }
                                logger.warn(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("pi")){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuPiShock();
                                    }
                                    else if(level.equalsIgnoreCase(nBadWords)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        rShockCollar();
                                    }else
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
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
                                String level="";
                                llMessageDelete(message);
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=COLLARLEVELS.Leather.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=COLLARLEVELS.Latex.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=COLLARLEVELS.Rubber.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=COLLARLEVELS.Chain.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=COLLARLEVELS.Iron.getName();break;
                                }
                                logger.warn(fName+"level="+level);
                                if(!level.isBlank()){
                                    isMenuLevel=true;boolean result=rCollar(level);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsWearer();
                                }

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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=COLLARLEVELS.Leather.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=COLLARLEVELS.Latex.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=COLLARLEVELS.Rubber.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=COLLARLEVELS.Chain.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=COLLARLEVELS.Iron.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){level=nBadWords;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning))){level="pi";}
                                    logger.warn(fName+"level="+level);
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("pi")){
                                            menuPiShock();
                                        }
                                        else if(level.equalsIgnoreCase(nBadWords)){
                                            rShockCollar();
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            isMenuLevel=true;rCollar(level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                if(gCurrentInteractionHook!=null)removeAction(message);
                                llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=COLLARLEVELS.Leather.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=COLLARLEVELS.Latex.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=COLLARLEVELS.Rubber.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=COLLARLEVELS.Chain.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=COLLARLEVELS.Iron.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){level=nBadWords;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning))){level="pi";}
                                    logger.warn(fName+"level="+level);
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("pi")){
                                            menuPiShock();
                                        }
                                        else if(level.equalsIgnoreCase(nBadWords)){
                                            rShockCollar();
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            isMenuLevel=true;rCollar(level);
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
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasZap:level=nBadWords;break;
                                    case lsUnicodeEmotes.aliasCloudLightning:level="pi";break;
                                }
                                logger.warn(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("pi")){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuPiShock();
                                    }
                                    else if(level.equalsIgnoreCase(nBadWords)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        rShockCollar(gNewUserProfile.getMember());
                                    }else
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
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
                                String level="";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level="off";break;
                                    case lsUnicodeEmotes.aliasOne:level=COLLARLEVELS.Leather.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=COLLARLEVELS.Latex.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=COLLARLEVELS.Rubber.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=COLLARLEVELS.Chain.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=COLLARLEVELS.Iron.getName();break;
                                }

                                logger.warn(fName+"level="+level);
                                if(!level.isBlank()){
                                    isMenuLevel=true;
                                    boolean result=rCollar(gNewUserProfile.getMember(),level);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsSomebody();
                                }
                                llMessageDelete(message);
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=COLLARLEVELS.Leather.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=COLLARLEVELS.Latex.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=COLLARLEVELS.Rubber.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=COLLARLEVELS.Chain.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=COLLARLEVELS.Iron.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){level=nBadWords;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning))){level="pi";}
                                    logger.warn(fName+"level="+level);
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("pi")){
                                            menuPiShock();
                                        }
                                        else if(level.equalsIgnoreCase(nBadWords)){
                                            rShockCollar(gNewUserProfile.getMember());
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            isMenuLevel=true;rCollar(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                if(gCurrentInteractionHook!=null)removeAction(message);
                                llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
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
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=COLLARLEVELS.Leather.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=COLLARLEVELS.Latex.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=COLLARLEVELS.Rubber.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=COLLARLEVELS.Chain.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=COLLARLEVELS.Iron.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){level=nBadWords;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCloudLightning))){level="pi";}
                                    logger.warn(fName+"level="+level);
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("pi")){
                                            menuPiShock();
                                        }
                                        else if(level.equalsIgnoreCase(nBadWords)){
                                            rShockCollar(gNewUserProfile.getMember());
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            isMenuLevel=true;rCollar(gNewUserProfile.getMember(),level);
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

        private void rShockCollar() {
            String fName = "[rBadword]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your collar due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }
            menuShockCollar();
        }
        private void rShockCollar(Member mtarget) {
            String fName = "[rBadword]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their collar due to their access setting.", llColorRed);return;
            }
            menuShockCollar();
        }
        private void menuShockCollar(){
            String fName="[menuShockCollar]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.addField(strTestSubjectTitle,strTestSubjectDesc,false);
                String desc="Select one option:";
                if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                    desc+="\n:zero: to turn off the collar shock system";
                }else{
                    desc+="\n:one: to turn on the collar shock system";
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()>0){
                    desc+="\n:two: to view badword list";
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0){
                    desc+="\n:three: to view enforced word list";
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()<30){
                    desc+="\n:four: to add new bad words";
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()<30){
                    desc+="\n:five: to add new enforced words";
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()>0){
                    desc+="\n:seven: to clear bad words list";
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0){
                    desc+="\n:nine: to clear enforced words list";
                }
                if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                    embed.setTitle(gTitleShockSystem);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+ gTitleShockSystem);
                }
                desc+="\n**bad words**, are list of words or sentences that if the user uses one of them get shocked as punishment";
                desc+="\n**enforced words**, are words or sentences that if the user does not use at least one of them, they get shocked as punishment";
                desc+="\n**Can be real**, this can be linked to a [PiShock](https://pishock.com/#/) shocker to send shocks to the user in real world.";
                embed.addField("Selecting option",desc,false);
                embed.addField("Limitations","You can have up to 30 entries.",false);
                desc="Set to use gag exception for auto shocking.\nOptions:";
                if(gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" don't use user gag exception";
                } else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" use user gag exception";
                }

                if(gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" don't use server gag exception";
                } else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" use server gag exception";
                }
                embed.addField("Auto shock exception", desc, false);
                if(gNewUserProfile.cCOLLAR.isOn()){
                    String level= gNewUserProfile.cCOLLAR.getLevelAsString();
                    embed.addField("Currently",level,false);
                    if( gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        embed.addField("Collar Shock System","enabled",false);
                    }else{
                        embed.addField("Collar Shock System","disabled",false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()<30){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()<30){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                if(gNewUserProfile.cCOLLAR.getBadWordsLength()>0){

                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                }
                if(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileShockPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(3,2);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        component0.getButtonAt4(0).setLabel("0️⃣ Disable").setStyle(ButtonStyle.DANGER).setCustomId("zero");
                    }else{
                        component0.getButtonAt4(0).setLabel("1️⃣ Enable").setStyle(ButtonStyle.SUCCESS).setCustomId("one");
                    }
                    if(!(gNewUserProfile.cCOLLAR.getBadWordsLength()>0)){
                        component1.getButtonAt4(0).setDisable();
                    }
                    if(!(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0)){
                        component2.getButtonAt4(0).setDisable();
                    }
                    if(!(gNewUserProfile.cCOLLAR.getBadWordsLength()<30)){
                        component1.getButtonAt4(1).setDisable();
                    }
                    if(!(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()<30)){
                        component2.getButtonAt4(1).setDisable();
                    }
                    if(!(gNewUserProfile.cCOLLAR.getBadWordsLength()>0)){
                        component1.getButtonAt4(2).setDisable();
                    }
                    if(!(gNewUserProfile.cCOLLAR.getEnforcedWordsLength()>0)){
                        component2.getButtonAt4(2).setDisable();
                    }
                    if(gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                        component0.getButtonAt4(1).setStyle(ButtonStyle.DANGER);
                    }
                    if(gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                        component0.getButtonAt4(2).setStyle(ButtonStyle.DANGER);
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuShockCollarListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockCollarListener(Message message){
            String fName="[menuShockCollarListener]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUser().getIdLong()==gUser.getIdLong()),
                    e -> {
                        if(gCurrentInteractionHook!=null) deferReplySet(e);
                        try {
                            String id=e.getButton().getId();
                            logger.info(fName+"id="+id);
                            isMenuLevel=true;
                            llMessageDelete(message);
                            boolean result=false;
                            switch (id){
                                case lsUnicodeEmotes.aliasArrowUp:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        menuLevels(null);
                                    }else{
                                        menuLevels(gNewUserProfile.gUserProfile.getMember());
                                    }
                                    break;
                                case lsUnicodeEmotes.aliasZero:
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOff);
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                                case lsUnicodeEmotes.aliasOne:
                                    if(!gNewUserProfile.cCOLLAR.isOn()){
                                        llMessageDelete(message);sendOrUpdatePrivateEmbed(gTitleShockSystem,"Can't enable shock function while collar is not on.",llColorRed_Chili);
                                        return;
                                    }
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOn);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOn);
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                                case lsUnicodeEmotes.aliasTwo:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Opening menu in dm",llColorBlue1);
                                    menuBadwordViewText(0);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    break;
                                case lsUnicodeEmotes.aliasThree:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Opening menu in dm",llColorBlue1);
                                    menuEnforcedViewText(0);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    break;
                                case lsUnicodeEmotes.aliasFour:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Opening menu in dm",llColorBlue1);
                                    menuBadwordAddText();
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    break;
                                case lsUnicodeEmotes.aliasFive:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Opening menu in dm",llColorBlue1);
                                    menuEnforcedAddText();
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    break;
                                case lsUnicodeEmotes.aliasSeven:
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nBadWords);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        result=rWords();
                                    }else{
                                        result=rWords(gNewUserProfile.getMember());
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                                case lsUnicodeEmotes.aliasNine:
                                    sendOrUpdatePrivateEmbed(sRTitle,"Clear enforcewords",llColorBlue1);
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);
                                    result=rWords();
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                                case lsUnicodeEmotes.aliasEuro:
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        result=rShockDevice(cExceptionUserOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        result=rShockDevice(cExceptionUserOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        result=rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOff);
                                    }else{
                                        result=rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOn);
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                                case lsUnicodeEmotes.aliasDollar:
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        rShockDevice(cExceptionGuildOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        result=rShockDevice(cExceptionGuildOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        result=rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOff);
                                    }else{
                                        result=rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOn);
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuShockCollar();
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        menuLevels(null);
                                    }else{
                                        menuLevels(gNewUserProfile.gUserProfile.getMember());
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOff);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    if(!gNewUserProfile.cCOLLAR.isOn()){
                                        llMessageDelete(message);sendOrUpdatePrivateEmbed(gTitleShockSystem,"Can't enable shock function while collar is not on.",llColorRed_Chili);
                                        return;
                                    }
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOn);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOn);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    menuBadwordViewText(0);
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    menuEnforcedViewText(0);
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    menuBadwordAddText();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    menuEnforcedAddText();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nBadWords);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    logger.warn(fName+"trigger="+name);
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);
                                    rWords();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        rShockDevice(cExceptionUserOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(cExceptionUserOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOn);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        rShockDevice(cExceptionGuildOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(cExceptionGuildOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOn);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            if(gCurrentInteractionHook!=null)removeAction(message);
                            llMessageDelete(message);
                            logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        menuLevels(null);
                                    }else{
                                        menuLevels(gNewUserProfile.gUserProfile.getMember());
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOff);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    if(!gNewUserProfile.cCOLLAR.isOn()){
                                        llMessageDelete(message);sendOrUpdatePrivateEmbed(gTitleShockSystem,"Can't enable shock function while collar is not on.",llColorRed_Chili);
                                        return;
                                    }
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(vOn);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),vOn);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    menuBadwordViewText(0);
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    menuEnforcedViewText(0);
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    menuBadwordAddText();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    menuEnforcedAddText();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nBadWords);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    logger.warn(fName+"trigger="+name);
                                    PARAMETERS.put(keyCommand,iRestraints.vClear);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);
                                    rWords();
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        rShockDevice(cExceptionUserOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(cExceptionUserOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4UserGagException()){
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionUserOn);
                                    }
                                }
                                else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()&&gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        rShockDevice(cExceptionGuildOff);
                                    }
                                    else if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                        rShockDevice(cExceptionGuildOn);
                                    }
                                    else if(gNewUserProfile.cCOLLAR.isAutoShockException4GuildGagException()){
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOff);
                                    }else{
                                        rShockDevice(gNewUserProfile.gUserProfile.getMember(),cExceptionGuildOn);
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
        }
        private void menuBadwordAddText(){
            String fName="[menuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                    embed.setTitle(gTitleShockSystem);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+ gTitleShockSystem);
                }
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) menuShockCollar();
                                }else
                                if(content.isBlank()){
                                    menuBadwordAddText();
                                }else{
                                    PARAMETERS.put(keyCommand,iRestraints.vAdd);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,content);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuBadwordViewText(int page){
            String fName="[menuViewText]";
            logger.info(fName);
            try{
                int length = gNewUserProfile.cCOLLAR.getBadWordsLength();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                    embed.setTitle(gTitleShockSystem);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+ gTitleShockSystem);
                }
                embed.addField("List","size: "+length+"",false);
                JSONObject quicklist=new JSONObject();boolean canBeQuickList=false;
                if(length<10){
                    quicklist=listofPreviews(gNewUserProfile.cCOLLAR.getBadWords());
                    if(quicklist.has("hitLimit")&&!quicklist.getBoolean("hitLimit")){
                        if(quicklist.has("text")&&!quicklist.getString("text").isBlank()&&quicklist.getString("text").length()<=2000){
                            canBeQuickList=true;
                        }
                    }
                }
                if(canBeQuickList){
                    embed.setDescription(quicklist.getString("text"));

                }else{
                    String text=gNewUserProfile.cCOLLAR.getText4BadWords(page);
                    if(text==null||text.isBlank()){
                        embed.setDescription("<no text>");
                    }
                    else if(text.length()>2000){
                        embed.setDescription("<text too big>");
                    }
                    else{
                        embed.setDescription(text);
                    }
                }

                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(canBeQuickList){
                    if(quicklist.optInt("index",-1)>=0)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(quicklist.optInt("index",-1)>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(quicklist.optInt("index",-1)>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(quicklist.optInt("index",-1)>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(quicklist.optInt("index",-1)>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(quicklist.optInt("index",-1)>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    if(quicklist.optInt("index",-1)>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    if(quicklist.optInt("index",-1)>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    if(quicklist.optInt("index",-1)>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                    if(quicklist.optInt("index",-1)>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                }else{
                    if(length>1){
                        if(page>0){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                        }
                        if(page<length-1){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                        }
                    }
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuShockCollar();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuBadwordViewText(page-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuBadwordViewText(page+1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,page);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,0);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,1);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,2);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,3);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,4);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,5);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,6);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,7);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,8);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nBadWords);PARAMETERS.put(keyValue,9);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuEnforcedAddText(){
            String fName="[menuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                    embed.setTitle(gTitleShockSystem);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+ gTitleShockSystem);
                }
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) menuShockCollar();
                                }else
                                if(content.isBlank()){
                                    menuEnforcedAddText();
                                }else{
                                    PARAMETERS.put(keyCommand,iRestraints.vAdd);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,content);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuEnforcedViewText(int page){
            String fName="[menuViewText]";
            logger.info(fName);
            try{
                int length = gNewUserProfile.cCOLLAR.getEnforcedWordsLength();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                    embed.setTitle(gTitleShockSystem);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+ gTitleShockSystem);
                }
                embed.addField("List","size: "+length+"",false);
                JSONObject quicklist=new JSONObject();boolean canBeQuickList=false;
                if(length<10){
                    quicklist=listofPreviews(gNewUserProfile.cCOLLAR.getEnforcedWords());
                    if(quicklist.has("hitLimit")&&!quicklist.getBoolean("hitLimit")){
                        if(quicklist.has("text")&&!quicklist.getString("text").isBlank()&&quicklist.getString("text").length()<=2000){
                            canBeQuickList=true;
                        }
                    }
                }
                if(canBeQuickList){
                    embed.setDescription(quicklist.getString("text"));

                }else{
                    String text=gNewUserProfile.cCOLLAR.getText4EnforcedWords(page);
                    if(text==null||text.isBlank()){
                        embed.setDescription("<no text>");
                    }
                    else if(text.length()>2000){
                        embed.setDescription("<text too big>");
                    }
                    else{
                        embed.setDescription(text);
                    }
                }
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(canBeQuickList){
                    if(quicklist.optInt("index",-1)>=0)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(quicklist.optInt("index",-1)>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(quicklist.optInt("index",-1)>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(quicklist.optInt("index",-1)>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(quicklist.optInt("index",-1)>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(quicklist.optInt("index",-1)>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    if(quicklist.optInt("index",-1)>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    if(quicklist.optInt("index",-1)>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    if(quicklist.optInt("index",-1)>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                    if(quicklist.optInt("index",-1)>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                }else{
                    if(length>1){
                        if(page>0){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                        }
                        if(page<length-1){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                        }
                    }
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuShockCollar();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuEnforcedViewText(page-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuEnforcedViewText(page+1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,page);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,0);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,1);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,2);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,3);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,4);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,5);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,6);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,7);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,8);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                    PARAMETERS.put(keyCommand,iRestraints.vRem);PARAMETERS.put(keyAtr,iRestraints.nEnforcedWords);PARAMETERS.put(keyValue,9);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rWords();
                                    }else{
                                        rWords(gNewUserProfile.getMember());
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private JSONObject listofPreviews(JSONArray array){
            String fName="[listofPreviews]";
            logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("hitLimit",false);
                jsonObject.put("text","");
                jsonObject.put("index",-1);
                logger.info(fName+"array="+ array.toString());
                int i=0;String result="";
                String tmp1="";
                StringBuilder tmp2= new StringBuilder();
                while(i<array.length()&&tmp1.length()<2000){
                    tmp2 = new StringBuilder();
                    String[] items = array.getString(i).split("\\s+");
                    if(items.length==1){
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+items[0];
                        }else{
                            tmp1+="\n"+i+". "+items[0];
                        }

                    }else{
                        int j=0;
                        int l=items.length;
                        if(l>3){
                            tmp2 = new StringBuilder(items[0] + " " + items[1] + " " + items[l - 1]);
                        }else{
                            while(j<l){
                                tmp2.append(items[j]).append(" ");
                                j++;
                            }
                        }
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+ tmp2.toString().trim();
                        }else{
                            tmp1+="\n"+i+". "+ tmp2.toString().trim();
                        }
                    }

                    if(tmp1.length()<2000){
                        result=tmp1;
                        jsonObject.put("index", i);
                    }else{
                        jsonObject.put("hitLimit",true);
                        jsonObject.put("index", i);
                    }
                    i++;
                }
                jsonObject.put("text",result);
                logger.info(fName+"result="+jsonObject.toString());
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }

        Role targetedRole;
        private void roleMenuShockCollar(String command){
            String fName="roleMenuShockCollar]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+ command);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="Select one option:";
                targetedRole= lsRoleHelper.lsGetRole(gGuild,command);
                if(targetedRole==null){
                    embed.setColor(llColorRed_Chili);
                    embed.setDescription("No role mentioned");
                    embed.setTitle(gTitleShockSystem +" for role ####");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                roleMenuShockCollar(targetedRole);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void roleMenuShockCollar(Role role){
            String fName="roleMenuShockCollar]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                targetedRole= role;
                if(targetedRole==null){
                    embed.setColor(llColorRed_Chili);
                    embed.setDescription("No role mentioned");
                    embed.setTitle(gTitleShockSystem +" for role ####");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    embed.setTitle(gTitleShockSystem +" for role "+targetedRole.getName());
                    embed.setColor(llColorRed_Chili);
                    embed.setDescription("For safety, as this affects everyone with the role "+targetedRole.getAsMention()+", you need manage roles or manage server permissions!");
                }
                embed.setColor(llColorBlue1);String desc="Select one option:";
                embed.setTitle(gTitleShockSystem +" for role "+targetedRole.getName());
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).has(targetedRole.getId())||gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).isNull(targetedRole.getId())){
                    gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).put(targetedRole.getId(), iRestraints.sCollarRoleNewEntry());
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getBoolean(nEnabled)){
                    desc+="\n:zero: to turn off the collar shock system for those with the role";
                }else{
                    desc+="\n:zero: to turn on the collar shock system for those with the role";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                    desc+="\n:two: to view badword list";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                    desc+="\n:three: to view enforced word list";
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).length()<30){
                    desc+="\n:four: to add new bad words";
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).length()<30){
                    desc+="\n:five: to add new enforced words";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                    desc+="\n:six: to remove bad words";
                    desc+="\n:seven: to clear bad words list";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                    desc+="\n:eight: to remove enforced words";
                    desc+="\n:nine: to clear enforced words list";
                }
                embed.addField("Selecting option",desc,false);
                embed.addField("Limitations","You can have up to 30 entries.",false);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse(gUser,embed);
                 lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).length()<30){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).length()<30){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                }

                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuOption("enabled","toggle");
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuBadwordViewText(false);
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuEnforcedViewText(false);
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuBadwordAddText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuEnforcedAddText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuBadwordRemoveText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    roleBadwordClearText();
                                }
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    logger.warn(fName+"trigger="+name);
                                    roleMenuEnforcedRemoveText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    logger.warn(fName+"trigger="+name);
                                    roleEnforcedClearText();
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void roleMenuOption(String command,String option){
            String fName="[guildMenuOption]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+ command+" ,option="+option);
                if(command.equalsIgnoreCase("enabled")){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).has(nEnabled)){
                            try {
                                logger.info(fName + ".has flagEnableCustom");
                                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getBoolean(nEnabled)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nEnabled,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled shock collar for role "+targetedRole.getAsMention()+".", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nEnabled,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled shock collar for role "+targetedRole.getAsMention()+".", llColorPurple2);
                    }
                }
                if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void roleMenuBadwordAddText(){
            String fName="[roleMenuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle(targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    roleMenuBadwordAddText();
                                }else{
                                    gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).put(content);
                                    if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+content+" as bad-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuBadwordRemoveText(){
            String fName="[roleMenuRemoveText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" you want to delete.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    roleMenuBadwordRemoveText();
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        content=finalArray.getString(i);
                                        finalArray.remove(i);
                                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nBadWords,finalArray);
                                        if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+content+" as bad-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                        if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                    }else{
                                        roleMenuBadwordRemoveText();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleBadwordClearText(){
            String fName="[roleBadwordClearText]";
            logger.info(fName);
            try{
                gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nBadWords,new JSONArray());
                if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has cleared all bad-words for "+targetedRole.getAsMention()+".",llColorPurple2);
                if(isMenuLevel) roleMenuShockCollar(targetedRole);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuBadwordViewText(boolean isPreview){
            String fName="[roleMenuViewText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" to view it.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    roleMenuBadwordViewText(isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text=finalArray.getString(i);
                                        roleMenuBadwordGenerateViewText(text,isPreview);
                                    }else{
                                        roleMenuBadwordViewText(isPreview);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuBadwordGenerateViewText(String text, boolean isPreview){
            String fName="[roleMenuGenerateViewText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("View text:\n"+text);
                Message message=llSendMessageResponse(gUser,embed);
                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                roleMenuBadwordViewText(isPreview);
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuEnforcedAddText(){
            String fName="[roleMenuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    roleMenuEnforcedAddText();
                                }else{
                                    gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).put(content);
                                    if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+content+" as enforced-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuEnforcedRemoveText(){
            String fName="[roleMenuRemoveText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" you want to delete.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    roleMenuEnforcedRemoveText();
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        content=finalArray.getString(i);
                                        finalArray.remove(i);
                                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nEnforcedWords,finalArray);
                                        if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+content+" as enforced-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                        if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                    }else{
                                        roleMenuBadwordRemoveText();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleEnforcedClearText(){
            String fName="[roleEnforcedClearText]";
            logger.info(fName);
            try{
                gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).put(nEnforcedWords,new JSONArray());
                if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has cleared all enforced-words for "+targetedRole.getAsMention()+".",llColorPurple2);
                if(isMenuLevel) roleMenuShockCollar(targetedRole);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuEnforcedViewText(boolean isPreview){
            String fName="[roleMenuViewText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" to view it.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) roleMenuShockCollar(targetedRole);
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    roleMenuEnforcedViewText(isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text=finalArray.getString(i);
                                        roleMenuEnforcedGenerateViewText(text,isPreview);
                                    }else{
                                        roleMenuEnforcedViewText(isPreview);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void roleMenuEnforcedGenerateViewText(String text, boolean isPreview){
            String fName="[roleMenuGenerateViewText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("@"+targetedRole.getName()+" "+ gTitleShockSystem);
                embed.setDescription("View text:\n"+text);
                Message message=llSendMessageResponse(gUser,embed);
                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                roleMenuEnforcedViewText(isPreview);
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }


        private void guildMenuShockCollar(){
            String fName="guildMenuShockCollar]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    embed.setTitle(gTitleShockSystem +" for role "+targetedRole.getName());
                    embed.setColor(llColorRed_Chili);
                    embed.setDescription("For safety, as this affects everyone you need manage server permissions!");
                }
                embed.setColor(llColorBlue1);String desc="Select one option:";
                embed.setTitle(gTitleShockSystem +" for server");
                if(!gBDSMCommands.collar.gProfile.jsonObject.getBoolean(fieldEnabled)){
                    desc+="\n:zero: to turn off the collar server shock system";
                }else{
                    desc+="\n:zero: to turn on the collar server shock system";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getBoolean(fieldEnabled)){
                    desc+="\n:one: to not use general server list";
                }else{
                    desc+="\n:one: to use general server list";
                }
             
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords).isEmpty()){
                    desc+="\n:two: to view badword list";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords).isEmpty()){
                    desc+="\n:three: to view enforced word list";
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords).length()<30){
                    desc+="\n:four: to add new bad words";
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords).length()<30){
                    desc+="\n:five: to add new enforced words";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords).isEmpty()){
                    desc+="\n:six: to remove bad words";
                    desc+="\n:seven: to clear bad words list";
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords).isEmpty()){
                    desc+="\n:eight: to remove enforced words";
                    desc+="\n:nine: to clear enforced words list";
                }
                embed.addField("Selecting option",desc,false);
                embed.addField("Limitations","You can have up to 30 entries.",false);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                 lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).length()<30){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                }
                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).length()<30){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nBadWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                }
                if(!gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(targetedRole.getId()).getJSONArray(nEnforcedWords).isEmpty()){
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                     lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                }

                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuOption("server","toggle");

                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuOption("general","toggle");
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuBadwordViewText(false);
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuEnforcedViewText(false);
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuBadwordAddText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuEnforcedAddText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuBadwordRemoveText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    roleBadwordClearText();
                                }
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    logger.warn(fName+"trigger="+name);
                                    guildMenuEnforcedRemoveText();
                                }else
                                if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    logger.warn(fName+"trigger="+name);
                                    roleEnforcedClearText();
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuOption(String command,String option){
            String fName="[guildMenuOption]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+ command+" ,option="+option);
                if(command.equalsIgnoreCase("enabled")){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.collar.gProfile.jsonObject.has(fieldEnabled)){
                            try {
                                logger.info(fName + ".has flagEnableCustom");
                                if(gBDSMCommands.collar.gProfile.jsonObject.getBoolean(fieldEnabled)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.collar.gProfile.jsonObject.put(fieldEnabled,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled server shock collar.", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.collar.gProfile.jsonObject.put(fieldEnabled,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled server shock collar.", llColorPurple2);
                    }
                }
                else if(command.equalsIgnoreCase("general")){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).has(fieldEnabled)){
                            try {
                                logger.info(fName + ".has flagEnableCustom");
                                if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getBoolean(fieldEnabled)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(fieldEnabled,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled using general server shock list.", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(fieldEnabled,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled using general server shock list.", llColorPurple2);
                    }
                }
                
                gBDSMCommands.collar.save();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void guildMenuBadwordAddText(){
            String fName="[guildMenuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("Server "+ gTitleShockSystem);
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    guildMenuBadwordAddText();
                                }else{
                                    gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords).put(content);
                                    if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+content+" as bad-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                    if(isMenuLevel) guildMenuShockCollar();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuBadwordRemoveText(){
            String fName="[guildMenuRemoveText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" you want to delete.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    guildMenuBadwordRemoveText();
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        content=finalArray.getString(i);
                                        finalArray.remove(i);
                                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(nBadWords,finalArray);
                                        if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+content+" as bad-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                        if(isMenuLevel) guildMenuShockCollar();
                                    }else{
                                        guildMenuBadwordRemoveText();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildBadwordClearText(){
            String fName="[roleBadwordClearText]";
            logger.info(fName);
            try{
                gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(nBadWords,new JSONArray());
                if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has cleared all bad-words for "+targetedRole.getAsMention()+".",llColorPurple2);
                if(isMenuLevel) guildMenuShockCollar();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuBadwordViewText(boolean isPreview){
            String fName="[guildMenuViewText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nBadWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" to view it.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    guildMenuBadwordViewText(isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text=finalArray.getString(i);
                                        guildMenuBadwordGenerateViewText(text,isPreview);
                                    }else{
                                        guildMenuBadwordViewText(isPreview);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuBadwordGenerateViewText(String text, boolean isPreview){
            String fName="[guildMenuGenerateViewText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("View text:\n"+text);
                Message message=llSendMessageResponse(gUser,embed);
                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                guildMenuBadwordViewText(isPreview);
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuEnforcedAddText(){
            String fName="[guildMenuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    guildMenuEnforcedAddText();
                                }else{
                                    gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords).put(content);
                                    if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has added "+content+" as enforced-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                    if(isMenuLevel) guildMenuShockCollar();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuEnforcedRemoveText(){
            String fName="[guildMenuRemoveText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" you want to delete.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    guildMenuEnforcedRemoveText();
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        content=finalArray.getString(i);
                                        finalArray.remove(i);
                                        gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(nEnforcedWords,finalArray);
                                        if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has removed "+content+" as enforced-word for "+targetedRole.getAsMention()+".",llColorPurple2);
                                        if(isMenuLevel) guildMenuShockCollar();
                                    }else{
                                        guildMenuBadwordRemoveText();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildEnforcedClearText(){
            String fName="[roleEnforcedClearText]";
            logger.info(fName);
            try{
                gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).put(nEnforcedWords,new JSONArray());
                if(!gBDSMCommands.collar.save()){lsMessageHelper.lsSendMessage(gTextChannel,"Failed to save!");return;}
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitleShockSystem,gUser.getAsMention()+" has cleared all enforced-words for "+targetedRole.getAsMention()+".",llColorPurple2);
                if(isMenuLevel) guildMenuShockCollar();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuEnforcedViewText(boolean isPreview){
            String fName="[guildMenuViewText]";
            logger.info(fName);
            try{
                JSONArray finalArray = gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(fieldGuild).getJSONArray(nEnforcedWords);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("Please enter nr of line between 0-"+(finalArray.length()-1)+" to view it.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    if(isMenuLevel) guildMenuShockCollar();
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    guildMenuEnforcedViewText(isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text=finalArray.getString(i);
                                        guildMenuEnforcedGenerateViewText(text,isPreview);
                                    }else{
                                        guildMenuEnforcedViewText(isPreview);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void guildMenuEnforcedGenerateViewText(String text, boolean isPreview){
            String fName="[guildMenuGenerateViewText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle( "Server "+ gTitleShockSystem);
                embed.setDescription("View text:\n"+text);
                Message message=llSendMessageResponse(gUser,embed);
                 lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                guildMenuEnforcedViewText(isPreview);
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }


        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            User user=null;String type="",zap="";boolean shockValue=false,shockProvided=false;
            boolean subdirProvided=false;
            slashReplyPleaseWait();
            gCurrentInteractionHook=gSlashInteractionHook;
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.subdir:
                        subdirProvided=true;
                        break;
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsUser();
                        }
                        break;
                    case llCommonKeys.SlashCommandReceive.type:
                        if(option.getType()==OptionType.STRING){
                            type=option.getAsString();
                        }
                        break;
                    case "zap":
                        if(option.getType()==OptionType.STRING){
                            zap=option.getAsString();
                        }
                        break;
                    case "shock":
                        if(option.getType()==OptionType.BOOLEAN){
                            shockValue=option.getAsBoolean();shockProvided=true;
                        }
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
                menuLevels(gTarget);
                return;
            }
            String messageAuth="",messagePublic="";
            if(gTarget==null){
                if(type.isBlank()&&zap.isBlank()&&!shockProvided){
                    menuLevelsWearer();
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't take off do to jacket");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed),llColorRed_Barn);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                    logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything),llColorRed_Barn);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't manipulate your collar due to they locked by !LOCKER"),llColorRed_Barn);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't manipulate your collar due to access set to owner. Only your owner and sec-owners have access"),llColorRed_Barn);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't manipulate your collar due to access set to public. While public everyone else can access it except you."),llColorRed_Barn);
                    return;
                }
                if(!zap.isBlank()){
                    if(!gNewUserProfile.cCOLLAR.isOn()){
                        messageAuth=textAdd(messageAuth,"Not wearing collar.");
                    }else
                    if(!gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        messageAuth=textAdd(messageAuth,"Not wearing shocker.");
                    }
                    else if(zap.equalsIgnoreCase("warn")){
                        messageAuth=textAdd(messageAuth,"A beep is heard.");
                        messagePublic=textAdd(messagePublic,"A beep is heard from !WEARER's collar as !USER warns themselves.");
                        new rdPishock(gGlobal,iPishock.strRdAction9,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                    }
                    else if(zap.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)){
                        messageAuth=textAdd(messageAuth,"A discard has happened");
                        messagePublic=textAdd(messagePublic,stringReplacer(iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"2 second discharge noise can be heard from !WEARER's collar as they get zapped by their own paws.")));
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                    }
                    else if(zap.equalsIgnoreCase("punish")){
                        messageAuth=textAdd(messageAuth,"You got shocked");
                        messagePublic=textAdd(messagePublic,stringReplacer(iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"10 seconddischarge noise can be heard from "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s collar as they get punished by their own paws.")));
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                        if(iChastityEmlalock.isInChastity(gNewUserProfile.gUserProfile)){
                            new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                        }
                    }
                }
                if(shockProvided){
                    if(!gNewUserProfile.cCOLLAR.isOn()){
                        messageAuth=textAdd(messageAuth,"Not wearing collar.");
                    }else
                    if(shockValue){
                        gNewUserProfile.cCOLLAR.setShockeEnabled( true);
                        messageAuth=textAdd(messageAuth,"You turned int on, you can be shocked now.");
                        messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has turned it on. They will be shocked for saying a badword."));
                    }else{
                        gNewUserProfile.cCOLLAR.setShockeEnabled( false);
                        messageAuth=textAdd(messageAuth,"You turned it off");
                        messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has turned it off. They wont be shocked."));
                    }
                }
                if(messageAuth.isBlank()&&!type.isBlank()){
                    if(type.equalsIgnoreCase(vOff)){
                        if(gNewUserProfile.cCOLLAR.isOn()){
                            if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                                logger.info(fName + ".can't take off do to locked");
                                messageAuth=textAdd(messageAuth,cantTakeItOffWhileItsLocked);
                            }else{
                                gNewUserProfile.cCOLLAR.setOn( false);
                                messageAuth=textAdd(messageAuth,"Taken collar off");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER managed to take off their collar. Someone must have forgot to secure it."));
                            }
                        }else{
                            logger.info(fName + ".is not on");
                            messageAuth=textAdd(messageAuth,"The collar is not on, silly.");
                        }
                    }else{
                        gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel(type);
                        switch (gNewUserProfile.cCOLLAR.getLevel()){
                            case Leather:
                                messageAuth=textAdd(messageAuth,"Put on a leather collar.");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has put a leather collar around their neck. Good boy, they ready for walk."));
                                break;
                            case Latex:
                                messageAuth=textAdd(messageAuth,"Put on a latex collar.");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has put a latex collar around their neck. Time to go out to the night club."));
                                break;
                            case Rubber:
                                messageAuth=textAdd(messageAuth,"Put on a rubber collar.");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has put a rubber collar around their neck. Ready to serve and follow orders."));
                                break;
                            case Chain:
                                messageAuth=textAdd(messageAuth,"Put on a chain collar.");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has put a chain collar around their neck. Showing off the desire to be chained to something or somebody."));
                                break;
                            case Iron:
                                messageAuth=textAdd(messageAuth,"Put on an iron collar.");
                                messagePublic=textAdd(messagePublic,stringReplacer("!WEARER has put an iron collar around their neck. Like the slaves they used to wear as they been sold, owned and given orders."));
                                break;
                        }
                    }

                }

            }else{
                if(type.isBlank()&&zap.isBlank()&&!shockProvided){
                    menuLevelsSomebody();
                    return;
                }
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".requesting update restraint");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iRdStr.strCatUseSlash4AskUseDmMeny),llColorRed_Barn);
                    return;
                }
                if(!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Can't manipulate their collar due to they locked by !LOCKER."),llColorRed_Barn);
                    return;
                }
                if(!gNewUserProfile.hasUserGotAccess2Restrain(gUser)) {
                    logger.info(fName + ".can't use do to access protected");
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer("Can't manipulate their collar due to their access setting."), llColorRed_Barn);
                    return;
                }
                if(!zap.isBlank()){
                    if(!gNewUserProfile.cCOLLAR.isOn()){
                        messageAuth=textAdd(messageAuth,gTarget.getAsMention()+" not wearing collar.");
                    }else
                    if(!gNewUserProfile.cCOLLAR.isShockeEnabled()){
                        messageAuth=textAdd(messageAuth,gTarget.getAsMention()+" not wearing shocker.");
                    }
                    else if(zap.equalsIgnoreCase("warn")){
                        messageAuth=textAdd(messageAuth,"A beep is heard.");
                        messagePublic=textAdd(messagePublic,stringReplacer("A beep is heard from !WEARER's collar as !USER warns them."));
                        new rdPishock(gGlobal,iPishock.strRdAction9,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                    }
                    else if(zap.equalsIgnoreCase(lsUnicodeEmotes.aliasZap)){
                        messageAuth=textAdd(messageAuth,"A dischard has happened.");
                        messagePublic=textAdd(messagePublic,stringReplacer("10 seconddischarge noise can be heard from !WEARER's collar as they get punished by !USER."));
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                    }
                    else if(zap.equalsIgnoreCase("punish")){
                        messageAuth=textAdd(messageAuth,"A shoc khappened.");
                        messagePublic=textAdd(messagePublic,stringReplacer(iRestraints.sStringReplacerifIsSpeciesHuman(gNewUserProfile.gUserProfile,"10 seconddischarge noise can be heard from !WEARER's collar as they get punished by their own paws.")));
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                        if(iChastityEmlalock.isInChastity(gNewUserProfile.gUserProfile)){
                            new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gNewUserProfile.gUserProfile.getMember());
                        }
                    }
                }
                if(shockProvided){
                    if(!gNewUserProfile.cCOLLAR.isOn()){
                        messageAuth=textAdd(messageAuth,gTarget.getAsMention()+" not wearing collar.");
                    }else
                    if(shockValue){
                        gNewUserProfile.cCOLLAR.setShockeEnabled( true);
                        messagePublic=textAdd(messagePublic,stringReplacer("!USER has turned it on for !WEARER. They will be shocked for saying a badword."));
                    }else{
                        gNewUserProfile.cCOLLAR.setShockeEnabled( false);
                        messagePublic=textAdd(messagePublic,stringReplacer("!USER has turned it off for !WEARER. They wont be shocked for saying a badword."));
                    }
                }
                if(messageAuth.isBlank()&&!type.isBlank()){
                        if(type.equalsIgnoreCase(vOff)){
                            if(gNewUserProfile.cCOLLAR.isOn()){
                                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                                    logger.info(fName + ".can't take off do to locked");
                                    messageAuth=textAdd(messageAuth,cantTakeItOffWhileItsLocked);
                                }else{
                                    gNewUserProfile.cCOLLAR.setOn( false);
                                    messageAuth=textAdd(messageAuth,"Taken off");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER pulls the collar off !WEARER's neck."));
                                }
                            }else{
                                logger.info(fName + ".is not on");
                                messageAuth=textAdd(messageAuth,"The collar is not on, silly.");
                            }
                        }else{
                            gNewUserProfile.cCOLLAR.setOn( true);gNewUserProfile.cCOLLAR.setLevel(type);
                            switch (gNewUserProfile.cCOLLAR.getLevel()){
                                case Leather:
                                    messageAuth=textAdd(messageAuth,"Put on a leather collar.");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER puts a leather collar around !WEARER's neck. Good boy, you're ready for walk."));
                                    break;
                                case Latex:
                                    messageAuth=textAdd(messageAuth,"Put on a latex collar.");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER puts a latex collar around !WEARER's neck. Time to go out to the night club."));
                                    break;
                                case Rubber:
                                    messageAuth=textAdd(messageAuth,"Put on a rubber collar.");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER puts a rubber collar around !WEARER's neck. Ready to serve and follow orders."));
                                    break;
                                case Chain:
                                    messageAuth=textAdd(messageAuth,"Put on a chain collar.");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER puts a chain collar around !WEARER's neck. Showing off the desire to be chained to something or somebody."));
                                    break;
                                case Iron:
                                    messageAuth=textAdd(messageAuth,"Put on an iron collar.");
                                    messagePublic=textAdd(messagePublic,stringReplacer("!USER puts a iron collar around !WEARER's neck. Like the slaves they used to wear as they been sold, owned and given orders."));
                                    break;
                            }
                        }

                    }


            }
            sendOrUpdatePrivateEmbed(sRTitle,messageAuth,llColorBlue3);
            if(lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,messagePublic,llColorBlue3))==null){
                lsMessageHelper.lsSendMessage(gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,messagePublic,llColorBlue3));
            }
        }


    private void userNotificationCollar(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nCollar;
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


        private void menuPiShock(){
            String fName="[menuPiShock]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();

                embed.setColor(llColorBlue1);String desc="";
                embed.setTitle("PiShock linking");
                embed.addField(strTestSubjectTitle,strTestSubjectDesc,false);
                embed.addField(strTestSubjectTitle,"Check out the command `"+llPrefixStr+"pishock` for PiShock options.",false);
                //embed.addField("So how will this work?","PiShock has its apis made public and theoretically the bot should able to shock a person using those apis.",false);
                //embed.addField("Why you need a test subject?","In order to ensure the bot uses PiShock apis correctly, a test subject is needed to who we test those commands.",false);
                //embed.addField("So what commands will be implemented?",":one: To link&unlick to/from PiShock collar.\n:two:Beep, sends the command for the PiShock collar to emit noise.\n:three:Customizable shock levels: warn, zap, punish.\n• Users will be responsible for setting up their shock intensity and duration.\n• Cap limit will exist to ensure users can't set above that.",false);
                //embed.addField("PiShock API","The bot would call this [apis](https://apidocs.pishock.com)",false);

                Message message=lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,embed);
                if(message==null)message=llSendMessageResponse_withReactionNotification(gUser,embed);
                if(!message.isFromGuild()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                }
                menuPiShockListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuPiShockListener(Message message){
            String fName="[menuPiShock]";
            logger.info(fName);
            try{
                if(message.isFromGuild()){

                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    isMenuLevel=true;
                                    if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                        logger.warn(fName+"trigger="+name);
                                        if(gUser.getIdLong()==gNewUserProfile.gUserProfile.getUser().getIdLong()){
                                            menuLevels(null);
                                        }else{
                                            menuLevels(gNewUserProfile.gUserProfile.getMember());
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },1, TimeUnit.MINUTES, () -> {
                                //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                                llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

}}
