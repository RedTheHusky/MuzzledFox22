package models.ls;

import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.*;

public interface lsGuildHelper
{
    static String lsGetGuildInviteLink(Guild guild){
        try{
            String selectedInviteLink="";
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)){
                selectedInviteLink=lsCustomGuilds.lsGuildBdsmInviteLink;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)){
                selectedInviteLink=lsCustomGuilds.lsGuildChastityInviteLink;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)){
                selectedInviteLink=lsCustomGuilds.lsGuildSnuffInviteLink;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAsylum)){
                selectedInviteLink=lsCustomGuilds.lsGuildAsylumInviteLink;
            }
            if(guild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyPrison)){
                selectedInviteLink=lsCustomGuilds.lsGuildPrisonInviteLink;
            }
            if(selectedInviteLink.isBlank()){
                return null;
            }
            return selectedInviteLink;
        }
        catch(Exception e){
            return null;
        }
    }
    static String lsGetGuildInviteLink(lcGlobalHelper global, Guild guild){
        try{
            String selectedInviteLink="";
            lcJSONGuildProfile entrie=global.getGuildSettings(guild, global.keyServer);
            if(!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(global.keyInviteLink)&&!entrie.jsonObject.isNull(global.keyInviteLink)){
                    selectedInviteLink= entrie.jsonObject.getString(global.keyInviteLink);
                }
            }
            return selectedInviteLink;
        }
        catch(Exception e){
            return null;
        }
    }
    static boolean lsIsGuildNSFW(lcGlobalHelper global, Guild guild){
        try{
            boolean result=false;
            String id=guild.getId();
            if(id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBlackGazza)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyLax)
            ){
                return true;
            }
            lcJSONGuildProfile entrie=global.getGuildSettings(guild, global.keyServer);
            if(!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(global.keyIsNSFWServer)&&!entrie.jsonObject.isNull(global.keyIsNSFWServer)){
                    result= entrie.jsonObject.getBoolean(global.keyIsNSFWServer);
                }
            }
            return result;
        }
        catch(Exception e){
            return false;
        }
    }
    static boolean lsIsGuildInHouse(lcGlobalHelper global, Guild guild){
        try{
            boolean result=false;
            String id=guild.getId();
            if(id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBlackGazza)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAsylum)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyPrison)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAdministration)
                    //||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyLax)
            ){
                return true;
            }
            lcJSONGuildProfile entrie=global.getGuildSettings(guild, global.keyInHouse);
            if(!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(global.keyInHouse)&&!entrie.jsonObject.isNull(global.keyInHouse)){
                    result= entrie.jsonObject.getBoolean(global.keyInHouse);
                }
            }
            return result;
        }
        catch(Exception e){
            return false;
        }
    }
    static boolean lsIsGuildCustomServer(lcGlobalHelper global, Guild guild){
        try{
            boolean result=false;
            String id=guild.getId();
            if(id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBlackGazza)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAsylum)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyPrison)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyAdministration)
                //||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyLax)
            ){
                return true;
            }
            lcJSONGuildProfile entrie=global.getGuildSettings(guild, global.keyCustomServer);
            if(!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(global.keyCustomServer)&&!entrie.jsonObject.isNull(global.keyCustomServer)){
                    result= entrie.jsonObject.getBoolean(global.keyCustomServer);
                }
            }
            return result;
        }
        catch(Exception e){
            return false;
        }
    }
    static int lsGetGuildAudit(lcGlobalHelper global, Guild guild){
        try{
            int result=0;
            lcJSONGuildProfile entrie=global.getGuildSettings(guild, global.keyAudit);
            if(!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(global.keyAudit)&&!entrie.jsonObject.isNull(global.keyAudit)){
                    result= entrie.jsonObject.getInt(global.keyAudit);
                }
            }
            return result;
        }
        catch(Exception e){
            return 0;
        }
    }
    static List<Guild> getGuildsAsList(JDA jda) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildsAsList]";
        try{
            List<Guild> list=jda.getGuilds();
            return list;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static Map<String, Guild> getGuildsAsMap(JDA jda) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildsAsMap]";
        try{
            Map<String, Guild> map=new TreeMap<>();
            List<Guild> guilds=getGuildsAsList(jda);
            for(Guild guild: guilds){
                try{
                  map.put(guild.getId(),guild);
                }
                catch(Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            return map;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new TreeMap<>();
        }
    }
    static List<Guild> getGuildsAsList( List<JDA> jdas) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildsAsList4JDAs]";
        try{
            List<Guild> list=new ArrayList<>();
            for(JDA jda: jdas){
                try{
                     list.addAll(getGuildsAsList(jda));
                }
                catch(Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            return list;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static Map<String, Guild> getGuildsAsMap(List<JDA> jdas) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildsAsMap4JDAs]";
        try{
            Map<String, Guild> map=new TreeMap<>();
            List<Guild> guilds=getGuildsAsList(jdas);
            for(Guild guild: guilds){
                try{
                    map.put(guild.getId(),guild);
                }
                catch(Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            return map;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new TreeMap<>();
        }
    }
    static Guild getGuild( List<JDA> jdas,long id) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuild4JDAs]";
        try{
            for(JDA jda: jdas){
                try{
                   Guild guild=jda.getGuildById(id);
                   if(guild!=null){
                       logger.error(fName + ".guild.id="+guild.getId());
                       return guild;
                   }
                }
                catch(Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            logger.error(fName + ".is null");
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Guild getGuild( List<JDA> jdas,String id) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuild4JDAs]";
        try{
            for(JDA jda: jdas){
                try{
                    Guild guild=jda.getGuildById(id);
                    if(guild!=null){
                        logger.error(fName + ".guild.id="+guild.getId());
                        return guild;
                    }
                }
                catch(Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
            }
            logger.error(fName + ".is null");
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Guild getGuild(   JDA jda,long id) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="getGuild4JDA]";
        try{
            Guild guild=jda.getGuildById(id);
            if(guild!=null){
                return  guild;
            }
            logger.error(fName + ".is null");
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Guild getGuild( JDA jda,String id) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuild4JDA]";
        try{
            Guild guild=jda.getGuildById(id);
            if(guild!=null){
                return  guild;
            }
            logger.error(fName + ".is null");
            return null;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildIconId(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildIconId]";
        try{
            return guild.getIconId();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildIconUrl(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildIconUrl]";
        try{
            return guild.getIconUrl();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildBannerId(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildBannerId]";
        try{
            return guild.getBannerId();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildBannerUrl(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildBannerUrl]";
        try{
            return guild.getBannerUrl();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildSplashId(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildSplashId]";
        try{
            return guild.getSplashId();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getGuildSplashUrl(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildSplashUrl]";
        try{
            return guild.getSplashUrl();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String getGuildName(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildName]";
        try{
            return guild.getName();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String getGuildIdAsString(Guild guild) {
        Logger logger = Logger.getLogger(lsGuildHelper.class);
        String fName="[getGuildIdAsString]";
        try{
            return guild.getId();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}