package web.jexpress.childrens;

import express.DynExpress;
import express.http.request.Request;
import express.http.response.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import nsfw.lovense.Lovense;
import nsfw.lovense.entityLovensense;
import nsfw.lovense.iLovense;
import org.apache.log4j.Logger;
import web.jexpress.models.expStats;
import web.jexpress.models.collectdata.CollectData;
import web.jexpress.models.collectdata.DataSetDasboardCommon;
import web.jexpress.models.collectdata.DataSetDiscord;

import java.sql.Timestamp;
import java.util.Arrays;

import static nsfw.lovense.iLovense.keyLovense;

public class expresschildLovense  {
    Logger logger = Logger.getLogger(getClass());Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.GAGUSED_STR);
    lcGlobalHelper gGlobal;
    public expresschildLovense(lcGlobalHelper global, Request req, Response res, String command) {
        String fName="build";
        try {
            gGlobal=global;
            Runnable r = new runLocal(req,res,command);
            new Thread(r).start();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        String gCommand="";Request gReq; Response gRes;
        CollectData collectData=new CollectData();
        DataSetDasboardCommon dataSetDasboardCommon=new DataSetDasboardCommon();
        DataSetDiscord dataSetDiscord =new DataSetDiscord();
        public runLocal(Request req, Response res,String command) {
            String fName="runLocal";
            try {
                logger.info(".run build");
                gCommand=command;
                logger.info(".gCommand="+gCommand);
                gReq=req;gRes=res;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        @Override
        public void run() {
            String fName="run";
            try {
                collectData.set(gReq);
                switch (gCommand){
                    case "verify":
                        dataSetDasboardCommon.set(collectData);
                        dataSetDiscord.set(collectData,gGlobal);
                        getVerify();
                        break;
                    case "callback":
                        callback();
                        break;
                    default:
                        logger.warn("invalid command="+gCommand);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }



        String keyVersion="version",valueVersion="0.0.2";

        @DynExpress(context = "/lovesense/verify")
        public void getVerify() {
            String fName="getVerify";
            try {
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonResponse.put(keyVersion,valueVersion);
                if(dataSetDiscord.gGuild!=null){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        jsonGuild.put(llCommonKeys.keyId, dataSetDiscord.gGuild.getIdLong());
                        jsonGuild.put(llCommonKeys.keyName, dataSetDiscord.gGuild.getName());
                        jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gUser!=null){
                    try {
                        JSONObject jsonUser=new JSONObject();
                        jsonUser.put(llCommonKeys.keyId, dataSetDiscord.gUser.getIdLong());
                        jsonUser.put(llCommonKeys.keyName, dataSetDiscord.gUser.getName());
                        jsonUser.put(llCommonKeys.keyDiscriminator, dataSetDiscord.gUser.getDiscriminator());
                        jsonResponse.put(llCommonKeys.keyUser,jsonUser);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gMember!=null){
                    try {
                        JSONObject jsonMember=new JSONObject();
                        jsonMember.put(llCommonKeys.keyId, dataSetDiscord.gMember.getIdLong());
                        jsonMember.put(llCommonKeys.keyEffectiveName, dataSetDiscord.gMember.getEffectiveName());
                        jsonMember.put(llCommonKeys.keyRoles, dataSetDiscord.gMember.getRoles().toString());
                        jsonResponse.put(llCommonKeys.keyMember,jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gTextChannel!=null){
                    try {
                        JSONObject jsonTextChannel=new JSONObject();
                        jsonTextChannel.put(llCommonKeys.keyId, dataSetDiscord.gTextChannel.getIdLong());
                        jsonTextChannel.put(llCommonKeys.keyName, dataSetDiscord.gTextChannel.getName());
                        jsonResponse.put(llCommonKeys.keyTextChannel,jsonTextChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gPrivateChannel!=null){
                    try {
                        JSONObject jsonPrivateChannel=new JSONObject();
                        jsonPrivateChannel.put(llCommonKeys.keyId, dataSetDiscord.gPrivateChannel.getIdLong());
                        jsonPrivateChannel.put(llCommonKeys.keyName, dataSetDiscord.gPrivateChannel.getName());
                        jsonResponse.put(llCommonKeys.keyPrivateChannel,jsonPrivateChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/lovesense/callback")
        public void callback() {
            String fName="callback";
            try {
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(!collectData.inputJSONObject.has(iLovense.keyutoken)){
                    jsonStatus.put(expStats.keyCode,expStats.CODE_BADREQUEST);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_MISSINGVALUE);
                    jsonStatus.put(expStats.keyMessage,expStats.INFO_BADREQUEST);
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes=expStats.defaultHeaders(gRes);
                    gRes.send(jsonResponse.toString());
                    return;
                }
                String utoken=collectData.inputJSONObject.getString(iLovense.keyutoken);
                logger.info(fName + ".utoken="+utoken);
                String items[] = utoken.split(",");
                dataSetDiscord.gGuild=gGlobal.getGuild(items[1]);
                dataSetDiscord.gMember= dataSetDiscord.gGuild.getMemberById(items[0]);
                if(!getProfile(dataSetDiscord.gMember)){logger.error(fName + ".can't get profile");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_BADREQUEST);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_MISSINGVALUE);
                    jsonStatus.put(expStats.keyMessage,expStats.INFO_BADREQUEST);
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes=expStats.defaultHeaders(gRes);
                    gRes.send(jsonResponse.toString());
                    return;
                }
                gLovense.setNet(collectData.inputJSONObject);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                gLovense.setNetLastGotTimestamp(timestamp.getTime());
                saveProfile();
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());

                try{
                    logger.info(fName+"check if qrcode submit");
                    if(!gGlobal.jsonObject.has(keyLovense)){
                       logger.info("json missing:"+keyLovense+"");
                    }
                    if(!gGlobal.jsonObject.getJSONObject(keyLovense).has(llCommonKeys.keyMembers)){
                        logger.info("json missing:"+keyLovense+"/"+llCommonKeys.keyMembers);
                        return;
                    }
                    if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).has(dataSetDiscord.gMember.getId())){
                        logger.info("json missing:"+keyLovense+"/"+llCommonKeys.keyMembers+"/"+ dataSetDiscord.gMember.getId());
                        return;
                    }
                    if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(dataSetDiscord.gMember.getId()).has(llCommonKeys.keyGuilds)){
                        logger.info("json missing:"+keyLovense+"/"+llCommonKeys.keyMembers+"/"+ dataSetDiscord.gMember.getId()+"/"+llCommonKeys.keyGuilds);
                        return;
                    }
                    if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(dataSetDiscord.gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).has(dataSetDiscord.gGuild.getId())){
                        logger.info("json missing:"+keyLovense+"/"+llCommonKeys.keyMembers+"/"+ dataSetDiscord.gMember.getId()+"/"+llCommonKeys.keyGuilds+"/"+ dataSetDiscord.gGuild.getId());
                        return;
                    }
                    if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(dataSetDiscord.gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(dataSetDiscord.gGuild.getId()).has(llCommonKeys.keyMessages)){
                        logger.info("json missing:"+keyLovense+"/"+llCommonKeys.keyMembers+"/"+ dataSetDiscord.gMember.getId()+"/"+llCommonKeys.keyGuilds+"/"+ dataSetDiscord.gGuild.getId()+"/"+llCommonKeys.keyMessages);
                        return;
                    }
                    JSONArray jsonArray=gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(dataSetDiscord.gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(dataSetDiscord.gGuild.getId()).getJSONArray(llCommonKeys.keyMessages);
                    logger.info(fName+"jsonarray="+jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        if(jsonObject.optInt("status",0)==1){
                            String textChannel_id=jsonObject.optString(llCommonKeys.keyTextChannel,"");
                            TextChannel textChannel= dataSetDiscord.gGuild.getTextChannelById(textChannel_id);
                            new Lovense(gGlobal,gRes, dataSetDiscord.gMember,textChannel,"main");
                            break;
                        }
                    }
                }
                catch (Exception ex){
                    logger.error(fName+"exception="+ex);
                    logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));

                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        lcJSONUserProfile gUserProfile;
        nsfw.lovense.entityLovensense gLovense=new entityLovensense();
        private Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.getMember().getIdLong()==member.getIdLong()){
                logger.info(fName + ".already present>skip");
                logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
                return true;
            }
            gUserProfile=gGlobal.getUserProfile(iLovense.profileName,member);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,iLovense.profileName);
                if(gUserProfile.getProfile(iLovense.table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserProfile= iLovense.sUserInit(gUserProfile);
            gGlobal.putUserProfile(gUserProfile,iLovense.profileName);
            logger.info(fName+"json="+gUserProfile.jsonObject.toString());
            gLovense.setLoc(gUserProfile.jsonObject.getJSONObject("loc"));
            return true;
        }

        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gUserProfile.jsonObject =gLovense.getJSON();
            logger.info(fName+"json="+gUserProfile.jsonObject.toString());
            gGlobal.putUserProfile(gUserProfile,iLovense.profileName);
            if(gUserProfile.saveProfile(iLovense.table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }

    }



}
