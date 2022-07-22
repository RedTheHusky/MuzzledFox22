package util.inhouse;
//implemented Runnable

import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsQuickMessages;
import models.ls.lsStreamHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.internal.StringUtil;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class utilityStickerSurge extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[utilityStickerSurge]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public utilityStickerSurge(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        this.name = "Utility-StickerSurge";
        this.help = "Getting stickers 4 StickerSurge";
        this.aliases = new String[]{"stickersurge"};
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
        CommandEvent gCommandEvent;Guild gGuild;User gUser;TextChannel gTextChannel; String gTitle="StickerS+";
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
                    setGuildUrl();
                    if(items[0].equalsIgnoreCase("help")){
                        help( "main");isInvalidCommand=false;
                    }
                    if(items[0].startsWith(":")||items[0].startsWith("-")){
                        doActionPostSticker(items[0]);isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("packages")||items.length>=2&&items[0].equalsIgnoreCase("package")){
                        if(items[1].equalsIgnoreCase("list")){
                            getPackagesList();isInvalidCommand=false;
                        }
                        else if(items[1].equalsIgnoreCase("download")){
                            downloadPackageImages(items[2]);isInvalidCommand=false;
                        }
                        else if(items[1].equalsIgnoreCase("print")){
                            if(items.length>=5){
                                printWithIndex4Package(items[2],items[3],items[4]); isInvalidCommand=false;
                            }else
                            if(items.length==4){
                                printWithIndex4Package(items[2],"0",items[3]); isInvalidCommand=false;
                            }else
                            if(items.length==3){
                                printWithIndex4Package(items[2],"0","0"); isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("print2")){
                            if(items.length>=5){
                                printWithImage4Package(items[2],items[3],items[4]);isInvalidCommand=false;
                            }else
                            if(items.length==4){
                                printWithImage4Package(items[2],"0",items[3]);isInvalidCommand=false;
                            }else
                            if(items.length==3){
                                printWithImage4Package(items[2],"0","0");isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("search")){
                            if(items.length>=5){
                                searchWithIndex4Package(items[2],items[3],items[4]); isInvalidCommand=false;
                            }else
                            if(items.length==4){
                                searchWithIndex4Package(items[2],items[3],"0"); isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("search2")){
                            if(items.length>=5){
                                searchWithImage4Package(items[2],items[3],items[4]);isInvalidCommand=false;
                            }else
                            if(items.length==4){
                                searchWithImage4Package(items[2],items[3],"0"); isInvalidCommand=false;
                            }
                        }

                    }
                    else if(items[0].equalsIgnoreCase("personal")){
                        if(items[1].equalsIgnoreCase("list")){
                            getUserPackagesList(gUser);isInvalidCommand=false;
                        }
                        else if(items[1].equalsIgnoreCase("download")){
                            downloadUserImages(gUser);isInvalidCommand=false;
                        }
                        else if(items[1].equalsIgnoreCase("print")){
                            if(items.length>=4){
                                printUserWithImage(gUser,items[2],items[3]); isInvalidCommand=false;
                            }else
                            if(items.length==3){
                                printUserWithIndex(gUser,"0",items[2]); isInvalidCommand=false;
                            }else{
                                printUserWithIndex(gUser,"0","0"); isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("print2")){
                            if(items.length>=4){
                                printUserWithImage(gUser,items[2],items[3]);isInvalidCommand=false;
                            }else
                            if(items.length==3){
                                printUserWithImage(gUser,"0",items[2]);isInvalidCommand=false;
                            }else{
                                printUserWithImage(gUser,"0","0");isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("search")){
                            if(items.length>=4){
                                searchUserWithIndex(gUser,items[2],items[3]); isInvalidCommand=false;
                            }else
                            if(items.length==3){
                                searchUserWithIndex(gUser,items[2],"0"); isInvalidCommand=false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("search2")){
                            if(items.length>=4){
                                searchUserWithImage(gUser,items[2],items[3]);isInvalidCommand=false;
                            }else
                            if(items.length==4){
                                searchUserWithImage(gUser,items[2],"0"); isInvalidCommand=false;
                            }
                        }

                    }
                    else if(items[0].equalsIgnoreCase("download")){
                        downloadServerImages();
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("print")){
                        if(items.length>=3){
                            printWithIndex(items[1],items[2]);
                        }else
                        if(items.length==2){
                            printWithIndex("0",items[1]);
                        }else{
                            printWithIndex("0","0");
                        }
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("print2")){
                        if(items.length>=3){
                            printWithImage(items[1],items[2]);
                        }else
                        if(items.length==2){
                            printWithImage("0",items[1]);
                        }else{
                            printWithImage("0","0");
                        }
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("search")){
                        if(items.length>=3){
                            searchWithIndex(items[1],items[2]);isInvalidCommand=false;
                        }else
                        if(items.length==2){
                            searchWithIndex(items[1],"0");isInvalidCommand=false;
                        }
                    }
                    else if(items[0].equalsIgnoreCase("search2")){
                        if(items.length>=3){
                            searchWithImage(items[1],items[2]);isInvalidCommand=false;
                        }else
                        if(items.length==2){
                            searchWithImage(items[1],"0");isInvalidCommand=false;
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
        embed.addField("Posting","`"+quickSummonWithSpace+" :name:`"+", `"+quickSummonWithSpace+" :package-name:`",false);
        embed.addField("Print","`"+quickSummonWithSpace+"print <count>`"+", `"+quickSummonWithSpace+"print <start index> <count>`"+"\n`"+quickSummonWithSpace+"print2 <count>`"+", `"+quickSummonWithSpace+"print2 <start index> <count>`",false);
        embed.addField("Search","`"+quickSummonWithSpace+"search <name>`"+", `"+quickSummonWithSpace+"search <name> <count>`"+"\n`"+quickSummonWithSpace+"search2 <name>`"+", `"+quickSummonWithSpace+"search2 <name> <count>`",false);
        embed.addField("Download","`"+quickSummonWithSpace+"download <package>`",false);
        quickSummonWithSpace+="packages ";
        String desc="`"+quickSummonWithSpace+"list`";
        desc+="\n`"+quickSummonWithSpace+"download <package>`";
        desc+="\n`"+quickSummonWithSpace+"print <package> <count>`"+", `"+quickSummonWithSpace+"print <package> <start index> <count>`"+"\n`"+quickSummonWithSpace+"print2 <package> <count>`"+", `"+quickSummonWithSpace+"print2 <package> <start index> <count>`";
        desc+="\n`"+quickSummonWithSpace+"search <package> <name>`"+", `"+quickSummonWithSpace+"search <package> <name> <count>`"+"\n`"+quickSummonWithSpace+"search2 <package> <name>`"+", `"+quickSummonWithSpace+"search2 <package> <name> <count>`";
        embed.addField("Packages",desc,false);
        quickSummonWithSpace+="personal ";desc="`"+quickSummonWithSpace+"list`";
        desc+="\n`"+quickSummonWithSpace+"download`";
        desc+="\n`"+quickSummonWithSpace+"print <count>`"+", `"+quickSummonWithSpace+"print <start index> <count>`"+"\n`"+quickSummonWithSpace+"print2 <count>`"+", `"+quickSummonWithSpace+"print2 <start index> <count>`";
        desc+="\n`"+quickSummonWithSpace+"search <name>`"+", `"+quickSummonWithSpace+"search <name> <count>`"+"\n`"+quickSummonWithSpace+"search2 <name>`"+", `"+quickSummonWithSpace+"search2 <name> <count>`";
        embed.addField("Personal",desc,false);desc="";
        llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
        llSendMessage(gUser,embed);
    }
        String gUrlGuild ="https://stickersfordiscord.com/api/guilds/%guild?nocache=1585402305843";
        String gUrlPackage="https://stickersurge.com/api/sticker-packs/%package";
        String gUrlUser ="https://stickersurge.com/api/users/%user?nocache=1590563319135";
        private void setGuildUrl(){
            String fName = "[setGuildUrl]";
            logger.info(fName + ".guildID="+gGuild.getId());
            gUrlGuild =gUrlGuild.replaceAll("%guild",gGuild.getId());
            logger.info(fName + ".gUrlGuild="+ gUrlGuild);
        }
        private void setPackageUrl(String name){
            String fName = "[setPackageUrl]";
            logger.info(fName + ".name="+name);
            gUrlPackage =gUrlPackage.replaceAll("%package",name);
            logger.info(fName + ".gUrlPackage="+ gUrlPackage);
        }
        private void printWithImage(String index, String count) {
            String fName = "[printWithImage]";
            try {
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                   try{
                                       JSONObject obj=array.getJSONObject(i);
                                       EmbedBuilder embed=new EmbedBuilder();
                                       if(obj.has(keyName)&&obj.has(keyUrl)){
                                           logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                           if(iIndex<=0||iIndex<=i){
                                               if(iCount<=0||iCount>c){
                                                   c++;
                                                   embed.setDescription("Use :"+obj.getString(keyName)+": for this image");
                                                   embed.setImage(obj.getString(keyUrl));
                                                   llSendMessage(gTextChannel,embed);
                                               }else{
                                                   logger.info(fName + ".break");break;
                                               }
                                           }
                                       }else{
                                           logger.info(fName + ".sticker["+i+"] invalid");
                                       }
                                   }catch (Exception e) {
                                       logger.error(fName+"exception:"+e);
                                   }

                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchWithImage(String text, String count) {
            String fName = "[searchWithImage]";
            try {
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                  try{
                                      JSONObject obj=array.getJSONObject(i);
                                      EmbedBuilder embed=new EmbedBuilder();
                                      if(obj.has(keyName)&&obj.has(keyUrl)){
                                          logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                          if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                              if(iCount<=0||iCount>c){
                                                  c++;
                                                  embed.setDescription("Use :"+obj.getString(keyName)+": for this image");
                                                  embed.setImage(obj.getString(keyUrl));
                                                  llSendMessage(gTextChannel,embed);
                                              }else{
                                                  logger.info(fName + ".break");break;
                                              }
                                          }
                                      }else{
                                          logger.info(fName + ".sticker["+i+"] invalid");
                                      }
                                  }catch (Exception e) {
                                      logger.error(fName+"exception:"+e);
                                  }
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void printWithIndex(String index, String count) {
            String fName = "[printWithIndex]";
            try {
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(iIndex<=0||iIndex<=i){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                   list.add(":"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchWithIndex(String text, String count) {
            String fName = "[searchWithIndex]";
            try {
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    list.add(":"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private JSONObject getJSONObject(@NotNull HttpResponse<JsonNode> response){
            String fName="getJSONObject";
            JSONObject object;
            int status=response.getStatus();
            if(!(200<=status&&status<=299)){ logger.error(fName+".invalid status");logger.error(fName+".return 1");return null;}
            object=response.getBody().getObject();
            return object;
        }
        String keyName="name", keyUrl="url", keyCustomStickers="customStickers", keyStickerPacks="stickerPacks",keyStickers="stickers";
        private void downloadServerImages(){
            String fName = "[downloadServerImages]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                Message messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                                OffsetDateTime now=OffsetDateTime.now();
                                lcTempZipFile zipFile=new lcTempZipFile();
                                String  entryExtension="";

                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            String stickerName=obj.getString(keyName);
                                            logger.info(fName + ".sticker["+i+"].name="+stickerName);
                                            String stickerUrl=obj.getString(keyUrl);
                                            logger.info(fName + ".sticker["+i+"].url="+ stickerUrl);
                                            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(stickerUrl);
                                            if(inputStream!=null) {
                                                if(stickerUrl.toLowerCase().contains(".png"))entryExtension=".png";
                                                else if(stickerUrl.toLowerCase().contains(".jpg"))entryExtension=".jpg";
                                                else if(stickerUrl.toLowerCase().contains(".gif"))entryExtension=".gif";
                                                String namex=stickerName+entryExtension;
                                                logger.info(fName + ".file["+i+"].url="+ namex);
                                                zipFile.addEntity(namex,inputStream);
                                                inputStream.close();
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                InputStream targetStream = zipFile.getInputStream();
                                String fileName="Stickers", fileExtension=".zip";
                                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                                gTextChannel.sendMessage("Here is the server stickers:").addFile(targetStream,fileName+fileExtension).complete();
                                messageProcessing.delete().queue();
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
               

                



            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void getPackagesList() {
            String fName = "[searchWithImage]";
            try {
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlGuild;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickerPacks)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no sticker packs!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickerPacks);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no sticker packs!",llColorRed);return;
                                }
                                int c=0;
                                List<String> list=new ArrayList<>();
                                for(int i=0;i<length;i++){
                                    try{
                                        String packName=array.getString(i);
                                        logger.info(fName + ".stickerpack["+i+"]="+packName);
                                        list.add(packName);
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                String desc="List of server packages:\n"+StringUtil.join(list,", ");
                                llSendQuickEmbedMessage(gTextChannel,gTitle,desc,llColorPurple2);
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void printWithImage4Package(String name,String index, String count) {
            String fName = "[printWithImage4Package]";
            try {
                logger.info(fName + ".name="+name);setPackageUrl(name);
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlPackage;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickers)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(iIndex<=0||iIndex<=i){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    embed.setDescription("Use :"+name+"-"+obj.getString(keyName)+": for this image");
                                                    embed.setImage(obj.getString(keyUrl));
                                                    llSendMessage(gTextChannel,embed);
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchWithImage4Package(String name,String text, String count) {
            String fName = "[searchWithImage4Package]";
            try {
                logger.info(fName + ".name="+name);setPackageUrl(name);
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlPackage;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickers)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    embed.setDescription("Use :"+name+"-"+obj.getString(keyName)+": for this image");
                                                    embed.setImage(obj.getString(keyUrl));
                                                    llSendMessage(gTextChannel,embed);
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void printWithIndex4Package(String name,String index, String count) {
            String fName = "[printWithIndex4Package]";
            try {
                logger.info(fName + ".name="+name);setPackageUrl(name);
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlPackage;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickers)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(iIndex<=0||iIndex<=i){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    list.add(":"+name+"-"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchWithIndex4Package(String name,String text, String count) {
            String fName = "[searchWithIndex4Package]";
            try {
                logger.info(fName + ".name="+name);setPackageUrl(name);
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlPackage;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickers)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    list.add(":"+name+"-"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void downloadPackageImages(String name){
            String fName = "[downloadPackageImages]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                logger.info(fName + ".name="+name);setPackageUrl(name);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlPackage;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                Message messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                                OffsetDateTime now=OffsetDateTime.now();
                                lcTempZipFile zipFile=new lcTempZipFile();
                                String  entryExtension="";

                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickers)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            String stickerName=obj.getString(keyName);
                                            logger.info(fName + ".sticker["+i+"].name="+stickerName);
                                            String stickerUrl=obj.getString(keyUrl);
                                            logger.info(fName + ".sticker["+i+"].url="+ stickerUrl);
                                            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(stickerUrl);
                                            if(inputStream!=null) {
                                                if(stickerUrl.toLowerCase().contains(".png"))entryExtension=".png";
                                                else if(stickerUrl.toLowerCase().contains(".jpg"))entryExtension=".jpg";
                                                else if(stickerUrl.toLowerCase().contains(".gif"))entryExtension=".gif";
                                                String namex=stickerName+entryExtension;
                                                logger.info(fName + ".file["+i+"].url="+ namex);
                                                zipFile.addEntity(namex,inputStream);
                                                inputStream.close();
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                InputStream targetStream = zipFile.getInputStream();
                                String fileName="Stickers_"+name, fileExtension=".zip";
                                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                                gTextChannel.sendMessage("Here is the package stickers:").addFile(targetStream,fileName+fileExtension).complete();
                                messageProcessing.delete().queue();
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });






            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void setUserUrl(User user){
            String fName = "[setPackageUrl]";
            logger.info(fName + ".user="+user.getId());
            gUrlUser = gUrlUser.replaceAll("%user",user.getId());
            logger.info(fName + "gUrlUser="+ gUrlUser);
        }
        private void downloadUserImages(User user){
            String fName = "[downloadServerImages]";
            logger.info(fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(fName+"denied");return;
            }
            try {
                Unirest a= new Unirest();setUserUrl(user);
                a.config().verifySsl(false);
                String url= gUrlUser;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                Message messageProcessing=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColorBlue1);
                                OffsetDateTime now=OffsetDateTime.now();
                                lcTempZipFile zipFile=new lcTempZipFile();
                                String  entryExtension="";

                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            String stickerName=obj.getString(keyName);
                                            logger.info(fName + ".sticker["+i+"].name="+stickerName);
                                            String stickerUrl=obj.getString(keyUrl);
                                            logger.info(fName + ".sticker["+i+"].url="+ stickerUrl);
                                            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(stickerUrl);
                                            if(inputStream!=null) {
                                                if(stickerUrl.toLowerCase().contains(".png"))entryExtension=".png";
                                                else if(stickerUrl.toLowerCase().contains(".jpg"))entryExtension=".jpg";
                                                else if(stickerUrl.toLowerCase().contains(".gif"))entryExtension=".gif";
                                                String namex=stickerName+entryExtension;
                                                logger.info(fName + ".file["+i+"].url="+ namex);
                                                zipFile.addEntity(namex,inputStream);
                                                inputStream.close();
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                InputStream targetStream = zipFile.getInputStream();
                                String fileName="Stickers", fileExtension=".zip";
                                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                                gTextChannel.sendMessage("Here is the server stickers:").addFile(targetStream,fileName+fileExtension).complete();
                                messageProcessing.delete().queue();
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });






            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void getUserPackagesList(User user) {
            String fName = "[getUserPackagesList]";
            try {
                Unirest a= new Unirest();setUserUrl(user);
                a.config().verifySsl(false);
                String url= gUrlUser;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyStickerPacks)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no sticker packs!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyStickerPacks);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no sticker packs!",llColorRed);return;
                                }
                                int c=0;
                                List<String> list=new ArrayList<>();
                                for(int i=0;i<length;i++){
                                    try{
                                        String packName=array.getString(i);
                                        logger.info(fName + ".stickerpack["+i+"]="+packName);
                                        list.add(packName);
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                String desc="List of personal packages:\n"+StringUtil.join(list,", ");
                                llSendQuickEmbedMessage(gTextChannel,gTitle,desc,llColorPurple2);
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void printUserWithImage(User user,String index, String count) {
            String fName = "[printUserWithImage]";
            try {
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                setUserUrl(user);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlUser;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(iIndex<=0||iIndex<=i){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    embed.setDescription("Use :"+obj.getString(keyName)+": for this image");
                                                    embed.setImage(obj.getString(keyUrl));
                                                    llSendMessage(gTextChannel,embed);
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchUserWithImage(User user,String text, String count) {
            String fName = "[searchUserWithImage]";
            try {
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                setUserUrl(user);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlUser;
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        EmbedBuilder embed=new EmbedBuilder();
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    embed.setDescription("Use :"+obj.getString(keyName)+": for this image");
                                                    embed.setImage(obj.getString(keyUrl));
                                                    llSendMessage(gTextChannel,embed);
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void printUserWithIndex(User user,String index, String count) {
            String fName = "[printUserWithIndex]";
            try {
                logger.info(fName + ".index="+index);
                logger.info(fName + ".count="+count);
                int iIndex= Integer.parseInt(index);int iCount= Integer.parseInt(count);
                logger.info(fName + ".iIndex="+iIndex);
                logger.info(fName + ".iCount="+iCount);
                setUserUrl(user);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlUser;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(iIndex<=0||iIndex<=i){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    list.add(":"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }

                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void searchUserWithIndex(User user,String text, String count) {
            String fName = "[searchUserWithIndex]";
            try {
                logger.info(fName + ".text="+text);
                logger.info(fName + ".count="+count);
                int iCount= Integer.parseInt(count);
                logger.info(fName + ".iCount="+iCount);
                setUserUrl(user);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                String url= gUrlUser;
                ArrayList<String> list=new ArrayList<>();
                logger.info(fName + ".url="+url);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(keyCustomStickers)){
                                    logger.info(fName + ".has no customStickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(keyCustomStickers);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no custom stickers!",llColorRed);return;
                                }
                                int c=0;
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            logger.info(fName + ".sticker["+i+"]:"+obj.getString(keyName));
                                            if(obj.getString(keyName).toLowerCase().contains(text.toLowerCase())){
                                                if(iCount<=0||iCount>c){
                                                    c++;
                                                    list.add(":"+obj.getString(keyName)+":");
                                                }else{
                                                    logger.info(fName + ".break");break;
                                                }
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setTitle(gTitle);
                                String desc="";
                                int listSize=list.size();
                                logger.info(fName + "listSize="+listSize);
                                for(int i=0;i<listSize;i++){
                                    logger.info(fName + ".list["+i+"]="+list.get(i));
                                    String tmp;
                                    if(i>0){
                                        tmp=desc+" "+list.get(i);
                                    }else{
                                        tmp=desc+list.get(i);
                                    }
                                    if(tmp.length()>2000){
                                        logger.info(fName + ".size too big> send desc="+desc);
                                        embed.setDescription(desc);
                                        llSendMessage(gTextChannel,embed);
                                        desc=list.get(i);
                                    }else{
                                        logger.info(fName + ".size ok> add to desc");
                                        desc=tmp;
                                    }
                                }
                                if(!desc.isEmpty()){
                                    logger.info(fName + ".last> send desc="+desc);
                                    embed.setDescription(desc);
                                    llSendMessage(gTextChannel,embed);
                                }
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        private void doActionPostSticker(String stickername){
            String fName = "[doActionPostSticker]";
            logger.info(fName);
            try {
                logger.info(fName + ".stickername="+stickername);
                String url="";
                if(stickername.startsWith(":")){
                    stickername=stickername.replaceAll(":","");
                    if(stickername.contains("-")){
                        String packageName="";
                        String []items=stickername.split("-");
                        if(items.length>=2){
                            packageName=items[0];
                            stickername=items[1];
                            setPackageUrl(packageName);
                            url= gUrlPackage;
                            logger.info(fName + ".url="+url);
                            logger.info(fName + ".packageName="+packageName);
                            logger.info(fName + ".stickername="+stickername);
                            postSticker(url,keyStickers,stickername);
                        }else{
                            llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
                        }
                    }else{
                        url= gUrlGuild;
                        logger.info(fName + ".url="+url);
                        postSticker(url,keyCustomStickers,stickername);
                    }
                }else
                if(stickername.startsWith("-")){
                    stickername=stickername.replaceAll("-","").replaceAll(":","");
                    setUserUrl(gUser);
                    url= gUrlUser;
                    logger.info(fName + ".url="+url);
                    postSticker(url,keyCustomStickers,stickername);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void postSticker(String url, String key, String stickername){
            String fName = "[getStrickers4Posting]";
            logger.info(fName);
            try {
                logger.info(fName + ".stickername="+stickername);
                logger.info(fName + ".url="+url);
                logger.info(fName + ".key="+key);
                String finalStickername=stickername;
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                a.get(url)
                        .asJson()
                        .ifSuccess(response -> {
                            logger.info(fName+".success");
                            try {
                                JSONObject object = getJSONObject(response);
                                assert object != null;
                                logger.info(fName + ".json=" + object.toString());
                                if (object.isEmpty()) {
                                    logger.error(fName + ".isEmpty");
                                    return;
                                }
                                if(!object.has(key)){
                                    logger.info(fName + ".has no Stickers");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                JSONArray array=object.getJSONArray(key);
                                int length=array.length();
                                logger.info(fName + ".length="+length);
                                if(length==0){
                                    logger.info(fName + ".length is 0");
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Has no stickers!",llColorRed);return;
                                }
                                for(int i=0;i<length;i++){
                                    try{
                                        JSONObject obj=array.getJSONObject(i);
                                        if(obj.has(keyName)&&obj.has(keyUrl)){
                                            String stickerName=obj.getString(keyName);
                                            logger.info(fName + ".sticker["+i+"].name="+stickerName);
                                            if(stickerName.equalsIgnoreCase(finalStickername)){
                                                String stickerUrl=obj.getString(keyUrl);
                                                logger.info(fName + ".sticker["+i+"].url="+ stickerUrl);
                                                postWebhookImage(gMember, gTextChannel,stickerUrl,stickerName);
                                                return;
                                            }
                                        }else{
                                            logger.info(fName + ".sticker["+i+"] invalid");
                                        }
                                    }catch (Exception e) {
                                        logger.error(fName+"exception:"+e);
                                    }
                                }
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"No such named sticker!",llColorRed);
                            } catch (Exception e) {
                                logger.error(fName+"exception:"+e);
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                            }
                        })
                        .ifFailure(response -> {
                            logger.error(fName+".failure");
                            logger.error("Oh No! Status" + response.getStatus());
                            response.getParsingError().ifPresent(e -> {
                                logger.error("Parsing Exception: "+e);
                                logger.error("Original body: " + e.getOriginalBody());
                            });
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }
        }
        private void postWebhookEmbed(Member member, TextChannel textChannel,String imageUrl) {
            String fName = "[postWebhookEmbed]";
            try {
                logger.info(fName + ".imageUrl="+ imageUrl);
                lcWebHookBuild whh = new lcWebHookBuild();
                whh.doSafetyCleanwToken(textChannel);
                String name=member.getEffectiveName();
                String avatarUrl = member.getUser().getEffectiveAvatarUrl();
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("avatarurl", avatarUrl);
                if (!whh.preBuild(gTextChannel, json)) {
                    logger.error(fName + "failed prebuild");
                    if(whh.isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "channel maxed out");

                    }else{

                    }
                    return;
                }
                if (!whh.build()) {
                    logger.error(fName + "failed build");
                    if(whh.isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "channel maxed out");
                    }else{
                    }
                    return;
                }
                if (!whh.clientOpen()) {
                    logger.error(fName + "failed client open");
                    whh.delete();
                    return;
                }
                logger.info(fName + ".send webhook");
                WebhookEmbedBuilder embed=new WebhookEmbedBuilder();
                embed.setImageUrl(imageUrl);
                whh.send(embed);
                Thread.sleep(2000);
                logger.info(fName + ".close and delete webhook");
                whh.clientClose();
                Thread.sleep(1000);
                whh.delete();
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
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
                InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(imageUrl);
                if(inputStream!=null) {
                    String entryExtension="";
                    if(imageUrl.toLowerCase().contains(".png"))entryExtension=".png";
                    else if(imageUrl.toLowerCase().contains(".jpg"))entryExtension=".jpg";
                    else if(imageUrl.toLowerCase().contains(".gif"))entryExtension=".gif";
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
                    whh.send(inputStream,namex);
                    Thread.sleep(2000);
                    logger.info(fName + ".close and delete webhook");
                    whh.clientClose();
                    Thread.sleep(1000);
                    whh.delete();
                }
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }

    
    
    
    
    
    
    
  //runLocal  
    }
}
