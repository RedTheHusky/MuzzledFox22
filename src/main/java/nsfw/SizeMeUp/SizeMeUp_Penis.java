package nsfw.SizeMeUp;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SizeMeUp_Penis extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper{
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="penis", gTitle="Penis Size";
    public SizeMeUp_Penis(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Penis Size Comparison";
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
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"SizeMeUp_Penis",gGlobal);
                gBasicFeatureControl.initProfile();
                logger.info(".run start");
                String[] items;
                boolean isInvalidCommand = true;
                if(!isNSFW()){
                    blocked();return;
                }
                if(gEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");requestUnit();isInvalidCommand=false;
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
                        requestUnit(); isInvalidCommand=false;
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
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
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
            String desc="`"+llPrefixStr+KeyTag+" UNIT(inch/cm) ERECT_LENGTH GIRTH FLACCID_LENGTH";
            embed.addField("Commands",desc,false);
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
            llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private void noMention() {
            String fName = "[noMention]";
            logger.info(fName);
            String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,desc, llColorRed);
        }
        String keyUnit="Unit",keyErectLength="ErectLength", keyGirth="Girth", keySoftLength="SoftLength";
        private void test() {
            String fName = "[test]";
            logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyErectLength,5);
                jsonObject.put(keyGirth,5);
                jsonObject.put(keySoftLength,5);
                doCalc(jsonObject);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void requestUnit() {
            String fName = "[requestUnit]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter cm or inch.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor()==gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                JSONObject jsonObject=new JSONObject();
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                if(content.toLowerCase().equalsIgnoreCase("inch")){
                                    jsonObject.put(keyUnit,true);
                                }else{
                                    jsonObject.put(keyUnit,false);
                                }

                                requestErectLength(jsonObject);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void requestErectLength(JSONObject jsonObject) {
            String fName = "[requestErectLength]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter your ERECT LENGTH in inch.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor()==gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                double size=Double.parseDouble(content);
                                logger.info(fName+"size="+size);
                                jsonObject.put(keyErectLength,size);
                                requestGirth(jsonObject);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void requestGirth(JSONObject jsonObject) {
            String fName = "[requestGirth]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter your GIRTH in inch.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor()==gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                double size=Double.parseDouble(content);
                                logger.info(fName+"size="+size);
                                jsonObject.put(keyGirth,size);
                                requestSoftLength(jsonObject);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void requestSoftLength(JSONObject jsonObject) {
            String fName = "[requestSoftLength]";
            logger.info(fName);
            try{
                Message message=llSendQuickEmbedMessageResponse(gUser, gTitle, "Please enter your FLACCID LENGTH in inch.", llColorPurple1);
                if(message==null){
                    logger.error(fName+"cound not send private message");
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel, gTitle, "Error", llColorOrange_InternationalEngineering);
                    return;
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same user, and in the same channel, and for safety, a different message
                        e->(e.getAuthor()==gUser),
                        // respond, inserting the name they listed into the response
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+"content="+content);
                                double size=Double.parseDouble(content);
                                logger.info(fName+"size="+size);
                                jsonObject.put(keySoftLength,size);
                                doCalc(jsonObject);
                            } catch (Exception ex) {
                                llSendQuickEmbedMessage(gUser, gTitle, "Error", llColorRed);
                                logger.error(fName + "  exception:"+ex);
                            }
                        },
                        // if the user takes more than a minute, time out
                        1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                        });
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void input() {
            String fName = "[input]";
            logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                String []items;
                items = gEvent.getArgs().split("\\s+");
                //unit, erect/girth/soft ==4
                int length=items.length;
                logger.info(fName+"length="+length);
                if(length<4){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Invalid input. Please input it as following: `"+llPrefixStr+KeyTag+" UNIT ERECT_LENGTH GIRTH FLACCID_LENGTH", llColorRed);
                    return;
                }
                if(items[0].toLowerCase().equalsIgnoreCase("inch")){
                    jsonObject.put(keyUnit,true);
                }else{
                    jsonObject.put(keyUnit,false);
                }
                double size=Double.parseDouble(items[1]);
                logger.info(fName+"size="+size);
                jsonObject.put(keyErectLength,size);
                size=Double.parseDouble(items[2]);
                logger.info(fName+"size="+size);
                jsonObject.put(keyGirth,size);
                size=Double.parseDouble(items[3]);
                logger.info(fName+"size="+size);
                jsonObject.put(keySoftLength,size);
                doCalc(jsonObject);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        double m_LENGTH 	= 13.92;
        double sd_LENGTH   = 1.75;
        double m_GIRTH 	= 11.55;
        double sd_GIRTH 	= 1.56;
        double m_SOFTL 	= 9.16;
        double sd_SOFTL   	= 1.57;
        double m_VOL 		= 150.4304;
        double sd_VOL   	= 44.29;

        boolean in_UNITM=false;
        double in_LENGTH = 0;
        double in_GIRTH = 0;
        double in_SOFTL =0;
        double in_VOL=0;
        double perc_LENGTH =0;
        double perc_GIRTH =0;
        double perc_SOFTL =0;
        double perc_VOL =0;
        double room_LENGTH_L =0;
        double room_LENGTH_S=0;
        double room_GIRTH_L=0;
        double room_GIRTH_S=0;
        double room_SOFTL_L=0;
        double room_SOFTL_S =0;
        double room_VOL =0;
        double con_NOM=0;
        private void doCalc(JSONObject jsonObject) {
            String fName = "[doCalc]";
            logger.info(fName);
            try{
                in_LENGTH = 0;
                in_GIRTH = 0;
                in_SOFTL =0;
                if(jsonObject.has(keyUnit)){
                    in_UNITM=jsonObject.getBoolean(keyUnit);
                }
                if(jsonObject.has(keyErectLength)&&jsonObject.getDouble(keyErectLength)>0){
                    in_LENGTH=jsonObject.getDouble(keyErectLength);
                }
                if(jsonObject.has(keyGirth)&&jsonObject.getDouble(keyGirth)>0){
                    in_GIRTH=jsonObject.getDouble(keyGirth);
                }
                if(jsonObject.has(keySoftLength)&&jsonObject.getDouble(keySoftLength)>0){
                    in_SOFTL=jsonObject.getDouble(keySoftLength);
                }
                logger.info(fName+"in_UNITM="+in_UNITM);
                logger.info(fName+"in_LENGTH="+in_LENGTH);
                logger.info(fName+"in_GIRTH="+in_GIRTH);
                logger.info(fName+"in_SOFTL="+in_SOFTL);
                if(in_UNITM){
                    in_LENGTH *= 2.54; in_GIRTH *= 2.54; in_SOFTL *= 2.54; //inch multiplyer
                }

                logger.info(fName+"in_LENGTH="+in_LENGTH);
                logger.info(fName+"in_GIRTH="+in_GIRTH);
                logger.info(fName+"in_SOFTL="+in_SOFTL);

                in_VOL = in_GIRTH / (2 * Math.PI);
                in_VOL = in_LENGTH * Math.PI * (in_VOL * in_VOL);
                logger.info(fName+"in_VOL="+in_VOL);
                perc_LENGTH =calc_PERC(in_LENGTH, m_LENGTH, sd_LENGTH);
                perc_GIRTH = calc_PERC(in_GIRTH, m_GIRTH, sd_GIRTH);
                perc_SOFTL = calc_PERC(in_SOFTL, m_SOFTL, sd_SOFTL);
                perc_VOL = calc_PERC(in_VOL, m_VOL, sd_VOL);
                logger.info(fName+"perc_LENGTH="+perc_LENGTH);
                logger.info(fName+"perc_GIRTH="+ perc_GIRTH);
                logger.info(fName+"perc_SOFTL="+perc_SOFTL);
                logger.info(fName+"perc_VOL="+perc_VOL);
                room_LENGTH_L = 1000 - Math.round(perc_LENGTH * 1000);
                room_LENGTH_S = Math.round(perc_LENGTH * 1000);
                room_GIRTH_L = 1000 - Math.round(perc_GIRTH * 1000);
                room_GIRTH_S = Math.round(perc_GIRTH * 1000);
                room_SOFTL_L = 1000 - Math.round(perc_SOFTL * 1000);
                room_SOFTL_S = Math.round(perc_SOFTL * 1000);
                room_VOL = 1000 - Math.round(perc_VOL * 1000);
                logger.info(fName+"room_LENGTH_L="+room_LENGTH_L);
                logger.info(fName+"room_LENGTH_S="+ room_LENGTH_S);
                logger.info(fName+"room_GIRTH_L="+room_GIRTH_L);
                logger.info(fName+"room_GIRTH_S="+room_GIRTH_S);
                logger.info(fName+"room_SOFTL_L="+room_SOFTL_L);
                logger.info(fName+"room_SOFTL_S="+room_SOFTL_S);
                logger.info(fName+"room_VOL="+room_VOL);
                /*perc_LENGTH = Math.round(perc_LENGTH * 10000) / 100;
                perc_GIRTH = Math.round(perc_GIRTH * 10000) / 100;
                perc_SOFTL = Math.round(perc_SOFTL * 10000) / 100;
                perc_VOL = Math.round(perc_VOL * 10000) / 100;
                in_VOL = Math.round(in_VOL * 100) / 100;*/
                perc_LENGTH = getNumberWithDecimals(perc_LENGTH *100,2);
                perc_GIRTH = getNumberWithDecimals(perc_GIRTH*100,2);
                perc_SOFTL = getNumberWithDecimals(perc_SOFTL *100,2);
                perc_VOL = getNumberWithDecimals(perc_VOL*100,2);
                in_VOL = getNumberWithDecimals(in_VOL,2);

                logger.info(fName+" perc_LENGTH="+perc_LENGTH );
                logger.info(fName+"perc_GIRTH="+perc_GIRTH);
                logger.info(fName+"perc_SOFTL="+perc_SOFTL);
                logger.info(fName+"perc_VOL="+perc_VOL);
                logger.info(fName+"in_VOL="+in_VOL);

                double con_NOM = in_GIRTH * 10 / 2.37;
                con_NOM = Math.round(con_NOM * 1) / 1;
                logger.info(fName+"con_NOM="+con_NOM);
                createMessage();
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private double calc_PERC(double value, double mean, double sd) {
            String fName = "[calc_PERC]";
            logger.info(fName);
            try{
                logger.info(fName+"value="+value);
                logger.info(fName+"mean="+mean);
                logger.info(fName+"sd="+sd);
                value -= mean;
                logger.info(fName+"value-mean="+value);
                value /= sd;
                logger.info(fName+"value/sd="+value);
                value =GetZPercent(value);
                logger.info(fName+"out="+value);
                return value;
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        private double GetZPercent(double z) {
            String fName = "[GetZPercent]";
            logger.info(fName);
            try{
                logger.info(fName+"z="+z);
                if (z < -6.5) {return 0.0;}
                if (z > 6.5) {return 1.0;}
                double factK = 1;
                double sum = 0;
                double term = 1;
                double k = 0;
                double loopStop = Math.exp(-23);
                while (Math.abs(term) > loopStop) {
                    term = 0.3989422804 * Math.pow(-1,k) * Math.pow(z,k) / (2 * k + 1) / Math.pow(2,k) * Math.pow(z,k+1) / factK;
                    sum += term;
                    k++;
                    factK *= k;
                }
                sum += 0.5;
                logger.info(fName+"sum="+sum);
                return sum;
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }

        }
        String out_con_ADV ="",out_size_ADVL="",out_size_ADVG="",out_size_SL="",out_size_SG="",out_size_B="";
        String out_LENGTH_PERC="",out_LENGTH_ROOM="",out_LENGTH_90="",out_GIRTH_PERC="",out_GIRTH_ROOM="",out_GIRTH_90="",out_SOFTL_PERC="",out_SOFTL_ROOM="",out_SOFTL_90="",out_VOL_PERC="",out_VOL_ROOM="";
        private void createMessage() {
            String fName = "[createMessage]";
            logger.info(fName);
            try{
                //condom advice
                if (0 <in_GIRTH&&in_GIRTH < 11.509) { out_con_ADV =" Your results indicate that standard condoms may not be ideal.";}
                if (11.51 < in_GIRTH&&in_GIRTH < 12.69) {
                    out_con_ADV =" Your results indicate that many condoms probably fit ok.";}
                if (12.691 < in_GIRTH) { out_con_ADV =" Your results indicate that standard condoms may feel a bit tight.";}
                logger.info(fName+"strCondomAdvice="+ out_con_ADV);
                //size advice
                // concl
                if (0 < in_LENGTH&&in_LENGTH < 7.01) {out_size_ADVL= "Your penis is shorter (and you may wish to discuss this with your doctor) than most men ";}
                if (7.02 < in_LENGTH&&in_LENGTH < 11.03) {out_size_ADVL= "Your penis is a bit shorter than most men ";}
                if (11.04 < in_LENGTH&&in_LENGTH < 12.74) {out_size_ADVL= "Your penis is on the shorter side of the normal length range ";}
                if (12.75 < in_LENGTH&&in_LENGTH < 13.91) {out_size_ADVL= "Your penis is around the average length ";}
                if (13.92 < in_LENGTH&&in_LENGTH < 15.09) {out_size_ADVL= "Your penis is on the longer side of the normal length range ";}
                if (15.10 < in_LENGTH&&in_LENGTH < 16.79) {out_size_ADVL= "Your penis is longer than most men ";}
                if (16.8 < in_LENGTH) {out_size_ADVL= "Your penis is much longer than most men ";}
                if (0 < in_GIRTH&&in_GIRTH < 8.97) {out_size_ADVG= "and a bit thinner.";}
                if (8.98 < in_GIRTH&&in_GIRTH < 10.49) {out_size_ADVG= "and on the thinner side of the normal girth range.";}
                if (10.50 < in_GIRTH&&in_GIRTH < 11.54) {out_size_ADVG= "and around the average girth.";}
                if (11.55 < in_GIRTH&&in_GIRTH < 12.59) {out_size_ADVG= "and on the thicker side of the normal girth range.";}
                if (12.60 < in_GIRTH&&in_GIRTH < 14.11) {out_size_ADVG= "and thicker.";}
                if (14.12 < in_GIRTH) {out_size_ADVG= "and much thicker.";}
                // small?
                if (0 < in_LENGTH&&in_LENGTH < 7.01) {out_size_SL= "Your penis is shorter than most men and you may wish to discuss this with your doctor. ";}
                if (7.02 < in_LENGTH&&in_LENGTH < 11.03) {out_size_SL= "Your penis is a bit shorter than most men but this needn't be a problem. ";}
                if (11.04 < in_LENGTH&&in_LENGTH < 16.798) {out_size_SL= "Your penis is within the normal length range so you shouldn't be concerned about being small. ";}
                if (16.799 < in_LENGTH) {out_size_SL= "Your penis is much longer than most men so you shouldn't be concerned about being small. ";}
                if (0 < in_GIRTH&&in_GIRTH < 8.97) {out_size_SG= "It's also a bit thinner than most guys so you may want to look at different condoms. ";}
                if (8.98 < in_GIRTH&&in_GIRTH < 12.59) {out_size_SG= "It's also within the normal girth range so you shouldn't be concerned about being thin. ";}
                if (12.60 < in_GIRTH) {out_size_SG= "It's also thicker than most men so you shouldn't be concerned about being thin. ";}
                // big?
                if (16.798 < in_LENGTH&&in_GIRTH < 14.115) {out_size_B= "Your penis is much longer than most men. ";}
                if (14.115 < in_GIRTH&&in_LENGTH < 16.798) {out_size_B= "Your penis is much thicker than most men. ";}
                if (16.799 < in_LENGTH&&14.116 < in_GIRTH) {out_size_B= "Your penis is much longer and thicker than most men. ";}
                //	size calc
                // erect length
                if (in_LENGTH > 18.58) {out_LENGTH_PERC= "You are longer than " + perc_LENGTH + "% of guys"; out_LENGTH_ROOM= "In a room of 1000 guys it is unlikely any would be longer than you";}
                else if (in_LENGTH > m_LENGTH) {out_LENGTH_PERC= "You are longer than " + perc_LENGTH + "% of guys"; out_LENGTH_ROOM= "In a room of 1000 guys only " + getNumberStrWithZeroDecimals(room_LENGTH_L) + " would be longer than you";}
                else if (in_LENGTH != 0){out_LENGTH_PERC= "You are longer than " + perc_LENGTH + "% of guys"; out_LENGTH_ROOM= "In a room of 1000 guys " + getNumberStrWithZeroDecimals(room_LENGTH_S) + " would be shorter than you";}
                // 90%
                if (in_UNITM == false&&0 < in_LENGTH&&in_LENGTH < 11.03) {out_LENGTH_90= "90% of guys will be between " + getNumberStrWithTwoDecimals(11.04 - in_LENGTH) +" and " +getNumberStrWithTwoDecimals(16.8 - in_LENGTH) +"cm longer than you";}
                if (in_UNITM == true&&0 < in_LENGTH&&in_LENGTH < 11.03) {out_LENGTH_90= "90% of guys will be between " + getNumberStrWithTwoDecimals(4.35 - in_LENGTH/2.54) +"\" and " +getNumberStrWithTwoDecimals(6.61 - in_LENGTH/2.54) +"\" longer than you";}
                if (in_UNITM == false&&11.04 < in_LENGTH&&in_LENGTH < 13.91) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH - 11.04) +"cm shorter or " +getNumberStrWithTwoDecimals(16.8 - in_LENGTH) +"cm longer than you";}
                if (in_UNITM == true&&11.04 < in_LENGTH&&in_LENGTH < 13.91) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH/2.54 - 4.35) +"\" shorter or " +getNumberStrWithTwoDecimals(6.61 - in_LENGTH/2.54) +"\" longer than you";}
                if (in_UNITM == false&&13.92 < in_LENGTH&&in_LENGTH < 16.79) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH - 11.04) +"cm shorter or " +getNumberStrWithTwoDecimals(16.8 - in_LENGTH) +"cm longer than you";}
                if (in_UNITM == true&&13.92 < in_LENGTH&&in_LENGTH < 16.79) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH/2.54 - 4.35) +"\" shorter or " +getNumberStrWithTwoDecimals(6.61 - in_LENGTH/2.54) +"\" longer than you";}
                if (in_UNITM == false&&16.8 < in_LENGTH) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH - 16.8) +" and " +getNumberStrWithTwoDecimals(in_LENGTH - 11.04) +"cm shorter than you";}
                if (in_UNITM == true&&16.8 < in_LENGTH) {out_LENGTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_LENGTH/2.54 - 6.61) +"\" and " +getNumberStrWithTwoDecimals(in_LENGTH/2.54 - 4.35) +"\" shorter than you";}
                // erect girth
                if (in_GIRTH > 15.26) {out_GIRTH_PERC= "You are thicker than " + perc_GIRTH + "% of guys"; out_GIRTH_ROOM= "In a room of 1000 guys it is unlikely any would be thicker than you";}
                else if (in_GIRTH > m_GIRTH) {out_GIRTH_PERC= "You are thicker than " + perc_GIRTH + "% of guys"; out_GIRTH_ROOM= "In a room of 1000 guys only " + getNumberStrWithZeroDecimals(room_GIRTH_L) + " would be thicker than you";}
                else if (in_GIRTH !=0) {out_GIRTH_PERC= "You are thicker than " + perc_GIRTH + "% of guys"; out_GIRTH_ROOM= "In a room of 1000 guys " + getNumberStrWithZeroDecimals(room_GIRTH_S) + " would be thinner than you";}
                // 90%
                if (in_UNITM == false&&0 < in_GIRTH&&in_GIRTH < 8.97) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(8.98 - in_GIRTH) +" and " +getNumberStrWithTwoDecimals(14.12 - in_GIRTH) +"cm thicker than you";}
                if (in_UNITM == true&&0 < in_GIRTH&&in_GIRTH < 8.97) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(3.54 - in_GIRTH/2.54) +"\" and " +getNumberStrWithTwoDecimals(5.56 - in_GIRTH/2.54) +"\" thicker than you";}
                if (in_UNITM == false&&8.98 < in_GIRTH&&in_GIRTH < 11.54) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH - 8.98) +"cm thinner or " +getNumberStrWithTwoDecimals(14.12 - in_GIRTH) +"cm thicker than you";}
                if (in_UNITM == true&&8.98 < in_GIRTH&&in_GIRTH < 11.54) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH/2.54 - 3.54) +"\" thinner or " +getNumberStrWithTwoDecimals(5.56 - in_GIRTH/2.54) +"\" thicker than you";}
                if (in_UNITM == false&&11.55 < in_GIRTH&&in_GIRTH < 14.11) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH - 8.98) +"cm thinner or " +getNumberStrWithTwoDecimals(14.12 - in_GIRTH) +"cm thicker than you";}
                if (in_UNITM == true&&11.55 < in_GIRTH&&in_GIRTH < 14.11) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH/2.54 - 3.54) +"\" thinner or " +getNumberStrWithTwoDecimals(5.56 - in_GIRTH/2.54) +"\" thicker than you";}
                if (in_UNITM == false&&14.12 < in_GIRTH) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH - 14.12) +" and " +getNumberStrWithTwoDecimals(in_GIRTH - 8.98) +"cm thinner than you";}
                if (in_UNITM == true&&14.12 < in_GIRTH) {out_GIRTH_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_GIRTH/2.54 - 5.56) +"\" and " +getNumberStrWithTwoDecimals(in_GIRTH/2.54 - 3.54) +"\" thinner than you";}
                // soft length
                if (in_SOFTL > 14.32) {out_SOFTL_PERC= "You are longer than " + perc_SOFTL + "% of guys"; out_SOFTL_ROOM= "In a room of 1000 guys it is unlikely any would be longer than you";}
                else if (in_SOFTL > m_SOFTL) {out_SOFTL_PERC= "You are longer than " + perc_SOFTL + "% of guys"; out_SOFTL_ROOM= "In a room of 1000 guys only " + getNumberStrWithZeroDecimals(room_SOFTL_L) + " would be longer than you";}
                else if (in_SOFTL !=0) {out_SOFTL_PERC= "You are longer than " + perc_SOFTL + "% of guys"; out_SOFTL_ROOM= "In a room of 1000 guys " + getNumberStrWithZeroDecimals(room_SOFTL_S) + " would be shorter than you";}
                // 90%
                if (in_UNITM == false&&0 < in_SOFTL&&in_SOFTL < 6.57) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(6.58 - in_SOFTL) +" and " +getNumberStrWithTwoDecimals(11.74 - in_SOFTL) +"cm longer than you";}
                if (in_UNITM == true&&0 < in_SOFTL&&in_SOFTL < 6.57) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(2.59 - in_SOFTL/2.54) +"\" and " +getNumberStrWithTwoDecimals(4.62 - in_SOFTL/2.54) +"\" longer than you";}
                if (in_UNITM == false&&6.58 < in_SOFTL&&in_SOFTL < 9.15) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL - 6.58) +"cm shorter or " +getNumberStrWithTwoDecimals(11.74 - in_SOFTL) +"cm longer than you";}
                if (in_UNITM == true&&6.58 < in_SOFTL&&in_SOFTL < 9.15) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL/2.54 - 2.59) +"\" shorter or " +getNumberStrWithTwoDecimals(4.62 - in_SOFTL/2.54) +"\" longer than you";}
                if (in_UNITM == false&&9.16 < in_SOFTL&&in_SOFTL < 11.73) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL - 6.58) +"cm shorter or " +getNumberStrWithTwoDecimals(11.74 - in_SOFTL) +"cm longer than you";}
                if (in_UNITM == true&&9.16 < in_SOFTL&&in_SOFTL < 11.73) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL/2.54 - 2.59) +"\" shorter or " +getNumberStrWithTwoDecimals(4.62 - in_SOFTL/2.54) +"\" longer than you";}
                if (in_UNITM == false&&11.74 < in_SOFTL) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL - 11.74) +" and " +getNumberStrWithTwoDecimals(in_SOFTL - 6.58) +"cm shorter than you";}
                if (in_UNITM == true&&11.74 < in_SOFTL) {out_SOFTL_90= "90% of guys will be between " +getNumberStrWithTwoDecimals(in_SOFTL/2.54 - 4.62) +"\" and " +getNumberStrWithTwoDecimals(in_SOFTL/2.54 - 2.59) +"\" shorter than you";}
                // 	volume
                if (in_LENGTH != 0&&in_GIRTH != 0) {out_VOL_PERC= "With a volume of " + in_VOL + " ml / " + Math.round(in_VOL * 0.033814 * 100) / 100 + " fl oz (US), you are " + Math.round(in_VOL / m_VOL * 10) / 10 + " times more voluminous than the average"; out_VOL_ROOM= "This means you are bigger than " + perc_VOL + "% of guys by volume and in a room of 1000 guys " + getNumberStrWithZeroDecimals(room_VOL) + " would be bigger than you";}

                logger.info(fName+"out_con_ADV="+out_con_ADV);
                logger.info(fName+"out_size_ADVL="+out_size_ADVL);
                logger.info(fName+"out_size_ADVG="+out_size_ADVG);
                logger.info(fName+"out_size_SL="+out_size_SL);
                logger.info(fName+"out_size_SG="+out_size_SG);
                logger.info(fName+"out_size_B="+out_size_B);

                logger.info(fName+"out_LENGTH_PERC="+out_LENGTH_PERC);
                logger.info(fName+"out_LENGTH_ROOM="+out_LENGTH_ROOM);
                logger.info(fName+"out_LENGTH_90="+out_LENGTH_90);
                logger.info(fName+"out_GIRTH_PERC="+out_GIRTH_PERC);
                logger.info(fName+"out_GIRTH_ROOM="+out_GIRTH_ROOM);
                logger.info(fName+"out_GIRTH_90="+out_GIRTH_90);
                logger.info(fName+"out_SOFTL_PERC="+out_SOFTL_PERC);
                logger.info(fName+"out_SOFTL_ROOM="+out_SOFTL_ROOM);
                logger.info(fName+"out_SOFTL_90="+out_SOFTL_90);
                logger.info(fName+"out_VOL_PERC="+out_VOL_PERC);
                logger.info(fName+"out_VOL_ROOM="+out_VOL_ROOM);

                //condom http://sizemeup.info/scripts/calc_CON_EU.js?_=1590417378749
                 postResponse();
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }
        private void postResponse() {
            String fName = "[postResponse]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle);
                embedBuilder.setAuthor(gUser.getName()+"#"+gUser.getDiscriminator(),null,gUser.getEffectiveAvatarUrl());
                embedBuilder.addField("OVERALL",out_size_ADVL+" "+out_size_ADVG,false);
                embedBuilder.addField("ERECT LENGTH",out_LENGTH_PERC+"\n"+out_LENGTH_ROOM+"\n"+out_LENGTH_90,false);
                embedBuilder.addField("ERECT GIRTH",out_GIRTH_PERC+"\n"+out_GIRTH_ROOM+"\n"+out_GIRTH_90,false);
                embedBuilder.addField("FLACCID LENGTH",out_SOFTL_PERC+"\n"+out_SOFTL_ROOM+"\n"+out_SOFTL_90,false);
                embedBuilder.addField("ERECT VOLUME",out_VOL_PERC+"\n"+out_VOL_ROOM,false);
                embedBuilder.addField("CONDOM",out_con_ADV,false);
                llSendMessageWithDelete(gGlobal,gTextChannel,embedBuilder);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private double getNumberWithDecimals(double value,int places) {
            String fName = "[getNumberWithDecimals]";
            logger.info(fName);
            try{
                logger.info(fName+"in="+value);
                logger.info(fName+"places="+places);
                if (places < 0) throw new IllegalArgumentException();
                long factor = (long) Math.pow(10, places);
                value = value * factor;
                long tmp = Math.round(value);
                double d=(double) tmp / factor;
                logger.info(fName+"out="+d);
                return d;
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        private String  getNumberStrWithTwoDecimals(double value) {
            String fName = "[getNumberStrWithTwoDecimals]";
            logger.info(fName);
            try{
                return String.valueOf( getNumberWithDecimals(value,2));
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return "#";
            }
        }
        private String  getNumberStrWithZeroDecimals(double value) {
            String fName = "[getNumberStrWithZeroDecimals]";
            logger.info(fName);
            try{
                return String.valueOf( getNumberWithDecimals(value,0));
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return "#";
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
                    List<Long> list=gBasicFeatureControl.getAllowedChannelsAsLong();
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
                    List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
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
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
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
                    List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<TextChannel>textChannels=message.getMentionedChannels();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        List<Role>roles=message.getMentionedRoles();
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
                        },5, TimeUnit.MINUTES, () -> {
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
