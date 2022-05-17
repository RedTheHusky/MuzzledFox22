package nsfw;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class chastitycompetition   extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper {
    Logger logger = Logger.getLogger(getClass()); String cName="[chastitycompetition]";
    String table="chastitycompetition_Spirit"; String sqlLog="chastitycompetitionLog_Spirit";
    lcGlobalHelper gGlobal;
    String sRTitle="New Year Chastity Competition";
    JSONObject rfEntries;
    String competitionChannelId="529352794956627968";
    String roleKeyID_AvailableKeyholder="486473513570664448";
    String roleKeyID_Keyholder="486473253833932802";
    public chastitycompetition(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(cName+fName);
        gGlobal=global;
        this.name = "Chastity Competition";
        this.help = "Chastity competition `"+llPrefixStr+"ccompetition`";
        this.aliases = new String[]{"ccompetition","chastitycompetition", "chastityc","competition"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true; this.hidden=true;
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
        String cName="[runLocal]";
        CommandEvent gEvent;
        User gUser ;
        Guild gGuild;TextChannel competitionChannel;TextChannel gTextChannel;
        public runLocal(CommandEvent ev){
            logger.info(cName+".run build");
            gEvent=ev;
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(cName+".run start");
            String[] items;Boolean isInvalidCommand=true;
            gUser = gEvent.getAuthor();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel=gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if(gGuildLock()){

            }else
            if(gEvent.getArgs().isEmpty()){
                logger.info(cName+fName+".Args=0");
                help("main");
                gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=false;
            }else {
                if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)){
                    try{
                        competitionChannel=gGuild.getTextChannelById(competitionChannelId);
                    }catch (Exception ex){
                        logger.error(cName+fName+".exception:"+ex);
                    }
                }
                logger.info(cName + fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                logger.info(cName + fName + ".items.size=" + items.length);
                logger.info(cName + fName + ".items[0]=" + items[0]);
                if(items[0].equalsIgnoreCase("help")){
                    if(items.length>=2){
                        //hygiene join sissygasm
                        if (items[1].equalsIgnoreCase("hygiene")) {
                            help("hygiene");
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("join")) {
                            help("join");
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("sissygasm")) {
                            help("sissygasm");
                            isInvalidCommand = false;
                        }
                    }else{
                        help("main");isInvalidCommand = false;
                    }
                }
                if(items[0].equalsIgnoreCase("list")||items[0].equalsIgnoreCase("printusers")){
                    printUsers();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("register")){
                    register();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("unregister")){
                    unregister();isInvalidCommand = false;
                }
                /*if(items[0].equalsIgnoreCase("start")){
                    start();isInvalidCommand = false;
                }*/
                if(items[0].equalsIgnoreCase("stop")||items[0].equalsIgnoreCase("abort")){
                    abort();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("getstart")){
                    getStartVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("acceptstart")){
                    acceptStartVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("rejectstart")){
                    rejectStartVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("hasverification")){
                    hasVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("requestverification")){
                    regVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("getverification")){
                    getVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("submitverification")){
                    submitVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("acceptverificatin")){
                    acceptVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("rejectverificatin")){
                    rejectVerification();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("hygienelock")){
                    hygieneLock();;isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("hygieneunlock")){
                    hygieneUnlock();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("printhygieneunlocks")){
                    printHygieneUnlock();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("hygiene")){
                    if(items.length>=2) {
                        if (items[1].equalsIgnoreCase("list")) {
                            printHygieneUnlock();
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("lock")) {
                            hygieneLock();
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("unlock")) {
                            hygieneUnlock();
                            isInvalidCommand = false;
                        }
                    }
                }
                if(items[0].equalsIgnoreCase("start")){
                   if(items.length>=2){
                       if(items[1].equalsIgnoreCase("view")){getStartVerification();isInvalidCommand = false;}
                       if(items[1].equalsIgnoreCase("accept")){acceptStartVerification();isInvalidCommand = false;}
                       if(items[1].equalsIgnoreCase("reject")){rejectStartVerification();isInvalidCommand = false;}
                   }else{
                       start();isInvalidCommand = false;
                   }
                }
                if(items[0].equalsIgnoreCase("verification")){
                    if(items.length>=2){
                        if(items[1].equalsIgnoreCase("has")){hasVerification();isInvalidCommand = false;}
                        if(items[1].equalsIgnoreCase("request")){regVerification();isInvalidCommand = false;}
                        if(items[1].equalsIgnoreCase("view")){getVerification();isInvalidCommand = false;}
                        if(items[1].equalsIgnoreCase("accept")){acceptVerification();isInvalidCommand = false;}
                        if(items[1].equalsIgnoreCase("reject")){rejectVerification();isInvalidCommand = false;}
                    }else{
                        hasVerification();isInvalidCommand = false;
                    }
                }
                if(items[0].equalsIgnoreCase("asksissygasms")){
                    askSissygasms();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("approvesissygasms")){
                    approveSissygasms();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("rejectsissygasms")){
                   rejectSissygasms();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("printAskSissygasms")){
                    printAskSissygasms();isInvalidCommand = false;
                }
                if(items[0].equalsIgnoreCase("sissygasm")||items[0].equalsIgnoreCase("sissygasms")){
                    if(items.length>=2) {
                        if (items[1].equalsIgnoreCase("list")) {
                            printAskSissygasms();
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("ask") || items[1].equalsIgnoreCase("request")) {
                            askSissygasms();
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("approve") || items[1].equalsIgnoreCase("accept")) {
                            approveSissygasms();
                            isInvalidCommand = false;
                        }
                        if (items[1].equalsIgnoreCase("reject") || items[1].equalsIgnoreCase("deny")) {
                            rejectSissygasms();
                            isInvalidCommand = false;
                        }
                    }
                }
            }
            logger.info(cName+fName+".deleting op message");
            llMessageDelete(gEvent);
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
            }
            logger.info(cName+".run ended");
        }

        private Boolean gGuildLock(){
            String fName = "[gGuildLock]";
            logger.info(cName + fName);
            if(!gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)){
                llSendQuickEmbedMessage(gUser,sRTitle,"Available only for the chastit yserver!", llColorRed);
                logger.info(cName + fName + ".true");return true;
            }
            logger.info(cName + fName + ".false");return false;
        }
        private void printUsers(){
            String fName = "[printUsers]";
            logger.info(cName + fName);
            gGlobal.sql.checkConnection();

            try{
                logger.info(cName+fName+".table="+table);
                String query = "select * from "+table+" WHERE guild_id='"+gGuild.getId()+"'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                JSONArray array=new JSONArray();
                while (rs.next()){
                    Map<String,Object> row=new TreeMap<String,Object>();
                    JSONObject obj=new JSONObject();
                    obj.put("id",rs.getObject("user_id"));
                    obj.put("json",new JSONObject(rs.getString("json")));
                    array.put(obj);
                }
                logger.info(cName+fName+".array="+array.toString());
                int size=array.length();
                StringBuilder desc_notStarted= new StringBuilder("Players registered but not started yet:");
                StringBuilder desc_pendingStart= new StringBuilder("Players pending their start verification:");
                StringBuilder desc_started= new StringBuilder("Players starting verification approved:");
                logger.info(cName+fName+".size="+size);
                for(int i=0;i<size;i++){
                    logger.info(cName+fName+".i="+i);
                    JSONObject obj=array.getJSONObject(i);
                    Member member=gGuild.getMemberById(obj.getString("id"));
                    if(member!=null){
                        JSONObject json=obj.getJSONObject("json");
                        Boolean isRegistered=false;
                        Boolean isStart=false;
                        Boolean isVerified=false;
                        if(json.has(fieldStatus)) {
                            isRegistered=json.getJSONObject(fieldStatus).getBoolean(keyStatusRegister);
                            isStart=json.getJSONObject(fieldStatus).getBoolean(keyStatusStart);
                            isVerified=json.getJSONObject(fieldStatus).getBoolean(keyStatusVerified);
                        }
                        if(isVerified&&isStart&&isRegistered){
                            desc_started.append("\n").append(member.getAsMention());
                        }else
                        if(isStart&&isRegistered){
                            desc_pendingStart.append("\n").append(member.getAsMention());
                        }else
                        if(isRegistered){
                            desc_notStarted.append("\n").append(member.getAsMention());
                        }
                    }
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle, desc_notStarted.toString(), llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, desc_pendingStart.toString(), llColorPurple1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, desc_started.toString(), llColorGreen1);
            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to read from Db!", llColorRed);
            }
        }
        lcJSONUserProfile gUserProfile;
        private void register(){
            String fName = "[register]";
            logger.info(cName + fName);
            loadedProfile(gUser);
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            if(isKicked){
                llSendQuickEmbedMessage(gUser,sRTitle,"Anti-cheat prevention! You are kicked from the competition! Please contact the staff.", llColorRed); return;
            }
            if(isAbort){
                llSendQuickEmbedMessage(gUser,sRTitle,"Anti-cheat prevention! To prevent cheating you can't simple un-register once started the competition. Please contact the staff.", llColorRed);return;
            }
            if(isRegistered){
                llSendQuickEmbedMessage(gUser,sRTitle,"Already registered!", llColorRed);return;
            }
            resetUserProfileEntry();
            putFieldEntry(fieldStatus, keyStatusRegister, true);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp:"+timestamp.getTime());
            putFieldEntry(fieldTimeStamp,registeredTimeStamp,timestamp.getTime());
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sRTitle,"You are registered", llColorGreen1);
        }
        private void unregister(){
            String fName = "[unregister]";
            logger.info(cName + fName);
            loadedProfile(gUser);
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            if(!isRegistered){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not registered to un-register!", llColorRed);return;
            }
            if(isKicked){
                llSendQuickEmbedMessage(gUser,sRTitle,"Anti-cheat prevention! You are kicked from the competition! Please contact the staff.", llColorRed); return;
            }
            if(isAbort){
                llSendQuickEmbedMessage(gUser,sRTitle,"Anti-cheat prevention! To prevent cheating you can't simple un-register once started the competition. Please contact the staff.", llColorRed);return;
            }
            if(isStart){
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't unregister once started! You need to abort the competition and then contact the staff to clear you from the list of anti-cheat.", llColorRed);return;
            }
            putFieldEntry(fieldStatus, keyStatusRegister, false);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
            llSendQuickEmbedMessage(gUser,sRTitle,"You are un-registered", llColorBlue1);
        }
        private void start(){
            String fName = "[start]";
            logger.info(cName + fName);
            loadedProfile(gUser);
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            if(!isRegistered){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not registered to start!", llColorRed);return;
            }
            if(isKicked){
                llSendQuickEmbedMessage(gUser,sRTitle,"You are kicked from the competition! Please contact the staff.", llColorRed); return;
            }
            if(isAbort){
                llSendQuickEmbedMessage(gUser,sRTitle,"Anti-cheat prevention! To prevent cheating you can't simple restart again once aborted. Please contact the staff.", llColorRed);return;
            }
            if(isStart){
                llSendQuickEmbedMessage(gUser,sRTitle,"Already started!", llColorRed);return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,"To start simple type `yes` once you locked yourself away.\nTo cancel the command, type anything else or wait for the timeout (1 minute).\nOnce started you can at anytime abort it, but can't restart a new one without contacting the staff first to clear your previous session.", llColorPurple1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gEvent.getAuthor()),
                    // respond, inserting the name they listed into the response
                    e -> {
                        if(e.getMessage().getContentRaw().equalsIgnoreCase("yes")){
                            startSubmitVerification();
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,"Canceled command!", llColorRed);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () ->{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Timeout!", llColorRed);});
        }
        private void startSubmitVerification(){
            String fName = "[startSubmitVerification]";
            logger.info(cName + fName);
            String code=randomCode();
            llSendQuickEmbedMessage(gUser,sRTitle,"Please perform your starting verification in no lass than 30 minutes from this point to start the competition!\nSend via attachment your verification picture with code:"+code+".\nType `cancel` to cancel the  command!", llColorPurple1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gEvent.getAuthor()),
                    // respond, inserting the name they listed into the response
                    e -> {
                        if(e.getMessage().getContentRaw().equalsIgnoreCase("cancel")){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Command canceled", llColorRed); return;
                        }
                        List<Message.Attachment> items=e.getMessage().getAttachments();
                        if(items.isEmpty()){
                            logger.warn(cName + fName + ".no attachments");
                            llSendQuickEmbedMessage(gUser,sRTitle,"Error, no attachments!", llColorRed); return;
                        }
                        Message.Attachment item=items.get(0);
                        String fileExtension= item.getFileExtension();
                        logger.info(cName + fName + ".fileExtension="+fileExtension);
                        if(!(fileExtension.equalsIgnoreCase("jpg")||fileExtension.equalsIgnoreCase("png"))){
                            logger.warn(cName + fName + ".invalid extension");
                            llSendQuickEmbedMessage(gUser,sRTitle,"Error, invalid attachments! Needs to be jpg or png.", llColorRed);return;
                        }
                        String url=item.getUrl();
                        logger.info(cName+fName+".url="+url);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        logger.info(cName+fName+".timestamp="+timestamp.getTime());
                        putFieldEntry(fieldStatus, keyStatusStart, true);
                        putFieldEntry(fieldVerificationStart,hasDoneStartVerification,true);
                        putFieldEntry(fieldVerificationStart,linkStartVerification,url);
                        putFieldEntry(fieldVerificationStart,codeStartVerification,code);
                        putFieldEntry(fieldVerificationStart,statusStartVerification,0);
                        putFieldEntry(fieldTimeStamp,starTimeStamp,timestamp.getTime());
                        if(!saveProfile()){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                        insert2Log(gUser,gGuild,"sp",url,code);
                        TextChannel textChannel=gTextChannel;
                        if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                            textChannel=competitionChannel;
                        }
                        EmbedBuilder embed= new EmbedBuilder();
                        embed.setColor(llColorBlue1);
                        embed.setTitle(sRTitle);
                        if(llIsValidURL(url)){
                            embed.setImage(url);
                        }else{
                            embed.setImage(llLewdNoImage);
                        }
                        String desc=gUser.getAsMention()+" has submitted their starting verification. Staff will check the picture before officially adding them to competition.";
                        desc+="\nCode: "+code;
                        embed.setDescription(desc);
                        llSendMessage(textChannel,embed);
                        //llQuickEmbedChannelResponse(textChannel,sRTitle,gUser.getAsMention()+" has submitted their starting verification. Staff will check the picture before officially adding them to competition.",llColorGreen);
                        llSendQuickEmbedMessage(gUser,sRTitle,"Thanks for submitting your starting verification. Staff will check the picture before officially adding you to the competition.", llColorGreen1);

                    },
                    // if the gUser takes more than a minute, time out
                    30, TimeUnit.MINUTES, () ->{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Timeout!", llColorRed);});

        }
        private void getStartVerification(){
            String fName = "[getStartVerification]";
            logger.info(cName + fName);
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            String code="";
            String url="";
            int startStatus=0;
            User user;
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                loadedProfile(gUser);
                user=gUser;
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                loadedProfile(member.getUser());
                user=member.getUser();
            }
            if(!gUserProfile.isProfile()){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+ " isn't playing!", llColorRed);return;
            }
            Boolean isRegistered= (Boolean)gUserProfile.getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusVerified);
            if(!isRegistered||!isStart||isAbort||isKicked){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+ " isn't playing!", llColorRed);return;
            }
            code= (String) gUserProfile.getFieldEntry(fieldVerificationStart,codeStartVerification);
            url= (String) gUserProfile.getFieldEntry(fieldVerificationStart,linkStartVerification);
            startStatus= (Integer) gUserProfile.getFieldEntry(fieldVerificationStart,statusStartVerification);
            if(url.isEmpty()){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,gUser.getAsMention()+ " has no starting verification!", llColorRed);return;
            }
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorBlue1);
            embed.setTitle(sRTitle);
            if(llIsValidURL(url)){
                embed.setImage(url);
            }else{
                embed.setImage(llLewdNoImage);
            }
            String desc="User:"+user.getAsMention();
            desc+="\nVerification code:"+code;
            if(startStatus==1){
                desc+="\nStatus: accepted";
            }else
            if(startStatus==-1){
                desc+="\nStatus: rejected";
            }else{
                desc+="\nStatus: pending";
            }
            embed.setDescription(desc);
            llSendMessage(gTextChannel,embed);
        }
        private void acceptStartVerification(){
            String fName = "[acceptStartVerification]";
            logger.info(cName + fName);
            if(!llMemberIsStaff(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Staff only allowed to use this command!", llColorRed);
                return;
            }
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the player.", llColorRed);
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                loadedProfile(member.getUser());
                if(!gUserProfile.isProfile()){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " isn't playing!", llColorRed);return;
                }
                Boolean isRegistered= (Boolean)gUserProfile.getFieldEntry(fieldStatus, keyStatusRegister);
                Boolean isKicked= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusKicked);
                Boolean isAbort= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusAbort);
                Boolean isStart= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusStart);
                Boolean isVerified= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusVerified);
                if(!isRegistered||!isStart){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " isn't playing!", llColorRed);return;
                }
                if(isAbort){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " has aborted!", llColorRed);return;
                }
                if(isKicked){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " is kicked!", llColorRed);return;
                }
                if(isVerified){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " is already verified!", llColorRed);return;
                }
                String code= (String) gUserProfile.getFieldEntry(fieldVerificationStart,codeStartVerification);
                String url= (String) gUserProfile.getFieldEntry(fieldVerificationStart,linkStartVerification);
                gUserProfile.putFieldEntry(fieldStatus, keyStatusVerified,true);
                gUserProfile.putFieldEntry(fieldStatus, keyStatusActive, true);
                gUserProfile.putFieldEntry(fieldVerificationStart,statusStartVerification,1);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db for "+member.getAsMention()+"!", llColorRed); return;}
                insert2Log(member.getUser(),gGuild,"sa",url,code);
                TextChannel textChannel=gTextChannel;
                if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                    textChannel=competitionChannel;
                }
                llSendQuickEmbedMessage(textChannel,sRTitle,member.getAsMention()+" has sealed the deal and started the competition.", llColorGreen1);
                llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+" starting verification was accepted!", llColorGreen1);
                llSendQuickEmbedMessage(member.getUser(),sRTitle,"Your starting verification was accepted!\nYou have sealed the deal and started the competition!", llColorGreen1);
                try{
                    Role role=gGuild.getRoleById("661609696918110238");
                    gGuild.addRoleToMember(member,role).queue();
                }catch (Exception e){
                    logger.error(cName+fName+".Exception: "+e);
                }

            }
        }
        private void rejectStartVerification(){
            String fName = "[rejectStartVerification]";
            logger.info(cName + fName);
            if(!llMemberIsStaff(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Staff only allowed to use this command!", llColorRed);
                return;
            }
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the player.", llColorRed);
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                loadedProfile(member.getUser());
                if(!gUserProfile.isProfile()){
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,member.getAsMention()+ " isn't playing!", llColorRed);return;
                }
                Boolean isRegistered= (Boolean)gUserProfile.getFieldEntry(fieldStatus, keyStatusRegister);
                Boolean isKicked= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusKicked);
                Boolean isAbort= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusAbort);
                Boolean isStart= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusStart);
                Boolean isVerified= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusVerified);
                if(!isRegistered||!isStart){
                    llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+ " isn't playing!", llColorRed);return;
                }
                if(isAbort){
                    llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+ " has aborted!", llColorRed);return;
                }
                if(isKicked){
                    llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+ " is kicked!", llColorRed);return;
                }
                int startStatus= (Integer) gUserProfile.getFieldEntry(fieldVerificationStart,statusStartVerification);
                if(isVerified||startStatus!=0){
                    llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+ " is already verified!", llColorRed);return;
                }
                String code= (String) gUserProfile.getFieldEntry(fieldVerificationStart,codeStartVerification);
                String url= (String) gUserProfile.getFieldEntry(fieldVerificationStart,linkStartVerification);
                gUserProfile.putFieldEntry(fieldStatus, keyStatusVerified,false);
                gUserProfile.putFieldEntry(fieldStatus, keyStatusStart, false);
                gUserProfile.putFieldEntry(fieldStatus, keyStatusActive, false);
                gUserProfile.putFieldEntry(fieldVerificationStart,statusStartVerification,-1);
                if(!gUserProfile.saveProfile(table)){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db for "+member.getAsMention()+"!", llColorRed); return;}
                insert2Log(member.getUser(),gGuild,"sr",url,code);
                TextChannel textChannel=gTextChannel;
                if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                    textChannel=competitionChannel;
                }
                llSendQuickEmbedMessage(textChannel,sRTitle,member.getAsMention()+" starting verification rejected!", llColorRed);
                llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+" starting verification was rejected!", llColorRed);
                llSendQuickEmbedMessage(member.getUser(),sRTitle,"Your starting verification was rejected!", llColorRed);
            }
        }
        private void abort(){
            String fName = "[abort]";
            logger.info(cName + fName);
            String kgUser=gUser.getId();
            logger.info(cName+fName+".kgUser="+kgUser);
            loadedProfile(gUser);
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            if(!isRegistered){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not registered to abort!", llColorRed);return;
            }
            if(isKicked){
                llSendQuickEmbedMessage(gUser,sRTitle,"You are kicked from the competition! Please contact the staff.", llColorRed); return;
            }
            if(!isStart){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not started the competition!", llColorRed);return;
            }
            if(isAbort){
                llSendQuickEmbedMessage(gUser,sRTitle,"Already aborted!", llColorRed);return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,"To abort simple type `yes`.\nTo cancel command, type anything else or wait for the timeout (1 minute).\nOnce aborted as a anti-cheat measure you can't restart again without contacting the staff first to clear your previous session.", llColorPurple1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gEvent.getAuthor()),
                    // respond, inserting the name they listed into the response
                    e -> {
                        if(e.getMessage().getContentRaw().equalsIgnoreCase("yes")){
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            logger.info(cName+fName+".timestamp:"+timestamp.getTime());
                            putFieldEntry(fieldStatus, keyStatusAbort, true);
                            putFieldEntry(fieldStatus, keyStatusActive,false);
                            putFieldEntry(fieldTimeStamp,abordTimeStamp,timestamp.getTime());
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            TextChannel textChannel=gTextChannel;
                            if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                                textChannel=competitionChannel;
                            }
                            llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" decided to abort the competition!", llColorGreen1);
                            llSendQuickEmbedMessage(gUser,sRTitle,"Decided to free yourself from the competition!", llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,"Canceled command!", llColorRed);
                        }
                    },
                    // if the gUser takes more than a minute, time out
                    1, TimeUnit.MINUTES, () ->{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Timeout!", llColorRed);});
        }
        private void regVerification(){
            String fName = "[regVerification]";
            logger.info(cName + fName);
             if(!llMemberIsStaff(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Staff only allowed to use this command!", llColorRed);
                return;
            }
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not implemented!", llColorRed);
        }
        private void getVerification(){
            String fName = "[getStartVerification]";
            logger.info(cName + fName);
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not implemented!", llColorRed);
        }
        private void acceptVerification(){
            String fName = "[acceptVerification]";
            logger.info(cName + fName);
            if(!llMemberIsStaff(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Staff only allowed to use this command!", llColorRed);
                return;
            }
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not implemented!", llColorRed);
        }
        private void rejectVerification(){
            String fName = "[rejectVerification]";
            logger.info(cName + fName);
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            if(!llMemberIsStaff(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Staff only allowed to use this command!", llColorRed);
                return;
            }
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not implemented!", llColorRed);
        }
        private void submitVerification(){
            String fName = "[submitVerification]";
            logger.info(cName + fName);
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Not implemented!", llColorRed);
        }
        private void hasVerification(){
            String fName = "[hasVerification]";
            logger.info(cName + fName);
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            User user;
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                if(!loadedProfile(gUser)){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+gUser.getAsMention()+" profile!", llColorRed); return;}
                user=gUser;
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                if(!loadedProfile(member.getUser())){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+member.getAsMention()+" profile!", llColorRed); return;}
                user=member.getUser();
            }
            if(!gUserProfile.isProfile()){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+ " isn't playing!", llColorRed);return;
            }
            Boolean isRegistered= (Boolean)gUserProfile.getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) gUserProfile.getFieldEntry(fieldStatus, keyStatusStart);
            if(!isRegistered||isKicked||isAbort||!isStart){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+ " isn't playing!", llColorRed);return;
            }
            Boolean isDoneStartVerification= (Boolean)gUserProfile.getFieldEntry(fieldVerificationStart,hasDoneStartVerification);
            int startStatus= (Integer) gUserProfile.getFieldEntry(fieldVerificationStart,statusStartVerification);
            if(!isDoneStartVerification){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+" has first verification to perform!", llColorPurple1);
                return;
            }
            if(startStatus==0){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+" has first verification still pending to be checked by staff!", llColorPurple1);
                return;
            }
            if(startStatus==-1){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+" has first verification rejected!", llColorRed);
                return;
            }
            Boolean isReqVerification= (Boolean) gUserProfile.getFieldEntry(fieldVerification,hasReqVerification);
            if(isReqVerification){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+" has verification to perform!", llColorPurple1);
                return;
            }
            llSendQuickEmbedMessage(gTextChannel,sRTitle,user.getAsMention()+" has no verification to perform!", llColorPurple1);

        }
        private  void printHygieneUnlock(){
            String fName = "[printHygieneUnlock]";
            logger.info(cName + fName);
            gGlobal.sql.checkConnection();

            try{
                logger.info(cName+fName+".table="+table);
                String query = "select * from "+table+" WHERE guild_id='"+gGuild.getId()+"'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                JSONArray array=new JSONArray();
                while (rs.next()){
                    Map<String,Object> row=new TreeMap<String,Object>();
                    JSONObject obj=new JSONObject();
                    obj.put("id",rs.getObject("user_id"));
                    obj.put("json",new JSONObject(rs.getString("json")));
                    array.put(obj);
                }
                logger.info(cName+fName+".array="+array.toString());
                int size=array.length();
                logger.info(cName+fName+".size="+size);
                StringBuilder desc_asking=new StringBuilder("Players currently unlocked for hygiene or other reasons:");
                for(int i=0;i<size;i++){
                    logger.info(cName+fName+".i="+i);
                    JSONObject obj=array.getJSONObject(i);
                    Member member=gGuild.getMemberById(obj.getString("id"));
                    if(member!=null){
                        JSONObject json=obj.getJSONObject("json");
                        Boolean isRegistered=false;
                        Boolean isStart=false;
                        Boolean isVerified=false;
                        int isHygieneUnlock=0;
                        if(json.has(fieldStatus)) {
                            isRegistered=json.getJSONObject(fieldStatus).getBoolean(keyStatusRegister);
                            isStart=json.getJSONObject(fieldStatus).getBoolean(keyStatusStart);
                            isVerified=json.getJSONObject(fieldStatus).getBoolean(keyStatusVerified);
                        }
                        if(isVerified&&isStart&&isRegistered){
                            isHygieneUnlock=json.getJSONObject(fieldHygieneOpening).getInt(keyHygieneOpeningStatus);
                            if(isHygieneUnlock==-1){
                                desc_asking.append("\n").append(member.getAsMention());
                            }
                        }
                    }
                }
            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to read from Db!", llColorRed);
            }
        }
        private void hygieneUnlock() {
            String fName = "[hygieneUnlock]";
            logger.info(cName + fName);
            if(!loadedProfile(gUser)){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+gUser.getAsMention()+" profile!", llColorRed); return;}
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            TextChannel textChannel=gTextChannel;
            if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                textChannel=competitionChannel;
            }
            if(!isRegistered||isKicked||isAbort||!isStart||!isVerified){
                llSendQuickEmbedMessage(gUser,sRTitle,"No playing!", llColorRed);return;
            }
            int mode= (int) getFieldEntry(fieldHygieneOpening,keyHygieneOpeningMode);
            if(mode==-1){
                llSendQuickEmbedMessage(gUser,sRTitle,"You are not allowed to open your cage for cleaning!", llColorRed);
                llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+ " is not allowed hygiene openings!", llColorRed);return;
            }
            if(mode==1){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not implemented ask mode!", llColorRed);
            }else{
                int hygieneStatus= (int)getFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus);
                if(hygieneStatus==-1){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Already unlocked!", llColorRed);return;
                }
                llSendQuickEmbedMessage(gUser,sRTitle," Are you sure you want to unlock? Type `yes` for yes.", llColorPurple1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                        e -> e.getAuthor().equals(gEvent.getAuthor()),
                        // respond, inserting the name they listed into the response
                        e -> {
                            if(e.getMessage().getContentRaw().equalsIgnoreCase("yes")){
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                logger.info(cName+fName+".timestamp:"+timestamp.getTime());
                                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus, -1);//-1 unlocked 1 (re)locked
                                int count=(int) getFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountUnlocks);
                                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountUnlocks, count++);
                                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastUnlock, timestamp.getTime());
                                if(!saveProfile()){
                                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                                insert2Log(gUser,gGuild,"hygieneLock","","unlock");
                                TextChannel textChannel2=gTextChannel;
                                if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                                    textChannel2=competitionChannel;
                                }
                                llSendQuickEmbedMessage(textChannel2,sRTitle,gUser.getAsMention()+" decided to open their cage for cleaning. They have an hour. :3", llColorGreen1);
                                llSendQuickEmbedMessage(gUser,sRTitle,"Decided to open your cage for cleaning. You have an hour. :3", llColorGreen1);
                            }else{
                                llSendQuickEmbedMessage(gUser,sRTitle,"Canceled command! You're safely locked away.", llColorRed);
                            }
                        },
                        // if the gUser takes more than a minute, time out
                        1, TimeUnit.MINUTES, () ->{
                            llSendQuickEmbedMessage(gUser,sRTitle,"Timeout!", llColorRed);});
            }
        }
        private void hygieneLock() {
            String fName = "[hygieneLock]";
            logger.info(cName + fName);

            if(!loadedProfile(gUser)){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+gUser.getAsMention()+" profile!", llColorRed); return;}
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            if(!isRegistered||isKicked||isAbort||!isStart||!isVerified){
                llSendQuickEmbedMessage(gUser,sRTitle,"Not playing!", llColorRed);return;
            }
            int mode= (int) getFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus);
            Boolean isRequred= (Boolean) getFieldEntry(fieldHygieneOpening, keyHygieneOpeningVerificationRequest);
            if(!isRequred){
                TextChannel textChannel=gTextChannel;
                if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                    textChannel=competitionChannel;
                }
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(cName+fName+".timestamp="+timestamp.getTime());
                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus, 1);//-1 unlocked 1 (re)locked
                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastLock, timestamp.getTime());
                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLink, "");
                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCode, "");
                putFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerified, 0);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" has relocked back. Good chastity sub. :3", llColorGreen1);
                llSendQuickEmbedMessage(gUser,sRTitle,"Relocked back. Good chastity sub. :3", llColorGreen1);
                return;
            }
            if(mode==0){
                llSendQuickEmbedMessage(gUser,sRTitle,"Still locked!", llColorRed);return;
            }
            if(mode==1){
                llSendQuickEmbedMessage(gUser,sRTitle,"You have relocked yourself!", llColorRed);return;
            }
            String code=randomCode();
            llSendQuickEmbedMessage(gUser,sRTitle,"Please upload a pic with the verification code: "+code+"\nThis is to check if you locked yourself back.\nType `cancel` to cancel the  command!", llColorPurple1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same gUser, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gEvent.getAuthor()),
                    // respond, inserting the name they listed into the response
                    e -> {
                        if(e.getMessage().getContentRaw().equalsIgnoreCase("cancel")){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Command canceled", llColorRed); return;
                        }
                        List<Message.Attachment> items=e.getMessage().getAttachments();
                        if(items.isEmpty()){
                            logger.warn(cName + fName + ".no attachments");
                            llSendQuickEmbedMessage(gUser,sRTitle,"Error, no attachments!", llColorRed); return;
                        }
                        Message.Attachment item=items.get(0);
                        String fileExtension= item.getFileExtension();
                        logger.info(cName + fName + ".fileExtension="+fileExtension);
                        if(!(fileExtension.equalsIgnoreCase("jpg")||fileExtension.equalsIgnoreCase("png"))){
                            logger.warn(cName + fName + ".invalid extension");
                            llSendQuickEmbedMessage(gUser,sRTitle,"Error, invalid attachments! Needs to be jpg or png.", llColorRed);return;
                        }
                        String url=item.getUrl();
                        logger.info(cName+fName+".url="+url);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        logger.info(cName+fName+".timestamp="+timestamp.getTime());
                        putFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus, 1);//-1 unlocked 1 (re)locked
                        putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastLock, timestamp.getTime());
                        putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLink, url);
                        putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCode, code);
                        putFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerified, 0);
                        if(!saveProfile()){
                            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                        insert2Log(gUser,gGuild,"hygieneLockPending",url,code);
                        TextChannel textChannel=gTextChannel;
                        if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                            textChannel=competitionChannel;
                        }
                        EmbedBuilder embed= new EmbedBuilder();
                        embed.setColor(llColorBlue1);
                        embed.setTitle(sRTitle);
                        if(llIsValidURL(url)){
                            embed.setImage(url);
                        }else{
                            embed.setImage(llLewdNoImage);
                        }
                        String desc=gUser.getAsMention()+" has submitted their verification pic after re-locking back from hygiene opening. They are a good chastity sub. :3";
                        desc+="\nCode: "+code;
                        Long lastUnlock=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldHygieneOpening, keyHygieneOpeningLastUnlock).toString()));
                        Long lastLock=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastLock).toString()));
                        desc+="\nUnlocked: "+displaytDate(lastUnlock);
                        desc+="\nRelocked: "+displaytDate(lastLock);
                        long diff=lastLock-lastUnlock;
                        logger.info(cName+fName+".diff="+diff);
                        long minutes = (diff / 1000) / 60;
                        long seconds = (diff / 1000) % 60;
                        desc+="\nTimer: "+minutes+" minutes and "+seconds+" seconds";
                        embed.setDescription(desc);
                        llSendMessage(textChannel,embed);
                        //llQuickEmbedChannelResponse(textChannel,sRTitle,gUser.getAsMention()+" has relocked themself back.",llColorGreen);
                        llSendQuickEmbedMessage(gUser,sRTitle,"Thanks for submitting your relock picture after locking yourself back in. Good chastity sub:3", llColorGreen1);

                    },
                    // if the gUser takes more than a minute, time out
                    5, TimeUnit.MINUTES, () ->{
                        llSendQuickEmbedMessage(gUser,sRTitle,"Timeout!", llColorRed);});

        }
        private  void printAskSissygasms(){
            String fName = "[printAskSissygasms]";
            logger.info(cName + fName);
            gGlobal.sql.checkConnection();

            try{
                logger.info(cName+fName+".table="+table);
                String query = "select * from "+table+" WHERE guild_id='"+gGuild.getId()+"'";
                logger.info(cName+fName+".sql="+query);
                PreparedStatement pst = gGlobal.sql.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs=pst.executeQuery();
                logger.info(cName+fName+".executed");
                JSONArray array=new JSONArray();
                while (rs.next()){
                    Map<String,Object> row=new TreeMap<String,Object>();
                    JSONObject obj=new JSONObject();
                    obj.put("id",rs.getObject("user_id"));
                    obj.put("json",new JSONObject(rs.getString("json")));
                    array.put(obj);
                }
                logger.info(cName+fName+".array="+array.toString());
                int size=array.length();
                logger.info(cName+fName+".size="+size);
                StringBuilder desc_asking=new StringBuilder("Players asking for a sissygasm:");
                for(int i=0;i<size;i++){
                    logger.info(cName+fName+".i="+i);
                    JSONObject obj=array.getJSONObject(i);
                    Member member=gGuild.getMemberById(obj.getString("id"));
                    if(member!=null){
                        JSONObject json=obj.getJSONObject("json");
                        Boolean isRegistered=false;
                        Boolean isStart=false;
                        Boolean isVerified=false;
                        Boolean isAsking=false;
                        if(json.has(fieldStatus)) {
                            isRegistered=json.getJSONObject(fieldStatus).getBoolean(keyStatusRegister);
                            isStart=json.getJSONObject(fieldStatus).getBoolean(keyStatusStart);
                            isVerified=json.getJSONObject(fieldStatus).getBoolean(keyStatusVerified);
                        }
                        if(isVerified&&isStart&&isRegistered){
                            isAsking=json.getJSONObject(fieldSissygasmas).getBoolean(keySissygasmasPending);
                            if(isAsking){
                                desc_asking.append("\n").append(member.getAsMention());
                            }
                        }
                    }
                }
            }
            catch(Exception e){
                logger.error(cName+fName+".exception:"+e);
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to read from Db!", llColorRed);
            }
        }
        private void askSissygasms(){
            String fName = "[askSissygasms]";
            logger.info(cName + fName);
            if(!loadedProfile(gUser)){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+gUser.getAsMention()+" profile!", llColorRed); return;}
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            TextChannel textChannel=gTextChannel;
            if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                textChannel=competitionChannel;
            }
            if(!isRegistered||isKicked||isAbort||!isStart||!isVerified){
                llSendQuickEmbedMessage(gUser,sRTitle,"No playing!", llColorRed);return;
            }
            int hygieneStatus= (int)getFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus);
            if(hygieneStatus==-1){
                llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" is a naughty chastity slave as they try to ask for sissygasms while unlocked!", llColorRed);
                llSendQuickEmbedMessage(gUser,sRTitle,"Naughty chastit yslave asking for sissygasms while unlocked!", llColorRed);return;
            }
            String desc=gUser.getAsMention()+" is asking permission for a sissygasm.";
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle(sRTitle);
            Long lastAsked= Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastAsked).toString()));
            Long lastApproved=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastApproved).toString()));
            Long ltrLastDenied=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastDenied).toString()));
            if(lastAsked==0){
                desc+="\nLast asked before this: N/A.";
            }else{
                desc+="\nLast asked before this:"+displaytDate(lastAsked);
            }
            if(lastApproved==0){
                desc+="\nLast approved: Never got approved.";
            }else{
                desc+="\nLast approved:"+displaytDate(lastApproved);
            }
            if(ltrLastDenied==0){
                desc+="\nLast denied: Never got denied.";
            }else{
                desc+="\nLast denied:"+displaytDate(ltrLastDenied);
            }
            embed.setDescription(desc);
            llSendMessage(textChannel,embed);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp="+timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasLastAsked, timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasPending,true);
            putFieldEntry(fieldSissygasmas,keySissygasmasResponse,0);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
            insert2Log(gUser,gGuild,"sissgasm","","ask");
        }
        private String displaytDate(Long timeStamp){
            String fName = "[displaytDate]";
            logger.info(cName+fName+".timeStamp="+timeStamp);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH)+1;
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            String result=mYear+"."+mMonth+"."+mDay+"  "+mHour+":"+mMinute;
            logger.info(cName+fName+".result="+result);
            return result;
        }
        private void approveSissygasms(){
            String fName = "[approveSissygasms]";
            logger.info(cName + fName);
            if(!llMemberHasRole(gEvent.getMember(),roleKeyID_AvailableKeyholder)||!llMemberHasRole(gEvent.getMember(),roleKeyID_Keyholder)||!llMemberIsManager(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Keyholders and Managers only allowed to use this command!", llColorRed);
                return;
            }
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            User user=gUser;
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                loadedProfile(gUser);
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                if(!loadedProfile(member.getUser())){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+member.getAsMention()+" profile!", llColorRed); return;}
                user=member.getUser();
            }
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            TextChannel textChannel=gTextChannel;
            if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                textChannel=competitionChannel;
            }
            if(!isRegistered||isKicked||isAbort||!isStart||!isVerified){
                llSendQuickEmbedMessage(gUser,sRTitle,"No playing!", llColorRed);return;
            }
            Boolean isAsking= (Boolean) getFieldEntry(fieldSissygasmas,keySissygasmasPending);
            if(!isAsking){
                llSendQuickEmbedMessage(gUser,sRTitle,user.getAsMention()+" is not asking for a sissygasm!", llColorRed); return;}
            int hygieneStatus= (int)getFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus);
            if(hygieneStatus==-1){
                llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" is a naughty chastity slave as they unlocked while asking for a sissygasm!", llColorRed);
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp="+timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasLastApproved, timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasPending,false);
            putFieldEntry(fieldSissygasmas,keySissygasmasResponse,1);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
            Long lastAsked= Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastAsked).toString()));
            Long lastApproved=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastApproved).toString()));
            llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" is a lucky chastity slave, they are allowed to have a sissygasm.\nAsked: "+displaytDate(lastAsked)+"\nApproved: "+displaytDate(lastApproved), llColorGreen1);
            llSendQuickEmbedMessage(user,sRTitle,"Lucky chastity slave, you are allowed to have a sissygasm.", llColorGreen1);
            insert2Log(gUser,gGuild,"sissgasm","","approve");
        }
        private void rejectSissygasms(){
            String fName = "[rejectSissygasms]";
            logger.info(cName + fName);
            if(!llMemberHasRole(gEvent.getMember(),roleKeyID_AvailableKeyholder)||!llMemberHasRole(gEvent.getMember(),roleKeyID_Keyholder)||!llMemberIsManager(gEvent.getMember())){
                llSendQuickEmbedMessage(gUser,sRTitle,"Keyholders and Managers only allowed to use this command!", llColorRed);
                return;
            }
            List<Member>members=gEvent.getMessage().getMentionedMembers();
            User user=gUser;
            if(members.isEmpty()){
                logger.info(cName+fName+".me");
                loadedProfile(gUser);
            }else{
                logger.info(cName+fName+".mention");
                int size=members.size();
                logger.info(cName+fName+".size="+size);
                int i=0;
                logger.info(cName+fName+".i="+i);
                Member member=members.get(i);
                logger.info(cName+fName+".member["+i+"]:"+member.getId()+"|"+member.getEffectiveName());
                if(!loadedProfile(member.getUser())){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to load "+member.getAsMention()+" profile!", llColorRed); return;}
                user=member.getUser();
            }
            Boolean isRegistered= (Boolean) getFieldEntry(fieldStatus, keyStatusRegister);
            Boolean isKicked= (Boolean) getFieldEntry(fieldStatus, keyStatusKicked);
            Boolean isAbort= (Boolean) getFieldEntry(fieldStatus, keyStatusAbort);
            Boolean isStart= (Boolean) getFieldEntry(fieldStatus, keyStatusStart);
            Boolean isVerified= (Boolean) getFieldEntry(fieldStatus, keyStatusVerified);
            TextChannel textChannel=gTextChannel;
            if(gGuild.getId().equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)&&competitionChannel!=null){
                textChannel=competitionChannel;
            }
            if(!isRegistered||isKicked||isAbort||!isStart||!isVerified){
                llSendQuickEmbedMessage(gUser,sRTitle,"No playing!", llColorRed);return;
            }
            Boolean isAsking= (Boolean) getFieldEntry(fieldSissygasmas,keySissygasmasPending);
            if(!isAsking){
                llSendQuickEmbedMessage(gUser,sRTitle,user.getAsMention()+" is not asking for a sissygasm!", llColorRed); return;}
            int hygieneStatus= (int)getFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus);
            if(hygieneStatus==-1){
                llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" is a naughty chastity slave as they unlocked while asking for a sissygasm!", llColorRed);
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(cName+fName+".timestamp="+timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasLastDenied, timestamp.getTime());
            putFieldEntry(fieldSissygasmas,keySissygasmasPending,false);
            putFieldEntry(fieldSissygasmas,keySissygasmasResponse,-1);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
            Long lastAsked= Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastAsked).toString()));
            Long lastDenied=Long.valueOf(Objects.requireNonNull(getFieldEntry(fieldSissygasmas, keySissygasmasLastDenied).toString()));
            llSendQuickEmbedMessage(textChannel,sRTitle,gUser.getAsMention()+" is an unlucky chastity slave, they are denied to have a sissygasm.\nAsked: "+displaytDate(lastAsked)+"\nDenied: "+displaytDate(lastDenied), llColorPurple1);
            llSendQuickEmbedMessage(user,sRTitle,"Poor you. You are denied to have an sissygasm even when you asked.", llColorPurple1);
            insert2Log(gUser,gGuild,"sissgasm","","reject");
        }

        private void help( String command) {
            String fName = "[help]";
            logger.info(cName + fName);
            logger.info(cName + fName + "command=" + command);
            String desc="N/a";
            String quickSummonWithSpace=llPrefixStr+"ccompetition ";
            if(command.equalsIgnoreCase("hygiene")){
                desc="As partaking in the comopetition you need to iform when you unlock and lock back.";
                desc+="**You should only unlock for cleaning, changing cage, visits ect.\nYou should not unlock for your own sexual pleasure!**";
                desc+="\n`"+quickSummonWithSpace+"hygiene unlock` inform the server that you are unlocked for cleaning";
                desc+="\n`"+quickSummonWithSpace+"hygiene lock` inform the server that you are locked back and uploaded a verification picture";
            }else
            if(command.equalsIgnoreCase("join")){
                desc="Joining the competition\nFirst you do the register and when locked and ready do the start";
                desc+="\n`"+quickSummonWithSpace+"register` to register for the competition";
                desc+="\n`"+quickSummonWithSpace+"start` to start competition, meaning you locked yourself";
                desc+="\n`"+quickSummonWithSpace+"getstart <@User>` view yours or the mentioned one starting verification image";
                if(llMemberIsStaff(gEvent.getMember())){
                    desc+="\n`"+quickSummonWithSpace+"acceptstart <@User>` accept starting(first) verification";
                    desc+="\n`"+quickSummonWithSpace+"rejectstart <@User>` reject starting(first) verification";
                }
            }else
            if(command.equalsIgnoreCase("verification")){
                desc="In development";
                //desc+="\n`"+quickSummonWithSpace+"verification has <@User>` check yours or the mentioned one has verification to perform";
            }else
            if(command.equalsIgnoreCase("sissygasm")){
                desc="Sissygasm asking, approving and rejecting";
                desc+="\n`"+quickSummonWithSpace+"sissygasm ask` you ask for a sissygasm";
                desc+="\n`"+quickSummonWithSpace+"sissygasm list` list players who are asking for a sissygasm";
                if(llMemberIsStaff(gEvent.getMember())){
                    desc+="\n`"+quickSummonWithSpace+"sissygasm approve <@User>` you ask for a sissygasm";
                    desc+="\n`"+quickSummonWithSpace+"sissygasm reject <@User>` you ask for a sissygasm";
                }
            }else{
                desc="`"+quickSummonWithSpace+"list` get a list of users partaking";
                desc+="\n`"+quickSummonWithSpace+"getstart <@User>` view yours or the mentioned one starting verification image";
                desc+="\n`"+quickSummonWithSpace+"help hygiene` help for hygiene opening branch";
                desc+="\n`"+quickSummonWithSpace+"help sissygasm` help forsissygasm opening branch";
            }
            llSendQuickEmbedMessage(gUser,sRTitle,desc, llColorRed);
        }
        String fieldStatus="status";
        String keyStatusRegister ="registered";String keyStatusKicked ="kicked";String kickedReason="kickedreason";String keyStatusStart ="start"; String keyStatusAbort ="abort";String keyStatusActive ="active";String keyStatusVerified ="verified";

        String fieldTimeStamp="time";
        String registeredTimeStamp="r";String starTimeStamp="s"; String endTimeStamp="e";String abordTimeStamp="a";String kickTimeStamp="k";

        String fieldSissygasmas="sissygasmas";
        String keySissygasmasPending ="pending"; String keySissygasmasResponse ="response";String keySissygasmasLastAsked ="lastrequest";String keySissygasmasLastApproved ="lastaccept";String keySissygasmasLastDenied ="lastdenied";

        String fieldSissygasmaLog="sissygasmasLog";
        String logReqissygasmas="request";String logAcceptedSissygasmas="accept";String logDeniedSissygasmas="denied";

        String fieldVerificationStart="verificationStart";
        String hasDoneStartVerification="start";String linkStartVerification="link";String codeStartVerification="code";String statusStartVerification ="status";
        String fieldVerification="verification";
        String hasReqVerification="req";String hasDoneVerification="done";String linkVerification="link";String codeVerification="code";String reqVerificationTimeStamp="reqtime";String doneVerificationTimeStamp ="donetime";String statusVerification ="status";
        String timesReqVerification="timesreq";;String timesDoneVerification="timesdone";

        String fieldHygieneOpening="hygieneopening";
        String keyHygieneOpeningMode="mode";String keyHygieneOpeningAsking="asking";String keyHygieneOpeningStatus="status";String keyHygieneOpeningCountAsk="countask";String keyHygieneOpeningCountUnlocks="countunlock";String keyHygieneOpeningLastAsked="lastask";String keyHygieneOpeningLastUnlock="lastunlock";String keyHygieneOpeningLastLock="lastlock";
        String keyHygieneOpeningVerificationRequest="vrequest";String keyHygieneOpeningLink="link";String keyHygieneOpeningCode="code";String keyHygieneOpeningVerified ="verified";

        String fieldLogCount="logCount";
        String logRegister="register";String logUnRegister="unregister";String logStart="start";String logStop="stop";String logAbort="abort";String logKick="kick";
        private void resetUserProfileEntry(){
            String fName="[resetUserProfileEntry]";
            logger.info(cName+fName+".reseting or creating");
            createFieldEntry(fieldStatus);
            putFieldEntry(fieldStatus, keyStatusRegister,false);
            putFieldEntry(fieldStatus, keyStatusActive,false);
            putFieldEntry(fieldStatus, keyStatusStart,false);
            putFieldEntry(fieldStatus, keyStatusAbort,false);
            putFieldEntry(fieldStatus, keyStatusKicked,false);
            putFieldEntry(fieldStatus, keyStatusVerified,false);
            putFieldEntry(fieldStatus,kickedReason,"n/a");
            createFieldEntry(fieldTimeStamp);
            putFieldEntry(fieldTimeStamp,registeredTimeStamp,0);
            putFieldEntry(fieldTimeStamp,starTimeStamp, 0);
            putFieldEntry(fieldTimeStamp,endTimeStamp, 0);
            putFieldEntry(fieldTimeStamp,kickTimeStamp, 0);
            putFieldEntry(fieldTimeStamp,abordTimeStamp, 0);
            createFieldEntry(fieldSissygasmas);
            putFieldEntry(fieldSissygasmas, keySissygasmasPending, false);
            putFieldEntry(fieldSissygasmas, keySissygasmasResponse,0);
            putFieldEntry(fieldSissygasmas, keySissygasmasLastAsked,0);
            putFieldEntry(fieldSissygasmas, keySissygasmasLastApproved,0);
            putFieldEntry(fieldSissygasmas, keySissygasmasLastDenied,0);
            createFieldEntry(fieldSissygasmaLog);
            putFieldEntry(fieldSissygasmaLog,logReqissygasmas,0);
            putFieldEntry(fieldSissygasmaLog,logAcceptedSissygasmas,0);
            putFieldEntry(fieldSissygasmaLog,logDeniedSissygasmas,0);
            createFieldEntry(fieldVerificationStart);
            putFieldEntry(fieldVerificationStart,hasDoneStartVerification, false);
            putFieldEntry(fieldVerificationStart,linkStartVerification, "");
            putFieldEntry(fieldVerificationStart,codeStartVerification, "");
            putFieldEntry(fieldVerificationStart, statusStartVerification, 0);
            createFieldEntry(fieldVerification);
            putFieldEntry(fieldVerification,hasDoneVerification, false);
            putFieldEntry(fieldVerification,hasReqVerification, false);
            putFieldEntry(fieldVerification,linkVerification, "");
            putFieldEntry(fieldVerification,codeVerification, "");
            putFieldEntry(fieldVerification, statusVerification, 0);
            putFieldEntry(fieldVerification,reqVerificationTimeStamp, 0);
            putFieldEntry(fieldVerification, doneVerificationTimeStamp, 0);
            putFieldEntry(fieldVerification,timesReqVerification, 0);
            putFieldEntry(fieldVerification,timesDoneVerification, 0);
            //new 1.03.20
            createFieldEntry(fieldHygieneOpening);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningMode, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningAsking, false);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus, 0);//-1 unlocked 1 (re)locked
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerificationRequest, true);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountAsk, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountUnlocks, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastAsked, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastLock, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastUnlock, 0);
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLink, "");
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningCode, "");
            putFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerified, 0);
        }
        private void safetyUserProfileEntry(){
            String fName="[safetyUserProfileEntry]";
            logger.info(cName+fName+".safety check");
            safetyCreateFieldEntry(fieldStatus);
            safetyPutFieldEntry(fieldStatus, keyStatusRegister,false);
            safetyPutFieldEntry(fieldStatus, keyStatusActive,false);
            safetyPutFieldEntry(fieldStatus, keyStatusStart,false);
            safetyPutFieldEntry(fieldStatus, keyStatusAbort,false);
            safetyPutFieldEntry(fieldStatus, keyStatusKicked,false);
            safetyPutFieldEntry(fieldStatus, keyStatusVerified,false);
            safetyPutFieldEntry(fieldStatus,kickedReason,"n/a");
            safetyCreateFieldEntry(fieldTimeStamp);
            safetyPutFieldEntry(fieldTimeStamp,registeredTimeStamp,0);
            safetyPutFieldEntry(fieldTimeStamp,starTimeStamp, 0);
            safetyPutFieldEntry(fieldTimeStamp,endTimeStamp, 0);
            safetyPutFieldEntry(fieldTimeStamp,kickTimeStamp, 0);
            safetyPutFieldEntry(fieldTimeStamp,abordTimeStamp, 0);
            safetyCreateFieldEntry(fieldSissygasmas);
            safetyPutFieldEntry(fieldSissygasmas, keySissygasmasPending, false);
            safetyPutFieldEntry(fieldSissygasmas, keySissygasmasResponse,0);
            safetyPutFieldEntry(fieldSissygasmas, keySissygasmasLastAsked,0);
            safetyPutFieldEntry(fieldSissygasmas, keySissygasmasLastApproved,0);
            safetyPutFieldEntry(fieldSissygasmas, keySissygasmasLastDenied,0);
            safetyCreateFieldEntry(fieldSissygasmaLog);
            safetyPutFieldEntry(fieldSissygasmaLog,logReqissygasmas,0);
            safetyPutFieldEntry(fieldSissygasmaLog,logAcceptedSissygasmas,0);
            safetyPutFieldEntry(fieldSissygasmaLog,logDeniedSissygasmas,0);
            safetyCreateFieldEntry(fieldVerificationStart);
            safetyPutFieldEntry(fieldVerificationStart,hasDoneStartVerification, false);
            safetyPutFieldEntry(fieldVerificationStart,linkStartVerification, "");
            safetyPutFieldEntry(fieldVerificationStart,codeStartVerification, "");
            safetyPutFieldEntry(fieldVerificationStart, statusStartVerification, 0);
            safetyCreateFieldEntry(fieldVerification);
            safetyPutFieldEntry(fieldVerification,hasDoneVerification, false);
            safetyPutFieldEntry(fieldVerification,hasReqVerification, false);
            safetyPutFieldEntry(fieldVerification,linkVerification, "");
            safetyPutFieldEntry(fieldVerification,codeVerification, "");
            safetyPutFieldEntry(fieldVerification, statusVerification, 0);
            safetyPutFieldEntry(fieldVerification,reqVerificationTimeStamp, 0);
            safetyPutFieldEntry(fieldVerification, doneVerificationTimeStamp, 0);
            safetyPutFieldEntry(fieldVerification,timesReqVerification, 0);
            safetyPutFieldEntry(fieldVerification,timesDoneVerification, 0);
            //new 1.03.20
            safetyCreateFieldEntry(fieldHygieneOpening);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningMode, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningAsking, false);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningStatus, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerificationRequest, true);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountAsk, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningCountUnlocks, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastAsked, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastLock, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningLastUnlock, 0);
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningLink, "");
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningCode, "");
            safetyPutFieldEntry(fieldHygieneOpening,keyHygieneOpeningVerified, 0);
        }
        private void creategUserDefaultEntryOrSafetyCheck(){
            String fName="[creategUserDefaultEntryOrSafetyCheck]";
            if(!gUserProfile.isProfile()){
                logger.info(cName+fName+".creating default profile");
                resetUserProfileEntry();
            }else{
                logger.info(cName+fName+".already has");
                safetyUserProfileEntry();
            }
        }
        private Boolean createFieldEntry(String field){
            String fName="[createFieldEntry]";
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName+fName+".field="+field);
            if(gUserProfile.createFieldEntry(field)){
                logger.info(cName + fName + ".success");return  true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }
        private Boolean safetyCreateFieldEntry(String field){
            String fName="[safetyCreateFieldEntry]";
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName+fName+".field="+field);
            if(gUserProfile.hasFieldEntry(field)){
                logger.info(cName + fName + ".ignore");return  true;
            }
            if(gUserProfile.createFieldEntry(field)){
                logger.info(cName + fName + ".success");return  true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }
        private Boolean putFieldEntry(String field,String name, Object value){
            String fName="[putFieldEntry]";
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            if(gUserProfile.putFieldEntry(field,name,value)){
                logger.info(cName + fName + ".success");return  true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }
        private Boolean safetyPutFieldEntry(String field,String name, Object value){
            String fName="[safetyPutFieldEntry]";
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            logger.info(cName+fName+".value="+value);
            if(gUserProfile.hasFieldEntry(field,name)){
                logger.info(cName + fName + ".ignore");return  true;
            }
            if(gUserProfile.putFieldEntry(field,name,value)){
                logger.info(cName + fName + ".success");return  true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }
        private Object getFieldEntry(String field,String name){
            String fName="[getFieldEntry]";
            logger.info(cName+fName+".field="+field);
            logger.info(cName+fName+".name="+name);
            Object tmp=gUserProfile.getFieldEntry(field,name);
            if(tmp!=null){
                logger.info(cName + fName + ".success");
                return tmp;
            }
            logger.warn(cName + fName + ".failed");
            return null;
        }

        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            if(gUserProfile.saveProfile(table)){
                logger.info(cName + fName + ".success");return  true;
            } 
            logger.warn(cName + fName + ".failed");return false;
        }
        private Boolean loadedProfile(User user){
            String fName="[loadedProfile]";
            logger.info(cName+fName);
            logger.info(cName + fName + ".user:"+ user.getId()+"|"+user.getName());
            gUserProfile=new lcJSONUserProfile(gGlobal,user,gGuild);
            if(gUserProfile.getProfile(table)){
                logger.info(cName + fName + ".success");
                safetyUserProfileEntry();
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed);}
                return true;
            }
            creategUserDefaultEntryOrSafetyCheck();
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return false;}
            if(gUserProfile.isProfile()){
                logger.info(cName + fName + ".success");return true;
            }
            logger.warn(cName + fName + ".failed");return false;
        }
        private String randomCode(){
            String fName="[randomCode]";
            // length is bounded by 256 Character
            int n=6;
            byte[] array = new byte[256];
            new Random().nextBytes(array);

            String randomString
                    = new String(array, StandardCharsets.UTF_8);

            // Create a StringBuffer to store the result
            StringBuffer r = new StringBuffer();

            // remove all spacial char
            String  AlphaNumericString
                    = randomString
                    .replaceAll("[^A-Za-z0-9]", "");

            // Append first 20 alphanumeric characters
            // from the generated random String into the result
            for (int k = 0; k < AlphaNumericString.length(); k++) {

                if (Character.isLetter(AlphaNumericString.charAt(k))
                        && (n > 0)
                        || Character.isDigit(AlphaNumericString.charAt(k))
                        && (n > 0)) {

                    r.append(AlphaNumericString.charAt(k));
                    n--;
                }
            }

            // return the resultant string
            return r.toString();
        }
        private void insert2Log(User user, Guild guild,String type, String file,String code){
            String fName="[insert2Log]";
            //type: Request,Pending, Accepted, Rejected
            logger.info(cName+fName);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return;
            }

            logger.info(cName + fName + ".type="+type);
            logger.info(cName + fName + ".file="+file);
            logger.info(cName + fName + ".code="+code);
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put("guild_id",guild.getId());
            data.put("user_id", user.getId());
            data.put("type", type);
            data.put("src", file); data.put("code", code);
            if (!gGlobal.sql.insert(sqlLog, data)) {
                logger.error(cName + fName + ".error while inserting");
            }else{
                logger.warn(cName + fName + ".success insert");
            }

        }
    }

}
