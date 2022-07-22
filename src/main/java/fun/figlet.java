package fun;

//import com.github.lalyos.jfiglet.FigletFont;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.leego.banana.BananaUtils;
import io.leego.banana.Font;
import models.la.aBasicCommandHandler;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

//https://github.com/lalyos/jfiglet
//https://github.com/dtmo/jfiglet
//https://github.com/yihleego/banana
public class figlet extends Command implements llGlobalHelper{
    Logger logger = Logger.getLogger(getClass()); String cName="[figlet]";
    lcGlobalHelper gGlobal=lsGlobalHelper.sGetGlobal();
    String gTitle="Figlet",gCommand="ascii";

    public figlet(){
        String fName="[constructor]";
        logger.info(cName+fName);
        this.name = "Ascii";
        this.help = "Converts text to ASCII banner.";
        this.aliases = new String[]{gCommand,"figlet"};
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
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            setCommandHandlerValues(logger,ev);
        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                buildBasicFeatureControl(logger,"figlet",gEvent);
                setString4BasicFeatureControl(gTitle,gCommand);
                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
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
                    if(items[0].equalsIgnoreCase("unset")){
                        unset();
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("set")){
                        if(items.length>1){
                            set(items[1]);
                        }else{
                           help("main");
                        }
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("fonts")){
                        if(items.length>1){
                            fonts(items[1]);
                        }else{
                            fonts("main");
                        }
                        isInvalidCommand=false;
                    }else{
                        convertText2Figlet();isInvalidCommand=false;
                    }

                }
                logger.info(cName+fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(gEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", lsMessageHelper.llColorRed);
                }
                logger.info(cName+".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);String desc="";
            String quickSummonWithSpace=llPrefixStr+gCommand+" ";
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(lsMessageHelper.llColorGreen2);
            embedBuilder.addField("How to use","Simple type the text after `"+llPrefixStr+gCommand+" <text>`",false);
            desc="Simple type the font name before the text after `"+llPrefixStr+gCommand+" <font> <text>`";
            desc+="\n`"+llPrefixStr+gCommand+" fonts ?`, to view the available fonts starting with the letter that ? represents from a to w, except q.";
            desc+="\n`"+llPrefixStr+gCommand+" set <font>`, to set it to use that font as default for yourself.";
            desc+="\n`"+llPrefixStr+gCommand+" unset`, to unset your default font.";
            Font font2UseProfile=null;
            try {
               getUserProfile(gMember,gGlobal,table,profileName,true,()->{gUserProfile=iSafetyUserProfileEntry(gUserProfile);});
                if(gUserProfile.jsonObject.has(keyFont2Use)&&!gUserProfile.jsonObject.isNull(keyFont2Use)&&!gUserProfile.jsonObject.getString(keyFont2Use).isBlank()){
                    String fromProfile=gUserProfile.jsonObject.getString(keyFont2Use);
                    logger.info(fName+" font2use_fromProfile="+fromProfile);
                    buildFontsList();
                    for(Font font:fonts){
                        String name=font.getName().replaceAll(" ","_");
                        logger.info(fName+"font.name="+name);
                        if(name.toLowerCase().equalsIgnoreCase(fromProfile)){
                            logger.info(fName+"font.match");
                            font2UseProfile=font;
                            break;
                        }
                    }
                }
                if(font2UseProfile!=null){
                    desc+="\nCustom font:"+font2UseProfile.getName().replaceAll(" ","_").toLowerCase();
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                desc+="\nCustom font: <can't read>";
            }
            embedBuilder.addField("Use a different font",desc,false);
            embedBuilder.addField("Library","These text generator and its fonts are provided by "+ lsUsefullFunctions.getUrlTextString("yihleego/banana","https://github.com/yihleego/banana"),false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);

            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }
        private void fonts(String command) {
            String fName = "[Fonts]";
            logger.info(cName + fName);
            try {
                logger.info(cName + fName+"command="+command);
                String desc="Invalid font command.";
                if(command.equalsIgnoreCase("main")){
                    desc="Input a letter:\n`"+llPrefixStr+gCommand+" ?`\n  ? is a letter from a to w, except: q";
                }
                else if(command.equalsIgnoreCase("a")){
                    desc="Fonts in `a` category:";
                    desc+="\n"+Font.ACROBATIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ALLIGATOR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ALLIGATOR2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ALPHA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ALPHABET.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_3_LINE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_3_LIV1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_AAA01.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_NEKO.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_RAZOR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_RAZOR2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_SLASH.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_SLIDER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_THIN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_TUBES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AMC_UNTITLED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ANSI_SHADOW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ARROWS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ASCII_NEW_ROMAN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.AVATAR.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("b")){
                    desc="Fonts in `b` category:";
                    desc+="\n"+Font.BANNER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BANNER3.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BANNER3_D.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BANNER4.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BARBWIRE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BASIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BEAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BELL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BENJAMIN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG_CHIEF.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG_MONEY_NE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG_MONEY_NW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG_MONEY_SE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIG_MONEY_SW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BIGFIG.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BINARY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BLOCK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BLOCKS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BOLGER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BRACED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BLOODY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BRIGHT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BROADWAY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BROADWAY_KB.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BUBBLE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.BULBHEAD.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("c")){
                    desc="Fonts in `c` category:";
                    desc+="\n"+Font.CALIGRAPHY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CALIGRAPHY2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CALVIN_S.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CARDS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CATWALK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CHISELED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CHUNKY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.COINSTAK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.COLA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.COLOSSAL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.COMPUTER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CONTESSA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CONTRAST.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.COSMIKE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CRAWFORD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CRAWFORD2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CRAZY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CRICKET.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CURSIVE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CYBERLARGE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CYBERMEDIUM.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CYBERSMALL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.CYGNET.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("d")){
                    desc="Fonts in `c` category:";
                    desc+="\n"+Font.DANC4.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DANCING_FONT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DECIMAL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DEF_LEPPARD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DELTA_CORPS_PRIEST_1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DIAMOND.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DIET_COLA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DIGITAL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+ Font.DOH.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DOOM.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DOT_MATRIX.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DOUBLE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DOUBLE_SHORTS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DR_PEPPER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.DWHISTLED.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("e")){
                    desc="Fonts in `e` category:";
                    desc+="\n"+Font.EFTI_CHESS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_FONT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_ITALIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_PITI.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_ROBOT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_WALL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EFTI_WATER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ELECTRONIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ELITE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.EPIC.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("f")){
                    desc="Fonts in `f` category:";
                    desc+="\n"+Font.FENDER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FILTER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FIRE_FONT_K.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FIRE_FONT_S.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FIVE_LINE_OBLIQUE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FLIPPED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FLOWER_POWER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FOUR_MAX.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FOUR_TOPS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FRAKTUR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FUN_FACE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FUN_FACES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.FUZZY.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("g")){
                    desc="Fonts in `g` category:";
                    desc+="\n"+Font.GEORGI16.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GEORGIA11.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GHOST.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GHOULISH.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GLENYN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GOOFY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GOTHIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GRACEFUL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GRADIENT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GRAFFITI.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.GREEK.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("h")){
                    desc="Fonts in `h` category:";
                    desc+="\n"+Font.HEART_LEFT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HEART_RIGHT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HENRY_3D.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HEX.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HIEROGLYPHS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HOLLYWOOD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HORIZONTAL_LEFT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.HORIZONTAL_RIGHT.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("i")){
                    desc="Fonts in `i` category:";
                    desc+="\n"+Font.ICL_1900.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.IMPOSSIBLE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.INVITA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ISOMETRIC1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ISOMETRIC2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ISOMETRIC3.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ISOMETRIC4.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ITALIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.IVRIT.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("j")){
                    desc="Fonts in `j` category:";
                    desc+="\n"+Font.JACKY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JAZMINE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JERUSALEM.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JS_BLOCK_LETTERS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JS_BRACKET_LETTERS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JS_CAPITAL_CURVES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JS_CURSIVE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.JS_STICK_LETTERS.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("k")){
                    desc="Fonts in `k` category:";
                    desc+="\n"+Font.KATAKANA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.KBAN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.KEYBOARD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.KNOB.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("l")){
                    desc="Fonts in `l` category:";
                    desc+="\n"+Font.LARRY_3D.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LCD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LEAN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LETTERS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LIL_DEVIL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LINE_BLOCKS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LINUX.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.LOCKERGNOME.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("m")){
                    desc="Fonts in `m` category:";
                    desc+="\n"+Font.MADRID.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MARQUEE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MAXFOUR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MERLIN1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MERLIN2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MIKE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MINI.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MIRROR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MNEMONIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MODULAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MORSE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MOSCOW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MSHEBREW210.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.MUZZLE.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("n")){
                    desc="Fonts in `n` category:";
                    desc+="\n"+Font.NANCYJ.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NANCYJ_FANCY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NANCYJ_UNDERLINED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NIPPLES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NSCRIPT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NT_GREEK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.NV_SCRIPT.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("o")){
                    desc="Fonts in `o` category:";
                    desc+="\n"+Font.O8.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.OCTAL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.OGRE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.OLD_BANNER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ONE_ROW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.OS2.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("p")){
                    desc="Fonts in `p` category:";
                    desc+="\n"+Font.PATORJK_CHEESE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PATORJK_HEX.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PAWP.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PEAKS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PEAKS_SLANT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PEBBLES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PEPPER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.POISON.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PUFFY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PUZZLE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.PYRAMID.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("r")){
                    desc="Fonts in `r` category:";
                    desc+="\n"+Font.RAMMSTEIN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.RECTANGLES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.RELIEF.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.RELIEF2.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.REVERSE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ROMAN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ROTATED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ROUNDED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ROWAN_CAP.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.ROZZO.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.RUNIC.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.RUNYC.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("s")){
                    desc="Fonts in `s` category:";
                    desc+="\n"+Font.S_BLOOD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SANTA_CLARA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SCRIPT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SERIFCAP.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SHADOW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SHIMROD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SHORT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SL_SCRIPT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SLANT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SLANT_RELIEF.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SLIDE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_CAPS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_ISOMETRIC1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_KEYBOARD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_POISON.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_SCRIPT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_SHADOW.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_SLANT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SMALL_TENGWAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SOFT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SPEED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SPLIFF.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STACEY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STAMPATE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STAMPATELLO.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STANDARD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STANDARD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STAR_STRIPS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STAR_WARS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STELLAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STFOREK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STICK_LETTERS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STOP.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STRAIGHT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.STRONGER_THAN_ALL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SUB_ZERO.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SWAMP_LAND.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SWAN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.SWEET.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("t")){
                    desc="Fonts in `t` category:";
                    desc+="\n"+Font.TANJA.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TENGWAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TERM.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TEST1.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THE_EDGE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THICK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THIN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THIS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THORNED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THREE_D.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THREE_D_ASCII.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THREE_D_DIAGONAL.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THREE_FIVE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.THREE_POINT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TICKS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TICKS_SLANT.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TILES.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TINKER_TOY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TOMBSTONE.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TRAIN.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TREK.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TSALAGI.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TUBULAR.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TWISTED.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.TWO_POINT.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("u")){
                    desc="Fonts in `u` category:";
                    desc+="\n"+Font.UNIVERS.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.USA_FLAG.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("v")){
                    desc="Fonts in `v` category:";
                    desc+="\n"+Font.VARSITY.getName().replaceAll(" ","_").toLowerCase();
                }
                else if(command.equalsIgnoreCase("w")){
                    desc="Fonts in `w` category:";
                    desc+="\n"+Font.WAVY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.WEIRD.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.WET_LETTER.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.WHIMSY.getName().replaceAll(" ","_").toLowerCase();
                    desc+="\n"+Font.WOW.getName().replaceAll(" ","_").toLowerCase();
                }


                lsMessageHelper.lsSendQuickEmbedMessage_Redirect(gUser,gTitle,desc, lsMessageHelper.llColorBlue2,gTextChannel);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void set(String command) {
            String fName = "[set]";
            
            logger.info(cName + fName+"command="+command);
            buildFontsList();
           getUserProfile(gMember,gGlobal,table,profileName,true,()->{gUserProfile=iSafetyUserProfileEntry(gUserProfile);});
            Font font2Use=null;
            for(Font font:fonts){
                String name=font.getName().replaceAll(" ","_");
                logger.info(fName+"font.name="+name);
                if(name.toLowerCase().equalsIgnoreCase(command)){
                    logger.info(fName+"font.match");
                    font2Use=font;
                    break;
                }
            }
            if(font2Use==null){
                logger.warn(cName + fName+"invalid name");
                lsMessageHelper.lsSendQuickEmbedMessage_Redirect(gUser,gTitle,"Invalid font name entered!", lsMessageHelper.llColorRed,gTextChannel);
                return;
            }
            gUserProfile.jsonObject.put(keyFont2Use,font2Use.getName().replaceAll(" ","_").toLowerCase());
            saveUserProfile(gGlobal);
            lsMessageHelper.lsSendQuickEmbedMessage_Redirect(gUser,gTitle,"Default font set to: "+font2Use.getName().replaceAll(" ","_").toLowerCase()+".", lsMessageHelper.llColorGreen2,gTextChannel);
        }
        private void unset() {
            String fName = "[set]";
            logger.info(cName + fName);
            getUserProfile(gMember,gGlobal,table,profileName,true,()->{gUserProfile=iSafetyUserProfileEntry(gUserProfile);});
            gUserProfile.jsonObject.put(keyFont2Use,"");
            saveUserProfile(gGlobal);
            lsMessageHelper.lsSendQuickEmbedMessage_Redirect(gUser,gTitle,"Unseted custom font.", lsMessageHelper.llColorGreen2,gTextChannel);
        }
        private void convertText2Figlet() {
            String fName = "[convertText2Figlet]";
            logger.info(cName + fName);
            getUserProfile(gMember,gGlobal,table,profileName,true,()->{gUserProfile=iSafetyUserProfileEntry(gUserProfile);});
            String text=gMessage.getContentRaw();
            text=text.replaceFirst(lcGlobalHelper.llPrefixStr+" ","").replaceFirst(lcGlobalHelper.llPrefixStr,"").trim();
            if(text.startsWith(gCommand)){text=text.replaceFirst(gCommand,"").trim();}
            else if(text.startsWith("ascii")){text=text.replaceFirst("ascii","").trim();}
            logger.info(cName + fName+"text="+text);
            //String newText=FigletFont.convertOneLine(text);
            buildFontsList();String newText="";
            Font font2UseProfile=null,font2Use=null;
            try {
                if(gUserProfile.jsonObject.has(keyFont2Use)&&!gUserProfile.jsonObject.isNull(keyFont2Use)&&!gUserProfile.jsonObject.getString(keyFont2Use).isBlank()){
                    String fromProfile=gUserProfile.jsonObject.getString(keyFont2Use);
                    logger.info(fName+" font2use_fromProfile="+fromProfile);
                    for(Font font:fonts){
                        String name=font.getName().replaceAll(" ","_");
                        logger.info(fName+"font.name="+name);
                        if(name.toLowerCase().equals(fromProfile)||name.toUpperCase().equals(fromProfile)||name.equals(fromProfile)){
                            logger.info(fName+"font.match");
                            font2UseProfile=font;
                            break;
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            String items[]= text.split("\\s+");
            logger.info(fName+" items="+ Arrays.toString(items));
            logger.info(fName+" items[0]="+items[0]);
            for(Font font:fonts){
                String name=font.getName().replaceAll(" ","_");
                logger.info(fName+"font.name="+name);
                if(name.toLowerCase().equals(items[0])||name.toUpperCase().equals(items[0])||name.equals(items[0])){
                    logger.info(fName+"font.match");
                    text=text.replaceFirst(name.toLowerCase(),"").replaceFirst(name.toUpperCase(),"").replaceFirst(name,"").trim();
                    font2Use=font;
                    break;
                }
            }
            if(font2Use!=null){
                newText=BananaUtils.bananaify(text, font2Use);
            }
            else if(font2UseProfile!=null){
                newText=BananaUtils.bananaify(text, font2UseProfile);
            }
            else{
                newText=BananaUtils.bananaify(text);
            }
            //newText=BananaUtils.bananaify(text, Font.THREE_D_ASCII);
            logger.info(cName + fName+"newText="+newText);
            if(newText.length()>2000){
                newText=newText.trim();
            }
            newText="```"+newText+"```";
            if(newText.length()>2000){
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Too big text.", lsMessageHelper.llColorRed);return;
            }
            postMessage(newText);
        }
        private void postMessage(String source) {
            String fName = "[postMessage]";
            logger.info(fName + ".sendwebhook");
            ReadonlyMessage readonlyMessage=lsMessageHelper.lsSendWebhookMessageResponse(gTextChannel,gMember,source);
            if(readonlyMessage==null){
                logger.info(fName + ".send embed message");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setDescription(source);
                embedBuilder.setColor(lsMessageHelper.llColorPurple2);
                embedBuilder.setAuthor(gUser.getName(),null, lsUserHelper.getAuthorIcon(gUser));
                if(lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder)==null){
                    logger.info(fName + ".send normal message");
                    lsMessageHelper.lsSendMessageResponse(gTextChannel,source);
                }
            }
        }
        List<Font> fonts;
        private void buildFontsList() {
            String fName = "[buildFontsList]";
            logger.info(cName + fName);
            try {
                fonts=new ArrayList<>();
                fonts.add(Font.ACROBATIC);
                fonts.add(Font.ALLIGATOR);
                fonts.add(Font.ALLIGATOR2);
                fonts.add(Font.ALPHA);
                fonts.add(Font.ALPHABET);
                fonts.add(Font.AMC_3_LINE);
                fonts.add(Font.AMC_3_LIV1);
                fonts.add(Font.AMC_AAA01);
                fonts.add(Font.AMC_NEKO);
                fonts.add(Font.AMC_RAZOR);
                fonts.add(Font.AMC_RAZOR2);
                fonts.add(Font.AMC_SLASH);
                fonts.add(Font.AMC_SLIDER);
                fonts.add(Font.AMC_THIN);
                fonts.add(Font.AMC_TUBES);
                fonts.add(Font.AMC_UNTITLED);
                fonts.add(Font.ANSI_SHADOW);
                fonts.add(Font.ARROWS);
                fonts.add(Font.ASCII_NEW_ROMAN);
                fonts.add(Font.AVATAR);

                fonts.add(Font.BANNER);
                fonts.add(Font.BANNER3);
                fonts.add(Font.BANNER3_D);
                fonts.add(Font.BANNER4);
                fonts.add(Font.BARBWIRE);
                fonts.add(Font.BASIC);
                fonts.add(Font.BEAR);
                fonts.add(Font.BELL);
                fonts.add(Font.BENJAMIN);
                fonts.add(Font.BIG);
                fonts.add(Font.BIG_CHIEF);
                fonts.add(Font.BIG_MONEY_NE);
                fonts.add(Font.BIG_MONEY_NW);
                fonts.add(Font.BIG_MONEY_SE);
                fonts.add(Font.BIG_MONEY_SW);
                fonts.add(Font.BIGFIG);
                fonts.add(Font.BINARY);
                fonts.add(Font.BLOCK);
                fonts.add(Font.BLOCKS);
                fonts.add(Font.BOLGER);
                fonts.add(Font.BRACED);
                fonts.add(Font.BLOODY);
                fonts.add(Font.BRIGHT);
                fonts.add(Font.BROADWAY);
                fonts.add(Font.BROADWAY_KB);
                fonts.add(Font.BUBBLE);
                fonts.add(Font.BULBHEAD);

                fonts.add(Font.CALIGRAPHY);
                fonts.add(Font.CALIGRAPHY2);
                fonts.add(Font.CALVIN_S);
                fonts.add(Font.CARDS);
                fonts.add(Font.CATWALK);
                fonts.add(Font.CHISELED);
                fonts.add(Font.CHUNKY);
                fonts.add(Font.COINSTAK);
                fonts.add(Font.COLA);
                fonts.add(Font.COLOSSAL);
                fonts.add(Font.COMPUTER);
                fonts.add(Font.CONTESSA);
                fonts.add(Font.CONTRAST);
                fonts.add(Font.COSMIKE);
                fonts.add(Font.CRAWFORD);
                fonts.add(Font.CRAWFORD2);
                fonts.add(Font.CRAZY);
                fonts.add(Font.CRICKET);
                fonts.add(Font.CURSIVE);
                fonts.add(Font.CYBERLARGE);
                fonts.add(Font.CYBERMEDIUM);
                fonts.add(Font.CYBERSMALL);
                fonts.add(Font.CYGNET);
                fonts.add(Font.DANC4);
                fonts.add(Font.DANCING_FONT);
                fonts.add(Font.DECIMAL);
                fonts.add(Font.DEF_LEPPARD);
                fonts.add(Font.DELTA_CORPS_PRIEST_1);
                fonts.add(Font.DIAMOND);
                fonts.add(Font.DIET_COLA);
                fonts.add(Font.DIGITAL);
                fonts.add( Font.DOH);
                fonts.add(Font.DOOM);
                fonts.add(Font.DOT_MATRIX);
                fonts.add(Font.DOUBLE);
                fonts.add(Font.DOUBLE_SHORTS);
                fonts.add(Font.DR_PEPPER);
                fonts.add(Font.DWHISTLED);

                fonts.add(Font.EFTI_CHESS);
                fonts.add(Font.EFTI_FONT);
                fonts.add(Font.EFTI_ITALIC);
                fonts.add(Font.EFTI_PITI);
                fonts.add(Font.EFTI_ROBOT);
                fonts.add(Font.EFTI_WALL);
                fonts.add(Font.EFTI_WATER);
                fonts.add(Font.ELECTRONIC);
                fonts.add(Font.ELITE);
                fonts.add(Font.EPIC);

                fonts.add(Font.FENDER);
                fonts.add(Font.FILTER);
                fonts.add(Font.FIRE_FONT_K);
                fonts.add(Font.FIRE_FONT_S);
                fonts.add(Font.FIVE_LINE_OBLIQUE);
                fonts.add(Font.FLIPPED);
                fonts.add(Font.FLOWER_POWER);
                fonts.add(Font.FOUR_MAX);
                fonts.add(Font.FOUR_TOPS);
                fonts.add(Font.FRAKTUR);
                fonts.add(Font.FUN_FACE);
                fonts.add(Font.FUN_FACES);
                fonts.add(Font.FUZZY);

                fonts.add(Font.GEORGI16);
                fonts.add(Font.GEORGIA11);
                fonts.add(Font.GHOST);
                fonts.add(Font.GHOULISH);
                fonts.add(Font.GLENYN);
                fonts.add(Font.GOOFY);
                fonts.add(Font.GOTHIC);
                fonts.add(Font.GRACEFUL);
                fonts.add(Font.GRADIENT);
                fonts.add(Font.GRAFFITI);
                fonts.add(Font.GREEK);

                fonts.add(Font.HEART_LEFT);
                fonts.add(Font.HEART_RIGHT);
                fonts.add(Font.HENRY_3D);
                fonts.add(Font.HEX);
                fonts.add(Font.HIEROGLYPHS);
                fonts.add(Font.HOLLYWOOD);
                fonts.add(Font.HORIZONTAL_LEFT);
                fonts.add(Font.HORIZONTAL_RIGHT);

                fonts.add(Font.ICL_1900);
                fonts.add(Font.IMPOSSIBLE);
                fonts.add(Font.INVITA);
                fonts.add(Font.ISOMETRIC1);
                fonts.add(Font.ISOMETRIC2);
                fonts.add(Font.ISOMETRIC3);
                fonts.add(Font.ISOMETRIC4);
                fonts.add(Font.ITALIC);
                fonts.add(Font.IVRIT);

                fonts.add(Font.JACKY);
                fonts.add(Font.JAZMINE);
                fonts.add(Font.JERUSALEM);
                fonts.add(Font.JS_BLOCK_LETTERS);
                fonts.add(Font.JS_BRACKET_LETTERS);
                fonts.add(Font.JS_CAPITAL_CURVES);
                fonts.add(Font.JS_CURSIVE);
                fonts.add(Font.JS_STICK_LETTERS);

                fonts.add(Font.KATAKANA);
                fonts.add(Font.KBAN);
                fonts.add(Font.KEYBOARD);
                fonts.add(Font.KNOB);

                fonts.add(Font.LARRY_3D);
                fonts.add(Font.LCD);
                fonts.add(Font.LEAN);
                fonts.add(Font.LETTERS);
                fonts.add(Font.LIL_DEVIL);
                fonts.add(Font.LINE_BLOCKS);
                fonts.add(Font.LINUX);
                fonts.add(Font.LOCKERGNOME);

                fonts.add(Font.MADRID);
                fonts.add(Font.MARQUEE);
                fonts.add(Font.MAXFOUR);
                fonts.add(Font.MERLIN1);
                fonts.add(Font.MERLIN2);
                fonts.add(Font.MIKE);
                fonts.add(Font.MINI);
                fonts.add(Font.MIRROR);
                fonts.add(Font.MNEMONIC);
                fonts.add(Font.MODULAR);
                fonts.add(Font.MORSE);
                fonts.add(Font.MOSCOW);
                fonts.add(Font.MSHEBREW210);
                fonts.add(Font.MUZZLE);

                fonts.add(Font.NANCYJ);
                fonts.add(Font.NANCYJ_FANCY);
                fonts.add(Font.NANCYJ_UNDERLINED);
                fonts.add(Font.NIPPLES);
                fonts.add(Font.NSCRIPT);
                fonts.add(Font.NT_GREEK);
                fonts.add(Font.NV_SCRIPT);

                fonts.add(Font.O8);
                fonts.add(Font.OCTAL);
                fonts.add(Font.OGRE);
                fonts.add(Font.OLD_BANNER);
                fonts.add(Font.ONE_ROW);
                fonts.add(Font.OS2);

                fonts.add(Font.PATORJK_CHEESE);
                fonts.add(Font.PATORJK_HEX);
                fonts.add(Font.PAWP);
                fonts.add(Font.PEAKS);
                fonts.add(Font.PEAKS_SLANT);
                fonts.add(Font.PEBBLES);
                fonts.add(Font.PEPPER);
                fonts.add(Font.POISON);
                fonts.add(Font.PUFFY);
                fonts.add(Font.PUZZLE);
                fonts.add(Font.PYRAMID);

                fonts.add(Font.RAMMSTEIN);
                fonts.add(Font.RECTANGLES);
                fonts.add(Font.RELIEF);
                fonts.add(Font.RELIEF2);
                fonts.add(Font.REVERSE);
                fonts.add(Font.ROMAN);
                fonts.add(Font.ROTATED);
                fonts.add(Font.ROUNDED);
                fonts.add(Font.ROWAN_CAP);
                fonts.add(Font.ROZZO);
                fonts.add(Font.RUNIC);
                fonts.add(Font.RUNYC);

                fonts.add(Font.S_BLOOD);
                fonts.add(Font.SANTA_CLARA);
                fonts.add(Font.SCRIPT);
                fonts.add(Font.SERIFCAP);
                fonts.add(Font.SHADOW);
                fonts.add(Font.SHIMROD);
                fonts.add(Font.SHORT);
                fonts.add(Font.SL_SCRIPT);
                fonts.add(Font.SLANT);
                fonts.add(Font.SLANT_RELIEF);
                fonts.add(Font.SLIDE);
                fonts.add(Font.SMALL);
                fonts.add(Font.SMALL_CAPS);
                fonts.add(Font.SMALL_ISOMETRIC1);
                fonts.add(Font.SMALL_KEYBOARD);
                fonts.add(Font.SMALL_POISON);
                fonts.add(Font.SMALL_SCRIPT);
                fonts.add(Font.SMALL_SHADOW);
                fonts.add(Font.SMALL_SLANT);
                fonts.add(Font.SMALL_TENGWAR);
                fonts.add(Font.SOFT);
                fonts.add(Font.SPEED);
                fonts.add(Font.SPLIFF);
                fonts.add(Font.STACEY);
                fonts.add(Font.STAMPATE);
                fonts.add(Font.STAMPATELLO);
                fonts.add(Font.STANDARD);
                fonts.add(Font.STANDARD);
                fonts.add(Font.STAR_STRIPS);
                fonts.add(Font.STAR_WARS);
                fonts.add(Font.STELLAR);
                fonts.add(Font.STFOREK);
                fonts.add(Font.STICK_LETTERS);
                fonts.add(Font.STOP);
                fonts.add(Font.STRAIGHT);
                fonts.add(Font.STRONGER_THAN_ALL);
                fonts.add(Font.SUB_ZERO);
                fonts.add(Font.SWAMP_LAND);
                fonts.add(Font.SWAN);
                fonts.add(Font.SWEET);

                fonts.add(Font.TANJA);
                fonts.add(Font.TENGWAR);
                fonts.add(Font.TERM);
                fonts.add(Font.TEST1);
                fonts.add(Font.THE_EDGE);
                fonts.add(Font.THICK);
                fonts.add(Font.THIN);
                fonts.add(Font.THIS);
                fonts.add(Font.THORNED);
                fonts.add(Font.THREE_D);
                fonts.add(Font.THREE_D_ASCII);
                fonts.add(Font.THREE_D_DIAGONAL);
                fonts.add(Font.THREE_FIVE);
                fonts.add(Font.THREE_POINT);
                fonts.add(Font.TICKS);
                fonts.add(Font.TICKS_SLANT);
                fonts.add(Font.TILES);
                fonts.add(Font.TINKER_TOY);
                fonts.add(Font.TOMBSTONE);
                fonts.add(Font.TRAIN);
                fonts.add(Font.TREK);
                fonts.add(Font.TSALAGI);
                fonts.add(Font.TUBULAR);
                fonts.add(Font.TWISTED);
                fonts.add(Font.TWO_POINT);

                fonts.add(Font.UNIVERS);
                fonts.add(Font.USA_FLAG);

                fonts.add(Font.VARSITY);

                fonts.add(Font.WAVY);
                fonts.add(Font.WEIRD);
                fonts.add(Font.WET_LETTER);
                fonts.add(Font.WHIMSY);
                fonts.add(Font.WOW);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        lcJSONUserProfile gUserProfile;
        String profileName="Figlet",table="MemberProfile";
        String keyFont2Use="font";

        private lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
            String fName = "[iSafetyUserProfileEntry]";
            gUserProfile.safetyPutFieldEntry(keyFont2Use, "");
            return gUserProfile;
        }
        
        
    }
}
