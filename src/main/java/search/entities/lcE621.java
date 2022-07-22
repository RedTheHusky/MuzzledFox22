package search.entities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.log4j.Logger;
import search.e621;

import java.util.Arrays;

public class lcE621 {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[E621]";
    public lcE621() {
    }
    public interface  INTERFACE{
        String gUrl="https://e621.net";
        String gUrlGetPost ="https://e621.net/posts/%.json",icon="https://e621.net/favicon.ico";
        String keyFile ="file", keyUrl ="url",keyExt="ext",keyRating="rating",keyTags="tags",keyGeneral="general";
    }
    public JSONObject getImageJSON(String viewId) {
        Logger logger = Logger.getLogger(e621.class);
        String fName="[getImageJSON]";
        logger.info(fName);
        try {
            logger.info(fName+"viewId="+viewId);
            HttpResponse<JsonNode> response =reqGetSubmission(viewId);
            int status=response.getStatus();
            if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                return null;}
            JsonNode body=response.getBody();
            logger.debug(fName+".body ="+body.toPrettyString());
            JSONObject json=body.getObject();
            if(json.isEmpty()||!json.has("post")||json.isNull("post")){
                logger.error(fName+".no post");return null;
            }
            return json;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public HttpResponse<JsonNode> reqGetSubmission(String viewId) {
        Logger logger = Logger.getLogger(e621.class);
        String fName="[reqGetSubmission]";
        logger.info(fName);
        try {
            logger.info(fName+"viewId="+viewId);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=INTERFACE.gUrlGetPost.replaceAll("%",viewId);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqSearchGallery(JSONObject jsonOptions) {
        String fName = "[reqSearchGallery]";
        try {
            logger.info(fName+"jsonOptions="+jsonOptions);
            if(!jsonOptions.has("done")){
                logger.error(fName+".options error");
                if(jsonOptions.has("error")){
                    logger.error(fName+".options error="+jsonOptions.getString("error"));
                    return new FailedResponse<>(new Exception("options error="+jsonOptions.getString("error")));
                }
            }
            String option="";
            if(jsonOptions.has("option")){
                option=jsonOptions.getString("option");
            }
            return reqSearchGalleryByOption(option);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqSearchGalleryByOption(String option) {
        String fName = "[reqSearchGalleryByOption]";
        try {
            logger.info(fName+"option="+option);
            if(option==null||option.isEmpty()){
                logger.error(fName+".no options added"); return new FailedResponse<>(new Exception("no options added"));
            }

            logger.info(fName+"option="+option);
            String url=INTERFACE.gUrl+"/posts.json"+option;
            logger.info(fName + ".url="+url);
            return reqSearchGalleryByUrl(url);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    private HttpResponse<JsonNode> reqSearchGalleryByUrl(String url) {
        //https://e621.net/posts.json?tags=fluffy+rating:s&limit=2
        String fName = "[reqSearchGalleryByUrl]";
        try {
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            logger.info(fName + ".url="+url);
            HttpResponse<JsonNode> jsonResponse=a.get(url)
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return  jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
}
