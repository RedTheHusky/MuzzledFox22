package util;
//implemented Runnable
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.emotes.lcEmote;
import models.lc.lcTempZipFile;
import models.lc.webhook.lcWebHookBuild;
import models.lc.UnicodeEmojis;
import models.lcGlobalHelper;
import models.ll.*;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class utilityEmote extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llCommonKeys {
    String cName="[utilityEmote]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    public utilityEmote(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "Emoji-Utility";
        this.help = "Utility commands for custom emojis.";
        this.aliases = new String[]{"emote","emoji",};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;private Message gMessage;
        String gTitle="Emote Utility";
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
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
            String fName="[run]";
            logger.info(fName);
            boolean isInvalidCommand = true;
            try {
                prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    String []items = gCommandEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")){
                        help( "main");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("print")){
                        printAll();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("info")){
                        info();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("setname")&&emoteSelected(gMessage,items)){
                        setName(items);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("setrole")&&emoteSelected(gMessage,items)){
                        setRole();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("allsetrole")){
                        setRoleForAll();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("createemote")||items[0].equalsIgnoreCase("addemote")||items[0].equalsIgnoreCase("add")){
                        create();isInvalidCommand=false;
                    }else
                    if((items[0].equalsIgnoreCase("deleteemote")||items[0].equalsIgnoreCase("rememote")||items[0].equalsIgnoreCase("remove"))&&emoteSelected(gMessage,items)){
                        delete();isInvalidCommand=false;
                    }else
                    if(items.length>=2&&items[0].equalsIgnoreCase("jsonemote")&&items[1].equalsIgnoreCase("multiple")){
                        sendEmoteLogZipFile(gGuild);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("jsonemote")){
                        sendEmoteJsonFile(gGuild);isInvalidCommand=false;
                    }else
                    if(items.length>=2&&items[0].equalsIgnoreCase("alldownload")&&(items[1].equalsIgnoreCase("id")||items[1].equalsIgnoreCase("name"))){
                        downloadAll(items[1]);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("alldownload")){
                        downloadAll("bot");isInvalidCommand=false;
                    }else
                    if(items.length>2&&items[0].equalsIgnoreCase("addreaction")){
                        if(items.length>3&&!items[1].contains("<")&&!items[2].contains("<")){
                            addReaction(items[1],items[2]);isInvalidCommand=false;
                        }else
                        if(!items[1].contains("<")){
                            addReaction(gTextChannel.getId(),items[1]);isInvalidCommand=false;
                        }
                    }else
                    if(items.length==3&&items[0].equalsIgnoreCase("getreactions")){
                        getReactions(items[1],items[2]);isInvalidCommand=false;
                    }else
                    if(items.length==2&&items[0].equalsIgnoreCase("getreactions")){
                        getReactions(gTextChannel.getId(),items[1]);isInvalidCommand=false;
                    }else
                    if(items.length==3&&items[0].equalsIgnoreCase("getemotes")){
                        getEmotes(items[1],items[2]);isInvalidCommand=false;
                    }else
                    if(items.length==2&&items[0].equalsIgnoreCase("getemotes")){
                        getEmotes(gTextChannel.getId(),items[1]);isInvalidCommand=false;
                    }else
                    if(items.length==2&&items[0].equalsIgnoreCase("img")){
                        if(emoteSelected2(gMessage,items)){
                            getImg();isInvalidCommand=false;
                        }else{
                            getImg(items[1]);isInvalidCommand=false;
                        }
                    }else
                    if(items.length==2&&items[0].equalsIgnoreCase("img40")){
                        if(emoteSelected2(gMessage,items)){
                            getImg40();isInvalidCommand=false;
                        }else{
                            getImg40(items[1]);isInvalidCommand=false;
                        }
                    }else
                    if(items[0].equalsIgnoreCase("listanimated")||items[0].equalsIgnoreCase("gif")){
                        listAnimated();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("liststatic")){
                        listStatic();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("listall")){
                        listAll();isInvalidCommand=false;
                    }else
                    if(gCommandEvent.getMessage().getEmotes().size()>0){
                        getEmotes(gTextChannel.getId(), gCommandEvent.getMessage().getId());isInvalidCommand=false;
                    }else
                     if(items[0].toLowerCase().contains(":")){
                        post(items[0]);isInvalidCommand=false;
                    }else
                    if(items.length>=2&&items[0].equalsIgnoreCase("test1")){
                        printEmoji(items[1]);isInvalidCommand=false;
                    }
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String prefix2="";
    private void help(String command){
        String fName="help";
        logger.info(fName + ".command:"+command);
        String quickSummonWithSpace=prefix2+"emote ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle); embed.setColor(llColorBlue1);
        embed.addField("Post","`"+quickSummonWithSpace+"[name/id]` allows posting that custom emote of server, especially if trying to use gif emojis and not nitro",false);
        embed.addField("Img","`"+quickSummonWithSpace+" img [name/id/emote]` get image of emote",false);
        embed.addField("Listing&Use","`"+quickSummonWithSpace+"gif`, `"+quickSummonWithSpace+"listanimated` listing all gif emojis, allowing you to use them even if not nitro"+"\n`"+quickSummonWithSpace+"liststatic/listall`"
                +"\nUse the :track_previous: :arrow_backward: :arrow_forward: :track_next: to move between emotes." +
                "\nUse  :heavy_check_mark: to keep this emote or it will get auto deleted after timeout." +
                "\nUse :x: to cancel",false);
        embed.addField("Info","`"+quickSummonWithSpace+"info [id/emote]`"+"\n`"+quickSummonWithSpace+"<emote>`"+"\n`"+quickSummonWithSpace+"getemotes <channel id> <message id>`"+"\n`"+quickSummonWithSpace+"getreactions <channel id> <message id>`",false);
        embed.addField("Print","`"+quickSummonWithSpace+"print`",false);
        embed.addField("Add reaction to message","`"+quickSummonWithSpace+"addreaction (channelid) [messageid] [emojis]`",false);
        if(llMemberHasPermission_MANAGEEMOTES(gMember)){
            embed.addField("Set name","`"+quickSummonWithSpace+"setname [id/emote]` rename custom emojis",false);
            embed.addField("Set Role","`"+quickSummonWithSpace+"setrole [id/emote] [@roles]`"+"\n`"+quickSummonWithSpace+"allsetrole [@roles]` sets roles for custom emojis",false);
            embed.addField("Add/remove","`"+quickSummonWithSpace+"addemote`"+"`\n"+quickSummonWithSpace+"deleteemote [id/emote]` upload/delete custom emojis",false);
        }
        if(llMemberIsManager(gMember))embed.addField("Download","`"+quickSummonWithSpace+"alldownload [id/name]` the attr is optional",false);
        if(llMemberIsManager(gMember))embed.addField("Emote JsonFile","\n`" + quickSummonWithSpace + "jsonemote <attr>`\nCan add attribute `multiple` to store each emote on a separate file.",false);
        llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
        llSendMessage(gUser,embed);
    }
    private void printAll() {
        String fName = "[printAll]";
        try{
            List<Emote>emotes=gGuild.getEmotes();
            int size=emotes.size();
            logger.info(fName + ". size="+size);
            String desc="";
            if(size==0){
                llSendQuickEmbedMessage(gUser,"Emotes List","No emote to display", llColorRed);
            }
            for (Emote emote : emotes) {
                String add = "\n" + emote.getAsMention() + "|" + emote.getName() + "|" + emote.getId();
                String tmp = desc + add;
                if (tmp.length() >= 2000) {
                    llSendQuickEmbedMessage(gTextChannel,"Emotes List",desc, llColorBlue1);
                    desc = add;
                } else {
                    desc = tmp;
                }
            }
            if(!desc.isEmpty()){
                llSendQuickEmbedMessage(gTextChannel,"Emotes List",desc, llColorBlue1);
            }
        }
        catch (Exception e){
            logger.error(fName + ". exception:"+e);
        }
    }
        lcEmote gEmoteSelected=new lcEmote();
        private boolean emoteSelected(Message message,String []items) {
            String fName = "[emoteSelected]";
            try{
                String title="Emotes Mention";
                List<Emote>emotes= message.getEmotes();
                if(emotes.isEmpty()){
                    logger.info(fName + ". search id");
                    if(items.length<2){
                        logger.warn(fName + ". no id provided");
                        llSendQuickEmbedMessage(gUser,gTitle,"No id provided", llColorRed);
                        return false;
                    }
                    logger.info(fName + ".  id="+ items[1]);
                    gEmoteSelected.setGuild(gGuild);
                    if(gEmoteSelected.getEmoteById(items[1])){
                        logger.info(fName + ". valid id"); return true;
                    }else
                    if(gEmoteSelected.getEmoteByName(items[1])){
                        logger.info(fName + ". valid name"); return true;
                    }else{
                        logger.warn(fName + ". invalid id/name");
                        llSendQuickEmbedMessage(gUser,gTitle,"Invalid id/name", llColorRed);
                        return false;
                    }
                }else{
                    logger.info(fName + ". use mentioned");
                    gEmoteSelected=new lcEmote(emotes.get(0));
                    return true;
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                return false;
            }
        }
        private boolean emoteSelected2(Message message,String []items) {
            String fName = "[emoteSelected2]";
            try{
                String title="Emotes Mention";
                List<Emote>emotes= message.getEmotes();
                if(emotes.isEmpty()){
                    logger.info(fName + ". search id");
                    if(items.length<2){
                        logger.warn(fName + ". no id provided");
                        return false;
                    }
                    logger.info(fName + ".  id="+ items[1]);
                    gEmoteSelected.setGuild(gGuild);
                    if(gEmoteSelected.getEmoteById(items[1])){
                        logger.info(fName + ". valid id"); return true;
                    }else
                    if(gEmoteSelected.getEmoteByName(items[1])){
                        logger.info(fName + ". valid name"); return true;
                    }else{
                        logger.warn(fName + ". invalid id/name");
                        return false;
                    }
                }else{
                    logger.info(fName + ". use mentioned");
                    gEmoteSelected=new lcEmote(emotes.get(0));
                    return true;
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                return false;
            }
        }
        private void info() {
                String fName = "[info]";
            try{
                String title="Emotes Mention";
                List<Emote>emotes= gCommandEvent.getMessage().getEmotes();
                if(emotes.isEmpty()){
                    try {
                        emotes= new ArrayList<Emote>();
                        String []items = gCommandEvent.getArgs().split("\\s+");

                        if(items.length<2){
                            logger.warn(fName + ". no id provided");
                            llSendQuickEmbedMessage(gUser,gTitle,"No id provided", llColorRed);
                            return;
                        }
                        logger.info(fName + ".  id="+ items[1]);
                        lcEmote uEmote=new lcEmote(gGuild);
                        if(uEmote.getEmoteById(items[1])){
                            logger.info(fName + ". valid id");
                        }else
                        if(uEmote.getEmoteByName(items[1])){
                            logger.info(fName + ". valid name");
                        }else{
                            logger.warn(fName + ". invalid id/name");
                            llSendQuickEmbedMessage(gUser,gTitle,"Invalid id", llColorRed);
                            return;
                        }
                        emotes.add(uEmote.getEmote());
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        llSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e, llColorRed);
                        return;
                    }

                }

                for(Emote emote:emotes){
                    EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
                    String id=emote.getId(),name=emote.getName(),imageUrl=emote.getImageUrl();
                    logger.info(fName + ". id="+ id+", name="+name);
                    logger.info(fName + ". imageUrl="+imageUrl);
                    Guild guild=null;String guildId="?",guildName="?";
                    boolean isAnimated=emote.isAnimated(), isManaged=emote.isManaged();
                    logger.info(fName + ".  isAnimated="+ isAnimated+",  isManaged="+isManaged);
                    try {
                        guild=emote.getGuild();
                    }catch (Exception e2){
                        logger.error(fName + ". exception:"+e2);
                        logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                    }
                    if(guild!=null){
                        guildId=guild.getId();
                        guildName=guild.getName();
                    }
                    logger.info(fName + ". guildId="+guildId+", guildName="+guildName);
                    OffsetDateTime timeCreate=emote.getTimeCreated();
                    List<Role>roles=new ArrayList<>();String strRoles="";
                    try {
                        roles=emote.getRoles();
                        logger.info(fName + ". roles.size="+roles.size());
                    }catch (Exception e2){
                        logger.error(fName + ". exception:"+e2);
                        logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                    }
                    for (int i=0;i<roles.size();i++) {
                        try {
                            Role role = roles.get(i);
                            if (role.getGuild() == gGuild) {
                                if (i == 0) strRoles = role.getAsMention();
                                else strRoles += " " + role.getAsMention();
                            } else {
                                if (i == 0) strRoles = role.getName() + "(" + role.getId() + ")";
                                else strRoles += " " + role.getName() + "(" + role.getId() + ")";
                            }
                        }catch (Exception e3){
                            logger.error(fName + ". exception:"+e3);
                            logger.error(fName+ ".exception:" + Arrays.toString(e3.getStackTrace()));

                        }
                    }
                    embedBuilder.addField("Id",id,true);
                    embedBuilder.addField("Name",name,true);
                    embedBuilder.addField("Created",timeCreate.getYear()+"."+(timeCreate.getMonthValue()+1)+"."+timeCreate.getDayOfMonth(),true);
                    embedBuilder.addBlankField(false);
                    if(guild==null){
                        embedBuilder.addField("Guild","Unable to return guild!",false);
                    }else
                    if(gGuild!=guild){
                        embedBuilder.addField("Guild",guildName+"("+guildId+")",false);
                    }
                    if(!strRoles.isBlank()){
                        embedBuilder.addField("Roles",strRoles,false);
                    }
                    embedBuilder.addField("Mention",emote.getAsMention(),true);
                    embedBuilder.setImage(imageUrl);
                    llSendMessage(gTextChannel,embedBuilder);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e, llColorRed);
            }

        }
        List<Emote> emotesList =new ArrayList<>();
        int indexListAnimated=0;
        private void listAnimated() {
            String fName = "[listAnimated]";
            try{
                logger.info(fName );
                List<Emote>emotes=gGuild.getEmotes();indexListAnimated=0;
                if(emotes.isEmpty()){logger.warn(fName + "emotes is empty");return;}
                for(Emote emote:emotes){
                    if(emote.isAnimated()){
                        emotesList.add(emote);
                    }
                }
                if(emotesList.isEmpty()){logger.warn(fName + "emotesList is empty");return;}
                previewEmoji4List();
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void listStatic() {
            String fName = "[listStatic]";
            try{
                logger.info(fName );
                List<Emote>emotes=gGuild.getEmotes();indexListAnimated=0;
                if(emotes.isEmpty()){logger.warn(fName + "emotes is empty");return;}
                for(Emote emote:emotes){
                    if(!emote.isAnimated()){
                        emotesList.add(emote);
                    }
                }
                if(emotesList.isEmpty()){logger.warn(fName + "emotesList is empty");return;}
                previewEmoji4List();
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void listAll() {
            String fName = "[listAll]";
            try{
                logger.info(fName );
                emotesList=gGuild.getEmotes();indexListAnimated=0;
                if(emotesList.isEmpty()){logger.warn(fName + "emotesList is empty");return;}
                previewEmoji4List();
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void previewEmoji4List() {
            String fName = "[previewEmoji4List]";
            try{
                logger.info(fName );
                Emote emote= emotesList.get(indexListAnimated);
                logger.info(fName+"emote["+indexListAnimated+"]:id="+emote.getId()+", name="+emote.getName() );
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);embed.setTitle("Emote Preview");
                embed.addField("Name",emote.getName(),false);
                embed.setThumbnail(emote.getImageUrl());
                List<Role>roles;boolean isBlocked=false;
                try{
                    roles=emote.getRoles();
                    if(!roles.isEmpty()){
                        logger.info(fName+"emote["+indexListAnimated+"]:has roles="+roles.size() );
                        List<Role>memberRoles=gMember.getRoles();
                        logger.info(fName+"emote["+indexListAnimated+"]:has memberRoles="+memberRoles.size() );
                        for(int i=0;i<roles.size();i++){
                            for(int j=0;j<memberRoles.size();j++){
                                logger.info(fName+"emote["+indexListAnimated+"]:role cycle:"+i+","+j );
                                if(roles.get(i)==memberRoles.get(j)){
                                    logger.info(fName+"emote["+indexListAnimated+"]:role cycle found match at:"+i+","+j );
                                    break;
                                }
                            }
                        }
                        isBlocked=true;
                    }
                }
                catch (Exception e){
                    logger.error(fName + ". exception:"+e);
                    logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+"emote["+indexListAnimated+"]:isBlocked="+isBlocked );
                if(isBlocked){
                    embed.addField("Blocked","Its blocked do to role requirement!",false);
                }
                Message message=llSendMessageResponse(gTextChannel,embed);
                if(emotesList.size()>1&&indexListAnimated!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(emotesList.size()>1&&indexListAnimated>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                if(!isBlocked){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark)).queue();}
                if(emotesList.size()>1&&indexListAnimated< emotesList.size()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton)).queue();}
                if(emotesList.size()>1&&indexListAnimated!= emotesList.size()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                logger.info(fName+"prepare wait");
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            String nameCode=e.getReactionEmote().getName();
                            logger.info(fName+"nameCode="+nameCode);
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                logger.info(fName+"do=back");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if((indexListAnimated-1)>=0){
                                    indexListAnimated--;
                                    previewEmoji4List();
                                }
                            }else
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))||nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                logger.info(fName+"do=print");
                                //llMessageClearReactions(e.getChannel(),e.getMessageId());
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                try{
                                    lcWebHookBuild whh = new lcWebHookBuild();
                                    whh.doSafetyCleanwToken(gTextChannel);
                                    JSONObject json = new JSONObject();
                                    json.put("name", gMember.getEffectiveName());
                                    json.put("avatarurl", gUser.getEffectiveAvatarUrl());
                                    if (!whh.preBuild(gTextChannel, json)) {
                                        logger.error(fName + "failed prebuild");
                                        if(whh.isChannelMaxedOut(gTextChannel)){
                                            logger.error(fName + "channel maxed out");
                                        }
                                        return;
                                    }
                                    whh.doSafetyCleanwToken(gTextChannel);
                                    if (!whh.build()) {
                                        logger.error(fName + "failed build");
                                        return;
                                    }
                                    if (!whh.clientOpen()) {
                                        logger.error(fName + "failed client open");
                                        whh.delete();
                                        return;
                                    }
                                    logger.info(fName + ".send webhook");
                                    whh.send(emote.getAsMention());
                                    Thread.sleep(2000);
                                    logger.info(fName + ".close and delete webhook");
                                    whh.clientClose();
                                    Thread.sleep(1000);
                                    whh.delete();
                                }
                                catch (Exception e2){
                                    logger.error(fName + ". exception:"+e2);
                                    logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e2, llColorRed);
                                }
                            }else
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                logger.info(fName+"do=next");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if((indexListAnimated+1)< emotesList.size()) {
                                    indexListAnimated++;
                                    previewEmoji4List();
                                }
                            }else
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                logger.info(fName+"do=first");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                indexListAnimated=0;
                                previewEmoji4List();
                            }else
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                logger.info(fName+"do=last");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                indexListAnimated= emotesList.size()-1;
                                previewEmoji4List();
                            }else
                            if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                logger.info(fName+"do=delete");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                            }else{
                                logger.info(fName+"do=invalid");
                                llMessageDelete(e.getChannel(),e.getMessageId());
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void post(String name) {
            String fName = "[post]";
            try{
                logger.info(fName + "name="+name);
                name=name.replaceAll(":","");
                List<Emote>emotes=new ArrayList<Emote>();;
                emotes=gGuild.getEmotesByName(name,false);
                if(emotes.isEmpty()){logger.info(fName + "secondary search");emotes=gGuild.getEmotesByName(name,true);}
                logger.info(fName + "emotes.isEmpty="+emotes.isEmpty());
                if(emotes.isEmpty()){logger.warn(fName + "is empty");return;}
                Emote emote=emotes.get(0); InputStream is=null;
                if(!emote.isAvailable()){
                    is=lsStreamHelper.llGetInputStream4WebFile(emote.getImageUrl()+"size=40");
                }
                lcWebHookBuild whh = new lcWebHookBuild();
                whh.doSafetyCleanwToken(gTextChannel);
                JSONObject json = new JSONObject();
                json.put("name", gMember.getEffectiveName());
                json.put("avatarurl", gUser.getEffectiveAvatarUrl());
                if (!whh.preBuild(gTextChannel, json)) {
                    logger.error(fName + "failed prebuild");
                    if(whh.isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "channel maxed out");
                    }
                    return;
                }
                whh.doSafetyCleanwToken(gTextChannel);
                if (!whh.build()) {
                    logger.error(fName + "failed build");
                    return;
                }
                if (!whh.clientOpen()) {
                    logger.error(fName + "failed client open");
                    whh.delete();
                    return;
                }
                if(emote.isAvailable()){
                    logger.info(fName + ".available->send webhook");
                    whh.send(emote.getAsMention());
                }else if(is!=null){
                    if(emote.isAnimated()){
                        whh.send(is,emote.getName()+".gif");
                    }else{
                        whh.send(is,emote.getName()+".png");
                    }
                }
                Thread.sleep(2000);
                logger.info(fName + ".close and delete webhook");
                whh.clientClose();
                Thread.sleep(1000);
                whh.delete();
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getImg(String name) {
            String fName = "[getImg]";
            try{
                logger.info(fName + "name="+name);
                name=name.replaceAll(":","");
                List<Emote>emotes=new ArrayList<Emote>();
                emotes=gGuild.getEmotesByName(name,false);
                if(emotes.isEmpty()){logger.info(fName + "secondary search");emotes=gGuild.getEmotesByName(name,true);}
                logger.info(fName + "emotes.isEmpty="+emotes.isEmpty());
                if(emotes.isEmpty()){logger.warn(fName + "is empty");return;}
                Emote emote=emotes.get(0);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setImage(emote.getImageUrl());
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getImg40(String name) {
            String fName = "[getImg40]";
            try{
                logger.info(fName + "name="+name);
                name=name.replaceAll(":","");
                List<Emote>emotes= new ArrayList<Emote>();
                emotes=gGuild.getEmotesByName(name,false);
                if(emotes.isEmpty()){logger.info(fName + "secondary search");emotes=gGuild.getEmotesByName(name,true);}
                logger.info(fName + "emotes.isEmpty="+emotes.isEmpty());
                if(emotes.isEmpty()){logger.warn(fName + "is empty");return;}
                Emote emote=emotes.get(0);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setImage(emote.getImageUrl()+"?size=40");
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getImg() {
            String fName = "[getImg]";
            try{
                logger.info(fName + "");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setImage(gEmoteSelected.getImageUrl());
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getImg40() {
            String fName = "[getImg40]";
            try{
                logger.info(fName + "");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setImage(gEmoteSelected.getImageUrl()+"?size=40");
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getReactions(String id4TextChannel, String id4Message) {
            String fName = "[getReactions]";
            try{
                logger.info(fName + "id4TextChannel="+id4TextChannel+", id4Message="+id4Message);
                TextChannel textChannel=gGuild.getTextChannelById(id4TextChannel);
                if(textChannel==null){
                    logger.warn(fName + ".  no such text channel");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No such text channel!", llColorRed);
                    return;
                }
                Message message=textChannel.retrieveMessageById(id4Message).complete();
                if(message==null){
                    logger.warn(fName + ".  no such message channel");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No such message channel!", llColorRed);
                    return;
                }
                List<MessageReaction>messageReactions=message.getReactions();
                if(messageReactions.isEmpty()){
                    logger.warn(fName + ".  messageReactions is empty");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No reactions!", llColorRed);
                    return;
                }
                for (MessageReaction messageReaction : messageReactions) {
                    try {
                        EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
                        MessageReaction.ReactionEmote reactionEmote=messageReaction.getReactionEmote();
                        String name="#";
                        if(reactionEmote.isEmote()){
                            Emote emote=reactionEmote.getEmote();
                            String imageUrl=emote.getImageUrl();
                            String id=emote.getId();name=emote.getName();
                            logger.info(fName + ". id="+ id+", name="+name);
                            logger.info(fName + ". imageUrl="+imageUrl);
                            Guild guild=null;String guildId="?",guildName="?";
                            logger.info(fName + ". guildId="+guildId+", guildName="+guildName);
                            boolean isAnimated=emote.isAnimated(), isManaged=emote.isManaged();
                            logger.info(fName + ".  isAnimated="+ isAnimated+",  isManaged="+isManaged);
                            try {
                                guild=emote.getGuild();
                            }catch (Exception e2){
                                logger.error(fName + ". exception:"+e2);
                                logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                            if(guild!=null){
                                guildId=guild.getId();
                                guildName=guild.getName();
                            }
                            OffsetDateTime timeCreate=emote.getTimeCreated();
                            List<Role>roles=new ArrayList<>();String strRoles="";
                            try {
                                roles=emote.getRoles();
                            }catch (Exception e2){
                                logger.error(fName + ". exception:"+e2);
                                logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                            for (int i=0;i<roles.size();i++) {
                                try {
                                    Role role = roles.get(i);
                                    if (role.getGuild() == gGuild) {
                                        if (i == 0) strRoles = role.getAsMention();
                                        else strRoles += " " + role.getAsMention();
                                    } else {
                                        if (i == 0) strRoles = role.getName() + "(" + role.getId() + ")";
                                        else strRoles += " " + role.getName() + "(" + role.getId() + ")";
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ". exception:"+e3);
                                    logger.error(fName+ ".exception:" + Arrays.toString(e3.getStackTrace()));

                                }
                            }
                            embedBuilder.addField("Id",id,true);
                            embedBuilder.addField("Name",name,true);
                            embedBuilder.addField("Created",timeCreate.getYear()+"."+(timeCreate.getMonthValue()+1)+"."+timeCreate.getDayOfMonth(),true);
                            embedBuilder.addBlankField(false);
                            if(guild==null){
                                embedBuilder.addField("Guild","Unable to return guild!",false);
                            }else
                            if(gGuild!=guild){
                                embedBuilder.addField("Guild",guildName+"("+guildId+")",false);
                            }
                            if(!strRoles.isBlank()){
                                embedBuilder.addField("Roles",strRoles,false);
                            }
                            embedBuilder.setImage(imageUrl);
                        }
                        if(reactionEmote.isEmoji()){
                            String codepoints=reactionEmote.getAsCodepoints(),emoji=reactionEmote.getEmoji();
                            name=reactionEmote.getName();
                            logger.info(fName + ".name="+name+", emoji="+emoji+", codepoints="+codepoints);
                            embedBuilder.addField("Emoji",emoji,true);
                            embedBuilder.addField("Name",name,true);
                            embedBuilder.addField("Codepoints",codepoints,false);
                        }
                        llSendMessage(gTextChannel,embedBuilder);
                    }catch (Exception e2){
                        logger.error(fName + ". exception:"+e2);
                        logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e2, llColorRed);
                    }
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void getEmotes(String id4TextChannel, String id4Message) {
            String fName = "[getEmotes]";
            try{
                logger.info(fName + "id4TextChannel="+id4TextChannel+", id4Message="+id4Message);
                TextChannel textChannel=gGuild.getTextChannelById(id4TextChannel);
                if(textChannel==null){
                    logger.warn(fName + ".  no such text channel");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No such text channel!", llColorRed);
                    return;
                }
                Message message=textChannel.retrieveMessageById(id4Message).complete();
                if(message==null){
                    logger.warn(fName + ".  no such message channel");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No such message channel!", llColorRed);
                    return;
                }
                List<Emote>emotes=message.getEmotes();
                if(emotes.isEmpty()){
                    logger.warn(fName + ".  emotes is empty");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No emotes!", llColorRed);
                    return;
                }
                for (Emote emote : emotes) {
                    try {
                        EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
                        String id=emote.getId(),name=emote.getName(),imageUrl=emote.getImageUrl();
                        logger.info(fName + ". id="+ id+", name="+name);
                        logger.info(fName + ". imageUrl="+imageUrl);
                        Guild guild=null;String guildId="?",guildName="?";
                        boolean isAnimated=emote.isAnimated(), isManaged=emote.isManaged();
                        logger.info(fName + ".  isAnimated="+ isAnimated+", isManaged="+isManaged);
                        try {
                            guild=emote.getGuild();
                        }catch (Exception e2){
                            logger.error(fName + ". exception:"+e2);
                            logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                        if(guild!=null){
                            guildId=guild.getId();
                            guildName=guild.getName();
                        }
                        logger.info(fName + ". guildId="+guildId+", guildName="+guildName);
                        OffsetDateTime timeCreate=emote.getTimeCreated();
                        List<Role>roles=new ArrayList<>();String strRoles="";
                        try {
                            roles=emote.getRoles();
                            logger.info(fName + ". roles.size="+roles.size());
                        }catch (Exception e2){
                            logger.error(fName + ". exception:"+e2);
                            logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                        for (int i=0;i<roles.size();i++) {
                            try {
                                Role role = roles.get(i);
                                if (role.getGuild() == gGuild) {
                                    if (i == 0) strRoles = role.getAsMention();
                                    else strRoles += " " + role.getAsMention();
                                } else {
                                    if (i == 0) strRoles = role.getName() + "(" + role.getId() + ")";
                                    else strRoles += " " + role.getName() + "(" + role.getId() + ")";
                                }
                            }catch (Exception e3){
                                logger.error(fName + ". exception:"+e3);
                                logger.error(fName+ ".exception:" + Arrays.toString(e3.getStackTrace()));

                            }
                        }
                        embedBuilder.addField("Id",id,true);
                        embedBuilder.addField("Name",name,true);
                        embedBuilder.addField("Created",timeCreate.getYear()+"."+(timeCreate.getMonthValue()+1)+"."+timeCreate.getDayOfMonth(),true);
                        embedBuilder.addBlankField(false);
                        if(guild==null){
                            embedBuilder.addField("Guild","Unable to return guild!",false);
                        }else
                        if(gGuild!=guild){
                            embedBuilder.addField("Guild",guildName+"("+guildId+")",false);
                        }
                        if(!strRoles.isBlank()){
                            embedBuilder.addField("Roles",strRoles,false);
                        }
                        embedBuilder.addField("Mention",emote.getAsMention(),true);
                        embedBuilder.setImage(imageUrl);
                        llSendMessage(gTextChannel,embedBuilder);
                    }catch (Exception e2){
                        logger.error(fName + ". exception:"+e2);
                        logger.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e2, llColorRed);
                    }
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }

        }
        private void setRole() {
            String fName = "[setRole]";
            logger.info(fName);
            String title="SetRole Emote";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                List<Role> mentionedRole=gCommandEvent.getMessage().getMentionedRoles();
                boolean result;
                boolean isDefault=true;
                if(mentionedRole.isEmpty()){
                    logger.info(fName+".roles not set");
                    result=gEmoteSelected.setRole(null);
                }else{
                    logger.info(fName+".roles set");
                    isDefault=false;
                    result=gEmoteSelected.setRole(mentionedRole);
                }
                if (result) {
                    if(isDefault){
                        llSendQuickEmbedMessage(gTextChannel,title,"Set Roles to null success for "+gEmoteSelected.getAsMention()+".", llColorGreen1);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,title,"Set Roles success for "+gEmoteSelected.getAsMention()+".", llColorGreen1);
                    }
                }else{
                    llSendQuickEmbedMessage(gTextChannel,title,"Set Roles failed.", llColorRed);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }

        }
        private void setRoleForAll() {
            String fName = "[setRoleForAll]";
            logger.info(fName);
            String title="SetRole For All Emotes";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                List<Emote>emotes=gGuild.getEmotes();
                logger.info(fName+".emotes.size="+emotes.size());
                List<Role> mentionedRole= gCommandEvent.getMessage().getMentionedRoles();
                logger.info(fName+".mentionedRole.size="+mentionedRole.size());
                logger.info(fName+".mentionedRole:"+mentionedRole.toString());
                boolean isDefault=true;
                Message message=llSendQuickEmbedMessageResponse(gTextChannel,title,"Setting roles for all emojis..This might take some time.", llColorPurple1);
                if(mentionedRole.isEmpty()){
                    logger.info(fName+".roles not set");
                    for(Emote emote : emotes){
                        try {
                            logger.info(fName+".emote:"+emote.getId()+"|"+emote.getName());
                            emote.getManager().setRoles(null).complete();
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }else{
                    logger.info(fName+".roles set");
                    isDefault=false;
                    Set<Role> sroles = new HashSet<>();
                    try {
                        for (Role x : mentionedRole) {
                            logger.info(fName+".role:"+x.getId()+"|"+x.getName());
                            sroles.add(x);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    for(Emote emote : emotes){
                        try {
                            logger.info(fName+".emote:"+emote.getId()+"|"+emote.getName());
                            emote.getManager().setRoles(sroles).complete();
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }

                    }
                }
                llMessageDelete(message);
                if(isDefault){
                    llSendQuickEmbedMessage(gTextChannel,title,"Set Roles to null.", llColorGreen1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,title,"Set Roles to custom.", llColorGreen1);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }
        }
        private void setName(String items[]) {
            String fName = "[setName]";
            logger.info(fName);
            String title="SetRole Emote";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                if(items.length<2){
                    llSendQuickEmbedMessage(gUser,title,"Not provided new name", llColorRed);
                    return;
                }
                Boolean result;
                String oldName=gEmoteSelected.getName();
                result=gEmoteSelected.setName(items[2]);
                if (result) {
                    llSendQuickEmbedMessage(gTextChannel,title,"Set name from "+oldName+" to "+items[2]+" success for "+gEmoteSelected.getAsMention()+".", llColorGreen1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,title,"Set name failed.", llColorRed);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }

        }
        private void delete(){
            String fName = "[create]";
            logger.info(fName);
            String title="Delete Emote";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gUser,title,"Are you sure you want to delete "+gEmoteSelected.getName()+"|"+gEmoteSelected.getAsMention()+". If yes type `yes`.", llColorPurple1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gCommandEvent.getAuthor()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            if(e.getMessage().getContentRaw().equalsIgnoreCase("yes")){
                                logger.info(fName + ".  delete=yes");
                                Boolean result=gEmoteSelected.delete();
                                if (result) {
                                    llSendQuickEmbedMessage(gUser,title,"Emote delete success.", llColorGreen1);
                                }else{
                                    llSendQuickEmbedMessage(gUser,title,"Emote delete failed.", llColorRed);
                                }
                            }else{
                                logger.info(fName + ".  delete=no");
                            }
                        },
                        // if the gUser takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,title,"Timeout!", llColorRed));
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }

        }
        private void create() {
            String fName = "[create]";
            logger.info(fName);
            String title="Create Emote";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                List<Message.Attachment> mentionedAttachments= gCommandEvent.getMessage().getAttachments();
                if(mentionedAttachments.isEmpty()){
                    logger.info(fName + ".move to dm");
                    createDM();
                    return;
                }
                logger.info(fName + ".use channel");
                try{
                    String []itemsMessage= gCommandEvent.getArgs().split("\\s+");
                    if(itemsMessage.length<2){
                        logger.warn(fName + ".no message");
                        llSendQuickEmbedMessage(gUser,title,"Error, no message!", llColorRed); return;
                    }
                    String name=itemsMessage[0];
                    logger.info(fName + ".name="+name);
                    List<Role> mentionedRole= gCommandEvent.getMessage().getMentionedRoles();
                    if(mentionedAttachments.isEmpty()){
                        logger.warn(fName + ".no attachments");
                        llSendQuickEmbedMessage(gUser,title,"Error, no attachments!", llColorRed); return;
                    }
                    Message.Attachment item=mentionedAttachments.get(0);
                    String fileExtension= item.getFileExtension();
                    logger.info(fName + ".fileExtension="+fileExtension);
                    assert fileExtension != null;
                    if(!(fileExtension.equalsIgnoreCase("jpg")||fileExtension.equalsIgnoreCase("png")||fileExtension.equalsIgnoreCase("gif"))){
                        logger.warn(fName + ".invalid extension");
                        llSendQuickEmbedMessage(gUser,title,"Error, invalid attachments! Needs to be jpg, png or gif.", llColorRed);return;
                    }
                    String str=item.getUrl();
                    logger.info(fName+".strUrl="+str);
                    lcEmote uEmote=new lcEmote(gGuild);
                    Emote result;
                    if(mentionedRole.isEmpty()){
                        logger.info(fName+".roles not set");
                        result=uEmote.createEmote(name,str);
                    }else{
                        logger.info(fName+".roles set");
                        result=uEmote.createEmote(name,str,mentionedRole);
                    }
                    if (result!=null) {
                        llSendQuickEmbedMessage(gUser,title,"Creating emote success: "+uEmote.getAsMention(), llColorGreen1);
                    }else{
                        llSendQuickEmbedMessage(gUser,title,"Creating emote failed.", llColorRed);
                    }
                }
                catch (Exception ex){
                    llSendQuickEmbedMessage(gUser,title,"Image uploaded failed.", llColorRed);
                    logger.error(fName+"exception="+ex);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }

        }
        private void createDM() {
            String fName = "[create]";
            logger.info(fName);
            String title="Create Emote";
            try{
                if(!llMemberHasPermission_MANAGEEMOTES(gMember)){
                    llSendQuickEmbedMessage(gUser,title,"Needs permission manage emotes!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gUser,title,"Please upload an image.", llColorPurple1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gCommandEvent.getAuthor()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try{
                                String message=e.getMessage().getContentStripped();
                                if(message.isEmpty()){
                                    logger.warn(fName + ".no message");
                                    llSendQuickEmbedMessage(gUser,title,"Error, no message!", llColorRed); return;
                                }
                                String []itemsMessage = message.split("\\s+");
                                String name=itemsMessage[0];
                                logger.info(fName + ".name="+name);
                                List<Role> mentionedRole=e.getMessage().getMentionedRoles();
                                List<Message.Attachment> mentionedAttachments=e.getMessage().getAttachments();
                                if(mentionedAttachments.isEmpty()){
                                    logger.warn(fName + ".no attachments");
                                    llSendQuickEmbedMessage(gUser,title,"Error, no attachments!", llColorRed); return;
                                }
                                Message.Attachment item=mentionedAttachments.get(0);
                                String fileExtension= item.getFileExtension();
                                logger.info(fName + ".fileExtension="+fileExtension);
                                assert fileExtension != null;
                                if(!(fileExtension.equalsIgnoreCase("jpg")||fileExtension.equalsIgnoreCase("png")||fileExtension.equalsIgnoreCase("gif"))){
                                    logger.warn(fName + ".invalid extension");
                                    llSendQuickEmbedMessage(gUser,title,"Error, invalid attachments! Needs to be jpg, png or gif.", llColorRed);return;
                                }
                                String str=item.getUrl();
                                logger.info(fName+".strUrl="+str);
                                lcEmote uEmote=new lcEmote(gGuild);
                                Emote result;
                                if(mentionedRole.isEmpty()){
                                    logger.info(fName+".roles not set");
                                    result=uEmote.createEmote(name,str);
                                }else{
                                    logger.info(fName+".roles set");
                                    result=uEmote.createEmote(name,str,mentionedRole);
                                }
                                if (result!=null) {
                                    llSendQuickEmbedMessage(gUser,title,"Creating emote success.", llColorGreen1);
                                }else{
                                    llSendQuickEmbedMessage(gUser,title,"Creating emote failed.", llColorRed);
                                }
                            }
                            catch (Exception ex){
                                llSendQuickEmbedMessage(gUser,title,"Image uploaded failed.", llColorRed);
                                logger.error(fName+"exception="+ex);
                            }
                        },
                        // if the gUser takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,title,"Timeout!", llColorRed));
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }
        }
        private void sendEmoteJsonFile(Guild guild){
            String fName = "[sendEmoteLogFile]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                JSONObject object=new JSONObject();
                JSONArray array=new JSONArray();
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    object.put(llCommonKeys.keyGuild,jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                List<Emote>emotes=guild.getEmotes();
                logger.info(fName+"emotes.Size="+emotes.size());
                for(Emote emote : emotes){
                    try {
                        JSONObject jsonEmote= lsJson4Entity.llGetEmoteJsonEntry(emote);
                        if(jsonEmote!=null)array.put(jsonEmote);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                object.put(llCommonKeys.keyEmotes,array);
                InputStream targetStream = new ByteArrayInputStream(object.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Emotes", fileExtension=".json";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }

        }
        private void sendEmoteLogZipFile(Guild guild){
            String fName = "[sendEmoteLogZipFile]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension=".json";

                List<Emote>emotes=guild.getEmotes();
                logger.info(fName+"emotes.Size="+emotes.size());
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,guild.getIdLong());
                    jsonGuild.put(llCommonKeys.keyName,guild.getName());
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                for(Emote emote : emotes){
                    try {
                        JSONObject jsonEmote= lsJson4Entity.llGetEmoteJsonEntry(emote);
                        if(jsonEmote!=null) {
                            String name=entryName.replaceAll("%id",emote.getId())+entryExtension;
                            zipFile.addEntity(name,jsonEmote);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }

                InputStream targetStream = zipFile.getInputStream();
                String fileName="Emotes", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }

        }

        private void downloadAll(String mode){
            String fName = "[downloadAll]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Message  messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                lcTempZipFile zipFile=new lcTempZipFile();
                String entryName="%id", entryExtension="";
                List<Emote>emotes=gGuild.getEmotes();
                logger.info(fName+"emotes.Size="+emotes.size());
                int countStatic=0,countAnimated=0;
                int countUnavailable=0,countStaticUnavailable=0,countAnimatedUnavailable=0;;
                for(Emote emote : emotes){
                    try {
                        JSONObject jsonEmote=lsJson4Entity.llGetEmoteJsonEntry(emote);
                        zipFile.addEntity(emote.getId()+".json",jsonEmote);
                        if(emote.isAnimated()){
                            countAnimated++;
                            if(!emote.isAvailable()){
                                countUnavailable++;
                                countAnimatedUnavailable++;
                            }
                        }
                        else{
                            countStatic++;
                            if(!emote.isAvailable()){
                                countUnavailable++;
                                countStaticUnavailable++;
                            }
                        }

                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    try {
                        String emoteUrl=emote.getImageUrl();
                        InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(emoteUrl);
                        if(inputStream!=null) {
                            if(emoteUrl.toLowerCase().contains("png"))entryExtension=".png";
                            if(emoteUrl.toLowerCase().contains("jpg"))entryExtension=".jpg";
                            if(emoteUrl.toLowerCase().contains("gif"))entryExtension=".gif";
                            String namex=entryName.replaceAll("%id",emote.getId())+entryExtension;
                            zipFile.addEntity(namex,inputStream);
                            inputStream.close();
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                try {
                    JSONObject jsonGuild=new JSONObject();
                    jsonGuild.put(llCommonKeys.keyId,gGuild.getId());
                    jsonGuild.put(llCommonKeys.keyName,gGuild.getName());
                    jsonGuild.put(llCommonKeys.keyCount,new JSONObject());
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).put("total",emotes.size());
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).put("static",countStatic);
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).put("animated",countAnimated);
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).put("unavailable",new JSONObject());
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("total",countUnavailable);
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("static",countStaticUnavailable);
                    jsonGuild.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("animated",countAnimatedUnavailable);
                    zipFile.addEntity("guild.json",jsonGuild);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Emotes", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the server emotes:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void printEmoji(String name){
            String fName = "[printEmoji]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                logger.info(fName + ".name=" + name);
                UnicodeEmojis emoji=new UnicodeEmojis();
                String strEmoji=emoji.getEmoji(name);
                String strEmojiChar=emoji.getEmojiChar(name);
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
                embedBuilder.setDescription("Name: "+name+", emoji:"+strEmoji+", emojichar:"+strEmojiChar);
                llSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void addReaction(String channelId,String messageId) {
            String fName = "[addReaction]";
            logger.info(fName);
            try{
                List<Emote>emotes=gMessage.getEmotes();
                logger.info(fName + ". emotes.size="+emotes.size());
                if(emotes.isEmpty()){
                    lsMessageHelper.lsSendMessage(gTextChannel,"No emotes mentioned.");
                    return;
                }
                TextChannel textChannel;
                Message message;
                logger.info(fName + ". channelId="+channelId);
                try{
                    textChannel=gGuild.getTextChannelById(channelId);
                    if(textChannel==null){
                        logger.warn(fName + ".No such channel with this id");
                        lsMessageHelper.lsSendMessage(gTextChannel,"No such channel with this id");
                        return;
                    }
                }
                catch (Exception e){
                    logger.error(fName + ". exception:"+e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendMessage(gTextChannel,"No such channel with this id");
                    return;
                }
                logger.info(fName + ".messageId="+messageId);
                try{
                    message=textChannel.retrieveMessageById(messageId).complete();
                    if(message==null){
                        logger.warn(fName + ".No such message with this id");
                        lsMessageHelper.lsSendMessage(gTextChannel,"No such message with this id");
                        return;
                    }
                }
                catch (Exception e){
                    logger.error(fName + ". exception:"+e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendMessage(gTextChannel,"No such message with this id");
                    return;
                }
                for(Emote emote:emotes){
                    try {
                        logger.info(fName + ".adding emote 2 queue");
                        message.addReaction(emote).queue();
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendMessage(gTextChannel,"Exception:"+e);
            }

        }

    
    
    
  //runLocal  
    }
}
