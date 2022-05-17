package models.lc.json;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcJSONSQLExtraEntry {
    Logger logger = Logger.getLogger(getClass());  String cName="[lcJSONSQLExtraEntry]";

    public lcJSONSQLExtraEntry(){
        String fName="[constructor1]";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

}
