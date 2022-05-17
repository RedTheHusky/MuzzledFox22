package interaction.button;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.apache.log4j.Logger;
import util.utilityStickers;

import java.util.Arrays;

public class buttonControler extends Command implements llMessageHelper, llGlobalHelper {
    String cName="[buttonControler]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gTitle="buttonControler", gCommand="buttonControler";
    public buttonControler(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        this.name = gTitle;
        this.help = "slashRD";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;
        this.hidden=true;
    }
    public buttonControler(lcGlobalHelper g, ButtonClickEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;
        ButtonClickEvent gButtonClick;
        Message gMessage;
        public runLocal(CommandEvent ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(ButtonClickEvent ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gButtonClick = ev;
            gUser = gButtonClick.getUser();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gMember=gButtonClick.getMember();
                gGuild = gButtonClick.getGuild();
                gTextChannel =gButtonClick.getTextChannel();
                if(gGuild!=null)logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
                if(gTextChannel!=null)logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(fName);
            try {
                if(gButtonClick!=null){
                    logger.info(fName+"ButtonClickEvent jda@");
                    select();
                }
                else
                    {
                    logger.info(fName+"basic@");
                    boolean isInvalidCommand = true;
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");isInvalidCommand=false;
                    }
                    logger.info(fName+".deleting op message");
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
        String keyName="name",keyValue="value";
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"emote ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            llSendMessage(gUser,embed);
        }
        private void select(){
            String fName="[select]";
            String id=gButtonClick.getButton().getId();
            if(id==null)id="";
            logger.info(fName+"id="+id);
            if(id.startsWith("stickerPack")){
                logger.info(fName+"utilityStickers");
                new utilityStickers(gGlobal,gButtonClick);
            }
        }

    }
}
