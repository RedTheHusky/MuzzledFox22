package events.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {
    public ByteBuffer buffer;
    public MutableAudioFrame frame;
    String cName="[AudioPlayerSendHandler]";
    Logger logger = Logger.getLogger(getClass());
    public AudioPlayer audioPlayer;
    public AudioFrame lastFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {

        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {

        return true;
    }
}
