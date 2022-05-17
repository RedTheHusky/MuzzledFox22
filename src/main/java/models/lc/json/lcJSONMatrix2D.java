package models.lc.json;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcJSONMatrix2D {
    Logger logger = Logger.getLogger(getClass());
    private JSONObject matrix =null;
    public lcJSONMatrix2D(){
        String fName="[constructor]";
        logger.info(fName);
        matrix =new JSONObject();
    }
    public void set(int x, int y, int value) {
        String fName="[set]";
        logger.info(fName); logger.info(fName+"x="+x+ ",y="+y+" ,value="+value);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            set(keyX,keyY,value);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void set(String keyX, String keyY, int value) {
        String fName="[set]";
        logger.info(fName);  logger.info(fName+"x="+keyX+ ",y="+keyY+" ,value="+value);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                objectX=new JSONObject();
            }else
            if(matrix.isNull(keyX)){
                objectX=new JSONObject();
            }else{
                objectX=matrix.getJSONObject(keyX);
            }
            objectX.put(keyY,value);
            matrix.put(keyX,objectX);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void set(int x, int y, String value) {
        String fName="[set]";
        logger.info(fName); logger.info(fName+"x="+x+ ",y="+y+" ,value="+value);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            set(keyX,keyY,value);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void set(String keyX, String keyY, String value) {
        String fName="[set]";
        logger.info(fName);
        logger.info(fName+"x="+keyX+ ",y="+keyY+" ,value="+value);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                objectX=new JSONObject();
            }else
            if(matrix.isNull(keyX)){
                objectX=new JSONObject();
            }else{
                objectX=matrix.getJSONObject(keyX);
            }
            objectX.put(keyY,value);
            matrix.put(keyX,objectX);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void set(int x, int y, boolean value) {
        String fName="[set]";
        logger.info(fName); logger.info(fName+"x="+x+ ",y="+y+" ,value="+value);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            set(keyX,keyY,value);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void set(String keyX, String keyY, boolean value) {
        String fName="[set]";
        logger.info(fName);  logger.info(fName+"x="+keyX+ ",y="+keyY+" ,value="+value);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                objectX=new JSONObject();
            }else
            if(matrix.isNull(keyX)){
                objectX=new JSONObject();
            }else{
                objectX=matrix.getJSONObject(keyX);
            }
            objectX.put(keyY,value);
            matrix.put(keyX,objectX);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean has(int x, int y) {
        String fName="[has]";
        logger.info(fName);
        logger.info(fName+"x="+x+ ",y="+y);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            return has(keyX,keyY);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean has(String keyX, String keyY) {
        String fName="[has]";
        logger.info(fName);
        logger.info(fName+fName+"x="+keyX+ ",y="+keyY);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                logger.warn(fName+"has no x");return false;
            }else
            if(matrix.isNull(keyX)){
                logger.warn(fName+"x is null");return false;
            }
            objectX=matrix.getJSONObject(keyX);
            if(!objectX.has(keyY)){
                logger.warn(fName+"has no y");return false;
            }else
            if(objectX.isNull(keyY)){
                logger.warn(fName+"y is null");return false;
            }
            logger.warn(fName+"y is not null");
            return  true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean has(String keyX) {
        String fName="[has]";
        logger.info(fName);
        logger.info(fName+fName+"x="+keyX);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                logger.warn(fName+"has no x");return false;
            }else
            if(matrix.isNull(keyX)){
                logger.warn(fName+"x is null");return false;
            }
            logger.warn(fName+"x is not null");
            return  true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getString(int x, int y) {
        String fName="[getString]";
        logger.info(fName);
        logger.info(fName+"x="+x+ ",y="+y);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            return  getString(keyX,keyY);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getString(String keyX, String keyY) {
        String fName="[getString]";
        logger.info(fName);
        logger.info(fName+"x="+keyX+ ",y="+keyY);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                logger.warn(fName+"has no x"); return null;
            }else
            if(matrix.isNull(keyX)){
                logger.warn(fName+"x is null"); return null;
            }
            objectX=matrix.getJSONObject(keyX);
            if(!objectX.has(keyY)){
                logger.warn(fName+"has no y"); return null;
            }else
            if(objectX.isNull(keyY)){
                logger.warn(fName+"y is null"); return null;
            }
            String result= objectX.getString(keyY);
            logger.info(fName+"result="+result);
            return  result;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getInt(int x, int y) {
        String fName="[getInt]";
        logger.info(fName);
        logger.info(fName+"x="+x+ ",y="+y);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            return  getInt(keyX,keyY);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getInt(String keyX, String keyY) {
        String fName="[getInt]";
        logger.info(fName);
        logger.info(fName+"x="+keyX+ ",y="+keyY);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                logger.warn(fName+"has no x");return 0;
            }else
            if(matrix.isNull(keyX)){
                logger.warn(fName+"x is null");return 0;
            }
            objectX=matrix.getJSONObject(keyX);
            if(!objectX.has(keyY)){
                logger.warn(fName+"has no y");return 0;
            }else
            if(objectX.isNull(keyY)){
                logger.warn(fName+"y is null");return 0;
            }
            int result= objectX.getInt(keyY);
            logger.info(fName+"result="+result);
            return  result;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean getBoolean(int x, int y) {
        String fName="[getBoolean]";
        logger.info(fName);
        logger.info(fName+"x="+x+ ",y="+y);
        try{
            String keyX=String.valueOf(x);
            String keyY=String.valueOf(y);
            return  getBoolean(keyX,keyY);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getBoolean(String keyX, String keyY) {
        String fName="[getBoolean]";
        logger.info(fName);
        logger.info(fName+"x="+keyX+ ",y="+keyY);
        try{
            JSONObject objectX;
            if(!matrix.has(keyX)){
                logger.warn(fName+"has no x"); return false;
            }else
            if(matrix.isNull(keyX)){
                logger.warn(fName+"x is null"); return false;
            }
            objectX=matrix.getJSONObject(keyX);
            if(!objectX.has(keyY)){
                logger.warn(fName+"has no y"); return false;
            }else
            if(objectX.isNull(keyY)){
                logger.warn(fName+"y is null"); return false;
            }
            boolean result= objectX.getBoolean(keyY);
            logger.info(fName+"result="+result);
            return  result;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
