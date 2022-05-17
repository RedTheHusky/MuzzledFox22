package util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import workers.iSqlLog;

import java.util.Iterator;

public class utilitySqlLog extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iSqlLog {
    String cName="[utilitySqlLog]";
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    public utilitySqlLog(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        this.name = "SqlLog-utility";
        this.help = "sqlLogUtility";
        gGlobal = g;
        this.aliases = new String[]{"sqllogutility"};
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        Runnable r = new runLocal(event);
        new Thread(r).start();
        logger.info(fName);
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        EventWaiter gWaiter;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage;

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gWaiter = gGlobal.waiter;
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            Boolean isInvalidCommand = true;
            gUser = gCommandEvent.getAuthor();
            gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if (gCommandEvent.getArgs().isEmpty()) {
                logger.info(fName + ".Args=0");
                help("main");
                isInvalidCommand = false;
            } else {
                logger.info(fName + ".Args");
                if (!llMemberIsStaff(gMember)) {
                    llSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColorRed);
                    llMessageDelete(gCommandEvent);
                    return;
                }
                initProfile();
                // split the choices on all whitespace
                String[] items = gCommandEvent.getArgs().split("\\s+");
                if (items.length>=2&&items[0].equalsIgnoreCase("enable")) {
                    enableKey(items[1]);isInvalidCommand=false;
                }else
                if (items.length>=2&&items[0].equalsIgnoreCase("disable")) {
                    disableKey(items[1]);isInvalidCommand=false;
                }else
                if (items[0].equalsIgnoreCase("print")) {
                    printKeys();isInvalidCommand=false;
                }else
                if (items[0].equalsIgnoreCase("enableall")) {
                    enableAll();isInvalidCommand=false;
                }else
                if (items[0].equalsIgnoreCase("disableall")) {
                    disableAll();isInvalidCommand=false;
                }
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
                logger.info(".run ended");
            }
        }
        String gTitle="SqlLog";
        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + "sqllogutility ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.addField("List","`" + quickSummonWithSpace + "print` ",false);
            embed.addField("Turn on/off key","\n`" + quickSummonWithSpace + "enable/disable <name>` ",false);
            embed.addField("Turn on/off all","\n`" + quickSummonWithSpace + "enableall/disableall` ",false);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        private void printKeys(){
            String fName="[printKeys]";
            if(!gProfile.isExistent()){
                logger.warn(fName+".no profile");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile",llColorRed);
                return;
            }
            Iterator<String>keys=gProfile.jsonObject.keys();
            String tmp="";
            while (keys.hasNext()){
                String key=keys.next();
                String value=gProfile.jsonObject.getString(key);
                if(tmp.length()>0){tmp+="\n";}
                tmp+=key+"="+value;
            }
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Here is the list:"+tmp,llColorBlue1);
        }
        private void enableKey(String key){
            String fName="[enableKey]";
            if(!gProfile.isExistent()){
                logger.warn(fName+".no profile");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile",llColorRed);
                return;
            }
            logger.info(fName+".key="+key);
            if(key.equalsIgnoreCase(keyMemberJoin)||
                    key.equalsIgnoreCase(keyMemberRemove)||
                    key.equalsIgnoreCase(keyMemberRoleAdd)||
                    key.equalsIgnoreCase(keyMemberRoleRemove)||
                    key.equalsIgnoreCase(keyBan)||
                    key.equalsIgnoreCase(keyUnban)||
                    key.equalsIgnoreCase(keyUserUpdateOnlineStatus)||
                    key.equalsIgnoreCase(keyCategoryCreate)||
                    key.equalsIgnoreCase(keyCategoryDelete)||
                    key.equalsIgnoreCase(keyCategoryUpdateName)||
                    key.equalsIgnoreCase(keyCategoryUpdatePosition)||
                    key.equalsIgnoreCase(keyMemberUpdateBoostTime)||
                    key.equalsIgnoreCase(keyMemberUpdateNickName)||
                    key.equalsIgnoreCase(keyUserUpdateAvatar)||
                    key.equalsIgnoreCase(keyUserUpdateName)||
                    key.equalsIgnoreCase(keyUserUpdateDiscriminator)){
                logger.info(fName+".valid key");
                gProfile.putFieldEntry(key.toLowerCase(), true);
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(!gProfile.saveProfile(llv2_GuildsSettings)){
                    logger.info(fName+".failed to save");
                    llSendQuickEmbedMessage(gUser,gTitle,"Failed to save key "+key.toLowerCase(),llColorRed);
                }
                llSendQuickEmbedMessage(gUser,gTitle,"Enabled key "+key.toLowerCase(),llColorBlue1);
            }else{
                logger.info(fName+".invalid key");
                llSendQuickEmbedMessage(gUser,gTitle,"Invalid key!",llColorRed);
            }
        }
        private void enableAll(){
            String fName="[enableAll]";
            if(!gProfile.isExistent()){
                logger.warn(fName+".no profile");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile",llColorRed);
                return;
            }
            Iterator<String>keys=gProfile.jsonObject.keys();
            String tmp="";
            while (keys.hasNext()){
                String key=keys.next();
                String value=gProfile.jsonObject.getString(key);
                gProfile.putFieldEntry(key.toLowerCase(), true);
            }


            gGlobal.putGuildSettings(gGuild,gProfile);
            if(!gProfile.saveProfile(llv2_GuildsSettings)){
                logger.info(fName+".failed to save");
                llSendQuickEmbedMessage(gUser,gTitle,"Failed to save all keys ",llColorRed);
            }
            llSendQuickEmbedMessage(gUser,gTitle,"Enabled all key ",llColorBlue1);
        }
        private void disableKey(String key){
            String fName="[disableKey]";
            if(!gProfile.isExistent()){
                logger.warn(fName+".no profile");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile",llColorRed);
                return;
            }
            logger.info(fName+".key="+key);
            if(key.equalsIgnoreCase(keyMemberJoin)||
                    key.equalsIgnoreCase(keyMemberRemove)||
                    key.equalsIgnoreCase(keyMemberRoleAdd)||
                    key.equalsIgnoreCase(keyMemberRoleRemove)||
                    key.equalsIgnoreCase(keyBan)||
                    key.equalsIgnoreCase(keyUnban)||
                    key.equalsIgnoreCase(keyUserUpdateOnlineStatus)||
                    key.equalsIgnoreCase(keyCategoryCreate)||
                    key.equalsIgnoreCase(keyCategoryDelete)||
                    key.equalsIgnoreCase(keyCategoryUpdateName)||
                    key.equalsIgnoreCase(keyCategoryUpdatePosition)||
                    key.equalsIgnoreCase(keyMemberUpdateBoostTime)||
                    key.equalsIgnoreCase(keyMemberUpdateNickName)||
                    key.equalsIgnoreCase(keyUserUpdateAvatar)||
                    key.equalsIgnoreCase(keyUserUpdateName)||
                    key.equalsIgnoreCase(keyUserUpdateDiscriminator)){
                logger.info(fName+".valid key");
                gProfile.putFieldEntry(key.toLowerCase(), true);
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(!gProfile.saveProfile(llv2_GuildsSettings)){
                    logger.info(fName+".failed to save");
                    llSendQuickEmbedMessage(gUser,gTitle,"Failed to save key "+key.toLowerCase(),llColorRed);
                }
                llSendQuickEmbedMessage(gUser,gTitle,"Enabled key "+key.toLowerCase(),llColorBlue1);
            }else{
                logger.info(fName+".invalid key");
                llSendQuickEmbedMessage(gUser,gTitle,"Invalid key!",llColorRed);
            }
        }
        private void disableAll(){
            String fName="[disable]";
            if(!gProfile.isExistent()){
                logger.warn(fName+".no profile");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile",llColorRed);
                return;
            }
            Iterator<String>keys=gProfile.jsonObject.keys();
            String tmp="";
            while (keys.hasNext()){
                String key=keys.next();
                String value=gProfile.jsonObject.getString(key);
                gProfile.putFieldEntry(key.toLowerCase(), false);
            }


            gGlobal.putGuildSettings(gGuild,gProfile);
            if(!gProfile.saveProfile(llv2_GuildsSettings)){
                logger.info(fName+".failed to save");
                llSendQuickEmbedMessage(gUser,gTitle,"Failed to save all keys ",llColorRed);
            }
            llSendQuickEmbedMessage(gUser,gTitle,"Disabled all key ",llColorBlue1);
        }
        String gName="sqllog";
        lcJSONGuildProfile gProfile;
        private boolean initProfile(){
            String fName="[initProfile]";
            logger.info(fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            gProfile= initJson(gProfile,gGuild);
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
            return  gProfile.isExistent();
        }


    }
}
