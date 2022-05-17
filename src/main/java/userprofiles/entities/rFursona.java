package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface rFursona {
    String  table="MemberProfile";
    String keyCharacters="characters";
    int restrictionNormal=5, restrictionBoosted=10;
    String keyName= FURSONA.keyName, keySpecies =FURSONA.keySpecies,keyAge=FURSONA.keyAge,keySexuality=FURSONA.keySexuality,keyGender=FURSONA.keyGender, keyLikes=FURSONA.keyLikes,keyHates=FURSONA.keyHates,keyImg=FURSONA.keyImg,keyDesc=FURSONA.keyDesc;

    static  int intDefaultMinutes=15;
    default lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
        String fName = "[iSafetyUserProfileEntry]";
        gUserProfile.safetyPutFieldEntry(keyCharacters, new JSONArray());
        return gUserProfile;
    }
    default JSONObject iSafetyCreateCharacter(JSONObject jsonObject) {
        String fName = "[iSafetyCreateCharacter]";
        if(!jsonObject.has(keyName)){
            jsonObject.put(keyName,"");
        }
        if(!jsonObject.has(keySpecies)){
            jsonObject.put(keySpecies,"");
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
        if(!jsonObject.has(keyLikes)){
            jsonObject.put(keyLikes,"");
        }
        if(!jsonObject.has(keyHates)){
            jsonObject.put(keyHates,"");
        }
        if(!jsonObject.has(keyDesc)){
            jsonObject.put(keyDesc,"");
        }
        if(!jsonObject.has(keyImg)){
            jsonObject.put(keyImg,"");
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
    default List<FURSONA> getCharactersList(lcJSONUserProfile gUserProfile){
        String fName="getCharactersList";
        Logger logger = Logger.getLogger(fName);
        try{
            List<FURSONA>list=new ArrayList<>();
            JSONArray array=new JSONArray();
            if(!gUserProfile.jsonObject.isNull(keyCharacters)){
                array=gUserProfile.jsonObject.getJSONArray(keyCharacters);
            }
            for(int i=0;i<array.length();i++){
                FURSONA character=new FURSONA(array.getJSONObject(i));
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
    default JSONArray listCharacters2Array(List<FURSONA> characters){
        String fName="listCharacters2Array";
        Logger logger = Logger.getLogger(fName);
        try{
            JSONArray array=new JSONArray();
            for(int i=0;i<characters.size();i++){
                FURSONA character=characters.get(i);
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
    String strNoCharacters="No characters",strFailed2LoadProfile="Failed to load profile!", strFailed2SaveProfile="Failed to save profile!",strIndexOutOfBound="Index out of bound. Please try between 0-";
    String gCommandFileCharacterPath = rGeneral.gFileMainPath+"menuFursona.json";
    public  interface KEYS{
        String name="name",species="species",age="age",gender="gender",sexuality="sexuality",desc="desc",
                likes="likes",hates="hates";
        String field="field";
        String field_all="field_all",field_main="field_main",field_desc="field_desc",field_preference="field_preference";
        String type = "type",field_avatar="field_avatar",field_image="field_image";
    }
}
