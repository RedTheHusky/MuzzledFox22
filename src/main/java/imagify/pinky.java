package imagify;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
/*import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;*/

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;


import static org.opencv.imgcodecs.Imgcodecs.imread;*/

public class pinky extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[pinky]";
    lcGlobalHelper gGlobal;
    String gTitle="Basic";

    public pinky(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "pinky_Dev";
        this.help = "pinky";
        this.aliases = new String[]{"pinky"};
        this.guildOnly = true;this.category= llCommandCategory_BuildAlpha;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(cName+fName);
        if(llDebug){
            logger.info(cName+fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        CommandEvent gEvent;String cName = "[runLocal]";
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel;
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
                logger.info(cName+fName+".Args=0");
                help("main"); isInvalidCommand=false;
                //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
            }else {
                logger.info(cName + fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                logger.info(cName + fName + ".items.size=" + items.length);
                logger.info(cName + fName + ".items[0]=" + items[0]);

            }
            logger.info(cName+fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }
            logger.info(cName+".run ended");
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String desc="N/a";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }
        private void doImage(){
            String fName = "[doImage]";
            logger.info(cName + fName);
            try{
                String str="";
                URL url = new URL(str);
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                //httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                InputStream is=httpcon.getInputStream();
                /*File file=is;
                Icon icon=Icon.from(is);
                Mat image = imread(filename);
                if (image != null) {
                    GaussianBlur(image, image, new Size(3, 3), 0);
                    imwrite(filename, image);
                }*/
            }catch (Exception e){
                logger.error(cName+fName+"exception="+e);
            }
        }


    }
}
