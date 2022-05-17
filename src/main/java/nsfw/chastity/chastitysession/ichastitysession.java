package nsfw.chastity.chastitysession;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public interface ichastitysession   {

    String profileName="chastitysession";
    default lcJSONUserProfile safetyUserProfileEntry(lcJSONUserProfile gUserProfile){
        String fName="[safetyUserProfileEntry]";
        Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName+".safety check");
            String field;

            field=fieldProfile;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyProfileCountTotalSession ,0);
            gUserProfile.safetyPutFieldEntry(field,keyProfileCountSuccessSession  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyProfileCountFailedSession  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyProfileTotalTimeLocked  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyProfileLongestSession  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyProfileShortestSession  ,0);

            field=fieldSession;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keySessionActive  ,false);
            gUserProfile.safetyPutFieldEntry(field,keySessionVerificationRequired  ,false);
            gUserProfile.safetyPutFieldEntry(field,keySessionKeyHolder  ,"");
            gUserProfile.safetyPutFieldEntry(field,keySessionKeyHolderAccept  ,false);
            gUserProfile.safetyPutFieldEntry(field,keySessionStartDate  ,0);
            gUserProfile.safetyPutFieldEntry(field, keySessionLockboxImageFile,"");
            gUserProfile.safetyPutFieldEntry(field,keySessionLockboxImageExtension ,"");
            gUserProfile.safetyPutFieldEntry(field,keySessionVerificationInterval ,0);
            gUserProfile.safetyPutFieldEntry(field,keySessionVerificationLastDate ,0);
            gUserProfile.safetyPutFieldEntry(field,keySessionVerificationReqDate ,0);

            field=fieldVotingFriend;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendEnableAdd  ,false);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendEnableSub ,false);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendAddTime ,milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendSubTime ,milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendEnableRandom  ,false);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendLogVotings ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendLogAdd  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendLogSub ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendLogRand ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingFriendUsers ,new JSONObject());

            field=fieldVotingRequirement;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementCount  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementDone ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementAddTime ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementSubTime ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementLogVotings ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementLogAdd ,0);
            gUserProfile.safetyPutFieldEntry(field,keyVotingRequirementLogSub ,0);

            field=fieldDuration;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyDurationMode  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyDuration0TotalDuration, 0);
            gUserProfile.safetyPutFieldEntry(field,keyDuration0StartingDuration ,milliseconds_week);
            gUserProfile.safetyPutFieldEntry(field,keyDuration0MinimumDuration ,milliseconds_week);
            gUserProfile.safetyPutFieldEntry(field,keyDuration0MaximumDuration ,milliseconds_2weeks);

            field=fieldDisplayMode;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyDisplayModeTimePassed  ,1);
            gUserProfile.safetyPutFieldEntry(field,keyDisplayModeTimeLeft ,1);

            field=fieldHygieneOpening;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningActive  ,false);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningCount  ,1);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningUsed  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningPeriod  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningDuration  ,milliseconds_hour);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningPenalty  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningIsOpen  ,false);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningUnlockDate  ,0);
            gUserProfile.safetyPutFieldEntry(field,keyHygieneOpeningLockDate  ,0);

            field=fieldEnd;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyEndMode ,0);
            gUserProfile.safetyPutFieldEntry(field,keyEndWheelSlots ,"");
            gUserProfile.safetyPutFieldEntry(field,keyEndVotingRequired ,0);
            gUserProfile.safetyPutFieldEntry(field,keyEndVotingCount ,0);
            gUserProfile.safetyPutFieldEntry(field,keyEndVotingForUnlock ,0);
            gUserProfile.safetyPutFieldEntry(field,keyEndVotingForLock ,0);
            gUserProfile.safetyPutFieldEntry(field,keyEndVotingAddTime ,0);

            field=fieldPillory;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyPilloryActive ,false);
            gUserProfile.safetyPutFieldEntry(field,keyPilloryDuration ,0);
            gUserProfile.safetyPutFieldEntry(field,keyPilloryDate ,0);
            gUserProfile.safetyPutFieldEntry(field,keyPilloryAddTime ,0);
            return  gUserProfile;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  gUserProfile;
        }
       
    }
    default String randomCode(){
        String fName="[randomCode]";
        Logger logger = Logger.getLogger(fName);
        try{
        // length is bounded by 256 Character
        int n=6;
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, StandardCharsets.UTF_8);

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return "null";
        }
    }
    default Boolean IsStringAMember(Guild guild, String str){
        String fName = "[IsStringAMember]";
        Logger logger = Logger.getLogger(fName);
        logger.info(fName+"str="+str);
        try{
            Member u=guild.getMemberById(str);
            if(u!=null){logger.info(fName+".is not null");return true;}
            logger.info(fName+".is null");return false;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    default String displayDuration(Long time){
        String fName = "[displayDuration]";
        Logger logger = Logger.getLogger(fName);
        try{
            if(time==null){
                logger.info(fName+"time is null");
                return "null";
            }
            logger.info(fName+"time="+time);
            long week = time / milliseconds_week;
            long diffWeek = time % milliseconds_week;
            long day = diffWeek / milliseconds_day;
            long diffDay = diffWeek % milliseconds_day;
            long hour = diffDay / milliseconds_hour;
            long diffHour = diffDay % milliseconds_hour;
            long minutes = diffHour / milliseconds_minute;
            logger.info(fName+"week="+week);
            logger.info(fName+"diffWeek="+diffWeek);
            logger.info(fName+"day="+day);
            logger.info(fName+"time="+diffDay);
            logger.info(fName+"hour="+hour);
            logger.info(fName+"minutes="+minutes);
            String str="";
            if(week>1){
                str+=week+" weeks ";
            }else
            if(week==1){
                str+=week+" week ";
            }
            if(day>1){
                str+=day+" days ";
            }else
            if(day==1){
                str+=day+" day ";
            }
            if(hour>1){
                str+=hour+" hours ";
            }else
            if(hour==1){
                str+=hour+" hour ";
            }
            if(minutes>1){
                str+=minutes+" minutes ";
            }else
            if(minutes==1){
                str+=minutes+" minute ";
            }
            logger.info(fName+"str="+str);
            return str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "null";}
    }
    String fieldProfile="profile";
    String keyProfileTotalTimeLocked ="totaltimelocked";
    String keyProfileLongestSession ="longestsession";
    String keyProfileShortestSession ="shortestsession";
    String keyProfileCountTotalSession ="counttotalsession";
    String keyProfileCountSuccessSession ="countsuccesssession";
    String keyProfileCountFailedSession ="countfailedsession";

    String fieldSession="session";
    String keySessionActive ="active";
    String keySessionKeyHolder="keyholder";
    String keySessionKeyHolderAccept="keyholderaccept";
    String keySessionVerificationRequired ="verificationrequired";
    String keySessionLockboxImageFile ="lockboxImageFile",keySessionLockboxImageExtension="lockboxImageExt",keySessionLockboxImageName="lockboxImageName";
    String keySessionStartDate ="startdate";
    String keySessionVerificationInterval ="verificationinterval";
    String keySessionVerificationLastDate ="verificationlastdate";
    String keySessionVerificationFile ="verificationfile";
    String keySessionVerificationCode ="verificationcode";
    String keySessionVerificationReqDate ="verificationreqdate";
    String keySessionVerificationName="verificationname";
    String keySessionVerificationExtension="verificationextension";

    String fieldVotingFriend ="votingfriend";
    String keyVotingFriendEnableAdd ="enableadd";
    String keyVotingFriendEnableSub ="enablesub";
    String keyVotingFriendAddTime ="addtime";
    String keyVotingFriendSubTime ="subtime";
    String keyVotingFriendEnableRandom ="enablerandom";
    String keyVotingFriendLogVotings ="logvotings";
    String keyVotingFriendLogAdd ="logadd";
    String keyVotingFriendLogSub ="logsub";
    String keyVotingFriendLogRand ="logran";
    String keyVotingFriendUsers ="users";

    String fieldVotingRequirement ="votingrequirement";
    String keyVotingRequirementCount ="count";
    String keyVotingRequirementDone ="done";
    String keyVotingRequirementAddTime ="addtime";
    String keyVotingRequirementSubTime ="subtime";
    String keyVotingRequirementLogVotings ="logvotings";
    String keyVotingRequirementLogAdd ="logadd";
    String keyVotingRequirementLogSub ="logsub";

    String fieldDuration ="duration";
    String keyDurationMode ="mode"; //default, fixdate, chance
    String keyDuration0TotalDuration="remainingduratio";
    String keyDuration0StartingDuration="startingduratio";
    String keyDuration0MinimumDuration="minimumduration";
    String keyDuration0MaximumDuration="maximumduration";

    String fieldDisplayMode ="displaymode";
    String keyDisplayModeTimePassed ="passed";
    String keyDisplayModeTimeLeft ="left";

    String fieldHygieneOpening="hygieneopening";
    String keyHygieneOpeningActive ="active";
    String keyHygieneOpeningCount ="count";
    String keyHygieneOpeningUsed ="used";
    String keyHygieneOpeningPeriod ="period";
    String keyHygieneOpeningDuration ="duration";
    String keyHygieneOpeningPenalty ="penalty";
    String keyHygieneOpeningIsOpen ="isopen";
    String keyHygieneOpeningUnlockDate ="unlock";
    String keyHygieneOpeningLockDate ="lock";


    String fieldEnd="sessionend";
    String keyEndMode="mode"; //end, end+redo, end+wheel, wheel, voting
    String keyEndWheelSlots="wheel_slots";
    String keyEndVotingRequired="voting_required";
    String keyEndVotingCount="voting_count";
    String keyEndVotingForUnlock="voting_forunlock";
    String keyEndVotingForLock="voting_forlock";
    String keyEndVotingAddTime="voting_addtime";

    String fieldPillory="pillory";
    String keyPilloryActive="active";
    String keyPilloryDuration="duration";
    String keyPilloryDate="date";
    String keyPilloryAddTime="addtime";

    long milliseconds_2weeks=1209600000;
    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;

    String pathLSave4Lockbox ="storage/chastitysession/lockbox/userid";
    String pathLSave4Verification ="storage/chastitysession/verification/userid";


    String sRTitle="Chastity Session";



}
