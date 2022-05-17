package models.ls;

import org.apache.log4j.Logger;

public interface lsNumbersUsefullFunctions {


    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;
    static Long milliseconds2seconds(long time) {
        String fName = "[milliseconds2seconds]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "input="+time);
            long result = time / 1000;
            logger.info(fName + "result="+result);
            return result;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0L;
        }
    }
    static Long milliseconds2minutes(long time) {
        String fName = "[milliseconds2minutes]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "input="+time);
            long result = (time / 1000) / 60;
            logger.info(fName + "result="+result);
            return result;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0L;
        }
    }
    static Long milliseconds2hours(long time) {
        String fName = "[milliseconds2hours]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "input="+time);
            long result = (time / 1000) / (60*60);
            logger.info(fName + "result="+result);
            return result;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0L;
        }
    }

    static int correctNumberIfNotBellow(int source,  int max, int def) {
        String fName = "[correctNumberIfNotBellow]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "source="+source+", max="+max+", def="+def);
            if(source>max)return def;
            return source;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0;
        }
    }
    static int correctNumberIfNotAbove(int source, int min,  int def) {
        String fName = "[correctNumberIfNotAbove]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "source="+source+" min="+min+", def="+def);
            if(source<min)return def;
            return source;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0;
        }
    }
    static int correctNumberIfNotBetween(int source, int min, int max, int def) {
        String fName = "[correctNumberIfNotBetween]";
        Logger logger = Logger.getLogger(lsNumbersUsefullFunctions.class);
        try {
            logger.info(fName + "source="+source+" min="+min+", max="+max+", def="+def);
            if(source<min)return def;
            if(source>max)return def;
            return source;
        } catch (Exception ex) {
            logger.error(fName + "exception=" + ex);
            return 0;
        }
    }
}
