package models;

import com.jagrosh.jdautilities.command.Command;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import models.la.NotInUse;
import models.lc.json.profile.lcJSONUserProfile;
import models.ls.lsMemberHelper;
import models.ls.lsUserHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface llGlobalHelper
{
    Boolean llDebug=false;
    String llBotToken="";
    String llMainBotToke="";
    String llPrivateBotToken="";
    String llLaxBotToken="";
    String llDebugBotToken="";
    int llLogMode=0;


    String llGuildKeyBotHelper="481700616457027597";    //Administration
    String llPrefixStr="!>";
    //int llShards=3;


    //String llDebugPrefixStr="?>";
    boolean llClientUseHelpBuilder=false; String llHelpWord="helpx";
    String llActionLog="actionLog";
    String llOverride="override";

    String llLogDefault="Log4j/Log4j.properties";
    String llLogNormal="Log4j/LogNormal.properties";
    String llLogError="Log4j/LogError.properties";
    String llLogIssues="Log4j/LogIssues.properties";
    String llLogAll="Log4j/LogAll.properties";
    String llLogDebug="Log4j/LogDebug.properties";

    String llv2_GuildsSettings ="2guilds_settings";
    String llv2_GlobalsSettings="2global_settings";
    String cName="llGlobalHelper", llMemberProfile="MemberProfile"; Boolean debugInfo=true;
    boolean llIsDisabled2SendMessage =false,llIsDisabled2DeleteMessage =false;


    default String llRemovePrefixFromContentRaw(String contentRaw){
        String fName = "[llRemovePrefixFromContentRaw]";
        Logger logger = Logger.getLogger(llGlobalHelper.class);
        try{
            logger.info("contentRaw="+contentRaw);
            if(!contentRaw.startsWith(llPrefixStr)){
                return contentRaw;
            }
            if(contentRaw.length()>1){
                contentRaw=contentRaw.replace(llPrefixStr,"");
                //contentRaw=contentRaw.substring(1);
            }else{
                contentRaw="";
            }
            return contentRaw;
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;
        }

    }
    @NotInUse
    default String llGetPrefix(String contentRaw){
        String fName = "[llGetPrefix]";
        Logger logger = Logger.getLogger(llGlobalHelper.class);
        try{
            logger.info("contentRaw="+contentRaw);
            if(contentRaw.isEmpty()){
                return null;
            }
            String[] items=contentRaw.split("\\s+");
            if(items[0].equalsIgnoreCase(llPrefixStr)&&items.length>=2){
                return items[0]+items[1];
            }
            return items[0];
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    default boolean llLogAction(lcGlobalHelper global,Guild guild, User author, String name,JSONObject data){
        lcJSONUserProfile json=new lcJSONUserProfile(global,author,guild,name);
        json.jsonObject =data;
        if(json.saveProfile(llActionLog)){
            return true;
        }
        return false;
    }
    @Deprecated
    @ForCleanup1
    @DeprecatedSince("11/24/21")
    default boolean llMemberIsBotOwner(Member member){
        String fName="llMemberIsBotOwner.";
        try{
           return lsMemberHelper.lsMemberIsBotOwner(member);
        }
        catch(Exception e){
            System.err.println("@"+fName+"exception:"+e);return false;
        }
    }
    @Deprecated
    @ForCleanup1
    @DeprecatedSince("11/24/21")
    default boolean llUserIsBotOwner(User user){
        String fName="llUserIsBotOwner.";
        try{
            return lsUserHelper.lsUserIsBotOwner(user);
        }
        catch(Exception e){
            System.err.println("@"+fName+"exception:"+e);return false;
        }
    }


    int llCompiledVersion=13;
    //int llSDKVersion=13;
    Command.Category llCommandCategory_SFW =new Command.Category("SFW"), llCommandCategory_NSFW =new Command.Category("NSFW");
    Command.Category llCommandCategory_SocialSFW =new Command.Category("SFW Social"), llCommandCategory_SocialNSFW =new Command.Category("NSFW Social");
    Command.Category llCommandCategory_Between=new Command.Category("Between SFW&NSFW");
    Command.Category llCommandCategory_StaffOnly=new Command.Category("Staff Only");
    Command.Category llCommandCategory_Removed=new Command.Category("Removed");
    Command.Category llCommandCategory_Utility=new Command.Category("Utility");
    Command.Category llCommandCategory_UtilityInHouse=new Command.Category("Utility (in House)");
    Command.Category llCommandCategory_BuildAlpha =new Command.Category("BuildAlpha");
    Command.Category llCommandCategory_SFWProfiles =new Command.Category("SFW Profiles");
    Command.Category llCommandCategory_NSFWProfiles =new Command.Category("NSFW Profiles");
}