package test;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.interaction.applicationcommand.lcApplicationCCommonBuilder;
import models.lc.interaction.applicationcommand.lcApplicationCCommonEditor;
import models.lc.interaction.applicationcommand.lcApplicationCommandBuild;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class test3_Application extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  llCommonKeys {
    String cName="[testApplication]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter; String gTitle="testApplication";
    Logger logger = Logger.getLogger(getClass());
    public test3_Application(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "test-testApplication";
        this.help = "testApplication";
        this.aliases = new String[]{"testApplication","test3"};
        this.guildOnly = true;
        this.hidden=true;
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
       
        public runLocal(CommandEvent ev) {
            logger.info(".run build");
            gEvent = ev;
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            boolean isInvalidCommand = true;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if(gEvent.getArgs().isEmpty()){
                logger.info(cName+fName+".Args=0");
                help("main");isInvalidCommand=false;
            }else {
                logger.info(fName + ".Args");
                String []items = gEvent.getArgs().split("\\s+");
                if(items[0].equalsIgnoreCase("help")){
                    help( "main");isInvalidCommand=false;
                }else
                if(items[0].equalsIgnoreCase("1")){
                    test1();isInvalidCommand=false;
                }
                if(items[0].equalsIgnoreCase("2")){
                    test2();isInvalidCommand=false;
                }

            }
            logger.info(cName+fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
            }
        }
        
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"test1 ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.setDescription("`channelpermission`");
            llSendMessage(gUser,embed);
        }
        String gFilesPath="resources/json/test/";
        lcText2Json text2Json=new lcText2Json();
        lcApplicationCommandBuild applicationCommandBuild=new lcApplicationCommandBuild(gGlobal);
        lcApplicationCCommonBuilder applicationCCommonBuilder=new lcApplicationCCommonBuilder(gGlobal);
        private void test1(){
            String fName="[test1]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle("TestApplication");
                File file1;
                InputStream fileStream=null;
                String pathfile=gFilesPath+"test1.json";
                try {
                    logger.info(fName+"path="+pathfile);
                    file1=new File(pathfile);
                    if(file1.exists()){
                        logger.info(fName+".file1 exists");
                        fileStream = new FileInputStream(file1);
                    }else{
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","File does not exists");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
                    return;
                }
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Failed to load");
                        return;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Json is empty");
                    return;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
                applicationCommandBuild=new lcApplicationCommandBuild(gGlobal,text2Json.jsonObject,gGuild);
                applicationCommandBuild.post();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }
        private void test2(){
            String fName="[test2]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle("TestApplication");
                File file1;
                InputStream fileStream=null;
                String pathfile=gFilesPath+"test1.json";
                try {
                    logger.info(fName+"path="+pathfile);
                    file1=new File(pathfile);
                    if(file1.exists()){
                        logger.info(fName+".file1 exists");
                        fileStream = new FileInputStream(file1);
                    }else{
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","File does not exists");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
                    return;
                }
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Failed to load");
                        return;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Json is empty");
                    return;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
                applicationCCommonBuilder.setGuild(gGuild);
                applicationCCommonBuilder.set(text2Json.jsonObject);
                //applicationCCommonBuilder.setGuild(gGuild);
                //applicationCCommonBuilder.post();
                logger.info(fName + ".applicationCCommonBuilder.getJson=" + applicationCCommonBuilder.getJson4Build());
                switch (applicationCCommonBuilder.getType()){
                    case CHAT_INPUT:
                        applicationCCommonBuilder.setName(applicationCCommonBuilder.getName()+"2c");
                        break;
                    case MESSAGE:
                        applicationCCommonBuilder.setName(applicationCCommonBuilder.getName()+"2m");
                        break;
                    case USER:
                        applicationCCommonBuilder.setName(applicationCCommonBuilder.getName()+"2u");
                        break;
                }
                lcApplicationCCommonEditor applicationCCommonEditor=applicationCCommonBuilder.post();
                if(applicationCCommonEditor==null){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Failed to post!");
                    return;
                }
                applicationCCommonEditor.setName(applicationCCommonBuilder.getName()+"e");
                if(!applicationCCommonEditor.patch()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test","Failed to patch!");
                    return;
                }
                String desc="";
                desc+="id: "+applicationCCommonEditor.getId();
                desc+="\nid: "+applicationCCommonEditor.getName();
                embedBuilder.setDescription(desc).setColor(llColorBlue1);
                gTextChannel.sendMessageEmbeds(embedBuilder.build()).complete();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,"Test",e.toString());
            }
        }

    
  //runLocal  
    }
}
