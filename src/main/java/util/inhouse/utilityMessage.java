package util.inhouse;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcGetMessageJsonExtended;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityMessage extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[postMessage]"; Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal; String gTitle = "utilityMessage",gCommand="utilitymessage";
    public utilityMessage(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal=g;
        this.name = "Utility-PostMessage";
        this.help = "posting message with bot";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";

        CommandEvent gCommandEvent;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel;private Message gMessage;
        List<String> gItems;
        EventWaiter gWaiter;

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
            gWaiter = gGlobal.waiter;
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            boolean isInvalidCommand = true;
            try {
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    gItems= Arrays.asList(gCommandEvent.getArgs().split("\\s+"));
                    logger.info(cName + fName+"gItems:"+gItems.toString());
                    if(!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                        llSendMessage(gUser,"Denied!");
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("postdm")) {
                        postMessageByAskingJson4Dm();
                        isInvalidCommand = false;
                    }
                    if (gItems.get(0).equalsIgnoreCase("testpostdm")) {
                        testpostMessageByAskingJson4Dm();
                        isInvalidCommand = false;
                    }
                    if (gItems.get(0).equalsIgnoreCase("deldm")) {
                        deleteMessageByAsking4Dm();
                        isInvalidCommand = false;
                    }
                    if (gItems.get(0).equalsIgnoreCase("getdm")) {
                        getMessageByAsking4Dm();
                        isInvalidCommand = false;
                    }

                }
                logger.info(cName + fName + ".deleting op message");
                //llMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }

        }
        private void help(String command) {
            String fName = "[help]";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + gCommand+" ";
            EmbedBuilder embed=new EmbedBuilder();
            String desc="";
            desc+="`"+quickSummonWithSpace+"postdm`";
            desc+="\n`"+quickSummonWithSpace+"deldm`";
            desc+="\n`"+quickSummonWithSpace+"getdm`";
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription(desc);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        int intDefaultMinutes=15;
        private void postMessageByAskingJson4Dm(){
            String fName = "[postMessageByAskingJson4Dm]";
            logger.info(cName + fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("Please send a json file");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            llMessageDelete(message);
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            logger.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                logger.info(fName + ".extension="+attachment.getFileExtension());
                                if (!attachment.getFileExtension().equalsIgnoreCase("json")) {
                                    logger.error(fName + ".attachment is not an json");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an json!", llColorRed);
                                    return;
                                }
                                text2Json.isInputStream2Json(attachment.retrieveInputStream().get());
                                postMessageJson(text2Json.jsonObject);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        private void deleteMessageByAsking4Dm(){
            String fName = "[postMessageByAsking4Dm]";
            logger.info(cName + fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            String desc="Please enter the fiel you want to delete.";
            desc+="\nMethods:";
            desc+="\n• `<id of server> <id of channel> <id of message>`";
            desc+="\n• `<id of dm channel> <id of message>`";
            desc+="\n• json file";
            embed.setDescription(desc);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            llMessageDelete(message);
                            String content=e.getMessage().getContentStripped();
                            if(content!=null&&!content.isBlank()){
                                logger.info(fName + ".mode content="+content);
                                List<String>items= Arrays.asList(content.split("\\s+"));
                                if(items.size()==3){
                                    long a=0,b=0,c=0;
                                    try {
                                        a=Long.parseLong(items.get(0));
                                        b=Long.parseLong(items.get(1));
                                        c=Long.parseLong(items.get(2));
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Parameters must be a number value!");
                                        return;
                                    }
                                    deleteMessageExtended(a,b,c);
                                }else
                                if(items.size()==2){
                                    long a=0,b=0;
                                    try {
                                        a=Long.parseLong(items.get(0));
                                        b=Long.parseLong(items.get(1));
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Parameters must be a number value!");
                                        return;
                                    }
                                    deleteMessageExtended(a,b);
                                }else{
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters length!");
                                }
                            }else{
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.mode=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    logger.info(fName + ".extension="+attachment.getFileExtension());
                                    if (!attachment.getFileExtension().equalsIgnoreCase("json")) {
                                        logger.error(fName + ".attachment is not an json");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an json!", llColorRed);
                                        return;
                                    }
                                    text2Json.isInputStream2Json(attachment.retrieveInputStream().get());
                                    deleteMessageExtended(text2Json.jsonObject);
                                }
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        private void getMessageByAsking4Dm(){
            String fName = "[postMessageByAsking4Dm]";
            logger.info(cName + fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            String desc="Please enter the fiel you want to delete.";
            desc+="\nMethods:";
            desc+="\n• `<id of server> <id of channel> <id of message>`";
            desc+="\n• `<id of dm channel> <id of message>`";
            desc+="\n• json file";
            embed.setDescription(desc);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            llMessageDelete(message);
                            String content=e.getMessage().getContentStripped();
                            if(content!=null&&!content.isBlank()){
                                logger.info(fName + ".mode content="+content);
                                List<String>items= Arrays.asList(content.split("\\s+"));
                                if(items.size()==3){
                                    long a=0,b=0,c=0;
                                    try {
                                        a=Long.parseLong(items.get(0));
                                        b=Long.parseLong(items.get(1));
                                        c=Long.parseLong(items.get(2));
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Parameters must be a number value!");
                                        return;
                                    }
                                    getMessageExtended(a,b,c);
                                }else
                                if(items.size()==2){
                                    long a=0,b=0;
                                    try {
                                        a=Long.parseLong(items.get(0));
                                        b=Long.parseLong(items.get(1));
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Parameters must be a number value!");
                                        return;
                                    }
                                    getMessageExtended(a,b);
                                }else{
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters length!");
                                }
                            }else{
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.mode=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    logger.info(fName + ".extension="+attachment.getFileExtension());
                                    if (!attachment.getFileExtension().equalsIgnoreCase("json")) {
                                        logger.error(fName + ".attachment is not an json");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an json!", llColorRed);
                                        return;
                                    }
                                    text2Json.isInputStream2Json(attachment.retrieveInputStream().get());
                                    getMessageExtended(text2Json.jsonObject);
                                }
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        lcText2Json text2Json=new lcText2Json();
        private void postMessageJson(JSONObject jsonObject){
            String fName = "[postMessageJson]";
            try {
                if(jsonObject==null){
                    logger.warn(fName + ".json cant be null");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Json cant be null");
                    return;
                }
                logger.info(cName + fName+"jsonObject="+jsonObject.toString());
                long memberid=0,userid=0,channelid=0,guildid=0;
                JSONObject jsonMessage=new JSONObject();
                if(jsonObject.has(llCommonKeys.keyMember_id)){
                    memberid=jsonObject.getLong(llCommonKeys.keyMember_id);
                }
                if(jsonObject.has(llCommonKeys.keyUser_id)){
                    userid=jsonObject.getLong(llCommonKeys.keyUser_id);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyGuildId)){
                    guildid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyGuildId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyChannelId)){
                    channelid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyChannelId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyMessage)){
                    jsonMessage=jsonObject.getJSONObject(llCommonKeys.lsMessageJsonKeys.keyMessage);
                }
                if(guildid>0){
                    Guild guild=gGlobal.getGuild(guildid);
                    if(guild==null){
                        logger.warn(fName + ".invalid guild");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild `"+guildid+"`!");
                        return;
                    }
                    if(channelid>0){
                        TextChannel textChannel=guild.getTextChannelById(channelid);
                        if(textChannel==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+guildid+"/"+channelid+"`!");
                            return;
                        }
                        postMessageJson(jsonMessage,textChannel);
                    }else
                    if(memberid>0){
                        Member member=guild.getMemberById(memberid);
                        if(member==null){
                            logger.warn(fName + ".invalid member");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member with `"+guildid+"/"+memberid+"`!");
                            return;
                        }
                        PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                        if(privateChannel==null){
                            logger.warn(fName + ".no private channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private channel to member `"+memberid+"`!");
                            return;
                        }
                        postMessageJson(jsonMessage,privateChannel);
                    }
                }else
                if(channelid>0){
                    TextChannel textChannel=gGuild.getTextChannelById(channelid);
                    if(textChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel with `"+channelid+"` in guild!");
                        return;
                    }
                    postMessageJson(jsonMessage,textChannel);
                }else
                if(memberid>0){
                    Member member=gGuild.getMemberById(memberid);
                    if(member==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member with `"+memberid+"` in guild!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private channel to member `"+memberid+"`!");
                        return;
                    }
                    postMessageJson(jsonMessage,privateChannel);
                }else
                if(userid>0){
                    User user=null;
                    List<JDA>jdas=gGlobal.getJDAList();
                    for(JDA jda:jdas){
                        user=jda.getUserById(userid);
                        if(user!=null)break;
                    }
                    if(user==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such user `"+userid+"`!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(user);
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private channel to user `"+userid+"`!");
                        return;
                    }
                    postMessageJson(jsonMessage,privateChannel);
                }else{
                    postMessageJson(jsonMessage,gTextChannel);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void postMessageJson(JSONObject jsonMessage, TextChannel textChannel){
            String fName = "[postMessageJson]";
            try {
                logger.info(cName + fName+"jsonMessage="+jsonMessage.toString());
                logger.info(cName + fName+"textChannel="+textChannel.getId());
                lcMyMessageJsonBuilder messageJsonBuilder=new lcMyMessageJsonBuilder(jsonMessage);
                logger.info(cName + fName+"messageJson="+messageJsonBuilder.getJson());
                Message message=messageJsonBuilder.post(textChannel);
                if(message==null){
                    logger.warn(fName + ".returned back no message");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to post!");
                    return;
                }
                logger.info(cName + fName+"message="+message.getId());
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorGreen2);embedBuilder.setTitle(gTitle);
                embedBuilder.setDescription("Successfully posted "+ lsUsefullFunctions.getUrlTextString("message",message.getJumpUrl())+".");
                llSendMessage(gUser,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void postMessageJson(JSONObject jsonMessage, PrivateChannel privateChannel){
            String fName = "[postMessageJson]";
            try {
                logger.info(cName + fName+"jsonMessage="+jsonMessage.toString());
                logger.info(cName + fName+"privateChannel="+privateChannel.getId());
                lcMyMessageJsonBuilder messageJsonBuilder=new lcMyMessageJsonBuilder(jsonMessage);
                logger.info(cName + fName+"messageJson="+messageJsonBuilder.getJson());
                Message message=messageJsonBuilder.post(privateChannel);
                if(message==null){
                    logger.warn(fName + ".returned back no message");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to post!");
                    return;
                }
                logger.info(cName + fName+"message="+message.getId());
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorGreen2);embedBuilder.setTitle(gTitle);
                embedBuilder.setDescription("Successfully posted "+ lsUsefullFunctions.getUrlTextString("message",message.getJumpUrl())+".");
                llSendMessage(gUser,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void deleteMessageExtended(long guildid, long channelid, long messageid){
            String fName = "[deleteMessageExtended]";
            try {
                logger.info(cName + fName+"guildid="+guildid+", channelid="+channelid+", messageid="+messageid);
                JSONObject jsonMessage=new JSONObject();

                if(guildid>0){
                    Guild guild=gGlobal.getGuild(guildid);
                    if(guild==null){
                        logger.warn(fName + ".invalid guild");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild `"+guildid+"`!");
                        return;
                    }
                    if(channelid>0){
                        TextChannel textChannel=guild.getTextChannelById(channelid);
                        if(textChannel==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+guildid+"/"+channelid+"`!");
                            return;
                        }
                        Message message=textChannel.retrieveMessageById(messageid).complete();
                        if(message==null){
                            logger.warn(fName + ".invalid message");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such message `"+guildid+"/"+channelid+"/"+messageid+"`!");
                            return;
                        }
                        deleteMessageExtended(message);
                    }
                }else
                if(channelid>0){
                    TextChannel textChannel=gGuild.getTextChannelById(channelid);
                    if(textChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel with `"+channelid+"` in guild!");
                        return;
                    }
                    Message message=textChannel.retrieveMessageById(messageid).complete();
                    if(message==null){
                        logger.warn(fName + ".invalid message");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such message `here/"+channelid+"/"+messageid+"`!");
                        return;
                    }
                    deleteMessageExtended(message);
                }else{
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void deleteMessageExtended(long privatechannelid, long messageid){
            String fName = "[deleteMessageExtended]";
            try {
                logger.info(cName + fName+"privatechannelid="+privatechannelid+", messageid="+messageid);
                if(privatechannelid>0){
                    PrivateChannel privateChannel=null;
                    List<JDA>jdas=gGlobal.getJDAList();
                    for(JDA jda:jdas){
                        privateChannel=jda.getPrivateChannelById(privatechannelid);
                        if(privateChannel!=null)break;
                    }
                    if(privateChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+privatechannelid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+privatechannelid+"/"+messageid+"`!");
                        return;
                    }
                    deleteMessageExtended(message);
                }else
                {
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void deleteMessageExtended(JSONObject jsonObject){
            String fName = "[deleteMessageExtended]";
            try {
                if(jsonObject==null){
                    logger.warn(fName + ".json cant be null");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Json cant be null");
                    return;
                }
                logger.info(cName + fName+"jsonObject="+jsonObject.toString());
                long memberid=0,userid=0,channelid=0,guildid=0,messageid=0;
                if(jsonObject.has(llCommonKeys.keyMember_id)){
                    memberid=jsonObject.getLong(llCommonKeys.keyMember_id);
                }
                if(jsonObject.has(llCommonKeys.keyUser_id)){
                    userid=jsonObject.getLong(llCommonKeys.keyUser_id);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyGuildId)){
                    guildid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyGuildId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyChannelId)){
                    channelid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyChannelId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyMessage)){
                    messageid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyMessage);
                }
                if(guildid>0){
                    Guild guild=gGlobal.getGuild(guildid);
                    if(guild==null){
                        logger.warn(fName + ".invalid guild");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild `"+guildid+"`!");
                        return;
                    }
                    if(channelid>0){
                        TextChannel textChannel=guild.getTextChannelById(channelid);
                        if(textChannel==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+guildid+"/"+channelid+"`!");
                            return;
                        }
                        Message message=lsMessageHelper.lsGetMessageById(textChannel,messageid);
                        if(message==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild message `"+guildid+"/"+channelid+"/"+message+"`!");
                            return;
                        }
                        deleteMessageExtended(message);
                    }else
                    if(memberid>0){
                        Member member=guild.getMemberById(memberid);
                        if(member==null){
                            logger.warn(fName + ".invalid member");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member `"+guildid+"/"+memberid+"`");
                            return;
                        }
                        PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                        if(privateChannel==null){
                            logger.warn(fName + ".no private channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private message for member `"+memberid+"`!");
                            return;
                        }
                        Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                        if(message==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+memberid+"/"+message+"`!");
                            return;
                        }
                        deleteMessageExtended(message);
                    }
                }else
                if(channelid>0){
                    TextChannel textChannel=gGuild.getTextChannelById(channelid);
                    if(textChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+channelid+"` in guild!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(textChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild message `"+channelid+"/"+message+"`!");
                        return;
                    }
                    deleteMessageExtended(message);
                }else
                if(memberid>0){
                    Member member=gGuild.getMemberById(memberid);
                    if(member==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member `"+memberid+"` in guild!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private message for member `"+memberid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+memberid+"/"+message+"`!");
                        return;
                    }
                    deleteMessageExtended(message);
                }else
                if(userid>0){
                    User user=null;
                    List<JDA>jdas=gGlobal.getJDAList();
                    for(JDA jda:jdas){
                        user=jda.getUserById(userid);
                        if(user!=null)break;
                    }
                    if(user==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such user `"+userid+"`!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(user);
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private channel to user `"+userid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+userid+"/"+message+"`!");
                        return;
                    }
                    deleteMessageExtended(message);
                }else{
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void deleteMessageExtended(Message message){
            String fName = "[deleteMessageExtended]";
            try {
                logger.info(cName + fName+"message="+message.getId());
                message.delete().complete();
                lsMessageHelper.lsMessageDelete(message);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorGreen2);embedBuilder.setTitle(gTitle);
                embedBuilder.setDescription("Successfully deleted message "+ message.getId()+".");
                llSendMessage(gUser,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void getMessageExtended(long guildid,long channelid, long messageid){
            String fName = "[getMessageExtended]";
            try {
                logger.info(cName + fName+"guildid="+guildid+", channelid="+channelid+", messageid="+messageid);
                JSONObject jsonMessage=new JSONObject();

                if(guildid>0){
                    Guild guild=gGlobal.getGuild(guildid);
                    if(guild==null){
                        logger.warn(fName + ".invalid guild");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild `"+guildid+"`!");
                        return;
                    }
                    if(channelid>0){
                        TextChannel textChannel=guild.getTextChannelById(channelid);
                        if(textChannel==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+guildid+"/"+channelid+"`!");
                            return;
                        }
                        Message message=textChannel.retrieveMessageById(messageid).complete();
                        if(message==null){
                            logger.warn(fName + ".invalid message");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such message `"+guildid+"/"+channelid+"/"+messageid+"`!");
                            return;
                        }
                        getMessageExtended(message);
                    }
                }else
                if(channelid>0){
                    TextChannel textChannel=gGuild.getTextChannelById(channelid);
                    if(textChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel with `"+channelid+"` in guild!");
                        return;
                    }
                    Message message=textChannel.retrieveMessageById(messageid).complete();
                    if(message==null){
                        logger.warn(fName + ".invalid message");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such message `here/"+channelid+"/"+messageid+"`!");
                        return;
                    }
                    getMessageExtended(message);
                }else{
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void getMessageExtended(long privatechannelid, long messageid){
            String fName = "[getMessageExtended]";
            try {
                logger.info(cName + fName+"privatechannelid="+privatechannelid+", messageid="+messageid);
                if(privatechannelid>0){
                    PrivateChannel privateChannel=null;
                    List<JDA>jdas=gGlobal.getJDAList();
                    for(JDA jda:jdas){
                        privateChannel=jda.getPrivateChannelById(privatechannelid);
                        if(privateChannel!=null)break;
                    }
                    if(privateChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+privatechannelid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+privatechannelid+"/"+messageid+"`!");
                        return;
                    }
                    getMessageExtended(message);
                }else
                {
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void getMessageExtended(JSONObject jsonObject){
            String fName = "[getMessageExtended]";
            try {
                if(jsonObject==null){
                    logger.warn(fName + ".json cant be null");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Json cant be null");
                    return;
                }
                logger.info(cName + fName+"jsonObject="+jsonObject.toString());
                long memberid=0,userid=0,channelid=0,guildid=0,messageid=0;
                if(jsonObject.has(llCommonKeys.keyMember_id)){
                    memberid=jsonObject.getLong(llCommonKeys.keyMember_id);
                }
                if(jsonObject.has(llCommonKeys.keyUser_id)){
                    userid=jsonObject.getLong(llCommonKeys.keyUser_id);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyGuildId)){
                    guildid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyGuildId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyChannelId)){
                    channelid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyChannelId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyMessage)){
                    messageid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyMessage);
                }
                if(guildid>0){
                    Guild guild=gGlobal.getGuild(guildid);
                    if(guild==null){
                        logger.warn(fName + ".invalid guild");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild `"+guildid+"`!");
                        return;
                    }
                    if(channelid>0){
                        TextChannel textChannel=guild.getTextChannelById(channelid);
                        if(textChannel==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+guildid+"/"+channelid+"`!");
                            return;
                        }
                        Message message=lsMessageHelper.lsGetMessageById(textChannel,messageid);
                        if(message==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild message `"+guildid+"/"+channelid+"/"+message+"`!");
                            return;
                        }
                        getMessageExtended(message);
                    }else
                    if(memberid>0){
                        Member member=guild.getMemberById(memberid);
                        if(member==null){
                            logger.warn(fName + ".invalid member");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member `"+guildid+"/"+memberid+"`");
                            return;
                        }
                        PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                        if(privateChannel==null){
                            logger.warn(fName + ".no private channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private message for member `"+memberid+"`!");
                            return;
                        }
                        Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                        if(message==null){
                            logger.warn(fName + ".invalid channel");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+memberid+"/"+message+"`!");
                            return;
                        }
                        getMessageExtended(message);
                    }
                }else
                if(channelid>0){
                    TextChannel textChannel=gGuild.getTextChannelById(channelid);
                    if(textChannel==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such channel `"+channelid+"` in guild!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(textChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such guild message `"+channelid+"/"+message+"`!");
                        return;
                    }
                    getMessageExtended(message);
                }else
                if(memberid>0){
                    Member member=gGuild.getMemberById(memberid);
                    if(member==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such member `"+memberid+"` in guild!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(member.getUser());
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private message for member `"+memberid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+memberid+"/"+message+"`!");
                        return;
                    }
                    getMessageExtended(message);
                }else
                if(userid>0){
                    User user=null;
                    List<JDA>jdas=gGlobal.getJDAList();
                    for(JDA jda:jdas){
                        user=jda.getUserById(userid);
                        if(user!=null)break;
                    }
                    if(user==null){
                        logger.warn(fName + ".invalid member");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such user `"+userid+"`!");
                        return;
                    }
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(user);
                    if(privateChannel==null){
                        logger.warn(fName + ".no private channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to open private channel to user `"+userid+"`!");
                        return;
                    }
                    Message message=lsMessageHelper.lsGetMessageById(privateChannel,messageid);
                    if(message==null){
                        logger.warn(fName + ".invalid channel");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "No such private message `"+userid+"/"+message+"`!");
                        return;
                    }
                    getMessageExtended(message);
                }else{
                    logger.warn(fName + ".invalid parameters");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Invalid parameters!");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }
        private void getMessageExtended(Message message){
            String fName = "[getMessageExtended]";
            try {
                logger.info(cName + fName+"message="+message.getId());
                lcGetMessageJsonExtended getMessageJson=new lcGetMessageJsonExtended(message);
                JSONObject jsonObject=getMessageJson.getJson();
                logger.info(cName + fName+"jsonObject="+jsonObject.toString());
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Message", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                /*PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(gUser);
                if(privateChannel==null){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Failed to send to private channel!");
                }*/
                Message message1=gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                if(message1!=null){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully sent to :"+gTextChannel.getAsMention()+".", llColors.llColorGreen1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }

        private void testpostMessageByAskingJson4Dm(){
            String fName = "[postMessageByAskingJson4Dm]";
            logger.info(cName + fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("Please send a json file");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            llMessageDelete(message);
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            logger.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                logger.info(fName + ".extension="+attachment.getFileExtension());
                                if (!attachment.getFileExtension().equalsIgnoreCase("json")) {
                                    logger.error(fName + ".attachment is not an json");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an json!", llColorRed);
                                    return;
                                }
                                text2Json.isInputStream2Json(attachment.retrieveInputStream().get());
                                testpostMessageJson(text2Json.jsonObject);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        private void testpostMessageJson(JSONObject jsonObject){
            String fName = "[postMessageJson]";
            try {
                if(jsonObject==null){
                    logger.warn(fName + ".json cant be null");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, "Json cant be null");
                    return;
                }
                logger.info(cName + fName+"jsonObject="+jsonObject.toString());
                long memberid=0,userid=0,channelid=0,guildid=0;
                JSONObject jsonMessage=new JSONObject();
                if(jsonObject.has(llCommonKeys.keyMember_id)){
                    memberid=jsonObject.getLong(llCommonKeys.keyMember_id);
                }
                if(jsonObject.has(llCommonKeys.keyUser_id)){
                    userid=jsonObject.getLong(llCommonKeys.keyUser_id);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyGuildId)){
                    guildid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyGuildId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyChannelId)){
                    channelid=jsonObject.getLong(llCommonKeys.lsMessageJsonKeys.keyChannelId);
                }
                if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyMessage)){
                    jsonMessage=jsonObject.getJSONObject(llCommonKeys.lsMessageJsonKeys.keyMessage);
                }
                logger.info(cName + fName+"jsonMessage="+jsonMessage.toString());
                lcMyMessageJsonBuilder messageJsonBuilder=new lcMyMessageJsonBuilder(jsonMessage);
                logger.info(cName + fName+"messageJson="+messageJsonBuilder.getJson());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle, e.toString());
            }
        }

    }
}
