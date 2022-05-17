package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum EARMUFFSLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Muffs(1,"muffs"),
    Plugs(2,"plugs");
    private String name,display, description;
    private int code;
    private EARMUFFSLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private EARMUFFSLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private EARMUFFSLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static EARMUFFSLEVELS valueByCode(int code) {
        EARMUFFSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EARMUFFSLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static EARMUFFSLEVELS valueByName(String name) {
        EARMUFFSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EARMUFFSLEVELS status = var1[var3];
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
        EARMUFFSLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            EARMUFFSLEVELS s = var0[var2];
        }

    }
    public static String getName(EARMUFFSLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(EARMUFFSLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
