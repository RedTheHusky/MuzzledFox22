package fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.la.aBasicCommandHandler;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import restraints.in.iRestraints;

import java.util.ArrayList;
import java.util.Arrays;

public class nekoLife_Why extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String gUrl="https://nekos.life/api/v2/why";
    Logger logger = Logger.getLogger(getClass()); String cName="[Why]",gCommand="why",gTitle="Why-Fun";
    lcGlobalHelper gGlobal=lsGlobalHelper.sGetGlobal();
    public nekoLife_Why(){
        String fName="[constructor]";
        logger.info(cName+fName);
        this.name = gTitle;
        this.help = "Get question from https://nekos.life/";
        this.aliases = new String[]{gCommand};
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
            logger.info(cName + ".run build");
            setCommandHandlerValues(logger,event);
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                buildBasicFeatureControl(logger,"nekoLife_Why",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                    }
                    else {getWhy();isInvalidCommand=false;}
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);
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
                    if(items[0].equalsIgnoreCase("get")){
                        getWhy();isInvalidCommand=false;
                    }
                }
                deleteCommandPostAndCheckIfInvalid();
                logger.info(cName+".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            String desc="N/a";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Helper");
            embed.addField(iRestraints.strSupportTitle,iRestraints.strSupport,false);
            embed.addField("Get a why","Type `"+quickSummonWithSpace+"get`",false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private JSONObject getJSONObject(@NotNull HttpResponse<JsonNode> response){
            String fName="getJSONObject";
            JSONObject object;
            int status=response.getStatus();
            if(!(200<=status&&status<=299)){ logger.error(cName+fName+".invalid status");logger.error(fName+".return 1");return null;}
            object=response.getBody().getObject();
            return object;
        }
        private void getWhy() {
            String fName = "[getWhy]";
            if(!gBasicFeatureControl.getEnable()){
                logger.info(fName+"its disabled");
                return;
            }
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=gUrl;
            ArrayList<String> list=new ArrayList<>();
            logger.info(cName + fName + ".url="+url);
            a.get(url)
                    .asJson()
                    .ifSuccess(response -> {
                        logger.info(cName+fName+".success");
                        try {
                            EmbedBuilder embed=new EmbedBuilder();
                            JSONObject object = getJSONObject(response);
                            assert object != null;
                            logger.info(cName + fName + ".json=" + object.toString());
                            if (object.isEmpty()) {
                                logger.error(cName + fName + ".isEmpty");
                                return;
                            }
                            if(!object.has("why")){
                                logger.error(cName + fName + ".has no customStickers");
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"No question!",llColorRed);return;
                            }
                            String str=object.getString("why");
                            logger.info(cName + fName + ".str="+str);  embed.setDescription(str);
                            embed.setColor(llColorPink2);
                            llSendMessage(gTextChannel,embed);

                        } catch (Exception e) {
                            logger.error(cName+fName+"exception:"+e);
                            llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                        }
                    })
                    .ifFailure(response -> {
                        logger.error(cName+fName+".failure");
                        logger.error(cName+"Oh No! Status" + response.getStatus());
                        response.getParsingError().ifPresent(e -> {
                            logger.error(cName+"Parsing Exception: "+e);
                            logger.error(cName+"Original body: " + e.getOriginalBody());
                        });
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
                    });
        }

    }

}
