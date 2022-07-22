package events.Guild.Channel;

import net.dv8tion.jda.api.events.channel.store.StoreChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.store.StoreChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.text.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onStoreChannel extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onStoreChannel]";
    public onStoreChannel(){
        logger.warn(".constructor");
    }
    /*
    public void	onStoreChannelCreate(@Nonnull StoreChannelCreateEvent event){
        String fName="onStoreChannelCreate";
        logger.info(fName+" channel="+event.getChannel().getId());
    }
    public void	onStoreChannelDelete(@Nonnull StoreChannelDeleteEvent event){
        String fName="onStoreChannelDelete";
        logger.info(fName+" channel="+event.getChannel().getId());
    }
    public void	onStoreChannelUpdateName(@Nonnull StoreChannelUpdateNameEvent event){
        String fName="onStoreChannelUpdateName";
        logger.info(fName+" channel="+event.getChannel().getId()+" old="+event.getOldName()+" new="+event.getNewName());
    }
    public void	onStoreChannelUpdatePosition(@Nonnull StoreChannelUpdatePositionEvent event){
        String fName="onStoreChannelUpdatePosition";
        logger.info(fName+" channel="+event.getChannel().getId()+" old="+event.getOldPosition()+" new="+event.getNewPosition());
    }
    */

}
