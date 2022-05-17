package nsfw;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ll.llMessageHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class lewdInteraction extends Command implements llMessageHelper, llGlobalHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[lewdInteraction]";
    lcGlobalHelper gGlobal;String gTitle="Lewd-interaction",gCommand="!";
    public lewdInteraction(lcGlobalHelper g){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=g;
        this.name = gTitle;
        this.help = "Lewd interaction";
        this.aliases = new String[]{gCommand,"lewdinteraction"};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName = "[execute]";
        logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent; 
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel;
        Member gTarget;String gRunCommand;
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gEvent = ev;
            
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"lewdInteraction",gGlobal);
                gBasicFeatureControl.initProfile();
                if (llDebug) {
                    logger.info(fName + ".global debug true");
                    return;
                }
                if(!isNSFW()){
                    blocked();return;
                }
                String[] items;
                boolean isInvalidCommand = true;
                if (gEvent.getArgs().isEmpty()) {
                    logger.info(fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    gTarget=getTarget(gEvent.getMessage());
                    gRunCommand =getCommand(items);
                    if(gTarget!=null){
                        logger.info(fName + ".target:" + gTarget.getId() + "|" + gTarget.getUser().getName());
                    }
                    if(gRunCommand !=null){
                        logger.info(fName + ".command:" + gRunCommand);
                    }
                    if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
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
                    if (items[0].equalsIgnoreCase("chastitycages")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        chastitycages(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("youneeddiaper")) {
                        youneeddiaper(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("gags")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        gags(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("diapercheck")) {
                        diapercheck(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("punish")) {
                        punish(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("putinstraitjacket")) {
                        putinstraitjacket(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("chastitypunish")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        chastitypunish(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("petcage")) {
                        putinpetcage(false);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmchastitycages")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        chastitycages(true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmyouneeddiaper")) {
                        youneeddiaper( true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmgags")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        gags(true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmdiapercheck")) {
                        diapercheck( true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmpunish")) {
                        punish(true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmputinstraitjacket")) {
                        putinstraitjacket(true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmchastitypunish")) {
                        if(!isNSFW()){
                            blocked();return;
                        }
                        chastitypunish(true);
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("dmpetcage")) {
                        putinpetcage(true);
                        isInvalidCommand = false;
                    }
                    if(isInvalidCommand){
                        if (items[0].equalsIgnoreCase("ketchup")) {
                            laxKetchup(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("ketchuppunish")) {
                            if(!isNSFW()){
                                blocked();return;
                            }
                            laxPunishKetchup(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("hotdog")) {
                            laxHotDog(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("lube")) {
                            if(!isNSFW()){
                                blocked();return;
                            }
                            laxLube(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("zap")) {
                            laxPunishZap(false);
                            isInvalidCommand = false;
                        }
                        if (items[0].equalsIgnoreCase("like")) {
                            laxLike(false);
                            isInvalidCommand = false;
                        }
                    }
                }
                logger.info(fName + ".deleting op message");
                llMessageDelete(gEvent);
                if (isInvalidCommand) {
                    llSendQuickEmbedMessage(gEvent.getAuthor(), "Lewd Interaction", "You provided an incorrect command!", llColorRed);
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
            llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private void help(String command) {
            String fName = "[help]";
            logger.info(fName);

            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            logger.info(fName + "command=" + command);
            String desc;
            String quickSummonWithSpace = llPrefixStr + "!" + " ";
            desc = "Allows sending lewd interaction";
            desc += "\nThe <@target> is mentioning somebody who you targeting and is required!\nThe <index> represent numeric value and its optional.";
            desc += "\nFor guilds channel use one of this commands:";
            if(lsCustomGuilds.lsIsCustomGuild4Lax(gGuild)||llBotToken==llLaxBotToken){
                desc += "\n`" + quickSummonWithSpace + "ketchup <@target>` target is optional";
                desc += "\n`" + quickSummonWithSpace + "ketchuppunish <@target>` target is optional";
                desc += "\n`" + quickSummonWithSpace + "hotdog <@target>` target is optional";
                desc += "\n`" + quickSummonWithSpace + "lube <@target>` target is optional";
                desc += "\n`" + quickSummonWithSpace + "zap <@target>` target is optional";
                desc += "\n`" + quickSummonWithSpace + "like <@target> <index 0-1>` target is optional";
            }
            desc += "\n`" + quickSummonWithSpace + "chastitycages <@target>` ";
            desc += "\n`" + quickSummonWithSpace + "youneeddiaper <@target>`";
            desc += "\n`" + quickSummonWithSpace + "gags <@target> <index 0-5>`";
            desc += "\n`" + quickSummonWithSpace + "diapercheck <@target> <index 0-6>`";
            desc += "\n`" + quickSummonWithSpace + "punish <@target> <index 0-6>`";
            desc += "\n`" + quickSummonWithSpace + "putinstraitjacket <@target> <index 0-3>`";
            desc += "\n`" + quickSummonWithSpace + "chastitypunish <@target> <index 0-2>`" ;
            desc += "\nTo send it to DM use this commands:";
            desc += "\n`" + quickSummonWithSpace + "dmchastitycages <@target>`";
            desc += "\n`" + quickSummonWithSpace + "dmyouneeddiaper <@target>`";
            desc += "\n`" + quickSummonWithSpace + "dmgags <@target> <index 0-5>`";
            desc += "\n`" + quickSummonWithSpace + "dmdiapercheck <@target> <index 0-6>`";
            desc += "\n`" + quickSummonWithSpace + "dmpunish <@target> <index 0-6>`";
            desc += "\n`" + quickSummonWithSpace + "dmputinstraitjacket <@target> <index 0-3>`";
            desc += "\n`" + quickSummonWithSpace + "dmchastitypunish <@target> <index 0-2>`";
            embed.addField("Commands",desc,false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private String imgInvalidIndex = "https://cdn.discordapp.com/attachments/587945871593832488/588055398142181381/maxresdefault_1.jpg";
        private String imgYouNeedChastity = "https://cdn.discordapp.com/attachments/587945871593832488/588227033369411584/CrimsonredKayNL30.png";
        private void chastitycages(Boolean isSent2DM) {
            String fName = "[chastitycages]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = gUser.getAsMention() + " prepares the chastity cage for " + gTarget.getAsMention() + ".\nTime to lock up poor " + gTarget.getAsMention() + " for a long time.";
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                embed.setImage(imgYouNeedChastity);
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, embed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String imgYouNeedDiaper="https://cdn.discordapp.com/attachments/587945871593832488/588228953261735946/Crinkles4.png";
        private void youneeddiaper(Boolean isSent2DM) {
            String fName = "[youneeddiaper]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = gUser.getAsMention() + " thinks that " + gTarget.getAsMention() + " needs a diaper change. Time tor chaange.";
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                embed.setImage(imgYouNeedDiaper);
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String imgGag0="https://cdn.discordapp.com/attachments/587945871593832488/588249821803184138/COL_CEC4_5be1da173caba_by_FavoriteStickerBot98.png";
        String imgGag1="https://cdn.discordapp.com/attachments/587945871593832488/588249829814304768/COL_CEC4_5be1da173caba_by_FavoriteStickerBot111.png";
        String imgGag2="https://cdn.discordapp.com/attachments/587945871593832488/588250408145780736/COL_huff_5b1d4237840ec_by_FavoriteStickerBot38.png";
        String imgGag3="https://cdn.discordapp.com/attachments/587945871593832488/588250409433432074/COL_huff_5b1d4237840ec_by_FavoriteStickerBot40.png";
        String imgGag4="https://cdn.discordapp.com/attachments/587945871593832488/588250415057993769/COL_huff_5b1d4237840ec_by_FavoriteStickerBot43.png";
        private void gags(Boolean isSent2DM) {
            String fName = "[gags]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-4";
                String img ="";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(5);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }

                switch (i) {
                    case 0:
                        desc = gUser.getAsMention() + " gently secures a gag around " + gTarget.getAsMention() + " jaw, silencing them to moans and whimpers.";
                        img = imgGag0;
                        break;
                    case 1:
                        desc = gUser.getAsMention() + " secures a gag around " + gTarget.getAsMention() + "'s jaw, warning them to be quiet.";
                        img = imgGag1;
                        break;
                    case 2:
                        desc = gUser.getAsMention() + " pushes a fabric over " + gTarget.getAsMention() + " face to silence them.";
                        img = imgGag2;
                        break;
                    case 3:
                        desc = gUser.getAsMention() + " muzzles " + gTarget.getAsMention() + " jaw, silencing them to moans and whimpers.";
                        img = imgGag3;
                        break;
                    case 4:
                        desc = gUser.getAsMention() + " secures a gag around " + gTarget.getAsMention() + " jaw, warning them to be quiet as they start securing the blindfold over their eyes.";
                        img = imgGag4;
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void diapercheck(Boolean isSent2DM) {
            String fName = "[diapercheck]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                boolean onSelf = false;
                if (gTarget == null) {
                    onSelf = true;
                } else {
                    if (gTarget == gUser) {
                        onSelf = true;
                    }
                }
                logger.info(fName + "onSelf=" + onSelf);

                String desc = "Invalid index. Index is between 0-6";
                String img ="";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(7);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                if (onSelf) {
                    switch (i) {
                        case 0:
                            desc = gUser.getAsMention() + " checks their own diaper. Its dry like the desert and fresh like a pine forest.";
                            break;
                        case 1:
                            desc = gUser.getAsMention() + " checks their own diaper. They are a bit wet between their legs, not visible from the outside.";
                            break;
                        case 2:
                            desc = gUser.getAsMention() + " checks their own diaper. They are visible wet between their legs. Luckily the diaper is holding it without any issues.";
                            break;
                        case 3:
                            desc = gUser.getAsMention() + " checks their own diaper. Their diaper is soggy, no way to hide that they peed themselves heavily. Luckily the diaper is holding it without any issues.";
                            break;
                        case 4:
                            desc = gUser.getAsMention() + " checks their own diaper. The diaper is full, it can't hold any more. Its advised to change it.";
                            break;
                        case 5:
                            desc = gUser.getAsMention() + " checks their own diaper as they feel their paw getting wet. The diaper is so full that it starts leaking, messing their fur down their leg. Its advised to change it.";
                            break;
                        case 6:
                            desc = gUser.getAsMention() + " checks their own diaper as they feel their paw getting wet. The diaper got so full that a puddle has formed bellow their legs.";
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (i) {
                        case 0:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper. Its dry like the desert and fresh like a pine forest.";
                            break;
                        case 1:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper. " + gTarget.getAsMention() + " are a bit wet between their legs, not visible from the outside.";
                            break;
                        case 2:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper. " + gTarget.getAsMention() + " are visible wet between their legs. Luckily the diaper is holding it without any issues.";
                            break;
                        case 3:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper. " + gTarget.getAsMention() + " diaper is soggy, no way they can hide that fact. Luckily the diaper is holding it without any issues.";
                            break;
                        case 4:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper. " + gTarget.getAsMention() + " diaper is full, it can't hold any more. Its advised to change it.";
                            break;
                        case 5:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper as they feel their paw getting wet. " + gTarget.getAsMention() + " is so full that it starts leaking, messing their fur down their leg. Its advised to change it.";
                            break;
                        case 6:
                            desc = gUser.getAsMention() + " checks " + gTarget.getAsMention() + " diaper as they feel their paw getting wet. " + gTarget.getAsMention() + " diaper got so full that a puddle has formed bellow their legs.";
                            break;
                        default:
                            break;
                    }
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        String imgPunish0="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560155848893-petzapped.png";
        String imgPunish1="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560156604548-petcage.png";
        String imgPunish2="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560160013466-redpunished.png";
        String imgPunish3="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560623730871-buttslap.png";
        String imgPunish5="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560623425585-wsmarking2.png";
        private void punish(Boolean isSent2DM) {
            String fName = "[punish]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-6";
                String img ="";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(7);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                switch (i) {
                    case 0:
                        desc = gTarget.getAsMention() + " gets zapped by " + gUser.getAsMention() + " for being a bad puppy!";
                        img = imgPunish0;
                        break;
                    case 1:
                        desc = gTarget.getAsMention() + " gets put in a pet cage by " + gUser.getAsMention() + " for being a bad puppy!";
                        img = imgPunish1;
                        break;
                    case 2:
                        desc = gTarget.getAsMention() + " gets put in the mischievous pet corner for spankings by " + gUser.getAsMention() + " for being a bad sub!";
                        img = imgPunish2;
                        break;
                    case 3:
                        desc = gTarget.getAsMention() + " has their butt slapped by " + gUser.getAsMention() + " for being a bad sub!";
                        img = imgPunish3;
                        break;
                    case 4:
                        desc = gTarget.getAsMention() + " has their paws put in long mitts chained to their collar and jaw muzzled by " + gUser.getAsMention() + " for being a bad pet!";
                        break;
                    case 5:
                        desc = gUser.getAsMention() + " pushes a o-ring between " + gTarget.getAsMention() + "'s jaw, as they place a blindfold over their eyes before pissing into their open jaw.";
                        img = imgPunish5;
                        break;
                    case 6:
                        desc = gUser.getAsMention() + " ties up " + gTarget.getAsMention() + " and uses them as a drink service table. Hopefully " + gTarget.getAsMention() + " wont spill any drinks.";
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        String imgPutInSj2="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1558772460157-pupjacket.png";
        String imgPutInSj3="https://s3.us-east-2.amazonaws.com/stickers-for-discord/482743226848116757-1561652645911-vasurp.png";
        private void putinstraitjacket(Boolean isSent2DM) {
            String fName = "[putinstraitjacket]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-3";
                String img ="";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(4);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                switch (i) {
                    case 0:
                        desc = gTarget.getAsMention() + " gets put in a straitjacket with buckles secured by " + gUser.getAsMention() + ".";
                        break;
                    case 1:
                        desc = gTarget.getAsMention() + " gets put in a straitjacket with buckles secured by " + gUser.getAsMention() + ". Additionally a padding is secured to ensure the jacket doesn't need to be removed for bathroom breaks.";
                        break;
                    case 2:
                        desc = gTarget.getAsMention() + " gets put in a straitjacket with buckles secured by " + gUser.getAsMention() + ". A hood covering their face, not just blinding them but also silencing them.";
                        img = imgPutInSj2;
                        break;
                    case 3:
                        desc = gTarget.getAsMention() + " gets put in a straitjacket with buckles secured by " + gUser.getAsMention() + ". Without a warning, something is pushed up their butt.";
                        img = imgPutInSj3;
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        String imgChastityPunish0="https://d.facdn.net/art/ruaidri/1408501975/1408501975.ruaidri_x014.jpg";
        String imgChastityPunish1="https://d.facdn.net/art/spinal22/1543263288/1543263288.spinal22_chastity_trouble_res.jpg";
        String imgChastityPunish2="https://cdn.discordapp.com/attachments/587851831011442701/596769897820717072/a_1481382849824s_permanent_chastity_musuko.jpg";
        private void chastitypunish(Boolean isSent2DM) {
            String fName = "[chastitypunish]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-2";
                String img ="";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(3);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                switch (i) {
                    case 0:
                        desc = "It looks like " + gTarget.getAsMention() + " has been disobedient and an e-stim was attached to their chastity device by" + gUser.getAsMention() + " for a few hours of shock treatment as punishment.";
                        img = imgChastityPunish0;
                        break;
                    case 1:
                        desc = "It looks like " + gTarget.getAsMention() + " has been disobedient and an magic wand is placed on top of their chastity device. It seams " + gUser.getAsMention() + " going to edge them for few hours, however they will not get any orgasm during that time even after it ends.";
                        img = imgChastityPunish1;
                        break;
                    case 2:
                        desc = gUser.getAsMention() + " secures a padded lock on " + gTarget.getAsMention() + ", they have been very naughty";
                        img = imgChastityPunish2;
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        String imgPutInCage0="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560156604548-petcage.png";
        String imgPutInCage1="https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1560235106353-petcage2.png";
        private void putinpetcage(Boolean isSent2DM) {
            String fName = "[putinpetcage]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-1";
                String img = "";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(3);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                switch (i) {
                    case 0:
                        desc = "It looks like " + gTarget.getAsMention() + " has been placed in a cage for pets by" + gUser.getAsMention() + ".";
                        img = imgPutInCage0;
                        break;
                    case 1:
                        desc = "It looks like " + gTarget.getAsMention() + " has been placed in a cage for pets by" + gUser.getAsMention() + ".";
                        img = imgPutInCage1;
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void requestInteraction(MessageEmbed messageEmbed, String command) {
            String fName = "[requestInteraction]";
            User target = gTarget.getUser();
            if (gTarget == null) {
                logger.info(fName + ".Target is required!");
                return;
            }
            logger.info(fName + ".target:" + target.getId() + "|" + target.getName());
            logger.info(fName + "command=" + command);
            if (command.equalsIgnoreCase("main")) {
                llSendQuickEmbedMessage(target, "DM Interaction Request", gUser.getAsMention() + "wants to send an nsfw interaction via DM.\nDo you allow it?\nType:'yes' or 'no'", llColorPurple1);
                llSendQuickEmbedMessage(gUser, "DM Interaction Request", "You sent a nsfw interaction request to " + target.getAsMention() + " DM.", llColorPurple1);
            }
            if (command.equalsIgnoreCase("incorrect")) {
                llSendQuickEmbedMessage(target, "DM Interaction Request", gUser.getAsMention() + "wants to send an nsfw interaction via DM.\nDo you allow it?\nType:'yes' or 'no'", llColorPurple1);
            }
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(target),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            String fWName = "[requestInteractionWaiter]";
                            logger.info(fWName + "response:");
                            if (e.getMessage().getContentRaw().equalsIgnoreCase("yes")) {
                                llSendQuickEmbedMessage(gUser, "DM Interaction Accepted", target.getAsMention() + " accepted your nsfw interaction.", llColorGreen1);
                                lsMessageHelper.lsSendMessage(target, messageEmbed);
                            } else if (e.getMessage().getContentRaw().equalsIgnoreCase("no")) {
                                llSendQuickEmbedMessage(gUser, "DM Interaction Rejected", target.getAsMention() + " rejected your nsfw interaction.", llColorRed);
                            } else {
                                requestInteraction(messageEmbed, "incorrect");
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        }
                    },
                    // if the user takes more than a minute, time out
                    5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, "DM Interaction Request", "Timeout", llColorRed));
            logger.info(fName + ".waiter created");
        }

        private String getCommand(String[] items) {
            String fName = "[getCommand]";
            logger.info(fName + "items.length="+items.length);
            logger.info(fName + "items="+ Arrays.toString(items));
            if (items.length == 2 && !items[1].contains("<")) {
                logger.info(fName + "return:item1a="+items[1]);
                return items[1];
            }
            if (items.length == 3 && !items[1].contains("<")) {
                logger.info(fName + "return:item1b="+items[1]);
                return items[1];
            }
            if (items.length == 3 && !items[2].contains("<")) {
                logger.info(fName + "return:item2="+items[2]);
                return items[2];
            }
            logger.info(fName + "return:null");
            return null;
        }

        private Member getTarget(Message message) {
            String fName = "[getTarget]";
            List<Member> mentions = message.getMentionedMembers();
            if (!mentions.isEmpty()) {
                Member m=mentions.get(0);
                User u= m.getUser();
                logger.info(fName + "mention:" +u.getId()+"|"+u.getName());
                return m;
            }
            logger.info(fName + "mention:null");
            return null;
        }

        String urlLaxKetchup="https://cdn.discordapp.com/attachments/698511311843229777/733011835132837938/Ketchup.png";
        private void laxKetchup(Boolean isSent2DM) {
            String fName = "[laxKetchup]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                String desc;
                String img = urlLaxKetchup;
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc=gUser.getAsMention()+" grabs a bottle of ketchup. Yummmy, its ketchup!";
                }else{
                    if(isSent2DM){
                        desc=gUser.getAsMention()+" grabs a bottle of ketchup and gives it to you. Is it for food or "+gUser.getAsMention()+" has other ideas with the ketchup?";
                    }else{
                        desc=gUser.getAsMention()+" grabs a bottle of ketchup and gives it to "+gTarget.getAsMention()+". Is it for food or "+gUser.getAsMention()+" has other ideas with the ketchup?";
                    }
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorRed_Imperial);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String urlLaxHotDog="https://cdn.discordapp.com/attachments/698511311843229777/733011828329939014/Layer_20.png";
        private void laxHotDog(Boolean isSent2DM) {
            String fName = "[laxHotDog]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                String desc;
                String img = urlLaxHotDog;
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc=gUser.getAsMention()+" grabs a hot dog and take a bite from it. Ketchup starts dripping out of it as they start eating it, obviously full of ketchup.";
                }else{
                    if(isSent2DM){
                        desc=gUser.getAsMention()+" starts eating a hot dog in your face. Ketchup dripping as they chewing it.";
                    }else{
                        desc=gUser.getAsMention()+" starts eating a hot dog in "+gTarget.getAsMention()+"'s face. Ketchup dripping as they chewing it.";
                    }
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorRed_Imperial);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String urlLaxLube="https://cdn.discordapp.com/attachments/698511311843229777/733011863037804544/s-l300.png";
        private void laxLube(Boolean isSent2DM) {
            String fName = "[laxLube]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                String desc;
                String img = urlLaxLube;
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc=gUser.getAsMention()+" grabs a bottle of lube. Naughty pup must have some naughty plans with it.";
                }else{
                    if(isSent2DM){
                        desc=gUser.getAsMention()+" grabs a bottle of lube and gives it to you. "+gUser.getAsMention()+" must have naughty plans involving you. Better get ready!";
                    }else{
                        desc=gUser.getAsMention()+" grabs a bottle of lube and gives it to "+gTarget.getAsMention()+". "+gUser.getAsMention()+" must have naughty plans involving them. Better get ready!";
                    }
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorRed_Imperial);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String urlLaxZap="https://cdn.discordapp.com/attachments/698511311843229777/733011849599254608/zap_leather.png";
        private void laxPunishZap(Boolean isSent2DM) {
            String fName = "[laxPunishZap]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                String desc;
                String img = urlLaxZap;
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc=gUser.getAsMention()+" is shocked by their shock collar as they scream in their gag. The blindfold is making it hard for them to know when they shocked.";
                }else{
                    if(isSent2DM){
                        desc=gUser.getAsMention()+" shocks you with the shock collar your wearing. "+gUser.getAsMention()+" ensured your gagged so you cant scream stop and blindfolded so you don't know when they press the button.";
                    }else{
                        desc=gUser.getAsMention()+" shocks "+gTarget.getAsMention()+" with the shock collar they wearing. "+gUser.getAsMention()+" ensured that "+gTarget.getAsMention()+" is gagged so they cant scream stop and blindfolded so they don't know when the button is pressed.";
                    }
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorRed_Imperial);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String urlLaxKetchupPunishment="https://cdn.discordapp.com/attachments/698511311843229777/733011832490688632/MESSY.png";
        private void laxPunishKetchup(Boolean isSent2DM) {
            String fName = "[laxPunishKetchup]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                String desc;
                String img = urlLaxKetchupPunishment;
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc=gUser.getAsMention()+" is punished hard by gagging and covering their body in ketchup.";
                }else{
                    if(isSent2DM){
                        desc=gUser.getAsMention()+" punishes you hard by gagging and covering you body in ketchup.";
                    }else{
                        desc=gUser.getAsMention()+" punishes "+gTarget.getAsMention()+" hard by gagging and covering "+gTarget.getAsMention()+" body in ketchup.";
                    }
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorRed_Imperial);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String urlLaxLike="https://cdn.discordapp.com/attachments/698511311843229777/733011853533249695/3_shaded_pacifier.png", urlLaxLike2="https://cdn.discordapp.com/attachments/698511311843229777/733011851310268538/3_shaded_pacifier_pink.png";
        private void laxLike(Boolean isSent2DM) {
            String fName = "[laxLike]";
            logger.info(fName);
            try {
                logger.info(fName + "isSent2DM=" + isSent2DM);
                if (gTarget == null) {
                    logger.info(fName + ".Target is required!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "Target is required!");
                    return;
                }
                if (gTarget == gUser) {
                    logger.info(fName + ".Target is author!");
                    llSendMessageWithDelete(gGlobal,gTextChannel, "You can't perform the action on yourself!");
                    return;
                }

                String desc = "Invalid index. Index is between 0-1";
                String img = "";
                int i;
                if (gRunCommand == null || gRunCommand.isEmpty()) {
                    i = new Random().nextInt(3);
                } else {
                    i = Integer.parseInt(gRunCommand);
                }
                if (gTarget == null||gTarget == gUser) {
                    isSent2DM=false;
                    desc = "It looks like " + gUser.getAsMention() + " is loving his situation.";
                }else{
                    if(isSent2DM){
                        desc = "It looks like " + gUser.getAsMention() + " is trying to give you some loving boops to the nose.";
                    }else{
                        desc = "It looks like " + gUser.getAsMention() + " is trying to give "+gTarget.getAsMention()+" some loving boops to the nose.";
                    }
                }

                switch (i) {
                    case 0:
                        img = urlLaxLike;
                        break;
                    case 1:
                        img = urlLaxLike2;
                        break;
                    default:
                        break;
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                embed.setDescription(desc);
                if(!img.isEmpty()){
                    embed.setImage(img);
                }
                MessageEmbed messageEmbed = embed.build();
                if (isSent2DM) {
                    requestInteraction(messageEmbed, "main");
                } else {
                    llSendMessageWithDelete(gGlobal,gTextChannel, messageEmbed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+ gCommand +" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+ gCommand +" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+ gCommand +" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+ gCommand +" server blockroles :four:/list|add|rem|set|clear`",false);
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
    }//runLocal}
}
