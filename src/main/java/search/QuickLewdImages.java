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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import search.entities.lcFURAFFINITY;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuickLewdImages extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {

    Logger logger = Logger.getLogger(getClass()); String cName="[QuickImageSearcher]";
    lcGlobalHelper gGlobal;
    String gTitle="QuickImageSearcher";String gCommand ="^";

    public QuickLewdImages(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Get images from e621";
        this.aliases = new String[]{gCommand,"qi","quickimage"};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;this.hidden=true;
    }
    public QuickLewdImages(lcGlobalHelper global, GuildMessageReceivedEvent event){
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
        CommandEvent gEvent; GuildMessageReceivedEvent gGuildMessageReceivedEvent;
        User gUser;Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;

        public runLocal(CommandEvent ev) {
            String fName = "[run]";
            logger.info(".runLocal");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(GuildMessageReceivedEvent ev) {
            String fName = "[run]";
            logger.info(".runLocal");
            gGuildMessageReceivedEvent = ev;
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
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"QuickLewdImages",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand = true;

                if(gGuildMessageReceivedEvent!=null){
                    if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        return;
                    }
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw().replaceFirst("\\^","");
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    items = args.split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    int i=getRandomSlot(100);
                    int i2=i%2;
                    logger.info(fName + "i=" + i+", i2="+i2);
                    if(i2==0){
                        logger.info(fName + ".search on=e621");
                        if(items[0].equalsIgnoreCase("chastity")){
                            doSearchE621("chastity_device chastity_cage");
                        }
                        else if(items[0].equalsIgnoreCase("bondage")){
                            doSearchE621("bondage");
                        }
                        else if(items[0].equalsIgnoreCase("pet")){
                            doSearchE621("petplay");
                        }
                        else if(items[0].equalsIgnoreCase("pup")||items[0].equalsIgnoreCase("puppy")){
                            doSearchE621("puppyplay");
                        }
                        else if(items[0].equalsIgnoreCase("pony")){
                            doSearchE621("ponyplay");
                        }
                        else if(items[0].equalsIgnoreCase("yiffy")){
                            doSearchE621("yiff");
                        }
                        else if(items[0].equalsIgnoreCase("yiff")
                                ||items[0].equalsIgnoreCase("straitjacket")
                                ||items[0].equalsIgnoreCase("suck")
                                ||items[0].equalsIgnoreCase("kiss")
                                ||items[0].equalsIgnoreCase("bang")
                                ||items[0].equalsIgnoreCase("cuddle")
                                ||items[0].equalsIgnoreCase("finger")
                                ||items[0].equalsIgnoreCase("group")
                                ||items[0].equalsIgnoreCase("hug")
                                ||items[0].equalsIgnoreCase("hold")
                                ||items[0].equalsIgnoreCase("tease")
                                ||items[0].equalsIgnoreCase("toys")
                                ||items[0].equalsIgnoreCase("creampie")
                                ||items[0].equalsIgnoreCase("cuntboy")
                                ||items[0].equalsIgnoreCase("anal")
                                ||items[0].equalsIgnoreCase("femboy")
                                ||items[0].equalsIgnoreCase("femboys")
                                ||items[0].equalsIgnoreCase("bulge")
                                ||items[0].equalsIgnoreCase("ride")
                                ||items[0].equalsIgnoreCase("lesbian")
                                ||items[0].equalsIgnoreCase("gay")
                                ||items[0].equalsIgnoreCase("pussy")
                                ||items[0].equalsIgnoreCase("trap")
                                ||items[0].equalsIgnoreCase("traps")
                                ||items[0].equalsIgnoreCase("ass")
                        ){
                            doSearchE621(items[0]);
                        }
                        else {
                            doSearchE621(gEvent.getArgs());
                        }

                    }else{
                        logger.info(fName + ".search on=FA");
                        valueFirstPage= String.valueOf(getRandomSlot(10));
                        logger.info(fName + "valueFirstPage:" + valueFirstPage);
                        if(items[0].equalsIgnoreCase("chastity")){
                            doSearchFA(items[0]);
                        }
                        else if(items[0].equalsIgnoreCase("bondage")){
                            doSearchFA(items[0]);
                        }
                        else if(items[0].equalsIgnoreCase("pet")){
                            doSearchFA("petplay");
                        }
                        else if(items[0].equalsIgnoreCase("pup")||items[0].equalsIgnoreCase("puppy")){
                            doSearchFA("puppyplay");
                        }
                        else if(items[0].equalsIgnoreCase("pony")){
                            doSearchFA("ponyplay");
                        }
                        else if(items[0].equalsIgnoreCase("yiffy")){
                            doSearchFA("yiff");
                        }
                        else if(items[0].equalsIgnoreCase("yiff")
                                ||items[0].equalsIgnoreCase("straitjacket")
                                ||items[0].equalsIgnoreCase("suck")
                                ||items[0].equalsIgnoreCase("kiss")
                                ||items[0].equalsIgnoreCase("bang")
                                ||items[0].equalsIgnoreCase("cuddle")
                                ||items[0].equalsIgnoreCase("finger")
                                ||items[0].equalsIgnoreCase("group")
                                ||items[0].equalsIgnoreCase("hug")
                                ||items[0].equalsIgnoreCase("hold")
                                ||items[0].equalsIgnoreCase("tease")
                                ||items[0].equalsIgnoreCase("toys")
                                ||items[0].equalsIgnoreCase("creampie")
                                ||items[0].equalsIgnoreCase("cuntboy")
                                ||items[0].equalsIgnoreCase("anal")
                                ||items[0].equalsIgnoreCase("femboy")
                                ||items[0].equalsIgnoreCase("femboys")
                                ||items[0].equalsIgnoreCase("bulge")
                                ||items[0].equalsIgnoreCase("ride")
                                ||items[0].equalsIgnoreCase("lesbian")
                                ||items[0].equalsIgnoreCase("gay")
                                ||items[0].equalsIgnoreCase("pussy")
                                ||items[0].equalsIgnoreCase("trap")
                                ||items[0].equalsIgnoreCase("traps")
                                ||items[0].equalsIgnoreCase("ass")
                        ){
                            doSearchFA(items[0]);
                        }
                        else {
                            doSearchFA(gEvent.getArgs());
                        }
                    }
                }else {
                    if (gEvent.getArgs().isEmpty()) {
                        logger.info(fName + ".Args=0");
                        help("main");
                        isInvalidCommand = false;
                    } else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);
                        if (items[0].equalsIgnoreCase("help")) {
                            help("main");
                            isInvalidCommand = false;
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
                        else {
                            if (!isNSFW()) {
                                blocked();
                                return;
                            }
                            int i = getRandomSlot(100);
                            int i2 = i % 2;
                            logger.info(fName + "i=" + i + ", i2=" + i2);
                            if (i2 == 0) {
                                logger.info(fName + ".search on=e621");
                                if (items[0].equalsIgnoreCase("chastity")) {
                                    doSearchE621("chastity_device chastity_cage");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("bondage")) {
                                    doSearchE621("bondage");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pet")) {
                                    doSearchE621("petplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pup") || items[0].equalsIgnoreCase("puppy")) {
                                    doSearchE621("puppyplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pony")) {
                                    doSearchE621("ponyplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("yiffy")) {
                                    doSearchE621("yiff");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("yiff")
                                        || items[0].equalsIgnoreCase("straitjacket")
                                        || items[0].equalsIgnoreCase("suck")
                                        || items[0].equalsIgnoreCase("kiss")
                                        || items[0].equalsIgnoreCase("bang")
                                        || items[0].equalsIgnoreCase("cuddle")
                                        || items[0].equalsIgnoreCase("finger")
                                        || items[0].equalsIgnoreCase("group")
                                        || items[0].equalsIgnoreCase("hug")
                                        || items[0].equalsIgnoreCase("hold")
                                        || items[0].equalsIgnoreCase("tease")
                                        || items[0].equalsIgnoreCase("toys")
                                        || items[0].equalsIgnoreCase("creampie")
                                        || items[0].equalsIgnoreCase("cuntboy")
                                        || items[0].equalsIgnoreCase("anal")
                                        || items[0].equalsIgnoreCase("femboy")
                                        || items[0].equalsIgnoreCase("femboys")
                                        || items[0].equalsIgnoreCase("bulge")
                                        || items[0].equalsIgnoreCase("ride")
                                        || items[0].equalsIgnoreCase("lesbian")
                                        || items[0].equalsIgnoreCase("gay")
                                        || items[0].equalsIgnoreCase("pussy")
                                        || items[0].equalsIgnoreCase("trap")
                                        || items[0].equalsIgnoreCase("traps")
                                        || items[0].equalsIgnoreCase("ass")
                                ) {
                                    doSearchE621(items[0]);
                                    isInvalidCommand = false;
                                } else {
                                    doSearchE621(gEvent.getArgs());
                                    isInvalidCommand = false;
                                }
                            } else {
                                logger.info(fName + ".search on=FA");
                                valueFirstPage = String.valueOf(getRandomSlot(10));
                                logger.info(fName + "valueFirstPage:" + valueFirstPage);
                                if (items[0].equalsIgnoreCase("chastity")) {
                                    doSearchFA(items[0]);
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("bondage")) {
                                    doSearchFA(items[0]);
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pet")) {
                                    doSearchFA("petplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pup") || items[0].equalsIgnoreCase("puppy")) {
                                    doSearchFA("puppyplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("pony")) {
                                    doSearchFA("ponyplay");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("yiffy")) {
                                    doSearchFA("yiff");
                                    isInvalidCommand = false;
                                } else if (items[0].equalsIgnoreCase("yiff")
                                        || items[0].equalsIgnoreCase("straitjacket")
                                        || items[0].equalsIgnoreCase("suck")
                                        || items[0].equalsIgnoreCase("kiss")
                                        || items[0].equalsIgnoreCase("bang")
                                        || items[0].equalsIgnoreCase("cuddle")
                                        || items[0].equalsIgnoreCase("finger")
                                        || items[0].equalsIgnoreCase("group")
                                        || items[0].equalsIgnoreCase("hug")
                                        || items[0].equalsIgnoreCase("hold")
                                        || items[0].equalsIgnoreCase("tease")
                                        || items[0].equalsIgnoreCase("toys")
                                        || items[0].equalsIgnoreCase("creampie")
                                        || items[0].equalsIgnoreCase("cuntboy")
                                        || items[0].equalsIgnoreCase("anal")
                                        || items[0].equalsIgnoreCase("femboy")
                                        || items[0].equalsIgnoreCase("femboys")
                                        || items[0].equalsIgnoreCase("bulge")
                                        || items[0].equalsIgnoreCase("ride")
                                        || items[0].equalsIgnoreCase("lesbian")
                                        || items[0].equalsIgnoreCase("gay")
                                        || items[0].equalsIgnoreCase("pussy")
                                        || items[0].equalsIgnoreCase("trap")
                                        || items[0].equalsIgnoreCase("traps")
                                        || items[0].equalsIgnoreCase("ass")
                                ) {
                                    doSearchFA(items[0]);
                                    isInvalidCommand = false;
                                } else {
                                    doSearchFA(gEvent.getArgs());
                                    isInvalidCommand = false;
                                }
                            }
                        }


                    }
                    llMessageDelete(gEvent);
                    if (isInvalidCommand) {
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "You provided an incorrect command!", llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(".run ended");
        }
        String valueFirstPage="";
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
            embedBuilder.addField("How to use" ,"User `"+llPrefixStr+ gCommand +" [tag]` to display image with that tag",false);
            embedBuilder.addField("How to use the reactions bellow the preview" ,"Reactions allow you to move between images, post it or cancel it.\nThe arrows left&right allows to move between images.\nThe down arrow means you post this image to channel else it auto deletes after timeout\nAnd the :x: simple cancels the preview.",false);
            embedBuilder.addField("NSFW content","They can only be displayed in NSFW channels.",false);
            embedBuilder.setColor(llColorPurple2);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+llPrefixStr+gCommand+" guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }
        private int getRandomSlot(int i){
            Random rand = new Random();// Generate random integers in range 0 to i
            int j=rand.nextInt(i);
            if(j<=0)j=1;
            return j;
        }
        String gUrlE621 ="https://e621.net";
        public JSONObject setOptionsE621(String message) {
            String fName = "[setOptionsE621]"; logger.info(fName); JSONObject response=new JSONObject();
            try{

                logger.info(fName+"message="+message);
                String rating="e";response.put("rating","e");

                String tags = "";response.put("tags","");
                String[] items=message.split("\\s+");
                for(String item:items){
                    if(!item.equalsIgnoreCase("explicit")&&!item.equalsIgnoreCase("safe")){
                        if(tags.isEmpty()){
                            tags=URLEncoder.encode(item, StandardCharsets.UTF_8.toString());
                        }else{
                            tags+="+"+URLEncoder.encode(item, StandardCharsets.UTF_8.toString());
                        }
                    }
                }
                response.put("tags",tags);
                if(tags.toLowerCase().contains("cub")||tags.toLowerCase().contains("kid")||tags.toLowerCase().contains("child")||tags.toLowerCase().contains("baby")){
                    logger.warn(fName+"contains ban words");response.put("error","ban words");return response;
                    //llQuickEmbedChannelResponse(gTextChannel,gTitle,"Contains blackwords!",llColorRed);
                }
                String limit="320";
                String option="?"; response.put("option","");
                option+="limit="+limit;
                response.put("limit",limit);
                if(!rating.isEmpty()||!tags.isEmpty()){
                    if(!tags.isEmpty()&&!rating.isEmpty()){
                        option+="&tags="+tags+"+rating:"+rating;
                    }else
                    if(!tags.isEmpty()){
                        option+="&tags="+tags;
                    }else{
                        option+="&rating:"+rating;
                    }
                }
                response.put("option",option);
                logger.info(fName+"option="+option);
                response.put("done",true);
                return response;
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed); response.put("error","exception"); return response;
            }

        }
        private void doSearchE621(String input) {
            //https://e621.net/posts.json?tags=fluffy+rating:s&limit=2
            String fName = "[doSearchWithDisplayE621]";
            try {
                logger.info(fName+"input="+input);
                JSONObject jsonOptions= setOptionsE621(input);
                if(!jsonOptions.has("done")){
                    logger.error(fName+".options error");
                    if(jsonOptions.has("error")){
                        logger.error(fName+".options error="+jsonOptions.getString("error"));
                        if(jsonOptions.getString("error").equalsIgnoreCase("ban words")){
                            logger.error(fName+".ban words"); llSendQuickEmbedMessage(gTextChannel,gTitle,"Contains forbidden words!",llColorRed); return;
                        }
                    }
                }
                if(jsonOptions.has("rating")){
                    if(jsonOptions.getString("rating").equalsIgnoreCase("e")&&!gTextChannel.isNSFW()){
                        logger.warn(fName+"not nsfw channel>return");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"This is not a nsfw channel!",llColorRed);return;
                    }
                }
                String option="";
                if(jsonOptions.has("option")){
                    option=jsonOptions.getString("option");
                }
                if(option==null||option.isEmpty()){
                    logger.error(fName+".no options added"); llSendQuickEmbedMessage(gTextChannel,gTitle,"No options added",llColorRed); return;
                }
                getHtmlGalleryE621(jsonOptions);
                if(gallery.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);return;
                }
                int i=0;
                logger.info(fName+"gallery.length="+gallery.length());
                if(gallery.length()>1){i=getRandomSlot(gallery.length());}
                selectedItem=gallery.getJSONObject(i);
                selectedItem.put(keyIndex,i);
                reactionMenuE621();
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        JSONArray gallery=new JSONArray();
        JSONObject selectedItem=new JSONObject();
        String keyIndex="index";
        private void getHtmlGalleryE621(JSONObject jsonOptions) {
            //https://e621.net/posts.json?tags=fluffy+rating:s&limit=2
            String fName = "[getHtmlGallery]";
            try {
                logger.info(fName+"jsonOptions="+jsonOptions);
                if(!jsonOptions.has("done")){
                    logger.error(fName+".options error");
                    if(jsonOptions.has("error")){
                        logger.error(fName+".options error="+jsonOptions.getString("error"));
                    }
                }
                String option="";
                if(jsonOptions.has("option")){
                    option=jsonOptions.getString("option");
                }
                if(option==null||option.isEmpty()){
                    logger.error(fName+".no options added");return;
                }
                logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator());
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlE621 +"/posts.json"+option;
                logger.info(fName+"option="+option);
                logger.info(fName + ".url="+url);
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
                                if(json.isEmpty()||!json.has("posts")||json.isNull("posts")){
                                    logger.error(fName+".no posts");return;
                                }
                                JSONArray array=json.getJSONArray("posts");
                                if(array.length()<=0){ logger.error(fName+".array is zero");
                                    return;}
                                int index=0;
                                while(index<array.length()){
                                    boolean isCompatible=true; boolean isTagsOK=true;
                                    JSONObject object=null;
                                    String ext=null;
                                    object=array.getJSONObject(index);index++; isTagsOK=true;
                                    if(object.has("id")){
                                        if(object.has("tags")&&!object.isNull("tags")){
                                            if(object.getJSONObject("tags").has("general")&&!object.getJSONObject("tags").isNull("general")){
                                                JSONArray tags=object.getJSONObject("tags").getJSONArray("general");
                                                for(int i=0;i<tags.length();i++){
                                                    String tag=tags.getString(i);
                                                    if(tag.toLowerCase().contains("cub")||tag.toLowerCase().contains("kid")||tag.toLowerCase().contains("child")||tag.toLowerCase().contains("baby")){
                                                        logger.warn(fName+"contains ban words");
                                                        isTagsOK=false;
                                                    }
                                                }
                                            }
                                        }
                                        if(isTagsOK){
                                            if(object.has("file")&&!object.isNull("file")&&object.getJSONObject("file").has("ext")&&!object.getJSONObject("file").isNull("ext")){
                                                ext=object.getJSONObject("file").getString("ext");
                                                if(ext.contains("webm")||ext.contains("swf"))isCompatible=false;
                                            }
                                        }
                                    }
                                    if(!isTagsOK){
                                        logger.warn(fName+"contains ban words");
                                    }else
                                    if(!isCompatible){
                                        logger.warn(fName+"not compatible");
                                    }else{
                                        gallery.put(object);
                                    }
                                }
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
        private void reactionMenuE621(){
            String fName="[reactionMenuE621]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                int index=-1;
                if(selectedItem.has(keyIndex)){
                    index=selectedItem.getInt(keyIndex);
                }
                JSONArray artists=null;
                String rating=null;
                String score=null;
                String ext=null;
                String img=null;
                String id=null;
                boolean isBanWordsInTags=false,isCompatible=true;
                JSONArray tags_general=new JSONArray();
                if(selectedItem.has("rating")){ rating=selectedItem.getString("rating");}
                if(selectedItem.has("tags")&&!selectedItem.isNull("tags")){
                    if(selectedItem.getJSONObject("tags").has("general")&&!selectedItem.getJSONObject("tags").isNull("general")) {
                        tags_general = selectedItem.getJSONObject("tags").getJSONArray("general");
                    }
                }
                for(int i=0;i<tags_general.length();i++){
                    String tag=tags_general.getString(i);
                    if(tag.toLowerCase().contains("cub")||tag.toLowerCase().contains("kid")||tag.toLowerCase().contains("child")||tag.toLowerCase().contains("baby")){
                        logger.warn(fName+"contains ban words");
                        isBanWordsInTags=true;
                    }
                }
                if(selectedItem.has("file")&&!selectedItem.isNull("file")&&selectedItem.getJSONObject("file").has("ext")&&!selectedItem.getJSONObject("file").isNull("ext")){
                    ext=selectedItem.getJSONObject("file").getString("ext");
                    if(ext.contains("webm")||ext.contains("swf"))isCompatible=false;
                }

                if(isBanWordsInTags){
                    embed.setColor(llColorRed_Cinnabar);
                    logger.warn(fName+"ban tags");
                    embed.setDescription("Contains ban words in tags!");
                }
                else if(!isCompatible){
                    embed.setColor(llColorRed_Cinnabar);
                    logger.warn(fName+"not compatible image");
                    embed.setDescription("Is not compatible to view!");
                }
                else if((rating.equalsIgnoreCase("q")||rating.equalsIgnoreCase("e"))&&!gTextChannel.isNSFW()){
                    logger.warn(fName+"channel not nsfw");
                    embed.setDescription("**Attention** The NSFW image can only be displayed on NSFW channels!");
                } else{
                    if(selectedItem.has("id")) { id = selectedItem.getString("id"); }
                    if(selectedItem.has("artist")){ artists=selectedItem.getJSONArray("artist");}
                    if(selectedItem.has("file")&&!selectedItem.isNull("file")&&selectedItem.getJSONObject("file").has("url")&&!selectedItem.getJSONObject("file").isNull("url")){ img=selectedItem.getJSONObject("file").getString("url");}
                    //embed.setTitle(gTitle);
                    embed.setColor(llColorPurple2);
                    if(id!=null){
                        //[Link](https://e621.net/post/show/"+id+"
                        //embed.addField("Link","["+id+"](https://e621.net/post/show/"+id+")",true);
                        embed.setTitle(id,"https://e621.net/post/show/"+id);
                    }
                    if(artists!=null&&artists.length()>=1){
                        embed.addField("Artist",artists.getString(0),true);
                    }
                    if(img!=null){
                        embed.setImage(img);
                    }
                }
                //embed.addField("Important","Report it if it violates rules 2 our staff!",false);
                //embed.addField("How to use the reactions bellow the preview" ,"The arrows left&right allows to move between images.\nThe down arrow means you post this image to channel else it auto deletes after timeout\nAnd the :x: simple cancels the preview.",false);
                Message message=llSendMessageResponse(gTextChannel,embed);
                if(gallery.length()>1){
                    if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                    if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                    if(index<gallery.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton)).queue();}
                    if(index!=gallery.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                    int finalIndex = index;
                    logger.info(fName+"prepare wait");
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    logger.info(fName+"nameCode="+nameCode);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex-1)>=0){
                                            selectedItem=gallery.getJSONObject(finalIndex-1);
                                            selectedItem.put(keyIndex,finalIndex-1);
                                            reactionMenuE621();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageClearReactions(e.getChannel(),e.getMessageId());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex+1)<gallery.length()) {
                                            selectedItem = gallery.getJSONObject(finalIndex + 1);
                                            selectedItem.put(keyIndex, finalIndex + 1);
                                            reactionMenuE621();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = gallery.getJSONObject(0);
                                        selectedItem.put(keyIndex,0);
                                        reactionMenuE621();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = gallery.getJSONObject(gallery.length()-1);
                                        selectedItem.put(keyIndex,gallery.length()-1);
                                        reactionMenuE621();
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
        JSONObject options;
        private void setOptionsFA(String message) {
            String fName = "[setOptionsFA]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                options.put(lcFURAFFINITY.INTERFACE.keyPage, lcFURAFFINITY.INTERFACE.valueFirstPage);options.put(lcFURAFFINITY.INTERFACE.keyResetPage, lcFURAFFINITY.INTERFACE.valueDefaultResetPage);options.put(lcFURAFFINITY.INTERFACE.keyRange, lcFURAFFINITY.INTERFACE.valueRangeAll);
                options.put(lcFURAFFINITY.INTERFACE.keyOne, lcFURAFFINITY.INTERFACE.valueFirstOne);options.put(lcFURAFFINITY.INTERFACE.keyMode, lcFURAFFINITY.INTERFACE.valueModeExtended);options.put(lcFURAFFINITY.INTERFACE.keyTypeArt, lcFURAFFINITY.INTERFACE.valueOn);
                options.put(lcFURAFFINITY.INTERFACE.keyDoSearch, lcFURAFFINITY.INTERFACE.valueDoSearch); options.put(lcFURAFFINITY.INTERFACE.keyOrderBy, lcFURAFFINITY.INTERFACE.valueOrderByPopularity);options.put(lcFURAFFINITY.INTERFACE.keyOrderDirection, lcFURAFFINITY.INTERFACE.valueDesc);
                options.put(lcFURAFFINITY.INTERFACE.keyRatingMature, lcFURAFFINITY.INTERFACE.valueOn);options.put(lcFURAFFINITY.INTERFACE.keyRatingAdult, lcFURAFFINITY.INTERFACE.valueOn);
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueAsc)){options.put(lcFURAFFINITY.INTERFACE.keyOrderDirection, lcFURAFFINITY.INTERFACE.valueAsc);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueOrderByDate)){options.put(lcFURAFFINITY.INTERFACE.keyOrderBy, lcFURAFFINITY.INTERFACE.valueOrderByDate);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueOrderByRelevancy)){options.put(lcFURAFFINITY.INTERFACE.keyOrderBy, lcFURAFFINITY.INTERFACE.valueOrderByRelevancy);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueModeAll)){options.put(lcFURAFFINITY.INTERFACE.keyMode, lcFURAFFINITY.INTERFACE.valueModeAll);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueModeAny)){options.put(lcFURAFFINITY.INTERFACE.keyMode, lcFURAFFINITY.INTERFACE.valueModeAny);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueRangeDay)){options.put(lcFURAFFINITY.INTERFACE.keyRange, lcFURAFFINITY.INTERFACE.valueRangeDay);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueRange3Days)||message.toLowerCase().contains("3 days")||message.toLowerCase().contains("days")){options.put(lcFURAFFINITY.INTERFACE.keyRange, lcFURAFFINITY.INTERFACE.valueRange3Days);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueRangeWeek)){options.put(lcFURAFFINITY.INTERFACE.keyRange, lcFURAFFINITY.INTERFACE.valueRangeWeek);}
                if(message.toLowerCase().contains(lcFURAFFINITY.INTERFACE.valueRangeMonth)){options.put(lcFURAFFINITY.INTERFACE.keyRange, lcFURAFFINITY.INTERFACE.valueRangeMonth);}
                String convertedMessage=message.toLowerCase().replaceAll(lcFURAFFINITY.INTERFACE.valueMature,"").replaceAll(lcFURAFFINITY.INTERFACE.valueAdult,"").replaceAll(lcFURAFFINITY.INTERFACE.valueGeneral,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(lcFURAFFINITY.INTERFACE.valueAsc,"").replaceAll(lcFURAFFINITY.INTERFACE.valueDesc,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(lcFURAFFINITY.INTERFACE.valueOrderByDate,"").replaceAll(lcFURAFFINITY.INTERFACE.valueOrderByPopularity,"").replaceAll(lcFURAFFINITY.INTERFACE.valueOrderByRelevancy,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(lcFURAFFINITY.INTERFACE.valueModeAll,"").replaceAll(lcFURAFFINITY.INTERFACE.valueModeAny,"").replaceAll(lcFURAFFINITY.INTERFACE.valueModeExtended,"");
                convertedMessage=convertedMessage.toLowerCase().replaceAll(lcFURAFFINITY.INTERFACE.valueRangeAll,"").replaceAll(lcFURAFFINITY.INTERFACE.valueRange3Days,"").replaceAll(lcFURAFFINITY.INTERFACE.valueRangeDay,"").replaceAll(lcFURAFFINITY.INTERFACE.valueRangeMonth,"").replaceAll(lcFURAFFINITY.INTERFACE.valueRangeWeek,"").replaceAll("days","").replaceAll("3 days","");
                String begin="page(",textShadow;int i,j,l;
                if(convertedMessage.contains(begin)){
                    logger.info(fName+"found=page");
                    i=convertedMessage.indexOf(begin);
                    l=begin.length();
                    j=convertedMessage.indexOf(")");
                    options.put(lcFURAFFINITY.INTERFACE.keyPage,convertedMessage.substring(i+l,j));
                    StringBuffer stringBuffer = new StringBuffer(convertedMessage);
                    stringBuffer.replace( i ,j+1,"");
                    logger.info(fName+"page="+options.getString(lcFURAFFINITY.INTERFACE.keyPage));
                    convertedMessage=stringBuffer.toString();
                    logger.info(fName+"convertedMessage="+convertedMessage);
                    options.remove(lcFURAFFINITY.INTERFACE.keyOne);options.remove(lcFURAFFINITY.INTERFACE.keyResetPage);
                }
                options.put(lcFURAFFINITY.INTERFACE.keyQ,convertedMessage);
                logger.info(fName+"options="+options);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }

        }
        private void getHtmlGallerySearchFA() {
            String fName = "[getHtmlGallerySearchFA]";
            try {
                Unirest a= new Unirest();
                //a.config().verifySsl(false);
                //HttpResponse<String> jsonResponse =a.post(gUrl).field(keyQ,options.getString(keyQ)).asString();
                Map<String,Object> mapOptions=new LinkedHashMap<>();
                Iterator<String> keys=options.keys();
                while(keys.hasNext()){
                    String key=keys.next();
                    Object value=options.get(key);
                    mapOptions.put(key,value);
                }
                int i=0;
                for (Map.Entry<String, Object> entry : mapOptions.entrySet()) {
                    logger.info(fName+".mapOptions["+i+"]="+entry.getKey()+ ":" + entry.getValue().toString());i++;
                }
                if( options.has(lcFURAFFINITY.INTERFACE.keyQ)){
                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>"+options.getString(lcFURAFFINITY.INTERFACE.keyQ));
                }else{
                    logger.info(fName + "do search by: " + gUser.getId() + "|" + gUser.getName()+"#"+gUser.getDiscriminator()+"=>undefined");
                }
                lcFURAFFINITY furaffinity=new lcFURAFFINITY(gGlobal);
                HttpResponse<String> jsonResponse=furaffinity.reqGetGallerySearch(mapOptions);
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status"); return ;
                }
                String body=jsonResponse.getBody();
                logger.info(fName+".body ="+body);
                String text="", textShadow="";
                int itemsI=-1,itemsJ=-1;
                if(body.contains("gallery-search-results")){
                    itemsI=body.indexOf("gallery-search-results");
                    textShadow=body.substring(itemsI);
                    itemsJ=textShadow.indexOf("/section");
                    if(itemsI>0&&itemsJ>0){
                        text=textShadow.substring(0,itemsJ-1);
                    }
                }
                text=text.replaceAll("</figure><!--","").replaceAll("-->","").replaceFirst("<figure","");
                logger.info(fName+".gallery-search-results ="+text);
                String [] items = text.split("<figure");
                jsonItems=new JSONArray();
                for(String item : items){
                    JSONObject jsonObject= convertText2JSONItemFA(item);
                    if(jsonObject.getString(keyItemType).equalsIgnoreCase("image")){
                        jsonItems.put(jsonObject);
                    }
                }

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);return ;
            }
        }
        JSONArray jsonItems=new JSONArray();
        String keyItemId="id",keyItemRating="rating",keyItemType="type",keyItemPage="page",keyItemUser="user",keyItemPreview="preview",keyItemTitle="title",keyItemUserName="userName";
        private JSONObject convertText2JSONItemFA(String text){
            String fName = "[convertText2JSONItemFA]"; logger.info(fName);options=new JSONObject();
            try{
                JSONObject jsonObject=new JSONObject();
                text="."+text;
                logger.info(fName+".text="+text);
                int i=-1,j=-1,l=1;
                String textShadow="";
                String begin="id=\"sid-";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\" class=");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemId,textShadow.substring(0,j));
                    }
                }
                begin="src=\"//";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("\"");
                    if(i>0&&j>0){
                        jsonObject.put(keyItemPreview,textShadow.substring(0,j));
                    }
                }
                if(text.contains("r-general")){ jsonObject.put(keyItemRating,0);}
                else if(text.contains("r-mature")){ jsonObject.put(keyItemRating,1);}
                else if(text.contains("r-adult")){ jsonObject.put(keyItemRating,2);}
                else { jsonObject.put(keyItemRating,-1);}
                if(text.contains("t-image")){ jsonObject.put(keyItemType,"image");}
                else{jsonObject.put(keyItemType,"invalid");}

                begin="href=\"/view/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf( begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemPage, textShadow.substring(0, j));
                    }
                    begin="/\" title=\"";
                    if( textShadow.contains(begin)&& textShadow.contains("href=\"/user/")){
                        i= textShadow.indexOf(begin);
                        int i2= textShadow.indexOf("href=\"/user/");
                        logger.info(fName+".i<i2: "+i+" < "+i2);
                        if(i<i2){
                            l=begin.length();
                            textShadow= textShadow.substring(i+l);
                            j=textShadow.indexOf("\"");
                            if(i>0&&j>0) {
                                jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                            }
                        }else{
                            logger.warn(fName+".invalid i<i2: "+i+" < "+i2);
                        }
                    }else{
                        logger.warn(fName+".no author found");
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemTitle, textShadow.substring(0, j));
                        }
                    }
                }
                begin="href=\"/user/";
                if(text.contains(begin)){
                    i=-1;j=-1;
                    i=text.indexOf(begin);
                    l=begin.length();
                    textShadow=text.substring(i+l);
                    j=textShadow.indexOf("/\"");
                    if(i>0&&j>0) {
                        jsonObject.put(keyItemUser, textShadow.substring(0, j));
                    }
                    begin="title=\"";
                    if( textShadow.contains(begin)){
                        i= textShadow.indexOf(begin);
                        l=begin.length();
                        textShadow= textShadow.substring(i+l);
                        j=textShadow.indexOf("\"");
                        if(i>0&&j>0) {
                            jsonObject.put(keyItemUserName, textShadow.substring(0, j));
                        }
                    }
                }
                logger.info(fName+".json ="+jsonObject);
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        private void doSearchFA(String message) {
            String fName = "[doSearchFA]"; logger.info(fName);options=new JSONObject();
            try{
                logger.info(fName+"message="+message);
                setOptionsFA(message);
                getHtmlGallerySearchFA();
                if(jsonItems.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Nothing found!", llColorRed);return;
                }
                int i=0;
                logger.info(fName+"jsonItems.length="+jsonItems.length());
                if(jsonItems.length()>1){i=getRandomSlot(jsonItems.length());}
                selectedItem=jsonItems.getJSONObject(i);
                selectedItem.put(keyIndex,i);
                reactionMenuFA();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);

            }
        }
        private void reactionMenuFA(){
            String fName="[reactionMenuFA]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String title="####";
                int index=-1;
                if(selectedItem.has(keyIndex)){
                    index=selectedItem.getInt(keyIndex);
                }
                if(selectedItem.has(keyItemTitle)){
                    title=selectedItem.getString(keyItemTitle);
                }
                String username="####";
                if(selectedItem.has(keyItemUserName)){
                    username=selectedItem.getString(keyItemUserName);
                }
                String image="";
                if(selectedItem.has(keyItemPreview)){
                    logger.info(fName+"keyItemPreview="+selectedItem.getString(keyItemPreview));
                    image=selectedItem.getString(keyItemPreview);
                }
                if(selectedItem.has(keyItemPage)){
                    String url= lcFURAFFINITY.INTERFACE.gUrlView.replaceAll("%",selectedItem.getString(keyItemPage));
                    logger.info(fName+"url="+url);
                    lcFURAFFINITY furaffinity=new lcFURAFFINITY(gGlobal);
                    HttpResponse<String> jsonResponse =furaffinity.reqGetSubmissionViewOld(url);
                    logger.info(fName+".status ="+jsonResponse.getStatus());
                    if(jsonResponse.getStatus()<=200&&jsonResponse.getStatus()<300){
                        String body=jsonResponse.getBody();
                        //logger.info(fName+".body ="+body);
                        String textShadow="";
                        int itemsI=-1,itemsJ=-1,l;
                        String begin="img id=\"submissionImg";
                        if(body.contains(begin)){
                            logger.info(fName+".found:"+ begin);
                            itemsI=body.indexOf(begin);
                            l=begin.length();
                            textShadow=body.substring(itemsI);
                            itemsJ=textShadow.indexOf("\">");
                            begin="src=\"//";
                            if( textShadow.contains(begin)){
                                logger.info(fName+".found:"+ begin);
                                itemsI= textShadow.indexOf(begin);
                                l=begin.length();
                                textShadow= textShadow.substring(itemsI+l);
                                itemsJ=textShadow.indexOf("\"");
                                if(itemsI>0&&itemsJ>0) {
                                    image=textShadow.substring(0, itemsJ);
                                    logger.info(fName+"imageGot="+image);
                                }
                            }
                        }
                    }
                }
                logger.info(fName+"setImage="+image);
                if(gTextChannel.isNSFW()){
                    embed.setImage("http://"+image);
                    if(selectedItem.has(keyItemPage)){
                        //embed.addField("Title","["+title+"]("+gUrlView.replaceAll("%",selectedItem.getString(keyItemPage))+")",false);
                        embed.setTitle(title, lcFURAFFINITY.INTERFACE.gUrlView.replaceAll("%",selectedItem.getString(keyItemPage)));
                    }else{
                        //embed.addField("Title",title,false);
                        embed.setTitle(title);
                    }
                }else{
                    logger.warn(fName+"channel not nsfw");
                    embed.setDescription("**Attention** The NSFW image can only be displayed on NSFW channels!");
                }
                //embed.addField("Important","Report it if it violates rules 2 our staff!",false);
                //embed.addField("How to use the reactions bellow the preview" ,"The arrows left&right allows to move between images.\nThe down arrow means you post this image to channel else it auto deletes after timeout\nAnd the :x: simple cancels the preview.",false);
                Message message=llSendMessageResponse(gTextChannel,embed);
                if(jsonItems.length()>1){
                    if(index!=0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton)).queue();}
                    if(index>0){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
                    if(index<jsonItems.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton)).queue();}
                    if(index!=jsonItems.length()-1){message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton)).queue();}
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
                    int finalIndex = index;
                    logger.info(fName+"prepare wait");
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                            e -> {
                                try {
                                    String nameCode=e.getReactionEmote().getName();
                                    logger.info(fName+"nameCode="+nameCode);
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReverseButton))){
                                        logger.info(fName+"do=back");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex-1)>=0){
                                            selectedItem=jsonItems.getJSONObject(finalIndex-1);
                                            selectedItem.put(keyIndex,finalIndex-1);
                                            reactionMenuFA();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                        logger.info(fName+"do=print");
                                        llMessageClearReactions(e.getChannel(),e.getMessageId());
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        logger.info(fName+"do=next");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        if((finalIndex+1)<jsonItems.length()) {
                                            selectedItem = jsonItems.getJSONObject(finalIndex + 1);
                                            selectedItem.put(keyIndex, finalIndex + 1);
                                            reactionMenuFA();
                                        }
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLastTrackButton))){
                                        logger.info(fName+"do=first");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = jsonItems.getJSONObject(0);
                                        selectedItem.put(keyIndex,0);
                                        reactionMenuFA();
                                    }else
                                    if(nameCode.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNextTrackButton))){
                                        logger.info(fName+"do=last");
                                        llMessageDelete(e.getChannel(),e.getMessageId());
                                        selectedItem = jsonItems.getJSONObject(jsonItems.length()-1);
                                        selectedItem.put(keyIndex,jsonItems.length()-1);
                                        reactionMenuFA();
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
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
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

}
