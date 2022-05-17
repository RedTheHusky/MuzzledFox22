package models.ls;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface lsRoleHelper 
{
    static boolean lsHasRole(Member member, Role role) {
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsHasRole";
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

    static Role lsGetFirstRolesByName(Guild guild, String str, Boolean ignoreCase){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetFirstRolesByName";
        try{
            return guild.getRolesByName(str, ignoreCase).get(0);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<Role> lsGetRolesByName(Guild guild, String str, Boolean ignoreCase){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRolesByName";
        try{
            return guild.getRolesByName(str, ignoreCase);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Role lsGetRole(Guild guild, String str){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRole";
        try{
            logger.info(fName + ".str=" + str);
            try {
                str = str.replaceAll("<@&", "").replaceAll("<", "").replaceAll("@", "").replaceAll("&", "").replaceAll(">", "").replaceAll("!", "");
                Role role=guild.getRoleById(str);
                if(role!=null)return role;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                List<Role>roles=guild.getRolesByName(str,false);
                if(!roles.isEmpty()){
                    return  roles.get(0);
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Role lsGetRoleByID(Guild guild, String str){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleByID";
        try{
            logger.info(fName + ".str=" + str);
            str=str.replaceAll("<@&","").replaceAll("<","").replaceAll("@","").replaceAll("&","").replaceAll(">","");
            return guild.getRoleById(str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Role lsGetRoleByID(Guild guild, long id){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleByID";
        try{
            return guild.getRoleById(id);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String lsGetRoleMentionByID(Guild guild, String str){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleMentionByID";
        try{
            str=str.replaceAll("<@&","").replaceAll("<","").replaceAll("@","").replaceAll("&","").replaceAll(">","");
            return guild.getRoleById(str).getAsMention();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetRoleMentionByID(Guild guild, long id){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleMentionByID";
        try{
            return guild.getRoleById(id).getAsMention();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetRoleMention(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
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
    static List<String> lsGetRolesMention(List<Role> roles){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleMention";
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

    static List<String> lsGetRolesMentionAsList(List<Role>roles){
        String fName="lsGetRolesMentionAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            List<String>result=new ArrayList<>();
            for(Role role:roles){
                try {
                    result.add(role.getAsMention());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return new ArrayList<>();
        }
    }
    static List<String> lsGetRolesNameAsList(List<Role>roles){
        String fName="lsGetRolesNameAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            List<String>result=new ArrayList<>();
            for(Role role:roles){
                try {
                    result.add(role.getName());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return new ArrayList<>();
        }
    }
    static String lsGetRolesMentionAsString(List<Role>roles,String divider){
        String fName="lsGetRolesMentionAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider);
            StringBuilder result= new StringBuilder();
            for(Role role:roles){
                try {
                    if(result.length()!=0){
                        result.append(divider).append(role.getAsMention());
                    }else{
                        result = new StringBuilder(role.getAsMention());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return "";
        }
    }
    static String lsGetRolesNameAsString(List<Role>roles,String divider){
        String fName="lsGetRolesNameAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider);
            StringBuilder result= new StringBuilder();
            for(Role role:roles){
                try {
                    if(result.length()!=0){
                        result.append(divider).append(role.getAsMention());
                    }else{
                        result = new StringBuilder(role.getAsMention());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return "";
        }
    }

    static List<String> lsGetRolesMentionAsList(List<Long>ids,Guild guild){
        String fName="lsGetRolesMentionAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"guild.id="+guild.getId());
            List<String>result=new ArrayList<>();
            for(Long id:ids){
                try {
                    Role role=guild.getRoleById(id);
                    if(role!=null){
                        result.add(role.getAsMention());
                    }else{
                        result.add("<"+id+">");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    result.add("<"+id+">");
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return new ArrayList<>();
        }
    }
    static List<String> lsGetRolesNameAsList(List<Long>ids,Guild guild){
        String fName="lsGetRolesNameAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"guild.id="+guild.getId());
            List<String>result=new ArrayList<>();
            for(Long id:ids){
                try {
                    Role role=guild.getRoleById(id);
                    if(role!=null){
                        result.add(role.getName());
                    }else{
                        result.add("<"+id+">");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    result.add("<"+id+">");
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return new ArrayList<>();
        }
    }
    static String lsGetRolesMentionAsString(List<Long>ids,String divider,Guild guild){
        String fName="lsGetRolesMentionAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider+", guild.id="+guild.getId());
            StringBuilder result= new StringBuilder();
            for(Long id:ids){
                try {
                    Role role=guild.getRoleById(id);
                    if(role!=null){
                        if(result.length()!=0){
                            result.append(divider).append(role.getAsMention());
                        }else{
                            result = new StringBuilder(role.getAsMention());
                        }
                    }else{
                        if(result.length()!=0){
                            result.append(divider).append("<"+id+">");
                        }else{
                            result = new StringBuilder("<"+id+">");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    if(result.length()!=0){
                        result.append(divider).append("<"+id+">");
                    }else{
                        result = new StringBuilder("<"+id+">");
                    }
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return "";
        }
    }
    static String lsGetRolesNameAsString(List<Long>ids,String divider,Guild guild){
        String fName="lsGetRolesNameAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider+", guild.id="+guild.getId());
            StringBuilder result= new StringBuilder();
            for(Long id:ids){
                try {
                    Role role=guild.getRoleById(id);
                    if(role!=null){
                        if(result.length()!=0){
                            result.append(divider).append(role.getName());
                        }else{
                            result = new StringBuilder(role.getName());
                        }
                    }else{
                        if(result.length()!=0){
                            result.append(divider).append("<"+id+">");
                        }else{
                            result = new StringBuilder("<"+id+">");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    if(result.length()!=0){
                        result.append(divider).append("<"+id+">");
                    }else{
                        result = new StringBuilder("<"+id+">");
                    }
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return "";
        }
    }

    static String lsGetRoleNameByID(Guild guild, String str){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleNameByID";
        try{
            str=str.replaceAll("<@&","").replaceAll("<","").replaceAll("@","").replaceAll("&","").replaceAll(">","");
            return guild.getRoleById(str).getName();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetRoleNameByID(Guild guild, long id){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleNameByID";
        try{
            return guild.getRoleById(id).getName();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetRoleName(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleName";
        try{
            return role.getName();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<String> lsGetRolesName(List<Role> roles){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGetRoleName";
        try{
            List<String>mentions=new LinkedList<>();
            if(roles.isEmpty()||roles.size()==0)return mentions;
            for (Role role : roles) {
                mentions.add(role.getName());
            }
            return mentions;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static boolean lsRoleHasPermission(Role role, Permission permission){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasPermission";
        try{
            return role.hasPermission(permission);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsRoleHasOnePermissions(Role role, List<Permission> permissions){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasOnePermissions";
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
    static boolean lsRoleHasAlsPermissions(Role role, List<Permission> permissions){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasAlsPermissions";
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
    static boolean lsRoleHasStaffRights(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasStaffRights";
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
    static boolean lsRoleHasModeratorRights(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasModeratorRights";
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
    static boolean lsRoleIsOrange(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        //https://discordapi.com/permissions.html#0
        String fName="lsRoleIsOrange.";
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
    static boolean lsRoleHasManagerRights(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasManagerRights";
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
    static boolean lsRoleHasAdminRights(Role role){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsRoleHasAdminRights";
        try{
            return role.hasPermission(Permission.ADMINISTRATOR);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static Role lsGuildNewMemberRole(Guild guild){
        Logger logger = Logger.getLogger(lsRoleHelper.class);
        String fName="lsGuildNewMemberRole";
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
            Role role=lsGetRoleByID(guild, selectedRole);
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