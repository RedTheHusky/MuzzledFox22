package restraints.in;

public interface iEncase {
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
    strLocked ="Can't manipulate your encasement as !LOCKER has the keys!",
    strAccessSet2Pet ="Can't manipulate your encasement as only youyr owner and sec-owners can do that.",
    strAccessSet2Public ="Can't manipulate your encasement as your access set to public. While public everyone else can access it except you.",
    strIsAlreadyOn ="The encasement is already on, silly; What are you going to do with 2 of them!?",
    strPutBlindolfOn="You put a encasement over your eyes.",
    srtPutBlindfoldOnMention="!USER has put a encasement over their eyes.",
    strPutPannel="You put a panelled encasement over your eyes, loosing your sight in the process.",
    strPutPannelMention="!USER has put panelled encasement over their eyes, loosing their sight in the process.",
    strPutContacts="You put contacts into your eyes, perfectly white, yet not letting so much as a beam of light in!",
    strPutContactsMention="!USER has put contacts into their eyes, perfectly white, yet not letting so much as a beam of light in!",
    strTakeOff="You managed to remove the encasement from your eyes, you can use your eyes again -- after they adjust that is!",
    strTakeOffMention="!USER managed to take off their encasement. Someone must have forgot to secure it.",
    strNotOn="The encasement is not on, silly.",
    strCantTakeOffDue2Jacket="!USER attempted to take off their encasement, but thankfully someone was thoughtful enough to tighten their straitjacket enough to prevent it.",
    strCantTakeOffDueLocked="!USER struggled to take off their encasement but failed because of the big padlock in the way!";

    String strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
    strCantDueLockedTarget="Can't manipulate their encasement as it is locked by !LOCKER",
    strCantDueAccess="Can't manipulate their encasement due to their access setting.";

    String strSoloRelease1="You release yourself from encasement.",
            strSoloRelease2="!USER release themselves from encasement.";
    String strSoloMummy1="You somehow manage yourself to mummify yourself like an egyptian mummy.",
            strSoloMummy2="!USER somehow manages to mummify themselves like an egyptian mummy.";
    String strSoloGibbet1="You somehow manage to lock yourself in a form-fitting cage.",
            strSoloGibbet2="!USER somehow manages to lock themselves in a form-fitting cage, putting themselves on display.";
    String strSoloRubber1="You slowly start coating yourself in rubber.",
            strSoloRubber2="!USER slowly starts coating themselves in rubber, turning themselves into a shinny statue.";
    String strSoloGlass1="You put yourself into a glass box for display.",
            strSoloGlass2="!USER puts themselves into a glass box for display.";
    String strSoloCage1="You put yourself into a cage.",
            strSoloCage2="!USER puts themselves into a cage, suitable for showing off at a slave market.";
    String strSoloPole1="You somehow manage to tie yourself to aa pole.",
            strSoloPole2="!USER somehow manages to tie themselves to aa pole with lots of rope.";
    String strSoloVacbed1="You somehow manage to place yourself in a vacbed, slowly pulling out the air, encasing you in airtight skin.",
            strSoloVacbed2="!USER somehow manage to place themselves in a vacbed, pumping out the air, the airtight skin getting tighter and tighter around !USER's body";

    String strTargetRelease1="You release !TARGET from encasement",
            strTargetRelease2="!USER releases you from encasement",
            strTargetRelease3="!USER releases !TARGET from encasement";
    String strTargetMummy1="You mummify !TARGET.",
            strTargetMummy2="!USER mummifies you.",
            strTargetMummy3="!USER mummifies !TARGET, ancient egyptian style.";
    String strTargetGibbet1="You lock !TARGET in a form-fitting cage.",
            strTargetGibbet2="!USER locks you in a form-fitting cage.",
            strTargetGibbet3="!USER locks !TARGET in a form-fitting cage, putting them on display for all to see.";
    String strTargetRubber1="You coat !TARGET in rubber.",
            strTargetRubber2="!USER coats you in rubber.",
            strTargetRubber3="!USER coats !TARGET in rubber, turning them into a shiny statue.";
    String strTargetGlass1="You put !TARGET on display.",
            strTargetGlass2="!USER puts !TARGET on display.",
            strTargetGlass3="!USER puts !TARGET on display in a glass box.";
    String strTargetCage1="You put !TARGET in a cage.",
            strTargetCage2="!USER puts you in a cage.",
            strTargetCage3="!USER puts !TARGET in a cage, suitable for showing off at the slave market.";
    String strTargetPole1="You tie !TARGET to a large pole.",
            strTargetPole2="!USER ties you to a large pole.",
            strTargetPole3="!USER ties !TARGET to a large pole with lots of rope.";
    String strTargetVacbed1="You place !TARGET in a vacbed, pumping out the air.",
            strTargetVacbede2="!USER places you in a vacbed, pumping out the air.",
            strTargetVacbed3="!USER places !TARGET in a vacbed, pumping out the air, the airtight skin getting tighter and tighter around !TARGET's body.";

    String commandRelease="release",commandMummy="mummy",commandGibbet="gibbet",commandRubber="rubber",commandGlass="glass",commandCage="cage",commandPole="pole",commandVacbed="vacbed";
    String strYouCantAccessYourOwnRestraints="You can't access your restraints while encased!";

    String strRedSoloAsk="Do you wish to used safeword for encase?",
            strRedSoloYes="You have safeworded for encase.";
}
