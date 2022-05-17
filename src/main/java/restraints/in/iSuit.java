package restraints.in;

import models.lc.json.profile.lcJSONUserProfile;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.models.entity.entityRDUserProfile;

import java.util.Arrays;

public interface iSuit {
    String title4ToyCustomGoodTalk="Custom Text 4 Toy Good Talk",
            title4CustomTextToyGag="Custom Text 4 Toy Gag",
            title4ToySuitPrefix="Toy Suit Prefix";
    int normalLimit4CustomText=5, patreonLimit4CustomText =20;

    String postKeyPrefix0="!PREFIX0",
            postKeyPrefix1="!PREFIX1";
    String postSuitPunishChastity="The suit chastity device zaps your private area. After that the hood displays a warning message as your speech is blocked: \n\"!PREFIX1 has said a bad sentence. \nPunishment: -1 point, chastity zap\"";
    String postSuitPunishChastityMale="A zapping noise can be heard as the lock symbol on Toy !USER null bulge lights up. :zap::zap::zap:",
        postSuitPunishChastityFemale="A zapping noise can be heard as the lock symbol on Toy !USER null sex lights up. :zap::zap::zap:",
        postSuitPunishChastityNeutral="A zapping noise can be heard as the lock symbol on Toy !USER null bulge/sex lights up. :zap::zap::zap:";
    String postSuitHoodPunishMessage1="The hood displays a warning message as your speech is blocked: \n\"!PREFIX1 has said a bad sentence. \nPunishment: -1 point\"";
    String postSuitUsedUpAllFreeSpeech1="!PREFIX has used up all their free speech. Please say `!TEXT` to earn more free speech and increase your score.";
    String postSuitPunishChastity2="The suit chastity device zaps your private area. After that the hood displays a warning message as your speech is blocked: \n\"!PREFIX1 has not mentioned themself as !PREFIX0. \n!PREFIX1 is required to start the sentence with word !PREFIX1 or contain toy.\nPunishment: -1 point, null chastity zap\"";
    String postSuitPunishNotMentioningThemselves="The hood displays a warning message as your speech is blocked: \n\"!PREFIX1 has not mentioned themself as !PREFIX0. \n!PREFIX1 is required to start the sentence with word !PREFIX1 or contain toy.\nPunishment: -1 point, null chastity zap\"";
    int vTalkLimit1=20,vTalkLimit2=5,vTalkLimit3=2;
    String levelSuitLatex="latex",levelSuitRubber="rubber", levelSuitTextile="textile",levelSuitToy="toy";
    String levelSuitPuppy="puppy",levelSuitKitty="kitty", levelSuitPony="pony",levelSuitDrone="drone",levelSuitCow="cow",levelSuitCatsuit="catsuit",levelSuitReindeer="reindeer",levelSuitPlush="plush";
    String levelSpecialToyReindeeer="toyreindeer",levelSuitSpecialToyAlpha="toyalpha",levelSuitSpecialToyBeta="toybeta",levelSuitSpecialToyOmega="toyOmega", vRelease="release", vToy="toy",vAlpha="alpha",vBeta="beta",vOmega="omega",vStatus="status";  int vTalkRestricted1=40,vTalkRestricted2=10,vTalkRestricted3=5;
    String levelSuitBitchsuit="bitchsuit",levelSuitHogsack="hogsack",levelSuitSleepsack="sleepsack";
    static String sStringReplacer(lcJSONUserProfile gUserProfile, Member author, String source,String goodTalk){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iMitts.class);
        logger.info(fName + ".executed");
        try {
            source=iRestraints.sStringReplacer(gUserProfile,author,source);
            source=source.replaceAll("!GOOD",goodTalk);
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    static String sStringReplacer(entityRDUserProfile gNewUserProfile, Member author, String source,String goodTalk){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iMitts.class);
        logger.info(fName + ".executed");
        try {
            return  sStringReplacer(gNewUserProfile.gUserProfile,author,source,goodTalk);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    String strCheaterAdded1="Cheater, you added 1 point.",strCheaterAdded5="Cheater, you added 5 points.",strCheaterAdded10="Cheater, you added 10 points.",strCheaterAdded25="Cheater, you added 25 points.",
    strSubstracted1="Good toy, you subtracted 1 point.",strSubstracted5="Good toy, you subtracted 5 point.",strSubstracted10="Good toy, you subtracted 10 point.",strSubstracted25="Good toy, you subtracted 25 point.";


    String strSoloToyAlpha1="You place yourself into a toy suit set to rank alpha and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nLimiting untie all function for wearer.\nIn order to free yourself you need to behave and earn 25 points or client to free you.\nHave a nice day and remember. A good toy always listens to its clients.\"",
    strSoloToyAlpha2="!USER places themselves into a toy suit set to rank alpha and activates it. Quickly the suit comes to life as it starts covering up !USER entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nLimiting untie all function for wearer.\nIn order to free yourself you need to behave and earn 25 points or client to free you.\nHave a nice day and remember. A good toy always listens to its clients.\"";
    String strSoloToyBeta1="You place yourself into a toy suit set to rank beta and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around your head with a built in dildo gag. The shorts get thick forming a null chastity. Your fists get covered in a ball that acts as mitts, before cuffing them front. Legs get linked together by half a meeter chain, still allowing walking but running not so much. And a heavy collar forms around your neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strSoloToyBeta2="!USER places themselves into a toy suit set to rank beta and activates it. Quickly the suit comes to life as it starts covering up !USER entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before cuffing them front. Legs get linked together by half a meeter chain, still allowing walking but running not so much. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"";
    String strSoloToyOmega1="You place yourself into a toy suit set to rank omega and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around your head with a built in dildo gag. The shorts get thick forming a null chastity. Your fists get covered in a ball that acts as mitts, before pulled back as an armbinder starts to form, securing your paws even more. A rigid plastic forms at your legs, linking both legs together in a spreadbar formation. And a heavy collar forms around your neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 90 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strSoloToyOmega2="!USER places themselves into a toy suit set to rank omega and activates it. Quickly the suit comes to life as it starts covering up !USER entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before their paw gets pulled back as an armbinder starts to form, securing it even more. A rigid plastic forms at their legs, linking both legs together in a spreadbar formation. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 90 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"";
    String strReleaseVotingAllow1="They were kind and released you from your suite.",
            strReleaseVotingAllow2="!USER is a lucky toy as not only they managed to be good toy, but they also got the vote to get released.  As the suit enters it shut down sequence, all the restraints it applied are undone before melting away.";
    String strReleaseVotingDeny1="!USER requirement for early release was denied. They still need to earn their release. Also as they were omega, their score was reset back to 0.",
            strReleaseVotingDeny2="!USER requirement for early release was denied. They still need to earn their release.";

    String strSoloLatexPuppy1="You put on a latex puppy suit, arf. It cover you from neck to toe in shinny latex, ready for walk in the park.",
            strSoloLatexPuppy2="!USER has put on a latex puppy suit. It cover them from neck to toe in shinny latex, ready for walk in the park.";
    String strSoloLatexKitty1="You put on a latex kitty suit, meow. It cover you from neck to toe in shinny latex with a cute bell on your tail or neck.",
            strSoloLatexKitty2 ="!USER has put on a latex kitty suit. It cover them from neck to toe in shinny latex with a cute bell on their tail or neck.";
    String strSoloLatexPony1="You put on a latex pony suit. It cover you from neck to toe in shinny latex, ready to drag the cart.",
            strSoloLatexPony2="!USER has put on a latex pony suit. It cover them from neck to toe in shinny latex, ready to drag the cart.";
    String strSoloLatexDrone1="You put on a latex drone suit. It cover you from neck to toe in shinny latex. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strSoloLatexDrone2="!USER has put on a latex drone suit. It cover them from neck to toe in shinny latex. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ";
    String strSoloLatexCow1="You put on a latex cow suit. It cover you from neck to toe in shinny latex. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strSoloLatexCow2="!USER has put on a latex cow suit. It cover them from neck to toe in shinny latex. Additionally an udder is placed over their crotch area and connected with pipes to their reproductive organ.";
    String strSoloLatexCatsuit1="You put on a latex catsuit. It cover you from neck to toe in shinny latex, all sexy and ready to share some fun time.",
            strSoloLatexCatsuit2="!USER has put on a latex catsuit. It cover them from neck to toe in shinny latex, all sexy and ready to share some fun time.";
    String strSoloLatexBitchsuit1="You put on a latex bitchsuit.",
            strSoloLatexBitchsuit2="!USER has put on a latex bitchsuit. Ideal suit to walk on all 4 and serve others.";
    String strSoloLatexHogsack1="You put on a latex hogsack.",
            strSoloLatexHogsack2="!USER has put on a latex hogsack. Immobile and just an object to be used by others.";
    String strSoloLatexSleepsack1="You put on a latex sleepsack.",
            strSoloLatexSleepsack2="!USER has put on a latex sleepsack. Ideal way for a pet to go to bed, no way for them to cause problems or play with themselves.";

    String strSoloRubberPuppy1="You put on a thick puppy suit, arf. It cover you from neck to toe in thick rubber, ready for walk in the park.",
            strSoloRubberPuppy2="!USER has put on a rubber puppy suit. It cover them from neck to toe in thick rubber, ready for walk in the park.";
    String strSoloRubberKitty1="You put on a thick kitty suit, meow. It cover you from neck to toe in thick rubber with a cute bell on your tail or neck.",
            strSoloRubberKitty2="!USER has put on a rubber kitty suit. It cover them from neck to toe in thick rubber with a cute bell on their tail or neck.";
    String strSoloRubberPony1="You put on a thick pony suit. It cover you from neck to toe in thick rubber, ready to drag the cart.",
            strSoloRubberPony2="!USER has put on a rubber pony suit. It cover them from neck to toe in thick rubber, ready to drag the cart.";
    String strSoloRubberDrone1="You put on a rubber drone suit. It cover you from neck to toe in thick rubber. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strSoloRubberDrone2="!USER has put on a rubber drone suit. It cover them from neck to toe in thick rubber. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ";
    String strSoloRubberCow1="You put on a rubber cow suit. It cover you from neck to toe in thick rubber. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strSoloRubberCow2="!USER has put on a rubber cow suit. It cover them from neck to toe in thick rubber. Additionally an udder is placed over their crotch area and connected with pipes to their reproductive organ.";
    String strSoloRubberCatsuit1="You put on a rubber catsuit. It cover you from neck to toe in shinny latex, all sexy and ready to share some fun time.",
            strSoloRubberCatsuit2="!USER has put on a rubber catsuit. It cover them from neck to toe in thick rubber, all sexy and ready to share some fun time. The hood display some info for the wearer and they learned just how long they need to wait before they finally released from the suit.";
    String strSoloRubberBitchsuit1="You put on a rubber bitchsuit.",
            strSoloRubberBitchsuit2="!USER has put on a rubber bitchsuit. Ideal suit to walk on all 4 and serve others.";
    String strSoloRubberHogsack1="You put on a rubber hogsack.",
            strSoloRubberHogsack2="!USER has put on a rubber hogsack. Immobile and just an object to be used by others.";
    String strSoloRubberSleepsack1="You put on a rubber sleepsack.",
            strSoloRubberSleepsack2="!USER has put on a rubber sleepsack. Ideal way for a pet to go to bed, no way for them to cause problems or play with themselves.";

    String strSoloTextilePlush1="You put on a thick textile plush suit.",
            strSoloTextilePlush2="!USER has put on a thick textile plush suit.";
    String strSoloTextileBitchsuit1="You put on a textile bitchsuit.",
            strSoloTextileBitchsuit2="!USER has put on a textile bitchsuit. Ideal suit to walk on all 4 and serve others.";
    String strSoloTextileHogsack1="You put on a textil hogsack.",
            strSoloTextileHogsack2="!USER has put on a textile hogsack. Immobile and just an object to be used by others.";
    String strSoloTextileSleepsack1="You put on a textile sleepsack.",
            strSoloTextileSleepsack2="!USER has put on a textile sleepsack. Ideal way for a pet to go to bed, no way for them to cause problems or play with themselves.";

    String strTargetToyAlpha1="You place !TARGET in a toy suit set to rank alpha and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nLimiting untie all function for wearer.\nIn order to free yourself you need to behave and earn 25 points or client to free you.\nHave a nice day and remember. A good toy always listens to its clients.\"",
            strTargetToyAlpha2="!USER places you in a toy sut set up to rank alpha and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around your head with a built in dildo gag. The shorts get thick forming a null chastity. Your fists get covered in a ball that acts as mitts. And a heavy collar forms around your neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nLimiting untie all function for wearer.\nIn order to free yourself you need to behave and earn 25 points or client to free you.\nHave a nice day and remember. A good toy always listens to its clients.\"",
            strTargetToyAlpha3="!USER places !TARGET' in a toy suit set to rank alpha and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nLimiting untie all function for wearer.\nIn order to free yourself you need to behave and earn 25 points or client to free you.\nHave a nice day and remember. A good toy always listens to its clients.\"";
    String strTargetToyBeta1="You place !TARGET in a toy suit set to rank beta and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before cuffing them front. Legs get linked together by half a meeter chain, still allowing walking but running not so much. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strTargetToyBeta2="!USER places you in a toy sut set up to rank beta and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around your head with a built in dildo gag. The shorts get thick forming a null chastity. Your fists get covered in a ball that acts as mitts, before cuffing them front. Legs get linked together by half a meeter chain, still allowing walking but running not so much. And a heavy collar forms around your neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strTargetToyBeta3="!USER places !TARGET' in a toy suit set to rank beta and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before cuffing them front. Legs get linked together by half a meeter chain, still allowing walking but running not so much. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"";
    String strTargetToyOmega1="You place !TARGET in a toy suit set to rank omega and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before pulling it back as an armbinder starts to form, securing their paws even more. A rigid plastic forms at their legs, linking both legs together in a spreadbar formation. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 90 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strTargetToyOmega2="!USER places you in a toy sut set up to rank omega and activates it. Quickly the suit comes to life as it starts covering up your entire body. A hood forms around your head with a built in dildo gag. The shorts get thick forming a null chastity. Your fists get covered in a ball that acts as mitts, before pulled back as an armbinder starts to form, securing your paws securing it even more. A rigid plastic forms at your legs, linking both legs together in a spreadbar formation. And a heavy collar forms around your neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 90 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"",
            strTargetToyOmega3="!USER places !TARGET' in a toy suit set to rank omega and activates it. Quickly the suit comes to life as it starts covering up !TARGET entire body. A hood forms around their head with a built in dildo gag. The shorts get thick forming a null chastity. Their fists get covered in a ball that acts as mitts, before pulled back as an armbinder starts to form, securing their paws securing it even more. A rigid plastic forms at their legs, linking both legs together in a spreadbar formation. And a heavy collar forms around their neck. A message pops up in the hood hud informing the wearer \"Welcome to ToySuit 0.5.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 90 points before a client can release you.\nHave a nice and productive day. And remember...!GOOD\"";


    String strTargetLatexPuppy1="You put !TARGET in a latex puppy suit, arf. It cover them from neck to toe in shinny latex, ready for walk in the park.",
            strTargetLatexPuppy2="!USER has put you in a latex puppy suit, arf. It cover you from neck to toe in shinny latex, ready for walk in the park.",
            strTargetLatexPuppy3="!USER has put !TARGET in a latex puppy suit. It cover them from neck to toe in shinny latex, ready for walk in the park.";
    String strTargetLatexKitty1="You put !TARGET in a latex kitty suit, meow. It cover you from neck to toe in shinny latex with a cute bell on your tail or neck.",
            strTargetLatexKitty2="!USER has put you in a latex kitty suit, meow. It cover you from neck to toe in shinny latex with a cute bell on your tail or neck.",
            strTargetLatexKitty3="!USER has put !TARGET in a latex kitty suit. It cover them from neck to toe in shinny latex with a cute bell on their tail or neck.";
    String strTargetLatexPony1="You put !TARGET in a latex pony suit. It cover you from neck to toe in shinny latex, ready to drag the cart.",
            strTargetLatexPony2="!USER has put you in a latex pony suit. It cover you from neck to toe in shinny latex, ready to drag the cart.",
            strTargetLatexPony3="!USER has put !TARGET in a latex pony suit. It cover them from neck to toe in shinny latex, ready to drag the cart.";
    String strTargetLatexDrone1="You put !TARGET in a latex drone suit. It cover you from neck to toe in shinny latex. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strTargetLatexDrone2="!USER has put you in a latex drone suit. It cover you from neck to toe in shinny latex. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strTargetLatexDrone3="!USER has put !TARGET in a latex drone suit. It cover them from neck to toe in shinny latex. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ";
    String strTargetLatexCow1="You put !TARGET in a latex cow suit. It cover you from neck to toe in shinny latex. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strTargetLatexCow2="!USER has put you in a latex cow suit. It cover you from neck to toe in shinny latex. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strTargetLatexCow3="!USER has put !TARGET in a latex cow suit. It cover them from neck to toe in shinny latex. Additionally an udder is placed over their crotch area and connected with pipes to their reproductive organ.";
    String strTargetLatexCatsuit1="You put !TARGET in a latex catsuit. It cover you from neck to toe in shinny latex, all sexy and ready to share some fun time.",
            strTargetLatexCatsuit2="!USER has put you in a latex catsuit. It cover you from neck to toe in shinny latex, all sexy and ready to share some fun time.",
            strTargetLatexCatsuit3="!USER has put !TARGET in a latex catsuit. It cover them from neck to toe in shinny latex, all sexy and ready to share some fun time.";
    String strTargetLatexBitchsuit1="You put !TARGET in a latex bitchsuit.",
            strTargetLatexBitchsuit2="!USER puts you in a latex bitchsuit.",
            strTargetLatexBitchsuit3="!USER puts !TARGET in a latex bitchsuit. Preparing them to walk on all 4 and service others.";
    String strTargetLatexHogsack1="You put !TARGET in a latex hogsack.",
            strTargetLatexHogsack2="!USER puts you in a latex hogsack.",
            strTargetLatexHogsack3="!USER puts !TARGET in a latex hogsack. Immobile and just an object to be used by others.";
    String strTargetLatexSleepsack1="You put !TARGET in a latex sleepsack.",
            strTargetLatexSleepsack2="!USER puts you in a latex sleepsack.",
            strTargetLatexSleepsack3="!USER puts !TARGET in a latex sleepsack. Ideal way putting pets to bed, no way for them to cause problems or play with themselves.";

    String strTargetRubberPuppy1="You put !TARGET in a rubber  puppy suit, arf. It cover you from neck to toe in thick rubber, ready for walk in the park.",
            strTargetRubberPuppy2="!USER has put you in a rubber  puppy suit, arf. It cover you from neck to toe in thick rubber, ready for walk in the park.",
            strTargetRubberPuppy3="!USER has put !TARGET in a rubber puppy suit. It cover them from neck to toe in thick rubber, ready for walk in the park.";
    String strTargetRubberKitty1="You put !TARGET in a rubber kitty suit, meow. It cover you from neck to toe in thick rubber with a cute bell on your tail or neck.",
            strTargetRubberKitty2="!USER has put you in a rubber kitty suit, meow. It cover you from neck to toe in thick rubber with a cute bell on your tail or neck.",
            strTargetRubberKitty3="!USER has put !TARGET in a rubber kitty suit. It cover them from neck to toe in thick rubber with a cute bell on their tail or neck.";
    String strTargetRubberPony1="You put !TARGET in a rubber pony suit. It cover you from neck to toe in thick rubber, ready to drag the cart.",
            strTargetRubberPony2="!USER has put you in a rubber pony suit. It cover you from neck to toe in thick rubber, ready to drag the cart.",
            strTargetRubberPony3="!USER has put !TARGET in a rubber pony suit. It cover them from neck to toe in thick rubber, ready to drag the cart.";
    String strTargetRubberDrone1="You put !TARGET in a rubber drone suit. It cover you from neck to toe in thick rubber. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strTargetRubberDrone2="!USER has put you in a rubber drone suit. It cover you from neck to toe in thick rubber. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ",
            strTargetRubberDrone3="!USER has put !TARGET in a rubber drone suit. It cover them from neck to toe in thick rubber. Additionally it cover your genitals and your paws too as good drones are always ready to serve. ";
    String strTargetRubberCow1="You put !TARGET in a rubber cow suit. It cover you from neck to toe in thick rubber. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strTargetRubberCow2="!USER has put you in a rubber cow suit. It cover you from neck to toe in thick rubber. Additionally an udder is placed over your crotch area and connected with pipes to your reproductive organ.",
            strTargetRubberCow3="!USER has put !TARGET in a rubber cow suit. It cover them from neck to toe in thick rubber. Additionally an udder is placed over their crotch area and connected with pipes to their reproductive organ.";
    String strTargetRubberCatsuit1="You put !TARGET in a rubber catsuit. It cover you from neck to toe in thick rubber, all sexy and ready to share some fun time.",
            strTargetRubberCatsuit2="!USER has put you in a rubber catsuit. It cover you from neck to toe in thick rubber, all sexy and ready to share some fun time.",
            strTargetRubberCatsuit3="!USER has put !TARGET in a rubber catsuit . It cover them from neck to toe in thick rubber, all sexy and ready to share some fun time.";
    String strTargetRubberBitchsuit1="You put !TARGET in a rubber bitchsuit.",
            strTargetRubberBitchsuit2="!USER puts you in a rubber bitchsuit.",
            strTargetRubberBitchsuit3="!USER puts !TARGET in a rubber bitchsuit. Preparing them to walk on all 4 and service others.";
    String strTargetRubberHogsack1="You put !TARGET in a rubber hogsack.",
            strTargetRubberHogsack2="!USER puts you in a rubber hogsack.",
            strTargetRubberHogsack3="!USER puts !TARGET in a rubber hogsack. Immobile and just an object to be used by others.";
    String strTargetRubberSleepsack1="You put !TARGET in a rubber sleepsack.",
            strTargetRubberSleepsack2="!USER puts you in a rubber sleepsack.",
            strTargetRubberSleepsack3="!USER puts !TARGET in a rubber sleepsack. Ideal way putting pets to bed, no way for them to cause problems or play with themselves.";

    String strTargetTextilePlush1="You put !TARGET in a thick textile plush suit.",
            strTargetTextilePlush2="!USER has put you in a thick textile plush suit.",
            strTargetTextilePlush3="!USER has put !TARGET in a thick textile plush suit.";
    String strTargetTextileBitchsuit1="You put !TARGET in a textile bitchsuit.",
            strTargetTextileBitchsuit2="!USER puts you in a textile bitchsuit.",
            strTargetTextileBitchsuit3="!USER puts !TARGET in a textile bitchsuit. Preparing them to walk on all 4 and service others.";
    String strTargetTextileHogsack1="You put !TARGET in a textile hogsack.",
            strTargetTextileHogsack2="!USER puts you in a textile hogsack.",
            strTargetTextileHogsack3="!USER puts !TARGET in a textile hogsack. Immobile and just an object to be used by others.";
    String strTargetTextileSleepsack1="You put !TARGET in a textile sleepsack.",
            strTargetTextileSleepsack2="!USER puts you in a textile sleepsack.",
            strTargetTextileSleepsack3="!USER puts !TARGET in a textile sleepsack. Ideal way putting pets to bed, no way for them to cause problems or play with themselves.";

    String strTargetOff1="You managed to pull the suit off from !TARGET's body.",
            strTargetOff2="!USER managed to pull the suit off from your body.",
            strTargetOff3="!USER managed to take off !TARGET's suit.";
    String strSoloOff1="You managed to pull the suit off from your body.",
            strSoloOff2="!USER managed to take off their suit. Someone must have forgot to secure it.";

    String strSuitIsNotOn="The suit is not on, silly.",strSuitIsOn="The suit is already on, silly.",
            strBadToyCantAddPointSelf="Bad toy! You can't add yourself points.",
            strCantManipulateDue2ToySuit="Can't manipulate your suit due to its a toy suit.",
            strCantManipulateAsTheyPermalocked="Can't manipulate your restraints as they permanently locked!",
            strCantManipulateAsAccessSet2Owner="Can't manipulate your suit due to access set to owner. Only your owner and sec-owners have access",
            strCantManipulateAsAccessSet2Public="Can't manipulate your suit due to access set to public. While public everyone else can access it except you.";

    String strRedSoloAsk="Do you wish to used safeword for suit?",
            strRedSoloYes="You have safeworded for suit.";
}
