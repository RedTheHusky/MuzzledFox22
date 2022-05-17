package nsfw.chastity.lockedmen;

import kong.unirest.Cookie;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ls.lsStringUsefullFunctions;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cLockedmenMember {
    Logger logger = Logger.getLogger(getClass());
    public cLockedmenMember(){

    }
    private List<Cookie>cookies=new ArrayList<>();
    public cLockedmenMember setCookies(List<Cookie>cookies) {
        String fName="[setCookies]";
        logger.info(fName);
        try{
            if(cookies==null)throw new Exception("input is null");
            this.cookies=cookies;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    private JSONObject getHttp(String url) {
        String fName="[getHttp]";
        logger.info(fName);
        JSONObject jsonObject=new JSONObject();
        try{
            if(url==null)throw  new Exception("url is null!");
            if(url.isBlank())throw  new Exception("url is blank!");
            logger.info(fName+"url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            logger.info(fName + ".ready to send");
            HttpResponse<String> response =a.get(url)
                    .cookie(cookies).asString();
            logger.info(fName + ".response.getStatus="+response.getStatus());
            jsonObject.put(iLockedmen.HTTPResponse.status,new JSONObject());
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.status).put(iLockedmen.HTTPResponse.message,response.getStatusText());
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.status).put(iLockedmen.HTTPResponse.code,response.getStatus());
            if(response.getStatus()>299||response.getStatus()<200){
                throw  new Exception("Invalid status");
            }
            logger.info(fName + ".body="+response.getBody());
            JSONArray jsonArray =new JSONArray(response.getBody());
            if(jsonArray.isEmpty()) throw  new Exception("jsonArray isEmpty");
            jsonObject.put(iLockedmen.HTTPResponse.body,jsonArray);
            jsonObject.put(iLockedmen.HTTPResponse.result,true);
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            jsonObject.put(iLockedmen.HTTPResponse.exception,new JSONObject());
            jsonObject.put(iLockedmen.HTTPResponse.result,false);
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.exception).put(iLockedmen.HTTPResponse.message,e);
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.exception).put(iLockedmen.HTTPResponse.code,-1);
            //logger.info(fName + ".illt="+ lsStringUsefullFunctions.getCharAt(url,89));
            return  jsonObject;
        }
    }
    private JSONObject jsonUser=new JSONObject();
    private JSONObject getUserProfileJsonResponse(String id) {
        String fName="[getSiteStatsJsonResponse]";
        logger.info(fName);
        try{
            if(id==null)throw  new Exception("id can't be null");
            if(id.isBlank())throw  new Exception("id can't be blank");
            logger.info(fName + ".id="+id);
            String url="";
            /*if(id.contains("{")){
                url=iLockedmen.URL.getUserProfile_b.replaceAll("!ID",id);
            }else{
                url=iLockedmen.URL.getUserProfile_a.replaceAll("!ID",id);
            }*/
            id=id.replaceAll("\\{","").replaceAll("}","");
            url=iLockedmen.URL.getUserProfile.replaceAll("!ID",id);
            JSONObject jsonObject=getHttp(url);
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(iLockedmen.HTTPResponse.exception,new JSONObject());
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.exception).put(iLockedmen.HTTPResponse.message,e);
            jsonObject.getJSONObject(iLockedmen.HTTPResponse.exception).put(iLockedmen.HTTPResponse.code,-1);
            jsonObject.put(iLockedmen.HTTPResponse.result,false);
            return  jsonObject;
        }
    }
    public  cLockedmenMember getUserProfile(String id) {
        String fName="[getUserProfile]";
        logger.info(fName);
        try{
            JSONObject jsonObject=getUserProfileJsonResponse(id);
            if(jsonObject==null)throw  new Exception("json is null");
            if(jsonObject.isEmpty())throw  new Exception("json is empty");
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
            jsonUser=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body).getJSONObject(0);
            logger.info(fName + ".jsonUser="+jsonUser.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            jsonUser=new JSONObject();
            return  null;
        }
    }
}
