package models.ll;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class lcImageFormat {
    protected Logger logger =null;
    protected String url="";
    protected String urlUpdated="";
    private  boolean png=false,jpeg=false,webp=false,gif=false,lottie=false;
    public  lcImageFormat(){
        logger = Logger.getLogger(lcImageFormat.class);

    }
    protected boolean set (String url,boolean png,boolean jpeg,boolean webp,boolean gif,boolean lottie){
        String fName="[set]";
        try{
            this.url=url;
            this.png=png;this.jpeg=jpeg;this.webp=webp;this.gif=gif;this.lottie=lottie;
            return true;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected boolean updateUrl (String url){
        String fName="[set]";
        try{
            this.urlUpdated=url;
            return true;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public static class GuildMemberAvatar extends lcImageFormat {
        public GuildMemberAvatar (String guild,String user, String avatar){
            super();
            String fName="[build]";
            logger = Logger.getLogger(lcImageFormat.class);
            try{
                set("guilds/!GUILD/users/!USER/avatars/!AVATAR.png",true,true,true,true,false);
                updateUrl(url.replaceAll("!GUILD",guild).replaceAll("!USER",user).replaceAll("!AVATAR",avatar));
            }
            catch(Exception e){
                logger.error(".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
    }
    public String getUrl(){
        String fName="[getUrl]";
        try{
            return urlUpdated;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getOriginalUrl(){
        String fName="[getOriginalUrl]";
        try{
            return url;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
}
