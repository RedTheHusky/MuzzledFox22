package models.lc.discordentities;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class lcGetGuildEmojiJsonExtended extends lcGetGuildEmojiJson {
    //https://discord.com/developers/docs/resources/emoji#emoji-object
    Logger logger = Logger.getLogger(getClass());

    protected lcGetGuildEmojiJsonExtended(){

    }
    public lcGetGuildEmojiJsonExtended(JSONObject jsonObject){
        set(jsonObject);
    }
    public lcGetGuildEmojiJsonExtended(Emote emoji){
        set(emoji);
    }
    public lcGetGuildEmojiJsonExtended(Guild guild, long emoji_id){
        set(guild,emoji_id);
    }
    public lcGetGuildEmojiJsonExtended(List<JDA>jdas, long guild_id, long emoji_id){
        set(jdas,guild_id,emoji_id);
    }
    Emote emoji=null;
    Guild guild=null;
    private lcGetGuildEmojiJsonExtended clear() {
        String fName="[clear]";
        jsonEmoji=new JSONObject();
        guildId=0;
        emoji=null;
        guild=null;
        logger.info(fName + ".cleared");
        return this;
    }
    private lcGetGuildEmojiJsonExtended set(JSONObject jsonObject) {
        String fName="[2setJson]";
        try {
            clear();
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji=new JSONObject(jsonObject.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetGuildEmojiJsonExtended set(Emote emoji) {
        String fName="[2setEmote]";
        try {
            clear();
            logger.info(fName + ".emoji="+emoji.getId());
            JSONObject jsonObject=getHttpBody(emoji.getGuild().getIdLong(),emoji.getIdLong());
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji=new JSONObject(jsonObject.toString());
            this.emoji=emoji;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetGuildEmojiJsonExtended set(Guild guild, long message_id) {
        String fName="[2setEmote4Guild]";
        try {
            clear();
            logger.info(fName + ". guild="+ guild.getId()+", message_id="+message_id);
            JSONObject jsonObject=getHttpBody( guild.getIdLong(),message_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji=new JSONObject(jsonObject.toString());
            this.guild=guild;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcGetGuildEmojiJsonExtended set(List<JDA> jdas, long guild_id, long emote_id) {
        String fName="[2setEmote4JDA]";
        try {
            clear();
            logger.info(fName + ".guild_id="+guild_id+", emote_id="+emote_id);
            JSONObject jsonObject=getHttpBody(guild_id,emote_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji=new JSONObject(jsonObject.toString());
            for(JDA jda:jdas){
                try {
                    Guild guild=jda.getGuildById(guild_id);
                    if(guild!=null){
                        this.guild=guild;
                        logger.info(fName + ".found guild="+guild.getId());
                        break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(guild!=null){
                emoji=guild.getEmoteById(emote_id);
                logger.info(fName + ".found emote in guild");
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Emote getEmote() {
        String fName="[getEmote]";
        try {
            if(this.emoji!=null){
                logger.info(fName + ".global emoji is not null>return");
                return  this.emoji;
            }
            Emote emote=null;
            if(this.guild!=null){
                logger.info(fName + ".guild is not null");
                emote=this.guild.getEmoteById(getId());
            }
            if(emote!=null){
                logger.info(fName + ".message is not null");
                this.emoji=emote;
            }
            return emote;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
