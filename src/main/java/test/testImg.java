package test;


import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import events.rawhandling.lsRawGeneralHelper;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class testImg extends Command implements llMessageHelper, llMemberHelper, llCommonKeys,llGlobalHelper {
    String cName="[testAspose]";
    //https://products.aspose.com/imaging/java/
    //Good test but wont buy it
    lcGlobalHelper gGlobal; String gTitle="testAspose";
    Logger logger = Logger.getLogger(getClass());
    public testImg(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = "test-testAspose";
        this.help = "testAspose";
        this.aliases = new String[]{"testAspose","!"};
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
                if(items[0].equalsIgnoreCase("save1")){
                    saveImageInDifferentFormats();isInvalidCommand=false;
                }

            }
            logger.info(cName+fName+".deleting op message");
            //llMessageDelete(gEvent);
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
            embed.setDescription("");
            llSendMessage(gUser,embed);
        }
        private void saveImageInDifferentFormats(){
            String fName="[Save image in different formats]";
            logger.info(fName);
            try{
                String dir="resources/test/img/";

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }


        //runLocal
    }
}
