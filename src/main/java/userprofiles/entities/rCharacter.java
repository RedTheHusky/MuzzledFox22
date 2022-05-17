package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface rCharacter {
    String  table="MemberProfile";
    String keyCharacters="characters";
    int restrictionNormal=5, restrictionBoosted=10;
    static  int intDefaultMinutes=15;
    default lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
        String fName = "[iSafetyUserProfileEntry]";
        gUserProfile.safetyPutFieldEntry(keyCharacters, new JSONArray());
        return gUserProfile;
    }
    String keyName= CHARACTER.keyName,keySpeciels=CHARACTER.keySpecies,keyAge=CHARACTER.keyAge,keySexuality=CHARACTER.keySexuality,keyGender=CHARACTER.keyGender,keyImg=CHARACTER.keyImg,keyDesc=CHARACTER.keyDesc,keyAvatar=CHARACTER.keyAvatar;
    String keyRPPreferences=CHARACTER.keyRPPreferences, keyFave=CHARACTER.keyFave,keyYes=CHARACTER.keyYes,keyMaybe=CHARACTER.keyMaybe,keyNo=CHARACTER.keyNo;
    default JSONObject iSafetyCreateCharacter(JSONObject jsonObject) {
        String fName = "[iSafetyCreateCharacter]";
        if(!jsonObject.has(keyName)){
            jsonObject.put(keyName,"");
        }
        if(!jsonObject.has(keySpeciels)){
            jsonObject.put(keySpeciels,"");
        }
        if(!jsonObject.has(keyAge)){
            jsonObject.put(keyAge,"");
        }
        if(!jsonObject.has(keyGender)){
            jsonObject.put(keyGender,"");
        }
        if(!jsonObject.has(keySexuality)){
            jsonObject.put(keySexuality,"");
        }
        if(!jsonObject.has(keyDesc)){
            jsonObject.put(keyDesc,"");
        }
        if(!jsonObject.has(keyImg)){
            jsonObject.put(keyImg,"");
        }
        if(!jsonObject.has(keyAvatar)){
            jsonObject.put(keyAvatar,"");
        }
        if(!jsonObject.has(keyRPPreferences)){
            JSONObject object=new JSONObject();
            object.put(keyFave,"");object.put(keyYes,"");object.put(keyMaybe,"");object.put(keyNo,"");
            jsonObject.put(keyRPPreferences,object);
        }

        return jsonObject;
    }
    default JSONArray getCharactersArray(lcJSONUserProfile gUserProfile){
        String fName="getCharactersArray";
        Logger logger = Logger.getLogger(fName);
        try{
            JSONArray array=new JSONArray();
            if(!gUserProfile.jsonObject.isNull(keyCharacters)){
                array=gUserProfile.jsonObject.getJSONArray(keyCharacters);
            }
            return  array;
        }
        catch(Exception e){
            logger.error(fName+".warning=" + e);
            logger.error(fName+ ".warning:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    default List<CHARACTER> getCharactersList(lcJSONUserProfile gUserProfile){
        String fName="getCharactersList";
        Logger logger = Logger.getLogger(fName);
        try{
            List<CHARACTER>list=new ArrayList<>();
            JSONArray array=new JSONArray();
            if(!gUserProfile.jsonObject.isNull(keyCharacters)){
                array=gUserProfile.jsonObject.getJSONArray(keyCharacters);
            }
            logger.info(fName+".array=" + array.toString());
            for(int i=0;i<array.length();i++){
                CHARACTER character=new CHARACTER(array.getJSONObject(i));
                list.add(character);
            }
            return  list;
        }
        catch(Exception e){
            logger.error(fName+".warning=" + e);
            logger.error(fName+ ".warning:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    default JSONArray listCharacters2Array(List<CHARACTER> characters){
        String fName="listCharacters2Array";
        Logger logger = Logger.getLogger(fName);
        try{
            JSONArray array=new JSONArray();
            for(int i=0;i<characters.size();i++){
                CHARACTER character=characters.get(i);
                JSONObject jsonObject=character.getJSON();
                array.put(jsonObject);
            }
            logger.info(fName+".array=" + array.toString());
            return  array;
        }
        catch(Exception e){
            logger.error(fName+".warning=" + e);
            logger.error(fName+ ".warning:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    //This allows you to post with your character name and profile pic.
    String strNoCharacters="No characters",strFailed2LoadProfile="Failed to load profile!", strFailed2SaveProfile="Failed to save profile!",strIndexOutOfBound="Index out of bound. Please try between 0-";
    String gCommandFileCharacterPath = rGeneral.gFileMainPath+"menuCharacter.json";
    public  interface KEYS{
        String name="name",species="species",age="age",gender="gender",sexuality="sexuality",desc="desc",
                fave="fave",yes="yes",maybe="maybe",no="no";
        String field="field";
        String field_all="field_all",field_main="field_main",field_desc="field_desc",field_preference="field_preference";
        String type = "type",field_avatar="field_avatar",field_image="field_image";
    }
}
