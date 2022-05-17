package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.json.profile.lcJSONUserProfile;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class upExtensionCharacter extends upExtension implements llMessageHelper,rCharacter, llMemberHelper {
    Logger loggerCharacter = Logger.getLogger("userprofiles.upExtensionCharacter");
    
   

    protected upExtensionCharacter(){
       
    }
    public void help( String command) {
        String fName = "[help]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName + "command=" + command);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle);
        embed.setColor(llColorPurple1);
        embed.addField("IMPORTANT","This is the SFW characters profile!!!",false);
        embed.addField("View","`"+llPrefixStr+KeyTag+" [@User] view [index] [general/details/rp]` to view existing character.\nIn case an index is given, it will display that character details. In case its not given an index, a character name list will be displayed.\nIn case an @User is mentioned, it will display their character(s).",false);
        embed.addField("Create","`"+llPrefixStr+KeyTag+" create` to create your character.\nYou can create 5 character.Boosters can create 10.",false);
        embed.addField("Edit","`"+llPrefixStr+KeyTag+" edit <index>` to edit an existing character.\nYou can edit only your character",false);
        embed.addField("Delete","`"+llPrefixStr+KeyTag+" delete <index>` to delete an existing character.\nYou can delete only your character",false);
        embed.addField("Use","`"+llPrefixStr+KeyTag+" use <index>` to set a character to use for posting. The same way how gag works, just uses this character avatar and name instead of the user."+"\n`"+llPrefixStr+KeyTag+" stop` to stop it",false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+llPrefixStr+KeyTag+" guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    public void noMention() {
        String fName = "[noMention]";
        loggerCharacter.info(fName);
        String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void noSlot() {
        String fName = "[noSlot]";
        loggerCharacter.info(fName);
        String desc="`Please mention the index number like `"+llPrefixStr+"+character edit 1` or `"+llPrefixStr+"character @User view 1`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
        loggerCharacter.info(fName);
    }
    public void viewAll() {
        String fName = "[viewAll]";
        loggerCharacter.info(fName);
        viewAll(gMember);
    }
    public void view(String index, int mode) {
        view(gMember,index,mode);
    }
    public void delete(String index) {
        String fName = "[delete]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName+"index="+index);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=gCharacters.size()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strIndexOutOfBound+(gCharacters.size()-1), llColorRed);return;
        }
        gCharacters.remove(i);
        JSONArray array=listCharacters2Array(gCharacters);
        gUserProfile.jsonObject.put(keyCharacters,array);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
        }else{
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Deleted character", llColorGreen1);
        }
    }

    public void create() {
        String fName = "[create]";
        loggerCharacter.info(fName);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        isBooster=llMemberIsBooster(gMember);
        int lenght=gCharacters.size();
        loggerCharacter.info(fName+"lenght="+lenght);
        gCharacterIndex=lenght;
        if(isBooster&&lenght<restrictionBoosted){
            menuCharacter(false);
        }else
        if(lenght<restrictionNormal){
            menuCharacter(false);
        }else{
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Max characters number reached.\nNormal members:"+restrictionNormal+"\nBoosted members:"+restrictionBoosted, llColorRed);return;
        }

    }
    public void edit(String index) {
        String fName = "[edit]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName+"index="+index);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }

        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=gCharacters.size()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strIndexOutOfBound+(gCharacters.size()-1), llColorRed);return;
        }
        gCharacterIndex=i;
        gCharacter=gCharacters.get(gCharacterIndex);
        menuCharacter(true);
    }
    public void viewAll(Member target) {
        String fName = "[viewAll]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName+"target="+target.getId());
        if(!getProfile(target)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }

        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        String desc="Characters for "+target.getAsMention()+":";
        for(int j=0;j<gCharacters.size();j++){
            CHARACTER character=gCharacters.get(j);
            String name="<no name>";
            if(!character.getName().isBlank()){
                name=character.getName();
            }
            desc+="\n"+j+"."+name;
        }
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorBlue1);
    }
    public void view(Member target,String index, int mode) {
        view(target,false, index, mode);
    }
    public void view(Member target,boolean isdm, String index, int mode) {
        viewPost(target,isdm, index, mode);
    }
    public void viewPost(Member target,boolean isdm, String index, int mode) {
        String fName = "[viewPost]";
        loggerCharacter.info(fName+"target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode);
        if(!getProfile(target)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=gCharacters.size()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strIndexOutOfBound+(gCharacters.size()-1), llColorRed);return;
        }
        EmbedBuilder embed=viewEmbed(target,isdm,index,mode);
        Message message=null;
        if(isdm){
            message=llSendMessageResponse(gUser,embed);
        }else{
            message=llSendMessageResponse(gTextChannel,embed);
        }
        loggerCharacter.info(fName+"message="+message.getIdLong());
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
        viewListener(message,target,isdm,index, mode);
    }
    public EmbedBuilder viewEmbed(Member target,boolean isdm, String index, int mode) {
        String fName = "[view]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName+"target="+target.getId());
        loggerCharacter.info(fName+"index="+index);loggerCharacter.info(fName+"mode="+mode);
        if(!getProfile(target)){
            loggerCharacter.warn(fName+"return error");
            return null;
        }

        if(gCharacters.isEmpty()){
            loggerCharacter.warn(fName+"return error");
            return null;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=gCharacters.size()){
            loggerCharacter.warn(fName+"return error");
            return null;
        }
        gCharacterIndex=i;
        gCharacter=gCharacters.get(gCharacterIndex);
        String img="",name="<n/a>",species="", age="",gender=">",sexuality="",avatar="",rpPreferencesFave="",rpPreferencesNo="",description="",rpPreferencesYes="",rpPreferencesMaybe="";
        if(!gCharacter.getName().isBlank()){
            name=gCharacter.getName();
        }
        if(!gCharacter.getSpecies().isBlank()){
            species=gCharacter.getSpecies();
        }
        if(!gCharacter.getAge().isBlank()){
            age=gCharacter.getAge();
        }
        if(!gCharacter.getAvatar().isBlank()){
            avatar=gCharacter.getAvatar();
        }
        if(!gCharacter.getSexuality().isBlank()){
            sexuality=gCharacter.getSexuality();
        }
        if(!gCharacter.getGender().isBlank()){
            gender=gCharacter.getGender();
        }
        if(!gCharacter.getFave().isBlank()){
            rpPreferencesFave=gCharacter.getFave();
        }
        if(!gCharacter.getNo().isBlank()){
            rpPreferencesNo=gCharacter.getNo();
        }
        if(!gCharacter.getMaybe().isBlank()){
            rpPreferencesMaybe=gCharacter.getMaybe();
        }
        if(!gCharacter.getYes().isBlank()){
            rpPreferencesYes=gCharacter.getYes();
        }
        if(!gCharacter.getDesc().isBlank()){
            description=gCharacter.getDesc();
        }
        if(!gCharacter.getImg().isBlank()){
            img=gCharacter.getImg();
        }
        EmbedBuilder embed=new EmbedBuilder();
        if(mode==1){
            embed.setTitle("Character: "+name);
            if(!species.isBlank())embed.addField("Species",species,true);
            if(!age.isBlank())embed.addField("Age",age,true);
            if(!gender.isBlank())embed.addField("Gender",gender,true);
            if(!sexuality.isBlank())embed.addField("Sexuality",sexuality,true);
            embed.setColor(llColorPurple1);
            if(img!=null&&!img.isBlank()&&!img.isEmpty()){
                embed.setImage(img);
            }
            if(avatar!=null&&!avatar.isBlank()&&!avatar.isEmpty()){
                embed.setThumbnail(avatar);
            }
            embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
            if(mode==1){
                embed.addField(" ","Add details or rp for those fields",false);
            }
            llSendMessage(gTextChannel,embed);
        }else
        if(mode==2&&description!=null&&!description.isBlank()&&!description.isEmpty()){
            embed.setColor(llColorPurple1); embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
            embed.setTitle("Details for "+name);
            embed.setDescription(description);
            llSendMessage(gTextChannel,embed);
        }else
        if(mode==3){
            boolean hasRPPreferences=false;
            if(rpPreferencesFave!=null&&!rpPreferencesFave.isBlank()&&!rpPreferencesFave.isEmpty()){
                embed.addField("Fave",rpPreferencesFave,false);hasRPPreferences=true;
            }
            if(rpPreferencesYes!=null&&!rpPreferencesYes.isBlank()&&!rpPreferencesYes.isEmpty()){
                embed.addField("Yes",rpPreferencesYes,false);hasRPPreferences=true;
            }
            if(rpPreferencesMaybe!=null&&!rpPreferencesMaybe.isBlank()&&!rpPreferencesMaybe.isEmpty()){
                embed.addField("Maybe",rpPreferencesMaybe,false);hasRPPreferences=true;
            }
            if(rpPreferencesNo!=null&&!rpPreferencesNo.isBlank()&&!rpPreferencesNo.isEmpty()){
                embed.addField("No",rpPreferencesNo,false);hasRPPreferences=true;
            }
            loggerCharacter.info(fName+"hasRPPreferences="+hasRPPreferences);
            if(hasRPPreferences){
                embed.setColor(llColorPurple1);  embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
                embed.setTitle("RP preferences: "+name);
                llSendMessage(gTextChannel,embed);
            }
        }else{
            embed.setDescription("n/a");
        }
        return  embed;
    }
    public void view2DM(Member target,String index, int mode) {
        view(target,true, index,mode);
    }
    public void viewListener(Message message,Member target,boolean isdm,String index, int mode) {
        String fName = "[viewListener]";
        loggerCharacter.info(fName+"message="+message.getId()+", target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode);
        if(message.isFromGuild()){
            gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            viewListener(e.getReaction(),message, target, isdm, index, mode);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageClearReactions(message);
                    });
        }else{
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            viewListener(e.getReaction(),message, target, isdm, index, mode);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });
        }
    }
    public void viewListener(MessageReaction messageReaction,Message message, Member target, boolean isdm, String index, int mode) {
        String fName = "[viewListener]";
        loggerCharacter.info(fName+"message="+message.getId()+", target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode);
        try {
            String name=messageReaction.getReactionEmote().getName();
            loggerCharacter.info(fName+"name="+name);
            int newmode=0;
            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                help("main");
                return;
            }
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                newmode=1;
            }
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                newmode=2;
            }
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                newmode=3;
            }
            else{
                viewListener(message,target,isdm,index, mode);
                return;
            }
            loggerCharacter.info(fName+"newmode="+newmode);
            llMessageDelete(message);
            viewPost(target,isdm,index, newmode);
        }catch (Exception e3){
            loggerCharacter.error(fName + ".exception=" + e3);
            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
            llMessageDelete(message);
        }
    }


    public void menuMainWearer(){
        loggerCharacter.warn("hellow!");
        String fName="[menuMainWearer]";
        loggerCharacter.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                CHARACTER character=gCharacters.get(j);
                String name="<no name>";
                if(!character.getName().isBlank()){
                    name=character.getName();
                }
                desc+="\n"+j+"."+name;
            }
            embed.setDescription(desc);
            Message message=null;//llSendMessageResponse(gUser,embed);
            isBooster=llMemberIsBooster(gMember);int lenght=gCharacters.size();
            /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gCharacters.size()>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
            if(gCharacters.size()>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
            if(gCharacters.size()>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
            if(gCharacters.size()>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
            if(gCharacters.size()>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
            if(gCharacters.size()>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
            if(gCharacters.size()>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
            if(gCharacters.size()>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
            if(gCharacters.size()>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
            if(gCharacters.size()>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
            if((isBooster&&lenght<restrictionBoosted)||(lenght<restrictionNormal)){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
            messageComponentManager.loadMessageComponents(rGeneral.gCommandFileCharactersPath);
            try {
                messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_character");
                for(int i=0;i<gCharacters.size();i++){
                    lcMessageBuildComponent.SelectMenu.Option option=selectContainer0.getOptionAt(i);
                    String label=option.getLabel().replaceAll("!Name",gCharacters.get(i).getName());
                    if(label.length()>30){
                        label=label.substring(0,25);
                        label+="...";
                    }
                    option.setLabel(label);
                }
                for(int i=gCharacters.size();i<10;i++){
                    selectContainer0.getOptionAt(i).setIgnored();
                }
                if(!((isBooster&&lenght<restrictionBoosted)||(lenght<restrictionNormal))){
                    selectContainer0.getOptionByValue("heavy_plus_sign").setIgnored();
                }
                loggerCharacter.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerCharacter.error(fName + ".exception=" + e3);
                loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainWearerListener(message);
        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainWearerListener(Message message){
        String fName="[menuMainWearer]";
        loggerCharacter.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerCharacter.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerCharacter.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerCharacter.warn(fName+"value="+value);
                            switch (value){
                                case lsUnicodeEmotes.aliasHeavyPlusSign:
                                    create();
                                    break;
                                case lsUnicodeEmotes.alias0:
                                    loadCharacter(0);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias1:
                                    loadCharacter(1);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias2:
                                    loadCharacter(2);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias3:
                                    loadCharacter(3);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias4:
                                    loadCharacter(4);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias5:
                                    loadCharacter(5);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias6:
                                    loadCharacter(6);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias7:
                                    loadCharacter(7);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias8:
                                    loadCharacter(8);
                                    menuCharacter(true);
                                    break;
                                case lsUnicodeEmotes.alias9:
                                    loadCharacter(9);
                                    menuCharacter(true);
                                    break;
                            }

                            llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerCharacter.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerCharacter.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                loggerCharacter.info(fName+"trigger="+name);
                                create();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(0);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(1);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(2);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(3);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(4);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(5);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(6);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(7);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(8);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerCharacter.info(fName+"trigger="+name);
                                loadCharacter(9);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerCharacter.info(fName+"exit");
                            }
                            else{
                                menuMainWearer();
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerCharacter.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebody(){
        String fName="[menuMainWearer]";
        loggerCharacter.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                CHARACTER character=gCharacters.get(j);
                String name="<no name>";
                if(!character.getName().isBlank()){
                    name=character.getName();
                }
                desc+="\n"+j+"."+name;
            }
            embed.setDescription(desc);
            Message message=null;//llSendMessageResponse(gUser,embed);
            /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gCharacters.size()>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
            if(gCharacters.size()>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
            if(gCharacters.size()>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
            if(gCharacters.size()>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
            if(gCharacters.size()>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
            if(gCharacters.size()>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
            if(gCharacters.size()>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
            if(gCharacters.size()>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
            if(gCharacters.size()>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
            if(gCharacters.size()>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
            int lenght=gCharacters.size();
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
            messageComponentManager.loadMessageComponents(rGeneral.gCommandFileCharactersPath);
            try {
                messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_character");
                for(int i=0;i<gCharacters.size();i++){
                    lcMessageBuildComponent.SelectMenu.Option option=selectContainer0.getOptionAt(i);
                    String label=option.getLabel().replaceAll("!Name",gCharacters.get(i).getName());
                    if(label.length()>30){
                        label=label.substring(0,25);
                        label+="...";
                    }
                    option.setLabel(label);
                }
                for(int i=gCharacters.size();i<10;i++){
                    selectContainer0.getOptionAt(i).setIgnored();
                }
                if(!((isBooster&&lenght<restrictionBoosted)||(lenght<restrictionNormal))){
                    selectContainer0.getOptionByValue("heavy_plus_sign").setIgnored();
                }
                loggerCharacter.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerCharacter.error(fName + ".exception=" + e3);
                loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainSomebodyListener(message);

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebodyListener(Message message){
        String fName="[menuMainSomebody]";
        loggerCharacter.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerCharacter.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerCharacter.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerCharacter.warn(fName+"value="+value);
                            switch (value){
                                case lsUnicodeEmotes.aliasHeavyPlusSign:
                                    create();
                                    break;
                                case lsUnicodeEmotes.alias0:
                                    view2DM(gTarget,"0",1);
                                    break;
                                case lsUnicodeEmotes.alias1:
                                    view2DM(gTarget,"1",1);
                                    break;
                                case lsUnicodeEmotes.alias2:
                                    view2DM(gTarget,"2",1);
                                    break;
                                case lsUnicodeEmotes.alias3:
                                    view2DM(gTarget,"3",1);
                                    break;
                                case lsUnicodeEmotes.alias4:
                                    view2DM(gTarget,"4",1);
                                    break;
                                case lsUnicodeEmotes.alias5:
                                    view2DM(gTarget,"5",1);
                                    break;
                                case lsUnicodeEmotes.alias6:
                                    view2DM(gTarget,"6",1);
                                    break;
                                case lsUnicodeEmotes.alias7:
                                    view2DM(gTarget,"7",1);
                                    break;
                                case lsUnicodeEmotes.alias8:
                                    view2DM(gTarget,"8",1);
                                    break;
                                case lsUnicodeEmotes.alias9:
                                    view2DM(gTarget,"9",1);
                                    break;
                            }

                            llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerCharacter.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerCharacter.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"0",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"1",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"2",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"3",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"4",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"5",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"6",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"7",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"8",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerCharacter.info(fName+"trigger="+name);
                                view2DM(gTarget,"9",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerCharacter.info(fName+"exit");
                            }
                            else{
                                menuMainSomebody();
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerCharacter.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuCharacter(boolean isEdit){
        String fName="[menuCharacter]";
        loggerCharacter.info(fName);
        if(gUserProfile.getMember().getIdLong()==gMember.getIdLong()){
            menuCharacterWearer(isEdit);
        }else{
            menuCharacterSomebody();
        }
    }
    public void menuCharacterWearer(boolean isEdit){
        String fName="[menuCharacter]";
        loggerCharacter.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        embed.setTitle(gTitle);
        embed.addField("Slot",String.valueOf(gCharacterIndex),false);
        String desc="";
        desc=":one: for name.";
        desc+="\n:two: for species.";
        desc+="\n:three: for age.";
        desc+="\n:four: for gender.";
        desc+="\n:five: for sexuality.";
        desc+="\n:star: for fave\n:green_heart: for yes\n:yellow_square: for maybe\n:skull: for no";
        desc+="\n:six: for description.";
        desc+="\n:seven: for adding attachment for image.";
        desc+="\n:eight: for adding attachment for avatar.";
        desc+="\n:white_check_mark: to finish.";
        embed.addField("Options",desc,false);
        Message message=llSendMessageResponse(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenHearth));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSkull));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
        if(isEdit) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
        lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);

        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        messageComponentManager.loadMessageComponents(gCommandFileCharacterPath);
        try {
            loggerCharacter.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            loggerCharacter.error(fName + ".exception=" + e3);
            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuCharacterWearerListener(message,isEdit);
    }
    public void menuCharacterWearerListener(Message message,boolean isEdit){
        String fName="[menuCharacterListener]";
        loggerCharacter.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        loggerCharacter.warn(fName+"value="+value);
                        llMessageDelete(message);
                        switch (value){
                            case lsUnicodeEmotes.aliasStar:
                                menuFave(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasGreenHearth:
                                menuYes(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasYellowSquare:
                                menuMaybe(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasSkull:
                                menuNo(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasEye:
                                view2DM(gMember,String.valueOf(gCharacterIndex),1);
                                break;
                            case lsUnicodeEmotes.alias1:
                                menuName(isEdit);
                                break;
                            case lsUnicodeEmotes.alias2:
                                menuSpecies(isEdit);
                                break;
                            case lsUnicodeEmotes.alias3:
                                menuAge(isEdit);
                                break;
                            case lsUnicodeEmotes.alias4:
                                menuGender(isEdit);
                                break;
                            case lsUnicodeEmotes.alias5:
                                menuSexuality(isEdit);
                                break;
                            case lsUnicodeEmotes.alias6:
                                menuDescription(isEdit);
                                break;
                            case lsUnicodeEmotes.alias7:
                                menuImage(isEdit);
                                break;
                            case lsUnicodeEmotes.alias8:
                                menuAvatar(isEdit);
                                break;

                        }

                    }catch (Exception e3){
                        loggerCharacter.error(fName + ".exception=" + e3);
                        loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_selectmenu));
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        loggerCharacter.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                break;
                            case lsUnicodeEmotes.aliasCrossMark:
                                delete(String.valueOf(gCharacterIndex));
                                break;
                            case lsUnicodeEmotes.aliasWhiteCheckMark:
                                llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                                break;
                        }
                    }catch (Exception e3){
                        loggerCharacter.error(fName + ".exception=" + e3);
                        loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> loggerCharacter.info(fName+lsGlobalHelper.timeout_button));
        gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        loggerCharacter.info(fName+"name="+name);
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuName(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuSpecies(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuAge(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuGender(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuSexuality(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){

                            menuFave(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenHearth))){
                            menuYes(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare))){
                            menuMaybe(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSkull))){
                            menuNo(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuDescription(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuImage(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                            loggerCharacter.info(fName+"trigger="+name);
                            menuAvatar(isEdit);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                            loggerCharacter.info(fName+"trigger="+name);
                            view2DM(gMember,String.valueOf(gCharacterIndex),0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                            loggerCharacter.info(fName+"trigger="+name);
                            delete(String.valueOf(gCharacterIndex));
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                            loggerCharacter.info(fName+"trigger="+name);llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                        }else{
                            menuCharacter(isEdit);
                        }
                    }catch (Exception e3){
                        loggerCharacter.error(fName + ".exception=" + e3);
                        loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                    llMessageDelete(message);
                    loggerCharacter.info(fName+lsGlobalHelper.timeout_reaction_add);
                });
    }
    public void menuCharacterSomebody(){
        String fName="[menuCharacter]";
        loggerCharacter.info(fName);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        embed.setTitle(gTitle);
        embed.addField("Slot",String.valueOf(gCharacterIndex),false);
        embed.addField("Denied","Only the user can edit their character",false);
        Message message=llSendMessageResponse_withReactionNotification(gUser,embed);

        lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
        gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        loggerCharacter.info(fName+"name="+name);
                        llMessageDelete(message);
                    }catch (Exception e3){
                        loggerCharacter.error(fName + ".exception=" + e3);
                        loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                    llMessageDelete(message);
                    llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                });
    }

    List<CHARACTER>gCharacters=new ArrayList<>();CHARACTER gCharacter=new CHARACTER(); int gCharacterIndex=0;
    public void saveCharacter(){
        String fName="[saveCharacter]";
        loggerCharacter.info(fName);
        if(gCharacterIndex==-1||gCharacterIndex>=gCharacters.size()){
            gCharacters.add(gCharacter);
        }else{
            gCharacters.set(gCharacterIndex,gCharacter);
        }
        JSONArray array=listCharacters2Array(gCharacters);
        gUserProfile.jsonObject.put(keyCharacters,array);
    }
    public void loadCharacter(int index){
        String fName="[loadCharacter]";
        loggerCharacter.info(fName);
        gCharacterIndex=index;
        gCharacter=gCharacters.get(gCharacterIndex);
    }
    public String getCharacterInfo(String key){
        String fName="[getCharacterInfo]";
        loggerCharacter.info(fName);
        JSONObject jsonObject=gCharacter.getJSONSameLine();
        if(jsonObject.has(key)){
            return  jsonObject.getString(key);
        }
        return "";
    }
    public void menuName(boolean isEdit){
        String fName="[menuName]";
        loggerCharacter.info(fName);
        try{
            String name=getCharacterInfo(keyName);Message message;
            if(name!=null&&!name.isBlank()&&!name.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current name is "+name+".\nPlease enter the characters name:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters name:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setName(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSpecies(boolean isEdit){
        String fName="[menuAge]";
        loggerCharacter.info(fName);
        try{
            String species=getCharacterInfo(keySpeciels);Message message;
            if(species!=null&&!species.isBlank()&&!species.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current species is "+species+".\nPlease enter the characters species:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters species:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setSpecies(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);;
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuAge(boolean isEdit){
        String fName="[menuAge]";
        loggerCharacter.info(fName);
        try{
            String age=getCharacterInfo(keyAge);Message message;
            if(age!=null&&!age.isBlank()&&!age.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current age is "+age+".\nPlease enter the characters age:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters age:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setAge(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuGender(boolean isEdit){
        String fName="[menuGender]";
        loggerCharacter.info(fName);
        try{
            String gender=getCharacterInfo(keyGender);Message message;
            if(gender!=null&&!gender.isBlank()&&!gender.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current gender is "+gender+".\nPlease enter the characters gender:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters gender:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setGender(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSexuality(boolean isEdit){
        String fName="[menuSexuality]";
        loggerCharacter.info(fName);
        try{
            String sexuality=getCharacterInfo(keySexuality);Message message;
            if(sexuality!=null&&!sexuality.isBlank()&&!sexuality.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current sexuality is "+sexuality+".\nPlease enter the characters sexuality:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters sexuality:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setSexuality(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuFave(boolean isEdit){
        String fName="[menuFave]";
        loggerCharacter.info(fName);
        try{
            String likes=getCharacterInfo(keyFave);Message message;
            if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current faves are "+likes+".\nPlease enter the characters rp faves:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters faves:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setFave(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuYes(boolean isEdit){
        String fName="[menuYes]";
        loggerCharacter.info(fName);
        try{
            String likes=getCharacterInfo(keyYes);Message message;
            if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current yes are "+likes+".\nPlease enter the characters rp yes:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters yes:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setYes(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMaybe(boolean isEdit){
        String fName="[menuMaybe]";
        loggerCharacter.info(fName);
        try{
            String likes=getCharacterInfo(keyMaybe);Message message;
            if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current maybes are "+likes+".\nPlease enter the characters rp maybes:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters maybes:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setMaybe(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuNo(boolean isEdit){
        String fName="[menuNo]";
        loggerCharacter.info(fName);
        try{
            String hates=getCharacterInfo(keyNo);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current dislikes are "+hates+".\nPlease enter the characters RP dislikes:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters dislikes:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setNo(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuDescription(boolean isEdit){
        String fName="[menuDescription";
        loggerCharacter.info(fName);
        try{
            String description=getCharacterInfo(keyDesc);Message message;
            if(description!=null&&!description.isBlank()&&!description.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current description is "+description+".\nPlease enter the characters description:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters description:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerCharacter.info(fName+".content="+content);
                            gCharacter.setDesc(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuImage(boolean isEdit){
        String fName="[menuImage]";
        loggerCharacter.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image.\nIf the image can't be sent do to Discord explicit content filter, please type `!channel` to ask you in guild.", llColorBlue1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content=e.getMessage().getContentStripped();
                            if(content!=null){
                                loggerCharacter.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!channel")){
                                    llMessageDelete(message); menuImageGuild(isEdit);
                                    return;
                                }
                            }
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerCharacter.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerCharacter.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerCharacter.error(fName + "  not image");
                                    menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                                    return;
                                }
                                String imageUrl=attachment.getUrl();
                                gCharacter.setImg(imageUrl);
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                            }else{
                                try{
                                    EmbedBuilder testEmbed=new EmbedBuilder();
                                    testEmbed.setDescription("test");testEmbed.setImage(content);
                                    testEmbed.build();
                                    gCharacter.setImg(content);
                                    saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                    menuCharacter(isEdit);llMessageDelete(message);
                                }  catch (Exception e2) {
                                    loggerCharacter.error(fName+".exception=" + e2);
                                    loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuAvatar(boolean isEdit){
        String fName="[menuAvatar]";
        loggerCharacter.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image for avatar.\nIf the image can't be sent do to Discord explicit content filter, please type `!channel` to ask you in guild.", llColorBlue1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content=e.getMessage().getContentStripped();
                            if(content!=null){
                                loggerCharacter.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!channel")){
                                    llMessageDelete(message); menuAvatarGuild(isEdit);
                                    return;
                                }
                            }
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerCharacter.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerCharacter.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerCharacter.error(fName + "  not image");
                                    menuCharacter(isEdit);llMessageDelete(message);
                                    return;
                                }
                                if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                    loggerCharacter.error(fName + ".image too big");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                    loggerCharacter.error(fName + ".image too big");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image height or width is above 1024px!", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                String imageUrl=attachment.getUrl();
                                gCharacter.setAvatar(imageUrl);
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuCharacter(isEdit);llMessageDelete(message);
                            }else{
                                loggerCharacter.info(fName+".content="+content);
                                try{
                                    EmbedBuilder testEmbed=new EmbedBuilder();
                                    testEmbed.setDescription("test");testEmbed.setImage(content);
                                    testEmbed.build();
                                    gCharacter.setAvatar(content);
                                    saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                    menuCharacter(isEdit);llMessageDelete(message);
                                }  catch (Exception e2) {
                                    loggerCharacter.error(fName+".exception=" + e2);
                                    loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuImageGuild(boolean isEdit){
        String fName="[menuImage]";
        loggerCharacter.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Please attach an image, "+gMember.getAsMention()+".", llColorBlue1);
            gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerCharacter.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerCharacter.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerCharacter.error(fName + "  not image");
                                    menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                                    return;
                                }
                                String imageUrl=attachment.getUrl();
                                gCharacter.setImg(imageUrl);
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                            }else{
                                String content = e.getMessage().getContentStripped();
                                loggerCharacter.info(fName+".content="+content);
                                try{
                                    EmbedBuilder testEmbed=new EmbedBuilder();
                                    testEmbed.setDescription("test");testEmbed.setImage(content);
                                    testEmbed.build();
                                    gCharacter.setImg(content);
                                    saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                    menuCharacter(isEdit);llMessageDelete(message);
                                }  catch (Exception e2) {
                                    loggerCharacter.error(fName+".exception=" + e2);
                                    loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuAvatarGuild(boolean isEdit){
        String fName="[menuAvatar]";
        loggerCharacter.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Please attach an image for avatar, "+gMember.getAsMention()+".", llColorBlue1);
            gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerCharacter.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerCharacter.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerCharacter.error(fName + "  not image");
                                    menuCharacter(isEdit);llMessageDelete(message);
                                    return;
                                }
                                if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                    loggerCharacter.error(fName + ".image too big");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                    loggerCharacter.error(fName + ".image too big");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image height or width is above 1024px!", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                String imageUrl=attachment.getUrl();
                                gCharacter.setAvatar(imageUrl);
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuCharacter(isEdit);llMessageDelete(message);
                            }else{
                                String content = e.getMessage().getContentStripped();
                                loggerCharacter.info(fName+".content="+content);
                                try{
                                    EmbedBuilder testEmbed=new EmbedBuilder();
                                    testEmbed.setDescription("test");testEmbed.setImage(content);
                                    testEmbed.build();
                                    gCharacter.setAvatar(content);
                                    saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                    menuCharacter(isEdit);llMessageDelete(message);
                                }  catch (Exception e2) {
                                    loggerCharacter.error(fName+".exception=" + e2);
                                    loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerCharacter.error(fName + ".exception=" + e3);
                            loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    public Boolean getProfile(Member member){
        String fName="[getProfile]";
        loggerCharacter.info(fName);
        try{
            loggerCharacter.info(fName + ".member:"+ member.getId()+"|"+member.getUser().getName());
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild,profileName);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                loggerCharacter.info(fName + ".is locally cached");
            }else{
                loggerCharacter.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild,profileName);
                if(gUserProfile.getProfile(table)){
                    loggerCharacter.info(fName + ".has sql entry");
                }
            }
            gUserProfile=iSafetyUserProfileEntry(gUserProfile);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                loggerCharacter.info(fName + ".no update>ignore");
                gCharacters=getCharactersList(gUserProfile);
                return true;
            }
            if(!saveProfile()){ loggerCharacter.error(fName+".failed to write in Db");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
            gCharacters=getCharactersList(gUserProfile);
            return true;

        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        loggerCharacter.info(fName);
        try{
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                loggerCharacter.info(fName + ".success");return  true;
            }
            loggerCharacter.warn(fName + ".failed");return false;
        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public void use(String index) {
        String fName = "[use]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName+"index="+index);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        JSONArray array=getCharactersArray(gUserProfile);
        if(array.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=array.length()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strIndexOutOfBound+(array.length()-1), llColorRed);return;
        }
        int characterIndex=i;
        JSONObject character=array.getJSONObject(i);JSONObject rpSpeach;
        if(!getGagProfile()){llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;}
        loggerCharacter.info(fName+"has profile");boolean isChanged=false;
        if(gGagUserProfile.jsonObject.has(rGeneral.RD.nRPSpeach)&&!gGagUserProfile.jsonObject.isNull(rGeneral.RD.nRPSpeach)){
            loggerCharacter.info(fName+"has rp speach entry");
            rpSpeach=gGagUserProfile.jsonObject.getJSONObject(rGeneral.RD.nRPSpeach);
            if(character.has(keyName)&&!character.isNull(keyName)){
                loggerCharacter.info(fName+"character name="+character.getString(keyName));
                rpSpeach.put(rGeneral.RD.nName,character.getString(keyName));rpSpeach.put(rGeneral.RD.nOn,true);isChanged=true;
            }
            if(character.has(keyAvatar)&&!character.isNull(keyAvatar)){
                loggerCharacter.info(fName+"character avatar="+character.getString(keyAvatar));
                rpSpeach.put(rGeneral.RD.nAvatar,character.getString(keyAvatar));rpSpeach.put(rGeneral.RD.nOn,true);isChanged=true;
            }
        }else{
            loggerCharacter.info(fName+"create rp speach entry");
            rpSpeach=new JSONObject();
            if(character.has(keyName)&&!character.isNull(keyName)){
                loggerCharacter.info(fName+"character name="+character.getString(keyName));
                rpSpeach.put(rGeneral.RD.nName,character.getString(keyName));rpSpeach.put(rGeneral.RD.nOn,true);isChanged=true;
            }
            if(character.has(keyAvatar)&&!character.isNull(keyAvatar)){
                loggerCharacter.info(fName+"character avatar="+character.getString(keyAvatar));
                rpSpeach.put(rGeneral.RD.nAvatar,character.getString(keyAvatar));rpSpeach.put(rGeneral.RD.nOn,true);isChanged=true;
            }
        }
        if(isChanged){
            gGagUserProfile.jsonObject.put(rGeneral.RD.nRPSpeach,rpSpeach);
            gGagUserProfile.isUpdated=true;
            saveGagProfile();
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Enabled", llColorGreen1);
        }
    }
    public void stop() {
        String fName = "[stop]";
        loggerCharacter.info(fName);
        if(!getGagProfile()){llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;}
        if(gGagUserProfile.jsonObject.has(rGeneral.RD.nRPSpeach)&&!gGagUserProfile.jsonObject.isNull(rGeneral.RD.nRPSpeach)){
            JSONObject rpSpeach=gGagUserProfile.jsonObject.getJSONObject(rGeneral.RD.nRPSpeach);
            if(rpSpeach.has(rGeneral.RD.nOn)&&rpSpeach.getBoolean(rGeneral.RD.nOn)){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Stopped", llColorBlue1);
                rpSpeach.put(rGeneral.RD.nOn,false); gGagUserProfile.jsonObject.put(rGeneral.RD.nRPSpeach,rpSpeach); gGagUserProfile.isUpdated=true;saveGagProfile();
            }
        }
    }

    public Boolean getGagProfile(){
        String fName="[getGagProfile]";
        loggerCharacter.info(fName);
        gGagUserProfile=gGlobal.getUserProfile(rGeneral.RD.gagProfileName,gUser,gGuild);
        if(gGagUserProfile!=null&&gGagUserProfile.isProfile()){
            loggerCharacter.info(fName + ".is locally cached");
        }else{
            loggerCharacter.info(fName + ".need to get or create");
            gGagUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
            if(gGagUserProfile.getProfile(rGeneral.RD.gagTable)){
                loggerCharacter.info(fName + ".has sql entry");
            }
        }
        gBDSMCommands = new lcBDSMGuildProfiles(gGlobal, gGuild);
        gBDSMCommands.muzzle.init();
        gGagUserProfile = iRestraints.sUserInit(gGagUserProfile, gBDSMCommands.getRestrainsProfile());
        if (!gGagUserProfile.isUpdated) {
            loggerCharacter.info(fName + ".no update>ignore");
            gGlobal.putUserProfile(gGagUserProfile, rGeneral.RD.gagProfileName);
            return true;
        }
        if (!saveProfile()) {
            loggerCharacter.error(fName + ".failed to write in Db");
            if (!gGagUserProfile.isProfile()) {
                loggerCharacter.info(fName + ".not a profile");
                return false;
            }
            gGlobal.putUserProfile(gGagUserProfile, rGeneral.RD.gagProfileName);
            return true;
        }
        if(!gGagUserProfile.isProfile()){
            loggerCharacter.info(fName + ".not a profile"); return false;
        }
        gGlobal.putUserProfile(gGagUserProfile,rGeneral.RD.gagProfileName);
        return true;
    }
    public Boolean saveGagProfile(){
        String fName="[saveGagProfile]";
        loggerCharacter.info(fName);
        gGlobal.putUserProfile(gGagUserProfile,rGeneral.RD.gagProfileName);
        if(gGagUserProfile.saveProfile(rGeneral.RD.gagTable)){
            loggerCharacter.info(fName + ".success");return  true;
        }
        loggerCharacter.warn(fName + ".failed");return false;
    }

    public Boolean getProfileL(){
        String fName="[getProfile]";
        loggerCharacter.info(fName);
        loggerCharacter.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
        gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
        if(gUserProfile.getProfile(table)){
            loggerCharacter.info(fName + ".has sql entry");  return true;
        }
        return false;
    }
    public Boolean saveProfileL(){
        String fName="[saveProfile]";
        loggerCharacter.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(table)){
            loggerCharacter.info(fName + ".success");return  true;
        }
        loggerCharacter.warn(fName + ".failed");return false;
    }


    public void slash(){
        String fName="[slash]";
        loggerCharacter.info(fName);
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            slashReplyPleaseWait();
            if(isNsfwCommand&&!isNSFW()){
                loggerCharacter.warn(fName+"nsfw command but wrong channel>return");
                embedBuilder.setDescription("NSFW channel is required!").setColor(llColorRed_Barn);
                sendOrUpdatePrivateEmbed(embedBuilder);
                return;
            }
            String subcommandname=gSlashCommandEvent.getSubcommandName();
            String subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
            loggerCharacter.info(fName+"subcommandgroup="+subcommandgroup+", subcommandname="+subcommandname);
            if(subcommandgroup==null)subcommandgroup="";
            if(subcommandname==null)subcommandname="";
            slashProfile(gMember);slashCharacter();Member member=null;
            List<OptionMapping>options=new ArrayList<>();boolean filedProv=false,nameProv=false;
            switch (subcommandgroup){
                case "set":
                    filedProv=false;
                    if(subcommandname.equalsIgnoreCase("text")){
                        options=gSlashCommandEvent.getOptions();
                        for(OptionMapping option:options){
                            switch (option.getName()){
                                case KEYS.name:gCharacter.setName(option.getAsString());filedProv=true;break;
                                case KEYS.species:gCharacter.setSpecies(option.getAsString());filedProv=true;break;
                                case KEYS.gender:gCharacter.setGender(option.getAsString());filedProv=true;break;
                                case KEYS.age:gCharacter.setAge(String.valueOf(option.getAsLong()));filedProv=true;break;
                                case KEYS.sexuality:gCharacter.setSexuality(option.getAsString());filedProv=true;break;
                                case KEYS.desc:gCharacter.setDesc(option.getAsString());filedProv=true;break;
                                case KEYS.yes:gCharacter.setYes(option.getAsString());filedProv=true;break;
                                case KEYS.fave:gCharacter.setFave(option.getAsString());filedProv=true;break;
                                case KEYS.maybe:gCharacter.setMaybe(option.getAsString());filedProv=true;break;
                                case KEYS.no:gCharacter.setNo(option.getAsString());filedProv=true;break;
                            }
                        }
                        if(!filedProv){
                            sendOrUpdatePrivateEmbed(gTitle,"Opening menu in DM",llColorBlue1);
                            menuCharacter(true);
                            return;
                        }
                        saveCharacter();
                        if(saveProfile()){
                            embedBuilder.setColor(llColorGreen_Fern).setDescription("Character ["+gCharacterIndex+"]"+gCharacter.getName()+" updated.");
                            sendOrUpdatePrivateEmbed(embedBuilder);
                            return;
                        }else{
                            embedBuilder.setColor(llColorRed_Barn).setDescription("Failed to update!");
                            sendOrUpdatePrivateEmbed(embedBuilder);
                            return;
                        }
                    }else
                    if(subcommandname.equalsIgnoreCase("attachment")){
                        String choice=gSlashCommandEvent.getOption(KEYS.type).getAsString();
                        String desc="";
                        switch (choice){
                            case KEYS.field_avatar:desc="Please post an image attachment for character ["+gCharacterIndex+"]"+gCharacter.getName()+" avatar";
                                break;
                            case KEYS.field_image:desc="Please post an image attachment for character ["+gCharacterIndex+"]"+gCharacter.getName()+" image";
                                break;
                        }
                        embedBuilder.setDescription(desc).setColor(llColorBlue1);
                        sendOrUpdatePrivateEmbed(embedBuilder);
                        gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                                e -> e.getAuthor().equals(gUser),
                                e -> {
                                    try {
                                        String content=e.getMessage().getContentStripped();
                                        if(content!=null){
                                            loggerCharacter.info(fName+".content="+content);
                                        }
                                        List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                        loggerCharacter.info(fName + ".attachments.size=" + attachments.size());
                                        if (attachments.size() > 0) {
                                            Message.Attachment attachment = attachments.get(0);
                                            if (!attachment.isImage()) {
                                                loggerCharacter.error(fName + ".attachment is not an image");
                                                llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                            }
                                            else if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                                loggerCharacter.error(fName + ".image too big");
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                            }
                                            else if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                                loggerCharacter.error(fName + ".image too big");
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image height or width is above 1024px!", llColorRed);
                                            }
                                            else{
                                                String imageUrl=attachment.getUrl();
                                                switch (choice){
                                                    case KEYS.field_avatar: gCharacter.setAvatar(imageUrl);
                                                        break;
                                                    case KEYS.field_image: gCharacter.setImg(imageUrl);
                                                        break;
                                                }
                                                saveCharacter();
                                                if(!saveProfile()){
                                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);
                                                }
                                            }

                                        }else{
                                            loggerCharacter.info(fName+".content="+content);
                                            llSendQuickEmbedMessage(gUser, gTitle, "No attachment!", llColorRed);
                                        }

                                    }catch (Exception e3){
                                        loggerCharacter.error(fName + ".exception=" + e3);
                                        loggerCharacter.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());

                                    }
                                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                    llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);;
                                });
                    }
                    break;
                default:
                    switch (subcommandname){
                        case "menu":
                            options=gSlashCommandEvent.getOptions();
                            for(OptionMapping option:options) {
                                switch (option.getName()) {
                                    case "user":
                                        member=option.getAsMember();
                                        if(member!=null&&member.getIdLong()==gMember.getIdLong()){
                                            gTarget=member;
                                            slashProfile(member);slashCharacter();
                                        }
                                        break;
                                }
                            }
                            sendOrUpdatePrivateEmbed(gTitle,"Opening menu in DM",llColorBlue1);
                            if(gTarget!=null){
                                menuMainSomebody();
                            }else{
                                menuMainWearer();
                            }
                            break;
                        case "get":
                            int mode=1;
                            options=gSlashCommandEvent.getOptions();
                            for(OptionMapping option:options){
                                switch (option.getName()){
                                    case KEYS.field:
                                        switch (option.getAsString()){
                                            case KEYS.field_all: mode=0;break;
                                            case KEYS.field_main: mode=1;break;
                                            case KEYS.field_desc: mode=2;break;
                                            case KEYS.field_preference: mode=3;break;
                                        }
                                        break;
                                }
                            }
                            loggerCharacter.info(fName+"mode="+mode);
                            String img="",name="<n/a>",species="", age="",gender=">",sexuality="",avatar="",rpPreferencesFave="",rpPreferencesNo="",description="",rpPreferencesYes="",rpPreferencesMaybe="";
                            if(!gCharacter.getName().isBlank()){
                                name=gCharacter.getName();
                            }
                            if(!gCharacter.getSpecies().isBlank()){
                                species=gCharacter.getSpecies();
                            }
                            if(!gCharacter.getAge().isBlank()){
                                age=gCharacter.getAge();
                            }
                            if(!gCharacter.getAvatar().isBlank()){
                                avatar=gCharacter.getAvatar();
                            }
                            if(!gCharacter.getSexuality().isBlank()){
                                sexuality=gCharacter.getSexuality();
                            }
                            if(!gCharacter.getGender().isBlank()){
                                gender=gCharacter.getGender();
                            }
                            if(!gCharacter.getFave().isBlank()){
                                rpPreferencesFave=gCharacter.getFave();
                            }
                            if(!gCharacter.getNo().isBlank()){
                                rpPreferencesNo=gCharacter.getNo();
                            }
                            if(!gCharacter.getMaybe().isBlank()){
                                rpPreferencesMaybe=gCharacter.getMaybe();
                            }
                            if(!gCharacter.getYes().isBlank()){
                                rpPreferencesYes=gCharacter.getYes();
                            }
                            if(!gCharacter.getDesc().isBlank()){
                                description=gCharacter.getDesc();
                            }
                            if(!gCharacter.getImg().isBlank()){
                                img=gCharacter.getImg();
                            }
                            EmbedBuilder embedGeneral=new EmbedBuilder() ,embedDesc=new EmbedBuilder(),embedRPPreferences=new EmbedBuilder();
                            embedGeneral.setColor(llColorPurple1);
                            embedGeneral.setAuthor(gUserProfile.getUser().getName()+"#"+gUserProfile.getUser().getDiscriminator(),null,gUserProfile.getUser().getEffectiveAvatarUrl());

                            if(mode==0||mode==1){
                                embedGeneral.setTitle("Character: "+name);
                                if(!species.isBlank())embedGeneral.addField("Species",species,true);
                                if(!age.isBlank())embedGeneral.addField("Age",age,true);
                                if(!gender.isBlank())embedGeneral.addField("Gender",gender,true);
                                if(!sexuality.isBlank())embedGeneral.addField("Sexuality",sexuality,true);

                                if(img!=null&&!img.isBlank()&&!img.isEmpty()){
                                    embedGeneral.setImage(img);
                                }
                                if(avatar!=null&&!avatar.isBlank()&&!avatar.isEmpty()){
                                    embedGeneral.setThumbnail(avatar);
                                }
                                if(mode==1){
                                    embedGeneral.addField(" ","Add details or rp for those fields",false);
                                }
                                sendOrUpdatePrivateEmbed(gTitle,"Getting character info",llColorBlue1);
                                llSendMessage(gTextChannel,embedBuilder);
                            }

                            if((mode==0||mode==2)&&description!=null&&!description.isBlank()&&!description.isEmpty()){
                                embedDesc.setColor(llColorPurple1);embedDesc.setTitle("Details for "+name);
                                embedDesc.setDescription(description);
                                sendOrUpdatePrivateEmbed(gTitle,"Getting character info",llColorBlue1);
                                llSendMessage(gTextChannel,embedDesc);
                            }
                            if(mode==0||mode==3){
                                boolean hasRPPreferences=false;
                                if(rpPreferencesFave!=null&&!rpPreferencesFave.isBlank()&&!rpPreferencesFave.isEmpty()){
                                    embedRPPreferences.addField("Fave",rpPreferencesFave,false);hasRPPreferences=true;
                                }
                                if(rpPreferencesYes!=null&&!rpPreferencesYes.isBlank()&&!rpPreferencesYes.isEmpty()){
                                    embedRPPreferences.addField("Yes",rpPreferencesYes,false);hasRPPreferences=true;
                                }
                                if(rpPreferencesMaybe!=null&&!rpPreferencesMaybe.isBlank()&&!rpPreferencesMaybe.isEmpty()){
                                    embedRPPreferences.addField("Maybe",rpPreferencesMaybe,false);hasRPPreferences=true;
                                }
                                if(rpPreferencesNo!=null&&!rpPreferencesNo.isBlank()&&!rpPreferencesNo.isEmpty()){
                                    embedRPPreferences.addField("No",rpPreferencesNo,false);hasRPPreferences=true;
                                }
                                loggerCharacter.info(fName+"hasRPPreferences="+hasRPPreferences);
                                if(hasRPPreferences){
                                    embedRPPreferences.setColor(llColorPurple1); embedRPPreferences.setTitle("RP preferences: "+name);
                                    sendOrUpdatePrivateEmbed(gTitle,"Getting character info",llColorBlue1);
                                    llSendMessage(gTextChannel,embedRPPreferences);
                                }else{
                                    embedBuilder.setColor(llColorRed_Barn).setDescription("No rp preferences!");
                                    sendOrUpdatePrivateEmbed(embedBuilder);
                                }
                            }
                            break;
                        case "del":
                            gCharacters.remove(gCharacterIndex);
                            JSONArray array=listCharacters2Array(gCharacters);
                            gUserProfile.jsonObject.put(keyCharacters,array);
                            if(saveProfile()){
                                embedBuilder.setColor(llColorGreen_Fern).setDescription("Removed character "+gCharacter.getName()+".");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }else{
                                embedBuilder.setColor(llColorRed_Barn).setDescription("Failed to remove character!");
                                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();

                            }
                            break;
                        case "list":
                            embedBuilder.setColor(llColorGreen_Fern);
                            StringBuilder desc= new StringBuilder();int count=0;
                            for(CHARACTER character:gCharacters){
                                desc.append("\n").append(count).append(character.getName());count++;
                            }
                            embedBuilder.setDescription("Characters:"+desc);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                            break;
                        case "new":
                            options=gSlashCommandEvent.getOptions();
                            filedProv=false;nameProv=false;
                            for(OptionMapping option:options){
                                switch (option.getName()){
                                    case KEYS.name:gCharacter.setName(option.getAsString());filedProv=true;nameProv=true;break;
                                    case KEYS.species:gCharacter.setSpecies(option.getAsString());filedProv=true;break;
                                    case KEYS.gender:gCharacter.setGender(option.getAsString());filedProv=true;break;
                                    case KEYS.age:gCharacter.setAge(String.valueOf(option.getAsLong()));filedProv=true;break;
                                    case KEYS.sexuality:gCharacter.setSexuality(option.getAsString());filedProv=true;break;
                                    case KEYS.desc:gCharacter.setDesc(option.getAsString());filedProv=true;break;
                                    case KEYS.yes:gCharacter.setYes(option.getAsString());filedProv=true;break;
                                    case KEYS.fave:gCharacter.setFave(option.getAsString());filedProv=true;break;
                                    case KEYS.maybe:gCharacter.setMaybe(option.getAsString());filedProv=true;break;
                                    case KEYS.no:gCharacter.setNo(option.getAsString());filedProv=true;break;
                                }
                            }
                            gCharacter=new CHARACTER();
                            if(!filedProv){
                                embedBuilder.setColor(llColorRed_Barn);
                                embedBuilder.setDescription("No field provided!");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                                return;
                            }
                            if(!nameProv){
                                embedBuilder.setColor(llColorRed_Barn);
                                embedBuilder.setDescription("No name provided!");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                                return;
                            }
                            gCharacterIndex=-1;
                            saveCharacter();
                            if(saveProfile()){
                                embedBuilder.setColor(llColorGreen_Fern);
                                embedBuilder.setDescription("Character "+gCharacter.getName()+" saved.");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }else{
                                embedBuilder.setColor(llColorRed_Barn);
                                embedBuilder.setDescription("Failed to add new character!");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }
                            break;
                        default:
                            embedBuilder.setDescription("Invalid command").setColor(llColorRed_Barn);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                    }
            }
        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashProfile(Member member){
        String fName="[slashProfile]";
        loggerCharacter.info(fName);
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            if(!getProfile(member)){
                embedBuilder.setColor(llColorRed_Barn);
                embedBuilder.setDescription("Failed to get entry from db!");
                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                return;
            }



        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashCharacter(){
        String fName="[slashCharacter]";
        loggerCharacter.info(fName);
        try{
            int slot= Math.toIntExact(gSlashCommandEvent.getOption("slot").getAsLong());
            loggerCharacter.info(fName+"slot="+slot);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            if(gCharacters.isEmpty()){
                embedBuilder.setColor(llColorRed_Barn);
                embedBuilder.setDescription("No characters!");
                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                return;
            }else
            if(slot<0||slot>=gCharacters.size()){
                embedBuilder.setColor(llColorRed_Barn);
                embedBuilder.setDescription("Invalid index!");
                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                return;
            }else{
                gCharacterIndex=slot;
                gCharacter=gCharacters.get(gCharacterIndex);
            }


        } catch (Exception e) {
            loggerCharacter.error(fName+".exception=" + e);
            loggerCharacter.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }


}
