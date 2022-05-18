package nsfw.diaper.modules.interfaces;

import models.lc.json.profile.lcJSONUserProfile;
import models.ll.colors.llColors;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;

public interface idDiaperPatreon {
    public static class patreonUser_239748154046545920{
        public static  long userID=239748154046545920L, serverID=818324625745510400L;
        public static String name ="plush",command="239748154046545920_plush",level="plush";

        public static  String strPutonSelf1="You unzips the back of the thick plush suit. Sliding your head into the built it puffy mask. Your eyes can just barely be seen through the tinted lenses. Slipping you arms and legs into the sleeves. You somehow finds a way to zip up the suit back up too. Good luck getting out with those big padded paws!",
                strPutOnSelf2="!WEARER unzips the back of their thick plush suit. Sliding their head into the built it puffy mask. Their eyes can just barely be seen through the tinted lenses. Slipping their arms and legs into the sleeves. !WEARER somehow finds a way to zip up the suit back up too. Good luck getting out with those big padded paws!";
        public  static  String strPutOnByOther1="You unzips the back of a big plush suit in front of !WEARER. Before !WEARER has time to react, their head is plunged into the puffy head of the suit along with their arms and legs stuffed into the matching sleeves. Their flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!",
        strPutOnByOther2="!USER unzips the back of a big plush suit in front of you. Before you have time to react, your head is plunged into the puffy head of the suit along with your arms and legs stuffed into the matching sleeves. Your flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!",
        strPutOnByOther3="!USER unzips the back of a big plush suit in front of !WEARER. Before !WEARER has time to react, their head is plunged into the puffy head of the suit along with their arms and legs stuffed into the matching sleeves. Their flustered eyes can just barely be seen through the tinted lenses. Look at how much of a struggle to move around! That must be one thick plush suit!";
        public static  void sendApplyingWearer4Diaper(lcJSONUserProfile userProfile, Member member, TextChannel textChannel){
            String fName="[sendApplyingWearer4Diaper]";
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
        public static  void sendApplyingUser4Diaper(lcJSONUserProfile userProfile, User wearer, Member member, TextChannel textChannel){
            String fName="[sendApplyingUser4Diaper]";
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
                desc= iRestraints.sStringReplacer(userProfile,member, strPutOnByOther1);
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
    }
}
