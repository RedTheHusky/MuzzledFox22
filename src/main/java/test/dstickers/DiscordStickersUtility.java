package test.dstickers;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.colors.llColors_Red;
import models.ll.llCommonKeys;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static models.ll.colors.llColors.*;
import static models.llGlobalHelper.llPrefixStr;

public class DiscordStickersUtility extends Command implements   llCommonKeys {
    String cName="[DiscordStickersUtility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String gCommand="dsticker",gTitle="ded";
    public DiscordStickersUtility(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "DiscordStickersUtility";
        this.help = "test1";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;
        this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(cName+fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;
        public runLocal(CommandEvent ev) {
            logger.info(".run build");
            gEvent = ev;
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            try {
                boolean isInvalidCommand = true;
                gUser = gEvent.getAuthor();gMember=gEvent.getMember();
                gGuild = gEvent.getGuild();
                gTextChannel =gEvent.getTextChannel();
                logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
                logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    String []items = gEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")){
                        help( "main");isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("get4message")){
                        switch (items.length){
                            case 4: getSticker4Message(items[1],items[2],items[3]);break;
                            case 3: getSticker4Message("",items[1],items[2]);break;
                            case 2: getSticker4Message("",gTextChannel.getId(),items[1]);break;
                            default:getSticker4Message("","","");
                        }

                        isInvalidCommand=false;
                    }

                }
                //logger.info(cName+fName+".deleting op message");
                //lsMessageHelper.lsMessageDelete(gEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!", llColors_Red.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }

        }
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"test1 ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("`channelpermission`");
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        public void getSticker4Message(String guildId,String channelID,String messageID) {
            String fName="[getSticker4Message]";
            try {
                Message message=null; TextChannel textChannel;Guild guild;PrivateChannel privateChannel;
                logger.info(fName+"guildId="+guildId+", channelID="+channelID+", messageID="+messageID);
                if(messageID==null||messageID.isBlank()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No message id provided!");
                    return;
                }

                if(channelID.equalsIgnoreCase(gTextChannel.getId())){
                    message=lsMessageHelper.lsGetMessageById(gTextChannel,messageID);
                }else
                if(guildId==null||guildId.isBlank()){
                    textChannel=lsMessageHelper.lsGetTextChannelById(gGuild,channelID);
                    if(textChannel==null){
                        privateChannel=lsMessageHelper.lsGetPrivateChannelById(gGlobal.getJDAList(),channelID);
                        if(privateChannel==null){
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No such channel with id `"+channelID+"` found!");
                            return;
                        }
                        message=lsMessageHelper.lsGetMessageById(privateChannel,messageID);
                    }else{
                        message=lsMessageHelper.lsGetMessageById(textChannel,messageID);
                    }
                }else{
                    guild=gGlobal.getGuild(guildId);
                    if(guild==null){
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No such guild with id `"+guildId+"` found!");
                        return;
                    }
                    textChannel=lsMessageHelper.lsGetTextChannelById(guild,channelID);
                    if(textChannel==null){
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No such channel with id `"+channelID+"` found!");
                        return;
                    }
                    message=lsMessageHelper.lsGetMessageById(textChannel,messageID);
                }


                if(message==null){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"No such message with id `"+messageID+"` found!");
                    return;
                }
                List<MessageSticker> stickers=message.getStickers();
                MessageSticker sticker=stickers.get(0);

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }

        }


  //runLocal  
    }
}
