package bot;

import FlexiPlay.FlexiPlayer;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.neovisionaries.ws.client.WebSocketFactory;
import events.*;
import events.Guild.*;
import events.generic.onRawGateway;
import forRemoval.nsfw.Locktober;
import forRemoval.nsfw.chastitycompetition;
import forRemoval.nsfw.diaperInteractive;
import forRemoval.social.NSFW.nKeyTease;
import forRemoval.social.NSFW.social621NSFW;
import forRemoval.social.SFW.boop;
import forRemoval.social.SFW.social621SFW;
import fun.*;
import fun.games.HangMan;
import fun.games.Minesweeper;
import forRemoval.thehospital.thehospital;
import forRemoval.textadventure.spooktober.Spooktober;
import holidays.naughtypresents.NaughtyPresents;
import forRemoval.holidays.outdated.NSantaLetters;
import forRemoval.holidays.outdated.NaughtyGifts;
import forRemoval.holidays.outdated.SantaLetters;
import holidays.commodore64;
import forRemoval.imagify.Image4List;
import imagify.LinkImagePreview;
import interaction.button.buttonControler;
import interaction.slash.slashSetup_Guild_v3;
import interaction.usercommand.usercommandControler;
import models.lc.BannedGuilds;
import models.lc.lcSqlConnEntity;
import models.lc.UnicodeEmojis;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import nsfw.*;
import nsfw.SizeMeUp.AnnualOrgasmPlanner;
import nsfw.SizeMeUp.SizeMeUp_Penis;
import nsfw.chastity.chastikey.ChastiKey;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.chastity.chastitysession.chastitysession;
import nsfw.chastity.lockedmen.Lockedmen;
import nsfw.diaper.*;
import nsfw.faprulette.FapRoulette;
import nsfw.lovense.Lovense;
import nsfw.verify.lewdverify;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import restraints.*;
import restraints.rdLock;
import search.*;
import interaction.slash.slashControler;
import sfw.numbergenerator.NumberGenerator;
import test.dstickers.DiscordStickersUtility;
import test.test1;
import test.test2;

import test.test3_Application;
import userprofiles.*;
import util.*;
import util.entity.GuildNotificationReader;
import util.inhouse.*;
import util.removed.utilityApplication;
import util.removed.worldclock;
import util.unfinished.utilityGetTime;
import web.jexpress.ServerExpress;
import web.spring.Spring2Init;


