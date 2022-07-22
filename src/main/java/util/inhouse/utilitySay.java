package util.inhouse;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class utilitySay extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[utilitySay]"; Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public utilitySay(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal=g;
        this.name = "Utility-SayBot";
        this.help = "posting with the bot";
        this.aliases = new String[]{"saybot"};
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
        String gTitle = "Santa Claus";
        CommandEvent gCommandEvent;
        MessageReceivedEvent gMessageEvent;
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
                    if(!llMemberIsStaff(gMember)) {
                        llSendMessage(gUser,"Denied!");
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("here")) {
                        sayHere();
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("there")) {
                        sayThere(null);
                        isInvalidCommand = false;
                    }else
                    if (gItems.get(0).equalsIgnoreCase("dm")) {
                        directMessaging();
                        isInvalidCommand = false;
                    }

                }
                logger.info(cName + fName + ".deleting op message");
                llMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void help(String command) {
            String fName = "[help]";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + "say ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.addField("Here","`" + quickSummonWithSpace + "here` ",false);
            embed.addField("There","\n`" + quickSummonWithSpace + "there` ",false);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        private void sayHere(){
            String fName = "[sayHere]";
            logger.info(cName + fName);
            sayThere(gTextChannel);
        }
        private void sayThere(TextChannel channel){
            String fName = "[sayThere]";
            logger.info(cName + fName);
            if(channel!=null){
                logger.info(cName + fName+"channel:"+channel.getId()+"|"+channel.getName());
            }
            boolean isEmbed=false;
            for (String gItem : gItems) {
                if (gItem.equalsIgnoreCase("embed")) {
                    isEmbed = true;
                    break;
                }
            }
            List<TextChannel>channels= gCommandEvent.getMessage().getMentionedChannels();
            String title;
            if(isEmbed){
                logger.info(cName + fName+"embed");
                title="Post embed";
                llSendQuickEmbedMessage(gUser,title, "Please enter text you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColorPurple1);
                String finalTitle = title;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            if (content.isEmpty()) {
                                logger.warn(cName + fName + ".no content");
                                llSendQuickEmbedMessage(gUser, finalTitle, "No text!", llColorRed);
                                return;
                            }
                            try {
                                logger.info(cName + fName + "text=" + content);
                                if(content.equalsIgnoreCase("cancel")){
                                    llSendQuickEmbedMessage(gUser, finalTitle, "Canceled!", llColorRed);
                                    return;
                                }
                                JSONObject json=new JSONObject(content);
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setTitle("Basic");
                                //embed.setDescription("()");

                                if(json.has("title")&&json.has("url")){
                                    embed.setTitle(json.getString("title"),json.getString("url"));
                                }else
                                if(json.has("title")){
                                    embed.setTitle(json.getString("title"));
                                }

                                if(json.has("description")){
                                    embed.setDescription(json.getString("description"));
                                }
                                if(json.has("image")&&json.getJSONObject("image").has("url")){
                                    embed.setImage(json.getJSONObject("image").getString("url"));
                                }
                                if(json.has("thumbnail")&&json.getJSONObject("thumbnail").has("url")){
                                    embed.setThumbnail(json.getJSONObject("thumbnail").getString("url"));
                                }
                                if(json.has("color")){
                                    embed.setColor(json.getInt("color"));
                                }
                                if(json.has("footer")){
                                    if(json.getJSONObject("footer").has("text")&&json.getJSONObject("footer").has("icon_url")){
                                        embed.setFooter(json.getJSONObject("footer").getString("text"),json.getJSONObject("footer").getString("icon_url"));
                                    }else
                                    if(json.getJSONObject("footer").has("text")){
                                        embed.setFooter(json.getJSONObject("footer").getString("text"));
                                    }else
                                    if(json.getJSONObject("footer").has("icon_url")){
                                        embed.setFooter(" ",json.getJSONObject("footer").getString("icon_url"));
                                    }
                                }
                                if(channel!=null){
                                    logger.info(cName + fName+"channel:here");
                                    llSendMessage(channel,embed);
                                }else{
                                    logger.info(cName + fName+"channels:there");
                                    for(int i=0;i<channels.size();i++){
                                        logger.info(cName + fName+"channel["+i+"]:"+channels.get(i).getId()+"|"+channels.get(i).getName());
                                        llSendMessage(channels.get(i),embed);
                                    }
                                }
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, finalTitle, "Failed!", llColorRed);
                                logger.info(cName + fName+"exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, finalTitle, "Timeout", llColorRed));
            }else{
                logger.info(cName + fName+"text");
                title="Post text";
                llSendQuickEmbedMessage(gUser,title, "Please enter you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColorPurple1);
                String finalTitle = title;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            if (content.isEmpty()) {
                                logger.warn(cName + fName + ".no content");
                                llSendQuickEmbedMessage(gUser, finalTitle, "No text!", llColorRed);
                                return;
                            }
                            try {
                                logger.info(cName + fName + "text=" + content);
                                if(content.equalsIgnoreCase("cancel")){
                                    llSendQuickEmbedMessage(gUser, finalTitle, "Canceled!", llColorRed);
                                    return;
                                }
                                if(channel!=null){
                                    logger.info(cName + fName+"channel:here");
                                    llSendMessage(channel,content);
                                }else{
                                    logger.info(cName + fName+"channels:there");
                                    for(int i=0;i<channels.size();i++){
                                        logger.info(cName + fName+"channel["+i+"]:"+channels.get(i).getId()+"|"+channels.get(i).getName());
                                        llSendMessage(channels.get(i),content);
                                    }
                                }
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, finalTitle, "Failed!", llColorRed);
                                logger.info(cName + fName+"exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, finalTitle, "Timeout", llColorRed));
            }
        }
        private void directMessaging(){
            String fName = "[directMessaging]";
            logger.info(cName + fName);

            boolean isEmbed=false;
            for (String gItem : gItems) {
                if (gItem.equalsIgnoreCase("embed")) {
                    isEmbed = true;
                    break;
                }
            }
            String title;
            List<Member>mentions= gCommandEvent.getMessage().getMentionedMembers();
            if(isEmbed){
                logger.info(cName + fName+"embed");
                title="DM embed";
                llSendQuickEmbedMessage(gUser,title, "Please enter you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColorPurple1);
                String finalTitle = title;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            if (content.isEmpty()) {
                                logger.warn(cName + fName + ".no content");
                                llSendQuickEmbedMessage(gUser, finalTitle, "No text!", llColorRed);
                                return;
                            }
                            try {
                                logger.info(cName + fName + "text=" + content);
                                if(content.equalsIgnoreCase("cancel")){
                                    llSendQuickEmbedMessage(gUser, finalTitle, "Canceled!", llColorRed);
                                    return;
                                }
                                JSONObject json=new JSONObject(content);
                                EmbedBuilder embed = new EmbedBuilder();
                                embed.setTitle("Basic");
                                embed.setDescription("()");

                                if(json.has("title")&&json.has("url")){
                                    embed.setTitle(json.getString("title"),json.getString("url"));
                                }else
                                if(json.has("title")){
                                    embed.setTitle(json.getString("title"));
                                }

                                if(json.has("description")){
                                    embed.setDescription(json.getString("description"));
                                }
                                if(json.has("image")&&json.getJSONObject("image").has("url")){
                                    embed.setImage(json.getJSONObject("image").getString("url"));
                                }
                                if(json.has("thumbnail")&&json.getJSONObject("thumbnail").has("url")){
                                    embed.setThumbnail(json.getJSONObject("thumbnail").getString("url"));
                                }
                                if(json.has("color")){
                                    embed.setColor(json.getInt("color"));
                                }
                                if(json.has("footer")){
                                    if(json.getJSONObject("footer").has("text")&&json.getJSONObject("footer").has("icon_url")){
                                        embed.setFooter(json.getJSONObject("footer").getString("text"),json.getJSONObject("footer").getString("icon_url"));
                                    }else
                                    if(json.getJSONObject("footer").has("text")){
                                        embed.setFooter(json.getJSONObject("footer").getString("text"));
                                    }else
                                    if(json.getJSONObject("footer").has("icon_url")){
                                        embed.setFooter(" ",json.getJSONObject("footer").getString("icon_url"));
                                    }
                                }
                                if(mentions.isEmpty()){
                                    logger.info(cName + fName+"to:myself");
                                    llSendMessage(e.getAuthor(),embed);
                                }else{
                                    logger.info(cName + fName+"to:multiple");
                                    for(int i=0;i<mentions.size();i++){
                                        logger.info(cName + fName+"mentions["+i+"]:"+mentions.get(i).getId()+"|"+mentions.get(i).getEffectiveName());
                                        llSendMessage(mentions.get(i).getUser(),embed);
                                    }
                                }
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, finalTitle, "Failed!", llColorRed);
                                logger.info(cName + fName+"exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, finalTitle, "Timeout", llColorRed));
            }else{
                logger.info(cName + fName+"text");
                title="DM text";
                llSendQuickEmbedMessage(gUser,title, "Please enter you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColorPurple1);
                String finalTitle = title;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            logger.info(cName + fName + "received");
                            try {
                            String content = e.getMessage().getContentStripped();
                            if (content.isEmpty()) {
                                logger.warn(cName + fName + ".no content");
                                llSendQuickEmbedMessage(gUser, finalTitle, "No text!", llColorRed);
                                return;
                            }

                                logger.info(cName + fName + "text=" + content);
                                if(content.equalsIgnoreCase("cancel")){
                                    llSendQuickEmbedMessage(gUser, finalTitle, "Canceled!", llColorRed);
                                    return;
                                }
                                if(mentions.isEmpty()){
                                    logger.info(cName + fName+"to:myself");
                                    llSendMessage(e.getAuthor(),content);
                                }else{
                                    logger.info(cName + fName+"to:multiple");
                                    for(int i=0;i<mentions.size();i++){
                                        logger.info(cName + fName+"mentions["+i+"]:"+mentions.get(i).getId()+"|"+mentions.get(i).getEffectiveName());
                                        llSendMessage(mentions.get(i).getUser(),content);
                                    }
                                }
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, finalTitle, "Failed!", llColorRed);
                                logger.info(cName + fName+"exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, finalTitle, "Timeout", llColorRed));
            }
        }
    }
}
