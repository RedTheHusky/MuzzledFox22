package restraints.in;

import kong.unirest.json.JSONObject;
import models.lc.lcSqlConnEntity;
import models.ll.llCommonKeys;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;
import restraints.models.entity.entityGagPost;
import restraints.models.enums.GAGLEVELS;

import java.util.*;

public interface iUtility {
    final String gagMessageTable="gagmessages";
    interface  KEYS{
       String  user_id= llCommonKeys.keyUser_id,guild_id=llCommonKeys.keyGuild_Id,channel_id=llCommonKeys.keyChannel_id;
       String org_message_id="org_message_id",new_message_id="new_message_id",content="content",gaglevel="gaglevel";
    }
    static boolean addGagPost2Registry(Member member, TextChannel textChannel, long messageIdOriginal, long messageIdGag, GAGLEVELS gaglevel,String content ) {
        String fName = "[addMessage2Registry]";
        Logger logger = Logger.getLogger(iUtility.class);
        try {
            logger.info("member=" + member.getId() + ", textchannel=" + textChannel.getId() + ", messageOriginal=" + messageIdOriginal + ", messageGag=" + messageIdGag + " gaglevel=" + gaglevel.getName() + ", content=" + content);
            lcSqlConnEntity sql = lsGlobalHelper.sGetGlobal().getSql();
            if (!sql.checkConnection()) {
                logger.error(fName + ".no open connection");
                return false;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEYS.user_id, member.getIdLong()).put(KEYS.guild_id, textChannel.getGuild().getIdLong()).put(KEYS.channel_id, textChannel.getIdLong());
            jsonObject.put(KEYS.org_message_id, messageIdOriginal).put(KEYS.new_message_id, messageIdGag);
            jsonObject.put(KEYS.content, content).put(KEYS.gaglevel, gaglevel.getName());
            Map<String, Object> data = new LinkedHashMap<>();
            data.put(KEYS.user_id, member.getIdLong());
            data.put(KEYS.guild_id, textChannel.getGuild().getIdLong());
            data.put(KEYS.channel_id, textChannel.getIdLong());
            data.put(llCommonKeys.keyMessage_id, messageIdGag);
            data.put("json", jsonObject.toString());
            if (!sql.insert(gagMessageTable, data)) {
                logger.error(fName + ".error while inserting");
                return false;
            }
            logger.info(fName + ".success insert");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static entityGagPost getGagPost4Registry(long author_id, long message_id, long guild_id) {
        String fName = "[addMessage2Registry]";
        Logger logger = Logger.getLogger(iUtility.class);
        try {
            logger.info("author_id=" + author_id + ", message_id,=" + message_id+ ", guild_id=" +guild_id);
            lcSqlConnEntity sql = lsGlobalHelper.sGetGlobal().getSql();
            if (!sql.checkConnection()) {
                logger.error(fName + ".no open connection");
                return null;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            colums.add("id");colums.add("json");
            rows=sql.selectFirst(gagMessageTable,"user_id = '"+author_id+"' and guild_id='"+guild_id+"' and message_id='"+message_id+"'",colums);

            if(rows==null){logger.info(fName+".select null"); return null; }
            boolean isGo=false;
            JSONObject jsonObject =null;
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("json")&&value!=null){
                    isGo=true;jsonObject =new JSONObject(value.toString());
                }
            }
            if(!isGo){logger.info(fName+".no go");return null; }
            logger.info(fName+".jsonUser="+ jsonObject.toString());
            logger.info(fName+".success");
            return new entityGagPost(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static entityGagPost getGagPost4Registry(long message_id, long guild_id) {
        String fName = "[addMessage2Registry]";
        Logger logger = Logger.getLogger(iUtility.class);
        try {
            logger.info("message_id,=" + message_id+ ", guild_id=" +guild_id);
            lcSqlConnEntity sql = lsGlobalHelper.sGetGlobal().getSql();
            if (!sql.checkConnection()) {
                logger.error(fName + ".no open connection");
                return null;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            colums.add("id");colums.add("json");
            rows=sql.selectFirst(gagMessageTable,"guild_id='"+guild_id+"' and message_id='"+message_id+"'",colums);

            if(rows==null){logger.info(fName+".select null"); return null; }
            boolean isGo=false;
            JSONObject jsonObject =null;
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("json")&&value!=null){
                    isGo=true;jsonObject =new JSONObject(value.toString());
                }
            }
            if(!isGo){logger.info(fName+".no go");return null; }
            logger.info(fName+".jsonUser="+ jsonObject.toString());
            logger.info(fName+".success");
            return new entityGagPost(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
