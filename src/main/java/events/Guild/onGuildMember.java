package events.Guild;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.OnlineStatus;
import util.utilityAutoRole;
import util.inhouse.utilityPersistenceRole;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import workers.SqlLog;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.Arrays;

public class onGuildMember extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onGuildMember]";
    public onGuildMember(){
        logger.warn(cName+".constructor");
    }
    lcGlobalHelper gGlobal;
    public onGuildMember(lcGlobalHelper global){
        logger.warn(cName+".constructor"); gGlobal =global;
    }
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        String fName="onGuildMemberJoin";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
            if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }else
            if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                //new SqlLog(gGlobal,event);
                new utilityPersistenceRole(event, gGlobal);
                new utilityAutoRole(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        String fName="onGuildMemberLeave";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
            if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }else
            if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                //new SqlLog(gGlobal,event);
                new utilityPersistenceRole(event, gGlobal);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event){
        String fName="onGuildMemberRoleAdd";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId()+" role="+event.getRoles());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
            if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }else
            if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                //new SqlLog(gGlobal,event);
                new utilityPersistenceRole(event, gGlobal);
                new utilityAutoRole(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event){
        String fName="onGuildMemberRoleRemove";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId()+" role="+event.getRoles());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
            if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }else
            if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                //new SqlLog(gGlobal,event);
                new utilityPersistenceRole(event, gGlobal);
                new utilityAutoRole(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    /*
    public void onGuildMemberUpdateBoostTime(@Nonnull GuildMemberUpdateBoostTimeEvent event){
        String fName="onGuildMemberUpdateBoostTime";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId()+" boosttime="+event.getNewTimeBoosted());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                new SqlLog(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event){
        String fName="onGuildMemberUpdateNickname";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember().getId()+" newValue="+event.getNewValue());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                new SqlLog(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    */
    public void onGuildBan(@Nonnull GuildBanEvent event){
        String fName="onGuildBan";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" user="+event.getUser().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
            if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }else
            if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                logger.info(fName + ".status is set offline>ignore events");
                return;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                //new SqlLog(gGlobal,event);
                new utilityPersistenceRole(event, gGlobal);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    /*
    public void onGuildUnban(@Nonnull GuildUnbanEvent event){
        String fName="onGuildUnban";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" user="+event.getUser().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(event.getGuild())){
                new SqlLog(gGlobal,event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    */
}
