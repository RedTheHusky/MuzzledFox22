package restraints.in;

public interface iStraitjacket {

    String strCantTakeItOffWhileJacketed="Did you really think you could grab things with your arms so tightly bound to your chest in that straitjacket?";
    String strCantTakeItOffWhileJacketed_PetDenied="Did you really think you could grab things with your arms so tightly bound to your chest in that straitjacket?\n~~Your access level must be set to ask or protected.~~";
    String strCantGrabAnything="Did you really think you could grab things with your arms so tightly bound to your chest in that straitjacket?";
    String commandStrapArms="straparms",commandUnStrapArms="unstraparms",commandReverseStrapArms="reversestrap";
    String commandStrapCrotch="strapcrotch",commandUnStrapCrotch="unstrapcrotch",commandStrapMonoCrotch="monocrotch",commandStrapTripleCrotch="tripleCrotch";
    String commandStrap="strap",commandUnStrap="unstrap";
    String commandArms="arms",commandArm="arm",commandCrotch="crotch";
    String commandStitch="stitch",commandUnstitch="unstitc";
    String strJacketNotOn="Victim does not appear to be wearing a straitjacket... yet, silly.";
    String strSoloStiching1="Somehow you managed to sow up the straps on their straitjacket. It must have been hard.",
            strSoloStiching2="Somehow !TARGET managed to sow up the straps on their straitjacket. It must have been hard.";
    String strTargetStiching1="You stitches the straps shut on the straitjacket !TARGET is wearing.",
            strTargetStiching2="!USER stitches the straps shut on your straitjacket. It's not coming off for a long time now.",
            strTargetStiching3="!USER stitches the straps shut on the straitjacket !TARGET is wearing. It's not coming off for a long time now.";
    String strSoloUnStiching1="Somehow you managed to work the stitches for straitjacket loose.",
            strSoloUnStiching2="!TARGET manages to work the stitches in their straitjacket loose after a lot of squirming. It can be unstrapped now.";
    String strTargetUnStiching1="You carefully snips away the stitches on the straitjacket !TARGET is wearing.",
            strTargetUnStiching2="!USER carefully snips away the stitches on the straitjacket you're wearing.",
            strTargetUnStiching3="!USER carefully snips away the stitches on the straitjacket !TARGET is wearing. It can be unstrapped now.";

    String strRedSoloAsk="Do you wish to used safeword for straitjacket?",
            strRedSoloYes="You have safeworded for straitjacket.";
}
