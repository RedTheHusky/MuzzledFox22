package search;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import search.entities.lcINKBUNNY;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class inkbunny extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, lcINKBUNNY.INTERFACE {

    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gTitle="InkBunny-ImageSearcher",gCommand="ibunny";
    public inkbunny(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Get images from InkBunny";
        this.aliases = new String[]{gCommand,"inkbunny"};
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
        String cName = "[runLocal]";
        CommandEvent gEvent;
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
        public runLocal(CommandEvent ev) {
            String fName = "[runLocal]";
            logger.info(".run build");
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
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"inkbunny",gGlobal);
                gBasicFeatureControl.initProfile();
                lcInkbunny =new lcINKBUNNY(gGlobal);
                String[] items;
                boolean isInvalidCommand = true;
            /*if(!isNSFW()){
                blocked();return;
            }*/
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");
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
                    else if(items.length>=2&&items[0].equalsIgnoreCase("view")){
                        getViewPage(items[1]);
                    }else
                    if(gEvent.getArgs().replaceAll("\\s+","").toLowerCase().contains("https:")||gEvent.getArgs().replaceAll("\\s+","").toLowerCase().contains("http:")){
                        getViewPage(gEvent.getArgs());
                    }
                    else{
                        doSearch(gEvent.getArgs());
                    }
                    llMessageDelete(gEvent);
                }
            /*isInvalidCommand=false;
            logger.info(fName+".deleting op message");
            llQuckCommandMessageDelete(gEvent);
            if(isInvalidCommand){
                llQuickEmbedChannelResponse(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }*/
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(".run ended");
        }
        lcINKBUNNY lcInkbunny =new lcINKBUNNY(null);
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
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle);
            embedBuilder.addField("Search Submissions" ,"`"+llPrefixStr+"ibunny [tags] <options>` Options are optional search parameter",false);
            embedBuilder.addField("Search Options" ,"`scraps(both (default)/only/no)` -enable to display scraps also or only scraps or not\n`keywords` -searth tags as keywords(default)\n`title` -search tags as title\n`description` -search tags as description\n`dayslimit(x) -filter by how long its been uploaded",false);
            embedBuilder.addField("View Submission" ,"`"+llPrefixStr+"ibunny view <id>` to display the submission image.",false);
            embedBuilder.addField("NSFW content","They can only be displayed in NSFW channels.",false);
            embedBuilder.addField("In case of displaying images against rules","Please report to a staff to have it deleted.",false);

            embedBuilder.setColor(llColorPurple2);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+llPrefixStr+gCommand+" guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }
        JSONObject options;

        private void setOptions(String message) {
            String fName = "[setOptions]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                String convertedMessage="";
                options.put(keyType,valueTypeSelected);
                if(message.toLowerCase().contains("scraps(both)"))options.put(keyScraps,valueScrapsBoth);
                if(message.toLowerCase().contains("scraps(no)"))options.put(keyScraps,valueScrapsBoth);
                if(message.toLowerCase().contains("scraps(only)"))options.put(keyScraps,valueScrapsBoth);
                if(message.toLowerCase().contains("keywords"))options.put(keyEnabledKeywords,valueEnabled);
                if(message.toLowerCase().contains("title"))options.put(keyEnabledTitle,valueEnabled);
                if(message.toLowerCase().contains("description"))options.put(keyEnabledDescription,valueEnabled);
                if(message.toLowerCase().contains("md5"))options.put(keyEnabledMd5,valueEnabled);
                if(message.toLowerCase().contains("random"))options.put(keyRandom,valueEnabled);
                convertedMessage=message.replaceAll("scraps(both)","").replaceAll("scraps(no)","").replaceAll("scraps(only)","");
                convertedMessage=convertedMessage.replaceAll("keywords","").replaceAll("title","").replaceAll("description","").replaceAll("md5","");
                String begin="dayslimit(";int i,j,l;
                if(convertedMessage.contains(begin)){
                    logger.info(fName+"found=page");
                    i=convertedMessage.indexOf(begin);
                    l=begin.length();
                    j=convertedMessage.indexOf(")");
                    options.put(keyDaysLimit,convertedMessage.substring(i+l,j));
                    StringBuffer stringBuffer = new StringBuffer(convertedMessage);
                    stringBuffer.replace( i ,j+1,"");
                    logger.info(fName+"keyDaysLimit="+options.getString(keyDaysLimit));
                    convertedMessage=stringBuffer.toString();
                    logger.info(fName+"convertedMessage="+convertedMessage);
                }
                options.put(keyText,convertedMessage);
                logger.info(fName+"options="+options);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private int getRandomSlot(int i){
            Random rand = new Random();// Generate random integers in range 0 to i
            int j=rand.nextInt(i);
            if(j<=0)j=1;
            return j;
        }
        private void getHtmlGallerySearch() {
            String fName = "[getHtmlGallerySearch]";
            try {
                Unirest a= new Unirest();
                //a.config().verifySsl(false);
                //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
                String url=gUrlSearch;
                if(options.has(keyText)&&!options.isNull(keyText)){

                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>"+options.getString(keyText));
                }else{
                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>undefined");
                }
                url= lcInkbunny.getGallerySearchURl(options);
                logger.info(fName+".url ="+url);
                HttpResponse<JsonNode> jsonResponse = lcInkbunny.reqGallerySearch(url);
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status"); return ;
                }
                JsonNode body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                searchResponse=body.getObject();

                JSONArray array=null;String submission_ids="";
                if(searchResponse.has(keySearchSubmissions)){
                    array=searchResponse.getJSONArray(keySearchSubmissions);
                    logger.info(fName+".array.length="+array.length());
                    logger.info(fName+".array="+array);
                    for(int i=0;i<array.length();i++){
                        JSONObject submission=array.getJSONObject(i);
                        logger.info(fName+"submission["+i+"]="+submission);
                        if(i>0){
                            submission_ids+=","+submission.getString(keySubmissionId);
                        }else{
                            submission_ids=submission.getString(keySubmissionId);
                        }
                    }
                    searchResponse.put(keySearchInfo_SubmissionIds,submission_ids);
                }


            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error: "+e,llColorRed);
            }
        }
        private void getHtmlSubmissionsView(String ids) {
            String fName = "[getHtmlSubmissionsView]";
            try {
                HttpResponse<JsonNode> jsonResponse = lcInkbunny.reqGetSubmissionsViewById(ids);
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status"); return;
                }
                JsonNode body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                JSONObject rdata=body.getObject();
                JSONArray array=null;
                if(rdata.has(keySearchSubmissions)){
                    array=rdata.getJSONArray(keySearchSubmissions);
                    logger.info(fName+".array.length="+array.length());
                    logger.info(fName+".array="+array);
                    for(int i=0;i<array.length();i++){
                        try {
                            JSONObject submission=array.getJSONObject(i);
                            logger.info(fName+"submission["+i+"]="+submission);
                            String keywords="";
                            boolean isValid=true;
                            if(submission.has("keywords")){
                                keywords=submission.getJSONArray("keywords").toString();
                                logger.info(fName+"submission["+i+"]keywords2String= "+keywords);
                                if(keywords.toLowerCase().contains("cub")||keywords.toLowerCase().contains("kid")||keywords.toLowerCase().contains("child")||keywords.toLowerCase().contains("baby")){
                                    logger.info(fName+"submission["+i+"] contains badword");isValid=false;
                                }
                            }
                            if(submission.has("friends_only")&&submission.getString("friends_only").equalsIgnoreCase("t")){
                                logger.info(fName+"submission["+i+"] is set to friends_only");isValid=false;
                            }
                            if(submission.has("guest_block")&&submission.getString("guest_block").equalsIgnoreCase("t")){
                                logger.info(fName+"submission["+i+"] is set to guest_block");isValid=false;
                            }
                            if(submission.has("hidden")&&submission.getString("hidden").equalsIgnoreCase("t")){
                                logger.info(fName+"submission["+i+"] is set to hidden");isValid=false;
                            }
                            if(isValid){
                                logger.info(fName+"submission["+i+"] is valid");
                                gallery.put(submission);
                            }
                        } catch (Exception e) {
                            logger.error(fName+"exception:"+e);
                            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }

                }
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error: "+e,llColorRed);
            }
        }
        private void doHtmlCheckup() {
            String fName = "[doHtmlCheckup]";
            try {
               lcInkbunny.doInkbunnyHtmlCheckup(gGlobal);
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error: "+e,llColorRed);
            }
        }

        JSONObject searchResponse =new JSONObject();
        JSONArray gallery =new JSONArray();
        JSONObject selectedItem=new JSONObject();
        private void doSearch(String message) {
            String fName = "[doSearch]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                setOptions(message);
                doHtmlCheckup();
                getHtmlGallerySearch();
                if(searchResponse.has(keySearchInfo_SubmissionIds)&&!searchResponse.isNull(keySearchInfo_SubmissionIds)){
                    getHtmlSubmissionsView(searchResponse.getString(keySearchInfo_SubmissionIds));
                }
                if(gallery.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);return;
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+ gallery.length());
                if(gallery.length()>1){i=getRandomSlot(gallery.length());}
                selectedItem= gallery.getJSONObject(i);
                selectedItem.put(keyIndex,i);
                reactionMenu();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private EmbedBuilder createEmbedDisplay(){
            String fName = "[createEmbedDisplay]";
            try {
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorRed_Light);
                String submissionId="#",title="#";
                if(selectedItem.has(keySubmissionId)){
                    submissionId=selectedItem.getString(keySubmissionId);
                }
                if(selectedItem.has(keySubmissionTitle)){
                    title=selectedItem.getString(keySubmissionTitle);
                }
                //embedBuilder.addField("Title","["+title+"]("+gUrlSubmissionPage.replaceAll("%",submissionId)+")",false);
                String userid="#",username="#";
                if(selectedItem.has(keySubmissionUserId)){
                    userid=selectedItem.getString(keySubmissionUserId);
                }
                if(selectedItem.has(keySubmissionUsername)){
                    username=selectedItem.getString(keySubmissionUsername);
                }
                if(selectedItem.has(keySubmissionUsername)){
                    username=selectedItem.getString(keySubmissionUsername);
                }
                int ratingId=-1;
                if(selectedItem.has(keySubmissionRatingId)){
                    ratingId=selectedItem.getInt(keySubmissionRatingId);
                }
                String ratingName="#";
                if(selectedItem.has(keySubmissionRatingName)){
                    ratingName=selectedItem.getString(keySubmissionRatingName);
                }
                if(selectedItem.has(keySubmissionRatings)){
                    JSONArray array=selectedItem.getJSONArray(keySubmissionRatings);
                    if(array!=null&&array.length()>0){
                        for(int i=0;i<array.length();i++){
                            JSONObject arrayItem=array.getJSONObject(i);
                            if(arrayItem.has("name")){
                                ratingName+=", "+arrayItem.getString("name");
                            }
                        }
                    }

                }
                if(ratingId<0){
                    embedBuilder.setDescription("**Attention** Whops..The rating is invalid!");
                }else
                if(ratingId==0||gTextChannel.isNSFW()){
                    if(selectedItem.has(keySubmissionFileUrlScreen)&&!selectedItem.isNull(keySubmissionFileUrlScreen)){
                        embedBuilder.setTitle(title,gUrlSubmissionPage.replaceAll("%",submissionId));
                        embedBuilder.addField("Author","["+username+"]("+gUrlUserPage.replaceAll("%",username)+")",false);
                        embedBuilder.setImage(selectedItem.getString(keySubmissionFileUrlScreen));
                    }else{
                        embedBuilder.setDescription("**Attention** Whops..No image found!");
                    }
                }else{
                    logger.warn(fName+"channel not nsfw");
                    embedBuilder.setDescription("**Attention** The NSFW image can only be displayed on NSFW channels!");
                }
                //embedBuilder.addField("Important","Report it if it violates rules 2 our staff!",false);
                return embedBuilder;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);return  null;
            }
        }
        private void reactionMenu(){
            String fName="[reactionMenu]";
            logger.info(fName);
            try{
                Message message=llSendMessageResponse(gTextChannel,createEmbedDisplay());
                if(gallery.length()>1){
                    int index=selectedItem.getInt(keyIndex);
                    if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                    if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                    if(index< gallery.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton)).queue();}
                    if(index!= gallery.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                    int finalIndex = index;
                    logger.info(fName+"prepare wait");
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    String asName=e.getReactionEmote().getName();
                                    String asEmoji=e.getReactionEmote().getEmoji();
                                    logger.info(fName+"nameCode="+nameCode);
                                    logger.info(fName+"asName="+asName);
                                    logger.info(fName+"asEmoji="+asEmoji);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex-1)>=0){
                                            selectedItem= gallery.getJSONObject(finalIndex-1);
                                            selectedItem.put(keyIndex,finalIndex-1);
                                            reactionMenu();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageClearReactions(e.getChannel(),e.getMessageId());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex+1)< gallery.length()) {
                                            selectedItem = gallery.getJSONObject(finalIndex + 1);
                                            selectedItem.put(keyIndex, finalIndex + 1);
                                            reactionMenu();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = gallery.getJSONObject(0);
                                        selectedItem.put(keyIndex,0);
                                        reactionMenu();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = gallery.getJSONObject(gallery.length()-1);
                                        selectedItem.put(keyIndex, gallery.length()-1);
                                        reactionMenu();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                        logger.info(fName+"do=delete");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                    }else{
                                        logger.info(fName+"do=invalid");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
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
                    logger.info(fName+"wait created");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void getViewPage(String address) {
            String fName = "[getViewPage]";
            try {
                logger.info(fName+".address ="+address);
                String viewId=address;
                if(viewId.toLowerCase().contains("https:")||viewId.toLowerCase().contains("http:")){
                    logger.info(fName+".mode given address");
                    viewId=viewId.replace("https:","").replace("http:","").replace("//","").replace("www","");
                    if(viewId.toLowerCase().contains("inkbunny.net/s/")||viewId.toLowerCase().contains("www.inkbunny.net/s/")){
                        viewId=viewId.replace("inkbunny.net/s/","");
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid address", llColorRed_Chili);
                    }
                }else{
                    logger.info(fName+".mode given id");
                    viewId=address;
                }
                logger.info(fName+".viewId ="+viewId);
                doHtmlCheckup();
                getHtmlSubmissionsView(viewId);
                if(gallery.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);return;
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+ gallery.length());
                if(gallery.length()>1){
                    i=getRandomSlot(gallery.length());
                    selectedItem= gallery.getJSONObject(i);
                    selectedItem.put(keyIndex,i);
                    reactionMenu();
                }else{
                    selectedItem= gallery.getJSONObject(i);
                    llSendMessage(gTextChannel,createEmbedDisplay());
                }
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
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

    static public JSONObject getImageJSONy(lcGlobalHelper global,String viewId) {
        String fName = "[getImageJSON]";Logger logger = Logger.getLogger(inkbunny.class);
        try{
            lcINKBUNNY lcInkbunny =new lcINKBUNNY(global);
            logger.info(fName+".viewId ="+viewId);
            String sid= lcInkbunny.doInkbunnyHtmlCheckup(global);
            if(sid==null||sid.isBlank()){
                return null;
            }
            lcInkbunny.setSid(sid);
            JSONObject listmimetype;
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
            HttpResponse<JsonNode> response= lcInkbunny.reqGetImage(viewId);
            int status=response.getStatus();
            if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                return null;}
            JsonNode body=response.getBody();
            logger.debug(fName+".body ="+body.toPrettyString());
            JSONObject json=body.getObject();
            logger.debug(fName+".json= ="+json.toString());
            return json;

        } catch (Exception e) {
            logger.error(fName+"exception:"+e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }


}
