package models.lc.json;

import org.apache.log4j.Logger;

public class lcProfileEntityName {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcProfileEntityName]";
    public String gName ="";
    public lcProfileEntityName(){
        String fName="[constructor1]";
        logger.info(cName + fName + ".gNamee="+ gName);
    }
    public lcProfileEntityName(String name){
        String fName="[constructor1]";
        gName =name;
        logger.info(cName + fName + ".gNamee="+ gName);
    }
    public Boolean set(String table){
        String fName="[set]";
        logger.info(fName);
        try{
            gName =table;
            logger.info(cName + fName + ".gName="+ gName);
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
            logger.info(cName + fName + ".gName="+ gName);
            return gName;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return null;
        }
    }
}
