package models.ls;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface lsPermission {
    static void lsSetPermissionOverride(PermissionOverride permissionOverride, Permission perm, int mode){
        String fName="[llSetPermissionOverride]";
        Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".permissionOverride.id=" +  permissionOverride.getId());
            logger.info(fName+".permissionOverride.guild=" +  permissionOverride.getGuild().getId());
            logger.info(fName+".permissionOverride.role=" +  permissionOverride.getRole());
            logger.info(fName+".permissionOverride.member=" +  permissionOverride.getMember());
            logger.info(fName+".perm=" +  perm.getName());
            logger.info(fName+".mode=" + mode);
            if(mode==1){
                permissionOverride.getManager().setAllow(perm).queue();
            }
            if(mode==0){
                permissionOverride.getManager().clear(perm).queue();
            }
            if(mode==-1){
                permissionOverride.getManager().setDeny(perm).queue();
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static boolean lsSetPermissionOverrideStatus(PermissionOverride permissionOverride, Permission perm, int mode){
        String fName="[llSetPermissionOverride]";
        Logger logger = Logger.getLogger(fName);
        try{
            return lsSetPermissionOverrideResponse(permissionOverride,perm,mode)!=null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static PermissionOverride lsSetPermissionOverrideResponse(PermissionOverride permissionOverride, Permission perm, int mode){
        String fName="[llSetPermissionOverride]";
        Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".permissionOverride.id=" +  permissionOverride.getId());
            logger.info(fName+".permissionOverride.guild=" +  permissionOverride.getGuild().getId());
            logger.info(fName+".permissionOverride.role=" +  permissionOverride.getRole());
            logger.info(fName+".permissionOverride.member=" +  permissionOverride.getMember());
            logger.info(fName+".perm=" +  perm.getName());
            logger.info(fName+".mode=" + mode);
            if(mode==1){
                return permissionOverride.getManager().setAllow(perm).complete();
            }
            if(mode==0){
                return permissionOverride.getManager().clear(perm).complete();
            }
            if(mode==-1){
                return permissionOverride.getManager().setDeny(perm).complete();
            }
            return null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    static boolean lsIsPermissionValid4Channel(Permission permission,TextChannel textChannel){
        boolean result=false;
        switch (permission){
            case MESSAGE_ADD_REACTION:
            case MESSAGE_ATTACH_FILES:
            case MESSAGE_EMBED_LINKS:
            case MESSAGE_EXT_EMOJI:
            case MESSAGE_HISTORY:
            case MESSAGE_MANAGE:
            case MESSAGE_MENTION_EVERYONE:
            case MESSAGE_READ:
            case MESSAGE_TTS:
            case MESSAGE_WRITE:
                result=true;
                break;
        }
        return result;
    }
    static boolean lsIsPermissionValid4Channel(Permission permission, VoiceChannel voiceChannel){
        boolean result=false;
        switch (permission){
            case VIEW_CHANNEL:
            case VOICE_CONNECT:
            case VOICE_DEAF_OTHERS:
            case VOICE_MOVE_OTHERS:
            case VOICE_MUTE_OTHERS:
            case VOICE_SPEAK:
            case VOICE_STREAM:
            case VOICE_USE_VAD:
            case PRIORITY_SPEAKER:
                result=true;
                break;
        }
        return result;
    }
    static boolean lsIsPermissionValid4TextChannel(Permission permission){
        boolean result=false;
        switch (permission){
            case MESSAGE_ADD_REACTION:
            case MESSAGE_ATTACH_FILES:
            case MESSAGE_EMBED_LINKS:
            case MESSAGE_EXT_EMOJI:
            case MESSAGE_HISTORY:
            case MESSAGE_MANAGE:
            case MESSAGE_MENTION_EVERYONE:
            case MESSAGE_READ:
            case MESSAGE_TTS:
            case MESSAGE_WRITE:
                result=true;
                break;
        }
        return result;
    }
    static boolean lsIsPermissionValid4VoiceChannel(Permission permission){
        boolean result=false;
        switch (permission){
            case VIEW_CHANNEL:
            case VOICE_CONNECT:
            case VOICE_DEAF_OTHERS:
            case VOICE_MOVE_OTHERS:
            case VOICE_MUTE_OTHERS:
            case VOICE_SPEAK:
            case VOICE_STREAM:
            case VOICE_USE_VAD:
            case PRIORITY_SPEAKER:
                result=true;
                break;
        }
        return result;
    }
    static boolean lsIsPermissionValidNonChannel(Permission permission){
        boolean result=false;
        switch (permission){
            case ADMINISTRATOR:
            case VIEW_AUDIT_LOGS:
            case MANAGE_SERVER:
            case MANAGE_CHANNEL:
            case MANAGE_EMOTES:
            case MANAGE_PERMISSIONS:
            case MANAGE_WEBHOOKS:
            case CREATE_INSTANT_INVITE:
            case NICKNAME_CHANGE:
            case NICKNAME_MANAGE:
            case MANAGE_ROLES:
            case KICK_MEMBERS:
            case BAN_MEMBERS:
                result=true;
                break;
        }
        return result;
    }
    static Permission lsIsPermission4Name(String name){
        if(Permission.MESSAGE_ADD_REACTION.getName().equalsIgnoreCase(name))return Permission.MESSAGE_ADD_REACTION;
        if(Permission.MESSAGE_ATTACH_FILES.getName().equalsIgnoreCase(name))return Permission.MESSAGE_ATTACH_FILES;
        if(Permission.MESSAGE_EMBED_LINKS.getName().equalsIgnoreCase(name))return Permission.MESSAGE_EMBED_LINKS;
        if(Permission.MESSAGE_EXT_EMOJI.getName().equalsIgnoreCase(name))return Permission.MESSAGE_EXT_EMOJI;
        if(Permission.MESSAGE_HISTORY.getName().equalsIgnoreCase(name))return Permission.MESSAGE_HISTORY;
        if(Permission.MESSAGE_MANAGE.getName().equalsIgnoreCase(name))return Permission.MESSAGE_MANAGE;
        if(Permission.MESSAGE_MENTION_EVERYONE.getName().equalsIgnoreCase(name))return Permission.MESSAGE_MENTION_EVERYONE;
        if(Permission.MESSAGE_READ.getName().equalsIgnoreCase(name))return Permission.MESSAGE_READ;
        if(Permission.MESSAGE_TTS.getName().equalsIgnoreCase(name))return Permission.MESSAGE_TTS;
        if(Permission.MESSAGE_WRITE.getName().equalsIgnoreCase(name))return  Permission.MESSAGE_WRITE;
        return null;
    }
}
