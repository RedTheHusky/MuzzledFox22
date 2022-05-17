package restraints.in;

public interface iLock {
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
            strLocked ="Can't manipulate your blindfold as !LOCKER has the keys!",
            strAccessSet2Pet ="Can't manipulate your blindfold as only youyr owner and sec-owners can do that.",
            strAccessSet2Public ="Can't manipulate your blindfold as your access set to public. While public everyone else can access it except you.";

    String strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
            strCantDueLockedTarget="Can't manipulate their blindfold as it is locked by !LOCKER",
            strCantDueAccess="Can't manipulate their blindfold due to their access setting.";

    String strSoloOnDefault1="You locked your restraints!",
            strSoloOnDefault2="!USER locks their restraints";
    String strSoloOnGlue1="You glue yours restraints shut.",
            strSoloOnGlue2="!USER glues theirs restraints shut.";
    String strSoloOnTape1="You wrap duct tape around yourself.",
            strSoloOnTape2="USER wrap duct tape around themselves to secure themselves.";
    String strSoloOnExtra1="You put additional padlocks on yours restraints...a lot of extra padlocks.",
            strSoloOnExtra2="!USER puts additional padlocks on their restraints...a lot of extra padlocks.";
    String strSoloOnStitch1="You stitch shut the straps on your gear...don't think too hard about what you're stitching on a chastity cage.",
            strSoloOnStitch2="!USER stitches shut the straps on their gear...don't think too hard about what you're stitching on a chastity cage.";
    String strSoloOffDefault1="You unlocked your restraints!",
            strSoloOffDefault2="!USER unlocks their restraints!";
    String strSoloOffGlue1="You use solvent on restraints.",
            strSoloOffGlue2="!USER uses solvent on their restraints.";
    String strSoloOffTape1="You pull off duct tape around yourself.",
            strSoloOffTape2="!USER pulls off duct tape around themselves.";
    String strSoloOffExtra1="You undo each padlocks one at time.",
            strSoloOffExtra2="!USER undo each padlocks one at time.";
    String strSoloOffStitch1="You use scissors to undo your gears stitching.",
            strSoloOffStitch2="!USER uses scissors to undo their gears stitching.";

    String strTargetOnDefault1="You locked !TARGET's restraints!",
            strTargetOnDefault2="!USER locked your restraints!",
            strTargetOnDefault3="!USER locked !TARGET's restraints.";
    String strTargetOnGlue1="You glue pet's restraints shut.",
            strTargetOnGlue2="!USER glue yours restraints shut.",
            strTargetOnGlue3="!USER glue !TARGET restraints shut.";
    String strTargetOnTape1="You wrap duct tape around !TARGET's restraints to secure them.",
            strTargetOnTape2="!USER wraps duct tape around your restraints to secure them.",
            strTargetOnTape3="!USER wraps duct tape around !TARGET's restraints to secure them.";
    String strTargetOnExtra1="You put additional padlocks on !TARGET's restraints...a lot of extra padlocks.",
            strTargetOnExtra2="!USER puts additional padlocks on your restraints...a lot of extra padlocks.",
            strTargetOnExtra3="!USER puts additional padlocks on !TARGET's restraints...a lot of extra padlocks.";
    String strTargetOnStitch1="You stitch shut the straps on !TARGET's gear...don't think too hard about what you're stitching on a chastity cage.",
            strTargetOnStitch2="!USER stitches shut the straps on your gear...don't think too hard about what you're stitching on a chastity cage.",
            strTargetOnStitch3="!USER stitches shut the straps on !TARGET's gear...don't think too hard about what you're stitching on a chastity cage.";

    String strTargetOffDefault1="You unlocked !TARGET's restraints!",
            strTargetOffDefault2="!USER unlocked your restraints!",
            strTargetOffDefault3="!USER unlocked !TARGET's restraints.";
    String strTargetOffGlue1="You use solvent pet's restraints.",
            strTargetOffGlue2="!USER uses solvent yours restraints.",
            strTargetOffGlue3="!USER uses solvent !TARGET restraints.";
    String strTargetOffTape1="You pull off duct tape around !TARGET's restraints.",
            strTargetOffTape2="!USER pulls off duct tape around your restraints to secure them.",
            strTargetOffTape3="!USER pulls off duct tape around !TARGET's restraints to secure them.";
    String strTargetOffExtra1="You undo each !TARGET's padlocks one at time.",
            strTargetOffExtra2="!USER undo each your padlocks one at time.",
            strTargetOffExtra3="!USER undo each !TARGET's padlock one at time.";
    String strTargetOffStitch1="You use scissors to undo !TARGET's gears stitching.",
            strTargetOffStitch2="!USER uses scissors to undo your gears stitching.",
            strTargetOffStitch3="!USER uses scissors to undo !TARGET's gears stitching.";
    String commandDefault="default",commandLock="lock",commandUnlock="unlock",
        commandGlue="glue",commandCurse="curse",commandTape="tape",commandExtra="extra",commandStitch="stitch",commandWarden="warden";
}
