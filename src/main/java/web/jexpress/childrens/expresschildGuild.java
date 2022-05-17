package web.jexpress.childrens;

import express.DynExpress;
import express.http.request.Request;
import express.http.response.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ls.lsChannelHelper;
import models.ls.lsEmote;
import models.ls.lsMemberHelper;
import models.ls.lsRoleHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import web.jexpress.models.expStats;
import web.jexpress.models.collectdata.CollectData;
import web.jexpress.models.collectdata.DataSetDasboardCommon;
import web.jexpress.models.collectdata.DataSetDiscord;


import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.*;

public class expresschildGuild  {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public expresschildGuild(lcGlobalHelper global, Request req, Response res, String command) {
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
                    case "getGuild":
                        getGuild();
                        break;
                    case "getMember":
                        getMember();
                        break;
                    case "getRole":
                        getRole();
                        break;
                    case "getChannel":
                        getChannel();
                        break;
                    case "getEmote":
                        getEmote();
                        break;
                    case "getRoles":
                        getRoles();
                        break;
                    case "getEmotes":
                        getEmotes();
                        break;
                    case "getChannels":
                        getChannels();
                        break;
                    case "getTextChannels":
                        getTextChannels();
                        break;
                    case "getVoiceChannels":
                        getVoiceChannels();
                        break;
                    case "getCategoryChannels":
                        getCategoryChannels();
                        break;
                    case "getMembers":
                        getMembers();
                        break;
                    case "getBans":
                        getBans();
                        break;
                    default:
                        logger.warn("invalid command="+gCommand);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        @DynExpress(context = "/guild")
        public void getGuild() {
            String fName="[getGuild]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                String str="";
                jsonResponse.put(llCommonKeys.GuildStructure.keyId,guild.getId());
                jsonResponse.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyIconUrl,guild.getIconUrl());
                jsonResponse.put(llCommonKeys.keyTextChannelsCount,guild.getTextChannels().size());
                jsonResponse.put(llCommonKeys.keyVoiceChannelsCount,guild.getVoiceChannels().size());
                jsonResponse.put(llCommonKeys.keyRolesCount,guild.getRoles().size());
                jsonResponse.put(llCommonKeys.keyMembersCount,guild.getMemberCount());
                jsonResponse.put(llCommonKeys.keyMaxMembers,guild.getMaxMembers());
                jsonResponse.put(llCommonKeys.keyEmotesCount,guild.getEmotes().size());

                try {
                    VoiceChannel afkChannel=guild.getAfkChannel();
                    Guild.Timeout afkTimout=guild.getAfkTimeout();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    Guild.BoostTier boostTier=guild.getBoostTier();
                    if(guild.getBannerUrl()!=null)jsonResponse.put(llCommonKeys.keyBannerUrl,guild.getBannerUrl());
                    if(guild.getSplashUrl()!=null)jsonResponse.put(llCommonKeys.keySplashUrl,guild.getSplashUrl());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    Guild.ExplicitContentLevel explicitContentLevel=guild.getExplicitContentLevel();
                    jsonResponse.put(llCommonKeys.GuildStructure.keyExplicitContentFilter,explicitContentLevel.name());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    Set<String>features=guild.getFeatures();
                    Iterator<String> featuresIterator=features.iterator();
                    JSONArray jsonFeatures=new JSONArray();
                    while(featuresIterator.hasNext()){
                        jsonFeatures.put(featuresIterator.next());
                    }
                    jsonResponse.put(llCommonKeys.GuildStructure.keyFeatures,jsonFeatures);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }




