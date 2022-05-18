package nsfw.diaper.modules.interfaces;

import models.ll.colors.llColors_Pokemon;

public interface iSuit {
    interface  solo{

        String cantDueToLockedBy="Can't manipulate your onesie due to they locked by !LOCKER.";
        String cantDueToAccessSet="Can't manipulate your onesie due to access set to caretaker. Only your caretaker.";

        String cantTakeOffDueToLocked="Can't take off the onesie while locked.",
                cantTakeOffDueToLocked_public="!WEARER struggled to take off the onesie from their body but failed due to its locked with a padlock.";

        String takesOff="You managed to pull the onesie off from your body.",
        takesOff_public="!WEARER managed to take off their onesie.";

        String isnoton="The onesie is not on, silly.";

        String putOnEevee="You decided to put on an eevee onesie.",
                putOnEevee_public="!WEARER decided to put on an eevee onesie.";

        String putOnFlareon="You decided to put on a flareon onesie.",
                putOnFlareon_public="!WEARER decided to put on a flareon onesie.";

        String putOnJolteon="You decided to put on a jolteon onesie.",
                putOnJolteon_public="!WEARER decided to put on a jolteon onesie.";

        String putOnKitten="You decided to put on a kitten onesie.",
                putOnKitten_public="!WEARER decided to put on a kitten onesie.";

        String putOnPikachu="You decided to put on a pikachu onesie.",
                putOnPikachu_public="!WEARER decided to put on a pikachu onesie.";

        String putOnPuppy="You decided to put on a puppy onesie.",
        putOnPuppy_public="!WEARER decided to put on a puppy onesie.";

        String putOnUnicorn="You decided to put on an unicorn onesie.",
                putOnUnicorn_public="!WEARER decided to put on an unicorn onesie.";

        String putOnVaporeon="You decided to put on vaporeon onesie.",
                putOnVaporeon_public="!WEARER decided to put on vaporeon onesie.";

        String putOnWolf="You decided to put on wolf onesie.",
                putOnWolf_public="!WEARER decided to put on wolf onesie.";

        String putOnSylveon="You decide to put on a sylveon onesie. A cute white-pink uniform with blue tips at the sleeves ends." ,
                putOnSylveon_public="!WEARER decides to put on a sylveon onesie. A cute white-pink uniform with blue tips at the sleeves ends.";
    }
    interface target{
        String cantDueToLockedBy="Can't manipulate their onesie  do to they locked by !LOCKER";
        String cantDueToacessSet="Can't manipulate their onesie due to their access setting.";

        String cantDueToLocked="Can't take off the onesie while locked.";

        String pulloff_auth="You pull the onesie off from !WEARER.",
                pullloff_public="!USER pull the onesie off from !WEARER.";

        String isnoton="The onesie is not on";

        String putonEevee_auth="You put !WEARER in an eevee onesie.",
                putonEevee_public="!USER puts !WEARER in an eevee onesie.";

        String putonFlareon_auth="You put !WEARER in a flareon onesie.",
                putonFlareon_public="!USER puts !WEARER in a flareon onesie.";

        String putonJolteon_auth="You put !WEARER in a jolteon onesie.",
                putonJolteon_public="!USER puts !WEARER in a jolteon onesie.";

        String putonKitten_auth="You put !WEARER in a kitten onesie.",
                putonKitten_public="!USER puts !WEARER in a kitten onesie.";

        String putonPikachu_auth="You put !WEARER in a pikachu onesie.",
                putonPikachu_public="!USER puts !WEARER in a pikachu onesie.";

        String putonPuppy_auth="You put !WEARER in a puppy onesie.",
        putonPuppy_public="!USER puts !WEARER in a puppy onesie.";

        String putonUnicorn_auth="You put !WEARER in an unicorn onesie.",
        putonUnicorn_public="!USER puts !WEARER in an unicorn onesie.";

        String putonVaporeon_auth="You put !WEARER in an vaporeon onesie.",
        putonVaporeon_public="!USER puts !WEARER in an vaporeon onesie.";

        String putonWolf_auth="You put !WEARER in a wolf onesie.",
        putonWolf_public="!USER puts !WEARER in a wolf onesie.";

        String putonSylveon_auth="You put !WEARER in a sylveon onesie. A cute white-pink uniform with blue tips at the sleeves ends." ,
        putonSylveon_public="!USER puts !WEARER in a sylveon onesie. A cute white-pink uniform with blue tips at the sleeves ends.";
    }
}
