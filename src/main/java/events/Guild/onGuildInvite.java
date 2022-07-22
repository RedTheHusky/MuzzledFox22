package events.Guild;



import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onGuildInvite extends ListenerAdapter {
    Logger logger = Logger.getLogger(getClass());
    public onGuildInvite(){
        logger.debug(".constructor");
    }
    lcGlobalHelper global;
    public onGuildInvite(lcGlobalHelper g){
        logger.debug(".constructor");
        global=g;
    }

    public  void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event){
        String fName="onGuildInviteCreate";
        try {
            Invite invite=event.getInvite(); Invite.Channel channel;
            Invite.Guild guild;
            User inviter;
            String code=""; int maxage=0,maxusage=0;
            boolean isTemporary=false, isExpanded=false;
            JSONObject jsonObject=new JSONObject();
            try {
                guild= invite.getGuild();
                jsonObject.put("guild_id",guild.getId());
                jsonObject.put("guild_name",guild.getName());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                inviter=invite.getInviter();
                jsonObject.put("inviter_id",inviter.getId());
                jsonObject.put("inviter_name",inviter.getName()+"#"+inviter.getDiscriminator());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                code=invite.getCode();
                jsonObject.put("code",code);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                maxage=invite.getMaxAge();
                jsonObject.put("maxage",maxage);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                maxusage=invite.getMaxUses();
                jsonObject.put("maxusage",maxusage);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                isTemporary=invite.isTemporary();
                jsonObject.put("isTemporary",isTemporary);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                isExpanded=invite.isExpanded();
                jsonObject.put("isExpanded",isExpanded);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                channel=invite.getChannel();
                jsonObject.put("channel_id",channel.getId());
                jsonObject.put("channel_name",channel.getName());
                jsonObject.put("channel_type_id",channel.getType().getId());
                jsonObject.put("channel_type_name",channel.getType().name());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.warn(fName+jsonObject.toString());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void onGuildInviteDelete(@Nonnull GuildInviteDeleteEvent event){
        String fName="onGuildInviteDelete";
        try {
            logger.info(fName+".guild="+event.getGuild().getId()+" code="+event.getCode()+" channelID="+event.getChannel().getId());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


}
