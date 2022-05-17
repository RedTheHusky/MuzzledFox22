package util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import models.lc.lcGuildMusicManager;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class utilityMusic extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[utilityMusic]";
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public utilityMusic(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = "Utility-Music";
        this.help = "voice stuff";
        this.aliases = new String[]{"utilitymusic","musicutility","music"};
        this.guildOnly = true;
        this.category=llCommandCategory_BuildAlpha;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(cName+fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        EventWaiter gWaiter;
        private User gUser;
        private Member gMember; String gTitle="Music utility";
        private Guild gGuild;lcGuildMusicManager musicPlayer;
        private TextChannel gTextChannel;VoiceChannel gVoiceChannel;private Message gMessage;

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gWaiter = gGlobal.waiter;
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                Boolean isInvalidCommand = true;
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(fName + ".Args");
                    if (!llMemberIsStaff(gMember)) {
                        llSendQuickEmbedMessage(gUser, "Music utility", "Denied!", llColorRed);
                        llMessageDelete(gCommandEvent);
                        return;
                    }
                    musicPlayer = gGlobal.getAudioPlayer(gGuild,true);
                    // split the choices on all whitespace
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if(items.length >= 2){
                        if (items[0].equalsIgnoreCase("setup")) {
                            if (items[1].equalsIgnoreCase("reset")) {
                                resetAudioManagerAndPlayer();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("createmanager")) {
                                createAudioManager();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("createplayer")) {
                                createAudioPlayer();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("forcecreateplayer")) {
                                forceCreateAudioPlayer();
                                isInvalidCommand = false;
                            }
                        }

                        if (items[0].equalsIgnoreCase("loadandplay")) {
                            loadAndPlayQueue(items[1]);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("loadandqueue")) {
                            loadAndPlayQueue(items[1]);
                            isInvalidCommand = false;
                        }
                        if(items[0].equalsIgnoreCase("self")){
                            if (items[1].equalsIgnoreCase("mute")) {
                                selfMuted();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("unmute")) {
                                selfUnMuted();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("deafen")) {
                                selfDeafened();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("undeafen")) {
                                selfUnDeafened();
                                isInvalidCommand = false;
                            }
                        }
                        if(items[0].equalsIgnoreCase("volume")){
                            if (items[1].equalsIgnoreCase("-")) {
                                volumeDecreased(1);
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("--")) {
                                volumeDecreased(5);
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("---")) {
                                volumeDecreased(20);
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("+")) {
                                volumeIncrease(1);
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("++")) {
                                volumeIncrease(5);
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("+++")) {
                                volumeIncrease(20);
                                isInvalidCommand = false;
                            }
                            Integer i= Integer.valueOf(items[1]);
                            if (i<=100&&i>0) {
                                volumeSet(i);
                                isInvalidCommand = false;
                            }
                        }
                    }
                    if(items.length>=1&&isInvalidCommand){

                        if (items[0].equalsIgnoreCase("help")) {
                            help("main");
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("connect")) {
                            connect();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("disconnect")) {
                            disconnect();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("skip")) {
                            skipToNextTrack();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("pause")) {
                            pause();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("resume")) {
                            resume();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("stop")) {
                            stop();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("mute")) {
                            mute();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("unmute")) {
                            unmute();
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume-")) {
                            volumeDecreased(1);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume--")) {
                            volumeDecreased(5);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume---")) {
                            volumeDecreased(20);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume+")) {
                            volumeIncrease(1);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume++")) {
                            volumeIncrease(5);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("volume+++")) {
                            volumeIncrease(20);
                            isInvalidCommand = false;
                        }
                    }


                }
                logger.info(fName + ".deleting op message");
                llMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    llSendMessage(gUser, "Provided an incorrect command!");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(".run ended");
        }

        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + "music ";
            String desc = "n/a";
            if (command.equalsIgnoreCase("main")) {
                desc = "`" +quickSummonWithSpace + "connect`";desc += " `" + quickSummonWithSpace + "disconnect`";
                desc += "\n`" + quickSummonWithSpace + "loadandplay` <link>";
                desc += "\n`" + quickSummonWithSpace + "volume <parameters>`";
                desc += "\n`" + quickSummonWithSpace + "mute`";desc += " `" + quickSummonWithSpace + "unmute`";
                desc += "\n`" + quickSummonWithSpace + "pause`";desc += " `" + quickSummonWithSpace + "resume`";
                desc += "\n`" + quickSummonWithSpace + "skip`";desc += " `" + quickSummonWithSpace + "stop`";
            }
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendQuickEmbedMessage(gUser, gTitle,desc,llColorBlue1);
        }
        private void loadAndPlayQueue(String command){
            String fName = "loadAndPlayQueue";
            logger.info(fName + ".command:" + command);
            connect2User();
            musicPlayer.loadAndPlayQueue(gTextChannel,command);
        }
        private void skipToNextTrack(){
            String fName = "skipToNextTrack";
            logger.info(fName);
            connect2User();
            musicPlayer.skipTrack(gTextChannel);
        }
        private void mute(){
            String fName = "skipToNextTrack";
            logger.info(fName);
            musicPlayer.mute();
            llSendMessage(gTextChannel,":mute: Muted");
        }
        private void volumeSet(int newVolume){
            String fName = "volumeDecreased";
            logger.info(fName);
            logger.info(fName+"newVolume="+newVolume);
            int currentVolume=musicPlayer.player.getVolume();
            logger.info(fName+"currentVolume="+currentVolume);
            if(newVolume>100)newVolume=100;
            if(newVolume<0)newVolume=0;
            musicPlayer.setVolume(newVolume);
            llSendMessage(gTextChannel,"Volume Set to "+newVolume);
        }
        private void volumeDecreased(int i){
            String fName = "volumeDecreased";
            logger.info(fName);
            logger.info(fName+"i="+i);
            int currentVolume=musicPlayer.player.getVolume();
            logger.info(fName+"currentVolume="+currentVolume);
            int difVolume=currentVolume-i;
            logger.info(fName+"difVolume="+difVolume);
            if(difVolume<0)difVolume=0;
            musicPlayer.setVolume(difVolume);
            llSendMessage(gTextChannel,"Volume Decreased to "+difVolume);
        }
        private void volumeIncrease(int i){
            String fName = "volumeIncrease";
            logger.info(fName);
            logger.info(fName+"i="+i);
            int currentVolume=musicPlayer.player.getVolume();
            logger.info(fName+"currentVolume="+currentVolume);
            int difVolume=currentVolume+i;
            logger.info(fName+"difVolume="+difVolume);
            if(difVolume>100)difVolume=100;
            musicPlayer.setVolume(difVolume);
            llSendMessage(gTextChannel,"Volume Increase to "+difVolume);
        }
        private void unmute(){
            String fName = "skipToNextTrack";
            logger.info(fName);
            musicPlayer.unmute();
            llSendMessage(gTextChannel,":sound:Unmuted");
        }
        private void pause(){
            String fName = "pause";
            logger.info(fName);
            musicPlayer.pause();
            llSendMessage(gTextChannel,":pause_button: Paused");
        }
        private void resume(){
            String fName = "resume";
            logger.info(fName);
            musicPlayer.unpause();
            llSendMessage(gTextChannel,": :play_pause: Resume");
        }
        private void stop(){
            String fName = "stop";
            logger.info(fName);
            musicPlayer.stop();
            llSendMessage(gTextChannel,":no_entry: Stopped");
            if(musicPlayer.isConnected()==1){
                musicPlayer.disconnectFromVoiceChannel();
            }
        }
        private void connect(){
            String fName = "connect";
            logger.info(cName+fName);
            gVoiceChannel= Objects.requireNonNull(gMember.getVoiceState()).getChannel();
            if(gVoiceChannel==null){
                logger.error(cName+fName+"not present in any voice channel");
                llSendMessage(gTextChannel," Not active to any voice channel!");
                return;
            }
            logger.info(fName + ".gVoiceChannel:" + gVoiceChannel.getId() + "|" + gVoiceChannel.getName());
            if(musicPlayer.isConnected()==-1){
                llSendMessage(gTextChannel,"Error to check connection");
                return;
            }
            if(musicPlayer.isConnected()==1){
                VoiceChannel connectedVoiceChannel= musicPlayer.getConnectedVoiceChannel();
                logger.info(fName + ".connectedVoiceChannel:" + connectedVoiceChannel.getId() + "|" + connectedVoiceChannel.getName());
                if(connectedVoiceChannel.getId().equalsIgnoreCase(gVoiceChannel.getId())){
                    logger.warn(cName+fName+"same so dont do nothing");
                    llSendMessage(gTextChannel," Already connected to that channel!");
                    return;
                }
                musicPlayer.disconnectFromVoiceChannel();
                musicPlayer.connectToVoiceChannel(gVoiceChannel);
                llSendMessage(gTextChannel," Disconnecting from "+connectedVoiceChannel.getName()+" and connecting to "+gVoiceChannel.getName());
                return;
            }
            musicPlayer.connectToVoiceChannel(gVoiceChannel);
            llSendMessage(gTextChannel," Connecting to voice channel: "+gVoiceChannel.getName());
        }
        private void connect2User(){
            String fName = "connect2User";
            logger.info(cName+fName);
            gVoiceChannel= Objects.requireNonNull(gMember.getVoiceState()).getChannel();
            if(gVoiceChannel==null){
                logger.error(cName+fName+"not present in any voice channel");
            }
            logger.info(fName + ".gVoiceChannel:" + gVoiceChannel.getId() + "|" + gVoiceChannel.getName());
            if(musicPlayer.isConnected()!=1){
                musicPlayer.connectToVoiceChannel(gVoiceChannel);
            }
        }
        private void disconnect(){
            String fName = "disconnect";
            logger.info(cName+fName);
            gVoiceChannel= Objects.requireNonNull(gMember.getVoiceState()).getChannel();
            if(gVoiceChannel==null){
                llSendMessage(gTextChannel," Not active to any voice channel!");
            }
            logger.info(fName + ".gVoiceChannel:" + gVoiceChannel.getId() + "|" + gVoiceChannel.getName());
            musicPlayer.disconnectFromVoiceChannel();
        }
        public void selfDeafened() {
            String fName="[selfDeafened]";
            logger.info(cName+fName);
            Member selfMember = gCommandEvent.getSelfMember();
            selfMember.deafen(true).reason("Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                if (error != null){
                    logger.error(fName+"exception:"+error);
                    llSendMessage(gTextChannel,"Exception:"+error);
                }else{
                    logger.info(fName+"success");
                    llSendMessage(gTextChannel,":sound:Bot Deafened");
                }
            });
        }
        public void selfUnDeafened() {
            String fName="[selfUnDeafened]";
            logger.info(cName+fName);
            Member selfMember = gCommandEvent.getSelfMember();
            selfMember.deafen(false).reason("Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                if (error != null){
                    logger.error(fName+"exception:"+error);
                    llSendMessage(gTextChannel,"Exception:"+error);
                }else{
                    logger.info(fName+"success");
                    llSendMessage(gTextChannel,":sound:Bot Un-deafened");
                }
            });
        }
        public void selfMuted() {
            String fName="[selfMuted]";
            logger.info(cName+fName);
            Member selfMember = gCommandEvent.getSelfMember();
            selfMember.mute(true).reason("Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                if (error != null){
                    logger.error(fName+"exception:"+error);
                    llSendMessage(gTextChannel,"Exception:"+error);
                }else{
                    logger.info(fName+"success");
                    llSendMessage(gTextChannel,":sound:Bot Muted");
                }
            });
        }
        public void selfUnMuted() {
            String fName="[selfUnMuted]";
            logger.info(cName+fName);
            Member selfMember = gCommandEvent.getSelfMember();
            selfMember.mute(false).reason("Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                if (error != null){
                    logger.error(fName+"exception:"+error);
                    llSendMessage(gTextChannel,"Exception:"+error);
                }else{
                    logger.info(fName+"success");
                    llSendMessage(gTextChannel,":sound:Bot Un-muted");
                }
            });
        }
        public void createAudioPlayer() {
            String fName="[createAudioPlaye]";
            logger.info(cName+fName);
            if(!llMemberIsStaff(gMember)){
                logger.warn(cName+fName+"not a staff");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Denied!",llColorRed);
                return;
            }
            try {
                String guildId=gGuild.getId();
                if(!gGlobal.musicManagers.containsKey(guildId)){
                    logger.info(cName+fName+"create");
                    lcGuildMusicManager musicManager = new lcGuildMusicManager(gGlobal.playerManager,gGuild,gGlobal);
                    //musicManager.getSendHandler();
                    gGlobal.musicManagers.put(guildId, musicManager);
                    logger.info(cName+fName+"created");
                }else{
                    logger.info(cName+fName+"already exists");
                }
            } catch (Exception e) {
                logger.error(fName + ".exception:" + e);
            }
        }
        public void forceCreateAudioPlayer() {
            String fName="[forceCreateAudioPlayer]";
            logger.info(cName+fName);
            if(!llMemberIsStaff(gMember)){
                logger.warn(cName+fName+"not a staff");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Denied!",llColorRed);
                return;
            }
            try {
                String guildId=gGuild.getId();
                lcGuildMusicManager musicManager = new lcGuildMusicManager(gGlobal.playerManager,gGuild,gGlobal);
                //musicManager.getSendHandler();
                gGlobal.musicManagers.put(guildId, musicManager);
                logger.info(cName+fName+"created");
            } catch (Exception e) {
                logger.error(fName + ".exception:" + e);
            }
        }
        public void createAudioManager(){
            String fName="[createAudioManage]";
            logger.info(cName+fName);
            if(!llMemberIsStaff(gMember)){
                logger.warn(cName+fName+"not a staff");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Denied!",llColorRed);
                return;
            }
            try {
                gGlobal.playerManager = new DefaultAudioPlayerManager();
                if(gGlobal.playerManager!=null){
                    logger.info(fName + ".is not null");
                    AudioSourceManagers.registerRemoteSources(gGlobal.playerManager);
                    AudioSourceManagers.registerLocalSource(gGlobal.playerManager);
                }else{
                    logger.error(fName + ".is null");
                }
            } catch (Exception e) {
                logger.error(fName + ".exception:" + e);
            }
        }
        public void resetAudioManagerAndPlayer(){
            String fName="[resetAudioManagerAndPlayer]";
            logger.info(cName+fName);
            if(!llMemberIsStaff(gMember)){
                logger.warn(cName+fName+"not a staff");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Denied!",llColorRed);
                return;
            }
            try {
                try {
                    createAudioManager();
                    Map<String,Guild> guilds=gGlobal.getGuildMap4JDAv2();
                    for(Map.Entry<String, Guild> guildEntry : guilds.entrySet()) {
                        String key = guildEntry.getKey();
                        Guild value = guildEntry.getValue();
                        String guildId=value.getId();
                        lcGuildMusicManager musicManager = new lcGuildMusicManager(gGlobal.playerManager,value,gGlobal);
                        //musicManager.getSendHandler();
                        gGlobal.musicManagers.put(guildId, musicManager);
                        logger.info(cName+fName+"created");
                    }
                } catch (Exception e) {
                    logger.error(fName + ".exception:" + e);
                }
            } catch (Exception e) {
                logger.error(fName + ".exception:" + e);
            }
        }
    }
}
