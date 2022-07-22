package util.inhouse;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsJson4Entity;
import models.ls.lsMessageHelper;
import models.ls.lsQuickMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.utils.cache.MemberCacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import net.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityCache extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llCommonKeys {
    String cName="[utilityCache]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="utilitycache";
    public utilityCache(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Utility-Cache";
        this.help = "guild stuffs";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category= llCommandCategory_UtilityInHouse;
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
        private TextChannel gTextChannel; private Message gMessage;String gTitle="Cache Utility";

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
            logger.info(cName + fName);
            boolean isInvalidCommand = true;
            try {
                if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                    logger.info(cName + fName + ".denied");
                    return;
                }
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items.length >= 2 && items[0].equalsIgnoreCase("help")) {
                        help(items[1]);
                        isInvalidCommand = false;
                    } else if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else if (items[0].equalsIgnoreCase("get")) {
                       if(items.length>=2){
                           if(items[1].equalsIgnoreCase("size")){
                                 getGuildCacheSize(gGuild);
                                 isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("categories")){
                               getCategoryCache(gGuild);
                               isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("text")){
                               getTextChannelsCache(gGuild);
                               isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("voice")){
                               getVoiceChannelsCache(gGuild);
                               isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("members")){
                               getMembersCache(gGuild);
                               isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("roles")){
                               getRolesCache(gGuild);
                               isInvalidCommand = false;
                           }
                           else if(items[1].equalsIgnoreCase("emotes")){
                               getEmotesCache(gGuild);
                               isInvalidCommand = false;
                           }
                       }
                    }else if (items[0].equalsIgnoreCase("getall")) {
                        if(items.length>=2){
                            if(items[1].equalsIgnoreCase("size")){
                                getAllGuildsCacheSize();
                                isInvalidCommand = false;
                            }
                        }
                    }else if (items[0].equalsIgnoreCase("prunemembers")) {
                        pruneMembersCache(gGuild);
                        isInvalidCommand = false;
                    }else if (items[0].equalsIgnoreCase("unloadmembers")) {
                        unloadMembersCache(gGuild);
                        isInvalidCommand = false;
                    }

                }
                logger.info(cName + fName + ".deleting op message");
                llMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }

        }
        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = " ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.addField("Get for current guild",quickSummonWithSpace+"get size|categories|text|voice|roles|emotes|members",false);
            if(lsMemberIsBotOwner(gMember))embed.addField("Get all guilds",quickSummonWithSpace+"get size",false);
            embed.setTitle(gTitle);embed.setColor(llColorBlue1);
            if(llSendMessageStatus(gUser,embed)){
                llSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                llSendMessageStatus(gTextChannel,embed);
            }
        }
        private void getCategoryCache(Guild guild) {
            String fName = "getCategoryCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            SortedSnowflakeCacheView<net.dv8tion.jda.api.entities.Category> sortedsnowflakecacheview=guild.getCategoryCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<net.dv8tion.jda.api.entities.Category>list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(net.dv8tion.jda.api.entities.Category entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetCategoryJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyCategories,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_Categories", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getTextChannelsCache(Guild guild) {
            String fName = "getTextChannelsCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            SortedSnowflakeCacheView<TextChannel> sortedsnowflakecacheview=guild.getTextChannelCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<TextChannel>list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(TextChannel entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetTextChannelJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyTextChannels,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_TextChannels", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getVoiceChannelsCache(Guild guild) {
            String fName = "getVoiceChannelsCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            SortedSnowflakeCacheView<VoiceChannel> sortedsnowflakecacheview=guild.getVoiceChannelCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<VoiceChannel>list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(VoiceChannel entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetVoiceChannelJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyVoiceChannels,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_VoiceChannels", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getRolesCache(Guild guild) {
            String fName = "getRolesCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            SortedSnowflakeCacheView<Role> sortedsnowflakecacheview=guild.getRoleCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<Role>list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(Role entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetRoleJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyRoles,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_Roles", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getEmotesCache(Guild guild) {
            String fName = "getEmotesCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            SnowflakeCacheView<Emote> sortedsnowflakecacheview=guild.getEmoteCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<Emote> list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(Emote entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetEmoteJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyEmotes,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_Emotes", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getMembersCache(Guild guild) {
            String fName = "getMembersCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            MemberCacheView sortedsnowflakecacheview=guild.getMemberCache();
            logger.info(cName + fName + ".sortedsnowflakecacheview.size=" + sortedsnowflakecacheview.size());
            List<Member> list=sortedsnowflakecacheview.asList();
            logger.info(cName + fName + ".list.size=" + list.size());
            JSONObject object=new JSONObject();
            JSONArray array=new JSONArray();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                jsonGuild.put(llCommonKeys.keySize, sortedsnowflakecacheview.size());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(Member entity : list){
                try {
                    JSONObject jsonEntity= lsJson4Entity.llGetMemberJsonEntry(entity);
                    if(jsonEntity!=null)array.put(jsonEntity);
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            object.put(llCommonKeys.keyMembers,array);
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_Members", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getGuildCacheSize(Guild guild) {
            String fName = "getGuildCacheSize";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            JSONObject object=new JSONObject();
            try {
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.keyName,guild.getName());
                object.put(llCommonKeys.keyGuild,jsonGuild);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                SortedSnowflakeCacheView<net.dv8tion.jda.api.entities.Category> sortedsnowflakecacheview0=guild.getCategoryCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview0.size=" + sortedsnowflakecacheview0.size());
                List<net.dv8tion.jda.api.entities.Category>list0=sortedsnowflakecacheview0.asList();
                logger.info(cName + fName + ".list0.size=" + list0.size());
                JSONObject jsonEntities0=new JSONObject();
                jsonEntities0.put(llCommonKeys.keySize, sortedsnowflakecacheview0.size());
                object.put(llCommonKeys.keyCategories,jsonEntities0);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                SortedSnowflakeCacheView<TextChannel> sortedsnowflakecacheview1=guild.getTextChannelCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview1.size=" + sortedsnowflakecacheview1.size());
                List<TextChannel> list1=sortedsnowflakecacheview1.asList();
                logger.info(cName + fName + ".list1.size=" + list1.size());
                JSONObject jsonEntities1=new JSONObject();
                jsonEntities1.put(llCommonKeys.keySize, sortedsnowflakecacheview1.size());
                object.put(llCommonKeys.keyTextChannels,jsonEntities1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                SortedSnowflakeCacheView<VoiceChannel> sortedsnowflakecacheview2=guild.getVoiceChannelCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview2.size=" + sortedsnowflakecacheview2.size());
                List<VoiceChannel> list2=sortedsnowflakecacheview2.asList();
                logger.info(cName + fName + ".list2.size=" + list2.size());
                JSONObject jsonEntities2=new JSONObject();
                jsonEntities2.put(llCommonKeys.keySize, sortedsnowflakecacheview2.size());
                object.put(llCommonKeys.keyVoiceChannels,jsonEntities2);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                SortedSnowflakeCacheView<Role> sortedsnowflakecacheview3=guild.getRoleCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview3.size=" + sortedsnowflakecacheview3.size());
                List<Role> list3=sortedsnowflakecacheview3.asList();
                logger.info(cName + fName + ".list3.size=" + list3.size());
                JSONObject jsonEntities3=new JSONObject();
                jsonEntities3.put(llCommonKeys.keySize, sortedsnowflakecacheview3.size());
                object.put(llCommonKeys.keyRoles,jsonEntities3);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                SnowflakeCacheView<Emote> sortedsnowflakecacheview4=guild.getEmoteCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview4.size=" + sortedsnowflakecacheview4.size());
                List<Emote> list4=sortedsnowflakecacheview4.asList();
                logger.info(cName + fName + ".list4.size=" + list4.size());
                JSONObject jsonEntities4=new JSONObject();
                jsonEntities4.put(llCommonKeys.keySize, sortedsnowflakecacheview4.size());
                object.put(llCommonKeys.keyEmotes,jsonEntities4);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                MemberCacheView sortedsnowflakecacheview5=guild.getMemberCache();
                logger.info(cName + fName + ".sortedsnowflakecacheview5.size=" + sortedsnowflakecacheview5.size());
                List<Member> list5=sortedsnowflakecacheview5.asList();
                logger.info(cName + fName + ".list5.size=" + list5.size());
                JSONObject jsonEntities5=new JSONObject();
                jsonEntities5.put(llCommonKeys.keySize, sortedsnowflakecacheview5.size());
                object.put(llCommonKeys.keyMembers,jsonEntities5);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_GuildSize", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }
        private void getAllGuildsCacheSize() {
            String fName = "getGuildCacheSize";
            if (!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.", llColors.llColorBlue1);
            List<Guild>listGuilds=gGlobal.getGuildList();
            logger.info(cName + fName + ".listGuilds.size=" +listGuilds.size());
            JSONArray array=new JSONArray();
            for(Guild guild:listGuilds){
                try {
                    logger.info(cName + fName + ".guild:" + guild.getId());
                    JSONObject object=new JSONObject();
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                        jsonGuild.put(llCommonKeys.keyName,guild.getName());
                        object.put(llCommonKeys.keyGuild,jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        SortedSnowflakeCacheView<net.dv8tion.jda.api.entities.Category> sortedsnowflakecacheview0=guild.getCategoryCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview0.size=" + sortedsnowflakecacheview0.size());
                        List<net.dv8tion.jda.api.entities.Category>list0=sortedsnowflakecacheview0.asList();
                        logger.info(cName + fName + ".list0.size=" + list0.size());
                        JSONObject jsonEntities0=new JSONObject();
                        jsonEntities0.put(llCommonKeys.keySize, sortedsnowflakecacheview0.size());
                        object.put(llCommonKeys.keyCategories,jsonEntities0);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        SortedSnowflakeCacheView<TextChannel> sortedsnowflakecacheview1=guild.getTextChannelCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview1.size=" + sortedsnowflakecacheview1.size());
                        List<TextChannel> list1=sortedsnowflakecacheview1.asList();
                        logger.info(cName + fName + ".list1.size=" + list1.size());
                        JSONObject jsonEntities1=new JSONObject();
                        jsonEntities1.put(llCommonKeys.keySize, sortedsnowflakecacheview1.size());
                        object.put(llCommonKeys.keyTextChannels,jsonEntities1);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        SortedSnowflakeCacheView<VoiceChannel> sortedsnowflakecacheview2=guild.getVoiceChannelCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview2.size=" + sortedsnowflakecacheview2.size());
                        List<VoiceChannel> list2=sortedsnowflakecacheview2.asList();
                        logger.info(cName + fName + ".list2.size=" + list2.size());
                        JSONObject jsonEntities2=new JSONObject();
                        jsonEntities2.put(llCommonKeys.keySize, sortedsnowflakecacheview2.size());
                        object.put(llCommonKeys.keyVoiceChannels,jsonEntities2);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        SortedSnowflakeCacheView<Role> sortedsnowflakecacheview3=guild.getRoleCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview3.size=" + sortedsnowflakecacheview3.size());
                        List<Role> list3=sortedsnowflakecacheview3.asList();
                        logger.info(cName + fName + ".list3.size=" + list3.size());
                        JSONObject jsonEntities3=new JSONObject();
                        jsonEntities3.put(llCommonKeys.keySize, sortedsnowflakecacheview3.size());
                        object.put(llCommonKeys.keyRoles,jsonEntities3);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        SnowflakeCacheView<Emote> sortedsnowflakecacheview4=guild.getEmoteCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview4.size=" + sortedsnowflakecacheview4.size());
                        List<Emote> list4=sortedsnowflakecacheview4.asList();
                        logger.info(cName + fName + ".list4.size=" + list4.size());
                        JSONObject jsonEntities4=new JSONObject();
                        jsonEntities4.put(llCommonKeys.keySize, sortedsnowflakecacheview4.size());
                        object.put(llCommonKeys.keyEmotes,jsonEntities4);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        MemberCacheView sortedsnowflakecacheview5=guild.getMemberCache();
                        logger.info(cName + fName + ".sortedsnowflakecacheview5.size=" + sortedsnowflakecacheview5.size());
                        List<Member> list5=sortedsnowflakecacheview5.asList();
                        logger.info(cName + fName + ".list5.size=" + list5.size());
                        JSONObject jsonEntities5=new JSONObject();
                        jsonEntities5.put(llCommonKeys.keySize, sortedsnowflakecacheview5.size());
                        object.put(llCommonKeys.keyMembers,jsonEntities5);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    array.put(object);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
            OffsetDateTime now=OffsetDateTime.now();
            String fileName="Cache_GuildsSize", fileExtension=".json";
            fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
            gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
            targetStream=null;messageProcessing.delete().queue();
        }

        private void pruneMembersCache(Guild guild) {
            String fName = "pruneMembersCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            guild.pruneMemberCache();
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Pruned Members cache!",llColors.llColorGreen2);
        }
        private void unloadMembersCache(Guild guild) {
            String fName = "unloadMembersCache";
            logger.info(cName + fName + ".guild:" + guild.getId());
            if (!lsMemberIsBotOwner(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members=guild.getMembers();
            for(Member member:members){
                try {
                    logger.info(cName + fName + ".member:"+member.getUser().getName()+"(" + member.getId()+"), unloaded="+guild.unloadMember(member.getIdLong()));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Unloaded Members cache!",llColors.llColorGreen2);
        }

    }

}
