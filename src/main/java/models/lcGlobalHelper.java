package models;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.BannedGuilds;
import models.lc.helper.lcSendMessageHelper;
import models.lc.json.*;
import models.lc.UnicodeEmojis;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcGuildMusicManager;
import models.lc.lcSqlConnEntity;
import models.ll.llMessageHelper;
import models.ls.lsChannelHelper;
import models.ls.lsGuildHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import telegram.TelegramRoot;
import util.entity.GuildNotificationReader;
import util.removed.worldclock;
import web.jexpress.OAUTH2HANDLING;
import web.jexpress.ServerExpress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class lcGlobalHelper implements llMessageHelper, llGlobalHelper {

    String cName="[llGlobalHelper]";
    Logger logger = Logger.getLogger(getClass());

    public lcGlobalHelper(){
        String fName="constructor";
        logger.info(cName+fName);
        //guilds=new TreeMap<String, Guild>();

    }
    public String logPreference="";
    public EventWaiter waiter; public ScheduledExecutorService waiterThreadpool;
    public lcSqlConnEntity sql;
    //public CommandClientBuilder commandclient;
    public CommandClient commandClientBuilt;

    //public JSONObject Timers= new JSONObject();
    //public JSONObject Objects= new JSONObject();
    //public regiontracker BgRegionTrackerThread;
    public worldclock worldclock;
    public UnicodeEmojis emojis;
    public BannedGuilds banguilds;
    //public newyear newYearTrackerThread;
    public JSONObject jsonObject=new JSONObject();

    public ShardManager shardManager=null;

    public Map<String, EventWaiter> eventwaiters=new TreeMap<>();
    public Guild GuildKeyBotHelper;

    public List<Guild> getGuildList(){
        String fName="getGuildList";
        try{
            List<Guild>list = lsGuildHelper.getGuildsAsList(getJDAList());
            logger.info(cName+fName+"size:"+list.size());
            return list;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);return null;
        }
    }
    public List<JDA> getJDAList(){
        String fName="getJDAList";
        try{
            List<JDA>list =shardManager.getShards();
            logger.info(cName+fName+"size:"+list.size());
            return list;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);return null;
        }
    }
    public JDA getJDA(int i){
        String fName="getJDA";
        try{
            logger.info(cName+fName+"i:"+i);
            JDA jda =shardManager.getShardById(i);
            return jda;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(long id){
        String fName="getGuild";
        logger.info(cName+fName);
        try{
            return lsGuildHelper.getGuild(getJDAList(),id);
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(String id){
        String fName="getGuild";
        logger.info(cName+fName);
        try{
            id=id.replaceAll("g_","").replaceAll("g-","");
            return lsGuildHelper.getGuild(getJDAList(), Long.parseLong(id));
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Map<String, Guild> getGuildMap4JDA(){
        String fName="getGuildMap4JDA";
        try{
            Map<String, Guild> guilds= new LinkedHashMap<>();
            for(JDA jda:shardManager.getShards()) {
                logger.info(cName+fName+"shard id:"+jda.getShardInfo().getShardId());
                if(jda!=null){
                    logger.info(cName+fName+"entry:not null");
                    List<Guild> items= jda.getGuilds();
                    logger.info(cName+fName+"entry items:"+items.size());
                    for(Guild item : items){
                        logger.info(cName+fName+"item id:"+item.getId());
                        guilds.put("g_"+item.getId(),item);
                    }
                }
            }
            logger.info(cName+fName+"size:"+guilds.size());
            return guilds;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);return null;
        }
    }
    public Map<String, Guild> getGuildMap4JDAv2(){
        String fName="getGuildMap4JDAv2";
        try{
            Map<String, Guild> guilds= new LinkedHashMap<>();
            for(JDA jda:shardManager.getShards()) {
                logger.info(cName+fName+"shard id:"+jda.getShardInfo().getShardId());
                if(jda!=null){
                    logger.info(cName+fName+"entry:not null");
                    List<Guild> items= jda.getGuilds();
                    logger.info(cName+fName+"entry items:"+items.size());
                    for(Guild item : items){
                        logger.info(cName+fName+"item id:"+item.getId());
                        guilds.put(item.getId(),item);
                    }
                }
            }
            logger.info(cName+fName+"size:"+guilds.size());
            return guilds;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);return null;
        }
    }
    public void initGuild(Guild guild){
        String fName="[initGuild]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            guildsSettingsGet4Db(guild);
            createAudioPlayer(guild);
            if(guild.getId().equalsIgnoreCase(llGuildKeyBotHelper)){
                GuildKeyBotHelper=guild;
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public JSONObject globalSettingsJson = new JSONObject();
    public void globalSettingsGet4Db(){
        String fName="[globalSettingsGet4Db]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+"start");
            if (!sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            lcJSONGlobalEntries entries=new lcJSONGlobalEntries(this,llv2_GlobalsSettings);
            if(entries.dbGetAllEntries()){
               globalSettingsJson=entries.jsonUser;
            }else{
                logger.warn(cName + fName + ".db get failed");
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public Boolean putGlobalSettings(lcJSONGlobalEntry entry){
        String fName="[putGlobalSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".global");
            globalSettingsJson.put(entry.gName,entry.jsonUser);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcJSONGlobalEntry getGlobalSettings(String name){
        String fName="[getGlobalSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".global");
            logger.info(cName+fName+".name:"+name);
            if(globalSettingsJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }if(globalSettingsJson.has(name)){
                logger.warn(cName + fName + ".has no such name");
                return null;
            }
            lcJSONGlobalEntry entries=new lcJSONGlobalEntry(this,name,llv2_GuildsSettings);
            entries.jsonUser=globalSettingsJson.getJSONObject(name);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /*public void addEventWaiter(JDA jda){
        String fName="[addEventWaiter]";
        logger.info(cName+fName);
        try{
            int id=jda.getShardInfo().getShardId();
            logger.info(cName+fName+"id:"+id);
            EventWaiter waiter = new EventWaiter();
            eventwaiters.put(String.valueOf(id),waiter);
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public void addEventWaiters(){
        String fName="[addEventWaiters]";
        logger.info(cName+fName);
        try{
            List<JDA>jdas=getJDAList();
            for(JDA jda:jdas){
                addEventWaiter(jda);
            }
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public EventWaiter getEventWaiter(JDA jda){
        String fName="[getEventWaiter]";
        logger.info(cName+fName);
        try{
            int id=jda.getShardInfo().getShardId();
            logger.info(cName+fName+"id:"+id);
            EventWaiter waiter=eventwaiters.get(String.valueOf(id));
            logger.info(cName+fName+"waiter.toString="+waiter.toString());
            return waiter;
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
            return  null;
        }
    }
    public EventWaiter getEventWaiter(Guild guild){
        String fName="[getEventWaiter]";
        logger.info(cName+fName);
        try{
            return getEventWaiter(guild.getJDA());
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
            return  null;
        }
    }
    public void endEventWaiter(JDA jda){
        String fName="[endEventWaiter]";
        logger.info(cName+fName);
        try{
            int id=jda.getShardInfo().getShardId();
            logger.info(cName+fName+"id:"+id);
            eventwaiters.get(String.valueOf(id)).shutdown();
            eventwaiters.remove(String.valueOf(id));
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public void endEventWaiters(){
        String fName="[endEventWaiters]";
        logger.info(cName+fName);
        try{
            List<JDA>jdas=getJDAList();
            for(JDA jda:jdas){
                endEventWaiter(jda);
            }
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }*/
    //https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Executors.html
    public void setGeneralEventWaiter(){
        String fName="[setGeneralEventWaiter]";
        logger.info(cName+fName);
        try{
            waiterThreadpool=Executors.newScheduledThreadPool(configfile.botconfig.getWaiterThreadpoolCount());
            waiter=new EventWaiter(waiterThreadpool,true);
            /*
            waiterThreadpool=Executors.newSingleThreadScheduledExecutor();
            //waiter=new EventWaiter(waiterThreadpool,false);
            //not working as expected
            waiter=new EventWaiter(waiterThreadpool,true);
            */
            /*waiterThreadpool=Executors.newScheduledThreadPool(3);
            waiter=new EventWaiter(waiterThreadpool,false);*/
            logger.warn(cName + fName + ".done");
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public void endGeneralEventWaiter(){
        String fName="[endGeneralEventWaiter]";
        logger.info(cName+fName);
        try{

            //not working
            //waiter.shutdown();
            //waiterThreadpool.shutdownNow();
            //waiterThreadpool.shutdown();
            /*logger.warn(cName + fName + ".done");
            logger.warn(cName + fName + ".waiterThreadpool.isShutdown="+waiterThreadpool.isShutdown());
            logger.warn(cName + fName + ".waiterThreadpool.isTerminated="+waiterThreadpool.isTerminated());*/
        }catch (Exception e){
            logger.error(cName + fName + ".exception:" + e);
        }
    }
    public JSONObject guildsSettingsJson = new JSONObject();
    public void guildsSettingsGet4Db(Guild guild){
        String fName="[guildsSettingsGet4Db]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if (!sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            lcJSONGuildEntries entries=new lcJSONGuildEntries(this,guild,llv2_GuildsSettings);
            if(entries.dbGetAllEntries()){
                if(!entries.isExistent()){
                    logger.warn(cName + fName + ".not existing");
                }
                entries=guildRegister(entries,guild);
                guildsSettingsJson.put(guild.getId(),entries.jsonUser);
            }else{
                logger.warn(cName + fName + ".db get failed");
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean hasGuildsSettings(Guild guild){
        String fName="[hasGuildsSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(guildsSettingsJson==null){
                logger.info(cName+fName+".isNull");
                return false;
            }
            if(!guildsSettingsJson.has(guild.getId())){
                logger.info(cName+fName+".has no such entry");
                return  false;
            }
            if(guildsSettingsJson.isNull(guild.getId())){
                logger.info(cName+fName+".entry is null");
                return  false;
            }
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public void guildsSettingsRemove(Guild guild){
        String fName="[guildsSettingsRemove]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(guildsSettingsJson!=null&&guildsSettingsJson.has(guild.getId())){
                logger.info(cName+fName+".has entry");
                guildsSettingsJson.remove(guild.getId());
                logger.info(cName+fName+".removed");
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public final String keyCustomServer="customserver",keyInHouse="inhouse",keyServer="server",keyMeta="meta",keyBot="bot",keyInfo="info",keyOwner="owner",keyId="id", keyName="name",
            keyIcon="icon",keyDescription="description", keyAudit="audit", keyIsNSFWServer="isNSFWServer", keyInviteLink="invitelink",keyNickname="nickname",
            keyRolesCount="rolesCount", keyRoles="roles",keyPermissionsCount="permissionsCount", keyPermissions="permissions", keyChannelsCount="channelsCount",
            keyTextChannelsCount="textchannelsCount", keyVoiceChannelsCount="voicechannelsCount",keyCategoryChannelsCount="categorychannelsCount",
            keyMembersCount="membersCount", keyBoost="boost",keyBoostCount="boostCount", keyBoostTier="boostTier",keySplashUrl="splashUrl", keyBannerUrl="bannerUrl", keyVanityUrl="vanityUrl",
            keyBDSMRestrictions="bdsmrestriction",keyCommandsUsedCount="commandUsedCount",keyGagUsedCount="gagUsedCount";
    public lcJSONGuildEntries guildRegister(lcJSONGuildEntries entries,Guild guild){
        //https://discordapp.com/developers/docs/resources/channel
        String fName="[guildRegister]";
        logger.info(cName+fName);
        try {
            lcJSONGuildProfile entrie= entries.getEntry(keyServer);
            Member selfMember=null,ownerMember=null;User ownerUser=null;JSONArray jsonArray=new JSONArray();List<Role>roles=new ArrayList<>();
            if(entrie==null){
                logger.warn(cName+fName+".guild:"+guild.getId()+" is not registered, no entry");
                entrie=new lcJSONGuildProfile(this,keyServer,guild);
            }else
            if(entrie.jsonObject ==null){
                logger.warn(cName+fName+".guild:"+guild.getId()+" is not registered, no json");
                entrie.jsonObject =new JSONObject();
            }else{
                logger.warn(cName+fName+".guild:"+guild.getId()+" already registered");
            }
            if(!entrie.hasFieldEntry(keyMeta))entrie.createFieldEntry(keyMeta);
            String guildId=guild.getId();
            entrie.putFieldEntry(keyMeta,keyId,guildId);
            if(guild.getIconUrl()!=null){
                entrie.putFieldEntry(keyMeta,keyIcon,guild.getIconUrl());
                logger.info(cName+fName+".guild:"+guildId+" guild.getIconUrl()="+guild.getIconUrl());
            }
            entrie.putFieldEntry(keyMeta,keyName,guild.getName());
            logger.info(cName+fName+".guild:"+guildId+" guild.getName()="+guild.getName());
            if(guild.getDescription()!=null){
                entrie.putFieldEntry(keyMeta,keyDescription,guild.getDescription());
                logger.info(cName+fName+".guild:"+guildId+" guild.getDescription()="+guild.getDescription());

            }
            try {
                roles=guild.getRoles();
                logger.info(cName+fName+".guild:"+guildId+" guild.roles.size="+roles.size());
                entrie.putFieldEntry(keyMeta,keyRolesCount,roles.size());
                List<GuildChannel>channels=guild.getChannels();
                logger.info(cName+fName+".guild:"+guildId+" guild.channels.size="+channels.size());
                int countTextChannel=0, countVoiceChannel=0, countCategoryChannel=0;
                for (GuildChannel channel : channels) {
                    String channelId=channel.getId();
                    ChannelType channelType=channel.getType();
                    logger.info(cName+fName+".guild:"+guildId+" channel["+channelId+"].type="+ channelType.getId());
                    if(channelType==ChannelType.TEXT){countTextChannel++;}
                    else if(channelType==ChannelType.VOICE){countVoiceChannel++;}
                    else if(channelType==ChannelType.CATEGORY){countCategoryChannel++;}
                }
                entrie.putFieldEntry(keyMeta,keyChannelsCount,channels.size());
                entrie.putFieldEntry(keyMeta,keyTextChannelsCount,countTextChannel);
                entrie.putFieldEntry(keyMeta,keyVoiceChannelsCount,countVoiceChannel);
                entrie.putFieldEntry(keyMeta,keyCategoryChannelsCount,countCategoryChannel);
                int countMembers=guild.getMemberCount();
                logger.info(cName+fName+".guild:"+guildId+" guild.countMembers="+countMembers);
                entrie.putFieldEntry(keyMeta,keyMembersCount,countMembers);
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }


            try {
                int countBoost = guild.getBoostCount();
                Guild.BoostTier boostTier = guild.getBoostTier();
                String splashUrl = guild.getSplashUrl();
                String bannerUrl = guild.getBannerUrl();
                String vanityUrl = guild.getVanityUrl();
                if(!entrie.hasFieldEntry(keyBoost))entrie.createFieldEntry(keyBoost);
                logger.info(cName+fName+".guild:"+guildId+" countBoost="+ countBoost);
                entrie.putFieldEntry(keyBoost,keyBoostCount, countBoost);
                logger.info(cName+fName+".guild:"+guildId+" boostTier="+ boostTier.getKey());
                entrie.putFieldEntry(keyBoost,keyBoostTier, boostTier.getKey());
                if(splashUrl!=null&&!splashUrl.isBlank()){
                    logger.info(cName+fName+".guild:"+guildId+" splashUrl="+ splashUrl);
                    entrie.putFieldEntry(keyBoost,keySplashUrl, splashUrl);
                }
                if(bannerUrl!=null&&!bannerUrl.isBlank()){
                    logger.info(cName+fName+".guild:"+guildId+" bannerUrl="+ bannerUrl);
                    entrie.putFieldEntry(keyBoost,keyBannerUrl, bannerUrl);
                }
                if(vanityUrl!=null&&!vanityUrl.isBlank()){
                    logger.info(cName+fName+".guild:"+guildId+" vanityUrl="+ vanityUrl);
                    entrie.putFieldEntry(keyBoost,keyVanityUrl, vanityUrl);
                }
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            try {
                if(guild.getOwner()!=null){
                    ownerMember= guild.getOwner();
                    ownerUser= ownerMember.getUser();
                    if(!entrie.hasFieldEntry(keyOwner))entrie.createFieldEntry(keyOwner);
                    entrie.putFieldEntry(keyOwner,keyId,ownerMember.getId());
                    logger.info(cName+fName+".guild:"+guildId+" ownerMember.getId()="+ownerMember.getId());
                    entrie.putFieldEntry(keyOwner,keyName,ownerUser.getName());
                    logger.info(cName+fName+".guild:"+guildId+" ownerUser.getName()="+ownerUser.getName());
                    if(ownerUser.getAvatarUrl()!=null){
                        entrie.putFieldEntry(keyOwner,keyIcon,ownerUser.getEffectiveAvatarUrl());
                        logger.info(cName+fName+".guild:"+guildId+" ownerUser.getAvatarUrl()="+ownerUser.getEffectiveAvatarUrl());
                    }
                }
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            try {
                selfMember=guild.getSelfMember();
                if(!entrie.hasFieldEntry(keyBot))entrie.createFieldEntry(keyBot);
                entrie.putFieldEntry(keyBot,keyId,selfMember.getId());
                logger.info(cName+fName+".guild:"+guildId+" selfMember.getId()="+selfMember.getId());
                entrie.putFieldEntry(keyBot,keyName,selfMember.getUser().getName());
                logger.info(cName+fName+".guild:"+guildId+" selfMember.getUser().getName()="+selfMember.getUser().getName());
                if(selfMember.getUser().getAvatarUrl()!=null){
                    entrie.putFieldEntry(keyBot,keyIcon,selfMember.getUser().getAvatarUrl());
                    logger.info(cName+fName+".guild:"+guildId+" elfMember.getUser().getAvatarUrl()="+selfMember.getUser().getAvatarUrl());
                }
                if(selfMember.getNickname()!=null){
                    entrie.putFieldEntry(keyBot,keyNickname,selfMember.getNickname());
                    logger.info(cName+fName+".guild:"+guildId+" selfMember.getNickname()="+selfMember.getNickname());
                }
                jsonArray=new JSONArray();
                roles=selfMember.getRoles();
                logger.info(cName+fName+".guild:"+guildId+" selfMember.roles.size="+roles.size());
                entrie.putFieldEntry(keyBot,keyRolesCount,roles.size());
                if(roles.size()==0){
                    entrie.putFieldEntry(keyBot,keyRoles,new JSONArray());
                }else{
                    for (Role role : roles) {
                        jsonArray.put(role.getId()+","+role.getName());
                    }
                    logger.info(cName+fName+".guild:"+guildId+" selfMember.roles="+jsonArray.toString());
                    entrie.putFieldEntry(keyBot,keyRoles,jsonArray);
                }

                jsonArray=new JSONArray();
                EnumSet<Permission> permissions=selfMember.getPermissions();
                logger.info(cName+fName+".guild:"+guildId+" selfMember.permissions.size="+permissions.size());
                entrie.putFieldEntry(keyBot,keyPermissionsCount,permissions.size());
                if(roles.size()==0){
                    entrie.putFieldEntry(keyBot,keyPermissions,new JSONArray());
                }else{
                    for (Permission permission : permissions) {
                        jsonArray.put(permission.getName());
                    }
                    entrie.putFieldEntry(keyBot,keyPermissions,jsonArray);
                }
                logger.info(cName+fName+".guild:"+guildId+" selfMember.permissions="+jsonArray.toString());
                entrie.putFieldEntry(keyBot,keyPermissions,jsonArray);
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            entrie.safetyPutFieldEntry(keyAudit,0);
            entrie.safetyPutFieldEntry(keyIsNSFWServer,false);
            entrie.safetyPutFieldEntry(keyBDSMRestrictions,0);

            entrie.safetyPutFieldEntry(keyCommandsUsedCount,0);
            entrie.safetyPutFieldEntry(keyGagUsedCount,0);


            entrie.saveProfile(llv2_GuildsSettings);
            entries.addEntry(entrie);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return entries;
        }
    }
    public Boolean putGuildSettings(Guild guild, lcJSONGuildProfile entry){
        String fName="[putGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(guildsSettingsJson.isEmpty()||!guildsSettingsJson.has(guild.getId())||guildsSettingsJson.isNull(guild.getId())){
                logger.info(cName + fName + ".isEmpty");
                guildsSettingsJson.put(guild.getId(),new JSONObject());
            }
            guildsSettingsJson.getJSONObject(guild.getId()).put(entry.getName(),entry.jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean putGuildSettings(Guild guild, String name, JSONObject jsonObject){
        String fName="[putGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(guildsSettingsJson.isEmpty()||!guildsSettingsJson.has(guild.getId())||guildsSettingsJson.isNull(guild.getId())){
                logger.info(cName + fName + ".isEmpty");
                guildsSettingsJson.put(guild.getId(),new JSONObject());
            }
            if(!guildsSettingsJson.getJSONObject(guild.getId()).has(name)||guildsSettingsJson.getJSONObject(guild.getId()).isNull(name)){
                logger.info(cName + fName + ".has no such name entry or name entry is null");
                guildsSettingsJson.getJSONObject(guild.getId()).put(name,new JSONObject());
            }
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            guildsSettingsJson.getJSONObject(guild.getId()).put(name,jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean putGuildSettings(lcJSONGuildProfile entry){
        String fName="[putGuildSettings]";
        logger.info(cName+fName);
        try {
            Guild guild=entry.getGuild();
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(guildsSettingsJson.isEmpty()||!guildsSettingsJson.has(guild.getId())||guildsSettingsJson.isNull(guild.getId())){
                logger.error(cName + fName + ".isEmpty");
                guildsSettingsJson.put(guild.getId(),new JSONObject());
            }
            guildsSettingsJson.getJSONObject(guild.getId()).put(entry.getName(),entry.jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<lcJSONGuildProfile> getGuildSettings(Guild guild){
        String fName="[getGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            List<lcJSONGuildProfile>list=new ArrayList<>();
            if(!hasGuildsSettings(guild)){
                logger.warn(cName + fName + ".has not, try to get");
                guildsSettingsGet4Db(guild);
            }
            if(!hasGuildsSettings(guild)){
                logger.error(cName + fName + ".still has not, failed");
                return list;
            }
            Iterator<String>keys=guildsSettingsJson.getJSONObject(guild.getId()).keys();
            while(keys.hasNext()){
                String key = keys.next();
                lcJSONGuildProfile entries=new lcJSONGuildProfile(this,key,guild,llv2_GuildsSettings);
                list.add(entries);
            }
            logger.info(cName+fName+".list.size="+list.size());
            return list;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile getGuildSettings(Guild guild, String name){
        String fName="[getGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".name:"+name);
            if(!hasGuildsSettings(guild)){
                logger.warn(cName + fName + ".has not, try to get");
                guildsSettingsGet4Db(guild);
            }
            if(!hasGuildsSettings(guild)){
                logger.error(cName + fName + ".still has not, failed");
                return null;
            }
            if(!guildsSettingsJson.getJSONObject(guild.getId()).has(name)){
                logger.error(cName + fName + ".has no such name entry");
                return null;
            }
            if(guildsSettingsJson.getJSONObject(guild.getId()).isNull(name)){
                logger.error(cName + fName + ".name entry is null");
                return null;
            }
            logger.info(cName+fName+".result="+guildsSettingsJson.getJSONObject(guild.getId()).getJSONObject(name));
            lcJSONGuildProfile entries=new lcJSONGuildProfile(this,name,guild,llv2_GuildsSettings);
            entries.jsonObject=guildsSettingsJson.getJSONObject(guild.getId()).getJSONObject(name);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getGuildSettingsAsJson(Guild guild){
        String fName="[getGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            if(!hasGuildsSettings(guild)){
                logger.warn(cName + fName + ".has not, try to get");
                guildsSettingsGet4Db(guild);
            }
            if(!hasGuildsSettings(guild)){
                logger.error(cName + fName + ".still has not, failed");
                return new JSONObject();
            }
            if(guildsSettingsJson.isNull(guild.getId())){
                logger.error(cName + fName + ".guild id is null");
                return new JSONObject();
            }
            logger.info(cName+fName+".result="+guildsSettingsJson.getJSONObject(guild.getId()));
            return guildsSettingsJson.getJSONObject(guild.getId());
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getGuildSettingsAsJson(Guild guild, String name){
        String fName="[getGuildSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".name:"+name);
            if(!hasGuildsSettings(guild)){
                logger.warn(cName + fName + ".has not, try to get");
                guildsSettingsGet4Db(guild);
            }
            if(!hasGuildsSettings(guild)){
                logger.error(cName + fName + ".still has not, failed");
                return new JSONObject();
            }
            if(guildsSettingsJson.isNull(guild.getId())){
                logger.error(cName + fName + ".guild id is null");
                return new JSONObject();
            }
            if(!guildsSettingsJson.getJSONObject(guild.getId()).has(name)){
                logger.error(cName + fName + ".has no such name entry");
                return new JSONObject();
            }
            if(guildsSettingsJson.getJSONObject(guild.getId()).isNull(name)){
                logger.error(cName + fName + ".name entry is null");
                return new JSONObject();
            }
            logger.info(cName+fName+".result="+guildsSettingsJson.getJSONObject(guild.getId()).getJSONObject(name));
            return guildsSettingsJson.getJSONObject(guild.getId()).getJSONObject(name);
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public void logGuildUsage(Guild guild,String key){
        String fName="[getSettings]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".key="+key);
            lcJSONGuildProfile guildEntry= getGuildSettings(guild,keyServer);
            if(guildEntry==null){
                logger.error(cName+fName+"no guild");
                return;
            }
            if(key.equalsIgnoreCase(keyCommandsUsedCount)){
                if(guildEntry.jsonObject.has(keyCommandsUsedCount)){
                    int count=guildEntry.jsonObject.getInt(keyCommandsUsedCount);
                    logger.info(cName+fName+".count_before="+count);
                    count++;
                    guildEntry.jsonObject.put(keyCommandsUsedCount,count);
                    logger.info(cName+fName+".count_after="+count);
                    guildEntry.setName(keyServer);
                    putGuildSettings(guild,guildEntry);
                }
            }else
            if(key.equalsIgnoreCase(keyGagUsedCount)){
                if(guildEntry.jsonObject.has(keyGagUsedCount)){
                    int count=guildEntry.jsonObject.getInt(keyGagUsedCount);
                    logger.info(cName+fName+".count_before="+count);
                    count++;
                    guildEntry.jsonObject.put(keyGagUsedCount,count);
                    logger.info(cName+fName+".count_after="+count);
                    guildEntry.setName(keyServer);
                    putGuildSettings(guild,guildEntry);
                }
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public JSONObject userProfileJson = new JSONObject();
    public lcJSONUserProfile getUserProfile(String field, User user,Guild guild,String name){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);  logger.info(cName+fName+".name:"+ name);
            String kGuild=guild.getId(); String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such user");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.info(cName + fName + ".user is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,user,guild,name);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, User user,Guild guild){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild=guild.getId(); String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such user");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.info(cName + fName + ".user is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,user,guild);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, User user,String name){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);  logger.info(cName+fName+".name:"+ name);
            String kGuild="noguild"; String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.error(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.error(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.error(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.error(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.error(cName + fName + ".has no such user");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.error(cName + fName + ".user is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,user,name);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, User user){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild="noguild"; String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.error(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.error(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.error(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.error(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.error(cName + fName + ".has no such user");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.error(cName + fName + ".user is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,user);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return  entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcJSONUserProfile getUserProfile(String field, Member member, Guild guild, String name){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".member:"+ member.getId()+"|"+ member.getUser().getName());
            logger.info(cName+fName+".field:"+ field);  logger.info(cName+fName+".name:"+ name);
            String kGuild=guild.getId(); String kUser=member.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such member");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.info(cName + fName + ".member is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,member,guild,name);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, Member member,Guild guild){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".member:"+ member.getId()+"|"+ member.getUser().getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild=guild.getId(); String kUser=member.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such member");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.info(cName + fName + ".member is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,member,guild);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, Member member,String name){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".member:"+ member.getId()+"|"+ member.getUser().getName());
            logger.info(cName+fName+".field:"+ field);  logger.info(cName+fName+".name:"+ name);
            String kGuild="noguild"; String kUser=member.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.error(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.error(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.error(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.error(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.error(cName + fName + ".has no such member");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.error(cName + fName + ".member is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,member,name);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile getUserProfile(String field, Member member){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".member:"+ member.getId()+"|"+ member.getUser().getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild="noguild"; String kUser=member.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.error(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.error(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.error(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.error(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.error(cName + fName + ".has no such member");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.error(cName + fName + ".member is null");
                return null;
            }
            lcJSONUserProfile entries=new lcJSONUserProfile(this,member);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return  entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONandSqlUserProfile getUserProfileSql(String field, Member member){
        String fName="[getUserProfileSql]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".member:"+ member.getId()+"|"+ member.getUser().getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild="noguild"; String kUser=member.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.error(cName + fName + ".isEmpty");
                return null;
            }
            if(!userProfileJson.has(kGuild)){
                logger.error(cName + fName + ".has no such guild id");
                return null;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.error(cName + fName + ".guild id is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.error(cName + fName + ".has no such field");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.error(cName + fName + ".field is null");
                return null;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.error(cName + fName + ".has no such member");
                return null;
            }
            if(userProfileJson.getJSONObject(kGuild).getJSONObject(field).isNull(kUser)){
                logger.error(cName + fName + ".member is null");
                return null;
            }
            lcJSONandSqlUserProfile entries=new lcJSONandSqlUserProfile(this,member);
            logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser));
            entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(kUser);
            return  entries;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcJSONUserProfile> getUserProfile(String field,Guild guild){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild=guild.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return new ArrayList<>();
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return new ArrayList<>();
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return new ArrayList<>();
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return new ArrayList<>();
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return new ArrayList<>();
            }
            List<lcJSONUserProfile> response=new ArrayList<>();
            Iterator<String>Keys=userProfileJson.getJSONObject(kGuild).getJSONObject(field).keys();
            while(Keys.hasNext()){
                try {
                    String id=Keys.next();
                    User user=guild.getJDA().getUserById(id);
                    lcJSONUserProfile entries=new lcJSONUserProfile(this,user,guild);
                    logger.info(cName+fName+".result="+userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(id));
                    entries.jsonObject =userProfileJson.getJSONObject(kGuild).getJSONObject(field).getJSONObject(id);
                    response.add(entries);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public JSONObject getUserProfile(String field){
        String fName="[getUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".field:"+ field);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return null;
            }
            JSONObject response=new JSONObject();
            Iterator<String>keysGuilds=userProfileJson.keys();
            try {
                while(keysGuilds.hasNext()){
                    try {
                        String idGuild=keysGuilds.next();
                        logger.info(cName + fName + ".idGuild="+idGuild);
                        if(!userProfileJson.getJSONObject(idGuild).has(field)){
                            logger.info(cName + fName + ".has no such field");
                        }
                        else if(userProfileJson.getJSONObject(idGuild).isNull(field)){
                            logger.info(cName + fName + ".field is null");

                        }else{
                           response.put(idGuild,userProfileJson.getJSONObject(idGuild).getJSONObject(field));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Boolean putUserProfile(lcJSONUserProfile profile,String field){
        String fName="[putUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".field:"+field);
            Guild guild=profile.getGuild();
            User user=profile.getUser();String kGuild="noguild";
            if(guild!=null){
                logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
                kGuild=guild.getId();
            }
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            String kUser=user.getId();
            logger.info(cName+fName+".kUser:"+ kUser);logger.info(cName+fName+".kGuild:"+kGuild);

            if(!userProfileJson.has(kGuild)){
                logger.warn(cName + fName + ".has no such guild id");
                userProfileJson.put(kGuild,new JSONObject());
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.warn(cName + fName + ".has no such field");
                userProfileJson.getJSONObject(kGuild).put(field,new JSONObject());
            }
            userProfileJson.getJSONObject(kGuild).getJSONObject(field).put(kUser,profile.jsonObject);
            return  true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean putUserProfile(lcJSONandSqlUserProfile profile,String field){
        String fName="[putUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".field:"+field);
            Guild guild=profile.gGuild;
            User user=profile.gUser;String kGuild="noguild";
            if(guild!=null){
                logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
                kGuild=guild.getId();
            }
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            String kUser=user.getId();
            logger.info(cName+fName+".kUser:"+ kUser);logger.info(cName+fName+".kGuild:"+kGuild);

            if(!userProfileJson.has(kGuild)){
                logger.warn(cName + fName + ".has no such guild id");
                userProfileJson.put(kGuild,new JSONObject());
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.warn(cName + fName + ".has no such field");
                userProfileJson.getJSONObject(kGuild).put(field,new JSONObject());
            }
            userProfileJson.getJSONObject(kGuild).getJSONObject(field).put(kUser,profile.jsonObject);
            return  true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(String field, User user,Guild guild){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild=guild.getId(); String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return true;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such user");
                return true;
            }
            JSONObject jsonObject=userProfileJson.getJSONObject(kGuild).getJSONObject(field);
            jsonObject.remove(kUser);
            userProfileJson=userProfileJson.getJSONObject(kGuild).put(field,jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(String field, User user){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {

            logger.info(cName+fName+".user:"+ user.getId()+"|"+ user.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild="noguild"; String kUser=user.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            logger.info(cName+fName+".kUser:"+ kUser);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return true;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).getJSONObject(field).has(kUser)){
                logger.info(cName + fName + ".has no such user");
                return true;
            }
            JSONObject jsonObject=userProfileJson.getJSONObject(kGuild).getJSONObject(field);
            jsonObject.remove(kUser);
            userProfileJson=userProfileJson.getJSONObject(kGuild).put(field,jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(String field, Guild guild){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(cName+fName+".field:"+ field);
            String kGuild=guild.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return true;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return true;
            }
            JSONObject jsonObject=userProfileJson.getJSONObject(kGuild);
            jsonObject.remove(field);
            userProfileJson=userProfileJson.put(kGuild,jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(String field){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {

            logger.info(cName+fName+".field:"+ field);
            String kGuild="noguild";
            logger.info(cName+fName+".kGuild:"+kGuild);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            if(!userProfileJson.getJSONObject(kGuild).has(field)){
                logger.info(cName + fName + ".has no such field");
                return true;
            }
            if(userProfileJson.getJSONObject(kGuild).isNull(field)){
                logger.info(cName + fName + ".field is null");
                return true;
            }
            JSONObject jsonObject=userProfileJson.getJSONObject(kGuild);
            jsonObject.remove(field);
            userProfileJson=userProfileJson.put(kGuild,jsonObject);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(Guild guild){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
            String kGuild=guild.getId();
            logger.info(cName+fName+".kGuild:"+kGuild);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            userProfileJson.remove(kGuild);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clearUserProfile(){
        String fName="[clearUserProfile]";
        logger.info(cName+fName);
        try {
            String kGuild="noguild";
            logger.info(cName+fName+".kGuild:"+kGuild);
            if(userProfileJson.isEmpty()){
                logger.info(cName + fName + ".isEmpty");
                return true;
            }
            if(!userProfileJson.has(kGuild)){
                logger.info(cName + fName + ".has no such guild id");
                return true;
            }
            if(userProfileJson.isNull(kGuild)){
                logger.info(cName + fName + ".guild id is null");
                return true;
            }
            userProfileJson.remove(kGuild);
            return true;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public DefaultAudioPlayerManager playerManager;
    public void createAudioManager(){
        String fName="[createAudioManage]";
        logger.info(cName+fName);
        try {
            playerManager = new DefaultAudioPlayerManager();
            if(playerManager!=null){
                logger.info(cName + fName + ".is not null");
                AudioSourceManagers.registerRemoteSources(playerManager);
                AudioSourceManagers.registerLocalSource(playerManager);
            }else{
                logger.error(cName + fName + ".is null");
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Map<String, lcGuildMusicManager> musicManagers=new TreeMap<>();
    public void createAudioPlayer(Guild guild){
        String fName="[createAudioPlayer]";
        logger.info(cName+fName);
        try {
            String guildId=guild.getId();
            logger.info(cName+fName+".guild:"+guildId+"|"+guild.getName());
            if(!musicManagers.containsKey(guildId)){
                logger.info(cName+fName+"create");
                lcGuildMusicManager musicManager = new lcGuildMusicManager(playerManager,guild,this);
                //musicManager.getSendHandler();
                musicManagers.put(guildId, musicManager);
            }
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildMusicManager getAudioPlayer(Guild guild,boolean isCreate){
        String fName="[getAudioPlayer]";
        logger.info(cName+fName);
        try {
            String guildId=guild.getId();
            logger.info(cName+fName+".guild:"+guildId+"|"+guild.getName()+", isCreate="+isCreate);
            lcGuildMusicManager musicManager;
            if(musicManagers.containsKey(guildId)){
                musicManager= musicManagers.get(guildId);
            }else{
                musicManager= new lcGuildMusicManager(playerManager,guild,this);
                musicManagers.put(guildId, musicManager);
            }
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManager;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildMusicManager getAudioPlayer(Guild guild){
        String fName="[getAudioPlayer]";
        logger.info(cName+fName);
        try {
            String guildId=guild.getId();
            logger.info(cName+fName+".guild:"+guildId+"|"+guild.getName());
            lcGuildMusicManager musicManager;
            if(!musicManagers.containsKey(guildId)){
                throw  new Exception("DOes not contain for guild");
            }
            musicManager= musicManagers.get(guildId);
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManager;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    public ServerExpress serverExpress;
    public OAUTH2HANDLING oauth2Handling;
    public TelegramBot botTelegram;
    public TelegramRoot telegramRoot;


    public TextChannel lsGetTextChannelById(String id){
        String fName="[lsGetTextChannelById].";
        try{
            logger.info(fName);
            return lsChannelHelper.lsGetTextChannelById(getJDAList(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel lsGetTextChannelById(long id){
        String fName="[getTextChannelsById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return lsChannelHelper.lsGetTextChannelById(getJDAList(),id);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public ShardManager getShardManager() {
        String fName="[getShardManager].";
        try{
            logger.info(fName);
            List<JDA>list=getJDAList();
            logger.warn(cName + fName + "jds list="+list.size());
            for(JDA jda:list){
                logger.warn(cName + fName + "jda.id="+jda.getShardInfo().getShardId());
                return jda.getShardManager();
            }
            logger.warn(cName + fName + "shardManager is null");
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public class CONFIGFILE {
        //Logger logger = Logger.getLogger(getClass());
        public CONFIGFILE(){}
        lcText2Json text2Json=new lcText2Json();
        String configFilePath="resources/json/config.json";
        public boolean initConfig(){
            String fName="[initConfig]";
            try {
                if(!readFile()){
                    logger.error(fName + ".failed to read file");
                    return false;
                }
                if(!getProperties()){
                    logger.error(fName + ".failed to get properties");
                    return false;
                }
                logger.info(fName + ".done");
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getJsonObject(){
            String fName="[getJsonObject]";
            try {
                return  text2Json.jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJsonObjectAsJsonObject(String key){
            String fName="[getJsonObjectAsJsonObject]";
            try {
                logger.info(fName+".key="+key);
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty"); return null;
                }
                if(!text2Json.jsonObject.has(key)){
                    logger.warn(fName+".has not"); return null;
                }
                if(text2Json.jsonObject.isNull(key)){
                    logger.warn(fName+".isNull"); return null;
                }
                JSONObject jsonObject=text2Json.jsonObject.getJSONObject(key);
                logger.info(fName+".jsonObject="+jsonObject.toString());
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean readFile(){
            String fName="[readFile]";
            try {
                File file1;
                InputStream fileStream=null;
                try {
                    logger.info(fName+"path="+configFilePath);
                    file1=new File(configFilePath);
                    if(file1.exists()){
                        logger.info(fName+".file1 exists");
                        fileStream = new FileInputStream(file1);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        fileStream.close();
                        logger.info(fName+".fileStream.close");
                        return false;
                    }else{
                        logger.info(fName+".loaded from file");
                        fileStream.close();
                        logger.info(fName+".fileStream.close");
                    }
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());

                try {
                    botconfig=new BOTCONFIG(text2Json.jsonObject.getJSONObject("bot"));
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                return  true;

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean getProperties(){
            String fName="[getProperties]";
            try {
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
                String key="";
                key="bot";if( text2Json.jsonObject.has(key)&&!text2Json.jsonObject.isNull(key)){
                    JSONObject jsonBot=text2Json.jsonObject.optJSONObject(key);
                    //key= llCommonKeys.BotConfig.token;if( jsonBot.has(key)&&!jsonBot.isNull(key))gBotToken=jsonBot.optString(key,"");
                    //key= llCommonKeys.BotConfig.id;if( jsonBot.has(key)&&!jsonBot.isNull(key))gBotId=jsonBot.optString(key,"");
                    logger.info(fName + ".return true");
                    return  true;
                }
                logger.info(fName + ".return false");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public class BOTCONFIG{
            private JSONObject jsonObject=new JSONObject();
            public BOTCONFIG(JSONObject jsonObject){
                String fName="BOTCONFIG@";
                try {
                    if(jsonObject==null)throw  new Exception("Json is null");
                    if(jsonObject.isEmpty())throw  new Exception("Json is empty");
                    this.jsonObject=jsonObject;
                    websoketfactory=new _WEBSOKETFACTORY(this.jsonObject.getJSONObject("websoketfactory"));
                    httpclientbuilder=new _HTTPCLIENTBUILDER(this.jsonObject.getJSONObject("httpclientbuilder"));
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                }
            }
            public int getShards(){
                String fName="getShards@";
                try {
                    int result=jsonObject.getInt("shards");
                    logger.info(fName + "result=" + result);
                    return  result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return 1;
                }
            }
            public  int getWaiterThreadpoolCount(){
                String fName="getWaiterThreadpoolCount@";
                try {
                    int result=jsonObject.getInt("waiter_threadpool");
                    logger.info(fName + "result=" + result);
                    return  result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return 1;
                }
            }
            public String getId(){
                String fName="getId@";
                try {
                    String result=jsonObject.getString("id");
                    logger.info(fName + "result=" + result);
                    return  result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getToken(){
                String fName="getToken@";
                try {
                    String result=jsonObject.getString("token");
                    logger.info(fName + "result=" + result);
                    return  result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getOwnerId(){
                String fName="getOwnerId@";
                try {
                    String result=jsonObject.getString("owner_id");
                    logger.info(fName + "result=" + result);
                    return  result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean hasMemberCachePolicy(){
                String fName="hasMemberCachePolicy@";
                try {
                    if(!jsonObject.has("member_cache_policy")){
                        return false;
                    }
                    if(jsonObject.isNull("member_cache_policy")){
                        return false;
                    }
                    return  true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public int getMemberCachePolicyAsInt(){
                String fName="getMemberCachePolicyAsInt@";
                try {
                    int result=jsonObject.getInt("member_cache_policy");
                    logger.info(fName + "result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            public MemberCachePolicy getMemberCachePolicy(){
                String fName="getMemberCachePolicy@";
                try {
                    int result=getMemberCachePolicyAsInt();
                    logger.info(fName + "result=" + result);
                    MemberCachePolicy policy=null;
                    switch (result){
                        case 0:
                            policy=MemberCachePolicy.NONE;
                            break;
                        case 1:
                            policy=MemberCachePolicy.ALL;
                            break;
                        case 2:
                            policy=MemberCachePolicy.OWNER;
                            break;
                        case 3:
                            policy=MemberCachePolicy.ONLINE;
                            break;
                        case 4:
                            policy=MemberCachePolicy.VOICE;
                            break;
                        case 5:
                            policy=MemberCachePolicy.PENDING;
                            break;
                        case 6:
                            policy=MemberCachePolicy.DEFAULT;
                            break;
                    }
                    return  policy;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public class _WEBSOKETFACTORY{
                private JSONObject jsonObject=new JSONObject();
                public _WEBSOKETFACTORY(JSONObject jsonObject){
                    String fName="_WEBSOKETFACTORY@";
                    try {
                        if(jsonObject==null)throw  new Exception("Json is null");
                        if(jsonObject.isEmpty())throw  new Exception("Json is empty");
                        this.jsonObject=jsonObject;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    }
                }
                public boolean isEnabled(){
                    String fName="isEnabled@";
                    try {
                        String key="enabled";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        boolean result=jsonObject.getBoolean(key);
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public int getConnectionTimeout(){
                    String fName="ConnectionTimeout@";
                    try {
                        String key="connection_timeout";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        int result=jsonObject.getInt(key);
                        if(result<0)throw  new Exception("Key is bellow 0!");
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                        return 10000;
                    }
                }
                public int getSocketTimeout(){
                    String fName="getSocketTimeout@";
                    try {
                        String key="socket_timeout";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        int result=jsonObject.getInt(key);
                        if(result<0)throw  new Exception("Key is bellow 0!");
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                        return 0;
                    }
                }
                public WebSocketFactory build(){
                    String fName="build@";
                    try {
                        WebSocketFactory webSocketFactory=new WebSocketFactory();
                        webSocketFactory.setConnectionTimeout(getConnectionTimeout());
                        webSocketFactory.setSocketTimeout(getSocketTimeout());
                        return  webSocketFactory;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            private  _WEBSOKETFACTORY websoketfactory=null;
            public _WEBSOKETFACTORY getWebsoketFactory(){
                String fName="getWebsoketFactory";
                try {
                    if(websoketfactory==null)websoketfactory=new _WEBSOKETFACTORY(null);
                    return websoketfactory;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public class _HTTPCLIENTBUILDER{
                private JSONObject jsonObject=new JSONObject();
                public _HTTPCLIENTBUILDER(JSONObject jsonObject){
                    String fName="_HTTPCLIENTBUILDER@";
                    try {
                        if(jsonObject==null)throw  new Exception("Json is null");
                        if(jsonObject.isEmpty())throw  new Exception("Json is empty");
                        this.jsonObject=jsonObject;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                public boolean isEnabled(){
                    String fName="isEnabled@";
                    try {
                        String key="enabled";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        boolean result=jsonObject.getBoolean(key);
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public int getMaxRequestsPerHost(){
                    String fName="getMaxRequestsPerHost@";
                    try {
                        String key="max_requests_per_host";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        int result=jsonObject.getInt(key);
                        if(result<0)throw  new Exception("Key is bellow 0!");
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+Arrays.toString(e.getStackTrace()));
                        return 25;
                    }
                }
                public int getMaxIdleConnections(){
                    String fName="getMaxIdleConnections@";
                    try {
                        String key="max_idle_connections";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        int result=jsonObject.getInt(key);
                        if(result<0)throw  new Exception("Key is bellow 0!");
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+Arrays.toString(e.getStackTrace()));
                        return 5;
                    }
                }
                public long getKeepAliveDuration(){
                    String fName="getKeepAliveDuration@";
                    try {
                        String key="keep_alive_duration";
                        if(!jsonObject.has(key))throw  new Exception("Key does not exists!");
                        if(jsonObject.isNull(key))throw  new Exception("Key is null!");
                        long result=jsonObject.getInt(key);
                        if(result<0)throw  new Exception("Key is bellow 0!");
                        logger.info(fName + "result=" + result);
                        return  result;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+Arrays.toString(e.getStackTrace()));
                        return 10L;
                    }
                }

                public OkHttpClient.Builder build(){
                    String fName="build@";
                    try {
                        Dispatcher dispatcher = new Dispatcher();
                        dispatcher.setMaxRequestsPerHost(25);
                        ConnectionPool connectionPool = new ConnectionPool(5, 10L, TimeUnit.SECONDS);
                        return  (new OkHttpClient.Builder()).connectionPool(connectionPool).dispatcher(dispatcher);
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e+Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            private  _HTTPCLIENTBUILDER httpclientbuilder=null;
            public _HTTPCLIENTBUILDER getHttpClientBuilder(){
                String fName="getHttpClientBuilder";
                try {
                    if(httpclientbuilder==null)httpclientbuilder=new _HTTPCLIENTBUILDER(null);
                    return httpclientbuilder;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
        }
        private  BOTCONFIG botconfig;
        public BOTCONFIG getBot(){
            String fName="getBot@";
            try {
                if(botconfig==null)botconfig=new BOTCONFIG(null);
               return  botconfig;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public CONFIGFILE configfile=new CONFIGFILE();

    public _SELF SELF=new _SELF();
    private class _SELF{
        public SelfUser getSelf(){
            String fName="[getSelf]";
            logger.info(cName+fName);
            try {
                JDA jda=null;
                int i=0;
                while(jda!=null&&i<1000){
                    jda=getJDA(i);
                    if(jda!=null)logger.info(cName+fName+".i="+ i);
                    else i++;
                }
                return jda.getSelfUser();
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong(){
            String fName="[getIdAsLong]";
            logger.info(cName+fName);
            try {
                SelfUser selfUser=getSelf();
                long value=selfUser.getIdLong();
                logger.info(cName + fName + ".value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getIdAsString(){
            String fName="[getIdAsString]";
            logger.info(cName+fName);
            try {
                SelfUser selfUser=getSelf();
                String value=selfUser.getId();
                logger.info(cName + fName + ".value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public User getUser(){
            String fName="[getUser]";
            logger.info(cName+fName);
            try {
                SelfUser selfUser=getSelf();
                User user=selfUser.getJDA().getUserById(selfUser.getIdLong());
                return  user;
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<Member> getMembers(){
            String fName="[getMembers]";
            logger.info(cName+fName);
            try {
                List<Member>list=new ArrayList<>();
                List<Guild>guilds=getGuildList();
                for(Guild guild:guilds){
                    try {
                        Member member=guild.getSelfMember();
                        if(member!=null){
                            list.add(member);
                        }
                    } catch (Exception e) {
                        logger.error(cName + fName + ".exception:" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(cName+fName+".list.size="+list.size());
                return  list;
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Member getMember(Guild guild){
            String fName="[getMember]";
            logger.info(cName+fName);
            try {
                if(guild==null){
                    throw new Exception("No guild selected");
                }
                logger.info(cName+fName+".guild="+guild.getId());
                return guild.getSelfMember();
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Member getMember(long guild){
            String fName="[getMember]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+".input="+guild);
                return getMember(getGuild(guild));
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Member getMember(String guild){
            String fName="[getMember]";
            logger.info(cName+fName);
            try {
                logger.info(cName+fName+".input="+guild);
                return getMember(getGuild(guild));
            } catch (Exception e) {
                logger.error(cName + fName + ".exception:" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public String getPrefix(){
        String fName="[getPrefix]";
        logger.info(cName+fName);
        try {
            return llPrefixStr;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isPartOfTeam(Member member){
        String fName="[isPartOfTeam]";
        logger.info(cName+fName);
        try {
            return isPartOfTeam(member.getUser());
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPartOfTeam(User user){
        String fName="[isPartOfTeam]";
        logger.info(cName+fName);
        try {
            List<TeamMember>teamMembers=getShardManager().retrieveApplicationInfo().complete().getTeam().getMembers();
            for(TeamMember teamMember:teamMembers){
                if(teamMember.getUser().getIdLong()==user.getIdLong()){
                    return  true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception:" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public GuildNotificationReader generalGuildNotification;

}
