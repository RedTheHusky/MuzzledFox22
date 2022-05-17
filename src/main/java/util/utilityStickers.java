package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcGetMessageJson;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.sticker.*;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityStickers extends Command implements llGlobalHelper, llMessageHelper,llMemberHelper {
    String cName="[utilityGuildStickers]";
    lcGlobalHelper gGlobal;String gTitle="Stickers",gCommand="stickers";
    Logger logger = Logger.getLogger(getClass());
    public utilityStickers(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        this.name = "Stickers";
        this.help = "Allowing accessing guild stickers";
        this.aliases = new String[]{gCommand,"sticker"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityStickers(lcGlobalHelper g, ButtonClickEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;Guild gGuild;User gUser;TextChannel gTextChannel;
        Member gMember;private Message gMessage;
        ButtonClickEvent gButtonClick;
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
        public runLocal(ButtonClickEvent ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gButtonClick = ev;
            gUser = gButtonClick.getUser();gMember=gButtonClick.getMember();
            gGuild = gButtonClick.getGuild();
            gTextChannel =gButtonClick.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(gGuild!=null)logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            if(gTextChannel!=null)logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(fName);
            try {
                if(gButtonClick!=null){
                    prefix2=gButtonClick.getJDA().getSelfUser().getAsMention();
                    String id=gButtonClick.getButton().getId();
                    if(id==null)id="";
                    logger.info(fName+"id="+id);
                    if(id.startsWith("stickerPack")){
                        infoPackage(id);
                    }
                }else{
                    prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                    boolean isInvalidCommand = true;
                    if(gCommandEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        String []items = gCommandEvent.getArgs().split("\\s+");
                        switch (items[0].toLowerCase()){
                            case "help":
                                help( "main");isInvalidCommand=false;
                                break;
                            case "info":
                                info();isInvalidCommand=false;
                                break;
                            case "list":
                                list();isInvalidCommand=false;
                                break;
                            case "download":
                                download();isInvalidCommand=false;
                                break;
                            case "message": case "getmessage": case "get_message":
                                if(items.length>=4){
                                    getMessage(items[1],items[2],items[3]);
                                }
                                else if(items.length==3){
                                    getMessage("",items[1],items[2]);
                                }
                                else if(items.length==2){
                                    getMessage("","",items[1]);
                                }else{
                                    getMessage("","","");
                                }
                                isInvalidCommand=false;
                                break;
                            case "downloadmessage": case "download_message":
                                if(items.length>=4){
                                    downloadMessage(items[1],items[2],items[3]);
                                }
                                else if(items.length==3){
                                    downloadMessage("",items[1],items[2]);
                                }
                                else if(items.length==2){
                                    downloadMessage("","",items[1]);
                                }else{
                                    downloadMessage("","","");
                                }
                                isInvalidCommand=false;
                                break;
                            case "id": case "getid": case "get_id":
                                if(items.length==2){
                                    getById(items[1]);
                                }else{
                                    getById("");
                                }
                                isInvalidCommand=false;
                                break;
                            case "downloadid": case "download_id":
                                if(items.length==2){
                                    downloadById(items[1]);
                                }else{
                                    downloadById("");
                                }
                                isInvalidCommand=false;
                                break;
                        }
                    }
                    logger.info(fName+".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }

        }
        String prefix2="";
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            EmbedBuilder embed= new EmbedBuilder();
            String quickSummonWithSpace=prefix2+gCommand+" ";
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.addField("Info","`"+quickSummonWithSpace+"info` to get stickers information of guild.",false);
            embed.addField("List","`"+quickSummonWithSpace+"list` to list stickers of guild.",false);
            embed.addField("Message","`"+quickSummonWithSpace+"message <opt guild_id> <opt channel_id> <message_id>` to get post sticker info. If no channel is mentioned, it used the current one.",false);
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember))embed.addField("Download","`"+quickSummonWithSpace+"download` to download stickers from guild.",false);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        lcGuildStickerPack guildStickerPack4Listing =new lcGuildStickerPack();
        private void info() {
            String fName = "[info]";
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
                guildStickerPack4Listing =new lcGuildStickerPack(gGuild);
                if(guildStickerPack4Listing.isEmpty()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("No stickers found!");
                    llSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                int size= guildStickerPack4Listing.size();
                embedBuilder.addField("Size", String.valueOf(size),false);
                embedBuilder.addField("PNG", String.valueOf(guildStickerPack4Listing.getCountOfPng()),true);
                embedBuilder.addField("APNG", String.valueOf(guildStickerPack4Listing.getCountOfAPng()),true);
                embedBuilder.addField("Lottie", String.valueOf(guildStickerPack4Listing.getCountOfLottie()),true);
                llSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void list() {
            String fName = "[list]";
            try{
                guildStickerPack4Listing =new lcGuildStickerPack(gGuild);
                if(guildStickerPack4Listing.isEmpty()){
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle);
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("No stickers found!");
                    llSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                list(0);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void list(int index) {
            String fName = "[list]";
            try{
                logger.info(fName+".index="+index);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);
                int size= guildStickerPack4Listing.size();
                String sticker_name="",sticker_id="",sticker_author="",sticker_tag="",sticker_desc="",sticker_media="";
                lcGuildSticker sticker= guildStickerPack4Listing.getSticker(index);
                if(sticker==null){
                    embedBuilder.setDescription("Invalid at index "+index+"!");embedBuilder.setColor(llColorRed_Carmine);
                }else{
                    embedBuilder.setColor(llColorBlue1);
                    sticker_name=sticker.getName();
                    sticker_desc=sticker.getDescription();
                    sticker_author=sticker.getAuthorId();
                    sticker_tag=sticker.getTag();
                    if(sticker_name!=null&&!sticker_name.isBlank()){
                        embedBuilder.setTitle(sticker_name);
                    }
                    if(!sticker.isImg()){
                        if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE) embedBuilder.addField("Lottie","This format can't be displayed!",false);
                        else embedBuilder.addField("Error","This format can't be displayed!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }else{
                        sticker_media=sticker.getMediaUrl();
                        if(sticker_media!=null&&!sticker_media.isBlank()){
                            embedBuilder.setImage(sticker_media);
                        }
                    }
                    if(sticker_tag!=null&&!sticker_tag.isBlank()){
                        embedBuilder.addField("Tag",":"+sticker_tag+":",false);
                    }
                    switch (sticker.getFormatType()){
                        case PNG: embedBuilder.addField("Type","png",false);break;
                        case APNG: embedBuilder.addField("Type","apng",false);break;
                        case LOTTIE: embedBuilder.addField("Type","lottie",false);break;
                    }
                    if(!sticker.isAvailable()){
                        embedBuilder.addField("Available","Is not available!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }
                    if(sticker_author!=null&&!sticker_author.isBlank()){
                        User user=sticker.getAuthor();
                        if(user==null)user=lsUserHelper.lsGetUserById(gGlobal.getJDAList(),sticker.getAuthorIdAsLong());
                        if(user!=null){
                            embedBuilder.addField("Author",user.getAsMention(),false);
                        }else{
                            embedBuilder.addField("Author","<@"+sticker_author+">",false);
                        }
                    }
                    if(sticker_desc!=null&&!sticker_desc.isBlank()){
                        embedBuilder.setDescription(sticker_desc);
                    }
                }

                Message message=llSendMessageResponse(gTextChannel,embedBuilder);
                if(index>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(index<size-1){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                listListen(message,index);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        int intDefaultMinutes=15;
        private void listListen(Message message,int index) {
            String fName = "[listListen]";
            try{
                logger.info(fName+".message="+message.getId()+", index="+index);
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e->(!e.getUser().isBot()&&e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    list(index-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    list(index+1);
                                }
                                else if(!name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare))){
                                    listListen(message,index);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void download() {
            String fName = "[download]";
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);
                if(!lsMemberIsBotOwner(gMember)&&!llMemberIsAdministrator(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Denied! Need to be guild admin or server manager.");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                guildStickerPack4Listing =new lcGuildStickerPack(gGuild);
                if(guildStickerPack4Listing.isEmpty()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("No stickers found!");
                    llSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                embedBuilder.setColor(llColorBlue1);embedBuilder.setDescription("Processing");
                Message message=llSendMessageResponse(gTextChannel,embedBuilder);
                InputStream inputStream= guildStickerPack4Listing.getZipAsInputStream();
                llMessageDelete(message);
                gTextChannel.sendMessage("Sent file").addFile(inputStream,"stickers.zip").complete();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
        String gPathption4StickerGet ="resources/json/utility/stickers/option4StickerGet.json";
        private void getMessage(String guild_id,String channel_id,String message_id) {
            String fName = "[getMessage]";
            try{
                logger.info("guild_id="+guild_id+", channel_id="+channel_id+", message_id="+message_id);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                embedBuilder.setTitle(gTitle);
                Guild guild=null;
                if(guild_id!=null&&!guild_id.isBlank()){
                    guild=gGlobal.getGuild(guild_id);
                    if(guild==null){
                        embedBuilder.setColor(llColorRed_Carmine);
                        embedBuilder.setDescription("Invalid guild id `"+guild_id+"`!");
                        llSendMessage(gUser,embedBuilder);
                        return;
                    }
                }
                TextChannel textChannel=null;PrivateChannel privateChannel=null;
                if(channel_id!=null&&!channel_id.isBlank()){
                    if(guild!=null){
                        textChannel= lsChannelHelper.lsGetTextChannelById(guild,channel_id);
                    }else{
                        textChannel= lsChannelHelper.lsGetTextChannelById(gGlobal.getJDAList(),channel_id);
                    }
                    if(textChannel==null){
                        privateChannel=lsChannelHelper.lsGetPrivateChannelById(gGlobal.getJDAList(),channel_id);
                        if(privateChannel==null){
                            embedBuilder.setColor(llColorRed_Carmine);
                            embedBuilder.setDescription("Invalid channel id `"+channel_id+"`!");
                            llSendMessage(gUser,embedBuilder);
                            return;
                        }
                    }
                }
                if(message_id==null||message_id.isBlank()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("No message_id provided!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                Message message=null;
                if(textChannel!=null){
                    message=lsMessageHelper.lsGetMessageById(textChannel,message_id);
                }else
                if(privateChannel!=null){
                    message=lsMessageHelper.lsGetMessageById(privateChannel,message_id);
                }else{
                    message=lsMessageHelper.lsGetMessageById(gTextChannel,message_id);
                }

                if(message==null){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Invalid message id `"+message_id+"`!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                lcGetMessageJson messageJson=new lcGetMessageJson(message);
                if(messageJson.isStickerItemsEmpty()||messageJson.getStickerItemsJson().isEmpty()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Message `"+message_id+"` has no stickers!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                JSONObject jsonObject= messageJson.getStickerItemsJson().getJSONObject(0);
                logger.info("jsonObject="+jsonObject.toString());
                if(!jsonObject.has(llCommonKeys.keyId)){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Message `"+message_id+"` has no stickers!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                lcSticker sticker=new lcSticker(Long.parseLong(jsonObject.getString(llCommonKeys.keyId)),gGlobal.getJDAList());
                lcGuildSticker guildSticker=new lcGuildSticker();
                lcStandardSticker standardSticker=new lcStandardSticker();
                embedBuilder.setColor(llColorBlue1);
                String sticker_name="",sticker_author="",sticker_tag="",sticker_tags="",sticker_desc="",sticker_media="",sticker_guild="",sticker_package="";
                boolean sticker_fromGuild=false,sticker_available=false;
                if(sticker.isEmpty()){
                    embedBuilder.setDescription("Invalid sticker!");embedBuilder.setColor(llColorRed_Carmine);
                }else{
                    embedBuilder.setColor(llColorBlue1);
                    embedBuilder.addField("Id",sticker.getId(),false);
                    sticker_name=sticker.getName();
                    sticker_desc=sticker.getDescription();
                    sticker_fromGuild=sticker.isFromGuild();
                    if(sticker_fromGuild){
                        guildSticker=sticker.getGuildSticker();
                        if(guildSticker==null)guildSticker=new lcGuildSticker();
                        sticker_author=guildSticker.getAuthorId();
                        sticker_guild=guildSticker.getGuildId();
                        sticker_tag=guildSticker.getTag();
                        sticker_available=guildSticker.isAvailable();
                    }else{
                        standardSticker=sticker.getNitroSticker();
                        if(standardSticker==null)standardSticker=new lcStandardSticker();
                        sticker_tags=standardSticker.getTagsAsJson().toString();
                    }

                    if(sticker_name!=null&&!sticker_name.isBlank()){
                        embedBuilder.setTitle(sticker_name);
                    }
                    switch (sticker.getFormatType()){
                        case PNG: embedBuilder.addField("Type","png",false);break;
                        case APNG: embedBuilder.addField("Type","apng",false);break;
                        case LOTTIE: embedBuilder.addField("Type","lottie",false);break;
                    }
                    if(sticker_tag!=null&&!sticker_tag.isBlank()){
                        embedBuilder.addField("Tag",":"+sticker_tag+":",false);
                    }else
                    if(sticker_tags!=null&&!sticker_tags.isBlank()){
                        embedBuilder.addField("Tags",sticker_tags,false);
                    }
                    if(!sticker_fromGuild){
                        embedBuilder.addField("Available","Only for nitro users.",false);
                        embedBuilder.setColor(llColorPink2);
                    }else
                    if(!sticker_available){
                        embedBuilder.addField("Available","Is not available!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }
                    if(sticker_fromGuild){
                        if(sticker_author!=null&&!sticker_author.isBlank()){
                            User user=guildSticker.getAuthor();
                            if(user==null)user=lsUserHelper.lsGetUserById(gGlobal.getJDAList(),guildSticker.getAuthorIdAsLong());
                            if(user!=null){
                                embedBuilder.addField("Author",user.getAsMention(),false);
                            }else{
                                embedBuilder.addField("Author","<@"+sticker_author+">",false);
                            }
                        }
                        if(sticker_guild!=null&&!sticker_guild.isBlank()){
                            Guild guild1=guildSticker.getGuild();
                            if(guild1==null)guild1=lsGuildHelper.getGuild(gGlobal.getJDAList(),guildSticker.getAuthorIdAsLong());
                            if(guild1!=null){
                                embedBuilder.addField("Guild",guild1.getName(),false);
                            }else{
                                embedBuilder.addField("Guild","("+sticker_guild+")",false);
                            }
                        }
                    }else{
                        lcStandardStickerPack standardstickerpack=standardSticker.getStickerPack();
                        if(standardstickerpack==null)standardstickerpack=new lcStandardStickerPack();
                        sticker_package=standardstickerpack.getName();
                        if(sticker_package!=null&&!sticker_package.isBlank()){
                            embedBuilder.addField("Package",sticker_package,false);
                        }
                    }
                    if(!sticker.isImg()){
                        if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE) embedBuilder.addField("Lottie","This format can't be displayed!",false);
                        else embedBuilder.addField("Error","This format can't be displayed!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }else{
                        sticker_media=sticker.getMediaUrl128();
                        if(sticker_media!=null&&!sticker_media.isBlank()){
                            embedBuilder.setImage(sticker_media);
                        }
                        desc=lsUsefullFunctions.getUrlTextString("32",sticker.getMediaUrl32());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("64",sticker.getMediaUrl64());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("128",sticker.getMediaUrl128());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("256",sticker.getMediaUrl256());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("512",sticker.getMediaUrl512());
                        embedBuilder.addField("Links",desc,false);
                        if(sticker_desc!=null&&!sticker_desc.isBlank()){
                            embedBuilder.setDescription(sticker_desc);
                        }
                    }
                }
                messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                messageComponentManager.loadMessageComponents(gPathption4StickerGet);
                try {
                    logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
                    if(sticker_fromGuild)component0.getButtonAt4(0).setCustomId("stickerPack,2,"+sticker_guild);
                    else component0.getButtonAt4(0).setCustomId("stickerPack,1,"+standardSticker.getPackId());
                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                    gTextChannel.sendMessageEmbeds(embedBuilder.build()).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    llSendMessageResponse(gTextChannel,embedBuilder);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void getById(String id) {
            String fName = "[getById]";
            try{
                logger.info("id="+id);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                embedBuilder.setTitle(gTitle);
                if(id==null||id.isBlank()){
                    embedBuilder.setDescription("No id provided!");embedBuilder.setColor(llColorRed_Carmine);
                    llSendMessageResponse(gTextChannel,embedBuilder);
                    return;
                }
                lcSticker sticker=new lcSticker(Long.parseLong(id),gGlobal.getJDAList());
                lcGuildSticker guildSticker=new lcGuildSticker();
                lcStandardSticker standardSticker=new lcStandardSticker();
                embedBuilder.setColor(llColorBlue1);
                String sticker_name="",sticker_author="",sticker_tag="",sticker_tags="",sticker_desc="",sticker_media="",sticker_guild="",sticker_package="";
                boolean sticker_fromGuild=false,sticker_available=false;
                if(sticker.isEmpty()){
                    embedBuilder.setDescription("Invalid sticker!");embedBuilder.setColor(llColorRed_Carmine);
                }else{
                    embedBuilder.setColor(llColorBlue1);
                    embedBuilder.addField("Id",sticker.getId(),false);
                    sticker_name=sticker.getName();
                    sticker_desc=sticker.getDescription();
                    sticker_fromGuild=sticker.isFromGuild();
                    if(sticker_fromGuild){
                        guildSticker=sticker.getGuildSticker();
                        if(guildSticker==null)guildSticker=new lcGuildSticker();
                        sticker_author=guildSticker.getAuthorId();
                        sticker_guild=guildSticker.getGuildId();
                        sticker_tag=guildSticker.getTag();
                        sticker_available=guildSticker.isAvailable();
                    }else{
                        standardSticker=sticker.getNitroSticker();
                        if(standardSticker==null)standardSticker=new lcStandardSticker();
                        sticker_tags=standardSticker.getTagsAsJson().toString();
                    }

                    if(sticker_name!=null&&!sticker_name.isBlank()){
                        embedBuilder.setTitle(sticker_name);
                    }
                    switch (sticker.getFormatType()){
                        case PNG: embedBuilder.addField("Type","png",false);break;
                        case APNG: embedBuilder.addField("Type","apng",false);break;
                        case LOTTIE: embedBuilder.addField("Type","lottie",false);break;
                    }
                    if(sticker_tag!=null&&!sticker_tag.isBlank()){
                        embedBuilder.addField("Tag",":"+sticker_tag+":",false);
                    }else
                    if(sticker_tags!=null&&!sticker_tags.isBlank()){
                        embedBuilder.addField("Tags",sticker_tags,false);
                    }
                    if(!sticker_fromGuild){
                        embedBuilder.addField("Available","Only for nitro users.",false);
                        embedBuilder.setColor(llColorPink2);
                    }else
                    if(!sticker_available){
                        embedBuilder.addField("Available","Is not available!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }
                    if(sticker_fromGuild){
                        if(sticker_author!=null&&!sticker_author.isBlank()){
                            User user=guildSticker.getAuthor();
                            if(user==null)user=lsUserHelper.lsGetUserById(gGlobal.getJDAList(),guildSticker.getAuthorIdAsLong());
                            if(user!=null){
                                embedBuilder.addField("Author",user.getAsMention(),false);
                            }else{
                                embedBuilder.addField("Author","<@"+sticker_author+">",false);
                            }
                        }
                        if(sticker_guild!=null&&!sticker_guild.isBlank()){
                            Guild guild1=guildSticker.getGuild();
                            if(guild1==null)guild1=lsGuildHelper.getGuild(gGlobal.getJDAList(),guildSticker.getAuthorIdAsLong());
                            if(guild1!=null){
                                embedBuilder.addField("Guild",guild1.getName(),false);
                            }else{
                                embedBuilder.addField("Guild","("+sticker_guild+")",false);
                            }
                        }
                    }else{
                        lcStandardStickerPack standardstickerpack=standardSticker.getStickerPack();
                        if(standardstickerpack==null)standardstickerpack=new lcStandardStickerPack();
                        sticker_package=standardstickerpack.getName();
                        if(sticker_package!=null&&!sticker_package.isBlank()){
                            embedBuilder.addField("Package",sticker_package,false);
                        }
                    }
                    if(!sticker.isImg()){
                        if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE) embedBuilder.addField("Lottie","This format can't be displayed!",false);
                        else embedBuilder.addField("Error","This format can't be displayed!",false);
                        embedBuilder.setColor(llColorOrange_webcolor);
                    }else{
                        sticker_media=sticker.getMediaUrl128();
                        if(sticker_media!=null&&!sticker_media.isBlank()){
                            embedBuilder.setImage(sticker_media);
                        }
                        desc=lsUsefullFunctions.getUrlTextString("32",sticker.getMediaUrl32());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("64",sticker.getMediaUrl64());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("128",sticker.getMediaUrl128());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("256",sticker.getMediaUrl256());
                        desc+=", "+lsUsefullFunctions.getUrlTextString("512",sticker.getMediaUrl512());
                        embedBuilder.addField("Links",desc,false);
                        if(sticker_desc!=null&&!sticker_desc.isBlank()){
                            embedBuilder.setDescription(sticker_desc);
                        }
                    }
                }
                messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                messageComponentManager.loadMessageComponents(gPathption4StickerGet);
                try {
                    logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
                    if(sticker_fromGuild)component0.getButtonAt4(0).setCustomId("stickerPack,2,"+sticker_guild);
                    else component0.getButtonAt4(0).setCustomId("stickerPack,1,"+standardSticker.getPackId());
                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                    gTextChannel.sendMessageEmbeds(embedBuilder.build()).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    llSendMessageResponse(gTextChannel,embedBuilder);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void downloadMessage(String guild_id,String channel_id,String message_id) {
            String fName = "[downloadMessage]";
            try{
                logger.info("guild_id="+guild_id+", channel_id="+channel_id+", message_id="+message_id);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                embedBuilder.setTitle(gTitle);
                Guild guild=null;
                if(guild_id!=null&&!guild_id.isBlank()){
                    guild=gGlobal.getGuild(guild_id);
                    if(guild==null){
                        embedBuilder.setColor(llColorRed_Carmine);
                        embedBuilder.setDescription("Invalid guild id `"+guild_id+"`!");
                        llSendMessage(gUser,embedBuilder);
                        return;
                    }
                }
                TextChannel textChannel=null;PrivateChannel privateChannel=null;
                if(channel_id!=null&&!channel_id.isBlank()){
                    if(guild!=null){
                        textChannel= lsChannelHelper.lsGetTextChannelById(guild,channel_id);
                    }else{
                        textChannel= lsChannelHelper.lsGetTextChannelById(gGlobal.getJDAList(),channel_id);
                    }
                    if(textChannel==null){
                        privateChannel=lsChannelHelper.lsGetPrivateChannelById(gGlobal.getJDAList(),channel_id);
                        if(privateChannel==null){
                            embedBuilder.setColor(llColorRed_Carmine);
                            embedBuilder.setDescription("Invalid channel id `"+channel_id+"`!");
                            llSendMessage(gUser,embedBuilder);
                            return;
                        }
                    }
                }
                if(message_id==null||message_id.isBlank()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("No message_id provided!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                Message message=null;
                if(textChannel!=null){
                    message=lsMessageHelper.lsGetMessageById(textChannel,message_id);
                }else
                if(privateChannel!=null){
                    message=lsMessageHelper.lsGetMessageById(privateChannel,message_id);
                }else{
                    message=lsMessageHelper.lsGetMessageById(gTextChannel,message_id);
                }

                if(message==null){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Invalid message id `"+message_id+"`!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                lcGetMessageJson messageJson=new lcGetMessageJson(message);
                if(messageJson.isStickerItemsEmpty()||messageJson.getStickerItemsJson().isEmpty()){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Message `"+message_id+"` has no stickers!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                JSONObject jsonObject= messageJson.getStickerItemsJson().getJSONObject(0);
                logger.info("jsonObject="+jsonObject.toString());
                if(!jsonObject.has(llCommonKeys.keyId)){
                    embedBuilder.setColor(llColorRed_Carmine);
                    embedBuilder.setDescription("Message `"+message_id+"` has no stickers!");
                    llSendMessage(gUser,embedBuilder);
                    return;
                }
                lcGuildSticker sticker=new lcGuildSticker(Long.parseLong(jsonObject.getString(llCommonKeys.keyId)),gGlobal.getJDAList());
                embedBuilder.setColor(llColorBlue1);
                if(sticker.isEmpty()){
                    embedBuilder.setDescription("Invalid sticker!");embedBuilder.setColor(llColorRed_Carmine);
                    llSendMessageResponse(gTextChannel,embedBuilder);
                    return;
                }
                gTextChannel.sendMessage("Here is the sticker....").addFile(sticker.getZipAsInputStream(),sticker.getId()+".zip").complete();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void downloadById(String id) {
            String fName = "[downloadById]";
            try{
                logger.info("id="+id);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                embedBuilder.setTitle(gTitle);
                if(id==null||id.isBlank()){
                    embedBuilder.setDescription("No id provided!");embedBuilder.setColor(llColorRed_Carmine);
                    llSendMessageResponse(gTextChannel,embedBuilder);
                    return;
                }
                lcGuildSticker sticker=new lcGuildSticker(Long.parseLong(id),gGlobal.getJDAList());
                embedBuilder.setColor(llColorBlue1);
                String sticker_name="",sticker_author="",sticker_tag="",sticker_desc="",sticker_media="",sticker_guild="";
                boolean sticker_available=false;
                if(sticker.isEmpty()){
                    embedBuilder.setDescription("Invalid sticker!");embedBuilder.setColor(llColorRed_Carmine);
                    llSendMessageResponse(gTextChannel,embedBuilder);
                    return;
                }
                gTextChannel.sendMessage("Here is the sticker....").addFile(sticker.getZipAsInputStream(),sticker.getId()+".zip").complete();

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        InteractionHook intergactionHook;
        lcStandardStickerPacks standardStickerPacks4Listing=new lcStandardStickerPacks(false);
        lcStandardStickerPack standardStickerPack4Listing=new lcStandardStickerPack();
        private void infoPackage(String value) {
            String fName = "[infoPackage]";
            try{
                String []items = value.split(",");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                embedBuilder.setTitle("Sticker Pack");embedBuilder.setColor(llColorBlue1);
                logger.info(fName+".items.length=" + items.length);
                if(items.length!=3){
                    logger.warn(fName+".invalid length");
                    embedBuilder.setDescription("Invalid command!");embedBuilder.setColor(llColorRed_Carmine);
                    if(gButtonClick!=null){
                        intergactionHook=gButtonClick.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    }
                    return;
                }
                switch (items[1]){
                    case "1":
                        standardStickerPacks4Listing.getHttps();
                        standardStickerPack4Listing=standardStickerPacks4Listing.getStickerPack(items[2]);
                        if(standardStickerPack4Listing==null)standardStickerPack4Listing=new lcStandardStickerPack();
                        break;
                    case "2":
                        guildStickerPack4Listing=new lcGuildStickerPack(items[2],gGlobal.getJDAList());
                        break;
                    default:
                        logger.warn(fName+".invalid type");
                        embedBuilder.setDescription("Invalid command!");embedBuilder.setColor(llColorRed_Carmine);
                        if(gButtonClick!=null){
                            intergactionHook=gButtonClick.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        }
                        return;
                }
                String packName="",packCount="",packDescription="",packBanner="",packIcon="";
                switch (items[1]){
                    case "1":
                        packName=standardStickerPack4Listing.getName();
                        packCount=String.valueOf(standardStickerPack4Listing.size());
                        packDescription=standardStickerPack4Listing.getDescription();
                        packBanner=standardStickerPack4Listing.getBannerUrl()+"?size=256";
                        lcStandardSticker coverSticker1=standardStickerPack4Listing.getCoverSticker();
                        if(coverSticker1!=null&&(coverSticker1.getFormatType()== MessageSticker.StickerFormat.PNG||coverSticker1.getFormatType()== MessageSticker.StickerFormat.APNG)){
                            packIcon=coverSticker1.getMediaUrl64();
                        }
                        break;
                    case "2":
                        packName=lsGuildHelper.getGuildName(guildStickerPack4Listing.getGuild());
                        packCount=String.valueOf(guildStickerPack4Listing.size());
                        packBanner=lsGuildHelper.getGuildBannerUrl(guildStickerPack4Listing.getGuild())+"?size=256";
                        packIcon=lsGuildHelper.getGuildIconUrl(guildStickerPack4Listing.getGuild());
                        if(packName==null)packName=lsGuildHelper.getGuildIdAsString(guildStickerPack4Listing.getGuild());
                        packName+="'s server pack";
                        break;

                }
                if(packName!=null&&!packName.isBlank()){
                    embedBuilder.addField("Name",packName,false);
                }
                switch (items[1]){
                    case "1":
                        embedBuilder.addField("Type","Is a Nitro sticker pack",false);
                        embedBuilder.setColor(llColorPink2);
                        break;
                    case "2":
                        embedBuilder.addField("Type","Is a guild sticker pack",false);
                        break;
                }
                if(!packCount.isBlank()){
                    embedBuilder.addField("Count",packCount,false);
                }
                if(packDescription!=null&&!packDescription.isBlank()){
                    embedBuilder.addField("Description",packDescription,false);
                }
                if(packIcon!=null&&!packIcon.isBlank()){
                    try {
                        logger.info(fName+". packIcon="+ packIcon);
                        URL url = new URL( packIcon);
                        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
                        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                        int code=httpcon.getResponseCode();
                        logger.info(fName+".code="+ code);
                        if(200<=code&&code<=299){
                            embedBuilder.setThumbnail(packIcon);
                        }
                    }catch (Exception e){
                        logger.error(fName+".exception=" + e);
                        logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(packBanner!=null&&!packBanner.isBlank()){
                    try {
                        logger.info(fName+".packBanner="+ packBanner);
                        URL url = new URL(packBanner);
                        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
                        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                        int code=httpcon.getResponseCode();
                        logger.info(fName+".code="+ code);
                        if(200<=code&&code<=299){
                            embedBuilder.setImage(packBanner);
                        }
                    }catch (Exception e){
                        logger.error(fName+".exception=" + e);
                        logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(gButtonClick!=null){
                    intergactionHook=gButtonClick.replyEmbeds(embedBuilder.build()).complete();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }


    
    
    
    
    
    
    
  //runLocal  
    }
}
