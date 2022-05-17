package models.ls;

import kong.unirest.json.JSONObject;
import models.llGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public interface lsMemberHelper
{
    static Member lsGetMember(Message eMessage, User user){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return eMessage.getGuild().getMember(user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMember(MessageReceivedEvent event, User user){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return event.getGuild().getMember(user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMember(Guild guild, User user){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=guild.getMember(user);
            if(member!=null){
                return member;
            }
            return null;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetMemberMention(Guild guild, User user){
        String fName="lsGetMember."; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=guild.getMember(user);
            if(member!=null){
                return member.getAsMention();
            }
            return null;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }

    static Member lsGetMember(Guild guild, String value){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName+"value="+value);
            Member member=null;
            try {
                String id=value;
                id=id.replace("<@","").replace("<!@","").replace("<@!","").replace(">","");
                id=id.replace("(@","").replace("(!@","").replace("(@!","").replace(")","");
                id=id.replace("!","");logger.info(fName+"id="+id);
                member=guild.getMemberById(id);
                if(member!=null)return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                member=guild.getMemberByTag(value);
                if(member!=null)return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                List<Member>members=guild.getMembersByName(value,false);
                if(!members.isEmpty()) member=members.get(0);
                if(member!=null)return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                List<Member>members=guild.getMembersByNickname(value,false);
                if(!members.isEmpty()) member=members.get(0);
                if(member!=null)return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                List<Member>members=guild.getMembersByEffectiveName(value,false);
                if(!members.isEmpty()) member=members.get(0);
                if(member!=null)return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return  null;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    static boolean lsIsMember(Guild guild, String value){
        String fName="lsIsMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMember(guild,value)!=null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static String lsGetMemberMention(Guild guild, String value){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMember(guild,value);
            if(member!=null){
                return member.getAsMention();
            }
            return null;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }

    static Member lsGetMemberById(Guild guild, String id){
        String fName="lsGetMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            id=id.replace("<@","").replace("<!@","").replace(">","");
            id=id.replace("(@","").replace("(!@","").replace(")","");
            id=id.replace("!","");logger.info(fName+"id="+id);
            return guild.getMemberById(id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static boolean lsIsMemberById(Guild guild, String id){
        String fName="lsIsMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMemberById(guild, id)!=null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static String lsGetMemberMentionById(Guild guild, String id){
        String fName="lsGetMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getAsMention();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static Member lsGetMemberById(Guild guild, long id){
        String fName="lsGetMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMemberById(id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static boolean lsIsMemberById(Guild guild, long id){
        String fName="lsIsMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMemberById(guild, id)!=null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static String lsGetMemberMentionById(Guild guild, long id){
        String fName="lsGetMemberById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getAsMention();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMember(Guild guild, long id){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMemberById(id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static boolean lsIsMember(Guild guild, long id){
        String fName="lsIsMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMemberById(guild, id)!=null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static String lsGetMemberMention(Guild guild, long id){
        String fName="lsGetMember.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getAsMention();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static List<Member> lsGetMembersByName(Guild guild, String name){
        String fName="lsGetMemberByName.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMembersByName(name,false);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static List<Member> lsGetMembersByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetMemberByName.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMembersByName(name,ignorecase);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMemberByName(Guild guild, String name){
        String fName="lsGetMemberByName.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMemberByName(guild,name,false);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMemberByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetMemberByName.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            List<Member>members= guild.getMembersByName(name,ignorecase);
            if(members.isEmpty())return null;
            return members.get(0);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static List<Member> lsGetMembersByNickname(Guild guild, String nickname){
        String fName="lsGetMemberByNickname.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMembersByNickname(nickname,false);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static List<Member> lsGetMembersByNickname(Guild guild, String nickname,boolean ignorecase){
        String fName="lsGetMemberByNickname.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMembersByNickname(nickname,ignorecase);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMemberByNickname(Guild guild, String nickname){
        String fName="lsGetMemberByNickname.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return lsGetMemberByNickname(guild,nickname,false);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMemberByNickname(Guild guild, String nickname,boolean ignorecase){
        String fName="lsGetMemberByNickname.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            List<Member>members= guild.getMembersByNickname(nickname,ignorecase);
            if(members.isEmpty())return null;
            return members.get(0);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static String lsGetMemberNameById(Guild guild, String id){
        String fName="lsGetMemberNameById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getUser().getName();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetMemberNameById(Guild guild, long id){
        String fName="lsGetMemberNameById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getUser().getName();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetMemberDiscriminatorById(Guild guild, String id){
        String fName="lsGetMemberDiscriminatorById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getUser().getDiscriminator();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetMemberDiscriminatorById(Guild guild, long id){
        String fName="lsGetMemberDiscriminatorById.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Member member=lsGetMemberById(guild,id);
            if(member!=null){
                return member.getUser().getDiscriminator();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static Member lsGetMemberByTag(Guild guild, String tag){
        String fName="lsGetMemberByTag.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMemberByTag(tag);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Member lsGetMemberByTag(Guild guild, String username,String discrimination){
        String fName="lsGetMemberByTag.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return guild.getMemberByTag(username,discrimination);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static boolean lsMemberIsStaff(Member member){
        String fName="lsMemberIsStaff.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            logger.info(fName+"member:"+member.getId()+"|"+member.getEffectiveName());
            return member.isOwner()||member.hasPermission(Permission.ADMINISTRATOR) ||
                    member.hasPermission(Permission.VIEW_AUDIT_LOGS) ||
                    member.hasPermission(Permission.MESSAGE_MANAGE) ||
                    member.hasPermission(Permission.MANAGE_CHANNEL) ||
                    member.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                    member.hasPermission(Permission.MANAGE_EMOTES) ||
                    member.hasPermission(Permission.MANAGE_ROLES) ||
                    member.hasPermission(Permission.MANAGE_SERVER) ||
                    member.hasPermission(Permission.MANAGE_WEBHOOKS) ||
                    member.hasPermission(Permission.BAN_MEMBERS) ||
                    member.hasPermission(Permission.KICK_MEMBERS) ||
                    member.hasPermission(Permission.NICKNAME_MANAGE);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsModerator(Member member){
        String fName="lsMemberIsModerator.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return member.isOwner()||member.hasPermission(Permission.ADMINISTRATOR) ||
                    member.hasPermission(Permission.BAN_MEMBERS) ||
                    member.hasPermission(Permission.KICK_MEMBERS);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsManager(Member member){
        String fName="lsMemberIsManager."; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return member.isOwner()||member.hasPermission(Permission.ADMINISTRATOR) ||
                    member.hasPermission(Permission.MANAGE_CHANNEL) ||
                    member.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                    member.hasPermission(Permission.MANAGE_SERVER) ||
                    member.hasPermission(Permission.MANAGE_WEBHOOKS);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsAdministrator(Member member){
        String fName="lsMemberIsAdministrator."; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return member.isOwner()||member.hasPermission(Permission.ADMINISTRATOR);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsOrange(Member member){
        //https://discordapi.com/permissions.html#0
        String fName="lsMemberIsOrange.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            return member.isOwner()||member.hasPermission(Permission.ADMINISTRATOR) ||
                    member.hasPermission(Permission.MESSAGE_MANAGE) ||
                    member.hasPermission(Permission.MANAGE_CHANNEL) ||
                    member.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                    member.hasPermission(Permission.MANAGE_ROLES) ||
                    member.hasPermission(Permission.MANAGE_SERVER) ||
                    member.hasPermission(Permission.MANAGE_WEBHOOKS) ||
                    member.hasPermission(Permission.BAN_MEMBERS) ||
                    member.hasPermission(Permission.KICK_MEMBERS);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasRole(Member member, long roleID) {
        String fName="lsMemberHasRole.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Role role=member.getGuild().getRoleById(roleID);
            for (Role memberRole : member.getRoles()) {
                assert role != null;
                if (memberRole.getId().equals(role.getId())) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasRole(Member member, String roleID) {
        String fName="lsMemberHasRole.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            Role role=member.getGuild().getRoleById(roleID);
            for (Role memberRole : member.getRoles()) {
                assert role != null;
                if (memberRole.getId().equals(role.getId())) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasRole(Member member, Role role) {
        String fName="lsMemberHasRole."; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            for (Role memberRole : member.getRoles()) {
                if (memberRole.getId().equals(role.getId())) {
                    return true;
                }
            }
            System.out.println("@"+fName+"is false");
            return false;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission(Member member, Permission permission) {
        String fName="lsMemberHasPermission.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(permission);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }

    static boolean lsMemberHasPermission_BAN(Member member) {
        String fName="lsMemberHasPermission_BAN.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.BAN_MEMBERS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_KICK(Member member) {
        String fName="lsMemberHasPermission_KICK.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.KICK_MEMBERS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MANAGECHANNEL(Member member) {
        String fName="lsMemberHasPermission_MANAGE_CHANNEL.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MANAGE_CHANNEL);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MANAGEROLES(Member member) {
        String fName="lsMemberHasPermission_MANAGE_ROLES.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MANAGE_ROLES);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MANAGEEMOTES(Member member) {
        String fName="lsMemberHasPermission_MANAGE_EMOTES.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MANAGE_EMOTES);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MANAGEWEBHOOKS(Member member) {
        String fName="lsMemberHasPermission_MANAGE_WEBHOOKS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MANAGE_WEBHOOKS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MANAGESERVER(Member member) {
        String fName="lsMemberHasPermission_MANAGE_SERVER.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MANAGE_SERVER);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_NICKNAMEMANAGE(Member member) {
        String fName="lsMemberHasPermission_NICKNAME_MANAGE.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.NICKNAME_MANAGE);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VIEWAUDITLOGS(Member member) {
        String fName="lsMemberHasPermission_VIEW_AUDIT_LOGS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VIEW_AUDIT_LOGS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    static boolean lsMemberHasPermission_MESSAGEMANAGE(Member member) {
        String fName="lsMemberHasPermission_MESSAGE_MANAGE.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_MANAGE);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MESSAGEADDREACTION(Member member) {
        String fName="lsMemberHasPermission_MESSAGE_ADD_REACTION.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_ADD_REACTION);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MESSAGEATTACHFILES(Member member) {
        String fName="lsMemberHasPermission_MESSAGE_ATTACH_FILES.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_ATTACH_FILES);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MESSAGEEMBEDLINKS(Member member) {
        String fName="lsMemberHasPermission_MESSAGEEMBEDLINKS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_EMBED_LINKS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MESSAGEEXTEMOJI(Member member) {
        String fName="lsMemberHasPermission_MESSAGE_EXT_EMOJI.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_EXT_EMOJI);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_MESSAGEMENTIONEVERYONE(Member member) {
        String fName="lsMemberHasPermission_MESSAGE_MENTION_EVERYONE.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.MESSAGE_MENTION_EVERYONE);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    static boolean lsMemberHasPermission_VOICEDEAFOTHERS(Member member) {
        String fName="lsMemberHasPermission_VOICE_DEAF_OTHERS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_DEAF_OTHERS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICEMOVEOTHERS(Member member) {
        String fName="lsMemberHasPermission_VOICE_MOVE_OTHERS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_MOVE_OTHERS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICEMUTEOTHERS(Member member) {
        String fName="lsMemberHasPermission_VOICE_MUTE_OTHERS.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_MUTE_OTHERS);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICECONNECT(Member member) {
        String fName="lsMemberHasPermission_VOICE_CONNECT.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_CONNECT);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICESPEAK(Member member) {
        String fName="lsMemberHasPermission_VOICE_SPEAK.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_SPEAK);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICESTREAM(Member member) {
        String fName="lsMemberHasPermission_VOICE_STREAM.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_STREAM);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberHasPermission_VOICEUSEVAD(Member member) {
        String fName="lsMemberHasPermission_VOICE_USE_VAD.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            boolean result=member.hasPermission(Permission.VOICE_USE_VAD);
            logger.info(fName+"result="+ result);
            return result;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    static boolean lsMemberIsBooster(Member member) {
        String fName="lsMemberIsBooster"; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            List<Member> boosters=member.getGuild().getBoosters();
            for(Member booster:boosters){
                if(booster==member||booster.getId().equalsIgnoreCase(member.getId())){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsBooster(Guild guilod,User user) {
        String fName="lsMemberIsBooster"; Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            logger.info(fName);
            List<Member> boosters=guilod.getBoosters();
            for(Member booster:boosters){
                User buser=booster.getUser();
                if(buser==user||booster.getId().equalsIgnoreCase(user.getId())){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsMemberIsBotOwner(Member member){
        String fName="lsMemberIsBotOwner.";Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            //return member.getId().equalsIgnoreCase(llGlobalHelper.llOwnerID);
            switch (member.getId()){
                case "345883700124319744":
                case "805020083201114132":
                    logger.info(fName+"member that should always return false");
                    return false;
            }
            ApplicationInfo applicationInfo=member.getJDA().retrieveApplicationInfo().complete();
            User owner=applicationInfo.getOwner();
            logger.info(fName+"owner.id="+owner.getIdLong());
            logger.info(fName+"owner.id="+owner.getName());
            boolean result=owner.getIdLong()==member.getIdLong();
            if(!result){
                ApplicationTeam team=applicationInfo.getTeam();
                List<TeamMember>teamMembers=team.getMembers();
                for(TeamMember teamMember:teamMembers){
                    if(teamMember.getUser().getIdLong()==member.getIdLong()){
                        result=true;
                        break;
                    }
                }
            }
            logger.info(fName+"result="+result);
            return  result;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static String getAuthorIcon(Member member){
        String fName = "[getAuthorIcon]";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return lsUserHelper.getAuthorIcon(member.getUser());
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }

    static String guildMemberUrl(String guild_id,String user_id){
        String fName="[guildMemberUrl]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return lsDiscordApi.liGetGuildMemberUrl.replaceAll("!GUILD",guild_id).replaceAll("!MEMBER",user_id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberUrl(long guild_id,long user_id){
        String fName="[guildMemberUrl]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return guildMemberUrl(String.valueOf(guild_id),String.valueOf(user_id));
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberUrl(Guild guild, User user){
        String fName="[guildMemberUrl]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return guildMemberUrl(guild.getId(),user.getId());
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberUrl(Member member){
        String fName="[guildMemberUrl]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return guildMemberUrl(member.getGuild().getId(),member.getId());
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberAvatar(String guild_id, String user_id, String avatar_id, int size){
        String fName="[guildMemberAvatar]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            String str= lsDiscordApi.liGetGuildMemberAvatarUrl.replaceAll("!GUILD",guild_id).replaceAll("!USER",user_id).replaceAll("!AVATAR",avatar_id);
            if(size>0){
                str+="size="+size;
            }
            if(lsDiscordApi.isIdAnimated(avatar_id)){
                str=str.replaceAll("!EXT","gif");
            }else{
                str=str.replaceAll("!EXT","png");
            }
            return str;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberAvatar(Member member,String avatar_id, int size){
        String fName="[guildMemberAvatar]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return guildMemberAvatar(member.getGuild().getId(),member.getId(),avatar_id,size);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberAvatar(String guild_id, String user_id, String avatar_id){
        String fName="[guildMemberAvatar]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try {
            return guildMemberAvatar(guild_id, user_id, avatar_id, 0);
        }catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String guildMemberAvatar(Member member,String avatar_id){
        String fName="[guildMemberAvatar]";
        Logger logger = Logger.getLogger(lsMemberHelper.class);
        try{
            return guildMemberAvatar(member.getGuild().getId(),member.getId(),avatar_id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}