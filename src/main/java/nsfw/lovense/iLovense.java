package nsfw.lovense;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Emoji;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iLovense {
    String profileName="lovense",table="MemberProfile";
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
            strLocked ="Can't manipulate your blindfold as !LOCKER has the keys!",
            strAccessSet2Pet ="Can't manipulate your blindfold as only youyr owner and sec-owners can do that.",
            strAccessSet2Public ="Can't manipulate your blindfold as your access set to public. While public everyone else can access it except you.",
            strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
            strCantDueLockedTarget="Can't manipulate their blindfold as it is locked by !LOCKER",
            strCantDueAccess="Can't manipulate their blindfold due to their access setting.";
    //https://www.lovense.com/sextoys/developer/doc#standard-api
    String gUrlLovesenseCommand="https://api.lovense.com/api/lan/v2/command";
    String gUrlLovesenseGetQrCode="https://api.lovense.com/api/lan/getQrCode";

    //{"uid":"55","appVersion":"4.1.0","toys":{"de68826ef711":{"nickName":"","name":"hush","id":"de68826ef711","status":1}},"wssPort":"34568","httpPort":"34567","wsPort":"34567","appType":"remote","domain":"192-168-100-126.lovense.club","utoken":"55test","httpsPort":"34568","version":"101","platform":"android"}

    String keyLovense="lovense",keyTimeStamp="timestamp",keyRegistered="registered",keyUpdated="updated";
    String keyEnable="enable",keyPublic="public";
    String keyuid="uid",keyappVersion="appVersion",
            keywssPort="wssPort",keyhttpPort="httpPort",keywsPort="wsPort",keyappType="appType",keydomain="domain",keyhttpsPort="httpsPort",keyversion="version",keyplatform="platform",
            keytoys="toys",keyutoken="utoken";
    String keyToyNickName="nickName",keyToyName="name",keyToyId="id",keyToyStatus="status";
    String commandRemove = "remove";

    static lcJSONUserProfile sUserInit(lcJSONUserProfile gUserProfile){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iLovense.class);
        try {gUserProfile.safetyCreateFieldEntry(entityLovensense.keyLoc);
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyLoc,keytoys,new JSONObject());
            gUserProfile.safetyCreateFieldEntry(entityLovensense.keyNet);
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyTimeStamp,0L);
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyuid,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyappVersion,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyappType,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keywssPort,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keywsPort,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyhttpPort,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyhttpsPort,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keydomain,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyversion,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keyutoken,"");
            gUserProfile.safetyPutFieldEntry(entityLovensense.keyNet,keytoys,new JSONObject());
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    String commandToy="toy",commandFunction ="function", commandFunctions ="functions",commandPreset="preset",commandStop="stop",commandqr="qr",commandMenu="menu",commandDisable="disable",commandEnable="enable",commandPublic="public",commandPrivate="private";
    long lovenseTimeout=60000;//300000;
    int intDefaultMinutes=10, defaultreqTimeSec=5;

    String keyOptionToyIndex="optionToyIndex", keyOptionCommand="optionCommand", keyOptionTarget="optionTarget";
    String keyOptionAction="optionAction", keyOptionActionLevel="optionActionLevel",keyOptionName="optionName", keyOptionTimeSec="optionTimeSec", keyOptionLoopRunningSec="optionLoopRunningSec", keyOptionLoopPauseSec="optionLoopPauseSec";
    int intColorRed=14423100,intColorGreen=2263842;
    String strDisclaim="Disclaimer",strDisclaimMore="The bot is no way associated with [Lovense](https://www.lovense.com), it's only using available [Api](https://www.lovense.com/sextoys/developer).\nThe bot is not responsible for any harm caused by misuse of the Lovense devices.";

    public enum ToyType {
        Invalid(0,"invalid"),
        Ambi(10,"Ambi",true),
        Ferri(20,"Ferri",true),
        Lush3(33,"Lush 3",true),Lush2(32,"Lush 2",true),Lush1(31,"Lush 1",true),Lush(30,"Lush",true),
        Nora(40,"Nora",true,true),
        Osci2(52,"Osci 2",true),Osci1(51,"Osci 1",true),Osci(50,"Osci",true),
        Domi2(62,"Domi 2",true),Domi1(61,"Domi 1",true),Domi(60,"Domi",true),
        Hush(70,"Hush",true),
        Diamo(80,"Diamo",true),
        Edge2(92,"Edge 2",true),Edge1(91,"Edge 1",true),Edge(90,"Edge",true),
        Max2(102,"Max 2",true,false,true),Max1(101,"Max 1",true,false,true),Max(100,"Max",true,false,true);


        private String name="",display="", description="";
        boolean vibrate=false,rotate=false,pump=false;
        private int code=-1;
        private ToyType(int code, String name) {
            this.code = code;
            this.name = name;

        }
        private ToyType(int code, String name, boolean vibrate) {
            this.code = code;
            this.name = name;
            this.vibrate=vibrate;
        }
        private ToyType(int code, String name, boolean vibrate, boolean rotate) {
            this.code = code;
            this.name = name;
            this.vibrate=vibrate;
            this.rotate=rotate;
        }
        private ToyType(int code, String name, boolean vibrate, boolean rotate, boolean pump) {
            this.code = code;
            this.name = name;
            this.vibrate=vibrate;
            this.rotate=rotate;
            this.pump=pump;
        }
        public static ToyType valueByCode(int code) {
            ToyType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ToyType status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static ToyType valueByName(String name) {
            ToyType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ToyType status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
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
        public boolean isVibrate() {
            return this.vibrate;
        }
        public boolean isRotate() {
            return this.rotate;
        }
        public boolean isPump() {
            return this.pump;
        }
        static {
            ToyType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                ToyType s = var0[var2];
            }

        }
        public static String getName(ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum Actions4Function {
        Stop(0,"Stop"),
        Vibrate(1,"Vibrate"),
        Rotate(2,"Rotate"),
        Pump(3,"Pump");

        private String name,display, description;
        private int code;
        private Actions4Function(int code, String name) {
            this.code = code;
            this.name = name;
            this.display="";
            this.description="";
        }
        private Actions4Function(int code, String name, String display) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description="";
        }
        private Actions4Function(int code, String name, String display, String description) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description=description;
        }
        public static Actions4Function valueByCode(int code) {
            Actions4Function[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Actions4Function status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static Actions4Function valueByName(String name) {
            Actions4Function[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Actions4Function status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
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
            Actions4Function[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Actions4Function s = var0[var2];
            }

        }
        public static String getName(Actions4Function gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(Actions4Function.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum Actions4Preset {
        Stop(0,"stop"),
        Pulse(1,"pulse"),
        Wave(2,"wave"),
        Fireworks(3,"fireworks"),
        Earthquake(4,"earthquake");

        private String name,display, description;
        private int code;
        private Actions4Preset(int code, String name) {
            this.code = code;
            this.name = name;
            this.display="";
            this.description="";
        }
        private Actions4Preset(int code, String name, String display) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description="";
        }
        private Actions4Preset(int code, String name, String display, String description) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description=description;
        }
        public static Actions4Preset valueByCode(int code) {
            Actions4Preset[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Actions4Preset status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static Actions4Preset valueByName(String name) {
            Actions4Preset[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Actions4Preset status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
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
            Actions4Preset[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Actions4Preset s = var0[var2];
            }

        }
        public static String getName(Actions4Preset gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(Actions4Preset.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum Commands {
        None(0,""),
        Function(1,"Function"),
        Pattern(2,"Pattern"),
        Preset(3,"Preset");

        private String name,display, description;
        private int code;
        private Commands(int code, String name) {
            this.code = code;
            this.name = name;
            this.display="";
            this.description="";
        }
        private Commands(int code, String name, String display) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description="";
        }
        private Commands(int code, String name, String display, String description) {
            this.code = code;
            this.name = name;
            this.display=display;
            this.description=description;
        }
        public static Commands valueByCode(int code) {
            Commands[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Commands status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static Commands valueByName(String name) {
            Commands[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Commands status = var1[var3];
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
            Commands[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Commands s = var0[var2];
            }

        }
        public static String getName(Commands gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(Commands.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum Emojis {
        Ambi(859268648702312449L,"Lovense_Ambi"),
        Diamo(859271330239479818L,"Lovense_Diamo"),
        Domi2(859271330143010816L,"Lovense_Domi2"),
        Domi2Animated(859281690290946089L,"Lovense_Domi2",true),
        Edge2(859271329978515496L,"Lovense_Edge2"),
        Edge2Animated(859291451292778516L,"Lovense_Edge2",true),
        Ferri(859271330401484830L,"Lovense_Ferri"),
        Hush(859271329998962699L,"Lovense_Hush"),
        Lush3(859271330111946753L,"Lovense_Lush3"),
        Max2(859271329727119362L,"Lovense_Max2"),
        MissonAnimated(859293691617476648L,"Lovense_Misson",true),
        Misson(859271329794621468L,"Lovense_Misson"),
        Nora(859271329929101350L,"Lovense_Nora"),
        NoraAnimated(859284706654027799L,"Lovense_Nora",true),
        Osci(859271330377367562L,"Lovense_Osci"),
        Quake(877091989160611840L,"Lovense_Quake"),
        Toys(859278832208707586L,"Lovense_Toys",true),
        Connect(859324131103604736L,"Lovense_connect"),
        Remote(859324131182247957L,"Lovense_remote");

        private String name="";
        private long id;
        private Emoji emoji=null;
        private  boolean animated=false;
        private Emojis(long id, String name) {
            if(name!=null)this.name = name;
            this.id=id;
            emoji=Emoji.fromEmote(this.name,this.id,false);
        }
        private Emojis(long id, String name,boolean animated) {
            if(name!=null)this.name = name;
            this.id=id;
            this.animated=animated;
            emoji=Emoji.fromEmote(this.name,this.id,animated);
        }
        public static Emojis valueById(int id) {
            Emojis[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Emojis status = var1[var3];
                if (status.id ==id) {
                    return status;
                }
            }
            return null;
        }
        public static Emojis valueByName(String name) {
            Emojis[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Emojis status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getName() {
            return this.name;
        }
        public long getId() {
            return this.id;
        }
        public boolean isAnimated() {
            return this.animated;
        }
        public Emoji getPartialEmoji() {
            return this.emoji;
        }

    }
    String gFilesPath="resources/json/nsfw/lovense/";
    String gMainFilePath=gFilesPath+"mainMenu.json";
    String gToyFilePath=gFilesPath+"toyMenu.json";

    public interface Config{
        public interface Keys {
            String config="Lovense",
                    callBackURL="callBackURL",
                    token="token";
        }
        static String getCallBackURL(lcGlobalHelper global){
            String fName="[getCallBackURL]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.callBackURL;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
                    String value=jsonProperties.optString(key);
                    logger.info(fName+"value="+value);
                    return value;
                }
                logger.warn(fName+" json("+ key+") not found");
                return "";
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        static String getToken(lcGlobalHelper global){
            String fName="[getToken]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonProperties=global.configfile.getJsonObjectAsJsonObject(Keys.config);
                if(jsonProperties==null){
                    logger.warn(fName+"jsonProperties is null");
                    return null;
                }
                if(jsonProperties.isEmpty()){
                    logger.warn(fName+"jsonProperties is empty");
                    return null;
                }
                String key="";
                key= Keys.token;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
                    String value=jsonProperties.optString(key);
                    logger.info(fName+"value="+value);
                    return value;
                }
                logger.warn(fName+" json("+ key+") not found");
                return "";
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
    }
}
