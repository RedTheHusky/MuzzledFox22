package models;

import models.la.ForCleanup1;
import models.ls.lsAssets;
import models.ls.lsMemberHelper;
import models.ls.lsUserHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.annotations.ForRemoval;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface lsGlobalHelper extends llGlobalHelper
{

    boolean lsIsDisabled2SendMessage = false;
    boolean lsIsDisabled2DeleteMessage =false;
    String strPrivateMessageReactionNotification="If you experiencing issues using the reaction buttons, please try posting a message first in DM.";
    String strPrivateMessageReactionNotificationOptional = "Reactions not added automatically as **message components** is used.\nStill it will accept reactions.\nIf you experiencing issues using the reactions, please try posting a message first in DM.";
    String strSupportTitle=":pushpin:Support",strSupport="If you like the bot and would like to keep it up, please support us at [paypal]("+ lsAssets.urlLink2PayPal+") or [patreon]("+lsAssets.urlLink2Patreon+"). Also check the [Forum]("+lsAssets.urlLink2Forum+")/[Discord]("+lsAssets.urlLink2BotSerer+").";
    @ForCleanup1
    @DeprecatedSince("11/24/21")
    static boolean slMemberIsBotOwner(Member member){
        String fName="llMemberIsBotOwner.";
        Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
           return lsMemberHelper.lsMemberIsBotOwner(member);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    @ForCleanup1
    @DeprecatedSince("11/24/21")
    static boolean slUserIsBotOwner(User user){
        String fName="llUserIsBotOwner.";
        Logger logger = Logger.getLogger(fName);
        try{
            logger.info(fName);
            return lsUserHelper.lsUserIsBotOwner(user);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    int lsImageSizeLimit=1024;
    int lsImageBytesLimit=10240000;
    String timeout = "timeout",timeout_button="timeout_button",timeout_selectmenu="timeout_selectMenu",timeout_reaction_add="timeout_reaction_add";
    String lsGeneralGuildNotificationPath="resources/json/guilds/notification.json";


    static Map<Integer,lcGlobalHelper>globals=new HashMap<>();
    static boolean sAddGlobal(lcGlobalHelper global){
        String fName="sAddGlobal.";
        Logger logger = Logger.getLogger(fName);
        try{
            globals.put(globals.size(),global);
            return true;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static lcGlobalHelper sGetGlobal(){
        String fName="sGetGlobal.";
        Logger logger = Logger.getLogger(fName);
        try{
            return globals.get(0);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}