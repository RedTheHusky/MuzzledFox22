package models.lc.webhook;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;


public class lcWebHookBuild extends lcWebHook2{
    WebhookAction webhookAction=null; //public Webhook webhook;
    Logger logger = Logger.getLogger(getClass()); String cName="[lcWebHookBuild]";
    public lcWebHookBuild(){
        String fName="[constructor]";
        logger.info(fName);
    }
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
            gWebhook=webhookAction.complete();
            logger.info(fName+"completed");
            return gWebhook!=null;
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
}
