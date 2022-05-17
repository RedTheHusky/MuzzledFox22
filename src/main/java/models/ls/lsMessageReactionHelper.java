package models.ls;

import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEmoteEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface lsMessageReactionHelper
{
    //https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/entities/Activity.Emoji.html
    //Emoji: unicode emoji.
    //Emote: custom emoji (Emote)
    /*ReactionCode
        For unicode emojis this will be the unicode of said emoji rather than an alias like :smiley:.
        For custom emotes this will be the name and id of said emote in the format <name>:<id>.
     */
    static String getEmoji(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmote()){
                logger.info(fName+"is a custom emoji>ignore");
                return "";
            }
            if(!reactionEmote.isEmoji()){
                logger.info(fName+"not a unicode emoji>ignore");
                return "";
            }
            String response=reactionEmote.getEmoji();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoji(GuildMessageReactionAddEvent message){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoji(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoji(GuildMessageReactionRemoveEvent message){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoji(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoji(PrivateMessageReactionAddEvent message){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoji(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoji(PrivateMessageReactionRemoveEvent message){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoji(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getUnicode(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getUnicode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            return  getEmoji(reactionEmote);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getUnicode(GuildMessageReactionAddEvent message){
        String fName="[getUnicode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getUnicode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getUnicode(GuildMessageReactionRemoveEvent message){
        String fName="[getUnicode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getUnicode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getUnicode(PrivateMessageReactionAddEvent message){
        String fName="[getUnicode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getUnicode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getUnicode(PrivateMessageReactionRemoveEvent message){
        String fName="[getUnicode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getUnicode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getCodepoints(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getCodepoints]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmote()){
                logger.info(fName+"is a custom emoji>ignore");
                return "";
            }
            if(!reactionEmote.isEmoji()){
                logger.info(fName+"not a unicode emoji>ignore");
                return "";
            }
            String response=reactionEmote.getAsCodepoints();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getCodepoints(GuildMessageReactionAddEvent message){
        String fName="[getCodepoints]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getCodepoints(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getCodepoints(GuildMessageReactionRemoveEvent message){
        String fName="[getCodepoints]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getCodepoints(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getCodepoints(PrivateMessageReactionAddEvent message){
        String fName="[getCodepoints]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getCodepoints(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getCodepoints(PrivateMessageReactionRemoveEvent message){
        String fName="[getCodepoints]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getCodepoints(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static Emote getEmote(MessageReaction.ReactionEmote reactionEmote){
    String fName="[getEmote]";
    Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
    try{
        logger.info(fName+".");
        if(reactionEmote.isEmoji()){
            logger.info(fName+"not a unicode emoji>ignore");
            return null;
        }
        if(!reactionEmote.isEmote()){
            logger.info(fName+"is not a custom emoji>ignore");
            return null;
        }

        return reactionEmote.getEmote();
    }catch (Exception e){
        logger.error(fName + ".exception=" + e);
        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        return null;
    }
}
    static Emote getEmote(GuildMessageReactionAddEvent message){
        String fName="[getEmote]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmote(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote getEmote(GuildMessageReactionRemoveEvent message){
        String fName="[getEmote]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmote(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote getEmote(PrivateMessageReactionAddEvent message){
        String fName="[getEmote]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmote(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote getEmote(PrivateMessageReactionRemoveEvent message){
        String fName="[getEmote]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmote(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String getEmoteName(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getEmoteName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmoji()){
                logger.info(fName+"not a unicode emoji>ignore");
                return null;
            }
            if(!reactionEmote.isEmote()){
                logger.info(fName+"is not a custom emoji>ignore");
                return null;
            }
            String response=reactionEmote.getEmote().getName();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteName(GuildMessageReactionAddEvent message){
        String fName="[getEmoteName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteName(GuildMessageReactionRemoveEvent message){
        String fName="[getEmoteName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteName(PrivateMessageReactionAddEvent message){
        String fName="[getEmoteName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteName(PrivateMessageReactionRemoveEvent message){
        String fName="[getEmoteName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getEmoteId(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmoji()){
                logger.info(fName+"is a unicode emoji>ignore");
                return null;
            }
            if(!reactionEmote.isEmote()){
                logger.info(fName+"not a custom emoji>ignore");
                return null;
            }
            String response=reactionEmote.getEmote().getId();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteId(GuildMessageReactionAddEvent message){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteId(GuildMessageReactionRemoveEvent message){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteId(PrivateMessageReactionAddEvent message){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getEmoteId(PrivateMessageReactionRemoveEvent message){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static long getEmoteLongId(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getEmoteId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmoji()){
                logger.info(fName+"is unicode emoji>snowfalkeid");
                return 0L;
            }
            if(!reactionEmote.isEmote()){
                logger.info(fName+"not a custom emoji>ignore");
                return 0L;
            }
            long response=reactionEmote.getEmote().getIdLong();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getEmoteLongId(GuildMessageReactionAddEvent message){
        String fName="[getEmoteLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getEmoteLongId(GuildMessageReactionRemoveEvent message){
        String fName="[getEmoteLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getEmoteLongId(PrivateMessageReactionAddEvent message){
        String fName="[getEmoteLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getEmoteLongId(PrivateMessageReactionRemoveEvent message){
        String fName="[getEmoteLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getEmoteLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }

    static String getName(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmoji()){
                logger.info(fName+"unicode emoji");
                String response=reactionEmote.getEmoji();
                logger.info(fName+"response:"+response);
                return response;
            }
            if(reactionEmote.isEmote()){
                logger.info(fName+"custom emoji");
                String response=reactionEmote.getEmote().getName();
                logger.info(fName+"response:"+response);
                return response;
            }
            logger.info(fName+"none above");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getName(GuildMessageReactionAddEvent message){
        String fName="[getName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getName(GuildMessageReactionRemoveEvent message){
        String fName="[getName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getName(PrivateMessageReactionAddEvent message){
        String fName="[getName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getName(PrivateMessageReactionRemoveEvent message){
        String fName="[getName]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getName(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getReactionCode(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getReactionCode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmote()){
                logger.info(fName+"is a custom emoji");
                String response=reactionEmote.getAsReactionCode();
                logger.info(fName+"response:"+response);
                return response;
            }
            if(reactionEmote.isEmoji()){
                logger.info(fName+"is a unicode emoji");
                String response=reactionEmote.getAsReactionCode();
                logger.info(fName+"response:"+response);
                return response;
            }
            logger.info(fName+"none above");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getReactionCode(GuildMessageReactionAddEvent message){
        String fName="[getReactionCode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getReactionCode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getReactionCode(GuildMessageReactionRemoveEvent message){
        String fName="[getReactionCode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getReactionCode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getReactionCode(PrivateMessageReactionAddEvent message){
        String fName="[getReactionCode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getReactionCode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getReactionCode(PrivateMessageReactionRemoveEvent message){
        String fName="[getReactionCode]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getReactionCode(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getMention(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getMention]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            if(reactionEmote.isEmote()){
                logger.info(fName+"is a custom emoji");
                String response=reactionEmote.getEmote().getAsMention();
                logger.info(fName+"response:"+response);
                return response;
            }
            if(reactionEmote.isEmoji()){
                logger.info(fName+"is a unicode emoji");
                String response=reactionEmote.getAsReactionCode();
                logger.info(fName+"response:"+response);
                return response;
            }
            logger.info(fName+"none above");
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getMention(GuildMessageReactionAddEvent message){
        String fName="[getMention]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getMention(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getMention(GuildMessageReactionRemoveEvent message){
        String fName="[getMention]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getMention(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getMention(PrivateMessageReactionAddEvent message){
        String fName="[getMention]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getMention(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getMention(PrivateMessageReactionRemoveEvent message){
        String fName="[getMention]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getMention(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static String getId(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            String response=reactionEmote.getId();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getId(GuildMessageReactionAddEvent message){
        String fName="[getId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getId(GuildMessageReactionRemoveEvent message){
        String fName="[getId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getId(PrivateMessageReactionAddEvent message){
        String fName="[getId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String getId(PrivateMessageReactionRemoveEvent message){
        String fName="[getId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static long getLongId(MessageReaction.ReactionEmote reactionEmote){
        String fName="[getLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            long response=reactionEmote.getIdLong();
            logger.info(fName+"response:"+response);
            return response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getLongId(GuildMessageReactionAddEvent message){
        String fName="[getLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getLongId(GuildMessageReactionRemoveEvent message){
        String fName="[getLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getLongId(PrivateMessageReactionAddEvent message){
        String fName="[getLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static long getLongId(PrivateMessageReactionRemoveEvent message){
        String fName="[getLongId]";
        Logger logger = Logger.getLogger(lsMessageReactionHelper.class);
        try{
            logger.info(fName+".");
            logger.info(fName+"message="+message.getMessageId()+", user="+message.getUser().getName()+"("+message.getUser().getId()+"), "+"textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            return getLongId(message.getReactionEmote());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
}