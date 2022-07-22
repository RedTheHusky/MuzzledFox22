package util.inhouse;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class utilityTransport extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper {
    String cName="[Transport]";
    String[] items;
    lcGlobalHelper global;

    Logger logger = Logger.getLogger(getClass());
    public utilityTransport(lcGlobalHelper g)
    {
        String fName="constructor";
        logger.info(cName+fName);
        global=g;
        this.name = "Utility-Transport";
        this.aliases = new String[]{"bus","transport","utilitybus"};
        this.help = "Transfer members between servers!";
        this.guildOnly = true;
        this.category=llCommandCategory_BuildAlpha;
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="execute";
        logger.info(cName+fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();

    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel;private Message gMessage;
        String gTitle = "Transport";
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
            logger.info(cName + ".run start");
            boolean isInvalidCommand = true;
            String[] items;
            try {
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(cName+fName+".Args=0");
                    help("main");isInvalidCommand=false;
                }else {
                    logger.info(cName + fName + ".Args");
                    if(!llMemberIsStaff(gCommandEvent.getMember())){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),"Bus utility","Denied!", llColorRed);
                        if(gCommandEvent.getMessage().isFromGuild()){
                            logger.info(cName+fName+".deleting op message");
                            gCommandEvent.getMessage().delete().queue();
                        }
                        return;
                    }
                    // split the choices on all whitespace
                    items = gCommandEvent.getArgs().split("\\s+");
                    String item = items[0];
                    logger.info(cName+fName+".item[0]="+item);
                    if (item.equalsIgnoreCase("guilds")) {
                        if (items.length == 1) {
                            getGuilds();isInvalidCommand=false;
                        }else
                        if (items.length == 2) {
                            item = items[1];
                            logger.info(cName+fName+".item[1]="+item);
                            if (item.equalsIgnoreCase("invites")){
                                getGuildsInvites();isInvalidCommand=false;
                            }
                        }
                    }else
                    if (item.equalsIgnoreCase("verify")) {
                        verifyUser2Guilds();isInvalidCommand=false;
                    }
                    if (item.equalsIgnoreCase("verify")) {
                        help("main");isInvalidCommand=false;
                    }
                }
                logger.info(cName+fName+".deleting op message");
                llMessageDelete(gCommandEvent);
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gCommandEvent.getAuthor(),"Bus utility","You provided an incorrect command!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        private void help(String command){
            String fName="help";
            logger.info(cName + fName + ".command:"+command);
            EmbedBuilder embed= new EmbedBuilder();
            String quickSummonWithSpace=llPrefixStr+"bus ";
            embed.setTitle(gTitle); embed.setColor(llColorBlue1);
            embed.addField("Guilds","\n`"+quickSummonWithSpace+"guilds <atr>`"+"\natr: invites",false);
            embed.addField("Verify","\n`"+quickSummonWithSpace+"verify`",false);
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            llSendMessage(gUser,embed);
        }
        private void getGuilds(){
            String fName="getGuilds";
            logger.info(cName+fName);
            Map<String, Guild> guilds=global.getGuildMap4JDA();
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(8388863);
            embed.setTitle("Guilds List");
            String desc="";
            for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
                String key = entry.getKey();
                Guild value = entry.getValue();
                if(value!=null){
                    logger.info(cName+fName+"entry :"+key+"|"+value.getId()+"|"+value.getName());
                    desc+="\n"+value.getName();
                }
            }
            embed.setDescription(desc);
            llSendMessage(gTextChannel,embed);
        }
        private void getGuildsInvites()  {
            String fName="getGuildsInvites";
            logger.info(cName+fName);
            Map<String, Guild> guilds=global.getGuildMap4JDA();
            for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
                EmbedBuilder embed= new EmbedBuilder();
                embed.setColor(8388863);
                String desc="";
                String key = entry.getKey();
                Guild value = entry.getValue();
                if(value!=null){
                    logger.info(cName+fName+"entry :"+key+"|"+value.getId()+"|"+value.getName());
                    embed.setTitle(value.getName());
                    try{
                        List <Invite> invites=value.retrieveInvites().complete(true);
                        for(Invite item :  invites){
                            desc+="\n"+item.getUrl();
                        }
                        embed.setDescription(desc);
                        llSendMessage(gTextChannel,embed);
                    }catch (Exception e){
                        logger.info(cName+fName+"error:"+e);
                    }
                }
            }
        }
        private void verifyUser2Guilds()  {
            String fName="verifyUser2Guilds";
            logger.info(cName+fName);
            Map<String, Guild> guilds=global.getGuildMap4JDA();
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(8388863);
            embed.setTitle("Guilds Verification");
            String desc="";
            Map<String, String> fInGuild= new LinkedHashMap<>();
            Map<String, String> fMember= new LinkedHashMap<>();
            Map<String, String> fStaff= new LinkedHashMap<>();
            for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
                String key = entry.getKey();
                Guild guild = entry.getValue();
                if(guild!=null){
                    String guildName=guild.getName();
                    String guildID=guild.getId();
                    String guildKey="g_"+guildID;
                    logger.info(cName+fName+"guild :"+key+"|"+guildID+"|"+guildName);
                    try{
                        if(guild.isMember(gUser)){
                            Member member=guild.getMember(gUser);
                            fInGuild.put(guildKey, guildName);
                            List<Role>roles=member.getRoles();
                            for(Role role : roles){
                                logger.info(cName+fName+"role:"+role.getId()+"|"+role.getName());
                                String roleId=role.getId();
                                if(roleId.equals(lsCustomGuilds.lsGuildAsylumStaffKey)){
                                    logger.info(cName+fName+"found staff");
                                    fStaff.put(guildKey,guildName );
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildBdsmStaffKey)){
                                    logger.info(cName+fName+"found staff");
                                    fStaff.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildChastityStaffKey)){
                                    logger.info(cName+fName+"found staff");
                                    fStaff.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildPrisonStaffKey)){
                                    logger.info(cName+fName+"found staff");
                                    fStaff.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildSnuffStaffKey)){
                                    logger.info(cName+fName+"found staff");
                                    fStaff.put(guildKey,guildName);
                                }

                                if(roleId.equals(lsCustomGuilds.lsGuildAsylumMemberKey)||roleId.equals(lsCustomGuilds.lsGuildAsylumNewMemberKey)){
                                    logger.info(cName+fName+"found member");
                                    fMember.put(guildKey,guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildBdsmMemberKey)||roleId.equals(lsCustomGuilds.lsGuildBdsmNewMemberKey)){
                                    logger.info(cName+fName+"found member");
                                    fMember.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildChastityMemberKey)||roleId.equals(lsCustomGuilds.lsGuildChastityNewMemberKey)){
                                    logger.info(cName+fName+"found member");
                                    fMember.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildPrisonMemberKey)||roleId.equals(lsCustomGuilds.lsGuildPrisonNewMemberKey)){
                                    logger.info(cName+fName+"found member");
                                    fMember.put(guildKey, guildName);
                                }else
                                if(roleId.equals(lsCustomGuilds.lsGuildSnuffMemberKey)||roleId.equals(lsCustomGuilds.lsGuildSnuffNewMemberKey)){
                                    logger.info(cName+fName+"found member");
                                    fMember.put(guildKey, guildName);
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.info(cName+fName+"exception:"+e);
                    }

                }
            }
            logger.info(cName+fName+"fStaff.size"+fStaff.size());
            logger.info(cName+fName+"fMember.size"+fMember.size());
            logger.info(cName+fName+"fInGuild.size"+fInGuild.size());
            if(!fStaff.isEmpty()){
                desc+="\n**Staff:**";
                for(Map.Entry<String, String> entry : fStaff.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    logger.info(cName+fName+"fStaff["+key+"]="+value);
                    desc+="\n"+value;
                }
            }
            if(!fMember.isEmpty()){
                desc+="\n**Approved:**";
                for(Map.Entry<String, String> entry : fMember.entrySet()) {
                    String key = entry.getKey();
                    logger.info(cName+fName+"fMember["+key+"].checking");
                    if(!fStaff.containsKey(key)){
                        String value = entry.getValue();
                        logger.info(cName+fName+"fMember["+key+"]="+value);
                        desc+="\n"+value;
                    }
                }
            }
            if(!fMember.isEmpty()){
                desc+="\n**Member:**";
                for(Map.Entry<String, String> entry : fInGuild.entrySet()) {
                    String key = entry.getKey();
                    logger.info(cName+fName+"fInGuild["+key+"].checking");
                    if(!fStaff.containsKey(key)&&!fMember.containsKey(key)){
                        String value = entry.getValue();
                        logger.info(cName+fName+"fInGuild["+key+"]="+value);
                        desc+="\n"+value;
                    }
                }
            }
            desc+="\n**Available:**";
            for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
                String key = entry.getKey();
                if(!fStaff.containsKey(key)&&!fMember.containsKey(key)&&!fInGuild.containsKey(key)){
                    Guild guild = entry.getValue();
                    desc+="\n"+guild.getName();
                }
            }
            embed.setDescription(desc);
            llSendMessage(gTextChannel,embed);
        }
    }


}
