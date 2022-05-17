package restraints.in;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iCuffs {
    static boolean isArmsCuffsOn(JSONObject arms){
        String fName="[isArmsCuffsOn]";
        Logger logger = Logger.getLogger(iCuffs.class);
        try {
            boolean result=arms.getBoolean(iRestraints.nOn);
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isArmsCuffsEqualLevel(JSONObject arms,String level){
        String fName="[isArmsCuffsEqualLevel]";
        Logger logger = Logger.getLogger(iCuffs.class);
        try {
            if(!arms.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(arms.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
                logger.info(fName + ".found>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isLegsCuffsOn(JSONObject legs){
        String fName="[isLegsCuffsOn]";
        Logger logger = Logger.getLogger(iCuffs.class);
        try {
            boolean result=legs.getBoolean(iRestraints.nOn);
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isLegsCuffsEqualLevel(JSONObject legs,String level){
        String fName="[isLegsCuffsEqualLevel]";
        Logger logger = Logger.getLogger(iCuffs.class);
        try {
            if(!legs.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(legs.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
                logger.info(fName + ".found>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String strCantOperateRestrainsDue2ArmCuffs="Do you really think you can manipulate your restraints while you have your arms tied?",
            strCantOperateRestrainsDue2ArmCuffs_PetDenied="Do you really think you can manipulate your restraints while you have your arms tied?\n~~Change your access to ask or protected.~~";
    String strArmsCantLocked="You cannot manipulate your arm cuffs as they are locked by !LOCKER",
    strArmsCantSet2Pet="You cannot manipulate your arm cuffs as only your owner and sec-owners can do that!",
     strArmsCantSet2Public="You cannot manipulate your arm cuffs whilst your access is set to public! That's for everyone else to worry about.",
     strArmsCantJacket="You can't put arm cuffs on at the same time as a straitjacket!",
     strArmsCantMitts1="You can't grab anything with mittens on your paws, Your mitted paws are too useless for that!",
     strArmsCantMitts2="!USER tries to grab the key to their cuffs but their mitted paws prevent it.",
     strArmsIsSet="Its already set to this!",
     strArmsCantDueLocked1="You can't untie your arms while they're locked!",
     strArmsCantDueLocked2="!USER tries to take off their cuffs, but they're tightly locked around their wrist.",
     strArmsUntie1="You've untied your arms.",
     strArmsUntie2="!USER managed to untie their arm restraints. Someone must have forgotten to secure them...",
     strArmsNotRestrained="Victim's arms are not restrained, silly.",
     strArmsCantDueKey1="Why do you think **you** would have the key to your own restraints?!",
     strArmsCantDueKey2="!USER tries to play with their arm cuffs but the padlock keeps them nice and secure -- Not like anyone gave them the key!",
    strArmsCantPermalocked="You cannot manipulate your arm cuffs as they are permanently locked.";

    String strArmArmbinder1="You've restrained your paws with an armbinder. How the hell did you do that?",
       strArmArmbinder2="!USER has restrained their paws with an armbinder.",
       strArmBehind1="You've restrained your paws behind your back. Must be difficult to use a phone!",
       strArmBehind2="!USER has restrained their paws behind their backs. Easy target for a gag!",
       strArmBehindT1="You've restrained your paws behind your back very tightly. Must be uncomfortable.",
       strArmBehindT2="!USER has restrained their paws behind their back very tightly in a very uncomfortable position.",
       strArmBehindBelt1="You've restrained your paws behind your back and onto your belt.",
       strArmBehindBelt2="!USER has restrained their paws behind their back and onto their belt.",
       strArmBehindBeltT1="You've restrained your paws behind your back very tightly. Must be very uncomfortable.",
       strArmBehindBeltT2="!USER restrained their paws behind your back very tightly and in a very uncomfortable position.",
       strArmElbow1="You've restrained your elbows.",
       strArmElbow2="!USER has restrained their elbows",
       strArmFront1="You've restrained your paws up front.",
       strArmFront2="!USER has restrained their paws up front.",
       strArmFrontBelt1="You've restrained your paws up front to the belt like a high security prisoner who needs restrained 24/7.",
       strArmFrontBelt2="!USER has restrained their paws up front like a high security prisoner who needs restrained 24/7",
       strArmPrayer1="You've restrained your paws in a prayer position, but not wanting to make it easy for themselves, in reverse!",
       strArmPrayer2="!USER restrained their paws in a prayer position, but not wanting to make it easy for themselves, in reverse!",
       strArmSidewaysBelt1="You've restrained your paws sideways, and attachged them to your belt.",
       strArmSidewaysBelt2="!USER has restrained their paws sideways, and attached them to their belt.",
       strArmSidewaysBeltT1="You've restrained your paws sideways, and attached them to your belt very tightly.",
       strArmSidewaysBeltT2="!USER restrained their paws sideways, and attached them to their belt very tightly.";

    String strLegCantDueLocked="You can't manipulate your leg cuffs, as !LOCKER has decided not to let you have the key.",
    strLegCantDue2Pet="You can't manipulate your legs cuffs as as only your owner and sec-owners can do this.",
    strLegCantDue2Public="You can't manipulate your legs cuffs as your access is set to public. Let someone else worry about how restrained you are -- It's not your problem!",
    strLegCantDueJacket1="How do you think you can attach leg cuffs while you are already stuck in that straitjacket?",
    strLegCantDueJacket2="!USER pulls hard on the sleeves of the straitjacket, but it wont bulge.",
    strLegCantDueArms1="How do you think you can attach leg cuffs while you already have your arms tied?",
    strLegCantDueArms2="!USER tries to take off their leg cuffs -- This would be much easier without their arms restrained!",
    strLegMitts1="You can't manage to grab anything with your mitted paws!",
    strLegMitts2="!USER tries to grab the keys to their leg cuffs but their mitted paws prevent them.",
    strLegAlreadySet="Its already set to this!",
    strLegCantLocked1="You can't untie your legs while that padlock is in the way -- if only you had the key...",
    strLegCantLocked2="!USER tries to take off their leg cuffs, but they are securely locked.",
    strLegUntie1="You've untied your legs.",
    strLegUntie2="!USER managed to untie their legs restraints. Someone must have forgotten to secure it.",
    strLegNotRestrained="The legs are not restrained, silly.",
    strLegNoKey1="You don't have the key to do that!",
    strLegNoKey2="!USER tries to play with their legs cuffs but fail as they don't have the key to the lock!";

    String strLegFauxTaut1="You've restrained your legs lightly, so you can walk but can't run.",
    strLegFauxTaut2="!USER has restrained their legs enough to walk but not to run.",
    strLegTaut1="You've restrained your legs taut, -- it's much harder to walk now.",
    strLegTaut2="!USER has restrained their legs taut, making it much harder to walk.",
    strLegStand1="You've restrained your legs enough to not able to walk, but hop like a cute little bunny rabbit.",
    strLegStand2= "!USER has restrained their legs so that they can't walk but can hop like a cute little bunny rabbit.",
    strLegLayBack1="You've restrained your legs so that it forces you to lay on your back.",
    strLegLayBack2="!USER has restrained their legs so that they are forced to stay on their back.",
    strLegLayBelly1="You've restrained your legs so that it forces you to lay on your belly.",
    strLegLayBelly2="!USER restrained their legs so that they forced to stay on their belly.",
    strLegSit1="You've restrained your legs in sitting position, hope you like sitting for hours... Not that you can stand up anyway.",
    strLegSit2= "!USER has restrained their legs such that they are forced to sit, hope they like sitting for hours... Not that they can stand up anyway.",
    strLegSpreadbar1="You've restrained your legs using a metal spreaderbar, giving a nice view between your legs.",
    strLegSpreadbar2="!USER has restrained their legs using a metal spreaderbar, exposing them selves perfectly to be used by everyone.",
    strLegHogback1="You've restrained your legs such that it forces you in a hogback.",
    strLegHogback2="!USER restrained their legs such that they forced into a hogback.",
    strLegHogside1="You've restrained your legs such that they force you into a hogside. Pick a side.",
    strLegHogside2="!USER has restrained their legs such that they forced into a hogside. Pick a side.",
    strLegHogtie1="You've restrained your legs such that it forces you in a hogtie, unable to stand up and walk away. ",
    strLegHogtie2="!USER has restrained their legs, forcing them into a hogtie, unable to stand up and walk away.";


    String strTargetArmCantPermalocked="You can't manipulate !TARGET's restraints as they have been permanently locked!",
    strTargetArmCantLocked="Victim can't manipulate their arms cuffs as they are locked by !LOCKER",
    strTargetArmCantDueAccess="Cannot manipulate victim's locks do to their access setting.",
    strTargetArmCantDueJacket="How do you think you will use the arm cuffs on !TARGET while they are wearing their straitjacket?",
    strTargetArmCantDUeAlreadySet="It's already set to this level!",
    strTargetArmCantDueLocked="You can't untie !TARGET's arms while they are locked with padlock.",
    strTargetArmCantDueNotRestrained="Those arms are not restrained... yet";

    String strTargetLegCantPermalocked="You can't manipulate !TARGET's restraints as they have been permanently locked!",
    strTargetLegCantLocked="Cannot manipulate their leg cuffs as they are locked by !LOCKER",
    strTargetLegCantDueAccess="Cannot manipulate victim's locks due to their access setting.",
    strTargetLegCantDueAlreadySet="It's already set to this level!",
    strTargetLegCantDuePadlock="You can't untie !TARGET's legs while they are locked with padlock.",
    strTargetLegCantDueNotRestrained="The legs are not restrained.";

    String strTargetArmUnties1="You've untied !TARGET's paws.",
    strTargetArmUnties2="!USER unties your paws.",
    strTargetArmUnties3="!USER unties !TARGET's paws'",
    strTargetArmArmbinder1="You place !TARGET's paws inside an armbinder and secure it, preventing them from using their paws.",
    strTargetArmArmbinder2="!USER places your paws inside an armbinder, preventing you from using your paws.",
    strTargetArmArmbinder3="!USER places !TARGET's paws inside an armbinder, preventing them from using their paws.",
    strTargetArmBehind1="You cuff !TARGET's paws behind their back.",
    strTargetArmBehind2="!USER cuffs your paws behind your back.",
    strTargetArmBehind3="!USER cuffs !TARGET's paws behind their back.",
    strTargetArmBehindT1="You cuff !TARGET's paws behind their back tightly.",
    strTargetArmBehindT2="!USER cuffs your paws behind your back tightly.",
    strTargetArmBehindT3="!USER cuffs !TARGET's paws behind their back tightly.",
    strTargetArmBehindBelt1="You cuff !TARGET's paws behind their back and secure it with a small chain to their belt.",
    strTargetArmBehindBelt2="!USER cuffs your paws behind your back and secures it with a small chain to your belt.",
    strTargetArmBehindBelt3="!USER cuffs !TARGET's paws behind their back and secures it with a small chain to their belt.",
    strTargetArmBehindBeltT1="You cuff !TARGET's paws behind their back and tightly secure it to their belt.",
    strTargetArmBehindBeltT2="!USER cuffs your paws behind your back and tightly secures it to your belt.",
    strTargetArmBehindBeltT3="!USER cuffs !TARGET's paws behind their back and tightly secure it to their belt.",
    strTargetArmElbow1="You cuff !TARGET's paws to their elbow.",
    strTargetArmElbow2="!USER cuffs your paws to your elbow.",
    strTargetArmElbow3="!USER cuffs !TARGET's paws to their elbow.",
    strTargetArmFront1="You cuff !TARGET's paws up front.",
    strTargetArmFront2="!USER cuffs your paws up front.",
    strTargetArmFront3="!USER cuffs !TARGET's paws up front.",
    strTargetArmBelt1="You cuff !TARGET's paws up front and secure it to their belt, like a high security prisoner that needs to be restrained 24/7.",
    strTargetArmBelt2="!USER cuffs your paws up front and secures it to your belt, like a high security prisoner that needs to be restrained 24/7.",
    strTargetArmBelt3="!USER cuffs !TARGET's paws up front and secures it to their belt, like a high security prisoner that needs to be restrained 24/7.",
    strTargetArmPray1="You cuff !TARGET's paws up and praying, but to make it less comfortable, you do it behind their back.",
    strTargetArmPray2="!USER cuffs your paws up and praying, but to make it less comfortable, its cuffed behind your back.",
    strTargetArmPray3="!USER cuffs !TARGET's paws up up and praying, but to make it less comfortable, you do it behind their back.",
    strTargetArmSideways1="You cuff !TARGET's paws sideways.",
    strTargetArmSideways2="!USER cuffs your paws sideways.",
    strTargetArmSideways3="!USER cuffs !TARGET's paws sideways.",
    strTargetArmSidewaysT1="You cuff !TARGET's paws sideways very tightly.",
    strTargetArmSidewaysT2="!USER cuffs your paws sideways very tightly.",
    strTargetArmSidewaysT3="!USER cuffs !TARGET's paws sideways very tightly.";

    String strTargetLegUntie1="You've untied !TARGET's legs.",
    strTargetLegUntie2="!USER has untied your legs.",
    strTargetLegUntie3="!USER unties !TARGET's legs.",
    strTargetLegFauxTaut1="You tie !TARGET's legs enough for them to walk but not to run.",
    strTargetLegFauxTaut2="!USER ties your legs enough to walk but not to run.",
    strTargetLegFauxTaut3="!USER ties !TARGET's legs enough for them to walk but not to run.",
    strTargetLegTaut1="You tie !TARGET's legs enough for them to barely walk.",
    strTargetLegTaut2="!USER ties your legs enough to barely walk.",
    strTargetLegTaut3="!USER ties !TARGET's legs enough for them to barely walk.",
    strTargetLegStand1="You tie !TARGET's legs to the point they can't walk but can hop like a bunny.",
    strTargetLegStand2="!USER ties your legs to the point you can't walk but can hop like a bunny.",
    strTargetLegStand3="!USER ties !TARGET's legs to the point they can't walk but can hop like a bunny.",
    strTargetLegLayBack1="You tie !TARGET's legs, forcing them to lay on their back, unable stand up.",
    strTargetLegLayBack2="!USER ties your legs, forcing you to lay on your back, unable stand up.",
    strTargetLegLayBack3="!USER ties !TARGET's legs, forcing them to lay on their back, unable stand up.",
    strTargetLegLayBelly1="You tie !TARGET's legs, forcing them to lay on their belly, unable stand up.",
    strTargetLegLayBelly2="!USER ties your legs, forcing you to lay on your belly, unable stand up.",
    strTargetLegLayBelly3="!USER ties !TARGET's legs, forcing them to lay on their belly, unable stand up.",
    strTargetLegSit1="You tie !TARGET's legs, forcing them to sit down, unable stand up.",
    strTargetLegSit2="!USER ties your legs, forcing you to sit down, unable stand up.",
    strTargetLegSit3="!USER ties !TARGET's legs, forcing them to sit down, unable stand up.",
    strTargetLegSpreadbar1="You forcefully spread !TARGET's legs with a metal spreaderbar.",
    strTargetLegSpreadbar2="!USER forcefully spreads your legs with a metal spreaderbar.",
    strTargetLegSpreadbar3="!USER forcefully spreads !TARGET's legs with a metal spreaderbar.",
    strTargetLegHogback1="You forcefully tie !TARGET's legs in a hogtie fashion, but reversed.",
    strTargetLegHogback2="!USER forcefully ties you in a hogtie fashion, but reversed.",
    strTargetLegHogback3="!USER forcefully ties !TARGET's legs in a hogtie fashion, but reversed.",
    strTargetLegHogside1="You forcefully tie !TARGET's legs in a hogtie fashion, but sideways.",
    strTargetLegHogside2="!USER forcefully ties you in a hogtie fashion, but sideways.",
    strTargetLegHogside3="!USER forcefully ties !TARGET's legs in a hogtie fashion, but sideways.",
    strTargetLegHogtie1="You forcefully tie !TARGET's legs in a hogtie fashion.",
    strTargetLegHogtie2="!USER forcefully ties you in a hogtie fashion.",
    strTargetLegHogtie3="!USER forcefully ties !TARGET's legs in a hogtie fashion.";

    String strCuffsCantDueLocked="Can't manipulate your cuffs due to they locked by !LOCKER.";



}
