package models.lc;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class lcTempZipFile {
    Logger logger = Logger.getLogger("lcTempZipFile");
    public ByteArrayOutputStream byteArrayOutputStream;
    public ZipOutputStream zipOutputStream;
    public lcTempZipFile(){
        String fName="constructor";
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public int size(){
        String fName="size";
        try {
            int size=byteArrayOutputStream.size();
            return size;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  -1;
        }
    }
    public boolean reset(){
        String fName="reset";
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean close(){
        String fName="close";
        try {
            zipOutputStream.close();logger.info(fName+"closed");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setLevel(int level){
        String fName="setLevel";
        try {
            logger.info(fName+"level="+level);
            zipOutputStream.setLevel(level);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setMethod(int method){
        String fName="setMethod";
        try {
            logger.info(fName+"method="+method);
            zipOutputStream.setMethod(method);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setComment(String comment){
        String fName="setComment";
        try {
            logger.info(fName+"comment="+comment);
            zipOutputStream.setComment(comment);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    private boolean exceptionCloseEntry(){
        String fName="exceptionCloseEntry";
        try {
            //zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean addEntity(String file,byte[] bytes){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"bytes.length="+bytes.length);
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,String string){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"string.length="+string.length());
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(string.getBytes());
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,InputStream inputStream){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"inputStream.available()="+inputStream.available());
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            int number = 0;
            byte[] buffer = new byte[512];
            while ((number = inputStream.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, number);
            }
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,InputStream inputStream, int byteValue){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"byteValue="+byteValue);
            logger.info(fName+"inputStream.available()="+inputStream.available());
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            int number = 0;
            if(byteValue<=0)byteValue=512;
            byte[] buffer = new byte[512];
            while ((number = inputStream.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, number);
            }
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,JSONObject jsonObject){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"jsonObject.length()="+jsonObject.length());
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(jsonObject.toString().getBytes());
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,JSONArray jsonArray){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"jsonArray.length()="+jsonArray.length());
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(jsonArray.toString().getBytes());
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean addEntity(String file,int integer){
        String fName="addEntity";
        try {
            logger.info(fName+"file="+file);
            logger.info(fName+"integer="+integer);
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(integer);
            zipOutputStream.closeEntry();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public InputStream getInputStream(){
        String fName="getInputStream";
        try {
            zipOutputStream.close();logger.info(fName+"zipOutputStream.closed");
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            logger.info(fName+"inputStream.available="+inputStream.available());
            return inputStream;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /*boolean newEntity(String file){
        String fName="newEntity";
        try {
            logger.info(fName+"file="+file);
            ZipEntry zipEntry = new ZipEntry(file);
            zipOutputStream.putNextEntry(zipEntry);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean writeEntity(byte[] bytes,int off, int len){
        String fName="writeEntity";
        try {
            logger.info(fName+"bytes.length="+bytes.length);
            logger.info(fName+"bytes.off="+off);
            logger.info(fName+"bytes.len="+len);
            zipOutputStream.write(bytes,off,len);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }
    public boolean closeEntity(){
        String fName="closeEntity";
        try {
            zipOutputStream.closeEntry();logger.info(fName+"closedEntity");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            exceptionCloseEntry();
            return  false;
        }
    }*/
}
