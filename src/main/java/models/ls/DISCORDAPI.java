package models.ls;

import kong.unirest.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class DISCORDAPI {
    Logger logger = Logger.getLogger(getClass());
    private String name="",url="",display="", description="";
    private int code,type;
    private JSONArray headers=new JSONArray();
    public  DISCORDAPI(){

    }
    public  DISCORDAPI(int code, String name,int type,String url,JSONArray headers){
        String fName="[construct]";
        try {
            build(code,name,type,url,headers);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean build(int code, String name,int type,String url,JSONArray headers){
        String fName="[build]";
        try {
            this.code = code;
            this.name = name;
            this.type=type;
            this.url=url;
            this.headers=headers;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setHeaders(JSONArray headers){
        String fName="[build]";
        try {

            this.headers=headers;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
}
