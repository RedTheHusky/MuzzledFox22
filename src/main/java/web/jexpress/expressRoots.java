package web.jexpress;
import express.DynExpress;
import express.http.RequestMethod;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import express.http.request.Request;
import express.http.response.Response;
import web.jexpress.childrens.expresschildGuild;
import web.jexpress.childrens.expresschildRoots;
import web.jexpress.models.expStats;

import java.util.*;

public class expressRoots {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public expressRoots(lcGlobalHelper global) {
        gGlobal=global;
    }

    @DynExpress() // Default is context="/" and method=RequestMethod.GET
    public void getIndex(Request req, Response res) {
        String fName="getIndex";
        try {
            new expresschildRoots(gGlobal,req, res,"getIndex");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    @DynExpress(context = "/about") // Only context is defined, method=RequestMethod.GET is used as method
    public void getAbout(Request req, Response res) {
        String fName="getAbout";
        try {
            new expresschildRoots(gGlobal,req, res,"getAbout");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    @DynExpress(context = "/statistics") // Only context is defined, method=RequestMethod.GET is used as method
    public void getStatics(Request req, Response res) {
        String fName="getStatistics";
        try {
            new expresschildRoots(gGlobal,req, res,"getStatistics");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    @DynExpress(context = "/bot")
    public void getBotUser(Request req, Response res) {
        String fName="[getBotUser]";
        try {
            new expresschildRoots(gGlobal,req, res,"getBotUser");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
           expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild")
    public void getGuild(Request req, Response res) {
        String fName="[getGuild]";
        try {
            new expresschildGuild(gGlobal,req, res,"getGuild");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/member")
    public void getMember(Request req, Response res) {
        String fName="[getMember]";
        try {
            new expresschildGuild(gGlobal,req, res,"getMember");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/role")
    public void getRole(Request req, Response res) {
        String fName="[getRole]";
        try {
            new expresschildGuild(gGlobal,req, res,"getRole");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/channel")
    public void getChannel(Request req, Response res) {
        String fName="[getChannel]";
        try {
            new expresschildGuild(gGlobal,req, res,"getChannel");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/emote")
    public void getEmote(Request req, Response res) {
        String fName="[getEmote]";
        try {
            new expresschildGuild(gGlobal,req, res,"getEmote");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/roles")
    public void getRoles(Request req, Response res) {
        String fName="[getRoles]";
        try {
            new expresschildGuild(gGlobal,req, res,"getRoles");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/emotes")
    public void getEmotes(Request req, Response res) {
        String fName="[getEmotes]";
        try {
            new expresschildGuild(gGlobal,req, res,"getEmotes");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/channels")
    public void getChannels(Request req, Response res) {
        String fName="[getChannels]";
        try {
            new expresschildGuild(gGlobal,req, res,"getChannels");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/textchannels")
    public void getTextChannels(Request req, Response res) {
        String fName="[getTextChannels]";
        try {
            new expresschildGuild(gGlobal,req, res,"getTextChannels");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/voicechannels")
    public void getVoiceChannels(Request req, Response res) {
        String fName="[getVoiceChannels]";
        try {
            new expresschildGuild(gGlobal,req, res,"getVoiceChannels");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/categories")
    public void getCategoryChannels(Request req, Response res) {
        String fName="[getCategoryChannels]";
        try {
            new expresschildGuild(gGlobal,req, res,"getCategoryChannels");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/members")
    public void getMembers(Request req, Response res) {
        String fName="[getMembers]";
        try {
            new expresschildGuild(gGlobal,req, res,"getMembers");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/guild/bans")
    public void getBans(Request req, Response res) {
        String fName="[getBans]";
        try {
            new expresschildGuild(gGlobal,req, res,"getBans");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }

    @DynExpress(context = "/compare/members")
    public void getCompareMembers(Request req, Response res) {
        String fName="[getCompareMembers]";
        try {
            new expresschildRoots(gGlobal,req, res,"getCompareMembers");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/compare/bans")
    public void getCompareBanned(Request req, Response res) {
        String fName="[getCompareBanned]";
        try {
            new expresschildRoots(gGlobal,req, res,"getCompareBanned");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }


    @DynExpress(context = "/interactions")
    public void getInteractions(Request req, Response res) {
        String fName="[getInteractions]";
        try {
            new expresschildRoots(gGlobal,req, res,"getInteractions");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/interactions",method = RequestMethod.POST)
    public void postInteractions(Request req, Response res) {
        String fName="[postInteractions]";
        try {
            new expresschildRoots(gGlobal,req, res,"postInteractions");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }


    @DynExpress(context = "/lovesense")
    public void getLovesense(Request req, Response res) {
        String fName="[getLovesense]";
        try {
            new expresschildRoots(gGlobal,req, res,"getLovesense");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }

    @DynExpress(context = "/test")
    public void test(Request req, Response res) {
        String fName="test";
        try {
            new expresschildRoots(gGlobal,req, res,"test");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
   /* @DynExpress(context = "*")
    public void all(Request req, Response res) {
        String fName="all";
        try {
            expStats.respond_PageNotFound404(res);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }*/

}
