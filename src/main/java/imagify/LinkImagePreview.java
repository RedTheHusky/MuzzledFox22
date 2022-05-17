package imagify;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Red;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;
import search.entities.lcE621;
import search.entities.lcFURAFFINITY;
import search.entities.lcINKBUNNY;
import search.entities.lcWEASYL;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class LinkImagePreview extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {

    Logger logger = Logger.getLogger(getClass()); String cName="[QuickImageSearcher]";
    lcGlobalHelper gGlobal;
    String gTitle="Link Image Preview";String gAliase="linkpreview";

    public LinkImagePreview(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Displaying image from art hosting website URLs.";
        this.aliases = new String[]{gAliase};
        this.guildOnly = true;this.category= llCommandCategory_Between;
    }
    public LinkImagePreview(lcGlobalHelper global, GuildMessageReceivedEvent event){
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
                String[] items;
                boolean isInvalidCommand = true;
                initProfile();
                if(gGuildMessageReceivedEvent!=null){
                    if(!gProfile.jsonObject.getBoolean(keyEnabled)){
                        logger.info(fName+"its not enabled");return;
                    }
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw();
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    boolean hasPrefix=false;
                    if(args.startsWith(llPrefixStr)){
                        hasPrefix=true;
                    }
                    if(lsUsefullFunctions.hasPrefix(args,gGuild.getSelfMember())){
                        hasPrefix=true;
                    }
                    logger.info(fName+"hasPrefix="+hasPrefix);
                    if(!gProfile.jsonObject.getBoolean(keyAutoPreviewEnabled)&&!hasPrefix){
                        logger.info(fName+"its not enabled to auto do url that starts without prefix");return;
                    }
                    selector(args);
                }else{
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");
                    }else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);
                        if(items[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand=false;
                        }else
                        if(items.length>=2){
                            if(!lsMemberHelper.lsMemberIsAdministrator(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsMemberIsBotOwner(gMember)){
                                logger.warn(fName + ".denied");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied", llColors_Red.llColorRed_Barn);
                                return;
                            }
                            boolean value=false, hasValue=false;
                            logger.info(fName + ".items[1]="+items[1]);
                            if(items[1].equalsIgnoreCase("1")||items[1].equalsIgnoreCase("true")){
                                value=true;hasValue=true;
                            }
                            if(items[1].equalsIgnoreCase("0")||items[1].equalsIgnoreCase("false")){
                                hasValue=true;
                            }
                            logger.info(fName + ".hasValue="+hasValue);
                            logger.info(fName + ".value="+value);
                            if(hasValue){
                                if(items[0].equalsIgnoreCase("enable")||items[0].equalsIgnoreCase("enabled")){
                                    setEnabled(value);isInvalidCommand=false;
                                }
                                else if(items[0].equalsIgnoreCase("auto")){
                                    setAuto(value);isInvalidCommand=false;
                                }
                                else  if(items[0].equalsIgnoreCase("ratingd")){
                                    setRatingDisplay(value);isInvalidCommand=false;
                                }
                            }else{
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                            }
                        }else
                        if(!gProfile.jsonObject.getBoolean(keyEnabled)){
                            logger.info(fName+"its not enabled");isInvalidCommand=false;
                        }else
                        if(items[0].equalsIgnoreCase("link")){
                            selector(gEvent.getArgs().replaceFirst("link","").trim());isInvalidCommand=false;
                        }else {
                            selector(gEvent.getArgs());isInvalidCommand=false;
                        }
                    }
                    //llMessageDelete(gEvent);
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
            String quick=llPrefixStr+gAliase+" ";
            EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle);
            embedBuilder.addField("How to use" ,"If set to auto, you can simple post the URL only. Also can use `"+llPrefixStr+" [URL]` in case auto is disabled do to other bots issues.",false);
            embedBuilder.addField("Supports" ,"Supports only displaying art from this sites:\n"+lsUsefullFunctions.getUrlTextString("Inkbunny","https://inkbunny.net")+"\n"+lsUsefullFunctions.getUrlTextString("FurAffinity","https://www.furaffinity.net")+"\n"+lsUsefullFunctions.getUrlTextString("Weasyl","https://www.weasyl.com")+"\n"+lsUsefullFunctions.getUrlTextString("DeviantArt","https://www.deviantart.com"),false);
            embedBuilder.addField("Also" ,"Will only displaying NSFW content as Discord already handles displaying general contents.",false);
            embedBuilder.addField("Setup" ,"Enabled: "+gProfile.jsonObject.getBoolean(keyEnabled)+"\nAuto: "+gProfile.jsonObject.getBoolean(keyAutoPreviewEnabled)+"\nDisplay Rating: "+gProfile.jsonObject.getBoolean(keyDisplayRating),false);
            embedBuilder.addField("Commands" ,"Use the following commands to change its behavior:\n`"+quick+"[enable] [1/0]`\n`"+quick+"[auto] [1/0]`\n`"+quick+"[ratingd] [1/0]`",false);

            embedBuilder.setColor(llColorPurple2);
            llSendMessage(gUser,embedBuilder);
        }
        private void setEnabled(boolean value) {
            String fName="[setEnabled]";
            logger.info(fName);
            try {
                String postId="";
                logger.info(fName+"value="+value);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gProfile.putFieldEntry(keyEnabled,value);
                if(value){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled to display a preview of URL.", llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Disabled to display a preview of URL.", llColors.llColorBlue1);
                }
               gProfile.isUpdated=true;
                saveProfile();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void setAuto(boolean value) {
            String fName="[setAuto]";
            logger.info(fName);
            try {
                String postId="";
                logger.info(fName+"value="+value);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gProfile.putFieldEntry(keyAutoPreviewEnabled,value);
                if(value){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled to display a preview if it starts with URL only.\nThis might conflict with other bots that handle URL(s)", llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Disabled to display a preview if it starts with URL only.", llColors.llColorBlue1);
                }
                gProfile.isUpdated=true;saveProfile();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void setRatingDisplay(boolean value) {
            String fName="[setRatingDisplay]";
            logger.info(fName);
            try {
                String postId="";
                logger.info(fName+"value="+value);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gProfile.putFieldEntry(keyDisplayRating,value);
                if(value){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled to display rating of preview.", llColors.llColorBlue1);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Disabled to display rating of preview.", llColors.llColorBlue1);
                }
                gProfile.isUpdated=true;saveProfile();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void selector(String address) {
            String fName="[selector]";
            logger.info(fName);
            try {
                String postId="";
                logger.info(fName+"address="+address);
                address=address.toLowerCase();
                address=address.replace(llPrefixStr,"").trim().replace("https://www.","").replace("http://www.","").replace("https://","").replace("http://","").replace("www.","").trim();
                logger.info(fName+"address="+address);
                //https://e621.net/posts/2432363
                //https://inkbunny.net/s/2267826
                if(address.contains("furaffinity.net/view/")&&!address.equals("furaffinity.net/view/")){
                    postId=address.replace("furaffinity.net/view/","").replace("/","");
                    logger.info(fName+"postId="+postId);
                    doPreviewFA(postId);
                }else
                if(address.contains("e621.net/post/")&&!address.equals("e621.net/post/")){
                    postId=address.replace("e621.net/post/","").replace("/","");
                    postId=postId.replace("/show","").replace("show","");
                    logger.info(fName+"postId="+postId);
                    doPreviewE621(postId);
                }else
                if(address.contains("e621.net/posts/")&&!address.equals("e621.net/posts/")){
                    postId=address.replace("e621.net/posts/","").replace("/","");
                    postId=postId.replace("/show","").replace("show","");
                    logger.info(fName+"postId="+postId);
                    doPreviewE621(postId);
                }else
                if(address.contains("inkbunny.net/s/")&&!address.equals("inkbunny.net/s/")){
                    postId=address.replace("inkbunny.net/s/","").replace("/","");
                    logger.info(fName+"postId="+postId);
                    doPreviewInkbunny(postId);
                }else
                if(address.contains("weasyl.com/")&&!address.equals("weasyl.com/")){
                    //weasyl.com/~bluefleet/submissions/1937843/chastity-belted-fleet
                    address=address.replace("weasyl.com/","");
                    //~bluefleet/submissions/1937843/chastity-belted-fleet
                    String items[]=address.split("/");

                    if(items.length>=3){
                        if(items[1].equalsIgnoreCase("submissions")){
                            postId=items[2];
                            logger.info(fName+"postId="+postId);
                            doPreviewWeasylSubmission(postId);
                        }
                        if(items[1].equalsIgnoreCase("characters")){
                            postId=items[2];
                            logger.info(fName+"postId="+postId);
                            doPreviewWeasylCharacter(postId);
                        }
                    }
                }else
                if(address.contains("deviantart.com")&&!address.equals("deviantart.com")){
                    //deviantart.com/nyxlis/art/furry-661999438
                    address=address.replace("deviantart.com/","");
                    //nyxlis/art/furry-661999438
                    String items[]=address.split("/");
                    if(items.length>=3){
                        if(items[1].equalsIgnoreCase("art")){
                            postId=items[2];
                            String auth=items[0];
                            logger.info(fName+"auth="+auth);
                            logger.info(fName+"postId="+postId);
                            doPreviewDeviantArt("https://www.deviantart.com/"+auth+"/art/"+postId);
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        String gUrlE621Post ="https://e621.net/posts/", gUrlFAPost="https://www.furaffinity.net/view/%/"; String gUrlFAUser ="https://www.furaffinity.net/user/%/";
        String keyE621File ="file", keyE621Url ="url",keyE621Ext="ext",keyE621Rating="rating",keyE621Tags="tags",keyE621General="general";
        private void doPreviewE621(String viewId) {
            String fName="[doPreviewE621]";
            logger.info(fName);
            try {
                logger.info(fName+"viewId="+viewId);
                lcE621 e621=new lcE621();
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                HttpResponse<JsonNode> jsonResponse=e621.reqGetSubmission(viewId);
                int status=jsonResponse.getStatus();
                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                    return;}
                JsonNode body=jsonResponse.getBody();
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
                    if(post.getJSONObject(keyE621File).has(keyE621Ext)&&!post.getJSONObject(keyE621File).isNull(keyE621Ext)){
                        ext=post.getJSONObject(keyE621File).getString(keyE621Ext);
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

                if(rating!=null){
                    if(rating.equalsIgnoreCase("s"))  embedBuilder.addField("rating","safe",true);
                    else if(rating.equalsIgnoreCase("q"))embedBuilder.addField("rating","questionable",true);
                    else if(rating.equalsIgnoreCase("e"))embedBuilder.addField("rating","explicit",true);
                }

                logger.info(fName+".isCompatible="+isCompatible+", isBanWordsInTags="+isBanWordsInTags+", rating="+rating+", is ChannelNSFW="+gTextChannel.isNSFW());
                if(isCompatible&&!isBanWordsInTags&&(rating=="s"||gTextChannel.isNSFW())){
                    if(id!=null){
                        embedBuilder.setTitle(id,gUrlE621Post+id);
                    }
                    if(artists!=null&&artists.length()>=1){
                        embedBuilder.addField("Artist",artists.getString(0),true);
                    }
                    embedBuilder.setImage(imgUrl);
                    deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
                }else{
                    embedBuilder.setDescription("Can't display!");
                    deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void doPreviewFA(String viewId) {
            String fName = "[doPreviewFA]";
            try {
                lcFURAFFINITY furaffinity=new lcFURAFFINITY(gGlobal);
                logger.info(fName+".viewId ="+viewId);
                String url=furaffinity.getViewUrlbyId(viewId);
                logger.info(fName+".url ="+url);
                HttpResponse<JsonNode> jsonResponse =furaffinity.reqGetSubmissionView(url);
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>200){
                    logger.error(fName+".invalid status"); return ;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                String image="",rating="",title="",author="",authorAvatar="";
                if(body.has(lcFURAFFINITY.INTERFACE.keyAuthor)&&!body.isNull(lcFURAFFINITY.INTERFACE.keyAuthor))author=body.getString(lcFURAFFINITY.INTERFACE.keyAuthor);
                if(body.has(lcFURAFFINITY.INTERFACE.keyImageUrl)&&!body.isNull(lcFURAFFINITY.INTERFACE.keyImageUrl))image=body.getString(lcFURAFFINITY.INTERFACE.keyImageUrl);
                if(body.has(lcFURAFFINITY.INTERFACE.keyAvatar)&&!body.isNull(lcFURAFFINITY.INTERFACE.keyAvatar))authorAvatar=body.getString(lcFURAFFINITY.INTERFACE.keyAvatar);
                if(body.has(lcFURAFFINITY.INTERFACE.keyTitle)&&!body.isNull(lcFURAFFINITY.INTERFACE.keyTitle))title=body.getString(lcFURAFFINITY.INTERFACE.keyTitle);
                if(body.has(lcFURAFFINITY.INTERFACE.keyRating)&&!body.isNull(lcFURAFFINITY.INTERFACE.keyRating))rating=body.getString(lcFURAFFINITY.INTERFACE.keyRating);
                if(!image.isBlank()&&!image.isEmpty()){
                    //embedBuilder.addField("Title","[Title]("+url+")",false);
                    if(rating.equalsIgnoreCase("mature")) embedBuilder.addField("Rating","Mature",false);
                    else if(rating.equalsIgnoreCase("adult")) embedBuilder.addField("Rating","Adult",false);
                    else if(rating.equalsIgnoreCase("general"))embedBuilder.addField("Rating","General",false);
                    embedBuilder.setColor(llColorPurple2);
                    if(rating.equalsIgnoreCase("general")||gTextChannel.isNSFW()){
                        embedBuilder.setImage(image);
                        embedBuilder.setTitle(title,gUrlFAPost.replaceAll("%",viewId));
                        embedBuilder.addField("Author","["+author+"]("+gUrlFAUser.replaceAll("%",author.toLowerCase())+")",false);
                    }else{
                        logger.warn(fName+"channel not nsfw");
                        embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                    }
                }else{
                    embedBuilder.setDescription("No image found in submission!");embedBuilder.setColor(llColorRed_Cinnabar);
                }
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }


        String valueTrue="t",valueFalse="f";
        private void doPreviewInkbunny(String viewId) {
            String fName = "[doPreviewInkbunny]";
            try {
                logger.info(fName+".viewId ="+viewId);
                lcINKBUNNY lcInkbunny =new lcINKBUNNY(gGlobal);
                lcInkbunny.doInkbunnyHtmlCheckup(gGlobal);
                JSONObject listmimetype=new JSONObject();
                listmimetype.put("image/bmp",true);
                listmimetype.put("image/gif",true);
                listmimetype.put("image/jpeg",true);
                listmimetype.put("image/png",true);
                logger.info(fName+"viewId="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                HttpResponse<JsonNode> jsonResponse = lcInkbunny.reqGetSubmissionsViewById(viewId);
                logger.info(fName+".status ="+jsonResponse.getStatus());
                int status=jsonResponse.getStatus();
                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                    return;}
                JsonNode body=jsonResponse.getBody();
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
                if(post.has(lcINKBUNNY.INTERFACE.keyIBPublic)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBPublic)&&post.getString(lcINKBUNNY.INTERFACE.keyIBPublic).equals(valueTrue)){
                    isPublic=true;
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBGuestBlock)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBGuestBlock)&&post.getString(lcINKBUNNY.INTERFACE.keyIBGuestBlock).equals(valueTrue)){
                    isGuestBlock=true;
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBHidden)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBHidden)&&post.getString(lcINKBUNNY.INTERFACE.keyIBHidden).equals(valueTrue)){
                    isHidden=true;
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBMimeType)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBMimeType)){
                    if(listmimetype.has(post.getString(lcINKBUNNY.INTERFACE.keyIBMimeType))&&listmimetype.getBoolean(post.getString(lcINKBUNNY.INTERFACE.keyIBMimeType))){
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
                if(post.has(lcINKBUNNY.INTERFACE.keyIBUserId)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBUserId)){
                    authorId=post.getString(lcINKBUNNY.INTERFACE.keyIBUserId);
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBUserName)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBUserName)){
                    authorName=post.getString(lcINKBUNNY.INTERFACE.keyIBUserName);
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBUserIconUrlLarge)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBUserIconUrlLarge)) {
                    authorAvatar = post.getString(lcINKBUNNY.INTERFACE.keyIBUserIconUrlLarge);
                }
                logger.info(fName+".authorId="+authorId+", authorName="+authorName+", authorAvatar="+authorAvatar);
                if(post.has(lcINKBUNNY.INTERFACE.keyIBSubmissionId)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBSubmissionId)) {
                    postId = post.getString(lcINKBUNNY.INTERFACE.keyIBSubmissionId);
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBTitle)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBTitle)) {
                    title = post.getString(lcINKBUNNY.INTERFACE.keyIBTitle);
                }
                logger.info(fName+". postId="+ postId+", title="+title);

                if(post.has(lcINKBUNNY.INTERFACE.keyIBRatingId)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBRatingId)){
                    ratingId=post.getInt(lcINKBUNNY.INTERFACE.keyIBRatingId);//0-general 1-matur 2-adult
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBRatingName)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBRatingName)){
                    ratings = new StringBuilder(post.getString(lcINKBUNNY.INTERFACE.keyIBRatingName));
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBRatings)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBRatings)){
                    JSONArray array=post.getJSONArray(lcINKBUNNY.INTERFACE.keyIBRatings);
                    for(int i=0;i<array.length();i++){
                        ratings.append(", ").append(array.getJSONObject(i).getString(lcINKBUNNY.INTERFACE.keyIBName));
                    }
                }
                logger.info(fName+".ratingId="+ratingId+" ,ratings="+ratings);
                if(gProfile.jsonObject.getBoolean(keyDisplayRating))embedBuilder.addField("Rating",ratings.toString(),false);
                if(post.has(lcINKBUNNY.INTERFACE.keyIBKeywords)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBKeywords)){
                    JSONArray array=post.getJSONArray(lcINKBUNNY.INTERFACE.keyIBKeywords);
                    for(int i=0;i<array.length();i++){
                        try {
                            String keywordName=array.getJSONObject(i).getString(lcINKBUNNY.INTERFACE.keyIBKeywordName);
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
                if(isBadKeywords){
                    logger.warn(fName + ".contains badword");return;
                }
                if(post.has(lcINKBUNNY.INTERFACE.keyIBFileUrlFull)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBFileUrlFull)){
                    image=post.getString(lcINKBUNNY.INTERFACE.keyIBFileUrlFull);
                }else
                if(post.has(lcINKBUNNY.INTERFACE.keyIBFileUrlScreen)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBFileUrlScreen)){
                    image=post.getString(lcINKBUNNY.INTERFACE.keyIBFileUrlScreen);
                }else
                if(post.has(lcINKBUNNY.INTERFACE.keyIBFileUrlPreview)&&!post.isNull(lcINKBUNNY.INTERFACE.keyIBFileUrlPreview)){
                    image=post.getString(lcINKBUNNY.INTERFACE.keyIBFileUrlPreview);
                }
                if(image.isBlank()){
                    logger.warn(fName + ".no image url");embedBuilder.setDescription("No image found in submission!"); deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));return;
                }
                if(ratingId==0){
                    embedBuilder.setColor( lsColorHelper.getColor("74B03C"));
                }else
                if(ratingId==1){
                    embedBuilder.setColor( lsColorHelper.getColor("5D84B6"));
                }else
                if(ratingId==2){
                    embedBuilder.setColor( lsColorHelper.getColor("F79637"));
                }

                if(ratingId>0&&!gTextChannel.isNSFW()){
                    logger.warn(fName+".not nsfw channel");
                    embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                }else{
                    embedBuilder.setImage(image);
                    if(!title.isBlank()){
                        if(!postId.isBlank()){
                            embedBuilder.setTitle(title, lcINKBUNNY.INTERFACE.gUrlInkBunnySubmissionPage.replaceAll("%",postId));
                        }else{
                            embedBuilder.setTitle(title);
                        }
                    }
                    if(!authorName.isBlank()){

                        if(!authorAvatar.isBlank()){
                            embedBuilder.setAuthor(authorName, lcINKBUNNY.INTERFACE.gUrlInkBunnyUserPage.replaceAll("!name",authorName),authorAvatar);
                        }
                        else{
                            embedBuilder.setAuthor(authorName, lcINKBUNNY.INTERFACE.gUrlInkBunnyUserPage.replaceAll("!name",authorName));
                        }
                    }
                }

                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void doPreviewWeasylSubmission(String viewId) {
            String fName = "[doPreviewWeasylSubmission]";
            try {
                lcWEASYL lcWeasyl =new lcWEASYL(gGlobal);
                logger.info(fName+".viewId ="+viewId);
                JSONObject listmimetype=new JSONObject();
                listmimetype.put("image/bmp",true);
                listmimetype.put("image/gif",true);
                listmimetype.put("image/jpeg",true);
                listmimetype.put("image/png",true);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                HttpResponse<JsonNode> jsonResponse= lcWeasyl.reqSubmission(viewId);
                int status=jsonResponse.getStatus();
                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                    return;}
                JsonNode body=jsonResponse.getBody();
                logger.debug(fName+".body ="+body.toPrettyString());
                JSONObject post=body.getObject();
                if(post.isEmpty()){
                    logger.error(fName+".no submission");return;
                }
                logger.info(fName+".post="+post.toString());
                String title="",link="",rating="",subtype="",ownerName="",ownerLogin="",ownerAvatar="",image="";
                if(post.has(lcWEASYL.INTERFACE.keyWeasylSubType)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylSubType)){
                    subtype=post.getString(lcWEASYL.INTERFACE.keyWeasylSubType);
                }
                logger.info(fName+".subtype="+subtype);
                if(!subtype.equalsIgnoreCase("visual")){
                    logger.warn(fName+".subtype is not visual");return;
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylRating)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylRating)){
                    rating=post.getString(lcWEASYL.INTERFACE.keyWeasylRating);
                }
                logger.info(fName+".rating="+rating);
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)){
                    logger.warn(fName+".rating is general");return;
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylTitle)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylTitle)){
                    title=post.getString(lcWEASYL.INTERFACE.keyWeasylTitle);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylLink)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylLink)){
                    link=post.getString(lcWEASYL.INTERFACE.keyWeasylLink);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylOwner)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylOwner)){
                    ownerName=post.getString(lcWEASYL.INTERFACE.keyWeasylOwner);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin)){
                    ownerLogin=post.getString(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylOwnerMedia)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylOwnerMedia)){
                    JSONObject jsonObject=post.getJSONObject(lcWEASYL.INTERFACE.keyWeasylOwnerMedia);
                    if(jsonObject.has(lcWEASYL.INTERFACE.keyWeasylAvatar)&&!jsonObject.isNull(lcWEASYL.INTERFACE.keyWeasylAvatar)){
                        JSONArray jsonArray=jsonObject.getJSONArray(lcWEASYL.INTERFACE.keyWeasylAvatar);
                        if(!jsonArray.isEmpty()){
                            JSONObject jsonObject2=jsonArray.getJSONObject(0);
                            if(jsonObject2.has(lcWEASYL.INTERFACE.keyWeasylUrl)&&!jsonObject2.isNull(lcWEASYL.INTERFACE.keyWeasylUrl)){
                                ownerAvatar=jsonObject2.getString(lcWEASYL.INTERFACE.keyWeasylUrl);
                            }
                        }
                    }
                }
                if(gProfile.jsonObject.getBoolean(keyDisplayRating))embedBuilder.addField("Rating",rating,false);
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)){
                    embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                }else
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylMature)){
                    embedBuilder.setColor( lsColorHelper.getColor("AABB22"));
                }else
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylExplicit)){
                    embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylMedia)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylMedia)){
                    JSONObject jsonObject=post.getJSONObject(lcWEASYL.INTERFACE.keyWeasylMedia);
                    if(jsonObject.has(lcWEASYL.INTERFACE.keyWeasylSubmission)&&!jsonObject.isNull(lcWEASYL.INTERFACE.keyWeasylSubmission)){
                        JSONArray jsonArray=jsonObject.getJSONArray(lcWEASYL.INTERFACE.keyWeasylSubmission);
                        if(!jsonArray.isEmpty()){
                            JSONObject jsonObject2=jsonArray.getJSONObject(0);
                            if(jsonObject2.has(lcWEASYL.INTERFACE.keyWeasylUrl)&&!jsonObject2.isNull(lcWEASYL.INTERFACE.keyWeasylUrl)){
                                image=jsonObject2.getString(lcWEASYL.INTERFACE.keyWeasylUrl);
                            }
                        }
                    }
                }
                if(image.isBlank()){
                    logger.warn(fName+".image is black");embedBuilder.setDescription("No image found in submission!"); deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));return;
                }

                if(!rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)&&!gTextChannel.isNSFW()){
                    logger.warn(fName+".not nsfw channel");
                    embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                }else{
                    embedBuilder.setImage(image);
                    embedBuilder.setTitle(title,link);
                    if(!ownerAvatar.isBlank()){
                        embedBuilder.setAuthor(ownerName, lcWEASYL.INTERFACE.gUrlWeasylUser.replaceAll("!name",ownerLogin),ownerAvatar);
                    }else{
                        embedBuilder.setAuthor(ownerName, lcWEASYL.INTERFACE.gUrlWeasylUser.replaceAll("!name",ownerLogin));
                    }
                }
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doPreviewWeasylCharacter(String viewId) {
            String fName = "[doPreviewWeasylCharacter]";
            try {
                lcWEASYL lcWeasyl =new lcWEASYL(gGlobal);
                logger.info(fName+".viewId ="+viewId);
                JSONObject listmimetype=new JSONObject();
                listmimetype.put("image/bmp",true);
                listmimetype.put("image/gif",true);
                listmimetype.put("image/jpeg",true);
                listmimetype.put("image/png",true);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                HttpResponse<JsonNode> jsonResponse= lcWeasyl.reqCharacter(viewId);
                int status=jsonResponse.getStatus();
                if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");
                    return;}
                JsonNode body=jsonResponse.getBody();
                logger.debug(fName+".body ="+body.toPrettyString());
                JSONObject post=body.getObject();
                if(post.isEmpty()){
                    logger.error(fName+".no submission");return;
                }
                logger.info(fName+".post="+post.toString());
                String title="",link="",rating="",subtype="",ownerName="",ownerLogin="",ownerAvatar="",image="";
                if(post.has(lcWEASYL.INTERFACE.keyWeasylRating)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylRating)){
                    rating=post.getString(lcWEASYL.INTERFACE.keyWeasylRating);
                }
                logger.info(fName+".rating="+rating);
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)){
                    logger.warn(fName+".rating is general");return;
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylTitle)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylTitle)){
                    title=post.getString(lcWEASYL.INTERFACE.keyWeasylTitle);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylLink)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylLink)){
                    link=post.getString(lcWEASYL.INTERFACE.keyWeasylLink);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylOwner)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylOwner)){
                    ownerName=post.getString(lcWEASYL.INTERFACE.keyWeasylOwner);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin)){
                    ownerLogin=post.getString(lcWEASYL.INTERFACE.keyWeasylWOwnerLogin);
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylOwnerMedia)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylOwnerMedia)){
                    JSONObject jsonObject=post.getJSONObject(lcWEASYL.INTERFACE.keyWeasylOwnerMedia);
                    if(jsonObject.has(lcWEASYL.INTERFACE.keyWeasylAvatar)&&!jsonObject.isNull(lcWEASYL.INTERFACE.keyWeasylAvatar)){
                        JSONArray jsonArray=jsonObject.getJSONArray(lcWEASYL.INTERFACE.keyWeasylAvatar);
                        if(!jsonArray.isEmpty()){
                            JSONObject jsonObject2=jsonArray.getJSONObject(0);
                            if(jsonObject2.has(lcWEASYL.INTERFACE.keyWeasylUrl)&&!jsonObject2.isNull(lcWEASYL.INTERFACE.keyWeasylUrl)){
                                ownerAvatar=jsonObject2.getString(lcWEASYL.INTERFACE.keyWeasylUrl);
                            }
                        }
                    }
                }
                if(gProfile.jsonObject.getBoolean(keyDisplayRating))embedBuilder.addField("Rating",rating,false);
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)){
                    embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                }else
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylMature)){
                    embedBuilder.setColor( lsColorHelper.getColor("AABB22"));
                }else
                if(rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylExplicit)){
                    embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                }
                if(post.has(lcWEASYL.INTERFACE.keyWeasylMedia)&&!post.isNull(lcWEASYL.INTERFACE.keyWeasylMedia)){
                    JSONObject jsonObject=post.getJSONObject(lcWEASYL.INTERFACE.keyWeasylMedia);
                    if(jsonObject.has(lcWEASYL.INTERFACE.keyWeasylSubmission)&&!jsonObject.isNull(lcWEASYL.INTERFACE.keyWeasylSubmission)){
                        JSONArray jsonArray=jsonObject.getJSONArray(lcWEASYL.INTERFACE.keyWeasylSubmission);
                        if(!jsonArray.isEmpty()){
                            JSONObject jsonObject2=jsonArray.getJSONObject(0);
                            if(jsonObject2.has(lcWEASYL.INTERFACE.keyWeasylUrl)&&!jsonObject2.isNull(lcWEASYL.INTERFACE.keyWeasylUrl)){
                                image=jsonObject2.getString(lcWEASYL.INTERFACE.keyWeasylUrl);
                            }
                        }
                    }
                }
                if(image.isBlank()){
                    logger.warn(fName+".image is black");embedBuilder.setDescription("No image found in submission!"); deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));return;
                }

                if(!rating.equalsIgnoreCase(lcWEASYL.INTERFACE.valueWeasylGeneral)&&!gTextChannel.isNSFW()){
                    logger.warn(fName+".not nsfw channel");
                    embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                }else{
                    embedBuilder.setImage(image);
                    embedBuilder.setTitle(title,link);
                    if(!ownerAvatar.isBlank()){
                        embedBuilder.setAuthor(ownerName, lcWEASYL.INTERFACE.gUrlWeasylUser.replaceAll("!name",ownerLogin),ownerAvatar);
                    }else{
                        embedBuilder.setAuthor(ownerName, lcWEASYL.INTERFACE.gUrlWeasylUser.replaceAll("!name",ownerLogin));
                    }
                }
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        String gUrlDeviantGetArt="https://backend.deviantart.com/oembed?url=!url";
        String keyDevTitle="title",keyDevUrl="url";
        String keyDevType="type",valDevType="photo";
        String keyDevSafety="safety",valueDevNonAdult="nonadult",valueDevAdult="adult";
        String keyDevAuthorName="author_name",keyDevAuthorUrl="author_url";
        private void doPreviewDeviantArt(String viewUrl) {
            String fName = "[doPreviewDeviantArt]";
            try {
                logger.info(fName+".viewUrl ="+viewUrl);
                logger.info(fName+"viewUrl="+viewUrl);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url=gUrlDeviantGetArt.replaceAll("!url",viewUrl);
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
                                JSONObject post=body.getObject();
                                if(post.isEmpty()){
                                    logger.error(fName+".no submission");return;
                                }
                                logger.info(fName+".post="+post.toString());
                                String title="",rating="",subtype="",ownerName="",ownerUrl="",image="";
                                if(post.has(keyDevType)&&!post.isNull(keyDevType)){
                                    subtype=post.getString(keyDevType);
                                }
                                logger.info(fName+".subtype="+subtype);
                                if(!subtype.equalsIgnoreCase(valDevType)){
                                    logger.error(fName+".not a photo");return;
                                }
                                if(post.has(keyDevSafety)&&!post.isNull(keyDevSafety)){
                                    rating=post.getString(keyDevSafety);
                                }
                                logger.info(fName+".rating="+rating);
                                if(post.has(keyDevTitle)&&!post.isNull(keyDevTitle)){
                                    title=post.getString(keyDevTitle);
                                }
                                if(post.has(keyDevAuthorName)&&!post.isNull(keyDevAuthorName)){
                                    ownerName=post.getString(keyDevAuthorName);
                                }
                                if(post.has(keyDevAuthorUrl)&&!post.isNull(keyDevAuthorUrl)){
                                    ownerUrl=post.getString(keyDevAuthorUrl);
                                }
                                if(rating.equalsIgnoreCase(valueDevAdult)){
                                    if(gProfile.jsonObject.getBoolean(keyDisplayRating))embedBuilder.addField("Rating","NSFW",false);
                                    embedBuilder.setColor( lsColorHelper.getColor("B91E23"));
                                }else   if(rating.equalsIgnoreCase(valueDevNonAdult)){
                                    if(gProfile.jsonObject.getBoolean(keyDisplayRating))embedBuilder.addField("Rating","SFW",false);
                                    embedBuilder.setColor( lsColorHelper.getColor("798BAA"));
                                }

                                if(post.has(keyDevUrl)&&!post.isNull(keyDevUrl)){
                                    image=post.getString(keyDevUrl);
                                }
                                if(image.isBlank()){
                                    logger.warn(fName+".image is black");embedBuilder.setDescription("No image found in submission!"); deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));return;
                                }
                                if(!rating.equalsIgnoreCase(valueDevNonAdult)&&!gTextChannel.isNSFW()){
                                    logger.warn(fName+".not nsfw channel");
                                    embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                                }else{
                                    embedBuilder.setTitle(title,viewUrl);
                                    //embedBuilder.setAuthor(ownerName,gUrlWeasylUser.replaceAll("!name",ownerUrl));
                                    embedBuilder.setAuthor(ownerName);
                                    embedBuilder.setImage(image);
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


        private void deleteOption(Message message) {
            String fName="[deleteOption]";
            logger.info(fName);
            try {
                try {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("bomb"));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.info(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
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

        lcJSONGuildProfile gProfile;
        String gProfileName="LinkImagePreview";
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putGuildSettings(gGuild,gProfile);
            if(gProfile.saveProfile()){
                logger.info(fName + ".success save to db");return true;
            }
            logger.error(fName + ".error save to db");return false;
        }
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gProfileName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gProfileName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyProfile();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
        }
        String keyEnabled="enabled",keyAutoPreviewEnabled ="autoenabled",keyDisplayRating="displayRating";
        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyEnabled)||gProfile.jsonObject.isNull(keyEnabled)){ gProfile.jsonObject.put(keyEnabled,true); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyAutoPreviewEnabled)||gProfile.jsonObject.isNull(keyAutoPreviewEnabled)){ gProfile.jsonObject.put(keyAutoPreviewEnabled,true); gProfile.isUpdated=true; }
            if(!gProfile.jsonObject.has(keyDisplayRating)||gProfile.jsonObject.isNull(keyDisplayRating)){ gProfile.jsonObject.put(keyDisplayRating,false); gProfile.isUpdated=true; }
        }

    }

}
