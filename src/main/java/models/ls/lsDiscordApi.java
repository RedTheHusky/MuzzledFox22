package models.ls;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Arrays;

import static models.llGlobalHelper.llBotToken;

public interface lsDiscordApi {


    String liGetGuildUrl="https://discord.com/api/v8/guilds/!GUILD",
            liGetGuildPreviewUrl="https://discord.com/api/v8/guilds/!GUILD/preview",
            liGetGuildChannelsUrl="https://discord.com/api/v8/guilds/!GUILD/channels",
            liGetGuildRoles="https://discordapp.com/api/guilds/!GUILD/roles",
    liGetGuildVoiceRegions="https://discordapp.com/api/guilds/!GUILD/regions",
            liGetGuildInvitesURL="https://discordapp.com/api/guilds/!GUILD/invites",
            liGetGuildIntegrationsURL="https://discordapp.com/api/guilds/!GUILD/integrations",
            liGetGuildWidgetSettingsURL="https://discordapp.com/api/guilds/!GUILD/widget",
            liGetGuildWidgetURL="https://discordapp.com/api/guilds/!GUILD/widget.json",
            liGetGuildVanityURL="https://discordapp.com/api/guilds/!GUILD/vanity-url",
            iGetGuildWelcomeScreenURL="https://discordapp.com/api/guilds/!GUILD/welcome-screen";
    String liGetGuildBansUrl="https://discord.com/api/v8/guilds/!GUILD/bans",
            liGetGuildBanUrl="https://discord.com/api/v8/guilds/!GUILD/bans/!USER";
    public  interface ImageSize{
        String str512="512",size512="size=512";
        int int512=512;
    }
    public  interface ImageType{
        String png="png";
        String gif="gif";
    }
    static boolean isIdAnimated(String id){
        String fName="[isIdAnimated]";
        Logger logger = Logger.getLogger(lsDiscordApi.class);
        try{
            if(id.startsWith("a_")){
                return true;
            }
            return false;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String liGetGuildMembersUrl="https://discordapp.com/api/guilds/!GUILD/members";
    String liGetGuildMemberUrl="https://discordapp.com/api/guilds/!GUILD/members/!MEMBER",
            liGetGuildMemberAvatarUrl ="https://cdn.discordapp.com/guilds/!GUILD/users/!USER/avatars/!AVATAR.!EXT";

    String liGetUserUrl="https://discordapp.com/api/users/!USER",
            liUserAvatarUrl="https://cdn.discordapp.com/avatars/!USER/!AVATAR.!EXT",
            liUserBannerUrl="https://cdn.discordapp.com/banners/!USER/!BANNER.!EXT";

    String liGetGuildEmojisUrl ="https://discordapp.com/api/guilds/!GUILD/emojis",
            liGetGuildEmojiUrl="https://discordapp.com/api/guilds/!GUILD/emojis/!EMOJI";

    String liGetChannelUrl="https://discordapp.com/api/channels/!CHANNEL",
            liGetChannelMessagesUrl="https://discordapp.com/api/channels/!CHANNEL/messages",
            liGetChannelMessageUrl="https://discordapp.com/api/channels/!CHANNEL/messages/!MESSAGE",
            liGetChannelMessageReactionUrl="https://discordapp.com/api/channels/!CHANNEL/messages/!MESSAGE/reactions/!EMOJI";

    String liGetStickerPacks="https://discord.com/api/v9/sticker-packs",
            liStickerAssetUrl_old ="https://cdn.discordapp.com/stickers/!STICKER/!ASSET.!EXT",
            liStickerMediaUrlPreview_old ="https://media.discordapp.net/stickers/!STICKER/!ASSET.png?size=!SIZE&passthrough=!PASSTHROUGH",
            liStickerMediaUrl_old ="https://media.discordapp.net/stickers/!STICKER/!ASSET.png",
            liStickerLottieUrl="https://discord.com/stickers/!STICKER.json",
            liStickerMediaUrl="https://media.discordapp.net/stickers/!STICKER.png",
            liStickerBannerUrl="https://cdn.discordapp.com/app-assets/710982414301790216/store/!PACKBANNERASSETID.png";

    String liGetGuildStickers="https://discord.com/api/v9/guilds/!GUILD/stickers",
            liGetGuildSticker="https://discord.com/api/v9/guilds/!GUILD/stickers/!STICKER";
    String liGetSticker="https://discord.com/api/v9/stickers/!STICKER"; //support bot nitro and guild sticker, not package

    String liChannelWebhooks="https://discord.com/api/v9/channels/!CHANNEL/webhooks";
    String liGetGuildWebhooks="https://discord.com/api/v9/guilds/!GUILDS/webhooks";
    String liWebhook="https://discord.com/api/v9/webhooks/!WEBHOOK";
    String liWebhookwToken="https://discord.com/api/v9/webhooks/!WEBHOOK/!TOKEN";
    String liWebhookMessage="https://discord.com/api/v9/webhooks/!WEBHOOK/!TOKEN/messages/!MESSAGE";

    interface ApplicationCommand{
        interface Global{
            String liGetCommands_Or_RegisteringCommand="https://discord.com/api/v8/applications/!APPLICATION/commands";
            static String lsGetCommands_Or_RegisteringCommand(String application_id){
                String fName="[lsGetCommands_Or_RegisteringCommand]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                   String str=liGetCommands_Or_RegisteringCommand.replaceAll("!APPLICATION",application_id);
                   return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            String liGetOrEditCommand="https://discord.com/api/v8/applications/!APPLICATION/commands/!COMMAND";
            static String lsGetOrEditCommand(String application_id,String command_id){
                String fName="[lsGetOrEditCommand]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                    String str=liGetOrEditCommand.replaceAll("!APPLICATION",application_id).replaceAll("!COMMAND",command_id);
                    return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
        }
        interface Guild{
            String liGetCommands_Or_RegisteringCommand="https://discord.com/api/v8/applications/!APPLICATION/guilds/!GUILD/commands";
            static String lsGetCommands_Or_RegisteringCommand(String application_id,String guild_id){
                String fName="[lsGetCommands_Or_RegisteringCommand]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                    String str=liGetCommands_Or_RegisteringCommand.replaceAll("!APPLICATION",application_id).replaceAll("!GUILD",guild_id);
                    return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            String liGetOrEditCommand="https://discord.com/api/v8/applications/!APPLICATION/guilds/!GUILD/commands/!COMMAND";
            static String lsGetOrEditCommand(String application_id,String guild_id,String command_id){
                String fName="[lsGetOrEditCommand]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                    String str=liGetOrEditCommand.replaceAll("!APPLICATION",application_id).replaceAll("!GUILD",guild_id).replaceAll("!COMMAND",command_id);
                    return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            String liGetCommandsPermission="https://discord.com/api/v8/applications/!APPLICATION/guilds/!GUILD/commands/permissions";
            static String lsGetCommandsPermission(String application_id,String guild_id){
                String fName="[lsGetCommandsPermission]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                    String str=liGetCommandsPermission.replaceAll("!APPLICATION",application_id).replaceAll("!GUILD",guild_id);
                    return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            String liGetOrEditCommandPermission="https://discord.com/api/v8/applications/!APPLICATION/guilds/!GUILD/commands/!COMMAND/permissions";
            static String lsGetOrEditCommandPermission(String application_id,String guild_id,String command_id){
                String fName="[lsGetOrEditCommandPermission]";
                Logger logger = Logger.getLogger(lsDiscordApi.class);
                try{
                    String str=liGetOrEditCommandPermission.replaceAll("!APPLICATION",application_id).replaceAll("!GUILD",guild_id).replaceAll("!COMMAND",command_id);
                    return  str;
                }
                catch(Exception e){
                    logger.error(".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
        }
    }




    static class ImageSources{
        String objectReferenceUrl="https://discord.com/developers/docs/reference#image-formatting-image-formats";
        String BaseUrl="Image Base Url";

    }
    static JSONObject lsGetGuildJson(String id){
        String fName="[lsGetGuildJson]";
        Logger logger = Logger.getLogger(lsDiscordApi.class);
        try{
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetGuildUrl.replaceAll("!GUILD",id);
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            logger.info(fName+"response.body"+response.getBody().getObject().toString());
            return response.getBody().getObject();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public enum StickerTypes {
        Invalid(0,"Invalid"),
        STANDARD(1,"STANDARD"),
        GUILD(2,"GUILD");

        private String name;
        private int code;
        private StickerTypes(int code, String name) {
            this.code = code;
            this.name = name;
        }
        public static StickerTypes valueByCode(int code) {
            StickerTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                StickerTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static StickerTypes valueByName(String name) {
            StickerTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                StickerTypes status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            StickerTypes[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                StickerTypes s = var0[var2];
            }

        }
        public static String getName(restraints.models.ACCESSLEVELS gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(restraints.models.ACCESSLEVELS.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
}
