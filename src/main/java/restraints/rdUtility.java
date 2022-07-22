package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import interaction.usercommand.messagecommandControler;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.emotes.lcEmote;
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
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.in.iUtility;
import restraints.models.entity.entityGagPost;
import restraints.models.lcBDSMGuildProfiles;
import restraints.models.rdExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdUtility extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdUtility]",gCommand="rdutility";
	String sRTitle="rd Utility";
    public rdUtility(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "utility-DiscordRestraints";
        this.help = "action";
        this.aliases = new String[]{gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public rdUtility(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        gGlobal=global;
        Runnable r = new runLocal(guild,user,command);
        new Thread(r).start();
    }
    public rdUtility(lcGlobalHelper g, lcApplicationInteractionReceive.lMessageCommand event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
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
            logger.info(".run build");String fName="runLocal";
            launch(gGlobal,ev);

        }
        public runLocal(Guild guild, User user,String command) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            launch(gGlobal,guild,user,command);
        }
        public runLocal(lcApplicationInteractionReceive.lMessageCommand ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            try {
                launch(gGlobal,ev);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                if(!gTextChannel.isNSFW()){
                    blocked();
                    return;
                }
                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    String[] items;
                    items = gRawForward.split("\\s+");
                    if(items[1].equalsIgnoreCase("reset")){
                        getProfile(gMember);
                        gNewUserProfile.reset();
                        saveProfile();
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");

                    boolean isInvalidCommand=true;
                    if(gCommandEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        rHelp("main");isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        gItems = gCommandEvent.getArgs().split("\\s+");
                        if(gCommandEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                        logger.info(fName + ".gRawForward="+gRawForward);
                        logger.info(fName + ".gRawForward:"+gRawForward.isEmpty()+"|"+gRawForward.isBlank());
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                        if(isInvalidCommand){
                            if(gItems[0].equalsIgnoreCase("user")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                }
                                else if(gItems[1].equalsIgnoreCase("reset")){
                                    getProfile(gMember);
                                    gNewUserProfile.reset();
                                    saveProfile();
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("get")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                }
                                else if(gItems[1].equalsIgnoreCase("memberprofile")){
                                    List<Member>members=gMessage.getMentionedMembers();
                                    if(members.isEmpty()){
                                        sendJSONFileOfUser(gMember);
                                    }else{
                                        sendJSONFileOfUser(members.get(0));
                                    }
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("userscaughtprofile")){
                                    sendJSONFileOfCaughtUsers(gGuild);
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("userscaughtprofileall")){
                                    sendJSONFileOfCaughtUsersAllGuilds();
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("guildprofile")){
                                    sendJSONFileOfGuildProfile(gGuild);
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("channelscaught")){
                                    sendJSONFileOfChannels(gGuild);
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("channelscaughtall")){
                                    sendJSONFileOfChannelsAllGuilds();
                                    isInvalidCommand=false;
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("clear")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                }
                                else if(gItems[1].equalsIgnoreCase("memberprofile")){
                                    List<Member>members=gMessage.getMentionedMembers();
                                    if(members.isEmpty()){
                                        clear4User(gMember);
                                    }else{
                                        clear4User(members.get(0));
                                    }
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("userscaughtprofile")){
                                    clear4Users(gGuild);
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("userscaughtprofileall")){
                                    clear4UsersAllGuilds();
                                    isInvalidCommand=false;
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("post")){
                                if(gItems.length>=3){
                                    checkPost(gItems[1],gItems[2]);
                                }else
                                if(gItems.length==2){
                                    checkPost(gItems[1],null);
                                }else{
                                    logger.warn(fName+".invalid args length");
                                }

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
            }
            logger.info(".run ended");
        }



        private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace2=llPrefixStr+gCommand;
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","just silly texts.",false);
        desc="**List of commands:**";
        desc+=newLine+quickSummonWithSpace2+"get memberprofile <@Member>"+endLine;
        desc+=newLine+quickSummonWithSpace2+"get userscaughtprofile"+endLine;
        if(lsGlobalHelper.slMemberIsBotOwner(gMember))desc+=newLine+quickSummonWithSpace2+"get userscaughtprofileall"+endLine;
        desc+=newLine+quickSummonWithSpace2+"get channelscaught"+endLine;
        if(lsGlobalHelper.slMemberIsBotOwner(gMember))desc+=newLine+quickSummonWithSpace2+"get channelscaughtall"+endLine;
        desc+=newLine+quickSummonWithSpace2+"clear memberprofile <@Member>"+endLine;
        desc+=newLine+quickSummonWithSpace2+"clear userscaughtprofile"+endLine;
            if(lsGlobalHelper.slMemberIsBotOwner(gMember))desc+=newLine+quickSummonWithSpace2+"clear userscaughtprofileall"+endLine;
        desc+=newLine+quickSummonWithSpace2+"get guildprofile"+endLine;
        embed.addField("Commands",desc,false);

        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void clear4User(Member member){
            String fName = "[clear4User]";
            logger.info(fName);
            try {
                logger.info(fName+"member="+member.getIdLong());
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                if(gGlobal.clearUserProfile(profileName,member.getUser(),gGuild)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared member "+member.getAsMention()+" catch profile for the current guild.",llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to clear member "+member.getAsMention()+" catch profile for the current guild.",llColors.llColorBlue1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }
        }
        private void clear4Users(Guild guild){
            String fName = "[clea4Users]";
            logger.info(fName);
            try {
                logger.info(fName+"guild="+guild.getId());
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                if(gGlobal.clearUserProfile(profileName,guild)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared members catch profile for the current guild.",llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to clear members  catch profile for the current guild.",llColors.llColorBlue1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }
        }
        private void clear4UsersAllGuilds(){
            String fName = "[clea4UsersAllGuilds]";
            logger.info(fName);
            try {
                if(!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                if(gGlobal.clearUserProfile(profileName)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared members catch profile for all guilds.",llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to clear members  catch profile for all guilds.",llColors.llColorBlue1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }
        }
        private void sendJSONFileOfUser(Member member){
            String fName = "[sendJSONFileOfUser]";
            logger.info(fName);
            try {
                logger.info(fName+"member="+member.getIdLong());
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,gGuild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                jsonObject.put(llCommonKeys.keyGuild,jsonGuild);
                JSONObject jsonUser=new JSONObject();
                jsonUser.put(llCommonKeys.keyId,member.getIdLong());
                jsonUser.put(llCommonKeys.keyName,member.getUser().getName());
                jsonUser.put(llCommonKeys.keyDiscriminator,member.getUser().getDiscriminator());
                jsonObject.put(llCommonKeys.keyUser,jsonUser);
                jsonObject.put(llCommonKeys.keyProfile,new JSONObject());
                try {
                    getProfile(member);
                    jsonObject.put(llCommonKeys.keyProfile,gNewUserProfile.gUserProfile.jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdMemberProfile", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }
        private void sendJSONFileOfGuildProfile(Guild guild){
            String fName = "[sendJSONFileOfGuildProfile]";
            logger.info(fName);
            try {
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,gGuild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                jsonObject.put(llCommonKeys.keyGuild,jsonGuild);
                try {
                    jsonObject.put(lcBDSMGuildProfiles.gNameRestrictions,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gNameRestrictions));
                    jsonObject.put(lcBDSMGuildProfiles.gMuzzleProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gMuzzleProfileName));
                    jsonObject.put(lcBDSMGuildProfiles.gRestrainsProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gRestrainsProfileName));
                    jsonObject.put(lcBDSMGuildProfiles.gProfileNameCollar,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameCollar));
                    jsonObject.put(lcBDSMGuildProfiles.gProfileNameTimeout,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameTimeout));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdProfileMuzzle", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }
        private void sendJSONFileOfCaughtUsers(Guild guild){
            String fName = "[sendJSONFileOfCaughtUser]";
            logger.info(fName);
            try {
                logger.info(fName+"guild="+guild.getId());
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                OffsetDateTime timeJoined=guild.getSelfMember().getTimeJoined();
                jsonGuild.put(llCommonKeys.keyTimeJoined,timeJoined.getYear()+"."+timeJoined.getMonthValue()+"."+timeJoined.getDayOfMonth());
                jsonObject.put(llCommonKeys.keyGuild,jsonGuild);
                jsonObject.put(llCommonKeys.keyMembers,new JSONObject());

                try {
                    jsonObject.put(lcBDSMGuildProfiles.gNameRestrictions,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gNameRestrictions));
                    jsonObject.put(lcBDSMGuildProfiles.gMuzzleProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gMuzzleProfileName));
                    jsonObject.put(lcBDSMGuildProfiles.gRestrainsProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gRestrainsProfileName));
                    jsonObject.put(lcBDSMGuildProfiles.gProfileNameCollar,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameCollar));
                    jsonObject.put(lcBDSMGuildProfiles.gProfileNameTimeout,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameTimeout));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    List<lcJSONUserProfile>lcprofiles=gGlobal.getUserProfile(profileName,guild);
                    logger.info(fName+"lcprofiles.size="+lcprofiles.size());
                    for (lcJSONUserProfile lcprofile : lcprofiles) {
                        try {
                            JSONObject jsonProfile = new JSONObject();
                            User user = lcprofile.getUser();
                            JSONObject jsonUser = new JSONObject();
                            jsonUser.put(llCommonKeys.keyId, user.getIdLong());
                            jsonUser.put(llCommonKeys.keyName, user.getName());
                            jsonUser.put(llCommonKeys.keyDiscriminator, user.getDiscriminator());
                            jsonProfile.put(llCommonKeys.keyUser, jsonUser);
                            jsonProfile.put(llCommonKeys.keyProfile,lcprofile.jsonObject);
                            logger.info(fName+"profile["+user.getId()+"]="+ jsonProfile);
                            if(jsonProfile.has(llCommonKeys.keyProfile)&&!jsonProfile.isNull(llCommonKeys.keyProfile)&&!jsonProfile.getJSONObject(llCommonKeys.keyProfile).isEmpty()){
                                jsonObject.getJSONObject(llCommonKeys.keyMembers).put(user.getId(), jsonProfile);
                            }
                        } catch (Exception e) {
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdCaughtUsersProfile", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }
        private void sendJSONFileOfCaughtUsersAllGuilds(){
            String fName = "[sendJSONFileOfCaughtUserAllGuilds]";
            logger.info(fName);
            try {
                if(!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                try {
                    JSONObject jsonGuilds=gGlobal.getUserProfile(profileName);
                    Iterator<String> keysGuilds=jsonGuilds.keys();
                    jsonObject.put(llCommonKeys.keyGuilds,new JSONObject());
                    JSONObject orders=new JSONObject();
                    orders.put(llCommonKeys.keyProfiles,new JSONArray());
                    orders.put(llCommonKeys.keyMembersCount,new JSONArray());
                    orders.put("none",new JSONArray());
                    while(keysGuilds.hasNext()){
                        try {
                            String idGuild=keysGuilds.next();
                            logger.info(fName + ".idGuild="+idGuild);
                            JSONObject jsonGuild=new JSONObject();JSONObject order=new JSONObject();
                            Guild guild=gGlobal.getGuild(idGuild);
                            jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                            jsonGuild.put(llCommonKeys.keyName,guild.getName());
                            jsonGuild.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                            OffsetDateTime timeJoined=guild.getSelfMember().getTimeJoined();
                            jsonGuild.put(llCommonKeys.keyTimeJoined,timeJoined.getYear()+"."+timeJoined.getMonthValue()+"."+timeJoined.getDayOfMonth());
                            jsonGuild.put(llCommonKeys.keyMembers,new JSONObject());
                            try {
                                jsonGuild.put(lcBDSMGuildProfiles.gNameRestrictions,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gNameRestrictions));
                                jsonGuild.put(lcBDSMGuildProfiles.gMuzzleProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gMuzzleProfileName));
                                jsonGuild.put(lcBDSMGuildProfiles.gRestrainsProfileName,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gRestrainsProfileName));
                                jsonGuild.put(lcBDSMGuildProfiles.gProfileNameCollar,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameCollar));
                                jsonGuild.put(lcBDSMGuildProfiles.gProfileNameTimeout,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gProfileNameTimeout));
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            jsonObject.getJSONObject(llCommonKeys.keyGuilds).put(idGuild,jsonGuild);
                            logger.info(fName + ".added guild");
                            Iterator<String> keysMembers=jsonGuilds.getJSONObject(idGuild).keys();
                            int profiles=0;
                            while(keysMembers.hasNext()){
                                try {
                                    String idMember=keysMembers.next();
                                    logger.info(fName + ".idMember="+idMember);
                                    JSONObject jsonUserProfile=new JSONObject();
                                    JSONObject jsonUser=new JSONObject();
                                    User user=lsUserHelper.lsGetUser(guild,idMember);
                                    jsonUser.put(llCommonKeys.keyId, user.getIdLong());
                                    jsonUser.put(llCommonKeys.keyName, user.getName());
                                    jsonUser.put(llCommonKeys.keyDiscriminator, user.getDiscriminator());
                                    jsonUserProfile.put(llCommonKeys.keyUser, jsonUser);
                                    jsonUserProfile.put(llCommonKeys.keyProfile, jsonGuilds.getJSONObject(idGuild).getJSONObject(idMember));
                                    if(jsonUserProfile.has(llCommonKeys.keyProfile)&&!jsonUserProfile.isNull(llCommonKeys.keyProfile)&&!jsonUserProfile.getJSONObject(llCommonKeys.keyProfile).isEmpty()){
                                        jsonGuild.getJSONObject(llCommonKeys.keyMembers).put(idMember,jsonUserProfile);
                                        profiles++;
                                    }
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                            jsonObject.getJSONObject(llCommonKeys.keyGuilds).put(idGuild,jsonGuild);
                            try {
                                order.put(llCommonKeys.keyId,guild.getIdLong());
                                order.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                                order.put(llCommonKeys.keyProfiles,profiles);
                                orders.getJSONArray(llCommonKeys.keyProfiles).put(order);
                                orders.getJSONArray(llCommonKeys.keyMembersCount).put(order);
                                orders.getJSONArray("none").put(order);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }

                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    try {
                        JSONArray array=orders.getJSONArray(llCommonKeys.keyProfiles);
                        int n=array.length();
                        for(int i=0;i<n;i++){
                           int j = i;
                           JSONObject a=array.getJSONObject(i);int d2=0;
                           while (d2<1000&&(j > 0) && (array.getJSONObject(j-1).getInt(llCommonKeys.keyProfiles) > a.getInt(llCommonKeys.keyProfiles)))   //returns true when both conditions are true
                           {
                               //array[j] = array[j-1];
                               array.put(j,array.getJSONObject(j-1));
                               j--;
                               d2++;
                           }
                           //array[j] = a;
                           array.put(j,a);
                        }
                        orders.put(llCommonKeys.keyProfiles,array);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        JSONArray array=orders.getJSONArray(llCommonKeys.keyMembersCount);
                        int n=array.length();
                        for(int i=0;i<n;i++){
                            int j = i;
                            JSONObject a=array.getJSONObject(i);int d2=0;
                            while (d2<1000&&(j > 0) && (array.getJSONObject(j-1).getInt(llCommonKeys.keyMembersCount) > a.getInt(llCommonKeys.keyMembersCount)))   //returns true when both conditions are true
                            {
                                //array[j] = array[j-1];
                                array.put(j,array.getJSONObject(j-1));
                                j--;
                                d2++;
                            }
                            //array[j] = a;
                            array.put(j,a);
                        }
                        orders.put(llCommonKeys.keyMembersCount,array);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    jsonObject.put("orders",orders);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdCaughtUsersProfile", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }
        private void sendJSONFileOfChannels(Guild guild){
            String fName = "[sendJSONFileOfChannels]";
            logger.info(fName);
            try {
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,gGuild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                jsonObject.put(llCommonKeys.keyGuild,jsonGuild);
                try {
                    jsonGuild.put(llCommonKeys.keyChannels,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gMuzzleChannelsName));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdMuzzleChannels", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }
        private void sendJSONFileOfChannelsAllGuilds(){
            String fName = "[sendJSONFileOfChannelsAllGuilds]";
            logger.info(fName);
            try {
                if(!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"insufficient permission>denied");
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied!", llColorRed);
                    return;
                }
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject jsonObject=new JSONObject();
                try {
                    JSONObject jsonGuilds=gGlobal.getUserProfile(profileName);
                    Iterator<String> keysGuilds=jsonGuilds.keys();
                    jsonObject.put(llCommonKeys.keyGuilds,new JSONObject());
                    JSONObject orders=new JSONObject();
                    orders.put(llCommonKeys.keyProfiles,new JSONArray());
                    orders.put(llCommonKeys.keyMembersCount,new JSONArray());
                    orders.put("none",new JSONArray());
                    while(keysGuilds.hasNext()){
                        try {
                            String idGuild=keysGuilds.next();
                            logger.info(fName + ".idGuild="+idGuild);
                            JSONObject jsonGuild=new JSONObject();JSONObject order=new JSONObject();
                            Guild guild=gGlobal.getGuild(idGuild);
                            jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                            jsonGuild.put(llCommonKeys.keyName,guild.getName());
                            try {
                                jsonGuild.put(llCommonKeys.keyChannels,gGlobal.getGuildSettingsAsJson(guild, lcBDSMGuildProfiles.gMuzzleChannelsName));
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            jsonObject.getJSONObject(llCommonKeys.keyGuilds).put(idGuild,jsonGuild);
                            logger.info(fName + ".added guild");
                            Iterator<String> keysMembers=jsonGuilds.getJSONObject(idGuild).keys();
                            int profiles=0;
                            while(keysMembers.hasNext()){
                                try {
                                    String idMember=keysMembers.next();
                                    logger.info(fName + ".idMember="+idMember);
                                    JSONObject jsonUserProfile=new JSONObject();
                                    JSONObject jsonUser=new JSONObject();
                                    User user=lsUserHelper.lsGetUser(guild,idMember);
                                    jsonUser.put(llCommonKeys.keyId, user.getIdLong());
                                    jsonUser.put(llCommonKeys.keyName, user.getName());
                                    jsonUser.put(llCommonKeys.keyDiscriminator, user.getDiscriminator());
                                    jsonUserProfile.put(llCommonKeys.keyUser, jsonUser);
                                    jsonUserProfile.put(llCommonKeys.keyProfile, jsonGuilds.getJSONObject(idGuild).getJSONObject(idMember));
                                    if(jsonUserProfile.has(llCommonKeys.keyProfile)&&!jsonUserProfile.isNull(llCommonKeys.keyProfile)&&!jsonUserProfile.getJSONObject(llCommonKeys.keyProfile).isEmpty()){
                                        jsonGuild.getJSONObject(llCommonKeys.keyMembers).put(idMember,jsonUserProfile);
                                        profiles++;
                                    }
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                            jsonObject.getJSONObject(llCommonKeys.keyGuilds).put(idGuild,jsonGuild);
                            try {
                                order.put(llCommonKeys.keyId,guild.getIdLong());
                                order.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                                order.put(llCommonKeys.keyProfiles,profiles);
                                orders.getJSONArray(llCommonKeys.keyProfiles).put(order);
                                orders.getJSONArray(llCommonKeys.keyMembersCount).put(order);
                                orders.getJSONArray("none").put(order);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }

                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    try {
                        JSONArray array=orders.getJSONArray(llCommonKeys.keyProfiles);
                        int n=array.length();
                        for(int i=0;i<n;i++){
                            int j = i;
                            JSONObject a=array.getJSONObject(i);int d2=0;
                            while (d2<1000&&(j > 0) && (array.getJSONObject(j-1).getInt(llCommonKeys.keyProfiles) > a.getInt(llCommonKeys.keyProfiles)))   //returns true when both conditions are true
                            {
                                //array[j] = array[j-1];
                                array.put(j,array.getJSONObject(j-1));
                                j--;
                                d2++;
                            }
                            //array[j] = a;
                            array.put(j,a);
                        }
                        orders.put(llCommonKeys.keyProfiles,array);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        JSONArray array=orders.getJSONArray(llCommonKeys.keyMembersCount);
                        int n=array.length();
                        for(int i=0;i<n;i++){
                            int j = i;
                            JSONObject a=array.getJSONObject(i);int d2=0;
                            while (d2<1000&&(j > 0) && (array.getJSONObject(j-1).getInt(llCommonKeys.keyMembersCount) > a.getInt(llCommonKeys.keyMembersCount)))   //returns true when both conditions are true
                            {
                                //array[j] = array[j-1];
                                array.put(j,array.getJSONObject(j-1));
                                j--;
                                d2++;
                            }
                            //array[j] = a;
                            array.put(j,a);
                        }
                        orders.put(llCommonKeys.keyMembersCount,array);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    jsonObject.put("orders",orders);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="rdCaughtUsersProfile", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Error!", llColorRed);
            }

        }

        private void checkPost(String item0,String item1){
            String fName = "[ checkPost]";
            logger.info(fName+"item0="+item0+", item1="+item1);
            if(!lsMemberHelper.lsMemberHasPermission_MESSAGEMANAGE(gMember)){
                logger.info(fName+"insufficient permission>denied");
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Denied! You dont have manage message permission.", llColorRed);
                return;
            }
            TextChannel textChannel=null;long message_id=0;
            if(item1!=null&&!item1.isBlank()){
                textChannel=lsChannelHelper.lsGetTextChannelById(gGuild,item0);
                if(textChannel==null){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Invalid text channel id '"+item0+"'!", llColorRed);
                    return;
                }
                try {
                    message_id=Long.getLong(item1);
                    if(message_id<=0)throw new Exception("Cant be bellow or equal to zero");
                }catch (Exception e){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Invalid messaga id '"+item1+"'!", llColorRed);
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return;
                }
            }else
            if(item0!=null&&!item0.isBlank()){
                textChannel=gTextChannel;
                try {
                    message_id=Long.getLong(item0);
                    if(message_id<=0)throw new Exception("Cant be bellow or equal to zero");
                }catch (Exception e){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Invalid messaga id '"+item1+"'!", llColorRed);
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return;
                }
            }else{
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Invalid parameters!", llColorRed);
                return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendMessageResponse(gTextChannel,lsMessageHelper.lsGenerateEmbed(null,"Processing...This might take some time.",llColorBlue1));
            JSONObject jsonObject=new JSONObject();
            entityGagPost post=iUtility.getGagPost4Registry(message_id,gGuild.getIdLong());
            EmbedBuilder embedBuilder=null;
            if(post==null){
                embedBuilder=lsMessageHelper.lsGenerateEmbed(null,"No such post registered!",llColorRed);
                if(lsMessageHelper.lsEditMessageResponse(messageProcessing,embedBuilder)==null){
                    lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                }
                return;
            }
            embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColorPurple2);
            Member author=post.getMember();
            embedBuilder.addField("Message id", String.valueOf(message_id),false);
            if(author!=null){
                embedBuilder.addField("Author",author.getAsMention()+" ("+post.getUserId()+")",false);
            }else{
                embedBuilder.addField("Author","("+post.getUserId()+")",false);
            }
            embedBuilder.addField("gagLevel",post.getGaglevelAsString(),false);
            TextChannel postChannel=post.getChannel();
            if(postChannel!=null&&postChannel!=gTextChannel){
                embedBuilder.addField("Channel",postChannel.getAsMention()+" ("+post.getChannelId()+")",false);
            }else if(gTextChannel.getIdLong()!=post.getChannelId()){
                embedBuilder.addField("Channel","("+post.getChannelId()+")",false);
            }
            String content=post.getContent();
            if(content!=null&&!content.isBlank()){
                embedBuilder.addField("Content",post.getContent(),false);
            }else{
                embedBuilder.addField("Content","[Error, this makes no sense!",false).setColor(llColorRed);
            }

            if(lsMessageHelper.lsEditMessageResponse(messageProcessing,embedBuilder)==null){
                lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
            }
        }
        JSONObject JSON;
        private void getDebug(){
            String fName="[getDebug]";
            logger.info(fName);
            getProfile(gMember);
            logger.info(fName+"timelock="+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nTimeOut).toString());
        }
        private void getMainJsons(){
            String fName="[getMainJsons]";
            logger.info(fName);
            try {
                try {
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nTimeLock).getLong(nTimeLockStart)<60000*15){
                        gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nTimeLock).put(nTimeLockStart,60000*15);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                JSON =gNewUserProfile.gUserProfile.jsonObject;
                logger.info(fName+"json="+ JSON.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
}}
