package FlexiPlay;

import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.UserAudio;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import com.google.common.primitives.Bytes;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.ArrayList;

public class cAudioSaver implements AudioReceiveHandler {
    Logger logger = Logger.getLogger(getClass());
    private ArrayList<byte[]> saveQueue = new ArrayList<>();


    @Override
    public boolean canReceiveCombined() {
        return true;
    }

    @Override
    public boolean canReceiveUser() {
        return false;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        for (User user : combinedAudio.getUsers()) {
            System.out.println(user.getId());
        }
        double volume = 1.0D;
        saveQueue.add(combinedAudio.getAudioData(volume));

        System.out.println("ADDED!");
    }

    @Override
    public void handleUserAudio(UserAudio userAudio) {
    }

    public File save(long guildID) {
        try {
            ArrayList<Byte> bytesProper = new ArrayList<>();
            for (byte[] bytes : saveQueue) {
                for (byte b : bytes) {
                    bytesProper.add(b);
                }
            }
           // FlexiUtils.logger.info("Queue size for " + guildID + ": " + saveQueue.size());
            byte[] finalizedArr = Bytes.toArray(bytesProper);
            //byte[] finalizedArr=bytesProper.toArray();
            InputStream b_in = new ByteArrayInputStream(finalizedArr);
            File file = File.createTempFile("audioSave-" + guildID, ".wav");
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(File.createTempFile("audioSaveRaw-" + guildID, ".wav")));
            dos.write(finalizedArr);
            AudioInputStream stream = new AudioInputStream(b_in, AudioReceiveHandler.OUTPUT_FORMAT,
                    finalizedArr.length);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
            System.out.println("File saved: " + file.getName() + ", bytes: "
                    + finalizedArr.length);
            return file;
        } catch (Exception e) {
            //FlexiUtils.logger.error("Exception: " + e);
        }
        return null;
    }
    public File save(String name) {
        try {
            ArrayList<Byte> bytesProper = new ArrayList<>();
            for (byte[] bytes : saveQueue) {
                for (byte b : bytes) {
                    bytesProper.add(b);
                }
            }
            // FlexiUtils.logger.info("Queue size for " + guildID + ": " + saveQueue.size());
            byte[] finalizedArr = Bytes.toArray(bytesProper);
            //byte[] finalizedArr=bytesProper.toArray();
            InputStream b_in = new ByteArrayInputStream(finalizedArr);
            File file = File.createTempFile(name, ".wav");
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(File.createTempFile("audioSaveRaw-" + name, ".wav")));
            dos.write(finalizedArr);
            AudioInputStream stream = new AudioInputStream(b_in, AudioReceiveHandler.OUTPUT_FORMAT,
                    finalizedArr.length);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
            System.out.println("File saved: " + file.getName() + ", bytes: "
                    + finalizedArr.length);
            return file;
        } catch (Exception e) {
            //FlexiUtils.logger.error("Exception: " + e);
        }
        return null;
    }
    public File save(String name,String ext,AudioFileFormat.Type type) {
        try {
            ArrayList<Byte> bytesProper = new ArrayList<>();
            for (byte[] bytes : saveQueue) {
                for (byte b : bytes) {
                    bytesProper.add(b);
                }
            }
            // FlexiUtils.logger.info("Queue size for " + guildID + ": " + saveQueue.size());
            byte[] finalizedArr = Bytes.toArray(bytesProper);
            //byte[] finalizedArr=bytesProper.toArray();
            InputStream b_in = new ByteArrayInputStream(finalizedArr);
            File file = File.createTempFile(name, ext);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(File.createTempFile("audioSaveRaw-" + name, ext)));
            dos.write(finalizedArr);
            AudioInputStream stream = new AudioInputStream(b_in, AudioReceiveHandler.OUTPUT_FORMAT,
                    finalizedArr.length);
            AudioSystem.write(stream, type, file);
            System.out.println("File saved: " + file.getName() + ", bytes: "
                    + finalizedArr.length);
            return file;
        } catch (Exception e) {
            //FlexiUtils.logger.error("Exception: " + e);
        }
        return null;
    }
}
