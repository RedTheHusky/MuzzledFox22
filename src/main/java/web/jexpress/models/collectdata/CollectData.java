package web.jexpress.models.collectdata;

import express.http.Cookie;
import express.http.request.Authorization;
import express.http.request.Request;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.ls.lsStringUsefullFunctions;
import org.apache.log4j.Logger;
import web.jexpress.models.expStats;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CollectData {
    Logger logger = Logger.getLogger(getClass());  String cName="[CollectData]";

    public CollectData(){
        String fName="[build]";
        logger.info(fName+"build");
    }
    public InetAddress gAddress=null;
    public String gContentType="",gHost="",gIp="",gMethod="",gUserAgent="",gContext="",gPath="",gProtocol="";
    public InputStream gBody; public  String gBodyString="";
    public List<Authorization> gAuthorizations=null;
    public URI gURI=null;
    public HashMap<String, Cookie> gCookies=null;
    public HashMap<String, String> gFormQuerys=null,gParams=null, gQuerys =null;
    public long gContentLength=0L;
    public lcText2Json gText2Json;
    public JSONObject inputJSONObject=new JSONObject();
    public JSONArray inputJSONArray=new JSONArray();
    public void set(Request req) {
        String fName="[set]";
        try {
            logger.info(fName+"start");
            try {
                gAddress=req.getAddress();
                logger.info(fName+"HostAddress="+gAddress.getHostAddress());
                logger.info(fName+"HostName="+gAddress.getHostName());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gContentType=req.getContentType();
                logger.info(fName+"gContentType="+gContentType);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gAuthorizations=req.getAuthorization();
                logger.info(fName+"gAuthorizations="+ gAuthorizations.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gHost=req.getHost();
                logger.info(fName+"gHost="+gHost);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gIp=req.getIp();
                logger.info(fName+"gIp="+gIp);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gMethod=req.getMethod();
                logger.info(fName+"gMethod="+gMethod);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserAgent=req.getUserAgent();
                logger.info(fName+"gUserAgent="+gUserAgent);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gContext=req.getContext();
                logger.info(fName+"gContext="+gContext);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gURI=req.getURI();
                logger.info(fName+"gURI="+gURI.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gCookies=req.getCookies();
                logger.info(fName+"gCookies="+gCookies.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gFormQuerys=req.getFormQuerys();
                logger.info(fName+"gFormQuerys="+gFormQuerys.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gParams=req.getParams();
                logger.info(fName+"gParams="+gParams.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gPath=req.getPath();
                logger.info(fName+"gPath="+gPath);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gProtocol=req.getProtocol();
                logger.info(fName+"gProtocol="+gProtocol);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gQuerys =req.getQuerys();
                logger.info(fName+"gQuerys="+ gQuerys.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gContentLength=req.getContentLength();
                logger.info(fName+"gContentLength="+gContentLength);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName+"isFresh="+req.isFresh());
                logger.info(fName+"isSecure="+req.isSecure());
                logger.info(fName+"isStale="+req.isStale());
                logger.info(fName+"isXHR="+req.isXHR());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gBody=req.getBody();
                if(gBody!=null){
                    logger.info(fName+"body is not null");
                    logger.info(fName+"body.available="+gBody.available());
                    try {
                        gBodyString=lsStringUsefullFunctions.InputStream2String(gBody);
                        logger.info(fName+"body.string="+ gBodyString);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    if(gContentType.toLowerCase().contains(expStats.InputContentType_MIME_JSON)){
                        gText2Json=new lcText2Json();
                        if(gText2Json.isString2Json( gBodyString)){
                            logger.info(fName+"get json from body string");
                            if(gText2Json.jsonArray!=null){
                                inputJSONArray=gText2Json.jsonArray;
                                logger.info(fName+"inputJSONArray="+inputJSONArray.toString());
                            }
                            if(gText2Json.jsonObject!=null){
                                inputJSONObject=gText2Json.jsonObject;
                                logger.info(fName+"inputJSONObject="+inputJSONObject.toString());
                            }
                        }else{
                            logger.warn(fName+"failed to get json from body string");
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName + ".header.Content-type=" + req.getHeader("Content-type"));

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName + ".header.User-Agent=" + req.getHeader("User-Agent"));

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName + ".header.Access-Control-Allow-Origin=" + req.getHeader("Access-Control-Allow-Origin"));

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public class Value{
        public Value(){

        }
        String key="";
        Object value=null;
        int mode=0;
        public Value(String key, int mode, String value){
            this.mode=mode;
            this.key=key;
            this.value=value;
        }
        public Value(String key, int mode, Object value){
            this.mode=mode;
            this.key=key;
            this.value=value;
        }
        public String getKey(){
            String fName="[getKey]";
            logger.info(fName+"value="+key);
            return key;
        }
        public int getMode(){
            String fName="[getMode]";
            logger.info(fName+"value="+mode);
            return mode;
        }
        public Object getObject(){
            String fName="[getObject]";
            logger.info(fName+"value="+value);
            return value;
        }
        public boolean hasValue(){
            String fName="[hasValue]";
            boolean result=value!=null;
            logger.info(fName+"value="+result);
            return result;
        }
        public String getString(){
            String fName="[getString]";
            String valueTmp=(String)getObject();
            logger.info(fName+"value="+valueTmp);
            return valueTmp;
        }
        public Boolean getBoolean(){
            String fName="[getBoolean]";
            Boolean valueTmp=(Boolean)getObject();
            logger.info(fName+"value="+valueTmp);
            return valueTmp;
        }
        public Integer getInt(){
            String fName="[getInt]";
            Integer valueTmp=(Integer) getObject();
            logger.info(fName+"value="+valueTmp);
            return valueTmp;
        }
        public Long getLong(){
            String fName="[getLong]";
            Long valueTmp=(Long) getObject();
            logger.info(fName+"value="+valueTmp);
            return valueTmp;
        }
    }
    public Value getValue(String key) {
        String fName="[getValue]";
        try {
            logger.info(fName+"key="+key);
            Value value=null;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=new Value(key,1,gParams.get(key));
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=new Value(key,2,gFormQuerys.get(key));
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=new Value(key,3,gQuerys.get(key));
            }
            else if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                if(!inputJSONObject.isNull(key)) value=new Value(key,4,inputJSONObject.getString(key));
                else value=new Value(key,4,null);
            }else{
                value=new Value(key,0,null);
            }
            logger.info(fName+"value.obj="+value.getObject());
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    public boolean hasParameter(String key) {
        String fName="[hasParameter]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=true;
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=true;
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=true;
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=true;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterNull(String key) {
        String fName="[isParameterNull]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=gParams.get(key)==null;
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=gFormQuerys.get(key)==null;
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=gQuerys.get(key)==null;
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=inputJSONObject.isNull(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterBlank(String key) {
        String fName="[isParameterBlank]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=gParams.get(key).isBlank();
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=gFormQuerys.get(key).isBlank();
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=gQuerys.get(key).isBlank();
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=inputJSONObject.getString(key).isBlank();
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getString(String key) {
        String fName="[getString]";
        try {
            logger.info(fName+"key="+key);
            String value="";
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=gParams.get(key);
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=gFormQuerys.get(key);
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=gQuerys.get(key);
            }
            else if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)&&!inputJSONObject.isNull(key)){
                value=inputJSONObject.getString(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean(String key) {
        String fName="[getBoolean]";
        try {
            logger.info(fName+"key="+key);
            int mode=0;
            String valueStr="";boolean valueBooleanProvided=false,valueBoolean=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueBooleanProvided=true;
                    valueBoolean=inputJSONObject.getBoolean(key);
                }
            }
            logger.info(fName+"mode="+mode);
            boolean result=false;
            switch (mode){
                case 1:case 2:case 3:
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=false");result=false;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=false");result=false;}
                    else if(valueStr.equalsIgnoreCase("true")||valueStr.equalsIgnoreCase("1")){ logger.info(fName+"valueStr is true => result=true");result=true;}
                    else if(valueStr.equalsIgnoreCase("false")||valueStr.equalsIgnoreCase("0")){  logger.info(fName+"valueStr is false =>result=false");result=false;}
                    break;
                case 4:
                    if(!valueBooleanProvided){logger.info(fName+"valueBooleanProvided is false => result=false");result=false;}
                    else result=valueBoolean;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong(String key) {
        String fName="[getLong]";
        try {
            logger.info(fName+"key="+key);
            int mode=0;
            String valueStr="";long valueL=0;boolean valueLProvided=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueLProvided=true;
                    valueL=inputJSONObject.getLong(key);
                }
            }
            logger.info(fName+"mode="+mode);
            Long result=0L;
            switch (mode){
                case 1:case 2:case 3:
                    logger.info(fName+"valueStr="+valueStr);
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=0");result=0L;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=0");result=0L;}
                    else  result= Long.parseLong(valueStr);
                    break;
                case 4:
                    if(!valueLProvided){logger.info(fName+"valueLProvided is false => result=0");result=0L;}
                    else result=valueL;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt(String key) {
        String fName="[getLong]";
        try {
            logger.info(fName+"key="+key);
            int mode=0;
            String valueStr="";int valueI=0;boolean valueIProvided=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueIProvided=true;
                    valueI=inputJSONObject.getInt(key);
                }
            }
            logger.info(fName+"mode="+mode);
            Integer result=0;
            switch (mode){
                case 1:case 2:case 3:
                    logger.info(fName+"valueStr="+valueStr);
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=0");result=0;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=0");result=0;}
                    else  result= Integer.parseInt(valueStr);
                    break;
                case 4:
                    if(!valueIProvided){logger.info(fName+"valueIProvided is false => result=0");result=0;}
                    else result=valueI;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public String getString(String key,String def) {
        String fName="[getString]";
        try {
            logger.info(fName+"key="+key+", default="+def);
            int mode=0;
            String value="";
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                value=gParams.get(key);
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                value=gFormQuerys.get(key);
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                value=gQuerys.get(key);
            }
            else if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key))value=inputJSONObject.getString(key);
            }
            if(mode>0&&(value==null||value.isBlank())){
                logger.info(fName+"set to default");
                value=def;
            }
            logger.info(fName+"mode="+mode);
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean(String key,boolean def) {
        String fName="[getBoolean]";
        try {
            logger.info(fName+"key="+key+", default="+def);
            int mode=0;
            String valueStr="";boolean valueBooleanProvided=false,valueBoolean=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueBooleanProvided=true;
                    valueBoolean=inputJSONObject.getBoolean(key);
                }
            }
            logger.info(fName+"mode="+mode);
            boolean result=false;
            switch (mode){
                case 1:case 2:case 3:
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=def");result=def;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=def");result=def;}
                    else if(valueStr.equalsIgnoreCase("true")||valueStr.equalsIgnoreCase("1")){ logger.info(fName+"valueStr is true => result=true");result=true;}
                    else if(valueStr.equalsIgnoreCase("false")||valueStr.equalsIgnoreCase("0")){  logger.info(fName+"valueStr is false =>result=false");result=false;}
                    break;
                case 4:
                    if(!valueBooleanProvided){logger.info(fName+"valueBooleanProvided is false => result=def");result=def;}
                    else result=valueBoolean;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong(String key, long def) {
        String fName="[getLong]";
        try {
            logger.info(fName+"key="+key+", default="+def);
            int mode=0;
            String valueStr="";long valueL=0;boolean valueLProvided=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueLProvided=true;
                    valueL=inputJSONObject.getLong(key);
                }
            }
            logger.info(fName+"mode="+mode);
            Long result=0L;
            switch (mode){
                case 1:case 2:case 3:
                    logger.info(fName+"valueStr="+valueStr);
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=def");result=def;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=def");result=def;}
                    else  result= Long.parseLong(valueStr);
                    break;
                case 4:
                    if(!valueLProvided){logger.info(fName+"valueLProvided is false => result=def");result=def;}
                    else result=valueL;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt(String key, int def) {
        String fName="[getLong]";
        try {
            logger.info(fName+"key="+key+", default="+def);
            int mode=0;
            String valueStr="";int valueI=0;boolean valueIProvided=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                mode=1;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    valueStr=gParams.get(key);
                }
            }
            else if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                mode=2;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    valueStr=gFormQuerys.get(key);
                }
            }
            else if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                mode=3;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    valueStr=gQuerys.get(key);
                }
            }else
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                mode=4;
                if(!inputJSONObject.isNull(key)){
                    valueIProvided=true;
                    valueI=inputJSONObject.getInt(key);
                }
            }
            logger.info(fName+"mode="+mode);
            Integer result=0;
            switch (mode){
                case 1:case 2:case 3:
                    logger.info(fName+"valueStr="+valueStr);
                    if(valueStr == null){logger.info(fName+"valueStr is null => result=def");result=def;}
                    else if(valueStr.isBlank()){logger.info(fName+"valueStr is blank => result=def");result=def;}
                    else  result= Integer.parseInt(valueStr);
                    break;
                case 4:
                    if(!valueIProvided){logger.info(fName+"valueIProvided is false => result=def");result=def;}
                    else result=valueI;
                default:
                    logger.info(fName+"default => result=false");
            }
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public boolean hasParameter4Param(String key) {
        String fName="[hasParameter4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=true;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterNull4Param(String key) {
        String fName="[isParameterNull4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=gParams.get(key)==null;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterBlank4Param(String key) {
        String fName="[isParameterBlank4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                value=gParams.get(key).isBlank();
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getString4Param(Request req,String key) {
        String fName="[getString4Param]";
        try {
            logger.info(fName+"key="+key);
            String value="";
            if(req.getParam(key)!=null){
                value=req.getParam(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean4Param(String key) {
        String fName="[getBoolean4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                has=true;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    value=gParams.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return  true;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  true;}
            if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1")){ logger.info(fName+"return value true");return  true;}
            else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("0")){  logger.info(fName+"return value false");return  false;}
            logger.info(fName+"return last:false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong4Param(String key) {
        String fName="[getLong4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                has=true;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    value=gParams.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0L;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0L;}
            long valueL= Long.parseLong(value);
            logger.info(fName+"valueL="+valueL);
            return  valueL;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt4Param(String key) {
        String fName="[getLong4Param4Param]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gParams!=null&&!gParams.isEmpty()&&gParams.containsKey(key)){
                has=true;
                if(gParams.get(key)!=null&&gParams.get(key).isBlank()){
                    value=gParams.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0;}
            int valueI= Integer.parseInt(value);
            logger.info(fName+"valueI="+valueI);
            return  valueI;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public boolean hasParameter4FormQuery(String key) {
        String fName="[hasParameter4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=true;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterNull4FormQuery(String key) {
        String fName="[isParameterNull4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=gFormQuerys.get(key)==null;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterBlank4FormQuery(String key) {
        String fName="[isParameterBlank4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                value=gFormQuerys.get(key).isBlank();
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getString4FormQuery(Request req,String key) {
        String fName="[getString4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            String value="";
            if(req.getFormQuery(key)!=null){
                value=req.getFormQuery(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean4FormQuery(String key) {
        String fName="[getBoolean4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                has=true;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    value=gFormQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return  true;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  true;}
            if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1")){ logger.info(fName+"return value true");return  true;}
            else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("0")){  logger.info(fName+"return value false");return  false;}
            logger.info(fName+"return last:false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong4FormQuery(String key) {
        String fName="[getLong4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                has=true;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    value=gFormQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0L;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0L;}
            long valueL= Long.parseLong(value);
            logger.info(fName+"valueL="+valueL);
            return  valueL;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt4FormQuery(String key) {
        String fName="[getLong4FormQuery]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gFormQuerys!=null&&!gFormQuerys.isEmpty()&&gFormQuerys.containsKey(key)){
                has=true;
                if(gFormQuerys.get(key)!=null&&gFormQuerys.get(key).isBlank()){
                    value=gFormQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0;}
            int valueI= Integer.parseInt(value);
            logger.info(fName+"valueI="+valueI);
            return  valueI;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public boolean hasParameter4Query(String key) {
        String fName="[hasParameter4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=true;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterNull4Query(String key) {
        String fName="[isParameterNull4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=gQuerys.get(key)==null;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterBlank4Query(String key) {
        String fName="[isParameterBlank4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                value=gQuerys.get(key).isBlank();
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getString4Query(Request req,String key) {
        String fName="[getString4Query]";
        try {
            logger.info(fName+"key="+key);
            String value="";
            if(req.getQuery(key)!=null){
                value=req.getQuery(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean4Query(String key) {
        String fName="[getBoolean4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                has=true;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    value=gQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return  true;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  true;}
            if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("1")){ logger.info(fName+"return value true");return  true;}
            else if(value.equalsIgnoreCase("false")||value.equalsIgnoreCase("0")){  logger.info(fName+"return value false");return  false;}
            logger.info(fName+"return last:false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong4Query(String key) {
        String fName="[getLong4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                has=true;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    value=gQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0L;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0L;}
            long valueL= Long.parseLong(value);
            logger.info(fName+"valueL="+valueL);
            return  valueL;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt4Query(String key) {
        String fName="[getLong4Query]";
        try {
            logger.info(fName+"key="+key);
            boolean has=false;String value="";
            if(gQuerys!=null&&!gQuerys.isEmpty()&&gQuerys.containsKey(key)){
                has=true;
                if(gQuerys.get(key)!=null&&gQuerys.get(key).isBlank()){
                    value=gQuerys.get(key);
                }
            }
            logger.info(fName+"value="+value);
            if(has&&value==null){logger.info(fName+"return value is null true");return 0;}
            if(has&&value.isBlank()){logger.info(fName+"return value is blank true");return  0;}
            int valueI= Integer.parseInt(value);
            logger.info(fName+"valueI="+valueI);
            return  valueI;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    public boolean hasParameter4JsonObject(String key) {
        String fName="[hasParameter4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=true;
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterNull4JsonObject(String key) {
        String fName="[isParameterNull4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=inputJSONObject.isNull(key);
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isParameterBlank4JsonObject(String key) {
        String fName="[isParameterBlank4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            boolean value=false;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()&&inputJSONObject.has(key)){
                value=inputJSONObject.getString(key).isBlank();
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getString4JsonObject(Request req,String key) {
        String fName="[getString4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            String value="";
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()){
                if(inputJSONObject.has(key)&&!inputJSONObject.isNull(key)){
                    try {
                        value=inputJSONObject.getString(key);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            logger.info(fName+"value="+value);
            return  value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean getBoolean4JsonObject(String key) {
        String fName="[getBoolean4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            String value="";boolean valueB=false;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()){
                if(inputJSONObject.has(key)&&!inputJSONObject.isNull(key)){
                    try {
                        valueB= inputJSONObject.getBoolean(key);
                        logger.info(fName+"valueB="+valueB);
                        return valueB;
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        try {
                            value=inputJSONObject.getString(key);
                            logger.info(fName+"value="+value);
                            switch (value.toLowerCase()){
                                case"1": case"true":valueB=true;break;
                            }
                            logger.info(fName+"valueB="+valueB);
                            return valueB;
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    }
                }
            }
            logger.info(fName+"none");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public long getLong4JsonObject(String key) {
        String fName="[getLong4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            String value="";long valueL=0;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()){
                if(inputJSONObject.has(key)&&!inputJSONObject.isNull(key)){
                    try {
                        valueL= inputJSONObject.getLong(key);
                        logger.info(fName+"valueL="+valueL);
                        return valueL;
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        try {
                            value=inputJSONObject.getString(key);
                            logger.info(fName+"value="+value);
                            valueL= Long.parseLong(value);
                            logger.info(fName+"valueL="+valueL);
                            return valueL;
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    }
                }
            }
            logger.info(fName+"none");
            return 0L;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    public int getInt4JsonObject(String key) {
        String fName="[getLong4JsonObject]";
        try {
            logger.info(fName+"key="+key);
            String value="";int valueI=0;
            if(inputJSONObject!=null&&!inputJSONObject.isEmpty()){
                if(inputJSONObject.has(key)&&!inputJSONObject.isNull(key)){
                    try {
                        valueI= inputJSONObject.getInt(key);
                        logger.info(fName+"valueI="+valueI);
                        return valueI;
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        try {
                            value=inputJSONObject.getString(key);
                            logger.info(fName+"value="+value);
                            valueI= Integer.parseInt(value);
                            logger.info(fName+"valueI="+valueI);
                            return valueI;
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    }
                }
            }
            logger.info(fName+"none");
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
}
