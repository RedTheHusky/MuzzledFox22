package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum BREATHPLAYLEVELS {
    Invalid(-1,""),
    None(0,"none"),
    TAPE(1,"tape"),
    ROPE(2,"rope"),
    BAG(3,"bag"),
    HOOD(4,"hood"),
    HANGING(5,"hanging");
    private String name,display, description;
    private int code;
    private BREATHPLAYLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private BREATHPLAYLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private BREATHPLAYLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static BREATHPLAYLEVELS valueByCode(int code) {
        BREATHPLAYLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BREATHPLAYLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static BREATHPLAYLEVELS valueByName(String name) {
        BREATHPLAYLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BREATHPLAYLEVELS status = var1[var3];
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
        BREATHPLAYLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            BREATHPLAYLEVELS s = var0[var2];
        }

    }
    public static String getName(BREATHPLAYLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(BREATHPLAYLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
