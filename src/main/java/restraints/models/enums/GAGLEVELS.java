package restraints.models.enums;

import org.apache.log4j.Logger;

import java.util.Arrays;

public enum GAGLEVELS {
    INVALID(-1,""),
    None(0,"none"),
    Faux(1,"faux"),
    Loose(2,"loose"),
    Severe(3,"severe"),
    Extreme(4,"extreme"),
    Mute(5,"mute"),
    Puppy(6,"puppy"),
    Kitty(7,"kitty"),
    Paci(8,"paci"),
    Pony(9,"pony"),
    Dinosaur(10,"dinosaur"),
    Squeaky(11,"squeaky"),
    Piggy(12,"piggy"),
    Bird(13,"bird"),
    Cow(14,"cow"),
    Sheep(15,"sheep"),
    NucleusMask(16,"nucleus"),
    DroneMask(17,"drone"),
    Toy (18,"toy"),
    Turkey(19,"turkey"),
    Corrupt(20,"corrupt"),
    DroneReindeer(21,"reindeer"),
    Training(22,"training"),
    ComputerNerd_Binary (23,"binary"),
    ComputerNerd_BinaryBlocks (24,"binaryblocks"),
    ComputerNerd_Base64 (25,"base64"),
    ComputerNerd_Hex (26,"hex"),
    Pokemon_Pikachu (27,"pikachu"),
    Pokemon_Eevee (28,"eevee"),
    Chocke (29,"choke");
    private String name,display, description;
    private int code;
    private GAGLEVELS(int code, String name) {
        this.code = code;
        this.name = name;
        this.display="";
        this.description="";
    }
    private GAGLEVELS(int code, String name,String display) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description="";
    }
    private GAGLEVELS(int code, String name,String display,String description) {
        this.code = code;
        this.name = name;
        this.display=display;
        this.description=description;
    }
    public static GAGLEVELS valueByCode(int code) {
        GAGLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GAGLEVELS status = var1[var3];
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    public static GAGLEVELS valueByName(String name) {
        GAGLEVELS[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GAGLEVELS status = var1[var3];
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
        GAGLEVELS[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            GAGLEVELS s = var0[var2];
        }

    }
    public static String getName(GAGLEVELS gag){
        String fName="[getName]";
        Logger logger = Logger.getLogger(GAGLEVELS.class);
        try {
            return gag.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

}
