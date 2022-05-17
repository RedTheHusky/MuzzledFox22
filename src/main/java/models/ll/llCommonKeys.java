package models.ll;

import restraints.models.ACCESSLEVELS;

public interface llCommonKeys {
    String keyIcon ="icon",keyIconUrl ="iconUrl", keyIconId ="iconId", keySplash ="splash",keySplashUrl ="splashUrl", keySplashId ="splashId", keyBanner ="banner", keyBannerUrl ="bannerUrl", keyBannerId ="bannerUrl",keyAvatar="avatar",keyAvatarUrl="avatarUrl",keyAvatarId="avatarId";

    String keyPermission="permission",keyPermissionRaw="permissionRaw",keyPermissions="permissions" ,keyPermissionsCount="permissionCount", keyName="name",keyColor="color",keyHoisted="hoisted",
            keyMentionable="mentionable", keyPublicRole="publicRole", keyPosition="position", keyId="id" ;

    String keyType="type",keyParent="parent", keyNsfw="nsfw",keyTopic="topic",keySlowmode="slowmode", keyBitrate ="bitrate", keyUserLimit="userLimit",
            keyChildsCount="childsCount", keyChilds="childs", keyMembersPermissions="membersPermissions",keyRolesPermission="rolesPermissions",
            keyAllow="allow", keyDeny="deny", keyInherit="inherit", keyAllows="allows", keyDenies="denies", keyInherits="inherits";

    String keyEffectiveName="effectiveName", keyNickname="nickname",keyTimeCreated="timeCreated",keyTimeJoined="timeJoined",keyTimeBoosted="timeBoosted", keyIsBot="isBot",
            keyDiscriminator="discriminator", keyRoles="roles", keyIsOwner="isOwner", keyPermissionOverrides="permissionOverrides", keyTimeHumanReadable="human", keyTimeEpoch="epoch";
    String keyVoiceState="voiceState",keyIsStream="isStream", keyIsDeafened="isDeafened",keyIsMuted="isMuted",keyIsSuppressed="isSuppressed", keyIsSelfMuted="isSelfMuted", keyIsSelfDeafened="isSelfDeafened",
            keyIsGuildDeafened="isGuildDeafened",keyIsGuildMuted="isGuildMuted", keyVoiceChannel="voiceChannel", keyReason="reason";

    String  keyException="exception",keyBasicValue="basicValue", keyNewValue="newValue",keyOldValue="oldValue",  keyChannelType="channelType", keyChannelNSFW="channelNsfw";
    String keyTarget ="target", keyAuthor ="author", keyChanges ="changes";

    String keyImageUrl="imageUrl", keyImageId="imageId", keyIsAnimated="isAnimated",keyIsAvailable="isAvailable";

    String keyUrl="url", keyUses="uses", keyMaxUses="maxUses",keyIsExpanded="isExpanded",keyIsTemporary="isTemporary",keyMaxAge="maxAge", keyCode="code",keyChannel="channel", keyGroup="group", keyUsers="users";
    String keyBoost="boost", keyTier="tier", keyCount="count";
    String keyOwner="owner", keyTextChannels="textChannels", keyVoiceChannels="voiceChannels", keyCategories="categories", keyEmotes="emotes", keyWebhooks="webhooks",keyInvites="invites",keyBanList="banList", keyBan="ban", keyMembers="members",keySize="size";

    String keyCustomServer="customserver",keyInHouse="inhouse",keyServer="server",keyMeta="meta",keyBot="bot",keyInfo="info",
           keyDescription="description", keyAudit="audit", keyIsNSFWServer="isNSFWServer", keyInviteLink="invitelink",
            keyRolesCount="rolesCount",   keyChannelsCount="channelsCount",
            keyTextChannelsCount="textchannelsCount", keyVoiceChannelsCount="voicechannelsCount",keyCategoryChannelsCount="categorychannelsCount",
            keyMembersCount="membersCount", keyBoostCount="boostCount", keyBoostTier="boostTier", keyVanityUrl="vanityUrl", keyBDSMRestrictions="bdsmrestriction";

