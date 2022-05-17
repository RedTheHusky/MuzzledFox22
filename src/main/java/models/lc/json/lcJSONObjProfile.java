package models.lc.json;

import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONObjProfile {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONProfile]";
    lcJSONObjProfile(){
        String fName="[constructor]";
        logger.warn(cName+fName+".not read");
    }
    Guild gGuild;String gName;
    lcGlobalHelper gGlobal;
    public JSONObject jsonUser;
    public  Boolean isUpdated=false;
    public lcJSONObjProfile(lcGlobalHelper global, String name, Guild guild){
        String fName="[constructor]";
        try {
            gName=name;
            gGuild=guild;
            gGlobal=global;
            jsonUser=new JSONObject();
            logger.info(cName + fName + ".name:" + gName );
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName+fName+".read");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public Boolean isProfile(){
        String fName="[isProfile]";
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
    public Boolean createFieldEntry(String field){
        String fName="[createFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(!jsonUser.has(field)){
                jsonUser.put(field,new JSONObject() );
                logger.info(cName+fName+".success");
                isUpdated=true;return true;
            }
            logger.warn(cName+fName+".already exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean putFieldEntry(String name, Object value){
        String fName="[putFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            jsonUser.put(name,value);
            logger.info(cName+fName+".success");
            isUpdated=true;return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean hasFieldEntry(String name){
        String fName="[hasFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            Boolean r=jsonUser.has(name);
            logger.info(cName+fName+".response="+r);
            return r;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean putFieldEntry(String field,String name, Object value){
        String fName="[putFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            if(jsonUser.has(field)){
                jsonUser.getJSONObject(field).put(name,value);
                logger.info(cName+fName+".success");
                isUpdated=true;return  true;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean hasFieldEntry(String field,String name){
        String fName="[hasFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(field)){
                Boolean r=jsonUser.getJSONObject(field).has(name);
                logger.info(cName+fName+".response="+r);
                return r;
            }
            logger.warn(cName+fName+".invalid field");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public Object getFieldEntry(String field){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonUser.has(field)){
                Object value=jsonUser.get(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getFieldEntry2JSONObject(String field){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonUser.has(field)){
                JSONObject value=jsonUser.getJSONObject(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Object getFieldEntry(String field,String name){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(field)){
                if(jsonUser.getJSONObject(field).has(name)){
                    Object value=jsonUser.getJSONObject(field).get(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Boolean deleteFieldEntry(String field){
        String fName="[deleteFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonUser.has(field)){
                jsonUser.remove(field);
                logger.info(cName+fName+".success:");
                isUpdated=true;return true;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean deleteFieldEntry(String field, String name){
        String fName="[deleteFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonUser.has(field)){
                if(jsonUser.getJSONObject(field).has(name)){
                    jsonUser.getJSONObject(field).remove(name);
                    logger.info(cName+fName+".success");
                    isUpdated=true;return  true;
                }
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public Boolean hasProfile(String table){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+gName+"' and guild_id='"+gGuild.getId()+"'",colums);
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
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getProfile(String table){
        String fName="[getProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+gName+"' and guild_id='"+gGuild.getId()+"'",colums);
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
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean saveProfile(String table){
        String fName="[saveProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+gName+"' and guild_id='"+gGuild.getId()+"'",colums);
            if(rows==null){
                logger.warn(cName + fName + ".row is null");
                return false;
            }
            if(!rows.isEmpty()){
                data.put("json",jsonUser.toString());
                if (!gGlobal.sql.update(table, data,"name = '"+gName+"' and guild_id='"+gGuild.getId()+"'" )) {
                    logger.error(cName + fName + ".error while updating");
                }else{
                    isUpdated=false;logger.warn(cName + fName + ".success update"); return true;
                }
            }else{
                data.put("guild_id",gGuild.getId());
                data.put("name", gName);
                data.put("json",jsonUser.toString());
                if (!gGlobal.sql.insert(table, data)) {
                    logger.error(cName + fName + ".error while inserting");
                }else{
                    isUpdated=false;logger.warn(cName + fName + ".success inser"); return true;
                }
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int safetyCreateFieldEntry(String field) {
        String fName = "[safetyCreateFieldEntry]";
        try {
            logger.info(cName + fName + ".field=" + field);
            if (hasFieldEntry(field)) {
                logger.info(cName + fName + ".ignore");
                return -1;
            }
            if (createFieldEntry(field)) {
                isUpdated=true;
                logger.info(cName + fName + ".success");
                return 1;
            }
            logger.warn(cName + fName + ".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int safetyPutFieldEntry(String field, String name, Object value) {
        String fName = "[safetyPutFieldEntry]";
        try {
            logger.info(cName + fName + ".field=" + field);
            logger.info(cName + fName + ".name=" + name);
            logger.info(cName + fName + ".value=" + value);
            if (hasFieldEntry(field, name)) {
                logger.info(cName + fName + ".ignore");
                return -1;
            }
            if (putFieldEntry(field, name, value)) {
                isUpdated=true;
                logger.info(cName + fName + ".success");
                return 1;
            }
            logger.warn(cName + fName + ".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
}
