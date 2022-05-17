package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum MITTSLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    General(1,"general"),
    Puppy(2,"puppy"),
    Pony(3,"pony"),
    Cow(4,"cow"),
    Ducky(5,"ducky");
    private String name,display, description;
    private int code;
    private MITTSLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private MITTSLEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private MITTSLEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static MITTSLEVELS valueByCode(int code) {
        MITTSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MITTSLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static MITTSLEVELS valueByName(String name) {
        MITTSLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MITTSLEVELS status = var1[var3];
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
        MITTSLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            MITTSLEVELS s = var0[var2];
        }

    }
    public static String getName(MITTSLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(MITTSLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
