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
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import forRemoval.social.lcSocialization;
import userprofiles.entities.rUsersona;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class nusersona extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, rUsersona {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="nmembersona", gTitle="NSFW Member Profile",profileName="nusersona";
    public nusersona(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "NMember Profile";
        this.help = "Creating/Displaying your member profile. NSFW edition";
        this.aliases = new String[]{KeyTag,"nusersona"};
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
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"nusersona",gGlobal);
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
            String img="",name="<n/a>", age="<n/a>",gender="<n/a>",sexuality="</a>",likes="<n/a>",hates="<n/a>",description="<n/a>";
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)){
                name=jsonObject.getString(keyName);
            }
            if(jsonObject.has(keyAge)&&!jsonObject.isNull(keyAge)){
                age=jsonObject.getString(keyAge);
            }
            if(jsonObject.has(keySexuality)&&!jsonObject.isNull(keySexuality)){
                sexuality=jsonObject.getString(keySexuality);
            }
            if(jsonObject.has(keyGender)&&!jsonObject.isNull(keyGender)){
                gender=jsonObject.getString(keyGender);
            }
            if(jsonObject.has(keyLikes)&&!jsonObject.isNull(keyLikes)){
                likes=jsonObject.getString(keyLikes);
            }
            if(jsonObject.has(keyHates)&&!jsonObject.isNull(keyHates)){
                hates=jsonObject.getString(keyHates);
            }
            if(jsonObject.has(keyDesc)&&!jsonObject.isNull(keyDesc)){
                description=jsonObject.getString(keyDesc);
            }

            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.addField("Name",name,false);
            embed.addField("Age",age,false);
            embed.addField("Gender",gender,false);
            embed.addField("Sexuality",sexuality,false);
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
            embed.setAuthor(gUser.getName()+"#"+gUser.getDiscriminator(),null,gUser.getEffectiveAvatarUrl());
            embed.addField("Likes",likes,false);
            embed.addField("Hates",hates,false);
            embed.addField("Description",description,false);
            String timezone="<n/a>",links="<n/a>";
            if(jsonObject.has(keyTimeZone)&&!jsonObject.isNull(keyTimeZone)){
                timezone=jsonObject.getString(keyTimeZone);
            }
            if(jsonObject.has(keyLinks)&&!jsonObject.isNull(keyLinks)){
                links=jsonObject.getString(keyLinks);
            }
            embed.addField("Timezone&Country",timezone,false);
            embed.addField("Links",links,false);
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
            String img="",name="<n/a>", age="<n/a>",gender="<n/a>",sexuality="</a>",likes="<n/a>",hates="<n/a>",description="<n/a>";
            if(jsonObject.has(keyName)&&!jsonObject.isNull(keyName)){
                name=jsonObject.getString(keyName);
            }
            if(jsonObject.has(keyAge)&&!jsonObject.isNull(keyAge)){
                age=jsonObject.getString(keyAge);
            }
            if(jsonObject.has(keySexuality)&&!jsonObject.isNull(keySexuality)){
                sexuality=jsonObject.getString(keySexuality);
            }
            if(jsonObject.has(keyGender)&&!jsonObject.isNull(keyGender)){
                gender=jsonObject.getString(keyGender);
            }
            if(jsonObject.has(keyLikes)&&!jsonObject.isNull(keyLikes)){
                likes=jsonObject.getString(keyLikes);
            }
            if(jsonObject.has(keyHates)&&!jsonObject.isNull(keyHates)){
                hates=jsonObject.getString(keyHates);
            }
            if(jsonObject.has(keyDesc)&&!jsonObject.isNull(keyDesc)){
                description=jsonObject.getString(keyDesc);
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.addField("Name",name,false);
            embed.addField("Age",age,false);
            embed.addField("Gender",gender,false);
            embed.addField("Sexuality",sexuality,false);
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
            embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
            embed.addField("Likes",likes,false);
            embed.addField("Hates",hates,false);
            embed.addField("Description",description,false);
            String timezone="<n/a>",links="<n/a>";
            if(jsonObject.has(keyTimeZone)&&!jsonObject.isNull(keyTimeZone)){
                timezone=jsonObject.getString(keyTimeZone);
            }
            if(jsonObject.has(keyLinks)&&!jsonObject.isNull(keyLinks)){
                links=jsonObject.getString(keyLinks);
            }
            embed.addField("Timezone&Country",timezone,false);
            embed.addField("Links",links,false);
            llSendMessage(gTextChannel,embed);
        }
        EventWaiter gWaiter;
        private void menuMain(boolean isEdit){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.addField("Name","Select :one: for name.",false);
                embed.addField("Age","Select :two: for age.",false);
                embed.addField("Gender","Select :three: for gender.",false);
                embed.addField("Sexuality","Select :four: for sexuality.",false);
                embed.addField("Likes","Select :five: for likes.",false);
                embed.addField("Hates","Select :six: for hates.",false);
                embed.addField("Description","Select :seven: for desc.",false);
                embed.addField("Country/Timezone","Select :eight: for adding coutry/timezone.",false);
                embed.addField("Links","Select :nine: for adding links",false);
                embed.addField("Done","Select ::white_check_mark: : to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(lsUnicodeEmotes.unicodeEmote_One).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Two).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Three).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Four).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Five).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Six).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Seven).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Eight).queue();
                message.addReaction(lsUnicodeEmotes.unicodeEmote_Nine).queue();
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
                                    menuName(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Two)){
                                    menuAge(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Three)){
                                    menuGender(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Four)){
                                    menuSexuality(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Five)){
                                    menuLikes(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Six)){
                                    menuHates(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Seven)){
                                    menuDescription(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Eight)){
                                    menuTimezone(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(name.contains(lsUnicodeEmotes.unicodeEmote_Nine)){
                                    menuLinks(isEdit);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                }else
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                                    llMessageDelete(e.getChannel(),e.getMessageId());
                                    return;
                                }else{
                                    menuMain(isEdit);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
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
        private void menuName(boolean isEdit){
            String fName="[menuName]";
            logger.info(fName);
            try{
                String name=getCharacterInfo(keyName); Message message;
                if(name!=null&&!name.isBlank()&&!name.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current name is "+name+".\nPlease enter name:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter name:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyName,content);
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
        private void menuTimezone(boolean isEdit){
            String fName="[menuTimezone]";
            logger.info(fName);
            try{
                String species=getCharacterInfo(keyTimeZone); Message message;
                if(species!=null&&!species.isBlank()&&!species.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current timezone/country is "+species+".\nPlease enter the new one:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter timezone/country:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyTimeZone,content);
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
        private void menuAge(boolean isEdit){
            String fName="[menuAge]";
            logger.info(fName);
            try{
                String age=getCharacterInfo(keyAge); Message message;
                if(age!=null&&!age.isBlank()&&!age.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current age is "+age+".\nPlease enter  age:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter age:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyAge,content);
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
        private void menuGender(boolean isEdit){
            String fName="[menuGender]";
            logger.info(fName);
            try{
                String gender=getCharacterInfo(keyGender); Message message;
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
        private void menuSexuality(boolean isEdit){
            String fName="[menuSexuality]";
            logger.info(fName);
            try{
                String sexuality=getCharacterInfo(keySexuality); Message message;
                if(sexuality!=null&&!sexuality.isBlank()&&!sexuality.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current sexuality is "+sexuality+".\nPlease enter sexuality:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter sexuality:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keySexuality,content);
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
        private void menuLikes(boolean isEdit){
            String fName="[menuLikes]";
            logger.info(fName);
            try{
                String likes=getCharacterInfo(keyLikes); Message message;
                if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current likes are "+likes+".\nPlease enter likes:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter likes:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyLikes,content);
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
        private void menuHates(boolean isEdit){
            String fName="[menuHates]";
            logger.info(fName);
            try{
                String hates=getCharacterInfo(keyHates); Message message;
                if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current hates are "+hates+".\nPlease enter hates:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter hates:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyHates,content);
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
        private void menuDescription(boolean isEdit){
            String fName="[menuDescription";
            logger.info(fName);
            try{
                String description=getCharacterInfo(keyDesc); Message message;
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
        private void menuLinks(boolean isEdit){
            String fName="[menuLinks";
            logger.info(fName);
            try{
                String description=getCharacterInfo(keyDesc);
                Message message;
                if(description!=null&&!description.isBlank()&&!description.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current links are "+description+".\nPlease enter links:", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter links:", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                //loadCharacter();
                                gUserProfile.jsonObject.put(keyLinks,content);
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

    public nusersona(lcGlobalHelper global, Guild guild, User user,String command){
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
