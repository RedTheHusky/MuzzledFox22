package util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.json.lcJSONObjProfile;
import models.lc.emotes.lcEmote;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ls.*;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import nsfw.chastity.chastitysession.chastitysession;
import forRemoval.nsfw.diaperInteractive;
import nsfw.verify.lewdverify;
import org.apache.log4j.Logger;
import restraints.rdUtility;
import userprofiles.*;

import java.awt.*;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class utilityUser extends Command implements  llGlobalHelper, llMemberHelper, lsUserHelper {
    String cName="[utilityEmote]";
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gTitle="User utility";
    public utilityUser(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        this.name = "User-Utility";
        this.help = "Utility commands for users";
        gGlobal = g;
        this.aliases = new String[]{"user","utilityuser","userutility","utilitymember","memberutility"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        EventWaiter gWaiter;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage;
        Timestamp gTimestamp;
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
            boolean isInvalidCommand = true;
            try {
                prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
                gGuild = gCommandEvent.getGuild();
                logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = gCommandEvent.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                gTimestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+gTimestamp.getTime());
                if (!llMemberIsStaff(gMember)) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                    lsMessageHelper.lsMessageDelete(gCommandEvent);
                    return;
                }
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(fName + ".Args");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("resetuserdata")) {
                        userDataReset();
                    }else
                    if (items[0].equalsIgnoreCase("whomember")) {
                        getTarget();
                        memberProfile();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("banner")) {
                        getTarget();
                        memberBanner();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("avatar")) {
                        getTarget();
                        memberAvatar();
                        isInvalidCommand = false;
                    }
                    if (items[0].equalsIgnoreCase("whouser")) {
                        getTarget();
                        userProfile();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("whois")) {
                        getTarget();
                        if(gTargetMember!=null){
                            memberProfile();
                        }else{
                            userProfile();
                        }
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("mute")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_VOICEMUTEOTHERS(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberVoiceMute();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("unmute")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_VOICEMUTEOTHERS(gMember)){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberVoiceUnMute();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("deafen")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_VOICEDEAFOTHERS(gMember)){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberVoiceDeafen();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("undeafen")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_VOICEDEAFOTHERS(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberVoiceUnDeafen();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("roles")) {
                        getTarget();
                        memberGetRoles();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("addroles")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_MANAGEROLES(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberAddRoles();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("removeroles")) {
                        if (!llMemberIsModerator(gMember)&&!llMemberHasPermission_MANAGEROLES(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        getTarget();
                        memberRemoveRoles();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("permission")||items[0].equalsIgnoreCase("permissions")||items[0].equalsIgnoreCase("perm")||items[0].equalsIgnoreCase("perms")) {
                        getTarget();
                        memberGetPermissions();
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("preban")) {
                        if (!llMemberIsModerator(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        if(items.length>=2){
                            if (items[1].equalsIgnoreCase("add")) {
                                getTarget();
                                add2PreBanList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("rem")) {
                                getTarget();
                                rem4PreBanList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("get")||items[1].equalsIgnoreCase("print")) {
                                getTarget();
                                printPreBanList();
                                isInvalidCommand = false;
                            }
                        }
                    }else
                    if (items[0].equalsIgnoreCase("troll")) {
                        if (!llMemberIsModerator(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        if(items.length>=2){
                            if (items[1].equalsIgnoreCase("add")) {
                                getTarget();
                                add2TrollList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("rem")) {
                                getTarget();
                                rem4TrollList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("get")||items[1].equalsIgnoreCase("print")) {
                                getTarget();
                                printTrollList();
                                isInvalidCommand = false;
                            }
                        }
                    }else
                    if (items[0].equalsIgnoreCase("trouble")) {
                        if (!llMemberIsModerator(gMember)) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied!", llColors.llColorRed);
                            lsMessageHelper.lsMessageDelete(gCommandEvent);
                            return;
                        }
                        if(items.length>=2){
                            if (items[1].equalsIgnoreCase("add")) {
                                getTarget();
                                add2TroubleList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("rem")) {
                                getTarget();
                                rem4TroubleList();
                                isInvalidCommand = false;
                            }else
                            if (items[1].equalsIgnoreCase("get")||items[1].equalsIgnoreCase("print")) {
                                getTarget();
                                printTroubleList();
                                isInvalidCommand = false;
                            }
                        }
                    }
                }
                logger.info(fName + ".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(), llColors.llColorRed);
            }
            logger.info(".run ended");
        }
        String prefix2="";
        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = prefix2 + "user ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColors.llColorBlue1);

            embed.addField("Whois","`"+quickSummonWithSpace + "whois\\whomember\\whouser [mention or id]`",false);
            embed.addField("Mute&Deafen","\n`"+quickSummonWithSpace + "mute\\unmute\\deafen\\undeafen [mention or id of member]`",false);
            embed.addField("Info roles&perms","\n`"+quickSummonWithSpace + "roles\\perms [mention or id of member]`",false);
            embed.addField("Add/Rem Roles","\n`"+quickSummonWithSpace + "addroles\\removeroles [mention or id of member] [mention or id of roles]`",false);
            embed.addField("(Alpha)List","\n`"+quickSummonWithSpace + "preban\\troll\\trouble add\\rem\\get [mention or id of user for add and rem] `",false);
            embed.addField("(Alpha)Reset UserData","\n`"+quickSummonWithSpace + "resetuserdata`, will reset your userdata stored for the bot",false);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!", llColors.llColorBlue1);
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void memberProfile(){
            String fName = "memberProfile";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                String id=gTargetMember.getId();
                User user=gTargetMember.getUser();
                String effectiveName=gTargetMember.getEffectiveName();
                String name=user.getName();
                String avatarUrl=user.getAvatarUrl();
                String serveravatarUrl=gTargetMember.getAvatarUrl();
                OffsetDateTime timeCreated=user.getTimeCreated();
                OffsetDateTime timeJoined=gTargetMember.getTimeJoined();
                int color=gTargetMember.getColorRaw();
                OnlineStatus onlineStatus=OnlineStatus.UNKNOWN;
                List<Activity>activities=new ArrayList<>();
                try {
                    onlineStatus=gTargetMember.getOnlineStatus();
                    activities=gTargetMember.getActivities();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                List<Role>roles=gTargetMember.getRoles();
                GuildVoiceState voiceState=null;
                try {
                    voiceState=gTargetMember.getVoiceState();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                //List<Guild>guilds=gTargetUser.getMutualGuilds();

                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                String desc="";
                embed.setTitle(getMemberName(name,effectiveName));
                embed.setColor(color);

                embed.addField("ID",id,true);
                if(avatarUrl!=null||serveravatarUrl!=null){
                    if(avatarUrl!=null){
                        logger.info(fName+"avatarUrl="+avatarUrl);
                        embed.setThumbnail(avatarUrl);
                        if(!desc.isBlank())desc+="\n";
                        desc+="[Avatar]("+avatarUrl+")";
                    }
                    if(serveravatarUrl!=null){
                        logger.info(fName+"serveravatarUrl="+serveravatarUrl);
                        if(!desc.isBlank())desc+="\n";
                        desc+="[Server Avatar]("+serveravatarUrl+")";
                    }

                }
                try{
                    User.Profile profile=gTargetMember.getUser().retrieveProfile().complete();
                    String urlBanner=profile.getBannerUrl();
                    Color colorAccent=profile.getAccentColor();
                    if( urlBanner!=null&&! urlBanner.isBlank()){
                        logger.info(fName+"s urlBannerl="+ urlBanner);
                        if(!desc.isBlank())desc+="\n";
                        desc+="[Banner]("+urlBanner+")";
                        InputStream in=lsStreamHelper.llGetInputStream4WebFile( urlBanner);
                        if(in!=null){
                            embed.setImage(urlBanner);
                        }
                    }
                    if(colorAccent!=null){
                        logger.info(fName+"s colorAccent="+ colorAccent.getRGB());
                        if(!desc.isBlank())desc+="\n";
                        desc+="RGB:"+colorAccent.getRGB()+"";
                        embed.setColor(colorAccent);
                    }else{
                        embed.setColor(llColors.llColorBlue1);
                    }
                }catch (Exception e){
                    logger.error(fName+"exception:"+e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                embed.addField("Profile",desc,true);
                embed.addField("Created", lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timeCreated),true);
                embed.addField("Joined",lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timeJoined),true);
                embed.addField("Roles", String.valueOf(roles.size()),true);
                embed.addField("Status",getOnlineStatus(onlineStatus),true);
                embed.addField("Voice",getVoiceState(voiceState),true);
                //embed.addBlankField(false);
                //embed.addField("Guilds",getMutualGuilds(guilds),true);
                /*lcUserObject userobject=new lcUserObject(gGuild.getJDA(),gTargetMember.getIdLong());
                String urlBanner=userobject.getBannerUrl();
                if(urlBanner!=null&&!urlBanner.isBlank()){
                    InputStream in=lsStreamHelper.llGetInputStream4WebFile(urlBanner);
                    if(in!=null){
                        embed.setImage(urlBanner);
                    }
                }*/


                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberGetRoles(){
            String fName = "memberGetRoles";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                String id=gTargetMember.getId();
                String effectiveName=gTargetMember.getEffectiveName();
                String name=gTargetUser.getName();
                String avatarUrl=gTargetUser.getAvatarUrl();
                int color=gTargetMember.getColorRaw();
                List<Role>roles=gTargetMember.getRoles();
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(getMemberName(name,effectiveName));
                embed.setColor(color);

                embed.addField("ID",id,true);
                if(avatarUrl!=null){
                    embed.setThumbnail(avatarUrl);
                }
                int length=roles.size();
                logger.info(fName+"length="+length);
                embed.addField("Count", String.valueOf(length),true);
                //embed.addBlankField(false);
                String tmp;
                if(length>0){
                    tmp="";
                    int ci=0; int cm=5;
                    logger.info(fName+"cm="+cm);
                    for(int i=0;i<length;i++){
                        Role role=roles.get(i);
                        logger.info(fName+"roles["+i+"]:"+role.getId()+"|"+role.getName());
                        ci++;logger.info(fName+"ci="+ci);
                        if(ci<cm){
                            tmp+=role.getAsMention();
                        }else{
                            ci=0;
                            tmp+="\n"+role.getAsMention();
                        }
                    }
                    logger.info(fName+" tmp="+tmp);
                    embed.addField("List", tmp,false);
                }
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberGetPermissions(){
            //https://discordapi.com/permissions.html#0
            String fName = "memberGetPermissions";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                String id=gTargetMember.getId();
                String effectiveName=gTargetMember.getEffectiveName();
                String name=gTargetUser.getName();
                String avatarUrl=gTargetUser.getAvatarUrl();
                int color=gTargetMember.getColorRaw();
                EnumSet<Permission> permissions=gTargetMember.getPermissions();
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(getMemberName(name,effectiveName));
                embed.setColor(color);

                embed.addField("ID",id,true);
                if(avatarUrl!=null){
                    embed.setThumbnail(avatarUrl);
                }
                int length=permissions.size();
                logger.info(fName+"length="+length);
                embed.addField("Count", String.valueOf(length),true);
                //embed.addBlankField(false);
                lcEmote emote=new lcEmote(gGuild);
                emote.getEmoteByName("staff");
                String staffEmoteMention=":shield:";
                if(emote.isValid()){
                    staffEmoteMention=emote.getAsMention();
                }
                StringBuilder tmp;
                if(length>0){
                    tmp = new StringBuilder();
                    int ci=0; int cm=5;
                    logger.info(fName+"cm="+cm);
                    Iterator itr =permissions.iterator();
                    while (itr.hasNext())
                    {
                        Permission perm = (Permission) itr.next();
                        ci++;
                        logger.info(fName+"ci="+ci);
                        logger.info(fName+"perm:"+perm.getName());

                        if(perm==Permission.ADMINISTRATOR||perm==Permission.MANAGE_SERVER||perm==Permission.MANAGE_ROLES||perm==Permission.MANAGE_PERMISSIONS||perm==Permission.MANAGE_CHANNEL||perm==Permission.KICK_MEMBERS||perm==Permission.BAN_MEMBERS||perm==Permission.MANAGE_WEBHOOKS||perm==Permission.MANAGE_EMOTES||perm==Permission.MESSAGE_MANAGE||perm==Permission.NICKNAME_MANAGE){
                            tmp.append("\n").append(staffEmoteMention).append(perm.getName());
                        }
                        else if(perm==Permission.VOICE_MUTE_OTHERS||perm==Permission.VOICE_DEAF_OTHERS||perm==Permission.VOICE_MOVE_OTHERS){
                            tmp.append("\n").append(staffEmoteMention).append(perm.getName());
                        }
                        else if(perm==Permission.VIEW_AUDIT_LOGS){
                            tmp.append("\n").append(staffEmoteMention).append(perm.getName());
                        }else{
                            tmp.append("\n").append(perm.getName());
                        }

                    }
                    logger.info(fName+" tmp="+tmp);
                    embed.addField("List", tmp.toString(),false);
                }
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberVoiceMute(){
            String fName = "memberVoiceMute";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                gTargetMember.mute(true).reason("Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                    if (error != null){
                        logger.error(fName+"exception:"+error);
                        lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+error);
                    }else{
                        logger.info(fName+"success");
                        lsMessageHelper.lsSendMessage(gTextChannel,"Muted member "+gTargetUser.getName()+".");
                    }
                });
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberVoiceUnMute(){
            String fName = "memberVoiceUnMute";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                gTargetMember.mute(false).reason("Un-Muted by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                    if (error != null){
                        logger.error(fName+"exception:"+error);
                        lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+error);
                    }else{
                        logger.info(fName+"success");
                        lsMessageHelper.lsSendMessage(gTextChannel,"Un-Muted member "+gTargetUser.getName()+".");
                    }
                });
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberVoiceDeafen(){
            String fName = "memberVoiceDeafen";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                gTargetMember.deafen(true).reason("Deafen by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                    if (error != null){
                        logger.error(fName+"exception:"+error);
                        lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+error);
                    }else{
                        logger.info(fName+"success");
                        lsMessageHelper.lsSendMessage(gTextChannel,"Deafened member "+gTargetUser.getName()+".");
                    }
                });
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberVoiceUnDeafen(){
            String fName = "memberVoiceUnDeafen";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                gTargetMember.deafen(false).reason("Un-Deafen by :"+gUser.getName()).submit().whenComplete((s, error) -> {
                    if (error != null){
                        logger.error(fName+"exception:"+error);
                        lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+error);
                    }else{
                        logger.info(fName+"success");
                        lsMessageHelper.lsSendMessage(gTextChannel,"Un-Deafened member "+gTargetUser.getName()+".");
                    }
                });
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberAddRoles(){
            String fName = "memberAddRoles";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                getRoles();
                if(gTargetedRoles==null||gTargetedRoles.isEmpty()){
                    logger.info(fName+"no roles selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No roles selected!", llColors.llColorRed);
                    return;
                }
                if(!isRolesBellowStaffRights()){
                    logger.info(fName+"has roles for staff only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for staff only!", llColors.llColorRed);
                    return;
                }
                int length=gTargetedRoles.size();
                logger.info(fName+"gTargetedRoles.length="+length);
                List<Role>toAdd=new ArrayList<>();
                for(int i=0;i<length;i++){
                    Role role=gTargetedRoles.get(i);
                    logger.info(fName+"allRoles["+i+"]="+role.getId()+"|"+role.getName());
                    if(llMemberHasRole(gTargetMember, role)){
                        logger.info(fName+"already has role>ignore");
                    }else{
                        logger.info(fName+"has no such role>add to list");
                        toAdd.add(role);
                    }
                }
                if(toAdd.isEmpty()){
                    logger.info(fName+"no roles to add");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No roles to add!", llColors.llColorRed);
                    return;
                }
                length=toAdd.size();
                logger.info(fName+"toAdd.length="+length);
               StringBuilder tmp= new StringBuilder();
                for(int i=0;i<length;i++){
                    Role role=toAdd.get(i);
                    logger.info(fName+"toAddRoles["+i+"]="+role.getId()+"|"+role.getName());
                    gGuild.addRoleToMember(gTargetMember,role).reason("Role Updated by :"+gUser.getName()).queue();
                    if(i>0){
                        tmp.append(",");}
                    tmp.append(role.getAsMention()).append(" ");
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Following roles "+tmp+" added to "+gTargetMember.getAsMention(), llColors.llColorBlue1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberRemoveRoles(){
            String fName = "memberRemoveRoles";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                getRoles();
                if(gTargetedRoles==null||gTargetedRoles.isEmpty()){
                    logger.info(fName+"no roles selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No roles selected!", llColors.llColorRed);
                    return;
                }
                if(!isRolesBellowStaffRights()){
                    logger.info(fName+"has roles for staff only");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied, it has roles reserved for staff only!", llColors.llColorRed);
                    return;
                }
                int length=gTargetedRoles.size();
                logger.info(fName+"gTargetedRoles.length="+length);
                List<Role>toRemove=new ArrayList<>();
                for(int i=0;i<length;i++){
                    Role role=gTargetedRoles.get(i);
                    logger.info(fName+"allRoles["+i+"]="+role.getId()+"|"+role.getName());
                    if(llMemberHasRole(gTargetMember, role)){
                        logger.info(fName+"already has role>add");
                        toRemove.add(role);
                    }else{
                        logger.info(fName+"has no such role>ignore");

                    }
                }
                if(toRemove.isEmpty()){
                    logger.info(fName+"no roles to remove");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No roles to remove!", llColors.llColorRed);
                    return;
                }
                length=toRemove.size();
                logger.info(fName+"toAdd.length="+length);
                StringBuilder tmp= new StringBuilder();
                for(int i=0;i<length;i++){
                    Role role=toRemove.get(i);
                    logger.info(fName+"toRemoveRoles["+i+"]="+role.getId()+"|"+role.getName());
                    gGuild.removeRoleFromMember(gTargetMember,role).reason("Role Updated by :"+gUser.getName()).queue();
                    if(i>0){
                        tmp.append(",");}
                    tmp.append(role.getAsMention()).append(" ");
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Following roles "+tmp+" removed from "+gTargetMember.getAsMention(), llColors.llColorBlue1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void userProfile(){
            String fName = "userProfile";
            logger.info(fName);
            try{
                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No user selected!", llColors.llColorRed);
                    return;
                }
                String id=gTargetUser.getId();
                String name=gTargetUser.getName();
                String avatarUrl=gTargetUser.getAvatarUrl();
                OffsetDateTime timeCreated=gTargetUser.getTimeCreated();
                //List<Guild>guilds=gTargetUser.getMutualGuilds();

                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                if(gTargetUser.isBot()){
                    embed.setTitle("[BOT]"+name);
                }else{
                    embed.setTitle(name);
                }
                embed.setColor(llColors.llColorBlue1);

                embed.addField("ID",id,true);
                if(avatarUrl!=null){
                    embed.setThumbnail(avatarUrl);embed.addField("Avatar","[Link]("+avatarUrl+")",true);
                }
                embed.addField("Created",lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timeCreated),true);
                //embed.addBlankField(false);
                //embed.addField("Guilds",getMutualGuilds(guilds),true);
                /*lcUserObject userobject=new lcUserObject(gGuild.getJDA(),gTargetUser.getIdLong());
                String urlBanner=userobject.getBannerUrl();
                if(urlBanner!=null&&!urlBanner.isBlank()){
                    InputStream in=lsStreamHelper.llGetInputStream4WebFile(urlBanner);
                    if(in!=null){
                        embed.setImage(urlBanner);
                    }
                }*/
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void memberAvatar(){
            String fName = "[memberAvatar]";
            logger.info(fName);
            try{
                if(gTargetMember==null){
                    logger.info(fName+"no member selected");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                    return;
                }
                String id=gTargetMember.getId();
                User user=gTargetMember.getUser();
                String name=user.getName();
                String avatarUrl=user.getAvatarUrl();
                String serverAvatarUrl=gTargetMember.getAvatarUrl();
                int color=gTargetMember.getColorRaw();
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                String desc="";
                embed.setColor(color);
                embed.setTitle(getMemberName(name,gTargetMember.getEffectiveName()));
                if(avatarUrl!=null||serverAvatarUrl!=null){
                    if(serverAvatarUrl!=null){
                        logger.info(fName+"serveravatarUrl="+serverAvatarUrl);
                        if(!desc.isBlank())desc+="\n";
                        desc+="[Server Avatar"+serverAvatarUrl+")";
                    }
                    if(avatarUrl!=null&&!avatarUrl.isBlank()){
                        logger.info(fName+"avatarUrl="+avatarUrl);
                        if(!desc.isBlank())desc+="\n";
                        desc+="[Avatar]("+avatarUrl+")";
                    }

                    if(serverAvatarUrl!=null){
                        embed.setImage(serverAvatarUrl);
                    }else
                    if(avatarUrl!=null){
                        embed.setThumbnail(avatarUrl);
                    }
                    embed.addField("Avatar",desc,true);
                }
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }

        private void memberBanner(){
            String fName = "[memberBanner]";
            logger.info(fName+"experimental");
            if(gTargetMember==null){
                logger.info(fName+"no member selected");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No member selected!", llColors.llColorRed);
                return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTargetMember.getUser().getName()+" banner");

            //lcUserObject userobject=new lcUserObject(gGuild.getJDA(),gTargetMember.getIdLong());
            //String url=userobject.getBannerStaticUrl();
            try{
                User.Profile profile=gTargetMember.getUser().retrieveProfile().complete();
                String url=profile.getBannerUrl();
                Color colorAccent=profile.getAccentColor();
                boolean valid=false;
                if(url!=null&&!url.isBlank()){
                    InputStream in=lsStreamHelper.llGetInputStream4WebFile(url);
                    if(in!=null){
                        embed.setImage(url);
                        valid=true;
                    }
                }
                if(!valid){
                    embed.setDescription("No banner for user!");
                }
                if(colorAccent!=null){
                    embed.setColor(colorAccent);
                }else if(valid){
                    embed.setColor(llColors.llColorBlue1);
                }else{
                    embed.setColor(llColors.llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.setDescription("Could not get user profile");
                embed.setColor(llColors.llColorRed_Barn);
            }
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        User gTargetUser; Member gTargetMember;
        private void getTarget(){
            String fName = "getTarget";
            logger.info(fName);
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(!members.isEmpty()){
                logger.info(fName+"do mention member");
                gTargetMember=members.get(0);
                gTargetUser=gTargetMember.getUser();
                logger.info(fName+"mentioned user&member:"+gTargetUser.getId()+"|"+gTargetUser.getName());
                return;
            }
            List<User> users= gCommandEvent.getMessage().getMentionedUsers();
            if(!users.isEmpty()){
                logger.info(fName+"do mention user");
                gTargetUser=users.get(0);
                logger.info(fName+"mentioned user:"+gTargetUser.getId()+"|"+gTargetUser.getName());
                return;
            }
            if(gCommandEvent.getArgs().isEmpty()){
                logger.info(fName+".no string>do self");
                gTargetUser=gUser;
                gTargetMember=gMember;
                return;
            }
            String[] items = gCommandEvent.getArgs().split("\\s+");
            logger.info(fName+".do check for string");
            int length=items.length;logger.info(fName+".length="+length);
            for(int i=0;i<length;i++){
                logger.info(fName+".items["+i+"]="+items[i]);
                Member member= lsMemberHelper.lsGetMember(gGuild,items[i]);
                User user= lsUserHelper.lsGetUser(gGuild,items[i]);
                User userR= lsUserHelper.lsRetrieveUserById(gGuild,items[i]);
                if(member!=null){
                    logger.info(fName+"found member:"+member.getId()+"|"+member.getEffectiveName());
                    gTargetMember=member;
                    gTargetUser=member.getUser();
                    return;
                }
                if(userR!=null){
                    logger.info(fName+"found userR:"+userR.getId()+"|"+userR.getName());
                    gTargetUser=userR;
                    Member member2=llGetMember(gGuild,userR);
                    if(member!=null){
                        logger.info(fName + ".userR is also a member");
                        gTargetMember=member2;
                    }else{
                        logger.info(fName + ".userR is not a member");
                    }
                    return;
                }
                if(user!=null){
                    logger.info(fName+"found user:"+user.getId()+"|"+user.getName());
                    gTargetUser=user;
                    Member member2=llGetMember(gGuild,user);
                    if(member!=null){
                        logger.info(fName + ".user is also a member");
                        gTargetMember=member2;
                    }else{
                        logger.info(fName + ".user is not a member");
                    }
                    return;
                }
            }
            logger.info(fName+".no other user>do self");
            gTargetUser=gUser;
            gTargetMember=gMember;
        }
        private String getMemberName(String name, String effectiveName){
            String fName = "getMemberName";
            logger.info(fName);
            String tmp=effectiveName+"("+name+")";
            if(effectiveName.equals(name)){
                tmp= name;
            }
            if(gTargetUser.isBot()){
                tmp="[BOT]"+tmp;
            }
            logger.info(fName+".return="+tmp);
            return tmp;
        }

        private String getOnlineStatus(OnlineStatus status){
            String fName = "getOnlineStatus";
            logger.info(fName);
            String tmp="n/a";
            tmp=status.getKey();
            logger.info(fName+".return="+tmp);
            return tmp;
        }
        private String getActivity(List<Activity>activities){
            String fName = "getActivity";
            logger.info(fName);
            String tmp="No activity";
            if(!activities.isEmpty()){
                tmp=activities.get(0).getName();
            }
            logger.info(fName+".return="+tmp);
            return tmp;
        }
        private String getMutualGuilds(List<Guild>guilds){
            String fName = "getMutualGuilds";
            logger.info(fName);
            String tmp="n/a";
            if(!guilds.isEmpty()){
                logger.info(fName+".size="+guilds.size());
                for(int i=0;i<guilds.size();i++){
                    Guild guild=guilds.get(i);
                    logger.info(fName+".guilds["+i+"]:"+guild.getId()+"|"+guild.getName());
                    if(i>0) tmp+="\n"+guild.getName();
                    else tmp=guild.getName();
                }
            }
            logger.info(fName+".return="+tmp);
            return tmp;
        }
        private String getVoiceState(GuildVoiceState voiceState){
            String fName = "getVoiceState";
            logger.info(fName);
            String tmp="No";
            if(voiceState!=null&&voiceState.inVoiceChannel()){
                 tmp=voiceState.getChannel().getName()+" [";
                 if(voiceState.isMuted()||voiceState.isSelfMuted()||voiceState.isGuildMuted()){
                     tmp+="m";
                 }
                 if(voiceState.isDeafened()||voiceState.isSelfDeafened()|| voiceState.isGuildDeafened()){
                     tmp+="d";
                 }
                 if(voiceState.isSuppressed()){
                     tmp+="s";
                 }
                 tmp+="]";
            }
            logger.info(fName+".return="+tmp);
            return tmp;
        }
        List<Role>gTargetedRoles=new ArrayList<>();
        private void getRoles(){
            String fName = "getRoles";
            logger.info(fName);
            List<Role> roles= gCommandEvent.getMessage().getMentionedRoles();
            if(!roles.isEmpty()){
                logger.info(fName+"do mention roles");
                gTargetedRoles=roles;
                logger.info(fName+"result="+ gTargetedRoles);
                return;
            }
            if(gCommandEvent.getArgs().isEmpty()){
                logger.info(fName+".no string>no roles added");
                return;
            }
            String[] items = gCommandEvent.getArgs().split("\\s+");
            logger.info(fName+".do check for string");
            int length=items.length;logger.info(fName+".length="+length);
            for(int i=0;i<length;i++){
                logger.info(fName+".items["+i+"]="+items[i]);
                Role role=lsRoleHelper.lsGetRoleByID(gGuild, items[i]);
                if(role!=null){
                    logger.info(fName+"found role by id:"+role.getId()+"|"+role.getName());
                    gTargetedRoles.add(role);
                }else{
                    role=lsRoleHelper.lsGetFirstRolesByName(gGuild, items[i],true);
                    if(role!=null){
                        logger.info(fName+"found role by first name role:"+role.getId()+"|"+role.getName());
                        gTargetedRoles.add(role);
                    }
                }
            }
            if(gTargetedRoles.isEmpty()){
                logger.info(fName+".no roles added");
            }
        }
        private Boolean isRolesBellowStaffRights(){
            String fName = "isRolesBellowStaffRole";
            logger.info(fName);
            if(gTargetedRoles.isEmpty()){
                logger.info(fName+"is empty");
                return false;
            }
            int length=gTargetedRoles.size();
            for(int i=0;i<length;i++){
                Role role=gTargetedRoles.get(i);
                logger.info(fName+"roles2check["+i+"]="+role.getId()+"|"+role.getName());
                if(lsRoleHelper.lsRoleHasAdminRights(role)||lsRoleHelper.lsRoleHasManagerRights(role)||lsRoleHelper.lsRoleHasModeratorRights(role)||lsRoleHelper.lsRoleHasStaffRights(role)||lsRoleHelper.lsRoleIsOrange(role)){
                    logger.info(fName+"role is for staff only");
                    return false;
                }
            }
            logger.info(fName+"clear");
            return true;
        }
        private Boolean isRolesBellowAuthorHighestRolePosition(){
            String fName = "isRolesBellowAuthorHighestRolePosition";
            logger.info(fName);
            if(gTargetedRoles.isEmpty()){
                logger.info(fName+" select roles is empty");
                return false;
            }
            List<Role>authorRole=gMember.getRoles();
            if(authorRole.isEmpty()){
                logger.info(fName+"author role is empty");
                return false;
            }
            int authorPosition=authorRole.get(0).getPosition();
            logger.info(fName+"authorPosition="+authorPosition);
            int length=gTargetedRoles.size();
            for(int i=0;i<length;i++){
                Role role=gTargetedRoles.get(i);
                logger.info(fName+"roles2check["+i+"]="+role.getId()+"|"+role.getName()+"|"+role.getPosition());
                if(role.getPosition()>=authorPosition){
                    logger.info(fName+"role is above or equal the author role");
                    return false;
                }
            }
            logger.info(fName+"clear");
            return true;
        }
        private Boolean isRolesBellowOrMatchWithAuthorPermissions(){
            String fName = "isRolesBellowOrMatchWithAuthorPermissions";
            logger.info(fName);
            if(gTargetedRoles.isEmpty()){
                logger.info(fName+" select roles is empty");
                return false;
            }
            List<Role>authorRole=gMember.getRoles();
            if(authorRole.isEmpty()){
                logger.info(fName+"author role is empty");
                return false;
            }
            JSONObject authorPermissions=new JSONObject();
            JSONObject targetedRolesPermissions=new JSONObject();
            int lengthTargetedRoles=gTargetedRoles.size();
            logger.info(fName+"gTargetedRoles.length="+lengthTargetedRoles);
            for(int i=0;i<lengthTargetedRoles;i++){
                Role role=gTargetedRoles.get(i);
                logger.info(fName+"roles2check["+i+"]="+role.getId()+"|"+role.getName());
                targetedRolesPermissions=getPermissionJSON(targetedRolesPermissions, role.getPermissions());
            }
            authorPermissions=getPermissionJSON(gMember.getPermissions());
            int lengthAuthorPermissions=authorPermissions.length();
            int lengthTargetRolesPermissions=targetedRolesPermissions.length();
            logger.info(fName+"authorPermissions.length="+lengthAuthorPermissions);
            logger.info(fName+"targetedRolesPermissions.length="+lengthTargetRolesPermissions);
            for(int i=0;i<lengthTargetedRoles;i++){
                Role role=gTargetedRoles.get(0);
                logger.info(fName+"roles2check["+i+"]="+role.getId()+"|"+role.getName());
                targetedRolesPermissions=getPermissionJSON(targetedRolesPermissions, role.getPermissions());
            }
            logger.info(fName+"not implemented");
            return false;
        }
        private JSONObject getPermissionJSON( EnumSet<Permission> permissions){
            String fName = "getPermissionJSON";
            logger.info(fName);
            JSONObject json=new JSONObject();
            Iterator itr =permissions.iterator();
            while (itr.hasNext())
            {
                Permission perm = (Permission) itr.next();
                logger.info(fName+"perm:"+perm.getName());
                json.put(perm.getName(),true);
            }
            return json;
        }
        private JSONObject getPermissionJSON(JSONObject json, EnumSet<Permission> permissions){
            String fName = "getPermissionJSON";
            logger.info(fName);
            Iterator itr =permissions.iterator();
            while (itr.hasNext())
            {
                Permission perm = (Permission) itr.next();
                logger.info(fName+"perm:"+perm.getName());
                json.put(perm.getName(),true);
            }
            return json;
        }

        private void add2PreBanList(){
            String fName = "add2PreBan";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldBanList);
                if(banList.has(id)){
                    logger.info(fName+"already on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Already on the list!", llColors.llColorRed);return;
                }

                JSONObject obj=new JSONObject();
                obj.put(keyAuthor,gUser.getId());
                obj.put(keyTimeStamp,gTimestamp.getTime());
                gObjectProfile.jsonUser.getJSONObject(fieldBanList).put(id,obj);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("add2preBan",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+id+" to pre-ban list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void rem4PreBanList(){
            String fName = "rem4PreBan";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldBanList);
                if(!banList.has(id)){
                    logger.info(fName+"not on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Not on the list!", llColors.llColorRed);return;
                }

                gObjectProfile.jsonUser.getJSONObject(fieldBanList).remove(id);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("rem4preBan",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+id+" from pre-ban list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void add2TrollList(){
            String fName = "add2TrollList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldTrollList);
                if(banList.has(id)){
                    logger.info(fName+"already on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Already on the list!", llColors.llColorRed);return;
                }

                JSONObject obj=new JSONObject();
                obj.put(keyAuthor,gUser.getId());
                obj.put(keyTimeStamp,gTimestamp.getTime());
                gObjectProfile.jsonUser.getJSONObject(fieldTrollList).put(id,obj);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("add2preTroll",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+id+" to pre-troll list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void rem4TrollList(){
            String fName = "rem4TrollList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldTrollList);
                if(!banList.has(id)){
                    logger.info(fName+"not on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Not on the list!", llColors.llColorRed);return;
                }

                gObjectProfile.jsonUser.getJSONObject(fieldTrollList).remove(id);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("rem4preTroll",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+id+" from pre-troll list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void add2TroubleList(){
            String fName = "add2Trouble";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldTroubleList);
                if(banList.has(id)){
                    logger.info(fName+"already on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Already on the list!", llColors.llColorRed);return;
                }

                JSONObject obj=new JSONObject();
                obj.put(keyAuthor,gUser.getId());
                obj.put(keyTimeStamp,gTimestamp.getTime());
                gObjectProfile.jsonUser.getJSONObject(fieldTroubleList).put(id,obj);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("add2preTroll",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added "+id+" to pre-troll list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void rem4TroubleList(){
            String fName = "rem4TroubleList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}
                if(gTargetMember!=null){
                    logger.error(fName+"is a member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You can only add users in pre-ban list who are not members", llColors.llColorRed);return;
                }
                if(gTargetUser!=null&&gTargetUser.isBot()){
                    logger.error(fName+"is a main.java.bot");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cant add bots!", llColors.llColorRed);return;
                }

                if(gTargetUser==null){
                    logger.info(fName+"no user selected");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName+".do check for string");
                    int length=items.length;logger.info(fName+".length="+length);
                    for(int i=0;i<length;i++){
                        logger.info(fName+".items["+i+"]="+items[i]);
                        String item=items[i];
                        if((item.contains("<")||item.contains("<@")||item.contains("<@!")||item.contains("<!@"))&&item.contains(">")){
                            id=item;break;
                        }
                    }
                }else{
                    logger.info(fName+"user selected");
                    id=gTargetUser.getId();
                }
                if(id.isEmpty()){
                    logger.error(fName+"no id mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No id mentioned!", llColors.llColorRed);return;
                }
                JSONObject banList= gObjectProfile.getFieldEntry2JSONObject(fieldTroubleList);
                if(!banList.has(id)){
                    logger.info(fName+"not on the list");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Not on the list!", llColors.llColorRed);return;
                }

                gObjectProfile.jsonUser.getJSONObject(fieldTroubleList).remove(id);
                if(!saveProfile()){
                    logger.info(fName+"failed to save table");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to update DB", llColors.llColorRed);return;
                }
                actionLog("rem4preTroll",id);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed "+id+" from pre-troll list", llColors.llColorGreen1);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void printPreBanList(){
            String fName = "printPreBanList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}

                JSONObject json=gObjectProfile.jsonUser.getJSONObject(fieldBanList);
                int length=json.length();
                logger.info(fName+"length="+length);
                Iterator<String> keys=json.keys();
                String tmp="";
                while(keys.hasNext()){
                    tmp+=keys.next()+" ";
                }
                logger.info(fName+"tmp="+tmp);
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Pre-Ban List");
                embed.setColor(llColors.llColorRed);
                embed.addField("Size", String.valueOf(length),true);
                embed.addBlankField(false);
                embed.addField("List",tmp,true);
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void printTrollList(){
            String fName = "printTrollList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}

                JSONObject json=gObjectProfile.jsonUser.getJSONObject(fieldTrollList);
                int length=json.length();
                logger.info(fName+"length="+length);
                Iterator<String> keys=json.keys();
                String tmp="";
                while(keys.hasNext()){
                    tmp+=keys.next()+" ";
                }
                logger.info(fName+"tmp="+tmp);
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Troll List");
                embed.setColor(llColors.llColorRed);
                embed.addField("Size", String.valueOf(length),true);
                embed.addBlankField(false);
                embed.addField("List",tmp,true);
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        private void printTroubleList(){
            String fName = "printTroubleList";
            logger.info(fName);
            try{
                String id="";
                if(!loadedProfile()){logger.error(fName+"DB loading");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't load DB!", llColors.llColorRed);return;}

                JSONObject json=gObjectProfile.jsonUser.getJSONObject(fieldTroubleList);
                int length=json.length();
                logger.info(fName+"length="+length);
                Iterator<String> keys=json.keys();
                StringBuilder tmp= new StringBuilder();
                while(keys.hasNext()){
                    tmp.append(keys.next()).append(" ");
                }
                logger.info(fName+"tmp="+tmp);
                logger.info(fName+"creating embed");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Trouble List");
                embed.setColor(llColors.llColorRed);
                embed.addField("Size", String.valueOf(length),true);
                embed.addBlankField(false);
                embed.addField("List", tmp.toString(),true);
                logger.info(fName+"building embed");
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }
        String gTable="profileGuild";
        String gName="utilityUser";
        lcJSONObjProfile gObjectProfile;
        private void actionLog(String name,String target){
            String fName = "[safetyUserProfileEntry]";
            logger.info(fName);  logger.info(fName+"name="+name);  logger.info(fName+"target="+target);
            JSONObject objLog=new JSONObject();
            objLog.put(keyUser,target);
            objLog.put(keyTimeStamp,gTimestamp.getTime());
            llLogAction(gGlobal,gGuild, gUser, name,objLog);
        }
        private Boolean loadedProfile() {
            String fName = "[loadedProfile]";
            logger.info(fName);
            gObjectProfile = new lcJSONObjProfile(gGlobal, gName, gGuild);
            if (gObjectProfile.getProfile(gTable)) {
                logger.info(fName + ".success");
                safetyUserProfileEntry();
                if(gObjectProfile.isUpdated){
                    if (!saveProfile()) {
                        logger.error(fName + ".failed to write to DB"); return false;
                    }
                    logger.info(fName + ".updated");
                    return true;
                }
                logger.info(fName + ".no update needed");
                return true;
            }
            logger.info(fName + ".creating default profile");
            safetyUserProfileEntry();
            if (!saveProfile()) {
                logger.error(fName + ".failed to write to DB");
                return false;
            }
            if (gObjectProfile.isProfile()) {
                logger.info(fName + ".success");
                return true;
            }
            logger.warn(fName + ".failed");
            return false;
        }
        private Boolean saveProfile() {
            String fName = "[saveProfile]";
            logger.info(fName);
            if (gObjectProfile.saveProfile(gTable)) {
                logger.info(fName + ".success");
                return true;
            }
            logger.warn(fName + ".failed");
            return false;
        }
        String fieldBanList="banList";String fieldTrollList="trollList";String fieldTroubleList="troubleList";
        String fieldProfile="profile";
        String keyBanEnabled="banEnabled";  String keyTimeStamp="timestamp"; String keyAuthor="author";String keyAction="action";String keyUser="user";
        private void safetyUserProfileEntry() {
            String fName = "[safetyUserProfileEntry]";
            logger.info(fName + ".safety check");
            String field;
            field=fieldProfile;
            gObjectProfile.safetyCreateFieldEntry(field);
            gObjectProfile.safetyPutFieldEntry(field,keyBanEnabled ,false);
            field=fieldBanList;
            gObjectProfile.safetyCreateFieldEntry(field);
            field=fieldTrollList;
            gObjectProfile.safetyCreateFieldEntry(field);
            field=fieldTroubleList;
            gObjectProfile.safetyCreateFieldEntry(field);

        }
        private void userDataReset(){
            String fName = "userDataReset";
            logger.info(fName);
            try{
                Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,null,"Do you wish to reset your user data stored for the bot? :green_book: yes or :closed_book: no", llColors.llColorPurple2);
                message.addReaction(gGlobal.emojis.getEmoji("green_book")).complete();
                message.addReaction(gGlobal.emojis.getEmoji("closed_book")).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("green_book"))){
                                    logger.info(fName + ".approved");
                                    Message message2=lsMessageHelper.lsSendMessageResponse(gUser,"Resetting your userdata...");
                                    String command="reset";
                                    new rdUtility(gGlobal,gGuild,gUser,command);
                                    new character(gGlobal,gGuild,gUser,command);
                                    new fursona(gGlobal,gGuild,gUser,command);
                                    new Patient(gGlobal,gGuild,gUser,command);
                                    new usersona(gGlobal,gGuild,gUser,command);
                                    new ncharacter(gGlobal,gGuild,gUser,command);
                                    new nfursona(gGlobal,gGuild,gUser,command);
                                    new nPatient(gGlobal,gGuild,gUser,command);
                                    new nusersona(gGlobal,gGuild,gUser,command);
                                    new nSlaveRegistry(gGlobal,gGuild,gUser,command);
                                    new lewdverify(gGlobal,gGuild,gUser,command);
                                    new diaperInteractive(gGlobal,gGuild,gUser,command);
                                    new chastitysession(gGlobal,gGuild,gUser,command);
                                    lsMessageHelper.lsMessageDelete(message2);
                                    lsMessageHelper.lsSendMessageResponse(gUser,"Resetting done");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("closed_book"))){
                                    logger.info(fName + ".reject");
                                }
                                lsMessageHelper.lsMessageDelete(message);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            lsMessageHelper.lsMessageDelete(message);
                        });

            }catch (Exception e){
                logger.error(fName+"exception:"+e);
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }
        }


    }

}
