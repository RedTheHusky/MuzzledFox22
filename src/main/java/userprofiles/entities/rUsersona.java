package userprofiles.entities;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;

public interface rUsersona {
    String table="MemberProfile";
    static  int intDefaultMinutes=15;
    default lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
        String fName = "[iSafetyUserProfileEntry]";
        gUserProfile.safetyPutFieldEntry(keyName,"");
        gUserProfile.safetyPutFieldEntry(keyAge,"");
        gUserProfile.safetyPutFieldEntry(keyGender,"");
        gUserProfile.safetyPutFieldEntry(keySexuality,"");
        gUserProfile.safetyPutFieldEntry(keyLikes,"");
        gUserProfile.safetyPutFieldEntry(keyHates,"");
        gUserProfile.safetyPutFieldEntry(keyDesc,"");
        gUserProfile.safetyPutFieldEntry(keyHobbies,"");
        gUserProfile.safetyPutFieldEntry(keyTimeZone,"");
        gUserProfile.safetyPutFieldEntry(keyLinks,"");
        return gUserProfile;
    }
    String keyName="name",keyAge="age",keySexuality="sexuality",keyGender="gender", keyLikes="likes",keyHates="hates",keyDesc="desc",keyHobbies="hobbies",keyTimeZone="timezone",keyLinks="links";
    default JSONObject iSafetyCreateCharacter(JSONObject jsonObject) {
        String fName = "[iSafetyCreateCharacter]";
        
        return jsonObject;
    }

}
