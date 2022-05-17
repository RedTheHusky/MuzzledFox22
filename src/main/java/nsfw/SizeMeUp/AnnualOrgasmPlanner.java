package nsfw.SizeMeUp;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.lcJSONMatrix2D;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import social.lcSocialization;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnnualOrgasmPlanner extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper{
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="aopfm", gTitle="Annual Orgasm Planner For Male";
    public AnnualOrgasmPlanner(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Know how much orgasm you're allowed to have for an entire year.";
        this.aliases = new String[]{KeyTag};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=false;
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
            String fName="runLocal";
            logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"AnnualOrgasmPlanner",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand = true;
                if(!isNSFW()){
                    blocked();return;
                }
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    requestAge();isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main"); isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
                        if(items.length>2){
                            // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                            int group=0,type=0,action=0;
                            switch (items[1].toLowerCase()){
                                case "allowedchannels":
                                case "allowchannels":
                                    group=1;type=1;
                                    break;
                                case "blockedchannels":
                                case "blockchannels":
                                    group=1;type=-1;
                                    break;
                                case "allowedroles":
                                case "allowroles":
                                    group=2;type=1;
                                    break;
                                case "blockedroles":
                                case "blockroles":
                                    group=2;type=-1;
                                    break;
                            }
                            switch (items[2].toLowerCase()){
                                case "list":
                                    action=0;
                                    break;
                                case "add":
                                    action=1;
                                    break;
                                case "set":
                                    action=2;
                                    break;
                                case "rem":
                                    action=-1;
                                    break;
                                case "clear":
                                    action=-2;
                                    break;
                            }
                            if(group==1){
                                if(action==0){
                                    getChannels(type,false);isInvalidCommand=false;
                                }else{
                                    setChannel(type,action,gEvent.getMessage());
                                }
                            }
                            else if(group==2){
                                if(action==0){
                                    getRoles(type,false);isInvalidCommand=false;
                                }else{
                                    setRole(type,action,gEvent.getMessage());
                                }
                            }
                        }else{
                            menuGuild();isInvalidCommand=false;
                        }
                    }
                    else if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("dm")){
                        requestAge(); isInvalidCommand=false;
                    }else
                    if(items[0].equalsIgnoreCase("test")){
                        test(); isInvalidCommand=false;
                    }else{
                        input();isInvalidCommand=false;
                    }

                }

                logger.info(fName+".deleting op message");
                llMessageDelete(gEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
                logger.info(".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            String desc="`"+llPrefixStr+KeyTag+" AGE LENGTH <UNIT(inch/cm)>";
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+llPrefixStr+KeyTag+" guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private boolean isNSFW(){
            String fName = "[isNSFW]";
            if(gTextChannel.isNSFW()){
                return true;
            }
            if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
                return true;
            }
            return false;
        }
        private void blocked(){
            String fName = "[blocked]";
            llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private void restricted() {
            String fName = "[restricted]";
            logger.info(fName);
            String desc="`Need to be used on a NSFW channel!";
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,desc, llColorRed);
        }
        private void noMention() {
            String fName = "[noMention]";
            logger.info(fName);
            String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,desc, llColorRed);
        }
        String keyCm ="Cm",keyLength="Length", keyAge="Age";
        int timeoutInt=5;
        private void test() {
            String fName = "[test]";
            logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyAge,27);
                jsonObject.put(keyLength,7);
                doCalc();doPosting();
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        JSONObject jsonUser;
        private void requestAge() {
            String fName = "[requestAge]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter age.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor().getIdLong()==gUser.getIdLong()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                jsonUser=new JSONObject();
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                int age=Integer.parseInt(content);
                                logger.info(fName+"age="+age);
                                jsonUser.put(keyAge,age);
                                requestLength();
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        timeoutInt, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void requestLength() {
            String fName = "[requestErectLength]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter your LENGTH in inch/cm. If using cm, don't forget to add cm.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor().getIdLong()==gUser.getIdLong()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                if(content.toLowerCase().contains("cm")){
                                    jsonUser.put(keyCm,true);
                                    content=content.toLowerCase().replaceAll("cm","");
                                }else{
                                    jsonUser.put(keyCm,false);
                                }
                                double size=Double.parseDouble(content);
                                logger.info(fName+"size="+size);
                                jsonUser.put(keyLength,size);
                                doCalc();doPosting();
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        timeoutInt, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void input() {
            String fName = "[input]";
            logger.info(fName);
            try{
                jsonUser=new JSONObject();
                String []items;
                items = gEvent.getArgs().split("\\s+");
                //unit, erect/girth/soft ==4
                int length=items.length;
                logger.info(fName+"length="+length);
                if(length<2){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Invalid input. Please input it as following: `"+llPrefixStr+KeyTag+" AGE LENGTH <UNIT(inch/cm)>", llColorRed);
                    return;
                }
                if(items[1].toLowerCase().contains("cm")){
                    jsonUser.put(keyCm,true);
                    items[1]=items[1].toLowerCase().replaceAll("cm","");
                }else{
                    jsonUser.put(keyCm,false);
                }
                int age=Integer.parseInt(items[0]);
                logger.info(fName+"age="+age);
                jsonUser.put(keyAge,age);
                double size=Double.parseDouble(items[1]);
                logger.info(fName+"size="+size);
                jsonUser.put(keyLength,size);
                jsonUser.put(keyCm,false);
                if(length>=3){
                    if(items[2].toLowerCase().equalsIgnoreCase("cm")){
                        jsonUser.put(keyCm,true);
                    }
                }
                doCalc();doPosting();
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        double SIZE=0;int AGE=0;
        double CM2INCH= 0.394;
        int ORGASMCOUNT=0, DAYSBETWEEN=0;
        lcJSONMatrix2D orgasmCountMatrix;
        private void createMatrix() {
            String fName = "[createMatrix]";
            logger.info(fName);
            orgasmCountMatrix=new lcJSONMatrix2D();

            orgasmCountMatrix.set(2,20,2);
            orgasmCountMatrix.set(2,25,1);

            orgasmCountMatrix.set(3,20,4);
            orgasmCountMatrix.set(3,25,2);
            orgasmCountMatrix.set(3,30,1);

            orgasmCountMatrix.set(4,20,8);
            orgasmCountMatrix.set(4,25,4);
            orgasmCountMatrix.set(4,30,2);
            orgasmCountMatrix.set(4,35,1);

            orgasmCountMatrix.set(5,20,12);
            orgasmCountMatrix.set(5,25,8);
            orgasmCountMatrix.set(5,30,4);
            orgasmCountMatrix.set(5,35,2);
            orgasmCountMatrix.set(5,40,1);

            orgasmCountMatrix.set(6,20,16);
            orgasmCountMatrix.set(6,25,12);
            orgasmCountMatrix.set(6,30,8);
            orgasmCountMatrix.set(6,35,4);
            orgasmCountMatrix.set(6,40,2);
            orgasmCountMatrix.set(6,45,1);

            orgasmCountMatrix.set(7,20,24);
            orgasmCountMatrix.set(7,25,16);
            orgasmCountMatrix.set(7,30,12);
            orgasmCountMatrix.set(7,35,8);
            orgasmCountMatrix.set(7,40,4);
            orgasmCountMatrix.set(7,45,2);
            orgasmCountMatrix.set(7,50,1);

            orgasmCountMatrix.set(8,20,48);
            orgasmCountMatrix.set(8,25,24);
            orgasmCountMatrix.set(8,30,16);
            orgasmCountMatrix.set(8,35,12);
            orgasmCountMatrix.set(8,40,4);
            orgasmCountMatrix.set(8,45,2);
            orgasmCountMatrix.set(8,50,1);

            orgasmCountMatrix.set(9,20,96);
            orgasmCountMatrix.set(9,25,48);
            orgasmCountMatrix.set(9,30,24);
            orgasmCountMatrix.set(9,35,16);
            orgasmCountMatrix.set(9,40,12);
            orgasmCountMatrix.set(9,45,4);
            orgasmCountMatrix.set(9,50,2);
            orgasmCountMatrix.set(9,55,1);

            orgasmCountMatrix.set(10,20,192);
            orgasmCountMatrix.set(10,25,96);
            orgasmCountMatrix.set(10,30,48);
            orgasmCountMatrix.set(10,35,24);
            orgasmCountMatrix.set(10,40,16);
            orgasmCountMatrix.set(10,45,12);
            orgasmCountMatrix.set(10,50,4);
            orgasmCountMatrix.set(10,55,2);
            orgasmCountMatrix.set(10,60,1);
        }
        private void doCalc() {
            String fName = "[doCalc]";
            logger.info(fName);
            try{
                createMatrix();
                if(jsonUser.getBoolean(keyCm)){
                    SIZE = jsonUser.getDouble(keyLength) * CM2INCH;
                }else{
                    SIZE = jsonUser.getDouble(keyLength);
                }
                logger.info(fName+ "  SIZE="+  SIZE);
                AGE = jsonUser.getInt(keyAge);
                logger.info(fName+ "  AGE="+  AGE);
                int sizeNoDecimal=(int)SIZE;
                logger.info(fName+ " sizeNoDecimal="+ sizeNoDecimal);
                double sizeDecimal=SIZE-sizeNoDecimal;
                logger.info(fName+ "  sizeDecimal="+  sizeDecimal);
                int matrixAgeMin,matrixAgeMax=0;
                int matrixOrgasmMax=0, matrixOrgasmMin=0;
                int matrixOrgasmMax2=0, matrixOrgasmMin2=0;
                int matrixOrgasmMaxDif=0, matrixOrgasmMinDif=0;int matrixOrgasmMax3=0, matrixOrgasmMin3=0;
                int ageOffSetPositive=0, ageOffSetNegative=0;int matrixOrgasmDif3=0,matrixOrgasmDif3Div2=0;
                if(AGE<20){
                    matrixAgeMin=matrixAgeMax=20;
                }else
                if(AGE%5==0){
                    matrixAgeMin=matrixAgeMax=AGE;
                }else{
                    matrixAgeMin=matrixAgeMax=AGE;
                    int n=0, nMax=50;
                    while(matrixAgeMin%5!=0&&n<nMax){
                        matrixAgeMin--; n++;ageOffSetNegative--;
                    }
                    n=0;
                    while(matrixAgeMax%5!=0&&n<nMax){
                        matrixAgeMax++; n++;ageOffSetPositive++;
                    }
                }
                if(sizeNoDecimal>10){
                    sizeNoDecimal=10; sizeDecimal=0;
                    logger.info(fName+ " updated sizeNoDecimal="+ sizeNoDecimal);
                    logger.info(fName+ "  updated  sizeDecimal="+  sizeDecimal);
                }
                if(sizeNoDecimal<2){
                    sizeNoDecimal=2; sizeDecimal=0;
                    logger.info(fName+ " updated sizeNoDecimal="+ sizeNoDecimal);
                    logger.info(fName+ "  updated  sizeDecimal="+  sizeDecimal);
                }
                matrixOrgasmMax=orgasmCountMatrix.getInt(sizeNoDecimal,matrixAgeMax);
                matrixOrgasmMin=orgasmCountMatrix.getInt(sizeNoDecimal,matrixAgeMin);
                logger.info(fName+ "  matrixOrgasmMax="+  matrixOrgasmMax); logger.info(fName+ "  matrixOrgasmMin="+  matrixOrgasmMin);
                matrixOrgasmMax3=matrixOrgasmMax;matrixOrgasmMin3=matrixOrgasmMin;
                if(sizeDecimal!=0){
                    matrixOrgasmMax2=orgasmCountMatrix.getInt(sizeNoDecimal+1,matrixAgeMax);
                    matrixOrgasmMin2=orgasmCountMatrix.getInt(sizeNoDecimal+1,matrixAgeMin);
                    logger.info(fName+ "  matrixOrgasmMax2="+  matrixOrgasmMax2);
                    logger.info(fName+ "  matrixOrgasmMin2="+  matrixOrgasmMin2);
                    if(matrixOrgasmMax2!=0){
                        matrixOrgasmMaxDif=matrixOrgasmMax2-=matrixOrgasmMax;
                        logger.info(fName+ "   matrixOrgasmMaxDif="+matrixOrgasmMaxDif);
                        if(matrixOrgasmMaxDif>0){
                            int offsetAddMax3=(int)(Math.round(matrixOrgasmMaxDif*sizeDecimal * 100.0) / 100.0);
                            logger.info(fName+ "  offsetAddMax3="+offsetAddMax3);
                            matrixOrgasmMax3=matrixOrgasmMax+offsetAddMax3;
                        }
                    }
                    if(matrixOrgasmMin2!=0){
                        matrixOrgasmMinDif=matrixOrgasmMin2-=matrixOrgasmMin;
                        logger.info(fName+ "  matrixOrgasmMinDif="+matrixOrgasmMinDif);
                        if(matrixOrgasmMinDif>0){
                            int offsetAddMin3=(int)(Math.round(matrixOrgasmMinDif*sizeDecimal * 100.0) / 100.0);
                            logger.info(fName+ "  offsetAddMin3="+offsetAddMin3);
                            matrixOrgasmMin3=matrixOrgasmMin+offsetAddMin3;
                        }
                    }
                }
                logger.info(fName+ "matrixOrgasmMax3="+  matrixOrgasmMax3);
                logger.info(fName+ "matrixOrgasmMin3="+  matrixOrgasmMin3);
                if(matrixOrgasmDif3!=0){
                    matrixOrgasmDif3=matrixOrgasmMax3-matrixOrgasmMin3;
                    matrixOrgasmDif3Div2=matrixOrgasmDif3/2;
                    logger.info(fName+ "matrixOrgasmDif3="+  matrixOrgasmDif3);
                    logger.info(fName+ "matrixOrgasmDif3Div2="+  matrixOrgasmDif3Div2);
                }
                if(ageOffSetNegative==0){
                    ORGASMCOUNT= matrixOrgasmMax3;
                }
                if(ageOffSetNegative>ageOffSetPositive){
                    ORGASMCOUNT= matrixOrgasmMin3+matrixOrgasmDif3Div2;
                }else{
                    ORGASMCOUNT= matrixOrgasmMin3;
                }
                logger.info(fName+ "ORGASMCOUNT="+  ORGASMCOUNT);
                if(ORGASMCOUNT!=0){
                    DAYSBETWEEN=365/ORGASMCOUNT;
                }
                logger.info(fName+ "DAYSBETWEEN="+  DAYSBETWEEN);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        String msgParticipateFull="Participate in events the way you want to.";
        String msgParticipateAsSlave="Participate in events as slave.";
        String msgParticipateAsFuckToy="Participate in events as fuck toy.";
        String msgParticipateAsPuppy="Participate in events as a puppy.";
        String msgDontParticipate="Don't participate in events.";
        String msgChastityPermanent="Pernament chastity recomended!";
        String msgChastityRequired="Chastity device required!";
        String msgChastityRecommended="Chastity device recommended.";
        String msgChastityAdvised="Chastity device advised as it will help you.";
        String msgChastitySuggested="Chastity device suggested if you cant even take a couple of days..";
        String msgChastity2Big="Their dick a monster, no need to lock it.";
        String msgChastityNo="No need to lock yourself up as almost everyday you get to cum.";
        String msgNA="N/A";
        Color useColor= Color.BLACK;
        String msgSizeLittleGirl="You're a little girl, sorry. Your dick is useless, well useful to remind your place. Just give up and take it up in the ass!";
        String msgSizeGirl="You're girl. Your dick has no great usage besides humiliating you in front of other mens with real penises";
        String msgSizeNotAMan="Not a Man but not a Girl eighter. So can both wear girl and men cloths.";
        String msgSizeAlmostAMan="Almost a man. Nobody will think you're a girl but you are not a real men.";
        String msgSizeAverage="Average. You're not bad in the bed, but advised to use some toys";
        String msgSizeMan="Man. They will feel it as you fuck them. Your own toy should be enought for them.";
        String msgSizeRealMan="Real Man. They will scream your name as you satisfy any girl. Any queen will knight you for your service.";
        
        String strPosting1="Recommended maximal annual number of orgasms based on your age and  penis size";
        String strPosting2="Recommended minimal time between orgasms";
        private void doPosting() {
            String fName = "[doPosting]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);
                embedBuilder.setAuthor(gUser.getName(),null, gUser.getEffectiveAvatarUrl());
                if(ORGASMCOUNT<=0){
                    embedBuilder.addField(strPosting1,"PERMANENT ORGASM DENIAL!",false);
                }else if(ORGASMCOUNT==1){
                    embedBuilder.addField(strPosting1,"You get just one day/year. Better have it on a special day.",false);
                }else{
                    embedBuilder.addField(strPosting1,String.valueOf(ORGASMCOUNT),false);
                }

                if(ORGASMCOUNT<=0&&DAYSBETWEEN<=0){
                    embedBuilder.addField(strPosting2,"24/7/365, no minimal time between orgasm, no orgasm",false);
                }else if(ORGASMCOUNT>0&&DAYSBETWEEN<=0){
                    embedBuilder.addField(strPosting2,"Everyday you get to cum!",false);
                }else if(DAYSBETWEEN==1){
                    embedBuilder.addField(strPosting2,String.valueOf(DAYSBETWEEN)+ " day.",false);
                }else{
                    embedBuilder.addField(strPosting2,String.valueOf(DAYSBETWEEN)+ " days.",false);
                }

                String msgChastity="", msgEvents="", msgIsMan="";
                if(SIZE>=10){
                    useColor=llColorBlue1;
                    msgChastity=msgChastity2Big;
                    msgEvents=msgParticipateFull;
                }else
                if(ORGASMCOUNT==0){
                    msgChastity=msgChastityPermanent;
                    useColor=llColorOrange_InternationalEngineering;
                    msgEvents=msgParticipateAsSlave;

                }else
                if(DAYSBETWEEN>=30){
                    msgChastity=msgChastityRequired;
                    useColor=llColorOrange_colorwheel;
                    msgEvents=msgParticipateAsFuckToy;
                }else
                if(DAYSBETWEEN>=14){
                    msgChastity=msgChastityRecommended;
                    useColor=llColorYellow_Cyber;
                    msgEvents=msgParticipateAsPuppy;
                }else
                if(DAYSBETWEEN>=7){
                    msgChastity=msgChastityAdvised;
                    useColor=llColorYellow_Cyber;
                    msgEvents=msgParticipateAsPuppy;
                }else
                if(DAYSBETWEEN>=3){
                    msgChastity=msgChastitySuggested;
                    useColor=llColorGreen2;
                    msgEvents=msgParticipateFull;
                }else
                if(DAYSBETWEEN>=3){
                    msgChastity=msgChastityNo;
                    useColor=llColorBlue1;
                    msgEvents=msgParticipateFull;
                }

                if(SIZE>=8){
                    msgIsMan=msgSizeRealMan;
                }else
                if(SIZE>=7){
                    msgIsMan=msgSizeMan;
                }else
                if(SIZE>=5.5){
                    msgIsMan=msgSizeAverage;
                }else
                if(SIZE>=5){
                    msgIsMan=msgSizeAlmostAMan;
                }else
                if(SIZE>=4){
                    msgIsMan=msgSizeNotAMan;
                }else
                if(SIZE>=3){
                    msgIsMan=msgSizeGirl;
                }else {
                    msgIsMan=msgSizeLittleGirl;
                }

                if(!msgIsMan.isEmpty()&&!msgIsMan.isBlank()){
                    embedBuilder.addField("Man Status",msgIsMan,false);
                }
                if(!msgChastity.isEmpty()&&!msgChastity.isBlank()){
                    embedBuilder.addField("Chastity device suggestion",msgChastity,false);
                }
                if(!msgEvents.isEmpty()&&!msgEvents.isBlank()){
                    embedBuilder.addField("Events suggestion",msgEvents,false);
                }

                embedBuilder.setColor(useColor);
                llSendMessageWithDelete(gGlobal,gTextChannel,embedBuilder);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void doDMPosting() {
            String fName = "[doDMPosting]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);
                embedBuilder.setAuthor(gUser.getName(),null, gUser.getEffectiveAvatarUrl());
                embedBuilder.addField(strPosting1,String.valueOf(ORGASMCOUNT),false);
                embedBuilder.addField("Recommended minimal time between orgasms",String.valueOf(DAYSBETWEEN),false);
                embedBuilder.setColor(llColorBlue1);
                llSendMessage(gUser,embedBuilder);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }

        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBasicFeatureControl.setEnable(enable);
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    java.util.List<Long> list=gBasicFeatureControl.getAllowedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    java.util.List<Long> list=gBasicFeatureControl.getDeniedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    java.util.List<Long> list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    java.util.List<Long> list=gBasicFeatureControl.getDeniedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        java.util.List<TextChannel> textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        java.util.List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        java.util.List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        java.util.List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        java.util.List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        java.util.List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role> roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTitle(gTitle+" Options");
                embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+KeyTag+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+KeyTag+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+KeyTag+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+KeyTag+" server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBasicFeatureControl.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    help("main");return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },timeoutInt, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
    }

}
