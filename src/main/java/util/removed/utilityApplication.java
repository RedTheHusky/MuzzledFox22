package util.removed;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class utilityApplication extends Command implements  llGlobalHelper {
    String cName="[utility]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="application";
    public utilityApplication(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Application-Utility";
        this.help = "debug";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility; this.hidden=true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel; String gTitle="AutoRoles";private Message gMessage;

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }


        @Override
        public void run() {
            String fName = "[run]";
            logger.info(fName);
            try {
                boolean isInvalidCommand = true;
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(fName + ".Args");

                    initProfile();
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items[0].equalsIgnoreCase("help")) {
                        if(items.length>=2){
                            help(items[1]);
                        }else{
                            help("main");
                        }
                        isInvalidCommand = false;
                    }else
                    if (items.length>=2) {
                        if ((items[0].equalsIgnoreCase("register")&&items[1].equalsIgnoreCase("set"))||(items[1].equalsIgnoreCase("register")&&items[0].equalsIgnoreCase("set"))) {
                            setRegister();
                            isInvalidCommand = false;
                        }else
                        if ((items[0].equalsIgnoreCase("register")&&items[1].equalsIgnoreCase("new"))||(items[1].equalsIgnoreCase("register")&&items[0].equalsIgnoreCase("new"))) {
                            newRegister();
                            isInvalidCommand = false;
                        }
                        if ((items[0].equalsIgnoreCase("register")&&items[1].equalsIgnoreCase("preview"))||(items[1].equalsIgnoreCase("register")&&items[0].equalsIgnoreCase("preview"))) {
                            previewRegister();
                            isInvalidCommand = false;
                        }
                        if ((items[0].equalsIgnoreCase("register")&&items[1].equalsIgnoreCase("do"))||(items[1].equalsIgnoreCase("register")&&items[0].equalsIgnoreCase("do"))) {
                            entryPreviewI=0;entryPreviewQ=0;doRegister();
                            isInvalidCommand = false;
                        }
                    }

                }
                logger.info(fName + ".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = "";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            if(lsCustomGuilds.lsIsCustomAdultGuild4Red(gGuild)){
                embed.addField("In house","`"+quickSummonWithSpace+"addbasicroles`",false);
            }
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        private void setRegister() {
            String fName = "[setRegister]";
            logger.info(fName);
            try {
               jsonApplication_Name="register";
                JSONObject jsonObject=gProfile.jsonObject.getJSONObject(keyApplications);
                if(jsonObject.has(jsonApplication_Name)){
                    entryCommand="edit";jsonApplication=jsonObject.getJSONObject(jsonApplication_Name);entryPreview();
                }else{
                    entryCommand="new";entryNew();entryAddingTitle();
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void newRegister() {
            String fName = "[newRegister]";
            logger.info(fName);
            try {
                jsonApplication_Name="register";
                entryCommand="new";entryNew();
                entryAddingTitle();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void previewRegister() {
            String fName = "[previewRegister]";
            logger.info(fName);
            try {
                jsonApplication_Name="register";
                JSONObject jsonObject=gProfile.jsonObject.getJSONObject(keyApplications);
                if(jsonObject.has(jsonApplication_Name)){
                    entryCommand="preview";jsonApplication=jsonObject.getJSONObject(jsonApplication_Name);
                    entryPreviewI=0;entryPreviewQ=0;
                    entryPreview();
                }else{

                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        JSONObject jsonResponses=new JSONObject();
        String keyUserId="userid",keyUserName="userName";
        private void doRegister() {
            String fName = "[doRegister]";
            logger.info(fName);
            try {
                jsonApplication_Name="register";
                JSONObject jsonObject=gProfile.jsonObject.getJSONObject(keyApplications);
                if(jsonObject.has(jsonApplication_Name)){
                    entryCommand="do";jsonApplication=jsonObject.getJSONObject(jsonApplication_Name);
                    EmbedBuilder embed=new EmbedBuilder();
                    embed.setColor(llColors.llColorBlue1);
                    embed.setTitle(jsonApplication.getString(keyTitle));
                    JSONObject jsonObject1=jsonApplication.getJSONObject(keyApplications);JSONObject jsonObject2=new JSONObject();
                    if(jsonObject1.has(keyPending)){
                        jsonObject2=jsonObject1.getJSONObject(keyPending);
                    }
                    if(jsonApplication.has(keyQuestions)){
                        jsonApplication_Questions=jsonApplication.getJSONArray(keyQuestions);
                    }

                    if(entryPreviewI==0){
                        waithBranch="app.int";
                        embed.setDescription(jsonApplication.getString(keyDescription));
                        jsonResponses.put(keyUserId,gMember.getIdLong());
                        jsonResponses.put(keyUserName,gUser.getName()+"#"+gUser.getDiscriminator());
                        if(!jsonApplication_Questions.isEmpty())embed.addField("Questions count", String.valueOf(jsonApplication_Questions.length()),false);
                        embed.addField("Do you wish to continue?",""+gGlobal.emojis.getEmoji("x")+" no, "+gGlobal.emojis.getEmoji("white_check_mark")+" yes.",false);
                    }
                    if(entryPreviewI==1){
                        waithBranch="app.answer";
                        embed.setDescription(jsonApplication_Questions.getString(entryPreviewQ));
                    }
                    if(entryPreviewI==2){
                        waithBranch="app.submit";
                        embed.setDescription("Do you wish to submit your responses?");
                    }
                    Message message= lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                    if(entryPreviewI==0||entryPreviewI==2) {
                        message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                    }
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                    waithId=message.getIdLong();
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&waithBranch.equalsIgnoreCase("app.answer")),
                            e -> {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                lsMessageHelper.lsMessageDelete(message);
                                jsonResponses.put(String.valueOf(entryPreviewQ),content);
                                entryPreviewQ++;
                                if (entryPreviewQ >= jsonApplication_Questions.length()) {
                                    entryPreviewI = 2;
                                }
                                doRegister();
                            },15, TimeUnit.MINUTES, () -> {

                            });
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&message.getId().equalsIgnoreCase(e.getMessageId())),
                            e -> {
                                String name=e.getReactionEmote().getName();
                                logger.info(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.info(fName+"asCodepoints="+asCodepoints);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Aborted",llColors.llColorRed);
                                    waithBranch="";waithId=0;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    waithBranch="";waithId=0;
                                    if(entryPreviewI==0){
                                        entryPreviewI=1;entryPreviewQ=0;doRegister();
                                    }
                                    if(entryPreviewI==2){
                                        if(jsonObject1.has(keyPending)){
                                            /*jsonObject2=jsonObject1.getJSONObject(keyPending);
                                            jsonObject2.put(gUser.getId(),jsonResponses);
                                            jsonObject1.put(keyPending,jsonObject2);*/
                                        }
                                        jsonApplication.put(keyApplications,jsonObject1);
                                        entrySave();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Submitted your responses.",llColors.llColorGreen1);
                                    }
                                }
                            },15, TimeUnit.MINUTES, () -> {
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                            });
                }else{

                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        JSONObject jsonApplication; int jsonApplication_Index=-1;String jsonApplication_Name="";
        JSONArray jsonApplication_Questions=new JSONArray(),jsonApplication_RolesRequired=new JSONArray(),jsonApplication_RolesReject=new JSONArray(),jsonApplication_RolesGiveAway=new JSONArray(),jsonApplication_RolesReview=new JSONArray();
        JSONObject jsonApplication_InWaiting=new JSONObject(); JSONArray jsonApplication_Accepted=new JSONArray(), jsonApplication_Rejected=new JSONArray();
        String keyTitle="title",keyQuestions="questions",keyRolesRequired="rolesRequired",keyRolesReject="rolesReject",keyRolesReview="rolesReview",keyRolesGiveAway="rolesGiveAway",keyDescription="description",keyAccepted="accepted",keyDenied="rejected",keyPending="pending";
        private void entryNew() {
            String fName = "[entryNew]";
            logger.info(fName);
            try {
                jsonApplication=new JSONObject();
                jsonApplication.put(keyTitle,"");jsonApplication.put(keyDescription,"");
                jsonApplication.put(keyQuestions,new JSONArray());jsonApplication.put(keyAccepted,"");jsonApplication.put(keyDenied,"");
                jsonApplication.put(keyRolesRequired,new JSONArray()); jsonApplication.put(keyRolesReject,new JSONArray()); jsonApplication.put(keyRolesGiveAway,new JSONArray());jsonApplication.put(keyRolesReview,new JSONArray());
                jsonApplication.put(keyEnabled,false);

                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyAccepted,new JSONArray());
                jsonObject.put(keyDenied,new JSONArray());
                jsonObject.put(keyPending,new JSONObject());
                jsonApplication.put(keyApplications,jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entrySave() {
            String fName = "[entrySave]";
            logger.info(fName);
            try {
                logger.info(fName+"jsonApplication="+jsonApplication.toString());
                logger.info(fName+"jsonApplication_Name="+jsonApplication_Name);
                JSONObject jsonObject=gProfile.jsonObject.getJSONObject(keyApplications);
                jsonObject.put(jsonApplication_Name,jsonApplication);
                gProfile.jsonObject.put(keyApplications,jsonObject);
                saveProfile();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String entryCommand="", waithBranch="";long waithId=0;
        private void entryAddingTitle() {
            String fName = "[entryAddingDescription]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Please enter the application title.");
                if(jsonApplication.has(keyTitle)&&!jsonApplication.isNull(keyTitle)&&!jsonApplication.getString(keyTitle).isBlank()){
                    embed.addField("Preview",jsonApplication.getString(keyTitle),false);
                }
                embed.addField("Options","When finished, use "+gGlobal.emojis.getEmoji("white_check_mark")+".",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="title";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("title")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            jsonApplication.put(keyTitle,content);
                            lsMessageHelper.lsMessageDelete(message);
                            entryAddingTitle();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyTitle,"");
                                entryAddingTitle();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingDescription();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingDescription() {
            String fName = "[entryAddingDescription]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Please enter the application description.");
                if(jsonApplication.has(keyDescription)&&!jsonApplication.isNull(keyDescription)&&!jsonApplication.getString(keyDescription).isBlank()){
                    embed.addField("Preview",jsonApplication.getString(keyDescription),false);
                }
                embed.addField("Options","When finished or want to skip, use "+gGlobal.emojis.getEmoji("white_check_mark")+".",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="desc";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("desc")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            jsonApplication.put(keyDescription,content);
                            lsMessageHelper.lsMessageDelete(message);
                            entryAddingDescription();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingRolesRequired();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingRolesRequired() {
            String fName = "[entryAddingRolesRequired]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Mention the roles you want to add in required roles list. Note that the member would only need one of this roles, not all the roles.");
                if(!jsonApplication_RolesRequired.isEmpty()){
                    StringBuilder roleMentions= new StringBuilder();
                    for(int i=0;i<jsonApplication_RolesRequired.length();i++){
                        logger.info(fName+"role["+i+"]="+jsonApplication_RolesRequired.getString(i));
                        Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesRequired.getString(i));
                        if(role!=null){
                            logger.info(fName+"role["+i+"].is valid="+role.getName());
                            roleMentions.append(" ").append(role.getAsMention());
                        }
                    }
                    embed.addField("Preview", String.valueOf(roleMentions),false);
                }

                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove the roles.\nWhen finished adding the roles, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to save it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_RolesRequired.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="rolerequired";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("rolerequired")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            List<Role>roles=e.getMessage().getMentionedRoles();
                            logger.info(fName+".roles="+roles.size());
                            for(int i=0;i<roles.size();i++){
                                Role role=roles.get(i);
                                logger.info(fName+".role["+i+"]="+role.getName()+"("+role.getId()+")");
                                jsonApplication_RolesRequired.put(role.getId());
                            }

                            lsMessageHelper.lsMessageDelete(message);entryAddingRolesRequired();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                jsonApplication_RolesRequired=new JSONArray();
                                lsMessageHelper.lsMessageDelete(message);entryAddingRolesRequired();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyRolesRequired,jsonApplication_RolesRequired);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingRolesReject();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingRolesReject() {
            String fName = "[entryAddingRolesReject]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Mention the roles you want to add in reject roles list. Note that the member would only need one of this roles, not all the roles.");
                if(!jsonApplication_RolesReject.isEmpty()){
                    StringBuilder roleMentions= new StringBuilder();
                    for(int i=0;i<jsonApplication_RolesReject.length();i++){
                        logger.info(fName+"role["+i+"]="+jsonApplication_RolesReject.getString(i));
                        Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesReject.getString(i));
                        if(role!=null){
                            logger.info(fName+"role["+i+"].is valid="+role.getName());
                            roleMentions.append(" ").append(role.getAsMention());
                        }
                    }
                    embed.addField("Preview", String.valueOf(roleMentions),false);
                }

                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove the roles.\nWhen finished adding the roles, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to save it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_RolesReject.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="rolereject";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("rolereject")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            List<Role>roles=e.getMessage().getMentionedRoles();
                            logger.info(fName+".roles="+roles.size());
                            for(int i=0;i<roles.size();i++){
                                Role role=roles.get(i);
                                logger.info(fName+".role["+i+"]="+role.getName()+"("+role.getId()+")");
                                jsonApplication_RolesReject.put(role.getId());
                            }

                            lsMessageHelper.lsMessageDelete(message);entryAddingRolesReject();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                jsonApplication_RolesReject=new JSONArray();
                                lsMessageHelper.lsMessageDelete(message);entryAddingRolesReject();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyRolesReject,jsonApplication_RolesReject);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingQuestions(-1);
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingQuestions(int index) {
            String fName = "[entryAddingQuestions]";
            logger.info(fName);
            try {
                logger.info(fName+".index="+index);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Post the questions separately in invidual messages.");
                embed.addField("Questions count", String.valueOf(jsonApplication_Questions.length()),false);
                if(!jsonApplication_Questions.isEmpty()&&index>=0&&index<jsonApplication_Questions.length()){
                    embed.addField("Added question",jsonApplication_Questions.getString(index),false);
                }
                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove last added question.\nUse "+gGlobal.emojis.getEmoji("bomb")+" to remove all questions.\nWhen finished adding questions, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to end it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_Questions.isEmpty()&&index>=0&&index<jsonApplication_Questions.length()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                if(!jsonApplication_Questions.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji("bomb")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="questions";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("questions")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            jsonApplication_Questions.put(content);
                            int index2=jsonApplication_Questions.length()-1;
                            logger.info(fName+".index="+index2);
                            entryAddingQuestions(index2);
                            },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication_Questions=new JSONArray();
                                entryAddingQuestions(-1);
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication_Questions.remove(index);
                                entryAddingQuestions(-1);
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyQuestions,jsonApplication_Questions);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingAcceptedMessage();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingAcceptedMessage() {
            String fName = "[entryAddingAcceptedMessage]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Please enter the application accepted message.");
                if(jsonApplication.has(keyAccepted)&&!jsonApplication.isNull(keyAccepted)&&!jsonApplication.getString(keyAccepted).isBlank()){
                    embed.addField("Preview",jsonApplication.getString(keyAccepted),false);
                }
                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove the message.\nWhen finished or want to skip, use "+gGlobal.emojis.getEmoji("white_check_mark")+".",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(jsonApplication.has(keyAccepted)&&!jsonApplication.isNull(keyAccepted)&&!jsonApplication.getString(keyAccepted).isBlank()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="acceptedmessage";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("acceptedmessage")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            jsonApplication.put(keyAccepted,content);
                            lsMessageHelper.lsMessageDelete(message);
                            entryAddingAcceptedMessage();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyAccepted,"");
                                entryAddingAcceptedMessage();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingRolesGiveAway();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingRolesGiveAway() {
            String fName = "[entryAddingRolesGiveAway]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Mention the roles you want to give to the member once application accepted.");
                if(!jsonApplication_RolesGiveAway.isEmpty()){
                    StringBuilder roleMentions= new StringBuilder();
                    for(int i=0;i<jsonApplication_RolesGiveAway.length();i++){
                        logger.info(fName+"role["+i+"]="+jsonApplication_RolesGiveAway.getString(i));
                        Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesGiveAway.getString(i));
                        if(role!=null){
                            logger.info(fName+"role["+i+"].is valid="+role.getName());
                            roleMentions.append(" ").append(role.getAsMention());
                        }
                    }
                    embed.addField("Preview", String.valueOf(roleMentions),false);
                }

                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove the roles.\nWhen finished adding the roles, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to save it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_RolesReject.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="rolesgive";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("rolesgive")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            List<Role>roles=e.getMessage().getMentionedRoles();
                            logger.info(fName+".roles="+roles.size());
                            for(int i=0;i<roles.size();i++){
                                Role role=roles.get(i);
                                logger.info(fName+".role["+i+"]="+role.getName()+"("+role.getId()+")");
                                jsonApplication_RolesGiveAway.put(role.getId());
                            }

                            lsMessageHelper.lsMessageDelete(message);entryAddingRolesGiveAway();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                jsonApplication_RolesGiveAway=new JSONArray();
                                lsMessageHelper.lsMessageDelete(message);entryAddingRolesGiveAway();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyRolesGiveAway,jsonApplication_RolesGiveAway);
                                entrySave();waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                    entryAddingRolesReview();
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryAddingRolesReview() {
            String fName = "[entryAddingRolesReview]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Mention the roles that is required to review the application.");
                if(!jsonApplication_RolesReview.isEmpty()){
                    StringBuilder roleMentions= new StringBuilder();
                    for(int i=0;i<jsonApplication_RolesReview.length();i++){
                        logger.info(fName+"role["+i+"]="+jsonApplication_RolesReview.getString(i));
                        Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesReview.getString(i));
                        if(role!=null){
                            logger.info(fName+"role["+i+"].is valid="+role.getName());
                            roleMentions.append(" ").append(role.getAsMention());
                        }
                    }
                    embed.addField("Preview", String.valueOf(roleMentions),false);
                }

                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove the roles.\nWhen finished adding the roles, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to save it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_RolesReview.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="rolesreview";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("rolesreview")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            List<Role>roles=e.getMessage().getMentionedRoles();
                            logger.info(fName+".roles="+roles.size());
                            for(int i=0;i<roles.size();i++){
                                Role role=roles.get(i);
                                logger.info(fName+".role["+i+"]="+role.getName()+"("+role.getId()+")");
                                jsonApplication_RolesReview.put(role.getId());
                            }

                            lsMessageHelper.lsMessageDelete(message);entryAddingRolesReview();
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                jsonApplication_RolesReview=new JSONArray();
                                lsMessageHelper.lsMessageDelete(message);entryAddingRolesReview();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyRolesReview,jsonApplication_RolesReview);
                                entrySave(); waithBranch="";waithId=0;
                                if(entryCommand.equalsIgnoreCase("new")){
                                   lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"New Application added", llColors.llColorGreen1);
                                }
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    entryPreview();
                                }
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        int entryPreviewI=0,entryPreviewQ=0;
        private void entryPreview() {
            String fName = "[entryPreview]";
            logger.info(fName);
            try {
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                if(entryPreviewI==0){//main
                    if(jsonApplication.has(keyTitle)&&!jsonApplication.isNull(keyTitle)&&!jsonApplication.getString(keyTitle).isBlank()){
                        embed.addField("Title",jsonApplication.getString(keyTitle),false);
                    }else{
                        embed.addField("Title","(none)",false);
                    }
                    if(jsonApplication.has(keyQuestions)&&!jsonApplication.isNull(keyQuestions)){
                        jsonApplication_Questions=jsonApplication.getJSONArray(keyQuestions);
                    }
                    if(jsonApplication.has(keyRolesRequired)&&!jsonApplication.isNull(keyRolesRequired)){
                        jsonApplication_RolesRequired=jsonApplication.getJSONArray(keyRolesRequired);
                    }
                    if(jsonApplication.has(keyRolesReject)&&!jsonApplication.isNull(keyRolesReject)){
                        jsonApplication_RolesReject=jsonApplication.getJSONArray(keyRolesReject);
                    }
                    if(jsonApplication.has(keyRolesGiveAway)&&!jsonApplication.isNull(keyRolesGiveAway)){
                        jsonApplication_RolesGiveAway=jsonApplication.getJSONArray(keyRolesGiveAway);
                    }
                    if(jsonApplication.has(keyRolesReview)&&!jsonApplication.isNull(keyRolesReview)){
                        jsonApplication_RolesReview=jsonApplication.getJSONArray(keyRolesReview);
                    }
                    if(jsonApplication.has(keyApplications)&&!jsonApplication.isNull(keyApplications)){
                        JSONObject jsonObject=jsonApplication.getJSONObject(keyApplications);
                        if(jsonObject.has(keyAccepted)&&!jsonObject.isNull(keyAccepted)){
                            jsonApplication_Accepted=jsonObject.getJSONArray(keyAccepted);
                        }
                        if(jsonObject.has(keyDenied)&&!jsonObject.isNull(keyDenied)){
                            jsonApplication_Rejected=jsonObject.getJSONArray(keyDenied);
                        }
                        if(jsonObject.has(keyPending)&&!jsonObject.isNull(keyPending)){
                            jsonApplication_InWaiting=jsonObject.getJSONObject(keyPending);
                        }
                    }

                    embed.addField("Questions", String.valueOf(jsonApplication_Questions.length()),false);
                    if(!jsonApplication_RolesRequired.isEmpty()){
                        StringBuilder roleMentions= new StringBuilder();
                        for(int i=0;i<jsonApplication_RolesRequired.length();i++){
                            logger.info(fName+"role["+i+"]="+jsonApplication_RolesRequired.getString(i));
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesRequired.getString(i));
                            if(role!=null){
                                logger.info(fName+"role["+i+"].is valid="+role.getName());
                                roleMentions.append(" ").append(role.getAsMention());
                            }
                        }
                        embed.addField("Roles Required", String.valueOf(roleMentions),false);
                    }
                    if(!jsonApplication_RolesReject.isEmpty()){
                        StringBuilder roleMentions= new StringBuilder();
                        for(int i=0;i<jsonApplication_RolesReject.length();i++){
                            logger.info(fName+"role["+i+"]="+jsonApplication_RolesReject.getString(i));
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesReject.getString(i));
                            if(role!=null){
                                logger.info(fName+"role["+i+"].is valid="+role.getName());
                                roleMentions.append(" ").append(role.getAsMention());
                            }
                        }
                        embed.addField("Roles Reject", String.valueOf(roleMentions),false);
                    }
                    if(!jsonApplication_RolesGiveAway.isEmpty()){
                        StringBuilder roleMentions= new StringBuilder();
                        for(int i=0;i<jsonApplication_RolesGiveAway.length();i++){
                            logger.info(fName+"role["+i+"]="+jsonApplication_RolesGiveAway.getString(i));
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesGiveAway.getString(i));
                            if(role!=null){
                                logger.info(fName+"role["+i+"].is valid="+role.getName());
                                roleMentions.append(" ").append(role.getAsMention());
                            }
                        }
                        embed.addField("Roles GiveAway", String.valueOf(roleMentions),false);
                    }
                    if(!jsonApplication_RolesReview.isEmpty()){
                        StringBuilder roleMentions= new StringBuilder();
                        for(int i=0;i<jsonApplication_RolesReview.length();i++){
                            logger.info(fName+"role["+i+"]="+jsonApplication_RolesReview.getString(i));
                            Role role=lsRoleHelper.lsGetRoleByID(gGuild,jsonApplication_RolesReview.getString(i));
                            if(role!=null){
                                logger.info(fName+"role["+i+"].is valid="+role.getName());
                                roleMentions.append(" ").append(role.getAsMention());
                            }
                        }
                        embed.addField("Roles Review", String.valueOf(roleMentions),false);
                    }
                }else
                if(entryPreviewI==1){//description
                    if(jsonApplication.has(keyTitle)&&!jsonApplication.isNull(keyTitle)&&!jsonApplication.getString(keyTitle).isBlank()){
                        embed.addField("Title",jsonApplication.getString(keyTitle),false);
                    }else{
                        embed.addField("Title","(none)",false);
                    }
                    if(jsonApplication.has(keyDescription)&&!jsonApplication.isNull(keyDescription)&&!jsonApplication.getString(keyDescription).isBlank()){
                        embed.addField("Description",jsonApplication.getString(keyDescription),false);
                    }else{
                        embed.addField("Description","(none)",false);
                    }
                }else
                if(entryPreviewI==2){//success message
                    if(jsonApplication.has(keyAccepted)&&!jsonApplication.isNull(keyAccepted)&&!jsonApplication.getString(keyAccepted).isBlank()){
                        embed.addField("Accepted Message",jsonApplication.getString(keyAccepted),false);
                    }else{
                        embed.addField("Accepted Message","(none)",false);
                    }
                }else
                if(entryPreviewI==3){//questions
                    if(!jsonApplication_Questions.isEmpty()&&entryPreviewQ>=0&&entryPreviewQ<jsonApplication_Questions.length()){
                        embed.addField("Question "+entryPreviewQ+"/"+jsonApplication_Questions.length()+":",jsonApplication_Questions.getString(entryPreviewQ),false);
                    }
                }
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(entryPreviewI==0){//main
                    if(entryCommand.equalsIgnoreCase("edit")){
                        message.addReaction(gGlobal.emojis.getEmoji("one")).queue();//edit title
                        message.addReaction(gGlobal.emojis.getEmoji("two")).queue();//edit required roles
                        message.addReaction(gGlobal.emojis.getEmoji("three")).queue();//edit rejected roles
                        message.addReaction(gGlobal.emojis.getEmoji("four")).queue();//edit edit giveaway roles
                        message.addReaction(gGlobal.emojis.getEmoji("five")).queue();//edit edit review roles
                    }
                    message.addReaction(gGlobal.emojis.getEmoji("bookmark_tabs")).queue();//description
                    message.addReaction(gGlobal.emojis.getEmoji("blue_book")).queue();//question
                    message.addReaction(gGlobal.emojis.getEmoji("green_book")).queue();//success message
                    message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                }else
                if(entryPreviewI==1){//description
                    message.addReaction(gGlobal.emojis.getEmoji("arrow_up")).queue();
                    if(entryCommand.equalsIgnoreCase("edit"))message.addReaction(gGlobal.emojis.getEmoji("memo")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                }else
                if(entryPreviewI==2){//success message
                    message.addReaction(gGlobal.emojis.getEmoji("arrow_up")).queue();
                    if(entryCommand.equalsIgnoreCase("edit"))message.addReaction(gGlobal.emojis.getEmoji("memo")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                }else
                if(entryPreviewI==3){//questions
                    message.addReaction(gGlobal.emojis.getEmoji("arrow_left")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("arrow_up")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("arrow_right")).queue();
                    if(entryCommand.equalsIgnoreCase("edit"))message.addReaction(gGlobal.emojis.getEmoji("memo")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("heavy_plus_sign")).queue();
                    message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                }

                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            //entryCommand="edit";
                            logger.info(fName+"entryCommand="+entryCommand+", entryPreviewI="+entryPreviewI+", entryPreviewQ="+entryPreviewQ);
                            if(entryPreviewI==0){
                                if(entryCommand.equalsIgnoreCase("edit")){
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("one"))){
                                        lsMessageHelper.lsMessageDelete(message);
                                        entryAddingTitle();
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("two"))){
                                        lsMessageHelper.lsMessageDelete(message);
                                        entryAddingRolesRequired();
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("three"))){
                                        lsMessageHelper.lsMessageDelete(message);
                                        entryAddingRolesReject();
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("four"))){
                                        lsMessageHelper.lsMessageDelete(message);
                                        entryAddingRolesGiveAway();
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("five"))){
                                        lsMessageHelper.lsMessageDelete(message);
                                        entryAddingRolesReview();
                                    }
                                }

                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bookmark_tabs"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    entryPreviewI=1;
                                    entryPreview();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("blue_book"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    entryPreviewI=3;entryPreviewQ=0;
                                    entryPreview();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("green_book"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    entryPreviewI=2;entryPreviewQ=0;
                                    entryPreview();
                                }
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("arrow_up"))){
                                lsMessageHelper.lsMessageDelete(message);
                                entryPreviewI=0;entryPreviewQ=0;
                                entryPreview();
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("memo"))&&entryCommand.equalsIgnoreCase("edit")){
                                lsMessageHelper.lsMessageDelete(message);
                                if(entryPreviewI==1){//description
                                    entryAddingDescription();
                                }else
                                if(entryPreviewI==2){//success message
                                    entryAddingAcceptedMessage();
                                }else
                                if(entryPreviewI==3){//questions
                                    entryEditingQuestion(entryPreviewQ);
                                }
                            }
                            if(entryPreviewI==3){
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("heavy_plus_sign"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    entryAddingQuestions(-1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("arrow_left"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(entryPreviewQ>0){
                                        entryPreviewQ--;
                                    }
                                    if(entryPreviewQ<0){
                                        entryPreviewQ=0;
                                    }
                                    entryPreview();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("arrow_right"))){
                                    lsMessageHelper.lsMessageDelete(message);
                                    if(entryPreviewQ<jsonApplication_Questions.length()){
                                        entryPreviewQ++;
                                    }
                                    if(entryPreviewQ>=jsonApplication_Questions.length()){
                                        entryPreviewQ=jsonApplication_Questions.length()-1;
                                    }
                                    if(entryPreviewQ<0){
                                        entryPreviewQ=0;
                                    }
                                    entryPreview();
                                }
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void entryEditingQuestion(int index) {
            String fName = "[entryEditingQuestion]";
            logger.info(fName);
            try {
                logger.info(fName+".index="+index);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
                embed.setDescription("Editing question "+index+".");
                if(!jsonApplication_Questions.isEmpty()&&index>=0&&index<jsonApplication_Questions.length()){
                    embed.addField("Preview",jsonApplication_Questions.getString(index),false);
                }
                embed.addField("Questions count", String.valueOf(jsonApplication_Questions.length()),false);
                embed.addField("Options","Use "+gGlobal.emojis.getEmoji("x")+" to remove questionn.\nWhen finished editing question, use "+gGlobal.emojis.getEmoji("white_check_mark")+" to end it.",false);
                Message message= lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
                if(!jsonApplication_Questions.isEmpty()&&index>=0&&index<jsonApplication_Questions.length()){
                    message.addReaction(gGlobal.emojis.getEmoji("x")).queue();
                }
                message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
                waithBranch="questionedit";waithId=message.getIdLong();
                gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getAuthor().equals(gUser)&&gTextChannel==e.getChannel()&&waithBranch.equalsIgnoreCase("questionedit")),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            jsonApplication_Questions.put(index,content);
                            entryEditingQuestion(index);
                        },15, TimeUnit.MINUTES, () -> {

                        });
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (waithId==message.getIdLong()&&e.getUser().equals(gUser)&&gTextChannel==e.getChannel()&&message.getId().equalsIgnoreCase(e.getMessageId())),
                        e -> {
                            String name=e.getReactionEmote().getName();
                            logger.info(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.info(fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("x"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication_Questions.remove(index);
                                jsonApplication.put(keyQuestions,jsonApplication_Questions);
                                entrySave();waithBranch="";waithId=0;
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                lsMessageHelper.lsMessageDelete(message);
                                jsonApplication.put(keyQuestions,jsonApplication_Questions);
                                entrySave();waithBranch="";waithId=0;
                                entryPreview();
                            }
                        },15, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(message);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        lcJSONGuildProfile gProfile;
        String gProfileName="applications";
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(cName+fName);
            gGlobal.putGuildSettings(gGuild,gProfile);
            if(gProfile.saveProfile()){
                logger.info(fName + ".success save to db");return true;
            }
            logger.error(fName + ".error save to db");return false;
        }
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(cName+fName+".safety check");
            gProfile=gGlobal.getGuildSettings(gGuild,gProfileName);
            if(gProfile==null||!gProfile.isExistent()||gProfile.jsonObject.isEmpty()){
                gProfile=new lcJSONGuildProfile(gGlobal,gProfileName,gGuild,llv2_GuildsSettings);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild,gProfile);
                }
            }
            safetyProfile();
            if(gProfile.isUpdated){
                gGlobal.putGuildSettings(gGuild,gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success save to db");
                }else{
                    logger.error(fName + ".error save to db");
                }
            }
        }
        String keyApplications="applications", keyEnabled="enabled";
        private void safetyProfile(){
            if(gProfile.jsonObject.isEmpty()){ gProfile.jsonObject =new JSONObject(); gProfile.isUpdated=true; }
            gProfile.safetyPutFieldEntry(keyApplications,new JSONObject());
            gProfile.safetyPutFieldEntry(keyEnabled,true);
        }

    }

}
