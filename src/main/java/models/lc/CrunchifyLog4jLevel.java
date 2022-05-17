package models.lc;

import org.apache.log4j.Level;

public class CrunchifyLog4jLevel extends Level {
    /**
     * Value of CrunchifyLog4jLevel level. This value is lesser than DEBUG_INT and higher
     * than TRACE_INT}
     */
    public static final int CRUNCHIFY_INT = DEBUG_INT - 10;
    public static final int GAGWEBHOOK_INT = INFO_INT + 20;
    public static final String PREFIXUSE_STR = "PREFIXUSE";
    public static final String GAGUSED_STR = "GAGUSED";

    public static final String PiShockUseSuccess_STR = "PISHOCKUSESUCCESS";
    public static final String PiShockUseFail_STR = "PISHOCKUSEFAIL";

    public static final String EmlalockUseSuccess_STR = "EMLALOCKUSESUCCESS";
    public static final String EmlalockUseFail_STR = "EMLALOCKUSEFAIL";
    public static final String EmlalockUpdateSuccess_STR = "EMLALOCKUPDATESUCCESS";

    public static final String LovenseReq_STR = "LOVENSEREQ";
    public static final String LovenseSuccess_STR = "LOVENSESUCCESS";
    public static final String LovenseFail_STR = "LOVENSEFAIL";
    /**
     * Level representing my log level
     */
    public static final Level CRUNCHIFY = new CrunchifyLog4jLevel(CRUNCHIFY_INT, "CRUNCHIFY", 10);
    public static final Level GAGWEBHOOK = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, "GAGWEBHOOK", 20);
    public static final Level GAGUSED = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, GAGUSED_STR, 20);
    public static final Level PREFIXUSE = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, PREFIXUSE_STR, 20);

    public static final Level PISHOCKUSEDSUCCESS = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, PiShockUseSuccess_STR, 20);
    public static final Level PISHOCKUSEDFAIL = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, PiShockUseFail_STR, 20);

    public static final Level EMLALOCKUSESUCCESS = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, EmlalockUseSuccess_STR, 20);
    public static final Level EMLALOCKUPDATESUCCESS = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, EmlalockUpdateSuccess_STR, 20);
    public static final Level EMLALOCKUSEFAIL = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, EmlalockUseFail_STR, 20);

    public static final Level LOVENSESUCCESS = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, LovenseSuccess_STR, 20);
    public static final Level LOVENSEFAIL = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, LovenseFail_STR, 20);
    public static final Level LOVENSEREQ = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, LovenseReq_STR, 20);

    public static final Level SENTSANTALETTER = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, "SENT_SANTA_LETTER", 20);
    public static final Level SENTNSANTALETTER = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, "SENTN_SANTA_LETTER", 20);

    public  static  final  String GuildJoin_STR="GuildJoin";
    public  static  final  String GuildLeave_STR="GuildLeave";
    public  static  final  String GuildUnavailable_STR="GuildUnavailable";
    public static final Level  GuildJoin = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, GuildJoin_STR, 20);
    public static final Level  GuildLeave = new CrunchifyLog4jLevel(GAGWEBHOOK_INT,  GuildLeave_STR, 20);
    public static final Level  GuildUnavailable = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, GuildUnavailable_STR, 20);

    public  static  final  String GuildNewsPost_STR="GuildNewsPost";
    public static final Level  GuildNewsPost = new CrunchifyLog4jLevel(GAGWEBHOOK_INT, GuildNewsPost_STR, 20);
    /**
     * Constructor
     */
    protected CrunchifyLog4jLevel(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);

    }

    /**
     * Checks whether logArgument is "CRUNCHIFY" level. If yes then returns
     * CRUNCHIFY}, else calls CrunchifyLog4jLevel#toLevel(String, Level) passing
     * it Level#DEBUG as the defaultLevel.
     */
    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("CRUNCHIFY")) {
            return CRUNCHIFY;
        }
        return (Level) toLevel(logArgument, Level.DEBUG);
    }

    /**
     * Checks whether val is CrunchifyLog4jLevel#CRUNCHIFY_INT. If yes then
     * returns CrunchifyLog4jLevel#CRUNCHIFY, else calls
     * CrunchifyLog4jLevel#toLevel(int, Level) passing it Level#DEBUG as the
     * defaultLevel
     *
     */
    public static Level toLevel(int val) {
        if (val == CRUNCHIFY_INT) {
            return CRUNCHIFY;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    /**
     * Checks whether val is CrunchifyLog4jLevel#CRUNCHIFY_INT. If yes
     * then returns CrunchifyLog4jLevel#CRUNCHIFY, else calls Level#toLevel(int, org.apache.log4j.Level)
     *
     */
    public static Level toLevel(int val, Level defaultLevel) {
        if (val == CRUNCHIFY_INT) {
            return CRUNCHIFY;
        }
        return Level.toLevel(val, defaultLevel);
    }

    /**
     * Checks whether logArgument is "CRUNCHIFY" level. If yes then returns
     * CrunchifyLog4jLevel#CRUNCHIFY, else calls
     * Level#toLevel(java.lang.String, org.apache.log4j.Level)
     *
     */
    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("CRUNCHIFY")) {
            return CRUNCHIFY;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
