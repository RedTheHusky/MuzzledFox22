package userprofiles;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
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
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import forRemoval.social.lcSocialization;
import userprofiles.entities.rSlaveRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class nSlaveRegistry extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, rSlaveRegistry {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="slaveregistry", gTitle="Slave Registry",profileName="nslaveRegistry";
    public nSlaveRegistry(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Slave Registry";
        this.help = "Creating/Displaying your slave registry.";
        this.aliases = new String[]{KeyTag,"slavern"};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=false;
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
        User gUser;Member gMember;Guild gGuild;TextChannel gTextChannel;
        lcSocialization socialization;
        public runLocal(CommandEvent ev) {
            logger.info(".run build");String fName="run";
            gEvent = ev;
            gWaiter =gGlobal.waiter;
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
            boolean isInvalidCommand = true;
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"nslaveregistry",gGlobal);
                gBasicFeatureControl.initProfile();
                if(!isNSFW()){
                    blocked();return;
                }
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
                    }else
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
                    }else
                    if(items[0].equalsIgnoreCase("start")||items[0].equalsIgnoreCase("create")||items[0].equalsIgnoreCase("edit")){
                        edit();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("view")){
                        view();isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("delete")){
                        delete();isInvalidCommand=false;
                    }else{
                        List<Member>mebers=gEvent.getMessage().getMentionedMembers();
                        Member target;
                        if(!mebers.isEmpty()){
                            logger.info(fName+".is mentioned in message");
                            target=mebers.get(0);
                        }else{
                            target=llGetMember(gGuild,items[0]);
                        }
                        if(target!=null){
                            logger.info(fName+".has mention");
                            if(items.length>=2&&items[1].equalsIgnoreCase("view")){
                                view(target);isInvalidCommand=false;
                            }
                        }
                    }
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
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
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);

            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);
            embed.setColor(llColorPurple1);
            embed.addField("IMPORTANT","This is the NSFW profile!!!",false);
            embed.addField("View","Use `"+llPrefixStr+KeyTag+"[@User] view` to view existing user profile.\nIn case an @User is mentioned, it will display their user profile",false);
            embed.addField("Start","Use `"+llPrefixStr+KeyTag+" start` to create&edit your profile.",false);
            embed.addField("Delete","Use `"+llPrefixStr+KeyTag+" delete` to delete an existing profile.\nYou can delete only your profile.",false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+llPrefixStr+KeyTag+" guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private void noMention() {
            String fName = "[noMention]";
            logger.info(fName);
            String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }
        private void noSlot() {
            String fName = "[noSlot]";
            logger.info(fName);
            String desc="`Please mention the index number like `"+llPrefixStr+"+fursona edit 1` or `"+llPrefixStr+"fursona @User view 1`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }
        String strNoCharacters="No characters",strFailed2LoadProfile="Failed to load profile!", strFailed2SaveProfile="Failed to save profile!",strIndexOutOfBound="Index out of bound. Please try between 0-";
        private void view() {
            String fName = "[view]";
            logger.info(fName);
            if(!getProfile(gUser)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
            }
            JSONObject jsonObject=gUserProfile.jsonObject;
            String img="",name="", gender="",description="",owner="",status="",service="";
            if(jsonObject.has(keyIsCommonlyKnownAs)&&!jsonObject.isNull(keyIsCommonlyKnownAs)){
                name=jsonObject.getString(keyIsCommonlyKnownAs);
            }
            if(jsonObject.has(keyGender)&&!jsonObject.isNull(keyGender)){
                gender=jsonObject.getString(keyGender);
            }
            if(jsonObject.has(keyDesc)&&!jsonObject.isNull(keyDesc)){
                description=jsonObject.getString(keyDesc);
            }
            if(jsonObject.has(keyImg)&&!jsonObject.isNull(keyImg)){
                img=jsonObject.getString(keyImg);
            }
            if(jsonObject.has(keyCurrentStatus)&&!jsonObject.isNull(keyCurrentStatus)){
                status=jsonObject.getString(keyCurrentStatus);
            }
            if(jsonObject.has(keyServiceType)&&!jsonObject.isNull(keyServiceType)){
                service=jsonObject.getString(keyServiceType);
            }
            if(jsonObject.has(keyOwner)&&!jsonObject.isNull(keyOwner)){
                owner=jsonObject.getString(keyOwner);
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.addField("Slave Nr:", rSlaveRegistry.iGenerateSlaveNumber(gUserProfile.getUser()),false);
            if(!name.isBlank()){
                embed.addField("Is Known As",name,true);
            }
            if(!gender.isBlank()){
                embed.addField("Gender",gender,true);
            }
            if(!status.isBlank()){
                embed.addField("Status",status,false);
                if(!status.equalsIgnoreCase(valueFree_Unowned)){
                    if(!owner.isBlank()){
                        embed.addField("Owner/Dominant",iGetOwnerMention(gGuild, owner),false);
                    }
                    if(!service.isBlank()){
                        embed.addField("Service",service,false);
                    }
                }
            }
            if(!description.isBlank()){
                embed.addField("Description",description,false);
            }
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
            llSendMessage(gTextChannel,embed);
        }
        private void delete() {
            String fName = "[delete]";
            logger.info(fName);
            if(!getProfile(gUser)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
            }
            gUserProfile.jsonObject =new JSONObject();
            if(!saveProfile()){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
            }else{
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Deleted character", llColorGreen1);
            }
        }
        boolean isBooster=false;
        private void create() {
            String fName = "[create]";
            logger.info(fName);
            if(!getProfile(gUser)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
            }
            menuMain(false);
        }
        private void edit() {
            String fName = "[edit]";
            logger.info(fName);
            if(!getProfile(gUser)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
            }
            menuMain(true);
        }
        private void view(Member target) {
            String fName = "[view]";
            logger.info(fName);
            logger.info(fName+"target="+target.getId());
            if(!getProfile(target.getUser())){
                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
            }
            JSONObject jsonObject=gUserProfile.jsonObject;
            String img="",name="", gender="",description="",owner="",status="",service="";
            if(jsonObject.has(keyIsCommonlyKnownAs)&&!jsonObject.isNull(keyIsCommonlyKnownAs)){
                name=jsonObject.getString(keyIsCommonlyKnownAs);
            }
            if(jsonObject.has(keyGender)&&!jsonObject.isNull(keyGender)){
                gender=jsonObject.getString(keyGender);
            }
            if(jsonObject.has(keyDesc)&&!jsonObject.isNull(keyDesc)){
                description=jsonObject.getString(keyDesc);
            }
            if(jsonObject.has(keyImg)&&!jsonObject.isNull(keyImg)){
                img=jsonObject.getString(keyImg);
            }
            if(jsonObject.has(keyCurrentStatus)&&!jsonObject.isNull(keyCurrentStatus)){
                status=jsonObject.getString(keyCurrentStatus);
            }
            if(jsonObject.has(keyServiceType)&&!jsonObject.isNull(keyServiceType)){
                service=jsonObject.getString(keyServiceType);
            }
            if(jsonObject.has(keyOwner)&&!jsonObject.isNull(keyOwner)){
                owner=jsonObject.getString(keyOwner);
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("Slave Nr. "+ rSlaveRegistry.iGenerateSlaveNumber(gUserProfile.getUser()));
            //embed.addField("Slave Nr:", rSlaveRegistry.iGenerateSlaveNumber(gUserProfile.getUser()),false);
            if(!name.isBlank()){
                embed.addField("Is Known As",name,true);
            }
            if(!gender.isBlank()){
                embed.addField("Gender",gender,true);
            }
            if(!status.isBlank()){
                embed.addField("Status",status,false);
                if(!status.equalsIgnoreCase(valueFree_Unowned)){
                    if(!owner.isBlank()){
                        embed.addField("Owner/Dominant",iGetOwnerMention(gGuild, owner),false);
                    }
                    if(!service.isBlank()){
                        embed.addField("Service",service,false);
                    }
                }
            }
            if(!description.isBlank()){
                embed.addField("Description",description,false);
            }
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
            llSendMessage(gTextChannel,embed);
        }
        EventWaiter gWaiter;
        private void menuMain(boolean isEdit){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.addField("Nickname","Select :one: for nickname.",false);
                embed.addField("Gender","Select :two: for gender.",false);
                embed.addField("Status","Select :three: for status.",false);
                embed.addField("Service","Select :four: for service type.",false);
                embed.addField("Owner/Dominant","Select :five: for owner/dominant.",false);
                embed.addField("Description","Select :six: for description.",false);
                embed.addField("Image","Select :seven: for image.",false);
                embed.addField("Done","Select ::white_check_mark: : to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(lsUnicodeEmotes.unicodeEmote_One).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Two).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Three).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Four).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Five).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Six).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Seven).queue();
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_One)){
                                    menuNickName(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Two)){
                                    menuGender(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                                    menuStatus(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                                    menuServiceType(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Five)){
                                    menuOwner(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Six)){
                                    menuDescription(isEdit);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Seven)){
                                    menuImage(isEdit);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                                    return;
                                }else{
                                    menuMain(isEdit);
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

        private String getCharacterInfo(String key){
            String fName="[getCharacterInfo]";
            logger.info(fName);
            if(gUserProfile.jsonObject ==null){
               return "";
            }else{
                if(gUserProfile.jsonObject.has(key)){
                    return  gUserProfile.jsonObject.getString(key);
                }
            }
            return "";
        }
        private void menuNickName(boolean isEdit){
            String fName="[menuNickName]";
            logger.info(fName);
            try{
                String name=getCharacterInfo(keyIsCommonlyKnownAs);Message message;
                if(name!=null&&!name.isBlank()&&!name.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current name is "+name+".\nPlease enter nickname:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter nickname:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyIsCommonlyKnownAs,content);
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuMain(isEdit);llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuOwner(boolean isEdit){
            String fName="[menuOwner]";
            logger.info(fName);
            try{
                String owner=getCharacterInfo(keyOwner);Message message;
                if(owner!=null&&!owner.isBlank()&&!owner.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current owner/dominant is "+owner+".\nPlease enter owner/dominant as `Name#Discriminator`, discriminator is the number after the name.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter owner/dominant as `Name#Discriminator`, discriminator is the number after the name.", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyOwner,content);
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuMain(isEdit);llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuStatus(boolean isEdit){
            String fName="[menuStatus]";
            logger.info(fName);
            try{
                String status=getCharacterInfo(keyCurrentStatus);
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);
                String options="";
                options+="\nSelect :one: for "+valueFree_Unowned+".";
                 options+="\nSelect :two: for "+valueSubmissive+".";
                 options+="\nSelect :three: for "+valueSubmissiveInTraining+".";
                 options+="\nSelect :four: for "+valueCollaredSubmissive+".";
                 options+="\nSelect :five: for "+valueInServiceSubmissive+".";
                 options+="\nSelect :six: for "+valuePet+".";
                 options+="\nSelect :seven: for "+valueSlaveInTraining+".";
                 options+="\nSelect :eight: for "+valueOwnedSlave+".";
                if(status!=null&&!status.isBlank()&&!status.isEmpty()){
                    embedBuilder.setDescription("Current status is: "+status+".\nPlease select a new one"+options);
                }else{
                    embedBuilder.setDescription("Please select a status"+options);
                }

                Message message=llSendMessageResponse_withReactionNotification(gUser,embedBuilder);
                message.addReaction(lsUnicodeEmotes.unicodeEmote_One).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Two).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Three).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Four).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Five).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Six).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Seven).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Eight).queue();
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                //loadCharacter();
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_One)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueFree_Unowned);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Two)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueSubmissive);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueSubmissiveInTraining);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueCollaredSubmissive);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Five)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueInServiceSubmissive);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Six)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valuePet);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Seven)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueSlaveInTraining);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Eight)){
                                    gUserProfile.jsonObject.put(keyCurrentStatus,valueOwnedSlave);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    menuMain(isEdit);
                                }else{
                                    menuMain(isEdit);
                                }
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuServiceType(boolean isEdit){
            String fName="[menuServiceType]";
            logger.info(fName);
            try{
                String serviceType=getCharacterInfo(keyServiceType);
                EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setColor(llColorBlue1);
                String options="";
                options+="\nSelect :one: for "+valueBeingTrainedBy+".";
                options+="\nSelect :two: for "+valueInServiceTo+".";
                options+="\nSelect :three: for "+valueCollaredTo+".";
                options+="\nSelect :four: for "+valueOwnedBy+".";
                if(serviceType!=null&&!serviceType.isBlank()&&!serviceType.isEmpty()){
                    embedBuilder.setDescription("Current service type is: "+serviceType+".\nPlease select service type."+options);
                }else{
                    embedBuilder.setDescription("Please select service type."+options);
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embedBuilder);
                message.addReaction(lsUnicodeEmotes.unicodeEmote_One).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Two).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Three).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Four).queue();
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                //loadCharacter();
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_One)){
                                    gUserProfile.jsonObject.put(keyServiceType,valueBeingTrainedBy);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Two)){
                                    gUserProfile.jsonObject.put(keyServiceType,valueInServiceTo);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                                    gUserProfile.jsonObject.put(keyServiceType,valueCollaredTo);
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                                    gUserProfile.jsonObject.put(keyServiceType,valueOwnedBy);
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    menuMain(isEdit);
                                }else{
                                    menuMain(isEdit);
                                }
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuGender(boolean isEdit){
            String fName="[menuGender]";
            logger.info(fName);
            try{
                String gender=getCharacterInfo(keyGender);
                Message message;
                if(gender!=null&&!gender.isBlank()&&!gender.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current gender is "+gender+".\nPlease enter gender:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter gender:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyGender,content);
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                llMessageDelete(message);menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void timeout(){

        }
        private void menuDescription(boolean isEdit){
            String fName="[menuDescription";
            logger.info(fName);
            try{
                String description=getCharacterInfo(keyDesc);Message message;
                if(description!=null&&!description.isBlank()&&!description.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current description is "+description+".\nPlease enter description:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter description:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyDesc,content);
                                //saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                llMessageDelete(message);menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuImage(boolean isEdit){
            String fName="[menuDescription";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image.\nIf the image can't be sent do to Discord explicit content filter, please type `!channel` to ask you in guild.", llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content=e.getMessage().getContentStripped();
                                if(content!=null){
                                    logger.info(fName+".content="+content);
                                    if(content.equalsIgnoreCase("!channel")){
                                        llMessageDelete(message); menuImageGuild(isEdit);
                                        return;
                                    }
                                }
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        menuMain(isEdit); llMessageDelete(message);
                                        return;
                                    }
                                    String imageUrl=attachment.getUrl();
                                    //loadCharacter();
                                    gUserProfile.jsonObject.put(keyImg,imageUrl);
                                    //saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                }else{
                                    try{
                                        EmbedBuilder testEmbed=new EmbedBuilder();
                                        testEmbed.setDescription("test");testEmbed.setImage(content);
                                        testEmbed.build();
                                        //loadCharacter();
                                        gUserProfile.jsonObject.put(keyImg,content);
                                        //saveCharacter();
                                        if(!saveProfile()){
                                            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                        }
                                    }  catch (Exception e2) {
                                        logger.error(fName+".exception=" + e2);
                                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                    }
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuImageGuild(boolean isEdit){
            String fName="[menuDescription";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Please attach an image, "+gMember.getAsMention()+".", llColorBlue1);
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content=e.getMessage().getContentStripped();
                                if(content!=null){
                                    logger.info(fName+".content="+content);
                                    if(content.equalsIgnoreCase("!channel")){
                                        llMessageDelete(message); menuImageGuild(isEdit);
                                        return;
                                    }
                                }
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        menuMain(isEdit); llMessageDelete(message);
                                        return;
                                    }
                                    String imageUrl=attachment.getUrl();
                                    //loadCharacter();
                                    gUserProfile.jsonObject.put(keyImg,imageUrl);
                                    //saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                }else{
                                    try{
                                        EmbedBuilder testEmbed=new EmbedBuilder();
                                        testEmbed.setDescription("test");testEmbed.setImage(content);
                                        testEmbed.build();
                                        //loadCharacter();
                                        gUserProfile.jsonObject.put(keyImg,content);
                                        //saveCharacter();
                                        if(!saveProfile()){
                                            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                        }
                                    }  catch (Exception e2) {
                                        logger.error(fName+".exception=" + e2);
                                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                        llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                    }
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());menuMain(isEdit);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String iGetOwnerMention(Guild guild, String name) {
            String fName = "[iGetOwnerMention]";
            try {
                logger.warn(fName+"name="+name);
                String[] items = name.split("#");
                String nr="",text="";
                if(items.length==0){
                    return name;
                }
                for(int i=0;i<items.length;i++){
                    if(i==items.length-1){
                        nr=items[i];
                    }else{
                        text+=items[i];
                    }
                }
                logger.warn(fName+"text="+text+", nr="+nr);
                List<Member> members=guild.getMembersByName(text,false);
                for(Member member:members){
                    if(member.getUser().getDiscriminator().equalsIgnoreCase(nr)){
                        return member.getAsMention();
                    }
                }
                return name;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return name;
            }
        }
        lcJSONUserProfile gUserProfile;
        private Boolean getProfile(User user){
            String fName="[getProfile]";
            logger.info(fName);
            try{
                logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
                gUserProfile=gGlobal.getUserProfile(profileName,user,gGuild,profileName);
                if(gUserProfile!=null&&gUserProfile.isProfile()){
                    logger.info(fName + ".is locally cached");
                }else{
                    logger.info(fName + ".need to get or create");
                    gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild,profileName);
                    if(gUserProfile.getProfile(table)){
                        logger.info(fName + ".has sql entry");
                    }
                }
                gUserProfile=iSafetyUserProfileEntry(gUserProfile);
                gGlobal.putUserProfile(gUserProfile,profileName);
                if(!gUserProfile.isUpdated){
                    logger.info(fName + ".no update>ignore");return true;
                }
                if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
                return true; 
                
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            try{
                gGlobal.putUserProfile(gUserProfile,profileName);
                if(gUserProfile.saveProfile(table)){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
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
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+ lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
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
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+KeyTag+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+KeyTag+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+KeyTag+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+KeyTag+" server blockroles :four:/list|add|rem|set|clear`",false);
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

    public nSlaveRegistry(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        Runnable r = new runUtility(global,guild,user,command);
        new Thread(r).start();
    }
    protected class runUtility implements Runnable {
        String cName = "[runReset]";

        lcGlobalHelper gGlobal;
        Guild gGuild;
        User gUser;
        String gCommand;

        public runUtility(lcGlobalHelper global, Guild g, User user,String command) {
            logger.info(".run build");
            gGlobal = global;
            gUser=user;
            gGuild = g;
            gCommand=command;

        }
        lcJSONUserProfile gUserProfile;
        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                if(gCommand.equalsIgnoreCase("reset")){
                    getProfile();
                    gUserProfile.jsonObject =new JSONObject();
                    saveProfile();
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private Boolean getProfile(){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
            gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");  return true;
            }
            return false;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
    }
}
