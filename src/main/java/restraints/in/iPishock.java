package restraints.in;

import models.lc.json.profile.lcJSONUserProfile;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iPishock {
    int optionBeep=2,optionVibrate=1,optionShock=0;
    int maxDuration=15,maxItensity=100;
    int defaultDuration=5,defaultItensity=10;
    int maxMembers=10,normalLimitMembers=3,patreonLimitMembers=10;
    int maxShockersCount=10;
    int secondsBeforeDelete=30;
    String strBeep="beep",strVibrate="vibrate",strShock="shock";
    String strCantSet2Pet="Can't manipulate your remote as only your owner and sec-owners has access",
            strCantSet2Public="Can't manipulate your remote as your access is set to public. While public everyone else can access it except you.",
            strCantJacket1 ="How do you think you can use the remote while youre paws are safely secured in a straitjacket?",
            strCantJacket2="USER tries to grab the remote but their straitjacketed paws prevent it.",
            strCantMitts1="Don't be so silly! You can't manage to grab anything with those mitted paws!",
            strCantMitts2="!USER tries to grab the remote but their mitted paws prevent it.",
            strTargetCantDueAccess="Can't manipulate their shocker due to their access setting.",
            strTargetCantDueAskMode="Can't manipulate their shocker as it is in ask mode.",
            strCantDueNotenabled="Can't send any actions as the shocker not enabled!",
            strTargetCantDueNotenabled="Can't send any actions to !TARGET's shocker as its not enabled!",
            strNoMentionedMembers="No members mentioned!",
            strListIsEmpty="List is empty!",
            strListMaxReached="List reached max members count. Max 10 members allowed to be added.",
            strListMaxReachedNoPatreon="List reached max members count. Max 3 members allowed non-patreon members, Max 10 for patreon members.",
            strCantUseDueList="Can't send pishock command to !TARGET due to white&black list configuration.";
    String strWhiteListUpdated="Whitelist updated.",
            strWhiteListFailed2Update="Failed to update whitelist.",
            strWhiteListCleared="Whitelist cleared.",
            strBlackListUpdated="Blacklist updated.",
            strBlackListFailed2Update="Failed to update blacklist.",
            strBlackListCleared="Blacklist cleared.";
    String strEnabled="You've enabled PiSHock, get ready to be shocked!",
            strDisabled="You've disabled PiShock",
            strReset="You've reset PiSHock entry",
            strTargetEnabled1="You enabled !TARGET's PiShock, Maybe someone should tell them to get ready for shocks.",
            strTargetEnabled2="!USER enabled your PiSHock, get ready for shocks.",
            strTargetDisabled1="You !TARGET's disabled PiShock",
            strTargetDisabled2="!USER disabled PiShock",
            strTargetReset1="You reset !TARGET's PiSHock entry",
            strTargetReset2="!USER reset PiSHock entry";
    String strDoneBeep1="A beep is emited from your shocker for !DURATION second(s).",
            strDoneBeep2="!USER shocker emits a beeeeep for !DURATION second(s).";
    String strDoneVibrate1="Your shocker vibrates for !DURATION second(s) at !ITENSITY intensity.",
            strDoneVibrate2="!USER shocker vibrates for !DURATION second(s) at !ITENSITY intensity.";
    String strDoneShock1="Your shocker shocks you for !DURATION second(s) at !ITENSITY intensity.",
            strDoneShock2="!USER shocker shockes them for !DURATION second(s) at !ITENSITY intensity.";
    String strTargetDoneBeep1="You use the remote to trigger the bell for !TARGET, for !DURATION second(s).",
            strTargetDoneBeep2="!USER triggered your shocker beep for !DURATION second(s).",
            strTargetDoneBeep3="!USER triggered !TARGET shocker beep for !DURATION second(s).";
    String strTargetDoneVibrate1="You use the remote to trigger the vibration for !TARGET, for !DURATION second(s) at !ITENSITY intensity.",
            strTargetDoneVibrate2="!USER triggered your shocker vibration for !DURATION second(s) at !ITENSITY intensityy.",
            strTargetDoneVibrate3="!USER triggered !TARGET shocker vibration for !DURATION second(s) at !ITENSITY intensity.";
    String strTargetDoneShock1="You use the remote to trigger the shock for !TARGET, for !DURATION second(s) at !ITENSITY intensity.",
            strTargetDoneShock2="!USER triggered your shocker shock for !DURATION second(s) at !ITENSITY intensity.",
            strTargetDoneShock3="!USER triggered !TARGET shocker shock for !DURATION second(s) at !ITENSITY intensity.";
    String strBetaTitle="Under development",strBetaDescription="These commands are in BETA and are still in development.";
    String strPressButton1="You press a button on the remote.",
            strPressButton2="!USER press a button on their remote.",
            strTargetPressButton1="You press a button on !TARGET's remote.",
            strTargetPressButton2="!USER presses a button on your remote.",
            strTargetPressButton3="!USER presses a button on !TARGET's remote.";
    int iMaxTask=5;
    String strTrigger0="Enter in the following format `type,duration,intensity` for action ",
            strTrigger1="\nWhere `type` is a string =beep/vibrate/shock ; duration is integer from 1-15 and intensity is an integer from 1-100(only if type is vibrate or shock).\nSub-actions can be separated with `~`, ex: `type1,duration1,intensity1~type2,duration2,intensity2`. Normal user can have 1 action for each action option, Patreon tier 2+ can have 5.\nType `!skip` to skip it.\nType `!cancel` to cancel operation.";
    String strListModeSelected0="Enabled both white&black list.",strListModeSelected1="Enabled only black list.",strListModeSellected2="Enabled only white list.";

    static String sStringReplacer(lcJSONUserProfile gUserProfile, Member author, String source,int duration){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iMitts.class);
        logger.info(fName + ".executed");
        try {
            source=iRestraints.sStringReplacer(gUserProfile,author,source);
            source=source.replaceAll("!DURATION", String.valueOf(duration));
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    static String sStringReplacer(lcJSONUserProfile gUserProfile, Member author, String source,int duration,int itensity){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iMitts.class);
        logger.info(fName + ".executed");
        try {
            source=iRestraints.sStringReplacer(gUserProfile,author,source);
            source=source.replaceAll("!DURATION", String.valueOf(duration)).replaceAll("!ITENSITY", String.valueOf(itensity)).replaceAll("!INTENSITY",String.valueOf(itensity));
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    String logstrReason="reason",logstrJson="json",logstrAuthor="author",logstrTarget="target",logstrGuild="guild",logstrChannl="channel";
    String strItensity45=", intensity=",strDuration45=", duration=";
    String strRdAction0="punish_collarchastity",strRdAction1="punish_collar",strRdAction2="punish_chastity";
    String strRdAction4="reward_collarchastity",strRdAction5="reward_collar",strRdAction6="reward_chastity";
    String strRdAction8="warn_collarchastity",strRdAction9="warn_collar",strRdAction10="warn_chastity";
    String strCollarShockerEnabled="Enabled collar shocker.",strCollarShockerDisabled="Disabled collar shocker";
    String strChastityShockerEnabled="Enabled chastity shocker.",strCChastityShockerDisabled="Disabled chastity shocker";

    String strWhatIs="What is PiShock?",strWhatIsDetails="The PiShock is an internet controlled remote shocking device that can be used as a shock collar or anywhere it can be attached to.",strWhatIsDetails2="The PiShock is an internet controlled remote shocking device.";
    String strSiteDiscord="PiShock Site&Discord",strSiteDiscordMore="For more information about PiShock visit its [website](https://pishock.com) or [discord](https://discord.gg/MrNb9CQyYA).";
    String strDisclaim="Disclaimer",strDisclaimMore="The bot is no way associated with PiShock, it's only using publicly available [Api](https://apidocs.pishock.com).\nThe bot is not responsible for any harm caused by misuse of the PiShock device.";


    String strMenu1="Registering a shocker to the bot.\nJust one shocker can be the main one given time!\nOptions:",
    strMenu2=" send a beep for 2 seconds",
    strMenu3=" send a vibration at 50 for 10 seconds",
    strMenu2b=" send a beep",
    strMenu3b=" send a vibration",
    strMenu5b=" send a shock",
    strMenu6b=" send main action",
    strMenu4a=" <duration> <code>` to trigger beep for the selected shocker.",
    strMenu4b=" <duration> <intensity> <code>` to trigger vibration for the selected shocker.",
    strMenu4c=" <duration> <intensity> <code>` to trigger shock for the selected shocker.",
    strMenu4d="\n--<duration> is an integer between 1-15, representing seconds\n--<intensity> an integer between 1-100 representing vibration or shock intensity\n--<code> optional string, representing shocker sharecode, using that instead the selected one. ";

    String commandGame="game",commandShockers="shockers";
    String keyGame_Field="pishockGames";
    String keyGame_Roulette="roulette",keyGame_RockPaperScisors="rps",keyGame_Players="players",keyGame_PlayersObject="playersObject",keyGame_PlayersAray="playersAray",keyGame_Played="played",keyGame_Won="won",keyGame_lost="lost",keyGame_Count="count",keyGame_Turn="turn";


}
