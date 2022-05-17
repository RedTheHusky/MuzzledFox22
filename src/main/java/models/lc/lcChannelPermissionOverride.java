package models.lc;

import models.ll.llPermission;
import models.ls.lsChannelHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcChannelPermissionOverride implements llPermission {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcChannelPermissionOverride]";
    private GuildChannel gChannel;private Guild gGuild;private Member gMember;private Role gRole;
    public boolean isAutoDelete =true;String autoCleanup="Cleaning up leftovers";
    public lcChannelPermissionOverride(){
        String fName="[constructor]";
        logger.info(cName+fName);
    }
    public lcChannelPermissionOverride(GuildChannel channel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gChannel=channel;gGuild=channel.getGuild();
        logger.info(cName+fName+"channel:"+gChannel.getId()+"|"+gChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
    }
    public lcChannelPermissionOverride(Category category){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGuild=category.getGuild();
        logger.info(cName+fName+"category:"+category.getId()+"|"+category.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(category.getId());
    }
    public lcChannelPermissionOverride(TextChannel textChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGuild=textChannel.getGuild();
        logger.info(cName+fName+"textchannel:"+textChannel.getId()+"|"+textChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(textChannel.getId());
    }
    public lcChannelPermissionOverride(VoiceChannel voiceChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGuild=voiceChannel.getGuild();
        logger.info(cName+fName+"voicechannel:"+voiceChannel.getId()+"|"+voiceChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(voiceChannel.getId());
    }
    public lcChannelPermissionOverride(Guild guild,String channelID){
        String fName="[constructor]";
        logger.info(cName+fName);
        logger.info(cName+fName+"channelID="+channelID);
        gGuild=guild;
        if(gGuild==null){
            logger.error(cName+fName+"error no guild set");return;
        }
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(channelID);
        if(gChannel==null){
            logger.error(cName+fName+"error no such channel");return;
        }
    }

    public boolean isChannel(){
        String fName="[isChannel]";
        try{
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            logger.info(cName+fName+"is channel");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isTextChannel(){
        String fName="[isTextChannel]";
        try{
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            logger.info(cName+fName+"is channel");
            if(gChannel.getType()!=ChannelType.TEXT){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isVoiceChannel(){
        String fName="[isVoiceChannel]";
        try{
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            logger.info(cName+fName+"is channel");
            if(gChannel.getType()!=ChannelType.VOICE){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            return true;

        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isCategoryChannel(){
        String fName="[isCategoryChannel]";
        try{
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            logger.info(cName+fName+"is channel");
            if(gChannel.getType()!=ChannelType.CATEGORY){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            return true;

        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }

    public boolean isMemberPermissionClear(Member member, Permission permission){
        String fName="[isPermissionClear]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)||result.getDenied().contains(permission)){
                logger.info(cName+fName+"is not clear");return false;
            }
            logger.info(cName+fName+"is clear");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isMemberPermissionOverride(Member member){
        String fName="[isPermissionOverride]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()&&result.getDenied().isEmpty()){
                logger.info(cName+fName+"both empty");return false;
            }
            logger.info(cName+fName+"ovveriden");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isMemberPermissionGranted(Member member){
        String fName="[isPermissionGranted]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isMemberPermissionDenied(Member member){
        String fName="[isPermissionDenied]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isMemberPermissionGranted(Member member, Permission permission){
        String fName="[isPermissionGranted]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isMemberPermissionDenied(Member member, Permission permission){
        String fName="[isPermissionDenied]";
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean hasMemberPermissionOverride(Member member){
        String fName="[hasMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public PermissionOverride getMemberPermissionOverride(Member member){
        String fName="[getMemberPermissionOverrid]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return null;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return null;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return null;
            }
            logger.info(cName+fName+"has permission override");
            return result;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return null;
        }

    }
    public boolean cleanup(PermissionOverride result){
        String fName="[cleanup]";
        //mode -1 restrict 0 none 1 allow
        try{
            if(isAutoDelete &&result.getAllowed().isEmpty()&&result.getDenied().isEmpty()){
                logger.warn(cName+fName+"cleaning up");
                result.delete().reason(autoCleanup).complete();
            }else{
                logger.warn(cName+fName+"no cleaning up");
            }
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public boolean grantMemberPermissionOverride(Member member, Permission permission, String reason){
        String fName="[grantMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantMemberPermissionOverrideStr(Member member, Permission permission, String reason){
        String fName="[grantMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyMemberPermissionOverride(Member member, Permission permission, String reason){
        String fName="[denyMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyMemberPermissionOverrideStr(Member member, Permission permission, String reason){
        String fName="[denyMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearMemberPermissionOverride(Member member, Permission permission, String reason){
        String fName="[clearMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearMemberPermissionOverrideStr(Member member, Permission permission, String reason){
        String fName="[clearMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetMemberPermissionOverride(Member member,  String reason){
        String fName="[resetMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetMemberPermissionOverrideStr(Member member,  String reason){
        String fName="[resetMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowMemberPermissionOverride(Member member,  String reason){
        String fName="[resetAllowMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowMemberPermissionOverrideStr(Member member,  String reason){
        String fName="[resetAllowMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyMemberPermissionOverride(Member member,  String reason){
        String fName="[resetDenyMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyMemberPermissionOverrideStr(Member member,  String reason){
        String fName="[resetDenyMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deleteMemberPermissionOverride(Member member, String reason){
        String fName="[deleteMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deleteMemberPermissionOverrideStr(Member member, String reason){
        String fName="[deleteMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    public boolean grantMemberPermissionOverride(Member member, Permission permission){
        String fName="[grantMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantMemberPermissionOverrideStr(Member member, Permission permission){
        String fName="[grantMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyMemberPermissionOverride(Member member, Permission permission){
        String fName="[denyMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyMemberPermissionOverrideStr(Member member, Permission permission){
        String fName="[denyMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(member) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(member);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(member).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearMemberPermissionOverride(Member member, Permission permission){
        String fName="[clearMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearMemberPermissionOverrideStr(Member member, Permission permission){
        String fName="[clearMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetMemberPermissionOverride(Member member){
        String fName="[resetMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetMemberPermissionOverrideStr(Member member){
        String fName="[resetMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowMemberPermissionOverride(Member member){
        String fName="[resetAllowMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowMemberPermissionOverrideStr(Member member){
        String fName="[resetAllowMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyMemberPermissionOverride(Member member){
        String fName="[resetDenyMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyMemberPermissionOverrideStr(Member member){
        String fName="[resetDenyMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deleteMemberPermissionOverride(Member member){
        String fName="[deleteMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deleteMemberPermissionOverrideStr(Member member){
        String fName="[deleteMemberPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"member:"+member.getId()+"|"+member.getUser().getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(member);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    public boolean isRolePermissionClear(Role role, Permission permission){
        String fName="[isPermissionClear]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)||result.getDenied().contains(permission)){
                logger.info(cName+fName+"is not clear");return false;
            }
            logger.info(cName+fName+"is clear");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRolePermissionOverride(Role role){
        String fName="[isPermissionOverride]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()&&result.getDenied().isEmpty()){
                logger.info(cName+fName+"both empty");return false;
            }
            logger.info(cName+fName+"ovveriden");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRolePermissionGranted(Role role){
        String fName="[isPermissionGranted]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }

            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRolePermissionDenied(Role role){
        String fName="[isPermissionDenied]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRolePermissionGranted(Role role, Permission permission){
        String fName="[isPermissionGranted]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRolePermissionDenied(Role role, Permission permission){
        String fName="[isPermissionDenied]";
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean hasRolePermissionOverride(Role role){
        String fName="[hasRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public PermissionOverride getRolePermissionOverride(Role role){
        String fName="[getRolePermissionOverrid]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return null;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return null;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return null;
            }
            logger.info(cName+fName+"has permission override");
            return result;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return null;
        }

    }

    public boolean grantRolePermissionOverride(Role role, Permission permission, String reason){
        String fName="[grantRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantRolePermissionOverrideStr(Role role, Permission permission, String reason){
        String fName="[grantRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyRolePermissionOverride(Role role, Permission permission, String reason){
        String fName="[denyRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyRolePermissionOverrideStr(Role role, Permission permission, String reason){
        String fName="[denyRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearRolePermissionOverride(Role role, Permission permission, String reason){
        String fName="[clearRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearRolePermissionOverrideStr(Role role, Permission permission, String reason){
        String fName="[clearRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetRolePermissionOverride(Role role,  String reason){
        String fName="[resetRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetRolePermissionOverrideStr(Role role,  String reason){
        String fName="[resetRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowRolePermissionOverride(Role role,  String reason){
        String fName="[resetAllowRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowRolePermissionOverrideStr(Role role,  String reason){
        String fName="[resetAllowRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyRolePermissionOverride(Role role,  String reason){
        String fName="[resetDenyRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyRolePermissionOverrideStr(Role role,  String reason){
        String fName="[resetDenyRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deleteRolePermissionOverride(Role role, String reason){
        String fName="[deleteRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deleteRolePermissionOverrideStr(Role role, String reason){
        String fName="[deleteRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    public boolean grantRolePermissionOverride(Role role, Permission permission){
        String fName="[grantRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantRolePermissionOverrideStr(Role role, Permission permission){
        String fName="[grantRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyRolePermissionOverride(Role role, Permission permission){
        String fName="[denyRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyRolePermissionOverrideStr(Role role, Permission permission){
        String fName="[denyRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if (gChannel.getPermissionOverride(role) != null){
                logger.info(cName+fName+"has permission override");
                result=gChannel.getPermissionOverride(role);
            }else{
                logger.info(cName+fName+"create");
                result=gChannel.createPermissionOverride(role).complete();
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearRolePermissionOverride(Role role, Permission permission){
        String fName="[clearRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearRolePermissionOverrideStr(Role role, Permission permission){
        String fName="[clearRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetRolePermissionOverride(Role role){
        String fName="[resetRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetRolePermissionOverrideStr(Role role){
        String fName="[resetRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowRolePermissionOverride(Role role){
        String fName="[resetAllowRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowRolePermissionOverrideStr(Role role){
        String fName="[resetAllowRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyRolePermissionOverride(Role role){
        String fName="[resetDenyRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyRolePermissionOverrideStr(Role role){
        String fName="[resetDenyRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deleteRolePermissionOverride(Role role){
        String fName="[deleteRolePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deleteRolePermissionOverrideStr(Role role){
        String fName="[deleteRolePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            result=gChannel.getPermissionOverride(role);
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    String fSuccess="success",fFailed2="failed:";
    String fNoPermissionOverride="no permission override",fNotApplied="not applied",fInvalidChannelType="invalid channel type",fNoChannel="no channel",fAlreadySet="already set";
    String fInvalidPermission4Channel="invalid permission for channel",fInvalidPermission4TextChannel="invalid permission for text channel",fInvalidPermission4VoiceChannel="invalid permission for voice channel",fInvalidPermission4CategoryChannel="invalid permission for category channel";

    public lcChannelPermissionOverride(Member member,GuildChannel channel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gMember=member;
        logger.info(cName+fName+"member:"+gMember.getId()+"|"+gMember.getUser().getName()+"#"+gMember.getUser().getDiscriminator());
        gChannel=channel;gGuild=channel.getGuild();
        logger.info(cName+fName+"channel:"+gChannel.getId()+"|"+gChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
    }
    public lcChannelPermissionOverride(Member member,Category category){
        String fName="[constructor]";
        logger.info(cName+fName);
        gMember=member;
        logger.info(cName+fName+"member:"+gMember.getId()+"|"+gMember.getUser().getName()+"#"+gMember.getUser().getDiscriminator());
        gGuild=category.getGuild();
        logger.info(cName+fName+"category:"+category.getId()+"|"+category.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(category.getId());
    }
    public lcChannelPermissionOverride(Member member,TextChannel textChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gMember=member;
        logger.info(cName+fName+"member:"+gMember.getId()+"|"+gMember.getUser().getName()+"#"+gMember.getUser().getDiscriminator());
        gGuild=textChannel.getGuild();
        logger.info(cName+fName+"textchannel:"+textChannel.getId()+"|"+textChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(textChannel.getId());
    }
    public lcChannelPermissionOverride(Member member,VoiceChannel voiceChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gMember=member;
        logger.info(cName+fName+"member:"+gMember.getId()+"|"+gMember.getUser().getName()+"#"+gMember.getUser().getDiscriminator());
        gGuild=voiceChannel.getGuild();
        logger.info(cName+fName+"voicechannel:"+voiceChannel.getId()+"|"+voiceChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(voiceChannel.getId());
    }
    public lcChannelPermissionOverride(Member member,Guild guild,String channelID){
        String fName="[constructor]";
        logger.info(cName+fName);
        logger.info(cName+fName+"channelID="+channelID);
        gMember=member;
        logger.info(cName+fName+"member:"+gMember.getId()+"|"+gMember.getUser().getName()+"#"+gMember.getUser().getDiscriminator());
        gGuild=guild;
        if(gGuild==null){
            logger.error(cName+fName+"error no guild set");return;
        }
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(channelID);
        if(gChannel==null){
            logger.error(cName+fName+"error no such channel");return;
        }
    }

    public lcChannelPermissionOverride(Role role,GuildChannel channel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gRole=role;
        logger.info(cName+fName+"roler:"+gRole.getId()+"|"+gRole.getName());
        gChannel=channel;gGuild=channel.getGuild();
        logger.info(cName+fName+"channel:"+gChannel.getId()+"|"+gChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
    }
    public lcChannelPermissionOverride(Role role,Category category){
        String fName="[constructor]";
        logger.info(cName+fName);
        gRole=role;
        logger.info(cName+fName+"roler:"+gRole.getId()+"|"+gRole.getName());
        gGuild=category.getGuild();
        logger.info(cName+fName+"category:"+category.getId()+"|"+category.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(category.getId());
    }
    public lcChannelPermissionOverride(Role role,TextChannel textChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gRole=role;
        logger.info(cName+fName+"roler:"+gRole.getId()+"|"+gRole.getName());
        gGuild=textChannel.getGuild();
        logger.info(cName+fName+"textchannel:"+textChannel.getId()+"|"+textChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(textChannel.getId());
    }
    public lcChannelPermissionOverride(Role role,VoiceChannel voiceChannel){
        String fName="[constructor]";
        logger.info(cName+fName);
        gRole=role;
        logger.info(cName+fName+"roler:"+gRole.getId()+"|"+gRole.getName());
        gGuild=voiceChannel.getGuild();
        logger.info(cName+fName+"voicechannel:"+voiceChannel.getId()+"|"+voiceChannel.getName());
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(voiceChannel.getId());
    }
    public lcChannelPermissionOverride(Role role,Guild guild,String channelID){
        String fName="[constructor]";
        logger.info(cName+fName);
        logger.info(cName+fName+"channelID="+channelID);
        gRole=role;
        logger.info(cName+fName+"roler:"+gRole.getId()+"|"+gRole.getName());
        gGuild=guild;
        if(gGuild==null){
            logger.error(cName+fName+"error no guild set");return;
        }
        logger.info(cName+fName+"guild:"+gGuild.getId()+"|"+gGuild.getName());
        gChannel=gGuild.getGuildChannelById(channelID);
        if(gChannel==null){
            logger.error(cName+fName+"error no such channel");return;
        }
    }

    public boolean isMemberProvided(){
        String fName="[isMemberProvided]";
        try{
            if(gMember==null){
                logger.warn(cName+fName+"no member provided");return false;
            }
            logger.info(cName+fName+"gMember:"+gMember.getId()+"|"+gMember.getUser().getName());
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isRoleProvided(){
        String fName="[isRoleProvided]";
        try{
            if(gRole==null){
                logger.warn(cName+fName+"no member provided");return false;
            }
            logger.info(cName+fName+"gRole:"+gRole.getId()+"|"+gRole.getName());
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionClear( Permission permission){
        String fName="[isPermissionClear]";
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)||result.getDenied().contains(permission)){
                logger.info(cName+fName+"is not clear");return false;
            }
            logger.info(cName+fName+"is clear");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionOverride(){
        String fName="[isPermissionOverride]";
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()&&result.getDenied().isEmpty()){
                logger.info(cName+fName+"both empty");return false;
            }
            logger.info(cName+fName+"ovveriden");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionGranted(){
        String fName="[isPermissionGranted]";
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionDenied(){
        String fName="[isPermissionDenied]";
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().isEmpty()){
                logger.info(cName+fName+"is empty");return false;
            }
            logger.info(cName+fName+"has override");return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionGranted( Permission permission){
        String fName="[isPermissionGranted]";
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean isPermissionDenied( Permission permission){
        String fName="[isPermissionDenied]";
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.info(cName+fName+"has this permission");return true;
            }
            logger.info(cName+fName+"has not this permission");return false;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }
    }
    public boolean hasPermissionOverride(){
        String fName="[hasPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public PermissionOverride getPermissionOverride(){
        String fName="[getPermissionOverrid]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return null;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return null;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return null;
            }
            logger.info(cName+fName+"has permission override");
            return result;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return null;
        }

    }

    public boolean grantPermissionOverride( Permission permission, String reason){
        String fName="[grantPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }

            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantPermissionOverrideStr( Permission permission, String reason){
        String fName="[grantPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyPermissionOverride( Permission permission, String reason){
        String fName="[denyPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyPermissionOverrideStr( Permission permission, String reason){
        String fName="[denyPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearPermissionOverride( Permission permission, String reason){
        String fName="[clearPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearPermissionOverrideStr( Permission permission, String reason){
        String fName="[clearPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetPermissionOverride(  String reason){
        String fName="[resetPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetPermissionOverrideStr(  String reason){
        String fName="[resetPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowPermissionOverride(  String reason){
        String fName="[resetAllowPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowPermissionOverrideStr(  String reason){
        String fName="[resetAllowPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyPermissionOverride(  String reason){
        String fName="[resetDenyPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyPermissionOverrideStr(  String reason){
        String fName="[resetDenyPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().reason(reason).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deletePermissionOverride( String reason){
        String fName="[deletePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deletePermissionOverrideStr( String reason){
        String fName="[deletePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"reason="+reason);
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().reason(reason).complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    public boolean grantPermissionOverride( Permission permission){
        String fName="[grantPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().grant(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String grantPermissionOverrideStr( Permission permission){
        String fName="[grantPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean denyPermissionOverride( Permission permission){
        String fName="[denyPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String denyPermissionOverrideStr( Permission permission){
        String fName="[denyPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gMember).complete();
                }
            }else{
                result=gChannel.getPermissionOverride(gRole);
                if (result!= null){
                    logger.info(cName+fName+"has permission override");
                }else{
                    logger.info(cName+fName+"create");
                    result=gChannel.createPermissionOverride(gRole).complete();
                }
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(result.getDenied().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return fFailed2+fAlreadySet;
            }
            result=result.getManager().deny(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean clearPermissionOverride( Permission permission){
        String fName="[clearPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return false;
            }


            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission");return false;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String clearPermissionOverrideStr( Permission permission){
        String fName="[clearPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            logger.info(cName+fName+"permission="+permission.getName());
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            if(gChannel.getType()==ChannelType.TEXT&&!llIsPermissionValid4TextChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4TextChannel;
            }else
            if(gChannel.getType()==ChannelType.VOICE&&!llIsPermissionValid4VoiceChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4VoiceChannel;
            }else
            if(gChannel.getType()==ChannelType.CATEGORY&&!llIsPermissionValid4CategoryChannel(permission)){
                logger.warn(cName+fName+"permission not valid for this type of channel");return fFailed2+fInvalidPermission4CategoryChannel;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                logger.warn(cName+fName+"already has this permission"); return fFailed2+fAlreadySet;
            }
            result=result.getManager().clear(permission).complete();
            if(result==null){
                logger.warn(cName+fName+"result is null"); return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            cleanup(result);
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }
    public boolean resetPermissionOverride(){
        String fName="[resetPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetPermissionOverrideStr(){
        String fName="[resetPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().reset().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetAllowPermissionOverride(){
        String fName="[resetAllowPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetAllowPermissionOverrideStr(){
        String fName="[resetAllowPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetAllow().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean resetDenyPermissionOverride(){
        String fName="[resetDenyPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return false;
            }
            logger.info(cName+fName+"result ok");
            return true;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return false;
        }

    }
    public String resetDenyPermissionOverrideStr(){
        String fName="[resetDenyPermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel"); return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            result=result.getManager().resetDeny().complete();
            if(result==null){
                logger.warn(cName+fName+"result is null");return fFailed2+fNotApplied;
            }
            logger.info(cName+fName+"result ok");
            return fSuccess;
        } catch (Exception e) {
            logger.warn(cName+fName+"exception="+e);
            return fFailed2+e.toString();
        }

    }
    public boolean deletePermissionOverride(){
        String fName="[deletePermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return false;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return false;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return false;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public String deletePermissionOverrideStr(){
        String fName="[deletePermissionOverrideStr]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(gChannel==null){
                logger.info(cName+fName+"no channel");
                return fFailed2+fNoChannel;
            }
            if(!lsChannelHelper.lsIsGuildChannel(gChannel)){
                logger.warn(cName+fName+"invalid type of channel");return fFailed2+fInvalidChannelType;
            }
            if(gMember!=null){
                result=gChannel.getPermissionOverride(gMember);
            }else{
                result=gChannel.getPermissionOverride(gRole);
            }
            if(result==null){
                logger.warn(cName+fName+"has no override");return fFailed2+fNoPermissionOverride;
            }
            logger.info(cName+fName+"has permission override");
            result.delete().complete();
            logger.info(cName+fName+"deleted");
            return fSuccess;
        } catch (Exception e) {
            logger.error(cName+fName+"exception="+e);
            logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return fFailed2+e.toString();
        }

    }

    
    
}
