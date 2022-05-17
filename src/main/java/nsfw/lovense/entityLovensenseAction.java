package nsfw.lovense;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;


public class entityLovensenseAction {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLovensenseAction]";
    iLovense.Actions4Function lovenseActions4Function;

    int actionStrength=0;

    public entityLovensenseAction(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLovensenseAction(iLovense.Actions4Function action){
        String fName="[constructor]";
        logger.info(fName);
        set(action);
    }
    public entityLovensenseAction(iLovense.Actions4Function action, int strength){
        String fName="[constructor]";
        logger.info(fName);
        set(action,strength);
    }
    public boolean set(iLovense.Actions4Function action){
        String fName="[set]";
        try {
            clear();
            lovenseActions4Function=action;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean set(iLovense.Actions4Function action, int strength){
        String fName="[set]";
        try {
            clear();
            lovenseActions4Function=action;
            actionStrength=strength;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            lovenseActions4Function=null;
            actionStrength=0;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getAsString(){
        String fName="[getAsString]";
        try {
            String str="";
            if( lovenseActions4Function!=null){
                if(lovenseActions4Function== iLovense.Actions4Function.Stop){
                    str=lovenseActions4Function.getName();
                }else{
                    str=lovenseActions4Function.getName()+":"+actionStrength;
                }
            }
            logger.info(fName + "str=" + str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public iLovense.Actions4Function getAction(){
        String fName="[getAction]";
        try {
            String str="";
            logger.info(fName + "lovenseActions4Function=" + lovenseActions4Function.getName());
            return lovenseActions4Function;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getStrength(){
        String fName="[getStrength]";
        try {
            String str="";
            logger.info(fName + "Strength=" + actionStrength);
            return actionStrength;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
}
