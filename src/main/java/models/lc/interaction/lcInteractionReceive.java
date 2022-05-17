package models.lc.interaction;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import models.lc.discordentities.basicobjects.lcMemberObject;
import models.lc.discordentities.basicobjects.lcUserObject;
import models.lc.discordentities.lcMyEmbedBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class lcInteractionReceive {
    Logger logger = Logger.getLogger(getClass());
    protected JDA jda;
    public lcInteractionReceive(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcInteractionReceive(RawGatewayEvent event){
        String fName="build";
        try {
            set(event);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected boolean set(RawGatewayEvent event){
        String fName="set";
        try {
            logger.info(fName+".clearing");
            jsonPayload=new JSONObject();jsonData=new JSONObject();
            JSONObject jsonMember=new JSONObject(),jsonUser=new JSONObject();
            logger.info(fName+".creating");
            jda=event.getJDA();
            if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                logger.warn(fName+".invaliod type");
                return false;
            }
            String strPayload=event.getPayload().toString();
            jsonPayload=new JSONObject(strPayload);
            try {
                logger.info(fName+".jsonPayload="+jsonPayload.toString());
                if(jsonPayload.has(keyData)){jsonData=jsonPayload.getJSONObject(keyData);}
                logger.info(fName+".jsonData="+jsonData.toString());
                if(jsonPayload.has(keyMember)){jsonMember=jsonPayload.getJSONObject(keyMember);
                    logger.info(fName+".jsonMember="+jsonMember.toString());
                    if(jsonMember.has(keyUser))jsonUser=jsonMember.getJSONObject(keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                    memberObject=new lcMemberObject(jda,jsonMember);
                    userObject=new lcUserObject(jda,jsonUser);
                }
                if(jsonPayload.has(keyUser)){jsonUser=jsonPayload.getJSONObject(keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                    userObject=new lcUserObject(jda,jsonUser);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected JSONObject jsonPayload=new JSONObject(), jsonData=new JSONObject();
    protected lcMemberObject memberObject=new lcMemberObject();
    protected lcUserObject userObject=new lcUserObject();

    static public String keyData="data",keyId="id",keyMember="member",keyUser="user";
    static public String keyGuild_Id="guild_id",keyVersion="version", keyType="type",keyChannel_Id="channel_id",keyToken="token";
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
    public String getUserId(){
        String fName="getUserId";
        try {
            return userObject.getId();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getUserIdAsLong(){
        String fName="getUserIDAsLong";
        try {
            return Long.parseLong(getUserId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getMemberId(){
        String fName="getMemberId";
        try {
            return memberObject.getId();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getMemberIdAsLong(){
        String fName="getMemberIDAsLong";
        try {
            return Long.parseLong(getMemberId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean isGuild(){
        String fName="[isGuild]";
        try {
            String tmp=getGuildId();
            if(tmp==null){
                logger.warn(fName + ".guild is null>false");
                return false;
            }
            if(tmp.isBlank()){
                logger.warn(fName + ".guild is blank>false");
                return false;
            }
            logger.info(fName + ".default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public User getUser(){
        String fName="getUser";
        try {
            User jdUser=jda.getUserById(getUserId());
            return jdUser;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember(){
        String fName="getMember";
        try {
            String memberid=getMemberId();
            logger.info(fName + ".memberid="+memberid);
            Member jdMember=jda.getGuildById(getGuildIDAsLong()).getMemberById(memberid);
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
    public String getGuildId(){
        String fName="getGuildId";
        try {
            return jsonPayload.getString(keyGuild_Id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getGuildIDAsLong(){
        String fName="getGuildIDAsLong";
        try {
            return Long.parseLong(getGuildId());
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
            return jsonPayload.getInt(keyVersion);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getType(){
        String fName="getType";
        try {
            return jsonPayload.getInt(keyType);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getChannelId(){
        String fName="getChannel_id";
        try {
            return jsonPayload.getString(keyChannel_Id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getChannelIdAsLong(){
        String fName="getChannelIdAsLong";
        try {
            return Long.parseLong(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public TextChannel getTextChannel(){
        String fName="[getTextChannel]";
        try {
            return getGuild().getTextChannelById(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public VoiceChannel getVoiceChannel(){
        String fName="[getVoiceChannel]";
        try {
            return getGuild().getVoiceChannelById(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public StoreChannel getStoreChannel(){
        String fName="[getStoreChannel]";
        try {
            return getGuild().getStoreChannelById(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GuildChannel getGuildChannel(){
        String fName="[getGuildChannel]";
        try {
            return getGuild().getGuildChannelById(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel(){
        String fName="[getPrivateChannel]";
        try {
            return jda.getPrivateChannelById(getChannelId());
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
    public JSONObject getData(){
        String fName="getData";
        try {
            return  jsonData;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    boolean isCallbacked=false;
    long gIdFollowup=0L;
    public boolean respond(JSONObject jsonObject){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="respond";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            logger.info(fName+".isCallbacked="+isCallbacked);
            if(isCallbacked){
                return  false;
                /*String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken();
                logger.info(fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                }*/
            }else{
                String url= "https://discord.com/api/v8/interactions/"+getInteractionId()+"/"+getToken()+"/callback";
                logger.info(fName+".url="+url);
                logger.info(fName+".data="+jsonObject.toString());
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                    logger.info(fName+".got");isCallbacked=true;return true;
                }
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean post(JSONObject jsonObject){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="respond";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            logger.info(fName+".isCallbacked="+isCallbacked);

            String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken();
                logger.info(fName+".url="+url);
                logger.info(fName+".data="+jsonObject.toString());
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                    logger.info(fName+".got");isCallbacked=true;return true;
                }


        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean edit(JSONObject jsonObject){
        String fName="[edit]";
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
    public boolean edit(JSONObject jsonObject,long id){
        String fName="[edit]";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString()+" id="+id);
            String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken()+"/messages/"+id;
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
        String fName="[delete]";
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
    public boolean delete(long id){
        String fName="[delete]";
        try {
            logger.info(fName+"id="+id);
            String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken()+"/messages/"+id;
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
    // https://discord.com/developers/docs/interactions/slash-commands#interaction-response-interactioncallbacktype
    public boolean respondMessage(String content, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            return  respondMessage( content, embeds, null,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(lcMyEmbedBuilder embed, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            return  respondMessage( null, embeds, null,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(String content, JSONObject allowed_mentions, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            return  respondMessage( content, embeds, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(lcMyEmbedBuilder embed, JSONObject allowed_mentions, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            return  respondMessage( null, embeds, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(String content, lcMyEmbedBuilder embed, JSONObject allowed_mentions, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            return  respondMessage( content, embeds, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(String content, List<lcMyEmbedBuilder> embeds, JSONObject allowed_mentions, boolean tts, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            int iflags=0;
            if(ephemera)iflags=64;
            JSONArray arrayembeds=new JSONArray();
            if(embeds!=null&&!embeds.isEmpty()){
                for(lcMyEmbedBuilder embed:embeds){
                    arrayembeds.put(embed.getJSON());
                }
            }
            return  respondMessage(content, arrayembeds, allowed_mentions,tts,iflags);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(String content, JSONArray embeds, JSONObject allowed_mentions, boolean tts, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            int iflags=0;
            if(ephemera)iflags=64;
            return  respondMessage(content, embeds, allowed_mentions,tts,iflags);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(String content, JSONArray embeds, JSONObject allowed_mentions, boolean tts, int flags){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage.type=4]";
        try {
            return  respondMessage(4,content, embeds,null, allowed_mentions, tts, flags);

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean respondMessage(int type, String content,boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>(); List<lcMessageBuildComponent> components=new ArrayList<>();
            return  respondMessage(type, content, embeds,components, null,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,lcMyEmbedBuilder embed,lcMessageBuildComponent component,boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            List<lcMessageBuildComponent> components=new ArrayList<>();
            if(component!=null) {
                components.add(component);
            }
            return  respondMessage(type, null, embeds,components, null,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,String content, JSONObject allowed_mentions,boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>(); List<lcMessageBuildComponent> components=new ArrayList<>();
            return  respondMessage(type, content, embeds,components, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,lcMyEmbedBuilder embed,lcMessageBuildComponent component, JSONObject allowed_mentions,boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            List<lcMessageBuildComponent> components=new ArrayList<>();
            if(component!=null) {
                components.add(component);
            }
            return  respondMessage(type, null, embeds,components, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,String content, lcMyEmbedBuilder embed,lcMessageBuildComponent component, JSONObject allowed_mentions,boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            List<lcMyEmbedBuilder> embeds=new ArrayList<>();
            if(embed!=null){
                embeds.add(embed);
            }
            List<lcMessageBuildComponent> components=new ArrayList<>();
            if(component!=null) {
                components.add(component);
            }
            return  respondMessage(type, content, embeds,components, allowed_mentions,false, ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type, String content, List<lcMyEmbedBuilder> embeds, List<lcMessageBuildComponent>components, JSONObject allowed_mentions, boolean tts, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            int iflags=0;
            if(ephemera)iflags=64;
            JSONArray arrayembeds=new JSONArray();JSONArray arraycomponents=new JSONArray();
            if(embeds!=null&&!embeds.isEmpty()){
                for(lcMyEmbedBuilder embed:embeds){
                    arrayembeds.put(embed.getJSON());
                }
            }
            if(components!=null&&!components.isEmpty()){
                for(lcMessageBuildComponent component:components){
                    arraycomponents.put(component.getJson());
                }
            }
            return  respondMessage(type,content, arrayembeds,arraycomponents, allowed_mentions,tts,iflags);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,String content, JSONArray embeds,JSONArray components, JSONObject allowed_mentions,boolean tts, boolean ephemera){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            int iflags=0;
            if(ephemera)iflags=64;
            return  respondMessage(type,content, embeds,components, allowed_mentions,tts,iflags);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean respondMessage(int type,String content, JSONArray embeds,JSONArray components, JSONObject allowed_mentions, boolean tts, int flags){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="[respondMessage]";
        try {
            logger.info(fName+".isCallbacked="+isCallbacked);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",type);
            jsonObject.put("data",new JSONObject());
            if(isCallbacked){
                logger.info(fName+".isCallbacked return");
                return  false;
            }else{
                if(tts){
                    logger.info(fName+".tts="+tts);
                    jsonObject.getJSONObject("data").put("tts",tts);
                }
                if(flags!=0){
                    logger.info(fName+".flags="+flags);
                    jsonObject.getJSONObject("data").put("flags",flags);
                }
                if(content!=null&&!content.isBlank()){
                    logger.info(fName+".content="+content);
                    jsonObject.getJSONObject("data").put("content",content);
                };
                if(embeds!=null&&!embeds.isEmpty()){
                    logger.info(fName+".embeds="+embeds.toString());
                    jsonObject.getJSONObject("data").put("embeds",embeds);
                    try {
                        int size=jsonObject.getJSONObject("data").getJSONArray("embeds").length();
                        for(int i=0;i<size;i++){
                            JSONObject embed=jsonObject.getJSONObject("data").getJSONArray("embeds").getJSONObject(i);
                            if(embed.has("color")){
                                if(embed.optInt("color")<0){
                                    embed.put("color",0);
                                    jsonObject.getJSONObject("data").getJSONArray("embeds").put(i,embed);
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

                    }
                };
                if(allowed_mentions!=null&&!allowed_mentions.isEmpty()){
                    logger.info(fName+".allowed_mentions="+allowed_mentions.toString());
                    jsonObject.getJSONObject("data").put("allowed_mentions",allowed_mentions);
                };
                if(components!=null&&!components.isEmpty()){
                    logger.info(fName+".components="+components.toString());
                    jsonObject.getJSONObject("data").put("components",components);
                };

                logger.info(fName+".json"+jsonObject.toString());
                String url= "https://discord.com/api/v8/interactions/"+getInteractionId()+"/"+getToken()+"/callback";
                logger.info(fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                    logger.info(fName+".got");isCallbacked=true;return true;
                }
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean respondPing(){
        //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
        String fName="respondPing";
        try {
            logger.info(fName+".isCallbacked="+isCallbacked);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",1);
            if(isCallbacked){
                logger.info(fName+".isCallbacked return");
                return  false;
                /*String url= "https://discord.com/api/v8/webhooks/"+jda.getSelfUser().getId()+"/"+getToken();
                logger.info(fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                }*/
            }else{
                logger.info(fName+".json"+jsonObject.toString());
                String url= "https://discord.com/api/v8/interactions/"+getInteractionId()+"/"+getToken()+"/callback";
                logger.info(fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
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
                    logger.info(fName+".got");isCallbacked=true;return true;
                }
            }

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


}
