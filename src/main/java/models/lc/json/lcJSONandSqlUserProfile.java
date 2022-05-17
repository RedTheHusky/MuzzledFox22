package models.lc.json;

import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONandSqlUserProfile extends lcJSONGenericEntry{
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONandSqlUserProfile]";
    lcJSONandSqlUserProfile(){
        String fName="[constructor]";
        logger.warn(cName+fName+".not read");
    }
    public User gUser; public Guild gGuild;public String kUser;public String gName,gTable;public String kGuild;
    public Member gMember;
    lcGlobalHelper gGlobal;
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user, Guild guild, String name, String table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            gName=name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user, Guild guild, String name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            gName=name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user, Guild guild){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name:null");
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user){
        String fName="[constructor]";
        try {
            gUser=user;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name:null");
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user, String name, String table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            gName=name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, User user, String name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            gName=name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member, Guild guild, String name, String table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            gName=name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member, Guild guild, String name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            gName=name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member, Guild guild){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name:null");
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name:null");
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member, String name, String table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            gName=name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONandSqlUserProfile(lcGlobalHelper global, Member member, String name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            gName=name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+gName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public Boolean hasProfile(String table){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName);
            logger.info(cName+fName+"table="+table);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(gGuild==null){
                if(gName==null) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(table, "user_id = '" + kUser + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and name = '"+gName+"'",colums);
                }
            }else{
                if(gName==null) {
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(table, "user_id = '" + kUser + "' and guild_id='" + kGuild + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+gName+"'",colums);
                }
            }
            if(rows==null){logger.info(cName+fName+".select null"); return false;}
            boolean isGo=false;
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equalsIgnoreCase("json") && value != null) {
                    isGo = true;
                    break;
                }
            }
            if(!isGo){logger.info(cName+fName+".no go"); return false; }
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Boolean getJSONProfile(String table){
        String fName="[getJSONProfile]";
        try {
            logger.info(cName+fName);
            logger.info(cName+fName+"table="+table);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(gGuild==null){
                if(gName==null) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"'",colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and name = '"+gName+"'",colums);
                }
            }else{
                if(gName==null) {
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and guild_id='"+kGuild+"'",colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+gName+"'",colums);
                }
            }

            if(rows==null){logger.info(cName+fName+".select null"); return false; }
            boolean isGo=false;
            String profile="";
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("json")&&value!=null){
                    isGo=true; profile=value.toString();
                }
            }
            if(!isGo){logger.info(cName+fName+".no go");return false; }
            jsonObject =new JSONObject(profile);
            logger.info(cName+fName+".jsonUser="+ jsonObject.toString());
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Boolean getProfile(String table){
        String fName="[getProfile]";
        try {
            logger.info(cName+fName);
            logger.info(cName+fName+"table="+table);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(gGuild==null){
                if(gName==null) {
                    //colums.add("id");colums.add("user_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"'");
                }else{
                    //colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and name = '"+gName+"'");
                }
            }else{
                if(gName==null) {
                    //colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and guild_id='"+kGuild+"'");
                }else{
                    //colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+gName+"'");
                }
            }

            if(rows==null){logger.info(cName+fName+".select null"); return false; }
            boolean isGo=false;
            String profile="";
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                try {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(key.equalsIgnoreCase("json")&&value!=null){
                        isGo=true; profile=value.toString();
                    }else
                    if(value!=null&&!key.equalsIgnoreCase("id")&&!key.equalsIgnoreCase("user_id")&&!key.equalsIgnoreCase("guild_id")&&!key.equalsIgnoreCase("name")){
                        isGo=true; jsonSQL.put(key,new JSONObject(value.toString()));
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(!isGo){logger.info(cName+fName+".no go");return false; }
            jsonObject =new JSONObject(profile);
            logger.info(cName+fName+".jsonUser="+ jsonObject.toString());
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Boolean saveProfile(String table){
        String fName="[saveProfile]";
        try {
            logger.info(cName+fName);
            logger.info(cName+fName+"table="+table);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();

            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(gGuild==null){
                if(gName==null) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(table, "user_id = '" + kUser + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(table,"user_id = '"+kUser+"' and name = '"+gName+"'",colums);
                }
            }else {
                if (gName == null) {
                    colums.add("id");
                    colums.add("user_id");
                    colums.add("guild_id");
                    colums.add("json");
                    rows = gGlobal.sql.selectFirst(table, "user_id = '" + kUser + "' and guild_id='" + kGuild + "'", colums);
                } else {
                    colums.add("id");
                    colums.add("user_id");
                    colums.add("guild_id");
                    colums.add("name");
                    colums.add("json");
                    rows = gGlobal.sql.selectFirst(table, "user_id = '" + kUser + "' and guild_id='" + kGuild + "' and name = '" + gName + "'", colums);
                }
            }
            if(rows==null){
                logger.warn(cName + fName + ".row is null");
                return false;
            }
            if(!rows.isEmpty()){
                data.put("json", jsonObject.toString());
                if(gGuild==null){
                    if(gName==null) {
                        if (!gGlobal.sql.update(table, data,"user_id = '"+kUser+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }else{
                        data.put("json", jsonObject.toString());
                        data=addingSQLExtraEntry( data );
                        if (!gGlobal.sql.update(table, data,"user_id = '"+kUser+"' and name = '"+gName+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }
                }else{
                    if(gName==null) {
                        if (!gGlobal.sql.update(table, data,"user_id = '"+kUser+"' and guild_id='"+kGuild+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }else{
                        data.put("json", jsonObject.toString());
                        data=addingSQLExtraEntry( data );
                        if (!gGlobal.sql.update(table, data,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+gName+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }

                }
            }else{
                if(gGuild==null){
                    if(gName==null) {
                        data.put("user_id",kUser);
                        data.put("json", jsonObject.toString());
                    }else{
                        data.put("user_id",kUser);
                        data.put("name",gName);
                        data.put("json", jsonObject.toString());
                    }
                }else{
                    if(gName==null) {
                        data.put("user_id", kUser);
                        data.put("guild_id",kGuild);
                        data.put("json", jsonObject.toString());
                    }else{
                        data.put("user_id", kUser);
                        data.put("guild_id",kGuild);
                        data.put("name",gName);
                        data.put("json", jsonObject.toString());
                    }
                }
                data=addingSQLExtraEntry( data );
                if (!gGlobal.sql.insert(table, data)) {
                    logger.error(cName + fName + ".error while inserting");
                }else{
                    logger.info(cName + fName + ".success insert");isUpdated=false;return true;
                }

            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Map<String, Object> addingSQLExtraEntry( Map<String, Object> data ){
        String fName="[addingSQLExtraEntry]";
        try {
            logger.info(cName + fName + "jsonSQL="+jsonSQL.toString());
            Iterator<String>keys=jsonSQL.keys();
            int i=0;
            while(keys.hasNext()){
                String key=keys.next();
                logger.info(cName + fName + "key["+i+"]="+key+" value="+jsonSQL.getJSONObject(key).toString());
                switch (jsonSQL.getJSONObject(key).getString("type").toLowerCase()){
                    case "string":
                    case "text":
                        data.put(key,jsonSQL.getJSONObject(key).getString("value"));
                        break;
                    case "int":
                        data.put(key,jsonSQL.getJSONObject(key).getInt("value"));
                        break;
                    case "bigint":
                        data.put(key,jsonSQL.getJSONObject(key).getBigInteger("value"));
                        break;
                    case "long":
                        data.put(key,jsonSQL.getJSONObject(key).getLong("value"));
                        break;
                    case "double":
                        data.put(key,jsonSQL.getJSONObject(key).getDouble("value"));
                        break;
                    case "float":
                        data.put(key,jsonSQL.getJSONObject(key).getFloat("value"));
                        break;
                    case "boolean":
                        data.put(key,jsonSQL.getJSONObject(key).getBoolean("value"));
                        break;
                    case "jsonobject":
                        data.put(key,jsonSQL.getJSONObject(key).getJSONObject("value"));
                        break;
                    case "jsonarray":
                        data.put(key,jsonSQL.getJSONObject(key).getJSONArray("value"));
                        break;
                    default:  data.put(key,jsonSQL.getJSONObject(key).get("value"));
                        break;
                }
                i++;
            }
            return data;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return data;
        }
    }

    public Boolean hasProfile(){
        String fName="[hasProfile]";
        logger.info(cName+fName);
        return hasFieldEntry(gTable);
    }
    public Boolean getProfile(){
        String fName="[getProfile]";
        logger.info(cName+fName);
        return getProfile(gTable);
    }
    public Boolean getJSONProfile(){
        String fName="[getJSONProfile]";
        logger.info(cName+fName);
        return getJSONProfile(gTable);
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(cName+fName);
        return saveProfile(gTable);
    }

    public JSONObject jsonSQL =new JSONObject();
    public Boolean putSQLEntry(String name,String type, Object value){
        String fName="[putSQLEntry]";
        try {
            logger.info(cName+fName+".name="+name+", type="+type+", value="+value);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",type);jsonObject.put("value", value);
            jsonSQL.put(name,jsonObject);
            logger.info(cName+fName+".jsonSQL="+jsonSQL);
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
}
