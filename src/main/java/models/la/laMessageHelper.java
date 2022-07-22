package models.la;

import models.ls.lsMessageHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.Arrays;

public abstract class laMessageHelper {
    public static void laSendMessage(User author, Message message){
        String fName="[lsSendMessage:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            PrivateChannel channel=author.openPrivateChannel().complete();
            channel.sendTyping().complete();
            channel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
