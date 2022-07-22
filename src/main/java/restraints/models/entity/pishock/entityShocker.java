package restraints.models.entity.pishock;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.*;


public class entityShocker implements iRestraints {
    Logger logger = Logger.getLogger(getClass()); String cName="[cShocker]";
    String code="",
            name="";
     Map<String, entityShockerActions> actionsMap=new HashMap<>();

    public entityShocker(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityShocker(JSONObject jsonObject){
        String fName="[constructor]";
        try {
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                return false;
            }
            jsonObject=new JSONObject(jsonObject.toString());
            if(jsonObject.has(nPishockCode)){
                code=jsonObject.getString(nPishockCode);
            }
            if(jsonObject.has(nPishockName)){
                name=jsonObject.getString(nPishockName);
            }
            JSONObject jsonActions =new JSONObject();
            if(jsonObject.has(nPishockActions)){
                jsonActions =jsonObject.getJSONObject(nPishockActions);
                String key=nPishockAction_main;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_warn;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_punish;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_zap;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_reward;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
            }
            jsonObject=null;
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
            jsonObject.put(nPishockCode,getCode());
            jsonObject.put(nPishockName,getName());
            jsonObject.put(nPishockActions, getAllActionsAsJSON());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getCode(){
        String fName="[getCode]";
        try {
            logger.info(fName+"code="+code);
           return code;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            logger.info(fName+"name="+name);
            return name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getAllActionsAsJSON(){
        String fName="[getActionsAsJSON]";
        try {
            logger.info(fName+"actionsMap.size="+actionsMap.size());
            JSONObject jsonObject=new JSONObject();
            Set<String> setKey=actionsMap.keySet();
            Iterator<String>iteratorKey=setKey.iterator();
            while(iteratorKey.hasNext()){
                String key=iteratorKey.next();
                jsonObject.put(key,getActionKeyAsJSON(key));
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean setCode(String input){
        String fName="[setCode]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return false;
            }
            code=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setName(String input){
        String fName="[setName]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return false;
            }
            name=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public entityShockerActions getActions(String key){
        String fName="[getActions]";
        try {
            if(key==null){
                logger.info(fName+"key cant be null");
                return new entityShockerActions();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new entityShockerActions();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new entityShockerActions();
            }
            entityShockerActions actions=actionsMap.get(key);
            logger.info(fName+"actions="+actions.getJSON());
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerActions();
        }
    }
    public JSONArray getActionKeyAsJSON(String key){
        String fName="[getActionKeyAsJSON]";
        try {
            if(key==null){
                logger.info(fName+"key cant be null");
                return new JSONArray();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new JSONArray();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new JSONArray();
            }
            entityShockerActions actions=actionsMap.get(key);
            JSONArray jsonArray=actions.getJSON();
            logger.info(fName+"jsonArray="+jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public boolean setActionKey(String key, entityShockerActions actions){
        String fName="[setActionKey]";
        try {
            if(key==null){
                logger.info(fName+"key cant be null");
                return false;
            }
            if(actions==null){
                logger.info(fName+"actions cant be null");
                return false;
            }
            logger.info(fName+"key="+key+", actions="+actions.getJSON().toString());
            actionsMap.put(key,actions);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setActionKey(String key,JSONArray array){
        String fName="[setActionKey]";
        try {
            if(key==null){
                logger.info(fName+"key cant be null");
                return false;
            }
            if(array==null){
                logger.info(fName+"array cant be null");
                return false;
            }
            logger.info(fName+"key="+key+", array="+array.toString());
            entityShockerActions actions=new entityShockerActions(array);
            actionsMap.put(key,actions);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setActions(JSONObject jsonObject){
        String fName="[setActions]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject cant be null");
                return false;
            }
            JSONObject jsonActions =new JSONObject();
            if(jsonObject.has(nPishockActions)){
                jsonActions =jsonObject.getJSONObject(nPishockActions);
                String key=nPishockAction_main;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_warn;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_punish;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_zap;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
                key=nPishockAction_reward;
                if(jsonActions.has(key)){
                    entityShockerActions actions=new entityShockerActions(jsonActions.getJSONArray(key));
                    actionsMap.put(key,actions);
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean hasCode(){
        String fName="[hasCode]";
        try {
            if(code==null){
                logger.info(fName+"code is null");
                return false;
            }
            if(code.isBlank()){
                logger.info(fName+"code is blank");
                return false;
            }
            logger.info(fName+"code="+code);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasName(){
        String fName="[hasName]";
        try {
            if(name==null){
                logger.info(fName+"name is null");
                return false;
            }
            if(name.isBlank()){
                logger.info(fName+"name is blank");
                return false;
            }
            logger.info(fName+"name="+name);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasActions(){
        String fName="[hasActions]";
        try {
            if(actionsMap==null){
                logger.info(fName+"actionsMap is null");
                return false;
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return false;
            }
            logger.info(fName+"actionsMap.size="+actionsMap.size());
            if(actionsMap.size()==0){
                logger.info(fName+"actionsMap.szie=0");
                return false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    JSONArray defaultWarn=new JSONArray("[{\"mode\": \"beep\",\n" +
            "\t\t\t\t\t\t\"duration\": 5,\n" +
            "\t\t\t\t\t\t\"itensity\": 0}]");
    JSONArray defaultPunish=new JSONArray("[{\"mode\": \"shock\",\n" +
            "\t\t\t\t\t\t\"duration\": 5,\n" +
            "\t\t\t\t\t\t\"itensity\": 10}]");
    JSONArray defaultReward=new JSONArray("[{\"mode\": \"vibrate\",\n" +
            "\t\t\t\t\t\t\"duration\": 5,\n" +
            "\t\t\t\t\t\t\"itensity\": 50}]");
    JSONArray defaultMain=new JSONArray("[{\"mode\": \"shock\",\n" +
            "\t\t\t\t\t\t\"duration\": 5,\n" +
            "\t\t\t\t\t\t\"itensity\": 10}]");
    public entityShockerActions getActionWarn(boolean useDefaultIfNull){
        String fName="[getActionsWarn]";
        try {
            String key=nPishockAction_warn;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new entityShockerActions();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new entityShockerActions();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new entityShockerActions();
            }
            entityShockerActions actions=actionsMap.get(key);
            logger.info(fName+"actions="+actions.getJSON());
            if(useDefaultIfNull&&actions.isEmpty()){
                actions.set(defaultWarn);
            }
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerActions();
        }
    }
    public JSONArray getActionWarnAsJSON(boolean useDefaultIfNull){
        String fName="[getActionWarnAsJSON]";
        try {
            String key=nPishockAction_warn;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new JSONArray();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new JSONArray();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new JSONArray();
            }
            entityShockerActions actions=actionsMap.get(key);
            JSONArray jsonArray=actions.getJSON();
            logger.info(fName+"jsonArray="+jsonArray.toString());
            if(useDefaultIfNull&&jsonArray.isEmpty()){
               return defaultWarn;
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public entityShockerActions getActionPunish(boolean useDefaultIfNull){
        String fName="[getActionsPunish]";
        try {
            String key=nPishockAction_punish;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new entityShockerActions();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new entityShockerActions();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new entityShockerActions();
            }
            entityShockerActions actions=actionsMap.get(key);
            logger.info(fName+"actions="+actions.getJSON());
            if(useDefaultIfNull&&actions.isEmpty()){
                actions.set(defaultPunish);
            }
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerActions();
        }
    }
    public JSONArray getActionPunishAsJSON(boolean useDefaultIfNull){
        String fName="[getActionPunishAsJSON]";
        try {
            String key=nPishockAction_punish;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new JSONArray();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new JSONArray();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new JSONArray();
            }
            entityShockerActions actions=actionsMap.get(key);
            JSONArray jsonArray=actions.getJSON();
            logger.info(fName+"jsonArray="+jsonArray.toString());
            if(useDefaultIfNull&&jsonArray.isEmpty()){
                return defaultPunish;
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public entityShockerActions getActionReward(boolean useDefaultIfNull){
        String fName="[getActionsReward]";
        try {
            String key=nPishockAction_reward;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new entityShockerActions();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new entityShockerActions();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new entityShockerActions();
            }
            entityShockerActions actions=actionsMap.get(key);
            logger.info(fName+"actions="+actions.getJSON());
            if(useDefaultIfNull&&actions.isEmpty()){
                actions.set(defaultReward);
            }
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerActions();
        }
    }
    public JSONArray getActionRewardAsJSON(boolean useDefaultIfNull){
        String fName="[getActionRewardAsJSON]";
        try {
            String key=nPishockAction_reward;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new JSONArray();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new JSONArray();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new JSONArray();
            }
            entityShockerActions actions=actionsMap.get(key);
            JSONArray jsonArray=actions.getJSON();
            logger.info(fName+"jsonArray="+jsonArray.toString());
            if(useDefaultIfNull&&jsonArray.isEmpty()){
                return defaultReward;
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public entityShockerActions getActionMain(boolean useDefaultIfNull){
        String fName="[getActionsMain]";
        try {
            String key=nPishockAction_main;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new entityShockerActions();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new entityShockerActions();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new entityShockerActions();
            }
            entityShockerActions actions=actionsMap.get(key);
            logger.info(fName+"actions="+actions.getJSON());
            if(useDefaultIfNull&&actions.isEmpty()){
                actions.set(defaultMain);
            }
            return actions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerActions();
        }
    }
    public JSONArray getActionMainAsJSON(boolean useDefaultIfNull){
        String fName="[getActionMainAsJSON]";
        try {
            String key=nPishockAction_main;
            if(key==null){
                logger.info(fName+"key cant be null");
                return new JSONArray();
            }
            if(actionsMap.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return new JSONArray();
            }
            logger.info(fName+"key="+key);
            if(!actionsMap.containsKey(key)){
                return new JSONArray();
            }
            entityShockerActions actions=actionsMap.get(key);
            JSONArray jsonArray=actions.getJSON();
            logger.info(fName+"jsonArray="+jsonArray.toString());
            if(useDefaultIfNull&&jsonArray.isEmpty()){
                return defaultMain;
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
}
