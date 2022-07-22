package forRemoval.social.NSFW;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import forRemoval.social.lcSocialization;
import forRemoval.social.llSocialization;

import java.util.Arrays;
import java.util.List;

public class nKeyTease extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llSocialization {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="nkey", gTitle="KeyTease";
    String localPath="resources/image";
    String fileName="keytease.txt";
    public nKeyTease(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle+" Social";
        this.help = "Teasing the sub with their key to their cage.";
        this.aliases = new String[]{KeyTag};
        this.guildOnly = true;this.category= llCommandCategory_SocialNSFW;
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
                        doAction(target);
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
            String desc="`"+llPrefixStr+KeyTag+"+ @User` or `"+llPrefixStr+KeyTag+" @User <index>`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorBlue1);
        }
        private void noMention() {
            String fName = "[noMention]";
            logger.info(cName + fName);
            String desc="`You need to mention somebody like `"+llPrefixStr+"+boop @User` or `"+llPrefixStr+"boop @User <index>`";
            llSendQuickEmbedMessage(gTextChannel,gTitle,desc, llColorRed);
        }
        private void doAction(Member target) {
            String fName = "[doAction]";
            logger.info(cName + fName);
            try{
                logger.info(fName+"target="+target.getId());
                JSONArray jsonArray=llGetJsonArray4Text(localPath,fileName);
                int size=jsonArray.length();
                logger.info(fName+"size="+size);
                if(size==0){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Is isEmpty", llColorRed);
                    return;
                }
                int i=lsUsefullFunctions.getRandom(size);
                logger.info(fName+"i="+i);
                String desc="N/A";
                EmbedBuilder embed=new EmbedBuilder();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.has(keyText)&&!jsonObject.isNull(keyText)&&!jsonObject.getString(keyText).isEmpty()){
                    logger.info(fName+"text="+desc);
                    desc=jsonObject.getString(keyText).replaceAll("%author",gMember.getAsMention()).replaceAll("%target",target.getAsMention());;
                }
                if(jsonObject.has(keyPage)&&!jsonObject.isNull(keyPage)&&!jsonObject.getString(keyPage).isEmpty()){
                    logger.info(fName+"source="+jsonObject.getString(keyText));
                    desc+="\n[Source]("+jsonObject.getString(keyText)+")";
                }
                if(jsonObject.has(keySrc)&&!jsonObject.isNull(keySrc)&&!jsonObject.getString(keySrc).isEmpty()){
                    logger.info(fName+"source="+jsonObject.getString(keySrc));
                    embed.setImage(jsonObject.getString(keySrc));
                }
                embed.setDescription(desc);
                llSendMessage(gTextChannel,embed);
            }
            catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception: "+e, llColorRed);
            }
        }




    }

}
