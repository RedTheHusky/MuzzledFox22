package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum HOODLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Gwen(1,"gwen"),
    OpenFace(2,"openface"),
    Bondage(3,"bondage"),
    Puppy(4,"puppy"),
    Kitty(5,"kitty"),
    Drone(6,"drone"),
    Cow(7,"cow"),
    Pony(8,"pony");
    private String name,display, description;
    private int code;
    private HOODLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private HOODLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private HOODLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static HOODLEVELS valueByCode(int code) {
        HOODLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HOODLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static HOODLEVELS valueByName(String name) {
        HOODLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HOODLEVELS status = var1[var3];
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
        HOODLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            HOODLEVELS s = var0[var2];
        }

    }
    public static String getName(HOODLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(HOODLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
