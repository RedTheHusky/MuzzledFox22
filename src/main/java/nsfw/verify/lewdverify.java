package nsfw.verify;
//implemented Runnable
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ll.llMessageHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsChannelHelper.lsGetTextChannelMention;

public class lewdverify extends Command implements llMessageHelper, llGlobalHelper,iLewdverify {
    lcGlobalHelper gGlobal;
   
    Logger logger = Logger.getLogger(getClass());


    public lewdverify(lcGlobalHelper g) {
        String fName = "[constructor]";
        logger.info(fName);
        gGlobal = g;
       
        this.name = "Lewd-verify";
        this.help = "Performing diaper or chastity verification";
        this.aliases = new String[]{gCommand, "lewdverify"};
        this.guildOnly = true;
        this.category = llCommandCategory_NSFW;
    }

    public lewdverify(lcGlobalHelper g, SlashCommandEvent event) {
        String fName = "[constructor]";
        logger.info(fName);
        gGlobal = g;
        gGlobal.waiter = g.waiter;
        Runnable r = new runLocal(event);new Thread(r).start();
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName = "[execute]";
        logger.info(fName);
        if (llDebug) {
            logger.info(fName + ".global debug true");
            return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }

    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        SlashCommandEvent gSlashCommandEvent;
        private User gUser;
        private Member gMember, gTarget;
        private Guild gGuild;
        private TextChannel gTextChannel;
        String[] gItems;
        lcJSONUserProfile gUserProfile;
        public runLocal(CommandEvent ev) {
            String fName="build";logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();
            gMember = gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        }
        public runLocal(SlashCommandEvent ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            gSlashCommandEvent = ev;
            gUser = ev.getUser();
            logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gMember=ev.getMember();
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
            }
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"verify",gGlobal);
                gBasicFeatureControl.initProfile();
                initGuildProfile();
                if(gSlashCommandEvent!=null){
                    logger.info(cName+fName+"slash jda@");
                    if(!isNSFW()){
                        blocked();return;
                    }
                    SlashNT();
                }
                else{
                    logger.info(cName+fName+"basic@");

                    boolean isInvalidCommand=true;

                    logger.info(".run ended");
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        if(!isNSFW()){
                            blocked();return;
                        }
                        menuMain();
                       isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        gItems = gEvent.getArgs().split("\\s+");

                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand=false;
                        }else{
                            isTargeted();
                            if(gItems[0].equalsIgnoreCase("help")){
                                help("main"); isInvalidCommand=false;
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
                                            setChannel(type,action,gEvent.getMessage());
                                        }
                                    }
                                    else if(group==2){
                                        if(action==0){
                                            getRoles(type,false);isInvalidCommand=false;
                                        }else{
                                            setRole(type,action,gEvent.getMessage());
                                        }
                                    }
                                }else{
                                    menuGuild();isInvalidCommand=false;
                                }
                            }
                            else if(!isNSFW()){
                                blocked();return;
                            }
                            else if(!gBasicFeatureControl.getEnable()){
                                logger.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                                return;
                            }
                            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                logger.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                                return;
                            }
                            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                logger.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                                return;
                            }else
                                switch (gItems[0].toLowerCase()){
                                    case"menu":
                                        menuMain();isInvalidCommand=false;
                                        break;
                                    case"generic":
                                        requestGenericVerification();isInvalidCommand=false;
                                        break;
                                    case"chastity":
                                        requestChastityVerification();isInvalidCommand=false;
                                        break;
                                    case"diaper":
                                        requestDiaperVerification();isInvalidCommand=false;
                                        break;
                                    case"collar":
                                        requestCollarVerification();isInvalidCommand=false;
                                        break;
                                    case"lastgeneric":
                                        getLastGenericVerification();isInvalidCommand=false;
                                        break;
                                    case"lastchastity":
                                        getLastChastityVerification();isInvalidCommand=false;
                                        break;
                                    case"lastdiaper":
                                        getLastDiaperVerification();isInvalidCommand=false;
                                        break;
                                    case"lastcollar":
                                        getLastCollarVerification();isInvalidCommand=false;
                                        break;
                                    case"dmgeneric":
                                        requestDMChastityVerification();isInvalidCommand=false;
                                        break;
                                    case"dmchastity":
                                        requestDMChastityVerification();isInvalidCommand=false;
                                        break;
                                    case"dmdiaper":
                                        requestDMDiaperVerification();isInvalidCommand=false;
                                        break;
                                    case"dmcollar":
                                        requestDMCollarVerification();isInvalidCommand=false;
                                        break;
                                    case"privategeneric":
                                        requestPrivateGenericVerification();isInvalidCommand=false;
                                        break;
                                    case"privatechastity":
                                        requestPrivateChastityVerification();isInvalidCommand=false;
                                        break;
                                    case"privatediaper":
                                        requestPrivateDiaperVerification();isInvalidCommand=false;
                                        break;
                                    case"privatecollar":
                                        requestPrivateCollarVerification();isInvalidCommand=false;break;
                                }

                        }
                    }
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean isNSFW(){
            String fName = "[isNSFW]";
            if(gTextChannel.isNSFW()){
                return true;
            }
            if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
                return true;
            }
            return false;
        }
        private void blocked(){
            String fName = "[blocked]";
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setDescription("Require NSFW channel or server.").setColor(llColorRed);
            if(gSlashCommandEvent!=null){
                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true);
            }else{
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String desc="N/a";
            String quickSummonWithSpace=llPrefixStr+"verify ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            if(command.equalsIgnoreCase("setup")){
                //embed.addField("Chastity Verification Channel", "`" + quickSummonWithSpace + "set4chastity <#TextChannel>", false);
                //embed.addField("Diaper Verification Channel", "`" + quickSummonWithSpace + "set4diaper <#TextChannel>", false);
            }else {
                embed.addField("Verification", "`" + quickSummonWithSpace + "chastity <@Mention>`"+"\n`" + quickSummonWithSpace + "diaper <@Mention>`"+"\n`" + quickSummonWithSpace + "collar <@Mention>`", false);
                embed.addField("How is it done?", "The bot will ask you to submit an attachment in any of the text channel of the server(guild) in order to perform verification.\nAn verification image should include the chastity device or diaper secured around the person and written down the 6 digit random code.", false);
                embed.addField("Get Last Verification", "`" + quickSummonWithSpace + "lastchastity <@Mention>`"+"\n`" + quickSummonWithSpace + "lastdiaper <@Mention>`"+"\n`" + quickSummonWithSpace + "lastcollar <@Mention>`", false);
                if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);

                //embed.addField("Setup", "`" + quickSummonWithSpace + "setup", false);
            }

            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                if(gSlashCommandEvent==null&&!isMenu)lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gUserProfile.jsonObject =gVerify.getJSON();
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(llMemberProfile)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        private Boolean loadedProfile(Member member){
            String fName="[loadedProfile]";
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.getMember()!=null&&gUserProfile.getMember().getIdLong()==member.getIdLong()){
                logger.info(fName + ".already present>skip");
                logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
                return true;
            }
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild,profileName);
                if(gUserProfile.getProfile(llMemberProfile)){
                    logger.info(fName + ".has sql entry");
                }
            }
            safetyUserProfileEntry();
            logger.info(fName + ".isUpdated="+gUserProfile.isUpdated);
            if(gUserProfile.getMember()==null){
                logger.error(fName+"gMember is null");
            }
            gVerify.set(gUserProfile.jsonObject);
            if(!gUserProfile.isUpdated){
                gGlobal.putUserProfile(gUserProfile,profileName);
                logger.info(fName + ".no update>ignore");
                return true;
            }
            logger.info(fName + ".is updated");
            if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
            return true;
        }

        private void safetyUserProfileEntry(){
            String fName="[safetyUserProfileEntry]";
            Logger logger = Logger.getLogger(fName);
            try{
                logger.info(fName+".safety check");
                String field;
                field=fieldGeneric;
                gUserProfile.safetyCreateFieldEntry(field);
                gUserProfile.safetyPutFieldEntry(field,keyCode ,"");
                //gUserProfile.safetyPutFieldEntry(field,keyUrl  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyImg  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyTimeStamp  ,0);
                gUserProfile.safetyPutFieldEntry(field,keyName  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyExt  ,"");
                field=fieldChastity;
                gUserProfile.safetyCreateFieldEntry(field);
                gUserProfile.safetyPutFieldEntry(field,keyCode ,"");
                //gUserProfile.safetyPutFieldEntry(field,keyUrl  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyImg  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyTimeStamp  ,0);
                gUserProfile.safetyPutFieldEntry(field,keyName  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyExt  ,"");
                field=fieldDiaper;
                gUserProfile.safetyCreateFieldEntry(field);
                gUserProfile.safetyPutFieldEntry(field,keyCode ,"");
                //gUserProfile.safetyPutFieldEntry(field,keyUrl  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyImg  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyTimeStamp  ,0);
                gUserProfile.safetyPutFieldEntry(field,keyName  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyExt  ,"");
                field=fieldCollar;
                gUserProfile.safetyCreateFieldEntry(field);
                gUserProfile.safetyPutFieldEntry(field,keyCode ,"");
                //gUserProfile.safetyPutFieldEntry(field,keyUrl  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyImg  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyTimeStamp  ,0);
                gUserProfile.safetyPutFieldEntry(field,keyName  ,"");
                gUserProfile.safetyPutFieldEntry(field,keyExt  ,"");

            }catch (Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        entityLewdverify gVerify=new entityLewdverify();
        lcJSONGuildProfile gProfileVerification;


        private void getLastChastityVerification(){
            String fName = "[getLastChastityVerification]";
            logger.info(fName);
            try {
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                String desc="";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(!isPatreon){
                    desc="Only patreon supports allowed to use this feature!";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).complete();
                    }else{
                        gTextChannel.sendMessage(desc).complete();
                    }
                    return;
                }
                String code="",time="",verificationFile="",verificationExt="png";
                if(gVerify.chastity.hasCode()){
                    code=gVerify.chastity.getCode();
                    time=lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.chastity.getTimeStamp());
                    verificationFile=gVerify.chastity.getImg();
                    verificationExt=gVerify.chastity.getExt();
                }

                File file=null;
                if(verificationFile!=null&&!verificationFile.isBlank()&&!verificationFile.isEmpty()){
                    String path=pathLSave4Chastity.replaceAll("userid",selectedMember.getId())+"/"+ verificationFile;
                    logger.info(fName+"path="+path);
                    file=new File( path);
                }
                if(file!=null&&file.exists()){
                    desc="Here is "+gUserProfile.getUser().getAsMention()+" latest chastity verification with code "+code+" at "+time+".";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).addFile(file,code+"."+verificationExt).complete();
                    }else{
                        gTextChannel.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }
                }else{
                    desc="No chastity verification image to display for "+selectedMember.getAsMention()+".";
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle);
                    embedBuilder.setColor(llColorRed_Barn);
                    embedBuilder.setDescription(desc);
                    if(interactionHook!=null){
                        interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    }else{
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getLastDiaperVerification(){
            String fName = "[getLastDiaperVerification]";
            logger.info(fName);
            try {
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                String code="",time="",verificationFile="",verificationExt="png";
                String desc="";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(!isPatreon){
                    desc="Only patreon supports allowed to use this feature!";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).complete();
                    }else{
                        gTextChannel.sendMessage(desc).complete();
                    }
                    return;
                }
                if(gVerify.diaper.hasCode()){
                    code=gVerify.diaper.getCode();
                    time=lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.diaper.getTimeStamp());
                    verificationFile=gVerify.diaper.getImg();
                    verificationExt=gVerify.diaper.getExt();
                }

                File file=null;

                if(verificationFile!=null&&!verificationFile.isBlank()&&!verificationFile.isEmpty()){
                    String path=pathLSave4Diaper.replaceAll("userid",selectedMember.getId())+"/"+ verificationFile;
                    logger.info(fName+"path="+path);
                    file=new File(path);
                }
                if(file!=null&&file.exists()){
                    desc="Here is "+gUserProfile.getUser().getAsMention()+" latest diaper verification with code "+code+" at "+time+".";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).addFile(file,code+"."+verificationExt).complete();
                    }else{
                        gTextChannel.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }
                }else{
                    desc="No diaper verification image to display for "+selectedMember.getAsMention()+".";
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle);
                    embedBuilder.setColor(llColorRed_Barn);
                    embedBuilder.setDescription(desc);
                    if(interactionHook!=null){
                        interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    }else{
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getLastCollarVerification(){
            String fName = "[getLastCollarVerification]";
            logger.info(fName);
            try {
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                String code="",time="",verificationFile="",verificationExt="png";
                String desc="";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(!isPatreon){
                    desc="Only patreon supports allowed to use this feature!";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).complete();
                    }else{
                        gTextChannel.sendMessage(desc).complete();
                    }
                    return;
                }
                if(gVerify.collar.hasCode()){
                    code=gVerify.collar.getCode();
                    time=lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.collar.getTimeStamp());
                    verificationFile=gVerify.collar.getImg();
                    verificationExt=gVerify.collar.getExt();
                }

                File file=null;

                if(verificationFile!=null&&!verificationFile.isBlank()&&!verificationFile.isEmpty()){
                    String path=pathLSave4Collar.replaceAll("userid",selectedMember.getId())+"/"+ verificationFile;
                    logger.info(fName+"path="+path);
                    file=new File(path);
                }
                if(file!=null&&file.exists()){
                    desc="Here is "+gUserProfile.getUser().getAsMention()+" latest collar verification with code "+code+" at "+time+".";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).addFile(file,code+"."+verificationExt).complete();
                    }else{
                        gTextChannel.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }
                }else{
                    desc="No collar verification image to display for "+selectedMember.getAsMention()+".";
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle);
                    embedBuilder.setColor(llColorRed_Barn);
                    embedBuilder.setDescription(desc);
                    if(interactionHook!=null){
                        interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    }else{
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getLastGenericVerification(){
            String fName = "[getLastGenericVerification]";
            logger.info(fName);
            try {
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                String code="",time="",verificationFile="",verificationExt="png";
                String desc="";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(!isPatreon){
                    desc="Only patreon supports allowed to use this feature!";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).complete();
                    }else{
                        gTextChannel.sendMessage(desc).complete();
                    }
                    return;
                }
                if(gVerify.generic.hasCode()){
                    code=gVerify.generic.getCode();
                    time=lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.generic.getTimeStamp());
                    verificationFile=gVerify.generic.getImg();
                    verificationExt=gVerify.generic.getExt();
                }

                File file=null;

                if(verificationFile!=null&&!verificationFile.isBlank()&&!verificationFile.isEmpty()){
                    String path=pathLSave4Generic.replaceAll("userid",selectedMember.getId())+"/"+ verificationFile;
                    logger.info(fName+"path="+path);
                    file=new File(path);
                }
                if(file!=null&&file.exists()){
                    desc="Here is "+gUserProfile.getUser().getAsMention()+" latest verification with code "+code+" at "+time+".";
                    if(interactionHook!=null){
                        interactionHook.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.reply(desc).addFile(file,code+"."+verificationExt).complete();
                    }else{
                        gTextChannel.sendMessage(desc).addFile(file,code+"."+verificationExt).complete();
                    }
                }else{
                    desc="No verification image to display for "+selectedMember.getAsMention()+".";
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle);
                    embedBuilder.setColor(llColorRed_Barn);
                    embedBuilder.setDescription(desc);
                    if(interactionHook!=null){
                        interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                    }else
                    if(gSlashCommandEvent!=null){
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    }else{
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        InteractionHook interactionHook=null;
        private void requestChastityVerification(){
            String fName = "[requestChastityVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;String strMessage="";
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    strMessage=selectedMember.getAsMention()+ " please submit your chastity verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }else{
                    strMessage=selectedMember.getAsMention()+ " is requested to perform chastity verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorBlue1).setDescription(strMessage);
                if(interactionHook!=null){
                    message=interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                }else
                if(gSlashCommandEvent!=null){
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    message=interactionHook.retrieveOriginal().complete();
                }else{
                    message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                InteractionHook finalInteractionHook = interactionHook;
                String finalStrMessage = strMessage;
               requestChastityVerificationListener(message,randomCode);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestDiaperVerification(){
            String fName = "[requestDiaperVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                String strMessage="";
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    strMessage=selectedMember.getAsMention()+ " please submit your diaper verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }else{
                    strMessage=selectedMember.getAsMention()+ " is requested to perform diaper verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorBlue1).setDescription(strMessage);
                if(interactionHook!=null){
                    message=interactionHook.sendMessageEmbeds(embedBuilder.build()).complete();
                }else
                if(gSlashCommandEvent!=null){
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                    message=interactionHook.retrieveOriginal().complete();
                }else{
                    message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                }
                requestDiaperVerificationListener(message,randomCode);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString()); logger.error(fName+"exception="+e);
            }
        }
        private void requestCollarVerification(){
            String fName = "[requestCollarVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;String strMessage="";
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    strMessage=selectedMember.getAsMention()+ " please submit your collared verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }else{
                    strMessage=selectedMember.getAsMention()+ " is requested to perform collared verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }
                if(interactionHook==null&&gSlashCommandEvent!=null){
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setColor(llColorBlue1).setDescription(strMessage);
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                }else{
                    message=llSendMessageResponse(gTextChannel,strMessage);
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                InteractionHook finalInteractionHook = interactionHook;
                String finalStrMessage = strMessage;
                requestCollarVerificationListener(message,randomCode);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString()); logger.error(fName+"exception="+e);
            }
        }
        private void requestGenericVerification(){
            String fName = "[requestGenericVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;String strMessage="";
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    strMessage=selectedMember.getAsMention()+ " please submit your verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }else{
                    strMessage=selectedMember.getAsMention()+ " is requested to perform verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout";
                }
                if(interactionHook==null&&gSlashCommandEvent!=null){
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setColor(llColorBlue1).setDescription(strMessage);
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                }else {
                    message=llSendMessageResponse(gTextChannel,strMessage);
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                InteractionHook finalInteractionHook = interactionHook;
                String finalStrMessage = strMessage;
                requestGenericVerificationListener(message,randomCode);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString()); logger.error(fName+"exception="+e);
            }
        }
        private void requestChastityVerificationListener(Message message,String randomCode){
            String fName = "[requestChastityVerificationListener]";
            logger.info(fName);

                gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==gUserProfile.getMember().getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Chastity.replaceAll("userid",gUserProfile.getMember().getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }

                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.chastity.setImg(fileId+"."+fileExtension);
                                            gVerify.chastity.setName( fileId);
                                            gVerify.chastity.setExt( fileExtension);
                                            gVerify.chastity.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.chastity.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(gUserProfile.getMember().getEffectiveName(),null,gUserProfile.getMember().getUser().getEffectiveAvatarUrl());
                                            Message m1=llSendMessageResponse(gTextChannel,embedBuilder);
                                            if(interactionHook !=null){
                                                if(m1!=null) {
                                                    embedBuilder.setDescription("~~"+ message.getContentRaw() +"~~");
                                                }
                                                interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                                            }
                                            if(message!=null){
                                                llMessageDelete(message);
                                            }
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Failed to process image!",null).build()).complete();
                                        else {
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to process image!");
                                            llMessageDelete(message);
                                        }
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Zero attachment!",null).build()).complete();
                                    else{
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Zero attachment!");
                                        llMessageDelete(message);
                                    }
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(message);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Chastity verification timeout for "+ gUserProfile.getMember().getAsMention(), llColorRed);
                        });

        }
        private void requestDiaperVerificationListener(Message message,String randomCode){
            String fName = "[requestDiaperVerificationListener]";
            logger.info(fName);

                gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==gUserProfile.getMember().getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Diaper.replaceAll("userid",gUserProfile.getMember().getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.diaper.setImg(fileId+"."+fileExtension);
                                            gVerify.diaper.setName( fileId);
                                            gVerify.diaper.setExt( fileExtension);
                                            gVerify.diaper.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.diaper.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(gUserProfile.getMember().getEffectiveName(),null,gUserProfile.getMember().getUser().getEffectiveAvatarUrl());
                                            Message m1=llSendMessageResponse(gTextChannel,embedBuilder);
                                            if(interactionHook !=null){
                                                if(m1!=null) {
                                                    embedBuilder.setDescription("~~"+message.getContentRaw()+"~~");
                                                }
                                                interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                                            }
                                            if(message!=null){
                                                llMessageDelete(message);
                                            }
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(message);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                llMessageDelete(message);
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Diaper verification timeout for "+ gUserProfile.getMember().getAsMention(), llColorRed);
                        });

        }
        private void requestCollarVerificationListener(Message message,String randomCode){
            String fName = "[requestCollarVerificationListener]";
            logger.info(fName);

                gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==gUserProfile.getMember().getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Collar.replaceAll("userid",gUserProfile.getMember().getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to process image!");
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.collar.setImg(fileId+"."+fileExtension);
                                            gVerify.collar.setName( fileId);
                                            gVerify.collar.setExt( fileExtension);
                                            gVerify.collar.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.collar.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(gUserProfile.getMember().getEffectiveName(),null,gUserProfile.getMember().getUser().getEffectiveAvatarUrl());
                                            Message m1=llSendMessageResponse(gTextChannel,embedBuilder);
                                            if(interactionHook !=null){
                                                if(m1!=null) {
                                                    embedBuilder.setDescription("~~"+message.getContentRaw()+"~~");
                                                }
                                                interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                                            }
                                            if(message!=null){
                                                llMessageDelete(message);
                                            }

                                        }
                                    }).exceptionally(t -> { // handle failure
                                        if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Failed to process image!",null).build()).complete();
                                        else {
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser, gTitle, "Failed to process image!");
                                            llMessageDelete(message);
                                        }
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Zero attachment!",null).build()).complete();
                                    else {
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Zero attachment!");
                                        llMessageDelete(message);
                                    }
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(message);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ gUserProfile.getMember().getAsMention(), llColorRed);
                        });

        }
        private void requestGenericVerificationListener(Message message,String randomCode){
            String fName = "[requestGenericVerificationListener]";
            logger.info(fName);

                gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==gUserProfile.getMember().getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Generic.replaceAll("userid",gUserProfile.getMember().getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to process image!");
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.generic.setImg(fileId+"."+fileExtension);
                                            gVerify.generic.setName( fileId);
                                            gVerify.generic.setExt( fileExtension);
                                            gVerify.generic.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.generic.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(gUserProfile.getMember().getEffectiveName(),null,gUserProfile.getMember().getUser().getEffectiveAvatarUrl());
                                            Message m1=llSendMessageResponse(gTextChannel,embedBuilder);
                                            if(interactionHook !=null){
                                                if(m1!=null) {
                                                    embedBuilder.setDescription("~~"+message.getContentRaw()+"~~");
                                                }
                                                interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                                            }
                                            if(message!=null){
                                                llMessageDelete(message);
                                            }

                                        }
                                    }).exceptionally(t -> { // handle failure
                                        if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Failed to process image!",null).build()).complete();
                                        else {
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser, gTitle, "Failed to process image!");
                                            llMessageDelete(message);
                                        }
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    if(interactionHook!=null)interactionHook.editOriginalEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Zero attachment!",null).build()).complete();
                                    else {
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Zero attachment!");
                                        llMessageDelete(message);
                                    }
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(message);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ gUserProfile.getMember().getAsMention(), llColorRed);
                        });

        }

        private void requestDMChastityVerification(){
            String fName = "[requestDMChastityVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your chastity verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform chastity verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Chastity.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }

                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.chastity.setImg(fileId+"."+fileExtension);
                                            gVerify.chastity.setName( fileId);
                                            gVerify.chastity.setExt( fileExtension);
                                            gVerify.chastity.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.chastity.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                llMessageDelete(finalMessage);
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Chastity verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestDMDiaperVerification(){
            String fName = "[requestDMDiaperVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your diaper verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform diaper verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Diaper.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.diaper.setImg(fileId+"."+fileExtension);
                                            gVerify.diaper.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.diaper.setName( fileId);
                                            gVerify.diaper.setCode( randomCode);
                                            gVerify.diaper.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                llMessageDelete(finalMessage);
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Diaper verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestDMCollarVerification(){
            String fName = "[requestDMCollarVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your collared verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform collared verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Collar.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                           gVerify.collar.setImg(fileId+"."+fileExtension);
                                            gVerify.collar.setTimeStamp( getCurrentTimeStamp());
                                           gVerify.collar.setCode( randomCode);
                                            gVerify.collar.setName( fileId);
                                            gVerify.collar.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestDMGenericVerification(){
            String fName = "[requestDMGenericVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Generic.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.generic.setImg(fileId+"."+fileExtension);
                                            gVerify.generic.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.generic.setCode( randomCode);
                                            gVerify.generic.setName( fileId);
                                            gVerify.generic.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }

        private void requestPrivateChastityVerification(){
            String fName = "[requestPrivateChastityVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your chastity verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform chastity verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Chastity.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }

                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.chastity.setImg(fileId+"."+fileExtension);
                                            gVerify.chastity.setName( fileId);
                                            gVerify.chastity.setExt( fileExtension);
                                            gVerify.chastity.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.chastity.setCode( randomCode);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gUser,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                llMessageDelete(finalMessage);
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Chastity verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestPrivateDiaperVerification(){
            String fName = "[requestPrivateDiaperVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your diaper verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform diaper verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Diaper.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.diaper.setImg(fileId+"."+fileExtension);
                                            gVerify.diaper.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.diaper.setName( fileId);
                                            gVerify.diaper.setCode( randomCode);
                                            gVerify.diaper.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gUser,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                llMessageDelete(finalMessage);
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Diaper verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestPrivateCollarVerification(){
            String fName = "[requestPrivateCollarVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your collared verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform collared verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Collar.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.collar.setImg(fileId+"."+fileExtension);
                                            gVerify.collar.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.collar.setCode( randomCode);
                                            gVerify.collar.setName( fileId);
                                            gVerify.collar.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gUser,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void requestPrivateGenericVerification(){
            String fName = "[requestPrivateGenericVerification]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                String randomCode= lsUsefullFunctions.randomCode(6);
                Message message=null;
                if(selectedMember.getIdLong()==gMember.getIdLong()){
                    message=llSendMessageResponse(selectedMember.getUser(),"Please submit your verification picture with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }else{
                    message=llSendMessageResponse(selectedMember.getUser(),"You are requested to perform verification picture by "+gMember.getAsMention()+" with the code "+randomCode+".\nWill wait 10 minutes till timeout");
                }
                Member finalSelectedMember = selectedMember;
                Message finalMessage = message;
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().getIdLong()==finalSelectedMember.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName + "text=" + content);
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Generic.replaceAll("userid",finalSelectedMember.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }else{
                                        if(folder.mkdir()){
                                            logger.info(fName +"Directory created successfully");
                                        }else{
                                            logger.warn(fName +"Sorry couldn’t create specified directory");
                                        }
                                    }
                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                            llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gVerify.generic.setImg(fileId+"."+fileExtension);
                                            gVerify.generic.setTimeStamp( getCurrentTimeStamp());
                                            gVerify.generic.setCode( randomCode);
                                            gVerify.generic.setName( fileId);
                                            gVerify.generic.setExt( fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, gTitle, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorGreen1);
                                            embedBuilder.setDescription("Thanks for submitting verification image `"+randomCode+"`, "+e.getAuthor().getAsMention()+".");
                                            embedBuilder.setAuthor(finalSelectedMember.getEffectiveName(),null,finalSelectedMember.getUser().getEffectiveAvatarUrl());
                                            llMessageDelete(finalMessage);llSendMessage(gUser,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, gTitle, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }catch (Exception ex) {
                                llMessageDelete(finalMessage);
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                                logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                            }

                        },
                        // if the user takes more than a minute, time out
                        10, TimeUnit.MINUTES, () -> {
                            llMessageDelete(finalMessage);
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Collar verification timeout for "+ finalSelectedMember.getAsMention(), llColorRed);
                        });
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }

        private Boolean isTargeted(){
            String fName = "[isTargeted]";
            logger.info(fName);
            try{ String[] items = gEvent.getArgs().split("\\s+");
                logger.info(fName + ".items.size=" + items.length);
                Member m=null;
                if(items.length>=2&&((items[1].contains("<@")&&items[1].contains(">"))||(items[1].contains("<@!")&&items[1].contains(">")))){
                   m=lsMemberHelper.lsGetMember(gGuild,items[1]);
                    if(m!=null){
                        if(m.getId().equals(gUser.getId())){
                            logger.info(fName + ".target same");
                            return false;
                        }
                        logger.info(fName + ".target ok");
                        gTarget=m;
                        return true;
                    }
                }
                if((items[0].contains("<@")&&items[0].contains(">"))||(items[0].contains("<@!")&&items[0].contains(">"))){
                    m=lsMemberHelper.lsGetMember(gGuild,items[0]);
                    if(m!=null){
                        if(m.getId().equals(gUser.getId())){
                            logger.info(fName + ".target same");
                            return false;
                        }
                        logger.info(fName + ".target ok");
                        gTarget=m;
                        return true;
                    }
                }
                logger.info(fName + ".target none");
                return false;
            }
            catch(Exception ex){
                logger.error(fName + ".exception: "+ex);
                return false;
            }
        }
        private long getCurrentTimeStamp(){
            String fName = "[getCurrentTimeStamp]";
            logger.info(fName);
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                return timestamp.getTime();
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        private void setChastityChannel(){
            String fName = "[setChastityChannel]";
            logger.info(fName);
            try {
               List<TextChannel>textChannels=gEvent.getMessage().getMentionedChannels();
                TextChannel textChannel=null;
               if(textChannels.isEmpty()){
                    gProfileVerification.putFieldEntry(keyChastityChannel,"");
                   logger.info(fName+"channel=null");
               }else{
                   textChannel=textChannels.get(0);
                   gProfileVerification.putFieldEntry(keyChastityChannel,textChannel.getId());
                   logger.info(fName+"channel="+textChannel.getId()+"|"+textChannel.getName());
               }
               if(!saveGuildProfile()){
                   llSendQuickEmbedMessage(gUser,gTitle,"Failed to update settings!", llColorRed);return;
               }
                if(textChannels.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No submission channel set for chastity verification!", llColorPink1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Submission channel for chastity verification set to "+textChannel.getAsMention(), llColorPink1);
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setDiaperChannel(){
            String fName = "[setDiaperChannel]";
            logger.info(fName);
            try {
                List<TextChannel>textChannels=gEvent.getMessage().getMentionedChannels();
                TextChannel textChannel=null;
                if(textChannels.isEmpty()){
                    gProfileVerification.putFieldEntry(keyDiaperChannel,"");
                    logger.info(fName+"channel=null");
                }else{
                    textChannel=textChannels.get(0);
                    gProfileVerification.putFieldEntry(keyDiaperChannel,textChannel.getId());
                    logger.info(fName+"channel="+textChannel.getId()+"|"+textChannel.getName());
                }
                if(!saveGuildProfile()){
                    llSendQuickEmbedMessage(gUser,gTitle,"Failed to update settings!", llColorRed);return;
                }
                if(textChannels.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No submission channel set for diaper verification!", llColorPink1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Submission channel for diaper verification set to "+textChannel.getAsMention(), llColorPink1);
                }
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setChastityAutoPost(boolean enabled){
            String fName = "[setChastityAutoPost]";
            logger.info(fName);
            try {
                logger.info(fName+"enabled="+enabled);
                gProfileVerification.putFieldEntry(keyChastityAutoPost,enabled);
                if(!saveGuildProfile()){
                    llSendQuickEmbedMessage(gUser,gTitle,"Failed to update settings!", llColorRed);return;
                }
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Chastity verification auto posting set to: "+enabled, llColorPink1);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setDiaperAutoPost(boolean enabled){
            String fName = "[setDiaperAutoPost]";
            logger.info(fName);
            try {
                logger.info(fName+"enabled="+enabled);
                gProfileVerification.putFieldEntry(keyDiaperAutoPost,enabled);
                if(!saveGuildProfile()){
                    llSendQuickEmbedMessage(gUser,gTitle,"Failed to update settings!", llColorRed);return;
                }
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Diaper verification auto posting set to: "+enabled, llColorPink1);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getSettings(){
            String fName = "[setDiaperAutoPost]";
            logger.info(fName);
            try {
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorBlue1);
                embedBuilder.addField("Chastity","Auto Posting: "+gProfileVerification.jsonObject.getBoolean(keyChastityAutoPost)+"\nChannel: "+lsGetTextChannelMention(gGuild, gProfileVerification.jsonObject.getString(keyChastityChannel)),false);
                embedBuilder.addField("Diaper","Auto Posting: "+gProfileVerification.jsonObject.getBoolean(keyDiaperAutoPost)+"\nChannel: "+lsGetTextChannelMention(gGuild, gProfileVerification.jsonObject.getString(keyChastityChannel)),false);
                llSendMessage(gTextChannel,embedBuilder);
            } catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        void initGuildProfile(){
            String fName="[initGuildProfile]";
            logger.info(fName+".safety check");
            try{
                gProfileVerification=gGlobal.getGuildSettings(gGuild, profileName);
                if(gProfileVerification==null||!gProfileVerification.isExistent()||gProfileVerification.jsonObject.isEmpty()){
                    gProfileVerification=new lcJSONGuildProfile(gGlobal, profileName,gGuild,llv2_GuildsSettings);
                    gProfileVerification.getProfile(llv2_GuildsSettings);
                    if(!gProfileVerification.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild,gProfileVerification);
                    }
                }
                iGuildProfileSafety();
                if(gProfileVerification.isUpdated){
                    gGlobal.putGuildSettings(gGuild,gProfileVerification);
                    if(gProfileVerification.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        boolean saveGuildProfile(){
            String fName="[saveGuildProfile]";
            logger.info(fName+".safety check");
            try{
                 gGlobal.putGuildSettings(gGuild,gProfileVerification);
                 if(gProfileVerification.saveProfile()){
                     logger.info(fName + ".success save to db");return true;
                 }else{
                     logger.error(fName + ".error save to db");return false;
                 }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        String keyChastityChannel="chastityChannel",keyDiaperChannel="diaperChannel",keyCollarChannel="collarChannel";
        String keyChastityAutoPost="chastityAutoPost",keyDiaperAutoPost="diaperAutoPost",keyCollarAutoPost="collarAutoPost";

        void iGuildProfileSafety(){
            String fName="iSafetyCreate";
            try {
                String field= keyChastityChannel;
                gProfileVerification.safetyPutFieldEntry(field, "");
                field= keyDiaperChannel;
                gProfileVerification.safetyPutFieldEntry(field,"");
                field= keyCollarChannel;
                gProfileVerification.safetyPutFieldEntry(field,"");
                field= keyChastityAutoPost;
                gProfileVerification.safetyPutFieldEntry(field, true);
                field= keyDiaperAutoPost;
                gProfileVerification.safetyPutFieldEntry(field,true);
                field= keyCollarAutoPost;
                gProfileVerification.safetyPutFieldEntry(field,true);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBasicFeatureControl.setEnable(enable);
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTitle(gTitle+" Options");
                embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBasicFeatureControl.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    help("main");return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }

        private void SlashNT() {
            String fName = "[SlashNT]";
            logger.info(fName);
            if(!gSlashCommandEvent.isFromGuild()){
                gSlashCommandEvent.replyEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Only available in guild!",null).build()).setEphemeral(true).complete();
                return;
            }
            if(!gTextChannel.isNSFW()){
                gSlashCommandEvent.replyEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Require NSFW channel",null).build()).setEphemeral(true).complete();
                return;
            }
            List<OptionMapping> options=gSlashCommandEvent.getOptions();
            if(options.isEmpty()){
                interactionHook=gSlashCommandEvent.reply("Opening menu").setEphemeral(true).complete();
                menuMain();
                return;
            }
            String valueOption="";
            User user=null;Member member=null;
            boolean valueLast=false,gotLast=false;
            for(OptionMapping option:options){
                String name=option.getName();
                logger.info(fName+"option.name="+name);
                switch (name){
                    case  "option":
                        if(option.getType()== OptionType.STRING){
                            valueOption=option.getAsString();
                        }
                        break;
                    case "last":
                        if(option.getType()== OptionType.BOOLEAN){
                            valueLast=option.getAsBoolean();
                            gotLast=true;
                        }
                        break;
                    case "user":
                        if(option.getType()== OptionType.USER){
                            user=option.getAsUser();
                            member=option.getAsMember();
                        }
                        break;
                }

            }
            if(member!=null&&member.getIdLong()!=gMember.getIdLong()){
                gTarget=member;
            }
            if(gotLast&&valueLast){
                switch (valueOption){
                    case "chastity":
                        getLastChastityVerification();
                        break;
                    case "collar":
                        getLastCollarVerification();
                        break;
                    case "diaper":
                        getLastDiaperVerification();
                        break;
                    case "generic":
                        getLastGenericVerification();
                        break;
                    default:
                        interactionHook=gSlashCommandEvent.reply("Opening menu").setEphemeral(true).complete();
                        menuMain();
                }
            }else{
                switch (valueOption){
                    case "chastity":
                        requestChastityVerification();
                        break;
                    case "collar":
                        requestCollarVerification();
                        break;
                    case "diaper":
                        requestDiaperVerification();
                        break;
                    case "generic":
                        requestGenericVerification();
                        break;
                    default:
                        gSlashCommandEvent.reply("Opening menu").setEphemeral(true).complete();
                        menuMain();
                }
            }

        }
        boolean isMenu=false,isPatreon=false;
        public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
        String gCommandFileMainPath="resources/json/nsfw/verify/mainMenu.json";
        private void menuMain(){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                Member selectedMember=null;
                if(gTarget==null) {
                    logger.info(fName + ".me");
                    selectedMember=gMember;
                }else{
                    logger.info(fName + ".them");
                    selectedMember=gTarget;
                }
                if (!loadedProfile(selectedMember)) {
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
                embed.addField(lsGlobalHelper.strSupportTitle,lsGlobalHelper.strSupport,false);
                isMenu=true;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(isPatreon){
                    if(gUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                        embed.setTitle(gTitle+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gUserProfile.getMember().getUser().getName()+"'s "+gTitle+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }
                }else{
                    if(gUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                        embed.setTitle(gTitle+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }else{
                        embed.setTitle(gUserProfile.getMember().getUser().getName()+"'s "+gTitle+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                }
                desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" generic";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" chastity";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" collar";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" diaper";
                embed.addField("Verification",desc,false);
                desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" generic";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)+" chastity";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" collar";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)+" diaper";
                embed.addField("Private Verification",desc,false);
                desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" generic";
                if(gVerify.chastity.hasCode()){
                    desc+="\n-last generic on: "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.generic.getTimeStamp());
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" chastity";
                if(gVerify.chastity.hasCode()){
                    desc+="\n-last chastity on: "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.chastity.getTimeStamp());
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" collar";
                if(gVerify.chastity.hasCode()){
                    desc+="\n-last collar on: "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.collar.getTimeStamp());
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" diaper";
                if(gVerify.chastity.hasCode()){
                    desc+="\n-last diaper on: "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(gVerify.diaper.getTimeStamp());
                }
                embed.addField("Get Verification","Request to be patreon supporter\n"+desc,false);
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component2=messageComponentManager.messageBuildComponents.getComponent(2);
                    if(!isPatreon){
                        component2.getButtonAt4(0).setDisable();
                        component2.getButtonAt4(1).setDisable();
                        component2.getButtonAt4(2).setDisable();
                        component2.getButtonAt4(3).setDisable();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuMainListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private boolean stopAsDenied(){
            String fName="[stopAsDenied";
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                return true;
            }
            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                logger.info(fName+"its not allowed by channel");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                return true;
            }
            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                logger.info(fName+"its not allowed by roles");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                return true;
            }
            return false;
        }
        private void menuMainListener(Message message){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                    help("main");
                                    return;
                                }
                                if(stopAsDenied())return;
                                switch (id){
                                    case lsUnicodeEmotes.alias1:
                                        requestGenericVerification();
                                        break;
                                    case lsUnicodeEmotes.alias2:
                                        requestChastityVerification();
                                        break;
                                    case lsUnicodeEmotes.alias3:
                                        requestCollarVerification();
                                        break;
                                    case lsUnicodeEmotes.alias4:
                                        requestDiaperVerification();
                                        break;
                                   case lsUnicodeEmotes.alias5:
                                        requestPrivateGenericVerification();
                                        break;
                                   case lsUnicodeEmotes.alias6:
                                        requestPrivateChastityVerification();
                                        break;
                                   case lsUnicodeEmotes.alias7:
                                        requestPrivateCollarVerification();
                                        break;
                                   case lsUnicodeEmotes.alias8:
                                        requestPrivateDiaperVerification();
                                        break;
                                   case lsUnicodeEmotes.aliasSymbolA:
                                        getLastGenericVerification();
                                        break;
                                   case lsUnicodeEmotes.aliasSymbolB:
                                        getLastChastityVerification();
                                        break;
                                   case lsUnicodeEmotes.aliasSymbolC:
                                        getLastCollarVerification();
                                        break;
                                   case lsUnicodeEmotes.aliasSymbolD:
                                        getLastDiaperVerification();
                                        break;

                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));

                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);

                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    help("main");return;
                                }
                                if(stopAsDenied())return;
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))) {
                                    requestGenericVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                   requestChastityVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))) {
                                    requestCollarVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))) {
                                   requestDiaperVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))) {
                                    requestPrivateGenericVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))) {
                                    requestPrivateChastityVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))) {
                                    requestPrivateCollarVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))) {
                                    requestPrivateDiaperVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))) {
                                    getLastGenericVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))) {
                                    getLastChastityVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))) {
                                    getLastCollarVerification();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))) {
                                    getLastDiaperVerification();
                                }
                                else{
                                    menuMain();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }


    }

    public lewdverify(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        Runnable r = new runUtility(global,guild,user,command);
        new Thread(r).start();
    }
    protected class runUtility implements Runnable {
        String cName = "[runReset]";
        String profileName="lewdverification";
        lcGlobalHelper gGlobal;
        Guild gGuild;
        User gUser;
        String gCommand;

        public runUtility(lcGlobalHelper global, Guild g, User user,String command) {
            logger.info(".run build");
            gGlobal = global;
            gUser=user;
            gGuild = g;
            gCommand=command;

        }
        lcJSONUserProfile gUserProfile;
        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                if(gCommand.equalsIgnoreCase("reset")){
                    getProfile();
                    gUserProfile.jsonObject =new JSONObject();
                    saveProfile();
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private Boolean getProfile(){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
            gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
            if(gUserProfile.getProfile(llMemberProfile)){
                logger.info(fName + ".has sql entry");  return true;
            }
            return false;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(llMemberProfile)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
    }
}
