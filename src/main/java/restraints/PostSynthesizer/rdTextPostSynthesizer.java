package restraints.PostSynthesizer;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.emotes.lcEmote;
import models.lc.webhook.lcWebHook2;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import nsfw.chastity.emlalock.ChastityEmlalock;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;
import restraints.in.*;
import restraints.models.entity.entityRDUserProfile;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.HOODLEVELS;
import restraints.models.*;
import restraints.models.enums.CUFFSARMSLEVELS;
import restraints.models.enums.CUFFSLEGSLEVELS;
import restraints.rdAction;
import restraints.rdPishock;
import userprofiles.entities.rSlaveRegistry;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class rdTextPostSynthesizer implements llGlobalHelper, iRestraints, iGagWork {
    Logger logger = Logger.getLogger(getClass()); Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.GAGUSED_STR);
    String cName="[textPostSynthesizer]";

    public rdTextPostSynthesizer(lcGlobalHelper global, GuildMessageReceivedEvent event){
        logger.info(".run build");
        Runnable r = new runLocal(global,event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        GuildMessageReceivedEvent guildMessageReceivedEvent;lcGlobalHelper gGlobal;
        Guild gGuild; User gUser;Member gMember; TextChannel gTextChannel;
        lcEmote emojiXPunish= new lcEmote(),emojiWand= new lcEmote();
        MessageReference messageReference=null;
        public runLocal(lcGlobalHelper global, GuildMessageReceivedEvent event) {
            logger.info(".run build");
            guildMessageReceivedEvent =event;gGlobal=global;
        }
        boolean wasShocked=false;
        @Override
        public void run() {
            String fName = "[run]";logger.info( fName +".run start");
            logger.info(".run ended");
        }
		/*deleted main functions*/
    }
}
