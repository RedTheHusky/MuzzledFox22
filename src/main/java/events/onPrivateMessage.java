package events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.priv.PrivateChannelDeleteEvent;
import net.dv8tion.jda.api.events.message.priv.*;
import net.dv8tion.jda.api.events.message.priv.react.GenericPrivateMessageReactionEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onPrivateMessage extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onPrivateMessage]";
    lcGlobalHelper global;
    public onPrivateMessage(){
         logger.warn(cName+".constructor");
    }
    public onPrivateMessage(lcGlobalHelper g){
        logger.warn(cName+".constructor");
        global=g;
    }
    /*public void onPrivateChannelCreate(PrivateChannelCreateEvent event){
        String fName="onPrivateChannelCreate";
        try {
            logger.info("channel="+event.getChannel().getName()+"("+event.getChannel().getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateChannelDelete(PrivateChannelDeleteEvent event){
        String fName="onPrivateChannelDelete";
        try {
            logger.info("channel="+event.getChannel().getName()+"("+event.getChannel().getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }*/
    public void	onPrivateMessageDelete(PrivateMessageDeleteEvent event){
        String fName="[onPrivateMessageDelete]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateMessageEmbed(PrivateMessageEmbedEvent event){
        String fName="[onPrivateMessageEmbed]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event){
        String fName="[onPrivateMessageReactionAdd]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), user="+event.getUser().getName()+"("+event.getUser().getId()+")"+", messageId="+event.getMessageId());
            MessageReaction.ReactionEmote reactionEmote=event.getReactionEmote();
            logger.info(fName +"isEmote="+reactionEmote.isEmote()+", isEmoji="+reactionEmote.isEmoji());
            if(reactionEmote.isEmoji()){
                logger.info(fName +"Emoji="+reactionEmote.getEmoji());
            }
            if(reactionEmote.isEmote()){
                logger.info(fName +"Emote="+reactionEmote.getEmote().getId());
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateMessageReactionRemove(PrivateMessageReactionRemoveEvent event){
        String fName="[onPrivateMessageReactionRemove]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), user="+event.getUser().getName()+"("+event.getUser().getId()+")"+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateMessageReceived(PrivateMessageReceivedEvent event){
        String fName="[onPrivateMessageReceived]";
        try {
            logger.info("channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+")"+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onPrivateMessageUpdate(PrivateMessageUpdateEvent event){
        String fName="[onPrivateMessageUpdate]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+")"+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onGenericPrivateMessageReaction(GenericPrivateMessageReactionEvent event){
        String fName="[onGenericPrivateMessageReaction]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), user="+event.getUser().getName()+"("+event.getUser().getId()+")"+", messageId="+event.getMessageId());
            MessageReaction.ReactionEmote reactionEmote=event.getReactionEmote();
            logger.info(fName +"isEmote="+reactionEmote.isEmote()+", isEmoji="+reactionEmote.isEmoji());
            if(reactionEmote.isEmoji()){
                logger.info(fName +"Emoji="+reactionEmote.getEmoji());
            }
            if(reactionEmote.isEmote()){
                logger.info(fName +"Emote="+reactionEmote.getEmote().getId());
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void	onGenericPrivateMessage(GenericPrivateMessageEvent event){
        String fName="[onGenericPrivateMessage]";
        try {
            logger.info(fName +"channel="+event.getChannel().getName()+"("+event.getChannel().getId()+")"+", messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
