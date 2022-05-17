package models.lc.json.profile;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcProfileEntityName;
import models.lc.json.lcProfileEntityTable;
import models.lcGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcJSONUsersProfiles {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONUsersProfiles]";
    Guild gGuild;
    lcGlobalHelper gGlobal;
    public String gTable;String gName;
    public JSONArray ARRAY=new JSONArray();
    public lcJSONUsersProfiles(lcGlobalHelper global,String table){
        String fName="[constructor1]";
        gGlobal=global;
        gTable=table;
        logger.info(cName + fName + ".gTable=" +gTable);
    }
    public lcJSONUsersProfiles(lcGlobalHelper global,String table, Guild guild){
        String fName="[constructor1]";
        gGlobal=global;
        gTable=table;
        logger.info(cName + fName + ".gTable=" +gTable);
        gGuild=guild;
        logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
    }
    public lcJSONUsersProfiles(lcGlobalHelper global,String table, String name){
        String fName="[constructor1]";
        gGlobal=global;
        gTable=table;
        logger.info(cName + fName + ".gTable=" +gTable);
        gName=name;
        logger.info(cName + fName + ".name=" + name);
    }
    public lcJSONUsersProfiles(lcGlobalHelper global,String table, Guild guild,String name){
        String fName="[constructor1]";
        gGlobal=global;
        gTable=table;
        logger.info(cName + fName + ".gTable=" +gTable);
        gGuild=guild;
        logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
        gName=name;
        logger.info(cName + fName + ".name=" + name);
    }
    public lcJSONUsersProfiles(lcGlobalHelper global, lcProfileEntityTable table, Guild guild){
        String fName="[constructor1]";
        gGlobal=global;
        if(table!=null)gTable=table.get();
        logger.info(cName + fName + ".gTable=" +gTable);
        gGuild=guild;
        logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
    }
    public lcJSONUsersProfiles(lcGlobalHelper global,lcProfileEntityTable table){
        String fName="[constructor1]";
        gGlobal=global;
        if(table!=null)gTable=table.get();
        logger.info(cName + fName + ".gTable=" +gTable);
    }
    public lcJSONUsersProfiles(lcGlobalHelper global, lcProfileEntityTable table, Guild guild, lcProfileEntityName name){
        String fName="[constructor1]";
        gGlobal=global;
        if(table!=null)gTable=table.get();
        logger.info(cName + fName + ".gTable=" +gTable);
        gGuild=guild;
        logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
        if(name!=null)gName=name.get();
        logger.info(cName + fName + ".name=" + name);
    }
    public lcJSONUsersProfiles(lcGlobalHelper global,lcProfileEntityTable table, lcProfileEntityName name){
        String fName="[constructor1]";
        gGlobal=global;
        if(table!=null)gTable=table.get();
        logger.info(cName + fName + ".gTable=" +gTable);
        if(name!=null)gName=name.get();
        logger.info(cName + fName + ".name=" + name);
    }
    static String keyId="id",keyUserId="userId",keyGuildId="guildID",keyName="name",keyJson="json",keyTable="table";
    public Boolean dbGetAllEntries(String table,Guild guild,String name){
        String fName="[dbGetAllEntries]";
        logger.info(fName);
        try{
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("user_id");columns.add("name"); columns.add("json");
            logger.info(fName+".table="+table);
            logger.info(cName + fName + ".guild=" + guild.getId());
            logger.info(cName + fName + ".name=" + name);
            logger.info(fName+".columns="+columns.toString());
            String query ="select * from "+table+" where guild_id='"+ guild.getId()+"'";
            if(name!=null&&!name.isBlank()){
                query="select * from "+table+" where guild_id='"+ guild.getId()+"' and name='"+name+"'";
            }
            logger.info(fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(fName+".executed");
            while (rs.next()){
                try {
                    int rsId=rs.getInt("id");
                    long rsUserId=rs.getLong("user_id");
                    String rsName=rs.getString("name");
                    JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyId,rsId);
                    jsonObject.put(keyUserId,rsUserId);
                    jsonObject.put(keyGuildId,guild.getId());
                    jsonObject.put(keyName,rsName);
                    jsonObject.put(keyJson,new JSONObject(rsJson));
                    jsonObject.put(keyTable,table);
                    ARRAY.put(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                
            }
           return true;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }

    }
    public Boolean dbGetAllEntries(String table,String name){
        String fName="[dbGetAllEntries]";
        logger.info(fName);
        try{
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("user_id");columns.add("name"); columns.add("json");
            logger.info(fName+".table="+table);
            logger.info(cName + fName + ".name=" + name);
            logger.info(fName+".columns="+columns.toString());
            String query ="select * from "+table+"";
            if(name!=null&&!name.isBlank()){
                query="select * from "+table+" where name='"+name+"'";
            }
            logger.info(fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(fName+".executed");
            while (rs.next()){
                try {
                    int rsId=rs.getInt("id");
                    long rsUserId=rs.getLong("user_id");
                    String rsName=rs.getString("name");
                    JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyId,rsId);
                    jsonObject.put(keyUserId,rsUserId);
                    jsonObject.put(keyName,rsName);
                    jsonObject.put(keyJson,new JSONObject(rsJson));
                    jsonObject.put(keyTable,table);
                    ARRAY.put(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }

    }
    public Boolean dbGetAllEntries(String table,Guild guild){
        String fName="[dbGetAllEntries]";
        logger.info(fName);
        try{
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("user_id"); columns.add("json");
            logger.info(fName+".table="+table);
            logger.info(cName + fName + ".guild=" + guild.getId());
            logger.info(fName+".columns="+columns.toString());
            String query = "select * from "+table+" where guild_id='"+ guild.getId()+"'";
            logger.info(fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(fName+".executed");
            while (rs.next()){
                try {
                    int rsId=rs.getInt("id");
                    long rsUserId=rs.getLong("user_id");
                    JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyId,rsId);
                    jsonObject.put(keyUserId,rsUserId);
                    jsonObject.put(keyGuildId,guild.getId());
                    jsonObject.put(keyJson,new JSONObject(rsJson));
                    jsonObject.put(keyTable,table);
                    ARRAY.put(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }

    }
    public Boolean dbGetAllEntries(String table){
        String fName="[dbGetAllEntries]";
        logger.info(fName);
        try{
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> columns=new ArrayList<>();
            columns.add("id"); columns.add("user_id");columns.add("json");
            logger.info(fName+".table="+table);
            logger.info(fName+".columns="+columns.toString());
            String query = "select * from "+table+"";
            logger.info(fName+".sql="+query);
            PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=pst.executeQuery();
            logger.info(fName+".executed");
            while (rs.next()){
                try {
                    int rsId=rs.getInt("id");
                    long rsUserId=rs.getLong("user_id");
                    JSONObject rsJson= new JSONObject((String) rs.getObject("json"));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyId,rsId);
                    jsonObject.put(keyUserId,rsUserId);
                    jsonObject.put(keyJson,new JSONObject(rsJson));
                    jsonObject.put(keyTable,table);
                    ARRAY.put(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }

    }
    public Boolean dbGetAllEntries(){
        String fName="[dbGetAllEntries]";
        logger.info(fName);
        try{
           if(gName==null){
               if(gGuild==null){
                   return dbGetAllEntries(gTable);
               }else{
                   return dbGetAllEntries(gTable,gGuild);
               }
           }else{
               if(gGuild==null){
                   return dbGetAllEntries(gTable,gName);
               }else{
                   return dbGetAllEntries(gTable,gGuild,gName);
               }
           }
        }
        catch(Exception e){
            logger.error(fName+".exception:"+e);return false;
        }

    }
    public JSONObject getUserProfileAsJSON(long userid){
        String fName="[getUserProfileAsJSON]";
        try {
            logger.info(fName+"userid="+userid);
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            for(int i=0;i<ARRAY.length();i++){
                JSONObject jsonObject=ARRAY.getJSONObject(i);
                logger.info(fName+"jsonObject["+i+"]="+jsonObject.toString());
                if(jsonObject.getLong(keyUserId)==userid){
                    return jsonObject;
                }
            }
            logger.info(fName+"not found");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public JSONObject getUserProfileAsJSON(Member member){
        String fName="[getUserProfileAsJSON]";
        try {
            logger.info(fName+"member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            return  getUserProfileAsJSON(member.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public JSONObject getUserProfileAsJSON(User user){
        String fName="[getUserProfileAsJSON]";
        try {
            logger.info(fName+"user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
            return  getUserProfileAsJSON(user.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public JSONObject getUserProfileAsJSON(int index){
        String fName="[getUserProfileAsJSON]";
        try {
            logger.info(fName+"index="+index);
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            if(index<0){
                logger.info(fName+"invalid index");
                return  null;
            }
            if(index>=ARRAY.length()){
                logger.info(fName+"invalid index");
                return  null;
            }
            JSONObject jsonObject=ARRAY.getJSONObject(index);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return  jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONUserProfile Json2JsonUserProfile(JSONObject jsonObject){
        String fName="[Json2JsonUserProfile]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return  null;
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(jsonObject.isEmpty()){
                logger.info(fName+"jsonObject.isEmpty()");
                return  null;
            }
            long userid=0L;
            if(jsonObject.has(keyUserId)&&!jsonObject.isNull(keyUserId)){
                userid=jsonObject.getLong(keyUserId);
            }
            logger.info(fName+"userid="+ userid);
            lcJSONUserProfile userProfile=null;
            String name="",table="";
            if(jsonObject.has(keyTable)&&!jsonObject.isNull(keyTable)){
                table=jsonObject.getString(keyTable);
            }
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)){
                name=jsonObject.getString(keyName);
            }
            logger.info(fName+"table="+ table+", name="+name);
            if(gGuild!=null){
                Member member= lsMemberHelper.lsGetMember(gGuild,userid);
                User user= lsUserHelper.lsGetUserById(gGuild.getJDA(),userid);
                if(member!=null){
                    if(!table.isBlank()&&!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,member,gGuild,name,table);
                    }
                    else if(!table.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,member,gGuild);
                        userProfile.setTable(table);
                    }
                    else if(!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,member,gGuild,name);
                    }
                    else {
                        userProfile=new lcJSONUserProfile(gGlobal,member,gGuild);
                    }
                }else
                if(user!=null){
                    if(!table.isBlank()&&!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,gGuild,name,table);
                    }
                    else if(!table.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,gGuild,new lcProfileEntityTable(table));
                    }
                    else if(!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,gGuild,name);
                    }
                    else {
                        userProfile=new lcJSONUserProfile(gGlobal,user,gGuild);
                    }
                }
            }else{
                User user=lsUserHelper.lsGetUser(gGlobal,userid);
                if(user!=null){
                    if(!table.isBlank()&&!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,name,table);
                    }
                    else if(!table.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,new lcProfileEntityTable(table));

                    }
                    else if(!name.isBlank()){
                        userProfile=new lcJSONUserProfile(gGlobal,user,name);
                    }
                    else {
                        userProfile=new lcJSONUserProfile(gGlobal,user);
                    }
                }
            }
            return  userProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONUserProfile getUserProfile(long userid){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"userid="+userid);
            JSONObject userJson=getUserProfileAsJSON(userid);
            if(userJson==null){
                logger.info(fName+"userJson is null");
                return  null;
            }
            if(userJson.isEmpty()){
                logger.info(fName+"userJson.isEmpty()");
                return  null;
            }
            return  Json2JsonUserProfile(userJson);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONUserProfile getUserProfile(Member member){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            return getUserProfile(member.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONUserProfile getUserProfile(User user){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
            return getUserProfile(user.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcJSONUserProfile getUserProfile(int index){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"index="+index);
            JSONObject userJson=getUserProfileAsJSON(index);
            if(userJson==null){
                logger.info(fName+"userJson is null");
                return  null;
            }
            if(userJson.isEmpty()){
                logger.info(fName+"userJson.isEmpty()");
                return  null;
            }
            return  Json2JsonUserProfile(userJson);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<lcJSONUserProfile> getUsersProfiles(){
        String fName="[getUserProfile]";
        try {
            List<lcJSONUserProfile>list=new ArrayList<>();
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            for(int i=0;i<ARRAY.length();i++){
                JSONObject userJson=ARRAY.getJSONObject(i);
                try {
                    logger.info(fName+"userJson["+i+"]="+userJson.toString());
                    lcJSONUserProfile userProfile= Json2JsonUserProfile(userJson);
                    if(userProfile!=null){
                        list.add(userProfile);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<lcJSONUserProfile> getUsersProfiles(String name){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"name="+name);
            List<lcJSONUserProfile>list=new ArrayList<>();
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            for(int i=0;i<ARRAY.length();i++){
                JSONObject userJson=ARRAY.getJSONObject(i);
                try {
                    logger.info(fName+"userJson["+i+"]="+userJson.toString());
                    if(userJson.has(keyName)&&!userJson.isNull(keyName)&&userJson.getString(keyName).equalsIgnoreCase(name)){
                        lcJSONUserProfile userProfile= Json2JsonUserProfile(userJson);
                        if(userProfile!=null){
                            list.add(userProfile);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<lcJSONUserProfile> getUserProfiles(String name,long userid){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"name="+name+", userid="+userid);
            List<lcJSONUserProfile>list=new ArrayList<>();
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            for(int i=0;i<ARRAY.length();i++){
                JSONObject userJson=ARRAY.getJSONObject(i);
                try {
                    logger.info(fName+"userJson["+i+"]="+userJson.toString());
                    if(userJson.has(keyUserId)&&!userJson.isNull(keyUserId)&&userJson.getLong(keyUserId)==userid){
                        if(userJson.has(keyName)&&!userJson.isNull(keyName)&&userJson.getString(keyName).equalsIgnoreCase(name)){
                            lcJSONUserProfile userProfile= Json2JsonUserProfile(userJson);
                            if(userProfile!=null){
                                list.add(userProfile);
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<lcJSONUserProfile> getUserProfiles(long userid){
        String fName="[getUserProfile]";
        try {
            logger.info(fName+"userid="+userid);
            List<lcJSONUserProfile>list=new ArrayList<>();
            if(ARRAY.isEmpty()){
                logger.info(fName+"ARRAY.isEmpty()");
                return  null;
            }
            for(int i=0;i<ARRAY.length();i++){
                JSONObject userJson=ARRAY.getJSONObject(i);
                try {
                    logger.info(fName+"userJson["+i+"]="+userJson.toString());
                    if(userJson.has(keyUserId)&&!userJson.isNull(keyUserId)&&userJson.getLong(keyUserId)==userid){
                        lcJSONUserProfile userProfile= Json2JsonUserProfile(userJson);
                        if(userProfile!=null){
                            list.add(userProfile);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
}
