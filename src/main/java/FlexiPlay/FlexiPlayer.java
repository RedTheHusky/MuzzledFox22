package FlexiPlay;

import FlexiPlay.entities.cAudioSaver;
import FlexiPlay.entities.cGuildMusicManager;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static FlexiPlay.iFlexiPlayer.*;
import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class FlexiPlayer extends Command implements llGlobalHelper {
    //https://github.com/techied/FlexiPlayr/blob/b23f3895788d095829207b7785de6708a6ea4319/src/main/java/command/Record.java
    //https://github.com/sedmelluq/lavaplayer
    Logger logger = Logger.getLogger(getClass()); String cName="[FlexiPlayer]";
    lcGlobalHelper gGlobal;
    String gTitle="AudioPlayer",gCommand="audioplay";
    public static Map<Long, cGuildMusicManager> musicManagers=new HashMap<>();
    public static Map<Long, cAudioSaver> recordings=new HashMap<>();
    public FlexiPlayer(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "FlexiPlayer";
        this.help = "Converts text to ASCII banner.";
        this.aliases = new String[]{gCommand,"flexiplay2"};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(cName+fName);
        if(llDebug){
            logger.info(cName+fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        CommandEvent gEvent;String cName = "[runLocal]";
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
        Message gMessage;
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage=gEvent.getMessage();

        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"FlexiPlayer",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand=true;

                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);

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
                                    getChannels(type,false);isInvalidCommand=false;
                                }else{
                                    setChannel(type,action,gEvent.getMessage());
                                }
                            }
                            else if(group==2){
                                if(action==0){
                                    getRoles(type,false);isInvalidCommand=false;
                                }else{
                                    setRole(type,action,gEvent.getMessage());
                                }
                            }
                        }else{
                            menuGuild();isInvalidCommand=false;
                        }
                    }
                    else if(items[0].equalsIgnoreCase("help")){
                        help("main"); isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else switch (items[0].toLowerCase()){
                            case"play":if(items.length>=2){
                                    loadAndPlay(items[1]); isInvalidCommand=false;
                                }
                                break;
                            case"resume":
                                resume(); isInvalidCommand=false;
                                break;
                            case"pause":
                                pause(); isInvalidCommand=false;
                                break;
                            case"stop":
                                stop(); isInvalidCommand=false;
                                break;
                            case"clear":
                                clear(); isInvalidCommand=false;
                                break;
                            case "queue":
                                showQueue(); isInvalidCommand=false;
                                break;
                            case"shuffle":
                                shuffle(); isInvalidCommand=false;
                                break;
                            case "leave":
                                leave(); isInvalidCommand=false;
                                break;
                            case "join":
                                join(); isInvalidCommand=false;
                                break;
                            case "volume": if(items.length>=2){
                                volume(items[1]); isInvalidCommand=false;
                                }
                                break;
                            case"skip":
                                skip(); isInvalidCommand=false;
                                break;
                            case"status":
                                status(); isInvalidCommand=false;
                                break;
                            case"register":
                                registerPlayerManager(true); isInvalidCommand=false;
                                break;
                            case"registerforce":
                                registerForcePlayerManager(true); isInvalidCommand=false;
                                break;
                            case"record":
                                if(items.length>=2){
                                    if(items[1].equalsIgnoreCase("start")){
                                        record_start();
                                    }
                                    else if(items[1].equalsIgnoreCase("stop")){
                                        record_stop();
                                    }
                                }else{
                                    record_toggle();
                                }
                                isInvalidCommand=false;
                                break;
                            case"add": if(items.length>=2){
                                    add2PlayList(items[1]); isInvalidCommand=false;
                                }
                                break;
                            case "sub": case"rem": if(items.length>=2){
                                rem2PlayList(items[1]); isInvalidCommand=false;
                            }
                                break;
                    }


                }
                logger.info(cName+fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(gEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", lsMessageHelper.llColorRed);
                }
                logger.info(cName+".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+"\nStackTrace: "+Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);String desc="";
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(lsMessageHelper.llColorGreen2);
            desc+="\n`"+quickSummonWithSpace+"status`";
            desc+="\n`"+quickSummonWithSpace+"join/leave`";
            desc+="\n`"+quickSummonWithSpace+"volume [1-100]`";
            desc+="\n`"+quickSummonWithSpace+"play <url/title>`";
            desc+="\n`"+quickSummonWithSpace+"stop`";
            desc+="\n`"+quickSummonWithSpace+"pause/resume`";
            desc+="\n`"+quickSummonWithSpace+"skip`";
            desc+="\n`"+quickSummonWithSpace+"shuffle`";
            desc+="\n`"+quickSummonWithSpace+"queue`";
            desc+="\n`"+quickSummonWithSpace+"record` to start and stop recording. Recording time 30 seconds.";
            if(lsMemberIsBotOwner(gMember)){
                desc+="\n`"+quickSummonWithSpace+"add <url/title>`";
                desc+="\n`"+quickSummonWithSpace+"rem <url/title/index>`";
            }
            embedBuilder.setDescription(desc);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }
        cGuildMusicManager guildMusicManager=null;
        AudioManager guildAudioManager;
        public boolean getAudioManager() {
            String fName="[getAudioManager]";
            registerPlayerManager(false);
            guildAudioManager=gGuild.getAudioManager();
            logger.info(fName + "result=true");
            return true;
        }
        public boolean getMusicManager() {
            String fName="[getMusicManager]";
            guildMusicManager=getGuildAudioPlayer(musicManagers, gGlobal.playerManager,gGuild);
            if(guildMusicManager==null){
                musicManagers=newGuildAudioPlayer(musicManagers,gGlobal.playerManager,gGuild);
            }
            guildMusicManager=getGuildAudioPlayer(musicManagers, gGlobal.playerManager,gGuild);
            if(guildMusicManager==null){
                logger.info(fName + "result=false");
                return false;
            }
            logger.info(fName + "result=true");
            return true;
        }
        public void status() {
            String fName="[leave]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.addField("Manager","failed to get audio manager",false);
            }
            if(!getMusicManager()){
                embedBuilder.addField("Manager","failed to get music manager",false);
            }
            if(!isConnected2VoiceChannel(guildAudioManager)){
                embedBuilder.addField("isConnected2VoiceChannel","false",false);
            }else
            {
                VoiceChannel voiceChannel=guildAudioManager.getConnectedChannel();
                if(voiceChannel==null){
                    embedBuilder.addField("isConnected2VoiceChannel", "none", false);
                }else {
                    embedBuilder.addField("isConnected2VoiceChannel", voiceChannel.getName() + "(" + voiceChannel.getId() + ")", false);
                }
            }

            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
        private void loadAndPlay(String input) {
            String fName="[loadAndPlay]";
            logger.info(fName + "input="+input);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            String trackUrl;
            if ( input.contains("http://") ||  input.contains("https://")) {
                trackUrl=input;
            } else {
                trackUrl="ytsearch:" + input;
            }
            logger.info(fName + "trackUrl="+trackUrl);
            String finalTrackUrl = trackUrl;
            gGlobal.playerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Adding track to queue");
                    eb.setColor(new Color(0x7289da));
                    iFlexiPlayer.playTrack(track, eb);
                    lsMessageHelper.lsSendMessage(gTextChannel,eb);
                    cGuildMusicManager tmp= play(gTextChannel.getGuild(), guildMusicManager, track, gMember);
                    if(tmp!=null)guildMusicManager=tmp;
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    if (playlist.getTracks().size() == 0) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Something went wrong trying to play this playlist");
                        eb.setColor(new Color(0x7289da));
                        eb.setDescription("There might be some songs missing.\nAdd some and try again!");
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        return;
                    }

                    if (finalTrackUrl.startsWith("ytsearch:")) {
                        AudioTrack track = playlist.getTracks().get(0);
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Adding song to queue from search");
                        eb.setColor(new Color(0x7289da));
                        iFlexiPlayer.playTrack(track, eb);
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        cGuildMusicManager tmp= play(gTextChannel.getGuild(), guildMusicManager, track, gMember);
                        if(tmp!=null)guildMusicManager=tmp;
                    } else {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Adding playlist to queue");
                        eb.setAuthor("YouTube", null, "https://cdn.discordapp.com/emojis/535586488801558538.png");
                        eb.setDescription(playlist.getName());
                        eb.setColor(new Color(0x7289da));
                        eb.addField("Track count", playlist.getTracks().size() + "", true);
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        for (AudioTrack track : playlist.getTracks()) {
                            cGuildMusicManager tmp= play(gTextChannel.getGuild(), guildMusicManager, track, gMember);
                            if(tmp!=null)guildMusicManager=tmp;
                        }
                    }
                }

                @Override
                public void noMatches() {
                    gTextChannel.sendMessage("Nothing found by " + finalTrackUrl).queue();
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    gTextChannel.sendMessage("Could not play: " + exception.getMessage()).queue();
                }
            });
        }
        private void add2PlayList(String input) {
            String fName="[add2PlayList]";
            logger.info(fName + "input="+input);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            String trackUrl;
            if ( input.contains("http://") ||  input.contains("https://")) {
                trackUrl=input;
            } else {
                trackUrl="ytsearch:" + input;
            }
            logger.info(fName + "trackUrl="+trackUrl);
            String finalTrackUrl = trackUrl;
            gGlobal.playerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Adding track to queue");
                    eb.setColor(new Color(0x7289da));
                    iFlexiPlayer.playTrack(track, eb);
                    lsMessageHelper.lsSendMessage(gTextChannel,eb);
                    cGuildMusicManager tmp= add2Queue( guildMusicManager, track);
                    if(tmp!=null)guildMusicManager=tmp;
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    if (playlist.getTracks().size() == 0) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Something went wrong trying to play this playlist");
                        eb.setColor(new Color(0x7289da));
                        eb.setDescription("There might be some songs missing.\nAdd some and try again!");
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        return;
                    }

                    if (finalTrackUrl.startsWith("ytsearch:")) {
                        AudioTrack track = playlist.getTracks().get(0);
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Adding song to queue from search");
                        eb.setColor(new Color(0x7289da));
                        iFlexiPlayer.playTrack(track, eb);
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        cGuildMusicManager tmp= add2Queue( guildMusicManager, track);
                        if(tmp!=null)guildMusicManager=tmp;
                    } else {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Adding playlist to queue");
                        eb.setAuthor("YouTube", null, "https://cdn.discordapp.com/emojis/535586488801558538.png");
                        eb.setDescription(playlist.getName());
                        eb.setColor(new Color(0x7289da));
                        eb.addField("Track count", playlist.getTracks().size() + "", true);
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        for (AudioTrack track : playlist.getTracks()) {
                            cGuildMusicManager tmp= add2Queue( guildMusicManager, track);
                            if(tmp!=null)guildMusicManager=tmp;
                        }
                    }
                }

                @Override
                public void noMatches() {
                    gTextChannel.sendMessage("Nothing found by " + finalTrackUrl).queue();
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    gTextChannel.sendMessage("Could not play: " + exception.getMessage()).queue();
                }


            });
        }
        private void rem2PlayList(String input) {
            String fName="[rem2PlayList]";
            logger.info(fName + "input="+input);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return;
            }
            String trackUrl;
            int index=-1;
            try {
                index=Integer.parseInt(input);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(index>=0){
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Removing track from queue");
                eb.setColor(new Color(0x7289da));
                lsMessageHelper.lsSendMessage(gTextChannel,eb);
                cGuildMusicManager tmp= sub4Queue( guildMusicManager, index);
                if(tmp!=null)guildMusicManager=tmp;
            }else{
                if ( input.contains("http://") ||  input.contains("https://")) {
                    trackUrl=input;
                } else {
                    trackUrl="ytsearch:" + input;
                }
                logger.info(fName + "trackUrl="+trackUrl);
                String finalTrackUrl = trackUrl;
                gGlobal.playerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Removing track from queue");
                        eb.setColor(new Color(0x7289da));
                        iFlexiPlayer.playTrack(track, eb);
                        lsMessageHelper.lsSendMessage(gTextChannel,eb);
                        cGuildMusicManager tmp= sub4Queue( guildMusicManager, track);
                        if(tmp!=null)guildMusicManager=tmp;
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        if (playlist.getTracks().size() == 0) {
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setTitle("Something went wrong trying to play this playlist");
                            eb.setColor(new Color(0x7289da));
                            eb.setDescription("There might be some songs missing.\nTry again!");
                            lsMessageHelper.lsSendMessage(gTextChannel,eb);
                            return;
                        }

                        if (finalTrackUrl.startsWith("ytsearch:")) {
                            AudioTrack track = playlist.getTracks().get(0);
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setTitle("Removing song to queue from search");
                            eb.setColor(new Color(0x7289da));
                            iFlexiPlayer.playTrack(track, eb);
                            lsMessageHelper.lsSendMessage(gTextChannel,eb);
                            cGuildMusicManager tmp= sub4Queue( guildMusicManager, track);
                            if(tmp!=null)guildMusicManager=tmp;
                        } else {
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setTitle("Removing playlist from queue");
                            eb.setAuthor("YouTube", null, "https://cdn.discordapp.com/emojis/535586488801558538.png");
                            eb.setDescription(playlist.getName());
                            eb.setColor(new Color(0x7289da));
                            eb.addField("Track count", playlist.getTracks().size() + "", true);
                            lsMessageHelper.lsSendMessage(gTextChannel,eb);
                            for (AudioTrack track : playlist.getTracks()) {
                                cGuildMusicManager tmp=sub4Queue( guildMusicManager, track);
                                if(tmp!=null)guildMusicManager=tmp;
                            }
                        }
                    }

                    @Override
                    public void noMatches() {
                        gTextChannel.sendMessage("Nothing found by " + finalTrackUrl).queue();
                    }

                    @Override
                    public void loadFailed(FriendlyException exception) {
                        gTextChannel.sendMessage("Could not play: " + exception.getMessage()).queue();
                    }


                });
            }
        }
        public boolean resume() {
            String fName="[resume]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.scheduler.player.setPaused(false);
            embedBuilder.setDescription("Resume.");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean pause() {
            String fName="[pause]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.scheduler.player.setPaused(true);
            embedBuilder.setDescription("Paused.");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean stop() {
            String fName="[stop]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.scheduler.queue.clear();
            guildMusicManager.scheduler.nextTrack();
            embedBuilder.setDescription("Player stopped");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean clear() {
            String fName="[clear]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.scheduler.queue.clear();
            embedBuilder.setDescription("Queue cleared.");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        private boolean showQueue() {
            String fName="[showQueue]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            List<AudioTrack>audioTracks=iFlexiPlayer.getQueue(guildMusicManager);
            if(audioTracks==null||audioTracks.size()==0){
                embedBuilder.setDescription("No queue available");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
                /*Paginator paginator = new Paginator.Builder().setText("Here's your queue:").addItems(items).setColor(new Color(0x7289da)).setItemsPerPage(5).setBulkSkipNumber(3).setEventWaiter(FlexiUtils.waiter).build();
                paginator.display(gTextChannel);*/
            showQueuePagination(audioTracks,-1,0);
            return true;
        }
        private void showQueuePagination(List<AudioTrack>audioTracks,int previous,int next) {
            String fName="[showQueuePagination]";
            logger.info(fName + "previous="+previous+",  next="+next);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(0x7289da));
            eb.setTitle("Player");
            String desc="",tmp;boolean requirePagination=false;int i;
            for (i = next; i < audioTracks.size(); i++) {
                AudioTrack track = audioTracks.get(i);
                tmp=desc;
                tmp+="\n"+i+" " +track.getInfo().title + " (" + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length),
                        TimeUnit.MILLISECONDS.toSeconds(track.getInfo().length) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length))
                ) + ")";
                if(tmp.length()<2000){
                    desc=tmp;
                }else{
                    requirePagination=true;
                    break;
                }
            }
            eb.setDescription(desc);
            if(!requirePagination){
                lsMessageHelper.lsSendMessage(gTextChannel,eb);
            }else{
                lsMessageHelper.lsSendMessageResponse(gTextChannel,eb);
            }
        }
        public boolean shuffle() {
            String fName="[shuffle]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            List<AudioTrack> queue=iFlexiPlayer.getQueue(guildMusicManager);
            if(queue==null||queue.isEmpty()){
                embedBuilder.setDescription("Queue is empty!");
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            Collections.shuffle(queue);
            guildMusicManager.scheduler.queue=new LinkedBlockingQueue<>(queue);
            embedBuilder.setDescription("Queue shuffled!");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean skip() {
            String fName="[skip]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.scheduler.nextTrack();
            embedBuilder.setDescription("Skipped!");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean volume(String input) {
            String fName="[volume]";
            logger.info(fName + "input="+input);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            int percentage=Integer.parseInt(input);
            logger.info(fName + "percentage="+percentage);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildMusicManager.getPlayer().setVolume(percentage);
            embedBuilder.setDescription("Volume set to "+percentage+"%.");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean leave() {
            String fName="[leave]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!isConnected2VoiceChannel(guildAudioManager)){
                embedBuilder.setDescription("Not connected to any voice channel!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            guildAudioManager.closeAudioConnection();
            embedBuilder.setDescription("Left the voice channel!");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean leaveForce() {
            String fName="[leaveForce]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            gGuild.getAudioManager().closeAudioConnection();
            embedBuilder.setDescription("Left the voice channel!");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean join() {
            String fName="[join]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(isConnected2VoiceChannel(guildAudioManager)){
                logger.info(fName + "Already connected to a voice channel");
                if(!isMemberInVoiceChannel(guildAudioManager, gMember)){
                    embedBuilder.setDescription("Failed to relocate as could not find "+gMember.getAsMention()+" in any voice channels!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
                if(!disconnect4VoiceChannel(guildAudioManager)){
                    embedBuilder.setDescription("Failed to disconnect from voice channel!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
                if(!connect2VoiceChannel(guildAudioManager, gMember)){
                    embedBuilder.setDescription("Failed to connect to voice channel!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
            }else{
                if(!isMemberInVoiceChannel(guildAudioManager, gMember)){
                    embedBuilder.setDescription("Failed to connect as could not find "+gMember.getAsMention()+" in any voice channels!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
                if(!connect2VoiceChannel(guildAudioManager, gMember)){
                    embedBuilder.setDescription("Failed to connect to voice channel!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
            }
            embedBuilder.setDescription("Joined voice channel!");
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean joinAndRelocateSub(boolean displayMessage) {
            String fName="[joinAndRelocateSub]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(isConnected2VoiceChannel(guildAudioManager)){
                logger.info(fName + "Already connected to a voice channel>relocate");
                if(!isMemberInVoiceChannel(guildAudioManager, gMember)){
                    logger.info(fName + "Failed to relocate as could not find "+gMember.getId()+" in any voice channels!");
                    if(displayMessage){
                        embedBuilder.setDescription("Failed to relocate as could not find "+gMember.getAsMention()+" in any voice channels!");
                        embedBuilder.setColor(llColors.llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                    return false;
                }
                if(!disconnect4VoiceChannel(guildAudioManager)){
                    logger.info(fName +"Failed to disconnect from voice channel in order to relocate!");
                    if(displayMessage){
                        embedBuilder.setDescription("Failed to disconnect from voice channel in order to relocate!");
                        embedBuilder.setColor(llColors.llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                    return false;
                }
                if(!connect2VoiceChannel(guildAudioManager, gMember)){
                    logger.info(fName +"Failed to connect to relocated voice channel!");
                    if(displayMessage){
                        embedBuilder.setDescription("Failed to connect to relocated voice channel!");
                        embedBuilder.setColor(llColors.llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                    return false;
                }
            }else{
                if(!isMemberInVoiceChannel(guildAudioManager, gMember)){
                    logger.info(fName +"Failed to connect as could not find "+gMember.getId()+" in any voice channels!");
                    if(displayMessage){
                        embedBuilder.setDescription("Failed to connect as could not find "+gMember.getAsMention()+" in any voice channels!");
                        embedBuilder.setColor(llColors.llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                    return false;
                }
                if(!connect2VoiceChannel(guildAudioManager, gMember)){
                    logger.info(fName +"Failed to connect to voice channel!");
                    if(displayMessage){
                        embedBuilder.setDescription("Failed to connect to voice channel!");
                        embedBuilder.setColor(llColors.llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    }
                    return false;
                }
            }
            logger.info(fName +"Joined voice channel!");
            return true;
        }
        public boolean record_toggle() {
            String fName="[record_start]";
            logger.info(fName + "");
            if(recordings.containsKey(gGuild.getIdLong())){
                return record_stop();
            }else{
                return record_start();
            }
        }
        public boolean record_start() {
            String fName="[record_start]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(recordings.containsKey(gGuild.getIdLong())){
                if(!isConnected2VoiceChannel(guildAudioManager)){
                    logger.info(fName + "Recording started but its not connected to a voice channel!");
                    embedBuilder.setDescription("Recording started but its not connected to a voice channel!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }else{
                    logger.info(fName + "Already recording!");
                    embedBuilder.setDescription("Already recording!");
                    embedBuilder.setColor(llColors.llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return false;
                }
            }
            else if(recordings.size()>= maxRecordingInstances){
                logger.info(fName + "Max recording instances reached!");
                embedBuilder.setDescription("Max recording instances reached!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            else if(!isConnected2VoiceChannel(guildAudioManager)){
                logger.info(fName + "Not connected to any voice channel > try to connect");
                if(!joinAndRelocateSub(true)){
                    logger.info(fName + "joinAndRelocateSub failed");
                    return false;
                }
            }
            GuildVoiceState voicestate=gMember.getVoiceState();
            VoiceChannel channel=null;
            if(voicestate!=null)channel = voicestate.getChannel();
            if(voicestate==null||channel==null){
                embedBuilder.setDescription("Recording failed to start!");
                messagerecord_start_dm=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                return false;
            }
            cAudioSaver saver = new cAudioSaver();
            AudioManager manager = gGuild.getAudioManager();
            manager.setSendingHandler(new AudioSendHandler() {
                @Override
                public boolean canProvide() {
                    return false;
                }

                @Override
                public ByteBuffer provide20MsAudio() {
                    return ByteBuffer.wrap(new byte[0]);
                }
            });
            manager.setReceivingHandler(saver);
            gGuild.getAudioManager().openAudioConnection(channel);
            recordings.put(gGuild.getIdLong(),saver);
            //FlexiUtils.waiter.waitForEvent(MessageReceivedEvent.class, evt -> evt.getAuthor().getId().equalsIgnoreCase(event.getAuthor().getId()) && evt.getMessage().getContentRaw().equalsIgnoreCase(FlexiUtils.PREFIX + "rend"), evt -> closeSaveAndSend(evt, saver), 20, TimeUnit.SECONDS, () -> closeSaveAndSend(event, saver));
            embedBuilder.setDescription("Recording started.\nYou can stop it by clicking on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo)+".\nOr waiting 30 seconds to auto-stop.");
            messagerecord_start_dm=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
            record_start_waiter();
            return true;
        }
        Calendar gCalendarToday;
        private void getTodayDate(){
            String fName="[getTodayDate]";
            logger.info(fName);
            gCalendarToday = Calendar.getInstance();
            logger.info(fName + ".today:"+gCalendarToday.get(gCalendarToday.YEAR)+"|"+gCalendarToday.get(gCalendarToday.MONTH)+"|"+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"@"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY)+gCalendarToday.get(gCalendarToday.MINUTE));
        }
        public boolean record_stop() {
            String fName="[record_stop]";
            logger.info(fName + "");
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(!getAudioManager()){
                embedBuilder.setDescription("Failed to get audio manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!getMusicManager()){
                embedBuilder.setDescription("Failed to get music manager!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!isConnected2VoiceChannel(guildAudioManager)){
                embedBuilder.setDescription("Not connected to any voice channel!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            if(!recordings.containsKey(gGuild.getIdLong())){
                embedBuilder.setDescription("Not recording!");
                embedBuilder.setColor(llColors.llColorRed_Barn);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                return false;
            }
            embedBuilder.setDescription("Processing...");
            Message toDel = lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
            gGuild.getAudioManager().closeAudioConnection();
            logger.info("Saving audio for " + gGuild.getId());
            cAudioSaver saver = recordings.get(gGuild.getIdLong());
            recordings.remove(gGuild.getIdLong());
            getTodayDate();
            File file;
            if(toDel==null){
                file = saver.save( "recording_"+(gCalendarToday.get(gCalendarToday.YEAR)+1)+"."+gCalendarToday.get(gCalendarToday.MONTH)+"."+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"H"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY));
            }else{
                file = saver.save( "recording_"+(gCalendarToday.get(gCalendarToday.YEAR)+1)+"."+gCalendarToday.get(gCalendarToday.MONTH)+"."+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"H"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY)+":"+toDel.getTimeCreated().getMinute());
            }
            logger.info("Audio saved, uploading using path " + file.getAbsolutePath());
            lsMessageHelper.lsMessageDelete(toDel);
            embedBuilder.setDescription("Recording done & uploaded.");
            gTextChannel.sendMessageEmbeds(embedBuilder.build()).addFile(file).queue();
            //gTextChannel.sendMessage(new EmbedBuilder().setColor(new Color(0x7289da)).setTitle("\uD83D\uDCBE Saved audio!").build()).addFile(file).queue();
            return true;
        }
        Message messagerecord_start_dm;
        private void record_start_waiter(){
            String fName="[record_start_waiter]";
            logger.info(fName);
            lsMessageHelper.lsMessageAddReactions(messagerecord_start_dm,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo));
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==messagerecord_start_dm.getIdLong())&&!e.getUser().isBot()&&e.getReactionEmote().getName().equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNo)),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            lsMessageHelper.lsMessageDelete(gTextChannel,e.getMessageIdLong());
                            record_stop();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(messagerecord_start_dm);
                            record_stop();
                        }
                    },30, TimeUnit.SECONDS, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        lsMessageHelper.lsMessageDelete(messagerecord_start_dm);
                        record_stop();
                    });
        }
        public boolean registerPlayerManager(boolean isCommand) {
            String fName="[registerPlayerManager]";
            logger.info(fName + "isCommand="+isCommand);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            if(gGlobal.playerManager==null){
                gGlobal.playerManager = new DefaultAudioPlayerManager();
                AudioSourceManagers.registerRemoteSources(gGlobal.playerManager);
                AudioSourceManagers.registerLocalSource(gGlobal.playerManager);
                logger.info(fName + "registered");
                embedBuilder.setDescription("Registering");
            }else{
                logger.info(fName + "already registered");
                embedBuilder.setDescription("Registered");
            }
            if(isCommand)lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        public boolean registerForcePlayerManager(boolean isCommand) {
            String fName="[registerForcePlayerManager]";
            logger.info(fName + "isCommand="+isCommand);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(0x7289da);
            gGlobal.playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(gGlobal.playerManager);
            AudioSourceManagers.registerLocalSource(gGlobal.playerManager);
            logger.info(fName + "registered");
            embedBuilder.setDescription("Registering");
            if(isCommand)lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            return true;
        }
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            logger.info(fName + "enable=" + enable);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            gBasicFeatureControl.setEnable(enable);
            if(enable){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result;
            if(type==1){
                logger.info(fName+"allowed");
                if(action==1){
                    logger.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                if(action==1){
                    logger.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result;
            if(type==1){
                logger.info(fName+"allowed");
                if(action==1){
                    logger.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                if(action==1){
                    logger.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                }
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(gTitle+" Options");
            embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
            embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
            embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gBasicFeatureControl.getEnable()){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            lsMessageHelper.lsMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setEnable(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setEnable(false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                getChannels(1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                getChannels(-1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                getRoles(1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                getRoles(-1,true);
                            }
                            else{
                                menuGuild();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }

                    },5, TimeUnit.MINUTES, () -> {
                        logger.info(fName+"timeout");
                        lsMessageHelper.lsMessageDelete(message);
                    });
        }
    }

}
