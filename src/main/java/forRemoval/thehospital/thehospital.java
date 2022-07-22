package forRemoval.thehospital;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Iterator;

public class thehospital extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iTheHospital {
    Logger logger = Logger.getLogger(getClass()); String cName="[TheHospital_NAdventure]";
    lcGlobalHelper gGlobal;
    String gTitle="The Hospital";
    String gTabel="nAdventure", gProfileName ="thehospital", gPrefix="nhospital";
    public thehospital(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "TheHospital_NAdventure";
        this.help = "You are at the hospital.";
        this.aliases = new String[]{"nhospital"};
        this.guildOnly = true;this.category= llCommandCategory_BuildAlpha;this.hidden=true;
    }

    public thehospital(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(cName+fName);
        if(llDebug){
            logger.info(cName+fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        CommandEvent gEvent;String cName = "[runLocal]";
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel; Message gMessage;
        int gScene=0;long gLastSceneMessageId=0, gLastSceneChannelId=0;
        GuildMessageReactionAddEvent gReactionAddEbent; MessageReaction.ReactionEmote gReactionEmote;
        public runLocal(CommandEvent ev) {
            logger.info(cName + ".run build");
            gEvent = ev;
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
            logger.info(cName + ".run build");
            gReactionAddEbent = ev;
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            if(gEvent!=null){
                String[] items;
                boolean isInvalidCommand = true;
                gUser = gEvent.getAuthor();gMember=gEvent.getMember();
                gGuild = gEvent.getGuild();
                logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = gEvent.getTextChannel();
                logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().toLowerCase().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("start")){
                        start(false);isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("current")){
                        current();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("reset")){

                    }

                }
                //logger.info(cName+fName+".deleting op message");
                //llQuckCommandMessageDelete(gEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
            }
            if(gReactionAddEbent!=null){
                gUser = gReactionAddEbent.getUser();gMember=gReactionAddEbent.getMember();
                gGuild = gReactionAddEbent.getGuild();
                logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = gReactionAddEbent.getChannel();
                logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                //gMessage= gTextChannel.retrieveMessageById(gReactionAddEbent.getMessageIdLong()).complete();
                logger.info(cName + fName + ".MessageId=:" + gReactionAddEbent.getMessageIdLong());
                gReactionEmote=gReactionAddEbent.getReactionEmote();
                logger.info(cName + fName + ".gReactionEmote.name=:" + gReactionEmote.getName());
                if(!getProfileLight(gUser)){logger.info(cName + fName + ".can't get profile"); return;}
                getValues();
                if(gLastSceneChannelId!=gTextChannel.getIdLong()||gLastSceneMessageId!=gReactionAddEbent.getMessageIdLong()){
                    logger.info(cName + fName + ".not same channel or message id");return;
                }
                logger.info(cName+".reaction added to correct message");
                next();
            }
            logger.info(cName+".run ended");
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String desc="N/a";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }
        private void start(boolean isRecall){
            String fName = "[start]";
            logger.info(cName + fName);
            if(gUserProfile==null&&!getProfile(gUser)){logger.error(cName + fName + ".can't get profile"); return;}
            getValues();
            if(!isRecall&&gScene!=0){
                logger.warn(cName + fName + ".already started");
                llSendQuickEmbedMessage(gUser,gTitle,"Already started exploration. Do `"+llPrefixStr+gPrefix+" current` to display current situation",llColorRed_Crayola);
                return;
            }
            gScene=100;
            logger.info(cName + fName+"getting post information");
            if(!getSelected(gScene)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Could not get branch!",llColorRed_Crayola);return;}
            logger.info(cName + fName+"got post information");
            EmbedBuilder embed=new EmbedBuilder();embed.setTitle(gTitle);embed.setColor(gSelectedColor);
            if(gSelectedImage!=null&&!gSelectedImage.isBlank()){embed.setImage(gSelectedImage);}
            embed.setDescription(getSceneWithUser(gSelectedText, gUser));
            String optionsStr=addChoicesFields(gGuild,gSelectedJson);
            if(optionsStr!=null&&!optionsStr.isBlank()){
                embed.addField("Options",optionsStr,false);
            }
            Message message= llSendMessageResponse(gTextChannel,embed);
            setCurrent(gScene, message.getTextChannel().getIdLong(),message.getIdLong(),gSelectedChoice);
            add2Log(gScene, message.getTextChannel().getIdLong(),message.getIdLong());
            addingReaction2Message(message);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Could not save!",llColorRed_Crayola);}
        }
        JSONObject gBranches=iMainBranche();
        JSONObject gSelectedJson=new JSONObject(), gSelectedChoice=new JSONObject();String gSelectedText="n/a", gSelectedImage="";Color gSelectedColor=llColorGreen2;
        private boolean getSelected(int i){
            String fName = "[getSelected]";
            logger.info(cName + fName+"i="+i);
            if(!gBranches.has(String.valueOf(i))){
                logger.error(cName + fName+"no such branch ="+i);
                return false;
            }
            gSelectedJson=gBranches.getJSONObject(String.valueOf(i));
            if(gSelectedJson.has("choices")){
                gSelectedChoice=gSelectedJson.getJSONObject("choices");
            }else  logger.warn(cName + fName+"no choices");
            if(gSelectedJson.has("text")){
                gSelectedText=gSelectedJson.getString("text");
            }else logger.warn(cName + fName+"no text");
            if(gSelectedJson.has("image")){
                gSelectedImage=gSelectedJson.getString("image");
            }else logger.warn(cName + fName+"no image");
            return true;
        }
        private void addingReaction2Message(Message message){
            String fName = "[addingReaction2Message]";
            logger.info(cName + fName);
            Iterator<String>keys=gSelectedChoice.keys();
            while(keys.hasNext()){
                String key=keys.next();
                logger.info(cName + fName+"emoji key="+key);
                Emote emote=getEmote(gGuild,key);
                if(emote!=null){
                    message.addReaction(emote).queue();
                }
            }
        }
        private void current(){
            String fName = "[current]";
            logger.info(cName + fName);
            if(gUserProfile==null&&!getProfile(gUser)){logger.error(cName + fName + ".can't get profile"); return;}
            getValues();
            if(gScene==0){
                logger.warn(cName + fName + ".not started started");
                llSendQuickEmbedMessage(gUser,gTitle,"Not started adventure yet. Do `"+llPrefixStr+gPrefix+" start` to start the adventure.",llColorRed_Crayola);
                return;
            }
            logger.info(cName + fName+"getting post information");
            if(!getSelected(gScene)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Could not get branch!",llColorRed_Crayola);return;}
            logger.info(cName + fName+"got post information");
            EmbedBuilder embed=new EmbedBuilder();embed.setTitle(gTitle);embed.setColor(gSelectedColor);
            if(gSelectedImage!=null&&!gSelectedImage.isBlank()){embed.setImage(gSelectedImage);}
            embed.setDescription(getSceneWithUser(gSelectedText, gUser));
            String optionsStr=addChoicesFields(gGuild,gSelectedJson);
            if(optionsStr!=null&&!optionsStr.isBlank()){
                embed.addField("Options",optionsStr,false);
            }
            Message message= llSendMessageResponse(gTextChannel,embed);
            setCurrent(gScene, message.getTextChannel().getIdLong(),message.getIdLong(),gSelectedChoice);
            add2Log(gScene, message.getTextChannel().getIdLong(),message.getIdLong());
            addingReaction2Message(message);
        }
        private void next(){
            String fName = "[next]";
            logger.info(cName + fName);
            if(gUserProfile==null&&!getProfile(gUser)){logger.error(cName + fName + ".can't get profile"); return;}
            getValues();
            logger.info(cName + fName+"gScene="+gScene);
            String name=gReactionEmote.getName();
            logger.info(cName + fName + ".gemotename=" + name);
            int iNumber=getEmoteNumber(name);
            logger.info(cName + fName + ".iNumber=" + iNumber);
            logger.info(cName + fName + ".gScene=" + gScene);
            getSelected(gScene);
            logger.info(cName + fName + ".gSelectedChoicee=" + gSelectedChoice.toString());
            int iNext=getEmoteOption(gSelectedChoice,iNumber);
            logger.info(cName + fName + ".iNext=" + iNext);
            //gScene=iNext;
            if(!getSelected(iNext)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Could not get branch!",llColorRed_Crayola);return;}
            logger.info(cName + fName+"got post information");
            EmbedBuilder embed=new EmbedBuilder();embed.setTitle(gTitle);embed.setColor(gSelectedColor);
            if(gSelectedImage!=null&&!gSelectedImage.isBlank()){embed.setImage(gSelectedImage);}
            embed.setDescription(getSceneWithUser(gSelectedText, gUser));
            String optionsStr=addChoicesFields(gGuild,gSelectedJson);
            if(optionsStr!=null&&!optionsStr.isBlank()){
                embed.addField("Options",optionsStr,false);
            }
            Message message= llSendMessageResponse(gTextChannel,embed);
            setCurrent(iNext, message.getTextChannel().getIdLong(),message.getIdLong(),gSelectedChoice);
            add2Log(iNext, message.getTextChannel().getIdLong(),message.getIdLong());
            addingReaction2Message(message);
        }


        private void getValues(){
            String fName = "[getValues]";
            logger.info(cName + fName);
            if(gUserProfile.jsonObject.has(nFieldCurrent)){
                if(gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nLevel)){
                    gScene=gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getInt(nLevel);
                }
                if(gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nChannelId)){
                    gLastSceneChannelId=gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getLong(nChannelId);
                }
                if(gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nMessageId)){
                    gLastSceneMessageId=gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getLong(nMessageId);
                }
                logger.info(cName + fName + ".gScene="+gScene);
                logger.info(cName + fName + ".nChannelId="+gLastSceneChannelId);
                logger.info(cName + fName + ".nMessageId="+gLastSceneMessageId);
            }
        }
        lcJSONUserProfile gUserProfile;
        private void setCurrent(int level, long channelid, long messageid, JSONObject choices){
            String fName = "[setCurren]";
            logger.info(cName + fName);
            gUserProfile.jsonObject.getJSONObject(nFieldCurrent).put(nLevel,level);
            gUserProfile.jsonObject.getJSONObject(nFieldCurrent).put(nChoices,choices);
            gUserProfile.jsonObject.getJSONObject(nFieldCurrent).put(nChannelId,channelid);
            gUserProfile.jsonObject.getJSONObject(nFieldCurrent).put(nMessageId,messageid);
        }
        private void add2Log(int level, long channelid, long messageid){
            String fName = "[setCurren]";
            logger.info(cName + fName);
            JSONObject json=new JSONObject();
            json.put(nChannelId,channelid);
            json.put(nLevel,level);
            gUserProfile.jsonObject.getJSONObject(nFieldLogs).put(String.valueOf(messageid),json);
        }
        private Boolean getProfile(User user){
            String fName="[getProfile]";
            logger.info(cName+fName);
            logger.info(cName + fName + ".user:"+ user.getId()+"|"+user.getName());
            gUserProfile=gGlobal.getUserProfile(gProfileName,user,gGuild,gProfileName);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(cName + fName + ".is locally cached");
            }else{
                logger.info(cName + fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild,gProfileName);
                if(gUserProfile.getProfile(gTabel)){
                    logger.info(cName + fName + ".has sql entry");
                }
            }
            gUserProfile=iInitProfile(gUserProfile);
            gGlobal.putUserProfile(gUserProfile, gProfileName);
            if(!gUserProfile.isUpdated){
                logger.info(cName + fName + ".no update>ignore");return true;
            }
            if(!saveProfile()){ logger.error(cName+fName+".failed to write in Db");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
            return true;
        }
        private Boolean getProfileLight(User user){
            String fName="[getProfileLight]";
            logger.info(cName+fName);
            logger.info(cName + fName + ".user:"+ user.getId()+"|"+user.getName());
            gUserProfile=gGlobal.getUserProfile(gProfileName,user,gGuild,gProfileName);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(cName + fName + ".is locally cached");
            }else{
                logger.info(cName + fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild,gProfileName);
                if(!gUserProfile.getProfile(gTabel)){
                    logger.info(cName + fName + ".has sql entry"); return false;
                }
            }
            gUserProfile=iInitProfile(gUserProfile);
            return true;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putUserProfile(gUserProfile, gProfileName);
            if(gUserProfile.saveProfile(gTabel)){
                logger.info(cName + fName + ".success");return  true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }


    }
}
