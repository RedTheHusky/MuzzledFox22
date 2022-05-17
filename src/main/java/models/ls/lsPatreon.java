package models.ls;


import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public interface lsPatreon {

    long lsGuildKeyPatreon=481700616457027597L;
    long lsPatreonKeyTier1=816990848339935262L,lsPatreonKeyTier2=816990996797325343L,lsPatreonKeyTier3=816991039352602624L,lsPatreonKeyTier4=816991083766349834L;
    static JSONObject getPatreonTiers4File( User user){
        String fName="[getPatreonTiers4File_User]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            JSONObject result=new JSONObject();
            logger.info(fName+"user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
            lcText2Json text2Json=null;
            String name="users",gUrlMainSoloPath="resources/json/patreon";
            File file1, file2;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".txt");
                file1=new File(gUrlMainSoloPath+"/"+name+".txt");
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".json");
                file2=new File(gUrlMainSoloPath+"/"+name+".json");
                if(file2.exists()){
                    logger.info(fName+".file2 exists");
                    fileStream = new FileInputStream(file2);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            text2Json=new lcText2Json();
            if(fileStream!=null){
                if(!text2Json.isInputStream2JsonObject(fileStream)){
                    logger.warn(fName+".failed to load");
                    return result;
                }else{
                    logger.info(fName+".loaded from file");
                }
            }else{
                logger.warn(fName+".no input stream");
            }
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");
                return result;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            if(!text2Json.jsonObject.has(user.getId())){
                logger.warn(fName+".has no such user");
                return result;
            }
            if(text2Json.jsonObject.isNull(user.getId())){
                logger.warn(fName+".user is null");
                return result;
            }
            logger.info(fName + ".text2Json.jsonObject.user=" + text2Json.jsonObject.getJSONObject(user.getId()));
            /*String tiername="1";
            if(text2Json.jsonObject.getJSONObject(user.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(user.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="2";
            if(text2Json.jsonObject.getJSONObject(user.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(user.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="3";
            if(text2Json.jsonObject.getJSONObject(user.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(user.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="4";
            if(text2Json.jsonObject.getJSONObject(user.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(user.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }*/
            Iterator<String>keys=text2Json.jsonObject.getJSONObject(user.getId()).keys();
            while(keys.hasNext()){
                String key=keys.next();
                if(text2Json.jsonObject.getJSONObject(user.getId()).getBoolean(key)){
                    result.put(key,true);
                }
            }
            logger.info(fName + ".result=" + result);
            return  result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    static JSONObject getPatreonTiersAsJson(lcGlobalHelper global, User user){
        String fName="[getPatreonTiersAsJson_User]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            JSONObject result=new JSONObject();
            logger.info(fName+"user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
            Guild guild=global.getGuild(lsGuildKeyPatreon);
            logger.info(fName+"guildr="+guild.getName()+"("+guild.getId()+")");
            Member member=guild.getMember(user);
            List<Role> roles=member.getRoles();
            for(Role role:roles){
                if(role.getIdLong()==lsPatreonKeyTier1){
                    logger.info("found role 1");
                    result.put("1",true);
                }
                else if(role.getIdLong()==lsPatreonKeyTier2){
                    logger.info("found role 2");
                    result.put("2",true);
                }
                else if(role.getIdLong()==lsPatreonKeyTier3){
                    logger.info("found role 3");
                    result.put("3",true);
                }
                else if(role.getIdLong()==lsPatreonKeyTier4){
                    logger.info("found role 4");
                    result.put("4",true);
                }
            }
            JSONObject jsonObject= getPatreonTiers4File(user);

            if(!jsonObject.isEmpty()){
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                try{
                    Iterator<String>keys=jsonObject.keys();
                    while(keys.hasNext()){
                        String key=keys.next();
                        logger.info("jsonObject["+key+"]");
                        try{
                            if(jsonObject.getBoolean(key)){
                                int number=Integer.valueOf(key);
                                if(number>0){
                                    result.put(key,true);
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName+".exception=" + e);
                            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + ".result=" + result);
            return  result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    static boolean hasPatreonTier(lcGlobalHelper global, User user,int tier){
        String fName="[hasPatreonTier_User]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), tier="+tier);
            JSONObject tiers=getPatreonTiersAsJson(global, user);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  false;
            }
            String strTier=String.valueOf(tier);
            logger.info("tiers="+tiers.toString());
            logger.info("strTier="+strTier);
            if(tiers.has(strTier)&&tiers.getBoolean(strTier)){
                logger.info("found>true");
                return true;
            }
            logger.info("not found>false");
            return  false;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean hasPatreonTierOrAbove(lcGlobalHelper global, User user,int tier){
        String fName="[hasPatreonTierOrAbove_User]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), tier="+tier);
            JSONObject tiers=getPatreonTiersAsJson(global, user);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  false;
            }
            logger.info("tiers="+tiers.toString());
            logger.info("tier="+tier);
            Iterator<String>keys=tiers.keys();
            while(keys.hasNext()){
                String key=keys.next();
                logger.info("key="+key);
                try{
                    if(tiers.getBoolean(key)){
                        int number=Integer.valueOf(key);
                        logger.info("number="+number);
                        if(number>=tier){
                            logger.info("found>true");
                            return  true;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info("not found>false");
            return  false;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static int getMaxPatreonTier(lcGlobalHelper global, User user){
        String fName="[getMaxPatreonTier_User]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("user="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
            JSONObject tiers=getPatreonTiersAsJson(global, user);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  0;
            }
            int tier=0;
            Iterator<String>keys=tiers.keys();
            while(keys.hasNext()){
                String key=keys.next();
                logger.info("key="+key);
                try{
                    if(tiers.getBoolean(key)){
                        int number=Integer.valueOf(key);
                        if(number>tier){
                            logger.info("update tier to "+number);
                            tier=number;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info("tier="+tier);
            return  tier;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }

    static JSONObject getPatreonTiersAsJson(lcGlobalHelper global, Member member){
        String fName="[getPatreonTiersAsJson_member]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            User user=member.getUser();Guild guild=member.getGuild();
            logger.info("member="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), guild="+guild.getName()+"("+guild.getId()+")");
            return  getPatreonTiersAsJson(global, user);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    static boolean hasPatreonTier(lcGlobalHelper global, Member member,int tier){
        String fName="[hasPatreonTier_member]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            User user=member.getUser();Guild guild=member.getGuild();
            logger.info("member="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), guild="+guild.getName()+"("+guild.getId()+"), tier="+tier);
            return hasPatreonTier(global, user,tier);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean hasPatreonTierOrAbove(lcGlobalHelper global, Member member,int tier){
        String fName="[hasPatreonTierOrAbove_member]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            User user=member.getUser();Guild guild=member.getGuild();
            logger.info(fName+"member="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), guild="+guild.getName()+"("+guild.getId()+"), tier="+tier);
            boolean result=hasPatreonTierOrAbove(global, user,tier);
            logger.info(fName+".result=" +result);
            return result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static int getMaxPatreonTier(lcGlobalHelper global, Member member){
        String fName="[getMaxPatreonTier_member]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            User user=member.getUser();Guild guild=member.getGuild();
            logger.info("member="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+"), guild="+guild.getName()+"("+guild.getId()+")");
            return  getMaxPatreonTier(global, user);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }


    static JSONObject getPatreonTiers4File( Guild guild){
        String fName="[getPatreonTiers4File_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            JSONObject result=new JSONObject();
            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            lcText2Json text2Json=null;
            String name="guilds",gUrlMainSoloPath="resources/json/patreon";
            File file1, file2;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".txt");
                file1=new File(gUrlMainSoloPath+"/"+name+".txt");
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".json");
                file2=new File(gUrlMainSoloPath+"/"+name+".json");
                if(file2.exists()){
                    logger.info(fName+".file2 exists");
                    fileStream = new FileInputStream(file2);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            text2Json=new lcText2Json();
            if(fileStream!=null){
                if(!text2Json.isInputStream2JsonObject(fileStream)){
                    logger.warn(fName+".failed to load");
                    return result;
                }else{
                    logger.info(fName+".loaded from file");
                }
            }else{
                logger.warn(fName+".no input stream");
            }
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");
                return result;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            if(!text2Json.jsonObject.has(guild.getId())){
                logger.warn(fName+".has no such user");
                return result;
            }
            if(text2Json.jsonObject.isNull(guild.getId())){
                logger.warn(fName+".user is null");
                return result;
            }
            logger.info(fName + ".text2Json.jsonObject.guild=" + text2Json.jsonObject.getJSONObject(guild.getId()));
            /*String tiername="1";
            if(text2Json.jsonObject.getJSONObject(guild.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(guild.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="2";
            if(text2Json.jsonObject.getJSONObject(guild.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(guild.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="3";
            if(text2Json.jsonObject.getJSONObject(guild.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(guild.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }
            tiername="4";
            if(text2Json.jsonObject.getJSONObject(guild.getId()).has(tiername)&&text2Json.jsonObject.getJSONObject(guild.getId()).getBoolean(tiername)){
                result.put(tiername,true);
            }*/
            Iterator<String>keys=text2Json.jsonObject.getJSONObject(guild.getId()).keys();
            while(keys.hasNext()){
                String key=keys.next();
                if(text2Json.jsonObject.getJSONObject(guild.getId()).getBoolean(key)){
                    result.put(key,true);
                }
            }
            logger.info(fName + ".result=" + result);
            return  result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    static JSONObject getPatreonTiersAsJson(lcGlobalHelper global, Guild guild){
        String fName="[getPatreonTiersAsJson_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            JSONObject result=new JSONObject();
            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            Guild guildMain=global.getGuild(lsGuildKeyPatreon);
            logger.info("guildMain="+guildMain.getName()+"("+guildMain.getId()+")");
            JSONObject jsonObject= getPatreonTiers4File(guild);
            if(!jsonObject.isEmpty()){
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                try{
                    Iterator<String>keys=jsonObject.keys();
                    while(keys.hasNext()){
                        String key=keys.next();
                        logger.info("jsonObject["+key+"]");
                        try{
                            if(jsonObject.getBoolean(key)){
                                int number=Integer.valueOf(key);
                                if(number>0){
                                    result.put(key,true);
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName+".exception=" + e);
                            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + ".result=" + result);
            return  result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  new JSONObject();
        }
    }
    static boolean hasPatreonTier(lcGlobalHelper global, Guild guild,int tier){
        String fName="[hasPatreonTier_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            JSONObject tiers=getPatreonTiersAsJson(global, guild);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  false;
            }
            String strTier=String.valueOf(tier);
            logger.info("tiers="+tiers.toString());
            logger.info("strTier="+strTier);
            if(tiers.has(strTier)&&tiers.getBoolean(strTier)){
                logger.info("found>true");
                return true;
            }
            logger.info("not found>false");
            return  false;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean hasPatreonTierOrAbove(lcGlobalHelper global, Guild guild,int tier){
        String fName="[hasPatreonTierOrAbove_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            JSONObject tiers=getPatreonTiersAsJson(global, guild);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  false;
            }
            logger.info("tiers="+tiers.toString());
            logger.info("tier="+tier);
            Iterator<String>keys=tiers.keys();
            while(keys.hasNext()){
                String key=keys.next();
                logger.info("key="+key);
                try{
                    if(tiers.getBoolean(key)){
                        int number=Integer.valueOf(key);
                        logger.info("number="+number);
                        if(number>=tier){
                            logger.info("found>true");
                            return  true;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info("not found>false");
            return  false;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean hasPatreonTier(lcGlobalHelper global, Guild guild,String tier){
        String fName="[hasPatreonTier_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{

            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            JSONObject tiers=getPatreonTiersAsJson(global, guild);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  false;
            }
            logger.info("tiers="+tiers.toString());
            logger.info("tier="+tier);
            if(tiers.has(tier)&&tiers.getBoolean(tier)){
                logger.info("found>true");
                return true;
            }
            logger.info("not found>false");
            return  false;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static int getMaxPatreonTier(lcGlobalHelper global, Guild guild){
        String fName="[getMaxPatreonTier_guild]";
        Logger logger = Logger.getLogger(lsPatreon.class);
        try{
            logger.info("guild="+guild.getName()+"("+guild.getId()+")");
            JSONObject tiers=getPatreonTiersAsJson(global,guild);
            if(tiers.isEmpty()){
                logger.info("tiers.isEmpty");
                return  0;
            }
            int tier=0;
            Iterator<String>keys=tiers.keys();
            while(keys.hasNext()){
                String key=keys.next();
                logger.info("key="+key);
                try{
                    if(tiers.getBoolean(key)){
                        int number=Integer.valueOf(key);
                        if(number>tier){
                            logger.info("update tier to "+number);
                            tier=number;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info("tier="+tier);
            return  tier;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
}
