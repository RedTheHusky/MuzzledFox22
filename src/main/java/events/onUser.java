package events;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.events.user.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import workers.SqlLog;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onUser extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onUser]";
    lcGlobalHelper gGlobal;
    public onUser(lcGlobalHelper global){
         logger.warn(".constructor");gGlobal=global;
    }
    public void	onUserActivityEnd(@Nonnull UserActivityEndEvent event){
        String fName="onUserActivityEnd";
        logger.info(fName+" user="+event.getUser().getId());
    }
    public void	onUserActivityStart(@Nonnull UserActivityStartEvent event){
        String fName="onUserActivityStart";
        logger.info(fName+" user="+event.getUser().getId());
    }
    public void	onUserTyping(@Nonnull UserTypingEvent event){
        String fName="onUserTyping";
        logger.info(fName+" user="+event.getUser().getId());
    }
    public void	onUserUpdateActivityOrder(@Nonnull UserUpdateActivityOrderEvent event){
        String fName="onUserUpdateActivityOrder";
        logger.info(fName+" user="+event.getUser().getId());
    }
    public void	onUserUpdateAvatar(@Nonnull UserUpdateAvatarEvent event){
        String fName="onUserUpdateAvatar";
        try {
            logger.info(fName+" user="+event.getUser().getId()+" old="+event.getOldAvatarUrl()+" new="+event.getNewAvatarUrl());
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
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onUserUpdateDiscriminator(@Nonnull UserUpdateDiscriminatorEvent event){
        String fName="onUserUpdateDiscriminator";
        logger.info(fName+" user="+event.getUser().getId()+" old="+event.getOldDiscriminator()+" new="+event.getNewDiscriminator());
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
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onUserUpdateName(@Nonnull UserUpdateNameEvent event){
        String fName="onUserUpdateName";
        logger.info(fName+" user="+event.getUser().getId()+" old="+event.getOldName()+" new="+event.getNewName());
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
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onUserUpdateOnlineStatus(@Nonnull UserUpdateOnlineStatusEvent event){
        String fName="onUserUpdateOnlineStatus";
        logger.info(fName+" user="+event.getUser().getId()+" old="+event.getOldOnlineStatus().getKey()+" new="+event.getNewOnlineStatus().getKey());
        //new SqlLog(gGlobal,event);
    }
}
