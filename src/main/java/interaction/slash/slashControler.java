package interaction.slash;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import holidays.naughtypresents.NaughtyPresents;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lc.interaction.slash.lcAPPLICATION_COMMAND_CREATE;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.diaper.*;
import nsfw.verify.lewdverify;
import nsfw.lovense.Lovense;
import org.apache.log4j.Logger;
import restraints.*;
import search.furaffinity;
import util.HelpCommand;
import userprofiles.fursona;
import userprofiles.nfursona;
import userprofiles.character;
import userprofiles.ncharacter;
import userprofiles.Patient;
import userprofiles.nPatient;
import util.utilityPrivacy;
import util.utilityUpdateNotification;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class slashControler extends Command implements llMessageHelper, llGlobalHelper {
    String cName="[slashControler]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gTitle="slashControler", gCommand="slashControler";
    public slashControler(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = gTitle;
        this.help = "slashRD";
        this.aliases = new String[]{gCommand};
        this.guildOnly = true;
        this.hidden=true;
    }

    public slashControler(lcGlobalHelper g, lcSlashInteractionReceive event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    public slashControler(lcGlobalHelper g, lcAPPLICATION_COMMAND_CREATE event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    public slashControler(lcGlobalHelper g, SlashCommandEvent event){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        if(gGlobal==null)logger.error("global is null at slash controller 0");
        Runnable r = new runLocal(event);new Thread(r).start();
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
        lcSlashInteractionReceive gInteractioncreate;lcAPPLICATION_COMMAND_CREATE gAPPLICATION_COMMAND_CREATE;
        SlashCommandEvent gSlashCommandEvent;
        public runLocal(CommandEvent ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(lcSlashInteractionReceive ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gInteractioncreate = ev;
            gUser = gInteractioncreate.getUser();gMember=gInteractioncreate.getMember();
            gGuild = gInteractioncreate.getGuild();
            gTextChannel =gInteractioncreate.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(gGuild!=null)logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            if(gTextChannel!=null)logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(lcAPPLICATION_COMMAND_CREATE ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gAPPLICATION_COMMAND_CREATE = ev;
            gUser = gAPPLICATION_COMMAND_CREATE.getAuthor();gMember=gAPPLICATION_COMMAND_CREATE.getMember();
            gGuild = gAPPLICATION_COMMAND_CREATE.getGuild();
            gTextChannel =gAPPLICATION_COMMAND_CREATE.getChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(SlashCommandEvent ev) {
            String fName="runLoccal";
            logger.info(".run build");
            gSlashCommandEvent = ev;
            gUser = gSlashCommandEvent.getUser();gMember=gSlashCommandEvent.getMember();
            gGuild = gSlashCommandEvent.getGuild();
            gTextChannel =gSlashCommandEvent.getTextChannel();
            logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            if(gGuild!=null)logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            if(gTextChannel!=null)logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            try {
                if(gSlashCommandEvent!=null){
                    jdaSlashHandler();

                }else
                if(gAPPLICATION_COMMAND_CREATE!=null){
                    logger.info(cName+fName+"application response@");
                    logger.info(cName+fName+"json="+gAPPLICATION_COMMAND_CREATE.getJSON().toString());
                }
                else if(gInteractioncreate!=null){
                    mySlashHandler();
                }else{
                    logger.info(cName+fName+"basic@");
                    boolean isInvalidCommand = true;
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(cName+fName+".Args=0");
                        help("main");isInvalidCommand=false;
                    }
                    logger.info(cName+fName+".deleting op message");
                    llMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void jdaSlashHandler() throws Exception {
            String fName="[jdaSlashHandler]";
            logger.info(fName);
            String commandName=gSlashCommandEvent.getName();
            String subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
            if(subcommandgroup==null)subcommandgroup="";
            String subcommand=gSlashCommandEvent.getSubcommandName();
            if(subcommand==null)subcommand="";
            logger.info(cName+fName+"commandName="+commandName+", subcommandgroup="+subcommandgroup+", subcommand="+subcommand);
            List<OptionMapping> options=gSlashCommandEvent.getOptions();
            StringBuilder strOptions=new StringBuilder();
            for(OptionMapping option:options){
                strOptions.append(option.getName()+", type="+option.getType().name());
                switch (option.getType()){
                    case BOOLEAN: strOptions.append(", value="+option.getAsBoolean());break;
                    case CHANNEL:strOptions.append(", value="+option.getAsMessageChannel().getId());break;
                    case INTEGER:strOptions.append(", value="+option.getAsLong());break;
                    case MENTIONABLE:strOptions.append(", value="+option.getAsMentionable().getAsMention());break;
                    case ROLE:strOptions.append(", value="+option.getAsRole().getIdLong());break;
                    case STRING:strOptions.append(", value="+option.getAsString());break;
                    case USER:strOptions.append(", value="+option.getAsUser().getId());break;
                    case SUB_COMMAND:
                    case SUB_COMMAND_GROUP:
                    case UNKNOWN:
                        break;
                }
                strOptions.append("\n");
            }
            logger.info(cName+fName+"options="+strOptions.toString());
            if(!gSlashCommandEvent.isFromGuild()){
                gSlashCommandEvent.replyEmbeds(lsMessageHelper.lsErrorEmbed(null,null,"Slash command only available in guild!",null).build()).setEphemeral(true).complete();
                return;
            }
            switch (commandName){
                case "test_sl": case "test_sg":
                    InteractionHook interactionHook0=gSlashCommandEvent.reply("Test 0").complete();
                    Thread.sleep(5000);
                    interactionHook0.editOriginal("test_sl/sg 0 edited").complete();
                            /*
                            This interaction has already been acknowledged or replied to. You can only reply or acknowledge an interaction (or slash command) once!
                                    gSlashCommandEvent.reply("Test 1").complete();
                                    interactionHook0.getInteraction().reply("Test 2").complete();
                            */
                    break;
                case "slash":
                    new slashSetup_Guild_v3(gGlobal,gSlashCommandEvent);
                    break;
                case "bdsm":
                    switch (subcommand){
                        case "main":
                            new rdRestraints(gGlobal,gSlashCommandEvent);
                            break;
                        case "padlock":
                            new rdAuth(gGlobal,gSlashCommandEvent);
                            break;
                        case "gag":
                            new rdGag(gGlobal,gSlashCommandEvent);
                            break;
                        case "collar":
                            new rdCollar(gGlobal,gSlashCommandEvent);
                            break;
                        case "cuffs":
                            new rdCuffs(gGlobal,gSlashCommandEvent);
                            break;
                        case "straitjacket":
                            new rdStraitjacket(gGlobal,gSlashCommandEvent);
                            break;
                    }
                    break;
                case "restraints":
                    if(!subcommandgroup.isBlank()){
                        logger.info(cName+fName+"entered subcommandgroup restraints="+subcommandgroup);
                        switch (subcommandgroup){
                            case "auth":
                                new rdAuth(gGlobal,gSlashCommandEvent);
                                break;
                        }
                    }else
                    if(!subcommand.isBlank()){
                        logger.info(cName+fName+"entered subcommand restraints="+subcommand);
                        switch (subcommand){
                            case "menu":
                                logger.info(cName+fName+"entered subcommand menu");
                                String subdirValue="";boolean subdirProvided=false;
                                for(OptionMapping option:options){
                                    logger.info(cName+fName+"options:"+option.getName());
                                    if(option.getName().equalsIgnoreCase(llCommonKeys.SlashCommandReceive.subdir)){
                                        subdirValue=option.getAsString();
                                        subdirProvided=true;
                                    }
                                }
                                logger.info(cName+fName+"subdirProvided="+subdirProvided);
                                if(!subdirProvided){
                                    new rdRestraints(gGlobal,gSlashCommandEvent);
                                }else {
                                    logger.info(cName+fName+"subdirValue="+subdirValue);
                                    switch (subdirValue.toLowerCase()){
                                        case "main":
                                            new rdRestraints(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "auth":
                                            new rdAuth(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "gag":
                                            new rdGag(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "collar":
                                            new rdCollar(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "cuffs":
                                            new rdCuffs(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "straitjacket":
                                            new rdStraitjacket(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "nametag":
                                            new rdNameTag(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "mitts":
                                            new rdMitts(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "suit":
                                            new rdSuit(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "hood":
                                            new rdHood(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "chastity":
                                            new rdChastity(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "blindfold":
                                            new rdBlindfold(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "ears":
                                            new rdEars(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "lock":
                                            new rdLock(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "encase":
                                            new rdEncase(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "pishock":
                                            new rdPishock(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "timelock":
                                            new rdTimelock(gGlobal,gSlashCommandEvent);
                                            break;
                                        case "timeout":
                                            new rdTimeout(gGlobal,gSlashCommandEvent);
                                            break;
                                    }
                                }
                                break;
                            case "padlock":
                                new rdLock(gGlobal,gSlashCommandEvent);
                                break;
                            case "gag":
                                new rdGag(gGlobal,gSlashCommandEvent);
                                break;
                            case "collar":
                                new rdCollar(gGlobal,gSlashCommandEvent);
                                break;
                            case "cuffs":
                                new rdCuffs(gGlobal,gSlashCommandEvent);
                                break;
                            case "straitjacket":
                                new rdStraitjacket(gGlobal,gSlashCommandEvent);
                                break;
                        }
                    }


                    break;
                case "padding":
                    logger.info(cName+fName+"entered command padding");
                    switch (subcommand){
                        case "menu":
                            logger.info(cName+fName+"entered subcommand menu");
                            String subdirValue="";boolean subdirProvided=false;
                            for(OptionMapping option:options){
                                logger.info(cName+fName+"options:"+option.getName());
                                if(option.getName().equalsIgnoreCase(llCommonKeys.SlashCommandReceive.subdir)){
                                    subdirValue=option.getAsString();
                                    subdirProvided=true;
                                }
                            }
                            logger.info(cName+fName+"subdirProvided="+subdirProvided);
                            if(!subdirProvided){
                                new diaperInteractive_v2(gGlobal,gSlashCommandEvent);
                            }else {
                                logger.info(cName+fName+"subdirValue="+subdirValue);
                                switch (subdirValue.toLowerCase()){
                                    case "main":
                                        new diaperInteractive_v2(gGlobal,gSlashCommandEvent);
                                        break;
                                    case "diaper":
                                        new diDiaper(gGlobal,gSlashCommandEvent);
                                        break;
                                    case "onesie":
                                        new diSuit(gGlobal,gSlashCommandEvent);
                                        break;
                                    case "timelock":
                                        new diTimelock(gGlobal,gSlashCommandEvent);
                                        break;
                                    case "setup":
                                        new diSetup(gGlobal,gSlashCommandEvent);
                                        break;
                                }
                            }
                            break;
                        case "quick":
                            new diaperInteractive_v2(gGlobal,gSlashCommandEvent);
                            break;
                        case "diaper":
                            new diDiaper(gGlobal,gSlashCommandEvent);
                            break;
                        case "onesie":
                            new diSuit(gGlobal,gSlashCommandEvent);
                            break;
                    }
                    break;
                case  "pishock":
                    new rdPishock(gGlobal,gSlashCommandEvent);
                    break;
                case "verify":
                    new lewdverify(gGlobal,gSlashCommandEvent);
                    break;
                case "patient":
                    if(gSlashCommandEvent.getOption("nsfw").getAsBoolean()){
                        new nPatient(gGlobal,gSlashCommandEvent);
                    }else{
                        new Patient(gGlobal,gSlashCommandEvent);
                    }
                    break;
                case "fursona":
                    if(gSlashCommandEvent.getOption("nsfw").getAsBoolean()){
                        new nfursona(gGlobal,gSlashCommandEvent);
                    }else{
                        new fursona(gGlobal,gSlashCommandEvent);
                    }
                    break;
                case "character":
                    if(gGlobal==null)throw  new Exception("global is null");
                    if(gSlashCommandEvent.getOption("nsfw").getAsBoolean()){
                        new ncharacter(gGlobal,gSlashCommandEvent);
                    }else{
                        new character(gGlobal,gSlashCommandEvent);
                    }
                    break;
                case  "lovense":
                    new Lovense(gGlobal,gSlashCommandEvent);
                    break;
                case  "emlalock":
                    new ChastityEmlalock(gGlobal,gSlashCommandEvent);
                    break;
                case  "presents":
                    new NaughtyPresents(gGlobal,gSlashCommandEvent);
                    break;
                case  "privacy":
                    new utilityPrivacy(gGlobal,gSlashCommandEvent);
                    break;
                case  "news":
                    new utilityUpdateNotification(gGlobal,gSlashCommandEvent);
                    break;
                case  "commands":
                    new HelpCommand(gGlobal,gSlashCommandEvent);
                case  "image_search":
                    switch (subcommand){
                        case "fa":
                            new furaffinity(gGlobal,gSlashCommandEvent);
                            break;
                    }
                    break;
            }
        }
        private void mySlashHandler() throws Exception {
            String fName="[mySlashHandler]";
            logger.info(fName);
            logger.info(cName+fName+"slash@");
            logger.info(cName+fName+"json="+gInteractioncreate.getJSON().toString());
            if(!gInteractioncreate.isGuild()){
                logger.warn(cName+fName+"not guild action");
                gInteractioncreate.respondMessage("Only available in guild's text channel!",true);
                return;
            }
            String name=gInteractioncreate.getName();
            logger.info(cName+fName+"name="+name);
            logger.info(cName+fName+"token="+gInteractioncreate.getToken());
            JSONObject jsonOptions=gInteractioncreate.getOptionPrime();
            logger.info(cName+fName+"jsonOptions="+jsonOptions.toString());
            switch (name){
                case "invite":
                    respondInvite();
                    break;
                case  "slash": case "display":  case  "verify": case "lovense": case  "emlalock": case  "pishock":
                    logger.info(cName+fName+"removed for jda version");
                    break;
                case  "bdsm":
                    switch (jsonOptions.getString(keyName)){
                        case "mitts": case "hood":
                            logger.warn(cName+fName+"outdated");
                            break;
                        case "gag": case "straitjacket": case "cuffs": case "cuff": case "collar": case "padlock":
                            logger.warn(cName+fName+"removed for jda version");
                            break;
                        default:
                            logger.warn(cName+fName+"invalid rd command");
                    }
                    break;
                case  "bdsmold":
                    logger.warn(cName+fName+"invalid old bdsm command");
                    break;
                case  "gift": case "gifts":
                    logger.warn(cName+fName+"removed temporary");
                    /*if(gTextChannel.isNSFW())respondGeneral();
                    new NaughtyGifts(gGlobal,gInteractioncreate);*/
                    break;
                /*case  "pishock":
                    new rdPishock(gGlobal,gInteractioncreate);
                    break;*/
            }

        }
        String keyName="name",keyValue="value";
        private void help(String command){
            String fName="help";
            logger.info(fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"emote ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            llSendMessage(gUser,embed);
        }
        //https://www.npmjs.com/package/discord-slash-commands
        private void respondInvite(){
            String fName="[respondInvite]";
            try {
                logger.info(fName);
                if(readFile("message_invite")){
                    logger.info(fName+"read");
                    String url="https://discord.com/api/oauth2/authorize?client_id="+gGuild.getJDA().getSelfUser().getId()+"&permissions=2113400055&scope=bot%20applications.commands";
                    String text="";
                    if(text2Json.jsonObject.getJSONObject("data").has("embeds")){
                        try {
                            text=text2Json.jsonObject.getJSONObject("data").getJSONArray("embeds").getJSONObject(0).getString("description");;
                            logger.info(fName+"embeds_text_old="+text);
                            text=text.replaceAll("!user",gMember.getAsMention()).replaceAll("!url", lsUsefullFunctions.getUrlTextString("this",url));
                            logger.info(fName+"embeds_text_new="+text);
                            text2Json.jsonObject.getJSONObject("data").getJSONArray("embeds").getJSONObject(0).put("description",text);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    if(text2Json.jsonObject.getJSONObject("data").has("content")){
                        try {
                            text=text2Json.jsonObject.getJSONObject("data").getString("content");;
                            logger.info(fName+"content_text_old="+text);
                            text=text.replaceAll("!user",gMember.getAsMention()).replaceAll("!url", lsUsefullFunctions.getUrlTextString("this",url));
                            logger.info(fName+"content_text_new="+text);
                            text2Json.jsonObject.getJSONObject("data").put("content",text);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    gInteractioncreate.respond(text2Json.jsonObject);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void respondGeneral(){
            String fName="[respondGeneral]";
            try {
                logger.info(fName);
                JSONObject jsonResponse5=new JSONObject("{\n" +
                        "    \"type\": 5,\n" +
                        "    \"data\": {}}");
                gInteractioncreate.respond(jsonResponse5);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void respondGBlank(){
            String fName="[respondGBlank]";
            try {
                logger.info(fName);
                gInteractioncreate.respondPing();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        String gUrlMainSoloPath="resources/json/slash/global";
        lcText2Json text2Json=null;
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
                        logger.info(fName+".file1 exists");
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
                        logger.info(fName+".file2 exists");
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
                }else{
                    logger.warn(fName+".no input stream");
                }
                if(text2Json.jsonObject.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
    
  //runLocal  
    }
}
