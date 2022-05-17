package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;

public class PATIENT {
    Logger logger = Logger.getLogger(getClass());
    public static final  String keyName="name", keySpecies ="species",keyAge="age",keySexuality="sexuality",keyGender="gender",keyImg="img",keyDesc="desc";
    public static final String keyHeight="height", keyWeight="weight";
    public static final String keyMedicalRecord="medical",keyTreatment="treatment",keySecurity="security",keyReason4Incarceration="reason4incarceration",keyMinimumStayDuration="minimumstay",keySchedule="schedule",keyIncidents="incidents",keyNotes="notes";

    String valueName="",valueSpeciels="",valueAge="",valueSexuality="",valueGender="",valueImg="",valueDesc="",valueAvatar="";
    String valueHeight="", valueWeight="";
    String valueMedicalRecord="",valueTreatment="",valueSecurity="",valueReason4Incarceration="",valueMinimumStayDuration="",valueSchedule="";
    JSONArray valueIncidents=new JSONArray(),valueNotes=new JSONArray();

    public PATIENT(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public PATIENT(JSONObject jsonObject){
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
            valueHeight=""; valueWeight="";
            valueMedicalRecord="";valueTreatment="";valueSecurity="";valueReason4Incarceration="";valueMinimumStayDuration="";valueSchedule="";
            valueIncidents=new JSONArray();valueNotes=new JSONArray();
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
            jsonObject.put(keyHeight,valueHeight);
            jsonObject.put(keyWeight,valueWeight);
            jsonObject.put(keyMedicalRecord,valueMedicalRecord);
            jsonObject.put(keyTreatment,valueTreatment);
            jsonObject.put(keySecurity,valueSecurity);
            jsonObject.put(keyReason4Incarceration,valueReason4Incarceration);
            jsonObject.put(keyMinimumStayDuration,valueMinimumStayDuration);
            jsonObject.put(keySchedule,valueSchedule);
            jsonObject.put(keyIncidents,valueIncidents);
            jsonObject.put(keyNotes,valueNotes);
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
                case keyHeight:
                    valueHeight= jsonObject.getString(key);
                    break;
                case keyWeight:
                    valueWeight= jsonObject.getString(key);
                    break;
                case keyMedicalRecord:
                    valueMedicalRecord= jsonObject.getString(key);
                    break;
                case keySecurity:
                    valueSecurity= jsonObject.getString(key);
                    break;
                case keyTreatment:
                    valueTreatment= jsonObject.getString(key);
                    break;
                case keyReason4Incarceration:
                    valueReason4Incarceration= jsonObject.getString(key);
                    break;
                case keyMinimumStayDuration:
                    valueMinimumStayDuration= jsonObject.getString(key);
                    break;
                case keySchedule:
                    valueSchedule= jsonObject.getString(key);
                    break;
                case keyIncidents:
                    valueIncidents= jsonObject.getJSONArray(key);
                    break;
                case keyNotes:
                    valueNotes= jsonObject.getJSONArray(key);
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
    public String getHeight(){
        String fName="[getHeight]";
        try {
            String value= valueHeight;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getWeight(){
        String fName="[getWeight]";
        try {
            String value= valueWeight;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMedicalRecord(){
        String fName="[getMedicalRecord]";
        try {
            String value= valueMedicalRecord;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getSecurity(){
        String fName="[getSecurity]";
        try {
            String value= valueSecurity;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getTreatment(){
        String fName="[getTreatment]";
        try {
            String value= valueTreatment;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getReason4Incarceration(){
        String fName="[getReason4Incarceration]";
        try {
            String value= valueReason4Incarceration;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMinimumStayDuration(){
        String fName="[getMinimumStayDuration]";
        try {
            String value= valueMinimumStayDuration;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getSchedule(){
        String fName="[getSchedule]";
        try {
            String value= valueSchedule;
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public void clearIncidents(){
        String fName="[clearIncidents]";
        try {
            valueIncidents=new JSONArray();
            logger.info(fName+"cleared");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void clearNotes(){
        String fName="[clearNotes]";
        try {
            valueNotes=new JSONArray();
            logger.info(fName+"cleared");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public JSONArray getIncidents(){
        String fName="[getIncidents]";
        try {
            JSONArray value= valueIncidents;
            logger.info(fName+"value="+value.toString());
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getNotes(){
        String fName="[getNotes]";
        try {
            JSONArray value= valueNotes;
            logger.info(fName+"value="+value.toString());
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public String getIncidents(int i){
        String fName="[getIncidents]";
        try {
            JSONArray value= valueIncidents;
            logger.info(fName+"arrary="+value.toString());
            logger.info(fName+"i="+i);
            return value.getString(i);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getNotes(int i){
        String fName="[getNotes]";
        try {
            JSONArray value= valueNotes;
            logger.info(fName+"array="+value.toString());
            logger.info(fName+"i="+i);
            return value.getString(i);
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
    public boolean setHeight(String input){
        String fName="[setHeight]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueHeight =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setWeight(String input){
        String fName="[setWeight]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueWeight =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMedicalRecord(String input){
        String fName="[setMedicalRecord]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueMedicalRecord =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTreatment(String input){
        String fName="[setTreatment]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueTreatment =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setSecurity(String input){
        String fName="[setSecurity]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueSecurity =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setReason4Incarceration(String input){
        String fName="[setReason4Incarceration]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueReason4Incarceration =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMinimumStayDurationn(String input){
        String fName="[setMinimumStayDuration]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueMinimumStayDuration =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setSchedule(String input){
        String fName="[setSchedule]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueSchedule =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setIncidents(JSONArray input){
        String fName="[setIncidents]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input.toString());
            valueIncidents =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNotes(JSONArray input){
        String fName="[setNotes]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input.toString());
            valueNotes =input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setIncidents(int index,String input){
        String fName="[setIncidents]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Index="+index+", Input="+input);
            valueIncidents.put(index,input);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNotes(int index,String input){
        String fName="[setNotes]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Index="+index+", Input="+input);
            valueNotes.put(index,input);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remIncidents(int index){
        String fName="[remIncidents]";
        try {
            logger.info("Index="+index);
            valueIncidents.remove(index);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remNotes(int index){
        String fName="[remNotes]";
        try {
            logger.info("Index="+index);
            valueNotes.remove(index);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addIncidents(String input){
        String fName="[addIncidents]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueIncidents.put(input);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addNotes(String input){
        String fName="[addNotes]";
        try {
            if(input==null){
                logger.warn("Input is null");
                return false;
            }
            logger.info("Input="+input);
            valueNotes.put(input);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
