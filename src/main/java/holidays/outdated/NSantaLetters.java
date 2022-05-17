package holidays.outdated;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Orange;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NSantaLetters extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass());Logger logger2 = Logger.getLogger("SentSantaLetters");
    lcGlobalHelper gGlobal;
    String gTitle="Dear Naughty Santa ",gCommand="dearnsanta";
    public NSantaLetters(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Confessing to Naughty Santa what an angelic or naughty person you have been this year.";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;this.hidden=true;

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
        CommandEvent gEvent;
        GuildMessageReceivedEvent gGuildMessageReceivedEvent;
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel;Message gMessage;
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
            gMessage=gEvent.getMessage();
        }
        public runLocal(GuildMessageReceivedEvent event) {
            String fName="build";logger.info(".run build");
            gGuildMessageReceivedEvent = event;
            gUser = gGuildMessageReceivedEvent.getAuthor();gMember=gGuildMessageReceivedEvent.getMember();
            gGuild = gGuildMessageReceivedEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gGuildMessageReceivedEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage=event.getMessage();
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            String[] items;
            Boolean isInvalidCommand = true;
            try{
                if(gGuildMessageReceivedEvent!=null){
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw().replaceFirst(llPrefixStr,"");
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    items = args.split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                }else{
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
                        if(items[0].equalsIgnoreCase("help")){
                            if(items.length>=2){
                                help(items[1]);
                            }else{
                                help("main");
                            }
                            isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("display")){
                           display();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("send")){
                            requestLetter();
                           isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("enable")){
                            setEnable("1");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("disable")){
                            setEnable("0");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("setchannel")){
                            setChannel("set");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("clearchannel")){
                            setChannel("clear");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("channel")){
                            if(items.length<2){
                                help("channel");
                            }else{
                                if(items[1].equalsIgnoreCase("set")){ setChannel("set");isInvalidCommand=false;}
                                else if(items[1].equalsIgnoreCase("clear")){ setChannel("clear");isInvalidCommand=false;}
                            }
                        }
                        else if(items[0].equalsIgnoreCase("role")){
                            if(items.length<2){
                                help("roles");
                            }else{
                                if(items[1].equalsIgnoreCase("set")){ setRoles("set");isInvalidCommand=false;}
                                else if(items[1].equalsIgnoreCase("clear")){ setRoles("clear");isInvalidCommand=false;}
                                else if(items[1].equalsIgnoreCase("add")){ setRoles("add");isInvalidCommand=false;}
                                else if(items[1].equalsIgnoreCase("remove")){ setRoles("remove");isInvalidCommand=false;}
                            }
                        }
                    }
                    logger.info(fName+".deleting op message");
                    lsMessageHelper.lsMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }
            catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
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
            String desc="";
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            if(command.equalsIgnoreCase("setup")){
                desc+="\n`"+quickSummonWithSpace+"display` to display stats and configuration";
                desc+="\n`"+quickSummonWithSpace+"enable|disable` to enable or disable it";
                desc+="\n`"+quickSummonWithSpace+"channel set <textchannel mention>` to set to post letters to mentioned text channel.";
                desc+="\n`"+quickSummonWithSpace+"channel clear` to remove so said channel where to send the letters";
                desc+="\n`"+quickSummonWithSpace+"roles set [list of mentioned roles]` to set only members with this roles can submit a letter";
                desc+="\n`"+quickSummonWithSpace+"roles add [list of mentioned roles]` to add to the roles list";
                desc+="\n`"+quickSummonWithSpace+"roles remove [list of mentioned roles]` to remove from roles list";
                desc+="\n`"+quickSummonWithSpace+"roles clear` to clear the roles list, allowing anyone to submit a letter";
            }
            else if(command.equalsIgnoreCase("channel")){
                desc+="\n`"+quickSummonWithSpace+"enable|disable` to enable or disable it";
                desc+="\n`"+quickSummonWithSpace+"channel set <textchannel mention>` to set to post letters to mentioned text channel.";
                desc+="\n`"+quickSummonWithSpace+"channel clear` to remove so said channel where to send the letters";
            }
            else if(command.equalsIgnoreCase("role")||command.equalsIgnoreCase("roles")){
                desc+="\n`"+quickSummonWithSpace+"roles set [list of mentioned roles]` to set only members with this roles can submit a letter";
                desc+="\n`"+quickSummonWithSpace+"roles add [list of mentioned roles]` to add to the roles list";
                desc+="\n`"+quickSummonWithSpace+"roles remove [list of mentioned roles]` to remove from roles list";
                desc+="\n`"+quickSummonWithSpace+"roles clear` to clear the roles list, allowing anyone to submit a letter";
            }
            else {
                desc="Confess what an angelic or naughty person you have been this year." +
                        "\n\u0095 All confessions are anonymous." +
                        "\n\u0095 Only use the bot for it's intended purpose." +
                        "\n\u0095 This is the NSFW version. For the SFW check it under the SFW commands.";
                desc+="\n`"+quickSummonWithSpace+" send` to open DM to post a letter.";
                desc+="\n`"+quickSummonWithSpace+" help setup` to get the lost of commands to set it up.";
            }
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void display() {
            String fName = "[display]";
            logger.info(fName);
            try {
                getValues();
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorBlue1);
                embedBuilder.addField("Enabled", String.valueOf(isenabled),false);
                if(postChannel==null){
                    embedBuilder.addField("Channel","(Not set)",false);
                }else{
                    embedBuilder.addField("Channel",postChannel.getAsMention(),false);
                }
                try {
                    if(whiteRoles.isEmpty()){
                        embedBuilder.addField("Roles","(Not set==everyone)",false);
                    }else{
                        StringBuilder rolesStr= new StringBuilder();
                        for(int i=0;i<whiteRoles.length();i++){
                            long id=whiteRoles.getLong(i);
                            Role role= lsRoleHelper.lsGetRoleByID(gGuild,id);
                            if(!rolesStr.toString().isBlank()){
                                rolesStr.append(", ");
                            }
                            if(role==null){
                                rolesStr.append("<").append(id).append(">");
                            }else{
                                rolesStr.append(role.getAsMention());
                            }
                        }
                        embedBuilder.addField("Roles",rolesStr.toString(),false);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                try {
                    int count=0;
                    Set<String> keySet=senders.keySet();
                    embedBuilder.addField("Senders", String.valueOf(keySet.size()),false);
                   Iterator<String>leyIter= keySet.iterator();
                   while(leyIter.hasNext()){
                       try {
                           count+=senders.getJSONObject(leyIter.next()).getJSONArray(keyMessages).length();
                       }catch (Exception e){
                           logger.error(fName + ".exception=" + e);
                           logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                       }
                   }
                    embedBuilder.addField("Messages", String.valueOf(count),false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void setEnable(String command) {
            String fName = "[setEnable]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.warn(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"You don't have the permission!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                getValues();
                if(command.equalsIgnoreCase("true")||command.equalsIgnoreCase("1"))isenabled=true;
                else if(command.equalsIgnoreCase("false")||command.equalsIgnoreCase("0"))isenabled=false;
                else if(command.equalsIgnoreCase("toggle"))isenabled=!isenabled;
                gProfileGuild.putFieldEntry(keyEnabled,isenabled);
                if(!saveCollar()){
                    logger.error(fName+"failed save");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                if(isenabled){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set enabled=true", llColors.llColorGreen2);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set enabled=false", llColors.llColorPurple1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void setChannel(String command) {
            String fName = "[setChannel]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.warn(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"You don't have the permission!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                getValues();
                if(command.equalsIgnoreCase("set")){
                    List<TextChannel> textChannelList=gMessage.getMentionedChannels();
                    if(textChannelList.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No channel mentioned!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    if(!textChannelList.get(0).isNSFW()){
                        logger.warn(fName+"can't be sfw channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This is the NSFW version. Can't use it in SFW channel!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    postChannel=textChannelList.get(0);
                    gProfileGuild.putFieldEntry(keyChannel,postChannel.getIdLong());
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set targeted channel to: "+postChannel.getAsMention()+".", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("clear")){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Removed targeted channel", llColors.llColorPurple1);
                    gProfileGuild.putFieldEntry(keyChannel,0L);
                }

                if(!saveCollar()){
                    logger.error(fName+"failed save");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void setRoles(String command) {
            String fName = "[setRoles]";
            logger.info(fName);
            try {
                logger.info(fName+"command="+command);
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    logger.warn(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"You don't have the permission!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                getValues();
                if(command.equalsIgnoreCase("set")){
                    List<Role>roles=gMessage.getMentionedRoles();
                    if(roles.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No roles mentioned to set to!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    whiteRoles=new JSONArray();
                    StringBuilder roleStr= new StringBuilder();
                    for(int i=0;i<roles.size();i++){
                        Role role=roles.get(i);
                        whiteRoles.put(role.getIdLong());
                        if (!(roleStr.length()==0)) roleStr.append(", ");
                        roleStr.append(role.getAsMention());
                    }
                    gProfileGuild.putFieldEntry(keyRoles,whiteRoles);
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Roles list set to "+roleStr.toString()+".", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("add")){
                    List<Role>roles=gMessage.getMentionedRoles();
                    if(roles.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No roles mentioned to add!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    boolean updated=false;
                    StringBuilder roleStr= new StringBuilder();
                    for (Role role : roles) {
                        boolean alreadyHas = false;
                        for (int j = 0; j < whiteRoles.length(); j++) {
                            if (whiteRoles.getLong(j) == role.getIdLong()) {
                                alreadyHas = true;
                                break;
                            }
                        }
                        if (!alreadyHas) {
                            logger.info(fName + "this role is new");
                            whiteRoles.put(role.getIdLong());
                            updated = true;
                            if (!(roleStr.length()==0)) roleStr.append(", ");
                            roleStr.append(role.getAsMention());
                        } else {
                            logger.warn(fName + "it's already entered this role");
                        }
                    }
                    if(!updated){
                        logger.warn(fName+"no update");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Nothing updated!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    gProfileGuild.putFieldEntry(keyRoles,whiteRoles);
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Added "+roleStr.toString()+" to the list.", llColors.llColorPurple1);
                }
                else if(command.equalsIgnoreCase("remove")){
                    List<Role>roles=gMessage.getMentionedRoles();
                    if(roles.isEmpty()){
                        logger.warn(fName+"no mentioned");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No roles mentioned to remove!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    boolean updated=false;
                    StringBuilder roleStr= new StringBuilder();
                    for (Role role : roles) {
                        boolean alreadyHas = false;
                        int j = 0;
                        for (j=0;j < whiteRoles.length(); j++) {
                            if (whiteRoles.getLong(j) == role.getIdLong()) {
                                alreadyHas = true;
                                break;
                            }
                        }
                        if (alreadyHas) {
                            logger.info(fName + "this role is present");
                            whiteRoles.remove(j);
                            updated = true;
                            if (!(roleStr.length()==0)) roleStr.append(", ");
                            roleStr.append(role.getAsMention());
                        } else {
                            logger.warn(fName + "this role is not present");
                        }
                    }
                    if(!updated){
                        logger.warn(fName+"no update");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Nothing updated!", llColors_Orange.llColorOrange_InternationalEngineering);
                        return;
                    }
                    gProfileGuild.putFieldEntry(keyRoles,whiteRoles);
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Removed "+roleStr.toString()+" from the list.", llColors.llColorPurple1);

                }
                else if(command.equalsIgnoreCase("clear")){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Removed all roles.", llColors.llColorPurple1);
                    gProfileGuild.putFieldEntry(keyRoles,new JSONArray());
                }
                if(!saveCollar()){
                    logger.error(fName+"failed save");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void requestLetter() {
            String fName = "[requestLetter]";
            logger.info(fName);
            try {
                logger.info(fName+"step 1");
                getValues();
                if(!isenabled){
                    logger.warn(fName+"not enabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not enabled!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                if(postChannel==null){
                    logger.warn(fName+"no post channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Has no post channel set!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                if(!postChannel.isNSFW()){
                    logger.warn(fName+"can't be sfw channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This is the NSFW version. Can't post it to SFW channel!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
                try {
                    if(!whiteRoles.isEmpty()){
                        logger.info(fName+"whiteRoles is not empty");
                        List<Role>roles=new ArrayList<>();
                        for(int i=0;i<whiteRoles.length();i++){
                            long id=whiteRoles.getLong(i);
                            logger.info(fName+"whiteRoles["+i+"]="+id);
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,id);
                            if(role!=null){
                                logger.info(fName+"whiteRoles["+i+"]="+role.getName()+"("+role.getId()+")");
                                roles.add(role);
                            }
                        }
                        boolean found=false;
                        if(!roles.isEmpty()){
                            logger.info(fName+"roles is not empty");
                            List<Role>memberRoles=gMember.getRoles();
                            for(int i=0;i<roles.size();i++){
                                Role role=roles.get(i);
                                logger.info(fName+"roles["+i+"]="+role.getName()+"("+role.getId()+")");
                                if(memberRoles.contains(role)){
                                    logger.info(fName+"found mach");
                                    found=true;break;
                                }
                            }
                        }
                        logger.info(fName+"found="+found);
                        if(!found){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"You don't have the required role to use it!", llColors_Orange.llColorOrange_InternationalEngineering);
                            return;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+"step 2");
                getMemberEntry();
                Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter your message.\nTimeout 15 minutes.\nType `!cancel` to cancel it.",llColorGreen2);
               gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            String content = e.getMessage().getContentDisplay();
                            logger.info(fName+".content="+content);
                            if(content.equalsIgnoreCase("!cancel")){
                                logger.info(fName+".canceled");
                            }else{
                                addNewMessage2MemberEntry(e.getMessageIdLong(),content);
                                postLetter(e.getMessageIdLong(),content);
                            }
                            llMessageDelete(message);
                        },15, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void postLetter(long postId,String content) {
            String fName = "[postLetter]";
            logger.info(fName);
            try {

                if(!postChannel.isNSFW()){
                    logger.warn(fName+"can't be sfw channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"This is the NSFW version. Can't post it to SFW channel!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
               if(!postChannel.canTalk()){
                   logger.warn(fName+"can't use channel");
                   lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Can't use channel! Ensure the bot can send post to it.", llColors_Orange.llColorOrange_InternationalEngineering);
                   lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Can't use channel! Ensure the bot can send post to it.", llColors_Orange.llColorOrange_InternationalEngineering);
                   return;
               }
               String code="Anon-"+lsUsefullFunctions.randomCode(6).toLowerCase();
               logger.info(fName+"code="+code);
               EmbedBuilder embedBuilder=new EmbedBuilder();
               embedBuilder.setAuthor(code,null,"http://www.1happypill.com/wp-content/uploads/2013/12/christmas_letter.png");
               embedBuilder.setDescription(content);
               embedBuilder.setColor(getColor());
               Message message=lsMessageHelper.lsSendMessageResponse(postChannel,embedBuilder);
               if(message!=null){
                   logger.info(fName+"posted successfully");
                   addPost2MemberEntry(message.getIdLong(),postId,code);
                   lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully sent.", llColors.llColorGreen2);
                   logger2.log(CrunchifyLog4jLevel.SENTNSANTALETTER,"Author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gUser.getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), message="+content);
               }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public Color getColor(){
            String fName="[getColor]";
            try{
                List<Color>colors=new ArrayList<>();
                colors.add(llColorOrange);
                colors.add(llColorWhite);
                colors.add(llColorBlue1);
                colors.add(llColorPurple2);
                colors.add(llColorOrange_Atomictangerine);
                colors.add(llColorOrange_Syracuse);
                colors.add(llColorOrange_Tearose);
                colors.add(llColorRed_Carmine);
                colors.add(llColorWhite_Champagne);
                colors.add(llColorRed_FireEngine);
                colors.add(llColorPink2);
                colors.add(llColorGreen1);
                colors.add(llColorGreen2);
                colors.add(llColorYellow_Maximum);
                colors.add(llColorYellow_Unmellow);
                return colors.get(lsUsefullFunctions.getRandom(colors.size()));
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return Color.MAGENTA;
            }
        }
        public JSONObject memberEntry;
        String keyId="",keyPosts="posts",keyMessages="messages",keyText="text",keyMessageId="messageId",keyPostId="postId";
        public void getMemberEntry(){
            String fName="[getMemberEntry]";
            try{
                if(senders.isEmpty()||!senders.has(gMember.getId())||senders.isNull(gMember.getId())){
                    logger.info(fName+".new");
                    memberEntry=new JSONObject();
                    memberEntry.put(keyId,gMember.getIdLong());
                    memberEntry.put(keyMessages,new JSONArray());
                    memberEntry.put(keyPosts,new JSONArray());
                    saveMemberEntry();
                }
                else{
                    logger.info(fName+".load");
                    memberEntry=senders.getJSONObject(gMember.getId());
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void saveMemberEntry(){
            String fName="[saveMemberEntry]";
            try{
                senders.put(gMember.getId(),memberEntry);
                gProfileGuild.jsonObject.getJSONObject(keySenders).put(gMember.getId(),memberEntry);
                if(!saveCollar()){
                    logger.error(fName+"failed save");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Error saving data!", llColors_Orange.llColorOrange_InternationalEngineering);
                    return;
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void addNewMessage2MemberEntry(long id,String content){
            String fName="[addNewMessage2MemberEntry]";
            try{
                logger.info(fName+".id="+id);
                logger.info(fName+".content="+content);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyMessageId,id);
                jsonObject.put(keyText,content);
                memberEntry.getJSONArray(keyMessages).put(jsonObject);
                saveMemberEntry();
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void addPost2MemberEntry(long messageId,long postId,String code){
            String fName="[addNewMessage2MemberEntry]";
            try{
                logger.info(fName+".messageId="+messageId);
                logger.info(fName+".postId="+postId);
                logger.info(fName+".code="+code);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyMessageId,messageId);
                jsonObject.put(keyPostId,postId);
                jsonObject.put("code",code);
                memberEntry.getJSONArray(keyPosts).put(jsonObject);
                saveMemberEntry();
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public lcJSONGuildProfile gProfileGuild;
        String gProfileName ="NSantaLetters";
        String keyEnabled="enabled",keyChannel="channel",keyRoles="roles",keySenders="senders";
        void iSafetyCollar(){
            String fName="iSafetyCreate";
            try {
                String field;
                field= keyEnabled;
                gProfileGuild.safetyPutFieldEntry(field,false);
                field= keyChannel;
                gProfileGuild.safetyPutFieldEntry(field,0L);
                field= keySenders;
                gProfileGuild.safetyPutFieldEntry(field, new JSONObject());
                field= keyRoles;
                gProfileGuild.safetyPutFieldEntry(field, new JSONArray());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public void initCollar(){
            String fName="[initCollar]";
            logger.info(fName+".safety check");
            try{
                gProfileGuild =gGlobal.getGuildSettings(gGuild, gProfileName);
                if(gProfileGuild ==null||!gProfileGuild.isExistent()|| gProfileGuild.jsonObject.isEmpty()){
                    gProfileGuild =new lcJSONGuildProfile(gGlobal, gProfileName,gGuild,llv2_GuildsSettings);
                    gProfileGuild.getProfile(llv2_GuildsSettings);
                    if(!gProfileGuild.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfileGuild);
                    }
                }
                iSafetyCollar();
                if(gProfileGuild.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfileGuild);
                    if(gProfileGuild.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public boolean saveCollar(){
            String fName="[saveCollar]";
            logger.info(fName);
            try{
                logger.info(fName+".profile="+gProfileGuild.jsonObject.toString());
                gGlobal.putGuildSettings(gGuild, gProfileGuild);
                if(gProfileGuild.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean isenabled=false; TextChannel postChannel; JSONArray whiteRoles=new JSONArray();JSONObject senders=new JSONObject();
        public void getValues(){
            String fName="[getValues]";
            logger.info(fName+".safety check");
            try{
                initCollar();
                logger.info(fName+".profile="+gProfileGuild.jsonObject.toString());
                try {
                    if(gProfileGuild.jsonObject.has(keyEnabled)&&!gProfileGuild.jsonObject.isNull(keyEnabled)){
                        isenabled=gProfileGuild.jsonObject.getBoolean(keyEnabled);
                    }
                    if(gProfileGuild.jsonObject.has(keyChannel)&&!gProfileGuild.jsonObject.isNull(keyChannel)){
                        postChannel=lsChannelHelper.lsGetTextChannelById(gGuild,gProfileGuild.jsonObject.getLong(keyChannel));
                    }
                    if(gProfileGuild.jsonObject.has(keySenders)&&!gProfileGuild.jsonObject.isNull(keySenders)){
                        senders=gProfileGuild.jsonObject.getJSONObject(keySenders);
                    }
                    if(gProfileGuild.jsonObject.has(keyRoles)&&!gProfileGuild.jsonObject.isNull(keyRoles)){
                        whiteRoles=gProfileGuild.jsonObject.getJSONArray(keyRoles);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