import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class Bot implements llGlobalHelper {
    static String cName = "[main.java.bot]";
    public static lcGlobalHelper xGlobal;public boolean isLoadedBot=false,isLoadedWeb=false;
    public static void main(String[] args) throws Exception {
        switch (llBotToken){
            case llMainBotToke:
            case llLaxBotToken:
                PropertyConfigurator.configure(llLogNormal);
                break;
            default:
                PropertyConfigurator.configure(llLogDefault);
                break;
        }
        xGlobal=startup();
        webServiceStart();
    }
    Logger logger = Logger.getLogger(getClass());
    public boolean startAll() {
        String fName = "[startAll]";
        logger.info(cName + fName + ".init");
        return startBot()&&startWeb();
    }
    public boolean stopAll() {
        String fName = "[stopAll]";
        logger.info(cName + fName + ".init");
        return stopBot()&&stopWeb();
    }
    public boolean startBot() {
        String fName = "[startBot]";
        try {
            if (isLoadedBot) {
                logger.warn(cName + fName + ".already started");
                return false;
            }
            logger.warn(cName + fName + ".starting up");
            xGlobal = startup();
            isLoadedBot = true;
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception=" + e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean stopBot() {
        String fName = "[stopBot]";
        try {
            if (!isLoadedBot) {
                logger.warn(cName + fName + ".already stopped");
                return false;
            }
            logger.warn(cName + fName + ".stopping");
            List<JDA>clients=xGlobal.getJDAList();
            xGlobal.endGeneralEventWaiter();
            for (JDA client : clients){
                client.removeEventListener(client.getEventManager());
            }
            for (JDA client : clients){
                client.shutdown();
            }
            isLoadedBot = false;
            logger.warn(cName + fName + ".stopped");
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception=" + e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean startWeb() {
        String fName = "[startWeb]";
        try {
            if (isLoadedWeb) {
                logger.warn(cName + fName + ".already started");
                return false;
            }
            webServiceStart();
            isLoadedWeb=true;
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception=" + e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean stopWeb() {
        String fName = "[stopWeb]";
        try {
            if (!isLoadedWeb) {
                logger.warn(cName + fName + ".already stopped");
                return false;
            }
            webServiceStop();
            isLoadedWeb=false;
            logger.warn(cName + fName + ".stopped");
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception=" + e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public static lcGlobalHelper startup() {
        Logger logger = Logger.getLogger(Bot.class);
        String fName="startup";
        try {
            CommandClientBuilder client = new CommandClientBuilder();
            // The default is "Type !!help" (or whatver prefix you set)
            //client.useDefaultGame();
            lcGlobalHelper global = new lcGlobalHelper();
            String logmode="";
            switch (llLogMode){
                case 1:
                    logmode = llLogDefault;
                    break;
                case 2:
                    logmode = llLogNormal;
                    break;
                default:
                    switch (llBotToken){
                        case llMainBotToke:
                        case llLaxBotToken:
                            logmode = llLogNormal;
                            break;
                        default:
                            logmode = llLogDefault;
                            break;
                    }
                    break;
            }
            try {
                logger.warn(cName + ": Set log="+logmode);
                PropertyConfigurator.configure(logmode);
                global.logPreference = logmode;
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.warn(cName + ":Loading Resources");
                global.emojis =new UnicodeEmojis() ;
                global.banguilds =new BannedGuilds() ;
                global.generalGuildNotification =new GuildNotificationReader(global) ;
                global.generalGuildNotification.init();
                logger.warn(cName + ": Resources Loaded");
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            lsGlobalHelper.sAddGlobal(global);
            //JDABuilder builder = JDABuilder.create(llBotToken,EnumSet.allOf(GatewayIntent.class));
            logger.warn(cName + ":JSON config file read:" + global.configfile.readFile());
            global.configfile.readProperties();
            DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(global.configfile.getBot().getToken(), EnumSet.allOf(GatewayIntent.class));
            try {
                if(global.configfile.getBot().getWebsoketFactory().isEnabled()){
                    WebSocketFactory webSocketFactory=global.configfile.getBot().getWebsoketFactory().build();
                    if(webSocketFactory==null)throw new Exception("Failed to build custom websocketfactory");
                    builder.setWebsocketFactory(webSocketFactory);
                    logger.warn(cName + ": load custom.webSocketFactory connectionTimeout=" + webSocketFactory.getConnectionTimeout()+", socketTimeout="+webSocketFactory.getSocketTimeout());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            }
            try {
                if(global.configfile.getBot().getHttpClientBuilder().isEnabled()){
                    OkHttpClient.Builder httpClientBuilder=global.configfile.getBot().getHttpClientBuilder().build();
                    if(httpClientBuilder==null)throw new Exception("Failed to build custom httpClientBuilder");
                    builder.setHttpClientBuilder(httpClientBuilder);
                    logger.warn(cName + ": load custom.httpClientBuilder maxRequestsPerHost=" + global.configfile.getBot().getHttpClientBuilder().getMaxRequestsPerHost()+", maxIdleConnections="+global.configfile.getBot().getHttpClientBuilder().getMaxIdleConnections()+", keepAlliveDuration(seconds)="+global.configfile.getBot().getHttpClientBuilder().getKeepAliveDuration());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            }

            builder.setShardsTotal(global.configfile.getBot().getShards());
            logger.warn(cName + ": set.shards=" + global.configfile.getBot().getShards());
            builder.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING);
            builder.disableCache(CacheFlag.ACTIVITY,CacheFlag.CLIENT_STATUS,CacheFlag.ONLINE_STATUS);
            try {
                if(global.configfile.getBot().hasMemberCachePolicy()){
                    builder.setMemberCachePolicy(global.configfile.getBot().getMemberCachePolicy());
                    logger.warn(cName + ": load custom.MemberCachePolicyAsInt=" + global.configfile.getBot().getMemberCachePolicyAsInt());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            }

            client.setOwnerId(global.configfile.getBot().getOwnerId());
            client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");
            client.useHelpBuilder(llClientUseHelpBuilder);
            client.setHelpWord(llHelpWord);
            client.setAlternativePrefix(llPrefixStr);
            global.worldclock = new worldclock(true);
            global.sql = new lcSqlConnEntity();
            logger.warn(cName + ": adding commands");
            client.addCommands(
                    new about(global), new HelpCommand(global), new utilityBot(global), new utilityTransport(global), new utilityPersistenceRole(global), new utilityGuild(global), new utilitySelf(global), new utilityWebHook(global),
                    new utilitySay(global), new utilityEmote(global), new utilityMusic(global), new utilityModMail(global), new utilityUser(global), new utilityStickerSurge(global), new utilityStickersIncluded(global),
                    new utilityAutoPosting(global), new information(global), new utilitySqlLog(global), new utilityGetTime(global),new utilityEat(global), new utilitySelfRole(global), new utilityAutoRole(global), new utilityChannel(global), new utilityRole(global), new utilityApplication(global),
                    new utilityVotingAttachment(global),new utilityPrune(global), new utilityCache(global),
                    new utilityMessage(global), new utilityStickers(global), new utilityPrivacy(global), new utilityUpdateNotification(global)
            );
            client.addCommands(
                    new rdRestraints(global), new rdGag(global), new rdCuffs(global), new rdMitts(global), new rdStraitjacket(global), new rdAuth(global), new rdLeash(global), new rdRestrictions(global), new rdNameTag(global),
                    new rdHood(global), new rdBlindfold(global), new rdChastity(global), new rdEars(global), new rdSuit(global), new rdCollar(global), new rdTimelock(global), new rdTimeout(global), new rdAction(global), new rdUtility(global), new rdPishock(global),
                    new rdLock(global), new rdConfine(global), new rdEncase(global), new rdBreathplay(global)
            );
            client.addCommands(
                     new lewdverify(global), new chastitycompetition(global),new chastitysession(global),new ChastiKey(global), new chastitytime(global), new ChastityEmlalock(global),new Locktober(global),
                    new lewdInteraction(global), new diaperInteractive(global),new LewdImageEvents(global),new LinkImagePreview(global),
                    new nekoLife_Fact(), new nekoLife_8ball(), new nekoLife_owoify(), new nekoLife_Why(), new chuckNorris_Joke(),
                    new e621(global), new furaffinity(global), new inkbunny(global),new speaker(), new kitsu(global), new urbandictionary(global), new QuickLewdImages(global), new Image4List(global), new postZalgo(), new figlet(), new encodePoster(),
                    new confessionalBooth(global)       );
            client.addCommands(new diaperInteractive_v2(global),new diSetup(global), new diSuit(global), new diDiaper(global), new diTimelock(global));
            //client.addCommands(new CatCommand(global), new ChooseCommand(global), new HelloCommand(global));
            //client.addCommands( new egghunt(global), new santaClaus(global),new newyear(global),new worldclock(global), new valentinesLetter(global), new regiontracker(global));
            client.addCommand(new NaughtyPresents(global));
            client.addCommands(
                    new social621SFW(global), new social621NSFW(global),new SizeMeUp_Penis(global),new AnnualOrgasmPlanner(global),new Lovense(global),
                    new boop(global), new nKeyTease(global), new commodore64(), new NaughtyGifts(global),new SantaLetters(global), new NSantaLetters(global)
            );
            client.addCommands(
                    new thehospital(global),new fursona(global), new usersona(global),new nfursona(global), new nusersona(global), new character(global), new ncharacter(global), new nSlaveRegistry(global), new nPatient(global), new Patient(global)
            );
            client.addCommands(
                    new slashControler(global),
                    new buttonControler(global), new slashSetup_Guild_v3(global)
            );
            client.addCommands(
                   new FlexiPlayer(), new Minesweeper(), new HangMan(),new DiscordStickersUtility(global), new NumberGenerator(global),
                    new Spooktober(global)
            );
            client.addCommands(
                    new test1(global),new test2(global),new test3_Application(global), new usercommandControler(global),
                    new FapRoulette(global), new Lockedmen(global)
                    //new LoveSenseLocal(global)// new slashCommandsTest(global)
            );
            logger.warn(cName + ": commands added, adding events");
            builder.addEventListeners(new onGuild(global), new onGuildMessage(global),new onGuildMember(global), new onGuildVoice(global));
            //builder.addEventListeners(new onGuildUpdate(),  new onRole(), new onEmote(), new onGuildInvite());
            builder.addEventListeners(new onPrivateMessage());
            //builder.addEventListeners(new onUser(global));
            //builder.addEventListeners(new onCategory(global), new onTextChannel(), new onVoiceChannel(), new onPermissionOverride(global), new onStoreChannel());
            builder.setRawEventsEnabled(true);
            builder.addEventListeners( new onJDA(global), new onRawGateway(global));
            //builder.addEventListeners(new onSelfUpdate(), new onJDAExtra(global), new onGeneric(global));
            builder.addEventListeners( new onInteraction(global));
            //builder.addEventListeners( new onRole(), new onMessage(global));
            logger.warn(cName + ": events added");
            Activity activity = Activity.playing(" with cookies!");
            builder.setActivity(activity);
            client.setActivity(activity);
            builder.setStatus(OnlineStatus.OFFLINE);
            client.setStatus(OnlineStatus.OFFLINE);
            logger.warn(cName + ": set.activity&status");
            CommandClient commandClientBuilt = client.build();
            global.setGeneralEventWaiter();global.setScheduleThreadpool();
            builder.addEventListeners(global.waiter, commandClientBuilt);

            logger.warn(cName + ":Sql Driver start:" + global.sql.startDriver());
            logger.warn(cName + ":Sql Init Config:" + global.sql.getProperties(global));
            logger.warn(cName + ":Sql Open connection:" + global.sql.openConnection());

            builder.setAutoReconnect(true);
            global.shardManager=builder.build();

            logger.warn(cName + ":Sharding built");
            logger.warn(cName + ":Bot loaded");
            global.commandClientBuilt = commandClientBuilt;
            global.createAudioManager();
            global.banguilds.setJDA(global.getJDAList());

            global.pishockConfig.set(global.configfile);
            logger.warn(cName + ": OwO");

            return  global;
        }catch(Exception e)
            {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    public static boolean webServiceStart() {
        Logger logger = Logger.getLogger(cName);
        String fName="[webServiceStart]";
        logger.info(cName + fName + "init");
        boolean resultExpress=false,resultSpring=false;
        try{
            logger.info(cName + fName + "ServerExpress.1");
            xGlobal.serverExpress= new ServerExpress(xGlobal);
            if(!xGlobal.serverExpress.start())throw  new Exception("Failed to start express server");
            logger.warn(cName + fName + "ServerExpress started");
            resultExpress=true;
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        try{
            //xGlobal.botTelegram = new TelegramBot("1364622474:AAGCEl5tzQP8XVPYBljs00Zmn3Nz_WG2l9M");
            //xGlobal.telegramRoot=new TelegramRoot(xGlobal);
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        try{
            logger.info(cName + fName + "Spring2Init.1");
            xGlobal.spring2Init= new Spring2Init(xGlobal);
            if(xGlobal.spring2Init.build()==null)throw  new Exception("Failed to build spring server");
            //if(!xGlobal.spring2Init.start())throw  new Exception("Failed to start spring server");
            //if(xGlobal.spring2Init.connect2MongoDb()==null)throw  new Exception("Failed to connect2MongoDb");
            logger.warn(cName + fName + "Spring2Init started");
            resultSpring=true;
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        logger.info(cName + fName + "resultExpress="+resultExpress+", resultSpring="+resultSpring);
        return  resultExpress&&resultSpring;
    }
    public static boolean webServiceStop() {
        Logger logger = Logger.getLogger(cName);
        String fName="[webServiceStop]";
        logger.info(cName + fName + "init");
        boolean resultExpress=false,resultSpring=false;
        try{
            logger.warn(cName + fName + "ServerExpress stopping");
            if(!xGlobal.serverExpress.stop())throw  new Exception("Failed");
            logger.warn(cName + fName + "ServerExpress stopped");
            resultExpress=true;
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        try{
            //xGlobal.botTelegram = new TelegramBot("1364622474:AAGCEl5tzQP8XVPYBljs00Zmn3Nz_WG2l9M");
            //xGlobal.telegramRoot=new TelegramRoot(xGlobal);
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        try{
            logger.warn(cName + fName + "Spring stopping");
            if(!xGlobal.spring2Init.stop())throw  new Exception("Failed");
            logger.warn(cName + fName + "Spring stopped");
            resultSpring=true;
        }catch (Exception e){
            logger.error(cName + fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
        }
        logger.info(cName + fName + "resultExpress="+resultExpress+", resultSpring="+resultSpring);
        return  resultExpress&&resultSpring;

    }
}
