package models.lc.interaction.applicationcommand;
//Shecued for deletion
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcMyEmbedBuilder;
import models.lc.discordentities.lcMyMessageAllowedMentionsBuilder;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.discordentities.lcMyMessageReferenceBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcMessageBuildComponents;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.InteractionType;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


public class lcApplicationInteractionRespond implements llCommonKeys.InteractionStructure{
    Logger logger = Logger.getLogger(getClass());

    private lcApplicationInteractionRespond() {
        String fName = "[build]";
        logger.info(fName + ".blank");
    }
    protected lcApplicationInteractionRespond(InteractionHook interactionHook) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=interactionHook.getJDA();
            this.token=interactionHook.getInteraction().getToken();
            this.id=interactionHook.getInteraction().getId();
            this.acknowledged=interactionHook.getInteraction().isAcknowledged();
            this.fromGuild=interactionHook.getInteraction().isFromGuild();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token) {
        String fName = "[getId]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token,int type) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
            this.type=type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token,String content) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
            if(content!=null)this.messageBuilder.setContent(content);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token,lcMyMessageJsonBuilder messageBuilder) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
            if(messageBuilder!=null)this.messageBuilder=messageBuilder;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token,String content,boolean isEphemera) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
            if(content!=null)this.messageBuilder.setContent(content);
            this.ephemera =isEphemera;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionRespond(JDA jda, String id, String token,lcMyMessageJsonBuilder messageBuilder,boolean isEphemera) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;
            this.id=id;
            this.token=token;
            if(messageBuilder!=null)this.messageBuilder=messageBuilder;
            this.ephemera =isEphemera;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global, String id, String token) {
        String fName = "[getId]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global, String id, String token,int type) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
            this.type=type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global,String id, String token,String content) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
            if(content!=null)this.messageBuilder.setContent(content);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global,String id, String token,lcMyMessageJsonBuilder messageBuilder) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
            if(messageBuilder!=null)this.messageBuilder=messageBuilder;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global,String id, String token,String content,boolean isEphemera) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
            if(content!=null)this.messageBuilder.setContent(content);
            this.ephemera =isEphemera;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionRespond(JDA jda, lcGlobalHelper global,String id, String token,lcMyMessageJsonBuilder messageBuilder,boolean isEphemera) {
        String fName = "[lcApplicationInteractionRespond]";
        try {
            this.jda=jda;this.global=global;
            this.id=id;
            this.token=token;
            if(messageBuilder!=null)this.messageBuilder=messageBuilder;
            this.ephemera =isEphemera;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private JDA jda;protected lcGlobalHelper global=null;
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
    public String getToken() {
        String fName = "[getToken]";
        try {
            return token;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getTypeAsInt() {
        String fName = "[getTypeAsInt]";
        try {
            return  type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public InteractionType getType() {
        String fName = "[getType]";
        try {
            return  InteractionType.fromKey(type);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return InteractionType.UNKNOWN;
        }
    }
    private String id="",token="";
    private  int type=4;
    private boolean ephemera =false, acknowledged =false, fromGuild=false;
    public boolean isEphemera() {
        String fName = "[isEphemera]";
        try {
            return ephemera;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isAcknowledged() {
        String fName = "[isAcknowledged]";
        try {
            return  acknowledged;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isFromGuild() {
        String fName = "[isFromGuild]";
        try {
            return  fromGuild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationInteractionRespond setEphemera(boolean value) {
        String fName = "[setEphemera]";
        try {
            logger.info(fName+"value="+value);
            ephemera=value;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setAcknowledged(boolean value) {
        String fName = "[setAcknowledged]";
        try {
            logger.info(fName+"value="+value);
            acknowledged=value;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setFromGuild(boolean value) {
        String fName = "[setFromGuild]";
        try {
            logger.info(fName+"value="+value);
            fromGuild=value;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcMyMessageJsonBuilder messageBuilder= new lcMyMessageJsonBuilder();
    public lcApplicationInteractionRespond setEmbed(@Nullable MessageEmbed embed) {
        String fName="[setEmbed]";
        try {
            return messageBuilder.setEmbed(embed)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setEmbed(@Nullable EmbedBuilder embed) {
        String fName="[setEmbed]";
        try {
            return messageBuilder.setEmbed(embed)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setEmbed(@Nullable JSONObject embed) {
        String fName="[setEmbed]";
        try {
            return messageBuilder.setEmbed(embed)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setEmbed(@Nullable lcMyEmbedBuilder embed) {
        String fName="[setEmbed]";
        try {
            return messageBuilder.setEmbed(embed)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond clearEmbed() {
        String fName="[clearEmbed]";
        try {
            return messageBuilder.clearEmbed()!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getEmbedJson() {
        String fName="[getEmbedJson]";
        try {
            return messageBuilder.getEmbedJson();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder getMyEmbed() {
        String fName="[getMyEmbed]";
        try {
            return messageBuilder.getMyEmbed();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public EmbedBuilder getEmbedBuilder() {
        String fName="[getEmbedBuilder]";
        try {
            return messageBuilder.getEmbedBuilder();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed getEmbed() {
        String fName="[getEmbed]";
        try {
            return messageBuilder.getEmbed();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcApplicationInteractionRespond setAllowedMentions(@Nullable lcMyMessageAllowedMentionsBuilder allowedMentions) {
        String fName="[setAllowedMentions]";
        try {
            return messageBuilder.setAllowedMentions(allowedMentions)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setAllowedMentions(@Nullable JSONObject allowedMentions) {
        String fName="[setAllowedMentions]";
        try {
            return messageBuilder.setAllowedMentions(allowedMentions)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond clearAllowedMentions() {
        String fName="[clearAllowedMentions]";
        try {
            return messageBuilder.clearAllowedMentions()!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getAllowedMentionsJson() {
        String fName="[getAllowedMentionsJson]";
        try {
            return messageBuilder.getAllowedMentionsJson();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder  getAllowedMentions() {
        String fName="[getAllowedMentions]";
        try {
            return messageBuilder.getAllowedMentions();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcApplicationInteractionRespond setMessageReference(@Nullable lcMyMessageReferenceBuilder messageReference) {
        String fName="[setMessageReference]";
        try {
            return messageBuilder.setMessageReference(messageReference)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setMessageReference(@Nullable JSONObject messageReference) {
        String fName="[setMessageReference]";
        try {
            return messageBuilder.setMessageReference(messageReference)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getMessageReferenceJson() {
        String fName="[getMessageReferenceJson]";
        try {
            return messageBuilder.getMessageReferenceJson();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond clearMessageReference() {
        String fName="[clearMessageReference]";
        try {
            return messageBuilder.clearMessageReference()!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageReferenceBuilder  getMessageReference() {
        String fName="[getMessageReference]";
        try {
           return messageBuilder.getMessageReference();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcApplicationInteractionRespond setContent(@Nullable String content) {
        String fName="[setContent]";
        try {
            return messageBuilder.setContent(content)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond clearContent() {
        String fName="[clearAllowedMentions]";
        try {
            return messageBuilder.clearContent()!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getContent() {
        String fName="[getContent]";
        try {
            return messageBuilder.getContent();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcApplicationInteractionRespond addComponent(@Nullable lcMessageBuildComponent component) {
        String fName="[addComponent]";
        try {
            return messageBuilder.addComponent(component)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent remComponent(int index) {
        String fName="[remComponent]";
        try {
            return messageBuilder.remComponent(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent getComponent(int index) {
        String fName="[getComponent]";
        try {
            return messageBuilder.getComponent(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setComponent(int index,lcMessageBuildComponent component) {
        String fName="[setComponent]";
        try {
            return messageBuilder.setComponent(index,component)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setComponents(@Nullable lcMessageBuildComponents components) {
        String fName="[setComponents]";
        try {
            return messageBuilder.setComponents(components)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond addComponents(@Nullable List<lcMessageBuildComponent> components) {
        String fName="[addComponents]";
        try {
            return messageBuilder.addComponents(components)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setComponents(@Nullable List<lcMessageBuildComponent> components) {
        String fName="[setComponents]";
        try {
            return messageBuilder.setComponents(components)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond addComponents(@Nullable JSONArray components) {
        String fName="[addComponents]";
        try {

            return messageBuilder.addComponents(components)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond setComponents(@Nullable JSONArray components) {
        String fName="[setComponents]";
        try {
            return messageBuilder.setComponents(components)!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationInteractionRespond clearComponents() {
        String fName="[clearComponents]";
        try {
            return messageBuilder.clearComponents()!=null?this:null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray getComponentsJson() {
        String fName="[getComponentsJson]";
        try {
            return messageBuilder.getComponentsJson();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcMessageBuildComponent> getComponents() {
        String fName="[getComponents]";
        try {
            return messageBuilder.getComponents();
        }catch (Exception e){
            return null;
        }
    }


    private  HttpResponse<JsonNode> lastJsonResponse;
    //https://discord.com/developers/docs/interactions/receiving-and-responding#interaction-response-object-interaction-callback-type
    private int interactioncallback=0;
    public boolean send(){
        String fName="[send]";
        try {
            if(acknowledged)throw  new Exception("is Acknowledged y");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",type);
            jsonObject.put("data",messageBuilder.getJson());
            if(ephemera) jsonObject.getJSONObject("data").put("flags",64);
            logger.info(fName+".json"+jsonObject.toString());
            String url= "https://discord.com/api/v8/interactions/"+getId()+"/"+getToken()+"/callback";
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                logger.warn(fName+".invalid status");return false;
            }else{
                logger.info(fName+".got");
                interactioncallback=4;
                acknowledged =true;
                return true;
            }


        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondWithAck(){
        String fName="[respondWithAck]";
        try {
            int type=5;
            if(acknowledged)throw  new Exception("isAcknowledged");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",type);
            logger.info(fName+".json"+jsonObject.toString());
            String url= "https://discord.com/api/v8/interactions/"+getId()+"/"+getToken()+"/callback";
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                logger.warn(fName+".invalid status");return false;
            }else{
                logger.info(fName+".got");
                interactioncallback=5;
                return true;
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondWithPing(){
        String fName="[respondWithPing]";
        try {
            int type=1;
            if(acknowledged)throw  new Exception("isAcknowledged");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",type);
            logger.info(fName+".json"+jsonObject.toString());
            String url= "https://discord.com/api/v8/interactions/"+getId()+"/"+getToken()+"/callback";
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                logger.warn(fName+".invalid status");return false;
            }else{
                logger.info(fName+".got");
                interactioncallback=1;
                acknowledged =true;
                return true;
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}

