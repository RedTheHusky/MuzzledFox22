package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class utilityEat extends Command implements  llGlobalHelper  {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="eat";
    public utilityEat(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Eat-Messages";
        this.help = "Eating messages";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityEat(lcGlobalHelper g, GuildMessageReactionAddEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;GuildMessageReactionAddEvent guildMessageReactionAddEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel; private Message gMessage;String gTitle="Eat Utility";

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }
        public runLocal(GuildMessageReactionAddEvent event) {
            String fName="runLocal";
            logger.info(".run build");
            guildMessageReactionAddEvent = event;
            gUser = guildMessageReactionAddEvent.getUser();gMember = guildMessageReactionAddEvent.getMember();
            gGuild = guildMessageReactionAddEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = guildMessageReactionAddEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= guildMessageReactionAddEvent.retrieveMessage().complete();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(fName);
            try {

                if(guildMessageReactionAddEvent!=null){
                    /*if(gUser.isBot()){
                        logger.warn(fName + ".reaction added by bot>ignore");
                        return;
                    }*/
					if(isValidBotMessageReactiong()){
                        if(!lsMemberHelper.lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MESSAGEMANAGE(gMember)){
                            logger.info(fName+"command author not owner and missing message manage permission");
                            return;
                        }
						User author=gMessage.getAuthor();
						try {
							logger.info(fName+"author="+author.getName()+"("+author.getId()+")");
							logger.info(fName+"author_isBot="+author.isBot());
						}catch (Exception e){
							logger.error(fName + ".exception=" + e);
							logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
						}
						if( gGuild.getSelfMember().getUser()==author){
							logger.info(fName+"reaction to selfbot message");
							selfbotMessageReactiong();
						}
						else{
							List<Webhook>webhooks=gTextChannel.retrieveWebhooks().complete();
							logger.info(fName+"webhooks.size="+webhooks.size());
							for(int i=0;i<webhooks.size();i++){
								try {
									Webhook webhook=webhooks.get(i);
									logger.info(fName+"webhooks["+i+"]:"+webhook.getId()+"|"+webhook.getName());
									logger.info(fName+"webhooks["+i+"].defaultUser"+webhook.getDefaultUser().getName()+"("+webhook.getDefaultUser().getId()+")");
									if(webhook.getId().equals(author.getId())){
										logger.info(fName+"webhooks["+i+"]:found webhook message post author");
										if(webhook.getOwner()!=null){
											logger.info(fName+"webhooks["+i+"].owner"+webhook.getOwner().getUser().getName()+"("+webhook.getOwner().getId()+")");
											if(gGuild.getSelfMember().getUser()==webhook.getOwner().getUser()){
												logger.info(fName+"reaction to selfbot webhook post");
												selfbotMessageReactiong();
											}
										}
										return;
									}
								}catch (Exception e){
									logger.error(fName + ".exception=" + e);
									logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
								}
							}
							webhooks=gGuild.retrieveWebhooks().complete();
							logger.info(fName+"webhooks.size="+webhooks.size());
							for(int i=0;i<webhooks.size();i++){
								try {
									Webhook webhook=webhooks.get(i);
									logger.info(fName+"webhooks["+i+"]:"+webhook.getId()+"|"+webhook.getName());
									logger.info(fName+"webhooks["+i+"].defaultUser"+webhook.getDefaultUser().getName()+"("+webhook.getDefaultUser().getId()+")");
									if(webhook.getId().equals(author.getId())){
										logger.info(fName+"webhooks["+i+"]:found webhook message post author");
										if(webhook.getOwner()!=null){
											logger.info(fName+"webhooks["+i+"].owner"+webhook.getOwner().getUser().getName()+"("+webhook.getOwner().getId()+")");
											if(gGuild.getSelfMember().getUser()==webhook.getOwner().getUser()){
												logger.info(fName+"reaction to selfbot webhook post");
												selfbotMessageReactiong();
											}
										}
										return;
									}
								}catch (Exception e){
									logger.error(fName + ".exception=" + e);
									logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
								}
							}
						}
					}
                }else{
                    try {
                        prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                        boolean isInvalidCommand = true;
                        if (gCommandEvent.getArgs().isEmpty()) {
                            logger.info(fName + ".Args=0");
                            help("main");
                            isInvalidCommand = false;
                        } else {
                            logger.info(fName + ".Args");
                            if(!lsMemberHelper.lsMemberHasPermission_MESSAGEMANAGE(gMember)){
                                logger.info(fName + ".denied");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied", llColors.llColorRed_Barn);
                                return;
                            }
                            String[] items = gCommandEvent.getArgs().split("\\s+");
                            if (items[0].equalsIgnoreCase("help")) {
                                if(items.length>=2){
                                    help(items[1]);
                                }else{
                                    help("main");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("all")) {
                                if(items.length>=2){
                                    eatMessages(items[1]);
                                }else{
                                    eatMessages("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("bots")) {
                                if(items.length>=2){
                                    eatMessages4Bots(items[1]);
                                }else{
                                    eatMessages4Bots("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("users")) {
                                if(items.length>=2){
                                    eatMessages4Users(items[1]);
                                }else{
                                    eatMessages4Users("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("emojis")) {
                                if(items.length>=2){
                                    eatMessages4Emojis(items[1]);
                                }else{
                                    eatMessages4Emojis("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("embeds")) {
                                if(items.length>=2){
                                    eatMessages4Embeds(items[1]);
                                }else{
                                    eatMessages4Embeds("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("files")) {
                                if(items.length>=2){
                                    eatMessages4Files(items[1]);
                                }else{
                                    eatMessages4Files("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("reactions")) {
                                if(items.length>=2){
                                    eatMessages4JustReactions(items[1]);
                                }else{
                                    eatMessages4JustReactions("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("images")) {
                                if(items.length>=2){
                                    eatMessages4Images(items[1]);
                                }else{
                                    eatMessages4Images("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("videos")) {
                                if(items.length>=2){
                                    eatMessages4Videos(items[1]);
                                }else{
                                    eatMessages4Videos("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("otherfiles")) {
                                if(items.length>=2){
                                    eatMessages4OtherFiles(items[1]);
                                }else{
                                    eatMessages4OtherFiles("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (items[0].equalsIgnoreCase("reactions+")) {
                                if(items.length>=2){
                                    eatMessages4FullReactions(items[1]);
                                }else{
                                    eatMessages4FullReactions("10");
                                }
                                isInvalidCommand = false;
                            }else
                            if (lsMemberHelper.lsIsMember(gGuild,items[0])) {
                                Member member=lsMemberHelper.lsGetMember(gGuild,items[0]);
                                if(items.length>=2){
                                    if (items[1].equalsIgnoreCase("all")) {
                                        if(items.length>=3){
                                            eatMessages(member,items[2]);
                                        }else{
                                            eatMessages(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("emojis")) {
                                        if(items.length>=3){
                                            eatMessages4Emojis(member,items[2]);
                                        }else{
                                            eatMessages4Emojis(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("embeds")) {
                                        if(items.length>=3){
                                            eatMessages4Embeds(member,items[2]);
                                        }else{
                                            eatMessages4Embeds(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("files")) {
                                        if(items.length>=3){
                                            eatMessages4Files(member,items[2]);
                                        }else{
                                            eatMessages4Files(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("reactions")) {
                                        if(items.length>=3){
                                            eatMessages4JustReactions(member,items[2]);
                                        }else{
                                            eatMessages4JustReactions(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("images")) {
                                        if(items.length>=3){
                                            eatMessages4Images(member,items[2]);
                                        }else{
                                            eatMessages4Images(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("videos")) {
                                        if(items.length>=3){
                                            eatMessages4Videos(member,items[2]);
                                        }else{
                                            eatMessages4Videos(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("otherfiles")) {
                                        if(items.length>=3){
                                            eatMessages4OtherFiles(member,items[2]);
                                        }else{
                                            eatMessages4OtherFiles(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("reactions+")) {
                                        if(items.length>=3){
                                            eatMessages4FullReactions(member,items[2]);
                                        }else{
                                            eatMessages4FullReactions(member,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else if(items.length==2){
                                        eatMessages(member,items[2]);isInvalidCommand = false;
                                    }
                                }else{
                                    eatMessages(member,"10");isInvalidCommand = false;
                                }
                            }
                            if (lsUsefullFunctions.isStringJustLong(items[0])) {
                                String id=items[0];
                                if(items.length>=2){
                                    if (items[1].equalsIgnoreCase("all")) {
                                        if(items.length>=3){
                                            eatMessages(id,items[2]);
                                        }else{
                                            eatMessages(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("emojis")) {
                                        if(items.length>=3){
                                            eatMessages4Emojis(id,items[2]);
                                        }else{
                                            eatMessages4Emojis(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("embeds")) {
                                        if(items.length>=3){
                                            eatMessages4Embeds(id,items[2]);
                                        }else{
                                            eatMessages4Embeds(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("files")) {
                                        if(items.length>=3){
                                            eatMessages4Files(id,items[2]);
                                        }else{
                                            eatMessages4Files(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("reactions")) {
                                        if(items.length>=3){
                                            eatMessages4JustReactions(id,items[2]);
                                        }else{
                                            eatMessages4JustReactions(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("images")) {
                                        if(items.length>=3){
                                            eatMessages4Images(id,items[2]);
                                        }else{
                                            eatMessages4Images(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("videos")) {
                                        if(items.length>=3){
                                            eatMessages4Videos(id,items[2]);
                                        }else{
                                            eatMessages4Videos(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("otherfiles")) {
                                        if(items.length>=3){
                                            eatMessages4OtherFiles(id,items[2]);
                                        }else{
                                            eatMessages4OtherFiles(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else
                                    if (items[1].equalsIgnoreCase("reactions+")) {
                                        if(items.length>=3){
                                            eatMessages4FullReactions(id,items[2]);
                                        }else{
                                            eatMessages4FullReactions(id,"10");
                                        }
                                        isInvalidCommand = false;
                                    }else if(items.length==2){
                                        eatMessages(id,items[2]);isInvalidCommand = false;
                                    }
                                }else{
                                    eatMessages(id,"10");isInvalidCommand = false;
                                }
                            }
                        }
                        logger.info(fName + ".deleting op message");
                        lsMessageHelper.lsMessageDelete(gCommandEvent);
                        if (isInvalidCommand) {
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(),llColors.llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String prefix2="";
        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = prefix2 + commandPrefix+" ";
            String desc = " ";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
//"all""bots""users""emojis""embeds""files""reactions""images""videos""otherfiles""reactions+"
            embed.addField("ATTRIBUTES","`<number>` refers to the number the messages it should delete, max is 100, default is not specified is 10\n`[@member]` refers to a mentioned member of the server, if used it will only delete messages from this author",false);
            embed.addField("ALL","`"+quickSummonWithSpace+"[@member or userID] all <number>` deletes all type of messages, if member is used then deletes it for that member. ",false);
            embed.addField("Users","`"+quickSummonWithSpace+"users <number>` deletes messages that are from a users",false);
            embed.addField("Bots","`"+quickSummonWithSpace+"bots <number>` deletes messages that are from bots",false);
            embed.addField("Emojis","`"+quickSummonWithSpace+"emojis [@member or userID] <number>` deletes messages that contains emojis",false);
            embed.addField("Embeds","`"+quickSummonWithSpace+"embeds [@member or userID] <number>` deletes messages that contains embeds",false);
            embed.addField("Reactions","`"+quickSummonWithSpace+"reactions [@member or userID] <number>`, clears all reactions from the messages it contains reactions"+"\n`"+quickSummonWithSpace+" reactions+ [@member] <number>`, deletes messages that contains reactions",false);
            embed.addField("Files","`"+quickSummonWithSpace+"files [@member or userID] <number>` deletes messages that contains attachments",false);
            embed.addField("Images","`"+quickSummonWithSpace+"images [@member or userID] <number>` deletes messages that contains image attachments",false);
            embed.addField("Videos","`"+quickSummonWithSpace+"videos [@member or userID] <number>` deletes messages that contains video attachments",false);
            embed.addField("Otherfiles","`"+quickSummonWithSpace+"otherfiles [@member or userID] <number>` deletes messages that contains attachments excluding images and videos. If the message contains non-image or video, it wil delete it even if it contains image&video.",false);
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        int MAX_ATTEMPTS=10,MAX_RETRIEVE_SIZE=100;
        String messageHeader="Yummm. I have eaten ";
		 private boolean isValidBotMessageReactiong() {
			 String fName = "isValidBotMessageReactiong";
            try {
                MessageReaction.ReactionEmote reactionEmote=guildMessageReactionAddEvent.getReactionEmote();
                if(reactionEmote.isEmote()){
                    logger.info(fName+"is a custom emoji>ignore");
                    return false;
                }
                if(!reactionEmote.isEmoji()){
                    logger.info(fName+"not a unicode emoji>ignore");
                    return false;
                }
                String emoji=reactionEmote.getEmoji();
                logger.info(fName + ".emoji=" +emoji);
                if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                    logger.info(fName+"delete message");
                    return true;
                }else
                if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSponge))){
                    logger.info(fName+"Clear Reactions");
                     return true;
                }
				 return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
				 return false;
            }
		 }
        private void selfbotMessageReactiong() {
            String fName = "selfbotMessageReactiong";
            try {
                MessageReaction.ReactionEmote reactionEmote=guildMessageReactionAddEvent.getReactionEmote();
                if(reactionEmote.isEmote()){
                    logger.info(fName+"is a custom emoji>ignore");
                    return;
                }
                if(!reactionEmote.isEmoji()){
                    logger.info(fName+"not a unicode emoji>ignore");
                    return;
                }
                String emoji=reactionEmote.getEmoji();
                logger.info(fName + ".emoji=" +emoji);
                if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                    logger.info(fName+"delete message");
                    lsMessageHelper.lsMessageDelete(gMessage);
                }else
                if(emoji.equalsIgnoreCase(  gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSponge))){
                    logger.info(fName+"Clear Reactions");
                    lsMessageHelper.lsMessageClearReactionsQueue(gMessage);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void eatMessages(String number) {
            String fName = "eatMessages";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        messages.addAll(retrieved);
                        amount -= numToRetrieve;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Users(String number) {
            String fName = "eatMessages4Users";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isBot=message.getAuthor().isBot();
                            logger.info(fName + "."+message.getId()+".isBot=" + isBot);
                            if(!isBot){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" users messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Bots(String number) {
            String fName = "eatMessages4Bots";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isBot=message.getAuthor().isBot();
                            logger.info(fName + "."+message.getId()+".isBot=" + isBot);
                            if(isBot){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" bots messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Emojis(String number) {
            String fName = "eatMessages4Emojis";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isEmpty=message.getEmotes().isEmpty();
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" emoji messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Embeds(String number) {
            String fName = "eatMessages4Embeds";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isEmpty=message.getEmbeds().isEmpty();
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" embeds messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4JustReactions(String number) {
            String fName = "eatMessages4JustReactions";
            logger.info(fName + ".number=" + number);
            try {
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0,count=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isEmpty=message.getEmbeds().isEmpty();
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                message.clearReactions().complete();
                                amount--;count++;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" reactions from messages.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4FullReactions(String number) {
            String fName = "eatMessages4FullReactions";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isEmpty=message.getEmbeds().isEmpty();
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with reactions.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Files(String number) {
            String fName = "eatMessages4Files";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            boolean isEmpty=message.getAttachments().isEmpty();
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with attachments.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Images(String number) {
            String fName = "eatMessages4Images";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                            for(Message.Attachment attachment:attachments){
                                if(attachment.isImage()){ isEmpty=false;break;}
                            }
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with images.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Videos(String number) {
            String fName = "eatMessages4Videos";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                            for(Message.Attachment attachment:attachments){
                                if(attachment.isVideo()){ isEmpty=false;break;}
                            }
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with videos.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4OtherFiles(String number) {
            String fName = "eatMessages4OtherFiles";
            logger.info(fName + ".number=" + number);
            try {
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                            for(Message.Attachment attachment:attachments){
                                if(!attachment.isVideo()&&!attachment.isImage()){
                                    isEmpty=false; break;
                                }
                            }
                            logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                            if(!isEmpty){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with other files.",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }

        private void eatMessages(Member member,String number) {
            String fName = "eatMessages";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Emojis(Member member,String number) {
            String fName = "eatMessages4Emojis";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmotes().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" emoji messages from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Embeds(Member member,String number) {
            String fName = "eatMessages4Embeds";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" emoji messages from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4JustReactions(Member member,String number) {
            String fName = "eatMessages4JustReactions";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0,count=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    message.clearReactions().complete();
                                    amount--;count++;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" reactions from messages from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4FullReactions(Member member,String number) {
            String fName = "eatMessages4FullReactions";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with reactions from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Files(Member member,String number) {
            String fName = "eatMessages4Files";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getAttachments().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }

                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with attachments from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(), llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Images(Member member,String number) {
            String fName = "eatMessages4Images";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(attachment.isImage()){ isEmpty=false;break;}
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with images from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Videos(Member member,String number) {
            String fName = "eatMessages4Videos";
            logger.info(fName + ".number=" + number);
            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(attachment.isVideo()){ isEmpty=false;break;}
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with videos from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4OtherFiles(Member member,String number) {
            String fName = "eatMessages4OtherFiles";
            logger.info(fName + ".number=" + number);

            try {
                if(member==null){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No member provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".member=" + member.getId());
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor()==member.getUser()){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(!attachment.isVideo()&&!attachment.isImage()){
                                        isEmpty=false; break;
                                    }
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }

                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with other files from "+member.getAsMention()+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }

        private void eatMessages(String id,String number) {
            String fName = "eatMessages";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                messages.add(message);amount--;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Emojis(String id,String number) {
            String fName = "eatMessages4Emojis";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmotes().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" emoji messages from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Embeds(String id,String number) {
            String fName = "eatMessages4Embeds";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" emoji messages from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4JustReactions(String id,String number) {
            String fName = "eatMessages4JustReactions";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0,count=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    message.clearReactions().complete();
                                    amount--;count++;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" reactions from messages from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4FullReactions(String id,String number) {
            String fName = "eatMessages4FullReactions";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getEmbeds().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with reactions from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Files(String id,String number) {
            String fName = "eatMessages4Files";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                boolean isEmpty=message.getAttachments().isEmpty();
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }

                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with attachments from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(), llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Images(String id,String number) {
            String fName = "eatMessages4Images";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(attachment.isImage()){ isEmpty=false;break;}
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with images from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4Videos(String id,String number) {
            String fName = "eatMessages4Videos";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(attachment.isVideo()){ isEmpty=false;break;}
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with videos from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
        private void eatMessages4OtherFiles(String id,String number) {
            String fName = "eatMessages4OtherFiles";
            logger.info(fName + ".number=" + number);
            try {
                if(id==null||id.isBlank()){ lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"No id provided!",llColors.llColorRed_Barn);return; }
                logger.info(fName + ".id="+id);
                List<Message> messages = new ArrayList<>();
                MessageHistory history=gTextChannel.getHistory();
                int amount=Integer.parseInt(number);int attempts=0;
                logger.info(fName + ".amount=" + amount);
                try {
                    while (amount > 0&&attempts<MAX_ATTEMPTS) {
                        attempts++;
                        int numToRetrieve = amount;

                        if (amount > MAX_RETRIEVE_SIZE) {
                            numToRetrieve = MAX_RETRIEVE_SIZE;
                        }

                        List<Message> retrieved = history.retrievePast(numToRetrieve).complete();
                        if (retrieved == null) {
                            break;
                        }
                        for(Message message:retrieved){
                            if(message.getAuthor().getId().equalsIgnoreCase(id)){
                                logger.info(fName + "."+message.getId()+".is same author");
                                List<Message.Attachment>attachments=message.getAttachments();  boolean isEmpty=true;
                                for(Message.Attachment attachment:attachments){
                                    if(!attachment.isVideo()&&!attachment.isImage()){
                                        isEmpty=false; break;
                                    }
                                }
                                logger.info(fName + "."+message.getId()+".isEmpty=" + isEmpty);
                                if(!isEmpty){
                                    messages.add(message);amount--;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                int count=messages.size();
                gTextChannel.deleteMessages(messages).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,messageHeader+count+" messages with other files from "+id+".",llColors.llColorRed_Barn);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Exception:"+e.toString(),llColors.llColorRed_Barn);
            }
        }
    }
}
