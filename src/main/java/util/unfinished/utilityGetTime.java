package util.unfinished;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class utilityGetTime extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[basic]";
    lcGlobalHelper gGlobal;
    String gTitle="Basic";
    String commandPrefix="gettime";
    public utilityGetTime(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "Utility-GetTime";
        this.help = "get time";
        this.aliases = new String[]{"gettime"};
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
        CommandEvent gCommandEvent;String cName = "[runLocal]";
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel;private Message gMessage;
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

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                String[] items;
                boolean isInvalidCommand = true;
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(fName + ".Args");
                    items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if (items.length>=2&&items[0].equalsIgnoreCase("help")) {
                        help(items[1]);
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("test1")) {
                        testDisplay1();isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("test2")) {
                        testDisplay2();isInvalidCommand = false;
                    }

                }
                logger.info(cName+fName+".deleting op message");
                llMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(cName+".run ended");
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String desc="";
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColorBlue1);
            embed.addField("Index","\n`" + quickSummonWithSpace + "<timezone>",false);
            llSendMessage(gUser,embed);
        }
        private void testDisplay1(){
            String fName = "[testDisplay1]";
            logger.info(fName);
            if (!llMemberIsManager(gMember)) {
                llSendQuickEmbedMessage(gUser, gTitle, "Managers only allowed to use this command!", llColorRed);
                logger.warn(fName+"denied");return;
            }
            try {
                List<Date>list=new ArrayList<>();
                Calendar today = Calendar.getInstance();
                TimeZone timeZone1 = TimeZone.getTimeZone("Asia/Kolkata");
                TimeZone timeZone2 = TimeZone.getTimeZone("America/New_York");
                list.add(today.getTime());
                today.setTimeZone(timeZone1);list.add(today.getTime());
                today.setTimeZone(timeZone2);list.add(today.getTime());
                String desc="";
                /*for(Date date: list){
                    date.toString()
                }*/
                desc=list.toString();
                llSendQuickEmbedMessage(gTextChannel,gTitle,desc,llColorBlue1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }

        }
        private void testDisplay2(){
            String fName = "[testDisplay2]";
            logger.info(fName);
            if (!llMemberIsManager(gMember)) {
                llSendQuickEmbedMessage(gUser, gTitle, "Managers only allowed to use this command!", llColorRed);
                logger.warn(fName+"denied");return;
            }
            try {
                String desc="Time:";
                String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                String dateInString = "22-01-2015 10:15:55 AM";
                Date date = formatter.parse(dateInString);
                TimeZone tz = TimeZone.getDefault();

                // From TimeZone Asia/Singapore
                /*System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
                System.out.println("TimeZone : " + tz);
                System.out.println("Date (Singapore) : " + formatter.format(date));*/
                desc+="\n["+tz.getDisplayName()+"]"+formatter.format(date);


                // To TimeZone America/New_York
                SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
                TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
                sdfAmerica.setTimeZone(tzInAmerica);

                String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
                Date dateInAmerica = formatter.parse(sDateInAmerica); // Create a new Date object

                /*System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - " + tzInAmerica.getDisplayName());
                System.out.println("TimeZone : " + tzInAmerica);
                System.out.println("Date (New York) (String) : " + sDateInAmerica);
                System.out.println("Date (New York) (Object) : " + formatter.format(dateInAmerica));*/
                desc+="\n["+tzInAmerica.getDisplayName()+"]"+formatter.format(dateInAmerica);
                llSendQuickEmbedMessage(gTextChannel,gTitle,desc,llColorBlue1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColorRed);
            }

        }

    }
}
