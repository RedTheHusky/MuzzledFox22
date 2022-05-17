package nsfw.faprulette;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.ll.llCommonKeys;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iFapRoulette {

    String iReturnedValue4ApiIsInvalid="The returned value from emlalock api is invalid.";
    String profileName="faproulette",guildprofileName="faproulette";
    int intDefaultMinutes=10;
    public interface KEYS {
        String gamesIncluded="games_included";
        String gamesCustom="games_custom";
        public interface Game {
            String name="name";
        }
    }
    static lcJSONUserProfile sUserInit(lcJSONUserProfile gUserProfile){
        String fName="[sUserInit]";
        Logger logger = Logger.getLogger(iFapRoulette.class);
        try {
            String field= llCommonKeys.keyUser;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,llCommonKeys.keyId,"");
            field=KEYS.gamesIncluded;
            gUserProfile.safetyPutFieldEntry(field,new JSONObject());
            field=KEYS.gamesCustom;
            gUserProfile.safetyPutFieldEntry(field,new JSONObject());
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    static lcJSONGuildProfile sGuildInit(lcJSONGuildProfile gGuildProfile){
        String fName="[sGuildInit]";
        Logger logger = Logger.getLogger(iFapRoulette.class);
        try {
            String field=KEYS.gamesIncluded;
            gGuildProfile.safetyPutFieldEntry(field,new JSONObject());
            field=KEYS.gamesCustom;
            gGuildProfile.safetyPutFieldEntry(field,new JSONObject());
            return gGuildProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gGuildProfile;
        }
    }
    String gFilesPath="resources/json/nsfw/faproulette/";
    String gGlobalGamesPath =gFilesPath+"games.json";
    String gAskPath =gFilesPath+"ask.json";
    String gMainMenuPath=gFilesPath+"mainMenu.json";



}
