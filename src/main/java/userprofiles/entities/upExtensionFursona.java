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
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class upExtensionFursona extends upExtension implements llMessageHelper, rFursona, llMemberHelper {
    Logger loggerFursona = Logger.getLogger("userprofiles.upExtensionFursona");

    protected upExtensionFursona(){

    }
    public void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
        loggerFursona.info(fName);
    }
    public void help( String command) {
        String fName = "[help]";
        loggerFursona.info(fName);
        loggerFursona.info(fName + "command=" + command);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle);
        embed.setColor(llColorPurple1);
        embed.addField("IMPORTANT","This is the NSFW characters profile!!!",false);
        embed.addField("View","`"+llPrefixStr+KeyTag+" [@User] view [index]` to view existing character.\nIn case an index is given, it will display that character details. In case its not given an index, a character name list will be displayed.\nIn case an @User is mentioned, it will display their character(s).",false);
        embed.addField("Create","`"+llPrefixStr+KeyTag+" create` to create your character.\nYou can create 5 character. Boosters can create 10.",false);
        embed.addField("Edit","`"+llPrefixStr+KeyTag+" edit <index>` to edit an existing character.\nYou can edit only your character",false);
        embed.addField("Delete","`"+llPrefixStr+KeyTag+" delete <index>` to delete an existing character.\nYou can delete only your character",false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+llPrefixStr+KeyTag+" guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    public void noMention() {
        String fName = "[noMention]";
        loggerFursona.info(fName);
        String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void noSlot() {
        String fName = "[noSlot]";
        loggerFursona.info(fName);
        String desc="`Please mention the index number like `"+llPrefixStr+"+fursona edit 1` or `"+llPrefixStr+"fursona @User view 1`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void viewAll() {
        String fName = "[viewAll]";
        loggerFursona.info(fName);
        viewAll(gMember);
    }
    public void view(String index, int mode) {
        String fName = "[view]";
        loggerFursona.info(fName);
        loggerFursona.info(fName+"index="+index);
        loggerFursona.info(fName+"mode="+mode);
        view(gMember,index,mode);
    }
    public void delete(String index) {
        String fName = "[delete]";
        loggerFursona.info(fName);
        loggerFursona.info(fName+"index="+index);
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
        loggerFursona.info(fName);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        int lenght=gCharacters.size();
        loggerFursona.info(fName+"lenght="+lenght);
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
        loggerFursona.info(fName);
        loggerFursona.info(fName+"index="+index);
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
        loggerFursona.info(fName);
        loggerFursona.info(fName+"target="+target.getId());
        if(!getProfile(target)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }

        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        String desc="Characters for "+target.getAsMention()+":";
        for(int j=0;j<gCharacters.size();j++){
            FURSONA character=gCharacters.get(j);
            String name="<no name>";
            if(!character.getName().isBlank()){
                name=character.getName();
            }
            desc+="\n"+j+"."+name;
        }
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorBlue1);
    }
    public void view(Member target,String index, int mode) {
        String fName = "[view]";
        loggerFursona.info(fName);
        loggerFursona.info(fName+"target="+target.getId());
        loggerFursona.info(fName+"index="+index);
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
        gCharacterIndex=i;
        gCharacter=gCharacters.get(gCharacterIndex);
        String img="",name="<n/a>",species="", age="",gender="",sexuality="",likes="",hates="",description="";
        if(!gCharacter.getName().isBlank()){
            name=gCharacter.getName();
        }
        if(!gCharacter.getSpecies().isBlank()){
            species=gCharacter.getSpecies();
        }
        if(!gCharacter.getAge().isBlank()){
            age=gCharacter.getAge();
        }
        if(!gCharacter.getSexuality().isBlank()){
            sexuality=gCharacter.getSexuality();
        }
        if(!gCharacter.getGender().isBlank()){
            gender=gCharacter.getGender();
        }
        if(!gCharacter.getLikes().isBlank()){
            likes=gCharacter.getLikes();
        }
        if(!gCharacter.getHates().isBlank()){
            hates=gCharacter.getHates();
        }
        if(!gCharacter.getDesc().isBlank()){
            description=gCharacter.getDesc();
        }
        if(!gCharacter.getImg().isBlank()){
            img=gCharacter.getImg();
        }
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPurple1);
        embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
        if(mode==0||mode==1){
            embed.setTitle("Fursona: "+name);
            if(!species.isBlank())embed.addField("Species",species,true);
            if(!age.isBlank())embed.addField("Age",age,true);
            if(!gender.isBlank())embed.addField("Gender",gender,true);
            if(!sexuality.isBlank())embed.addField("Sexuality",sexuality,true);
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
        }
        if((mode==0||mode==2)&&!description.isBlank()){
            embed.addField("Description",description,false);
        }
        if(mode==0||mode==3){
            if(!likes.isBlank())embed.addField("Likes",likes,false);
            if(!hates.isBlank())embed.addField("Hates",hates,false);
        }
        llSendMessage(gTextChannel,embed);
    }
    public void view2DM(Member target,String index, int mode) {
        String fName = "[view2DM]";
        loggerFursona.info(fName);
        loggerFursona.info(fName+"target="+target.getId());
        loggerFursona.info(fName+"index="+index);
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
        gCharacterIndex=i;
        gCharacter=gCharacters.get(gCharacterIndex);
        String img="",name="<n/a>",species="", age="",gender="",sexuality="",likes="",hates="",description="";
        if(!gCharacter.getName().isBlank()){
            name=gCharacter.getName();
        }
        if(!gCharacter.getSpecies().isBlank()){
            species=gCharacter.getSpecies();
        }
        if(!gCharacter.getAge().isBlank()){
            age=gCharacter.getAge();
        }
        if(!gCharacter.getSexuality().isBlank()){
            sexuality=gCharacter.getSexuality();
        }
        if(!gCharacter.getGender().isBlank()){
            gender=gCharacter.getGender();
        }
        if(!gCharacter.getLikes().isBlank()){
            likes=gCharacter.getLikes();
        }
        if(!gCharacter.getHates().isBlank()){
            hates=gCharacter.getHates();
        }
        if(!gCharacter.getDesc().isBlank()){
            description=gCharacter.getDesc();
        }
        if(!gCharacter.getImg().isBlank()){
            img=gCharacter.getImg();
        }
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPurple1);
        embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
        if(mode==0||mode==1){
            embed.setTitle("Fursona: "+name);
            if(!species.isBlank())embed.addField("Species",species,true);
            if(!age.isBlank())embed.addField("Age",age,true);
            if(!gender.isBlank())embed.addField("Gender",gender,true);
            if(!sexuality.isBlank())embed.addField("Sexuality",sexuality,true);
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
        }
        if((mode==0||mode==2)&&!description.isBlank()){
            embed.addField("Description",description,false);
        }
        if(mode==0||mode==3){
            if(!likes.isBlank())embed.addField("Likes",likes,false);
            if(!hates.isBlank())embed.addField("Hates",hates,false);
        }
        llSendMessage(gUser,embed);
    }
    public void menuMainWearer(){
        String fName="[menuMainWearer]";
        loggerFursona.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                FURSONA character=gCharacters.get(j);
                String name="<no name>";
                if(!character.getName().isBlank()){
                    name=character.getName();
                }
                desc+="\n"+j+"."+name;
            }
            embed.setDescription(desc);isBooster=llMemberIsBooster(gMember); int lenght=gCharacters.size();
            Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
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
                loggerFursona.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerFursona.error(fName + ".exception=" + e3);
                loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainWearerListener(message);
        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainWearerListener(Message message){
        String fName="[menuMainWearer]";
        loggerFursona.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerFursona.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerFursona.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerFursona.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerFursona.warn(fName+"value="+value);
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
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerFursona.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerFursona.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerFursona.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                loggerFursona.info(fName+"trigger="+name);
                                create();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(0);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(1);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(2);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(3);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(4);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(5);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(6);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(7);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(8);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerFursona.info(fName+"trigger="+name);
                                loadCharacter(9);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerFursona.info(fName+"exit");
                            }
                            else{
                                menuMainWearer();
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerFursona.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebody(){
        String fName="[menuMainWearer]";
        loggerFursona.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                FURSONA character=gCharacters.get(j);
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
                loggerFursona.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerFursona.error(fName + ".exception=" + e3);
                loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainSomebodyListener(message);

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebodyListener(Message message){
        String fName="[menuMainSomebody]";
        loggerFursona.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerFursona.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerFursona.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerFursona.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerFursona.warn(fName+"value="+value);
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
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerFursona.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerFursona.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerFursona.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"0",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"1",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"2",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"3",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"4",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"5",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"6",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"7",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"8",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerFursona.info(fName+"trigger="+name);
                                view2DM(gTarget,"9",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerFursona.info(fName+"exit");
                            }
                            else{
                                menuMainSomebody();
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerFursona.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuCharacter(boolean isEdit){
        String fName="[menuCharacter]";
        loggerFursona.info(fName);
        try{
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
            desc+="\n:six: for likes.";
            desc+="\n:seven: for hates.";
            desc+="\n:eight: for description.";
            desc+="\n:nine: for adding attachment for image.";
            desc+="\n:white_check_mark: to finish.";
            embed.addField("Options",desc,false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            if(isEdit) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
            lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerFursona.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                menuName(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                menuSpecies(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                menuAge(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                menuGender(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                menuSexuality(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                menuLikes(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                menuHates(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                menuDescription(isEdit);

                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                menuImage(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                            }else{
                                menuCharacter(isEdit);
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerFursona.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    List<FURSONA>gCharacters=new ArrayList<>();FURSONA gCharacter=new FURSONA(); int gCharacterIndex=0;
    public void saveCharacter(){
        String fName="[saveCharacter]";
        loggerFursona.info(fName);
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
        loggerFursona.info(fName);
        gCharacterIndex=index;
        gCharacter=gCharacters.get(gCharacterIndex);
    }
    public String getCharacterInfo(String key){
        String fName="[getCharacterInfo]";
        loggerFursona.info(fName);
        JSONObject jsonObject=gCharacter.getJSON();
        if(jsonObject.has(key)){
            return  jsonObject.getString(key);
        }
        return "";
    }
    public void menuName(boolean isEdit){
        String fName="[menuName]";
        loggerFursona.info(fName);
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setName(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSpecies(boolean isEdit){
        String fName="[menuAge]";
        loggerFursona.info(fName);
        try{
            String species=getCharacterInfo(keySpecies);Message message;
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setSpecies(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuAge(boolean isEdit){
        String fName="[menuAge]";
        loggerFursona.info(fName);
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setAge(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuGender(boolean isEdit){
        String fName="[menuGender]";
        loggerFursona.info(fName);
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setGender(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSexuality(boolean isEdit){
        String fName="[menuSexuality]";
        loggerFursona.info(fName);
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setSexuality(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuLikes(boolean isEdit){
        String fName="[menuLikes]";
        loggerFursona.info(fName);
        try{
            String likes=getCharacterInfo(keyLikes);Message message;
            if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current likes is "+likes+".\nPlease enter the characters likes:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters likes:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setLikes(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuHates(boolean isEdit){
        String fName="[menuHates]";
        loggerFursona.info(fName);
        try{
            String hates=getCharacterInfo(keyHates);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current hates is "+hates+".\nPlease enter the characters hates:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters hates:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setHates(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuDescription(boolean isEdit){
        String fName="[menuDescription";
        loggerFursona.info(fName);
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
                            loggerFursona.info(fName+".content="+content);
                            gCharacter.setDesc(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuImage(boolean isEdit){
        String fName="[menuDescription";
        loggerFursona.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image.\nIf the image can't be sent do to Discord explicit content filter, please type `!channel` to ask you in guild.", llColorBlue1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content=e.getMessage().getContentStripped();
                            if(content!=null){
                                loggerFursona.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!channel")){
                                    llMessageDelete(message); menuImageGuild(isEdit);
                                    return;
                                }
                            }
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerFursona.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerFursona.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerFursona.error(fName + "  not image");
                                    menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                                    return;
                                }
                                String imageUrl=attachment.getUrl();
                                gCharacter.setImg(imageUrl);
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
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
                                }  catch (Exception e2) {
                                    loggerFursona.error(fName+".exception=" + e2);
                                    loggerFursona.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void menuImageGuild(boolean isEdit){
        String fName="[menuImage]";
        loggerFursona.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Please attach an image, "+gMember.getAsMention()+".", llColorBlue1);
            gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerFursona.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerFursona.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerFursona.error(fName + "  not image");
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
                                loggerFursona.info(fName+".content="+content);
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
                                    loggerFursona.error(fName+".exception=" + e2);
                                    loggerFursona.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerFursona.error(fName + ".exception=" + e3);
                            loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    public Boolean getProfile(Member member){
        String fName="[getProfile]";
        loggerFursona.info(fName);
        try{
            loggerFursona.info(fName + ".member:"+ member.getId()+"|"+member.getUser().getName());
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild,profileName);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                loggerFursona.info(fName + ".is locally cached");
            }else{
                loggerFursona.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild,profileName);
                if(gUserProfile.getProfile(table)){
                    loggerFursona.info(fName + ".has sql entry");
                }
            }
            gUserProfile=iSafetyUserProfileEntry(gUserProfile);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                loggerFursona.info(fName + ".no update>ignore");
                gCharacters=getCharactersList(gUserProfile);
                return true;
            }
            if(!saveProfile()){ loggerFursona.error(fName+".failed to write in Db");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
            gCharacters=getCharactersList(gUserProfile);
            return true;

        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        loggerFursona.info(fName);
        try{
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                loggerFursona.info(fName + ".success");return  true;
            }
            loggerFursona.warn(fName + ".failed");return false;
        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public Boolean getProfileL(){
        String fName="[getProfile]";
        loggerFursona.info(fName);
        loggerFursona.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
        gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
        if(gUserProfile.getProfile(table)){
            loggerFursona.info(fName + ".has sql entry");  return true;
        }
        return false;
    }
    public Boolean saveProfileL(){
        String fName="[saveProfile]";
        loggerFursona.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(table)){
            loggerFursona.info(fName + ".success");return  true;
        }
        loggerFursona.warn(fName + ".failed");return false;
    }


    public void slash(){
        String fName="[slash]";
        loggerFursona.info(fName);
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            slashReplyPleaseWait();
            if(isNsfwCommand&&!isNSFW()){
                loggerFursona.warn(fName+"nsfw command but wrong channel>return");
                embedBuilder.setDescription("NSFW channel is required!").setColor(llColorRed_Barn);
                sendOrUpdatePrivateEmbed(embedBuilder);
                return;
            }
            String subcommandname=gSlashCommandEvent.getSubcommandName();
            String subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
            loggerFursona.info(fName+"subcommandgroup="+subcommandgroup+", subcommandname="+subcommandname);
            if(subcommandgroup==null)subcommandgroup="";
            if(subcommandname==null)subcommandname="";
            slashProfile(gMember);slashCharacter();
            List<OptionMapping>options=new ArrayList<>();boolean filedProv=false,nameProv=false;
            Member member=null;
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
                                case KEYS.likes:gCharacter.setLikes(option.getAsString());filedProv=true;break;
                                case KEYS.hates:gCharacter.setHates(option.getAsString());filedProv=true;break;
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
                        String desc="Please post an image attachment for character ["+gCharacterIndex+"]"+gCharacter.getName()+" image";
                        embedBuilder.setDescription(desc).setColor(llColorBlue1);
                        sendOrUpdatePrivateEmbed(embedBuilder);
                        gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                                e -> e.getAuthor().equals(gUser),
                                e -> {
                                    try {
                                        String content=e.getMessage().getContentStripped();
                                        if(content!=null){
                                            loggerFursona.info(fName+".content="+content);
                                        }
                                        List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                        loggerFursona.info(fName + ".attachments.size=" + attachments.size());
                                        if (attachments.size() > 0) {
                                            Message.Attachment attachment = attachments.get(0);
                                            if (!attachment.isImage()) {
                                                loggerFursona.error(fName + ".attachment is not an image");
                                                llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                            }
                                            else if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                                loggerFursona.error(fName + ".image too big");
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                            }
                                            else if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                                loggerFursona.error(fName + ".image too big");
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image height or width is above 1024px!", llColorRed);
                                            }
                                            else{
                                                String imageUrl=attachment.getUrl();
                                                gCharacter.setImg(imageUrl);
                                                saveCharacter();
                                                if(!saveProfile()){
                                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);
                                                }
                                            }

                                        }else{
                                            loggerFursona.info(fName+".content="+content);
                                            llSendQuickEmbedMessage(gUser, gTitle, "No attachment!", llColorRed);
                                        }

                                    }catch (Exception e3){
                                        loggerFursona.error(fName + ".exception=" + e3);
                                        loggerFursona.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());

                                    }
                                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                    llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
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
                                    case upExtensionCharacter.KEYS.field:
                                        switch (option.getAsString()){
                                            case KEYS.field_all: mode=0;break;
                                            case KEYS.field_main: mode=1;break;
                                            case KEYS.field_desc: mode=2;break;
                                            case KEYS.field_preference: mode=3;break;
                                        }
                                        break;
                                }
                            }
                            loggerFursona.info(fName+"mode="+mode);
                            String img="",name="<n/a>",species="", age="",gender="",sexuality="",likes="",hates="",description="";
                            if(!gCharacter.getName().isBlank()){
                                name=gCharacter.getName();
                            }
                            if(!gCharacter.getSpecies().isBlank()){
                                species=gCharacter.getSpecies();
                            }
                            if(!gCharacter.getAge().isBlank()){
                                age=gCharacter.getAge();
                            }
                            if(!gCharacter.getSexuality().isBlank()){
                                sexuality=gCharacter.getSexuality();
                            }
                            if(!gCharacter.getGender().isBlank()){
                                gender=gCharacter.getGender();
                            }
                            if(!gCharacter.getLikes().isBlank()){
                                likes=gCharacter.getLikes();
                            }
                            if(!gCharacter.getHates().isBlank()){
                                hates=gCharacter.getHates();
                            }
                            if(!gCharacter.getDesc().isBlank()){
                                description=gCharacter.getDesc();
                            }
                            if(!gCharacter.getImg().isBlank()){
                                img=gCharacter.getImg();
                            }
                            EmbedBuilder embed=new EmbedBuilder();
                            embed.setColor(llColorPurple1);
                            embed.setAuthor(gUserProfile.getUser().getName()+"#"+gUserProfile.getUser().getDiscriminator(),null,gUserProfile.getUser().getEffectiveAvatarUrl());
                            if(mode==0||mode==1){
                                embed.setTitle("Fursona: "+name);
                                if(!species.isBlank())embed.addField("Species",species,true);
                                if(!age.isBlank())embed.addField("Age",age,true);
                                if(!gender.isBlank())embed.addField("Gender",gender,true);
                                if(!sexuality.isBlank())embed.addField("Sexuality",sexuality,true);
                                if(img!=null&&!img.isBlank()){
                                    embed.setImage(img);
                                }
                            }
                            if((mode==0||mode==2)&&!description.isBlank()){
                                embed.addField("Description",description,false);
                            }
                            if(mode==0||mode==3){
                                if(!likes.isBlank())embed.addField("Likes",likes,false);
                                if(!hates.isBlank())embed.addField("Hates",hates,false);
                            }
                            sendOrUpdatePrivateEmbed(gTitle,"Getting character info",llColorBlue1);
                            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
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
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }
                            break;
                        case "list":
                            slashProfile(gMember);slashCharacter();
                            embedBuilder.setColor(llColorGreen_Fern);
                            StringBuilder desc= new StringBuilder();int count=0;
                            for(FURSONA character:gCharacters){
                                desc.append("\n").append(count).append(character.getName());count++;
                            }
                            embedBuilder.setDescription("Characters:"+desc);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                            break;
                        case "new":
                            filedProv=false;nameProv=false;
                            options=gSlashCommandEvent.getOptions();
                            for(OptionMapping option:options){
                                switch (option.getName()){
                                    case KEYS.name:gCharacter.setName(option.getAsString());nameProv=true;filedProv=true;break;
                                    case KEYS.species:gCharacter.setSpecies(option.getAsString());filedProv=true;break;
                                    case KEYS.gender:gCharacter.setGender(option.getAsString());filedProv=true;break;
                                    case KEYS.age:gCharacter.setAge(String.valueOf(option.getAsLong()));filedProv=true;break;
                                    case KEYS.sexuality:gCharacter.setSexuality(option.getAsString());filedProv=true;break;
                                    case KEYS.desc:gCharacter.setDesc(option.getAsString());filedProv=true;break;
                                    case KEYS.likes:gCharacter.setLikes(option.getAsString());filedProv=true;break;
                                    case KEYS.hates:gCharacter.setHates(option.getAsString());filedProv=true;break;
                                }
                            }
                            gCharacter=new FURSONA();
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
                                embedBuilder.setDescription("Fursona "+gCharacter.getName()+" saved.");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }else{
                                embedBuilder.setColor(llColorRed_Barn);
                                embedBuilder.setDescription("Failed to add new fursona!");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }
                            break;
                        default:
                            embedBuilder.setDescription("Invalid command").setColor(llColorRed_Barn);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                    }
            }
        } catch (Exception e) {
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashProfile(Member member){
        String fName="[slashProfile]";
        loggerFursona.info(fName);
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
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashCharacter(){
        String fName="[slashCharacter]";
        loggerFursona.info(fName);
        try{
            int slot= Math.toIntExact(gSlashCommandEvent.getOption("slot").getAsLong());
            loggerFursona.info(fName+"slot="+slot);
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
            loggerFursona.error(fName+".exception=" + e);
            loggerFursona.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }


    
    
}
