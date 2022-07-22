package fun;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.la.aBasicCommandHandler;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUserHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class encodePoster extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    //https://stackoverflow.com/questions/26927419/zalgo-text-in-java
    Logger logger = Logger.getLogger(getClass()); String cName="[encodePoster]";String gCommand ="encode", gTitle="Encoder-Text";
    lcGlobalHelper gGlobal= lsGlobalHelper.sGetGlobal();
    public encodePoster(){
        String fName="[constructor]";
        logger.info(cName+fName);
        this.name = gTitle;
        this.help = "Post text encoded";
        this.aliases = new String[]{gCommand,"text2encode"};
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
            logger.info(".run build");
            setCommandHandlerValues(logger,event);
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                buildBasicFeatureControl(logger,"chuckNorris_Joke",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help( "main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
                        return;
                    }
                    else if(ifItsAnAccessControlCommand()){
                        logger.info(fName+"its an AccessControlCommand");
                        return;
                    }
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                        return;
                    }
                    switch (items[0].toLowerCase()){
                        case "hex2dm":
                            ask4TextAsHex2DM( );isInvalidCommand=false;
                            break;
                        case "hex":
                            ask4TextAsHex( );isInvalidCommand=false;
                            break;
                        case "bin2dm":
                            ask4TextAsBinary2DM( );isInvalidCommand=false;
                            break;
                        case "bin":
                            ask4TextAsBinary( );isInvalidCommand=false;
                            break;
                        case "binblocks2dm":
                            ask4TextAsBinaryBlocks2DM( );isInvalidCommand=false;
                            break;
                        case "binblocks":
                            ask4TextAsBinaryBlocks( );isInvalidCommand=false;
                            break;
                        case "base64dm":
                            ask4TextAsBase64ToDM( );isInvalidCommand=false;
                            break;
                        case "base64":
                            ask4TextAsBase64();isInvalidCommand=false;
                            break;
                        default:
                            help( "main");isInvalidCommand=false;
                            break;
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
            embed.addField("Hex","Convert text to hex.\n`"+quickSummonWithSpace+"hex`, will open a DM to ask for text and will post it to server.\n`"+quickSummonWithSpace+"hex2dm`, same just to your DM.",false);
            embed.addField("Binary","Convert text to binary.\n`"+quickSummonWithSpace+"bin`, will open a DM to ask for text and will post it to server.\n`"+quickSummonWithSpace+"bin2dm`, same just to your DM.",false);
            embed.addField("Binary Blocks","Convert text to binary and separates them ito blocks.\n`"+quickSummonWithSpace+"binblocks`, will open a DM to ask for text and will post it to server.\n`"+quickSummonWithSpace+"binblocks2dm`, same just to your DM.",false);
            embed.addField("Base64","Convert text to base64.\n`"+quickSummonWithSpace+"base64`, will open a DM to ask for text and will post it to server.\n`"+quickSummonWithSpace+"base64dm`, same just to your DM.",false);

            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private void ask4TextAsHex2DM( ) {
            String fName = "[ask4TextAsHex]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as hex or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content); llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            String hexText=lsStringUsefullFunctions.string2Hex(content);
                            logger.info(fName+"hexText="+hexText);
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"String 2 Hex:\n"+hexText,llColors.llColorBlue2);
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
        private void ask4TextAsHex( ) {
            String fName = "[ask4TextAsHex]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as hex or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            postMessageAsHex(content);
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
        private void postMessageAsHex(String source) {
            String fName = "[postMessageAsHex]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            logger.info(fName+"source="+source);
            String hexText=lsStringUsefullFunctions.string2Hex(source);
            logger.info(fName+"hexText="+hexText);
            try {
                logger.info(fName + ".sendwebhook");
                ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,hexText);
                if(readonlyMessage==null){
                    logger.info(fName + ".send embed message");
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setDescription(hexText);
                    embedBuilder.setColor(llColorPurple2);
                    embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                    if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                        logger.info(fName + ".send normal message");
                        lsMessageHelper.lsSendMessageResponse(gTextChannel,hexText);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName + ".send normal message");
                lsMessageHelper.lsSendMessage(gTextChannel,hexText);
            }
        }
        private void ask4TextAsBinary2DM( ) {
            String fName = "[ask4TextAsBinary]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as binary or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content); llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            String binaryText=lsStringUsefullFunctions.string2Binary(content);
                            logger.info(fName+"binaryText="+binaryText);
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"String 2 Binary:\n"+binaryText,llColors.llColorBlue2);
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
        private void ask4TextAsBinary( ) {
            String fName = "[ask4TextAsBinary]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as binary or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            postMessageAsBinary(content);
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
        private void postMessageAsBinary(String source) {
            String fName = "[postMessageAsBinary]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            logger.info(fName+"source="+source);
            String binaryText=lsStringUsefullFunctions.string2Binary(source);
            logger.info(fName+"binaryText="+binaryText);
            try {
                logger.info(fName + ".sendwebhook");
                ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,binaryText);
                if(readonlyMessage==null){
                    logger.info(fName + ".send embed message");
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setDescription(binaryText);
                    embedBuilder.setColor(llColorPurple2);
                    embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                    if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                        logger.info(fName + ".send normal message");
                        lsMessageHelper.lsSendMessageResponse(gTextChannel,binaryText);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName + ".send normal message");
                lsMessageHelper.lsSendMessage(gTextChannel,binaryText);
            }
        }
        private void ask4TextAsBinaryBlocks2DM( ) {
            String fName = "[ask4TextAsBinaryBlocks]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as binary or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content); llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            String binaryText=lsStringUsefullFunctions.string2BinaryBlocksAsString(content,8," ");
                            logger.info(fName+"binaryText="+binaryText);
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"String 2 Binary:\n"+binaryText,llColors.llColorBlue2);
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
        private void ask4TextAsBinaryBlocks( ) {
            String fName = "[ask4TextAsBinaryBlocks]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as binary or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            postMessageAsBinaryBlocks(content);
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
        private void postMessageAsBinaryBlocks(String source) {
            String fName = "[postMessageAsBinaryBlocks]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            logger.info(fName+"source="+source);
            String binaryText=lsStringUsefullFunctions.string2BinaryBlocksAsString(source,8," ");
            logger.info(fName+"binaryText="+binaryText);
            try {
                logger.info(fName + ".sendwebhook");
                ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,binaryText);
                if(readonlyMessage==null){
                    logger.info(fName + ".send embed message");
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setDescription(binaryText);
                    embedBuilder.setColor(llColorPurple2);
                    embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                    if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                        logger.info(fName + ".send normal message");
                        lsMessageHelper.lsSendMessageResponse(gTextChannel,binaryText);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName + ".send normal message");
                lsMessageHelper.lsSendMessage(gTextChannel,binaryText);
            }
        }
        private void ask4TextAsBase64ToDM( ) {
            String fName = "[ask4TextAsBase64]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as base64 or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content); llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            String base64Text=lsStringUsefullFunctions.string2Base64(content);
                            logger.info(fName+"base64Text="+base64Text);
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"String 2 Base64:\n"+base64Text,llColors.llColorBlue2);
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
        private void ask4TextAsBase64( ) {
            String fName = "[ask4TextAsBase64]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            lsMessageHelper.lsMessageDelete(gEvent.getMessage());
            Message message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the text you want to post as base64 or type `!cancel`.", llColorBlue1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);llMessageDelete(message);
                            if(content.equalsIgnoreCase("!cancel")){
                                return;
                            }
                            postMessageAsBase64(content);
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
        private void postMessageAsBase64(String source) {
            String fName = "[postMessageAsBinaryBlocks]";
            logger.info(fName);
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            logger.info(fName+"source="+source);
            String base64Text=lsStringUsefullFunctions.string2Base64(source);
            logger.info(fName+"base64Text="+base64Text);
            try {
                logger.info(fName + ".sendwebhook");
                ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,base64Text);
                if(readonlyMessage==null){
                    logger.info(fName + ".send embed message");
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setDescription(base64Text);
                    embedBuilder.setColor(llColorPurple2);
                    embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                    if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                        logger.info(fName + ".send normal message");
                        lsMessageHelper.lsSendMessageResponse(gTextChannel,base64Text);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName + ".send normal message");
                lsMessageHelper.lsSendMessage(gTextChannel,base64Text);
            }
        }


        //https://yaytext.com/monospace/
        //https://qwerty.dev/zalgo-text-generator/
        //"𝕿𝖊𝖘𝖙""𝚃𝚎𝚜𝚝""Test""𝕋𝕖𝕤𝕥"

    }

}
