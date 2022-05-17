package models.lc.helper;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.audit.AuditLogChange;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.audit.AuditLogKey;
import net.dv8tion.jda.api.audit.TargetType;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.*;

public class lcAuditLogEntryHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcAuditLogChangeEntry]";

    AuditLogEntry gEntry=null;
    public lcAuditLogEntryHelper(){
        String fName="[constructor1]";
    }
    public lcAuditLogEntryHelper(AuditLogEntry entry){
        String fName="[constructor1]";selectEntry(entry);
    }
    public void selectEntry(AuditLogEntry entry){
        String fName="[selectEntry]";
        logger.info(cName + fName);
        try {
            gEntry=entry;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
        }
    }

    public String getTargetName(){
        String fName="[getTargetName]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return null;
            }
            String name="";
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                name=guild.getMemberById(id).getUser().getName();
            }else
            if(type==TargetType.ROLE){
                name=guild.getRoleById(id).getName();
            }else
            if(type==TargetType.CHANNEL){
                TextChannel textChannel=guild.getTextChannelById(id);
                VoiceChannel voiceChannel=guild.getVoiceChannelById(id);
                Category category=guild.getCategoryById(id);
                if(textChannel!=null){
                    logger.info(cName + fName+"type=text channel");
                    name=textChannel.getName();
                }else
                if(voiceChannel!=null){
                    logger.info(cName + fName+"type=voice channel");
                    name=voiceChannel.getName();
                }else
                if(category!=null){
                    logger.info(cName + fName+"type=category channel");
                    name=category.getName();
                }
            }else
            if(type==TargetType.EMOTE){
                Emote emote=guild.getEmoteById(id);
                if(emote!=null){
                    logger.info(cName + fName+"emote found");
                    name=emote.getName();
                }
            }else
            if(type==TargetType.GUILD){
                name=guild.getName();
            }else
            if(type==TargetType.WEBHOOK){
                List<Webhook>webhooks=guild.retrieveWebhooks().complete();
                for(Webhook webhook : webhooks){
                    if(webhook.getId().equalsIgnoreCase(id)){
                        logger.info(cName + fName+"webhook found");
                        name=webhook.getName();
                        break;
                    }
                }
            }
            logger.info(cName + fName+"name="+name);
            return  name;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getTargetIconUrl(){
        String fName="[getTargetIconUrl]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return null;
            }
            String response="";
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                response=guild.getMemberById(id).getUser().getAvatarUrl();
            }else
            if(type==TargetType.EMOTE){
                Emote emote=guild.getEmoteById(id);
                if(emote!=null){
                    response=emote.getName();
                }
            }else
            if(type==TargetType.GUILD){
                response=guild.getIconUrl();
            }else
            if(type==TargetType.WEBHOOK){
                List<Webhook>webhooks=guild.retrieveWebhooks().complete();
                for(Webhook webhook : webhooks){
                    if(webhook.getId().equalsIgnoreCase(id)){
                        response=webhook.getDefaultUser().getAvatarUrl();
                        break;
                    }
                }
            }
            logger.info(cName + fName+"response="+response);
            return  response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public boolean isTargetMember(){
        String fName="[isTargetMember]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                response=true;
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Member getTargetMember(){
        String fName="[getTargetMembe]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return null;
            }
            Member response=null;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                response=guild.getMemberById(id);
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public User getTargetUser(){
        String fName="[getTargetMembe]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return null;
            }
            User response=null;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                response=guild.getMemberById(id).getUser();
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isTargetBot(){
        String fName="[isTargetBot]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.MEMBER){
                response=guild.getMemberById(id).getUser().isBot();
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean isTargetChannel(){
        String fName="[isTargetChannel]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                response=true;
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getTargetChannelType(){
        String fName="[getTargetChannelType]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return null;
            }
            String response="unknown";
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                TextChannel textChannel=guild.getTextChannelById(id);
                VoiceChannel voiceChannel=guild.getVoiceChannelById(id);
                Category category=guild.getCategoryById(id);
                if(textChannel!=null){
                    logger.info(cName + fName+"type=text channel");
                    response="text";
                }
                if(voiceChannel!=null){
                    logger.info(cName + fName+"type=voice channel");
                    response="voice";
                }
                if(category!=null){
                    logger.info(cName + fName+"type=category channel");
                    response="category";
                }
            }else{
                return null;
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isTargetTextChannel(){
        String fName="[isTargetTextChannel]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                TextChannel textChannel=guild.getTextChannelById(id);
                if(textChannel!=null){
                    logger.info(cName + fName+"type=text channel");
                    response=true;
                }
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTargetNSFWChannel(){
        String fName="[isTargetNSFWChannel]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                TextChannel textChannel=guild.getTextChannelById(id);
                if(textChannel!=null){
                    logger.info(cName + fName+"type=text channel");
                    response=textChannel.isNSFW();
                }
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTargetVoiceChannel(){
        String fName="[isTargetVoiceChannel]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                VoiceChannel voiceChannel=guild.getVoiceChannelById(id);
                if(voiceChannel!=null){
                    logger.info(cName + fName+"type=text channel");
                    response=true;
                }
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTargetCategoryChannel(){
        String fName="[isTargetCategoryChannel]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.CHANNEL){
                Category category=guild.getCategoryById(id);
                if(category!=null){
                    logger.info(cName + fName+"type=category channel");
                    response=true;
                }
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean isTargetRole(){
        String fName="[isTargetRole]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.ROLE){
                response=true;
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTargetWebhook(){
        String fName="[isTargetWebhook]";
        logger.info(cName + fName);
        try {
            if(gEntry==null){
                return false;
            }
            boolean response=false;
            Guild guild= gEntry.getGuild();
            TargetType type=gEntry.getTargetType();
            String id=gEntry.getTargetId();
            logger.info(cName + fName+"type="+type.name());
            if(type==TargetType.WEBHOOK){
                response=true;
            }
            logger.info(cName + fName+"response="+response);
            return response;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
