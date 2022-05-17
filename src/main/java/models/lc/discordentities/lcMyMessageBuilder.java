package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.annotations.ForRemoval;
import net.dv8tion.jda.annotations.ReplaceWith;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.exceptions.MissingAccessException;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.entities.DataMessage;
import net.dv8tion.jda.internal.entities.EntityBuilder;
import net.dv8tion.jda.internal.requests.Route;
import net.dv8tion.jda.internal.requests.restaction.MessageActionImpl;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.internal.utils.Helpers;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lcMyMessageBuilder implements Appendable{
    //https://discord.com/developers/docs/resources/channel#create-message
    Logger logger = Logger.getLogger(getClass());

    public lcMyMessageBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageBuilder clear() {
        String fName="[clear]";
        try {
            this.builder.setLength(0);  
            this.isTTS = false;
            this.nonce=null;
            this.embed = null;
            this.allowedMentions=null;
            this.mentionedUsers = new HashSet();
            this.mentionedRoles = new HashSet();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageBuilder getBuildMessage() {
        String fName="[getBuildMessage]";
        try {
            MessageBuilder messageBuilder=new MessageBuilder();
            
            return messageBuilder;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    
    protected StringBuilder builder = new StringBuilder();
    protected boolean isTTS = false;
    protected String nonce;
    protected MessageEmbed embed;
    protected EnumSet<Message.MentionType> allowedMentions = null;
    protected Set<String> mentionedUsers = new HashSet();
    protected Set<String> mentionedRoles = new HashSet();
   
    public lcMyMessageBuilder setTTS(boolean tts) {
        String fName="[setTTS]";
        try {
            logger.info(fName + ".value="+tts);
            this.isTTS = tts;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public boolean getTTS() {
        String fName="[getTTS]";
        try {
            logger.info(fName + ".value="+this.isTTS);
            return this.isTTS;
        }catch (Exception e){
            return false;
        }
    }
    public lcMyMessageBuilder setEmbed(@Nullable MessageEmbed embed) {
        String fName="[setEmbed]";
        try {
            logger.info(fName + ".value="+embed.toString());
            this.embed = embed;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public MessageEmbed getEmbed() {
        String fName="[getEmbed]";
        try {
            logger.info(fName + ".value="+this.embed.toString());
            return this.embed;
        }catch (Exception e){
            return null;
        }
    }

    public lcMyMessageBuilder setNonce(@Nullable String nonce) {
        String fName="[setNonce]";
        try {
            logger.info(fName + ".value="+nonce);
            this.nonce = nonce;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public String getNonce() {
        String fName="[getNonce]";
        try {
            logger.info(fName + ".value="+this.nonce);
            return this.nonce;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder setContent(@Nullable String content) {
        String fName="[setContent]";
        try {
            logger.info(fName + ".value="+content);
            if (content == null) {
                this.builder.setLength(0);
            } else {
                int newLength = Math.max(this.builder.length(), content.length());
                this.builder.replace(0, newLength, content);
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public String getContent() {
        String fName="[getContent]";
        try {
            logger.info(fName + ".value="+this.builder.toString());
            return this.builder.toString();
        }catch (Exception e){
            return null;
        }
    }
    
    public lcMyMessageBuilder append(@Nullable CharSequence text) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+text);
            this.builder.append(text);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder append(@Nullable CharSequence text, int start, int end) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+text+", start="+start+", end="+end);
            this.builder.append(text, start, end);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder append(char c) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+c);
            this.builder.append(c);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder append(@Nullable Object object) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+String.valueOf(object));
            return this.append((CharSequence)String.valueOf(object));
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder append(@Nonnull IMentionable mention) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+mention.getAsMention());
            Checks.notNull(mention, "Mentionable");
            this.builder.append(mention.getAsMention());
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder append(@Nullable CharSequence text, @Nonnull lcMyMessageBuilder.Formatting... format) {
        String fName="[append]";
        try {
            logger.info(fName + ".value="+text+" format"+format.length);
            boolean blockPresent = false;
            lcMyMessageBuilder.Formatting[] var4 = format;
            int var5 = format.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                lcMyMessageBuilder.Formatting formatting = var4[var6];
                if (formatting == lcMyMessageBuilder.Formatting.BLOCK) {
                    blockPresent = true;
                } else {
                    this.builder.append(formatting.getTag());
                }
            }

            if (blockPresent) {
                this.builder.append(lcMyMessageBuilder.Formatting.BLOCK.getTag());
            }

            this.builder.append(text);
            if (blockPresent) {
                this.builder.append(lcMyMessageBuilder.Formatting.BLOCK.getTag());
            }

            for(int i = format.length - 1; i >= 0; --i) {
                if (format[i] != lcMyMessageBuilder.Formatting.BLOCK) {
                    this.builder.append(format[i].getTag());
                }
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder appendFormat(@Nonnull String format, @Nonnull Object... args) {
        String fName="[appendFormat]";
        try {
            logger.info(fName + ".value="+format+", args="+args.length);
            Checks.notEmpty(format, "Format String");
            this.append((CharSequence)String.format(format, args));
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder appendCodeLine(@Nullable CharSequence text) {
        String fName="[appendCodLine]";
        try {
            logger.info(fName + ".value="+text);
            this.append(text, lcMyMessageBuilder.Formatting.BLOCK);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder appendCodeBlock(@Nullable CharSequence text, @Nullable CharSequence language) {
        String fName="[appendCodeBlock]";
        try {
            logger.info(fName + ".value="+text+", language="+language);
            this.builder.append("```").append(language).append('\n').append(text).append("\n```");
            return this;
        }catch (Exception e){
            return null;
        }
    }

    public int length() {
        String fName="[lenght]";
        try {
            logger.info(fName + ".value="+this.builder.length());
            return this.builder.length();
        }catch (Exception e){
            return 0;
        }
    }
    public boolean isEmpty() {
        String fName="[isEmpty]";
        try {
            boolean result=this.builder.length() == 0 && this.embed == null;
            logger.info(fName + ".value="+result);
            return result;
        }catch (Exception e){
            return true;
        }
    }
    
    public lcMyMessageBuilder replace(@Nonnull String target, @Nonnull String replacement) {
        String fName="[replace]";
        try {
            logger.info(fName + ".target="+target+", replacement="+replacement);
            for(int index = this.builder.indexOf(target); index != -1; index = this.builder.indexOf(target, index + replacement.length())) {
                this.builder.replace(index, index + target.length(), replacement);
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder replaceFirst(@Nonnull String target, @Nonnull String replacement) {
        String fName="[replaceFirst]";
        try {
            logger.info(fName + ".target="+target+", replacement="+replacement);
            int index = this.builder.indexOf(target);
            if (index != -1) {
                this.builder.replace(index, index + target.length(), replacement);
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder replaceLast(@Nonnull String target, @Nonnull String replacement) {
        String fName="[replaceLast]";
        try {
            logger.info(fName + ".target="+target+", replacement="+replacement);
            int index = this.builder.lastIndexOf(target);
            if (index != -1) {
                this.builder.replace(index, index + target.length(), replacement);
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }

    
    public lcMyMessageBuilder clearMentionedUsers() {
        String fName="[clearMentionedUsers]";
        try {
            logger.info(fName + ".");
            this.mentionedUsers.clear();
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder clearMentionedRoles() {
        String fName="[clearMentionedRoles]";
        try {
            logger.info(fName + ".");
            this.mentionedRoles.clear();
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder clearMentions() {
        String fName="[clearMentions]";
        try {
            logger.info(fName + ".");
            return this.clearMentionedUsers().clearMentionedRoles();
        }catch (Exception e){
            return null;
        }
    }

    public lcMyMessageBuilder setAllowedMentions(@Nullable Collection<Message.MentionType> mentionTypes) {
        String fName="[setAllowedMentions]";
        try {
            logger.info(fName + ".size="+mentionTypes.size());
            this.allowedMentions = mentionTypes == null ? MessageAction.getDefaultMentions() : Helpers.copyEnumSet(Message.MentionType.class, mentionTypes);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder allowMentions(@Nonnull Message.MentionType... types) {
        String fName="[allowedMentions]";
        try {
            logger.info(fName + ".size="+types.length);
            Checks.noneNull(types, "MentionTypes");
            if (types.length > 0) {
                if (this.allowedMentions == null) {
                    this.allowedMentions = MessageAction.getDefaultMentions();
                }

                Collections.addAll(this.allowedMentions, types);
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder denyMentions(@Nonnull Message.MentionType... types) {
        String fName="[denyMentions]";
        try {
            logger.info(fName + ".size="+types.length);
            Checks.noneNull(types, "MentionTypes");
            if (types.length > 0) {
                if (this.allowedMentions == null) {
                    this.allowedMentions = MessageAction.getDefaultMentions();
                }

                Message.MentionType[] var2 = types;
                int var3 = types.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Message.MentionType type = var2[var4];
                    this.allowedMentions.remove(type);
                }
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public EnumSet<Message.MentionType> getMentionTypes() {
        String fName="[getMentionTypes]";
        try {
            logger.info(fName + ".size="+allowedMentions.size());
            return allowedMentions;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mention(@Nonnull IMentionable... mentions) {
        String fName="[mention]";
        try {
            logger.info(fName + ".size="+mentions.length);
            Checks.noneNull(mentions, "Mentions");
            IMentionable[] var2 = mentions;
            int var3 = mentions.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                IMentionable mention = var2[var4];
                if (!(mention instanceof User) && !(mention instanceof Member)) {
                    if (mention instanceof Role) {
                        this.mentionedRoles.add(mention.getId());
                    }
                } else {
                    this.mentionedUsers.add(mention.getId());
                }
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mention(@Nonnull Collection<? extends IMentionable> mentions) {
        String fName="[mentions]";
        try {
            logger.info(fName + ".size="+mentions.size());
            Checks.noneNull(mentions, "Mentions");
            return this.mention((IMentionable[])mentions.toArray(new IMentionable[0]));
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mentionUsers(@Nonnull String... users) {
        String fName="[mentionUsers]";
        try {
            logger.info(fName + ".size="+users.length);
            Checks.noneNull(users, "Users");
            Collections.addAll(this.mentionedUsers, users);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mentionRoles(@Nonnull String... roles) {
        String fName="[mentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length);
            Checks.noneNull(roles, "Roles");
            Collections.addAll(this.mentionedRoles, roles);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mentionUsers(@Nonnull long... users) {
        String fName="[mentionUsers]";
        try {
            logger.info(fName + ".size="+users.length);
            Checks.notNull(users, "Users");
            return this.mentionUsers(this.toStringArray(users));
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageBuilder mentionRoles(@Nonnull long... roles) {
        String fName="[mentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length);
            Checks.notNull(roles, "Roles");
            return this.mentionRoles(this.toStringArray(roles));
        }catch (Exception e){
            return null;
        }
    }
    public Set<String> getMentionUsers() {
        String fName="[getMentionUsers]";
        try {
            logger.info(fName + ".size="+mentionedUsers.size());
            return mentionedUsers;
        }catch (Exception e){
            return null;
        }
    }
    public Set<String> getMentionRoles() {
        String fName="[getMentionRoles]";
        try {
            logger.info(fName + ".size="+mentionedRoles.size());
            return mentionedRoles;
        }catch (Exception e){
            return null;
        }
    }

    /** @deprecated */
    @Deprecated
    @ForRemoval
    @ReplaceWith("setAllowedMentions(Collections.emptyList())")
    @DeprecatedSince("4.2.0")
    public lcMyMessageBuilder stripMentions(@Nonnull JDA jda) {
        String fName="[stripMentions]";
        try {
            logger.info(fName + ".jda="+jda.getShardInfo().getShardId());
            return this.stripMentions(jda, (Guild)null, Message.MentionType.values());
        }catch (Exception e){
            return null;
        }
    }
    /** @deprecated */
    @Deprecated
    @ForRemoval
    @ReplaceWith("setAllowedMentions(Collections.emptyList())")
    @DeprecatedSince("4.2.0")
    public lcMyMessageBuilder stripMentions(@Nonnull Guild guild) {
        String fName="[stripMentions]";
        try {
            logger.info(fName + ".guild="+guild.getId());
            return this.stripMentions(guild.getJDA(), guild, Message.MentionType.values());
        }catch (Exception e){
            return null;
        }
    }
    /** @deprecated */
    @Deprecated
    @ForRemoval
    @ReplaceWith("denyMentions(types)")
    @DeprecatedSince("4.2.0")
    public lcMyMessageBuilder stripMentions(@Nonnull Guild guild, @Nonnull Message.MentionType... types) {
        String fName="[stripMentions]";
        try {
            logger.info(fName + ".guild="+guild.getId()+", types.size="+types.length);
            return this.stripMentions(guild.getJDA(), guild, types);
        }catch (Exception e){
            return null;
        }
    }
    /** @deprecated */
    @Deprecated
    @ForRemoval
    @ReplaceWith("denyMentions(types)")
    @DeprecatedSince("4.2.0")
    public lcMyMessageBuilder stripMentions(@Nonnull JDA jda, @Nonnull Message.MentionType... types) {
        String fName="[stripMentions]";
        try {
            logger.info(fName + ".jda="+jda.getShardInfo().getShardId()+", types.size="+types.length);
            return this.stripMentions(jda, (Guild)null, types);
        }catch (Exception e){
            return null;
        }
    }
    private lcMyMessageBuilder stripMentions(JDA jda, Guild guild, Message.MentionType... types) {
        String fName="[stripMentions]";
        try {
            logger.info(fName + ".");
            if (types == null) {
                return this;
            } else {
                String string = null;
                Message.MentionType[] var5 = types;
                int var6 = types.length;

                label89:
                for(int var7 = 0; var7 < var6; ++var7) {
                    Message.MentionType mention = var5[var7];
                    if (mention != null) {
                        Matcher matcher;
                        switch(mention) {
                            case EVERYONE:
                                this.replace("@everyone", "@еveryone");
                                break;
                            case HERE:
                                this.replace("@here", "@hеre");
                                break;
                            case CHANNEL:
                                if (string == null) {
                                    string = this.builder.toString();
                                }

                                matcher = Message.MentionType.CHANNEL.getPattern().matcher(string);

                                while(true) {
                                    if (!matcher.find()) {
                                        continue label89;
                                    }

                                    TextChannel channel = jda.getTextChannelById(matcher.group(1));
                                    if (channel != null) {
                                        this.replace(matcher.group(), "#" + channel.getName());
                                    }
                                }
                            case ROLE:
                                if (string == null) {
                                    string = this.builder.toString();
                                }

                                matcher = Message.MentionType.ROLE.getPattern().matcher(string);

                                while(true) {
                                    while(true) {
                                        if (!matcher.find()) {
                                            continue label89;
                                        }

                                        Iterator var13 = jda.getGuilds().iterator();

                                        while(var13.hasNext()) {
                                            Guild g = (Guild)var13.next();
                                            Role role = g.getRoleById(matcher.group(1));
                                            if (role != null) {
                                                this.replace(matcher.group(), "@" + role.getName());
                                                break;
                                            }
                                        }
                                    }
                                }
                            case USER:
                                if (string == null) {
                                    string = this.builder.toString();
                                }

                                matcher = Message.MentionType.USER.getPattern().matcher(string);

                                while(true) {
                                    User user;
                                    do {
                                        if (!matcher.find()) {
                                            continue label89;
                                        }

                                        user = jda.getUserById(matcher.group(1));
                                    } while(user == null);

                                    String replacement;
                                    Member member;
                                    if (guild != null && (member = guild.getMember(user)) != null) {
                                        replacement = member.getEffectiveName();
                                    } else {
                                        replacement = user.getName();
                                    }

                                    this.replace(matcher.group(), "@" + replacement);
                                }
                        }
                    }
                }

                return this;
            }
        }catch (Exception e){
            return null;
        }

    }
    public StringBuilder getStringBuilder() {
        String fName="[getStringBuilder]";
        try {
            logger.info(fName + ".value="+this.builder.length());
            return this.builder;
        }catch (Exception e){
            return null;
        }
    }
    public int indexOf(@Nonnull CharSequence target, int fromIndex, int endIndex) {
        String fName="[indexOf]";
        try {
            logger.info(fName + ".target="+target+", from="+fromIndex+", end="+endIndex);
            if (fromIndex < 0) {
                throw new IndexOutOfBoundsException("index out of range: " + fromIndex);
            } else if (endIndex < 0) {
                throw new IndexOutOfBoundsException("index out of range: " + endIndex);
            } else if (fromIndex > this.length()) {
                throw new IndexOutOfBoundsException("fromIndex > length()");
            } else if (fromIndex > endIndex) {
                throw new IndexOutOfBoundsException("fromIndex > endIndex");
            } else {
                if (endIndex >= this.builder.length()) {
                    endIndex = this.builder.length() - 1;
                }

                int targetCount = target.length();
                if (targetCount == 0) {
                    return fromIndex;
                } else {
                    char strFirstChar = target.charAt(0);
                    int max = endIndex + targetCount - 1;

                    label51:
                    for(int i = fromIndex; i <= max; ++i) {
                        if (this.builder.charAt(i) == strFirstChar) {
                            for(int j = 1; j < targetCount; ++j) {
                                if (this.builder.charAt(i + j) != target.charAt(j)) {
                                    continue label51;
                                }
                            }

                            return i;
                        }
                    }

                    return -1;
                }
            }
        }catch (Exception e){
            return -1;
        }
    }
    public int lastIndexOf(@Nonnull CharSequence target, int fromIndex, int endIndex) {
        String fName="[lastIndexOf]";
        try {
            logger.info(fName + ".target="+target+", from="+fromIndex+", end="+endIndex);
            if (fromIndex < 0) {
                throw new IndexOutOfBoundsException("index out of range: " + fromIndex);
            } else if (endIndex < 0) {
                throw new IndexOutOfBoundsException("index out of range: " + endIndex);
            } else if (fromIndex > this.length()) {
                throw new IndexOutOfBoundsException("fromIndex > length()");
            } else if (fromIndex > endIndex) {
                throw new IndexOutOfBoundsException("fromIndex > endIndex");
            } else {
                if (endIndex >= this.builder.length()) {
                    endIndex = this.builder.length() - 1;
                }

                int targetCount = target.length();
                if (targetCount == 0) {
                    return endIndex;
                } else {
                    int rightIndex = endIndex - targetCount;
                    if (fromIndex > rightIndex) {
                        fromIndex = rightIndex;
                    }

                    int strLastIndex = targetCount - 1;
                    char strLastChar = target.charAt(strLastIndex);
                    int min = fromIndex + targetCount - 1;

                    label55:
                    for(int i = endIndex; i >= min; --i) {
                        if (this.builder.charAt(i) == strLastChar) {
                            int j = strLastIndex - 1;

                            for(int k = 1; j >= 0; ++k) {
                                if (this.builder.charAt(i - k) != target.charAt(j)) {
                                    continue label55;
                                }

                                --j;
                            }

                            return i - target.length() + 1;
                        }
                    }

                    return -1;
                }
            }
        }catch (Exception e){
            return -1;
        }
    }
    private String[] toStringArray(long[] users) {
        String[] ids = new String[users.length];

        for(int i = 0; i < ids.length; ++i) {
            ids[i] = Long.toUnsignedString(users[i]);
        }

        return ids;
    }
    public static enum Formatting {
        ITALICS("*"),
        BOLD("**"),
        STRIKETHROUGH("~~"),
        UNDERLINE("__"),
        BLOCK("`");

        private final String tag;

        private Formatting(String tag) {
            this.tag = tag;
        }

        @Nonnull
        private String getTag() {
            return this.tag;
        }
    }


    

}
