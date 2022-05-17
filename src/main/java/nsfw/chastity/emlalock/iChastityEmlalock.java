package nsfw.chastity.emlalock;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.json.lcJSONandSqlUserProfile;
import models.lcGlobalHelper;
import nsfw.lovense.iLovense;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;

public interface iChastityEmlalock {

    String iReturnedValue4ApiIsInvalid="The returned value from emlalock api is invalid.";
    String profileName="emalock",table="emlalock_v2";//llMemberProfile;
    int intDefaultMinutes=10;
    String keyUser="user",fieldApiKey="api",fieldUserId="userId";
    String keyTimerAdd="timerAdd",keyTimerSub="timerSub",fieldMax="max",fieldMin="min",fieldGeneral="general",fieldEnable="enable";
    String keyVoting="voting",fieldTimeAdd="timeAdd",fieldTimeSub="timeSub",fieldTimeWait="timeWait",fieldAllowRandom="allowRandom",fieldAllowAdd="allowAdd",fieldAllowSub="allowSub", fieldLogs="logs",fieldTimeStamp="timeStamp";
    String keyRDCollar="rdCollar",keyRDChastity="rdChastity",keyRDTimeout="rdTimeout",keyPunish="punish",keyReward="reward",fieldDuration="duration";
    static lcJSONandSqlUserProfile sUserInit(lcJSONandSqlUserProfile gUserProfile){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iChastityEmlalock.class);
        try {
            String field=keyUser;
            gUserProfile.safetyCreateFieldEntry(keyUser);
            gUserProfile.safetyPutFieldEntry(field,fieldApiKey,"");
            gUserProfile.safetyPutFieldEntry(field,fieldUserId,"");
            /*field=keyTimerAdd;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,fieldEnable,false);
            gUserProfile.safetyPutFieldEntry(field,fieldMax,0L);
            gUserProfile.safetyPutFieldEntry(field,fieldMin,0L);
            gUserProfile.safetyPutFieldEntry(field,fieldGeneral,0L);
            field=keyTimerSub;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,fieldEnable,false);
            gUserProfile.safetyPutFieldEntry(field,fieldMax,0L);
            gUserProfile.safetyPutFieldEntry(field,fieldMin,0L);
            gUserProfile.safetyPutFieldEntry(field,fieldGeneral,0L);*/
            field=keyVoting;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,fieldEnable,false);
            gUserProfile.safetyPutFieldEntry(field,fieldTimeAdd,milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,fieldTimeSub,milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,fieldTimeWait,milliseconds_day);
            gUserProfile.safetyPutFieldEntry(field,fieldAllowRandom,false);
            gUserProfile.safetyPutFieldEntry(field,fieldAllowSub,true);
            gUserProfile.safetyPutFieldEntry(field,fieldAllowAdd,true);
            gUserProfile.safetyPutFieldEntry(field,fieldLogs,new JSONObject());
            field=keyRDChastity;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,fieldEnable,false);
            JSONObject jsonObjectPunish=new JSONObject();
            jsonObjectPunish.put(fieldEnable,true);
            jsonObjectPunish.put(fieldDuration, milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,keyPunish,jsonObjectPunish);
            JSONObject jsonObjectReward=new JSONObject();
            jsonObjectReward.put(fieldEnable,false);
            jsonObjectReward.put(fieldDuration, milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,keyReward,jsonObjectReward);
            field=keyRDTimeout;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,fieldEnable,false);
            gUserProfile.safetyPutFieldEntry(field,fieldDuration, milliseconds_hour);
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    String sqlKeyEmUserId="emuserid",sqlKeyApi="emapikey",sqlKeyUserID="user_id";
    String keyerror="error",keyjson="json";
    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;
    long defaultWaitTIme=milliseconds_hour*24,defaultAddTime=milliseconds_hour,defaultSubTime=milliseconds_hour, defaultMinTime=milliseconds_minute*15;
    String strInputMessageGoBack="\nEnter `!cancel` to abort.\nEnter `!back` to go back.";
    String strInputDurationFormate4ApiMenu ="\nThe time values format ex `w5d4h7m34`.  Syntax: w=week, d=day, h=hour, m=minute";

    String apiOptions_Add                  ="add",
    apiOptions_AddMaximum           ="addmaximum",
    apiOptions_AddMaximumRandom     ="addmaximumrandom",
    apiOptions_AddMinimum           ="addminimum",
    apiOptions_AddMinimumRandom     ="addminimumrandom",
    apiOptions_AddRandom            ="addrandom",
    apiOptions_AddRequirement       ="addrequirement",
    apiOptions_AddRequirementRandom ="addrequirementrandom",
    apiOptions_Info                 ="info",
    apiOptions_Sub                  ="sub",
    apiOptions_SubMaximum           ="submaximum",
    apiOptions_SubMaximumRandom     ="submaximumrandom",
    apiOptions_SubMinimum           ="subminimum",
    apiOptions_SubMinimumRandom     ="subminimumrandom",
    apiOptions_SubRandom            ="subrandom",
    apiOptions_SubRequirement       ="subrequirement",
    apiOptions_SubRequirementRandom ="subrequirementrandom";
    String commandPunish="punish",commandReward="reward",commandTimeout="timeout";
    static boolean isInChastity(lcJSONUserProfile gUserProfile){
        String fName="[isInChastity]";
        Logger logger = Logger.getLogger(iChastityEmlalock.class);
        try {
            boolean isChastity=false;
            isChastity=gUserProfile.jsonObject.getJSONObject(iRestraints.nChastity).getBoolean(iRestraints.nOn);
            logger.info("isChastity="+isChastity);
            return isChastity;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String strWhatIs="What is Emlalock?",strWhatIsDetails="The Emlalock is an online keyholding service.",strWhatIsDetails2="The Emlalock is an online keyholding service.";
    String strSiteDiscord="Emlalock Site&Discord",strSiteDiscordMore="For more information about Emlalock visit its [website](https://www.emlalock.com/#/) or [discord](https://discord.gg/xGWZNtv).";
    String strDisclaim="Disclaimer",strDisclaimMore="The bot is no way associated with Emlalock, it's only using publicly available [Api](https://apidoc.emlalock.com).\nThe bot is not responsible for any harm caused by misuse.";
    String strTitleNote="Note",
            strNoteRDChastity="In order for this to take effect, the user needs to wear the fictional collar in the bondage(rd) commands and have the fictional shock collar enabled.",
            strNoteRDTimeout="In order for this to take effect, the user needs to have their timeout active in the bondage(rd) commands.";

    String gURLUserProfile="https://api.emlalock.com/profile/?userid=!id",
        gUrlUserPicture ="https://api.emlalock.com/profilepicture?userid=!id",
        gUrlSessionProfile="https://api.emlalock.com/userchastitysession?userid=!id",
        gUrlSessionPicture="https://api.emlalock.com/chastitysessionpicture?type=2&chastitysessionid=!id&history=0",
        gUrlProfileFile="https://api.emlalock.com/profile/!id",
        gUrlSessionFile="https://api.emlalock.com/session/!id";

    String keySessionID="sessionID";
    String keyUserName="username", keyUserId="userid";
    String keywearerrating="wearerrating", keyholderrating="holderrating",keysessions="sessions",keyfailedsessions="failedsessions",keymaxsession="maxsession",keyminsession="minsession",keysumsession="sumsession",keygender="gender",keychastityrole="chastityrole";

    String keychastitysessionid="chastitysessionid",keycreatorid="creatorid",keywearerid="wearerid",keyholderid="holderid",keystatus="status",keysessiontype="sessiontype";
    String keyfriendlink="friendlink",keyreqlink="reqlink",keyrequirements="requirements",keyfriendlinkadd="friendlinkadd",keyfriendlinksub="friendlinksub",keyreqlinkadd="reqlinkadd",keyreqlinksub="reqlinksub",keyfriendlinkid="friendlinkid",keyreqlinkid="reqlinkid";
    String keydurationtype="durationtype",keyminduration="minduration",keymaxduration="maxduration",keystartduration="startduration",keyduration="duration";
    String keyinterval="interval",keylastverification="lastverification";
    String keyCode="code",keyFile="file";
    String keystartdate="startdate",keyenddate="enddate";
    String gUrlPage2Profile="https://www.emlalock.com/#/profile/!id";
    String keyhygieneopening="hygieneopening",keyincleaning="incleaning", keycleaningaction="cleaningaction",keycleaningpenalty="cleaningpenalty",keycleaningperiod="cleaningperiod",keycleanings="cleanings",keycleaningsperday="cleaningsperday",keycleaningstarted="cleaningstarted",keytimeforcleaning="timeforcleaning";

    String gUrlApiInfo="https://api.emlalock.com/info/?userid=!USERID&apikey=!APIKEY";
    String gUrlApiAdd="https://api.emlalock.com/add?userid=!USERID&apikey=!APIKEY&value=!VALUE",
        gUrlApiAddMaximum="https://api.emlalock.com/addmaximum?userid=!USERID&apikey=!APIKEY&value=!VALUE",
        gUrlApiAddMaximumRandom="https://api.emlalock.com/addmaximumrandom?userid=!USERID&apikey=!APIKEY&from=!FROM&to=!TO",
        gUrlApiAddMinimum="https://api.emlalock.com/addminimum?userid=!USERID&apikey=!APIKEY&value=!VALUE",
        gUrlApiAddMinimumRandom="https://api.emlalock.com/addminimumrandom?userid=!USERID&apikey=!APIKEY&from=!FROM&to=!TO",
        gUrlApiAddRandom="https://api.emlalock.com/addrandom?userid=!USERID&apikey=!APIKEY&from=!FROM&to=!TO",
        gUrlApiAddRequirement="https://api.emlalock.com/addrequirement?userid=!USERID&apikey=!APIKEY&value=!VALUE",
        gUrlApiAddRequirementRandom="https://api.emlalock.com/addrequirementrandom?userid=!USERID&apikey=!APIKEY&from=!FROM&to=!TO";
    String gUrlApiSub="https://api.emlalock.com/sub?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKEY&value=!VALUE",
        gUrlApiSubMaximum="https://api.emlalock.com/submaximum?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&value=!VALUE",
        gUrlApiSubMaximumRandom="https://api.emlalock.com/submaximumrandom?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&from=!FROM&to=!TO",
        gUrlApiSubMinimum="https://api.emlalock.com/subminimum?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&value=!VALUE",
        gUrlApiSubMinimumRandom="https://api.emlalock.com/subminimumrandom?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&from=!FROM&to=!TO",
        gUrlApiSubRandom="https://api.emlalock.com/subrandom?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKEY&from=!FROM&to=!TO",
        gUrlApiSubRequirement="https://api.emlalock.com/subrequirement?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&value=!VALUE",
        gUrlApiSubRequirementRandom="https://api.emlalock.com/subrequirementrandom?userid=!USERID&apikey=!APIKEY&holderapikey=!HOLDERAPIKE&from=!FROM&to=!TO";

    String strTitleRDStatus="RD status";
    String strChastityOnShockOn ="\nchastity: wearing & shocke enable",
        strChastityOnShockOff ="\nchastity: wearing & shocke disabled",
        strChastityOff="\nchastity: not wearing",
        strChastityRdCommand="\n`!>rchastity` to change";
    String strCollarOnShockOn ="\ncollar: wearing & shocke enable",
            strCollarOnShockOff ="\nchastity: wearing & shocke disabled",
            strCollarOff="\nchastity: not wearing",
            strCollarRdCommand="\n`!>rchastity` to change",
            strCollarNoBadwords="\n        bad words list is empty",
            strCollarNoEnforcedWords="\n        enforced words list is empty";
    String gFilesPath="resources/json/nsfw/emlalock/";
    String gValueFilePath=gFilesPath+"values.json";
    String gMainFilePath=gFilesPath+"mainMenu.json";
    String gVotingFilePath=gFilesPath+"votingMenu.json";
    String gcallFilePath=gFilesPath+"callMenu.json";

    public enum Genders {
        NotApplicable(0,"Not Applicable"),
        Male(1,"Male"),
        Female(2,"Female"),
        Crossdresser(3,"Cross-dresser"),
        TransgenderMale2Female(4,"Transgender: Male to Female"),
        TransgenderFemale2Male(5,"Transgender: Female to Male"),
        Transgender(6,"Transgender"),
        Genderfluid(7,"Genderfluid"),
        Genderqueer(8,"Genderqueer"),
        Intersex(9,"Intersex");
        private String name="";
        private int code=-1;
        private Genders(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static Genders valueByCode(int code) {
            Genders[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Genders status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public static Genders valueByName(String name) {
            Genders[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Genders status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            Genders[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Genders s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum Roles {
        None(0,"None"),
        Wearer(1,"Wearer"),
        Holder(2,"Holder"),
        Switch(3,"Switch");
        private String name="";
        private int code=-1;
        private Roles(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static Roles valueByCode(int code) {
            Roles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Roles status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return None;
        }
        public static Roles valueByName(String name) {
            Roles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Roles status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return None;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            Roles[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Roles s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum SessionTypes {
        None(0,"None"),
        CombinationPicture(1,"Combination Picture"),
        Verification(2,"Verification");
        private String name="";
        private int code=-1;
        private SessionTypes(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static SessionTypes valueByCode(int code) {
            SessionTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SessionTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return None;
        }
        public static SessionTypes valueByName(String name) {
            SessionTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SessionTypes status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return None;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            SessionTypes[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                SessionTypes s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum DurationTypes {
        None(-1,"None"),
        SpecificDuration(0,"Specific Duration"),
        a1(1,"n/a"),
        a2(2,"n/a"),
        SpecificDate(3,"Specific Date"),
        a4(4,"n/a"),
        Chance(5,"Chance");
        private String name="";
        private int code=-1;
        private DurationTypes(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static DurationTypes valueByCode(int code) {
            DurationTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                DurationTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return None;
        }
        public static DurationTypes valueByName(String name) {
            DurationTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                DurationTypes status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return None;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            DurationTypes[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                DurationTypes s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum CleaningPeriodType {
        None(0,"None"),
        Daily(1,"Daily"),
        Weekly(2,"Weekly"),
        Monthly(3,"Monthly"),
        PerSession(4,"Per session");
        private String name="";
        private int code=-1;
        private CleaningPeriodType(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static CleaningPeriodType valueByCode(int code) {
            CleaningPeriodType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CleaningPeriodType status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return None;
        }
        public static CleaningPeriodType valueByName(String name) {
            CleaningPeriodType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CleaningPeriodType status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return None;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            CleaningPeriodType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                CleaningPeriodType s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }

    public interface Config{
        public interface Keys {
            String config="Emlalock",
                    sessionID="session_id";
        }
        static String getSessionID(lcGlobalHelper global){
            String fName="[getSessionID]";
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
                key= Keys.sessionID;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
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
