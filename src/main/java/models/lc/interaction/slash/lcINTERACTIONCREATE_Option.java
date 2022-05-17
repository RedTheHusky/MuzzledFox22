package models.lc.interaction.slash;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import net.dv8tion.jda.annotations.DeprecatedSince;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
@ForCleanup1
@DeprecatedSince("11/24/21")
public class lcINTERACTIONCREATE_Option {
    Logger logger = Logger.getLogger(getClass());
    String gName="",gValue="";JSONArray gOptions=new JSONArray();int gType=0;

    public lcINTERACTIONCREATE_Option(String name, String value, JSONArray options,int type){
        String fName="build";
        try {
            logger.info(fName+".creating");
            if(name!=null)gName=name;
            if(value!=null)gValue=value;
            if(options!=null)gOptions=options;
            gType=type;
            logger.info(fName+".gName="+gName);
            logger.info(fName+".gValue="+gValue);
            logger.info(fName+".gOptions="+gOptions);
            logger.info(fName+".gType="+gType);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public String getName(){
        String fName="getName";
        try {
            logger.info(fName+".do");
            return  gName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getValue(){
        String fName="getValue";
        try {
            logger.info(fName+".do");
            return  gValue;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getValueAsLong(){
        String fName="getValueAsLong";
        try {
            logger.info(fName+".do");
            return Long.parseLong(gValue.replaceAll("<","").replaceAll("@","").replaceAll("!","").replaceAll("#","").replaceAll(">","").replaceAll("@",""));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public int getValueAsInt(){
        String fName="getValueAsInt";
        try {
            logger.info(fName+".do");
            return Integer.parseInt(gValue.replaceAll("<","").replaceAll("@","").replaceAll("!","").replaceAll("#","").replaceAll(">","").replaceAll("@",""));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean getValueABoolean(){
        String fName="getValueAsInt";
        try {
            logger.info(fName+".do");
            return Boolean.parseBoolean(gValue.replaceAll("<","").replaceAll("@","").replaceAll("!","").replaceAll("#","").replaceAll(">","").replaceAll("@",""));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getOptionsSize(){
        String fName="getOptionsSize";
        try {
            logger.info(fName+".do");
            return gOptions.length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONArray getOptionsJson(){
        String fName="getOptionsJson";
        try {
            logger.info(fName+".do");
            return gOptions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String keyName="name",keyValue="value",keyOptions="options",keyType="type";
    public lcINTERACTIONCREATE_Option getOption(int i){
        String fName="getOption";
        try {
            logger.info(fName+".i="+i);
            JSONObject jsonOption=gOptions.getJSONObject(i);
            String name="",value=""; JSONArray options=new JSONArray(); int type=0;
            try {
                if(jsonOption.has(keyName)){
                    name=jsonOption.getString(keyName);
                }
                if(jsonOption.has(keyValue)){
                    value=jsonOption.getString(keyValue);
                }
                if(jsonOption.has(keyOptions)){
                    options=jsonOption.getJSONArray(keyOptions);
                }
                if(jsonOption.has(keyType)){
                    type=jsonOption.getInt(keyType);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            lcINTERACTIONCREATE_Option newOptions=new lcINTERACTIONCREATE_Option(name,value,options,type);
            return  newOptions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcINTERACTIONCREATE_Option> getOptions(){
        String fName="getOptions";
        try {
            List<lcINTERACTIONCREATE_Option>list=new ArrayList<>();
            for(int i=0;i< gOptions.length();i++){
                try {
                    String name="",value="";int type=0; JSONArray options=new JSONArray();
                    JSONObject jsonOption= gOptions.getJSONObject(i);
                    if(jsonOption.has(keyName)){
                        name=jsonOption.getString(keyName);
                    }
                    if(jsonOption.has(keyValue)){
                        value=jsonOption.getString(keyValue);
                    }
                    if(jsonOption.has(keyOptions)){
                        options=jsonOption.getJSONArray(keyOptions);
                    }
                    if(jsonOption.has(keyType)){
                        type=jsonOption.getInt(keyType);
                    }
                    lcINTERACTIONCREATE_Option newOptions=new lcINTERACTIONCREATE_Option(name,value,options,type);
                    list.add(newOptions);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
}
