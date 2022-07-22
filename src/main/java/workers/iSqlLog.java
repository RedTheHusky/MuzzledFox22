package workers;

import models.lc.json.profile.lcJSONGuildProfile;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;

public interface iSqlLog {
    String keyEnabled="enabled";
    String keyMemberJoin="memberjoin",keyMemberRemove="memberremove";
    String keyMemberRoleAdd="memberroleadd",keyMemberRoleRemove="memberroleremove";
    String keyBan="ban",keyUnban="unban";
    String keyUserUpdateOnlineStatus="userupdateonlinestatus";
    String keyCategoryCreate="categorycreate",keyCategoryDelete="categorydelete",keyCategoryUpdateName="categoryupdatename",keyCategoryUpdatePosition="categoryupdateposition";
    String keyMemberUpdateBoostTime="memberupdateboosttime",keyMemberUpdateNickName="memberupdatenickname",keyUserUpdateAvatar="userupdateavatar",keyUserUpdateName="userupdatename",keyUserUpdateDiscriminator="userupdatediscriminator";
    default lcJSONGuildProfile initJson(lcJSONGuildProfile gProfile, Guild guild) {
        String fName = "[iSafetyUserProfileEntry]";
        //gProfile.safetyPutFieldEntry(keyEnabled, true);
        gProfile.safetyPutFieldEntry(keyMemberJoin, true);gProfile.safetyPutFieldEntry(keyMemberRemove, true);
        gProfile.safetyPutFieldEntry(keyMemberRoleAdd, true);gProfile.safetyPutFieldEntry(keyMemberRoleRemove, true);
        gProfile.safetyPutFieldEntry(keyBan, true);gProfile.safetyPutFieldEntry(keyUnban, true);
        gProfile.safetyPutFieldEntry(keyCategoryCreate, true);gProfile.safetyPutFieldEntry(keyCategoryDelete, true);gProfile.safetyPutFieldEntry(keyCategoryUpdateName, true);gProfile.safetyPutFieldEntry(keyCategoryUpdatePosition, true);
        gProfile.safetyPutFieldEntry(keyUserUpdateOnlineStatus, true);
        gProfile.safetyPutFieldEntry(keyMemberUpdateBoostTime, true);
        gProfile.safetyPutFieldEntry(keyMemberUpdateNickName, true);
        if(guild.getId().equalsIgnoreCase(llGlobalHelper.llGuildKeyBotHelper)){
            gProfile.safetyPutFieldEntry(keyUserUpdateAvatar, true);
            gProfile.safetyPutFieldEntry(keyUserUpdateDiscriminator, true);
            gProfile.safetyPutFieldEntry(keyUserUpdateName, true);
        }
        return  gProfile;
    }
    default lcJSONGuildProfile initJson(lcJSONGuildProfile gProfile) {
        String fName = "[iSafetyUserProfileEntry]";
        //gProfile.safetyPutFieldEntry(keyEnabled, true);
        gProfile.safetyPutFieldEntry(keyMemberJoin, true);gProfile.safetyPutFieldEntry(keyMemberRemove, true);
        gProfile.safetyPutFieldEntry(keyMemberRoleAdd, true);gProfile.safetyPutFieldEntry(keyMemberRoleRemove, true);
        gProfile.safetyPutFieldEntry(keyBan, true);gProfile.safetyPutFieldEntry(keyUnban, true);
        gProfile.safetyPutFieldEntry(keyCategoryCreate, true);gProfile.safetyPutFieldEntry(keyCategoryDelete, true);gProfile.safetyPutFieldEntry(keyCategoryUpdateName, true);gProfile.safetyPutFieldEntry(keyCategoryUpdatePosition, true);
        gProfile.safetyPutFieldEntry(keyUserUpdateOnlineStatus, true);
        gProfile.safetyPutFieldEntry(keyMemberUpdateBoostTime, true);
        gProfile.safetyPutFieldEntry(keyMemberUpdateNickName, true);
        return  gProfile;
    }

    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberJoinEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberJoin;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberRemoveEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberRemove;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildBanEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyBan;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildUnbanEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyUnban;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberRoleAddEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberRoleAdd;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberRoleRemoveEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberRoleRemove;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, UserUpdateOnlineStatusEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyUserUpdateOnlineStatus;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, CategoryCreateEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyCategoryCreate;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, CategoryDeleteEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyCategoryDelete;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, CategoryUpdateNameEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyCategoryUpdateName;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, CategoryUpdatePositionEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyCategoryUpdatePosition;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberUpdateNicknameEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberUpdateNickName;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, GuildMemberUpdateBoostTimeEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyMemberUpdateBoostTime;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, UserUpdateDiscriminatorEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyUserUpdateDiscriminator;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, UserUpdateNameEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyUserUpdateName;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }
    default boolean isEnabled(lcJSONGuildProfile gProfile, UserUpdateAvatarEvent event){
        try{
            if(gProfile==null){return false;}
            if(!gProfile.isExistent()){return false;}
            String key=keyUserUpdateAvatar;
            if(!gProfile.jsonObject.has(key)){return false;}
            if(gProfile.jsonObject.isNull(key)){return false;}
            return gProfile.jsonObject.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }

    String column_rolesId="roles_id", column_rolesName="roles_name";
    String column_guildId="guild_id",column_guildName="guild_name";
    String column_userdId="user_id", column_userdName="user_name";
    String column_author="author";
    String column_status="status";
    String dbName_categoryUodate="log_categoryupdate";
    String column_categoryId="category_id",column_categoryType="type",column_categoryValue="value",column_categoryName="name";
    String column_oldValue="value_old",column_newValue="value_new";
}
