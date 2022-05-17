package nsfw.diaper;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.colors.llColors_Pokemon;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
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
import restraints.in.iRdStr;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diDiaper extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iDiaperInteractive {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public diDiaper(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Diaper-DiaperInteractive";
        this.help = "rdSuit";
        this.aliases = new String[]{"diDiaper","diDiaperss"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public diDiaper(lcGlobalHelper global, CommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,true);
        new Thread(r).start();
    }
    public diDiaper(lcGlobalHelper global, CommandEvent ev,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,true,target);
        new Thread(r).start();
    }
    public diDiaper(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public diDiaper(lcGlobalHelper global, InteractionHook interactionHook,String forward){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward);
        new Thread(r).start();
    }
    public diDiaper(lcGlobalHelper global, InteractionHook interactionHook,String forward,Member target){
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
        public runLocal(CommandEvent ev){
            loggerExt.info(".run build");
            launch(gGlobal,sRTitle,ev);
        }
        public runLocal(CommandEvent ev,boolean isForward){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,sRTitle,ev,isForward);
        }
        public runLocal(CommandEvent ev,boolean isForward,Member target){
            loggerExt.info(".run build");String fName="runLocal";
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
            loggerExt.info(".run start");
            try {
                if(gCurrentInteractionHook!=null){
                    logger.info(cName + fName + "InteractionHook@");
                    rInteraction();
                }else
                if(gSlashCommandEvent!=null){
                    logger.info(cName + fName + "slash@");
                    rSlashNT();
                }else{
                    loggerExt.info(cName + fName + "basic@");
                    boolean isInvalidCommand=true;
                    loggerExt.info(fName+".gIsForward="+gIsForward);
                    loggerExt.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
                    if(gCommandEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                    loggerExt.info(fName + ".items.size=" + gItems.length);
                    loggerExt.info(fName + ".items[0]=" + gItems[0]);
                    if(gItems[0].equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;}
                    if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                        loggerExt.info(fName+".detect mention characters");
                        Member target;
                        List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                        if(mentions.isEmpty()){
                            loggerExt.warn(fName+".zero member mentions in message>check itemns[0]");
                            target=llGetMember(gGuild,gItems[0]);
                        }else{
                            loggerExt.info(fName+".member mentions in message");
                            target=mentions.get(0);

                        }
                        if(target!=null){
                            loggerExt.info(fName+".target is not null");
                            gTarget=target;
                        }
                        if(target==null){
                            loggerExt.warn(fName+".zero member mentions");
                        }
                        else if(target.getIdLong()==gUser.getIdLong()){
                            loggerExt.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                            target=null;
                            gTarget=null;
                            //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                        }
                    }
                    if(isInvalidCommand&&gTarget!=null){
                        if(gItems.length<2){
                            loggerExt.warn(fName+".invalid args length");
                            menuLevels(gTarget);isInvalidCommand=false;
                        }else {
                            loggerExt.info(fName + ".target:" + gTarget.getId() + "|" + gTarget.getEffectiveName());
                            if (!getProfile()) {
                                loggerExt.error(fName + ".can't get profile");
                                return;
                            }
                            int i=1;
                            while(i<gItems.length&&isInvalidCommand){
                                String selected=gItems[i];
                                if(selected.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Paws.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.White.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Pink.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())
                                        ||selected.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())
                                        ||selected.equalsIgnoreCase(vOff)
                                ){
                                    rDiaper(gTarget,selected);isInvalidCommand=false;
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
                            loggerExt.error(fName + ".can't get profile"); return;}
                        int i=0;
                        while(i<gItems.length&&isInvalidCommand){
                            String selected=gItems[i];
                            if(selected.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Paws.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.White.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Pink.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())
                                    ||selected.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())
                                    ||selected.equalsIgnoreCase(vOff)
                            ){
                                rDiaper(selected);isInvalidCommand=false;
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
                loggerExt.error(fName + ".exception=" + e);
                loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            loggerExt.info(".run ended");
        }

      
    private void rHelp(String command){
        String fName="[rHelp]";
        loggerExt.info(fName);
        loggerExt.info(fName + ".command="+command);
        String desc="N/A";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        desc="Diaper options:";
        String cFirst="\n`<@Bot> <@wearer> type",cEnd="`";
        desc+=cFirst+DIAPERTYPE.White.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.WhitePlastic.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Pink.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.PinkPlastic.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.PullUps.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Briefs4Overnight.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.BriefsWithClothBacking.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.BriefsWithPlasticBacking.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Peekabu.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Paws.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Pikachu.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Eevee.getString()+cEnd;
        desc+=cFirst+DIAPERTYPE.Unicorn.getString()+cEnd;
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


        private void rDiaper(String command) {
            String fName = "[rDiaper]";
            loggerExt.info(fName);
            loggerExt.info(fName + ".command=" + command);
            if(!getProfile()){ sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }

            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                loggerExt.info(fName + ".can't use do to locked by somebody");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.cantManipulateDueToLockedBy), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                loggerExt.info(fName + ".can't use do to access owner");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.cantManipulateDueToAccessSetToCaretaker), llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cDIAPER.isEnabled()){
                    if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                        loggerExt.info(fName + ".can't take off do to locked");
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.cantTakeOffWhileLocked), llColorRed);
                        sendEmbed(sRTitle,stringReplacer(iDiaper.solo.cantTakeOffWhileLocked_Public), llColorRed);
                    }else{
                        gNewUserProfile.cDIAPER.setOff();
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.managedToTakeoff), llColorBlue1);
                        sendEmbed(sRTitle, stringReplacer(iDiaper.solo.managedToTakeOff_Public), llColorBlue1);
                    }
                }else{
                    loggerExt.info(fName + ".suit is not on");
                   sendOrUpdatePrivateEmbed(sRTitle,"The diaper is not on, silly.", llColorPurple1);
                }
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.White.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.White);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnClassic), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnClassic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Pink.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pink);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPink), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPink_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.WhitePlastic);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPlastic), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPlastic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PinkPlastic);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPlasticPink), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPlasticPink_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.SwimDiapers);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnSwim), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnSwim_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Briefs4Overnight);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnOvernight) , llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnOvernight_Public) , llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithClothBacking);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnBriefsWithCloth), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnBriefsWithCloth_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithPlasticBacking);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnBriefsWithPlastic), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnBriefsWithPlastic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PullUps);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPullUps), llColorBlue1);
                sendEmbed(sRTitle, stringReplacer(iDiaper.solo.youPutOnPullUps_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Eevee);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnEevee), llColors_Pokemon.llColorEevee);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnEevee_Public),llColors_Pokemon.llColorEevee);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Paws.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Paws);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPaws), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPaws_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pikachu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPikachu),llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPikachu_Public),llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Peekabu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPeekabu), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnPeekabu_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Unicorn);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnUnicorn), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.solo.youPutOnUnicorn_Public), llColorBlue1);
            }
            saveProfile();
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void rDiaper(Member mtarget, String command) {
            String fName = "[rDiaper]";
            loggerExt.info(fName);
            loggerExt.info(fName + ".command=" + command);
            if(!getProfile()){ sendOrUpdatePrivateEmbed(sRTitle,"Failed to load  profile!", llColorRed);return; }
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                loggerExt.info(fName + ".can't use do to locked by not you");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.cantManipulateTheirsDueToLockedBy), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                loggerExt.info(fName + ".can't use do to access protected");
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.cantManipulateTheirsDueToTheirAccessSet), llColorRed);return;
            }

            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cDIAPER.isEnabled()){
                    if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                        loggerExt.info(fName + ".can't take off do to locked");
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.cantTakeOffTheirsWHileLocked), llColorRed);
                       }else{
                        gNewUserProfile.cDIAPER.setOff();
                       sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.targetTakenOffDiaper), llColorBlue1);
                        sendEmbed(sRTitle, stringReplacer(iDiaper.target.targetTakenOffDiaper_Public), llColorBlue1);
                    }
                }else{
                    loggerExt.info(fName + ".suit is not on");
                   sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.noneIsOn), llColorPurple1);
                }
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.White.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.White);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnClassic_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnClassic_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnClassic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Pink.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pink);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPink_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPink_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPink_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.WhitePlastic);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPlastic_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPlastic_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPlastic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PinkPlastic);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPlasticPink_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPlasticPink_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPlasticPink_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.SwimDiapers);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnSwim_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnSwim_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnSwim_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Briefs4Overnight);
               sendOrUpdatePrivateEmbed(sRTitle,  stringReplacer(iDiaper.target.putOnOvernight_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle, stringReplacer(iDiaper.target.putOnOvernight_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnOvernight_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithClothBacking);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithCloth_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithCloth_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithCloth_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithPlasticBacking);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithPlastic_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithPlastic_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnBriefsWithPlastic_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PullUps);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPullUps_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPullUps_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPullUps_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Eevee);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnEevee_Auth), llColors_Pokemon.llColorEevee);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnEevee_Target),llColors_Pokemon.llColorEevee);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnEevee_Public),llColors_Pokemon.llColorEevee);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Paws.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Paws);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPaws_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPaws_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPaws_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pikachu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPikachu_Auth), llColors_Pokemon.llColorPikachu_Jolteon);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle, stringReplacer(iDiaper.target.putOnPikachu_Target), llColors_Pokemon.llColorPikachu_Jolteon);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPink_Public), llColors_Pokemon.llColorPikachu_Jolteon);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Peekabu);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPeekabu_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnPeekabu_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnPeekabu_Public), llColorBlue1);
            }
            else if(command.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())){
                gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Unicorn);
               sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iDiaper.target.putOnUnicorn_Auth), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer(iDiaper.target.putOnUnicorn_Target), llColorBlue1);
                sendEmbed(sRTitle,stringReplacer(iDiaper.target.putOnUnicorn_Public), llColorBlue1);
            }
            saveProfile();

        }
        String gCommandFileMainPath =gFileMainPath+"menuDiaper.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            loggerExt.info(fName);
            try{
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);
                if(gNewUserProfile.gMember.getIdLong()!=gMember.getIdLong()){
                    embed.setTitle(gNewUserProfile.gMember.getUser().getName()+"'s"+ sRTitle+" Menu");
                }else{
                    embed.setTitle(sRTitle+" Menu");
                }
                if(!gNewUserProfile.cDIAPER.isEnabled()){
                    embed.addField("Wearing","(none)",false);
                }else{
                    embed.addField("Wearing",gNewUserProfile.cDIAPER.getName(),false);
                }
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" none";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+DIAPERTYPE.White.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+DIAPERTYPE.WhitePlastic.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+DIAPERTYPE.Pink.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+DIAPERTYPE.PinkPlastic.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+DIAPERTYPE.PullUps.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+DIAPERTYPE.Briefs4Overnight.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+DIAPERTYPE.BriefsWithClothBacking.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+DIAPERTYPE.BriefsWithPlasticBacking.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+DIAPERTYPE.SwimDiapers.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+DIAPERTYPE.Eevee.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+DIAPERTYPE.Pikachu.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+DIAPERTYPE.Paws.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+DIAPERTYPE.Peekabu.getName();
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" "+DIAPERTYPE.Unicorn.getName();
                embed.addField(" ", "Please select a diaper option :"+desc, false);
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
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
                    if(!gNewUserProfile.cDIAPER.isEnabled()){
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    }else{
                        switch (gNewUserProfile.cDIAPER.getType()){
                            case White:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                            case WhitePlastic:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            case Pink:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                            case PinkPlastic:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            case PullUps:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                            case Briefs4Overnight:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                            case BriefsWithClothBacking:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                            case BriefsWithPlasticBacking:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                            case SwimDiapers:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            case Eevee:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                            case Pikachu:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);break;
                            case Paws:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolC);break;
                            case Peekabu:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolD);break;
                            case Unicorn:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolE);break;
                        }
                    }
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsListener(message,mtarget);
            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuLevelsListener(Message message,Member mtarget){
            String fName="[menuLevels]";
            loggerExt.info(fName);
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
                                    case lsUnicodeEmotes.aliasZero: level=vOff;break;
                                    case lsUnicodeEmotes.aliasOne: level=DIAPERTYPE.White.getString();break;
                                    case lsUnicodeEmotes.aliasTwo: level=DIAPERTYPE.WhitePlastic.getString();break;
                                    case lsUnicodeEmotes.aliasThree: level=DIAPERTYPE.Pink.getString();break;
                                    case lsUnicodeEmotes.aliasFour: level=DIAPERTYPE.PinkPlastic.getString();break;
                                    case lsUnicodeEmotes.aliasFive: level=DIAPERTYPE.PullUps.getString();break;
                                    case lsUnicodeEmotes.aliasSix: level=DIAPERTYPE.Briefs4Overnight.getString();break;
                                    case lsUnicodeEmotes.aliasSeven: level=DIAPERTYPE.BriefsWithClothBacking.getString();break;
                                    case lsUnicodeEmotes.aliasEight: level=DIAPERTYPE.BriefsWithPlasticBacking.getString();break;
                                    case lsUnicodeEmotes.aliasNine: level=DIAPERTYPE.SwimDiapers.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolA: level=DIAPERTYPE.Eevee.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolB: level=DIAPERTYPE.Pikachu.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolC: level=DIAPERTYPE.Paws.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolD: level=DIAPERTYPE.Peekabu.getString();break;
                                    case lsUnicodeEmotes.aliasSymbolE: level=DIAPERTYPE.Unicorn.getString();break;
                                }
                                if(level.equalsIgnoreCase("info")){
                                    rHelp("main");
                                }else
                                if(gNewUserProfile.gMember.getIdLong()==gUser.getIdLong()){
                                    rDiaper(level);
                                }else{
                                    rDiaper(mtarget,level);
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
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                String level="";
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){ level="info";}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){ level=vOff;}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){ level=DIAPERTYPE.White.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){ level=DIAPERTYPE.WhitePlastic.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){ level=DIAPERTYPE.Pink.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){ level=DIAPERTYPE.PinkPlastic.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){ level=DIAPERTYPE.PullUps.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){ level=DIAPERTYPE.Briefs4Overnight.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){ level=DIAPERTYPE.BriefsWithClothBacking.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){ level=DIAPERTYPE.BriefsWithPlasticBacking.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){ level=DIAPERTYPE.SwimDiapers.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){ level=DIAPERTYPE.Eevee.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){ level=DIAPERTYPE.Pikachu.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){ level=DIAPERTYPE.Paws.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){ level=DIAPERTYPE.Peekabu.getString();}
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))){ level=DIAPERTYPE.Unicorn.getString();}

                                if(level.equalsIgnoreCase("info")){
                                    rHelp("main");
                                }else
                                if(gNewUserProfile.gMember.getIdLong()==gUser.getIdLong()){
                                    rDiaper(level);
                                }else{
                                    rDiaper(mtarget,level);
                                }

                                llMessageDelete(message);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () ->{llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

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
            embedBuilder.setColor(llColorRed_Barn).setTitle(sRTitle);
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.subdir:
                        subdirProvided=true;
                        break;
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsMember();
                        }
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
            embedBuilder.setColor(llColorBlue1);
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
                    if(gNewUserProfile.cDIAPER.isEnabled()){
                        if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                            loggerExt.info(fName + ".can't take off do to locked");
                            sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iDiaper.solo.cantTakeOffWhileLocked_Public),llColorRed);
                            return;
                        }else{
                            gNewUserProfile.cDIAPER.setOff();
                            embedBuilder.setDescription(stringReplacer(iDiaper.solo.managedToTakeOff_Public));
                        }
                    }else{
                        loggerExt.info(fName + ".suit is not on");
                        sendOrUpdatePrivateEmbed(strTitle,"The diaper is not on, silly.", llColorPurple1);
                        return;
                    }
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.White.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.White);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnClassic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Pink.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pink);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPink_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.WhitePlastic);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPlastic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PinkPlastic);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPlasticPink_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.SwimDiapers);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnSwim_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Briefs4Overnight);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnOvernight_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithClothBacking);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnBriefsWithCloth_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithPlasticBacking);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnBriefsWithPlastic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PullUps);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPullUps_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Eevee);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnEevee_Public)).setColor(llColors_Pokemon.llColorEevee);
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Paws.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Paws);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPaws_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pikachu);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPikachu_Public)).setColor(llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Peekabu);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnPeekabu_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Unicorn);
                    sendOrUpdatePrivateEmbed(strTitle,"Changes diaper to "+typeValue+".",llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.solo.youPutOnUnicorn_Public)).setColor( llColorBlue1);
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
                    if(gNewUserProfile.cDIAPER.isEnabled()){
                        if(!gIsOverride&&gNewUserProfile.cProfile.isLocked()){
                            loggerExt.info(fName + ".can't take off do to locked");
                            sendOrUpdatePrivateEmbed(strTitle,stringReplacer(iDiaper.target.cantTakeOffTheirsWHileLocked), llColorRed);
                            return;
                        }else{
                            gNewUserProfile.cDIAPER.setOff();
                            embedBuilder.setDescription(stringReplacer(iDiaper.target.targetTakenOffDiaper_Public));
                        }
                    }else{
                        loggerExt.info(fName + ".suit is not on");
                        sendOrUpdatePrivateEmbed(strTitle,"The diaper is not on, silly.", llColorPurple1);
                        return;
                    }
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.White.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.White);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnClassic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Pink.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pink);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPink_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.WhitePlastic.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.WhitePlastic);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPlastic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.PinkPlastic.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PinkPlastic);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPlasticPink_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.SwimDiapers.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.SwimDiapers);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnSwim_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Briefs4Overnight.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Briefs4Overnight);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnOvernight_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.BriefsWithClothBacking.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithClothBacking);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnBriefsWithCloth_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.BriefsWithPlasticBacking.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.BriefsWithPlasticBacking);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnBriefsWithPlastic_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.PullUps.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.PullUps);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPullUps_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Eevee.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Eevee);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnEevee_Public)).setColor(llColors_Pokemon.llColorEevee);
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Paws.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Paws);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPaws_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Pikachu.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Pikachu);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPikachu_Public)).setColor(llColors_Pokemon.llColorPikachu_Jolteon);
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Peekabu.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Peekabu);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnPeekabu_Public));
                }
                else if(typeValue.equalsIgnoreCase(DIAPERTYPE.Unicorn.getString())){
                    gNewUserProfile.cDIAPER.setEnabled(true).setType(DIAPERTYPE.Unicorn);
                    sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Changes !WEARER diaper to "+typeValue+"."),llColorBlue1);
                    embedBuilder.setDescription(stringReplacer(iDiaper.target.putOnUnicorn_Public)).setColor( llColorBlue1);
                }else{
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


    

}}
