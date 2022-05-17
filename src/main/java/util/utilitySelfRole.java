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
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsRoleHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class utilitySelfRole extends Command implements  llGlobalHelper  {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="selfroles";
    public utilitySelfRole(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "SelfRoles-Utility";
        this.help = "debug";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category=llCommandCategory_BuildAlpha;
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
        private Guild gGuild;private TextChannel gTextChannel; private Message gMessage;String gTitle="SelfRoles";

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
                    if (items[0].equalsIgnoreCase("list")) {
                        list();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("enable")) {
                       enableSR(true);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("disable")) {
                        enableSR(false);isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("add")) {
                        addRole();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("remove")) {
                        removeRole();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("iam")) {
                        iam();isInvalidCommand=false;
                    }else
                    if (items[0].equalsIgnoreCase("iamnot")) {
                        iamnot();isInvalidCommand=false;
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
            String desc = " ";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);

            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void addRole() {
            String fName = "addRole";
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
                         List<Role>tmp1= lsRoleHelper.lsGetRolesByName(gGuild, items[i], false);
                         Role tmp2=lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                         if(tmp2!=null){
                             roles.add(tmp2);
                         }
                         if(tmp1!=null&&!tmp1.isEmpty()){
                             roles.addAll(tmp1);
                         }
                     }

                }
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles);
                    for(Role role:roles){
                        if(!role.hasPermission(Permission.ADMINISTRATOR)&&!role.hasPermission(Permission.MANAGE_ROLES)&&!role.hasPermission(Permission.MANAGE_SERVER)&&!role.hasPermission(Permission.MANAGE_CHANNEL)&&!role.hasPermission(Permission.BAN_MEMBERS)&&!role.hasPermission(Permission.KICK_MEMBERS)){
                            if(array.isEmpty()){
                                wasChanged=true;array.put(role.getId());
                            }else{
                                boolean canadd=true;
                                for(int index=0;index<array.length();index++){
                                    if(array.getString(index).equalsIgnoreCase(role.getId())){
                                        canadd=false;break;
                                    }
                                }
                                if(canadd){
                                    wasChanged=true;array.put(role.getId());
                                }
                            }

                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Can't add role with managing permission!",llColors.llColorRed_Barn);return;
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was added!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+generateMention(roles)+" to self roles list.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to add to self roles list!",llColors.llColorRed_Barn);
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
        private void removeRole() {
            String fName = "removeRole";
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
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles);
                    for(Role role:roles){
                        int index=0;
                        while(index<array.length()){
                            String id=array.getString(index);
                            if(id.equalsIgnoreCase(role.getId())){
                                array.remove(index);wasChanged=true;
                            }else{
                                index++;
                            }
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Nothing was removed!",llColors.llColorRed_Barn);return;
                    }
                    gProfile.jsonObject.put(keyRoles,array);
                    if(saveProfile()){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+generateMention(roles)+" from self roles list.",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Failed to remove from self roles list!",llColors.llColorRed_Barn);
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
        private void enableSR(boolean enable) {
            String fName = "enableSR";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    logger.info(fName + ".denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied!", llColors.llColorRed_Barn);
                    return;
                }
                logger.info(fName + ".enable="+enable);
                gProfile.jsonObject.put(keyEnabled,enable);
                if(saveProfile()){
                    if(enable){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"SelfRoles Enabled",llColors.llColorGreen1);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"SelfRoles Disabled",llColors.llColorPurple2);
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
        private void list() {
            String fName = "list";
            try {
                if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
                JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles);
                if(array.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No self roles were added!",llColors.llColorRed_Barn);return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorBlue1);
                String tmp=".";
                if(!gProfile.jsonObject.has(keyEnabled)||gProfile.jsonObject.isNull(keyEnabled)||!gProfile.jsonObject.getBoolean(keyEnabled)){
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
        private void iam() {
            String fName = "iam";
            try {
                if(!gProfile.jsonObject.has(keyEnabled)||gProfile.jsonObject.isNull(keyEnabled)||!gProfile.jsonObject.getBoolean(keyEnabled)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"SelfRoles is not enabled!",llColors.llColorRed_Barn);return;
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
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles);
                    for(Role role:roles){
                        if(!role.hasPermission(Permission.ADMINISTRATOR)&&!role.hasPermission(Permission.MANAGE_ROLES)&&!role.hasPermission(Permission.MANAGE_SERVER)&&!role.hasPermission(Permission.MANAGE_CHANNEL)&&!role.hasPermission(Permission.BAN_MEMBERS)&&!role.hasPermission(Permission.KICK_MEMBERS)){
                            for(int index=0;index<array.length();index++){
                                if(array.getString(index).equalsIgnoreCase(role.getId())){
                                    wasChanged=true;
                                    gGuild.addRoleToMember(gMember,role).queue();
                                    break;
                                }
                            }
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Can't self add role with managing permission!",llColors.llColorRed_Barn);return;
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Warning! Nothing was added to "+gMember.getAsMention()+"!",llColors.llColorRed_Barn);return;
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+generateMention(roles)+" to"+gMember.getAsMention(),llColors.llColorGreen1);
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
        private void iamnot() {
            String fName = "iamnot";
            try {
                if(!gProfile.jsonObject.has(keyEnabled)||gProfile.jsonObject.isNull(keyEnabled)||!gProfile.jsonObject.getBoolean(keyEnabled)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"SelfRoles is not enabled!",llColors.llColorRed_Barn);return;
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
                if(!roles.isEmpty()){
                    logger.info(fName + ".roles");
                    boolean wasChanged=false;
                    if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyRoles);
                    for(Role role:roles){
                        if(!role.hasPermission(Permission.ADMINISTRATOR)&&!role.hasPermission(Permission.MANAGE_ROLES)&&!role.hasPermission(Permission.MANAGE_SERVER)&&!role.hasPermission(Permission.MANAGE_CHANNEL)&&!role.hasPermission(Permission.BAN_MEMBERS)&&!role.hasPermission(Permission.KICK_MEMBERS)){
                            for(int index=0;index<array.length();index++){
                                if(array.getString(index).equalsIgnoreCase(role.getId())){
                                    wasChanged=true;
                                    gGuild.removeRoleFromMember(gMember,role).queue();
                                    break;
                                }
                            }
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Warning! Can't self remove role with managing permission!",llColors.llColorRed_Barn);return;
                        }
                    }
                    if(!wasChanged){
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Warning! Nothing was removed from "+gMember.getAsMention()+"!",llColors.llColorRed_Barn);return;
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed"+generateMention(roles)+" from "+gMember.getAsMention(),llColors.llColorGreen1);
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
        private String generateMention(List<Role>roles) {
            String fName = "generateMention";
            try {

                if(!roles.isEmpty()){
                    String tmp="";
                    for(Role role:roles){
                        if(tmp.isBlank()){
                            tmp=role.getAsMention();
                        }else{
                            tmp+=", "+role.getAsMention();
                        }
                    }
                }
                return "";
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        lcJSONGuildProfile gProfile;
        String gProfileName="selfroles";
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
        String keyEnabled="enabled",keyRoles="roles";
        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled)||gProfile.jsonObject.isNull(keyEnabled)){ gProfile.jsonObject.put(keyEnabled,false); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyRoles)||gProfile.jsonObject.isNull(keyRoles)){ gProfile.jsonObject.put(keyRoles,new JSONArray()); gProfile.isUpdated=true; }
        }
    }
}
