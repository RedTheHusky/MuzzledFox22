package models.lc.helper;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageUpdateAction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcSendMessageHelper {
    Logger logger = Logger.getLogger(getClass());
    public lcSendMessageHelper(){

    }
    private MessageChannel messageChannel=null;
    private PrivateChannel privateChannel=null;
    private TextChannel textChannel=null;
    private InteractionHook interactionHook=null;
    private Message message2Edit=null;

    List<MessageEmbed> messageEmbeds=null;
    List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows=null;
    private String content=null;
    private boolean clearEmbeds=false,clearActionRows=false,clearContent=false;

    public lcSendMessageHelper clear(){
        String fName="[clear]";
        try{
            clearInput();
            clearResult();
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearResult(){
        String fName="[clearResult]";
        try{
            editedMessage=null;
            sentMessage=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearInput(){
        String fName="[clearInput]";
        try{
            clearChannels();
            clearData();
            clearReference();
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearReference(){
        String fName="[clearReference]";
        try{
            interactionHook=null;
            message2Edit=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearData(){
        String fName="[clearData]";
        try{
            messageEmbeds=null;content=null;actionRows=null;
            clearEmbeds=false;clearActionRows=false;clearContent=false;;
            messageBuilder=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearChannels(){
        String fName="[clear]";
        try{
            messageChannel=null;
            privateChannel=null;
            textChannel=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcSendMessageHelper setMessageChannel(MessageChannel messageChannel,boolean allowNull){
        String fName="[setMessageChannel]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&messageChannel==null)throw  new Exception("Input is null!");
            if(messageChannel==null) {
                logger.info(fName+" input is null");
                this.messageChannel=null;
                return this;
            }
            clearChannels();
            this.messageChannel=messageChannel;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setTextChannel(TextChannel textChannel,boolean allowNull){
        String fName="[setTextChannel]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&textChannel==null)throw  new Exception("Input is null!");
            if(textChannel==null){
               logger.info(fName+" input is null");
               this.textChannel=null;
               return  this;
            }
            clearChannels();
            this.textChannel=textChannel;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setPrivateChannel(PrivateChannel privateChannel,boolean allowNull){
        String fName="[setPrivateChannel:privateChannel]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&privateChannel==null)throw  new Exception("Input is null!");
            if(privateChannel==null){
                logger.info(fName+" input is null");
                this.privateChannel=null;
                return this;
            }
            clearChannels();
            this.privateChannel=privateChannel;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setPrivateChannel(User user,boolean allowNull){
        String fName="[setPrivateChannel:user]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&user==null)throw  new Exception("Input is null!");
            if(user==null){
                logger.info(fName+" input is null");
                this.privateChannel=null;
                return this;
            }
            clearChannels();
            PrivateChannel privateChannel=user.openPrivateChannel().complete();
            if(privateChannel==null)throw  new Exception("Returned channel can't be null!");
            this.privateChannel=privateChannel;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setInteractionHook(InteractionHook interactionHoo,boolean allowNull){
        String fName="[setInteractionHook]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&interactionHoo==null)throw  new Exception("Input is null!");
            if(interactionHoo==null){
                logger.info(fName+" input is null");
            }
            this.interactionHook=interactionHoo;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setMessage(Message message,boolean allowNull){
        String fName="[setMessage]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&message2Edit==null)throw  new Exception("Input is null!");
            this.message2Edit=message;
            if(this.message2Edit!=null){
                logger.info(fName+" input is not null");
                clearChannels();
                messageChannel=message.getChannel();
                if(message.isFromGuild()){
                    textChannel=message.getTextChannel();
                }else{
                    privateChannel=message.getPrivateChannel();
                }
            }
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setMessageChannel(MessageChannel messageChannel){
        String fName="[setMessageChannel]";
        try{
            logger.info(fName+"exc");
            return setMessageChannel(messageChannel,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setTextChannel(TextChannel textChannel){
        String fName="[setTextChannel]";
        try{
            logger.info(fName+"exc");
           return setTextChannel(textChannel,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setPrivateChannel(PrivateChannel privateChannel){
        String fName="[setPrivateChannel:privateChannel]";
        try{
            logger.info(fName+"exc");
            return setPrivateChannel(privateChannel,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setPrivateChannel(User user){
        String fName="[setPrivateChannel:user]";
        try{
            logger.info(fName+"exc");
            return setPrivateChannel(user,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setInteractionHook(InteractionHook interactionHook){
        String fName="[setInteractionHook]";
        try{
            logger.info(fName+"exc");
            return setInteractionHook(interactionHook,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setMessage(Message message){
        String fName="[setMessage]";
        try{
            logger.info(fName+"exc");
           return setMessage(message,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearMessageChannel(){
        String fName="[clearMessageChannel]";
        try{
            logger.info(fName+"exc");
            messageChannel=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearTextChannel(){
        String fName="[clearTextChannel]";
        try{
            logger.info(fName+"exc");
            textChannel=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearPrivateChannel(){
        String fName="[clearPrivateChannel]";
        try{
            logger.info(fName+"exc");
            privateChannel=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearInteractionHook(){
        String fName="[clearInteractionHook]";
        try{
            logger.info(fName+"exc");
            interactionHook=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearMessage(){
        String fName="[clearMessage]";
        try{
            logger.info(fName+"exc");
            message2Edit=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcSendMessageHelper setEmbeds(List<MessageEmbed> messageEmbeds,boolean allowNull){
        String fName="[setEmbeds]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&messageEmbeds==null)throw  new Exception("Input is null!");
            if(messageEmbeds==null){
                logger.info(fName+" input is null");
            }
            this.messageEmbeds=messageEmbeds;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setEmbed(MessageEmbed messageEmbed,boolean allowNull){
        String fName="[setEmbed]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&messageEmbed==null)throw  new Exception("Input is null!");
            if(messageEmbed==null){
                logger.info(fName+" input is null");
            }
            this.messageEmbeds=null;
            if(messageEmbed==null){
                logger.info(fName+" input is null");
                return  this;
            }
            this.messageEmbeds=new ArrayList<>();
            this.messageEmbeds.add(messageEmbed);
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setContent(String content,boolean allowNull){
        String fName="[setContent]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&content==null)throw  new Exception("Input is null!");
            if(content==null){
                logger.info(fName+" input is null");
            }
            this.content=content;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setActionRows(List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows,boolean allowNull ){
        String fName="[setActionRows]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&actionRows==null)throw  new Exception("Input is null!");
            if(actionRows==null){
                logger.info(fName+" input is null");
            }
            this.actionRows=actionRows;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setMessageBuilder(MessageBuilder messageBuilder,boolean allowNull){
        String fName="[setMessageBuilder]";
        try{
            logger.info(fName+"allowNull="+allowNull);
            if(!allowNull&&messageBuilder==null)throw  new Exception("Input is null!");
            if(messageBuilder==null) {
                logger.info(fName+" input is null");
                this.messageBuilder=null;
                return this;
            }
            clearInput();
            this.messageBuilder=messageBuilder;
            return  this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setEmbeds(List<MessageEmbed> messageEmbeds){
        String fName="[setEmbeds]";
        try{
            logger.info(fName+"exc");
           return setEmbeds(messageEmbeds,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setEmbed(MessageEmbed messageEmbed){
        String fName="[setEmbed]";
        try{
            logger.info(fName+"exc");
            return setEmbed(messageEmbed,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setContent(String content){
        String fName="[setContent]";
        try{
            logger.info(fName+"exc");
           return setContent(content,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setActionRows(List<net.dv8tion.jda.api.interactions.components.ActionRow> actionRows ){
        String fName="[setActionRows]";
        try{
            logger.info(fName+"exc");
           return setActionRows(actionRows,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setMessageBuilder(MessageBuilder messageBuilder){
        String fName="[setMessageBuilder]";
        try{
            logger.info(fName+"exc");
            return setMessageBuilder(messageBuilder,true);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearEmbeds(){
        String fName="[clearEmbeds]";
        try{
            logger.info(fName+"exc");
            messageEmbeds=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearContent(){
        String fName="[clearContent]";
        try{
            logger.info(fName+"exc");
            content=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearActionRows(){
        String fName="[clearActionRows]";
        try{
            logger.info(fName+"exc");
            actionRows=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper clearMessageBuilder(){
        String fName="[clearMessageBuilder]";
        try{
            logger.info(fName+"exc");
            messageBuilder=null;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcSendMessageHelper setEmbedsClearFlag(boolean clear){
        String fName="[setEmbedsClearFlag]";
        try{
            logger.info(fName+"clear="+clear);
            clearEmbeds=clear;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setContentClearFlag(boolean clear){
        String fName="[setContentClearFlag]";
        try{
            logger.info(fName+"clear="+clear);
            clearContent=clear;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSendMessageHelper setActionRowsClearFlag(boolean clear){
        String fName="[setActionRowsClearFlag]";
        try{
            logger.info(fName+"clear="+clear);
            clearActionRows=clear;
            return this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean getEmbedsClearFlag(){
        String fName="[getEmbedsClearFlag]";
        try{
            logger.info(fName+"result="+clearEmbeds);
            return  clearEmbeds;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getContentClearFlag(){
        String fName="[getContentClearFlag]";
        try{
            logger.info(fName+"result="+clearContent);
            return clearContent;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getActionRowsClearFlag(){
        String fName="[getActionRowsClearFlag]";
        try{
            logger.info(fName+"result="+clearActionRows);
            return clearActionRows;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public MessageChannel getMessageChannel(){
        String fName="[getMessageChannel]";
        try{
            return this.messageChannel;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getTextChannel(){
        String fName="[getTextChannel]";
        try{
            return this.textChannel;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel(){
        String fName="[getPrivateChannel]";
        try{
            return this.privateChannel;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public User getPrivateChannelUser(){
        String fName="[getPrivateChannelUser]";
        try{
            if(this.privateChannel==null)throw  new Exception("PrivateChannel is null!");
            return this.privateChannel.getUser();
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InteractionHook getInteractionHook(){
        String fName="[getInteractionHook]";
        try{
            return this.interactionHook;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message getMessage2Edit(){
        String fName="[getMessage2Edit]";
        try{
            return this.message2Edit;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<MessageEmbed> getEmbeds(){
        String fName="[getEmbeds]";
        try{
            return this.messageEmbeds;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed getEmbed(int index){
        String fName="[getEmbed]";
        try{
            if(this.messageEmbeds==null)throw  new Exception("messageEmbeds is null!");
            if(this.messageEmbeds.isEmpty())throw  new Exception("messageEmbeds is empty!");
            logger.info(fName+".index="+index);
            if(index<0)throw  new Exception("index cant be bellow zero!");
            if(index>=this.messageEmbeds.size())throw  new Exception("index cant be equal or above list size!");
            return this.messageEmbeds.get(index);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed getEmbed(){
        String fName="[getEmbed]";
        try{
            return getEmbed(0);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getContent(){
        String fName="[getContent]";
        try{
            return this.content;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<net.dv8tion.jda.api.interactions.components.ActionRow> getActionRows(){
        String fName="[getActionRows]";
        try{
            return this.actionRows;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public net.dv8tion.jda.api.interactions.components.ActionRow getActionRow(int index){
        String fName="[getActionRows]";
        try{
            if(this.actionRows==null)throw  new Exception("actionRows is null!");
            if(this.actionRows.isEmpty())throw  new Exception("actionRows is empty!");
            logger.info(fName+".index="+index);
            if(index<0)throw  new Exception("index cant be bellow zero!");
            if(index>=this.actionRows.size())throw  new Exception("index cant be equal or above list size!");
            return this.actionRows.get(index);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageBuilder getMessageBuilder(){
        String fName="[getMessageBuilder]";
        try{
            return  this.messageBuilder;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    MessageBuilder messageBuilder=null;
    private lcSendMessageHelper _sendPreBuild(){
        String fName="[preBuild]";
        try{
            messageBuilder=new MessageBuilder();
            if(messageEmbeds!=null&&!messageEmbeds.isEmpty()){
                messageBuilder.setEmbeds(messageEmbeds);
            }
            if(content!=null&&!content.isEmpty()){
                messageBuilder.setContent(content);
            }
            if(actionRows!=null&&!actionRows.isEmpty()){
                messageBuilder.setActionRows(actionRows);
            }
            return  this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    Message message2Send=null;
    private lcSendMessageHelper _sendBuild(){
        String fName="[build]";
        try{
            message2Send=null;
            message2Send=messageBuilder.build();
            return  this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcSendMessageHelper _prepare2Send(){
        String fName="[_prepare2Send]";
        try{
            if(_sendPreBuild()==null)throw  new Exception("Failed to prebuild");
            if(_sendBuild()==null)throw  new Exception("Failed to build");
            return  this;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    Message sentMessage=null;
    public Message send(){
        String fName="[send]";
        try{
            sentMessage=null;
            if(_prepare2Send()==null)return  null;
            if(interactionHook!=null){
               sendInteractionHook();
            }
            if(sentMessage==null&&messageChannel!=null){
                sendMessageChannel();
            }
            if(sentMessage==null&&privateChannel!=null){
                sendPrivateChannel();
            }
            if(sentMessage==null&&textChannel!=null){
                sendTextChannel();
            }
            if(sentMessage==null)throw  new Exception("Failed to send messages");
            return  sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendInteractionHook(){
        String fName="[sendInteractionHook]";
        try{
            sentMessage=null;
            if(_prepare2Send()==null)return  null;
            if(interactionHook==null)throw  new Exception("Is null the channel");
            if(interactionHook.getInteraction().isAcknowledged()){
                sentMessage=interactionHook.sendMessage(message2Send).complete();
            }else{
                sentMessage=interactionHook.editOriginal(message2Send).complete();
            }
            if(sentMessage==null)throw  new Exception("Failed to send messages");
            clearInput();
            logger.info(fName+"success sent");
            return sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendMessageChannel(){
        String fName="[sendMessageChannel]";
        try{
            sentMessage=null;
            if(_prepare2Send()==null)return  null;
            if(messageChannel==null)throw  new Exception("Is null the channel");
            sentMessage=messageChannel.sendMessage(message2Send).complete();
            if(sentMessage==null)throw  new Exception("Failed to send messages");
            clearInput();
            logger.info(fName+"success sent");
            return sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendTextChannel(){
        String fName="[sendTextChannel]";
        try{
            sentMessage=null;
            if(_prepare2Send()==null)return  null;
            if(textChannel==null)throw  new Exception("Is null the channel");
            sentMessage=textChannel.sendMessage(message2Send).complete();
            if(sentMessage==null)throw  new Exception("Failed to send messages");
            clearInput();
            logger.info(fName+"success sent");
            return sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message sendPrivateChannel(){
        String fName="[sendPrivateChannel]";
        try{
            sentMessage=null;
            if(_prepare2Send()==null)return  null;
            if(privateChannel==null)throw  new Exception("Is null the channel");
            sentMessage=privateChannel.sendMessage(message2Send).complete();
            if(sentMessage==null)throw  new Exception("Failed to send messages");
            clearInput();
            logger.info(fName+"success sent");
            return sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message getSentMessage(){
        String fName="[getSentMessage]";
        try{
            return sentMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    Message editedMessage=null;
    public Message editOriginalInteractionHook(){
        String fName="[editOriginalInteractionHook]";
        try{
            editedMessage=null;
            WebhookMessageUpdateAction<Message> a=null;
            if(clearEmbeds){
                logger.info(fName+"embed.1");
                a=interactionHook.editOriginalEmbeds();
            }else
            if(messageEmbeds!=null&&!messageEmbeds.isEmpty()){
                logger.info(fName+"embed.2");
                a=interactionHook.editOriginalEmbeds(messageEmbeds);
            }
            if(clearContent){
                if(a==null) a=interactionHook.editOriginal("");else
                    a=a.setContent(null);
            }else
            if(content!=null&&!content.isEmpty()){
                if(a==null) a=interactionHook.editOriginal(content);else
                    a=a.setContent(content);
            }
            if(clearActionRows){
                if(a==null)a=interactionHook.editOriginalComponents();else
                    a=a.setActionRows();
            }else
            if(actionRows!=null&&!actionRows.isEmpty()){
                if(a==null)a=interactionHook.editOriginalComponents(actionRows);else
                    a=a.setActionRows(actionRows);
            }
            if(a==null)throw  new Exception("Nothing to edit");
            editedMessage=a.complete();
            if(editedMessage==null)throw  new Exception("Failed to edit messages");
            clearInput();
            logger.info(fName+"success edit");
            logger.info("message_id="+editedMessage.getId());
            return editedMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message editOriginalInteractionHookOrBackupSend(){
        String fName="[editOriginalInteractionHookOrBackupSend]";
        try{
            editedMessage=null;
            if(interactionHook!=null){
                editOriginalInteractionHook();
            }else{
                logger.warn(fName+"interactionHook is null");
            }
            if(editedMessage==null){
                logger.warn(fName+"Failed to edit message, do send as backup");
                send();
            }
            if(sentMessage==null&&editedMessage==null)throw  new Exception("Failed to send&&edit messages");
            clearInput();
            if(editedMessage!=null) {
                logger.info(fName+"success edit");
                logger.info("message_id="+editedMessage.getId());
                return editedMessage;
            }
            if(sentMessage!=null) {
                logger.info(fName+"success sent");
                logger.info("message_id="+sentMessage.getId());
                return sentMessage;
            }
            throw  new Exception("Failed to get any message");
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message editMessage(){
        String fName="[editMessage]";
        try{
            editedMessage=null;
            MessageAction a=null;
            if(clearEmbeds){
                a=message2Edit.editMessageEmbeds();
            }else
            if(messageEmbeds!=null&&!messageEmbeds.isEmpty()){
                a=message2Edit.editMessageEmbeds(messageEmbeds);
            }
            if(clearContent){
                if(a==null) a=message2Edit.editMessage("");else
                    a=a.content(null);
            }else
            if(content!=null&&!content.isEmpty()){
                if(a==null) a=message2Edit.editMessage(content);else
                    a=a.content(content);
            }if(clearActionRows){

            }else
            if(actionRows!=null&&!actionRows.isEmpty()){
                if(a==null)a=message2Edit.editMessageComponents(actionRows);else
                    a=a.setActionRows(actionRows);
            }
            if(a==null)throw  new Exception("Nothing to edit");
            editedMessage=a.complete();
            if(editedMessage==null)throw  new Exception("Failed to edit messages");
            clearInput();
            logger.info("message_id="+editedMessage.getId());
            return editedMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message getEditMessage(){
        String fName="[getEditMessage]";
        try{
            return editedMessage;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public Message getMessage(){
        String fName="[getMessage]";
        try{
            if(editedMessage!=null){
                logger.info(fName+"returned editedMessage="+editedMessage.getId());
                return editedMessage;
            }
            if(sentMessage!=null){
                logger.info(fName+"returned sentMessage="+sentMessage.getId());
                return sentMessage;
            }
            if(message2Edit!=null){
                logger.info(fName+"returned message2Edit="+message2Edit.getId());
                return message2Edit;
            }
            logger.info(fName+" nothing to return");
            return  null;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
