package nsfw.lovense;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static nsfw.lovense.iLovense.gUrlLovesenseCommand;


public class entityLovensenseToyLoc {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLovensenseToy]";
    Logger loggerReq = Logger.getLogger(CrunchifyLog4jLevel.LovenseReq_STR);
    /*"de68826ef711": {
			"nickName": "",
			"name": "hush",
			"id": "de68826ef711",
			"status": 1
		}*/
    final String keyTimeStamp=iLovense.keyTimeStamp,keyUpdated=iLovense.keyUpdated,keyRegistered=iLovense.keyRegistered;
    final String keyEnable=iLovense.keyEnable,keyPublic=iLovense.keyPublic;
    final String keyId=iLovense.keyToyId,
            keyNickname=iLovense.keyToyNickName,
            keyName=iLovense.keyToyName;
    String valueId="",
            valueNickname="",
            valueName="";
    long valueRegistered=0,valueUpdated=0;
    boolean valueEnabled=true,valuePublic=true;

    public entityLovensenseToyLoc(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        this.global=global;
    }
    public entityLovensenseToyLoc(lcGlobalHelper global,JSONObject jsonObject){
        String fName="[constructor]";
        try {
            this.global=global;
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    lcGlobalHelper global;
    public boolean clear(){
        String fName="[clear]";
        try {
            valueId="";
            valueNickname="";
            valueName="";
            valueRegistered=0;valueUpdated=0;
            valueEnabled=true;valuePublic=false;
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
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyId:
                    valueId= jsonObject.getString(key);
                    break;
                case keyName:
                    valueName= jsonObject.getString(key);
                    break;
                case keyNickname:
                    valueNickname= jsonObject.getString(key);
                    break;
                case keyUpdated:
                    valueUpdated= jsonObject.getLong(key);
                    break;
                case keyRegistered:
                    valueRegistered= jsonObject.getLong(key);
                    break;
                case keyEnable:
                    valueEnabled= jsonObject.getBoolean(key);
                    break;
                case keyPublic:
                    valuePublic= jsonObject.getBoolean(key);
                    break;
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
            jsonObject.put(keyId,valueId);
            jsonObject.put(keyName,valueName);
            jsonObject.put(keyNickname,valueNickname);
            jsonObject.put(keyUpdated,valueUpdated);
            jsonObject.put(keyRegistered,valueRegistered);
            jsonObject.put(keyEnable,valueEnabled);
            jsonObject.put(keyPublic,valuePublic);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean hasID(){
        String fName="[hasID]";
        try {
            logger.info(fName+"value="+valueId);
            if(valueId==null){
                logger.info(fName+"isnull>false");
                return false;
            }
            if(valueId.isBlank()){
                logger.info(fName+"isblank>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getID(){
        String fName="[getID]";
        try {
            logger.info(fName+"value="+valueId);
            return valueId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean hasName(){
        String fName="[hasName]";
        try {
            logger.info(fName+"value="+valueName);
            if(valueName==null){
                logger.info(fName+"isnull>false");
                return false;
            }
            if(valueName.isBlank()){
                logger.info(fName+"isblank>false");
                return false;
            }
            logger.info(fName+"default>true");
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
            logger.info(fName+"value="+valueName);
            return valueName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean hasNickname(){
        String fName="[hasName]";
        try {
            logger.info(fName+"value="+valueNickname);
            if(valueNickname==null){
                logger.info(fName+"isnull>false");
                return false;
            }
            if(valueNickname.isBlank()){
                logger.info(fName+"isblank>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getNickname(){
        String fName="[getNickname]";
        try {
            logger.info(fName+"value="+valueNickname);
            return valueNickname;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    public long getRegistered(){
        String fName="[getRegistered]";
        try {
            logger.info(fName+"value="+valueRegistered);
            return valueRegistered;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getUpdated(){
        String fName="[getUpdated]";
        try {
            logger.info(fName+"value="+valueUpdated);
            return valueUpdated;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean isEnabled(){
        String fName="[isEnabled]";
        try {
            logger.info(fName+"value="+valueEnabled);
            return valueEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPublic(){
        String fName="[isPublic]";
        try {
            logger.info(fName+"value="+valuePublic);
            return valuePublic;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setID(String input){
        String fName="[setID]";
        try {
            if(input==null){
                logger.info(fName+"input can't be null");
                return false;
            }
            logger.info(fName+"input="+input);
            valueId=input;
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
            if(input==null){
                logger.info(fName+"input can't be null");
                return false;
            }
            logger.info(fName+"input="+input);
            valueName=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNickname(String input){
        String fName="[setNickname]";
        try {
            if(input==null){
                logger.info(fName+"input can't be null");
                return false;
            }
            logger.info(fName+"input="+input);
            valueNickname=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setRegistered(long input){
        String fName="[setRegistered]";
        try {
            logger.info(fName+"input="+input);
            valueRegistered=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setUpdated(long input){
        String fName="[setUpdated]";
        try {
            logger.info(fName+"input="+input);
            valueUpdated=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEnabled(boolean input){
        String fName="[setEnabled]";
        try {
            logger.info(fName+"input="+input);
            valueEnabled=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setPublic(boolean input){
        String fName="[setPublic]";
        try {
            logger.info(fName+"input="+input);
            valuePublic=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static  final  String keyRequest_Token="token",keyRequest_uId="uid";
    static final String keyRequest_Command="command",keyRequest_Action="action",keyRequest_Name="name",keyRequest_TimeSec="timeSec",keyRequest_LoopRunningSec="loopRunningSec",keyRequest_LoopPauseSec="LoopPauseSec",keyRequest_Toy="toy",keyRequest_ApiVer="apiVer";

    public HttpResponse<JsonNode> doStopFunction(String uid,int apiVer){
        String fName="[doStopFunction]";
        try {
            return  doFunction(uid, apiVer, iLovense.Actions4Function.Stop,0,20,0,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (String uid, int apiVer, iLovense.Actions4Function action){
        String fName="[doFunction]";
        try {
            return  doFunction(uid, apiVer,action,0,0,0,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (String uid, int apiVer, iLovense.Actions4Function action, int actionLevel, int timeSec){
        String fName="[doFunction]";
        try {
            return  doFunction(uid, apiVer,action,actionLevel,timeSec,0,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (String uid, int apiVer, iLovense.Actions4Function action, int actionLevel, int timeSec, int loopRunningSec, int LoopPauseSec){
        String fName="[doFunction]";
        try {
            JSONObject jsonObject=new JSONObject();boolean isStop=false;
            /*
            Parameters	Description	Type	Note	Required
            command	Type of request	String	/	yes
            action	Control the function and strength of the toy	string	action can be Vibrate, Rotate, Pump, Stop, if you want to stop running ,please using stop.	yes
            timeSec	Total time	double	The total time should be greater than 1	yes
            loopRunningSec	Running time	double	The running time should be greater than 1	no
            LoopPauseSec	Suspend time	double	The suspend time should be greater than 1	no
            toy	Toy id	string	Its optinal ,if you don’t input this, it will apply to all toys	no
            apiVer	The version of the request	int	Please always it be 1	yes
            */
            jsonObject.put(keyRequest_Command, iLovense.Commands.Function.getName());
            if(action==null)action= iLovense.Actions4Function.Stop;
            if(action== iLovense.Actions4Function.Stop){
                jsonObject.put(keyRequest_Action,action.getName());isStop=true;
            }
            else if(actionLevel>0){
                jsonObject.put(keyRequest_Action,action+":"+actionLevel);
            }else{
                jsonObject.put(keyRequest_Action,action.getName());
            }
            if(timeSec>1){
                jsonObject.put(keyRequest_TimeSec,timeSec);
            }
            logger.info(fName+".isStop ="+isStop);
            if(!isStop){
                if(loopRunningSec>1){
                    jsonObject.put(keyRequest_LoopRunningSec,loopRunningSec);
                }
                if(LoopPauseSec>1){
                    jsonObject.put(keyRequest_LoopPauseSec,LoopPauseSec);
                }
            }else{
                jsonObject.put(keyRequest_TimeSec,iLovense.defaultreqTimeSec);
            }
            jsonObject.put(keyRequest_Toy,getID());
            jsonObject.put(keyRequest_uId,uid);
            jsonObject.put(keyRequest_ApiVer,apiVer);
            jsonObject.put(keyRequest_Token, iLovense.Config.getToken(global));
            return  doHttp(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doFunction (String uid, int apiVer, List<entityLovensenseAction> actions, int timeSec, int loopRunningSec, int LoopPauseSec){
        String fName="[doFunction]";
        try {
            JSONObject jsonObject=new JSONObject();boolean isStop=false;
            /*
            Parameters	Description	Type	Note	Required
            command	Type of request	String	/	yes
            action	Control the function and strength of the toy	string	action can be Vibrate, Rotate, Pump, Stop, if you want to stop running ,please using stop.	yes
            timeSec	Total time	double	The total time should be greater than 1	yes
            loopRunningSec	Running time	double	The running time should be greater than 1	no
            LoopPauseSec	Suspend time	double	The suspend time should be greater than 1	no
            toy	Toy id	string	Its optinal ,if you don’t input this, it will apply to all toys	no
            apiVer	The version of the request	int	Please always it be 1	yes
            */
            jsonObject.put(keyRequest_Command, iLovense.Commands.Function.getName());
            if(actions==null){
                actions=new ArrayList<>();
                entityLovensenseAction action=new entityLovensenseAction(iLovense.Actions4Function.Stop);
                actions.add(action);
            }
            StringBuilder strActions= new StringBuilder();
            for(entityLovensenseAction action:actions){
                if(strActions.length()>0)strActions.append(",");
                strActions.append(action.getAsString());
                if(action.getAction()==iLovense.Actions4Function.Stop){
                    isStop=true;
                    break;
                }
            }
            jsonObject.put(keyRequest_Action,strActions.toString());
            if(timeSec>1){
                jsonObject.put(keyRequest_TimeSec,timeSec);
            }
            logger.info(fName+".isStop ="+isStop);
            if(!isStop){
                if(loopRunningSec>1){
                    jsonObject.put(keyRequest_LoopRunningSec,loopRunningSec);
                }
                if(LoopPauseSec>1){
                    jsonObject.put(keyRequest_LoopPauseSec,LoopPauseSec);
                }
            }else{
                jsonObject.put(keyRequest_TimeSec,iLovense.defaultreqTimeSec);
            }
            jsonObject.put(keyRequest_Toy,getID());
            jsonObject.put(keyRequest_uId,uid);
            jsonObject.put(keyRequest_ApiVer,apiVer);
            jsonObject.put(keyRequest_Token,iLovense.Config.getToken(global));
            return  doHttp(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doPreset (String uid,int apiVer,iLovense.Actions4Preset action){
        String fName="[doPreset]";
        try {
            return  doPreset(uid, apiVer,action,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doPreset (String uid,int apiVer,iLovense.Actions4Preset action,int timeSec){
        String fName="[doPreset]";
        try {
            JSONObject jsonObject=new JSONObject();boolean isStop=false;
           /*
           Parameters	Description	Type	Note	Required
            command	Type of request	String	/	yes
            name	Preset pattern name	string	“pulse, wave, fireworks, earthquake”	yes
            timeSec	Total running time	double	The running time should be greater than 1.	yes
            toy	Toy id	string	Its optinal ,if you don’t input this, it will be apply to all toys	no
            apiVer	The version of the request	int	Please always it be 1	yes
            */
            jsonObject.put(keyRequest_Command,iLovense.Commands.Preset.getName());
            if(action==null)action=iLovense.Actions4Preset.Stop;
            if(action==iLovense.Actions4Preset.Stop){
                isStop=true;
            }
            jsonObject.put(keyRequest_Name,action.getName());
            if(timeSec>1){
                jsonObject.put(keyRequest_TimeSec,timeSec);
            }
            logger.info(fName+".isStop ="+isStop);
            jsonObject.put(keyRequest_Toy,getID());
            jsonObject.put(keyRequest_uId,uid);
            jsonObject.put(keyRequest_ApiVer,apiVer);
            jsonObject.put(keyRequest_Token,iLovense.Config.getToken(global));
            return  doHttp(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> doHttp(JSONObject jsonObject){
        String fName="[doFunction]";
        try {
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            logger.info(fName+".jsonObject ="+jsonObject.toString());
            logger.info(fName + ".ready to send");
            HttpResponse<JsonNode> jsonResponse =a.post(gUrlLovesenseCommand)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+ jsonResponse.getBody());
            try {
                loggerReq.info("POSTING="+jsonObject.toString()+", BODY="+jsonResponse.getBody().getObject().toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }

    public iLovense.ToyType getType(){
        String fName="[getType]";
        try {
            String strType=getName();
            logger.info(fName+"strType="+strType);
            iLovense.ToyType type=iLovense.ToyType.valueByName(strType);
            if(type==null)type=iLovense.ToyType.Invalid;
            logger.info(fName+"type="+type.getCode());
            return type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return iLovense.ToyType.Invalid;
        }
    }
    public String getTypeAsString(){
        String fName="[getTypeAsString]";
        try {
            String strType="";
            iLovense.ToyType type=getType();
            if(type!=null&&type!=iLovense.ToyType.Invalid)strType=type.getName();
            logger.info(fName+"strType="+strType);
            return strType;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isVibratingType(){
        String fName="[isVibratingType]";
        try {
            iLovense.ToyType type=getType();
            boolean result=type.isVibrate();
            logger.info(fName+"result=="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isRotateType(){
        String fName="[isRotateType]";
        try {
            iLovense.ToyType type=getType();
            boolean result=type.isRotate();
            logger.info(fName+"result=="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPumpType(){
        String fName="[isPumpType]";
        try {
            iLovense.ToyType type=getType();
            boolean result=type.isPump();
            logger.info(fName+"result=="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


}
