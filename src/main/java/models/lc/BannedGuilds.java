package models.lc;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.ll.llCommonKeys;
import models.ls.lsGuildHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannedGuilds {
    protected Logger logger = Logger.getLogger(getClass());
    protected lcText2Json text2Json;
    public BannedGuilds(){
        build();
    }
    public BannedGuilds(boolean auto){
        if(auto){
            build();
        }
    }
    String path="resources/json/others/bannedguilds.json";
    public boolean build(){
        String fName="[build]";
        try {
            if(!load())return false;
            if(!buildList())return false;
            logger.info(fName+".built");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            text2Json.jsonArray=new JSONArray();
            guilds=new ArrayList<>();
            logger.info(fName+".cleared");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    protected boolean load(){
        String fName="[load]";
        try {
            text2Json=new lcText2Json();
            text2Json.isFile2JsonArray(path);
            logger.info(fName+".loaded");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }


    public BannedGuilds setJDA(List<JDA> jdaList) {
        String fName="[setJDA]";
        try {
            jdas=jdaList;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }



    public interface CommonKeys {
        //https://discord.com/developers/docs/interactions/message-components#buttons-button-object
        String EmojiChar="emojiChar", Emoji="emoji",Description="description",Aliases="aliases",Tags="tags";

    }
    public class GuildEntity {
        protected Logger logger = Logger.getLogger(BannedGuilds.class);
        protected String nName="GuildJson@";
        protected  String id="";
        protected GuildEntity(){
            String fName="[build]";
            logger.info(fName+".blank");
        }
        public GuildEntity(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName+".blank");
            set(jsonObject);
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                if(jsonObject.isEmpty())return false;
                String key="";
                key= llCommonKeys.keyId;if(jsonObject.has(key))id=jsonObject.optString(key,"");
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public String getId(){
            String fName="[getId";
            try {
                String value=id;
                logger.info(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public long getIdAsLong(){
            String fName="[getIdAsLong]";
            try {
                long value=Long.parseLong(getId());
                logger.info(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }

    }
    List<GuildEntity> guilds=new ArrayList<>();
    List<JDA> jdas=new ArrayList<>();
    public boolean isEmpty(){
        String fName="[isEmpty]";
        try {
            boolean result= guilds.isEmpty();;
            logger.info(fName + ".result" + result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public GuildEntity getGuildEntity(int index){
        String fName="[getGuildEntity]";
        try {
            logger.info(fName+"index="+index);
            return guilds.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(int index){
        String fName="[getGuild]";
        try {
            logger.info(fName+"inxed="+index);
            Guild guild=lsGuildHelper.getGuild(jdas,getGuildEntity(index).getIdAsLong());
            return guild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<GuildEntity> leaveGuilds(){
        String fName="[leaveGuilds]";
        try {
            logger.info(fName + ".banned size=" + guilds.size());
            List<GuildEntity>guildLeft=new ArrayList<>();
            for(GuildEntity guild:guilds){
                try {
                    logger.info(fName + ".guilds[]:" + guild.getId());
                    logger.info(fName + ".jdas.size=" + jdas.size());
                    Guild guild1=lsGuildHelper.getGuild(jdas,guild.getIdAsLong());
                    logger.info(fName + ".guild id=" + guild1.getId());
                    if(guild1!=null){
                        guild1.leave().complete();
                        logger.info(fName + ".left");
                        guildLeft.add(guild);
                    }

                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }


            }
            logger.info(fName + ".removed size=" + guildLeft.size());
            return guildLeft;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected boolean buildList(){
        String fName="[buildList]";
        try {
            if(text2Json.jsonArray.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName+".text2Json.jsonArray.length="+text2Json.jsonArray.length());
            for(int i=0;i<text2Json.jsonArray.length();i++){
                GuildEntity emoji=new GuildEntity(text2Json.jsonArray.optJSONObject(i));
                guilds.add(emoji);
            }
            logger.info(fName + ".done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;
        }
    }
    public int checkAndLeave(Guild guild) {
        String fName="[checkAndLeave]";
        try {
            logger.info(fName + ".guild=" + guild.getId());
            for(GuildEntity guild0:guilds){
                try {
                    logger.info(fName + ".guilds[]:" + guild0.getId());
                    if(guild0.getIdAsLong()==guild.getIdLong()){
                        logger.info(fName + ".equal");
                        guild.leave().complete();
                        logger.info(fName + ".left");
                        return 1;
                    }

                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return -1;
        }
    }

}
