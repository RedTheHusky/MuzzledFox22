package test.discordteampicker;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

public class entityTeamPicker {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityHood]";
    public JSONObject extraJsonObject=new JSONObject();

    public entityTeamPicker(){
        String fName="[constructor]";
        logger.info(fName);
    }
}
