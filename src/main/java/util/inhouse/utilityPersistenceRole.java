package util.inhouse;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.audit.TargetType;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import org.apache.log4j.Logger;

import java.util.*;

public class utilityPersistenceRole extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName = "[persistenceRole]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String fieldPersistencerole="persistencerole";
    String keyPersistencerolEnablede="enabled";
    public utilityPersistenceRole(lcGlobalHelper global) {
        String fName = "[constructor]";
        logger.warn(cName + fName);
        gGlobal =global;
        this.name = "Utility-PersistenceRole";
        this.aliases = new String[]{"persistencerole"};
        this.help = "persistenceRole";
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName = "[execute]";logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {

        String[] items;
        CommandEvent gCommandEvent; Guild gGuild; TextChannel gTextChannel; User gUser;Member gMember;private Message gMessage;
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
            boolean isInvalidCommand=true;
            try {
                logger.info(cName + fName + ".mode=default");
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");

                } else {
                    logger.info(cName + fName + ".Args");
                    if(!llMemberIsStaff(gCommandEvent.getMember())){
                        llSendQuickEmbedMessage(gUser,"Self utility","Denied!", llColorRed);
                        logger.info(fName+".deleting op message");
                        llMessageDelete(gCommandEvent);
                        return;
                    }
                    // split the choices on all whitespace
                    items = gCommandEvent.getArgs().split("\\s+");
                    if (items.length == 0) {
                        return;
                    }
                    if(items[0].equalsIgnoreCase("on")||items[0].equalsIgnoreCase("true")||items[0].equalsIgnoreCase("1")){
                        enablePersistenceRole();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("off")||items[0].equalsIgnoreCase("false")||items[0].equalsIgnoreCase("0")){
                        disablePersistenceRole();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("get")){
                        getPersistenceRole();isInvalidCommand=false;
                    }
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gUser,"Persistence Role","You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        private void enablePersistenceRole() {
            String fName = "[enablePersistenceRole]";
            logger.info(cName + fName);
            String title="SetPersistenceRole";
            if(!gGlobal.guildsSettingsJson.has(gGuild.getId())){
                logger.error(cName + fName + "invalid guild");
                llSendQuickEmbedMessage(gUser,title,"Failed to enable it!", llColorRed);return ;
            }
            JSONObject jsonGuild= gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId());
            if(!jsonGuild.has(fieldPersistencerole)){
                logger.error(cName + fName + "no field");
                llSendQuickEmbedMessage(gUser,title,"Failed to enable it!", llColorRed);return ;
            }
            JSONObject jsonPersistenceRole=jsonGuild.getJSONObject(fieldPersistencerole);
            lcJSONGuildProfile entry=new lcJSONGuildProfile(gGlobal,fieldPersistencerole, gGuild);
            jsonPersistenceRole.put(keyPersistencerolEnablede,true);
            entry.jsonObject =jsonPersistenceRole;
            if(!entry.saveProfile(llv2_GuildsSettings)){
                llSendQuickEmbedMessage(gUser,title,"Failed to enable it!", llColorRed);return ;
            }
            llSendQuickEmbedMessage(gUser,title,"Enabled", llColorGreen1);
        }
        private void disablePersistenceRole() {
            String fName = "[disablePersistenceRole]";
            logger.info(cName + fName);
            String title="SetPersistenceRole";
            if(!gGlobal.guildsSettingsJson.has(gGuild.getId())){
                logger.error(cName + fName + "invalid guild");
                llSendQuickEmbedMessage(gUser,title,"Failed to disable it!", llColorRed);return ;
            }
            JSONObject jsonGuild= gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId());
            if(!jsonGuild.has(fieldPersistencerole)){
                logger.error(cName + fName + "no field");
                llSendQuickEmbedMessage(gUser,title,"Failed to disable it!", llColorRed);return ;
            }
            JSONObject jsonPersistenceRole=jsonGuild.getJSONObject(fieldPersistencerole);
            lcJSONGuildProfile entry=new lcJSONGuildProfile(gGlobal,fieldPersistencerole, gGuild);
            jsonPersistenceRole.put(keyPersistencerolEnablede,false);
            entry.jsonObject =jsonPersistenceRole;
            if(!entry.saveProfile(llv2_GuildsSettings)){
                llSendQuickEmbedMessage(gUser,title,"Failed to disable it!", llColorRed);return ;
            }
            llSendQuickEmbedMessage(gUser,title,"Disabled", llColorGreen1);
        }
        private void getPersistenceRole() {
            String fName = "[getPersistenceRole]";
            logger.info(cName + fName);
            String title="GetPersistenceRole";
            if(!gGlobal.guildsSettingsJson.has(gGuild.getId())){
                logger.error(cName + fName + "invalid guild");
                llSendQuickEmbedMessage(gUser,title,"Error!", llColorRed);return;
            }
            JSONObject jsonGuild= gGlobal.guildsSettingsJson.getJSONObject(gGuild.getId());
            if(!jsonGuild.has(fieldPersistencerole)){
                logger.error(cName + fName + "no field");
                llSendQuickEmbedMessage(gUser,title,"Error!", llColorRed);return;
            }
            JSONObject jsonPersistenceRole=jsonGuild.getJSONObject(fieldPersistencerole);
            if(!jsonPersistenceRole.has(keyPersistencerolEnablede)){
                logger.error(cName + fName + "no entry");
                llSendQuickEmbedMessage(gUser,title,"Error!", llColorRed);return ;
            }
            boolean result=jsonPersistenceRole.getBoolean("enabled");
            logger.info(cName + fName + "result:" + result);
            llSendQuickEmbedMessage(gUser,title,"Result:"+result, llColorGreen1);
        }
    }


    ////Demon
    //////////////
    public utilityPersistenceRole(GuildMemberRemoveEvent event, lcGlobalHelper g) {
        Runnable r = new runSQL(event,g);
        new Thread(r).start();
    }
    public utilityPersistenceRole(GuildMemberRoleAddEvent event, lcGlobalHelper g) {
        Runnable r = new runSQL(event,g);
        new Thread(r).start();
    }
    public utilityPersistenceRole(GuildMemberRoleRemoveEvent event, lcGlobalHelper g) {
        Runnable r = new runSQL(event,g);
        new Thread(r).start();
    }
    public utilityPersistenceRole(GuildBanEvent event, lcGlobalHelper g) {
        Runnable r = new runSQL(event,g);
        new Thread(r).start();
    }
    public utilityPersistenceRole(GuildMemberJoinEvent event, lcGlobalHelper g){
        Runnable r = new runSQL(event,g);
        new Thread(r).start();
    }
    /////////////////////
    protected class runSQL implements Runnable {
        String cName="[runLocalGuildMemberLeaveEvent]";
        lcGlobalHelper rlGlobal;
        String table="PersistenceRole";
        GuildMemberRemoveEvent guildmemberremoveevent;
        public runSQL(GuildMemberRemoveEvent event, lcGlobalHelper global){
            logger.info(".run build");
            guildmemberremoveevent=event;
            rlGlobal =global;
        }
        GuildMemberJoinEvent guildmemberjoinevent;
        public runSQL(GuildMemberJoinEvent event, lcGlobalHelper global){
            logger.info(".run build");
            guildmemberjoinevent=event;
            rlGlobal =global;
        }
        GuildMemberRoleAddEvent guildmemberroleaddevent;
        public runSQL(GuildMemberRoleAddEvent event, lcGlobalHelper global){
            logger.info(".run build");
            guildmemberroleaddevent=event;
            rlGlobal =global;
        }
        GuildMemberRoleRemoveEvent guildmemberroleremoveevent;
        public runSQL(GuildMemberRoleRemoveEvent event, lcGlobalHelper global){
            logger.info(".run build");
            guildmemberroleremoveevent=event;
            rlGlobal =global;
        }
        GuildBanEvent  guildbanevent;

        public runSQL(GuildBanEvent event, lcGlobalHelper global){
            logger.info(".run build");
            guildbanevent=event;
            rlGlobal =global;
        }
        @Override
        public void run() {
            logger.info(".run ended");
            dowork(guildmemberjoinevent);
            dowork(guildmemberremoveevent);//dowork(guildmemberleaveevent);
            dowork(guildbanevent);
            dowork(guildmemberroleaddevent);
            dowork(guildmemberroleremoveevent);
        }

        private void dowork(GuildMemberRemoveEvent event) {
            if (event == null) return;
            String fName = "[GuildMemberLeaveEvent]";
            logger.info(cName + fName);
            Guild guild=event.getGuild();
            if(!isExist(guild)){
                createDefaultEntry(guild);
            }
            if(!isEnabled(guild)){
                logger.warn(cName + fName + ".not enabled!");return;
            }
            Member member = event.getMember();

            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            assert member != null;
            logger.info(cName + fName + "member:" + member.getId() + "|" + member.getUser().getName());
            if (!rlGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            List<Role> roles = member.getRoles();
            logger.info(cName + fName + "roles.size=" + roles.size());
            for (Role role : roles) {
                logger.info(cName + fName + "role:" + role.getId() + "|" + role.getName());
            }

            AuditLogPaginationAction logs = guild.retrieveAuditLogs();
            logger.info(cName + fName + "AuditLogPaginationAction start");
            boolean isKick=false;
            for (AuditLogEntry entry : logs)
            {
                if(entry.getType()== ActionType.KICK){
                    logger.info(cName + fName + "audit["+entry.getId()+"].actionType==KICK"); isKick=true;
                    if(entry.getTargetType()== TargetType.MEMBER){
                        logger.info(cName + fName + "audit["+entry.getId()+"].targetType==MEMBER");
                        if(entry.getTargetId().equals(member.getId())){
                            logger.info(cName + fName + "audit["+entry.getId()+"].user="+ Objects.requireNonNull(entry.getUser()).getName()+" | target type="+entry.getTargetType().toString()+" | target id"+entry.getTargetId());
                            Map<String, Object> result = checkIfmemberHasAnEntry(guild.getId(), member.getId());
                            if(result==null||result.isEmpty()) {
                                logger.info(cName + fName + "audit["+entry.getId()+"].target has no persitantrole");
                            }else{
                                logger.info(cName + fName + "audit["+entry.getId()+"].target has persitantrole > DELETE");
                                rlGlobal.sql.delete(table, " guild_id='" + guild.getId() + "' and user_id='" + member.getId() + "'");
                            }
                        }
                    }
                }
                break;
            }
            logger.info(cName + fName + "AuditLogPaginationAction end");
            if(!isKick){
                logger.info(cName + fName + "no kick> do checkIfMemberHasValidRole");
                checkIfMemberHasValidRole(guild,member);
            }

        }
        private void dowork(GuildMemberRoleAddEvent event){
            if(event==null)return;
            String fName = "[GuildMemberRoleAddEvent]";
            logger.info(cName + fName);
            Guild guild=event.getGuild();
            if(!isExist(guild)){
                createDefaultEntry(guild);
            }
            if(!isEnabled(guild)){
                logger.warn(cName + fName + ".not enabled!");return;
            }
            Member member = event.getMember();
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "member:" + member.getId() + "|" + member.getUser().getName());
            List<Role> roles_has = member.getRoles();
            logger.info(cName + fName + "roles_has.size=" + roles_has.size());
            for (Role role : roles_has) {
                logger.info(cName + fName + "role_has:" + role.getId() + "|" + role.getName());
            }

            if (!rlGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            Map<String, Object> result = checkIfmemberHasAnEntry(guild.getId(), member.getId());
            Map<String, Object> data = new LinkedHashMap<>();

            List<Role> roles_update = event.getRoles();
            logger.info(cName + fName + "roles_update.size=" +roles_update.size());
            Boolean isOK=false; String addRole="";
            for (Role role : roles_update) {
                String roleId=role.getId();
                logger.info(cName + fName + "role_update:" + roleId + "|" + role.getName());
                if(roleId.equals(lsCustomGuilds.lsGuildAsylumMemberKey) || roleId.equals(lsCustomGuilds.lsGuildAsylumNewMemberKey)){
                    addRole=roleId; isOK=true;break;
                }else
                if(roleId.equals(lsCustomGuilds.lsGuildBdsmMemberKey) || roleId.equals(lsCustomGuilds.lsGuildBdsmNewMemberKey)){
                    addRole=roleId; isOK=true;break;
                }else
                if(roleId.equals(lsCustomGuilds.lsGuildChastityMemberKey) || roleId.equals(lsCustomGuilds.lsGuildChastityNewMemberKey)){
                    addRole=roleId; isOK=true;break;
                }else
                if(roleId.equals(lsCustomGuilds.lsGuildPrisonMemberKey) || roleId.equals(lsCustomGuilds.lsGuildPrisonNewMemberKey)){
                    addRole=roleId; isOK=true;break;
                }else
                if(roleId.equals(lsCustomGuilds.lsGuildSnuffMemberKey) || roleId.equals(lsCustomGuilds.lsGuildSnuffNewMemberKey)){
                    addRole=roleId; isOK=true;break;
                }
            }
            logger.info(cName + fName + ".branching");
            if(!isOK){
                logger.info(cName + fName + ".no valid role to add> check if they have a valid role");
                checkIfMemberHasValidRole(guild,member);
            }else{
                if(result==null||result.isEmpty()){
                    logger.info(cName + fName + ".found valid role>insert");
                    data.put("guild_id",guild.getId());
                    data.put("user_id",member.getId());
                    data.put("role_id",addRole);
                    rlGlobal.sql.insert(table,data);
                }else{
                    logger.info(cName + fName + ".found valid role>update");
                    data.put("role_id",addRole);
                    rlGlobal.sql.update(table,data," guild_id='"+guild.getId()+"' and user_id='"+member.getId()+"'");
                }
            }
        }
        private void dowork(GuildMemberRoleRemoveEvent event) {
            if (event == null) return;
            String fName = "[GuildMemberRoleRemoveEvent]";
            logger.info(cName + fName);
            Guild guild=event.getGuild();
            if(!isExist(guild)){
                createDefaultEntry(guild);
            }
            if(!isEnabled(guild)){
                logger.warn(cName + fName + ".not enabled!");return;
            }
            Member member = event.getMember();
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "member:" + member.getId() + "|" + member.getUser().getName());
            if (!rlGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }

            List<Role> roles_update = event.getRoles();
            logger.info(cName + fName + "roles_update.size=" +roles_update.size());
            for (Role role : roles_update) {
                logger.info(cName + fName + "role_update:" + role.getId() + "|" + role.getName());
            }
            checkIfMemberHasValidRole(guild, member);
        }
        private void dowork(GuildBanEvent event) {
            if (event == null) return;
            String fName = "[GuildBanEvent]";
            logger.info(cName + fName);
            Guild guild=event.getGuild();
            if(!isExist(guild)){
                createDefaultEntry(guild);
            }
            if(!isEnabled(guild)){
                logger.warn(cName + fName + ".not enabled!");return;
            }
            User user = event.getUser();
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "user:" + user.getId() + "|" + user.getName());
            if (!rlGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            Map<String, Object> result = checkIfmemberHasAnEntry(guild.getId(), user.getId());
            if(result!=null&&!result.isEmpty()){
                logger.info(cName + fName + "has persistencerole > DELETE");
                rlGlobal.sql.delete(table," guild_id='"+guild.getId()+"' and user_id='"+user.getId()+"'");
            }
        }
        private void dowork(GuildMemberJoinEvent event) {
            if (event == null) return;
            String fName = "[GuildMemberJoinEvent]";
            logger.info(cName + fName);
            Guild guild=event.getGuild();
            if(!isExist(guild)){
                createDefaultEntry(guild);
            }
            if(!isEnabled(guild)){
                logger.warn(cName + fName + ".not enabled!");return;
            }
            User user = event.getUser();
            Member member= event.getMember();
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "user:" + user.getId() + "|" + user.getName());
            if (!rlGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }
            Map<String, Object> result = checkIfmemberHasAnEntry(guild.getId(),user.getId());
            if(result==null||result.isEmpty()) {
                logger.info(cName + fName + ".result=0");
            }else{
                logger.info(cName + fName + ".result=1");
                String roleId=result.get("role_id").toString();
                logger.info(cName + fName + "roleId="+roleId);
                boolean isOK=false;
                switch (roleId) {
                    case lsCustomGuilds.lsGuildAsylumMemberKey:
                    case lsCustomGuilds.lsGuildAsylumNewMemberKey:
                        logger.info(cName + fName + ".found aslyum key");
                        isOK = true;
                        break;
                    case lsCustomGuilds.lsGuildBdsmMemberKey:
                    case lsCustomGuilds.lsGuildBdsmNewMemberKey:
                        logger.info(cName + fName + ".found bdsm key");
                        isOK = true;
                        break;
                    case lsCustomGuilds.lsGuildChastityMemberKey:
                    case lsCustomGuilds.lsGuildChastityNewMemberKey:
                        logger.info(cName + fName + ".found chastity key");
                        isOK = true;
                        break;
                    case lsCustomGuilds.lsGuildPrisonMemberKey:
                    case lsCustomGuilds.lsGuildPrisonNewMemberKey:
                        logger.info(cName + fName + ".found prison key");
                        isOK = true;
                        break;
                    case lsCustomGuilds.lsGuildSnuffMemberKey:
                    case lsCustomGuilds.lsGuildSnuffNewMemberKey:
                        logger.info(cName + fName + ".found snuff key");
                        isOK = true;
                        break;
                }
                logger.info(cName + fName + ".isOK="+isOK);
                if(isOK){
                    logger.info(cName + fName + ".normal user");
                    Role role=event.getGuild().getRoleById(roleId);
                    if(role==null){return;}
                    logger.info(cName + fName + ".role: id="+role.getId()+"| name="+role.getName()+" |position="+role.getPosition()+" |guildName="+role.getGuild().getName());
                    logger.warn(cName + fName + "add role "+role.getId()+"| name="+role.getName()+" to "+member.getId()+" | "+user.getName());
                    guild.addRoleToMember(member,role).queue();
                }
            }
        }
        private void checkIfMemberHasValidRole(Guild guild, Member member){
            String fName = "[checkIfMemberHasValidRole]";
            List<Role> roles_has = member.getRoles();
            logger.info(cName + fName + "roles_has:" + roles_has.size());
            boolean isOK=false; String hasRole="";
            label:
            for (Role role : roles_has) {
                String roleId=role.getId();
                logger.info(cName + fName + "role_has:" + roleId + "|" + role.getName());
                switch (roleId) {
                    case lsCustomGuilds.lsGuildAsylumMemberKey:
                    case lsCustomGuilds.lsGuildAsylumNewMemberKey:
                        logger.info(cName + fName + ".found aslyum key");
                        hasRole = roleId;
                        isOK = true;
                        break label;
                    case lsCustomGuilds.lsGuildBdsmMemberKey:
                    case lsCustomGuilds.lsGuildBdsmNewMemberKey:
                        logger.info(cName + fName + ".found bdsm key");
                        hasRole = roleId;
                        isOK = true;
                        break label;
                    case lsCustomGuilds.lsGuildChastityMemberKey:
                    case lsCustomGuilds.lsGuildChastityNewMemberKey:
                        logger.info(cName + fName + ".found chastity key");
                        hasRole = roleId;
                        isOK = true;
                        break label;
                    case lsCustomGuilds.lsGuildPrisonMemberKey:
                    case lsCustomGuilds.lsGuildPrisonNewMemberKey:
                        logger.info(cName + fName + ".found prison key");
                        hasRole = roleId;
                        isOK = true;
                        break label;
                    case lsCustomGuilds.lsGuildSnuffMemberKey:
                    case lsCustomGuilds.lsGuildSnuffNewMemberKey:
                        logger.info(cName + fName + ".found snuff key");
                        hasRole = roleId;
                        isOK = true;
                        break label;
                }
            }

            Map<String, Object> result = checkIfmemberHasAnEntry(guild.getId(), member.getId());
            Map<String, Object> data = new LinkedHashMap<>();
            logger.info(cName + fName + ".branching");
            logger.info(cName + fName + ".hasRole="+hasRole);
            logger.info(cName + fName + ".isOK="+isOK);
            if(result==null||result.isEmpty()){
                logger.info(cName + fName + ".result=0");
                if(isOK){
                    logger.info(cName + fName + ".found valid role>insert");
                    data.put("guild_id",guild.getId());
                    data.put("user_id",member.getId());
                    data.put("role_id",hasRole);
                    rlGlobal.sql.insert(table,data);
                }
            }else{
                logger.info(cName + fName + ".result="+result.get("role_id"));
                if(isOK){
                    if(hasRole.equals(result.get("role_id").toString())){
                        logger.info(cName + fName + ".found same valid role>ignore");
                    }else{
                        logger.info(cName + fName + ".found valid role>update");
                        data.put("role_id",hasRole);
                        rlGlobal.sql.update(table,data," guild_id='"+guild.getId()+"' and user_id='"+member.getId()+"'");
                    }
                }else{
                    logger.info(cName + fName + ".no valid role they have>delete");
                    rlGlobal.sql.delete(table," guild_id='"+guild.getId()+"' and user_id='"+member.getId()+"'");
                }
            }
        }
        private Map checkIfmemberHasAnEntry(String guild, String user){
            String fName = "[checkIfmemberHasAnEntry]";
            List<String> col = new ArrayList<>();
            logger.info(cName + fName + ".guild="+guild+"| user="+user);
            col.add("role_id");
            Map<String, Object> result = rlGlobal.sql.selectFirst(table, " guild_id = '" + guild + "' and user_id ='" + user + "'", col);
            if(result==null||result.isEmpty()){
                logger.info(cName + fName + ".result=false");
            }else{
                logger.info(cName + fName + ".result=true");
            }
            return result;
        }
        private Boolean isExist(Guild guild){
            String fName = "[isExist]";
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "global.guildsSettings="+ rlGlobal.guildsSettingsJson.toString());
            if(!rlGlobal.guildsSettingsJson.has(guild.getId())){
                logger.error(cName + fName + "invalid guild");return false;
            }
            JSONObject jsonGuild= rlGlobal.guildsSettingsJson.getJSONObject(guild.getId());
            if(!jsonGuild.has(fieldPersistencerole)){
                logger.error(cName + fName + "no field");return false;
            }
            JSONObject jsonPersistenceRole=jsonGuild.getJSONObject(fieldPersistencerole);
            if(!jsonPersistenceRole.has(keyPersistencerolEnablede)){
                logger.error(cName + fName + "no entry");return false;
            }
            return true;
        }
        private Boolean createDefaultEntry(Guild guild){
            String fName = "[isEnable]";
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "global.guildsSettings="+ rlGlobal.guildsSettingsJson.toString());
            lcJSONGuildProfile entry=new lcJSONGuildProfile(rlGlobal,fieldPersistencerole,guild);
            entry.jsonObject.put(keyPersistencerolEnablede,false);
            if(entry.saveProfile(llv2_GuildsSettings)){
                logger.info(cName + fName + "saved default entry");
                return true;
            }
            logger.error(cName + fName + "failed to create default entry");return false;
        }
        private Boolean isEnabled(Guild guild){
            String fName = "[isEnable]";
            logger.info(cName + fName + "guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + "global.guildsSettings="+ rlGlobal.guildsSettingsJson.toString());
            if(!rlGlobal.guildsSettingsJson.has(guild.getId())){
                logger.error(cName + fName + "invalid guild");return false;
            }
            JSONObject jsonGuild= rlGlobal.guildsSettingsJson.getJSONObject(guild.getId());
            if(!jsonGuild.has(fieldPersistencerole)){
                logger.error(cName + fName + "no field");return false;
            }
            JSONObject jsonPersistenceRole=jsonGuild.getJSONObject(fieldPersistencerole);
            if(!jsonPersistenceRole.has(keyPersistencerolEnablede)){
                logger.error(cName + fName + "no entry");return false;
            }
            Boolean result=jsonPersistenceRole.getBoolean("enabled");
            logger.info(cName + fName + "result:" + result);
            return result;
        }
    }


}