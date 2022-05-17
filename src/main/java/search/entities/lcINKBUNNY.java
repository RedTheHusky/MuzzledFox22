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

public class lcINKBUNNY {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[INKBUNNY]";
    public lcINKBUNNY(lcGlobalHelper global) {
        String fName = "[create]";
        try {
            this.global=global;
            gSIDUser=Config.getSid(this.global);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    lcGlobalHelper global;
    public  interface  INTERFACE{
        String gUrlInkBunnyView ="https://inkbunny.net/api_submissions.php?sid=!sid&submission_ids=%";
        String gUrlInkBunnySubmissionPage ="https://inkbunny.net/s/%";
        String gUrlInkBunnyUserPage ="https://inkbunny.net/!name";
        String gUrlLogInGuest ="https://inkbunny.net/api_login.php?username=guest";
        String gUrlLogInUser ="https://inkbunny.net/api_login.php?username=!USERNAME&password=!PASSWORD";
        String gInkBunnyIcon="https://nl.ib.metapix.net/images78/favicon.ico";
        String keyIBSubmissionId="submission_id",keyIBTitle="title",keyIBPublic="public",keyIBMimeType="mimetype",keyIBGuestBlock="guest_block";
        String keyIBKeywords="keywords",keyIBKeywordName="keyword_name",keyIBHidden="hidden";
        String keyIBUserName="username",keyIBUserId="user_id",keyIBUserIconUrlSmall="user_icon_url_small",keyIBUserIconUrlLarge="user_icon_url_large";
        String keyIBFileUrlFull="file_url_full",keyIBFileUrlScreen="file_url_screen",keyIBFileUrlPreview="file_url_preview";
        String keyIBRatingId="rating_id",keyIBRatingName="rating_name", keyIBRatings="ratings",keyIBName="name";
        String keyIBSubmissionTypeId="submission_type_id",keyIBTypeName="type_name",keyCount="count";
        String gUrlSearch ="https://inkbunny.net/api_search.php?sid=!sid&text=!text&get_rid=yes&submissions_per_page=100";
        String gUrlView ="https://inkbunny.net/api_submissions.php?sid=!sid&submission_ids=%";
        String gUrlSubmissionPage="https://inkbunny.net/s/%";
        String gUrlUserPage="https://inkbunny.net/%";
        //String gIncludedSIDUser =llGlobalHelper.InkBunnyAuth.sid;

        String keyType="type", valueTypeSelected="1,2,3,4";int valueTypePicturePinup=1,valueTypeSketch=2,valueTypePictureSeries=3,valueTypeComic=4,valueTypePortofolio=5,valueTypeCharacterSheet=13,valueTypePhotography=14;
        String keyOrderBy="orderby", valueOrderByCreate_datetime="create_datetime", valueOrderByLast_file_update_datetime="last_file_update_datetime", valueOrderByViews="views", valuePrderByUsername="username";
        String keyDaysLimit="dayslimit"; int valueDaysLimit=0;
        String keyScraps="scraps",valueScrapsBoth="both",valueScrapsNo="no",valueScrapsOnly="only";
        String keyRandom="random",keyText="text",keyEnabledKeywords="keywords",keyEnabledTitle="title",keyEnabledDescription="description",keyEnabledMd5="md5",valueEnabled="yes";

        String keySearchInfo_Page="page", keySearchInfo_Submissions="submissions",keySearchInfo_SubmissionIds="submission_ids",keySearchInfo_PagesCount="pages_count", keySearchInfo_ResultsCountAll="results_count_all",keySearchInfo_Rid="rid",keySearchSubmissions="submissions";
        JSONObject searchResponse =new JSONObject();
        JSONArray gallery =new JSONArray();
        JSONObject selectedItem=new JSONObject();
        String keySubmissionId="submission_id",keySubmissionTitle="title",keySubmissionTypeId="submission_type_id",keySubmissionRatingId="rating_id",keySubmissionRatingName="rating_name", keySubmissionRatings ="ratings",keySubmissionFileUrlScreen="file_url_screen",keySubmissionUsername="username",keySubmissionUserId="user_id";
        String keyIndex="index";
    }
    String gSIDUser="";
    public void setSid(String sid) {
        String fName = "[setSid]";
        try {
           gSIDUser=sid;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public JSONObject getImageJSON(String viewId) {
        String fName = "[getImageJSON]";
        try {
            logger.info(fName+".viewId ="+viewId);
            HttpResponse<JsonNode> response=reqGetImage(viewId);
            int status=response.getStatus();
            if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                return new JSONObject();}
            JsonNode body=response.getBody();
            logger.debug(fName+".body ="+body.toPrettyString());
            JSONObject json=body.getObject();
            logger.debug(fName+".json= ="+json.toString());
            return json;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public HttpResponse<JsonNode> reqGetImage(String viewId) {
        String fName = "[reqGetImage]";
        try {
            logger.info(fName+".viewId ="+viewId);
            logger.info(fName+"viewId="+viewId);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=INTERFACE.gUrlInkBunnyView.replaceAll("%",viewId).replaceAll("!sid",gSIDUser);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse=a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqGallerySearch(JSONObject options) {
        String fName = "[reqGallerySearch]";
        try {
            String url=getGallerySearchURl(options);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse =reqGallerySearch(url);
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return  jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public String getGallerySearchURl(JSONObject options) {
        String fName = "[reqGallerySearch]";
        try {
            Unirest a= new Unirest();
            //a.config().verifySsl(false);
            //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
            String url=INTERFACE.gUrlSearch;
            String gInkBunnySIDUser=gSIDUser;
            url=url.replaceAll("!sid",gInkBunnySIDUser);
            if(options.has(INTERFACE.keyText)&&!options.isNull(INTERFACE.keyText)){
                url=url.replaceAll("!text",options.getString(INTERFACE.keyText));
                logger.info(fName + "do search by: "+options.getString(INTERFACE.keyText));
            }else{
                logger.info(fName + "do search by: undefined");
            }
            if(options.has(INTERFACE.keyOrderBy)&&!options.isNull(INTERFACE.keyOrderBy)){
                url+="&"+INTERFACE.keyOrderBy+"="+options.getString(INTERFACE.keyOrderBy);
                if(options.has(INTERFACE.keyRandom)&&!options.isNull(INTERFACE.keyOrderBy)){
                    url+="&"+INTERFACE.keyRandom+"="+INTERFACE.valueEnabled;
                }
            }else{
                url+="&"+INTERFACE.keyRandom+"="+INTERFACE.valueEnabled;
            }
            if(options.has(INTERFACE.keyScraps)&&!options.isNull(INTERFACE.keyScraps)){
                url+="&"+INTERFACE.keyScraps+"="+options.getString(INTERFACE.keyScraps);
            }
            if(options.has(INTERFACE.keyDaysLimit)&&!options.isNull(INTERFACE.keyDaysLimit)){
                url+="&"+INTERFACE.keyDaysLimit+"="+options.getString(INTERFACE.keyDaysLimit);
            }
            if(options.has(INTERFACE.keyType)&&!options.isNull(INTERFACE.keyType)){
                url+="&"+INTERFACE.keyType+"="+options.getString(INTERFACE.keyType);
            }
            if(options.has(INTERFACE.keyEnabledKeywords)&&!options.isNull(INTERFACE.keyEnabledKeywords)){
                url+="&"+INTERFACE.keyEnabledKeywords+"="+options.getString(INTERFACE.keyEnabledKeywords);
            }
            if(options.has(INTERFACE.keyEnabledTitle)&&!options.isNull(INTERFACE.keyEnabledTitle)){
                url+="&"+INTERFACE.keyEnabledTitle+"="+options.getString(INTERFACE.keyEnabledTitle);
            }
            if(options.has(INTERFACE.keyEnabledDescription)&&!options.isNull(INTERFACE.keyEnabledDescription)){
                url+="&"+INTERFACE.keyEnabledDescription+"="+options.getString(INTERFACE.keyEnabledDescription);
            }
            if(options.has(INTERFACE.keyEnabledMd5)&&!options.isNull(INTERFACE.keyEnabledMd5)){
                url+="&"+INTERFACE.keyEnabledMd5+"="+options.getString(INTERFACE.keyEnabledMd5);
            }
            logger.info(fName+".url ="+url);
            return  url;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public HttpResponse<JsonNode> reqGallerySearch(String url) {
        String fName = "[reqGallerySearch]";
        try {
            Unirest a= new Unirest();
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return  jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public String getSubmissionsViewUrl(String ids) {
        String fName = "[getSubmissionsViewUrl]";
        try {
            String url=INTERFACE.gUrlView;
            url=url.replaceAll("!sid",gSIDUser);
            logger.info(fName+".ids ="+ids);
            url=url.replaceAll("%",ids);
            logger.info(fName+".url ="+url);
            return url;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public HttpResponse<JsonNode> reqGetSubmissionsViewById(String id) {
        String fName = "[reqGetSubmissionsViewById]";
        try {
            String url=getSubmissionsViewUrl(id);
            HttpResponse<JsonNode> jsonResponse =reqGetSubmissionsViewByUrl(url);
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqGetSubmissionsViewByUrl(String url) {
        String fName = "[reqGetSubmissionsViewByUrl]";
        try {
            Unirest a= new Unirest();
            HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    JSONObject listmimetype=new JSONObject("{\"image/bmp\":true,\"image/gif\":true,\"image/jpeg\":true,\"image/png\":true}");
    public String getImageUrl(lcGlobalHelper global,String viewId) {
        String fName = "[getImageUrl]";
        try {
            logger.info(fName+".viewId ="+viewId);
            String valueTrue="t",valueFalse="f";
            logger.info(fName+"viewId="+viewId);
            HttpResponse<JsonNode> response=reqGetImage(viewId);
            if(!(200<=response.getStatus()&&response.getStatus()<=299)){ logger.error(fName+".invalid status");
                return null;}
            JsonNode body=response.getBody();
            logger.debug(fName+".body ="+body.toPrettyString());
            JSONObject json=body.getObject();
            logger.debug(fName+".json= ="+json.toString());

            if(json.isEmpty()||!json.has("submissions")||json.isNull("submissions")){
                logger.error(fName+".no submissions");return null;
            }
            JSONArray submissions=json.getJSONArray("submissions");
            if(submissions.isEmpty()||submissions.length()<0){
                logger.error(fName+".no post");return null;
            }
            JSONObject post=submissions.getJSONObject(0);
            logger.info(fName+".post="+post.toString());
            boolean isPublic=false,isGuestBlock=false,isHidden=false,isImage=false, isBadKeywords=false;
            int ratingId=-1;
            String postId="",title="",authorName="",authorId="",authorAvatar="", image="";
            StringBuilder ratings= new StringBuilder();
            if(post.has(INTERFACE.keyIBPublic)&&!post.isNull(INTERFACE.keyIBPublic)&&post.getString(INTERFACE.keyIBPublic).equals(valueTrue)){
                isPublic=true;
            }
            if(post.has(INTERFACE.keyIBGuestBlock)&&!post.isNull(INTERFACE.keyIBGuestBlock)&&post.getString(INTERFACE.keyIBGuestBlock).equals(valueTrue)){
                isGuestBlock=true;
            }
            if(post.has(INTERFACE.keyIBHidden)&&!post.isNull(INTERFACE.keyIBHidden)&&post.getString(INTERFACE.keyIBHidden).equals(valueTrue)){
                isHidden=true;
            }
            if(post.has(INTERFACE.keyIBMimeType)&&!post.isNull(INTERFACE.keyIBMimeType)){
                if(listmimetype.has(post.getString(INTERFACE.keyIBMimeType))&&listmimetype.getBoolean(post.getString(INTERFACE.keyIBMimeType))){
                    isImage=true;
                }
            }
            logger.info(fName+".isPublic="+isPublic+", isHidden="+isHidden+", isImage="+isImage);
            if(!isPublic){
                logger.warn(fName + ".not public");return "";
            }
            if(isHidden){
                logger.warn(fName + ".is hidden");return "";
            }
            if(!isImage){
                logger.warn(fName + ".not image");return "";
            }
            logger.info(fName+".isGuestBlock="+isGuestBlock);

            if(post.has(INTERFACE.keyIBRatingId)&&!post.isNull(INTERFACE.keyIBRatingId)){
                ratingId=post.getInt(INTERFACE.keyIBRatingId);//0-general 1-matur 2-adult
            }
            if(post.has(INTERFACE.keyIBRatingName)&&!post.isNull(INTERFACE.keyIBRatingName)){
                ratings = new StringBuilder(post.getString(INTERFACE.keyIBRatingName));
            }
            if(post.has(INTERFACE.keyIBRatings)&&!post.isNull(INTERFACE.keyIBRatings)){
                JSONArray array=post.getJSONArray(INTERFACE.keyIBRatings);
                for(int i=0;i<array.length();i++){
                    ratings.append(", ").append(array.getJSONObject(i).getString(INTERFACE.keyIBName));
                }
            }
            logger.info(fName+".ratingId="+ratingId+" ,ratings="+ratings);
            if(post.has(INTERFACE.keyIBKeywords)&&!post.isNull(INTERFACE.keyIBKeywords)){
                JSONArray array=post.getJSONArray(INTERFACE.keyIBKeywords);
                for(int i=0;i<array.length();i++){
                    try {
                        String keywordName=array.getJSONObject(i).getString(INTERFACE.keyIBKeywordName);
                        if(keywordName.toLowerCase().contains("cub")||keywordName.toLowerCase().contains("kid")||keywordName.toLowerCase().contains("child")||keywordName.toLowerCase().contains("baby")){
                            logger.warn(fName+"contains badword");isBadKeywords=true;
                            break;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }

            if(post.has(INTERFACE.keyIBFileUrlFull)&&!post.isNull(INTERFACE.keyIBFileUrlFull)){
                image=post.getString(INTERFACE.keyIBFileUrlFull);
            }else
            if(post.has(INTERFACE.keyIBFileUrlScreen)&&!post.isNull(INTERFACE.keyIBFileUrlScreen)){
                image=post.getString(INTERFACE.keyIBFileUrlScreen);
            }else
            if(post.has(INTERFACE.keyIBFileUrlPreview)&&!post.isNull(INTERFACE.keyIBFileUrlPreview)){
                image=post.getString(INTERFACE.keyIBFileUrlPreview);
            }
            logger.info(fName+" image="+ image);
            if(!image.isBlank()&&!image.isEmpty()&&!isBadKeywords){
                logger.info(fName+" success");
                return  image;
            }else {
                logger.warn(fName + ".No image found or bad words");
            }
            return  null;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public String doInkbunnyHtmlCheckup(lcGlobalHelper global) {
        String fName = "[doInkbunnyHtmlCheckup]";
        try {
            HttpResponse<JsonNode> jsonResponse =reqInkbunnyHtmlCheckup(global);
            logger.info(fName+".status ="+jsonResponse.getStatus());
            if(jsonResponse.getStatus()>299){
                logger.error(fName+".invalid status"); return "";
            }
            JsonNode body=jsonResponse.getBody();
            logger.info(fName+".body ="+body);
            JSONObject rdata=body.getObject();
            if(rdata.has("error_code")) {
                logger.info(fName+".error_code ="+rdata.getInt("error_code"));
                if (rdata.getInt("error_code") == 0 || rdata.getInt("error_code") == 1 || rdata.getInt("error_code") == 2) {
                    return doInkbunnyHtmlLogIn(global);
                }
            }
            if(rdata.has("sid")){
                String sid= rdata.getString("sid");
                gSIDUser=sid;
                return sid;
            }
            return  "";
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return doInkbunnyHtmlLogIn(global);
        }
    }
    public String doInkbunnyHtmlLogIn(lcGlobalHelper global) {
        String fName = "[doInkbunnyHtmlLogIn]";
        try {
            HttpResponse<JsonNode> jsonResponse =reqInkbunnyHtmlLogIn();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            if(jsonResponse.getStatus()>299){
                logger.error(fName+".invalid status"); return "";
            }
            JsonNode body=jsonResponse.getBody();
            logger.info(fName+".body ="+body);
            JSONObject rdata=body.getObject();
            if(rdata.has("sid")) {
                String sid=rdata.getString("sid");
                logger.info(fName+"sid="+sid);
                global.jsonObject.put("InkBunny-ImageSearcher_sid",sid);
                gSIDUser=sid;
                return rdata.getString("sid");
            }
            return "";
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public HttpResponse<JsonNode> reqInkbunnyHtmlCheckup(lcGlobalHelper global) {
        String fName = "[reqInkbunnyHtmlCheckup]";
        try {
            Unirest a= new Unirest();
            String gInkBunnySIDUser=gSIDUser;
            if(global.jsonObject.has("InkBunny-ImageSearcher_sid")){
                gInkBunnySIDUser =global.jsonObject.getString("InkBunny-ImageSearcher_sid");
                logger.info(fName+".replace sid with ="+ gInkBunnySIDUser);
            }
            logger.info(fName+".sid ="+ gInkBunnySIDUser);
            HttpResponse<JsonNode> jsonResponse =reqInkbunnyHtmlCheckup(gInkBunnySIDUser);
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqInkbunnyHtmlCheckup(String sid) {
        String fName = "[reqInkbunnyHtmlCheckup]";
        try {
            Unirest a= new Unirest();
            String url="https://inkbunny.net/api_submissions.php?sid=!sid&submission_ids=1962041";
            logger.info(fName+".sid ="+ sid);
            url=url.replaceAll("!sid", sid);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqInkbunnyHtmlLogIn() {
        String fName = "[reqInkbunnyHtmlLogIn]";
        try {
            Unirest a= new Unirest();
            String url=INTERFACE.gUrlLogInUser;
            url=url.replaceAll("!USERNAME", Config.getUsername(global)).replaceAll("!PASSWORD",Config.getPassword(global));
            HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }

    public interface Config{
        public interface Keys {
            String config="InkBunny",
                    sid="sid",
                    username="username",
                    password="password";
        }
        static String getSid(lcGlobalHelper global){
            String fName="[getSid]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.sid;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
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
        static String getUsername(lcGlobalHelper global){
            String fName="[getUsername]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.username;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
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
        static String getPassword(lcGlobalHelper global){
            String fName="[getPassword]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.password;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
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
