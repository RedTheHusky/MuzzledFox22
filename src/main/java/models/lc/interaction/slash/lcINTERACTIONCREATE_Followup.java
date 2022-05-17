package models.lc.interaction.slash;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import net.dv8tion.jda.annotations.DeprecatedSince;
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
public class lcINTERACTIONCREATE_Followup {
    Logger logger = Logger.getLogger(getClass());
    RawGatewayEvent gEvent;JDA jda;
    long gIdFollowup=0L;
    public lcINTERACTIONCREATE_Followup(RawGatewayEvent event,int message_id){
        String fName="build";
        try {
            logger.info(fName+".creating");
            //-1==@original
            gIdFollowup=message_id;
            gEvent=event;jda=event.getJDA();
            if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                logger.warn(fName+".invaliod type");
                return;
            }
            String strPayload=event.getPayload().toString();
            jsonPayload=new JSONObject(strPayload);
            try {
                jsonData=jsonPayload.getJSONObject(keyData);
                jsonMember=jsonPayload.getJSONObject(keyMember);
                jsonUser=jsonMember.getJSONObject(keyUser);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    JSONObject jsonPayload,jsonData,jsonMember,jsonUser;
    String keyData="data",keyId="id",keyName="name",keyMember="member",keyUser="user";
    String keyGuild_Id="guild_id",keyVersion="version", keyType="type",keyChannel_Id="channel_id",keyToken="token",keyOptions="options",keyValue="value";
    public long getInteractionId(){
        String fName="getInteractionId";
        try {
            return jsonPayload.getLong(keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getCommandId(){
        String fName="getCommandId";
        try {
            return jsonData.getLong(keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getName(){
        String fName="getName";
        try {
            return jsonData.getString(keyName);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getMemberID(){
        String fName="getMemberID";
        try {
            return Long.parseLong(jsonUser.getString(keyId));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public User getUser(){
        String fName="getUser";
        try {
            User jdUser=jda.getUserById(getMemberID());
            return jdUser;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember(){
        String fName="getChannel_id";
        try {
            Member jdMember=jda.getGuildById(getGuildID()).getMemberById(getMemberID());
            /*Member member= new Member() {
                @Override
                public long getIdLong() {
                    return jdMember.getIdLong();
                }

                @NotNull
                @Override
                public String getAsMention() {
                    return jdMember.getAsMention();
                }

                @Override
                public boolean isFake() {
                    return false;
                }

                @NotNull
                @Override
                public User getUser() {
                    return jdMember.getUser();
                }

                @NotNull
                @Override
                public Guild getGuild() {
                    return jdMember.getGuild();
                }

                @NotNull
                @Override
                public EnumSet<Permission> getPermissions() {
                    return jdMember.getPermissions();
                }

                @NotNull
                @Override
                public EnumSet<Permission> getPermissions(@NotNull GuildChannel guildChannel) {
                    return  jdMember.getPermissions(guildChannel);
                }

                @NotNull
                @Override
                public EnumSet<Permission> getPermissionsExplicit() {
                    return  jdMember.getPermissionsExplicit();
                }

                @NotNull
                @Override
                public EnumSet<Permission> getPermissionsExplicit(@NotNull GuildChannel guildChannel) {
                    return  jdMember.getPermissionsExplicit(guildChannel);
                }

                @Override
                public boolean hasPermission(@NotNull Permission... permissions) {
                    return jdMember.hasPermission(permissions);
                }

                @Override
                public boolean hasPermission(@NotNull Collection<Permission> collection) {
                    return jdMember.hasPermission(collection);
                }

                @Override
                public boolean hasPermission(@NotNull GuildChannel guildChannel, @NotNull Permission... permissions) {
                    return jdMember.hasPermission(guildChannel,permissions);
                }

                @Override
                public boolean hasPermission(@NotNull GuildChannel guildChannel, @NotNull Collection<Permission> collection) {
                    return jdMember.hasPermission(guildChannel,collection);
                }

                @NotNull
                @Override
                public JDA getJDA() {
                    return jda;
                }

                @NotNull
                @Override
                public OffsetDateTime getTimeJoined() {
                    return jdMember.getTimeJoined();
                }

                @Override
                public boolean hasTimeJoined() {
                    return jdMember.hasTimeJoined();
                }

                @Nullable
                @Override
                public OffsetDateTime getTimeBoosted() {
                    return jdMember.getTimeBoosted();
                }

                @Nullable
                @Override
                public GuildVoiceState getVoiceState() {
                    return jdMember.getVoiceState();
                }

                @NotNull
                @Override
                public List<Activity> getActivities() {
                    return jdMember.getActivities();
                }

                @NotNull
                @Override
                public OnlineStatus getOnlineStatus() {
                    return jdMember.getOnlineStatus();
                }

                @NotNull
                @Override
                public OnlineStatus getOnlineStatus(@NotNull ClientType clientType) {
                    return jdMember.getOnlineStatus(clientType);
                }

                @NotNull
                @Override
                public EnumSet<ClientType> getActiveClients() {
                    return null;
                }

                @Nullable
                @Override
                public String getNickname() {
                    return null;
                }

                @NotNull
                @Override
                public String getEffectiveName() {
                    return null;
                }

                @NotNull
                @Override
                public List<Role> getRoles() {
                    return null;
                }

                @Nullable
                @Override
                public Color getColor() {
                    return null;
                }

                @Override
                public int getColorRaw() {
                    return 0;
                }

                @Override
                public boolean canInteract(@NotNull Member member) {
                    return jdMember.canInteract(member);
                }

                @Override
                public boolean canInteract(@NotNull Role role) {
                    return jdMember.canInteract(role);
                }

                @Override
                public boolean canInteract(@NotNull Emote emote) {
                    return jdMember.canInteract(emote);
                }

                @Override
                public boolean isOwner() {
                    return jdMember.isOwner();
                }

                @Nullable
                @Override
                public TextChannel getDefaultChannel() {
                    return jdMember.getDefaultChannel();
                }
            };*/

            return jdMember;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getGuildID(){
        String fName="getGuildID";
        try {
            return Long.parseLong(jsonPayload.getString(keyGuild_Id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public Guild getGuild(){
        String fName="getGuild";
        try {
            return jda.getGuildById(jsonPayload.getString(keyGuild_Id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getVersion(){
        String fName="getVersion";
        try {
            return Integer.parseInt(jsonPayload.getString(keyVersion));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getType(){
        String fName="getType";
        try {
            return Integer.parseInt(jsonPayload.getString(keyType));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getChannelId(){
        String fName="getChannel_id";
        try {
            return Long.parseLong(jsonPayload.getString(keyChannel_Id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public TextChannel getChannel(){
        String fName="getChannel";
        try {
            return getGuild().getTextChannelById(jsonPayload.getString(keyChannel_Id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getToken(){
        String fName="getToken";
        try {
            return jsonPayload.getString(keyToken);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSON(){
        String fName="getJSON";
        try {
            return jsonPayload;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getOptions(){
        String fName="getOptions";
        try {
            int i=0;
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyName,"");jsonObject.put(keyValue,"");
            try {
                JSONObject jsonOptions=jsonData.getJSONArray(keyOptions).getJSONObject(0);
                if(jsonOptions.has(keyName)){
                    jsonObject.put(keyName,jsonObject.getString(keyName)+"/"+jsonOptions.getString(keyName));
                }
                if(jsonOptions.has(keyValue)){
                    jsonObject.put(keyValue,jsonObject.getString(keyValue)+"/"+jsonOptions.getString(keyValue));
                }
                while(jsonOptions.has(keyOptions)&&i<50){
                    i++;
                    try {
                        jsonOptions=jsonOptions.getJSONArray(keyOptions).getJSONObject(0);
                        if(jsonOptions.has(keyName)){
                            jsonObject.put(keyName,jsonObject.getString(keyName)+"/"+jsonOptions.getString(keyName));
                        }
                        if(jsonOptions.has(keyValue)){
                            jsonObject.put(keyValue,jsonObject.getString(keyValue)+"/"+jsonOptions.getString(keyValue));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return  jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /*
InteractionType
Ping 1
ApplicationCommand	2
InteractionResponseType
NAME	VALUE	DESCRIPTION
Pong	1	ACK a Ping
Acknowledge	2	ACK a command without sending a message, eating the user's input
ChannelMessage	3	respond with a message, eating the user's input
ChannelMessageWithSource	4	respond with a message, showing the user's input
ACKWithSource	5	ACK a command without sending a message, showing the user's input
*/
    public boolean edit(JSONObject jsonObject){
        String fName="edit";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken()+"/messages/";
            logger.info(fName+".gIdFollowup="+gIdFollowup);
            if(gIdFollowup>0){
                url+=gIdFollowup;
            }else{
                url+="@original";
            }
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.patch(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");return false;
            }else{
                logger.info(fName+".got");return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean delete(){
        String fName="delete";
        try {
            String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken()+"/messages/";
            logger.info(fName+".gIdFollowup="+gIdFollowup);
            if(gIdFollowup>0){
                url+=gIdFollowup;
            }else{
                url+="@original";
            }
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.delete(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");return false;
            }else{
                logger.info(fName+".got");return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
