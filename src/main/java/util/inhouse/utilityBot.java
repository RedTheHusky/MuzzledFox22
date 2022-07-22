package util.inhouse;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.BannedGuilds;
import models.lc.json.lcText2Json;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;
import static models.lsGlobalHelper.lsGeneralGuildNotificationPath;

public class utilityBot extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llCommonKeys {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="utilitybot";
    public utilityBot(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Utility-Bot";
        this.help = "guild stuffs";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category= llCommandCategory_UtilityInHouse;
    }
    public utilityBot(lcGlobalHelper g, ReadyEvent event){
        String fName=".constructor";
        logger.info(fName); gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    public utilityBot(lcGlobalHelper g, ReconnectedEvent event){
        String fName=".constructor";
        logger.info(fName); gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel; private Message gMessage;String gTitle="Bot Utility";
        String[] gItems=null;
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
        ReadyEvent  gReadyEvent;ReconnectedEvent gReconnectedEvent;
        public runLocal( ReadyEvent event) {
            String fName="runLocal";
            logger.info(".run build");
            gReadyEvent=event;
        }
        public runLocal( ReconnectedEvent event) {
            String fName="runLocal";
            logger.info(".run build");
            gReconnectedEvent=event;
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + fName);
            boolean isInvalidCommand = true;
            try {
                if(gReadyEvent!=null){
                    logger.info(cName + fName + "onReady.id="+gReadyEvent.getJDA().getShardInfo().getShardId());
                    List<Guild>guilds=gReadyEvent.getJDA().getGuilds();
                    for(int i=0;i<guilds.size();i++){
                        try {
                            Guild guild=guilds.get(i);
                            logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").processing");
                            gGlobal.initGuild(guild);
                            //logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").finished");
                            Thread.sleep(2000);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    logger.info(cName + fName + "completed");
                    /*for(int i=0;i<guilds.size();i++){
                        try {
                            Guild guild=guilds.get(i);
                            if(gGlobal.guildsSettingsJson.has(guild.getId())){
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").has such entry>ignore");
                            }else{
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").processing 2");
                                gGlobal.initGuild(guild);
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").finished 2");
                                Thread.sleep(10000);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }*/
                    //logger.info(cName + fName + "completed step 2");
                    List<BannedGuilds.GuildEntity>bannedGuilds=gGlobal.banguilds.leaveGuilds();
                    logger.info(cName + fName + "leave banned guilds: "+bannedGuilds.size());
                }else
                if(gReconnectedEvent!=null){
                    logger.info(cName + fName + "onReconnected.id="+gReconnectedEvent.getJDA().getShardInfo().getShardId());
                    List<Guild>guilds=gReconnectedEvent.getJDA().getGuilds();
                    for(int i=0;i<guilds.size();i++){
                        try {
                            Guild guild=guilds.get(i);
                            logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").processing");
                            gGlobal.initGuild(guild);
                            logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").finished");
                            //Thread.sleep(2000);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    logger.info(cName + fName + "completed step 1");
					for(int i=0;i<guilds.size();i++){
                        try {
                            Guild guild=guilds.get(i);
                            if(gGlobal.guildsSettingsJson.has(guild.getId())){
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").has such entry>ignore");
                            }else{
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").processing 2");
                                gGlobal.initGuild(guild);
                                logger.info(cName + fName + "guild["+i+"]:"+guild.getName()+"("+guild.getId()+").finished 2");
                                Thread.sleep(10000);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
					 logger.info(cName + fName + "completed step 2");
                }else {
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(cName + fName + ".Args=0");
                        help("main");
                        isInvalidCommand = false;
                    } else {
                        logger.info(cName + fName + ".Args");
                        if (!lsMemberIsBotOwner(gMember)) {
                            llSendQuickEmbedMessage(gUser, "Bot utility", "Denied!", llColorRed);
                            llMessageDelete(gCommandEvent);
                            return;
                        }
                        gItems = gCommandEvent.getArgs().split("\\s+");
                        if (gItems.length >= 2 && gItems[0].equalsIgnoreCase("help")) {
                            help(gItems[1]);
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("help")) {
                            help("main");
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("reloadsettings")) {
                            reloadCurrentGuildSettings();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("reloadallsettings")) {
                            reloadAllGuildSettings();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("countsettings")) {
                            count4CurrentGuildSettings();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("countallsettings")) {
                            count4AllGuildSettings();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sqlclose") ) {
                            sqlCloseConnection();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sqlopen") ) {
                            sqlOpenConnection();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sqlis") ) {
                            sqlIsConnected();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sqlcheck") ) {
                            sqlCheckConnection();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sqlrereadproperties") ) {
                            sqlReReadProperties();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("sql")&&gItems.length >= 2) {
                            if (gItems[1].equalsIgnoreCase("close") ) {
                                sqlCloseConnection();
                                isInvalidCommand = false;
                            }
                            else if (gItems[1].equalsIgnoreCase("open") ) {
                                sqlOpenConnection();
                                isInvalidCommand = false;
                            }
                            else if (gItems[1].equalsIgnoreCase("is") ) {
                                sqlIsConnected();
                                isInvalidCommand = false;
                            }
                            else if (gItems[1].equalsIgnoreCase("check") ) {
                                sqlCheckConnection();
                                isInvalidCommand = false;
                            }
                            else if (gItems[1].equalsIgnoreCase("rereadproperties")||gItems[1].equalsIgnoreCase("re-readproperties")||gItems[1].equalsIgnoreCase("re-read_properties")) {
                                sqlReReadProperties();
                                isInvalidCommand = false;
                            }
                            else if (gItems.length >= 3&&gItems[2].equalsIgnoreCase("reread")||gItems[2].equalsIgnoreCase("re-read")||gItems[2].equalsIgnoreCase("properties")) {
                                if (gItems[1].equalsIgnoreCase("reread")||gItems[1].equalsIgnoreCase("re-read")||gItems[1].equalsIgnoreCase("properties")) {
                                    sqlReReadProperties();
                                    isInvalidCommand = false;
                                }
                            }
                        }
                        else if (gItems[0].equalsIgnoreCase("countuserprofilecacheall")) {
                            countUserProfilesCache4All();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("countuserprofilecachenoguild")) {
                            countUserProfilesCache4NoGuild();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("countuserprofilecache")) {
                            countUserProfilesCache4Current();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("clearuserprofilecache")) {
                            clearUserProfilesCache4CurrentGuild();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("clearuserprofilecacheall")) {
                            clearUserProfilesCache4AllGuilds();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("serverread")) {
                            getGuildRegistry();
                            isInvalidCommand = false;
                        }
                        else if (gItems.length >= 3 && gItems[0].equalsIgnoreCase("serverwrite_boolean")) {
                            setGuildRegistry_BooleanValue(gItems[1], gItems[2]);
                            isInvalidCommand = false;
                        }
                        else if (gItems.length >= 3 && gItems[0].equalsIgnoreCase("serverwrite_int")) {
                            setGuildRegistry_IntValue(gItems[1], gItems[2]);
                            isInvalidCommand = false;
                        }
                        else if (gItems.length >= 3 && gItems[0].equalsIgnoreCase("serverwrite_world")) {
                            setGuildRegistry_WorldValue(gItems[1], gItems[2]);
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("jsonsettings")) {
                            sendGuildsSettingsJSON();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("jsonprofile")) {
                            sendGuildsUserProfileJSON();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("jsoncache")) {
                            sendGlobalJSONObject();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("appendjson2guild")) {
                            //appendGuildsSettingsJSON();isInvalidCommand=false;
                        }
                        else if (gItems[0].equalsIgnoreCase("load_emojis")) {
                            reloadEmojis();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("clear_messagecomponents")) {
                            resetMessageComponentJsonCache();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("clear_json")) {
                            resetOthersJsonCache();
                            isInvalidCommand = false;
                        }
                        else if (gItems[0].equalsIgnoreCase("log")) {
                            if (gItems.length >= 2) {
                                if (gItems[1].equalsIgnoreCase("get")) {
                                    getLogPreset();
                                    isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("default")||gItems[1].equalsIgnoreCase("0")) {
                                    setLogPreset(llLogDefault);  isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("normal")||gItems[1].equalsIgnoreCase("1")) {
                                    setLogPreset(llLogNormal);  isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("error")||gItems[1].equalsIgnoreCase("2")) {
                                    setLogPreset(llLogError);  isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("issues")||gItems[1].equalsIgnoreCase("warning")||gItems[1].equalsIgnoreCase("3")) {
                                    setLogPreset(llLogIssues);  isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("info")||gItems[1].equalsIgnoreCase("all")||gItems[1].equalsIgnoreCase("4")) {
                                    setLogPreset(llLogAll);  isInvalidCommand = false;
                                }else
                                if (gItems[1].equalsIgnoreCase("debug")||gItems[1].equalsIgnoreCase("5")) {
                                    setLogPreset(llLogDebug);  isInvalidCommand = false;
                                }
                            }else {
                                getLogPreset();
                                isInvalidCommand = false;
                            }
                        }
                        else if (gItems[0].equalsIgnoreCase("waiter")) {
                            if(gItems.length>=2){
                                if (gItems[1].equalsIgnoreCase("status")) {
                                    getWaiterStatus();
                                    isInvalidCommand = false;
                                }
                                if (gItems[1].equalsIgnoreCase("restart")) {
                                    restartWaiters();
                                    isInvalidCommand = false;
                                }
                            }
                        }
                        else if (gItems[0].equalsIgnoreCase("jdas")||gItems[0].equalsIgnoreCase("shards")) {
                            if(gItems.length>=2){
                                if (gItems[1].equalsIgnoreCase("get")) {
                                    getShardsJson();
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("restart")) {
                                    restarShards();
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("stop")) {
                                    stopShards();
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("status")) {
                                    getShardsStatus();
                                    isInvalidCommand = false;
                                }
                            }

                        }
                        else if (gItems[0].equalsIgnoreCase("jda")||gItems[0].equalsIgnoreCase("shard")) {
                            if(gItems.length>=3){
                                if (gItems[1].equalsIgnoreCase("get")) {
                                    getShardJson(gItems[2]);
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("restart")) {
                                    restartShard(gItems[2]);
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("stop")) {
                                    stopShard(gItems[2]);
                                    isInvalidCommand = false;
                                }
                                else if (gItems[1].equalsIgnoreCase("status")) {
                                    getShardStatus(gItems[2]);
                                    isInvalidCommand = false;
                                }
                            }
                        }
                        else if (gItems[0].equalsIgnoreCase("eval")) {
                          eval(gItems);
                        }
                        else if (gItems[0].equalsIgnoreCase("banedguilds")) {
                            doLeaveBannedGuilds();
                        }
                        else if (gItems[0].equalsIgnoreCase("reload_notification")) {
                            doReloadNotification();
                        }

                    }
                    logger.info(cName + fName + ".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if (isInvalidCommand) {
                        llSendQuickEmbedMessage(gTextChannel, gTitle, "Provided an incorrect command!", llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }

        }
        String commandKeyInfo="info", commandKeyUser="user", commandKeyServer="server", commandKeyPrune="prune",commandKeyDownloads="downloads";
        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = " ";
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColorBlue1);
            embed.addField("Settings","`jsonsettings/reloadsettings/reloadallsettings/countsettings/countallsettings`",false);
            embed.addField("Userprofile","`jsonprofile/countuserprofilecacheall/countuserprofilecachenoguild/clearuserprofilecache/clearuserprofilecacheall`",false);
            embed.addField("SQL","`sqlclose/sqlopen/sqlis/sqlcheck`",false);
            embed.addField("Registry",",`serverread`, `serverwrite_boolean/serverwrite_int/serverwrite_world <name> <valeu>`",false);
            embed.addField("Log","\n`" + quickSummonWithSpace + "log [get, debug, info, default, normal, issues, error]`",false);
            embed.addField("Other","`load_emojis/clear_messagecomponents/jsoncache/waiter status/banedguilds/reload_notification`",false);
            embed.addField("Shard","`shards get`,`jda get <index>`, `shard restart <index>`",false);
            llSendMessage(gUser,embed);
        }
        private void sqlCloseConnection() {
            String fName = "[sqlCloseConnection]";
            logger.info(cName + fName);
            boolean result = gGlobal.sql.closeConnection();
            llSendQuickEmbedMessage(gUser,gTitle, "SQL Close Connection:" + result, llColorBlue1);
        }
        private void sqlOpenConnection() {
            String fName = "[sqlOpenConnection]";
            logger.info(cName + fName);
            boolean result = gGlobal.sql.openConnection();
            llSendQuickEmbedMessage(gUser,gTitle, "SQL Open Connection:" + result, llColorBlue1);
        }
        private void sqlIsConnected() {
            String fName = "[sqlIsConnected]";
            logger.info(cName + fName);
            Boolean result = gGlobal.sql.isConnected();
            llSendQuickEmbedMessage(gUser,gTitle, "SQL Is Connected:" + result, llColorBlue1);
        }
        private void sqlCheckConnection() {
            String fName = "[ sqlCheckConnection]";
            logger.info(cName + fName);
            Boolean result = gGlobal.sql.checkConnection();
            llSendQuickEmbedMessage(gUser,gTitle, "SQL Check Connection:" + result, llColorBlue1);
        }
        private void sqlReReadProperties() {
            String fName = "[sqlReReadProperties]";
            logger.info(cName + fName);
            Boolean result = gGlobal.sql.getProperties(gGlobal);
            llSendQuickEmbedMessage(gUser,gTitle, "SQL properties re-read:" + result, llColorBlue1);
        }
        private void reloadCurrentGuildSettings() {
            String fName = "[reloadCurrentGuildSettings]";
            logger.info(cName + fName);
            gGlobal.guildsSettingsGet4Db(gGuild);
            llSendQuickEmbedMessage(gUser,gTitle, "Settings reload for guild " + gGuild.getName() + ".", llColorBlue1);
        }
        private void count4AllGuildSettings() {
            String fName = "[count4AllGuildSettings]";
            logger.info(cName + fName);
            int count=0;
            Iterator<String> iGuilds= gGlobal.guildsSettingsJson.keys();
            while (iGuilds.hasNext())
            {
                String key=iGuilds.next();
                if(gGlobal.guildsSettingsJson.has(key)&&!gGlobal.guildsSettingsJson.isNull(key)){
                    Iterator<String> iEntries= gGlobal.guildsSettingsJson.getJSONObject(key).keys();
                    while (iEntries.hasNext()){
                        String entrie=iEntries.next();
                        if(gGlobal.guildsSettingsJson.getJSONObject(key).has(entrie)&&!gGlobal.guildsSettingsJson.getJSONObject(key).isNull(entrie)){
                            count++;
                        }
                    }
                }
            }
            llSendQuickEmbedMessage(gUser,gTitle, "Guilds setting entries count:"+count, llColorBlue1);
        }
        private void count4CurrentGuildSettings() {
            String fName = "[count4CurrentGuildSettings]";
            logger.info(cName + fName);
            int count=0;
            if(gGlobal.guildsSettingsJson.has(gGuild.getId())&&!gGlobal.guildsSettingsJson.isNull(gGuild.getId())){
                Iterator<String> iEntries= gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId()).keys();
                while (iEntries.hasNext()){
                    String entrie=iEntries.next();
                    if(gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId()).has(entrie)&&!gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId()).isNull(entrie)){
                        count++;
                    }
                }
            }
            llSendQuickEmbedMessage(gUser,gTitle, "Current guild setting entries count:"+count, llColorBlue1);
        }
        private void reloadAllGuildSettings() {
            String fName = "[reloadAllGuildSettings]";
            logger.info(cName + fName);
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle, "Reloading settings for all guilds.", llColorBlue1);
            Map<String, Guild> guilds = gGlobal.getGuildMap4JDA();
            for (Map.Entry<String, Guild> entry : guilds.entrySet()) {
                try {
                    String key = entry.getKey();
                    Guild value = entry.getValue();
                    gGlobal.guildsSettingsGet4Db(value);
                    Thread.sleep(5000);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            lsMessageHelper.lsMessageDelete(message);
            llSendQuickEmbedMessage(gUser,gTitle, "Settings reload for all guilds.", llColorBlue1);

        }
        private void clearUserProfilesCache4AllGuilds() {
            String fName = "[clearUserProfilesCache4AllGuilds]";
            logger.info(cName + fName);
            gGlobal.userProfileJson = new JSONObject();
            llSendQuickEmbedMessage(gUser,gTitle, "Cleared user profile cache for all guilds", llColorBlue1);
        }
        private void clearUserProfilesCache4CurrentGuild() {
            String fName = "[clearUserProfilesCache4CurrentGuild]";
            logger.info(cName + fName);
            if(!gGlobal.userProfileJson.has(gGuild.getId())){
                logger.error(cName + fName + ".has no such guild id"); llSendQuickEmbedMessage(gUser,gTitle, "Guild has no user settings cached", llColorBlue1);
                return;
            }
            gGlobal.userProfileJson.put(gGuild.getId(),new JSONObject());
            llSendQuickEmbedMessage(gUser,gTitle, "\"Cleared user profile cache for this guild", llColorBlue1);
        }
        private void countUserProfilesCache4All() {
            String fName = "[countUserProfilesCache4All]";
            logger.info(cName + fName);
            int count=0;
            Iterator<String> iGuilds= gGlobal.userProfileJson.keys();
            while (iGuilds.hasNext())
            {
                try {
                    String guild=iGuilds.next();
                    if(gGlobal.guildsSettingsJson.has(guild)&&!gGlobal.guildsSettingsJson.isNull(guild)){
                        Iterator<String> iFields= gGlobal.guildsSettingsJson.getJSONObject(guild).keys();
                        while (iFields.hasNext()){
                            try {
                                String field=iFields.next();
                                if(gGlobal.guildsSettingsJson.getJSONObject(guild).has(field)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).isNull(field)){
                                    Iterator<String> iUser= gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).keys();
                                    while (iUser.hasNext()){
                                        try {
                                            String user=iUser.next();
                                            if(gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).has(user)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).isNull(user)){
                                                count++;
                                            }
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
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            llSendQuickEmbedMessage(gUser,gTitle, "All guilds user profile cache entries count:"+count, llColorBlue1);


        }
        private void countUserProfilesCache4Current() {
            String fName = "[countUserProfilesCache4Current]";
            logger.info(cName + fName);
            int count=0;String guild=gGuild.getId();
            if(gGlobal.guildsSettingsJson.has(guild)&&!gGlobal.guildsSettingsJson.isNull(guild)){
                Iterator<String> iFields= gGlobal.guildsSettingsJson.getJSONObject(guild).keys();
                while (iFields.hasNext()){
                    try {
                        String field=iFields.next();
                        if(gGlobal.guildsSettingsJson.getJSONObject(guild).has(field)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).isNull(field)){
                            Iterator<String> iUser= gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).keys();
                            while (iUser.hasNext()){
                                try {
                                    String user=iUser.next();
                                    if(gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).has(user)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).isNull(user)){
                                        count++;
                                    }
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
            }
            llSendQuickEmbedMessage(gUser,gTitle, "Current guild user profile cache entries count:"+count, llColorBlue1);
        }
        private void countUserProfilesCache4NoGuild() {
            String fName = "[countUserProfilesCacheNoGuild]";
            logger.info(cName + fName);
            int count=0;String guild="noguild";
            if(gGlobal.guildsSettingsJson.has(guild)&&!gGlobal.guildsSettingsJson.isNull(guild)){
                Iterator<String> iFields= gGlobal.guildsSettingsJson.getJSONObject(guild).keys();
                while (iFields.hasNext()){
                    try {
                        String field=iFields.next();
                        if(gGlobal.guildsSettingsJson.getJSONObject(guild).has(field)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).isNull(field)){
                            Iterator<String> iUser= gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).keys();
                            while (iUser.hasNext()){
                                try {
                                    String user=iUser.next();
                                    if(gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).has(user)&&!gGlobal.guildsSettingsJson.getJSONObject(guild).getJSONObject(field).isNull(user)){
                                        count++;
                                    }
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
            }
            llSendQuickEmbedMessage(gUser,gTitle, "NoGuild user profile cache entries count:"+count, llColorBlue1);
        }
        private long twoWeeksToSecond=1209600;
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        String sTitleRegistry="Server Registry";
        private void getGuildRegistry(){
            String fName = "[getGuildRegistry]";
            logger.info(cName + fName);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColorRed);logger.warn(cName + fName+"not registered");return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sTitleRegistry);embed.setColor(llColorOrange_Pumpkin);
            String desc="Server keys:";
            Iterator<String>keys=entrie.jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    String data="";
                    try {
                        data=entrie.jsonObject.get(key).toString();
                    } catch (Exception e) {
                        logger.info(cName + fName+"exception="+e);
                        data="!exception!";
                    }
                    String tmp=desc+"\n"+data;
                    if(tmp.length()>2000){
                        embed.setDescription(desc);
                        llSendMessage(gTextChannel,embed);
                        desc="Server keys:"+"\n"+data;
                    }else{
                        desc=tmp;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            llSendMessage(gTextChannel,embed);
        }
        private void setGuildRegistry_BooleanValue(String name, String value){
            String fName = "[setGuildRegistry_BooleanValue]";
            logger.info(cName + fName+"name="+name);
            logger.info(cName + fName+"value="+value);
            boolean bvalue=false;
            if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1")){bvalue=true;}
            else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("0")){bvalue=false;}
            else{  llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Invalid value", llColorRed); logger.warn(cName + fName+"invalid value");return;}
            logger.info(cName + fName+"bvalue="+bvalue);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            entrie.jsonObject.put(name,bvalue);
            if(!entrie.saveProfile()){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+bvalue+" to "+name, llColorGreen1);
        }
        private void setGuildRegistry_IntValue(String name, String value){
            String fName = "[setGuildRegistry_IntValue]";
            logger.info(cName + fName+"name="+name);
            logger.info(cName + fName+"value="+value);
            int ivalue=0;
            try {
                ivalue= Integer.parseInt(value);
            } catch (Exception e) {
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Invalid value", llColorRed); logger.warn(cName + fName+"invalid value");return;
            }
            logger.info(cName + fName+"ivalue="+ivalue);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            entrie.jsonObject.put(name,ivalue);
            if(!entrie.saveProfile()){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+ivalue+" to "+name, llColorGreen1);
        }
        private void setGuildRegistry_WorldValue(String name, String value){
            String fName = "[setGuildRegistry_WorldValue]";
            logger.info(cName + fName);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            logger.info(cName + fName+"name="+name);logger.info(cName + fName+"value="+value);
            entrie.jsonObject.put(name,value);
            if(!entrie.saveProfile()){
                llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            llSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+value+" to "+name, llColorGreen1);
        }
        private void sendGuildsSettingsJSON(){
            String fName = "[sendGuildsSettingsJSON]";
            logger.info(cName + fName);
            JSONObject jsonObject= gGlobal.guildsSettingsJson;
            if(jsonObject!=null){
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="GuildsJSON", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;
            }else{
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void sendGuildsUserProfileJSON(){
            String fName = "[sendGuildsUserProfileJSON]";
            logger.info(cName + fName);
            JSONObject jsonObject= gGlobal.userProfileJson;
            if(jsonObject!=null){
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="GuildsJSON", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;
            }else{
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void sendGlobalJSONObject(){
            String fName = "[sendGlobalJSONObject]";
            logger.info(cName + fName);
            JSONObject jsonObject= gGlobal.jsonObject;
            if(jsonObject!=null){
                InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="GlobalJDONObject", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;

            }else{
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorRed);
            }
        }
        private void appendGuildsSettingsJSON(){
            String fName = "[appendGuildsSettingsJSON]";
            logger.info(cName + fName);
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image:", llColorBlue1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            logger.info(cName + fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment=attachments.get(0);
                                String fileExtension=attachment.getFileExtension();
                                logger.info(cName + fName + ".fileExtension=" + fileExtension);
                                if(!fileExtension.equalsIgnoreCase("json")){
                                    llSendQuickEmbedMessage(gUser, gTitle, "Invalid", llColorRed);return;
                                }
                                appendGuildsSettingsJSON2(attachment.getUrl());
                            }else{
                                llSendQuickEmbedMessage(gUser, gTitle, "No attachment", llColorRed);
                            }
                        }catch (Exception e2){
                            logger.error(cName + fName + ".exception=" + e2);
                            logger.error(cName + fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }

                        llMessageDelete(message);
                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        private void appendGuildsSettingsJSON2(String url){
            String fName = "[appendGuildsSettingsJSON]";
            logger.info(cName + fName);
            try {
                lcText2Json text2Json=new lcText2Json();
                /*ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream is = classloader.getResourceAsStream("json/emojis.json");*/
                InputStream is=lsStreamHelper.llGetInputStream4WebFile(url);
                if(!text2Json.isInputStream2Json(is)) {
                    logger.warn(fName + ".failed to load");
                }
                gGlobal.guildsSettingsJson.append(gGuild.toString(),text2Json.jsonObject);
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void setLogPreset(String str){
            String fName = "[setLogPreset]";
            logger.info(fName + "str=" +str);
            File f = new File(str);
            if(f.exists()){
                if(f.isDirectory()){
                    logger.warn(fName + "is a directory!");
                    llSendMessage(gTextChannel,"Failed to set log prefrence. File "+str+" does not exists!");
                }else{
                    logger.info(fName + "is a file");
                    PropertyConfigurator.configure(str);
                    gGlobal.logPreference=str;
                    llSendMessage(gTextChannel,"Set log prefrence to "+str+".");
                }
            }else{
                logger.warn(fName + "does not exists!");
                llSendMessage(gTextChannel,"Failed to set log prefrence. File "+str+" does not exists!");
            }
        }
        private void getLogPreset(){
            String fName = "[getLogPreset]";
            String preference=gGlobal.logPreference;
            logger.info(fName + "preference=" +preference);
            llSendMessage(gTextChannel,"Log preference:"+preference);
        }
        private void reloadEmojis(){
            gGlobal.emojis.build();
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Reloading emojis!", llColors.llColorGreen1);
        }
        private void resetMessageComponentJsonCache(){
            gGlobal.globalSettingsJson.put(llCommonKeys.keyMessageComponents,new JSONObject());
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Cleared message component cache!", llColors.llColorGreen1);
        }
        private void resetOthersJsonCache(){
            gGlobal.globalSettingsJson.put(llCommonKeys.keyEtc,new JSONObject());
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle,"Cleared others json cache!", llColors.llColorGreen1);
        }
        private void restartWaiters() {
            String fName = "[restartWaiters]";
            logger.info(cName + fName);
            //gGlobal.endEventWaiters();
            //gGlobal.endGeneralEventWaiter();
            //gGlobal.setGeneralEventWaiter();
            //gGlobal.addEventWaiters();
            llSendQuickEmbedMessage(gUser,gTitle, "\"Reload event waiters", llColorBlue1);
        }
        private void getWaiterStatus() {
            String fName = "[getWaiterStatus]";
            logger.info(cName + fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            boolean isShutdown=gGlobal.waiterThreadpool.isShutdown();
            boolean isTerminated=gGlobal.waiterThreadpool.isTerminated();
            logger.warn(cName + fName + ".waiterThreadpool.isShutdown="+isShutdown);
            logger.warn(cName + fName + ".waiterThreadpool.isTerminated="+isTerminated);
            embedBuilder.setDescription("**waiterThreadpool Status:**\nisShutdown="+isShutdown+"\nisTerminated="+isTerminated);
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
        private void getShardsStatus() {
            String fName = "[getShardsStatus]";
            logger.info(cName + fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorBlue1);
            List<JDA>jdas=gGlobal.getJDAList();
            for(JDA jda:jdas){
                try {
                    int id=jda.getShardInfo().getShardId();
                    List<Guild>guilds=jda.getGuilds();
                    int guildsCount=guilds.size();
                    String status="";
                    switch (jda.getStatus()){
                        case INITIALIZING:status="INITIALIZING";break;
                        case INITIALIZED:status="INITIALIZED";break;
                        case LOGGING_IN:status="LOGGING_IN";break;
                        case CONNECTING_TO_WEBSOCKET:status="CONNECTING_TO_WEBSOCKET";break;
                        case IDENTIFYING_SESSION:status="IDENTIFYING_SESSION";break;
                        case AWAITING_LOGIN_CONFIRMATION:status="AWAITING_LOGIN_CONFIRMATION";break;
                        case LOADING_SUBSYSTEMS:status="LOADING_SUBSYSTEMS";break;
                        case CONNECTED:status="CONNECTED";break;
                        case DISCONNECTED:status="DISCONNECTED";break;
                        case RECONNECT_QUEUED:status="RECONNECT_QUEUED";break;
                        case WAITING_TO_RECONNECT:status="WAITING_TO_RECONNECT";break;
                        case ATTEMPTING_TO_RECONNECT:status="ATTEMPTING_TO_RECONNECT";break;
                        case SHUTTING_DOWN:status="SHUTTING_DOWN";break;
                        case SHUTDOWN:status="SHUTDOWN";break;
                        case FAILED_TO_LOGIN:status="FAILED_TO_LOGIN";break;
                        default:status="unknown";break;
                    }
                    embedBuilder.addField(String.valueOf(id),"Status:"+status+"\nGuilds:"+guildsCount,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                }
            }
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
        private void getShardStatus(String index) {
            String fName = "[getShardStatus]";
            logger.info(cName + fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorBlue1);
             try {
                 JDA jda=gGlobal.shardManager.getShardById(Integer.parseInt(index));
                 int id=jda.getShardInfo().getShardId();
                 List<Guild>guilds=jda.getGuilds();
                 int guildsCount=guilds.size();
                 String status="";
                 switch (jda.getStatus()){
                     case INITIALIZING:status="INITIALIZING";break;
                     case INITIALIZED:status="INITIALIZED";break;
                     case LOGGING_IN:status="LOGGING_IN";break;
                     case CONNECTING_TO_WEBSOCKET:status="CONNECTING_TO_WEBSOCKET";break;
                     case IDENTIFYING_SESSION:status="IDENTIFYING_SESSION";break;
                     case AWAITING_LOGIN_CONFIRMATION:status="AWAITING_LOGIN_CONFIRMATION";break;
                     case LOADING_SUBSYSTEMS:status="LOADING_SUBSYSTEMS";break;
                     case CONNECTED:status="CONNECTED";break;
                     case DISCONNECTED:status="DISCONNECTED";break;
                     case RECONNECT_QUEUED:status="RECONNECT_QUEUED";break;
                     case WAITING_TO_RECONNECT:status="WAITING_TO_RECONNECT";break;
                     case ATTEMPTING_TO_RECONNECT:status="ATTEMPTING_TO_RECONNECT";break;
                     case SHUTTING_DOWN:status="SHUTTING_DOWN";break;
                     case SHUTDOWN:status="SHUTDOWN";break;
                     case FAILED_TO_LOGIN:status="FAILED_TO_LOGIN";break;
                     default:status="unknown";break;
                 }
                 embedBuilder.addField(String.valueOf(id),"Status:"+status+"\nGuilds:"+guildsCount,false);
             }catch (Exception e){
                 logger.error(fName + ".exception=" + e);
                 logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                 lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                 return;
             }

            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
        private void getShardsJson() {
            String fName = "[getShardsJson]";
            logger.info(cName + fName);
            JSONArray jsonJdas=new JSONArray();
            List<JDA>jdas=gGlobal.getJDAList();
            for(JDA jda:jdas){
                try {
                    JSONObject jsonJda=new JSONObject();
                    jsonJda.put("id",jda.getShardInfo().getShardId());
                    switch (jda.getStatus()){
                        case INITIALIZING:jsonJda.put("status","INITIALIZING");break;
                        case INITIALIZED:jsonJda.put("status","INITIALIZED");break;
                        case LOGGING_IN:jsonJda.put("status","LOGGING_IN");break;
                        case CONNECTING_TO_WEBSOCKET:jsonJda.put("status","CONNECTING_TO_WEBSOCKET");break;
                        case IDENTIFYING_SESSION:jsonJda.put("status","IDENTIFYING_SESSION");break;
                        case AWAITING_LOGIN_CONFIRMATION:jsonJda.put("status","AWAITING_LOGIN_CONFIRMATION");break;
                        case LOADING_SUBSYSTEMS:jsonJda.put("status","LOADING_SUBSYSTEMS");break;
                        case CONNECTED:jsonJda.put("status","CONNECTED");break;
                        case DISCONNECTED:jsonJda.put("status","DISCONNECTED");break;
                        case RECONNECT_QUEUED:jsonJda.put("status","RECONNECT_QUEUED");break;
                        case WAITING_TO_RECONNECT:jsonJda.put("status","WAITING_TO_RECONNECT");break;
                        case ATTEMPTING_TO_RECONNECT:jsonJda.put("status","ATTEMPTING_TO_RECONNECT");break;
                        case SHUTTING_DOWN:jsonJda.put("status","SHUTTING_DOWN");break;
                        case SHUTDOWN:jsonJda.put("status","SHUTDOWN");break;
                        case FAILED_TO_LOGIN:jsonJda.put("status","FAILED_TO_LOGIN");break;
                        default:jsonJda.put("status","unknown");break;
                    }
                    List<Guild>guilds=jda.getGuilds();
                    JSONArray jsonGuilds=new JSONArray();
                    for(Guild guild:guilds){
                        try {
                           JSONObject jsonGuild=new JSONObject();
                           jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                           jsonGuild.put(llCommonKeys.keyName,guild.getName());
                           jsonGuild.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                           jsonGuilds.put( jsonGuild);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            jsonGuilds.put( e.toString());
                        }
                    }
                    jsonJda.put("guilds_count",jsonGuilds.length());
                    jsonJda.put("guilds",jsonGuilds);
                    jsonJdas.put( jsonJda);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    jsonJdas.put( e.toString());
                }
            }
            InputStream targetStream = new ByteArrayInputStream(jsonJdas.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="JDAsJSON", fileExtension=".txt";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
        }
        private void getShardJson(String index) {
            String fName = "[getShardsJson]";
            logger.info(cName + fName);
            JDA jda=null;
            try {
                int i=Integer.parseInt(index);
                if(i<0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Invalid "+i+".",llColorRed);
                    return;
                }
                jda=gGlobal.shardManager.getShardById(i);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return;
            }
            JSONObject jsonJda=new JSONObject();
            try {
                jsonJda.put("id",jda.getShardInfo().getShardId());
                switch (jda.getStatus()){
                    case INITIALIZING:jsonJda.put("status","INITIALIZING");break;
                    case INITIALIZED:jsonJda.put("status","INITIALIZED");break;
                    case LOGGING_IN:jsonJda.put("status","LOGGING_IN");break;
                    case CONNECTING_TO_WEBSOCKET:jsonJda.put("status","CONNECTING_TO_WEBSOCKET");break;
                    case IDENTIFYING_SESSION:jsonJda.put("status","IDENTIFYING_SESSION");break;
                    case AWAITING_LOGIN_CONFIRMATION:jsonJda.put("status","AWAITING_LOGIN_CONFIRMATION");break;
                    case LOADING_SUBSYSTEMS:jsonJda.put("status","LOADING_SUBSYSTEMS");break;
                    case CONNECTED:jsonJda.put("status","CONNECTED");break;
                    case DISCONNECTED:jsonJda.put("status","DISCONNECTED");break;
                    case RECONNECT_QUEUED:jsonJda.put("status","RECONNECT_QUEUED");break;
                    case WAITING_TO_RECONNECT:jsonJda.put("status","WAITING_TO_RECONNECT");break;
                    case ATTEMPTING_TO_RECONNECT:jsonJda.put("status","ATTEMPTING_TO_RECONNECT");break;
                    case SHUTTING_DOWN:jsonJda.put("status","SHUTTING_DOWN");break;
                    case SHUTDOWN:jsonJda.put("status","SHUTDOWN");break;
                    case FAILED_TO_LOGIN:jsonJda.put("status","FAILED_TO_LOGIN");break;
                    default:jsonJda.put("status","unknown");break;
                }
                List<Guild>guilds=jda.getGuilds();
                JSONArray jsonGuilds=new JSONArray();
                for(Guild guild:guilds){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                        jsonGuild.put(llCommonKeys.keyName,guild.getName());
                        jsonGuild.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                        jsonGuilds.put( jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        jsonGuilds.put( e.toString());
                    }
                }
                jsonJda.put("guilds_count",jsonGuilds.length());
                jsonJda.put("guilds",jsonGuilds);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                jsonJda.put("error",e.toString());
            }

            InputStream targetStream = new ByteArrayInputStream(jsonJda.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="JDAJSON", fileExtension=".txt";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
        }
        private void restartShard(String index) {
            String fName = "[restartShard]";
            logger.info(cName + fName);
            int i=-1;
            try {
                i=Integer.parseInt(index);
                if(i<0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Invalid "+i+".",llColorRed);
                    return;
                }
                gGlobal.shardManager.restart(i);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Restarted shard "+i+".",llColorBlue1);
        }
        private void restarShards() {
            String fName = "[restarShards]";
            logger.info(cName + fName);
            gGlobal.shardManager.restart();
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Restarted shards.",llColorBlue1);

        }
        private void stopShard(String index) {
            String fName = "[stopShard]";
            logger.info(cName + fName);
            int i=-1;
            try {
                i=Integer.parseInt(index);
                if(i<0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Invalid "+i+".",llColorRed);
                    return;
                }
                gGlobal.shardManager.shutdown(i);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Shutdown shard "+i+".",llColorBlue1);
        }
        private void stopShards() {
            String fName = "[stopShards]";
            logger.info(cName + fName);
            gGlobal.shardManager.shutdown();
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Shutdown shards.",llColorBlue1);

        }
        private void startShard(String index) {
            String fName = "[startShard]";
            logger.info(cName + fName);
           int i=-1;
            try {
                i=Integer.parseInt(index);
                if(i<0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Invalid "+i+".",llColorRed);
                    return;
                }
                gGlobal.shardManager.start(i);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Shutdown shard "+i+".",llColorBlue1);
        }
        private void eval(String[] items) {
            String fName = "[eval]";
            logger.info(cName + fName);
            if(items.length == 1){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"I need at least one argument!");
                return;
            }

            ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
            se.put("jda", gCommandEvent.getJDA());
            se.put("shardManager", gGlobal.shardManager);
            se.put("guild", gGuild);
            se.put("channel", gTextChannel);
            se.put("msg", gMessage);
            String statement = lsStringUsefullFunctions.arrayStringJoin2String(items," ",1,true);
            logger.info(cName + fName+"statement="+statement);
            long startTime = System.currentTimeMillis();
            try{
                String result = se.eval(statement).toString();

                sendEvalEmbed(gTextChannel, statement, result, String.format(
                        "Evaluated in %dms",
                        System.currentTimeMillis() - startTime
                ), true);
            }catch(ScriptException ex){
                sendEvalEmbed(gTextChannel, statement, ex.getMessage(), String.format(
                        "Evaluated in %dms",
                        System.currentTimeMillis() - startTime
                ), false);
            }

        }
        private void sendEvalEmbed(TextChannel tc, String input, String output, String footer, boolean success){
            String fName = "[sendEvalEmbed]";
            logger.info(cName + fName+"input="+input+", output="+output+", footer="+footer+", success="+success);
            String newMsg = input;
            String overflow = null;
            if(newMsg.length() > 2000){
                overflow = newMsg.substring(1999);
                newMsg = newMsg.substring(0, 1999);
            }
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(success ? 0x00FF00 : 0xFF0000);
            embed.addField("Input", String.format(
                            "```java\n" +
                                    "%s\n" +
                                    "```",
                            newMsg
                    ), false);
            embed.addField("Output", String.format(
                            "```java\n" +
                                    "%s\n" +
                                    "```",
                            output
                    ), false);
            embed.setFooter(footer, null);
            tc.sendMessageEmbeds(embed.build()).queue();
            if(overflow != null)
                sendEvalEmbed(tc, overflow, output, footer, success);
        }
        private void doLeaveBannedGuilds() {
            gGlobal.banguilds.setJDA(gGlobal.getJDAList());
            List<BannedGuilds.GuildEntity>guilds=gGlobal.banguilds.leaveGuilds();
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Left guilds"+guilds.size(),llColorBlue1);
        }
        private void doReloadNotification() {
            boolean result=gGlobal.generalGuildNotification.init(lsGeneralGuildNotificationPath);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Reloaded notification: "+result,llColorBlue1);
        }
    }
}
