package models.la;

import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

public abstract class aBasicCommandHandler extends aBasicAccessControl {
    public boolean isInvalidCommand = true;
    public String[] items;
    public CommandEvent gEvent;
    public String gTitle2="";
    public User gUser;
    public Member gMember;
    public Guild gGuild;
    public TextChannel gTextChannel;
    public  Message gMessage;
    private void  checkLoger(){
        if(logger==null) logger = Logger.getLogger(getClass());
    }
    public void setCommandHandlerValues(Logger logger, CommandEvent event)  {
        String fName = "[setCommandHandlerValues]";
         try {
             if(logger==null)throw  new Exception("Logger cant be null!");this.logger=logger;
             if(event==null)throw  new Exception("Event cant be null!");gEvent = event;
             gMessage=event.getMessage();
             gUser = gEvent.getAuthor();user=gUser;
             logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
             if(event.isFromType(ChannelType.TEXT)){
                 gMember=gEvent.getMember();member=gMember;
                 gGuild = gEvent.getGuild();guild = gGuild;
                 logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                 gTextChannel = gEvent.getTextChannel();textChannel =gTextChannel;
                 logger.info(fName + ".textChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
             }
        } catch (Exception e) {
             if(logger==null) logger = Logger.getLogger(getClass());
             logger.error(fName + ".exception=" + e+"\nStackTrace:" + Arrays.toString(e.getStackTrace()));
        }

    }
    public boolean checkIfMemberIsAllowed(){
        String fName = "[checkIfMemberIsAllowed]";
        checkLoger();
        if(!gBasicFeatureControl.getEnable()){
            logger.info(fName+"its disabled");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle2,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
            isInvalidCommand=false;
            return false;
        }
        else if(!gBasicFeatureControl.isChannelAllowed(textChannel)){
            logger.info(fName+"its not allowed by channel");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle2,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
            isInvalidCommand=false;
            return false;
        }
        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
            logger.info(fName+"its not allowed by roles");
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle2,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
            isInvalidCommand=false;
            return false;
        }
        logger.info(fName+"allowed");
        return true;

    }
    public boolean ifItsAnAccessControlCommand(){
        String fName = "[ifItsAnAccessControlCommand]";
        checkLoger();
        if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
            if(items.length>2){
                // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                int group=0,type=0,action=0;
                switch (items[1].toLowerCase()){
                    case "allowedchannels":
                    case "allowchannels":
                        group=1;type=1;
                        break;
                    case "blockedchannels":
                    case "blockchannels":
                        group=1;type=-1;
                        break;
                    case "allowedroles":
                    case "allowroles":
                        group=2;type=1;
                        break;
                    case "blockedroles":
                    case "blockroles":
                        group=2;type=-1;
                        break;
                }
                switch (items[2].toLowerCase()){
                    case "list":
                        action=0;
                        break;
                    case "add":
                        action=1;
                        break;
                    case "set":
                        action=2;
                        break;
                    case "rem":
                        action=-1;
                        break;
                    case "clear":
                        action=-2;
                        break;
                }
                if(group==1){
                    if(action==0){
                        getChannels(type,false);
                    }else{
                        setChannel(type,action,gEvent.getMessage());
                    }
                }
                else if(group==2){
                    if(action==0){
                        getRoles(type,false);
                    }else{
                        setRole(type,action,gEvent.getMessage());
                    }
                }
            }else{
                menuGuild();
            }
            isInvalidCommand=false;
            return true;
        }
        return false;
    }
    public void deleteCommandPostAndCheckIfInvalid(){
        String fName = "[deleteCommandPostAndCheckIfInvalid]";
        checkLoger();
        logger.info(fName+".deleting op message");
        lsMessageHelper.lsMessageDelete(gEvent);
        if(isInvalidCommand){
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle2,"You provided an incorrect command!", llColors.llColorRed);
        }

    }
    public lcJSONGuildProfile gGuildProfile=new lcJSONGuildProfile(null);
    public boolean getGuildProfile(lcGlobalHelper global,String table,String name,boolean isAutoSave,Runnable runnable){
        String fName="[getGuildPProfile]";
        checkLoger();
        logger.info(fName+".safety check");
        if(gGuildProfile ==null||!gGuildProfile.isProfile()){
            logger.info(fName + ".get");
            gGuildProfile =global.getGuildSettings(gGuild, name);
            if(gGuildProfile ==null||!gGuildProfile.isExistent()|| gGuildProfile.jsonObject.isEmpty()){
                gGuildProfile =new lcJSONGuildProfile(global, name,gGuild,table);
                gGuildProfile.getProfile(table);
                if(!gGuildProfile.jsonObject.isEmpty()){
                    global.putGuildSettings(gGuild, gGuildProfile);
                }
            }

            runnable.run();
            if(gGuildProfile.isUpdated){
                global.putGuildSettings(gGuild, gGuildProfile);
                if(isAutoSave){
                    if(gGuildProfile.saveProfile()){
                        logger.info(fName + ".success save to db>true");
                        return true;
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            logger.info(fName + ".still got but failed to save>true");
        }else{
            logger.info(fName + ".skip>true");
        }
        return true;
    }
    public boolean saveGuild(lcGlobalHelper global){
        String fName="[saveGuild]";
        checkLoger();
        logger.info(fName);
        global.putGuildSettings(gGuild, gGuildProfile);
        if(gGuildProfile.saveProfile()){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }
    public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
    public Boolean getUserProfile(Member member,lcGlobalHelper global,String table,String name,boolean isAutoSave){
        String fName="[getProfile]";
        checkLoger();
        logger.info(fName);
        try{
            logger.info(fName + ".member:"+ member.getId());
            gUserProfile=global.getUserProfile(name,member,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(global,member,gGuild,name);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            global.putUserProfile(gUserProfile,name);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            if(!isAutoSave){
                if(!saveUserProfile(global)){ logger.error(fName+".failed to write in Db");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle2,"Failed to write in Db!", lsMessageHelper.llColorRed);}
            }
            return true;

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean getUserProfile(Member member,lcGlobalHelper global,String table,String name,boolean isAutoSave,Runnable runnable){
        String fName="[getProfile]";
        checkLoger();
        logger.info(fName);
        try{
            logger.info(fName + ".member:"+ member.getId());
            if(runnable==null)throw new Exception("Runnable is null");
            gUserProfile=global.getUserProfile(name,member,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(global,member,gGuild,name);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            runnable.run();
            global.putUserProfile(gUserProfile,name);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            if(!isAutoSave){
                if(!saveUserProfile(global)){ logger.error(fName+".failed to write in Db");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle2,"Failed to write in Db!", lsMessageHelper.llColorRed);}
            }
            return true;

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveUserProfile(lcGlobalHelper global){
        String fName="[saveProfile]";
        checkLoger();
        logger.info(fName);
        try{
            global.putUserProfile(gUserProfile);
            if(gUserProfile.saveProfile()){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
}
