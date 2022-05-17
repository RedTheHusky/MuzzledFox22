package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class utilityAutoRole extends Command implements  llGlobalHelper {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="autoroles";

    long idBDSM_Verify = 476764066250096641L;
    long idBDSM_Member = 776862275520299009L;
    long idBDSM_NotVerified = 777063838474305566L;

    public utilityAutoRole(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "AutoRole-Utility";
        this.help = "Setting up auto role for new members.";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityAutoRole(lcGlobalHelper g, GuildMemberJoinEvent guildMemberJoinEvent){
        gGlobal=g;
        Runnable r = new runEvent(guildMemberJoinEvent);new Thread(r).start();
    }
    public utilityAutoRole(lcGlobalHelper g, GuildMemberRoleAddEvent guildMemberRoleAddEvent){
        gGlobal=g;
        Runnable r = new runEvent(guildMemberRoleAddEvent);new Thread(r).start();
    }
    public utilityAutoRole(lcGlobalHelper g, GuildMemberRoleRemoveEvent guildMemberRoleRemoveEvent){
        gGlobal=g;
        Runnable r = new runEvent(guildMemberRoleRemoveEvent);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel; String gTitle="AutoRoles";private Message gMessage;

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
            logger.info(fName);
            try {
                boolean isInvalidCommand = true;
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(fName + ".Args");

                    initProfile();
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items[0].equalsIgnoreCase("help")) {
                        if(items.length>=2){
                            help(items[1]);
                        }else{
                            help("main");
                        }
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("botslist")||items[0].equalsIgnoreCase("botlist")) {
                        listBot();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("botsenable")||items[0].equalsIgnoreCase("botenable")) {
                        enableBot(true);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("botsdisable")||items[0].equalsIgnoreCase("botdisable")) {
                        enableBot(false);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("botsadd")||items[0].equalsIgnoreCase("botadd")) {
                        addBot();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("botsremove")||items[0].equalsIgnoreCase("botremove")) {
                        removeBot();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("userslist")||items[0].equalsIgnoreCase("userlist")) {
                        listUser();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("usersenable")||items[0].equalsIgnoreCase("userenable")) {
                        enableUser(true);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("usersdisable")||items[0].equalsIgnoreCase("userdisable")) {
                        enableUser(false);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("usersadd")||items[0].equalsIgnoreCase("useradd")) {
                        addUser();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("usersremove")||items[0].equalsIgnoreCase("userremove")) {
                        removeUser();isInvalidCommand=false;
                    }else
                    if (items.length>=2&&(items[0].equalsIgnoreCase("bots")||items[0].equalsIgnoreCase("bot"))) {
                        if (items[1].equalsIgnoreCase("list")) {
                            listBot();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("enable")) {
                            enableBot(true);isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("disable")) {
                            enableBot(false);isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("add")) {
                            addBot();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("remove")) {
                            removeBot();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("clear")) {
                            clearBot();isInvalidCommand=false;
                        }
                    }else
                    if (items.length>=2&&(items[0].equalsIgnoreCase("users")||items[0].equalsIgnoreCase("user"))) {
                        if (items[1].equalsIgnoreCase("list")) {
                            listUser();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("enable")) {
                            enableUser(true);isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("disable")) {
                            enableUser(false);isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("add")) {
                            addUser();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("remove")) {
                            removeUser();isInvalidCommand=false;
                        }else
                        if (items[1].equalsIgnoreCase("clear")) {
                            clearUser();isInvalidCommand=false;
                        }
                    }else
                    if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)){
                        if (items[0].equalsIgnoreCase("removensfw")) {
                            removeNSFWroles4NonVerified();isInvalidCommand=false;
                        }else
                        if (items[0].equalsIgnoreCase("addnew")) {
                            addNewMemberRole();isInvalidCommand=false;
                        }else
                        if (items[0].equalsIgnoreCase("addnotverified")) {
                            addNotVerifiedRole();isInvalidCommand=false;
                        }
                    }


                }
                logger.info(fName + ".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = "";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            embed.addField("List auto roles","`"+quickSummonWithSpace+"[bot|user] list`, list the auto roles for the bots/users",false);
            embed.addField("Enable/Disable auto roles","`"+quickSummonWithSpace+"[bot|user] [enable|disable]`, enables or disables the auto roles for the bots/users",false);
            embed.addField("Add/Remove auto roles","`"+quickSummonWithSpace+"[bot|user] [add|remove]`, add or removes auto roles for the bots/users",false);
            if(gGuild.getId().equals(lsCustomGuilds.lsGuildKeyBdsm)){
                embed.addField("In house","`"+quickSummonWithSpace+"removensfw`"+"\n`"+quickSummonWithSpace+"addnew`"+"\n`"+quickSummonWithSpace+"addnotverified`",false);
            }
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void addBot() {
            String fName = "addBot";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    for(int i=1;i<items.length;i++){
                         List<Role>tmp1=lsRoleHelper.lsGetRolesByName(gGuild, items[i], false);
                         Role tmp2= lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                         if(tmp2!=null){
                             roles.add(tmp2);
                         }
                         if(tmp1!=null&&!tmp1.isEmpty()){
                             roles.addAll(tmp1);
                         }
                     }

                }
                List<String> preview=new ArrayList();
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4Bot);
                    for(Role role:roles){
                        if(!role.hasPermission(Permission.ADMINISTRATOR)&&!role.hasPermission(Permission.MANAGE_ROLES)&&!role.hasPermission(Permission.MANAGE_SERVER)&&!role.hasPermission(Permission.MANAGE_CHANNEL)&&!role.hasPermission(Permission.BAN_MEMBERS)&&!role.hasPermission(Permission.KICK_MEMBERS)){
                            if(array.isEmpty()){
                                wasChanged=true;array.put(role.getId());
                            }
                            boolean canadd=true;
                            for(int index=0;index<array.length();index++){
                                if(array.getString(index).equalsIgnoreCase(role.getId())){
                                    canadd=false;break;
                                }
                            }
                            if(canadd){
                                wasChanged=true;array.put(role.getId());
                                preview.add(role.getAsMention());
                            }
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Can't add role with managing permission!",llColors.llColorRed_Barn);return;
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was added!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles4Bot,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+lsUsefullFunctions.listListString(preview)+" role(s) to bot auto roles list .",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to add to bot auto roles list!",llColors.llColorRed_Barn);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Please mention role(s) to add!",llColors.llColorRed_Barn);return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void removeBot() {
            String fName = "removeBot";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    for(int i=1;i<items.length;i++){
                        List<Role>tmp1=lsRoleHelper.lsGetRolesByName(gGuild, items[i], false);
                        Role tmp2=lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                        if(tmp2!=null){
                            roles.add(tmp2);
                        }
                        if(tmp1!=null&&!tmp1.isEmpty()){
                            roles.addAll(tmp1);
                        }
                    }
                }
                List<String>preview=new ArrayList<>();
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4Bot);
                    for(Role role:roles){
                        int index=0;
                        while(index<array.length()){
                            String id=array.getString(index);
                            if(id.equalsIgnoreCase(role.getId())){
                                array.remove(index);wasChanged=true;preview.add(role.getAsMention());
                            }else{
                                index++;
                            }
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was removed!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles4Bot,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+lsUsefullFunctions.listListString(preview)+" role(s) from bot auto roles list.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to remove from bot auto roles list!",llColors.llColorRed_Barn);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Please mention role(s) to add!",llColors.llColorRed_Barn);return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void clearBot() {
            String fName = "clearBot";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                gProfile.jsonObject.put(keyRoles4Bot,new JSONArray());gProfile.isUpdated=true;
                if(saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed all roles from bot auto roles list.",llColors.llColorGreen1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to remove from bot auto roles list!",llColors.llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void enableBot(boolean enable) {
            String fName = "enableBot";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                logger.info(fName + ".enable="+enable);
                gProfile.jsonObject.put(keyEnabled4Bot,enable);
                if(saveProfile()){
                    if(enable){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Bot auto roles enabled",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Bot auto roles disabled",llColors.llColorPurple2);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to update changes!",llColors.llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void listBot() {
            String fName = "listBot";
            try {
                if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray()); gProfile.isUpdated=true; }
                JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4Bot);
                if(array.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No auto roles were added!",llColors.llColorRed_Barn);return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle+" for bot.");embedBuilder.setColor(llColors.llColorBlue1);
                String tmp="Roles: ";
                if(!gProfile.jsonObject.has(keyEnabled4Bot)||gProfile.jsonObject.isNull(keyEnabled4Bot)||!gProfile.jsonObject.getBoolean(keyEnabled4Bot)){
                    embedBuilder.addField("Status","Disabled",false);
                }else{
                    embedBuilder.addField("Status","Enabled",false);
                }
                for(int i=0;i<array.length();i++){
                    String id=array.getString(i);
                    String mention=lsRoleHelper.lsGetRoleMentionByID(gGuild,id);
                    if(mention==null||mention.isBlank()){
                        tmp+=" <"+id+">";
                    }else{
                        tmp+=mention+" ";
                    }
                }
                embedBuilder.setDescription(tmp);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void addUser() {
            String fName = "addUser";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    for(int i=1;i<items.length;i++){
                        List<Role>tmp1=lsRoleHelper.lsGetRolesByName(gGuild, items[i], false);
                        Role tmp2=lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                        if(tmp2!=null){
                            roles.add(tmp2);
                        }
                        if(tmp1!=null&&!tmp1.isEmpty()){
                            roles.addAll(tmp1);
                        }
                    }

                }
                List<String>preview=new ArrayList<>();
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4User);
                    for(Role role:roles){
                        if(!role.hasPermission(Permission.ADMINISTRATOR)&&!role.hasPermission(Permission.MANAGE_ROLES)&&!role.hasPermission(Permission.MANAGE_SERVER)&&!role.hasPermission(Permission.MANAGE_CHANNEL)&&!role.hasPermission(Permission.BAN_MEMBERS)&&!role.hasPermission(Permission.KICK_MEMBERS)){
                            if(array.isEmpty()){
                                wasChanged=true;array.put(role.getId());
                            }
                            boolean canadd=true;
                            for(int index=0;index<array.length();index++){
                                if(array.getString(index).equalsIgnoreCase(role.getId())){
                                    canadd=false;break;
                                }
                            }
                            if(canadd){
                                wasChanged=true;array.put(role.getId());preview.add(role.getAsMention());
                            }
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Can't add role with managing permission!",llColors.llColorRed_Barn);return;
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was added!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles4User,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+lsUsefullFunctions.listListString(preview)+" role(s) to user auto roles list.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to add to user auto roles list!",llColors.llColorRed_Barn);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Please mention role(s) to add!",llColors.llColorRed_Barn);return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void removeUser() {
            String fName = "removeUser";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    for(int i=1;i<items.length;i++){
                        List<Role>tmp1=lsRoleHelper.lsGetRolesByName(gGuild, items[i], false);
                        Role tmp2=lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                        if(tmp2!=null){
                            roles.add(tmp2);
                        }
                        if(tmp1!=null&&!tmp1.isEmpty()){
                            roles.addAll(tmp1);
                        }
                    }
                }
                List<String>preview=new ArrayList<>();
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4User);
                    for(Role role:roles){
                        int index=0;
                        while(index<array.length()){
                            String id=array.getString(index);
                            if(id.equalsIgnoreCase(role.getId())){
                                array.remove(index);wasChanged=true;preview.add(role.getAsMention());
                            }else{
                                index++;
                            }
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was removed!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles4User,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+lsUsefullFunctions.listListString(preview)+" role(s) from user auto roles list.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to remove from user auto roles list!",llColors.llColorRed_Barn);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Please mention role(s) to add!",llColors.llColorRed_Barn);return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void clearUser() {
            String fName = "clearUser";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                gProfile.jsonObject.put(keyRoles4User,new JSONArray());gProfile.isUpdated=true;
                if(saveProfile()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed all roles from user auto roles list.",llColors.llColorGreen1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to remove from user auto roles list!",llColors.llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void enableUser(boolean enable) {
            String fName = "enableUser";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                logger.info(fName + ".enable="+enable);
                gProfile.jsonObject.put(keyEnabled4User,enable);
                if(saveProfile()){
                    if(enable){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Users auto roles enabled.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Users auto roles disabled.",llColors.llColorPurple2);
                    }
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to update changes!",llColors.llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void listUser() {
            String fName = "listUser";
            try {
                if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); gProfile.isUpdated=true; }
                JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles4User);
                if(array.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No auto roles were added!",llColors.llColorRed_Barn);return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle+" for users.");embedBuilder.setColor(llColors.llColorBlue1);
                String tmp=".";
                if(!gProfile.jsonObject.has(keyEnabled4User)||gProfile.jsonObject.isNull(keyEnabled4User)||!gProfile.jsonObject.getBoolean(keyEnabled4User)){
                    embedBuilder.addField("Status","Disabled",false);
                }else{
                    embedBuilder.addField("Status","Enabled",false);
                }
                for(int i=0;i<array.length();i++){
                    String id=array.getString(i);
                    String mention=lsRoleHelper.lsGetRoleMentionByID(gGuild,id);
                    if(mention==null||mention.isBlank()){
                        tmp+=" <"+id+">";
                    }else{
                        tmp+=mention+" ";
                    }
                }
                embedBuilder.setDescription(tmp);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        lcJSONGuildProfile gProfile;
        String gProfileName="autoroles";
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putGuildSettings(gGuild,gProfile);
            if(gProfile.saveProfile()){
                logger.info(fName + ".success save to db");return true;
            }
            logger.error(fName + ".error save to db");return false;
        }
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gProfileName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gProfileName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyProfile();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
        }
        String keyEnabled4Bot ="enabled4bots", keyRoles4Bot ="roles4bots";
        String keyEnabled4User ="enabled4users", keyRoles4User ="roles4users";
        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled4Bot)||gProfile.jsonObject.isNull(keyEnabled4Bot)){ gProfile.jsonObject.put(keyEnabled4Bot,false); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray()); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled4User)||gProfile.jsonObject.isNull(keyEnabled4User)){ gProfile.jsonObject.put(keyEnabled4User,false); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); gProfile.isUpdated=true; }
        }

        private void removeNSFWroles4NonVerified() {
            String fName = "removeNSFWroles4NonVerified";
            try {
                logger.info(fName );
                Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,"Processing...");

                List<Long>id2Remove=new ArrayList<>();
                id2Remove.add(475599879482245120L);
                id2Remove.add(674927018357424128L);
                id2Remove.add(475600056309776395L);
                id2Remove.add(674926001884626964L);
                id2Remove.add(475599453923835904L);
                id2Remove.add(674927349044609034L);
                id2Remove.add(476305822956126209L);
                id2Remove.add(594054046177034263L);
                id2Remove.add(476225649938530307L);
                id2Remove.add(475731310497890305L);
                id2Remove.add(534563304463400981L);
                id2Remove.add(475602612553646090L);
                id2Remove.add(476304946149720066L);
                id2Remove.add(475992310488760321L);
                id2Remove.add(475608580179886082L);
                id2Remove.add(767952599613177876L);
                id2Remove.add(476333847785504778L);
                id2Remove.add(614435655702937602L);
                id2Remove.add(480991342357512193L);
                id2Remove.add(767956153203163157L);
                id2Remove.add(481561380974231554L);
                id2Remove.add(475994940468625410L);
                id2Remove.add(475871374297268236L);
                id2Remove.add(475663205473976323L);
                id2Remove.add(475663261589569539L);
                id2Remove.add(536751857947639823L);
                List<Member>members=gGuild.getMembers();
                Role verified=lsRoleHelper.lsGetRoleByID(gGuild, idBDSM_Verify);
                logger.info(fName + ".role verified :" + verified.getName()+"("+verified.getId()+")");
                List<Member>verifiedMembers=gGuild.getMembersWithRoles(verified);
                logger.info(fName + ".members.size=" + members.size());
                logger.info(fName + ".verifiedMembers.size=" + verifiedMembers.size());
                if(members.isEmpty()){
                    lsMessageHelper.lsMessageDelete(message);
                    lsMessageHelper.lsSendMessage(gTextChannel,"No members");
                    return;
                }
                for(int i=0;i<members.size();i++){
                    try {
                        Member member=members.get(i);
                        logger.info(fName + ".member[" +i+"]:"+member.getUser().getName()+"("+member.getId()+")");
                        if(member.getUser().isBot()){
                            logger.info(fName + ".member[" +i+"]:is a bot");
                        }else
                        if(verifiedMembers.contains(member)){
                            logger.info(fName + ".member[" +i+"]:is verified");
                        }else{
                            logger.info(fName + ".member[" +i+"]:unverified");
                            List<Role>roles4Member=member.getRoles();
                            logger.info(fName + ".member[" +i+"].rolesSize"+roles4Member.size());
                            if(!roles4Member.isEmpty()){
                                try {
                                    if(roles4Member.contains(verified)){
                                        logger.info(fName + ".member[" +i+"]:contains-ver");
                                    }else{
                                        for(int j=0;j<roles4Member.size();j++){
                                            Role role=roles4Member.get(j);
                                            logger.info(fName + ".member[" +i+"].role["+j+"]:"+role.getName()+"("+role.getId()+")");
                                            if(id2Remove.contains(role.getIdLong())){
                                                logger.info(fName + ".member[" +i+"].role["+j+"]: is a mach> remove");
                                                try {
                                                    gGuild.removeRoleFromMember(member,role).complete();
                                                    logger.info(fName + ".member[" +i+"].role["+j+"]: removed");
                                                }catch (Exception e){
                                                    logger.error(fName + ".exception=" + e);
                                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                                }
                                            }else{
                                                logger.info(fName + ".member[" +i+"].role["+j+"]: ignore");
                                            }
                                        }
                                    }
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }else{
                                logger.info(fName + ".member[" +i+"]: not a member");
                            }
                        }

                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                lsMessageHelper.lsMessageDelete(message);
                lsMessageHelper.lsSendMessage(gTextChannel,"Successfully updated members roles.");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void addNewMemberRole() {
            String fName = "addNewMemberRole";
            try {
                logger.info(fName );
                Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,"Processing...");
                List<Member>members=gGuild.getMembers();
                Role NewMember=lsRoleHelper.lsGetRoleByID(gGuild,idBDSM_Member);
                List<Member>membersHave=gGuild.getMembersWithRoles(NewMember);
                logger.info(fName + ".role NewMember :" + NewMember.getName()+"("+NewMember.getId()+")");
                logger.info(fName + ".members.size=" + members.size());
                logger.info(fName + ".membersHave.size=" + membersHave.size());
                if(members.isEmpty()){
                    lsMessageHelper.lsMessageDelete(message);
                    lsMessageHelper.lsSendMessage(gTextChannel,"No members");
                    return;
                }
                for(int i=0;i<members.size();i++){
                    try {
                        Member member=members.get(i);
                        logger.info(fName + ".member[" +i+"]:"+member.getUser().getName()+"("+member.getId()+")");
                        if(member.getUser().isBot()){
                            logger.info(fName + ".member[" +i+"]:is a bot");
                        }else
                        if(membersHave.contains(member)){
                            logger.info(fName + ".member[" +i+"]ignore");
                        }else{
                            List<Role>roles=member.getRoles();
                            if(!roles.isEmpty()){
                                try {
                                    gGuild.addRoleToMember(member,NewMember).complete();
                                    logger.info(fName + ".member[" +i+"]add");
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
                lsMessageHelper.lsMessageDelete(message);
                lsMessageHelper.lsSendMessage(gTextChannel,"Successfully updated members roles.");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void addNotVerifiedRole() {
            String fName = "addNotVerifiedRole";
            try {
                logger.info(fName );
                Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,"Processing...");

                List<Member>members=gGuild.getMembers();
                logger.info(fName + ".members.size=" + members.size());
                Role NotVerified=lsRoleHelper.lsGetRoleByID(gGuild, idBDSM_NotVerified);
                logger.info(fName + ".role NotVerified :" + NotVerified.getName()+"("+NotVerified.getId()+")");
                Role Verified=lsRoleHelper.lsGetRoleByID(gGuild, idBDSM_Verify);
                logger.info(fName + ".role Verified :" + Verified.getName()+"("+Verified.getId()+")");
                List<Member>membersHaveVerified=gGuild.getMembersWithRoles(Verified);
                logger.info(fName + ".membersHaveVerified.size=" + membersHaveVerified.size());
                List<Member>membersHaveNotVerified=gGuild.getMembersWithRoles(NotVerified);
                logger.info(fName + ".membersHaveNotVerified.size=" + membersHaveNotVerified.size());
                if(members.isEmpty()){
                    lsMessageHelper.lsMessageDelete(message);
                    lsMessageHelper.lsSendMessage(gTextChannel,"No members");
                    return;
                }
                for(int i=0;i<members.size();i++){
                    try {
                        Member member=members.get(i);
                        logger.info(fName + ".member[" +i+"]:"+member.getUser().getName()+"("+member.getId()+")");
                        if(member.getUser().isBot()){
                            logger.info(fName + ".member[" +i+"]:is a bot>ignore");
                        }else
                        if(membersHaveVerified.contains(member)){
                            logger.info(fName + ".member[" +i+"]has verified>ignore");
                        }else
                        if(membersHaveNotVerified.contains(member)){
                            logger.info(fName + ".member[" +i+"]has not verified>ignore");
                        }
                        else{
                            List<Role>roles=member.getRoles();
                            if(!roles.isEmpty()){
                                try {
                                    gGuild.addRoleToMember(member,NotVerified).complete();
                                    logger.info(fName + ".member[" +i+"]add");
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
                lsMessageHelper.lsMessageDelete(message);
                lsMessageHelper.lsSendMessage(gTextChannel,"Successfully updated members roles.");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
    }
    protected class runEvent implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel; String gTitle="AutoRoles";
        boolean isMemberJoinEvent=false,isMemberRoleAddEvent=false,isMemberRoleRemoveEvent=false;

        public runEvent(GuildMemberJoinEvent guildMemberJoinEvent) {
            logger.info(".run build");String fName="runLocal";
            gUser = guildMemberJoinEvent.getUser();
            gMember = guildMemberJoinEvent.getMember();
            gGuild =guildMemberJoinEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            isMemberJoinEvent=true;
        }
        public runEvent(GuildMemberRoleAddEvent guildMemberRoleAddEvent) {
            logger.info(".run build");String fName="runLocal";
            gUser = guildMemberRoleAddEvent.getUser();
            gMember = guildMemberRoleAddEvent.getMember();
            gGuild =guildMemberRoleAddEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            isMemberRoleAddEvent=true;
        }
        public runEvent(GuildMemberRoleRemoveEvent guildMemberRoleRemoveEvent) {
            logger.info(".run build");String fName="runLocal";
            gUser = guildMemberRoleRemoveEvent.getUser();
            gMember = guildMemberRoleRemoveEvent.getMember();
            gGuild =guildMemberRoleRemoveEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            isMemberRoleRemoveEvent=true;
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(fName);
            try {
                boolean isInvalidCommand = true;
                if(isMemberJoinEvent){
                    initProfile();
                    JSONArray array=new JSONArray();
                    if(gUser.isBot()){
                        logger.info(fName + "its a bot");
                        if(!gProfile.jsonObject.has(keyEnabled4Bot)||gProfile.jsonObject.isNull(keyEnabled4Bot)||!gProfile.jsonObject.getBoolean(keyEnabled4Bot)){
                            logger.info(fName + "its disabled");return;
                        }
                        if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray());  }
                        array=gProfile.jsonObject.getJSONArray(keyRoles4Bot);
                    }else{
                        logger.info(fName + "its a user");
                        if(!gProfile.jsonObject.has(keyEnabled4User)||gProfile.jsonObject.isNull(keyEnabled4User)||!gProfile.jsonObject.getBoolean(keyEnabled4User)){
                            logger.info(fName + "its disabled");return;
                        }
                        if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); }
                        array=gProfile.jsonObject.getJSONArray(keyRoles4User);
                    }
                    for(int i=0;i<array.length();i++){
                        try {
                            String id=array.getString(i);
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,id);
                            if(role!=null){
                                gGuild.addRoleToMember(gMember,role).reason("by AutoRole").queue();
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }
                if(isMemberRoleAddEvent||isMemberRoleRemoveEvent){
                    if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)){
                        addNotVerifiedRole();
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }


        lcJSONGuildProfile gProfile;
        String gProfileName="autoroles";
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putGuildSettings(gGuild,gProfile);
            if(gProfile.saveProfile()){
                logger.info(fName + ".success save to db");return true;
            }
            logger.error(fName + ".error save to db");return false;
        }
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gProfileName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gProfileName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyProfile();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
        }
        String keyEnabled4Bot ="enabled4bots", keyRoles4Bot ="roles4bots";
        String keyEnabled4User ="enabled4users", keyRoles4User ="roles4users";
        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled4Bot)||gProfile.jsonObject.isNull(keyEnabled4Bot)){ gProfile.jsonObject.put(keyEnabled4Bot,false); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyRoles4Bot)||gProfile.jsonObject.isNull(keyRoles4Bot)){ gProfile.jsonObject.put(keyRoles4Bot,new JSONArray()); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled4User)||gProfile.jsonObject.isNull(keyEnabled4User)){ gProfile.jsonObject.put(keyEnabled4User,false); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyRoles4User)||gProfile.jsonObject.isNull(keyRoles4User)){ gProfile.jsonObject.put(keyRoles4User,new JSONArray()); gProfile.isUpdated=true; }
        }
        private void addNotVerifiedRole() {
            String fName = "addNotVerifiedRole";
            try {
                logger.info(fName );
                logger.info(fName+"member="+gMember.getUser().getName()+"("+gMember.getId()+")");
                if(gMember.getUser().isBot()){
                    logger.info(fName + ".member is a bot");
                }else{
                    boolean hasMember=lsMemberHelper.lsMemberHasRole(gMember,idBDSM_Member);
                    boolean hasVerified=lsMemberHelper.lsMemberHasRole(gMember,idBDSM_Verify);
                    boolean hasNotVerified=lsMemberHelper.lsMemberHasRole(gMember,idBDSM_NotVerified);
                    logger.info(fName+"hasMember="+hasMember+", hasVerified="+hasVerified+", hasNotVerified="+hasNotVerified);
                    if(!hasMember){
                        logger.info(fName+"ignore");return;
                    }
                    if(hasVerified&&hasNotVerified){
                        logger.info(fName+"remove unverified role");
                        gGuild.removeRoleFromMember(gMember,lsRoleHelper.lsGetRoleByID(gGuild,idBDSM_NotVerified)).complete();
                        logger.info(fName+"removed unverified role");
                    }
                    if(!hasVerified&&!hasNotVerified){
                        logger.info(fName+"add unverified role");
                        gGuild.addRoleToMember(gMember,lsRoleHelper.lsGetRoleByID(gGuild,idBDSM_NotVerified)).complete();
                        logger.info(fName+"added unverified role");
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
    }
}
