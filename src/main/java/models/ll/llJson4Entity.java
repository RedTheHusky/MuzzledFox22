package models.ll;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.helper.lcAuditLogChangeEntryHelper;
import models.lc.helper.lcAuditLogEntryHelper;
import models.lc.sticker.lcGuildStickerPack;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogChange;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.audit.TargetType;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public interface llJson4Entity extends llCommonKeys {
    default JSONObject llGetMemberJsonEntry(Member member){
        Logger logger = Logger.getLogger("getMemberJsonEntry");
        try{
            JSONObject jsonMember=new JSONObject();JSONObject jsonObject;JSONArray jsonArray;
            String memberId=member.getId();
            Guild guild=member.getGuild();
            String memberEffectiveName=member.getEffectiveName();
            String memberNickname=member.getNickname();
            OffsetDateTime memberJoined=member.getTimeJoined();
            boolean memberIsOwner=member.isOwner();
            int memberColor=member.getColorRaw();
            OffsetDateTime memberBoosted=member.getTimeBoosted();
            GuildVoiceState memberVoiceState=member.getVoiceState();
            User user=member.getUser();
            String memberName=user.getName();
            String memberAvatarUrl=user.getAvatarUrl();
            String memberDiscriminator=user.getDiscriminator();
            boolean memberIsBot=user.isBot();

            logger.info("memberId="+memberId);
            logger.info("memberName="+memberName);logger.info("memberDiscriminator="+memberDiscriminator);
            logger.info("memberEffectiveName="+memberEffectiveName);
            jsonMember.put(keyId,memberId);
            jsonMember.put(keyName,memberName);jsonMember.put(keyDiscriminator,memberDiscriminator);
            jsonMember.put(keyEffectiveName,memberEffectiveName);
            if( memberAvatarUrl!=null){
                jsonObject=new JSONObject();
                logger.info("memberAvatarUrl="+memberAvatarUrl);
                jsonObject.put(keyAvatarUrl, memberAvatarUrl);
                String memberAvatarId=user.getAvatarId();
                jsonObject.put(keyAvatarId, memberAvatarId);
                jsonMember.put(keyAvatar,jsonObject);jsonObject=null;
            }
            logger.info("memberColor="+memberColor);
            logger.info("memberIsBot="+memberIsBot);
            jsonMember.put(keyColor,memberColor);
            jsonMember.put(keyIsBot,memberIsBot);
            logger.info("memberIsOwner="+memberIsOwner);
            jsonMember.put(keyIsOwner,memberIsOwner);

            logger.info("memberJoined="+memberJoined);
            jsonObject=new JSONObject();
            jsonObject.put(keyTimeHumanReadable,memberJoined);
            jsonObject.put(keyTimeEpoch,memberJoined.toInstant().toEpochMilli());
            jsonMember.put(keyTimeJoined,jsonObject);
            if(memberBoosted!=null){
                logger.info("memberBoosted="+memberBoosted);
                jsonObject=new JSONObject();
                jsonObject.put(keyTimeHumanReadable,memberBoosted);
                jsonObject.put(keyTimeEpoch,memberBoosted.toInstant().toEpochMilli());
                jsonMember.put(keyTimeBoosted,jsonObject);
            }
            jsonObject=null;

            if(memberNickname!=null){
                logger.info("memberNickname="+memberNickname);
                jsonMember.put(keyNickname,memberNickname);
            }

            if(memberVoiceState!=null){
                boolean memberIsStream=memberVoiceState.isStream();
                boolean memberIsDeafened=memberVoiceState.isDeafened();
                boolean memberIsMuted=memberVoiceState.isMuted();
                boolean memberIsSelfDeafened=memberVoiceState.isSelfDeafened();
                boolean memberIsSelfMuted=memberVoiceState.isSelfMuted();
                boolean memberIsSuppressed=memberVoiceState.isSuppressed();
                boolean memberIsGuildDeafened=memberVoiceState.isGuildDeafened();
                boolean memberIsGuildMuted=memberVoiceState.isGuildMuted();
                VoiceChannel memberVoiceChannel=memberVoiceState.getChannel();
                jsonObject=new JSONObject();
                jsonObject.put(keyIsStream,memberIsStream);
                jsonObject.put(keyIsDeafened,memberIsDeafened);
                jsonObject.put(keyIsMuted,memberIsMuted);
                jsonObject.put(keyIsSuppressed,memberIsSuppressed);
                jsonObject.put(keyIsSelfDeafened,memberIsSelfDeafened);
                jsonObject.put(keyIsSelfMuted,memberIsSelfMuted);
                jsonObject.put(keyIsGuildDeafened,memberIsGuildDeafened);
                jsonObject.put(keyIsGuildMuted,memberIsGuildMuted);
                if(memberVoiceChannel!=null){
                    jsonObject.put(keyVoiceChannel,memberVoiceChannel.getId());
                }
                jsonMember.put(keyVoiceState,jsonObject);jsonObject=null;
            }
            memberAvatarUrl=memberEffectiveName=memberDiscriminator=memberName=memberNickname=null;memberJoined=memberBoosted=null;


            List<Role> roles=member.getRoles();
            jsonArray=new JSONArray();
            logger.info("roles.size="+roles.size());
            for(Role role : roles){
                jsonObject=new JSONObject();
                jsonObject.put(keyId,role.getId());
                jsonObject.put(keyName,role.getName());
                jsonArray.put(jsonObject);jsonObject=null;
            }roles=null;
            jsonMember.put(keyRoles,jsonArray);

            EnumSet<Permission> permissions=member.getPermissions();
            jsonArray=new JSONArray();
            logger.info("permissions.size="+permissions.size());
            for(Permission permission: permissions){
                jsonArray.put(permission.getName());
            }permissions=null;
            jsonMember.put(keyPermissions,jsonArray);jsonArray=null;
            try{
                List<GuildChannel>channels=guild.getChannels();
                jsonArray=new JSONArray();
                logger.info("channels.size="+channels.size());
                for(GuildChannel channel : channels){
                    List<PermissionOverride>permissionOverrides=channel.getMemberPermissionOverrides();
                    String channelId=channel.getId();
                    logger.info("channel["+channelId+"].permissionOverrides.size="+permissionOverrides.size());
                    for(PermissionOverride permissionOverride : permissionOverrides){
                        if(permissionOverride.getMember().getId().equalsIgnoreCase(memberId)){
                            jsonObject=new JSONObject();JSONArray arrayPermissions;
                            String channelName=channel.getName();
                            logger.info("channel["+channelId+"].is valid channel");
                            jsonObject.put(keyId,channelId);
                            jsonObject.put(keyName,channelName);

                            permissions=permissionOverride.getAllowed();
                            arrayPermissions=new JSONArray();
                            logger.info("allowed.size="+permissions.size());
                            for(Permission permission :permissions){
                                arrayPermissions.put(permission.getName());
                            }
                            jsonObject.put(keyAllow,arrayPermissions);arrayPermissions=null;

                            permissions=permissionOverride.getDenied();
                            arrayPermissions=new JSONArray();
                            logger.info("denied.size="+permissions.size());
                            for(Permission permission :permissions){
                                arrayPermissions.put(permission.getName());
                            }
                            jsonObject.put(keyDeny,arrayPermissions);arrayPermissions=null;

                            permissions=permissionOverride.getInherit();
                            arrayPermissions=new JSONArray();
                            logger.info("inherit.size="+permissions.size());
                            for(Permission permission :permissions){
                                arrayPermissions.put(permission.getName());
                            }
                            jsonObject.put(keyInherit,arrayPermissions);arrayPermissions=null;

                            jsonArray.put(jsonObject);
                        }
                    }

                }channels=null;
                jsonMember.put(keyPermissionOverrides,jsonArray);

            }catch (Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            }
            jsonArray=null;jsonObject=null;
            return jsonMember;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetRoleJsonEntry(Role role){
        Logger logger = Logger.getLogger("llGetRoleJsonEntry");
        try{
            JSONObject jsonRole=new JSONObject();JSONObject jsonObject; JSONArray jsonArray;
            String roleId=role.getId();
            String roleName=role.getName();
            int roleColor=role.getColorRaw();
            boolean roleHoisted=role.isHoisted();
            boolean roleMentionable=role.isMentionable();
            boolean rolePublicRole=role.isPublicRole();
            int rolePosition=role.getPositionRaw();
            logger.info("roleId="+roleId);
            logger.info("roleName="+roleName);
            logger.info("roleColor="+roleColor);
            logger.info("roleHoisted="+roleHoisted);
            logger.info("roleMentionable="+roleMentionable);
            logger.info("rolePublicRole="+rolePublicRole);
            logger.info("rolePosition="+rolePosition);
            jsonRole.put(keyId,roleId);
            jsonRole.put(keyName,roleName);
            jsonRole.put(keyColor,roleColor);
            jsonRole.put(keyHoisted,roleHoisted);
            jsonRole.put(keyMentionable,roleMentionable);
            jsonRole.put(keyPublicRole,rolePublicRole);
            jsonRole.put(keyPosition,rolePosition);

            OffsetDateTime channelTimeCreated=role.getTimeCreated();
            logger.info("channelTimeCreated="+channelTimeCreated);
            jsonObject=new JSONObject();
            jsonObject.put(keyTimeHumanReadable,channelTimeCreated);
            jsonObject.put(keyTimeEpoch,channelTimeCreated.toInstant().toEpochMilli());
            jsonRole.put(keyTimeCreated,jsonObject);jsonObject=null;

            EnumSet<Permission>permissions=role.getPermissions();
            logger.info("permissions.Size="+permissions.size());
            jsonArray=new JSONArray();
            for(Permission permission : permissions){
                String permissionName=permission.getName();
                logger.info("permissionName="+permissionName);
                jsonArray.put(permissionName);
            }permissions=null;
            jsonRole.put(keyPermissions,jsonArray);jsonArray=null;
            return  jsonRole;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetChannelJsonEntry(GuildChannel channel){
        Logger logger = Logger.getLogger("llGetChannelJsonEntry");
        try{
            JSONObject jsonChannel=new JSONObject();JSONObject jsonObject;JSONArray jsonArray;
            String channelId=channel.getId();
            Guild guild=channel.getGuild();
            ChannelType channelType=channel.getType();
            int channelTypeId=channelType.getId();
            String channelName=channel.getName();
            int channelPosition=channel.getPositionRaw();
            //channel.getPermissionOverrides();
            Category channelParent=channel.getParent();
            logger.info("channelId="+channelId);
            logger.info("channelName="+channelName);
            logger.info("channelPosition="+channelPosition);
            logger.info("channelTypeId="+channelTypeId);
            jsonChannel.put(keyId,channelId);
            jsonChannel.put(keyName,channelName);
            jsonChannel.put(keyPosition,channelPosition);
            jsonChannel.put(keyType,channelTypeId);
            OffsetDateTime userTimeCreated=channel.getTimeCreated();
            logger.info("userTimeCreated="+userTimeCreated);
            jsonObject=new JSONObject();
            jsonObject.put(keyTimeHumanReadable,userTimeCreated);
            jsonObject.put(keyTimeEpoch,userTimeCreated.toInstant().toEpochMilli());
            jsonChannel.put(keyTimeCreated,jsonObject);jsonObject=null;

            if(channelParent!=null){
                logger.info("channelParent="+channelParent.getId());
                jsonChannel.put(keyParent,channelParent.getId());}
            if(channelType==ChannelType.TEXT){
                TextChannel textChannel=guild.getTextChannelById(channelId);
                String channelTopic=textChannel.getTopic();
                boolean channelNSFW=textChannel.isNSFW();
                int channelSlowmode=textChannel.getSlowmode();
                logger.info("channelTopic="+channelTopic);
                logger.info("channelNSFW="+channelNSFW);
                logger.info("channelSlowmode="+channelSlowmode);
                if(channelTopic!=null){ jsonChannel.put(keyTopic,channelTopic);}
                jsonChannel.put(keyNsfw,channelNSFW);
                jsonChannel.put(keySlowmode,channelSlowmode);
            }
            if(channelType==ChannelType.VOICE){
                VoiceChannel voiceChannel=guild.getVoiceChannelById(channelId);
                int channelBitrate=voiceChannel.getBitrate();
                int channelUserLimit=voiceChannel.getUserLimit();
                logger.info("channelBitrate="+channelBitrate);
                logger.info("channelUserLimit="+channelUserLimit);
                jsonChannel.put(keyBitrate,channelBitrate);
                jsonChannel.put(keyUserLimit,channelUserLimit);
            }
            if(channelType==ChannelType.CATEGORY){
                Category category=guild.getCategoryById(channelId);
                List<GuildChannel>childs=category.getChannels();
                jsonArray=new JSONArray();
                for(GuildChannel child :childs){
                    jsonArray.put(child.getId());
                }
                logger.info("childs="+childs.size());
                jsonChannel.put(keyChilds,jsonArray);
            }
            channelName=null;

            OffsetDateTime channelTimeCreated=channel.getTimeCreated();
            logger.info("channelTimeCreated="+channelTimeCreated);
            JSONObject jsonTime=new JSONObject();
            jsonTime.put(keyTimeHumanReadable,channelTimeCreated);
            jsonTime.put(keyTimeEpoch,channelTimeCreated.toInstant().toEpochMilli());
            jsonChannel.put(keyTimeCreated,jsonTime);

            List<PermissionOverride>permissions4Roles=channel.getRolePermissionOverrides();
            JSONArray arrayPermissions;JSONObject permissionsOverride;EnumSet<Permission>permissions;
            logger.info("permissions4Roles.size="+permissions4Roles.size());
            jsonArray=new JSONArray();
            try{
                for(PermissionOverride permissions4Role :permissions4Roles){
                    Role role=permissions4Role.getRole();
                    permissionsOverride=new JSONObject();
                    permissionsOverride.put(keyId,role.getId());
                    permissionsOverride.put(keyName,role.getName());

                    permissions=permissions4Role.getAllowed();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyAllow,arrayPermissions);

                    permissions=permissions4Role.getDenied();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyDeny,arrayPermissions);

                    permissions=permissions4Role.getInherit();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyInherit,arrayPermissions);

                    jsonArray.put(permissionsOverride);
                }permissions4Roles=null;arrayPermissions=null;
                jsonChannel.put(keyRolesPermission,jsonArray);jsonArray=null;
            }catch (Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try{
                List<PermissionOverride>permissions4Members=channel.getMemberPermissionOverrides();
                logger.info("permissions4Members.size="+permissions4Members.size());
                jsonArray=new JSONArray();
                for(PermissionOverride permissions4Member :permissions4Members){
                    Member member=permissions4Member.getMember();
                    permissionsOverride=new JSONObject();
                    permissionsOverride.put(keyId,member.getId());
                    permissionsOverride.put(keyName,member.getUser().getName());
                    permissionsOverride.put(keyDiscriminator,member.getUser().getDiscriminator());

                    permissions=permissions4Member.getAllowed();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyAllow,arrayPermissions);

                    permissions=permissions4Member.getDenied();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyDeny,arrayPermissions);

                    permissions=permissions4Member.getInherit();
                    arrayPermissions=new JSONArray();
                    for(Permission permission :permissions){
                        arrayPermissions.put(permission.getName());
                    }
                    permissionsOverride.put(keyInherit,arrayPermissions);

                    jsonArray.put(permissionsOverride);

                }permissions4Members=null;arrayPermissions=null;
                jsonChannel.put(keyMembersPermissions,jsonArray);jsonArray=null;
            }catch (Exception e){
                logger.error(".exception=" + e);
                logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return jsonChannel;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetBanJsonEntry(Guild.Ban ban){
        Logger logger = Logger.getLogger("getBanJsonEntry");
        try{
            JSONObject jsonBan=new JSONObject();JSONObject jsonObject;
            User user=ban.getUser();
            String userId=user.getId();
            String userName=user.getName();
            String userAvatarUrl=user.getAvatarUrl();
            String userDiscriminator=user.getDiscriminator();
            boolean userIsBot=user.isBot();
            logger.info("userId="+userId);
            logger.info("userName="+userName);logger.info("userDiscriminator="+userDiscriminator);
            jsonBan.put(keyId,userId);
            jsonBan.put(keyName,userName);jsonBan.put(keyDiscriminator,userDiscriminator);
            if( userAvatarUrl!=null){
                logger.info("userAvatarUrl="+userAvatarUrl);
                jsonObject=new JSONObject();
                String userAvatarId=user.getAvatarId();
                jsonObject.put(keyAvatarUrl, userAvatarUrl);
                jsonObject.put(keyAvatarId, userAvatarId);
                jsonBan.put(keyAvatar,jsonObject);jsonObject=null;
            }
            logger.info("userIsBot="+userIsBot);
            jsonBan.put(keyIsBot,userIsBot);

            OffsetDateTime userTimeCreated=user.getTimeCreated();
            logger.info("userTimeCreated="+userTimeCreated);
            jsonObject=new JSONObject();
            jsonObject.put(keyTimeHumanReadable,userTimeCreated);
            jsonObject.put(keyTimeEpoch,userTimeCreated.toInstant().toEpochMilli());
            jsonBan.put(keyTimeCreated,jsonObject);jsonObject=null;

            String banReason=ban.getReason();
            logger.info("banReason="+banReason);
            jsonBan.put(keyReason,banReason);
            return jsonBan;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONArray  llGetServerImageEntries(Guild guild){
        Logger logger = Logger.getLogger("getServerImageEntries");
        try{
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject;
            String serverIconUrl=guild.getIconUrl();
            if(serverIconUrl!=null&&!serverIconUrl.isBlank()){
                jsonObject=new JSONObject();
                jsonObject.put(keyImageUrl,serverIconUrl);
                jsonObject.put(keyImageId,guild.getIconId());
                jsonObject.put(keyType,keyIcon);
                jsonArray.put(jsonObject);jsonObject=null;
            }
            String serverSplashUrl=guild.getSplashUrl();
            if(serverSplashUrl!=null&&!serverSplashUrl.isBlank()){
                jsonObject=new JSONObject();
                jsonObject.put(keyImageUrl,serverSplashUrl);
                jsonObject.put(keySplashId,guild.getSplashId());
                jsonObject.put(keyType,keySplash);
                jsonArray.put(jsonObject);jsonObject=null;
            }
            String serverBannerUrl=guild.getBannerUrl();
            if(serverBannerUrl!=null&&!serverBannerUrl.isBlank()){
                jsonObject=new JSONObject();
                jsonObject.put(keyImageUrl,serverBannerUrl);
                jsonObject.put(keyImageId,guild.getBannerId());
                jsonObject.put(keyType,keyBanner);
                jsonArray.put(jsonObject);jsonObject=null;
            }
            return jsonArray;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetAuditJsonEntry(AuditLogEntry auditEntry){
        Logger logger = Logger.getLogger("getAuditJsonEntry");
        try{
            lcAuditLogEntryHelper gLogEntryHelper=new lcAuditLogEntryHelper();
            lcAuditLogChangeEntryHelper gLogChangeEntryHelper= new lcAuditLogChangeEntryHelper();
            gLogEntryHelper.selectEntry(auditEntry);
            JSONObject jsonEntry=new JSONObject();JSONObject tmp;

            jsonEntry.put(keyId,auditEntry.getId());
            ActionType actionType=auditEntry.getType();
            jsonEntry.put(keyType,actionType.getKey());

            {
                tmp=new JSONObject();
                TargetType type=auditEntry.getTargetType();
                tmp.put(keyType,type.name());
                tmp.put(keyId,auditEntry.getTargetId());
                String name= gLogEntryHelper.getTargetName();
                if(name!=null&&!name.isBlank()){
                    tmp.put(keyName,name);
                }
                User tUser= gLogEntryHelper.getTargetUser();
                if(tUser!=null){
                    tmp.put(keyIsBot,tUser.isBot());
                }
                if(gLogEntryHelper.isTargetChannel()){
                    tmp.put(keyChannelType, gLogEntryHelper.getTargetChannelType());
                    if(gLogEntryHelper.isTargetTextChannel()){
                        tmp.put(keyChannelNSFW, gLogEntryHelper.isTargetNSFWChannel());
                    }
                }
                jsonEntry.put(keyTarget,tmp);
            }

            if(auditEntry.getReason()!=null){
                jsonEntry.put(keyReason,auditEntry.getReason());
            }
            if(auditEntry.getUser()!=null){
                tmp=new JSONObject();
                tmp.put(keyName,auditEntry.getUser().getName());
                tmp.put(keyDiscriminator,auditEntry.getUser().getDiscriminator());
                tmp.put(keyId,auditEntry.getUser().getId());
                jsonEntry.put(keyAuthor,tmp);
            }
            try {
                Map<String, AuditLogChange> changes = auditEntry.getChanges();
                tmp=new JSONObject();
                for (Map.Entry<String, AuditLogChange> entry : changes.entrySet()) {
                    JSONObject tmp2=new JSONObject();
                    String key = entry.getKey();
                    AuditLogChange value = entry.getValue();
                    gLogChangeEntryHelper.selectEntry(value);
                    String newValue= gLogChangeEntryHelper.getNewValue2String();
                    String oldValue= gLogChangeEntryHelper.getOldValue2String();
                    if(newValue!=null&&oldValue!=null){
                        if(newValue.equals(oldValue)){
                            tmp2.put(keyBasicValue,newValue);
                        }else{
                            tmp2.put(keyNewValue,newValue);
                            tmp2.put(keyOldValue,oldValue);
                        }
                    }else
                    if(newValue!=null){
                        tmp2.put(keyBasicValue,newValue);
                    }else
                    if(oldValue!=null){
                        tmp2.put(keyBasicValue,oldValue);
                    }
                    tmp.put(key,tmp2);
                                /*AuditLogEntry entry = auditLogEntries.get(0);
                                Map<String, AuditLogChange> changes = entry.getChanges();
                                AuditLogChange change = changes.get("$remove");
                                @SuppressWarnings("unchecked")
                                HashMap<String, String> role = ((ArrayList<HashMap<String, String>>) change.getNewValue()).get(0);*/
                }
                jsonEntry.put(keyChanges,tmp);
            }catch (Exception e){
                logger.error(".changes exception:" + Arrays.toString(e.getStackTrace()));
                logger.error(".exception:" + e);
                tmp=new JSONObject();
                tmp.put(keyException,e.toString());
                jsonEntry.put(keyChanges,tmp);
            }
            return jsonEntry;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetInviteJsonEntry(Invite invite){
        Logger logger = Logger.getLogger("getInviteJsonEntry");
        try{
            JSONObject jsonInvite=new JSONObject();
            Invite.InviteType inviteType=invite.getType();
            Invite.Channel inviteChannel=invite.getChannel();
            String inviteCode=invite.getCode();
            Invite.Group inviteGroup=invite.getGroup();
            User inviteInviter=invite.getInviter();
            String inviteUrl=invite.getUrl();
            OffsetDateTime inviteTimeCreated=invite.getTimeCreated();
            logger.info("userTimeCreated="+inviteTimeCreated);
            JSONObject jsonTime=new JSONObject();
            jsonTime.put(keyTimeHumanReadable,inviteTimeCreated);
            jsonTime.put(keyTimeEpoch,inviteTimeCreated.toInstant().toEpochMilli());
            jsonInvite.put(keyTimeCreated,jsonTime);
            logger.info("inviteType="+inviteType);
            jsonInvite.put(keyType,inviteType);
            if(inviteChannel!=null){
                logger.info("inviteChannel="+inviteChannel.getId());
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyId,inviteChannel.getId());
                jsonObject.put(keyName,inviteChannel.getName());
                jsonInvite.put(keyChannel,jsonObject);
            }
            logger.info("inviteCode="+inviteCode);
            jsonInvite.put(keyCode,inviteCode);
            if(inviteGroup!=null){
                JSONObject jsonObject=new JSONObject();
                String iconUrl=inviteGroup.getIconUrl();
                String iconId=inviteGroup.getIconId();
                String name=inviteGroup.getName();
                List<String>users=inviteGroup.getUsers();
                JSONArray array=new JSONArray();
                for(String user : users){
                    array.put(user);
                }
                jsonObject.put(keyUsers,array);
                jsonObject.put(keyIconId,iconId);
                jsonObject.put(keyIconUrl,iconUrl);
                jsonObject.put(keyName,name);
                jsonInvite.put(keyGroup,jsonObject);
            }
            if(inviteInviter!=null){
                logger.info("inviteInviter="+inviteInviter.getId());
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyId,inviteInviter.getId());
                jsonObject.put(keyName,inviteInviter.getName());
                jsonObject.put(keyDiscriminator,inviteInviter.getDiscriminator());
                jsonInvite.put(keyAuthor,jsonObject);
            }
            logger.info("inviteUrl="+inviteUrl);
            jsonInvite.put(keyUrl,inviteUrl);
            int inviteUses=invite.getUses();
            int inviteMaxUses=invite.getMaxUses();
            int inviteMaxAge=invite.getMaxAge();
            boolean inviteIsExpanded=invite.isExpanded();
            boolean inviteIsTemporary=invite.isTemporary();
            logger.info("inviteUses="+inviteUses);
            logger.info("inviteMaxUses="+inviteMaxUses);
            logger.info("inviteMaxAge="+inviteMaxAge);
            logger.info("inviteIsExpanded="+inviteIsExpanded);
            logger.info("inviteIsTemporary="+inviteIsTemporary);
            jsonInvite.put(keyUses,inviteUses);
            jsonInvite.put(keyMaxUses,inviteMaxUses);
            jsonInvite.put(keyMaxAge,inviteMaxAge);
            jsonInvite.put(keyIsExpanded,inviteIsExpanded);
            jsonInvite.put(keyIsTemporary,inviteIsTemporary);
            return jsonInvite;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetEmoteJsonEntry(Emote emote){
        Logger logger = Logger.getLogger("llGetEmoteJsonEntry");
        try{
            JSONObject jsonEmote=new JSONObject();
            Guild guild=emote.getGuild();
            String emoteId=emote.getId();
            String emoteName=emote.getName();
            String emoteImageUrl=emote.getImageUrl();
            boolean emoteIsAnimated=emote.isAnimated();
            logger.info("emoteId="+emoteId);
            logger.info("emoteName="+emoteName);
            logger.info("emoteImageUrl="+emoteImageUrl);
            logger.info("emoteIsAnimated="+emoteIsAnimated);
            jsonEmote.put(keyId,emoteId);
            jsonEmote.put(keyName,emoteName);
            jsonEmote.put(keyImageUrl,emoteImageUrl);
            jsonEmote.put(keyIsAnimated,emoteIsAnimated);
            OffsetDateTime emoteTimeCreated=emote.getTimeCreated();
            logger.info("emoteTimeCreated="+emoteTimeCreated);
            JSONObject jsonTime=new JSONObject();
            jsonTime.put(keyTimeHumanReadable,emoteTimeCreated);
            jsonTime.put(keyTimeEpoch,emoteTimeCreated.toInstant().toEpochMilli());
            jsonEmote.put(keyTimeCreated,jsonTime);

            List<Role>roles=emote.getRoles();
            logger.info("roles.Size="+roles.size());
            JSONArray arrayPermission=new JSONArray();
            for(Role role : roles){
                JSONObject jsonRole=new JSONObject();
                jsonRole.put(keyId,role.getId());
                jsonRole.put(keyName,role.getName());
                arrayPermission.put(jsonRole);
            }roles=null;
            jsonEmote.put(keyRoles,arrayPermission);arrayPermission=null;
            return  jsonEmote;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetEmoteJsonEntry_wGuild(Emote emote){
        Logger logger = Logger.getLogger("llGetEmoteJsonEntry_wGuild");
        try{
            JSONObject jsonEmote=llGetEmoteJsonEntry(emote);
            jsonEmote.put(llCommonKeys.keyGuild_Id,emote.getGuild().getId());
            return  jsonEmote;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject llGetEmotesJsonEntry(Guild guild) {
        String fName = "[llGetEmotesJsonEntry]";
        Logger logger = Logger.getLogger("llJson4Entity");
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.keyId,guild.getIdLong());
            jsonObject.put(llCommonKeys.keyName,guild.getName());
            List<Emote>emotes=guild.getEmotes();
            jsonObject.put(llCommonKeys.keyCount,emotes.size());
            if(emotes.isEmpty()){
                jsonObject.put(llCommonKeys.keyEmotes,new JSONArray());
            }else{
                JSONArray jsonArray=new JSONArray();
                for(Emote emote:emotes){
                    try {
                        JSONObject jsonEmote=llGetEmoteJsonEntry(emote);
                        jsonArray.put(jsonEmote);
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonObject.put(llCommonKeys.keyEmotes,jsonArray);
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    default JSONObject  llGetServerEntry(Guild guild){
        Logger logger = Logger.getLogger("llGetServerEntry");
        try{
            JSONObject jsonServer=new JSONObject(); JSONObject jsonObject;
            String guildId=guild.getId();
            String guildName=guild.getName();
            logger.info("guildId="+guildId);logger.info("guildName="+guildName);
            String guildDescription=guild.getDescription();
            if(guildDescription!=null){
                logger.info("guildDescription="+guildDescription);
            }
            Member guildOwner=guild.getOwner();
            if(guildOwner!=null){
                String ownerId=guildOwner.getId();
                String ownerDiscriminator=guildOwner.getUser().getDiscriminator();
                String ownerName=guildOwner.getUser().getName();
                logger.info("ownerId="+ownerId);
                logger.info("ownerDiscriminator="+ownerDiscriminator);
                logger.info("ownerName="+ownerName);
                jsonObject=new JSONObject();
                jsonObject.put(keyId,ownerId);
                jsonObject.put(keyName,ownerName);
                jsonObject.put(keyDiscriminator,ownerDiscriminator);
                jsonServer.put(keyOwner,jsonObject);jsonObject=null;
            }
            String guildIconUrl=guild.getIconUrl();
            if(guildIconUrl!=null){
                logger.info("guildIconUrl="+guildIconUrl);
                String guildIconId=guild.getIconId();
                jsonObject=new JSONObject();
                jsonObject.put(keyIconUrl,guildIconUrl);
                jsonObject.put(keyIconId,guildIconId);
                jsonServer.put(keyIcon,jsonObject);jsonObject=null;
            }
            String guildBannerUrl=guild.getBannerUrl();
            if(guildBannerUrl!=null){
                logger.info("guildBannerUrl="+guildBannerUrl);
                String guildBannerId=guild.getBannerId();
                jsonObject=new JSONObject();
                jsonObject.put(keyBannerUrl,guildBannerUrl);
                jsonObject.put(keyBannerId,guildBannerId);
                jsonServer.put(keyBanner,jsonObject);jsonObject=null;
            }
            String guildSplashUrl=guild.getSplashUrl();
            if(guildSplashUrl!=null){
                logger.info("guildSplashUrl="+guildSplashUrl);
                String guildSplashId=guild.getSplashId();
                jsonObject=new JSONObject();
                jsonObject.put(keySplashUrl,guildSplashUrl);
                jsonObject.put(keySplashId,guildSplashId);
                jsonServer.put(keySplash,jsonObject);jsonObject=null;
            }
            Guild.BoostTier guildBoostTier=guild.getBoostTier();
            if(guildBoostTier!=null){
                int guildBoostCount=guild.getBoostCount();
                int guildBoosTierKey=guildBoostTier.getKey();
                jsonObject=new JSONObject();
                jsonObject.put(keyCount,guildBoostCount);
                jsonObject.put(keyTier,guildBoosTierKey);
                jsonServer.put(keyBoost,jsonObject);jsonObject=null;
            }

            int guildSizeTextChannels=guild.getTextChannels().size();
            int guildSizeVoiceChannels=guild.getVoiceChannels().size();
            int guildSizeCategories=guild.getCategories().size();
            int guildSizeMembers=guild.getMembers().size();
            int guildSizeEmotes=guild.getEmotes().size();
            int guildSizeWebhooks=guild.retrieveWebhooks().complete().size();
            int guildSizeInvites=guild.retrieveInvites().complete().size();
            int guildSizeBanList=guild.retrieveBanList().complete().size();
            logger.info("guildSizeTextChannels="+guildSizeTextChannels);logger.info("guildSizeVoiceChannels="+guildSizeVoiceChannels);
            logger.info("guildSizeCategories="+guildSizeCategories);logger.info("guildSizeMembers="+guildSizeMembers);
            logger.info("guildSizeEmotes="+guildSizeEmotes);logger.info("guildSizeWebhooks="+guildSizeWebhooks);
            logger.info("guildSizeInvites="+guildSizeInvites);logger.info("guildSizeBanList="+guildSizeBanList);
            jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.keyGuild_Id,guild.getIdLong());
            jsonObject.put(keyBanList,guildSizeBanList);
            jsonObject.put(keyCategories,guildSizeCategories);
            jsonObject.put(keyEmotes,guildSizeEmotes);
            jsonObject.put(keyInvites,guildSizeInvites);
            jsonObject.put(keyMembers,guildSizeMembers);
            jsonObject.put(keyTextChannels,guildSizeTextChannels);
            jsonObject.put(keyVoiceChannels,guildSizeVoiceChannels);
            jsonObject.put(keyWebhooks,guildSizeWebhooks);
            jsonServer.put(keySize,jsonObject);jsonObject=null;

            return jsonServer;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    default JSONObject  llGetServerStickersEntry(Guild guild){
        Logger logger = Logger.getLogger("llGetServerStickersEntry");
        try{
            JSONObject jsonObject=new JSONObject();
            lcGuildStickerPack stickerPack=new lcGuildStickerPack(guild);
            JSONArray jsonArray=stickerPack.getStickersAsJson();
            jsonObject.put(llCommonKeys.keyGuild_Id,guild.getIdLong());
            jsonObject.put(llCommonKeys.keyCount,jsonArray.length());
            jsonObject.put("stickers",jsonArray);
            return jsonObject;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
}