    String keyGuilds="guilds",keyGuild="guild",keyGuildId="guildID",keyGuildName="guildName";
    String keyMember="member",keyMemberId="memberID",keyMemberName="memberName";
    String keyUser="user",keyUserId="userID",keyUserName="userName";
    String keyProfile="profile",keyProfiles="profiles",keyChannels="channels",keyLogs="logs",keyBans="bans";
    String keyTextChannel="textChannel",keyPrivateChannel="privateChannel",keyPrivateChannels="privateChannels";

    String keyMember_id="member_id",keyUser_id="user_id";
    String keyEmoji = "emoji";
    String keyMessage_id ="message_id" ;
    String keyChannel_id ="channel_id" ;

    String keyMessage="message",keyMessages="messages";
    String keyMessageComponents="messageComponents";
    String keyData = "data",keyOptions="options",keyResolved="resolved";
    String keyEtc = "etc";
    String keyNsfwLevel = "nsfw_level";

    public interface lsMessageJsonKeys    {
        String keyMessage="message";
        String keyMentionEveryone="mention_everyone",
                keyPinned="pinned",
                keyComponents="components",
                keyAttachments="attachments",
                keyAuthor="author",
                keyFlags="flags",
                keyType="type",
                keyMentionRoles="mention_roles",
                keyEditedTimestamp="edited_timestamp",
                keyContent="content",
                keyTts="tts",
                keyMentions="mentions",
                keyId="id",
                keyEmbeds="embeds",
                keyChannelId="channel_id",
                keyTimestamp="timestamp";
        String keyGuildId=keyGuild_Id;
    }
    String keyGuild_Id="guild_id";
    public interface ChannelStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#channel-object";
        String keyId="id",
                keyType="type",
                keyGuildId="guild_id",


                keyPosition="position",

                keypermissionOverwrites="permission_overwrites",
                keyName="name",
                keyTopic="topic",
                keyNsfw="nsfw",
                keyLastMessageId="last_message_id",
                keyBitrate ="bitrate",
                keyUserLimit ="user_limit",
                keyrate_limit_per_user="rate_limit_per_user",
                keyrecipients="recipients",
                keyicon="icon",
                keyOwnerId="owner_id",
                keyapplication_id="application_id",
                keyParentId="parent_id",
                keylast_pin_timestamp="last_pin_timestamp",
                keyrtc_region="rtc_region",
                keyvideo_quality_mode="video_quality_mode",

