package interaction.usercommand;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionMessage;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lcGlobalHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.rdRestraints;

import java.util.Arrays;

public class messagecommandControler extends Command implements llMessageHelper, llGlobalHelper {
    String cName="[messagecommandControler]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gTitle="messagecommandControler", gCommand="messagecommandControler";
    public messagecommandControler(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = gTitle;
        this.help = "messagecommandControler";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;
        this.hidden=true;
    }
    public messagecommandControler(lcGlobalHelper g, lcApplicationInteractionReceive.lMessageCommand event){
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
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;
        lcApplicationInteractionReceive.lMessageCommand gMessageCommand;
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
        public runLocal(lcApplicationInteractionReceive.lMessageCommand ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            try {
                if(ev==null)throw  new Exception("Event is null");
                gMessageCommand =ev;
                gUser = gMessageCommand.getUser();
                logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
                if(gMessageCommand.isFromGuild()){
                    gGuild = gMessageCommand.getGuild();
                    gTextChannel = gMessageCommand.getTextChannel();
                    gMember= gMessageCommand.getMember();
                    logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                    logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
                }else{
                    logger.warn(cName + fName + "not from guild");
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            try {
                {
                    if(gMessageCommand !=null){
                        logger.info(cName+fName+"gUserCommand@");
                        logger.info(cName+fName+"name="+ gMessageCommand.getName());
                        switch (gMessageCommand.getName()){
                            case "test_ul": case "test_ug":
                                lcApplicationInteractionReceive.lInteractionResponse lresponse0,lresponse1;
                                lcApplicationInteractionMessage lmessage0,lmessage1;
                                lresponse0= gMessageCommand.respondWithMessage("test_ul/ug 0",true);
                                lmessage0=lresponse0.send();
                                Thread.sleep(5000);
                                lmessage0.getBuilder().setContent("test_ul/ug 0 edited");lmessage0.edit();
                                logger.warn("edited");
                                Thread.sleep(5000);
                                logger.warn("send second");
                                lresponse1= gMessageCommand.respondWithMessage("test_ul/ug 2",true);
                                lmessage1=lresponse1.send();
                                Thread.sleep(5000);
                                lmessage1.getBuilder().setContent("test_ul/ug 2 edited");lmessage1.edit();
                                logger.warn("edited");
                            case "gag_status": case "gag status": case "gag":

                            break;
                        }
                        
                    }else{
                        logger.info(cName+fName+"basic@");
                        boolean isInvalidCommand = true;
                        if(gEvent.getArgs().isEmpty()){
                            logger.info(cName+fName+".Args=0");
                            help("main");isInvalidCommand=false;
                        }
                        logger.info(cName+fName+".deleting op message");
                        llMessageDelete(gEvent);
                        if(isInvalidCommand){
                            llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                        }
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
            String quickSummonWithSpace=llPrefixStr+"emote ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            llSendMessage(gUser,embed);
        }




    
  //runLocal  
    }
}
