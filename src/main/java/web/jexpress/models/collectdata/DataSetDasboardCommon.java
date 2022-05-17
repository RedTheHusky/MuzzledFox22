package web.jexpress.models.collectdata;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class DataSetDasboardCommon {
    Logger logger = Logger.getLogger(getClass());
    public DataSetDasboardCommon(){

    }
    public void set(CollectData collectDat) {
        String fName="[set]";
        try {
            logger.info(fName+"clear");
            sessionAuthId ="";
            sessionCode ="";
            sessionToken ="";
            sessionAccessToken ="";
            sessionRefresToken ="";
            logger.info(fName+"start");
            try {
                sessionAuthId =collectDat.getString(iAuth.Option.auth);
                sessionCode =collectDat.getString(iAuth.Keys.code);
                sessionRefresToken =collectDat.getString(iAuth.Keys.refreshToken);
                sessionAccessToken =collectDat.getString(iAuth.Keys.accessToken);
                sessionToken =collectDat.getString(iAuth.Keys.token);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public String sessionCode ="", sessionRefresToken ="", sessionAccessToken ="", sessionToken ="", sessionAuthId ="";

}
