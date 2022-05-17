package fun.games;

//import com.github.lalyos.jfiglet.FigletFont;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.lcBasicFeatureControl;
import models.lc.lcText2List;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.rdPishock;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

//https://github.com/DonnyStout/HangMan
public class HangMan extends Command implements llGlobalHelper{
    Logger logger = Logger.getLogger(getClass()); String cName="[HangMan]";
    lcGlobalHelper gGlobal;
    String gTitle="HangMan[beta]",gCommand="hangman";

    public HangMan(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "HangMan[beta]";
        this.help = "The classic HangMan game, but with a twist.";
        this.aliases = new String[]{gCommand,"gamehangman"};
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
    protected class runLocal implements Runnable {
        CommandEvent gEvent;String cName = "[runLocal]";
        User gUser;Member gMember;
        Guild gGuild;TextChannel gTextChannel;
        Message gMessage;
        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage=gEvent.getMessage();

        }

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"bfc_HangMan",gGlobal);
                gBasicFeatureControl.initProfile();
                String[] items;
                boolean isInvalidCommand=true;

                if(gEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(cName + fName + ".Args");
                    items = gEvent.getArgs().split("\\s+");
                    logger.info(cName + fName + ".items.size=" + items.length);
                    logger.info(cName + fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
                        if(items.length>2){
                            // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                            int group=0,type=0,action=0;
                            switch (items[1].toLowerCase()){
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
                            switch (items[2].toLowerCase()){
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
                                    setChannel(type,action,gEvent.getMessage());
                                }
                            }
                            else if(group==2){
                                if(action==0){
                                    getRoles(type,false);isInvalidCommand=false;
                                }else{
                                    setRole(type,action,gEvent.getMessage());
                                }
                            }
                        }else{
                            menuGuild();isInvalidCommand=false;
                        }
                    }
                    else if(items[0].equalsIgnoreCase("help")){
                        help("main"); isInvalidCommand=false;
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
                    else if(items[0].equalsIgnoreCase("testgame")){
                       testGame(); isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("new")){
                        newGame();
                        isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("load")){
                        continueGame(); isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("show")){
                        showAll(); isInvalidCommand=false;
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
            desc="\n`"+quickSummonWithSpace+"new` to start new game";
            desc+="\n`"+quickSummonWithSpace+"load` to load previous game";
            embedBuilder.addField("Commands",desc,false);
            desc="\n"+"Can use "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward)+", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward)+", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+", "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" to scroll between characters.";
            desc+="\n"+"Select a character from "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ)+" to guess.";
            desc+="\n"+"To make it more difficult, you can chose again the wrong character.";
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
        public void testGame() {
            String fName="[testGame]";
            try {

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void newGame() {
            String fName="[newGame]";
            try {
                getGuildProfile();
                getFile();
                setUpWords(getRandomWord());
                savePlayers();saveValues();
                saveGuild();

                createEmbed();
                createBoard(0,"");
                postBoard();
                abcindex=-1;showABCForward();
                listenABC();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        public void continueGame() {
            String fName="[continueGame]";
            try {
                getGuildProfile();
                getValues();
                getPlayers();

                createEmbed();
                createBoard(0,"");
                postBoard();
                abcindex=-1;showABCForward();
                listenABC();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void showAll() {
            String fName="[showAll]";
            try {
                getGuildProfile();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        Message messageBoard=null;
        EmbedBuilder embedBoard;
        public void createEmbed() {
            String fName="[createEmbed]";
            try {
                if(embedBoard!=null)return;
                embedBoard=new EmbedBuilder();
                embedBoard.setTitle(gTitle);embedBoard.setColor(llColors.llColorGreen_Fern2);
                logger.info(fName+"embed created");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void createBoard(int type,String strChar) {
            String fName="[createBoard]";
            try {
                createEmbed();
                String text="";
                String ascii=HANGMANASCII.valueByCode(4+misses).getText();
                text+=ascii;
                text+="\n\n"+filledoutWord;
                switch (type){
                    case 1:
                        text+="\n"+lastMember.getAsMention()+" chose "+strChar+", an wrong word!";
                        break;
                    case 2:
                        text+="\n"+lastMember.getAsMention()+" chose "+strChar+", an correct word";
                        break;
                }

                embedBoard.setDescription(text);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void gamewon() {
            String fName="[gamewon]";
            try {
                createEmbed();
                String text="";
                String ascii=HANGMANASCII.valueByCode(4+misses).getText();
                text+=ascii;
                text+="\n\n"+filledoutWord;
                text+="\n"+lastMember.getAsMention()+" won the game";
                StringBuilder desc2= new StringBuilder();
                Set<Long>setPlayerKey=players.keySet();
                for (Long aLong : setPlayerKey) {
                    PLAYER player = players.get(aLong);
                    if (player.getID() != lastMember.getIdLong()) {
                        Member member = player.getMember(gGuild);
                        if (member != null) {
                            desc2.append(member.getAsMention()).append(" [").append(player.getTurns()).append(" turn(s)] ");
                        } else {
                            desc2.append(player.getID()).append(" [").append(player.getTurns()).append(" turn(s)] ");
                        }
                    }
                }
                if(desc2.length()!=0){
                    text+="\nOther players:"+desc2.toString();
                }
                Iterator<String>iteratorGuessesKey=guesses.keys();
                desc2= new StringBuilder();
                while(iteratorGuessesKey.hasNext()){
                    String key=iteratorGuessesKey.next();
                    if(!guesses.getBoolean(key)){
                        if(desc2.length()>0)desc2.append(", ");
                        desc2.append(key);
                    }
                }
                if(desc2.length()!=0){
                    text+="\nWrong characters:"+desc2.toString();
                }
                embedBoard.setDescription(text);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void gamelost() {
            String fName="[gamelost]";
            try {
                createEmbed();
                String text="";
                logger.info(fName+"misses="+misses);
                String ascii=HANGMANASCII.valueByCode(4+misses).getText();
                text+=ascii;
                text+="\n\n"+filledoutWord;
                text+="\n"+lastMember.getAsMention()+" used up all the chances";
                StringBuilder desc2= new StringBuilder();
                Set<Long>setPlayerKey=players.keySet();
                for (Long aLong : setPlayerKey) {
                    PLAYER player = players.get(aLong);
                    if (player.getID() != lastMember.getIdLong()) {
                        Member member = player.getMember(gGuild);
                        if (member != null) {
                            desc2.append(member.getAsMention()).append(" [").append(player.getTurns()).append(" turn(s)] ");
                        } else {
                            desc2.append(player.getID()).append(" [").append(player.getTurns()).append(" turn(s)] ");
                        }
                    }
                }
                if(desc2.length()!=0){
                    text+="\nOther players:"+desc2.toString();
                }
                Iterator<String>iteratorGuessesKey=guesses.keys();
                desc2= new StringBuilder();
                while(iteratorGuessesKey.hasNext()){
                    String key=iteratorGuessesKey.next();
                    if(!guesses.getBoolean(key)){
                        if(desc2.length()>0)desc2.append(", ");
                        desc2.append(key);
                    }
                }
                if(desc2.length()!=0){
                    text+="\nWrong characters:"+desc2.toString();
                }
                embedBoard.setDescription(text);
                new rdPishock(gGlobal,"game_punish",gGuild,gTextChannel,lastMember,lastMember);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void postBoard() {
            String fName="[postBoard]";
            try {
                createEmbed();
                if(messageBoard!=null){
                    lsMessageHelper.lsMessageDelete(messageBoard);
                }
                messageBoard=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBoard);
                logger.info(fName+"posted");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void updateBoard() {
            String fName="[updateBoard]";
            try {
                if(messageBoard==null){
                    postBoard();
                    logger.info(fName+"posted");
                }else{
                    messageBoard.editMessageEmbeds(embedBoard.build()).complete();
                    logger.info(fName+"updated");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        int abcindex=-1;
        List<String>listABCD=new ArrayList<>(
                Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        );
        List<Character>listCharABCD=new ArrayList<>(
                Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        );
        Member lastMember=null;PLAYER lastPlayer=new PLAYER();
        public void showABCForward() {
            String fName="[showABCForward]";
            try {
                if(messageBoard==null){
                    logger.info(fName+"is null>false");
                    return;
                }
                logger.info(fName+"abcindex="+abcindex);
                int d=0,n=0;
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                if(abcindex>9)lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                logger.info(fName+"listABCD.size()="+listABCD.size());
                while(d<10&&n<28000){
                    n++;d++;
                    abcindex++;
                    logger.info(fName+"while.abcindex="+abcindex+", d="+d+", n="+n);
                    if(abcindex<listABCD.size()&&abcindex>-1){
                        String strChar=listABCD.get(abcindex);
                        logger.info(fName+"strChar="+strChar+"");
                        lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji("regional_indicator_symbol_"+strChar.toLowerCase()));
                    }
                }
                if(abcindex<listCharABCD.size()-1)lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                logger.info(fName+"done");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void showABCBackward() {
            String fName="[showABCBackward]";
            try {
                if(messageBoard==null){
                    logger.info(fName+"is null>false");
                    return;
                }
                logger.info(fName+"abcindex="+abcindex);
                int d=0,n=0;
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                if(abcindex>9)lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                logger.info(fName+"listABCD.size()="+listABCD.size());
                while(d<10&&n<28000){
                    n++;d++;
                    abcindex--;
                    logger.info(fName+"while.abcindex="+abcindex+", d="+d+", n="+n);
                    if(abcindex<listABCD.size()&&abcindex>-1){
                        String strChar=listABCD.get(abcindex);
                        logger.info(fName+"strChar="+strChar+"");
                        lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji("regional_indicator_symbol_"+strChar.toLowerCase()));
                    }
                }
                if(abcindex<listCharABCD.size()-1)lsMessageHelper.lsMessageAddReactions(messageBoard,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                logger.info(fName+"done");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void listenABC() {
            String fName="[listenABC]";
            try {
                if(messageBoard==null){
                    logger.info(fName+"is null>false");
                    return;
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(messageBoard.getId())&&!e.getUser().isBot()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    lsMessageHelper.lsMessageDelete(messageBoard);messageBoard=null;
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                    lsMessageHelper.lsMessageClearReactions(messageBoard);
                                    abcindex=-1;showABCForward();
                                    listenABC();
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                    lsMessageHelper.lsMessageClearReactions(messageBoard);
                                    abcindex=9;showABCForward();
                                    listenABC();
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                    lsMessageHelper.lsMessageClearReactions(messageBoard);
                                    abcindex=19;showABCForward();
                                    listenABC();
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    lsMessageHelper.lsMessageClearReactions(messageBoard);
                                    showABCBackward();
                                    listenABC();
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    lsMessageHelper.lsMessageClearReactions(messageBoard);
                                    showABCForward();
                                    listenABC();
                                    return;
                                }
                                String strChar="";
                                lastMember=e.getMember();
                                lastPlayer=getPlayer(lastMember);
                                lastPlayer.incTurns();
                                turns++;
                                boolean isCharacter=false,found=false;
                                for (String s : listABCD) {
                                    strChar = s;
                                    logger.info(fName + "strChar=" + strChar + "");
                                    if (name.equalsIgnoreCase(gGlobal.emojis.getEmoji("regional_indicator_symbol_" + strChar.toLowerCase()))) {
                                        logger.info(fName + "strChar=" + strChar + ", equal");
                                        isCharacter = true;
                                        break;
                                    }
                                }
                                logger.info(fName+"isCharacter="+isCharacter);
                                if(isCharacter){
                                    logger.info(fName+"selectedWord="+selectedWord+", char="+strChar);
                                    if(selectedWord.toLowerCase().contains(strChar.toLowerCase())){
                                        found=true;
                                    }
                                    logger.info(fName+"found="+found);
                                    if(found){
                                        guesses.put(strChar,true);
                                        char c=strChar.toLowerCase().charAt(0);
                                        logger.info(fName+"filledoutWord_before="+filledoutWord+", char="+c);
                                        for(int i=0;i<selectedWord.length();i++){
                                            char sel=selectedWord.charAt(i);
                                            logger.info(fName+"sel="+sel);
                                            if(sel==c){
                                                logger.info(fName+"found>replace");
                                                filledoutWord=lsStringUsefullFunctions.changeCharInPosition(filledoutWord,i,c);
                                            }
                                        }
                                        logger.info(fName+"filledoutWord_after="+filledoutWord);
                                        if(filledoutWord.contains("?")){
                                            //still has
                                            saveValues();savePlayers();saveGuild();
                                            createBoard(2,strChar);
                                            postBoard();
                                            abcindex=-1;showABCForward();
                                            listenABC();
                                        }else{
                                            //won
                                            finished=true;
                                            saveValues();savePlayers();saveGuild();
                                            gamewon();
                                            postBoard();
                                        }
                                    }else{
                                        guesses.put(strChar,false);
                                        //wrong
                                        misses++;
                                        if(misses>=TOTALMISSES){
                                            //game lost
                                            finished=true;
                                            saveValues();savePlayers();saveGuild();
                                            gamelost();
                                            postBoard();
                                        }else{
                                            //still has chances
                                            saveValues();savePlayers();saveGuild();
                                            createBoard(1,strChar);
                                            postBoard();
                                            abcindex=-1;showABCForward();
                                            listenABC();
                                        }
                                    }
                                }

                            }catch (Exception e2) {
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },15, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(messageBoard);messageBoard=null;});
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        lcText2List TEXTFILE=new lcText2List();
        List<String>words=new ArrayList<>();
        int TOTALMISSES=6;
        public boolean getFile() {
            String fName="[getFile]";
            try {
                if(!TEXTFILE.getFile2List("resources/txt/hangmanwords.txt")){
                    logger.info(fName+"failed to get file>false");
                    return false;
                }
                if(TEXTFILE.isListEmpty()){
                    logger.info(fName+"is Empty>false");
                    return false;
                }
                words=TEXTFILE.textLines;
                logger.info(fName+"transfer>false");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getRandomWord() {
            String fName="[getRandomWord]";
            try {
                int i=words.size();
                logger.info(fName+"i="+i);
                int r=lsUsefullFunctions.getRandom(i);
                logger.info(fName+"r="+r);
                return words.get(r);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean setUpWords(String word) {
            String fName="[setUpWords]";
            try {
                logger.info(fName+"word="+word);
                selectedWord=word;filledoutWord="";
                StringBuilder tmp= new StringBuilder();
                int r=lsUsefullFunctions.getRandom(selectedWord.length());
                char c=selectedWord.charAt(r);
                guesses.put( lsStringUsefullFunctions.StringAt(selectedWord,r),true);
                for(int i=0;i<selectedWord.length();i++){
                    char sel=selectedWord.charAt(i);
                    logger.info(fName+"selectedWord["+i+"]="+sel);
                    if(sel==c){
                        tmp.append(c);
                    }else{
                        tmp.append("?");
                    }
                }
                logger.info(fName+"filledoutWord="+tmp.toString());
                filledoutWord=tmp.toString();
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        String selectedWord="",filledoutWord="";
        int  misses=0,turns=0;boolean finished=false;
        JSONObject guesses=new JSONObject();
        public boolean getValues() {
            String fName="[getValues]";
            try {
                if(gGuildProfile==null||!gGuildProfile.isProfile()){
                    logger.info(fName+"no guild profile>false");
                    return false;
                }
                logger.info(fName+"gGuildProfile.json="+gGuildProfile.jsonObject.toString());
                guesses=gGuildProfile.getFieldEntryAsJSONObject(keyGuesses);
                misses=gGuildProfile.getFieldEntryAsInt(keyMisses);
                turns=gGuildProfile.getFieldEntryAsInt(keyTurns);
                finished=gGuildProfile.getFieldEntryAsBoolean(keyFinished);
                selectedWord=gGuildProfile.optFieldEntryAsString(keySelectedWord);
                filledoutWord=gGuildProfile.optFieldEntryAsString(keyFilledOutWord);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean saveValues() {
            String fName="[saveValues]";
            try {
                gGuildProfile.putFieldEntry(keyGuesses,guesses);
                gGuildProfile.putFieldEntry(keySelectedWord,selectedWord);
                gGuildProfile.putFieldEntry(keyFilledOutWord,filledoutWord);
                gGuildProfile.putFieldEntry(keyMisses,misses);
                gGuildProfile.putFieldEntry(keyTurns,turns);
                gGuildProfile.putFieldEntry(keyFinished,finished);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
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
                if(gGuildProfile==null||!gGuildProfile.isProfile()){
                    logger.info(fName+"no guild profile>false");
                    return false;
                }
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


        lcJSONGuildProfile gGuildProfile=new lcJSONGuildProfile(null);
        public boolean getGuildProfile(){
            String fName="[getGuildPProfile]";
            logger.info(fName+".safety check");
            try{
                if(gGuildProfile ==null||!gGuildProfile.isProfile()){
                    logger.info(fName + ".get");
                    gGuildProfile =gGlobal.getGuildSettings(gGuild, profileName);
                    if(gGuildProfile ==null||!gGuildProfile.isExistent()|| gGuildProfile.jsonObject.isEmpty()){
                        gGuildProfile =new lcJSONGuildProfile(gGlobal, profileName,gGuild,llv2_GuildsSettings);
                        gGuildProfile.getProfile(llv2_GuildsSettings);
                        if(!gGuildProfile.jsonObject.isEmpty()){
                            gGlobal.putGuildSettings(gGuild, gGuildProfile);
                        }
                    }
                    iGuildPSafety();
                    if(gGuildProfile.isUpdated){
                        gGlobal.putGuildSettings(gGuild, gGuildProfile);
                        if(isAutoSave){
                            if(gGuildProfile.saveProfile()){
                                logger.info(fName + ".success save to db>true");
                                return true;
                            }else{
                                logger.error(fName + ".error save to db");
                            }
                        }
                    }
                    logger.info(fName + ".still got but failed to save>true");
                    return true;
                }else{
                    logger.info(fName + ".skip>true");
                    return true;
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        String keyPlayers="players",keySelectedWord="selectedWord",keyFilledOutWord="filledOutWord",keyGuesses="guesses",keyMisses="misses";
        public void iGuildPSafety(){
            String fName="iSafety";
            try {

                gGuildProfile.safetyPutFieldEntry(keySelectedWord, "");
                gGuildProfile.safetyPutFieldEntry(keyFilledOutWord, "");
                gGuildProfile.safetyPutFieldEntry(keyGuesses,  new JSONObject());
                gGuildProfile.safetyPutFieldEntry(keyPlayers, new JSONArray());
                gGuildProfile.safetyPutFieldEntry(keyFinished, false);
                gGuildProfile.safetyPutFieldEntry(keyTurns, 0);
                gGuildProfile.safetyPutFieldEntry(keyMisses, 0);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        boolean isAutoSave =true;
        boolean saveGuild(){
            String fName="[saveGuildP]";
            logger.info(fName);
            try{
                gGlobal.putGuildSettings(gGuild, gGuildProfile);
                if(gGuildProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        String profileName="HangMan",table="MemberProfile";
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBasicFeatureControl.setEnable(enable);
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTitle(gTitle+" Options");
                embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBasicFeatureControl.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    help("main");return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }

                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName+"timeout");
                            lsMessageHelper.lsMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            }
        }
    }
    public enum HANGMANASCII {
        INVALID(-1,""),
        Blank(0,"`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `"),
        Platform(1,"`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`                        `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Pole(2,"`                        `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Extension(3,"`        +------------+  `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Nosse(4,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Head(5,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Body(6,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |            |  `\n" +
                "`        |            |  `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Arm1(7,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |           /|  `\n" +
                "`        |            |  `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Arm2(8,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |           /|\\ `\n" +
                "`        |            |  `\n" +
                "`        |               `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Leg1(9,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |           /|\\ `\n" +
                "`        |            |  `\n" +
                "`        |           /   `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `"),
        Full(10,"`        +------------+  `\n" +
                "`        |            |  `\n" +
                "`        |            +  `\n" +
                "`        |            0  `\n" +
                "`        |           /|\\ `\n" +
                "`        |            |  `\n" +
                "`        |           / \\ `\n" +
                "`    XXXXXXXXXX          `\n" +
                "`  XX          XX        `\n" +
                "` XX            XX       `\n" +
                "`XX              XX      `\n" +
                "`X                X      `");

        private String text;
        private int code;
        private HANGMANASCII(int code, String text) {
            this.code = code;
            this.text = text;
        }
        public static HANGMANASCII valueByCode(int code) {
            HANGMANASCII[] var1 = values();
            int var2 = var1.length;
            for(int var3 = 0; var3 < var2; ++var3) {
                HANGMANASCII status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public String getText() {
            return this.text;
        }
        static {
            HANGMANASCII[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                HANGMANASCII s = var0[var2];
            }

        }
        public static String getText(HANGMANASCII gag){
            String fName="[getText]";
            Logger logger = Logger.getLogger(HANGMANASCII.class);
            try {
                return gag.getText();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
}
