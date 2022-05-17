package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum CONFINELEVELS {
    INVALID(-1,""),
    None(0,"none"),
    CELL(1,"cell"),
    PADDED(2,"padded"),
    SACK(3,"sack"),
    CIRCLE(4,"circle"),
    PIT(5,"pit");
    private String name,display, description;
    private int code;
    private CONFINELEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private CONFINELEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private CONFINELEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static CONFINELEVELS valueByCode(int code) {
        CONFINELEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CONFINELEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static CONFINELEVELS valueByName(String name) {
        CONFINELEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CONFINELEVELS status = var1[var3];
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
        CONFINELEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            CONFINELEVELS s = var0[var2];
        }

    }
    public static String getName(CONFINELEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(CONFINELEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
