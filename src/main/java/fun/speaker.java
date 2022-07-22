package fun;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.la.aBasicCommandHandler;
import models.lc.webhook.lcWebHook2;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsChannelHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUserHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;
import restraints.in.iGag;
import restraints.in.iGagWork;
import restraints.in.iRestraints;
import restraints.models.enums.GAGLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class speaker extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints,iGagWork {
    Logger logger = Logger.getLogger(getClass()); 
    lcGlobalHelper gGlobal=lsGlobalHelper.sGetGlobal();
    String gTitle="Speaker",gCommand="speak";
    public speaker(){
        String fName="[constructor]";
        logger.info(fName);
        this.name = gTitle;
        this.help = "Temporary allows you to speak with different face&name.";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        //this.hidden=true;
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
    protected class runLocal extends aBasicCommandHandler implements Runnable {
        String cName = "[runLocal]";
        public runLocal(CommandEvent event) {
            String fName="runLocal";
            logger.info(cName + ".run build"); 
            setCommandHandlerValues(logger,event);
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                buildBasicFeatureControl(logger,"speaker",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
                        return;
                    }
                    if(ifItsAnAccessControlCommand()){
                        logger.info(fName+"its an AccessControlCommand");
                        return;
                    }
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                        return;
                    }
                    if(items[0].equalsIgnoreCase("status")){ status();isInvalidCommand=false;}
                    else if(items[0].equalsIgnoreCase("setname")){ setName();isInvalidCommand=false;}
                    else if(items[0].equalsIgnoreCase("setimage")){ setImage();isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("setmuffle")){ setGag(items[1]);isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("send")){ send2Channel(items[1]);isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("dminput")){ dmSend2Channel(items[1]);isInvalidCommand=false;}
                    else { send2Channel(items[0]);isInvalidCommand=false;}
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gEvent);
                logger.info(".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorPurple2);
            embedBuilder.addField("Sending messages 2 channel","`"+llPrefixStr+"speak <#channel> <text>`\n`"+llPrefixStr+"speak dminput <#channel>` to open Dm where you input your message.",false);
            embedBuilder.addField("Config","`"+llPrefixStr+"speak setname/setimage` will open DM to configure it.\n`"+llPrefixStr+"speak setmuffle <gag level>` mention gag level to simulate it",false);
            embedBuilder.addField("Important","All this data are temporary.",false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }
       
        String keyName="name",keyPicture="picture",keyMuffle="",keyTextChannel="textchannel";
        String profilePath="speaker";
        private void status( ) {
            String fName = "[status]";
            logger.info(cName + fName);
            getUserProfile(gMember,gGlobal,llGlobalHelper.llMemberProfile,profilePath,true);
            logger.info(cName + fName+"profile="+gUserProfile.getJson().toString());
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorPurple2);
            String name="(Not set)",gagLevel="(Not set)";
            if(gUserProfile.getJson().has(keyName)&&!gUserProfile.getJson().isNull(keyName)){
                name=gUserProfile.getJson().getString(keyName);
            }
            if(gUserProfile.getJson().has(keyMuffle)&&!gUserProfile.getJson().isNull(keyMuffle)){
                name=gUserProfile.getJson().getString(keyMuffle);
            }
            embedBuilder.addField("Name",name,false);
            embedBuilder.addField("Gag Level",gagLevel,false);
            if(gUserProfile.getJson().has(keyPicture)&&!gUserProfile.getJson().isNull(keyPicture)){
                embedBuilder.setThumbnail(gUserProfile.getJson().getString(keyPicture));
            }
            llSendMessage(gTextChannel,embedBuilder);
        }
        private void setGag(String command){
            String fName="setGag";
            command=command.toLowerCase();
            logger.info(fName+"command="+command);
            getUserProfile(gMember,gGlobal,llGlobalHelper.llMemberProfile,profilePath,true);
            if(command.equalsIgnoreCase(iGag.nUngag)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Loose.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Severe.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Extreme.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Mute.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Puppy.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Kitty.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }else
            if(command.equalsIgnoreCase(GAGLEVELS.Paci.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Pony.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Bird.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Sheep.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Piggy.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
            else
            if(command.equalsIgnoreCase(GAGLEVELS.Cow.getName())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);gUserProfile.getJson().put(keyMuffle,command);saveUserProfile(gGlobal);
            }
        }
        private void setName(){
            String fName="[setName]";
            logger.info(cName+fName);
            getUserProfile(gMember,gGlobal,llGlobalHelper.llMemberProfile,profilePath,true);
            llSendQuickEmbedMessage(gUser,gTitle,"Please enter the name you want to use while posting or type !cancel to cancel it.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(cName+fName+".content="+content);
                            if(!content.equalsIgnoreCase("!cancel")){
                                gUserProfile.getJson().put(keyName,content);saveUserProfile(gGlobal);
                                llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen1);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        }
                    },3, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                    });
        }
        private void setImage(){
            String fName="[setImage]";
            logger.info(cName+fName);
            Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image or type !cancel", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(cName+fName+".content="+content);
                            if(!content.equalsIgnoreCase("!cancel")){
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(cName + fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(cName + fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(cName + fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                        logger.error(fName + ".image too big");
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image height or width is above 1024px!", llColorRed);
                                        lsMessageHelper.lsMessageDelete(message);
                                        return;
                                    }
                                    String imageUrl=attachment.getUrl();
                                    gUserProfile.getJson().put(keyPicture,imageUrl);saveUserProfile(gGlobal);
                                    llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen1);
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    llSendQuickEmbedMessage(gUser, gTitle, "No attachment present!", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                }
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        lsMessageHelper.lsMessageDelete(message);
                    });
        }
        TextChannel textChannel2Post=null;
        private void send2Channel(String command){
            String fName="send2Channel";
            logger.info(fName+"command="+command);
            getUserProfile(gMember,gGlobal,llGlobalHelper.llMemberProfile,profilePath,true);
            if(!isChannelMentioned(command)){
                logger.warn(fName+".not mentioned in command");
                if(!gUserProfile.getJson().has(keyTextChannel)||gUserProfile.getJson().isNull(keyTextChannel)||lsChannelHelper.lsGetTextChannelById(gGuild,gUserProfile.getJson().getString(keyTextChannel))==null){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Please mention the channel you want to post it!", llColorRed);return;
                }
                textChannel2Post=lsChannelHelper.lsGetTextChannelById(gGuild,gUserProfile.getJson().getString(keyTextChannel));
            }else{
                gUserProfile.getJson().put(keyTextChannel,textChannel2Post.getId());
            }
            String  message2Post = gEvent.getArgs();
            converter(message2Post);
        }
        private void dmSend2Channel(String command){
            String fName="[dmSend2Channel]";
            logger.info(fName+"command="+command);
            getUserProfile(gMember,gGlobal,llGlobalHelper.llMemberProfile,profilePath,true);
            if(!isChannelMentioned(command)){
                logger.warn(fName+".not mentioned in command");
                if(!gUserProfile.getJson().has(keyTextChannel)||gUserProfile.getJson().isNull(keyTextChannel)||lsChannelHelper.lsGetTextChannelById(gGuild,gUserProfile.getJson().getString(keyTextChannel))==null){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Please mention the channel you want to post it!", llColorRed);return;
                }
                textChannel2Post=lsChannelHelper.lsGetTextChannelById(gGuild,gUserProfile.getJson().getString(keyTextChannel));
            }else{
                gUserProfile.getJson().put(keyTextChannel,textChannel2Post.getId());saveUserProfile(gGlobal);
            }
            llSendQuickEmbedMessage(gUser,gTitle,"Please enter the name you want to use while posting or type !cancel to cancel it.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(cName+fName+".content="+content);
                            if(!content.equalsIgnoreCase("!cancel")){
                                converter(content);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                    });
        }
        private boolean isChannelMentioned(String command){
            String fName="isChannelMentioned";
            try{
                logger.info(fName+"command="+command);
                command=command.replace("<!#","").replace("<#","").replace(">","");
                logger.info(fName+"converted="+command);
                textChannel2Post=lsChannelHelper.lsGetTextChannelById(gGuild,command);
                if(textChannel2Post==null){
                    return  false;
                }
                return true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);return  false;
            }
        }
        private void converter(String  message2Post){
            String fName="[converter]";
            logger.info(cName+fName);
            logger.info(cName + fName + "message= " + message2Post);
            String name=gMember.getEffectiveName();
            String avatar= lsUserHelper.getAuthorIcon(gUser);
            String gag="";
            if(gUserProfile.getJson().has(keyName)&&!gUserProfile.getJson().isNull(keyName)){
                name = gUserProfile.getJson().getString(keyName);
            }
            if(gUserProfile.getJson().has(keyPicture)&&!gUserProfile.getJson().isNull(keyPicture)){
                avatar = gUserProfile.getJson().getString(keyPicture);
            }
            if(gUserProfile.getJson().has(keyMuffle)&&!gUserProfile.getJson().isNull(keyMuffle)){
                gag= gUserProfile.getJson().getString(keyMuffle);
            }
            logger.info(cName + fName + ".name=" + name);
            logger.info(cName + fName + ".avatar=" + avatar);
            logger.info(cName + fName + ". gag=" +  gag);

            JSONObject jsonObject=new JSONObject();
            if(!gag.isEmpty()&&!gag.isBlank()){
                message2Post=stringManipulator(message2Post,gag);
            }
            jsonObject.put(lcWebHook2.keyContent, message2Post);
            jsonObject.put(lcWebHook2.keyName, name);
            jsonObject.put(lcWebHook2.keyAvatar, avatar);
            ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(textChannel2Post,jsonObject);
            if(readonlyMessage==null){
                logger.info(fName + ".send embed message");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setDescription(message2Post);
                embedBuilder.setColor(llColorPurple2);
                embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                if(lsMessageHelper.lsSendMessageResponse(textChannel2Post,embedBuilder)==null){
                    logger.info(fName + ".send normal message");
                    lsMessageHelper.lsSendMessageResponse(textChannel2Post,message2Post);
                }
            }
        }
        private String stringManipulator(String message,String severity){
            String fName="[stringManipulator]";
            logger.info(cName+fName+"severity="+severity);
            logger.info(cName+fName+"old="+message);
            String[] items;
            items =message.split("\\*|_");
            logger.info(cName + fName + ".items.size=" + items.length);
            logger.info(cName + fName + ".items=" + items);
            boolean hasAction=false;
            int c=0;
            for(int i=0;i<items.length;i++){
                c++;
                logger.info(cName + fName + ".c=" + c);
                if(c==2){
                    // 1 *2* 3 *4* 5
                    logger.info(cName + fName + ".ignore");
                    c=0;
                    if(!items[i].isEmpty()){
                        hasAction=true;
                        items[i]=items[i];
                    }
                }else{
                    logger.info(cName + fName + ".change");
                    if(!items[i].isEmpty()){
                        items[i]=stringReplacer(items[i],severity);
                    }
                }
            }
            logger.info(cName + fName + ".hasAction=" + hasAction);
            message="";

            StringBuilder messageBuilder = new StringBuilder(message);
            for(int i = 0; i<items.length; i++){
                if(hasAction&&i<items.length-1){
                    logger.info(cName + fName + ".default.item["+i+"].last="+items[i]);
                    messageBuilder.append(items[i]).append("*");
                }else
                if(hasAction&&c%2==0){
                    logger.info(cName + fName + ".default.item["+i+"].mod2="+items[i]);
                    messageBuilder.append(items[i]).append("*");
                } else{
                    logger.info(cName + fName + ".default.item["+i+"].mormal="+items[i]);
                    messageBuilder.append(items[i]);
                }
            }
            message = messageBuilder.toString();


            logger.info(cName+fName+"new="+message);
            return message;
        }
        private String stringReplacer(String message,String severity){
            String fName="[stringReplacer]";
            logger.info(cName+fName+"severity="+severity);
            logger.info(cName+fName+"old="+message);
            if(severity.equalsIgnoreCase(GAGLEVELS.Mute.getName())) {
                message ="#";
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Paci.getName())) {
                message ="s";
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Loose.getName())) {
                message = message.replaceAll("l", "w").replaceAll("R", "W").replaceAll("L", "W").replaceAll("s", "f").replaceAll("S", "F").replaceAll("t", "g").replaceAll("T", "G");
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Severe.getName())) {
                message = message.replaceAll("[abjsvz]", "r").replaceAll("[ABJSVZ]", "R").replaceAll("[dklv]", "f").replaceAll("[DKLV]", "F").replaceAll("[gx]", "n").replaceAll("[GX]", "N").replaceAll("[himu]", "d").replaceAll("[HIMU]", "D").replaceAll("q", "m").replaceAll("Q", "M");
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Extreme.getName())) {
                message = message.replaceAll("[BDKTV]", "Mph").replaceAll("[bdktv]", "m").replaceAll("[DJLQR]", "M").replaceAll("[djlqr]", "ph").replaceAll("[AEIOU]", "Mph").replaceAll("[aeiou]", "m").replaceAll("[CVNY]", "Mh").replaceAll("[cvny]", "ph").replaceAll("[WZX]", "Mf").replaceAll("[wzx]", "f").replaceAll("S", "h").replaceAll("s", "m");
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Mute.getName())) {
                message = "#######";
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Puppy.getName())) {
                message= puppyGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Kitty.getName())) {
                message=kittenGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Pony.getName())) {
                message=ponyGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Piggy.getName())) {
                message=piggyGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())) {
                message=dinosaurGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())) {
                message=squeakyGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Bird.getName())) {
                message=birdGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Cow.getName())) {
                message=cowGag(message);
            }
            if(severity.equalsIgnoreCase(GAGLEVELS.Sheep.getName())) {
                message=sheepGag(message);
            }
            logger.info(cName+fName+"new="+message);
            return message;
        }

       



    }

}
