package events.Guild;

import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onEmote extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onEmote]";
    public onEmote(){
        logger.warn(".constructor");
    }

    public  void	onEmoteAdded(@Nonnull EmoteAddedEvent event){
        String fName="onEmoteAdded";
       logger.info(fName+" guild="+event.getGuild().getId()+" emote="+event.getEmote().getId()+" name="+event.getEmote().getName()+" url="+event.getEmote().getImageUrl());
    }
    public  void	onEmoteRemoved(@Nonnull EmoteRemovedEvent event){
        String fName="onEmoteRemoved";
        logger.info(fName+" guild="+event.getGuild().getId()+" emote="+event.getEmote().getId()+" name="+event.getEmote().getName()+" url="+event.getEmote().getImageUrl());
    }
    public  void	onEmoteUpdateName(@Nonnull EmoteUpdateNameEvent event){
        String fName="onEmoteUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" emote="+event.getEmote().getId()+" name="+event.getEmote().getName());
    }
    public  void	onEmoteUpdateRoles(@Nonnull EmoteUpdateRolesEvent event){
        String fName="onEmoteUpdateRoles";
        logger.info(fName+" guild="+event.getGuild().getId()+" emote="+event.getEmote().getId());
    }

}
