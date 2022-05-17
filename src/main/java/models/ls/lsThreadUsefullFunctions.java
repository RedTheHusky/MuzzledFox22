package models.ls;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

import static models.llGlobalHelper.llPrefixStr;

public interface lsThreadUsefullFunctions {
    static boolean setThreadName4Display(Thread thread, String name) {
        String fName = "[setThreadName4Display]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            if (name == null) {
                throw new Exception("Name cant be null!");
            }
            if (name.isBlank()) {
                throw new Exception("Name cant be blank!");
            }
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            String old=thread.getName();
            logger.info(fName + ".old thread=" + old+", new="+name);
            thread.setName(name+" ["+old+"]");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getThreadName(Thread thread) {
        String fName = "[getThreadName]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            String name=thread.getName();
            logger.info(fName + ".name=" + name);
            return name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Long getThreadId(Thread thread) {
        String fName = "[getThreadId]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            long id=thread.getId();
            logger.info(fName + ".id=" + id);
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static boolean setThreadName4Display( String name) {
        String fName = "[setThreadName4Display]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            return setThreadName4Display(Thread.currentThread(),name);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getThreadName() {
        String fName = "[getThreadName]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            return getThreadName(Thread.currentThread());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Long getThreadId() {
        String fName = "[getThreadId]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            return getThreadId(Thread.currentThread());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    //Webhook-RateLimit Thread WebhookID: 913025116844199966
    static Set<Thread>getThreadsAsSet() {
        String fName = "[getThreadsAsSet]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            Set<Thread> set = Thread.getAllStackTraces().keySet();
            logger.info(fName+".set.size="+set.size());
            return set;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<Thread>getThreadsAsList() {
        String fName = "[getThreadsAsList]";
        Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
        try {
            Set<Thread>set = getThreadsAsSet();
            if(set==null){
                logger.warn(fName+".set is null");
                return  new ArrayList<>();
            }
            if(set.isEmpty()){
                logger.warn(fName+".set is empty");
                return  new ArrayList<>();
            }
            List<Thread>list=new ArrayList<>();
            for(Thread thread:set){
                list.add(thread);
            }
            logger.info(fName+".list.size="+list.size());
            return list;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    interface WebhookRateLimitThreads{
        String name="Webhook-RateLimit Thread WebhookID: ";
        long millisecondsToClear=3600000;
        interface  KEYS{
            String main="WebhookRateLimitThreads";
            interface JSON{
                String list="list",
                        added="added",
                        removed="removed",
                        interrupt="interrupt",interrupted="interrupted";
            }
            interface THREAD{
                String id="id",
                        webhook_id="webhook_id",
                        timestamp_registered="timestamp_registered",timestamp_checked="timestamp_checked";
            }
        }
        static Set<Thread>getThreadsAsSet() {
            String fName = "[WebhookRateLimitThreads@getThreadsAsSet]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                Set<Thread> threadSet = lsThreadUsefullFunctions.getThreadsAsSet();
                logger.info(fName+".threadSet.size="+threadSet.size());
                Set<Thread> set= new HashSet<>();
                for(Thread thread:threadSet){
                   String threadName=thread.getName();
                   if(threadName.contains(WebhookRateLimitThreads.name)){
                       set.add(thread);
                   }
                }
                logger.info(fName+".set.size="+set.size());
                return set;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<Thread>getThreadsAsList() {
            String fName = "[WebhookRateLimitThreads@getThreadsAsList]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                List<Thread>threadList = lsThreadUsefullFunctions.getThreadsAsList();
                if(threadList==null){
                    logger.warn(fName+".list is null");
                    return  new ArrayList<>();
                }
                if(threadList.isEmpty()){
                    logger.warn(fName+".list is empty");
                    return  new ArrayList<>();
                }
                List<Thread>list=new ArrayList<>();
                for(Thread thread:threadList){
                    String threadName=thread.getName();
                    if(threadName.contains(WebhookRateLimitThreads.name)){
                        list.add(thread);
                    }
                }
                logger.info(fName+".list.size="+list.size());
                return list;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static Set<String>getWebHooksIdAsSet() {
            String fName = "[WebhookRateLimitThreads@getWebHooksIdAsSet]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                Set<Thread> threadSet = WebhookRateLimitThreads.getThreadsAsSet();
                logger.info(fName+".threadSet.size="+threadSet.size());
                Set<String> set= new HashSet<>();
                for(Thread thread:threadSet){
                    set.add(thread.getName().replaceAll(WebhookRateLimitThreads.name,""));
                }
                logger.info(fName+".set.size="+set.size());
                return set;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<String>getWebHooksIdAsList() {
            String fName = "[WebhookRateLimitThreads@getWebHooksIdAsList]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                List<Thread>threadList = WebhookRateLimitThreads.getThreadsAsList();
                if(threadList==null){
                    logger.warn(fName+".list is null");
                    return  new ArrayList<>();
                }
                if(threadList.isEmpty()){
                    logger.warn(fName+".list is empty");
                    return  new ArrayList<>();
                }
                List<String>list=new ArrayList<>();
                for(Thread thread:threadList){
                    list.add(thread.getName().replaceAll(WebhookRateLimitThreads.name,""));
                }
                logger.info(fName+".list.size="+list.size());
                return list;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static String getWebHookId(Thread thread) {
            String fName = "[WebhookRateLimitThreads@getWebHookId]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                if(thread==null){
                    logger.warn(fName+".thread is null");
                    return "";
                }
                String threadName=thread.getName();
                if(!threadName.contains(WebhookRateLimitThreads.name)){
                    logger.warn(fName+".invalid thread");
                    return "";
                }
                threadName=thread.getName().replaceAll(WebhookRateLimitThreads.name,"");
                return threadName;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        static JSONObject getJsonById(lcGlobalHelper global,long id) {
            String fName = "[WebhookRateLimitThreads@getJsonById]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                if(!global.jsonObject.has(KEYS.main)){
                    logger.info(fName + ".json["+KEYS.main+"] branch is missing");
                    return new JSONObject();
                }
                if(global.jsonObject.isNull(KEYS.main)){
                    logger.info(fName + ".json["+KEYS.main+"] branch is null");
                    return new JSONObject();
                }
                if(!global.jsonObject.getJSONObject(KEYS.main).has(KEYS.JSON.list)){
                    logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"] branch is missing");
                    return new JSONObject();
                }
                if(global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).isEmpty()){
                    logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"] branch is empty");
                    return new JSONObject();
                }
                //logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"]="+global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).toString());
                for(int i=0;i<global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).length();i++){
                    JSONObject jsonObject=global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).getJSONObject(i);
                    if(jsonObject.has(KEYS.THREAD.id)){
                        if(jsonObject.optLong(KEYS.THREAD.id,0)==id){
                            return jsonObject;
                        }
                    }
                }
                return new JSONObject();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static JSONObject removeJsonById(lcGlobalHelper global,long id) {
            String fName = "[WebhookRateLimitThreads@removeJsonById]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                if(!global.jsonObject.has(KEYS.main)){
                    logger.info(fName + ".json["+KEYS.main+"] branch is missing");
                    return new JSONObject();
                }
                if(global.jsonObject.isNull(KEYS.main)){
                    logger.info(fName + ".json["+KEYS.main+"] branch is null");
                    return new JSONObject();
                }
                if(!global.jsonObject.getJSONObject(KEYS.main).has(KEYS.JSON.list)){
                    logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"] branch is missing");
                    return new JSONObject();
                }
                if(global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).isEmpty()){
                    logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"] branch is empty");
                    return new JSONObject();
                }
                //logger.info(fName + ".json["+KEYS.main+"."+KEYS.JSON.list+"]="+global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).toString());
                for(int i=0;i<global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).length();i++){
                    JSONObject jsonObject=global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).getJSONObject(i);
                    if(jsonObject.has(KEYS.THREAD.id)){
                        if(jsonObject.optLong(KEYS.THREAD.id,0)==id){
                            jsonObject=new JSONObject(global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).getJSONObject(i).toString());
                            global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).remove(i);
                            return jsonObject;
                        }
                    }
                }
                return new JSONObject();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        static JSONObject registeringAndCleaning(lcGlobalHelper global) {
            String fName = "[registeringAndCleaning]";
            Logger logger = Logger.getLogger(lsThreadUsefullFunctions.class);
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(KEYS.JSON.added,new JSONArray());
                jsonObject.put(KEYS.JSON.interrupt,new JSONArray());
                jsonObject.put(KEYS.JSON.interrupted,new JSONArray());
                if(!global.jsonObject.has(KEYS.main)||global.jsonObject.isNull(KEYS.main)){
                    global.jsonObject.put(KEYS.main,new JSONObject());
                    global.jsonObject.getJSONObject(KEYS.main).put(KEYS.JSON.list,new JSONArray());
                    logger.info(fName + ".created json["+KEYS.main+"] branch");
                }
                if(!global.jsonObject.getJSONObject(KEYS.main).has(KEYS.JSON.list)||global.jsonObject.getJSONObject(KEYS.main).isNull(KEYS.JSON.list)){
                    global.jsonObject.getJSONObject(KEYS.main).put(KEYS.JSON.list,new JSONArray());
                    logger.info(fName + ".created json["+KEYS.main+"."+KEYS.JSON.list+"] branch");
                }
                logger.info(fName + ".read json["+KEYS.main+"."+KEYS.JSON.list+"]="+global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).toString());
                Set<Thread>threads=WebhookRateLimitThreads.getThreadsAsSet();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                for(Thread thread:threads){
                    logger.info(fName + ".thread["+thread.getId()+"].name="+thread.getName()+", isAlive="+thread.isAlive()+", isInterrupted="+thread.isInterrupted()+", isDaemon="+thread.isDaemon());
                    long id = thread.getId();
                    if(thread.isAlive()) {
                        String webhookid = WebhookRateLimitThreads.getWebHookId(thread);
                        JSONObject jsonThread = WebhookRateLimitThreads.getJsonById(global, id);
                        if (jsonThread == null || jsonThread.isEmpty()) {
                            jsonThread.put(KEYS.THREAD.id, id);
                            jsonThread.put(KEYS.THREAD.webhook_id, webhookid);
                            jsonThread.put(KEYS.THREAD.timestamp_registered, timestamp.getTime());
                            jsonThread.put(KEYS.THREAD.timestamp_checked, timestamp.getTime());
                            global.jsonObject.getJSONObject(KEYS.main).getJSONArray(KEYS.JSON.list).put(jsonThread);
                            jsonObject.getJSONArray(KEYS.JSON.added).put(jsonThread);
                            logger.info(fName + ".thread[" + id + "]_registered=" + jsonThread.toString());
                            WebhookRateLimitThreads.getJsonById(global, id);
                        } else {
                            jsonThread.put(KEYS.THREAD.timestamp_checked, timestamp.getTime());
                            logger.info(fName + ".thread[" + id + "]_already registered=" + jsonThread.toString());
                            long timestamp_registered = jsonThread.optLong(KEYS.THREAD.timestamp_registered);
                            long timestamp_current = timestamp.getTime();
                            long timestamp_diff =timestamp_current-timestamp_registered;
                            logger.info(fName + ".thread[" + id + "]: timestamp_current=" + timestamp_current + ", timestamp_registered=" + timestamp_registered + ", timeout=" + WebhookRateLimitThreads.millisecondsToClear+", diff="+timestamp_diff);
                            if (timestamp_diff >= WebhookRateLimitThreads.millisecondsToClear) {
                                try {
                                    thread.interrupt();
                                    jsonObject.getJSONArray(KEYS.JSON.interrupt).put(jsonThread);
                                    logger.info(fName + ".thread[" + id + "] send interrupt command");
                                } catch (Exception e) {
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                        }
                    }else
                    if(thread.isInterrupted()){
                        JSONObject jsonThread = WebhookRateLimitThreads.removeJsonById(global, id);
                        if (jsonThread == null || jsonThread.isEmpty()) {
                            logger.info(fName + ".thread[" + id + "]._interrupted=" + jsonThread.toString());
                            jsonObject.getJSONArray(KEYS.JSON.interrupted).put(jsonThread);
                        }
                    }else{
                        JSONObject jsonThread = WebhookRateLimitThreads.removeJsonById(global, id);
                        if (jsonThread == null || jsonThread.isEmpty()) {
                            logger.info(fName + ".thread[" + id + "]._removed=" + jsonThread.toString());
                            jsonObject.getJSONArray(KEYS.JSON.removed).put(jsonThread);
                        }
                    }
                }
                //jsonObject.put(KEYS.main,global.jsonObject.getJSONObject(KEYS.main));
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }

}
