package fun;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.lcBasicFeatureControl;
import models.lc.webhook.lcWebHook2;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.in.iGag;
import restraints.in.iGagWork;
import restraints.in.iRestraints;
import restraints.models.GAGLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class speaker extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints,iGagWork {
    Logger logger = Logger.getLogger(getClass()); 
    lcGlobalHelper gGlobal;
    EventWaiter gWaiter;
    String gTitle="Speaker",gCommand="speak";
    public speaker(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;gWaiter=gGlobal.waiter;
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
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
        public runLocal(CommandEvent event) {
            String fName="runLocal";
            logger.info(cName + ".run build"); gEvent = event;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"speaker",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand = true;
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){ help("main");isInvalidCommand=false;}
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
                    else if(items[0].equalsIgnoreCase("status")){ status();isInvalidCommand=false;}
                    else if(items[0].equalsIgnoreCase("setname")){ setName();isInvalidCommand=false;}
                    else if(items[0].equalsIgnoreCase("setimage")){ setImage();isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("setmuffle")){ setGag(items[1]);isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("send")){ send2Channel(items[1]);isInvalidCommand=false;}
                    else if(items.length>=2&&items[0].equalsIgnoreCase("dminput")){ dmSend2Channel(items[1]);isInvalidCommand=false;}
                    else { send2Channel(items[0]);isInvalidCommand=false;}
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gEvent);
            /*if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }*/
                logger.info(".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
        JSONObject profile=new JSONObject();
        String keyName="name",keyPicture="picture",keyMuffle="",keyTextChannel="textchannel";
        String profilePath="";
        private void getUserProfile(){
            String fName="getUserProfile";
            try{
                logger.info(fName);
                profilePath=gTitle+"_"+gGuild.getId();
                logger.info(fName+"gGlobal.jsonObject="+gGlobal.jsonObject.toString());
                logger.info(fName+"profilePath="+profilePath);
                if(gGlobal.jsonObject.has(profilePath)&&!gGlobal.jsonObject.isNull(profilePath)){
                    logger.info(fName+"has "+profilePath);
                    logger.info(fName+"gUse="+ gUser.getId());
                    if(gGlobal.jsonObject.getJSONObject(profilePath).has(gUser.getId())&&!gGlobal.jsonObject.getJSONObject(profilePath).isNull(gUser.getId())){
                        logger.info(fName+"has "+gUser.getId());
                        profile=gGlobal.jsonObject.getJSONObject(profilePath).getJSONObject(gUser.getId());
                        logger.info(fName+"profile="+ profile.toString());
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void saveUserProfile(){
            String fName="saveUserProfile";
            try{
                logger.info(fName); JSONObject tmpA=new JSONObject();
                profilePath=gTitle+"_"+gGuild.getId();
                logger.info(fName+"gGlobal.jsonObject= "+gGlobal.jsonObject.toString());
                if(gGlobal.jsonObject.has(profilePath)&&!gGlobal.jsonObject.isNull(profilePath)){
                    logger.info(fName+"has "+profilePath);
                    tmpA=gGlobal.jsonObject.getJSONObject(profilePath);
                    tmpA.put(gUser.getId(),profile);
                    gGlobal.jsonObject.put(profilePath,tmpA);
                    logger.info(fName+" updated "+profilePath);
                    logger.info(fName+" profile "+tmpA.toString());
                }else{
                    tmpA.put(gUser.getId(),profile);
                    gGlobal.jsonObject.put(profilePath,tmpA);
                    logger.info(fName+" new "+profilePath);
                    logger.info(fName+" profile "+tmpA.toString());
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void status( ) {
            String fName = "[status]";
            logger.info(cName + fName);
            getUserProfile();
            logger.info(cName + fName+"profile="+profile.toString());
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorPurple2);
            String name="(Not set)",gagLevel="(Not set)";
            if(profile.has(keyName)&&!profile.isNull(keyName)){
                name=profile.getString(keyName);
            }
            if(profile.has(keyMuffle)&&!profile.isNull(keyMuffle)){
                name=profile.getString(keyMuffle);
            }
            embedBuilder.addField("Name",name,false);
            embedBuilder.addField("Gag Level",gagLevel,false);
            if(profile.has(keyPicture)&&!profile.isNull(keyPicture)){
                embedBuilder.setThumbnail(profile.getString(keyPicture));
            }
            llSendMessage(gTextChannel,embedBuilder);
        }
        private void setGag(String command){
            String fName="setGag";
            try{
                command=command.toLowerCase();
                logger.info(fName+"command="+command);
                getUserProfile();
                if(command.equalsIgnoreCase(iGag.nUngag)){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Loose.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Severe.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Extreme.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Mute.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Puppy.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Kitty.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Paci.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Pony.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Bird.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Sheep.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Piggy.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
                else
                if(command.equalsIgnoreCase(GAGLEVELS.Cow.getName())){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Set speakers muffle to: "+command, llColorBlue1);profile.put(keyMuffle,command);saveUserProfile();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void setName(){
            String fName="[setName]";
            logger.info(cName+fName);
            try{
                getUserProfile();
                llSendQuickEmbedMessage(gUser,gTitle,"Please enter the name you want to use while posting or type !cancel to cancel it.", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(cName+fName+".content="+content);
                                if(!content.equalsIgnoreCase("!cancel")){
                                    profile.put(keyName,content);saveUserProfile();
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

            } catch (Exception e) {
                logger.error(cName + fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void setImage(){
            String fName="[setImage]";
            logger.info(cName+fName);
            try{
                Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image or type !cancel", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
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
                                        profile.put(keyPicture,imageUrl);saveUserProfile();
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

            } catch (Exception e) {
                logger.error(cName + fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        TextChannel textChannel2Post=null;
        private void send2Channel(String command){
            String fName="send2Channel";
            try{
                logger.info(fName+"command="+command);
                getUserProfile();
                if(!isChannelMentioned(command)){
                    logger.warn(fName+".not mentioned in command");
                    if(!profile.has(keyTextChannel)||profile.isNull(keyTextChannel)||lsChannelHelper.lsGetTextChannelById(gGuild,profile.getString(keyTextChannel))==null){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Please mention the channel you want to post it!", llColorRed);return;
                    }
                    textChannel2Post=lsChannelHelper.lsGetTextChannelById(gGuild,profile.getString(keyTextChannel));
                }else{
                    profile.put(keyTextChannel,textChannel2Post.getId());
                }
                String  message2Post = gEvent.getArgs();
                converter(message2Post);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);return;
            }
        }
        private void dmSend2Channel(String command){
            String fName="[dmSend2Channel]";
            logger.info(cName+fName);
            try{
                logger.info(fName+"command="+command);
                getUserProfile();
                if(!isChannelMentioned(command)){
                    logger.warn(fName+".not mentioned in command");
                    if(!profile.has(keyTextChannel)||profile.isNull(keyTextChannel)||lsChannelHelper.lsGetTextChannelById(gGuild,profile.getString(keyTextChannel))==null){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Please mention the channel you want to post it!", llColorRed);return;
                    }
                    textChannel2Post=lsChannelHelper.lsGetTextChannelById(gGuild,profile.getString(keyTextChannel));
                }else{
                    profile.put(keyTextChannel,textChannel2Post.getId());saveUserProfile();
                }
                llSendQuickEmbedMessage(gUser,gTitle,"Please enter the name you want to use while posting or type !cancel to cancel it.", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
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

            } catch (Exception e) {
                logger.error(cName + fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
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
            try{
                //String  message2Post = gEvent.getArgs();
                logger.info(cName + fName + "message= " + message2Post);
                String name=gMember.getEffectiveName();
                String avatar= lsUserHelper.getAuthorIcon(gUser);
                String gag="";
                if(profile.has(keyName)&&!profile.isNull(keyName)){
                    name = profile.getString(keyName);
                }
                if(profile.has(keyPicture)&&!profile.isNull(keyPicture)){
                    avatar = profile.getString(keyPicture);
                }
                if(profile.has(keyMuffle)&&!profile.isNull(keyMuffle)){
                    gag= profile.getString(keyMuffle);
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
            } catch (Exception e) {
                logger.error(cName + fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
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
                        e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
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
                            logger.info(fName+"timeout");
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
