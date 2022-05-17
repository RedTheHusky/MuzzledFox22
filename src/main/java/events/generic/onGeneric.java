package events.generic;

import models.lc.CrunchifyLog4jLevel;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onGeneric extends ListenerAdapter {
    Logger logger = Logger.getLogger(getClass());
    public onGeneric(){
        logger.info(".constructor");
    }
    lcGlobalHelper global;
    public onGeneric(lcGlobalHelper g){
        logger.info(".constructor");
        global=g;
    }
    public  void onGenericEvent(@Nonnull GenericEvent event){
        String fName="[onGenericEvent]";
        try {
            logger.info(fName+".responseNumber:"+event.getResponseNumber());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGenericGuildEvent(@Nonnull GenericGuildEvent event){
        String fName="[onGenericGuildEvent]";
        try {
            logger.info(fName+".guild:"+event.getGuild().getId()+"|"+event.getGuild().getName());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGenericMessageEvent(@Nonnull GenericMessageEvent event){
        String fName="[onGenericMessageEvent]";
        try {
            logger.info(fName+".id:"+event.getMessageId()+"|"+event.isFromGuild());
            if(event.isFromGuild()){
                logger.info(fName+".id:"+event.getMessageId()+"|"+"guild:"+event.getGuild().getName()+"("+event.getGuild().getId()+")|"+event.getTextChannel().getName()+"("+event.getTextChannel().getId()+")");
                Message message= event.getTextChannel().retrieveMessageById(event.getMessageId()).complete();
                logger.info(fName+".id:"+event.getMessageId()+"|"+"message.raw="+message.getContentRaw());
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGenericMessageEvent(@Nonnull GenericMessageReactionEvent event){
        String fName="[onGenericMessageEvent]";
        try {
            logger.info(fName+".id:"+event.getMessageId()+"|"+event.isFromGuild());
            if(event.isFromGuild()){
                logger.info(fName+".id:"+event.getMessageId()+"|"+"guild:"+event.getGuild().getName()+"("+event.getGuild().getId()+")|"+event.getTextChannel().getName()+"("+event.getTextChannel().getId()+")");
                Message message= event.getTextChannel().retrieveMessageById(event.getMessageId()).complete();
                logger.info(fName+".id:"+event.getMessageId()+"|"+"message.raw="+message.getContentRaw());
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

}
