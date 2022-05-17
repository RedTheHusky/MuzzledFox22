package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum CUFFSARMSLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Armbinder(1,"armbinder"),
    Behind(2,"behind"),
    BehindTight(3,"behindtight"),
    BehindBelt (4,"behindbelt"),
    BehindTBelt(5,"behindtbelt"),
    Elbows(6,"elbows"),
    Front(7,"front"),
    FrontBelt(8,"frontbelt"),
    ReversePrayer(9,"reverseprayer"),
    Sides(10,"sides"),
    SidesTight(11,"sidestight"),
    ChristmasLights(12,"christmas_lights");
    private String name,display, description;
    private int code;
    private CUFFSARMSLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private CUFFSARMSLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private CUFFSARMSLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static CUFFSARMSLEVELS valueByCode(int code) {
        CUFFSARMSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CUFFSARMSLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static CUFFSARMSLEVELS valueByName(String name) {
        CUFFSARMSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CUFFSARMSLEVELS status = var1[var3];
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
        CUFFSARMSLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            CUFFSARMSLEVELS s = var0[var2];
        }

    }
    public static String getName(CUFFSARMSLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(CUFFSARMSLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
