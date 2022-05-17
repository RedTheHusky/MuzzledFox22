package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.in.iAuth;
import restraints.in.iRdStr;
import restraints.in.iRestraints;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.rdExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdAuth extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public static String quickAlias="auth",gCommand="auth";
    public static String sMainRTitle ="Author";
    public rdAuth(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Auth-DiscordRestraints";
        this.help = "rdAuth";
        this.aliases = new String[]{"auth"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        //this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdAuth(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdAuth(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }

    public rdAuth(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdAuth(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdAuth(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdAuth(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdAuth(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd,target);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent gEvent) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(gEvent);
        new Thread(r).start();
    }
    protected class runLocal extends rdExtension implements Runnable {
        String cName="[runLocal]";

        public runLocal(CommandEvent ev){
            launch(gGlobal,ev);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){

            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){

            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }

        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build");
            launch(gGlobal,ev);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
                logger.info(".run build");
                launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward,target);
        }

        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try{
                setTitleStr(rdAuth.sMainRTitle);setPrefixStr(rdAuth.llPrefixStr);setCommandStr(rdAuth.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdAuth_commands");
                lsUsefullFunctions.setThreadName4Display("rdAuth");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuLevels(gTarget);
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuLevels(gTarget);
                            break;
                    }
                }
                else if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }
                else if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}

                    boolean isInvalidCommand=true;
                    if(!checkIFAllowed2UseCommand2_text()){ return; }
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        menuLevels(gTarget);isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }

                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}

                    boolean isInvalidCommand=true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        menuLevels(null);isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs!=null&&gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);

                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("guild")||gItems[0].equalsIgnoreCase("server")){
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
                                        setChannel(type,action, gCommandEvent.getMessage());
                                    }
                                }
                                else if(group==2){
                                    if(action==0){
                                        getRoles(type,false);isInvalidCommand=false;
                                    }else{
                                        setRole(type,action, gCommandEvent.getMessage());
                                    }
                                }
                            }else{
                                menuGuild();isInvalidCommand=false;
                            }
                        }

                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
                             if(!gBasicFeatureControl.getEnable()){
                                logger.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                logger.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                logger.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }else{

                                 List<Member>mentions= gCommandEvent.getMessage().getMentionedMembers();
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
                                 else if(gTarget.getId().equalsIgnoreCase(gCommandEvent.getAuthor().getId())){
                                     logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                                     //llSendQuickEmbedMessage(gEvent.getAuthor(),sMainRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                                 }
                                 else if(gItems.length<2){
                                     logger.warn(fName+".invalid args length");
                                     menuLevels(gTarget);isInvalidCommand=false;
                                 }else{
                                     if(gItems[1].equalsIgnoreCase("owner")){
                                         if(gItems.length<3){
                                             logger.warn(fName+".invalid args length");
                                             rHelp("owner");isInvalidCommand=false;
                                         }else{
                                             if(gItems[2].equalsIgnoreCase("reject")){
                                                 rejectSub(gTarget);isInvalidCommand=false;
                                             }
                                             if(gItems[2].equalsIgnoreCase("accept")){
                                                 acceptSub(gTarget);isInvalidCommand=false;
                                             }
                                             if(gItems[2].equalsIgnoreCase("release")){
                                                 if(gIsOverride)adminReleaseSub(gTarget);
                                                 else releaseSub(gTarget);
                                                 isInvalidCommand=false;
                                             }
                                             if(gIsOverride&&gItems[2].equalsIgnoreCase("add")){
                                                 adminAddOwner(gTarget);isInvalidCommand=false;
                                             }
                                         }
                                     }
                                     else if(gItems[1].equalsIgnoreCase("list")){
                                         listAuth(gTarget);isInvalidCommand=false;
                                     }
                                     else if(gItems[1].equalsIgnoreCase("secowner")){
                                         if(gItems.length<3){
                                             logger.warn(fName+".invalid args length");
                                             rHelp("secowner");isInvalidCommand=false;
                                         }else{
                                             if(gItems[2].equalsIgnoreCase("add")){
                                                 addSecOwner(gTarget);isInvalidCommand=false;
                                             }
                                             if(gItems[2].equalsIgnoreCase("remove")){
                                                 removeSecOwner(gTarget);isInvalidCommand=false;
                                             }

                                         }
                                     }
                                     else if(gItems.length>=3&&(gItems[2].equalsIgnoreCase("owner"))){
                                         if(gItems.length<4){
                                             logger.warn(fName+".invalid args length");
                                             rHelp("owner");isInvalidCommand=false;
                                         }else{
                                             if(gItems[3].equalsIgnoreCase("reject")){
                                                 rejectSub(gTarget);isInvalidCommand=false;
                                             }
                                             if(gItems[3].equalsIgnoreCase("accept")){
                                                 acceptSub(gTarget);isInvalidCommand=false;
                                             }
                                             if(gItems[3].equalsIgnoreCase("release")){
                                                 releaseSub(gTarget);isInvalidCommand=false;
                                             }
                                         }
                                     }
                                     else if(gItems.length>=3&&(gItems[2].equalsIgnoreCase("list"))){
                                         listAuth(gTarget);isInvalidCommand=false;
                                     }
                                     else if(gItems.length>=3&&(gItems[2].equalsIgnoreCase("secowner"))){
                                         if(gItems[3].equalsIgnoreCase("add")){
                                             addSecOwner(gTarget);isInvalidCommand=false;
                                         }
                                         if(gItems[3].equalsIgnoreCase("remove")){
                                             removeSecOwner(gTarget);isInvalidCommand=false;
                                         }
                                     }
                                     else if(gItems[1].equalsIgnoreCase(commandPermalock)||gItems[1].equalsIgnoreCase(commandUnseal)||gItems[1].equalsIgnoreCase(optionLock)||gItems[1].equalsIgnoreCase(optionUnlock)){
                                         movedCommands();
                                     }
                                     else if(gItems[1].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                         rAccess(gTarget,gItems[1].toLowerCase());isInvalidCommand=false;
                                     }
                                     else if(gItems.length>=3&&(gItems[2].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Ask.getName()))){
                                         rAccess(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                                     }
                                     else if(gItems.length>=3&&gItems[1].equalsIgnoreCase("access")){
                                         if(gItems[2].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[2].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                             rAccess(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                                         }
                                     }
                                     else if(gItems.length>=3&&gItems[2].equalsIgnoreCase("access")){
                                         if(gItems[3].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[3].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                             rAccess(gTarget,gItems[3].toLowerCase());isInvalidCommand=false;
                                         }
                                     }
                                 }
                            }
                        }
                        if(isInvalidCommand){
                            if(gItems!=null&&gItems.length>0&&gItems[0].equalsIgnoreCase("runaway")){
                                rRunAway();isInvalidCommand=false;
                            }
                            else if(!checkIFAllowed2UseCommand1_slash()){ return; }
                            else if(gItems==null||gItems.length==0){
                                menuLevels(null);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("setup")&&(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                                menuServerSetup(); isInvalidCommand=false;
                            }
                            if(gItems[0].equalsIgnoreCase(commandPermalock)||gItems[0].equalsIgnoreCase(commandUnseal)||gItems[0].equalsIgnoreCase(optionLock)||gItems[0].equalsIgnoreCase(optionUnlock)){
                                movedCommands();
                            }
                            else if(gItems[0].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                rAccess(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("owner")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("owner");isInvalidCommand=false;
                                }else{
                                    if(gItems[1].equalsIgnoreCase("add")){
                                        addOwner();isInvalidCommand=false;
                                    }
                                    if(gItems[1].equalsIgnoreCase("remove")){
                                        removeOwner();isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("secowner")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("secowner");isInvalidCommand=false;
                                }else{
                                    if(gItems[1].equalsIgnoreCase("add")){
                                        addSecOwner();isInvalidCommand=false;
                                    }
                                    if(gItems[1].equalsIgnoreCase("remove")){
                                        removeSecOwner();isInvalidCommand=false;
                                    }
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("access")){
                                if(gItems.length<2){
                                    logger.warn(fName+".invalid args length");
                                    rHelp("access");isInvalidCommand=false;
                                }else{
                                    if(gItems[1].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[1].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                        rAccess(gItems[1].toLowerCase());isInvalidCommand=false;
                                    }

                                }
                            }

                        }
                    }
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sMainRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
            logger.info(".run ended");
        }




        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            switch (command) {
                case "owner":
                    embed.addField("What is this?", "The owner system for this sub's restraints.\nSub can set up the owner\nOwner can release the sub.", false);
                    embed.addField("Add Owner", "To add type in `" + llPrefixStr + quickAlias + " owner add <@User>`\nWill ask the user if they want to be your owner or not. They can reject the proposition.\nTo remove type in `" + llPrefixStr + quickAlias + " owner remove` \nWorks only if owner has not accepted the proposition!", false);
                    embed.addField("Proposition response", "proposed owner respond to the sub proposition by typing `" + llPrefixStr + quickAlias + " <@Pet> owner <accept/reject>`", false);
                    embed.addField("Releasing", "Releasing the pet from your ownership `" + llPrefixStr + quickAlias + " <@Pet> owner release`", false);
                    break;
                case "secowner":
                    embed.addField("What is this?", "The sec-owner system for this sub's restraints.\nSub can set up the sec-owners until they dont have an owner.\nOwner ca set up sec-owners.", false);
                    embed.addField("Add Sec-Owner", "To add type in `" + llPrefixStr + quickAlias + " secowner add <@User>`\nMax 3 sec-owners. Pet cant add if owned.", false);
                    embed.addField("Remove Sec-Owner", "To remove type in `" + llPrefixStr + quickAlias + " secowner remove <@User>` \n Pet can't remove if owned.", false);
                    break;
                case "access":
                    embed.addField("What is this?", "Sets the access to the subs restrains. Can block others or the sub to access it. \nOwner and sec-owners will always have access.", false);
                    embed.addField("Public", "Sets that anyone can use the sub except the sub has no access to its restraints  \n`" + llPrefixStr + quickAlias + " <@Pet> public` sets that anyone can use the sub except the sub has no access to its restraints", false);
                    embed.addField("Protected", "Sets that only the sub, sub's owner and sec-owner have access \n`" + llPrefixStr + quickAlias + " <@Pet> protected`", false);
                    embed.addField("Exposed", "Sets that everyone has access including the sub \n`" + llPrefixStr + quickAlias + " <@Pet> exposed`", false);
                    embed.addField("Key", "Sets that everyone has access including the sub unless its locked. When locked only who locked has access, except owner and sec-owners. \n`" + llPrefixStr + quickAlias + " <@Pet> key`", false);
                    embed.addField("Pet", "Sets that only owner & sec-owners have access. If there is no owner, the sub has access too. \n`" + llPrefixStr + quickAlias + " <@Pet> pet`", false);
                    break;
                default:
                    embed.addField(strSupportTitle,strSupport,false);
                    embed.addField("What is this?", "The owner&sec-owner system for this sub's restraints.\nSub can set up the owner and sec-owners but lose the rights once they have an owner.\nOwner ca set up sec-owners.", false);
                    embed.addField("Add Owner", "To add type in `" + llPrefixStr + quickAlias + " owner add <@User>`\nWill ask the user if they want to be your owner or not. They can reject the proposition.", false);
                    embed.addField("Remove Owner", "To remove type in `" + llPrefixStr + quickAlias + " owner remove <@User>` \nWorks only if owner has not accepted the proposition!", false);
                    embed.addField("Add Sec-Owner", "To add type in `" + llPrefixStr + quickAlias + " secowner add <@User>`\nMax 3 sec-owners. Pet cant add if owned.", false);
                    embed.addField("Remove Sec-Owner", "To remove type in `" + llPrefixStr + quickAlias + " secowner remove <@User>` \n Pet can't remove if owned.", false);
                    embed.addField("Proposition response", "You respond to the sub proposition by typing `" + llPrefixStr + quickAlias + " <@Pet> owner <accept/reject>`", false);
                    embed.addField("Releasing", "Releasing the pet from your ownership `" + llPrefixStr + quickAlias + " <@Pet> owner release`", false);
                    embed.addField("Runaway", "Safeword command for pets to reset&runaway `" + llPrefixStr + quickAlias + " runaway`", false);
                    embed.addField("Public", "Sets that anyone can use the sub except the sub has no access to its restraints  \n`" + llPrefixStr + quickAlias + " <@Pet> public` sets that anyone can use the sub except the sub has no access to its restraints", false);
                    embed.addField("Protected", "Sets that only the sub, sub's owner and sec-owner have access \n`" + llPrefixStr + quickAlias + " <@Pet> protected`", false);
                    embed.addField("Exposed", "Sets that everyone has access including the sub \n`" + llPrefixStr + quickAlias + " <@Pet> exposed`", false);
                    embed.addField("Key", "Sets that everyone has access including the sub unless its locked. When locked only who locked has access, except owner and sec-owners. \n`" + llPrefixStr + quickAlias + " <@Pet> key`", false);
                    embed.addField("Pet", "Sets that only owner & sec-owners have access. If there is no owner, the sub has access too. \n`" + llPrefixStr + quickAlias + " <@Pet> pet`", false);
                    //embed.addField("Permalock", "Locks the restraints permanently\n`" + llPrefixStr + quickAlias + " <@Pet> permalock`", false);
                    if(llMemberIsAdministrator(gMember))embed.addField("Unseal", "Unseals the permanent restraints\n`" + llPrefixStr + quickAlias + " <@Pet> unseal`", false);
                    if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                        embed.addField("Setup","To access the setup `"+llPrefixStr+"auth setup`" , false);
                    }
                    if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
                    break;
            }
            if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
                logger.info(fName+"sent as slash");
            }
            else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        String strAccessWithDualSpacing=" access ";
        private void rRunAway(){
            String fName = "[rRunAway]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".do permalock");
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription("Are you sure you want to do a runaway?\nBesides freeing you from your owner, it will also undo your restraints."+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" for yes/"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" for no.");
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).queue();
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {																	
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                gNewUserProfile.gUserProfile=iRunAway(gNewUserProfile.gUserProfile,gBDSMCommands.getRestrainsProfile());
                                if(!saveJSON()){
                                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
                                new rdRestrictions(gGlobal, gCommandEvent,false,"",true);
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" has run away! Is no longer owned.", llColorRed);
                            }
                            llMessageDelete(message);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
        }
        private void addOwner(){
            String fName = "[addOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".hasOwner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Already have an owner", llColorRed); return;
            }
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(0);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(member.getId().equals(gUser.getId())){
                logger.warn(fName + ".owner cant be the wearer");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You can't name yourself as owner!", llColorRed); return;
            }
            gNewUserProfile.cAUTH.setOwnerAccepted(false); gNewUserProfile.cAUTH.setOwnerId(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Added "+member.getAsMention()+" as your owner. Now they need to confirm it!", llColorPurple1);
            askingKeyholder2AcceptOwnership();
        }
        private void addOwner(Member member){
            String fName = "[addOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".hasOwner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Already have an owner", llColorRed); return;
            }
            if(member==null){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(member.getId().equals(gUser.getId())){
                logger.warn(fName + ".owner cant be the wearer");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You can't name yourself as owner!", llColorRed); return;
            }
            gNewUserProfile.cAUTH.setOwnerAccepted(false); gNewUserProfile.cAUTH.setOwnerId(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Added "+member.getAsMention()+" as your owner. Now they need to confirm it!", llColorPurple1);
            askingKeyholder2AcceptOwnership();
        }
        private void removeOwner(){
            String fName = "[removeOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".no owner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Don't have any owner request", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                logger.info(fName + ".owner accepted");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Sorry, you can't undo this action once the owner accepted your request. Only they can free you from ownership.", llColorRed); return;
            }
            gNewUserProfile.cAUTH.setOwnerAccepted(false); gNewUserProfile.cAUTH.setOwnerId("");
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Removed your owner request.", llColorPurple1);
        }
        private void addSecOwner(){
            String fName = "[addSecOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(gNewUserProfile.cAUTH.isOwned()){
                logger.warn(fName + ".no access");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied, you are owned!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.getSecOwnerLength()>2){
                logger.warn(fName + ".max secowners");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Limit reached", llColorRed); return;
            }
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(0);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(member.getId().equals(gUser.getId())){
                logger.warn(fName + ".sec owner cant be the wearer");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You can't name yourself as sec-owner!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.hasSecOwner(member.getId())){
                logger.warn(fName + ".has this secowner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Already entered as sec-owner", llColorRed); return;
            }
            gNewUserProfile.cAUTH.addSecOwner(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Added "+member.getAsMention()+" as your sec-owner.", llColorPurple1);
            llSendQuickEmbedMessage(member.getUser(),sMainRTitle,gUser.getAsMention()+" added you as their sec-owner!", llColorPurple1);
        }
        private void removeSecOwner(){
            String fName = "[removeOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(gNewUserProfile.cAUTH.isOwned()){
                logger.warn(fName + ".no access");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied, you are owned!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.getSecOwnerLength()==0){
                logger.warn(fName + ".no secowners");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No sec-owners", llColorRed); return;
            }
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(0);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(!gNewUserProfile.cAUTH.hasSecOwner(member.getId())){
                logger.warn(fName + ".has this secowner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Mentioned user is not a sec-owner", llColorRed); return;
            }
            gNewUserProfile.cAUTH.remSecOwner(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Removed "+member.getAsMention()+" as your sec-owner.", llColorPurple1);
            llSendQuickEmbedMessage(member.getUser(),sMainRTitle,gUser.getAsMention()+" removed you as their sec-owner!", llColorPurple1);
        }
        private void acceptSub(Member mtarget){
            String fName = "[acceptSub]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".has NO Owner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
            }
            if(!gNewUserProfile.cAUTH.isOwner(gUser)){
                logger.warn(fName + ".not a match");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                logger.info(fName + ".owner accepted");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You already accepted this", llColorRed); return;
            }
            gNewUserProfile.cAUTH.setOwnerAccepted(true);
            if(gNewUserProfile.cAUTH.hasSecOwner(gMember.getId())){
                logger.info(fName + ".has secowner entry>remove");
                gNewUserProfile.cAUTH.remSecOwner(gMember.getId());
            }
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"You accepted "+target.getAsMention()+" leash!" ,llColorGreen2);
            llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" accepted your ownership contract!", llColorGreen2);
        }
        private void rejectSub(Member mtarget){
            String fName = "[rejectSub]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".has NO Owner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
            }
            if(!gNewUserProfile.cAUTH.isOwner(gUser)){
                logger.warn(fName + ".not a match");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                logger.info(fName + ".owner accepted");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You already accepted this", llColorRed); return;
            }
            gNewUserProfile.cAUTH.setOwnerAccepted(false);gNewUserProfile.cAUTH.setOwnerId("");
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"You rejected "+target.getAsMention()+" leash!" ,llColorRed_CoralPink);
            llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" rejected your ownership contract!", llColorRed_CoralPink);
        }
        private void releaseSub(Member mtarget){
                String fName = "[releaseKeyHolder]";
                logger.info(fName);
                logger.info(fName);
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gNewUserProfile.cAUTH.hasOwner()){
                    logger.warn(fName + ".has NO Owner");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
                }
                if(!gNewUserProfile.cAUTH.isOwner(gUser)){
                    logger.warn(fName + ".not a match");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied", llColorRed); return;
                }
                if(!gNewUserProfile.cAUTH.isOwnerAccepted()){
                    logger.info(fName + ".owner accepted");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,"You not yet accepted their contract to release them.", llColorRed); return;
                }
                gNewUserProfile.cAUTH.setOwnerAccepted(false);gNewUserProfile.cAUTH.setOwnerId("");
                if(!saveProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You released "+target.getAsMention()+" leash!" ,llColorGreen2);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" releases you from ownership contract!", llColorGreen2);

            }
        private void addSecOwner(Member mtarget){
            String fName = "[addSecOwner]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)){
                logger.warn(fName + ".no access");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.getSecOwnerLength()>2){
                logger.warn(fName + ".max secowners");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Limit reached", llColorRed); return;
            }
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()||members.size()<2){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(1);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(member.getId().equals(gUser.getId())){
                logger.warn(fName + ".sec owner cant be the wearer");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You can't name yourself as sec-owner!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.hasSecOwner(member.getId())){
                logger.warn(fName + ".has this secowner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Already entered as sec-owner", llColorRed); return;
            }
            gNewUserProfile.cAUTH.addSecOwner(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Added "+member.getAsMention()+" as "+target.getAsMention()+" sec-owner.", llColorPurple1);
            llSendQuickEmbedMessage(member.getUser(),sMainRTitle,gUser.getAsMention()+" added you as "+target.getAsMention()+" sec-owner!", llColorPurple1);
            llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" added "+member.getAsMention()+" as your sec-owner!", llColorPurple1);
        }
        private void removeSecOwner(Member mtarget){
            String fName = "[removeOwner]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)){
                logger.warn(fName + ".no access");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Denied!", llColorRed); return;
            }
            if(gNewUserProfile.cAUTH.getSecOwnerLength()==0){
                logger.warn(fName + ".no secowners");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No sec-owners", llColorRed); return;
            }
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()||members.size()<2){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(1);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(!gNewUserProfile.cAUTH.hasSecOwner(member.getId())){
                logger.warn(fName + ".has this secowner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Mentioned user is not a sec-owner", llColorRed); return;
            }
            gNewUserProfile.cAUTH.remSecOwner(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,"Removed "+member.getAsMention()+" as "+target.getAsMention()+" sec-owner.", llColorPurple1);
            llSendQuickEmbedMessage(member.getUser(),sMainRTitle,gUser.getAsMention()+" removed you as "+target.getAsMention()+" sec-owner!", llColorPurple1);
            llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" removed "+member.getAsMention()+" as your sec-owner!", llColorPurple1);
        }
        private void listAuth(Member mtarget){
            String fName = "[removeOwner]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorBlue1);
            embed.setTitle(target.getName()+" auth status");
            if(gNewUserProfile.cAUTH.isOwned()){
                embed.addField("Owner",llGetMemberMention(gGuild,gNewUserProfile.cAUTH.getOwnerId()),true);
            }
            if(gNewUserProfile.cAUTH.getSecOwnerLength()>0){
                Iterator<String> keys=gNewUserProfile.cAUTH.getSecOwnerJSON().keys();
                StringBuilder tmp = new StringBuilder();
                while (keys.hasNext()) {
                    String id = keys.next();
                    tmp.append("\n").append(llGetMemberMention(gGuild, id));
                }
                embed.addField("Sec-Owners", tmp.toString(),true);
            }
            String access;
            
            if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){ access = "ask";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){ access = "ask";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){ access = "ask+";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName())){ access = "owner&secowner";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName())){access = "key";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){ access = "exposed";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName())){access = "public";}
            else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){access = "protected";}
            else{ access = "invalid"; }
            embed.addBlankField(false);
            embed.addField("Access",access,true);
            llSendMessageWithDelete(gGlobal,gTextChannel,embed);
        }
        private void adminAddOwner(Member mtarget){
            String fName = "[adminAddOwner]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            List<Member> members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(fName + ".no mention");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"No mention!", llColorRed); return;
            }
            Member member=members.get(0);
            logger.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
            if(mtarget==null){
                if(member.getIdLong()==gUser.getIdLong()){
                    logger.warn(fName + ".owner cant be the wearer");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("You can't name yourself as owner!"), llColorRed); return;
                }
            }else{
                if(member.getIdLong()==mtarget.getIdLong()){
                    logger.warn(fName + ".owner cant be the wearer");
                    llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("You can't name !TARGET as onwer to themselves!"), llColorRed); return;
                }
            }

            gNewUserProfile.cAUTH.setOwnerAccepted(true); gNewUserProfile.cAUTH.setOwnerId(member.getId());
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            if(mtarget==null){
                llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("Added "+member.getAsMention()+" as your owner. Now they need to confirm it!"), llColorPurple1);
            }else{
                llSendQuickEmbedMessage(gTarget.getUser(),sMainRTitle,stringReplacer("Added "+member.getAsMention()+" as your owner by "+gMember.getAsMention()+"."),  llColorPurple1);
                llSendQuickEmbedMessage(member.getUser(),sMainRTitle,stringReplacer("Added you as !TARGET's owner by "+gMember.getAsMention()+"."),  llColorPurple1);
                llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("Added "+member.getAsMention()+" as !TARGET's owner. by you"),  llColorPurple1);
                return;
            }

        }
        private void adminReleaseSub(Member mtarget){
            String fName = "[adminReleaseSub]";
            logger.info(fName);
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gNewUserProfile.cAUTH.hasOwner()){
                logger.warn(fName + ".has NO Owner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("!TARGET has no owner!"), llColorRed); return;
            }

            gNewUserProfile.cAUTH.setOwnerAccepted(false);gNewUserProfile.cAUTH.setOwnerId("");
            if(!saveProfile()){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sMainRTitle,stringReplacer("You released !TARGET from the contract!") ,llColorGreen2);
            llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,stringReplacer("!USER releases you from ownership contract!"), llColorGreen2);

        }

        private void rAccess(String command){
            String fName="[rAccess]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            /*if(!gIsOverride&&gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(levelSuitSpecialToyOmega)){
                logger.info(fName + ".can't use do to special suit");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Warning, you are wearing a special toy suit. First run `"+llPrefixStr+"suit release` to take off the suit to change your access mode.", llColorRed);
                return;
            }*/
            if(!gIsOverride&&gNewUserProfile.cAUTH.isOwned()){
                logger.warn(fName + ".no access");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are owned!", llColorRed); return;
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: key", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: public", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||command.equalsIgnoreCase("protect")||command.equalsIgnoreCase("private")){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: protected", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
            }
            else if(command.equalsIgnoreCase("expose")||command.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||command.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: exposed", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||command.equalsIgnoreCase("slave")){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: pet", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: ask", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You set your access to: ask. Additionally you enable to also send the ask request to your owner.", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs, however they also enabled to send the ask request to their owner as well.");
            }
            saveProfile();
        }
        private void rAskRedirect(String command){
            String fName="[rAskRedirect]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Can't manipulate your locks as access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(optionEnable)){
                    logger.info(fName + ".enable");
                    gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                    llSendQuickEmbedMessage(gUser,sMainRTitle,"You enable to optionally send your ask mode requests to owner, not just to the target.", llColorBlue1);
                    if(gNewUserProfile.cAUTH.isOwned()){
                        llSendQuickEmbedMessage(gNewUserProfile.cAUTH.getOwnerAsUser(gGuild),sMainRTitle,gUser.getAsMention()+" enabled sending their ask request to the owner.", llColorBlue1);
                    }
            }
            else if(command.equalsIgnoreCase(optionDisable)){
                logger.info(fName + ".disable");
                gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(false);
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You disable to optionally send your ask mode requests to owner.", llColorBlue1);
                if(gNewUserProfile.cAUTH.isOwned()){
                    llSendQuickEmbedMessage(gNewUserProfile.cAUTH.getOwnerAsUser(gGuild),sMainRTitle,gUser.getAsMention()+" disabled sending their ask request to the owner.", llColorBlue1);
                }
            }
            saveProfile();
        }


        private void rAccess(Member mtarget,String command){
            String fName="[rAccess]";
            logger.info(fName);
            User target=gTarget.getUser();
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            /*if(!gIsOverride&&gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(levelSuitSpecialToyOmega)){
                logger.info(fName + ".can't use do to special suit");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"Warning, they are wearing a special toy suit. First run `"+llPrefixStr+"suit <target> release` to take off the suit to change their access mode.", llColorRed);
                return;
            }*/
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.warn(fName + ".no access");
                sendOrUpdatePrivateEmbed(sRTitle,iAuth.deniedOnlyOwnerAccess, llColorRed); return;
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: key", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+"'s access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: public", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+"s access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||command.equalsIgnoreCase("protect")||command.equalsIgnoreCase("private")){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: protected", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+"s access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
            }
            else if(command.equalsIgnoreCase("expose")||command.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||command.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: exposed", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+"s access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||command.equalsIgnoreCase("slave")){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: pet", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+" access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: ask", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs.");
            }
            else if(command.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getName()+" access to: ask. Additionally you enabled for their ask request to be also sent to their owner.", llColorBlue1);
                llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+target.getName()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs, however, they also have their  ask request sent to their owner as well.");
            }

            saveProfile();
        }
        private void rAskRedirect(Member mtarget,String command){
            String fName="[rAskRedirect]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(gTarget==null)gTarget=mtarget;
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.warn(fName + ".no access");
                llSendQuickEmbedMessage(gUser,sMainRTitle,iAuth.deniedOnlyOwnerAccess, llColorRed); return;
            }
            if(command.equalsIgnoreCase(optionEnable)){
                logger.info(fName + ".enable");
                gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You enable to send optionally all "+mtarget.getAsMention()+"'s ask mode requests to owner.", llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" enabled your ask mode request to also be sent to your owner.", llColorBlue1);
                User owner=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                if(gNewUserProfile.cAUTH.isOwned()&&owner!=null&&gUser.getIdLong()!=owner.getIdLong()){
                    llSendQuickEmbedMessage(owner,sMainRTitle,gUser.getAsMention()+" enabled "+mtarget.getAsMention()+"'s ask mode request to also be sent to their owner.", llColorBlue1);
                }
            }
            else if(command.equalsIgnoreCase(optionDisable)){
                logger.info(fName + ".disable");
                gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(false);
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You disable to send optionally "+mtarget.getAsMention()+"'s ask mode requests to owner.", llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" disabled your ask mode request to also be sent to your owner.", llColorBlue1);
                User owner=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                if(gNewUserProfile.cAUTH.isOwned()&&owner!=null&&gUser.getIdLong()!=owner.getIdLong()){
                    llSendQuickEmbedMessage(owner,sMainRTitle,gUser.getAsMention()+" disabled "+mtarget.getAsMention()+"'s ask mode request to also be sent to their owner.", llColorBlue1);
                }
            }
            saveProfile();
        }
        private void userNotification(String desc){
            String fName="[userNotification]";
            //logger.info(fName+"area="+area);
            logger.info(fName+"desc="+desc);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                    llSendMessageWithDelete(gGlobal,gTextChannel,desc);
                }
            }
        }
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                    if(gTarget==null)gTarget=mtarget;
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsOthers();
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        String gCommandFileMainPath =gFileMainPath+"auth/menuLevels.json",gAskFilePath=gFileMainPath+"auth/ask.json";
        private void menuLevelsWearer(){
            String fName="[menuLevelsWearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                String access="";
                if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){ access = "ask";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){ access = "ask";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){ access = "ask+";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName())){ access = "owner&secowner";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName())){access = "key";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){ access = "exposed";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName())){access = "public";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){access = "protected";}
                else{ access = "invalid"; }
                embed.addField("Access",access,false);
                desc = new StringBuilder();
                if(gNewUserProfile.cAUTH.hasOwner()){
                    logger.warn(fName + ".hasOwner");
                    User owner=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                    if(owner==null){
                        desc = new StringBuilder("<" + gNewUserProfile.cAUTH.getOwnerId() + ">");
                    }else{
                        desc = new StringBuilder(owner.getAsMention());
                    }
                    if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                        desc.append(" (accepted)");
                    }else{
                        desc.append(" (pending)");
                    }
                }else{
                    desc = new StringBuilder("n/a");
                }
                embed.addField("Owner", desc.toString(),false);
                desc = new StringBuilder();
                if(gNewUserProfile.cAUTH.getSecOwnerLength()>0){
                    List<String>secowners=gNewUserProfile.cAUTH.getSecOwnerAsUserMentionList(gGuild);
                    for(String secowner:secowners){
                        desc.append("\n").append(secowner);
                    }
                }else{
                    desc = new StringBuilder("N/a");
                }
                embed.addField("Secowner", desc.toString(), false);
                desc = new StringBuilder();
                if(gNewUserProfile.cLOCK.isLocked()){
                    String lockedById=gNewUserProfile.cLOCK.getLockedByAsString();
                    if(lockedById.equalsIgnoreCase(gNewUserProfile.getMember().getId())){
                        desc.append("\nBy: self");
                    }else{
                        String lockedByMention=llGetMemberMention(gGuild,lockedById);
                        if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){

                            desc.append("\nBy: <").append(lockedById).append(">");
                        }else{
                            desc.append("\nBy: ").append(lockedByMention);

                        }
                    }
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        desc.append("\n(permalock)");
                    }
                    embed.addField("Locked", desc.toString(), false);
                    desc = new StringBuilder();
                }
                if(!gNewUserProfile.cAUTH.hasOwner()||!gNewUserProfile.cAUTH.isOwned()){
                    desc = new StringBuilder();
                    if(!gNewUserProfile.cAUTH.hasOwner()){
                        desc.append("To **add owner** `" + llPrefixStr).append(quickAlias).append(" owner add <@User>`\nWill ask the user if they want to be your owner or not. They can reject the proposition.");
                    }else{
                        desc.append("To **remove owner** `" + llPrefixStr).append(quickAlias).append(" owner remove <@User>` \nWorks only if owner has not accepted the proposition!");
                    }
                    if(!gNewUserProfile.cAUTH.isOwned()){
                        if(!desc.toString().isBlank()){
                           desc.append("\n");
                        }
                        desc.append("To **add sec-owner** `" + llPrefixStr).append(quickAlias).append(" secowner add <@User>`\nMax 3 sec-owners. Pet cant add if owned.");
                        desc.append("\nTo **remove sec-owner** `" + llPrefixStr).append(quickAlias).append(" secowner remove <@User>` \n Pet can't remove if owned.");
                    }
                    embed.addField("Commands", desc.toString(), false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                desc = new StringBuilder();
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Ask.getName()).append(", asks you first before applying it");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.ExposedOld.getName()).append(", anyone including the sub has access");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Public.getName()).append(",  sets that anyone can use the sub except the sub has no access to its restraints");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Key.getName()).append(", anyone until locked, then only who locked has");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Protected.getName()).append(", only the sub and its owner,sec-owner have access to its restraints");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Pet.getName()).append(", only sub's owner&sec-owner have access to its restraints");
                if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).append(" unlock restraints");
                }else if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).append(" lock restraints");
                }
                if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire)).append(" lock restraints permanently (hard core)");
                }
                if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff)).append(" to disable redirect ask to owner");
                }else{
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)).append(" to enable redirect ask to owner");
                }
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSOS)).append(" runaway (safeword)");
                desc.append("\nLock and Permalock moved to `!>lock`");
                embed.setDescription(desc.toString());
                MessageEmbed messageEmbed=embed.build();
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(!gIsOverride&&!gNewUserProfile.cAUTH.isOwned()) {
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName()) && !gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if (!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName()))
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                }
                if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
                }else if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                }
                if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire));
                }
                lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSOS));*/
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent.Button buttonRedirect=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                    if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                        buttonRedirect.setCustomId(lsUnicodeEmotes.aliasMobilePhoneOff).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff));
                    }else{
                        buttonRedirect.setCustomId(lsUnicodeEmotes.aliasVibrationMode).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                    }
                    messageComponentManager.selectContainer= messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName()) && !gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);
                    if(!gIsOverride&&gNewUserProfile.cAUTH.isOwned()) {
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsWearerListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsOthers(){
            String fName="[menuLevelsOthers]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);

                String access="";

                if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){ access = "ask";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){ access = "ask";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){ access = "ask+";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName())){ access = "owner&secowner";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName())){access = "key";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){ access = "exposed";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName())){access = "public";}
                else if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){access = "protected";}
                else{ access = "invalid"; }
                embed.addField("Access",access,false);
                desc = new StringBuilder();
                if(gNewUserProfile.cAUTH.hasOwner()){
                    logger.warn(fName + ".hasOwner");
                    User owner=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                    if(owner==null){
                        desc = new StringBuilder("<" + gNewUserProfile.cAUTH.getOwnerId() + ">");
                    }else{
                        desc = new StringBuilder(owner.getAsMention());
                    }
                    if(!gNewUserProfile.cAUTH.isOwnerAccepted()){
                        desc.append(" (pending)");
                    }
                }else{
                    desc = new StringBuilder("n/a");
                }
                embed.addField("Owner", desc.toString(),false);
                desc = new StringBuilder();
                if(gNewUserProfile.cAUTH.getSecOwnerLength()>0){
                    List<String>secowners=gNewUserProfile.cAUTH.getSecOwnerAsUserMentionList(gGuild);
                    for(String secowner:secowners){
                        desc.append("\n").append(secowner);
                    }
                }else{
                    desc = new StringBuilder("N/a");
                }
                embed.addField("Secowner", desc.toString(), false);
                desc = new StringBuilder();
                if(gNewUserProfile.cLOCK.isLocked()){
                    String lockedById=gNewUserProfile.cLOCK.getLockedByAsString();
                    if(lockedById.equalsIgnoreCase(gNewUserProfile.getMember().getId())){
                        desc.append("\nBy: self");
                    }else{
                        String lockedByMention=llGetMemberMention(gGuild,lockedById);
                        if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){

                            desc.append("\nBy: <").append(lockedById).append(">");
                        }else{
                            desc.append("\nBy: ").append(lockedByMention);

                        }
                    }
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        desc.append("\n(permalock)");
                    }
                    embed.addField("Locked", desc.toString(), false);
                    desc = new StringBuilder();
                }
                embed.addField(" ","Please select an option for: "+gNewUserProfile.getMember().getAsMention()+".",false);
                desc = new StringBuilder();
                if(gNewUserProfile.cAUTH.isOwner(gMember)){
                   if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                       desc.append("Releasing the pet from your ownership `" + llPrefixStr).append(quickAlias).append(" <@Pet> owner release`");
                       desc.append("\nTo add owner type in `" + llPrefixStr).append(quickAlias).append(" <@Pet> secowner add <@User>`\nMax 3 sec-owners. Pet cant add if owned.");
                       desc.append("\nTo remove type in `" + llPrefixStr).append(quickAlias).append(" <@Pet> secowner remove <@User>` \n Pet can't remove if owned.");
                   }else{
                       desc.append("proposed owner respond to the sub proposition by typing `" + llPrefixStr).append(quickAlias).append(" <@Pet> owner <accept/reject>`");
                      }
                   embed.addField("Commands", desc.toString(), false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                desc = new StringBuilder();
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Ask.getName()).append(", asks you first before applying it");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.ExposedOld.getName()).append(", anyone including the sub has access");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Public.getName()).append(",  sets that anyone can use the sub except the sub has no access to its restraints");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Key.getName()).append(", anyone until locked, then only who locked has");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Protected.getName()).append(", only the sub and its owner,sec-owner have access to its restraints");
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)).append(strAccessWithDualSpacing).append(ACCESSLEVELS.Pet.getName()).append(", only sub's owner&sec-owner have access to its restraints");
                if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).append(" unlock restraints");
                }else if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).append(" lock restraints");
                }
                if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire)).append(" lock restraints permanently (hard core)");
                }
                if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff)).append(" to disable redirect ask to owner");
                }else{
                    desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)).append(" to enable redirect ask to owner");
                }
                desc.append("\n").append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors)).append(" to release pet");
                desc.append("\nLock and Permalock moved to `!>lock`");
                embed.setDescription(desc.toString());
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gMember.getId())||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gMember.getId())){
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())&&!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(!gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff));
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                    }
                }
                if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
                }else if(!gNewUserProfile.cLOCK.isPermaLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                }
                if(gIsOverride||((gNewUserProfile.cAUTH.hasUserOwnerAccess(gMember.getId())||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gMember.getId()))&&!gNewUserProfile.cLOCK.isPermaLocked())){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire));
                }
                if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gMember.getId())){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                }*/
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Button=messageComponentManager.messageBuildComponents.getComponent(1);lcMessageBuildComponent.Button button=null;
                    messageComponentManager.selectContainer= messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Ask.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Exposed.getName()) && !gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Key.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                    if (gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName()))
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);
                    lcMessageBuildComponent.Button buttonRedirect=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                    if(gNewUserProfile.cAUTH.isRedirectAsk2OwnerAsWell()){
                        buttonRedirect.setCustomId(lsUnicodeEmotes.aliasMobilePhoneOff).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff));
                    }else{
                        buttonRedirect.setCustomId(lsUnicodeEmotes.aliasVibrationMode).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                    }
                    lcMessageBuildComponent.Button buttonSos=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    buttonSos.setCustomId(lsUnicodeEmotes.aliasScissors).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                    if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gMember.getId())&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gMember.getId())){
                        messageComponentManager.selectContainer.setDisabled();
                        buttonRedirect.setDisable();
                        buttonSos.setDisable();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsOthersListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message){
            String fName="[menuLevelsWearer]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasLock:level=optionLock; break;
                                    case lsUnicodeEmotes.aliasUnlock:level=optionUnlock;break;
                                    case lsUnicodeEmotes.aliasVibrationMode:level=optionAskredirecOn;break;
                                    case lsUnicodeEmotes.aliasMobilePhoneOff:level=optionAskredirecOff;break;
                                    case lsUnicodeEmotes.aliasFire:level=commandPermalock;break;
                                    case lsUnicodeEmotes.aliasSOS:level="sos";break;
                                }

                                logger.info(fName+"level="+level);

                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("sos")){
                                        rRunAway();
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOn)){
                                        rAskRedirect(optionEnable);
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOff)){
                                        rAskRedirect(optionDisable);
                                    }else
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rAccess(level);
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String>values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"values[0]="+value);
                                String level="";llMessageDelete(message);
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level=ACCESSLEVELS.Ask.getName();break;
                                    case lsUnicodeEmotes.aliasOne:level=ACCESSLEVELS.Exposed.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=ACCESSLEVELS.Public.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=ACCESSLEVELS.Key.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=ACCESSLEVELS.Protected.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=ACCESSLEVELS.Pet.getName();break;
                                }
                                logger.info(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("sos")){
                                        rRunAway();
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOn)){
                                        rAskRedirect(optionEnable);
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOff)){
                                        rAskRedirect(optionDisable);
                                    }else
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        rAccess(level);
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){level=optionLock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){level=optionUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){level=optionAskredirecOn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff))){level=optionAskredirecOff;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire))){level=commandPermalock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSOS))){level="sos";}
                                    logger.info(fName+"level="+level);

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("sos")){
                                            rRunAway();
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOn)){
                                            rAskRedirect(optionEnable);
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOff)){
                                            rAskRedirect(optionDisable);
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rAccess(level);
                                        }
                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                if(gCurrentInteractionHook!=null)removeAction(message);
                                llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);}
                        );
                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){level=optionLock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){level=optionUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){level=optionAskredirecOn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff))){level=optionAskredirecOff;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire))){level=commandPermalock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSOS))){level="sos";}
                                    logger.info(fName+"level="+level);

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("sos")){
                                            rRunAway();
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOn)){
                                            rAskRedirect(optionEnable);
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOff)){
                                            rAskRedirect(optionDisable);
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rAccess(level);
                                        }
                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsOthersListener(Message message){
            String fName="[menuLevelsOthers]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String>values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"values[0]="+value);
                                String level="";llMessageDelete(message);
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.aliasZero:level=ACCESSLEVELS.Ask.getName();break;
                                    case lsUnicodeEmotes.aliasOne:level=ACCESSLEVELS.Exposed.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=ACCESSLEVELS.Public.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=ACCESSLEVELS.Key.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=ACCESSLEVELS.Protected.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=ACCESSLEVELS.Pet.getName();break;
                                }
                                logger.info(fName+"level="+level);
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("sos")){
                                        rRunAway();
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOn)){
                                        rAskRedirect(optionEnable);
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOff)){
                                        rAskRedirect(optionDisable);
                                    }else
                                    {
                                        rAccess(level);
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsOthers();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES,() -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";break;
                                    case lsUnicodeEmotes.aliasLock:level=optionLock; break;
                                    case lsUnicodeEmotes.aliasUnlock:level=optionUnlock;break;
                                    case lsUnicodeEmotes.aliasVibrationMode:level=optionAskredirecOn;break;
                                    case lsUnicodeEmotes.aliasMobilePhoneOff:level=optionAskredirecOff;break;
                                    case lsUnicodeEmotes.aliasFire:level=commandPermalock;break;
                                    case lsUnicodeEmotes.aliasSOS:level="sos";break;
                                }
                                logger.info(fName+"level="+level);

                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase("sos")){
                                        rRunAway();
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOn)){
                                        rAskRedirect(optionEnable);
                                    }else
                                    if(level.equalsIgnoreCase(optionAskredirecOff)){
                                        rAskRedirect(optionDisable);
                                    }else
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        rAccess(level);
                                    }
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsOthers();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){level=optionLock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){level=optionUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){level=optionAskredirecOn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff))){level=optionAskredirecOff;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire))){level=commandPermalock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){level="release";}
                                    logger.info(fName+"level="+level);

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("release")){
                                            releaseSub(gNewUserProfile.getMember());
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOn)){
                                            rAskRedirect(gNewUserProfile.getMember(),optionEnable);
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOff)){
                                            rAskRedirect(gNewUserProfile.getMember(),optionDisable);
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rAccess(gNewUserProfile.getMember(),level);
                                        }

                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                if(gCurrentInteractionHook!=null)removeAction(message);
                                llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);}
                                );
                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){level=optionLock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){level=optionUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){level=optionAskredirecOn;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMobilePhoneOff))){level=optionAskredirecOff;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFire))){level=commandPermalock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){level="release";}
                                    logger.info(fName+"level="+level);

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase("release")){
                                            releaseSub(gNewUserProfile.getMember());
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOn)){
                                            rAskRedirect(gNewUserProfile.getMember(),optionEnable);
                                        }else
                                        if(level.equalsIgnoreCase(optionAskredirecOff)){
                                            rAskRedirect(gNewUserProfile.getMember(),optionDisable);
                                        }else
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rAccess(gNewUserProfile.getMember(),level);
                                        }

                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }

        private void askingKeyholder2AcceptOwnership(){
            String fName = "[askingKeyholder2AcceptOwnership]";
            logger.info(fName);
            try{
                Member owner=gNewUserProfile.cAUTH.getOwnerAsMember(gGuild);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(sMainRTitle).setColor(llColorPurple1);
                embedBuilder.setDescription(gUser.getAsMention()+" added you as their owner! Please confirm or deny it by typing in the server channel. \nAccept:`!>auth "+gUser.getAsMention()+" owner accept`\nReject `!>auth "+gUser.getAsMention()+" owner reject`. Or use the reactions.");
                messageComponentManager.loadMessageComponents(gAskFilePath);
                Message message=null;
                try {
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=lsMessageHelper.lsSendEmbed(owner.getUser(),embedBuilder,messageComponentManager.messageBuildComponents.getAsActionRows());
                    if(message==null)throw  new Exception("could not create message with actionrows");
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=lsMessageHelper.lsSendMessageResponse(owner.getUser(),embedBuilder);
                }
                askingKeyholder2AcceptOwnershipListener(message,owner);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void askingKeyholder2AcceptOwnershipListener(Message message,Member owner){
            String fName = "[askingKeyholder2AcceptOwnershipListener]";
            logger.info(fName);
            try{
                logger.info(fName + "owner=" + owner.getId());
                logger.info(fName + "wearer=" + gNewUserProfile.getMember().getId());
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            //if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasGreenCircle: Keyholder2AcceptOwnershipResponseListener("1",owner);break;
                                    case lsUnicodeEmotes.aliasRedCircle:Keyholder2AcceptOwnershipResponseListener("0",owner); break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                if(!message.isFromGuild()){
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(owner.getId())),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(!gNewUserProfile.cAUTH.hasOwner()||!gNewUserProfile.cAUTH.isOwner(owner)){
                                        logger.warn(fName + ".has NO Owner or Not a match");
                                        llSendQuickEmbedMessage(owner.getUser(),sMainRTitle,"Denied!\nIts possible they change it or removed it!", llColorRed); return;
                                    }
                                    if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                        llMessageDelete(message);
                                        Keyholder2AcceptOwnershipResponseListener("1",owner);
                                    }else
                                    if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThumbsDown))) {
                                        llMessageDelete(message);
                                        Keyholder2AcceptOwnershipResponseListener("0",owner);
                                    }else{
                                        askingKeyholder2AcceptOwnershipListener(message,owner);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void Keyholder2AcceptOwnershipResponseListener(String command,Member owner){
            String fName = "[askingKeyholder2AcceptOwnershipListener]";
            logger.info(fName);
            try{
                logger.info(fName + "ommand=" + command);
                logger.info(fName + "owner=" + owner.getId());
                logger.info(fName + "wearer=" + gNewUserProfile.getMember().getId());
                switch (command){
                    case "accept" :case "1":
                        if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                            logger.info(fName + ".owner accepted");
                            llSendQuickEmbedMessage(owner.getUser(),sMainRTitle,"You already accepted this", llColorRed); return;
                        }
                        gNewUserProfile.cAUTH.setOwnerAccepted(true);
                        if(gNewUserProfile.cAUTH.getSecOwnerJSON().has(owner.getUser().getId())){
                            logger.info(fName + ".has secowner entry>remove");
                            gNewUserProfile.cAUTH.getSecOwnerJSON().remove(owner.getUser().getId());
                        }
                        if(!saveProfile()){
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
                        llSendQuickEmbedMessage(owner.getUser(),sMainRTitle,"You accepted "+gUser.getAsMention()+" leash!" ,llColorGreen2);
                        llSendQuickEmbedMessage(gUser,sMainRTitle,owner.getAsMention()+" accepted your ownership contract!", llColorGreen2);

                        break;
                    case "reject": case "0":
                        if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                            logger.info(fName + ".owner accepted");
                            llSendQuickEmbedMessage(owner.getUser(),sMainRTitle,"You already accepted this", llColorRed); return;
                        }
                        gNewUserProfile.cAUTH.setOwnerAccepted(false);gNewUserProfile.cAUTH.setOwnerId("");
                        if(!saveProfile()){
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Failed to write in Db!", llColorRed); return;}
                        llSendQuickEmbedMessage(owner.getUser(),sMainRTitle,"You rejected "+gUser.getAsMention()+" leash!" ,llColorRed_CoralPink);
                        llSendQuickEmbedMessage(gUser,sMainRTitle,owner.getAsMention()+" rejected your ownership contract!", llColorRed_CoralPink);
                        break;
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                User reqUser=null;
                boolean subdirProvided=false;
                String subcommand="",subcommandgroup="";
                subcommand=gSlashCommandEvent.getSubcommandName();subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
                if(subcommand==null)subcommand="";if(subcommandgroup==null)subcommandgroup=null;
                logger.info(fName + ".subcommand="+subcommand+", subcommandgroup="+subcommandgroup);
                boolean levelProvided=false; String levelValue="";
                Member owmer=null;
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()==OptionType.USER){
                                reqUser=option.getAsUser();
                            }
                            break;
                        case "owner":
                            if(option.getType()==OptionType.USER){
                                owmer=option.getAsMember();
                            }
                            break;
                        case llCommonKeys.SlashCommandReceive.level:
                            if(option.getType()==OptionType.STRING){
                                levelProvided=true;
                                levelValue=option.getAsString();
                            }
                            break;
                    }
                }
                slashReplyPleaseWait();
                if(reqUser!=null&&gMember.getIdLong()!=reqUser.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,reqUser);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                gCurrentInteractionHook=gSlashInteractionHook;
                if(subdirProvided){
                    menuLevels(gTarget);
                    return;
                }
                if(gTarget==null){
                    switch (subcommand.toLowerCase()){
                        case "menu":
                            menuLevels(gTarget);
                            break;
                        case "access":
                            if(!levelProvided){
                                sendOrUpdatePrivateEmbed(sRTitle,"Level required", llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                if(!gIsOverride&&gNewUserProfile.cAUTH.isOwned()){
                                    logger.warn(fName + ".no access");
                                    sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are owned!", llColorRed); return;
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: key", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: public", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||levelValue.equalsIgnoreCase("protect")||levelValue.equalsIgnoreCase("private")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: protected", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
                                }
                                else if(levelValue.equalsIgnoreCase("expose")||levelValue.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||levelValue.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: exposed", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||levelValue.equalsIgnoreCase("slave")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: pet", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: ask", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                                    gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: ask. Additionally you enable to also send the ask request to your owner.", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs, however they also enabled to send the ask request to their owner as well.");
                                }
                            }else{
                                if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                                    logger.warn(fName + ".no access");
                                    sendOrUpdatePrivateEmbed(sRTitle,iAuth.deniedOnlyOwnerAccess, llColorRed);
                                    return;
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: key", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"'s access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: public", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||levelValue.equalsIgnoreCase("protect")||levelValue.equalsIgnoreCase("private")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: protected", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
                                }
                                else if(levelValue.equalsIgnoreCase("expose")||levelValue.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||levelValue.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: exposed", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||levelValue.equalsIgnoreCase("slave")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: pet", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: ask", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                                    gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: ask. Additionally you enabled for their ask request to be also sent to their owner.", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs, however, they also have their  ask request sent to their owner as well.");
                                }
                            }
                            break;
                        case "owner_add":
                            if(owmer==null){
                                sendOrUpdatePrivateEmbed(sRTitle,"Need to mention owner is required!", llColorRed_Barn);
                                return;
                            }
                            if(owmer.getIdLong()==gMember.getIdLong()){
                                sendOrUpdatePrivateEmbed(sRTitle,"Can't mention yourself!", llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.cAUTH.hasOwner()){
                                logger.warn(fName + ".hasOwner");
                                sendOrUpdatePrivateEmbed(sRTitle,"Already have an owner", llColorRed_Barn);
                                return;
                            }
                            gNewUserProfile.cAUTH.setOwnerAccepted(false); gNewUserProfile.cAUTH.setOwnerId(owmer.getId());
                            if(!saveProfile()){
                                sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strFailedToSave, llColorRed);
                                return;
                            }
                            sendOrUpdatePrivateEmbed(sRTitle,"Added "+owmer.getAsMention()+" as your owner. Now they need to confirm it!", llColorPurple1);
                            askingKeyholder2AcceptOwnership();
                            break;
                       case "owner_reject":
                           if(!gNewUserProfile.cAUTH.hasOwner()){
                               logger.warn(fName + ".hasOwner");
                               sendOrUpdatePrivateEmbed(sRTitle,"You don't have an owner!", llColorRed_Barn);
                               return;
                           }
                           if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                               logger.warn(fName + ".owner acceptedr");
                               sendOrUpdatePrivateEmbed(sRTitle,"Owner already accepted it, can't take back!", llColorRed_Barn);
                               return;
                           }
                           owmer=gNewUserProfile.cAUTH.getOwnerAsMember(gGuild);
                           gNewUserProfile.cAUTH.setOwnerAccepted(false).setOwnerId("");
                           if(!saveProfile()){
                               sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strFailedToSave, llColorRed);
                               return;
                           }
                           sendOrUpdatePrivateEmbed(sRTitle,"You rejected to be owned.", llColorPurple1);
                           Message message=llSendQuickEmbedMessageResponse(owmer.getUser(),sMainRTitle,stringReplacer("!WEARER taken back the ownership before you accepted it!"), llColorPurple1);
                           break;
                        case "owner_accept":
                            sendPrivateEmbed(sMainRTitle,"Only the owner can use this!",llColorRed_Barn);
                            break;
                    }
                }else{
                    switch (subcommand.toLowerCase()){
                        case "menu":
                            menuLevels(gTarget);
                            break;
                        case "access":
                            if(!levelProvided){
                                sendOrUpdatePrivateEmbed(sRTitle,"Level required", llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                if(!gIsOverride&&gNewUserProfile.cAUTH.isOwner(gMember)){
                                    logger.warn(fName + ".no access");
                                    sendOrUpdatePrivateEmbed(sRTitle,"You are not their owner!", llColorRed); return;
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: key", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets their access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: public", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||levelValue.equalsIgnoreCase("protect")||levelValue.equalsIgnoreCase("private")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: protected", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
                                }
                                else if(levelValue.equalsIgnoreCase("expose")||levelValue.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())||levelValue.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: exposed", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||levelValue.equalsIgnoreCase("slave")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: pet", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: ask", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                                    gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set your access to: ask. Additionally you enable to also send the ask request to your owner.", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gNewUserProfile.getMember().getAsMention()+" sets their access to "+ACCESSLEVELS.Ask.getName()+" They have the power to decline your cuffs, however they also enabled to send the ask request to their owner as well.");
                                }
                            }else{
                                if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                                    logger.warn(fName + ".no access");
                                    sendOrUpdatePrivateEmbed(sRTitle,iAuth.deniedOnlyOwnerAccess, llColorRed);
                                    return;
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Key.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Key);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: key", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"'s access to "+ACCESSLEVELS.Key.getName()+" Time to lock them and take their key!");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Public);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: public", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.Public.getName()+" Time to gag them.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||levelValue.equalsIgnoreCase("protect")||levelValue.equalsIgnoreCase("private")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Protected);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: protected", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.Protected.getName()+" Not fun :C");
                                }
                                else if(levelValue.equalsIgnoreCase("expose")||levelValue.equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||levelValue.equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Exposed);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: exposed", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+"s access to "+ACCESSLEVELS.ExposedOld.getName()+" Time to play with their restraints, playfully");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||levelValue.equalsIgnoreCase("slave")){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Pet);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: pet", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Pet.getName()+" Time to show what do good pets do.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.Ask);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: ask", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs.");
                                }
                                else if(levelValue.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                                    gNewUserProfile.cAUTH.setAccess(ACCESSLEVELS.AskPlus);
                                    gNewUserProfile.cAUTH.setRedirectAsk2OwnerAsWell(true);
                                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+gNewUserProfile.getMember().getAsMention()+" access to: ask. Additionally you enabled for their ask request to be also sent to their owner.", llColorBlue1);
                                    llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" sets "+gNewUserProfile.getMember().getAsMention()+" access to "+ACCESSLEVELS.Ask.getName()+" They now have the power to decline your cuffs, however, they also have their  ask request sent to their owner as well.");
                                }
                            }
                            break;
                        case "owner_add":
                            sendPrivateEmbed(sMainRTitle,"Only user can add owner to themselves!",llColorRed_Barn);
                        case "owner_accept":
                            if(reqUser==null){
                                sendOrUpdatePrivateEmbed(sRTitle,"Need to mention user!", llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                sendOrUpdatePrivateEmbed(sRTitle,"Can't mention yourself!", llColorRed_Barn);
                                return;
                            }
                            if(!gNewUserProfile.cAUTH.hasOwner()){
                                logger.warn(fName + ".has NO Owner");
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are not their owner", llColorRed);
                                return;
                            }
                            if(!gNewUserProfile.cAUTH.isOwner(gUser)){
                                logger.warn(fName + ".not a match");
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied you are not their owner", llColorRed);
                                return;
                            }
                            if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                                logger.info(fName + ".owner accepted");
                                sendOrUpdatePrivateEmbed(sRTitle,"You already accepted this", llColorRed);
                                return;
                            }
                            gNewUserProfile.cAUTH.setOwnerAccepted(true);
                            if(gNewUserProfile.cAUTH.hasSecOwner(gMember.getId())){
                                logger.info(fName + ".has secowner entry>remove");
                                gNewUserProfile.cAUTH.remSecOwner(gMember.getId());
                            }
                            if(!saveProfile()){
                                sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strFailedToSave, llColorRed);
                                return;
                            }
                            sendOrUpdatePrivateEmbed(sRTitle,"You accepted "+gNewUserProfile.getMember().getAsMention()+" leash!" ,llColorGreen2);
                            llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,gUser.getAsMention()+" accepted your ownership contract!", llColorGreen2);
                            break;
                        case "owner_reject":
                            if(reqUser==null){
                                sendOrUpdatePrivateEmbed(sRTitle,"Need to mention user!", llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                sendOrUpdatePrivateEmbed(sRTitle,"Can't mention yourself!", llColorRed_Barn);
                                return;
                            }
                            if(!gNewUserProfile.cAUTH.hasOwner()){
                                logger.warn(fName + ".has NO Owner");
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are not their owner", llColorRed);
                                return;
                            }
                            if(!gNewUserProfile.cAUTH.isOwner(gUser)){
                                logger.warn(fName + ".not a match");
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied you are not their owner", llColorRed);
                                return;
                            }
                            gNewUserProfile.cAUTH.setOwnerAccepted(false);gNewUserProfile.cAUTH.setOwnerId("");
                            if(!saveProfile()){
                                sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strFailedToSave, llColorRed_Barn);
                                return;
                            }
                            if(gNewUserProfile.cAUTH.isOwnerAccepted()){
                                logger.info(fName + ".owner accepted");
                                sendOrUpdatePrivateEmbed(sRTitle,"You rejected "+gNewUserProfile.getMember().getAsMention()+" leash!" ,llColorRed_CoralPink);
                                llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,gUser.getAsMention()+" rejected your ownership contract!", llColorRed_CoralPink);
                            }else{
                                logger.info(fName + ".owner not accepted");
                                sendOrUpdatePrivateEmbed(sRTitle,"You release "+gNewUserProfile.getMember().getAsMention()+" from your collar!" ,llColorRed_CoralPink);
                                llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,gUser.getAsMention()+" releases you from ownership contract!", llColorRed_CoralPink);
                            }
                            break;
                    }
                }
                

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        public void rSlashNT_old1229() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                User reqUser=null;
                boolean hasLock=false,reqLock=false;
                boolean hasPermalock=false,reqPermalock=false;
                boolean subdirProvided=false;
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()==OptionType.USER){
                                reqUser=option.getAsUser();
                            }
                            break;
                        case "lock":
                            if(option.getType()==OptionType.BOOLEAN){
                                reqLock=option.getAsBoolean();
                                hasLock=true;
                            }
                            break;
                        case "permalock":
                            if(option.getType()==OptionType.BOOLEAN){
                                reqPermalock=option.getAsBoolean();
                                hasPermalock=true;
                            }
                            break;
                    }
                }
                if(reqUser!=null&&gMember.getIdLong()!=reqUser.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,reqUser);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorRed_Barn);
                boolean valid=false;
                String desc="";
                if(subdirProvided){
                    slashReplyCheckDm();
                    menuLevels(gTarget);
                    return;
                }
                slashReplyPleaseWait();
                if(gTarget!=null){
                    if(hasPermalock&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        logger.warn(fName + ".no access");
                        embedBuilder.setDescription(iAuth.deniedOnlyOwnerAccess);
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                        logger.info(fName + ".can't use do to locked by not you");
                        embedBuilder.setDescription(iRestraints.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iAuth.strCantManipulateLockedAsLoekcedBy));
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        embedBuilder.setDescription(iAuth.strCantManipulateDue2Access);
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }else
                    if(hasPermalock&&reqPermalock){
                        gNewUserProfile.cLOCK.setLocked(true).setPermaLocked(true);
                        if(gNewUserProfile.cLOCK.getLockedByAsLong()==0){
                            gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                        }
                        valid=true;
                        desc=gUser.getAsMention()+" burns the keys to "+gTarget.getAsMention()+" locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on "+gTarget.getAsMention()+". Only special tool can free them now.";
                    }
                    else if(hasPermalock&&!reqPermalock&&hasLock&&!reqLock){
                        gNewUserProfile.cLOCK.setLocked(false).setPermaLocked(false);
                        gNewUserProfile.cLOCK.setLockedBy("");
                        valid=true;
                        desc=gUser.getAsMention()+" manages to break the perma-locks, freeing "+gTarget.getAsMention()+" from permanent bindings.";

                    }
                    else if(hasPermalock&&!reqPermalock){
                        gNewUserProfile.cLOCK.setPermaLocked(false);
                        valid=true;
                        desc=gUser.getAsMention()+" manages to break the perma-locks, freeing "+gTarget.getAsMention()+" from  permanent bindings.";
                    }
                    else if(hasLock&&reqLock){
                        gNewUserProfile.cLOCK.setLocked(true);
                        if(gNewUserProfile.cLOCK.getLockedByAsLong()==0){
                            gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                        }
                        valid=true;
                        desc=gUser.getName()+" has locked "+gTarget.getAsMention()+" restraints. Hope they wont lose their key.";
                    }
                    else if(hasLock&&!reqLock){
                        gNewUserProfile.cLOCK.setLocked(false);
                        gNewUserProfile.cLOCK.setLockedBy("");
                        valid=true;
                        desc=gUser.getName()+" has unlocked "+gTarget.getAsMention()+" restraints. Someone should have placed the key in a safe.";
                    }
                }else{
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        logger.info(fName + ".permalock");
                        embedBuilder.setDescription("Can't manipulate your restraints as they permanently locked!");
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }
                    if(gNewUserProfile.cAUTH.isOwned()&&hasPermalock&&!reqPermalock&&gNewUserProfile.isPermalocked()){
                        logger.warn(fName + ".no access");
                        embedBuilder.setDescription("Denied to undo permalock as you are owned!");
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }
                    if(!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        embedBuilder.setDescription("Can't manipulate your locks as access set to owner. Only your owner and sec-owners have access");
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }else
                    if(gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                        logger.info(fName + ".can't use do to access public");
                        embedBuilder.setDescription("Can't manipulate your locks due to access set to public. While public everyone else can access it except you.");
                        sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                        return;
                    }
                    if(hasPermalock&&reqPermalock){
                        gNewUserProfile.cLOCK.setLocked(true).setPermaLocked(true);
                        if(gNewUserProfile.cLOCK.getLockedByAsLong()==0){
                            gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                        }
                        valid=true;
                        desc=gUser.getAsMention()+" burns the keys to their locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on "+gUser.getAsMention()+". Only special tool can free them now.";

                    }
                    else if(hasPermalock&&!reqPermalock&&hasLock&&!reqLock){
                        gNewUserProfile.cLOCK.setLocked(false).setPermaLocked(false);
                        gNewUserProfile.cLOCK.setLockedBy("");
                        valid=true;
                        desc=gUser.getAsMention()+" manages to break the perma-locks, freeing themselves from your permanent bindings.";

                    }
                    else if(hasPermalock&&!reqPermalock){
                        gNewUserProfile.cLOCK.setPermaLocked(false);
                        valid=true;
                        desc=gUser.getAsMention()+" manages to break the perma-locks, freeing themselves from your permanent bindings.";
                    }
                    else if(hasLock&&reqLock){
                        gNewUserProfile.cLOCK.setLocked(true);
                        if(gNewUserProfile.cLOCK.getLockedByAsLong()==0){
                            gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                        }
                        valid=true;
                        desc=gUser.getName()+" has locked their restraints. Hope they wont lose their key.";
                    }
                    else if(hasLock&&!reqLock){
                        if(gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())){
                            logger.info(fName + ".can't unlock do to exposed access");
                            embedBuilder.setDescription("Can't unlock your restraints due to exposed access!");
                            sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                            return;
                        }
                        gNewUserProfile.cLOCK.setLocked(false);
                        gNewUserProfile.cLOCK.setLockedBy("");
                        valid=true;
                        desc=gUser.getName()+" has unlocked their restraints. Someone should have placed the key in a safe.";
                    }
                }
                if(valid){
                    embedBuilder.setDescription(desc);
                    embedBuilder.setColor(llColorBlue1);
                    sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                }else{
                    embedBuilder.setDescription("Invalid command");
                    sendPrivateEmbed(embedBuilder,gSlashInteractionHook);
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        //GUILD//SERVER
        private void menuServerSetup(){
            String fName="[menuServerSetup]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Guild Auth Setup");embed.setColor(llColorPurple2);
                embed.addField("Default access",gBDSMCommands.restraints.getDefaultAccess()+"\n Select "+lsUnicodeEmotes.aliasOne+" to change.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                String level="";

                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){menuServerSetup_DefaultAccessMode();}
                                else if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuServerSetup();
                                }
                                if(!level.isBlank()){
                                    gBDSMCommands.restraints.gProfile.jsonObject.put(fieldDefaultAccessMode,level);
                                    if(!gBDSMCommands.restraints.gProfile.saveProfile()){
                                        logger.warn(fName+"failed save");
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to save", llColorRed_Blood);
                                    }else{
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Default access mode updated to "+level+".", llColorGreen2);
                                    }

                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,sMainRTitle, "Timeout", llColorRed));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuServerSetup_DefaultAccessMode(){
            String fName="[menuServerSetup_DefaultAccessMode]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                String desc="Current default access: "+gBDSMCommands.restraints.getDefaultAccess()+".\nSelect default access:";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+strAccessWithDualSpacing+ACCESSLEVELS.Ask.getName()+", asks you first before applying it";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+strAccessWithDualSpacing+ACCESSLEVELS.ExposedOld.getName()+", anyone including the sub has access";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+strAccessWithDualSpacing+ACCESSLEVELS.Public.getName()+",  sets that anyone can use the sub except the sub has no access to its restraints";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+strAccessWithDualSpacing+ACCESSLEVELS.Key.getName()+", anyone until locked, then only who locked has";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+strAccessWithDualSpacing+ACCESSLEVELS.Protected.getName()+", only the sub and its owner,sec-owner have access to its restraints";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+strAccessWithDualSpacing+ACCESSLEVELS.Pet.getName()+", only sub's owner&sec-owner have access to its restraints";
                embed.setTitle("Guild Auth Setup");embed.setColor(llColorPurple2);
                embed.setDescription(desc);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){menuServerSetup();return;}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                else if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuServerSetup_DefaultAccessMode();
                                }
                                if(!level.isBlank()){
                                    gBDSMCommands.restraints.setDefaultAccess(level);
                                    if(!gBDSMCommands.restraints.gProfile.saveProfile()){
                                        logger.warn(fName+"failed save");
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to save", llColorRed_Blood);
                                    }else{
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Default access mode updated to "+level+".", llColorGreen2);
                                    }

                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,sMainRTitle, "Timeout", llColorRed));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuServerSetup_DomRoles(){
            String fName="[menuServerSetup_DefaultAccessMode]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                String desc="Current default access: "+gBDSMCommands.restraints.getDefaultAccess()+".\nSelect default access:";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+strAccessWithDualSpacing+ACCESSLEVELS.Ask.getName()+", asks you first before applying it";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+strAccessWithDualSpacing+ACCESSLEVELS.ExposedOld.getName()+", anyone including the sub has access";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+strAccessWithDualSpacing+ACCESSLEVELS.Public.getName()+",  sets that anyone can use the sub except the sub has no access to its restraints";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+strAccessWithDualSpacing+ACCESSLEVELS.Key.getName()+", anyone until locked, then only who locked has";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+strAccessWithDualSpacing+ACCESSLEVELS.Protected.getName()+", only the sub and its owner,sec-owner have access to its restraints";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+strAccessWithDualSpacing+ACCESSLEVELS.Pet.getName()+", only sub's owner&sec-owner have access to its restraints";
                embed.setTitle("Guild Auth Setup");embed.setColor(llColorPurple2);
                embed.setDescription(desc);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                String level="";
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){menuServerSetup();return;}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){level=ACCESSLEVELS.Ask.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){level=ACCESSLEVELS.Exposed.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){level=ACCESSLEVELS.Public.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){level=ACCESSLEVELS.Key.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){level=ACCESSLEVELS.Protected.getName();}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){level=ACCESSLEVELS.Pet.getName();}
                                else if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuServerSetup_DefaultAccessMode();
                                }
                                if(!level.isBlank()){
                                    gBDSMCommands.restraints.setDefaultAccess(level);
                                    if(!gBDSMCommands.restraints.gProfile.saveProfile()){
                                        logger.warn(fName+"failed save");
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to save", llColorRed_Blood);
                                    }else{
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Default access mode updated to "+level+".", llColorGreen2);
                                    }

                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,sMainRTitle, "Timeout", llColorRed));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        public void movedCommands() {
            String fName="[movedCommands]";
            logger.info(".start");
            try{
                sendOrUpdatePrivateEmbed(sRTitle,"Moved to `!>lock`",llColorBlue3);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }

}}
