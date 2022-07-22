package restraints.models.entity.pishock;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;


public class entityShockerAction implements iRestraints {
    Logger logger = Logger.getLogger(getClass()); String cName="[cShockerActions]";
    int gOp =-1, gIntensity =-1, gDuration =-1;
    public entityShockerAction(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityShockerAction(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           if(jsonObject!=null){
               String key=nPishockMode;
               if(jsonObject.has(key)){
                   String value=jsonObject.getString(key);
                   logger.info(fName+"value["+key+"]="+value);
                   switch (value){
                       case vPishockMode_Beep:
                           gOp =2;break;
                       case vPishockMode_Vibrate:
                           gOp =1;break;
                       case vPishockMode_Shock:
                           gOp =0;break;
                   }
               }
               key=nPishockItensity;
               if(jsonObject.has(key)){
                   int value=jsonObject.getInt(key);
                   logger.info(fName+"value["+key+"]="+value);
                   gIntensity =value;
               }
               key=nPishockDuration;
               if(jsonObject.has(key)){
                   int value=jsonObject.getInt(key);
                   logger.info(fName+"value["+key+"]="+value);
                   gDuration =value;
               }
           }
            jsonObject=null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityShockerAction(String mode, int duration, int intensity){
        String fName="[constructor]";
        try {
            if(mode!=null){
                logger.info(fName+"mode="+mode);
                switch (mode){
                    case vPishockMode_Beep:
                        gOp =2;break;
                    case vPishockMode_Vibrate:
                        gOp =1;break;
                    case vPishockMode_Shock:
                        gOp =0;break;
                }
            }
            logger.info(fName+"duration="+duration);
            if(duration<0)duration=1;
            if(duration>15)duration=15;
            gDuration=duration;
            logger.info(fName+"intensity="+intensity);
            if(intensity<0)intensity=1;
            if(intensity>100)intensity=100;
            gIntensity=intensity;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityShockerAction(int op, int duration, int intensity){
        String fName="[constructor]";
        try {
            logger.info(fName+"op="+op);
            if(op<0)op=1;
            if(op>2)op=1;
            gOp=op;
            logger.info(fName+"duration="+duration);
            if(duration<0)duration=1;
            if(duration>15)duration=15;
            gDuration=duration;
            logger.info(fName+"intensity="+intensity);
            if(intensity<0)intensity=1;
            if(intensity>100)intensity=100;
            gIntensity=intensity;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nPishockMode,getMode());
            jsonObject.put(nPishockDuration,getDuration());
            jsonObject.put(nPishockItensity,getIntensity());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getMode(){
        String fName="[getMode]";
        try {
            logger.info(fName+"op="+ gOp);
            String value="";
            switch (gOp){
                case 2:
                    value=vPishockMode_Beep;break;
                case 1:
                    value=vPishockMode_Vibrate;break;
                case 0:
                    value=vPishockMode_Shock;break;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getOp(){
        String fName="[getOp]";
        try {
            logger.info(fName+"op="+ gOp);
            return gOp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getIntensity(){
        String fName="[getIntensity]";
        try {
            logger.info(fName+"intensity="+ gIntensity);
            return gIntensity;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getDuration(){
        String fName="[getDuration]";
        try {
            logger.info(fName+"duration="+ gDuration);
            return gDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean setMode(String input){
        String fName="[setMode]";
        try {
            logger.info(fName+"input="+input);
            switch (input){
                case vPishockMode_Beep:
                    gOp =2;break;
                case vPishockMode_Vibrate:
                    gOp =1;break;
                case vPishockMode_Shock:
                    gOp =0;break;
                default:
                    return false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setOp(int input){
        String fName="[setOp]";
        try {
            logger.info(fName+"input="+input);
            if(input>2)input=1;
            if(input<0)input=1;
            gOp =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setIntensity(int input){
        String fName="[setIntensity]";
        try {
            logger.info(fName+"input="+input);
            if(input>100)input=100;
            if(input<0)input=1;
            gIntensity =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDuration(int input){
        String fName="[setDuration]";
        try {
            logger.info(fName+"input="+input);
            if(input>15)input=15;
            if(input<0)input=1;
            gDuration =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
