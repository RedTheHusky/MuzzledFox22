package nsfw.diaper.modules.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class entityCustomActions {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaper]";
    /*gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyType,typeDiaper_White);
            gUserProfile.safetyPutFieldEntry(field, keyMaxLevel,6);*/
    protected final String fieldAction= iDiaperInteractive.fieldCustomActions,keyEnabled=iDiaperInteractive.keyEnabled,keyActions=iDiaperInteractive.keyActions;
    protected final String keyName=iDiaperInteractive.keyName,keyText1=iDiaperInteractive.keyText1,keyText2=iDiaperInteractive.keyText2;
    protected  boolean gEnabled=false;
    protected List<ACTION>actions=new ArrayList<>();

    public entityCustomActions(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityCustomActions(JSONObject jsonObject){
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
            gEnabled=true;
            actions=new ArrayList<>();
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
            if(jsonObject.has(fieldAction)){
                logger.info(fName+"has key fieldDiaper");
                jsonObject=jsonObject.getJSONObject(fieldAction);
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
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
            jsonObject.put(keyEnabled,gEnabled);
            JSONArray jsonArray=new JSONArray();
            for(ACTION action:actions){
                jsonArray.put(action.getJSON());
            }
            jsonObject.put(keyActions,jsonArray);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isEnabled(){
        String fName="[isEnabled]";
        try {
            logger.info(fName+"value="+gEnabled);
            return gEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<ACTION> getActions(){
        String fName="[getActions]";
        try {
            logger.info(fName+"value="+actions.toString());
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public int size(){
        String fName="[getActionsSize]";
        try {
            logger.info(fName+"value="+actions.size());
            return actions.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmpty(){
        String fName="[isActionsEmpty]";
        try {
            logger.info(fName+"value="+actions.isEmpty());
            return actions.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public ACTION getAction(int index){
        String fName="[getAction]";
        try {
            logger.info(fName+"index="+index);
            return actions.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCustomActions setEnabled(boolean input){
        String fName="[setEnabled]";
        try {
            logger.info(fName+"input="+input);
            gEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean put(String key,Object value){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyEnabled:
                    gEnabled= (boolean) value;
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyEnabled:
                    gEnabled= jsonObject.getBoolean(key);
                    break;
                case keyActions:
                    JSONArray jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        actions.add(new ACTION(jsonArray.optJSONObject(i)));
                    }
                    break;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public ACTION remAction(int index){
        String fName="[remAction]";
        try {
            logger.info(fName+"index="+index);
            return actions.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCustomActions addAction(ACTION action){
        String fName="[remAction]";
        try {
            logger.info(fName+"action.name="+action.getName());
            actions.add(action);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCustomActions setAction(int index,ACTION action){
        String fName="[remAction]";
        try {
            logger.info(fName+"index="+index);
            actions.set(index,action);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public static class ACTION{
        Logger logger = Logger.getLogger(getClass());
        protected  String name="",text1="",text2="";
        protected  final String keyName=iDiaperInteractive.keyName,keyText1=iDiaperInteractive.keyText1,keyText2=iDiaperInteractive.keyText2;
        public ACTION(){}
        public ACTION(JSONObject jsonObject){set(jsonObject);}
        protected boolean set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(fName+"jsonObject is null");
                    return false;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                String key="";
                key=keyName;if(jsonObject.has(key))name=jsonObject.optString(key);
                key=keyText1;if(jsonObject.has(key))text1=jsonObject.optString(key);
                key=keyText2;if(jsonObject.has(key))text2=jsonObject.optString(key);
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
                jsonObject.put(keyName,name);
                jsonObject.put(keyText1,text1);
                jsonObject.put(keyText2,text2);
                logger.info("jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                logger.info(fName + ".value="+name);
                if(name==null)return "";
                return name;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getText1(){
            String fName="[getText1]";
            try {
                logger.info(fName + ".value="+text1);
                if(text1==null)return "";
                return text1;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getText2(){
            String fName="[getText2]";
            try {
                logger.info(fName + ".value="+text2);
                if(text2==null)return "";
                return text2;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION setName(String input){
            String fName="[setName]";
            try {
                logger.info(fName + ".input="+input);
                if(input==null)input="";
                name=input;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION setText1(String input){
            String fName="[setText1]";
            try {
                logger.info(fName + ".input="+input);
                if(input==null)input="";
                text1=input;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION setText2(String input){
            String fName="[setText2]";
            try {
                logger.info(fName + ".input="+input);
                if(input==null)input="";
                text2=input;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION clearName(){
            String fName="[clearName]";
            try {
                name="";
                logger.info("done");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION clearText1(){
            String fName="[clearText1]";
            try {
                text1="";
                logger.info("done");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION clearText2(){
            String fName="[clearText2]";
            try {
                text2="";
                logger.info("done");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ACTION reset() {
            String fName="[reset]";
            try {
                name="";text1="";text2="";
                logger.info(fName + ".done");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
}
