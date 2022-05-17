package restraints.in;

public interface iCollar {
    String strCantPermalock="Can't manipulate restraints as they are permanently locked!",
            strCantSuit="Can't manipulate your collar as its part of the suit.",
            strCantLocked="Can't manipulate your collar as it is locked by !LOCKER",
            strCantAccess2Pet="Can't manipulate your collar as only your owner and sec-owners can do that.",
            strCantAccess2Public="Can't manipulate your collar as your access set to public. While public everyone else can access it except you.",
            strIsOn="The collar is already on, silly.",
            strIsOff="The collar is not on.";

   String strPutOnLeather1="You put a leather collar around your neck. Good boy, all dressed and ready for walkies.",
           strPutOnLeather12="!USER has put a leather collar around your neck. Good boy, all dressed and ready for walkies.",
           strPutOnLatex1="You put a latex collar around your neck. Time to go out to the night club and have it used for what it was designed for.",
           strPutOnLatex2="!USER has put a latex collar around their neck. Time to go out to the night club and have it used for what it was designed for.",
           strPutOnRubber1="You put a rubber collar around your neck. Ready to serve and follow orders.",
           strPutOnRubber2="!USER has put a rubber collar around their neck. Ready to serve and follow orders.",
           strPutOnChain1="You put a chain collar around your neck. Showing off the desire to be chained to something or somebody.",
           strPutOnChain2="!USER has put a chain collar around their neck. Showing off the desire to be chained to something or somebody.",
           strPutOnIron1="You put an iron collar around your neck, like slaves used to wear while they were being sold, owned and compelled to obey.",
           strPutOnIron2="!USER has put an iron collar around their neck, like slaves used to wear while they were being sold, owned and compelled to obey.";


    String strCantDueJacket="!USER attempted to take off their collar but all they can manage to do is struggle in the straitjacket.",
    strCantDueLocked="!USER struggled to take off the collar but failed! Thankfully someone was thoughtful enough to stick a padlock through the buckle.",
    strTakeOff1="You managed to pull the collar off.",
    strTakeOff2="!USER managed to take off their collar. Someone must have forgot to secure it...",
    strNotOnSIlly="The collar is not on, silly.";

    String strTargetCantPermalock="Can't manipulate !TARGET's restraints as they are permanently locked!",
    strTargetCantDueSuit="Can't manipulate their collar as its part of the suit.",
    strTargetCantLocked="Can't manipulate their collar as it is locked by !LOCKER",
    strTargetCantSettings="Can't manipulate their collar due to their access setting.";

    String strTargetPutLeather1="You put a leather collar !TARGET's neck, now they're ready for a walk!",
    strTargetPutLeather2="!USER puts a leather collar around your neck. Good boy, time for walkies.",
    strTargetPutLeather3="!USER puts a leather collar around !TARGET's neck, now they're ready for a walk!",
    strTargetPutLatex1="You put a latex collar !TARGET's neck. Time to go out to the night club and have it used for what it was designed for.",
    strTargetPutLatex2="!USER puts a latex collar around your neck. Time to go out to the night club and have it used for what it was designed for.",
    strTargetPutLatex3="!USER puts a latex collar around !TARGET's neck. Time to go out to the night club and have it used for what it was designed for.",
    strTargetPutRubber1="You put a rubber collar !TARGET's neck. Ready to serve and follow orders.",
    strTargetPutRubber2="!USER puts a rubber collar around your neck. Ready to serve and follow orders.",
    strTargetPutRubber3="!USER puts a rubber collar around !TARGET's neck. Ready to serve and follow orders.",
    strTargetPutChain1="You put a chain collar !TARGET's neck. Showing off the desire to be chained to something or somebody.",
    strTargetPutChain2="!USER puts a chain collar around your neck. Showing off the desire to be chained to something or somebody.",
    strTargetPutChain3="!USER puts a chain collar around !TARGET's neck. Showing off the desire to be chained to something or somebody.",
    strTargetPutIron1="You put a iron collar !TARGET's neck, like slaves used to wear while they were being sold, owned and compelled to obey.",
    strTargetPutIron2="!USER puts a iron collar around your neck, like slaves used to wear while they were being sold, owned and compelled to obey.",
    strTargetPutIron3="!USER puts a iron collar around !TARGET's neck, like slaves used to wear while they were being sold, owned and compelled to obey.",
    strTargetPull1="You remove the collar from !TARGET's neck.",
    strTargetPull2="!USER removes the collar from your neck.",
    strTargetPull3="!USER removes the collar from !TARGET's neck.";

    String gCommandFilePath=iRestraints.gFileMainPath+"collar/";
    String gCommandFileMainPath =gCommandFilePath+"menuMain.json",
            gCommandFileShockPath =gCommandFilePath+"menuShock.json";
    String gTitleShockSystem ="Collar shock system";

    String strRedSoloAsk="Do you wish to used safeword for collar?",
            strRedSoloYes="You have safeworded for collar.";

}
