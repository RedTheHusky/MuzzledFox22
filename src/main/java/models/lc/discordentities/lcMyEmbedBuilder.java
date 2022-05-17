package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.internal.entities.EntityBuilder;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.internal.utils.Helpers;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class lcMyEmbedBuilder  {
    //https://discord.com/developers/docs/interactions/slash-commands#responding-to-an-interaction
    Logger logger = Logger.getLogger(getClass());
    public static final String ZERO_WIDTH_SPACE = "\u200e";
    public static final Pattern URL_PATTERN = Pattern.compile("\\s*(https?|attachment)://\\S+\\s*", 2);
    private List<MessageEmbed.Field> fields = new LinkedList();
    private StringBuilder description = new StringBuilder();
    private int color = 0;//536870911;
    private String url=null;
    private String title=null;
    private OffsetDateTime timestamp=null;
    private MessageEmbed.Thumbnail thumbnail=null;
    private MessageEmbed.AuthorInfo author=null;
    private MessageEmbed.Footer footer=null;
    private MessageEmbed.ImageInfo image=null;
    public lcMyEmbedBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyEmbedBuilder(@Nullable EmbedBuilder builder){
        String fName="[create]";
        try {
            set(builder.build());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyEmbedBuilder(@Nullable MessageEmbed embed){
        String fName="[create]";
        try {
           set(embed);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyEmbedBuilder(@Nullable JSONObject jsonObject){
        String fName="[create]";
        try {
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean set(@Nullable MessageEmbed embed){
        String fName="[set]";
        try {
            if (embed != null) {
                this.setDescription(embed.getDescription());
                this.url = embed.getUrl(); if(url!=null)logger.error(fName + ".url=" + url);
                this.title = embed.getTitle();if(title!=null)logger.error(fName + ".title=" + title);
                this.timestamp = embed.getTimestamp();if(timestamp!=null)logger.error(fName + ".timestamp.toString()=" + timestamp.toString());
                this.color = embed.getColorRaw();if(color!=0)logger.error(fName + ".color.getColorRaw=" + color+", color.getRGB="+embed.getColor().getRGB());
                this.thumbnail = embed.getThumbnail();if(thumbnail!=null)logger.error(fName + ".thumbnail.toString()=" + thumbnail.toString());
                this.author = embed.getAuthor();if(author!=null)logger.error(fName + ".author.toString()=" + author.toString());
                this.footer = embed.getFooter();if(footer!=null)logger.error(fName + ".footer.toString()=" + footer.toString());
                this.image = embed.getImage();if(image!=null)logger.error(fName + ".image.toString()=" + image.toString());
                if (embed.getFields() != null) {
                    this.fields.addAll(embed.getFields());
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean set(@Nullable JSONObject jsonObject){
        String fName="[set]";
        try {
            if (jsonObject != null) {
                logger.error(fName + ".json=" + jsonObject.toString());
                setDescription(jsonObject.optString(keyDescription));
                setTitle(jsonObject.optString(keyTitle),jsonObject.optString(keyUrl));
                setColor(jsonObject.optInt(keyColor));
                setThumbnail(jsonObject.optJSONObject(keyThumbnail));
                setAuthor(jsonObject.optJSONObject(keyAuthor));
                setFooter(jsonObject.optJSONObject(keyFooter));
                setImage(jsonObject.optJSONObject(keyImage));
                setFields(jsonObject.optJSONArray(keyFields));
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public MessageEmbed build() {
        String fName="[build]";
        try {
            if (this.isEmpty()) {
                throw new IllegalStateException("Cannot build an empty embed!");
            } else if (this.description.length() > 2048) {
                throw new IllegalStateException(String.format("Description is longer than %d! Please limit your input!", 2048));
            } else if (this.length() > 6000) {
                throw new IllegalStateException("Cannot build an embed with more than 6000 characters!");
            } else {
                String description = this.description.length() < 1 ? null : this.description.toString();
                return EntityBuilder.createMessageEmbed(this.url, this.title, description, EmbedType.RICH, this.timestamp, this.color, this.thumbnail, (MessageEmbed.Provider)null, this.author, (MessageEmbed.VideoInfo)null, this.footer, this.image, new LinkedList(this.fields));
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public EmbedBuilder embed() {
        String fName="[embed]";
        try {
            EmbedBuilder embedBuilder=new EmbedBuilder();
            if(description.length()>0)embedBuilder.setDescription(description);
            for(MessageEmbed.Field field:fields){
                embedBuilder.addField(field);
            }
            if(title!=null&&url!=null){
                embedBuilder.setTitle(title,url);
            }
            else if(title!=null){
                embedBuilder.setTitle(title);
            }
            if(timestamp!=null){
                embedBuilder.setTimestamp(timestamp);
            }
            embedBuilder.setColor(color);
            if(thumbnail!=null){
                embedBuilder.setThumbnail(thumbnail.getUrl());
            }
            if(author!=null){
                if(author.getName()!=null&&author.getUrl()!=null&&author.getIconUrl()!=null){
                    embedBuilder.setAuthor(author.getName(),author.getUrl(),author.getIconUrl());
                }
                else if(author.getName()!=null&&author.getUrl()!=null){
                    embedBuilder.setAuthor(author.getName(),author.getUrl());
                }
                else if(author.getName()!=null){
                    embedBuilder.setAuthor(author.getName());
                }

            }
            if(footer!=null){
                if(footer.getText()!=null&&footer.getIconUrl()!=null){
                    embedBuilder.setFooter(footer.getText(),footer.getIconUrl());
                }
                else if(footer.getText()!=null){
                    embedBuilder.setFooter(footer.getText());
                }
            }
            if(image!=null){
              embedBuilder.setImage(image.getUrl());
            }
            return embedBuilder;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  static  final String keyDescription="description",keyFields="fields",keyTitle="title",keyUrl="url",keyTimestamp="timestamp",
    keyColor="color",keyThumbnail="thumbnail",keyAuthor="author",keyFooter="footer",keyImage="image";
    public JSONObject getJSON() {
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(description.length()>0)jsonObject.put(keyDescription,description.toString());
            if(!fields.isEmpty()) jsonObject.put(keyFields,getFieldsAsJson());
            if(title!=null) jsonObject.put(keyTitle,title);
            if(url!=null) jsonObject.put(keyUrl,url);
            if(timestamp!=null){
                jsonObject.put(keyTimestamp,getTimestampAsString());
            }
            jsonObject.put(keyColor,color);
            if(thumbnail!=null){
                jsonObject.put(keyThumbnail,getThumbnailAsJson());
            }
            if(author!=null){
                jsonObject.put(keyAuthor,getAuthorAsJson());
            }
            if(footer!=null){
                jsonObject.put(keyFooter,getFooterAsJson());
            }
            if(image!=null){
                jsonObject.put(keyImage,getImageAsJson());
            }
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public lcMyEmbedBuilder setJSON(JSONObject jsonObject) {
        String fName="[setJSON]";
        try {
            clear();
            if(jsonObject==null){
                logger.info(fName + ".jsonObject is null>return");
                return this;
            }
            logger.info(fName + ".jsonObject=" + jsonObject.toString());

            if(jsonObject.has(keyDescription))setDescription(jsonObject.getString(keyDescription));
            if(jsonObject.has(keyTitle)){
                if(jsonObject.has(keyUrl))setTitle(jsonObject.getString(keyTitle),jsonObject.getString(keyUrl));
                        else setTitle(jsonObject.getString(keyTitle));
            }
            if(jsonObject.has(keyFields))setFields(jsonObject.getJSONArray(keyDescription));
            if(jsonObject.has(keyImage))setImage(jsonObject.getJSONObject(keyImage));
            if(jsonObject.has(keyThumbnail))setThumbnail(jsonObject.getJSONObject(keyThumbnail));
            if(jsonObject.has(keyColor))setColor(jsonObject.getInt(keyColor));
            if(jsonObject.has(keyFooter))setFooter(jsonObject.getJSONObject(keyFooter));
            if(jsonObject.has(keyAuthor))setFooter(jsonObject.getJSONObject(keyAuthor));
            if(jsonObject.has(keyTimestamp))logger.warn(fName + ".timestamp is not implemented");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder clear() {
        String fName="[clear]";
        try {
            this.description.setLength(0);
            this.fields.clear();
            this.url = null;
            this.title = null;
            this.timestamp = null;
            this.color = 536870911;
            this.thumbnail = null;
            this.author = null;
            this.footer = null;
            this.image = null;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return this;
        }
    }
    public boolean isEmpty() {
        String fName="[isEmpty]";
        try {
            return this.title == null && this.timestamp == null && this.thumbnail == null && this.author == null && this.footer == null && this.image == null && this.description.length() == 0 && this.fields.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public int length() {
        String fName="[length]";
        try {
            int length = this.description.length();
            synchronized(this.fields) {
                length = (Integer)this.fields.stream().map((f) -> {
                    return f.getName().length() + f.getValue().length();
                }).reduce(length, Integer::sum);
            }

            if (this.title != null) {
                length += this.title.length();
            }

            if (this.author != null) {
                length += this.author.getName().length();
            }

            if (this.footer != null) {
                length += this.footer.getText().length();
            }

            return length;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isValidLength() {
        String fName="[isValidLength]";
        try {
            int length = this.length();
            return length <= 6000;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isValidLength(@Nonnull AccountType type) {
        String fName="[isValidLength]";
        try {
            Checks.notNull(type, "AccountType");
            int length = this.length();
            switch(type) {
                case BOT:
                    return length <= 6000;
                // case CLIENT: removed since JDA.version 4.3.0+
                default:
                    return length <= 2000;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public lcMyEmbedBuilder setTitle(@Nullable String title) {
        return this.setTitle(title, (String)null);
    }
    public lcMyEmbedBuilder setTitle(@Nullable String title, @Nullable String url) {
        String fName="[setTitle]";
        try {
            if (title == null) {
                this.title = null;
                this.url = null;
            } else {
                Checks.notEmpty(title, "Title");
                Checks.check(title.length() <= 256, "Title cannot be longer than %d characters.", 256);
                if (Helpers.isBlank(url)) {
                    url = null;
                }

                this.urlCheck(url);
                this.title = title;
                this.url = url;
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getTitleAsJson() {
        String fName="[getTitleAsJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(title!=null)jsonObject.put("title",title);
            if(url!=null)jsonObject.put("url",url);
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getTitleTitle() {
        String fName="[getTitleTitle]";
        try {
            return title;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getTitleUrl() {
        String fName="[getTitleUrl]";
        try {
            return url;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    public StringBuilder getDescriptionBuilder() {
        return this.description;
    }
    public final lcMyEmbedBuilder setDescription(@Nullable CharSequence description) {
        String fName="[setDescription]";
        try {
            this.description.setLength(0);
            if (description != null && description.length() >= 1) {
                this.appendDescription(description);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder appendDescription(@Nonnull CharSequence description) {
        String fName="[appendDescription]";
        try {
            Checks.notNull(description, "description");
            Checks.check(this.description.length() + description.length() <= 2048, "Description cannot be longer than %d characters.", 2048);
            this.description.append(description);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setTimestamp(@Nullable TemporalAccessor temporal) {
        String fName="[setTimestamp]";
        try {
            if (temporal == null) {
                this.timestamp = null;
            } else if (temporal instanceof OffsetDateTime) {
                this.timestamp = (OffsetDateTime)temporal;
            } else {
                ZoneOffset offset;
                try {
                    offset = ZoneOffset.from(temporal);
                } catch (DateTimeException var7) {
                    offset = ZoneOffset.UTC;
                }

                try {
                    LocalDateTime ldt = LocalDateTime.from(temporal);
                    this.timestamp = OffsetDateTime.of(ldt, offset);
                } catch (DateTimeException var6) {
                    try {
                        Instant instant = Instant.from(temporal);
                        this.timestamp = OffsetDateTime.ofInstant(instant, offset);
                    } catch (DateTimeException var5) {
                        throw new DateTimeException("Unable to obtain OffsetDateTime from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), var5);
                    }
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TemporalAccessor getTimestamp() {
        String fName="[getTimestamp]";
        try {
            return timestamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getTimestampAsJson() {
        String fName="[getTimestampAsJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            int year=timestamp.getYear();
            int month=timestamp.getMonthValue()+1;
            int day=timestamp.getDayOfMonth();
            int hour=timestamp.getHour();
            int minute=timestamp.getMinute();
            int second=timestamp.getSecond();
            String offsetId=timestamp.getOffset().getId();
            jsonObject.put("year",year);jsonObject.put("month",month);jsonObject.put("day",day);
            jsonObject.put("hour",hour);jsonObject.put("minute",minute);jsonObject.put("second",second);
            jsonObject.put("offset",offsetId);
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getTimestampAsString() {
        String fName="[getTimestampAsJson]";
        try {
            StringBuilder str=new StringBuilder("");
            int year=timestamp.getYear();
            int month=timestamp.getMonthValue()+1;
            int day=timestamp.getDayOfMonth();
            int hour=timestamp.getHour();
            int minute=timestamp.getMinute();
            int second=timestamp.getSecond();
            String offsetId=timestamp.getOffset().getId();
            str.append(year).append("-").append(month).append("-").append(day);
            str.append("T");
            str.append(hour).append(":").append(minute).append(":").append(second);
            str.append(".");str.append(offsetId);
            logger.info(fName + ".str=" + str.toString());
            return str.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setColor(@Nullable Color color) {
        String fName="[setColor]";
        try {
            this.color = color == null ? 536870911 : color.getRGB();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return this;
        }
    }
    public lcMyEmbedBuilder setColor(int color) {
        String fName="[setColor]";
        try {
            this.color = color;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getColorAsInt() {
        String fName="[getColorAsInt]";
        try {
            return color;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Color getColorAsColor() {
        String fName="[getColorAsColor]";
        try {
            return new Color(color);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setThumbnail(@Nullable String url) {
        String fName="[setThumbnail]";
        try {
            if (url == null) {
                this.thumbnail = null;
            } else {
                this.urlCheck(url);
                this.thumbnail = new MessageEmbed.Thumbnail(url, (String)null, 0, 0);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setThumbnail(@Nullable JSONObject jsonObject) {
        String fName="[setThumbnail]";
        try {
            thumbnail=null;
            if(jsonObject==null){
                logger.info(fName + ".jsonObject is null>return");
                return this;
            }
            String url=null;
            if(jsonObject.has("url")){
                url=jsonObject.getString("url");
            }
            return setThumbnail(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed.Thumbnail getThumbnail() {
        String fName="[getThumbnail]";
        try {
            return thumbnail;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getThumbnailAsJson() {
        String fName="[getThumbnailAsJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(thumbnail.getUrl()!=null)jsonObject.put("url",thumbnail.getUrl());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getThumbnailAsString() {
        String fName="[getThumbnailAsString]";
        try {
            return thumbnail.getUrl();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setImage(@Nullable String url) {
        String fName="[setImage]";
        try {
            if (url == null) {
                this.image = null;
            } else {
                this.urlCheck(url);
                this.image = new MessageEmbed.ImageInfo(url, (String)null, 0, 0);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setImage(@Nullable JSONObject jsonObject) {
        String fName="[setImage]";
        try {
            image=null;
            if(jsonObject==null){
                logger.info(fName + ".jsonObject is null>return");
                return this;
            }
            String url=null;
            if(jsonObject.has("url")){
                url=jsonObject.getString("url");
            }
            return setImage(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed.ImageInfo getImage() {
        String fName="[getImage]";
        try {
            return image;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getImageAsJson() {
        String fName="[getImageAsJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(image.getUrl()!=null)jsonObject.put("url",image.getUrl());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getImageAsString() {
        String fName="[getImageAsString]";
        try {
            return image.getUrl();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setAuthor(@Nullable String name) {
        return this.setAuthor(name, (String)null, (String)null);
    }
    public lcMyEmbedBuilder setAuthor(@Nullable String name, @Nullable String url) {
        return this.setAuthor(name, url, (String)null);
    }
    public lcMyEmbedBuilder setAuthor(@Nullable String name, @Nullable String url, @Nullable String iconUrl) {
        String fName="[setAuthor]";
        try {
            if (name == null) {
                this.author = null;
            } else {
                Checks.check(name.length() <= 256, "Name cannot be longer than %d characters.", 256);
                this.urlCheck(url);
                this.urlCheck(iconUrl);
                this.author = new MessageEmbed.AuthorInfo(name, url, iconUrl, (String)null);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setAuthor(@Nullable JSONObject jsonObject) {
        String fName="[setAuthor]";
        try {
            author=null;
            if(jsonObject==null){
                logger.info(fName + ".jsonObject is null>return");
                return this;
            }
            String name=null,url=null, iconUrl=null;
            if(jsonObject.has("name"))name=jsonObject.getString("name");
            if(jsonObject.has("url"))name=jsonObject.getString("url");
            if(jsonObject.has("iconUrl"))name=jsonObject.getString("iconUrl");
            return this.setAuthor(name, url, iconUrl);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  MessageEmbed.AuthorInfo getAuthor() {
        String fName="[getAuthor]";
        try {
            return author;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  JSONObject getAuthorAsJson() {
        String fName="[getAuthorAsJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(author.getName()!=null)jsonObject.put("name",author.getName());
            if(author.getUrl()!=null)jsonObject.put("url",author.getUrl());
            if(author.getIconUrl()!=null)jsonObject.put("iconUrl",author.getIconUrl());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public  String getAuthorName() {
        String fName="[getAuthorName]";
        try {
            return author.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  String getAuthorUrl() {
        String fName="[getAuthorUrl]";
        try {
            return author.getUrl();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  String getAuthorIconUrl() {
        String fName="[getAuthorIconUrl]";
        try {
            return author.getIconUrl();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder setFooter(@Nullable String text) {
        return this.setFooter(text, (String)null);
    }
    public lcMyEmbedBuilder setFooter(@Nullable String text, @Nullable String iconUrl) {
        String fName="[setFooter]";
        try {
            if (text == null) {
                this.footer = null;
            } else {
                Checks.check(text.length() <= 2048, "Text cannot be longer than %d characters.", 2048);
                this.urlCheck(iconUrl);
                this.footer = new MessageEmbed.Footer(text, iconUrl, (String)null);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setFooter(@Nullable JSONObject jsonObject) {
        String fName="[setFooter]";
        try {
            footer=null;
            if(jsonObject==null){
                logger.info(fName + ".jsonObject is null>return");
                return this;
            }
            String text=null,iconUrl=null;
            if(jsonObject.has("text"))text=jsonObject.getString("text");
            if(jsonObject.has("iconUrl"))iconUrl=jsonObject.getString("iconUrl");
            return setFooter(text,iconUrl);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed.Footer getFooter() {
        String fName="[getFooter]";
        try {
            return footer;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getFooterAsJson() {
        String fName="[getFooter]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(footer.getText()!=null)jsonObject.put("text",footer.getText());
            if(footer.getIconUrl()!=null)jsonObject.put("iconUrl",footer.getIconUrl());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getFooterText() {
        String fName="[getFooterText]";
        try {
            return footer.getText();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getFooterIconUrl() {
        String fName="[getFooterIconUrl]";
        try {
            return footer.getIconUrl();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcMyEmbedBuilder addField(@Nullable MessageEmbed.Field field) {
        String fName="[addField]";
        try {
            return field == null ? this : this.addField(field.getName(), field.getValue(), field.isInline());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder addField(@Nullable String name, @Nullable String value, boolean inline) {
        String fName="[addField]";
        try {
            if (name == null && value == null) {
                return this;
            } else {
                this.fields.add(new MessageEmbed.Field(name, value, inline));
                return this;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setField(int index,@Nullable MessageEmbed.Field field) {
        String fName="[setField]";
        try {
            return field == null ? this : this.setField(index,field.getName(), field.getValue(), field.isInline());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setField(int index,@Nullable String name, @Nullable String value, boolean inline) {
        String fName="[setField]";
        try {
            if (name == null && value == null) {
                return this;
            } else {
                this.fields.set(index,new MessageEmbed.Field(name, value, inline));
                return this;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setFields(@Nullable JSONArray jsonArray) {
        String fName="[setFields]";
        try {
            this.fields=new ArrayList<>();
            if(jsonArray==null){
                logger.info(fName + ".jsonArray is null>return");
                return this;
            }
            for(int i=0;i<jsonArray.length();i++){
               String name=null,value=null;boolean inline=false;
               JSONObject jsonObject=jsonArray.getJSONObject(i);
               if(jsonObject.has("name"))name=jsonObject.getString("name");
               if(jsonObject.has("value"))value=jsonObject.getString("value");
               if(jsonObject.has("inline"))inline=jsonObject.getBoolean("inline");
               if(addField(name,value,inline)==null){
                   return null;
               }
            }
            return  this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder addBlankField(boolean inline) {
        String fName="[addBlankField]";
        try {
            this.fields.add(new MessageEmbed.Field("\u200e", "\u200e", inline));
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setBlankField(int index,boolean inline) {
        String fName="[setBlankField]";
        try {
            this.fields.set(index,new MessageEmbed.Field("\u200e", "\u200e", inline));
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageEmbed.Field remField(int index) {
        String fName="[remField]";
        try {
            return this.fields.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder clearFields() {
        String fName="[clearFields]";
        try {
            this.fields.clear();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMyEmbedBuilder setFieldInline(int index, boolean inline) {
        String fName="[setFieldInline]";
        try {
            MessageEmbed.Field fieldOld=fields.get(index);
            MessageEmbed.Field fieldNew=new MessageEmbed.Field(fieldOld.getName(),fieldOld.getValue(),inline);
            fields.set(index,fieldNew);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<MessageEmbed.Field> getFields() {
        return this.fields;
    }
    public JSONArray getFieldsAsJson(){
        String fName="[getFieldsAsJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<fields.size();i++){
                JSONObject jsonField=getFieldAsJson(i);
                jsonArray.put(jsonField);
            }
            return  jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public MessageEmbed.Field getField(int index) {
        String fName="[getField]";
        try {
            return fields.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getFieldAsJson(int index) {
        String fName="[getFieldAsJson]";
        try {
            MessageEmbed.Field field=getField(index);
            JSONObject jsonObject=new JSONObject();
            if(field.getName()!=null)jsonObject.put("name",field.getName());
            if(field.getValue()!=null)jsonObject.put("value",field.getValue());
            jsonObject.put("inline",field.isInline());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getFieldName(int index) {
        String fName="[getFieldName]";
        try {
            MessageEmbed.Field field=getField(index);
            return field.getName();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getFieldValue(int index) {
        String fName="[getFieldValue]";
        try {
            MessageEmbed.Field field=getField(index);
            return field.getValue();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean getFieldInline(int index) {
        String fName="[getFieldInline]";
        try {
            MessageEmbed.Field field=getField(index);
            return field.isInline();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private void urlCheck(@Nullable String url) {
        String fName="[urlCheck]";
        if (url != null) {
            Checks.check(url.length() <= 2000, "URL cannot be longer than %d characters.", 2000);
            Checks.check(URL_PATTERN.matcher(url).matches(), "URL must be a valid http(s) or attachment url.");
        }
    }

}
