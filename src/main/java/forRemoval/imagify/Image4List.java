package forRemoval.imagify;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Image4List extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gTitle="Image4List<removed>",gCommand="image4list";
    public Image4List(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "<removed>";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_UtilityInHouse;
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
        CommandEvent gEvent;
        GuildMessageReceivedEvent gGuildMessageReceivedEvent;
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
        Member gTarget;
        boolean gIsOverride=false;

        public runLocal(CommandEvent ev) {
            String fName="build";logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(GuildMessageReceivedEvent event) {
            String fName="build";logger.info(".run build");
            gGuildMessageReceivedEvent = event;
            gUser = gGuildMessageReceivedEvent.getAuthor();gMember=gGuildMessageReceivedEvent.getMember();
            gGuild = gGuildMessageReceivedEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gGuildMessageReceivedEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            String[] items;
            Boolean isInvalidCommand = true;
            try{
                if(gGuildMessageReceivedEvent!=null){
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw().replaceFirst(llPrefixStr,"");
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    items = args.split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items.length>=3&&isTargeted(items[1])){

                    }else{
                        if(!isNSFW()){
                            blocked();isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("bondage")){
                            doSelection("bondage");
                        }

                    }
                }else{
                    if(!isNSFW()){
                        blocked();
                        return;
                    }
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");
                        gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=true;
                        isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);

                        if(items.length>=3&&isTargeted(items[1])){

                        }else{
                            if(items[0].equalsIgnoreCase("help")){
                                help("main");isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("bondage")){
                                doSelection("bondage");isInvalidCommand=false;
                            }

                        }
                    }
                    logger.info(fName+".deleting op message");
                    lsMessageHelper.lsMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }
            catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
            logger.info(".run ended");
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
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel.",llColorRed);
            logger.info(fName);
        }
        private Boolean isTargeted(String command){
            String fName = "[isTargeted]";
            logger.info(fName);
            try{
                logger.info(fName + ".command=" + command);
                if((command.contains("<@")&&command.contains(">"))||(command.contains("<@!")&&command.contains(">"))){
                    String tmp=command.replace("<@!","").replace("<@","").replace(">","");
                    Member m=gGuild.getMemberById(tmp);
                    if(m!=null){
                        if(m.getId().equals(gUser.getId())){
                            logger.info(fName + ".target same");
                            return false;
                        }
                        logger.info(fName + ".target ok");
                        gTarget=m;
                        return true;
                    }
                }
                logger.info(fName + ".target none");
                return false;
            }
            catch(Exception ex){
                logger.error(fName + ".exception: "+ex);
                return false;
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String desc="N/a";
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            embed.addField("Commands","`"+quickSummonWithSpace+" [bondage]` to view an event with image.",false);
            llSendMessage(gUser,embed);
        }
        String gUrlMainSoloPath="resources/json/nsfw/imagevent/solo";
        lcText2Json text2Json=null;
        String keyE621="e621",keyFA="fa",keyText="text",keyInkbunny="ibunny";
        private void doSelection(String name) {
            String fName="[doSelection]";
            logger.info(fName);
            try {
                logger.info(fName+"name="+name);
                if(!readFile(name)){
                    logger.warn(fName+".failed to load");
                    return;
                }
                int random= lsUsefullFunctions.getRandom(text2Json.jsonArray.length());
                logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                int count=0;
                while((random<0||text2Json.jsonArray.length()<=random)&&count<50){
                    random=lsUsefullFunctions.getRandom(text2Json.jsonArray.length());count++;
                    logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                }
                if(random<0||text2Json.jsonArray.length()<=random){
                    logger.warn(fName+".invalid random");
                    return;
                }
                JSONObject jsonObject=text2Json.jsonArray.getJSONObject(random);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                if(!jsonObject.has(keyText)){
                    logger.warn(fName+".keyText is missing");return;
                }
                if(jsonObject.isNull(keyText)){
                    logger.warn(fName+".keyText is null");return;
                }

                if(jsonObject.has(keyE621)&&!jsonObject.isNull(keyE621)&&!jsonObject.getString(keyE621).isBlank()){
                    logger.info(fName+".keyE621 found");
                    doPreviewE621(jsonObject.getString(keyE621));

                }
                else if(jsonObject.has(keyFA)&&!jsonObject.isNull(keyFA)&&!jsonObject.getString(keyFA).isBlank()){
                    logger.info(fName+".keyFA found");
                    doPreviewFA(jsonObject.getString(keyFA));
                }
                else if(jsonObject.has(keyInkbunny)&&!jsonObject.isNull(keyInkbunny)&&!jsonObject.getString(keyInkbunny).isBlank()){
                    logger.info(fName+".keyInkBunny found");
                    doPreviewInkbunny(jsonObject.getString(keyInkbunny));
                }
                else{
                    logger.warn(fName+"keys missing or null");
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

        private void doPreviewE621(String viewId) {
            String fName="[doPreviewE621]";
            logger.info(fName);
            try {
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"This feature is deprecated!");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void doPreviewFA(String viewId) {
            String fName = "[doPreviewFA]";
            try {
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"This feature is deprecated!");
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void doPreviewInkbunny(String viewId) {
            String fName = "[doPreviewInkbunny]";
            try {
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"This feature is deprecated!");
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void deleteOption(Message message) {
            String fName="[deleteOption]";
            logger.info(fName);
            try {
                try {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("bomb"));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.info(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                    logger.info(fName+"do=delete");
                                    lsMessageHelper.lsMessageDelete(message);
                                }else{
                                    logger.info(fName+"do=invalid");
                                    deleteOption(message);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageClearReactionsQueue(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean readFile(String name) {
            String fName="[readFile]";
            logger.info(fName);
            try {
                File file1, file2;
                InputStream fileStream=null;
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".txt");
                    file1=new File(gUrlMainSoloPath+"/"+name+".txt");
                    if(file1.exists()){
                        fileStream = new FileInputStream(file1);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                }
                try {
                    logger.info(fName+"path="+gUrlMainSoloPath+"/"+name+".json");
                    file2=new File(gUrlMainSoloPath+"/"+name+".json");
                    if(file2.exists()){
                        fileStream = new FileInputStream(file2);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                text2Json=new lcText2Json();
                if(fileStream!=null){
                    if(!text2Json.isInputStream2Json(fileStream)){
                        logger.warn(fName+".failed to load");
                        return false;
                    }else{
                        logger.info(fName+".loaded from file");
                    }
                }
                if(text2Json.jsonArray.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName+"length="+text2Json.jsonArray.length());
                logger.info(fName + ".text2Json.jsonArray=" + text2Json.jsonArray.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }


    }
}
