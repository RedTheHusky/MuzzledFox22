package models.lc;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Arrays;

public class lcEmoji {
    Logger logger = Logger.getLogger(getClass());
    public lcEmoji(){
        String fName="[constructor]";
    }
    String emojiChar="",emoji="",description="";
    JSONArray aliases=new JSONArray(),tags=new JSONArray();
    public String getEmoji(){
        String fName="[getEmoji]";
        try {
            return emoji;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";

        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            if(aliases.isEmpty())return "";
            String name=aliases.getString(0);
            name=name.replaceAll("regional_indicator_symbol_","regional_indicator_");
            return name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";

        }
    }
    public String getNameAsEmoji(){
        String fName="[getNameAsEmoji]";
        try {
            String name=getName();
            name=":"+name+":";
            return name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";

        }
    }
}
