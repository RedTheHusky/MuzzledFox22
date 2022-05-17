package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Orange;
import models.ll.colors.llColors_Red;
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
import restraints.in.iTimeout;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.rdExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdTimeout extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gCommand="timeout";
	String sMainRTitle ="Timeout";
    public rdTimeout(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Timeout-DiscordRestraints";
        this.help = "timeout";
        this.aliases = new String[]{gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }

    public rdTimeout(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimeout(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
                setTitleStr(rdTimeout.this.sMainRTitle);setPrefixStr(rdTimeout.this.llPrefixStr);setCommandStr(rdTimeout.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdTimeout_commands");
                lsUsefullFunctions.setThreadName4Display("rdTimeout");
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
                        status(gTarget);isInvalidCommand=false;
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
                                if(gItems.length>=3&&gItems[1].equalsIgnoreCase("set")){
                                    if(gItems[2].equalsIgnoreCase("duration")){
                                        if(gItems.length>3){
                                            setDuration(gTarget,gArgs);
                                        }else{
                                            menuText(gTarget,1,"duration");
                                        }
                                        isInvalidCommand=false;
                                    }
                                    else if(gItems[2].equalsIgnoreCase("text")){
                                        menuText(gTarget,2,"message");isInvalidCommand=false;
                                    }
                                    else if(gItems[2].equalsIgnoreCase("count")){
                                        if(gItems.length>3){
                                            setCount(gTarget,gItems[3]);
                                        }else{
                                            menuText(gTarget,2,"count");
                                        }
                                        isInvalidCommand=false;
                                    }
                                    else if(gItems[2].equalsIgnoreCase("channel")){
                                        if(gItems.length>3){
                                            if(gItems[3].equalsIgnoreCase("clear")){
                                                setChannel(gTarget,"clear",null);
                                            }else{
                                                setChannel(gTarget,"set",gMessage);
                                            }
                                            isInvalidCommand=false;
                                        }
                                    }
                                }
                                else if(gItems[1].equalsIgnoreCase("start")){
                                    startSession(gTarget);isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("red")){
                                    doRed(gTarget);isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("status")){
                                   status(gTarget);isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("help")){
                                   rHelp("main");isInvalidCommand=false;
                                }
                            }
                            if(isInvalidCommand&&gTarget!=null){
                                menuLevels(gTarget);isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            //normal
                            if(gItems==null||gItems.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }
                            else if(gItems.length>=2&&gItems[0].equalsIgnoreCase("setup")){
                                gBDSMCommands.timeout.init();
                                if(gItems[1].equalsIgnoreCase("channel")){
                                    if(gItems.length>2){
                                        if(gItems[2].equalsIgnoreCase("clear")){
                                            setGuildChannel("clear",null);
                                        }else{
                                            setGuildChannel("set",gMessage);
                                        }
                                        isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems.length>=2&&gItems[0].equalsIgnoreCase("set")){
                                if(gItems[1].equalsIgnoreCase("duration")){
                                    if(gItems.length>2){
                                        setDuration(gArgs);
                                    }else{
                                        menuText(1,"duration");
                                    }
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("text")){
                                    menuText(2,"message");isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("count")){
                                    if(gItems.length>2){
                                        setCount(gItems[2]);
                                    }else{
                                        menuText(2,"count");
                                    }
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("channel")){
                                    if(gItems.length>2){
                                        if(gItems[2].equalsIgnoreCase("clear")){
                                            setChannel("clear",null);
                                        }else{
                                            setChannel("set",gMessage);
                                        }
                                        isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("start")){
                                startSession(-1);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("red")){
                                doRed(null);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("add")){
                                add(gArgs);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("sub")){
                                sub(gCommandEvent.getArgs());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("status")){
                               status(null);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("help")){
                                rHelp("main");isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("debug")){
                                //getDebug();
                                logger.info(fName+".debug");
                                isInvalidCommand=false;
                            }
                            else{
                                menuLevels(null);isInvalidCommand=false;
                            }
                        }
                    }

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
        String quickSummonWithSpace2=llPrefixStr+"timeout <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Initiating timeout to prevent them from posting as punishment.",false);
        desc=newLine+quickSummonWithSpace2+"set duration <number> <range of time>"+endLine+", sets the duration, also selects the timer mode";
        desc+=newLine+quickSummonWithSpace2+"set text"+endLine+", sets the text they need to post n times, also selects the writing mode";
        desc+=newLine+quickSummonWithSpace2+"set count"+endLine+", sets the number how many time they need to post the message, also selects the writting mode\"";
        desc+=newLine+quickSummonWithSpace2+"start"+endLine+", starts the timeout. The mode selection is done by the last thing set.";
        desc+=newLine+quickSummonWithSpace2+"set channel <#channel>|clear"+endLine+", sets text channel for the writting mode";
        if(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember)){
            desc+=newLine+quickSummonWithSpace2+"red"+endLine+", safeword to undo timeout.";
        }
        embed.addField("Commands",desc,false);
        desc="In the **timer** mode, they wont be able to post(s) text normally till timer runs out.";
        desc+="\nIn the **writing** mode, they wont be able to post(s) text normally till they written the enforced sentence n times.";
        desc+="\nThis uses the method to delete their post(s), less invasive than doing channel permission override.";
        embed.addField("Info",desc,false);
        desc="Setting the channel for writting mode at server level.";
        desc+=newLine+llPrefixStr+"timeout setup channel <#channel>|clear`";
        embed.addField("Setup",desc,false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
        private void status(Member mtarget){
            String fName = "[status]";
            logger.info(fName);
            try {
                String desc="";
                if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setAuthor(gNewUserProfile.gUserProfile.getUser().getName(),null, lsUserHelper.getAuthorIcon(gNewUserProfile.gUserProfile.getUser()));
                embedBuilder.setTitle("Timeout Status");embedBuilder.setColor(llColorOrange);
                try {
                    if(gNewUserProfile.cTIMEOUT.isOn()){
                        embedBuilder.addField("Active","yes",false);
                    }
                    else{
                        embedBuilder.addField("Active","no",false);
                    }
                    if(gNewUserProfile.cTIMEOUT.getTimeOutMode()==1){
                        embedBuilder.addField("Mode","timeout",false);
                    }
                    else if(gNewUserProfile.cTIMEOUT.getTimeOutMode()==2){
                        embedBuilder.addField("Mode","writing",false);
                    }else{
                        embedBuilder.addField("Mode","n/a",false);
                    }
                    desc="duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMEOUT.getTimeOut1Duration());
                    desc+="\nremaning: "+lsUsefullFunctions.displayDuration(iRestraints.sIsTimeOutGetRemaningDuration(gNewUserProfile.gUserProfile));
                    embedBuilder.addField("Timeout",desc,false);
                    desc="count: "+gNewUserProfile.cTIMEOUT.getTimeOut2Count();
                    desc+="\nremaning: "+iRestraints.sIsTimeOutGetRemaningCount(gNewUserProfile.gUserProfile);
                    desc+="\nsentence: "+gNewUserProfile.cTIMEOUT.getTimeOut2Sentence();
                    embedBuilder.addField("Writing",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void setDuration(String message){
            String fName = "[setDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMEOUT.setTimeOut1Duration( timeset);gNewUserProfile.cTIMEOUT.setTimeOutMode(1);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMessage(String message){
            String fName = "[setMessage]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                logger.info(fName + ".message=" + message);
                gNewUserProfile.cTIMEOUT.setTimeOut2Sentence( message);gNewUserProfile.cTIMEOUT.setTimeOutMode(2);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Writing sentence for "+gMember.getAsMention()+" set to:"+message, llColorPurple1);

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setCount(String message){
            String fName = "[setCount_self]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                logger.info(fName + ".message=" + message);
                int i=Integer.parseInt(message);
                if(i<=0){
                    logger.info(fName + ".i too small");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Count must be equal or above 1.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(i>50&&!isPatreon){
                    logger.info(fName + ".i too big for normal user");
                    llSendQuickEmbedMessage(gUser,sRTitle, iTimeout.strSettingCount, llColorRed);
                    return;
                }
                gNewUserProfile.cTIMEOUT.setTimeOut2Count( i);gNewUserProfile.cTIMEOUT.setTimeOutMode(2);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Writing sentence count for "+gMember.getAsMention()+" set to:"+i, llColorPurple1);

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set count!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void startSession(int mode){
            String fName = "[startSession]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                logger.info(fName + ".mode="+mode);
                if(mode<=0){
                    mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                    logger.info(fName + ".mode="+mode);
                }
                if(mode==1){
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut1Duration()<milliseconds_minute*15){
                        logger.info(fName + ".duration too small");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Duration is bellow the minimum time or not set.", llColorRed);
                        return;
                    }
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    logger.info(fName+".timestamp="+timestamp.getTime());
                    gNewUserProfile.cTIMEOUT.setTimeOut1Timestamp(timestamp.getTime());
                    gNewUserProfile.cTIMEOUT.setOn(true);
                }
                else if(mode==2){
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut2Count()<=0){
                        logger.info(fName + ".count too small");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't have 0 count writing objective.", llColorRed);
                        return;
                    }
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut2Sentence().isBlank()){
                        logger.info(fName + ".no text");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't have blank text writing objective.", llColorRed);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setTimeOut2Done(0);
                    gNewUserProfile.cTIMEOUT.setOn(true);
                }
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, gMember.getAsMention()+" started their timeout.", llColorPurple1);

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void setChannel(String command,Message message) {
            String fName = "[setChannel]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                if(command.equalsIgnoreCase("set")){
                    List<TextChannel> textChannelList=message.getMentionedChannels();
                    if(textChannelList.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"No channel mentioned!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    TextChannel textChannel=textChannelList.get(0);
                    if(!textChannel.isNSFW()){
                        logger.warn(fName+"can't be sfw channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"The channel must be NSFW!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setChannel(textChannel);
                    if(!saveProfile()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set targeted channel to: "+textChannel.getAsMention()+".", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("clear")){
                    gNewUserProfile.cTIMEOUT.setChannel(0);
                    if(!saveProfile()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Removed targeted channel", llColors.llColorPurple1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void add(String message){
            String fName = "[add]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timeout due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                logger.info(fName + ".message=" + message);
                int mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                logger.info(fName + ".mode=" + mode);
                if(mode==1){
                    isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                    long timeset = String2Timeset4Duration(message);
                    logger.info(fName + ".timeset=" + timeset);
                    long diff= gNewUserProfile.cTIMEOUT.getTimeOut1Duration()+timeset;
                    logger.info(fName + ".diff=" + diff);
                    gNewUserProfile.cTIMEOUT.setTimeOut1Duration(diff);
                    if (!saveProfile()) {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                        return;
                    }
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Added "+lsUsefullFunctions.displayDuration(timeset)+" to "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
                }
                if(mode==2){
                    int i=Integer.getInteger(message);
                    logger.info(fName + ".i=" + i);
                    int diff= gNewUserProfile.cTIMEOUT.getTimeOut2Count()+i;
                    logger.info(fName + ".diff=" + diff);
                    gNewUserProfile.cTIMEOUT.setTimeOut2Count(diff);
                    if (!saveProfile()) {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                        return;
                    }
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Added "+i+" to "+gMember.getAsMention()+"'s count.\nUpdated count: "+diff, llColorPink1);
                }
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));

            }
        }
        private void sub(String message){
            String fName = "[sub]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The sub can't subtract, ony add more time.", llColorRed);
                    return;
                }else
                if(gNewUserProfile.cTIMEOUT.getTimeLockMin()<milliseconds_minute*15){
                    logger.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                logger.info(fName + ".message=" + message);
                int mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                logger.info(fName + ".mode=" + mode);
                if(mode==1){
                    isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                    long timeset = String2Timeset4Duration(message);
                    logger.info(fName + ".timeset=" + timeset);
                    long diff= gNewUserProfile.cTIMEOUT.getTimeOut1Duration()-timeset;
                    logger.info(fName + ".diff=" + diff);
                    if(diff<milliseconds_minute*15){
                        llSendQuickEmbedMessage(gUser, sRTitle, "Duration can't be bellow 15 minutes.", llColorRed);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setTimeOut1Duration(diff);
                    if (!saveProfile()) {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                        return;
                    }
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed"+lsUsefullFunctions.displayDuration(timeset)+" from "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
                }
                if(mode==2){
                    int i=Integer.getInteger(message);
                    logger.info(fName + ".i=" + i);
                    int diff= gNewUserProfile.cTIMEOUT.getTimeOut2Count()+i;
                    logger.info(fName + ".diff=" + diff);
                    if(diff<1){
                        llSendQuickEmbedMessage(gUser, sRTitle, "Count can't be less than 1.", llColorRed);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setTimeOut2Count(diff);
                    if (!saveProfile()) {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                        return;
                    }
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed"+i+" from "+gMember.getAsMention()+"'s count.\nUpdated count: "+diff, llColorPink1);
                }
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void setDuration(Member mtarget, String message){
            String fName = "[setDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can set the duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can set !TARGET duration.");
                    gAskHandlingHelper.doAsk(() -> {setDurationSave(mtarget,timeset);});
                    return;
                }
                setDurationSave(mtarget,timeset);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMessage(Member mtarget, String message){
            String fName = "[setMessage]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                logger.info(fName + ".message=" + message);
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can set the message.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can set !TARGET's message.");
                    gAskHandlingHelper.doAsk(() -> {setMessageSave(mtarget,message);});
                    return;
                }
                setMessageSave(mtarget,message);

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setCount(Member mtarget, String message){
            String fName = "[setCount_target]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                logger.info(fName + ".message=" + message);
                int i=Integer.parseInt(message);
                if(i<=0){
                    logger.info(fName + ".i too small");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Count must be equal or above 1.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(i>50&&!isPatreon){
                    logger.info(fName + ".i too big for normal user");
                    llSendQuickEmbedMessage(gUser,sRTitle,iTimeout.strSettingCount, llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can set the count of message.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can set !TARGET's count of message'.");
                    gAskHandlingHelper.doAsk(() -> {setCountSave(mtarget,i);});
                    return;
                }
                setCountSave(mtarget,i);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void startSession(Member mtarget){
            String fName = "[startSession]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&& gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can start the session.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can start !TARGET session.");
                    gAskHandlingHelper.doAsk(() -> {startSession(mtarget);});
                    return;
                }
                startSessionSave(mtarget);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void setChannel(Member mtarget,String command,Message message) {
            String fName = "[setChannel]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.warn(fName + ".no access");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Denied! Only owner and sec-onwers have access.", llColorRed); return;
                }
                if(command.equalsIgnoreCase("set")){
                    List<TextChannel> textChannelList=message.getMentionedChannels();
                    if(textChannelList.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"No channel mentioned!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    TextChannel textChannel=textChannelList.get(0);
                    if(!textChannel.isNSFW()){
                        logger.warn(fName+"can't be sfw channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"The channel must be NSFW!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setChannel(textChannel);
                    if(!saveProfile()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set targeted channel for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" to "+textChannel.getAsMention()+".", llColors.llColorPurple1);
                    lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" set targeted channel for you to "+textChannel.getAsMention()+".", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("clear")){
                    gNewUserProfile.cTIMEOUT.setChannel(0);
                    if(!saveProfile()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Removed targeted channel for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+".", llColors.llColorPurple1);
                    lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" removed targeted channel for you.", llColors.llColorPurple1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void setDurationSave(Member mtarget,long timeset){
            String fName = "[setStartingDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTIMEOUT.setTimeOut1Duration( timeset);gNewUserProfile.cTIMEOUT.setTimeOutMode(1);
                llSendQuickEmbedMessage(gTextChannel, sRTitle, gMember.getAsMention()+" set "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s duration to "+lsUsefullFunctions.displayDuration(timeset)+".", llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMessageSave(Member mtarget,String message){
            String fName = "[setMinDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".message=" + message);
                gNewUserProfile.cTIMEOUT.setTimeOut2Sentence( message);gNewUserProfile.cTIMEOUT.setTimeOutMode(2);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, gMember.getAsMention()+" set "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s writing task test `"+message+"`.", llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setCountSave(Member mtarget,int count){
            String fName = "[setMaxDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".count=" + count);
                gNewUserProfile.cTIMEOUT.setTimeOut2Count( count);gNewUserProfile.cTIMEOUT.setTimeOutMode(2);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, gMember.getAsMention()+" set "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s writing task count to "+count+".", llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void startSessionSave(Member mtarget){
            String fName = "[startSession]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                int mode=0;
                logger.info(fName + ".mode="+mode);
                if(mode<=0){
                    mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                    logger.info(fName + ".mode="+mode);
                }
                if(mode==1){
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut1Duration()<milliseconds_minute*15){
                        logger.info(fName + ".duration too small");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Duration is bellow the minimum time or not set.", llColorRed);
                        return;
                    }
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    logger.info(fName+".timestamp="+timestamp.getTime());
                    gNewUserProfile.cTIMEOUT.setTimeOut1Timestamp(timestamp.getTime());
                    gNewUserProfile.cTIMEOUT.setOn(true);
                }
                else if(mode==2){
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut2Count()<=0){
                        logger.info(fName + ".count too small");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't have 0 count writing objective.", llColorRed);
                        return;
                    }
                    if(!gIsOverride&& gNewUserProfile.cTIMEOUT.getTimeOut2Sentence().isBlank()){
                        logger.info(fName + ".no text");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't have blank text writing objective.", llColorRed);
                        return;
                    }
                    gNewUserProfile.cTIMEOUT.setTimeOut2Done(0);
                    gNewUserProfile.cTIMEOUT.setOn(true);
                }
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, gMember.getAsMention()+" started "+gNewUserProfile.getMember().getAsMention()+"'s timeout.", llColorPurple1);
              }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }

        private void add(Member mtarget,String message){
            String fName = "[addDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                logger.info(fName + ".message=" + message);
                /*long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff= TIMEOUT.getLong(nTimeLockDuration)+timeset;
                logger.info(fName + ".diff=" + diff);
                if(TIMEOUT.getLong(nTimeLockMax)>=milliseconds_minute*30&&diff> TIMEOUT.getLong(nTimeLockMax)&& TIMEOUT.getLong(nTimeLockMax)>0){
                    logger.info(fName + ".above max");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration above maxmum.", llColorRed);
                    return;
                }
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".requesting update restraint");
                    String what="timelock";
                    llSendQuickEmbedMessage(gUser,sRTitle,mtarget.getAsMention()+"access is set to ask, so they need to confirm any change to their timelock.", llColorPurple2);
                    Message message2=llSendQuickEmbedMessageResponse(mtarget.getUser(),sRTitle,gUser.getAsMention()+"is attempting to add extra duration to your timelock. Do you accept it?", llColorPurple2);
                    message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple)).complete();
                    message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple)).complete();
                    long finalTimeset = timeset;
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message2.getId())&&e.getUserId().equalsIgnoreCase(mtarget.getId())),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))){
                                        logger.info(fName + ".approved");
                                        addDurationSave(mtarget,timeset);}else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                        logger.info(fName + ".reject");
                                        llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+rejectedyourrequestofUpdate+what+".", llColorRed_Cinnabar);
                                        llSendQuickEmbedMessage(target,sRTitle,yourejectedtheirrequest,llColorRed_Cinnabar);
                                    }
                                    llMessageDelete(message2);
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                logger.info(fName + ".timeout");
                                llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                                llMessageDelete(message2);
                            });
                    return;
                }
                addDurationSave(mtarget,timeset);*/
               } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void sub(Member mtarget,String message){
            String fName = "[subDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMEOUT.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timeout is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }else
                if(gNewUserProfile.cTIMEOUT.getTimeLockMin()<milliseconds_minute*15){
                    logger.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                logger.info(fName + ".message=" + message);
                /*long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff= TIMEOUT.getLong(nTimeLockDuration)-timeset;
                logger.info(fName + ".diff=" + diff);
                if(diff< gNewUserProfile.cTIMEOUT.getTimeLockMin()){
                    logger.info(fName + ".bellow minimum");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration bellow minimum.", llColorRed);
                    return;
                }
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".requesting update restraint");
                    String what="timelock";
                    llSendQuickEmbedMessage(gUser,sRTitle,mtarget.getAsMention()+"access is set to ask, so they need to confirm any change to their timelock.", llColorPurple2);
                    Message message2=llSendQuickEmbedMessageResponse(mtarget.getUser(),sRTitle,gUser.getAsMention()+"is attempting to remote duration from your timelock. Do you accept it?", llColorPurple2);
                    message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple)).complete();
                    message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple)).complete();
                    long finalTimeset = timeset;
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message2.getId())&&e.getUserId().equalsIgnoreCase(mtarget.getId())),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))){
                                        logger.info(fName + ".approved");
                                        subDurationSave(mtarget,timeset);}else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                        logger.info(fName + ".reject");
                                        llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+rejectedyourrequestofUpdate+what+".", llColorRed_Cinnabar);
                                        llSendQuickEmbedMessage(target,sRTitle,yourejectedtheirrequest,llColorRed_Cinnabar);
                                    }
                                    llMessageDelete(message2);
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                logger.info(fName + ".timeout");
                                llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                                llMessageDelete(message2);
                            });
                    return;
                }
                subDurationSave(mtarget,timeset);*/
               } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        String gCommandFileMainPath =gFileMainPath+"timeout/menuLevels.json";
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
                iRestraints.sIsTimeOut(gNewUserProfile.gUserProfile,gGlobal);
                try {
                    if(gNewUserProfile.cTIMEOUT.isOn()){
                        embed.addField("Active","yes",false);
                    }
                    else{
                        embed.addField("Active","no",false);
                    }
                    if(gNewUserProfile.cTIMEOUT.getTimeOutMode()==1){
                        embed.addField("Mode","timeout",false);
                    }
                    else if(gNewUserProfile.cTIMEOUT.getTimeOutMode()==2){
                        embed.addField("Mode","writing",false);
                    }else{
                        embed.addField("Mode","n/a",false);
                    }
                    desc="duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMEOUT.getTimeOut1Duration());
                    desc+="\nremaning: "+lsUsefullFunctions.displayDuration(iRestraints.sIsTimeOutGetRemaningDuration(gNewUserProfile.gUserProfile));
                    embed.addField("Timeout",desc,false);
                    desc="count: "+gNewUserProfile.cTIMEOUT.getTimeOut2Count();
                    desc+="\nremaning: "+iRestraints.sIsTimeOutGetRemaningCount(gNewUserProfile.gUserProfile);
                    desc+="\nsentence: "+gNewUserProfile.cTIMEOUT.getTimeOut2Sentence();
                    embed.addField("Writing",desc,false);
                    desc="";
                    if(gNewUserProfile.cTIMEOUT.getChannel()>0){
                        TextChannel selectedChannel=lsChannelHelper.lsGetTextChannelById(gGuild,gNewUserProfile.cTIMEOUT.getChannel());
                        if(selectedChannel!=null){
                            desc="Selected channel is "+selectedChannel.getAsMention()+", user is restricted to use this channel for writting their punishment sentence.";
                            if(!selectedChannel.isNSFW()){
                                desc+="\nNote the selected target is not a NSFW channel, invalid.";
                            }
                        }else{
                            desc="No channel selected, free to write their punishment in any channel.";
                        }
                    }
                    embed.addField("Channel",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                if(mtarget!=null){
                    embed.addField(" ","Please select an option for: "+mtarget.getAsMention()+".",false);
                }else{
                    embed.addField(" ","Please select an options.",false);
                }
                desc="";
                //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(!gNewUserProfile.cTIMEOUT.isOn()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" switch mode";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" set duration (timer mode)";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" set message (write mode)";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" set count (write mode)";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" set channel (check the text channel)";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" start the timeout";
                    if(gNewUserProfile.cTIMEOUT.getChannel()!=0L) {
                        desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive) + " clear channel";
                    }
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" set channel";
                    if(gNewUserProfile.cTIMEOUT.getChannel()!=0L) {
                        desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive) + " clear channel";
                    }
                }
                embed.setDescription(desc);
                Message message=llSendMessageResponse(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(!gNewUserProfile.cTIMEOUT.isOn()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(gNewUserProfile.cTIMEOUT.getChannel()!=0L){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    }
                }else{
                   // lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign));
                    //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));

                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    if(!gNewUserProfile.cTIMEOUT.isOn()){
                        messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(0);
                        if(gNewUserProfile.cTIMEOUT.getChannel()==0L){
                            messageComponentManager.messageBuildComponent_Button.delSelectById(lsUnicodeEmotes.aliasFive);
                        }
                        logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                        messageComponentManager.myMessageJsonBuilder.setComponents(messageComponentManager.messageBuildComponents);
                        logger.info(fName+"message="+messageComponentManager.myMessageJsonBuilder.getJson());
                        messageComponentManager.myMessageJsonBuilder.patch(message);
                    }
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
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
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource: rHelp("main"); break;
                                    case lsUnicodeEmotes.aliasOne:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(1,"duration");
                                        }else{
                                            menuText(mtarget,1,"duration");
                                        }

                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(2,"message");
                                        }else{
                                            menuText(mtarget,2,"message");
                                        }

                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(2,"count");
                                        }else{
                                            menuText(mtarget,2,"count");
                                        }

                                        break;
                                    case lsUnicodeEmotes.aliasHeavyPlusSign:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(0,"add");
                                        }else{
                                            menuText(mtarget,0,"add");
                                        }

                                        break;
                                    case lsUnicodeEmotes.aliasHeavyMinusSign:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(0,"sub");
                                        }else{
                                            menuText(mtarget,0,"sub");
                                        }

                                        break;
                                    case lsUnicodeEmotes.aliasLock:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            startSession(-1);
                                        }else{
                                            startSession(mtarget);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuText(-2,"channel_set");
                                        }else{
                                            menuText(mtarget,-2,"channel_set");
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            setChannel("clear",null);
                                        }else{
                                            setChannel(mtarget,"clear",null);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasZero:
                                        switch ( gNewUserProfile.cTIMEOUT.getTimeOutMode()){
                                            case 1:gNewUserProfile.cTIMEOUT.setTimeOutMode(2);break;
                                            default:gNewUserProfile.cTIMEOUT.setTimeOutMode(1);break;
                                        }
                                        saveProfile();
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            menuLevels(null);
                                        }else{
                                            menuLevels(mtarget);
                                        }
                                        break;
                                }


                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES,() -> logger.info(fName+ lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) { rHelp("main"); }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(1,"duration");
                                    }else{
                                        menuText(mtarget,1,"duration");
                                    }

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(2,"message");
                                    }else{
                                        menuText(mtarget,2,"message");
                                    }

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(2,"count");
                                    }else{
                                        menuText(mtarget,2,"count");
                                    }

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(0,"add");
                                    }else{
                                        menuText(mtarget,0,"add");
                                    }

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(0,"sub");
                                    }else{
                                        menuText(mtarget,0,"sub");
                                    }

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        startSession(-1);
                                    }else{
                                        startSession(mtarget);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuText(-2,"channel_set");
                                    }else{
                                        menuText(mtarget,-2,"channel_set");
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        setChannel("clear",null);
                                    }else{
                                        setChannel(mtarget,"clear",null);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    switch ( gNewUserProfile.cTIMEOUT.getTimeOutMode()){
                                        case 1:gNewUserProfile.cTIMEOUT.setTimeOutMode(2);break;
                                        default:gNewUserProfile.cTIMEOUT.setTimeOutMode(1);break;
                                    }
                                    saveProfile();
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        menuLevels(null);
                                    }else{
                                        menuLevels(mtarget);
                                    }
                                }
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

        private void menuText(int mode,String command){
            String fName="[menuText]";
            logger.info(fName);
            try{
                logger.info(fName+"mode="+mode);
                logger.info(fName+"command="+command);
                if(gNewUserProfile.gUserProfile==null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(sRTitle);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+sRTitle);
                }
                if(mode==-2){
                    if(command.equalsIgnoreCase("channel_set")){
                        embed.setDescription("Mention the channel you want to set to.");

                    }
                    Message message=llSendMessageResponse(gTextChannel,embed);
                    gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.equalsIgnoreCase("!cancel")){
                                        logger.info(fName+".cancel");
                                    }else
                                    if(content.isBlank()){
                                        logger.info(fName+".blank");
                                    }else{
                                        if(command.equalsIgnoreCase("channel_set")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setChannel("set",e.getMessage());
                                            }else{
                                                setChannel(gNewUserProfile.gUserProfile.getMember(),"set",e.getMessage());
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
                            },2, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }else{
                    if(mode==0){
                        mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                        logger.info(fName+"mode="+mode);
                    }
                    if(mode==1){
                        if(command.equalsIgnoreCase("duration")){
                            embed.setDescription("Please enter time to set duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                        else if(command.equalsIgnoreCase("add")){
                            embed.setDescription("Please enter time. to add to duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                        else if(command.equalsIgnoreCase("sub")){
                            embed.setDescription("Please enter time to subtract from duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                    }
                    if(mode==2){
                        if(command.equalsIgnoreCase("count")){
                            embed.setDescription("Please enter an integer number to set the count of messages.");
                        }
                        else if(command.equalsIgnoreCase("message")){
                            embed.setDescription("Please enter an the message they need to post n times.");
                        }
                        else if(command.equalsIgnoreCase("add")){
                            embed.setDescription("Please enter an integer number to add to the count.");
                        }
                        else if(command.equalsIgnoreCase("sub")){
                            embed.setDescription("Please enter an integer number to subtract from the count.");
                        }
                    }
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);

                                    if(content.equalsIgnoreCase("!cancel")){

                                    }else
                                    if(content.isBlank()){

                                    }else{
                                        if(command.equalsIgnoreCase("duration")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setDuration(content);
                                            }else{
                                                setDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("count")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setCount(content);
                                            }else{
                                                setCount(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("message")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setMessage(content);
                                            }else{
                                                setMessage(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("add")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                add(content);
                                            }else{
                                                add(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("sub")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                sub(content);
                                            }else{
                                                sub(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                    }
                                    llMessageDelete(message);
                                    menuLevels(null);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }

                            },10, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuText(Member target,int mode,String command){
            String fName="[menuText]";
            logger.info(fName);
            try{
                logger.info(fName+"mode="+mode);
                logger.info(fName+"command="+command);
                if(gNewUserProfile.gUserProfile==null){
                    if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(sRTitle);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+sRTitle);
                }
                if(mode==-2){
                    if(command.equalsIgnoreCase("channel_set")){
                        embed.setDescription("Mention the channel you want to set to.");

                    }
                    Message message=llSendMessageResponse(gTextChannel,embed);
                    gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);

                                    if(content.equalsIgnoreCase("!cancel")){

                                    }else
                                    if(content.isBlank()){

                                    }else{
                                        if(command.equalsIgnoreCase("channel_set")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setChannel("set",e.getMessage());
                                            }else{
                                                setChannel(gNewUserProfile.gUserProfile.getMember(),"set",e.getMessage());
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

                            },2, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }else{
                    if(mode==0){
                        mode=gNewUserProfile.cTIMEOUT.getTimeOutMode();
                        logger.info(fName+"mode="+mode);
                    }
                    if(mode==1){
                        if(command.equalsIgnoreCase("duration")){
                            embed.setDescription("Please enter time to set duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                        else if(command.equalsIgnoreCase("add")){
                            embed.setDescription("Please enter time. to add to duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                        else if(command.equalsIgnoreCase("sub")){
                            embed.setDescription("Please enter time to subtract from duration.\n"+iTimeout.strSettingTimerFormat);

                        }
                    }
                    if(mode==2){
                        if(command.equalsIgnoreCase("count")){
                            embed.setDescription("Please enter an integer number to set the count of messages.");
                        }
                        else if(command.equalsIgnoreCase("message")){
                            embed.setDescription("Please enter an the message they need to post n times.");
                        }
                        else if(command.equalsIgnoreCase("add")){
                            embed.setDescription("Please enter an integer number to add to the count.");
                        }
                        else if(command.equalsIgnoreCase("sub")){
                            embed.setDescription("Please enter an integer number to subtract from the count.");
                        }
                    }
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);

                                    if(content.equalsIgnoreCase("!cancel")){

                                    }else
                                    if(content.isBlank()){

                                    }else{
                                        if(command.equalsIgnoreCase("duration")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setDuration(content);
                                            }else{
                                                setDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("count")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setCount(content);
                                            }else{
                                                setCount(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("message")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                setMessage(content);
                                            }else{
                                                setMessage(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("add")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                add(content);
                                            }else{
                                                add(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                        else if(command.equalsIgnoreCase("sub")){
                                            if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                                sub(content);
                                            }else{
                                                sub(gNewUserProfile.gUserProfile.getMember(),content);
                                            }
                                        }
                                    }
                                    llMessageDelete(message);
                                    menuLevels(target);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }

                            },10, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doRed(Member target){
            String fName="[doRed]";
            logger.info(fName);
            try{
                if(!(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied, only manager&admin can use this command!",llColors_Red.llColorRed_Cinnabar);
                    return;
                }
                if(target==null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                }
                Message message2=llSendQuickEmbedMessageResponse(gUser,sRTitle,"You're about to use the safeword on "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s timout. Are you sure?", llColorPurple2);
                message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple)).complete();
                message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple)).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message2.getId())&&e.getUserId().equalsIgnoreCase(gMember.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))){
                                    logger.info(fName + ".approved");
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeOut,nOn,false);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeOut,nTimeOutMode,0);
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put(nTimeOut1Duration,0);jsonObject.put(nTimeOut1Timestamp,0);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeOut,nTimeoutModeTimer,jsonObject);
                                    jsonObject=new JSONObject();
                                    jsonObject.put(nTimeOut2Count,0);jsonObject.put(nTimeOut2Done,0);jsonObject.put(nTimeOut2Sentence,"");
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeOut,nTimeoutModeWritting,jsonObject);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeOut,nChannel,0);
                                    saveProfile();
                                    if(target==null){
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on yours timeout.", llColorPurple2);
                                    }else{
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s timeout.", llColorPurple2);
                                        llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" used their power to stop your timeout.", llColorPurple2);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                    logger.info(fName + ".reject");
                                }
                                llMessageDelete(message2);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                            llMessageDelete(message2);
                        });

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
        long milliseconds_day=86400000;
        long milliseconds_week=604800000;
        long milliseconds_hour=3600000;
        long milliseconds_minute=60000;
        boolean isPatreon=false;
        private long String2Timeset4Duration(String str){
            String fName = "[String2Timeset4Duration]";
            //Logger logger = Logger.getLogger(fName);
            logger.info(fName+"str="+str);
            try{
                long timeset = 0;
                String []items = str.split("\\s+");
                logger.info(fName + ".items.size=" + items.length);
                for (int i = 0; i < items.length; i++) {
                    logger.info(fName + ".iteme=" + items[i]);
                    if (items[i].equalsIgnoreCase("w") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                    }
                    if (items[i].equalsIgnoreCase("d") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                    }
                    if (items[i].equalsIgnoreCase("h") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                    }
                    if (items[i].equalsIgnoreCase("m") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_minute;
                    }
                }
                logger.info(fName + ".timeset=" +timeset);
                return  timeset;
            }
            catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
        }


        private void setGuildChannel(String command,Message message) {
            String fName = "[setGuildChannel]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied! Require manage server permission.", llColors_Red.llColorRed_Cinnabar);
                    return;
                }
                if(command.equalsIgnoreCase("set")){
                    List<TextChannel> textChannelList=message.getMentionedChannels();
                    if(textChannelList.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"No channel mentioned!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    TextChannel textChannel=textChannelList.get(0);
                    if(!textChannel.isNSFW()){
                        logger.warn(fName+"can't be sfw channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"The channel must be NSFW!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    gBDSMCommands.timeout.gProfile.jsonObject.put(gBDSMCommands.timeout.keyChannel,textChannel.getIdLong());
                    if(!gBDSMCommands.timeout.save()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set server targeted channel to: "+textChannel.getAsMention()+".", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("clear")){
                    gBDSMCommands.timeout.gProfile.jsonObject.put(gBDSMCommands.timeout.keyChannel,0);
                    if(!gBDSMCommands.timeout.save()){
                        logger.error(fName+"failed save");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Removed server targeted channel", llColors.llColorPurple1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }


}}
