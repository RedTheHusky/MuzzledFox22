package search.entities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class lcFURAFFINITY {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[FURAFFINITY]";
    public lcFURAFFINITY(lcGlobalHelper global) {
        this.global=global;
    }
    lcGlobalHelper global;
    public  interface  INTERFACE{
        String gUrlFapi="https://bawk.space/fapi/submission/%";
        String keyAuthor="author",keyAvatar="avatar",keyImageUrl="image_url",keyRating="rating",keyTitle="title";
        String gLogo="https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png";
        String gUrlMain="https://www.furaffinity.net";
        String gUrlSearch ="https://www.furaffinity.net/search/";
        String gUrlView ="https://www.furaffinity.net/view/%/";
        String gUrlUser="https://www.furaffinity.net/user/%/";
        String gUrlUserGallery="https://www.furaffinity.net/gallery/%/";
        //String gCookie= llGlobalHelper.FurAffinityAuth.cookie;
        String keyRatingGeneral="rating-general",keyRatingMature="rating-mature",keyRatingAdult="rating-adult",valueOn="on"; boolean booleanOn=true;
        String keyPage="page",valueFirstPage="1";
        String keyResetPage="reset_page", valueDefaultResetPage ="1";
        String keyQ="q",keyDoSearch="do_search", valueDoSearch="Search";
        String keyOrderBy="order-by",valueOrderByPopularity="popularity",valueOrderByDate="date",valueOrderByRelevancy="relevancy";
        String keyOrderDirection="order-direction",valueDesc="desc",valueAsc="asc";
        String keyRange="range",valueRangeAll="all",valueRangeDay="day",valueRange3Days="3days",valueRangeWeek="week", valueRangeMonth="month";
        String keyTypeArt="type-art",keyTypeMusic="type-music";
        String keyMode="mode", valueModeExtended="extended", valueModeAll="all", valueModeAny="any";
        String keyOne="one", valueFirstOne="on";
        String valueGeneral="general",valueMature="mature",valueAdult="adult";
        String keyItemId="id",keyItemRating="rating",keyItemType="type",keyItemPage="page",keyItemUser="user",keyItemPreview="preview",keyItemTitle="title",keyItemUserName="userName";
        String keyIndex="index";
    }



    public String getViewIDbyAddress(String address) {
        String fName = "[getViewUrl]";
        try {
            logger.info(fName+".address ="+address);
            //https://www.furaffinity.net/view/%id/
            String viewId=address;
            if(viewId.toLowerCase().contains("https:")||viewId.toLowerCase().contains("http:")){
                logger.info(fName+".mode given address");
                viewId=viewId.replace("https:","").replace("http:","").replace("//","");
                if(viewId.toLowerCase().contains("furaffinity.net/view/")){
                    viewId=viewId.replace("www.furaffinity.net/view/","").replace("furaffinity.net/view/","");
                    int index=viewId.indexOf("/");
                    if(index>0){
                        StringBuffer stringBuffer = new StringBuffer(viewId);
                        stringBuffer.replace( index , stringBuffer.length()-1,"");
                        viewId=stringBuffer.toString();
                    }
                }else{
                    logger.warn("Invalid address");
                    return null;
                }
            }else{
                logger.info(fName+".mode given id");
                viewId=address;
            }
            logger.info(fName+".viewId ="+viewId);
            return viewId;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public String getViewUrlbyAddress(String address) {
        String fName = "[getViewUrlbyAddress]";
        try {
            logger.info(fName+".address ="+address);
            String viewId= getViewIDbyAddress(address);
            if(viewId==null||viewId.isBlank()){
                logger.warn(fName+".viewID is null or blank");
                return null;
            }
            logger.info(fName+".viewId ="+viewId);
            String url=INTERFACE.gUrlFapi.replaceAll("%",viewId);
            logger.info(fName+".url ="+url);
            return url;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public String getViewUrlbyId(String viewId) {
        String fName = "[getViewUrlbyId]";
        try {
            if(viewId==null||viewId.isBlank()){
                logger.warn(fName+".viewID is null or blank");
                return null;
            }
            logger.info(fName+".viewId ="+viewId);
            String url=INTERFACE.gUrlFapi.replaceAll("%",viewId);
            logger.info(fName+".url ="+url);
            return url;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public HttpResponse<JsonNode> reqGetSubmissionView(String url) {
        String fName = "[reqGetSubmissionView]";
        try {
            Unirest a= new Unirest();
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("dnt","1")
                    .header("referer:",url)
                    .header("sec-fetch-dest","document")
                    .header("sec-fetch-mode","navigate")
                    .header("sec-fetch-site:","same-origin")
                    .header("sec-fetch-user","?1")
                    .header("upgrade-insecure-requests","1")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            return  jsonResponse;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    public HttpResponse<String> reqGetSubmissionViewOld(String url) {
        String fName = "[reqGetSubmissionViewOld]";
        try {
            Unirest a= new Unirest();
            logger.info(fName+".url ="+url);
            HttpResponse<String> stringHttpResponse =a.get(url)
                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("cookie",Config.getCookie(global))
                    .header("dnt","1")
                    .header("referer:","https://www.furaffinity.net/browse/")
                    .header("sec-fetch-dest","document")
                    .header("sec-fetch-mode","navigate")
                    .header("sec-fetch-site:","same-origin")
                    .header("sec-fetch-user","?1")
                    .header("upgrade-insecure-requests","1")
                    .asString();
            logger.info(fName+".status ="+stringHttpResponse.getStatus());
            return  stringHttpResponse;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    public JSONObject getViewJson(String address) {
        String fName = "[getViewJson]";
        try {
            logger.info(fName+".address ="+address);
            String url= getViewUrlbyAddress(address);
            if(url==null||url.isBlank()){
                logger.error(fName+"invalid address");
                return  new JSONObject();
            }
            HttpResponse<JsonNode>jsonResponse= reqGetSubmissionView(url);
            JSONObject jsonObject=jsonResponse.getBody().getObject();
            logger.info(fName+".jsonObject ="+jsonObject.toString());
            return  jsonObject;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    public HttpResponse<String> reqGetUserGallery(String name, int page) {
        String fName = "[getUserGallery]";
        try {
            logger.info(fName+".name ="+name);
            logger.info(fName+".page ="+page);
            Unirest a= new Unirest();
            String url=INTERFACE.gUrlUserGallery.replaceAll("%",name);
            logger.info(fName+".url ="+url);
            String refer=INTERFACE.gUrlMain;
            if(page>1){
                refer+="/gallery/"+name+"/";
                url+=page+"/?";
            }
            logger.info(fName+".url ="+url);
            logger.info(fName+".refer ="+refer);
            HttpResponse<String> stringHttpResponse =a.get(url)
                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("cookie", Config.getCookie(global))
                    .header("dnt","1")
                    .header("referer:",refer)
                    .header("sec-fetch-dest","document")
                    .header("sec-fetch-mode","navigate")
                    .header("sec-fetch-site:","same-origin")
                    .header("sec-fetch-user","?1")
                    .header("upgrade-insecure-requests","1")
                    .asString();
            logger.info(fName+".status ="+stringHttpResponse.getStatus());
            return  stringHttpResponse;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<String> reqGetUserProfile(String name) {
        String fName = "[getUserPage]";
        try {
            logger.info(fName+".name ="+name);
            Unirest a= new Unirest();
            String url=INTERFACE.gUrlUser.replaceAll("%",name);
            logger.info(fName+".url ="+url);
            HttpResponse<String> stringHttpResponse =a.post(url)
                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("cookie",Config.getCookie(global))
                    .header("dnt","1")
                    .header("referer:","https://www.furaffinity.net/")
                    .header("sec-fetch-dest","document")
                    .header("sec-fetch-mode","navigate")
                    .header("sec-fetch-site:","same-origin")
                    .header("sec-fetch-user","?1")
                    .header("upgrade-insecure-requests","1")
                    .asString();
            logger.info(fName+".status ="+stringHttpResponse.getStatus());
            if(stringHttpResponse.getStatus()>299) {
                logger.error(fName + ".invalid status");
            }
            return stringHttpResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public Map<String,Object> optionConvert2Map(JSONObject options) {
        String fName = "[optionConvert2Map]";
        try {
            logger.info(fName+".options="+options);
            Map<String,Object> mapOptions=new LinkedHashMap<>();
            Iterator<String> keys=options.keys();
            while(keys.hasNext()){
                String key=keys.next();
                Object value=options.get(key);
                mapOptions.put(key,value);
            }
            int i=0;
            for (Map.Entry<String, Object> entry : mapOptions.entrySet()) {
                logger.info(fName+".mapOptions["+i+"]="+entry.getKey()+ ":" + entry.getValue().toString());i++;
            }
            return mapOptions;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new LinkedHashMap<>();
        }
    }
    public HttpResponse<String> reqGetGallerySearch(JSONObject options) {
        String fName = "[reqGetGallerySearch]";
        try {
            logger.info(fName+".options="+options);
            return reqGetGallerySearch(optionConvert2Map(options));
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<String> reqGetGallerySearch(Map<String,Object> options) {
        String fName = "[reqGetGallerySearch]";
        try {
            logger.info(fName+".options="+options.toString());
            Unirest a= new Unirest();
            HttpResponse<String> stringHttpResponse =a.post(INTERFACE.gUrlSearch)
                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("cookie",Config.getCookie(global))
                    .header("dnt","1")
                    .header("origin","https://www.furaffinity.net")
                    .header("referer:","https://www.furaffinity.net/search")
                    .header("sec-fetch-dest","document")
                    .header("sec-fetch-mode","navigate")
                    .header("sec-fetch-site:","same-origin")
                    .header("sec-fetch-user","?1")
                    .header("upgrade-insecure-requests","1")
                    .queryString(options)
                    .asString();
            logger.info(fName+".headers ="+stringHttpResponse.getHeaders().toString());
            logger.info(fName+".status ="+stringHttpResponse.getStatus());
            if(stringHttpResponse.getStatus()>299){
                logger.error(fName+".invalid status");
            }
            return stringHttpResponse;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }

    public interface Config{
        interface Keys {
            String config="FurAffinity",
                    cookie="cookie";
        }
        static String getCookie(lcGlobalHelper global){
            String fName="[getCookie]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Config.Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.cookie;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
                    String value=jsonProperties.optString(key);
                    logger.info(fName+"value="+value);
                    return value;
                }
                logger.warn(fName+" json("+ key+") not found");
                return "";
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
    }
}
