package models.lc.helper;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class lcWebImages {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONGeneralEntries]";
    lcGlobalHelper gGlobal;
    public JSONObject gJSON=new JSONObject();
    public String gTable=""; 
    public lcWebImages(lcGlobalHelper global){
        String fName="[constructor1]";
        gGlobal=global;
        logger.info(cName + fName + ".table:" + gTable);
    }
    public lcWebImages(lcGlobalHelper global, String table){
        String fName="[constructor1]";
        gGlobal=global;gTable=table;
        try{
            logger.info(cName + fName + ".table:" + gTable);
        } catch (Exception e) {
            logger.info(cName + fName + ".exception=" + e);
        }
    }


    public int ratingNull=0, ratingGeneral=1, ratingMature=2,ratingMature_Nudity=3,ratingMature_MildViolence=4, ratingAdult=5,ratingAdult_SexualTheme=6,ratingAdult_StrongViolence=7, ratingExplicit=8;
    String fieldAuthor="author", fieldSource="source",fieldUrl="url",fieldTags="tags",fieldCategories="",fieldRating="rating";
    public String filterAuthor="", filterSource="",filterUrl="",filterTags="",filterCategories="";
    int filterRating=0;

    public void clear(){
        String fName="[clear]";
        try{
            String gUrl="";
            String gSource="";
            String gAuthor="";
            JSONArray gTags=new JSONArray();
            JSONArray gCategories=new JSONArray();
            int gRating=0;
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
    private void clearFilter(){
        String fName="[clearFilter]"; logger.info(cName + fName);
        filterAuthor=""; filterSource="";filterUrl="";filterTags="";filterCategories="";filterRating=0;
    }
    private boolean filter(JSONObject object){
        String fName="[filter]";
        try{
            String gUrl="",gSource="",gAuthor="";
            JSONArray gTags=new JSONArray(),gCategories=new JSONArray();
            int  gRating=0;
            if(object.has(fieldAuthor)&&!object.isNull(fieldAuthor)){
                gAuthor=object.getString(fieldAuthor);
            }
            if(object.has(fieldSource)&&!object.isNull(fieldSource)){
                gSource=object.getString(fieldSource);
            }
            if(object.has(fieldUrl)&&!object.isNull(fieldUrl)){
                gUrl=object.getString(fieldUrl);
            }
            if(object.has(fieldRating)&&!object.isNull(fieldRating)){
                gRating=object.getInt(fieldRating);
            }
            if(object.has(fieldTags)&&!object.isNull(fieldTags)){
                gTags=object.getJSONArray(fieldTags);
            }
            if(object.has(fieldCategories)&&!object.isNull(fieldCategories)){
                gCategories=object.getJSONArray(fieldCategories);
            }
            logger.info(cName + fName + ".gUrl=" + gUrl);
            logger.info(cName + fName + ".gAuthor=" + gAuthor);
            logger.info(cName + fName + ".gSource=" + gSource);
            logger.info(cName + fName + ".gRating=" +gRating);
            logger.info(cName + fName + ".gTags=" +gTags);
            logger.info(cName + fName + ".gCategories=" +gCategories);
            if(!gUrl.isEmpty()){
                logger.info(cName + fName + ".has no url");return  false;
            }
            if(!filterAuthor.isEmpty()&&!gAuthor.isEmpty()&&!filterAuthor.equalsIgnoreCase(gAuthor)){
                logger.info(cName + fName + ".notsame Author=" + filterAuthor+"|"+gAuthor);return  false;
            }
            if(filterRating>0&&filterRating!=gRating){
                logger.info(cName + fName + ".notsame Rating=" + filterRating+"|"+gRating);return  false;
            }
            if(gCategories!=null&&!gCategories.isEmpty()&&!filterCategories.isEmpty()){
                boolean isCategoryOK = false;
                for(int i=0;i<gCategories.length();i++){
                    if(gCategories.getString(i).equalsIgnoreCase(filterCategories)){
                        isCategoryOK=true;
                    }
                }
                if(!isCategoryOK){
                    logger.info(cName + fName + ".has no such categories: "+filterCategories);return  false;
                }
            }
            logger.info(cName + fName + ".all seams right");
           return true;
        } catch (Exception e) {
            logger.info(cName + fName + ".exception=" + e);return  false;
        }
    }
    public Boolean dbGetAllEntries(String table){
        String fName="[dbGetAllEntries]";
        logger.info(cName+fName);
        if(table.isEmpty()){
            logger.error(cName + fName + ".invalid table");
            return false;
        }
        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        try{
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("url");columns.add("source");columns.add("json");
            logger.info(cName+fName+".table="+table);
            logger.info(cName+fName+".columns="+columns.toString());
            String query = "select * from "+table;
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            while (rs.next()){
                JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                int rsId=rs.getInt("id");
                logger.info(cName+fName+".rs:"+rsId+"|"+rsJson.toString());
                rsJson.put("url",rs.getString("url"));rsJson.put("source",rs.getString("source"));
                gJSON.put(String.valueOf(rsId),rsJson);
            }
            return true;
        }
        catch(Exception e){
            logger.error(cName+fName+".exception:"+e);return false;
        }

    }
    public Boolean dbGetAllEntries(){
        String fName="[dbGetAllEntries]";
        logger.info(cName+fName);
        return dbGetAllEntries(gTable);
    }
    public Boolean dbGetAllEntriesWithFilter(String table){
        String fName="[dbGetAllEntries]";
        logger.info(cName+fName);
        if(table.isEmpty()){
            logger.error(cName + fName + ".invalid table");
            return false;
        }
        if (!gGlobal.sql.checkConnection()) {
            logger.error(cName + fName + ".no open connection");
            return false;
        }
        try{
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("url");columns.add("source");columns.add("json");
            logger.info(cName+fName+".table="+table);
            logger.info(cName+fName+".columns="+columns.toString());
            String query = "select * from "+table;
            logger.info(cName+fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(cName+fName+".executed");
            while (rs.next()){
                JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                int rsId=rs.getInt("id");
                logger.info(cName+fName+".rs:"+rsId+"|"+rsJson.toString());
                if(filter(rsJson)){
                    rsJson.put("url",rs.getString("url"));rsJson.put("source",rs.getString("source"));
                    gJSON.put(String.valueOf(rsId),rsJson);
                }
            }
            return true;
        }
        catch(Exception e){
            logger.error(cName+fName+".exception:"+e);return false;
        }

    }
}
