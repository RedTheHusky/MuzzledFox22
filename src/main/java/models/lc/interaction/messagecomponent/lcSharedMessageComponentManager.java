package models.lc.interaction.messagecomponent;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcSharedMessageComponentManager {
    Logger logger = Logger.getLogger(getClass());
    public lcSharedMessageComponentManager(){}
    public lcSharedMessageComponentManager(lcGlobalHelper global,TextChannel textChannel,String title,User user){set(global,textChannel,title,user);}
    public lcSharedMessageComponentManager set(lcGlobalHelper global,TextChannel textChannel,String title,User user){
        String fName = "[set]";
        try {
            this.global=global;this.textChannel=textChannel;this.title=title;this.user=user;
            return this;
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    lcGlobalHelper global;
    TextChannel textChannel;
    User user;String title="";
    public lcText2Json text2Json=new lcText2Json();
    public JSONArray gJsonMessageComponent=new JSONArray();
    boolean loadMessageComponentDebug=false;
    public lcMessageBuildComponents messageBuildComponents=new lcMessageBuildComponents();
    public lcMessageBuildComponent messageBuildComponent_Button=new lcMessageBuildComponent();
    public lcMessageBuildComponent messageBuildComponent_Select=new lcMessageBuildComponent();
    public  lcMessageBuildComponent.SelectMenu selectContainer=null;
    public  lcMessageBuildComponents.Buttons buttonContainer=null;
    public lcMyMessageJsonBuilder myMessageJsonBuilder=new lcMyMessageJsonBuilder();
    public void buildMessageComponents(){
        String fName = "[buildMessageComponents]";
        try {
            messageBuildComponents.set(gJsonMessageComponent);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public void clear(){
        String fName = "[clear]";
        try {
            global.globalSettingsJson.put(llCommonKeys.keyMessageComponents,new JSONObject());
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public JSONArray readMessageComponentsJson(String path){
        String fName = "[loadMessageComponentsJson]";
        try {
            if(loadMessageComponentDebug){
                text2Json.isFile2JsonArray(path);
                return text2Json.jsonArray;
            }
            if(global==null){
                throw new Exception("global is null");
            }
            if(global.globalSettingsJson==null){
                throw new Exception("global.globalSettingsJson is null");
            }
            if(!global.globalSettingsJson.has(llCommonKeys.keyMessageComponents)||global.globalSettingsJson.isNull(llCommonKeys.keyMessageComponents)){
                global.globalSettingsJson.put(llCommonKeys.keyMessageComponents,new JSONObject());
            }
            JSONObject jsonObject=global.globalSettingsJson.optJSONObject(llCommonKeys.keyMessageComponents);
            if(!jsonObject.has(path)||jsonObject.isNull(path)){
                text2Json.isFile2JsonArray(path);
                jsonObject.put(path,text2Json.jsonArray);
                return new JSONArray(text2Json.jsonArray.toString());
            }else{
                return new JSONArray(jsonObject.getJSONArray(path).toString());

            }
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());
            return  null;
        }
    }
    public void loadMessageComponents(String path,boolean build){
        String fName = "[loadMessageComponents]";
        try {
            if(loadMessageComponentDebug){
                text2Json.isFile2JsonArray(path);
                gJsonMessageComponent=text2Json.jsonArray;
                if(build)messageBuildComponents.set(gJsonMessageComponent);
                return;
            }
            if(global==null){
                throw new Exception("global is null");
            }
            if(global.globalSettingsJson==null){
                throw new Exception("global.globalSettingsJson is null");
            }
            if(!global.globalSettingsJson.has(llCommonKeys.keyMessageComponents)||global.globalSettingsJson.isNull(llCommonKeys.keyMessageComponents)){
                global.globalSettingsJson.put(llCommonKeys.keyMessageComponents,new JSONObject());
            }
            JSONObject jsonObject=global.globalSettingsJson.optJSONObject(llCommonKeys.keyMessageComponents);
            if(!jsonObject.has(path)||jsonObject.isNull(path)){
                text2Json.isFile2JsonArray(path);
                jsonObject.put(path,text2Json.jsonArray);
                gJsonMessageComponent=new JSONArray(text2Json.jsonArray.toString());
            }else{
                gJsonMessageComponent=new JSONArray(jsonObject.getJSONArray(path).toString());

            }
            if(build)messageBuildComponents.set(gJsonMessageComponent);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public void setMessageComponents(JSONArray jsonArray,boolean build){
        String fName = "[setMessageComponents]";
        try {
            if(jsonArray==null)jsonArray=new JSONArray();
            gJsonMessageComponent=jsonArray;
            if(build)messageBuildComponents.set(gJsonMessageComponent);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public void addMessageComponents(JSONObject jsonObject,boolean build){
        String fName = "[addMessageComponents]";
        try {
            if(jsonObject==null||jsonObject.isEmpty())return;
            gJsonMessageComponent.put(jsonObject);
            if(build)messageBuildComponents.set(gJsonMessageComponent);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public void addMessageComponents(JSONArray jsonArray,boolean build){
        String fName = "[setMessageComponents]";
        try {
            if(jsonArray==null||jsonArray.isEmpty())return;
            for(int i=0;i<jsonArray.length();i++){
                gJsonMessageComponent.put(jsonArray.getJSONObject(i));
            }
            if(build)messageBuildComponents.set(gJsonMessageComponent);
        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,e.toString());

        }
    }
    public void loadMessageComponents(String path){
        loadMessageComponents(path,true);
    }
    public void setMessageComponents(JSONArray jsonArray){
        setMessageComponents(jsonArray,true);
    }
    public void addMessageComponents(JSONObject jsonObject){
        addMessageComponents(jsonObject,true);
    }
    public void addMessageComponents(JSONArray jsonArray){ addMessageComponents(jsonArray,true); }
}
