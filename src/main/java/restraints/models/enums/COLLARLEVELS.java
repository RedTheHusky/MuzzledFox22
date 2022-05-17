package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum COLLARLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Leather(1,"leather"),
    Rubber(2,"rubber"),
    Latex(3,"latex"),
    Chain(4,"chain"),
    Iron(5,"iron");
    private String name,display, description;
    private int code;
    private COLLARLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private COLLARLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private COLLARLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static COLLARLEVELS valueByCode(int code) {
        COLLARLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            COLLARLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static COLLARLEVELS valueByName(String name) {
        COLLARLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            COLLARLEVELS status = var1[var3];
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }
    public String getName() {
        return this.name;
    }
    public String getDisplay() {
        return this.display;
    }
    public String getDescription() {
        return this.description;
    }
    public int getCode() {
        return this.code;
    }
    static {
        COLLARLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            COLLARLEVELS s = var0[var2];
        }

    }
    public static String getName(COLLARLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(COLLARLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
