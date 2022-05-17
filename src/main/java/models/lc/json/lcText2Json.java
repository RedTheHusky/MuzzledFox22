package models.lc.json;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcText2Json {
    Logger logger = Logger.getLogger(getClass());
    public JSONObject jsonObject =new JSONObject();
    public JSONArray jsonArray=new JSONArray();
    private  int type=0;
    public lcText2Json(){
        String fName="[constructor1]";
        logger.info(fName + "");
    }
    public void clear(){
        String fName="[clear]";
        try{
            jsonObject =new JSONObject();
            jsonArray=new JSONArray();
            textLines=new ArrayList<>();
            type=0;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public List<String>textLines=new ArrayList<>();
    public boolean isWebFile2Json(String address){
        String fName="[isWebFile2Json]";
        try{
            logger.info(fName);
            return  isWebFile2Json(address,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isFile2Json(String filePath){
        String fName="[isFile2Json]";
        try{
            logger.info(fName);
            return  isFile2Json(filePath,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isResource2Json(String filePath){
        String fName="[isResource2Json]";
        try{
            logger.info(fName);
            return  isResource2Json(filePath,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStream2Json(InputStream inputStream){
        String fName="[isInputStream2Json]";
        try{
            logger.info(fName);
            return isInputStream2Json(inputStream,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStreamReader2Json(InputStreamReader inputStreamReader){
        String fName="[isInputStreamReader2Json]";
        try{
            logger.info(fName);
            return isInputStreamReader2Json(inputStreamReader,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isBufferedReader2Json(BufferedReader reader){
        String fName="[isBufferedReader2Json]";
        try{
            logger.info(fName);
            return isBufferedReader2Json(reader,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isString2Json(String str){
        String fName="[isString2Json]";
        try{
            logger.info(fName);
            return isString2Json(str,0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public lcText2Json getWebFile2Json(String address){
        String fName="[getWebFile2Json]";
        try{
            logger.info(fName);
            return  isWebFile2Json(address)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getFile2Json(String filePath){
        String fName="[getFile2Json]";
        try{
            logger.info(fName);
            return isFile2Json(filePath)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getResource2Json(String filePath) {
        String fName = "[getResource2Json]";
        try {
            logger.info(fName);
            return isResource2Json(filePath) ? this : null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcText2Json getInputStream2Json(InputStream inputStream){
        String fName="[getInputStream2Json]";
        try{
            logger.info(fName);
            return isInputStream2Json(inputStream)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getInputStreamReader2Json(InputStreamReader inputStreamReader){
        String fName="[getInputStreamReader2Json]";
        try{
            logger.info(fName);
            return isInputStreamReader2Json(inputStreamReader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getBufferedReader2Json(BufferedReader reader){
        String fName="[getBufferedReader2Json]";
        try{
            logger.info(fName);
            return isBufferedReader2Json(reader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getString2Json(String str){
        String fName="[getString2Json]";
        try{
            logger.info(fName);
            return isString2Json(str)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public boolean isWebFile2JsonObject(String address){
        String fName="[isWebFile2JsonObject]";
        try{
            logger.info(fName);
            return  isWebFile2Json(address,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isFile2JsonObject(String filePath){
        String fName="[isFile2JsonObject]";
        try{
            logger.info(fName);
            return  isFile2Json(filePath,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isResource2JsonObject(String filePath){
        String fName="[isResource2JsonObject]";
        try{
            logger.info(fName);
            return  isResource2Json(filePath,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStream2JsonObject(InputStream inputStream){
        String fName="[isInputStream2JsonObject]";
        try{
            logger.info(fName);
            return isInputStream2Json(inputStream,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStreamReader2JsonObject(InputStreamReader inputStreamReader){
        String fName="[isInputStreamReader2JsonObject]";
        try{
            logger.info(fName);
            return isInputStreamReader2Json(inputStreamReader,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isBufferedReader2JsonObject(BufferedReader reader){
        String fName="[isBufferedReader2JsonObject]";
        try{
            logger.info(fName);
            return isBufferedReader2Json(reader,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isString2JsonObject(String str){
        String fName="[isString2JsonObject]";
        try{
            logger.info(fName);
            return isString2Json(str,1);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public lcText2Json getWebFile2JsonObject(String address){
        String fName="[getWebFile2JsonObject]";
        try{
            logger.info(fName);
            return  isWebFile2JsonObject(address)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getFile2JsonObject(String filePath){
        String fName="[getFile2JsonObject]";
        try{
            logger.info(fName);
            return isFile2JsonObject(filePath)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getResource2JsonObject(String filePath) {
        String fName = "[getResource2JsonObject]";
        try {
            logger.info(fName);
            return isResource2JsonObject(filePath) ? this : null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcText2Json getInputStream2JsonObject(InputStream inputStream){
        String fName="[getInputStream2JsonObject]";
        try{
            logger.info(fName);
            return isInputStream2JsonObject(inputStream)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getInputStreamReader2JsonObject(InputStreamReader inputStreamReader){
        String fName="[getInputStreamReader2JsonObject]";
        try{
            logger.info(fName);
            return isInputStreamReader2JsonObject(inputStreamReader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getBufferedReader2JsonObject(BufferedReader reader){
        String fName="[getBufferedReader2JsonObject]";
        try{
            logger.info(fName);
            return isBufferedReader2JsonObject(reader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getString2JsonObject(String str){
        String fName="[getString2JsonObject]";
        try{
            logger.info(fName);
            return isString2JsonObject(str)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public boolean isWebFile2JsonArray(String address){
        String fName="[isWebFile2JsonArray]";
        try{
            logger.info(fName);
            return  isWebFile2Json(address,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isFile2JsonArray(String filePath){
        String fName="[isFile2JsonArray]";
        try{
            logger.info(fName);
            return  isFile2Json(filePath,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isResource2JsonArray(String filePath){
        String fName="[isResource2JsonArray]";
        try{
            logger.info(fName);
            return  isResource2Json(filePath,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStream2JsonArray(InputStream inputStream){
        String fName="[isInputStream2JsonArray]";
        try{
            logger.info(fName);
            return isInputStream2Json(inputStream,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStreamReader2JsonArray(InputStreamReader inputStreamReader){
        String fName="[isInputStreamReader2JsonArray]";
        try{
            logger.info(fName);
            return isInputStreamReader2Json(inputStreamReader,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isBufferedReader2JsonArray(BufferedReader reader){
        String fName="[isBufferedReader2JsonArray]";
        try{
            logger.info(fName);
            return isBufferedReader2Json(reader,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isString2JsonArray(String str){
        String fName="[isString2JsonArray]";
        try{
            logger.info(fName);
            return isString2Json(str,2);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public lcText2Json getWebFile2JsonArray(String address){
        String fName="[getWebFile2JsonArray]";
        try{
            logger.info(fName);
            return  isWebFile2JsonArray(address)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getFile2JsonArray(String filePath){
        String fName="[getFile2JsonArray]";
        try{
            logger.info(fName);
            return isFile2JsonArray(filePath)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getResource2JsonArray(String filePath) {
        String fName = "[getResource2JsonArray]";
        try {
            logger.info(fName);
            return isResource2JsonArray(filePath) ? this : null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcText2Json getInputStream2JsonArray(InputStream inputStream){
        String fName="[getInputStream2JsonArray]";
        try{
            logger.info(fName);
            return isInputStream2JsonArray(inputStream)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getInputStreamReader2JsonArray(InputStreamReader inputStreamReader){
        String fName="[getInputStreamReader2JsonArray]";
        try{
            logger.info(fName);
            return isInputStreamReader2JsonArray(inputStreamReader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getBufferedReader2JsonArray(BufferedReader reader){
        String fName="[getBufferedReader2JsonArray]";
        try{
            logger.info(fName);
            return isBufferedReader2JsonArray(reader)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getString2JsonArray(String str){
        String fName="[getString2JsonArray]";
        try{
            logger.info(fName);
            return isString2JsonArray(str)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public boolean isWebFile2Json(String address, int type){
        String fName="[isWebFile2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doWebFile2InputStream(address)))))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doWebFile2InputStream(address)))))>0;
                default:
                    return doString2Json(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doWebFile2InputStream(address)))))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isFile2Json(String filePath, int type){
        String fName="[isFile2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doFile2InputStream(filePath)))))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doFile2InputStream(filePath)))))>0;
                default:
                    return doString2Json(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doFile2InputStream(filePath)))))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isResource2Json(String filePath, int type){
        String fName="[isResource2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doResource2InputStream(filePath)))))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doResource2InputStream(filePath)))))>0;
                default:
                    return doString2Json(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(doResource2InputStream(filePath)))))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStream2Json(InputStream inputStream, int type){
        String fName="[isInputStream2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(inputStream))))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(inputStream))))>0;
                default:
                    return doString2Json(doBufferedReader2String(doInputStreamReader2BufferedReader(doInputStream2InputStreamReader(inputStream))))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isInputStreamReader2Json(InputStreamReader inputStreamReader, int type){
        String fName="[isInputStreamReader2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(doInputStreamReader2BufferedReader(inputStreamReader)))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(doInputStreamReader2BufferedReader(inputStreamReader)))>0;
                default:
                    return doString2Json(doBufferedReader2String(doInputStreamReader2BufferedReader(inputStreamReader)))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isBufferedReader2Json(BufferedReader reader, int type){
        String fName="[isBufferedReader2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(doBufferedReader2String(reader))>0;
                case 2:
                    return doString2JsonArray(doBufferedReader2String(reader))>0;
                default:
                    return doString2Json(doBufferedReader2String(reader))>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isString2Json(String str, int type){
        String fName="[isString2Json]";
        try{
            logger.info(fName+"type="+type);
            switch (type){
                case 1:
                    return doString2JsonObject(str)>0;
                case 2:
                    return doString2JsonArray(str)>0;
                default:
                    return doString2Json(str)>0;
            }
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public lcText2Json getWebFile2Json(String address, int type){
        String fName="[getWebFile2Json]";
        try{
            logger.info(fName+"type="+type);
            return  isWebFile2Json(address,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getFile2Json(String filePath, int type){
        String fName="[getFile2Json]";
        try{
            logger.info(fName+"type="+type);
            return isFile2Json(filePath,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getResource2Json(String filePath, int type) {
        String fName = "[getResource2Json]";
        try {
            logger.info(fName+"type="+type);
            return isResource2Json(filePath,type) ? this : null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcText2Json getInputStream2Json(InputStream inputStream, int type){
        String fName="[getInputStream2Json]";
        try{
            logger.info(fName+"type="+type);
            return isInputStream2Json(inputStream,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getInputStreamReader2Json(InputStreamReader inputStreamReader, int type){
        String fName="[getInputStreamReader2Json]";
        try{
            logger.info(fName+"type="+type);
            return isInputStreamReader2Json(inputStreamReader,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getBufferedReader2Json(BufferedReader reader, int type){
        String fName="[getBufferedReader2Json]";
        try{
            logger.info(fName+"type="+type);
            return isBufferedReader2Json(reader,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcText2Json getString2Json(String str, int type){
        String fName="[getString2Json]";
        try{
            logger.info(fName+"type="+type);
            return isString2Json(str,type)?this:null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private InputStream doWebFile2InputStream(String address){
        String fName="[doWebFile2InputStream]";
        try{
            logger.info(fName+"type="+type);
            logger.info(fName+" address="+address);
            URL url = new URL(address);
            HttpURLConnection httpcon = (HttpURLConnection)url.openConnection() ;
            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream inputStream= httpcon.getInputStream();
            if(inputStream==null){
                throw  new Exception("InputStream is null!");
            }
            return inputStream;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private InputStream doFile2InputStream(String filePath){
        String fName="[doFile2InputStream]";
        try{
            logger.info(fName);
            logger.info(fName+" filePath="+filePath);
            InputStream inputStream=new FileInputStream(filePath);
            if(inputStream==null){
                throw  new Exception("InputStream is null!");
            }
            return inputStream;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private  InputStream doResource2InputStream(String filePath){
        String fName="[doResource2InputStream]";
        try{
            logger.info(fName);
            logger.info(fName+" filePath="+filePath);
            InputStream inputStream= ClassLoader.class.getResourceAsStream("filePath");
            if(inputStream==null){
                throw  new Exception("InputStream is null!");
            }
            return inputStream;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private InputStreamReader doInputStream2InputStreamReader(InputStream inputStream){
        String fName="[doInputStream2InputStreamReader]";
        try{
            logger.info(fName);
            if(inputStream==null){
                throw  new Exception("InputStream is null!");
            }
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            if(inputStreamReader==null){
                throw  new Exception("InputStreamReader is null!");
            }
            return inputStreamReader;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private BufferedReader doInputStreamReader2BufferedReader(InputStreamReader inputStreamReader){
        String fName="[doInputStreamReader2BufferedReader]";
        try{
            logger.info(fName);
            if(inputStreamReader==null){
                throw  new Exception("InputStreamReader is null!");
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            if(reader==null){
                throw  new Exception("BufferedReader is null!");
            }
            return reader;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private String doBufferedReader2String(BufferedReader reader){
        String fName="[doBufferedReader2String]";
        try{
            logger.info(fName);
            if(reader==null){
                throw  new Exception("BufferedReader is null!");
            }
            String str;
            textLines=new ArrayList<>();
            StringBuilder buff= new StringBuilder();
            int i=0;
            while ((str = reader.readLine()) != null) {
                logger.info(fName+".line="+i);i++;
                if(2147483600<=i){i=0;}
                textLines.add(str);
                buff.append(str);
            }
            String strBuff=buff.toString();
            logger.info(fName+".strBuff="+ strBuff);
            if(strBuff==null){
                throw  new Exception("String is null!");
            }
            return strBuff;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private int doString2Json(String str){
        String fName="[doString2Json]";
        try{
            logger.info(fName);
            if(str==null){
                logger.warn(fName+".str is null");return  -1;
            }
            if(str.isBlank()){
                logger.warn(fName+".str is blank");return  -2;
            }
            logger.info(fName+".str="+ str);
            if(convert2JsonObject(str)){
                logger.info(fName+".str is jsonobject");
                type=1;
                return  1;
            }
            if(convert2JsonArray(str)){
                logger.info(fName+".str is jsonarray");
                type=2;
                return  2;
            }
            logger.warn(fName+".str is invalid");
            return -3;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  -4;
        }
    }
    private int doString2JsonObject(String str){
        String fName="[doString2JsonObject]";
        try{
            logger.info(fName);
            if(str==null){
                logger.warn(fName+".str is null");return  -1;
            }
            if(str.isBlank()){
                logger.warn(fName+".str is blank");return  -2;
            }
            logger.info(fName+".str="+ str);
            if(convert2JsonObject(str)){
                logger.info(fName+".str is jsonobject");
                type=1;
                return  1;
            }
            logger.warn(fName+".str is invalid");
            return -3;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  -4;
        }
    }
    private int doString2JsonArray(String str){
        String fName="[doString2JsonArray]";
        try{
            logger.info(fName);
            if(str==null){
                logger.warn(fName+".str is null");return  -1;
            }
            if(str.isBlank()){
                logger.warn(fName+".str is blank");return  -2;
            }
            logger.info(fName+".str="+ str);
            if(convert2JsonArray(str)){
                logger.info(fName+".str is jsonarray");
                type=2;
                return  1;
            }
            logger.warn(fName+".str is invalid");
            return -3;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  -4;
        }
    }
    private boolean convert2JsonObject(String str){
        String fName="[convert2JsonObject]";
        try{
            jsonObject =new JSONObject(str);
            logger.info(fName+".jsonObject="+ jsonObject);
            return  true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    private boolean convert2JsonArray(String str){
        String fName="[convert2JsonArray]";
        try{
            jsonArray =new JSONArray(str);
            logger.info(fName+".jsonArray="+ jsonArray);
            return  true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean save2File(String filePath){
        String fName="[save2File]";
        try{
            logger.info(fName);
            FileWriter fileWriter;
            //:/Users/Shared/crunchify.txt
            logger.info(fName+" filePath="+filePath);
            fileWriter = new FileWriter(filePath);
            if(jsonArray!=null){
                fileWriter.write(jsonArray.toString());
            }
            else if(jsonObject!=null){
                fileWriter.write(jsonObject.toString());
            }
            else{
                logger.info(fName+" n/a");
                return  false;
            }
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    private int getType(){
        String fName="[getType]";
        try{
            logger.info(fName);
            return type;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
}
