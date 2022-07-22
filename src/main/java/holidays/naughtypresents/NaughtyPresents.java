//revising doPreviews for gifts

package holidays.naughtypresents;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class NaughtyPresents extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper globalL;
    String gTitle=iNaughtyPresents.gTitle,gCommand=iNaughtyPresents.gCommand;
    public NaughtyPresents(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        globalL =global;
        this.name = gTitle;
        this.help = "Small fun command for the holiday season.";
        this.aliases = new String[]{gCommand,"naughtygifts","naughtygift","gift","gifts","present"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;

    }
    public NaughtyPresents(lcGlobalHelper global, SlashCommandEvent event){
        String fName="[constructor]";
        logger.info(fName);
        globalL =global;
        Runnable r = new runLocal(event);new Thread(r).start();
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
    protected class runLocal extends naughtyPresentsExt implements Runnable {


        public runLocal(CommandEvent ev) {
            logger.info(".run build");
            launch(globalL,ev);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(globalL,ev);
        }
        @Override
        public void run() {
            String fName = "[run]";
            try{
                logger.info(".run start");
                setTitleStr(iNaughtyPresents.gTitle);setPrefixStr(llGlobalHelper.llPrefixStr);setCommandStr(gCommand);
                loadBasic("naughtypresents2020");
                if(gSlashCommandEvent!=null){
                    slash();
                }
                else{
                    logger.info(cName + fName + "basic@");
                    boolean isInvalidCommand = true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        else {
                            loadMainProfiles();
                            loadUserProfiles();
                            menuMain();
                            isInvalidCommand=false;
                        }
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs!=null&&gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand=false;
                        }
                        else if(isCommand2BasicFeatureControl(gItems)){
                            isInvalidCommand=false;
                        }
                        else if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }

                        /*if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");

                        }*/
                        if(isInvalidCommand){
                            loadMainProfiles();
                            switch(gItems[0].toLowerCase()){
                                case "nowait":
                                    if(gItems.length==2){
                                        undeoTimeWait(gItems[1]);
                                    }else{
                                        loadUserProfiles();
                                        undeoTimeWait();
                                    }
                                    isInvalidCommand=false;
                                    break;
                                case "reset":
                                    if(gItems.length==2){
                                        resetUser(gItems[1]);
                                    }else{
                                        loadUserProfiles();
                                        resetUser();
                                    }
                                    isInvalidCommand=false;
                                    break;
                                case "event":
                                    loadUserProfiles();
                                    if(gItems.length>=2&&gItems[1].equalsIgnoreCase("dm")){
                                       doEventDM();
                                    }else{
                                        doEvent();
                                    }
                                    isInvalidCommand=false;
                                    break;
                                case "event_index":
                                    loadUserProfiles();
                                    if(gItems.length==3&&gItems[2].equalsIgnoreCase("dm")){
                                        doEventDM(gItems[1]);
                                    }else
                                    if(gItems.length==2){
                                        doEvent(gItems[1]);
                                    }else{
                                        doEvent();
                                    }
                                    isInvalidCommand=false;
                                    break;
                                case "inventory":
                                    if(gItems.length>=3){
                                        doInventory(gItems[1],gItems[2]);
                                    }else
                                    if(gItems.length==2){
                                        loadUserProfiles();
                                        doInventory(gItems[1]);
                                    }else{
                                        loadUserProfiles();
                                        doInventory(0);
                                    }
                                    isInvalidCommand=false;
                                    break;
                                case "gift":
                                    if(gItems.length>=3){
                                        loadUserProfiles();
                                       sendGift(gItems[1],gItems[2]); isInvalidCommand=false;
                                    }
                                    break;
                                case "use":
                                    if(gItems.length==3){
                                        loadUserProfiles();
                                        doUse4Other(gItems[1],gItems[2]); isInvalidCommand=false;
                                    }else
                                    if(gItems.length==2){
                                        loadUserProfiles();
                                        doUse4Self(gItems[1]); isInvalidCommand=false;
                                    }
                                    break;
                                case "reload":
                                    reload(); isInvalidCommand=false;
                                    break;
                            }
                        }

                    }
                    logger.info(fName+".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }


            }
            catch (Exception ex){ logger.error(fName+".exception=" + ex+", StackTrace:"+Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
            logger.info(".run ended");
        }



        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String desc;
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            desc="`"+quickSummonWithSpace+"` to open menu";
            desc+="\n`"+quickSummonWithSpace+"event/event dm` to receive a present";
            desc+="\n`"+quickSummonWithSpace+"inventory [index] [user]` to open inventory menu";
            desc+="\n`"+quickSummonWithSpace+"gift [user] [index] ` to gift index present to user";
            desc+="\n`"+quickSummonWithSpace+"use [index]` to use index present, available from Dec 24";
            desc+="\n`"+quickSummonWithSpace+"use [index] [user]` to use index present on user, available from Dec 24";
            if(lsMemberHelper.lsMemberIsBotOwner(gMember)){
                desc+="\n`"+quickSummonWithSpace+"reset|nowait` ";
                desc+="\n`"+quickSummonWithSpace+"reload` ";
            }
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }

        private void menuMain(){
            String fName="[menuMain]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            String desc;
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
            boolean patreon=entityUser.isPatreon();
            int eventSize=entityUser.eventsSize();
            embed.addField("Event participated",""+eventSize,false);
            int presentSize=entityUser.presentsSize();
            embed.addField("Presents",""+presentSize,false);
            desc="Small fun command for the holiday season. In order to receive presents you need to participate in events. Events are flavor text with a story to it. Some stories will target you as a sub, even enforce a restraint from rd functions, like the gag.\nPlease select an option.\n• There is a timeout, 6 hours, using the event command to not spam and receive alot of gifts.\n• The gifts received are invisible till Dec 24.\n• Non patreon support users have max "+iNaughtyPresents.maxEvents+"events";
            if(!patreon){
                desc+="\nYou are non patreon support";
            }else{
                desc+="\nYou are patreon support user";
            }
            embed.setDescription(desc);
            Message message;
            messageComponentManager.loadMessageComponents(iNaughtyPresents.gMainMenuFile);
            try {
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                lcMessageBuildComponent.Button buttonEvent=messageComponentManager.messageBuildComponents.getButtonAt(0,0);
                lcMessageBuildComponent.Button buttonEventDm=messageComponentManager.messageBuildComponents.getButtonAt(0,1);
                lcMessageBuildComponent.Button buttonInventory=messageComponentManager.messageBuildComponents.getButtonAt(0,2);
                if(!patreon&&eventSize>iNaughtyPresents.maxEvents){
                    buttonEvent.setDisable(); buttonEventDm.setDisable();
                }
                if(presentSize<=0){
                    buttonInventory.setDisable();
                }
                logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).complete();
            }
            menuMainListener(message);
        }
        private void menuMainListener(Message message){
            String fName="[menuMain]";
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            switch (id){
                                case iNaughtyPresents.comGoEvent:
                                    doEvent();
                                    break;
                                case iNaughtyPresents.comGoEvent_Dm:
                                    doEventDM();
                                    break;
                                case iNaughtyPresents.comInventory:
                                    menuPresents(0,null);
                                    break;
                                case iNaughtyPresents.comHelp:
                                    help("main");
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
                /*gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getId();
                                logger.warn(fName+"id="+id);
                                String value=e.getValues().get(0);
                                logger.warn(fName+"value="+value);
                                String level="";
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));*/
            if(!message.isFromGuild()){
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    help("main");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))){
                                    doEvent();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))){
                                    doEventDM();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFileCabinet))){
                                    doInventory(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);
                            logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    help("main");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))){
                                    doEvent();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))){
                                    doEventDM();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFileCabinet))){
                                    doInventory(0);
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);
                            logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }
        }
        private void menuPresents(int index,Member target){
            String fName="[menuPresents]";
            logger.info(fName+"index="+index);
            int sizePresents= entityUser.presentsSize();
            logger.info(fName+"sizePresents="+sizePresents);
            entityUserProfile4NaughtyPresents.Present presentUser=entityUser.getPresent(index);
            logger.info(fName+"presentUser.json="+presentUser._getJson().toString());
            entity4NaughtyPresents.Present presentGeneral=null;
            entity4NaughtyPresents.Event eventGeneral=null;
            if(presentUser!=null){
                logger.info(fName+"presentUser.key="+presentUser.getRefKey());
                presentGeneral=entityGeneral.getPresent( presentUser.getRefKey());
                if(presentGeneral!=null)logger.info(fName+"presentGeneral.key="+presentGeneral.getKey());
                logger.info(fName+"presentUser.key="+presentUser.getEventKey());
                eventGeneral=entityGeneral.getEvent(presentUser.getEventKey());
                if(eventGeneral!=null)logger.info(fName+"eventGeneral.key="+eventGeneral.getKey());
            }

            EmbedBuilder embed;
            JSONObject jsonObject;
            if(iNaughtyPresents.isPresentHidden){
                jsonObject=presentGeneral.getEmbedHiddenJson();
                if(jsonObject!=null&&!jsonObject.isEmpty()){
                    if(jsonObject.has(llCommonKeys.EmbedBuildStructure.description)){
                        String description=jsonObject.optString(llCommonKeys.EmbedBuildStructure.description);
                        if(presentUser.isGifted()){
                            description+="\nSent a gift to !TARGET at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGift());
                            description=stringReplacer2(description,gMember.getId(),presentUser.getTarget());
                        }else
                        if(presentUser.isGift()){
                            description+="\nReceived as a gift from !TARGET at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGot());
                            description=stringReplacer2(description,gMember.getId(),presentUser.getTarget());
                        }else
                        if(eventGeneral!=null){
                            description=stringReplacer2(description,gMember.getId());
                            description+="\nGot from !EVENT at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGot());
                            description=stringReplacerEventName(description,eventGeneral.getName());
                        }
                        else{
                            description=stringReplacer2(description,gMember.getId());
                        }
                        if(presentGeneral!=null){
                            description+="\nHP:"+presentUser.getHp()+"/"+presentGeneral.getHp();
                        }else{
                            description+="\nHP:"+presentUser.getHp()+"/?";
                        }
                        jsonObject.put(llCommonKeys.EmbedBuildStructure.description,description);
                    }else{
                        jsonObject.put(llCommonKeys.EmbedBuildStructure.description,"(null)");
                    }
                }
            }else{
                jsonObject=presentGeneral.getEmbedShowJson();
                if(jsonObject!=null&&!jsonObject.isEmpty()){
                    if(jsonObject.has(llCommonKeys.EmbedBuildStructure.description)){
                        String description=jsonObject.optString(llCommonKeys.EmbedBuildStructure.description);
                        if(presentUser.isGifted()){
                            description+="\nSent a gift to !TARGET at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGift());
                            description=stringReplacer2(description,gMember.getId(),presentUser.getTarget());
                        }else
                        if(presentUser.isGift()){
                            description+="\nReceived as a gift from !TARGET at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGot());
                            description=stringReplacer2(description,gMember.getId(),presentUser.getTarget());
                        }else
                        if(eventGeneral!=null){
                            description=stringReplacer2(description,gMember.getId());
                            description+="\nGot from !EVENT at "+lsUsefullFunctions.convertOffsetDateTime2HumanReadable(presentUser.getTimestampGot());
                            description=stringReplacerEventName(description,eventGeneral.getName());
                        }
                        else{
                            description=stringReplacer2(description,gMember.getId());
                        }
                        if(presentGeneral!=null){
                            description+="\nHP:"+presentUser.getHp()+"/"+presentGeneral.getHp();
                        }else{
                            description+="\nHP:"+presentUser.getHp()+"/?";
                        }
                        jsonObject.put(llCommonKeys.EmbedBuildStructure.description,description);
                    }else{
                        jsonObject.put(llCommonKeys.EmbedBuildStructure.description,"(null)");
                    }
                }
            }
            embed=iNaughtyPresents.buildEmbed(jsonObject);
            if(embed!=null){
                embed.setColor(llColorBlue1);
            }else{
                embed=new EmbedBuilder();
                embed.setDescription("Failed tp get embed info!");
                embed.setColor(llColorRed_Chili);
            }
            embed.setTitle(gTitle);
            if(!iNaughtyPresents.isPresentHidden){
                if(target==null){
                    embed.addField("Target",entityUser.getMember().getAsMention(),false);
                }else{
                    embed.addField("Target",target.getAsMention(),false);
                }
            }
            if(gInteractionHook!=null||gSlashCommandEvent!=null||entityUser.getMember().getIdLong()!=gMember.getIdLong()){
                embed.setTitle(entityUser.getMember().getUser().getName()+"'s "+gTitle);
            }else{
                embed.setTitle(gTitle);
            }

            Message message;
            messageComponentManager.loadMessageComponents(iNaughtyPresents.gPresentsMenuFile);
            try {
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                lcMessageBuildComponent.Button buttonInventoryBack=messageComponentManager.messageBuildComponents.getButtonAt(0,0);
                lcMessageBuildComponent.Button buttonInventoryUse=messageComponentManager.messageBuildComponents.getButtonAt(0,1);
                lcMessageBuildComponent.Button buttonInventoryForward=messageComponentManager.messageBuildComponents.getButtonAt(0,2);
                lcMessageBuildComponent.Button buttonUtilUp=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                lcMessageBuildComponent.Button buttonUtilInfo=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                lcMessageBuildComponent.Button buttonUtilClose=messageComponentManager.messageBuildComponents.getButtonAt(1,2);

                if(iNaughtyPresents.isPresentHidden){
                    buttonInventoryUse.setDisable();
                }else{
                    if(presentGeneral==null){
                        buttonInventoryUse.setDisable();
                    }else if(presentGeneral.getEmbedUseSelfJsons()==null&&presentGeneral.getEmbedUseSelfJsons().isEmpty()){
                        buttonInventoryUse.setDisable();
                    }
                }
                if(sizePresents==0){
                    buttonInventoryBack.setDisable();
                    buttonInventoryForward.setDisable();
                }else{
                    if(index==0)buttonInventoryBack.setDisable();
                    if(index>=sizePresents-1)buttonInventoryForward.setDisable();
                }
                if(gMember.getIdLong()!=entityUser.getMember().getIdLong()){
                    buttonUtilUp.setIgnored();
                }
                logger.info(fName+"component_after3="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).complete();
            }
            menuPresentsListener(message,index,target);
        }
        private void menuPresentsListener(Message message, int index,Member target){
            String fName="[menuPresents]";
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            switch (id){
                                case iNaughtyPresents.comUp:
                                    menuMain();
                                    break;
                                case iNaughtyPresents.comBack:
                                    menuPresents(index-1,target);
                                    break;
                                case iNaughtyPresents.comForward:
                                    menuPresents(index+1,target);
                                    break;
                                case iNaughtyPresents.comUse:
                                    if(entityUser.getMember().getIdLong()!=gMember.getIdLong()){
                                        doUse4Holder(index);
                                    }else if(target==null){
                                        doUse4Self(index);
                                    }else{
                                        doUse4Other(index,target);
                                    }
                                    break;
                                case iNaughtyPresents.comHelp:
                                    help("main");
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
            if(!message.isFromGuild()){
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    help("main");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuMain();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReversePlayButton))){
                                    menuPresents(index-1,target);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                    menuPresents(index+1,target);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    if(entityUser.getMember().getIdLong()!=gMember.getIdLong()){
                                        doUse4Holder(index);
                                    }else if(target==null){
                                        doUse4Self(index);
                                    }else{
                                        doUse4Other(index,target);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);
                            logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuMain();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReversePlayButton))){
                                    menuPresents(index-1,target);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                    menuPresents(index+1,target);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                    if(entityUser.getMember().getIdLong()!=gMember.getIdLong()){
                                        doUse4Holder(index);
                                    }else if(target==null){
                                        doUse4Self(index);
                                    }else{
                                        doUse4Other(index,target);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);
                            logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }
        }
        private void doEvent() throws Exception {
            String fName="[doEvent]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            List<entity4NaughtyPresents.Event> events=entityGeneral.getEvents();
            int size=events.size();
            logger.info(fName+"size="+size);
            int index=lsUsefullFunctions.getRandom(size);
            if(index>=size)index=size-1;
            if(index<0)index=0;
            logger.info(fName+"index="+index);
            entity4NaughtyPresents.Event event=events.get(index);
            doEvent(event);
        }
        private void doEvent(String number){
            String fName="[doEvent]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            try{
                int index=Integer.parseInt(number);
                doEvent(index);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedError("Not a number!");
            }
        }
        private void doEvent(int index) throws Exception {
            String fName="[doEvent]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            List<entity4NaughtyPresents.Event> events=entityGeneral.getEvents();
            int size=events.size();
            logger.info(fName+"size="+size+", index="+index);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            entity4NaughtyPresents.Event event=events.get(index);
            doEvent(event);
        }
        private void doEvent(entity4NaughtyPresents.Event event) throws Exception {
            String fName="[doEvent]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            long timestamp_call=entityUser.getTimestampCall();
            logger.info(fName+"timestamp_call="+timestamp_call);
            Timestamp timestamp_current = new Timestamp(System.currentTimeMillis());
            logger.info(fName+"timestamp_current="+timestamp_current);
            long time_diff=timestamp_current.getTime()-timestamp_call;
            logger.info(fName+"time_diff="+time_diff+", timeoutBetweenEvents="+iNaughtyPresents.timeoutBetweenEvents);
            if(time_diff<iNaughtyPresents.timeoutBetweenEvents){
                long time_wait=iNaughtyPresents.timeoutBetweenEvents-time_diff;
                logger.info(fName+"need to wait="+time_wait);
                sendEmbedError("Needto wait for "+iNaughtyPresents.convertDuration2Human(time_wait)+".");
                return;
            }
            embed=event.getEmbed();
            if(embed==null){
                sendEmbedError("Failed tp get embed info!");
                return;
            }
            embed.setTitle(gTitle).setDescription(stringReplacer2(embed.getDescriptionBuilder().toString(),gMember.getId()));
            entityUser.addEvent(event);
            List<entity4NaughtyPresents.Present> presents=event.getPresents4Event();
            logger.info(fName+"presents.size="+presents.size());
            for(entity4NaughtyPresents.Present present:presents){
                entityUser.addPresent(present,event);
            }
            entityUser.setTimestampCall(timestamp_current.getTime());
            sendEmbed(embed);
            if(gResturnedMessage==null){
                throw  new Exception("Failed to send embed");
            }
            entityUser.saveProfile();
            logger.info(fName+"check for restraints");
            List<entity4NaughtyPresents.Event.Restraint> restraints=event.getRestraints();
            if(restraints!=null){
                logger.info(fName+"restraints="+restraints.size());
                try{
                    for(entity4NaughtyPresents.Event.Restraint restraint:restraints){
                        String rkey=restraint.getKey(),rvalue0,rvalue1,rvalue2;
                        switch (rkey){
                            case "gag":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cGAG.release();
                                }else{
                                    entityRDUser.cGAG.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "cuffs_arm":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cARMCUFFS.release();
                                }else{
                                    entityRDUser.cARMCUFFS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "cuffs_leg":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cLEGCUFFS.release();
                                }else{
                                    entityRDUser.cLEGCUFFS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "mitts":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cMITTS.release();
                                }else{
                                    entityRDUser.cMITTS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "ear":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cEAR.release();
                                }else{
                                    entityRDUser.cEAR.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "blindfold":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cBLINDFOLD.release();
                                }else{
                                    entityRDUser.cBLINDFOLD.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "collar":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cCOLLAR.release();
                                }else{
                                    entityRDUser.cCOLLAR.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "suit":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                rvalue1=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Suit.matterial);
                                rvalue2=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Suit.type);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0+", "+rvalue1+", "+rvalue2);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cSUIT.release();
                                }else{
                                    entityRDUser.cSUIT.setSuitMatterial(rvalue1).setSuitType(rvalue2).setOn(true);
                                }
                                break;
                            case "sj":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.wear);
                                rvalue1=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.arm);
                                rvalue2=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.crotch);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0+", "+rvalue1+", "+rvalue2);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cSTRAITJACKET.release();
                                }else{
                                    entityRDUser.cSTRAITJACKET.setOn(true);
                                    if(rvalue1.equalsIgnoreCase("off")){
                                        entityRDUser.cSTRAITJACKET.setArmsStrapped(false);
                                    }else{
                                        entityRDUser.cSTRAITJACKET.setArmsStrapped(true).setArmsLevel(rvalue1);
                                    }
                                    if(rvalue2.equalsIgnoreCase("off")){
                                        entityRDUser.cSTRAITJACKET.setCrotchStrapped(false);
                                    }else{
                                        entityRDUser.cSTRAITJACKET.setCrotchStrapped(true).setCrotchLevel(rvalue2);
                                    }
                                }
                                break;
                        }
                        if(entityRDUser.saveProfile()){
                            logger.info(fName+"success rd.save");
                        }else{
                            logger.warn(fName+"failed rd.save");
                        }
                    }
                }catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
                }
            }else{
                logger.warn(fName+"restraints is null");
            }
            logger.info(fName+"done");
        }
        private void doEventDM() throws Exception {
            String fName="[doEventDM]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedErrorDM("NSFW channel is required!");
                return;
            }
            List<entity4NaughtyPresents.Event> events=entityGeneral.getEvents();
            int size=events.size();
            logger.info(fName+"size="+size);
            int index=lsUsefullFunctions.getRandom(size);
            if(index>=size)index=size-1;
            if(index<0)index=0;
            logger.info(fName+"index="+index);
            entity4NaughtyPresents.Event event=events.get(index);
            doEventDM(event);
        }
        private void doEventDM(String number){
            String fName="[doEventDM]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedErrorDM("NSFW channel is required!");
                return;
            }
            try{
                int index=Integer.parseInt(number);
                doEventDM(index);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedErrorDM("Not a number!");
            }
        }
        private void doEventDM(int index) throws Exception {
            String fName="[doEventDM]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            List<entity4NaughtyPresents.Event> events=entityGeneral.getEvents();
            int size=events.size();
            logger.info(fName+"size="+size+", index="+index);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedErrorDM("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedErrorDM("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedErrorDM("Index must be bellow "+(size-1)+"!");
                return;
            }
            entity4NaughtyPresents.Event event=events.get(index);
            doEventDM(event);
        }
        private void doEventDM(entity4NaughtyPresents.Event event) throws Exception {
            String fName="[doEvent]";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorRed_Chili);
            long timestamp_call=entityUser.getTimestampCall();
            logger.info(fName+"timestamp_call="+timestamp_call);
            Timestamp timestamp_current = new Timestamp(System.currentTimeMillis());
            logger.info(fName+"timestamp_current="+timestamp_current);
            long time_diff=timestamp_current.getTime()-timestamp_call;
            logger.info(fName+"time_diff="+time_diff+", timeoutBetweenEvents="+iNaughtyPresents.timeoutBetweenEvents);
            if(time_diff<iNaughtyPresents.timeoutBetweenEvents){
                long time_wait=iNaughtyPresents.timeoutBetweenEvents-time_diff;
                logger.info(fName+"need to wait="+time_wait);
                sendEmbedErrorDM("Needto wait for "+iNaughtyPresents.convertDuration2Human(time_wait)+".");
                return;
            }
            embed=event.getEmbed();
            if(embed==null){
                sendEmbedErrorDM("Failed tp get embed info!");
                return;
            }
            embed.setTitle(gTitle).setDescription(stringReplacer2(embed.getDescriptionBuilder().toString(),gMember.getId()));
            entityUser.addEvent(event);
            List<entity4NaughtyPresents.Present> presents=event.getPresents4Event();
            logger.info(fName+"presents.size="+presents.size());
            for(entity4NaughtyPresents.Present present:presents){
                entityUser.addPresent(present,event);
            }
            entityUser.setTimestampCall(timestamp_current.getTime());
            gResturnedMessage=lsMessageHelper.lsSendMessageResponse(gUser,embed);
            if(gResturnedMessage==null){
                throw  new Exception("Failed to send embed");
            }
            entityUser.saveProfile();
            logger.info(fName+"check for restraints");
            List<entity4NaughtyPresents.Event.Restraint> restraints=event.getRestraints();
            if(restraints!=null){
                logger.info(fName+"restraints="+restraints.size());
                try{
                    for(entity4NaughtyPresents.Event.Restraint restraint:restraints){
                        String rkey=restraint.getKey(),rvalue0="",rvalue1="",rvalue2="";
                        switch (rkey){
                            case "gag":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cGAG.release();
                                }else{
                                    entityRDUser.cGAG.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "cuffs_arm":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cARMCUFFS.release();
                                }else{
                                    entityRDUser.cARMCUFFS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "cuffs_leg":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cLEGCUFFS.release();
                                }else{
                                    entityRDUser.cLEGCUFFS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "mitts":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cMITTS.release();
                                }else{
                                    entityRDUser.cMITTS.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "ear":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cEAR.release();
                                }else{
                                    entityRDUser.cEAR.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "blindfold":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cBLINDFOLD.release();
                                }else{
                                    entityRDUser.cBLINDFOLD.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "collar":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cCOLLAR.release();
                                }else{
                                    entityRDUser.cCOLLAR.setLevel(rvalue0).setOn(true);
                                }
                                break;
                            case "suit":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.level);
                                rvalue1=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Suit.matterial);
                                rvalue2=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Suit.type);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0+", "+rvalue1+", "+rvalue2);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cSUIT.release();
                                }else{
                                    entityRDUser.cSUIT.setSuitMatterial(rvalue1).setSuitType(rvalue2).setOn(true);
                                }
                                break;
                            case "sj":
                                rvalue0=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.wear);
                                rvalue1=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.arm);
                                rvalue2=restraint.getValue(iNaughtyPresents.KEYS_Store.Event.Restraint.Sj.crotch);
                                logger.info(fName+"restraint["+rkey+"]="+rvalue0+", "+rvalue1+", "+rvalue2);
                                if(rvalue0.equalsIgnoreCase("off")){
                                    entityRDUser.cSTRAITJACKET.release();
                                }else{
                                    entityRDUser.cSTRAITJACKET.setOn(true);
                                    if(rvalue1.equalsIgnoreCase("off")){
                                        entityRDUser.cSTRAITJACKET.setArmsStrapped(false);
                                    }else{
                                        entityRDUser.cSTRAITJACKET.setArmsStrapped(true).setArmsLevel(rvalue1);
                                    }
                                    if(rvalue2.equalsIgnoreCase("off")){
                                        entityRDUser.cSTRAITJACKET.setCrotchStrapped(false);
                                    }else{
                                        entityRDUser.cSTRAITJACKET.setCrotchStrapped(true).setCrotchLevel(rvalue2);
                                    }
                                }
                                break;
                        }
                        if(entityRDUser.saveProfile()){
                            logger.info(fName+"success rd.save");
                        }else{
                            logger.warn(fName+"failed rd.save");
                        }
                    }
                }catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,e.toString());
                }
            }else{
                logger.warn(fName+"restraints is null");
            }
            logger.info(fName+"done");
        }
        private void doInventory(String number,String user){
            String fName="[doInventory]";
            logger.info(fName + "number="+number+", user="+user);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            if (!mentions.isEmpty()) {
                logger.warn(fName + ".has mention list");
                if(mentions.size()>1){
                    logger.warn(fName + ".too many mentions, only 1 allowed");
                    sendEmbedError("Too many mentioned, only 1 can be mentioned!");
                    return;
                }
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else
            if((user.contains("<!@")||user.contains("<@"))&&user.contains(">")) {
                logger.info(fName + ".detect mention characters");
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else{
                logger.warn(fName + ".zero mentions char");
                sendEmbedError("Nobody mentioned!");
                return;
            }
            try{
                int index=Integer.parseInt(number);
                doInventory(index);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedError("Not a number!");
            }
        }
        private void doInventory(String number){
            String fName="[doInventory]";
            logger.info(fName + "number="+number);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            try{
                int index=Integer.parseInt(number);
                doInventory(index);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedError("Not a number!");
            }
        }
        private void doInventory(int index){
            String fName="[doInventory]";
            logger.info(fName + "index="+index);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            loadUserProfiles();
            int size=entityUser.presentsSize();
            if(size<=0) {
                logger.warn(fName + ".you have no presents");
                if(gTarget==null){
                    embedBuilder.setColor(llColorRed_Chili).setDescription("You have no presents!");
                }else{
                    embedBuilder.setColor(llColorRed_Chili).setDescription(gTarget.getAsMention()+" has no presents!");
                }
                sendEmbed(embedBuilder);
                autoDeleteResturnedMessage();
                return;
            }
            logger.info(fName + ".index="+index+", size="+size);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            menuPresents(index,null);
        }
        private void sendGift(String user,String number){
            String fName="[sendGift]";
            logger.info(fName + ".user="+user+", number="+number);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            Member member=null;
            if (!mentions.isEmpty()) {
                logger.warn(fName + ".has mention list");
                if(mentions.size()>1){
                    logger.warn(fName + ".too many mentions, only 1 allowed");
                    sendEmbedError("Too many mentioned, only 1 can be mentioned!");
                    return;
                }
                member = lsMemberHelper.lsGetMember(gGuild, user);
            }else
            if((user.contains("<!@")||user.contains("<@"))&&user.contains(">")) {
                logger.info(fName + ".detect mention characters");
                member = lsMemberHelper.lsGetMember(gGuild, user);
            }else{
                logger.warn(fName + ".zero mentions char");
                sendEmbedError("Nobody mentioned!");
                return;
            }

            try{
                int index=Integer.parseInt(number);
                sendGift(member,index);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedError("Not a number!");
            }
        }
        private void sendGift(Member member,int index){
            String fName="[sendGift]";
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            if (member == null) {
                logger.warn(fName + ".zero member mentions");
                sendEmbedError("Nobody mentioned!");
                return;
            } else if (member.getIdLong() == gUser.getIdLong()) {
                logger.warn(fName + ".target cant be the gUser");
                sendEmbedError("Can't self send gift!");
                return;
            }
            entityUserProfile4NaughtyPresents targetProfile=new entityUserProfile4NaughtyPresents();
            if(!targetProfile.build(gGlobal,member)){
                logger.warn(fName + ".failed to get target");
                sendEmbedError("Failed to get target profile!");
                return;
            }
            int size=entityUser.presentsSize();
            if(size<=0) {
                logger.warn(fName + ".you have no presents");
                embedBuilder.setColor(llColorRed_Chili).setDescription("You have no presents to gift away!");
                sendEmbed(embedBuilder);
                return;
            }
            logger.info(fName + ".index="+index+", size="+size);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            entityUserProfile4NaughtyPresents.Present present=entityUser.getPresent(index);
            if(targetProfile.addPresentAsGift(present,gUser)==null){
                logger.warn(fName + ".could not add present as gift");
                sendEmbedError("Failed to gift away present!");
                return;
            }
            if(!targetProfile.saveProfile()){
                logger.warn(fName + ".could not save");
                sendEmbedError("Failed to gift away present!");
                return;
            }
            if(present.setGifted(member.getUser())==null||!entityUser.saveProfile()){
                logger.warn(fName + ".could not save");
                sendEmbedError("Error!");
                return;
            }
            embedBuilder.setColor(llColorGreen2).setDescription("Successfully gifted away to "+member.getAsMention()+".");
            sendEmbed(embedBuilder);
            EmbedBuilder embedBuilder1=new EmbedBuilder();
            embedBuilder1.setTitle(gTitle).setColor(llColorGreen2).setDescription("Received a gift from "+member.getAsMention()+".");
        }
        private void doUse4Self(String numberorUser){
            String fName="[doUse4Self]";
            logger.info(fName+"numberorUser="+numberorUser);
            Member target=null;
            if((numberorUser.contains("<!@")||numberorUser.contains("<@"))&&numberorUser.contains(">")) {
                logger.info(fName + ".detect mention characters");
                target = lsMemberHelper.lsGetMember(gGuild, numberorUser);
            }
            if(target==null){
                try{
                    int index=Integer.parseInt(numberorUser);
                    doUse4Self(index);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    sendEmbedError("Not a numberorUser!");
                }
            }else{
                if(target.getIdLong()==gMember.getIdLong()){
                    target=null;
                }
                menuPresents(0,target);
            }
        }
        private void doUse4Self(int index){
            String fName="[doUse4Self]";
            logger.info(fName+"index="+index);
            int size=entityUser.presentsSize();
            if(size<=0) {
                logger.warn(fName + ".you have no presents");
                sendEmbedError("You have no presents to use!");
                return;
            }
            logger.info(fName + ".index="+index+", size="+size);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            entityUserProfile4NaughtyPresents.Present present=entityUser.getPresent(index);
            doUse4Self(present);
        }
        private void doUse4Self(entityUserProfile4NaughtyPresents.Present present){
            String fName="[doUse4Self]";
            if(present==null) {
                logger.warn(fName + ".present cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            logger.info(fName + "present="+present.getRefKey());
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            int hp=present.getHp();
            logger.info(fName + ".hp="+hp);
            if(hp<0){
                logger.warn(fName+"hp is bellow 0!");
                sendEmbedError("[Present hp is bellow zero]");
                return;
            }
            entity4NaughtyPresents.Present presentGeneral=entityGeneral.getPresent(present.getRefKey());
            if(presentGeneral==null) {
                logger.warn(fName + ".presentGeneral cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            if(hp==0){
                logger.warn(fName+"hp is 0");
                sendEmbedError("Present '"+presentGeneral.getName()+"' is used up.");
                return;
            }
            JSONArray jsonArray;
            jsonArray=presentGeneral.getEmbedUseSelfJsons();
            if(jsonArray==null) {
                logger.warn(fName + ".jsonArray is null");
                sendEmbedError("No present action provided!");
                return;
            }
            if(jsonArray.isEmpty()) {
                logger.warn(fName + ".jsonArray is empty");
                sendEmbedError("No present action provided!");
                return;
            }
            logger.info(fName + ".jsonArray="+jsonArray.toString());
            int random=lsUsefullFunctions.getRandom(jsonArray.length());
            logger.info(fName + ".random=="+random+"/"+jsonArray.length());
            JSONObject jsonObject=jsonArray.getJSONObject(random);
            logger.info(fName + "jsonObject_bef="+jsonObject);
            String description=jsonObject.optString(llCommonKeys.EmbedBuildStructure.description,"(null)");
            description=stringReplacer1(description,entityUser.getMember().getAsMention());
            logger.info(fName + ".description_after="+description);
            jsonObject.put(llCommonKeys.EmbedBuildStructure.description,description);
            logger.info(fName + "jsonObject_aft="+jsonObject);
            embedBuilder=iNaughtyPresents.buildEmbed(jsonObject);
            if(!jsonObject.has(llCommonKeys.EmbedBuildStructure.color)){embedBuilder.setColor(llColorGreen);}
            if(embedBuilder==null) {
                logger.warn(fName + ".embedBuilder is null");
                sendEmbedError("Error building embed!");
                return;
            }
            embedBuilder.setTitle(gTitle);
            present.decHp();
            sendEmbed(embedBuilder);
            entityUser.saveProfile();
            logger.warn(fName+"used present "+presentGeneral.getKey()+" author["+gMember.getId()+", "+gTextChannel.getId()+", "+gGuild.getId()+"]");
        }
        private void doUse4Other(String number,String user){
            String fName="[doUse4Other]";
            logger.info(fName+"number="+number+", user="+user);
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            Member target;
            if (!mentions.isEmpty()) {
                logger.warn(fName + ".has mention list");
                if(mentions.size()>1){
                    logger.warn(fName + ".too many mentions, only 1 allowed");
                    sendEmbedError("Too many mentioned, only 1 can be mentioned!");
                    return;
                }
                target = lsMemberHelper.lsGetMember(gGuild, user);
            }else
            if((user.contains("<!@")||user.contains("<@"))&&user.contains(">")) {
                logger.info(fName + ".detect mention characters");
                target = lsMemberHelper.lsGetMember(gGuild, user);
            }else{
                logger.warn(fName + ".zero mentions char");
                sendEmbedError("Nobody mentioned!");
                return;
            }
            try{
                int index=Integer.parseInt(number);
                doUse4Other(index,target);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                sendEmbedError("Not a number!");
            }
        }
        private void doUse4Other(int index,Member target){
            String fName="[doUse4Other]";
            logger.info(fName+"index="+index);
            int size=entityUser.presentsSize();
            if(size<=0) {
                logger.warn(fName + ".you have no presents");
                sendEmbedError("You have no presents to use!");
                return;
            }
            logger.info(fName + ".index="+index+", size="+size);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            entityUserProfile4NaughtyPresents.Present present=entityUser.getPresent(index);
            doUse4Other(present,target);
        }
        private void doUse4Other(entityUserProfile4NaughtyPresents.Present present,Member target){
            String fName="[doUse4Other]";
            if(present==null) {
                logger.warn(fName + ".present cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            if (target == null) {
                logger.warn(fName + ".zero member mentions");
                sendEmbedError("Nobody mentioned!");
                return;
            } else if (target.getIdLong() == gUser.getIdLong()) {
                logger.warn(fName + ".target cant be the gUser");
                sendEmbedError("Can't self send gift!");
                return;
            }
            logger.info(fName + "present="+present.getRefKey());
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            int hp=present.getHp();
            logger.info(fName + ".hp="+hp);
            if(hp<0){
                logger.warn(fName+"hp is bellow 0!");
                sendEmbedError("[Present hp is bellow zero]");
                return;
            }
            entity4NaughtyPresents.Present presentGeneral=entityGeneral.getPresent(present.getRefKey());
            if(presentGeneral==null) {
                logger.warn(fName + ".presentGeneral cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            if(hp==0){
                logger.warn(fName+"hp is 0");
                sendEmbedError("Present '"+presentGeneral.getName()+"' is used up.");
                return;
            }
            JSONArray jsonArray=presentGeneral.getEmbedUseOtherJsons();
            if(jsonArray==null) {
                logger.warn(fName + ".jsonArray is null");
                sendEmbedError("No present action provided!");
                return;
            }
            if(jsonArray.isEmpty()) {
                logger.warn(fName + ".jsonArray is empty");
                sendEmbedError("No present action provided!");
                return;
            }
            logger.info(fName + ".jsonArray="+jsonArray.toString());
            int random=lsUsefullFunctions.getRandom(jsonArray.length());
            logger.info(fName + ".random=="+random+"/"+jsonArray.length());
            JSONObject jsonObject=jsonArray.getJSONObject(random);
            String description=jsonObject.optString(llCommonKeys.EmbedBuildStructure.description,"(null)");
            description=stringReplacer1(description,entityUser.getMember().getAsMention(),target.getAsMention());
            logger.info(fName + ".description_after="+description);
            jsonObject.put(llCommonKeys.EmbedBuildStructure.description,description);
            embedBuilder=iNaughtyPresents.buildEmbed(jsonObject);
            if(!jsonObject.has(llCommonKeys.EmbedBuildStructure.color)){embedBuilder.setColor(llColorGreen);}
            if(embedBuilder==null) {
                logger.warn(fName + ".embedBuilder is null");
                sendEmbedError("Error building embed!");
                return;
            }
            present.decHp();
            sendEmbed(embedBuilder);
            entityUser.saveProfile();
            logger.warn(fName+"used present "+presentGeneral.getKey()+" author["+gMember.getId()+", "+gTextChannel.getId()+", "+gGuild.getId()+"]");
        }
        private void doUse4Holder(String numberorUser){
            String fName="[doUse4Holder]";
            logger.info(fName+"numberorUser="+numberorUser);
            Member target=null;
            if((numberorUser.contains("<!@")||numberorUser.contains("<@"))&&numberorUser.contains(">")) {
                logger.info(fName + ".detect mention characters");
                target = lsMemberHelper.lsGetMember(gGuild, numberorUser);
            }
            if(target==null){
                try{
                    int index=Integer.parseInt(numberorUser);
                    doUse4Holder(index);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    sendEmbedError("Not a numberorUser!");
                }
            }else{
                if(target.getIdLong()==gMember.getIdLong()){
                    target=null;
                }
                menuPresents(0,target);
            }
        }
        private void doUse4Holder(int index){
            String fName="[doUse4Holder]";
            logger.info(fName+"index="+index);
            int size=entityUser.presentsSize();
            if(size<=0) {
                logger.warn(fName + ".you have no presents");
                sendEmbedError("You have no presents to use!");
                return;
            }
            logger.info(fName + ".index="+index+", size="+size);
            if(index==-1) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("No index provided!");
                return;
            }
            if(index<0) {
                logger.warn(fName + ".index can't be bellow 0");
                sendEmbedError("Index can't be bellow 0!");
                return;
            }
            if(index>=size){
                logger.warn(fName + ".index can't be equal or above");
                sendEmbedError("Index must be bellow "+(size-1)+"!");
                return;
            }
            entityUserProfile4NaughtyPresents.Present present=entityUser.getPresent(index);
            doUse4Holder(present);
        }
        private void doUse4Holder(entityUserProfile4NaughtyPresents.Present present){
            String fName="[doUse4Holder]";
            if(present==null) {
                logger.warn(fName + ".present cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            logger.info(fName + "present="+present.getRefKey());
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle).setColor(llColorRed_Chili);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                sendEmbedError("NSFW channel is required!");
                return;
            }
            int hp=present.getHp();
            logger.info(fName + ".hp="+hp);
            if(hp<0){
                logger.warn(fName+"hp is bellow 0!");
                sendEmbedError("[Present hp is bellow zero]");
                return;
            }
            entity4NaughtyPresents.Present presentGeneral=entityGeneral.getPresent(present.getRefKey());
            if(presentGeneral==null) {
                logger.warn(fName + ".presentGeneral cant be null");
                sendEmbedError("No present provided!");
                return;
            }
            if(hp==0){
                logger.warn(fName+"hp is 0");
                sendEmbedError("Present '"+presentGeneral.getName()+"' is used up.");
                return;
            }
            JSONArray jsonArray=presentGeneral.getEmbedUseOtherJsons();
            if(jsonArray==null) {
                logger.warn(fName + ".jsonArray is null");
                sendEmbedError("No present action provided!");
                return;
            }
            if(jsonArray.isEmpty()) {
                logger.warn(fName + ".jsonArray is empty");
                sendEmbedError("No present action provided!");
                return;
            }
            logger.info(fName + ".jsonArray="+jsonArray.toString());
            int random=lsUsefullFunctions.getRandom(jsonArray.length());
            logger.info(fName + ".random=="+random+"/"+jsonArray.length());
            JSONObject jsonObject=jsonArray.getJSONObject(random);
            logger.info(fName + "jsonObject_bef="+jsonObject);
            String description=jsonObject.optString(llCommonKeys.EmbedBuildStructure.description,"(null)");
            description=stringReplacer1(description,gMember.getAsMention(),entityUser.getMember().getAsMention());
            logger.info(fName + ".description_after="+description);
            jsonObject.put(llCommonKeys.EmbedBuildStructure.description,description);
            logger.info(fName + "jsonObject_aft="+jsonObject);
            embedBuilder=iNaughtyPresents.buildEmbed(jsonObject);
            if(!jsonObject.has(llCommonKeys.EmbedBuildStructure.color)){embedBuilder.setColor(llColorGreen);}
            if(embedBuilder==null) {
                logger.warn(fName + ".embedBuilder is null");
                sendEmbedError("Error building embed!");
                return;
            }
            embedBuilder.setTitle(gTitle);
            present.decHp();
            sendEmbed(embedBuilder);
            entityUser.saveProfile();
            logger.warn(fName+"used present "+presentGeneral.getKey()+" author["+gMember.getId()+", "+gTextChannel.getId()+", "+gGuild.getId()+"]");
        }
        private void resetUser(String user){
            String fName="[resetUser]";
            if(!lsMemberHelper.lsMemberIsBotOwner(gMember)){
                logger.warn(fName+"failed permission");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Denied!");
                return;
            }
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            if (!mentions.isEmpty()) {
                logger.warn(fName + ".has mention list");
                if(mentions.size()>1){
                    logger.warn(fName + ".too many mentions, only 1 allowed");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Too many mentioned, only 1 can be mentioned!");
                    return;
                }
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else
            if((user.contains("<!@")||user.contains("<@"))&&user.contains(">")) {
                logger.info(fName + ".detect mention characters");
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else{
                logger.warn(fName + ".zero mentions char");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Nobody mentioned!");
                return;
            }
            if (gTarget == null) {
                logger.warn(fName + ".zero member mentions");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Nobody mentioned!");
                return;
            } else if (gTarget.getIdLong() == gUser.getIdLong()) {
                logger.warn(fName + ".target cant be the gUser");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Cant use it like this on self!");
                return;
            }
            loadUserProfiles();
            if(!entityUser.reset()){
                logger.warn(fName+".Failed to reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to reset!");
            }else
            if(!entityUser.saveProfile()){
                logger.warn(fName+".Failed to save reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save reset!");
            }else{
                logger.info(fName+".reseted");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Resteted profile for "+gTarget.getAsMention()+" by "+gMember.getAsMention()+".", llColors.llColorGreen1);
            }
        }
        private void resetUser(){
            String fName="[resetUser]";
            if(!entityUser.reset()){
                logger.warn(fName+".Failed to reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to reset!");
            }else
            if(!entityUser.saveProfile()){
                logger.warn(fName+".Failed to save reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save reset!");
            }else{
                logger.info(fName+".reseted");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Resteted profile", llColors.llColorGreen1);
            }
        }
        private void undeoTimeWait(String user){
            String fName="[undeoTimeWait]";
            if(!lsMemberHelper.lsMemberIsBotOwner(gMember)){
                logger.warn(fName+"failed permission");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Denied!");
                return;
            }
            List<Member> mentions = gCommandEvent.getMessage().getMentionedMembers();
            if (!mentions.isEmpty()) {
                logger.warn(fName + ".has mention list");
                if(mentions.size()>1){
                    logger.warn(fName + ".too many mentions, only 1 allowed");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Too many mentioned, only 1 can be mentioned!");
                    return;
                }
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else
            if((user.contains("<!@")||user.contains("<@"))&&user.contains(">")) {
                logger.info(fName + ".detect mention characters");
                gTarget = lsMemberHelper.lsGetMember(gGuild, user);
            }else{
                logger.warn(fName + ".zero mentions char");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Nobody mentioned!");
                return;
            }
            if (gTarget == null) {
                logger.warn(fName + ".zero member mentions");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Nobody mentioned!");
                return;
            } else if (gTarget.getIdLong() == gUser.getIdLong()) {
                logger.warn(fName + ".target cant be the gUser");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Cant use it like this on self!");
                return;
            }
            loadUserProfiles();
            if(entityUser.setTimestampCall(0)==null){
                logger.warn(fName+".Failed to reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to reset!");
            }else
            if(!entityUser.saveProfile()){
                logger.warn(fName+".Failed to save reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save reset!");
            }else{
                logger.info(fName+".reseted");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Resteted profile for "+gTarget.getAsMention()+" by "+gMember.getAsMention()+".", llColors.llColorGreen1);
            }
        }
        private void undeoTimeWait(){
            String fName="[undeoTimeWait]";
            if(entityUser.setTimestampCall(0)==null){
                logger.warn(fName+".Failed to reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to reset!");
            }else
            if(!entityUser.saveProfile()){
                logger.warn(fName+".Failed to save reset");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save reset!");
            }else{
                logger.info(fName+".reseted");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Resteted profile", llColors.llColorGreen1);
            }
        }
        public void slash() throws Exception {
            String fName="[slash]";
            logger.info(fName);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            if(!isNSFW()){
                logger.warn(fName+"nsfw command but wrong channel>return");
                embedBuilder.setDescription("NSFW channel is required!");
                embedBuilder.setColor(llColorRed_Barn);
                gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                return;
            }
            loadMainProfiles();
            gInteractionHook=gSlashCommandEvent.deferReply().complete();
            String subcommand=gSlashCommandEvent.getSubcommandName();
            logger.info(fName+"subcommand="+subcommand);
            if(subcommand==null||subcommand.isBlank()){
                logger.warn(fName+"subcommand is null/blank");
                embedBuilder.setColor(llColorRed_Chili).setDescription("Invalid command");
                sendEmbed(embedBuilder);
                return;
            }
            List<OptionMapping> options=gSlashCommandEvent.getOptions();
            boolean providedIndex=false;long valueIndex=0;
            Member memberProvided=null;
            for(OptionMapping option:options){
                String name=option.getName();
                switch (name){
                    case "index":
                        valueIndex=option.getAsLong();
                        providedIndex=true;
                        break;
                    case "user":
                        memberProvided=option.getAsMember();
                        break;
                    default:

                }
            }
            switch (subcommand){
                case "menu":
                    loadUserProfiles();
                    embedBuilder.setColor(llColorGreen).setDescription("Opening menu, check DM");
                    sendEmbed(embedBuilder);
                    autoDeleteSlashMessage();
                    menuMain();
                    break;
                case "event":
                    if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                        embedBuilder.setColor(llColorRed_Chili).setDescription("Sorry! You can't force others to partake in events but can trade gifts.");
                        sendEmbed(embedBuilder);
                        return;
                    }
                    loadUserProfiles();
                    doEvent();
                    break;
                case "inventory":
                    if(memberProvided!=null||gMember.getIdLong()!=memberProvided.getIdLong()){
                        gTarget=memberProvided;
                    }
                    loadUserProfiles();
                    int index=0;
                    if(providedIndex){
                        int size=entityUser.presentsSize();
                        if(valueIndex<0){
                            logger.info(fName+"valueIndex is bellow 0");
                        }else if(valueIndex>=size){
                            logger.info(fName+"valueIndex is equal above="+valueIndex+":"+size);
                        }else{
                            logger.info(fName+"valueIndex is OK="+valueIndex+":"+size);
                            index= (int) valueIndex;
                        }

                    }
                    embedBuilder.setColor(llColorGreen).setDescription("Wonder what toy you got :)");
                    sendEmbed(embedBuilder);
                    autoDeleteSlashMessage();
                    menuPresents(index,null);
                    break;
                case "gift":
                    loadUserProfiles();
                    if(!providedIndex){
                        embedBuilder.setColor(llColorRed_Chili).setDescription("No index provided! Can't send gift!");
                        sendEmbed(embedBuilder);
                        return;
                    }
                    sendGift(memberProvided,(int)valueIndex);
                    break;
                case "use":
                    loadUserProfiles();
                        /*embedBuilder.setColor(llColorRed_Chili).setDescription("Cristmas is not here yet, you dont know what is what, yet.");
                        sendEmbed(embedBuilder);*/
                    if(memberProvided==null||memberProvided.getIdLong()==gMember.getIdLong()){
                        doUse4Self((int) valueIndex);
                    }else{
                        doUse4Other((int) valueIndex,memberProvided);
                    }
                    break;
                default:
                    logger.warn(fName+"subcommand is invalid");
                    embedBuilder.setColor(llColorRed_Chili).setDescription("Invalid command");
                    sendEmbed(embedBuilder);
            }
        }
        private void reload(){
            String fName="[doEvent]";
            if(!lsMemberHelper.lsMemberIsBotOwner(gMember)){
                logger.warn(fName+"failed permission");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Denied!");
                return;
            }
            if(!entityGeneral.getCache()){
                logger.info(fName+"mainjson not in cache -> get from file");
            }else{
                logger.info(fName+"mainjson is in cache _> still get from file");
            }
            if(!entityGeneral.readFile()){
                logger.warn(fName+"failed to read");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to read file!");
                return;
            }
            if(!entityGeneral.setCache()){
                logger.warn(fName+"failed to cache");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to set cache!");
                return;
            }
            logger.info(fName+"success 1");
            if(gGlobal.globalSettingsJson.has(llCommonKeys.keyMessageComponents)||!gGlobal.globalSettingsJson.isNull(llCommonKeys.keyMessageComponents)){
                logger.info(fName+"has: "+llCommonKeys.keyMessageComponents);
                JSONObject jsonObject=gGlobal.globalSettingsJson.optJSONObject(llCommonKeys.keyMessageComponents);
                if(jsonObject.has(iNaughtyPresents.gMainMenuFile)){
                    jsonObject.remove(iNaughtyPresents.gMainMenuFile);
                    logger.info(fName+"removed: "+iNaughtyPresents.gMainMenuFile);
                }
                if(jsonObject.has(iNaughtyPresents.gPresentsMenuFile)){
                    jsonObject.remove(iNaughtyPresents.gPresentsMenuFile);
                    logger.info(fName+"removed: "+iNaughtyPresents.gPresentsMenuFile);
                }
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle).setColor(llColorBlue1);
            embed.setDescription("Successfully read file");
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
    }
}
