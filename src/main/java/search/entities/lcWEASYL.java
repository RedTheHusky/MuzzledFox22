package search.entities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import models.ls.lsColorHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcWEASYL {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[WEASYL]";
    public lcWEASYL(lcGlobalHelper global) {
        this.global=global;
    }
    lcGlobalHelper global;
    public  interface  INTERFACE{
        String gUrlWeasylSubmission="https://www.weasyl.com/api/submissions/%/view";
        String gUlrWeasylCharacter="https://www.weasyl.com/api/characters/%/view";
        String gUrlWeasylUser="https://www.weasyl.com/~!name";
        //String gWeasylApi= llGlobalHelper.WeasylAuth.apikey;
        String keyWeasylRating="rating",valueWeasylExplicit="explicit",valueWeasylMature="mature",valueWeasylGeneral="general";
        String keyWeasylTitle="title",keyWeasylSubType="subtype",keyWeasylTags="tags",keyWeasylLink="link";
        String keyWeasylMedia="media",keyWeasylSubmission="submission",keyWeasylUrl="url";
        String keyWeasylWOwnerLogin="owner_login",keyWeasylOwner="owner",keyWeasylOwnerMedia="owner_media",keyWeasylAvatar="avatar";
    }
    public HttpResponse<JsonNode> reqSubmission(String viewId) {
        String fName = "[reqSubmission]";
        try {
            logger.info(fName+".viewId ="+viewId);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=INTERFACE.gUrlWeasylSubmission.replaceAll("%",viewId);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse=a.get(url)
                    .header("X-Weasyl-API-Key",Config.getApiKey(global))
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return  jsonResponse;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> reqCharacter(String viewId) {
        String fName = "[reqCharacter]";
        try {
            logger.info(fName+".viewId ="+viewId);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=INTERFACE.gUlrWeasylCharacter.replaceAll("%",viewId);
            logger.info(fName+".url ="+url);
            HttpResponse<JsonNode> jsonResponse=a.get(url)
                    .header("X-Weasyl-API-Key",Config.getApiKey(global))
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject().toString());
            return  jsonResponse;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new FailedResponse<>(e);
        }
    }

    public interface Config{
        interface Keys {
            String config="Weasyl",
                    api_key="api_key";
        }
        static String getApiKey(lcGlobalHelper global){
            String fName="[getApiKey]";
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
                key= Keys.api_key;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
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
