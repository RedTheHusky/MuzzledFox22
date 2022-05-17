package nsfw.chastity.voting;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.lsGuildHelper;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ChastityVoting extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper {
        Logger logger = Logger.getLogger(getClass());

        lcGlobalHelper gGlobal;
        String gTitle="ChastiVoting",gCommand="chastivoting";
        JSONObject rfEntries;
    public ChastityVoting(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Accessing information  from ChastiKey App.";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
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
    CommandEvent gEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
    lcJSONUserProfile gUserProfile;
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

    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        String[] items;
        Boolean isInvalidCommand = true;
        try{
            if(!isNSFW()){
                blocked();return;
            }
            if(gEvent.getArgs().isEmpty()){
                logger.info(fName+".Args=0");
                help("main");
                gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=true;
                isInvalidCommand=false;
            }else {
                logger.info(fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                logger.info(fName + ".items.size=" + items.length);
                logger.info(fName + ".items[0]=" + items[0]);

            }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
            }

        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
        logger.info(".run ended");
    }
    private boolean isNSFW(){
        String fName = "[isNSFW]";
        if(gTextChannel.isNSFW()){
            return true;
        }
        if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
            return true;
        }
        return false;
    }
    private void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel or server.",llColorRed);
        logger.info(fName);
    }
    private Boolean isTargeted(String command){
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

    Calendar gCalendarToday;
    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+gCommand+" ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(gTitle);
        embed.addField("What it does?","Allows viewing stats from users profile on ChastiKey",false);
        embed.addField("Combination","Use `"+quickSummonWithSpace+"combinations [@Member]` Retrieve previous combinations for a specific user.",false);
        embed.addField("Lockee","Use `"+quickSummonWithSpace+"lockee [@Member]` Retrieve lockee stats for a specific user.",false);
        embed.addField("Keyholder","Use `"+quickSummonWithSpace+"keyholder [@Member]` Retrieve keyholder stats for a specific user.",false);
        embed.addField("Checklock","Use `"+quickSummonWithSpace+"checklock [@Member] <LockID/LockName>` Retrieve checklock stats for a specific user.",false);
        embed.addField("Mentioning","If no user is mentioned the author of the command is used.",false);
        llSendMessage(gUser,embed);
    }
    String keyLocks="locks";
    void SendMistake(Message c, String Content, String Explanation) {
        String fName="SendMistake";
        try {
            if(Explanation==null)Explanation="";
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.addField("Error",Content,true);
            if (!Explanation.equals(""))
            {
                eb.addField("Potential Fix:",Explanation,true);
            }
            eb.setAuthor(c.getAuthor().getName(),null,c.getAuthor().getEffectiveAvatarUrl());
            /*EmbedFooterBuilder efob = new EmbedFooterBuilder();
            efob.Text = DateTime.Now.ToString("dd/MM/yyyy H:mm");
            eb.WithFooter(efob);
            eb.setFooter();*/
            lsMessageHelper.lsSendMessage(c.getTextChannel(),eb);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    void SendError(Message c, String Content){
        String fName="SendError";
        try {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.addField("CRITICAL Error",Content,true);
            eb.addField("Cause:","`"+c.getContentDisplay()+"`",true);
            eb.setAuthor(c.getAuthor().getName(),null,c.getAuthor().getEffectiveAvatarUrl());
           /* EmbedFooterBuilder efob = new EmbedFooterBuilder();
            efob.Text = DateTime.Now.ToString("dd/MM/yyyy H:mm");
            eb.WithFooter(efob);*/
            lsMessageHelper.lsSendMessage(c.getTextChannel(),eb);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    void SendSuccess(Message c, String Content)
    {
        String fName="SendSuccess";
        try {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN);
            eb.addField("Success!",Content,true);
            eb.setAuthor(c.getAuthor().getName(),null,c.getAuthor().getEffectiveAvatarUrl());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public List<ChastityVotingTeachable> Classes=new ArrayList<>();
    public List<ChastityVotingVerification> Vers=new ArrayList<>();
    /*public void AddClass(Message M)
    {
        String fName="AddClass";
        try {
            Classes.Add(new Teachable(((IGuildChannel)M.Channel).Guild.GetUserAsync(M.MentionedUserIds.First()).GetAwaiter().GetResult()
                    , M.Content.Split(' ')[1],
                    ((IGuildChannel)M.Channel).Guild));
            SaveClasses();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }


    public void SaveClasses()
    {
        string output = "";
        foreach (Teachable t in Classes)
        {
            if (t.IsValid())
                output += t.ToString();
        }
        WriteFile(output);
    }

    public void LoadClasses(IGuild g)
    {
        string load = ReadFile();
        while (load != "")
        {
            Classes.Add(Teachable.FromString(g, load, out string s));
            if (!Classes.Last().IsValid())
                Classes.RemoveAt(Classes.Count - 1);
            load = s;
        }
        Console.ForegroundColor = ConsoleColor.Cyan;
        Console.WriteLine("Loaded Classes!");
    }


    public string ReadFile()
    {
        try
        {
            File.OpenText(Path).Close();
        }
        catch (Exception e)
        {
            File.CreateText(Path).Close();
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("Created file");
        }

        return File.ReadAllText(Path);
    }

    public void WriteFile(string Content)
    {
        try
        {
            File.OpenText(Path).Close();
        }
        catch (Exception e)
        {
            File.CreateText(Path).Close();
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("Created file");
        }

        File.WriteAllText(Path, Content);
        Console.ForegroundColor = ConsoleColor.Cyan;
        Console.WriteLine("Saved File!");
    }

    void printclasses() {
        Console.Clear();
        Console.ForegroundColor = ConsoleColor.Cyan;
        foreach (Teachable teachable in Classes)
        {
            Console.WriteLine(teachable.Name);
        }
    }

    public void DeleteClass(string Name)
    {
        for (int i = 0; i < Classes.Count; i++)
        {
            if (Classes[i].Name == Name)
            {
                foreach(ITextChannel tc in Classes[i].CreatedChannels)
                {
                    tc.DeleteAsync();
                }
                Classes[i].ClassRole.DeleteAsync().GetAwaiter().GetResult();
                Classes[i].MainCategory.DeleteAsync().GetAwaiter().GetResult();
                Classes.RemoveAt(i);
                break;
            }
        }
        SaveClasses();
    }

    public void AddVerification(IUserMessage M)
    {
    }

    public void SendResultBack(string Message, Verification React)
    {
    }*/

}
}
