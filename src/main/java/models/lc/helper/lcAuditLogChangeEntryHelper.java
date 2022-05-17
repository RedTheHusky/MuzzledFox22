package models.lc.helper;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.audit.AuditLogChange;
import net.dv8tion.jda.api.audit.AuditLogKey;
import net.dv8tion.jda.api.entities.*;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.*;

public class lcAuditLogChangeEntryHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcAuditLogChangeEntry]";

    String gKey="", gExpected="";
    AuditLogChange gEntry=null;
    public lcAuditLogChangeEntryHelper(){
        String fName="[constructor1]";
    }
    public lcAuditLogChangeEntryHelper(AuditLogChange entry){
        String fName="[constructor1]";selectEntry(entry);
    }
    public lcAuditLogChangeEntryHelper(Map.Entry<String, AuditLogChange> entry){
        String fName="[constructor1]";selectEntry(entry);
    }
    String keyKey="key",keyExpected="expected", keyException="exception", keyValue="value",keyName="name";
    //https://javadoc.io/static/net.dv8tion/JDA/4.1.1_142/net/dv8tion/jda/api/audit/AuditLogKey.html#CHANNEL_BITRATE
    String expectedUnknown="unknown",expectedList_string="list<string>",expectedString="string",expectedInteger="integer",expectedBoolean="boolean",expectedLong="long",expectedList_map_string_object="list<map<string,object>>",expectedStringOrInteger="string/integer";

    public void selectEntry(AuditLogChange entry){
        String fName="[selectEntry]";
        logger.info(cName + fName);
        try {
            gKey=entry.getKey();
            gEntry=entry;
            gExpected="";getExpected();
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void selectEntry(Map.Entry<String, AuditLogChange> entry){
        String fName="[selectEntry]";
        logger.info(cName + fName);
        try {
            gKey=entry.getKey();
            gEntry=entry.getValue();
            gExpected="";getExpected();
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
        }
    }
    public String getExpected(){
        String fName="[getExpected]";
        logger.info(cName + fName);
        try {
            if (AuditLogKey.ID.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.APPLICATION_ID.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_NAME.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_OWNER.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_REGION.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_AFK_CHANNEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_SYSTEM_CHANNEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_ICON.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_SPLASH.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_VANITY_URL_CODE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_WIDGET_CHANNEL_ID.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_NAME.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_PARENT.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_TOPIC.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.MEMBER_NICK.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.OVERRIDE_TYPE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.ROLE_NAME.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.EMOTE_NAME.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.WEBHOOK_NAME.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.WEBHOOK_ICON.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.WEBHOOK_CHANNEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_CODE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_INVITER.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_CHANNEL.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: String
                gExpected=expectedString;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if (AuditLogKey.GUILD_AFK_TIMEOUT.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_EXPLICIT_CONTENT_FILTER.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_VERIFICATION_LEVEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_NOTIFICATION_LEVEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_MFA_LEVEL.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.GUILD_PRUNE_DELETE_DAYS.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_SLOWMODE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_BITRATE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_USER_LIMIT.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.ROLE_COLOR.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_TYPE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_MAX_AGE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_USES.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_MAX_USES.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: Integer
                gExpected=expectedInteger;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if (AuditLogKey.GUILD_WIDGET_ENABLED.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.CHANNEL_NSFW.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.MEMBER_MUTE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.MEMBER_DEAF.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.ROLE_HOISTED.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.ROLE_MENTIONABLE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.INVITE_TEMPORARY.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: Boolean
                gExpected=expectedBoolean;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if (AuditLogKey.OVERRIDE_DENY.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.OVERRIDE_ALLOW.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.ROLE_PERMISSIONS.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: Long
                gExpected=expectedLong;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if (AuditLogKey.MEMBER_ROLES_ADD.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.MEMBER_ROLES_REMOVE.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.EMOTE_ROLES_ADD.getKey().equalsIgnoreCase(gKey)
                    ||AuditLogKey.EMOTE_ROLES_REMOVE.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: List<String>
                gExpected=expectedList_string;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if (AuditLogKey.CHANNEL_OVERRIDES.getKey().equalsIgnoreCase(gKey)
            ) {
                //Expected type: List<Map<String, Object>>
                //CHANNEL_OVERRIDES:The overrides for this channel.
                gExpected=expectedList_map_string_object;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            else if(AuditLogKey.TYPE.getKey().equalsIgnoreCase(gKey)){
                //Expected type: String or int
                gExpected=expectedStringOrInteger;
                logger.info(cName + fName + ".expected=" + gExpected);
            }else if(gKey.equalsIgnoreCase("inviter_id")) {
                gExpected = expectedString;
                logger.info(cName + fName + ".expected=" + gExpected);
            }else{
                gExpected=expectedUnknown;
                logger.info(cName + fName + ".expected=" + gExpected);
            }
            return gExpected;
        } catch (Exception e) {
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            gExpected="";return null;
        }
    }
    public JSONObject getJson(){
        String fName="[getJson]";
        logger.info(cName + fName);
        String key="";
        AuditLogChange value=null;
        try {
            if(gEntry==null){
                return null;
            }

        } catch (Exception e) {
            logger.error(cName + fName + ".exception for ["+key+"]:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
        return null;
    }
    public String getKey(){
        String fName="[getKey]";
        logger.info(cName + fName);
        return gKey;
    }


    public String getNewValue2String(){
        String fName="[getNewValue2String]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".gKey="+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            logger.info(cName + fName + ".gExpected="+gExpected);
            Object value=gEntry.getOldValue();
            if(value==null){
                logger.info(cName + fName + ".getOldValue is null");
                return null;
            }
            try {
                if(value instanceof String){
                    return String.valueOf(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Integer){
                    return Integer.toString(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Long){
                    return Long.toString(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Float){
                    return Float.toString(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Double){
                    return Double.toString(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Boolean){
                    return Boolean.toString(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedList_string)) {
                    List<String>items=gEntry.getOldValue();
                    if (items == null) {
                        logger.warn(cName + fName + ".items is null");
                        return null;
                    }
                    return Arrays.toString(items.toArray());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedStringOrInteger)) {
                    return String.valueOf(gEntry.getOldValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedList_map_string_object)) {
                    List<Map<String,Object>>items=gEntry.getOldValue();
                    if (items == null) {
                        logger.warn(cName + fName + ".items is null");
                        return null;
                    }
                    logger.info(cName + fName + ".items size"+items.size());
                    String tmp="";
                    List<String>listKeys=new ArrayList<>();
                    for(Map<String,Object> item : items){
                        for(Map.Entry<String, Object> entry : item.entrySet()) {
                            String entryKey = entry.getKey();
                            Object entryValue = entry.getValue();
                            listKeys.add(entryKey);
                        }
                    }
                    if (listKeys.isEmpty()) {
                        logger.warn(cName + fName + ".listKeys is empty");
                        return null;
                    }
                    return Arrays.toString(listKeys.toArray());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.warn(cName + fName + ".outside condition");
            return  null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception["+gKey+"] =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Role> getNewRoles(Guild guild){
        String fName="[getNewRoles]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            List<String>items=gEntry.getNewValue();
            if (items == null) {
                logger.warn(cName + fName + ".items is null");
                return null;
            }
            logger.info(cName + fName + ".items size"+items.size());
            List<Role> roles = new ArrayList<>();
            for(String item : items){
                logger.info(cName + fName + ".item="+item);
                Role role=guild.getRoleById(item);
                if(role!=null){
                    roles.add(role);
                    logger.info(cName + fName + ".item["+item+"] found role");
                }else{
                    logger.warn(cName + fName + ".item["+item+"] no such role id or invalid guild");
                }
            }
            return roles;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
           return null;
        }
    }
    public Member getNewMember(Guild guild){
        String fName="[getNewMember]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getNewValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            Member member=guild.getMemberById(item);
            if(member!=null){
                logger.info(cName + fName + ".item["+item+"] found member");return  member;
            }
            logger.warn(cName + fName + ".item["+item+"] no such member or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public VoiceChannel getNewVoiceChannel(Guild guild){
        String fName="[getNewVoiceChannel]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getNewValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            VoiceChannel channel=guild.getVoiceChannelById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found channel");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such channel or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getNewTextChannel(Guild guild){
        String fName="[getNewTextChannel]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getNewValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            TextChannel channel=guild.getTextChannelById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found channel");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such channel or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Category getNewCategory(Guild guild){
        String fName="[getNewCategory]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getNewValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            Category channel=guild.getCategoryById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found category");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such category or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.ExplicitContentLevel getNewExplicitContentLevel(){
        String fName="[getNewExplicitContentLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getNewValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.ExplicitContentLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.VerificationLevel getNewVerificationLevel(){
        String fName="[getNewVerificationLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getNewValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.VerificationLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.NotificationLevel getNewNotificationLevel(){
        String fName="[getNewNotificationLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".key= "+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getNewValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.NotificationLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public String getOldValue2String(){
        String fName="[getOldValue2String]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            logger.info(cName + fName + ".gKey="+gKey);
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            logger.info(cName + fName + ".gExpected="+gExpected);
            Object value=gEntry.getNewValue();
            if(value==null){
                logger.info(cName + fName + ".getOldValue is null");
                return null;
            }
            try {
                if(value instanceof String){
                    return String.valueOf(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Integer){
                    return Integer.toString(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Long){
                    return Long.toString(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Float){
                    return Float.toString(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Double){
                    return Double.toString(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(value instanceof Boolean){
                    return Boolean.toString(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedList_string)) {
                    List<String>items=gEntry.getNewValue();
                    if (items == null) {
                        logger.warn(cName + fName + ".items is null");
                        return null;
                    }
                    return Arrays.toString(items.toArray());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedStringOrInteger)) {
                    return String.valueOf(gEntry.getNewValue());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if (gExpected.equalsIgnoreCase(expectedList_map_string_object)) {
                    List<Map<String,Object>>items=gEntry.getNewValue();
                    if (items == null) {
                        logger.warn(cName + fName + ".items is null");
                        return null;
                    }
                    logger.info(cName + fName + ".items size"+items.size());
                    String tmp="";
                    List<String>listKeys=new ArrayList<>();
                    for(Map<String,Object> item : items){
                        for(Map.Entry<String, Object> entry : item.entrySet()) {
                            String entryKey = entry.getKey();
                            Object entryValue = entry.getValue();
                            listKeys.add(entryKey);
                        }
                    }
                    if (listKeys.isEmpty()) {
                        logger.warn(cName + fName + ".listKeys is empty");
                        return null;
                    }
                    return Arrays.toString(listKeys.toArray());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.warn(cName + fName + ".outside condition");
            return  null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception["+gKey+"] =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Role> getOldRoles(Guild guild){
        String fName="[getOldRoles]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            List<String>items=gEntry.getNewValue();
            if (items == null) {
                logger.warn(cName + fName + ".items is null");
                return null;
            }
            logger.info(cName + fName + ".items size"+items.size());
            List<Role> roles = new ArrayList<>();
            for(String item : items){
                logger.info(cName + fName + ".item="+item);
                Role role=guild.getRoleById(item);
                if(role!=null){
                    roles.add(role);
                    logger.info(cName + fName + ".item["+item+"] found role");
                }else{
                    logger.warn(cName + fName + ".item["+item+"] no such role id or invalid guild");
                }
            }
            return roles;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getOldMember(Guild guild){
        String fName="[getOldMember]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getOldValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            Member member=guild.getMemberById(item);
            if(member!=null){
                logger.info(cName + fName + ".item["+item+"] found member");return  member;
            }
            logger.warn(cName + fName + ".item["+item+"] no such member or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public VoiceChannel getOldVoiceChannel(Guild guild){
        String fName="[getOldVoiceChannel]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getOldValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            VoiceChannel channel=guild.getVoiceChannelById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found channel");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such channel or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getOldTextChannel(Guild guild){
        String fName="[getOldTextChannel]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getOldValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            TextChannel channel=guild.getTextChannelById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found channel");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such channel or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Category getOldCategory(Guild guild){
        String fName="[getOldCategory]";
        logger.info(cName + fName);
        try {
            if (guild == null) {
                logger.warn(cName + fName + ".invalid guild");
                return null;
            }
            logger.info(cName + fName + ".guild="+guild.getId());
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedString)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            String item=gEntry.getOldValue();
            if(item==null||item.isBlank()||item.isEmpty()){
                logger.warn(cName + fName + ".item is null");
                return null;
            }
            Category channel=guild.getCategoryById(item);
            if(channel!=null){
                logger.info(cName + fName + ".item["+item+"] found category");return  channel;
            }
            logger.warn(cName + fName + ".item["+item+"] no such category or invalid guild");
            return null;
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.ExplicitContentLevel getOldExplicitContentLevel(){
        String fName="[getOldExplicitContentLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getOldValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.ExplicitContentLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.VerificationLevel getOldVerificationLevel(){
        String fName="[getOldVerificationLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getOldValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.VerificationLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild.NotificationLevel getOldNotificationLevel(){
        String fName="[getOldNotificationLevel]";
        logger.info(cName + fName);
        try {
            if (gEntry == null) {
                logger.warn(cName + fName + "entry is null");
                return null;
            }
            if(gExpected==null||gExpected.isBlank()){
                getExpected();
            }
            if(!gExpected.equalsIgnoreCase(expectedInteger)){
                logger.warn(cName + fName + "invalid expected formate");
                return null;
            }
            Object object=gEntry.getOldValue();
            if(object==null){
                logger.warn(cName + fName + ".object is null");
                return  null;
            }
            int item= (int)object;
            return Guild.NotificationLevel.fromKey(item);
        }catch (Exception e) {
            logger.error(cName + fName + ".exception =" + e.toString());
            logger.error(cName + fName + ".exception :" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
