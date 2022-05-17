package models.lc;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcText2List {
    Logger logger = Logger.getLogger(getClass());
    public lcText2List(){
        String fName="[constructor1]";
        logger.info(fName + "");
    }
    public boolean getWebFile2List(String address){
        String fName="[getWebFile2List]";
        try{
            logger.info(fName);
            logger.info(fName+" address="+address);
            URL url = new URL(address);
            HttpURLConnection httpcon = (HttpURLConnection)url.openConnection() ;
            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream inputStream= httpcon.getInputStream();
            if(inputStream==null){
                logger.warn(fName+".inputstream is null");return  false;
            }
           return getInputStream2List(inputStream);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean getFile2List(String filePath){
        String fName="[getFile2List]";
        try{
            logger.info(fName);
            logger.info(fName+" filePath="+filePath);
            InputStream inputStream=new FileInputStream(filePath);
            return getInputStream2List(inputStream);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean getResource2List(String filePath){
        String fName="[getResource2List]";
        try{
            logger.info(fName);
            logger.info(fName+" filePath="+filePath);
            InputStream inputStream= ClassLoader.class.getResourceAsStream("filePath");
            if(inputStream==null){
                logger.warn(fName+".inputstream is null");return  false;
            }
            return getInputStream2List(inputStream);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean getInputStream2List(InputStream inputStream){
        String fName="[getInputStream2List]";
        try{
            logger.info(fName);
            if(inputStream==null){
                logger.warn(fName+".inputstream is null");return  false;
            }
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            return getInputStreamReader2List(inputStreamReader);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean getInputStreamReader2List(InputStreamReader inputStreamReader){
        String fName="[getInputStreamReader2List]";
        try{
            logger.info(fName);
            if(inputStreamReader==null){
                logger.warn(fName+".inputStreamReader is null");return  false;
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            return getBufferedReader2List(reader);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public List<String>textLines=new ArrayList<>();
    public boolean getBufferedReader2List(BufferedReader reader){
        String fName="[getBufferedReader2List]";
        try{
            logger.info(fName);
            if(reader==null){
                logger.warn(fName+".bufferedReader is null");return  false;
            }
            String str;
            textLines=new ArrayList<>();
            int i=0;
            while ((str = reader.readLine()) != null) {
                logger.info(fName+".line="+i);i++;
                if(2147483600<=i){i=0;}
                textLines.add(str);
            }
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
            if(textLines!=null){
                fileWriter.write(textLines.toString());
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
    public boolean isListEmpty(){
        String fName="[isListEmpty]";
        try{
            if(textLines==null){
                logger.info(fName+"is null>true");
                return true;
            }
            return textLines.isEmpty();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  true;
        }
    }
}
