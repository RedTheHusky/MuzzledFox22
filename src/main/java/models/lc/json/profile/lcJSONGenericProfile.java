package models.lc.json.profile;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcJSONGenericEntry;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcJSONGenericProfile extends lcJSONGenericEntry {
    Logger logger = Logger.getLogger(getClass());  String cName="[lcJSONGenericProfile]";
    protected String kName,gTable;
    protected lcGlobalHelper gGlobal;
    public lcJSONGenericProfile setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null)throw  new Exception(cName+fName+", global cant be null!");
            gGlobal=global;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGenericProfile setTable(String table){
        String fName="[setTable]";
        try {
            logger.info(cName+fName+"table="+table);
            if(table==null)throw  new Exception(cName+fName+", input table cant be null!");
            if(table.isBlank())throw  new Exception(cName+fName+", input table cant be blank!");
            gTable=table;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGenericProfile setName(String name){
        String fName="[setName]";
        try {
            logger.info(cName+fName+"name="+name);
            if(name==null)throw  new Exception(cName+fName+", input name cant be null!");
            if(name.isBlank())throw  new Exception(cName+fName+", input name cant be blank!");
            kName =name;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGenericProfile clearTable(){
        String fName="[clearTable]";
        try {
            logger.info(cName+fName+"table_old="+gTable);
            gTable=null;
            logger.info(cName+fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGenericProfile clearName(){
        String fName="[clearName]";
        try {
            logger.info(cName+fName+"name_old="+kName);
            kName=null;
            logger.info(cName+fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGenericProfile clearJson(){
        String fName="[clearJson]";
        try {
            clear();
            logger.info(cName+fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getTable(){
        String fName="[getTable]";
        try {
            logger.info(cName+fName+"table="+gTable);
            return gTable;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    public String getName(){
        String fName="[getName]";
        try {
            logger.info(cName+fName+"name="+ kName);
            return kName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    public Boolean isNameBlank(){
        String fName="[isNameBlank]";
        try {
            logger.info(cName+fName+"name="+ kName);
            if(kName ==null)throw  new Exception(cName+fName+", name cant be null!");
            if(kName.isBlank())throw  new Exception(cName+fName+", name cant be blank!");
            boolean result= kName.isBlank();
            logger.info(cName+fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean isTabBlank(){
        String fName="[isTabBlank]";
        try {
            logger.info(cName+fName+"tab="+gTable);
            if(gTable==null)throw  new Exception(cName+fName+", tab cant be null!");
            if(gTable.isBlank())throw  new Exception(cName+fName+", tab cant be blank!");
            boolean result=gTable.isBlank();
            logger.info(cName+fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
