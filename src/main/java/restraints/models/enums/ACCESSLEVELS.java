package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum ACCESSLEVELS {
    ExposedOld(-2,"exposed"),
    INVALID(-1,""),
    Exposed(0,"exposed2"),
    Public(1,"public"),
    Key(2,"key"),
    Ask(3,"ask"),
    AskPlus(4,"ask+"),
    Protected(5,"protected"),
    Pet (6,"pet");

    private String name,display, description;
    private int code;
    private ACCESSLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private ACCESSLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private ACCESSLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static ACCESSLEVELS valueByCode(int code) {
        ACCESSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ACCESSLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static ACCESSLEVELS valueByName(String name) {
        ACCESSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ACCESSLEVELS status = var1[var3];
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
        ACCESSLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ACCESSLEVELS s = var0[var2];
        }

    }
    public static String getName(ACCESSLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(ACCESSLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
