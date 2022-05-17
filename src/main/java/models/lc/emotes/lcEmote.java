package models.lc.emotes;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Role;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class lcEmote {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcEmote]";
    private Guild gGuild;
    private Emote gEmote;
    public lcEmote(){
        String fName="[constructor]";
        logger.info(cName+fName+".not ready");
    }
    public lcEmote(Emote e){
        String fName="[constructor]";
        gGuild=e.getGuild();
        gEmote=e;
        logger.info(cName+fName+".ready");
    }
    public lcEmote(Guild g){
        String fName="[constructor]";
        gGuild=g;
        logger.info(cName+fName+".half ready");
    }
    public lcEmote(Guild g, long id){
        String fName="[constructor]";
        gGuild=g;
        logger.info(cName+fName+".half ready");
        getEmoteById(id);
    }
    public lcEmote(Guild g, String idorname){
        String fName="[constructor]";
        gGuild=g;
        logger.info(cName+fName+".half ready");
        getEmoteById(idorname);
        if(!isValid()){
            getEmoteByName(idorname);
        }
    }
    public lcEmote(Guild g, String name, boolean ignoreCase){
        String fName="[constructor]";
        gGuild=g;
        logger.info(cName+fName+".half ready");
        getEmoteByName(name,ignoreCase);
    }
    public Boolean isValid(){
        String fName="[isValid]";
        try {
            logger.info(cName+fName);
            if(gEmote!=null){
                logger.info(cName+fName+"true");
                return true;
            }
            logger.info(cName+fName+"false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Emote createEmote(String name,String  url){
        return createEmote(name, url, null);
    }
    public Emote createEmote(String name, String  url, List<Role> roles){
        String fName="[createEmote]";
        try {
            logger.info(fName+".name="+name);
            logger.info(fName+".url="+url);
            if(roles!=null)logger.info(fName+".roles="+roles.toString());
            Emote emote=null;URL url2 = new URL(url);
            HttpURLConnection httpcon = (HttpURLConnection) url2.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream is=httpcon.getInputStream();
            return  createEmote(name,is,roles);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Emote createEmote(String name,InputStream  is){
        return createEmote(name, is, null);
    }
    public Emote createEmote(String name, InputStream  is, List<Role> roles){
        String fName="[createEmote]";
        try {
            logger.info(fName+".name="+name);
            if(roles!=null)logger.info(fName+".roles="+roles.toString());
            Emote emote=null;
            Icon icon=Icon.from(is);
            return  createEmote(name,icon,roles);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Emote createEmote(String name,Icon  icon){
        return createEmote(name, icon, null);
    }
    public Emote createEmote(String name, Icon  icon, List<Role> roles){
        String fName="[createEmote]";
        try {
            logger.info(fName+".name="+name);

            if(roles!=null)logger.info(fName+".roles="+roles.toString());
            Emote emote=null;
            if(roles!=null&&roles.isEmpty()){
                emote=gGuild.createEmote(name,icon, (Role[]) roles.toArray()).complete();
            }else{
                emote=gGuild.createEmote(name,icon).complete();
            }
            if(emote!=null){
                logger.info(fName+".success");
                gEmote=emote;
                return gEmote;
            }
            logger.warn(fName+".fail");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Boolean getEmoteById(long id){
        String fName="[getEmoteById]";
        try {
            logger.info(cName+fName+".id="+id);gEmote=null;
            gEmote=gGuild.getEmoteById(id);
            if(gEmote!=null){
                logger.info(cName+fName+".emote:"+gEmote.getId()+"|"+gEmote.getName());
                return true;
            }
            logger.warn(cName+fName+".no such emote");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getEmoteById(String str){
        String fName="[getEmoteById]";
        try {
            logger.info(cName+fName+".str="+str);
            gEmote=gGuild.getEmoteById(str);
            if(gEmote!=null){
                logger.info(cName+fName+".emote:"+gEmote.getId()+"|"+gEmote.getName());
                return true;
            }
            logger.warn(cName+fName+".no such emote");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getEmoteByName(String str){
        String fName="[getEmoteByName]";
        try {
             logger.info(cName+fName+".str="+str);
             List<Emote>emotes=gGuild.getEmotesByName(str,false);gEmote=null;
             if(!emotes.isEmpty()){
                 gEmote=emotes.get(0);
                 logger.info(cName+fName+".emote:"+gEmote.getId()+"|"+gEmote.getName());
                 return true;
             }
            logger.warn(cName+fName+".no such emote");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean getEmoteByName(String str, Boolean ignoreCase){
        String fName="[getEmoteByName]";
        try {
            logger.info(cName+fName+".str="+str);
            logger.info(cName+fName+".ignoreCase="+ignoreCase);
            List<Emote>emotes=gGuild.getEmotesByName(str,ignoreCase);gEmote=null;
            if(!emotes.isEmpty()){
                gEmote=emotes.get(0);
                logger.info(cName+fName+".emote:"+gEmote.getId()+"|"+gEmote.getName());
                return true;
            }
            logger.warn(cName+fName+".no such emote");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getAsMention(){
        String fName="[getAsMention]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return null;}
            String result=gEmote.getAsMention();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getAsMention(String failed){
        String fName="[getAsMention]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return failed;}
            String result=gEmote.getAsMention();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return null;}
            String result=gEmote.getName();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Role> getRoles(){
        String fName="[getRoles]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return null;}
            boolean canProvideRoles=gEmote.canProvideRoles();
            logger.info(cName+fName+".canProvideRoles="+canProvideRoles);
            if(!canProvideRoles){
                logger.warn(cName+fName+".has no roles to provide"); return null;
            }
            List<Role> result=gEmote.getRoles();
            logger.info(cName+fName+".result="+result.toString());
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasRoles(){
        String fName="[hasRoles]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            boolean canProvideRoles=gEmote.canProvideRoles();
            logger.info(cName+fName+".canProvideRoles="+canProvideRoles);
            if(!canProvideRoles){
                logger.warn(cName+fName+".has no roles to provide"); return false;
            }
            List<Role> result=gEmote.getRoles();
            logger.info(cName+fName+".result="+result.toString());
            return !result.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getImageUrl(){
        String fName="[getImageUrl]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return null;}
            String result=gEmote.getImageUrl();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getId(){
        String fName="[getId]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return null;}
            String result=gEmote.getId();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getIdLong(){
        String fName="[getId]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return 0;}
            long result=gEmote.getIdLong();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Boolean setName(String name){
        String fName="[setName]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            if(name.isEmpty()){ logger.warn(cName+fName+".name is null"); return false;}
            gEmote.getManager().setName(name).complete();
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            logger.info(cName+fName+".guild="+guild.getName()+"("+guild.getId()+")");
            gGuild=guild;
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean setRole(List<Role>roles){
        String fName="[setRole]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            if(roles==null||roles.isEmpty()){
                gEmote.getManager().setRoles(null).complete();
            }else{
            /*Set<Role> sroles;
            sroles= (Set<Role>) roles;*/
                Set<Role> sroles = new HashSet<>();
                for (Role x : roles) {
                    sroles.add(x);
                }
                gEmote.getManager().setRoles(sroles).complete();
            }
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean delete(){
        String fName="[delete]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            gEmote.delete().complete();
            gEmote=null;
            logger.info(cName+fName+".success");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public Boolean isAnimated(){
        String fName="[isAnimated]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            Boolean result=gEmote.isAnimated();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean isManaged(){
        String fName="[isManaged]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            Boolean result=gEmote.isManaged();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Boolean isAvailable(){
        String fName="[isAvailable]";
        try {
            if(gEmote==null){ logger.warn(cName+fName+".is null"); return false;}
            Boolean result=gEmote.isAvailable();
            logger.info(cName+fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public Emote getEmote(){
        String fName="[getEmote]";
        try {
            return this.gEmote;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild(){
        String fName="[getGuild]";
        try {
            return this.gGuild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmotes getlcEmotes(){
        String fName="[getlcEmotes]";
        try {
            return new lcEmotes(this.gEmote.getGuild());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public class lcEmoteBuild {
        Logger logger = Logger.getLogger(getClass()); String cName="[lcEmoteBuild]";
        private Guild guild;
        private String name="";
        private List<Role>roles=new ArrayList<>();
        private Icon icon=null;

        public lcEmoteBuild(){
            String fName="[constructor]";
            logger.info(cName+fName+".not ready");
        }
        public String getName(){
            String fName="[getName]";
            try {
                String result=this.name;
                logger.info(cName+fName+".result="+result);
                return result;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<Role> getRoles(){
            String fName="[getRoles]";
            try {
                List<Role> result=this.roles;
                logger.info(cName+fName+".result="+result.toString());
                return result;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Icon getIcon(){
            String fName="[getIcon]";
            try {
                Icon result=this.icon;
                logger.info(cName+fName+".result="+result.toString());
                return result;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcEmoteBuild setIcon(Icon icon){
            String fName="[setIcon]";
            try {
                if(icon==null){
                    logger.warn(cName+fName+".this is null");
                    return  null;
                }
                this.icon=icon;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcEmoteBuild setName(String name){
            String fName="[setName]";
            try {
                if(name==null){
                    logger.warn(cName+fName+".this is null");
                    return  null;
                }
                if(name.isEmpty()){ logger.warn(cName+fName+".name is null"); return null;}
                this.name=name;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcEmoteBuild setGuild(Guild guild){
            String fName="[setGuild]";
            try {
                if(roles==null){
                    logger.warn(cName+fName+".this is null");
                    return  null;
                }
                logger.info(cName+fName+".guild="+guild.getName()+"("+guild.getId()+")");
                this.guild=guild;
                logger.info(cName+fName+".success");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcEmoteBuild setRole(List<Role>roles){
            String fName="[setRole]";
            try {
                if(roles==null){
                    logger.warn(cName+fName+".this is null");
                    return  null;
                }
                this.roles=roles;
                logger.info(cName+fName+".success");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcEmote create(){
            String fName="[create]";
            try {
                logger.info(fName+".name="+this.name);
                if(this.roles!=null)logger.info(fName+".roles="+this.roles.toString());
                Emote emote=null;
                if(this.roles!=null&&this.roles.isEmpty()){
                    emote=this.guild.createEmote(this.name,this.icon, (Role[]) this.roles.toArray()).complete();
                }else{
                    emote=this.guild.createEmote(this.name,this.icon).complete();
                }
                if(emote!=null){
                    logger.info(fName+".success");
                    return new lcEmote(emote);
                }
                logger.warn(fName+".fail");
                return null;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

    }
}
