package fun.games;


import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.aBasicCommandHandler;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.rdPishock;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Minesweeper extends Command implements llGlobalHelper{
    Logger logger = Logger.getLogger(getClass()); String cName="[Minesweeper]";
    lcGlobalHelper gGlobal;
    String gTitle="Minesweeper[beta]",gCommand="minesweeper";

    public Minesweeper(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Imitation of the popular game Minesweeper.";
        this.aliases = new String[]{gCommand,"gameminesweeper"};
        this.guildOnly = true;this.category= llCommandCategory_Between;

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
                buildBasicFeatureControl(gGlobal,logger,"bfc_gameminesweeper",gEvent);
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
                        help("main"); isInvalidCommand=false;
                    }else
                    if(ifItsAnAccessControlCommand()){
                        logger.info(fName+"its an AccessControlCommand");
                    }else
                    if(!checkIfMemberIsAllowed()){
                        logger.info(fName+"not allowed");
                    }
                    else if(items[0].equalsIgnoreCase("testgame")){
                       testGame(); isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("new")){
                        if(items.length>=3){
                            newGame(items[1],items[2]);
                        }
                        else if(items.length==2){
                            newGame(items[1],"");
                        }
                        else{
                            newGame();
                        }
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("calc")){
                        if(items.length>=3){
                            calcMines(items[1],items[2]);
                        }
                        else if(items.length==2){
                            calcMines(items[1],"");
                        }
                        else{
                            calcMines("10","");
                        }
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("load")){
                        continueGame(); isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("show")){
                        showAll(); isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("debugprint")){
                        debugPrintBricks(); isInvalidCommand=false;
                    }
                }
                logger.info(cName+fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(gEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", lsMessageHelper.llColorRed);
                }
                logger.info(cName+".run ended");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
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
            desc="\n`"+quickSummonWithSpace+"new` to start new game";
            desc+="\n`"+quickSummonWithSpace+"load` to load previous game";
            embedBuilder.addField("Commands",desc,false);
            desc="\n"+"The character[A-J] reaction select the row";
            desc+="\n"+"The number[0-9] reaction select the column.";
            desc+="\n"+"After a row&column was selected one of the option can be selected to view the cell or to place a flag.";
            embedBuilder.addField("Info",desc,false);
            desc="\n"+"Can be linked to PiShock feature, so the person who caused to lose the game will get punished.";
            embedBuilder.addField("PiShock",desc,false);
            if(lsMemberHelper.lsMemberIsManager(gMember))embedBuilder.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
            if(lsMessageHelper.lsSendMessageStatus(gUser,embedBuilder)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embedBuilder);
            }
        }

        //https://github.com/deedy/MinesweeperForJava/blob/master/Minesweeper/Minesweeper.java
        /** Number of bricks per row */
        int TILE_COLUMNS= 10;
        /** Number of rows of bricks, in range 1..10. */
        int TILE_ROWS= 10;
        /** Width of a brick */
        /** Mine Number. Default setting - 10% of squares */
        int MINE_NUMBER= (int) (TILE_COLUMNS*TILE_ROWS/10);
        /** Set Up all the components of Minesweeper */
        public void testGame() {
            String fName="[testGame]";
            gameSetup();
            debugPrintBricks();

            createBoard();
            postBoard();
        }
        public void newGame() {
            String fName="[newGame]";
            getGuildProfile(gGlobal,lsGlobalHelper.llv2_GlobalsSettings,profileName,true,()->{iGuildPSafety();});
            gameSetup();
            debugPrintBricks();
            saveBricks();
            saveGuild(gGlobal);
            getPlayer(gMember);
            savePlayers();

            createBoard();
            postBoard();
            ask2SelectRow();
            listen2SelectRow();
        }
        public void newGame(String x,String y) {
            String fName="[newGame]";
            getGuildProfile(gGlobal,lsGlobalHelper.llv2_GlobalsSettings,profileName,true,()->{iGuildPSafety();});
            logger.info(fName+"x="+x+" y="+y);
            int ix=0,iy=0;
            try {
                if(!x.isBlank())ix=Integer.parseInt(x);
                if(!y.isBlank())iy=Integer.parseInt(y);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Please input number value!");
                return;
            }
            if(ix<=0){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Rows size can't be 0 or negative number!");
                return;
            }
            else if(ix<4){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Rows size can't be smaller than 4!");
                return;
            }
            else if(ix>13){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Rows size can't be bigger than 13!");
                return;
            }
            if(!y.isBlank()){
                if(iy<=0){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Columns size can't be 0 or negative number!");
                    return;
                }else
                if(iy<4){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Columns size can't be smaller than 4!");
                    return;
                }else
                if(iy>13){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Columns size can't be bigger than 13!");
                    return;
                }
            }


            TILE_ROWS=ix;TILE_COLUMNS=iy;
            MINE_NUMBER= (int) (TILE_COLUMNS*TILE_ROWS/10);
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS+", MINE_NUMBER="+MINE_NUMBER);
            gameSetup();
            debugPrintBricks();
            saveBricks();
            saveGuild(gGlobal);
            getPlayer(gMember);
            savePlayers();

            createBoard();
            postBoard();
            ask2SelectRow();
            listen2SelectRow();
        }
        public void calcMines(String x,String y) {
            String fName="[calcMines]";
            getGuildProfile(gGlobal,lsGlobalHelper.llv2_GlobalsSettings,profileName,true,()->{iGuildPSafety();});
            logger.info(fName+"x="+x+" y="+y);
            int ix=0,iy=0;
            try {
                if(!x.isBlank())ix=Integer.parseInt(x);
                if(!y.isBlank())iy=Integer.parseInt(y);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Please input number value!");
                return;
            }
            if(ix<=0){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Rows size can't be 0 or negative number!");
                return;
            }
            if(ix>20){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Rows size can't be bigger than 20!");
                return;
            }
            if(!y.isBlank()&&iy<=0){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Columns size can't be 0 or negative number!");
                return;
            }else
            if(!y.isBlank()&&iy>20){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Columns size can't be bigger than 20!");
                return;
            }
            else{
                iy=ix;
            }
            TILE_ROWS=ix;TILE_COLUMNS=iy;
            MINE_NUMBER= (int) (TILE_COLUMNS*TILE_ROWS/10);
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS+", MINE_NUMBER="+MINE_NUMBER);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setTitle(gTitle);
            embedBuilder.setColor(llColors.llColorGreen_Fern2);
            embedBuilder.addField("Rows&Columns",""+TILE_ROWS+"x"+TILE_COLUMNS,false);
            embedBuilder.addField("Bricks",""+(TILE_ROWS*TILE_COLUMNS),false);
            embedBuilder.addField("Mines",""+MINE_NUMBER,false);
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
        public void continueGame() {
            String fName="[continueGame]";
            getGuildProfile(gGlobal,lsGlobalHelper.llv2_GlobalsSettings,profileName,true,()->{iGuildPSafety();});
            getBricks();
            debugPrintBricks();
            getPlayers();

            createBoard();
            postBoard();
            ask2SelectRow();
            listen2SelectRow();
        }
        public void showAll() {
            String fName="[showAll]";
            getGuildProfile(gGlobal,lsGlobalHelper.llv2_GlobalsSettings,profileName,true,()->{iGuildPSafety();});
            getBricks();
            createBoard();
            postBoard();
        }

        Message messageBoard=null;
        EmbedBuilder embedBoard;
        public void createEmbed() {
            String fName="[createEmbed]";
            if(embedBoard!=null)return;
            embedBoard=new EmbedBuilder();
            embedBoard.setTitle(gTitle);embedBoard.setColor(llColors.llColorGreen_Fern2);
            logger.info(fName+"embed created");
        }
        public void createBoard() {
            String fName="[createBoard]";
            createEmbed();
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS);
            int x=0,y=0;
            StringBuilder strx= new StringBuilder();
            StringBuilder stry= new StringBuilder();
            StringBuilder columlabel=new StringBuilder();
            columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
            for(int i=0;i<TILE_COLUMNS;i++){
                switch (i){
                    case 0: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                        break;
                    case 1: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        break;
                    case 2: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        break;
                    case 3: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        break;
                    case 4: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        break;
                    case 5: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                        break;
                    case 6: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                        break;
                    case 7: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                        break;
                    case 8: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                        break;
                    case 9: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                        break;
                    case 10: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        break;
                    case 11: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                        break;
                    case 12: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        break;
                    case 13: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        break;
                    case 14: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                        break;
                    case 15: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                        break;
                    case 16: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                        break;
                    case 17: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                        break;
                    case 18: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                        break;
                    case 19: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                        break;
                    default: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
                        break;
                }
                if(TILE_COLUMNS>10&&i<TILE_COLUMNS)columlabel.append(" ");
            }
            strx.append(columlabel.toString()).append("\n");
            for (BRICK brick : bricks) {
                if(brick.getX() != x){
                    x = brick.getX();
                    strx.append(stry).append("\n");
                    stry = new StringBuilder();
                }
                if(stry.length()==0){
                    //row label
                    switch (x){
                        case 0: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            break;
                        case 1: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            break;
                        case 2: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            break;
                        case 3: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            break;
                        case 4: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                            break;
                        case 5: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                            break;
                        case 6: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                            break;
                        case 7: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                            break;
                        case 8: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                            break;
                        case 9: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                            break;
                        case 10: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                            break;
                        case 11: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                            break;
                        case 12: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                            break;
                        case 13: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                            break;
                        case 14: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                            break;
                        case 15: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                            break;
                        case 16: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                            break;
                        case 17: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            break;
                        case 18: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            break;
                        case 19: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        default: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
                            break;
                    }
                }
                if(brick.isVisible()){
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"].visible");
                    if(brick.isDetonated()){
                        logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]=detonated");
                        stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSkullCrossbones));
                    }
                    else if(brick.isMine()){
                        logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]=bomb");
                        stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
                    }
                    else {
                        logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]="+brick.getNumber());
                        switch (brick.getNumber()){
                            case 1: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                                break;
                            case 2: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                                break;
                            case 3: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                                break;
                            case 4: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                                break;
                            case 5: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                                break;
                            case 6: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                                break;
                            case 7: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                                break;
                            case 8: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                                break;
                            default: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                                break;
                        }
                    }
                }else{
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"].hidden");
                    stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteLargeSquare));
                }
                if(TILE_COLUMNS>10&&brick.getY()<TILE_COLUMNS-1)stry.append(" ");
            }
            if(!stry.toString().isBlank()){
                strx.append(stry);
            }
            String text=strx.toString();
            logger.info(fName+"bricks.text:\n"+text);
            logger.info(fName+"bricks.text.size:\n"+text.length());
            embedBoard.setDescription(text);
        }
        public void createBoardShowAll() {
            String fName="[createBoardShowAll]";
            createEmbed();
            int x=0,y=0;
            StringBuilder strx= new StringBuilder();
            StringBuilder stry= new StringBuilder();
            StringBuilder columlabel=new StringBuilder();
            columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
            for(int i=0;i<TILE_COLUMNS;i++){
                switch (i){
                    case 0: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                        break;
                    case 1: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                        break;
                    case 2: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                        break;
                    case 3: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                        break;
                    case 4: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                        break;
                    case 5: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                        break;
                    case 6: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                        break;
                    case 7: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                        break;
                    case 8: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                        break;
                    case 9: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                        break;
                    case 10: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        break;
                    case 11: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                        break;
                    case 12: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        break;
                    case 13: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        break;
                    case 14: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                        break;
                    case 15: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                        break;
                    case 16: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                        break;
                    case 17: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                        break;
                    case 18: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                        break;
                    case 19: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                        break;
                    default: columlabel.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
                        break;
                }
                columlabel.append(" ");
            }
            strx.append(columlabel.toString()).append("\n");
            for (BRICK brick : bricks) {
                if(brick.getX() != x){
                    x = brick.getX();
                    strx.append(stry).append("\n");
                    stry = new StringBuilder();
                }
                if(stry.length()==0){
                    //row lable
                    switch (x){
                        case 0: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            break;
                        case 1: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            break;
                        case 2: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            break;
                        case 3: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            break;
                        case 4: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                            break;
                        case 5: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                            break;
                        case 6: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                            break;
                        case 7: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                            break;
                        case 8: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                            break;
                        case 9: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                            break;
                        case 10: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                            break;
                        case 11: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                            break;
                        case 12: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                            break;
                        case 13: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                            break;
                        case 14: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                            break;
                        case 15: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                            break;
                        case 16: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                            break;
                        case 17: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            break;
                        case 18: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            break;
                        case 19: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        default: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackLargeSquare));
                            break;
                    }
                }
                if(brick.isDetonated()){
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]=detonated");
                    stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSkullCrossbones));
                }
                else if(brick.isMine()){
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]=bomb");
                    stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
                }
                else {
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]="+brick.getNumber());
                    switch (brick.getNumber()){
                        case 1: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                            break;
                        case 2: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                            break;
                        case 3: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                            break;
                        case 4: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                            break;
                        case 5: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                            break;
                        case 6: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                            break;
                        case 7: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            break;
                        case 8: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            break;
                        default: stry.append(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                            break;
                    }
                }
                if(TILE_COLUMNS>10&&brick.getY()<TILE_COLUMNS-1)stry.append(" ");
            }
            if(!stry.toString().isBlank()){
                strx.append(stry);
            }
            String text=strx.toString();
            logger.info(fName+"bricks.text:\n"+text);
            logger.info(fName+"bricks.text.size:\n"+text.length());
            embedBoard.setDescription(text);
        }
        public void postBoard() {
            String fName="[postBoard]";
            createEmbed();
            if(messageBoard!=null){
                lsMessageHelper.lsMessageDelete(messageBoard);
            }
            messageBoard=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBoard);
            logger.info(fName+"posted");
        }
        public void updateBoard() {
            String fName="[updateBoard]";
            if(messageBoard==null){
                postBoard();
                logger.info(fName+"posted");
            }else{
                messageBoard.editMessageEmbeds(embedBoard.build()).complete();
                logger.info(fName+"updated");
            }
        }
        public void lostGame() {
            String fName="[lostGame]";
            createEmbed();
            createBoard();
            embedBoard.addField("Game lost", playerWhoSelectedLast.getAsMention()+" choose badly. They lost in "+getPlayer(playerWhoSelectedLast).getTurns()+" turn(s).",false);
            StringBuilder desc2= new StringBuilder();
            Set<Long>setKey=players.keySet();
            Iterator<Long>iteratorKey=setKey.iterator();
            while(iteratorKey.hasNext()){
                PLAYER player=players.get(iteratorKey.next());
                if(player.getID()!=playerWhoSelectedLast.getIdLong()){
                    Member member=player.getMember(gGuild);
                    if(member!=null){
                        desc2.append("\n").append(member.getAsMention()).append(":").append(player.getTurns()).append(" turn(s)");
                    }else{
                        desc2.append("\n").append(player.getID()).append(":").append(player.getTurns()).append(" turn(s)");
                    }
                }
            }
            if(desc2.length()!=0){
                embedBoard.addField("Other players", desc2.toString(),false);
            }
            postBoard();
            new rdPishock(gGlobal,"game_punish",gGuild,gTextChannel,playerWhoSelectedLast,playerWhoSelectedLast);
        }
        public void woneGame() {
            String fName="[woneGane]";
            createEmbed();
            createBoard();
            embedBoard.addField("Game won", playerWhoSelectedLast.getAsMention()+" finished the game in "+getPlayer(playerWhoSelectedLast).getTurns()+" turn(s).",false);
            StringBuilder desc2= new StringBuilder();
            Set<Long>setKey=players.keySet();
            Iterator<Long>iteratorKey=setKey.iterator();
            while(iteratorKey.hasNext()){
                PLAYER player=players.get(iteratorKey.next());
                if(player.getID()!=playerWhoSelectedLast.getIdLong()){
                    Member member=player.getMember(gGuild);
                    if(member!=null){
                        desc2.append("\n").append(member.getAsMention()).append(":").append(player.getTurns()).append(" turn(s)");
                    }else{
                        desc2.append("\n").append(player.getID()).append(":").append(player.getTurns()).append(" turn(s)");
                    }
                }
            }
            if(desc2.length()!=0){
                embedBoard.addField("Other players", desc2.toString(),false);
            }
            postBoard();
        }
        public void stillGoingGame() {
            String fName="[stillGoingGame]";
            createEmbed();
            createBoard();
            postBoard();
        }
        int selectedI=-1,selectedJ=-1;Member playerWhoSelectedLast;
        public void ask2SelectRow() {
            String fName="[ask2SelectRow]";
            updateBoard();
            for(int i=0;i<TILE_ROWS;i++){
                if(isRowAvailable(i)){
                    switch (i){
                        case 0: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            break;
                        case 1:lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            break;
                        case 2: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            break;
                        case 3:lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            break;
                        case 4: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                            break;
                        case 5: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                            break;
                        case 6: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                            break;
                        case 7: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                            break;
                        case 8: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                            break;
                        case 9: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                            break;
                        case 10: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                            break;
                        case 11: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                            break;
                        case 12: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                            break;
                        case 13: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                            break;
                        case 14: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                            break;
                        case 15: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                            break;
                        case 16: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                            break;
                        case 17: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            break;
                        case 18: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            break;
                        case 19: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                    }
                }
            }
        }
        public void listen2SelectRow() {
            String fName="[listen2SelectRow]";
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(messageBoard.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))selectedI=0;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))selectedI=1;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))selectedI=2;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))selectedI=3;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)))selectedI=4;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)))selectedI=5;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)))selectedI=6;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)))selectedI=7;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI)))selectedI=8;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ)))selectedI=9;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)))selectedI=10;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)))selectedI=11;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)))selectedI=12;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)))selectedI=13;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)))selectedI=14;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)))selectedI=15;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)))selectedI=16;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)))selectedI=17;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)))selectedI=18;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)))selectedI=19;

                            if(selectedI<0){
                                listen2SelectRow();
                            }else{
                                lsMessageHelper.lsMessageClearReactions(messageBoard);
                                ask2SelectColumn();
                                listen2SelectColumn();
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(messageBoard);messageBoard=null;});
        }
        public void ask2SelectColumn() {
            String fName="[ask2SelectColumn]";
            updateBoard();
            for(int i=0;i<TILE_COLUMNS;i++){
                BRICK brick=getBrick(selectedI,i);
                if(!brick.isVisible()){
                    switch (i){
                        case 0: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                            break;
                        case 1: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                            break;
                        case 2: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                            break;
                        case 3: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                            break;
                        case 4: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                            break;
                        case 5: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                            break;
                        case 6: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                            break;
                        case 7: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            break;
                        case 8: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            break;
                        case 9: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        case 10: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            break;
                        case 11: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            break;
                        case 12:lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            break;
                        case 13: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            break;
                        case 14: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                            break;
                        case 15: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                            break;
                        case 16: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG));
                            break;
                        case 17: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                            break;
                        case 18: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI));
                            break;
                        case 19: lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ));
                            break;
                    }
                }
            }
        }
        public void listen2SelectColumn() {
            String fName="[listen2SelectColumn]";
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(messageBoard.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)))selectedJ=0;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)))selectedJ=1;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)))selectedJ=2;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)))selectedJ=3;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)))selectedJ=4;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)))selectedJ=5;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)))selectedJ=6;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)))selectedJ=7;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)))selectedJ=8;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)))selectedJ=9;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))selectedI=10;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))selectedI=11;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))selectedI=12;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))selectedI=13;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)))selectedI=14;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)))selectedI=15;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)))selectedI=16;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)))selectedI=17;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolI)))selectedI=18;
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolJ)))selectedI=19;

                            if(selectedJ<0){
                                listen2SelectRow();
                            }else{
                                lsMessageHelper.lsMessageClearReactions(messageBoard);
                                ask2Option();
                                listen2Option();
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(messageBoard);messageBoard=null;});
        }
        public void ask2Option() {
            String fName="[ask2Option]";
            updateBoard();
            BRICK brick=getBrick(selectedI,selectedJ);
            if(!brick.isVisible()){
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTriangularFlagOnPost));
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
            }
            lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
        }
        public void listen2Option() {
            String fName="[listen2Option]";
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(messageBoard.getId())&&!e.getUser().isBot()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            PLAYER player=getPlayer(e.getMember());
                            player.incTurns();
                            playerWhoSelectedLast =e.getMember();
                            setPlayer(player);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                lsMessageHelper.lsMessageClearReactions(messageBoard);
                                ask2SelectRow();listen2SelectRow();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTriangularFlagOnPost))){
                                BRICK brick=getBrick(selectedI,selectedJ);
                                if(brick.isFlagged()){
                                    brick.setFlagged(false);
                                }else{
                                    brick.setFlagged(true);
                                }
                                setBrick(selectedI,selectedJ,brick);
                                saveBricks();savePlayers();saveGuild(gGlobal);
                                if(!isRemaining()){
                                    woneGame();
                                    player.setWinner(true);
                                    player.setFinished(true);
                                }
                                else{
                                    continueGame();
                                }
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                BRICK brick=getBrick(selectedI,selectedJ);
                                brick.setFlagged(false);
                                brick.setVisible(true);
                                if(brick.isMine()){
                                    brick.setDetonated(true);

                                }
                                setBrick(selectedI,selectedJ,brick);
                                saveBricks();savePlayers();saveGuild(gGlobal);
                                if(brick.isDetonated()){
                                    lostGame();
                                    player.setLoser(true);
                                    player.setFinished(true);
                                }
                                else if(!isRemaining()){
                                    woneGame();
                                    player.setWinner(true);
                                    player.setFinished(true);
                                }
                                else{
                                    continueGame();
                                }
                            }
                            else{
                                listen2Option();
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(messageBoard);messageBoard=null;});
        }

        public void gameSetup () {
            String fName="[gameSetup]";
            gameCreateBricks();
            gameMineGenerator();
            gameNumberGenerator();
        }
        private void gameCreateBricks () {
            String fName="[gameCreateBricks]";
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS);
            for (int i=0;i<TILE_ROWS;i++) {
                for (int j=0;j<TILE_COLUMNS;j++) {
                    bricks.add(new BRICK(i,j,bricks.size()));
                }
            }
        }
        /** Randomly generate mines throughtout the board */
        private void gameMineGenerator () {
            String fName="[gameMineGenerator]";
            logger.info(fName+"MINE_NUMBER="+MINE_NUMBER);
            int i=0,d=0;
            while (i<MINE_NUMBER&&d<500) {
                int x=lsUsefullFunctions.getRandom(TILE_COLUMNS-1);
                int y=lsUsefullFunctions.getRandom(TILE_ROWS-1);
                logger.info(fName+"i["+i+"]:x="+x+", y="+y);
                BRICK brick=getBrick(x,y);
                if(!brick.isMine()){
                    logger.info(fName+"brick is not a mine");
                    brick.setMine(true);
                    i++;
                    setBrick(x,y,brick);
                }
                d++;
            }
        }
        /** Generates the numbers on each of the tiles throughout the board. Each number denoted the number of mines surrounding
         * the tile. 0 is not displayed. */
        private void gameNumberGenerator() {
            String fName="[gameNumberGenerator]";
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS+", MINE_NUMBER="+MINE_NUMBER);
            for (int i=0;i<TILE_ROWS;i++) {
                for (int j=0;j<TILE_COLUMNS;j++) {
                    BRICK brick=getBrick(i,j);
                    if (!brick.isMine()){
                        brick.setNumber(gameCountSurround(i,j));
                    }
                    setBrick(i,j,brick);
                }
            }
        }
        /** Counts the mines surrounding the Tile at [i,j] */
        private int gameCountSurround (int i, int j) {
            String fName="[gameCountSurround]";
            int count=0;
            if (i+1!=TILE_COLUMNS && getBrick(i+1,j).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 1");
                count++;
            }
            if (i+1!=TILE_COLUMNS &&  j+1!=TILE_ROWS &&getBrick(i+1,j+1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 2");
                count++;
            }
            if (i-1!=-1 && j+1!=TILE_ROWS &&getBrick(i-1,j+1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 3");
                count++;
            }
            if (i+1!=TILE_COLUMNS && j-1!=-1 && getBrick(i+1,j-1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 4");
                count++;
            }
            if (i-1!=-1 && j-1!=-1 && getBrick(i-1,j-1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 5");
                count++;
            }
            if (i-1!=-1 && getBrick(i-1,j).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 6");
                count++;
            }
            if (j+1!=TILE_ROWS && getBrick(i,j+1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 7");
                count++;
            }
            if (j-1!=-1 &&  getBrick(i,j-1).isMine()) {
                logger.info(fName+"i="+i+", j="+j+", trigger 8");
                count++;
            }
            logger.info(fName+"i="+i+", j="+j+", count="+count);
            return count;
        }
        public boolean isRemaining() {
            String fName="[isRemaining]";
            for (BRICK brick:bricks) {
                if(!brick.isVisible()&&!brick.isMine()){
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]is not visible and not a mine>true");
                    return true;
                }
            }
            logger.info(fName+"default>false");
            return false;
        }
        public boolean isRowAvailable(int x) {
            String fName="[isRowAvailable]";
            logger.info(fName+"x="+x);
            for (int j=0;j<TILE_COLUMNS;j++) {
                BRICK brick=getBrick(x,j);
                if(!brick.isVisible()){
                    logger.info(fName+"brick["+brick.getX()+","+brick.getY()+"]is not visible>true");
                    return true;
                }
            }
            logger.info(fName+"default>false");
            return false;
        }

        List<BRICK>bricks=new ArrayList<>();
        class BRICK{
            boolean raised=false,filled=false;
            int x=-1,y=-1;
            int index;
            String str="";
            boolean mine=false,flagged=false,visible=false,detonated=false;
            int number=-1;
            public BRICK(){

            }
            public BRICK(JSONObject jsonObject){
                setJSON(jsonObject);
            }
            public BRICK(int x, int y){
                this.x=x;this.y=y;
            }
            public BRICK(int x, int y,int index){
                this.x=x;this.y=y;this.index=index;
            }
            private int getX() {
                String fName="[getX]";
                try {
                    return x;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            private int getY() {
                String fName="[getY]";
                try {
                    return y;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            private boolean setX(int index) {
                String fName="[setX]";
                try {
                    x=index;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean setY(int index) {
                String fName="[setY]";
                try {
                    y=index;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private int getIndex() {
                String fName="[getIndex]";
                try {
                    return index;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            private boolean setIndex(int index) {
                String fName="[setIndex]";
                try {
                    this.index=index;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean isFlagged() {
                String fName="[isFlagged]";
                try {
                    return flagged;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean setFlagged(boolean value) {
                String fName="[setFlagged]";
                try {
                    this.flagged=value;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean isVisible() {
                String fName="[isVisible]";
                try {
                    return visible;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean setVisible(boolean value) {
                String fName="[setVisible]";
                try {
                    this.visible=value;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean isMine() {
                String fName="[isMine]";
                try {
                    return mine;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean setMine(boolean value) {
                String fName="[setMine]";
                try {
                    this.mine=value;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean isDetonated() {
                String fName="[isDetonated]";
                try {
                    return detonated;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private boolean setDetonated(boolean value) {
                String fName="[setDetonated]";
                try {
                    this.detonated=value;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private int getNumber() {
                String fName="[getNumber]";
                try {
                    return number;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            private boolean setNumber(int value) {
                String fName="[setNumber]";
                try {
                    this.number=value;
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private String print() {
                String fName="[print]";
                try {
                    return "{x:"+x+", y:"+y+", index:"+index+", mine:"+mine+", number:"+number+", flagged:"+flagged+"}";
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "{error}";
                }
            }
            String keyX="x",keyY="y",keyIndex="index",keyMine="mine",keyNumber="number",keyFlagged="flagged",keyVisible="visible";
            private JSONObject getJSON() {
                String fName="[getJSON]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyX,x); jsonObject.put(keyY,y); jsonObject.put(keyIndex,index);
                    jsonObject.put(keyMine,mine); jsonObject.put(keyFlagged,flagged); jsonObject.put(keyNumber,number);
                    jsonObject.put(keyVisible,visible);
                    logger.info(fName+"jsonObject="+jsonObject.toString());
                    return jsonObject;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            private boolean setJSON(JSONObject jsonObject) {
                String fName="[getJSON]";
                try {
                    logger.info(fName+"jsonObject="+jsonObject.toString());
                    x=jsonObject.optInt(keyX,-1);y=jsonObject.optInt(keyY,-1);index=jsonObject.optInt(keyIndex,-1);
                    number=jsonObject.optInt(keyNumber,-1);flagged=jsonObject.optBoolean(keyFlagged,false);mine=jsonObject.optBoolean(keyMine,false);
                    visible=jsonObject.optBoolean(keyVisible,false);
                    return true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
        }
        private boolean getBricks () {
            String fName="[getBricks]";
            logger.info(fName);
            bricks=new ArrayList<>();
            TILE_COLUMNS=gGuildProfile.getFieldEntryAsInt(keyColumns);
            TILE_ROWS=gGuildProfile.getFieldEntryAsInt(keyRows);
            MINE_NUMBER=gGuildProfile.getFieldEntryAsInt(keyMineNumber);
            logger.info(fName+"TILE_COLUMNS="+TILE_COLUMNS+", TILE_ROWS="+TILE_ROWS+", MINE_NUMBER="+MINE_NUMBER);
            JSONArray jsonArray=gGuildProfile.optFieldEntryAsJSONArray(keyBricks);
            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                BRICK brick=new BRICK(jsonArray.getJSONObject(i));
                bricks.add(brick);
            }
            return true;
        }
        private boolean saveBricks () {
            String fName="[saveBricks]";
            logger.info(fName);
            JSONArray jsonArray=new JSONArray();
            for (BRICK brick : bricks) {
                jsonArray.put(brick.getJSON());
            }
            logger.info(fName+"jsonArray="+jsonArray.toString());
            gGuildProfile.putFieldEntry(keyBricks,jsonArray);
            gGuildProfile.putFieldEntry(keyMineNumber,MINE_NUMBER);
            gGuildProfile.putFieldEntry(keyColumns,TILE_COLUMNS);
            gGuildProfile.putFieldEntry(keyRows,TILE_ROWS);
            return true;
        }
        private void debugPrintBricks () {
            String fName="[debugPrintBricks]";
            logger.info(fName);
            int x=0,y=0;
            StringBuilder strx= new StringBuilder();
            StringBuilder stry= new StringBuilder();
            for (BRICK brick : bricks) {
                if (brick.getX() == x) {
                    if (!stry.toString().isBlank()) {
                        stry.append(", ");
                    }
                    stry.append(brick.print());
                } else {
                    x = brick.getX();
                    strx.append(stry).append("\n");
                    stry = new StringBuilder();
                }
            }
            if(!stry.toString().isBlank()){
                strx.append(stry);
            }
            logger.info(fName+"bricks:\n"+strx);
        }
        private BRICK getBrick (int x, int y) {
            String fName="[getBrick]";
            logger.info(fName+"x="+x+", y="+y);
            for (int i=0;i<bricks.size();i++) {
                BRICK brick=bricks.get(i);
                if(brick.getX()==x&&brick.getY()==y){
                    logger.info(fName+"success");
                    return brick;
                }
            }
            logger.info(fName+"default");
            return  new BRICK();
        }
        private boolean setBrick (int x, int y,BRICK brick) {
            String fName="[setBrick]";
            logger.info(fName+"x="+x+", y="+y+", brick="+brick.print());
            for (int i=0;i<bricks.size();i++) {
                BRICK brickt=bricks.get(i);
                if(brickt.getX()==x&&brickt.getY()==y){
                    logger.info(fName+"found and so set");
                    bricks.set(i,brick);
                    logger.info(fName+"setted to:"+bricks.get(i).print());
                    return true;
                }
            }
            logger.info(fName+"not found>false");
            return false;
        }

        Map<Long,PLAYER>players=new HashMap<>();
        String keyID="id",keyTimeStampFirstPlayed="firstPlayed",keyTimeStampLastPlayed="lastPlayed",keyWiner="winer",keyLoser="loser",keyFinished="finished",keyTurns="turns";
        class PLAYER{
            long id=0;
            long timeStampFirstPlayed=0,timeStampLastPlayed=0;
            int turns=0;
            boolean winner=false,loser=false,finished=false;
            public PLAYER(){

            }
            public PLAYER(JSONObject jsonObject){
                setJSON(jsonObject);
            }
            public PLAYER(Member member){
               setMember(member);updateTimeStampFirstPlayed();
            }
            public PLAYER(long id){
                setID(id);updateTimeStampFirstPlayed();
            }
            private JSONObject getJSON() {
                String fName="[getJSON]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(keyID,id);
                    jsonObject.put(keyTimeStampFirstPlayed,timeStampFirstPlayed); jsonObject.put(keyTimeStampLastPlayed,timeStampLastPlayed);
                    jsonObject.put(keyWiner,winner);jsonObject.put(keyLoser,loser);jsonObject.put(keyFinished,finished);
                    jsonObject.put(keyTurns,turns);
                    logger.info(fName+"jsonObject="+jsonObject.toString());
                    return jsonObject;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            private PLAYER setJSON(JSONObject jsonObject) {
                String fName="[getJSON]";
                try {
                    logger.info(fName+"jsonObject="+jsonObject.toString());
                    id=jsonObject.optLong(keyID);
                    timeStampFirstPlayed=jsonObject.optLong(keyTimeStampFirstPlayed);timeStampLastPlayed=jsonObject.optLong(keyTimeStampLastPlayed);
                    winner=jsonObject.optBoolean(keyWiner);loser=jsonObject.optBoolean(keyLoser);finished=jsonObject.optBoolean(keyFinished);
                    turns=jsonObject.optInt(keyTurns);
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private PLAYER setMember(Member member) {
                String fName="[setMember]";
                try {
                    logger.info(fName+"member="+member.getId());
                    this.id=member.getIdLong();
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private Member getMember(Guild guild) {
                String fName="[getMember]";
                try {
                    logger.info(fName+"guild="+guild.getId()+", user="+id);
                    return guild.getMemberById(id);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private PLAYER setID(long id) {
                String fName="[setID]";
                try {
                    logger.info(fName+"id="+id);
                    this.id=id;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private long getID() {
                String fName="[getID]";
                try {
                    return id;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            private PLAYER setTurns(int value) {
                String fName="[setTurns]";
                try {
                    logger.info(fName+"value="+value);
                    this.turns=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private PLAYER incTurns() {
                String fName="[incTurns]";
                try {
                    turns++;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private int getTurns() {
                String fName="[getTurns]";
                try {
                    return turns;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            private PLAYER setTimeStampFirstPlayed(long value) {
                String fName="[setTimeStampFirstPlayed]";
                try {
                    logger.info(fName+"value="+value);
                    this.timeStampFirstPlayed=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private PLAYER updateTimeStampFirstPlayed() {
                String fName="[updateTimeStampFirstPlayed]";
                try {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    logger.info(fName+".timestamp="+timestamp.getTime());
                    this.timeStampFirstPlayed=timestamp.getTime();
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private long getTimeStampFirstPlayed() {
                String fName="[getTimeStampFirstPlayed]";
                try {
                    return timeStampFirstPlayed;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            private PLAYER setTimeStampLastPlayed(long value) {
                String fName="[setTimeStampLastPlayed]";
                try {
                    logger.info(fName+"value="+value);
                    this.timeStampLastPlayed=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private PLAYER updateTimeStampLastPlayed() {
                String fName="[updateTimeStampLastPlayed]";
                try {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    logger.info(fName+".timestamp="+timestamp.getTime());
                    this.timeStampLastPlayed=timestamp.getTime();
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private long getTimeStampLastPlayed() {
                String fName="[getTimeStampLastPlayed]";
                try {
                    return timeStampLastPlayed;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            private PLAYER setWinner(boolean value) {
                String fName="[setWinner]";
                try {
                    logger.info(fName+"value="+value);
                    this.winner=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private boolean getWinner() {
                String fName="[getWinner]";
                try {
                    return winner;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private PLAYER setLoser(boolean value) {
                String fName="[setLoser]";
                try {
                    logger.info(fName+"value="+value);
                    this.loser=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private boolean getLoser() {
                String fName="[getLoser]";
                try {
                    return loser;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private PLAYER setFinished(boolean value) {
                String fName="[setFinished]";
                try {
                    logger.info(fName+"value="+value);
                    this.finished=value;
                    return this;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private boolean getFinished() {
                String fName="[getFinished]";
                try {
                    return finished;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }

        }
        private boolean getPlayers () {
            String fName="[getPlayers]";
            try {
                logger.info(fName);
                players=new HashMap<>();
                JSONArray jsonArray=gGuildProfile.getFieldEntryAsJSONArray(keyPlayers);
                logger.info(fName+"jsonArray="+jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    PLAYER player=new PLAYER(jsonArray.getJSONObject(i));
                    players.put(player.getID(),player);
                }
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean savePlayers () {
            String fName="[savePlayers]";
            try {
                logger.info(fName);
                JSONArray jsonArray=new JSONArray();
                Set<Long>setKeys=players.keySet();
                Iterator<Long>iteratorKeys=setKeys.iterator();
                while(iteratorKeys.hasNext()){
                    long id=iteratorKeys.next();
                    PLAYER player=players.get(id);
                    jsonArray.put(player.getJSON());
                }
                logger.info(fName+"jsonArray="+jsonArray.toString());
                gGuildProfile.putFieldEntry(keyPlayers,jsonArray);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private PLAYER getPlayer (Member member) {
            String fName="[getPlayer]";
            try {
                logger.info(fName+"member="+member.getId());
                PLAYER player=players.getOrDefault(member.getIdLong(),new PLAYER(member));
                return  player;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new PLAYER();
            }
        }
        private boolean setPlayer (PLAYER player) {
            String fName="[setPlayer]";
            try {
                logger.info(fName+"player.id="+player.getID());
                if(player.getID()<=0){
                    logger.info(fName+"invalid id>false");
                    return false;
                }
                players.put(player.getID(),player);
                logger.info(fName+"set>true");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }


        lcJSONGuildProfile gGuildProfile;

        String keyBricks="bricks",keyPlayers="players",keyColumns="columns",keyRows="rows",keyMineNumber="mineNumber";
        public void iGuildPSafety(){
            String fName="iSafety";
            gGuildProfile.safetyPutFieldEntry(keyBricks, new JSONArray());
            gGuildProfile.safetyPutFieldEntry(keyMineNumber, 0);
            gGuildProfile.safetyPutFieldEntry(keyColumns, 0);
            gGuildProfile.safetyPutFieldEntry(keyRows, 0);
            gGuildProfile.safetyPutFieldEntry(keyPlayers, new JSONArray());
            gGuildProfile.safetyPutFieldEntry(keyFinished, false);
            gGuildProfile.safetyPutFieldEntry(keyTurns, 0);
        }
        String profileName="Minesweeper",table="MemberProfile";
        private lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
            String fName = "[iSafetyUserProfileEntry]";
            return gUserProfile;
        }

    }
}
