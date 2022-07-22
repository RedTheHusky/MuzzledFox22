package models.ll;

import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.lsMessageHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
public interface llMessageHelper extends llColors
{
    String llSplitMessageFilter=" ";//filter="[, ?.@]+";

    default void llSendMessageWithDelete(lcGlobalHelper global,TextChannel textChannel, MessageEmbed messageEmbed){
        lsMessageHelper.lsSendMessageWithDelete(global,textChannel,messageEmbed);
    }

    default void llSendMessage(User author, EmbedBuilder embedBuilder){
        lsMessageHelper.lsSendMessage(author, embedBuilder);
    }
    default Boolean llSendMessageStatus(User author, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageStatus(author, embedBuilder);
    }
    default Message llSendMessageResponse(User author, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageResponse(author, embedBuilder);
    }
    default Message llSendMessageResponse_withReactionNotification(User author, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageResponse(author, embedBuilder, lsGlobalHelper.strPrivateMessageReactionNotification);
    }
    default Message llSendMessageResponse_withReactionNotificationOptional(User author, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageResponse(author, embedBuilder, lsGlobalHelper.strPrivateMessageReactionNotificationOptional);
    }

    default void llSendMessage(TextChannel textChannel, EmbedBuilder embedBuilder){
        lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
    }
    default Boolean llSendMessageStatus(TextChannel textChannel, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageStatus(textChannel,embedBuilder);
    }
    default Message llSendMessageResponse(TextChannel textChannel, EmbedBuilder embedBuilder){
        return  lsMessageHelper.lsSendMessageResponse(textChannel,  embedBuilder);
    }
    default void llSendMessageWithDelete(lcGlobalHelper global,TextChannel textChannel, EmbedBuilder embedBuilder){
        lsMessageHelper.lsSendMessageWithDelete(global,textChannel, embedBuilder);
    }

    default void llSendMessage(User author, String messageString){
        lsMessageHelper.lsSendMessage(author, messageString);
    }
    default Boolean llSendMessageStatus(User author, String messageString){
        return  lsMessageHelper.lsSendMessageStatus(author, messageString);
    }
    default Message llSendMessageResponse(User author, String messageString){
        return  lsMessageHelper.lsSendMessageResponse( author, messageString);
    }

    default void llSendMessageWithDelete(lcGlobalHelper global,TextChannel textChannel, String messageString){
        lsMessageHelper.lsSendMessageWithDelete( global, textChannel, messageString);
    }

    default void llSendMessage(TextChannel textChannel, String messageString){
        lsMessageHelper.lsSendMessage(textChannel,messageString);
    }
    default Boolean llSendMessageStatus(TextChannel textChannel, String messageString){
        return  lsMessageHelper.lsSendMessageStatus(textChannel, messageString);
    }
    default Message llSendMessageResponse(TextChannel textChannel, String messageString){
        return  lsMessageHelper.lsSendMessageResponse(textChannel,messageString);
    }

    default Message llGetMessageById(TextChannel channel, long id){
        return  lsMessageHelper.lsGetMessageById(channel,  id);
    }
    default Message llGetMessageById(TextChannel channel, String messageId){
        return  lsMessageHelper.lsGetMessageById( channel, messageId);
    }
    default Message llGetMessageById(Guild guild, long id){
        return  lsMessageHelper.lsGetMessageById(guild, id);
    }
    default Message llGetMessageById(Guild guild, String messageId){
        return lsMessageHelper.lsGetMessageById( guild, messageId);
    }

    default void llEditMessage(Message message,String text){
        lsMessageHelper.lsEditMessage(message, text);
    }
    default void llEditMessage(Message message,EmbedBuilder embedbuilder){
        lsMessageHelper.lsEditMessage(message,embedbuilder);
    }

