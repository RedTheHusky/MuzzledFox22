package userprofiles.entities;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;

public class FURSONA {
    Logger logger = Logger.getLogger(getClass());
    public static final  String keyName="name", keySpecies ="species",keyAge="age",keySexuality="sexuality",keyGender="gender", keyLikes="likes",keyHates="hates",keyImg="img",keyDesc="desc";

    String valueName="",valueSpeciels="",valueAge="",valueSexuality="",valueGender="",valueImg="",valueDesc="",valueAvatar="";
    String valueLikes ="", valueHates ="";
    public FURSONA(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public FURSONA(JSONObject jsonObject){
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
            valueLikes ="";valueHates ="";
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
            jsonObject.put(keyLikes, valueLikes);jsonObject.put(keyHates, valueHates);
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
                case keyLikes:
                    valueLikes = jsonObject.getString(key);
                    break;
                case keyHates:
                    valueHates = jsonObject.getString(key);
                    break;
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
    public String getLikes(){
        String fName="[getLikes]";
        try {
            String value= valueLikes;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getHates(){
        String fName="[getHates]";
        try {
            String value= valueHates;
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
    public boolean setLikes(String input){
        String fName="[setLikes]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueLikes =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setHates(String input){
        String fName="[setHates]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueHates =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
