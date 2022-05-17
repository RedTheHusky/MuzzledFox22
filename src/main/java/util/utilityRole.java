package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.llGlobalHelper;
import models.ls.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityRole extends Command implements  llGlobalHelper, llMemberHelper,  llCommonKeys {
    String cName="[utilityRole]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    public utilityRole(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal = g;
        gWaiter=g.waiter;
        this.name = "Role-Utility";
        this.help = "Utility commands for roles.";
        this.aliases = new String[]{"role"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
        //this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        Guild gGuild;User gUser; TextChannel gTextChannel; Member gMember;private Message gMessage;
        String gTitle="Role Utility";
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
            String fName="[run]";
            logger.info(fName);
            boolean isInvalidCommand = true;
            try {
                prefix2=gCommandEvent.getJDA().getSelfUser().getAsMention();
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    String []items = gCommandEvent.getArgs().split("\\s+");
                    if(items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else
                    if(isRoleMentioned(items[0])){
                         if(items.length>=2){
                             switch (items[1].toLowerCase()){
                                 case "setcolor":
                                     setColor();isInvalidCommand=false;
                                     break;
                                 case "setname":
                                     setName();isInvalidCommand=false;
                                     break;
                                 case "sethoisted":
                                     if(items.length>=3){
                                         switch (items[2].toLowerCase()){
                                             case "yes":
                                             case "true":
                                             case "1":
                                                 setHoisted(true);isInvalidCommand=false;
                                                 break;
                                             case "no":
                                             case "false":
                                             case "0":
                                                 setHoisted(false);isInvalidCommand=false;
                                                 break;
                                         }
                                     }
                                     break;
                                 case "setmntionable":
                                     if(items.length>=3){
                                         switch (items[2].toLowerCase()){
                                             case "yes":
                                             case "true":
                                             case "1":
                                                 setMentionable(true);isInvalidCommand=false;
                                                 break;
                                             case "no":
                                             case "false":
                                             case "0":
                                                 setMentionable(false);isInvalidCommand=false;
                                                 break;
                                         }
                                     }
                                     break;
                             }
                         }else{
                             infoRole(); isInvalidCommand=false;
                         }
                    }
                    else switch (items[0].toLowerCase()){
                        case "everyoneadd":
                            addEveryone2Role(3); isInvalidCommand=false;
                            break;
                        case "everyonerem":
                            remEveryone2Role(3); isInvalidCommand=false;
                            break;
                        case "usersoneadd":
                            addEveryone2Role(2); isInvalidCommand=false;
                            break;
                        case "usersrem":
                            remEveryone2Role(2); isInvalidCommand=false;
                            break;
                        case "botsadd":
                            addEveryone2Role(1); isInvalidCommand=false;
                            break;
                        case "botsrem":
                            remEveryone2Role(1); isInvalidCommand=false;
                            break;
                        case "everyoneswap":
                            swapEveryone2Role(3); isInvalidCommand=false;
                            break;
                        case "usersswap":
                            swapEveryone2Role(2); isInvalidCommand=false;
                            break;
                        case "botsswap":
                            swapEveryone2Role(1); isInvalidCommand=false;
                            break;
                        case "everyoneifadd":
                            addIfEveryone2Role(3); isInvalidCommand=false;
                            break;
                        case "usersifadd":
                            addIfEveryone2Role(2); isInvalidCommand=false;
                            break;
                        case "botsifadd":
                            addIfEveryone2Role(1); isInvalidCommand=false;
                            break;
                        case "everyoneifrem":
                            remIfEveryone2Role(3); isInvalidCommand=false;
                            break;
                        case "usersifrem":
                            remIfEveryone2Role(2); isInvalidCommand=false;
                            break;
                        case "botsifrem":
                            remIfEveryone2Role(1); isInvalidCommand=false;
                            break;
                        case "json":
                            if(items.length>=2){
                                switch (items[1].toLowerCase()){
                                    case "simple":
                                        sendRolesJsonFile();isInvalidCommand=true;
                                        break;
                                    case "multi":
                                        sendRolesJsonZipFile();isInvalidCommand=true;
                                        break;
                                }
                            }else{
                                sendRolesJsonFile();isInvalidCommand=true;
                            }
                            break;
                    }
                }
                logger.info(fName+".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(),llColors.llColorRed);
            }
        }
        String prefix2="";
        private void help(String command){
        String fName="help";
        logger.info(fName + ".command:"+command);
        String quickSummonWithSpace=prefix2+"role ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle); embed.setColor(llColors.llColorBlue1);
        embed.addField("Info","`"+quickSummonWithSpace+"[mention or  id]`  get basic info of channel",false);
        embed.addField("json","`"+quickSummonWithSpace+"json <multi>`  get a json text file(s) of the server roles",false);
        if(llMemberHasPermission_MANAGECHANNEL(gMember)){
            embed.addField("Settings","`"+quickSummonWithSpace+"[mention or  id] setname` sets role name."
                    +"\n`"+quickSummonWithSpace+"[mention or  id] sethoisted|setmntionable <1|0>` sets its flag"
                    +"\n`"+quickSummonWithSpace+"[mention or  id] setcolor [int|int_r int_b int_g]` sets the role color",false);
            embed.addField("Add to","`"+quickSummonWithSpace+"everyoneadd|usersadd|botsadd @Role` adds role(s) to everyone or just user or just bot.",false);
            embed.addField("Remove from","`"+quickSummonWithSpace+"everyonerem|usersrem|botsrem @Role` removes role(s) from everyone or just user or just bot.",false);
            embed.addField("Swap roles","`"+quickSummonWithSpace+"everyoneswap|usersswap|botsswap @roleA @roleB` Removes roleA from member and replaces it with roleB.",false);

        }
        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
        lsMessageHelper.lsSendMessage(gUser,embed);
    }
        Role mentionedRole =null;
        private boolean isRoleMentioned(String nameOrid){
            String fName="isTextChannelMentioned";
            logger.info(fName);
            try {
                List<TextChannel>textChannels= gCommandEvent.getMessage().getMentionedChannels();
                /*if(!textChannels.isEmpty()){
                    mentionedTextChannel=textChannels.get(0);mentionedGuildChannel=gGuild.getGuildChannelById(mentionedTextChannel.getId());logger.info(fName+"=true");return true;
                }*/
                logger.info(fName+".nameOrid="+nameOrid);
                Role role=lsRoleHelper.lsGetFirstRolesByName(gGuild,nameOrid,true);
                if(role!=null){
                    mentionedRole =role;logger.info(fName+"=true");return true;
                }
                role=lsRoleHelper.lsGetRoleByID(gGuild,nameOrid);
                if(role!=null){
                    mentionedRole =role;logger.info(fName+"=true");return true;
                }
                logger.info(fName+"=false");return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                logger.info(fName+"=false");return false;
            }
        }

        private void infoRole(){
            String fName="infoRole";
            logger.info(fName);
            try {
               if(mentionedRole ==null){
                   return;
               }
               String id= mentionedRole.getId();
               String name= mentionedRole.getName();
               Color color=mentionedRole.getColor();
               int position=mentionedRole.getPosition();
               boolean isMentionable=mentionedRole.isMentionable();
               boolean isHoisted=mentionedRole.isHoisted();
               boolean isPublicRole=mentionedRole.isPublicRole();
               EnumSet<Permission> permissionEnum=null;
               OffsetDateTime timecreated= mentionedRole.getTimeCreated();
               try {
                   permissionEnum=mentionedRole.getPermissions();
                   logger.info(fName+"got permissions");
                   logger.info(fName+"permissions="+permissionEnum.size());
               }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
               }
               EmbedBuilder embedBuilder=new EmbedBuilder();
               embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorPurple2);
               embedBuilder.addField("Id",id,true);
               embedBuilder.addField("Name",name,true);
                embedBuilder.addField("Created", lsUsefullFunctions.convertOffsetDateTime2HumanReadable(timecreated),true);
                if(color!=null) {
                    embedBuilder.setColor(color);
                    embedBuilder.addField("Color", color.getRGB() + "\n" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha(), true);
                }
                embedBuilder.addField("Position", String.valueOf(position).replace("t","T").replace("f","F"),true);
                embedBuilder.addField("Hoisted", String.valueOf(isHoisted).replace("t","T").replace("f","F"),true);
                embedBuilder.addField("Mentionable", String.valueOf(isMentionable).replace("t","T").replace("f","F"),true);
                if(isPublicRole)embedBuilder.addField("PublicRole", "True",true);
                List<Member>membersWithRole=new LinkedList<>();
                try {
                    membersWithRole=gGuild.getMembersWithRoles(mentionedRole);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                embedBuilder.addField("Members", String.valueOf(membersWithRole.size()),true);

                if(permissionEnum!=null&&!permissionEnum.isEmpty()){
                    try {
                        logger.info(fName+"entered permissions="+permissionEnum.size());
                        StringBuilder tmp= new StringBuilder();
                        for(Permission permission:permissionEnum){
                            if(!tmp.toString().isBlank()){
                                tmp.append(", ").append(permission.getName());
                            }else{
                                tmp = new StringBuilder(permission.getName());
                            }
                        }
                        embedBuilder.addField("Permissions", tmp.toString(),false);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void setName(){
            String fName="[setName]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                Message message = null;
                if(mentionedRole==null){
                    logger.warn(fName+"no roles");
                    return;
                }
                Message finalMessage = lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the new name for "+mentionedRole.getAsMention()+" or type `!cance;`.",llColors.llColorBlue1);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            lsMessageHelper.lsMessageDelete(finalMessage);
                            if(content.equalsIgnoreCase("!cancel")){
                               return;
                            }
                            mentionedRole.getManager().setName(content).complete();
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+ mentionedRole.getAsMention()+" name.", llColors.llColorPurple2);

                        },3, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColors.llColorRed);lsMessageHelper.lsMessageDelete(finalMessage);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setColor(){
            String fName="[setColor]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                String []items = gCommandEvent.getArgs().split("\\s+");
                //role command color color color
                if(items.length!=3&&items.length!=5){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Please enter the color for "+mentionedRole.getAsMention()+" like ` int_red int_green int_blue` or `int.",llColors.llColorBlue1);
                    return;
                }
                Color color=null;
                if(items.length==5){
                    if(!lsUsefullFunctions.isStringJustInteger(items[2])||!lsUsefullFunctions.isStringJustInteger(items[3])||!lsUsefullFunctions.isStringJustInteger(items[4])){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Not a number! Please use just numbers.", llColors.llColorRed);return;
                    }
                    int red=lsUsefullFunctions.getStringJustInteger(items[2]);
                    int green=lsUsefullFunctions.getStringJustInteger(items[3]);
                    int blue=lsUsefullFunctions.getStringJustInteger(items[4]);
                    color=new Color(red,green,blue);

                }else
                if(!lsUsefullFunctions.isStringJustInteger(items[2])){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Not a number! Please use just numbers.", llColors.llColorRed);return;
                }else{
                    int colorRaaw=lsUsefullFunctions.getStringJustInteger(items[2]);
                    color=new Color(colorRaaw);
                }

                if(color==null){
                    return;
                }
                mentionedRole.getManager().setColor(color).complete();
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+ mentionedRole.getAsMention()+" color.", llColors.llColorPurple2);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setHoisted(boolean flag){
            String fName="[setHoisted]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                if(mentionedRole==null){
                    logger.warn(fName+"no roles");
                    return;
                }
                logger.info(fName+"flag="+flag);
                mentionedRole.getManager().setHoisted(flag);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+ mentionedRole.getAsMention()+" option hoisted to "+flag, llColors.llColorPurple2);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void setMentionable(boolean flag){
            String fName="[setMentionable]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                if(mentionedRole==null){
                    logger.warn(fName+"no roles");
                    return;
                }
                logger.info(fName+"flag="+flag);
                mentionedRole.getManager().setMentionable(flag);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Updated "+ mentionedRole.getAsMention()+" option mentionable to "+flag, llColors.llColorPurple2);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void addEveryone2Role(int targets){
            String fName="[addEveryone2Role]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "No role mentioned", llColors.llColorRed);return;
                }
                List<Member>members=gGuild.getMembers();
                if(members.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Member list empty", llColors.llColorPink2);return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorPurple2);
                int membersSize2Update=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            membersSize2Update++;
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            membersSize2Update++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                Message messageWait=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel, gTitle, "This will take some time to update roles for "+membersSize2Update+" members.", llColors.llColorBlue1);
                int found=0,success=0,failed=0, users=0,bots=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            found++;
                            for(Role role:roles){
                                logger.info(fName+"role:"+role.getName()+"|"+role.getId());
                                gGuild.addRoleToMember(member,role).reason("by "+gUser.getId()).complete();
                                bots++;success++;
                            }
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            found++;
                            for(Role role:roles){
                                logger.info(fName+"role:"+role.getName()+"|"+role.getId());
                                gGuild.addRoleToMember(member,role).reason("by "+gUser.getId()).complete();
                                users++;success++;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed++;
                    }
                }
                StringBuilder tmp= new StringBuilder();
                for(Role role:roles){
                    logger.info(fName+"role:"+role.getName()+"|"+role.getId());
                    tmp.append(role.getAsMention());
                }
                lsMessageHelper.lsMessageDelete(messageWait);
                embedBuilder.addField("Found", String.valueOf(found),true);
                embedBuilder.addField("Users", String.valueOf(users),true);
                embedBuilder.addField("Bots", String.valueOf(bots),true);
                embedBuilder.addField("Success", String.valueOf(success),true);
                embedBuilder.addField("Failed", String.valueOf(failed),true);
                embedBuilder.addField("Added roles", tmp.toString(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void remEveryone2Role(int targets){
            String fName="[remEveryone2Role]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "No role mentioned", llColors.llColorRed);return;
                }
                if(roles.size()>1){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Only 1 role!", llColors.llColorRed);return;
                }
                Role role=roles.get(0);
                logger.info(fName+"role:"+role.getName()+"|"+role.getId());
                List<Member>members=gGuild.getMembersWithRoles(role);
                if(members.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Member list empty", llColors.llColorPink2);return;
                }
                int membersSize2Update=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            membersSize2Update++;
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            membersSize2Update++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                Message messageWait=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel, gTitle, "This will take some time to update roles for "+membersSize2Update+" members. Please be patient.", llColors.llColorBlue1);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorPurple2);
                int found=0,success=0,failed=0, users=0,bots=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            found++;
                            gGuild.removeRoleFromMember(member,role).reason("by "+gUser.getId()).complete();
                            bots++;success++;
                        }else
                        if(!user.isBot()&&(targets==2||targets==3)){
                            found++;
                            gGuild.removeRoleFromMember(member,role).reason("by "+gUser.getId()).complete();
                            users++;success++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed++;
                    }
                }
                lsMessageHelper.lsMessageDelete(messageWait);
                embedBuilder.addField("Found", String.valueOf(found),true);
                embedBuilder.addField("Users", String.valueOf(users),true);
                embedBuilder.addField("Bots", String.valueOf(bots),true);
                embedBuilder.addField("Success", String.valueOf(success),true);
                embedBuilder.addField("Failed", String.valueOf(failed),true);
                embedBuilder.addField("Removed role", role.getAsMention(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void swapEveryone2Role(int targets){
            String fName="[swapEveryone2Role]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "No role mentioned for swapping!", llColors.llColorRed);return;
                }
                if(roles.size()>2){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Only 2 roles to use for swap!", llColors.llColorRed);return;
                }
                if(roles.size()==1){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Need a role to swap with!", llColors.llColorRed);return;
                }
                Role roleA=roles.get(0); Role roleB=roles.get(1);
                if(roleA==roleB){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Both roles cant be the same one!", llColors.llColorRed);return;
                }
                logger.info(fName+"roleA:"+roleA.getName()+"|"+roleA.getId());
                logger.info(fName+"roleB:"+roleB.getName()+"|"+roleB.getId());
                List<Member>members=gGuild.getMembersWithRoles(roleA);
                if(members.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Member list empty", llColors.llColorPink2);return;
                }
                int membersSize2Update=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            membersSize2Update++;
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            membersSize2Update++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                Message messageWait=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel, gTitle, "This will take some time to update roles for "+membersSize2Update+" members. Please be patient.", llColors.llColorBlue1);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorPurple2);
                int found=0,success=0,failed=0, users=0,bots=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if (user.isBot() && (targets == 1 || targets == 3)) {
                            found++;
                            gGuild.addRoleToMember(member, roleB).reason("swapped by " + gUser.getId()).complete();
                            gGuild.removeRoleFromMember(member, roleA).reason("swapped by " + gUser.getId()).complete();
                            bots++;
                            success++;
                        } else if (!user.isBot()&&(targets==2||targets==3)) {
                            found++;
                            gGuild.addRoleToMember(member, roleB).reason("swapped by " + gUser.getId()).complete();
                            gGuild.removeRoleFromMember(member, roleA).reason("swapped by " + gUser.getId()).complete();
                            users++;
                            success++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed++;
                    }
                }
                lsMessageHelper.lsMessageDelete(messageWait);
                embedBuilder.addField("Found", String.valueOf(found),true);
                embedBuilder.addField("Users", String.valueOf(users),true);
                embedBuilder.addField("Bots", String.valueOf(bots),true);
                embedBuilder.addField("Success", String.valueOf(success),true);
                embedBuilder.addField("Failed", String.valueOf(failed),true);
                embedBuilder.addField("Swap role", roleA.getAsMention()+" -> "+roleB.getAsMention(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void addIfEveryone2Role(int targets){
            String fName="[addIfEveryone2Role]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "No role mentioned!", llColors.llColorRed);return;
                }
                if(roles.size()>2){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Only 2 roles to use!", llColors.llColorRed);return;
                }
                if(roles.size()==1){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Need a role to add!", llColors.llColorRed);return;
                }
                Role roleA=roles.get(0); Role roleB=roles.get(1);
                if(roleA==roleB){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Both roles cant be the same one!", llColors.llColorRed);return;
                }
                logger.info(fName+"roleA:"+roleA.getName()+"|"+roleA.getId());
                logger.info(fName+"roleB:"+roleB.getName()+"|"+roleB.getId());
                List<Member>members=gGuild.getMembersWithRoles(roleA);
                if(members.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Member list empty", llColors.llColorPink2);return;
                }
                int membersSize2Update=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            membersSize2Update++;
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            membersSize2Update++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                Message messageWait=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel, gTitle, "This will take some time to update roles for "+membersSize2Update+" members. Please be patient.", llColors.llColorBlue1);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorPurple2);
                int found=0,success=0,failed=0, users=0,bots=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            found++;
                            gGuild.addRoleToMember(member,roleB).reason("added if by "+gUser.getId()).complete();
                            bots++;success++;
                        }else
                        if(!user.isBot()&&(targets==2||targets==3)){
                            found++;
                            gGuild.addRoleToMember(member,roleB).reason("added if by "+gUser.getId()).complete();
                            users++;success++;
                        }

                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed++;
                    }
                }
                lsMessageHelper.lsMessageDelete(messageWait);
                embedBuilder.addField("Found", String.valueOf(found),true);
                embedBuilder.addField("Users", String.valueOf(users),true);
                embedBuilder.addField("Bots", String.valueOf(bots),true);
                embedBuilder.addField("Success", String.valueOf(success),true);
                embedBuilder.addField("Failed", String.valueOf(failed),true);
                embedBuilder.addField("Added if", "Added "+roleB.getAsMention()+ "if they have "+roleA.getAsMention(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void remIfEveryone2Role(int targets){
            String fName="[addIfEveryone2Role]";
            logger.info(fName);
            try{
                if(!lsMemberIsBotOwner(gMember)&&!lsMemberHelper.lsMemberHasPermission_MANAGEROLES(gMember)){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Denied", llColors.llColorRed);return;
                }
                List<Role>roles= gCommandEvent.getMessage().getMentionedRoles();
                if(roles.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "No role mentioned!", llColors.llColorRed);return;
                }
                if(roles.size()>2){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Only 2 roles to use!", llColors.llColorRed);return;
                }
                if(roles.size()==1){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Need a role to add!", llColors.llColorRed);return;
                }
                Role roleA=roles.get(0); Role roleB=roles.get(1);
                if(roleA==roleB){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Both roles cant be the same one!", llColors.llColorRed);return;
                }
                logger.info(fName+"roleA:"+roleA.getName()+"|"+roleA.getId());
                logger.info(fName+"roleB:"+roleB.getName()+"|"+roleB.getId());
                List<Member>members=gGuild.getMembersWithRoles(roleA);
                if(members.isEmpty()){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Member list empty", llColors.llColorPink2);return;
                }
                int membersSize2Update=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                        if(user.isBot()&&(targets==1||targets==3)){
                            membersSize2Update++;
                        }else if(!user.isBot()&&(targets==2||targets==3)){
                            membersSize2Update++;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                }
                Message messageWait=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel, gTitle, "This will take some time to update roles for "+membersSize2Update+" members. Please be patient.", llColors.llColorBlue1);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColors.llColorPurple2);
                int found=0,success=0,failed=0, users=0,bots=0;
                for(Member member:members){
                    try {
                        User user=member.getUser();
                        logger.info(fName+"member:"+user.getName()+"#"+user.getDiscriminator()+"("+member.getId()+")");
                            if(user.isBot()&&(targets==1||targets==3)){
                                found++;
                                gGuild.addRoleToMember(member,roleB).reason("removed if by "+gUser.getId()).complete();
                                bots++;success++;
                            }else
                            if(!user.isBot()&&(targets==2||targets==3)){
                                found++;
                                gGuild.addRoleToMember(member,roleB).reason("removed if by "+gUser.getId()).complete();
                                users++;success++;
                            }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        failed++;
                    }
                }
                lsMessageHelper.lsMessageDelete(messageWait);
                embedBuilder.addField("Found", String.valueOf(found),true);
                embedBuilder.addField("Users", String.valueOf(users),true);
                embedBuilder.addField("Bots", String.valueOf(bots),true);
                embedBuilder.addField("Success", String.valueOf(success),true);
                embedBuilder.addField("Failed", String.valueOf(failed),true);
                embedBuilder.addField("Added if", "Removed "+roleB.getAsMention()+ "if they have "+roleA.getAsMention(),false);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Exception:"+e.toString(), llColors.llColorRed);
            }
        }
        private void sendRolesJsonFile(){
            String fName = "[sendRolesFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONArray array=new JSONArray();
                List<Role>roles=gGuild.getRoles();
                logger.info(cName + fName+"roles.Size="+roles.size());
                for(Role role : roles){
                    try {
                        JSONObject jsonRole= lsJson4Entity.llGetRoleJsonEntry(role);
                        if(jsonRole!=null)array.put(jsonRole);
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="Roles", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendRolesJsonZipFile(){
            String fName = "[sendRolesZipFile]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser, gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            try {
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                OffsetDateTime now=OffsetDateTime.now();
                String entryName="%id", entryExtension=".txt";
                lcTempZipFile zipFile=new lcTempZipFile();

                List<Role>roles=gGuild.getRoles();
                logger.info(cName + fName+"roles.Size="+roles.size());
                for(Role role : roles){
                    try {
                        JSONObject jsonRole= lsJson4Entity.llGetRoleJsonEntry(role);
                        if(jsonRole!=null) {
                            zipFile.addEntity(entryName.replaceAll("%id",role.getId())+entryExtension,jsonRole);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = zipFile.getInputStream();
                String fileName="Roles", fileExtension=".zip";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
    
  //runLocal  
    }
}
