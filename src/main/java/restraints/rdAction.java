package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.emotes.lcEmote;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;
import restraints.in.iPishock;
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdAction extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); String gCommand="rdaction";
	String sRTitle="Action";
    public rdAction(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Action-DiscordRestraints";
        this.help = "action";
        this.aliases = new String[]{gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public rdAction(lcGlobalHelper global, GuildMessageReceivedEvent event,boolean isForward,String forward,Member author){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event,isForward,forward,author);
        new Thread(r).start();
    }
    public rdAction(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public rdAction(lcGlobalHelper global, GuildMessageReceivedEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
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
    protected class runLocal implements Runnable {
        String cName="[runLocal]";
        CommandEvent gEvent;GuildMessageReceivedEvent gGuildMessageReceivedEvent;GuildMessageReactionAddEvent gGuildMessageReactionAddEvent;
        String gRawForward="";boolean gIsForward=false, gIsOverride =false;
        Guild gGuild; User gUser;Member gMember; TextChannel gTextChannel;Member gTarget;
        String gArgs="";
        public runLocal(CommandEvent ev){
            logger.info(".run build");String fName="runLocal";
            gEvent=ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBBDSMCommands=new lcBDSMGuildProfiles(gGlobal,gGuild);
            gArgs=gEvent.getArgs();
            updateIsAdult();
        }
        public runLocal(GuildMessageReceivedEvent event,boolean isForward,String forward,Member author){
            logger.info(".run build");String fName="runLocal";
            gGuildMessageReceivedEvent=event;gIsForward=isForward;gRawForward=forward;
            gUser = author.getUser();gMember=author;
            gTarget=event.getMember();
            gGuild = event.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = event.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gArgs=lsUsefullFunctions.removePrefix(gGuildMessageReceivedEvent.getMessage().getContentRaw(),gGuild.getSelfMember());
            gBBDSMCommands=new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
        }
        public runLocal(GuildMessageReceivedEvent event){
            logger.info(".run build");String fName="runLocal";
            gGuildMessageReceivedEvent=event;
            gUser = event.getAuthor();gMember=event.getMember();
            gGuild = event.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = event.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBBDSMCommands=new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
        }
        public runLocal(GuildMessageReactionAddEvent ev){
            logger.info(".run build");String fName="[runLocal]";
            gGuildMessageReactionAddEvent=ev;
            gUser = ev.getUser();gMember=ev.getMember();
            gGuild = ev.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = ev.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gBBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            updateIsAdult();
        }
        lcBDSMGuildProfiles gBBDSMCommands;
        lcEmote emojiXPunish= new lcEmote(),emojiWand= new lcEmote();
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                lsUsefullFunctions.setThreadName4Display("rdAction");
                gBBDSMCommands.restraints.init();
                if(gGuildMessageReactionAddEvent!=null){
                    try {
                        if(!isAdult){logger.info(cName + fName + "resturn as not allowed do to not nsfw channel or restricted");return;}
                        logger.info(cName + fName + "reaction received@");
                        emojiWand.setGuild(gGlobal.getGuild(lsCustomGuilds.lsGuildKeyAdministration));
                        emojiWand.getEmoteByName("wand");

                        Message message=gGuildMessageReactionAddEvent.retrieveMessage().complete();
                        logger.info(fName + ".message.id=" +message.getId());
                        if(message.getAuthor().isBot()){
                            logger.info(fName + ".its from bot>ignore");
                            return;
                        }
                        User messageUser=message.getAuthor();logger.info(fName + "message.user=" + messageUser);
                        Member messageMember=message.getMember();logger.info(fName + "messageMember:" + messageMember);
                        MessageReaction.ReactionEmote reactionEmote=gGuildMessageReactionAddEvent.getReactionEmote();
                        OffsetDateTime timeCreated=message.getTimeCreated();
                        long timeCreatedEpoch=timeCreated.toEpochSecond()*1000;
                        logger.info(fName + ".timeCreatedEpoch="+timeCreatedEpoch);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
                        logger.info(fName+".timestamp="+timestamp.getTime());
                        long diff=timestamp.getTime()-timeCreatedEpoch;
                        logger.info(fName+".diff="+diff);
                        logger.info(fName+".15 minutes="+milliseconds_minute*15);
                        if(milliseconds_minute*15<diff){
                            logger.info(fName + ".post too old");
                            return;
                        }
                        if(reactionEmote==null){
                            logger.info(fName + ".reactionEmote is null");
                            return;
                        }
                        String name=reactionEmote.getName();
                        logger.info(fName + ".name="+name);
                        if(reactionEmote.isEmote()){
                            logger.info(fName + ".its emote>custom");
                            Emote emote=reactionEmote.getEmote();
                            if(name.equalsIgnoreCase("wand")||(emojiWand.getEmote()!=null&&emote.getIdLong()==emojiWand.getIdLong())){
                                logger.info(fName + ".do action reward");
                                reward(messageMember);
                            }
                        }else{
                            String emoji=reactionEmote.getEmoji();
                            logger.info(fName + ".emoji=" +emoji);
                            if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                punish(messageMember);
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGiftHeart))){
                                logger.info(fName + ".do action reward");
                                reward(messageMember);
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                else if(gGuildMessageReceivedEvent!=null) {
                    logger.info(cName + fName + "message received@");
                    if(!gBBDSMCommands.restraints.getEnable()) {
                        logger.info(fName + "its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    else if(!gBBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    else if(!gBBDSMCommands.restraints.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    if(!isAdult){blocked();return;}
                    String[] items;
                    logger.info(cName + fName + "gIsForward="+gIsForward);
                    if(gIsForward){
                       items = gRawForward.split("\\s+");
                        if(gTarget!=null){
                            if(items[0].equalsIgnoreCase("punish")){ punish(gTarget);}
                            else if(items[0].equalsIgnoreCase("reward")){ reward(gTarget);}
                        }else{
                            if(items[0].equalsIgnoreCase("punish")){ punish(gMember);}
                            else if(items[0].equalsIgnoreCase("reward")){ reward(gMember);}
                        }
                    }else{
                        boolean isInvalidCommand=true;
                        items = gArgs.split("\\s+");
                        if(items.length>=2){
                            if(items[1].contains("<!@")||items[1].contains("<@")&&items[1].contains(">")){
                                logger.info(fName + ".detect mention characters");
                                Member target;
                                List<Member> mentions = gGuildMessageReceivedEvent.getMessage().getMentionedMembers();
                                if (mentions.isEmpty()) {
                                    logger.warn(fName + ".zero member mentions in message>check itemns[0]");
                                    target = llGetMember(gGuild, items[1]);
                                } else {
                                    logger.info(fName + ".member mentions in message");
                                    target = mentions.get(0);
                                }
                                if(target!=null&&target.getIdLong()!=gMember.getIdLong()){
                                    gTarget=target;
                                }
                            }
                        }
                        if(gTarget!=null){
                            if(items[0].equalsIgnoreCase("punish")){
                                punish(gTarget);isInvalidCommand=false;
                            }
                            if(items[0].equalsIgnoreCase("reward")){
                                reward(gTarget);isInvalidCommand=false;
                            }
                        }else{
                            if(items[0].equalsIgnoreCase("punish")){
                                punish(gMember);isInvalidCommand=false;
                            }
                            if(items[0].equalsIgnoreCase("reward")){
                                reward(gMember);isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                        }
                    }
                }
                else{
                    try {
                        logger.info(cName + fName + "basic@");
                        if(!gBBDSMCommands.restraints.getEnable()) {
                            logger.info(fName + "its disabled");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                            return;
                        }
                        else if(!gBBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                            logger.info(fName+"its not allowed by channel");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                            return;
                        }
                        else if(!gBBDSMCommands.restraints.isRoleAllowed(gMember)){
                            logger.info(fName+"its not allowed by roles");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                            return;
                        }
                        if(!isAdult&&bdsmRestriction==1){blocked();return;}
                        else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                        String[] items;
                        boolean isInvalidCommand=true;
                        logger.info(fName+".gIsForward="+gIsForward);
                        if(gArgs!=null&&gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        if(gArgs==null||gArgs.isEmpty()){
                            logger.info(fName+".Args=0");
                            rHelp("main");isInvalidCommand=false;
                        }else {
                            logger.info(fName + ".Args");
                            items = gArgs.split("\\s+");
                            logger.info(fName + ".gRawForward="+gRawForward);
                            logger.info(fName + ".gRawForward:"+gRawForward.isEmpty()+"|"+gRawForward.isBlank());
                            logger.info(fName + ".items.size=" + items.length);
                            logger.info(fName + ".items[0]=" + items[0]);
                            if(items[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                            ///TARGETED
                            if(isInvalidCommand&&(items[0].contains("<!@")||items[0].contains("<@"))&&items[0].contains(">")){
                                logger.info(fName+".detect mention characters");
                                Member target;
                                List<Member> mentions=gEvent.getMessage().getMentionedMembers();
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
                                    //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                                }
                                else if(items.length<2){
                                    logger.warn(fName+".invalid args length");

                                }else{
                                    if(items[1].equalsIgnoreCase("punish")){
                                        punish(target);isInvalidCommand=false;
                                    }
                                    if(items[1].equalsIgnoreCase("reward")){
                                        reward(target);isInvalidCommand=false;
                                    }
                                }
                            }
                            if(isInvalidCommand){
                                if(items==null||items.length==0){
                                    logger.warn(fName+".blank command");
                                }
                                else if(items[0].equalsIgnoreCase("punish")){
                                    punish(gMember);isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("reward")){
                                    reward(gMember);isInvalidCommand=false;
                                }
                            }
                        }

                        if(isInvalidCommand){
                            llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Exception:"+e.toString(),llColors.llColorRed);
                    }
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
            llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,sRTitle,"Require NSFW channel or server.",llColorRed);
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
            bdsmRestriction=gBBDSMCommands.getBDSMRestriction();
            logger.info(fName+"is safe");
        }
        private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace2=llPrefixStr+"rdaction <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","just silly texts.",false);
        desc="**List of commands:**";
        desc+=newLine+quickSummonWithSpace2+"punish"+endLine;
        desc+=newLine+quickSummonWithSpace2+"reward"+endLine;
        embed.addField("Commands",desc,false);

        if(llSendMessageStatus(gUser,embed)){
            llSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            llSendMessageStatus(gTextChannel,embed);
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void punish(Member target){
            String fName = "[punish]";
            logger.info(fName);
            try {
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                if(!isFreeHanded()){
                    logger.info(fName+"no free hands>return");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrainedCantZap),llColors.llColorRed_Barn);
                    return;
                }
                boolean isCollar=false,isChastity=false,isChastityShockboc=false,isSuit=false;
                try {
                    isSuit=gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn);
                    isCollar=gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nOn);
                    isChastity=gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nOn);
                    isChastityShockboc=gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nShock);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(isSuit){
                    if(isCollar&&isChastity&&isChastityShockboc){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit collar and chastity zaps you as punishment. After that the hood displays a warning message : \n\"Toy was a bad toy. Toy is required to say sorry!\"", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard as the collar around Toy "+gUserProfile.getUser().getAsMention()+" neck lights up a bit and between their legs as they get punished."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal, iPishock.strRdAction0,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else
                    if(isCollar){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit collar zaps you as punishment. After that the hood displays a warning message : \n\"Toy was a bad toy. Toy is required to say sorry!\"", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard as the collar around Toy "+gUserProfile.getUser().getAsMention()+" neck lights up a bit as they get punished."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else
                    if(isChastity&&isChastityShockboc){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit chastity zaps you as punishment. After that the hood displays a warning message : \n\"Toy was a bad toy. Toy is required to say sorry!\"", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard between the Toy "+gUserProfile.getUser().getAsMention()+" legs as they get zapped for punishment."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,null,gUserProfile.getUser().getAsMention()+" cant be punished!",llColorRed_Cinnabar);
                    }
                }else{
                    if(isCollar&&isChastity&&isChastityShockboc){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The collar and chastity zaps you as punishment.", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard as the collar around "+gUserProfile.getUser().getAsMention()+" neck lights up a bit and between their legs as they get punished."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal,iPishock.strRdAction0,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else
                    if(isCollar){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The collar zaps you.", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard as the collar around "+gUserProfile.getUser().getAsMention()+" neck lights up a bit as they get punished."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal,iPishock.strRdAction1,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else
                    if(isChastity&&isChastityShockboc){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The chastity zaps you", llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A zapping noise can be heard between the "+gUserProfile.getUser().getAsMention()+" legs as they get zapped for punishment."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap),llColors.llColorRed_Cinnabar);
                        new rdPishock(gGlobal,iPishock.strRdAction2,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                    }else{
                        logger.info(gUserProfile.getUser().getId()+" cant be punished");
                        //lsMessageHelper.lsSendQuickEmbedMessage(gUser,null,gUserProfile.getUser().getAsMention()+" cant be punished!",llColorRed_Cinnabar);
                    }
                }
                if(isChastity){
                    logger.info(gUserProfile.getUser().getId()+" do emlalock punish");
                    new ChastityEmlalock(gGlobal, iChastityEmlalock.commandPunish,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                }

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, ex.toString());
            }
        }
        private void reward(Member target){
            String fName = "[reward]";
            logger.info(fName);
            try {
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                if(!isFreeHanded()){
                    logger.info(fName+"no free hands>return");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrainedCantZap),llColors.llColorRed_Barn);
                    return;
                }
                boolean isCollar=false,isChastity=false,isChastityShockboc=false,isSuit=false;int gender=0;
                try {
                    isSuit=gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn);
                    isCollar=gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nOn);
                    isChastity=gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nOn);
                    isChastityShockboc=gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nShock);
                    if(gUserProfile.jsonObject.has(nGender)&&!gUserProfile.jsonObject.isNull(nGender)){
                        int i=gUserProfile.jsonObject.getInt(nGender);
                        if(0<=i&&i<=2){
                            gender=i;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(isSuit){
                    switch (gender){
                        case vGenderMale:
                            lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit bulge vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"",llColors.llColorBlue1);
                            lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gUserProfile.getUser().getAsMention()+"'s bulge.",llColors.llColorRed_Cinnabar);
                            break;
                        case vGenderFemale:
                            lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit sex vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"",llColors.llColorBlue1);
                            lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gUserProfile.getUser().getAsMention()+"'s sex.",llColors.llColorRed_Cinnabar);
                            break;
                        default:
                            lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The suit bulge/sex vibrates, sending sweet pleasure for you, but only for a short time. After that the hood displays a message : \n\"Toy was a good toy. Toy is rewarded\"",llColors.llColorBlue1);
                            lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from Toy "+gUserProfile.getUser().getAsMention()+"'s bulge/sex.",llColors.llColorRed_Cinnabar);
                            break;
                    }
                }else{
                    if(isChastity&&isChastityShockboc){
                        switch (gender){
                            case vGenderMale:
                                lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The chastity gently shocks your bulge, sending sweet pleasure for you, but only for a short time.",llColors.llColorBlue1);
                                lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from "+gUserProfile.getUser().getAsMention()+"'s locked bulge.",llColors.llColorRed_Cinnabar);
                                break;
                            case vGenderFemale:
                                lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The chastity gently shocks your sex, sending sweet pleasure for you, but only for a short time.",llColors.llColorBlue1);
                                lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from "+gUserProfile.getUser().getAsMention()+"'s locked sex.",llColors.llColorRed_Cinnabar);
                                break;
                            default:
                                lsMessageHelper.lsSendQuickEmbedMessage(gUserProfile.getUser(),null,"The chastity gently shocks your bulge/sex, sending sweet pleasure for you, but only for a short time.",llColors.llColorBlue1);
                                lsMessageHelper.lsSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,null,"A bzzz noise can be heard from "+gUserProfile.getUser().getAsMention()+"'s locked bulge/sex.",llColors.llColorRed_Cinnabar);
                                break;
                        }
                    }else{
                        logger.info(gUserProfile.getUser().getId()+" cant be rewarded");
                        //lsMessageHelper.lsSendQuickEmbedMessage(gUser,null,gUserProfile.getUser().getAsMention()+" cant be rewarded!",llColorRed_Cinnabar);
                    }
                }
                if(isCollar&&isChastity&&isChastityShockboc){
                    new rdPishock(gGlobal,iPishock.strRdAction4,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                }
                else if(isCollar){
                    new rdPishock(gGlobal,iPishock.strRdAction5,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                }
                else if(isChastity&&isChastityShockboc){
                    new rdPishock(gGlobal,iPishock.strRdAction6,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                }
                if(isChastity){
                    logger.info(gUserProfile.getUser().getId()+" do emlalock reward");
                    new ChastityEmlalock(gGlobal, iChastityEmlalock.commandReward,gGuild,gTextChannel,gMember,gUserProfile.getMember());
                }

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, ex.toString());
            }
        }
        lcJSONUserProfile gUserProfile4Options;
        private boolean isFreeHanded(){
            String fName = "[isFreeHanded]";
            logger.info(fName);
            try {
                logger.info(fName+"member="+gUser.getName()+"("+gUser.getId()+")");
                gUserProfile4Options=gGlobal.getUserProfile(profileName,gMember,gGuild);
                if(gUserProfile4Options!=null&&gUserProfile4Options.isProfile()){
                    logger.info(fName + ".is locally cached");
                }else{
                    logger.info(fName + ".need to get or create");
                    gUserProfile4Options=new lcJSONUserProfile(gGlobal,gMember,gGuild);
                    if(gUserProfile4Options.getProfile(table)){
                        logger.info(fName + ".has sql entry");
                    }
                }
                if(!gUserProfile4Options.isProfile()&&gUserProfile4Options.jsonObject.isEmpty()){
                    logger.info(fName+"not a profile");
                    return true;
                }
                logger.info(fName+"json="+gUserProfile4Options.jsonObject.toString());
                boolean areAllowed2Wand=true;
                if (gUserProfile4Options.jsonObject.getBoolean(nLocked)) {
                    areAllowed2Wand = false;
                } else if (gUserProfile4Options.jsonObject.getJSONObject(nArmsCuffs).getBoolean(nOn)) {
                    areAllowed2Wand = false;
                } else if (gUserProfile4Options.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)) {
                    areAllowed2Wand = false;
                } else if (gUserProfile4Options.jsonObject.getJSONObject(nSuit).getBoolean(nOn)) {
                    areAllowed2Wand = false;
                } else if (gUserProfile4Options.jsonObject.getJSONObject(nMitts).getBoolean(nOn)) {
                    areAllowed2Wand = false;
                }
                logger.info(fName+"areAllowed2Wand="+areAllowed2Wand);
                return  areAllowed2Wand;

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                return true;
            }
        }

        lcJSONUserProfile gUserProfile;
         private Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
             if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.getMember().getIdLong()==member.getIdLong()){
                 logger.info(fName + ".already present>skip");
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
            gUserProfile= iUserInit(gUserProfile,gBBDSMCommands.getRestrainsProfile());
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");
            }
            getMainJsons();return true;
        }
        JSONObject JSON;
        private void getMainJsons(){
            String fName="[getMainJsons]";
            logger.info(fName);
            try {
                JSON =gUserProfile.jsonObject;
                logger.info(fName+"json="+ JSON.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }


}}
