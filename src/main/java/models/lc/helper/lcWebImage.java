package models.lc.helper;


import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class lcWebImage {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONGeneralEntries]";
    lcGlobalHelper gGlobal;
    public JSONObject gEntry;
    public String gTable=""; public int gID=-1;
    public  boolean isUpdated=false;
    public lcWebImage(lcGlobalHelper global){
        String fName="[constructor1]";
        gGlobal=global;
        gEntry =null;
        logger.info(cName + fName + ".id:" + gID);
        logger.info(cName + fName + ".table:" + gTable);
    }

    public String gUrl="";
    public String gSource="";
    public String gAuthor="";
    public JSONArray gTags=new JSONArray();
    public JSONArray gCategories=new JSONArray();
    public int gRating=0; int ratingNull=0, ratingGeneral=1, ratingMature=2,ratingMature_Nudity=3,ratingMature_MildViolence=4, ratingAdult=5,ratingAdult_SexualTheme=6,ratingAdult_StrongViolence=7, ratingExplicit=8;
   
    String fieldAuthor="author", fieldSource="source",fieldUrl="url",fieldTags="tags",fieldCategories="",fieldRating="rating";
    public void extract(){
        String fName="[extract]";
        try{
            if(gEntry.has(fieldAuthor)&&!gEntry.isNull(fieldAuthor)){
                gAuthor=gEntry.getString(fieldAuthor);
            }
            if(gEntry.has(fieldSource)&&!gEntry.isNull(fieldSource)){
                gSource=gEntry.getString(fieldSource);
            }
            if(gEntry.has(fieldUrl)&&!gEntry.isNull(fieldUrl)){
                gUrl=gEntry.getString(fieldUrl);
            }
            if(gEntry.has(fieldRating)&&!gEntry.isNull(fieldRating)){
                gRating=gEntry.getInt(fieldRating);
            }
            if(gEntry.has(fieldTags)&&!gEntry.isNull(fieldTags)){
                gTags=gEntry.getJSONArray(fieldTags);
            }
            if(gEntry.has(fieldCategories)&&!gEntry.isNull(fieldCategories)){
                gCategories=gEntry.getJSONArray(fieldCategories);
            }
            logger.info(cName + fName + ".gUrl=" + gUrl);
            logger.info(cName + fName + ".gAuthor=" + gAuthor);
            logger.info(cName + fName + ".gSource=" + gSource);
            logger.info(cName + fName + ".gRating=" +gRating);
            logger.info(cName + fName + ".gTags=" +gTags);
            logger.info(cName + fName + ".gCategories=" +gCategories);
        } catch (Exception e) {
            logger.info(cName + fName + ".exception=" + e);
        }
    }
    public Boolean getById(String table,int id){
        String fName="[getById]";
        logger.info(cName+fName);
        logger.info(cName+fName+"table="+table);
        logger.info(cName+fName+"id="+id);
        if(table.isEmpty()){
            logger.error(cName + fName + ".invalid table");
            return false;
        }
        if(id<0){
            logger.error(cName + fName + ".invalid id");
            return false;
        }
        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        List<String> colums= new ArrayList<>();
        colums.add("id");colums.add("json");colums.add("url");colums.add("source");
        Map<String,Object> rows=gGlobal.sql.selectFirst(table,"id = '"+id+"'",colums);
        if(rows==null){logger.error(cName+fName+".select null"); return false; }
        boolean isGo=false;
        String profile="";
        String url="", source="";
        for(Map.Entry<String, Object> entry : rows.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equalsIgnoreCase("json")&&value!=null){
                isGo=true; profile=value.toString();
            }
            if(key.equalsIgnoreCase("url")&&value!=null){
                url=value.toString();
            }
            if(key.equalsIgnoreCase("source")&&value!=null){
                source=value.toString();
            }
        }
        if(!isGo){logger.error(cName+fName+".no go");return false; }
        gEntry=new JSONObject(profile);
        gEntry.put("url",url);gEntry.put("source",source);
        logger.info(cName+fName+".success:");gID=id;
        return true;
    }
    public Boolean getById(int id){
        String fName="[getById]";
        logger.info(cName+fName);
        logger.info(cName+fName+"id="+id);
        return  getById(gTable,id);
    }
    public Boolean getById(String table){
        String fName="[getById]";
        logger.info(cName+fName);
        logger.info(cName+fName+" table="+ table);
        return  getById(table,gID);
    }
    public Boolean getById(){
        String fName="[getById]";
        logger.info(cName+fName);
        return  getById(gTable,gID);
    }

}