                OffsetDateTime timeCreated=guild.getTimeCreated();
                JSONObject jsonTimeCreated =new JSONObject();
                jsonTimeCreated.put("date",timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonTimeCreated.put("time",timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);

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
        @DynExpress(context = "/guild/member")
        public void getMember() {
            String fName="[getMember]";
            try {
                logger.info(fName+"init");
               
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                if(dataSetDiscord.inputMemberId ==null|| dataSetDiscord.inputMemberId.isBlank()){
                    logger.warn(fName + "Has no member id");
                    expStats.respond_NoMemberId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                Member member= lsMemberHelper.lsGetMemberById(guild, dataSetDiscord.inputMemberId);
                if(member==null){
                    logger.warn(fName + "No such member");
                    expStats.respond_InvalidMember(gRes);
                    return;
                }
                String str="";
                User user=member.getUser();
                jsonResponse.put(llCommonKeys.UserStructure.keyId,member.getIdLong());
                JSONObject jsonUser=new JSONObject();
                jsonUser.put(llCommonKeys.keyName,user.getName());
                jsonUser.put(llCommonKeys.UserStructure.keyDiscriminator,user.getDiscriminator());
                str=user.getAvatarUrl();if(str==null)str="";
                jsonUser.put(llCommonKeys.keyAvatarUrl,str);
                OffsetDateTime timeCreated=user.getTimeCreated();
                JSONObject jsonTimeCreated =new JSONObject();
                jsonTimeCreated.put("date",timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonTimeCreated.put("time",timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                jsonUser.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                jsonResponse.put(llCommonKeys.keyUser,jsonUser);

                OffsetDateTime timeJoined=member.getTimeJoined();
                JSONObject jsonTimeJoined =new JSONObject();
                jsonTimeJoined.put("date",timeJoined.getYear()+"."+timeJoined.getMonthValue()+"."+timeJoined.getDayOfMonth());
                jsonTimeJoined.put("time",timeJoined.getHour()+"."+timeJoined.getMinute()+"."+timeJoined.getSecond());
                //jsonTimeJoined.put(expStats.keyDayOfWeek,timeJoined.getDayOfWeek().getValue());
                //jsonTimeJoined.put(expStats.keyDayOfYear,timeJoined.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeJoined,jsonTimeJoined);

                if(dataSetDiscord.inputFlagIncludeRoles){
                    try {
                        List<Role>roles=member.getRoles();
                        JSONArray jsonRoles =new JSONArray();
                        for(int i=0;i<roles.size();i++){
                            try {
                                Role role=roles.get(i);
                                JSONObject jsonRole=new JSONObject();
                                jsonRole.put(llCommonKeys.RoleStructure.keyId,role.getId());
                                jsonRole.put(llCommonKeys.RoleStructure.keyName,role.getName());
                                //jsonRole.put(llCommonKeys.keyColorRaw,role.getColorRaw());
                                //Color color=role.getColor();
                                //jsonRole.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                        /*jsonRole.put(llCommonKeys.RoleStructure.keyHoist,role.isHoisted());
                        jsonRole.put(llCommonKeys.RoleStructure.Misc.keyPublic,role.isPublicRole());
                        jsonRole.put(llCommonKeys.RoleStructure.keyMentionable,role.isMentionable());
                        jsonRole.put(expStats.keyIManaged,role.isManaged());*/
                                jsonRoles.put(jsonRole);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonResponse.put(llCommonKeys.keyRoles,jsonRoles);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.inputFlagIncludePermissions){
                    try {
                        JSONArray jsonPermissions=new JSONArray();
                        EnumSet<Permission> permissionEnum=member.getPermissions();
                        Iterator<Permission>permissionIterator=permissionEnum.iterator();
                        while(permissionIterator.hasNext()){
                            Permission permission=permissionIterator.next();
                            jsonPermissions.put(permission.getName());
                        }
                        jsonResponse.put(llCommonKeys.keyPermissions,jsonPermissions);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                /*try {
                    jsonResponse.put(llCommonKeys.keyColorRaw,member.getColorRaw());
                    Color color=member.getColor();
                    if(color!=null)jsonResponse.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }*/
                try {
                    str=member.getNickname();if(str==null)str="";
                    jsonResponse.put(llCommonKeys.keyNickname,str);
                    jsonResponse.put(llCommonKeys.keyIsOwner,member.isOwner());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.GuildStructure.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
                gRes=expStats.defaultHeaders(gRes);
                logger.info(fName+"Content-Type="+gRes.getHeader("Content-Type").toString());
                logger.info(fName+"Access-Control-Allow-Origin="+gRes.getHeader("Access-Control-Allow-Origin").toString());
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/guild/role")
        public void getRole() {
            String fName="[getRole]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                if(dataSetDiscord.inputRoleId ==null|| dataSetDiscord.inputRoleId.isBlank()){
                    logger.warn(fName + "Has no role id");
                    expStats.respond_NoRoleId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                Role role= lsRoleHelper.lsGetRoleByID(guild, dataSetDiscord.inputRoleId);
                if( role==null){
                    logger.warn(fName + "No such role");
                    expStats.respond_InvalidRole(gRes);
                    return;
                }


                jsonResponse.put(llCommonKeys.keyId,role.getIdLong());
                jsonResponse.put(llCommonKeys.RoleStructure.keyName,role.getName());
                try {
                    jsonResponse.put(llCommonKeys.RoleStructure.keyColor,role.getColorRaw());
                    Color color=role.getColor();
                    //if(color!=null)jsonResponse.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                jsonResponse.put(llCommonKeys.RoleStructure.keyHoist,role.isHoisted());
                jsonResponse.put(llCommonKeys.keyPublicRole,role.isPublicRole());
                jsonResponse.put(llCommonKeys.RoleStructure.keyMentionable,role.isMentionable());
                jsonResponse.put(llCommonKeys.RoleStructure.keyManaged,role.isManaged());
                OffsetDateTime timeCreated=role.getTimeCreated();
                JSONObject jsonTimeCreated =new JSONObject();
                jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                if(dataSetDiscord.inputFlagIncludePermissions){
                    try {
                        JSONArray jsonPermissions=new JSONArray();
                        EnumSet<Permission> permissionEnum=role.getPermissions();
                        Iterator<Permission>permissionIterator=permissionEnum.iterator();
                        while(permissionIterator.hasNext()){
                            Permission permission=permissionIterator.next();
                            jsonPermissions.put(permission.getName());
                        }
                        jsonResponse.put(llCommonKeys.keyPermissions,jsonPermissions);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/channel")
        public void getChannel() {
            String fName="[getChannel]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                if(dataSetDiscord.inputChannelId ==null|| dataSetDiscord.inputChannelId.isBlank()){
                    logger.warn(fName + "Has no channel id");
                    expStats.respond_NoChannelId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                GuildChannel guildChannel= lsChannelHelper.lsGetGuildChannelById(guild, dataSetDiscord.inputChannelId);
                if( guildChannel==null){
                    logger.warn(fName + "No such channel");
                    expStats.respond_InvalidChannel(gRes);
                    return;
                }

                jsonResponse.put(llCommonKeys.keyId,guildChannel.getIdLong());
                jsonResponse.put(llCommonKeys.GuildStructure.keyName,guildChannel.getName());
                OffsetDateTime timeCreated=guildChannel.getTimeCreated();
                JSONObject jsonTimeCreated =new JSONObject();
                jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                ChannelType type=guildChannel.getType();
                jsonResponse.put(llCommonKeys.ChannelStructure.keyType,type.name());
                jsonResponse.put(llCommonKeys.ChannelStructure.keyPosition,guildChannel.getPosition());
                jsonResponse.put(llCommonKeys.keyPositionRaw,guildChannel.getPositionRaw());
                jsonResponse.put(llCommonKeys.keyIsSynced,guildChannel.isSynced());
                try {
                    if(type!=ChannelType.CATEGORY&&type!=ChannelType.PRIVATE&&type!=ChannelType.GROUP&&type!=ChannelType.UNKNOWN){
                        Category category=guildChannel.getParent();
                        JSONObject jsonCategory=new JSONObject();
                        jsonCategory.put(llCommonKeys.keyId,category.getIdLong());
                        jsonCategory.put(llCommonKeys.ChannelStructure.keyName,category.getName());
                        jsonResponse.put(llCommonKeys.keyParent,jsonCategory);
                    }
                    if(type==ChannelType.TEXT){
                        TextChannel textChannel=lsChannelHelper.lsGetTextChannelById(guild,guildChannel.getIdLong());
                        jsonResponse.put(llCommonKeys.ChannelStructure.keyNsfw,textChannel.isNSFW());
                        jsonResponse.put(llCommonKeys.keyIsNews,textChannel.isNews());
                        jsonResponse.put(llCommonKeys.keySlowmode,textChannel.getSlowmode());
                        jsonResponse.put(llCommonKeys.ChannelStructure.keyTopic,textChannel.getTopic());
                    }
                    else if(type==ChannelType.VOICE){
                        VoiceChannel voiceChannel=lsChannelHelper.lsGetVoiceChannelById(guild,guildChannel.getIdLong());
                        jsonResponse.put(llCommonKeys.ChannelStructure.keyBitrate,voiceChannel.getBitrate());
                        jsonResponse.put(llCommonKeys.ChannelStructure.keyUserLimit,voiceChannel.getUserLimit());
                    }
                    else if(type==ChannelType.CATEGORY){
                        Category category=lsChannelHelper.lsGetCategoryById(guild,guildChannel.getIdLong());
                        List<GuildChannel>channels=category.getChannels();
                        JSONArray jsonChannels=new JSONArray();
                        for(int i=0;i<channels.size();i++){
                            try {
                                GuildChannel channel=channels.get(i);
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                jsonChannels.put(jsonObject);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonResponse.put(llCommonKeys.keyChannels,jsonChannels);
                        List<TextChannel>textChannels=category.getTextChannels();
                        JSONArray jsonTextChannels=new JSONArray();
                        for(int i=0;i<textChannels.size();i++){
                            try {
                                TextChannel channel=textChannels.get(i);
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                jsonTextChannels.put(jsonObject);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonResponse.put(llCommonKeys.keyTextChannels,jsonTextChannels);
                        List<VoiceChannel>voiceChannels=category.getVoiceChannels();
                        JSONArray jsonVoiceChannels=new JSONArray();
                        for(int i=0;i<voiceChannels.size();i++){
                            try {
                                VoiceChannel channel=voiceChannels.get(i);
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                jsonVoiceChannels.put(jsonObject);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonResponse.put(llCommonKeys.keyVoiceChannels,jsonVoiceChannels);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);


                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
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
        @DynExpress(context = "/guild/emote")
        public void getEmote() {
            String fName="[getEmote]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                if(dataSetDiscord.inputEmoteId ==null|| dataSetDiscord.inputEmoteId.isBlank()){
                    logger.warn(fName + "Has no emote id");
                    expStats.respond_NoEmoteId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                Emote emote= lsEmote.lsGetEmoteById(guild, dataSetDiscord.inputEmoteId);
                if( emote==null){
                    logger.warn(fName + "No such emote");
                    expStats.respond_InvalidEmote(gRes);
                    return;
                }

                jsonResponse.put(llCommonKeys.keyId,emote.getIdLong());
                jsonResponse.put(llCommonKeys.EmojiStructure.keyName,emote.getName());
                jsonResponse.put(llCommonKeys.EmojiStructure.keyManaged,emote.isManaged());
                OffsetDateTime timeCreated=emote.getTimeCreated();
                JSONObject jsonTimeCreated =new JSONObject();
                jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                jsonResponse.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                jsonResponse.put(llCommonKeys.EmojiStructure.Misc.keyImageUrl,emote.getImageUrl());
                jsonResponse.put(llCommonKeys.EmojiStructure.keyAnimated,emote.isAnimated());
                jsonResponse.put(llCommonKeys.EmojiStructure.keyAvailable,emote.isAvailable());

                if(dataSetDiscord.inputFlagIncludeRoles){
                    try {
                        JSONArray jsonRoles=new JSONArray();
                        List<Role>roles=emote.getRoles();
                        for(int i=0;i<roles.size();i++){
                            Role role=roles.get(i);
                            JSONObject jsonRole=new JSONObject();
                            jsonResponse.put(llCommonKeys.keyId,role.getIdLong());
                            jsonResponse.put(llCommonKeys.RoleStructure.keyName,role.getName());
                            jsonRoles.put(jsonRole);
                        }
                        jsonResponse.put(llCommonKeys.keyRoles,jsonRoles);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
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
        @DynExpress(context = "/guild/roles")
        public void getRoles() {
            String fName="[getRoles]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                List<Role>roles=guild.getRoles();
                JSONArray jsonRoles=new JSONArray();
                for(int i=0;i<roles.size();i++){
                    try {
                        JSONObject jsonRole=new JSONObject();
                        Role role=roles.get(i);
                        jsonRole.put(llCommonKeys.keyId,role.getIdLong());
                        jsonRole.put(llCommonKeys.RoleStructure.keyName,role.getName());
                        try {
                            jsonRole.put(llCommonKeys.keyColorRaw,role.getColorRaw());
                            Color color=role.getColor();
                            if(color!=null)jsonRole.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonRole.put(llCommonKeys.RoleStructure.keyHoist,role.isHoisted());
                        jsonRole.put(llCommonKeys.RoleStructure.Misc.keyPublic,role.isPublicRole());
                        jsonRole.put(llCommonKeys.RoleStructure.keyMentionable,role.isMentionable());
                        jsonRole.put(llCommonKeys.RoleStructure.keyManaged,role.isManaged());
                        OffsetDateTime timeCreated=role.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonRole.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        if(dataSetDiscord.inputFlagIncludePermissions){
                            try {
                                JSONArray jsonPermissions=new JSONArray();
                                EnumSet<Permission> permissionEnum=role.getPermissions();
                                Iterator<Permission>permissionIterator=permissionEnum.iterator();
                                while(permissionIterator.hasNext()){
                                    Permission permission=permissionIterator.next();
                                    jsonPermissions.put(permission.getName());
                                }
                                jsonRole.put(llCommonKeys.keyPermissions,jsonPermissions);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonRoles.put(jsonRole);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonStatus.put(llCommonKeys.keyRoles,jsonRoles);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"create response");
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
        @DynExpress(context = "/guild/emotes")
        public void getEmotes() {
            String fName="[getEmotes]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }

                List<Emote>emotes=guild.getEmotes();
                JSONArray jsonEmotes=new JSONArray();
                for(int i=0;i<emotes.size();i++){
                    try {
                        JSONObject jsonEmote=new JSONObject();
                        Emote emote=emotes.get(i);
                        jsonEmote.put(llCommonKeys.keyId,emote.getIdLong());
                        jsonEmote.put(llCommonKeys.EmojiStructure.keyName,emote.getName());
                        jsonEmote.put(llCommonKeys.EmojiStructure.keyManaged,emote.isManaged());
                        OffsetDateTime timeCreated=emote.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonEmote.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        jsonEmote.put(llCommonKeys.EmojiStructure.Misc.keyImageUrl,emote.getImageUrl());
                        jsonEmote.put(llCommonKeys.EmojiStructure.keyAnimated,emote.isAnimated());
                        jsonEmote.put(llCommonKeys.EmojiStructure.keyAvailable,emote.isAvailable());
                        if(dataSetDiscord.inputFlagIncludeRoles){
                            try {
                                JSONArray jsonRoles=new JSONArray();
                                List<Role>roles=emote.getRoles();
                                for(int j=0;j<roles.size();j++){
                                    Role role=roles.get(j);
                                    JSONObject jsonRole=new JSONObject();
                                    jsonRole.put(llCommonKeys.keyId,role.getIdLong());
                                    jsonRole.put(llCommonKeys.RoleStructure.keyName,role.getName());
                                    jsonRoles.put(jsonRole);
                                }
                                jsonEmote.put(llCommonKeys.keyRoles,jsonRoles);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        jsonEmotes.put(jsonEmote);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                jsonResponse.put(llCommonKeys.keyEmotes,jsonEmotes);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/channels")
        public void getChannels() {
            String fName="[getChannels]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                List<GuildChannel>guildChannels=guild.getChannels();
                JSONArray jsonGuildChannels=new JSONArray();
                for(int i2=0;i2<guildChannels.size();i2++){
                    try {
                        JSONObject jsonGuildChannel=new JSONObject();
                        GuildChannel guildChannel=guildChannels.get(i2);
                        jsonGuildChannel.put(llCommonKeys.keyId,guildChannel.getIdLong());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyName,guildChannel.getName());
                        OffsetDateTime timeCreated=guildChannel.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonGuildChannel.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        ChannelType type=guildChannel.getType();
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyType,type.name());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyPosition,guildChannel.getPosition());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyPositionRaw,guildChannel.getPositionRaw());
                        jsonGuildChannel.put(llCommonKeys.keyIsSynced,guildChannel.isSynced());
                        try {
                            if(type!=ChannelType.CATEGORY&&type!=ChannelType.PRIVATE&&type!=ChannelType.GROUP&&type!=ChannelType.UNKNOWN){
                                Category category=guildChannel.getParent();
                                JSONObject jsonCategory=new JSONObject();
                                jsonCategory.put(llCommonKeys.keyId,category.getIdLong());
                                jsonCategory.put(llCommonKeys.ChannelStructure.keyName,category.getName());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyParent,jsonCategory);
                            }
                            if(type==ChannelType.TEXT){
                                TextChannel textChannel=lsChannelHelper.lsGetTextChannelById(guild,guildChannel.getIdLong());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyNsfw,textChannel.isNSFW());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyIsNews,textChannel.isNews());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keySlowmode,textChannel.getSlowmode());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyTopic,textChannel.getTopic());
                            }
                            else if(type==ChannelType.VOICE){
                                VoiceChannel voiceChannel=lsChannelHelper.lsGetVoiceChannelById(guild,guildChannel.getIdLong());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyBitrate,voiceChannel.getBitrate());
                                jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyUserLimit,voiceChannel.getUserLimit());
                            }
                            else if(type==ChannelType.CATEGORY){
                                Category category=lsChannelHelper.lsGetCategoryById(guild,guildChannel.getIdLong());
                                List<GuildChannel>channels=category.getChannels();
                                JSONArray jsonChannels=new JSONArray();
                                for(int i=0;i<channels.size();i++){
                                    try {
                                        GuildChannel channel=channels.get(i);
                                        JSONObject jsonObject=new JSONObject();
                                        jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                        jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                        jsonChannels.put(jsonObject);
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                }
                                jsonGuildChannel.put(llCommonKeys.keyChannels,jsonChannels);
                                List<TextChannel>textChannels=category.getTextChannels();
                                JSONArray jsonTextChannels=new JSONArray();
                                for(int i=0;i<textChannels.size();i++){
                                    try {
                                        TextChannel channel=textChannels.get(i);
                                        JSONObject jsonObject=new JSONObject();
                                        jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                        jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                        jsonTextChannels.put(jsonObject);
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                }
                                jsonGuildChannel.put(llCommonKeys.keyTextChannels,jsonTextChannels);
                                List<VoiceChannel>voiceChannels=category.getVoiceChannels();
                                JSONArray jsonVoiceChannels=new JSONArray();
                                for(int i=0;i<voiceChannels.size();i++){
                                    try {
                                        VoiceChannel channel=voiceChannels.get(i);
                                        JSONObject jsonObject=new JSONObject();
                                        jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                        jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                        jsonVoiceChannels.put(jsonObject);
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                }
                                jsonGuildChannel.put(llCommonKeys.keyVoiceChannels,jsonVoiceChannels);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonGuildChannels.put(jsonGuildChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonResponse.put(llCommonKeys.keyChannels,jsonGuildChannels);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/textchannels")
        public void getTextChannels() {
            String fName="[getTextChannels]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                List<TextChannel>guildChannels=guild.getTextChannels();
                JSONArray jsonGuildChannels=new JSONArray();
                for(int i2=0;i2<guildChannels.size();i2++){
                    try {
                        JSONObject jsonGuildChannel=new JSONObject();
                        TextChannel guildChannel=guildChannels.get(i2);
                        jsonGuildChannel.put(llCommonKeys.keyId,guildChannel.getIdLong());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyName,guildChannel.getName());
                        OffsetDateTime timeCreated=guildChannel.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonGuildChannel.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        ChannelType type=guildChannel.getType();
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyType,type.name());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyPosition,guildChannel.getPosition());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyPositionRaw,guildChannel.getPositionRaw());
                        jsonGuildChannel.put(llCommonKeys.keyIsSynced,guildChannel.isSynced());
                        try {
                            Category category=guildChannel.getParent();
                            JSONObject jsonCategory=new JSONObject();
                            jsonCategory.put(llCommonKeys.keyId,category.getIdLong());
                            jsonCategory.put(llCommonKeys.ChannelStructure.keyName,category.getName());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyParent,jsonCategory);
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyNsfw,guildChannel.isNSFW());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyIsNews,guildChannel.isNews());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keySlowmode,guildChannel.getSlowmode());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyTopic,guildChannel.getTopic());
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonGuildChannels.put(jsonGuildChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonResponse.put(llCommonKeys.keyTextChannels,jsonGuildChannels);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/voicechannels")
        public void getVoiceChannels() {
            String fName="[getVoiceChannels]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                List<VoiceChannel>guildChannels=guild.getVoiceChannels();
                JSONArray jsonGuildChannels=new JSONArray();
                for(int i2=0;i2<guildChannels.size();i2++){
                    try {
                        JSONObject jsonGuildChannel=new JSONObject();
                        VoiceChannel guildChannel=guildChannels.get(i2);
                        jsonGuildChannel.put(llCommonKeys.keyId,guildChannel.getIdLong());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyName,guildChannel.getName());
                        OffsetDateTime timeCreated=guildChannel.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonGuildChannel.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        ChannelType type=guildChannel.getType();
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyType,type.name());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyPosition,guildChannel.getPosition());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyPositionRaw,guildChannel.getPositionRaw());
                        jsonGuildChannel.put(llCommonKeys.keyIsSynced,guildChannel.isSynced());
                        try {
                            Category category=guildChannel.getParent();
                            JSONObject jsonCategory=new JSONObject();
                            jsonCategory.put(llCommonKeys.keyId,category.getIdLong());
                            jsonCategory.put(llCommonKeys.ChannelStructure.keyName,category.getName());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyParent,jsonCategory);
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyBitrate,guildChannel.getBitrate());
                            jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyUserLimit,guildChannel.getUserLimit());
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonGuildChannels.put(jsonGuildChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonResponse.put(llCommonKeys.keyVoiceChannels,jsonGuildChannels);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/categories")
        public void getCategoryChannels() {
            String fName="[getCategoryChannels]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }
                List<Category>guildChannels=guild.getCategories();
                JSONArray jsonGuildChannels=new JSONArray();
                for(int i2=0;i2<guildChannels.size();i2++){
                    try {
                        JSONObject jsonGuildChannel=new JSONObject();
                        Category guildChannel=guildChannels.get(i2);
                        jsonGuildChannel.put(llCommonKeys.keyId,guildChannel.getIdLong());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyName,guildChannel.getName());
                        OffsetDateTime timeCreated=guildChannel.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonGuildChannel.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        ChannelType type=guildChannel.getType();
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyType,type.name());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.keyPosition,guildChannel.getPosition());
                        jsonGuildChannel.put(llCommonKeys.ChannelStructure.Misc.keyPositionRaw,guildChannel.getPositionRaw());
                        jsonGuildChannel.put(llCommonKeys.keyIsSynced,guildChannel.isSynced());
                        try {
                            List<GuildChannel>channels=guildChannel.getChannels();
                            JSONArray jsonChannels=new JSONArray();
                            for(int i=0;i<channels.size();i++){
                                try {
                                    GuildChannel channel=channels.get(i);
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                    jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                    jsonChannels.put(jsonObject);
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                            jsonGuildChannel.put(llCommonKeys.keyChannels,jsonChannels);
                            List<TextChannel>textChannels=guildChannel.getTextChannels();
                            JSONArray jsonTextChannels=new JSONArray();
                            for(int i=0;i<textChannels.size();i++){
                                try {
                                    TextChannel channel=textChannels.get(i);
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                    jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                    jsonTextChannels.put(jsonObject);
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                            jsonGuildChannel.put(llCommonKeys.keyTextChannels,jsonTextChannels);
                            List<VoiceChannel>voiceChannels=guildChannel.getVoiceChannels();
                            JSONArray jsonVoiceChannels=new JSONArray();
                            for(int i=0;i<voiceChannels.size();i++){
                                try {
                                    VoiceChannel channel=voiceChannels.get(i);
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put(llCommonKeys.keyId,channel.getIdLong());
                                    jsonObject.put(llCommonKeys.ChannelStructure.keyName,channel.getName());
                                    jsonVoiceChannels.put(jsonObject);
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                            jsonGuildChannel.put(llCommonKeys.keyVoiceChannels,jsonVoiceChannels);

                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonGuildChannels.put(jsonGuildChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonResponse.put(llCommonKeys.keyVoiceChannels,jsonGuildChannels);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/members")
        public void getMembers() {
            String fName="[getMembers]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }

                List<Member>members=guild.getMembers();
                JSONArray jsonMembers=new JSONArray();
                for(int i2=0;i2<members.size();i2++){
                    try {
                        JSONObject jsonMember=new JSONObject();
                        Member member= members.get(i2);
                        String str="";
                        User user=member.getUser();
                        jsonMember.put(llCommonKeys.keyId,member.getIdLong());
                        JSONObject jsonUser=new JSONObject();
                        jsonUser.put(llCommonKeys.keyName,user.getName());
                        jsonUser.put(llCommonKeys.UserStructure.keyDiscriminator,user.getDiscriminator());
                        str=user.getAvatarUrl();if(str==null)str="";
                        jsonUser.put(llCommonKeys.keyAvatarUrl,str);
                        OffsetDateTime timeCreated=user.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonUser.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);
                        jsonMember.put(llCommonKeys.keyUser,jsonUser);

                        OffsetDateTime timeJoined=member.getTimeJoined();
                        JSONObject jsonTimeJoined =new JSONObject();
                        jsonTimeJoined.put(llCommonKeys.keyDate,timeJoined.getYear()+"."+timeJoined.getMonthValue()+"."+timeJoined.getDayOfMonth());
                        jsonTimeJoined.put(llCommonKeys.keyTime,timeJoined.getHour()+"."+timeJoined.getMinute()+"."+timeJoined.getSecond());
                        //jsonTimeJoined.put(expStats.keyDayOfWeek,timeJoined.getDayOfWeek().getValue());
                        //jsonTimeJoined.put(expStats.keyDayOfYear,timeJoined.getDayOfYear());
                        jsonMember.put(llCommonKeys.keyTimeJoined,jsonTimeJoined);
                        if(dataSetDiscord.inputFlagIncludeRoles){
                            try {
                                List<Role>roles=member.getRoles();
                                JSONArray jsonRoles =new JSONArray();
                                for(int i=0;i<roles.size();i++){
                                    try {
                                        Role role=roles.get(i);
                                        JSONObject jsonRole=new JSONObject();
                                        jsonRole.put(llCommonKeys.keyId,role.getId());
                                        jsonRole.put(llCommonKeys.RoleStructure.keyName,role.getName());
                                        //jsonRole.put(llCommonKeys.keyColorRaw,role.getColorRaw());
                                        //Color color=role.getColor();
                                        //jsonRole.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                        /*jsonRole.put(llCommonKeys.RoleStructure.keyHoist,role.isHoisted());
                        jsonRole.put(llCommonKeys.RoleStructure.Misc.keyPublic,role.isPublicRole());
                        jsonRole.put(llCommonKeys.RoleStructure.keyMentionable,role.isMentionable());
                        jsonRole.put(expStats.keyIManaged,role.isManaged());*/
                                        jsonRoles.put(jsonRole);
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                }
                                jsonMember.put(llCommonKeys.keyRoles,jsonRoles);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        if(dataSetDiscord.inputFlagIncludePermissions){
                            try {
                                JSONArray jsonPermissions=new JSONArray();
                                EnumSet<Permission> permissionEnum=member.getPermissions();
                                Iterator<Permission>permissionIterator=permissionEnum.iterator();
                                while(permissionIterator.hasNext()){
                                    Permission permission=permissionIterator.next();
                                    jsonPermissions.put(permission.getName());
                                }
                                jsonMember.put(llCommonKeys.keyPermissions,jsonPermissions);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        try {
                            jsonMember.put(llCommonKeys.keyColorRaw,member.getColorRaw());
                            Color color=member.getColor();
                            if(color!=null)jsonMember.put(llCommonKeys.keyColorRGBA,color.getRed()+"."+color.getGreen()+"."+color.getBlue()+"."+color.getAlpha());
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        try {
                            str=member.getNickname();if(str==null)str="";
                            jsonMember.put(llCommonKeys.MemberStructure.keyNick,str);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonMember.put(llCommonKeys.keyIsOwner,member.isOwner());
                        jsonMembers.put(jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                jsonResponse.put(llCommonKeys.keyMembers,jsonMembers);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
        @DynExpress(context = "/guild/bans")
        public void getBans() {
            String fName="[getBans]";
            try {
                logger.info(fName+"init");
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                if(gGlobal==null){
                    logger.error(fName + "Error: global is null");
                    expStats.respondInternalError(gRes);
                    return;
                }
                if(dataSetDiscord.inputGuildId ==null|| dataSetDiscord.inputGuildId.isBlank()){
                    logger.warn(fName + "Has no guild id");
                    expStats.respond_NoGuildId(gRes);
                    return;
                }
                Guild guild=gGlobal.getGuild(dataSetDiscord.inputGuildId);
                if(guild==null){
                    logger.warn(fName + "No such guild");
                    expStats.respond_InvalidGuild(gRes);
                    return;
                }

                List<Guild.Ban>bans=guild.retrieveBanList().complete();
                JSONArray jsonMembers=new JSONArray();
                for(int i2=0;i2<bans.size();i2++){
                    try {
                        JSONObject jsonMember=new JSONObject();
                        Guild.Ban ban= bans.get(i2);
                        String str="";
                        User user=ban.getUser();
                        jsonMember.put(llCommonKeys.keyId,user.getIdLong());
                        jsonMember.put(llCommonKeys.keyName,user.getName());
                        jsonMember.put(llCommonKeys.keyReason,ban.getReason());
                        jsonMember.put(llCommonKeys.UserStructure.keyDiscriminator,user.getDiscriminator());
                        str=user.getAvatarUrl();if(str==null)str="";
                        jsonMember.put(llCommonKeys.keyAvatarUrl,str);
                        OffsetDateTime timeCreated=user.getTimeCreated();
                        JSONObject jsonTimeCreated =new JSONObject();
                        jsonTimeCreated.put(llCommonKeys.keyDate,timeCreated.getYear()+"."+timeCreated.getMonthValue()+"."+timeCreated.getDayOfMonth());
                        jsonTimeCreated.put(llCommonKeys.keyTime,timeCreated.getHour()+"."+timeCreated.getMinute()+"."+timeCreated.getSecond());
                        //jsonTimeCreated.put(expStats.keyDayOfWeek,timeCreated.getDayOfWeek().getValue());
                        //jsonTimeCreated.put(expStats.keyDayOfYear,timeCreated.getDayOfYear());
                        jsonMember.put(llCommonKeys.keyTimeCreated,jsonTimeCreated);

                        jsonMembers.put(jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                jsonResponse.put(llCommonKeys.keyBans,jsonMembers);
                JSONObject jsonGuild=new JSONObject();
                jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                jsonGuild.put(llCommonKeys.GuildStructure.keyName,guild.getName());
                jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);

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
