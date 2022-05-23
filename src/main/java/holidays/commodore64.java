package holidays;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.la.aBasicCommandHandler;
import models.lc.lcBasicFeatureControl;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class commodore64 extends Command implements  llGlobalHelper  {
    //http://zerosprites.com/mine/
    //https://www.spritemate.com
    String cName="[Commodor64-OwO]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gCommand ="c64";
    public commodore64(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; 
        this.name = "Commodore64";
        this.help = "A fun reminder for Commodore 64 that was released on August";
        this.aliases = new String[]{gCommand,"64"};
        this.guildOnly = true;
        this.category=llCommandCategory_SFW;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal extends aBasicCommandHandler implements Runnable {
        String cName = "[runLocal]";
        String gTitle = "Commodore 64";

        public runLocal(CommandEvent ev) {
            logger.info(cName + ".run build");
            String fName = "runLocal";
            setCommandHandlerValues(logger,ev);
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + fName);
            try {
                buildBasicFeatureControl(gGlobal,logger,"commodore64",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if (gEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
                    }
                    else if(ifItsAnAccessControlCommand()){
                        logger.info(fName+"its an AccessControlCommand");
                    }else
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                    }else
                    if (items[0].equalsIgnoreCase("hello")) {
                        doHello();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("system")) {
                       doSystemSpecs();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("helloworld")||(items.length>=2&&items[0].equalsIgnoreCase("hello")&&items[1].equalsIgnoreCase("world"))) {
                        doHELLOWORLD();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("price")) {
                        doPrice();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("printexample")||(items.length>=2&&items[0].equalsIgnoreCase("print")&&items[1].equalsIgnoreCase("example"))) {
                        doPrintExample();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("testsound1")) {
                        testAudioULAW2PCM();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("testsound2")) {
                        testAudioMP32Wav();isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("getavatar")) {
                        List <Member>members=gEvent.getMessage().getMentionedMembers();
                        int mode=0;boolean high=false, transparent=false;
                        for (String item : items) {
                            logger.info(cName + fName + ".item="+item);
                            switch (item) {
                                case "1":
                                case "2":
                                case "3":
                                case "4":
                                case "5":
                                case "-1":
                                case "-2":
                                case "-3":
                                case "-4":
                                    mode = Integer.parseInt(item);
                                    //if(item.charAt(0)=='-'&&mode<0){mode=-1*mode;}
                                    logger.info(cName + fName + ".mode="+mode);
                                    break;
                                case "true":
                                    high = true;
                                    break;
                                case "transparent":
                                    transparent = true;
                                    break;
                            }
                        }
                        if(members.isEmpty()){
                            doAvatar(gMember,mode,high,transparent);
                        }else{
                            for(Member member:members){
                                doAvatar(member,mode,high,transparent);
                            }
                        }
                        isInvalidCommand = false;
                    }else
                    if (items[0].equalsIgnoreCase("testavatar")) {
                        List <Member>members=gEvent.getMessage().getMentionedMembers();
                        int mode=0;boolean high=false, transparent=false;
                        for (String item : items) {
                            logger.info(cName + fName + ".item="+item);
                            switch (item) {
                                case "1":
                                case "2":
                                case "3":
                                case "4":
                                case "5":
                                case "-1":
                                case "-2":
                                case "-3":
                                case "-4":
                                    mode = Integer.parseInt(item);
                                    logger.info(cName + fName + ".mode_general="+mode);
                                    break;
                                /*case "-1": mode=-1; logger.info(cName + fName + ".mode_negative="+mode);break;
                                case "-2": mode=-2; logger.info(cName + fName + ".mode_negative="+mode);break;
                                case "-3": mode=-3;logger.info(cName + fName + ".mode_negative="+mode);break;
                                case "-4": mode=-4; logger.info(cName + fName + ".mode_negative="+mode);break;*/
                                case "true":
                                    high = true;
                                    break;
                                case "transparent":
                                    transparent = true;
                                    break;
                            }
                        }
                        if(members.isEmpty()){
                            testAvatar(gMember,mode,high,transparent);
                        }else{
                            for(Member member:members){
                                testAvatar(member,mode,high,transparent);
                            }
                        }
                        isInvalidCommand = false;
                    }
                }
                deleteCommandPostAndCheckIfInvalid();
            } catch (Exception e) {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        lcWebHookBuild webHookBuild;
        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + gCommand + " ";
            String desc = "1?:?:?:?:?:?:?:?:?:?:?:?:?:?:?:?:?:?:?:?Commands:\n" +
                    "system, hello, price, getavatar <1,2,3,4,5,-1,-2,-3,-4, transparent>, helloworld, printexample";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Sent command list to DM!", llColors.llColorGreen1);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(gTitle);embed.setDescription(desc);
            embed.setColor(llColors.llColorRed);
            embed.addField("Server (guild) options","Type `"+quickSummonWithSpace+"guild|server` to enable or disable this module for the server.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        String strStartup=" \\*\\*\\*\\* COMMODORE 64 BASIC V2 \\*\\*\\*\\*\n" +
                " 64K RAM SYSTEM  38911 BASIC BYTES FREE";
        String strReady="READY.";
        String strHELLOWORLD="HELLO WORLD";
        String strPrintExample="PRINT \"I AM YOUR LOYAL ROBOT SLAVE.\"";
        String strPrintScheme="PRINT \n!text\n";
        String strPrice595="FOR $595, YOU GET WHAT NOBODY ELSE CAN GIVE YOU FOR TWICE THE PRICE.";
        String strPriceMore="Even at twice the price, you won't find the power of a Commodore 64™️ in any personal computer:\n" +
                "The Commodore 64 has a built-in memory of 64K.\n" +
                "That fact alone would have sent computer critics and analysts such as Shearson/American Express to the typewriter for the kind of praise you read on the cover.\nIT'S NOT HOW LITTLE IT COSTS, IT'S HOW MUCH YOU GET";
        String strSystemSpecs="CPU 1.023MHz\nMEMORY 64KB RAM+ 20KB ROM\nGRAPHICS VIC-II 320x200, 16 COLOR\nSOUND SID 6581 3x osc, 4x wave, filter, ADSR, ring\nCONNECTIVITY 2x CIA 6526, ROM CARTRIDGE, RF/A/V, SERIAL IEEE 488 bus floppy/printer, DIGITAL TAPE";
        String strSystemSpecs_Name="\\*\\*SYSTEM SPECS\\*\\*";
        String strSystemSpecs_CPU="CPU 1.023MHz";
        String strSystemSpecs_MEMORY="MEMORY 64KB RAM+ 20KB ROM";
        String strSystemSpecs_GRAPHICS="GRAPHICS VIC-II 320x200, 16 COLOR";
        String strSystemSpecs_SOUND="SOUND SID 6581 3x OSC, 4x WAVE, FILTER, ADSR, RING";
        String strSystemSpecs_CONNECTIVITY="CONNECTIVITY 2x CIA 6526, ROM CARTRIDGE, RF/A/V, SERIAL IEEE 488 BUS FLOPPY/PRINTER, DIGITAL TAPE";
        String strAvatarImage0="RUNNING PROGRAM DISPLAYING USER AVATAR";
        String strAvatarImage1="ACCESSING PICTURE ADDRESS OF !user";
        String strAvatarImage_SIZE1024="ATTEMPTING TO LOAD SIZE=1024 ";
        String strAvatarImage_SIZEDEFAULT="FAILED 0X1024\nATTEMPTING TO LOAD SIZE=DEFAULT ";
        String strHello1="HELLO, I'M THE COMMODORE 64 - WHAT IS YOUR NAME?";
        String strHello2="HELLO !input";

        private boolean createWebHook() {
            String fName = "createWebHook";
            logger.info(cName + fName);
            webHookBuild=new lcWebHookBuild();
            webHookBuild.doSafetyCleanwToken(gTextChannel);
            JSONObject json = new JSONObject();
            json.put("name", "Commodore 64");
            json.put("avatarurl","https://cdn.discordapp.com/attachments/698511311843229777/733350767699689602/kisspng-logo-commodore-international-commodore-64-amiga-pe-name-5ac4457d93f809.411656411522812285606.png");
            if (!webHookBuild.preBuild(gTextChannel, json)) {logger.warn(cName + fName + ".no prebuild!");return false;}
            if (!webHookBuild.build()) {logger.warn(cName + fName + ".no build!");return false;}
            if (!webHookBuild.clientOpen()) { webHookBuild.delete();logger.warn(cName + fName + ".no client!");return false;}
            logger.info(cName + fName + ".ready");
            return true;
        }
        private boolean deleteWebHook() throws InterruptedException {
            String fName = "deleteWebHook";
            logger.info(cName + fName);
            logger.info(cName + fName + ".close and delete webhook");
            webHookBuild.clientClose();
            Thread.sleep(1000);
            return webHookBuild.delete();
        }
        private void doSystemSpecs() throws InterruptedException {
            String fName = "doSystemSpecs";
            logger.info(cName + fName);
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);

            Thread.sleep(5000);
            message=strSystemSpecs_Name;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strSystemSpecs_CPU;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strSystemSpecs_MEMORY;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strSystemSpecs_GRAPHICS;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strSystemSpecs_SOUND;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strSystemSpecs_CONNECTIVITY;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);

            Thread.sleep(2000);
            message=strReady;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
        }
        private void doHELLOWORLD() throws InterruptedException {
            String fName = "doHELLOWORLD";
            logger.info(cName + fName);
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strHELLOWORLD;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            message=strReady;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
        }
        private void doHello() throws InterruptedException {
            String fName = "doHello";
            logger.info(cName + fName);
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strHello1;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
            gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> (e.getAuthor().equals(gUser)&&e.getChannel().equals(gTextChannel)),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(!createWebHook()){return;}
                            webHookBuild.send(strHello2.replaceAll("!input",content.toUpperCase()));
                            Thread.sleep(2000);
                            webHookBuild.send(strReady);
                            Thread.sleep(2000);
                            deleteWebHook();
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Exception:" + e2.toString(), llColors.llColorRed_Barn);
                        }
                    },1, TimeUnit.MINUTES, () -> {
                        try {
                            if(!createWebHook()){return;}
                            webHookBuild.send("TIMEOUT");
                            Thread.sleep(2000);
                            webHookBuild.send(strReady);
                            Thread.sleep(2000);
                            deleteWebHook();
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Exception:" + e2.toString(), llColors.llColorRed_Barn);
                        }
                    });
        }
        private void doPrintExample() throws InterruptedException {
            String fName = "doPrintExample";
            logger.info(cName + fName);
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strPrintExample;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            message=strReady;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
        }
        private void doPrice() throws InterruptedException {
            String fName = "doPrice";
            logger.info(cName + fName);
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(5000);
            message=strPrice595;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            message=strPriceMore;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            message=strReady;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
        }
        private void doAvatar(Member member,int mode, boolean high,boolean transparent) throws InterruptedException, IOException {
            String fName = "doAvatar";
            logger.info(cName + fName+"member:"+member.getId());
            String url=member.getUser().getEffectiveAvatarUrl();
            String name=member.getUser().getDiscriminator();
            //gTextChannel.sendFile(is2,"test8.jpg").queue();
            if(!createWebHook()){return;}
            logger.info(cName + fName + ".send webhook");
            String message=strStartup;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            message=strAvatarImage0;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(1000);
            message=strAvatarImage1.replaceAll("!user",member.getEffectiveName().toUpperCase()+"("+member.getId()+")");
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            message=strAvatarImage_SIZE1024;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            InputStream is= lsStreamHelper.llGetInputStream4WebFile(url+"?size=1024");
            if(is==null){
                is= lsStreamHelper.llGetInputStream4WebFile(url);
                message=strAvatarImage_SIZEDEFAULT;
                logger.info(cName + fName + ".message=" + message);
                webHookBuild.send(message);
            }
            int[] palette;
            if(is!=null){
                BufferedImage image=ImageIO.read(is);
                BufferedImage src;
                if(mode==0){
                    palette = lsImageConvert.CGAPalette();
                    message="CONVERTING TO 8 BIT 16 COLOR CGA";
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }else{
                    palette = lsImageConvert.CGAPalette(mode,high);
                    message="CONVERTING TO 8 BIT 16 COLOR CGA\nSELECT PALETTE "+Arrays.toString(palette);
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }
                if(transparent){
                    message="APPLY TRANSPARENCY";
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                    src = lsImageConvert.convert8CGATransparency(image,palette);
                }else{
                    src = lsImageConvert.convert8CGA(image,palette);
                }
                src=lsImageConvert.pixelate(src,1);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                if(src!=null){
                    ImageIO.write(src, "gif", os);
                    InputStream is2 = new ByteArrayInputStream(os.toByteArray());
                    Thread.sleep(2000);
                    if(url.contains(".gif")){
                        webHookBuild.send(is2,name+".gif");
                    }else{
                        webHookBuild.send(is2,name+".png");
                    }
                }else{
                    message="ERROR!";
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }
            }else{
                message="ERROR!";
                logger.info(cName + fName + ".message=" + message);
                webHookBuild.send(message);
            }
            Thread.sleep(2000);
            message=strReady;
            logger.info(cName + fName + ".message=" + message);
            webHookBuild.send(message);
            Thread.sleep(2000);
            deleteWebHook();
        }
        private void testAvatar(Member member,int mode, boolean high,boolean transparent) {
            String fName = "testAvatar";
            try {
                logger.info(cName + fName+"member:"+member.getId());
                String url=member.getUser().getEffectiveAvatarUrl();
                String name=member.getUser().getDiscriminator();
                //gTextChannel.sendFile(is2,"test8.jpg").queue();
                if(!createWebHook()){return;}
                logger.info(cName + fName + ".send webhook");
                String message="";
                InputStream is= lsStreamHelper.llGetInputStream4WebFile(url+"?size=1024");
                if(is==null){
                    is= lsStreamHelper.llGetInputStream4WebFile(url);
                    message=strAvatarImage_SIZEDEFAULT;
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }
                int []palette;
                if(is!=null){
                    BufferedImage image=ImageIO.read(is);
                    if(mode==0){
                        palette=lsImageConvert.Mix_1_Palette();
                        testAvatarPost(image,palette);
                        palette=lsImageConvert.C64Palette();
                        testAvatarPost(image,palette);
                        palette=lsImageConvert.C64_2_Palette();
                        testAvatarPost(image,palette);
                        palette=lsImageConvert.C64_3_Palette();
                        testAvatarPost(image,palette);
                        palette = lsImageConvert.CGAPalette();
                        testAvatarPost(image,palette);
                    }else{
                        palette=lsImageConvert.C64Palette(mode,high);
                        testAvatarPost(image,palette);
                        palette=lsImageConvert.C64_2_Palette(mode,high);
                        testAvatarPost(image,palette);
                        palette=lsImageConvert.C64_3_Palette(mode,high);
                        testAvatarPost(image,palette);
                        palette = lsImageConvert.CGAPalette(mode,high);
                        testAvatarPost(image,palette);
                    }
                }else{
                    message="ERROR!";
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }
                Thread.sleep(2000);
                message=strReady;
                logger.info(cName + fName + ".message=" + message);
                webHookBuild.send(message);
                Thread.sleep(20000);
                deleteWebHook();
            } catch (Exception e) {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:" + e.toString(), llColors.llColorRed_Barn);
            }
        }
        private void testAvatarPost(BufferedImage image,int []palette) {
            String fName = "testAvatarPost";
            try {
                BufferedImage src;
                src = lsImageConvert.convert8CGA(image,palette);
                String message="SELECT PALETTE "+Arrays.toString(palette);
                logger.info(cName + fName + ".message=" + message);
                webHookBuild.send(message);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                if(src!=null){
                    ImageIO.write(src, "gif", os);
                    InputStream is2 = new ByteArrayInputStream(os.toByteArray());
                    Thread.sleep(2000);
                    webHookBuild.send(is2,name+".png");
                }else{
                    message="ERROR!";
                    logger.info(cName + fName + ".message=" + message);
                    webHookBuild.send(message);
                }
            } catch (Exception e) {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:" + e.toString(), llColors.llColorRed_Barn);
            }
        }

        private void testAudioULAW2PCM() {
            //IN&Out:wav
            String fName = "testAudioULAW2PCM";
            logger.info(cName + fName);
            try {
                //https://www.codeproject.com/Messages/3168737/Java-convert-uLaw-encoded-audio-file-to-PCM-ecoded.aspx
                File temp = new File("resources/test/test.wav");
                if(!temp.exists()){ lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No exists!", llColors.llColorRed_Barn);return;}
                if(!temp.isFile()||temp.isDirectory()){ lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Not a file", llColors.llColorRed_Barn);return;}
                logger.warn(fName+".file:"+temp.getPath()+"|"+temp.getName());
                File fileout = new File("storage/test/1java.wav");
                AudioInputStream sourceaudio = AudioSystem.getAudioInputStream(temp);
                AudioFormat targetformat = new AudioFormat(new AudioFormat.Encoding("PCM_UNSIGNED"),8000,16,0,8,8000,true);
                AudioFileFormat.Type targettype = AudioFileFormat.Type.WAVE;
                AudioInputStream targetaudiostream = AudioSystem.getAudioInputStream(targetformat,sourceaudio);
                AudioSystem.write(targetaudiostream, targettype, fileout);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Done",llColors.llColorBlue1);
            } catch (Exception e) {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Exception:" + e.toString(), llColors.llColorRed_Barn);
            }
        }
        private void testAudioMP32Wav() {
            String fName = "testAudioMP32Wav";
            logger.info(cName + fName);
            try {
                //https://stackoverflow.com/questions/41784397/convert-mp3-to-wav-in-java
                //https://stackoverflow.com/questions/14085199/mp3-to-wav-conversion-in-java/14144956
                File temp = new File("resources/test/test.mp3");
                if(!temp.exists()){ lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No exists!", llColors.llColorRed_Barn);return;}
                if(!temp.isFile()||temp.isDirectory()){ lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Not a file", llColors.llColorRed_Barn);return;}
                logger.warn(fName+".file:"+temp.getPath()+"|"+temp.getName());
                AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(temp);
                AudioFormat sourceFormat = mp3Stream.getFormat();
                // create audio format object for the desired stream/audio format
                // this is *not* the same as the file format (wav)
                AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        sourceFormat.getSampleRate(), 16,
                        sourceFormat.getChannels(),
                        sourceFormat.getChannels() * 2,
                        sourceFormat.getSampleRate(),
                        false);
                // create stream that delivers the desired format
                AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
                // write stream into a file with file format wav
                AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File("test/out.wav"));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Done",llColors.llColorBlue1);
            } catch (Exception e) {
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Exception:" + e.toString(), llColors.llColorRed_Barn);
            }
        }
    }

}
