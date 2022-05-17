package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum SUITTYPE {
    INVALID(-1,""),
    None(0,"none"),
    Puppy(1,"puppy"),
    Kitty(2,"kitty"),
    Pony(3,"pony"),
    Drone(4,"drone"),
    Cow(5,"cow"),
    Catsuit(6,"catsuit"),
    Reindeer(7,"reindeer"),
    Plush(8,"plush"),
    ToyReindeeer(9,"toyreindeer",0),
    ToyAlpha(10,"toyalpha",20),
    ToyBeta(11,"toybeta",5),
    ToyOmega(12,"toyOmega",2 ),
    Bitchsuit(13,"bitchsuit" ,true),
    Hogsack(14,"hogsack" ,true),
    Sleepsack(15,"sleepsack" ,true),
    Sleeper(16,"sleeper");
    private String name,display, description;
    private int code,maxtalk;
    boolean isToy,isBDSM;
    private SUITTYPE(int code, String name) {
        this.code = code;
        this.name = name;
        maxtalk=0;isToy=false;
        this.display="";
        this.description="";
    }
    private SUITTYPE(int code, String name, int talk) {
        this.code = code;
        this.name = name;
        maxtalk=talk;isToy=true;
    }
    private SUITTYPE(int code, String name, boolean isBDSM) {
        this.code = code;
        this.name = name;
        isBDSM=true;
    }
    public static SUITTYPE valueByCode(int code) {
        SUITTYPE[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SUITTYPE status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static SUITTYPE valueByName(String name) {
        SUITTYPE[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SUITTYPE status = var1[var3];
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }
    public String getName() {
        return this.name;
    }
    public boolean isToy() {
        return this.isToy;
    }
    public boolean isBDSM() {
        return this.isBDSM;
    }
    public int getMaxTalk() {
        return this.maxtalk;
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
        SUITTYPE[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            SUITTYPE s = var0[var2];
        }

    }
    public static String getName(SUITTYPE gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(SUITTYPE.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
