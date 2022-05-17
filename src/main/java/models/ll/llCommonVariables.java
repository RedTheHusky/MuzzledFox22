package models.ll;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Arrays;

import static models.llGlobalHelper.llBotToken;

public interface llCommonVariables {

    enum ApplicationCommandTypes{
        Unknown("Unknown",	-1),
        CHAT_INPUT("CHAT_INPUT",	1),
        USER("USER",	2),
        MESSAGE("MESSAGE"	,3);
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-types";
        private String name="",note="";
        private int value=0;
        private ApplicationCommandTypes(String name,int value ) {
            this.value=value;
            this.name = name;
        }
        private ApplicationCommandTypes(String name,int value, String note) {
            this.value=value;
            this.name = name;
            this.note=note;
        }
        public static ApplicationCommandTypes valueByCode(int value) {
            ApplicationCommandTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandTypes status = var1[var3];
                if (status.value == value) {
                    return status;
                }
            }
            return Unknown;
        }
        public static ApplicationCommandTypes valueByName(String name) {
            ApplicationCommandTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandTypes status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return Unknown;
        }
        public int getValue() {
            return this.value;
        }
        public String getName() {
            return this.name;
        }
        public String getNote() {
            return this.note;
        }
    }
    enum ApplicationCommandOptionTypes {
        Unknown("Unknown",	-1),
        SUB_COMMAND("SUB_COMMAND",	1),
        SUB_COMMAND_GROUP("SUB_COMMAND_GROUP",	2),
        STRING("STRING"	,3),
        INTEGER("INTEGER"	,4	,"Any integer between -2^53 and 2^53"),
        BOOLEAN("BOOLEAN",	5),
        USER("USER",	6),
        CHANNEL("CHANNEL"	,7,	"Includes all channel types + categories"),
        ROLE("ROLE",	8),
        MENTIONABLE("MENTIONABLE",	9	,"Includes users and roles"),
        NUMBER("NUMBER",	10	,"Any double between -2^53 and 2^53");
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type";
        private String name="",note="";
        private int value=0;
        private ApplicationCommandOptionTypes(String name, int value ) {
            this.value=value;
            this.name = name;
        }
        private ApplicationCommandOptionTypes(String name, int value, String note) {
            this.value=value;
            this.name = name;
            this.note=note;
        }
        public static ApplicationCommandOptionTypes valueByCode(int value) {
            ApplicationCommandOptionTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandOptionTypes status = var1[var3];
                if (status.value == value) {
                    return status;
                }
            }
            return Unknown;
        }
        public static ApplicationCommandOptionTypes valueByName(String name) {
            ApplicationCommandOptionTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandOptionTypes status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return Unknown;
        }
        public int getValue() {
            return this.value;
        }
        public String getName() {
            return this.name;
        }
        public String getNote() {
            return this.note;
        }
    }
    enum ApplicationCommandPermissionType {
        Unknown("Unknown",	-1),
        ROLE("ROLE",	1),
        USER("USER",	2);
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-permissions-object-application-command-permission-type";
        private String name="";
        private int value=0;
        private ApplicationCommandPermissionType(String name, int value ) {
            this.value=value;
            this.name = name;
        }
        public static ApplicationCommandPermissionType valueByCode(int value) {
            ApplicationCommandPermissionType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandPermissionType status = var1[var3];
                if (status.value == value) {
                    return status;
                }
            }
            return Unknown;
        }
        public static ApplicationCommandPermissionType valueByName(String name) {
            ApplicationCommandPermissionType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApplicationCommandPermissionType status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return Unknown;
        }
        public int getValue() {
            return this.value;
        }
        public String getName() {
            return this.name;
        }
    }
    enum ButtonStyles{
        Unknown("unknown",	-1),
        Primary("primary",	1,new Color(114, 137, 218),1),
        Secondary("secondary",	2,new Color(79,84,92),1),
        Success("success"	,3,new Color(67,181,129),1),
        Danger("danger"	,4	,new Color(240,71,71),1),
        Link("link",	5,new Color(79,84,92),2);
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/message-components#button-object-button-styles";
        private String name="";
        private int value=0;
        private Color color=Color.BLACK;
        private  boolean requireID=false,requireUrl=false;
        private ButtonStyles(String name,int value ) {
            this.value=value;
            if(name!=null)this.name = name;
        }
        private ButtonStyles(String name,int value, Color color, int require) {
            this.value=value;
            if(name!=null)this.name = name;
            if(color!=null)this.color=color;
            switch (require){
                case 1: requireID=true;break;
                case 2: requireUrl=true;break;
            }
        }
        public static ButtonStyles valueByCode(int value) {
            ButtonStyles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ButtonStyles status = var1[var3];
                if (status.value == value) {
                    return status;
                }
            }
            return Unknown;
        }
        public static ButtonStyles valueByName(String name) {
            ButtonStyles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ButtonStyles status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return Unknown;
        }
        public int getValue() {
            return this.value;
        }
        public String getName() {
            return this.name;
        }
        public Color getColor() {
            return this.color;
        }
        public boolean isRequireId() {
            return this.requireID;
        }
        public boolean isRequireUrl() {
            return this.requireUrl;
        }
    }
    public interface MessageEmbedLimitations{
        String objectReferenceUrl="https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/EmbedBuilder.htm";
        int TITLE_MAX_LENGTH = MessageEmbed.TITLE_MAX_LENGTH,
            AUTHOR_MAX_LENGTH = MessageEmbed.AUTHOR_MAX_LENGTH,
            VALUE_MAX_LENGTH = MessageEmbed.VALUE_MAX_LENGTH,
            DESCRIPTION_MAX_LENGTH = MessageEmbed.DESCRIPTION_MAX_LENGTH,
            TEXT_MAX_LENGTH = MessageEmbed.TEXT_MAX_LENGTH,
            URL_MAX_LENGTH = MessageEmbed.URL_MAX_LENGTH,
            EMBED_MAX_LENGTH_BOT = MessageEmbed.EMBED_MAX_LENGTH_BOT,
            EMBED_MAX_LENGTH_CLIENT = MessageEmbed.EMBED_MAX_LENGTH_CLIENT;
    }
    public  class ImageFormats{
        public enum Types {
            Unknown("",	0),
            JPEG("jpg",	1),
            PNG("png",	2),
            WebP("webp"	,3),
            GIF("gif"	,4	),
            Lottie("json",5	);
            String objectReferenceUrl="https://discord.com/developers/docs/reference#image-formatting-image-formats";
            private String extension ="";
            private int value=0;
            private Types(String name, int value ) {
                this.value=value;
                if(name!=null)this.extension = name;
            }

            public static Types valueByCode(int value) {
                Types[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Types status = var1[var3];
                    if (status.value == value) {
                        return status;
                    }
                }
                return Unknown;
            }
            public static Types valueByExtension(String name) {
                Types[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Types status = var1[var3];
                    if (status.extension.equals(name)) {
                        return status;
                    }
                }
                return Unknown;
            }
            public int getValue() {
                return this.value;
            }
            public String getExtension() {
                return this.extension;
            }

        }

        public enum CDNEndpoints{
            Unknown(0,""	),
            CustomEmoji(1,"emojis/!EMOJI.png",true,true,true,true),
            GuildIcon(1,"icons/!GUILD/!ICON.png","icons/!GUILD/a_!ICON.png",true,true,true,true),
            GuildSplash(1,"splashes/!GUILD/!SPLASH.png",true,true,true,true),
            GuildDiscoverySplash(1,"discovery-splashes/!GUILD/!SPLASH.png",true,true,true,true),
            GuildBanner(1,"banners/!GUILD/!BANNER.png ",true,true,true,true),
            UserBanner(1,"banners/!USER/!BANNER.png","banners/!USER/a_!BANNER.png",true,true,true,true),
            DefaultUserAvatar(1,"embed/avatars/user_discriminator.png ",true,true,true,true),
            UserAvatar(1,"avatars/!USER/user_avatar.png",true,true,true,true),
            GuildMemberAvatar(1,"guilds/!GUILD/users/!USER/avatars/!AVATAR.png",true,true,true,true),
            ApplicationIcon(1,"app-icons/!APPLICATION/icon.png",true,true,true,true),
            ApplicationCover(1,"app-icons/!APPLICATION/cover_image.png",true,true,true,true),
            ApplicationAsset(1,"app-assets/!APPLICATION/asset_id.png",true,true,true,true),
            AchievementIcon(1,"app-assets/!APPLICATION/achievements/achievement_id/icons/icon_hash.png",true,true,true,true),
            StickerPackBanner(1,"app-assets/710982414301790216/store/!BANNER.png",true,true,true,true),
            TeamIcon(1,"team-icons/!TEAM/team_icon.png",true,true,true,true),
            Sticker(1,"stickers/!STICKER.png",true,true,true,true),
            RoleIcon(1,"role-icons/!ROLE/role_icon.png",true,true,true,true);
            String objectReferenceUrl="https://discord.com/developers/docs/reference#image-formatting-cdn-endpoints";
            private String url ="", urlAnimation="";
            private int code=0;
            private  boolean png=false,jpeg=false,webp=false,gif=false,lottie=false;
            private CDNEndpoints() {

            }
            private CDNEndpoints(int code,String url) {
                this.code=code;
                this.url=url;
            }
            private CDNEndpoints(int code,String url,boolean png) {
                this.code=code;
                this.url=url;
                this.png=png;
            }
            private CDNEndpoints(int code,String url,boolean png,boolean jpeg) {
                this.code=code;
                this.url=url;
                this.png=png;
                this.jpeg=jpeg;
            }
            private CDNEndpoints(int code,String url,boolean png,boolean jpeg, boolean webp) {
                this.code=code;
                this.url=url;
                this.png=png;
                this.jpeg=jpeg;
                this.webp=webp;
            }
            private CDNEndpoints(int code,String url,boolean png,boolean jpeg, boolean webp,boolean gif) {
                this.code=code;
                this.url=url;
                this.png=png;
                this.jpeg=jpeg;
                this.webp=webp;
                this.gif=gif;
            }
            private CDNEndpoints(int code,String url,String urlAnimation,boolean png,boolean jpeg, boolean webp,boolean gif) {
                this.code=code;
                this.url=url;this.urlAnimation=urlAnimation;
                this.png=png;
                this.jpeg=jpeg;
                this.webp=webp;
                this.gif=gif;
            }
            private CDNEndpoints(int code,String url,boolean png,boolean jpeg, boolean webp,boolean gif, boolean lottie) {
                this.code=code;
                this.url=url;
                this.png=png;
                this.jpeg=jpeg;
                this.webp=webp;
                this.gif=gif;
                this.lottie=lottie;
            }
            public static CDNEndpoints valueByCode(int value) {
                CDNEndpoints[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    CDNEndpoints status = var1[var3];
                    if (status.code == value) {
                        return status;
                    }
                }
                return Unknown;
            }
            public int getValue() {
                return this.code;
            }
            public String getUrl() {
                return this.url;
            }
            public String getUrlAnimation() {
                return this.urlAnimation;
            }
            public boolean isSupportedPng() {
                return this.png;
            }
            public boolean isSupportedJpeg() {
                return this.jpeg;
            }
            public boolean isSupportedWebP() {
                return this.webp;
            }
            public boolean isSupportedGif() {
                return this.gif;
            }
            public boolean isSupportedLottie() {
                return this.lottie;
            }

        }
    }
    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;
    long milliseconds_second=1000;
    enum EmbedTypes{
        Unknown("Unknown"),
        Rich("rich"),
        Image("image"),
        Video("video"),
        Gifv("gifv"),
        Article("article"),
        Link("link");
        String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-types";
        private String name="",note="";
        private EmbedTypes(String name) {
            this.name = name;
        }
        private EmbedTypes(String name, String note) {
            this.name = name;
            this.note=note;
        }
        public static EmbedTypes valueByName(String name) {
            EmbedTypes[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                EmbedTypes status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return Unknown;
        }
        public String getName() {
            return this.name;
        }
        public String getNote() {
            return this.note;
        }
    }
}
