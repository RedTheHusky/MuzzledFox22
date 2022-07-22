package events.Guild;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import restraints.PostSynthesizer.rdVoicePostSynthesizer;

import java.util.Arrays;

public class onGuildVoice extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onGuildVoice]";
    lcGlobalHelper gGlobal;
    public onGuildVoice(lcGlobalHelper global){
        logger.warn(".constructor");
        gGlobal=global;
    }
    /*
    public void	onGuildVoiceDeafen(@NotNull GuildVoiceDeafenEvent event){
        String fName="onGuildVoiceDeafen";
       logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isDeafened="+event.isDeafened());
    }
    public void	onGuildVoiceGuildDeafen(@NotNull GuildVoiceGuildDeafenEvent event){
        String fName="onGuildVoiceGuildDeafen";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isGuildDeafened="+event.isGuildDeafened());
    }
    public void	onGuildVoiceGuildMute(@NotNull GuildVoiceGuildMuteEvent event){
        String fName="onGuildVoiceGuildMute";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isGuildMuted="+event.isGuildMuted());
    }
    */
    public void	onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event){
        String fName="onGuildVoiceJoin";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" channelJoined="+event.getChannelJoined());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
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
        new rdVoicePostSynthesizer(gGlobal,event);
    }
    /*
    public void	onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event){
        String fName="onGuildVoiceLeave";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" channelLeft="+event.getChannelLeft());
    }
    */
    public void	onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event){
        String fName="onGuildVoiceMove";
        try {
            logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" channelLeft="+event.getChannelLeft().getId()+" channelJoined="+event.getChannelJoined().getId());

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
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
       new rdVoicePostSynthesizer(gGlobal,event);
    }
    /*
    public void	onGuildVoiceMute(@NotNull GuildVoiceMuteEvent event){
        String fName="onGuildVoiceMute";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isMuted()="+event.isMuted());
    }
    public void	onGuildVoiceSelfDeafen(@NotNull GuildVoiceSelfDeafenEvent event){
        String fName="onGuildVoiceSelfDeafen";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isSelfDeafened="+event.isSelfDeafened());
    }
    public void	onGuildVoiceSelfMute(@NotNull GuildVoiceSelfMuteEvent event){
        String fName="onGuildVoiceSelfMute";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isSelfMuted="+event.isSelfMuted());
    }
    public void	onGuildVoiceSuppress(@NotNull GuildVoiceSuppressEvent event){
        String fName="onGuildVoiceSuppress";
        logger.info(fName+" guild="+event.getGuild().getId()+" member="+event.getMember()+" isSuppressed="+event.isSuppressed());
    }
    public void	onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event){
        String fName="onGuildVoiceUpdate";
       logger.info(fName);
    }
    */
}
