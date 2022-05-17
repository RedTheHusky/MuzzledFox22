package models.lc;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import events.audio.AudioPlayerSendHandler;
import events.audio.TrackScheduler;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.log4j.Logger;

public class lcGuildMusicManager {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcGuildMusicManager]";
    public AudioPlayer player;
    public TrackScheduler scheduler;
    public AudioPlayerManager gManager;
    public AudioPlaylist lapPlaylist;
    public AudioPlaylist gPlaylist;
   lcGuildMusicManager(){
        String fName="[constructor1]";
        logger.info(cName+fName);
    }
    Guild gGuild;
    lcGlobalHelper gGlobal;
    public lcGuildMusicManager(AudioPlayerManager manager, Guild guild,lcGlobalHelper g ) {
        String fName="[constructor1]";
        logger.info(cName+fName);
        gManager=manager;
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        gGuild=guild;
        gGlobal=g;
        logger.info(cName+fName+".guild:"+guild.getId()+"|"+guild.getName());
    }

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    public AudioPlayerSendHandler getSendHandler() {
        String fName="[getSendHandler]";
        logger.info(cName+fName);
        return new AudioPlayerSendHandler(player);
    }
    public JSONObject json=new JSONObject();
    public void loadAndPlayQueue(TextChannel channel, String trackUrl){
        String fName="[loadAndPlay]";
        logger.info(cName+fName);
        logger.info(cName+fName+"trackUrl="+trackUrl);
        if(channel!=null){
            logger.info(cName+fName+"textchannel="+channel.getId()+"|"+channel.getName());
        }
        json.remove("track");json.remove("firstTrack");json.remove("playlist");
        json.remove("trackLoaded");json.remove("playlistLoaded");json.remove("noMatches");json.remove("loadFailed");
        lcGuildMusicManager gThis=this;
        gManager.loadItemOrdered(gThis, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                String fName="[trackLoaded]";
                logger.info(cName+fName);
                json.put("track",track);
                json.put("trackLoaded",true);
                logger.info(cName+fName+"track.uri"+track.getInfo().uri);
                logger.info(cName+fName+"track.title"+track.getInfo().title);logger.info(cName+fName+"track.author"+track.getInfo().author);
                logger.info(cName+fName+"track.length"+track.getInfo().length);
                logger.info(cName+fName+"track.identifier"+track.getInfo().identifier);
                logger.info(cName+fName+"track.isStream"+track.getInfo().isStream);
                if(channel!=null) {
                    channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
                }
                playQueue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                String fName="[playlistLoaded]";
                logger.info(cName+fName);
                lapPlaylist=playlist;
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                json.put("firstTrack",firstTrack);
                json.put("playlist",playlist);
                json.put("playlistLoaded",true);
                logger.info(cName+fName+"firstTrack.uri"+firstTrack.getInfo().uri);
                logger.info(cName+fName+"firstTrack.title"+firstTrack.getInfo().title);logger.info(cName+fName+"firstTrack.author"+firstTrack.getInfo().author);
                logger.info(cName+fName+"firstTrack.length"+firstTrack.getInfo().length);
                logger.info(cName+fName+"firstTrack.identifier"+firstTrack.getInfo().identifier);
                logger.info(cName+fName+"firstTrack.isStream"+firstTrack.getInfo().isStream);
                logger.info(cName+fName+"playlist.name"+playlist.getName());
                if(channel!=null) {
                    channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                }
                playQueue(firstTrack);
            }

            @Override
            public void noMatches() {
                String fName="[playlistLoaded]";
                logger.warn(cName+fName);
                json.put("noMatches",true);
                if(channel!=null) {
                    channel.sendMessage("Nothing found by " + trackUrl).queue();
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                String fName="[loadFailed]";
                logger.error(cName+fName);
                json.put("loadFailed",true);
                if(channel!=null) {
                    channel.sendMessage("Could not play: " + exception.getMessage()).queue();
                }
            }
        });
    }
    public Boolean playQueue(AudioTrack track) {
        String fName="[playQueue]";
        logger.info(cName+fName);
        try{
            connectToFirstVoiceChannel();
            scheduler.queue(track);
            return true;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }
    public void stop() {
        String fName="[stop]";
        logger.info(cName+fName);
        player.stopTrack();
    }
    public void pause() {
        String fName="[pause]";
        logger.info(cName+fName);
        player.setPaused(true);
    }
    public void unpause() {
        String fName="[unpause]";
        logger.info(cName+fName);
        player.setPaused(false);
    }
    public void setVolume(int i) {
        String fName="[setVolume]";
        logger.info(cName+fName);
        logger.info(cName+fName+"i="+i);
        player.setVolume(i);
    }
    int volumeBeforeMute=0;
    public void mute() {
        String fName="[mute]";
        logger.info(cName+fName);
        volumeBeforeMute=player.getVolume();
        logger.info(cName+fName+"volumeBeforeMute="+volumeBeforeMute);
        player.setVolume(0);
    }
    public void unmute() {
        String fName="[unmute]";
        logger.info(cName+fName);
        logger.info(cName+fName+"volumeBeforeMute="+volumeBeforeMute);
        player.setVolume(volumeBeforeMute);
    }
    public Boolean skipTrack(TextChannel channel) {
        String fName="[skipTrack]";
        logger.info(cName+fName);
        try{
            scheduler.nextTrack();
            AudioTrack track=player.getPlayingTrack();
            if(track!=null){
                logger.info(cName+fName+"track.uri"+track.getInfo().uri);
                logger.info(cName+fName+"track.title"+track.getInfo().title);logger.info(cName+fName+"track.author"+track.getInfo().author);
                logger.info(cName+fName+"track.length"+track.getInfo().length);
                logger.info(cName+fName+"track.identifier"+track.getInfo().identifier);
                logger.info(cName+fName+"track.isStream"+track.getInfo().isStream);
                if(channel!=null){
                    logger.info(cName+fName+"textchannel="+channel.getId()+"|"+channel.getName());
                    channel.sendMessage(":track_next: Next track " + track.getInfo().title).queue();
                }
            }
            return true;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }

    public Boolean connectToFirstVoiceChannel() {
        String fName="[connectToFirstVoiceChannel]";
        logger.info(cName+fName);
        try{
            AudioManager audioManager=gGuild.getAudioManager();
            boolean isConnected=audioManager.isConnected();
            boolean isAttemptingToConnect=audioManager.isAttemptingToConnect();
            logger.info(cName+fName+"isConnected="+isConnected);
            logger.info(cName+fName+"isAttemptingToConnect="+isAttemptingToConnect);
            if (!isConnected && !isAttemptingToConnect) {
                for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                    logger.info(cName+fName+"voiceChannel"+voiceChannel.getId()+"|"+voiceChannel.getName());
                    audioManager.openAudioConnection(voiceChannel);return true;
                }
            }
            return false;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }
    public Boolean connectToVoiceChannel(VoiceChannel voiceChannel) {
        String fName="[connectToVoiceChannel]";
        logger.info(cName+fName);
        try{
            logger.info(cName+fName+"voiceChannel"+voiceChannel.getId()+"|"+voiceChannel.getName());
            AudioManager audioManager=gGuild.getAudioManager();
            boolean isConnected=audioManager.isConnected();
            boolean isAttemptingToConnect=audioManager.isAttemptingToConnect();
            logger.info(cName+fName+"isConnected="+isConnected);
            logger.info(cName+fName+"isAttemptingToConnect="+isAttemptingToConnect);
            if (!isConnected && !isAttemptingToConnect) {
                audioManager.openAudioConnection(voiceChannel);return true;
            }
            return false;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }
    public int isConnected() {
        String fName="[isConnected]";
        logger.info(cName+fName);
        try{
            AudioManager audioManager=gGuild.getAudioManager();
            boolean isConnected=audioManager.isConnected();
            boolean isAttemptingToConnect=audioManager.isAttemptingToConnect();
            logger.info(cName+fName+"isConnected="+isConnected);
            logger.info(cName+fName+"isAttemptingToConnect="+isAttemptingToConnect);
            VoiceChannel voiceChannel=audioManager.getConnectedChannel();
            if(voiceChannel!=null){
                logger.info(cName+fName+"voiceChannel"+voiceChannel.getId()+"|"+voiceChannel.getName());
            }
            if (isConnected) {
                return 1;
            }
            return 0;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return -1;
        }
    }
    public VoiceChannel getConnectedVoiceChannel() {
        String fName="[getConnectedVoiceChannel]";
        logger.info(cName+fName);
        try{
            AudioManager audioManager=gGuild.getAudioManager();
            boolean isConnected=audioManager.isConnected();
            boolean isAttemptingToConnect=audioManager.isAttemptingToConnect();
            logger.info(cName+fName+"isConnected="+isConnected);
            logger.info(cName+fName+"isAttemptingToConnect="+isAttemptingToConnect);
            VoiceChannel voiceChannel=audioManager.getConnectedChannel();
            if (isConnected) {
                if(voiceChannel!=null){
                    logger.info(cName+fName+"voiceChannel"+voiceChannel.getId()+"|"+voiceChannel.getName());
                }
                return voiceChannel;
            }
            return null;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return null;
        }
    }
    public Boolean disconnectFromVoiceChannel() {
        String fName="[disconnectFromVoiceChannel]";
        logger.info(cName+fName);
        try{
            AudioManager audioManager=gGuild.getAudioManager();
            boolean isConnected=audioManager.isConnected();
            boolean isAttemptingToConnect=audioManager.isAttemptingToConnect();
            logger.info(cName+fName+"isConnected="+isConnected);
            logger.info(cName+fName+"isAttemptingToConnect="+isAttemptingToConnect);
            VoiceChannel voiceChannel=audioManager.getConnectedChannel();
            if(voiceChannel!=null){
                logger.info(cName+fName+"voiceChannel"+voiceChannel.getId()+"|"+voiceChannel.getName());
            }
            if (isConnected) {
                audioManager.closeAudioConnection();return true;
            }
            return false;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }

    //not used functions
    /*public Boolean skipTrack() {
        String fName="[skipTrack]";
        logger.info(cName+fName);
        return skipTrack(null);
    }
    public void loadAndPlayQueue(String trackUrl){
        String fName="[loadAndPlay]";
        logger.info(cName+fName);
        loadAndPlayQueue(null,trackUrl);
    }
    public Boolean playTrack(AudioTrack track) {
        String fName="[playQueue]";
        logger.info(cName+fName);
        try{
            connectToFirstVoiceChannel();
            player.playTrack(track);
            return true;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }
    public void setSendingHandler(){
        String fName="[setSendingHandler]";
        logger.info(cName+fName);
        gGuild.getAudioManager().setSendingHandler(getSendHandler());
    }
    public void queueClear() {
        String fName="[queueClear]";
        logger.info(cName+fName);
        scheduler.queue.clear();
    }
    public Boolean queueAdd(AudioTrack a) {
        String fName="[queueAdd]";
        logger.info(cName+fName);
        try{
            Boolean result=scheduler.queue.add(a);
            logger.info(cName+fName+"result="+result);
            return result;
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
            return false;
        }
    }
    public void selfDeafened() {
        String fName="[selfDeafened]";
        logger.info(cName+fName);
        try {
            AudioManager audioManager = gGuild.getAudioManager();
            audioManager.setSelfDeafened(true);
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
        }
    }
    public void selfUnDeafened() {
        String fName="[selfUnDeafened]";
        logger.info(cName+fName);
        try {
            AudioManager audioManager = gGuild.getAudioManager();
            audioManager.setSelfDeafened(false);
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
        }
    }
    public void selfMuted() {
        String fName="[selfMuted]";
        logger.info(cName+fName);
        try {
            AudioManager audioManager = gGuild.getAudioManager();
            audioManager.setSelfMuted(true);
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
        }
    }
    public void selfUnMuted() {
        String fName="[selfUnMuted]";
        logger.info(cName+fName);
        try {
            AudioManager audioManager = gGuild.getAudioManager();
            audioManager.setSelfMuted(false);
        }
        catch (Exception e){
            logger.error(cName+fName+"Exception:"+e);
        }
    }*/
}
