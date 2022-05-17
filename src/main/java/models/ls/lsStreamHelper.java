package models.ls;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public interface lsStreamHelper {
    static InputStream llGetInputStream4WebFile(String source){
        Logger logger = Logger.getLogger(lsStreamHelper.class);
        String fName="[llGetInputStream4WebFile]";
        try {
            logger.info(fName+".source="+ source);
            URL url = new URL(source);
            HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            int code=httpcon.getResponseCode();
            logger.info(fName+".code="+ code);
            if(code>299){
                logger.warn(fName+".the return code is:"+code);
                return  null;
            }
            return httpcon.getInputStream();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InputStream llGetInputStream4File(String source){
        Logger logger = Logger.getLogger(lsStreamHelper.class);
        String fName="[llGetInputStream4File]";
        try {
            logger.info(fName+".source="+ source);
            File file;
            file=new File(source);
            if(!file.exists()){
                logger.warn(fName+".file does not exists");
                return  null;
            }
            return  new FileInputStream(file);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InputStream llGetInputStream4App(String source){
        Logger logger = Logger.getLogger(lsStreamHelper.class);
        String fName="[llGetInputStream4App]";
        try {
            logger.info(fName+".source="+ source);
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            if(classloader==null){
                logger.warn(fName+".classloader does not exists");
                return  null;
            }
            return classloader.getResourceAsStream(source);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InputStream llGetInputStream4Source(String source){
        Logger logger = Logger.getLogger(lsStreamHelper.class);
        String fName="[llGetInputStream4Source]";
        try {
            logger.info(fName+".source="+ source);
            try {
                File file;
                file=new File(source);
                return  new FileInputStream(file);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                return classloader.getResourceAsStream(source);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                URL url = new URL(source);
                HttpURLConnection httpcon = (HttpURLConnection)url.openConnection() ;
                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                return httpcon.getInputStream();
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));

            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

}
