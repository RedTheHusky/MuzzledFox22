package nsfw.chastity.chastikey;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lc.emotes.lcEmote;
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
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ChastiKey extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper,iChastiKey {
        Logger logger = Logger.getLogger(getClass());
       
        lcGlobalHelper gGlobal;
        String gTitle="ChastiKey",gCommand="chastikey";
        JSONObject rfEntries;
    public ChastiKey(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Accessing information  from ChastiKey App.";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
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

    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        String[] items;
        Boolean isInvalidCommand = true;
        try{
            gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"ChastiKey",gGlobal);
            gBasicFeatureControl.initProfile();
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
                getEmojis();
                if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
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
                if(isInvalidCommand&&items.length>=3&&isTargeted(items[1])){
                    if(items[0].equalsIgnoreCase("checklock")){
                        getUserChecklock("",items[2]);isInvalidCommand=false;
                    }
                }
                if(isInvalidCommand&&items.length>=3){
                    if(items[0].equalsIgnoreCase("checklock")){
                        getUserChecklock(items[1],items[2]);isInvalidCommand=false;
                    }
                }
                if(isInvalidCommand&&items.length>=2&&isTargeted(items[1])){
                    if(items[0].equalsIgnoreCase("combinations")||items[0].equalsIgnoreCase("combination")){
                        getUserCombination("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("lockee")){
                        getUserLockeeData("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("keyholder")){
                        getUserKeyholderData("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("checklock")){
                        getUserChecklock("","");isInvalidCommand=false;
                    }
                }
                 if(isInvalidCommand&&items.length>=2){
                    if(items[0].equalsIgnoreCase("combinations")||items[0].equalsIgnoreCase("combination")){
                        getUserCombination(items[1]);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("lockee")){
                        getUserLockeeData(items[1]);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("keyholder")){
                        getUserKeyholderData(items[1]);isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("checklock")){
                        getUserChecklock("",items[1]);isInvalidCommand=false;
                    }
                }
                if(isInvalidCommand){
                    if(items[0].equalsIgnoreCase("combinations")||items[0].equalsIgnoreCase("combination")){
                        getUserCombination("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("lockee")){
                        getUserLockeeData("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("keyholder")){
                        getUserKeyholderData("");isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("checklock")){
                        getUserChecklock("","");isInvalidCommand=false;
                    }
                }
                //isInvalidCommand=false;
            }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
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

    Calendar gCalendarToday;
    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+gCommand+" ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(gTitle);
        embed.addField("What is ChastiKey App?","Is an online chastity keyholding mobile app service.\nGo to their [site](https://chastikey.com), [discord](https://discordapp.com/invite/7CNpSJe) or [api](https://chastikey.com/api/docs).",false);
        embed.addField("What it does?","Allows viewing stats from users profile on ChastiKey",false);
        embed.addField("Combination","Use `"+quickSummonWithSpace+"combinations [@Member]` Retrieve previous combinations for a specific user.",false);
        embed.addField("Lockee","Use `"+quickSummonWithSpace+"lockee [@Member]` Retrieve lockee stats for a specific user.",false);
        embed.addField("Keyholder","Use `"+quickSummonWithSpace+"keyholder [@Member]` Retrieve keyholder stats for a specific user.",false);
        embed.addField("Checklock","Use `"+quickSummonWithSpace+"checklock [@Member] <LockID/LockName>` Retrieve checklock stats for a specific user.",false);
        embed.addField("Mentioning","If no user is mentioned the author of the command is used.",false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    String keyLocks="locks";

    private void getUserCombination(String name) {
        String fName = "[getUserCombination]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "member=" + member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            logger.info(fName + "name=");
            Unirest a= new Unirest();
            MultipartBody b;
            b=a.post(urlCombinations)
                    .header(clientAuth.keyClientId,Config.getClientId(gGlobal))
                    .header(clientAuth.keyClientSecret,Config.getClientKey(gGlobal))
                    .field(parameters.keyIncludeTestLocks,1);
            if(name.isBlank()||name.isEmpty()){
                logger.info(fName+".using discord");
                b.field(parameters.keyDiscordID,member.getId());
            }else{
                logger.info(fName+".using name");
                b.field(parameters.keyUsername,name);
            }
            HttpResponse<JsonNode> jsonResponse=b.asJson();JsonNode body;
            if(jsonResponse.getStatus()>200){
                logger.error(fName+".invalid status");
                getError(jsonResponse);
                return ;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()){
                logger.error(fName+".body is an array");
                getError(jsonResponse);
                return ;
            }
            JSONArray array=new JSONArray();
            if(!body.getObject().isEmpty()&&body.getObject().has(keyLocks)&&!body.getObject().isNull(keyLocks)){
                array =body.getObject().getJSONArray(keyLocks);
            }
            if(array.isEmpty()){
                logger.error(fName+".array is empty");
                return ;
            }
            reactionMenuCombinations(array,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void getUserKeyholderData(String name) {
        String fName = "[getUserKeyholderData]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "member=" + member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            logger.info(fName + "name=");
            Unirest a= new Unirest();
            HttpRequestWithBody b;
            b=a.post(urlKeyholderdata)
                    .header(clientAuth.keyClientId,Config.getClientId(gGlobal))
                    .header(clientAuth.keyClientSecret,Config.getClientKey(gGlobal));
            HttpResponse<JsonNode> jsonResponse;JsonNode body;
            if(name.isBlank()||name.isEmpty()){
                logger.info(fName+".using discord");
                jsonResponse=b.field(parameters.keyDiscordID,member.getId()).asJson();
            }else{
                logger.info(fName+".using name");
                jsonResponse=b.field(parameters.keyUsername,name).asJson();
            }
            if(jsonResponse.getStatus()>200){
                getError(jsonResponse);
                return ;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()){
                logger.error(fName+".body is an array");
                getError(jsonResponse);
                return ;
            }
            JSONObject objects=new JSONObject();
            if(!body.getObject().isEmpty()){
                objects =body.getObject();
            }
            if(objects.isEmpty()){
                logger.error(fName+".array is empty");
                return ;
            }
            reactionMenuKeyholder(objects);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void getUserLockeeData(String name) {
        String fName = "[getUserLockeeData]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "member=" + member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            logger.info(fName + "name=");
            Unirest a= new Unirest();
            MultipartBody b;
            b=a.post(urlLockeedata)
                    .header(clientAuth.keyClientId,Config.getClientId(gGlobal))
                    .header(clientAuth.keyClientSecret,Config.getClientKey(gGlobal))
                    .field(parameters.keyIncludeTestLocks,1);
            if(name.isBlank()||name.isEmpty()){
                logger.info(fName+".using discord");
                b.field(parameters.keyDiscordID,member.getId());
            }else{
                logger.info(fName+".using name");
                b.field(parameters.keyUsername,name);
            }
            HttpResponse<JsonNode> jsonResponse=b.asJson();JsonNode body;
            if(jsonResponse.getStatus()>200){
                logger.error(fName+".invalid status");
                getError(jsonResponse);
                return ;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()){
                logger.error(fName+".body is an array");
                getError(jsonResponse);
                return ;
            }
            JSONObject objects=new JSONObject();
            if(!body.getObject().isEmpty()){
                objects =body.getObject();
            }
            if(objects.isEmpty()){
                logger.error(fName+".array is empty");
                return ;
            }
            reactionMenuLockee(objects);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void getError(HttpResponse<JsonNode> jsonResponse) {
        String fName = "[getError]";
        logger.info(fName);
        try {
            EmbedBuilder embedBuilder=new EmbedBuilder();
            //Its empty...The given data returned invalid response from the ChastiKey Server
            //embedBuilder.setFooter(String.valueOf(jsonResponse.getStatus()));
            if(jsonResponse!=null&&jsonResponse.getBody()!=null){
                logger.warn(fName+".jsonResponse="+jsonResponse.getBody().toString());
                if(!jsonResponse.getBody().isArray()){
                    logger.warn(fName+".body is an array");
                    JSONObject jsonObject=jsonResponse.getBody().getObject();
                    if(jsonObject.has("response")&&!jsonObject.isNull("response")&&jsonObject.getJSONObject("response").has("message")&&!jsonObject.getJSONObject("response").isNull("message")){
                        embedBuilder.setDescription("The returned value from chastikey api is invalid.\n"+jsonObject.getJSONObject("response").getString("message"));
                    }else{
                        embedBuilder.setDescription("The returned value from chastikey api is invalid.");
                    }
                }else{
                    embedBuilder.setDescription("The returned value from chastikey api is invalid.");
                }
            }else{
                embedBuilder.setDescription("The returned value from chastikey api is empty.");
            }
            embedBuilder.setColor(llColorRed_Cornell);
            llSendMessage(gTextChannel,embedBuilder);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void reactionMenuCombinations(JSONArray array,int index){
        String fName="[reactionMenuCombinations]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            JSONObject jsonObject=array.getJSONObject(index);
            String key=keyLockGroupID;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Group ID",tmp,true);
                }
            }
            key=keyLockID;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock ID",tmp,true);
                }
            }
            key=keyLockedBy;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Locked By",tmp,true);
                }
            }
            key=keyLockName;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Name",tmp,true);
                }
            }
            key=keyCombination;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Combination",tmp,true);
                }
            }
            key=keyTest;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                int tmp=jsonObject.getInt(key);
                if(tmp==1){
                    embed.addField("Test","Yes",true);
                }
            }
            key=keyTimestampUnlocked;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                long tmp=jsonObject.getLong(key);
                if(tmp!=0){
                    String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date (tmp*1000));
                    embed.addField("Unlocked",date,true);
                }
            }

            Message message=llSendMessageResponse(gTextChannel,embed);
            if(array.length()>1){
                if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                if(index<array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton)).queue();}
                if(index!=array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                int finalIndex = index;
                logger.info(cName+fName+"prepare wait");
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.info(cName+fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                    logger.info(cName+fName+"do=back");
                                    llMessageDelete(message);
                                    if((finalIndex-1)>=0){
                                        //selectedItem=array4Responses.getJSONObject(finalIndex-1);
                                        reactionMenuCombinations(array,finalIndex-1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton))){
                                    logger.info(cName+fName+"do=next");
                                    llMessageDelete(message);
                                    if((finalIndex+1)<array.length()) {
                                        //selectedItem = array4Responses.getJSONObject(finalIndex + 1);
                                        reactionMenuCombinations(array,finalIndex+1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                    logger.info(cName+fName+"do=first");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(0);
                                    reactionMenuCombinations(array,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                    logger.info(cName+fName+"do=last");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(array4Responses.length()-1);
                                    reactionMenuCombinations(array,array.length()-1);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void reactionMenuKeyholder(JSONObject mainObject){
        String fName="[reactionMenuKeyholder]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String key=keyUsername;
            JSONObject object=mainObject.getJSONObject(keyData);
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(parameters.keyUsername,tmp,true);
                }
            }
            key=keyDiscordID;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    String mention=llGetMemberMention(gGuild,tmp);
                    if(mention==null||mention.isEmpty()||mention.isBlank()){
                        embed.addField("Discord","("+tmp+")",true);
                    }else{
                        embed.addField("Discord",mention,true);
                    }
                }
            }
            key=keyAverageRating;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Average Rating",tmp,true);
                }
            }
            key=keyMainRole;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Main role",tmp,true);
                }
            }
            key=keyKeyholderLevel;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Keyholder Level",tmp,true);
                }
            }
            key=keyLocks;
            JSONArray array=new JSONArray();
            if(mainObject.has(key)&&!mainObject.isNull(key)){
                JSONArray tmp=mainObject.getJSONArray(key);
                if(!tmp.isEmpty()){
                    array=tmp;
                    embed.addField("Locks", String.valueOf(array.length()+"\nUse :mag_right: to view it"),false);
                }
            }
            //embed.addField("Options","Use :one: to select locks.",false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse(gTextChannel,embed);
            if(!array.isEmpty()){
                logger.info(cName+fName+"add mag right");
                message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
            }
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
            JSONArray finalArray = array;
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(cName+fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            //logger.warn(cName+fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("mag_right"))){
                                llMessageDelete(message);
                                reactionMenuKeyholderLocks(mainObject,finalArray,0);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                llMessageClearReactions(message);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                llMessageDelete(message);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },1, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void reactionMenuKeyholderLocks(JSONObject mainObject,JSONArray array, int index){
        String fName="[reactionMenuKeyholderLocks]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String key=keyUsername;
            JSONObject keyholder=mainObject.getJSONObject(keyData);
            String valueKeyHolder="";
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    valueKeyHolder=tmp;
                }
            }
            key=keyDiscordID;
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    String mention=llGetMemberMention(gGuild,tmp);
                    if(mention==null||mention.isEmpty()||mention.isBlank()){
                        valueKeyHolder+="\n("+tmp+")";

                    }else{
                        valueKeyHolder+="\n"+mention;
                    }
                }
            }
            embed.addField("Keyholder",valueKeyHolder,true);
            key=keyLockName;
            JSONObject object=array.getJSONObject(index);
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Name",tmp,true);
                }
            }
            key=keyCumulative;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Cumulative",tmp,true);
                }
            }
            key=keyFixed;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Fixed",tmp,true);
                }
            }
            key=keyKeyDisabled;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Key Disabled",tmp,true);
                }
            }
            key=keyMaxAutoResets;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    embed.addField("Max auto reset", String.valueOf(tmp),true);
                }
            }
            int maxDoubleUps = 0,maxFreezes=0,maxGreens=0,maxMinutes=0,maxReds = 0,maxResets = 0,maxYellows = 0;
            key=keyMaxDoubleUps;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                   maxDoubleUps=object.getInt(key);
                }
            }
            key=keyMaxFreezes;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxFreezes=object.getInt(key);
                }
            }
            key=keyMaxGreens;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxGreens=object.getInt(key);
                }
            }
            key=keyMaxMinutes;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxMinutes=object.getInt(key);
                }
            }
            key=keyMaxReds;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxReds=object.getInt(key);
                }
            }
            key=keyMaxResets;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxResets=object.getInt(key);
                }
            }
            key=keyMaxYellows;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    maxYellows=object.getInt(key);
                }
            }
            int minDoubleUps = 0,minFreezes = 0,minGreens=0,minMinutes=0,minReds=0,minResets=0, minYellows=0;
            key=keyMinDoubleUps;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minDoubleUps=object.getInt(key);
                }
            }
            key=keyMinFreezes;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minFreezes=object.getInt(key);
                }
            }
            key=keyMinGreens;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minGreens=object.getInt(key);
                }
            }
            key=keyMinMinutes;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minMinutes=object.getInt(key);
                }
            }
            key=keyMinReds;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minReds=object.getInt(key);
                }
            }
            key=keyMinResets;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minResets=object.getInt(key);
                }
            }
            key=keyMinYellows;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp!=0){
                    minYellows=object.getInt(key);
                }
            }
            embed.addField(emoteDoubleup.getAsMention("")+"Double Up Cards", String.valueOf(minDoubleUps)+"-"+String.valueOf(maxDoubleUps),true);
            embed.addField(emoteFreezecard.getAsMention("")+"Freeze Cards", String.valueOf(minFreezes)+"-"+String.valueOf(maxFreezes),true);
            embed.addField(emoteGreencard.getAsMention("")+"Green Cards", String.valueOf(minGreens)+"-"+String.valueOf(maxGreens),true);
            embed.addField(emoteRedcard.getAsMention("")+"Red Cards", String.valueOf(minReds)+"-"+String.valueOf(maxReds),true);
            embed.addField(emoteResetcard.getAsMention("")+"Reset Cards", String.valueOf(minResets)+"-"+String.valueOf(maxResets),true);
            embed.addField(emoteYellowrandom.getAsMention("")+"Yellow Cards", String.valueOf(minYellows)+"-"+String.valueOf(maxYellows),true);
            JSONArray arrayLockees=new JSONArray();
            if(object.has(keyLockees)&&!object.isNull(keyLockees)){
               arrayLockees=object.getJSONArray(keyLockees);
               if(!arrayLockees.isEmpty()){
                   embed.addField("Lockees", String.valueOf(arrayLockees.length())+"\nUse :mag_right: to view it",true);
               }
            }
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse(gTextChannel,embed);
            int finalIndex = index; JSONArray finalArrayLockees = arrayLockees;
            if(array.length()==1){
                message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                String name=e.getReactionEmote().getName();
                                logger.info(cName+fName+"name="+name);
                                //logger.info(cName+fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("mag_right"))){
                                    logger.info(cName+fName+"do=check");
                                    llMessageDelete(message);
                                    reactionMenuKeyholderLockees(mainObject,object,finalArrayLockees,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }else
            if(array.length()>1){
                if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
                if(index<array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton)).queue();}
                if(index!=array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                logger.info(cName+fName+"prepare wait");
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                String name=e.getReactionEmote().getName();
                                logger.info(cName+fName+"name="+name);
                                //logger.info(cName+fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("mag_right"))){
                                    logger.info(cName+fName+"do=check");
                                    llMessageDelete(message);
                                    reactionMenuKeyholderLockees(mainObject,object,finalArrayLockees,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                    logger.info(cName+fName+"do=back");
                                    llMessageDelete(message);
                                    if((finalIndex-1)>=0){
                                        //selectedItem=array4Responses.getJSONObject(finalIndex-1);
                                        reactionMenuKeyholderLocks(mainObject,array,finalIndex-1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton))){
                                    logger.info(cName+fName+"do=next");
                                    llMessageDelete(message);
                                    if((finalIndex+1)<array.length()) {
                                        //selectedItem = array4Responses.getJSONObject(finalIndex + 1);
                                        reactionMenuKeyholderLocks(mainObject,array,finalIndex+1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                    logger.info(cName+fName+"do=first");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(0);
                                    reactionMenuKeyholderLocks(mainObject,array,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                    logger.info(cName+fName+"do=last");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(array4Responses.length()-1);
                                    reactionMenuKeyholderLocks(mainObject,array,array.length()-1);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void reactionMenuKeyholderLockees(JSONObject mainObject,JSONObject lock,JSONArray array, int index){
        String fName="[reactionMenuKeyholderLockees]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String key=keyUsername;
            JSONObject keyholder=mainObject.getJSONObject(keyData);
            String valueKeyHolder="";
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    valueKeyHolder=tmp;
                }
            }
            key=keyDiscordID;
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    String mention=llGetMemberMention(gGuild,tmp);
                    if(mention==null||mention.isEmpty()||mention.isBlank()){
                        valueKeyHolder+="\n("+tmp+")";

                    }else{
                        valueKeyHolder+="\n"+mention;
                    }
                }
            }
            embed.addField("Keyholder",valueKeyHolder,true);
            JSONObject object=array.getJSONObject(index);
            key=keyUsername;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(parameters.keyUsername,tmp,true);
                }
            }
            JSONObject data=mainObject.getJSONObject(keyData);
            if(data.has(key)&&!data.isNull(key)){
                String tmp=data.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Keyholder",tmp,true);
                }
            }
            key=keyLockName;
            if(lock.has(key)&&!lock.isNull(key)){
                String tmp=lock.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Name",tmp,true);
                }
            }
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse(gTextChannel,embed);
            if(array.length()>1){
                if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
                if(index<array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton)).queue();}
                if(index!=array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                int finalIndex = index;
                logger.info(cName+fName+"prepare wait");

                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                String name=e.getReactionEmote().getName();
                                //logger.info(cName+fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                    logger.info(cName+fName+"do=back");
                                    llMessageDelete(message);
                                    if((finalIndex-1)>=0){
                                        //selectedItem=array4Responses.getJSONObject(finalIndex-1);
                                        reactionMenuKeyholderLockees(mainObject,lock,array,finalIndex-1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton))){
                                    logger.info(cName+fName+"do=next");
                                    llMessageDelete(message);
                                    if((finalIndex+1)<array.length()) {
                                        //selectedItem = array4Responses.getJSONObject(finalIndex + 1);
                                        reactionMenuKeyholderLockees(mainObject,lock,array,finalIndex+1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                    logger.info(cName+fName+"do=first");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(0);
                                    reactionMenuKeyholderLockees(mainObject,lock,array,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                    logger.info(cName+fName+"do=last");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(array4Responses.length()-1);
                                    reactionMenuKeyholderLockees(mainObject,lock,array,array.length()-1);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void reactionMenuLockee(JSONObject mainObject){
        String fName="[reactionMenuLockee]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String key=keyUsername;
            JSONObject object=mainObject.getJSONObject(keyData);
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(parameters.keyUsername,tmp,true);
                }
            }
            key=keyDiscordID;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    String mention=llGetMemberMention(gGuild,tmp);
                    if(mention==null||mention.isEmpty()||mention.isBlank()){
                        embed.addField("Discord","("+tmp+")",true);
                    }else{
                        embed.addField("Discord",mention,true);
                    }
                }
            }
            key=keyAverageRating;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Average Rating",tmp,true);
                }
            }
            key=keyMainRole;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Main role",tmp,true);
                }
            }
            key=keyLockeeLevel;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lockee Level",tmp,true);
                }
            }
            key=keyAverageTimeLockedInSeconds;
            if(object.has(key)&&!object.isNull(key)){
                long tmp=object.getLong(key);
                embed.addField("Average Time Locked", displayDuration(tmp*1000),true);

            }
            key=keyLongestCompletedLockInSeconds;
            if(object.has(key)&&!object.isNull(key)){
                long tmp=object.getLong(key);
                embed.addField("Longest Completed Lock", displayDuration(tmp*1000),true);

            }
            key=keySecondsLockedInCurrentLock;
            if(object.has(key)&&!object.isNull(key)){
                long tmp=object.getLong(key);
                embed.addField("Curren tLock", displayDuration(tmp*1000),true);

            }

            key=keyLocks;
            JSONArray array=new JSONArray();
            if(mainObject.has(key)&&!mainObject.isNull(key)){
                JSONArray tmp=mainObject.getJSONArray(key);
                if(!tmp.isEmpty()){
                    array=tmp; embed.addField("Locks", String.valueOf(array.length()+"\nUse :mag_right: to view it"),false);
                }
            }
            //embed.addField("Options","Use :one: to select locks.",false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse(gTextChannel,embed);
            if(!array.isEmpty()){
                message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
            }
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
            JSONArray finalArray = array;
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(cName+fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(cName+fName+"asCodepoints="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("mag_right"))){
                                logger.warn(cName+fName+"do");
                                llMessageDelete(message);
                                reactionMenuLockeeLocks(mainObject,finalArray,0);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                logger.warn(cName+fName+"do");
                                llMessageClearReactions(message);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                logger.warn(cName+fName+"do");
                                llMessageDelete(message);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },1, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void reactionMenuLockeeLocks(JSONObject mainObject,JSONArray array, int index){
        String fName="[reactionMenuKeyholderLocks]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            JSONObject object=array.getJSONObject(index);
            String key=keyUsername;
            JSONObject keyholder=mainObject.getJSONObject(keyData);
            String valueKeyHolder="";
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    valueKeyHolder=tmp;
                }
            }
            key=keyDiscordID;
            if(keyholder.has(key)&&!keyholder.isNull(key)){
                String tmp=keyholder.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    String mention=llGetMemberMention(gGuild,tmp);
                    if(mention==null||mention.isEmpty()||mention.isBlank()){
                        valueKeyHolder+="\n("+tmp+")";

                    }else{
                        valueKeyHolder+="\n"+mention;
                    }
                }
            }
            embed.addField("Lockee",valueKeyHolder,true);
            key=keyLockedBy;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Locked By",tmp,true);
                }
            }
            key=keyLockName;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Name",tmp,true);
                }
            }
            key=keyStatus;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Status",tmp,true);
                }
            }
            key=keyTest;
            if(object.has(key)&&!object.isNull(key)){
                int tmp=object.getInt(key);
                if(tmp==1){
                    embed.addField("Test","true",true);
                }
            }
            key=keyDoubleUpCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteDoubleup.getAsMention("")+"Double Up Cards",tmp,true);
                }
            }
            key=keyGreenCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteGreencard.getAsMention("")+"Green Cards",tmp,true);
                }
            }
            key=keyFreezeCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteFreezecard.getAsMention("")+"Freeze Cards",tmp,true);
                }
            }
            key=keyRedCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteRedcard.getAsMention("")+"Red Cards",tmp,true);
                }
            }
            key=keyStickyCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteStickycard.getAsMention("")+"Stick Cards",tmp,true);
                }
            }
            key=keyYellowCards;
            if(object.has(key)&&!object.isNull(key)){
                String tmp=object.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField(emoteYellowrandom.getAsMention("")+"Yellow Cards",tmp,true);
                }
            }
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embed.build());
            if(array.length()>1){
                if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                //message.addReaction(gGlobal.emojis.getEmoji("mag_right")).queue();
                if(index<array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton)).queue();}
                if(index!=array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                int finalIndex = index;
                logger.info(cName+fName+"prepare wait");
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                String name=e.getReactionEmote().getName();
                                logger.info(cName+fName+"name="+name);
                                //logger.info(cName+fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                    logger.info(cName+fName+"do=back");
                                    llMessageDelete(message);
                                    if((finalIndex-1)>=0){
                                        //selectedItem=array4Responses.getJSONObject(finalIndex-1);
                                        reactionMenuLockeeLocks(mainObject,array,finalIndex-1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton))){
                                    logger.info(cName+fName+"do=next");
                                    llMessageDelete(message);
                                    if((finalIndex+1)<array.length()) {
                                        //selectedItem = array4Responses.getJSONObject(finalIndex + 1);
                                        reactionMenuLockeeLocks(mainObject,array,finalIndex+1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                    logger.info(cName+fName+"do=first");
                                    llMessageDelete(message);
                                    reactionMenuLockeeLocks(mainObject,array,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                    logger.info(cName+fName+"do=last");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(array4Responses.length()-1);
                                    reactionMenuLockeeLocks(mainObject,array,array.length()-1);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    long milliseconds_2weeks=1209600000;
    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;
    private String displayDuration(Long time){
        String fName = "[displayDuration]";
        Logger logger = Logger.getLogger(fName);
        try{
            if(time==null){
                logger.info(fName+"time is null");
                return "null";
            }
            logger.info(fName+"time="+time);
            long week = time / milliseconds_week;
            long diffWeek = time % milliseconds_week;
            long day = diffWeek / milliseconds_day;
            long diffDay = diffWeek % milliseconds_day;
            long hour = diffDay / milliseconds_hour;
            long diffHour = diffDay % milliseconds_hour;
            long minutes = diffHour / milliseconds_minute;
            logger.info(fName+"week="+week);
            logger.info(fName+"diffWeek="+diffWeek);
            logger.info(fName+"day="+day);
            logger.info(fName+"time="+diffDay);
            logger.info(fName+"hour="+hour);
            logger.info(fName+"minutes="+minutes);
            String str="";
            if(week>1){
                str+=week+" weeks ";
            }else
            if(week==1){
                str+=week+" week ";
            }
            if(day>1){
                str+=day+" days ";
            }else
            if(day==1){
                str+=day+" day ";
            }
            if(hour>1){
                str+=hour+" hours ";
            }else
            if(hour==1){
                str+=hour+" hour ";
            }
            if(minutes>1){
                str+=minutes+" minutes ";
            }else
            if(minutes==1){
                str+=minutes+" minute ";
            }
            logger.info(fName+"str="+str);
            return str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "null";}
    }
    Guild gGuildChastityApp;
    //:stickycard::resetcard::redcard::greencard::goagain::frozenlock::freezecard::doubleup::yellowrandom:
    lcEmote emoteStickycard, emoteResetcard, emoteRedcard, emoteGreencard, emoteGoagain, emoteFrozenlock, emoteFreezecard,emoteDoubleup,emoteYellowrandom;
    private void getEmojis(){
        String fName = "[getEmoji]";
        try{
            gGuildChastityApp=gGlobal.getGuild("473856867768991744");
            if(gGuildChastityApp==null){
                logger.warn(cName+fName+"gGuildChastityApp is null");
            }else{
                logger.info(cName+fName+"gGuildChastityApp name:"+gGuildChastityApp.getName());
            }
            emoteStickycard =new lcEmote(gGuildChastityApp,"stickycard",false);
            emoteResetcard =new lcEmote(gGuildChastityApp,"resetcard",false);
            emoteRedcard =new lcEmote(gGuildChastityApp,"redcard",false);
            emoteGreencard =new lcEmote(gGuildChastityApp,"greencard",false);
            emoteGoagain =new lcEmote(gGuildChastityApp,"goagain",false);
            emoteFrozenlock =new lcEmote(gGuildChastityApp,"frozenlock",false);
            emoteFreezecard =new lcEmote(gGuildChastityApp,"freezecard",false);
            emoteDoubleup=new lcEmote(gGuildChastityApp,"doubleup",false);
            emoteYellowrandom=new lcEmote(gGuildChastityApp,"yellowrandom",false);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); }
    }
    private void getUserChecklock(String name, String lockid) {
        String fName = "[getUserChecklock]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "member=" + member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            logger.info(fName + "name="+name);
            logger.info(fName + " lockid="+ lockid);
            Unirest a= new Unirest();
            HttpRequestWithBody b;
            b=a.post(urlChecklock)
                    .header(clientAuth.keyClientId,Config.getClientId(gGlobal))
                    .header(clientAuth.keyClientSecret,Config.getClientKey(gGlobal));
            HttpResponse<JsonNode> jsonResponse = null;JsonNode body;
            Map<String,Object>map= new LinkedHashMap<>();
            if(name.isBlank()||name.isEmpty()){
                logger.info(fName+".using discord");
                map.put(parameters.keyDiscordID,member.getId());
            }else{
                logger.info(fName+".using name");
                map.put(parameters.keyUsername,name);
            }
            if(!lockid.isEmpty()&& !Pattern.matches("[a-zA-Z]+", lockid)){
                logger.info(fName+".is number");
                if(lockid.length()>10){
                    map.put("LockID",lockid);
                }else{
                    map.put("LockGroupID",lockid);
                }
            }else if(!lockid.isEmpty()){
                map.put("LockName",lockid);
            }
            jsonResponse=b.fields(map).asJson();
            if(jsonResponse!=null&&jsonResponse.getBody()!=null){
                logger.info(fName+".jsonRespons ="+ jsonResponse.getBody().toString());
            }

            if(jsonResponse.getStatus()>200){
                getError(jsonResponse);
                return ;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()) {
                logger.error(fName + ".body is an array");
                getError(jsonResponse);
                return;
            }
            JSONArray array=new JSONArray();
            if(!body.getObject().isEmpty()&&body.getObject().has(keyLocks)&&!body.getObject().isNull(keyLocks)){
                array =body.getObject().getJSONArray(keyLocks);
            }
            if(array.isEmpty()){
                logger.error(fName+".array is empty");
                return ;
            }
            reactionMenuChecklock(array,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private void reactionMenuChecklock(JSONArray array,int index){
        String fName="[reactionMenuCombinations]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            JSONObject jsonObject=array.getJSONObject(index);
            String key=keyLockGroupID;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Group ID",tmp,true);
                }
            }
            key=keyLockID;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock ID",tmp,true);
                }
            }
            key=keyLockedBy;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Locked By",tmp,true);
                }
            }
            key=keyLockName;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Lock Name",tmp,true);
                }
            }
            key=keyTest;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                int tmp=jsonObject.getInt(key);
                if(tmp==1){
                    embed.addField("Test","Yes",true);
                }
            }
            key=keyStatus;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                String tmp=jsonObject.getString(key);
                if(!tmp.isEmpty()&&!tmp.isBlank()){
                    embed.addField("Status",tmp,true);
                }
            }
            key=keyLockFrozen;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                int tmp=jsonObject.getInt(key);
                if(tmp==1){
                    embed.addField("Frozen","Yes",true);
                }
            }
            key=keyTimestampLocked;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                long tmp=jsonObject.getLong(key);
                if(tmp!=0){
                    String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date (tmp*1000));
                    embed.addField("Locked",date,true);
                }
            }
            key=keyTimestampUnlocked;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                long tmp=jsonObject.getLong(key);
                if(tmp!=0){
                    String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date (tmp*1000));
                    embed.addField("Unlocked",date,true);
                }
            }
            Message message=llSendMessageResponse(gTextChannel,embed);
            if(array.length()>1){
                if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                if(index<array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton)).queue();}
                if(index!=array.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                int finalIndex = index;
                logger.info(cName+fName+"prepare wait");
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                //logger.info(cName+fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                    logger.info(cName+fName+"do=back");
                                    llMessageDelete(message);
                                    if((finalIndex-1)>=0){
                                        //selectedItem=array4Responses.getJSONObject(finalIndex-1);
                                        reactionMenuChecklock(array,finalIndex-1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    logger.info(cName+fName+"do=print");
                                    llMessageClearReactions(e.getChannel(),e.getMessageId());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastPlayButton))){
                                    logger.info(cName+fName+"do=next");
                                    llMessageDelete(message);
                                    if((finalIndex+1)<array.length()) {
                                        //selectedItem = array4Responses.getJSONObject(finalIndex + 1);
                                        reactionMenuChecklock(array,finalIndex+1);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                    logger.info(cName+fName+"do=first");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(0);
                                    reactionMenuChecklock(array,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                    logger.info(cName+fName+"do=last");
                                    llMessageDelete(message);
                                    //selectedItem = array4Responses.getJSONObject(array4Responses.length()-1);
                                    reactionMenuChecklock(array,array.length()-1);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.info(cName+fName+"do=delete");
                                    llMessageDelete(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
                logger.info(cName+fName+"wait created");
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

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
                List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
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
