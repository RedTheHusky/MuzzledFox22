package forRemoval.social.SFW;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.*;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import search.e621;
import org.apache.log4j.Logger;
import search.entities.lcE621;

import java.util.List;

public class social621SFW extends Command implements llGlobalHelper,llMessageHelper{
    String cName="[social621]";
    lcGlobalHelper gGlobal;
    EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="social621SFW";
    public social621SFW(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "socialSWF621";
        this.help = "Random sfw image from e621 for social interaction :`boop621`, `cuddle621`,`hold621`,`hug621`,`kiss621`,`lick621`,`nuzzle621`,`pat621`";
        this.aliases = new String[]{
                "boop621",
                "cuddle621",
                "hold621",
                "hug621",
                "kiss621",
                "lick621",
                "nuzzle621",
                "pat621"
        };
        this.guildOnly = true;
        this.category= llCommandCategory_SocialSFW;
        this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(cName + fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gEvent;
        private User gUser;
        private Member gMember;
        private Guild gGuild;
        private TextChannel gTextChannel;
        String gTitle = "Guild Utility";
        String gTag="fuck";
        Member gTarget=null;
        public runLocal(CommandEvent ev) {
            logger.info(cName + ".run build");
            gEvent = ev;
        }
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + fName);
            boolean isInvalidCommand = true;
            gUser = gEvent.getAuthor();
            gMember = gEvent.getMember();
            gGuild = gEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            if(isCommand(gEvent.getMessage().getContentRaw())){
                isInvalidCommand = false;
                getTarget(gEvent.getMessage());
                get();
            }else
            if (gEvent.getArgs().isEmpty()) {
                logger.info(cName + fName + ".Args=0");
            } else {
                logger.info(cName + fName + ".Args");
                String[] items = gEvent.getArgs().split("\\s+");
                if (items.length>=2&&items[0].equalsIgnoreCase("help")) {
                    help(items[1]);
                    isInvalidCommand = false;
                }
                else if (items[0].equalsIgnoreCase("help")) {
                    help("main");
                    isInvalidCommand = false;
                }
            }
            logger.info(cName + fName + ".deleting op message");
            //llQuickCommandMessageDelete(gEvent);
            if (isInvalidCommand) {
                //llSendQuickEmbedChannel(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
            }
        }
        JSONObject gTags=new JSONObject();
        private void help(String command) {
            String fName = "help";
            logger.info(cName + fName + ".command:" + command);
            String quickSummonWithSpace = llPrefixStr + commandPrefix+" ";
            String desc = "Test";
            llSendQuickEmbedMessage(gTextChannel, gTitle,"Sent command list to DM!",llColorBlue1);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColorBlue1);
            embed.addField("Test",desc,false);
            llSendMessage(gUser,embed);
        }
        private boolean isCommand(String item){
            String fName = "[isCommand]";
            logger.info(cName + fName);
            try{
                logger.info(cName + fName + ".getTextualPrefix="+gEvent.getClient().getTextualPrefix());
                String[] items = llRemovePrefixFromContentRaw(item).split("\\s+");
                logger.info(cName + fName + ".items.length="+items.length);
                if (items.length==0){
                    return  false;
                }
                gTags.put("boop621","boop");
                gTags.put("cuddle621","cuddle snuggle");
                gTags.put("hold621","hand_holding");
                gTags.put("hug621","hug");
                gTags.put("kiss621","kiss");
                gTags.put("lick621","lick");
                gTags.put("nuzzle621","nuzzle");
                gTags.put("pat621","pat");

                String key=items[0].toLowerCase();
                logger.info(cName + fName + ".key="+key);
                if(gTags.has(key)){
                    logger.info(cName + fName + ".found key");
                    gTag=gTags.getString(key);
                    return true;
                }
                logger.info(cName + fName + ".did not found key");
                return  false;
            }catch (Exception e) {
                logger.error(cName + fName+"exception: "+e);
                return  false;
            }
        }
        private void get() {
            String fName = "[get]";
            logger.info(cName + fName);
            try{
                lcE621 e621=new lcE621();
                JSONObject image= e621.getImageJSON("safe "+gTag);
                logger.info(cName + fName + "image=" +image.toString());
                EmbedBuilder embed=new EmbedBuilder();
                String url="";
                JSONArray artists=new JSONArray();String rating="";
                String id="";
                if(image.has("error")){
                    if(image.has("error")){ logger.error(cName+fName+".image error:"+image.getString("error"));  }
                    return;
                }
                if(image.has("id")){ id=image.getString("id"); logger.info(cName + fName + "id=" +id);}
                if(image.has("artist")){ artists=image.getJSONArray("artist"); logger.info(cName + fName + "artist=" +artists.toString());}
                if(image.has("rating")){ rating=image.getString("rating");logger.info(cName + fName + "rating=" +rating);}
                if(image.has("file")&&!image.isNull("file")&&image.getJSONObject("file").has("url")&&!image.getJSONObject("file").isNull("url")){ url=image.getJSONObject("file").getString("url");logger.info(cName + fName + "url=" +url);}

                if(rating==null||rating.isEmpty()){
                    logger.warn(cName+fName+".image rating is null");return;
                }
                if(!rating.equalsIgnoreCase("s")){
                    logger.warn(cName+fName+".image rating is invalid");return;
                }
                if(url!=null&&!url.isEmpty()){
                    logger.info(cName + fName + "url=" +url);
                    embed.setImage(url);
                }
                String desc="";
                String person1="";
                String person2="";
                if(gTarget==null||gMember==gTarget){
                    person1=gEvent.getSelfUser().getAsMention(); person2=gUser.getAsMention();
                }else{
                    person1=gUser.getAsMention(); person2=gTarget.getAsMention();
                }
                desc=person1+" kisses "+person2+". Will they get marrieds?";
                logger.info(cName + fName + "desc=" +desc);
                if(id!=null||!id.isEmpty()){
                    logger.info(cName + fName + "id=" +id);
                    desc+="\n[Source](https://e621.net/post/show/"+id+")";
                }
                embed.setDescription(desc);
                embed.setColor(llColorPink2);
                llSendMessage(gTextChannel,embed);
            }
            catch (Exception e) {
                logger.error(cName + fName+"exception: "+e);
            }
        }
        private Boolean getTarget(Message message) {
            String fName = "[getTarget]";
            List<Member> mentions = message.getMentionedMembers();
            if (!mentions.isEmpty()) {
                Member m=mentions.get(0);
                User u= m.getUser();
                logger.info(cName + fName + "mention:" +u.getId()+"|"+u.getName());
                gTarget=m;
                return true;
            }
            logger.info(cName + fName + "mention:null");
            return false;
        }











    }
}
