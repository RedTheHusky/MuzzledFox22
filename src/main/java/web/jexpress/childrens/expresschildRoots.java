package web.jexpress.childrens;

import express.DynExpress;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.Presence;
import org.apache.log4j.Logger;
import web.jexpress.models.expStats;
import web.jexpress.models.collectdata.CollectData;
import web.jexpress.models.collectdata.DataSetDasboardCommon;
import web.jexpress.models.collectdata.DataSetDiscord;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.*;

public class expresschildRoots  {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public expresschildRoots(lcGlobalHelper global,Request req, Response res,String command) {
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
                dataSetDasboardCommon.set(collectData);
                dataSetDiscord.set(collectData,gGlobal);
                switch (gCommand){
                    case "getAbout":
                        getAbout();
                        break;
                    case "getStatistics":
                        getStatistics();
                        break;
                    case "getBotUser":
                        getBotUser();
                        break;
                    case "getCompareMembers":
                        getCompareMembers();
                        break;
                    case "getCompareBanned":
                        getCompareBanned();
                        break;
                    case "getInteractions":
                        getInteractions();
                        break;
                    case "postInteractions":
                        postInteractions();
                        break;
                    case "getLovesense":
                        getLovesense();
                        break;
                    case "test":
                        test();
                        break;
                    default:
                        logger.warn("invalid command="+gCommand);
                        getIndex();
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        @DynExpress() // Default is context="/" and method=RequestMethod.GET
        public void getIndex() {
            String fName="getIndex";
            try {
                JSONObject jsonResponse=new JSONObject();
                jsonResponse.put("index1","index2");
                gRes.setHeader("Content-Type", "application/json");

                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        @DynExpress(context = "/about") // Only context is defined, method=RequestMethod.GET is used as method
        public void getAbout() {
            String fName="getAbout";
            try {
                JSONObject jsonResponse=new JSONObject();
                jsonResponse.put("index1","index2");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        @DynExpress(context = "/statics") // Only context is defined, method=RequestMethod.GET is used as method
        public void getStatistics() {
            String fName="getStatistics";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                logger.info(fName+"generate");
                //JSONObject jsonData=new JSONObject();
                jsonResponse.put("guilds_count",gGlobal.getGuildList().size());
                jsonResponse.put("shards_count",gGlobal.getShardManager().getShardsTotal());
                JSONObject jsonUser=new JSONObject();
                jsonUser.put("avatar_url",gGlobal.getJDA(0).getSelfUser().getAvatarUrl());
                SelfUser selfUser=gGlobal.getJDA(0).getSelfUser();
                jsonUser.put("name",selfUser.getName());
                Presence presence=gGlobal.getJDA(0).getPresence();
                OnlineStatus onlineStatus=presence.getStatus();
                jsonUser.put("status",onlineStatus.getKey());
                jsonResponse.put(llCommonKeys.keyUser,jsonUser);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);

                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.setStatus(expStats.STATUS_OK);
                logger.info(fName+"return");
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/bot")
        public void getBotUser() {
            String fName="[getBotUser]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                logger.info(fName+"generate");
                List<JDA> jds=gGlobal.getJDAList();
                String str="";
                SelfUser selfUser=jds.get(0).getSelfUser();
                jsonResponse.put(llCommonKeys.UserStructure.keyId,selfUser.getIdLong());
                jsonResponse.put(llCommonKeys.UserStructure.keyUsername,selfUser.getName());
                jsonResponse.put(llCommonKeys.UserStructure.keyDiscriminator,selfUser.getDiscriminator());
                str=selfUser.getAvatarUrl();if(str==null)str="";
                jsonResponse.put(llCommonKeys.keyAvatarUrl,str);
                OffsetDateTime timeCreated=selfUser.getTimeCreated();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("date",timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonObject.put("time",timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonObject.put(llCommonKeys.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonObject.put(llCommonKeys.keyDayOfYear,timeCreated.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeCreated,jsonObject);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);

                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.setStatus(expStats.STATUS_OK);
                logger.info(fName+"return");
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }

        @DynExpress(context = "/compare/members")
        public void getCompareMembers() {
            String fName="[getCompareMembers]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                String source=collectData.getString("source");
                String target=collectData.getString("target");
                if(source==null||source.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source(guild) is not provided");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                if(target==null||target.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source is not provided");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                if(source.equals(target)){
                    logger.warn(fName + "cant be same huild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source(guild) and target(guild) cant be the same.");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                Guild guildSource=gGlobal.getGuild(source);
                if(guildSource==null){
                    logger.warn(fName + "No such guild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source guild is invalid");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                }
                Guild guildTarget=gGlobal.getGuild(target);
                if(guildTarget==null){
                    logger.warn(fName + "No such guild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Target guild is invalid");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                }

                JSONObject jsonGuildSource=new JSONObject();
                JSONObject jsonGuildTarget=new JSONObject();
                jsonGuildSource.put(llCommonKeys.GuildStructure.keyId,guildSource.getIdLong());
                jsonGuildTarget.put(llCommonKeys.GuildStructure.keyId,guildTarget.getIdLong());
                jsonGuildSource.put(llCommonKeys.GuildStructure.keyName,guildSource.getName());
                jsonGuildTarget.put(llCommonKeys.GuildStructure.keyName,guildTarget.getName());
                jsonResponse.put("source_guild",jsonGuildSource);
                jsonResponse.put("target_guild",jsonGuildTarget);
                List<Member>sourceMembers=guildSource.getMembers();
                List<Member>targetMembers=guildTarget.getMembers();
                //int n1=sourceMembers.size(), n2=targetMembers.size(),n=0;
                int i1=0,i2=0;
                //if(n1<n2){n=n2;}else{n=n1;}
                JSONArray jsonMatch=new JSONArray();
                JSONArray jsonSource=new JSONArray();
                JSONArray jsonTarget=new JSONArray();
                for(int i=0;i<sourceMembers.size();i++){
                    Member member=sourceMembers.get(i);
                    JSONObject jsonMember=new JSONObject();
                    jsonMember.put(llCommonKeys.UserStructure.keyId,member.getIdLong());
                    jsonMember.put(llCommonKeys.UserStructure.keyUsername,member.getUser().getName());
                    jsonMember.put(llCommonKeys.UserStructure.keyDiscriminator,member.getUser().getDiscriminator());
                    String strAvatar=member.getUser().getAvatarUrl();if(strAvatar==null||strAvatar.isBlank())strAvatar="";
                    jsonMember.put(llCommonKeys.keyAvatarUrl,strAvatar);
                    jsonMember.put(llCommonKeys.MemberStructure.keyNick,member.getEffectiveName());
                    jsonMember.put("match",false);
                    jsonSource.put(jsonMember);
                }
                for(int i=0;i<targetMembers.size();i++){
                    Member member=sourceMembers.get(i);
                    JSONObject jsonMember=new JSONObject();
                    jsonMember.put(llCommonKeys.UserStructure.keyId,member.getIdLong());
                    jsonMember.put(llCommonKeys.UserStructure.keyUsername,member.getUser().getName());
                    jsonMember.put(llCommonKeys.UserStructure.keyDiscriminator,member.getUser().getDiscriminator());
                    String strAvatar=member.getUser().getAvatarUrl();if(strAvatar==null||strAvatar.isBlank())strAvatar="";
                    jsonMember.put(llCommonKeys.keyAvatarUrl,strAvatar);
                    jsonMember.put(llCommonKeys.MemberStructure.keyNick,member.getEffectiveName());
                    jsonMember.put("match",false);
                    jsonTarget.put(jsonMember);
                }
                int count_mach=0;
                if(jsonSource.length()>jsonTarget.length()){
                    for(int i=0;i<jsonSource.length();i++){
                        for(int j=0;j<jsonTarget.length();j++){
                            if(jsonSource.getJSONObject(i).getLong(llCommonKeys.UserStructure.keyId)==jsonTarget.getJSONObject(j).getLong(llCommonKeys.UserStructure.keyId)){
                                jsonSource.getJSONObject(i).put("match",true);
                                jsonTarget.getJSONObject(j).put("match",true);
                                count_mach++;
                            }
                        }
                    }
                }else{
                    for(int j=0;j<jsonTarget.length();j++){
                        for(int i=0;i<jsonSource.length();i++){
                            if(jsonSource.getJSONObject(i).getLong(llCommonKeys.UserStructure.keyId)==jsonTarget.getJSONObject(j).getLong(llCommonKeys.UserStructure.keyId)){
                                jsonSource.getJSONObject(i).put("match",true);
                                jsonTarget.getJSONObject(j).put("match",true);
                                count_mach++;
                            }
                        }
                    }
                }
                jsonResponse.put("source",jsonSource);
                jsonResponse.put("target",jsonTarget);
                jsonResponse.put("count_mach",count_mach);
                jsonResponse.put("source_size",jsonSource.length());
                jsonResponse.put("target_size",jsonTarget.length());
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/compare/bans")
        public void getCompareBanned() {
            String fName="[getCompareBanned]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                String source=collectData.getString("source");
                String target=collectData.getString("target");
                if(source==null||source.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source(guild) is not provided");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                if(target==null||target.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source is not provided");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                if(source.equals(target)){
                    logger.warn(fName + "cant be same huild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source(guild) and target(guild) cant be the same.");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                    return;
                }
                Guild guildSource=gGlobal.getGuild(source);
                if(guildSource==null){
                    logger.warn(fName + "No such guild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Source guild is invalid");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                }
                Guild guildTarget=gGlobal.getGuild(target);
                if(guildTarget==null){
                    logger.warn(fName + "No such guild");
                    jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                    jsonStatus.put(expStats.keyLable,expStats.LABEL_INPUTINCOMPLRT);
                    jsonStatus.put(expStats.keyMessage,"Target guild is invalid");
                    jsonResponse.put(expStats.keyStatus,jsonStatus);
                    gRes.setHeader("Content-Type", "application/json");
                    gRes.setHeader("Access-Control-Allow-Origin", "*");
                    gRes.send(jsonResponse.toString());
                }

                JSONObject jsonGuildSource=new JSONObject();
                JSONObject jsonGuildTarget=new JSONObject();
                jsonGuildSource.put(llCommonKeys.GuildStructure.keyId,guildSource.getIdLong());
                jsonGuildTarget.put(llCommonKeys.GuildStructure.keyId,guildTarget.getIdLong());
                jsonGuildSource.put(llCommonKeys.GuildStructure.keyName,guildSource.getName());
                jsonGuildTarget.put(llCommonKeys.GuildStructure.keyName,guildTarget.getName());
                jsonResponse.put("source_guild",jsonGuildSource);
                jsonResponse.put("target_guild",jsonGuildTarget);
                List<Guild.Ban>sourceMembers=guildSource.retrieveBanList().complete();
                List<Guild.Ban>targetMembers=guildTarget.retrieveBanList().complete();
                //int n1=sourceMembers.size(), n2=targetMembers.size(),n=0;
                int i1=0,i2=0;
                //if(n1<n2){n=n2;}else{n=n1;}
                JSONArray jsonMatch=new JSONArray();
                JSONArray jsonSource=new JSONArray();
                JSONArray jsonTarget=new JSONArray();

                for(int i=0;i<sourceMembers.size();i++){
                    Guild.Ban member=sourceMembers.get(i);
                    JSONObject jsonMember=new JSONObject();
                    jsonMember.put(llCommonKeys.UserStructure.keyId,member.getUser().getIdLong());
                    jsonMember.put(llCommonKeys.UserStructure.keyUsername,member.getUser().getName());
                    jsonMember.put(llCommonKeys.UserStructure.keyDiscriminator,member.getUser().getDiscriminator());
                    String strAvatar=member.getUser().getAvatarUrl();if(strAvatar==null||strAvatar.isBlank())strAvatar="";
                    jsonMember.put(llCommonKeys.keyAvatarUrl,strAvatar);
                    jsonMember.put("reason",member.getReason());
                    jsonMember.put("match",false);
                    jsonSource.put(jsonMember);
                }
                for(int i=0;i<targetMembers.size();i++){
                    Guild.Ban member=targetMembers.get(i);
                    JSONObject jsonMember=new JSONObject();
                    jsonMember.put(llCommonKeys.UserStructure.keyId,member.getUser().getIdLong());
                    jsonMember.put(llCommonKeys.UserStructure.keyUsername,member.getUser().getName());
                    jsonMember.put(llCommonKeys.UserStructure.keyDiscriminator,member.getUser().getDiscriminator());
                    String strAvatar=member.getUser().getAvatarUrl();if(strAvatar==null||strAvatar.isBlank())strAvatar="";
                    jsonMember.put(llCommonKeys.keyAvatarUrl,strAvatar);
                    jsonMember.put("reason",member.getReason());
                    jsonMember.put("match",false);
                    jsonTarget.put(jsonMember);
                }
                int count_mach=0;
                if(jsonSource.length()>jsonTarget.length()){
                    for(int i=0;i<jsonSource.length();i++){
                        for(int j=0;j<jsonTarget.length();j++){
                            if(jsonSource.getJSONObject(i).getLong(llCommonKeys.UserStructure.keyId)==jsonTarget.getJSONObject(j).getLong(llCommonKeys.UserStructure.keyId)){
                                jsonSource.getJSONObject(i).put("match",true);
                                jsonTarget.getJSONObject(j).put("match",true);
                                count_mach++;
                            }
                        }
                    }
                }else{
                    for(int j=0;j<jsonTarget.length();j++){
                        for(int i=0;i<jsonSource.length();i++){
                            if(jsonSource.getJSONObject(i).getLong(llCommonKeys.UserStructure.keyId)==jsonTarget.getJSONObject(j).getLong(llCommonKeys.UserStructure.keyId)){
                                jsonSource.getJSONObject(i).put("match",true);
                                jsonTarget.getJSONObject(j).put("match",true);
                                count_mach++;
                            }
                        }
                    }
                }
                jsonResponse.put("source",jsonSource);
                jsonResponse.put("target",jsonTarget);
                jsonResponse.put("count_mach",count_mach);
                jsonResponse.put("source_size",jsonSource.length());
                jsonResponse.put("target_size",jsonTarget.length());
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }


        @DynExpress(context = "/interactions")
        public void getInteractions() {
            String fName="[getInteractions]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/interactions",method = RequestMethod.POST)
        public void postInteractions() {
            String fName="[postInteractions]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }


        @DynExpress(context = "/lovesense")
        public void getLovesense() {
            String fName="[getLovesense]";
            try {
                logger.info(fName+"init");

                logger.info(fName+"checkup");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }

        @DynExpress(context = "/test")
        public void test() {
            String fName="[test]";
            try {
                logger.info(fName+"init");
                logger.info(fName+"checkup");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }

    }

}
