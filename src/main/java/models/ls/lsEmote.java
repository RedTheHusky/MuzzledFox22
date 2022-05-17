package models.ls;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface lsEmote {
    static List<String> lsGetEmotesAsMention(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotesAsMention]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            List<String>mentions=new ArrayList<String>();
            for(int i=0;i<emotes.size();i++){
                Emote emote = emotes.get(i);
                mentions.add(emote.getAsMention());
            }
            return mentions;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteAsMention(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteAsMention]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.getAsMention();
            }
            return "";
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteAsMention(Guild guild, long id) {
        String fName = "[lsGetEmoteAsMention]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.getAsMention();
            }
            return "";
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static List<Emote> lsGetEmotes(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotes]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            return emotes;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote lsGetEmote(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmote]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote;
            }
            return null;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote lsGetEmote(Guild guild, long id) {
        String fName = "[lsGetEmote]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote;
            }
            return null;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote lsGetEmoteById(Guild guild, String id) {
        String fName = "[lsGetEmoteById]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote;
            }
            return null;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static List<String> lsGetEmotesImageUrl(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotesImageUrl]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            List<String>mentions=new ArrayList<String>();
            for(int i=0;i<emotes.size();i++){
                Emote emote = emotes.get(i);
                mentions.add(emote.getImageUrl());
            }
            return mentions;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteImageUrl(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteImageUrl]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.getImageUrl();
            }
            return "";
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteImageUrl(Guild guild, long id) {
        String fName = "[lsGetEmoteImageUrl]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.getImageUrl();
            }
            return "";
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    
    static boolean lsGetEmoteIsAvailable(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteIsAvailable]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.isAvailable();
            }
            return false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsGetEmoteIsAvailable(Guild guild, long id) {
        String fName = "[lsGetEmoteIsAvailable]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.isAvailable();
            }
            return false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static boolean lsGetEmoteIsAnimated(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteIsAnimated]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.isAnimated();
            }
            return false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsGetEmoteIsAnimated(Guild guild, long id) {
        String fName = "[lsGetEmoteIsAnimated]";
        Logger logger = Logger.getLogger(lsEmote.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.isAnimated();
            }
            return false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static Emote lsGetEmoteById(JDA jda, String id){
        String fName="lsGetEmoteById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            Emote emote=jda.getEmoteById(id);
            return emote;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Emote lsGetEmoteById(JDA jda, long id){
        String fName="lsGetEmoteById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            Emote emote=jda.getEmoteById(id);
            return emote;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Emote lsGetEmoteById(List<JDA> jdas, String id){
        String fName="[lsGetEmoteById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            Emote emote=null;
            for(JDA jda:jdas){
                emote=lsGetEmoteById(jda, id);
                if(emote!=null){
                    return emote;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Emote lsGetEmoteById(List<JDA> jdas, long id){
        String fName="[lsGetEmoteById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            Emote emote=null;
            for(JDA jda:jdas){
                emote=lsGetEmoteById(jda, id);
                if(emote!=null){
                    return emote;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Emote lsGetEmoteById(lcGlobalHelper global, String id){
        String fName="lsGetEmoteById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            Emote emote=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                emote=lsGetEmoteById(jda, id);
                if(emote!=null){
                    return emote;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static Emote lsGetEmoteById(lcGlobalHelper global, long id){
        String fName="lsGetEmoteById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            Emote emote=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                emote=lsGetEmoteById(jda, id);
                if(emote!=null){
                    return emote;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    long gMuzzle_eUpdate=790209364619034644L;

}

