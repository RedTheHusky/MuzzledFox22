package FlexiPlay;

import FlexiPlay.entities.cGuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public interface iFlexiPlayer {
    int maxRecordingInstances =5;
   static  void  test(){
        String fName="[sendApplyingUser4RD]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static cGuildMusicManager getGuildAudioPlayer(Map<Long, cGuildMusicManager> musicManagers, AudioPlayerManager playerManager, Guild guild) {
        String fName="[getGuildAudioPlayer]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            long guildId = Long.parseLong(guild.getId());
            logger.info(fName + "guildId="+guildId);
            cGuildMusicManager musicManager = musicManagers.get(guildId);
            if (musicManager == null) {
                return null;
            }
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManager;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Map<Long, cGuildMusicManager> newGuildAudioPlayer(Map<Long, cGuildMusicManager> musicManagers, AudioPlayerManager playerManager, Guild guild) {
        String fName="[newGuildAudioPlayer]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            long guildId = Long.parseLong(guild.getId());
            logger.info(fName + "guildId="+guildId);
            cGuildMusicManager musicManager = getGuildAudioPlayer(musicManagers,playerManager,guild);
            if (musicManager == null) {
                musicManager = new cGuildMusicManager(playerManager);
                musicManagers.put(guildId, musicManager);
            }
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManagers;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static void playTrack(AudioTrack track, EmbedBuilder eb, TextChannel channel) {
        String fName="[playTrack]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            eb.setAuthor("YouTube", null, "https://cdn.discordapp.com/emojis/535586488801558538.png");
            eb.setDescription(track.getInfo().title);
            eb.setColor(new Color(0x7289da));
            eb.addField("Track length", String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length),
                    TimeUnit.MILLISECONDS.toSeconds(track.getInfo().length) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length))
            ), true);
            channel.sendMessageEmbeds(eb.build()).queue();
           return;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    static EmbedBuilder playTrack(AudioTrack track, EmbedBuilder eb) {
        String fName="[playTrack]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            eb.setAuthor("YouTube", null, "https://cdn.discordapp.com/emojis/535586488801558538.png");
            eb.setDescription(track.getInfo().title);
            eb.setColor(new Color(0x7289da));
            eb.addField("Track length", String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length),
                    TimeUnit.MILLISECONDS.toSeconds(track.getInfo().length) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length))
            ), true);
            return eb;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return eb;
        }
    }

    static cGuildMusicManager play(Guild guild, cGuildMusicManager musicManager, AudioTrack track, Member member) {
        String fName="[play]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            connect2VoiceChannel(guild.getAudioManager(), member);
            musicManager.scheduler.queue(track);
            return musicManager;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static cGuildMusicManager add2Queue(cGuildMusicManager musicManager, AudioTrack track) {
        String fName="[add2Queue]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            musicManager.scheduler.queue.add(track);
            return musicManager;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static cGuildMusicManager sub4Queue(cGuildMusicManager musicManager, AudioTrack track) {
        String fName="[sub4Queue]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            musicManager.scheduler.queue.remove(track);
            return musicManager;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static cGuildMusicManager sub4Queue(cGuildMusicManager musicManager, int index) {
        String fName="[sub4Queue]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            List l = new ArrayList( musicManager.scheduler.queue);
            l.remove(index);
            musicManager.scheduler.queue= (BlockingQueue<AudioTrack>) l;
            return musicManager;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static boolean isMemberInVoiceChannel(AudioManager audioManager, Member member) {
        String fName="[isMemberInVoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            Guild guild=member.getGuild();
            logger.info(fName + "guild="+guild.getName()+"("+guild.getId()+")");
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                for (Member m : voiceChannel.getMembers()) {
                    if (m.equals(member)) {
                        logger.info(fName + "result=true");
                        return true;
                    }
                }
            }
            logger.info(fName + "result=false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isConnected2VoiceChannel(AudioManager audioManager) {
        String fName="[isConnected2VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            if (audioManager.isConnected()) {
                logger.info(fName + "result=true");
                return true;
            }
            logger.info(fName + "result=false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean disconnect4VoiceChannel(AudioManager audioManager) {
        String fName="[disconnect4VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            audioManager.closeAudioConnection();
            logger.info(fName + "result=true");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static VoiceChannel getConnectedVoiceChannel(AudioManager audioManager) {
        String fName="[getConnectedVoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            if(!isConnected2VoiceChannel(audioManager)){
                logger.info(fName + "not connected");
                return null;
            }
            VoiceChannel voiceChannel=audioManager.getConnectedChannel();
            logger.info(fName + "voiceChannel="+voiceChannel.getName()+"("+voiceChannel.getId()+")");
            Guild guild=voiceChannel.getGuild();
            logger.info(fName + "guild="+guild.getName()+"("+guild.getId()+")");
            return voiceChannel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static boolean connect2VoiceChannel(AudioManager audioManager, Member member,boolean allowSwapping) {
        String fName="[connect2VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "allowSwapping="+allowSwapping);
            logger.info(fName + "member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            Guild guild=member.getGuild();
            logger.info(fName + "guild="+guild.getName()+"("+guild.getId()+")");
            if(allowSwapping){
                for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                    for (Member m : voiceChannel.getMembers()) {
                        if (m.equals(member)) {
                            if (!audioManager.isConnected()){
                                logger.info(fName + "already connected>attempt disconnect");
                                if(!disconnect4VoiceChannel(audioManager)){
                                    logger.info(fName + "failed to disconnect=>false");
                                    return  false;
                                }
                            }
                            logger.info(fName + "attempt connection to "+voiceChannel.getName()+"("+voiceChannel.getId()+")");
                            audioManager.openAudioConnection(voiceChannel);
                            logger.info(fName + "openAudioConnection to voice channel =>true");
                            return true;
                        }
                    }
                }
            }else{
                if (!audioManager.isConnected()) {
                    logger.info(fName + "is not connected");
                    for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                        for (Member m : voiceChannel.getMembers()) {
                            if (m.equals(member)) {
                                logger.info(fName + "attempt connection to "+voiceChannel.getName()+"("+voiceChannel.getId()+")");
                                audioManager.openAudioConnection(voiceChannel);
                                logger.info(fName + "openAudioConnection to voice channel =>true");
                                return true;
                            }
                        }
                    }
                }else{
                    logger.info(fName + "already connected=>false");
                    return false;
                }
            }

            logger.info(fName + "result=false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean connect2VoiceChannel(AudioManager audioManager, Member member) {
        String fName="[connect2VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            return connect2VoiceChannel(audioManager,member,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean connect2VoiceChannel(AudioManager audioManager, VoiceChannel voiceChannel,boolean allowSwapping) {
        String fName="[connect2VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "allowSwapping="+allowSwapping);
            logger.info(fName + "voiceChannel="+voiceChannel.getName()+"("+voiceChannel.getId()+")");
            Guild guild=voiceChannel.getGuild();
            logger.info(fName + "guild="+guild.getName()+"("+guild.getId()+")");
            if(allowSwapping){
                if (!audioManager.isConnected()){
                    logger.info(fName + "already connected>attempt disconnect");
                    if(!disconnect4VoiceChannel(audioManager)){
                        logger.info(fName + "failed to disconnect=>false");
                        return  false;
                    }
                }
                logger.info(fName + "not connected>attempt connection");
                audioManager.openAudioConnection(voiceChannel);
                logger.info(fName + "openAudioConnection to voice channel =>true");
                return true;
            }else{
                if (!audioManager.isConnected()) {
                    logger.info(fName + "not connected>attempt connection");
                    audioManager.openAudioConnection(voiceChannel);
                    logger.info(fName + "openAudioConnection to voice channel =>true");
                    return true;
                }
            }
            logger.info(fName + "result=>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean connect2VoiceChannel(AudioManager audioManager, VoiceChannel voiceChannel) {
        String fName="[connect2VoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            return connect2VoiceChannel(audioManager, voiceChannel,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean connect2FirstVoiceChannel(AudioManager audioManager,boolean allowSwapping) {
        String fName="[connect2FirstVoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            Guild guild=audioManager.getGuild();
            logger.info(fName + "guild="+guild.getName()+"("+guild.getId()+")");
            logger.info(fName + "allowSwapping="+allowSwapping);
            if(allowSwapping){
                if (audioManager.isConnected()) {
                    logger.info(fName + "is connected");
                    if(!disconnect4VoiceChannel(audioManager)){
                        logger.info(fName + "failed to disconnect=>false");
                        return false;
                    }
                }
                for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                    logger.info(fName + "attempt connection to "+voiceChannel.getName()+"("+voiceChannel.getId()+")");
                    audioManager.openAudioConnection(voiceChannel);
                    logger.info(fName + "openAudioConnection to voice channel =>true");
                    return  true;
                }
            }else{
                if (!audioManager.isConnected()) {
                    logger.info(fName + "is not connected");
                    for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                        logger.info(fName + "attempt connection to "+voiceChannel.getName()+"("+voiceChannel.getId()+")");
                        audioManager.openAudioConnection(voiceChannel);
                        logger.info(fName + "openAudioConnection to voice channel =>true");
                        return  true;
                    }
                }else{
                    logger.info(fName + "already connected=>false");
                    return false;
                }
            }

            logger.info(fName + "result=>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean connect2FirstVoiceChannel(AudioManager audioManager) {
        String fName="[connect2FirstVoiceChannel]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            return connect2FirstVoiceChannel(audioManager,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String keyTitle="title",keyAuthor="author",keyIdentifier="identifier",keyIsstream="isStream",keyLength="length",keyUri="keyUri",keyDuration="duration",keyPosition="position";
    String keyInfo="info",keyEntity="entity";
    static  List<AudioTrack> getQueue(cGuildMusicManager musicManager) {
        String fName="[getQueue]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            List<AudioTrack>list=new ArrayList<>();
            Object[] queue = musicManager.scheduler.queue.toArray();
            if(queue.length == 0){
                return list;
            }
            for (Object o : queue) {
                AudioTrack track = (AudioTrack) o;
                list.add(track);
            }
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static JSONArray getQueueAsJSONArray(cGuildMusicManager musicManager) {
        String fName="[getQueueAsJSONArray]";
        Logger logger = Logger.getLogger(iFlexiPlayer.class);
        try {
            logger.info(fName + "");
            JSONArray array=new JSONArray();
            List<AudioTrack>audioTracks=getQueue(musicManager);
            if(audioTracks==null){
                return array;
            }
            for (int i = 0; i < audioTracks.size(); i++) {
                AudioTrack track = audioTracks.get(i);
                JSONObject jsonInfo=new JSONObject(); JSONObject jsonTrack=new JSONObject();
                jsonInfo.put(keyTitle, track.getInfo().title);
                jsonInfo.put(keyAuthor, track.getInfo().author);
                jsonInfo.put(keyIsstream, track.getInfo().isStream);
                jsonInfo.put(keyIdentifier, track.getInfo().identifier);
                jsonInfo.put(keyLength, track.getInfo().length);
                jsonInfo.put(keyUri, track.getInfo().uri);
                jsonTrack.put(keyInfo,jsonInfo);
                jsonTrack.put(keyIdentifier,track.getIdentifier());
                jsonTrack.put(keyDuration,track.getDuration());
                jsonTrack.put(keyPosition,track.getPosition());
                jsonTrack.put(keyEntity,track);
                logger.info(fName + "jsonTrack["+i+"]="+jsonTrack.toString());
            }
            return array;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
