package util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import models.ll.llMessageHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.*;

public class information extends Command implements llMessageHelper, llGlobalHelper {
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass()); String cName="[information]";
    public information(lcGlobalHelper g) {
        String fName = "[constructor]";
        logger.info(fName);
        gGlobal=g;gWaiter = g.waiter;
        this.name = "Utility-Information";
        this.help = "information";
        this.aliases = new String[]{"info","utilityinformation"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.hidden=true;
        this.category=llCommandCategory_UtilityInHouse;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();

    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel;private Message gMessage;

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
        String gTitle = "Extra Information";
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(".run start");
            try {
                boolean isInvalidCommand = true;

                String[] items;
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    String raw= gCommandEvent.getMessage().getContentStripped();
                    gCommandEvent.getTextChannel().sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    logger.info(fName + ".Args");
                    items = gCommandEvent.getArgs().split("\\s+");
                    logger.info(fName + ".items.size=" + items.length);
                    logger.info(fName + ".items[0]=" + items[0]);
                    if(items[0].equalsIgnoreCase("darules")){
                        daRules();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("selfroles")){
                        selfRoles();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("sticker")||items[0].equalsIgnoreCase("stickers")){
                        stickers();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("boost")){
                        boost();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("whatdoesthefoxsay")||items[0].equalsIgnoreCase("foxsay")){
                        whatDoesTheFoxSay();isInvalidCommand=false;
                    }
                    if(items[0].equalsIgnoreCase("staffteam")){
                        staffTeam();isInvalidCommand=false;
                    }
                }
                logger.info(fName+".deleting op message");
                llMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gUser,"Info","You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void daRules(){
            String fName="[daRules]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**Da Rules**");
            String desc="N/A";
            if(gGuild.getId().equals(lsCustomGuilds.lsGuildKeyBdsm)||gGuild.getId().equals(lsCustomGuilds.lsGuildKeyChastity)||gGuild.getId().equals(lsCustomGuilds.lsGuildKeySnuff)){
                desc="\nADULT MEMBER, 18+";
                desc+="\nBE RESPECTFUL!";
                desc+="\nNO TARGETING, NOR HUNTING!";
                desc+="\nKEEP TOPICS TO THE APPROPRIATE TOPICS!";
                desc+="\nNO BLACKMAIL!";
                desc+="\nNO ADVERTISING!";
                desc+="\nNO RAIDS!";
                desc+="\nPLEASE TRY TO AVOID MASS MENTIONG STAFF FOR NOT URGENTLY IMPORTAT SITUATION!";
            }else
            if(gGuild.getId().equals(lsCustomGuilds.lsGuildKeyAsylum)||gGuild.getId().equals(lsCustomGuilds.lsGuildKeyPrison)){
                desc="\nBE RESPECTFUL!";
                desc+="\nNO TARGETING, NOR HUNTING!";
                desc+="\nKEEP TOPICS TO THE APPROPRIATE TOPICS!";
                desc+="\nNO ADVERTISING!";
                desc+="\nNO RAIDS!";
                desc+="\nPLEASE TRY TO AVOID MASS MENTIONG STAFF FOR NOT URGENTLY IMPORTAT SITUATION!";
            }
            embed.setThumbnail("https://vignette.wikia.nocookie.net/fairlyoddparents/images/1/1e/Da_Rules.png/revision/latest/scale-to-width-down/200?cb=20180503070000&path-prefix=en");
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void selfRoles(){
            String fName="[selfRoles]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**Getting the roles yourself | Assigning self-roles**");
            String desc="N/A";
            if(gGuild.getId().equals(lsCustomGuilds.lsGuildKeyBdsm)){
                TextChannel roleMenu=gGuild.getTextChannelById("577482578022498315");
                TextChannel roleConsole=gGuild.getTextChannelById("489063812738318366");
                desc="\n**What do you mean?**\nYou can get the roles yourself, no need to ask a staff to assign the roles. Just visiting the channels"+roleMenu.getAsMention()+" and "+roleConsole.getAsMention();
                desc+="\n**roles-menu**\nHere the most used roles are displayed in a more user friendly way. No need to type, just react the the reaction the role menu displays. To remove a role simple de-select your selected reaction.";
                desc+="\n**roles-request-console**\nHere you need to type the roles you want. `furiam <role name>` will add the role you mention while `furiamnot <role name>` will remove the mentioned role. Use `furselfroles list` to get the role list you can apply yourself.";
                desc+="\n**I dont see all the roles in roles-menu**\nOf course not, do to the amount of the roles we have we only made role menu for the basic of them. Use `furiam <role name>` for roles we dont have in the roles-menu.";
                desc+="\n**I'm not getting any roles**\nCheck out if the bots are online and responsive. Check out if you are typing it correctly. Ask staff help if you can't figure out the issue or the bots for the self roles are off-line.";
            }
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void stickers(){
            String fName="[stickers]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**Stickers for Discord**");
            String desc="";
            Member stickerbot=gGuild.getMemberById("224415693393625088");
            logger.info(fName + ".stickerbot:"+stickerbot.getEffectiveName());
            desc="**What is this main.java.bot?**\n" +
                    "This main.java.bot implements one of the Telegram feature, the Stickers\n" +
                    "**What are Stickers?**\n" +
                    "Stickers are high-definition images intended to provide more expressive emojis.\n" +
                    "**How do i use it?**\n" +
                    "You use it by simple typing in the sticker name ex: :tiedup:"+
                    "Server stickers:\n"+
                    "This https://stickersfordiscord.com/server/"+gGuild.getId()+" is the list of stickers that everyone from the server can use.\n"+
                    "Personal Stickers:\n" +
                    "You can create your own personal sticker than only you can use where the main.java.bot is added. Visit the site to know how to make your own sticker https://stickersfordiscord.com/ \n" +
                    "Example:\n" +
                    "The image bellow us is an example by using the command `:tiedup:`";
            embed.setImage("https://s3.us-east-2.amazonaws.com/stickers-for-discord/475589373791043594-1557676546599-tiedup.png");
            embed.setThumbnail("https://cdn.discordapp.com/avatars/224415693393625088/8f6a0ca7e883f87241b02ba8e1328f34.png");
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void boost(){
            String fName="[boost]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**Discord Nitro Server Boosting**");
            String desc="";
            desc="**What is that?**\n" +
                    "As the name say, boosting the server helping the server unlock perks from better audio quality, more emoji slots and much more.\n" +
                    "**What do i need?**\n" +
                    "You need a Nitro account, not the Classic Nitro one that is 5$/month unless you are an early supporter that allows exception, you need the 10$/month one.\n" +
                    "**How do i use it?**\n" +
                    "You need to use the Desktop version of App as its not implemented on the phone version. Go to your User Settings, go to 'Discord Nitro', then 'Select a Server'. You can choose ours if you want to support this server :nkogasm:\n" +
                    "**I don't see the option to boost the server.**\n" +
                    "Ensure your account has that perk and that you're using the Desktop version of the App and not the phone version.\n" +
                    "**Level 1 (2 boost is required to unlock it):**\n" +
                    "+50 Emoji Slots (for a total of 100 emojis)\n" +
                    "128 Kbps Audio Quality\n" +
                    "Go Live streams boosted to 720P 60FPS\n" +
                    "Custom Server Invite Background\n" +
                    "Animated Server Icon\n" +
                    "**Level 2 (15 boost is required to unlock it):**\n" +
                    "+50 Emoji Slots (for a total of 150 emojis)\n" +
                    "256 Kbps Audio Quality\n" +
                    "Go Live streams boosted to 1080P 60FPS\n" +
                    "Server Banner\n" +
                    "50MB Upload Limit for all members (server only)\n" +
                    "**Level 3 (30 boost is required to unlock it):**\n" +
                    "Everything in Level 1 AND Level 2 AND…..\n" +
                    "+100 Emoji Slots (for a total of 250 emojis)\n" +
                    "384Kbps Audio Quality\n" +
                    "100MB Upload Limit for all members (server only)\n" +
                    "Vanity URL\n" +
                    "https://support.discordapp.com/hc/en-us/articles/360028038352-Server-Boosting-\n";
            embed.setThumbnail("https://cdn.discordapp.com/emojis/585678290476597257.png");
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }
        private void whatDoesTheFoxSay(){
            String fName="[whatDoesTheFoxSay]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**What does the fox say...**");
            embed.setImage("https://cdn.discordapp.com/attachments/587945871593832488/588055398142181381/maxresdefault_1.jpg");
            llSendMessage(gTextChannel,embed);
        }
        private void staffTeam(){
            String fName="[staffTeam]";
            logger.info(fName);
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorPurple1);
            embed.setTitle("**STAFF TEAM**");
            String desc="";
            Map<String,Member> membersOwners= new LinkedHashMap<>();
            Map<String,Member> membersAdmin= new LinkedHashMap<>();
            Map<String,Member> membersManager= new LinkedHashMap<>();
            Map<String,Member> membersMod= new LinkedHashMap<>();
            Map<String,Member> membersStaff= new LinkedHashMap<>();
            membersOwners.put("m_249197641181822977",gGuild.getMemberById("249197641181822977"));

            List<Member> items=new ArrayList<>();
            items=gGuild.getMembersWithRoles(gGuild.getRoleById("475747697496948785"));
            for(Member item : items){
                membersAdmin.put("m_"+item.getId(),item);
            }
            items=gGuild.getMembersWithRoles(gGuild.getRoleById("476020301939867658"));
            for(Member item : items){
                membersManager.put("m_"+item.getId(),item);
            }
            items=gGuild.getMembersWithRoles(gGuild.getRoleById("475612401711185951"));
            for(Member item : items){
                membersMod.put("m_"+item.getId(),item);
            }
            items=gGuild.getMembersWithRoles(gGuild.getRoleById("476024097155842079"));
            for(Member item : items){
                membersStaff.put("m_"+item.getId(),item);
            }
            Emote wiremuzzle=gGuild.getEmoteById("476368594780028938");

            desc+="Server owner "+wiremuzzle.getAsMention()+":"+membersOwners.get("m_249197641181822977").getAsMention();
            desc+="\n\nAdmin:crown:: ";
            int i=0;
            i=0;
            for(Map.Entry<String,Member> entry : membersAdmin.entrySet()) {
                if(!membersOwners.containsKey(entry.getKey())){
                    if(i>0){
                        desc+=", "+entry.getValue().getAsMention();
                    }else{
                        desc+=entry.getValue().getAsMention();
                    }
                    i++;
                }
            }
            desc+="\n\nManagers:wrench:: ";
            i=0;
            for(Map.Entry<String,Member> entry : membersManager.entrySet()) {
                if(!membersOwners.containsKey(entry.getKey())&&!membersAdmin.containsKey(entry.getKey())){
                    if(i>0){
                        desc+=", "+entry.getValue().getAsMention();
                    }else{
                        desc+=entry.getValue().getAsMention();
                    }
                    i++;
                }
            }
            desc+="\n\nMods:police_officer:: ";
            i=0;
            for(Map.Entry<String,Member> entry : membersMod.entrySet()) {
                if(!membersManager.containsKey(entry.getKey())&&!membersOwners.containsKey(entry.getKey())&&!membersAdmin.containsKey(entry.getKey())){
                    if(i>0){
                        desc+=", "+entry.getValue().getAsMention();
                    }else{
                        desc+=entry.getValue().getAsMention();
                    }
                    i++;
                }
            }
            desc+="\n\nStaff:tophat:: ";
            i=0;
            for(Map.Entry<String,Member> entry : membersStaff.entrySet()) {
                if(!membersMod.containsKey(entry.getKey())&&!membersManager.containsKey(entry.getKey())&&!membersOwners.containsKey(entry.getKey())&&!membersAdmin.containsKey(entry.getKey())){
                    if(i>0){
                        desc+=", "+entry.getValue().getAsMention();
                    }else{
                        desc+=entry.getValue().getAsMention();
                    }
                    i++;
                }
            }
            embed.setThumbnail(gGuild.getIconUrl());
            embed.setDescription(desc);
            llSendMessage(gUser,embed);
        }

    }

}
