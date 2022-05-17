package models.lc.interaction.applicationcommand;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class lcApplicationInteractionMessage implements llCommonKeys.MessageStructureGet{
    Logger logger = Logger.getLogger(getClass());

    private lcApplicationInteractionMessage() {
        String fName = "[build]";
        logger.info(fName + ".blank");
    }

    public lcApplicationInteractionMessage(RawGatewayEvent event, lcApplicationInteractionReceive.lInteractionResponse response, JSONObject jsonSentPayload, boolean original) {
        String fName = "[build]";
        try {
            interactionResponse=response;
            jda=interactionResponse.getJDA();
            setByRawGateway(new JSONObject(event.getPayload().toString()));
            this.original=original;
            jsonOriginalSendPayload=new JSONObject(jsonSentPayload.toString());
            messageBuilder=new lcMyMessageJsonBuilder(jsonOriginalSendPayload);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionMessage(lcApplicationInteractionReceive.lInteractionResponse response, JSONObject jsonSentPayload, JSONObject followupResponse) {
        String fName = "[build]";
        try {
            interactionResponse=response;
            jda=interactionResponse.getJDA();
            setByFollowupResponse(followupResponse);
            this.original=false;
            jsonOriginalSendPayload=new JSONObject(jsonSentPayload.toString());
            messageBuilder=new lcMyMessageJsonBuilder(jsonOriginalSendPayload);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    lcApplicationInteractionReceive.lInteractionResponse interactionResponse=null;
    private JDA jda;
    private String id="",application_id="",channel_id="";
    private int type=0,flags=0;
    private boolean ephemera =false, original=false,deleted=false;
    private Message message=null;
    private TextChannel textChannel=null; private PrivateChannel privateChannel=null;
    private  JSONObject jsonPayload =new JSONObject(),jsonOriginalSendPayload=new JSONObject();
    protected lcApplicationInteractionMessage setByRawGateway(JSONObject jsonObject) {
        String fName = "[setByRawGateway]";
        try {
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            jsonPayload =jsonObject;
            String key="";
            key=keyId;if(jsonObject.has(key))id=jsonObject.optString(key,"");
            key=keyChannelId;if(jsonObject.has(key))channel_id=jsonObject.optString(key,"");
            key=keyFlags;if(jsonObject.has(key))flags=jsonObject.optInt(key);
            if(flags==64)ephemera=true;
            key=keyType;if(jsonObject.has(key))type=jsonObject.optInt(key);
            retrieveMessage();
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionMessage setByFollowupResponse(JSONObject jsonObject) {
        String fName = "[setByFollowupResponse]";
        try {
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            jsonPayload =jsonObject;
            String key="";
            key=keyId;if(jsonObject.has(key))id=jsonObject.optString(key,"");
            key=keyChannelId;if(jsonObject.has(key))channel_id=jsonObject.optString(key,"");
            key=keyFlags;if(jsonObject.has(key))flags=jsonObject.optInt(key);
            if(flags==64)ephemera=true;
            key=keyType;if(jsonObject.has(key))type=jsonObject.optInt(key);
            retrieveMessage();
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected Message retrieveMessage() {
        String fName = "[retrieveMessage]";
        try {
            textChannel=jda.getTextChannelById(channel_id);
            if(textChannel!=null){
                logger.info(fName + "textChannel=" + textChannel.getName());
                if(isEphemera()){
                    logger.warn(fName+"ignore to retrieve as it cant ephemera messages");
                    return null;
                }
                message=textChannel.retrieveMessageById(id).complete();
            }else{
                privateChannel=jda.getPrivateChannelById(channel_id);
                if(privateChannel!=null){
                    logger.info(fName + "privateChannel=" + privateChannel.getName());
                    message=privateChannel.retrieveMessageById(id).complete();
                }else{
                    throw  new Exception("Could not get the channel");
                }
            }
            if(message==null)throw  new Exception("Could not get message");
            return message;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getId() {
        String fName = "[getId]";
        try {
            return  id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getIdAsLong() {
        String fName = "[getIdAsLong]";
        try {
            return  Long.parseLong(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JDA getJDA() {
        String fName = "[getJDA]";
        try {
            return  jda;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionReceive.lInteractionResponse getInteractionResponse() {
        String fName = "[getInteractionResponse]";
        try {
            return  interactionResponse;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId() {
        String fName = "[getChannelId]";
        try {
            return  channel_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getChannelIdAsLong() {
        String fName = "[getChannelIdAsLong]";
        try {
            return  Long.parseLong(channel_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public TextChannel getTextChannel() {
        String fName = "[getTextChannel]";
        try {
            return  textChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel() {
        String fName = "[getrivateChannel]";
        try {
            return privateChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getPayload() {
        String fName = "[getPayload]";
        try {
            return jsonPayload;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message getMessage() {
        String fName = "[getMessage]";
        try {
            return message;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEphemera() {
        String fName="[isEphemera]";
        try {
            logger.info(fName+"value="+ephemera);
            return ephemera;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationInteractionMessage setEphemera(boolean ephemera) {
        String fName="[setEphemera]";
        try {
            logger.info(fName+"value="+ephemera);
            this.ephemera=ephemera;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isOriginal() {
        String fName="[isOriginal]";
        try {
            logger.info(fName+"value="+original);
            return original;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isDeleted() {
        String fName="[isDeleted]";
        try {
            logger.info(fName+"value="+deleted);
            return deleted;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private lcMyMessageJsonBuilder messageBuilder= new lcMyMessageJsonBuilder();
    public lcMyMessageJsonBuilder getBuilder(){
        String fName="[getBuilder]";
        try {
            return messageBuilder;
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            return null;
        }
    };
    private HttpResponse<JsonNode> lastJsonResponse;
    //lcApplicationInteractionMessage lmessage=null;
    /*public boolean didWeGetMessage4(RawGatewayEvent event){
        String fName="[didWeGetMessage4]";
        try {
            if(!event.getType().equalsIgnoreCase("MESSAGE_CREATE")){
                return false;
            }
            if(event.getPayload().getInt("type")!=20){
                return false;
            }
            JSONObject jsonObject=new JSONObject(event.getPayload().toString());
            logger.info(fName+".Payload="+jsonObject.toString());
            if(!jsonObject.has(keyAuthor)){
                return false;
            }
            if(!jsonObject.getJSONObject(keyAuthor).optString(keyId).equalsIgnoreCase(jda.getSelfUser().getId())){
                return false;
            }
            if(!jsonObject.has(keyInteraction)){
                return false;
            }
            if(!jsonObject.getJSONObject(keyInteraction).optString(keyId).equalsIgnoreCase(interactionResponse.getInteractionId())){
                return false;
            }
            return true;
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            return false;
        }
    };*/
    public lcApplicationInteractionMessage edit(){
        String fName="[edit]";
        try {
            String url="";
            if(isOriginal()){
                url= "https://discord.com/api/v8/webhooks/"+interactionResponse.getApplicationInteraction().getApplicationId()+"/"+interactionResponse.getToken()+"/messages/@original";
            }else{
                url= "https://discord.com/api/v8/webhooks/"+interactionResponse.getApplicationInteraction().getApplicationId()+"/"+interactionResponse.getToken()+"/messages/"+id;
            }
            logger.info(fName+".url"+url);
            JSONObject jsonObject=messageBuilder.getJson();
            if(ephemera) jsonObject.put("flags",64);
            logger.info(fName+".json"+jsonObject.toString());
            /*interactionResponse.getGlobal().waiter.waitForEvent(RawGatewayEvent.class,
                    e -> (
                            didWeGetMessage4(e)
                    ),
                    e -> {
                        try {
                            lmessage=new lcApplicationInteractionMessage(e,interactionResponse,jsonObject,original);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

                        }
                    },5, TimeUnit.MINUTES, null);*/
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.patch(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            lastJsonResponse=jsonResponse;
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");return null;
            }else{
                logger.info(fName+".got");
            }
            /*int c=0;
            while (lmessage == null&&c<10){
                Thread.sleep(1000);
                c++;
            }
            if(lmessage==null){
                throw  new Exception("Could not return message!");
            }
            logger.info(fName +".got message");*/
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionMessage edit(lcMyMessageJsonBuilder messageJsonBuilder){
        String fName="[edit]";
        try {
            if(messageJsonBuilder==null)throw  new Exception("messagebuilder cant be null");
            this.messageBuilder=messageJsonBuilder;
            return edit();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionMessage delete(){
        String fName="[delete]";
        try {
            String url="";
            if(isOriginal()){
                url= "https://discord.com/api/v8/webhooks/"+interactionResponse.getApplicationInteraction().getApplicationId()+"/messages/"+interactionResponse.getToken()+"/@original";
            }else{
                url= "https://discord.com/api/v8/webhooks/"+interactionResponse.getApplicationInteraction().getApplicationId()+"/messages/"+interactionResponse.getToken()+"/"+id;
            }
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.delete(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            lastJsonResponse=jsonResponse;
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");return null;
            }else{
                logger.info(fName+".got");
                textChannel=null;privateChannel=null;message=null;
                deleted=true;
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}

