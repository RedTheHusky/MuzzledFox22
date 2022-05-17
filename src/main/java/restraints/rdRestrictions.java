package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcChannelPermissionOverride;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llPermission;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.PostSynthesizer.rdVoicePostSynthesizer;
import restraints.in.iRestraints;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.enums.GAGLEVELS;
import restraints.models.lcBDSMGuildProfiles;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdRestrictions extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints,  llPermission {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String cName="[rdRestrictions]";
	String sRTitle="Restrictions";
    public rdRestrictions(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Restrictions-DiscordRestraints";
        this.help = "rdRestrictions";
        this.aliases = new String[]{"restriction","restrictions"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdRestrictions(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdRestrictions(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd, boolean isRunaway){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,isRunaway);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent gEvent) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(gEvent);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable{
        String cName="[runLocal]";
        CommandEvent gEvent;String gRawForward="";boolean gIsForward=false,gIsRunaway=false, gIsOverride =false;
        Guild gGuild; User gUser;Member gMember; TextChannel gTextChannel;
        public runLocal(CommandEvent ev){
            logger.info(".run build");
            gEvent=ev;
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            gEvent=ev;gIsForward=isForward;gRawForward=forward;
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,boolean isRunaway){
            logger.info(".run build");
            gEvent=ev;
            gIsForward=isForward;gRawForward=forward;gIsRunaway=isRunaway;
        }
        lcBDSMGuildProfiles gBDSMCommands;
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                gUser = gEvent.getAuthor();
                gMember = gEvent.getMember();
                gGuild = gEvent.getGuild();
                logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gTextChannel = gEvent.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
                gBDSMCommands.restraints.init();
                lsUsefullFunctions.setThreadName4Display("rdold-Restrictions");
                if(!gBDSMCommands.restraints.getEnable()) {
                    logger.info(fName + "its disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                    logger.info(fName+"its not allowed by channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                    logger.info(fName+"its not allowed by roles");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                loadValues();
                updateIsAdult();
                if(!isAdult&&bdsmRestriction==1){blocked();return;}
                else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                String[] items;
                boolean isInvalidCommand = true;
                logger.info(fName+".gIsRunaway="+gIsRunaway);
                logger.info(fName+".gIsForward="+gIsForward);
                if(gIsRunaway){
                    if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    clearAllRestriction();isInvalidCommand=false;
                }else
                if(gIsForward&&(gRawForward.isBlank()||gRawForward.isEmpty())){
                    logger.info(fName+".gRawForward is null");
                    rHelp("main");
                    isInvalidCommand=false;
                }else
                if(gIsForward&&(gRawForward.equalsIgnoreCase("help"))){
                    logger.info(fName+".gRawForward=help");
                    rHelp("main");
                    isInvalidCommand=false;
                }else
                if((!gIsForward&&gEvent.getArgs().isEmpty())){
                    logger.info(fName+".Args=0");
                    rHelp("main");
                    isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    if(gEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                    logger.info(fName + ".gRawForward="+gRawForward);
                    logger.info(fName + ".gRawForward:"+gRawForward.isEmpty()+"|"+gRawForward.isBlank());
                    if(!gRawForward.isEmpty()&&!gRawForward.isBlank()){
                        items = gRawForward.split("\\s+");
                    }
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("setup")){
                        if(!(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Request admin, manage server or manage channel permission.",llColorRed);
                            isInvalidCommand=false;
                        }else{
                            isInvalidCommand=false;
                            if(items.length<3){
                                menuSetup();
                            }else{
                                if(items[1].equalsIgnoreCase("exceptionmembers")){
                                    switch (items[2].toLowerCase()){
                                        case "add":
                                            setupAdd2Member();
                                            break;
                                        case "rem":
                                        case "remove":
                                            setupRem2Member();
                                            break;
                                        case "list":
                                            setupViewList(fieldBannedUsers);
                                            break;
                                    }
                                }
                                if(items[1].equalsIgnoreCase("deniedchannels")){
                                    switch (items[2].toLowerCase()){
                                        case "add":
                                            setupAdd2Channel();
                                            break;
                                        case "rem":
                                        case "remove":
                                            setupRem2Channel();
                                            break;
                                        case "list":
                                            setupViewList(fieldBannedChannels);
                                            break;
                                    }
                                }
                            }
                        }

                    }
                    else if(isInvalidCommand){
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);isInvalidCommand=false;
                    }
                    else if(!vEnabled){
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"It's not enabled!",llColorRed);isInvalidCommand=false;
                    }
                    if(isInvalidCommand&&(items[0].contains("<!@")||items[0].contains("<@"))&&items[0].contains(">")){
                        logger.info(fName+".detect mention characters");
                        Member target;
                        List<Member>mentions=gEvent.getMessage().getMentionedMembers();
                        if(mentions.isEmpty()){
                            logger.warn(fName+".zero member mentions in message>check itemns[0]");
                            target=llGetMember(gGuild,items[0]);
                        }else{
                            logger.info(fName+".member mentions in message");
                            target=mentions.get(0);
                        }

                        if(target==null){
                            logger.warn(fName+".zero member mentions");
                        }
                        else if(target.getId().equalsIgnoreCase(gEvent.getAuthor().getId())){
                            logger.warn(fName+".target cant be the gUser");items= lsUsefullFunctions.RemoveFirstElement4ItemsArg(items);
                            //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,"You can't target yourself.", llColorRed);
                        }
                        else if(items.length<2){
                            logger.warn(fName+".invalid args length");
                        }else{
                            if(items[1].equalsIgnoreCase(optionVoiceMute)||items[1].equalsIgnoreCase(optionVoiceDeafen)||items[1].equalsIgnoreCase(optionVoiceUnmute)||items[1].equalsIgnoreCase(optionVoiceUndeafen)){
                                rVoiceRestriction(target,".",items[1]);isInvalidCommand=false;
                            }else
                            if(items.length>=3&&(items[1].equalsIgnoreCase(nGag)||items[1].equalsIgnoreCase(nEarMuffs))&&(items[2].equalsIgnoreCase(optionEnable)||items[2].equalsIgnoreCase(optionDisable))){
                                rVoiceRestriction(target,items[1],items[2]);isInvalidCommand=false;
                            }else
                            if(items[1].equalsIgnoreCase("clearall")||items[1].equalsIgnoreCase("clear")){
                                rClearChannels(target,items[1]);isInvalidCommand=false;
                            }else
                            if(items[1].equalsIgnoreCase(nRestrictionDeny)||items[1].equalsIgnoreCase("restrict")){
                                if(items.length>=3&&(items[2].equalsIgnoreCase(nPermissionText_Read)||items[2].equalsIgnoreCase(nPermissionText_Write)||items[2].equalsIgnoreCase(nPermissionText_History)||items[2].equalsIgnoreCase(nPermissionText_AddReactions)||items[2].equalsIgnoreCase(nPermissionText_File)||items[2].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                    rRestrictChannels(target,items[2]);isInvalidCommand=false;
                                }else{
                                    rHelp("main");isInvalidCommand=false;
                                }
                            }else
                            if(items[1].equalsIgnoreCase(nRestrictionGrant)||items[1].equalsIgnoreCase("allow")){
                                if(items.length>=3&&(items[2].equalsIgnoreCase(nPermissionText_Read)||items[2].equalsIgnoreCase(nPermissionText_Write)||items[2].equalsIgnoreCase(nPermissionText_History)||items[2].equalsIgnoreCase(nPermissionText_AddReactions)||items[2].equalsIgnoreCase(nPermissionText_File)||items[2].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                    rAllowChannels(target,items[2]);isInvalidCommand=false;
                                }else{
                                    rHelp("main");isInvalidCommand=false;
                                }
                            }else
                            if(items.length>=3&&(items[2].equalsIgnoreCase("clearall")||items[2].equalsIgnoreCase("clear"))){
                                rClearChannels(target,items[2]);isInvalidCommand=false;
                            }else
                            if(items.length>=3&&(items[2].equalsIgnoreCase(nRestrictionDeny)||items[2].equalsIgnoreCase("restrict"))){
                                if(items.length>=4&&(items[3].equalsIgnoreCase(nPermissionText_Read)||items[3].equalsIgnoreCase(nPermissionText_Write)||items[3].equalsIgnoreCase(nPermissionText_History)||items[3].equalsIgnoreCase(nPermissionText_AddReactions)||items[3].equalsIgnoreCase(nPermissionText_File)||items[3].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                    rRestrictChannels(target,items[3]);isInvalidCommand=false;
                                }else{
                                    rHelp("main");isInvalidCommand=false;
                                }
                            }else
                            if(items.length>=3&&(items[2].equalsIgnoreCase(nRestrictionGrant)||items[2].equalsIgnoreCase("allow"))){
                                if(items.length>=4&&(items[3].equalsIgnoreCase(nPermissionText_Read)||items[3].equalsIgnoreCase(nPermissionText_Write)||items[3].equalsIgnoreCase(nPermissionText_History)||items[3].equalsIgnoreCase(nPermissionText_AddReactions)||items[3].equalsIgnoreCase(nPermissionText_File)||items[3].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                    rAllowChannels(target,items[3]);isInvalidCommand=false;
                                }else{
                                    rHelp("main");isInvalidCommand=false;
                                }
                            }
                        }
                    }
                    if(isInvalidCommand){
                        if(items==null||items.length==0){
                            logger.warn(fName+".blank command");
                        }else
                        if(items[0].equalsIgnoreCase(optionVoiceMute)||items[0].equalsIgnoreCase(optionVoiceDeafen)||items[0].equalsIgnoreCase(optionVoiceUnmute)||items[0].equalsIgnoreCase(optionVoiceUndeafen)){
                            rVoiceRestriction(".",items[0]);isInvalidCommand=false;
                        }else
                        if(items.length>=2&&(items[0].equalsIgnoreCase(nGag)||items[0].equalsIgnoreCase(nEarMuffs))&&(items[1].equalsIgnoreCase(optionEnable)||items[1].equalsIgnoreCase(optionDisable))){
                            rVoiceRestriction(items[0],items[1]);isInvalidCommand=false;
                        }else
                        if(items[0].equalsIgnoreCase("clearall")||items[0].equalsIgnoreCase("clear")){
                            rClearChannels(items[0]);isInvalidCommand=false;
                        }else
                        if(items[0].equalsIgnoreCase(nRestrictionDeny)||items[0].equalsIgnoreCase("restrict")){
                            if(items.length>=2&&(items[1].equalsIgnoreCase(nPermissionText_Read)||items[1].equalsIgnoreCase(nPermissionText_Write)||items[1].equalsIgnoreCase(nPermissionText_History)||items[1].equalsIgnoreCase(nPermissionText_AddReactions)||items[1].equalsIgnoreCase(nPermissionText_File)||items[1].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                rRestrictChannels(items[1]);isInvalidCommand=false;
                            }else{
                                rHelp("main");isInvalidCommand=false;
                            }
                        }else
                        if(items[0].equalsIgnoreCase(nRestrictionGrant)||items[0].equalsIgnoreCase("allow")){
                            if(items.length>=2&&(items[1].equalsIgnoreCase(nPermissionText_Read)||items[1].equalsIgnoreCase(nPermissionText_Write)||items[1].equalsIgnoreCase(nPermissionText_History)||items[1].equalsIgnoreCase(nPermissionText_AddReactions)||items[1].equalsIgnoreCase(nPermissionText_File)||items[1].equalsIgnoreCase(nPermissionText_ExternalEmojis))){
                                rAllowChannels(items[1]);isInvalidCommand=false;
                            }else{
                                rHelp("main");isInvalidCommand=false;
                            }
                        }
                    }
                }
                if(isInvalidCommand){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(".run ended");
        }
        boolean isAdult=false;
        private void blocked(){
            String fName = "[blocked]";
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Require NSFW channel or server.",llColorRed);
            logger.info(fName);
        }
        int bdsmRestriction=0;
        private void updateIsAdult(){
            String fName="[updateIsAdult]";
            logger.info(fName);
            if(gTextChannel.isNSFW()){
                logger.info(fName+"channel is nsfw"); isAdult=true; return;
            }
            if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
                logger.info(fName+"guild is adult"); isAdult=true; return;
            }
            bdsmRestriction= gBDSMCommands.getBDSMRestriction();
            logger.info(fName+"is safe");
        }
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            String quickSummonWithSpace2=llPrefixStr+"restriction <@Pet> ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            embed.addField("What is it?", "Allows owners and sec-owners to apply permission restriction to the pet!\nIt literally creates a channel permission override where the pet would have limited permission on that channel!",false);
            embed.addField("Limitations","Can't do voice channels, just text channels.\nNot all text channels can be restricted, it will exclude those that their name contains bot and staff.",false);
            if(vEnabled){
                embed.addField("To Restrict Permission","`"+quickSummonWithSpace2+" deny <Permission> [mention channels]` to deny that permission on the channel",false);
                embed.addField("To Allow Permission","`"+quickSummonWithSpace2+" allow <Permission> [mention channels]` to allow that permission on the channel",false);
                embed.addField("To Clear","`"+quickSummonWithSpace2+" clearall` to clear all channel restrictions!",false);
                embed.addField("Values","[mention channels] -optional value if you want to target mentioned channel instead the channel where the command is posted\n<Permission> -required value to know what to deny or allow",false);
                embed.addField("Permission","`read` = reading the channel\n`write` =posting tp channel\n`history` =reading old posts\n`reactions` = adding reactions\n`external` =using external emojis",false);
                embed.addField("Voice","Restricting the subs access to voice channels.\n`"+quickSummonWithSpace2+" "+optionVoiceMute+"|"+optionVoiceUnmute+"|"+optionVoiceDeafen+"|"+optionVoiceUndeafen+"`.",false);
                embed.addField("Gag&EarMuffs","Enabling gag and earmuffs restraints to enforce voice channels restrictions.\nGag will enforce a mute restriction.\nEarmuffs will enforce a deaf restriction.\nGag level "+ GAGLEVELS.Faux.getName() +" wont enforce mute.\n`"+quickSummonWithSpace2+" "+nGag+"|"+nEarMuffs+" "+optionEnable+"|"+optionDisable+"`.",false);

            }else{
                embed.addField("Houston, we have a problem ", "This feature is not enabled.",false);

            }
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                embed.addField("Setup", "Use the command `"+llPrefixStr+"restriction setup` to access the setup menu to enable or disable this feature.",false);
            }
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private void rClearChannels(String command) {
            String fName = "[rClearChannels]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&isPetOwned(gUserProfile)){
                logger.warn(fName + ".pet is owned, has no access to this functions");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are owned!", llColorRed); return;
            }
            if(command.equalsIgnoreCase("clearall")){
                if(gUserProfile.jsonObject.has(nChannelRestrictions)&&gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).isEmpty()){
                    logger.info(fName+".has no channels to clear");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Has nothing to clear!", llColorRed);return;
                }
                clearAllRestriction();
            }else
            if(command.equalsIgnoreCase("clear")){
                List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
                if(channels.isEmpty()){
                    logger.info(fName+"no mentioned channels, use even channel");
                    String result= clearRestrictionOnChannel(gTextChannel);
                    if(!result.equalsIgnoreCase("done")){
                        llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong+result, llColorRed); return;
                    }
                    llSendQuickEmbedMessage(gUser,sRTitle,"Cleared chanel "+gTextChannel.getAsMention()+".",  llColorGreen2);
                }else{
                    logger.info(fName+"mentioned channels="+channels.size());
                    String names="";
                    for(TextChannel channel: channels){
                        logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                        String result= clearRestrictionOnChannel(channel);
                        if(!result.equalsIgnoreCase("done")){
                            llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4Channel+channel.getAsMention()+": "+result, llColorRed); //return;
                        }else
                        if(!names.isBlank()){
                            names=", "+channel.getAsMention();
                        }else{
                            names=channel.getAsMention();
                        }
                    }
                    llSendQuickEmbedMessage(gUser,sRTitle,"Cleared channels "+name+".", llColorGreen2);
                }
            }
        }
        private void rVoiceRestriction(Member mtarget,String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!hasUserOwnerAccess(gUserProfile,gUser)&&!hasUserSecOwnerAccess(gUserProfile,gUser)){
                logger.warn(fName + ".not owner or secc owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are not their owner or sec-owner!", llColorRed); return;
            }
            if(category.equalsIgnoreCase(nGag)&&command.equalsIgnoreCase(optionEnable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enabled gag voice mute for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" enabled gag voice mute for you.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nGag)&&command.equalsIgnoreCase(optionDisable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disabled gag voice mute for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" disabled gag voice mute for you.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nEarMuffs)&&command.equalsIgnoreCase(optionEnable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enabled earmuffs voice deafen for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" enabled earmuffs voice deafen for you.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nEarMuffs)&&command.equalsIgnoreCase(optionDisable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disabled earmuffs voice deafen for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" disabled earmuffs voice deafen for you.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceMute)){
                if(isMute(gUserProfile.getMember())==1){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't enable, user need to be cleared of any guild voice restrictions.", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceMute,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You apply voice mute to "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" applies voice mute to you.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceUnmute)){
                boolean isREnabled=false;
                if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                    JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);

                    if(jsonObject.has(optionVoiceMute)){
                        isREnabled=jsonObject.getBoolean(optionVoiceMute);
                    }
                }
                if(!isREnabled){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Whooops....", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceMute,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You lift up voice mute from "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" lift up voice mute from you.", llColorBlue3);
                try {
                    gGuild.mute(gUserProfile.getMember(),false).complete();
                    logger.info(fName + ".mute_I lift up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }else
            if(command.equalsIgnoreCase(optionVoiceDeafen)){
                if(isDeafened(gUserProfile.getMember())==1){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't enable, user need to be cleared of any guild voice restrictions.", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You apply voice deafen to "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" applies voice deafen to you.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceUndeafen)){
                boolean isREnabled=false;
                if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                    JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);

                    if(jsonObject.has(optionVoiceDeafen)){
                        isREnabled=jsonObject.getBoolean(optionVoiceDeafen);
                    }
                }
                if(!isREnabled){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Whooops....", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You lift up voice deafen from "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" lift up voice deafen from you.", llColorBlue3);
                try {
                    gGuild.deafen(gUserProfile.getMember(),false).complete();
                    logger.info(fName + ".deafen_I lift up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gUserProfile.getMember().getVoiceState());

        }
        private void rClearChannels(Member mtarget,String command) {
            String fName = "[rClearChannels]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!hasUserOwnerAccess(gUserProfile,gUser)&&!hasUserSecOwnerAccess(gUserProfile,gUser)){
                logger.warn(fName + ".not owner or secc owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are not their owner or sec-owner!", llColorRed); return;
            }
            if(command.equalsIgnoreCase("clearall")){
                //!To DO
                if(gUserProfile.jsonObject.has(nChannelRestrictions)&&gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).isEmpty()){
                    logger.info(fName+".has no channels to clear");
                }else{
                    int detectedProblem=0;
                    Member member=llGetMember(gGuild,gUserProfile.getUser());
                    logger.info(fName+".member="+member.getId()+"|"+member.getUser().getName());
                    logger.info(fName+".has channels to clear");
                    JSONObject channels=gUserProfile.jsonObject.getJSONObject(nChannelRestrictions);
                    Iterator<String>keys=channels.keys();
                    while(keys.hasNext()) {
                        try {
                            String key = keys.next();
                            logger.info(fName+".channelID="+key);
                            channelPermissionOverride=new lcChannelPermissionOverride(member,gGuild,key);
                            if(!channels.isNull(key)) {
                                if(!channelPermissionOverride.isChannel()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else
                                if(!channelPermissionOverride.hasPermissionOverride()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else
                                if(!channelPermissionOverride.isPermissionDenied()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else{
                                    JSONObject permissions=channels.getJSONObject(key);
                                    Iterator<String>permsI=permissions.keys();
                                    while(permsI.hasNext()) {
                                        try {
                                            String name=permsI.next();logger.info(fName + ".name=" + name);
                                            Permission p= lsPermission.lsIsPermission4Name(name);
                                            if(p==null){
                                                logger.warn(fName + ".invalid"); detectedProblem++;
                                            }else{
                                                if(channelPermissionOverride.clearPermissionOverride(p,"Cleared pet channel permissions by "+gUser.getName())){
                                                    gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                                }else{
                                                    detectedProblem++;
                                                    llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Failed to clear channel:"+ lsChannelHelper.lsGetChannelMentionById(gGuild,key),llColorRed_Blood);
                                                }
                                            }
                                        }catch (Exception e){
                                            logger.error(fName + ".exception=" + e);
                                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace())); detectedProblem++;
                                        }
                                    }
                                    //channelPermissionOverride.deletePermissionOverride("Delete pet channel permissions by "+gUser.getName())
                                }
                            }

                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    saveProfile();
                    if(detectedProblem>0){
                        llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Clear command executed by: "+gUser.getAsMention()+".\nCleared channels restriction with "+detectedProblem+" problems.\nThis can be a result of channels not existing, no overrides logged or no permission to perform overrides." ,llColorRed_Barn);
                        llSendQuickEmbedMessage(gUser,sRTitle,"Clear command executed for: "+member.getAsMention()+".\nCleared channels restriction with "+detectedProblem+" problems.\nThis can be a result of channels not existing, no overrides logged or no permission to perform overrides." ,llColorRed_Barn);
                    }else{
                        llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Clear command executed by: "+gUser.getAsMention()+".\nSuccessfully cleared." ,llColorGreen2);
                        llSendQuickEmbedMessage(gUser,sRTitle,"Clear command executed for: "+member.getAsMention()+".\nSuccessfully cleared." ,llColorGreen2);
                    }
                }
            }else
            if(command.equalsIgnoreCase("clear")){
                List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
                if(channels.isEmpty()){
                    logger.info(fName+"no mentioned channels, use even channel");
                    String result= clearRestrictionOnChannel(gTextChannel);
                    if(!result.equalsIgnoreCase("done")){
                        llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4+mtarget.getAsMention()+": "+result, llColorRed); return;
                    }
                    llSendQuickEmbedMessage(gUser,sRTitle,"Cleared chanel "+gTextChannel.getAsMention()+" for "+target.getAsMention(),  llColorGreen2);
                    llSendQuickEmbedMessage(target,sRTitle,"Cleared chanel "+gTextChannel.getAsMention()+" by "+target.getAsMention(),  llColorGreen2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Cleared for "+target.getAsMention()+" chanel "+gTextChannel.getAsMention()+" by "+gUser.getAsMention(),  llColorGreen2);
                }else{
                    logger.info(fName+"mentioned channels="+channels.size());
                    String names="";
                    for(TextChannel channel: channels){
                        logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                        String result= clearRestrictionOnChannel(channel);
                        if(!result.equalsIgnoreCase("done")){
                            llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4+mtarget.getAsMention()+" at "+channel.getAsMention()+": "+result, llColorRed); //return;
                        }else
                        if(!names.isBlank()){
                            names=", "+channel.getAsMention();
                        }else{
                            names=channel.getAsMention();
                        }
                    }
                    llSendQuickEmbedMessage(gUser,sRTitle,"Cleared channels "+name+" for "+target.getAsMention(), llColorGreen2);
                    llSendQuickEmbedMessage(target,sRTitle,"Cleared channels "+name+" by "+gUser.getAsMention(), llColorGreen2);
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Cleared for "+target.getAsMention()+" channels "+name+" by "+gUser.getAsMention(),  llColorGreen2);
                }
            }
        }
        private void rVoiceRestriction(String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!hasPetGotAccess2Restrain(gUserProfile)){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your restrictions as access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gUserProfile.jsonObject.getString(nAccess).equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your restrictions due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(category.equalsIgnoreCase(nGag)&&command.equalsIgnoreCase(optionEnable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enabled gagvoice mute.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nGag)&&command.equalsIgnoreCase(optionDisable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disabled gag voice mute.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nEarMuffs)&&command.equalsIgnoreCase(optionEnable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enabled earmuffs voice deafen.", llColorBlue3);
            }else
            if(category.equalsIgnoreCase(nEarMuffs)&&command.equalsIgnoreCase(optionDisable)){
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disabled earmuffs voice deafen.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceMute)){
                if(isMute(gUserProfile.getMember())==1){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't enable, user need to be cleared of any guild voice restrictions.", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceMute,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enable voice mute.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceUnmute)){
                boolean isREnabled=false;
                if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                    JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);

                    if(jsonObject.has(optionVoiceMute)){
                        isREnabled=jsonObject.getBoolean(optionVoiceMute);
                    }
                }
                if(!isREnabled){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Whooops....", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceMute,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disable voice mute.", llColorBlue3);
                try {
                    gGuild.mute(gUserProfile.getMember(),false).complete();
                    logger.info(fName + ".mute_I lift up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }else
            if(command.equalsIgnoreCase(optionVoiceDeafen)){
                if(isDeafened(gUserProfile.getMember())==1){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't enable, user need to be cleared of any guild voice restrictions.", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,true);
                llSendQuickEmbedMessage(gUser,sRTitle,"You enable voice deafen.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionVoiceUndeafen)){
                boolean isREnabled=false;
                if(gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                    JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);

                    if(jsonObject.has(optionVoiceDeafen)){
                        isREnabled=jsonObject.getBoolean(optionVoiceDeafen);
                    }
                }
                if(!isREnabled){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Whooops....", llColorRed_Chili);
                    return;
                }
                gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,false);
                llSendQuickEmbedMessage(gUser,sRTitle,"You disable voice deafen.", llColorBlue3);
                try {
                    gGuild.deafen(gUserProfile.getMember(),false).complete();
                    logger.info(fName + ".deafen_I lift up for:"+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+") at:"+gGuild.getName()+"("+gGuild.getId()+")");
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gUserProfile.getMember().getVoiceState());
        }
        String strSomethingWentWrong="Something went wrong: ";
        String strSomethingWentWrong4Channels="Something went wrong for channel: ";
        String strSomethingWentWrong4Channel="Something went wrong for channel: ";
        String strSomethingWentWrong4="Something went wrong for ";
        private void rRestrictChannels(String perm) {
            String fName = "[rRestrictChannels]";
            logger.info(fName);
            //logger.warn(fName + ".blocked");
            //llSendQuickEmbedMessage(gUser,sRTitle,"Denied. Only owner&secowner allowed to change it!", llColorRed);
            logger.info(fName + ".perm=" + perm);
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&isPetOwned(gUserProfile)){
                logger.warn(fName + ".pet is owned, has no access to this functions");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are owned!", llColorRed); return;
            }
            Permission permission=getPermission(perm);
            if(permission==null){
                logger.warn(fName + ".invalid name");
                llSendQuickEmbedMessage(gUser,sRTitle,"Invalid permission name", llColorRed); return;
            }
            if(permission==Permission.MESSAGE_READ||permission==Permission.MESSAGE_WRITE){
                logger.warn(fName + ".pet cant deny read and write restrictions");
                llSendQuickEmbedMessage(gUser,sRTitle,"You can't deny your own read&write permission for the channels. Only owners and sec-owners are allowed to do it.", llColorRed); return;
            }

            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            if(channels.isEmpty()){
                logger.info(fName+"no mentioned channels, use even channel");
                if(isChannelBlocked(gTextChannel)){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Channel is blocked to be restricted!", llColorRed); return;
                }
                String result=denyRestriction(gTextChannel,permission);
                if(!result.equalsIgnoreCase("done")){
                    llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong+result, llColorRed); return;
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You applied the restriction "+permission.getName()+" to "+gTextChannel.getAsMention()+".", llColorRed);
            }else{
                logger.info(fName+"mentioned channels="+channels.size());
                String names="";
                for(TextChannel channel: channels){
                    logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                    if(isChannelBlocked(channel)){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Channel "+channel.getAsMention()+" is blocked to be restricted!", llColorRed);
                    }else{
                        String result=denyRestriction(channel,permission);
                        if(!result.equalsIgnoreCase("done")){
                            llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4Channels+channel.getName()+": "+result, llColorRed); //return;
                        }else
                        if(!names.isBlank()){
                            names=", "+channel.getAsMention();
                        }else{
                            names=channel.getAsMention();
                        }
                    }
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You applied the restriction "+permission.getName()+" to "+names+".", llColorRed);
            }
            saveProfile();
        }
        private void rRestrictChannels(Member mtarget,String perm) {
            String fName = "[rRestrictChannels]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".perm="+perm);
            if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!hasUserOwnerAccess(gUserProfile,gUser)&&!hasUserSecOwnerAccess(gUserProfile,gUser)){
                logger.warn(fName + ".not owner or secc owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are not their owner or sec-owner!", llColorRed); return;
            }
            Permission permission=getPermission(perm);
            if(permission==null){
                logger.warn(fName + ".invalid name");
                llSendQuickEmbedMessage(gUser,sRTitle,"Invalid permission name", llColorRed); return;
            }

            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            if(channels.isEmpty()){
                logger.info(fName+"no mentioned channels, use even channel");
                if((permission==Permission.MESSAGE_READ||permission==Permission.MESSAGE_WRITE)&&(gTextChannel.getName().contains("bot")||gTextChannel.getName().contains("staff")||gTextChannel.getName().contains("feedbacks"))){
                    llSendQuickEmbedMessage(gUser,sRTitle,"This channel contains keywords that prevents it from  restricting read or write for the pet.", llColorRed); return;
                }
                if(isChannelBlocked(gTextChannel)){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Channel " + gTextChannel.getAsMention() + " is blocked to be restricted!", llColorRed);return;
                }
                String result=denyRestriction(gTextChannel,permission);
                if(!result.equalsIgnoreCase("done")){
                    llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4+mtarget.getAsMention()+": "+result, llColorRed); return;
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You applied the restriction "+permission.getName()+" to "+gTextChannel.getAsMention()+" for "+target.getAsMention(), llColorRed);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" applied the restriction "+permission.getName()+" to "+gTextChannel.getAsMention()+" for you.", llColorGreen2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" applied the restriction "+permission.getName()+" to "+gTextChannel.getAsMention()+" for "+target.getAsMention(), llColorGreen2);
            }else{
                logger.info(fName+"mentioned channels="+channels.size());
                String names="";
                for(TextChannel channel: channels){
                    logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                    if((permission==Permission.MESSAGE_READ||permission==Permission.MESSAGE_WRITE)&&(channel.getName().contains("bot")||channel.getName().contains("staff")||channel.getName().contains("feedbacks"))){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Channel "+channel.getAsMention()+" contains keywords that prevents it from  restricting read or write for the pet.", llColorRed);
                    }else{
                        if(isChannelBlocked(channel)) {
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Channel " + channel.getAsMention() + " is blocked to be restricted!", llColorRed);
                        }else{
                            String result=denyRestriction(channel,permission);
                            if(!result.equalsIgnoreCase("done")){
                                llSendQuickEmbedMessage(gUser,sRTitle,strSomethingWentWrong4+mtarget.getAsMention()+" at "+channel.getAsMention()+": "+result, llColorRed); //return;
                            }else
                            if(!names.isBlank()){
                                names=", "+channel.getAsMention();
                            }else{
                                names=channel.getAsMention();
                            }
                        }
                    }
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You applied the restriction "+permission.getName()+" to "+names+" for "+target.getAsMention(), llColorRed);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getAsMention()+" applied the restriction "+permission.getName()+" to "+names+" for you.", llColorGreen2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getAsMention()+" applied the restriction "+permission.getName()+" to "+names+" for "+target.getAsMention(), llColorGreen2);
            }
            saveProfile();
        }
        private void rAllowChannels(String perm) {
            String fName = "[rAllowChannels]";
            logger.info(fName);
            logger.info(fName + ".perm=" + perm);
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&isPetOwned(gUserProfile)){
                logger.warn(fName + ".pet is owned, has no access to this functions");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are owned!", llColorRed); return;
            }

            Permission permission=getPermission(perm);
            if(permission==null){
                logger.warn(fName + ".invalid name");
                llSendQuickEmbedMessage(gUser,sRTitle,"Invalid permission name", llColorRed); return;
            }

            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            if(channels.isEmpty()){
                logger.info(fName+"no mentioned channels, use even channel");
                String result=clearRestriction(gTextChannel,permission);
                if(!result.equalsIgnoreCase("done")){
                    llSendQuickEmbedMessage(gUser,sRTitle,result, llColorRed); return;
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You cleared the restriction "+permission.getName()+" on "+gTextChannel.getAsMention()+".", llColorRed);
            }else{
                logger.info(fName+"mentioned channels="+channels.size());
                String names="";
                for(TextChannel channel: channels){
                    logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                    String result=clearRestriction(channel,permission);
                    if(!result.equalsIgnoreCase("done")){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Something went wrong for channel "+channel.getAsMention()+": "+result, llColorRed); //return;
                    }else
                    if(!names.isBlank()){
                        names=", "+channel.getAsMention();
                    }else{
                        names=channel.getAsMention();
                    }
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You cleared the restriction "+permission.getName()+" on "+names+".", llColorRed);
            }
            saveProfile();
        }
        private void rAllowChannels(Member mtarget,String perm) {
            String fName = "[rRestrictChannels]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".perm="+perm);
            if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!hasUserOwnerAccess(gUserProfile,gUser)&&!hasUserSecOwnerAccess(gUserProfile,gUser)){
                logger.warn(fName + ".not owner or secc owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, you are not their owner or sec-owner!", llColorRed); return;
            }
            Permission permission=getPermission(perm);
            if(permission==null){
                logger.warn(fName + ".invalid name");
                llSendQuickEmbedMessage(gUser,sRTitle,"Invalid permission name", llColorRed); return;
            }

            List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
            if(channels.isEmpty()){
                logger.info(fName+"no mentioned channels, use even channel");
                String result=clearRestriction(gTextChannel,permission);
                if(!result.equalsIgnoreCase("done")){
                    llSendQuickEmbedMessage(gUser,sRTitle,result, llColorRed); return;
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You cleared the restriction "+permission.getName()+" on "+gTextChannel.getName()+" for "+target.getName(), llColorRed);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getName()+" cleared the restriction "+permission.getName()+" on "+gTextChannel.getName()+" for you.", llColorGreen2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getName()+" cleared the restriction "+permission.getName()+" to "+gTextChannel.getName()+" for "+target.getName(), llColorGreen2);
            }else{
                logger.info(fName+"mentioned channels="+channels.size());
                String names="";
                for(TextChannel channel: channels){
                    logger.info(fName+"mentioned channels: "+channel.getId()+"|"+channel.getName());
                    String result=clearRestriction(channel,permission);
                    if(!result.equalsIgnoreCase("done")){
                        llSendQuickEmbedMessage(gUser,sRTitle,"Something went wrong for channel "+channel.getAsMention()+": "+result, llColorRed);
                    }else
                    if(!names.isBlank()){
                        names=", "+channel.getName();
                    }else{
                        names=channel.getName();
                    }
                }
                llSendQuickEmbedMessage(gUser,sRTitle,"You cleared the restriction "+permission.getName()+" on "+names+" for "+target.getName(), llColorRed);
                llSendQuickEmbedMessage(target,sRTitle,gUser.getName()+" cleared the restriction "+permission.getName()+" to "+names+" for you.", llColorGreen2);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,gUser.getName()+" cleared the restriction "+permission.getName()+" to "+names+" for "+target.getName(), llColorGreen2);
            }
            saveProfile();
        }

        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        private String denyRestriction(TextChannel channel, Permission permission){
            String fName="[denyRestriction]";
            try{
                Member member=llGetMember(gGuild,gUserProfile.getUser());
                logger.info(fName+".member="+member.getId()+"|"+member.getUser().getName());
                logger.info(fName+".channel:"+channel.getId()+"|"+channel.getName());
                logger.info(fName+".permission="+permission.getName());
                channelPermissionOverride=new lcChannelPermissionOverride(member,channel);
                boolean permSet,permChecked;
                if(!llIsPermissionValid4Channel(permission,channel)){
                    logger.error(fName+".member["+member.getId()+"]"+".not valid permission for text channel");return "Invalid permission!";
                }
                permSet=channelPermissionOverride.denyPermissionOverride(permission, "Set deny permission for pet by "+gUser.getName());
                permChecked=channelPermissionOverride.isPermissionDenied(permission);
                if(!permSet){
                    logger.warn(fName+".member["+member.getId()+"]"+".failed to set permission: "+permission.getName());
                    return "Failed to set permission";
                }
                if(!gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).has(channel.getId())){
                    gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(channel.getId(), new JSONObject());
                }
                gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).getJSONObject(channel.getId()).put(permission.getName(),true);
                if(!permChecked){
                    logger.warn(fName+".member["+member.getId()+"]"+".perm check failed: "+permission.getName());
                    //return "Perm check failed";
                }
                logger.info(fName+".done");
                return "done";
            } catch (Exception e) {
                logger.error(fName+".exception="+e);return "Exception= "+e;
            }
        }
        private String clearRestriction(TextChannel channel, Permission permission){
            String fName="[clearRestriction]";
            try{
                Member member=llGetMember(gGuild,gUserProfile.getUser());
                logger.info(fName+".member="+member.getId()+"|"+member.getUser().getName());
                logger.info(fName+".channel:"+channel.getId()+"|"+channel.getName());
                logger.info(fName+".permission="+permission.getName());
                channelPermissionOverride=new lcChannelPermissionOverride(member,channel);
                boolean permSet,permChecked;
                if(!llIsPermissionValid4Channel(permission,channel)){
                    logger.error(fName+".member["+member.getId()+"]"+".not valid permission for text channel");return "Permission is invalid for this channel!";
                }
                if(!gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).has(channel.getId())){
                    logger.error(fName+".member["+member.getId()+"]"+".no such channel in record");return "No such channel in user record!";
                }
                if(!gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).getJSONObject(channel.getId()).has(permission.getName())){
                    logger.error(fName+".member["+member.getId()+"]"+".no such permission in record");return "No such channel permission in user record!";
                }
                permSet=channelPermissionOverride.clearPermissionOverride(permission, "Clear deny permission for pet by "+gUser.getName());
                permChecked=channelPermissionOverride.isPermissionDenied(permission);
                if(!permSet){
                    logger.warn(fName+".member["+member.getId()+"]"+".failed to set permission: "+permission.getName());return  "Failed to set permission";
                }
                gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).getJSONObject(channel.getId()).put(permission.getName(),false);
                if(!permChecked){
                    logger.warn(fName+".member["+member.getId()+"]"+".perm check failed: "+permission.getName()); //return "Perm check failed!";
                }
                logger.info(fName+".done");
                return "done";
            } catch (Exception e) {
                logger.error(fName+".exception="+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "exception:"+e;
            }
        }
        private String clearRestrictionOnChannel(TextChannel channel){
            String fName="[clearRestrictionOnChannel]";
            try{
                Member member=llGetMember(gGuild,gUserProfile.getUser());
                logger.info(fName+".member="+member.getId()+"|"+member.getUser().getName());
                logger.info(fName+".channel:"+channel.getId()+"|"+channel.getName());
                if(gUserProfile.jsonObject.has(nChannelRestrictions)&&gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).has(channel.getId())){
                    logger.info(fName+".has channel");
                    channelPermissionOverride=new lcChannelPermissionOverride(member,channel);
                    if(channelPermissionOverride.hasPermissionOverride()){
                        if(!gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).has(channel.getId())){
                            logger.error(fName+".member["+member.getId()+"]"+".no such channel in record");return "No such channel on user record!";
                        }
                        int detectedProblem=0;
                        JSONObject permissions=gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).getJSONObject(channel.getId());
                        Iterator<String>permsI=permissions.keys();
                        while(permsI.hasNext()) {
                            try {
                                String name=permsI.next();logger.info(fName + ".name=" + name);
                                Permission p=lsPermission.lsIsPermission4Name(name);
                                if(p==null){
                                    logger.warn(fName + ".invalid");
                                }else{
                                    if(channelPermissionOverride.clearPermissionOverride(p,"Cleared pet channel permissions by "+gUser.getName())){
                                        gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(channel.getId(),new JSONObject());
                                    }else{
                                        detectedProblem++;
                                        llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Failed to clear "+name+" from channel:"+ channel.getAsMention(),llColorRed_Blood);
                                    }
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace())); detectedProblem++;
                            }
                        }
                        //channelPermissionOverride.deletePermissionOverride("Delete pet channel permissions by "+gUser.getName());
                        //gUserProfile.jsonUser.getJSONObject(nChannelRestrictions).put(channel.getId(),new JSONObject());
                        saveProfile();
                        if(detectedProblem>0){
                            return "problems detected: "+detectedProblem;
                        }
                        return "done";
                    }else{
                        logger.warn(fName+".member["+member.getId()+"]"+".has no override at channel="+channel.getId());
                        return "Has no override on channel!";
                    }
                }else{

                    logger.warn(fName+".member["+member.getId()+"]"+".has no such channel="+channel.getId());
                    return "Has no such channel!";
                }
            } catch (Exception e) {
                logger.error(fName+".exception="+e); saveProfile(); logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "Exception="+e;
            }
        }
        private void clearAllRestriction(){
        String fName="clearAllRestriction";
            try{
                if(gUserProfile.jsonObject.has(nChannelRestrictions)&&gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).isEmpty()){
                    logger.info(fName+".has no channels to clear");
                }else{
                    logger.info(fName+".has channels to clear");
                    int detectedProblem=0;
                    Member member=llGetMember(gGuild,gUserProfile.getUser());
                    logger.info(fName+".member="+member.getId()+"|"+member.getUser().getName());
                    JSONObject channels=gUserProfile.jsonObject.getJSONObject(nChannelRestrictions);
                    Iterator<String>keys=channels.keys();
                    while(keys.hasNext()) {
                        try {
                            String key = keys.next();
                            logger.info(fName+".channelID="+key);
                            channelPermissionOverride=new lcChannelPermissionOverride(member,gGuild,key);
                            if(!channels.isNull(key)) {
                                if(!channelPermissionOverride.isChannel()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else
                                if(!channelPermissionOverride.hasPermissionOverride()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else
                                if(!channelPermissionOverride.isPermissionDenied()){
                                    detectedProblem++;gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                }else{
                                    JSONObject permissions=channels.getJSONObject(key);
                                    Iterator<String>permsI=permissions.keys();
                                    while(permsI.hasNext()) {
                                        try {
                                            String name=permsI.next();logger.info(fName + ".name=" + name);
                                            Permission p=lsPermission.lsIsPermission4Name(name);
                                            if(p==null){
                                                logger.warn(fName + ".invalid");
                                            }
                                            if(channelPermissionOverride.clearPermissionOverride(p,"Cleared pet channel permissions by "+gUser.getName())){
                                                gUserProfile.jsonObject.getJSONObject(nChannelRestrictions).put(key,new JSONObject());
                                            }else{
                                                detectedProblem++;
                                                llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Failed to clear "+name+" from channel "+ lsChannelHelper.lsGetChannelMentionById(gGuild,key),llColorRed_Blood);
                                            }
                                        }catch (Exception e){
                                            logger.error(fName + ".exception=" + e);
                                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace())); detectedProblem++;
                                        }
                                    }
                                    //channelPermissionOverride.deletePermissionOverride("Delete pet channel permissions by "+gUser.getName())
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    if(detectedProblem>0){
                        llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Cleared channels restriction with "+detectedProblem+" problems.\nThis can be a result of channels not existing, no overrides logged or no permission to perform overrides." ,llColorRed_Barn);
                    }else{
                        llSendQuickEmbedMessage(gUserProfile.getUser(),sRTitle,"Successfully cleared." ,llColorGreen2);
                    }
                    saveProfile();
                }

            } catch (Exception e) {
                logger.error(fName+".exception="+e);logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

        lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
        lcChannelPermissionOverride channelPermissionOverride;
        private Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            if(gUserProfile!=null&&gUserProfile.getMember()!=null&&gUserProfile.getMember().getIdLong()==member.getIdLong()&&gUserProfile.isProfile()){
                logger.info(fName + ".is already set>skip");
                return true;
            }
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserProfile= iUserInit(gUserProfile, gBDSMCommands.getRestrainsProfile());
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sRTitle,"Failed to write in Db!", llColorRed);}
            return true;
        }

        String gName="rdRestrictions";

        private Boolean saveServerProfile(){
            String fName="[saveServerProfile]";
            logger.info(fName);
            gGlobal.putGuildSettings(gGuild, gBDSMCommands.restrictions.gProfile);
            if(gBDSMCommands.restrictions.gProfile.saveProfile()){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        Boolean vEnabled=false;
        JSONArray vBannedChannels=null;JSONArray vBannedUsers=null;
        private void loadValues(){
            String fName = "[loadValues]";
            logger.info(fName);
            try {
                gBDSMCommands.restrictions.init();
                vEnabled = gBDSMCommands.restrictions.gProfile.getFieldEntryAsBoolean(fieldEnabled2);
                logger.info(fName + ".vEnabled="+vEnabled);
                vBannedChannels= gBDSMCommands.restrictions.gProfile.getFieldEntryAsJSONArray(fieldBannedChannels);
                logger.info(fName + ".vBannedChannels="+vBannedChannels.toString());
                vBannedUsers= gBDSMCommands.restrictions.gProfile.getFieldEntryAsJSONArray(fieldBannedUsers);
                logger.info(fName + ".vBannedUsers="+vBannedUsers.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void menuSetup(){
            String fName="[menuSetup]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="Select one option";
                if(vEnabled){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable";
                }
                desc+="\n:three: Clear channels list";
                desc+="\n:four: Clear banned users list";
                embed.setTitle("Restrictions Setup");embed.setColor(llColorPurple2);
                embed.setDescription(desc);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                if(vEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName + "name=" + name);
                                llMessageDelete(message);
                                if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    logger.warn(fName + "trigger=" + name);
                                    gBDSMCommands.restrictions.gProfile.putFieldEntry(fieldEnabled2, false);
                                    if (saveServerProfile()) {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Updated to disable.", llColorGreen2);
                                    } else {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Failed to update.", llColorRed_Imperial);
                                    }
                                }
                                else if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    logger.warn(fName + "trigger=" + name);
                                    gBDSMCommands.restrictions.gProfile.putFieldEntry(fieldEnabled2, true);
                                    if (saveServerProfile()) {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Updated to enable.", llColorGreen2);
                                    } else {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Failed to update.", llColorRed_Imperial);
                                    }
                                }
                                else if (name.contains(lsUnicodeEmotes.unicodeEmote_Two)) {
                                    logger.warn(fName + "trigger=" + name);

                                }
                                else if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    logger.warn(fName + "trigger=" + name);
                                    gBDSMCommands.restrictions.gProfile.putFieldEntry(fieldBannedChannels, new JSONArray());
                                    if (saveServerProfile()) {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Cleared channels list", llColorGreen2);
                                    } else {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Failed to cleared channels list", llColorRed_Imperial);
                                    }
                                }
                                else if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    logger.warn(fName + "trigger=" + name);
                                    gBDSMCommands.restrictions.gProfile.putFieldEntry(fieldBannedUsers, new JSONArray());
                                    if (saveServerProfile()) {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Cleared forbidden users list", llColorGreen2);
                                    } else {
                                        llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Failed to cleared forbidden members list", llColorRed_Imperial);
                                    }
                                }
                                else if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                    logger.warn(fName + "trigger=" + name);
                                    llSendQuickEmbedMessage(gUser, strRestrictionSetupTitle, "Done", llColorGreen2);
                                }
                                else {
                                    menuSetup();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gUser,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        private void setupAdd2Channel(){
            String fName="[setupAdd2Channel]";
            logger.info(fName);
            try{
                List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
                JSONArray array;String field="";
                field= fieldBannedChannels;
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has( field)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull( field)){
                    gBDSMCommands.restrictions.gProfile.jsonObject.put( field,new JSONArray());
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray( field);
                for(TextChannel channel:channels){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(channel.getId())){
                            has=true;
                        }
                        i++;
                    }
                    if(!has){
                        array.put(channel.getId());
                    }
                }
                gBDSMCommands.restrictions.gProfile.jsonObject.put( field,array);
                gBDSMCommands.restrictions.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Updated channels list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Failed to update channels list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        private void setupRem2Channel(){
            String fName="[setupRem2Channel]";
            logger.info(fName);
            try{
                List<TextChannel>channels=gEvent.getMessage().getMentionedChannels();
                JSONArray array;String field="";
                field= fieldBannedChannels;
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has( field)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull( field)){
                    gBDSMCommands.restrictions.gProfile.jsonObject.put( field,new JSONArray());
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray( field);
                for(TextChannel channel:channels){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(channel.getId())){
                            has=true;
                        }else{
                            i++;
                        }
                    }
                    if(has){
                        array.remove(i);
                    }
                }
                gBDSMCommands.restrictions.gProfile.jsonObject.put( field,array);
                gBDSMCommands.restrictions.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Updated channels list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Failed to update channels list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        private void setupAdd2Member(){
            String fName="[setupAdd2Member]";
            logger.info(fName);
            try{
                List<Member>members=gEvent.getMessage().getMentionedMembers();
                JSONArray array;
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has(fieldBannedUsers)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull(fieldBannedUsers)){
                    gBDSMCommands.restrictions.gProfile.jsonObject.put(fieldBannedUsers,new JSONArray());
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray(fieldBannedUsers);
                for(Member cmember:members){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                            has=true;
                        }
                        i++;
                    }
                    if(!has){
                        array.put(cmember.getId());
                    }
                }
                gBDSMCommands.restrictions.gProfile.jsonObject.put(fieldBannedUsers,array);
                gBDSMCommands.restrictions.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Updated members list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Failed to update members list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        private void setupRem2Member(){
            String fName="[setupRem2Channel]";
            logger.info(fName);
            try{
                List<Member>members=gEvent.getMessage().getMentionedMembers();
                JSONArray array;
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has(fieldBannedUsers)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull(fieldBannedUsers)){
                    gBDSMCommands.restrictions.gProfile.jsonObject.put(fieldBannedUsers,new JSONArray());
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray(fieldBannedUsers);
                for(Member cmember:members){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                            has=true;
                        }else{
                            i++;
                        }
                    }
                    if(has){
                        array.remove(i);
                    }
                }
                gBDSMCommands.restrictions.gProfile.jsonObject.put(fieldBannedUsers,array);
                gBDSMCommands.restrictions.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Updated members list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Failed to update members list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        private void setupViewList(String field){
            String fName="[setupViewList]";
            logger.info(fName);
            try{
                JSONArray array;
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has( field)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull( field)){
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in allowed channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in blocked channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in blocked users list!", llColorRed_Imperial);
                    return;
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray( field);
                if(array.isEmpty()){
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in allowed channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in blocked channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Nothing in blocked users list!", llColorRed_Imperial);
                    return;
                }
                String tmp="";
                for(int i=0;i<array.length();i++){
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        tmp+=" "+lsChannelHelper.lsGetChannelMentionById(gGuild,array.getString(i));
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        tmp+=" "+lsChannelHelper.lsGetChannelMentionById(gGuild,array.getString(i));
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        tmp+=" "+llGetMemberMention(gGuild,array.getString(i));
                }
                if(field.equalsIgnoreCase(fieldAllowedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Allowed channels list: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldBannedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Blocked channels list: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldBannedUsers))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Blocked users list: "+tmp, llColorBlue3);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strRestrictionSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }
        String strRestrictionSetupTitle=sRTitle+" Setup";
        private boolean isUserBlocked(){
            String fName="[isUserBlocked]";
            logger.info(fName);
            try{
                JSONArray array;
                if(gBDSMCommands.restrictions.gProfile.jsonObject ==null){
                    return false;
                }
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has(fieldBannedUsers)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull(fieldBannedUsers)){
                    return false;
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray(fieldBannedUsers);
                if(array.isEmpty()){
                   return false;
                }
                for(int i=0;i<array.length();i++){
                    if(gMember.getId().equalsIgnoreCase(array.getString(i))){
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                 return false;
            }
        }
        private boolean isChannelBlocked(TextChannel textChannel){
            String fName="[isChannelBlocked]";
            logger.info(fName);
            try{
                JSONArray array;
                if(gBDSMCommands.restrictions.gProfile.jsonObject ==null){
                    return false;
                }
                if(!gBDSMCommands.restrictions.gProfile.jsonObject.has(fieldBannedChannels)|| gBDSMCommands.restrictions.gProfile.jsonObject.isNull(fieldBannedChannels)){
                    return false;
                }
                array= gBDSMCommands.restrictions.gProfile.jsonObject.getJSONArray(fieldBannedChannels);
                if(array.isEmpty()){
                    return false;
                }
                for(int i=0;i<array.length();i++){
                    if(textChannel.getId().equalsIgnoreCase(array.getString(i))){
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                 return false;
            }
        }
}}
