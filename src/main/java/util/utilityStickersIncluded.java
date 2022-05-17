package util;
//implemented Runnable

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.lcText2Json;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsStreamHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityStickersIncluded extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[utilityStickersIncluded]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public utilityStickersIncluded(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        this.name = "Utility-StickersIncluded";
        this.help = "Allowing using image as stickers like telegram.";
        this.aliases = new String[]{"stickerincluded","stickersincluded2"};
        this.guildOnly = true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;Guild gGuild;User gUser;TextChannel gTextChannel; String gTitle="Stickers";
        Member gMember;private Message gMessage;
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
            try {
                boolean isInvalidCommand = true;
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    String []items = gCommandEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")){
                        help( "main");isInvalidCommand=false;
                    }else{
                        readDefaultFile();
                        loadedProfile();
                        getValues();
                        if(valueStickers.isEmpty()){
                            readFile();
                        }
                        if(items[0].equalsIgnoreCase("info")){
                            info();isInvalidCommand=false;
                        }else
                        if(items[0].equalsIgnoreCase("read")){
                            read();isInvalidCommand=false;
                        }else
                        if(items[0].equalsIgnoreCase("set")){
                            if(items.length>=2){
                                set(items[1]);
                            }else{
                                set("");
                            }
                            isInvalidCommand=false;
                        }else
                        if(items[0].startsWith(":")||items[0].startsWith("-")){
                            doActionPostSticker(items[0]);isInvalidCommand=false;
                        }else{
                            doActionPostSticker(items[0]);isInvalidCommand=false;
                        }
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
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            EmbedBuilder embed= new EmbedBuilder();
            String quickSummonWithSpace=llPrefixStr+"stickers ";
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.addField("Posting","`"+quickSummonWithSpace+" :name`"+", `"+quickSummonWithSpace+" -package-name`",false);
            embed.addField("Info","`"+quickSummonWithSpace+" info` Info about source file and the sticker entries.",false);
            embed.addField("Read","`"+quickSummonWithSpace+" read` reading the source file for the sticker images.",false);
            embed.addField("Set","`"+quickSummonWithSpace+" set <url>` setting a new source file for the sticker images. Can only use jpg, png and gif files. ",false);
            embed.addField("Structure of source file","Text file should contain array&json as such:\n[{\n\t\"name\":\"name of entity\", \n\t\"src\":\"source image jpg,png,gif\", \n\t\""+keyAllowedRoles+"\":[id of roles], \n\t\""+keyBlockedRoles+"\":[id of roles], \n\t\""+keyAllowedUsers+"\":[id of users], \n\t\""+keyBlockedUsers+"\":[id of users]}\n,{...]",false);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        private void info() {
            String fName="info]";
            logger.info(fName);
            try {
               EmbedBuilder embedBuilder=new EmbedBuilder();
               embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorBlue1);
               embedBuilder.addField("Source",valueSourceFile,false);
               embedBuilder.addField("Stickers", String.valueOf(valueStickers.length()),false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void read() {
            String fName="[read]";
            logger.info(fName);
            try {
                if(valueSourceFile.isBlank()&&valueSourceFile.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No source file set.",llColorRed_Barn);return;
                }
               if(readFile()){
                   llSendQuickEmbedMessage(gTextChannel,gTitle,"Successfully read "+valueSourceFile+".",llColorGreen2);
               }else{
                   llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to read "+valueSourceFile+".",llColorRed_Barn);
               }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void set(String newAddress) {
            String fName="[set]";
            logger.info(fName);
            try {
                if(!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Denied access!",llColorRed_Barn);return;
                }
                if(newAddress.isBlank()&&newAddress.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No source file set.",llColorRed_Barn);return;
                }
                valueSourceFile=newAddress;
                gProfile.putFieldEntry(keySourceFile,valueSourceFile);
                saveProfile();
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Source file set to "+valueSourceFile+".",llColorBlue1);
                if(readFile()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Successfully read "+valueSourceFile+".",llColorGreen2);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to read "+valueSourceFile+".",llColorRed_Barn);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void doActionPostSticker(String name){
            String fName = "[doActionPostSticker]";
            logger.info(fName);
            try {
                logger.info(fName + ".name.before="+name);
                boolean blockedByUser=false, blockedByRole=false, allowedByRole=false,allowedByUser=false;
                boolean hasBlockUsers=false, hasBlockRoles=false, hasAllowRoles=false,hasAllowUsers=false;
                String userId=gMember.getId();
                if(gProfile.jsonObject.has(keyBlockedUsers)){
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyBlockedUsers);
                    for(int j=0;j<array.length();j++){
                        hasBlockUsers=true;
                        if(userId.equalsIgnoreCase(array.getString(j))){
                            blockedByUser=true;break;
                        }
                    }
                }
                if(gProfile.jsonObject.has(keyAllowedUsers)){
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyAllowedUsers);
                    for(int j=0;j<array.length();j++){
                        hasAllowUsers=true;
                        if(userId.equalsIgnoreCase(array.getString(j))){
                            allowedByUser=true;break;
                        }
                    }
                }
                if(gProfile.jsonObject.has(keyBlockedRoles)){
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyBlockedRoles);
                    for(int j=0;j<array.length();j++){
                        hasBlockRoles=true;
                        if(llMemberHasRole(gMember,array.getString(j))){
                            blockedByRole=true;break;
                        }
                    }
                }
                if(gProfile.jsonObject.has(keyAllowedRoles)){
                    JSONArray array=gProfile.jsonObject.getJSONArray(keyAllowedRoles);
                    for(int j=0;j<array.length();j++){
                        hasAllowRoles=true;
                        if(llMemberHasRole(gMember,array.getString(j))){
                            allowedByRole=true;break;
                        }
                    }
                }
                logger.info(fName + ". hasAllowRoles="+hasAllowRoles+", hasAllowUsers="+hasAllowUsers+", hasBlockRoles="+hasBlockRoles+", hasBlockUsers="+hasBlockUsers);
                logger.info(fName + ". allowedByRole="+allowedByRole+", allowedByUser="+allowedByUser+", blockedByRole="+blockedByRole+", blockedByUser="+blockedByUser);
                if(blockedByRole){
                    llSendQuickEmbedMessage(gUser,gTitle,"This command restricted for role you're associated!",llColorRed_Barn);return;
                }
                if(blockedByUser){
                    llSendQuickEmbedMessage(gUser,gTitle,"This command restricted for you!",llColorRed_Barn);return;
                }
                if(!allowedByRole&&!allowedByUser){
                    if(hasAllowRoles){
                        llSendQuickEmbedMessage(gUser,gTitle,"This command restricted do to missing required role!",llColorRed_Barn);return;
                    }
                    if(hasAllowUsers){
                        llSendQuickEmbedMessage(gUser,gTitle,"This command restricted do to you're not on the list!",llColorRed_Barn);return;
                    }
                }
                if(name.startsWith(":")){
                    name=name.replace(":","");
                }else
                if(name.startsWith("-")){
                    name=name.replace("-","");
                }
                logger.info(fName + ".name.after="+name);
                boolean foundDefault=false,foundCustom=false;
                for(int i=0;i<valueStickersDefault.length();i++){
                    try{
                        JSONObject sticker=valueStickersDefault.getJSONObject(i);
                        if(sticker.has(keyName)&&sticker.has(keySrc)){
                            String stickerName=sticker.getString(keyName);
                            logger.info(fName + ".sticker["+i+"].name="+stickerName);
                            if(stickerName.equalsIgnoreCase(name)){
                                foundDefault=true;
                                logger.info(fName + ".sticker["+i+"]="+sticker.toString());
                                blockedByUser=false; blockedByRole=false; allowedByRole=false;allowedByUser=false;
                                hasBlockUsers=false; hasBlockRoles=false; hasAllowRoles=false;hasAllowUsers=false;
                                if(sticker.has(keyBlockedUsers)){
                                    JSONArray array=sticker.getJSONArray(keyBlockedUsers);
                                    for(int j=0;j<array.length();j++){
                                        hasBlockUsers=true;
                                        if(userId.equalsIgnoreCase(array.getString(j))){
                                            blockedByUser=true;break;
                                        }
                                    }
                                }
                                if(sticker.has(keyAllowedUsers)){
                                    JSONArray array=sticker.getJSONArray(keyAllowedUsers);
                                    for(int j=0;j<array.length();j++){
                                        hasAllowUsers=true;
                                        if(userId.equalsIgnoreCase(array.getString(j))){
                                            allowedByUser=true;break;
                                        }
                                    }
                                }
                                if(sticker.has(keyBlockedRoles)){
                                    JSONArray array=sticker.getJSONArray(keyBlockedRoles);
                                    for(int j=0;j<array.length();j++){
                                        hasBlockRoles=true;
                                        if(llMemberHasRole(gMember,array.getString(j))){
                                            blockedByRole=true;break;
                                        }
                                    }
                                }
                                if(sticker.has(keyAllowedRoles)){
                                    JSONArray array=sticker.getJSONArray(keyAllowedRoles);
                                    for(int j=0;j<array.length();j++){
                                        hasAllowRoles=true;
                                        if(llMemberHasRole(gMember,array.getString(j))){
                                            allowedByRole=true;break;
                                        }
                                    }
                                }
                                logger.info(fName + ". hasAllowRoles="+hasAllowRoles+", hasAllowUsers="+hasAllowUsers+", hasBlockRoles="+hasBlockRoles+", hasBlockUsers="+hasBlockUsers);
                                logger.info(fName + ". allowedByRole="+allowedByRole+", allowedByUser="+allowedByUser+", blockedByRole="+blockedByRole+", blockedByUser="+blockedByUser);
                                if(blockedByRole){
                                    logger.warn("denied");
                                }else
                                if(blockedByUser){
                                    logger.warn("denied");
                                }else
                                if(!allowedByRole&&!allowedByUser){
                                    if(hasAllowRoles){
                                        logger.warn("denied");
                                    }else
                                    if(hasAllowUsers){
                                        logger.warn("denied");
                                    }else{
                                        postWebhookImage(gMember, gTextChannel,sticker.getString(keySrc),stickerName);
                                    }
                                }
                            }
                        }else{
                            logger.info(fName + ".sticker["+i+"] invalid");
                        }
                    }catch (Exception e) {
                        logger.error(fName+"exception:"+e);
                    }
                }
                if(!foundDefault){
                    for(int i=0;i<valueStickers.length();i++){
                        try{
                            JSONObject sticker=valueStickers.getJSONObject(i);
                            if(sticker.has(keyName)&&sticker.has(keySrc)){
                                String stickerName=sticker.getString(keyName);
                                logger.info(fName + ".sticker["+i+"].name="+stickerName);
                                if(stickerName.equalsIgnoreCase(name)){
                                    foundCustom=true;
                                    logger.info(fName + ".sticker["+i+"]="+sticker.toString());
                                    blockedByUser=false; blockedByRole=false; allowedByRole=false;allowedByUser=false;
                                    hasBlockUsers=false; hasBlockRoles=false; hasAllowRoles=false;hasAllowUsers=false;
                                    if(sticker.has(keyBlockedUsers)){
                                        JSONArray array=sticker.getJSONArray(keyBlockedUsers);
                                        for(int j=0;j<array.length();j++){
                                            hasBlockUsers=true;
                                            if(userId.equalsIgnoreCase(array.getString(j))){
                                                blockedByUser=true;break;
                                            }
                                        }
                                    }
                                    if(sticker.has(keyAllowedUsers)){
                                        JSONArray array=sticker.getJSONArray(keyAllowedUsers);
                                        for(int j=0;j<array.length();j++){
                                            hasAllowUsers=true;
                                            if(userId.equalsIgnoreCase(array.getString(j))){
                                                allowedByUser=true;break;
                                            }
                                        }
                                    }
                                    if(sticker.has(keyBlockedRoles)){
                                        JSONArray array=sticker.getJSONArray(keyBlockedRoles);
                                        for(int j=0;j<array.length();j++){
                                            hasBlockRoles=true;
                                            if(llMemberHasRole(gMember,array.getString(j))){
                                                blockedByRole=true;break;
                                            }
                                        }
                                    }
                                    if(sticker.has(keyAllowedRoles)){
                                        JSONArray array=sticker.getJSONArray(keyAllowedRoles);
                                        for(int j=0;j<array.length();j++){
                                            hasAllowRoles=true;
                                            if(llMemberHasRole(gMember,array.getString(j))){
                                                allowedByRole=true;break;
                                            }
                                        }
                                    }
                                    logger.info(fName + ". hasAllowRoles="+hasAllowRoles+", hasAllowUsers="+hasAllowUsers+", hasBlockRoles="+hasBlockRoles+", hasBlockUsers="+hasBlockUsers);
                                    logger.info(fName + ". allowedByRole="+allowedByRole+", allowedByUser="+allowedByUser+", blockedByRole="+blockedByRole+", blockedByUser="+blockedByUser);
                                    if(blockedByRole){
                                        logger.warn("denied");
                                    }else
                                    if(blockedByUser){
                                        logger.warn("denied");
                                    }else
                                    if(!allowedByRole&&!allowedByUser){
                                        if(hasAllowRoles){
                                            logger.warn("denied");
                                        }else
                                        if(hasAllowUsers){
                                            logger.warn("denied");
                                        }else{
                                            postWebhookImage(gMember, gTextChannel,sticker.getString(keySrc),stickerName);
                                        }
                                    }
                                }
                            }else{
                                logger.info(fName + ".sticker["+i+"] invalid");
                            }
                        }catch (Exception e) {
                            logger.error(fName+"exception:"+e);
                        }
                    }
                }
                logger.info(fName + ".foundDefault="+foundDefault);
                logger.info(fName + ".foundCustom="+foundCustom);
                if(foundDefault||foundCustom){
                    if(blockedByRole){
                        llSendQuickEmbedMessage(gUser,gTitle,"This sticker is not available for role you're associated!",llColorRed_Barn);return;
                    }
                    if(blockedByUser){
                        llSendQuickEmbedMessage(gUser,gTitle,"This sticker is not available for you!",llColorRed_Barn);return;
                    }
                    if(!allowedByRole&&!allowedByUser){
                        if(hasAllowRoles){
                            llSendQuickEmbedMessage(gUser,gTitle,"This sticker is not available do to missing required role!",llColorRed_Barn);return;
                        }
                        if(hasAllowUsers){
                            llSendQuickEmbedMessage(gUser,gTitle,"This sticker is not available do to you're not on the list!",llColorRed_Barn);return;
                        }
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void postWebhookImage(Member member, TextChannel textChannel, String imageUrl, String stickername) {
            String fName = "[postWebhookMessage]";
            try {
                logger.info(fName + ".imageUrl="+imageUrl);
                logger.info(fName + ".i stickername="+stickername);
                lcWebHookBuild whh = new lcWebHookBuild();
                whh.doSafetyCleanwToken(textChannel);
                String name=member.getEffectiveName();
                String avatarUrl = member.getUser().getEffectiveAvatarUrl();
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("avatarurl", avatarUrl);
                logger.info(fName + ".send webhook");
                InputStream inputStream= null;
                if(imageUrl.startsWith("resources/")||imageUrl.startsWith("storage/")){
                    File file;
                    try {
                        file=new File(imageUrl);
                        if(file!=null){
                            inputStream = new FileInputStream(file);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }else{
                    inputStream= lsStreamHelper.llGetInputStream4WebFile(imageUrl);
                }
                if(inputStream!=null) {
                    String entryExtension="";
                    if(imageUrl.toLowerCase().endsWith(".png"))entryExtension=".png";
                    else if(imageUrl.toLowerCase().endsWith(".jpg"))entryExtension=".jpg";
                    else if(imageUrl.toLowerCase().endsWith(".gif"))entryExtension=".gif";
                    else {llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed); return;}
                    String namex=stickername+entryExtension;
                    logger.info(fName + ".name of file="+namex);
                    if (!whh.preBuild(gTextChannel, json)) {
                        logger.error(fName + "failed prebuild");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        return;
                    }
                    if (!whh.build()) {
                        logger.error(fName + "failed build");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        return;
                    }
                    if (!whh.clientOpen()) {
                        logger.error(fName + "failed client open");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        return;
                    }
                    ReadonlyMessage message=whh.sendReturnMessage(inputStream,namex);
                    long rMID=message.getId();
                    Thread.sleep(2000);
                    logger.info(fName + ".close and delete webhook");
                    whh.clientClose();
                    Thread.sleep(1000);
                    whh.delete();
                    gTextChannel.addReactionById(rMID,gGlobal.emojis.getEmoji("bomb")).complete();
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(String.valueOf(rMID))&&!e.getUser().isBot()),
                            e -> {
                                try {
                                    String nameReaction=e.getReactionEmote().getName();
                                    logger.warn(fName+"nameReaction="+nameReaction);
                                    if(nameReaction.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                        lsMessageHelper.lsMessageDelete(gTextChannel,rMID);
                                    }
                                }catch (Exception e2) {
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                            },2, TimeUnit.MINUTES, () -> {
                                lsMessageHelper.lsMessageClearReactionsQueue(gTextChannel,rMID);
                            });
                }
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        lcText2Json text2Json;
        String keyName="name", keySrc ="src", keyAllowedRoles="allowedRoles",keyBlockedRoles="blockedRoles",keyAllowedUsers="allowedusers",keyBlockedUsers="blockedUsers";
        private boolean readFile() {
            String fName="[readFile]";
            logger.info(fName);
            try {
                if(valueSourceFile.isBlank()&&valueSourceFile.isEmpty()){
                    logger.warn("No source file set.");return false;
                }
                InputStream is=lsStreamHelper.llGetInputStream4WebFile(valueSourceFile);
                text2Json=new lcText2Json();
                if(!text2Json.isInputStream2Json(is)){
                    logger.warn(fName+".failed to load");return false;
                }
                logger.info(fName+".loaded");
                if(text2Json.jsonArray.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName+"length="+text2Json.jsonArray.length());
                valueStickers=new JSONArray();

                for(int i=0;i<text2Json.jsonArray.length();i++){
                    try {
                        JSONObject sticker=new JSONObject();
                        sticker.put(keyName,""); sticker.put(keySrc,"");
                        sticker.put(keyAllowedRoles,new JSONArray());
                        sticker.put(keyBlockedRoles,new JSONArray());
                        sticker.put(keyAllowedUsers,new JSONArray());
                        sticker.put(keyBlockedUsers,new JSONArray());
                        logger.info(fName + ".index=" + i);
                        JSONObject item = text2Json.jsonArray.getJSONObject(i);
                        logger.info(fName + ".item=" + item.toString());
                        if (item.has(keyName) && !item.isNull(keyName)) {
                            sticker.put(keyName,item.getString(keyName));
                        }
                        if (item.has(keySrc) && !item.isNull(keySrc)) {
                            sticker.put(keySrc,item.getString(keySrc));
                        }
                        if (item.has(keyAllowedRoles) && !item.isNull(keyAllowedRoles)) {
                            sticker.put(keyAllowedRoles,item.getJSONArray(keyAllowedRoles));
                        }
                        if (item.has(keyBlockedRoles) && !item.isNull(keyBlockedRoles)) {
                            sticker.put(keyBlockedRoles,item.getJSONArray(keyBlockedRoles));
                        }
                        if (item.has(keyAllowedUsers) && !item.isNull(keyAllowedUsers)) {
                            sticker.put(keyAllowedUsers,item.getJSONArray(keyAllowedUsers));
                        }
                        if (item.has(keyBlockedUsers) && !item.isNull(keyBlockedUsers)) {
                            sticker.put(keyBlockedUsers,item.getJSONArray(keyBlockedUsers));
                        }
                        valueStickers.put(sticker);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                logger.info(fName + ".valueStickers=" + valueStickers.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean readDefaultFile() {
            String fName="[readDefaultFile]";
            logger.info(fName);
            try {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream isDefault = classloader.getResourceAsStream("json/stickers.json");
                File file;InputStream fileStream=null;
                try {
                    file=new File("resources/json/stickers.json");
                    if(file!=null){
                        fileStream = new FileInputStream(file);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                text2Json=new lcText2Json();
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        if(!text2Json.isInputStream2Json(isDefault)){
                            logger.warn(fName+".failed to load");return false;
                        }else{
                            logger.info(fName+".loaded from jar");
                        }
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }else{
                    if(!text2Json.isInputStream2Json(isDefault)){
                        logger.warn(fName+".failed to load");return false;
                    }else{
                        logger.info(fName+".loaded from jar");
                    }
                }
                if(text2Json.jsonArray.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName+"length="+text2Json.jsonArray.length());
                valueStickersDefault=new JSONArray();

                for(int i=0;i<text2Json.jsonArray.length();i++){
                    try {
                        JSONObject sticker=new JSONObject();
                        sticker.put(keyName,""); sticker.put(keySrc,"");
                        sticker.put(keyAllowedRoles,new JSONArray());
                        sticker.put(keyBlockedRoles,new JSONArray());
                        sticker.put(keyAllowedUsers,new JSONArray());
                        sticker.put(keyBlockedUsers,new JSONArray());
                        logger.info(fName + ".index=" + i);
                        JSONObject item = text2Json.jsonArray.getJSONObject(i);
                        logger.info(fName + ".item=" + item.toString());
                        if (item.has(keyName) && !item.isNull(keyName)) {
                            sticker.put(keyName,item.getString(keyName));
                        }
                        if (item.has(keySrc) && !item.isNull(keySrc)) {
                            sticker.put(keySrc,item.getString(keySrc));
                        }
                        if (item.has(keyAllowedRoles) && !item.isNull(keyAllowedRoles)) {
                            sticker.put(keyAllowedRoles,item.getJSONArray(keyAllowedRoles));
                        }
                        if (item.has(keyBlockedRoles) && !item.isNull(keyBlockedRoles)) {
                            sticker.put(keyBlockedRoles,item.getJSONArray(keyBlockedRoles));
                        }
                        if (item.has(keyAllowedUsers) && !item.isNull(keyAllowedUsers)) {
                            sticker.put(keyAllowedUsers,item.getJSONArray(keyAllowedUsers));
                        }
                        if (item.has(keyBlockedUsers) && !item.isNull(keyBlockedUsers)) {
                            sticker.put(keyBlockedUsers,item.getJSONArray(keyBlockedUsers));
                        }
                        valueStickersDefault.put(sticker);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                logger.info(fName + ".valueStickersDefault=" + valueStickersDefault.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private lcJSONGuildProfile gProfile;
        private Boolean saveProfile() {
            String fName = "[saveProfile]";
            logger.info(cName + fName);
            if (gProfile.saveProfile(llv2_GuildsSettings)) {
                logger.info(cName + fName + ".success");
                return true;
            }
            logger.warn(cName + fName + ".failed");
            return false;
        }
        String gName="stickers";
        private Boolean loadedProfile() {
            String fName = "[loadedProfile]";
            logger.info(cName + fName);
            gProfile =gGlobal.getGuildSettings(gGuild,gName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyUserProfileEntry();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile(llv2_GuildsSettings)){
                    logger.info(cName + fName + ".success save to db"); return true;
                }else{
                    logger.error(cName + fName + ".error save to db"); return false;
                }
            }
            return gProfile.isExistent();
        }
        String keyStickers="stickers", keyEnabled="enabled", keySourceFile="sourceFile";
        private void safetyUserProfileEntry() {
            String fName = "[safetyUserProfileEntry]";
            logger.info(cName + fName + ".safety check");
            gProfile.safetyPutFieldEntry(keyStickers,new JSONArray());
            gProfile.safetyPutFieldEntry(keyEnabled,true);
            gProfile.safetyPutFieldEntry(keySourceFile,"");
            gProfile.safetyPutFieldEntry(keyAllowedRoles,new JSONArray());
            gProfile.safetyPutFieldEntry(keyAllowedUsers,new JSONArray());
            gProfile.safetyPutFieldEntry(keyBlockedRoles,new JSONArray());
            gProfile.safetyPutFieldEntry(keyBlockedUsers,new JSONArray());
        }
        String valueSourceFile=""; boolean valueEnabled=true; JSONArray valueStickers=new JSONArray(); JSONArray valueStickersDefault=new JSONArray();
        private void getValues() {
            String fName = "[getValues]";
            logger.info(cName + fName + "....");
            try {
                if(gProfile.jsonObject.has(keyEnabled)){
                    valueEnabled=gProfile.jsonObject.getBoolean(keyEnabled);
                }
                if(gProfile.jsonObject.has(keySourceFile)){
                    valueSourceFile=gProfile.jsonObject.getString(keySourceFile);
                }
                if(gProfile.jsonObject.has(keyEnabled)){
                    valueStickers=gProfile.jsonObject.getJSONArray(keyStickers);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }



    
    
    
    
    
    
    
  //runLocal  
    }
}
