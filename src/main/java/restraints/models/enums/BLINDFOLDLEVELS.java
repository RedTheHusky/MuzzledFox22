package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum BLINDFOLDLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Paneled(1,"paneled"),
    Contacts(2,"contacts");
    private String name,display, description;
    private int code;
    private BLINDFOLDLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private BLINDFOLDLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private BLINDFOLDLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static BLINDFOLDLEVELS valueByCode(int code) {
        BLINDFOLDLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BLINDFOLDLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static BLINDFOLDLEVELS valueByName(String name) {
        BLINDFOLDLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BLINDFOLDLEVELS status = var1[var3];
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
        BLINDFOLDLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            BLINDFOLDLEVELS s = var0[var2];
        }

    }
    public static String getName(BLINDFOLDLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(BLINDFOLDLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
