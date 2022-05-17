package nsfw;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LewdImageEvents extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gTitle="LewdImageEvents",gCommand=".";
    public LewdImageEvents(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Simple lewd stories/events linked to images.";
        this.aliases = new String[]{gCommand,"lewdevent","lewdevents"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;

    }
    public LewdImageEvents(lcGlobalHelper global, GuildMessageReceivedEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
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
        CommandEvent gEvent;GuildMessageReceivedEvent gGuildMessageReceivedEvent;
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
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
        public runLocal(GuildMessageReceivedEvent event) {
            String fName="build";logger.info(".run build");
            gGuildMessageReceivedEvent = event;
            gUser = gGuildMessageReceivedEvent.getAuthor();gMember=gGuildMessageReceivedEvent.getMember();
            gGuild = gGuildMessageReceivedEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gGuildMessageReceivedEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            String[] items;
            Boolean isInvalidCommand = true;
            try{
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"LewdImageEvents",gGlobal);
                gBasicFeatureControl.initProfile();
                if(!isNSFW()){
                    logger.warn(fName + ".not nsfw channel");
                    blocked();
                    return;
                }
                if(gGuildMessageReceivedEvent!=null){
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw().replaceFirst(".","");
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    items = args.split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items.length>=3&&isTargeted(items[1])){

                    }else{
                        if(!gBasicFeatureControl.getEnable()){
                            logger.info(fName+"its disabled");
                            return;
                        }
                        else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                            logger.info(fName+"its not allowed by channel");
                           return;
                        }
                        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                            logger.info(fName+"its not allowed by roles");
                            return;
                        }
                        if(items[0].equalsIgnoreCase("thanksgiving")||items[0].equalsIgnoreCase("turkey")){
                            doSelection("thanksgiving");
                        }
                        else if(items[0].equalsIgnoreCase("bondage")||items[0].equalsIgnoreCase("bdsm")){
                            doSelection("bondage");
                        }
                        else if(items[0].equalsIgnoreCase("puppy")||items[0].equalsIgnoreCase("pup")){
                            doSelection("puppy");
                        }
                        else if(items[0].equalsIgnoreCase("chastity")){
                            doSelection("chastity");
                        }
                        else if(items[0].equalsIgnoreCase("pony")){
                            doSelection("pony");
                        }
                        else if(items[0].equalsIgnoreCase("straitjacket")){
                            doSelection("straitjacket");
                        }
                        else if(items[0].equalsIgnoreCase("gimp")){
                            doSelection("gimp");
                        }
                    }
                }else{
                    try {
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
                                }
                                else if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
                                    if(items.length>2){
                                        // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                        int group=0,type=0,action=0;
                                        switch (items[1].toLowerCase()){
                                            case "allowedchannels":
                                            case "allowchannels":
                                                group=1;type=1;
                                                break;
                                            case "blockedchannels":
                                            case "blockchannels":
                                                group=1;type=-1;
                                                break;
                                            case "allowedroles":
                                            case "allowroles":
                                                group=2;type=1;
                                                break;
                                            case "blockedroles":
                                            case "blockroles":
                                                group=2;type=-1;
                                                break;
                                        }
                                        switch (items[2].toLowerCase()){
                                            case "list":
                                                action=0;
                                                break;
                                            case "add":
                                                action=1;
                                                break;
                                            case "set":
                                                action=2;
                                                break;
                                            case "rem":
                                                action=-1;
                                                break;
                                            case "clear":
                                                action=-2;
                                                break;
                                        }
                                        if(group==1){
                                            if(action==0){
                                                getChannels(type,false);isInvalidCommand=false;
                                            }else{
                                                setChannel(type,action,gEvent.getMessage());
                                            }
                                        }
                                        else if(group==2){
                                            if(action==0){
                                                getRoles(type,false);isInvalidCommand=false;
                                            }else{
                                                setRole(type,action,gEvent.getMessage());
                                            }
                                        }
                                    }else{
                                        menuGuild();isInvalidCommand=false;
                                    }
                                }
                                else if(!gBasicFeatureControl.getEnable()){
                                    logger.info(fName+"its disabled");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                    isInvalidCommand=false;
                                }
                                else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                    logger.info(fName+"its not allowed by channel");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                    isInvalidCommand=false;
                                }
                                else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                    logger.info(fName+"its not allowed by roles");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                                    isInvalidCommand=false;
                                }
                                else if(!isNSFW()){
                                    blocked();isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("thanksgiving")||items[0].equalsIgnoreCase("turkey")){
                                    doSelection("thanksgiving");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("bondage")||items[0].equalsIgnoreCase("bdsm")){
                                    doSelection("bondage");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("puppy")||items[0].equalsIgnoreCase("pup")){
                                    doSelection("puppy");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("chastity")){
                                    doSelection("chastity");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("pony")){
                                    doSelection("pony");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("straitjacket")){
                                    doSelection("straitjacket");isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("gimp")){
                                    doSelection("gimp");isInvalidCommand=false;
                                }
                            }
                        }
                        logger.info(fName+".deleting op message");
                        lsMessageHelper.lsMessageDelete(gEvent);
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
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
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
            embed.addField("Commands","`"+quickSummonWithSpace+"[thanksgiving/turkey|bondage|chastity|pup|gimp|straitjacket|pony]` to view an event with image.",false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        String gUrlMainSoloPath="resources/json/nsfw/imagevent/solo";
        lcText2Json text2Json=null;
        String keyE621="e621",keyFA="fa",keyText="text";
        String gUrlE621GetPost ="https://e621.net/posts/!id.json", gUrlFAPost="https://www.furaffinity.net/view/!id/";
        String keyFile="file", keyUrl ="url",keyShowAuthor="showAuthor";
        JSONObject jsonSelect;
        private void doSelection(String name) {
            String fName="[doSelection]";
            logger.info(fName);
            try {
                logger.info(fName+"name="+name);
                if(!readFile(name)){
                    logger.warn(fName+".failed to load");
                    return;
                }
                int random=lsUsefullFunctions.getRandom(text2Json.jsonArray.length());
                logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                int count=0;
                while((random<0||text2Json.jsonArray.length()<=random)&&count<50){
                    random=lsUsefullFunctions.getRandom(text2Json.jsonArray.length());count++;
                    logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                }
                if(random<0||text2Json.jsonArray.length()<=random){
                    logger.warn(fName+".invalid random");
                    return;
                }
                jsonSelect=text2Json.jsonArray.getJSONObject(random);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                if(!jsonSelect.has(keyText)){
                    logger.warn(fName+".keyText is missing");return;
                }
                if(jsonSelect.isNull(keyText)){
                    logger.warn(fName+".keyText is null");return;
                }

                if(jsonSelect.has(keyE621)&&!jsonSelect.isNull(keyE621)&&!jsonSelect.getString(keyE621).isBlank()){
                    logger.info(fName+".keyE621 found");
                    doPreviewE621(jsonSelect.getString(keyE621));
                }else
                if(jsonSelect.has(keyFA)&&!jsonSelect.isNull(keyFA)&&!jsonSelect.getString(keyFA).isBlank()){
                    doPreviewFA(jsonSelect.getString(keyFA));
                }else{
                    logger.warn(fName+".keyE621 and keyFA are missing or null");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void deleteOption(Message message) {
            String fName="[deleteOption]";
            logger.info(fName);
            try {
                try {
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                        e -> {
                            try {
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.info(fName+"asCodepoints="+asCodepoints);
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicodeEmote_X_Red)){
                                    logger.info(fName+"do=delete");
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    logger.info(fName+"do=invalid");
                                    deleteOption(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageClearReactionsQueue(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean readFile(String name) {
            String fName="[readFile]";
            logger.info(fName);
            try {
                File file1, file2;InputStream fileStream=null;
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".txt");
                    file1=new File(gUrlMainSoloPath+"/"+name+".txt");
                    if(file1.exists()){
                        fileStream = new FileInputStream(file1);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".json");
                    file2=new File(gUrlMainSoloPath+"/"+name+".json");
                    if(file2.exists()){
                        fileStream = new FileInputStream(file2);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                text2Json=new lcText2Json();
                if(fileStream!=null){
                    if(!text2Json.isInputStream2JsonArray(fileStream)){
                        logger.warn(fName+".failed to load");
                        return false;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }
                if(text2Json.jsonArray.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName+"length="+text2Json.jsonArray.length());
                logger.info(fName + ".text2Json.jsonArray=" + text2Json.jsonArray.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        String gUrlFAUser ="https://www.furaffinity.net/user/%/";
        String keyE621File ="file", keyE621Url ="url",keyE621Ext="ext",keyE621Rating="rating",keyE621Tags="tags",keyE621General="general";
        private void doPreviewE621(String viewId) {
            String fName="[doPreviewE621]";
            logger.info(fName);
            try {
                logger.info(fName+"viewId="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url=gUrlE621GetPost.replaceAll("!id",viewId);
                logger.info(fName+".url ="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                int status=response.getStatus();
                                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                                    return;}
                                JsonNode body=response.getBody();
                                logger.debug(fName+".body ="+body.toPrettyString());
                                JSONObject json=body.getObject();
                                if(json.isEmpty()||!json.has("post")||json.isNull("post")){
                                    logger.error(fName+".no post");return;
                                }
                                JSONObject post=json.getJSONObject("post");
                                String id="",ext="",imgUrl="",rating="";
                                JSONArray tags_general=new JSONArray(), artists=new JSONArray();
                                boolean isCompatible=false,isBanWordsInTags=false;
                                if(post.has(keyE621Rating)&&!post.isNull(keyE621Rating)){
                                    rating=post.getString(keyE621Rating);//rating can be:s,q,e
                                }
                                if(post.has(keyE621File)&&!post.isNull(keyE621File)&&post.getJSONObject(keyE621File).has(keyE621Url)&&!post.getJSONObject(keyE621File).isNull(keyE621Url)){
                                    logger.info(fName+".entered post 2");
                                    if(post.getJSONObject(keyE621File).has(keyE621Ext)&&!post.getJSONObject(keyE621File).isNull(keyE621Ext)){
                                        logger.info(fName+".entered post 3");
                                        ext=post.getJSONObject(keyE621File).getString(keyE621Ext);
                                        logger.info(fName+".ext="+ext);
                                        if(ext.contains("webm")||ext.contains("swf"))isCompatible=false;
                                        if(ext.contains("png")||ext.contains("jpg")||ext.contains("gif"))isCompatible=true;
                                        imgUrl=post.getJSONObject(keyE621File).getString(keyE621Url);
                                    }
                                    logger.info(fName+".imgUrl="+imgUrl);
                                }
                                if(post.has(keyE621Tags)&&!post.isNull(keyE621Tags)){
                                    if(post.getJSONObject(keyE621Tags).has(keyE621General)&&!post.getJSONObject(keyE621Tags).isNull(keyE621General)) {
                                        tags_general = post.getJSONObject(keyE621Tags).getJSONArray(keyE621General);
                                    }
                                }
                                for(int i=0;i<tags_general.length();i++){
                                    String tag=tags_general.getString(i);
                                    if(tag.toLowerCase().contains("cub")||tag.toLowerCase().contains("kid")||tag.toLowerCase().contains("child")||tag.toLowerCase().contains("baby")){
                                        logger.warn(fName+"contains ban words");
                                        isBanWordsInTags=true;
                                    }
                                }
                                if(post.has("id")) { id = post.getString("id"); }
                                if(post.has("artist")){ artists=post.getJSONArray("artist");}

                                logger.info(fName+".isCompatible="+isCompatible+", isBanWordsInTags="+isBanWordsInTags+", rating="+rating+", is ChannelNSFW="+gTextChannel.isNSFW());
                                if(rating!=null&&isCompatible&&!isBanWordsInTags){
                                    if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&gTextChannel.isNSFW()){
                                        logger.info(fName+".nsfw channel");
                                        embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                                            if(artists!=null&&artists.length()>=1){
                                                embedBuilder.addField("Artist",artists.getString(0),true);
                                            }
                                        }
                                        embedBuilder.setImage(imgUrl);
                                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                                            String text=jsonSelect.getString(keyText);
                                            logger.info(fName+".text before="+text);
                                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                                            logger.info(fName+".text after="+text);
                                            embedBuilder.setDescription(text);
                                        }
                                    }else
                                    if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&!gTextChannel.isNSFW()){
                                        logger.warn(fName+".not nsfw channel");
                                        embedBuilder.setDescription("Not NSFW channel");
                                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                                    }else if(rating.equalsIgnoreCase("s")){
                                        logger.info(fName+".sfw content");
                                        embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                                            if(artists!=null&&artists.length()>=1){
                                                embedBuilder.addField("Artist",artists.getString(0),true);
                                            }
                                        }
                                        embedBuilder.setImage(imgUrl);
                                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                                            String text=jsonSelect.getString(keyText);
                                            logger.info(fName+".text before="+text);
                                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                                            logger.info(fName+".text after="+text);
                                            embedBuilder.setDescription(text);
                                        }
                                    }else{
                                        embedBuilder.setDescription("Invalid rating");
                                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                                    }
                                }else{
                                    logger.warn(fName + ".No image found");
                                    embedBuilder.setDescription("No image found");
                                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                                }
                                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e); logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String gUrlFapi="https://bawk.space/fapi/submission/%";
        String keyAuthor="author",keyAvatar="avatar",keyImageUrl="image_url",keyRating="rating",keyTitle="title";
        private void doPreviewFA(String viewId) {
            String fName = "[doPreviewFA]";
            try {
                logger.info(fName+".viewId ="+viewId);
                Unirest a= new Unirest();
                String url=gUrlFapi.replaceAll("%",viewId);
                logger.info(fName+".url ="+url);
                HttpResponse<JsonNode> jsonResponse =a.get(url)
                        .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        //.header("cookie","__cfduid=deeaef1de440c69a982b08688bcb8c0ad1593253201; b=6c76433b-5c4f-412a-92eb-c76d2df1a985; __gads=ID=6ede4f83e6fdeae2:T=1593253203:S=ALNI_MbzxADPN8g6RHuFv-hBKPQ96JPpmg; __qca=P0-487465766-1593253203374; cc=1; a=5e16e67e-0ab2-44a3-9769-3235eebdc393; sz=1311x627")
                        .header("dnt","1")
                        .header("referer:",url)
                        .header("sec-fetch-dest","document")
                        .header("sec-fetch-mode","navigate")
                        .header("sec-fetch-site:","same-origin")
                        .header("sec-fetch-user","?1")
                        .header("upgrade-insecure-requests","1")
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>200){
                    logger.error(fName+".invalid status"); return ;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                String image="",rating="",title="",author="",authorAvatar="";
                if(body.has(keyAuthor)&&!body.isNull(keyAuthor))author=body.getString(keyAuthor);
                if(body.has(keyImageUrl)&&!body.isNull(keyImageUrl))image=body.getString(keyImageUrl);
                if(body.has(keyAvatar)&&!body.isNull(keyAvatar))authorAvatar=body.getString(keyAvatar);
                if(body.has(keyTitle)&&!body.isNull(keyTitle))title=body.getString(keyTitle);
                if(body.has(keyRating)&&!body.isNull(keyRating))rating=body.getString(keyRating);


                if(!image.isBlank()&&!image.isEmpty()){
                    logger.info(fName+"rating="+rating+", isNSFW="+gTextChannel.isNSFW());
                    if((rating.equalsIgnoreCase("mature")||rating.equalsIgnoreCase("adult"))&&gTextChannel.isNSFW()){
                        logger.info(fName+".nsfw channel");
                        embedBuilder.setImage(image);
                        embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)) {
                            if (authorAvatar.isBlank()) {
                                embedBuilder.setAuthor(author, gUrlFAUser.replaceAll("%", author.toLowerCase()), "https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                            } else {
                                embedBuilder.setAuthor(author, gUrlFAUser.replaceAll("%", author.toLowerCase()), authorAvatar);
                            }
                        }
                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                            String text=jsonSelect.getString(keyText);
                            logger.info(fName+".text before="+text);
                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                            logger.info(fName+".text after="+text);
                            embedBuilder.setDescription(text);
                        }
                    }else
                    if((rating.equalsIgnoreCase("mature")||rating.equalsIgnoreCase("adult"))&&!gTextChannel.isNSFW()){
                        logger.warn(fName+".not nsfw channel");
                        embedBuilder.setDescription("Not NSFW channel");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }else if(rating.equalsIgnoreCase("general")){
                        logger.info(fName+".sfw content");
                        embedBuilder.setImage(image);
                        embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                            if(authorAvatar.isBlank()){
                                //embedBuilder.setAuthor(author,null,"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                                embedBuilder.setAuthor(author,gUrlFAUser.replaceAll("%",author.toLowerCase()),"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                            }else{
                                //embedBuilder.setAuthor(author,null,"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                                embedBuilder.setAuthor(author,gUrlFAUser.replaceAll("%",author.toLowerCase()),authorAvatar);
                            }
                        }
                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                            String text=jsonSelect.getString(keyText);
                            logger.info(fName+".text before="+text);
                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                            logger.info(fName+".text after="+text);
                            embedBuilder.setDescription(text);
                        }
                    }else{
                        logger.warn(fName+".invalid rating");
                        embedBuilder.setDescription("Invalid rating");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }
                }else {
                    logger.warn(fName + ".No image found");
                    embedBuilder.setDescription("No image found");
                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                }
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doPreviewFA_v1(String viewId) {
            String fName = "[doPreviewFA]";
            try {
                logger.info(fName+".viewId ="+viewId);
                Unirest a= new Unirest();
                String url=gUrlFAPost.replaceAll("!id",viewId);
                logger.info(fName+".url ="+url);
                HttpResponse<String> jsonResponse =a.get(url)
                        .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.173")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("cookie","__cfduid=deeaef1de440c69a982b08688bcb8c0ad1593253201; b=6c76433b-5c4f-412a-92eb-c76d2df1a985; __gads=ID=6ede4f83e6fdeae2:T=1593253203:S=ALNI_MbzxADPN8g6RHuFv-hBKPQ96JPpmg; __qca=P0-487465766-1593253203374; cc=1; a=5e16e67e-0ab2-44a3-9769-3235eebdc393; sz=1311x627")
                        .header("dnt","1")
                        .header("referer:",url)
                        .header("sec-fetch-dest","document")
                        .header("sec-fetch-mode","navigate")
                        .header("sec-fetch-site:","same-origin")
                        .header("sec-fetch-user","?1")
                        .header("upgrade-insecure-requests","1")
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>200){
                    logger.error(fName+".invalid status"); return ;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                String textShadow="";
                int i=-1,j=-1,l;
                String begin="";
                String image="";begin="img id=\"submissionImg";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    i=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(i);
                    j=textShadow.indexOf("\">");
                    textShadow=textShadow.substring(0,j);
                    begin="src=\"//";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            image=textShadow.substring(0, j);
                            logger.info(fName+"image="+image);
                        }
                    }
                }
                String imageAuthor="";begin="img class=\"submission-user-icon floatleft avatar\"";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    i=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(i);
                    j=textShadow.indexOf("\">");
                    textShadow=textShadow.substring(0,j);
                    begin="src=\"//";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            imageAuthor=textShadow.substring(0, j);
                            logger.info(fName+"imageAuthor="+imageAuthor);
                        }
                    }
                }
                String title="";begin="<div class=\"submission-title";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    i=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(i);
                    j=textShadow.indexOf("/div>");
                    textShadow=textShadow.substring(0,j);
                    begin="<p>";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("</p>");
                        if(i>0&&j>0) {
                            title=textShadow.substring(0, j);
                            logger.info(fName+"title="+title);
                        }
                    }
                }
                String author="";begin="<div class=\"submission-id-sub-container";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    i=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(i);
                    j=textShadow.indexOf("div class=\"section-body");
                    textShadow=textShadow.substring(0,j);
                    begin="<strong>";
                    if( textShadow.contains(begin)){
                        logger.info(fName+".found:"+ begin);
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("</strong>");
                        if(i>0&&j>0) {
                            author=textShadow.substring(0, j);
                            logger.info(fName+"author="+author);
                        }
                    }
                }
                String user="";begin="<a href=\"/user/";
                if(body.contains(begin)){
                    logger.info(fName+".found:"+ begin);
                    i=body.indexOf(begin);
                    l=begin.length();
                    textShadow=body.substring(i);
                    j=textShadow.indexOf("/\">");
                    textShadow=textShadow.substring(0,j);
                    user=textShadow.substring(l, j);
                    logger.info(fName+"user="+user);
                }
                int rating=-1;
                if(body.contains("<span class=\"font-large rating-box inline general")) rating=0;
                if(body.contains("<span class=\"font-large rating-box inline mature"))rating=1;
                if(body.contains("<span class=\"font-large rating-box inline adult"))rating=2;

                if(!image.isBlank()&&!image.isEmpty()){
                    logger.info(fName+"rating="+rating+", isNSFW="+gTextChannel.isNSFW());
                    if(rating!=0&&gTextChannel.isNSFW()){
                        logger.info(fName+".nsfw channel");
                        embedBuilder.setImage("http://"+image);
                        embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)) {
                            if (imageAuthor.isBlank()) {
                                embedBuilder.setAuthor(author, gUrlFAUser.replaceAll("%", user), "https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                            } else {
                                embedBuilder.setAuthor(author, gUrlFAUser.replaceAll("%", user), imageAuthor);
                            }
                        }
                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                            String text=jsonSelect.getString(keyText);
                            logger.info(fName+".text before="+text);
                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                            logger.info(fName+".text after="+text);
                            embedBuilder.setDescription(text);
                        }
                    }else
                    if(rating!=0&&!gTextChannel.isNSFW()){
                        logger.warn(fName+".not nsfw channel");
                        embedBuilder.setDescription("Not NSFW channel");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }else{
                        logger.info(fName+".sfw content");
                        embedBuilder.setImage("http://"+image);
                        embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                            if(imageAuthor.isBlank()){
                                //bug
                                embedBuilder.setAuthor(author,null,"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                                //embedBuilder.setAuthor(author,gUrlFAUser.replaceAll("%",user),"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                            }else{
                                embedBuilder.setAuthor(author,null,"https://www.furaffinity.net/themes/beta/img/banners/fa_logo_20191231.png");
                                //embedBuilder.setAuthor(author,gUrlFAUser.replaceAll("%",user),imageAuthor);
                            }
                        }
                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                            String text=jsonSelect.getString(keyText);
                            logger.info(fName+".text before="+text);
                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                            logger.info(fName+".text after="+text);
                            embedBuilder.setDescription(text);
                        }
                    }


                }else {
                    logger.warn(fName + ".No image found");
                    embedBuilder.setDescription("No image found");
                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                }
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String gUrlSearch ="https://inkbunny.net/api_search.php?sid=!sid&text=!text&get_rid=yes&submissions_per_page=100";
        String gUrlInkBunnyView ="https://inkbunny.net/api_submissions.php?sid=!sid&submission_ids=!id";
        String gUrlInkBunnySubmissionPage ="https://inkbunny.net/s/!id";
        String gUrlInkBunnyUserPage ="https://inkbunny.net/!name";
        String gUrlLogInGuest ="https://inkbunny.net/api_login.php?username=guest";
        String gUrlLogInUser ="https://inkbunny.net/api_login.php?username=!username&password=!password";
        String gInkBunnySIDUser ="NK5XbvH55euzWEEgt5PwPn13Ue";
        private void doPreviewInkbunny(String viewId) {
            String fName = "[doPreviewInkbunny]";
            try {
                logger.info(fName+".viewId ="+viewId);
                doInkbunnyHtmlCheckup();
                listmimetype=new JSONObject();
                listmimetype.put("image/bmp",true);
                listmimetype.put("image/gif",true);
                listmimetype.put("image/jpeg",true);
                listmimetype.put("image/png",true);
                logger.info(fName+"viewId="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url=gUrlInkBunnyView.replaceAll("!id",viewId).replaceAll("!sid",gInkBunnySIDUser);
                logger.info(fName+".url ="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                int status=response.getStatus();
                                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                                    return;}
                                JsonNode body=response.getBody();
                                logger.debug(fName+".body ="+body.toPrettyString());
                                JSONObject json=body.getObject();
                                if(json.isEmpty()||!json.has("submissions")||json.isNull("submissions")){
                                    logger.error(fName+".no submissions");return;
                                }
                                JSONArray submissions=json.getJSONArray("submissions");
                                if(submissions.isEmpty()||submissions.length()<0){
                                    logger.error(fName+".no post");return;
                                }
                                JSONObject post=submissions.getJSONObject(0);
                                logger.info(fName+".post="+post.toString());
                                boolean isPublic=false,isGuestBlock=false,isHidden=false,isImage=false, isBadKeywords=false;
                                int ratingId=-1;
                                String postId="",title="",authorName="",authorId="",authorAvatar="", image="";
                                StringBuilder ratings= new StringBuilder();
                                if(post.has(keyIBPublic)&&!post.isNull(keyIBPublic)&&post.getString(keyIBPublic).equals(valueTrue)){
                                    isPublic=true;
                                }
                                if(post.has(keyIBGuestBlock)&&!post.isNull(keyIBGuestBlock)&&post.getString(keyIBGuestBlock).equals(valueTrue)){
                                    isGuestBlock=true;
                                }
                                if(post.has(keyIBHidden)&&!post.isNull(keyIBHidden)&&post.getString(keyIBHidden).equals(valueTrue)){
                                    isHidden=true;
                                }
                                if(post.has(keyIBMimeType)&&!post.isNull(keyIBMimeType)){
                                    if(listmimetype.has(post.getString(keyIBMimeType))&&listmimetype.getBoolean(post.getString(keyIBMimeType))){
                                        isImage=true;
                                    }
                                }
                                logger.info(fName+".isPublic="+isPublic+", isHidden="+isHidden+", isImage="+isImage);
                                if(!isPublic){
                                    logger.warn(fName + ".not public");return;
                                }
                                if(isHidden){
                                    logger.warn(fName + ".is hidden");return;
                                }
                                if(!isImage){
                                    logger.warn(fName + ".not image");return;
                                }
                                logger.info(fName+".isGuestBlock="+isGuestBlock);
                                if(post.has(keyIBUserId)&&!post.isNull(keyIBUserId)){
                                    authorId=post.getString(keyIBUserId);
                                }
                                if(post.has(keyIBUserName)&&!post.isNull(keyIBUserName)){
                                    authorName=post.getString(keyIBUserName);
                                }
                                if(post.has(keyIBUserIconUrlLarge)&&!post.isNull(keyIBUserIconUrlLarge)) {
                                    authorAvatar = post.getString(keyIBUserIconUrlLarge);
                                }
                                logger.info(fName+".authorId="+authorId+", authorName="+authorName+", authorAvatar="+authorAvatar);
                                if(post.has(keyIBSubmissionId)&&!post.isNull(keyIBSubmissionId)) {
                                    postId = post.getString(keyIBSubmissionId);
                                }
                                if(post.has(keyIBTitle)&&!post.isNull(keyIBTitle)) {
                                    title = post.getString(keyIBTitle);
                                }
                                logger.info(fName+". postId="+ postId+", title="+title);

                                if(post.has(keyIBRatingId)&&!post.isNull(keyIBRatingId)){
                                    ratingId=post.getInt(keyIBRatingId);//0-general 1-matur 2-adult
                                }
                                if(post.has(keyIBRatingName)&&!post.isNull(keyIBRatingName)){
                                    ratings = new StringBuilder(post.getString(keyIBRatingName));
                                }
                                if(post.has(keyIBRatings)&&!post.isNull(keyIBRatings)){
                                    JSONArray array=post.getJSONArray(keyIBRatings);
                                    for(int i=0;i<array.length();i++){
                                        ratings.append(", ").append(array.getJSONObject(i).getString(keyIBName));
                                    }
                                }
                                logger.info(fName+".ratingId="+ratingId+" ,ratings="+ratings);
                                if(post.has(keyIBKeywords)&&!post.isNull(keyIBKeywords)){
                                    JSONArray array=post.getJSONArray(keyIBKeywords);
                                    for(int i=0;i<array.length();i++){
                                        try {
                                            String keywordName=array.getJSONObject(i).getString(keyIBKeywordName);
                                            if(keywordName.toLowerCase().contains("cub")||keywordName.toLowerCase().contains("kid")||keywordName.toLowerCase().contains("child")||keywordName.toLowerCase().contains("baby")){
                                                logger.warn(fName+"contains badword");isBadKeywords=true;
                                                break;
                                            }
                                        }catch (Exception e){
                                            logger.error(fName + ".exception=" + e);
                                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                        }
                                    }
                                }

                                if(post.has(keyIBFileUrlFull)&&!post.isNull(keyIBFileUrlFull)){
                                    image=post.getString(keyIBFileUrlFull);
                                }else
                                if(post.has(keyIBFileUrlScreen)&&!post.isNull(keyIBFileUrlScreen)){
                                    image=post.getString(keyIBFileUrlScreen);
                                }else
                                if(post.has(keyIBFileUrlPreview)&&!post.isNull(keyIBFileUrlPreview)){
                                    image=post.getString(keyIBFileUrlPreview);
                                }

                                if(!image.isBlank()&&!image.isEmpty()&&!isBadKeywords){
                                    logger.info(fName+"rating="+ratingId+", isNSFW="+gTextChannel.isNSFW());
                                    if(ratingId!=0&&gTextChannel.isNSFW()){
                                        logger.info(fName+".nsfw channel");
                                        embedBuilder.setImage("http://"+image);
                                        embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                                            if(!authorName.isBlank()){
                                                if(!authorAvatar.isBlank()){
                                                    embedBuilder.setAuthor(authorName,gUrlInkBunnyUserPage.replaceAll("!name",authorName),authorAvatar);
                                                }
                                                else{
                                                    embedBuilder.setAuthor(authorName,gUrlInkBunnyUserPage.replaceAll("!name",authorName));
                                                }
                                            }
                                        }
                                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                                            String text=jsonSelect.getString(keyText);
                                            logger.info(fName+".text before="+text);
                                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                                            logger.info(fName+".text after="+text);
                                            embedBuilder.setDescription(text);
                                        }
                                    }else
                                    if(ratingId!=0&&!gTextChannel.isNSFW()){
                                        logger.warn(fName+".not nsfw channel");
                                        embedBuilder.setDescription("Not NSFW channel");
                                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                                    }else{
                                        logger.info(fName+".sfw content");
                                        embedBuilder.setImage("http://"+image);
                                        embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                                        if(jsonSelect.has(keyShowAuthor)&&!jsonSelect.isNull(keyShowAuthor)&&jsonSelect.getBoolean(keyShowAuthor)){
                                            if(!authorName.isBlank()){
                                                if(!authorAvatar.isBlank()){
                                                    embedBuilder.setAuthor(authorName,gUrlInkBunnyUserPage.replaceAll("!name",authorName),authorAvatar);
                                                }
                                                else{
                                                    embedBuilder.setAuthor(authorName,gUrlInkBunnyUserPage.replaceAll("!name",authorName));
                                                }
                                            }
                                        }
                                        if(jsonSelect.has(keyText)&&!jsonSelect.isNull(keyText)&&!jsonSelect.getString(keyText).isBlank()){
                                            String text=jsonSelect.getString(keyText);
                                            logger.info(fName+".text before="+text);
                                            text=text.replaceAll("@User",gMember.getAsMention()).replaceAll("@user",gMember.getAsMention());
                                            text=text.replaceAll("!User",gMember.getAsMention()).replaceAll("!user",gMember.getAsMention());
                                            logger.info(fName+".text after="+text);
                                            embedBuilder.setDescription(text);
                                        }
                                    }
                                }else {
                                    logger.warn(fName + ".No image found");
                                    embedBuilder.setDescription("No image found");
                                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                                }
                                logger.info(fName+".post message");
                                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e); logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doInkbunnyHtmlCheckup() {
            String fName = "[doInkbunnyHtmlCheckup]";
            try {
                Unirest a= new Unirest();
                //a.config().verifySsl(false);
                //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
                String url="https://inkbunny.net/api_submissions.php?sid=!sid&submission_ids=1962041";
                if(gGlobal.jsonObject.has("InkBunny-ImageSearcher_sid")){
                    gInkBunnySIDUser =gGlobal.jsonObject.getString("InkBunny-ImageSearcher_sid");
                    logger.info(fName+".replace sid with ="+ gInkBunnySIDUser);
                }
                logger.info(fName+".sid ="+ gInkBunnySIDUser);
                url=url.replaceAll("!sid", gInkBunnySIDUser);
                logger.info(fName+".url ="+url);
                HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>200){
                    logger.error(fName+".invalid status"); return;
                }
                JsonNode body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                JSONObject rdata=body.getObject();
                if(rdata.has("error_code")) {
                    logger.info(fName+".error_code ="+rdata.getInt("error_code"));
                    if (rdata.getInt("error_code") == 0 || rdata.getInt("error_code") == 1 || rdata.getInt("error_code") == 2) {
                        doInkbunnyHtmlLogIn();
                    }
                }

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                doInkbunnyHtmlLogIn();
            }
        }
        private void doInkbunnyHtmlLogIn() {
            String fName = "[doInkbunnyHtmlLogIn]";
            try {
                Unirest a= new Unirest();
                //a.config().verifySsl(false);
                //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
                String url="https://inkbunny.net/api_login.php?username=18rjc&password=burkus@Ink10";
                HttpResponse<JsonNode> jsonResponse =a.get(url).asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>200){
                    logger.error(fName+".invalid status"); return;
                }
                JsonNode body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                JSONObject rdata=body.getObject();
                if(rdata.has("sid")) {
                    logger.info(fName+"sid="+rdata.getString("sid"));
                    gInkBunnySIDUser =rdata.getString("sid");
                    gGlobal.jsonObject.put("InkBunny-ImageSearcher_sid", gInkBunnySIDUser);
                }

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error: "+e,llColorRed);
            }
        }
        String keyIBSubmissionId="submission_id",keyIBTitle="title",keyIBPublic="public",keyIBMimeType="mimetype",keyIBGuestBlock="guest_block";
        String keyIBKeywords="keywords",keyIBKeywordName="keyword_name",keyIBHidden="hidden";
        String keyIBUserName="username",keyIBUserId="user_id",keyIBUserIconUrlSmall="user_icon_url_small",keyIBUserIconUrlLarge="user_icon_url_large";
        String keyIBFileUrlFull="file_url_full",keyIBFileUrlScreen="file_url_screen",keyIBFileUrlPreview="file_url_preview";
        String keyIBRatingId="rating_id",keyIBRatingName="rating_name", keyIBRatings="ratings",keyIBName="name";
        String keyIBSubmissionTypeId="submission_type_id",keyIBTypeName="type_name";
        JSONObject listmimetype;
        String valueTrue="t",valueFalse="f";

        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBasicFeatureControl.setEnable(enable);
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long> list=gBasicFeatureControl.getAllowedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private boolean checkIFChannelsAreNSFW(List<TextChannel>textChannels) {
            String fName = "[checkIFChannelsAreNSFW]";
            try {
                logger.info(fName + "textChannels.size=" +textChannels.size());
                for(TextChannel textChannel:textChannels){
                    logger.info(fName + "textChannel.id=" +textChannel.getId()+" ,nsfw="+textChannel.isNSFW());
                    if(!textChannel.isNSFW()){
                        logger.info(fName + "not nsfw");
                        return false;
                    }
                }
                logger.info(fName + "default");
                return true;
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                return false;
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        if(!checkIFChannelsAreNSFW(textChannels)){
                            logger.warn(fName+"failed as not all are nsfw");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update as all channels are required to be NSFW!");
                            return;
                        }
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        if(!checkIFChannelsAreNSFW(textChannels)){
                            logger.warn(fName+"failed as not all are nsfw");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update as all channels are required to be NSFW!");
                            return;
                        }
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTitle(gTitle+" Options");
                embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBasicFeatureControl.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    help("main");return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
    }
}
