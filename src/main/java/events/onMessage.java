package events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.priv.PrivateChannelDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onMessage extends ListenerAdapter {
    public EventWaiter waiter;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onMessage]";
    lcGlobalHelper global;
    public onMessage(){
         logger.warn(cName+".constructor");
    }
    public onMessage(lcGlobalHelper g){
        logger.warn(cName+".constructor");
        global=g;
    }
    public void	onMessageDelete(MessageDeleteEvent event){
        String fName="[onMessageDelete]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId());
    }
    public void	onMessageEmbed(MessageEmbedEvent event){
        String fName="[onMessageEmbed]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId());
    }
    public void	onMessageReactionAdd(MessageReactionAddEvent event){
        String fName="[onMessageReactionAdd]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId()+", user="+event.getUser().getId());
    }
    public void	onMessageReactionRemove(MessageReactionRemoveEvent event){
        String fName="[onMessageReactionRemove]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId()+", user="+event.getUser().getId());
    }
    public void	onMessageReceived(MessageReceivedEvent event){
        String fName="[onMessageReceived]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId()+", author="+event.getAuthor().getId());
    }
    public void	onMessageUpdate(MessageUpdateEvent event){
        String fName="[onMessageUpdate]";
        logger.info(cName+fName+"message="+event.getMessageId()+" isFromGuild="+event.isFromGuild()+" channel="+event.getChannel().getId()+", author="+event.getAuthor().getId());
    }
}
