package web.jexpress.models.collectdata;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import web.jexpress.models.expStats;

import java.util.Arrays;

public class DataSetDiscord {
    Logger logger = Logger.getLogger(getClass());
    public DataSetDiscord(){

    }
    lcGlobalHelper gGlobal;
    public void set(CollectData collectDat,lcGlobalHelper global) {
        String fName="[set]";
        try {
            gGlobal=global;
            logger.info(fName+"clear");
            inputGuildId ="";
            inputMemberId ="";
            inputRoleId ="";
            inputChannelId ="";
            inputEmoteId ="";
            inputUserId ="";
            inputMessageContent ="";
            inputMessageId="";
            logger.info(fName+"start");
            try {
                inputGuildId =collectDat.getString( iDiscord.Options.guildID);
                inputMemberId =collectDat.getString(iDiscord.Options.memberId);
                inputUserId =collectDat.getString(iDiscord.Options.userId);
                inputRoleId =collectDat.getString(iDiscord.Options.roleId);
                inputChannelId =collectDat.getString(iDiscord.Options.channelId);
                inputEmoteId =collectDat.getString(iDiscord.Options.emoteId);
                inputFlagIncludePermissions = collectDat.getBoolean(iDiscord.Options.includePermissions);
                inputFlagIncludeRoles = collectDat.getBoolean(iDiscord.Options.includeRoles);
                inputFlagIncludeGuilds = collectDat.getBoolean(iDiscord.Options.includeGuilds);
                inputMessageId =collectDat.getString(iDiscord.Options.messageId);
                inputMessageContent =collectDat.getString(iDiscord.Options.messageContent);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                if(inputGuildId!=null&&!inputGuildId.isBlank()){
                    gGuild=gGlobal.getGuild(inputGuildId);
                    if(gGuild!=null){
                        try {
                            if(inputMemberId!=null&&!inputMemberId.isBlank()){
                                gMember=gGuild.getMemberById(inputMemberId);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        try {
                            if(inputRoleId!=null&&!inputRoleId.isBlank()){
                                gRole=gGuild.getRoleById(inputRoleId);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(fName+"done");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public String inputGuildId ="", inputMemberId ="", inputRoleId ="", inputChannelId ="", inputEmoteId ="", inputUserId ="", inputMessageId="";
    public Guild gGuild=null;
    public Role gRole=null;
    public Member gMember=null;
    public User gUser=null;
    public TextChannel gTextChannel=null;
    public PrivateChannel gPrivateChannel=null;
    public boolean inputFlagIncludeRoles =false, inputFlagIncludePermissions =false, inputFlagIncludeGuilds =false;
    public  String inputMessageContent ="";
}
