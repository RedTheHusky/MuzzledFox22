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

import java.io.StringReader;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    public USER getUserProfile(String name) {
        String fName = "[getUserProfile]";
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
                return  new USER();
            }
            return  new USER(stringHttpResponse);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  class USER{
        HttpResponse<String> httpResponse=null;
        String image="",accountstatus="";
        public USER(){

        }
        public USER(HttpResponse<String> httpResponse){
            set(httpResponse);
        }
        public USER set(HttpResponse<String> httpResponse) {
            String fName = "[set]";
            try {
                this.httpResponse=httpResponse;
                String body=httpResponse.getBody();
                logger.info(fName+".body ="+body);
                String textShadow="";
                int itemsI=-1,itemsJ=-1,l;
                String begin="";


                begin="img class=\"user-nav-avatar";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    itemsI=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("\">");
                    begin="src=\"//";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        itemsI= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(itemsI+l);
                        itemsJ=textShadow.indexOf("\"");
                        if(itemsI>0&&itemsJ>0) {
                            image="https://"+textShadow.substring(0, itemsJ);
                            logger.info(fName+"image="+image);
                        }
                    }
                }


                begin="div id=\"user-profile";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    itemsI=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(itemsI);
                    //itemsJ=textShadow.indexOf(">");
                    begin="title=\"";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        itemsI= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(itemsI+l);
                        itemsJ=textShadow.indexOf("\"");
                        if(itemsI>0&&itemsJ>0) {
                            accountstatus=textShadow.substring(0, itemsJ);
                            logger.info(fName+"accountstatus="+accountstatus);
                        }
                    }
                }
                return this;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public USER set2(HttpResponse<String> httpResponse) {
            String fName = "[set]";
            try {
                this.httpResponse=httpResponse;
                String body=httpResponse.getBody();
                logger.info(fName+".body ="+body);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(body));
                Document doc = dBuilder.parse(is);
                logger.info(fName+".success built");
                logger.info(fName+".doc.toString ="+ doc.toString());

                return this;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public HttpResponse<String> getResponse() {
            String fName = "[getResponse]";
            try {
                return httpResponse;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getBody() {
            String fName = "[getBody]";
            try {
                return httpResponse.getBody();
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImage() {
            String fName = "[getImage]";
            try {
                return image;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getAccountStatus() {
            String fName = "[getAccountStatus]";
            try {
                return accountstatus;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getStatus() {
            String fName = "[getStatus]";
            try {
                return httpResponse.getStatus();
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
    public GalleryImages getGallerySearch(JSONObject options) {
        String fName = "[reqGetGallerySearch]";
        try {
            logger.info(fName+".options="+options.toString());
            HttpResponse<String> stringHttpResponse=reqGetGallerySearch(options);
            if(stringHttpResponse.getStatus()>299) {
                logger.error(fName + ".invalid status");
                return  new GalleryImages();
            }
            return new GalleryImages(stringHttpResponse);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GalleryImages getGalleryUser(String name,int page) {
        String fName = "[reqGetGallerySearch]";
        try {
            logger.info(fName+".name="+name+", page="+page);
            HttpResponse<String> stringHttpResponse=reqGetUserGallery(name,page);
            if(stringHttpResponse.getStatus()>299) {
                logger.error(fName + ".invalid status");
                return  new GalleryImages();
            }
            return new GalleryImages(stringHttpResponse);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  class GalleryImages{
        HttpResponse<String> httpResponse=null;
        String keyItemId="id",keyItemRating="rating",keyItemType="type",keyItemPage="page",keyItemUser="user",keyItemPreview="preview",keyItemTitle="title",keyItemUserName="userName";
        public GalleryImages(){

        }
        public GalleryImages(HttpResponse<String> httpResponse){
            set(httpResponse);
        }
        List<GalleryImage>images=new ArrayList<>();
        public GalleryImages set(HttpResponse<String> httpResponse) {
            String fName = "[set]";
            try {
                this.httpResponse=httpResponse;
                String body=httpResponse.getBody();
                logger.info(fName+".body ="+body);
                logger.info(fName+".body ="+body);
                String text="", textShadow="";
                int itemsI=-1,itemsJ=-1;
                if(body.contains("gallery-search-results")){
                    itemsI=body.indexOf("gallery-search-results");
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("/section");
                    if(itemsI>0&&itemsJ>0){
                        text=textShadow.substring(0,itemsJ-1);
                    }
                }else
                if(body.contains("gallery-gallery")){
                    logger.info(fName+".found="+"gallery-gallery");
                    itemsI=body.indexOf("gallery-gallery");
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("/section");
                    if(itemsI>0&&itemsJ>0){
                        text=textShadow.substring(0,itemsJ-1);
                    }
                }
                text=text.replaceAll("</figure><!--","").replaceAll("-->","").replaceFirst("<figure","");
                logger.info(fName+".gallery-search-results ="+text);
                String [] items = text.split("<figure");
                images=new ArrayList<>();
                for(String item : items){
                    JSONObject jsonObject=convertText2JSONItem(item);
                    if(jsonObject.getString("type").equalsIgnoreCase("image")){
                        images.add(new GalleryImage(jsonObject));
                    }
                }
                return this;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public HttpResponse<String> getResponse() {
            String fName = "[getResponse]";
            try {
                return httpResponse;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getBody() {
            String fName = "[getBody]";
            try {
                return httpResponse.getBody();
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private JSONObject convertText2JSONItem(String text){
            String fName = "[convertText2JSONItem]"; logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                text="."+text;
                logger.info(fName+".text="+text);
                int i=-1,j=-1,l=1;
                String textShadow="";
                String begin="id=\"sid-";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\" class=");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemId,textShadow.substring(0,j));
                    }
                }
                begin="src=\"//";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\"");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemPreview,textShadow.substring(0,j));
                    }
                }
                if(text.contains("r-general")){ jsonObject.put(keyItemRating,0);}
                else if(text.contains("r-mature")){ jsonObject.put(keyItemRating,1);}
                else if(text.contains("r-adult")){ jsonObject.put(keyItemRating,2);}
                else { jsonObject.put(keyItemRating,-1);}
                if(text.contains("t-image")){ jsonObject.put(keyItemType,"image");}
                else{jsonObject.put(keyItemType,"invalid");}

                begin="href=\"/view/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf( begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemPage, textShadow.substring(0, j));
                    }
                    begin="/\" title=\"";
                    if( textShadow.contains(begin)&& textShadow.contains("href=\"/user/")){
                        i= textShadow.indexOf(begin);
                        int i2= textShadow.indexOf("href=\"/user/");
                        logger.info(fName+".i<i2: "+i+" < "+i2);
                        if(i<i2){
                            l=begin.length();
                            textShadow= textShadow.substring(i+l);
                            j=textShadow.indexOf("\"");
                            if(i>0&&j>0) {
                                jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                            }
                        }else{
                            logger.warn(fName+".invalid i<i2: "+i+" < "+i2);
                        }
                    }else{
                        logger.warn(fName+".no author found");
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                        }
                    }
                }
                begin="href=\"/user/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemUser, textShadow.substring(0, j));
                    }
                    begin="title=\"";
                    if( textShadow.contains(begin)){
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemUserName, textShadow.substring(0, j));
                        }
                    }
                }
                logger.info(fName+".json ="+jsonObject);
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        public List<GalleryImage> getImages() {
            String fName = "[getImages]";
            try {
                return images;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GalleryImage getImage(int index) {
            String fName = "[getImage]";
            try {
                return images.get(index);
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEmpty() {
            String fName = "[isEmpty]";
            try {
                boolean value=images==null||images.isEmpty();
                logger.info(fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int length() {
            String fName = "[length]";
            try {
                int value=images.size();
                logger.info(fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public  class GalleryImage{
            JSONObject jsonObject=null;
            String keyItemId="id",keyItemRating="rating",keyItemType="type",keyItemPage="page",keyItemUser="user",keyItemPreview="preview",keyItemTitle="title",keyItemUserName="userName";
            public GalleryImage(){

            }
            public GalleryImage(JSONObject jsonObject){
                set(jsonObject);
            }
            protected String title="",username="",preview="",user="";
            protected int rating=-1;
            public lcFURAFFINITY.GalleryImages.GalleryImage set(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    this.jsonObject=jsonObject;
                    if(jsonObject.has(keyItemTitle)){
                        title=jsonObject.getString(keyItemTitle);
                    }
                    if(jsonObject.has(keyItemUserName)){
                        username=jsonObject.getString(keyItemUserName);
                    }
                    if(jsonObject.has(keyItemRating)){
                        rating=jsonObject.getInt(keyItemRating);
                    }
                    if(jsonObject.has(keyItemPreview)){
                        preview=jsonObject.getString(keyItemPreview);
                    }
                    if(jsonObject.has(keyItemUser)){
                        user=jsonObject.getString(keyItemUser);
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONObject getJson() {
                String fName = "[getJson]";
                try {
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public int getRating() {
                String fName = "[getRating]";
                try {

                    logger.info(fName+"value="+rating);
                    return rating;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public boolean isRatingAdult() {
                String fName = "[isRatingAdult]";
                try {
                    boolean value=getRating()==2;
                    logger.info(fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public boolean isRatingMature() {
                String fName = "[isRatingMature]";
                try {
                    boolean value=getRating()==1;
                    logger.info(fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public boolean isRatingGeneral() {
                String fName = "[isRatingGeneral]";
                try {
                    boolean value=getRating()==0;
                    logger.info(fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public String getPreview() {
                String fName = "[getPreview]";
                try {
                    logger.info(fName+"value="+preview);
                    return preview;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getTitle() {
                String fName = "[getTitle]";
                try {
                    logger.info(fName+"value="+title);
                    return title;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getUserAsString() {
                String fName = "[getUserAsString]";
                try {
                    logger.info(fName+"value="+user);
                    return user;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getUserName() {
                String fName = "[getUserName]";
                try {
                    logger.info(fName+"value="+username);
                    return username;
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public USER getUser() {
                String fName = "[getUser]";
                try {
                    logger.info(fName+"value="+user);
                    return getUserProfile(user);
                } catch (Exception e) {
                    logger.error(fName+"exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

        }
    }
}
