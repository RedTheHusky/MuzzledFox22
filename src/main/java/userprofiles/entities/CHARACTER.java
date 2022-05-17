package userprofiles.entities;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;

public class CHARACTER {
    Logger logger = Logger.getLogger(getClass());
    public static final  String keyName="name", keySpecies ="species",keyAge="age",keySexuality="sexuality",keyGender="gender",keyImg="img",keyDesc="desc",keyAvatar="avatar";
    public static  final String keyRPPreferences="rpPreferences", keyFave="fave",keyYes="yes",keyMaybe="maybe",keyNo="no";
    String valueName="",valueSpeciels="",valueAge="",valueSexuality="",valueGender="",valueImg="",valueDesc="",valueAvatar="";
    String valueRPPreferences="", valueFave="",valueYes="",valueMaybe="",valueNo="";
    public CHARACTER(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public CHARACTER(JSONObject jsonObject){
        String fName="[constructor]";
        try {
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            valueName="";valueSpeciels="";valueAge="";valueSexuality="";valueGender="";valueImg="";valueDesc="";valueAvatar="";
            valueRPPreferences=""; valueFave="";valueYes="";valueMaybe="";valueNo="";
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            if(!jsonObject.isEmpty()){
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    put(key,jsonObject);
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyName,valueName);
            jsonObject.put(keySpecies,valueSpeciels);
            jsonObject.put(keyAge,valueAge);
            jsonObject.put(keyGender,valueGender);
            jsonObject.put(keySexuality,valueSexuality);
            jsonObject.put(keyDesc,valueDesc);
            jsonObject.put(keyImg,valueImg);
            jsonObject.put(keyAvatar,valueAvatar);
            JSONObject object=new JSONObject();
            object.put(keyFave,valueFave);object.put(keyYes,valueYes);object.put(keyMaybe,valueMaybe);object.put(keyNo,valueNo);
            jsonObject.put(keyRPPreferences,object);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getJSONSameLine(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyName,valueName);
            jsonObject.put(keySpecies,valueSpeciels);
            jsonObject.put(keyAge,valueAge);
            jsonObject.put(keyGender,valueGender);
            jsonObject.put(keySexuality,valueSexuality);
            jsonObject.put(keyDesc,valueDesc);
            jsonObject.put(keyImg,valueImg);
            jsonObject.put(keyAvatar,valueAvatar);
            jsonObject.put(keyFave,valueFave);jsonObject.put(keyYes,valueYes);jsonObject.put(keyMaybe,valueMaybe);jsonObject.put(keyNo,valueNo);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyName:
                    valueName= jsonObject.getString(key);
                    break;
                case keySpecies:
                    valueSpeciels= jsonObject.getString(key);
                    break;
                case keyAge:
                    valueAge= jsonObject.getString(key);
                    break;
                case keyGender:
                    valueGender= jsonObject.getString(key);
                    break;
                case keySexuality:
                    valueSexuality= jsonObject.getString(key);
                    break;
                case keyDesc:
                    valueDesc= jsonObject.getString(key);
                    break;
                case keyImg:
                    valueImg= jsonObject.getString(key);
                    break;
                case keyAvatar:
                    valueAvatar= jsonObject.getString(key);
                    break;
                case keyRPPreferences:
                    try {
                        JSONObject jsonObject2= jsonObject.getJSONObject(key);
                        if(jsonObject2==null){
                            logger.info(fName+"jsonObject2 is null");
                            break;
                        }
                        if(!jsonObject2.isEmpty()){
                            Iterator<String> keys2=jsonObject2.keys();
                            while (keys2.hasNext()){
                                String key2=keys2.next();
                                switch (key2){
                                    case keyFave:
                                        valueFave= jsonObject2.getString(key2);
                                        break;
                                    case keyYes:
                                        valueYes= jsonObject2.getString(key2);
                                        break;
                                    case keyMaybe:
                                        valueMaybe= jsonObject2.getString(key2);
                                        break;
                                    case keyNo:
                                        valueNo= jsonObject2.getString(key2);
                                        break;
                                }
                            }
                        }
                        break;
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        break;
                    }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            String value=valueName;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAge(){
        String fName="[getAge]";
        try {
            String value=valueAge;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getSpecies(){
        String fName="[getSpecies]";
        try {
            String value=valueSpeciels;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getSexuality(){
        String fName="[getSexuality]";
        try {
            String value=valueSexuality;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getGender(){
        String fName="[getGender]";
        try {
            String value=valueGender;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getImg(){
        String fName="[getImg]";
        try {
            String value=valueImg;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatar(){
        String fName="[getAvatar]";
        try {
            String value=valueAvatar;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getDesc(){
        String fName="[getDesc]";
        try {
            String value=valueDesc;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getFave(){
        String fName="[getFave]";
        try {
            String value=valueFave;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getYes(){
        String fName="[getYes]";
        try {
            String value=valueYes;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMaybe(){
        String fName="[getMaybe]";
        try {
            String value=valueMaybe;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getNo(){
        String fName="[getNo]";
        try {
            String value=valueNo;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean setName(String input){
        String fName="[setName]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueName=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setAge(String input){
        String fName="[setAge]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueAge=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setSpecies(String input){
        String fName="[setSpecies]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueSpeciels=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setSexuality(String input){
        String fName="[setSexuality]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueSexuality=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGender(String input){
        String fName="[setGender]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueGender=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setImg(String input){
        String fName="[setImg]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueImg=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setAvatar(String input){
        String fName="[setAvatar]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueAvatar=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDesc(String input){
        String fName="[setDesc]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueDesc=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setFave(String input){
        String fName="[setFave]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueFave=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setYes(String input){
        String fName="[setYes]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueYes=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMaybe(String input){
        String fName="[setMaybe]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueMaybe=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNo(String input){
        String fName="[setNo]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueNo=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
