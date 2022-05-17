package models.lc.discordentities;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcMessageBuildComponents;
import models.ll.llCommonKeys;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetChannelMessagesUrl;

public class lcMyMessageJsonBuilder {
    //https://discord.com/developers/docs/resources/channel#create-message
    Logger logger = Logger.getLogger(getClass());

    public lcMyMessageJsonBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageJsonBuilder(JSONObject jsonObject){
        String fName="build";
        try {
            setJson(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageJsonBuilder clear() {
        String fName="[clear]";
        try {
            content = null;
            tts = false;
            embed=new lcMyEmbedBuilder();
            allowed_mentions=new JSONObject();
            message_reference=new JSONObject();
            payload_json=new JSONObject();
            components=new ArrayList<>();;
            files=new ArrayList<>();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private MessageBuilder getBuildMessage() {
        String fName="[getBuildMessage]";
        try {
            MessageBuilder messageBuilder=new MessageBuilder();
            return messageBuilder;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    
    protected String content = null;
    protected boolean tts = false;
    protected lcMyEmbedBuilder embed=new lcMyEmbedBuilder();
    protected JSONObject allowed_mentions=new JSONObject();
    protected JSONObject message_reference=new JSONObject();
    protected JSONObject payload_json=new JSONObject();
    protected List<lcMessageBuildComponent> components=new ArrayList<>();
    String keyContent="content",keyTts="tts",keyEmbed="embed",keyAllowedMentions="allowed_mentions",keyMessageReference="message_reference",keyPayloadJson="payload_json",keyComponents="components";
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(content!=null&&!content.isBlank())jsonObject.put(keyContent,content);
            if(embed!=null&&!embed.isEmpty())jsonObject.put(keyEmbed,embed.getJSON());
            if(payload_json!=null&&!payload_json.isEmpty())jsonObject.put(keyPayloadJson,payload_json);
            if(allowed_mentions!=null&&!allowed_mentions.isEmpty())jsonObject.put(keyAllowedMentions,allowed_mentions);
            if(message_reference!=null&&!message_reference.isEmpty())jsonObject.put(keyMessageReference,message_reference);
            if(message_reference!=null&&!message_reference.isEmpty())jsonObject.put(keyMessageReference,message_reference);
            if(components!=null&&!components.isEmpty()) {
                JSONArray jsonArray=getComponentsJson();
                if(jsonArray!=null&&!jsonArray.isEmpty())jsonObject.put(keyComponents, jsonArray);
            }
            logger.info(fName + ".value="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setJson(JSONObject jsonObject) {
        String fName="[setJson]";
        try {
            clear();
            logger.info(fName + ".value="+jsonObject.toString());
            if(jsonObject.has(keyContent))content=jsonObject.getString(keyContent);
            if(jsonObject.has(keyEmbed))embed=new lcMyEmbedBuilder(jsonObject.getJSONObject(keyEmbed));
            if(jsonObject.has(keyAllowedMentions))allowed_mentions=jsonObject.getJSONObject(keyAllowedMentions);
            if(jsonObject.has(keyMessageReference))message_reference=jsonObject.getJSONObject(keyMessageReference);
            if(jsonObject.has(keyTts))tts=jsonObject.getBoolean(keyTts);
            if(jsonObject.has(keyPayloadJson))payload_json=jsonObject.getJSONObject(keyPayloadJson);
            if(jsonObject.has(keyComponents)){
                JSONArray array=jsonObject.getJSONArray(keyComponents);
                for(int i=0;i<array.length();i++){
                    components.add(new lcMessageBuildComponent(array.getJSONObject(i)));
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder setTTS(boolean tts) {
        String fName="[setTTS]";
        try {
            logger.info(fName + ".value="+tts);
            this.tts = tts;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public boolean getTTS() {
        String fName="[getTTS]";
        try {
            logger.info(fName + ".value="+this.tts);
            return this.tts;
        }catch (Exception e){
            return false;
        }
    }

    public lcMyMessageJsonBuilder setPayloadJson(@Nullable JSONObject payload_json) {
        String fName="[setPayloadJson]";
        try {
            if(payload_json==null){
                logger.info(fName + ".is input null");
                this.payload_json=new JSONObject();
                return  this;
            }
            this.payload_json =payload_json;
            logger.info(fName + ".value="+this.payload_json);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearPayloadJson() {
        String fName="[clearPayloadJson]";
        try {
            this.payload_json=new JSONObject();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getPayloadJson() {
        String fName="[getPayloadJson]";
        try {
            logger.info(fName + ".value="+this.payload_json.toString());
            return this.payload_json;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder setEmbed(@Nullable MessageEmbed embed) {
        String fName="[setEmbed]";
        try {
            if(embed==null){
                logger.info(fName + ".is input null");
                this.embed=new lcMyEmbedBuilder();
                return  this;
            }
            lcMyEmbedBuilder myEmbedBuilder=new lcMyEmbedBuilder(embed);
            if(myEmbedBuilder!=null){
                this.embed = myEmbedBuilder;
                logger.info(fName + ".value="+this.embed.getJSON());
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setEmbed(@Nullable EmbedBuilder embed) {
        String fName="[setEmbed]";
        try {
            if(embed==null){
                logger.info(fName + ".is input null");
                this.embed=new lcMyEmbedBuilder();
                return  this;
            }
            return setEmbed(embed.build());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setEmbed(@Nullable JSONObject embed) {
        String fName="[setEmbed]";
        try {
            if(embed==null){
                logger.info(fName + ".is input null");
                this.embed=new lcMyEmbedBuilder();
                return  this;
            }
            lcMyEmbedBuilder myEmbedBuilder=new lcMyEmbedBuilder(embed);
            if(myEmbedBuilder!=null){
                this.embed = myEmbedBuilder;
                logger.info(fName + ".value="+this.embed.getJSON());
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setEmbed(@Nullable lcMyEmbedBuilder embed) {
        String fName="[setEmbed]";
        try {
            if(embed==null){
                logger.info(fName + ".is input null");
                this.embed=new lcMyEmbedBuilder();
                return  this;
            }
            this.embed =embed;
            logger.info(fName + ".value="+this.embed.getJSON());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearEmbed() {
        String fName="[clearEmbed]";
        try {
            this.embed=new lcMyEmbedBuilder();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getEmbedJson() {
        String fName="[getEmbedJson]";
        try {
            logger.info(fName + ".value="+this.embed.getJSON().toString());
            return this.embed.getJSON();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder getMyEmbed() {
        String fName="[getMyEmbed]";
        try {
            logger.info(fName + ".value="+this.embed.getJSON().toString());
            return embed;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public EmbedBuilder getEmbedBuilder() {
        String fName="[getEmbedBuilder]";
        try {
            logger.info(fName + ".value="+this.embed.getJSON().toString());
            return embed.embed();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed getEmbed() {
        String fName="[getEmbed]";
        try {
            logger.info(fName + ".value="+this.embed.getJSON().toString());
            lcMyEmbedBuilder myEmbedBuilder=new lcMyEmbedBuilder();
            return embed.build();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder setAllowedMentions(@Nullable lcMyMessageAllowedMentionsBuilder allowedMentions) {
        String fName="[setAllowedMentions]";
        try {
            if(allowedMentions==null){
                logger.info(fName + ".is input null");
                this.allowed_mentions=new JSONObject();
                return  this;
            }
            this.allowed_mentions=allowedMentions.getJson();
            logger.info(fName + ".result="+this.allowed_mentions);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setAllowedMentions(@Nullable JSONObject allowedMentions) {
        String fName="[setAllowedMentions]";
        try {
            if(allowedMentions==null){
                logger.info(fName + ".is input null");
                this.allowed_mentions=new JSONObject();
                return  this;
            }
            this.allowed_mentions=allowedMentions;
            logger.info(fName + ".result="+this.allowed_mentions);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearAllowedMentions() {
        String fName="[clearAllowedMentions]";
        try {
            this.allowed_mentions=new JSONObject();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getAllowedMentionsJson() {
        String fName="[getAllowedMentionsJson]";
        try {
            logger.info(fName + ".value="+this.allowed_mentions.toString());
            return this.allowed_mentions;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder  getAllowedMentions() {
        String fName="[getAllowedMentions]";
        try {
            logger.info(fName + ".value="+this.embed.toString());
            lcMyMessageAllowedMentionsBuilder  allowedMentionsBuilder=new lcMyMessageAllowedMentionsBuilder ();
            return allowedMentionsBuilder.setJson(this.allowed_mentions);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder setMessageReference(@Nullable lcMyMessageReferenceBuilder messageReference) {
        String fName="[setMessageReference]";
        try {
            if(messageReference==null){
                logger.info(fName + ".is input null");
                this.message_reference=new JSONObject();
                return  this;
            }
            this.message_reference=messageReference.getJson();
            logger.info(fName + ".result="+this.message_reference);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setMessageReference(@Nullable JSONObject messageReference) {
        String fName="[setMessageReference]";
        try {
            if(messageReference==null){
                logger.info(fName + ".is input null");
                this.message_reference=new JSONObject();
                return  this;
            }
            this.message_reference=messageReference;
            logger.info(fName + ".result="+this.message_reference);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getMessageReferenceJson() {
        String fName="[getMessageReferenceJson]";
        try {
            logger.info(fName + ".value="+this.message_reference.toString());
            return this.message_reference;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearMessageReference() {
        String fName="[clearMessageReference]";
        try {
            this.message_reference=new JSONObject();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageReferenceBuilder  getMessageReference() {
        String fName="[getMessageReference]";
        try {
            logger.info(fName + ".value="+this.message_reference.toString());
            lcMyMessageReferenceBuilder  messageReferenceBuilder=new lcMyMessageReferenceBuilder();
            return messageReferenceBuilder.setJson(this.message_reference);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder setContent(@Nullable String content) {
        String fName="[setContent]";
        try {
            if(content==null){
                logger.info(fName + ".is input null");
                this.content=null;
                return  this;
            }
            this.content=content;
            logger.info(fName + ".result="+this.content);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearContent() {
        String fName="[clearAllowedMentions]";
        try {
            this.content=null;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getContent() {
        String fName="[getContent]";
        try {
            logger.info(fName + ".value="+this.content);
            return this.content;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyMessageJsonBuilder addComponent(@Nullable lcMessageBuildComponent component) {
        String fName="[addComponent]";
        try {
            if(component==null){
                logger.info(fName + ".is input null");
                return  null;
            }
            this.components.add(component);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent remComponent(int index) {
        String fName="[remComponent]";
        try {
            return  this.components.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent getComponent(int index) {
        String fName="[getComponent]";
        try {
            return this.components.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setComponent(int index,lcMessageBuildComponent component) {
        String fName="[setComponent]";
        try {
            if(component==null){
                logger.info(fName + ".is input null");
                return  null;
            }
            this.components.set(index,component);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setComponents(@Nullable lcMessageBuildComponents components) {
        String fName="[setComponents]";
        try {
            if(components==null){
                logger.info(fName + ".is input null");
                this.components=new ArrayList<>();;
                return  this;
            }
            return setComponents(components.getComponents());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder addComponents(@Nullable List<lcMessageBuildComponent> components) {
        String fName="[addComponents]";
        try {
            if(components==null){
                logger.info(fName + ".is input null");
                return  null;
            }
            for (lcMessageBuildComponent component : components) {
                this.components.add(component);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setComponents(@Nullable List<lcMessageBuildComponent> components) {
        String fName="[setComponents]";
        try {
            if(components==null){
                logger.info(fName + ".is input null");
                this.components=new ArrayList<>();;
                return  this;
            }
            this.components=components;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder addComponents(@Nullable JSONArray components) {
        String fName="[addComponents]";
        try {

            if(components==null){
                logger.info(fName + ".is input null");
                return  null;
            }
            for(int i=0;i<components.length();i++){
                this.components.add(new lcMessageBuildComponent(components.getJSONObject(i)));
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder setComponents(@Nullable JSONArray components) {
        String fName="[setComponents]";
        try {
            this.components=new ArrayList<>();
            if(components==null){
                logger.info(fName + ".is input null");
                return  this;
            }
            for(int i=0;i<components.length();i++){
                this.components.add(new lcMessageBuildComponent(components.getJSONObject(i)));
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearComponents() {
        String fName="[clearComponents]";
        try {
            this.components=new ArrayList<>();;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray getComponentsJson() {
        String fName="[getComponentsJson]";
        try {
            logger.info(fName + ".size="+this.components.size());
            JSONArray array=new JSONArray();
            for(lcMessageBuildComponent component:components){
                if(!component.isIgnored()){
                    array.put(component.getJson());
                }
            }
            return array;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcMessageBuildComponent> getComponents() {
        String fName="[getComponents]";
        try {
            return components;
        }catch (Exception e){
            return null;
        }
    }

    public Message post(TextChannel textChannel){
        String fName="[post]";
        try {
            logger.info(fName+".textChannel="+textChannel.getIdLong());
            JSONObject jsonObject=postHttpBody(textChannel.getIdLong());
            Message message=null;
            if(jsonObject.has(llCommonKeys.MessageStructureGet.keyId)){
                message= lsMessageHelper.lsGetMessageById(textChannel,jsonObject.getString(llCommonKeys.MessageStructureGet.keyId));
            }
            logger.info(fName+".message.id="+message.getId());
            return  message;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message post(PrivateChannel privateChannel){
        String fName="[post]";
        try {
            logger.info(fName+".privateChannel="+privateChannel.getIdLong());
            JSONObject jsonObject=postHttpBody(privateChannel.getIdLong());
            Message message=null;
            if(jsonObject.has(llCommonKeys.MessageStructureGet.keyId)){
                message= lsMessageHelper.lsGetMessageById(privateChannel,jsonObject.getString(llCommonKeys.MessageStructureGet.keyId));
            }
            logger.info(fName+".message.id="+message.getId());
            return  message;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message post(Member member){
        String fName="[post]";
        try {
            logger.info(fName+".member="+member.getIdLong());
            return  post(member.getUser());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message post(User user){
        String fName="[post]";
        try {
            logger.info(fName+".user="+user.getIdLong());
            PrivateChannel privateChannel=user.openPrivateChannel().complete();
            return  post(privateChannel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Message patch(Message message){
        String fName="[patch]";
        try {
            logger.info(fName+".message.id="+message.getIdLong());
            logger.info(fName+".channel.id="+message.getChannel().getId());
            JSONObject jsonObject=patchHttpBody(message.getIdLong(),message.getChannel().getIdLong());

            return  message;

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    //issues
    private Message post(long channel){
        String fName="[post]";
        try {
            logger.info(fName+".channel="+channel);
            JSONObject jsonObject=postHttpBody(channel);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.info(fName+".json is null or empty");
                return null;
            }
            Message message=null;
            String id="",guildId="";
            if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyId)){
                id=jsonObject.getString(llCommonKeys.lsMessageJsonKeys.keyId);
            }
            if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyGuildId)){
                guildId=jsonObject.getString(llCommonKeys.lsMessageJsonKeys.keyGuildId);
            }
            if(guildId==null||guildId.isBlank()){
                logger.info(fName+".private");
            }else{
                logger.info(fName+".guild");

            }
            logger.info(fName+".message.id="+message.getId());
            return  message;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private JSONObject postHttpBody( long channel){
        String fName="[postHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=null;
            if(isFilesEmpty()){
                jsonResponse=postHttp(channel);
            }else{
                jsonResponse=postHttpFormData(channel);
            }
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299||jsonResponse.getStatus()<200){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return new JSONObject();
            }else{
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    private HttpResponse<JsonNode> postHttp( long channel){
        String fName="[postHttp]";
        try {
            logger.info(fName+".channel="+channel);
            String url = liGetChannelMessagesUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(channel));
            JSONObject jsonObject=getJson();
            logger.info(fName+".json="+jsonObject.toString());
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.post(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject)
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            return  jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    private JSONObject patchHttpBody(long message, long channel){
        String fName="[patchHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=null;
            if(isFilesEmpty()){
                jsonResponse=patchHttp(message,channel);
            }else{
                //jsonResponse=postHttpFormData(channel);
            }
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299||jsonResponse.getStatus()<200){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return new JSONObject();
            }else{
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    private HttpResponse<JsonNode> patchHttp(long message, long channel){
        String fName="[patchHttp]";
        try {
            logger.info(fName+".channel="+channel+", message="+message);
            String url = liGetChannelMessagesUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(channel))+"/"+message;
            JSONObject jsonObject=getJson();
            logger.info(fName+".json="+jsonObject.toString());
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.patch(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject)
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            return  jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }

    List<incFileInputStream>files=new ArrayList<>();
    public static class incFileInputStream {
        Logger logger = Logger.getLogger(getClass());
        public incFileInputStream(){
            String fName="[File.build]";
            try {

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public incFileInputStream(String filename, ContentType contentType,InputStream content){
            String fName="[File.build]";
            try {
                set(filename,contentType,content);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private String filename="";
        private ContentType contentType=null;
        private InputStream content;

        public incFileInputStream clear() {
            String fName="[File.clear]";
            try {
                filename="";
                contentType=null;
                content=null;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incFileInputStream set(String filename, ContentType contentType,InputStream content) {
            String fName="[File.setJson]";
            try {
                clear();
                this.filename=filename;this.contentType=contentType;this.content=content;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public String getFilename() {
            String fName="[incFile.getFilename]";
            try {
                return this.filename;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ContentType getContentType() {
            String fName="[incFile.getContentType]";
            try {
                return this.contentType;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public InputStream getContent() {
            String fName="[incFile.getContent]";
            try {
                return this.content;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEmpty() {
            String fName="[incFile.isEmpty]";
            try {
               if(this.filename==null||this.filename.isBlank()){
                   logger.warn(fName + ".filename is null or blank");
                   return true;
               }
                if(this.contentType==null){
                    logger.warn(fName + ".contenttype is null or blank");
                    return true;
                }
                if(this.content==null){
                    logger.warn(fName + ".content is null or blank");
                    return true;
                }
                logger.warn(fName + ".default>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }

    }
    private HttpResponse<JsonNode> postHttpFormData(long channel){
        String fName="[postHttp]";
        try {
            logger.info(fName+".channel="+channel);
            String url = liGetChannelMessagesUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(channel));
            JSONObject jsonObject=getJson();
            logger.info(fName+".json="+jsonObject.toString());
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =null;
            MultipartBody b=a.post(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "multipart/form-data")
                    .multiPartContent();
            if(!jsonObject.isEmpty())b.field("payload_json",jsonObject.toString());
            for(int i=0;i<this.files.size();i++){
                incFileInputStream file=this.files.get(i);
               b.field("file",file.getContent(), file.getContentType(),file.getFilename());
            }
            jsonResponse=b.asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            return  jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public lcMyMessageJsonBuilder addFile(String name,ContentType contentType,InputStream inputStream) {
        String fName="[addFile]";
        try {
            logger.info(fName + ".name="+name+", type="+contentType.getMimeType());
            incFileInputStream file=new incFileInputStream(name,contentType,inputStream);
            if(!file.isEmpty()){
                this.files.add(file);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyMessageJsonBuilder clearFiles() {
        String fName="[clearFiles]";
        try {
            this.files=new ArrayList<>();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<incFileInputStream> getFiles() {
        String fName="[getFilesCount]";
        try {
            return  this.files;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public int getFilesCount() {
        String fName="[getFilesCount]";
        try {
            return  this.files.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isFilesEmpty() {
        String fName="[isFilesEmpty]";
        try {
            return  this.files.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }

}
