package web.jexpress.models;

import express.http.response.Response;
import express.utils.MediaType;
import express.utils.Status;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;

public interface expStats {
    public static final String
            INFO_OK = "200 OK",
            INFO_PARTIALCONTENT = "206 Partial Content",
            INFO_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable",
            INFO_FORBIDDEN = "403 Forbidden",
            INFO_NOTFOUND = "404 Not Found",
            INFO_BADREQUEST = "400 Bad Request",
            INFO_INTERNALERROR = "500 Internal Server Error",
            INFO_NOTIMPLEMENTED = "501 Not Implemented";
    public static final int
            CODE_OK = Status._200.getCode(),
            CODE_PARTIALCONTENT = Status._206.getCode(),
            CODE_RANGE_NOT_SATISFIABLE = Status._416.getCode(),
            CODE_FORBIDDEN = Status._403.getCode(),
            CODE_NOTFOUND = Status._404.getCode(),
            CODE_BADREQUEST = Status._400.getCode(),
            CODE_INTERNALERROR = Status._500.getCode(),
            CODE_NOTIMPLEMENTED = Status._501.getCode();
    public static final Status
            STATUS_OK = Status._200,
            STATUS_PARTIALCONTENT = Status._206,
            STATUS_RANGE_NOT_SATISFIABLE = Status._416,
            STATUS_FORBIDDEN = Status._403,
            STATUS_NOTFOUND = Status._404,
            STATUS_BADREQUEST = Status._400,
            STATUS_INTERNALERROR = Status._500,
            STATUS_NOTIMPLEMENTED = Status._501;
    public static final String
            LABEL_OK =  Status._200.getDescription(),
            LABEL_PARTIALCONTENT =  Status._206.getDescription(),
            LABEL_RANGE_NOT_SATISFIABLE = Status._416.getDescription(),
            LABEL_FORBIDDEN = Status._403.getDescription(),
            LABEL_NOTFOUND =  Status._404.getDescription(),
            LABEL_BADREQUEST =  Status._400.getDescription(),
            LABEL_INTERNALERROR = Status._500.getDescription(),
            LABEL_NOTIMPLEMENTED = Status._501.getDescription(),
            LABEL_INPUTINCOMPLRT = "Input Incomplete",
            LABEL_MISSINGVALUE = "Missing Value";
    public static final MediaType
            MediaType_PLAINTEXT =MediaType._txt,
            MediaType_JSON =MediaType._json;
    public static final String
            MIME_PLAINTEXT = MediaType._txt.getMIME(),
            MIME_JSON = MediaType._json.getMIME();
    public static final MediaType
            InputContentType_MediaType_Form_UrlEncoded =MediaType._xww,
            InputContentType_MediaType_JSON=MediaType._json,
            InputContentType_MediaType_Text=MediaType._txt;
    public static final String
            InputContentType_MIME_Form_UrlEncoded =MediaType._xww.getMIME(),
            InputContentType_MIME_JSON =MediaType._json.getMIME(),
            InputContentType_MIME_Text =MediaType._txt.getMIME();
    public static final String
            HeaderName_ContentType ="Content-Type",
            HeaderName_AccessControlAllowOrigin ="Access-Control-Allow-Origin";
    public static final String
            HeaderValue_AccessControlAllowOrigin ="*";
    String keyStatus="status",keyCode="code", keyMessage ="message",keyLable="label";

