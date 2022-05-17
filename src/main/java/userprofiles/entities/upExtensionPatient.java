package userprofiles.entities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.json.profile.lcJSONUserProfile;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class upExtensionPatient extends upExtension implements llMessageHelper, rPatient, llMemberHelper {
    Logger loggerPatient = Logger.getLogger("userprofiles.upExtensionPatient");

    protected upExtensionPatient(){

    }
    public void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
        loggerPatient.info(fName);
        loggerPatient.info(fName);
    }
    public void help( String command) {
        String fName = "[help]";
        loggerPatient.info(fName);
        loggerPatient.info(fName + "command=" + command);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle);
        embed.setColor(llColorPurple1);
        embed.addField("IMPORTANT","This is the NSFW characters profile!!!",false);
        embed.addField("View","`"+llPrefixStr+KeyTag+" [@User] view [index] [general/about/medical/schedule/incident/note] [nr for incident/note report]` to view existing character.\nIn case an index is given, it will display that character details. In case its not given an index, a character name list will be displayed.\nIn case an @User is mentioned, it will display their character(s).",false);
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
        loggerPatient.info(fName);
        String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void noSlot() {
        String fName = "[noSlot]";
        loggerPatient.info(fName);
        String desc="`Please mention the index number like `"+llPrefixStr+"+fursona edit 1` or `"+llPrefixStr+"fursona @User view 1`";
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
    }
    public void viewAll() {
        String fName = "[viewAll]";
        loggerPatient.info(fName);
        viewAll(gMember);
    }
    public void delete(String index) {
        String fName = "[delete]";
        loggerPatient.info(fName);
        loggerPatient.info(fName+"index="+index);
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
        loggerPatient.info(fName);
        if(!getProfile(gMember)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }
        int lenght=gCharacters.size();
        loggerPatient.info(fName+"lenght="+lenght);
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
        loggerPatient.info(fName);
        loggerPatient.info(fName+"index="+index);
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
        loggerPatient.info(fName);
        loggerPatient.info(fName+"target="+target.getId());
        if(!getProfile(target)){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2LoadProfile, llColorRed);return;
        }

        if(gCharacters.isEmpty()){
            llSendQuickEmbedMessage(gTextChannel,gTitle,strNoCharacters, llColorRed);return;
        }
        String desc="Characters for "+target.getAsMention()+":";
        for(int j=0;j<gCharacters.size();j++){
            PATIENT character=gCharacters.get(j);
            String name="<no name>";
            if(!character.getName().isBlank()){
                name=character.getName();
            }
            desc+="\n"+j+"."+name;
        }
        llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorBlue1);
    }
    public void view(String index, int mode) {
        String fName = "[view]";
        viewPost(gMember,false,index,mode,"");
    }
    public void view(String index, int mode, String line) {
        String fName = "[view]";
        viewPost(gMember,false,index,mode,line);
    }
    public void view(boolean isdm,String index, int mode, String line) {
        String fName = "[view]";
        viewPost(gMember,isdm,index,mode,line);
    }
    public void view(Member target,String index, int mode) {
        viewPost(target,false,index, mode, "");
    }
    public void view(Member target,boolean isdm,String index, int mode) {
        viewPost(target,isdm,index, mode, "");
    }
    public void view(Member target,String index, int mode, String line) {
        viewPost(target,false,index, mode, "");
    }
    public void viewPost(Member target,boolean isdm,String index, int mode, String line) {
        String fName = "[viewPost]";
        loggerPatient.info(fName+"target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode+", line="+line);
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
        EmbedBuilder embed=viewEmbed(target,isdm,index,mode,line);
        Message message=null;
        if(isdm){
            message=llSendMessageResponse(gUser,embed);
        }else{
            message=llSendMessageResponse(gTextChannel,embed);
        }
        loggerPatient.info(fName+"message="+message.getIdLong());
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
        viewListener(message,target,isdm,index, mode, line);
    }
    public void viewUpdate(Message message,Member target,boolean isdm,String index, int mode, String line) {
        String fName = "[viewUpdate]";
        loggerPatient.info(fName+"message="+message.getId()+", target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode+", line="+line);
        EmbedBuilder embed=viewEmbed(target,isdm,index,mode,line);
        lsMessageHelper.lsMessageClearReactions(message);
        message=lsMessageHelper.lsEditMessageResponse(message,embed);
        viewListener(message,target,isdm,index, mode, line);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
    }
    public EmbedBuilder viewEmbed(Member target,boolean isdm,String index, int mode, String line) {
        String fName = "[viewEmbed]";
        loggerPatient.info(fName+"target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode+", line="+line);
        if(!getProfile(target)){
            loggerPatient.warn(fName+"return error");
            return null;
        }
        if(gCharacters.isEmpty()){
            loggerPatient.warn(fName+"return error");
            return null;
        }
        int i=Integer.parseInt(index);
        if(i<0||i>=gCharacters.size()){
            loggerPatient.warn(fName+"return error");
            return null;
        }
        gCharacterIndex=i;
        gCharacter=gCharacters.get(gCharacterIndex);
        String img="",name="<n/a>",species="<n/a>", age="<n/a>",gender="<n/a>",sexuality="</a>",description="<n/a>";
        String height="", weight="";
        String medicalRecord="<n/a>",treatment="<n/a>",reason4Incarceration="<n/a>",minimumStayDuration="<n/a>",schedule="<n/a>",security="<n/a>";
        JSONArray arrayIncidents = new JSONArray(),arrayNotes =new JSONArray();
        if(!gCharacter.getIncidents().isEmpty()){
            arrayIncidents = gCharacter.getIncidents();
        }
        if(!gCharacter.getNotes().isEmpty()){
            arrayNotes =gCharacter.getNotes();
        }
        String patientNr=iGenerateNumber(target.getUser(), i );
        if(!gCharacter.getName().isBlank()){
            name=gCharacter.getName();
        }
        if(!gCharacter.getSpecies().isBlank()){
            species=gCharacter.getSpecies();
        }
        if(!gCharacter.getAge().isBlank()){
            age=gCharacter.getAge();
        }
        if(!gCharacter.getSchedule().isBlank()){
            schedule=gCharacter.getSchedule();
        }
        if(!gCharacter.getSexuality().isBlank()){
            sexuality=gCharacter.getSexuality();
        }
        if(!gCharacter.getGender().isBlank()){
            gender=gCharacter.getGender();
        }
        if(!gCharacter.getDesc().isBlank()){
            description=gCharacter.getDesc();
        }
        if(!gCharacter.getHeight().isBlank()){
            height=gCharacter.getHeight();
        }
        if(!gCharacter.getWeight().isBlank()){
            weight=gCharacter.getWeight();
        }
        if(!gCharacter.getImg().isBlank()){
            img=gCharacter.getImg();
        }
        if(!gCharacter.getMedicalRecord().isBlank()){
            medicalRecord=gCharacter.getMedicalRecord();
        }
        if(!gCharacter.getTreatment().isBlank()){
            treatment=gCharacter.getTreatment();
        }
        if(!gCharacter.getReason4Incarceration().isBlank()){
            reason4Incarceration=gCharacter.getReason4Incarceration();
        }
        if(!gCharacter.getMinimumStayDuration().isBlank()){
            minimumStayDuration=gCharacter.getMinimumStayDuration();
        }
        if(!gCharacter.getSecurity().isBlank()){
            security=gCharacter.getSecurity();
        }
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPurple1);
        embed.setAuthor(target.getUser().getName()+"#"+target.getUser().getDiscriminator(),null,target.getUser().getEffectiveAvatarUrl());
        if(mode==1){
            embed.setTitle("Patient "+patientNr);
            //embed.addField("Patient Nr",patientNr,true);
            embed.addField("Name",name,true);
            embed.addField("Species",species,true);
            embed.addField("Age",age,true);
            embed.addField("Gender",gender,true);
            embed.addField("Sexuality",sexuality,true);
            if(!weight.isBlank())embed.addField("Weight",weight,true);
            if(!height.isBlank())embed.addField("Height",height,true);
            embed.addBlankField(false);
            if(!reason4Incarceration.isBlank())embed.addField("Reason for incarceration",reason4Incarceration,true);
            if(!minimumStayDuration.isBlank())embed.addField("Minimum Stay Duration",minimumStayDuration,true);
            if(!security.isBlank())embed.addField("Security",security,true);
            if(!arrayIncidents.isEmpty())embed.addField("Incidents", String.valueOf(arrayIncidents.length()),true);
            if(!arrayNotes.isEmpty())embed.addField("Notes", String.valueOf(arrayNotes.length()),true);
            if(img!=null&&!img.isBlank()){
                embed.setImage(img);
            }
        }
        if(mode==2){
            embed.setTitle(patientNr+"'s about");
            embed.addField("Description",description,false);
        }
        if(mode==3){
            embed.setTitle(patientNr+"'s medical & treatment report");
            embed.addField("Medical Record",medicalRecord,false);
            embed.addField("Treatment",treatment,false);
        }
        if(mode==4){
            embed.setTitle(patientNr+"'s incident report");
            embed.addField("Schedule",schedule,false);
        }
        if(mode==5){
            embed.setTitle(patientNr+"'s incident report");
            if(arrayIncidents.isEmpty()){embed.setDescription("Nothing to display");}
            else if(line.isBlank()){embed.setDescription("Please select between 0-"+(arrayIncidents.length()-1));}
            else{
                int iLine=0;
                try {
                    iLine=Integer.parseInt(line);
                }catch (Exception e){
                    loggerPatient.error(fName + ".exception=" + e);
                    loggerPatient.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(iLine<0||iLine>arrayIncidents.length()-1){embed.setDescription("Invalid number!");}else{
                    embed.setDescription(arrayIncidents.getString(iLine));
                }
            }
        }
        if(mode==6){
            embed.setTitle(patientNr+"'s note report");
            if(arrayNotes.isEmpty()){embed.setDescription("Nothing to display");}
            else if(line.isBlank()){embed.setDescription("Please select between 0-"+(arrayNotes.length()-1));}
            else{
                int iLine=0;
                try {
                    iLine=Integer.parseInt(line);
                }catch (Exception e){
                    loggerPatient.error(fName + ".exception=" + e);
                    loggerPatient.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(iLine<0||iLine>arrayNotes.length()-1){embed.setDescription("Invalid number!");}else{
                    embed.setDescription(arrayNotes.getString(iLine));
                }
            }
        }
        return  embed;

    }
    public void viewListener(Message message,Member target,boolean isdm,String index, int mode, String line) {
        String fName = "[viewListener]";
        loggerPatient.info(fName+"message="+message.getId()+", target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode+", line="+line);
        if(message.isFromGuild()){
            gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            viewListener(e.getReaction(),message, target, isdm, index, mode, line);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
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
                            viewListener(e.getReaction(),message, target, isdm, index, mode, line);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });
        }
    }
    public void viewListener(MessageReaction messageReaction,Message message, Member target, boolean isdm, String index, int mode, String line) {
        String fName = "[viewListener]";
        loggerPatient.info(fName+"message="+message.getId()+", target="+target.getId()+", index="+index+" ,isdm="+isdm+", mode="+mode+", line="+line);
        try {
            String name=messageReaction.getReactionEmote().getName();
            loggerPatient.info(fName+"name="+name);
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
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                newmode=4;
            }
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                newmode=5;
            }
            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                newmode=6;
            }else{
                viewListener(message,target,isdm,index, mode, line);
                return;
            }
            loggerPatient.info(fName+"newmode="+newmode);
            llMessageDelete(message);
            viewPost(target,isdm,index, newmode,line);
        }catch (Exception e3){
            loggerPatient.error(fName + ".exception=" + e3);
            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
            llMessageDelete(message);
        }
    }

    public void menuMainWearer(){
        String fName="[menuMainWearer]";
        loggerPatient.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                PATIENT character=gCharacters.get(j);
                String name="<no name>";
                if(!character.getName().isBlank()){
                    name=character.getName();
                }
                desc+="\n"+j+"."+name;
            }
            embed.setDescription(desc);isBooster=llMemberIsBooster(gMember);int lenght=gCharacters.size();
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
                loggerPatient.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerPatient.error(fName + ".exception=" + e3);
                loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainWearerListener(message);
        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainWearerListener(Message message){
        String fName="[menuMainWearer]";
        loggerPatient.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerPatient.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerPatient.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerPatient.warn(fName+"value="+value);
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
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerPatient.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerPatient.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                loggerPatient.info(fName+"trigger="+name);
                                create();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(0);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(1);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(2);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(3);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(4);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(5);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(6);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(7);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(8);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerPatient.info(fName+"trigger="+name);
                                loadCharacter(9);
                                menuCharacter(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerPatient.info(fName+"exit");
                            }
                            else{
                                menuMainWearer();
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerPatient.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebody(){
        String fName="[menuMainWearer]";
        loggerPatient.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle);
            embed.setColor(llColorBlue1);
            String desc="Characters:";
            for(int j=0;j<gCharacters.size();j++){
                PATIENT character=gCharacters.get(j);
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
                loggerPatient.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                loggerPatient.error(fName + ".exception=" + e3);
                loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainSomebodyListener(message);

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMainSomebodyListener(Message message){
        String fName="[menuMainSomebody]";
        loggerPatient.info(fName);
        try{
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            loggerPatient.warn(fName+"id="+id);
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                loggerPatient.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){

                                help("main");
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            List<String> values=e.getValues();
                            String value=values.get(0);
                            loggerPatient.warn(fName+"value="+value);
                            switch (value){
                                case lsUnicodeEmotes.aliasHeavyPlusSign:
                                    create();
                                    break;
                                case lsUnicodeEmotes.alias0:
                                    view(gTarget,true,"0",1);
                                    break;
                                case lsUnicodeEmotes.alias1:
                                    view(gTarget,true,"1",1);
                                    break;
                                case lsUnicodeEmotes.alias2:
                                    view(gTarget,true,"2",1);
                                    break;
                                case lsUnicodeEmotes.alias3:
                                    view(gTarget,true,"3",1);
                                    break;
                                case lsUnicodeEmotes.alias4:
                                    view(gTarget,true,"4",1);
                                    break;
                                case lsUnicodeEmotes.alias5:
                                    view(gTarget,true,"5",1);
                                    break;
                                case lsUnicodeEmotes.alias6:
                                    view(gTarget,true,"6",1);
                                    break;
                                case lsUnicodeEmotes.alias7:
                                    view(gTarget,true,"7",1);
                                    break;
                                case lsUnicodeEmotes.alias8:
                                    view(gTarget,true,"8",1);
                                    break;
                                case lsUnicodeEmotes.alias9:
                                    view(gTarget,true,"9",1);
                                    break;
                            }

                            llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_selectmenu));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerPatient.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                loggerPatient.info(fName+"trigger="+name);
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"0",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"1",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"2",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"3",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"4",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"5",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"6",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"7",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"8",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                loggerPatient.info(fName+"trigger="+name);
                                view(gTarget,true,"9",1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerPatient.info(fName+"exit");
                            }
                            else{
                                menuMainSomebody();
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                        loggerPatient.info(fName+lsGlobalHelper.timeout_reaction_add);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuCharacter(boolean isEdit){
        String fName="[menuCharacter]";
        loggerPatient.info(fName);
        if(gUserProfile.getMember().getIdLong()==gMember.getIdLong()){
            menuCharacterWearer(isEdit);
        }else{
            menuCharacterSomebody();
        }
    }
    public void menuCharacterWearer(boolean isEdit){
        String fName="[menuCharacter]";
        loggerPatient.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        embed.setTitle(gTitle);
        embed.addField("Slot",String.valueOf(gCharacterIndex),false);
        String desc=":one: for name.";
        desc+="\n:two: for species.";
        desc+="\n:three: for age.";
        desc+="\n:four: for gender.";
        desc+="\n:five: for sexuality.";
        desc+="\n:six: for height.";
        desc+="\n:seven: for weight.";
        desc+="\n:eight: for description.";
        desc+="\n:nine: for adding attachment for image.";
        desc+="\n:lock: for reason for incarceration.";
        desc+="\n:timer_clock: for minimum stay.";
        desc+="\n:chains: for security.";
        desc+="\n:green_book: for medical record.";
        desc+="\n:blue_book: for treatment.";
        desc+="\n:spiral_calendar_pad: for schedule.";
        desc+="\n:smiling_imp: for incident options.";
        desc+="\n:spiral_note_pad: for notes options.";
        desc+="\n:white_check_mark: to finish.";
        embed.addField("Edit",desc,false);
        //embed.addField("Close","Select :x: to finish.",false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTimerClock));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasChains));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpiralCalendarPad));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenBook));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueBook));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSmilingImp));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpiralNotePad));
        if(isEdit) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
        lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/

        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        messageComponentManager.loadMessageComponents(gCommandFileCharacterPath);
        Message message=null;
        try {
            loggerPatient.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            loggerPatient.error(fName + ".exception=" + e3);
            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuCharacterWearerListener(message,isEdit);
    }
    public void menuCharacterWearerListener(Message message,boolean isEdit){
        String fName="[menuCharacterListener]";
        loggerPatient.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        loggerPatient.warn(fName+"value="+value);
                        llMessageDelete(message);
                        switch (value){
                            case lsUnicodeEmotes.aliasSpiralNotePad:
                                menuTextOptions(keyNotes,isEdit);
                                break;
                            case lsUnicodeEmotes.aliasSmilingImp:
                                menuTextOptions(keyIncidents,isEdit);
                                break;
                            case lsUnicodeEmotes.aliasChains:
                                menuSecurity(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasLock:
                                menuReason4Incarceration(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasSpiralCalendarPad:
                                menuSchedule(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasBlueBook:
                                menuTreatment(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasTimerClock:
                                menuMinimumStay(isEdit);
                                break;
                            case lsUnicodeEmotes.aliasGreenBook:
                                menuMedicalRecord(isEdit);
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
                                menuHeight(isEdit);
                                break;
                            case lsUnicodeEmotes.alias7:
                                menuWeight(isEdit);
                                break;
                            case lsUnicodeEmotes.alias8:
                                menuDescription(isEdit);
                                break;
                            case lsUnicodeEmotes.alias9:
                                menuImage(isEdit);
                                break;
                        }
                        
                    }catch (Exception e3){
                        loggerPatient.error(fName + ".exception=" + e3);
                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_selectmenu));
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        loggerPatient.warn(fName+"id="+id);
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
                        loggerPatient.error(fName + ".exception=" + e3);
                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> loggerPatient.info(fName+lsGlobalHelper.timeout_button));
        gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        loggerPatient.info(fName+"name="+name);
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpiralNotePad))){
                            menuTextOptions(keyNotes,isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSmilingImp))){
                            menuTextOptions(keyIncidents,isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasChains))){
                            menuSecurity(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                            menuReason4Incarceration(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpiralCalendarPad))){
                            menuSchedule(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueBook))){
                            menuTreatment(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTimerClock))){
                            menuMinimumStay(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenBook))){
                            menuMedicalRecord(isEdit);
                        }else
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
                            menuHeight(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                            menuWeight(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                            menuDescription(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                            menuImage(isEdit);
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                            delete(String.valueOf(gCharacterIndex));
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                            llSendQuickEmbedMessage(gUser, gTitle, "Done", llColorGreen2);
                        }else{
                            menuCharacter(isEdit);
                        }
                    }catch (Exception e3){
                        loggerPatient.error(fName + ".exception=" + e3);
                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                    llMessageDelete(message);
                    loggerPatient.info(fName+lsGlobalHelper.timeout_reaction_add);
                });

    }
    public void menuCharacterSomebody(){
        String fName="[menuCharacter]";
        loggerPatient.info(fName);

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
                        loggerPatient.info(fName+"name="+name);
                        llMessageDelete(message);
                    }catch (Exception e3){
                        loggerPatient.error(fName + ".exception=" + e3);
                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                    llMessageDelete(message);
                    loggerPatient.info(fName+lsGlobalHelper.timeout_reaction_add);
                });
    }
    List<PATIENT> gCharacters=new ArrayList<>();PATIENT gCharacter=new PATIENT(); int gCharacterIndex=0;
    public void saveCharacter(){
        String fName="[saveCharacter]";
        loggerPatient.info(fName);
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
        loggerPatient.info(fName);
        gCharacterIndex=index;
        gCharacter=gCharacters.get(gCharacterIndex);
    }
    public String getCharacterInfo(String key){
        String fName="[getCharacterInfo]";
        loggerPatient.info(fName);
        JSONObject jsonObject=gCharacter.getJSON();
        if(jsonObject.has(key)){
            return  jsonObject.getString(key);
        }
        return "";
    }
    public void menuName(boolean isEdit){
        String fName="[menuName]";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setName(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSpecies(boolean isEdit){
        String fName="[menuAge]";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setSpecies(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuAge(boolean isEdit){
        String fName="[menuAge]";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setAge(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(e.getChannel(),e.getMessageId());
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuGender(boolean isEdit){
        String fName="[menuGender]";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setGender(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSexuality(boolean isEdit){
        String fName="[menuSexuality]";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setSexuality(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuHeight(boolean isEdit){
        String fName="[menuHeight]";
        loggerPatient.info(fName);
        try{
            String likes=getCharacterInfo(keyHeight);Message message;
            if(likes!=null&&!likes.isBlank()&&!likes.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current height is "+likes+".\nPlease enter the characters height:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters height:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setHeight(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuWeight(boolean isEdit){
        String fName="[menuWeight]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keyWeight);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current weight is "+hates+".\nPlease enter the characters weight:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters weight:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setWeight(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuReason4Incarceration(boolean isEdit){
        String fName="[menuReason4Incarceration]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keyReason4Incarceration);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current reason for incarceration stay is "+hates+".\nPlease enter the characters reason for incarceration:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters reason for incarceration:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setReason4Incarceration(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMinimumStay(boolean isEdit){
        String fName="[menuMinimumStay]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keyMinimumStayDuration);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current minimum stay is "+hates+".\nPlease enter the characters minimum stay:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters minimum stay:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setMinimumStayDurationn(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSecurity(boolean isEdit){
        String fName="[menuSecurity]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keySecurity);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current security is "+hates+".\nPlease enter the characters security:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters security:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setSecurity(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuMedicalRecord(boolean isEdit){
        String fName="[menuMedicalRecord]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keyMedicalRecord);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current medical record is stay is "+hates+".\nPlease enter the characters medical record:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters medical record:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setMedicalRecord(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTreatment(boolean isEdit){
        String fName="[menuTreatment]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keyTreatment);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current medical treatment is stay is "+hates+".\nPlease enter the characters treatment:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters treatment:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setTreatment(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuSchedule(boolean isEdit){
        String fName="[menuSchedule]";
        loggerPatient.info(fName);
        try{
            String hates=getCharacterInfo(keySchedule);Message message;
            if(hates!=null&&!hates.isBlank()&&!hates.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current medical schedule is stay is "+hates+".\nPlease enter the characters schedule:", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the characters schedule:", llColorBlue1);
            }
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setSchedule(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuDescription(boolean isEdit){
        String fName="[menuDescription";
        loggerPatient.info(fName);
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
                            loggerPatient.info(fName+".content="+content);
                            gCharacter.setDesc(content);
                            saveCharacter();
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuImage(boolean isEdit){
        String fName="[menuDescription";
        loggerPatient.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please attach an image.\nIf the image can't be sent do to Discord explicit content filter, please type `!channel` to ask you in guild.", llColorBlue1);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content=e.getMessage().getContentStripped();
                            if(content!=null){
                                loggerPatient.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!channel")){
                                    llMessageDelete(message); menuImageGuild(isEdit);
                                    return;
                                }
                            }
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerPatient.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerPatient.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerPatient.error(fName + "  not image");
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
                                    loggerPatient.error(fName+".exception=" + e2);
                                    loggerPatient.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                            menuCharacter(isEdit);llMessageDelete(message);
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuImageGuild(boolean isEdit){
        String fName="[menuImage]";
        loggerPatient.info(fName);
        try{
            Message message=llSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Please attach an image, "+gMember.getAsMention()+".", llColorBlue1);
            gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            loggerPatient.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    loggerPatient.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                    loggerPatient.error(fName + "  not image");
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
                                loggerPatient.info(fName+".content="+content);
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
                                    loggerPatient.error(fName+".exception=" + e2);
                                    loggerPatient.error(fName+ ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    llSendQuickEmbedMessage(gUser, gTitle, "The input is invalid!", llColorRed);
                                }
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTextOptions(String key, boolean isEdit){
        String fName="[menuSelected]";
        loggerPatient.info(fName);
        try{

            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            JSONArray array=new JSONArray();
            switch (key){
                case  keyNotes:array=gCharacter.getNotes();break;
                case  keyIncidents:array=gCharacter.getIncidents();break;
            }
            String desc="";
            if(!array.isEmpty()) {
                desc += "\n:one: for view a record";
            }
            desc+="\n:two: for add a record";
            if(!array.isEmpty()){
                desc+="\n:three: for removing";
                desc+="\n:four: to clear";
            }
            boolean isBooster=llMemberIsBooster(gMember);
            if(key.equals(keyIncidents)){embed.setTitle("Patient Incidents");}
            if(key.equals(keyNotes)){embed.setTitle("Patient Notes");}
            if(isBooster){
                embed.addField("Lines used/total:", array.length() +"/30",false);
            }else{
                embed.addField("Lines used/total:", array.length() +"/15",false);
            }

            embed.addField("Options",desc,false);
            embed.addField("Done","Select :white_check_mark: to finish & go back",false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            if(!array.isEmpty()) {
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
            }
            if((isBooster&&array.length()<30)||array.length()<15){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
            }
            if(!array.isEmpty()){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            int lenght=array.length();
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerPatient.info(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                menuTextSelect(key,isEdit);
                            }else
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                if((isBooster&&lenght<30)||lenght<10){
                                    menuTextAdd(key,isEdit);
                                }else{
                                    menuTextOptions(key,isEdit);
                                }
                            }else
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                menuTextRemove(key,isEdit);
                            }else
                            if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                switch (key){
                                    case  keyNotes:gCharacter.clearNotes();break;
                                    case  keyIncidents:gCharacter.clearIncidents();break;
                                }
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                saveProfile();
                                menuCharacter(isEdit);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                loggerPatient.warn(fName+"trigger="+name);
                                menuCharacter(isEdit);
                            }else{
                                menuTextOptions(key,isEdit);
                            }

                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser,gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTextSelect(String key,boolean isEdit){
        String fName="[menuViewText]";
        loggerPatient.info(fName);
        try{
            JSONArray array=new JSONArray();
            switch (key){
                case  keyNotes:array=gCharacter.getNotes();break;
                case  keyIncidents:array=gCharacter.getIncidents();break;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple2);
            if(key.equals(keyIncidents)){embed.setTitle("Patient Incidents");}
            if(key.equals(keyNotes)){embed.setTitle("Patient Notes");}
            embed.setDescription("Please enter nr of line between 0-"+(array.length()-1)+" you want to view.Type '!cancel' to abort.");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            int lenght=array.length();
            JSONArray finalArray = array;
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                menuTextOptions(key,isEdit);
                            }else
                            if(content.isBlank()){
                                menuTextOptions(key,isEdit);
                            }else{
                                int i=Integer.parseInt(content);
                                if(i>=0&&i< lenght){
                                    menuTextView(key, finalArray.getString(i),isEdit);
                                }else{
                                    menuTextSelect(key,isEdit);
                                }
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTextView(String key,String text,boolean isEdit){
        String fName="[menuViewText]";
        loggerPatient.info(fName);
        try{
            JSONArray array=new JSONArray();
            switch (key){
                case  keyNotes:array=gCharacter.getNotes();break;
                case  keyIncidents:array=gCharacter.getIncidents();break;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple2);
            if(key.equals(keyIncidents)){embed.setTitle("Patient Incidents");}
            if(key.equals(keyNotes)){embed.setTitle("Patient Notes");}
            embed.setDescription(text);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerPatient.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerPatient.warn(fName+"asCodepoints="+name);
                            menuTextOptions(key,isEdit);
                            llMessageDelete(e.getChannel(),e.getMessageId());
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser,gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTextAdd(String key,boolean isEdit){
        String fName="[menuAddText]";
        loggerPatient.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple2);
            if(key.equals(keyIncidents)){embed.setTitle("Patient Incidents");}
            if(key.equals(keyNotes)){embed.setTitle("Patient Notes");}
            embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                menuTextOptions(key,isEdit);
                            }else
                            if(content.isBlank()){
                                menuTextOptions(key,isEdit);
                            }else{
                                switch (key){
                                    case  keyNotes:gCharacter.addNotes(content);break;
                                    case  keyIncidents:gCharacter.addIncidents(content);break;
                                }
                                saveCharacter();
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                }
                                menuTextOptions(key,isEdit);
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void menuTextRemove(String key,boolean isEdit){
        String fName="[menuRemoveText]";
        loggerPatient.info(fName);
        try{
            JSONArray array=new JSONArray();
            switch (key){
                case  keyNotes:array=gCharacter.getNotes();break;
                case  keyIncidents:array=gCharacter.getIncidents();break;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple2);
            if(key.equals(keyIncidents)){embed.setTitle("Patient Incidents");}
            if(key.equals(keyNotes)){embed.setTitle("Patient Notes");}
            embed.setDescription("Please enter nr of line between 0-"+(array.length()-1)+" you want to delete.Type '!cancel' to abort.");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            int lenght=array.length();
            gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerPatient.info(fName+".content="+content);
                            llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                menuTextOptions(key,isEdit);
                            }else
                            if(content.isBlank()){
                                menuTextOptions(key,isEdit);
                            }else{
                                int i=Integer.parseInt(content);
                                if(i>=0&&i< lenght){
                                    switch (key){
                                        case  keyNotes:gCharacter.remNotes(i);break;
                                        case  keyIncidents:gCharacter.remIncidents(i);break;
                                    }
                                    saveCharacter();
                                    if(!saveProfile()){
                                        llSendQuickEmbedMessage(gTextChannel,gTitle,strFailed2SaveProfile, llColorRed);return;
                                    }
                                    menuTextOptions(key,isEdit);
                                }else{
                                    menuTextRemove(key,isEdit);
                                }
                            }
                        }catch (Exception e3){
                            loggerPatient.error(fName + ".exception=" + e3);
                            loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    public Boolean getProfile(Member member){
        String fName="[getProfile]";
        loggerPatient.info(fName);
        try{
            loggerPatient.info(fName + ".member:"+ member.getId()+"|"+member.getUser().getName());
            gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild,profileName);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                loggerPatient.info(fName + ".is locally cached");
            }else{
                loggerPatient.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild,profileName);
                if(gUserProfile.getProfile(table)){
                    loggerPatient.info(fName + ".has sql entry");
                }
            }
            gUserProfile=iSafetyUserProfileEntry(gUserProfile);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                loggerPatient.info(fName + ".no update>ignore");
                gCharacters=getCharactersList(gUserProfile);
                return true;
            }
            if(!saveProfile()){ loggerPatient.error(fName+".failed to write in Db");
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
            gCharacters=getCharactersList(gUserProfile);
            return true;

        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        loggerPatient.info(fName);
        try{
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                loggerPatient.info(fName + ".success");return  true;
            }
            loggerPatient.warn(fName + ".failed");return false;
        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean getProfileL(){
        String fName="[getProfileL]";
        loggerPatient.info(fName);
        loggerPatient.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
        gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
        if(gUserProfile.getProfile(table)){
            loggerPatient.info(fName + ".has sql entry");  return true;
        }
        return false;
    }
    public Boolean saveProfileL(){
        String fName="[saveProfileL]";
        loggerPatient.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(table)){
            loggerPatient.info(fName + ".success");return  true;
        }
        loggerPatient.warn(fName + ".failed");return false;
    }



    public void slash(){
        String fName="[slash]";
        loggerPatient.info(fName);
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            slashReplyPleaseWait();
            if(isNsfwCommand&&!isNSFW()){
                loggerPatient.warn(fName+"nsfw command but wrong channel>return");
                embedBuilder.setDescription("NSFW channel is required!").setColor(llColorRed_Barn);
                sendOrUpdatePrivateEmbed(embedBuilder);
                return;
            }
            String subcommandname=gSlashCommandEvent.getSubcommandName();
            String subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
            loggerPatient.info(fName+"subcommandgroup="+subcommandgroup+", subcommandname="+subcommandname);
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
                                case KEYS.height:gCharacter.setHeight(option.getAsString());filedProv=true;break;
                                case KEYS.weight:gCharacter.setWeight(option.getAsString());filedProv=true;break;
                                case KEYS.reason:gCharacter.setReason4Incarceration(option.getAsString());filedProv=true;break;
                                case KEYS.minimum:gCharacter.setMinimumStayDurationn(option.getAsString());filedProv=true;break;
                                case KEYS.security:gCharacter.setSecurity(option.getAsString());filedProv=true;break;
                                case KEYS.medical:gCharacter.setMedicalRecord(option.getAsString());filedProv=true;break;
                                case KEYS.treatment:gCharacter.setTreatment(option.getAsString());filedProv=true;break;
                                case KEYS.schedule:gCharacter.setSchedule(option.getAsString());filedProv=true;break;
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
                                            loggerPatient.info(fName+".content="+content);
                                        }
                                        List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                        loggerPatient.info(fName + ".attachments.size=" + attachments.size());
                                        if (attachments.size() > 0) {
                                            Message.Attachment attachment = attachments.get(0);
                                            if (!attachment.isImage()) {
                                                loggerPatient.error(fName + ".attachment is not an image");
                                                llSendQuickEmbedMessage(gUser, gTitle, "The attachment is not an image!", llColorRed);
                                            }
                                            else if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                                loggerPatient.error(fName + ".image too big");
                                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "The image is above 10MB", llColorRed);
                                            }
                                            else if ((attachment.getWidth()>lsGlobalHelper.lsImageSizeLimit)||(attachment.getHeight()>lsGlobalHelper.lsImageSizeLimit)) {
                                                loggerPatient.error(fName + ".image too big");
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
                                            loggerPatient.info(fName+".content="+content);
                                            llSendQuickEmbedMessage(gUser, gTitle, "No attachment!", llColorRed);
                                        }
                                    }catch (Exception e3){
                                        loggerPatient.error(fName + ".exception=" + e3);
                                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
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
                            String line="";
                            for(OptionMapping option:options){
                                String name=option.getName();
                                switch (name){
                                    case KEYS.field:
                                        switch (option.getAsString()){
                                            case KEYS.field_main: mode=1;break;
                                            case KEYS.field_desc: mode=2;break;
                                            case KEYS.field_medical: mode=3;break;
                                            case KEYS.field_schedule: mode=4;break;
                                            case KEYS.field_incident: mode=5;break;
                                            case KEYS.field_note: mode=6;break;
                                        }
                                        break;

                                }
                            }
                            loggerPatient.info(fName+"mode="+mode);
                            String img="",name="<n/a>",species="<n/a>", age="<n/a>",gender="<n/a>",sexuality="</a>",description="<n/a>";
                            String height="", weight="";
                            String medicalRecord="<n/a>",treatment="<n/a>",reason4Incarceration="<n/a>",minimumStayDuration="<n/a>",schedule="<n/a>",security="<n/a>";
                            JSONArray arrayIncidents = new JSONArray(),arrayNotes =new JSONArray();
                            if(!gCharacter.getIncidents().isEmpty()){
                                arrayIncidents = gCharacter.getIncidents();
                            }
                            if(!gCharacter.getNotes().isEmpty()){
                                arrayNotes =gCharacter.getNotes();
                            }
                            String patientNr=iGenerateNumber(gUserProfile.getUser(), gCharacterIndex );
                            if(!gCharacter.getName().isBlank()){
                                name=gCharacter.getName();
                            }
                            if(!gCharacter.getSpecies().isBlank()){
                                species=gCharacter.getSpecies();
                            }
                            if(!gCharacter.getAge().isBlank()){
                                age=gCharacter.getAge();
                            }
                            if(!gCharacter.getSchedule().isBlank()){
                                schedule=gCharacter.getSchedule();
                            }
                            if(!gCharacter.getSexuality().isBlank()){
                                sexuality=gCharacter.getSexuality();
                            }
                            if(!gCharacter.getGender().isBlank()){
                                gender=gCharacter.getGender();
                            }
                            if(!gCharacter.getDesc().isBlank()){
                                description=gCharacter.getDesc();
                            }
                            if(!gCharacter.getHeight().isBlank()){
                                height=gCharacter.getHeight();
                            }
                            if(!gCharacter.getWeight().isBlank()){
                                weight=gCharacter.getWeight();
                            }
                            if(!gCharacter.getImg().isBlank()){
                                img=gCharacter.getImg();
                            }
                            if(!gCharacter.getMedicalRecord().isBlank()){
                                medicalRecord=gCharacter.getMedicalRecord();
                            }
                            if(!gCharacter.getTreatment().isBlank()){
                                treatment=gCharacter.getTreatment();
                            }
                            if(!gCharacter.getReason4Incarceration().isBlank()){
                                reason4Incarceration=gCharacter.getReason4Incarceration();
                            }
                            if(!gCharacter.getMinimumStayDuration().isBlank()){
                                minimumStayDuration=gCharacter.getMinimumStayDuration();
                            }
                            if(!gCharacter.getSecurity().isBlank()){
                                security=gCharacter.getSecurity();
                            }
                            EmbedBuilder embed=new EmbedBuilder();
                            embed.setColor(llColorPurple1);
                            embed.setAuthor(gUserProfile.getUser().getName()+"#"+gUserProfile.getUser().getDiscriminator(),null,gUserProfile.getUser().getEffectiveAvatarUrl());
                            if(mode==1){
                                embed.setTitle("Patient "+patientNr);
                                //embed.addField("Patient Nr",patientNr,true);
                                embed.addField("Name",name,true);
                                embed.addField("Species",species,true);
                                embed.addField("Age",age,true);
                                embed.addField("Gender",gender,true);
                                embed.addField("Sexuality",sexuality,true);
                                if(!weight.isBlank())embed.addField("Weight",weight,true);
                                if(!height.isBlank())embed.addField("Height",height,true);
                                embed.addBlankField(false);
                                if(!reason4Incarceration.isBlank())embed.addField("Reason for incarceration",reason4Incarceration,true);
                                if(!minimumStayDuration.isBlank())embed.addField("Minimum Stay Duration",minimumStayDuration,true);
                                if(!security.isBlank())embed.addField("Security",security,true);
                                if(!arrayIncidents.isEmpty())embed.addField("Incidents", String.valueOf(arrayIncidents.length()),true);
                                if(!arrayNotes.isEmpty())embed.addField("Notes", String.valueOf(arrayNotes.length()),true);
                                if(img!=null&&!img.isBlank()){
                                    embed.setImage(img);
                                }
                            }
                            if(mode==2){
                                embed.setTitle(patientNr+"'s about");
                                embed.addField("Description",description,false);
                            }
                            if(mode==3){
                                embed.setTitle(patientNr+"'s medical & treatment report");
                                embed.addField("Medical Record",medicalRecord,false);
                                embed.addField("Treatment",treatment,false);
                            }
                            if(mode==4){
                                embed.setTitle(patientNr+"'s Schedule report");
                                embed.addField("Schedule",schedule,false);
                            }
                            if(mode==5){
                                embed.setTitle(patientNr+"'s incident report");
                                if(arrayIncidents.isEmpty()){embed.setDescription("Nothing to display");}
                                else if(line.isBlank()){embed.setDescription("Please select between 0-"+(arrayIncidents.length()-1));}
                                else{
                                    int iLine=0;
                                    try {
                                        iLine=Integer.parseInt(line);
                                    }catch (Exception e){
                                        loggerPatient.error(fName + ".exception=" + e);
                                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                    if(iLine<0||iLine>arrayIncidents.length()-1){embed.setDescription("Invalid number!");}else{
                                        embed.setDescription(arrayIncidents.getString(iLine));
                                    }
                                }
                            }
                            if(mode==6){
                                embed.setTitle(patientNr+"'s note report");
                                if(arrayNotes.isEmpty()){embed.setDescription("Nothing to display");}
                                else if(line.isBlank()){embed.setDescription("Please select between 0-"+(arrayNotes.length()-1));}
                                else{
                                    int iLine=0;
                                    try {
                                        iLine=Integer.parseInt(line);
                                    }catch (Exception e){
                                        loggerPatient.error(fName + ".exception=" + e);
                                        loggerPatient.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                    if(iLine<0||iLine>arrayNotes.length()-1){embed.setDescription("Invalid number!");}else{
                                        embed.setDescription(arrayNotes.getString(iLine));
                                    }
                                }
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
                            embedBuilder.setColor(llColorGreen_Fern);
                            StringBuilder desc= new StringBuilder();int count=0;
                            for(PATIENT character:gCharacters){
                                desc.append("\n").append(count).append(character.getName());count++;
                            }
                            embedBuilder.setDescription("Characters:"+desc);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                            break;
                        case "new":
                            options=gSlashCommandEvent.getOptions();
                            filedProv=false;nameProv=false;
                            options=gSlashCommandEvent.getOptions();
                            for(OptionMapping option:options){
                                switch (option.getName()){
                                    case KEYS.name:gCharacter.setName(option.getAsString());filedProv=true;break;
                                    case KEYS.species:gCharacter.setSpecies(option.getAsString());filedProv=true;break;
                                    case KEYS.gender:gCharacter.setGender(option.getAsString());filedProv=true;break;
                                    case KEYS.age:gCharacter.setAge(String.valueOf(option.getAsLong()));filedProv=true;break;
                                    case KEYS.sexuality:gCharacter.setSexuality(option.getAsString());filedProv=true;break;
                                    case KEYS.desc:gCharacter.setDesc(option.getAsString());filedProv=true;break;
                                    case KEYS.height:gCharacter.setHeight(option.getAsString());filedProv=true;break;
                                    case KEYS.weight:gCharacter.setWeight(option.getAsString());filedProv=true;break;
                                    case KEYS.reason:gCharacter.setReason4Incarceration(option.getAsString());filedProv=true;break;
                                    case KEYS.minimum:gCharacter.setMinimumStayDurationn(option.getAsString());filedProv=true;break;
                                    case KEYS.security:gCharacter.setSecurity(option.getAsString());filedProv=true;break;
                                    case KEYS.medical:gCharacter.setMedicalRecord(option.getAsString());filedProv=true;break;
                                    case KEYS.treatment:gCharacter.setTreatment(option.getAsString());filedProv=true;break;
                                    case KEYS.schedule:gCharacter.setSchedule(option.getAsString());filedProv=true;break;
                                }
                            }
                            gCharacter=new PATIENT();
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
                                embedBuilder.setDescription("Patient "+gCharacter.getName()+" saved.");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }else{
                                embedBuilder.setColor(llColorRed_Barn);
                                embedBuilder.setDescription("Failed to add new patient!");
                                sendOrUpdatePrivateEmbed(embedBuilder);
                            }
                            break;
                        default:
                            embedBuilder.setDescription("Invalid command").setColor(llColorRed_Barn);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                    }
            }




        } catch (Exception e) {
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashProfile(Member member){
        String fName="[slashProfile]";
        loggerPatient.info(fName);
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
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public void slashCharacter(){
        String fName="[slashCharacter]";
        loggerPatient.info(fName);
        try{
            int slot= Math.toIntExact(gSlashCommandEvent.getOption("slot").getAsLong());
            loggerPatient.info(fName+"slot="+slot);
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
            loggerPatient.error(fName+".exception=" + e);
            loggerPatient.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }


    
}
