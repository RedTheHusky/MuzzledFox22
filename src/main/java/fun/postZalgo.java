package fun;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.la.aBasicCommandHandler;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class postZalgo extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    //https://stackoverflow.com/questions/26927419/zalgo-text-in-java
    Logger logger = Logger.getLogger(getClass()); String cName="[Zalgoe]";String gCommand ="zalgo", gTitle="Zalgo";
    lcGlobalHelper gGlobal=lsGlobalHelper.sGetGlobal();
    public postZalgo(){
        String fName="[constructor]";
        logger.info(cName+fName);
        this.name = gTitle;
        this.help = "Post text wit hzalgo effect";
        this.aliases = new String[]{gCommand,"text2zalgo"};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
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
    protected class runLocal extends aBasicCommandHandler implements Runnable {
        String cName = "[runLocal]";
        public runLocal(CommandEvent event) {
            String fName="runLocal";
            logger.info(".run build"); setCommandHandlerValues(logger,event);
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                buildBasicFeatureControl(logger,"zalgo",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                    }
                    else {ask4Text( );isInvalidCommand=false;}
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
                        return;
                    }
                    if(ifItsAnAccessControlCommand()){
                        logger.info(fName+"its an AccessControlCommand");
                        return;
                    }
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                        return;
                    }
                    if(items[0].equalsIgnoreCase("input")){
                        ask4Text( );isInvalidCommand=false;

                    }
                    else{
                        postMessage(gEvent.getArgs());isInvalidCommand=false;
                    }
                }
                deleteCommandPostAndCheckIfInvalid();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
            logger.info(cName+".run ended");
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String quickSummonWithSpace=llPrefixStr+ gCommand +" ";
            EmbedBuilder embed=new EmbedBuilder();
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Helper");
            embed.addField(iRestraints.strSupportTitle,iRestraints.strSupport,false);
            embed.addField("Options","Add following words to text for options.\nPosition forbid: `!up`, `!down`, `!mid`\nSize: `!mini`, `!normal`, `!maxi`",false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private void ask4Text( ) {
            String fName = "[ask4Text]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post with zalgo effect or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.equalsIgnoreCase("!cancel")){
                                llMessageDelete(message);return;
                            }
                            postMessage(content);
                            llMessageDelete(message);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },3, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });
        }
        private void inpputHere( ) {
            String fName = "[askHere]";
            logger.info(fName);
            try {
               String text=gEvent.getArgs();
                logger.info(fName+"text="+text);

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void postMessage(String source) {
            String fName = "[postMessage]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            logger.info(fName+"source="+source);
            String zargoText=string2Zalgo(source);
            logger.info(fName+"zargoText="+zargoText);
            try {
                logger.info(fName + ".sendwebhook");
                ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,zargoText);
                if(readonlyMessage==null){
                    logger.info(fName + ".send embed message");
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setDescription(zargoText);
                    embedBuilder.setColor(llColorPurple2);
                    embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                    if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                        logger.info(fName + ".send normal message");
                        lsMessageHelper.lsSendMessageResponse(gTextChannel,zargoText);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName + ".send normal message");
                lsMessageHelper.lsSendMessage(gTextChannel,zargoText);
            }
        }
        private  final char[] zalgo_up =
                { '\u030d', /*     Ì?     */'\u030e', /*     ÌŽ     */'\u0304', /*     Ì„     */'\u0305', /*     Ì…     */
                        '\u033f', /*     Ì¿     */'\u0311', /*     Ì‘     */'\u0306', /*     Ì†     */'\u0310', /*     Ì?     */
                        '\u0352', /*     Í’     */'\u0357', /*     Í—     */'\u0351', /*     Í‘     */'\u0307', /*     Ì‡     */
                        '\u0308', /*     Ìˆ     */'\u030a', /*     ÌŠ     */'\u0342', /*     Í‚     */'\u0343', /*     Ì“     */
                        '\u0344', /*     ÌˆÌ?     */'\u034a', /*     ÍŠ     */'\u034b', /*     Í‹     */'\u034c', /*     ÍŒ     */
                        '\u0303', /*     Ìƒ     */'\u0302', /*     Ì‚     */'\u030c', /*     ÌŒ     */'\u0350', /*     Í?     */
                        '\u0300', /*     Ì€     */'\u0301', /*     Ì?     */'\u030b', /*     Ì‹     */'\u030f', /*     Ì?     */
                        '\u0312', /*     Ì’     */'\u0313', /*     Ì“     */'\u0314', /*     Ì”     */'\u033d', /*     Ì½     */
                        '\u0309', /*     Ì‰     */'\u0363', /*     Í£     */'\u0364', /*     Í¤     */'\u0365', /*     Í¥     */
                        '\u0366', /*     Í¦     */'\u0367', /*     Í§     */'\u0368', /*     Í¨     */'\u0369', /*     Í©     */
                        '\u036a', /*     Íª     */'\u036b', /*     Í«     */'\u036c', /*     Í¬     */'\u036d', /*     Í­     */
                        '\u036e', /*     Í®     */'\u036f', /*     Í¯     */'\u033e', /*     Ì¾     */'\u035b', /*     Í›     */
                        '\u0346', /*     Í†     */'\u031a' /*     Ìš     */
                } ;

        private  final char[] zalgo_down =
                { '\u0316', /*     Ì–     */'\u0317', /*     Ì—     */'\u0318', /*     Ì˜     */'\u0319', /*     Ì™     */
                        '\u031c', /*     Ìœ     */'\u031d', /*     Ì?     */'\u031e', /*     Ìž     */'\u031f', /*     ÌŸ     */
                        '\u0320', /*     Ì      */'\u0324', /*     Ì¤     */'\u0325', /*     Ì¥     */'\u0326', /*     Ì¦     */
                        '\u0329', /*     Ì©     */'\u032a', /*     Ìª     */'\u032b', /*     Ì«     */'\u032c', /*     Ì¬     */
                        '\u032d', /*     Ì­     */'\u032e', /*     Ì®     */'\u032f', /*     Ì¯     */'\u0330', /*     Ì°     */
                        '\u0331', /*     Ì±     */'\u0332', /*     Ì²     */'\u0333', /*     Ì³     */'\u0339', /*     Ì¹     */
                        '\u033a', /*     Ìº     */'\u033b', /*     Ì»     */'\u033c', /*     Ì¼     */'\u0345', /*     Í…     */
                        '\u0347', /*     Í‡     */'\u0348', /*     Íˆ     */'\u0349', /*     Í‰     */'\u034d', /*     Í?     */
                        '\u034e', /*     ÍŽ     */'\u0353', /*     Í“     */'\u0354', /*     Í”     */'\u0355', /*     Í•     */
                        '\u0356', /*     Í–     */'\u0359', /*     Í™     */'\u035a', /*     Íš     */'\u0323' /*     Ì£     */
                } ;

        //those always stay in the middle
        private  final char[] zalgo_mid =
                { '\u0315', /*     Ì•     */'\u031b', /*     Ì›     */'\u0340', /*     Ì€     */'\u0341', /*     Ì?     */
                        '\u0358', /*     Í˜     */'\u0321', /*     Ì¡     */'\u0322', /*     Ì¢     */'\u0327', /*     Ì§     */
                        '\u0328', /*     Ì¨     */'\u0334', /*     Ì´     */'\u0335', /*     Ìµ     */'\u0336', /*     Ì¶     */
                        '\u034f', /*     Í?     */'\u035c', /*     Íœ     */'\u035d', /*     Í?     */'\u035e', /*     Íž     */
                        '\u035f', /*     ÍŸ     */'\u0360', /*     Í      */'\u0362', /*     Í¢     */'\u0338', /*     Ì¸     */
                        '\u0337', /*     Ì·     */'\u0361', /*     Í¡     */'\u0489' /*     Ò‰_     */
                } ;
        private boolean is_zalgo_char(char c) {
            String fName = "[is_zalgo_char]";
            try {
                logger.info(fName+"c="+c);
                for (char element : zalgo_up)
                    if (c == element)
                        return true;
                for (char item : zalgo_down)
                    if (c == item)
                        return true;
                for (char value : zalgo_mid)
                    if (c == value)
                        return true;
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private char rand_zalgo(char[] array) {
            String fName = "[rand_zalgo]";
            try {
                logger.info(fName+"array="+ Arrays.toString(array));
                int ind = (int)Math.floor(Math.random() * array.length);
                return array[ind];
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return ' ';
            }

        }
        private String string2Zalgo(String source) {
            //https://stackoverflow.com/questions/26927419/zalgo-text-in-java
            String fName = "[string2Zalgo]";
            logger.info(fName);
            try {
                logger.info(fName+"source="+source);
                if(source==null)source="";
                StringBuilder zalgoTxt = new StringBuilder();
                boolean up=true, mid=true,down=true;
                if(source.contains("!up")){
                    up=false; source=source.replaceAll("!up","");
                }
                if(source.contains("!down")){
                    up=false; source=source.replaceAll("!down","");
                }
                if(source.contains("!mid")){
                    up=false; source=source.replaceAll("!mid","");
                }
                boolean zalgo_opt_mini=true,zalgo_opt_normal=false,zalgo_opt_corrupted=false;
                if(source.contains("!mini")){
                    zalgo_opt_mini=true;zalgo_opt_normal=false;zalgo_opt_corrupted=false; source=source.replaceAll("!mini","");
                }
                if(source.contains("!normal")){
                    zalgo_opt_mini=false;zalgo_opt_normal=true;zalgo_opt_corrupted=false; source=source.replaceAll("!normal","");
                }
                if(source.contains("!maxi")){
                    zalgo_opt_mini=false;zalgo_opt_normal=false;zalgo_opt_corrupted=false; source=source.replaceAll("!maxi","");
                }
                if(source.contains("!corrupted")){
                    zalgo_opt_mini=false;zalgo_opt_normal=false;zalgo_opt_corrupted=true; source=source.replaceAll("!corrupted","");
                }
                if(source.contains("!fraktur")){
                    source= lsStringUsefullFunctions.text2Fraktur(source.replaceAll("!fraktur",""));
                    logger.info(fName+"new source="+source);
                }
                if(source==null)source="";
                if(source.contains("!double")){
                    source= lsStringUsefullFunctions.text2DoubleStruck(source.replaceAll("!double",""));
                    logger.info(fName+"new source="+source);
                }
                if(source==null)source="";
                for (int i = 0; i <source.length(); i++) {
                    if (is_zalgo_char(source.charAt(i)))
                        continue;

                    int num_up;
                    int num_mid;
                    int num_down;

                    //add the normal character
                    zalgoTxt.append(source.charAt(i));

                    //options
                    if (zalgo_opt_mini) {
                        num_up = lsUsefullFunctions.getRandom(8);
                        num_mid = lsUsefullFunctions.getRandom(2);
                        num_down = lsUsefullFunctions.getRandom(8);
                    } else if (zalgo_opt_normal) {
                        num_up = lsUsefullFunctions.getRandom(16) / 2 + 1;
                        num_mid = lsUsefullFunctions.getRandom(6) / 2;
                        num_down = lsUsefullFunctions.getRandom(16) / 2 + 1;
                    } else if (zalgo_opt_corrupted) {
                        num_up = lsUsefullFunctions.getRandom(16) / 2 + 1;
                        num_mid = lsUsefullFunctions.getRandom(16,32) / 2+1 ;
                        num_down = lsUsefullFunctions.getRandom(16) / 2 + 1;
                    }
                    else //maxi
                    {
                        num_up = lsUsefullFunctions.getRandom(64) / 4 + 3;
                        num_mid =lsUsefullFunctions.getRandom(16) / 4 + 1;
                        num_down = lsUsefullFunctions.getRandom(64) / 4 + 3;
                    }
                    logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                    logger.info(fName+"num_up ="+num_up +", num_mid="+num_mid+", num_down="+num_down);
                    if (up)
                        for (int j = 0; j < num_up; j++){
                            zalgoTxt.append(rand_zalgo(zalgo_up));
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }

                    if (mid)
                        for (int j = 0; j < num_mid; j++){
                            zalgoTxt.append(rand_zalgo(zalgo_mid));
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }
                    if (down)
                        for (int j = 0; j < num_down; j++){
                            zalgoTxt.append(rand_zalgo(zalgo_down));
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }

                }



                return zalgoTxt.toString();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  "";
            }
        }


        //https://yaytext.com/monospace/
        //https://qwerty.dev/zalgo-text-generator/
        //"𝕿𝖊𝖘𝖙""𝚃𝚎𝚜𝚝""Test""𝕋𝕖𝕤𝕥"

    }

}
