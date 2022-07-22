package restraints.models.entity.pishock;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.*;


public class entityShockerActions implements iRestraints {
    Logger logger = Logger.getLogger(getClass()); String cName="[cShockerActions]";
    List<entityShockerAction> actionList=new ArrayList<>();
    public entityShockerActions(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityShockerActions(JSONArray array){
        String fName="[constructor]";
        try {
            if(array==null){
                logger.info(fName+"array is null");
                return;
            }
            for(int i=0;i<array.length();i++){
                try {
                    JSONObject jsonObject=array.getJSONObject(i);
                    logger.info(fName+"jsonObject["+i+"]="+jsonObject.toString());
                    entityShockerAction action=new entityShockerAction(jsonObject);
                    actionList.add(action);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean set(JSONArray array){
        String fName="[set]";
        try {
            if(array==null){
                logger.info(fName+"array is null");
                return false;
            }
            for(int i=0;i<array.length();i++){
                try {
                    JSONObject jsonObject=array.getJSONObject(i);
                    logger.info(fName+"jsonObject["+i+"]="+jsonObject.toString());
                    entityShockerAction action=new entityShockerAction(jsonObject);
                    actionList.add(action);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addAction(int op, int duration,int intensity){
        String fName="[addAction]";
        try {
            logger.info(fName+"op="+op+", duration="+duration+", intensity="+intensity);
            entityShockerAction action=new entityShockerAction(op,duration,intensity);
            actionList.add(action);
            logger.info(fName+"added");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addAction(String mode, int duration,int intensity){
        String fName="[addAction]";
        try {
            logger.info(fName+"mode="+mode+", duration="+duration+", intensity="+intensity);
            entityShockerAction action=new entityShockerAction(mode,duration,intensity);
            actionList.add(action);
            logger.info(fName+"added");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addAction(JSONObject jsonObject){
        String fName="[addAction]";
        try {
            logger.info(fName+"jsonObject="+jsonObject.toString());
            entityShockerAction action=new entityShockerAction(jsonObject);
            actionList.add(action);
            logger.info(fName+"added");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addAction(entityShockerAction action){
        String fName="[addAction]";
        try {
            if(action==null){
                logger.info(fName+"action is null");
                return false;
            }
            logger.info(fName+"op="+action.getOp()+", duration="+action.getDuration()+", intensity="+action.getIntensity());
            actionList.add(action);
            logger.info(fName+"added");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean put(entityShockerAction action){
        String fName="[put]";
        try {
            logger.info(fName);
            return addAction(action);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            actionList.clear();
            logger.info(fName+"cleared");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONArray getJSON(){
        String fName="[getJSON]";
        try {
            JSONArray jsonArray=new JSONArray();
            if(actionList==null){
                logger.info(fName+"actionList is null");
                return jsonArray;
            }
            if(actionList.isEmpty()){
                logger.info(fName+"actionList is isEmpty");
                return jsonArray;
            }
            for(entityShockerAction action:actionList){
                try {
                    jsonArray.put(action.getJSON());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONObject getJSONObject(int index){
        String fName="[getJSONObject]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(actionList==null){
                logger.info(fName+"actionList is null");
                return jsonObject;
            }
            if(actionList.isEmpty()){
                logger.info(fName+"actionList is isEmpty");
                return jsonObject;
            }
            logger.info(fName+"index="+index);
            if(index<0){
                logger.info(fName+"invalid index, too small");
                return jsonObject;
            }
            if(index>=actionList.size()){
                logger.info(fName+"invalid index, too big or equal");
                return jsonObject;
            }
            jsonObject=actionList.get(index).getJSON();
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public entityShockerAction getAction(int index){
        String fName="[getAction]";
        try {
            entityShockerAction shockerAction=new entityShockerAction();
            if(actionList==null){
                logger.info(fName+"actionList is null");
                return shockerAction;
            }
            if(actionList.isEmpty()){
                logger.info(fName+"actionList is isEmpty");
                return shockerAction;
            }
            logger.info(fName+"index="+index);
            if(index<0){
                logger.info(fName+"invalid index, too small");
                return shockerAction;
            }
            if(index>=actionList.size()){
                logger.info(fName+"invalid index, too big or equal");
                return shockerAction;
            }
            shockerAction=actionList.get(index);
            return shockerAction;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShockerAction();
        }
    }
    public boolean isEmpty(){
        String fName="[isEmpty]";
        try {
            if(actionList==null){
                logger.info(fName+"actionList is null");
                return true;
            }
            if(actionList.isEmpty()){
                logger.info(fName+"actionList is isEmpty");
                return true;
            }
            logger.info(fName+"actionList not empty");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int length(){
        String fName="[length]";
        try {
            if(actionList==null){
                logger.info(fName+"actionsMap is null");
                return 0;
            }
            if(actionList.isEmpty()){
                logger.info(fName+"actionsMap isEmpty");
                return 0;
            }
            logger.info(fName+"actionList.size="+actionList.size());
            return actionList.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

}
