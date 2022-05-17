package models.lc.interaction.slash;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import models.lc.interaction.lcInteractionReceive;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.List;

@Deprecated
@ForCleanup1
@DeprecatedSince("11/24/21")
public class lcSlashInteractionReceive extends lcInteractionReceive {
    Logger logger = Logger.getLogger(getClass());
    public lcSlashInteractionReceive(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcSlashInteractionReceive(RawGatewayEvent event){
        String fName="build";
        logger.info(fName+".creating");
        set(event);
    }

    static public String keyName="name";
    static public String keyOptions="options",keyValue="value";
    public long getCommandId(){
        String fName="getCommandId";
        try {
            return jsonData.getLong(keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getName(){
        String fName="getName";
        try {
            return jsonData.getString(keyName);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getOptionPrime(){
        String fName="getOptions";
        try {
            int i=0;
            JSONObject jsonCollected=new JSONObject();
            jsonCollected.put(keyName,"");jsonCollected.put(keyValue,"");
            try {
                JSONObject jsonOptions=jsonData.getJSONArray(keyOptions).getJSONObject(0);
                if(jsonOptions.has(keyName)){
                    if(jsonCollected.getString(keyName).isBlank()){
                        jsonCollected.put(keyName,jsonOptions.getString(keyName));
                    }else{
                        jsonCollected.put(keyName,jsonCollected.getString(keyName)+"/"+jsonOptions.getString(keyName));
                    }

                }
                if(jsonOptions.has(keyValue)){
                    if(jsonCollected.getString(keyValue).isBlank()){
                        jsonCollected.put(keyValue,jsonOptions.getString(keyValue));
                    }else{
                        jsonCollected.put(keyValue,jsonCollected.getString(keyValue)+"/"+jsonOptions.getString(keyValue));
                    }
                }
                while(jsonOptions.has(keyOptions)&&i<50){
                    i++;
                    try {
                        jsonOptions=jsonOptions.getJSONArray(keyOptions).getJSONObject(0);
                        if(jsonOptions.has(keyName)){
                            if(jsonCollected.getString(keyName).isBlank()){
                                jsonCollected.put(keyName,jsonOptions.getString(keyName));
                            }else{
                                jsonCollected.put(keyName,jsonCollected.getString(keyName)+"/"+jsonOptions.getString(keyName));
                            }
                        }
                        if(jsonOptions.has(keyValue)){
                            if(jsonCollected.getString(keyValue).isBlank()){
                                jsonCollected.put(keyValue,jsonOptions.getString(keyValue));
                            }else{
                                jsonCollected.put(keyValue,jsonCollected.getString(keyValue)+"/"+jsonOptions.getString(keyValue));
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return  jsonCollected;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getOptionPrime_Name(){
        String fName="getOptions_Name";
        try {
            int i=0;
            StringBuilder value= new StringBuilder();
            try {
                JSONObject jsonOptions=jsonData.getJSONArray(keyOptions).getJSONObject(0);
                if(jsonOptions.has(keyName)){
                    if(value.toString().isBlank()){
                        value = new StringBuilder(jsonOptions.getString(keyName));
                    }else{
                        value.append("/").append(jsonOptions.getString(keyName));
                    }

                }
                while(jsonOptions.has(keyOptions)&&i<50){
                    i++;
                    try {
                        jsonOptions=jsonOptions.getJSONArray(keyOptions).getJSONObject(0);
                        if(jsonOptions.has(keyName)){
                            if(value.toString().isBlank()){
                                value = new StringBuilder(jsonOptions.getString(keyName));
                            }else{
                                value.append("/").append(jsonOptions.getString(keyName));
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return  value.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getOptionPrime_Value(){
        String fName="getOptions_Value";
        try {
            int i=0;
            StringBuilder value= new StringBuilder();
            try {
                JSONObject jsonOptions=jsonData.getJSONArray(keyOptions).getJSONObject(0);
                if(jsonOptions.has(keyValue)){
                    if(value.toString().isBlank()){
                        value = new StringBuilder(jsonOptions.getString(keyValue));
                    }else{
                        value.append("/").append(jsonOptions.getString(keyValue));
                    }
                }
                while(jsonOptions.has(keyOptions)&&i<50){
                    i++;
                    try {
                        jsonOptions=jsonOptions.getJSONArray(keyOptions).getJSONObject(0);
                        if(jsonOptions.has(keyValue)){
                            if(value.toString().isBlank()){
                                value = new StringBuilder(jsonOptions.getString(keyValue));
                            }else{
                                value.append("/").append(jsonOptions.getString(keyValue));
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return  value.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray getOptionsAsJson(){
        String fName="getOptionsAsJson";
        try {
            return  jsonData.getJSONArray(keyOptions);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getOptionSize(){
        String fName="getOptionSize";
        try {
            return  jsonData.getJSONArray(keyOptions).length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<lcINTERACTIONCREATE_Option> getOptions(){
        String fName="getOptions";
        try {
           List<lcINTERACTIONCREATE_Option>list=new ArrayList<>();
           for(int i=0;i< jsonData.getJSONArray(keyOptions).length();i++){
               try {
                   String name="",value="";int type=0; JSONArray options=new JSONArray();
                   JSONObject jsonOption= jsonData.getJSONArray(keyOptions).getJSONObject(i);
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
    public lcINTERACTIONCREATE_Option getOption(int i){
        String fName="getOption";
        try {
            String name="",value="";int type=0; JSONArray options=new JSONArray();
            try {
                JSONObject jsonOption= jsonData.getJSONArray(keyOptions).getJSONObject(i);
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
}
