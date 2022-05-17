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
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class utilityPrune extends Command implements  llGlobalHelper,  llMemberHelper, llCommonKeys {
    String cName="[utilityPrune]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="prune";
    public utilityPrune(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Prune-Utility";
        this.help = "Utility commands for prunning.";
        this.aliases = new String[]{commandPrefix,"utilityprune","pruneutility"};
        this.guildOnly = true;
        this.category= llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage; String gTitle="Prune Utility";

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
            logger.info(cName + fName);
            boolean isInvalidCommand = true;
            try {
                if (gCommandEvent.getArgs().isEmpty()) {
                    logger.info(cName + fName + ".Args=0");
                    help("main");
                    isInvalidCommand = false;
                } else {
                    logger.info(cName + fName + ".Args");
                    String[] items = gCommandEvent.getArgs().split("\\s+");
                    if (items.length>=2&&items[0].equalsIgnoreCase("help")) {
                        help(items[1]);
                        isInvalidCommand = false;
                    }
                    else if (items[0].equalsIgnoreCase("help")) {
                        help("main");
                        isInvalidCommand = false;
                    }else
                    if ((items[0].equalsIgnoreCase("newmember")||items[0].equalsIgnoreCase("nm"))&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)) {
                        if(items.length>=2){
                            if (items[1].equalsIgnoreCase("list")) {
                                getListOfNewMembers2Prune();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("doall")) {
                                pruneNewMembers();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("is")) {
                                checkIfNewMemberIs4Pruning();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("domention")) {
                                pruneMentionedNewMember();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("json")) {
                                sendNewMembersPruneJsonFile();
                                isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("message")){
                                messageAfterPruned(null);  isInvalidCommand = false;
                            }
                        }
                    }
                    if ((items[0].equalsIgnoreCase("norole")||items[0].equalsIgnoreCase("0"))) {
                        if(items.length>=2){
                            if (items[1].equalsIgnoreCase("list")) {
                                getListOfNoRoles2Prune();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("doall")) {
                                pruneNoRoleMembers();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("is")) {
                                checkIfMentionedIsNoRole4Pruning();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("domention")) {
                                pruneNoRoleMentioned();
                                isInvalidCommand = false;
                            }
                            if (items[1].equalsIgnoreCase("json")) {
                                sendNoRolePruneJsonFile();
                                isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("message")){
                                messageAfterPrunedNoRoleMember(null);  isInvalidCommand = false;
                            }
                        }
                    }

                }
                logger.info(cName + fName + ".deleting op message");
                lsMessageHelper.lsMessageDelete(gCommandEvent);
                if (isInvalidCommand) {
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+e.toString(), llColors.llColorRed);
            }
        }

        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = " ";
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColors.llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            if (llMemberIsStaff(gMember)) {
                desc = "\n`" + quickSummonWithSpace + "0 is` checks if mentioned members is valid for pruning";
                desc += "\n`" + quickSummonWithSpace +"0 domention` prunes only mentioned members that have 0 roles and joined 14 days ago";
                desc += "\n`" + quickSummonWithSpace + "0 list` generates a list of new members that have 0 roles and joined 14 days ago";
                desc += "\n`" + quickSummonWithSpace + "0 doall` prunes all new members that have 0 roles and joined 14 days ago";
                embed.addField("Inactive members without role 2 prune", desc, false);
                desc = "";
            }
            if (llMemberIsStaff(gMember)&&lsGuildHelper.lsIsGuildInHouse(gGlobal, gGuild)) {
                desc = "\n`" + quickSummonWithSpace + "nm is` checks if mentioned members is valid for pruning";
                if (llMemberIsModerator(gMember))
                    desc += "\n`" + quickSummonWithSpace + "nm domention` prunes only mentioned new members that only have that role and joined 14 days ago";
                desc += "\n`" + quickSummonWithSpace + "nm list` generates a list of new members that only have that role and joined 14 days ago";
                if (llMemberIsManager(gMember))
                    desc += "\n`" + quickSummonWithSpace + "nm doall` prunes all new members that only have that role and joined 14 days ago";
                embed.addField("Inactive new members 2 prune", desc, false);
                desc = "";
            }
            lsMessageHelper.lsSendMessage(gUser,embed);
        }

        private long twoWeeksToSecond=1209600;
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        private boolean hasItBeen2WeeksAndNotBooster(Member member) {
            String fName = "[hasItBeen2WeeksAndNotBooster]";
            logger.info(cName + fName);
            try {
                logger.info(cName + fName+" member="+ member.getUser().getName()+"("+ member.getId()+")");
                OffsetDateTime joined=member.getTimeJoined();
                OffsetDateTime now=OffsetDateTime.now();
                logger.info(cName + fName+"time now:"+now.getYear()+"."+now.getMonth()+"."+now.getDayOfMonth()+"[epoch Second="+now.toEpochSecond()+"]");
                logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth()+"[epoch Second="+joined.toEpochSecond()+"]");
                if(now.isAfter(joined)){
                    logger.info(cName + fName+"is after");
                    long diff=now.toEpochSecond()-joined.toEpochSecond();
                    logger.info(cName + fName+"diff="+diff);
                    logger.info(cName + fName+"check if bigger than=="+twoWeeksToSecond);
                    if(diff>twoWeeksToSecond){
                        logger.info(cName + fName+"is bigger");
                        if(!llMemberIsBooster(member)){
                            logger.info(cName + fName+"not a booster>true");
                           return  true;
                        }else{
                            logger.info(cName + fName+"is a booster>false");
                            return  false;
                        }
                    }
                }
                logger.info(cName + fName+"is not after>false");
                return  false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private void getListOfNewMembers2Prune() {
            String fName = "[getListOfNewMembers2Prune]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="New members/w 1role";
            Role role=lsRoleHelper.lsGuildNewMemberRole(gGuild);
            if(role==null){
                logger.info(cName + fName+"check if has mentioned roles");
                List<Role>rolesMentioned= gCommandEvent.getMessage().getMentionedRoles();
                if(!rolesMentioned.isEmpty()){
                    logger.warn(cName + fName+"use mentioned role");
                    role=rolesMentioned.get(0);
                }
            }
            if(role==null){
                logger.warn(cName + fName+"no role to use");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error:No role selected",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"role="+role.getId()+"|"+role.getName());
            members=gGuild.getMembersWithRoles(role);
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with this role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,title,"No members with the role:"+ role.getAsMention(),llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    if(roleSize==1){
                        countMembersOnlyHaveThatRole++;
                        if(hasItBeen2WeeksAndNotBooster(member)){
                            members2Print.add(member);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                if(countMembersOnlyHaveThatRole==0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role:"+ role.getAsMention(),llColors.llColorRed);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role "+ role.getAsMention() +" and been member for 2 weeks.",llColors.llColorRed);
                }
                return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(title);embed.setColor(llColors.llColorOrange_Pumpkin);
            String desc="Members that only have role "+role.getAsMention()+" and joined 14+ days ago";
            int count=0,displayed=0, size=members2Print.size();
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    OffsetDateTime joined=member.getTimeJoined();
                    logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth());
                    String formatted=joined.format(europeanDateFormatter);
                    logger.info(cName + fName+"formatted="+formatted);
                    String tmp=desc+"\n"+member.getAsMention()+" "+formatted;
                    if(tmp.length()>2000){
                        embed.setDescription(desc);embed.setTitle(title+" ["+displayed+"/"+count+"/"+size);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        desc="Members that only have role "+role.getAsMention()+" and joined 14+ days ago"+"\n"+member.getAsMention()+" "+formatted;displayed=1;
                    }else{
                        desc=tmp;displayed++;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            embed.setDescription(desc);embed.setTitle(title+" ["+displayed+"/"+count+"/"+size);
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        private void pruneNewMembers() {
            String fName = "[pruneNewMembers]";
            logger.info(cName + fName);
            if (!llMemberIsManager(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Managers+ only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="Prune new members";
            Role role=lsRoleHelper.lsGuildNewMemberRole(gGuild);
            if(role==null){
                logger.info(cName + fName+"check if has mentioned roles");
                List<Role>rolesMentioned= gCommandEvent.getMessage().getMentionedRoles();
                if(!rolesMentioned.isEmpty()){
                    logger.warn(cName + fName+"use mentioned role");
                    role=rolesMentioned.get(0);
                }
            }
            if(role==null){
                logger.warn(cName + fName+"no role to use");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error:No role selected",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"role="+role.getId()+"|"+role.getName());
            members=gGuild.getMembersWithRoles(role);
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with this role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members with the role:"+ role.getAsMention(),llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    if(roleSize==1){
                        countMembersOnlyHaveThatRole++;
                        if(hasItBeen2WeeksAndNotBooster(member)){
                            members2Print.add(member);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                if(countMembersOnlyHaveThatRole==0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role:"+ role.getAsMention(),llColors.llColorRed);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role "+ role.getAsMention() +" and been member for 2 weeks.",llColors.llColorRed);
                }
                return;
            }
            int count=0;
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    member.kick("Pruned for inactivity by"+gUser.getName()).complete();
                    //messageAfterPruned(member);
                    if(count>=10){logger.warn(cName + fName+"safety break");break;}
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,gUser.getAsMention()+" has pruned "+count+" new members who only has those roles and joined 14+ days ago.",llColors.llColorOrange_Coral);
        }
        private void pruneMentionedNewMember() {
            String fName = "[pruneMentionedNewMember]";
            logger.info(cName + fName);
            if (!llMemberIsModerator(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Moderator+ only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="Prune new members";
            Role role=lsRoleHelper.lsGuildNewMemberRole(gGuild);
            if(role==null){
                logger.info(cName + fName+"check if has mentioned roles");
                List<Role>rolesMentioned= gCommandEvent.getMessage().getMentionedRoles();
                if(!rolesMentioned.isEmpty()){
                    logger.warn(cName + fName+"use mentioned role");
                    role=rolesMentioned.get(0);
                }
            }
            if(role==null){
                logger.warn(cName + fName+"no role to use");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error:No role selected",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"role="+role.getId()+"|"+role.getName());
            members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with mentioned");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members mentioned",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersHaveThatRole=0;
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName());
                    if(lsRoleHelper.lsHasRole(member,role)){
                        countMembersHaveThatRole++;
                        int roleSize=member.getRoles().size();
                        logger.info(cName + fName+"role size="+roleSize);
                        if(roleSize==1){
                            countMembersOnlyHaveThatRole++;
                            OffsetDateTime joined=member.getTimeJoined();
                            OffsetDateTime now=OffsetDateTime.now();
                            logger.info(cName + fName+"time now:"+now.getYear()+"."+now.getMonth()+"."+now.getDayOfMonth()+"[epoch econd="+now.toEpochSecond()+"]");
                            logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth()+"[epoch Second="+joined.toEpochSecond()+"]");
                            if(now.isAfter(joined)){
                                logger.info(cName + fName+"is after");
                                long diff=now.toEpochSecond()-joined.toEpochSecond();
                                logger.info(cName + fName+"diff="+diff);
                                logger.info(cName + fName+"check if bigger than=="+twoWeeksToSecond);
                                if(diff>twoWeeksToSecond){
                                    logger.info(cName + fName+"is bigger");
                                    if(!llMemberIsBooster(member)){
                                        members2Print.add(member);
                                    }else{
                                        logger.info(cName + fName+"is a booster");
                                    }
                                }
                            }

                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info(cName + fName+"countMembersHaveThatRole="+countMembersHaveThatRole);
            logger.info(cName + fName+"countMembersOnlyHaveThatRole="+countMembersOnlyHaveThatRole);
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                if(countMembersHaveThatRole==0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members having role:"+ role.getAsMention(),llColors.llColorRed);
                }else if(countMembersOnlyHaveThatRole==0){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role:"+ role.getAsMention(),llColors.llColorRed);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members just having role "+ role.getAsMention() +" and been member for 2 weeks.",llColors.llColorRed);
                }

                return;
            }
            int count=0;
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    member.kick("Pruned for inactivity by"+gUser.getName()).complete();
                    //messageAfterPruned(member);
                    if(count>=10){logger.warn(cName + fName+"safety break");break;}
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,gUser.getAsMention()+" has pruned "+count+" new members who only has those roles and joined 14+ days ago.",llColors.llColorOrange_Coral);
        }
        private void checkIfNewMemberIs4Pruning() {
            String fName = "[checkIfNewMemberIs4Pruning]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member> members;
            String title = "Prune new members";
            Role role=lsRoleHelper.lsGuildNewMemberRole(gGuild);
            if(role==null){
                logger.info(cName + fName+"check if has mentioned roles");
                List<Role>rolesMentioned= gCommandEvent.getMessage().getMentionedRoles();
                if(!rolesMentioned.isEmpty()){
                    logger.warn(cName + fName+"use mentioned role");
                    role=rolesMentioned.get(0);
                }
            }
            if(role==null){
                logger.warn(cName + fName+"no role to use");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error:No role selected",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName + "role=" + role.getId() + "|" + role.getName());
            members = gCommandEvent.getMessage().getMentionedMembers();
            if (members.isEmpty()) {
                logger.warn(cName + fName + "no members with mentioned");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No members mentioned", llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(title);embed.setColor(llColors.llColorOrange_Pumpkin);
            String desc="Members checking who have the role "+role.getAsMention()+" and joined 14+ days ago:";
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    OffsetDateTime joined=member.getTimeJoined();
                    String tmp="";
                    if(roleSize==1){
                        OffsetDateTime now=OffsetDateTime.now();
                        logger.info(cName + fName+"time now:"+now.getYear()+"."+now.getMonth()+"."+now.getDayOfMonth()+"[epochSecon="+now.toEpochSecond()+"]");
                        logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth()+"[epochSecon="+joined.toEpochSecond()+"]");
                        if(now.isAfter(joined)){
                            logger.info(cName + fName+"isafter");
                            long diff=now.toEpochSecond()-joined.toEpochSecond();
                            logger.info(cName + fName+"diff="+diff);
                            logger.info(cName + fName+"checkifbiggerthan=="+twoWeeksToSecond);
                            String formated=joined.format(europeanDateFormatter);
                            logger.info(cName + fName+"formated="+formated);
                            if(diff>twoWeeksToSecond){
                                logger.info(cName + fName+"is bigger");
                                if(!llMemberIsBooster(member)){
                                    tmp="\n"+member.getAsMention()+" valid, join date: "+formated;
                                }else{
                                    logger.info(cName + fName+"is a booster");
                                    tmp="\n"+member.getAsMention()+" invalid: is a booster, join date: "+formated;
                                }

                            }else{
                                tmp="\n"+member.getAsMention()+" invalid: has not yet pass 2 weeks, joined date: "+formated;
                            }
                        }
                    }else{
                        tmp="\n"+member.getAsMention()+" invalid: has more than 1 role";
                    }
                    String tmp2=desc+tmp;
                    if(tmp2.length()>2000){
                        embed.setDescription(desc);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        desc="Members checking who have the role "+role.getAsMention()+" and joined 14+ days ago:"+tmp;
                    }else{
                        desc=tmp2;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        private void messageAfterPruned(Member member){
            String fName = "[messageAfterPruned]";
            logger.info(cName + fName);
            try {
                if(member==null){
                    member=gMember;logger.info(cName + fName+"no input member> use author");
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Pruned");embed.setColor(llColors.llColorRed_Dark);
                embed.addField("What happened", "You got pruned from the "+gGuild.getName(),false);
                embed.addField("Reason", "Do to having one role and joined more than 14 days ago",false);
                String icon=gGuild.getIconUrl();
                if(!icon.isBlank()){
                    embed.setThumbnail(icon);
                }
                String invite= lsGuildHelper.lsGetGuildInviteLink(gGuild);
                if(!invite.isBlank()){
                    embed.addField("Re-joining", "You can re-join here [Link]("+invite+")",false);
                }else{
                    embed.addField("Re-joining", "You're always free to re-join if you want to.",false);
                }
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void getListOfNoRoles2Prune() {
            String fName = "[getListOfNoRoles2Prune]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="No roles members";
            members=gGuild.getMembers();
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with this role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,title,"No members to prune!",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    if(roleSize==0){
                        countMembersOnlyHaveThatRole++;
                        if(hasItBeen2WeeksAndNotBooster(member)){
                            members2Print.add(member);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members without roles to prune!",llColors.llColorRed);
                return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(title);embed.setColor(llColors.llColorOrange_Pumpkin);
            String desc="Members that don't have roles and joined 14+ days ago";
            int count=0,displayed=0, size=members2Print.size();
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    OffsetDateTime joined=member.getTimeJoined();
                    logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth());
                    String formatted=joined.format(europeanDateFormatter);
                    logger.info(cName + fName+"formatted="+formatted);
                    String tmp=desc+"\n"+member.getAsMention()+" "+formatted;
                    if(tmp.length()>2000){
                        embed.setDescription(desc);embed.setTitle(title+" ["+displayed+"/"+count+"/"+size);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        desc="Members that don't have roles and joined 14+ days ago"+"\n"+member.getAsMention()+" "+formatted;displayed=1;
                    }else{
                        desc=tmp;displayed++;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            embed.setDescription(desc);embed.setTitle(title+" ["+displayed+"/"+count+"/"+size);
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        private void pruneNoRoleMembers() {
            String fName = "[pruneNoRoleMembers]";
            logger.info(cName + fName);
            if (!llMemberIsManager(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Managers+ only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="Prune new members";
            members=gGuild.getMembers();
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with this role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members to prune!",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    if(roleSize==0){
                        countMembersOnlyHaveThatRole++;
                        if(hasItBeen2WeeksAndNotBooster(member)){
                            members2Print.add(member);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members without roles to prune!",llColors.llColorRed);
                return;
            }
            int count=0;
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    member.kick("Pruned for inactivity by"+gUser.getName()).complete();
                    //messageAfterPrunedNoRoleMember(member);
                    if(count>=10){logger.warn(cName + fName+"safety break");break;}
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,gUser.getAsMention()+" has pruned "+count+" new members who only has those roles and joined 14+ days ago.",llColors.llColorOrange_Coral);
        }
        private void pruneNoRoleMentioned() {
            String fName = "[pruneNoRoleMentioned]";
            logger.info(cName + fName);
            if (!llMemberIsModerator(gMember)) {
                lsMessageHelper.lsSendQuickEmbedMessage(gUser, gTitle, "Moderator+ only allowed to use this command!", llColors.llColorRed);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member>members;
            String title="Prune new members";
            members= gCommandEvent.getMessage().getMentionedMembers();
            if(members.isEmpty()){
                logger.warn(cName + fName+"no members with mentioned");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members mentioned",llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            List<Member>members2Print=new ArrayList<>();
            int countMembersHaveThatRole=0;
            int countMembersOnlyHaveThatRole=0;
            for(Member member:members){
                try {
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName());
                    List<Role>roles=member.getRoles();
                    logger.info(cName + fName+"roles.size="+roles.size());
                    if(roles.isEmpty()){
                        if(hasItBeen2WeeksAndNotBooster(member)){
                            members2Print.add(member);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            logger.info(cName + fName+"countMembersHaveThatRole="+countMembersHaveThatRole);
            logger.info(cName + fName+"countMembersOnlyHaveThatRole="+countMembersOnlyHaveThatRole);
            if(members2Print.isEmpty()){
                logger.warn(cName + fName+"no members just have that one role");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members haze 0 roles",llColors.llColorRed);
                return;
            }
            int count=0;
            for(Member member:members2Print){
                try {
                    count++;
                    logger.info(cName + fName+"member2Print:"+member.getId()+"|"+member.getUser().getName());
                    member.kick("Pruned for inactivity by"+gUser.getName()).complete();
                    //messageAfterPruned(member);
                    if(count>=10){logger.warn(cName + fName+"safety break");break;}
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,gUser.getAsMention()+" has pruned "+count+" members who had no roles and joined 14+ days ago.",llColors.llColorOrange_Coral);
        }
        private void checkIfMentionedIsNoRole4Pruning() {
            String fName = "[checkIfNewMemberIsNoRole4Pruning]";
            logger.info(cName + fName);
            if (!llMemberIsStaff(gMember)) {
                lsQuickMessages.lsSendDeny_RequireStaff(gUser,gTitle);
                logger.warn(cName + fName+"denied");return;
            }
            List<Member> members;
            String title = "Prune new members";
            members = gCommandEvent.getMessage().getMentionedMembers();
            if (members.isEmpty()) {
                logger.warn(cName + fName + "no members with mentioned");
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "No members mentioned", llColors.llColorRed);
                return;
            }
            logger.info(cName + fName+"members="+members.size());
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(title);embed.setColor(llColors.llColorOrange_Pumpkin);
            String desc="Members checking who have no roles and joined 14+ days ago:";
            for(Member member:members){
                try {
                    int roleSize=member.getRoles().size();
                    logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                    OffsetDateTime joined=member.getTimeJoined();
                    String tmp="";
                    if(roleSize==0){
                        OffsetDateTime now=OffsetDateTime.now();
                        logger.info(cName + fName+"time now:"+now.getYear()+"."+now.getMonth()+"."+now.getDayOfMonth()+"[epochSecon="+now.toEpochSecond()+"]");
                        logger.info(cName + fName+"time joined:"+joined.getYear()+"."+joined.getMonth()+"."+joined.getDayOfMonth()+"[epochSecon="+joined.toEpochSecond()+"]");
                        if(now.isAfter(joined)){
                            logger.info(cName + fName+"isafter");
                            long diff=now.toEpochSecond()-joined.toEpochSecond();
                            logger.info(cName + fName+"diff="+diff);
                            logger.info(cName + fName+"checkifbiggerthan=="+twoWeeksToSecond);
                            String formated=joined.format(europeanDateFormatter);
                            logger.info(cName + fName+"formated="+formated);
                            if(diff>twoWeeksToSecond){
                                logger.info(cName + fName+"is bigger");
                                if(!llMemberIsBooster(member)){
                                    tmp="\n"+member.getAsMention()+" valid, join date: "+formated;
                                }else{
                                    logger.info(cName + fName+"is a booster");
                                    tmp="\n"+member.getAsMention()+" invalid: is a booster, join date: "+formated;
                                }

                            }else{
                                tmp="\n"+member.getAsMention()+" invalid: has not yet pass 2 weeks, joined date: "+formated;
                            }
                        }
                    }else{
                        tmp="\n"+member.getAsMention()+" invalid: has roles";
                    }
                    String tmp2=desc+tmp;
                    if(tmp2.length()>2000){
                        embed.setDescription(desc);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        desc="Members checking who have no roles and joined 14+ days ago:"+tmp;
                    }else{
                        desc=tmp2;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            embed.setDescription(desc);
            lsMessageHelper.lsSendMessage(gTextChannel,embed);
        }
        private void messageAfterPrunedNoRoleMember(Member member){
            String fName = "[messageAfterPrunedNoRoleMember]";
            logger.info(cName + fName);
            try {
                if(member==null){
                    member=gMember;logger.info(cName + fName+"no input member> use author");
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Pruned");embed.setColor(llColors.llColorRed_Dark);
                embed.addField("What happened", "You got pruned from the "+gGuild.getName(),false);
                embed.addField("Reason", "Do to having 0 roles and joined more than 14 days ago.",false);
                String icon=gGuild.getIconUrl();
                if(!icon.isBlank()){
                    embed.setThumbnail(icon);
                }
                String invite= lsGuildHelper.lsGetGuildInviteLink(gGuild);
                if(!invite.isBlank()){
                    embed.addField("Re-joining", "You can re-join here [Link]("+invite+")",false);
                }else{
                    embed.addField("Re-joining", "You're always free to re-join if you want to.",false);
                }
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void sendNewMembersPruneJsonFile(){
            String fName = "[sendNewMembersPruneJsonFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                Role role=lsRoleHelper.lsGuildNewMemberRole(gGuild);
                if(role==null){
                    logger.info(cName + fName+"check if has mentioned roles");
                    List<Role>rolesMentioned= gMessage.getMentionedRoles();
                    if(!rolesMentioned.isEmpty()){
                        logger.warn(cName + fName+"use mentioned role");
                        role=rolesMentioned.get(0);
                    }
                }
                if(role==null){
                    logger.warn(cName + fName+"no role to use");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Error:No role selected",llColors.llColorRed);
                    return;
                }
                logger.info(cName + fName+"role="+role.getId()+"|"+role.getName());
                List<Member>members=gGuild.getMembersWithRoles(role);
                if(members.isEmpty()){
                    logger.warn(cName + fName+"no members with this role");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members with the role:"+ role.getAsMention(),llColors.llColorRed);
                    return;
                }
                logger.info(cName + fName+"members="+members.size());
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONArray array=new JSONArray();

                logger.info(cName + fName+"members.Size="+members.size());
                for(Member member : members){
                    try {
                        int roleSize=member.getRoles().size();
                        logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                        if(roleSize==1&&hasItBeen2WeeksAndNotBooster(member)){
                            logger.info(cName + fName+"adding member");
                            JSONObject jsonMember= lsJson4Entity.llGetMemberJsonEntry(member);
                            if(jsonMember!=null)array.put(jsonMember);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="NewMembers2Prune", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }
        private void sendNoRolePruneJsonFile(){
            String fName = "[sendNoRolePruneJsonFile]";
            logger.info(cName + fName);
            if (!llMemberHasPermission_MANAGESERVER(gMember)&&!llMemberIsAdministrator(gMember)&&!lsMemberIsBotOwner(gMember)) {
                lsQuickMessages.lsSendDeny_RequirePermission_MANAGESERVER(gUser,gTitle);
                return;
            }
            try {
                List<Member>members=gGuild.getMembers();
                if(members.isEmpty()){
                    logger.warn(cName + fName+"no members with this role");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"No members to prune!",llColors.llColorRed);
                    return;
                }
                logger.info(cName + fName+"members="+members.size());
                Message  messageProcessing=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,gTitle,"Processing...This might take some time.",llColors.llColorBlue1);
                JSONArray array=new JSONArray();

                logger.info(cName + fName+"members.Size="+members.size());
                for(Member member : members){
                    try {
                        int roleSize=member.getRoles().size();
                        logger.info(cName + fName+"member:"+member.getId()+"|"+member.getUser().getName()+">rolesize="+roleSize);
                        if(roleSize==0&&hasItBeen2WeeksAndNotBooster(member)){
                            logger.info(cName + fName+"adding member");
                            JSONObject jsonMember= lsJson4Entity.llGetMemberJsonEntry(member);
                            if(jsonMember!=null)array.put(jsonMember);
                        }
                    }catch (Exception e){
                        logger.error(cName + fName + ".exception=" + e);
                        logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                InputStream targetStream = new ByteArrayInputStream(array.toString().getBytes());
                OffsetDateTime now=OffsetDateTime.now();
                String fileName="NoRolesMembers2Prune", fileExtension=".txt";
                fileName+="_"+now.getMonth()+"-"+now.getDayOfMonth()+"_"+now.getHour()+"-"+now.getMinute();
                gTextChannel.sendMessage("Here is the json:").addFile(targetStream,fileName+fileExtension).complete();
                targetStream=null;messageProcessing.delete().queue();
            }catch (Exception e){
                logger.error(cName + fName + ".exception=" + e);
                logger.error(cName + fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle, "Error!", llColors.llColorRed);
            }

        }


    }
}
