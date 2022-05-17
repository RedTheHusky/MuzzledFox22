package nsfw.diaper;

import kong.unirest.json.JSONArray;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors_Red;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;
import restraints.in.iMitts;
import restraints.in.iRDPatreon;
import restraints.in.iRestraints;
import restraints.models.CUFFSARMSLEVELS;

import java.util.Arrays;

public interface iDiaperInteractive {
    int intDefaultWaitingMinute=10;
    String strWaitingPeriodImeout="Timeout",waitingresponsehastimeuted=" waiting response has timeuted!";
    String sRTitle="Interactive Diaper";
    String profileName="diaperinteraction";String gTable ="diaperInteractive";
    String fieldProfile="profile",keyProfileLockedBy="lockedBy";
    String keyProfileLocked ="locked";
    String keyProfileAccess ="access";
    public enum ACCESSLEVEL {
        INVALID(-1,""),
        Private(0,"private"),
        Caretaker(1,"caretaker"),
        Exposed(2,"exposed"),
        Key(3,"key"),
        Protected(4,"protected"),
        Public(5,"public"),
        Ask(6,"ask");
        private String string;
        private int code;
        private ACCESSLEVEL(int code, String string) {
            this.code = code;
            this.string = string;

        }
        public static ACCESSLEVEL valueByCode(int code) {
            ACCESSLEVEL[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ACCESSLEVEL status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static ACCESSLEVEL valueByString(String name) {
            ACCESSLEVEL[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ACCESSLEVEL status = var1[var3];
                if (status.string.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getString() {
            return this.string;
        }
        public int getCode() {
            return this.code;
        }
        static {
            ACCESSLEVEL[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                ACCESSLEVEL s = var0[var2];
            }

        }
        public static String getString(ACCESSLEVEL level){
            String fName="[getString]";
            Logger logger = Logger.getLogger(restraints.models.CONFINELEVELS.class);
            try {
                return level.getString();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    String fieldCaretaker ="caretaker",keyCaretakerId ="id",keyCaretakerAccepted ="accepted";
    String nSecOwners="secOnwers";
    String keyEnabled ="enabled";
    String keyChance ="chance";
    String keyLevel ="level",keyMaxLevel="maxlevel";
    String fieldWet="wet";
    String fieldMessy="messy",fieldSuit="suit",fieldDiaper="diaper",keyType="type";
    String fieldCustomActions="customactions",keyActions="actions",keyName="name",keyText1="text1",keyText2="text2";
    static lcJSONUserProfile safetyUserProfileEntry(lcJSONUserProfile gUserProfile){
        String fName="[safetyUserProfileEntry]";  Logger logger = Logger.getLogger(iDiaperInteractive.class);
        logger.info(fName+".safety check");
        try {
            String field;
            field=fieldProfile;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyProfileLocked,false);
            gUserProfile.safetyPutFieldEntry(field, keyProfileAccess,ACCESSLEVEL.Private.getString());
            gUserProfile.safetyPutFieldEntry(field, keyProfileLockedBy,0);
            field=fieldCaretaker;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyCaretakerId,"");
            gUserProfile.safetyPutFieldEntry(field, keyCaretakerAccepted,false);
            field=fieldWet;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyChance,4);
            gUserProfile.safetyPutFieldEntry(field, keyLevel,0);
            field=fieldMessy;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,false);
            gUserProfile.safetyPutFieldEntry(field, keyChance,4);
            gUserProfile.safetyPutFieldEntry(field, keyLevel,0);
            field=fieldSuit;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,false);
            gUserProfile.safetyPutFieldEntry(field, keyType,"");
            field=fieldDiaper;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyType,DIAPERTYPE.White.getString());
            gUserProfile.safetyPutFieldEntry(field, keyMaxLevel,6);
            field=fieldCustomActions;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyActions,new JSONArray());
            field=fieldTimeLock;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field,keyEnabled,false);
            gUserProfile.safetyPutFieldEntry(field,keyDuration,0);
            gUserProfile.safetyPutFieldEntry(field,keyMaxDuration,0);
            gUserProfile.safetyPutFieldEntry(field,keyMinDuration,0);
            gUserProfile.safetyPutFieldEntry(field,keyStartDuration,60000*15);
            gUserProfile.safetyPutFieldEntry(field,keyTimestamp,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        return  gUserProfile;
    }
    String fieldTimeLock="timelock",keyDuration="duration",keyMaxDuration="maxDuration",keyMinDuration="minDuration",keyStartDuration="startDuration",keyTimestamp="timestamp";










    String vOn="on",vOff="off",vTrue="true",vFalse="false";
    public enum DIAPERTYPE {
        INVALID(-1,"",""),
        White(0,"white", "classic",6),
        Pink(1,"pink","pink classic",6),
        WhitePlastic(2,"white_plastic","plastic",8),
        PinkPlastic(3,"pink_plastic","pink plastic",8),
        Eevee(4,"eevee","eevee",6),
        Pikachu(5,"pikachu", "pikachu",6),
        Unicorn(6,"unicorn","unicorn",6),
        Peekabu(7,"peekabu","peekabu",11),
        Paws(8,"paws","paws",6),
        BriefsWithClothBacking(9,"briefs_with_cloth_backing","briefs with cloth backing",4),
        BriefsWithPlasticBacking(10,"briefs_with_plastic_backing","briefs with plastic backing",4),
        Briefs4Overnight(11,"briefs_for_overnight","briefs for overnights",11),
        PullUps(12,"pull-ups","pull-ups"),
        SwimDiapers(13,"swim_diapers","swim diapers",1);
        private String string,name;
        private int code, wetness;
        private DIAPERTYPE(int code, String string,String name) {
            this.code = code;
            this.string = string;
            this.name=name;
            this.wetness=0;
        }
        private DIAPERTYPE(int code, String string,String name,int wetness) {
            this.code = code;
            this.string = string;
            this.name=name;
            this.wetness=wetness;
        }
        public static DIAPERTYPE valueByCode(int code) {
            DIAPERTYPE[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                DIAPERTYPE status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static DIAPERTYPE valueByString(String name) {
            DIAPERTYPE[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                DIAPERTYPE status = var1[var3];
                if (status.string.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getString() {
            return this.string;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        public int getWetness() {
            return this.wetness;
        }
        static {
            DIAPERTYPE[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                DIAPERTYPE s = var0[var2];
            }

        }
        public static String getString(DIAPERTYPE level){
            String fName="[getString]";
            Logger logger = Logger.getLogger(nsfw.diaper.iDiaperInteractive.class);
            try {
                return level.getString();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public static String getName(DIAPERTYPE level){
            String fName="[geName]";
            Logger logger = Logger.getLogger(nsfw.diaper.iDiaperInteractive.class);
            try {
                return level.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }

    public enum SUITTYPE {
        INVALID(-1,"",""),
        Eevee(1,"eevee","eevee"),
        Pikachu(2,"pikachu","pikachu"),
        Unicorn(3,"unicorn","unicorn"),
        Wolf(7,"wolf","wolf"),
        Kitten(8,"kitten","kitten"),
        Puppy(9,"puppy","puppy"),
        Flareon(10,"flareon","flareon"),
        Jolteon(11,"jolteon","jolteon"),
        Vaporeon(12,"vaporeon","vaporeon"),
        Sylveon(13,"sylveon","sylveon");
        private String string,name;
        private int code;
        private SUITTYPE(int code, String string,String name) {
            this.code = code;
            this.string = string;
            this.name=name;
        }
        public static SUITTYPE valueByCode(int code) {
            SUITTYPE[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SUITTYPE status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static SUITTYPE valueByString(String string) {
            SUITTYPE[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                SUITTYPE status = var1[var3];
                if (status.string.equals(string)) {
                    return status;
                }
            }
            return null;
        }
        public String getString() {
            return this.string;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            SUITTYPE[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                SUITTYPE s = var0[var2];
            }

        }
        public static String getString(DIAPERTYPE level){
            String fName="[getString]";
            Logger logger = Logger.getLogger(nsfw.diaper.iDiaperInteractive.class);
            try {
                return level.getString();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public static String getName(DIAPERTYPE level){
            String fName="[getName]";
            Logger logger = Logger.getLogger(nsfw.diaper.iDiaperInteractive.class);
            try {
                return level.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }


    int maxWetLevel=6,maxMessyLevel=6;
    int maxActions=10,maxNormalUserActions=2;
    static int isArmsRestrainedHelpless(lcGlobalHelper gGlobal, Member member){
        String fName="[isArmsRestrainedHelpless]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            lcJSONUserProfile userProfile=iRestraints.getUserProfile(gGlobal,member, false);
            if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                logger.info(fName + ".invalid>0");
                return  0;
            }
            logger.info(fName + ".userProfile.jsonUser="+userProfile.jsonObject.toString());
            if(userProfile.jsonObject.has(iRestraints.nMitts)&&userProfile.jsonObject.getJSONObject(iRestraints.nMitts).getBoolean(iRestraints.nOn)){
                logger.info(fName + ".mitts>2");
                return 2;
            }
            if(userProfile.jsonObject.has(iRestraints.nArmsCuffs)&&userProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).getBoolean(iRestraints.nOn)&&userProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).getString(iRestraints.nLevel).equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())){
                logger.info(fName + ".armcuffs&armbinder>3");
                return  3;
            }
            if(userProfile.jsonObject.has(iRestraints.nArmsCuffs)&&userProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).getBoolean(iRestraints.nOn)){
                logger.info(fName + ".armcuffs>1");
                return  1;
            }
            if(userProfile.jsonObject.has(iRestraints.nStraitjacket)&&userProfile.jsonObject.getJSONObject(iRestraints.nStraitjacket).getBoolean(iRestraints.nOn)&&userProfile.jsonObject.getJSONObject(iRestraints.nStraitjacket).getBoolean(iRestraints.nStrapArms)){
                logger.info(fName + ".jacket>true");
                return  4;
            }
            logger.info(fName + ".default>f0");
            return  0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }


    }
    String strHowDoyouThinkYouCanWithHandsBound_Mitts="How do you think you can do that while wearing mitts?";
    String strHowDoyouThinkYouCanWithHandsBound_Armbinder="How do you think you can do that while hands safely locked away inside an amrbinder?";
    String strHowDoyouThinkYouCanWithHandsBound_HandsCuffed="How do you think you can do that while hands cuffed?";
    String strHowDoyouThinkYouCanWithHandsBound_SJ="How do you think you can do that while forced to hug yourself with a jacket?";
    String strTitle="Interactive Diaper";
    static boolean isPrevented2AccessOption2ArmsRestrained(lcGlobalHelper gGlobal, Member member){
        String fName="[isPrevented2AccessOption2ArmsRestrained]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            int i=isArmsRestrainedHelpless(gGlobal, member);
            logger.info(fName + ".i="+i);
            if(i>0){
                logger.info(fName + ".has>true");
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
    static boolean isPrevented2ActionArmsRestrained(lcGlobalHelper gGlobal, Member member){
        String fName="[isPrevented2Action2ArmsRestrained]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            int i=isArmsRestrainedHelpless(gGlobal, member);
            logger.info(fName + ".i="+i);
            if(i>=3){
                logger.info(fName + ".is above>true");
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
    static boolean doPrevented2AccessOption2ArmsRestrained(lcGlobalHelper gGlobal, Member member){
        String fName="[doPrevented2AccessOption2ArmsRestrained]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            int i=isArmsRestrainedHelpless(gGlobal, member);
            logger.info(fName + ".i="+i);
            if(i>1){
                logger.info(fName + ".has>true");
                switch (i){
                    case 2:
                        if(iMitts.isMittsEqualLevel(gGlobal, member, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(member,sRTitle);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_Mitts, llColors_Red.llColorRed_CoralPink);
                        }
                        break;
                    case 3:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_Armbinder, llColors_Red.llColorRed_CoralPink);
                        break;
                    case 4:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_SJ, llColors_Red.llColorRed_CoralPink);
                        break;
                }
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
    static boolean doPrevented2AccessOption2ArmsRestrained(lcGlobalHelper gGlobal, Member member, TextChannel textChannel){
        String fName="[doPrevented2AccessOption2ArmsRestrained]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            int i=isArmsRestrainedHelpless(gGlobal, member);
            logger.info(fName + ".i="+i);
            if(i>1){
                logger.info(fName + ".has>true");
                switch (i){
                    case 2:
                        if(iMitts.isMittsEqualLevel(gGlobal, member, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(member,sRTitle,textChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_Mitts, llColors_Red.llColorRed_CoralPink);
                        }
                        break;
                    case 3:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_Armbinder, llColors_Red.llColorRed_CoralPink);
                        break;
                    case 4:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_SJ, llColors_Red.llColorRed_CoralPink);
                        break;
                }
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
    static boolean doPrevented2Action2ArmsRestrained(lcGlobalHelper gGlobal, Member member){
        String fName="[doPrevented2Action2ArmsRestrained]";
        Logger logger = Logger.getLogger(iDiaperInteractive.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            int i=isArmsRestrainedHelpless(gGlobal, member);
            logger.info(fName + ".i="+i);
            if(i>=3){
                logger.info(fName + ".is above>true");
                switch (i){
                    case 3:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_Armbinder, llColors_Red.llColorRed_CoralPink);
                        break;
                    case 4:
                        lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),strTitle,strHowDoyouThinkYouCanWithHandsBound_SJ, llColors_Red.llColorRed_CoralPink);
                        break;
                }
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

    String strCantManipulateTheirTimelockDue2Settings="Can't manipulate their timelock due to their access setting.",
    strCantManipulateTheirTimelockDue2Locked="Can't manipulate their timelock  do to they locked by ";



}
