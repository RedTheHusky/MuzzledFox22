package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface rPatient {
    String  table="MemberProfile";
    String keyCharacters="characters";
    int restrictionNormal=5, restrictionBoosted=10;
    static  int intDefaultMinutes=15;
    default lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
        String fName = "[iSafetyUserProfileEntry]";
        gUserProfile.safetyPutFieldEntry(keyCharacters, new JSONArray());
        return gUserProfile;
    }
    String keyName="name", keySpecies ="species",keyAge="age",keySexuality="sexuality",keyGender="gender",keyImg="img",keyDesc="desc";
    String keyHeight="height", keyWeight="weight";
    String keyMedicalRecord="medical",keyTreatment="treatment",keySecurity="security",keyReason4Incarceration="reason4incarceration",keyMinimumStayDuration="minimumstay",keySchedule="schedule",keyIncidents="incidents",keyNotes="notes";
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
        if(!jsonObject.has(keyHeight)){
            jsonObject.put(keyHeight,"");
        }
        if(!jsonObject.has( keyWeight)){
            jsonObject.put( keyWeight,"");
        }
        if(!jsonObject.has(keyDesc)){
            jsonObject.put(keyDesc,"");
        }
        if(!jsonObject.has(keyImg)){
            jsonObject.put(keyImg,"");
        }
        if(!jsonObject.has(keyMedicalRecord)){
            jsonObject.put(keyMedicalRecord,"");
        }
        if(!jsonObject.has(keyTreatment)){
            jsonObject.put(keyTreatment,"");
        }
        if(!jsonObject.has(keyReason4Incarceration)){
            jsonObject.put(keyReason4Incarceration,"");
        }
        if(!jsonObject.has(keyMinimumStayDuration)){
            jsonObject.put(keyMinimumStayDuration,"");
        }
        if(!jsonObject.has(keySchedule)){
            jsonObject.put(keySchedule,"");
        }
        if(!jsonObject.has(keyIncidents)){
            jsonObject.put(keyIncidents,new JSONArray());
        }
        if(!jsonObject.has(keyNotes)){
            jsonObject.put(keyNotes,new JSONArray());
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
    default String iGenerateNumber(User user, int index ) {
        String fName = "[iGenerateNumber]";Logger logger = Logger.getLogger(rPatient.class);
        String strID=user.getId();
        Long longID=user.getIdLong();
        String a=strID.substring(0,2), c=strID.substring(strID.length()-2),b=strID.substring(6,8);
        logger.info(fName+"a="+a+", b="+b+", c="+c+", index="+index);
        return a+b+c+index;
    }
    default List<PATIENT> getCharactersList(lcJSONUserProfile gUserProfile){
        String fName="getCharactersList";
        Logger logger = Logger.getLogger(fName);
        try{
            List<PATIENT>list=new ArrayList<>();
            JSONArray array=new JSONArray();
            if(!gUserProfile.jsonObject.isNull(keyCharacters)){
                array=gUserProfile.jsonObject.getJSONArray(keyCharacters);
            }
            for(int i=0;i<array.length();i++){
                PATIENT character=new PATIENT(array.getJSONObject(i));
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
    default JSONArray listCharacters2Array(List<PATIENT> characters){
        String fName="listCharacters2Array";
        Logger logger = Logger.getLogger(fName);
        try{
            JSONArray array=new JSONArray();
            for(int i=0;i<characters.size();i++){
                PATIENT character=characters.get(i);
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
    String gCommandFileCharacterPath = rGeneral.gFileMainPath+"menuPatient.json";
    String strNoCharacters="No characters",strFailed2LoadProfile="Failed to load profile!", strFailed2SaveProfile="Failed to save profile!",strIndexOutOfBound="Index out of bound. Please try between 0-";
    public  interface KEYS{
        String name="name",species="species",age="age",gender="gender",sexuality="sexuality",desc="desc",
                likes="likes",hates="hates";
        String field="field";
        String field_main="field_main",field_desc="field_desc",field_medical="field_medical",field_schedule="field_schedule",field_incident="field_incident",field_note="field_note";
        String type = "type",field_avatar="field_avatar",field_image="field_image";
        String height="height",weight="weight",reason="reason",minimum="minimum",security="security",medical="medical",
                treatment="treatment",schedule="schedule";
    }
}
