package events.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    String cName="[TrackScheduler]";
    Logger logger = Logger.getLogger(getClass());
    public   AudioPlayer player;
    public BlockingQueue<AudioTrack> queue;


    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue =  new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        String fName="[queue]";
        if (!player.startTrack(track, true)) {
            queue.offer(track);
            logger.info(cName+fName+"enter");
        }else{
            logger.info(cName+fName+"skip");
        }
    }
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        String fName="[nextTrack]";
        player.startTrack(queue.poll(), false);
        logger.info(cName+fName+"enter");
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
        logger.info(cName+"onPlayerPause");
    }


    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
        logger.info(cName+"onPlayerResume");
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
        logger.info(cName+"onTrackStart");
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        String fName="[onTrackEnd]";
        logger.info(cName+fName);
        if (endReason.mayStartNext) {
            logger.info(cName+fName+"mayStartNext");
            nextTrack();
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
        logger.info(cName+"onTrackException");
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
        logger.info(cName+"onTrackStuck");
    }
}