    default List<MessageReaction> llMessageGetReactions(Message message){
        return  lsMessageHelper.lsMessageGetReactions(message);
    }
    default void llMessageClearReactions(Message message){
        lsMessageHelper.lsMessageClearReactionsQueue(message);
    }
    default void llMessageClearReactions(TextChannel textChannel,long id){
        lsMessageHelper.lsMessageClearReactionsQueue(textChannel,id);
    }
    default void llMessageClearReactions(TextChannel channel, String id){
        lsMessageHelper.lsMessageClearReactions(channel, id);
    }

    default void llMessageAddReactions(Message message,Emote emote){
       lsMessageHelper.lsMessageAddReactions(message,emote);
    }
    default void llMessageAddReactions(Message message,String emote){
        lsMessageHelper.lsMessageAddReactions(message, emote);
    }
    default void llMessageRemoveReactions(Message message,Emote emote){
        lsMessageHelper.lsMessageRemoveReactions(message,emote);
    }
    default void llMessageRemoveReactions(Message message,String emote){
        lsMessageHelper.lsMessageRemoveReactions( message,emote);
    }
    default void llMessageRemoveReactions(TextChannel textChannel,long id,Emote emote){
        lsMessageHelper.lsMessageRemoveReactions(textChannel,id,emote);
    }
    default void llMessageRemoveReactions(TextChannel textChannel,long id,String emote){
        lsMessageHelper.lsMessageRemoveReactions(textChannel,id,emote);
    }

    default String llSplitMessage(@NotNull String content, String filter, int index){
        String fName="llSplitMessage1";
        Logger logger = Logger.getLogger(getClass());
        if(filter==null)filter=llSplitMessageFilter;
        if(index<0) return content;
        String[] tmp=content.split(filter);
        if(index>=tmp.length) return content;
        return tmp[index];
    }
    default String llSplitMessage(@NotNull String content, int index){
        String fName="llSplitMessage2";
        Logger logger = Logger.getLogger(getClass());
        if(index<0) return content;
        String[] tmp=content.split(llSplitMessageFilter);
        if(index>=tmp.length) return content;
        return tmp[index];
    }
    default String[] llSplitMessage(@NotNull String content, String filter){
        String fName="llSplitMessage3";
        Logger logger = Logger.getLogger(getClass());
        if(filter==null) filter=llSplitMessageFilter;
        return content.split(filter);
    }
    default String[] llSplitMessage(@NotNull String content){
        String fName="llSplitMessage4";
        Logger logger = Logger.getLogger(getClass());
        return content.split(llSplitMessageFilter);
    }

    default  void llReplyMissingRequirement(CommandEvent event, String command, String requires){
        lsMessageHelper.lsReplyMissingRequirement(event, command, requires);
    }


    default  void llSendQuickEmbedMessage(User user, String title, String desc, Color color){
        lsMessageHelper.lsSendQuickEmbedMessage(user, title, desc,color);
    }
    default  void llSendQuickEmbedMessage(User user, String title, String desc, int color){
        lsMessageHelper.lsSendQuickEmbedMessage(user, title,desc,color);
    }
    default  Boolean llSendQuickEmbedMessageStatus(User user, String title, String desc, Color color){
        return lsMessageHelper.lsSendQuickEmbedMessageStatus(user, title, desc, color);
    }
    default  Boolean llSendQuickEmbedMessageStatus(User user, String title, String desc, int color){
        return lsMessageHelper.lsSendQuickEmbedMessageStatus(user, title, desc,color);
    }
    default  Message llSendQuickEmbedMessageResponse(User user, String title, String desc, int color){
        return lsMessageHelper.lsSendQuickEmbedMessageResponse(user, title, desc, color);
    }
    default  Message llSendQuickEmbedMessageResponse(User user, String title, String desc, Color color){
            return lsMessageHelper.lsSendQuickEmbedMessageResponse(user,title, desc, color);
    }

