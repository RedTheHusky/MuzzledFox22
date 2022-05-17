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

public class lcEmotes {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcEmotes]";
    private Guild gGuild;
    private List<lcEmote>lcEmotes=new ArrayList<>();
    public lcEmotes(){

    }
    public lcEmotes(Guild g){
        String fName="[constructor]";
        try {
            gGuild=g;setEmotes();
            logger.info(fName+".half ready");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private Boolean setEmotes(){
        String fName="[setEmotes]";
        try {
            logger.info(fName+".gGuild="+gGuild.getId());
            List<Emote>emotes=gGuild.getEmotes();
            for(Emote emote:emotes){
                lcEmotes.add(new lcEmote(emote));
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcEmote createEmote(String name,String  url){
        return createEmote(name, url, null);
    }
    public lcEmote createEmote(String name, String  url, List<Role> roles){
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
    public lcEmote createEmote(String name,InputStream  is){
        return createEmote(name, is, null);
    }
    public lcEmote createEmote(String name, InputStream  is, List<Role> roles){
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
    public lcEmote createEmote(String name,Icon  icon){
        return createEmote(name, icon, null);
    }
    public lcEmote createEmote(String name, Icon  icon, List<Role> roles){
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
                lcEmote lcemote=new lcEmote(emote);
                lcEmotes.add(lcemote);
                return lcemote;
            }
            logger.warn(fName+".fail");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmotes refresh() {
        String fName = "[refresh]";
        try {
            setEmotes();
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            boolean result=lcEmotes.isEmpty();
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<Emote> getEmotes() {
        String fName = "[getEmotes]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            List<Emote>emotes=new ArrayList<>();
            for(lcEmote emote:lcEmotes){
                emotes.add(emote.getEmote());
            }
            return emotes;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Emote getEmote(long id) {
        String fName = "[getEmote]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            List<Emote>emotes=getEmotes();
            for(Emote emote:emotes){
                if(emote.getIdLong()==id){
                    logger.info(fName+"found");
                    return emote;
                }
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getEmotesIndex(long id) {
        String fName = "[getEmotesIndex]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return -1;
            }
            List<Emote>emotes=getEmotes();
            for(int i=0;i<emotes.size();i++){
                Emote emote=emotes.get(i);
                if(emote.getIdLong()==id){
                    logger.info(fName+"found at="+i);
                    return i;
                }
            }
            logger.warn(fName+"not found");
            return -1;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public Emote getEmote(int index) {
        String fName = "[getEmote]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            List<Emote>emotes=getEmotes();
            return emotes.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcEmote> getEmotesAslcEmote() {
        String fName = "[getEmotesAslcEmote]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return lcEmotes;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmote getEmoteAslcEmote(long id) {
        String fName = "[getEmoteAslcEmote]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            for(lcEmote emote:lcEmotes){
                if(emote.getIdLong()==id){
                    logger.info(fName+"found");
                    return emote;
                }
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmote getEmoteAslcEmote(int index) {
        String fName = "[getEmoteAslcEmote]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return lcEmotes.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private Boolean delete(lcEmote emote){
        String fName="[delete]";
        try {
            if(emote==null){ logger.warn(fName+".is null"); return false;}
            boolean result=emote.delete();
            logger.info(fName+".result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean delete(long id){
        String fName="[delete]";
        try {
            return delete(getEmoteAslcEmote(id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean delete(int index){
        String fName="[delete]";
        try {
            return delete(getEmoteAslcEmote(index));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
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



}
