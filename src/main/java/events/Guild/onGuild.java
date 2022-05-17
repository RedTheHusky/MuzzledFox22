package events.Guild;

import models.lc.CrunchifyLog4jLevel;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onGuild extends ListenerAdapter {
    Logger logger = Logger.getLogger(getClass());
    Logger loggerJoinLeave = Logger.getLogger("GuildJoin&Leave");
    Logger loggerUnavailable = Logger.getLogger("GuildUnavailable");
    public onGuild(){
        logger.debug(".constructor");
    }
    lcGlobalHelper global;
    public onGuild(lcGlobalHelper g){
        logger.debug(".constructor");
        global=g;
    }
    public  void onGuildReady(@Nonnull GuildReadyEvent event){
        String fName="onGuildReady";
        try {
            logger.warn(fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGuildUnavailable(@Nonnull GuildUnavailableEvent event){
        String fName="onGuildUnavailable";
        try {
            logger.warn(fName+".guild:"+event.getGuild().getId());
            loggerUnavailable.info(fName+".guild:"+event.getGuild().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGuildJoin(@Nonnull GuildJoinEvent event){
        String fName="onGuildJoin";
        try {
            logger.warn(fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
            //global.initGuild(event.getGuild());
            loggerJoinLeave.log(CrunchifyLog4jLevel.GuildJoin,fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
            global.banguilds.checkAndLeave(event.getGuild());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGuildLeave(@Nonnull GuildLeaveEvent event){
        String fName="onGuildLeave";
        try {
            logger.warn(fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
            global.guildsSettingsRemove(event.getGuild());
            loggerJoinLeave.log(CrunchifyLog4jLevel.GuildLeave,fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onUnavailableGuildJoined(@Nonnull UnavailableGuildJoinedEvent event){
        String fName="onUnavailableGuildJoined";
        try {
            logger.warn(fName+".guildID="+event.getGuildId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onUnavailableGuildLeave(@Nonnull UnavailableGuildLeaveEvent event){
        String fName="onGuildLeave";
        try {
            logger.warn(fName+".guildID="+event.getGuildId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
