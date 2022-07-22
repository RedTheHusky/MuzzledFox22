package util.inhouse;
//implemented Runnable
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.*;
import models.lc.json.profile.lcJSONGuildProfile;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class utilityAutoPosting extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[utilityAutoPosting]";
    String table=llv2_GuildsSettings;//"profileGuild";
    lcGlobalHelper gGlobal;
    String sRTitle="Auto Posting";
    JSONObject rfEntries;
    public utilityAutoPosting(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "Auto Posting";
        this.help = "Auto Posting";
        this.aliases = new String[]{"autoposting","utilityautoposting"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true; this.category=llCommandCategory_BuildAlpha;
    }
    public utilityAutoPosting(lcGlobalHelper global,GuildMessageReceivedEvent event){
        String fName="[constructor]";
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(cName+fName+"starts");
        if(llDebug){
            logger.info(cName+fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
        logger.info(cName+fName+"stops");
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        private CommandEvent gCommandEvent; private GuildMessageReceivedEvent gMessageReceivedEvent; private Message gMessage;
        private User gUser; private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel; private String gTextChannelID;
        private int gMode;
        public runLocal(CommandEvent ev) {
            logger.info(cName + ".run build");
            String fName="runLocal";
            gCommandEvent = ev;
            gUser =ev.getAuthor();gMember = ev.getMember();
            gGuild = ev.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel =ev.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gTextChannelID=gTextChannel.getId();
            gMessage= ev.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
            gMode=1;
        }
        public runLocal(GuildMessageReceivedEvent ev) {
            logger.info(cName + ".run build");  String fName="runLocal";
            gMessageReceivedEvent = ev;
            gUser =ev.getAuthor();gMember = ev.getMember();
            gGuild = ev.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel =ev.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gTextChannelID=gTextChannel.getId();
            gMessage= ev.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
            gMode=2;
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                if(gMode==2){
                    if(gMessage.getContentRaw().startsWith(llPrefixStr)){
                        logger.info(cName + fName + ".main.java.bot command");
                    }else{
                        if(gGlobal.jsonObject.has("autoposting")){
                            logger.warn(cName + fName + ".autoposting already in use");
                            return;
                        }
                        logger.info(cName + fName + ".autoposting available");
                        gGlobal.jsonObject.put("autoposting",true);
                        listenTextChannel();
                        response();
                        if(gGlobal.jsonObject.has("autoposting")){
                            gGlobal.jsonObject.remove("autoposting");
                        }

                    }
                }else
                if(gMode==1){
                    String[] items;
                    boolean isInvalidCommand = true;
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(cName + fName + ".Args=0");
                        help("main");
                        gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                        isInvalidCommand = false;
                    } else {
                        logger.info(cName + fName + ".Args");
                        items = gCommandEvent.getArgs().split("\\s+");
                        logger.info(cName + fName + ".items.size=" + items.length);
                        logger.info(cName + fName + ".items[0]=" + items[0]);
                        if(items[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("status")){
                            status();isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("lock")){
                            lock(true);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("islocked")){
                            islocked();isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("unlock")){
                            lock(false);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("enable")){
                            enable(0);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("disable")){
                            disable(0);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("enablesfw")){
                            enable(1);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("disablesfw")){
                            disable(1);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("enablensfw")){
                            enable(2);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("disablensfw")){
                            disable(2);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("interval")){
                            if(items.length>=2){
                                setInterval(items[1]);isInvalidCommand=false;
                            }
                        }
                        if(items[0].equalsIgnoreCase("print")){
                            printAllText4Db();isInvalidCommand=false;
                        }
                    }
                    logger.info(cName + fName + ".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if (isInvalidCommand) {
                        llSendQuickEmbedMessage(gTextChannel, sRTitle, "You provided an incorrect command!", llColorRed);
                    }

                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(cName + ".run ended");
        }
        private void help(String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String desc;
            String quickSummonWithSpace = llPrefixStr + "autoposting ";
            desc="`"+quickSummonWithSpace+"status`";
            desc+="\n`"+quickSummonWithSpace+"enable`"; desc+="; `"+quickSummonWithSpace+"enablesfw`"; desc+="; `"+quickSummonWithSpace+"enablensfw`";
            desc+="\n`"+quickSummonWithSpace+"disable`"; desc+="; `"+quickSummonWithSpace+"disablesfw`"; desc+="; `"+quickSummonWithSpace+"disablensfw`";
            desc+="\n`"+quickSummonWithSpace+"interval`";
            llSendQuickEmbedMessage(gUser, sRTitle, desc, llColorRed);
        }
        private void status(){
            String fName = "[status]";
            logger.info(cName + fName);
            String desc="Guild:"+gGuild.getName();
            desc+="\nisEnabled="+isEnabled;
            desc+="\nisEnabledSFW="+isEnabledSFW;
            desc+="\nisEnabledNSFW="+isEnabledNSFW;
            desc+="\nminuteMultiplyer="+minuteMultiplyer;
            llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorPurple1);
        }
        private void setInterval(String strNum){
            String fName = "[setInterval]";
            logger.info(cName + fName);
            if (!loadedProfile()) {
                logger.warn(cName + fName + ".can't load");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load", llColorRed);return;
            }
            logger.info(cName + fName+"strNum="+strNum);
            boolean isNumber=false;int i=0;
            if (strNum != null) {
                try {
                    i = Integer.parseInt(strNum);isNumber=true;
                } catch (NumberFormatException nfe) {

                }
            }
            if(isNumber){
                if(i<0){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Invalid option!", llColorRed);return;
                }
                putFieldEntry(fieldProfile,keyProfileInterval,i);
            }else{
                assert strNum != null;
                if(!strNum.equalsIgnoreCase("default")){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Invalid option!", llColorRed);return;
                }
                putFieldEntry(fieldProfile,keyProfileInterval,minuteMultiplyerDefault);
            }
            if (!saveProfile()) {
                logger.warn(cName + fName + ".can't save");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to save!", llColorRed);
            }
        }
        private void enable(int mode){
            String fName = "[enable]";
            logger.info(cName + fName);
            String desc;
            if (!loadedProfile()) {
                logger.warn(cName + fName + ".can't load");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load", llColorRed);return;
            }
            logger.info(cName + fName+"mode="+mode);
            if(mode==3){
                putFieldEntry(fieldProfile,keyProfileNSFW,true);
                putFieldEntry(fieldProfile,keyProfileSFW,true);
                putFieldEntry(fieldProfile,keyProfileEnabled,true);
                desc="Enabled all";
            }else
            if(mode==2){
                putFieldEntry(fieldProfile,keyProfileNSFW,true);
                desc="Enabled nsfw";
            }else
            if(mode==1){
                putFieldEntry(fieldProfile,keyProfileSFW,true);
                desc="Enabled sfw";
            }else{
                putFieldEntry(fieldProfile,keyProfileEnabled,true);
                desc="Enabled controler";
            }
            if (!saveProfile()) {
                logger.warn(cName + fName + ".can't save");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to save!", llColorRed);return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorGreen1);
        }
        private void disable(int mode){
            String fName = "[disable]";
            logger.info(cName + fName);
            String desc;
            if (!loadedProfile()) {
                logger.warn(cName + fName + ".can't load");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load", llColorRed);return;
            }
            logger.info(cName + fName+"mode="+mode);
            if(mode==3){
                putFieldEntry(fieldProfile,keyProfileNSFW,false);
                putFieldEntry(fieldProfile,keyProfileSFW,false);
                putFieldEntry(fieldProfile,keyProfileEnabled,false);
                desc="Disabled all";
            }else
            if(mode==2){
                putFieldEntry(fieldProfile,keyProfileNSFW,false);
                desc="Disabled nsfw";
            }else
            if(mode==1){
                putFieldEntry(fieldProfile,keyProfileSFW,false);
                desc="Disabled sfw";
            }else{
                putFieldEntry(fieldProfile,keyProfileEnabled,false);
                desc="Disabled controler";
            }
            if (!saveProfile()) {
                logger.warn(cName + fName + ".can't save");
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to save!", llColorRed);return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorGreen1);
        }
        private void islocked() {
            String fName = "[islocked]";
            logger.info(cName + fName);
            if(gGlobal.jsonObject.has("autoposting")){
                llSendQuickEmbedMessage(gUser, sRTitle, "Lock set", llColorPurple1);
            }else{
                llSendQuickEmbedMessage(gUser, sRTitle, "Lock lifted", llColorPurple1);
            }
        }
        private void lock(Boolean enabled) {
            String fName = "[lock]";
            logger.info(cName + fName);
            logger.info(cName + fName + "enabled=" + enabled);
           if(enabled){
               gGlobal.jsonObject.put("autoposting",true);
               llSendQuickEmbedMessage(gUser, sRTitle, "Lock set", llColorPurple1);
           }else {
               if(gGlobal.jsonObject.has("autoposting")){
                   gGlobal.jsonObject.remove("autoposting");
               }
               llSendQuickEmbedMessage(gUser, sRTitle, "Lock lifted", llColorPurple1);
           }
        }

        Boolean isChannelNSFW=false; String isChannelType="";String isChannelCategory="";
        Boolean hasMessageAttachments=false; Boolean hasMessageImage=false;
        Long millisecond_Minute=60000L;
        int minuteMultiplyer=30; int minuteMultiplyerDefault=30;
        Boolean isToRespond=false; Boolean isOK2Respond=false;
        private void listenTextChannel(){
            String fName = "[listenTextChannel]";
            logger.info(cName + fName);
            if(gUser.isBot()){
                logger.info(cName + fName + ".is a main.java.bot");
                return ;
            }
            /*if(!gGuild.getId().equals(llGuildKeyBdsm)){
                logger.warn(cName + fName + ".invalid guild");
                return ;
            }*/
            /*if(!gTextChannel.getId().equals("664770172522856448")){
                logger.warn(cName + fName + ".debug failsafe");
                return ;
            }*/
            if (!loadedProfile()) {
                logger.warn(cName + fName + ".can't load");
                return ;
            }
            loadDUserValues();
            if(!isEnabled){
                logger.info(cName + fName + ".not enabled");
                return ;
            }
            String channelName=gTextChannel.getName().toLowerCase();
            if(gTextChannel.isNSFW()){isChannelNSFW=true;}
            if(channelName.contains("nsfw")){isChannelNSFW=true;}

            if(channelName.contains("lounge")){isChannelType="lounge";isOK2Respond=true;}
            else if(channelName.contains("art")&&gTextChannel.getName().toLowerCase().contains("irl")){isChannelType="art-irl";isOK2Respond=true;}
            else if(channelName.contains("art")){isChannelType="art";isOK2Respond=true;}
            else if(channelName.contains("irl")){isChannelType="irl";isOK2Respond=true;}

            if(channelName.contains("nsfw")){isChannelCategory="nsfw";}
            else if(channelName.contains("bdsm")){isChannelCategory="bdsm";isOK2Respond=true;}
            else if(channelName.contains("abdl")){isChannelCategory="abdl";isOK2Respond=true;}
            else if(channelName.contains("watersports")){isChannelCategory="watersports";isOK2Respond=true;}

            if(channelName.contains("rp")){isOK2Respond=false;}
            if(isChannelNSFW&&!isEnabledNSFW){
                logger.info(cName + fName + ".nsfw blocked");
                return ;
            }
            if(!isChannelNSFW&&!isEnabledSFW){
                logger.info(cName + fName + ".sfw blocked");
                return ;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp="+timestamp.getTime());
            long current=timestamp.getTime();
            long last=getLastTimePosted();
            long diff=current-last;
            logger.info(cName+fName+".diff="+diff);
            long waiting=millisecond_Minute*minuteMultiplyer;
            logger.info(cName+fName+".waiting="+waiting);
            if(diff<waiting){
                logger.info(cName+fName+".still need to wait");
                return ;
            }
            List<Message.Attachment> attachments=gMessage.getAttachments();
            int attachmentsSize=attachments.size();
            if(attachmentsSize>0){hasMessageAttachments=true;}
            for (Message.Attachment attachment : attachments) {
                String fileExtension = attachment.getFileExtension();
                logger.info(cName + fName + ".fileName=" + attachment.getFileName());
                logger.info(cName + fName + ".fileExtension=" + fileExtension);
                assert fileExtension != null;
                if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("gif")) {
                    logger.info(cName + fName + ".has image");
                    hasMessageImage = true;
                    break;
                }
            }
            isToRespond=true;
        }
        JSONArray gResponseTexts=new JSONArray();
        JSONObject gResponseText=new JSONObject();

        private void response(){
            String fName = "[responsd]";
            logger.info(cName + fName);
            /*if(!gTextChannel.getId().equals("664770172522856448")){
                logger.warn(cName + fName + ".debug failsafe");
                return ;
            }*/
            if(!isToRespond){
                logger.info(cName + fName + ".not to respond to"+gTextChannelID);
                return ;
            }
            if(!isOK2Respond){
                logger.info(cName + fName + ".not approved to respond"+gTextChannelID);
                return ;
            }
            logger.info(cName + fName + ".isChannelNSFW="+isChannelNSFW);
            logger.info(cName + fName + ".hasMessageImage="+hasMessageImage);
            logger.info(cName + fName + ".typee="+isChannelType);
            logger.info(cName + fName + ".category="+isChannelCategory);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp="+timestamp.getTime());
            Long current=timestamp.getTime();
            putFieldEntry(fieldChannels, gTextChannelID, current);
            saveProfile();
            if(isChannelNSFW&&hasMessageImage){
                if(isChannelCategory.equals("nsfw")){loadText4Db("nsfw","nsfw","image");}
                if(isChannelCategory.equals("bdsm")){loadText4Db("nsfw","bdsm","image");}
                if(isChannelCategory.equals("abdl")){loadText4Db("nsfw","abdl","image");}
                if(isChannelCategory.equals("watersports")){loadText4Db("nsfw","watersports","image");}
            }else
            if(!isChannelNSFW&&hasMessageImage){
                loadText4Db("Sfw","Sfw","image");
            }else
            if(isChannelNSFW&&!hasMessageImage){
                if(isChannelType.equals("lounge")){
                    if(isChannelCategory.equals("nsfw")){loadText4Db("nsfw","nsfw","text");}
                    if(isChannelCategory.equals("bdsm")){loadText4Db("nsfw","bdsm","text");}
                    if(isChannelCategory.equals("abdl")){loadText4Db("nsfw","abdl","text");}
                    if(isChannelCategory.equals("watersports")){loadText4Db("nsfw","watersports","text");}
                }
            }else
            if(!isChannelNSFW&&!hasMessageImage){
                loadText4Db("Sfw","Sfw","text");
            }

            if(gResponseTexts.isEmpty()){
                logger.warn(cName + fName+"Empty");
                return;
            }
            Random rand = new Random();
            int rand1 = rand.nextInt(gResponseTexts.length());
            logger.info(cName + fName+"rand1="+rand1);
            gResponseText=gResponseTexts.getJSONObject(rand1);
            String message="";
            getEntityInfo();
            if(eType.equalsIgnoreCase("text")&&!eText.isBlank()){
                logger.info(cName + fName+"message="+message);
                message=message.replaceAll("%user%",gUser.getName());
                message=message.replaceAll("%member%",gMember.getEffectiveName());
                message=message.replaceAll("%mention%",gMember.getAsMention());
                message=message.replaceAll("%channel%",gTextChannel.getName());
                message=message.replaceAll("%channelM%",gTextChannel.getAsMention());
                message=message.replaceAll("%guild%",gGuild.getName());
                message=message.replaceAll("%parent%", Objects.requireNonNull(gTextChannel.getParent()).getName());
                llSendMessage(gTextChannel,message);
                return;
            }
            EmbedBuilder embed=new EmbedBuilder(); boolean isBuilt=false;
            embed.setColor(llColorOrange_webcolor);
            if(eType.equalsIgnoreCase("image")&&!eImage.isBlank()){
               embed.setImage(eImage); isBuilt=true;
                if(!eText.isBlank()){
                    embed.setDescription(eText);
                }
                if(eiColor!=0){
                    embed.setColor(eiColor);
                }
                llSendMessage(gTextChannel,embed);
                return;
            }
        }
        String eType="",eCategory="",eAudit="",eText="",eImage="",eAvatarImage="",eAvatarName="";
        int eiColor=0;
        private void getEntityInfo(){
            String fName = "[getEntityInfo]";
            logger.info(cName + fName);
            if(gResponseText.isEmpty()){
                logger.info(cName + fName+"gResponseText.isEmpty()");return;
            }
            if(gResponseText.has(keyType))eType=gResponseText.getString(keyType);
            if(gResponseText.has(keyCategory))eCategory=gResponseText.getString(keyCategory);
            if(gResponseText.has(keyAudit))eAudit=gResponseText.getString(keyAudit);
            if(gResponseText.has(keyText))eText=gResponseText.getString(keyText);
            if(gResponseText.has(keyImage))eImage=gResponseText.getString(keyImage);
            if(gResponseText.has(keyAvatarImage))eAvatarImage=gResponseText.getString(keyAvatarImage);
            if(gResponseText.has(keyAvatarName))eAvatarName=gResponseText.getString(keyAvatarName);
            if(gResponseText.has(keyColor))eiColor=gResponseText.getInt(keyColor);
        }
        private Long getLastTimePosted(){
            String fName = "[getLastTimePosted]";
            logger.info(cName + fName);
            if(!gProfile.hasFieldEntry(fieldChannels)){
                logger.info(cName + fName+"result=no filed");
                return 0L;
            }
            if(!gProfile.hasFieldEntry(fieldChannels, gTextChannelID)){
                logger.info(cName + fName+"result=no such channel:"+gTextChannelID);
                return 0L;
            }
            Long lastPosted=Long.valueOf(Objects.requireNonNull(Objects.requireNonNull(getFieldEntry(fieldChannels, gTextChannelID)).toString()));
            logger.info(cName + fName+"result="+lastPosted);
            return lastPosted;
        }
        private lcJSONGuildProfile gProfile;
        String fieldProfile="profile";
        String keyProfileEnabled="enabled";
        String keyProfileNSFW="nsfw";
        String keyProfileSFW="sfw"; String keyProfileInterval="interval";
        String fieldChannels="channels";
        Boolean safetyUpdated=false;
        private void safetyUserProfileEntry() {
            String fName = "[safetyUserProfileEntry]";
            logger.info(cName + fName + ".safety check");
            String field;
            field=fieldProfile;
            safetyCreateFieldEntry(field);
            safetyPutFieldEntry(field,keyProfileEnabled ,false);
            safetyPutFieldEntry(field,keyProfileSFW ,false);
            safetyPutFieldEntry(field,keyProfileNSFW ,false);
            safetyPutFieldEntry(field,keyProfileInterval ,minuteMultiplyerDefault);
            field=fieldChannels;
            safetyCreateFieldEntry(field);
        }
        private Boolean safetyCreateFieldEntry(String field) {
            String fName = "[safetyCreateFieldEntry]";
            logger.info(cName + fName + ".field=" + field);
            if (gProfile.hasFieldEntry(field)) {
                logger.info(cName + fName + ".ignore");
                return true;
            }
            if (gProfile.createFieldEntry(field)) {
                safetyUpdated=true;
                logger.info(cName + fName + ".success");
                return true;
            }
            logger.warn(cName + fName + ".failed");
            return false;
        }

        private Boolean putFieldEntry(String field, String name, Object value) {
            String fName = "[putFieldEntry]";
            logger.info(cName + fName + ".field=" + field);
            logger.info(cName + fName + ".name=" + name);
            logger.info(cName + fName + ".value=" + value);
            if (gProfile.putFieldEntry(field, name, value)) {
                logger.info(cName + fName + ".success");
                return true;
            }
            logger.warn(cName + fName + ".failed");
            return false;
        }

        private Boolean safetyPutFieldEntry(String field, String name, Object value) {
            String fName = "[safetyPutFieldEntry]";
            logger.info(cName + fName + ".field=" + field);
            logger.info(cName + fName + ".name=" + name);
            logger.info(cName + fName + ".value=" + value);
            if (gProfile.hasFieldEntry(field, name)) {
                logger.info(cName + fName + ".ignore");
                return true;
            }
            if (gProfile.putFieldEntry(field, name, value)) {
                safetyUpdated=true;
                logger.info(cName + fName + ".success");
                return true;
            }
            logger.warn(cName + fName + ".failed");
            return false;
        }

        private Object getFieldEntry(String field, String name) {
            String fName = "[getFieldEntry]";
            logger.info(cName + fName + ".field=" + field);
            logger.info(cName + fName + ".name=" + name);
            Object tmp = gProfile.getFieldEntry(field, name);
            if (tmp != null) {
                logger.info(cName + fName + ".success");
                return tmp;
            }
            logger.warn(cName + fName + ".failed");
            return null;
        }

        private Boolean saveProfile() {
            String fName = "[saveProfile]";
            logger.info(cName + fName);
            if (gProfile.saveProfile(llv2_GuildsSettings)) {
                logger.info(cName + fName + ".success");
                return true;
            }
            logger.warn(cName + fName + ".failed");
            return false;
        }
        String gName="autopost";
        private Boolean loadedProfile() {
            String fName = "[loadedProfile]";
            logger.info(cName + fName);
            gProfile =gGlobal.getGuildSettings(gGuild,gName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyUserProfileEntry();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile(llv2_GuildsSettings)){
                    logger.info(cName + fName + ".success save to db"); return true;
                }else{
                    logger.error(cName + fName + ".error save to db"); return false;
                }
            }
            return gProfile.isExistent();
        }
        Boolean isEnabled=false; Boolean isEnabledSFW=false; Boolean isEnabledNSFW=false;
        private void loadDUserValues(){
            String fName = "[loadDUserValues]";
            logger.info(cName + fName);
            isEnabled= (Boolean)getFieldEntry(fieldProfile,keyProfileEnabled);
            isEnabledSFW= (Boolean)getFieldEntry(fieldProfile,keyProfileSFW);
            isEnabledNSFW= (Boolean)getFieldEntry(fieldProfile,keyProfileNSFW);
            minuteMultiplyer= Integer.parseInt(Objects.requireNonNull(getFieldEntry(fieldProfile, keyProfileInterval)).toString());
            //maxDuration= Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldDuration, keyDuration0MaximumDuration).toString()));
        }
        String tableTextList="ChatBotTextList";
        private void printAllText4Db() {
            String fName = "[printAllText4Db]";
            String desc="";
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                llSendQuickEmbedMessage(gUser,sRTitle,"No connection!", llColorRed);
                return;
            }
            try{
                List<String> columns=new ArrayList<>();
                columns.add("id"); columns.add("guild_id"); columns.add("audit");columns.add("category");columns.add("type");columns.add("value");columns.add("json");
                logger.info(cName+fName+".table="+tableTextList);
                logger.info(cName+fName+".guild:"+gGuild.getId()+"|"+gGuild.getName());
                logger.info(cName+fName+".columns="+columns.toString());
                String query = "select * from "+tableTextList+" where guild_id = '"+gGuild.getId()+"'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                boolean hasValue=false;
                while (rs.next()){
                    hasValue=true;
                    String rAudit=rs.getString("audit");
                    String rCategory=rs.getString("category");
                    String rType=rs.getString("type");
                    String rValue=rs.getString("value");
                    logger.info(cName+fName+".row:"+rAudit+"|"+rCategory+"|"+rType+"="+rValue);
                    String add="\n@"+rAudit+"|"+rCategory+"|"+rType+"="+rValue;
                    String tmp=desc+add;
                    if(tmp.length()>=2000){
                        llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorBlue1);
                        desc=add;
                    } else{
                        desc=tmp;
                    }
                }
                if(hasValue&&!desc.isEmpty()){
                    llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorBlue1);
                }
                if(!hasValue){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Empty!", llColorRed);
                }
            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
                llSendQuickEmbedMessage(gUser,sRTitle,"Exception!", llColorRed);
            }
        }
        String keyText="text",keyAudit="audit",keyGuild="guild",keyCategory="category",keyType="type",keyId="id",keyAvatarImage="avatarImage",keyAvatarName="avatarName",keyImage="image", keyColor="color";
        private void loadText4Db(String audit,String category,String type){
            String fName = "[ loadText4Db]";
            logger.info(cName + fName);
            logger.info(cName + fName+"audit="+audit);
            logger.info(cName + fName+"category="+category);
            logger.info(cName + fName+"type="+type);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            try{
                List<String> columns=new ArrayList<>();
                columns.add("id"); columns.add("guild_id"); columns.add("audit");columns.add("category");columns.add("type");columns.add("value");columns.add("json");
                logger.info(cName+fName+".table="+tableTextList);
                logger.info(cName+fName+".guild:"+gGuild.getId()+"|"+gGuild.getName());
                logger.info(cName+fName+".columns="+columns.toString());
                String query = "select * from "+tableTextList+" where audit LIKE '%"+audit+"%' and (category LIKE '%"+category+"%' OR category LIKE '%all%' ) AND type LIKE '%"+type+"%'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                while (rs.next()){
                    int rId=rs.getInt("id");
                    String rAudit=rs.getString("audit");
                    String rGuild=rs.getString("guild_id");
                    String rCategory=rs.getString("category");
                    String rType=rs.getString("type");
                    String rValue=rs.getString("value");
                    String rJson=rs.getString("json");
                    logger.info(cName+fName+".row:"+rAudit+"|"+rCategory+"|"+rType+"="+rValue);
                    JSONObject obj=new JSONObject();
                    try{
                        obj=new JSONObject(rJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    obj.put(keyId,rId);
                    obj.put(keyGuild,rGuild);
                    obj.put(keyAudit,rAudit);
                    obj.put(keyCategory,rCategory);
                    obj.put(keyType,rType);
                    obj.put(keyText,rValue);
                    gResponseTexts.put(obj);
                    //gResponseTexts.add(rValue);
                }
            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
            }
        }
        private void loadText4Db(Guild guild,String audit,String category,String type){
            String fName = "[ loadText4Db]";
            logger.info(cName + fName);
            logger.info(cName + fName+"audit="+audit);
            logger.info(cName + fName+"category="+category);
            logger.info(cName + fName+"type="+type);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            try{
                List<String> columns=new ArrayList<>();
                columns.add("id"); columns.add("guild_id"); columns.add("audit");columns.add("category");columns.add("type");columns.add("value");columns.add("json");
                logger.info(cName+fName+".table="+tableTextList);
                logger.info(cName+fName+".guild:"+gGuild.getId()+"|"+gGuild.getName());
                logger.info(cName+fName+".columns="+columns.toString());
                String query = "select * from "+tableTextList+" where guild_id ='"+guild.getId()+"' and audit LIKE '%"+audit+"%' and (category LIKE '%"+category+"%' OR category LIKE '%all%' ) AND type LIKE '%"+type+"%'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                while (rs.next()){
                    int rId=rs.getInt("id");
                    String rAudit=rs.getString("audit");
                    String rGuild=rs.getString("guild_id");
                    String rCategory=rs.getString("category");
                    String rType=rs.getString("type");
                    String rValue=rs.getString("value");
                    String rJson=rs.getString("json");
                    logger.info(cName+fName+".row:"+rAudit+"|"+rCategory+"|"+rType+"="+rValue);
                    JSONObject obj=new JSONObject();
                    try{
                        obj=new JSONObject(rJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    obj.put(keyId,rId);
                    obj.put(keyGuild,rGuild);
                    obj.put(keyAudit,rAudit);
                    obj.put(keyCategory,rCategory);
                    obj.put(keyType,rType);
                    obj.put(keyText,rValue);
                    gResponseTexts.put(obj);
                }



            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
            }
        }
    }
}
