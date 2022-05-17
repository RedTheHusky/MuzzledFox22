package events;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import util.inhouse.utilityBot;
import util.inhouse.utilitySelf;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

public class onJDA extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    public onJDA(){
        logger.debug(".constructor");
    }
    lcGlobalHelper global;
    public onJDA(lcGlobalHelper g){
        logger.debug(".constructor");
        global=g;
    }

    public  void	onDisconnect(@Nonnull DisconnectEvent event){
        String fName="onDisconnect";
        logger.warn(fName);
        try {
            try {
                logger.info(fName+".payloadText="+ Objects.requireNonNull(event.getClientCloseFrame()).getPayloadText());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName+".closeCode="+ Objects.requireNonNull(event.getCloseCode()).getCode());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName+".timeDisconnected="+event.getTimeDisconnected().toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void	onException(@Nonnull ExceptionEvent event){
        String fName="onException";
        logger.error(fName+":"+event.getCause().toString());
        try {
            logger.error(fName+".exception=" + event);
            logger.error(fName+ ".exception:" + Arrays.toString(event.getCause().getStackTrace()));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void	onReady(@Nonnull ReadyEvent event){
        String fName="onReady";
        try {
            //global.addJDA(event.getJDA());
            logger.warn(fName+":guilds total="+event.getGuildTotalCount()+", available="+event.getGuildAvailableCount()+", unavailable="+event.getGuildUnavailableCount());
            //new utilityBot(global,event);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
        try {
            if(global.emojis.isEmpty()){
                global.emojis.build();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
        try {
            new utilitySelf(global,event);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onReconnected(@Nonnull ReconnectedEvent event){
        String fName="onReconnected";
        logger.warn(fName);
        try {
            //new utilityBot(global,event);
            logger.info(fName+":guilds.size="+event.getJDA().getGuilds().size());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
    /*public  void	onReconnect(@Nonnull ReconnectedEvent event){
        String fName="onReconnect";
        logger.warn(fName);
        try {
            List<Guild> guildList=event.getJDA().getGuilds();
            logger.info(fName+":guildList="+guildList.size());
            for (Guild guild : guildList) {
                global.addGuild(guild);
            }
            logger.info(fName+":guilds.size="+event.getJDA().getGuilds().size());
            logger.info(fName+":guilds="+event.getJDA().getGuilds().toString());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }*/
    public  void onResumed(@Nonnull ResumedEvent event){
        String fName="onResumed";
        logger.info(fName);
        try {
            logger.info(fName+":guilds.size="+event.getJDA().getGuilds().size());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
   /*public  void	onResume(@Nonnull ResumedEvent event){
        String fName="onResume";
        logger.info(fName);
        try {
            List<Guild> guildList=event.getJDA().getGuilds();
            logger.info(fName+":guildList="+guildList.size());
            for (Guild guild : guildList) {
                global.addGuild(guild);
                //global.initGuild(guildList.get(i));
            }
            logger.info(fName+":guilds.size="+event.getJDA().getGuilds().size());
            logger.info(fName+":guilds="+event.getJDA().getGuilds());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }*/
    public  void	onShutdown(@Nonnull ShutdownEvent event){
        String fName="onShutdown";
        logger.info(fName);
        try {
            logger.info(fName+":guilds.size="+event.getJDA().getGuilds().size());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void	onStatusChange(@Nonnull StatusChangeEvent event){
        String fName="onStatusChange";
        logger.info(fName);
        try {
            logger.info(fName+":old="+event.getOldStatus().toString()+", new="+event.getNewStatus().toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+ ".StackTrace=" + Arrays.toString(e.getStackTrace()));
        }

    }





}
