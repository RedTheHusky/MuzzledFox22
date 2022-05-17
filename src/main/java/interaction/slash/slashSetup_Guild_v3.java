package interaction.slash;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.emotes.lcEmote;
import models.lc.interaction.applicationcommand.lcApplicationCCommonBuilder;
import models.lc.interaction.applicationcommand.lcApplicationCCommonEditor;
import models.lc.interaction.applicationcommand.lcApplicationsCCommonManager;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.lcText2Json;
import models.lc.lcTempZipFile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Red;
import models.ll.llCommonKeys;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class slashSetup_Guild_v3 extends Command implements llMessageHelper, llGlobalHelper {
    String cName="[slashCommands_Guild]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gTitle="Slash Commands [WIP]", gCommand= "slash";
    public slashSetup_Guild_v3(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = gTitle;
        this.help = "Guild slash commands";
        this.aliases = new String[]{gCommand,"slashSCommands3","slashCommands3"};
        this.guildOnly = true;
        this.hidden=false;this.category= llCommandCategory_Utility;
    }
    public slashSetup_Guild_v3(lcGlobalHelper g, SlashCommandEvent event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(cName+fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }

    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;

        SlashCommandEvent gSlashCommandEvent=null;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;
        public runLocal(CommandEvent ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }

        public runLocal(SlashCommandEvent ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            gSlashCommandEvent = ev;
            gUser = ev.getUser();
            logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gMember=ev.getMember();
                gGuild = ev.getGuild();
                gTextChannel =ev.getTextChannel();
                logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            try {
                if(gSlashCommandEvent!=null){
                    logger.info(cName+fName+"slash jda@");
                    if(!gSlashCommandEvent.isFromGuild()){
                        EmbedBuilder embedBuilder=lsMessageHelper.lsErrorEmbed(null,"Slash","Ony available in guild",null);
                        gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                    }
                    if(gSlashCommandEvent.getName().equalsIgnoreCase("slash")){
                        menuDM("main");
                    }
                }else{
                    logger.info(cName+fName+"basic@");
                    boolean isInvalidCommand = true;
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(cName+fName+".Args=0");
                        menuDM("main");isInvalidCommand=false;
                    }else {
                        logger.info(cName + fName + ".Args");
                        String []items = gEvent.getArgs().split("\\s+");
                        if(items[0].equalsIgnoreCase("help")){
                            help( "main");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("menu")){
                            menuDM( "main");isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("update")){
                            updateGuild();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("updateall")){
                            updateAll();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("check")){
                            checkProfile();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("set")){
                            if(items.length>=2){
                                set(items[1]);isInvalidCommand=false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("remove")){
                            if(items.length>=2){
                                remove(items[1]);isInvalidCommand=false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("toggle")){
                            if(items.length>=2){
                                toggle(items[1]);isInvalidCommand=false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("clear")){
                            clearGuild();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("get")){
                            getGuild();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("json")){
                            if(items.length>=2){
                                getJsonZip(items[1]);
                            }else{
                                getJsonZip();
                            }
                            isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("profile")){
                            if(items.length>=2){
                                if(items[1].equalsIgnoreCase("reset")){
                                    profileDelete();isInvalidCommand=false;
                                }
                            }else{
                                getProfileZip();
                                isInvalidCommand=false;
                            }

                        }
                    }
                    logger.info(cName+fName+".deleting op message");
                    llMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        
        private void help(String command){
            String fName="help";
            logger.info(cName + fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embed=new EmbedBuilder();
            String desc="";
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            desc="The bot needs the scope `applications.commands` in order to add slash commands.";
            String urlScope="https://discord.com/api/oauth2/authorize?client_id="+gGuild.getSelfMember().getId()+"&scope=applications.commands";
            logger.info(cName + fName + ".urlScope:"+urlScope);
            desc+="\nThis is a new feature of discord.";
            desc+="\nIn case it does not have the scope, use this link to grant it: "+lsUsefullFunctions.getUrlTextString("grant applications.commands", urlScope);
            embed.addField("IMPORTANT",desc,false);
            embed.addField("DM Menu","Use `"+quickSummonWithSpace+"menu` to access the DM menu with options.",false);
            desc="`"+quickSummonWithSpace+"set|remove bdsm|verify|santa|gifts`";
            desc+="\n- First type in if you `set` it, that enabled it and adds the menu or updates it.\nTo remove type `remove`";
            desc+="\n- Also type what category of commands you want to add: bdsm, verify, santa, gifts";
            desc="\n`"+quickSummonWithSpace+"update`";
            embed.addField("Commands",desc,false);
            desc="\nRun `"+quickSummonWithSpace+"clear` to clear all group commands";
            desc+="\nRun `"+quickSummonWithSpace+"profile reset` if there is issue with the profile";
            embed.addField("Clear&Reset",desc,false);
            llSendMessage(gUser,embed);
        }
        lcEmote emoteUpdate;
        private void menuDM(String command){
            String fName="[menuDM]";
            try {
                logger.info(cName + fName + ".command:"+command);
                String quickSummonWithSpace=llPrefixStr+gCommand+" ";
                emoteUpdate=new lcEmote(gGuild,lsEmote.gMuzzle_eUpdate);

                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle); embed.setColor(llColorBlue1);
                String desc="";
                initProfile();
                /*try {
                    desc="Includes quick commands for the dear santa functions, both the sfw and nsfw version";
                    desc+="Command: `"+llPrefixStr+"dearsanta`, `"+llPrefixStr+"dearnsanta`";
                    desc+="Status:";
                    if(gProfileGuild.jsonUser.getJSONObject(fieldSanta).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="Use "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" to toggle.";
                    embed.addField("Dear Santa",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }*/
                /*try {
                    desc="Includes quick commands for the naughty christmas gifts functions.";
                    desc+="Command: `"+llPrefixStr+"gift`";
                    desc+="Status:";
                    if(gProfileGuild.jsonUser.getJSONObject(fieldGifts).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="Use "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" to toggle.";
                    embed.addField("Gifts",desc,false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }*/
                /*{
                    desc="Includes quick commands for the bdsm functions, such as: gag, cuffs, suit...";
                    desc+="\nCommand: `"+llPrefixStr+"rd`";
                    desc+="\nStatus:";
                    if(gProfileGuild.jsonUser.getJSONObject(fieldBDSM).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" to toggle.";
                        embed.addField("BDSM",desc,false);
                }*/
                {
                    desc="Includes quick commands for the verification functions, such for chastity, collar verification.";
                    desc+="\nCommand: `"+llPrefixStr+"verify`";
                    desc+="\nStatus:";
                    if(gProfileGuild.jsonObject.getJSONObject(fieldVerify).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" to toggle.";
                    embed.addField("Verify",desc,false);
                }
                {
                    desc="Includes quick commands for the pishock functions.";
                    desc+="\nCommand: `"+llPrefixStr+"pishock`";
                    desc+="\nStatus:";
                    if(gProfileGuild.jsonObject.getJSONObject(fieldPishock).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" to toggle.";
                    embed.addField("PiShock",desc,false);
                }
               /*{
                    desc="Includes quick commands for the emlalock functions.";
                    desc+="\nCommand: `"+llPrefixStr+"emlalock`";
                    desc+="\nStatus:";
                    if(gProfileGuild.jsonUser.getJSONObject(fieldEmlalock).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" to toggle.";
                    embed.addField("Emlalock",desc,false);
                }*/
                /*{
                    desc="Includes quick commands for the lovense remote toys.";
                    desc+="\nCommand: `"+llPrefixStr+"lovense`";
                    desc+="\nStatus:";
                    if(gProfileGuild.jsonUser.getJSONObject(fieldLovense).getBoolean(keyEnabled)) desc+=" enabled";
                    else desc+=" disabled";
                    desc+="\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" to toggle.";
                    embed.addField("Lovense",desc,false);
                }*/
                embed.addField("Updating","If you think the slash menus are not up to date, please click "+emoteUpdate.getAsMention()+".",false);
                desc="The bot needs the scope `applications.commands` in order to add slash commands.";
                String urlScope="https://discord.com/api/oauth2/authorize?client_id="+gGuild.getSelfMember().getId()+"&scope=applications.commands";
                logger.info(cName + fName + ".urlScope:"+urlScope);
                desc+="\nThis is a new feature of discord.";
                desc+="\nIn case it does not have the scope, use this link to grant it: "+lsUsefullFunctions.getUrlTextString("grant applications.commands", urlScope);
                embed.addField("IMPORTANT",desc,false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser, embed);
                messageOptions(command,message);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void messageOptions(String command, Message message){
            String fName="[messageOptions]";
            try {
                logger.info(cName + fName + ".command:"+command);
                logger.info(cName + fName + ".message:"+message.getId());
                if(emoteUpdate==null) emoteUpdate=new lcEmote(gGuild,lsEmote.gMuzzle_eUpdate);
                /*try {
                    if(gProfileGuild.jsonUser.has(fieldSanta)){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }*/
                /*try {
                    if(gProfileGuild.jsonUser.has(fieldGifts)){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }*/
                if(gProfileGuild.jsonObject.has(fieldBDSM)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                }
                if(gProfileGuild.jsonObject.has(fieldVerify)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                }
                if(gProfileGuild.jsonObject.has(fieldPishock)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                }
                /*if(gProfileGuild.jsonUser.has(fieldEmlalock)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                if(gProfileGuild.jsonUser.has(fieldLovense)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                }*/

                lsMessageHelper.lsMessageAddReactions(message,emoteUpdate.getEmote());
                messageListen(command,message);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void messageListen(String command, Message message){
            String fName="[messageListen]";
            try {
                logger.info(cName + fName + ".command:"+command);
                logger.info(cName + fName + ".message:"+message.getId());
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                if(e.getReactionEmote().isEmoji()){
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                    logger.warn(fName+"asCodepoints="+name);
                                    if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldBDSM);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldVerify);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldPishock);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldEmlalock);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldLovense);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldSanta);
                                    }
                                    else if(name.contains(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG))){
                                        logger.warn(fName+"trigger="+name);
                                        toggle(fieldGifts);
                                    }else{
                                        messageListen(command,message);
                                        return;
                                    }
                                }
                                if(e.getReactionEmote().isEmote()){
                                    if(emoteUpdate==null) emoteUpdate=new lcEmote(gGuild,lsEmote.gMuzzle_eUpdate);
                                    Emote emote=e.getReactionEmote().getEmote();
                                    if(emote.getIdLong()==emoteUpdate.getEmote().getIdLong()){
                                        updateGuild();
                                    }else{
                                        messageListen(command,message);
                                        return;
                                    }
                                }
                                llMessageDelete(e.getChannel(),e.getMessageId());
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            logger.info(fName+"timeout");
                            llMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void checkProfile(){
            String fName="[add]";
            logger.info(cName + fName);
            try {
                initProfile();
                logger.info(cName + fName+"profile="+ gProfileGuild.jsonObject.toString());
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Check the logs",llColorOrange);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        lcApplicationsCCommonManager applicationsCCommonManager= new lcApplicationsCCommonManager();
        lcApplicationCCommonEditor applicationsCCommonEditor = new lcApplicationCCommonEditor();
        lcApplicationCCommonBuilder applicationsCCommonBuilder = new lcApplicationCCommonBuilder();
        private void toggle(String field){
            String fName="[toggle]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                initProfile();
                logger.info(cName + fName+"field="+field);
                logger.info(cName + fName+"jsonProfile="+gProfileGuild.jsonObject.toString());
                if(!gProfileGuild.jsonObject.has(field)){
                    logger.info(cName + fName+"no such field");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No such field `"+field+"` to toggle!", llColors_Red.llColorRed_Cinnabar);
                    return;
                }
                if(gProfileGuild.jsonObject.getJSONObject(field).getBoolean(keyEnabled)){
                    remove(field);
                }else{
                    set(field);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        //https://discord.com/developers/docs/interactions/slash-commands#application-command-object-application-command-option-type
        String fieldVerify="verify", fieldBDSM="bdsm", fieldGifts="gifts",fieldSanta="santa",fieldPishock="pishock",fieldEmlalock="emlalock",fieldLovense="lovense";
        private void set(String field){
            String fName="[set]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                logger.info(cName + fName+"field="+field);
                initProfile();
                logger.info(cName + fName+"jsonProfile="+gProfileGuild.jsonObject.toString());
                JSONObject jsonObject=new JSONObject();
                if(!readFile(field)){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(field,0)+" slash command file could not be read.",llColorOrange);
                    return;
                }

                jsonObject=text2Json.jsonObject;
                logger.info(cName + fName+"json="+jsonObject.toString());
                String name=jsonObject.optString(llCommonKeys.ApplicationCommandStructure.Name);
                if(!gProfileGuild.jsonObject.has(name)){
                    gProfileGuild.safetyCreateFieldEntry(name);
                    gProfileGuild.safetyPutFieldEntry(name,keyEnabled,false);
                    gProfileGuild.safetyPutFieldEntry(name,keyId,0L);
                }
                applicationsCCommonManager.request(gGuild);
                applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(name);
                if(applicationsCCommonEditor==null){
                    applicationsCCommonEditor=applicationsCCommonManager.getById(gProfileGuild.jsonObject.getJSONObject(name).optLong(keyId));
                }
                if(applicationsCCommonEditor!=null){
                    logger.info(cName + fName+"exists=true");
                    applicationsCCommonEditor.setGuild(gGuild).setData(jsonObject);
                    if(applicationsCCommonEditor.patch()){
                        logger.info(cName + fName+"update=true");
                        long id=applicationsCCommonEditor.getIdAsLong();
                        gProfileGuild.jsonObject.getJSONObject(name).put(keyId,id);
                        gProfileGuild.jsonObject.getJSONObject(name).put(keyEnabled,true);
                        saveProfile();
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" slash command updated.",llColorOrange);
                        //llSendQuickEmbedMessage(gUser,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" slash command updated.",llColorOrange);
                    }else{
                        logger.info(cName + fName+"update=false");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" lash command failed to updated.",llColorRed_Cinnabar);
                        //llSendQuickEmbedMessage(gUser,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" lash command failed to updated.",llColorRed_Cinnabar);
                    }
                   }else{
                    logger.info(cName + fName+"exists=false");
                    applicationsCCommonBuilder.setGuild(gGuild).set(jsonObject);
                    applicationsCCommonEditor=applicationsCCommonBuilder.post();
                    if(applicationsCCommonEditor!=null){
                        logger.info(cName + fName+"added=true");
                        long id=applicationsCCommonEditor.getIdAsLong();
                        gProfileGuild.jsonObject.getJSONObject(name).put(keyId,id);
                        gProfileGuild.jsonObject.getJSONObject(name).put(keyEnabled,true);
                        saveProfile();
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" slash command added.",llColorOrange);
                        //llSendQuickEmbedMessage(gUser,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" slash command added.",llColorOrange);
                    }else{
                        logger.info(cName + fName+"false=true");
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" lash command failed to add.",llColorRed_Cinnabar);
                        //llSendQuickEmbedMessage(gUser,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(name,0)+" lash command failed to add.",llColorRed_Cinnabar);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void remove(String field){
            String fName="[remove]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                logger.info(cName + fName+"field="+field);
                initProfile();
                logger.info(cName + fName+"jsonProfile="+gProfileGuild.jsonObject.toString());
                JSONObject jsonObject=new JSONObject();
                if(readFile(field)){
                    jsonObject=text2Json.jsonObject;
                }
                try {
                    if(gProfileGuild.jsonObject.getJSONObject(field).has(keyId)){
                        applicationsCCommonManager.request(gGuild);
                        applicationsCCommonEditor=applicationsCCommonManager.getById(gProfileGuild.jsonObject.getJSONObject(field).getLong(keyId));
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(applicationsCCommonEditor==null){
                    try {
                        if(jsonObject.has(keyName)){
                            applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(jsonObject.getString(keyName));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(applicationsCCommonEditor==null){
                    applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(field);
                }
                if(applicationsCCommonEditor!=null){
                    logger.info(cName + fName+"has true>delete");
                    if(applicationsCCommonEditor.delete()){
                        logger.info(cName + fName+"deleted");
                        try {
                            if(gProfileGuild.jsonObject.has(field)){
                                gProfileGuild.jsonObject.getJSONObject(field).put(keyId,0L);
                                gProfileGuild.jsonObject.getJSONObject(field).put(keyEnabled,false);
                                saveProfile();
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(field,0)+" slash command deleted",llColorOrange);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(field,0)+" slash command failed to delete",llColorOrange);
                    }

                }else{
                    logger.info(cName + fName+"has false>ignore");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,lsStringUsefullFunctions.changeChar2UpperCaseAtPosition(field,0)+" slash command does not exists to be deleted.",llColorOrange);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void getJsonZip(String field){
            String fName="[getJsonZip]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                logger.info(cName + fName+"field="+field);
                applicationsCCommonManager.request(gGuild);
                if(applicationsCCommonManager.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No slash commands.",llColorRed_Cinnabar);
                    return;
                }
                applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(field);
                if(applicationsCCommonEditor!=null){
                    lcTempZipFile zipFile=new lcTempZipFile();
                    try {
                        JSONObject jsonMeta=new JSONObject();
                        jsonMeta.put(llCommonKeys.keyGuild,gGuild.getIdLong());
                        JSONObject jsonCommand=applicationsCCommonEditor.getJson();
                        logger.info(fName+"jsonMeta="+jsonMeta.toString());
                        logger.info(fName+"jsonCommand="+jsonCommand.toString());
                        zipFile.addEntity("meta.json",jsonMeta.toString());
                        zipFile.addEntity("command.json",jsonCommand.toString());
                        InputStream in=zipFile.getInputStream();
                        if(in!=null){
                            gTextChannel.sendMessage("Here is the json you asked:").addFile(in,applicationsCCommonEditor.getName()+".zip").complete();
                            return;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                llSendQuickEmbedMessage(gTextChannel,gTitle,"No such slash command.",llColorRed_Cinnabar);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getJsonZip(){
            String fName="[getJsonZip]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                applicationsCCommonManager.request(gGuild);
                if(applicationsCCommonManager.isEmpty()){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"No slash commands.",llColorRed_Cinnabar);
                    return;
                }
                lcTempZipFile zipFile=new lcTempZipFile();
                JSONObject jsonMeta=new JSONObject();
                jsonMeta.put(llCommonKeys.keyGuild,gGuild.getIdLong());
                jsonMeta.put(llCommonKeys.keyCount,applicationsCCommonManager.size());
                zipFile.addEntity("meta.json",jsonMeta);
                for(lcApplicationCCommonEditor command:applicationsCCommonManager.get()){
                    zipFile.addEntity(command.getName()+".zip",command.getJson());
                }
                gTextChannel.sendMessage("Here is the json you asked:").addFile(zipFile.getInputStream(),gGuild.getId()+"slashs.zip").complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getProfileZip(){
            String fName="[getProfileZip]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                initProfile();
                InputStream targetStream = new ByteArrayInputStream(gProfileGuild.jsonObject.toString().getBytes());
                gTextChannel.sendMessage("Here is the json you asked:").addFile(targetStream,gGuild.getId()+"slash.json").complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void profileDelete(){
            String fName="[profileDelete]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                initProfile();
                gProfileGuild.jsonObject =new JSONObject();
                saveProfile();
                gTextChannel.sendMessage("Deleted").complete();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void updateAll(){
            String fName="[updateAll]";
            logger.info(cName + fName);
            try {
                if(!gGlobal.isPartOfTeam(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Updating commands...", llColors.llColorGreen2);
                List<Guild> guildsList=gGlobal.getGuildList();
                for(int i=0;i<guildsList.size();i++){
                    try {
                        Guild guild=guildsList.get(i);
                        logger.info(cName + fName+"guild["+i+"]:"+guild.getName()+"("+guild.getId()+")");
                        if(initProfile4Update(guild)){
                            logger.info(cName + fName+"guild["+i+"]: checking");
                            applicationsCCommonManager.request(guild);
                            Iterator<String>keys=gProfileGuild.jsonObject.keys();
                            while(keys.hasNext()){
                                String key=keys.next();
                                logger.info(cName + fName+"guild["+i+"]:key="+key);
                                try {
                                    if(gProfileGuild.jsonObject.has(key)){
                                        logger.info(cName + fName+"guild["+i+"],key["+key+"]: has such entry in profile");
                                        if(gProfileGuild.jsonObject.getJSONObject(key).getBoolean(keyEnabled)){
                                            logger.info(cName + fName+"guild["+i+"],key["+key+"]: enabled");
                                            if(!readFile(key)){
                                                logger.info(cName + fName+"guild["+i+"],key["+key+"]: file not read");

                                            }if(text2Json.jsonObject.isEmpty()){
                                                logger.info(cName + fName+"guild["+i+"],key["+key+"]: jsonObject.isEmpty");
                                            }
                                            else{
                                                logger.info(cName + fName+"json="+text2Json.jsonObject.toString());
                                                long id=0;
                                                if(gProfileGuild.jsonObject.getJSONObject(key).has(keyId)){
                                                    id=gProfileGuild.jsonObject.getJSONObject(key).optLong(keyId);
                                                }
                                                applicationsCCommonEditor=applicationsCCommonManager.getById(id);
                                                if(applicationsCCommonEditor==null&&text2Json.jsonObject.has(keyName)){
                                                    applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(text2Json.jsonObject.optString(keyName));
                                                }
                                                if(applicationsCCommonEditor==null){
                                                    applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(key);
                                                }
                                                if(text2Json.jsonObject.has("deleted")||text2Json.jsonObject.has("removed")){
                                                    logger.info(cName + fName+"its a delete entry");
                                                    if(applicationsCCommonEditor==null){
                                                        logger.info(cName + fName+"skip deletin as it does not exists");
                                                    }
                                                    if(applicationsCCommonEditor.delete()){
                                                        gProfileGuild.jsonObject.getJSONObject(key).put(keyId,0L);
                                                        gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,false);
                                                    }
                                                }else{
                                                    if(applicationsCCommonEditor!=null){
                                                        logger.info(cName + fName+"key["+key+"]: found>update");
                                                        applicationsCCommonEditor.setData(text2Json.jsonObject);
                                                        if(applicationsCCommonEditor.patch()){
                                                            logger.info(cName + fName+"key["+key+"]: updated");
                                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyId,applicationsCCommonEditor.getIdAsLong());
                                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,true);
                                                            if(!saveProfile()){
                                                                logger.info(cName + fName+"key["+key+"]: failed to save");
                                                            }
                                                        }else{
                                                            logger.info(cName + fName+"key["+key+"]: failed 2 updated");
                                                        }

                                                    }else{
                                                        logger.info(cName + fName+"not found>create");
                                                        applicationsCCommonBuilder.setGuild(gGuild).set(text2Json.jsonObject);
                                                        applicationsCCommonEditor=applicationsCCommonBuilder.post();
                                                        if(applicationsCCommonEditor!=null){
                                                            logger.info(cName + fName+"key["+key+"]: created");
                                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyId,applicationsCCommonEditor.getIdAsLong());
                                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,true);
                                                            if(!saveProfile()){
                                                                logger.info(cName + fName+"key["+key+"]: failed to save");
                                                            }

                                                        }else{
                                                            logger.info(cName + fName+"key["+key+"]: failed 2 create");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        logger.info(cName + fName+"guild["+i+"],key["+key+"]: no such entry in profile");
                                    }
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }

                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                lsMessageHelper.lsMessageDelete(message);
                lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Updating commands finished.", llColors.llColorGreen2);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void updateGuild(){
            String fName="[updateGuild]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                applicationsCCommonManager.request(gGuild);
                initProfile();
                Iterator<String>keys=gProfileGuild.jsonObject.keys();
                Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Updating commands...", llColors.llColorGreen2);
                while(keys.hasNext()){
                    String key=keys.next();
                    logger.info(cName + fName+"key="+key);
                    try {
                        if(gProfileGuild.jsonObject.has(key)){
                            logger.info(cName + fName+"key["+key+"]: has such entry in profile");
                            if(gProfileGuild.jsonObject.getJSONObject(key).getBoolean(keyEnabled)){
                                if(!readFile(key)){
                                    logger.info(cName + fName+"key["+key+"]: file not read");

                                }if(text2Json.jsonObject.isEmpty()){
                                    logger.info(cName + fName+"key["+key+"]: jsonObject.isEmpty");
                                }
                                else{
                                    logger.info(cName + fName+"json="+text2Json.jsonObject.toString());
                                    long id=0;
                                    if(gProfileGuild.jsonObject.getJSONObject(key).has(keyId)){
                                        id=gProfileGuild.jsonObject.getJSONObject(key).optLong(keyId);
                                    }
                                    applicationsCCommonEditor=applicationsCCommonManager.getById(id);
                                    if(applicationsCCommonEditor==null&&text2Json.jsonObject.has(keyName)){
                                        applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(text2Json.jsonObject.optString(keyName));
                                    }
                                    if(applicationsCCommonEditor==null){
                                        applicationsCCommonEditor=applicationsCCommonManager.getByNameIgnoreCase(key);
                                    }
                                    if(text2Json.jsonObject.has("deleted")||text2Json.jsonObject.has("removed")){
                                        logger.info(cName + fName+"its a delete entry");
                                        if(applicationsCCommonEditor==null){
                                            logger.info(cName + fName+"skip deletin as it does not exists");
                                        }
                                        if(applicationsCCommonEditor.delete()){
                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyId,0L);
                                            gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,false);
                                        }
                                    }else{
                                        if(applicationsCCommonEditor!=null){
                                            logger.info(cName + fName+"key["+key+"]: found>update");
                                            applicationsCCommonEditor.setData(text2Json.jsonObject);
                                            if(applicationsCCommonEditor.patch()){
                                                logger.info(cName + fName+"key["+key+"]: updated");
                                                gProfileGuild.jsonObject.getJSONObject(key).put(keyId,applicationsCCommonEditor.getIdAsLong());
                                                gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,true);
                                                if(!saveProfile()){
                                                    logger.info(cName + fName+"key["+key+"]: failed to save");
                                                }
                                            }else{
                                                logger.info(cName + fName+"key["+key+"]: failed 2 updated");
                                            }

                                        }else{
                                            logger.info(cName + fName+"not found>create");
                                            applicationsCCommonBuilder.setGuild(gGuild).set(text2Json.jsonObject);
                                            applicationsCCommonEditor=applicationsCCommonBuilder.post();
                                            if(applicationsCCommonEditor!=null){
                                                logger.info(cName + fName+"key["+key+"]: created");
                                                gProfileGuild.jsonObject.getJSONObject(key).put(keyId,applicationsCCommonEditor.getIdAsLong());
                                                gProfileGuild.jsonObject.getJSONObject(key).put(keyEnabled,true);
                                                if(!saveProfile()){
                                                    logger.info(cName + fName+"key["+key+"]: failed to save");
                                                }

                                            }else{
                                                logger.info(cName + fName+"key["+key+"]: failed 2 create");
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            logger.info(cName + fName+"key["+key+"]: no such entry in profile");
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                lsMessageHelper.lsMessageDelete(message);
                lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Updating commands finished.", llColors.llColorGreen2);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }


        private void getGuild(){
            String fName="[getGuild]";
            logger.info(cName + fName);
            try {

                String url = lsDiscordApi.ApplicationCommand.Guild.lsGetCommands_Or_RegisteringCommand(gGlobal.configfile.getBot().getId(),gGuild.getId());
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.get(url)
                        .header("Authorization", "Bot "+gGlobal.configfile.getBot().getToken())
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorOrange_InternationalEngineering);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Success, checks logs",llColorGreen1);
                }
                JSONArray body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getGuildTest(){
            String fName="[getGuild]";
            logger.info(cName + fName);
            try {
                //lcSLASHCOMMAND slashcommands=new lcSLASHCOMMAND(789536298737991692L,gGuild);
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Checks logs",llColorGreen1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String deniedMessage="Denied! Require server manage permission.";
        private void clearGuild(){
            String fName="[clearGuild]";
            logger.info(cName + fName);
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)){
                    logger.info(cName + fName+"denied");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,deniedMessage,llColorRed_Cinnabar);
                    return;
                }
                initProfile();
                List<net.dv8tion.jda.api.interactions.commands.Command>commands=gGuild.retrieveCommands().complete();
                for(net.dv8tion.jda.api.interactions.commands.Command command:commands){
                    try {
                        String name=command.getName();
                        command.delete().complete();
                        gProfileGuild.jsonObject.remove(name);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                saveProfile();
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Success, checks logs",llColorGreen1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        String gUrlMainSoloPath="resources/json/slash/guild";
        lcText2Json text2Json=null;
        private boolean readFile(String name) {
            String fName="[readFile]";
            logger.info(fName);
            try {
                File file1, file2;
                InputStream fileStream=null;
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".txt");
                    file1=new File(gUrlMainSoloPath+"/"+name+".txt");
                    if(file1.exists()){
                        logger.info(fName+".file1 exists");
                        fileStream = new FileInputStream(file1);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".json");
                    file2=new File(gUrlMainSoloPath+"/"+name+".json");
                    if(file2.exists()){
                        logger.info(fName+".file2 exists");
                        fileStream = new FileInputStream(file2);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                text2Json=new lcText2Json();
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        return false;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public lcJSONGuildProfile gProfileGuild;
        String gProfileName ="slashGuilds";
        String keyEnabled="enabled",keyName="name",keyIsNSFW="isNSFW",keyId="id", keyHasNSFW="hasNSFW",keyHasSFW="hasSfw";

        void iSafetyProfile(){
            String fName="iSafetyCreate";
            try {
                String field;
                field=fieldVerify;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,false);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldBDSM;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,false);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldGifts;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,false);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldSanta;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,true);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldPishock;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,true);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldEmlalock;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,false);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
                field=fieldLovense;
                gProfileGuild.safetyCreateFieldEntry(field);
                gProfileGuild.safetyPutFieldEntry(field,keyIsNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyHasSFW,false);gProfileGuild.safetyPutFieldEntry(field,keyHasNSFW,true);
                gProfileGuild.safetyPutFieldEntry(field,keyEnabled,false);
                gProfileGuild.safetyPutFieldEntry(field,keyId,0L);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public void initProfile(){
            String fName="[initCollar]";
            logger.info(fName+".safety check");
            try{
                gProfileGuild =gGlobal.getGuildSettings(gGuild, gProfileName);
                if(gProfileGuild ==null||!gProfileGuild.isExistent()|| gProfileGuild.jsonObject.isEmpty()){
                    gProfileGuild =new lcJSONGuildProfile(gGlobal, gProfileName,gGuild,llv2_GuildsSettings);
                    gProfileGuild.getProfile(llv2_GuildsSettings);
                    if(!gProfileGuild.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfileGuild);
                    }
                }
                iSafetyProfile();
                if(gProfileGuild.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfileGuild);
                    if(gProfileGuild.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public boolean initProfile4Update(Guild guild){
            String fName="[initProfile4Update]";
            logger.info(fName+".safety check");
            try{
                gProfileGuild =gGlobal.getGuildSettings(guild, gProfileName);
                if(gProfileGuild ==null||!gProfileGuild.isExistent()|| gProfileGuild.jsonObject.isEmpty()){
                    logger.info(fName + ".is none existent 1");
                    gProfileGuild =new lcJSONGuildProfile(gGlobal, gProfileName,guild,llv2_GuildsSettings);
                    gProfileGuild.getProfile(llv2_GuildsSettings);

                }
                if(gProfileGuild ==null||!gProfileGuild.isExistent()|| gProfileGuild.jsonObject.isEmpty()){
                    logger.info(fName + ".is none existent 2");
                    return false;
                }
                iSafetyProfile();
                if(gProfileGuild.isUpdated){
                    if(gProfileGuild.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
                return  true;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean saveProfile(){
            String fName="[saveCollar]";
            logger.info(fName);
            try{
                logger.info(fName+".profile="+gProfileGuild.jsonObject.toString());
                gGlobal.putGuildSettings(gGuild, gProfileGuild);
                if(gProfileGuild.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean saveProfile4Update(Guild guild){
            String fName="[saveCollar4Update]";
            logger.info(fName);
            try{
                logger.info(fName+".profile="+gProfileGuild.jsonObject.toString());
                gGlobal.putGuildSettings(guild, gProfileGuild);
                if(gProfileGuild.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
    
  //runLocal  
    }
}
