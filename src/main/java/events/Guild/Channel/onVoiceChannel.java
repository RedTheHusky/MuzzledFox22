package events.Guild.Channel;

import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onVoiceChannel extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onVoiceChannel]";
    public onVoiceChannel(){
         logger.warn(".constructor");
    }

    public void	onVoiceChannelCreate(@Nonnull VoiceChannelCreateEvent event){
        String fName="onVoiceChannelCreate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public void	onVoiceChannelDelete(@Nonnull VoiceChannelDeleteEvent event){
        String fName="onVoiceChannelDelete";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public void	onVoiceChannelUpdateBitrate(@Nonnull VoiceChannelUpdateBitrateEvent event){
        String fName="onVoiceChannelUpdateBitrate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldBitrate()+" new="+event.getNewBitrate());
    }
    public void	onVoiceChannelUpdateName(@Nonnull VoiceChannelUpdateNameEvent event){
        String fName="onVoiceChannelUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldName()+" new="+event.getNewName());
    }
    public void	onVoiceChannelUpdateParent(@Nonnull VoiceChannelUpdateParentEvent event){
        String fName="onVoiceChannelUpdateParent";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }

    /*public void	onVoiceChannelUpdatePermissions(@Nonnull VoiceChannelUpdatePermissionsEvent event){
        String fName="onVoiceChannelUpdatePermissions";
        logger.info(fName);
        //ReplaceWith("onPermissionOverrideUpdate(), onPermissionOverrideCreate(), and onPermissionOverrideDelete()")
    }
    /*
    public void	onVoiceChannelUpdatePosition(@Nonnull VoiceChannelUpdatePositionEvent event){
        String fName="onVoiceChannelUpdatePosition";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldPosition()+" new="+event.getNewPosition());
    }
    public void	onVoiceChannelUpdateUserLimit(@Nonnull VoiceChannelUpdateUserLimitEvent event){
        String fName="onVoiceChannelUpdateUserLimit";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId()+" old="+event.getOldUserLimit()+" new="+event.getNewUserLimit());
    }
    */
}
