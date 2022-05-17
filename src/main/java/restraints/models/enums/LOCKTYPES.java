package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum LOCKTYPES {
    INVALID(-1,""),
    DEFAULT(0,"default"),
    GLUE(1,"glue"),
    CURSE(2,"curse"),
    TAPE(3,"tape"),
    EXTRA(4,"extra"),
    STITCH(5,"stitch"),
    WARDEN(6,"warden");
    private String name,display, description;
    private int code;
    private LOCKTYPES(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private LOCKTYPES(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private LOCKTYPES(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static LOCKTYPES valueByCode(int code) {
        LOCKTYPES[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LOCKTYPES status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static LOCKTYPES valueByName(String name) {
        LOCKTYPES[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LOCKTYPES status = var1[var3];
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
        LOCKTYPES[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            LOCKTYPES s = var0[var2];
        }

    }
    public static String getName(LOCKTYPES gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(LOCKTYPES.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
