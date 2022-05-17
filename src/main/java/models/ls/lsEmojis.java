package models.ls;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public interface lsEmojis {
    static List<String> lsGetEmotesAsMention(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotesAsMention]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            List<String>mentions=new ArrayList<String>();
            for(int i=0;i<emotes.size();i++){
                Emote emote = emotes.get(i);
                mentions.add(emote.getAsMention());
            }
            return mentions;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteAsMention(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteAsMention]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.getAsMention();
            }
            return "";
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteAsMention(Guild guild, long id) {
        String fName = "[lsGetEmoteAsMention]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.getAsMention();
            }
            return "";
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static List<Emote> lsGetEmotes(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotes]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            return emotes;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote lsGetEmote(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmote]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote;
            }
            return null;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Emote lsGetEmote(Guild guild, long id) {
        String fName = "[lsGetEmote]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote;
            }
            return null;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static List<String> lsGetEmotesImageUrl(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmotesImageUrl]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            List<String>mentions=new ArrayList<String>();
            for(int i=0;i<emotes.size();i++){
                Emote emote = emotes.get(i);
                mentions.add(emote.getImageUrl());
            }
            return mentions;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteImageUrl(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteImageUrl]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.getImageUrl();
            }
            return "";
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String lsGetEmoteImageUrl(Guild guild, long id) {
        String fName = "[lsGetEmoteImageUrl]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.getImageUrl();
            }
            return "";
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    
    static boolean lsGetEmoteIsAvailable(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteIsAvailable]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.isAvailable();
            }
            return false;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsGetEmoteIsAvailable(Guild guild, long id) {
        String fName = "[lsGetEmoteIsAvailable]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.isAvailable();
            }
            return false;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static boolean lsGetEmoteIsAnimated(Guild guild, String name, boolean ignoreCase) {
        String fName = "[lsGetEmoteIsAnimated]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            List<Emote> emotes = guild.getEmotesByName(name, ignoreCase);
            if (!emotes.isEmpty()) {
                Emote emote = emotes.get(0);
                return emote.isAnimated();
            }
            return false;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsGetEmoteIsAnimated(Guild guild, long id) {
        String fName = "[lsGetEmoteIsAnimated]";
        Logger logger = Logger.getLogger(lsEmojis.class);
        try {
            Emote emote = guild.getEmoteById(id);
            if (emote!=null) {
                return emote.isAnimated();
            }
            return false;
        } catch (Exception e) {
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}

