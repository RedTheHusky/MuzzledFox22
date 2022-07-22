package util.inhouse;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.managers.Presence;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilitySelf extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[utilitySelf]";
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public utilitySelf(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        this.name = "Self-utility";
        this.help = "selfutility";
        gGlobal = g;
        this.aliases = new String[]{"utilityself","selfu","selfutility"};
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    public utilitySelf(lcGlobalHelper g,ReadyEvent event){
        String fName=".ReadyEvent";
        gGlobal = g;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        Runnable r = new runLocal(event);
        new Thread(r).start();
        logger.info(fName);
    }

    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;EventWaiter gWaiter;
        ReadyEvent readyEvent;
        private User gUser;private Member gMember;private Guild gGuild;
        private TextChannel gTextChannel;private Message gMessage;
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gWaiter = gGlobal.waiter;
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
        public runLocal(ReadyEvent event) {
            String fName="runReadyEvent";
            logger.info(".run build");
            gWaiter = gGlobal.waiter;
            readyEvent = event;
        }
        @Override
        public void run() {
            String fName = "[run]";
            try {
                if(readyEvent!=null){
                    logger.info(".readyEvent");
                    Thread.sleep(5000);
                    readyEvent.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
                    logger.info(fName+"status set to ONLINE");
                }else{
                    logger.info(".run start");
                    Boolean isInvalidCommand = true;
                    gUser = gCommandEvent.getAuthor();
                    gMember = gCommandEvent.getMember();
                    gGuild = gCommandEvent.getGuild(); gTextChannel = gCommandEvent.getTextChannel();
                    logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                    logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                    logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(fName + ".Args=0");
                        help("main");
                        isInvalidCommand = false;
                    } else {
                        logger.info(fName + ".Args");
                        if (!lsMemberIsBotOwner(gMember)) {
                            llSendQuickEmbedMessage(gUser, "Self utility", "Denied!", llColorRed);
                            llMessageDelete(gCommandEvent);
                            return;
                        }
                        // split the choices on all whitespace
                        String[] items = gCommandEvent.getArgs().split("\\s+");
                        if (items[0].equalsIgnoreCase("system")) {
                            getAppProperties();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("build")) {
                            //getBuild();
                            //isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("uptime")) {
                            getUpTime();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("profile")) {
                            getProfile(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("dmprofile")) {
                            getProfile(true);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("setavatar")) {
                            setAvatar();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("setname")) {
                            setName();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("setactivity")) {
                            if (items.length >= 2) {
                                if (items[1].equalsIgnoreCase("play") || items[1].equalsIgnoreCase("playing")) {
                                    setActivity(items[1]); isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("stream") || items[1].equalsIgnoreCase("streaming")) {
                                    setActivity(items[1]); isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("listen") ) {
                                    setActivity(items[1]); isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("watch") || items[1].equalsIgnoreCase("watching")) {
                                    setActivity(items[1]); isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("0") || items[1].equalsIgnoreCase("1") || items[1].equalsIgnoreCase("2") || items[1].equalsIgnoreCase("3")) {
                                    setActivity(items[1]); isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("help")) {
                                    help("activity"); isInvalidCommand = false;
                                }
                            } else {
                                setActivity(""); isInvalidCommand = false;
                            }
                        }
                        if (items[0].equalsIgnoreCase("setstatus")) {
                            if (items.length >= 2) {
                                if (items[1].equalsIgnoreCase("0") || items[1].equalsIgnoreCase("dnd")) {
                                    setStatus(items[1]);  isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("1") || items[1].equalsIgnoreCase("idle")) {
                                    setStatus(items[1]);  isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("2") || items[1].equalsIgnoreCase("invisible")) {
                                    setStatus(items[1]);  isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("3") || items[1].equalsIgnoreCase("offline")) {
                                    setStatus(items[1]);  isInvalidCommand = false;
                                } else if (items[1].equalsIgnoreCase("4") || items[1].equalsIgnoreCase("online")) {
                                    setStatus(items[1]);  isInvalidCommand = false;
                                } else {
                                    help("status");  isInvalidCommand = false;
                                }
                            } else {
                                help("status");  isInvalidCommand = false;
                            }
                        }
                        if (items[0].equalsIgnoreCase("help")) {
                            if(items.length>=2){
                                if (items[1].equalsIgnoreCase("activity")){
                                    help("activity");
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("status")){
                                    help("status");
                                    isInvalidCommand = false;
                                }
                            }else{
                                help("main");
                                isInvalidCommand = false;
                            }
                        }
                    }
                    logger.info(fName + ".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if (isInvalidCommand) {
                        llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                    }
                    logger.info(".run ended");
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }

        String gTitle="Bot-Self";
        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + "selfutility ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            if (command.equalsIgnoreCase("main")) {
                embed.addField("System","\n`" + quickSummonWithSpace + "uptime`"+" `" + quickSummonWithSpace + "system`",false);
                embed.addField("Bot Profile","\n`" + quickSummonWithSpace + "profile`" + "\n`" + quickSummonWithSpace + "dmprofile`",false);
                embed.addField("Set Bot","\n`" + quickSummonWithSpace + "setAvatar`"+"\n`" + quickSummonWithSpace + "setName`"+"\n`" + quickSummonWithSpace + "setactivity`"+"\n`" + quickSummonWithSpace + "setstatus`",false);


            }else
            if (command.equalsIgnoreCase("activity")) {
                embed.addField("Command","\n`" + quickSummonWithSpace + "setActivity <options>`",false);
                embed.addField("Options","\n0, play, playing"+"\n1, stream, streaming"+"\n2, listen, listening"+"\n3, watch, watching",false);
            }else
            if (command.equalsIgnoreCase("status")) {
                embed.addField("Command","\n`" + quickSummonWithSpace + "status <options>`",false);
                embed.addField("Options","\n0, dnd"+"\n1, idle"+"\n2, invisible"+"\n3, offline"+ "\n4, online",false);
            }
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }

        private void setAvatar() {
            String fName = "[setAvatar]";
            logger.info(fName);
            String title = "Set Self Avatar";
            llSendQuickEmbedMessage(gUser, title, "Please upload an image", llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        List<Message.Attachment> attachments = e.getMessage().getAttachments();
                        logger.info(fName + ".attachments.size=" + attachments.size());
                        if (attachments.size() > 0) {
                            Message.Attachment attachment = attachments.get(0);
                            if (!attachment.isImage()) {
                                llSendQuickEmbedMessage(gUser, title, "Is not an image!", llColorRed);
                                logger.error(fName + ".attachment is not an image");
                                return;
                            }
                            try {
                                URL url = new URL(attachment.getUrl());
                                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                                InputStream is=httpcon.getInputStream();
                                Icon icon=Icon.from(is);
                                gCommandEvent.getJDA().getSelfUser().getManager().setAvatar(icon).complete();
                                //gEvent.getJDA().getSelfUser().getManager().setAvatar(Icon.from(new URL(attachment.getUrl()).openStream())).complete();
                                llSendQuickEmbedMessage(gUser, title, "Avatar set.", llColorGreen1);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to update avatar!", llColorRed);
                            }
                        } else {
                            String content = e.getMessage().getContentStripped();
                            if (content.isEmpty()) {
                                logger.warn(fName + ".no content");
                                llSendQuickEmbedMessage(gUser, title, "No attachment or url!", llColorRed);
                                return;
                            }
                            try {
                                logger.info(fName + "url=" + content);
                                gCommandEvent.getJDA().getSelfUser().getManager().setAvatar(Icon.from(new URL(content).openStream())).complete();
                                llSendQuickEmbedMessage(gUser, title, "Avatar set.", llColorGreen1);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to update avatar!", llColorRed);
                            }
                        }
                    },
                    // if the user takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed));
        }

        private void getProfile( Boolean inDM) {
            String fName = "[getProfile]";
            logger.info(fName);
            String title = "Get Self Profile";
            logger.info(fName + "inDM=" + inDM);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle(title);
            String desc = "**Bots profile**";
            SelfUser selfUser = gCommandEvent.getJDA().getSelfUser();
            String selfAvatarUrl = selfUser.getAvatarUrl();
            String selfName = selfUser.getName();

            Presence presence = gCommandEvent.getJDA().getPresence();
            Activity activity = presence.getActivity();
            assert activity != null;
            String activityType = activity.getType().name();
            logger.info(fName + "activityType=" + activityType);
            String activityName = activity.getName();
            logger.info(fName + "activityName=" + activityName);
            Boolean activityisRich = activity.isRich();
            logger.info(fName + "activityisRich=" + activityisRich);
            RichPresence activityRich;
            if (activityisRich) {
                activityRich = activity.asRichPresence();
                assert activityRich != null;
                logger.info(fName + "activityRich=" + activityRich.toString());
            }
            OnlineStatus onlinestatus = presence.getStatus();
            String onlinestatusName = onlinestatus.name();
            logger.info(fName + "onlinestatusName=" + onlinestatusName);
            desc += "\nname: " + selfName;
            desc += "\navatar: " + selfAvatarUrl;
            desc += "\nStatus: " + onlinestatusName;
            if (!activityisRich) {
                if (activity.getType() == Activity.ActivityType.WATCHING) {
                    desc += "\nActivity: Watching " + activityName;
                } else if (activity.getType() == Activity.ActivityType.LISTENING) {
                    desc += "\nActivity: Listening " + activityName;
                } else if (activity.getType() == Activity.ActivityType.STREAMING) {
                    desc += "\nActivity: Streaming " + activityName;
                } else {
                    desc += "\nActivity: Playing " + activityName;
                }
            }
            embed.setDescription(desc);
            embed.setImage(selfAvatarUrl);
            if (inDM) {
                llSendMessage(gUser, embed);
            } else {
                llSendMessage(gTextChannel, embed);
            }
        }

        private void setName() {
            String fName = "[setName]";
            String title = "Set Self Name";
            logger.info(fName);
            llSendQuickEmbedMessage(gUser, "Self Name Set", "Please enter text for name", llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        String content = e.getMessage().getContentStripped();
                        if (content.isEmpty()) {
                            logger.warn(fName + ".no content");
                            llSendQuickEmbedMessage(gUser, title, "No text!", llColorRed);
                            return;
                        }
                        try {
                            logger.info(fName + "text=" + content);
                            gCommandEvent.getJDA().getSelfUser().getManager().setName(content).complete();
                            llSendQuickEmbedMessage(gUser, title, "Name set.", llColorGreen1);
                        } catch (Exception ex) {
                            llSendQuickEmbedMessage(gUser, title, "Failed to update name!", llColorRed);
                        }
                    },
                    // if the user takes more than a minute, time out
                    5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed));
        }

        private void setActivity(String command) {
            String fName = "[setActivity]";
            String title = "Set Self Activity";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            AtomicInteger type = new AtomicInteger();
            Presence presence = gCommandEvent.getJDA().getPresence();
            logger.info(fName + "presence:" + Objects.requireNonNull(presence.getActivity()).getName() + "|" + presence.getActivity().getType().toString());
            type.set(0);
            if (presence.getActivity().getType() == Activity.ActivityType.LISTENING) {
                type.set(2);
            } else if (presence.getActivity().getType() == Activity.ActivityType.STREAMING) {
                type.set(1);
            } else if (presence.getActivity().getType() == Activity.ActivityType.WATCHING) {
                type.set(3);
            }

            if (command.equalsIgnoreCase("play") || command.equalsIgnoreCase("playing") || command.equalsIgnoreCase("0")) {
                type.set(0);
            } else if (command.equalsIgnoreCase("stream") || command.equalsIgnoreCase("streaming") || command.equalsIgnoreCase("1")) {
                type.set(1);
            } else if (command.equalsIgnoreCase("listen") || command.equalsIgnoreCase("2")) {
                type.set(2);
            } else if (command.equalsIgnoreCase("watch") || command.equalsIgnoreCase("watching") || command.equalsIgnoreCase("3")) {
                type.set(3);
            }
            logger.info(fName + "type=" + type.get());

            llSendQuickEmbedMessage(gUser, "Self Name Set", "Please enter text for activity", llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        String content = e.getMessage().getContentStripped();
                        if (content.isEmpty()) {
                            logger.warn(fName + ".no content");
                            llSendQuickEmbedMessage(gUser, title, "No text!", llColorRed);
                            return;
                        }
                        try {
                            logger.info(fName + "text=" + content);
                            Activity activity;
                            if (type.get() == 2) {
                                activity = Activity.listening(content);
                            } else if (type.get() == 1) {
                                String[] items = content.split("|");
                                if (items.length >= 2) {
                                    activity = Activity.streaming(items[0], items[1]);
                                } else {
                                    llSendQuickEmbedMessage(gUser, title, "Failed to update acitvity! Please provide stream with the split character `|`", llColorRed);
                                    logger.error(fName + ".invalid stream activity text");
                                    return;
                                }
                            } else if (type.get() == 3) {
                                activity = Activity.watching(content);
                            } else {
                                activity = Activity.playing(content);
                            }
                            presence.setActivity(activity);
                            llSendQuickEmbedMessage(gUser, title, "Activity set.", llColorGreen1);
                        } catch (Exception ex) {
                            llSendQuickEmbedMessage(gUser, title, "Failed to update activity!", llColorRed);
                        }
                    },
                    // if the user takes more than a minute, time out
                    5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed));
        }

        private void setStatus(String command) {
            String fName = "[setStatus]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            Presence presence = gCommandEvent.getJDA().getPresence();
            logger.info(fName + "status=" + presence.getStatus().name());
            int type =5;
            if (presence.getStatus() == OnlineStatus.DO_NOT_DISTURB) {
                type = 0;
            }
            else if (presence.getStatus() == OnlineStatus.IDLE) {
                type = 1;
            }
            else if (presence.getStatus() == OnlineStatus.INVISIBLE) {
                type = 2;
            }
            else if (presence.getStatus() == OnlineStatus.OFFLINE) {
                type = 3;
            }
            else if (presence.getStatus() == OnlineStatus.ONLINE) {
                type = 4;
            }
            else if (presence.getStatus() == OnlineStatus.UNKNOWN) {
                type = 5;
            }
            logger.info(fName + "type present=" + type);
            if (command.equalsIgnoreCase("0") || command.equalsIgnoreCase("dnd")) {
                type = 0;
                presence.setStatus(OnlineStatus.DO_NOT_DISTURB);
                logger.info(fName + "type set=" + type);
            } else if (command.equalsIgnoreCase("1") || command.equalsIgnoreCase("idle")) {
                type = 1;
                presence.setStatus(OnlineStatus.IDLE);
                logger.info(fName + "type set=" + type);
            } else if (command.equalsIgnoreCase("2") || command.equalsIgnoreCase("invisible")) {
                type = 2;
                presence.setStatus(OnlineStatus.INVISIBLE);
                logger.info(fName + "type set=" + type);
            } else if (command.equalsIgnoreCase("3") || command.equalsIgnoreCase("offline")) {
                type = 3;
                presence.setStatus(OnlineStatus.OFFLINE);
                logger.info(fName + "type set=" + type);
            } else if (command.equalsIgnoreCase("4") || command.equalsIgnoreCase("online")) {
                type = 4;
                presence.setStatus(OnlineStatus.ONLINE);
                logger.info(fName + "type set=" + type);
            }
            else {
                logger.warn(fName + "not set status");
            }
        }

        private void getUpTime() {
            String fName = "[getUpTime]";
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            long upTime = runtimeBean.getUptime();
            String display=String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(upTime),
                    TimeUnit.MILLISECONDS.toMinutes(upTime) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(upTime)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(upTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(upTime)));

            logger.warn(fName + "uptime: "+display);
            llSendQuickEmbedMessage(gTextChannel,"Up Time",display, llColorBlue1);
        }

        private void getAppProperties(){
            String fName = "[getAppProperties]";
            try{
                String version = System.getProperty("java.version");
                String osArch = System.getProperty("os.arch");
                String osName = System.getProperty("os.name");
                String osVersion = System.getProperty("os.version");
                logger.info(fName+".version="+version);
                logger.info(fName+".osName="+osName);
                logger.info(fName+".osVersion="+osVersion);
                logger.info(fName+".osArch="+osArch);
                String port=System.getenv("PORT");
                String toolOptions=System.getenv("JAVA_TOOL_OPTIONS");
                String javaOpts=System.getenv("JAVA_OPTS");
                logger.info(fName+".port="+port);
                logger.info(fName+".toolOptions="+toolOptions);
                logger.info(fName+".javaOpts="+javaOpts);

                RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
                long upTime = runtimeBean.getUptime();
                //String vmVersion = runtimeBean.getVmVersion();
                String displayUptime=String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(upTime),
                        TimeUnit.MILLISECONDS.toMinutes(upTime) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(upTime)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(upTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(upTime)));

                logger.info(fName + "uptime: "+displayUptime);

                MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                long memoryMaxHeap=memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024);
                long memoryMaxNonHeap=memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024);
                logger.info(fName+"Memory limit" + " heap: " + memoryMaxHeap+ "mb" + " non-heap: " + memoryMaxNonHeap + "mb");
                long memoryUsedHeap=memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
                long memoryUsedNonHeap=memoryBean.getNonHeapMemoryUsage().getUsed() / (1024 * 1024);
                logger.info(fName+"Memory used" + " heap: " + memoryUsedHeap+ "mb" + " non-heap: " + memoryUsedNonHeap + "mb");

                ThreadMXBean threadBean =ManagementFactory.getThreadMXBean();
                int threadPeak=threadBean.getPeakThreadCount();
                int threadCount=threadBean.getThreadCount();
                //long threadCPUUsage=threadBean.getCurrentThreadCpuTime();

                /*Runtime runtime = Runtime.getRuntime();
                long rFreeMemory=runtime.freeMemory();
                long rMaxMemory=runtime.maxMemory();
                long rTotalMemory=runtime.totalMemory();*/

                String JDAVersion=JDAInfo.VERSION;
                String JDAUtilitiesVersion= JDAUtilitiesInfo.VERSION;
                logger.info(fName+"JDAVersion="+JDAVersion);logger.info(fName+"JDAUtilitiesVersion="+JDAUtilitiesVersion);
                 EmbedBuilder embed=new EmbedBuilder();
                /*String desc="version: "+version;
                desc+="\n**OS**\nname:"+osName+"\nversion:"+osVersion+"\narch: ";
                desc+="\n**Other**\nport:"+port+"\ntoolOptions:"+toolOptions+"\njavaOpts: ";*/
                embed.addField("Versions","jave:"+version+"jda:"+JDAVersion+"\njdautility:"+JDAUtilitiesVersion,true);
                embed.addField("OS","name:"+osName+"\nversion:"+osVersion+"\narch: "+osArch,true);
                embed.addField("Enviroment","port:"+port+"\ntoolOptions:"+toolOptions+"\njavaOpts: "+javaOpts,true);
                embed.addBlankField(false);
                embed.addField("Uptime",displayUptime,true);
                embed.addField("Memory Used","heap: " + memoryUsedHeap+ "mb" + "\nnon-heap: " + memoryUsedNonHeap + "mb",true);
                embed.addField("Memory Max","heap: " + memoryMaxHeap+ "mb" + "\nnon-heap: " + memoryMaxNonHeap + "mb",true);
                embed.addField("Thread","count: " + threadCount+ "\npeak: " + threadPeak,true);
                embed.setColor(llColorBlue1);embed.setTitle(gTitle);
                //embed.addBlankField(false);
                //embed.addField("Last Modified", getStrLastModified(),true);
                llSendMessage(gTextChannel,embed);
            }catch (Exception ex){
                logger.error(fName+"exception:"+ex);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void getBuild(){
            String fName = "[getBuild]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            embed.addField("Important Libraries", getStrLibrary(),true);
            embed.addBlankField(false);
            embed.addField("Compiled Version", String.valueOf(llCompiledVersion),true);
            embed.addField("Last Modified", getStrLastModified(),true);
            llSendMessage(gTextChannel,embed);
        }
        private String getStrLibrary(){
            String fName = "[getStrLastModified]";
            logger.info(fName );
            JSONObject dependencies=new JSONObject();
            dependencies.put("JDA","4.1.1_127");
            dependencies.put("jda-utilities","3.0.3");
            dependencies.put("unirest-java","3.7.00");
            dependencies.put("mysql-connector-java","8.0.19");
            dependencies.put("discord-webhooks","0.3.0");
            StringBuilder result= new StringBuilder();
            Iterator<String>keys=dependencies.keys();
            while(keys.hasNext()){
                String key=keys.next();
                String version=dependencies.getString(key);
                result.append(" ").append(key).append("[").append(version).append("]");
            }
            return result.toString();
        }
        private String getStrLastModified(){
            String fName = "[getStrLastModified]";
            try{
                URL uManifest;// Thread.currentThread().getContextClassLoader().getResource("META-INF/MANIFEST.MF");
                uManifest=this.getClass().getClassLoader().getResource("META-INF/MANIFEST.MF");
                if(uManifest!=null){
                    logger.info(fName + "url got");
                    File fManifest=new File(uManifest.toURI());
                    if(fManifest.exists()){
                        logger.info(fName + "file got");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(fManifest.lastModified());
                        String result=calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
                        logger.info(fName + "result="+result);
                        return result;
                    }else{logger.warn(fName + "invalid file");return "!FILE";}
                }else{logger.warn(fName + "invalid url");return "!URL";}
            }catch (Exception ex){
                logger.error(fName+"exception:"+ex);return "!EXC";
            }
        }
    }
}
