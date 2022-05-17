package models.ls;

import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

public interface lsJsonArrayHelper
{

    static boolean lsCheckIfHas(JSONArray array, String text){
        String fName="lsCheckIfHas.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".text="+text);
            logger.info(fName+".array="+array);
            for(int index=0;index<array.length();index++){
                String value=array.getString(index);
                if(value.equals(text)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
           logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsCheckIfHas(JSONArray array, int i){
        String fName="lsCheckIfHas.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".i="+i);
            logger.info(fName+".array="+array);
            for(int index=0;index<array.length();index++){
                int value=array.getInt(index);
                if(value==i){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsCheckIfHas(JSONArray array, double d){
        String fName="lsCheckIfHas.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".d="+d);
            logger.info(fName+".array="+array);
            for(int index=0;index<array.length();index++){
                double value=array.getDouble(index);
                if(value==d){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsCheckIfHas(JSONArray array, float f){
        String fName="lsCheckIfHas.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".f="+f);
            logger.info(fName+".array="+array);
            for(int index=0;index<array.length();index++){
                float value=array.getFloat(index);
                if(value==f){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }
    static boolean lsCheckIfHas(JSONArray array, long l){
        String fName="lsCheckIfHas.";Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".l="+l);
            logger.info(fName+".array="+array);
            for(int index=0;index<array.length();index++){
                long value=array.getLong(index);
                if(value==l){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return false;
        }
    }

}