                keyMessageCount="message_count",
                keyMemberCount="member_count",
                keyThreadMetadata="thread_metadata",
                keymember="member",
                keyDefaultAutoArchiveDuration="default_auto_archive_duration",
                keypermissions="permissions";
            public interface ThreadMetadata {
                String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#thread-metadata-object";
                String keyArchived="archived",
                    keyAutoArchiveDuration="auto_archive_duration",
                    keyArchiveTimestamp="archive_timestamp",
                    keyLocked="locked";
            }
            interface Misc{
                String keyIsNews="is_news",keyNews="news",
                        keySlowmode="slowmode",
                        keyPositionRaw="position_raw",
                        keyParent="parent";
            }
        public interface ThreadMember {
            String objectReferenceUrl="hhttps://discord.com/developers/docs/resources/channel#thread-member-object";
            String keyId="id",
                    keyUserId="user_id",
                    keyJoinTimestamp="join_timestamp",
                    keyFlags="flags";
        }
    }
    public interface MessageStructureGet {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#message-object";
        String keyId="id",
                keyChannelId="channel_id",
                keyGuildId="guild_id",
                keyAuthor="author",
                keyMember="member",
                keyContent="content",
                keyTimestamp="timestamp",
                keyEditedTimestamp="edited_timestamp",
                keyTts="tts",
                keyMentionEveryone="mention_everyone",
                keyMentions="mentions",
                keyMentionRoles="mention_roles",
                keyMentionChannels="mention_channels",
                keyAttachments="attachments",
                keyEmbeds="embeds",
                keyReactions="reactions",
                keyNonce="nonce",
                keyPinned="pinned",
                keyWebhookId="webhook_id",
                keyType="type",
                keyActivity="activity",
                keyApplication="application",
                keyApplicationId="application_id",
                keyMessageReference="message_reference",
                keyFlags="flags",
                keyReferencedMessage="referenced_message",
                keyInteraction="interaction",
                keyThread="thread",
                keyComponents="components",
                keyStickerItems="sticker_items",
                keyStickers="stickers";
    }

    public interface MemberStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/guild#guild-member-object-guild-member-structure";
        String  keyUser="user",
                keyNick="nick",
                keyRoles="roles",
                keyJoinedAt="joined_at",
                keyPremiumSince="premium_since",
                keyDeaf="deaf",
                keyMute="mute",
                keyPending="pending",
                keyPermissions="permissions",
                keyAvatar="avatar";
    }
    public interface UserStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/user#user-object";
        String keyId="id",
                keyUsername="username",
                keyDiscriminator="discriminator",
                keyAvatar="avatar",
                keyBot="bot",
                keySystem="system",
                keyMfaEnabled="mfa_enabled",
                keyLocale="locale",
                keyVerified="verified",
                keyEmail="email",
                keyFlags="flags",
                keyPremiumType="premium_type",
                keyPublicFlags="public_flags",
                keyBanner = "banner",
                keyAccentColor="accent_color",
                keyBio="bio";

    }
    public interface RoleStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/topics/permissions#role-object";
        String keyId="id",
                keyName="name",
                keyColor="color",
                keyHoist="hoist",
                keyPosition="position",
                keyPermissions="permissions",
                keyManaged="managed",
                keyMentionable="mentionable",
                keyTags="tags";
        interface  Misc{
            String keyPublic="public";
        }
        public interface TagsStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/topics/permissions#role-object-role-tags-structure";
            String keyBotId="bot_id",
                    keyIntegrationId="integration_id",
                    keyPremiumSubscriber="premium_subscriber";
        }
    }
    public interface GuildStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/guild#guild-object-guild-structure";
        String keyId="id",
                keyName="name",
                keyIcon="icon",
                keyIconHash="icon_hash",
                keySplash="splash",
                keyDiscoverySplash="discovery_splash",
                keyowner="owner",
                keyOwnerId="owner_id",
                keyPermissions="permissions",
                keyAfkChannelId="afk_channel_id",
                keyAfkTimeout="afk_timeout",
                keyWidgetEnabled="widget_enabled",
                keyWidgetChannelId = "widget_channel_id",
                keyVerificationLevel="verification_level",
                keyDefaultMessageNotifications="default_message_notifications",
                keyExplicitContentFilter="explicit_content_filter",
                keyRoles="roles",
                keyEmojis="emojis",
                keyFeatures="features",
                keyMfaLevel="mfa_level";
        interface Deprecated {
            String
                    keyRegion = "region";
        }
        interface forUser{
            String
                    keyOwner ="owner",
                    keyPermissions="permissions";
        }

    }
    public interface EmojiStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/resources/emoji#emoji-object-emoji-structure";
        String keyId="id",
                keyName="name",
                keyRoles="roles",
                keyUser="user",
                keyRequireColons="require_colons",
                keyManaged="managed",
                keyAnimated="animated",
                keyAvailable="Available";
        interface  Misc{
            String keyImageUrl="image_url";
        }
    }

    public interface lsGetMessageInteractionObjectJsonKeys {
        String keyId="id",
                keyType="type",
                keyName="name",
                keyUser="user";
    }
    public interface lsPostMessageObjectJsonKeys {
        String keyContent="content",
                keyTts="tts",
                keyFile="file",
                keyEmbed="embed",
                keyPayloadJson="payload_json",
                keyAllowedMentions="allowed_mentions",
                keyMessageReference="message_reference",
                keyComponents="components";
    }
    public interface lsComponentMessageInteraction {
        String keyComponentType="component_type",
                keyCustomId="custom_id",
                keyValues="values";
    }
    public interface Item {
        String embed="embed";
    }


    public interface lsGetEmojiObjectJsonKeys {
        String keyName="name",
                keyRoles="roles",
                keyId="id",
                keyRequireColons="require_colons",
                keyManaged="managed",
                keyAnimated="animated",
                keyAvailable="available",
                keyUser="user";
    }
    public interface lsAttachmentObjectJsonKeys {
        //https://discord.com/developers/docs/resources/channel#attachment-object
        String keyId="id",
                keyFilename="filename",
                keyContentType="content_type",
                keySize="size",
                keyUrl="url",
                keyProxyUrl="proxy_url",
                keyHeight="height",
                keyWidth="width";
    }

    public interface SlashCommandReceive {
        //prefered values
        String user="user",level="level",type="type",subdir="subdir";
    }
    public  interface InteractionStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/receiving-and-responding#interaction-object-interaction-structure";
        String Id="id",
                ApplicationId="application_id",
                Type="type",
                Data="data",
                GuildId="guild_id",
                ChannelId="channel_id",
                Member="member",
                User="user",
                Token="token",
                Version="version",
                Message="message";
        public  interface InteractionDataStructure {
            String objectReferenceUrl="https://discord.com/developers/docs/interactions/receiving-and-responding#interaction-object-interaction-data-structure";
            String Id="id",
                    Name="name",
                    Type="type",
                    Resolved="resolved",
                    Options="options",
                    CustomId="custom_id",
                    ComponentType="component_type",
                    Values="valuesr",
                    TargetId="target_id";
            public  interface OptionStructure{
                String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-interaction-data-option-structure";
                String Name="name",
                        Type="type",
                        Value="value",
                        Options="options";
            }
        }
    }
    public  interface StickerPackObject{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#sticker-pack-object";
        String Id="id",
                Stickers="stickers",
                Name="name",
                SkuId="sku_id",
                CoverStickerId="cover_sticker_id",
                Description="description",
                BannerAssetId="banner_asset_id";
    }
    public  interface StickerStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-structure";
        String Id="id",
                PackId="pack_id",
                Name="name",
                Description="description",
                Tags="tags",
                Type="type",
                FormatType="format_type",
                Available="available",
                GuildId="guild_id",
                User="user",
                SortValue="sort_value";
        public  interface StickerTypes {
            String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-types";
            int STANDARD=1,
                    GUILD=2;
        }
        public  interface StickerFormatTypes {
            String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#sticker-object-sticker-format-types";
            int PNG=1,
                    APNG=2,
                    LOTTIE=3;
        }
    }
    public  interface StickerItemObject{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#sticker-item-object";
        String Id="id",
                Name="name",
                FormatType="format_type";
    }
    public  interface NitroStickerPacks{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/sticker#list-nitro-sticker-packs";
        String StickerPacks="sticker_packs";
    }
    public  interface WebhookStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/webhook#webhook-object-webhook-structure";
        String Id="id",
                Type="type",
                GuildId="guild_id",
                ChannelId="channel_id",
                User="user",
                Name="name",
                Avatar="avatar",
                Token="token",
                ApplicationId="application_id",
                SourceGuild="source_guild",
                SourceChannel="source_channel",
                Url="url";
    }
    public  interface WebhookTypes{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/webhook#webhook-object-webhook-types";
        int Incoming=1,
                ChannelFollower=2,
                Application=3;
    }
    public  interface  EmbedStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-structure";
        String  title="title",
                type="type",
                description="description",
                url="url",
                timestamp="timestamp",
                color="color",
                footer="footer",
                image="image",
                thumbnail="thumbnail",
                video="video",
                provider="provider",
                author="author",
                fields="fields";
        String  custom_colorRGB="color_RGB";
        public  interface  EmbedAuthorStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-author-structure";
            String  name="name",
                    url="url",
                    icon_url="icon_url",
                    proxy_icon_url="proxy_icon_url";
        }
        public  interface  EmbedFooterStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-footer-structure";
            String  text="text",
                    icon_url="icon_url",
                    proxy_icon_url="proxy_icon_url";
        }
        public  interface  EmbedFieldStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-field-structure";
            String  name="name",
                    value="value",
                    inline="inline";
        }
        public  interface  EmbedThumbnailStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-thumbnail-structure";
            String  url="url",
                    proxy_url="proxy_url",
                    height="height",
                    width="width";
        }
        public  interface  EmbedVideoStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-video-structure";
            String  url="url",
                    proxy_url="proxy_url",
                    height="height",
                    width="width";
        }
        public  interface  EmbedImageStructure{
            String objectReferenceUrl="https://discord.com/developers/docs/resources/channel#embed-object-embed-image-structure";
            String  url="url",
                    proxy_url="proxy_url",
                    height="height",
                    width="width";
        }
    }
    public  interface  EmbedBuildStructure{
        String  title="title",
                description="description",
                timestamp="timestamp",
                color="color",
                footer="footer",
                image="image",
                thumbnail="thumbnail",
                author="author",
                fields="fields";
        public  interface  Title{
            String  text="text",
                    url="url";
        }
        public  interface  Author{
            String  name="name",
                    url="url",
                    icon_url="icon_url";
        }
        public  interface  Footer{
            String  text="text",
                    icon_url="icon_url";
        }
        public  interface  Field{
            String  name="name",
                    value="value",
                    inline="inline";
        }
    }

    public  interface ComponentStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/message-components#component-object-component-structure";
        String Type="type",
                CustomId="custom_id",
                Disabled="disabled",
                Style="style",
                Label="label",
                Emoji="emoji",
                Url="url",
                Options="options",
                Placeholder="placeholder",
                MinValues="min_values",
                MaxValues="max_values",
                Components="components";
            public  interface ButtonStructure{
                String Type="type",
                        CustomId="custom_id",
                        Disabled="disabled",
                        Style="style",
                        Label="label",
                        Emoji="emoji",
                        Url="url";
            }
        public  interface SelectMenuStructure{
            String Type="type",
                    CustomId="custom_id",
                    Disabled="disabled",
                    Options="options",
                    Placeholder="placeholder",
                    MinValues="min_values",
                    MaxValues="max_values";
        }
    }
    public  interface ApplicationCommandStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-structure";
        String Type="type",
                Name="name",
                Description="description",
                DefaultPermission="default_permission",
                Options="options";
        String id="id",
                application_id="application_id",
                guild_id="guild_id",
                version="version";
    }
    public  interface ApplicationCommandOptionStructure{
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-structure";
        String Type="type",
                Name="name",
                Description="description",
                Required="required",
                Choices="choices",
                Autocomplete="autocomplete",
                Options="options",
                ChannelTypes="channel_types",
                MinValue="min_value",
                MaxValue="max_value";
    }
    public  interface ApplicationCommandOptionChoiceStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-choice-structure";
        String  Name="name",
                Value="value";
    }
    public  interface GuildApplicationCommandPermissionsStructure {
        String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-permissions-object-guild-application-command-permissions-structure";
        String  Id="id",
                ApplicationId="application_id",
                GuildId="guild_id",
                Permissions="permissions";
        public  interface ApplicationCommandPermissionsStructure {
            String objectReferenceUrl="https://discord.com/developers/docs/interactions/application-commands#application-command-permissions-object-application-command-permissions-structure";
            String  Id="id",
                    Type="type",
                    Permission="permission";
        }
    }
    String keyMaxMembers="maxMembers",keyEmotesCount="emotesCount";
    String keyDate="date",keyTime="time",keyDayOfWeek="dayOfWeek",keyDayOfYear="dayOfYear";
    String keyColorRaw="color_raw",keyColorRGBA="color_rgba";
    String keyPositionRaw="position_raw",keyIsSynced="is_synced",keyIsNews="is_news";

}