    static boolean respondWithUnknown(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respondInternalError(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondInternalError]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public  static String
        keyMeta="meta",keyInfo="info";
    static boolean respond_NoParameters(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoGuildId(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoMemberId(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, "*");
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoRoleId(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoChannelId(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, "*");
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoEmoteId(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_InvalidGuild(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_InvalidMember(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_InvalidRole(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_InvalidChannel(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_InvalidEmote(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respondWithUnknown]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,INFO_NOTFOUND);
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean hasKeyParameter(Map<String, List<String>> parameters,String key) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[hasKeyParameter]";
        try {
            return hasKeyParameter(parameters,key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean hasKeyParameter(Map<String, List<String>> parameters,String key,int index) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[hasKeyParameter]";
        try {
            logger.info(fName+"key="+key+", index="+index+", parameters="+parameters.toString());
            boolean hasKey=false;String key2="";
            if(parameters.containsKey(key)&&!parameters.get(key).isEmpty()){
                logger.info(fName + "has true");
                key2=key;hasKey=true;
            }
            if(!hasKey&&parameters.containsKey(key.toLowerCase())&&!parameters.get(key.toLowerCase()).isEmpty()){
                logger.info(fName + "has true");
                key2=key.toLowerCase();hasKey=true;
            }
            if(!hasKey){
                Set<String> keys=parameters.keySet();
                Iterator<String>keysIterator=keys.iterator();
                while(!hasKey&&keysIterator.hasNext()){
                    key2=keysIterator.next();
                    if(key2.equalsIgnoreCase(key)&&!parameters.get(key2).isEmpty()){
                        logger.info(fName + "has true");
                        hasKey=true;
                    }
                }
            }
            List<String>list=parameters.get(key2);
            if(list.isEmpty()){logger.warn(fName + "list is null");return false;}
            if(index>=list.size()){logger.warn(fName + "asking index that does not exists");return false;}
            logger.info(fName + "return value");
            String a=list.get(index);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getKeyParameter(Map<String, List<String>> parameters,String key) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[getKeyParameter]";
        try {
            return getKeyParameter(parameters,key,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getKeyParameter(Map<String, List<String>> parameters,String key,int index) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[getKeyParameter]";
        try {
            logger.info(fName+"key="+key+", index="+index+", parameters="+parameters.toString());
            boolean hasKey=false;String key2="";
            if(parameters.containsKey(key)&&!parameters.get(key).isEmpty()){
                logger.info(fName + "has true");
                key2=key;hasKey=true;
            }
            if(!hasKey&&parameters.containsKey(key.toLowerCase())&&!parameters.get(key.toLowerCase()).isEmpty()){
                logger.info(fName + "has true");
                key2=key.toLowerCase();hasKey=true;
            }
            if(!hasKey){
                Set<String> keys=parameters.keySet();
                Iterator<String>keysIterator=keys.iterator();
                while(!hasKey&&keysIterator.hasNext()){
                    key2=keysIterator.next();
                    if(key2.equalsIgnoreCase(key)&&!parameters.get(key2).isEmpty()){
                        logger.info(fName + "has true");
                        hasKey=true;
                    }
                }
            }
            List<String>list=parameters.get(key2);
            if(list.isEmpty()){logger.warn(fName + "list is null");return null;}
            if(index>=list.size()){logger.warn(fName + "asking index that does not exists");return null;}
            logger.info(fName + "return value");
            return list.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Response defaultHeaders(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[defaultHeaders]";
        try {
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            logger.info(fName+"header set");
            logger.info(fName+"Content-Type="+res.getHeader(HeaderName_ContentType).toString());
            logger.info(fName+"Access-Control-Allow-Origin="+res.getHeader(HeaderName_AccessControlAllowOrigin).toString());
            return res;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    static boolean respond_NoTokenFoundInCookie(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respond_NoTokenFoundInCookie]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,"No token found in cookie");
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin,HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_NoUserIdFoundInCookie(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respond_NoUserIdFoundInCookie]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,"No userId found in cookie");
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            //res.setStatus(STATUS_NOTFOUND);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean respond_PageNotFound404(Response res) {
        Logger logger = Logger.getLogger(expStats.class);String fName="[respond_PageNotFound404]";
        try {
            logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
            jsonStatus.put(keyCode,CODE_NOTFOUND);
            jsonStatus.put(keyLable,LABEL_NOTFOUND);
            jsonStatus.put(keyMessage,"No such routine");
            jsonResponse.put(keyStatus,jsonStatus);
            res.setHeader(HeaderName_ContentType, MIME_JSON);
            res.setContentType(MIME_JSON);
            res.setHeader(HeaderName_AccessControlAllowOrigin, HeaderValue_AccessControlAllowOrigin);
            res.send(jsonResponse.toString());
            logger.info(fName+"return response");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


}
