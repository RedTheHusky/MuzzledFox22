package events;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.http.HttpRequestEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class onJDAExtra extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    public onJDAExtra(){
        logger.debug(".constructor");
    }
    lcGlobalHelper global;
    public onJDAExtra(lcGlobalHelper g){
        logger.debug(".constructor");
        global=g;
    }


    public  void	onHttpRequest(@Nonnull HttpRequestEvent event){
        String fName="onHttpRequest";
        logger.info(fName);
    }
    public  void	onRawGateway(@Nonnull RawGatewayEvent event){
        String fName="onRawGateway";
        logger.info(fName);
    }
    public  void	onGatewayPing(@Nonnull GatewayPingEvent event){
        String fName="onGatewayPing";
        logger.info(fName);
        try {
            logger.info(fName+".pings(old|new)="+event.getOldPing()+"|"+event.getNewPing());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }




}
