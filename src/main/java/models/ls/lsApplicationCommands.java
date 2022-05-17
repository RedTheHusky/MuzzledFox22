package models.ls;

import models.lc.interaction.applicationcommand.lcApplicationCCommonEditor;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface lsApplicationCommands
{
    static Command lsRetrieveCommandById(Guild guild, String id){
        String fName="[lsRetrieveCommandById].";Logger logger = Logger.getLogger(lsApplicationCommands.class);
        try{
            logger.info(fName);
            Command command=guild.retrieveCommandById(id).complete();
            return command;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Command lsRetrieveCommandById(Guild guild, long id){
        String fName="[lsRetrieveCommandById].";Logger logger = Logger.getLogger(lsApplicationCommands.class);
        try{
            logger.info(fName);
            Command command=guild.retrieveCommandById(id).complete();
            return command;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<Command> lsRetrieveCommands(Guild guild){
        String fName="[lsRetrieveCommands].";Logger logger = Logger.getLogger(lsApplicationCommands.class);
        try{
            logger.info(fName);
            List<Command>commands=guild.retrieveCommands().complete();
            return commands;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Command lsRetrieveCommandByName(Guild guild, String name){
        String fName="[lsRetrieveCommandByName].";Logger logger = Logger.getLogger(lsApplicationCommands.class);
        try{
            logger.info(fName);
            List<Command>commands=lsRetrieveCommands(guild);
            for (Command command : commands) {
                String name1 = command.getName();
                logger.info(fName + ".command: " + name1 + " (" + command.getId() + ")");
                if (name1.equals(name)) {
                    logger.info(fName + "found");
                    return command;
                }

            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Command lsRetrieveCommandByNameIgnoreCase(Guild guild, String name){
        String fName="[lsRetrieveCommandByNameIgnoreCase].";Logger logger = Logger.getLogger(lsApplicationCommands.class);
        try{
            logger.info(fName);
            List<Command>commands=lsRetrieveCommands(guild);
            for (Command command : commands) {
                String name1 = command.getName();
                logger.info(fName + ".command: " + name1 + " (" + command.getId() + ")");
                if (name1.equalsIgnoreCase(name)) {
                    logger.info(fName + "found");
                    return command;
                }

            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}