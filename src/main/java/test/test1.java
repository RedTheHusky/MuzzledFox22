package test;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.pengrad.telegrambot.model.Sticker;
import com.pengrad.telegrambot.model.StickerSet;
import com.pengrad.telegrambot.request.GetStickerSet;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.response.GetStickerSetResponse;
import kong.unirest.json.JSONObject;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsStreamHelper;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

public class test1 extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  llCommonKeys {
    String cName="[test1]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter; String gTitle="test1";
    Logger logger = Logger.getLogger(getClass());
    public test1(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "test-test1";
        this.help = "test1";
        this.aliases = new String[]{"test1"};
        this.guildOnly = true;
        this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(cName+fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;
       
        public runLocal(CommandEvent ev) {
            logger.info(".run build");
            gEvent = ev;
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            boolean isInvalidCommand = true;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if(gEvent.getArgs().isEmpty()){
                logger.info(cName+fName+".Args=0");
                help("main");isInvalidCommand=false;
            }else {
                logger.info(fName + ".Args");
                String []items = gEvent.getArgs().split("\\s+");
                if(items[0].equalsIgnoreCase("help")){
                    help( "main");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("test")){
                   testCalc("1800");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("test2")){
                    testCalc("380");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("test3")){
                    testCalc2("1800");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("test4")){
                    testCalc2("380");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("telegramsticker")){
                    telegramTestSticker("");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("image1")){
                    getImgFromSite1();isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("image2")){
                    getImgFromSite2();isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("image3")){
                    getImgFromSite3();isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("channelpermission")){
                    getChannelPermission();isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("userflags")){
                    userflags();isInvalidCommand=false;
                }
            }
            logger.info(cName+fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
            }
        }
        
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"test1 ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("`channelpermission`");
            llSendMessage(gUser,embed);
        }
        private void testCalc(String str) {
            String fName = "[testCalc]";
            try{
                logger.info(fName + "str="+str);
                double internalValueMax=67.5, netloadMax=26199;
                int weightOfOneCubicMeter=Integer.valueOf(str);
                logger.info(fName + "internalValueMax="+internalValueMax);
                logger.info(fName + "netloadMax="+netloadMax);
                logger.info(fName + "eight4OneCubicMeter="+weightOfOneCubicMeter);
                double weightAtMaxInterval=weightOfOneCubicMeter*internalValueMax; //we calculated weight using the max internal value
                logger.info(fName + "weightAtMaxInterval="+weightAtMaxInterval);
                if(weightAtMaxInterval<=netloadMax){
                    logger.info(fName + "at max or bellow!");
                    //if weight is equal or bellow maximum netload it means the output is 67.5
                    //in case of weight being small than max net load, its still 67.5 as we cant have more do to the limitation of maximum internal.
                    //the weightAtMaxInterval was calculated using the max internal value
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Input="+weightOfOneCubicMeter+"\nOutput="+internalValueMax,llColorBlue1);
                }else{
                    logger.info(fName + "past max!");
                    //if the calculated weight using the max internal value is past the maximum netload
                    //it means we cant use the full internal value, meaning the returned internal value is less than the maximum internal value
                    double cubicMeterBasedOnTotalNetLoad=netloadMax/weightOfOneCubicMeter;//we calculated the used internal value based on the maximum load/weight of one cubic meter
                    logger.info(fName + "cubicMeterBasedOnTotalNetLoad="+cubicMeterBasedOnTotalNetLoad);
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Input="+weightOfOneCubicMeter+"\nOutput="+cubicMeterBasedOnTotalNetLoad,llColorBlue1);
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }
        }
        private void testCalc2(String str) {
                String fName = "[testCalc]";
                try{
                    /*
                    capacity = 26199/kg_cargo_per_cubic_metre
      if(capacity > 67.5):
        return 67.5
      else:
        return capacity
                     */
                    logger.info(fName + "str="+str);
                    double internalValueMax=67.5, netloadMax=26199;
                    int weightOfOneCubicMeter=Integer.valueOf(str);
                    logger.info(fName + "internalValueMax="+internalValueMax);
                    logger.info(fName + "netloadMax="+netloadMax);
                    logger.info(fName + "eight4OneCubicMeter="+weightOfOneCubicMeter);
                    double capacity=netloadMax/weightOfOneCubicMeter; //we calculated weight using the max internal value
                    logger.info(fName + "capacity="+capacity);
                    if(capacity>=internalValueMax){
                        logger.info(fName + "at max or past!");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Input="+weightOfOneCubicMeter+"\nOutput="+internalValueMax,llColorBlue1);
                    }else{
                        logger.info(fName + "bellow");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Input="+weightOfOneCubicMeter+"\nOutput="+capacity,llColorBlue1);
                    }
                }
                catch (Exception e){
                    logger.error(fName + ". exception:"+e);
                }
            }
        private void telegramTestSticker(String str) {
            String fName = "[telegramTestSticker]";
            try{
                GetStickerSet getStickerSet = new GetStickerSet("RedUsagi");
                logger.info(fName + ". getStickerSet.done");
                GetStickerSetResponse response = gGlobal.botTelegram.execute(getStickerSet);
                logger.info(fName + ". response.done");
                StickerSet stickerSet = response.stickerSet();
                logger.info(fName + ". stickerSet.done");
                stickerSet.name();
                logger.info(fName + ". stickerSet.name="+stickerSet.name());
                logger.info(fName + ". stickerSet.title="+stickerSet.title());
                Sticker[]stickers=stickerSet.stickers();

                logger.info(fName + ". stickers[0].fileId="+stickers[0].fileId());
                logger.info(fName + ". stickers[0].fileUniqueId="+stickers[0].fileUniqueId());
                logger.info(fName + ". stickers[0].toString="+stickers[0].toString());
                logger.info(fName + ". stickers[0].emoji="+stickers[0].emoji());
                SendSticker sendSticker = new SendSticker("000", stickers[0].fileId());

            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getImgFromSite1() {
            String fName = "[getImgFromSite1]";
            try{
               EmbedBuilder embedBuilder=new EmbedBuilder();
               embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle(gTitle);
               embedBuilder.setImage("http://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
               llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
            }
        }
        private void getImgFromSite2() {
            String fName = "[getImgFromSite2]";
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle("image2");
                embedBuilder.setDescription("https://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
                embedBuilder.setImage("https://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getGlobalRatelimit() {
            String fName = "[getImgFromSite2]";
            try{

                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorBlue1);embedBuilder.setTitle("image2");
                embedBuilder.setDescription("https://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
                embedBuilder.setImage("https://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
                llSendMessage(gTextChannel,embedBuilder);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getImgFromSite3() {
            String fName = "[getImgFromSite3]";
            try{
                InputStream is=lsStreamHelper.llGetInputStream4WebFile("https://redhusky.hostingerapp.com/discord/media/ndick/0f6bff40-bcdd-4a6d-aee9-c97b2b720aa4.png");
                lcWebHookBuild whh = new lcWebHookBuild();
                whh.doSafetyCleanwToken(gTextChannel);
                JSONObject json = new JSONObject();
                Member member = gEvent.getMember();
                String avatarUrl = gUser.getEffectiveAvatarUrl();
                logger.info(fName + ".avatarUrl=" + avatarUrl);
                assert member != null;
                json.put("name", member.getEffectiveName());
                json.put("avatarurl",avatarUrl);
                logger.info(fName + ".json=" +json.toString());
                if (!whh.preBuild(gTextChannel, json)) {
                    logger.error(fName + "failed prebuild");
                    if(whh.isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "channel maxed out");
                    }
                    return;
                }
                if (!whh.build()) {
                    logger.error(fName + "failed build");
                    if(whh.isChannelMaxedOut(gTextChannel)){
                        logger.error(fName + "channel maxed out");
                    }
                    return;
                }
                if (!whh.clientOpen()) {
                    logger.error(fName + "failed client open");
                    whh.delete();
                    return;
                }
                //ReadonlyMessage readonlyMessage= whh.sendReturnMessage(message2Post);
                whh.send(is,"image.png");
                Thread.sleep(2000);
                logger.info(fName + ".close and delete webhook");
                whh.clientClose();
                Thread.sleep(1000);
                whh.delete();
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getChannelPermission() {
            String fName = "[getChannelPermission]";
            try{
               List<PermissionOverride> permissionOverrides=gTextChannel.getMemberPermissionOverrides();
                logger.info(fName + ". permissionOverrides="+permissionOverrides.size());
                for(int i=0;i<permissionOverrides.size();i++){
                    PermissionOverride permissionOverride=permissionOverrides.get(i);
                    logger.info(fName + ". permissionOverride["+i+"].member="+permissionOverride.getMember().getUser().getName()+"("+permissionOverride.getMember().getId()+")");
                    EnumSet<Permission>inherit=permissionOverride.getInherit();
                    Iterator<Permission>inheritIterator=inherit.iterator();
                    EnumSet<Permission>allowed=permissionOverride.getAllowed();
                    Iterator<Permission>allowedIterator=allowed.iterator();
                    EnumSet<Permission>denied=permissionOverride.getDenied();
                    Iterator<Permission>deniedIterator=denied.iterator();
                    int j=0;
                    while(inheritIterator.hasNext()){
                        Permission permission=inheritIterator.next();
                        logger.info(fName + ". inherit["+j+"]="+permission.getName());
                        j++;
                    }
                    j=0;
                    while(allowedIterator.hasNext()){
                        Permission permission=allowedIterator.next();
                        logger.info(fName + ". allowed["+j+"]="+permission.getName());
                        j++;
                    }
                    j=0;
                    while(deniedIterator.hasNext()){
                        Permission permission=deniedIterator.next();
                        logger.info(fName + ". denied["+j+"]="+permission.getName());
                        j++;
                    }
                }
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void userflags() {
            String fName = "[userflags]";
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                User user= lsUserHelper.lsGetUser(gGuild,"299993180508651530");
                if(user==null){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No such user!");
                    return;
                }
                embed.addField("user",user.getAsMention(),false);
                EnumSet<User.UserFlag>euserFlags=user.getFlags();
                Iterator<User.UserFlag>iuserFlags=euserFlags.iterator();
                StringBuilder suserFlags=new StringBuilder();
                while (iuserFlags.hasNext()){
                    User.UserFlag userFlag=iuserFlags.next();
                    suserFlags.append("\n"+userFlag.getOffset()+":"+userFlag.getName());
                }
                logger.info(fName + ". builtinoutput:"+suserFlags);
                embed.addField("Built-in",suserFlags.toString(),false);
                int flagsRaw=user.getFlagsRaw();
                logger.info(fName + ". flagsRaw="+flagsRaw);
                EnumSet<User.UserFlag> foundFlags = EnumSet.noneOf(User.UserFlag.class);
                if (flagsRaw == 0) {
                    logger.info(fName + ". myoutput:none");
                } else {
                    User.UserFlag[] var2 = User.UserFlag.values();
                    int var3 = var2.length;
                    logger.info(fName + ". var3="+var3);
                    for(int var4 = 0; var4 < var3; ++var4) {
                        User.UserFlag flag = var2[var4];
                        logger.info(fName + ". var4="+var4+", flag:rawvalue="+flag.getRawValue()+", offset="+flag.getOffset()+", name="+flag.getName());
                        if (flag != User.UserFlag.UNKNOWN && (flagsRaw & flag.getRawValue()) == flag.getRawValue()) {
                            logger.info(fName + ". adding");
                            foundFlags.add(flag);
                        }
                    }
                }
                iuserFlags=foundFlags.iterator();
                suserFlags.setLength(0);
                while (iuserFlags.hasNext()){
                    User.UserFlag userFlag=iuserFlags.next();
                    suserFlags.append("\n"+userFlag.getOffset()+":"+userFlag.getName());
                }
                logger.info(fName + ". myoutput::"+suserFlags);
                embed.addField("Personal",suserFlags.toString(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void threadmessage1() {
            String fName = "[threadmessage1]";
            try{


            }
            catch (Exception e){
                logger.error(fName + ". exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
    
    
  //runLocal  
    }
}
