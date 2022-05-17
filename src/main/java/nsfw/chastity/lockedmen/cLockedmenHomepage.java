package nsfw.chastity.lockedmen;

import kong.unirest.Cookie;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cLockedmenHomepage {
    Logger logger = Logger.getLogger(getClass());
    public cLockedmenHomepage(){

    }
    List<Cookie>cookies=new ArrayList<>();
    public cLockedmenHomepage setCookies(List<Cookie>cookies) {
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
    public JSONObject getHttp(String url) {
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
            return  jsonObject;
        }
    }
    public JSONObject getSiteStatsJsonResponse() {
        String fName="[getSiteStatsJsonResponse]";
        logger.info(fName);
        try{
            JSONObject jsonObject=getHttp(iLockedmen.URL.getStatus);
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
    public JSONObject getHomepageNewImagesJsonResponse() {
        String fName="[getHomepageNewImagesJsonResponse]";
        logger.info(fName);
        try{
            JSONObject  jsonObject=getHttp(iLockedmen.URL.getHomepageNewImages);
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
    public JSONObject getHomepageRandomImagesJsonResponse() {
        String fName="[getHomepageRandomImagesJsonResponse]";
        logger.info(fName);
        try{
            JSONObject  jsonObject=getHttp(iLockedmen.URL.getHomepageRandomImages);
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
    public JSONObject getHomepageRecentForumsJsonResponse() {
        String fName="[getHomepageRecentForumsJsonResponse]";
        logger.info(fName);
        try{
            JSONObject  jsonObject=getHttp(iLockedmen.URL.getHomepageRecentForumPosts);
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
    public JSONObject getHomepageSearchUserJsonResponse(String nick) {
        String fName="[getHomepageSearchUserJsonResponse]";
        logger.info(fName);
        try{
            if(nick==null)throw  new Exception("nick can't be null");
            if(nick.isBlank())throw  new Exception("nick can't be blank");
            logger.info(fName + ".nick="+nick);
            JSONObject  jsonObject=getHttp(iLockedmen.URL.getUsersWithNick.replaceAll("!NICK",nick));
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
    public SiteStats getSiteStats() {
        String fName="[getSiteStats]";
        logger.info(fName);
        try{
            SiteStats entity=new SiteStats(getSiteStatsJsonResponse());
            logger.info(fName + ".entity=" + entity.result());
            return entity;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<ImagePost> getHomepageNewImages() {
        String fName="[getHomepageNewImages]";
        logger.info(fName);
        try{
            JSONObject jsonObject=getHomepageNewImagesJsonResponse();
            if(jsonObject==null)throw  new Exception("json is null");
            if(jsonObject.isEmpty())throw  new Exception("json is empty");
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
            JSONArray jsonArray=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body);
            List<ImagePost>imagePosts=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                imagePosts.add(new ImagePost(jsonArray.getJSONObject(i)));
            }
            return imagePosts;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<ImagePost> getHomepageRandomImages() {
        String fName="[getHomepageRandomImages]";
        logger.info(fName);
        try{
            JSONObject jsonObject=getHomepageRandomImagesJsonResponse();
            if(jsonObject==null)throw  new Exception("json is null");
            if(jsonObject.isEmpty())throw  new Exception("json is empty");
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
            JSONArray jsonArray=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body);
            List<ImagePost>imagePosts=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                imagePosts.add(new ImagePost(jsonArray.getJSONObject(i)));
            }
            return imagePosts;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<ForumPost> getHomepageRecentForums() {
        String fName="[getHomepageRecentForums]";
        logger.info(fName);
        try{
            JSONObject jsonObject=getHomepageRecentForumsJsonResponse();
            if(jsonObject==null)throw  new Exception("json is null");
            if(jsonObject.isEmpty())throw  new Exception("json is empty");
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
            JSONArray jsonArray=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body);
            List<ForumPost>imagePosts=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                imagePosts.add(new ForumPost(jsonArray.getJSONObject(i)));
            }
            return imagePosts;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<FoundUser> getHomepageSearchUser(String nick) {
        String fName="[getHomepageSearchUser]";
        logger.info(fName);
        try{
            if(nick==null)throw  new Exception("nick can't be null");
            if(nick.isBlank())throw  new Exception("nick can't be blank");
            JSONObject jsonObject=getHomepageSearchUserJsonResponse(nick);
            if(jsonObject==null)throw  new Exception("json is null");
            if(jsonObject.isEmpty())throw  new Exception("json is empty");
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
            JSONArray jsonArray=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body);
            List<FoundUser>imagePosts=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                imagePosts.add(new FoundUser(jsonArray.getJSONObject(i)));
            }
            return imagePosts;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    class SiteStats {
        Logger logger = Logger.getLogger(getClass());
        private JSONObject jsonObject=new JSONObject();
        private boolean result=false;
        public SiteStats(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName);
            try{
                //logger.error(fName );
                if(jsonObject==null)throw  new Exception("json is null");
                if(jsonObject.isEmpty())throw  new Exception("json is empty");
                logger.info(fName + ".jsonObject="+jsonObject.toString());
                if(!jsonObject.getBoolean(iLockedmen.HTTPResponse.result))throw  new Exception("result is false");
                this.jsonObject=jsonObject.getJSONArray(iLockedmen.HTTPResponse.body).getJSONObject(0);
                result=true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public boolean result(){
            return  result;
        }
        public int getProfiles(){
            String fName="[getProfiles]";
            logger.info(fName);
            try{
                return jsonObject.getInt(iLockedmen.HTTPResponse.GETSITESTATS.profiles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getMembers(){
            String fName="[getMembers]";
            logger.info(fName);
            try{
                return jsonObject.getInt(iLockedmen.HTTPResponse.GETSITESTATS.members);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getPhotos(){
            String fName="[getPhotos]";
            logger.info(fName);
            try{
                return jsonObject.getInt(iLockedmen.HTTPResponse.GETSITESTATS.photos);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getForumPosts(){
            String fName="[getForumPosts]";
            logger.info(fName);
            try{
                return jsonObject.getInt(iLockedmen.HTTPResponse.GETSITESTATS.forumposts);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
    class ImagePost{
        Logger logger = Logger.getLogger(getClass());
        private JSONObject jsonObject=new JSONObject();
        private boolean result=false;
        public ImagePost(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName);
            try{
                //logger.error(fName );
                if(jsonObject==null)throw  new Exception("json is null");
                if(jsonObject.isEmpty())throw  new Exception("json is empty");
                logger.info(fName + ".jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                result=true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public boolean result(){
            return  result;
        }
        public String getUserId(){
            String fName="[getUserId]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.l_GUID);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUserNick(){
            String fName="[getUserNick]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.img_lnick);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageId(){
            String fName="[getImageId]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.imgGUID);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageUrl(){
            String fName="[getImageUrl]";
            logger.info(fName);
            try{
                String url=iLockedmen.URL.imageUrl.replaceAll("!USERNICK",getUserNick()).replaceAll("!IMAGEID",getImageId());
                if(getImageFilename().contains("jpg"))url=url.replaceAll("!EXT","jpg");
                else if(getImageFilename().contains("png"))url=url.replaceAll("!EXT","png");
                return url;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageFilename(){
            String fName="[getImageFilename]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.img_filename);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUserRole(){
            String fName="[getUserRole]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.lproles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageCaption(){
            String fName="[getImageCaption]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.imgcaption);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUserLockedStatus(){
            String fName="[getUserLockedStatus]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.lplockedstatus);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getLastCountryCode(){
            String fName="[getLastCountryCode]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.l_lastcountrycode);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageUploadDate(){
            String fName="[getImageUploadDate]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.imgcreated);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageHeight(){
            String fName="[getImageHeight]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.imgheight);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageWidth(){
            String fName="[getImageWidth]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEIMAGE.imgwidth);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public cLockedmenMember getMember(){
            String fName="[getMember]";
            logger.info(fName);
            try{
                cLockedmenMember member=new cLockedmenMember();
                member.setCookies(cookies).getUserProfile(getUserId());
                return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    class ForumPost{
        Logger logger = Logger.getLogger(getClass());
        private JSONObject jsonObject=new JSONObject();
        private boolean result=false;
        public ForumPost(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName);
            try{
                //logger.error(fName );
                if(jsonObject==null)throw  new Exception("json is null");
                if(jsonObject.isEmpty())throw  new Exception("json is empty");
                logger.info(fName + ".jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                result=true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public boolean result(){
            return  result;
        }
        public String getUserNick(){
            String fName="[getUserNick]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.author);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getTopicId(){
            String fName="[getTopicId]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.topic_ID);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getSubject(){
            String fName="[getSubject]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.subject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getLink(){
            String fName="[getLink]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.link);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getMessageDate(){
            String fName="[getMessageDate]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.Message_date);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getForum(){
            String fName="[getForum]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGEFORUM.forum);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public cLockedmenMember getMember(){
            String fName="[getMember]";
            logger.info(fName);
            try{
                cLockedmenMember member=new cLockedmenMember();
                List<cLockedmenHomepage.FoundUser>foundUsers=getHomepageSearchUser(getUserNick());
                if(foundUsers==null)foundUsers=new ArrayList<>();
                logger.info(fName+"foundUsers.size="+foundUsers.size());
                member.setCookies(cookies).getUserProfile(foundUsers.get(0).getId());
                return member;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    class FoundUser{
        Logger logger = Logger.getLogger(getClass());
        private JSONObject jsonObject=new JSONObject();
        private boolean result=false;
        public FoundUser(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName);
            try{
                //logger.error(fName );
                if(jsonObject==null)throw  new Exception("json is null");
                if(jsonObject.isEmpty())throw  new Exception("json is empty");
                logger.info(fName + ".jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                result=true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public boolean result(){
            return  result;
        }
        public String getFlag(){
            String fName="[getflag]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGESEARECHNICKRESULT.flag);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getNick(){
            String fName="[getNick]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGESEARECHNICKRESULT.nick);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId(){
            String fName="[getId]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGESEARECHNICKRESULT.GUID);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getImageCount(){
            String fName="[getImageCount]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGESEARECHNICKRESULT.imgCount);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getIcons(){
            String fName="[getIcons]";
            logger.info(fName);
            try{
                return jsonObject.getString(iLockedmen.HTTPResponse.HOMEPAGESEARECHNICKRESULT.icons);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
}
