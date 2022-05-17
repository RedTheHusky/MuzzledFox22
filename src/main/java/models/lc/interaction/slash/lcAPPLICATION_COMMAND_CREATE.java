package models.lc.interaction.slash;

import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import models.lcGlobalHelper;
import models.ls.lsMessageHelper;
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
public class lcAPPLICATION_COMMAND_CREATE {
    Logger logger = Logger.getLogger(getClass());
    RawGatewayEvent gEvent;JDA jda;
    lcGlobalHelper global;
    public lcAPPLICATION_COMMAND_CREATE(lcGlobalHelper global, RawGatewayEvent event){
        String fName="build";
        try {
            logger.info(fName+".creating");
            this.global=global;
            gEvent=event;jda=event.getJDA();
            if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                logger.warn(fName+".invaliod type");
                return;
            }
            String strPayload=event.getPayload().toString();
            jsonPayload=new JSONObject(strPayload);
            try {
                jsonApplication=jsonPayload.getJSONObject(keyApplication);
                jsonAuthor=jsonPayload.getJSONObject(keyAuthor);
                jsonMember=jsonPayload.getJSONObject(keyMember);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            getCommandValues();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    JSONObject jsonPayload,jsonMember,jsonAuthor,jsonApplication;
    String keyId="id",keyMember="member",keyAuthor="author",keyApplication="application";
    String keyGuild_Id="guild_id",keyChannel_Id="channel_id",keyContent="content",keyMention_Everyone="mention_everyone";
    public long getId(){
        String fName="getId";
        try {
            return jsonPayload.getLong(keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getAuthorID(){
        String fName="getAuthorID";
        try {
            return Long.parseLong(jsonAuthor.getString(keyId));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public User getAuthor(){
        String fName="getAuthorID";
        try {
            return jda.getUserById(jsonAuthor.getString(keyId));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember(){
        String fName="getMember";
        try {
            Member jdMember=jda.getGuildById(getGuildID()).getMemberById(getAuthorID());
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
    public long getBotID(){
        String fName="getBotID";
        try {
            return Long.parseLong(jsonApplication.getString(keyId));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public Member getBot(){
        String fName="getMember";
        try {
            Member jdMember=jda.getGuildById(getGuildID()).getMemberById(getBotID());
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
    public String getContent(){
        String fName="getContent";
        try {
            return jsonPayload.getString(keyContent);
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
    String command_name="",command_id="",options="",variable="";
    public String getCommandName(){
        String fName="getCommandName";
        try {
            return command_name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getCommandId(){
        String fName="getCommandId";
        try {
            return Long.parseLong(command_id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getOptions(){
        String fName="getOptions";
        try {
            return options;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getVariable(){
        String fName="getVariable";
        try {
            return variable;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private void getCommandValues(){
        String fName="getCommandValues";
        try {
            String content=getContent();
            logger.info(fName + ".content=" + content);
            if(!content.startsWith("</")){
                logger.warn(fName + ".invalid startwith");
                return;
            }
            String items[]=content.split(">");
            String command=items[0];
            command=command.replaceAll("</","").trim();
            String items_command[]=command.split(":");
            command_name=items_command[0];command_id=items_command[1];
            try {
                String items_2[]=items[1].split(":");
                options=items_2[0];
                for(int i=1;i<items_2.length;i++){
                    if(!variable.isBlank())variable+=":";
                    variable+=items_2[i];
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return;
        }
    }
    public lcSLASHCOMMAND getSlashCommand(){
        String fName="getSlashCommand";
        try {
            return new lcSLASHCOMMAND(global,getCommandId(),getGuild());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean delete(){
        String fName="delete";
        try {
            return lsMessageHelper.lsMessageDeleteStatus(getChannel(),getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
