package test;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class slashCommandsTest extends Command implements llMessageHelper, llGlobalHelper {
    String cName="[slashCommandsTest]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gTitle="slashCommandstest", gCommand="slashCommandstest";
    public slashCommandsTest(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal = g;
        this.name = gTitle;
        this.help = "slashCommandstest";
        this.aliases = new String[]{gCommand};
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
            String fName="runLoccal";
            logger.info(cName + ".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            gTextChannel =gEvent.getTextChannel();
            logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+fName);
            boolean isInvalidCommand = true;

            if(gEvent.getArgs().isEmpty()){
                logger.info(cName+fName+".Args=0");
                help("main");isInvalidCommand=false;
            }else {
                logger.info(cName + fName + ".Args");
                String []items = gEvent.getArgs().split("\\s+");
                if(items[0].equalsIgnoreCase("help")){
                    help( "main");isInvalidCommand=false;
                }
                else if(items[0].equalsIgnoreCase("add")){
                    add();isInvalidCommand=false;
                }
                else if(items[0].equalsIgnoreCase("edit")){
                    edit();isInvalidCommand=false;
                }
                else if(items[0].equalsIgnoreCase("get")){
                    getGuild();isInvalidCommand=false;
                }
                else if(items[0].equalsIgnoreCase("getglobal")){
                    getGlobal();isInvalidCommand=false;
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
            logger.info(cName + fName + ".command:"+command);
            String quickSummonWithSpace=llPrefixStr+"emote ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            llSendMessage(gUser,embed);
        }
        String botId="389512707973316608",
        botToken="Mzg5NTEyNzA3OTczMzE2NjA4.Wi2X-g.ZlVQQUvC1tRGoYa8gPLK2okxSBE";

        private void add(){
            String fName="[add]";
            logger.info(cName + fName);
            try {
                // String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/481700616457027597/commands";
                String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/"+ gGuild.getId()+"/commands";
                String json="{\n" +
                        "    \"name\": \"uuuu\",\n" +
                        "    \"description\": \"oiii\",\n" +
                        "    \"options\": []\n" +
                        "}";
                //2020-12-18 18:55:23,323 [Thread-11] INFO  test.slashCommands - [add].body ={"id":"789536298737991692","application_id":"389512707973316608","name":"permissions","description":"Get or edit permissions for a user or a role"}
                /*json="{\n" +
                        "    \"name\": \"blep\",\n" +
                        "    \"description\": \"Send a random adorable animal photo\",\n" +
                        "    \"options\": [\n" +
                        "        {\n" +
                        "            \"name\": \"animal\",\n" +
                        "            \"description\": \"The type of animal\",\n" +
                        "            \"type\": 3,\n" +
                        "            \"required\": True,\n" +
                        "            \"choices\": [\n" +
                        "                {\n" +
                        "                    \"name\": \"Dog\",\n" +
                        "                    \"value\": \"animal_dog\"\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"Cat\",\n" +
                        "                    \"value\": \"animal_dog\"\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"Penguin\",\n" +
                        "                    \"value\": \"animal_penguin\"\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"name\": \"only_smol\",\n" +
                        "            \"description\": \"Whether to show only baby animals\",\n" +
                        "            \"type\": 5,\n" +
                        "            \"required\": False\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";*/
                json="{\n" +
                        "    \"name\": \"permissions\",\n" +
                        "    \"description\": \"Get or edit permissions for a user or a role\",\n" +
                        "    \"options\": [\n" +
                        "        {\n" +
                        "            \"name\": \"user\",\n" +
                        "            \"description\": \"Get or edit permissions for a user\",\n" +
                        "            \"type\": 2, // 2 is type SUB_COMMAND_GROUP\n" +
                        "            \"options\": [\n" +
                        "                {\n" +
                        "                    \"name\": \"get\",\n" +
                        "                    \"description\": \"Get permissions for a user\",\n" +
                        "                    \"type\": 1, // 1 is type SUB_COMMAND\n" +
                        "                    \"options\": [\n" +
                        "                        {\n" +
                        "                            \"name\": \"user\",\n" +
                        "                            \"description\": \"The user to get\",\n" +
                        "                            \"type\": 6, // 6 is type USER\n" +
                        "                            \"required\": true\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"name\": \"channel\",\n" +
                        "                            \"description\": \"The channel permissions to get. If omitted, the guild permissions will be returned\",\n" +
                        "                            \"type\": 7, // 7 is type CHANNEL\n" +
                        "                            \"required\": false\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"edit\",\n" +
                        "                    \"description\": \"Edit permissions for a user\",\n" +
                        "                    \"type\": 1,\n" +
                        "                    \"options\": [\n" +
                        "                        {\n" +
                        "                            \"name\": \"user\",\n" +
                        "                            \"description\": \"The user to edit\",\n" +
                        "                            \"type\": 6,\n" +
                        "                            \"required\": true\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"name\": \"channel\",\n" +
                        "                            \"description\": \"The channel permissions to edit. If omitted, the guild permissions will be edited\",\n" +
                        "                            \"type\": 7,\n" +
                        "                            \"required\": false\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"name\": \"role\",\n" +
                        "            \"description\": \"Get or edit permissions for a role\",\n" +
                        "            \"type\": 2,\n" +
                        "            \"options\": [\n" +
                        "                {\n" +
                        "                    \"name\": \"get\",\n" +
                        "                    \"description\": \"Get permissions for a role\",\n" +
                        "                    \"type\": 1,\n" +
                        "                    \"options\": [\n" +
                        "                        {\n" +
                        "                            \"name\": \"role\",\n" +
                        "                            \"description\": \"The role to get\",\n" +
                        "                            \"type\": 8, // 8 is type ROLE\n" +
                        "                            \"required\": true\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"name\": \"channel\",\n" +
                        "                            \"description\": \"The channel permissions to get. If omitted, the guild permissions will be returned\",\n" +
                        "                            \"type\": 7,\n" +
                        "                            \"required\": false\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"edit\",\n" +
                        "                    \"description\": \"Edit permissions for a role\",\n" +
                        "                    \"type\": 1,\n" +
                        "                    \"options\": [\n" +
                        "                        {\n" +
                        "                            \"name\": \"role\",\n" +
                        "                            \"description\": \"The role to edit\",\n" +
                        "                            \"type\": 8,\n" +
                        "                            \"required\": true\n" +
                        "                        },\n" +
                        "                        {\n" +
                        "                            \"name\": \"channel\",\n" +
                        "                            \"description\": \"The channel permissions to edit. If omitted, the guild permissions will be edited\",\n" +
                        "                            \"type\": 7,\n" +
                        "                            \"required\": false\n" +
                        "                        }\n" +
                        "                    ]\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";
                JSONObject jsonObject=new JSONObject(json);
                logger.info(cName + fName+"jsonObject="+ jsonObject.toString());
                /*headers = {
    "Authorization": "Bot 123456"
}*/
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
                        .header("Authorization", "Bot "+botToken)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorOrange_InternationalEngineering);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Success",llColorGreen1);
                }
                JSONObject body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void edit(){
            String fName="[edit]";
            logger.info(cName + fName);
            try {
                // String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/481700616457027597/commands";
                String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/"+ gGuild.getId()+"/commands/789536298737991692";
                String json="{\n" +
                        "    \"name\": \"test1\",\n" +
                        "    \"description\": \"justr to test it out \",\n" +
                        "    \"options\": []\n" +
                        "}";
                JSONObject jsonObject=new JSONObject(json);
                logger.info(cName + fName+"jsonObject="+ jsonObject.toString());
                /*headers = {
    "Authorization": "Bot 123456"
}*/
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.patch(url)
                        .header("Authorization", "Bot "+botToken)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(json)
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorOrange_InternationalEngineering);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Success",llColorGreen1);
                }
                JSONObject body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getGuild(){
            String fName="[getGuild]";
            logger.info(cName + fName);
            try {
                // String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/481700616457027597/commands";
                String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/"+ gGuild.getId()+"/commands";
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.get(url)
                        .header("Authorization", "Bot "+botToken)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorOrange_InternationalEngineering);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Success",llColorGreen1);
                }
                JSONArray body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getGlobal(){
            String fName="[getGlobal]";
            logger.info(cName + fName);
            try {
                // String url = "https://discord.com/api/v8/applications/"+botId+"/guilds/481700616457027597/commands";
                String url = "https://discord.com/api/v8/applications/"+botId+"/commands";
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.get(url)
                        .header("Authorization", "Bot "+botToken)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .asJson();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                if(jsonResponse.getStatus()>299){
                    logger.error(fName+".invalid status");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Error",llColorOrange_InternationalEngineering);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Success",llColorGreen1);
                }
                JSONArray body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    
  //runLocal  
    }
}
