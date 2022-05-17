package web.jexpress;

import express.DynExpress;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import web.jexpress.childrens.expresschildLovense;
import web.jexpress.models.expStats;

import java.util.Arrays;

public class expressLovense {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    //https://92.83.177.246:8778/discord/Dasboard/backend/lovesense/callback
    public expressLovense(lcGlobalHelper global) {
        gGlobal=global;
    }
    @DynExpress(context = "/lovesense/callback")
    public void callback(Request req, Response res) {
        String fName="callback";
        try {
            new expresschildLovense(gGlobal,req,res,"callback");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/lovesense/callback",method = RequestMethod.POST)
    public void postcallback(Request req, Response res) {
        String fName="postcallback";
        try {
            new expresschildLovense(gGlobal,req,res,"callback");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/lovesense/verify")
    public void getVerify(Request req, Response res) {
        String fName=" getVerify";
        try {
            new expresschildLovense(gGlobal,req,res,"verify");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }



}
