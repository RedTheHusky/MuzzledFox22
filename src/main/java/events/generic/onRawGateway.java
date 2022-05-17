package events.generic;

import interaction.usercommand.usercommandControler;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import interaction.slash.slashControler;
import models.ls.lsRawGateway;
import javax.annotation.Nonnull;
import java.util.Arrays;

public class onRawGateway extends ListenerAdapter implements lsRawGateway {
    Logger logger = Logger.getLogger(getClass());
    public onRawGateway(){
        logger.info(".constructor");
    }
    lcGlobalHelper global;
    public onRawGateway(lcGlobalHelper g){
        logger.info(".constructor");
        global=g;
    }
    public  void onRawGateway(@Nonnull RawGatewayEvent event){
        String fName="[onRawGatewayEvent]";
        try {
            //logger.info(fName+".ResponseNumber="+event.getResponseNumber());
            try {
                logger.info(fName+".Type="+event.getType());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                INTERACTION_CREATE(event);
            }
            if(event.getType().equalsIgnoreCase("MESSAGE_CREATE")){
                if(event.getPayload().getInt("type")==20){
                    APPLICATION_COMMAND_CREATE(event);
                }

            }
            if(event.getType().equalsIgnoreCase("MESSAGE_REACTION_ADD")){
                MESSAGE_REACTION_ADD(event);
            }

            /*try {
                logger.info(fName+".Package="+event.getPackage().toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
           try {
                logger.info(fName+".Payload="+event.getPayload().toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }*/
            if(event.getType().toLowerCase().contains("message")||event.getType().toLowerCase().contains("thread")){
                DEBUG(event);
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void DEBUG(@Nonnull RawGatewayEvent event){
        String fName="[DEBUG]";
        try {
            logger.info(fName+".type="+event.getType());
            String str=event.getPayload().toString();
            logger.info(fName+".Payload="+str);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void INTERACTION_CREATE(@Nonnull RawGatewayEvent event){
        String fName="[INTERACTION_CREATE]";
        try {
            String str=event.getPayload().toString();
            logger.info(fName+".Payload="+str);
            JSONObject jsonObject=new JSONObject(str);
            if(jsonObject.has(keyType)){
                int type=jsonObject.optInt(keyType,0);
                logger.warn(fName+".type="+type);
                switch (type){
                    case 1:
                    case 2:
                        if(jsonObject.has(llCommonKeys.keyData)){
                            int type2=jsonObject.getJSONObject(llCommonKeys.keyData).optInt(llCommonKeys.keyType);
                            logger.warn(fName+".type2="+type2);
                            switch (type2){
                                case 1:
                                    logger.warn("is a slash command");
                                    new slashControler(global,new lcSlashInteractionReceive(event));
                                    break;
                                case 2:
                                    logger.warn("is a user command");
                                    new usercommandControler(global,new lcApplicationInteractionReceive(event,global).getUserCommand());
                                    break;
                                case 3:
                                    logger.warn("is a message command");
                                    break;
                            }
                        }else{
                            throw  new Exception("missing data");
                        }
                        break;
                    case 3:
                        //new buttonControler(global,new lcComponentInteractionReceive(event));
                        break;
                }
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void APPLICATION_COMMAND_CREATE(@Nonnull RawGatewayEvent event){
        String fName="[APPLICATION_COMMAND_CREATE]";
        try {
            logger.info(fName+".Payload="+event.getPayload().toString());
            //new slashControler(global,new lcAPPLICATION_COMMAND_CREATE(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void MESSAGE_REACTION_ADD(@Nonnull RawGatewayEvent event){
        String fName="[MESSAGE_REACTION_ADD]";
        try {
            logger.info(fName+".Payload="+event.getPayload().toString());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


}