    default  void llSendQuickEmbedMessageWithAuthor(TextChannel textChannel,User user, String title, String desc, Color color){
        lsMessageHelper.lsSendQuickEmbedMessageWithAuthor(textChannel,user, title, desc,color);
    }

    default void llSendQuickEmbedMessage(TextChannel channel, String title, String desc, int color){
        lsMessageHelper.lsSendQuickEmbedMessage(channel,title,desc, color);
    }
    default void llSendQuickEmbedMessage(TextChannel channel, String title, String desc, Color color){
        lsMessageHelper.lsSendQuickEmbedMessage( channel, title, desc, color);
    }
    default Boolean llSendQuickEmbedMessageStatus(TextChannel channel, String title, String desc, int color){
        return lsMessageHelper.lsSendQuickEmbedMessageStatus(channel, title, desc,color);
    }
    default Boolean llSendQuickEmbedMessageStatus(TextChannel channel, String title, String desc, Color color){
        return  lsMessageHelper.lsSendQuickEmbedMessageStatus(channel, title,desc, color);
    }
    default Message llSendQuickEmbedMessageResponse(TextChannel channel, String title, String desc, int color){
        return lsMessageHelper.lsSendQuickEmbedMessageResponse(channel, title,desc,color);
    }
    default Message llSendQuickEmbedMessageResponse(TextChannel channel, String title, String desc, Color color){
        return lsMessageHelper.lsSendQuickEmbedMessageResponse(channel, title,desc, color);
    }
    default void llSendQuickEmbedMessageWithDelete(lcGlobalHelper global,Member author,TextChannel channel, String title, String desc, Color color){
            lsMessageHelper.lsSendQuickEmbedMessageWithDelete(global,author,channel, title, desc, color);
    }
    default void llSendQuickEmbedMessageWithDelete(lcGlobalHelper global,boolean everyone,TextChannel channel, String title, String desc, Color color){
        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(global,everyone,channel, title, desc, color);
    }
    default void llSendQuickEmbedMessageWithDelete(lcGlobalHelper global,TextChannel channel, String title, String desc, Color color){
        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(global,channel, title, desc, color);
    }
    default void llSendQuickEmbedMessageWithDelete(lcGlobalHelper global,Member author,TextChannel channel, EmbedBuilder embedBuilder){
        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(global,author,channel, embedBuilder);
    }

    default void llMessageDelete(CommandEvent event){
        lsMessageHelper.lsMessageDelete(event);
    }
    default Boolean llMessageDeleteStatus(CommandEvent event){
        return lsMessageHelper.lsMessageDeleteStatus(event);
    }
    default void llMessageDelete(Message message){
        lsMessageHelper.lsMessageDelete(message);
    }
    default Boolean llMessageDeleteStatus(Message message){
        return lsMessageHelper.lsMessageDeleteStatus(message);
    }
    default void llMessageDelete(PrivateChannel channel, String id){
        lsMessageHelper.lsMessageDelete(channel,id);
    }
    default Boolean llMessageDeleteStatus(PrivateChannel channel, String id){
        return lsMessageHelper.lsMessageDeleteStatus(channel, id);
    }
    default void llMessageDelete(TextChannel channel, String id){
        lsMessageHelper.lsMessageDelete(channel, id);
    }
    default Boolean llMessageDeleteStatus(TextChannel channel, String id){
        return lsMessageHelper.lsMessageDeleteStatus(channel,id);
    }
    default void llMessageDelete(PrivateChannel channel, long id){
        lsMessageHelper.lsMessageDelete(channel,id);
    }
    default Boolean llMessageDeleteStatus(PrivateChannel channel, long id){
        return lsMessageHelper.lsMessageDeleteStatus(channel,id);
    }
    default void llMessageDelete(TextChannel channel, long id){
        lsMessageHelper.lsMessageDelete(channel,id);
    }
    default Boolean llMessageDeleteStatus(TextChannel channel, long id){
        return lsMessageHelper.lsMessageDeleteStatus(channel, id);
    }

}