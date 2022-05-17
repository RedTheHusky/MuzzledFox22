package userprofiles;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.apache.log4j.Logger;
import userprofiles.entities.rFursona;
import userprofiles.entities.upExtensionFursona;

import java.util.Arrays;
import java.util.List;

public class fursona extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, rFursona {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String KeyTag ="fursona", gTitle="Fursona",profileName="fursona";
    public fursona(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Fursona";
        this.help = "Creating/Displaying your fursona. SFW edition";
        this.aliases = new String[]{KeyTag,"fursonas"};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=false;
    }
    public fursona(lcGlobalHelper global, SlashCommandEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
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
    protected class runLocal extends upExtensionFursona implements Runnable {
        String cName = "[runLocal]";


        public runLocal(CommandEvent ev) {
            logger.info(".run build");String fName="run";
            KeyTag ="fursona"; gTitle="Fursona";profileName="fursona";
            launch(gGlobal,ev);
        }
        public runLocal(lcGlobalHelper global,SlashCommandEvent ev) {
            logger.info(".run build");String fName="run";
            KeyTag ="fursona"; gTitle="Fursona";profileName="fursona";
            launch(global,ev);
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");

            boolean isInvalidCommand = true;
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"fursona",gGlobal);
                gBasicFeatureControl.initProfile();
                if(gSlashCommandEvent!=null){
                    slash();
                }
                else if(gCommandEvent!=null){
                    if(gCommandEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        getProfile(gMember);
                        menuMainWearer();isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        gItems = gCommandEvent.getArgs().split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("guild")||gItems[0].equalsIgnoreCase("server")){
                            if(gItems.length>2){
                                // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                int group=0,type=0,action=0;
                                switch (gItems[1].toLowerCase()){
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
                                switch (gItems[2].toLowerCase()){
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
                                        setChannel(type,action,gCommandEvent.getMessage());
                                    }
                                }
                                else if(group==2){
                                    if(action==0){
                                        getRoles(type,false);isInvalidCommand=false;
                                    }else{
                                        setRole(type,action,gCommandEvent.getMessage());
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
                        }

                        if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                            List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                            if(mentions.isEmpty()){
                                logger.warn(fName+".zero member mentions in message>check itemns[0]");
                                gTarget=llGetMember(gGuild,gItems[0]);
                            }else{
                                logger.info(fName+".member mentions in message");
                                gTarget=mentions.get(0);
                            }

                            if(gTarget==null){
                                logger.warn(fName+".zero member mentions");
                            }
                            else if(gTarget.getIdLong()==gUser.getIdLong()){
                                logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                                gTarget=null;

                            }else
                            if(gItems.length>=2&&gItems[1].equalsIgnoreCase("view")){
                                if(gItems.length<3){
                                    viewAll(gTarget);isInvalidCommand=false;
                                }else{
                                    if(gItems.length<4){
                                        view(gTarget,gItems[2],1);isInvalidCommand=false;
                                    }else{
                                        switch (gItems[3].toLowerCase()){
                                            case"general":
                                                view(gTarget,gItems[2],1);isInvalidCommand=false;
                                                break;
                                            case "description":
                                            case "details":
                                                view(gTarget,gItems[2],2);isInvalidCommand=false;
                                                break;
                                            case "rp":
                                            case "preferences":
                                            case "rppreferences":
                                            case "rpreferences":
                                                view(gTarget,gItems[2],3);isInvalidCommand=false;
                                                break;
                                        }
                                    }
                                }
                            }
                            else{
                                getProfile(gTarget);
                                menuMainSomebody();
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems[0].equalsIgnoreCase("create")){
                                create();isInvalidCommand=false;
                            }else
                            if(gItems[0].equalsIgnoreCase("view")){
                                if(gItems.length<2){
                                    viewAll();isInvalidCommand=false;
                                }else{
                                    if(gItems.length<3){
                                        view(gItems[1],1);isInvalidCommand=false;
                                    }else{
                                        switch (gItems[2].toLowerCase()){
                                            case"general":
                                                view(gItems[1],1);isInvalidCommand=false;
                                                break;
                                            case "description":
                                            case "details":
                                                view(gItems[1],2);isInvalidCommand=false;
                                                break;
                                            case "rp":
                                            case "preferences":
                                            case "rppreferences":
                                            case "rpreferences":
                                                view(gItems[1],3);isInvalidCommand=false;
                                                break;
                                        }
                                    }
                                }
                            }else
                            if(gItems[0].equalsIgnoreCase("edit")){
                                if(gItems.length<2){
                                    noSlot();isInvalidCommand=false;
                                }else{
                                    edit(gItems[1]);isInvalidCommand=false;
                                }
                            }else
                            if(gItems[0].equalsIgnoreCase("delete")){
                                if(gItems.length<2){
                                    noSlot();isInvalidCommand=false;
                                }else{
                                    delete(gItems[1]);isInvalidCommand=false;
                                }
                            }else{
                                getProfile(gMember);
                                menuMainWearer();
                            }
                        }

                    }
                    logger.info(fName+".deleting op message");
                    llMessageDelete(gCommandEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(".run ended");
        }


    }

    public fursona(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        Runnable r = new runUtility(global,guild,user,command);
        new Thread(r).start();
    }
    protected class runUtility extends upExtensionFursona implements Runnable {
        String cName = "[runReset]";


        public runUtility(lcGlobalHelper global, Guild g, User user,String command) {
            logger.info(".run build");
            KeyTag ="fursona"; gTitle="Fursona";profileName="fursona";
            launch( global,g,user,command);
        }

        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                if(gRawForward.equalsIgnoreCase("reset")){
                    getProfileL();
                    gUserProfile.jsonObject =new JSONObject();
                    saveProfileL();
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }

    }
}
