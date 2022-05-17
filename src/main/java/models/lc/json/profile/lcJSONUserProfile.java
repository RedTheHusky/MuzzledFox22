package models.lc.json.profile;

import kong.unirest.json.JSONObject;
import models.lc.json.lcProfileEntityName;
import models.lc.json.lcProfileEntityTable;
import models.lcGlobalHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.*;

public class lcJSONUserProfile extends lcJSONGenericProfile {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcJSONUserProfile]";
    public lcJSONUserProfile(){
        String fName="[constructor]";
        try {
            jsonObject =new JSONObject();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private User gUser; private Guild gGuild;private String kUser;private String kGuild;
    private Member gMember;
    private lcGlobalHelper gGlobal;
    public lcJSONUserProfile(lcGlobalHelper global,User user, Guild guild, String name,String table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            kName =name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user, Guild guild, String name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            kName =name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user, Guild guild){
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
    public lcJSONUserProfile(lcGlobalHelper global,User user){
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
    public lcJSONUserProfile(lcGlobalHelper global,User user,String name,String table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            kName =name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user,String name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            kName =name;
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild, String name,String table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            kName =name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild, String name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            kName =name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild){
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
    public lcJSONUserProfile(lcGlobalHelper global,Member member){
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
    public lcJSONUserProfile(lcGlobalHelper global,Member member,String name,String table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            kName =name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
            gTable=table;
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member,String name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            kName =name;
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcJSONUserProfile(lcGlobalHelper global, User user, Guild guild, lcProfileEntityName name, lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            if(table!=null)gTable=table.get();
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user, Guild guild, lcProfileEntityName name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user, Guild guild, lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(table!=null)gTable=table.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".table="+gTable);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user,lcProfileEntityName name,lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
            if(table!=null)gTable=table.get();
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user,lcProfileEntityName name){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,User user,lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(table!=null)gTable=table.get();
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".table="+gTable);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild, lcProfileEntityName name,lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
            if(table!=null)gTable=table.get();
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild, lcProfileEntityName name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member, Guild guild, lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=guild;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();kGuild=guild.getId();
            if(table!=null)gTable=table.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:" + guild.getId() + "|" + guild.getName());
            logger.info(cName + fName + ".table="+gTable);
            logger.info(cName+fName+".kUser="+kUser);logger.info(cName+fName+".kGuild="+kGuild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member,lcProfileEntityName name,lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
            if(table!=null)gTable=table.get();
            logger.info(cName+fName+".gTable="+gTable);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member,lcProfileEntityName name){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(name!=null) kName =name.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".name="+ kName);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONUserProfile(lcGlobalHelper global,Member member,lcProfileEntityTable table){
        String fName="[constructor]";
        try {
            gMember=member;User user=gMember.getUser();
            gUser=user;
            gGuild=null;
            gGlobal=global;
            jsonObject =new JSONObject();
            kUser=user.getId();
            if(table!=null)gTable=table.get();
            logger.info(cName + fName + ".member: true");
            logger.info(cName + fName + ".user:" + user.getId() + "|" + user.getName());
            logger.info(cName + fName + ".guild:null");
            logger.info(cName + fName + ".table="+gTable);
            logger.info(cName+fName+".kUser="+kUser);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


    public lcJSONUserProfile setGlobal(lcGlobalHelper global){
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
    public lcJSONUserProfile setTable(String table){
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
    public lcJSONUserProfile setName(String name){
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
    public lcJSONUserProfile clearTable(){
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
    public lcJSONUserProfile clearName(){
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
    public lcJSONUserProfile clearJson(){
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

    public lcJSONUserProfile setUser(String user_id){
        String fName="[setUser]";
        try {
            if(user_id==null)throw  new Exception(cName+fName+", input user_id cant be null!");
            if(user_id.isBlank())throw  new Exception(cName+fName+", input user_id cant be blank!");
            logger.info(cName+fName+"user_id="+user_id);
            kUser=user_id;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile setUser(User user){
        String fName="[setUser]";
        try {
            if(user==null)throw  new Exception(cName+fName+", input user cant be null!");
            logger.info(cName+fName+"user="+user.getIdLong());
            if(setUser(user.getId())==null)throw  new Exception(cName+fName+", could not set user_id!");
            gUser=user;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile setGuild(String guild_id){
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
    public lcJSONUserProfile setGuild(Guild guild){
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
    public lcJSONUserProfile setMember(Member member){
        String fName="[setMember]";
        try {
            if( member==null)throw  new Exception(cName+fName+", input member cant be null!");
            logger.info(cName+fName+"member="+member.getIdLong());
            if(setUser(member.getUser())==null)throw  new Exception(cName+fName+", failed to set user!");
            if(setGuild(member.getGuild())==null)throw  new Exception(cName+fName+", failed to set guild!");
            gMember=member;
            logger.info(cName+fName+"set");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcJSONUserProfile clearMember(){
        String fName="[clearMember]";
        try {
            logger.info(cName+fName+" kUser_old="+ kUser);
            gMember=null;
            gUser=null;
            kUser=null;
            logger.info(cName+fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getUserId(){
        String fName="[getUserId]";
        try {
            logger.info(cName+fName+"kUser="+kUser);
            return kUser;
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
    public User getUser(){
        String fName="[getUse]";
        try {
            logger.info(cName+fName+"gUser="+gUser.getId());
            return gUser;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember(){
        String fName="[getMember]";
        try {
            logger.info(cName+fName+"gMember="+gMember.getId());
            return gMember;
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
    public Boolean getProfile(String table){
        String fName="[getProfile]";
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
    public Boolean saveProfile(String table){
        String fName="[saveProfile]";
        try {
            logger.info(cName+fName+"table="+table);
            if(setTable(table)==null){
                logger.warn(cName+fName+"aborted");
                return false;
            }
            gTable=table;
            return saveProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
    public Boolean hasProfile(){
        String fName="[hasProfile]";
        try {
            logger.info(cName+fName+"gTable="+gTable);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(kGuild==null||kGuild.isBlank()){
                if(kName ==null|| kName.isBlank()) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(gTable, "user_id = '" + kUser + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and name = '"+ kName +"'",colums);
                }
            }else{
                if(kName ==null|| kName.isBlank()) {
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(gTable, "user_id = '" + kUser + "' and guild_id='" + kGuild + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+ kName +"'",colums);
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
    public Boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(cName+fName+"gTable="+gTable);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(kGuild==null||kGuild.isBlank()){
                if(kName ==null|| kName.isBlank()) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"'",colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and name = '"+ kName +"'",colums);
                }
            }else{
                if(kName ==null|| kName.isBlank()) {
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and guild_id='"+kGuild+"'",colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("guild_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+ kName +"'",colums);
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
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        try {
            logger.info(cName+fName+"gTable="+gTable+", kGuild="+kGuild+", kUser="+kUser+", kName="+kName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();

            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(kGuild==null||kGuild.isBlank()){
                if(kName ==null|| kName.isBlank()) {
                    colums.add("id");colums.add("user_id");colums.add("json");
                    rows = gGlobal.sql.selectFirst(gTable, "user_id = '" + kUser + "'", colums);
                }else{
                    colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                    rows=gGlobal.sql.selectFirst(gTable,"user_id = '"+kUser+"' and name = '"+ kName +"'",colums);
                }
            }else {
                if (kName ==null|| kName.isBlank()) {
                    colums.add("id");
                    colums.add("user_id");
                    colums.add("guild_id");
                    colums.add("json");
                    rows = gGlobal.sql.selectFirst(gTable, "user_id = '" + kUser + "' and guild_id='" + kGuild + "'", colums);
                } else {
                    colums.add("id");
                    colums.add("user_id");
                    colums.add("guild_id");
                    colums.add("name");
                    colums.add("json");
                    rows = gGlobal.sql.selectFirst(gTable, "user_id = '" + kUser + "' and guild_id='" + kGuild + "' and name = '" + kName + "'", colums);
                }
            }
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
            return false;
        }

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
            if(kGuild==null||kGuild.isBlank()){
                if(kName ==null|| kName.isBlank()) {
                    if (!gGlobal.sql.update(gTable, data,"user_id = '"+kUser+"'" )) {
                        logger.error(cName + fName + ".error while updating"); return false;
                    }else{
                        logger.info(cName + fName + ".success update");isUpdated=false;return true;
                    }
                }else{
                    data.put("json", jsonObject.toString());
                    if (!gGlobal.sql.update(gTable, data,"user_id = '"+kUser+"' and name = '"+ kName +"'" )) {
                        logger.error(cName + fName + ".error while updating"); return false;
                    }else{
                        logger.info(cName + fName + ".success update");isUpdated=false;return true;
                    }
                }
            }else{
                if(kName ==null|| kName.isBlank()) {
                    if (!gGlobal.sql.update(gTable, data,"user_id = '"+kUser+"' and guild_id='"+kGuild+"'" )) {
                        logger.error(cName + fName + ".error while updating"); return false;
                    }else{
                        logger.info(cName + fName + ".success update");isUpdated=false;return true;
                    }
                }else{
                    data.put("json", jsonObject.toString());
                    if (!gGlobal.sql.update(gTable, data,"user_id = '"+kUser+"' and guild_id='"+kGuild+"' and name = '"+ kName +"'" )) {
                        logger.error(cName + fName + ".error while updating"); return false;
                    }else{
                        logger.info(cName + fName + ".success update");isUpdated=false;return true;
                    }
                }
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
            if(kGuild==null||kGuild.isBlank()){
                if(kName ==null|| kName.isBlank()) {
                    data.put("user_id",kUser);
                    data.put("json", jsonObject.toString());
                }else{
                    data.put("user_id",kUser);
                    data.put("name", kName);
                    data.put("json", jsonObject.toString());
                }
            }else{
                if(kName ==null|| kName.isBlank()) {
                    data.put("user_id", kUser);
                    data.put("guild_id",kGuild);
                    data.put("json", jsonObject.toString());
                }else{
                    data.put("user_id", kUser);
                    data.put("guild_id",kGuild);
                    data.put("name", kName);
                    data.put("json", jsonObject.toString());
                }
            }
            if (!gGlobal.sql.insert(gTable, data)) {
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
    @Deprecated
    @DeprecatedSince("12/15/21 need to finish it")
    public Boolean saveCopy(String table,String name,String guild_id,String user_id){
        String fName="[saveCopy]";
        try {
            logger.info(cName+fName+"table="+table+", name="+name+", guild_id="+guild_id+", user_id="+user_id);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return false;
            }
            Map<String, Object> data = new LinkedHashMap<>();

            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            if(guild_id==null||guild_id.isBlank()){
                if(name ==null|| name.isBlank()) {
                    if(user_id==null||user_id.isBlank()){
                        colums.add("id");colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "user_id = '" + user_id + "'", colums);
                    }else{
                        colums.add("id");colums.add("user_id");colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "user_id = '" + user_id + "'", colums);
                    }
                }else{
                    if(user_id==null||user_id.isBlank()){
                        colums.add("id");colums.add("user_id");colums.add("name");colums.add("json");
                        rows=gGlobal.sql.selectFirst(table,"user_id = '"+user_id+"' and name = '"+ name +"'",colums);
                    }else{
                        colums.add("id");colums.add("name");colums.add("json");
                        rows=gGlobal.sql.selectFirst(table,"user_id = '"+user_id+"' and name = '"+ name +"'",colums);
                    }
                }
            }else {
                if (name ==null|| name.isBlank()) {
                    if(user_id==null||user_id.isBlank()){
                        colums.add("id");
                        colums.add("guild_id");
                        colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "guild_id='" + guild_id + "'", colums);
                    }else{
                        colums.add("id");
                        colums.add("user_id");
                        colums.add("guild_id");
                        colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "user_id = '" + user_id + "' and guild_id='" + guild_id + "'", colums);
                    }
                } else {
                    if(user_id==null||user_id.isBlank()){
                        colums.add("id");
                        colums.add("guild_id");
                        colums.add("name");
                        colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "guild_id='" + guild_id + "' and name = '" + name + "'", colums);
                    }else {
                        colums.add("id");
                        colums.add("user_id");
                        colums.add("guild_id");
                        colums.add("name");
                        colums.add("json");
                        rows = gGlobal.sql.selectFirst(table, "user_id = '" + user_id + "' and guild_id='" + guild_id + "' and name = '" + name + "'", colums);
                    }
                }
            }
            if(rows==null){
                logger.warn(cName + fName + ".row is null");
                return false;
            }
            if(!rows.isEmpty()){
                data.put("json", jsonObject.toString());
                if(guild_id==null||guild_id.isBlank()){
                    if(name ==null|| name.isBlank()) {
                        if (!gGlobal.sql.update(table, data,"user_id = '"+user_id+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }else{
                        data.put("json", jsonObject.toString());
                        if (!gGlobal.sql.update(table, data,"user_id = '"+user_id+"' and name = '"+ name +"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }
                }else{
                    if(name ==null|| name.isBlank()) {
                        if (!gGlobal.sql.update(table, data,"user_id = '"+user_id+"' and guild_id='"+guild_id+"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }else{
                        data.put("json", jsonObject.toString());
                        if (!gGlobal.sql.update(table, data,"user_id = '"+user_id+"' and guild_id='"+guild_id+"' and name = '"+ name +"'" )) {
                            logger.error(cName + fName + ".error while updating");
                        }else{
                            logger.info(cName + fName + ".success update");isUpdated=false;return true;
                        }
                    }
                }
            }else{
                if(guild_id==null||guild_id.isBlank()){
                    if(name ==null|| name.isBlank()) {
                        data.put("user_id",kUser);
                        data.put("json", jsonObject.toString());
                    }else{
                        data.put("user_id",kUser);
                        data.put("name", name);
                        data.put("json", jsonObject.toString());
                    }
                }else{
                    if(name ==null|| name.isBlank()) {
                        data.put("user_id", user_id);
                        data.put("guild_id",guild_id);
                        data.put("json", jsonObject.toString());
                    }else{
                        data.put("user_id", user_id);
                        data.put("guild_id",guild_id);
                        data.put("name", name);
                        data.put("json", jsonObject.toString());
                    }
                }
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


}
