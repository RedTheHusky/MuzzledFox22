package models.lc.json;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcProfileEntityTable {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcProfileEntityTable]";
    public String gTable="";
    public lcProfileEntityTable(){
        String fName="[constructor1]";
        logger.info(cName + fName + ".gTable="+gTable);
    }
    public lcProfileEntityTable(String table){
        String fName="[constructor1]";
        gTable=table;
        logger.info(cName + fName + ".gTable="+gTable);
    }
    public Boolean set(String table){
        String fName="[set]";
        logger.info(fName);
        try{
            gTable=table;
            logger.info(cName + fName + ".gTable="+gTable);
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }
    }
    public String get(){
        String fName="[get]";
        logger.info(fName);
        try{
            logger.info(cName + fName + ".gTable="+gTable);
            return gTable;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return null;
        }
    }
}
