package events.Guild.Channel;

import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onTextChannel extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onTextChannel]";
    public onTextChannel(){
        logger.warn(".constructor");
    }

    public void	onTextChannelCreate(@Nonnull TextChannelCreateEvent event){
        String fName="onTextChannelCreate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public void	onTextChannelDelete(@Nonnull TextChannelDeleteEvent event){
        String fName="onTextChannelDelete";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public void	onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event){
        String fName="onTextChannelUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldName()+" new="+event.getNewName());
    }
    public void	onTextChannelUpdateNSFW(@Nonnull TextChannelUpdateNSFWEvent event){
        String fName="onTextChannelUpdateNSFW";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldNSFW()+" new="+event.getNewValue());
    }
    public void	onTextChannelUpdateParent(@Nonnull TextChannelUpdateParentEvent event){
        String fName="onTextChannelUpdateParent";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }

    /*public void	onTextChannelUpdatePermissions(@Nonnull TextChannelUpdatePermissionsEvent event){
        String fName="onTextChannelUpdatePermissions";
        logger.info(cName+fName);
        //ReplaceWith("onPermissionOverrideUpdate(), onPermissionOverrideCreate(), and onPermissionOverrideDelete()")
    }
    /*
    public void	onTextChannelUpdatePosition(@Nonnull TextChannelUpdatePositionEvent event){
        String fName="onTextChannelUpdatePosition";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldPosition()+" new="+event.getNewPosition());
    }
    public void	onTextChannelUpdateSlowmode(@Nonnull TextChannelUpdateSlowmodeEvent event){
        String fName="onTextChannelUpdateSlowmode";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldSlowmode()+" new="+event.getNewSlowmode());
    }
    public void	onTextChannelUpdateTopic(@Nonnull TextChannelUpdateTopicEvent event){
        String fName="onTextChannelUpdateTopic";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldTopic()+" new="+event.getNewTopic());
    }
    */
}
