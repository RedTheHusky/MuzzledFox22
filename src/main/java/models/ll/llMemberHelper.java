package models.ll;

import models.ls.lsMemberHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.log4j.Logger;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public interface llMemberHelper
{
    default Member llGetMember(Message eMessage, User user){
        String fName="llGetMember.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMember(eMessage,user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default Member llGetMember(MessageReceivedEvent event, User user){
        String fName="llGetMember.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMember(event, user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default Member llGetMember(Guild guild, User user){
        String fName="llGetMember.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMember(guild, user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default String llGetMemberMention(Guild guild, User user){
        String fName="llGetMember."; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMemberMention(guild, user);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default Member llGetMember(Guild guild, String id){
        String fName="llGetMember.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMember(guild, id);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default String llGetMemberMention(Guild guild, String id){
        String fName="llGetMember.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsGetMemberMention(guild, id);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return null;
        }
    }
    default boolean llMemberIsStaff(Member member){
        String fName="llMemberIsStaff.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsStaff(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberIsModerator(Member member){
        String fName="llMemberIsModerator.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsModerator(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberIsManager(Member member){
        String fName="llMemberIsManager."; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsManager(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberIsAdministrator(Member member){
        String fName="llMemberIsAdministrator."; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsAdministrator(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberIsOrange(Member member){
        //https://discordapi.com/permissions.html#0
        String fName="llMemberIsOrange.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsOrange(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasRole(Member member, String roleID) {
        String fName="llMemberHasRole.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasRole(member,roleID);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasRole(Member member, Role role) {
        String fName="llMemberHasRole."; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasRole(member,role);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission(Member member, Permission permission) {
        String fName="llMemberHasPermission.";Logger logger = Logger.getLogger(fName);
        try{
            return lsMemberHelper.lsMemberHasPermission(member,permission);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }

    default boolean llMemberHasPermission_BAN(Member member) {
        String fName="llMemberHasPermission_BAN.";Logger logger = Logger.getLogger(fName);
        try{
            return lsMemberHelper.lsMemberHasPermission_BAN(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_KICK(Member member) {
        String fName="llMemberHasPermission_KICK.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_KICK(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MANAGECHANNEL(Member member) {
        String fName="llMemberHasPermission_MANAGE_CHANNEL.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MANAGECHANNEL(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MANAGEROLES(Member member) {
        String fName="llMemberHasPermission_MANAGE_ROLES.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MANAGEROLES(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MANAGEEMOTES(Member member) {
        String fName="llMemberHasPermission_MANAGE_EMOTES.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MANAGEEMOTES(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MANAGEWEBHOOKS(Member member) {
        String fName="llMemberHasPermission_MANAGE_WEBHOOKS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MANAGEWEBHOOKS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MANAGESERVER(Member member) {
        String fName="llMemberHasPermission_MANAGE_SERVER.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MANAGESERVER(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_NICKNAMEMANAGE(Member member) {
        String fName="llMemberHasPermission_NICKNAME_MANAGE.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_NICKNAMEMANAGE(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VIEWAUDITLOGS(Member member) {
        String fName="llMemberHasPermission_VIEW_AUDIT_LOGS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VIEWAUDITLOGS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    default boolean llMemberHasPermission_MESSAGEMANAGE(Member member) {
        String fName="llMemberHasPermission_MESSAGE_MANAGE.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEMANAGE(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MESSAGEADDREACTION(Member member) {
        String fName="llMemberHasPermission_MESSAGE_ADD_REACTION.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEADDREACTION(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MESSAGEATTACHFILES(Member member) {
        String fName="llMemberHasPermission_MESSAGE_ATTACH_FILES.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEATTACHFILES(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MESSAGEEMBEDLINKS(Member member) {
        String fName="llMemberHasPermission_MESSAGEEMBEDLINKS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEEMBEDLINKS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MESSAGEEXTEMOJI(Member member) {
        String fName="llMemberHasPermission_MESSAGE_EXT_EMOJI.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEEXTEMOJI(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_MESSAGEMENTIONEVERYONE(Member member) {
        String fName="llMemberHasPermission_MESSAGE_MENTION_EVERYONE.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_MESSAGEMENTIONEVERYONE(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    default boolean llMemberHasPermission_VOICEDEAFOTHERS(Member member) {
        String fName="llMemberHasPermission_VOICE_DEAF_OTHERS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICEDEAFOTHERS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICEMOVEOTHERS(Member member) {
        String fName="llMemberHasPermission_VOICE_MOVE_OTHERS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICEMOVEOTHERS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICEMUTEOTHERS(Member member) {
        String fName="llMemberHasPermission_VOICE_MUTE_OTHERS.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICEMUTEOTHERS(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICECONNECT(Member member) {
        String fName="llMemberHasPermission_VOICE_CONNECT.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICECONNECT(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICESPEAK(Member member) {
        String fName="llMemberHasPermission_VOICE_SPEAK.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICESPEAK(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICESTREAM(Member member) {
        String fName="llMemberHasPermission_VOICE_STREAM.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICESTREAM(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberHasPermission_VOICEUSEVAD(Member member) {
        String fName="llMemberHasPermission_VOICE_USE_VAD.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberHasPermission_VOICEUSEVAD(member);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

    default boolean llMemberIsBooster(Member member) {
        String fName="llMemberIsBooster"; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsBooster(member);
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    default boolean llMemberIsBooster(Guild guilod,User user) {
        String fName="llMemberIsBooster"; Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsMemberHelper.lsMemberIsBooster(guilod,user);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }




}