package models.ls;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;

import kong.unirest.json.JSONObject;
import models.lc.webhook.lcWebHook2;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import restraints.in.iRdStr;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface lsMessageHelper extends llColors
{
    /*https://discord.com/channels/125227483518861312/125227483518861312/860120398830108682*/
    boolean sendTyingBeforeMessage4Private=true;
    default void lsSendMessage258(User author, Message message){
        String fName="[lsSendMessage:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            PrivateChannel  channel=author.openPrivateChannel().complete();
            if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
            channel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    default void lsSendMessage2(User author, Message message){
        String fName="[lsSendMessage:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            PrivateChannel  channel=author.openPrivateChannel().complete();
            if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
            channel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsSendMessage(User author, Message message){
        String fName="[lsSendMessage:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            PrivateChannel  channel=author.openPrivateChannel().complete();
            if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
            channel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, Message message){
        String fName="[lsSendMessageStatus:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author,message)!=null;
    }
    static Message lsSendMessageResponse(User author, Message message){
        String fName="[lsSendMessageResponse:author,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            PrivateChannel  channel=author.openPrivateChannel().complete();
            if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
            return channel.sendMessage(message).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, MessageBuilder messageBuilder){
        String fName="[lsSendMessage:author,messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            lsSendMessage(author,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, MessageBuilder messageBuilder){
        String fName="[lsSendMessageStatus:author,messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author,messageBuilder)!=null;
    }
    static Message lsSendMessageResponse(User author, MessageBuilder messageBuilder){
        String fName="[lsSendMessageResponse:author,messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(author,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, MessageEmbed messageEmbed){
        String fName="[lsSendMessage:author,messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(author,new MessageBuilder().setEmbeds(messageEmbed));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, MessageEmbed messageEmbed){
        String fName="[lsSendMessageStatus:author,messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author,messageEmbed)!=null;
    }
    static Message lsSendMessageResponse(User author, MessageEmbed messageEmbed){
        String fName="[lsSendMessageResponse:author,messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(author,new MessageBuilder().setEmbeds(messageEmbed).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessage:author,messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(author,new MessageBuilder().setEmbeds(messageEmbed).setContent(str));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessageStatus:author,messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author,messageEmbed,str)!=null;
    }
    static Message lsSendMessageResponse(User author, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessageResponse:author,messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(author,new MessageBuilder().setEmbeds(messageEmbed).setContent(str).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, EmbedBuilder embedBuilder){
        String fName="[lsSendMessage:author,embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(author,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageStatus:author,embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author, embedBuilder)!=null;
    }
    static Message lsSendMessageResponse(User author, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageResponse:author,embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(author,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessage:author,embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(author,embedBuilder.build(),str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessageStatus:author,embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author, embedBuilder,str)!=null;
    }
    static Message lsSendMessageResponse(User author, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessageResponse:author,embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(author,embedBuilder.build(),str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(User author, String messageString){
        String fName="[lsSendMessage:author,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(author,new MessageBuilder().setContent(messageString));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(User author, String messageString){
        String fName="[lsSendMessageStatus:author,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(author,messageString) != null;
    }
    static Message lsSendMessageResponse(User author, String messageString){
        String fName="[lsSendMessageResponse:author,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return  lsSendMessageResponse(author,new MessageBuilder().setContent(messageString).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsSendMessage(TextChannel textChannel, Message message){
        String fName="[lsSendMessage:textChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            textChannel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(TextChannel textChannel, Message message){
        String fName="[lsSendMessageStatus:textChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(textChannel,message)!=null;
    }
    static Message lsSendMessageResponse(TextChannel textChannel, Message message){
        String fName="[lsSendMessageResponse:textChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            return textChannel.sendMessage(message).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(TextChannel textChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessage:textChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(textChannel,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(TextChannel textChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessageStatus:textChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(textChannel,messageBuilder)!=null;
    }
    static Message lsSendMessageResponse(TextChannel textChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessageResponse:textChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(textChannel,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(TextChannel textChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessage:textChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(textChannel,new MessageBuilder().setEmbeds(messageEmbed));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(TextChannel textChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessageStatus:textChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(textChannel,messageEmbed) != null;
    }
    static Message lsSendMessageResponse(TextChannel textChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessageResponse:textChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(textChannel,new MessageBuilder().setEmbeds(messageEmbed).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(TextChannel textChannel, EmbedBuilder embedBuilder){
        String fName="[lsSendMessage:textChannel, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(textChannel,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(TextChannel textChannel, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageStatus:textChannel, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(textChannel,embedBuilder) != null;
    }
    static Message lsSendMessageResponse(TextChannel textChannel, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageResponse:textChannel, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(textChannel,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(TextChannel textChannel, String messageString){
        String fName="[lsSendMessage:textChannel,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(textChannel,new MessageBuilder().setContent(messageString));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(TextChannel textChannel, String messageString){
        String fName="[lsSendMessageStatus:textChannel,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(textChannel,messageString) != null;
    }
    static Message lsSendMessageResponse(TextChannel textChannel, String messageString){
        String fName="[lsSendMessageResponse:textChannel,messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(textChannel,new MessageBuilder().setContent(messageString).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsSendMessage(MessageChannel messageChannel, Message message){
        String fName="[lsSendMessage:messageChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            messageChannel.sendMessage(message).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(MessageChannel messageChannel, Message message){
        String fName="[lsSendMessageStatus:messageChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(messageChannel,message)!=null;
    }
    static Message lsSendMessageResponse(MessageChannel messageChannel, Message message){
        String fName="[lsSendMessageResponse:messageChannel,message]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            return messageChannel.sendMessage(message).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(MessageChannel messageChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessage:messageChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            lsSendMessage(messageChannel,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(MessageChannel messageChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessageStatus:messageChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(messageChannel,messageBuilder)!=null;
    }
    static Message lsSendMessageResponse(MessageChannel messageChannel, MessageBuilder messageBuilder){
        String fName="[lsSendMessageResponse:messageChannel, messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(messageChannel,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(MessageChannel messageChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessage:messageChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(messageChannel,new MessageBuilder().setEmbeds(messageEmbed));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(MessageChannel messageChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessageStatus:messageChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(messageChannel,messageEmbed) != null;
    }
    static Message lsSendMessageResponse(MessageChannel messageChannel, MessageEmbed messageEmbed){
        String fName="[lsSendMessageResponse:messageChannel, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(messageChannel,new MessageBuilder().setEmbeds(messageEmbed).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsSendMessage(Message eMessage, MessageEmbed messageEmbed){
        String fName="[lsSendMessage:eMessage, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            logger.info(fName+".init");
            lsSendMessage(eMessage.getChannel(),messageEmbed);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(Message eMessage, MessageEmbed messageEmbed){
        String fName="[lsSendMessageStatus:eMessage, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(eMessage,messageEmbed) != null;
    }
    static Message lsSendMessageResponse(Message eMessage, MessageEmbed messageEmbed){
        String fName="[lsSendMessageResponse:eMessage, messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(eMessage.getChannel(),messageEmbed);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(Message eMessage, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessage:eMessage, messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(eMessage.getChannel(),new MessageBuilder().setEmbeds(messageEmbed).setContent(str));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(Message eMessage, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessageStatus:eMessage, messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(eMessage,messageEmbed,str) != null;
    }
    static Message lsSendMessageResponse(Message eMessage, MessageEmbed messageEmbed,String str){
        String fName="[lsSendMessageResponse:eMessage, messageEmbed,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(eMessage.getChannel(),new MessageBuilder().setEmbeds(messageEmbed).setContent(str));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(Message eMessage, EmbedBuilder embedBuilder){
        String fName="[lsSendMessage:eMessage, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(eMessage,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(Message eMessage, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageStatus:eMessage, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(eMessage,embedBuilder) != null;
    }
    static Message lsSendMessageResponse(Message eMessage, EmbedBuilder embedBuilder){
        String fName="[lsSendMessageResponse:eMessage, embedBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return  lsSendMessageResponse(eMessage,embedBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(Message eMessage, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessage:eMessage, embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(eMessage,embedBuilder.build(),str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(Message eMessage, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessageStatus:eMessage, embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(eMessage,embedBuilder,str) != null;
    }
    static Message lsSendMessageResponse(Message eMessage, EmbedBuilder embedBuilder,String str){
        String fName="[lsSendMessageRespinse:eMessage, embedBuilder,str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(eMessage,embedBuilder.build(),str);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage(Message eMessage, String messageString){
        String fName="[lsSendMessage:eMessage, messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsSendMessage(eMessage.getChannel(),new MessageBuilder().setContent(messageString));
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsSendMessageStatus(Message eMessage, String messageString){
        String fName="[lsSendMessageStatus:eMessage, messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        return lsSendMessageResponse(eMessage,messageString) != null;
    }
    static Message lsSendMessageResponse(Message eMessage, String messageString){
        String fName="[lsSendMessageResponse:eMessage, messageString]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return lsSendMessageResponse(eMessage.getChannel(),new MessageBuilder().setContent(messageString).build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    static void lsSendMessageWithDelete(lcGlobalHelper global, TextChannel textChannel, MessageEmbed messageEmbed){
        String fName="lsSendMessageWithDelete2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,messageEmbed);
            lsAddDeleteReaction(global,message);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static Message lsSendMessageResponse_withReactionNotification(User author, EmbedBuilder embedBuilder){
        String fName="lsSendPrivateMessageResponse1_withReactionNotification";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            return lsSendMessageResponse(author, embedBuilder, lsGlobalHelper.strPrivateMessageReactionNotification);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static void lsSendMessageWithDelete(lcGlobalHelper global,TextChannel textChannel, EmbedBuilder embedBuilder){
        String fName="lsSendMessageWithDelete3";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,embedBuilder);
            lsAddDeleteReaction(global,message);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsAddDeleteReaction(lcGlobalHelper global,Message message){
        String fName="lsAddDeleteReaction";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsAddDeleteReaction(global,message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        lsMessageClearReactionsQueue(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }


    static void lsSendMessageWithDelete(lcGlobalHelper global,TextChannel textChannel, String messageString){
        String fName="lsSendMessageWithDelete2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,messageString);
            lsAddDeleteReaction(global,message);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsSendMessageWithDeleteAndAutoDelete(lcGlobalHelper global,TextChannel textChannel, String messageString){
        String fName="lsSendMessageWithDeleteAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,messageString);
            lsAddDeleteReactionAndAutoDelete(global,message);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsAddDeleteReactionAndAutoDelete(lcGlobalHelper global,Message message){
        String fName="lsAddDeleteReactionAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsAddDeleteReactionAndAutoDelete(lcGlobalHelper global,Message message,TimeUnit timeUnit,long timeout){
        String fName="lsAddDeleteReactionAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },timeout,timeUnit, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsSendMessageWithDeleteAndAutoDelete(lcGlobalHelper global,TextChannel textChannel, String messageString,long seconds){
        String fName="lsSendMessageWithDeleteAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,messageString);
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },seconds, TimeUnit.SECONDS, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsSendMessageWithDeleteAndAutoDelete(lcGlobalHelper global,TextChannel textChannel, EmbedBuilder embedBuilder){
        String fName="lsSendMessageWithDeleteAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,embedBuilder);
            lsAddDeleteReactionAndAutoDelete(global,message);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsSendMessageWithDeleteAndAutoDelete(lcGlobalHelper global,TextChannel textChannel, EmbedBuilder embedBuilder,long seconds){
        String fName="lsSendMessageWithDeleteAndAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            Message message=lsSendMessageResponse(textChannel,embedBuilder);
             lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },seconds, TimeUnit.SECONDS, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static TextChannel lsGetFirstTextChannelByName(Guild guild, String channelName){
        String fName="getFirstTextChannelByName1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return  guild.getTextChannelsByName(channelName,true).get(0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(Guild guild, String channelId){
        String fName="getTextChannelById1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            channelId=channelId.replaceAll("<#","").replaceAll(">","");
            return  guild.getTextChannelById(channelId);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(Guild guild, long id){
        String fName="getTextChannelsByName1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return  guild.getTextChannelById(id);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static List<TextChannel> lsGetTextChannelsByName(Guild guild, String channelName){
        String fName="getTextChannelsByName1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return  guild.getTextChannelsByName(channelName,true);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(List<JDA>jdas,String channelId){
        String fName="getPrivateChannelById";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            for(JDA jda:jdas){
                PrivateChannel channel=jda.getPrivateChannelById(channelId);
                if(channel!=null){
                    return channel;
                }
            }
            return  null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(List<JDA>jdas, long id){
        String fName="getPrivateChannelById";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            for(JDA jda:jdas){
                PrivateChannel channel=jda.getPrivateChannelById(id);
                if(channel!=null){
                    return channel;
                }
            }
            return  null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsSetAutoDelete(lcGlobalHelper global,Message message){
        String fName=" lsSetAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsSetAutoDelete(lcGlobalHelper global,Message message,TimeUnit timeUnit,long timeout){
        String fName="lsSetAutoDelete";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsSetAutoDelete(global,message,timeUnit,timeout);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },timeout,timeUnit, () -> {
                        lsMessageDelete(message);
                    });
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }

    static Message lsGetMessageById(TextChannel textChannel, long messageId){
        String fName="[lsGetMessageById:textChannel,messageId_long]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return textChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(TextChannel textChannel, String messageId){
        String fName="[lsGetMessageById:textChannel,messageId_str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return textChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(Guild guild, long messageId){
        String fName="[lsGetMessageById:guild,messageId_long]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            List<TextChannel>channels=guild.getTextChannels();
            if(channels.isEmpty())throw  new Exception("zero received textChannel!");
            for (TextChannel textChannel : channels) {
                TextChannel channel;
                channel = textChannel;
                Message message = channel.retrieveMessageById(messageId).complete();
                if (message != null) {
                    return message;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(Guild guild, String messageId){
        String fName="[lsGetMessageById:guild,messageId_str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            List<TextChannel>channels=guild.getTextChannels();
            if(channels.isEmpty())throw  new Exception("zero received textChannel!");
            for (TextChannel textChannel : channels) {
                TextChannel channel;
                channel = textChannel;
                Message message = channel.retrieveMessageById(messageId).complete();
                if (message != null) {
                    return message;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(Guild guild, long textchannelId,long messageId){
        String fName="[lsGetMessageById:guild,textchannelId_long,messageId_long]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            TextChannel textChannel=guild.getTextChannelById(textchannelId);
            if(textChannel==null)throw  new Exception("failed to receive textChannel!");
            return textChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(Guild guild, String textchannelId,String messageId){
        String fName="[lsGetMessageById:guild,textchannelId_str,messageId_str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            TextChannel textChannel=guild.getTextChannelById(textchannelId);
            if(textChannel==null)throw  new Exception("failed to receive textChannel!");
            return textChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(PrivateChannel privateChannel, long messageId){
        String fName="[lsGetMessageById:privateChannel,messageId_long]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return privateChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Message lsGetMessageById(PrivateChannel privateChannel, String messageId){
        String fName="[lsGetMessageById:privateChannel,messageId_str]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return privateChannel.retrieveMessageById(messageId).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsEditMessage(Message message,Message newMessage){
        String fName="[lsEditMessage:message,newMessage]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            message.editMessage(newMessage).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,MessageBuilder messageBuilder){
        String fName="[lsEditMessage:message,messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            lsEditMessage(message,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,String text){
        String fName="[lsEditMessage:message,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            message.editMessage(text).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,MessageEmbed messageEmbed){
        String fName="[lsEditMessage:message,messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            message.editMessageEmbeds(messageEmbed).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,MessageEmbed messageEmbed,String text){
        String fName="[lsEditMessage:message,messageEmbed,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            message.editMessageEmbeds(messageEmbed).content(text).queue();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,EmbedBuilder embedbuilder){
        String fName="[lsEditMessage:message,embedbuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            lsEditMessage(message,embedbuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static void lsEditMessage(Message message,EmbedBuilder embedbuilder,String text){
        String fName="[lsEditMessage:message,embedbuilder,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            lsEditMessage(message,embedbuilder.build(),text);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Message lsEditMessageResponse(Message message,Message newMessage){
        String fName="[lsEditMessageResponse:message,newMessage]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return message.editMessage(newMessage).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,MessageBuilder messageBuilder){
        String fName="[lsEditMessageResponse:message,messageBuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return lsEditMessageResponse(message,messageBuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,String text){
        String fName="[lsEditMessageResponse:message,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return message.editMessage(text).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,MessageEmbed messageEmbed){
        String fName="[lsEditMessageResponse:message,messageEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return message.editMessageEmbeds(messageEmbed).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,EmbedBuilder embedbuilder){
        String fName="[lsEditMessageResponse:message,embedbuilder]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return lsEditMessageResponse(message,embedbuilder.build());
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,MessageEmbed messageEmbed,String text){
        String fName="[lsEditMessageResponse:message,messageEmbed,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return message.editMessageEmbeds(messageEmbed).content(text).complete();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditMessageResponse(Message message,EmbedBuilder embedbuilder,String text){
        String fName="[lsEditMessageResponse:message,embedbuilder,text]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(message.getChannelType()==ChannelType.TEXT){
                logger.info(fName+"id="+message.getId()+", guild="+message.getGuild().getName()+"("+message.getGuild().getId()+"), textChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }else{
                logger.info(fName+"id="+message.getId()+"privateChannel="+message.getChannel().getName()+"("+message.getChannel().getId()+")");
            }
            return lsEditMessageResponse(message,embedbuilder.build(),text);
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    static void lsEditMessage(long id,TextChannel textChannel,String text){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            textChannel.editMessageById(id,text).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsEditMessage(long id,TextChannel textChannel,EmbedBuilder embedbuilder){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            textChannel.editMessageEmbedsById(id,embedbuilder.build()).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsEditMessage(long id,TextChannel textChannel,MessageEmbed messageEmbed){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            textChannel.editMessageEmbedsById(id,messageEmbed).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static Message lsEditMessageResponse(long id,TextChannel textChannel,String text){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return textChannel.editMessageById(id,text).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return  null;
        }
    }
    static Message lsEditMessageResponse(long id,TextChannel textChannel,EmbedBuilder embedbuilder){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return textChannel.editMessageEmbedsById(id,embedbuilder.build()).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return  null;
        }
    }
    static Message lsEditMessageResponse(long id,TextChannel textChannel,MessageEmbed messageEmbed){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return textChannel.editMessageEmbedsById(id,messageEmbed).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return  null;
        }
    }

    static List<MessageReaction> lsMessageGetReactions(Message message){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            return message.getReactions();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsMessageClearReactionsQueue(Message message){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions().queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel textChannel, long id){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel channel, String id){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id).queue();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(Message message){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions().complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel textChannel, long id){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel channel, String id){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static boolean lsMessageClearReactionsStatus(Message message){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return false;
            }
            message.clearReactions().complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel textChannel,long id){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            textChannel.clearReactionsById(id).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel channel, String id){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            channel.clearReactionsById(id).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static Message lsMessageClearReactionsResponse(Message message){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return null;
            }
            message.clearReactions().complete();
            return  message;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel textChannel,long id){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            textChannel.clearReactionsById(id).complete();
            return  textChannel.retrieveMessageById(id).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel channel, String id){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            channel.clearReactionsById(id).complete();
            return  channel.retrieveMessageById(id).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsMessageClearReactionsQueue(Message message, Emote emote){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel textChannel, long id, Emote emote){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel channel, String id,Emote emote){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id,emote).queue();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(Message message, Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions(emote).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel textChannel, long id, Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id,emote).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel channel, String id,Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id,emote).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static boolean lsMessageClearReactionsStatus(Message message,Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return false;
            }
            message.clearReactions(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel textChannel,long id,Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            textChannel.clearReactionsById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel channel, String id,Emote emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            channel.clearReactionsById(id,emote).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static Message lsMessageClearReactionsResponse(Message message,Emote emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return null;
            }
            message.clearReactions(emote).complete();
            return  message;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel textChannel,long id,Emote emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            textChannel.clearReactionsById(id,emote).complete();
            return  textChannel.retrieveMessageById(id).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel channel, String id,Emote emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            channel.clearReactionsById(id,emote).complete();
            return  channel.retrieveMessageById(id).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsMessageClearReactionsQueue(Message message, String emote){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel textChannel, long id, String emote){
        String fName="[lsMessageClearReactionsQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactionsQueue(TextChannel channel, String id,String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id,emote).queue();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(Message message, String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return;
            }
            message.clearReactions(emote).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel textChannel, long id, String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            textChannel.clearReactionsById(id,emote).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageClearReactions(TextChannel channel, String id,String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return;
            }
            channel.clearReactionsById(id,emote).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static boolean lsMessageClearReactionsStatus(Message message,String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return false;
            }
            message.clearReactions(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel textChannel,long id,String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            textChannel.clearReactionsById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageClearReactionsStatus(TextChannel channel, String id,String emote){
        String fName="[lsMessageClearReactions]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return false;
            }
            channel.clearReactionsById(id,emote).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static Message lsMessageClearReactionsResponse(Message message,String emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            if(!message.isFromGuild()){
                logger.info(fName+"message not from guild");
                return null;
            }
            message.clearReactions(emote).complete();
            return  message;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel textChannel,long id,String emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            textChannel.clearReactionsById(id,emote).complete();
            return  textChannel.retrieveMessageById(id).complete();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message lsMessageClearReactionsResponse(TextChannel channel, String id,String emote){
        String fName="[lsMessageClearReactionsResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage){
                logger.warn(fName+"Its disabled to delete message");
                return null;
            }
            channel.clearReactionsById(id,emote).complete();
            return  channel.retrieveMessageById(id).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsMessageAddReactions(Message message,Emote emote){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.addReaction(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(Message message,String emote){
        String fName="lsEditPrivateMessage";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.addReaction(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(ReadonlyMessage readonlyMessage,Emote emote,Guild guild){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            lsMessageAddReactions(readonlyMessage,emote, guild.getTextChannelById(readonlyMessage.getChannelId()));
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(ReadonlyMessage readonlyMessage,String emote,Guild guild){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            lsMessageAddReactions(readonlyMessage,emote, guild.getTextChannelById(readonlyMessage.getChannelId()));
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(ReadonlyMessage readonlyMessage,Emote emote,TextChannel textChannel){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(readonlyMessage.getId(),emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(ReadonlyMessage readonlyMessage,String emote,TextChannel textChannel){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(readonlyMessage.getId(),emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(TextChannel textChannel,long id,Emote emote){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageAddReactions(TextChannel textChannel,long id,String emote){
        String fName="lsMessageAddReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }

    static void lsMessageRefresh4Reaction(Message message){
        String fName="[lsMessageRefresh4Reaction]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            long id=message.getIdLong();
            logger.info(fName+".id=" + id);
            if(message.isFromGuild()){
                logger.info(fName+".from guild>return");
                return;
            }
            PrivateChannel privateChannel=message.getPrivateChannel();
            if(privateChannel==null){
                logger.warn(fName+".privateChannel is null>return");
                return;
            }
            /*MessageHistory history=privateChannel.getHistory();
            logger.warn(fName+".history.size"+history.size());*/
            MessageHistory historyBefore=privateChannel.getHistoryBefore(id,2).complete();
            logger.warn(fName+".historyBefore.size"+historyBefore.size());
            MessageHistory historyAfter=privateChannel.getHistoryAfter(id,2).complete();
            logger.warn(fName+".historyAfter.size"+historyAfter.size());
            logger.warn(fName+".refreshed");
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }

    static boolean lsMessageAddReactionsStatus(Message message,Emote emote){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.addReaction(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(Message message,String emote){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.addReaction(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(ReadonlyMessage readonlyMessage,Emote emote,Guild guild){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            return lsMessageAddReactionsStatus(readonlyMessage,emote, guild.getTextChannelById(readonlyMessage.getChannelId()));
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(ReadonlyMessage readonlyMessage,String emote,Guild guild){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            return lsMessageAddReactionsStatus(readonlyMessage,emote, guild.getTextChannelById(readonlyMessage.getChannelId()));
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(ReadonlyMessage readonlyMessage,Emote emote,TextChannel textChannel){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(readonlyMessage.getId(),emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(ReadonlyMessage readonlyMessage,String emote,TextChannel textChannel){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(readonlyMessage.getId(),emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(TextChannel textChannel,long id,Emote emote){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageAddReactionsStatus(TextChannel textChannel,long id,String emote){
        String fName="lsMessageAddReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.addReactionById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static void lsMessageRemoveReactions(Message message,Emote emote){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(Message message,String emote){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(Message message,Emote emote,User user){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote,user).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(Message message,String emote,User user){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote,user).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(TextChannel textChannel, long id,Emote emote){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(TextChannel textChannel, long id,String emote){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(TextChannel textChannel, long id,Emote emote,User user){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote,user).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }
    static void lsMessageRemoveReactions(TextChannel textChannel, long id,String emote,User user){
        String fName="lsMessageRemoveReactions";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote,user).queue();
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
        }
    }

    static boolean lsMessageRemoveReactionsStatus(Message message,Emote emote){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(Message message,String emote){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(Message message,Emote emote,User user){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote,user).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(Message message,String emote,User user){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            message.removeReaction(emote,user).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(TextChannel textChannel, long id,Emote emote){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(TextChannel textChannel, long id,String emote){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(TextChannel textChannel, long id,Emote emote,User user){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote,user).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static boolean lsMessageRemoveReactionsStatus(TextChannel textChannel, long id,String emote,User user){
        String fName="lsMessageRemoveReactionsStatus";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            textChannel.removeReactionById(id,emote,user).complete();
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static String lsSplitMessage(@NotNull String content, String filter, int index){
        String fName="lsSplitMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);String lsSplitMessageFilter=" ";
        if(filter==null)filter=lsSplitMessageFilter;
        if(index<0) return content;
        String[] tmp=content.split(filter);
        if(index>=tmp.length) return content;
        return tmp[index];
    }
    static String lsSplitMessage(@NotNull String content, int index){
        String fName="lsSplitMessage2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        if(index<0) return content;String lsSplitMessageFilter=" ";
        String[] tmp=content.split(lsSplitMessageFilter);
        if(index>=tmp.length) return content;
        return tmp[index];
    }
    static String[] lsSplitMessage(@NotNull String content, String filter){
        String fName="lsSplitMessage3";
        Logger logger = Logger.getLogger(lsMessageHelper.class);String lsSplitMessageFilter=" ";
        if(filter==null) filter=lsSplitMessageFilter;
        return content.split(filter);
    }
    static String[] lsSplitMessage(@NotNull String content){
        String fName="lsSplitMessage4";String lsSplitMessageFilter=" ";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return content.split(lsSplitMessageFilter);
    }

    static  void lsReplyMissingRequirement(CommandEvent event, String command, String requires){
        String fName="lsReplyMissingRequirement";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return;
            }
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(8388863);
            embed.setTitle("Command cant be executed!");
            embed.setDescription("**Command:** "+command+"\n**Requires:** "+requires);
            MessageEmbed messageEmbed=embed.build();
            event.reply(messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
        }
    }


    static  void lsSendQuickEmbedMessage(User user, String title, String desc, Color color){
        String fName="[quickEmbedPrivateResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse(user, title, desc, color);
    }
    static  void lsSendQuickEmbedMessage(User user, String title, String desc, int color){
        String fName="[quickEmbedPrivateResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse(user, title, desc, color);
    }
    static  Boolean lsSendQuickEmbedMessageStatus(User user, String title, String desc, Color color){
        String fName="[quickEmbedPrivateResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse(user, title, desc, color)!=null;
    }
    static  Boolean lsSendQuickEmbedMessageStatus(User user, String title, String desc, int color){
        String fName="[quickEmbedPrivateResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse(user, title, desc, color)!=null;
    }
    static  Message lsSendQuickEmbedMessageResponse(User user, String title, String desc, int color){
        String fName="[quickEmbedPrivateResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color>-1){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(user,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  Message lsSendQuickEmbedMessageResponse(User user, String title, String desc, Color color){
        String fName="[quickEmbedPrivateResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color!=null){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(user,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static  void lsSendQuickEmbedMessageWithAuthor(User user, String title, String desc, Color color){
        String fName="[quickEmbedPrivateResponseWithAuthor]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponseWithAuthor(user, title, desc, color);
    }
    static  Message lsSendQuickEmbedMessageResponseWithAuthor(User user, String title, String desc, Color color){
        String fName="[lsSendQuickEmbedMessageResponseWithAuthor]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            embed.setAuthor(user.getName(),null,lsUserHelper.getAuthorIcon(user));
            if(color!=null){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(user,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  void lsSendQuickEmbedMessageWithAuthor(TextChannel textChannel,User user, String title, String desc, Color color){
        String fName="[quickEmbedPrivateResponseWithAuthor]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponseWithAuthor(textChannel,user, title, desc, color);
    }
    static  Message lsSendQuickEmbedMessageResponseWithAuthor(TextChannel textChannel,User user, String title, String desc, Color color){
        String fName="[lsSendQuickEmbedMessageResponseWithAuthor]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            embed.setAuthor(user.getName(),null,lsUserHelper.getAuthorIcon(user));
            if(color!=null){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(textChannel,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static void lsSendQuickEmbedMessage(TextChannel channel, String title, String desc, int color){
        String fName="[quickChannelResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse(channel,title,desc, color);
    }
    static void lsSendQuickEmbedMessage(TextChannel channel, String title, String desc, Color color){
        String fName="[quickChannelResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse(channel,title,desc, color);
    }
    static Boolean lsSendQuickEmbedMessageStatus(TextChannel channel, String title, String desc, int color){
        String fName="[quickChannelResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse(channel,title,desc, color)!=null;
    }
    static Boolean lsSendQuickEmbedMessageStatus(TextChannel channel, String title, String desc, Color color){
        String fName="[quickChannelResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse(channel,title,desc, color)!=null;
    }
    static Message lsSendQuickEmbedMessageResponse(TextChannel channel, String title, String desc, int color){
        String fName="[quickChannelResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color>-1){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return channel.sendMessageEmbeds(messageEmbed).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return  null;
        }
    }
    static Message lsSendQuickEmbedMessageResponse(TextChannel channel, String title, String desc, Color color){
        String fName="[quickChannelResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color!=null){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return channel.sendMessageEmbeds(messageEmbed).complete();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return  null;
        }
    }

    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,TextChannel channel, String title, String desc, Color color){
        lsSendQuickEmbedMessageWithDeleteResponse(global,false,null,channel, title, desc, color);
    }
    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,TextChannel channel,  EmbedBuilder embedBuilder){
        lsSendQuickEmbedMessageWithDeleteResponse(global,false,null,channel, embedBuilder);
    }
    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,Member author,TextChannel channel, String title, String desc, Color color){
        lsSendQuickEmbedMessageWithDeleteResponse(global,false,author,channel, title, desc, color);
    }
    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,Member author,TextChannel channel, EmbedBuilder embedBuilder){
        lsSendQuickEmbedMessageWithDeleteResponse(global,false,author,channel, embedBuilder);
    }
    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,boolean everyone,TextChannel channel, String title, String desc, Color color){
        lsSendQuickEmbedMessageWithDeleteResponse(global,everyone,null,channel, title, desc, color);
    }
    static void lsSendQuickEmbedMessageWithDelete(lcGlobalHelper global,boolean everyone,TextChannel channel, EmbedBuilder embedBuilder){
        lsSendQuickEmbedMessageWithDeleteResponse(global,everyone,null,channel, embedBuilder);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,TextChannel channel, String title, String desc, Color color){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,false,null,channel, title, desc, color);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,TextChannel channel, EmbedBuilder embedBuilder){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,false,null,channel, embedBuilder);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,Member author,TextChannel channel, String title, String desc, Color color){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,false,author,channel, title, desc, color);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,Member author,TextChannel channel, EmbedBuilder embedBuilder){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,false,author,channel, embedBuilder);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,boolean everyone,TextChannel channel, String title, String desc, Color color){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,everyone,null,channel, title, desc, color);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,boolean everyone,TextChannel channel, EmbedBuilder embedBuilder){
        return lsSendQuickEmbedMessageWithDeleteResponse(global,everyone,null,channel, embedBuilder);
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,boolean everyone,Member author,TextChannel channel, String title, String desc, Color color){
        String fName="[quickChannelResponseResponseWithDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            Message message=lsSendQuickEmbedMessageResponse(channel, title, desc,color);
            lsAddDeleteReaction(global,message);
            return message;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return  null;

        }
    }
    static Message lsSendQuickEmbedMessageWithDeleteResponse(lcGlobalHelper global,boolean everyone,Member author,TextChannel channel,EmbedBuilder embedBuilder){
        String fName="[quickChannelResponseResponseWithDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(llGlobalHelper.llIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            Message message=channel.sendMessageEmbeds(embedBuilder.build()).complete();
            lsMessageAddReactions(message,global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()
                            &&isMemberAllowed2UseTheBombReaction(everyone,e.getMember(),author)
                    ),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                lsMessageDelete(message);
                            }else{
                                lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        lsMessageClearReactionsQueue(message);
                    });

            return message;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return  null;

        }
    }

    static boolean isMemberAllowed2UseTheBombReaction(boolean everyone,Member user,Member author){
        String fName="[isMemberAllowed2UseTheBombReaction]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(everyone){
                logger.info(fName+".everyone>true");
                return true;
            }
            if(author!=null){
                logger.info(fName+".author not null");
                if(user.getIdLong()==author.getIdLong()){
                    logger.info(fName+".equal>true");
                    return true;
                }
            }
            if(user==null){
                logger.info(fName+".no user>false");
                return false;
            }
            if(lsMemberHelper.lsMemberHasPermission_MANAGESERVER(user)||lsMemberHelper.lsMemberHasPermission_MESSAGEMANAGE(user)){
                logger.info(fName+".allowed");
                return true;
            }
            if(lsMemberHelper.lsMemberIsBotOwner(user)){
                logger.info(fName+".allowed as its bot owner");
                return true;
            }
            logger.info(fName+".denied");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return  false;
        }
    }

    static void lsMessageDelete(CommandEvent event){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(event);
    }
    static void lsMessageDeleteQueue(CommandEvent event){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            lsMessageDelete(event.getMessage());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(CommandEvent event){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            return lsMessageDeleteStatus(event.getMessage());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static void lsMessageDelete(Message message){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(message);
    }
    static void lsMessageDeleteQueue(Message message){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(message==null)throw  new Exception("can't delete null");
            if(message.isEphemeral()) throw  new Exception("can't delete ephemeral");
            message.delete().queue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(Message message){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(message==null)throw  new Exception("can't delete null");
            if(message.isEphemeral()) throw  new Exception("can't delete ephemeral");
            message.delete().complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static void lsMessageDelete(PrivateChannel channel, String id){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(channel,id);
    }
    static void lsMessageDeleteQueue(PrivateChannel channel, String id){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==null||id.isBlank())throw  new Exception("can't delete id null/blank");
            channel.deleteMessageById(id).queue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(PrivateChannel channel, String id){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==null||id.isBlank())throw  new Exception("can't delete id null/blank");
            channel.deleteMessageById(id).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static void lsMessageDelete(TextChannel channel, String id){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(channel,id);
    }
    static void lsMessageDeleteQueue(TextChannel channel, String id){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==null||id.isBlank())throw  new Exception("can't delete id null/blank");
            channel.deleteMessageById(id).queue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(TextChannel channel, String id){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==null||id.isBlank())throw  new Exception("can't delete id null/blank");
            channel.deleteMessageById(id).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static void lsMessageDelete(PrivateChannel channel, long id){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(channel, id);
    }
    static void lsMessageDeleteQueue(PrivateChannel channel, long id){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==0L)throw  new Exception("can't delete id zero");
            channel.deleteMessageById(id).queue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(PrivateChannel channel, long id){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==0L)throw  new Exception("can't delete id zero");
            channel.deleteMessageById(id).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }
    static void lsMessageDelete(TextChannel channel, long id){
        String fName="[lsMessageDelete]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsMessageDeleteQueue(channel,id);
    }
    static void lsMessageDeleteQueue(TextChannel channel,long id){
        String fName="[lsMessageDeleteQueue]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try {
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==0)throw  new Exception("can't delete id zero");
            channel.deleteMessageById(id).queue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static Boolean lsMessageDeleteStatus(TextChannel channel, long id){
        String fName="[lsMessageDeleteStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2DeleteMessage)throw  new Exception("Its disabled to delete message");
            if(channel==null)throw  new Exception("can't delete channel null");
            if(id==0)throw  new Exception("can't delete id zero");
            channel.deleteMessageById(id).complete();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static ReadonlyMessage lsSendWebhookMessageResponse(TextChannel textChannel,Member member, String message){
        String fName="lsSendWebhookMessageResponse";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            lcWebHook2 whh = new lcWebHook2();
            if(!whh.useChannel(textChannel)){
                logger.info(fName+"webhook failed to get/build");
                return  null;
            }
            if(!whh.clientOpen()){
                logger.info(fName+"webhook client faild to open");
                return  null;
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(lcWebHook2.keyContent, message);
            String avatarUrl = member.getUser().getEffectiveAvatarUrl();
            jsonObject.put(lcWebHook2.keyName, member.getEffectiveName());
            jsonObject.put(lcWebHook2.keyUserName, member.getEffectiveName());
            jsonObject.put(lcWebHook2.keyUserAvatar, avatarUrl.replaceFirst("gif","png")+"?size=512");
            logger.info(fName + ".sendwebhook");
            ReadonlyMessage readonlyMessage=whh.sendReturnWebhookMessageBuilder(jsonObject);
            logger.info(fName + ".close client");
            whh.clientClose();
            return readonlyMessage;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static ReadonlyMessage lsSendWebhookMessageResponse(TextChannel textChannel,User user, String message){
        String fName="lsSendWebhookMessageResponse";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            lcWebHook2 whh = new lcWebHook2();
            if(!whh.useChannel(textChannel)){
                logger.info(fName+"webhook failed to get/build");
                return  null;
            }
            if(!whh.clientOpen()){
                logger.info(fName+"webhook client faild to open");
                return  null;
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(lcWebHook2.keyContent, message);
            String avatarUrl = user.getEffectiveAvatarUrl();
            jsonObject.put(lcWebHook2.keyName, user.getName());
            jsonObject.put(lcWebHook2.keyUserName,user.getName());
            jsonObject.put(lcWebHook2.keyUserAvatar, avatarUrl.replaceFirst("gif","png")+"?size=512");
            jsonObject.put(lcWebHook2.keyAvatar,avatarUrl.replaceFirst("gif","png")+"?size=512");
            logger.info(fName + ".sendwebhook");
            ReadonlyMessage readonlyMessage=whh.sendReturnWebhookMessageBuilder(jsonObject);
            logger.info(fName + ".close client");
            whh.clientClose();
            return readonlyMessage;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static ReadonlyMessage lsSendWebhookMessageResponse(TextChannel textChannel,JSONObject webhookmessageBuilder){
        String fName="lsSendWebhookMessageResponse";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            lcWebHook2 whh = new lcWebHook2();
            if(!whh.useChannel(textChannel)){
                logger.info(fName+"webhook failed to get/build");
                return  null;
            }
            if(!whh.clientOpen()){
                logger.info(fName+"webhook client faild to open");
                return  null;
            }
            logger.info(fName + ".sendwebhook");
            ReadonlyMessage readonlyMessage=whh.sendReturnWebhookMessageBuilder(webhookmessageBuilder);
            logger.info(fName + ".close client");
            whh.clientClose();
            return readonlyMessage;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static ReadonlyMessage lsSendWebhookMessageResponse(TextChannel textChannel, WebhookMessageBuilder webhookMessageBuilder){
        String fName="lsSendWebhookMessageResponse";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            lcWebHook2 whh = new lcWebHook2();
            if(!whh.useChannel(textChannel)){
                logger.info(fName+"webhook failed to get/build");
                return  null;
            }
            if(!whh.clientOpen()){
                logger.info(fName+"webhook client faild to open");
                return  null;
            }
            logger.info(fName + ".sendwebhook");
            ReadonlyMessage readonlyMessage=whh.sendReturnMessage(webhookMessageBuilder);
            logger.info(fName + ".close client");
            whh.clientClose();
            return readonlyMessage;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    static void lsSendMessage_Redirect(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_Redirect(author,messageEmbed,failback);
    }
    static Boolean lsSendMessageStatus_Redirect(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessageStatus1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_Redirect(author,messageEmbed,failback)!=null;
    }
    static Message lsSendMessageResponse_Redirect(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessageResponse1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                return channel.sendMessageEmbeds(messageEmbed).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessageEmbeds(messageEmbed).complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage_Redirect(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_Redirect(author, embedBuilder,failback);
    }
    static Boolean lsSendMessageStatus_Redirect(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessageStatus1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_Redirect(author, embedBuilder,failback)!=null;
    }
    static Message lsSendMessageResponse_Redirect(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessageResponse1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            MessageEmbed messageEmbed=embedBuilder.build();
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                Message message=channel.sendMessageEmbeds(messageEmbed).complete();
                return channel.sendMessageEmbeds(messageEmbed).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessageEmbeds(messageEmbed).complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage_Redirect(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessag2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_Redirect(author,messageString,failback);
    }
    static Boolean lsSendMessageStatus_Redirect(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessageStatus2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_Redirect(author,messageString,failback) != null;
    }
    static Message lsSendMessageResponse_Redirect(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessageResponse2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                return channel.sendMessage(messageString).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessage(messageString).complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsSendMessage_CouldNotSendDM(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_CouldNotSendDM(author,messageEmbed,failback);
    }
    static Boolean lsSendMessageStatus_CouldNotSendDM(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessageStatus1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_CouldNotSendDM(author,messageEmbed,failback)!=null;
    }
    static Message lsSendMessageResponse_CouldNotSendDM(User author, MessageEmbed messageEmbed,TextChannel failback){
        String fName="lsSendPrivateMessageResponse1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                return channel.sendMessageEmbeds(messageEmbed).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessage("Could not send DM to "+author.getAsMention()+"!").complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage_CouldNotSendDM(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_CouldNotSendDM(author, embedBuilder,failback);
    }
    static Boolean lsSendMessageStatus_CouldNotSendDM(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessageStatus1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_CouldNotSendDM(author, embedBuilder,failback)!=null;
    }
    static Message lsSendMessageResponse_CouldNotSendDM(User author, EmbedBuilder embedBuilder,TextChannel failback){
        String fName="lsSendPrivateMessageResponse1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            MessageEmbed messageEmbed=embedBuilder.build();
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                return channel.sendMessageEmbeds(messageEmbed).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessage("Could not send DM to "+author.getAsMention()+"!").complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void lsSendMessage_CouldNotSendDM(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessag2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendMessageResponse_CouldNotSendDM(author,messageString,failback);
    }
    static Boolean lsSendMessageStatus_CouldNotSendDM(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessageStatus2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendMessageResponse_CouldNotSendDM(author,messageString,failback) != null;
    }
    static Message lsSendMessageResponse_CouldNotSendDM(User author, String messageString,TextChannel failback){
        String fName="lsSendPrivateMessageResponse2";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            try {
                PrivateChannel  channel=author.openPrivateChannel().complete();
                if(sendTyingBeforeMessage4Private)channel.sendTyping().complete();
                return channel.sendMessage(messageString).complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return failback.sendMessage("Could not send DM to "+author.getAsMention()+"!").complete();
            }
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static  void lsSendQuickEmbedMessage_Redirect(User user, String title, String desc, Color color,TextChannel failback){
        String fName="[quickEmbedPrivateResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse_Redirect(user, title, desc, color,failback);
    }
    static  void lsSendQuickEmbedMessage_Redirect(User user, String title, String desc, int color,TextChannel failback){
        String fName="[quickEmbedPrivateResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        lsSendQuickEmbedMessageResponse_Redirect(user, title, desc, color,failback);
    }
    static  Boolean lsSendQuickEmbedMessageStatus_Redirect(User user, String title, String desc, Color color,TextChannel failback){
        String fName="[quickEmbedPrivateResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse_Redirect(user, title, desc, color,failback)!=null;
    }
    static  Boolean lsSendQuickEmbedMessageStatus_Redirect(User user, String title, String desc, int color,TextChannel failback){
        String fName="[quickEmbedPrivateResponseStatus]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickEmbedMessageResponse_Redirect(user, title, desc, color,failback)!=null;
    }
    static  Message lsSendQuickEmbedMessageResponse_Redirect(User user, String title, String desc, int color,TextChannel failback){
        String fName="[quickEmbedPrivateResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color>-1){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse_Redirect(user,messageEmbed,failback);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  Message lsSendQuickEmbedMessageResponse_Redirect(User user, String title, String desc, Color color,TextChannel failback){
        String fName="[quickEmbedPrivateResponseResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            if(color!=null){
                embed.setColor(color);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse_Redirect(user,messageEmbed,failback);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }

    static  EmbedBuilder lsErrorEmbed( User user,String title, String desc, Color color){
        String fName="[lsErrorEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= new EmbedBuilder();
            embed.setThumbnail(lsAssets.errorImage);
            if(color!=null){
                embed.setColor(color);
            }else{
                embed.setColor(llColorOrange_InternationalEngineering);
            }
            if(title!=null&&!title.isEmpty()&&!title.isBlank()){
                embed.setTitle(title);
            }
            if(desc!=null&&!desc.isEmpty()&&!desc.isBlank()){
                embed.setDescription(desc);
            }
            if(user!=null){
                embed.setAuthor(user.getName(),null,lsUserHelper.getAuthorIcon(user));
            }
            return  embed;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  EmbedBuilder lsErrorEmbed( String title, String desc, Color color){
        return lsErrorEmbed( null,title, desc, color);
    }
    static  EmbedBuilder lsErrorEmbed( String title, String desc){
        return lsErrorEmbed( null,title, desc, null);
    }
    static  EmbedBuilder lsErrorEmbed( String title){
        return lsErrorEmbed( null,title, null, null);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel, User user,String title, String desc, Color color){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= lsErrorEmbed( user,title, desc, color);
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(textChannel,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel, User user,String title, String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(textChannel,user,title,desc,null);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel,User user,String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(textChannel,user,null,desc);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel, String title, String desc, Color color){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(textChannel,null,title,desc,null);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel, String title, String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(textChannel,null,title,desc,null);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(TextChannel textChannel,String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(textChannel,null,null,desc);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(User user,String title, String desc, Color color){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            if(lsGlobalHelper.lsIsDisabled2SendMessage){
                logger.warn(fName+".Its disabled to send message");
                return null;
            }
            EmbedBuilder embed= lsErrorEmbed(title, desc, color);
            MessageEmbed messageEmbed=embed.build();
            return lsSendMessageResponse(user,messageEmbed);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(User user, String title, String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(user,title,desc,null);
    }
    static  Message lsSendQuickErrorEmbedMessageResponse(User user,String desc){
        String fName="[lsSendQuickErrorEmbedMessageResponse]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        return lsSendQuickErrorEmbedMessageResponse(user,null,desc);
    }

    static PrivateChannel lsOpenPrivateChannel(User author){
        String fName="lsSendPrivateMessageResponse1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName+".init");
            PrivateChannel  channel=author.openPrivateChannel().complete();
            return channel;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static void lsSendMessageTest555(Member author, TextChannel textChannel, EmbedBuilder embedBuilder, String str, SlashCommandEvent slashCommandEvent){
        String fName="lsSendPrivateMessage1";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        logger.info(fName+".init");
        try{
            MessageBuilder messageBuilder=new MessageBuilder();
            slashCommandEvent.reply("dd").setEphemeral(true);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return ;
        }

    }

    static EmbedBuilder lsGenerateEmbed(String title, String description,Color color){
        String fName="[lsGeenerateEmbed]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            if(title!=null&&!title.isBlank())embedBuilder.setTitle(title);
            if(description!=null&&!description.isBlank())embedBuilder.setDescription(description);
            if(color!=null)embedBuilder.setColor(color);
            return  embedBuilder;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsGetInteractionHook(SlashCommandEvent slashCommandEvent,boolean isEphemeral){
        String fName="[lsGetInteractionHook -slashCommandEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            InteractionHook interactionHook = null;
            if(isEphemeral)interactionHook=slashCommandEvent.deferReply(true).complete();
            else interactionHook=slashCommandEvent.deferReply().complete();
            return  interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsGetInteractionHook(SelectionMenuEvent selectionMenuEvent, boolean isEphemeral){
        String fName="[lsGetInteractionHook -selectionMenuEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            InteractionHook interactionHook = null;
            if(isEphemeral)interactionHook=selectionMenuEvent.deferReply(true).complete();
            else interactionHook=selectionMenuEvent.deferReply().complete();
            return  interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsGetInteractionHook(ButtonClickEvent buttonClickEvent, boolean isEphemeral){
        String fName="[lsGetInteractionHook -buttonClickEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            InteractionHook interactionHook = null;
            if(isEphemeral)interactionHook=buttonClickEvent.deferReply(true).complete();
            else interactionHook=buttonClickEvent.deferReply().complete();
            return  interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder){
        String fName="[lsSendEmbed2InteractionHook]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(false).sendMessageEmbeds(embedBuilder.build()).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr){
        String fName="[lsSendEmbed2InteractionHook +with additionalStr]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(false).sendMessageEmbeds(embedBuilder.build()).setContent(additionStr).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEmbed2InteractionHook +with actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(false).sendMessageEmbeds(embedBuilder.build()).addActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEmbed2InteractionHook +with additionalStr and actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(false).sendMessageEmbeds(embedBuilder.build()).setContent(additionStr).addActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditOriginEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder){
        String fName="[lsEditOriginEmbed2InteractionHook]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.editOriginalEmbeds(embedBuilder.build()).setContent(null).setActionRows().complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditOriginEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr){
        String fName="[lsEditOriginEmbed2InteractionHook +with additionalStr]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.editOriginalEmbeds(embedBuilder.build()).setActionRows().setContent(additionStr).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditOriginEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsEditOriginEmbed2InteractionHook +with actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.editOriginalEmbeds(embedBuilder.build()).setContent(null).setActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsEditOriginEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsEditOriginEmbed2InteractionHook +with additionalStr and actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.editOriginalEmbeds(embedBuilder.build()).setContent(additionStr).setActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEphemeralEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder){
        String fName="[lsSendEphemeralEmbed2InteractionHook ]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(true).sendMessageEmbeds(embedBuilder.build()).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEphemeralEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr){
        String fName="[lsSendEphemeralEmbed2InteractionHook +with additionalStr]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(true).sendMessageEmbeds(embedBuilder.build()).setContent(additionStr).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEphemeralEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEphemeralEmbed2InteractionHook +with actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(true).sendMessageEmbeds(embedBuilder.build()).addActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEphemeralEmbed(InteractionHook interactionHook,EmbedBuilder embedBuilder, String additionStr, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEphemeralEmbed2InteractionHook +with additionalStr and actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=interactionHook.setEphemeral(true).sendMessageEmbeds(embedBuilder.build()).setContent(additionStr).addActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    static Message lsSendEmbed(User user,EmbedBuilder embedBuilder){
        String fName="[lsSendEmbedToPrivate ]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=user.openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(User user,EmbedBuilder embedBuilder, String additionStr){
        String fName="[lsSendEmbedToPrivate +with additionalStr]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=user.openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).content(additionStr).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(User user,EmbedBuilder embedBuilder, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEmbedToPrivate +with actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=user.openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).setActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Message lsSendEmbed(User user,EmbedBuilder embedBuilder, String additionStr, List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows){
        String fName="[lsSendEmbedToPrivate +with additionalStr and actionRows]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            Message message=user.openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).content(additionStr).setActionRows(actionRows).complete();
            return  message;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    static InteractionHook lsDeferReply(SelectionMenuEvent selectionMenuEvent,boolean isEphemeral){
        String fName="[lsDeferReply -selectionMenuEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return  lsDeferReply(selectionMenuEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsDeferReply(ButtonClickEvent buttonClickEvent,boolean isEphemeral){
        String fName="[lsDeferReply -buttonClickEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return  lsDeferReply(buttonClickEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsDeferReply(SlashCommandEvent slashCommandEvent,boolean isEphemeral){
        String fName="[lsDeferReply -slashCommandEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return lsDeferReply(slashCommandEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsDeferReply(Interaction interaction, boolean isEphemeral){
        String fName="[lsDeferReply -interaction]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            InteractionHook interactionHook=interaction.deferReply(isEphemeral).complete();
            return  interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsReplywPleaseWait(SelectionMenuEvent selectionMenuEvent, boolean isEphemeral){
        String fName="[lsReplywPleaseWait -selectionMenuEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return lsReplywPleaseWait(selectionMenuEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsReplywPleaseWait(ButtonClickEvent buttonClickEvent, boolean isEphemeral){
        String fName="[lsReplywPleaseWait -selectionMenuEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return lsReplywPleaseWait(buttonClickEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsReplywPleaseWait(SlashCommandEvent slashCommandEvent, boolean isEphemeral){
        String fName="[lsReplywPleaseWait -selectionMenuEvent]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            return lsReplywPleaseWait(slashCommandEvent.getInteraction(),isEphemeral);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static InteractionHook lsReplywPleaseWait(Interaction interaction, boolean isEphemeral){
        String fName="[lsReplywPleaseWait -interaction]";
        Logger logger = Logger.getLogger(lsMessageHelper.class);
        try{
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(iRdStr.strPleaseWait).setColor(llColors.llColorPurple2);
            InteractionHook interactionHook=interaction.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            return interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}