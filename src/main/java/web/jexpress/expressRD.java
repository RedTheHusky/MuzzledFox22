package web.jexpress;

import express.DynExpress;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import web.jexpress.childrens.expresschildRD;
import web.jexpress.models.expStats;

import java.util.*;

public class expressRD {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public expressRD(lcGlobalHelper global) {
        gGlobal=global;
    }
    @DynExpress(context = "/rd/verify")
    public void getVerify(Request req, Response res) {
        String fName="getVerify";
        try {
            new  expresschildRD(gGlobal,req,res,"getVerify");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/rd/verify",method = RequestMethod.POST)
    public void postVerify(Request req, Response res) {
        String fName="postVerify";
        try {
            new  expresschildRD(gGlobal,req,res,"getVerify");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }

    @DynExpress(context = "/rd/gag",method = RequestMethod.POST)
    public void postGag(Request req, Response res) {
        String fName="postGag";
        try {
            new expresschildRD(gGlobal,req,res,"postGag");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/rd/gag")
    public void getGag(Request req, Response res) {
        String fName="getGag";
        try {
            new expresschildRD(gGlobal,req,res,"getGag");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }


}
