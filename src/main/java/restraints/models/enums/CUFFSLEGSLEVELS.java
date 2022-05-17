package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum CUFFSLEGSLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    FauxTaut(1,"fauxtaut"),
    Taut(2,"taut"),
    Stand(3,"stand"),
    LayBack(4,"layback"),
    LayBelly(5,"laybelly"),
    Sit(6,"sit"),
    Spreaderbar(7,"spreaderbar"),
    HogBack(8,"hogback"),
    HogSideways(9,"hogsideways"),
    HogTie(10,"hogtie"),
    ChristmasLights(10,"christmas_lights");
    private String name,display, description;
    private int code;
    private CUFFSLEGSLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private CUFFSLEGSLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private CUFFSLEGSLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static CUFFSLEGSLEVELS valueByCode(int code) {
        CUFFSLEGSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CUFFSLEGSLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static CUFFSLEGSLEVELS valueByName(String name) {
        CUFFSLEGSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CUFFSLEGSLEVELS status = var1[var3];
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
        CUFFSLEGSLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            CUFFSLEGSLEVELS s = var0[var2];
        }

    }
    public static String getName(CUFFSLEGSLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(CUFFSLEGSLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
