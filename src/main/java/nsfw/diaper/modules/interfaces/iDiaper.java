package nsfw.diaper.modules.interfaces;

public interface iDiaper {
    interface solo {
        String cantManipulateDueToLockedBy = "Can't manipulate your diaper due to they locked by !LOCKER";
        String cantManipulateDueToAccessSetToCaretaker = "Can't manipulate your diaper due to access set to caretaker. Only your caretaker.";
        String cantTakeOffWhileLocked = "Can't take off the diaper while locked.",
                cantTakeOffWhileLocked_Public = "!WEARER struggled to take off the diaper from their body but failed due to its locked with a padlock.";
        String managedToTakeoff = "You managed to pull the diaper off from your body.",
                managedToTakeOff_Public = "!WEARER managed to take off their diaper.";
        String isNotonToTakeItoff = "The diaper is not on, silly.";
        String youPutOnClassic = "You decided to put on the classic diapers.",
                youPutOnClassic_Public = "!WEARER  decided to put on the classic diapers.";
        String youPutOnPink = "You decided to put on a pink version of classic diapers.",
                youPutOnPink_Public = "!WEARER decided to wear the pink version of classic diapers.";
        String youPutOnPlastic = "You decided to wear a plastic diapers. A plastic cover over the diapers, adding extra layer of holding back the liquids.",
                youPutOnPlastic_Public = "!WEARER decided to wear a plastic diapers. A plastic cover over the diapers, adding extra layer of holding back the liquids.";
        String youPutOnPlasticPink = "You decided to wear a pink plastic diapers. A plastic cover over the diapers, adding extra layer of holding back the liquids but with pink color.",
                youPutOnPlasticPink_Public = "!WEARER decided to wear a pink plastic diapers. A plastic cover over the diapers, adding extra layer of holding back the liquids but with pink color.";

        String youPutOnSwim = "You decided to wear swim diapers, protect against fecal incontinence while the person is swimming. ",
                youPutOnSwim_Public = "!WEARER decided to wear swim diapers, protect against fecal incontinence while the person is swimming. ";

        String youPutOnOvernight = "You decided to wear overnight briefs. The most absorbent type of briefs. bulkier in size to ensure to absord all the liquids.",
                youPutOnOvernight_Public = "!WEARER decided to wear overnight briefs. The most absorbent type of briefs. bulkier in size to ensure to absord all the liquids.";

        String youPutOnBriefsWithCloth = "You decided to wear briefs with cloth backing, simply means quiet and discreet diaper.",
                youPutOnBriefsWithCloth_Public = "!WEARER decided to wear briefs with cloth backing, simply means quiet and discreet diaper.";

        String youPutOnBriefsWithPlastic = "You decided to wear briefs with plastic backing, a more noise diaper.",
                youPutOnBriefsWithPlastic_Public = "!WEARER decided to wear briefs with plastic backing, a more noise diaper.";

        String youPutOnPullUps = "You decided to wear pull-ups, ideal for taking on and off to go to the bathroom. ",
                youPutOnPullUps_Public = "!WEARER decided to wear pull-ups, ideal for taking on and off to go to the bathroom. ";

        String youPutOnEevee = "You decided to wear light green diaper with eevee stickers on it. Tail wagging is mandatory.",
                youPutOnEevee_Public = "!WEARER decided to wear light green diaper with eevee stickers on it. Tail wagging is mandatory.";

        String youPutOnPaws = "You decided to wear nice-puffy paws diapers.",
                youPutOnPaws_Public = "!WEARER decided to wear nice-puffy paws diapers.";

        String youPutOnPikachu = "You decided to wear a light yellow diaper with pikachu stickers on it. Be warned, the diaper might shock you a bit if it gets wet.",
                youPutOnPikachu_Public = "!WEARER decided to wear a light yellow diaper with pikachu stickers on it. Be warned, the diaper might shock you a bit if it gets wet.";

        String youPutOnPeekabu = "You decided to wear nice-comfy peekabu diapers. Just look at the cute faces.",
                youPutOnPeekabu_Public = "!WEARER decided to wear nice-comfy peekabu diapers. Just look at the cute faces.";

        String youPutOnUnicorn = "You decided to wear light blue diaper with an unicorn sticker on it.",
                youPutOnUnicorn_Public = "!WEARER decided to wear light blue diaper with an unicorn sticker on it.";
    }
    interface target{
        String cantManipulateTheirsDueToLockedBy="Can't manipulate their diaper  do to they locked by !LOCKER.";
        String cantManipulateTheirsDueToTheirAccessSet="Can't manipulate their diaper due to their access setting.";

        String cantTakeOffTheirsWHileLocked="Can't take off !WEARER's diaper while locked.";

        String targetTakenOffDiaper="You managed to pull !WEARER's diaper off from their body.",
                targetTakenOffDiaper_Public="!USER managed to take off !WEARER's diaper";

        String noneIsOn="The diaper is not on.";

        String putOnClassic_Auth="You decided to put a classic diapers on !WEARER.",
                putOnClassic_Target="!USER decided to put a classic diapers on your body.",
                putOnClassic_Public="!USER decided to put a classic diapers on !WEARER.";

