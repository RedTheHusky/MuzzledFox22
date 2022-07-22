package test;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import events.rawhandling.*;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class test2 extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  llCommonKeys {
    String cName="[test1]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter; String gTitle="test2";
    Logger logger = Logger.getLogger(getClass());
    public test2(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "test-test2";
        this.help = "test2";
        this.aliases = new String[]{"test2"};
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
                }else
                if(items[0].equalsIgnoreCase("dmmenu1")){
                    dmMenu1("");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("dmmenu2")){
                    dmMenu2("");isInvalidCommand=false;
                }

            }
            logger.info(cName+fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
            }
        }
        
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"test1 ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("`channelpermission`");
            llSendMessage(gUser,embed);
        }
        private void dmMenu1(String str){
            String fName="[dmMenu1]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle("Test Menu ");
                if(str!=null&&!str.isBlank()){
                    embed.setDescription("Test Menu\n return value="+str);
                }else{
                    embed.setDescription("Test Menu");
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))) {
                                    dmMenu1("0");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))) {
                                    dmMenu1("1");;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu1("2");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu1("3");
                                }
                                else{
                                    dmMenu1("invalid");
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }
        private void dmMenu2(String str){
            String fName="[dmMenu2]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle("Test Menu ");
                if(str!=null&&!str.isBlank()){
                    embed.setDescription("Test Menu\n return value="+str);
                }else{
                    embed.setDescription("Test Menu");
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(RawGatewayEvent.class,
                        e->(lsRawGeneralHelper.isType_MessageReactionAdd(e)&&lsRawGeneralHelper.getMessageIdLong(e)==message.getIdLong()&&lsRawGeneralHelper.getUserIdLong(e)==gMember.getIdLong()),
                        e -> {
                            try {
                                lsRawGeneralHelper.RawEmoji rawEmoji=lsRawGeneralHelper.getEmoji(e);
                                String name=rawEmoji.getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))) {
                                    dmMenu2("0");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))) {
                                    dmMenu2("1");;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu2("2");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu2("3");
                                }
                                else{
                                    dmMenu2("invalid");
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }
        private void dmMenu3(String str){
            String fName="[dmMenu2]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle("Test Menu ");
                if(str!=null&&!str.isBlank()){
                    embed.setDescription("Test Menu\n return value="+str);
                }else{
                    embed.setDescription("Test Menu");
                }
                lcMyMessageJsonBuilder myMessageBuilder=new lcMyMessageJsonBuilder();
                myMessageBuilder.setEmbed(embed);

                lcMessageBuildComponent component=new lcMessageBuildComponent();
                Message message=myMessageBuilder.post(gTextChannel);
                gWaiter.waitForEvent(RawGatewayEvent.class,
                        e->(lsRawGeneralHelper.isType_MessageReactionAdd(e)&&lsRawGeneralHelper.getMessageIdLong(e)==message.getIdLong()&&lsRawGeneralHelper.getUserIdLong(e)==gMember.getIdLong()),
                        e -> {
                            try {
                                lsRawGeneralHelper.RawEmoji rawEmoji=lsRawGeneralHelper.getEmoji(e);
                                String name=rawEmoji.getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))) {
                                    dmMenu2("0");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))) {
                                    dmMenu2("1");;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu2("2");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))) {
                                    dmMenu2("3");
                                }
                                else{
                                    dmMenu2("invalid");
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }

    
  //runLocal  
    }
}
