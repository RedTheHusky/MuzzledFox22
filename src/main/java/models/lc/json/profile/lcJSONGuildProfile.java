package models.lc.json.profile;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGenericProfile;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONGuildProfile extends lcJSONGenericProfile {
    Logger logger = Logger.getLogger(getClass());  String cName="[lcJSONGuildSettingEntry]";
    protected Guild gGuild;
    protected String kGuild;
    protected String kTable = llGlobalHelper.llv2_GuildsSettings;
    public lcJSONGuildProfile(lcGlobalHelper global){
        String fName="[constructor1]";
        try {
            gGlobal=global;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGuildProfile(lcGlobalHelper global, String name, Guild guild){
        String fName="[constructor1]";
        try {
            if(!build(global,guild,name))throw  new Exception("Failed to build 1");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGuildProfile(lcGlobalHelper global, String name, Guild guild, boolean autoget){
        String fName="[constructor2]";
        try {
            if(!build(global,guild,name))throw  new Exception("Failed to build 1");
            if(autoget){
                getProfile(kTable,name);
            }
            logger.info(cName+fName+".ready");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGuildProfile(lcGlobalHelper global, String name, Guild guild, String table){
        String fName="[constructor2]";
        try {
            if(!build(global,guild,name,table))throw  new Exception("Failed to build 1");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGuildProfile(lcGlobalHelper global, String name, Guild guild, String table, boolean autoget){
        String fName="[constructor2]";
        try {

            if(!build(global,guild,name,table))throw  new Exception("Failed to build 1");
            logger.info(cName+fName+".autoget="+autoget);
            if(autoget){
                getProfile(kTable,name);
            }
            logger.info(cName+fName+".ready");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean build(lcGlobalHelper global,Guild guild,String name){
        String fName="[build1]";
        try {
            if(setGlobal(global)==null||setName(name)==null)throw  new Exception("Failed to build");
            gGuild=guild;
            kGuild=guild.getId();
            logger.info(cName + fName + ".name:" + name );
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".table:" + kTable);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean build(lcGlobalHelper global,Guild guild,String name,String table){
        String fName="[build2]";
        try {
            if(setGlobal(global)==null||setName(name)==null||setTable(table)==null)throw  new Exception("Failed to build");
            gGuild=guild;
            kGuild=guild.getId();
            logger.info(cName + fName + ".name:" + name );
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".table:" + kTable);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean hasProfile(String table, String name){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName+"table="+table);
            if(setTable(table)==null||setName(name)==null){
                logger.warn(cName+fName+"aborted");
                return false;
            }
            return hasProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean hasProfile(String table){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName+"table="+table);
            if(setTable(table)==null){
                logger.warn(cName+fName+"aborted");
                return false;
            }
            return hasProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean hasProfile(){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(kTable,"name = '"+kName+"' and guild_id='"+kGuild+"'",colums);
            if(rows==null){logger.error(cName+fName+".select null"); return false;}
            boolean isGo=false;
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equalsIgnoreCase("json") && value != null) {
                    isGo = true;
                    break;
                }
            }
            if(!isGo){logger.error(cName+fName+".no go"); return false; }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

    }
    public Boolean getProfile(String table, String name){
        String fName="[getProfile]";
        try {
            logger.info(cName+fName+"table="+table);
            if(setTable(table)==null||setName(name)==null){
                logger.warn(cName+fName+"aborted");
                return false;
            }
            return getProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getProfile(String table){
        String fName="[getEntry]";
        logger.info(cName+fName);
        try {
            logger.info(cName+fName+"table="+table);
            if(setTable(table)==null){
                logger.warn(cName+fName+"aborted");
                return false;
            }
            return getProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(kTable,"name = '"+kName+"' and guild_id='"+kGuild+"'",colums);
            if(rows==null){logger.error(cName+fName+".select null"); return false; }
            boolean isGo=false;
            String profile="";
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("json")&&value!=null){
                    isGo=true; profile=value.toString();
                }
            }
            if(!isGo){logger.error(cName+fName+".no go");return false; }
            jsonObject =new JSONObject(profile);
            logger.info(cName+fName+".success:");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(String table, String name){
        String fName="[saveProfile]";
        try {
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            colums.add("id");colums.add("name");colums.add("guild_id");colums.add("json");
            Map<String,Object> rows=gGlobal.sql.selectFirst(table,"name = '"+name+"' and guild_id='"+gGuild.getId()+"'",colums);
            if(rows==null){
                logger.warn(cName + fName + ".row is null");
                return false;
            }
            if(!rows.isEmpty()){
                return saveProfile_Update();
            }else{
                return saveProfile_Insert();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(String table){
        String fName="[saveProfile]";
        logger.info(cName+fName);
        return saveProfile(table,kName);
    }
    public Boolean saveProfile(){
        String fName="[saveEntry]";
        logger.info(cName+fName);
        return saveProfile(kTable,kName);
    }
    private Boolean saveProfile_Update(){
        String fName="[saveProfile_Update]";
        try {
            logger.info(cName+fName+"init");
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("json", jsonObject.toString());
            if (!gGlobal.sql.update(kTable, data,"name = '"+kName+"' and guild_id='"+gGuild.getId()+"'" )) {
                logger.error(cName + fName + ".error while updating");return false;
            }else{
                logger.info(cName + fName + ".success update");isUpdated=false;return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    private Boolean saveProfile_Insert(){
        String fName="[saveProfile_Insert]";
        try {
            logger.info(cName+fName+"init");
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("guild_id",gGuild.getId());
            data.put("name", kName);
            data.put("json", jsonObject.toString());
            if (!gGlobal.sql.insert(kTable, data)) {
                logger.error(cName + fName + ".error while inserting");return false;
            }else{
                logger.info(cName + fName + ".success insert");isUpdated=false;return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }

    public lcJSONGuildProfile setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            super.setGlobal(global);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile setTable(String table){
        String fName="[setTable]";
        try {
            super.setTable(table);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile setName(String name){
        String fName="[setName]";
        try {
            super.setName(name);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile clearTable(){
        String fName="[clearTable]";
        try {
            super.clearTable();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile clearName(){
        String fName="[clearName]";
        try {
            super.clearName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile clearJson(){
        String fName="[clearJson]";
        try {
            super.clearJson();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcJSONGuildProfile setGuild(String guild_id){
        String fName="[setGuild]";
        try {
            if(guild_id==null)throw  new Exception(cName+fName+", input guild_id cant be null!");
            if(guild_id.isBlank())throw  new Exception(cName+fName+", input guild_id cant be blank!");
            logger.info(cName+fName+"guild_id="+guild_id);
            kGuild=guild_id;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONGuildProfile setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null)throw  new Exception(cName+fName+", input guild cant be null!");
            logger.info(cName+fName+"guild="+guild.getIdLong());
            if(setGuild(guild.getId())==null)throw  new Exception(cName+fName+", failed to set user!");
            gGuild=guild;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getGuildId(){
        String fName="[getGuildId]";
        try {
            logger.info(cName+fName+"kUser="+kGuild);
            return kGuild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(){
        String fName="[getGuild]";
        try {
            logger.info(cName+fName+"gGuild="+gGuild.getId());
            return gGuild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
