package models.lc.json;

import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONGlobalEntry extends lcJSONGenericEntry{
    Logger logger = Logger.getLogger(getClass());  String cName="[lcJSONGlobalSettingEntry]";
     public String gName;
    lcGlobalHelper gGlobal;
    String gTable= llGlobalHelper.llv2_GlobalsSettings;
    public JSONObject jsonUser=new JSONObject(); public  Boolean isUpdated=false;
    public lcJSONGlobalEntry(lcGlobalHelper global, String name){
        String fName="[constructor1]";
        try {
            gName=name;
            gGlobal=global;
            logger.info(cName + fName + ".name:" + gName );
            logger.info(cName + fName + ".table:" + gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGlobalEntry(lcGlobalHelper global, String name,  boolean autoget){
        String fName="[constructor2]";
        try {
            gName=name;
            gGlobal=global;
            logger.info(cName + fName + ".name:" + gName );
            logger.info(cName + fName + ".table:" + gTable);
            logger.info(cName+fName+".autoget="+autoget);
            if(autoget){
                getEntry(gTable,gName);
            }
            logger.info(cName+fName+".ready");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGlobalEntry(lcGlobalHelper global, String name, String table){
        String fName="[constructor2]";
        try {
            gName=name;
            gGlobal=global;
            gTable=table;
            logger.info(cName + fName + ".name:" + gName );
            logger.info(cName + fName + ".table:" + gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGlobalEntry(lcGlobalHelper global, String name,  String table, boolean autoget){
        String fName="[constructor2]";
        try {
            gName=name;
            gGlobal=global;
            gTable=table;
            logger.info(cName + fName + ".name:" + gName );
            logger.info(cName + fName + ".table:" + gTable);
            logger.info(cName+fName+".autoget="+autoget);
            if(autoget){
                getEntry(gTable,gName);
            }
            logger.info(cName+fName+".ready");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


    public Boolean hasEntry(String table,String name){
        String fName="[hasEntry]";
        logger.info(cName+fName);

        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        List<String> colums= new ArrayList<>();
        colums.add("id");colums.add("name");colums.add("json");
        Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+name+"'",colums);
        if(rows==null){logger.error(cName+fName+".select null"); return false;}
        boolean isGo=false;
        for(Map.Entry<String, Object> entry : rows.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equalsIgnoreCase("json") && value != null) {
                isGo = true;
                break;
            }
        }
        if(!isGo){logger.error(cName+fName+".no go"); return false; }
        return true;
    }
    public Boolean getEntry(String table,String name){
        String fName="[getEntry]";
        logger.info(cName+fName);


        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        List<String> colums= new ArrayList<>();
        colums.add("id");colums.add("name");colums.add("json");
        Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+name+"'",colums);
        if(rows==null){logger.error(cName+fName+".select null"); return false; }
        boolean isGo=false;
        String profile="";
        for(Map.Entry<String, Object> entry : rows.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equalsIgnoreCase("json")&&value!=null){
                isGo=true; profile=value.toString();
            }
        }
        if(!isGo){logger.error(cName+fName+".no go");return false; }
        jsonUser=new JSONObject(profile);
        logger.info(cName+fName+".success:");
        return true;
    }
    public Boolean saveEntry(String table,String name){
        String fName="[saveEntry]";
        logger.info(cName+fName);
        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        Map<String, Object> data = new LinkedHashMap<>();
        List<String> colums= new ArrayList<>();
        colums.add("id");colums.add("name");colums.add("json");
        Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+name+"'",colums);
        if(rows==null){
            logger.warn(cName + fName + ".row is null");
            return false;
        }
        if(!rows.isEmpty()){
            data.put("json",jsonUser.toString());
            if (!gGlobal.sql.update(table, data,"name = '"+name+"'")) {
                logger.error(cName + fName + ".error while updating");
            }else{
                logger.info(cName + fName + ".success update");isUpdated=false;return true;
            }
        }else{
            data.put("name", name);
            data.put("json",jsonUser.toString());
            if (!gGlobal.sql.insert(table, data)) {
                logger.error(cName + fName + ".error while inserting");
            }else{
                logger.info(cName + fName + ".success insert");isUpdated=false;return true;
            }
        }
        return false;
    }
    public Boolean hasEntry(String table){
        String fName="[hasEntry]";
        logger.info(cName+fName);
        return hasEntry(table,gName);
    }
    public Boolean getEntry(String table){
        String fName="[getEntry]";
        logger.info(cName+fName);
        return getEntry(table,gName);
    }
    public Boolean saveEntry(String table){
        String fName="[saveEntry]";
        logger.info(cName+fName);
        return saveEntry(table,gName);
    }
    public Boolean hasEntry(){
        String fName="[hasEntry]";
        logger.info(cName+fName);
        return hasEntry(gTable,gName);
    }
    public Boolean getEntry(){
        String fName="[getEntry]";
        logger.info(cName+fName);
        return getEntry(gTable,gName);
    }
    public Boolean saveEntry(){
        String fName="[saveEntry]";
        logger.info(cName+fName);
        return saveEntry(gTable,gName);
    }

}