        String putOnPink_Auth="You decided to put a pink version of classic diapers on!WEARER.",
                putOnPink_Target="!USER decided to put a pink version of classic diapers on your body.",
                putOnPink_Public="!USER decided to put a pink version of classic diapers on!WEARER.";

        String putOnPlastic_Auth="You decided to put a plastic diapers on !WEARER. A plastic cover over the diapers, adding extra layer of holding back the liquids.",
                putOnPlastic_Target="!USER decided to put a plastic diapers on your body. A plastic cover over the diapers, adding extra layer of holding back the liquids.",
                putOnPlastic_Public="!USER decided to put a plastic diapers on !WEARER. A plastic cover over the diapers, adding extra layer of holding back the liquids.";

        String putOnPlasticPink_Auth="You decided to put a pink plastic diapers on !WEARER. A plastic cover over the diapers, adding extra layer of holding back the liquids but with pink color.",
                putOnPlasticPink_Target="!USER decided to put a pink plastic diapers on your body. A plastic cover over the diapers, adding extra layer of holding back the liquids but with pink color.",
                putOnPlasticPink_Public="!USER decided to put a pink plastic diapers on !WEARER. A plastic cover over the diapers, adding extra layer of holding back the liquids but with pink color.";

        String putOnSwim_Auth="You decided to put swim diapers on !WEARER. Protect against fecal incontinence while the person is swimming. ",
                putOnSwim_Target="!USER decided to put swim diapers on your body. Protect against fecal incontinence while the person is swimming. ",
                putOnSwim_Public="!USER decided to put swim diapers on !WEARER. Protect against fecal incontinence while the person is swimming. ";

        String putOnOvernight_Auth="You decided to put overnight briefs on !WEARER. The most absorbent type of briefs. bulkier in size to ensure to absord all the liquids." ,
                putOnOvernight_Target="!USER decided to put overnight briefs on your body. The most absorbent type of briefs. bulkier in size to ensure to absord all the liquids." ,
                putOnOvernight_Public="!USER decided to put overnight briefs on !WEARER. The most absorbent type of briefs. bulkier in size to ensure to absord all the liquids." ;

        String putOnBriefsWithCloth_Auth="You decided to put briefs with cloth backing on !WEARER. Simply means quiet and discreet diaper.",
                putOnBriefsWithCloth_Target="!USER decided to put briefs with cloth backing on your body. Simply means quiet and discreet diaper.",
                putOnBriefsWithCloth_Public="!USER decided to put briefs with cloth backing on !WEARER. Simply means quiet and discreet diaper.";

        String putOnBriefsWithPlastic_Auth="You decided to put briefs with plastic backing on !WEARER. A more noise diaper.",
                putOnBriefsWithPlastic_Target="!USER decided to put briefs with plastic backing on your body. A more noise diaper.",
                putOnBriefsWithPlastic_Public="!USER decided to put briefs with plastic backing on !WEARER. A more noise diaper.";

        String putOnPullUps_Auth="You decided to put pull-ups on !WEARER. Ideal for taking on and off to go to the bathroom. ",
                putOnPullUps_Target="!USER decided to put pull-ups on your body. Ideal for taking on and off to go to the bathroom. ",
                putOnPullUps_Public="!USER decided to put pull-ups on !WEARER. Ideal for taking on and off to go to the bathroom. ";

        String putOnEevee_Auth="You decided to put light green diaper with eevee stickers on !WEARER. Tail wagging is mandatory.",
                putOnEevee_Target="!USER decided to put light green diaper with eevee stickers on your body. Tail wagging is mandatory.",
                putOnEevee_Public="!USER decided to put light green diaper with eevee stickers on !WEARER. Tail wagging is mandatory.";

        String putOnPaws_Auth="You decided to put nice-puffy paws diapers on !WEARER.",
                putOnPaws_Target="!USER decided to put nice-puffy paws diapers on your body.",
                putOnPaws_Public="!USER decided to put nice-puffy paws diapers on !WEARER.";

        String putOnPikachu_Auth="You decided to put a light yellow diaper with pikachu stickers on !WEARER. Be warned, the diaper might shock you a bit if it gets wet.",
                putOnPikachu_Target="!USER decided to put a light yellow diaper with pikachu stickers on your body. Be warned, the diaper might shock you a bit if it gets wet.",
                putOnPikachu_Public="!USER decided to put a light yellow diaper with pikachu stickers on !WEARER. Be warned, the diaper might shock you a bit if it gets wet.";

        String putOnPeekabu_Auth="You decided to put nice-comfy peekabu diapers on !WEARER. Just look at the cute faces.",
                putOnPeekabu_Target="!USER decided to put nice-comfy peekabu diapers on your body. Just look at the cute faces.",
                putOnPeekabu_Public="!USER decided to put nice-comfy peekabu diapers on !WEARER. Just look at the cute faces.";

        String putOnUnicorn_Auth="You decided to put light blue diaper with an unicorn sticker on !WEARER.",
                putOnUnicorn_Target="!USER decided to put light blue diaper with an unicorn sticker on your body.",
                putOnUnicorn_Public="!USER decided to put light blue diaper with an unicorn sticker on !WEARER.";
    }


}
