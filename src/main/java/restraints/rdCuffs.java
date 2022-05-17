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
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
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
import restraints.models.enums.*;
import restraints.in.*;
import restraints.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdCuffs extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Cuffs",gCommand="cuffs";
    public rdCuffs(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Cuffs-DiscordRestraints";
        this.help = "rdCuffs";
        this.aliases = new String[]{"cuff","cuffs"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdCuffs(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdCuffs(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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

                setTitleStr(rdCuffs.this.sMainRTitle);setPrefixStr(rdCuffs.this.llPrefixStr);setCommandStr(rdCuffs.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdCuffs_commands");
                lsUsefullFunctions.setThreadName4Display("rdCuffs");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuLevels(gTarget,"main");
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuLevels(gTarget,"");
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
                        menuLevels(gTarget,"main");isInvalidCommand=false;
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
                        else {menuLevels(null,menuCommand);isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);

                        if(gItems[0].equalsIgnoreCase("help")){
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                rHelp("main");isInvalidCommand=false;
                            }else{
                                if(gItems[1].equalsIgnoreCase("arm")||gItems[1].equalsIgnoreCase("arms")){
                                    rHelp("arm");isInvalidCommand=false;
                                }
                                if(gItems[1].equalsIgnoreCase("leg")||gItems[1].equalsIgnoreCase("legs")){
                                    rHelp("leg");isInvalidCommand=false;
                                }
                                if(gItems[1].equalsIgnoreCase("post")||gItems[1].equalsIgnoreCase("slowdown")||gItems[1].equalsIgnoreCase("slowmode")){
                                    rHelp("post");isInvalidCommand=false;
                                }
                            }
                        }
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
                                menuLevels(gTarget,menuCommand);isInvalidCommand=false;
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
                                menuLevels(gTarget,"main");isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                menuLevels(null,"main");isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("arm")||gItems[0].equalsIgnoreCase("arms")||gItems[0].equalsIgnoreCase("cuffarm")||gItems[0].equalsIgnoreCase("cuffarms")||gItems[0].equalsIgnoreCase("cuffsarm")||gItems[0].equalsIgnoreCase("cuffsarms")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("arm");isInvalidCommand=false;
                                }else{
                                    //||items[1].equalsIgnoreCase(nArmBinding)
                                    if(gItems[1].equalsIgnoreCase(nUntie)||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())||gItems[1].equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                                        rAarmBinding(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())||gItems[0].equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                                rAarmBinding(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("leg")||gItems[0].equalsIgnoreCase("legs")||gItems[0].equalsIgnoreCase("cuffleg")||gItems[0].equalsIgnoreCase("cufflegs")||gItems[0].equalsIgnoreCase("cuffsleg")||gItems[0].equalsIgnoreCase("cuffslegs")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("leg");isInvalidCommand=false;
                                }else{
                                    if(gItems[1].equalsIgnoreCase(nUntie)||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())||gItems[1].equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())){
                                        rLegBinding(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())||gItems[0].equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())){
                                rLegBinding(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("delay")||gItems[0].equalsIgnoreCase("slowdown")||gItems[0].equalsIgnoreCase("post")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("slowdown");isInvalidCommand=false;
                                }else{
                                    //||items[1].equalsIgnoreCase(nArmBinding)
                                    if(gItems[1].equalsIgnoreCase("help")){
                                        rHelp("slowdown");isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(cDisable)||gItems[1].equalsIgnoreCase(cShortest)||gItems[1].equalsIgnoreCase(cShort)||gItems[1].equalsIgnoreCase(cNormal)||gItems[1].equalsIgnoreCase(cLong)||gItems[1].equalsIgnoreCase(cLongest)||gItems[1].equalsIgnoreCase(cDefault)){
                                        rPostLimiter(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(cExceptionGuildOff)||gItems[1].equalsIgnoreCase(cExceptionGuildOn)||gItems[1].equalsIgnoreCase(cExceptionUserOff)||gItems[1].equalsIgnoreCase(cExceptionUserOn)){
                                        rPostLimiter(gItems[1].toLowerCase());
                                        isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("exception")){
                                        if(gItems.length<4){
                                            logger.warn(fName+".invalid args length");
                                            rHelp("slowdown");isInvalidCommand=false;
                                        }else
                                            switch (gItems[2].toLowerCase()){
                                                case "guild":
                                                    switch (gItems[3].toLowerCase()){
                                                        case cEnable:
                                                            rPostLimiter(cExceptionGuildOn);isInvalidCommand=false;break;
                                                        case cDisable:
                                                            rPostLimiter(cExceptionGuildOff);isInvalidCommand=false;break;
                                                        default:
                                                            rHelp("slowdown");isInvalidCommand=false;
                                                    }
                                                    break;
                                                case "user":
                                                    switch (gItems[3].toLowerCase()){
                                                        case cEnable:
                                                            rPostLimiter(cExceptionUserOn);isInvalidCommand=false;break;
                                                        case cDisable:
                                                            rPostLimiter(cExceptionUserOff);isInvalidCommand=false;break;
                                                        default:
                                                            rHelp("slowdown");isInvalidCommand=false;
                                                    }
                                                    break;
                                                default:
                                                    rHelp("slowdown");isInvalidCommand=false;
                                            }
                                    }
                                    else{
                                        rHelp("slowdown");isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("channel")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("channel");isInvalidCommand=false;
                                }else{
                                    //||items[1].equalsIgnoreCase(nArmBinding)
                                    if(gItems[1].equalsIgnoreCase("help")){
                                        rHelp("channel");isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("!"+cDrag)||gItems[1].equalsIgnoreCase("!"+cPull)){
                                        rChannelLimiter(gItems[1].replaceAll("!","").toLowerCase());isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(cDisable)||gItems[1].equalsIgnoreCase(cFree)||gItems[1].equalsIgnoreCase(cEliberate)){
                                        rChannelLimiter(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(cEnable)||gItems[1].equalsIgnoreCase(cFix)||gItems[1].equalsIgnoreCase(cRestrict)){
                                        rChannelLimiter(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(cExceptionGuildOff)||gItems[1].equalsIgnoreCase(cExceptionGuildOn)||gItems[1].equalsIgnoreCase(cExceptionUserOff)||gItems[1].equalsIgnoreCase(cExceptionUserOn)){
                                        rChannelLimiter(gItems[1].toLowerCase());
                                        isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("exception")){
                                        if(gItems.length<4){
                                            logger.warn(fName+".invalid args length");
                                            rHelp("slowdown");isInvalidCommand=false;
                                        }else
                                            switch (gItems[2].toLowerCase()){
                                                case "guild":
                                                    switch (gItems[3].toLowerCase()){
                                                        case cEnable:
                                                            rChannelLimiter(cExceptionGuildOn);isInvalidCommand=false;break;
                                                        case cDisable:
                                                            rChannelLimiter(cExceptionGuildOff);isInvalidCommand=false;break;
                                                        default:
                                                            rHelp("slowdown");isInvalidCommand=false;
                                                    }
                                                    break;
                                                case "user":
                                                    switch (gItems[3].toLowerCase()){
                                                        case cEnable:
                                                            rChannelLimiter(cExceptionUserOn);isInvalidCommand=false;break;
                                                        case cDisable:
                                                            rChannelLimiter(cExceptionUserOff);isInvalidCommand=false;break;
                                                        default:
                                                            rHelp("slowdown");isInvalidCommand=false;
                                                    }
                                                    break;
                                                default:
                                                    rHelp("slowdown");isInvalidCommand=false;
                                            }
                                    }
                                    else{
                                        rHelp("channel");isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase(cFree)||gItems[0].equalsIgnoreCase(cRestrict)){
                                rChannelLimiter(gItems[0].toLowerCase());isInvalidCommand=false;
                            }else{
                                menuLevels(null,"main");isInvalidCommand=false;
                            }
                        }
                    }
                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gEvent);
                    if(isInvalidCommand){
                        sendOrUpdatePrivateEmbed(sRTitle,"You provided an incorrect command!", llColorRed);
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
            if(item1.equalsIgnoreCase("delay")||item1.equalsIgnoreCase("slowdown")){
                if(item2.equalsIgnoreCase(cDisable)||item2.equalsIgnoreCase(cShortest)||item2.equalsIgnoreCase(cShort)||item2.equalsIgnoreCase(cNormal)||item2.equalsIgnoreCase(cLong)||item2.equalsIgnoreCase(cLongest)||item2.equalsIgnoreCase(cDefault)){
                    rPostLimiter(target,item2);
                    return false;
                }
                if(item2.equalsIgnoreCase(cExceptionGuildOff)||item2.equalsIgnoreCase(cExceptionGuildOn)||item2.equalsIgnoreCase(cExceptionUserOff)||item2.equalsIgnoreCase(cExceptionUserOn)){
                    rPostLimiter(target,item2);
                    return false;
                }
            }
            if(item1.equalsIgnoreCase("channel")){
                if(item2.equalsIgnoreCase(cDisable)||item2.equalsIgnoreCase(cEnable)||item2.equalsIgnoreCase(cFix)||item2.equalsIgnoreCase(cFree)||item2.equalsIgnoreCase(cRestrict)||item2.equalsIgnoreCase(cEliberate)||item2.equalsIgnoreCase(cDrag)||item2.equalsIgnoreCase(cPull)){
                    rChannelLimiter(target,item2);
                    return false;
                }
                if(item2.equalsIgnoreCase(cExceptionGuildOff)||item2.equalsIgnoreCase(cExceptionGuildOn)||item2.equalsIgnoreCase(cExceptionUserOff)||item2.equalsIgnoreCase(cExceptionUserOn)){
                    rChannelLimiter(target,item2);
                    return false;
                }
            }
            if(item1.equalsIgnoreCase("arm")||item1.equalsIgnoreCase("arms")){
                if(item2.equalsIgnoreCase(nUntie)||item2.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())||item2.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                    rAarmBinding(target,item2);
                    return false;
                }
            }
            if(item1.equalsIgnoreCase("leg")||item1.equalsIgnoreCase("legs")){
                if(item2.equalsIgnoreCase(nUntie)||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())||item2.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())) {
                    rLegBinding(target, item2.toLowerCase());
                    return false;
                }
            }
            if(item1.equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())||item1.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())){
                rLegBinding(target,item1.toLowerCase());
                return false;
            }
            if(item1.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())||item1.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                rAarmBinding(target,item1.toLowerCase());
                return false;
            }
            return true;
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc;
        String quickSummonWithSpace2=llPrefixStr+"cuffs <@Pet> ";
        String newLine="\n  `",arm="arm ", leg="leg ", spacingDual="` , `" ,slowdown="slowdown ", channel="channel ", endLine="`", deliminator="|";
        EmbedBuilder embed=new EmbedBuilder();
        if(command.equalsIgnoreCase("slowdown")||command.equalsIgnoreCase("delay")||command.equalsIgnoreCase("post")||command.equalsIgnoreCase("slowmode")){
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Help");
            embed.addField(strSupportTitle,strSupport,false);
            embed.addField("What it does?","It simulates difficulty in writing by using a virtual slowmode.\nIn real situation a person would be slow in writting while restrained.",false);
            embed.addField("How it works?","It will count the time since your last post. If your new post is more early posted than it should be, it will delete it automatically.",false);
            embed.addField("What it needs?","Besides enabling it, you need to 'restrain' your hands/paws as well.",false);
            desc = newLine + quickSummonWithSpace2 + slowdown + cDisable + endLine + " disabled the feature";
            desc += newLine + quickSummonWithSpace2 + slowdown + cShortest + endLine + " set it to 5 seconds";
            desc += newLine + quickSummonWithSpace2 + slowdown + cShort + endLine + " set it to 15 seconds";
            desc += newLine + quickSummonWithSpace2 + slowdown + cNormal + endLine + " set it to 30 seconds";
            desc += newLine + quickSummonWithSpace2 + slowdown + cLong + endLine + " set it to 45 seconds";
            desc += newLine + quickSummonWithSpace2 + slowdown + cLongest + endLine + " set it to 60 seconds";
            desc += newLine + quickSummonWithSpace2 + slowdown + cDefault + endLine + " set it based on restraints";
            desc += "\n   straitjacket arm straps +60s";
            desc += "\n   straitjacket +15s";
            desc += "\n   mitts +15s";
            desc += "\n   cuffs " + CUFFSARMSLEVELS.Front.getName() + " +5s";
            desc += "\n   cuffs " + CUFFSARMSLEVELS.FrontBelt.getName() + " +15s";
            desc += "\n   cuffs " + CUFFSARMSLEVELS.Sides.getName() + " +30s";
            desc += "\n   cuffs " + CUFFSARMSLEVELS.SidesTight.getName() + " +45s";
            desc += "\n   the rest of arm cuffs +60s";
            desc += "\n   gag +5s";
            desc += "\n   blindfold +15s";
            desc += "\n   hood " + HOODLEVELS.Drone.getName() + " +50s";
            desc += "\n   hood " + HOODLEVELS.Bondage.getName() + " +35s";
            desc += "\n   hood " + HOODLEVELS.Puppy.getName() + "|" + HOODLEVELS.Kitty.getName() + "|" + HOODLEVELS.Cow.getName() + "|" + HOODLEVELS.Pony.getName() + " +20s";
            desc += "\n   rest of hood +5s";
            embed.addField("Levels", desc, false);
        }
        else if(command.equalsIgnoreCase("channel")) {
            embed.setColor(llColorOrange);
            embed.setTitle(sRTitle + " Help");
            embed.addField(strSupportTitle, strSupport, false);
            embed.addField("What it does?", "It simulates unable to move between rooms (channels) like when in real life a person can't move while their legs care cuffed.", false);
            embed.addField("How it works?", "It will auto delete post posted outside the channel where this was enabled or legs cuffed.", false);
            embed.addField("What it needs?", "Besides enabling it, you need to 'restrain' your legs above faux level.", false);
            desc=newLine+quickSummonWithSpace2+channel+cDisable+"|"+cFree+"|"+cEliberate+endLine+" disabled the feature";
            desc+=newLine+quickSummonWithSpace2+channel+cEnable+"|"+cFix+"|"+cRestrict+endLine+" enable the feature";
            desc+=newLine+quickSummonWithSpace2+channel+cDrag+"|"+cPull+endLine+" to drag them to a different chanel. Can't do on themselves.";
            embed.addField("Options",desc,false);
        }
        else if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Help");
            embed.addField(strSupportTitle,strSupport,false);
            desc=newLine+quickSummonWithSpace2+"arm untie` ";
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.Armbinder.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.Behind.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.BehindBelt.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.BehindTBelt.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.BehindTight.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.Elbows.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.Front.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.FrontBelt.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.ReversePrayer.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.Sides.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+arm+CUFFSARMSLEVELS.SidesTight.getName()+endLine;
            embed.addField("Levels",desc,false);
        }
        else if(command.equalsIgnoreCase("leg")||command.equalsIgnoreCase("legs")){
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Help");
            embed.addField(strSupportTitle,strSupport,false);
            desc=newLine+quickSummonWithSpace2+"leg untie` unties the leg cuffs.";
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.FauxTaut.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.Taut.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.Stand.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.Sit.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.LayBack.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.LayBelly.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.Spreaderbar.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.HogBack.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.HogSideways.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.HogTie.getName()+endLine;
            embed.addField("Levels",desc,false);
        }
        else {
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Help");
            embed.addField(strSupportTitle,strSupport,false);
            embed.addField("OwO",strMessage1,false);
            desc=strMessage2;
            desc+=newLine+quickSummonWithSpace2+"arm untie` ";
            desc+=deliminator+CUFFSARMSLEVELS.Armbinder.getName();
            desc+=deliminator+CUFFSARMSLEVELS.Behind.getName();
            desc+=deliminator+CUFFSARMSLEVELS.BehindBelt.getName();
            desc+=deliminator+CUFFSARMSLEVELS.BehindTBelt.getName();
            desc+=deliminator+CUFFSARMSLEVELS.BehindTight.getName();
            desc+=deliminator+CUFFSARMSLEVELS.Elbows.getName();
            desc+=deliminator+CUFFSARMSLEVELS.Front.getName();
            desc+=deliminator+CUFFSARMSLEVELS.FrontBelt.getName();
            desc+=deliminator+CUFFSARMSLEVELS.ReversePrayer.getName();
            desc+=deliminator+CUFFSARMSLEVELS.Sides.getName();
            desc+=deliminator+CUFFSARMSLEVELS.SidesTight.getName()+endLine;
            embed.addField("Arm",desc,false);
            desc=strMessage3;
            desc+=newLine+quickSummonWithSpace2+"leg untie` unties the leg cuffs.";
            desc+=newLine+quickSummonWithSpace2+leg+CUFFSLEGSLEVELS.FauxTaut.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.Taut.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.Stand.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.Sit.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.LayBack.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.LayBelly.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.Spreaderbar.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.HogBack.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.HogSideways.getName();
            desc+=deliminator+CUFFSLEGSLEVELS.HogTie.getName()+endLine;
            embed.addField("Leg",desc,false);
            desc=newLine+llPrefixStr+"cuffs "+"slowdown disable|shortest|short|normal|long|longest|default"+endLine;
            desc+="\nSimulates difficulty writing posts, like when hands are restrained, by deleting original message if they posted to quickly. The more complex the restrain, the more they need to wait between posting.";
            embed.addField("Post slowdown",desc,false);
            desc=newLine+quickSummonWithSpace2+channel+cFree+"|"+cRestrict+endLine+" disabled|enable the feature";
            desc+=newLine+quickSummonWithSpace2+channel+cDrag+endLine+" to drag them to a different chanel. Can't do on themselves.";
            desc+="\nSimulates being restrained to a channel. Imagine each channel is a room, if the subject has their legs tied, they not able to leave the current room to enter a new one.";
            embed.addField("Channel restriction",desc,false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
        }
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    private void rAarmBinding(String command) {
        String fName = "[rArmBinding]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cARMCUFFS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,strMessageCantManipulatePermalock, llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantSet2Pet), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantSet2Public), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
            logger.info(fName + ".can't restrain via cuffs while the jacket is on");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantJacket), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
            logger.info(fName + ".can't restrain via cuffs while the mittens are on");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmsCantMitts1), llColorRed);
            userNotificationArmCuffs(actionStruggle, stringReplacer(iCuffs.strArmsCantMitts2));
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(command)) {
            logger.info(fName + ".same level>ignore");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsIsSet), llColorRed);return;
        }else
        if(command.equalsIgnoreCase(nUntie)){
            if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't untie while locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmsCantDueLocked1), llColorRed);
                    userNotificationArmCuffs(actionStruggle, stringReplacer(iCuffs.strArmsCantDueLocked2));
                    return;
                }else{
                    gNewUserProfile.cARMCUFFS.setLevel( nNone); gNewUserProfile.cARMCUFFS.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmsUntie1), llColorRed);
                    userNotificationArmCuffs(actionUnSecured , stringReplacer(iCuffs.strArmsUntie2));
                }
            }else{
                logger.info(fName + ".wrist not restrained");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsNotRestrained), llColorPurple1);return;
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
            logger.info(fName + ".locked and access exposed");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegNoKey1), llColorRed);
            userNotificationArmCuffs(actionStruggle, stringReplacer(iCuffs.strArmsCantDueKey2));return;
        }else{
            int actionSelected=actionChangeLevel;
            if(!gNewUserProfile.cARMCUFFS.isOn()){actionSelected=actionSecured;}
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmArmbinder1), llColorBlue1);
                userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strArmArmbinder2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmBehind1), llColorBlue1);
                userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strArmBehind2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmBehindT1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmBehindT2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmBehindBelt1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmBehindBelt2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmBehindBeltT1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmBehindBeltT2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmElbow1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmElbow2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmFront1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmFront2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmFrontBelt1), llColorBlue1);
                userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strArmFrontBelt2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmPrayer1), llColorBlue1);
                userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strArmPrayer2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmSidewaysBelt1), llColorBlue1);
                userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strArmSidewaysBelt2));
            }else
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())){
                gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strArmSidewaysBeltT1), llColorBlue1);
                userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strArmSidewaysBeltT2));
            }
        }
        saveProfile();
    }
    private void rLegBinding(String command) {
        String fName = "[rLegBinding]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cLEGCUFFS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,strMessageCantManipulatePermalock, llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs. strLegCantDueLocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs. strLegCantDue2Pet), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs. strLegCantDue2Public), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
            logger.info(fName + ".can't restrain via cuffs while the jacket is on");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegCantDueJacket1), llColorRed);
            userNotificationLegCuffs(actionStruggle , stringReplacer(iCuffs. strLegCantDueJacket2));
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
            logger.info(fName + ".can't restrain via cuffs while the jacket is on");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegCantDueArms1), llColorRed);
            userNotificationLegCuffs(actionStruggle , stringReplacer(iCuffs. strLegCantDueArms2));
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
            logger.info(fName + ".can't restrain via cuffs while the mittens are on");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegMitts1), llColorRed);
            userNotificationLegCuffs(actionStruggle , stringReplacer(iCuffs. strLegMitts2));
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(command.toLowerCase())) {
            logger.info(fName + ".same level>ignore");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsIsSet), llColorRed);return;
        }else
        if(command.equalsIgnoreCase(nUntie)){
            if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't untie while locked");
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegCantLocked1), llColorRed);
                    userNotificationLegCuffs(actionStruggle , stringReplacer(iCuffs. strLegCantLocked2));
                    return;
                }else{
                     gNewUserProfile.cLEGCUFFS.setLevel( nNone); gNewUserProfile.cLEGCUFFS.setOn( false);
                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegUntie1), llColorRed);
                    userNotificationLegCuffs(actionUnSecured ,stringReplacer(iCuffs. strLegUntie2));
                }
            }else{
                logger.info(fName + ".wrist not restrained");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs. strLegNotRestrained), llColorPurple1);return;
            }
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
            logger.info(fName + ".locked and access exposed");
            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegNoKey1), llColorRed);
            userNotificationLegCuffs(actionStruggle , stringReplacer(iCuffs. strLegNoKey2));return;
        }else{
            int actionSelected=actionChangeLevel;
            if(!gNewUserProfile.cLEGCUFFS.isOn()){actionSelected=actionSecured;}
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegFauxTaut1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs. strLegFauxTaut2));
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegTaut1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs. strLegTaut2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegStand1), llColorBlue1);
                userNotificationLegCuffs(actionSelected , stringReplacer(iCuffs. strLegStand2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegLayBack1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs. strLegLayBack2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegLayBelly1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs. strLegLayBelly2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegSit1), llColorBlue1);
                userNotificationLegCuffs(actionSelected , stringReplacer(iCuffs. strLegSit2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs. strLegSpreadbar1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs. strLegSpreadbar2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.  strLegHogback1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs.  strLegHogback2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.  strLegHogside1), llColorBlue1);
                userNotificationLegCuffs(actionSelected ,stringReplacer(iCuffs.  strLegHogside2));
                rChannelSet();
            }else
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())){
                 gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iCuffs.strLegHogtie1), llColorBlue1);
                userNotificationLegCuffs(actionSelected , stringReplacer(iCuffs.strLegHogtie2));
                rChannelSet();
            }
        }
        saveProfile();
    }
        final String cDisable="disable",cShortest="shortest",cShort="short",cNormal="normal",cLong="long",cLongest="longest",cDefault="default";
        String strItsGoung2BeDifficultWritting="It's going to be difficult typing once your hands are restrained.";
        private void rPostLimiter(String command) {
            String fName = "[rPostLimiter]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantSet2Pet), llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(cDisable)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( false);
               gNewUserProfile.cARMCUFFS.setPostDelay( 0);
                sendOrUpdatePrivateEmbed(sRTitle,"You disable post slowdown.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cShortest)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 1);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to 5 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cShort)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 2);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to 15 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cNormal)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 3);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to 30 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cLong)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 4);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to 45 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cLongest)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 5);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to 60 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cDefault)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 9);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to default.\nMore restrained means more time between posts.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cDefault)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 9);
                sendOrUpdatePrivateEmbed(sRTitle,strItsGoung2BeDifficultWritting+"\nPost slowdown set to default.\nMore restrained means more time between posts.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag guild exception will be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag guild exception won't be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag user exception will be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag user exception won't be used for post slowdown exception.", llColorBlue1);
            }
            saveProfile();
        }
        final String cExceptionGuildOn="eg1", cExceptionGuildOff ="eg0",cExceptionUserOn="eu1", cExceptionUserOff ="ef0";
        final String cEliberate="eliberate",cFree="free",cRestrict="restrict",cFix="fix",cEnable="enable", cDrag="drag",cPull="pull";
        private void rChannelLimiter(String command) {
            String fName = "[rChannelLimiter]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs. strLegCantDueLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your leg cuffs due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(cDisable)||command.equalsIgnoreCase(cFree)||command.equalsIgnoreCase(cEliberate)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainEnabled( false);
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainId( 0);
                sendOrUpdatePrivateEmbed(sRTitle,"You are free to move between channels, regardless if your legs are cuffed.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cEnable)||command.equalsIgnoreCase(cFix)||command.equalsIgnoreCase(cRestrict)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainEnabled( true);
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                sendOrUpdatePrivateEmbed(sRTitle,"You are no longer free to move between channels once your legs are cuffed.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cDrag)||command.equalsIgnoreCase(cPull)){
                if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag as channel restrain is not enabled.", llColorRed);
                    return;
                }
                else if(!gNewUserProfile.cLEGCUFFS.isOn()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag as legs are not cuffed.", llColorRed);
                    return;
                }
                else if(gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag as legs cuffed just for show.", llColorRed);
                    return;
                }
                else {
                     gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                    sendOrUpdatePrivateEmbed(sRTitle,"You drag yourself by the chains to "+gTextChannel.getAsMention()+".", llColorBlue1);
                }

            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainException2UseGuildGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag guild exception will be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainException2UseGuildGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag guild exception won't be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainException2UseUserGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag user exception will be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainException2UseUserGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"Gag user exception won't be used for channel limiter exception.", llColorBlue1);
            }
            saveProfile();
        }
    ///////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private void rAarmBinding(Member mtarget,String command) {
        String fName = "[rArmBinding]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
            logger.info(fName + ".isSameConfinement>return");
            sendOrUpdatePrivateEmbed(sRTitle, iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
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
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cARMCUFFS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantPermalocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantLocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantDueAccess), llColorRed);return;
        }else
        if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
            logger.info(fName + ".can't restrain via cuffs while the jacket is on");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantDueJacket), llColorRed);return;
        }else
        if(!gIsOverride&& gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(command.toLowerCase())){
            logger.info(fName + ".same level>ignore"); sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantDUeAlreadySet), llColorPurple1);return;
        }else
        if(command.equalsIgnoreCase(nUntie)){
            if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't untie while locked");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantLocked), llColorRed);return;
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can undo your arms cuffs.");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can undo !TARGET 's arms cuffs.");
                        gAskHandlingHelper.doAsk(() -> {rAarmBindingSave4Target(mtarget,command);});
                        return;
                    }
                    rAarmBindingSave4Target(mtarget,command);
                }
            }else{
                logger.info(fName + ".wrist not restrained");
                sendOrUpdatePrivateEmbed(sRTitle,"The arms are not restrained.", llColorPurple1);return;
            }
        }else{
            int actionSelected=actionChangeLevel;
            if(!gNewUserProfile.cARMCUFFS.isOn()){actionSelected=actionSecured;}
            if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())||
                    command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())||
                    command.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())||
                    command.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())||command.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can secure your arms cuffs.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can secure !TARGET 's arms cuffs.");
                    gAskHandlingHelper.doAsk(() -> {rAarmBindingSave4Target(mtarget,command);});
                    return;
                }
                rAarmBindingSave4Target(mtarget,command);
            }
        }
    }
    private void rLegBinding(Member mtarget,String command) {
        String fName = "[rLegBinding]";
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
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&& gNewUserProfile.cLEGCUFFS.isOn()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantPermalocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantLocked), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDueAccess), llColorRed);return;
        }else
        if(!gIsOverride&& gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(command.toLowerCase())){
            logger.info(fName + ".same level>ignore"); sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmCantDUeAlreadySet), llColorPurple1);return;
        }else
        if(command.equalsIgnoreCase(nUntie)){
            if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".can't untie while locked");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDuePadlock), llColorRed);return;
                }else{
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can undo your legs cuffs.");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can undo !TARGET 's legs cuffs.");
                        gAskHandlingHelper.doAsk(() -> {rLegBindingSave4Target(mtarget,command);});
                        return;
                    }
                    rLegBindingSave4Target(mtarget,command);
                }
            }else{
                logger.info(fName + ".wrist not restrained");
                sendOrUpdatePrivateEmbed(sRTitle,"The legs are not restrained.", llColorPurple1);return;
            }
        }else{
            int actionSelected=actionChangeLevel;
            if(!gNewUserProfile.cLEGCUFFS.isOn()){actionSelected=actionSecured;}
            if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())||
                    command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())||
                    command.equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())||command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can secure your legs cuffs.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can secure !TARGET 's legs cuffs.");
                    gAskHandlingHelper.doAsk(() -> {rLegBindingSave4Target(mtarget,command);});
                    return;
                }
                rLegBindingSave4Target(mtarget,command);
            }
        }

    }
        private void rPostLimiter(Member mtarget,String command) {
            String fName = "[rPostLimiter]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command=" + command);
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
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDueAccess), llColorRed);return;
            }
            else if(command.equalsIgnoreCase(cShortest)||command.equalsIgnoreCase(cShort)||command.equalsIgnoreCase(cNormal)||command.equalsIgnoreCase(cDisable)||
                    command.equalsIgnoreCase(cLong)||command.equalsIgnoreCase(cLongest)||command.equalsIgnoreCase(cDefault)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your posts limiter.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET 's posts limiter.");
                    gAskHandlingHelper.doAsk(() -> {rPostLimiterSave4Target(mtarget,command);});
                    return;
                }
                rPostLimiterSave4Target(mtarget,command);
                saveProfile();
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)||command.equalsIgnoreCase(cExceptionGuildOn)||command.equalsIgnoreCase(cExceptionUserOff)||command.equalsIgnoreCase(cExceptionUserOn)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your posts limiter.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET 's posts limiter.");
                    gAskHandlingHelper.doAsk(() -> {rPostLimiterSave4Target(mtarget,command);});
                    return;
                }
                rPostLimiterSave4Target(mtarget,command);
                saveProfile();
            }
        }
        private void rChannelLimiter(Member mtarget,String command) {
            String fName = "[rChannelLimiter]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command=" + command);
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
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDueAccess), llColorRed);return;
            }else
            if(command.equalsIgnoreCase(cDisable)||command.equalsIgnoreCase(cFree)||command.equalsIgnoreCase(cEliberate)||command.equalsIgnoreCase(cEnable)||command.equalsIgnoreCase(cFix)||command.equalsIgnoreCase(cRestrict)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your channels limiter.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET 's channels limiter.");
                    gAskHandlingHelper.doAsk(() -> {rChannelLimiterSave4Target(mtarget,command);});
                    return;
                }
                rChannelLimiterSave4Target(mtarget,command);
                saveProfile();
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)||command.equalsIgnoreCase(cExceptionGuildOn)||command.equalsIgnoreCase(cExceptionUserOff)||command.equalsIgnoreCase(cExceptionUserOn)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your channels limiter.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET 's channels limiter.");
                    gAskHandlingHelper.doAsk(() -> {rChannelLimiterSave4Target(mtarget,command);});
                    return;
                }
                rChannelLimiterSave4Target(mtarget,command);
                saveProfile();
            }
            else if(command.equalsIgnoreCase(cDrag)||command.equalsIgnoreCase(cPull)){
                if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as channel restrain is not enabled.", llColorRed);
                    return;
                }
                else if(!gNewUserProfile.cLEGCUFFS.isOn()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as their legs are not cuffed.", llColorRed);
                    return;
                }
                else if(gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as their legs cuffed just for show.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can pull you by your leg cuffs to room "+gTextChannel.getAsMention()+".");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can pull !TARGET by their leg cuffs to room "+gTextChannel.getAsMention()+".");
                    gAskHandlingHelper.doAsk(() -> {rChannelLimiterSave4Target(mtarget,command);});
                    return;
                }
                rChannelLimiterSave4Target(mtarget,command);
                saveProfile();
            }
            saveProfile();
        }

        private void rAarmBindingSave4Target(Member mtarget,String command) {
            String fName = "[rArmBindingSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(nUntie)){
                gNewUserProfile.cARMCUFFS.setLevel( nNone); gNewUserProfile.cARMCUFFS.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmUnties1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmUnties2), llColorBlue1);
                userNotificationArmCuffs(actionUnSecured, stringReplacer(iCuffs.strTargetArmUnties3));
            }else{
                int actionSelected=actionChangeLevel;
                if(!gNewUserProfile.cARMCUFFS.isOn()){actionSelected=actionSecured;}
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmArmbinder1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmArmbinder2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmArmbinder3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmBehind1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmBehind2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strTargetArmBehind3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmBehindT1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmBehindT2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmBehindT3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmBehindBelt1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmBehindBelt2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strTargetArmBehindBelt3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmBehindBeltT1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmBehindBeltT2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmBehindBeltT3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmElbow1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmElbow2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmElbow3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmFront1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmFront2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strTargetArmFront3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmBelt1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmBelt2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strTargetArmBelt3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmPray1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmPray2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmPray3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmSideways1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmSideways2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected,stringReplacer(iCuffs.strTargetArmSideways3));
                }else
                if(command.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())){
                    gNewUserProfile.cARMCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cARMCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetArmSidewaysT1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetArmSidewaysT2), llColorBlue1);
                    userNotificationArmCuffs(actionSelected, stringReplacer(iCuffs.strTargetArmSidewaysT3));
                }
            }
            saveProfile();
        }
        private void rLegBindingSave4Target(Member mtarget,String command) {
            String fName = "[rLegBindingSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(nUntie)){
                 gNewUserProfile.cLEGCUFFS.setLevel( nNone); gNewUserProfile.cLEGCUFFS.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegUntie1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegUntie2), llColorBlue1);
                userNotificationLegCuffs(actionUnSecured,stringReplacer(iCuffs.strTargetLegUntie3));
            }else{
                int actionSelected=actionChangeLevel;
                if(!gNewUserProfile.cLEGCUFFS.isOn()){actionSelected=actionSecured;}
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegFauxTaut1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegFauxTaut2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegFauxTaut3));
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegTaut1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegTaut2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected,stringReplacer(iCuffs.strTargetLegTaut3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegStand1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegStand2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegStand3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegLayBack1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegLayBack2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegLayBack3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegLayBelly1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegLayBelly2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegLayBelly3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegSit1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegSit2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegSit3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegSpreadbar1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegSpreadbar2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegSpreadbar3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegHogback1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegHogback2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected, stringReplacer(iCuffs.strTargetLegHogback3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegHogside1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegHogside2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected,stringReplacer(iCuffs.strTargetLegHogside3));
                    rChannelSet();
                }else
                if(command.equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName())){
                     gNewUserProfile.cLEGCUFFS.setLevel( command.toLowerCase()); gNewUserProfile.cLEGCUFFS.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegHogtie1), llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iCuffs.strTargetLegHogtie2), llColorBlue1);
                    userNotificationLegCuffs(actionSelected,stringReplacer(iCuffs.strTargetLegHogtie3));
                    rChannelSet();
                }
            }
            saveProfile();
        }
        private void rPostLimiterSave4Target(Member mtarget,String command) {
            String fName = "[rPostLimiterSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDueAccess), llColorRed);return;
            }else
            if(command.equalsIgnoreCase(cDisable)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( false);
               gNewUserProfile.cARMCUFFS.setPostDelay( 0);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" disabled post slowdown for "+target.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" disabled your post slowdown.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cShortest)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 2);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to 5 seconds.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to 5 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cShort)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 2);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to 15 seconds.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to 15 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cNormal)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 3);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to 30 seconds.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to 30 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cLong)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 4);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to 45 seconds.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to 45 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cLongest)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 5);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to 60 seconds.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to 60 seconds.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cDefault)){
               gNewUserProfile.cARMCUFFS.setPostRestrictEnabled( true);
               gNewUserProfile.cARMCUFFS.setPostDelay( 9);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" set post slowdown for "+target.getAsMention()+" to depend on restraints.\nMore restrained means more time between posts.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set post slowdown for you to depend on restraints.\nMore restrained means more time between posts.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag guild exception will be used for post slowdown exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag guild exception will be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag guild exception won't be used for post slowdown exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag guild exception won't be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag user exception will be used for post slowdown exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag user exception will be used for post slowdown exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag user exception won't be used for post slowdown exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag user exception won't be used for post slowdown exception.", llColorBlue1);
            }
            saveProfile();
        }
        private void rChannelLimiterSave4Target(Member mtarget,String command) {
            String fName = "[rChannelLimiter]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strArmsCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantLocked), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iCuffs.strTargetLegCantDueAccess), llColorRed);return;
            }else
            if(command.equalsIgnoreCase(cDisable)||command.equalsIgnoreCase(cFree)||command.equalsIgnoreCase(cEliberate)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainEnabled( false);
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainId( 0);
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" allows "+target.getAsMention()+" to move freely between channels, regardless if "+mtarget.getEffectiveName()+" legs are cuffed.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" allows you to move freely between channels, regardless if your legs are cuffed.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cEnable)||command.equalsIgnoreCase(cFix)||command.equalsIgnoreCase(cRestrict)){
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainEnabled( true);
                 gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" denies "+target.getAsMention()+" to move freely between channels once "+mtarget.getEffectiveName()+" legs are cuffed.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" denies you to move freely between channels once your legs are cuffed.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cDrag)||command.equalsIgnoreCase(cPull)){
                if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as channel restrain is not enabled.", llColorRed);
                    return;
                }
                else if(!gNewUserProfile.cLEGCUFFS.isOn()&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as their legs are not cuffed.", llColorRed);
                    return;
                }
                else if(gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())&&!gIsOverride){
                    logger.info(fName + ".can't as legs not cuffed");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't drag "+mtarget.getAsMention()+",as their legs cuffed just for show.", llColorRed);
                    return;
                }
                else {
                     gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                    llSendQuickEmbedMessage(gTextChannel,sMainRTitle,gUser.getAsMention()+" drags "+target.getAsMention()+" by the chains to "+gTextChannel.getAsMention()+".", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" drags you by the chains to "+gTextChannel.getAsMention()+".", llColorBlue1);
                }
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag guild exception will be used for channel limiter exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag guild exception will be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionGuildOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseGuildGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag guild exception won't be used for channel limiter exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag guild exception won't be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOn)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag user exception will be used for channel limiter exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag user exception will be used for channel limiter exception.", llColorBlue1);
            }
            else if(command.equalsIgnoreCase(cExceptionUserOff)){
                gNewUserProfile.cARMCUFFS.setPostRestrictionException2UseUserGag( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You set for "+target.getAsMention()+" that gag user exception won't be used for channel limiter exception.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" set it for you that gag user exception won't be used for channel limiter exception.", llColorBlue1);
            }
            saveProfile();
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
            menuLevels(gTarget,"");
        }

        boolean isMenuLevel=false;String menuCommand="";Message menuMessage;
        private void menuLevels(Member mtarget,String command){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+command);menuCommand=command;
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
                    menuLevelsSomebody(menuCommand);
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer(menuCommand);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        String gCommandFileMainPath =gFileMainPath+"cuffs/menuMain.json";
        private void menuLevelsWearer(String command){
            String fName="[menuLevelsWearer]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+command);menuCommand=command;
                EmbedBuilder embed=new EmbedBuilder();

                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(!gNewUserProfile.cARMCUFFS.getLevelAsString().isBlank()&&!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                    embed.addField("Arm binding", gNewUserProfile.cARMCUFFS.getLevelAsString(),true);
                } else{
                    embed.addField("Arm binding","(not wearing)",true);
                }

                boolean b= gNewUserProfile.cARMCUFFS.isPostRestrictEnabled();
                long l= gNewUserProfile.cARMCUFFS.getPostDelay();
                if(b&&l==1){
                    embed.addField("Post slowdown","5 seconds",true);
                }
                else if(b&&l==2){
                    embed.addField("Post slowdown","15 seconds",true);
                }
                else if(b&&l==3){
                    embed.addField("Post slowdown","30 seconds",true);
                }
                else if(b&&l==4){
                    embed.addField("Post slowdown","45 seconds",true);
                }
                else if(b&&l==5){
                    embed.addField("Post slowdown","60 seconds",true);
                }
                else if(b&&l==9){
                    embed.addField("Post slowdown","restrain depending",true);
                }
                else{
                    embed.addField("Post slowdown","disabled",true);
                }

                if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().isBlank()&&!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                    embed.addField("Leg binding", gNewUserProfile.cLEGCUFFS.getLevelAsString(),true);
                } else{
                    embed.addField("Leg binding","(not wearing)",true);
                }

                if(gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()){
                    embed.addField("Channel Restriction","enabled\nbound to:"+lsChannelHelper.lsGetTextChannelMention(gGuild, gNewUserProfile.cLEGCUFFS.getChannelRestrainId()),true);
                }else{
                    embed.addField("Channel Restriction","disabled",true);
                }

                switch (command){
                    case "arm": case "arms":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+"untie arms";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+CUFFSARMSLEVELS.Armbinder.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CUFFSARMSLEVELS.Behind.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CUFFSARMSLEVELS.BehindBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CUFFSARMSLEVELS.BehindTBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+CUFFSARMSLEVELS.BehindTight.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+CUFFSARMSLEVELS.Elbows.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+CUFFSARMSLEVELS.Front.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+CUFFSARMSLEVELS.FrontBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+CUFFSARMSLEVELS.ReversePrayer.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+CUFFSARMSLEVELS.Sides.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+CUFFSARMSLEVELS.SidesTight.getName();
                        embed.addField(" ", "Please select a arm cuffs option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                        embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                        if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()||gNewUserProfile.cENCASE.isEncased()||gNewUserProfile.cMITTS.isOn()||gNewUserProfile.cSUIT.isBDSMSuitOn()){
                            desc="";
                            if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                                desc+=iRdStr.strRestraintJacketArms+" *(cant be cuffed if jacketed)";
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
                            desc2+=" How do you think you can manage your cuffs?";
                            embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);
                        }
                        break;
                    case "leg": case "legs":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+"untie legs";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+CUFFSLEGSLEVELS.FauxTaut.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CUFFSLEGSLEVELS.Taut.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CUFFSLEGSLEVELS.Stand.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CUFFSLEGSLEVELS.Sit.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+CUFFSLEGSLEVELS.LayBack.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+CUFFSLEGSLEVELS.LayBelly.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+CUFFSLEGSLEVELS.Spreaderbar.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+CUFFSLEGSLEVELS.HogBack.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+CUFFSLEGSLEVELS.HogSideways.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+CUFFSLEGSLEVELS.HogTie.getName();
                        embed.addField(" ", "Please select a leg cuffs option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                        embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of restricted postings, they need to be enabled from the cuffs restrictions menu.", false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
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
                            if(gNewUserProfile.cMITTS.isOn()){
                                desc+=iRdStr.strRestraintMitts;
                            }
                            if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                                if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit){
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
                            desc2+=" How do you think you can manage your cuffs?";
                            embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);
                        }
                        break;
                    case "restrictions": case"restriction":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" "+cDisable;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" "+cShortest+" (5 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" "+cShort+" (15 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle)+" "+cNormal+" (30 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle)+" "+cLong+" (45 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" "+cLongest+" (60 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle)+" "+cDefault+" (restrain depending)";
                        desc+="\nSimulates difficulty writing posts, like when hands are restrained, by deleting original message if they posted to quickly. The more complex the restrain, the more they need to wait between posting.";
                        desc+="\nIn order to work they need to have their hands restrained: cuffs, mitts, straitjacket";
                        embed.addField(" ", "Please select post slowdown option :"+desc, false);
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" "+cFree+", disable restrictions";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" "+cRestrict+", enable restriction";
                        if(gNewUserProfile.gUserProfile.getUser()!=gUser)desc+="\n"+gGlobal.emojis.getEmoji("chains")+" "+cDrag+", allow them to post in the channel the command was called";
                        desc+="\nSimulates being restrained to a channel. Imagine each channel is a room, if the subject has their legs tied, they not able to leave the current room to enter a new one.";
                        desc+="\nIn order to work they need to have their legs restrained.";
                        embed.addField(" ", "Please select channel restriction option :"+desc, false);
                        desc="Set to use gag exception for post slowdown(slowmode) exception. It uses the exception for the gag option.\nOptions:";

                        if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" don't use user gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" use user gag exception";
                        }


                        if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen)+" don't use server gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen)+" use server gag exception";
                        }

                        embed.addField("Post Slowdown exception", desc, false);
                        desc="Set to use gag exception for channel restriction exception. It uses the exception for the gag option.\nOptions:";
                        if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" don't use user gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" use user gag exception";
                        }

                        if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound)+" don't use server gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound)+" use server gag exception";
                        }

                        embed.addField("Channel restriction exception", desc, false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                        break;
                    default:
                        desc="\narms: armbinder, behind...sides, sides tight";
                        desc+="\nlegs: taut, stand...hog sideways,hog tie";
                        embed.addField("Arms&leg cuffs", "Select an arms/legs cuffs level:"+desc, false);
                        desc="Simulates difficulty writing posts, like when hands are restrained, by deleting original message if they posted to quickly. Depending the option, the more complex the restrain, the more they need to wait between posting, from 5 seconds to minutes.";
                        embed.addField("Arms Slowdown", desc, false);
                        desc="Simulates being restrained to a channel. Imagine each channel is a room, if the subject has their legs tied, they not able to leave the current room to enter a new one.";
                        embed.addField("Legs Restrictions", desc, false);
                        desc=""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" arms level";
                        desc+=", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" legs level";
                        desc+=", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" restriction options";
                        embed.addField("Reaction options", "Please select a sub-command:"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`"+"\nOptional if the select menu does not work.", false);
                        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                    break;
                }
                

                Message message=null;
                if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    if(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&(gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cMITTS.isOn()&&!gNewUserProfile.cSUIT.isBDSMSuitOn()))){
                        if(gNewUserProfile.cARMCUFFS.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                    }
                }
                else if(command.equalsIgnoreCase("leg")||command.equalsIgnoreCase("legs")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    if(gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()&&!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cMITTS.isOn()&&!gNewUserProfile.cSUIT.isBDSMSuitOn())){
                        if(gNewUserProfile.cLEGCUFFS.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                        if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                    }
                }
                else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));

                    if(gNewUserProfile.gUserProfile.getUser()!=gUser) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("chains"));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound));
                }
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component.pre="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent.SelectMenu selectContainerArms=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                    lcMessageBuildComponent.SelectMenu selectContainerLegs=messageComponentManager.messageBuildComponents.getSelectMenuAt(1);
                    lcMessageBuildComponent.SelectMenu selectContainerSlowdown=messageComponentManager.messageBuildComponents.getSelectMenuAt(2);
                    lcMessageBuildComponent.SelectMenu selectContainerRestriction=messageComponentManager.messageBuildComponents.getSelectMenuAt(3);
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(4,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    //ARMS
                    if(!gNewUserProfile.cARMCUFFS.isOn())selectContainerArms.setDefaultOptionAt(0);
                    else{
                        switch (gNewUserProfile.cARMCUFFS.getLevel()){
                            case Armbinder:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Behind:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case BehindBelt:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case BehindTBelt:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case BehindTight:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Elbows:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case Front:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case FrontBelt:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case ReversePrayer:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case Sides:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                            case SidesTight:selectContainerArms.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);break;
                        }
                        if(!gNewUserProfile.allow2BypassNewRestrictions()&&(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()||gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()||gNewUserProfile.cENCASE.isEncased()||gNewUserProfile.cMITTS.isOn()||gNewUserProfile.cSUIT.isBDSMSuitOn())){
                            selectContainerArms.setDisabled();
                        }
                    }

                  //LEGS
                    if(!gNewUserProfile.cLEGCUFFS.isOn())selectContainerLegs.setDefaultOptionAt(0);
                    else{
                        switch (gNewUserProfile.cLEGCUFFS.getLevel()){
                            case FauxTaut:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Taut:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Stand:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Sit:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case LayBack:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case LayBelly:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case Spreaderbar:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case HogBack:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case HogSideways:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case HogTie:selectContainerLegs.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                        }
                        if(!gNewUserProfile.allow2BypassNewRestrictions()&&(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()||gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()||gNewUserProfile.cENCASE.isEncased()||gNewUserProfile.cMITTS.isOn()||gNewUserProfile.cSUIT.isBDSMSuitOn())){
                            selectContainerLegs.setDisabled();
                        }
                        else if(!gNewUserProfile.allow2BypassNewRestrictions()&&gNewUserProfile.cARMCUFFS.isOn()){
                            selectContainerLegs.setDisabled();
                        }
                    }

                    //SLOWDOWN
                    if(!gNewUserProfile.cARMCUFFS.isPostRestrictEnabled())selectContainerSlowdown.setDefaultOptionAt(0);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==1)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasBlueCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==2)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasGreenCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==3)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasYellowCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==4)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasOrangeCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==5)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasRedCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )>5)selectContainerSlowdown.setDefaultOptionByValue(lsUnicodeEmotes.aliasPurpleCircle);
                    //RESTRICTION
                    if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled())selectContainerRestriction.setDefaultOptionByValue(lsUnicodeEmotes.aliasUnlock);
                    else selectContainerRestriction.setDefaultOptionByValue(lsUnicodeEmotes.aliasLock);
                    switch (command){
                        case "arm": case "arms":
                            messageComponentManager.messageBuildComponents.setIgnored(0);
                            break;
                        case "leg": case "legs":
                            messageComponentManager.messageBuildComponents.setIgnored(1);
                            break;
                        case "restrictions": case"restriction":
                            messageComponentManager.messageBuildComponents.setIgnored(2);
                            messageComponentManager.messageBuildComponents.setIgnored(3);
                            break;
                    }
                    logger.info(fName+"component.post="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsWearerListener(message,command);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsSomebody(String command){
            String fName="[menuLevelsSomebody]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+command);menuCommand=command;
                EmbedBuilder embed=new EmbedBuilder();

                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(!gNewUserProfile.cARMCUFFS.getLevelAsString().isBlank()&&!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                    embed.addField("Arm binding", gNewUserProfile.cARMCUFFS.getLevelAsString(),true);
                } else{
                    embed.addField("Arm binding","(not wearing)",true);
                }

                boolean b= gNewUserProfile.cARMCUFFS.isPostRestrictEnabled();
                long l= gNewUserProfile.cARMCUFFS.getPostDelay();
                if(b&&l==1){
                    embed.addField("Post slowdown","5 seconds",true);
                }
                else if(b&&l==2){
                    embed.addField("Post slowdown","15 seconds",true);
                }
                else if(b&&l==3){
                    embed.addField("Post slowdown","30 seconds",true);
                }
                else if(b&&l==4){
                    embed.addField("Post slowdown","45 seconds",true);
                }
                else if(b&&l==5){
                    embed.addField("Post slowdown","60 seconds",true);
                }
                else if(b&&l==9){
                    embed.addField("Post slowdown","restrain depending",true);
                }
                else{
                    embed.addField("Post slowdown","disabled",true);
                }

                if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().isBlank()&&!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(nNone)){
                    embed.addField("Leg binding", gNewUserProfile.cLEGCUFFS.getLevelAsString(),true);
                } else{
                    embed.addField("Leg binding","(not wearing)",true);
                }

                if(gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()){
                    embed.addField("Channel Restriction","enabled\nbound to:"+lsChannelHelper.lsGetTextChannelMention(gGuild, gNewUserProfile.cLEGCUFFS.getChannelRestrainId()),true);
                }else{
                    embed.addField("Channel Restriction","disabled",true);
                }

                switch (command){
                    case "arm": case "arms":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+"untie arms";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+CUFFSARMSLEVELS.Armbinder.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CUFFSARMSLEVELS.Behind.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CUFFSARMSLEVELS.BehindBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CUFFSARMSLEVELS.BehindTBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+CUFFSARMSLEVELS.BehindTight.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+CUFFSARMSLEVELS.Elbows.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+CUFFSARMSLEVELS.Front.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+CUFFSARMSLEVELS.FrontBelt.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+CUFFSARMSLEVELS.ReversePrayer.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+CUFFSARMSLEVELS.Sides.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+CUFFSARMSLEVELS.SidesTight.getName();
                        if(gNewUserProfile.gUserProfile.getUser()==gUser){
                            embed.addField(" ", "Please select a arm cuffs option :"+desc, false);
                        }else{
                            embed.addField(" ", "Please select a arm cuffs option for " + gNewUserProfile.getMember().getAsMention() + ":"+desc, false);
                        }
                        embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of limited postings, they need to be enabled from the cuffs restrictions menu.", false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                        break;
                    case "leg": case "legs":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+"untie legs";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+CUFFSLEGSLEVELS.Taut.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+CUFFSLEGSLEVELS.Stand.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+CUFFSLEGSLEVELS.Sit.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+CUFFSLEGSLEVELS.LayBack.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+CUFFSLEGSLEVELS.LayBelly.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+CUFFSLEGSLEVELS.Spreaderbar.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+CUFFSLEGSLEVELS.HogBack.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+CUFFSLEGSLEVELS.HogSideways.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+CUFFSLEGSLEVELS.HogTie.getName();
                        embed.addField(" ", "Please select a leg cuffs option :"+desc, false);
                        embed.addField("Effects?", "By default it's disabled.\nIn order to see the effects of restricted postings, they need to be enabled from the cuffs restrictions menu.", false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                        break;
                    case "restrictions": case"restriction":
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" "+cDisable;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" "+cShortest+" (5 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" "+cShort+" (15 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle)+" "+cNormal+" (30 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle)+" "+cNormal+" (45 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" "+cNormal+" (60 seconds)";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle)+" "+cNormal+" (restrain depending)";
                        desc+="\nSimulates difficulty writing posts, like when hands are restrained, by deleting original message if they posted to quickly. The more complex the restrain, the more they need to wait between posting.";
                        desc+="\nIn order to work they need to have their hands restrained: cuffs, mitts, straitjacket";
                        embed.addField(" ", "Please select post slowdown option :"+desc, false);
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" "+cFree+", disable restrictions";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" "+cRestrict+", enable restriction";
                        if(gNewUserProfile.gUserProfile.getUser()!=gUser)desc+="\n"+gGlobal.emojis.getEmoji("chains")+" "+cDrag+", allow them to post in the channel the command was called";
                        desc+="\nSimulates being restrained to a channel. Imagine each channel is a room, if the subject has their legs tied, they not able to leave the current room to enter a new one.";
                        desc+="\nIn order to work they need to have their legs restrained.";
                        embed.addField(" ", "Please select channel restriction option :"+desc, false);
                        desc="Set to use gag exception for post slowdown(slowmode) exception. It uses the exception for the gag option.\nOptions:";

                        if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" don't use user gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar)+" use user gag exception";
                        }


                        if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen)+" don't use server gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen)+" use server gag exception";
                        }

                        embed.addField("Post Slowdown exception", desc, false);
                        desc="Set to use gag exception for channel restriction exception. It uses the exception for the gag option.\nOptions:";
                        if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" don't use user gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro)+" use user gag exception";
                        }

                        if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()){
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound)+" don't use server gag exception";
                        } else{
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound)+" use server gag exception";
                        }

                        embed.addField("Channel restriction exception", desc, false);
                        embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);

                        break;
                    default:
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" arms level";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" legs level";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" restriction options";
                        embed.addField(" ", "Please select a sub-command:"+desc, false);
                        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                        break;
                }
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                /*if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    if(gNewUserProfile.cARMCUFFS.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                    if(!gNewUserProfile.cARMCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                }
                else if(command.equalsIgnoreCase("legs")||command.equalsIgnoreCase("legs")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    if(gNewUserProfile.cLEGCUFFS.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Taut.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Stand.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Sit.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.LayBack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.LayBelly.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.Spreaderbar.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogBack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogSideways.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                    if(!gNewUserProfile.cLEGCUFFS.getLevelAsString().equalsIgnoreCase(CUFFSLEGSLEVELS.HogTie.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                }
                else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));

                    if(gNewUserProfile.gUserProfile.getUser()!=gUser) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("chains"));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound));

                }
                else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                //if(message!=null)menuMessage=message;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                    lcMessageBuildComponent.SelectMenu selectContainer1=messageComponentManager.messageBuildComponents.getSelectMenuAt(1);
                    lcMessageBuildComponent.SelectMenu selectContainer2=messageComponentManager.messageBuildComponents.getSelectMenuAt(2);
                    lcMessageBuildComponent.SelectMenu selectContainer3=messageComponentManager.messageBuildComponents.getSelectMenuAt(3);
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(4,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    //ARMS
                    if(!gNewUserProfile.cARMCUFFS.isOn())selectContainer0.setDefaultOptionAt(0);
                    else{
                        switch (gNewUserProfile.cARMCUFFS.getLevel()){
                            case Armbinder:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Behind:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case BehindBelt:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case BehindTBelt:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case BehindTight:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Elbows:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case Front:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case FrontBelt:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case ReversePrayer:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case Sides:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                            case SidesTight:selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);break;
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember)){
                            selectContainer0.setDisabled();
                        }
                    }

                    //LEGS
                    if(!gNewUserProfile.cLEGCUFFS.isOn())selectContainer1.setDefaultOptionAt(0);
                    else{
                        switch (gNewUserProfile.cLEGCUFFS.getLevel()){
                            case FauxTaut:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Taut:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Stand:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Sit:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case LayBack:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case LayBelly:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case Spreaderbar:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case HogBack:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case HogSideways:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case HogTie:selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                        }
                        if(gNewUserProfile.isLocked_checkMember(gMember)){
                            selectContainer1.setDisabled();
                        }
                    }

                    //SLOWDOWN
                    if(!gNewUserProfile.cARMCUFFS.isPostRestrictEnabled())selectContainer2.setDefaultOptionAt(0);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==1)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasBlueCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==2)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasGreenCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==3)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasYellowCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==4)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasOrangeCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )==5)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasRedCircle);
                    else if(gNewUserProfile.cARMCUFFS.getPostDelay( )>5)selectContainer2.setDefaultOptionByValue(lsUnicodeEmotes.aliasPurpleCircle);
                    //RESTRICTION
                    if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled())selectContainer3.setDefaultOptionAt(0);
                    else selectContainer3.setDefaultOptionByValue(lsUnicodeEmotes.aliasLock);

                    switch (command){
                        case "arm": case "arms":
                            messageComponentManager.messageBuildComponents.setIgnored(0);
                            break;
                        case "leg": case "legs":
                            messageComponentManager.messageBuildComponents.setIgnored(1);
                            break;
                        case "restrictions": case"restriction":
                            messageComponentManager.messageBuildComponents.setIgnored(2);
                            messageComponentManager.messageBuildComponents.setIgnored(3);
                            break;
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    //message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    //message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsSomebodyListener(message,command);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message,String command){
            String fName="[menuLevelsWearerListener]";
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
                                        rHelp("main");return;
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
                                String id=e.getComponentId();
                                logger.warn(fName+"componentId="+id);
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="",category="";
                                switch (id){
                                    case "select_arm":
                                        category="arm";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:level=nUntie;break;
                                            case lsUnicodeEmotes.aliasOne:level=CUFFSARMSLEVELS.Armbinder.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=CUFFSARMSLEVELS.Behind.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=CUFFSARMSLEVELS.BehindBelt.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=CUFFSARMSLEVELS.BehindTBelt.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=CUFFSARMSLEVELS.BehindTight.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=CUFFSARMSLEVELS.Elbows.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=CUFFSARMSLEVELS.Front.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=CUFFSARMSLEVELS.FrontBelt.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=CUFFSARMSLEVELS.ReversePrayer.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=CUFFSARMSLEVELS.Sides.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolB:level=CUFFSARMSLEVELS.SidesTight.getName();break;
                                        }
                                        break;
                                    case "select_leg":
                                        category="leg";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:level=nUntie;break;
                                            case lsUnicodeEmotes.aliasOne:level=CUFFSLEGSLEVELS.FauxTaut.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=CUFFSLEGSLEVELS.Taut.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=CUFFSLEGSLEVELS.Stand.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=CUFFSLEGSLEVELS.Sit.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=CUFFSLEGSLEVELS.LayBack.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=CUFFSLEGSLEVELS.LayBelly.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=CUFFSLEGSLEVELS.Spreaderbar.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=CUFFSLEGSLEVELS.HogBack.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=CUFFSLEGSLEVELS.HogSideways.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=CUFFSLEGSLEVELS.HogTie.getName();break;
                                        }
                                        break;
                                    case "select_slowdown":
                                        category="post";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasWhiteCircle:level=cDisable;break;
                                            case lsUnicodeEmotes.aliasBlueCircle:level=cShortest;break;
                                            case lsUnicodeEmotes.aliasGreenCircle:level=cShort;break;
                                            case lsUnicodeEmotes.aliasYellowCircle:level=cNormal;break;
                                            case lsUnicodeEmotes.aliasOrangeCircle:level=cLong;break;
                                            case lsUnicodeEmotes.aliasRedCircle:level=cLongest;break;
                                            case lsUnicodeEmotes.aliasPurpleCircle:level=cDefault;break;
                                        }
                                        break;
                                    case "select_restriction":
                                        category="channel";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasUnlock:level=cFree;break;
                                            case lsUnicodeEmotes.aliasLock:level=cRestrict;break;
                                        }
                                        break;
                                }
                                if(!level.isBlank()){
                                    switch (category.toLowerCase()){
                                        case "arm":
                                            isMenuLevel=true;rAarmBinding(level);
                                            break;
                                        case "leg":
                                            isMenuLevel=true;rLegBinding(level);
                                            break;
                                        case"post":
                                            isMenuLevel=true;rPostLimiter(level);
                                            break;
                                        case"channel":
                                            isMenuLevel=true;rChannelLimiter(level);
                                            break;
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer(command);
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
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main"); return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="arm";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="arm";level=CUFFSARMSLEVELS.Armbinder.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="arm";level=CUFFSARMSLEVELS.Behind.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="arm";level=CUFFSARMSLEVELS.BehindBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="arm";level=CUFFSARMSLEVELS.BehindTBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="arm";level=CUFFSARMSLEVELS.BehindTight.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="arm";level=CUFFSARMSLEVELS.Elbows.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="arm";level=CUFFSARMSLEVELS.Front.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="arm";level=CUFFSARMSLEVELS.FrontBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="arm";level=CUFFSARMSLEVELS.ReversePrayer.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="arm";level=CUFFSARMSLEVELS.Sides.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="arm";level=CUFFSARMSLEVELS.SidesTight.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("legs")||command.equalsIgnoreCase("legs")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="leg";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="leg";level=CUFFSLEGSLEVELS.FauxTaut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="leg";level=CUFFSLEGSLEVELS.Taut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="leg";level=CUFFSLEGSLEVELS.Stand.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="leg";level=CUFFSLEGSLEVELS.Sit.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="leg";level=CUFFSLEGSLEVELS.LayBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="leg";level=CUFFSLEGSLEVELS.LayBelly.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="leg";level=CUFFSLEGSLEVELS.Spreaderbar.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="leg";level=CUFFSLEGSLEVELS.HogBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="leg";level=CUFFSLEGSLEVELS.HogSideways.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="leg";level=CUFFSLEGSLEVELS.HogTie.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){category="post";level=cDisable;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category="post";level=cShortest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category="post";level=cShort;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category="post";level=cNormal;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category="post";level=cLong;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){category="post";level=cLongest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle))){category="post";level=cDefault;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){category="channel";level=cFree;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){category="channel";level=cRestrict;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("chains"))){category="channel";level=cDrag;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                    }
                                    else{
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                            rHelp("main");
                                            return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){menuLevelsWearer("arms");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){menuLevelsWearer("legs");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){menuLevelsWearer("restrictions");return;}

                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            menuLevelsWearer(command);return;
                                        }else{
                                            if(category.equalsIgnoreCase("arm")){
                                                isMenuLevel=true;rAarmBinding(level);
                                            }
                                            else if(category.equalsIgnoreCase("leg")){
                                                isMenuLevel=true;rLegBinding(level);
                                            }
                                            else if(category.equalsIgnoreCase("post")){
                                                isMenuLevel=true;rPostLimiter(level);
                                            }
                                            else if(category.equalsIgnoreCase("channel")){
                                                isMenuLevel=true;rChannelLimiter(level);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main"); return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="arm";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="arm";level=CUFFSARMSLEVELS.Armbinder.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="arm";level=CUFFSARMSLEVELS.Behind.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="arm";level=CUFFSARMSLEVELS.BehindBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="arm";level=CUFFSARMSLEVELS.BehindTBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="arm";level=CUFFSARMSLEVELS.BehindTight.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="arm";level=CUFFSARMSLEVELS.Elbows.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="arm";level=CUFFSARMSLEVELS.Front.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="arm";level=CUFFSARMSLEVELS.FrontBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="arm";level=CUFFSARMSLEVELS.ReversePrayer.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="arm";level=CUFFSARMSLEVELS.Sides.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="arm";level=CUFFSARMSLEVELS.SidesTight.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("legs")||command.equalsIgnoreCase("legs")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="leg";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="leg";level=CUFFSLEGSLEVELS.FauxTaut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="leg";level=CUFFSLEGSLEVELS.Taut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="leg";level=CUFFSLEGSLEVELS.Stand.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="leg";level=CUFFSLEGSLEVELS.Sit.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="leg";level=CUFFSLEGSLEVELS.LayBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="leg";level=CUFFSLEGSLEVELS.LayBelly.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="leg";level=CUFFSLEGSLEVELS.Spreaderbar.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="leg";level=CUFFSLEGSLEVELS.HogBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="leg";level=CUFFSLEGSLEVELS.HogSideways.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="leg";level=CUFFSLEGSLEVELS.HogTie.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevelsWearer("main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){category="post";level=cDisable;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category="post";level=cShortest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category="post";level=cShort;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category="post";level=cNormal;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category="post";level=cLong;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){category="post";level=cLongest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle))){category="post";level=cDefault;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){category="channel";level=cFree;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){category="channel";level=cRestrict;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("chains"))){category="channel";level=cDrag;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                    }
                                    else{
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                            rHelp("main");
                                            return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){menuLevelsWearer("arms");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){menuLevelsWearer("legs");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){menuLevelsWearer("restrictions");return;}

                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            menuLevelsWearer(command);return;
                                        }else{
                                            if(category.equalsIgnoreCase("arm")){
                                                isMenuLevel=true;rAarmBinding(level);
                                            }
                                            else if(category.equalsIgnoreCase("leg")){
                                                isMenuLevel=true;rLegBinding(level);
                                            }
                                            else if(category.equalsIgnoreCase("post")){
                                                isMenuLevel=true;rPostLimiter(level);
                                            }
                                            else if(category.equalsIgnoreCase("channel")){
                                                isMenuLevel=true;rChannelLimiter(level);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsSomebodyListener(Message message,String command){
            String fName="[menuLevelsSomebodyListener]";
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
                                        rHelp("main");return;
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
                                String id=e.getComponentId();
                                logger.warn(fName+"componentId="+id);
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                String level="",category="";
                                llMessageDelete(message);
                                switch (id){
                                    case "select_arm":
                                        category="arm";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:level=nUntie;break;
                                            case lsUnicodeEmotes.aliasOne:level=CUFFSARMSLEVELS.Armbinder.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=CUFFSARMSLEVELS.Behind.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=CUFFSARMSLEVELS.BehindBelt.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=CUFFSARMSLEVELS.BehindTBelt.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=CUFFSARMSLEVELS.BehindTight.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=CUFFSARMSLEVELS.Elbows.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=CUFFSARMSLEVELS.Front.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=CUFFSARMSLEVELS.FrontBelt.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=CUFFSARMSLEVELS.ReversePrayer.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=CUFFSARMSLEVELS.Sides.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolB:level=CUFFSARMSLEVELS.SidesTight.getName();break;
                                        }
                                        break;
                                    case "select_leg":
                                        category="leg";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:level=nUntie;break;
                                            case lsUnicodeEmotes.aliasOne:level=CUFFSLEGSLEVELS.FauxTaut.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=CUFFSLEGSLEVELS.Taut.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=CUFFSLEGSLEVELS.Stand.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=CUFFSLEGSLEVELS.Sit.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=CUFFSLEGSLEVELS.LayBack.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=CUFFSLEGSLEVELS.LayBelly.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=CUFFSLEGSLEVELS.Spreaderbar.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=CUFFSLEGSLEVELS.HogBack.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=CUFFSLEGSLEVELS.HogSideways.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=CUFFSLEGSLEVELS.HogTie.getName();break;
                                        }
                                        break;
                                    case "select_slowdown":
                                        category="post";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasWhiteCircle:level=cDisable;break;
                                            case lsUnicodeEmotes.aliasBlueCircle:level=cShortest;break;
                                            case lsUnicodeEmotes.aliasGreenCircle:level=cShort;break;
                                            case lsUnicodeEmotes.aliasYellowCircle:level=cNormal;break;
                                            case lsUnicodeEmotes.aliasOrangeCircle:level=cLong;break;
                                            case lsUnicodeEmotes.aliasRedCircle:level=cLongest;break;
                                            case lsUnicodeEmotes.aliasPurpleCircle:level=cDefault;break;
                                        }
                                        break;
                                    case "select_restriction":
                                        category="channel";
                                        switch (value){
                                            case lsUnicodeEmotes.aliasUnlock:level=cFree;break;
                                            case lsUnicodeEmotes.aliasLock:level=cRestrict;break;
                                        }
                                        break;
                                }
                                if(!level.isBlank()){
                                    switch (category.toLowerCase()){
                                        case "arm":
                                            isMenuLevel=true;rAarmBinding(gNewUserProfile.getMember(),level);
                                            break;
                                        case "leg":
                                            isMenuLevel=true;rLegBinding(gNewUserProfile.getMember(),level);
                                            break;
                                        case"post":
                                            isMenuLevel=true;rPostLimiter(gNewUserProfile.getMember(),level);
                                            break;
                                        case"channel":
                                            isMenuLevel=true;rChannelLimiter(gNewUserProfile.getMember(),level);
                                            break;
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsSomebody(command);
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
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main"); return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="arm";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="arm";level=CUFFSARMSLEVELS.Armbinder.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="arm";level=CUFFSARMSLEVELS.Behind.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="arm";level=CUFFSARMSLEVELS.BehindBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="arm";level=CUFFSARMSLEVELS.BehindTBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="arm";level=CUFFSARMSLEVELS.BehindTight.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="arm";level=CUFFSARMSLEVELS.Elbows.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="arm";level=CUFFSARMSLEVELS.Front.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="arm";level=CUFFSARMSLEVELS.FrontBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="arm";level=CUFFSARMSLEVELS.ReversePrayer.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="arm";level=CUFFSARMSLEVELS.Sides.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="arm";level=CUFFSARMSLEVELS.SidesTight.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("legs")||command.equalsIgnoreCase("legs")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="leg";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="leg";level=CUFFSLEGSLEVELS.FauxTaut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="leg";level=CUFFSLEGSLEVELS.Taut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="leg";level=CUFFSLEGSLEVELS.Stand.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="leg";level=CUFFSLEGSLEVELS.Sit.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="leg";level=CUFFSLEGSLEVELS.LayBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="leg";level=CUFFSLEGSLEVELS.LayBelly.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="leg";level=CUFFSLEGSLEVELS.Spreaderbar.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="leg";level=CUFFSLEGSLEVELS.HogBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="leg";level=CUFFSLEGSLEVELS.HogSideways.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="leg";level=CUFFSLEGSLEVELS.HogTie.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){category="post";level=cDisable;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category="post";level=cShortest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category="post";level=cShort;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category="post";level=cNormal;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category="post";level=cLong;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){category="post";level=cLongest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle))){category="post";level=cDefault;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){category="channel";level=cFree;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){category="channel";level=cRestrict;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("chains"))){category="channel";level=cDrag;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                    }
                                    else{
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                            rHelp("main");
                                            return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){menuLevels(gNewUserProfile.getMember(),"arms");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){menuLevels(gNewUserProfile.getMember(),"legs");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){menuLevels(gNewUserProfile.getMember(),"restrictions");return;}

                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            menuLevels(gNewUserProfile.getMember(),command);return;
                                        }else{
                                            if(category.equalsIgnoreCase("arm")){
                                                isMenuLevel=true;rAarmBinding(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("leg")){
                                                isMenuLevel=true;rLegBinding(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("post")){
                                                isMenuLevel=true;rPostLimiter(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("channel")){
                                                isMenuLevel=true;rChannelLimiter(gNewUserProfile.getMember(),level);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(command.equalsIgnoreCase("arm")||command.equalsIgnoreCase("arms")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main"); return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="arm";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="arm";level=CUFFSARMSLEVELS.Armbinder.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="arm";level=CUFFSARMSLEVELS.Behind.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="arm";level=CUFFSARMSLEVELS.BehindBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="arm";level=CUFFSARMSLEVELS.BehindTBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="arm";level=CUFFSARMSLEVELS.BehindTight.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="arm";level=CUFFSARMSLEVELS.Elbows.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="arm";level=CUFFSARMSLEVELS.Front.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="arm";level=CUFFSARMSLEVELS.FrontBelt.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="arm";level=CUFFSARMSLEVELS.ReversePrayer.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="arm";level=CUFFSARMSLEVELS.Sides.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category="arm";level=CUFFSARMSLEVELS.SidesTight.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("legs")||command.equalsIgnoreCase("legs")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){category="leg";level=nUntie;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category="leg";level=CUFFSLEGSLEVELS.FauxTaut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category="leg";level=CUFFSLEGSLEVELS.Taut.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category="leg";level=CUFFSLEGSLEVELS.Stand.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category="leg";level=CUFFSLEGSLEVELS.Sit.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category="leg";level=CUFFSLEGSLEVELS.LayBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category="leg";level=CUFFSLEGSLEVELS.LayBelly.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){category="leg";level=CUFFSLEGSLEVELS.Spreaderbar.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){category="leg";level=CUFFSLEGSLEVELS.HogBack.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){category="leg";level=CUFFSLEGSLEVELS.HogSideways.getName();}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category="leg";level=CUFFSLEGSLEVELS.HogTie.getName();}
                                    }
                                    else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("restriction")){
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                        {
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){category="post";level=cDisable;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category="post";level=cShortest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category="post";level=cShort;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category="post";level=cNormal;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category="post";level=cLong;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){category="post";level=cLongest;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPurpleCircle))){category="post";level=cDefault;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){category="channel";level=cFree;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){category="channel";level=cRestrict;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("chains"))){category="channel";level=cDrag;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDollar))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYen))){
                                            category="post";
                                            if(gNewUserProfile.cARMCUFFS.isPostRestrictionException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEuro))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseUserGag()) level=cExceptionUserOff;
                                            else level=cExceptionUserOn;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPound))){
                                            category="channel";
                                            if(gNewUserProfile.cLEGCUFFS.isChannelRestrainException2UseGuildGag()) level=cExceptionGuildOff;
                                            else level=cExceptionGuildOn;}
                                    }
                                    else{
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                            rHelp("main");
                                            return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){menuLevels(gNewUserProfile.getMember(),"arms");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){menuLevels(gNewUserProfile.getMember(),"legs");return;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){menuLevels(gNewUserProfile.getMember(),"restrictions");return;}

                                    }
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            menuLevels(gNewUserProfile.getMember(),command);return;
                                        }else{
                                            if(category.equalsIgnoreCase("arm")){
                                                isMenuLevel=true;rAarmBinding(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("leg")){
                                                isMenuLevel=true;rLegBinding(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("post")){
                                                isMenuLevel=true;rPostLimiter(gNewUserProfile.getMember(),level);
                                            }
                                            else if(category.equalsIgnoreCase("channel")){
                                                isMenuLevel=true;rChannelLimiter(gNewUserProfile.getMember(),level);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
    private void userNotificationLegCuffs(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nLegsCuffs;
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
    private void userNotificationArmCuffs(int action,String desc) {
            String fName = "[userNotificationLegCuffs]";
            logger.info(fName + "action=" + action);
            logger.info(fName + "desc=" + desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field = nArmsCuffs;
                if (gNewUserProfile.gUserProfile.jsonObject.has(nNotification)) {
                    if (gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)) {
                        if (isAdult&&action == actionSecured) {
                            if (gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlSecure) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlSecure) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure).isEmpty()) {
                                String url = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure);
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            } else {
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        } else if (isAdult&&action == actionUnSecured) {
                            if (gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlUnSecure) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlUnSecure) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure).isEmpty()) {
                                String url = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure);
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            } else {
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        } else if (isAdult&&action == actionStruggle) {
                            if (gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlStruggle) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlStruggle) && !gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle).isEmpty()) {
                                String url = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle);
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            } else {
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        } else {
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            
        }

        private void rChannelSet() {
            String fName = "[rChannelSet]";
            logger.info(fName);
            try {
                if(!gNewUserProfile.cLEGCUFFS.isChannelRestrainEnabled()){
                    logger.info(fName + ".disabled>ignore");return;
                }
                long channelId= gNewUserProfile.cLEGCUFFS.getChannelRestrainId();
                logger.info(fName + ".channelId="+channelId);
                if(channelId==0L){
                    logger.info(fName + ".set new channel 1");
                     gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                }
                TextChannel textChannel=lsChannelHelper.lsGetTextChannelById(gGuild,channelId);
                if(textChannel==null){
                    logger.info(fName + ".set new channel 2");
                     gNewUserProfile.cLEGCUFFS.setChannelRestrainId( gTextChannel.getIdLong());
                }
                logger.info(fName + ".nothing set");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    String strMessage1="A pair of cuffs that can be attached to things or eachother.",
        strMessage2="Ties up sub's arms.\n**List of commands:**",
        strMessage3="Ties up sub's legs.\n**List of commands:**",
        strMessageCantManipulatePermalock="Can't manipulate your restraints as they permanently locked!";


}}
