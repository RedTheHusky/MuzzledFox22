package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum ENCASELEVELS {
    Invalid(-1,""),
    None(0,"none"),
    MUMMY(1,"mummy"),
    GIBBET(2,"gibbet"),
    RUBBER(3,"rubber"),
    GLASS(4,"glass"),
    CAGE(5,"cage"),
    POLE(6,"pole"),
    VACBED(7,"vacbed"),
    CEMENT(8,"cement"),
    GLUE(9,"glue");
    private String name,display, description;
    private int code;
    private ENCASELEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private ENCASELEVELS(int code, String name, String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private ENCASELEVELS(int code, String name, String display, String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static ENCASELEVELS valueByCode(int code) {
        ENCASELEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ENCASELEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static ENCASELEVELS valueByName(String name) {
        ENCASELEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ENCASELEVELS status = var1[var3];
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
        ENCASELEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ENCASELEVELS s = var0[var2];
        }

    }
    public static String getName(ENCASELEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(ENCASELEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
