package nsfw.chastity.chastikey;

import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iChastiKey {
    public interface  clientAuth{
        String keyClientId="ClientID",keyClientSecret="ClientSecret";
        //String clietId= llGlobalHelper.ChastiKeyAuth.clietId, clientKey=llGlobalHelper.ChastiKeyAuth.clientKey;
    }

    String urlCombinations="https://api.chastikey.com/v0.5/combinations.php";
    String urlKeyholderdata="https://api.chastikey.com/v0.5/keyholderdata.php";
    String urlLockeedata="https://api.chastikey.com/v0.5/lockeedata.php";
    String urlLogdata="https://api.chastikey.com/v0.5/logdata.php";
    String urlChecklock="https://api.chastikey.com/v0.5/checklock.php";
    public interface  parameters{
        String keyDiscordID="DiscordID",keyUsername="Username";
        String keyIncludeTestLocks="IncludeTestLocks";
    }

    String keyLockGroupID="lockGroupID", keyLockID="lockID", keyLockedBy="lockedBy", keyLockName="lockName", keyBuild="build", keyCombination="combination", keyTest="test", keyTimestampUnlocked="timestampUnlocked";

    String keyData="data";
    String keyUserID="userID", keyUsername="username", keyDiscordID="discordID", keyDisplayInStats="displayInStats", keyAverageRating="averageRating", keyBuildNumberInstalled="buildNumberInstalled", keyDateFirstKeyheld="dateFirstKeyheld",
            keyFollowersCount="followersCount", keyFollowingCount="followingCount", keyJoined="joined", keyKeyholderLevel="keyholderLevel", keyMainRole="mainRole", keyNoOfLocksFlaggedAsTrusted="noOfLocksFlaggedAsTrusted",
            keyNoOfLocksManagingNow="noOfLocksManagingNow", keyNoOfLocksManagingNowFixed="noOfLocksManagingNowFixed", keyNoOfLocksManagingNowVariable="noOfLocksManagingNowVariable", keyNoOfRatings="noOfRatings",
            keyNoOfSharedLocks="noOfSharedLocks", keyNoOfSharedLocksFixed="noOfSharedLocksFixed", keyNoOfSharedLocksVariable="noOfSharedLocksVariable", keyStatus="status",
            keyTimestampFirstKeyheld="timestampFirstKeyheld", keyTimestampJoined="timestampJoined", keyTimestampLastActive="timestampLastActive", keyTotalLocksManaged="totalLocksManaged", keyVersionInstalled="versionInstalled";

    String keySharedLockID="sharedLockID", keySharedLockQRCode="sharedLockQRCode", keySharedLockURL="sharedLockURL", keyAutoResetFrequencyInSeconds="autoResetFrequencyInSeconds",
            keyBlockUsersAlreadyLocked="blockUsersAlreadyLocked", keyCardInfoHidden="cardInfoHidden", keyCumulative="cumulative", keyFixed="fixed", keyForceTrust="forceTrust",
            keyKeyDisabled="keyDisabled", keyLockees="lockees", keyMaxAutoResets="maxAutoResets", keyMaxDoubleUps="maxDoubleUps", keyMaxFreezes="maxFreezes", keyMaxGreens="maxGreens",
            keyMaxMinutes="maxMinutes", keyMaxReds="maxReds", keyMaxResets="maxResets", keyMaxUsers="maxUsers", keyMaxYellows="maxYellows", keyMaxYellowsAdd="maxYellowsAdd",
            keyMaxYellowsMinus="maxYellowsMinus", keyMinDoubleUps="minDoubleUps", keyMinFreezes="minFreezes", keyMinGreens="minGreens", keyMinMinutes="minMinutes", keyMinReds="minReds",
            keyMinResets="minResets", keyMinVersionRequired="minVersionRequired", keyMinYellows="minYellows", keyMinYellowsAdd="minYellowsAdd", keyMinYellowsMinus="minYellowsMinus",
            keyMultipleGreensRequired="multipleGreensRequired", keyRegularity="regularity", keyRequireDM="requireDM", keyImulationAverageMinutesLocked="simulationAverageMinutesLocked",
            keySimulationBestCaseMinutesLocked="simulationBestCaseMinutesLocked", keySimulationWorstCaseMinutesLocked="simulationWorstCaseMinutesLocked", keyTimerHidden="timerHidden";

    String keyAverageTimeLockedInSeconds="averageTimeLockedInSeconds", keyCumulativeSecondsLocked="cumulativeSecondsLocked", keyLockeeLevel="lockeeLevel",
            keyLongestCompletedLockInSeconds="longestCompletedLockInSeconds", keySecondsLockedInCurrentLock="secondsLockedInCurrentLock", keyTotalNoOfCompletedLocks="totalNoOfCompletedLocks",
            keyTotalNoOfLocks="totalNoOfLocks";

    String keyAutoResetsPaused="autoResetsPaused", keyBotChosen="botChosen", keyDeleted="deleted", keyDiscardPile="discardPile", keyDoubleUpCards="doubleUpCards",
            keyFreezeCards="freezeCards",keyGreenCards="greenCards", keyGreenCardsPicked="greenCardsPicked", keyLockFrozen="lockFrozen", keyLockFrozenByCard="lockFrozenByCard", keyLockFrozenByKeyholder="lockFrozenByKeyholder",
            keyRedCards="redCards",keyLogID="logID", keyMaximumAutoResets="maximumAutoResets", keyNoOfTimesAutoReset="noOfTimesAutoReset", keyNoOfTimesCardReset="noOfTimesCardReset", keyNoOfTimesFullReset="noOfTimesFullReset",
            keyNoOfTurns="noOfTurns", keyResetCards="resetCards", keyStickyCards="stickyCards", keyTimestampDeleted="timestampDeleted", keyTimestampExpectedUnlock="timestampExpectedUnlock",
            keyTimestampFrozenByCard="timestampFrozenByCard", keyTimestampFrozenByKeyholder="timestampFrozenByKeyholder", keyTimestampLastAutoReset="timestampLastAutoReset",
            keyTimestampLastCardReset="timestampLastCardReset", keyTimestampLastFullReset="timestampLastFullReset", keyTimestampLastPicked="timestampLastPicked",
            keyTimestampLocked="timestampLocked", keyTimestampNextPick="timestampNextPick", keyTotalTimeFrozen="totalTimeFrozen", keyTrustKeyholder="trustKeyholder", keyYellowCards="yellowCards";

    public interface Config{
        public interface Keys {
            String config="ChastiKey",
                    client="client";
            public interface Client {
                String id="id",
                        key="key";
            }
        }
        static JSONObject getClient(lcGlobalHelper global){
            String fName="[getClient]";
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
                logger.info(fName+" jsonProperties="+jsonProperties.toString());
                String key="";
                key= Keys.client;if(jsonProperties.has(key)&&!jsonProperties.isNull(key)){
                    JSONObject jsonObject=jsonProperties.optJSONObject(key);
                    return  jsonObject;
                }
                logger.warn(fName+" json("+ key+") not found");
                return null;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static String getClientId(lcGlobalHelper global){
            String fName="[getClientId]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonCliennt=getClient(global);
                if(jsonCliennt==null){
                    logger.warn(fName+"jsonCliennt is null");
                    return "";
                }
                if(jsonCliennt.isEmpty()){
                    logger.warn(fName+"jsonCliennt is empty");
                    return "";
                }
                String key="";
                key= Keys.Client.id;if(jsonCliennt.has(key)&&!jsonCliennt.isNull(key)){
                    String value=jsonCliennt.optString(key);
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
        static String getClientKey(lcGlobalHelper global){
            String fName="[getClientKey]";
            Logger logger = Logger.getLogger(Config.class);
            try {
                JSONObject jsonCliennt=getClient(global);
                if(jsonCliennt==null){
                    logger.warn(fName+"jsonCliennt is null");
                    return "";
                }
                if(jsonCliennt.isEmpty()){
                    logger.warn(fName+"jsonCliennt is empty");
                    return "";
                }
                String key="";
                key= Keys.Client.key;if(jsonCliennt.has(key)&&!jsonCliennt.isNull(key)){
                    String value=jsonCliennt.optString(key);
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
