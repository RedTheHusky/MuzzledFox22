package social.SFW;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import social.lcSocialization;

import java.util.List;

public class boop extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass()); 
    lcGlobalHelper gGlobal;
    String KeyTag ="boop", gTitle="Boop";
    public boop(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle+" Social";
        this.help = "Booping others";
        this.aliases = new String[]{KeyTag};
        this.guildOnly = true;this.category= llCommandCategory_SocialSFW;
        this.hidden=true;
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
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        User gUser;Member gMember;Guild gGuild;TextChannel gTextChannel;
        lcSocialization socialization;
        public runLocal(CommandEvent ev) {
            logger.info(cName + ".run build");
            gEvent = ev;
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            String[] items;
            boolean isInvalidCommand = true;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if(gEvent.getArgs().isEmpty()){
                logger.info(fName+".Args=0");noMention();
            }else {
                logger.info(cName + fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                logger.info(cName + fName + ".items.size=" + items.length);
                logger.info(cName + fName + ".items[0]=" + items[0]);
                if(items[0].equalsIgnoreCase("help")){
                   help("main");
                }
                if(isInvalidCommand){
                    List<Member>mebers=gEvent.getMessage().getMentionedMembers();
                    Member target;
                    if(!mebers.isEmpty()){
                        logger.info(fName+".is mentioned in message");
                        target=mebers.get(0);
                    }else{
                        target=llGetMember(gGuild,items[0]);
                    }
                    if(target!=null){
                        logger.info(fName+".has mention");
                        socialization=new lcSocialization(gTextChannel,gMember,target);
                        socialization.sendResponse(KeyTag);
                    }else{
                        noMention();
                    }
                }

            }
            isInvalidCommand=false;
            logger.info(fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }
            logger.info(".run ended");
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String desc="`"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorBlue1);
        }
        private void noMention() {
            String fName = "[noMention]";
            logger.info(cName + fName);
            String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }

    }
}
