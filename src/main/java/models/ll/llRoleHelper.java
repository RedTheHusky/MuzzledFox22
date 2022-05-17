package models.ll;

import models.llGlobalHelper;
import models.ls.lsChannelHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.log4j.Logger;

import java.util.*;

public interface llRoleHelper 
{
    default boolean llHasRole(Member member, Role role) {
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llHasRole";
        try{
            for (Role memberRole : member.getRoles()) {
                if (memberRole.getId().equals(role.getId())) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default Role llGetFirstRolesByName(Guild guild, String str, Boolean ignoreCase){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetFirstRolesByName";
        try{
            return guild.getRolesByName(str, ignoreCase).get(0);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default List<Role> llGetRolesByName(Guild guild, String str, Boolean ignoreCase){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRolesByName";
        try{
            return guild.getRolesByName(str, ignoreCase);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default Role llGetRoleByID(Guild guild, String str){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRoleByID";
        try{
            return guild.getRoleById(str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default String llGetRoleMentionByID(Guild guild, String str){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRoleMentionByID";
        try{
            return guild.getRoleById(str).getAsMention();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default String llGetRoleMentionByID(Guild guild, long id){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRoleMentionByID";
        try{
            return guild.getRoleById(id).getAsMention();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default String llGetRoleMention(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="";
        try{
            return role.getAsMention();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default List<String> llGetRolesMention(List<Role> roles){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRoleMention";
        try{
            List<String>mentions=new LinkedList<>();
            if(roles.isEmpty()||roles.size()==0)return mentions;
            for (Role role : roles) {
                mentions.add(role.getAsMention());
            }
            return mentions;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default Role llGetRoleByID(Guild guild, long id){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGetRoleByID";
        try{
            return guild.getRoleById(id);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default boolean llRoleHasPermission(Role role, Permission permission){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasPermission";
        try{
            return role.hasPermission(permission);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasOnePermissions(Role role, List<Permission> permissions){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasOnePermissions";
        try{
            if(permissions.isEmpty()||permissions.size()==0)return false;
            for (Permission permission : permissions) {
               if(role.hasPermission(permission))return true;
            }
            return false;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasAllPermissions(Role role, List<Permission> permissions){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasAllPermissions";
        try{
            if(permissions.isEmpty()||permissions.size()==0)return false;
            for (Permission permission : permissions) {
                if(!role.hasPermission(permission))return false;
            }
            return true;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasStaffRights(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasStaffRights";
        try{
            return role.hasPermission(Permission.ADMINISTRATOR) ||
                    role.hasPermission(Permission.VIEW_AUDIT_LOGS) ||
                    role.hasPermission(Permission.MESSAGE_MANAGE) ||
                    role.hasPermission(Permission.MANAGE_CHANNEL) ||
                    role.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                    role.hasPermission(Permission.MANAGE_EMOTES) ||
                    role.hasPermission(Permission.MANAGE_ROLES) ||
                    role.hasPermission(Permission.MANAGE_SERVER) ||
                    role.hasPermission(Permission.MANAGE_WEBHOOKS) ||
                    role.hasPermission(Permission.BAN_MEMBERS) ||
                    role.hasPermission(Permission.KICK_MEMBERS) ||
                    role.hasPermission(Permission.NICKNAME_MANAGE);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasModeratorRights(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasModeratorRights";
        try{
            return role.hasPermission(Permission.ADMINISTRATOR) ||
                    role.hasPermission(Permission.BAN_MEMBERS) ||
                    role.hasPermission(Permission.KICK_MEMBERS);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleIsOrange(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        //https://discordapi.com/permissions.html#0
        String fName="llRoleIsOrange.";
        try{

            if(role.hasPermission(Permission.ADMINISTRATOR)||
                    role.hasPermission(Permission.MESSAGE_MANAGE)||
                    role.hasPermission(Permission.MANAGE_CHANNEL)||
                    role.hasPermission(Permission.MANAGE_PERMISSIONS)||
                    role.hasPermission(Permission.MANAGE_ROLES)||
                    role.hasPermission(Permission.MANAGE_SERVER)||
                    role.hasPermission(Permission.MANAGE_WEBHOOKS)||
                    role.hasPermission(Permission.BAN_MEMBERS)||
                    role.hasPermission(Permission.KICK_MEMBERS)
            ){
                System.out.println("@"+fName+"is true");
                return true;
            }
            System.out.println("@"+fName+"is false");
            return false;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasManagerRights(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasManagerRights";
        try{
            return role.hasPermission(Permission.ADMINISTRATOR) ||
                    role.hasPermission(Permission.MANAGE_ROLES)||
                    role.hasPermission(Permission.MANAGE_CHANNEL) ||
                    role.hasPermission(Permission.MANAGE_EMOTES) ||
                    role.hasPermission(Permission.MANAGE_SERVER) ||
                    role.hasPermission(Permission.MANAGE_WEBHOOKS);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean llRoleHasAdminRights(Role role){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llRoleHasAdminRights";
        try{
            return role.hasPermission(Permission.ADMINISTRATOR);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default Role llGuildNewMemberRole(Guild guild){
        Logger logger = Logger.getLogger(llRoleHelper.class);
        String fName="llGuildNewMemberRole";
        try{
            String selectedRole="";
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)){
                selectedRole=lsCustomGuilds.lsGuildBdsmNewMemberKey;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)){
                selectedRole=lsCustomGuilds.lsGuildChastityNewMemberKey;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)){
                selectedRole=lsCustomGuilds.lsGuildSnuffNewMemberKey;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAsylum)){
                selectedRole=lsCustomGuilds.lsGuildAsylumNewMemberKey;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyPrison)){
                selectedRole=lsCustomGuilds.lsGuildPrisonNewMemberKey;
            }
            if(selectedRole.isBlank()){
                return null;
            }
            Role role=llGetRoleByID(guild, selectedRole);
            if(role==null){
                return null;
            }
            return role;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}