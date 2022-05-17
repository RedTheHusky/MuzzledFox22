package models.lc.json;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class lcJSONGlobalEntries {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONGlobalEntries]";
    lcGlobalHelper gGlobal;
    String gTable= llGlobalHelper.llv2_GlobalsSettings;
    public JSONObject jsonUser;
    public lcJSONGlobalEntries(lcGlobalHelper global){
        String fName="[constructor1]";
        try {
            gGlobal=global;
            jsonUser=new JSONObject();
            logger.info(cName + fName + ".table:" + gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGlobalEntries(lcGlobalHelper global, String table){
        String fName="[constructor2]";
        try {
            gGlobal=global;
            gTable=table;
            jsonUser=new JSONObject();
            logger.info(cName + fName + ".table:" + gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Boolean isExistent(){
        String fName="[isExistent]";
        try {
            if(jsonUser==null){
                logger.warn(cName+fName+".null");
                return false;
            }
            if(jsonUser.isEmpty()){
                logger.info(cName+fName+".empty");
                return false;
            }
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean addEntry(String name, JSONObject json){
        String fName="[addEntry.1]";
        try {
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            if(!jsonUser.has(name)){
                jsonUser.put(name,json);
                logger.info(cName+fName+".success");
                return true;
            }
            logger.warn(cName+fName+".already exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean setEntry(String name, JSONObject json){
        String fName="[setEntry.1]";
        try {
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            jsonUser.put(name,json);
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean updateEntry(String name, JSONObject json){
        String fName="[updateEntry.1]";
        try {
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            if(!jsonUser.has(name)){
                logger.warn(cName+fName+".does not exists");
                return false;
            }
            jsonUser.put(name,json);
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean removeEntry(String name){
        String fName="[removeEntry.1]";
        try {
            logger.info(cName+fName+".name="+name);
            if(!jsonUser.has(name)){
                logger.warn(cName+fName+".does not exists");
                return false;
            }
            jsonUser.remove(name);
            logger.info(cName+fName+".success");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean addEntry(lcJSONGuildProfile object){
        String fName="[addEntry.2]";
        try {
            String name=object.getName();
            JSONObject json=object.jsonObject;
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            if(!jsonUser.has(name)){
                jsonUser.put(name,json);
                logger.info(cName+fName+".success");
                return true;
            }
            logger.warn(cName+fName+".already exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean setEntry(lcJSONGuildProfile object){
        String fName="[setEntry.2]";
        try {
            String name=object.getName();
            JSONObject json=object.jsonObject;
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            jsonUser.put(name,json);
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean updateEntry(lcJSONGuildProfile object){
        String fName="[updateEntry.2]";
        try {
            String name=object.getName();
            JSONObject json=object.jsonObject;
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".json="+json.toString());
            if(!jsonUser.has(name)){
                logger.warn(cName+fName+".does not exists");
                return false;
            }
            jsonUser.put(name,json);
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean removeEntry(lcJSONGuildProfile object){
        String fName="[removeEntry.2]";
        try {
            String name=object.getName();
            logger.info(cName+fName+".name="+name);
            if(!jsonUser.has(name)){
                logger.warn(cName+fName+".does not exists");
                return false;
            }
            jsonUser.remove(name);
            logger.info(cName+fName+".success");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean has(String name){
        String fName="[has]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(name)){
                logger.info(cName+fName+".does exists");
                return true;
            }
            logger.warn(cName+fName+".does not exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJSON(String name){
        String fName="[getJSON]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(name)){
                logger.info(cName+fName+".does exists");
                JSONObject json=jsonUser.getJSONObject(name);
                logger.info(cName+fName+".json="+json.toString());
                return json;
            }
            logger.warn(cName+fName+".does not exists");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGlobalEntry getEntry(String name){
        String fName="[getEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(name)){
                logger.info(cName+fName+".does exists");
                JSONObject json=jsonUser.getJSONObject(name);
                logger.info(cName+fName+".json="+json.toString());
                lcJSONGlobalEntry entry=new lcJSONGlobalEntry(gGlobal,name,gTable);
                entry.jsonUser=json;
                return entry;
            }
            logger.warn(cName+fName+".does not exists");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Boolean dbGetEntry(String name){
        String fName="[dbGetEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            lcJSONGlobalEntry entry=new lcJSONGlobalEntry(gGlobal,name,gTable);
            if(entry.getEntry(gTable)){
                if(entry.isExistent()){
                    logger.info(cName+fName+".key exists="+name+" value="+entry.jsonUser.toString());
                    jsonUser.put(name,entry.jsonUser);
                    return true;
                }else{
                    logger.warn(cName+fName+".key non exists 2="+name);
                }
            }else{
                logger.warn(cName+fName+".key non exists 1="+name);
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean dbGetAllEntries(){
        String fName="[dbGetAllEntries]";
        logger.info(cName+fName);
        try{
            String query="";
            for(int attempt=1;attempt<=3;attempt++) {
                try{
                    if (!gGlobal.sql.checkConnection()) {
                        logger.error(cName + fName + ".no open connection");
                        return false;
                    }
                    List<String> columns=new ArrayList<>();
                    columns.add("id"); columns.add("name"); columns.add("json");
                    logger.info(cName+fName+".table="+gTable);
                    logger.info(cName+fName+".columns="+columns.toString());
                    query = "select * from "+gTable+"";
                    logger.info(cName+fName+".sql="+query);
                    PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs=pst.executeQuery();
                    logger.info(cName+fName+".executed");
                    while (rs.next()){
                        String rsName=rs.getString("name");
                        JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                        int rsId=rs.getInt("id");
                        logger.info(cName+fName+".rs:"+rsId+"|"+rsName+rsJson.toString());
                        jsonUser.put(rsName,rsJson);
                    }
                    return true;
                }catch(Exception e){
                    logger.error(cName + fName + ".exception["+attempt+"]:" + e);
                    if(attempt==3){
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        logger.error(cName + fName + ".query:" + query);
                    }
                }
            }
            return false;
        }
        catch(Exception e){
            logger.error(cName+fName+".exception:"+e);return false;
        }

    }
    public void dbRefresh(){
        String fName="[dbRefresh]";
        try {
            Set<String> keys=jsonUser.keySet();
            for (String f : keys) {
                lcJSONGlobalEntry entry = new lcJSONGlobalEntry(gGlobal, f, gTable);
                if (entry.getEntry(gTable)) {
                    if (entry.isExistent()) {
                        logger.info(cName + fName + ".key exists=" + f + " value=" + entry.jsonUser.toString());
                        jsonUser.put(f, entry.jsonUser);
                    } else {
                        logger.warn(cName + fName + ".key non exists 2=" + f);
                    }
                } else {
                    logger.info(cName + fName + ".key non exists 1=" + f);
                }
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
