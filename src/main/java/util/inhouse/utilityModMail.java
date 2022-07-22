package util.inhouse;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class utilityModMail extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[ModMail]";
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public utilityModMail(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = "Utility-Modmail";
        this.help = "modmail";
        this.aliases = new String[]{"staffmail","modmail"};
        this.guildOnly = true;
        this.category=llCommandCategory_BuildAlpha;
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName+fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        EventWaiter gWaiter;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel;private Message gMessage;

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
            Boolean isInvalidCommand = true;
            try {
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    // split the choices on all whitespace
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items[0].equalsIgnoreCase("send")||items[0].equalsIgnoreCase("new")||items[0].equalsIgnoreCase("start")) {
                        newMail(); isInvalidCommand = false;
                    }
                    if (items[0].equalsIgnoreCase("respond")) {
                        if (!llMemberIsStaff(gMember)) {
                            llSendQuickEmbedMessage(gTextChannel, "ModMail", "Denied!", llColorRed);
                            return;
                        }
                        respondMail(); isInvalidCommand = false;
                    }
                    if (items[0].equalsIgnoreCase("close")) {
                        if (!llMemberIsStaff(gMember)) {
                            llSendQuickEmbedMessage(gTextChannel, "ModMail", "Denied!", llColorRed);
                            return;
                        }
                        closeMail(); isInvalidCommand = false;
                    }
                    if (items[0].equalsIgnoreCase("delete")) {
                        if (!llMemberIsStaff(gMember)) {
                            llSendQuickEmbedMessage(gTextChannel, "ModMail", "Denied!", llColorRed);
                            return;
                        }
                        deleteMail(); isInvalidCommand = false;
                    }
                    if (items[0].equalsIgnoreCase("help")) {
                        if(items.length>=2){
                            help("main");
                            isInvalidCommand = false;
                        }else{
                            help("main");
                            isInvalidCommand = false;
                        }
                    }
                }
                //logger.info(cName + fName + ".deleting op message");
                //llQuckCommandMessageDelete(gEvent);
                if (isInvalidCommand) {
                    llSendMessage(gUser,"Provided an incorrect command!");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(cName + ".run ended");
        }


        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(llColorBlue1);
            embed.setTitle("ModMail Help");
            String quickSummonWithSpace = llPrefixStr + "modmail ";
            String desc = "n/a";
            if (command.equalsIgnoreCase("main")) {
                desc = "\n`" + quickSummonWithSpace + "send`";
                if (llMemberIsStaff(gMember)) {
                    desc += "\n`" + quickSummonWithSpace + "respond`";
                    desc += "\n`" + quickSummonWithSpace + "close`";
                    desc += "\n`" + quickSummonWithSpace + "delete`";
                }
            }

            embed.setDescription(desc);
            llSendMessage(gUser, embed);
        }
        net.dv8tion.jda.api.entities.Category gModCategory=null;
        private Boolean getCategory(){
            String fName = "getCategory";
            logger.info(cName + fName );gModCategory=null;
            List<net.dv8tion.jda.api.entities.Category> categories=gGuild.getCategories();
            if(categories.isEmpty()) {
                logger.info(cName + fName + "null");return false;
            }
            net.dv8tion.jda.api.entities.Category categorie=null;
            for (int i = 0; i < categories.size(); i++) {
                 categorie = categories.get(i);
                 logger.info(cName + fName + "categories[" + i + "]:" + categorie.getId() + "|" + categorie.getName());
                 if (categorie.getName().toLowerCase().equalsIgnoreCase("modmail")||categorie.getName().toLowerCase().equalsIgnoreCase("[modmail]")||categorie.getName().toLowerCase().contains("modmail")||categorie.getName().toLowerCase().contains("[modmail]")) {
                     logger.info(cName + fName + "found");
                     gModCategory=categorie; return true;
                 }
            }
            logger.info(cName + fName + "not found");
            return false;
        }
        TextChannel gTicketTextChannel =null;
        private Boolean getTicketChannel() {
            String fName = "getTicketChannel";
            logger.info(cName + fName);
            gTicketTextChannel = null;
            if (gModCategory == null) {
                logger.info(cName + fName + "null category");
                return false;
            }
            String name=gUser.getId();
            logger.info(cName + fName + "name="+name);
            List<TextChannel> textChannels = gModCategory.getTextChannels();
            TextChannel textChannel = null;
            for (int i = 0; i < textChannels.size(); i++) {
                textChannel = textChannels.get(i);
                logger.info(cName + fName + "textChannel[" + i + "]:" + textChannel.getId() + "|" + textChannel.getName());
                if (textChannel.getName().equalsIgnoreCase(name)) {
                    logger.info(cName + fName + "found channel");
                    gTicketTextChannel = textChannel;
                    return true;
                }
            }
            logger.info(cName + fName + "no such channel");
            return false;
        }
        private Boolean createTicketChannel() {
            String fName = "createTicketChannel";
            logger.info(cName + fName);
            gTicketTextChannel = null;
            if (gModCategory == null) {
                logger.info(cName + fName + "null category");
                return false;
            }
            String name=gUser.getId();
            logger.info(cName + fName + "name="+name);
            List<TextChannel> textChannels = gModCategory.getTextChannels();
            TextChannel textChannel = null;
            for (int i = 0; i < textChannels.size(); i++) {
                textChannel = textChannels.get(i);
                logger.info(cName + fName + "textChannel[" + i + "]:" + textChannel.getId() + "|" + textChannel.getName());
                if (textChannel.getName().equalsIgnoreCase(name)) {
                    logger.info(cName + fName + "found channel");
                    return false;
                }
            }
            logger.info(cName + fName + "create channel");
            gTicketTextChannel =gModCategory.createTextChannel(name).complete();
            if(gTicketTextChannel !=null){logger.info(cName + fName + "success creating channel");return true;}
            logger.error(cName + fName + "failed creating channel");
            return false;
        }
        TextChannel gLogTextChannel=null;
        private Boolean getLogChannel() {
            String fName = "getLogChannel";
            logger.info(cName + fName);
            String name="modmail-log";
            logger.info(cName + fName + "name="+name);
            gLogTextChannel = null;
            if (gModCategory == null) {
                logger.info(cName + fName + "null category");
                return false;
            }
            List<TextChannel> textChannels = gModCategory.getTextChannels();
            TextChannel textChannel = null;
            for (int i = 0; i < textChannels.size(); i++) {
                textChannel = textChannels.get(i);
                logger.info(cName + fName + "textChannel[" + i + "]:" + textChannel.getId() + "|" + textChannel.getName());
                if (textChannel.getName().equalsIgnoreCase(name)) {
                    logger.info(cName + fName + "found channel");
                    gLogTextChannel = textChannel;
                    return true;
                }
            }
            logger.info(cName + fName + "create channel");
            gLogTextChannel=gModCategory.createTextChannel(name).complete();
            if(gLogTextChannel!=null){logger.info(cName + fName + "success creating channel");return true;}
            logger.error(cName + fName + "failed creating channel");
            return false;
        }

        private void newMail() {
            String fName = "newMail";
            logger.info(cName + fName );
            String title="Submitting Ticket";
            llSendQuickEmbedMessage(gUser, title, "Enter the message you want to send.\nIf you want to cancel it type `cancel`.", llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(cName + fName + "text=" + content);
                            if(content.isEmpty()){
                                llSendQuickEmbedMessage(gUser, title, "No text submitted!", llColorRed);
                                return;
                            }
                            if(content.length()>1500){
                                llSendQuickEmbedMessage(gUser, title, "Too big!", llColorRed);
                                return;
                            }
                            if (content.equalsIgnoreCase("cancel")) {
                                llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                                return;
                            }
                            sqlInsert("submit", content, null);

                            if(getCategory()){
                                if(getLogChannel()){
                                    logger.info(cName + fName +"log channel");
                                    EmbedBuilder embedLog=new EmbedBuilder();
                                    embedLog.setDescription("Author:"+gUser.getAsMention());
                                    embedLog.setTitle("Ticket Submitted");
                                    embedLog.setColor(llColorBlue1);
                                    llSendMessage(gLogTextChannel,embedLog);
                                }
                                if(getTicketChannel()){
                                    logger.info(cName + fName +"using already existing channel");
                                    llSendQuickEmbedMessage(gTicketTextChannel,"Ticket Add",content, llColorBlue1);
									llSendQuickEmbedMessage(gUser, title, "Ticket Add", llColorGreen1);
                                }else if(createTicketChannel()){
                                    logger.info(cName + fName +"using new channel");
                                    llSendQuickEmbedMessage(gTicketTextChannel,"Ticket Submitted",content, llColorBlue1);
                                    llSendQuickEmbedMessage(gUser, title, "Ticket Submitted", llColorGreen1);
                                }else {
                                    logger.info(cName + fName +"channel failed");
                                    llSendQuickEmbedMessage(gUser, title, "Ticket Failed to Submit", llColorRed);
                                }
                            }else{
								logger.error(cName + fName +"no such category");
								llSendQuickEmbedMessage(gUser, title, "Modmail category is not set!", llColorRed);
							}

                        }catch (Exception ex) {
                            llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                            logger.error(cName + fName + "  exception:"+ex);
                        }

                    },
                    // if the user takes more than a minute, time out
                    5, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
             });

        }
        private void respondMail() {
            String fName = "respondMail";
            logger.info(cName + fName );
            String title="Responding to Ticket";
            try {
                if(!getCategory()){
                    logger.error(cName + fName +"Modmail category not set");
                    llSendQuickEmbedMessage(gTextChannel, title, "Modmail category not set!", llColorRed);return;
                }
                if(!gTextChannel.getParent().getId().equalsIgnoreCase(gModCategory.getId())){
                    logger.error(cName + fName +"not in Modmail category");
                    llSendQuickEmbedMessage(gTextChannel, title, "Not in Modmail category!", llColorRed);return;
                }
                Member member=gGuild.getMemberById(gTextChannel.getName());
                if(member==null){
                    logger.error(cName + fName +"no such member");
                    llSendQuickEmbedMessage(gTextChannel, title, "No such member!", llColorRed);return;
                }
                llSendQuickEmbedMessage(gUser, title, "Enter the message you want to send.\nIf you want to cancel it type `cancel`.", llColorPurple1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(cName + fName + "text=" + content);
                                if(content.isEmpty()){
                                    llSendQuickEmbedMessage(gUser, title, "No text submitted!", llColorRed);
                                    return;
                                }
                                if(content.length()>1500){
                                    llSendQuickEmbedMessage(gUser, title, "Too big!", llColorRed);
                                    return;
                                }
                                if (content.equalsIgnoreCase("cancel")) {
                                    llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                                    return;
                                }
                                sqlInsert("respond", content, member.getUser());
                                EmbedBuilder embedBuilder=new EmbedBuilder();
                                embedBuilder.setAuthor(gUser.getName(),null,gUser.getAvatarUrl());
                                embedBuilder.setDescription(content);
                                embedBuilder.setTitle("Ticket Response");
                                embedBuilder.setColor(llColorGreen1);
                                llSendMessage(gTextChannel,embedBuilder);
                                if(getLogChannel()){
                                    logger.info(cName + fName +"log channel");
                                    EmbedBuilder embedLog=new EmbedBuilder();
                                    embedLog.setDescription("Author:"+member.getAsMention()+"\nStaff:"+gUser.getAsMention());
                                    embedLog.setTitle("Ticket Response");
                                    embedLog.setColor(llColorGreen1);
                                    llSendMessage(gLogTextChannel,embedLog);
                                }
                                if(!llSendMessageStatus(member.getUser(), embedBuilder)){
                                    llSendQuickEmbedMessage(gTextChannel, title, "Failed to send DM!", llColorRed);return;
                                }else{
                                    llSendQuickEmbedMessage(gTextChannel, title, "DM sent", llColorGreen1);}
                            }catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                                logger.error(cName + fName + "  exception:"+ex);
                            }

                        },
                        // if the user takes more than a minute, time out
                        5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
                        });
            }
            catch (Exception ex) {
                llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                logger.error(cName + fName + "  exception:"+ex);
            }
        }
        private void closeMail() {
            String fName = "closedMail";
            logger.info(cName + fName );
            String title="Closing Response to Ticket";
            try {
                if(!getCategory()){
                    logger.error(cName + fName +"Modmail category not set");
                    llSendQuickEmbedMessage(gTextChannel, title, "Modmail category not set!", llColorRed);return;
                }
                if(!gTextChannel.getParent().getId().equalsIgnoreCase(gModCategory.getId())){
                    logger.error(cName + fName +"not in Modmail category");
                    llSendQuickEmbedMessage(gTextChannel, title, "Not in Modmail category!", llColorRed);return;
                }
                Member member=gGuild.getMemberById(gTextChannel.getName());
                if(member==null){
                    logger.error(cName + fName +"no such member");
                    llSendQuickEmbedMessage(gTextChannel, title, "No such member!", llColorRed);return;
                }
                llSendQuickEmbedMessage(gUser, title, "Enter the message you want to send.\nIf you want to cancel it type `cancel`.", llColorPurple1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(cName + fName + "text=" + content);
                                if(content.isEmpty()){
                                    llSendQuickEmbedMessage(gUser, title, "No text submitted!", llColorRed);
                                    return;
                                }
                                if(content.length()>1500){
                                    llSendQuickEmbedMessage(gUser, title, "Too big!", llColorRed);
                                    return;
                                }
                                if (content.equalsIgnoreCase("cancel")) {
                                    llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                                    return;
                                }
                                sqlInsert("respond", content, member.getUser());
                                EmbedBuilder embedBuilder=new EmbedBuilder();
                                embedBuilder.setAuthor(gUser.getName(),null,gUser.getAvatarUrl());
                                embedBuilder.setDescription(content);
                                embedBuilder.setTitle("Ticket Closed");
                                embedBuilder.setColor(llColorGreen1);
                                llSendMessage(gTextChannel,embedBuilder);
                                if(getLogChannel()){
                                    logger.info(cName + fName +"log channel");
                                    EmbedBuilder embedLog=new EmbedBuilder();
                                    embedLog.setDescription("Author:"+member.getAsMention()+"\nStaff:"+gUser.getAsMention());
                                    embedLog.setTitle("Ticket Closed");
                                    embedLog.setColor(llColorPurple1);
                                    llSendMessage(gLogTextChannel,embedLog);
                                }
                                if(!llSendMessageStatus(member.getUser(), embedBuilder)){
                                    llSendQuickEmbedMessage(gTextChannel, title, "Failed to send DM!", llColorRed);return;
                                }else{
                                    llSendQuickEmbedMessage(gTextChannel, title, "DM sent", llColorGreen1);}
                                gTextChannel.getManager().setName("closed_"+gTextChannel.getName()).submit();
                            }catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                                logger.error(cName + fName + "  exception:"+ex);
                            }

                        },
                        // if the user takes more than a minute, time out
                        5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
                        });
            }
            catch (Exception ex) {
                llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                logger.error(cName + fName + "  exception:"+ex);
            }
        }
        private void deleteMail() {
            String fName = "deleteMail";
            logger.info(cName + fName );
            String title="Delete Ticket";
            try {
                if(!getCategory()){
                    logger.error(cName + fName +"Modmail category not set");
                    llSendQuickEmbedMessage(gTextChannel, title, "Modmail category not set!", llColorRed);return;
                }
                if(!gTextChannel.getParent().getId().equalsIgnoreCase(gModCategory.getId())){
                    logger.error(cName + fName +"not in Modmail category");
                    llSendQuickEmbedMessage(gTextChannel, title, "Not in Modmail category!", llColorRed);return;
                }
                Member member=gGuild.getMemberById(gTextChannel.getName());
                if(member==null){
                    logger.error(cName + fName +"no such member");
                    llSendQuickEmbedMessage(gTextChannel, title, "No such member!", llColorRed);return;
                }
                sqlInsert("delete", "n/a", member.getUser());
                gTextChannel.delete().submit();
                if(getLogChannel()){
                    logger.info(cName + fName +"log channel");
                    EmbedBuilder embedLog=new EmbedBuilder();
                    embedLog.setDescription("Author:"+member.getAsMention()+"\nStaff:"+gUser.getAsMention());
                    embedLog.setTitle("Ticket Deleted");
                    embedLog.setColor(llColorRed);
                    llSendMessage(gLogTextChannel,embedLog);
                }
            }
            catch (Exception ex) {
                llSendQuickEmbedMessage(gUser, title, "Failed to perform command!", llColorRed);
                logger.error(cName + fName + "  exception:"+ex);
            }
        }
        String gTable="modmail";
        private void sqlInsert(String role, String text, User target){
            String fName = "sqlInsert";
            logger.info(cName + fName );
            try{
                if(!gGlobal.sql.checkConnection()){
                    if(!gGlobal.sql.checkConnection()){
                        if(gGlobal.sql.openConnection()){
                            if(!gGlobal.sql.checkConnection()){
                                logger.error(cName+fName+".no connection");
                                return;
                            }
                        }
                    }
                }
                Map<String, Object>map=new LinkedHashMap();
                map.put("guild",gGuild.getId());
                map.put("author",gUser.getId());
                map.put("role",role);
                map.put("text",text);
                if(target!=null){
                    map.put("target",target.getId());
                }

                String columns="";
                String space4values="";
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(columns.length()>0){
                        columns+=", "+key;space4values+=",?";
                    }else{
                        columns=key; space4values="?";
                    }
                }

                String query = "insert into "+gTable+" ("+columns+")values("+space4values+")";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst =  gGlobal.sql.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                int i=1;
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    pst.setObject(i, value);
                    i++;
                }
                logger.info(cName+fName+".columns="+columns);
                logger.info(cName+fName+".space4values="+space4values);
                int status=pst.executeUpdate();
                logger.info(cName+fName+".executed");
                if(status==0){
                    logger.info(cName+fName+".failed");

                }
                logger.info(cName+fName+".success");

            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
            }
        }
    }
}
