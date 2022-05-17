package models.lc.webhook;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyMessage;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcMyEmbedBuilder;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.ls.lsMessageHelper;
import models.ls.lsStreamHelper;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class lcWebHook2 {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcWebHook2]";
    public lcWebHook2(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public lcWebHook2(Webhook gWebhook){
        String fName="[constructor]";
        logger.info(fName);
        this.gWebhook = gWebhook;
    }

    WebhookAction webhookAction=null; //public Webhook webhook;
    public boolean preBuild(TextChannel channel){
        String fName="[preBuild]";
        try {
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            webhookAction=channel.createWebhook("build");
            return webhookAction != null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
        //https://github.com/DV8FromTheWorld/JDA/wiki/9)-Webhooks
        //https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/entities/TextChannel.html#createWebhook(java.lang.String)
        //https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/restaction/WebhookAction.html
        //https://www.programcreek.com/java-api-examples/index.php?api=net.dv8tion.jda.core.entities.Icon
        //https://www.programcreek.com/java-api-examples/index.php?api=net.dv8tion.jda.core.entities.Icon
    }
    long deadlineOffset=10000;
    public boolean preBuild(TextChannel channel, JSONObject data){
        String fName="[preBuild]";
        logger.info(fName);
        try {
            String keyUserName="username",keyName="name",keyUserAvatar="useravatar",keyAvatar="avatar",keyAvatarurl="avatarurl";
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            webhookAction=channel.createWebhook("build").deadline(System.currentTimeMillis() + deadlineOffset);
            if(data.has(keyUserName)){
                String value=data.getString(keyUserName);
                logger.info(fName+"json:.main.name="+value);
                webhookAction.setName(value);
            }
            if(data.has(keyName)){
                String value=data.getString(keyName);
                logger.info(fName+"json:.main.name="+value);
                webhookAction.setName(value);
            }
            if(data.has(keyAvatarurl)){
                logger.info(fName+"json:.main.avataricon");
                try{
                    String value=data.getString(keyAvatarurl);
                    logger.info(fName+"json:.main.avatar="+value);
                    logger.info(fName+"url="+value);
                    URL url = new URL(value);
                    HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                    httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    InputStream is=httpcon.getInputStream();
                    Icon icon=Icon.from(is);
                    webhookAction.setAvatar(icon);
                    logger.info(fName+"json:.main.avatar:set");
                }catch (Exception e){
                    logger.error(fName+"exception="+e);
                }
            }
            if(data.has(keyUserAvatar)){
                logger.info(fName+"json:.main.avataricon");
                try{
                    String value=data.getString(keyUserAvatar);
                    logger.info(fName+"json:.main.avatar="+value);
                    logger.info(fName+"url="+value);
                    URL url = new URL(value);
                    HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                    httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    InputStream is=httpcon.getInputStream();
                    Icon icon=Icon.from(is);
                    webhookAction.setAvatar(icon);
                    logger.info(fName+"json:.main.avatar:set");
                }catch (Exception e){
                    logger.error(fName+"exception="+e);
                }
            }
            if(data.has(keyAvatar)){
                logger.info(fName+"json:.main.avataricon");
                try{
                    String value=data.getString(keyAvatar);
                    logger.info(fName+"json:.main.avatar="+value);
                    logger.info(fName+"url="+value);
                    URL url = new URL(value);
                    HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                    httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    InputStream is=httpcon.getInputStream();
                    Icon icon=Icon.from(is);
                    webhookAction.setAvatar(icon);
                    logger.info(fName+"json:.main.avatar:set");
                }catch (Exception e){
                    logger.error(fName+"exception="+e);
                }
            }
            return webhookAction != null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean build(){
        String fName="[build]";
        if(webhookAction==null){logger.warn(fName+"no action to build"); return false;}
        try{
            gWebhook =webhookAction.complete();
            logger.info(fName+"completed");
            return gWebhook !=null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean doSafetyCleanwToken(TextChannel channel){
        String fName="doSafetyCleanwToken";
        try{
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            List<Webhook>webhooks=channel.retrieveWebhooks().complete();
            logger.info(fName+"webhooks.size="+webhooks.size());
            if(webhooks.size()>=5){
                for(Webhook webhookEntry:webhooks){
                    if(webhookEntry.getOwner().getId().equals(channel.getJDA().getSelfUser().getId())){
                        OffsetDateTime then=webhookEntry.getTimeCreated();
                        OffsetDateTime current=OffsetDateTime.now();
                        logger.info(fName+" then("+webhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                        if(then.getMinute()<current.getMinute()||then.getHour()!=current.getHour()){
                            try {
                                logger.info(fName+" perform deletion");
                                String token=webhookEntry.getToken();
                                webhookEntry.delete(token).complete();
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                }
            }
            logger.info(fName+"ended");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean doSafetyCleanQueuewToken(TextChannel channel){
        String fName="doSafetyCleanQueuewToken";
        try{
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            List<Webhook>webhooks=channel.retrieveWebhooks().complete();
            logger.info(fName+"webhooks.size="+webhooks.size());
            if(webhooks.size()>=5){
                for(Webhook webhookEntry:webhooks){
                    if(webhookEntry.getOwner().getId().equals(channel.getJDA().getSelfUser().getId())){
                        OffsetDateTime then=webhookEntry.getTimeCreated();
                        OffsetDateTime current=OffsetDateTime.now();
                        logger.info(fName+" then("+webhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                        if(then.getMinute()<current.getMinute()||then.getHour()!=current.getHour()){
                            try {
                                logger.info(fName+" queue for deletion");
                                String token=webhookEntry.getToken();
                                webhookEntry.delete(token).queue();
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                }
            }
            logger.info(fName+"ended");
            return true;
        }catch (Exception e){
            logger.error(fName+"exception="+e); return false;
        }
    }
    public boolean doSafetyClean(TextChannel channel){
        String fName="doSafetyCleanwToken";
        try{
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            List<Webhook>webhooks=channel.retrieveWebhooks().complete();
            logger.info(fName+"webhooks.size="+webhooks.size());
            if(webhooks.size()>=5){
                for(Webhook webhookEntry:webhooks){
                    if(webhookEntry.getOwner().getId().equals(channel.getJDA().getSelfUser().getId())){
                        OffsetDateTime then=webhookEntry.getTimeCreated();
                        OffsetDateTime current=OffsetDateTime.now();
                        logger.info(fName+" then("+webhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                        if(then.getMinute()<current.getMinute()||then.getHour()!=current.getHour()){
                            try {
                                logger.info(fName+" perform deletion");
                                webhookEntry.delete().complete();
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                }
            }
            logger.info(fName+"ended");
            return true;
        }catch (Exception e){
            logger.error(fName+"exception="+e); return false;
        }
    }
    public boolean doSafetyCleanQueue(TextChannel channel){
        String fName="doSafetyCleanQueuewToken";
        try{
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            List<Webhook>webhooks=channel.retrieveWebhooks().complete();
            logger.info(fName+"webhooks.size="+webhooks.size());
            if(webhooks.size()>=5){
                for(Webhook webhookEntry:webhooks){
                    if(webhookEntry.getOwner().getId().equals(channel.getJDA().getSelfUser().getId())){
                        OffsetDateTime then=webhookEntry.getTimeCreated();
                        OffsetDateTime current=OffsetDateTime.now();
                        logger.info(fName+" then("+webhookEntry.getId()+")["+then.getHour()+":"+then.getMinute()+"] now["+current.getHour()+":"+current.getMinute()+"]");
                        if(then.getMinute()<current.getMinute()||then.getHour()!=current.getHour()){
                            try {
                                logger.info(fName+" queue for deletion");
                                webhookEntry.delete().queue();
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                }
            }
            logger.info(fName+"ended");
            return true;
        }catch (Exception e){
            logger.error(fName+"exception="+e); return false;
        }
    }
    public boolean isChannelMaxedOut(TextChannel channel){
        String fName="isChannelMaxedOut";
        try{
            logger.info(fName+"channel="+channel.getId()+"|"+channel.getName());
            logger.info(fName+"guild="+channel.getGuild().getId()+"|"+channel.getGuild().getName());
            List<Webhook>webhooks=channel.retrieveWebhooks().complete();
            logger.info(fName+"webhooks.size="+webhooks.size());
            return  webhooks.size()>=10;
        }
        catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    Guild gGuild;TextChannel gTextChannel;
    static public String keyUserName="username",keyName="name",keyUserAvatar="useravatar",keyAvatar="avatar",keyContent="content",keyAvatarurl="avatarurl";
    public boolean useChannel(TextChannel textChannel){
        String fName="[useChannel]";
        logger.info(fName);
        try {
            logger.info(fName+"textchannel="+textChannel.getId());
            gGuild =textChannel.getGuild();
            gTextChannel=textChannel;
            List<Webhook> webhookList=textChannel.retrieveWebhooks().complete();
            logger.info(fName + ".webhookList.size=" + webhookList.size());
            for(int i=0;i<webhookList.size();i++){
                try {
                    Webhook webhook=webhookList.get(i);
                    logger.info(fName + ".webhook["+i+"]: id=" + webhook.getId()+", owner="+webhook.getOwner().getUser().getName()+"#"+webhook.getOwner().getUser().getDiscriminator()+"("+webhook.getOwner().getId()+")");
                    if(webhook.getOwner().getIdLong()== gGuild.getJDA().getSelfUser().getIdLong()){
                        logger.info(fName + ".same id");
                        gWebhook=webhook;
                        break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(gWebhook==null){
                logger.info(fName + ".no webhook>create");
                JSONObject jsonSelf=new JSONObject();
                String selfAvatar = gGuild.getSelfMember().getUser().getEffectiveAvatarUrl();
                String selfName = gGuild.getSelfMember().getUser().getName();
                jsonSelf.put(keyName, selfName);  jsonSelf.put(keyAvatar,selfAvatar.replaceFirst("gif","png")+"?size=512");
                doSafetyCleanwToken(gTextChannel);
                if (!preBuild(gTextChannel, jsonSelf)) {
                    logger.error(fName + "failed prebuild");
                    if(isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "textChannel maxed out");
                    }else{
                        logger.error(fName + "failed for other reasons");
                    }
                    return  false;
                }
                if (!build()) {
                    logger.error(fName + "failed build");
                    if(isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "textChannel maxed out");
                    }else{
                        logger.error(fName + "failed for other reasons");
                    }
                    return  false;
                }
                logger.info(fName + ".created");
                return  true;
            }else{
                logger.info(fName + ".has webhook>use");
                return  true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean useWebhook(long id,TextChannel textChannel){
        String fName="[useWebhook]";
        logger.info(fName);
        try {
            logger.info(fName+"textchannel="+textChannel.getId()+", id="+id);
            gGuild =textChannel.getGuild();
            gTextChannel=textChannel;
            List<Webhook> webhookList=textChannel.retrieveWebhooks().complete();
            logger.info(fName + ".webhookList.size=" + webhookList.size());
            for(int i=0;i<webhookList.size();i++){
                try {
                    Webhook webhook=webhookList.get(i);
                    logger.info(fName + ".webhook["+i+"]: id=" + webhook.getId()+", owner="+webhook.getOwner().getUser().getName()+"#"+webhook.getOwner().getUser().getDiscriminator()+"("+webhook.getOwner().getId()+")");
                    if(webhook.getIdLong()== id){
                        logger.info(fName + ".same id");
                        gWebhook=webhook;
                        break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(gWebhook==null){
                logger.info(fName + ".no webhook>go to use channel");
                return useChannel(textChannel);
            }else{
                logger.info(fName + ".has webhook>use");
                return  true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public Webhook gWebhook;
    public Boolean set(Icon icon){
        String fName="[set.icon]";
        if(gWebhook ==null){logger.warn(fName+"no webhook to  set"); return false;}
        try{
           gWebhook.getManager().setAvatar(icon).complete();logger.info(fName+"done");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
	public Boolean setAvatar(String str){
        String fName="[setAvatar]";
        if(gWebhook ==null){logger.warn(fName+"no webhook to  set"); return false;}
        try{
			logger.info(fName+"url="+str);
            Icon icon=Icon.from(lsStreamHelper.llGetInputStream4WebFile(str));
            gWebhook.getManager().setAvatar(icon).complete();
			logger.info(fName+"done");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean set(TextChannel channel){
        String fName="[set.channel]";
        if(gWebhook ==null){logger.warn(fName+"no webhook to  set"); return false;}
        logger.info(fName+"guild:"+channel.getGuild().getId()+"|"+channel.getGuild().getName());
        logger.info(fName+"channel:"+channel.getId()+"|"+channel.getName());
        try{
            gWebhook.getManager().setChannel(channel).complete();logger.info(fName+"done");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean set(String name){
        String fName="[set.name]";
        if(gWebhook ==null){logger.warn(fName+"no webhook to  set"); return false;}
        try{
			logger.info(fName+"name="+name);
            gWebhook.getManager().setName(name).complete();logger.info(fName+"done");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean delete(){
        String fName="[delete]";
        try{
            if(gWebhook ==null){logger.warn(fName+"no webhook to  delete"); return false;}
            gWebhook.delete().complete();
            logger.info(fName+"done");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean deletewToken(){
        String fName="[deletewToken]";
        try{
            if(gWebhook ==null){logger.warn(fName+"no webhook to  delete"); return false;}
            String token= gWebhook.getToken();
            gWebhook.delete(token).complete();
            logger.info(fName+"done");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public WebhookClient client;
    public Boolean clientOpen(){
        //https://github.com/MinnDevelopment/discord-webhooks
        String fName="[clientOpen]";
        try{
            if(gWebhook ==null){logger.warn(fName+"no webhook to  delete"); return false;}
            client = WebhookClient.withUrl(gWebhook.getUrl());
            logger.info(fName+"timeout="+client.getTimeout());
            logger.info(fName+"done");return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clientOpen(long timeout){
        //https://github.com/MinnDevelopment/discord-webhooks
        String fName="[clientOpen]";
        try{
            if(gWebhook ==null){logger.warn(fName+"no webhook to  delete"); return false;}
            client = WebhookClient.withUrl(gWebhook.getUrl());
            logger.info(fName+"timeout="+timeout);
            client.setTimeout(timeout);
            logger.info(fName+"timeout updated to="+client.getTimeout());
            logger.info(fName+"done");return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean clientClose(){
        String fName="[clientClose]";
        try{
            if(client==null){logger.warn(fName+"no client to  close"); return false;}
            client.close();
            logger.info(fName+"done");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public void send(String message){
        String fName="[send.String]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.info(fName+"error:"+e);
        }
    }
    public void send(WebhookMessage message){
        String fName="[send.WebhookMessage]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void send(WebhookMessageBuilder message){
        String fName="[send.WebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void sendByWebhookMessageBuilder(JSONObject jsonObject){
        String fName="[sendByWebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnWebhookMessageBuilder(jsonObject);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.info(fName+"error:"+e);
        }
    }
    public void send(WebhookEmbed message){
        String fName="[send.WebhookEmbed]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void send(WebhookEmbedBuilder message){
        String fName="[send.WebhookEmbedBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void send(File message){
        String fName="[send.File]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(message);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void send(InputStream inputStream,String name){
        String fName="[send.InputStream]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return;}
            sendReturnMessage(inputStream,name);
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public ReadonlyMessage sendReturnMessage(String message){
        String fName="[sendReturnMessage.String]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"String="+message);
            ReadonlyMessage readonlyMessage=client.send(message).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnWebhookMessageBuilder(JSONObject jsonObject){
        String fName="[sendReturnWebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(File file){
        String fName="[sendReturnMessage.File]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(file==null){logger.warn(fName+"no item to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(file).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ReadonlyMessage sendReturnWebhookMessageBuilder(JSONObject jsonObject,File file){
        String fName="[sendReturnWebhookMessageBuilder.File]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(file!=null){
                webhookMessageBuilder.addFile(file);
            }
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(WebhookMessage webhookMessage){
        String fName="[sendReturnMessage.WebhookMessage]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(webhookMessage==null){logger.warn(fName+"no item to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(webhookMessage).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(WebhookMessageBuilder webhookMessageBuilder){
        String fName="[sendReturnMessage.WebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(webhookMessageBuilder==null){logger.warn(fName+"no item to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(WebhookEmbedBuilder webhookEmbedBuilder){
        String fName="[sendReturnMessage.WebhookEmbedBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(webhookEmbedBuilder==null){logger.warn(fName+"no item to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(webhookEmbedBuilder.build()).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnWebhookMessageBuilder(JSONObject jsonObject,WebhookEmbedBuilder webhookEmbedBuilder){
        String fName="[sendReturnWebhookMessageBuilder.WebhookEmbedBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(webhookEmbedBuilder!=null){
                webhookMessageBuilder.addEmbeds(webhookEmbedBuilder.build());
            }
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(WebhookEmbed webhookEmbed){
        String fName="[sendReturnMessage.WebhookEmbed]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(webhookEmbed==null){logger.warn(fName+"no item to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(webhookEmbed).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnWebhookMessageBuilder(JSONObject jsonObject,WebhookEmbed webhookEmbed){
        String fName="[sendReturnWebhookMessageBuilder.WebhookEmbed]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(webhookEmbed!=null){
                webhookMessageBuilder.addEmbeds(webhookEmbed);
            }
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage sendReturnMessage(InputStream is,String name){
        String fName="[sendReturnMessage.InputStream&Name]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            if(is==null){logger.warn(fName+"no item to send"); return null;}
            if(name==null||name.isBlank()){logger.warn(fName+"no name to send"); return null;}
            ReadonlyMessage readonlyMessage=client.send(is,name).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ReadonlyMessage sendReturnWebhookMessageBuilder(JSONObject jsonObject,InputStream is,String name){
        String fName="[sendReturnWebhookMessageBuilder.InputStream&Name]";
        try{
            if(client==null){logger.warn(fName+"no client to send"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(is!=null&&name!=null&&!name.isBlank()){
                webhookMessageBuilder.addFile(name,is);
            }
            ReadonlyMessage readonlyMessage=client.send(webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }

    public ReadonlyMessage editReturnMessage(long id,String message){
        String fName="[editReturnMessage.String]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"String="+message);
            ReadonlyMessage readonlyMessage=client.edit(id,message).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnWebhookMessageBuilder(long id,JSONObject jsonObject){
        String fName="[editReturnWebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnWebhookMessageBuilder(long id,JSONObject jsonObject,File file){
        String fName="[editReturnWebhookMessageBuilder.File]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(file!=null){
                webhookMessageBuilder.addFile(file);
            }
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnMessage(long id,WebhookMessage webhookMessage){
        String fName="[editReturnMessage.WebhookMessage]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            if(webhookMessage==null){logger.warn(fName+"no item to edit"); return null;}
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessage).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnMessage(long id,WebhookMessageBuilder webhookMessageBuilder){
        String fName="[editReturnMessage.WebhookMessageBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            if(webhookMessageBuilder==null){logger.warn(fName+"no item to edit"); return null;}
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnMessage(long id,WebhookEmbedBuilder webhookEmbedBuilder){
        String fName="[editReturnMessage.WebhookEmbedBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            if(webhookEmbedBuilder==null){logger.warn(fName+"no item to edit"); return null;}
            ReadonlyMessage readonlyMessage=client.edit(id,webhookEmbedBuilder.build()).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnWebhookMessageBuilder(long id,JSONObject jsonObject,WebhookEmbedBuilder webhookEmbedBuilder){
        String fName="[editReturnWebhookMessageBuilder.WebhookEmbedBuilder]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(webhookEmbedBuilder!=null){
                webhookMessageBuilder.addEmbeds(webhookEmbedBuilder.build());
            }
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnMessage(long id,WebhookEmbed webhookEmbed){
        String fName="[editReturnMessage.WebhookEmbed]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            if(webhookEmbed==null){logger.warn(fName+"no item to edit"); return null;}
            ReadonlyMessage readonlyMessage=client.edit(id,webhookEmbed).get();
            logger.warn(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnWebhookMessageBuilder(long id,JSONObject jsonObject,WebhookEmbed webhookEmbed){
        String fName="[editReturnWebhookMessageBuilder.WebhookEmbed]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(webhookEmbed!=null){
                webhookMessageBuilder.addEmbeds(webhookEmbed);
            }
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public ReadonlyMessage editReturnWebhookMessageBuilder(long id,JSONObject jsonObject,InputStream is,String name){
        String fName="[editReturnWebhookMessageBuilder.InputStream&Name]";
        try{
            if(client==null){logger.warn(fName+"no client to edit"); return null;}
            logger.info(fName+"jsonObject="+jsonObject.toString());
            WebhookMessageBuilder webhookMessageBuilder=new WebhookMessageBuilder();
            if(jsonObject.has(keyUserName)&&!jsonObject.isNull(keyUserName)&&!jsonObject.getString(keyUserName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyUserName));
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)&&!jsonObject.getString(keyName).isBlank()){
                webhookMessageBuilder.setUsername(jsonObject.getString(keyName));
            }
            if(jsonObject.has(keyUserAvatar)&&!jsonObject.isNull(keyUserAvatar)&&!jsonObject.getString(keyUserAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyUserAvatar));
            }
            if(jsonObject.has(keyAvatar)&&!jsonObject.isNull(keyAvatar)&&!jsonObject.getString(keyAvatar).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatar));
            }
            if(jsonObject.has(keyAvatarurl)&&!jsonObject.isNull(keyAvatarurl)&&!jsonObject.getString(keyAvatarurl).isBlank()){
                webhookMessageBuilder.setAvatarUrl(jsonObject.getString(keyAvatarurl));
            }
            if(jsonObject.has(keyContent)){
                logger.info(fName+keyContent+"="+jsonObject.getString(keyContent));
                webhookMessageBuilder.setContent(jsonObject.getString(keyContent));
            }
            if(is!=null&&name!=null&&!name.isBlank()){
                webhookMessageBuilder.addFile(name,is);
            }
            ReadonlyMessage readonlyMessage=client.edit(id,webhookMessageBuilder.build()).get();
            logger.info(fName+"done");
            return readonlyMessage;
        }catch (Exception e){
            logger.info(fName+"error:"+e);
            return null;
        }
    }
    public Boolean select4Guild(Guild guild, String id){
        String fName="[select4Guild]";
        logger.info(fName+".id="+id);
        try{
            List<Webhook> items=guild.retrieveWebhooks().complete(true);
            logger.info(fName+".items.size="+items.size());
            for(Webhook item :  items){
                logger.info(fName+".item:"+item.getId()+"|"+item.getName());
                if(item.getId().equals(id)){
                    logger.info(fName+".item:match");
                    gWebhook =item;
                    return  true;
                }
            }
            logger.info(fName+".no match found");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Boolean set2Self(CommandEvent event){
        String fName="[set2Self]";
        if(gWebhook ==null){logger.warn(fName+"no webhook to  set"); return false;}
        try{
            String avatarUrl=event.getSelfUser().getAvatarUrl();
            String avatarName=event.getSelfMember().getEffectiveName();
            logger.info(fName+"avatarUrl="+avatarUrl);
            logger.info(fName+"avatarName="+avatarName);
            gWebhook.getManager().setName(avatarName).complete();logger.info(fName+"name set");
            assert avatarUrl != null;
            URL url = new URL(avatarUrl);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream is=httpcon.getInputStream();
            Icon icon=Icon.from(is);
            gWebhook.getManager().setAvatar(icon).complete();logger.info(fName+"avatar set");
            logger.info(fName+"done");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }




}
