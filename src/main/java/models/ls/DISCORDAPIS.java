package models.ls;

import kong.unirest.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.Arrays;

public enum DISCORDAPIS {
    INVALID(-1,"",0,"",new JSONArray("[Authorization]")),
    GetGuild(0,"GUILD",1,"https://discord.com/api/v8/guilds/:guildid",new JSONArray("[Authorization]"));

    private String name="",url="",display="", description="";
    private int code,type;
    private JSONArray headers=new JSONArray();
    private DISCORDAPIS(int code, String name,int type,String url,JSONArray headers) {
        this.code = code;
        this.name = name;
        this.type=type;
        this.url=url;
        this.headers=headers;
    }

    public static DISCORDAPIS valueByCode(int code) {
        DISCORDAPIS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DISCORDAPIS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static DISCORDAPIS valueByName(String name) {
        DISCORDAPIS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DISCORDAPIS status = var1[var3];
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }
    public String getName() {
        return this.name;
    }
    public String getUrl() {
        return this.url;
    }
    public DISCORDAPI getDISCORDAPI(){
        String fName="[getDISCORDAPI]";
        Logger logger = Logger.getLogger(DISCORDAPIS.class);
        try {
            return new DISCORDAPI(this.code, this.name,this.type,this.url,this.headers);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getCode() {
        return this.code;
    }
    static {
        DISCORDAPIS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            DISCORDAPIS s = var0[var2];
            /*if (s.name().charAt(0) != '_') {
                throw new IllegalStateException("GAGLEVEL code '" + s + "' need to start with underscore.");
            }*/
        }

    }
    public static String getName(DISCORDAPIS discordapis){
        String fName="[getName]";
        Logger logger = Logger.getLogger(DISCORDAPIS.class);
        try {
            return discordapis.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }


}
