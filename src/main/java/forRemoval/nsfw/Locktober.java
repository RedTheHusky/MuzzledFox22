package forRemoval.nsfw;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsCustomGuilds;
import models.ls.lsGuildHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Locktober extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper {
        Logger logger = Logger.getLogger(getClass());

        lcGlobalHelper gGlobal;
        String gTitle="Locktober",gCommand="locktober";
    public Locktober(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Locktober";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;this.userPermissions=new Permission[]{Permission.MESSAGE_MANAGE};
    }
    public Locktober(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(fName);gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public Locktober(lcGlobalHelper global, GuildMessageReactionRemoveEvent event){
        String fName="[constructor]";
        logger.info(fName);gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
        @Override
        protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }

protected class runLocal implements Runnable {
    CommandEvent gEvent;GuildMessageReactionAddEvent gMessageReactionAddEvent;GuildMessageReactionRemoveEvent gMessageReactionRemoveEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
    lcJSONUserProfile gUserProfile;
    Member gTarget;
    boolean gIsOverride=false;

    public runLocal(CommandEvent ev) {
        String fName="build";logger.info(".run build");
        gEvent = ev;
        gUser = gEvent.getAuthor();gMember=gEvent.getMember();
        gGuild = gEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gEvent.getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
    }
    public runLocal(GuildMessageReactionAddEvent ev) {
        String fName="build";logger.info(".run GuildMessageReactionAddEvent");
        gMessageReactionAddEvent = ev;
        gUser = gMessageReactionAddEvent.getUser();gMember=gMessageReactionAddEvent.getMember();
        gGuild = gMessageReactionAddEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gMessageReactionAddEvent.getReaction().getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
    }
    public runLocal(GuildMessageReactionRemoveEvent ev) {
        String fName="build";logger.info(".run GuildMessageReactionRemoveEvent");
        gMessageReactionRemoveEvent = ev;
        gUser = gMessageReactionRemoveEvent.getUser();gMember=gMessageReactionRemoveEvent.getMember();
        gGuild = gMessageReactionRemoveEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gMessageReactionRemoveEvent.getReaction().getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
    }
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        String[] items;
        Boolean isInvalidCommand = true;
        try{
            if(gMessageReactionAddEvent!=null){
                init();
                MessageReaction messageReaction=gMessageReactionAddEvent.getReaction();
                MessageReaction.ReactionEmote reactionEmote=messageReaction.getReactionEmote();
                String name="",id="",codepoints="",reactioncode="",emoji="";
                try {
                    name=reactionEmote.getName();
                    logger.info(fName + ".emoteName=" + name);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    id=reactionEmote.getId();
                    logger.info(fName + ".emoteID=" + id);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    codepoints=reactionEmote.getAsCodepoints();
                    logger.info(fName + ".emoteAsCodepoints="+codepoints);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    reactioncode=reactionEmote.getAsReactionCode();
                    logger.info(fName + ".emoteAsReactionCode="+reactioncode);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    emoji=reactionEmote.getEmoji();
                    logger.info(fName + ".emoteEmoji="+emoji);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(!gGuild.getId().equals(lsCustomGuilds.lsGuildKeyChastity)){
                    logger.warn(fName + ".invalid guild");return;
                }
                if(!gMessageReactionAddEvent.getMessageId().equals(gLocktoberMessageID)){
                    logger.warn(fName + ".invalid message id");return;
                }
                if(gLocktoberReactions==null){
                    logger.warn(fName + ".its null");return;
                }
                if(name.equalsIgnoreCase("lock_green")||name.equalsIgnoreCase("lock_blue")||name.equalsIgnoreCase("lock_yellow")||name.equalsIgnoreCase("lock_red")||name.equalsIgnoreCase("infinity")){
                    logger.info(fName + ".add role");
                    gGuild.addRoleToMember(gMember,gLocktoberRole).complete();
                }
            }else
            if(gMessageReactionRemoveEvent!=null){

            }else{
                try {
                    if(!isNSFW()){
                        blocked();return;
                    }
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");
                        gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=true;
                        isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);

                        if(items.length>=3&&isTargeted(items[1])){

                        }else{
                            if(items[0].equalsIgnoreCase("help")){
                                help("main");isInvalidCommand=false;
                            }else
                            if(items[0].equalsIgnoreCase("log")){
                                init();
                                print2LogReactions();isInvalidCommand=false;
                            }else
                            if(items[0].equalsIgnoreCase("assign")){
                                init();
                                assignRole();isInvalidCommand=false;
                            }
                        }

                        //isInvalidCommand=false;
                    }
                /*logger.info(fName+".deleting op message");
                llQuckCommandMessageDelete(gEvent);*/
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e, llColorRed);
                }

            }
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
        }
        logger.info(".run ended");
    }
    private boolean isNSFW(){
        String fName = "[isNSFW]";
        if(gTextChannel.isNSFW()){
            return true;
        }
        if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
            return true;
        }
        return false;
    }
    private void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel or server.",llColorRed);
        logger.info(fName);
    }
    private Boolean isTargeted(String command){
        String fName = "[isTargeted]";
        logger.info(fName);
        try{
            logger.info(fName + ".command=" + command);
            if((command.contains("<@")&&command.contains(">"))||(command.contains("<@!")&&command.contains(">"))){
                String tmp=command.replace("<@!","").replace("<@","").replace(">","");
                Member m=gGuild.getMemberById(tmp);
                if(m!=null){
                    if(m.getId().equals(gUser.getId())){
                        logger.info(fName + ".target same");
                        return false;
                    }
                    logger.info(fName + ".target ok");
                    gTarget=m;
                    return true;
                }
            }
            logger.info(fName + ".target none");
            return false;
        }
        catch(Exception ex){
            logger.error(fName + ".exception: "+ex);
            return false;
        }
    }


    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+gCommand+" ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(gTitle);
        embed.addField("Commands","`"+quickSummonWithSpace+"assign` Assign locktober role based on the reaction",false);
        llSendMessage(gUser,embed);
    }
    String gLocktoberChannelID="760376703217565739",gLocktoberMessageID="760379836409315349",gLocktoberRoleId="760377461103132703";
    TextChannel gLocktoberTextChannel=null;Message gLocktoberMessage=null; List<MessageReaction>gLocktoberReactions=null;Role gLocktoberRole=null;
    private boolean init(){
        String fName = "[getMessage]";
        try {
            if(!gGuild.getId().equals(lsCustomGuilds.lsGuildKeyChastity)){
                logger.warn(fName + ".invalid guild");return false;
            }
            try {
                gLocktoberTextChannel=gGuild.getTextChannelById(gLocktoberChannelID);
                if(gLocktoberTextChannel!=null){
                    gLocktoberMessage=gLocktoberTextChannel.retrieveMessageById(gLocktoberMessageID).complete();
                    if(gLocktoberMessage!=null){
                        gLocktoberReactions= gLocktoberMessage.getReactions();
                        if(gLocktoberReactions==null){
                            logger.warn(fName + ".no such reactions");
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                gLocktoberRole=gGuild.getRoleById(gLocktoberRoleId);
                if(gLocktoberRole==null){
                    logger.warn(fName + ".no such role");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private void print2LogReactions(){
        String fName = "[print2LogReactions]";
        try {
            if(gLocktoberReactions==null){
                logger.warn(fName + ".its null");return;
            }
            for(int i=0;i<gLocktoberReactions.size();i++){
                try {
                    MessageReaction messageReaction=gLocktoberReactions.get(i);
                    MessageReaction.ReactionEmote emote=messageReaction.getReactionEmote();
                    String name="",id="",codepoints="",reactioncode="",emoji="";
                    try {
                        name=emote.getName();
                        logger.info(fName + ".[" + i + "]emoteName=" + name);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        id=emote.getId();
                        logger.info(fName + ".[" + i + "]emoteID=" + id);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        codepoints=emote.getAsCodepoints();
                        logger.info(fName + ".["+i+"]emoteAsCodepoints="+codepoints);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        reactioncode=emote.getAsReactionCode();
                        logger.info(fName + ".["+i+"]emoteAsReactionCode="+reactioncode);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        emoji=emote.getEmoji();
                        logger.info(fName + ".["+i+"]emoteEmoji="+emoji);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    List<User>users=messageReaction.retrieveUsers().complete();
                    logger.info(fName + ".["+i+"]users.size="+users.size());
                    for(int j=0;j<users.size();j++){
                        try {
                            User user=users.get(j);
                            logger.info(fName + ".["+i+"]users["+j+"]="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Done", llColors.llColorBlue1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void assignRole(){
        String fName = "[assignRole]";
        try {
            if(gLocktoberReactions==null){
                logger.warn(fName + ".its null");return;
            }
            for(int i=0;i<gLocktoberReactions.size();i++){
                try {
                    MessageReaction messageReaction=gLocktoberReactions.get(i);
                    MessageReaction.ReactionEmote emote=messageReaction.getReactionEmote();
                    String name="",id="",codepoints="",reactioncode="",emoji="";
                    try {
                        name=emote.getName();
                        logger.info(fName + ".[" + i + "]emoteName=" + name);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        id=emote.getId();
                        logger.info(fName + ".[" + i + "]emoteID=" + id);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        codepoints=emote.getAsCodepoints();
                        logger.info(fName + ".["+i+"]emoteAsCodepoints="+codepoints);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        reactioncode=emote.getAsReactionCode();
                        logger.info(fName + ".["+i+"]emoteAsReactionCode="+reactioncode);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        emoji=emote.getEmoji();
                        logger.info(fName + ".["+i+"]emoteEmoji="+emoji);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    List<User>users=messageReaction.retrieveUsers().complete();
                    logger.info(fName + ".["+i+"]users.size="+users.size());
                    for(int j=0;j<users.size();j++){
                        try {
                            User user=users.get(j);
                            logger.info(fName + ".["+i+"]users["+j+"]="+user.getName()+"#"+user.getDiscriminator()+"("+user.getId()+")");
                            if(name.equalsIgnoreCase("lock_green")||name.equalsIgnoreCase("lock_blue")||name.equalsIgnoreCase("lock_yellow")||name.equalsIgnoreCase("lock_red")||name.equalsIgnoreCase("infinity")){
                                gGuild.addRoleToMember(user.getId(),gLocktoberRole).complete();
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Done", llColors.llColorBlue1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

}
}
