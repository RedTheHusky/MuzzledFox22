package models.lc.webhook;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsDiscordApi;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static models.llGlobalHelper.llBotToken;

public class lcWebhookHttp {
    //https://discord.com/developers/docs/resources/webhook
    protected Logger logger = Logger.getLogger(getClass());
    private lcWebhookHttp(){
        String fName="[constructor]";
        logger.info(fName);
    }
    protected String id="",guild_id="",channel_id="",name="",avatar="",token="", application_id ="",url="";
    protected int type=0;
    protected JDA jda;
    protected Guild guild;
    protected TextChannel textChannel;
    protected User user;
    protected JSONObject source_guild=new JSONObject(),source_channel=new JSONObject(), jsonUser=new JSONObject();
    protected Webhook webhook;
    private lcWebhookHttp(Webhook webhook){
        String fName="[constructor]";
        try {
            this.webhook=webhook;
            this.jda=webhook.getJDA();
            this.id=webhook.getId();
            this.token=webhook.getToken();
            this.name=webhook.getName();
            this.url=webhook.getUrl();
            this.type=webhook.getType().getKey();
           if(!webhook.isPartial()){
               this.guild=webhook.getGuild();
               this.guild_id=guild.getId();
               this.textChannel=webhook.getChannel();
               this.channel_id=textChannel.getId();
           }else{
               logger.warn(fName+"webhook is partial");
           }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private lcWebhookHttp(JDA jda, long id){
        String fName="[constructor]";
        try {
            this.jda=webhook.getJDA();
            this.id=String.valueOf(id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private lcWebhookHttp(JDA jda, long id,String token){
        String fName="[constructor]";
        try {
            this.jda=webhook.getJDA();
            if(getWebHookHttp(String.valueOf(id),token).isSuccess()){
                setGuild();
                setChannel();
                setUser();
                setWebhook();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    private HttpResponse<JsonNode> getWebHookHttp(String id,String token){
        String fName="[getWebHookHttp]";
        try {
            logger.info(fName+".id="+id+", token="+token);
            String url ="";
            if(token!=null&&token.isBlank()){
                url= lsDiscordApi.liWebhookwToken.replaceAll("!WEBHOOK", id).replaceAll("!TOKEN",token);
            }else{
                url= lsDiscordApi.liWebhook.replaceAll("!WEBHOOK", id);
            }
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+jsonResponse.getBody().getObject());
            if(jsonResponse.isSuccess()){
                JSONObject jsonObject=jsonResponse.getBody().getObject();
                Iterator<String>keys=jsonObject.keys();
                while(keys.hasNext()){
                    String key=keys.next();
                    try {
                        switch (key){
                            case llCommonKeys.WebhookStructure.Id:
                                if(!jsonObject.isNull(key))id=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.Type:
                                if(!jsonObject.isNull(key))type=jsonObject.optInt(key);
                                break;
                            case llCommonKeys.WebhookStructure.GuildId:
                                if(!jsonObject.isNull(key))guild_id=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.ChannelId:
                                if(!jsonObject.isNull(key))channel_id=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.Name:
                                if(!jsonObject.isNull(key))name=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.Avatar:
                                if(!jsonObject.isNull(key))avatar=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.Token:
                                if(!jsonObject.isNull(key))token=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.ApplicationId:
                                if(!jsonObject.isNull(key)) application_id =jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.Url:
                                if(!jsonObject.isNull(key))url=jsonObject.optString(key);
                                break;
                            case llCommonKeys.WebhookStructure.SourceChannel:
                                if(!jsonObject.isNull(key))source_channel=jsonObject.optJSONObject(key);
                                break;
                            case llCommonKeys.WebhookStructure.SourceGuild:
                                if(!jsonObject.isNull(key))source_guild=jsonObject.optJSONObject(key);
                                break;
                            case llCommonKeys.WebhookStructure.User:
                                if(!jsonObject.isNull(key))jsonUser=jsonObject.optJSONObject(key);
                                break;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            return  jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    private Guild setGuild(){
        String fName="[setGuild]";
        try {
            logger.info(fName+".guild_id="+guild_id);
            guild=jda.getGuildById(guild_id);
            return  guild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private TextChannel setChannel(){
        String fName="[setChannel]";
        try {
            logger.info(fName+".channel_id="+channel_id);
            textChannel=guild.getTextChannelById(channel_id);
            return  textChannel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private User setUser(){
        String fName="[setUser]";
        try {
            logger.info(fName+".jsonUser="+jsonUser.toString());
            user=jda.getUserById(jsonUser.optString(llCommonKeys.UserStructure.keyId));
            return  user;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private Webhook setWebhook(){
        String fName="[setUser]";
        try {
            logger.info(fName+".id="+id);
            List<Webhook>webhooks=textChannel.retrieveWebhooks().complete();
            for(Webhook webhook:webhooks){
                if(webhook.getId().equalsIgnoreCase(id)){
                    logger.info(fName+"found");
                    this.webhook=webhook;
                    return webhook;
                }
            }
            logger.info(fName+"not found");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public User getUser(){
        String fName="[getUser]";
        try {
            logger.info(fName+"user="+user.getId());
            return  user;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getChannel(){
        String fName="[getChannel]";
        try {
            logger.info(fName+"channel="+textChannel.getId());
            return  textChannel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(){
        String fName="[getGuild]";
        try {
            logger.info(fName+"guild="+guild.getId());
            return  guild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId(){
        String fName="[getChannelId]";
        try {
            logger.info(fName+"channel="+channel_id);
            return  channel_id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getGuildId(){
        String fName="[getGuild]";
        try {
            logger.info(fName+"guild="+guild_id);
            return  guild_id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getId(){
        String fName="[getId]";
        try {
            logger.info(fName+"id="+id);
            return  id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getIdLong(){
        String fName="[getIdLong]";
        try {
            long r=Long.parseLong(id);
            logger.info(fName+"id="+r);
            return  r;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getToken(){
        String fName="[getToken]";
        try {
            logger.info(fName+"token="+token);
            return  token;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getTypeInt(){
        String fName="[getTypeInt]";
        try {
            logger.info(fName+"type="+type);
            return  type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public WebhookType getType(){
        String fName="[getType]";
        try {
            logger.info(fName+"type.int="+type);
            WebhookType type=WebhookType.fromKey(this.type);
            logger.info(fName+"type.name="+type.name());
            return  type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return WebhookType.UNKNOWN;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            logger.info(fName+"name="+name);
            return  name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getAvatar(){
        String fName="[getAvatar]";
        try {
            logger.info(fName+"avatar="+avatar);
            return  avatar;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getUrl(){
        String fName="[getUrl]";
        try {
            logger.info(fName+"url="+url);
            return  url;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getApplication(){
        String fName="[getApplication]";
        try {
            logger.info(fName+"application_id="+ application_id);
            return  application_id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isIncoming(){
        String fName="[isIncoming]";
        try {
            logger.info(fName+"type:"+ type+"?=1");
            return  type==1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isChannelFollower(){
        String fName="[isChannelFollowet]";
        try {
            logger.info(fName+"type:"+ type+"?=2");
            return  type==2;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isApplication(){
        String fName="[isApplication]";
        try {
            logger.info(fName+"type:"+ type+"?=3");
            return  type==3;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getSourceGuildJson(){
        String fName="[getSourceGuildJson]";
        try {
            logger.info(fName+"json="+source_guild.toString());
            return  source_guild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getSourceGuildId(){
        String fName="[getSourceGuildId]";
        try {
            String id=getSourceGuildJson().optString(llCommonKeys.keyId);
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getSourceGuildIdLong(){
        String fName="[getSourceGuildId]";
        try {
            long id=Long.parseLong(getSourceChannelId());
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Guild getSourceGuild(){
        String fName="[getSourceGuild]";
        try {
            String id=getSourceGuildId();
            try {
                Guild guild=jda.getGuildById(id);
                if(guild!=null){
                    logger.info(fName+"found A");
                    return guild;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            List<JDA> jdaList=jda.getShardManager().getShards();
            for(JDA jda:jdaList){
                try {
                    Guild guild=jda.getGuildById(id);
                    if(guild!=null){
                        logger.info(fName+"found B");
                        return guild;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            logger.info(fName+"not found");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getSourceChannelJson(){
        String fName="[getSourceChannelJson]";
        try {
            logger.info(fName+"json="+source_channel.toString());
            return  source_channel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getSourceChannelId(){
        String fName="[getSourceChannelId]";
        try {
            String id=getSourceChannelJson().optString(llCommonKeys.keyId);
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getSourceChannelIdLong(){
        String fName="[getSourceChannelId]";
        try {
            long id=Long.parseLong(getSourceChannelId());
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public TextChannel getSourceChannel(){
        String fName="[getSourceChannel]";
        try {
            String id=getSourceGuildId();
            try {
                TextChannel textChannel=jda.getTextChannelById(id);
                if(textChannel!=null){
                    logger.info(fName+"found A");
                    return textChannel;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            List<JDA> jdaList=jda.getShardManager().getShards();
            for(JDA jda:jdaList){
                try {
                    TextChannel textChannel=jda.getTextChannelById(id);
                    if(textChannel!=null){
                        logger.info(fName+"found B");
                        return textChannel;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            logger.info(fName+"not found");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Webhook getWebhook(){
        String fName="[getWebhook]";
        try {
            logger.info(fName+"id="+webhook.getId());
            return webhook;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
