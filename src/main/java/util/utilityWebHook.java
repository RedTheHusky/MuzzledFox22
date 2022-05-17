package util;
//implemented Runnable

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.lcSqlConnEntity;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.llGlobalHelper;
import models.ls.lsGuildHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;
import static models.ls.lsUserHelper.lsUserIsBotOwner;

public class utilityWebHook extends Command implements llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[utilityWebHook]";

    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    String  gTitle="Webhooks Utility";
    public utilityWebHook(lcGlobalHelper g){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=g;gWaiter=g.waiter;
        this.name = "Webhook-Utility";
        this.help = "Commands for gWebhook";
        this.aliases = new String[]{"gWebhook","utilitygWebhook"};
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;//llCommandCategory_Utility;
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage;
        List<String> gItems;
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

        @Override
        public void run() {
            String fName="[run]";
            try {
                CommandEvent event= gCommandEvent;
                gUser = gCommandEvent.getAuthor();
                gMember = gCommandEvent.getMember();
                gGuild = gCommandEvent.getGuild();
                logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = gCommandEvent.getTextChannel();
                String[] items;
                Boolean isInvalidCommand = true;
                if(event.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");help("main");isInvalidCommand = false;
                }else {
                    logger.info(fName + ".Args");
                    items = event.getArgs().split("\\s+");
                    logger.info(fName+".items.size="+items.length);
                    logger.info(fName+".items[0]="+items[0]);
                    if(!llMemberIsStaff(gMember)) {
                        lsMessageHelper.lsSendMessage(gUser,"Denied!");
                        isInvalidCommand = false;
                    }else
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand = false;
                    }else
                    if(items[0].equalsIgnoreCase("gethere")) {
                        getChannelHooks();isInvalidCommand = false;
                    }else
                    if(items[0].contains("[")&&items[0].contains("]")){
                        selectHook(items[0], items);
                    }else
                    if(items[0].equalsIgnoreCase("qcreate")||items[0].equalsIgnoreCase("qbuild")||items[0].equalsIgnoreCase("qnew")){
                        quickHookCreateDefault(event);isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("qsetname")||items[0].equalsIgnoreCase("qsetavatar")||items[0].equalsIgnoreCase("qsetchannel")){
                        quickHookSet(event,items[0]);isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("qreset")){
                        quickHookReset(event);isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("qsendtext")||items[0].equalsIgnoreCase("qsendembed")){
                        quickHookSend(event,items[0]);isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("halsendtext")||items[0].equalsIgnoreCase("halsendembed")){
                        quickHalSend(event,items[0],items,"hal");isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("gladossendtext")||items[0].equalsIgnoreCase("gladossendembed")){
                        quickHalSend(event,items[0],items,"glados");isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("emperorsendtext")||items[0].equalsIgnoreCase("emperorsendembed")){
                        quickHalSend(event,items[0],items,"emperor");isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("lewdfursendtext")||items[0].equalsIgnoreCase("lewdfursendembed")){
                        quickHalSend(event,items[0],items,"lewdfur");isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("cleanchannel")){
                        clearChannelSelfUserHooks();isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("cleanserver")){
                        clearServerSelfUserHooks();isInvalidCommand = false;
                    }
                    if(items[0].equalsIgnoreCase("cleanallservers")){
                        clearAllServersSelfUserHooks();isInvalidCommand = false;
                    }
                }
                logger.info(fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(event);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(event.getAuthor(),"Webhook utility","You provided an incorrect command!", llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(), llColors.llColorRed);
            }
        }

        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(gTitle);
            String quickSummonWithSpace=llPrefixStr+"gWebhook ";
            String desc=" ";
            embed.addField("Get channel hooks","`"+quickSummonWithSpace+"gethere`",false);
            embed.addField("Selected hook","`"+quickSummonWithSpace+"[id] <get,setname,setavatar,setchannel,sendtext>`",false);
            embed.addField("Quick hook","`"+quickSummonWithSpace+"qsetname`"+", `"+quickSummonWithSpace+"qsetavatar`"+", `"+quickSummonWithSpace+"qsetchannel`"+"\n`"+quickSummonWithSpace+"qsendtext`"+", `"+quickSummonWithSpace+"qsendembed`",false);
            embed.addField("Hal profile","`"+quickSummonWithSpace+"halsendtext`"+", `"+quickSummonWithSpace+"halsendembed`",false);
            if(lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)) {
                embed.addField("Glados profile", "`" + quickSummonWithSpace + "gladossendtext`" + ", `" + quickSummonWithSpace + "gladossendembed`", false);
                embed.addField("Emperor profile", "`" + quickSummonWithSpace + "emperorsendtext`" + ", `" + quickSummonWithSpace + "emperorsendembed`", false);
                embed.addField("LewdFyr profile", "`" + quickSummonWithSpace + "lewdfursendtext`" + ", `" + quickSummonWithSpace + "lewdfursendembed`", false);
            }
            embed.addField("Clear hooks","`"+quickSummonWithSpace+"cleanchannel`"+"\n`"+quickSummonWithSpace+"cleanserver`"+"\n`"+quickSummonWithSpace+"cleanallservers`",false);
            //desc+="\n`"+quickSummonWithSpace+"embedbuild`";
            //desc+="\n`"+quickSummonWithSpace+"'";
            lsMessageHelper.lsSendMessage(gTextChannel, "I sent you a help list in DMs");
            lsMessageHelper.lsSendMessage(gUser,embed);
        }

        private void clearChannelSelfUserHooks(){
            String fName="[clearChannelSelfUserHooks]";
            try{
                if(llMemberIsStaff(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear channel main.java.bot hooks","Denied",llColors.llColorRed);
                    return;
                }
                List<Webhook>gWebhooks=gTextChannel.retrieveWebhooks().complete();
                logger.info(fName+"gWebhooks.size="+gWebhooks.size());
                int countOwn=0,countQueue=0;
                if(gWebhooks.size()>=5){
                    for(Webhook gWebhookEntry:gWebhooks){
                        if(gWebhookEntry.getOwner().equals(gTextChannel.getJDA().getSelfUser().getId())){
                            countOwn++;
                            OffsetDateTime then=gWebhookEntry.getTimeCreated();
                            OffsetDateTime current=OffsetDateTime.now();
                            logger.info(fName+"then("+gWebhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                            if(then.getMinute()>current.getMinute()||then.getHour()!=current.getHour()){
                                logger.info(fName+"queue for deletion");
                                gWebhookEntry.delete().queue();
                                countQueue++;
                            }
                        }
                    }
                }
                logger.info(fName+"ended");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear channel main.java.bot hooks","Found in total "+gWebhooks.size()+" weebhooks, found main.java.bot owned hooks "+countOwn+", queued for deleteion:"+countQueue,llColors.llColorOrange);
            }catch (Exception e){
                logger.error(fName+"exception="+e);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear channel main.java.bot hooks","Error",llColors.llColorRed);
            }
        }
        private void clearServerSelfUserHooks(){
            String fName="[clearServerSelfUserHooks]";
            try{
                if(llMemberIsStaff(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear server main.java.bot hooks","Denied",llColors.llColorRed);
                    return;
                }
                List<Webhook>gWebhooks=gGuild.retrieveWebhooks().complete();
                logger.info(fName+"gWebhooks.size="+gWebhooks.size());
                int countOwn=0,countQueue=0;
                if(gWebhooks.size()>=5){
                    for(Webhook gWebhookEntry:gWebhooks){
                        if(gWebhookEntry.getOwner().equals(gTextChannel.getJDA().getSelfUser().getId())){
                            countOwn++;
                            OffsetDateTime then=gWebhookEntry.getTimeCreated();
                            OffsetDateTime current=OffsetDateTime.now();
                            logger.info(fName+"then("+gWebhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                            if(then.getMinute()>current.getMinute()||then.getHour()!=current.getHour()){
                                logger.info(fName+"queue for deletion");
                                gWebhookEntry.delete().queue();
                                countQueue++;
                            }
                        }
                    }
                }
                logger.info(fName+"ended");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear server main.java.bot hooks","Found in total "+gWebhooks.size()+" weebhooks, found main.java.bot owned hooks "+countOwn+", queued for deleteion:"+countQueue,llColors.llColorOrange);
            }catch (Exception e){
                logger.error(fName+"exception="+e);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear server main.java.bot hooks","Error",llColors.llColorRed);
            }
        }
        private void clearAllServersSelfUserHooks(){
            String fName="[clearServerSelfUserHooks]";
            try{
                if(!lsMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear all servers main.java.bot hooks","Denied",llColors.llColorRed);
                    return;
                }
                List<Guild>guilds=gGlobal.getGuildList();
                if(guilds==null||guilds.isEmpty()||guilds.size()==0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear all servers main.java.bot hooks","No guilds",llColors.llColorRed);
                    return;
                }
                int countGuild=0,countWebhooks=0,countOwn=0,countQueue=0;
                for(Guild guild:guilds){
                    countGuild++;
                    List<Webhook>gWebhooks=gGuild.retrieveWebhooks().complete();
                    logger.info(fName+"gWebhooks.size="+gWebhooks.size());
                    if(gWebhooks.size()>=5){
                        for(Webhook gWebhookEntry:gWebhooks){
                            countWebhooks++;
                            if(gWebhookEntry.getOwner().equals(gTextChannel.getJDA().getSelfUser().getId())){
                                countOwn++;
                                OffsetDateTime then=gWebhookEntry.getTimeCreated();
                                OffsetDateTime current=OffsetDateTime.now();
                                logger.info(fName+"then("+gWebhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                                if(then.getMinute()>current.getMinute()||then.getHour()!=current.getHour()){
                                    logger.info(fName+"queue for deletion");
                                    gWebhookEntry.delete().queue();
                                    countQueue++;
                                }
                            }
                        }
                    }
                }

                logger.info(fName+"ended");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear all servers main.java.bot hooks","Found in total "+countOwn+" owned hooks, queued for deleteion:"+countQueue,llColors.llColorOrange);
            }catch (Exception e){
                logger.error(fName+"exception="+e);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,"Clear all servers main.java.bot hooks","Error",llColors.llColorRed);
            }
        }
        private void getChannelHooks(){
            String fName="[getChannelHooks]";

            logger.info(fName+".channel="+gTextChannel.getId()+"|"+gTextChannel.getName());
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(8388863);
            embed.setTitle("Channel gWebhooks");
            String desc="**Channel:**"+gTextChannel.getAsMention();
            try{
                List<Webhook> gWebhooks=gTextChannel.retrieveWebhooks().complete(true);
                logger.info(fName+".gWebhooks.size="+gWebhooks.size());
                for(Webhook item :  gWebhooks){
                    logger.info(fName+".gWebhook:"+item.getId()+"|"+item.getName());
                    desc+="\n"+item.getId()+"|"+item.getName();
                }
            }catch (Exception e){
                logger.info(fName+"error:"+e);
                desc+="\nError";
            }
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void selectHook(String id, String items[]){
            String fName="[selectHook]";
            id=id.replace("[","").replace("]","");
            logger.info(fName+".id="+id);
            lcWebHookBuild whh= new lcWebHookBuild();
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(8388863);
            embed.setTitle("Get info of gWebhook");
            String desc="";
            if(!whh.select4Guild(gGuild,id)){
                embed.setTitle("No such gWebhook");
                desc+="\nFound no gWebhook with id:"+id;
                desc+="\nGuild searched:"+gGuild.getName();
                logger.warn(fName+".no such id hook found"); return;
            }
            if(items.length>=2){
                logger.info(fName+".items[1]="+items[1]);
                if(items[1].equalsIgnoreCase("get")){
                    embed.setTitle("Get info of gWebhook");
                    desc+="\nId:"+whh.gWebhook.getId();
                    desc+="\nUrl:"+whh.gWebhook.getUrl();
                    desc+="\nName:"+whh.gWebhook.getName();
                    desc+="\nGuild"+whh.gWebhook.getGuild().getName()+"|"+whh.gWebhook.getGuild().getId();
                    desc+="\nChannel"+whh.gWebhook.getChannel().getAsMention();
                    embed.setDescription(desc);
                    lsMessageHelper.lsSendMessage(gUser,embed);
                }else
                if(items[1].equalsIgnoreCase("setname")){
                    embed.setTitle("Set name"); desc+="\nId:"+whh.gWebhook.getId();
                    String tmp="";
                    for(int i=2;i<items.length;i++){
                        tmp+=" "+items[i];
                    }
                    logger.info(fName+".new name="+tmp);
                    whh.set(tmp);
                    embed.setDescription(desc);
                    lsMessageHelper.lsSendMessage(gUser,embed);
                }else
                if(items[1].equalsIgnoreCase("setavatar")){
                    List<Message.Attachment> attachments= gCommandEvent.getMessage().getAttachments();
                    embed.setTitle("Set Avatar"); desc+="\nId:"+whh.gWebhook.getId();
                    logger.info(fName+".attachments.size="+attachments.size());
                    if(attachments.size()>0){
                        Message.Attachment attachment=attachments.get(0);
                        try{
                            if(!attachment.isImage()){
                                quickHookResponse(gUser,"Error attachment is not an image", colorError);
                                logger.error(fName+".attachment is not an image");
                                return;
                            }
                            CompletableFuture<Icon> a=attachment.retrieveAsIcon();
                            logger.info(fName+".set icon");
                            whh.set(a.get());
                        }catch (Exception e){
                            logger.error(fName+"exception="+e);
                        }
                    }
                    embed.setDescription(desc);
                    lsMessageHelper.lsSendMessage(gUser,embed);
                }else
                if(items[1].equalsIgnoreCase("setchannel")){
                    embed.setTitle("Set Channel"); desc+="\nId:"+whh.gWebhook.getId();
                    List<TextChannel> channels= gCommandEvent.getMessage().getMentionedChannels();
                    logger.info(fName+".mentionedChannels.size="+channels.size());
                    if(channels.size()>0){
                        logger.warn(fName+"channel:"+channels.get(0).getId()+"|"+channels.get(0).getName());
                        whh.set(channels.get(0));
                    }
                    embed.setDescription(desc);
                    lsMessageHelper.lsSendMessage(gUser,embed);
                }else
                if(items[1].equalsIgnoreCase("sendtext")){
                    logger.info(fName+"text");
                    if(whh.clientOpen()){
                        logger.info(fName+".client ok");
                        String title="Webhook SendText";
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,title, "Please enter you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColors.llColorPurple1);
                        gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                                // make sure it's by the same user, and in the same channel, and for safety, a different message
                                e -> e.getAuthor().equals(gUser),
                                // respond, inserting the name they listed into the response
                                e -> {
                                    String content = e.getMessage().getContentStripped();
                                    if (content.isEmpty()) {
                                        logger.warn(fName + ".no content");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "No text!", llColors.llColorRed);
                                        return;
                                    }
                                    try {
                                        logger.info(fName + "text=" + content);
                                        if(content.equalsIgnoreCase("cancel")){
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Canceled!", llColors.llColorRed);
                                            return;
                                        }
                                        whh.send(content);
                                        whh.clientClose();
                                    } catch (Exception ex) {
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Failed!", llColors.llColorRed);
                                    }
                                },
                                // if the user takes more than a minute, time out
                                1, TimeUnit.MINUTES, () -> {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,title, "Timeout", llColors.llColorRed);
                                });

                    }
                }else
                if(items[1].equalsIgnoreCase("sendembed")){
                    logger.info(fName+"embed");
                    if(whh.clientOpen()){
                        String title="Webhook SendEmbed";
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,title, "Please enter you want to post.\nIf you want to cancel creating the letter type `cancel`.", llColors.llColorPurple1);
                        gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                                // make sure it's by the same user, and in the same channel, and for safety, a different message
                                e -> e.getAuthor().equals(gUser),
                                // respond, inserting the name they listed into the response
                                e -> {
                                    String content = e.getMessage().getContentStripped();
                                    if (content.isEmpty()) {
                                        logger.warn(fName + ".no content");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "No text!", llColors.llColorRed);
                                        return;
                                    }
                                    try {
                                        logger.info(fName + "text=" + content);
                                        if(content.equalsIgnoreCase("cancel")){
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Canceled!", llColors.llColorRed);
                                            return;
                                        }
                                        JSONObject json=new JSONObject(content);

                                    } catch (Exception ex) {
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Failed!", llColors.llColorRed);
                                    }
                                },
                                // if the user takes more than a minute, time out
                                1, TimeUnit.MINUTES, () -> {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Timeout", llColors.llColorRed);
                                });
                    }
                }
            }
        }

        private JSONObject embedEntries= new JSONObject();


        private int colorError=16397829;
        private int colorInputTimout=16397829;
        private int colorInputRequest=8421631;
        private int colorSuccess =32768;
        private int colorInputDone =32768;
        private void hookEmbedResponse(User user, String desc, int color){
            String fName="[hookEmbedResponse]";
            logger.info(fName+":"+user.getId()+"|"+desc);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(color);
            embed.setTitle("WebHook Embed Builder");
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(user,embed);
        }
        private void quickHookResponse(User user, String desc, int color){
            String fName="[quickHookResponse]";
            logger.info(fName+":"+user.getId()+"|"+desc);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(color);
            embed.setTitle("Quick Hook");
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(user,embed);
        }
        private void quickHookResponse(TextChannel channel, String desc, int color){
            String fName="[quickHookResponse]";
            logger.info(fName+":"+channel.getId()+"|"+desc);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(color);
            embed.setTitle("Quick Hook");
            embed.setDescription(desc);
            MessageEmbed messageEmbed=embed.build();
            //channel.sendMessage(messageEmbed).queue(); outdated since JDA.version 4.3.0+
            channel.sendMessageEmbeds(messageEmbed).queue();
        }
        private void quickHookSet(CommandEvent event,String command){
            String fName="[quickHookSet]";
            User user=event.getAuthor();
            Guild guild=event.getGuild();
            logger.info(fName+".user="+user.getId()+"|"+user.getName());
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName+". command="+command);
            String gWebhookid="";
            gWebhookid=getDefaultWebHook(guild);
            if(gWebhookid==null||gWebhookid.isEmpty()){
                gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
            }
            if(gWebhookid==null||gWebhookid.isEmpty()){
                logger.error(fName+".gWebhookid is empty");
                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                return;
            }
            logger.info(fName+".gWebhookid="+gWebhookid);
            lcWebHookBuild whh= new lcWebHookBuild();

            if(!whh.select4Guild(guild,gWebhookid)){
                logger.error(fName+".no such gWebhook");
                quickHookResponse(user,"No such gWebhook", colorError);
            }else{
                if(command.toLowerCase().contains("setname")){
                    quickHookResponse(user,"Please provide a name", colorInputRequest);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel, and for safety, a different message
                            e -> e.getAuthor().equals(user),
                            // respond, inserting the name they listed into the response
                            e -> { if(whh.set(e.getMessage().getContentRaw())){
                                quickHookResponse(user,"Name set", colorInputDone);
                            }else{quickHookResponse(user,"Error at setting name", colorError);}
                            },
                            // if the user takes more than a minute, time out
                            5, TimeUnit.MINUTES, () ->{quickHookResponse(user,"Timeout", colorInputTimout);});
                }else
                if(command.toLowerCase().contains("setchannel")){
                    TextChannel eChannel=event.getTextChannel();
                    List<TextChannel> channels=event.getMessage().getMentionedChannels();
                    logger.info(fName+".mentionedChannels.size="+channels.size());
                    if(channels.size()>0){
                        logger.warn(fName+"channel:"+channels.get(0).getId()+"|"+channels.get(0).getName());
                        if(whh.set(channels.get(0))){
                            quickHookResponse(eChannel,"Channel set.", colorInputDone);
                        }else{
                            quickHookResponse(eChannel,"Error at setting channel.", colorError);
                        }
                    }else{
                        if(whh.set(eChannel)){
                            quickHookResponse(eChannel,"Channel set.", colorInputDone);
                        }else{
                            quickHookResponse(eChannel,"Error at setting channel.", colorError);
                        }
                    }

                }else
                if(command.toLowerCase().contains("setavatar")){
                    quickHookResponse(user,"Please provide an image via attaching it.", colorInputRequest);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel, and for safety, a different message
                            e -> e.getAuthor().equals(user),
                            // respond, inserting the name they listed into the response
                            e -> {
                                List<Message.Attachment> attachments=e.getMessage().getAttachments();
                                logger.info(fName+".attachments.size="+attachments.size());
                                if(attachments.size()>0){
                                    Message.Attachment attachment=attachments.get(0);
                                    try{
                                        if(!attachment.isImage()){
                                            quickHookResponse(user,"Error attachment is not an image", colorError);
                                            logger.error(fName+".attachment is not an image");
                                            return;
                                        }
                                        CompletableFuture<Icon> a=attachment.retrieveAsIcon();
                                        logger.info(fName+".set icon");
                                        if(whh.set(a.get())){
                                            quickHookResponse(user,"Avatar set", colorInputDone);
                                        }else{
                                            quickHookResponse(user,"Error at setting avatar", colorError);
                                        }
                                    }catch (Exception er){
                                        logger.error(fName+"exception="+er);
                                    }
                                }else{
                                    quickHookResponse(user,"Error at setting avatar. No attachments", colorError);
                                }
                            },
                            // if the user takes more than a minute, time out
                            5, TimeUnit.MINUTES, () ->{quickHookResponse(user,"Timeout", colorInputTimout);});
                }else
                if(command.toLowerCase().contains("reset")){
                    try{
                        String avatarUrl=event.getSelfUser().getAvatarUrl();
                        String avatarName=event.getSelfMember().getEffectiveName();
                        whh.set(avatarName);
                    /*InputStream is = new URL(avatarUrl).openStream();
                    whh.set(Icon.from(is));*/
                        whh.setAvatar(avatarUrl);
                        logger.info(fName+".reseted name and avatar");
                    }catch (Exception er){
                        logger.error(fName+"exception="+er);
                    }
                }
            }
        }
        private  void quickHookReset(CommandEvent event){
            String fName="[quickHookReset]";
            User user=event.getAuthor();
            Guild guild=event.getGuild();
            logger.info(fName+".user="+user.getId()+"|"+user.getName());
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            String gWebhookid="";
            gWebhookid=getDefaultWebHook(guild);
            if(gWebhookid==null||gWebhookid.isEmpty()){
                gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
            }
            if(gWebhookid==null||gWebhookid.isEmpty()){
                logger.error(fName+".gWebhookid is empty");
                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                return;
            }
            logger.info(fName+".gWebhookid="+gWebhookid);
            lcWebHookBuild whh= new lcWebHookBuild();

            if(!whh.select4Guild(guild,gWebhookid)){
                logger.error(fName+".no such gWebhook");
                quickHookResponse(user,"No such gWebhook", colorError);
            }else{
                whh.set2Self(event);
            }
        }
        private void quickHookSend(CommandEvent event,String command){
            String fName="[quickHookSend]";
            User user=event.getAuthor();
            Guild guild=event.getGuild();
            logger.info(fName+".user="+user.getId()+"|"+user.getName());
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName+". command="+command);

            if(command.toLowerCase().contains("text")){
                quickHookResponse(user,"Please provide a text to send.", colorInputRequest);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(user),
                        // respond, inserting the name they listed into the response
                        e -> {
                            String gWebhookid="";
                            gWebhookid=getDefaultWebHook(guild);
                            if(gWebhookid==null||gWebhookid.isEmpty()){
                                gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
                            }
                            if(gWebhookid==null||gWebhookid.isEmpty()){
                                logger.error(fName+".gWebhookid is empty");
                                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                                return;
                            }
                            logger.info(fName+".gWebhookid="+gWebhookid);
                            lcWebHookBuild whh= new lcWebHookBuild();
                            if(!whh.select4Guild(guild,gWebhookid)){ quickHookResponse(user,"No such gWebhook", colorError);return;}
                            try{
                                Thread.sleep(5000);
                                if(whh.clientOpen()){
                                    logger.info(fName+".sendText");
                                    whh.send(e.getMessage().getContentRaw());
                                    quickHookResponse(user,"Text sent.", colorInputDone);
                                    whh.clientClose();
                                }else{quickHookResponse(user,"Error at opening client", colorError);}
                            }catch (Exception ex){
                                whh.clientClose();logger.error(fName+".exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        5, TimeUnit.MINUTES, () ->{quickHookResponse(user,"Timeout", colorInputTimout);});
            }else
            if(command.toLowerCase().contains("embed")){
                logger.info(fName+".sendEmbed");
                WebhookEmbedBuilder wembed = new WebhookEmbedBuilder();
                String kUser="g_"+user.getId();
                logger.info(fName+".key="+kUser);
                if(!embedEntries.has(kUser)){
                    logger.warn(fName+"embed entity is null");
                    quickHookResponse(user,"No embed entry found.", colorError);
                }else{
                    if(!embedEntries.getJSONObject(kUser).has("main")){
                    }else{
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("description")){
                            logger.info(fName+"entry:"+kUser+".main.description="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("description"));
                            wembed.setDescription(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("description"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("image")){
                            logger.info(fName+"entry:"+kUser+".main.image="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("image"));
                            wembed.setImageUrl(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("image"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("thumbnail")){
                            logger.info(fName+"entry:"+kUser+".main.thumbnail="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("thumbnail"));
                            wembed.setThumbnailUrl(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("thumbnail"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("color")){
                            logger.info(fName+"entry:"+kUser+".main.color="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("color"));
                            wembed.setColor(embedEntries.getJSONObject(kUser).getJSONObject("main").getInt("color"));
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("auth")){
                    }else{
                        String authName=""; String authIcon=""; String authUrl="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("name")){
                            logger.info(fName+"entry:"+kUser+".auth.name="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("name"));
                            authName=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("name");
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("image")){
                            logger.info(fName+"entry:"+kUser+".auth.image="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("image"));
                            authIcon=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("image");
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("url")){
                            logger.info(fName+"entry:"+kUser+".auth.url="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("url"));
                            authUrl=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("url");
                        }
                        if(!authName.isEmpty()||!authIcon.isEmpty()){
                            WebhookEmbed.EmbedAuthor embedAuthor= new WebhookEmbed.EmbedAuthor(authName, authIcon,authUrl);
                            wembed.setAuthor(embedAuthor);
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("title")){
                    }else{
                        String titleText=""; String titleUrl="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("title").has("text")){
                            logger.info(fName+"entry:"+kUser+".title.text="+embedEntries.getJSONObject(kUser).getJSONObject("title").get("text"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("title").has("url")){
                            logger.info(fName+"entry:"+kUser+".title.url="+embedEntries.getJSONObject(kUser).getJSONObject("title").get("url"));
                        }
                        if(!titleText.isEmpty()){
                            WebhookEmbed.EmbedTitle embedTitle= new WebhookEmbed.EmbedTitle(titleText,titleUrl);
                            wembed.setTitle(embedTitle);
                        }

                    }
                    if(!embedEntries.getJSONObject(kUser).has("footer")){
                    }else{
                        String footerText=""; String footerIcon="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("footer").has("text")){
                            logger.info(fName+"entry:"+kUser+".footer.text="+embedEntries.getJSONObject(kUser).getJSONObject("footer").get("text"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("footer").has("icon")){
                            logger.info(fName+"entry:"+kUser+".footer.icon="+embedEntries.getJSONObject(kUser).getJSONObject("footer").get("icon"));
                        }
                        if(!footerText.isEmpty()){
                            WebhookEmbed.EmbedFooter embedFooter= new WebhookEmbed.EmbedFooter(footerText,footerIcon);
                            wembed.setFooter(embedFooter);
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("fields")){
                    }else{
                        int length=embedEntries.getJSONObject(kUser).getJSONArray("fields").length();
                        logger.info(fName+"entry:"+kUser+".fields->length="+length);
                    }
                    if(wembed.isEmpty()){
                        logger.warn(fName+"wembed.isEmpty");
                    }else{
                        logger.warn(fName+"wembed build");
                        String gWebhookid="";
                        gWebhookid=getDefaultWebHook(guild);
                        if(gWebhookid==null||gWebhookid.isEmpty()){
                            gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
                        }
                        if(gWebhookid==null||gWebhookid.isEmpty()){
                            logger.error(fName+".gWebhookid is empty");
                            lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                            return;
                        }
                        logger.info(fName+".gWebhookid="+gWebhookid);
                        lcWebHookBuild whh= new lcWebHookBuild();
                        if(!whh.select4Guild(guild,gWebhookid)){ quickHookResponse(user,"No such gWebhook", colorError);return;}
                        try{
                            Thread.sleep(5000);
                            if(whh.clientOpen()){
                                logger.info(fName+".sendText");
                                whh.send(wembed.build());
                                quickHookResponse(user,"Text sent.", colorInputDone);
                                whh.clientClose();
                            }else{quickHookResponse(user,"Error at opening client", colorError);}
                        }catch (Exception ex){
                            whh.clientClose();logger.error(fName+".exception:"+ex);
                        }
                    }
                }
            }
        }
        private  void quickHookCreateDefault(CommandEvent event){
            String fName="[quickHookCreateDefault]";
            User user=event.getAuthor();
            Guild guild=event.getGuild();
            logger.info(fName+".user="+user.getId()+"|"+user.getName());
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            String gWebhookid="";
            gWebhookid=getDefaultWebHook(guild);
            if(gWebhookid==null||gWebhookid.isEmpty()){
                gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Create Default","Already exists!", llColors.llColorOrange);
                return;
            }
            if(gWebhookid==null||gWebhookid.isEmpty()){
                logger.error(fName+".gWebhookid is empty");
                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Create Default","Failed to create default!", llColors.llColorRed);
                return;
            }
            logger.info(fName+".gWebhookid="+gWebhookid);
            lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Create Default","Default created!", llColors.llColorBlue1);
        }
        private String webHookDb="gWebhooks";
        private String getDefaultWebHook(Guild guild){
            String fName="[getDefaultWebHook]";
            String id="";
            lcSqlConnEntity sql = new lcSqlConnEntity();
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            if (!sql.startDriver()) {
                logger.error(fName + ".diver failed");
                return null;
            }
            if (!sql.openConnection()) {
                logger.error(fName + ".no open connection");
                return null;
            }
            List<String> colums=new ArrayList<String>();
            colums.add("id"); colums.add("name"); colums.add("guild_id");
            Map<String,Object> rows=sql.selectFirst(webHookDb,"name = 'default' and guild_id='"+guild.getId()+"'",colums);

            if(rows==null){logger.warn(fName+".select null"); sql.closeConnection();return null; }
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("id")&&value!=null){
                    id= value.toString();
                    logger.info(fName + ".default one found= "+id);
                }
            }
            try{
                List<Webhook> items=guild.retrieveWebhooks().complete(true);
                logger.info(fName+".items.size="+items.size());
                for(Webhook item :  items){
                    logger.info(fName+".item:"+item.getId()+"|"+item.getName());
                    if(item.getId().equals(id)){
                        logger.info(fName+".item:match");
                        sql.closeConnection();return id;
                    }
                }
                logger.info(fName+".no match found");
                if(sql.delete(webHookDb,"name = 'default' and guild_id='"+guild.getId()+"'")){
                    logger.info(fName+".delete success");
                }else{
                    logger.warn(fName+".delete failed");
                }
                sql.closeConnection();return null;
            }catch (Exception e){
                logger.info(fName+"error:"+e);
                sql.closeConnection();return null;
            }
        }
        private String newDefaultWebHook(Guild guild,TextChannel textChannel){
            String fName="[newDefaultWebHook]";
            lcSqlConnEntity sql = new lcSqlConnEntity();
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName+".textChannel="+textChannel.getId()+"|"+textChannel.getName());
            if (!sql.startDriver()) {
                logger.error(fName + ".diver failed");
                return null;
            }
            if (!sql.openConnection()) {
                logger.error(fName + ".no open connection");
                return null;
            }
            lcWebHookBuild whh= new lcWebHookBuild();
            if(!whh.preBuild(textChannel)){logger.error(fName + "failed prebuild"); return null;}
            if(!whh.build()){logger.error(fName + "failed build"); return null;}
            String id=whh.gWebhook.getId();
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put("name","default");
            data.put("guild_id",guild.getId());
            data.put("id",id);
            if (!sql.insert(webHookDb, data)) {
                logger.error(fName + ".error while inserting"); return null;
            }else{
                logger.warn(fName + ".success inser");
            }
            Boolean isFound=false;
            try{
                List<Webhook> items=guild.retrieveWebhooks().complete(true);
                logger.info(fName+".items.size="+items.size());
                for(Webhook item :  items){
                    logger.info(fName+".item:"+item.getId()+"|"+item.getName());
                    if(item.getId().equals(id)){
                        logger.info(fName+".item:match");
                        isFound=true;
                    }
                }
                if(!isFound){
                    logger.info(fName+".no match found");
                    return null;
                }
            }catch (Exception e){
                logger.info(fName+"error:"+e);
                return null;
            }
            Member selfMember=guild.getSelfMember();
            whh.set(selfMember.getEffectiveName());
            String avatarUrl=selfMember.getUser().getAvatarUrl();
            logger.info(fName + ".avatarUrl="+avatarUrl);
            whh.setAvatar(avatarUrl);
            return id;
        }
        private void characterSet(lcWebHookBuild whh, String character){
            String fName="[characterSet]";
            if(character.equalsIgnoreCase("hal")){
                whh.set("Hal");
                whh.setAvatar("https://p7.hiclipart.com/preview/2/349/82/hal-9000-clip-art-hal-cliparts-thumbnail.jpg");
                logger.info(fName+".set name and avatar to Hal");
            }
            if(character.equalsIgnoreCase("glados")){
                whh.set("GLaDOS");
                whh.setAvatar("https://freepngimg.com/thumb/portal/88205-technology-portal-shoe-glados-free-download-image.png");
                logger.info(fName+".set name and avatar to GLaDOS");
            }
            if(character.equalsIgnoreCase("lewd")){
                whh.set("Lewd Fur");
                whh.setAvatar("https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560623236093-facialmarkedb3.png");
                logger.info(fName+".set name and avatar to Lewd Fur");
            }
            if(character.equalsIgnoreCase("emperor")){
                whh.set("Emperor");
                whh.setAvatar("https://d.facdn.net/art/imperatorphilo/1575316852/1575316852.imperatorphilo_by_juzztie.png");
                logger.info(fName+".set name and avatar to Emperor.");
            }
        }
        private void quickHalSend(CommandEvent event,String command, String[] items, String character){
            String fName="[quickHalSend]";
            User user=event.getAuthor();
            Guild guild=event.getGuild();
            logger.info(fName+".user="+user.getId()+"|"+user.getName());
            logger.info(fName+".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName+". command="+command);
            logger.info(fName+". items.size="+items.length);
            logger.info(fName+". character="+command);

            if(command.toLowerCase().contains("text")){
                quickHookResponse(user,"Please provide a text to send.", colorInputRequest);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(user),
                        // respond, inserting the name they listed into the response
                        e -> {
                            String gWebhookid="";
                            gWebhookid=getDefaultWebHook(guild);
                            if(gWebhookid==null||gWebhookid.isEmpty()){
                                gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
                            }
                            if(gWebhookid==null||gWebhookid.isEmpty()){
                                logger.error(fName+".gWebhookid is empty");
                                lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                                return;
                            }
                            logger.info(fName+".gWebhookid="+gWebhookid);
                            lcWebHookBuild whh= new lcWebHookBuild();
                            if(!whh.select4Guild(guild,gWebhookid)){return;}
                            characterSet( whh, character);
                            try{
                                Thread.sleep(5000);
                                if(whh.clientOpen()){
                                    if(items.length<2){
                                        logger.info(fName+".sendText.basic");
                                        whh.send(e.getMessage().getContentRaw());
                                        quickHookResponse(user,"Text sent.", colorInputDone);

                                    }else{
                                        logger.info(fName+".sendText.embed");
                                        WebhookEmbedBuilder wembed = new WebhookEmbedBuilder();
                                        wembed.setDescription(e.getMessage().getContentRaw());
                                        logger.info(fName+".sendText.setcolor="+items[1]);
                                        wembed.setColor(Integer.valueOf(items[1]));
                                        whh.send(wembed.build());
                                    }
                                    whh.clientClose(); whh.set2Self(event);
                                }else{quickHookResponse(user,"Error at opening client", colorError);}
                            }catch (Exception ex){
                                whh.clientClose(); whh.set2Self(event);logger.error(fName+".exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        5, TimeUnit.MINUTES, () ->{quickHookResponse(user,"Timeout", colorInputTimout);});
            }
            if(command.toLowerCase().contains("embed")){
                logger.info(fName+".sendEmbed");
                WebhookEmbedBuilder wembed = new WebhookEmbedBuilder();
                String kUser="g_"+user.getId();
                logger.info(fName+".key="+kUser);
                if(!embedEntries.has(kUser)){
                    logger.warn(fName+"embed entity is null");
                    quickHookResponse(user,"No embed entry found.", colorError);
                }else{
                    if(!embedEntries.getJSONObject(kUser).has("main")){
                        logger.info(fName+"entry:"+kUser+".main:no such key");
                    }else{
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("description")){
                            logger.info(fName+"entry:"+kUser+".main.description="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("description"));
                            wembed.setDescription(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("description"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("image")){
                            logger.info(fName+"entry:"+kUser+".main.image="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("image"));
                            wembed.setImageUrl(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("image"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("thumbnail")){
                            logger.info(fName+"entry:"+kUser+".main.thumbnail="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("thumbnail"));
                            wembed.setThumbnailUrl(embedEntries.getJSONObject(kUser).getJSONObject("main").getString("thumbnail"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("main").has("color")){
                            logger.info(fName+"entry:"+kUser+".main.color="+embedEntries.getJSONObject(kUser).getJSONObject("main").get("color"));
                            wembed.setColor(embedEntries.getJSONObject(kUser).getJSONObject("main").getInt("color"));
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("auth")){
                        logger.info(fName+"entry:"+kUser+".auth:no such key");
                    }else{
                        String authName=""; String authIcon=""; String authUrl="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("name")){
                            logger.info(fName+"entry:"+kUser+".auth.name="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("name"));
                            authName=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("name");
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("image")){
                            logger.info(fName+"entry:"+kUser+".auth.image="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("image"));
                            authIcon=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("image");
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("auth").has("url")){
                            logger.info(fName+"entry:"+kUser+".auth.url="+embedEntries.getJSONObject(kUser).getJSONObject("auth").get("url"));
                            authUrl=embedEntries.getJSONObject(kUser).getJSONObject("auth").getString("url");
                        }
                        if(!authName.isEmpty()||!authIcon.isEmpty()){
                            WebhookEmbed.EmbedAuthor embedAuthor= new WebhookEmbed.EmbedAuthor(authName, authIcon,authUrl);
                            wembed.setAuthor(embedAuthor);
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("title")){
                        logger.info(fName+"entry:"+kUser+".title:no such key");
                    }else{
                        String titleText=""; String titleUrl="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("title").has("text")){
                            logger.info(fName+"entry:"+kUser+".title.text="+embedEntries.getJSONObject(kUser).getJSONObject("title").get("text"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("title").has("url")){
                            logger.info(fName+"entry:"+kUser+".title.url="+embedEntries.getJSONObject(kUser).getJSONObject("title").get("url"));
                        }
                        if(!titleText.isEmpty()){
                            WebhookEmbed.EmbedTitle embedTitle= new WebhookEmbed.EmbedTitle(titleText,titleUrl);
                            wembed.setTitle(embedTitle);
                        }

                    }
                    if(!embedEntries.getJSONObject(kUser).has("footer")){
                        logger.info(fName+"entry:"+kUser+".footer:no such key");
                    }else{
                        String footerText=""; String footerIcon="";
                        if(embedEntries.getJSONObject(kUser).getJSONObject("footer").has("text")){
                            logger.info(fName+"entry:"+kUser+".footer.text="+embedEntries.getJSONObject(kUser).getJSONObject("footer").get("text"));
                        }
                        if(embedEntries.getJSONObject(kUser).getJSONObject("footer").has("icon")){
                            logger.info(fName+"entry:"+kUser+".footer.icon="+embedEntries.getJSONObject(kUser).getJSONObject("footer").get("icon"));
                        }
                        if(!footerText.isEmpty()){
                            WebhookEmbed.EmbedFooter embedFooter= new WebhookEmbed.EmbedFooter(footerText,footerIcon);
                            wembed.setFooter(embedFooter);
                        }
                    }
                    if(!embedEntries.getJSONObject(kUser).has("fields")){
                        logger.info(fName+"entry:"+kUser+".fields:no such key");
                    }else{
                        int length=embedEntries.getJSONObject(kUser).getJSONArray("fields").length();
                        logger.info(fName+"entry:"+kUser+".fields->length="+length);
                    }
                    if(wembed.isEmpty()){
                        logger.warn(fName+"wembed.isEmpty");
                    }else{
                        logger.warn(fName+"wembed build");
                        String gWebhookid="";
                        gWebhookid=getDefaultWebHook(guild);
                        if(gWebhookid==null||gWebhookid.isEmpty()){
                            gWebhookid=newDefaultWebHook(guild,event.getTextChannel());
                        }
                        if(gWebhookid==null||gWebhookid.isEmpty()){
                            logger.error(fName+".gWebhookid is empty");
                            lsMessageHelper.lsSendQuickEmbedMessage(user,"Quick WebHook Send","Found no defautl; gWebhook to use!", llColors.llColorRed);
                            return;
                        }
                        logger.info(fName+".gWebhookid="+gWebhookid);
                        lcWebHookBuild whh= new lcWebHookBuild();
                        if(!whh.select4Guild(guild,gWebhookid)){return;}
                        characterSet( whh, character);
                        try{
                            Thread.sleep(5000);
                            if(whh.clientOpen()){
                                logger.info(fName+".sendText");
                                whh.send(wembed.build());
                                quickHookResponse(user,"Embed sent.", colorInputDone);
                                whh.clientClose();whh.set2Self(event);
                            }else{quickHookResponse(user,"Error at opening client", colorError);}
                        }catch (Exception ex){
                            whh.clientClose();whh.set2Self(event);logger.error(fName+".exception:"+ex);
                        }
                    }
                }
            }
        }








        //////////////
    }
}
