package events.Guild.Channel;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onPermissionOverride extends ListenerAdapter {
    lcGlobalHelper gGlobal;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onPermissionOverride]";
    public onPermissionOverride(lcGlobalHelper global){
       logger.warn(".constructor");
        gGlobal=global;
    }

    public  void onPermissionOverrideUpdate(@Nonnull PermissionOverrideUpdateEvent event){
        String fName="onPermissionOverrideUpdate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public  void onPermissionOverrideCreate(@Nonnull PermissionOverrideCreateEvent event){
        String fName="onPermissionOverrideCreate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }
    public  void onPermissionOverrideDelete(@Nonnull PermissionOverrideDeleteEvent event){
        String fName="onPermissionOverrideDelete";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getChannel().getId());
    }


}
