//revising doPreviews for gifts

package forRemoval.holidays.outdated;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.json.lcText2Json;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;
import search.e621;
import search.furaffinity;
import search.inkbunny;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class NaughtyGifts extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gTitle="Naughty Gifts_old",gCommand="naughtygifts_old";
    public NaughtyGifts(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Collecting 'gifts'. Small fun command for the holiday season.";
        this.aliases = new String[]{"gift_old",gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;this.hidden=true;

    }
    public NaughtyGifts(lcGlobalHelper global, lcSlashInteractionReceive event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);new Thread(r).start();

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
    protected class runLocal implements Runnable {
        CommandEvent gEvent;GuildMessageReceivedEvent gGuildMessageReceivedEvent;
        lcSlashInteractionReceive gInteractionCreate;
        User gUser;
        Member gMember;
        Guild gGuild;
        TextChannel gTextChannel;
        Member gTarget;
        boolean gIsOverride=false;

        public runLocal(CommandEvent ev) {
            String fName="build";logger.info(".run build");
            gEvent = ev;
            gUser = gEvent.getAuthor();gMember=gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(GuildMessageReceivedEvent event) {
            String fName="build";logger.info(".run build");
            gGuildMessageReceivedEvent = event;
            gUser = gGuildMessageReceivedEvent.getAuthor();gMember=gGuildMessageReceivedEvent.getMember();
            gGuild = gGuildMessageReceivedEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gGuildMessageReceivedEvent.getChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        public runLocal(lcSlashInteractionReceive ev) {
            String fName="runLoccal";
            logger.info(cName + ".run build");
            gInteractionCreate = ev;
            gUser = ev.getUser();gMember=ev.getMember();
            gGuild = ev.getGuild();
            gTextChannel =ev.getTextChannel();
            logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            String[] items;
            boolean isInvalidCommand = true;
            try{
                gBBDSMCommands=new lcBDSMGuildProfiles(gGlobal,gGuild);
                if(gInteractionCreate!=null){
                    logger.info(cName+fName+"slash@");
                    if(gInteractionCreate.getName().equalsIgnoreCase("gift")||gInteractionCreate.getName().equalsIgnoreCase("gifts")){
                        if(!isNSFW()){
                            return;
                        }
                        String optionName=gInteractionCreate.getOptionPrime().getString(keyName);
                        if(optionName.contains("open")||optionName.equalsIgnoreCase("open")){
                            open();
                        }
                        else if(optionName.contains("receive")||optionName.equalsIgnoreCase("receive")){
                            doSelection("hosts");
                        }
                        else if(optionName.contains("items")||optionName.equalsIgnoreCase("items")){
                            brows();
                        }
                    }
                }else
                if(gGuildMessageReceivedEvent!=null){
                    String args=gGuildMessageReceivedEvent.getMessage().getContentRaw().replaceFirst(llPrefixStr,"");
                    args=args.trim();
                    logger.info(fName+".args="+args);
                    items = args.split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);

                }else{
                    logger.info(cName+fName+"basic@");
                    if(gEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        help("main");
                        gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                        isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        items = gEvent.getArgs().split("\\s+");
                        if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                        logger.info(fName + ".items.size=" + items.length);
                        logger.info(fName + ".items[0]=" + items[0]);
                        if(isTargeted(items[0])){
                            if(!isNSFW()){
                                blocked();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("status")){
                                status();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("items")){
                                brows();isInvalidCommand=false;
                            }
                        }else{
                            if(items[0].equalsIgnoreCase("help")){
                                help("main");isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("debug")){
                                debugUserPrint();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("resettoys")||items[0].equalsIgnoreCase("resettoy")){
                                resetUserToys();isInvalidCommand=false;
                            }
                            else if(!isNSFW()){
                                blocked();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("receive")){
                                doSelection("hosts");isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("status")){
                                status();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("open")){
                                open();isInvalidCommand=false;
                            }
                            else if(items[0].equalsIgnoreCase("items")){
                                brows();isInvalidCommand=false;
                            }
                        }

                    }
                    logger.info(fName+".deleting op message");
                    lsMessageHelper.lsMessageDelete(gEvent);
                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }
            catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
            logger.info(".run ended");
        }
        private boolean isNSFW(){
            String fName = "[isNSFW]";
            logger.info(fName);
            if(gTextChannel.isNSFW()){
                return true;
            }
            return lsGuildHelper.lsIsGuildNSFW(gGlobal, gGuild);
        }
        private void blocked(){
            String fName = "[blocked]";
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel or server.",llColorRed);
            logger.info(fName);
        }
        private boolean isTargeted(String command){
            String fName = "[isTargeted]";
            logger.info(fName);
            try{
                logger.info(fName + ".command=" + command);
                if((command.contains("<@")&&command.contains(">"))||(command.contains("<@!")&&command.contains(">"))){
                    String tmp=command.replace("<@!","").replace("<@","").replace(">","");
                    Member m=gGuild.getMemberById(tmp);
                    if(m!=null){
                        if(m.getId().equals(gUser.getId())){
                            logger.info(fName + ".target same");
                            return false;
                        }
                        logger.info(fName + ".target ok");
                        gTarget=m;
                        return true;
                    }
                }
                logger.info(fName + ".target none");
                return false;
            }
            catch(Exception ex){
                logger.error(fName + ".exception: "+ex);
                return false;
            }
        }

        private void help( String command) {
            String fName = "[help]";
            logger.info(fName);
            logger.info(fName + "command=" + command);
            String desc;
            String quickSummonWithSpace=llPrefixStr+"gift ";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);
            desc="`"+quickSummonWithSpace+"receive` to receive a gift.";
            desc+="\n`"+quickSummonWithSpace+"open` to open a gift.";
            desc+="\n`"+quickSummonWithSpace+"items <@User>` to view the items received from opened gifts.";
            desc+="\n`"+quickSummonWithSpace+"status <@User>` to view status.";
            desc+="\n  `<@User>`is an optional value used to refer the command to the mentioned member than the user who sends the command";
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void status( ) {
            String fName = "[status]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                if(!getProfile(gTarget.getUser())){
                    logger.warn(fName + "no profile");
                    return;
                }
            }else{
                if(!getProfile(gUser)){
                    logger.warn(fName + "no profile");
                    return;
                }
            }
            embed.setColor(llColorBlue1);embed.setTitle(gUserProfile.getUser().getName()+" "+gTitle);

            embed.addField("Unopened Gifts", String.valueOf(gUserProfile.jsonObject.getJSONArray(keyUnopenedGifts).length()),false);
            embed.addField("Total Received Gifts", String.valueOf(gUserProfile.jsonObject.getJSONArray(keyGifts).length()),false);
            embed.addField("Items", String.valueOf(gUserProfile.jsonObject.getJSONArray(keyToys).length()),false);
            llSendMessage(gTextChannel,embed);
        }
        private void debugUserPrint( ) {
            String fName = "[debugUserPrint]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                if(!getProfile(gTarget.getUser())){
                    logger.warn(fName + "no profile");
                    return;
                }
            }else{
                if(!getProfile(gUser)){
                    logger.warn(fName + "no profile");
                    return;
                }
            }
            logger.info(fName+"json="+gUserProfile.jsonObject.toString());
            embed.setColor(llColorBlue1);embed.setTitle(gUser.getName()+" "+gTitle);
            embed.setDescription("check logs");llSendMessage(gTextChannel,embed);

        }
        private void resetUserToys( ) {
            String fName = "[resetUserToys]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                if(!getProfile(gTarget.getUser())){
                    logger.warn(fName + "no profile");
                    return;
                }
            }else{
                if(!getProfile(gUser)){
                    logger.warn(fName + "no profile");
                    return;
                }
            }
            gUserProfile.jsonObject.put(keyToys,new JSONArray());
            saveProfile();
            logger.info(fName+"json="+gUserProfile.jsonObject.toString());
            embed.setColor(llColorBlue1);embed.setTitle(gUser.getName()+" "+gTitle);
            embed.setDescription("check logs");llSendMessage(gTextChannel,embed);

        }
        private void open( ) {
            String fName = "[open]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            if(!getProfile(gUser)){
                logger.warn(fName + "no profile");
                return;
            }
            embed.setColor(llColorBlue1);embed.setTitle(gUser.getName()+" "+gTitle);
            int unopenedGiftsCount=gUserProfile.jsonObject.getJSONArray(keyUnopenedGifts).length();
            logger.info(fName+"unopenedGiftsCount="+unopenedGiftsCount);
            if(unopenedGiftsCount>0){
                //embed.setDescription("Don't you think its too early to open any of your received gifts!");
                openGift();
            }else{
                embed.setDescription("You have 0 gifts.");llSendMessage(gTextChannel,embed);
            }

        }
        private void brows() {
            String fName="[brows]";
            logger.info(fName);
            try {
                getProfile(gUser);
                JSONArray toys=gUserProfile.jsonObject.getJSONArray(keyToys);
                logger.info(fName+"toys.size="+toys.length());
                if(toys.isEmpty()){
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setColor(getRandomColor());
                    embedBuilder.setAuthor(gUser.getName(),null,gUser.getEffectiveAvatarUrl().replaceAll(".gif",".png"));
                    embedBuilder.setDescription("You have no toys");
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                getToysList();
                browsToys(null,0);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String gUrlMainSoloPath="resources/json/nsfw/naughtygifts";
        lcText2Json text2Json=null;
        String keyType="type",keyE621="e621",keyFA="fa",keyText="text",keyInkbunny="ibunny";
        String gUrlE621GetPost ="https://e621.net/posts/%.json",gUrlE621Post ="https://e621.net/posts/", gUrlFAPost="https://www.furaffinity.net/view/%/";
        String keyUrl ="url",keyGift="gift", keyToy="toy",keyRestrains="restraints";
        JSONObject jsonObjectSelection;
        String vUnopen="unopen",vOpen="open";
        String keyJFile="jFile",keyJIndex="jIndex";
        private void doSelection(String name) {
            String fName="[doSelection]";
            logger.info(fName);
            try {
                logger.info(fName+"name="+name);
                if(!readFile(name)){
                    logger.warn(fName+".failed to load");
                    return;
                }
                int random= lsUsefullFunctions.getRandom(text2Json.jsonArray.length());
                logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                int count=0;
                while((random<0||text2Json.jsonArray.length()<=random)&&count<50){
                    random=lsUsefullFunctions.getRandom(text2Json.jsonArray.length());count++;
                    logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                }
                if(random<0||text2Json.jsonArray.length()<=random){
                    logger.warn(fName+".invalid random");
                    return;
                }
                jsonObjectSelection=text2Json.jsonArray.getJSONObject(random);
                jsonObjectSelection.put(keyJFile,name);
                jsonObjectSelection.put(keyJIndex,random);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                if(!jsonObjectSelection.has(keyText)){
                    logger.warn(fName+".keyText is missing");return;
                }
                if(jsonObjectSelection.isNull(keyText)){
                    logger.warn(fName+".keyText is null");return;
                }
                getProfile(gUser);
                if(jsonObjectSelection.has(keyE621)&&!jsonObjectSelection.isNull(keyE621)&&!jsonObjectSelection.getString(keyE621).isBlank()){
                    logger.info(fName+".keyE621 found");
                    doPreviewE621(jsonObjectSelection.getString(keyE621));

                }
                else if(jsonObjectSelection.has(keyFA)&&!jsonObjectSelection.isNull(keyFA)&&!jsonObjectSelection.getString(keyFA).isBlank()){
                    logger.info(fName+".keyFA found");
                    doPreviewFA(jsonObjectSelection.getString(keyFA));
                }
                else if(jsonObjectSelection.has(keyInkbunny)&&!jsonObjectSelection.isNull(keyInkbunny)&&!jsonObjectSelection.getString(keyInkbunny).isBlank()){
                    logger.info(fName+".keyInkBunny found");
                    doPreviewInkbunny(jsonObjectSelection.getString(keyInkbunny));
                }
                else if(jsonObjectSelection.has(keyUrl)&&!jsonObjectSelection.isNull(keyUrl)&&!jsonObjectSelection.getString(keyUrl).isBlank()){
                    logger.info(fName+".keyUrl found");
                    doURLPreview(jsonObjectSelection.getString(keyUrl));
                }
                else{
                    logger.warn(fName+"keys missing or null");
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void receiveGift() {
            String fName="[receiveGift]";
            logger.info(fName);
            try {
                boolean isUpdated=false;
                if(jsonObjectSelection.has(keyGift)){
                    String type=jsonObjectSelection.getString(keyType);
                    logger.info(fName+"type="+type);
                    String gift=jsonObjectSelection.getString(keyGift);
                    logger.info(fName+"gift="+gift);
                    if(type.equalsIgnoreCase(vUnopen)){
                        logger.info(fName+"received unopened");
                        JSONObject jsonGift;
                        logger.info(fName+"create unopened gift");
                        jsonGift=new JSONObject();
                        jsonGift.put(keyGift,gift);
                        if(jsonObjectSelection.has(keyE621)&&!jsonObjectSelection.isNull(keyE621)&&!jsonObjectSelection.getString(keyE621).isBlank()){
                            logger.info(fName+".keyE621 found");
                            jsonGift.put(keyE621,jsonObjectSelection.getString(keyE621));
                        }
                        else if(jsonObjectSelection.has(keyFA)&&!jsonObjectSelection.isNull(keyFA)&&!jsonObjectSelection.getString(keyFA).isBlank()){
                            logger.info(fName+".keyFA found");
                            jsonGift.put(keyFA,jsonObjectSelection.getString(keyFA));
                        }
                        else if(jsonObjectSelection.has(keyInkbunny)&&!jsonObjectSelection.isNull(keyInkbunny)&&!jsonObjectSelection.getString(keyInkbunny).isBlank()){
                            logger.info(fName+".keyInkBunny found");
                            jsonGift.put(keyInkbunny,jsonObjectSelection.getString(keyInkbunny));
                        }
                        logger.info(fName+"jsonGift="+jsonGift.toString());
                        gUserProfile.jsonObject.getJSONArray(keyUnopenedGifts).put(jsonGift);
                        gUserProfile.jsonObject.getJSONArray(keyGifts).put(jsonGift);
                        isUpdated=true;
                    }
                    if(type.equalsIgnoreCase(vOpen)){
                        logger.info(fName+"received opened");
                        JSONObject jsonGift;
                        logger.info(fName+"create toy");
                        jsonGift=new JSONObject();
                        jsonGift.put(keyToy,gift);
                        if(jsonObjectSelection.has(keyE621)&&!jsonObjectSelection.isNull(keyE621)&&!jsonObjectSelection.getString(keyE621).isBlank()){
                            logger.info(fName+".keyE621 found");
                            jsonGift.put(keyE621,jsonObjectSelection.getString(keyE621));
                        }
                        else if(jsonObjectSelection.has(keyFA)&&!jsonObjectSelection.isNull(keyFA)&&!jsonObjectSelection.getString(keyFA).isBlank()){
                            logger.info(fName+".keyFA found");
                            jsonGift.put(keyFA,jsonObjectSelection.getString(keyFA));
                        }
                        else if(jsonObjectSelection.has(keyInkbunny)&&!jsonObjectSelection.isNull(keyInkbunny)&&!jsonObjectSelection.getString(keyInkbunny).isBlank()){
                            logger.info(fName+".keyInkBunny found");
                            jsonGift.put(keyInkbunny,jsonObjectSelection.getString(keyInkbunny));
                        }
                        logger.info(fName+"jsonGift="+jsonGift.toString());
                        gUserProfile.jsonObject.getJSONArray(keyToys).put(jsonGift);
                        gUserProfile.jsonObject.getJSONArray(keyGifts).put(jsonGift);
                        isUpdated=true;
                    }
                }
                if(jsonObjectSelection.has(keyGifts)){
                    String type=jsonObjectSelection.getString(keyType);
                    logger.info(fName+"type="+type);
                    JSONArray gifts=jsonObjectSelection.getJSONArray(keyGifts);
                    logger.info(fName+"gifts="+gifts.toString());
                    if(type.equalsIgnoreCase(vUnopen)){
                        logger.info(fName+"received unopened");
                        for(int i=0;i<gifts.length();i++){
                            try {
                                String gift=gifts.getString(i);
                                logger.info(fName+"gift["+i+"]="+gift);
                                JSONObject jsonGift;
                                logger.info(fName+"create unopened gift");
                                jsonGift=new JSONObject();
                                jsonGift.put(keyGift,gift);
                                logger.info(fName+"jsonGift="+jsonGift.toString());
                                gUserProfile.jsonObject.getJSONArray(keyUnopenedGifts).put(jsonGift);
                                gUserProfile.jsonObject.getJSONArray(keyGifts).put(jsonGift);
                                isUpdated=true;
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                    if(type.equalsIgnoreCase(vOpen)){
                        logger.info(fName+"received opened");
                        for(int i=0;i<gifts.length();i++){
                            try {
                                String gift=gifts.getString(i);
                                logger.info(fName+"gift["+i+"]="+gift);
                                JSONObject jsonGift;
                                logger.info(fName+"create toy");
                                jsonGift=new JSONObject();
                                jsonGift.put(keyToy,gift);
                                logger.info(fName+"jsonGift="+jsonGift.toString());
                                gUserProfile.jsonObject.getJSONArray(keyToys).put(jsonGift);
                                gUserProfile.jsonObject.getJSONArray(keyGifts).put(jsonGift);
                                isUpdated=true;
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }
                }
                logger.info(fName+"isUpdated="+isUpdated);
                if(isUpdated){
                    if(jsonObjectSelection.has(keyRestrains)){
                        try {
                            JSONArray restraints=jsonObjectSelection.getJSONArray(keyRestrains);
                            logger.info(fName+"restraints="+restraints.toString());
                            for(int i=0;i<restraints.length();i++){
                                try {
                                    executeRestraints(restraints.getJSONObject(i));
                                }catch (Exception e){
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    if(jsonObjectSelection.has(keyRestraint)){
                        try {
                            executeRestraints(jsonObjectSelection.getJSONObject(keyRestraint));
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    logger.info(fName+"json="+gUserProfile.jsonObject.toString());
                    saveProfile();
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void openGift() {
            String fName="[openGift]";
            logger.info(fName);
            try {
                //getProfile(gUser);
                JSONArray gifts=gUserProfile.jsonObject.getJSONArray(keyUnopenedGifts);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(getRandomColor());
                embedBuilder.setAuthor(gUser.getName(),null,gUser.getEffectiveAvatarUrl().replaceAll(".gif",".png"));
                logger.info(fName+"gifts.size="+gifts.length());
                if(gifts.isEmpty()){
                  embedBuilder.setDescription("You have no unopened gifts");
                  lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                  return;
                }
                JSONObject gift=gifts.getJSONObject(0);
                gifts.remove(0);//saveProfile();
                String name=gift.getString(keyGift);
                logger.info(fName+"gift.name="+name);
                if(!readFile(name)){
                    logger.warn(fName+".failed to load");
                    return;
                }
                int random= lsUsefullFunctions.getRandom(text2Json.jsonArray.length());
                logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                int count=0;
                while((random<0||text2Json.jsonArray.length()<=random)&&count<50){
                    random=lsUsefullFunctions.getRandom(text2Json.jsonArray.length());count++;
                    logger.info(fName+"random="+random+" from "+text2Json.jsonArray.length());
                }
                if(random<0||text2Json.jsonArray.length()<=random){
                    logger.warn(fName+".invalid random");
                    return;
                }
                jsonObjectSelection=text2Json.jsonArray.getJSONObject(random);
                if(!jsonObjectSelection.has(keyText)){
                    logger.warn(fName+".keyText is missing");return;
                }
                if(jsonObjectSelection.isNull(keyText)){
                    logger.warn(fName+".keyText is null");return;
                }
                jsonObjectSelection.put(keyJFile,name);
                jsonObjectSelection.put(keyJIndex,random);
                if(jsonObjectSelection.has(keyE621)&&!jsonObjectSelection.isNull(keyE621)&&!jsonObjectSelection.getString(keyE621).isBlank()){
                    logger.info(fName+".keyE621 found");
                    doPreviewE621(jsonObjectSelection.getString(keyE621));
                }
                else if(jsonObjectSelection.has(keyFA)&&!jsonObjectSelection.isNull(keyFA)&&!jsonObjectSelection.getString(keyFA).isBlank()){
                    logger.info(fName+".keyFA found");
                    doPreviewFA(jsonObjectSelection.getString(keyFA));
                }
                else if(jsonObjectSelection.has(keyInkbunny)&&!jsonObjectSelection.isNull(keyInkbunny)&&!jsonObjectSelection.getString(keyInkbunny).isBlank()){
                    logger.info(fName+".keyInkBunny found");
                    doPreviewInkbunny(jsonObjectSelection.getString(keyInkbunny));
                }
                else if(jsonObjectSelection.has(keyUrl)&&!jsonObjectSelection.isNull(keyUrl)&&!jsonObjectSelection.getString(keyUrl).isBlank()){
                    logger.info(fName+".keyUrl found");
                    doURLPreview(jsonObjectSelection.getString(keyUrl));
                }
                else{
                    logger.warn(fName+"keys missing or null");
                }
                JSONObject toy=new JSONObject();
                toy.put(keyJFile,name);
                toy.put(keyJIndex,random);
                if(jsonObjectSelection.has(keyToy)){
                    toy.put(keyToy,jsonObjectSelection.getString(keyToy));
                }
                gUserProfile.jsonObject.getJSONArray(keyToys).put(toy);
                saveProfile();
                if(jsonObjectSelection.has(keyRestrains)){
                    try {
                        getRestraintsProfile(gUser);
                        JSONArray restraints=jsonObjectSelection.getJSONArray(keyRestrains);
                        logger.info(fName+"restraints="+restraints.toString());
                        for(int i=0;i<restraints.length();i++){
                            try {
                                executeRestraints(restraints.getJSONObject(i));
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        saveRestraintsProfile();
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(jsonObjectSelection.has(keyRestraint)){
                    try {
                        getRestraintsProfile(gUser);
                        executeRestraints(jsonObjectSelection.getJSONObject(keyRestraint));
                        saveRestraintsProfile();
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String keyName="name",keyDesc="desc";
        private void browsToys(Message message, int index) {
            String fName="[browsToys]";
            logger.info(fName);
            try {
                logger.info(fName+"index="+index);
                //JSONArray toys=gUserProfile.jsonUser.getJSONArray(keyToys);
                JSONArray toys=toysListed;
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(getRandomColor());
                embedBuilder.setAuthor(gUser.getName(),null,gUser.getEffectiveAvatarUrl().replaceAll(".gif",".png"));
                logger.info(fName+"toys.size="+toys.length());
                if(toys.isEmpty()){
                    embedBuilder.setDescription("You have no toys");
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                if(index<0){
                    index=0;
                }
                else if(index>=toys.length()){
                    index=toys.length()-1;
                }
                JSONObject toy=toys.getJSONObject(index);
                JSONObject toyDesc=getToy(toy.getString(keyJFile),toy.getInt(keyJIndex));
                String desc="";
                assert toyDesc != null;
                if(toyDesc.has(keyName)){
                    desc+="**Name:** "+toyDesc.getString(keyName);
                }else{
                    desc+="**Name:** (unknown)";
                }
                if(toyDesc.has(keyType)){
                    desc+="\n**Type:** "+toyDesc.getString(keyType);
                }else{
                    desc+="**Type:** (unknown)";
                }
                if(toyDesc.has(keyDesc)){
                    desc+="\n"+toyDesc.getString(keyDesc);
                }
                String imgSource="";
                if(toyDesc.has(keyE621)&&!toyDesc.isNull(keyE621)&&!toyDesc.getString(keyE621).isBlank()){
                    logger.info(fName+".keyE621 found");
                    JSONObject imageJson=e621.getImageJSON(toyDesc.getString(keyE621));
                    assert imageJson != null;
                    if(imageJson.isEmpty()||!imageJson.has("post")||imageJson.isNull("post")){
                        logger.error(fName+".no post");
                    }
                    JSONObject post=imageJson.getJSONObject("post");
                    String ext="",imgUrl="",rating="";
                    JSONArray tags_general=new JSONArray();
                    boolean isCompatible=false,isBanWordsInTags=false;
                    if(post.has(keyE621Rating)&&!post.isNull(keyE621Rating)){
                        rating=post.getString(keyE621Rating);//rating can be:s,q,e
                    }
                    if(post.has(keyE621File)&&!post.isNull(keyE621File)&&post.getJSONObject(keyE621File).has(keyE621Url)&&!post.getJSONObject(keyE621File).isNull(keyE621Url)){
                        if(post.getJSONObject(keyE621File).has(keyE621Ext)&&!post.getJSONObject(keyE621File).isNull(keyE621Ext)){
                            ext=post.getJSONObject(keyE621File).getString(keyE621Ext);
                            if(ext.contains("webm")||ext.contains("swf"))isCompatible=false;
                            if(ext.contains("png")||ext.contains("jpg")||ext.contains("gif"))isCompatible=true;
                            imgUrl=post.getJSONObject(keyE621File).getString(keyE621Url);
                        }
                        logger.info(fName+".imgUrl="+imgUrl);
                    }
                    if(post.has(keyE621Tags)&&!post.isNull(keyE621Tags)){
                        if(post.getJSONObject(keyE621Tags).has(keyE621General)&&!post.getJSONObject(keyE621Tags).isNull(keyE621General)) {
                            tags_general = post.getJSONObject(keyE621Tags).getJSONArray(keyE621General);
                        }
                    }
                    for(int i=0;i<tags_general.length();i++){
                        String tag=tags_general.getString(i);
                        if(tag.toLowerCase().contains("cub")||tag.toLowerCase().contains("kid")||tag.toLowerCase().contains("child")||tag.toLowerCase().contains("baby")){
                            logger.warn(fName+"contains ban words");
                            isBanWordsInTags=true;
                        }
                    }
                    logger.info(fName+".isCompatible="+isCompatible+", isBanWordsInTags="+isBanWordsInTags+", rating="+rating+", is ChannelNSFW="+gTextChannel.isNSFW());
                    if(rating!=null&&isCompatible&&!isBanWordsInTags){
                        if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&gTextChannel.isNSFW()){
                            logger.info(fName+".nsfw channel");
                            embedBuilder.setImage(imgUrl);
                            embedBuilder.setColor(getRandomColor());
                            imgSource=lsUsefullFunctions.getUrlTextString("img_source",gUrlE621Post+toyDesc.getString(keyE621));
                        }else
                        if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&!gTextChannel.isNSFW()){
                            logger.warn(fName+".not nsfw channel");
                            embedBuilder.setColor(llColorOrange_InternationalEngineering);
                        }else if(rating.equalsIgnoreCase("s")){
                            logger.info(fName+".sfw content");
                            embedBuilder.setImage(imgUrl);
                            embedBuilder.setColor(getRandomColor());
                            imgSource=lsUsefullFunctions.getUrlTextString("img_source",gUrlE621Post+toyDesc.getString(keyE621));
                        }else{
                            embedBuilder.setColor(llColorOrange_InternationalEngineering);
                        }
                    }else{
                        logger.warn(fName + ".No image found");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }
                }
                else if(toyDesc.has(keyFA)&&!toyDesc.isNull(keyFA)&&!toyDesc.getString(keyFA).isBlank()){
                    logger.info(fName+".keyFA found");
                    JSONObject imageJson=furaffinity.getImageJSON(gGlobal,toyDesc.getString(keyFA));
                    String image="",rating="",title="",author="",authorAvatar="";
                    if(imageJson.has(keyAuthor)&&!imageJson.isNull(keyAuthor))author=imageJson.getString(keyAuthor);
                    if(imageJson.has(keyImageUrl)&&!imageJson.isNull(keyImageUrl))image=imageJson.getString(keyImageUrl);
                    if(imageJson.has(keyAvatar)&&!imageJson.isNull(keyAvatar))authorAvatar=imageJson.getString(keyAvatar);
                    if(imageJson.has(keyTitle)&&!imageJson.isNull(keyTitle))title=imageJson.getString(keyTitle);
                    if(imageJson.has(keyRating)&&!imageJson.isNull(keyRating))rating=imageJson.getString(keyRating);
                    if(!image.isBlank()){
                        logger.info(fName+".image ="+image);
                        if(rating.isBlank()){
                            logger.warn(fName+"no rating info"); embedBuilder.setColor(llColorRed_Imperial);
                        }
                        else if(rating.equalsIgnoreCase("general")||gTextChannel.isNSFW()){
                            logger.info(fName+".author ="+author);
                            logger.info(fName+".rating ="+rating);
                            embedBuilder.setImage(image); embedBuilder.setColor(getRandomColor());
                            imgSource=lsUsefullFunctions.getUrlTextString("img_source",gUrlFAPost.replaceAll("%",toyDesc.getString(keyFA)));
                        }else{
                            logger.warn(fName+"channel not nsfw"); embedBuilder.setColor(llColorRed_Imperial);
                        }
                    }else{
                        embedBuilder.setColor(llColorRed_Cinnabar);
                    }
                }
                else if(toyDesc.has(keyInkbunny)&&!toyDesc.isNull(keyInkbunny)&&!toyDesc.getString(keyInkbunny).isBlank()){
                    logger.info(fName+".keyInkBunny found");

                    JSONObject imageJson=inkbunny.getImageJSONy(gGlobal,toyDesc.getString(keyInkbunny));
                    if(imageJson.isEmpty()||!imageJson.has("submissions")||imageJson.isNull("submissions")){
                        logger.error(fName+".no submissions");return;
                    }
                    JSONArray submissions=imageJson.getJSONArray("submissions");
                    if(submissions.isEmpty()||submissions.length()<0){
                        logger.error(fName+".no post");return;
                    }
                    JSONObject post=submissions.getJSONObject(0);
                    logger.info(fName+".post="+post.toString());
                    boolean isPublic=false,isGuestBlock=false,isHidden=false,isImage=false, isBadKeywords=false;
                    int ratingId=-1;
                    String postId="",title="",authorName="",authorId="",authorAvatar="", image="";
                    StringBuilder ratings= new StringBuilder();
                    if(post.has(keyIBPublic)&&!post.isNull(keyIBPublic)&&post.getString(keyIBPublic).equals(valueTrue)){
                        isPublic=true;
                    }
                    if(post.has(keyIBGuestBlock)&&!post.isNull(keyIBGuestBlock)&&post.getString(keyIBGuestBlock).equals(valueTrue)){
                        isGuestBlock=true;
                    }
                    if(post.has(keyIBHidden)&&!post.isNull(keyIBHidden)&&post.getString(keyIBHidden).equals(valueTrue)){
                        isHidden=true;
                    }
                    if(post.has(keyIBMimeType)&&!post.isNull(keyIBMimeType)){
                        if(listmimetype.has(post.getString(keyIBMimeType))&&listmimetype.getBoolean(post.getString(keyIBMimeType))){
                            isImage=true;
                        }
                    }
                    logger.info(fName+".isPublic="+isPublic+", isHidden="+isHidden+", isImage="+isImage);
                    if(!isPublic){
                        logger.warn(fName + ".not public");return;
                    }
                    if(isHidden){
                        logger.warn(fName + ".is hidden");return;
                    }
                    if(!isImage){
                        logger.warn(fName + ".not image");return;
                    }
                    logger.info(fName+".isGuestBlock="+isGuestBlock);
                    if(post.has(keyIBUserId)&&!post.isNull(keyIBUserId)){
                        authorId=post.getString(keyIBUserId);
                    }
                    if(post.has(keyIBUserName)&&!post.isNull(keyIBUserName)){
                        authorName=post.getString(keyIBUserName);
                    }
                    if(post.has(keyIBUserIconUrlLarge)&&!post.isNull(keyIBUserIconUrlLarge)) {
                        authorAvatar = post.getString(keyIBUserIconUrlLarge);
                    }
                    logger.info(fName+".authorId="+authorId+", authorName="+authorName+", authorAvatar="+authorAvatar);
                    if(post.has(keyIBSubmissionId)&&!post.isNull(keyIBSubmissionId)) {
                        postId = post.getString(keyIBSubmissionId);
                    }
                    if(post.has(keyIBTitle)&&!post.isNull(keyIBTitle)) {
                        title = post.getString(keyIBTitle);
                    }
                    logger.info(fName+". postId="+ postId+", title="+title);

                    if(post.has(keyIBRatingId)&&!post.isNull(keyIBRatingId)){
                        ratingId=post.getInt(keyIBRatingId);//0-general 1-matur 2-adult
                    }
                    if(post.has(keyIBRatingName)&&!post.isNull(keyIBRatingName)){
                        ratings = new StringBuilder(post.getString(keyIBRatingName));
                    }
                    if(post.has(keyIBRatings)&&!post.isNull(keyIBRatings)){
                        JSONArray array=post.getJSONArray(keyIBRatings);
                        for(int i=0;i<array.length();i++){
                            ratings.append(", ").append(array.getJSONObject(i).getString(keyIBName));
                        }
                    }
                    logger.info(fName+".ratingId="+ratingId+" ,ratings="+ratings);
                    if(post.has(keyIBKeywords)&&!post.isNull(keyIBKeywords)){
                        JSONArray array=post.getJSONArray(keyIBKeywords);
                        for(int i=0;i<array.length();i++){
                            try {
                                String keywordName=array.getJSONObject(i).getString(keyIBKeywordName);
                                if(keywordName.toLowerCase().contains("cub")||keywordName.toLowerCase().contains("kid")||keywordName.toLowerCase().contains("child")||keywordName.toLowerCase().contains("baby")){
                                    logger.warn(fName+"contains badword");isBadKeywords=true;
                                    break;
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                    }

                    if(post.has(keyIBFileUrlFull)&&!post.isNull(keyIBFileUrlFull)){
                        image=post.getString(keyIBFileUrlFull);
                    }else
                    if(post.has(keyIBFileUrlScreen)&&!post.isNull(keyIBFileUrlScreen)){
                        image=post.getString(keyIBFileUrlScreen);
                    }else
                    if(post.has(keyIBFileUrlPreview)&&!post.isNull(keyIBFileUrlPreview)){
                        image=post.getString(keyIBFileUrlPreview);
                    }
                    logger.info(fName+" image="+ image);
                    if(!image.isBlank()&&!image.isEmpty()&&!isBadKeywords){
                        logger.info(fName+"rating="+ratingId+", isNSFW="+gTextChannel.isNSFW());
                        if(ratingId!=0&&gTextChannel.isNSFW()){
                            logger.info(fName+".nsfw channel");
                            embedBuilder.setImage(image);
                            embedBuilder.setColor(getRandomColor());
                            imgSource=lsUsefullFunctions.getUrlTextString("img_source",gUrlInkBunnySubmissionPage.replaceAll("%",toyDesc.getString(keyInkbunny)));
                        }else
                        if(ratingId!=0&&!gTextChannel.isNSFW()){
                            logger.warn(fName+".not nsfw channel");
                            embedBuilder.setColor(llColorOrange_InternationalEngineering);
                        }else{
                            logger.info(fName+".sfw content");
                            embedBuilder.setImage(image);
                            embedBuilder.setColor(getRandomColor());
                            imgSource=lsUsefullFunctions.getUrlTextString("img_source",gUrlInkBunnySubmissionPage.replaceAll("%",toyDesc.getString(keyInkbunny)));
                        }
                    }else {
                        logger.warn(fName + ".No image found");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }
                }
                else if(toyDesc.has(keyUrl)&&!toyDesc.isNull(keyUrl)&&!toyDesc.getString(keyUrl).isBlank()){
                    logger.info(fName+".keyUrl found");
                    embedBuilder.setImage(toyDesc.getString(keyUrl));
                }
                if(!imgSource.isBlank()){
                   desc+="\n\n"+imgSource;
                }
                embedBuilder.setDescription(desc);
                if(message!=null){
                    logger.info(fName+".delete old message");
                    lsMessageHelper.lsMessageDelete(message);
                }
                Message newMessage=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                navigateOption(newMessage,index, toys.length());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void navigateOption(Message message, int index, int size) {
            String fName="[navigateOption]";
            logger.info(fName);
            try {
                try {
                    if(index>1){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTrackPrevious));
                    }
                    if(index>0){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                    }
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown));
                    if(index<size-1){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                    }
                    if(index<size-2){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTrackNext));
                    }
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("bomb"));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                logger.info(fName+"do=backward");
                                browsToys(message,index-1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                logger.info(fName+"do=forward");
                                browsToys(message,index+1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTrackPrevious))){
                                logger.info(fName+"do=first");
                                browsToys(message,0);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTrackNext))){
                                logger.info(fName+"do=last");
                                browsToys(message,size-1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){
                                logger.info(fName+"do=post");
                                lsMessageHelper.lsMessageClearReactionsQueue(message);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                logger.info(fName+"do=delete");
                                lsMessageHelper.lsMessageDelete(message);
                            }else{
                                logger.info(fName+"do=invalid");
                                navigateOption(message,index,size);
                            }
                        },10, TimeUnit.MINUTES, () -> lsMessageHelper.lsMessageClearReactionsQueue(message));
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String keyAmount="amount";
        JSONArray toysListed;
        private void getToysList() {
            String fName="[getToysList]";
            logger.info(fName);
            try {
                logger.info(fName+"name="+name);
                JSONArray toys=gUserProfile.jsonObject.getJSONArray(keyToys);
                logger.info(fName+"toys="+toys.toString());
                toysListed=new JSONArray();
                boolean found;
                for(int i=0;i<toys.length();i++){
                    try {
                        logger.info(fName+"toys["+i+"]="+toys.getJSONObject(i).toString());
                        found=false;
                        for(int j=0;j<toysListed.length();j++){
                            logger.info(fName+"toysListed["+j+"]="+toysListed.getJSONObject(j).toString());
                            if(toys.getJSONObject(i).getString(keyToy).equalsIgnoreCase(toysListed.getJSONObject(j).getString(keyToy))){
                                logger.info(fName+"found");
                                toysListed.getJSONObject(j).put(keyAmount, toysListed.getJSONObject(j).getInt(keyAmount)+1);
                                found=true;break;
                            }
                        }
                        if(!found){
                            logger.info(fName+"add to list");
                            toys.getJSONObject(i).put(keyAmount, 1);
                            toysListed.put(toys.getJSONObject(i));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+"toys="+toysListed.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private JSONObject getToy(String name, int index) {
            String fName="[getToy]";
            logger.info(fName);
            try {
                logger.info(fName+"name="+name);
                if(!readFile(name)){
                    logger.warn(fName+".failed to load");
                    return null;
                }
                logger.info(fName+"index="+index+" from "+text2Json.jsonArray.length());
                if(index<0||text2Json.jsonArray.length()<=index){
                    logger.warn(fName+".invalid index");
                    return null;
                }
                JSONObject jsonObject=text2Json.jsonArray.getJSONObject(index);
                logger.info(fName+"jsonObject="+jsonObject.toString());
                jsonObject.put(keyJFile,name);jsonObject.put(keyJIndex,index);
                return  jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        private void doURLPreview(String address) {
            String fName = "[doURLPreview]";
            try {
                logger.info(fName+".address ="+address);
                //InputStream is=lsStreamHelper.llGetInputStream4WebFile(address);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                if(address!=null&&!address.isBlank()){
                    if(gTextChannel.isNSFW()){
                        embedBuilder.setImage(address); embedBuilder.setColor(getRandomColor());
                        receiveGift();
                    }else{
                        logger.warn(fName+"channel not nsfw"); embedBuilder.setColor(llColorRed_Imperial);
                        embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                    }
                }else{
                    embedBuilder.setDescription("No image found in submission!");embedBuilder.setColor(llColorRed_Cinnabar);
                }
                //embedBuilder.addField("Important","Report it if it violates rules 2 our staff!",false);
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        String keyE621File ="file", keyE621Url ="url",keyE621Ext="ext",keyE621Rating="rating",keyE621Tags="tags",keyE621General="general";
        private void doPreviewE621(String viewId) {
            String fName="[doPreviewE621]";
            logger.info(fName);
            try {
                logger.info(fName+"viewId="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                JSONObject json= e621.getImageJSON(viewId);
                logger.info(fName+".json ="+json.toString());
                if(json.isEmpty()||!json.has("post")||json.isNull("post")){
                    logger.error(fName+".no post");return;
                }
                JSONObject post=json.getJSONObject("post");
                String id="",ext="",imgUrl="",rating="";
                JSONArray tags_general=new JSONArray(), artists=new JSONArray();
                boolean isCompatible=false,isBanWordsInTags=false;
                if(post.has(keyE621Rating)&&!post.isNull(keyE621Rating)){
                    rating=post.getString(keyE621Rating);//rating can be:s,q,e
                }
                if(post.has(keyE621File)&&!post.isNull(keyE621File)&&post.getJSONObject(keyE621File).has(keyE621Url)&&!post.getJSONObject(keyE621File).isNull(keyE621Url)){
                    if(post.getJSONObject(keyE621File).has(keyE621Ext)&&!post.getJSONObject(keyE621File).isNull(keyE621Ext)){
                        ext=post.getJSONObject(keyE621File).getString(keyE621Ext);
                        if(ext.contains("webm")||ext.contains("swf"))isCompatible=false;
                        if(ext.contains("png")||ext.contains("jpg")||ext.contains("gif"))isCompatible=true;
                        imgUrl=post.getJSONObject(keyE621File).getString(keyE621Url);
                    }
                    logger.info(fName+".imgUrl="+imgUrl);
                }
                if(post.has(keyE621Tags)&&!post.isNull(keyE621Tags)){
                    if(post.getJSONObject(keyE621Tags).has(keyE621General)&&!post.getJSONObject(keyE621Tags).isNull(keyE621General)) {
                        tags_general = post.getJSONObject(keyE621Tags).getJSONArray(keyE621General);
                    }
                }
                for(int i=0;i<tags_general.length();i++){
                    String tag=tags_general.getString(i);
                    if(tag.toLowerCase().contains("cub")||tag.toLowerCase().contains("kid")||tag.toLowerCase().contains("child")||tag.toLowerCase().contains("baby")){
                        logger.warn(fName+"contains ban words");
                        isBanWordsInTags=true;
                    }
                }
                if(post.has("id")) { id = post.getString("id"); }
                if(post.has("artist")){ artists=post.getJSONArray("artist");}

                logger.info(fName+".isCompatible="+isCompatible+", isBanWordsInTags="+isBanWordsInTags+", rating="+rating+", is ChannelNSFW="+gTextChannel.isNSFW());
                if(rating!=null&&isCompatible&&!isBanWordsInTags){
                    if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&gTextChannel.isNSFW()){
                        logger.info(fName+".nsfw channel");
                        //embedBuilder.setAuthor(id,gUrlE621Post+"viewId",gE621Icon);
                        embedBuilder.setImage(imgUrl);
                        embedBuilder.setColor(getRandomColor());
                        embedBuilder.setDescription(jsonObjectSelection.getString(keyText).replaceAll("!User",gMember.getAsMention())+"\n\n"+lsUsefullFunctions.getUrlTextString("img_source",gUrlE621Post+viewId));
                        receiveGift();
                    }else
                    if((rating.equalsIgnoreCase("e")||rating.equalsIgnoreCase("q"))&&!gTextChannel.isNSFW()){
                        logger.warn(fName+".not nsfw channel");
                        embedBuilder.setDescription("Not NSFW channel");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }else if(rating.equalsIgnoreCase("s")){
                        logger.info(fName+".sfw content");
                        //embedBuilder.setAuthor(id,gUrlE621Post+"viewId",gE621Icon);
                        embedBuilder.setImage(imgUrl);
                        embedBuilder.setColor(getRandomColor());
                        embedBuilder.setDescription(jsonObjectSelection.getString(keyText).replaceAll("!User",gMember.getAsMention())+"\n\n"+lsUsefullFunctions.getUrlTextString("img_source",gUrlE621Post+viewId));
                        receiveGift();
                    }else{
                        embedBuilder.setDescription("Invalid rating");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }
                }else{
                    logger.warn(fName + ".No image found");
                    embedBuilder.setDescription("No image found");
                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                }
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        String keyAuthor="author",keyAvatar="avatar",keyImageUrl="image_url",keyRating="rating",keyTitle="title";
        private void doPreviewFA(String address) {
            String fName = "[doPreviewFA]";
            try {
                logger.info(fName+".address ="+address);
                String viewId=address;
                if(viewId.toLowerCase().contains("https:")||viewId.toLowerCase().contains("http:")){
                    logger.info(fName+".mode given address");
                    viewId=viewId.replace("https:","").replace("http:","").replace("//","");
                    if(viewId.toLowerCase().contains("furaffinity.net/view/")){
                        viewId=viewId.replace("www.furaffinity.net/view/","").replace("furaffinity.net/view/","");
                        int index=viewId.indexOf("/");
                        if(index>0){
                            StringBuffer stringBuffer = new StringBuffer(viewId);
                            stringBuffer.replace( index , stringBuffer.length()-1,"");
                            viewId=stringBuffer.toString();
                        }
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid address", llColorRed_Chili);
                    }
                }else{
                    logger.info(fName+".mode given id");
                    viewId=address;
                }
                logger.info(fName+".viewId ="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject body=furaffinity.getImageJSON(gGlobal,viewId);
                logger.info(fName+".body ="+body.toString());
                String image="",rating="",title="",author="",authorAvatar="";
                if(body.has(keyAuthor)&&!body.isNull(keyAuthor))author=body.getString(keyAuthor);
                if(body.has(keyImageUrl)&&!body.isNull(keyImageUrl))image=body.getString(keyImageUrl);
                if(body.has(keyAvatar)&&!body.isNull(keyAvatar))authorAvatar=body.getString(keyAvatar);
                if(body.has(keyTitle)&&!body.isNull(keyTitle))title=body.getString(keyTitle);
                if(body.has(keyRating)&&!body.isNull(keyRating))rating=body.getString(keyRating);
                if(!image.isBlank()){
                    logger.info(fName+".image ="+image);
                    if(rating.isBlank()){
                        logger.warn(fName+"no rating info"); embedBuilder.setColor(llColorRed_Imperial);
                        embedBuilder.setDescription("No rating information found");
                    }
                    else if(rating.equalsIgnoreCase("general")||gTextChannel.isNSFW()){
                        embedBuilder.setImage(image); embedBuilder.setColor(getRandomColor());
                        logger.info(fName+".title ="+title);
                        logger.info(fName+".author ="+author);
                        logger.info(fName+".rating ="+rating);
                        embedBuilder.setDescription(jsonObjectSelection.getString(keyText).replaceAll("!User",gMember.getAsMention())+"\n\n"+lsUsefullFunctions.getUrlTextString("img_source",gUrlFAPost.replaceAll("%",viewId)));
                        receiveGift();
                    }else{
                        logger.warn(fName+"channel not nsfw"); embedBuilder.setColor(llColorRed_Imperial);
                        embedBuilder.setDescription("**Attention** The NSFW image can only be displayed in NSFW channels!");
                    }
                }else{
                    embedBuilder.setDescription("No image found in submission!");embedBuilder.setColor(llColorRed_Cinnabar);
                }
                //embedBuilder.addField("Important","Report it if it violates rules 2 our staff!",false);
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));

            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Error!",llColorRed);
            }
        }
        String gUrlInkBunnySubmissionPage ="https://inkbunny.net/s/%";
        private void doPreviewInkbunny(String viewId) {
            String fName = "[doPreviewInkbunny]";
            try {
                logger.info(fName+".viewId ="+viewId);
                listmimetype=new JSONObject();
                listmimetype.put("image/bmp",true);
                listmimetype.put("image/gif",true);
                listmimetype.put("image/jpeg",true);
                listmimetype.put("image/png",true);
                logger.info(fName+"viewId="+viewId);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorPurple2);
                JSONObject json=inkbunny.getImageJSONy(gGlobal,viewId);
                logger.info(fName+".json ="+json.toString());
                if(json.isEmpty()||!json.has("submissions")||json.isNull("submissions")){
                    logger.error(fName+".no submissions");return;
                }
                JSONArray submissions=json.getJSONArray("submissions");
                if(submissions.isEmpty()||submissions.length()<0){
                    logger.error(fName+".no post");return;
                }
                JSONObject post=submissions.getJSONObject(0);
                logger.info(fName+".post="+post.toString());
                boolean isPublic=false,isGuestBlock=false,isHidden=false,isImage=false, isBadKeywords=false;
                int ratingId=-1;
                String postId="",title="",authorName="",authorId="",authorAvatar="", image="";
                StringBuilder ratings= new StringBuilder();
                if(post.has(keyIBPublic)&&!post.isNull(keyIBPublic)&&post.getString(keyIBPublic).equals(valueTrue)){
                    isPublic=true;
                }
                if(post.has(keyIBGuestBlock)&&!post.isNull(keyIBGuestBlock)&&post.getString(keyIBGuestBlock).equals(valueTrue)){
                    isGuestBlock=true;
                }
                if(post.has(keyIBHidden)&&!post.isNull(keyIBHidden)&&post.getString(keyIBHidden).equals(valueTrue)){
                    isHidden=true;
                }
                if(post.has(keyIBMimeType)&&!post.isNull(keyIBMimeType)){
                    if(listmimetype.has(post.getString(keyIBMimeType))&&listmimetype.getBoolean(post.getString(keyIBMimeType))){
                        isImage=true;
                    }
                }
                logger.info(fName+".isPublic="+isPublic+", isHidden="+isHidden+", isImage="+isImage);
                if(!isPublic){
                    logger.warn(fName + ".not public");return;
                }
                if(isHidden){
                    logger.warn(fName + ".is hidden");return;
                }
                if(!isImage){
                    logger.warn(fName + ".not image");return;
                }
                logger.info(fName+".isGuestBlock="+isGuestBlock);
                if(post.has(keyIBUserId)&&!post.isNull(keyIBUserId)){
                    authorId=post.getString(keyIBUserId);
                }
                if(post.has(keyIBUserName)&&!post.isNull(keyIBUserName)){
                    authorName=post.getString(keyIBUserName);
                }
                if(post.has(keyIBUserIconUrlLarge)&&!post.isNull(keyIBUserIconUrlLarge)) {
                    authorAvatar = post.getString(keyIBUserIconUrlLarge);
                }
                logger.info(fName+".authorId="+authorId+", authorName="+authorName+", authorAvatar="+authorAvatar);
                if(post.has(keyIBSubmissionId)&&!post.isNull(keyIBSubmissionId)) {
                    postId = post.getString(keyIBSubmissionId);
                }
                if(post.has(keyIBTitle)&&!post.isNull(keyIBTitle)) {
                    title = post.getString(keyIBTitle);
                }
                logger.info(fName+". postId="+ postId+", title="+title);

                if(post.has(keyIBRatingId)&&!post.isNull(keyIBRatingId)){
                    ratingId=post.getInt(keyIBRatingId);//0-general 1-matur 2-adult
                }
                if(post.has(keyIBRatingName)&&!post.isNull(keyIBRatingName)){
                    ratings = new StringBuilder(post.getString(keyIBRatingName));
                }
                if(post.has(keyIBRatings)&&!post.isNull(keyIBRatings)){
                    JSONArray array=post.getJSONArray(keyIBRatings);
                    for(int i=0;i<array.length();i++){
                        ratings.append(", ").append(array.getJSONObject(i).getString(keyIBName));
                    }
                }
                logger.info(fName+".ratingId="+ratingId+" ,ratings="+ratings);
                if(post.has(keyIBKeywords)&&!post.isNull(keyIBKeywords)){
                    JSONArray array=post.getJSONArray(keyIBKeywords);
                    for(int i=0;i<array.length();i++){
                        try {
                            String keywordName=array.getJSONObject(i).getString(keyIBKeywordName);
                            if(keywordName.toLowerCase().contains("cub")||keywordName.toLowerCase().contains("kid")||keywordName.toLowerCase().contains("child")||keywordName.toLowerCase().contains("baby")){
                                logger.warn(fName+"contains badword");isBadKeywords=true;
                                break;
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }

                if(post.has(keyIBFileUrlFull)&&!post.isNull(keyIBFileUrlFull)){
                    image=post.getString(keyIBFileUrlFull);
                }else
                if(post.has(keyIBFileUrlScreen)&&!post.isNull(keyIBFileUrlScreen)){
                    image=post.getString(keyIBFileUrlScreen);
                }else
                if(post.has(keyIBFileUrlPreview)&&!post.isNull(keyIBFileUrlPreview)){
                    image=post.getString(keyIBFileUrlPreview);
                }
                logger.info(fName+" image="+ image);
                if(!image.isBlank()&&!image.isEmpty()&&!isBadKeywords){
                    logger.info(fName+"rating="+ratingId+", isNSFW="+gTextChannel.isNSFW());
                    if(ratingId!=0&&gTextChannel.isNSFW()){
                        logger.info(fName+".nsfw channel");
                        embedBuilder.setImage(image);
                        embedBuilder.setColor(getRandomColor());
                        embedBuilder.setDescription(jsonObjectSelection.getString(keyText).replaceAll("!User",gMember.getAsMention())+"\n\n"+lsUsefullFunctions.getUrlTextString("img_source",gUrlInkBunnySubmissionPage.replaceAll("%",viewId)));
                        receiveGift();
                    }else
                    if(ratingId!=0&&!gTextChannel.isNSFW()){
                        logger.warn(fName+".not nsfw channel");
                        embedBuilder.setDescription("Not NSFW channel");
                        embedBuilder.setColor(llColorOrange_InternationalEngineering);
                    }else{
                        logger.info(fName+".sfw content");
                        embedBuilder.setImage(image);
                        embedBuilder.setColor(getRandomColor());
                        embedBuilder.setDescription(jsonObjectSelection.getString(keyText).replaceAll("!User",gMember.getAsMention())+"\n\n"+lsUsefullFunctions.getUrlTextString("img_source",gUrlInkBunnySubmissionPage.replaceAll("%",viewId)));
                        receiveGift();
                    }
                }else {
                    logger.warn(fName + ".No image found");
                    embedBuilder.setDescription("No image found");
                    embedBuilder.setColor(llColorOrange_InternationalEngineering);
                }
                logger.info(fName+".post message");
                deleteOption(lsMessageHelper.lsSendMessageResponse(gTextChannel, embedBuilder.build()));
            } catch (Exception e) {
                logger.error(fName+"exception:"+e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String keyIBSubmissionId="submission_id",keyIBTitle="title",keyIBPublic="public",keyIBMimeType="mimetype",keyIBGuestBlock="guest_block";
        String keyIBKeywords="keywords",keyIBKeywordName="keyword_name",keyIBHidden="hidden";
        String keyIBUserName="username",keyIBUserId="user_id",keyIBUserIconUrlSmall="user_icon_url_small",keyIBUserIconUrlLarge="user_icon_url_large";
        String keyIBFileUrlFull="file_url_full",keyIBFileUrlScreen="file_url_screen",keyIBFileUrlPreview="file_url_preview";
        String keyIBRatingId="rating_id",keyIBRatingName="rating_name", keyIBRatings="ratings",keyIBName="name";
        JSONObject listmimetype;
        String valueTrue="t",valueFalse="f";
        private void deleteOption(Message message) {
            String fName="[deleteOption]";
            logger.info(fName);
            try {
                try {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji("bomb"));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                logger.info(fName+"do=delete");
                                lsMessageHelper.lsMessageDelete(message);
                            }else{
                                logger.info(fName+"do=invalid");
                                deleteOption(message);
                            }
                        },10, TimeUnit.MINUTES, () -> lsMessageHelper.lsMessageClearReactionsQueue(message));
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private Color getRandomColor(){
            String fName="getRandomColor";
            try {
                ArrayList<Color> list4Collors= new ArrayList<>();
                list4Collors.add(llColorBlue1);
                list4Collors.add(llColorBlue2);
                list4Collors.add(llColorGreen1);
                list4Collors.add(llColorGreen2);
                list4Collors.add(llColorRed_Cinnabar);
                list4Collors.add(llColorRed_CoralPink);
                int r=lsUsefullFunctions.getRandom(list4Collors.size());
                return list4Collors.get(r);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return Color.BLACK;
            }
        }
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
                }
                if(text2Json.jsonArray.isEmpty()){
                    logger.warn(fName+".isEmpty");return false;
                }
                logger.info(fName+"length="+text2Json.jsonArray.length());
                logger.info(fName + ".text2Json.jsonArray=" + text2Json.jsonArray.toString());
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        lcJSONUserProfile gUserProfile;
        String profileName="NaughtyGifts",table="MemberProfile";
        String keyGifts="gifts",keyToys="toys",keyUnopenedGifts="unopenedGifts";

        private lcJSONUserProfile iSafetyUserProfileEntry(lcJSONUserProfile gUserProfile) {
            String fName = "[iSafetyUserProfileEntry]";
            gUserProfile.safetyPutFieldEntry(keyUnopenedGifts, new JSONArray());
            gUserProfile.safetyPutFieldEntry(keyGifts, new JSONArray());
            gUserProfile.safetyPutFieldEntry(keyToys, new JSONArray());
            return gUserProfile;
        }
        private Boolean getProfile(User user){
            String fName="[getProfile]";
            logger.info(fName);
            try{
                logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
                gUserProfile=gGlobal.getUserProfile(profileName,user,gGuild,profileName);
                if(gUserProfile!=null&&gUserProfile.isProfile()){
                    logger.info(fName + ".is locally cached");
                }else{
                    logger.info(fName + ".need to get or create");
                    gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild,profileName);
                    if(gUserProfile.getProfile(table)){
                        logger.info(fName + ".has sql entry");
                    }
                }
                gUserProfile=iSafetyUserProfileEntry(gUserProfile);
                gGlobal.putUserProfile(gUserProfile,profileName);
                if(!gUserProfile.isUpdated){
                    logger.info(fName + ".no update>ignore");return true;
                }
                if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
                return true;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            try{
                gGlobal.putUserProfile(gUserProfile,profileName);
                if(gUserProfile.saveProfile(table)){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        lcJSONUserProfile gUserRestraintsProfile;
        lcBDSMGuildProfiles gBBDSMCommands;
        private Boolean getRestraintsProfile(User user){
            String fName="[getRestraintsProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
            gUserRestraintsProfile=gGlobal.getUserProfile(iRestraints.profileName,user,gGuild);
            if(gUserRestraintsProfile!=null&&gUserRestraintsProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserRestraintsProfile=new lcJSONUserProfile(gGlobal,user,gGuild);
                if(gUserRestraintsProfile.getProfile(iRestraints.table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserRestraintsProfile= iRestraints.sUserInit(gUserRestraintsProfile,gBBDSMCommands.getRestrainsProfile());
            gGlobal.putUserProfile(gUserRestraintsProfile,iRestraints.profileName);
            if(!gUserRestraintsProfile.isUpdated){
                logger.info(fName + ".no update>ignore");
                getRestraintsMainJsons();return true;
            }
            if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,"Restrainrtrs update","Failed to write in Db!", llColorRed);}
            getRestraintsMainJsons();return true;
        }
        private Boolean saveRestraintsProfile(){
            String fName="[saveRestraintsProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserRestraintsProfile,iRestraints.profileName);
            if(gUserRestraintsProfile.saveProfile(iRestraints.table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        JSONObject STRAITJACKET,ARMCUFFS, LEGCUFFS, GAG, COLLAR,CHASTITY,EARMUFFS,SUIT,BLINDFOLD,HOOD,MITTS;
        private void getRestraintsMainJsons(){
            String fName="[getRestraintsMainJsons]";
            try {
                logger.info(fName);
                STRAITJACKET =gUserProfile.jsonObject.getJSONObject(iRestraints.nStraitjacket);
                ARMCUFFS =gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs);
                LEGCUFFS =gUserProfile.jsonObject.getJSONObject(iRestraints.nLegsCuffs);
                GAG=gUserProfile.jsonObject.getJSONObject(iRestraints.nGag);
                COLLAR =gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar);
                CHASTITY =gUserProfile.jsonObject.getJSONObject(iRestraints.nChastity);
                EARMUFFS =gUserProfile.jsonObject.getJSONObject(iRestraints.nEarMuffs);
                SUIT =gUserProfile.jsonObject.getJSONObject(iRestraints.nSuit);
                BLINDFOLD =gUserProfile.jsonObject.getJSONObject(iRestraints.nBlindfold);
                HOOD =gUserProfile.jsonObject.getJSONObject(iRestraints.nHood);
                MITTS =gUserProfile.jsonObject.getJSONObject(iRestraints.nMitts);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String vName="name", keyRestraint ="restraint";
        private void executeRestraints(JSONObject restraint) {
            String fName="[executeRestraints]";
            logger.info(fName);
            try {
                logger.info(fName+"restraint="+restraint.toString());
                if(!restraint.has(vName)){
                    logger.info(fName+" vName is not found");
                    return;
                }
                if(restraint.isNull(vName)){
                    logger.info(fName+" vName is null");
                    return;
                }
                if(restraint.getString(vName).isBlank()){
                    logger.info(fName+" vName is blank");
                    return;
                }
                String name=restraint.getString(vName);
                logger.info(fName+"name="+name);
                String level="";
                if(!restraint.has(iRestraints.nLevel)){
                    logger.info(fName+" level is not found");
                }
                else if(restraint.isNull(iRestraints.nLevel)){
                    logger.info(fName+" level is null");
                }
                else if(restraint.getString(iRestraints.nLevel).isBlank()){
                    logger.info(fName+" level is blank");
                }
                else {
                    level=restraint.getString(iRestraints.nLevel);
                    logger.info(fName+" level="+level);
                }

                if(name.equalsIgnoreCase(iRestraints.nStraitjacket)||name.equalsIgnoreCase("sj")){
                    putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);
                    if(restraint.has(iRestraints.nOn)&&!restraint.isNull(iRestraints.nOn)){
                        logger.info(fName+" nOn found");
                        putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,restraint.getBoolean(iRestraints.nOn));
                    }

                    if(restraint.has(iRestraints.nStrapArms)&&!restraint.isNull(iRestraints.nStrapArms)){
                        logger.info(fName+" nStrapArms found");
                        if(restraint.getBoolean(iRestraints.nStrapArms)){putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,true);  putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);}
                        else putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,false);
                    }
                    else if(restraint.has("arm")&&!restraint.isNull("arm")){
                        logger.info(fName+" arm found");
                        if(restraint.getBoolean("arm")){putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,true);  putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);}
                        else putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,false);
                    }
                    else if(restraint.has("arms")&&!restraint.isNull("arms")){
                        logger.info(fName+" arms found");
                        if(restraint.getBoolean("arms")){putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,true);  putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);}
                        else putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapArms,false);
                    }

                    if(restraint.has(iRestraints.nStrapCrotch)&&!restraint.isNull(iRestraints.nStrapCrotch)){
                        logger.info(fName+" nStrapCrotch found");
                        if(restraint.getBoolean(iRestraints.nStrapCrotch)){putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapCrotch,true);  putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);}
                        else putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapCrotch,false);
                    }
                    else if(restraint.has("crotch")&&!restraint.isNull("crotch")){
                        logger.info(fName+" crotch found");
                        if(restraint.getBoolean("crotch")){putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapCrotch,true);  putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nOn,true);}
                        else putFieldEntryRestraints(iRestraints.nStraitjacket,iRestraints.nStrapCrotch,false);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nArmsCuffs)||name.equalsIgnoreCase("arms")||name.equalsIgnoreCase("arm")){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nArmsCuffs,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nArmsCuffs,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nLegsCuffs)||name.equalsIgnoreCase("legs")||name.equalsIgnoreCase("leg")){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nLegsCuffs,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nLegsCuffs,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nCollar)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nCollar,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nCollar,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nGag)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nGag,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nGag,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nChastity)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nChastity,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nChastity,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nHood)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nHood,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nHood,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nBlindfold)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nBlindfold,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nBlindfold,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nSuit)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nSuit,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nSuit,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nMitts)){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nMitts,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nMitts,iRestraints.nLevel,true);
                    }
                }
                else  if(name.equalsIgnoreCase(iRestraints.nEarMuffs)||name.equalsIgnoreCase("ears")||name.equalsIgnoreCase("ear")){
                    if(!level.isBlank()){
                        putFieldEntryRestraints(iRestraints.nEarMuffs,iRestraints.nOn,true);
                        putFieldEntryRestraints(iRestraints.nEarMuffs,iRestraints.nLevel,true);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void putFieldEntryRestraints(String field,String name, Object value){
            String fName="[putFieldEntryRestraints]";
            logger.info(fName+".field="+field);
            logger.info(fName+".name="+name);
            logger.info(fName+".value="+value);
            gUserRestraintsProfile.putFieldEntry(field,name,value);
        }
    }
}
