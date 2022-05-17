package events.Guild;

import imagify.LinkImagePreview;
import models.lc.CrunchifyLog4jLevel;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nsfw.LewdImageEvents;
import org.apache.log4j.Logger;
import restraints.rdAction;
import restraints.PostSynthesizer.rdTextPostSynthesizer;
import search.QuickLewdImages;
import util.entity.GuildNotificationProfile;
import util.entity.UserPrivacy;
import util.utilityEat;
import util.utilityVotingAttachment;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class onGuildMessage extends ListenerAdapter implements llMessageHelper, llMemberHelper, llGlobalHelper {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.PREFIXUSE_STR);


    public onGuildMessage(lcGlobalHelper global){
        logger.warn(".constructor");
        gGlobal=global;
    }
    //https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/hooks/ListenerAdapter.html#<init>()
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String fName="[onGuildMessageReceived]";
        try {
            logger.info(fName + "guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            try {
                OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
                if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }else
                if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            boolean isBot=event.getAuthor().isBot();
            boolean isWebhook=event.isWebhookMessage();
            logger.info(fName+".isBot="+isBot+" isWebhook="+isWebhook);
            if(!isWebhook&&!isBot){
                logger.info(fName+".is not a bot and is not a webhookmessage");
                try {
                    List<MessageSticker> stickerList=event.getMessage().getStickers();
                    logger.info(fName + "stickerList="+stickerList.size());
                    if(!stickerList.isEmpty()){
                        MessageSticker sticker=stickerList.get(0);
                        logger.info("sticker.id="+sticker.getId()+", name="+sticker.getName()+", pack="+sticker.getPackId());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                UserPrivacy userPrivacy=new UserPrivacy(gGlobal,event);

                if(!userPrivacy.hasGuildMessageRead()){
                    logger.warn("does not have GuildMessageRead");
                }else if(!userPrivacy.isGuildMessageRead()){
                    logger.warn("GuildMessageRead is false>abort");
                    return;
                }
                new rdTextPostSynthesizer(gGlobal,event);
                //new utilityAutoPosting(gGlobal,event);
                if(event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention())){
                    logger2.log(CrunchifyLog4jLevel.PREFIXUSE,"guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+"), messageId="+event.getMessageId());
                    logger2.log(CrunchifyLog4jLevel.PREFIXUSE,"messageId="+event.getMessageId()+" content="+event.getMessage().getContentRaw());
                    gGlobal.logGuildUsage(event.getGuild(),gGlobal.keyCommandsUsedCount);
                }else
                if(event.getMessage().getContentRaw().startsWith(llPrefixStr)){
                    logger2.log(CrunchifyLog4jLevel.PREFIXUSE,"guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+"), messageId="+event.getMessageId());
                    logger2.log(CrunchifyLog4jLevel.PREFIXUSE,"messageId="+event.getMessageId()+" content="+event.getMessage().getContentRaw());
                    gGlobal.logGuildUsage(event.getGuild(),gGlobal.keyCommandsUsedCount);
                }
                if(lsCustomGuilds.lsIsCustomGuild4Red(event.getGuild())){
                    logger.info(fName+".is a guild owned by red");
                    if(event.getMessage().getContentRaw().startsWith(".")){
                        new LewdImageEvents(gGlobal,event);
                    }
                    if(event.getMessage().getContentRaw().startsWith("^")){
                        new QuickLewdImages(gGlobal,event);
                    }
                    if(event.getMessage().getContentRaw().startsWith("https://www.furaffinity.net/view/")
                            ||event.getMessage().getContentRaw().startsWith("http://www.furaffinity.net/view/")
                            ||event.getMessage().getContentRaw().startsWith("https://furaffinity.net/view/")
                            ||event.getMessage().getContentRaw().startsWith("http://furaffinity.net/view/")
                            ||event.getMessage().getContentRaw().startsWith("www.furaffinity.net/view/")
                            ||event.getMessage().getContentRaw().startsWith("furaffinity.net/view/")){
                        new LinkImagePreview(gGlobal,event);
                    }else
                    if(event.getMessage().getContentRaw().startsWith("https://www.inkbunny.net/s/")
                            ||event.getMessage().getContentRaw().startsWith("http://www.inkbunny.net/s/")
                            ||event.getMessage().getContentRaw().startsWith("https://inkbunny.net/s/")
                            ||event.getMessage().getContentRaw().startsWith("http://inkbunny.net/s/")
                            ||event.getMessage().getContentRaw().startsWith("www.inkbunny.net/s/")
                            ||event.getMessage().getContentRaw().startsWith("inkbunny.net/s/")){
                        new LinkImagePreview(gGlobal,event);
                    }else
                    if(event.getMessage().getContentRaw().startsWith("https://www.weasyl.com/")
                            ||event.getMessage().getContentRaw().startsWith("http://www.weasyl.com/")
                            ||event.getMessage().getContentRaw().startsWith("https://weasyl.com/")
                            ||event.getMessage().getContentRaw().startsWith("http://weasyl.com/")
                            ||event.getMessage().getContentRaw().startsWith("www.weasyl.com/")
                            ||event.getMessage().getContentRaw().startsWith("weasyl.com/")){
                        new LinkImagePreview(gGlobal,event);
                    }else
                    if(event.getMessage().getContentRaw().startsWith("https://www.deviantart.com/")
                            ||event.getMessage().getContentRaw().startsWith("http://www.deviantart.com/")
                            ||event.getMessage().getContentRaw().startsWith("https://deviantart.com/")
                            ||event.getMessage().getContentRaw().startsWith("http://deviantart.com/")
                            ||event.getMessage().getContentRaw().startsWith("www.deviantart.com/")
                            ||event.getMessage().getContentRaw().startsWith("deviantart.com/")){
                        new LinkImagePreview(gGlobal,event);
                    }else
                    if(event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention().replaceFirst("<@","<@!"))||event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention())||event.getMessage().getContentRaw().startsWith(llPrefixStr)){
                        String message=event.getMessage().getContentRaw();
                        event.getJDA().getSelfUser();
                        if(event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention().replaceFirst("<@","<@!"))){
                            message=message.replaceFirst(event.getGuild().getSelfMember().getAsMention().replaceFirst("<@","<@!"),"").trim();
                        }
                        if(event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention())){
                            message=message.replaceFirst(event.getGuild().getSelfMember().getAsMention(),"").trim();
                        }
                        if(event.getMessage().getContentRaw().startsWith(llPrefixStr)){
                            message=message.replaceFirst(llPrefixStr,"").trim();
                        }
                        logger.info(fName+".message="+message);
                        if(message.startsWith("https://www.furaffinity.net/view/")
                                ||message.startsWith("http://www.furaffinity.net/view/")
                                ||message.startsWith("https://furaffinity.net/view/")
                                ||message.startsWith("http://furaffinity.net/view/")
                                ||message.startsWith("www.furaffinity.net/view/")
                                ||message.startsWith("furaffinity.net/view/")){
                            new LinkImagePreview(gGlobal,event);
                        }else
                        if(message.startsWith("https://www.inkbunny.net/s/")
                                ||message.startsWith("http://www.inkbunny.net/s/")
                                ||message.startsWith("https://inkbunny.net/s/")
                                ||message.startsWith("http://inkbunny.net/s/")
                                ||message.startsWith("www.inkbunny.net/s/")
                                ||message.startsWith("inkbunny.net/s/")){
                            new LinkImagePreview(gGlobal,event);
                        }else
                        if(message.startsWith("https://www.weasyl.com/")
                                ||message.startsWith("http://www.weasyl.com/")
                                ||message.startsWith("https://weasyl.com/")
                                ||message.startsWith("http://weasyl.com/")
                                ||message.startsWith("www.weasyl.com/")
                                ||message.startsWith("weasyl.com/")){
                            new LinkImagePreview(gGlobal,event);
                        }else
                        if(message.startsWith("https://www.deviantart.com/")
                                ||message.startsWith("http://www.deviantart.com/")
                                ||message.startsWith("https://deviantart.com/")
                                ||message.startsWith("http://deviantart.com/")
                                ||message.startsWith("www.deviantart.com/")
                                ||message.startsWith("deviantart.com/")){
                            new LinkImagePreview(gGlobal,event);
                        }else
                        if(message.startsWith("punish")||message.startsWith("reward")){
                            new rdAction(gGlobal,event);
                        }
                    }
                    if(event.getMessage().getAttachments().size()>0){
                        new utilityVotingAttachment(gGlobal,event);
                    }
                }
                /*if(event.getMessage().getContentRaw().startsWith("https://www.e621.net/posts/")
                        ||event.getMessage().getContentRaw().startsWith("http://www.e621.net/posts/")
                        ||event.getMessage().getContentRaw().startsWith("https://e621.net/posts/")
                        ||event.getMessage().getContentRaw().startsWith("http://e621.net/posts/")
                        ||event.getMessage().getContentRaw().startsWith("www.e621.net/posts/")
                        ||event.getMessage().getContentRaw().startsWith("e621.net/posts/")){
                    new LinkImagePreview(gGlobal,event);
                }*/
            }else{
                logger.info(fName+".ignore do to bot or webhookmessage");
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    /*
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event){
        String fName="onGuildMessageDelete";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMessageEmbed(@Nonnull GuildMessageEmbedEvent event) {
        String fName="onGuildMessageEmbed";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    */
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event){
        String fName="[onGuildMessageReactionAdd]";
        try {
            logger.info(fName + "guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), user="+event.getUser().getName()+"("+event.getUser().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            try {
                OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
                if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }else
                if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            boolean isBot=event.getMember().getUser().isBot();
            if(!isBot){
                logger.info(fName+".user");
                //new rdCollar(gGlobal,event);
                //new rdChastity(gGlobal,event);
                new rdAction(gGlobal,event);
                new utilityEat(gGlobal,event);
            }else{
                logger.info(fName+".bot");
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event){
        String fName="[onGuildMessageReactionRemove]";
        try {
            logger.info(fName + "guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), user="+ Objects.requireNonNull(event.getUser()).getName()+"("+event.getUser().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        try {
            try {
                OnlineStatus onlineStatus=event.getJDA().getPresence().getStatus();
                if(onlineStatus.equals(OnlineStatus.OFFLINE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }else
                if(onlineStatus.equals(OnlineStatus.INVISIBLE)){
                    logger.info(fName + ".status is set offline>ignore events");
                    return;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            boolean isBot=event.getMember().getUser().isBot();
            if(!isBot){
                logger.info(fName+".user");
            }else{
                logger.info(fName+".bot");
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    /*
    public void onGuildMessageReactionRemoveAll(@Nonnull GuildMessageReactionRemoveAllEvent event){
        String fName="onGuildMessageReactionRemoveAll";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMessageReactionRemoveEmote(@Nonnull GuildMessageReactionRemoveEmoteEvent event){
        String fName="onGuildMessageReactionRemoveEmote";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        String fName = "onGuildMessageUpdate";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+"), author="+event.getAuthor().getName()+"("+event.getAuthor().getId()+"), messageId="+event.getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void onMessageBulkDelete(@Nonnull MessageBulkDeleteEvent event){
        String fName="onMessageBulkDelete";
        try {
            logger.info("guild="+event.getGuild().getName()+"("+event.getGuild().getId()+"), channel="+event.getChannel().getName()+"("+event.getChannel().getId()+")");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    */



}
