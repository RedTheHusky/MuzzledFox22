package restraints.in;

public interface iBlindfold {
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
    strLocked ="Can't manipulate your blindfold as !LOCKER has the keys!",
    strAccessSet2Pet ="Can't manipulate your blindfold as only your owner and sec-owners can do that.",
    strAccessSet2Public ="Can't manipulate your blindfold as your access set to public. While public everyone else can access it except you.",
    strIsAlreadyOn ="The blindfold is already on, silly; What are you going to do with 2 of them!?",
    strPutBlindolfOn="You put a blindfold over your eyes.",
    srtPutBlindfoldOnMention="!USER has put a blindfold over their eyes.",
    strPutPannel="You put a panelled blindfold over your eyes, loosing your sight in the process.",
    strPutPannelMention="!USER has put panelled blindfold over their eyes, loosing their sight in the process.",
    strPutContacts="You put contacts into your eyes, perfectly white, yet not letting so much as a beam of light in!",
    strPutContactsMention="!USER has put contacts into their eyes, perfectly white, yet not letting so much as a beam of light in!",
    strTakeOff="You managed to remove the blindfold from your eyes, you can use your eyes again -- after they adjust that is!",
    strTakeOffMention="!USER managed to take off their blindfold. Someone must have forgot to secure it.",
    strNotOn="The blindfold is not on, silly.",
    strCantTakeOffDue2Jacket="!USER attempted to take off their blindfold, but thankfully someone was thoughtful enough to tighten their straitjacket enough to prevent it.",
    strCantTakeOffDueLocked="!USER struggled to take off their blindfold but failed because of the big padlock in the way!";

    String strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
    strCantDueLockedTarget="Can't manipulate their blindfold as it is locked by !LOCKER",
    strCantDueAccess="Can't manipulate their blindfold due to their access setting.";

    String strPutTargetBlindfold1="You put blindfold over !TARGET's eyes.",
            strPutTargetBlindfold2="!USER places the blindfold over your eyes.",
            strPutTargetBlindfold3= "!USER places blindfold over !TARGET's eyes'";

    String strPutTargetPannel1="You put a panelled blindfold over !TARGET eyes, loosing their sight in the process.",
    strPutTargetPannel2="!TARGET puts a panelled blindfold over your eyes, loosing your sight in the process.",
    strPutTargetPannel3="!USER has put a panelled blindfold over !TARGET eyes, loosing their sight in the process.";


    String strPutTargetContacts1="You put contacts into !TARGET eyes, perfectly white, yet not letting so much as a beam of light in!",
            strPutTargetContacts2="!TARGET puts contacts into your eyes, perfectly white, yet not letting so much as a beam of light in!",
            strPutTargetContacts3="!USER has put contacts into !TARGET eyes, perfectly white, yet not letting so much as a beam of light in!";

    String strTakeOffTarget1="You pull the blindfold from !TARGET's head.",
            strTakeOffTarget2="!USER pulls the blindfold from your head.",
            strTakeOffTarget3="!USER pulls the blindfold from !TARGET's head.";

    String strRedSoloAsk="Do you wish to used safeword for blindfold?",
            strRedSoloYes="You have safeworded for blindfold.";

}
