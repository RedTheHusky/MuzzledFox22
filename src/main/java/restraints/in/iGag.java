package restraints.in;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.GAGTYPES;

import java.util.Arrays;

public interface iGag {
    static boolean isGagOn(JSONObject gag){
        String fName="[isGagOn]";
        Logger logger = Logger.getLogger(iGag.class);
        try {
            boolean result=gag.getBoolean(iRestraints.nOn);
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isGagEqualLevel(JSONObject gag,String level){
        String fName="[isGagEqualLevel]";
        Logger logger = Logger.getLogger(iGag.class);
        try {
            if(!gag.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(gag.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
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
    static boolean isGagEqualType(JSONObject gag,String level){
        String fName="[isGagEqualType]";
        Logger logger = Logger.getLogger(iGag.class);
        try {
            if(!gag.has(iRestraints.nType)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(gag.getString(iRestraints.nType).equalsIgnoreCase(level)){
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
    String cantmanipulatedueaccessset2owner="You cannot manipulate your own gag, as only your owners and sec-owners can do that!";
    String cantmanipulaleduepermalocked= "You cannot manipulate your restraints as they have been permanently locked!";
    String cantmanipulatedueaccessset2public="You cannot manipulate your own gag whilst it's set to public! That's for everyone else to control.";
    String cantmanipualetduetimelocked="Your gag is still time-locked, and that time's not up yet!";
    String cantmanipulateduepartofsuit="You can't manipulate your gag as it's part of the suit you're wearing!";
    String cantduewearingjacket="How can you manipulate your gag with that straitjacket on? You can't even reach it!";
    String cantduearmscuffed="How can you manipulate your gag with your arms bound? You can't even reach it!";
    int patreonLimit4CustomText=40,normalLimit4CustomText=10;
    String strSetupTitle="Gag Setup";
    String nUngag="ungag";
    default boolean isGagLevelCommand(String str){
        String fName="[isGagLevelCommand]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(str.equalsIgnoreCase(nUngag)
                    ||str.equalsIgnoreCase(GAGLEVELS.Mute.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Extreme.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Severe.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Loose.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Faux.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Kitty.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Puppy.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Paci.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Pony.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Piggy.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Sheep.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Bird.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Cow.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Turkey.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Corrupt.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Toy.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.DroneReindeer.getName())||str.equalsIgnoreCase("reindeer")
                    ||str.equalsIgnoreCase(GAGLEVELS.Training.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName()) ||str.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName()) ||str.equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName()) ||str.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName())
                    ||str.equalsIgnoreCase(GAGLEVELS.Chocke.getName())
            ){return  true;}
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean isGagTypeCommand(String str){
        String fName="[isGagTypeCommand]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return str.equalsIgnoreCase(GAGTYPES.Ball.getName()) || str.equalsIgnoreCase(GAGTYPES.Dildo.getName()) || str.equalsIgnoreCase(GAGTYPES.DReverseildo.getName()) || str.equalsIgnoreCase(GAGTYPES.Sock.getName()) || str.equalsIgnoreCase(GAGTYPES.Underwear.getName()) || str.equalsIgnoreCase(GAGTYPES.Paci.getName()) || str.equalsIgnoreCase(GAGTYPES.LeatherMuzzle.getName()) || str.equalsIgnoreCase(GAGTYPES.WireFrameMuzzle.getName()) || str.equalsIgnoreCase(GAGTYPES.Tape.getName()) || str.equalsIgnoreCase(GAGTYPES.Ring.getName())||str.equalsIgnoreCase(GAGTYPES.ReindeerMuzzle.getName());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean isGagExceptionCommand(String str){
        String fName="[lsHasUserAnyOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return str.equalsIgnoreCase(iRestraints.nOn) || str.equalsIgnoreCase(iRestraints.vOff) || str.equalsIgnoreCase(iRestraints.vAdd) || str.equalsIgnoreCase(iRestraints.vRemove) || str.equalsIgnoreCase(iRestraints.vClear) || str.equalsIgnoreCase(iRestraints.vList);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String strRedSoloAsk="Do you wish to used safeword for gag?",
            strRedSoloYes="You have safeworded for gag.";
}
