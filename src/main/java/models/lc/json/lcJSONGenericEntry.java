package models.lc.json;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONGenericEntry {
    Logger logger = Logger.getLogger(getClass());  String cName="[lcJSONGenericEntry]";
    public JSONObject jsonObject =new JSONObject(); public  Boolean isUpdated=false;
    public lcJSONGenericEntry(){
        String fName="[constructor1]";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public JSONObject getJson(){
        String fName="[getJson]";
        try {
            logger.info(cName+fName+".success");
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONGenericEntry setJson(JSONObject jsonObject){
        String fName="[setJson]";
        try {
            this.jsonObject=jsonObject;
            logger.info(cName+fName+".success");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public Boolean isExistent(){
        String fName="[isExistent]";
        try {
            if(jsonObject ==null){
                logger.warn(cName+fName+".null");
                return false;
            }
            if(jsonObject.isEmpty()){
                logger.info(cName+fName+".empty");
                return false;
            }
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean isProfile(){
        String fName="[isProfile]";
        try {
            if(jsonObject ==null){
                logger.warn(cName+fName+".null");
                return false;
            }
            if(jsonObject.isEmpty()){
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
            if(!jsonObject.has(field)){
                jsonObject.put(field,new JSONObject() );
                logger.info(cName+fName+".success");isUpdated=true;
                return true;
            }
            logger.warn(cName+fName+".already exists");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean putFieldEntry(String name, Object value){
        String fName="[putFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            jsonObject.put(name,value);
            logger.info(cName+fName+".success");isUpdated=true;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean hasFieldEntry(String name){
        String fName="[hasFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(name)&&!jsonObject.isNull(name)){
                logger.info(cName+fName+".valid");return true;
            }
            logger.warn(cName+fName+".invalid field");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean putFieldEntry(String field,String name, Object value){
        String fName="[putFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            if(jsonObject.has(field)){
                jsonObject.getJSONObject(field).put(name,value);
                logger.info(cName+fName+".success");isUpdated=true;
                return  true;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean hasFieldEntry(String field,String name){
        String fName="[hasFieldEntry]";
        try {
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)&&!jsonObject.isNull(field)){
                if(jsonObject.getJSONObject(field).has(name)&&!jsonObject.getJSONObject(field).isNull(name)){
                    logger.info(cName+fName+".valid");return true;
                }
                logger.info(cName+fName+".invalid key");
                return false;
            }
            logger.warn(cName+fName+".invalid field");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public Object getFieldEntry(String field){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                Object value= jsonObject.get(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public Object getFieldEntry(String field,String name){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    Object value= jsonObject.getJSONObject(field).get(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public boolean getFieldEntryAsBoolean(String field){
        String fName="[getFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                boolean value= jsonObject.getBoolean(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getFieldEntryAsBoolean(String field,String name){
        String fName="[getFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    boolean value= jsonObject.getJSONObject(field).getBoolean(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
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
    public int getFieldEntryAsInt(String field){
        String fName="[getFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                int value= jsonObject.getInt(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getFieldEntryAsInt(String field,String name){
        String fName="[getFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    int value= jsonObject.getJSONObject(field).getInt(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }

    }
    public long getFieldEntryAsLong(String field){
        String fName="[getFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                long value= jsonObject.getLong(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getFieldEntryAsLong(String field,String name){
        String fName="[getFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    long value= jsonObject.getJSONObject(field).getLong(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }

    }
    public String getFieldEntryAsString(String field){
        String fName="[getFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                String value= jsonObject.getString(field);
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
    public String getFieldEntryAsString(String field,String name){
        String fName="[getFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    String value= jsonObject.getJSONObject(field).getString(name);
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
    public JSONObject getFieldEntryAsJSONObject(String field){
        String fName="[getFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONObject(field);
                logger.info(cName+fName+".success:"+value.toString());
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
    public JSONObject getFieldEntryAsJSONObject(String field,String name){
        String fName="[getFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONObject value= jsonObject.getJSONObject(field).getJSONObject(name);
                    logger.info(cName+fName+".success:"+value.toString());
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
    public JSONArray getFieldEntryAsJSONArray(String field){
        String fName="[getFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field);
                logger.info(cName+fName+".success:"+value.toString());
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
    public JSONArray getFieldEntryAsJSONArray(String field,String name){
        String fName="[getFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONArray value= jsonObject.getJSONObject(field).getJSONArray(name);
                    logger.info(cName+fName+".success:"+value.toString());
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

    public Object getFieldEntry(String field,int index){
        String fName="[getFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                Object value= jsonObject.getJSONArray(field).get(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public boolean getFieldEntryAsBoolean(String field,int index){
        String fName="[getFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                boolean value= jsonObject.getJSONArray(field).getBoolean(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public int getFieldEntryAsInt(String field,int index){
        String fName="[getFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                int value= jsonObject.getJSONArray(field).getInt(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }

    }
    public long getFieldEntryAsLong(String field,int index){
        String fName="[getFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                long value= jsonObject.getJSONArray(field).getLong(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }

    }
    public String getFieldEntryAsString(String field,int index){
        String fName="[getFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                String value= jsonObject.getJSONArray(field).getString(index);
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
    public JSONObject getFieldEntryAsJSONObject(String field,int index){
        String fName="[getFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONArray(field).getJSONObject(index);
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
    public JSONArray getFieldEntryAsJSONArray(String field,int index){
        String fName="[getFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field).getJSONArray(index);
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

    public Boolean deleteFieldEntry(String field){
        String fName="[deleteFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                jsonObject.remove(field);
                logger.info(cName+fName+".success:");isUpdated=true;
                return true;
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean deleteFieldEntry(String field, String name){
        String fName="[deleteFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    jsonObject.getJSONObject(field).remove(name);
                    logger.info(cName+fName+".success");isUpdated=true;
                    return  true;
                }
            }
            logger.warn(cName+fName+".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
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
            return  0;
        }
    }
    public int safetyPutFieldEntry(String field, Object value){
        String fName="[safetyPutFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".value="+value);
            if(hasFieldEntry(field)){
                logger.info(cName + fName + ".ignore");return  -1;
            }
            if(putFieldEntry(field,value)){
                isUpdated=true;
                logger.info(cName + fName + ".json="+ jsonObject);
                logger.info(cName + fName + ".success");return  1;
            }
            logger.warn(cName + fName + ".failed");return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public int safetyPutFieldEntry(String field,String name, Object value){
        String fName="[safetyPutFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            if(hasFieldEntry(field,name)){
                logger.info(cName + fName + ".ignore");return  -1;
            }
            if(putFieldEntry(field,name,value)){
                isUpdated=true;
                logger.info(cName + fName + ".success");return  1;
            }
            logger.warn(cName + fName + ".failed");return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }

    public Object opt2FieldEntry(String field,Object def){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                Object value= jsonObject.get(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  def;
        }
    }
    public Object opt2FieldEntry(String field,String name,Object def){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    Object value= jsonObject.getJSONObject(field).get(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  def;
        }
    }
    public boolean opt2FieldEntryAsBoolean(String field,boolean def){
        String fName="[optFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                boolean value= jsonObject.getBoolean(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public boolean opt2FieldEntryAsBoolean(String field,String name,boolean def){
        String fName="[optFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    boolean value= jsonObject.getJSONObject(field).getBoolean(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public int opt2FieldEntryAsInt(String field,int def){
        String fName="[optFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                int value= jsonObject.getInt(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public int opt2FieldEntryAsInt(String field,String name,int def){
        String fName="[optFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    int value= jsonObject.getJSONObject(field).getInt(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public long opt2FieldEntryAsLong(String field,long def){
        String fName="[optFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                long value= jsonObject.getLong(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public long opt2FieldEntryAsLong(String field,String name,long def){
        String fName="[optFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    long value= jsonObject.getJSONObject(field).getLong(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public String opt2FieldEntryAsString(String field,String def){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                String value= jsonObject.getString(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public String opt2FieldEntryAsString(String field,String name,String def){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    String value= jsonObject.getJSONObject(field).getString(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public JSONObject opt2FieldEntryAsJSONObject(String field,JSONObject def){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONObject(field);
                logger.info(cName+fName+".success:"+value.toString());
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public JSONObject opt2FieldEntryAsJSONObject(String field,String name,JSONObject def){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONObject value= jsonObject.getJSONObject(field).getJSONObject(name);
                    logger.info(cName+fName+".success:"+value.toString());
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public JSONArray opt2FieldEntryAsJSONArray(String field,JSONArray def){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field);
                logger.info(cName+fName+".success:"+value.toString());
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }
    }
    public JSONArray opt2FieldEntryAsJSONArray(String field,String name,JSONArray def){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONArray value= jsonObject.getJSONObject(field).getJSONArray(name);
                    logger.info(cName+fName+".success:"+value.toString());
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }

    public Object opt2FieldEntry(String field,int index,Object def){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                Object value= jsonObject.getJSONArray(field).get(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  def;
        }
    }
    public boolean opt2FieldEntryAsBoolean(String field,int index,boolean def){
        String fName="[optFieldEntryAsBoolean]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                boolean value= jsonObject.getJSONArray(field).getBoolean(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public int opt2FieldEntryAsInt(String field,int index,int def){
        String fName="[optFieldEntryAsInt]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                int value= jsonObject.getJSONArray(field).getInt(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public long opt2FieldEntryAsLong(String field,int index,long def){
        String fName="[optFieldEntryAsLong]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                long value= jsonObject.getJSONArray(field).getLong(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public String opt2FieldEntryAsString(String field,int index,String def){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                String value= jsonObject.getJSONArray(field).getString(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public JSONObject opt2FieldEntryAsJSONObject(String field,int index,JSONObject def){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONArray(field).getJSONObject(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }
    public JSONArray opt2FieldEntryAsJSONArray(String field,int index,JSONArray def){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field).getJSONArray(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return def;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return def;
        }

    }


    public Object optFieldEntry(String field){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                Object value= jsonObject.get(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return  new Object();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return   new Object();
        }
    }
    public Object optFieldEntry(String field,String name){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    Object value= jsonObject.getJSONObject(field).get(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return  new Object();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new Object();
        }
    }
    public String optFieldEntryAsString(String field){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                String value= jsonObject.getString(field);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String optFieldEntryAsString(String field,String name){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    String value= jsonObject.getJSONObject(field).getString(name);
                    logger.info(cName+fName+".success:"+value);
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }

    }
    public JSONObject optFieldEntryAsJSONObject(String field){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONObject(field);
                logger.info(cName+fName+".success:"+value.toString());
                return value;
            }
            logger.warn(cName+fName+".failed");
            return new JSONObject();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject optFieldEntryAsJSONObject(String field,String name){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONObject value= jsonObject.getJSONObject(field).getJSONObject(name);
                    logger.info(cName+fName+".success:"+value.toString());
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return new JSONObject();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }

    }
    public JSONArray optFieldEntryAsJSONArray(String field){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field);
                logger.info(cName+fName+".success:"+value.toString());
                return value;
            }
            logger.warn(cName+fName+".failed");
            return new JSONArray();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray optFieldEntryAsJSONArray(String field,String name){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            if(jsonObject.has(field)){
                if(jsonObject.getJSONObject(field).has(name)){
                    JSONArray value= jsonObject.getJSONObject(field).getJSONArray(name);
                    logger.info(cName+fName+".success:"+value.toString());
                    return value;
                }
            }
            logger.warn(cName+fName+".failed");
            return new JSONArray();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }

    }

    public Object optFieldEntry(String field,int index){
        String fName="[optFieldEntry]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                Object value= jsonObject.getJSONArray(field).get(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return new Object();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new Object();
        }
    }
    public String optFieldEntryAsString(String field,int index){
        String fName="[optFieldEntryAsString]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                String value= jsonObject.getJSONArray(field).getString(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }

    }
    public JSONObject optFieldEntryAsJSONObject(String field,int index){
        String fName="[optFieldEntryAsJSONObject]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONObject value= jsonObject.getJSONArray(field).getJSONObject(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return new JSONObject();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }

    }
    public JSONArray optFieldEntryAsJSONArray(String field,int index){
        String fName="[optFieldEntryAsJSONArray]";
        try {
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".index="+index);
            if(jsonObject.has(field)){
                JSONArray value= jsonObject.getJSONArray(field).getJSONArray(index);
                logger.info(cName+fName+".success:"+value);
                return value;
            }
            logger.warn(cName+fName+".failed");
            return new JSONArray();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }

    }

    public lcJSONGenericEntry clear(){
        String fName="[clear]";
        try {
            jsonObject =new JSONObject();
            logger.info(cName+fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
