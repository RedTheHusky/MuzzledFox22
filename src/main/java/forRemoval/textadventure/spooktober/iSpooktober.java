package forRemoval.textadventure.spooktober;

import forRemoval.textadventure.PLAYER;
import kong.unirest.json.JSONArray;
import models.lc.json.profile.lcJSONUserProfile;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iSpooktober {
    String profileName="spooktober2021",table="MemberProfile";
    String gFilesPath="resources/json/spooktober/";
    String gChaptersFilePath=gFilesPath+"chapters.json";
    String gMainFilePath=gFilesPath+"toyMenu.json";
    int intDefaultMinutes=15;

    static lcJSONUserProfile sUserInit(lcJSONUserProfile gUserProfile){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iChastityEmlalock.class);
        try {
            gUserProfile.safetyPutFieldEntry(PLAYER.KEYS.attributes,new JSONArray());
            gUserProfile.safetyPutFieldEntry(PLAYER.KEYS.group,0);
            gUserProfile.safetyPutFieldEntry(PLAYER.KEYS.pageIndex,0);
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    static lcJSONUserProfile sUserReset(lcJSONUserProfile gUserProfile){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iChastityEmlalock.class);
        try {
            gUserProfile.putFieldEntry(PLAYER.KEYS.group,0);
            gUserProfile.putFieldEntry(PLAYER.KEYS.pageIndex,0);
            gUserProfile.putFieldEntry(PLAYER.KEYS.attributes,new JSONArray());
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }


}
