package userprofiles.entities;

import models.lc.json.profile.lcJSONUserProfile;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

public interface rSlaveRegistry {
    String  table="MemberProfile";
    String keyCharacters="characters";
    int restrictionNormal=5, restrictionBoosted=10;
    static  int intDefaultMinutes=15;
    default lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
        String fName = "[iSafetyUserProfileEntry]"; Logger logger = Logger.getLogger(getClass());
        logger.warn(fName);
        gUserProfile.safetyPutFieldEntry(keyGender,"");
        gUserProfile.safetyPutFieldEntry(keyDesc,"");
        gUserProfile.safetyPutFieldEntry(keyImg,"");
        gUserProfile.safetyPutFieldEntry(keyIsCommonlyKnownAs,"");
        gUserProfile.safetyPutFieldEntry(keyCurrentStatus,"");
        gUserProfile.safetyPutFieldEntry(keyServiceType,"");
        gUserProfile.safetyPutFieldEntry(keyOwner,"");
        return gUserProfile;
    }
    String keyGender="gender", keyImg="img",keyDesc="desc", keyCurrentStatus="status",keyServiceType="servicetype", keyOwner="owner", keyOwnerIsMentioned="ownerIsMentioned", keyIsCommonlyKnownAs="nickname";
    static String iGenerateSlaveNumber(User user) {
        String fName = "[iGenerateSlaveNumber]";Logger logger = Logger.getLogger(rSlaveRegistry.class);
        String strID=user.getId();
        Long longID=user.getIdLong();
        String a=strID.substring(0,3), c=strID.substring(strID.length()-3),b=strID.substring(5,8);
        logger.info(fName+"a="+a+", b="+b+", c="+c);
        return a+" "+b+" "+c;
    }

    // 14 for Gender
    String valueMale="";
    String valueFemale="";
    String valueCrossdresser="";
    String valueTransvestite="";
    String valueTransMale2Female="";
    String valueTransFemale2Male="";
    String valueTransgender="";
    String valueGenderFluid="";
    String valueGenderqueer="";
    String valueIntersex="";
    String valueButch="";
    String valueFemme="";
    String valueNonbinary="";
    String valueNotApplicable="";
    //8 for Current Status
    String valueFree_Unowned="Free/Unowned";
    String valueOwnedSlave="Owned Slave";
    String valuePet="Pet";
    String valueSubmissive="Submissive";
    String valueCollaredSubmissive="Collared Submissive";
    String valueInServiceSubmissive="In Service Submissive";
    String valueSubmissiveInTraining="Submissive In Training";
    String valueSlaveInTraining="Slave In Training";
    //4 for Type of service
    String valueInServiceTo="In Service To";
    String valueOwnedBy="Owned By";
    String valueCollaredTo="Collared To";
    String valueBeingTrainedBy="Being Trained By";
}
