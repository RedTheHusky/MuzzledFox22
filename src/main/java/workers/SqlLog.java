package workers;

import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SqlLog implements llGlobalHelper ,iSqlLog {
    String cName="[sqlLog]";

    ///////////////
    Logger logger=Logger.getLogger(getClass());
    public SqlLog(lcGlobalHelper global){
        Runnable r = new runLocal(global);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        GuildMemberJoinEvent  guildmemberjoinevent;
        GuildMemberRemoveEvent guildmemberremoveevent;
        GuildBanEvent guildbanevent;
        GuildUnbanEvent guildunbanevent;
        GuildMemberRoleAddEvent guildmemberroleaddevent;
        GuildMemberRoleRemoveEvent guildmemberroleremoveevent;
        UserUpdateOnlineStatusEvent userupdateonlinestatusevent;
        lcGlobalHelper gGlobal;
        CategoryCreateEvent categorycreateevent;
        CategoryDeleteEvent  categorydeleteevent;
        CategoryUpdateNameEvent categoryupdatenameevent;
        CategoryUpdatePositionEvent categoryupdatepositionevent;
        GuildMemberUpdateNicknameEvent guildmemberupdatenicknameevent;
        GuildMemberUpdateBoostTimeEvent guildmemberupdateboosttimeevent;
        UserUpdateDiscriminatorEvent userupdatediscriminatorevent;
        UserUpdateNameEvent userupdatenameevent;
        UserUpdateAvatarEvent userupdateavatarevent;

        public runLocal(lcGlobalHelper global){
            gGlobal=global;
        }
        public runLocal(lcGlobalHelper global,GuildMemberJoinEvent event){
            gGlobal=global;guildmemberjoinevent=event;
        }
        public runLocal(lcGlobalHelper global,GuildMemberRemoveEvent event){
            gGlobal=global;guildmemberremoveevent = event;
        }
        public runLocal(lcGlobalHelper global,GuildBanEvent event){
            gGlobal=global;guildbanevent = event;
        }
        public runLocal(lcGlobalHelper global,GuildUnbanEvent event){
            gGlobal=global;guildunbanevent = event;
        }
        public runLocal(lcGlobalHelper global,GuildMemberRoleAddEvent event){
            gGlobal=global;guildmemberroleaddevent = event;
        }
        public runLocal(lcGlobalHelper global,GuildMemberRoleRemoveEvent event){
            gGlobal=global;guildmemberroleremoveevent = event;
        }
        public runLocal(lcGlobalHelper global,UserUpdateOnlineStatusEvent event){
            gGlobal=global;userupdateonlinestatusevent = event;
        }
        public runLocal(lcGlobalHelper global,CategoryCreateEvent event){
            gGlobal=global;categorycreateevent=event;
        }
        public runLocal(lcGlobalHelper global,CategoryDeleteEvent event){
            gGlobal=global;categorydeleteevent=event;
        }
        public runLocal(lcGlobalHelper global,CategoryUpdateNameEvent event){
            gGlobal=global;categoryupdatenameevent=event;
        }
        public runLocal(lcGlobalHelper global,CategoryUpdatePositionEvent event){
            gGlobal=global;categoryupdatepositionevent=event;
        }
        public runLocal(lcGlobalHelper global,GuildMemberUpdateNicknameEvent event){
            gGlobal=global;guildmemberupdatenicknameevent=event;
        }
        public runLocal(lcGlobalHelper global,GuildMemberUpdateBoostTimeEvent event){
            gGlobal=global;guildmemberupdateboosttimeevent=event;
        }
        public runLocal(lcGlobalHelper global,UserUpdateDiscriminatorEvent event){
            gGlobal=global;userupdatediscriminatorevent=event;
        }
        public runLocal(lcGlobalHelper global,UserUpdateNameEvent event){
            gGlobal=global;userupdatenameevent=event;
        }
        public runLocal(lcGlobalHelper global,UserUpdateAvatarEvent event){
            gGlobal=global;userupdateavatarevent=event;
        }
        @Override
        public void run() {
            logger.info(cName+".run start");
            go(guildmemberjoinevent);
            go(guildmemberremoveevent);//go(guildmemberleaveevent);
            go(guildmemberroleaddevent);
            go(guildmemberroleremoveevent);
            go(guildbanevent);
            go(guildunbanevent);
            go(userupdateonlinestatusevent);
            go(categorycreateevent);
            go(categorydeleteevent);
            go(categoryupdatenameevent);
            go(categoryupdatepositionevent);
            go(guildmemberupdateboosttimeevent);
            go(guildmemberupdatenicknameevent);
            go(userupdateavatarevent);
            go(userupdatenameevent);
            go(userupdatediscriminatorevent);
            logger.info(cName+".run ended");
        }
        String gName="sqllog";
        lcJSONGuildProfile gProfile;
        private boolean initGuildProfile(Guild guild){
            String fName="[initGuildProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(guild,gName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gName,guild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(guild,gProfile);
                }
            }
            gProfile= initJson(gProfile,guild);
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(guild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(cName + fName + ".success save to db");
                }else{
                    logger.error(cName + fName + ".error save to db");
                }
            }
            return  gProfile.isExistent();
        }
        private void go(GuildMemberJoinEvent event){
            if(event==null) return;
            String dbName_memberJoin_log="log_member_join";
            Guild guild=event.getGuild(); User user=event.getUser();
            String fName="userJoin"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberJoin_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId);
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_userdName,userName);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.warn(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildMemberRemoveEvent event){
            if(event==null) return;
            String dbName_memberLeave_log="log_member_leave";
            Guild guild=event.getGuild(); User user=event.getUser();
            String fName="userRemoved"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberLeave_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId);
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_userdName,userName);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");
                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }


        }
        private void go(GuildBanEvent event){
            if(event==null) return;
            String dbName_memberBan_log="log_member_ban";
            Guild guild=event.getGuild(); User user=event.getUser();
            String fName="userBan"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberBan_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId);
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_userdName,userName);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildUnbanEvent event){
            if(event==null) return;
            String dbName_memberUnBan_log="log_member_unban";
            Guild guild=event.getGuild(); User user=event.getUser();
            String fName="userUnBan"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberUnBan_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId);
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_userdName,userName);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildMemberRoleAddEvent event){
            if(event==null) return;
            String dbName_memberRoleAdd_log="log_member_roleadd";
            Guild guild=event.getGuild(); User user=event.getUser();List<Role>roles=event.getRoles();
            String fName="userRoleAdd"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberRoleAdd_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                String str="";
                for(Role role : roles){
                    if(str.length()>0){
                        str+=", "+role.getId();
                    }else{
                        str=role.getId();
                    }
                }
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                data.put(column_rolesId,str);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    str="";
                    for(Role role : roles){
                        if(str.length()>0){
                            str+=", "+role.getName();
                        }else{
                            str=role.getName();
                        }
                    }
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId+"|roles="+str);
                    data.put(column_userdName,userName);data.put(column_rolesName,str);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildMemberRoleRemoveEvent event){
            if(event==null) return;
            String dbName_memberRoleRem_log="log_member_roleremove";
            Guild guild=event.getGuild(); User user=event.getUser();List<Role>roles=event.getRoles();
            String fName="userRoleRemove"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberRoleRem_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();
                String str="";
                for(Role role : roles){
                    if(str.length()>0){
                        str+=", "+role.getId();
                    }else{
                        str=role.getId();
                    }
                }
                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                data.put(column_rolesId,str);
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    str="";
                    for(Role role : roles){
                        if(str.length()>0){
                            str+=", "+role.getName();
                        }else{
                            str=role.getName();
                        }
                    }
                    logger.warn(cName+fName+"user="+user.getName()+"|guild="+guildId+"|roles="+str);
                    data.put(column_userdName,userName);data.put(column_rolesName,str);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(UserUpdateOnlineStatusEvent event){
            if(event==null) return;
            Guild guild=event.getGuild(); User user=event.getUser(); OnlineStatus status=event.getNewOnlineStatus();
            String dbName_memberStatusChange_log="log_member_status";
            String fName="userStatusChange"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_memberStatusChange_log;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userName=user.getName();
                String userId=user.getId();
                String guildId=guild.getId();

                data.put(column_guildId,guildId);
                data.put(column_userdId,userId);
                data.put(column_status,status.name());
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(UserUpdateAvatarEvent event){
            if(event==null) return;
            String dbName_userUpdateAvatar="log_member_updateavatar";
            User user=event.getUser(); String oldValue=event.getOldValue();String newValue=event.getNewValue();
            String fName="userUpdateAvatar"; boolean response=true;
            Guild guild=gGlobal.getGuild(llGuildKeyBotHelper);
            if(guild==null){guild=gGlobal.GuildKeyBotHelper;}
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for main.java.bot helper guild");
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_userUpdateAvatar;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userid=user.getId();
                data.put(column_userdId,userid);
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_oldValue,oldValue);
                    data.put(column_newValue,newValue);
                    logger.warn(cName+fName+"user="+user.getName()+"|new="+newValue+"|old="+oldValue);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userid+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(UserUpdateNameEvent event){
            if(event==null) return;
            String dbName_userUpdateName="log_member_updatename";
            User user=event.getUser(); String oldValue=event.getOldValue(); String newValue=event.getNewValue();
            String fName="userUpdateName"; boolean response=true;
            Guild guild=gGlobal.getGuild(llGuildKeyBotHelper);
            if(guild==null){guild=gGlobal.GuildKeyBotHelper;}
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for main.java.bot helper guild");
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_userUpdateName;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userid=user.getId();
                data.put(column_userdId,userid);
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_oldValue,oldValue);
                    data.put(column_newValue,newValue);
                    logger.warn(cName+fName+"user="+user.getName()+"|old="+oldValue);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userid+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(UserUpdateDiscriminatorEvent event){
            if(event==null) return;
            String dbName_userUpdateDiscriminator="log_member_updatediscriminator";
            User user=event.getUser(); String oldValue=event.getOldValue(); String newValue=event.getNewValue();
            String fName="userUpdateDiscriminator"; boolean response=true;
            Guild guild=gGlobal.getGuild(llGuildKeyBotHelper);
            if(guild==null){guild=gGlobal.GuildKeyBotHelper;}
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for main.java.bot helper guild");
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_userUpdateDiscriminator;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String userid=user.getId();
                data.put(column_userdId,userid);
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_oldValue,oldValue);
                    data.put(column_newValue,newValue);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userid+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildMemberUpdateBoostTimeEvent event){
            if(event==null) return;
            String fName="userUpdateBoostTime";
            String dbName_userUpdateBoostTime="log_member_updateboosttime";
            Guild guild=event.getGuild(); User user=event.getUser();
            String oldValue="0"; String newValue="0";
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(event.getOldValue()!=null){
                oldValue=event.getOldValue().toString();
            }
            if(event.getNewValue()!=null){
                newValue=event.getNewValue().toString();
            }
            boolean response=true;
            if(gGlobal.sql.checkConnection()){
                String db=dbName_userUpdateBoostTime;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildid=guild.getId();
                String userid=user.getId();
                data.put(column_guildId,guildid);
                data.put(column_userdId,userid);
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_oldValue,oldValue);
                    data.put(column_newValue,newValue);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userid+"' and "+column_guildId+"='"+guildid+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(GuildMemberUpdateNicknameEvent event){
            if(event==null) return;
            String dbName_userUpdateNickname="log_member_updatenickname";
            Guild guild=event.getGuild(); User user=event.getUser(); String oldValue=event.getOldValue(); String newValue=event.getNewValue();
            String fName="userUpdateNickname"; boolean response=true;
            initGuildProfile(guild);
            if(!isEnabled(gProfile,event)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_userUpdateNickname;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildid=guild.getId();
                String userid=user.getId();
                data.put(column_guildId,guildid);
                data.put(column_userdId,userid);
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    data.put(column_oldValue,oldValue);
                    data.put(column_newValue,newValue);
                    logger.warn(cName+fName+"user="+user.getName()+"|old="+oldValue);
                    if(!gGlobal.sql.update(db,data,column_userdId+" ='"+userid+"' and "+column_guildId+"='"+guildid+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(CategoryUpdatePositionEvent e){
            if(e==null) return;
            String fName="categoryPositionUpdate"; boolean response=true;
            Guild guild= e.getGuild();
            initGuildProfile(guild);
            if(!isEnabled(gProfile,e)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_categoryUodate;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildId=e.getGuild().getId();
                String categoryId=e.getCategory().getId();

                data.put(column_guildId,guildId);
                data.put(column_categoryId,categoryId);
                data.put(column_categoryType,"position");
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    String categoryName=e.getCategory().getName();
                    data.put(column_categoryValue,e.getOldValue()+">"+e.getNewPosition());
                    data.put(column_categoryName,categoryName);
                    if(!gGlobal.sql.update(db,data,column_categoryId+" ='"+categoryId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(CategoryCreateEvent e){
            if(e==null) return;
            String fName="categoryCreate"; boolean response=true;
            Guild guild= e.getGuild();
            initGuildProfile(guild);
            if(!isEnabled(gProfile,e)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_categoryUodate;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildId=e.getGuild().getId();
                String categoryId=e.getCategory().getId();

                data.put(column_guildId,guildId);
                data.put(column_categoryId,categoryId);
                data.put(column_categoryType,"create");
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    String categoryName=e.getCategory().getName();
                    data.put(column_categoryName,categoryName);
                    if(!gGlobal.sql.update(db,data,column_categoryId+" ='"+categoryId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(CategoryDeleteEvent e){
            if(e==null) return;
            String fName="categoryDelete"; boolean response=true;
            Guild guild= e.getGuild();
            initGuildProfile(guild);
            if(!isEnabled(gProfile,e)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_categoryUodate;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildId=e.getGuild().getId();
                String categoryId=e.getCategory().getId();

                data.put(column_guildId,guildId);
                data.put(column_categoryId,categoryId);
                data.put(column_categoryType,"delete");
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    String categoryName=e.getCategory().getName();
                    data.put(column_categoryName,categoryName);
                    if(!gGlobal.sql.update(db,data,column_categoryId+" ='"+categoryId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
        private void go(CategoryUpdateNameEvent e){
            if(e==null) return;
            String fName="categoryNameUpdate"; boolean response=true;
            Guild guild= e.getGuild();
            initGuildProfile(guild);
            if(!isEnabled(gProfile,e)){
                logger.warn(cName+fName+"not enabled for guild="+guild.getId());
                return;
            }
            if(gGlobal.sql.checkConnection()){
                String db=dbName_categoryUodate;
                Map<String,Object> data=new LinkedHashMap<String,Object>();
                String guildId=e.getGuild().getId();
                String categoryId=e.getCategory().getId();

                data.put(column_guildId,guildId);
                data.put(column_categoryId,categoryId);
                data.put(column_categoryType,"name");
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    logger.info(cName+fName+".data:"+key+"="+value);
                }
                if(gGlobal.sql.insert(db,data)){
                    logger.info(cName+fName+".insert ok");
                    data=new LinkedHashMap<String,Object>();
                    String categoryName=e.getCategory().getName();
                    data.put(column_categoryValue,e.getOldName()+">"+e.getNewName());
                    data.put(column_categoryName,categoryName);
                    if(!gGlobal.sql.update(db,data,column_categoryId+" ='"+categoryId+"' and "+column_guildId+"='"+guildId+"'")){
                        logger.info(cName+fName+".update failed");

                    }else{
                        logger.info(cName+fName+".update ok");
                    }
                }else{
                    logger.error(cName+fName+".insert failed");
                    response=false;
                }
            }else{
                logger.error(cName+fName+".connection failed");
                response=false;
            }

        }
    }



    public SqlLog(lcGlobalHelper global,GuildMemberJoinEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global, GuildMemberRemoveEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildBanEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildUnbanEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildMemberRoleAddEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildMemberRoleRemoveEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,UserUpdateOnlineStatusEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,CategoryCreateEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,CategoryDeleteEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,CategoryUpdateNameEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,CategoryUpdatePositionEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildMemberUpdateNicknameEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,GuildMemberUpdateBoostTimeEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,UserUpdateDiscriminatorEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,UserUpdateNameEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    public SqlLog(lcGlobalHelper global,UserUpdateAvatarEvent event){
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }

}
