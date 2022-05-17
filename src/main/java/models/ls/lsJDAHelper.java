package models.ls;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.log4j.Logger;

import java.util.*;

public interface lsJDAHelper
{
    //https://www.codota.com/code/java/classes/net.dv8tion.jda.bot.sharding.ShardManager
    //https://www.codota.com/code/java/methods/net.dv8tion.jda.bot.sharding.ShardManager/getShards
    static int getShardCount(JDA jda) {
        Logger logger = Logger.getLogger(lsJDAHelper.class);
        String fName="[getShardCount]";
        try{
           return  jda.getShardManager().getShards().size();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static boolean stopShard(JDA jda) {
        Logger logger = Logger.getLogger(lsJDAHelper.class);
        String fName="[stopShard]";
        try{
            jda.removeEventListener(jda.getEventManager());
            jda.shutdown();
            return  true;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean stopShardManager(ShardManager shardManager) {
        Logger logger = Logger.getLogger(lsJDAHelper.class);
        String fName="[stopShardManager]";
        try{
            for (JDA client : shardManager.getShards()){
                client.removeEventListener(client.getEventManager());
            }
            shardManager.shutdown();
            for (JDA client : shardManager.getShards()){
                client.shutdown();
            }
            return  true;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}