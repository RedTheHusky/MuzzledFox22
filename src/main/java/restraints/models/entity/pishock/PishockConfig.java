package restraints.models.entity.pishock;

import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import restraints.rdPishock;

import java.util.Arrays;

public class PishockConfig {
    Logger logger = Logger.getLogger(getClass());
    private JSONObject jsonObject=new JSONObject();
    public PishockConfig(){

    }
    public PishockConfig(lcGlobalHelper.CONFIGFILE configfile){
        String fName="CONFIG@";
        set(configfile);
    }
    public PishockConfig set(lcGlobalHelper.CONFIGFILE configfile){
        String fName="[set]";
        try {
            if(configfile==null)throw  new Exception("is null!");
            JSONObject jsonObject=configfile.getJsonObject();
            if(jsonObject==null)throw  new Exception("Json is null");
            if(jsonObject.isEmpty())throw  new Exception("Json is empty");
            if(!jsonObject.has("Pishock"))throw  new Exception("Json[Pishock] missing");
            set(jsonObject.getJSONObject("Pishock"));
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace="+ Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public PishockConfig(JSONObject jsonObject){
        String fName="CONFIG@";
        set(jsonObject);
    }
    public PishockConfig set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null)throw  new Exception("Json is null");
            if(jsonObject.isEmpty())throw  new Exception("Json is empty");
            this.jsonObject=jsonObject;
            logger.info(fName + ".json=" +this.jsonObject);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public String getApiUserName(){
        String fName="getApiUserName@";
        try {
            String result=jsonObject.getJSONObject("api").getString("username");
            logger.info(fName + "result=" + result);
            return  result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getApiKey(){
        String fName="getApiKey@";
        try {
            String result=jsonObject.getJSONObject("api").getString("apikey");
            logger.info(fName + "result=" + result);
            return  result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getInviteLink(){
        String fName="getInviteLink@";
        try {
            String result=jsonObject.getJSONObject("links").getString("discord");
            logger.info(fName + "result=" + result);
            return  result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace="+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
