package nsfw.diaper;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors_Pokemon;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diSuit extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iDiaperInteractive {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
	String sRTitle=iDiaperInteractive.sRTitle+" Onesies",sRTitles="Onesies",sCommand="onesies";
    public diSuit(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Onesies-DiaperInteractive";
        this.help = "rdSuit";
        this.aliases = new String[]{sCommand,"onesie","diSuit","diSuits"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public diSuit(lcGlobalHelper global, CommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,true);
        new Thread(r).start();
    }
    public diSuit(lcGlobalHelper global, CommandEvent ev,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,true,target);
        new Thread(r).start();
    }
    public diSuit(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public diSuit(lcGlobalHelper global, InteractionHook interactionHook, String forward){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward);
        new Thread(r).start();
    }
    public diSuit(lcGlobalHelper global, InteractionHook interactionHook,String forward,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward,target);
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
    protected class runLocal extends  dExtension implements Runnable {
        String cName="[runLocal]";String fName="runLocal";
        public runLocal(CommandEvent ev){
            logger.info(".run build");
            launch(gGlobal,sRTitle,ev);
        }
        public runLocal(CommandEvent ev,boolean isForward){
            logger.info(".run build");String fName="runLocal";
            launch(gGlobal,sRTitle,ev,isForward);
        }
        public runLocal(CommandEvent ev,boolean isForward,Member target){
            logger.info(".run build");String fName="runLocal";
            launch(gGlobal,sRTitle,ev,isForward,target);
        }
        public runLocal(SlashCommandEvent ev){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,sRTitle);
        }
        public runLocal(InteractionHook interactionHook,String forward){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,interactionHook,sRTitle,forward);
        }
        public runLocal(InteractionHook interactionHook,String forward,Member target){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,interactionHook,sRTitle,forward,target);
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                if(gCurrentInteractionHook!=null){
                    logger.info(cName + fName + "InteractionHook@");
                    rInteraction();
                }else
                if(gSlashCommandEvent!=null){
                    logger.info(cName + fName + "slash@");
                    rSlashNT();
                }else{
                    logger.info(cName + fName + "basic@");
                    loadValues();

                    boolean isInvalidCommand=true;
                    logger.info(fName+".gIsForward="+gIsForward);
                    logger.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
                    if(gCommandEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                    logger.info(fName + ".items.size=" + gItems.length);
                    logger.info(fName + ".items[0]=" + gItems[0]);
                    if(gItems[0].equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;}
                    if(!vEnabled){
                        sendEmbed(sRTitle,"Its disabled!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(!gBDSMCommands.diaper.isAllowedChannel4Command(gTextChannel)){
                        sendEmbed(sRTitle,"Not allowed channel!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(!gBDSMCommands.diaper.hasPermission2UseCommand(gMember)){
                        sendEmbed(sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(gBDSMCommands.diaper.isMemberBanned2UseCommand(gMember)){
                        sendEmbed(sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }
                    if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                        logger.info(fName+".detect mention characters");
                        Member target;
                        List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                        if(mentions.isEmpty()){
                            logger.warn(fName+".zero member mentions in message>check itemns[0]");
                            target=llGetMember(gGuild,gItems[0]);
                        }else{
                            logger.info(fName+".member mentions in message");
                            target=mentions.get(0);

                        }
                        if(target!=null){
                            logger.info(fName+".target is not null");
                            gTarget=target;
                        }
                        if(target==null){
                            logger.warn(fName+".zero member mentions");
                        }
                        else if(target.getIdLong()==gUser.getIdLong()){
                            logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                            target=null;
                            gTarget=null;
                            //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                        }
                    }
                    if(isInvalidCommand&&gTarget!=null){
                        if(gItems.length<2){
                            logger.warn(fName+".invalid args length");
                            menuLevels(gTarget);isInvalidCommand=false;
                        }else {
                            logger.info(fName + ".target:" + gTarget.getId() + "|" + gTarget.getEffectiveName());
                            if (!getProfile()) {
                                logger.error(fName + ".can't get profile");
                                return;
                            }
                            int i=1;
                            while(i<gItems.length&&isInvalidCommand){
                                String selected=gItems[i];
                                if(selected.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Wolf.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Unicorn.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Puppy.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Pikachu.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Kitten.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Jolteon.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Flareon.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Eevee.getString())
                                        ||selected.equalsIgnoreCase(SUITTYPE.Sylveon.getString())
                                        ||selected.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.level)
                                        ||selected.equalsIgnoreCase(vOff)
                                ){
                                    rSuit(gTarget,selected);isInvalidCommand=false;
                                }
                                i++;
                            }
                            if(isInvalidCommand&&gItems[1].equalsIgnoreCase("help")){
                                rHelp("main");isInvalidCommand=false;
                            }
                            if(isInvalidCommand){
                                menuLevels(gTarget);isInvalidCommand=false;
                            }
                        }

                    }
                    if(isInvalidCommand){
                        if(!getProfile()){
                            logger.error(fName + ".can't get profile"); return;}
                        int i=0;
                        while(i<gItems.length&&isInvalidCommand){
                            String selected=gItems[i];
                            if(selected.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Wolf.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Unicorn.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Puppy.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Pikachu.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Kitten.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Jolteon.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Flareon.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Eevee.getString())
                                    ||selected.equalsIgnoreCase(SUITTYPE.Sylveon.getString())
                                    ||selected.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.level)
                                    ||selected.equalsIgnoreCase(vOff)
                            ){
                                rSuit(selected);isInvalidCommand=false;
                            }
                            i++;
                        }
                        if(isInvalidCommand){
                            menuLevels(null);isInvalidCommand=false;
                        }
                    }

                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                    }


                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(".run ended");
        }

        boolean isAdult=false;
        private void blocked(){
            String fName = "[blocked]";
            llSendQuickEmbedMessageWithDelete(global,true,gTextChannel,sRTitle,"Require NSFW channel or server.",llColorRed);
            logger.info(fName);
        }
        int bdsmRestriction=0;
        private void updateIsAdult(){
            String fName="[updateIsAdult]";
            logger.info(fName);
            if(gTextChannel.isNSFW()){
                logger.info(fName+"channel is nsfw"); isAdult=true; return;
            }
            if(lsGuildHelper.lsIsGuildNSFW(global,gGuild)){
                logger.info(fName+"guild is adult"); isAdult=true; return;
            }
            bdsmRestriction= gBDSMCommands.getBDSMRestriction();
            logger.info(fName+"is safe");
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        desc="Onesie options:";
        String cFirst="\n`<@Bot>diaper <@wearer> onesies",cEnd="`";
        desc+=cFirst+vOff+cEnd;
        desc+=cFirst+SUITTYPE.Eevee.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Flareon.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Jolteon.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Kitten.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Pikachu.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Sylveon.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Puppy.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Unicorn.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Vaporeon.getString()+cEnd;
        desc+=cFirst+SUITTYPE.Wolf.getString()+cEnd;
        desc+="\n<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself";
        embed.setDescription(desc);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }else
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(global,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }

            //https://www.idiaper.com/types-of-adult-diapers-there-are
        private void rSuit(String command) {
            String fName = "[rSuit]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){ sendOrUpdatePrivateEmbed(sRTitle,"Failed to load  profile!", llColorRed);return; }

            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.cantDueToLockedBy), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.cantDueToAccessSet), llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cSuit.isEnabled()){
                    if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.cantTakeOffDueToLocked), llColorRed);
                        sendEmbed(sRTitle,stringReplacer(iSuit.solo.cantTakeOffDueToLocked_public), llColorRed);
                    }else{
                        gNewUserProfile.cSuit.setOff();
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.takesOff), llColorBlue1);
                        sendEmbed(sRTitle, stringReplacer(iSuit.solo.takesOff_public), llColorBlue1);
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                   sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.isnoton), llColorPurple1);
                }
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Eevee.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Eevee);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnEevee), llColors_Pokemon.llColorEevee);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnEevee_public), llColors_Pokemon.llColorEevee);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Flareon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Flareon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnFlareon), llColors_Pokemon.llColorFlareon);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnFlareon_public), llColors_Pokemon.llColorFlareon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Jolteon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Jolteon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnJolteon), llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnJolteon_public), llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Kitten.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Kitten);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnKitten), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnKitten_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Pikachu.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Pikachu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnPikachu), llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnPikachu_public), llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Puppy.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Puppy);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnPuppy), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnPuppy_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Unicorn.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Unicorn);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnUnicorn), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnUnicorn_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Vaporeon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnVaporeon), llColors_Pokemon.llColorVaporeon);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnVaporeon_public), llColors_Pokemon.llColorVaporeon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Wolf.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Wolf);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnWolf), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnWolf_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Sylveon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Sylveon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.solo.putOnSylveon) , llColors_Pokemon.llColorSylveon);
                sendEmbed(sRTitle,stringReplacer(iSuit.solo.putOnSylveon_public), llColors_Pokemon.llColorSylveon);
            }
            else if((gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||lsMemberIsBotOwner(gMember))
               &&(command.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.name)||command.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.command))
            ){
                gNewUserProfile.cSuit.setEnabled(true).setType(idDiaperPatreon.patreonUser_239748154046545920.level);
                idDiaperPatreon.patreonUser_239748154046545920.sendApplyingWearer4Diaper(gNewUserProfile.gUserProfile,gMember,gTextChannel);
            }
            saveProfile();
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void rSuit(Member mtarget, String command) {
            String fName = "[rSuit]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){ sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.cantDueToLockedBy), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.cantDueToacessSet), llColorRed);return;
            }

            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cSuit.isEnabled()){
                    if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.cantDueToLocked), llColorRed);
                        }else{
                        gNewUserProfile.cSuit.setOff();
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.pulloff_auth), llColorBlue1);
                        sendEmbed(sRTitle, stringReplacer(iSuit.target.pullloff_public), llColorBlue1);
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                   sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.isnoton), llColorPurple1);
                }
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Eevee.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Eevee);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonEevee_auth), llColors_Pokemon.llColorEevee);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonEevee_public), llColors_Pokemon.llColorEevee);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Flareon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Flareon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonFlareon_auth), llColors_Pokemon.llColorFlareon);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonFlareon_public), llColors_Pokemon.llColorFlareon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Jolteon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Jolteon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonJolteon_auth), llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonJolteon_public), llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Kitten.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Kitten);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonKitten_auth), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonKitten_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Pikachu.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Pikachu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonPikachu_auth), llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonPikachu_public), llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Puppy.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Puppy);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonPuppy_auth), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonPuppy_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Unicorn.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Unicorn);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonUnicorn_auth), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonUnicorn_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Vaporeon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonVaporeon_auth),llColors_Pokemon.llColorVaporeon);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonVaporeon_public), llColors_Pokemon.llColorVaporeon);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Wolf.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Wolf);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonWolf_auth), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonWolf_public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(SUITTYPE.Sylveon.getString())){
                gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Sylveon);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.target.putonSylveon_auth), llColors_Pokemon.llColorSylveon);
                sendEmbed(sRTitle,stringReplacer(iSuit.target.putonSylveon_public), llColors_Pokemon.llColorSylveon);
            }
            else if((gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||lsMemberIsBotOwner(gMember))
                            &&(command.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.name)||command.equalsIgnoreCase(idDiaperPatreon.patreonUser_239748154046545920.command))
            ) {
                gNewUserProfile.cSuit.setEnabled(true).setType(idDiaperPatreon.patreonUser_239748154046545920.level);
                idDiaperPatreon.patreonUser_239748154046545920.sendApplyingUser4Diaper(gNewUserProfile.gUserProfile,mtarget.getUser(),gMember,gTextChannel);
            }
            saveProfile();

        }
        String gCommandFileMainPath =gFileMainPath+"menuOnesie.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);
                if(!getProfile()){
                    logger.error(fName + ".can't get profile"); return;}
                if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                    embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+ sRTitle+" Menu");
                }else{
                    embed.setTitle(sRTitle+" Menu");
                }
                if(!gNewUserProfile.cSuit.isEnabled()){
                    embed.addField("Wearing","(none)",false);
                }else{
                    embed.addField("Wearing",gNewUserProfile.cSuit.getName(),false);
                }
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" none";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+SUITTYPE.Eevee.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+SUITTYPE.Pikachu.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+SUITTYPE.Kitten.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+SUITTYPE.Jolteon.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+SUITTYPE.Flareon.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+SUITTYPE.Puppy.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+SUITTYPE.Unicorn.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+SUITTYPE.Vaporeon.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+SUITTYPE.Wolf.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+SUITTYPE.Sylveon.getName();
                if(gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID){
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasStar)+" "+ idDiaperPatreon.patreonUser_239748154046545920.name;
                }
                embed.addField(" ", "Please select a onesies option :"+desc, false);
                //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                /*lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasNine) );
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA) );*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if(gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID){
                        lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
                        lcMessageBuildComponent.SelectMenu.Option vip0=new lcMessageBuildComponent.SelectMenu.Option(idDiaperPatreon.patreonUser_239748154046545920.name,lsUnicodeEmotes.aliasStar);
                        messageComponentManager.selectContainer.addOption(vip0);
                    }
                    if(!gNewUserProfile.cSuit.isEnabled()){
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    }else{
                        switch (gNewUserProfile.cSuit.getType()){
                            case Eevee:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case Pikachu:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Kitten:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case Jolteon:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case Flareon:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case Unicorn:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case Vaporeon:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case Wolf:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case Sylveon:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                            default:
                                if(gNewUserProfile.cSuit.getTypeAsString().equals(idDiaperPatreon.patreonUser_239748154046545920.level)){
                                    messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasStar);
                                }
                    }
                    }
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                if(gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasStar));
                menuLevelsListener(message,mtarget);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuLevelsListener(Message message,Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                global.waiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                        e -> {
                            if(gCurrentInteractionHook!=null)deferReplySet(e);
                            try {
                                String value=e.getValues().get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero: level=vOff;
                                    case lsUnicodeEmotes.aliasOne: level=SUITTYPE.Eevee.getString();break;
                                    case lsUnicodeEmotes.aliasTwo: level=SUITTYPE.Pikachu.getString();break;
                                    case lsUnicodeEmotes.aliasThree: level=SUITTYPE.Kitten.getString();break;
                                    case lsUnicodeEmotes.aliasFour: level=SUITTYPE.Jolteon.getString();break;
                                    case lsUnicodeEmotes.aliasFive: level=SUITTYPE.Flareon.getString();break;
                                    case lsUnicodeEmotes.aliasSix: level=SUITTYPE.Puppy.getString();break;
                                    case lsUnicodeEmotes.aliasSeven: level=SUITTYPE.Unicorn.getString();break;
                                    case lsUnicodeEmotes.aliasEight: level=SUITTYPE.Vaporeon.getString();break;
                                    case lsUnicodeEmotes.aliasNine: level=SUITTYPE.Wolf.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolA: level=SUITTYPE.Sylveon.getString();break;
                                    case lsUnicodeEmotes.aliasStar:
                                        if(gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID){
                                            level= idDiaperPatreon.patreonUser_239748154046545920.name;
                                        }
                                        break;
                                }
                                if(level.equalsIgnoreCase("info")){
                                    rHelp("main");
                                }else
                                if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                                    rSuit(level);
                                }else{
                                    rSuit(mtarget,level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevels(gTarget);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                global.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                        e -> {
                            if(gCurrentInteractionHook!=null)deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        rHelp("main");
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){ level="info";}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){ level=vOff;}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){ level=SUITTYPE.Eevee.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){ level=SUITTYPE.Pikachu.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){ level=SUITTYPE.Kitten.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){ level=SUITTYPE.Jolteon.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){ level=SUITTYPE.Flareon.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){ level=SUITTYPE.Puppy.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){ level=SUITTYPE.Unicorn.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){ level=SUITTYPE.Vaporeon.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){ level=SUITTYPE.Wolf.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){ level=SUITTYPE.Sylveon.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasStar))){
                                    if(gMember.getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID||gNewUserProfile.getMember().getIdLong()== idDiaperPatreon.patreonUser_239748154046545920.userID){
                                        level= idDiaperPatreon.patreonUser_239748154046545920.name;
                                    }
                                }
                                if(level.equalsIgnoreCase("info")){
                                    rHelp("main");
                                }else
                                if(gNewUserProfile.getMember().getIdLong()==gUser.getIdLong()){
                                    rSuit(level);
                                }else{
                                    rSuit(mtarget,level);
                                }

                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            slashReplyPleaseWait();
            gCurrentInteractionHook=gSlashInteractionHook;
            Member user=null;
            boolean subdirProvided=false;
            String typeValue="";boolean typeProvided=false;
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColorRed_Barn);
            //gInteractionHook=gSlashCommandEvent.deferReply(true).complete();
            loadValues();
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.subdir:
                        subdirProvided=true;
                        break;
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsMember();
                        }
                        break;
                    case llCommonKeys.SlashCommandReceive.type:
                        if(option.getType()== OptionType.STRING){
                            typeValue=option.getAsString();
                            typeProvided=true;
                        }
                        break;
                }
            }
            if(user!=null&&gMember.getIdLong()!=user.getIdLong()){
                logger.info(fName + ".target="+gTarget.getId());
            }else{
                logger.info(fName + ".target=author");
            }
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".subdirProvided="+subdirProvided);
            if(subdirProvided){
                menuLevels(gTarget);
                return;
            }
            if(!typeProvided){
                menuLevels(gTarget);
                return;
            }
            if(gTarget==null){
                if(gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    sendOrUpdatePrivateEmbed(strTitle,iSuit.solo.cantDueToLockedBy,llColorRed);
                    return;
                }
                if(!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    sendOrUpdatePrivateEmbed(strTitle,iSuit.solo.cantDueToAccessSet,llColorRed);
                    return;
                }
                if(typeValue.equalsIgnoreCase(vOff)){
                    if(gNewUserProfile.cSuit.isEnabled()){
                        if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                            logger.info(fName + ".can't take off do to locked");
                            sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.cantTakeOffDueToLocked_public),llColorRed);
                            return;
                        }else{
                            gNewUserProfile.cSuit.setOff();
                            sendOrUpdatePrivateEmbed(strTitle,"You take it off.",llColorBlue1);
                            embedBuilder.setDescription(stringReplacer(iSuit.solo.takesOff_public));
                        }
                    }else{
                        logger.info(fName + ".suit is not on");
                        sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.isnoton),llColorPurple1);
                        return;
                    }
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Eevee.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Eevee);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnEevee_public)).setColor( llColors_Pokemon.llColorEevee);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Flareon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Flareon);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnFlareon_public)).setColor( llColors_Pokemon.llColorFlareon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Jolteon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Jolteon);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnJolteon_public)).setColor( llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Kitten.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Kitten);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnKitten_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Pikachu.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Pikachu);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnPikachu_public)).setColor( llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Puppy.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Puppy);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnPuppy_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Unicorn.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Unicorn);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnUnicorn_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Vaporeon);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnVaporeon_public)).setColor( llColors_Pokemon.llColorVaporeon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Wolf.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Wolf);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnWolf_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Sylveon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Sylveon);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnSylveon_public)).setColor( llColors_Pokemon.llColorSylveon);
                }else{
                    sendOrUpdatePrivateEmbed(strTitle,"Invalid command.",llColorRed);
                    return;
                }
                sendEmbed(embedBuilder);
                saveProfile();
            }else{
                if(gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.cantDueToLockedBy), llColorRed);
                    return;
                }else
                if(!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.cantDueToAccessSet), llColorRed);
                    return;
                }
                if(typeValue.equalsIgnoreCase(vOff)){
                    if(gNewUserProfile.cSuit.isEnabled()){
                        if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                            logger.info(fName + ".can't take off do to locked");
                            sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.cantTakeOffDueToLocked_public), llColorRed);
                            return;
                        }else{
                            gNewUserProfile.cSuit.setOff();
                            sendOrUpdatePrivateEmbed(strTitle,"You take it off.",llColorBlue1);
                            embedBuilder.setDescription( stringReplacer(iSuit.solo.takesOff_public));
                        }
                    }else{
                        logger.info(fName + ".suit is not on");
                        sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iSuit.solo.isnoton), llColorPurple1);
                        return;
                    }
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Eevee.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Eevee);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnEevee_public)).setColor( llColors_Pokemon.llColorEevee);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Flareon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Flareon);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnFlareon_public)).setColor( llColors_Pokemon.llColorFlareon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Jolteon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Jolteon);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnJolteon_public)).setColor( llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Kitten.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Kitten);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnKitten_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Pikachu.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Pikachu);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnPikachu_public)).setColor( llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Puppy.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Puppy);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnPuppy_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Unicorn.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Unicorn);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnUnicorn_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Vaporeon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Vaporeon);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnVaporeon_public)).setColor( llColors_Pokemon.llColorVaporeon);
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Wolf.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Wolf);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnWolf_public));
                }
                else if(typeValue.equalsIgnoreCase(SUITTYPE.Sylveon.getString())){
                    gNewUserProfile.cSuit.setEnabled(true).setType(SUITTYPE.Sylveon);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes to "+typeValue+" for !WEARER."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iSuit.solo.putOnSylveon_public)).setColor( llColors_Pokemon.llColorSylveon);
                }
                else{
                    sendOrUpdatePrivateEmbed(strTitle,"Invalid command.",llColorRed);
                    return;
                }
                sendEmbed(embedBuilder);
                saveProfile();
            }
        }
        private void rInteraction() {
            String fName = "[rInteraction]";
            logger.info(fName);
            Member user=null;
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            sendOrUpdatePrivateEmbed(strTitle,"Openign DM",llColorBlue1);
            menuLevels(gTarget);
        }
        

        Boolean vEnabled=false;
        JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
        private void loadValues(){
            String fName = "[loadValues]";
            logger.info(fName);
            logger.info(fName+"json="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
            if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldEnabled)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldEnabled)){
                vEnabled = gBDSMCommands.diaper.gProfile.getFieldEntryAsBoolean(iRestraints.fieldEnabled);
            }
            if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldBannedUsers)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldBannedUsers)){
                vBannedUsers= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldBannedUsers);
            }
            if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldAllowedChannels)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldAllowedChannels)){
                vAllowedChannels= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldAllowedChannels);
            }
        }

}}
