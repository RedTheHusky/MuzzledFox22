package restraints.PostSynthesizer;

import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import org.apache.log4j.Logger;
import restraints.models.enums.GAGLEVELS;
import restraints.in.iRestraints;

import java.util.Arrays;

public class rdVoicePostSynthesizer implements llGlobalHelper, iRestraints {
    Logger logger = Logger.getLogger(getClass()); Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.GAGUSED_STR);
    String cName="[textPostSynthesizer]";
    public rdVoicePostSynthesizer(lcGlobalHelper global, GuildVoiceJoinEvent event){
        logger.info(".run build");
        Runnable r = new runVoice(global,event);
        new Thread(r).start();
    }
    public rdVoicePostSynthesizer(lcGlobalHelper global, GuildVoiceMoveEvent event){
        logger.info(".run build");
        Runnable r = new runVoice(global,event);
        new Thread(r).start();
    }
    public rdVoicePostSynthesizer(lcGlobalHelper global, GuildVoiceState guildVoiceState){
        logger.info(".run build");
        Runnable r = new runVoice(global,guildVoiceState);
        new Thread(r).start();
    }
    protected class runVoice implements Runnable {
        String cName = "[runVoice]";
        lcGlobalHelper gGlobal;
        Guild gGuild;
        Member gMember;
        VoiceChannel gVoiceChannel;
        GuildVoiceState gGuildVoiceState;

        public runVoice(lcGlobalHelper global, GuildVoiceJoinEvent event) {
            logger.info(".run build");
            gGlobal = global;
            gMember = event.getMember();
            gGuild = event.getGuild();
            gVoiceChannel = event.getChannelJoined();
            gGuildVoiceState = event.getVoiceState();
        }

        public runVoice(lcGlobalHelper global, GuildVoiceMoveEvent event) {
            logger.info(".run build");
            gGlobal = global;
            gMember = event.getMember();
            gGuild = event.getGuild();
            gVoiceChannel = event.getNewValue();
            gGuildVoiceState = event.getVoiceState();
        }
        public runVoice(lcGlobalHelper global, GuildVoiceState guildVoiceState) {
            logger.info(".run build");
            gGlobal = global;
            gMember = guildVoiceState.getMember();
            gGuild = guildVoiceState.getGuild();
            gVoiceChannel = guildVoiceState.getChannel();
            gGuildVoiceState = guildVoiceState;
        }
        lcJSONUserProfile gUserProfile;
        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                lsUsefullFunctions.setThreadName4Display("rdVoicePost");
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                boolean isGuildMuted=gGuildVoiceState.isGuildMuted();
                boolean isGuildDeafened=gGuildVoiceState.isGuildDeafened();
                logger.info(fName + ".isGuildMuted=" + isGuildMuted+",isGuildDeafened="+isGuildDeafened);
                boolean isRMuted=false,isRDeafened=false;
                if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                    JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                    if(jsonObject.has(optionVoiceMute)){
                        isRMuted=jsonObject.getBoolean(optionVoiceMute);
                    }
                    if(jsonObject.has(optionVoiceDeafen)){
                        isRDeafened=jsonObject.getBoolean(optionVoiceDeafen);
                    }
                }
                logger.info(fName + ".isRMuted=" + isRMuted+",isRDeafened="+isRDeafened);
                if(!isRDeafened){
                    try {
                        boolean enabled=false, inuse=false;int action=0;
                        if(gUserProfile.jsonObject.has(nEarMuffs)&&gUserProfile.jsonObject.getJSONObject(nEarMuffs).getBoolean(nOn)){action=1;}else{action=-1;}
                        if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                            JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                            if(jsonObject.has(nVoiceChannelRestriction_ListenEnabled)){
                                enabled=jsonObject.getBoolean(nVoiceChannelRestriction_ListenEnabled);
                            }
                            if(jsonObject.has(nVoiceChannelRestriction_ListenInUse)){
                                inuse=jsonObject.getBoolean(nVoiceChannelRestriction_ListenInUse);
                            }
                        }

                        logger.info(fName + ".enabled=" + enabled+",inuse="+inuse+", action=" + action);
                        if(gUserProfile.getMember()==null){
                            logger.warn(fName + ".no member provide");
                        }else
                        if(action==-1&&inuse){
                            try {
                                gGuild.deafen(gUserProfile.getMember(),false).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,false);
                                saveProfile();
                                logger.info(fName + ".deafen lifted up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(inuse&&!enabled){
                            try {
                                gGuild.deafen(gUserProfile.getMember(),false).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,false);
                                saveProfile();
                                logger.info(fName + ".deafen lifted up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(action==1&&!inuse&&enabled){
                            try {
                                gGuild.deafen(gUserProfile.getMember(),true).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,true);
                                saveProfile();
                                logger.info(fName + ".deafen applied for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(action==1&&inuse&&enabled&&!isGuildDeafened){
                            try {
                                gGuild.deafen(gUserProfile.getMember(),true).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,true);
                                saveProfile();
                                logger.info(fName + ".deafen applied for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        else{
                            logger.warn(fName + ".invalid condition");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }else{
                    try {
                        if(!isGuildDeafened){
                            gGuild.deafen(gMember,true).complete();addProfile();
                            logger.info(fName + ".deafen_R applied up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(!isRMuted){
                    try {
                        boolean enabled=false, inuse=false;int action=0;
                        if(gUserProfile.jsonObject.has(nGag)&&gUserProfile.jsonObject.getJSONObject(nGag).getBoolean(nOn)){
                            String level=gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel);
                            if(level.equalsIgnoreCase(GAGLEVELS.Faux.getName())){ action=-1;}
                            else if(level.equalsIgnoreCase(GAGLEVELS.None.getName())){ action=-1;}
                            else { action=1;}
                        }else{action=-1;}
                        if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                            JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                            if(jsonObject.has(nVoiceChannelRestriction_MuteEnabled)){
                                enabled=jsonObject.getBoolean(nVoiceChannelRestriction_MuteEnabled);
                            }
                            if(jsonObject.has(nVoiceChannelRestriction_MuteInUse)){
                                inuse=jsonObject.getBoolean(nVoiceChannelRestriction_MuteInUse);
                            }
                        }

                        logger.info(fName + ".enabled=" + enabled+",inuse="+inuse+", action=" + action);
                        if(gUserProfile.getMember()==null){
                            logger.warn(fName + ".no member provide");
                        }else
                        if(action==-1&&inuse){
                            try {
                                gGuild.mute(gUserProfile.getMember(),false).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,false);
                                saveProfile();
                                logger.info(fName + ".mute lifted up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(inuse&&!enabled){
                            try {
                                gGuild.mute(gUserProfile.getMember(),false).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,false);
                                saveProfile();
                                logger.info(fName + ".mute lifted up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(action==1&&!inuse&&enabled){
                            try {
                                gGuild.mute(gUserProfile.getMember(),true).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,true);
                                saveProfile();
                                logger.info(fName + ".mute applied for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }else
                        if(action==1&&inuse&&enabled&&!isGuildMuted){
                            try {
                                gGuild.mute(gUserProfile.getMember(),true).complete();addProfile();
                                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,true);
                                saveProfile();
                                logger.info(fName + ".mute applied for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        else{
                            logger.warn(fName + ".invalid condition");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }else{
                    try {
                        if(!isGuildMuted){
                            gGuild.mute(gMember,true).complete();addProfile();
                            logger.info(fName + ".mute_R applied for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }


            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            //gUserProfile=iSafetyUserProfileEntry(gUserProfile,gBDSMCommands.getRestrainsProfile());
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            return true;
        }
        private void addProfile(){
            String fName="[addProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
    }
}
