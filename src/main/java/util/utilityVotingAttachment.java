package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.emotes.lcEmote;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class utilityVotingAttachment extends Command implements  llGlobalHelper {
    String cName="[utilityVotingAttachment]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="votingatt";
    public utilityVotingAttachment(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Voting-Attachments";
        this.help = "Setting up automatic tumbs up/down for attachment posts..";
        this.aliases = new String[]{commandPrefix,"votingattachment","votingattachments"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityVotingAttachment(lcGlobalHelper g,  GuildMessageReceivedEvent event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        Runnable r = new runLocal(event);new Thread(r).start();
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;GuildMessageReceivedEvent messageReceivedEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel; String gTitle="Voting-Attachments";private Message gMessage;
        private TextChannel targetChannel;

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }
        public runLocal(GuildMessageReceivedEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            messageReceivedEvent = ev;
            gUser = messageReceivedEvent.getAuthor();gMember = messageReceivedEvent.getMember();
            gGuild = messageReceivedEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = messageReceivedEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= messageReceivedEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }


        @Override
        public void run() {
            String fName = "[run]";
            logger.info(fName);
            try {
                if(messageReceivedEvent==null){
                    boolean isInvalidCommand = true;
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(fName + ".Args=0");
                        help("main");
                        isInvalidCommand = false;
                    } else {
                        logger.info(fName + ".Args");
                        initProfile(); initEmotes();
                        String[] items = gCommandEvent.getArgs().split("\\s+");
                        targetChannel=gTextChannel;
                        if (items[0].equalsIgnoreCase("help")) {
                            if(items.length>=2){
                                help(items[1]);
                            }else{
                                help("main");
                            }
                            isInvalidCommand = false;
                        }
                        if(items.length>=2){
                            if (items[0].equalsIgnoreCase("guild")) {
                                if(items[1].equalsIgnoreCase("enable")){
                                    enableGuild(true);isInvalidCommand = false;
                                }
                                if(items[1].equalsIgnoreCase("disable")){
                                    enableGuild(false);isInvalidCommand = false;
                                }
                                if(items[1].equalsIgnoreCase("reset")){
                                    reset();isInvalidCommand = false;
                                }
                            }
                            if (items[0].equalsIgnoreCase("image")) {
                                if(items[1].equalsIgnoreCase("allow")){
                                    allowImage(true);isInvalidCommand = false;
                                }
                                if(items[1].equalsIgnoreCase("ignore")){
                                    allowImage(false);isInvalidCommand = false;
                                }
                            }
                            if (items[0].equalsIgnoreCase("video")) {
                                if(items[1].equalsIgnoreCase("allow")){
                                    allowVideo(true);isInvalidCommand = false;
                                }
                                if(items[1].equalsIgnoreCase("ignore")){
                                    allowVideo(false);isInvalidCommand = false;
                                }
                            }
                            if (items[0].equalsIgnoreCase("other")) {
                                if(items[1].equalsIgnoreCase("allow")){
                                    allowOther(true);isInvalidCommand = false;
                                }
                                if(items[1].equalsIgnoreCase("ignore")){
                                    allowOther(false);isInvalidCommand = false;
                                }
                            }
                            if(items[0].equalsIgnoreCase("mode")){
                                setMode(items[1]);isInvalidCommand = false;
                            }
                        }

                        if(items[0].equalsIgnoreCase("enable")){
                            enableChannel(true);isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("disable")){
                            enableChannel(false);isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("status")){
                            statusChannel();isInvalidCommand = false;
                        }

                    }
                    logger.info(fName + ".deleting op message");
                    lsMessageHelper.lsMessageDelete(gCommandEvent);
                    if (isInvalidCommand) {
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                    }
                }else{
                    initProfile();
                    if(!gProfile.jsonObject.getBoolean(keyEnabled)){
                        logger.info(fName + ".is disabled");return;
                    }
                    if(!entryHas(gTextChannel)){
                        logger.info(fName + ".has no such channel");return;
                    }
                    JSONObject jsonObject=entryGet(gTextChannel);
                    if(!jsonObject.getBoolean(keyEnabled)){
                        logger.info(fName + ".channel is disabled");return;
                    }
                    List<Message.Attachment>attachments=gMessage.getAttachments();
                    if(attachments.size()==0){
                        logger.info(fName + ".no attachment");return;
                    }
                    boolean bImage=jsonObject.getBoolean(keyAllowImage), bVideo=jsonObject.getBoolean(keyAllowVideo),bOther=jsonObject.getBoolean(keyAllowOther);
                    logger.info(fName + ". bImag="+ bImage+", bVideo="+bVideo+", bOther="+bOther);
                    int i=0; initEmotes();
                    for(Message.Attachment attachment:attachments){
                        try {
                            boolean isImage=attachment.isImage();
                            boolean isVideo=attachment.isVideo();
                            logger.info(fName + ".attachment["+i+"]isImage="+isImage+", isVideo="+isVideo);
                            if(bImage&&isImage){
                                addReaction(jsonObject);
                            }else
                            if(bVideo&&isVideo){
                                addReaction(jsonObject);
                            }else
                            if(bOther&&!isImage&&!isVideo){
                                addReaction(jsonObject);
                            }
                            i++;
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = "";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            embed.addField("Enable/Disable guild","`"+quickSummonWithSpace+"guild enable|disable`",false);
            embed.addField("Enable/Disable for channel","`"+quickSummonWithSpace+"enable|disable`, enables or disables in the channel the command is used",false);
            embed.addField("Allow/Ignore Type","`"+quickSummonWithSpace+"image|video|other allow|ignore`, allow or ignore that type of attachment",false);
            embed.addField("Modes","`"+quickSummonWithSpace+"mode 0|1|2`\n0: both thumbs up/down\n1: thumbs up\n2: thumbs down",false);
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void reset(){
            String fName = "reset()";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                gProfile.putFieldEntry(keyChannels,new JSONObject());
                gProfile.putFieldEntry(keyEnabled,false);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Reseted.", llColors.llColorBlue2);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void enableGuild(boolean enabled){
            String fName = "enableGuild";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"enabled="+enabled);
                gProfile.putFieldEntry(keyEnabled,enabled);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                if(enabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled for guild.", llColors.llColorBlue2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Disabled for guild.", llColors.llColorBlue2);
                }

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void enableChannel(boolean enabled){
            String fName = "enableChannel";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"enabled="+enabled);
                if(!entryHas( targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet( targetChannel);
                jsonObject.put(keyEnabled,enabled);
                entrySet( targetChannel,jsonObject);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                if(enabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled for "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Disabled for "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void statusChannel(){
            String fName = "statusChannel";
            logger.info(fName);
            try{
                if(!entryHas( targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet( targetChannel);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorBlue2);
                embedBuilder.addField("Channel" ,targetChannel.getAsMention(),false);
                embedBuilder.addField("Flags","enabled:"+jsonObject.getBoolean(keyEnabled)+"\nimage: "+jsonObject.getBoolean(keyAllowImage)+"\nvideo: "+jsonObject.getBoolean(keyAllowVideo)+"\nother: "+jsonObject.getBoolean(keyAllowOther),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void setMode(String mode){
            String fName = "setMode";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"mode="+ mode);
                int iMode=0;
                try {
                    iMode=Integer.parseInt(mode);
                    logger.info(fName+"iMode="+ iMode);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid mode input. Needs to be number", llColors.llColorRed);
                    return;
                }
                if(!entryHas( targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet( targetChannel);
                jsonObject.put(keyMode,iMode);
                entrySet( targetChannel,jsonObject);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Mode for "+ targetChannel.getAsMention()+" set to "+iMode+".", llColors.llColorBlue2);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void allowImage(boolean enabled){
            String fName = "allowimage";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"enabled="+enabled);
                if(!entryHas( targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet( targetChannel);
                jsonObject.put(keyAllowImage,enabled);
                entrySet( targetChannel,jsonObject);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                if(enabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allow image at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Ignore image at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void allowVideo(boolean enabled){
            String fName = "allowvideo";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"enabled="+enabled);
                if(!entryHas(targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet( targetChannel);
                jsonObject.put(keyAllowVideo,enabled);
                entrySet( targetChannel,jsonObject);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                if(enabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allow video at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Ignore video at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void allowOther(boolean enabled){
            String fName = "allowother";
            logger.info(fName);
            try{
                if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.info(fName+"admin/manager only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for admin/manager only!", llColors.llColorRed);
                    return;
                }
                logger.info(fName+"enabled="+enabled);
                if(!entryHas( targetChannel)){entryAdd( targetChannel);}
                JSONObject jsonObject=entryGet(targetChannel);
                jsonObject.put(keyAllowOther,enabled);
                entrySet( targetChannel,jsonObject);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to save!", llColors.llColorRed);return;
                }
                if(enabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allow other at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Ignore other at "+ targetChannel.getAsMention()+".", llColors.llColorBlue2);
                }

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        lcJSONGuildProfile gProfile;
        String gProfileName="votingAttachment";
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putGuildSettings(gGuild,gProfile);
            if(gProfile.saveProfile()){
                logger.info(fName + ".success save to db");return true;
            }
            logger.error(fName + ".error save to db");return false;
        }
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gProfileName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gProfileName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyProfile();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
        }
        String keyChannels="channels",keyEnabled ="enabled",keyMode="mode", keyAllowImage="image",keyAllowVideo="video",keyAllowOther="other";

        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            gProfile.safetyPutFieldEntry(keyChannels,new JSONObject());
            gProfile.safetyPutFieldEntry(keyEnabled,false);
        }
        lcEmote emojiYes; lcEmote emojiNo;
        private void initEmotes(){
            String fName="[initEmotes]";
            logger.info(cName+fName);
            try {
                emojiYes= new lcEmote();emojiNo= new lcEmote();
                emojiYes.setGuild(gGlobal.getGuild(lsCustomGuilds.lsGuildKeyAdministration));
                emojiYes.getEmoteByName("pumpkinkittyYes");
                emojiNo.setGuild(gGlobal.getGuild(lsCustomGuilds.lsGuildKeyAdministration));
                emojiNo.getEmoteByName("pumpkinkittyNo");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void addReaction(JSONObject jsonObject){
            String fName="[addReaction]";
            logger.info(cName+fName);
            try {
                //gGlobal.emojis.getEmoji("+1");gGlobal.emojis.getEmoji("+1");
                int mode=0;
                if(jsonObject.has(keyMode)&&!jsonObject.isNull(keyMode)){
                    mode=jsonObject.getInt(keyMode);
                }
                logger.info(fName + ". mode=" +  mode);
                switch (mode){
                    case 2:
                        gMessage.addReaction(emojiNo.getEmote()).complete();
                        break;
                    case 1:
                        gMessage.addReaction(emojiYes.getEmote()).complete();
                        break;
                    default:
                        gMessage.addReaction(emojiYes.getEmote()).complete();
                        gMessage.addReaction(emojiNo.getEmote()).complete();
                        break;
                }
                gMessage.addReaction(emojiYes.getEmote()).complete();
                gMessage.addReaction(emojiNo.getEmote()).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private Boolean entryHas(TextChannel textChannel){
            String fName="[entryHas]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+"id="+textChannel.getId());
                boolean bHas=gProfile.jsonObject.getJSONObject(keyChannels).has(textChannel.getId());
                boolean bNull=gProfile.jsonObject.getJSONObject(keyChannels).isNull(textChannel.getId());
                logger.info(cName+fName+"bHas="+bHas+", bNull="+bNull);
                return bHas&&!bNull;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private JSONObject entryNew(){
            String fName="[entryNew]";
            logger.info(cName+fName);
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyEnabled,false);
                jsonObject.put(keyAllowImage,false);
                jsonObject.put(keyAllowVideo,false);
                jsonObject.put(keyAllowOther,false);
                jsonObject.put(keyMode,0);
                return  jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        private void entryAdd(TextChannel textChannel){
            String fName="[entryAdd]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+"id="+textChannel.getId());
                gProfile.jsonObject.getJSONObject(keyChannels).put(textChannel.getId(),entryNew());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private JSONObject entryGet(TextChannel textChannel){
            String fName="[entryGet]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+"id="+textChannel.getId());
                if(gProfile.jsonObject.getJSONObject(keyChannels).has(textChannel.getId())){
                    logger.info(cName+fName+"jsonObject="+gProfile.jsonObject.getJSONObject(keyChannels).getJSONObject(textChannel.getId()).toString());
                    return  gProfile.jsonObject.getJSONObject(keyChannels).getJSONObject(textChannel.getId());
                }else{
                    return  new JSONObject();
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        private void entrySet(TextChannel textChannel, JSONObject jsonObject){
            String fName="[entrySet]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+"id="+textChannel.getId());
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                gProfile.jsonObject.getJSONObject(keyChannels).put(textChannel.getId(),jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
    }

}
