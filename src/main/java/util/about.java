package util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsAssets;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class about extends Command implements llMessageHelper, llGlobalHelper {
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass()); String cName="[information]";
    public about(lcGlobalHelper g) {
        String fName = "[constructor]";
        logger.info(fName);
        gGlobal=g;gWaiter = g.waiter;
        this.name = "About-Bot";
        this.help = "Information about the bot";
        this.aliases = new String[]{"about"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;
        this.category=llCommandCategory_SFW;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();

    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage;

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
            String fName = "[run]";
            logger.info(".run start");
            boolean isInvalidCommand = true;
            try {
                String[] items;
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    showMain();isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("main")){
                        showMain();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("prog")){
                        showAcknowledgements();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("contact")){
                        showContact();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("donation")||items[0].equalsIgnoreCase("donate")||items[0].equalsIgnoreCase("support")||items[0].equalsIgnoreCase("cash")){
                        showSupport();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("system")||items[0].equalsIgnoreCase("stats")){
                        showSystem();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("legality")||items[0].equalsIgnoreCase("privacy")){
                        showLegality();isInvalidCommand=false;
                    }
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gMessage);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gUser,"Info","You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String gTitle="About";
        private void showMain(){
            String fName = "[showAcknowledgements]";
            try{
                //Acknowledgements

                EmbedBuilder embed=new EmbedBuilder();
                embed.addField("**ReadMe**","The bot is still under development.\nTarget preference: NSFW channels or servers",false);
                embed.addField("Biography","Created to indulge your bdsm fantasies.\nI'm a gag bot that will gag you and force you to say 'Yes, Sir!'\nI will place your in a heavy restraints and lock away your tools.\nAdditionally i come with some other functions and utilities.",false);
                embed.addField("Top Feature","Gag & BDSM Commands\nAdding role to custom emoji, downloading all custom emojis for backup and non-nitro using server custom gif emojis.\nMultiple type of character organizer(limited to server)\nChastity&Diaper Verification\nExporting in json a list of members/banned/roles/channels/audit-logs",false);
                String desc= lsUsefullFunctions.getUrlTextString("Bot Invite",lsAssets.urlBotInvite);
                desc+="\n"+lsUsefullFunctions.getUrlTextString(lsAssets.nameLink2Forum,lsAssets.urlLink2Forum);
                desc+="\n"+lsUsefullFunctions.getUrlTextString(lsAssets.nameLink2BotServer,lsAssets.urlLink2BotSerer);
                desc+="\n"+lsUsefullFunctions.getUrlTextString(lsAssets.nameLink2BDSMServer,lsAssets.urlLink2BDSMServer);
                desc+="\n"+lsUsefullFunctions.getUrlTextString(lsAssets.nameLink2PayPal, lsAssets.urlLink2PayPal);
                desc+="\n"+lsUsefullFunctions.getUrlTextString(lsAssets.nameLink2Patreon,lsAssets.urlLink2Patreon);
                embed.addField("Links",desc,false);
                embed.addField("Contacts","To get the contacts type `"+llPrefixStr+"about contact`",false);
                embed.addField("Donation/support","Support the bot by donating `"+llPrefixStr+"about donate`",false);
                embed.addField("Programmed","To get the tools&libraries used type `"+llPrefixStr+"about prog`",false);
                embed.addField("Stats","To get the stats type `"+llPrefixStr+"about stats`",false);
                embed.addField("Legality & Privacy","Type `"+llPrefixStr+"about legality`, `"+llPrefixStr+"about privacy`",false);
                embed.setColor(llColorPurple2);embed.setTitle(gTitle);
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void showSupport(){
            String fName = "[showAcknowledgements]";
            try{
                //Acknowledgements

                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);embed.setTitle("Support");embed.setDescription("Support the bot by donating to the creator [PayPal]("+lsAssets.urlLink2PayPal+") or [Patreon]("+lsAssets.urlLink2Patreon+").\nIts not free hosting the bot and takes time coding it.");
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void showContact(){
            String fName = "[showContact]";
            try{
                //Acknowledgements

                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);embed.setTitle("Contact");
                String desc="";
                desc="Discord: Red The Husky#9918";
                desc+="\n[Main Server]("+lsAssets.urlLink2BDSMServer+")";
                desc+="\n[Support Server]("+lsAssets.urlLink2BotSerer+")";
                desc+="\n[Forum]("+lsAssets.urlLink2Forum+")";
                desc+="\n[TWITTER]("+lsAssets.urlTwitter+")";
                desc+="\n[Telegram]("+lsAssets.urlTelegram+")";
                desc+="\nGMail: "+lsAssets.emailGMail+"";
                embed.addField("Bot Owner",desc,false);
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void showLegality(){
            String fName = "[showLegality]";
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);embed.setTitle("Legality & Privacy");
                String desc="";
                desc="**\u0095 The following data may be stored by the bot:**\n  ~ userid,username, avatar url, member roles and permission on guild\n  ~ command usages\n  ~ message content\n  ~ any data (text&file) input to the bot";
                desc+="\n\u0095 The bot will not collect any of your personal information. The only data may be collected that is related to the server and allowed by the Discord Public API.\n" +
                        "\u0095 No collected data is shared publicly or with third-parties, except when required to by law.\n" +
                        "\u0095 You are free to ask deleting your entries in database.";
                embed.addField("Privacy",desc,false);
                desc="\u0095 The bot is not intended for and should not be used by anyone under the age of 18.\n" +
                        "\u0095 While the bot has SFW functions, they should not be used by anyone under the age of 13.\n" +
                        "\u0095 The bot NSFW functions should only be used in NSFW tagged channels or server. Additionally they are not intended for and should not be used by anyone under the age of 18 or used in channels that exposes minors too it.";
                embed.addField("Legality",desc,false);
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void showSystem(){
            String fName = "[showSystem]";
            try{

                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);embed.setTitle("System");
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
                //embed.addField("OS","name:"+osName+"\nversion:"+osVersion+"\narch: "+osArch,true);
                //embed.addField("Environment","port:"+port+"\ntoolOptions:"+toolOptions+"\njavaOpts: "+javaOpts,true);
                embed.addField("Guilds", String.valueOf(gGlobal.getGuildList().size()),true);
                embed.addBlankField(false);
                embed.addField("Uptime",displayUptime,true);
                embed.addField("Memory Used","heap: " + memoryUsedHeap+ "mb" + "\nnon-heap: " + memoryUsedNonHeap + "mb",true);
                embed.addField("Memory Max","heap: " + memoryMaxHeap+ "mb" + "\nnon-heap: " + memoryMaxNonHeap + "mb",true);
                embed.addField("Thread","count: " + threadCount+ "\npeak: " + threadPeak,true);
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void showAcknowledgements(){
            String fName = "[showAcknowledgements]";
            try{
                //Acknowledgements
                EmbedBuilder embed=new EmbedBuilder();
                String JDAVersion= JDAInfo.VERSION;
                String JDAUtilitiesVersion= JDAUtilitiesInfo.VERSION;
                logger.info(fName+"JDAVersion="+JDAVersion);logger.info(fName+"JDAUtilitiesVersion="+JDAUtilitiesVersion);
                embed.addField(" ","This are the libraries and tools used to create the bot",false);
                embed.addField("JetBrains IntelliJ IDEA ","2020.2.4",false);
                embed.addField("jdk","13",false);
                embed.addField("dv8tion JDA ",JDAVersion,false);
                embed.addField("jagrosh JDA Utilities",JDAUtilitiesVersion,false);
                embed.addField("MY SQL Version","8.0.19",false);
                embed.addField("club minnced discord-webhooks","0.5.4-rc",false);
                embed.addField("apache imaging","1.0-alpha1",false);
                embed.addField("apache logging log4j","2.13.2",false);
                embed.addField("konghq unirest-java","3.11.11",false);
                embed.setColor(llColorPurple2);embed.setTitle("Programmed with");
                //embed.addBlankField(false);
                //embed.addField("Last Modified", getStrLastModified(),true);
                llSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
    }

}
