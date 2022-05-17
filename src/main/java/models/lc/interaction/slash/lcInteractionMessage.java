package models.lc.interaction.slash;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;

@Deprecated
@ForCleanup1
@DeprecatedSince("11/24/21")
public class lcInteractionMessage {
    //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
    Logger logger = Logger.getLogger(getClass());
    int flags=0; int type=0;
    boolean tts=false;
    String content="";
    JSONArray embeds=new JSONArray();

    public lcInteractionMessage(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


}
