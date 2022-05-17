package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum GAGTYPES {
    INVALID(-1,""),
    Ball(0,"ball"),
    WireFrameMuzzle(1,"wire"),
    LeatherMuzzle(2,"leather"),
    Dildo(3,"dildo"),
    DReverseildo(4,"reverse-dildo"),
    Paci(5,"paci"),
    Tape(6,"tape"),
    Sock(7,"sock"),
    Underwear(8,"underwear"),
    Ring(9,"ring"),
    ReindeerMuzzle (10,"reindeer");

    private String name,display, description;
    private int code;
    private GAGTYPES(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private GAGTYPES(int code, String name,String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private GAGTYPES(int code, String name,String display,String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static GAGTYPES valueByCode(int code) {
        GAGTYPES[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GAGTYPES status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static GAGTYPES valueByName(String name) {
        GAGTYPES[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GAGTYPES status = var1[var3];
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
        GAGTYPES[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            GAGTYPES s = var0[var2];
        }

    }
    public static String getName(GAGTYPES gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(GAGTYPES.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
