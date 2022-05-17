package web.jexpress.models.collectdata;

import express.http.response.Response;
import express.utils.MediaType;
import express.utils.Status;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;

public interface expKeys {

    public static final String
            keyGuildID="guild_id",
            keyMemberId="member_id",
            keyUserId="user_id",
            keyChannelId="channel_id",
            keyTextChannelId="text_channel_id",
            keyVoiceChannelId="voice_channel_id",
            keyCategoryId="category_id",
            keyRoleId="role_id",
            keyEmoteId="emote_id",
            keyMessageContent ="message_content",keyMessageId="message_id";
    public static final String
            keyName="name",
            keyDscriminator="discriminator",
            keyId="id",
            keyAvatarUrl="avatarUrl",
            keyTimeJoined="timeJoined",
            keyTimeCreated="timeCreated",
            keyColorRaw="colorRaw",
            keyColorRGBA="colorRGBA",
            keyIsHoisted="isHoisted",
            keyIsPublicRole="isPublicRole",
            keyIsMentionable="isMentionable",
            keyIsManaged ="isManaged",
            keyRoles="roles",
            keyEmotes="emotes",
            keyPermissions="permissions",
            keyNickname="nickname",
            keyIsOwner="isOwner",
            keyIsSynced="isSynced",
            keyParent="parent",
            keyIsNsfw="isNSFW",
            keyIsNews="isNews",
            keySlowmode="slowmode",
            keyTopic="topic",
            keyBitrate="bitrate",
            keyUserLimit="userLimit",
            keyImageUrl="imageUrl",
            keyIsAnimated="isAnimated",
            keyIsAvailable="isAvailable",
            keyType="type",
            keyPosition="position",
            keyPositionRaw="positionRaw",
            keyMembers="members";
    public static final String
            keyIconUrl="iconUrl",
            keyTextChannelsCount="textChannelsCount",
            keyVoiceChannelsCount="voiceChannelsCount",
            keyRolesCount="rolesCount",
            keyMembersCount="membersCount",
            keyMaxMembers="maxMembers",
            keyEmotesCount="emotesCount",
            keyBannerUrl="bannerUrl",
            keySplashUrl="splashUrl",
            keyRegion="region",
            keyExplicitContentLevel="explicitContentLevel",
            keyFeatures="features";
    public static final String
            keyUser="user",
            keyDate="date",
            keyTime="time",
            keyDayOfYear="dayOfYear",
            keyDayOfWeek="dayOfWeek",
            keyChannels="channels",
            keyTextChannels="textChannels",
            keyVoiceChannels="voiceChannels",
            keyIcon="icon",
            keyBot="bot",
            keyEmail="email",
            keyFullName="fullName",
            keyVerified="verified",
            keyFlags="flags",
            keyPremiumType="premiumType",
            keyGuilds="guilds",
            keyGuild="guild",
            keyReason="reason",
            keyBans="bans";
    public static final String
            option_Code="code",
            option_Auth="auth",
            option_Token="token",
            option_IncludeRoles="includeRoles",
            option_IncludePermissions="includePermissions",
            option_IncludeGuilds="includeGuilds";
    public static final String
            keySessionAccessToken="accessToken",
            keySessionRefreshToken="refreshToken";
    public static final String
            keySessionUserId="sessionUserId",
            keySessionUserCode="sessionUserCode";
    public  static String
        keyMeta="meta",keyInfo="info";


    public static final String
            keySessionToken = "sessiontoken";



}
