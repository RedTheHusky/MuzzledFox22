package nsfw.verify;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.BREATHPLAYLEVELS;
import restraints.models.GAGLEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityLewdverify {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLewdverify]";

    final String fieldChastity=iLewdverify.fieldChastity,fieldDiaper=iLewdverify.fieldDiaper,fieldCollar=iLewdverify.fieldCollar,fieldGeneric=iLewdverify.fieldGeneric;
    public entityLewdverify(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLewdverify(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityLewdverify set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(!jsonObject.isEmpty()){
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    try {
                        switch (key){
                            case fieldGeneric:
                                generic.set(jsonObject.getJSONObject(key));
                                break;
                            case fieldChastity:
                                chastity.set(jsonObject.getJSONObject(key));
                                break;
                            case fieldCollar:
                                collar.set(jsonObject.getJSONObject(key));
                                break;
                            case fieldDiaper:
                                diaper.set(jsonObject.getJSONObject(key));
                                break;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
            }
            jsonObject=null;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            logger.info("jsonObject="+jsonObject.toString());
            jsonObject.put(fieldGeneric,generic.getJSON());
            jsonObject.put(fieldCollar,collar.getJSON());
            jsonObject.put(fieldChastity,chastity.getJSON());
            jsonObject.put(fieldDiaper,diaper.getJSON());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }

    public OPTION generic=new OPTION(),collar=new OPTION(),chastity=new OPTION(),diaper=new OPTION();
    public class OPTION{
        public OPTION(){
            String fName="[constructor]";
            logger.info(fName);
        }
        final String keyCode=iLewdverify.keyCode,keyUrl=iLewdverify.keyUrl, keyImg =iLewdverify.keyImg, keyTimeStamp=iLewdverify.keyTimeStamp, keyName=iLewdverify.keyName,keyExt=iLewdverify.keyExt;
        private String code="",url="",img ="",name="",ext="";
        private long timeStamp=0;
        public OPTION(JSONObject jsonObject){
            String fName="[constructor]";
            logger.info(fName);
            set(jsonObject);
        }
        public OPTION set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(fName+"jsonObject is null");
                    return null;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                if(!jsonObject.isEmpty()){
                    Iterator<String> keys=jsonObject.keys();
                    while (keys.hasNext()){
                        String key=keys.next();
                        try {
                            switch (key){
                                case keyCode:
                                    if(!jsonObject.isNull(key))code=jsonObject.optString(key);
                                    break;
                                case keyUrl:
                                    if(!jsonObject.isNull(key))url=jsonObject.optString(key);
                                    break;
                                case keyImg:
                                    if(!jsonObject.isNull(key))img=jsonObject.optString(key);
                                    break;
                                case keyName:
                                    if(!jsonObject.isNull(key))name=jsonObject.optString(key);
                                    break;
                                case keyExt:
                                    if(!jsonObject.isNull(key))ext=jsonObject.optString(key);
                                    break;
                                case keyTimeStamp:
                                    if(!jsonObject.isNull(key))timeStamp=jsonObject.optLong(key);
                                    break;
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                        }
                    }
                }
                jsonObject=null;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                JSONObject jsonObject=new JSONObject();
                logger.info("jsonObject="+jsonObject.toString());
                jsonObject.put(keyCode,code);
                jsonObject.put(keyName,name);
                jsonObject.put(keyExt,ext);
                jsonObject.put(keyUrl,keyUrl);
                jsonObject.put(keyImg,img);
                jsonObject.put(keyTimeStamp,timeStamp);
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public String getCode(){
            String fName="[getCode]";
            try {
                String value=code;
                logger.info(fName+"value="+ value);
                if(value==null)value="";
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                String value=name;
                logger.info(fName+"value="+ value);
                if(value==null)value="";
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getExt(){
            String fName="[getExt]";
            try {
                String value=ext;
                logger.info(fName+"value="+ value);
                if(value==null)value="";
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUrl(){
            String fName="[getUrl]";
            try {
                String value=url;
                logger.info(fName+"value="+ value);
                if(value==null)value="";
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImg(){
            String fName="[getImg]";
            try {
                String value=img;
                logger.info(fName+"value="+ value);
                if(value==null)value="";
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getTimeStamp(){
            String fName="[getTimeStamp]";
            try {
                long value=timeStamp;
                logger.info(fName+"value="+ value);
                return  value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public OPTION setCode(String input){
            String fName="[setCode]";
            try {
                logger.info(fName+"value="+ input);
                if(input==null)throw  new Exception("Input can't be null");
                code=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public OPTION setImg(String input){
            String fName="[setImg]";
            try {
                logger.info(fName+"value="+ input);
                if(input==null)throw  new Exception("Input can't be null");
                img=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public OPTION setUrl(String input){
            String fName="[setUrl]";
            try {
                logger.info(fName+"value="+ input);
                if(input==null)throw  new Exception("Input can't be null");
                url=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public OPTION setName(String input){
            String fName="[setName]";
            try {
                logger.info(fName+"value="+ input);
                if(input==null)throw  new Exception("Input can't be null");
                name=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public OPTION setExt(String input){
            String fName="[setExt]";
            try {
                logger.info(fName+"value="+ input);
                if(input==null)throw  new Exception("Input can't be null");
                ext=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public OPTION setTimeStamp(long input){
            String fName="[setTimeStamp]";
            try {
                logger.info(fName+"value="+ input);
                timeStamp=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private boolean hasStringValue(String str){
            String fName="[hasStringValue]";
            try {
                logger.info(fName+"value="+ str);
                if(str==null){
                    logger.info(fName+"isNUll");
                    return false;
                }
                if(str.isBlank()){
                    logger.info(fName+"isBlank");
                    return false;
                }
                logger.info(fName+"default");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasCode(){
            return hasStringValue(code);
        }
        public boolean hasName(){
            return hasStringValue(name);
        }
        public boolean hasExt(){
            return hasStringValue(ext);
        }
        public boolean hasUrl(){
            return hasStringValue(url);
        }
        public boolean hasImg(){
            return hasStringValue(img);
        }
        public boolean hasTimeStamp(){
            String fName="[hasTimeStamp]";
            try {
                logger.info(fName+"value="+ timeStamp);
                if(timeStamp<=0){
                    logger.info(fName+"bellow or equal to 0");
                    return false;
                }
                logger.info(fName+"default");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
    }
}
