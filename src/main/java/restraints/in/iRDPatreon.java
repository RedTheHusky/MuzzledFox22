package restraints.in;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import nsfw.diaper.idDiaperPatreon;
import org.apache.log4j.Logger;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.GAGTYPES;
import restraints.models.enums.MITTSLEVELS;
import restraints.models.entity.entityRDUserProfile;

import java.util.Arrays;

public interface iRDPatreon {
    public static class patreonUser_239748154046545920_mitts {
        public static  long userID=239748154046545920L, serverID=818324625745510400L;
        public static String name ="ducky",command="239748154046545920_ducky",level="ducky";
        public static  String denied2TakeOffMitts2="!WEARER tries to take off their mittens but they squeak uselessly against the buckles!",denied2TakeOffMitts1="You trie to take off your mittens but they squeak uselessly against the buckles!";
        public static String strDeniedMessage1="You mittens bounce off with an adorable squeak",strDeniedMessage2="!WEARER mittens bounce off with an adorable squeak",
                strApplyMittsUser3="!USER straps an adorable pair of ducky themed mittens onto !WEARER, squishing one to show off it's built in squeaky toy.",
                strApplyMittsUser1="You straps an adorable pair of ducky themed mittens onto !WEARER, squishing one to show off it's built in squeaky toy.",
                strApplyMittsUser2="!USER straps an adorable pair of ducky themed mittens onto you, squishing one to show off it's built in squeaky toy.",
                strApplyMittsWearer2="!WEARER straps an adorable pair of ducky themed mitts over their own hands, clapping them together to show off the built in squeaky toys.",
                strApplyMittsWearer1="You strap an adorable pair of ducky themed mitts over your own hands, clapping them together to show off the built in squeaky toys.";
        public static String urlImageFile="https://cdn.discordapp.com/attachments/820068758851813416/820075266386427924/1600847665.theawsomeman1_finsihed_bunny-1.jpg";
        public static  void sendDeniedMessage(Member member, String title){
            String fName="[sendDeniedMessage]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".patreon message:"+ patreonUser_239748154046545920_mitts.name);
                lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),title, patreonUser_239748154046545920_mitts.strDeniedMessage1, llColors.llColorYellow_Canary);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendDeniedMessage(Member member, String title,TextChannel textChannel){
            String fName="[sendDeniedMessage]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".patreon message:"+ patreonUser_239748154046545920_mitts.name);
                lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),title, patreonUser_239748154046545920_mitts.strDeniedMessage1, llColors.llColorYellow_Canary);
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title, patreonUser_239748154046545920_mitts.strDeniedMessage2.replaceAll("!WEARER",member.getAsMention()),llColors.llColorYellow_Canary);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void cantTakeOffAsItsLocked(Member member, String title,TextChannel textChannel){
            String fName="[cantTakeOffAsItsLocked]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".patreon message:"+ patreonUser_239748154046545920_mitts.name);
                lsMessageHelper.lsSendQuickEmbedMessage(member.getUser(),title, patreonUser_239748154046545920_mitts.denied2TakeOffMitts1, llColors.llColorYellow_Canary);
                lsMessageHelper.lsSendQuickEmbedMessage(textChannel,title, patreonUser_239748154046545920_mitts.denied2TakeOffMitts2.replaceAll("!WEARER",member.getAsMention()),llColors.llColorYellow_Canary);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  boolean isValid(lcGlobalHelper gGlobal, Member member){
            String fName="[isValid]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                lcJSONUserProfile userProfile= iRestraints.getUserProfile(gGlobal,member, false);
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return  false;
                }
                logger.info(fName + ".userProfile.jsonUser="+userProfile.jsonObject.toString());
                JSONObject mitts=userProfile.jsonObject.getJSONObject(iRestraints.nMitts);
                boolean on=mitts.getBoolean(iRestraints.nOn);
                logger.info(fName + ".on="+on);
                if(!on){
                    logger.info(fName + ".not on>false");
                    return  false;
                }
                if(!mitts.has(iRestraints.nLevel)){
                    logger.info(fName + ".no levels>false");
                    return false;
                }
                if(mitts.getString(iRestraints.nLevel).equalsIgnoreCase(name)){
                    logger.info(fName + ".found level>true");
                    return true;
                }
                logger.info(fName + ".incorrect level>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        public static  void sendApplyingWearer(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingWearer]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc= iRestraints.sStringReplacer(userProfile,member, patreonUser_239748154046545920_mitts.strApplyMittsWearer1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, patreonUser_239748154046545920_mitts.strApplyMittsWearer2);
                embedBuilder.setDescription(desc);
                embedBuilder.setImage(urlImageFile);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendApplyingUser(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingUser]";
            Logger logger = Logger.getLogger(iRDPatreon.class);
            try {
                logger.info(fName + ".wearer="+wearer.getName()+"#"+wearer.getDiscriminator()+"("+wearer.getId()+")");
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member, patreonUser_239748154046545920_mitts.strApplyMittsUser1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, patreonUser_239748154046545920_mitts.strApplyMittsUser2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(wearer,embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, patreonUser_239748154046545920_mitts.strApplyMittsUser3);
                embedBuilder.setDescription(desc);
                embedBuilder.setImage(urlImageFile);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
    public static class patreonUser_239748154046545920_suit{
        public static  long userID=239748154046545920L, serverID=818324625745510400L;
        public static String name ="plush",command="239748154046545920_plush",level="(vip) plush";

        public static  String strPutonSelf1="You unzips the back of the thick plush suit. Sliding your head into the built it puffy mask. Your eyes can just barely be seen through the tinted lenses. Slipping you arms and legs into the sleeves. You somehow finds a way to zip up the suit back up too. Good luck getting out with those big padded paws!",
                strPutOnSelf2="!WEARER unzips the back of their thick plush suit. Sliding their head into the built it puffy mask. Their eyes can just barely be seen through the tinted lenses. Slipping their arms and legs into the sleeves. !WEARER somehow finds a way to zip up the suit back up too. Good luck getting out with those big padded paws!";
        public  static  String strPutOnByOther1="You unzips the back of a big plush suit in front of !WEARER. Before !WEARER has time to react, their head is plunged into the puffy head of the suit along with their arms and legs stuffed into the matching sleeves. Their flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!",
                strPutOnByOther2="!USER unzips the back of a big plush suit in front of you. Before you have time to react, your head is plunged into the puffy head of the suit along with your arms and legs stuffed into the matching sleeves. Your flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!",
                strPutOnByOther3="!USER unzips the back of a big plush suit in front of !WEARER. Before !WEARER has time to react, their head is plunged into the puffy head of the suit along with their arms and legs stuffed into the matching sleeves. Their flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!";


        public static  void sendApplyingWearer4RD(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingWearer4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member,strPutonSelf1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member,strPutOnSelf2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendApplyingUser4RD(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingUser4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".wearer="+wearer.getName()+"#"+wearer.getDiscriminator()+"("+wearer.getId()+")");
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member, strPutOnByOther1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strPutOnByOther2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(wearer,embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strPutOnByOther3);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  lcJSONUserProfile applyAdditionalRestraints(lcJSONUserProfile userProfile){
            String fName="[applyAdditionalRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                userProfile.putFieldEntry(iRestraints.nMitts,iRestraints.nOn,true);userProfile.putFieldEntry(iRestraints.nMitts,iRestraints.nLevel, MITTSLEVELS.General.getName());
                userProfile.putFieldEntry(iRestraints.nGag,iRestraints.nOn,true);userProfile.putFieldEntry(iRestraints.nGag,iRestraints.nLevel,GAGLEVELS.Extreme.getName());
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }
        public static entityRDUserProfile applyAdditionalRestraints(entityRDUserProfile userProfile){
            String fName="[applyAdditionalRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                userProfile.cMITTS.setOn(true);userProfile.cMITTS.setLevel(MITTSLEVELS.General);
                userProfile.cGAG.setOn(true);userProfile.cGAG.setLevel(GAGLEVELS.Extreme);
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }
    }
    public static class patreonUser_118178462149115913_suit{
        public static  long userID=118178462149115913L, serverID=818324625745510400L;
        public static String name ="sleeper",command="118178462149115913_sleeper",level="sleeper";
        public static String  strApplyUser3="!USER zips !WEARER into a very thick fleece footed sleeper. Three warm layers of soft, pink fleece puffs out their form, making their proportions much more babyish, and the attached, thickly padded booties make walking a bit of a chore. A zipper through the legs allows for changes without having to take the sleeper off.",
                strApplyUser1="You zip !WEARER into a very thick fleece footed sleeper.",
                strApplyUser2="You get zipped up by !USER into a very thick fleece footed sleeper. Three warm layers of soft, pink fleece puffs out their form, making their proportions much more babyish, and the attached, thickly padded booties make walking a bit of a chore. A zipper through the legs allows for changes without having to take the sleeper off.",
                strApplyWearer2="!USER zips themselves into a very thick fleece footed sleeper. Three warm layers of soft, pink fleece puffs out their form, making their proportions much more babyish, and the attached, thickly padded booties make walking a bit of a chore. A zipper through the legs allows for changes without having to take the sleeper off.",
                strApplyWearer1="You zip yourself into a very thick fleece footed sleeper. Three warm layers of soft, pink fleece puffs out their form, making their proportions much more babyish, and the attached, thickly padded booties make walking a bit of a chore. A zipper through the legs allows for changes without having to take the sleeper off.";
        public static  void sendApplyingWearer4RD(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingWearer4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member,strApplyWearer1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member,strApplyWearer2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendApplyingUser4RD(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingUser4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".wearer="+wearer.getName()+"#"+wearer.getDiscriminator()+"("+wearer.getId()+")");
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member, strApplyUser1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strApplyUser2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(wearer,embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strApplyUser3);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  lcJSONUserProfile applyAdditionalRestraints(lcJSONUserProfile userProfile){
            String fName="[applyAdditionalRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                userProfile.putFieldEntry(iRestraints.nMitts,iRestraints.nOn,true);userProfile.putFieldEntry(iRestraints.nMitts,iRestraints.nLevel,MITTSLEVELS.General.getName());
                userProfile.putFieldEntry(iRestraints.nGag,iRestraints.nOn,true);userProfile.putFieldEntry(iRestraints.nGag,iRestraints.nLevel, GAGLEVELS.Loose.getName());userProfile.putFieldEntry(iRestraints.nGag,iRestraints.nType, GAGTYPES.Paci.getName());
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }
        public static  entityRDUserProfile applyAdditionalRestraints(entityRDUserProfile userProfile){
            String fName="[applyAdditionalRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                userProfile.cMITTS.setOn(true);userProfile.cMITTS.setLevel(MITTSLEVELS.General);
                userProfile.cGAG.setOn(true);userProfile.cGAG.setLevel(GAGLEVELS.Loose);userProfile.cGAG.setType(GAGTYPES.Paci);
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }
    }
    public static class patreonUser_118178462149115913_encase{
        public static  long userID=118178462149115913L, serverID=818324625745510400L;
        public static String nameCement ="cement",commandCement="118178462149115913_cement",levelCement="cement";
        public static String nameGlue ="glue",commandGlue="118178462149115913_glue",levelGlue="glue";
        public static String  strCementApplyUser3="!USER dumps a barrel of cement of sloppy gray cement over !VICTIM's head, quickly covering them from head to toe, and instantly hardening into stone. They're frozen in place, stuck holding their pose, unable to move even a single muscle.",
                strCementApplyUser1="!You dump a barrel of cement of sloppy gray cement over !VICTIM's head, quickly covering them from head to toe, and instantly hardening into stone. They're frozen in place, stuck holding their pose, unable to move even a single muscle",
                strCementApplyUser2="!USER dumps a barrel of cement of sloppy gray cement over yours head, quickly covering you from head to toe, and instantly hardening into stone. You frozen in place, stuck holding your pose, unable to move even a single muscle.",
                strCementApplyWearer2="!USER dumps a barrel of cement of sloppy gray cement over themselves head, quickly covering them from head to toe, and instantly hardening into stone. They're frozen in place, stuck holding their pose, unable to move even a single muscle.",
                strCementApplyWearer1="You dump a barrel of cement of sloppy gray cement over yours head, quickly covering you from head to toe, and instantly hardening into stone. You frozen in place, stuck holding your pose, unable to move even a single muscle.";
        public static  void sendApplyingCementWearer4RD(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingCementWearer4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member,strCementApplyWearer1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member,strCementApplyWearer2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendApplyingCementUser4RD(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingCementUser4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".wearer="+wearer.getName()+"#"+wearer.getDiscriminator()+"("+wearer.getId()+")");
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member, strCementApplyUser1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strCementApplyUser2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(wearer,embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strCementApplyUser3);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  entityRDUserProfile applyAdditionalCementRestraints(entityRDUserProfile userProfile){
            String fName="[applyAdditionalCementRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }

        public static String  strGlueApplyUser3="!USER grabs a hose and sprays !VICTIM down with a thick stream of heavy rubber glue. It covers their entire body, sticking them firmly in place. No matter how hard they pull and stretch at the glue, it pulls them right back down. They're stuck!",
                strGlueApplyUser1="You grab a hose and sprays !VICTIM down with a thick stream of heavy rubber glue. It covers their entire body, sticking them firmly in place. No matter how hard they pull and stretch at the glue, it pulls them right back down. They're stuck!",
                strGlueApplyUser2="!USER grabs a hose and sprays you down with a thick stream of heavy rubber glue. It covers your entire body, sticking you firmly in place. No matter how hard you pull and stretch at the glue, it pulls right back down. You're stuck!",
                strGlueApplyWearer2="!USER grabs a hose and sprays themselves down with a thick stream of heavy rubber glue. It covers their entire body, sticking them firmly in place. No matter how hard they pull and stretch at the glue, it pulls them right back down. They're stuck!",
                strGlueApplyWearer1="You grab a hose and spray yourself down with a thick stream of heavy rubber glue. It covers your entire body, sticking them firmly in place. No matter how hard you pull and stretch at the glue, it pulls right back down. You're stuck!";
        public static  void sendApplyingGlueWearer4RD(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingCGlueWearer4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member,strGlueApplyWearer1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member,strGlueApplyWearer2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  void sendApplyingGlueUser4RD(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingGlueUser4RD]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                logger.info(fName + ".wearer="+wearer.getName()+"#"+wearer.getDiscriminator()+"("+wearer.getId()+")");
                logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
                if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                    logger.info(fName + ".invalid>false");
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorYellow_Canary);
                String desc="";
                desc=iRestraints.sStringReplacer(userProfile,member, strGlueApplyUser1);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(member.getUser(),embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strGlueApplyUser2);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(wearer,embedBuilder);
                desc=iRestraints.sStringReplacer(userProfile,member, strGlueApplyUser3);
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(textChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public static  entityRDUserProfile applyAdditionalGlueRestraints(entityRDUserProfile userProfile){
            String fName="[applyAdditionalGlueRestraints]";
            Logger logger = Logger.getLogger(idDiaperPatreon.class);
            try {
                return  userProfile;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  userProfile;
            }
        }
    }
    public static class patreonUser_365946661442158603_tickleaddon{
        public static  long userID=365946661442158603L;
        public  static  final String keyFeatherShow="feather_show",keyFeather="feather";
        public static final String com_on="on",com_off="off",com_show="show",com_hide="hide",com_tickle="tickle",com_status="status", com_toggle="toggle", com_toggle_show="toggle_show";

        public static boolean lsRegisteredMember(Member member){
            String fName="lsRegisteredMember.";Logger logger = Logger.getLogger(iRDPatreon.class);
            try{
                boolean result=member.getIdLong()==userID;
                logger.warn(fName+"result="+result);
                return  result;
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                return false;
            }
        }
    }
}
