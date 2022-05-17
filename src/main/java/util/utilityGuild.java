package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.lcTempZipFile;
import models.lcGlobalHelper;
import models.ll.*;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityGuild extends Command implements  llGlobalHelper,  llMemberHelper, llCommonKeys {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="server";
    public utilityGuild(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Server-Utility";
        this.help = "Utility commands for the server(guild)";
        this.aliases = new String[]{commandPrefix,"guild","utilityguild","guildutility","utilityserver","serverutility"};
        this.guildOnly = true;
        this.category= llCommandCategory_Utility;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable,llMessageHelper {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage; String gTitle="Guild Utility";

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
                prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    menuMain(1);
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items.length>=2&&items[0].equalsIgnoreCase("help")) {
                        help(items[1]);
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("clearmember")) {
                        removeNewmemberRoleForThoseWhoHaveMemberRole();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("dmindex")) {
                        if (items.length < 2) {
                            dmIndex(0);
                        } else {
                            dmIndex(getMode(items[1]));
                        }
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("indexbuild")) {
                        if (items.length < 2) {
                            indexBuild(0);
                        } else {
                            indexBuild(getMode(items[1]));
                        }
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("reloadsettings")) {
                        reloadCurrentGuildSettings();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("countsettings")) {
                        count4CurrentGuildSettings();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("info")||items[0].equalsIgnoreCase("serverinfo") || items[0].equalsIgnoreCase("guildinfo")) {
                        getGuildInfo();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("boostinfo")) {
                        getBoostInfo();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("getsplash")) {
                        getGuildSplash();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("getbanner")) {
                        getGuildBanner();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("setsplash")) {
                        setGuildSplash();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("getnsfw_db")) {
                        getGuildNSFWFlag();
                        isInvalidCommand = false;
                    }
                    else if (items.length>=2&&items[0].equalsIgnoreCase("setnsfw_db")&&(items[1].equalsIgnoreCase("1")||items[1].equalsIgnoreCase("true"))) {
                        setGuildNSFWFlag(true);isInvalidCommand=false;
                    }
                    else if (items.length>=2&&items[0].equalsIgnoreCase("setnsfw_db")&&(items[1].equalsIgnoreCase("0")||items[1].equalsIgnoreCase("false"))) {
                        setGuildNSFWFlag(false);isInvalidCommand=false;
                    }
                    else if (items[0].equalsIgnoreCase("setbanner")) {
                        setGuildBanner();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("seticon")) {
                        setGuildIcon();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("countuserprofilecache")) {
                        countUserProfilesCache4Current();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("clearuserprofilecache")) {
                        clearUserProfilesCache4CurrentGuild();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("prune")) {
                        if(items.length>=2){
                            if ((items[1].equalsIgnoreCase("newmember")||items[1].equalsIgnoreCase("nm"))&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)) {
                                if(items.length>=3){
                                    if (items[2].equalsIgnoreCase("list")) {
                                        getOneRoleNewMembers4Prune();
                                        isInvalidCommand = false;
                                    }
                                    if (items[2].equalsIgnoreCase("doall")) {
                                        pruneOneRoleNewMembers();
                                        isInvalidCommand = false;
                                    }
                                    if (items[2].equalsIgnoreCase("is")) {
                                        statusOneRoleNewMembersByMentioning();
                                        isInvalidCommand = false;
                                    }
                                    if (items[2].equalsIgnoreCase("domention")) {
                                        pruneOneRoleNewMembersByMentioning();
                                        isInvalidCommand = false;
                                    }
                                    if(items[2].equalsIgnoreCase("message")){
                                        messageAfterPruned(null);  isInvalidCommand = false;
                                    }
                                }
                            }
                        }
                    }
                    else if (items[0].equalsIgnoreCase("getdescription")) {
                        getGuildDescription();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("setdescription")) {
                        setGuildDescription();
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("serverread")) {
                        getGuildRegistry();
                        isInvalidCommand = false;
                    }
                    else if (items.length>=3&&items[0].equalsIgnoreCase("serverwrite_boolean")) {
                        setGuildRegistry_BooleanValue(items[1],items[2]);
                        isInvalidCommand = false;
                    }
                    else if (items.length>=3&&items[0].equalsIgnoreCase("serverwrite_int")) {
                        setGuildRegistry_IntValue(items[1],items[2]);
                        isInvalidCommand = false;
                    }
                    else if (items.length>=3&&items[0].equalsIgnoreCase("serverwrite_world")) {
                        setGuildRegistry_WorldValue(items[1],items[2]);
                        isInvalidCommand = false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("jsonaudit")&&items[1].equalsIgnoreCase("multiple")){
                        sendAuditZipFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonaudit")){
                        sendAuditFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("jsonbans")&&items[1].equalsIgnoreCase("multiple")){
                        sendBanJsonZipFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonbans")){
                        sendBanJsonFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("jsonchannels")&&items[1].equalsIgnoreCase("multiple")){
                        sendChannelsJsonZipFile();isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("jsonroles")&&items[1].equalsIgnoreCase("multiple")){
                        sendRolesJsonZipFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("jsonmembers")&&items[1].equalsIgnoreCase("multiple")){
                        sendMembersJsonZipFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonchannels")){
                        sendChannelsJsonFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonroles")){
                        sendRolesJsonFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsoninvites")){
                        sendInviteJsonFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonmembers")){
                        sendMembersJsonFile(gGuild);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("jsonserver")){
                        sendServerJsonFile();isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("listmembers")){
                        sendMembersListFile(items[1]);isInvalidCommand=false;
                    }
                    else if(items.length>=2&&items[0].equalsIgnoreCase("listbans")){
                        sendBanListFile(items[1]);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("listmembers")){
                        sendMembersListFile("");isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("listbans")){
                        sendBanListFile("");isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("downloadserverimage")){
                        downloadServerImages("");isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("downloadmembersavatar")&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)){
                        downloadMemberAvatars();isInvalidCommand=false;
                    }
                }
                logger.info(cName + fName + ".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        String prefix2="";
        String commandKeyInfo="info", commandKeyUser="user", commandKeyServer="server", commandKeyPrune="prune",commandKeyDownloads="downloads";
        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);

            String quickSummonWithSpace = prefix2 + commandPrefix+" ";
            String desc = " ";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            if(command.equalsIgnoreCase(commandKeyDownloads)){
                embed.addField("Server", "`" + quickSummonWithSpace + "downloadserverimage`", false);
                if(lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)){embed.addField("Avatars", "`" + quickSummonWithSpace + "downloadmembersavatar`", false);}
            }
            else if(command.equalsIgnoreCase(commandKeyInfo)){
                embed.addField("Index","\n`" + quickSummonWithSpace + "dmindex <modes>`\n`" + quickSummonWithSpace + "indexbuild <modes>`\nmodes: alldisplayboice, justvoice, channels, textchannels",false);
                embed.addField("Server","\n`" + quickSummonWithSpace + "serverinfo`  `" + quickSummonWithSpace + "boostinfo`  `" + quickSummonWithSpace + "getsplash`  `" + quickSummonWithSpace + "getbanner`",false);
                if (llMemberIsManager(gMember)){
                    desc="\nCommand:`" + quickSummonWithSpace + "jsonaudit|jsonbans|jsonchannels|jsonroles|jsonmembers|jsoninvites|jsonserver <attr>` ";
                    desc+="\nAttribute `multiple` allows storing each channel|ban|role|member entity separate in a zip file.";
                    embed.addField("Generate Json Files",desc,false);
                }
                if (llMemberIsManager(gMember)){
                    desc="\nCommand:`" + quickSummonWithSpace + "listbans|listmembers <attr>` ";
                    desc+="\nAttribute `name` and `id` filters out what to be stored in the list.";
                    embed.addField("Generate List Files",desc,false);
                }
            }
            else if(command.equalsIgnoreCase(commandKeyUser)){
                if(llMemberIsStaff(gMember)&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild))embed.addField("User Fix","\n`" + quickSummonWithSpace + "clearmember` fixes user who have Member and New member role",false);
                if(llMemberIsStaff(gMember))embed.addField("Users profiles","\n`" + quickSummonWithSpace + "clearuserprofilecache`  `"+ quickSummonWithSpace + "countuserprofilecache`  ",false);
            }
            else if(command.equalsIgnoreCase(commandKeyServer)){
                if(llMemberIsManager(gMember))embed.addField("Server Set","\n`" + quickSummonWithSpace + "seticon` `" + quickSummonWithSpace + "setsplash`  `" + quickSummonWithSpace + "setbanner`",false);
                if(llMemberIsStaff(gMember))embed.addField("Servers profiles", "\n`" + quickSummonWithSpace + "reloadsettings` `"+ quickSummonWithSpace + "countsettings`  `"+quickSummonWithSpace+"clearuserprofilecache",false);
            }
            else if(command.equalsIgnoreCase(commandKeyPrune)) {
                if (llMemberIsStaff(gMember)&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)) {
                    desc = "\n`" + quickSummonWithSpace + "prune nm is` checks if mentioned members is valid for pruning";
                    if (llMemberIsModerator(gMember))
                        desc += "\n`" + quickSummonWithSpace + "prune nm domention` prunes only mentioned new members that only have that role and joined 14 days ago";
                    desc += "\n`" + quickSummonWithSpace + "prune nm list` generates a list of new members that only have that role and joined 14 days ago";
                    if (llMemberIsAdministrator(gMember))
                        desc += "\n`" + quickSummonWithSpace + "prune nm doall` prunes all new members that only have that role and joined 14 days ago";
                    embed.addField("Inactive member prune", desc, false);
                    desc = "";
                }
            }else{
                quickSummonWithSpace+="help ";
                embed.addField("Info","\n`" + quickSummonWithSpace +commandKeyInfo+"`",false);
                embed.addField("Setting Server","\n`" + quickSummonWithSpace +commandKeyServer+"`",false);
                embed.addField("Setting User(s)","\n`" + quickSummonWithSpace +commandKeyUser+"`",false);
                embed.addField("Prune User(s)","\n`" + quickSummonWithSpace +commandKeyPrune+"`",false);
                embed.addField("Downloads","\n`" + quickSummonWithSpace +commandKeyDownloads+"`",false);
            }
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private int getMode(String str) {
            String fName = "[getMode]";
            logger.info(cName + fName + ".str=" + str);
            if (str.equalsIgnoreCase("3") || str.equalsIgnoreCase("alldisplayboice")) return 3;
            else if (str.equalsIgnoreCase("2") || str.equalsIgnoreCase("justvoice")) return 2;
            else if (str.equalsIgnoreCase("1") || str.equalsIgnoreCase("channels")) return 1;
            else if (str.equalsIgnoreCase("0") || str.equalsIgnoreCase("textchannels")) return 0;
            else return 0;
        }
        private void indexBuild( int mode) {
            String fName = "[indexBuild]";
            logger.info(cName + fName);
            logger.info(cName + fName + "mode=" + mode);
            List<net.dv8tion.jda.api.entities.Category> categories = gGuild.getCategories();
            for (net.dv8tion.jda.api.entities.Category category : categories) {
                logger.info(cName + fName + "category=" + category.getId() + "|" + category.getName());
                String caName = category.getName().toLowerCase();
                if (caName.contains("foyer") || caName.contains("staff") || caName.contains("log") || caName.contains("modmail") || caName.contains("admin") || caName.contains("jail") || caName.contains("\uD83D\uDD12")) {
                    logger.info(cName + fName + "contains name exception");
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(8388863);
                    embed.setTitle(category.getName());
                    StringBuilder desc = new StringBuilder();
                    switch (mode) {
                        case 3:
                            List<GuildChannel> channelsG2 = category.getChannels();
                            for (GuildChannel channel : channelsG2) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                if (channel.getType() == ChannelType.TEXT) {
                                    desc.append("\n").append(Objects.requireNonNull(gGuild.getTextChannelById(channel.getId())).getAsMention());
                                } else {
                                    desc.append("\n(voice) ").append(channel.getName());
                                }
                            }
                            break;
                        case 2:
                            List<VoiceChannel> channelsV = category.getVoiceChannels();
                            for (GuildChannel channel : channelsV) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getName());
                            }
                            break;
                        case 1:
                            List<GuildChannel> channelsG = category.getChannels();
                            for (GuildChannel channel : channelsG) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getName());
                            }
                            break;
                        default:
                            List<TextChannel> channelsT = category.getTextChannels();
                            for (TextChannel channel : channelsT) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getAsMention());
                            }
                            break;
                    }
                    embed.setDescription(desc.toString());
                    logger.info(cName + fName + "post to:" + gTextChannel.getId() + "|" +gTextChannel.getName());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
            }
        }
        private void dmIndex(int mode) {
            String fName = "[dmIndex]";
            logger.info(cName + fName);
            logger.info(cName + fName + "mode=" + mode);
            List<net.dv8tion.jda.api.entities.Category> categories = gGuild.getCategories();
            lsMessageHelper.lsSendMessage(gUser, "Start sending index...");
            for (net.dv8tion.jda.api.entities.Category category : categories) {
                logger.info(cName + fName + "category=" + category.getId() + "|" + category.getName());
                String caName = category.getName().toLowerCase();
                if (caName.contains("foyer") || caName.contains("staff") || caName.contains("log") || caName.contains("modmail") || caName.contains("admin") || caName.contains("jail") || caName.contains("\uD83D\uDD12")) {
                    logger.info(cName + fName + "contains name exception");
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(8388863);
                    embed.setTitle(category.getName());
                    StringBuilder desc = new StringBuilder();
                    switch (mode) {
                        case 3:
                            List<GuildChannel> channelsG2 = category.getChannels();
                            for (GuildChannel channel : channelsG2) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                if (channel.getType() == ChannelType.TEXT) {
                                    desc.append("\n").append(Objects.requireNonNull(gGuild.getTextChannelById(channel.getId())).getAsMention());
                                } else {
                                    desc.append("\n(voice) ").append(channel.getName());
                                }
                            }
                            break;
                        case 2:
                            List<VoiceChannel> channelsV = category.getVoiceChannels();
                            for (GuildChannel channel : channelsV) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getName());
                            }
                            break;
                        case 1:
                            List<GuildChannel> channelsG = category.getChannels();
                            for (GuildChannel channel : channelsG) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getName());
                            }
                            break;
                        default:
                            List<TextChannel> channelsT = category.getTextChannels();
                            for (TextChannel channel : channelsT) {
                                logger.info(cName + fName + "channel=" + channel.getId() + "|" + channel.getName());
                                desc.append("\n").append(channel.getAsMention());
                            }
                            break;
                    }
                    embed.setDescription(desc.toString());
                    lsMessageHelper.lsSendMessage(gUser, embed);
                }
            }
            lsMessageHelper.lsSendMessage(gUser, "Indexing end.");
        }
        private void removeNewmemberRoleForThoseWhoHaveMemberRole() {
            String fName = "[removeNewmemberRoleForThoseWhoHaveMemberRole]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_KICK(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Require kick permission from author.", llColors.llColorRed);
                return;
            }
            Role roleMember;
            Role roleNewMember;
            if (gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)) {
                roleMember = gGuild.getRoleById(lsCustomGuilds.lsGuildBdsmMemberKey);
                roleNewMember =gGuild.getRoleById(lsCustomGuilds.lsGuildBdsmNewMemberKey);
            } else if (gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)) {
                roleMember = gGuild.getRoleById(lsCustomGuilds.lsGuildChastityMemberKey);
                roleNewMember = gGuild.getRoleById(lsCustomGuilds.lsGuildChastityNewMemberKey);
            } else if (gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAsylum)) {
                roleMember = gGuild.getRoleById(lsCustomGuilds.lsGuildAsylumMemberKey);
                roleNewMember = gGuild.getRoleById(lsCustomGuilds.lsGuildAsylumNewMemberKey);
            } else if (gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyPrison)) {
                roleMember = gGuild.getRoleById(lsCustomGuilds.lsGuildPrisonMemberKey);
                roleNewMember = gGuild.getRoleById(lsCustomGuilds.lsGuildPrisonNewMemberKey);
            } else if (gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)) {
                roleMember = gGuild.getRoleById(lsCustomGuilds.lsGuildSnuffMemberKey);
                roleNewMember = gGuild.getRoleById(lsCustomGuilds.lsGuildSnuffNewMemberKey);
            } else {
                logger.warn(cName + fName + ".invalid guild!");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Invalid guild!", llColors.llColorRed);
                return;
            }
            assert roleMember != null;
            logger.info(cName + fName + ".roleMember:" + roleMember.getId() + "|" + roleMember.getName());
            List<Member> members = gGuild.getMembersWithRoles(roleMember);
            logger.info(cName + fName + ".members with role:" + members.size());
            int i = 0;
            int clean = 0;
            for (Member member : members) {
                List<Role> roles = member.getRoles();
                boolean hasNewMember = false;
                for (Role role : roles) {
                    if (role == roleNewMember) {
                        hasNewMember = true;
                        break;
                    }
                }
                logger.info(cName + fName + ".member[" + i + "]:" + member.getId() + "|" + member.getEffectiveName() + ", roles=" + roles.size() + ", hasNewMember=" + hasNewMember);
                if (hasNewMember) {
                    clean++;
                    logger.info(cName + fName + ".member[" + i + "]:" + member.getId() + "|" + member.getEffectiveName() + ", remove role");
                    assert roleNewMember != null;
                    gGuild.removeRoleFromMember(member, roleNewMember).queue();
                }
                i++;
            }
            assert roleNewMember != null;
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Cleanup new member for:" + gGuild.getName() + "\nCount:" + clean + "/" + members.size() + "\nRoles:" + roleNewMember.getName() + "," + roleMember.getName(), llColors.llColorBlue1);
        }
        private void reloadCurrentGuildSettings() {
            String fName = "[reloadCurrentGuildSettings]";
            logger.info(cName + fName);

            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                return;
            }
            gGlobal.guildsSettingsGet4Db(gGuild);
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Settings reload for guild " + gGuild.getName() + ".", llColors.llColorBlue1);
        }
        private void count4CurrentGuildSettings() {
            String fName = "[count4CurrentGuildSettings]";
            logger.info(cName + fName);

            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                return;
            }
            int count=0;
            if(gGlobal.hasGuildsSettings(gGuild)){
                JSONObject jsonObject= gGlobal.getGuildSettingsAsJson(gGuild);
                Iterator<String> iEntries=jsonObject.keys();
                while (iEntries.hasNext()){
                    String entrie=iEntries.next();
                    if(jsonObject.has(entrie)&&!jsonObject.isNull(entrie)){
                        count++;
                    }
                }
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Current guild setting entries count:"+count, llColors.llColorBlue1);
        }
        private void clearUserProfilesCache4CurrentGuild() {
            String fName = "[clearUserProfilesCache4CurrentGuild]";
            logger.info(cName + fName);

            if (!llMemberIsStaff(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                return;
            }
            if(!gGlobal.userProfileJson.has(gGuild.getId())){
                logger.error(cName + fName + ".has no such guild id"); lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Guild has no user settings cached", llColors.llColorBlue1);
                return;
            }
            gGlobal.userProfileJson.put(gGuild.getId(),new JSONObject());
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "\"Cleared user profile cache for this guild", llColors.llColorBlue1);
        }
        private void countUserProfilesCache4Current() {
            String fName = "[countUserProfilesCache4Current]";
            logger.info(cName + fName);

            if (!llMemberIsStaff(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                return;
            }
            int count=0;String guild=gGuild.getId();
             if(gGlobal.hasGuildsSettings(gGuild)){
                 JSONObject jsonObject=gGlobal.getGuildSettingsAsJson(gGuild);
                 Iterator<String> iFields= jsonObject.keys();
                 while (iFields.hasNext()){
                     String field=iFields.next();
                     if(jsonObject.has(field)&&!jsonObject.isNull(field)){
                         Iterator<String> iUser= jsonObject.keys();
                         while (iUser.hasNext()){
                             String user=iUser.next();
                             if(jsonObject.getJSONObject(field).has(user)&&!jsonObject.getJSONObject(field).isNull(user)){
                                 count++;
                             }
                         }
                     }
                 }
             }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle, "Current guild user profile cache entries count:"+count, llColors.llColorBlue1);
        }
        private void getGuildInfo() {
            String fName = "[getGuildInfo]";
            logger.info(cName + fName);
            try {
                JSONObject jsonGuild=lsDiscordApi.lsGetGuildJson(gGuild.getId());
                if(jsonGuild==null)jsonGuild=new JSONObject();
                String guildIconUrl = gGuild.getIconUrl();
                String guildName = gGuild.getName();
                String guildID = gGuild.getId();
                OffsetDateTime guildTimeCreated = gGuild.getTimeCreated();
                String guildBannerUrl = gGuild.getBannerUrl();
                String guildSplashUrl = gGuild.getSplashUrl();
                String guildVanityUrl=gGuild.getVanityUrl();String guildVanityCode=gGuild.getVanityCode();
                int guildBoostCount = gGuild.getBoostCount();
                Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTimestamp(guildTimeCreated);
                embed.setThumbnail(guildIconUrl);
                //embed.setFooter(guildID);
                String desc="";
            /*embed.setTitle("Server Info");
            desc = "Name: " + guildName;
            desc += "\nBoost Tier: " + guildBoostTier.ordinal() + " | Boost count: " + guildBoostCount;
            assert guildBannerUrl != null;
            if (guildBoostTier.ordinal() >= 2 && !guildBannerUrl.isEmpty()) {
                desc += "\n[Banner](" + guildBannerUrl + ")";
            }
            assert guildSplashUrl != null;
            if (guildBoostTier.ordinal() >= 1 && !guildSplashUrl.isEmpty()) {
                desc += "\n[Splash](" + guildSplashUrl + ")";
            }
            embed.setDescription(desc);*/
                Member owner=gGuild.getOwner();
                if(owner!=null){
                    desc="Owner: "+owner.getUser().getName()+"#"+owner.getUser().getDiscriminator();
                }
                desc+="\nId: "+guildID;desc+="\nBoost Tier: "+guildBoostTier.ordinal()+" ("+guildBoostCount+")";
                desc+="\nRegion: "+gGuild.getRegion().getName();
                if(jsonGuild.has("nsfw")){
                    desc+="\nnsfw_boolean: "+jsonGuild.optBoolean("nsfw");
                }
                if(jsonGuild.has("nsfw_level")){
                    desc+="\nnsfw_level: ";
                    int nsfwLevel=jsonGuild.optInt("nsfw_level",-1);
                    switch (nsfwLevel){
                        case 0:desc+="DEFAULT(0)";break;
                        case 1:desc+="EXPLICIT(1)";break;
                        case 2:desc+="SAFE(2)";break;
                        case 3:desc+="AGE_RESTRICTED(3)";break;
                        default:desc+=nsfwLevel;break;
                    }
                }
                if (guildBoostTier.ordinal() >= 1 && !guildSplashUrl.isEmpty()) {
                    desc += "\n[Splash](" + guildSplashUrl + ")";
                }
                if (guildBoostTier.ordinal() >= 2 && !guildBannerUrl.isEmpty()) {
                    desc += "\n[Banner](" + guildBannerUrl + ")";
                }
                if (guildBoostTier.ordinal() >= 3 && !guildVanityUrl.isEmpty()) {
                    desc += "\n[Vanity URL](" + guildVanityUrl + ")";
                }
                embed.addField(guildName,desc,false);
                desc="";
                if(gGuild.getExplicitContentLevel()==Guild.ExplicitContentLevel.NO_ROLE){
                    desc="NSFW Filter: no role";
                }else
                if(gGuild.getExplicitContentLevel()==Guild.ExplicitContentLevel.ALL){
                    desc="NSFW Filter: everyone";
                }else
                if(gGuild.getExplicitContentLevel()==Guild.ExplicitContentLevel.OFF){
                    desc="NSFW Filter: off";
                }
                if(gGuild.getVerificationLevel()==Guild.VerificationLevel.VERY_HIGH){
                    desc+="\nVerification Level: very high";
                }else
                if(gGuild.getVerificationLevel()==Guild.VerificationLevel.HIGH){
                    desc+="\nVerification Level: high";
                }else
                if(gGuild.getVerificationLevel()==Guild.VerificationLevel.MEDIUM){
                    desc+="\nVerification Level: medium";
                }else
                if(gGuild.getVerificationLevel()==Guild.VerificationLevel.LOW){
                    desc+="\nVerification Level: low";
                }else
                if(gGuild.getVerificationLevel()==Guild.VerificationLevel.NONE){
                    desc+="\nVerification Level: no";
                }
                if(gGuild.getRequiredMFALevel()==Guild.MFALevel.TWO_FACTOR_AUTH){
                    desc+="\n2FA Enabled: yes";
                }else{
                    desc+="\n2FA Enabled: no";
                }
                embed.addField("Security",desc,false);
                List<Role>roles=gGuild.getRoles();
                int roleCount=roles.size(), roleHoistedCount=0, roleMentionableCount=0;
                for(Role role:roles){
                    if(role.isHoisted())roleHoistedCount++;
                    if(role.isMentionable())roleMentionableCount++;
                }
                desc="Count: "+roleCount+"\nHoisted: "+roleHoistedCount+"\nMentionable: "+roleMentionableCount;
                embed.addField("Roles",desc,false);
                //List<net.dv8tion.jda.api.entities.Category>categories=gGuild.getCategories();
                List<TextChannel>textChannels=gGuild.getTextChannels();
                int textChannelsCount=textChannels.size(), textChannelsSFW=0, textChannelsNSFW=0;
                for(TextChannel textChannel:textChannels){
                   if(textChannel.isNSFW()){textChannelsNSFW++;}else{textChannelsSFW++;}
                }
                desc="Count: "+textChannelsCount+"\nSFW: "+textChannelsSFW+"\nNSFW: "+textChannelsNSFW;
                embed.addField("Text Channels",desc,false);
                List<VoiceChannel>voiceChannels=gGuild.getVoiceChannels();
                VoiceChannel afkChannel=gGuild.getAfkChannel();
                Guild.Timeout afkTimeout=gGuild.getAfkTimeout();
                desc="Count: "+voiceChannels.size();
                desc+="\n";
                if(afkChannel!=null){
                    desc+="AFK Channel: "+afkChannel.getName();
                }
                if(afkTimeout!=null){
                    if(afkTimeout.getSeconds()>1){
                        desc+="AFK Timout: "+afkTimeout.getSeconds()+" seconds";
                    }else
                    if(afkTimeout.getSeconds()==1){
                        desc+="AFK Timout: "+afkTimeout.getSeconds()+" second";
                    }
                }
                embed.addField("Voice Channels",desc,false);
                List<Emote>emotes=gGuild.getEmotes();
                int emoteCount=0,emoteStaticCount=0,emoteAnimatedCount=0,emoteAvailableCount=0,emoteManageCount=0;
                for(Emote emote:emotes){
                    emoteCount++;
                    if(emote.isAvailable())emoteAvailableCount++;
                    if(emote.isManaged())emoteManageCount++;
                    if(emote.isAnimated())emoteAnimatedCount++;else emoteStaticCount++;
                }
                embed.addField("Emote Statistics","Count: "+emoteCount+"\nAvailable: "+emoteAvailableCount+"\nAnimated: "+emoteAnimatedCount+"\nmanaged:"+emoteManageCount,false);
                List<Member>members=gGuild.getMembers();  List<Guild.Ban>bans=gGuild.retrieveBanList().complete();
                int memberCount=gGuild.getMemberCount(), memberHuman=0, memberBot=0, userBanned=bans.size();
                for(Member member:members){
                    /*removed since JDA.version 4.3.0+ if(!member.isFake()){*/
                    if(member.getUser().isBot()){memberBot++;}else{memberHuman++;}
                }
                embed.addField("Member Statistics","Count: "+memberCount+"\nHuman: "+memberHuman+"\nBot: "+memberBot+"\nBanned:"+userBanned,false);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getBoostInfo() {
            String fName = "[getBoostInfo]";
            logger.info(cName + fName);
            int guildBoostCount = gGuild.getBoostCount();
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            List<Member> members = gGuild.getBoosters();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle("Booster Info");
            StringBuilder desc = new StringBuilder("Boost Tier: " + guildBoostTier.ordinal() + " | Boost count: " + guildBoostCount);
            desc.append("\nBoosters:\n");
            int j = 0;
            for (Member member : members) {
                j++;
                if (j == 3) {
                    j = 0;
                    desc.append("\n");
                }
                desc.append(" ").append(member.getAsMention()).append(" ");
            }
            embed.setDescription(desc.toString());

            lsMessageHelper.lsSendMessage(gTextChannel, embed);
        }
        private void getGuildSplash() {
            String fName = "[getGuildSplash]";
            logger.info(cName + fName);
            String guildSplashUrl = gGuild.getSplashUrl();
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(llColors.llColorRed);
            embed.setTitle("Server Splash");
            String desc = "Server has no splash!";
            assert guildSplashUrl != null;
            if (guildBoostTier.ordinal() >= 1 && !guildSplashUrl.isEmpty()) {
                desc = "[Splash](" + guildSplashUrl + ")";
                embed.setImage(guildSplashUrl);
                embed.setColor(llColors.llColorBlue1);
            }
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel, embed);
        }
        private void getGuildBanner() {
            String fName = "[getGuildBanner]";
            logger.info(cName + fName);
            String guildBannerUrl = gGuild.getBannerUrl();
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(llColors.llColorRed);
            embed.setTitle("Server Banner");
            String desc = "Server has no banner!";
            assert guildBannerUrl != null;
            if (guildBoostTier.ordinal() >= 2 && !guildBannerUrl.isEmpty()) {
                desc = "[Banner](" + guildBannerUrl + ")";
                embed.setImage(guildBannerUrl);
                embed.setColor(llColors.llColorBlue1);
            }
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel, embed);
        }
        private void setGuildSplash() {
            String fName = "[setGuildSplash]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            String title = "Set Splash";
            if (guildBoostTier.ordinal() != 1 && guildBoostTier.ordinal() != 2) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Invalid tier!", llColors.llColorRed);
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Please upload an image.", llColors.llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            List<Message.Attachment> items = e.getMessage().getAttachments();
                            if (items.isEmpty()) {
                                logger.warn(cName + fName + ".no attachments");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, no attachments!", llColors.llColorRed);
                                return;
                            }
                            Message.Attachment item = items.get(0);
                            String fileExtension = item.getFileExtension();
                            logger.info(cName + fName + ".fileExtension=" + fileExtension);
                            assert fileExtension != null;
                            if (!(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png"))) {
                                logger.warn(cName + fName + ".invalid extension");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, invalid attachments! Needs to be jpg or png.", llColors.llColorRed);
                                return;
                            }
                            String str = item.getUrl();
                            logger.info(cName + fName + ".strUrl=" + str);
                            URL url = new URL(str);
                            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                            InputStream is = httpcon.getInputStream();
                            Icon icon = Icon.from(is);
                            gGuild.getManager().setSplash(icon).submit().whenComplete((s, error) -> {
                                if (error != null) {
                                    logger.error(cName + fName + "error=" + error);
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set failed.", llColors.llColorRed);
                                } else {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set success.", llColors.llColorGreen1);
                                }
                            });
                        } catch (Exception ex) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image uploaded failed.", llColors.llColorRed);
                            logger.error(cName + fName + "exception=" + ex);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Timeout!", llColors.llColorRed));
        }
        private void setGuildBanner() {
            String fName = "[setGuildBanner]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            String title = "Set Banner";
            if (guildBoostTier.ordinal() != 2) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Invalid tier!", llColors.llColorRed);
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Please upload an image.", llColors.llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            List<Message.Attachment> items = e.getMessage().getAttachments();
                            if (items.isEmpty()) {
                                logger.warn(cName + fName + ".no attachments");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, no attachments!", llColors.llColorRed);
                                return;
                            }
                            Message.Attachment item = items.get(0);
                            String fileExtension = item.getFileExtension();
                            logger.info(cName + fName + ".fileExtension=" + fileExtension);
                            assert fileExtension != null;
                            if (!(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png"))) {
                                logger.warn(cName + fName + ".invalid extension");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, invalid attachments! Needs to be jpg or png.", llColors.llColorRed);
                                return;
                            }
                            String str = item.getUrl();
                            logger.info(cName + fName + ".strUrl=" + str);
                            URL url = new URL(str);
                            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                            InputStream is = httpcon.getInputStream();
                            Icon icon = Icon.from(is);
                            gGuild.getManager().setBanner(icon).submit().whenComplete((s, error) -> {
                                if (error != null) {
                                    logger.error(cName + fName + "error=" + error);
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set failed.", llColors.llColorRed);
                                } else {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set success.", llColors.llColorGreen1);
                                }
                            });
                        } catch (Exception ex) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image uploaded failed.", llColors.llColorRed);
                            logger.error(cName + fName + "exception=" + ex);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Timeout!", llColors.llColorRed));
        }
        private void setGuildIcon() {
            String fName = "[setGuildIcon]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            Guild.BoostTier guildBoostTier = gGuild.getBoostTier();
            String title = "Set Icon";
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Please upload an image.", llColors.llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            List<Message.Attachment> items = e.getMessage().getAttachments();
                            if (items.isEmpty()) {
                                logger.warn(cName + fName + ".no attachments");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, no attachments!", llColors.llColorRed);
                                return;
                            }
                            Message.Attachment item = items.get(0);
                            String fileExtension = item.getFileExtension();
                            logger.info(cName + fName + ".fileExtension=" + fileExtension);
                            assert fileExtension != null;
                            if (guildBoostTier.ordinal() == 1 || guildBoostTier.ordinal() == 2) {
                                if (!(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("gif"))) {
                                    logger.warn(cName + fName + ".invalid extension");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, invalid attachments! Needs to be jpg, png or gif.", llColors.llColorRed);
                                    return;
                                }
                            } else {
                                if (!(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png"))) {
                                    logger.warn(cName + fName + ".invalid extension");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Error, invalid attachments! Needs to be jpg or png.", llColors.llColorRed);
                                    return;
                                }
                            }

                            String str = item.getUrl();
                            logger.info(cName + fName + ".strUrl=" + str);
                            URL url = new URL(str);
                            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                            InputStream is = httpcon.getInputStream();
                            Icon icon = Icon.from(is);
                            gGuild.getManager().setBanner(icon).submit().whenComplete((s, error) -> {
                                if (error != null) {
                                    logger.error(cName + fName + "error=" + error);
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set failed.", llColors.llColorRed);
                                } else {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image set success.", llColors.llColorGreen1);
                                }
                            });
                        } catch (Exception ex) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Image uploaded failed.", llColors.llColorRed);
                            logger.error(cName + fName + "exception=" + ex);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Timeout!", llColors.llColorRed));
        }
        private long twoWeeksToSecond=1209600;
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        private void getOneRoleNewMembers4Prune() {
            String fName = "[getOneRoleNewMembers4Prune]";
            logger.info(cName + fName);
            lsMessageHelper.lsSendMessage(gTextChannel,"This command was moved to `!>prune`");
        }
        private void pruneOneRoleNewMembers() {
            String fName = "[pruneOneRoleNewMembers]";
            logger.info(cName + fName);
            lsMessageHelper.lsSendMessage(gTextChannel,"This command was moved to `!>prune`");
        }
        private void pruneOneRoleNewMembersByMentioning() {
            String fName = "[pruneOneRoleNewMembersByMentioning]";
            logger.info(cName + fName);
            lsMessageHelper.lsSendMessage(gTextChannel,"This command was moved to `!>prune`");
        }
        private void statusOneRoleNewMembersByMentioning() {
            String fName = "[statusOneRoleNewMembersByMentioning]";
            logger.info(cName + fName);
            lsMessageHelper.lsSendMessage(gTextChannel,"This command was moved to `!>prune`");
        }
        private void messageAfterPruned(Member member){
            String fName = "[messageAfterPruned]";
            logger.info(cName + fName);
            lsMessageHelper.lsSendMessage(gTextChannel,"This command was moved to `!>prune`");
        }
        private void getGuildDescription(){
            String fName = "[getGuildDescription]";
            logger.info(cName + fName);
            String tmp=gGuild.getDescription();
            if(tmp!=null&&!tmp.isBlank()){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Guild description:"+tmp,llColors.llColorGreen1);
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"There is no Guild description",llColors.llColorRed);
            }
        }
        private void setGuildDescription(){
            String fName = "[setGuildDescription]";
            logger.info(cName + fName);
            String title="Set Guild Description";
            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Please enter a text. Or type '!cancel", llColors.llColorPurple1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            String text=e.getMessage().getContentRaw();
                            if(text.contains("!cancel")){
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Canceled", llColors.llColorRed);
                                return;
                            }
                            gGuild.getManager().setDescription(text).submit().whenComplete((s, error) -> {
                                if (error != null) {
                                    logger.error(cName + fName + "error=" + error);
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Set guild description failed.", llColors.llColorRed);
                                } else {
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "et guild description success.", llColors.llColorGreen1);
                                }
                            });
                        } catch (Exception ex) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Set guild description failed.", llColors.llColorRed);
                            logger.error(cName + fName + "exception=" + ex);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> lsMessageHelper.lsSendQuickEmbedMessage(gUser, title, "Timeout!", llColors.llColorRed));

        }
        String sTitleRegistry="Server Registry";
        private void getGuildRegistry(){
            String fName = "[getGuildRegistry]";
            logger.info(cName + fName);
            if (!llMemberIsAdministrator(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Administrators only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColors.llColorRed);logger.warn(cName + fName+"not registered");return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sTitleRegistry);embed.setColor(llColors.llColorOrange_Pumpkin);
            String desc="Server keys:";
            Iterator<String>keys=entrie.jsonObject.keys();
            while(keys.hasNext()){
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
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    desc="Server keys:"+"\n"+data;
                }else{
                    desc=tmp;
                }
            }
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        private void setGuildRegistry_BooleanValue(String name, String value){
            String fName = "[setGuildRegistry_BooleanValue]";
            logger.info(cName + fName);
            if (!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Administrators only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            logger.info(cName + fName+"name="+name);
            logger.info(cName + fName+"value="+value);
            boolean bvalue=false;
            if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1")){bvalue=true;}
            else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("0")){bvalue=false;}
            else{  lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Invalid value", llColors.llColorRed); logger.warn(cName + fName+"invalid value");return;}
            logger.info(cName + fName+"bvalue="+bvalue);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColors.llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            entrie.jsonObject.put(name,bvalue);
            if(!entrie.saveProfile(llv2_GuildsSettings)){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColors.llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+bvalue+" to "+name, llColors.llColorGreen1);
        }
        private void setGuildRegistry_IntValue(String name, String value){
            String fName = "[setGuildRegistry_IntValue]";
            logger.info(cName + fName);
            if (!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Administrators only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            logger.info(cName + fName+"name="+name);
            logger.info(cName + fName+"value="+value);
            int ivalue=0;
            try {
                ivalue= Integer.parseInt(value);
            } catch (Exception e) {
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Invalid value", llColors.llColorRed); logger.warn(cName + fName+"invalid value");return;
            }
            logger.info(cName + fName+"ivalue="+ivalue);
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColors.llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            entrie.jsonObject.put(name,ivalue);
            if(!entrie.saveProfile(llv2_GuildsSettings)){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColors.llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+ivalue+" to "+name, llColors.llColorGreen1);
        }
        private void setGuildRegistry_WorldValue(String name, String value){
            String fName = "[setGuildRegistry_WorldValue]";
            logger.info(cName + fName);
            if (!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Administrators only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            lcJSONGuildProfile entrie= gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
            if(entrie==null){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Server is not registered", llColors.llColorRed); logger.warn(cName + fName+"not registered");return;
            }
            logger.info(cName + fName+"name="+name);logger.info(cName + fName+"value="+value);
            entrie.jsonObject.put(name,value);
            if(!entrie.saveProfile(llv2_GuildsSettings)){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Failed to save key", llColors.llColorRed); logger.warn(cName + fName+"failed to save");return;
            }
            gGlobal.putGuildSettings(gGuild,entrie);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sTitleRegistry,"Saved value "+value+" to "+name, llColors.llColorGreen1);
        }

        //https://www.codota.com/code/java/classes/net.dv8tion.jda.core.requests.restaction.pagination.AuditLogPaginationAction
		//https://www.codota.com/code/java/classes/net.dv8tion.jda.core.requests.restaction.pagination.AuditLogPaginationAction?snippet=5ce6d223e594670004f6ae38
        private void sendMembersJsonFile(Guild guild){
            String fName = "[sendMembersFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                List<Member>members=guild.getMembers();
                logger.info(cName + fName+"members.Size="+members.size());
                for(Member member : members){
                    try {
                        JSONObject jsonMember= lsJson4Entity.llGetMemberJsonEntry(member);
                        if(jsonMember!=null)array.put(jsonMember);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyMembers,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Members", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendChannelsJsonFile(Guild guild){
            String fName = "[sendChannelsFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                List<GuildChannel>channels=guild.getChannels();
                logger.info(cName + fName+"channels.Size="+channels.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    jsonGuild.put(llCommonKeys.keyCount,channels.size());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                for(GuildChannel channel : channels){
                    try {
                        if(channel.getParent()==null){
                            JSONObject jsonChannel= new JSONObject();
                            if(channel.getType()==ChannelType.CATEGORY){
                                jsonChannel= lsJson4Entity.llGetCategoryJsonEntryWithChildren(guild.getCategoryById(channel.getIdLong()));
                            }else{
                                jsonChannel= lsJson4Entity.llGetChannelJsonEntry(channel);
                            }

                            if(jsonChannel!=null)array.put(jsonChannel);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyChannels,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Channels", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendRolesJsonFile(Guild guild){
            String fName = "[sendRolesFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                List<Role>roles=guild.getRoles();
                logger.info(cName + fName+"roles.Size="+roles.size());
                for(Role role : roles){
                    try {
                        JSONObject jsonRole= lsJson4Entity.llGetRoleJsonEntry(role);
                        if(jsonRole!=null)array.put(jsonRole);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyRoles,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Roles", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendRolesJsonZipFile(Guild guild){
            String fName = "[sendRolesZipFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                String entryName="%id", entryExtension=".json";
                lcTempZipFile zipFile=new lcTempZipFile();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                List<Role>roles=guild.getRoles();
                logger.info(cName + fName+"roles.Size="+roles.size());
                for(Role role : roles){
                    try {
                        JSONObject jsonRole= lsJson4Entity.llGetRoleJsonEntry(role);
                        if(jsonRole!=null) {
                            zipFile.addEntity(entryName.replaceAll("%id",role.getId())+entryExtension,jsonRole);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Roles", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendChannelsJsonZipFile(){
            String fName = "[sendChannelsZipFile]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension=".json";

                List<GuildChannel>channels=gGuild.getChannels();
                logger.info(fName+"channels.Size="+channels.size());
                for(GuildChannel channel : channels){
                    try {
                        JSONObject jsonChannel= lsJson4Entity.llGetChannelJsonEntry(channel);
                        if(jsonChannel!=null) {
                            String name=entryName.replaceAll("%id", channel.getId()) + entryExtension;
                            zipFile.addEntity(name,jsonChannel);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }

                InputStream targetStream = zipFile.getInputStream();
                String fileName="Channels", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendMembersJsonZipFile(Guild guild){
            String fName = "[sendMembersZipFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension=".json";
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                List<Member>members=guild.getMembers();
                logger.info(cName + fName+"members.Size="+members.size());
                for(Member member : members){
                    try {
                        JSONObject jsonMember= lsJson4Entity.llGetMemberJsonEntry(member);
                        if(jsonMember!=null){
                            String name=entryName.replaceAll("%id", member.getId()) + entryExtension;
                            zipFile.addEntity(name,jsonMember);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Members", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void sendAuditFile(Guild guild){
            String fName = "[sendAuditFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_VIEWAUDITLOGS(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_VIEWAUDITLOGS(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time. As the audit log is big, it might take 15-30 minutes.",llColors.llColorBlue1);
                AuditLogPaginationAction auditLogs=guild.retrieveAuditLogs();
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int countEntry=0;
                for (AuditLogEntry auditEntry : auditLogs)
                {
                    try {
                        JSONObject jsonEntry= lsJson4Entity.llGetAuditJsonEntry(auditEntry);
                        if(jsonEntry!=null)array.put(jsonEntry);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                logger.info(cName + fName+"entries.number="+countEntry);
                object.put(llCommonKeys.keyLogs,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="AuditLog", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendAuditZipFile(Guild guild){
            String fName = "[sendAuditZipFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_VIEWAUDITLOGS(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_VIEWAUDITLOGS(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time. As the audit log is big, it might take 15-30 minutes.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%group", entryExtension=".json";
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                AuditLogPaginationAction auditLogs=guild.retrieveAuditLogs();

                JSONArray array;
                JSONObject jsonInCategories=new JSONObject();
                for (AuditLogEntry auditEntry : auditLogs)
                {
                    try {
                        JSONObject jsonEntry= lsJson4Entity.llGetAuditJsonEntry(auditEntry);
                        if(jsonEntry!=null) {
                            int type=auditEntry.getType().getKey();
                            if(jsonInCategories.isEmpty()||!jsonInCategories.has(String.valueOf(type))){
                                jsonInCategories.put(String.valueOf(type),new JSONArray());
                            }
                            array=jsonInCategories.getJSONArray(String.valueOf(type));
                            array.put(jsonEntry);
                            jsonInCategories.put(String.valueOf(type),array);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                array=null;
                try {
                    Iterator<String>keys=jsonInCategories.keys();
                    String group="unknown";
                    while(keys.hasNext()){
                        String key=keys.next();
                        int iKey=Integer.parseInt(key);
                        if(ActionType.from(iKey)==ActionType.BOT_ADD){group="BOT_ADD";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_CREATE){group="CHANNEL_CREATE";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_DELETE){group="CHANNEL_DELETE";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_OVERRIDE_CREATE){group="CHANNEL_OVERRIDE_CREATE";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_OVERRIDE_DELETE){group="CHANNEL_OVERRIDE_DELETE";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_OVERRIDE_UPDATE){group="CHANNEL_OVERRIDE_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.CHANNEL_UPDATE){group="CHANNEL_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.EMOTE_CREATE){group="EMOTE_CREATE";}
                        if(ActionType.from(iKey)==ActionType.EMOTE_DELETE){group="EMOTE_DELETE";}
                        if(ActionType.from(iKey)==ActionType.EMOTE_UPDATE){group="EMOTE_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.GUILD_UPDATE){group="GUILD_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.INTEGRATION_CREATE){group="INTEGRATION_CREATE";}
                        if(ActionType.from(iKey)==ActionType.INTEGRATION_DELETE){group="INTEGRATION_DELETE";}
                        if(ActionType.from(iKey)==ActionType.INTEGRATION_UPDATE){group="INTEGRATION_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.INVITE_CREATE){group="INVITE_CREATE";}
                        if(ActionType.from(iKey)==ActionType.INVITE_DELETE){group="INVITE_DELETE";}
                        if(ActionType.from(iKey)==ActionType.INVITE_UPDATE){group="INVITE_UPDATE)";}
                        if(ActionType.from(iKey)==ActionType.KICK){group="KICK";}
                        if(ActionType.from(iKey)==ActionType.MEMBER_ROLE_UPDATE){group="MEMBER_ROLE_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.MEMBER_UPDATE){group="MEMBER_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.MEMBER_VOICE_KICK){group="MEMBER_VOICE_KICK";}
                        if(ActionType.from(iKey)==ActionType.MEMBER_VOICE_MOVE){group="MEMBER_VOICE_MOVE";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_BULK_DELETE){group="MESSAGE_BULK_DELETE";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_CREATE){group="MESSAGE_CREATE";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_DELETE){group="MESSAGE_DELETE";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_PIN){group="MESSAGE_PIN";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_UNPIN){group="MESSAGE_UNPIN";}
                        if(ActionType.from(iKey)==ActionType.MESSAGE_UPDATE){group="MESSAGE_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.PRUNE){group="PRUNE";}
                        if(ActionType.from(iKey)==ActionType.ROLE_CREATE){group="ROLE_CREATE";}
                        if(ActionType.from(iKey)==ActionType.ROLE_DELETE){group="ROLE_DELETE";}
                        if(ActionType.from(iKey)==ActionType.ROLE_UPDATE){group="ROLE_UPDATE";}
                        if(ActionType.from(iKey)==ActionType.UNBAN){group="UNBAN";}
                        if(ActionType.from(iKey)==ActionType.WEBHOOK_CREATE){group="WEBHOOK_CREATE";}
                        if(ActionType.from(iKey)==ActionType.WEBHOOK_REMOVE){group="WEBHOOK_REMOVE";}
                        if(ActionType.from(iKey)==ActionType.WEBHOOK_UPDATE){group="WEBHOOK_UPDATE";}
                        if(jsonInCategories.has(key)||!jsonInCategories.isNull(key)){
                            String name=entryName.replaceAll("%group",group) + entryExtension;
                            zipFile.addEntity(name,jsonInCategories.getJSONArray(key));
                            jsonInCategories.put(key, new JSONArray());
                        }
                    }
                }catch (Exception e){
                    logger.error(cName + fName + ".exception=" + e);
                    logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Audit", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendBanJsonFile(Guild guild){
            String fName = "[sendBanListFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                List<Guild.Ban>bans=guild.retrieveBanList().complete();
                logger.info(cName + fName+"bans.Size="+bans.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                    object.put(llCommonKeys.keyCount,bans.size());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                for(Guild.Ban ban : bans){
                    try {
                        JSONObject jsonMember= lsJson4Entity.llGetBanJsonEntry(ban);
                        if(jsonMember!=null)array.put(jsonMember);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyBans,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Bans", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendBanJsonZipFile(Guild guild){
            String fName = "[sendBanListZipFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension=".json";
                List<Guild.Ban>bans=guild.retrieveBanList().complete();
                logger.info(cName + fName+"bans.Size="+bans.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    jsonGuild.put(llCommonKeys.keyCount,bans.size());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                for(Guild.Ban ban : bans){
                    try {
                        JSONObject jsonMember= lsJson4Entity.llGetBanJsonEntry(ban);
                        if(jsonMember!=null){
                            String name=entryName.replaceAll("%id",ban.getUser().getId()) + entryExtension;
                            zipFile.addEntity(name,jsonMember);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Bans", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void sendMembersListFile(String option){
            String fName = "[sendMembersListFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                //JSONArray array=new JSONArray();
                StringBuilder list= new StringBuilder();
                List<Member>members=gGuild.getMembers();
                logger.info(cName + fName+"members.Size="+members.size());
                for(Member member : members){
                    try {
                        /*if(option!=null&&option.equalsIgnoreCase("id")){
                            array.put(member.getId());
                        }else if(option!=null&&option.equalsIgnoreCase("name")){
                            array.put(member.getUser().getName()+"#"+member.getUser().getDiscriminator());
                        } else{
                            array.put(member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                        }*/
                        if(list.length()==0){
                            if(option!=null&&option.equalsIgnoreCase("id")){
                                list.append(member.getUser().getId());
                            }else if(option!=null&&option.equalsIgnoreCase("name")){
                                list.append(member.getUser().getName()).append("#").append(member.getUser().getDiscriminator());
                            } else{
                                list.append(member.getUser().getName()).append("#").append(member.getUser().getDiscriminator()).append("(").append(member.getUser().getId()).append(")");
                            }
                        }else{
                            if(option!=null&&option.equalsIgnoreCase("id")){
                                list.append("\n").append(member.getUser().getId());
                                //array.put(ban.getUser().getId());
                            }else if(option!=null&&option.equalsIgnoreCase("name")){
                                list.append("\n").append(member.getUser().getName()).append("#").append(member.getUser().getDiscriminator());
                                //array.put(ban.getUser().getName()+"#"+ban.getUser().getDiscriminator());
                            } else{
                                list.append("\n").append(member.getUser().getName()).append("#").append(member.getUser().getDiscriminator()).append("(").append(member.getUser().getId()).append(")");
                                //array.put(ban.getUser().getName()+"#"+ban.getUser().getDiscriminator()+"("+ban.getUser().getId()+")");
                            }
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                //InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                InputStream targetStream = new ByteArrayInputStream(list.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Members", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the list:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendBanListFile(String option){
            String fName = "[sendBanListFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                //JSONArray array=new JSONArray();
                StringBuilder list= new StringBuilder();
                List<Guild.Ban>bans=gGuild.retrieveBanList().complete();
                logger.info(cName + fName+"bans.Size="+bans.size());
                for(Guild.Ban ban : bans){
                    try {
                        if(list.length()==0){
                            if(option!=null&&option.equalsIgnoreCase("id")){
                                list.append(ban.getUser().getId()).append(" reason<").append(ban.getReason().trim()).append(">");
                            }else if(option!=null&&option.equalsIgnoreCase("name")){
                                list.append(ban.getUser().getName()).append("#").append(ban.getUser().getDiscriminator()).append(" reason<").append(ban.getReason().trim()).append(">");
                            } else{
                                list.append(ban.getUser().getName()).append("#").append(ban.getUser().getDiscriminator()).append("(").append(ban.getUser().getId()).append(")").append(" reason<").append(ban.getReason().trim()).append(">");
                            }
                        }else{
                            if(option!=null&&option.equalsIgnoreCase("id")){
                                list.append("\n").append(ban.getUser().getId()).append(" reason<").append(ban.getReason().trim()).append(">");
                                //array.put(ban.getUser().getId());
                            }else if(option!=null&&option.equalsIgnoreCase("name")){
                                list.append("\n").append(ban.getUser().getName()).append("#").append(ban.getUser().getDiscriminator()).append(" reason<").append(ban.getReason().trim()).append(">");
                                //array.put(ban.getUser().getName()+"#"+ban.getUser().getDiscriminator());
                            } else{
                                list.append("\n").append(ban.getUser().getName()).append("#").append(ban.getUser().getDiscriminator()).append("(").append(ban.getUser().getId()).append(")").append(" reason<").append(ban.getReason().trim()).append(">");
                                //array.put(ban.getUser().getName()+"#"+ban.getUser().getDiscriminator()+"("+ban.getUser().getId()+")");
                            }
                        }

                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                //InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                InputStream targetStream = new ByteArrayInputStream(list.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Bans", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the list:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void downloadServerImages(String option){
            String fName = "[downloadAll]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,gGuild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                String entryName="%name(%id)", entryExtension="";
                if(option!=null&&option.equalsIgnoreCase("id"))entryName="%id";
                else if(option!=null&&option.equalsIgnoreCase("name"))entryName="%name";
                JSONArray jsonServerImages= lsJson4Entity.llGetServerImageEntries(gGuild);

                for(int i=0;i<jsonServerImages.length();i++){
                    try {
                        JSONObject jsonObject=jsonServerImages.getJSONObject(i);
                        String url=jsonObject.getString(keyImageUrl);
                        String id=jsonObject.getString(keyImageId);
                        String name=jsonObject.getString(keyType);
                        InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
                        InputStream inputStream4096= lsStreamHelper.llGetInputStream4WebFile(url+"?size=4096");
                        String namex0=entryName.replaceAll("%id",id).replaceAll("%name",name);
                        if(url.toLowerCase().contains("png"))entryExtension=".png";
                        if(url.toLowerCase().contains("jpg"))entryExtension=".jpg";
                        if(url.toLowerCase().contains("gif"))entryExtension=".gif";
                        if(inputStream!=null) {
                            String namex=namex0 + entryExtension;
                            zipFile.addEntity(namex,inputStream);
                            inputStream.close();
                        }
                        if(inputStream4096!=null) {
                            String namex=namex0+"_4096" + entryExtension;
                            zipFile.addEntity(namex,inputStream4096);
                            inputStream.close();
                        }

                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Server", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the server images:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void sendInviteJsonFile(Guild guild){
            String fName = "[sendInviteJsonFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                List<Invite>invites=guild.retrieveInvites().complete();
                logger.info(cName + fName+"invites.Size="+invites.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                    object.put(llCommonKeys.keyCount,invites.size());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                for(Invite invite : invites){
                    try {
                        JSONObject jsonInvite= lsJson4Entity.llGetInviteJsonEntry(invite);
                        if(jsonInvite!=null)array.put(jsonInvite);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyInvites,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Invites", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void sendServerJsonFile(){
            String fName = "[sendServerJsonFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                JSONObject jsonObject= lsJson4Entity.llGetServerEntry(gGuild);
                if(jsonObject!=null){
                    InputStream targetStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
                    OffsetDateTime now=OffsetDateTime.now();
                    String fileName="Server", fileExtension=".json";
                    fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                    gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                    targetStream=null;
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void downloadMemberAvatars(){
            String fName = "[downloadMemberAvatars]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension="";
                List<Member>members=gGuild.getMembers();
                logger.info(cName + fName+"members.Size="+members.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,gGuild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                    jsonGuild.put(llCommonKeys.keyCount,members.size());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=0;
                InputStream targetStream = null;
                String fileName="Avatars", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                for(Member member : members){
                    try {
                        JSONObject jsonMember=lsJson4Entity.llGetMemberJsonEntry(member);
                        zipFile.addEntity(member.getId()+".json",jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        String membername=member.getUser().getName()+"#"+member.getUser().getDiscriminator();
                        String avatarUrl=member.getUser().getEffectiveAvatarUrl();
                        logger.info(cName + fName+"membername="+membername);
                        logger.info(cName + fName+"avatarUrl="+avatarUrl);
                        InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(avatarUrl);
                        if(inputStream!=null) {
                            if(avatarUrl.toLowerCase().contains(".png"))entryExtension=".png";
                            else if(avatarUrl.toLowerCase().contains(".jpg"))entryExtension=".jpg";
                            else if(avatarUrl.toLowerCase().contains(".gif"))entryExtension=".gif";
                            String namex=entryName.replaceAll("%id",member.getId())+entryExtension;
                            logger.info(cName + fName+"file="+namex);
                            zipFile.addEntity(namex,inputStream);inputStream.close();
                            int size=zipFile.size();
                            logger.info(cName + fName+"size="+zipFile.size());
                            if(size>=7000000){
                                logger.info(cName + fName+"send:"+count);
                                targetStream = zipFile.getInputStream();
                                gTextChannel.sendMessage("Avatar image:").addFile(targetStream,fileName+"("+count+")"+fileExtension).complete(); targetStream=null;
                                zipFile.reset();
                                count++;
                            }
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                int size=zipFile.size();
                logger.info(cName + fName+"size="+zipFile.size());
                if(size>0){
                    logger.info(cName + fName+"send:"+count);
                    targetStream = zipFile.getInputStream();
                    gTextChannel.sendMessage("Last Avatar image:").addFile(targetStream,fileName+"("+count+")"+fileExtension).complete(); targetStream=null;
                    zipFile.reset();
                }else{
                    gTextChannel.sendMessage("Finished sending all Avatar images.").complete();
                }
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void getGuildNSFWFlag(){
            String fName = "[getGuildNSFWFlag]";
            logger.info(cName + fName);
            try {
                lcJSONGuildProfile entry=gGlobal.getGuildSettings(gGuild,keyServer);
                if(!entry.isExistent()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No such entry", llColors.llColorRed);return;
                }
                if(!entry.jsonObject.has(keyIsNSFWServer)||entry.jsonObject.isNull(keyIsNSFWServer)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No such flag "+keyIsNSFWServer, llColors.llColorRed);return;
                }
                boolean result=entry.jsonObject.getBoolean(keyIsNSFWServer);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Flag "+keyIsNSFWServer+"="+result, llColors.llColorPurple2);
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }
        private void setGuildNSFWFlag(boolean flag){
            String fName = "[setGuildNSFWFlag]";
            logger.info(cName + fName);
            try {
                if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                    lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                    return;
                }
                lcJSONGuildProfile entry=gGlobal.getGuildSettings(gGuild,keyServer);
                if(!entry.isExistent()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No such entry", llColors.llColorRed);return;
                }
                entry.jsonObject.put(keyIsNSFWServer,flag);
                gGlobal.putGuildSettings(gGuild,entry);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Flag "+keyIsNSFWServer+"="+flag, llColors.llColorPurple2);
                if(!entry.saveProfile(llv2_GuildsSettings)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Failed to save!", llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }
        }

        lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
        String gCommandMainPath ="resources/json/utility/guild/menuMain.json";
        private void menuMain(int page){
            String fName="[menuMain]";
            logger.info(fName+" page="+page);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);embed.setTitle(gTitle);
                String desc="";
                Message message=null;
                String quickSummonWithSpace = prefix2 + commandPrefix+" ";
                /*
                else if(command.equalsIgnoreCase(commandKeyInfo)){
                    embed.addField("Index","\n`" + quickSummonWithSpace + "dmindex <modes>`\n`" + quickSummonWithSpace + "indexbuild <modes>`\nmodes: alldisplayboice, justvoice, channels, textchannels",false);
                }
                */
                switch (page){
                    case 1:
                        embed.addField("Info","Get info of the server(guild)\n`" + quickSummonWithSpace + "serverinfo`",false);
                        embed.addField("Boost","Get server boost info\n`" + quickSummonWithSpace + "boostinfo`",false);
                        embed.addField("Splash","Get server slpash\n`" + quickSummonWithSpace + "getsplash`",false);
                        embed.addField("Banner","Get server banner\n`" + quickSummonWithSpace + "getbanner`",false);
                        break;
                    case 2:
                        embed.addField("List bans","Provides a txt file of banned users name and id\n`" + quickSummonWithSpace + "listbans`",false);
                        embed.addField("List members","Provides a txt file of members name and id\n`" + quickSummonWithSpace + "listmembers`",false);
                        break;
                    case 3:
                        embed.addField("Json","Provides a json file of informations about server, members,bans, audit, channel:\n`" + quickSummonWithSpace + "jsonaudit|jsonbans|jsonchannels|jsonroles|jsonmembers|jsoninvites|jsonserver`",false);
                        break;
                    case 4:
                        embed.addField("Caches&settings","Caches and settings for bot:\n`" + quickSummonWithSpace + "reloadsettings|countsettings|countuserprofilecache|clearuserprofilecache`",false);
                        break;
                }
                messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                messageComponentManager.loadMessageComponents(gCommandMainPath);
                try {
                    logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2=messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent component3=messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent component4=messageComponentManager.messageBuildComponents.getComponent(4);
                    lcMessageBuildComponent componentPaginator=messageComponentManager.messageBuildComponents.getComponent(messageComponentManager.messageBuildComponents.componentsSize()-1);
                    if(page==1){
                        componentPaginator.getButtonAt4(0).setDisable();
                    }
                    if(page==4){
                        componentPaginator.getButtonAt4(1).setDisable();
                    }
                    component0.setIgnored();component1.setIgnored(); component2.setIgnored();component3.setIgnored();component4.setIgnored();
                    switch (page){
                        case 1:
                            component1.setIgnored(false);
                            break;
                        case 2:
                            component2.setIgnored(false);
                            break;
                        case 3:
                            component3.setIgnored(false);
                            break;
                        case 4:
                            component4.setIgnored(false);
                            break;
                    }
                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuMainListener(message,page);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void menuMainListener(Message message, int page){
            String fName="[menuMainListener]";
            logger.info(fName);
            try{
                if(message==null)throw  new Exception("Return message is null");
                gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasInformationSource:
                                        help("main");
                                        break;
                                    case lsUnicodeEmotes.aliasArrowBackward:
                                        menuMain(page-1);
                                        break;
                                    case lsUnicodeEmotes.aliasArrowForward:
                                        menuMain(page+1);
                                        break;
                                    case "serverinfo":
                                       getGuildInfo();
                                        break;
                                    case "boostinfo":
                                        getBoostInfo();
                                        break;
                                    case "getsplash":
                                        getGuildSplash();
                                        break;
                                    case "getbanner":
                                        getGuildBanner();
                                        break;
                                    case "listbans":
                                        sendBanListFile("");
                                        break;
                                    case "listmembers":
                                        sendMembersListFile("");
                                        break;
                                    case "reloadsettings":
                                        reloadCurrentGuildSettings();
                                        break;
                                    case "countsettings":
                                        count4CurrentGuildSettings();
                                        break;
                                    case "clearuserprofilecache":
                                       clearUserProfilesCache4CurrentGuild();
                                        break;
                                    case "countuserprofilecache":
                                        countUserProfilesCache4Current();
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                if(page==3){
                    gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()),
                            e -> {
                                try {
                                    String value=e.getValues().get(0);
                                    logger.warn(fName+"value="+value);
                                    llMessageDelete(message);
                                    switch (value){
                                        case "jsonaudit":
                                            sendAuditFile(gGuild);
                                            break;
                                        case "jsonbans":
                                            sendBanJsonFile(gGuild);
                                            break;
                                        case "jsonchannels":
                                            sendChannelsJsonFile(gGuild);
                                            break;
                                        case "jsonroles":
                                            sendRolesJsonFile(gGuild);
                                            break;
                                        case "jsonmembers":
                                            sendMembersJsonFile(gGuild);
                                            break;
                                        case "jsoninvites":
                                            sendInviteJsonFile(gGuild);
                                            break;
                                        case "jsonserver":
                                            sendServerJsonFile();
                                            break;
                                        case "jsonaudit-multi":
                                            sendAuditZipFile(gGuild);
                                            break;
                                        case "jsonbans-multi":
                                            sendBanJsonZipFile(gGuild);
                                            break;
                                        case "jsonchannels-multi":
                                            sendChannelsJsonZipFile();
                                            break;
                                        case "jsonroles-multi":
                                            sendRolesJsonZipFile(gGuild);
                                            break;
                                        case "jsonmembers-multi":
                                            sendMembersJsonZipFile(gGuild);
                                            break;
                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llMessageDelete(message));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }

    }
}
