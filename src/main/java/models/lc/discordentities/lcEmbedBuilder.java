package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
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

public class lcEmbedBuilder {
    Logger logger = Logger.getLogger(getClass());
    public EmbedBuilder embedBuilder=new EmbedBuilder();
    public lcEmbedBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcEmbedBuilder(@Nullable EmbedBuilder builder){
        String fName="[create]";
        try {
            set(builder.build());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcEmbedBuilder(@Nullable MessageEmbed embed){
        String fName="[create]";
        try {
           set(embed);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcEmbedBuilder(@Nullable JSONObject jsonObject){
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
                setDescription(embed.getDescription());

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
                logger.info(fName + ".json=" + jsonObject.toString());
                if(jsonObject.has(keyDescription))setDescription(jsonObject.optString(keyDescription));
                if(jsonObject.has(keyTitle)&&jsonObject.has(keyUrl))setTitle(jsonObject.optString(keyTitle),jsonObject.optString(keyUrl)); else
                if(jsonObject.has(keyTitle))setTitle(jsonObject.optString(keyTitle));
                if(jsonObject.has(keyColor))setColor(jsonObject.optInt(keyColor));
                if(jsonObject.has(keyColor))setColor(jsonObject.optInt(keyColor));
                if(jsonObject.has(keyThumbnail))setThumbnail(jsonObject.optJSONObject(keyThumbnail));
                if(jsonObject.has(keyAuthor))setAuthor(jsonObject.optJSONObject(keyAuthor));
                if(jsonObject.has(keyFooter))setFooter(jsonObject.optJSONObject(keyFooter));
                if(jsonObject.has(keyImage))setImage(jsonObject.optJSONObject(keyImage));
                if(jsonObject.has(keyFields))setFields(jsonObject.optJSONArray(keyFields));
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
            return  embedBuilder.build();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public EmbedBuilder embed() {
        String fName="[embed]";
        try {
            return embedBuilder;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /*public  static  final String keyDescription="description",keyFields="fields",keyTitle="title",keyUrl="url",keyTimestamp="timestamp",
    keyColor="color",keyThumbnail="thumbnail",keyAuthor="author",keyFooter="footer",keyImage="image";*/
    public  static  final String keyDescription= llCommonKeys.EmbedStructure.description,keyFields=llCommonKeys.EmbedStructure.fields,keyTitle=llCommonKeys.EmbedStructure.title,keyUrl=llCommonKeys.EmbedStructure.url,keyTimestamp=llCommonKeys.EmbedStructure.timestamp,
            keyColor=llCommonKeys.EmbedStructure.color,keyColorRGB=llCommonKeys.EmbedStructure.custom_colorRGB,keyThumbnail=llCommonKeys.EmbedStructure.thumbnail,keyAuthor=llCommonKeys.EmbedStructure.author,keyFooter=llCommonKeys.EmbedStructure.footer,keyImage=llCommonKeys.EmbedStructure.image;
    public JSONObject getJSON() {
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            String description=getDescription(), title=getTitle4Title(),url=getUrl4Title();
            OffsetDateTime timestamp=getTimestamp();
            List<MessageEmbed.Field>fields=getFields();
            MessageEmbed.Thumbnail thumbnail=getThumbnail();
            MessageEmbed.AuthorInfo author=getAuthor();
            MessageEmbed.Footer footer=getFooter();
            MessageEmbed.ImageInfo image=getImage();
            if(description.length()>0)jsonObject.put(keyDescription,description.toString());
            if(!fields.isEmpty()) jsonObject.put(keyFields,getFieldsAsJson());
            if(title!=null) jsonObject.put(keyTitle,title);
            if(url!=null) jsonObject.put(keyUrl,url);
            if(timestamp!=null){
                jsonObject.put(keyTimestamp,getTimestampAsString());
            }
            jsonObject.put(keyColor,getColorAsInt());
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
    public lcEmbedBuilder setJSON(JSONObject jsonObject) {
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

    public lcEmbedBuilder clear() {
        String fName="[clear]";
        try {
            embedBuilder=new EmbedBuilder();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return this;
        }
    }
    public lcEmbedBuilder setTitle(@Nullable String title) {
        return this.setTitle(title, (String)null);
    }
    public lcEmbedBuilder setTitle(@Nullable String title, @Nullable String url) {
        String fName="[setTitle]";
        try {
            if (title == null) {
                embedBuilder.setTitle(null);
            } else {
                Checks.notEmpty(title, "Title");
                Checks.check(title.length() <= 256, "Title cannot be longer than %d characters.", 256);
                if (url!=null&&!url.isBlank()&&Helpers.isBlank(url)) {
                    url = null;
                    embedBuilder.setTitle(title);
                }else{
                    this.urlCheck(url);
                    embedBuilder.setTitle(title,url);
                }

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
            String title=getTitle4Title(),url=getUrl4Title();
            if(title!=null)jsonObject.put("title",title);
            if(url!=null)jsonObject.put("url",url);
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getTitle4Title() {
        String fName="[getTitle4Title]";
        try {
            String value=embedBuilder.build().getTitle();
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getUrl4Title() {
        String fName="[getUrl4Title]";
        try {
            String value=embedBuilder.build().getUrl();
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    public String getDescription() {
        String fName="[getDescription]";
        try {
            String value=embedBuilder.getDescriptionBuilder().toString();
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public StringBuilder getDescriptionBuilder() {
        String fName="[getDescription]";
        try {
            StringBuilder value=embedBuilder.getDescriptionBuilder();
            logger.info(fName+"value="+value.toString());
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public final lcEmbedBuilder setDescription(@Nullable CharSequence description) {
        String fName="[setDescription]";
        try {
            Checks.check(description.length() <= 2048, "Description cannot be longer than %d characters.", 2048);
            embedBuilder.setDescription(description);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder appendDescription(@Nonnull CharSequence description) {
        String fName="[appendDescription]";
        try {
            Checks.notNull(description, "description");
            Checks.check( embedBuilder.getDescriptionBuilder().length() + description.length() <= 2048, "Description cannot be longer than %d characters.", 2048);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setTimestamp(@Nullable TemporalAccessor temporal) {
        String fName="[setTimestamp]";
        try {
            embedBuilder.setTimestamp(temporal);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public OffsetDateTime getTimestamp() {
        String fName="[getTimestamp]";
        try {
            return embedBuilder.build().getTimestamp();
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
            OffsetDateTime offsetDateTime=getTimestamp();
            int year=offsetDateTime.getYear();
            int month=offsetDateTime.getMonthValue()+1;
            int day=offsetDateTime.getDayOfMonth();
            int hour=offsetDateTime.getHour();
            int minute=offsetDateTime.getMinute();
            int second=offsetDateTime.getSecond();
            String offsetId=offsetDateTime.getOffset().getId();
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
            OffsetDateTime offsetDateTime=getTimestamp();
            int year=offsetDateTime.getYear();
            int month=offsetDateTime.getMonthValue()+1;
            int day=offsetDateTime.getDayOfMonth();
            int hour=offsetDateTime.getHour();
            int minute=offsetDateTime.getMinute();
            int second=offsetDateTime.getSecond();
            String offsetId=offsetDateTime.getOffset().getId();
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

    public lcEmbedBuilder setColor(@Nullable Color color) {
        String fName="[setColor]";
        try {
            embedBuilder.setColor(color);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return this;
        }
    }
    public lcEmbedBuilder setColor(int color) {
        String fName="[setColor]";
        try {
            embedBuilder.setColor(color);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setColor(int r, int g, int b) {
        String fName="[setColor]";
        try {
            embedBuilder.setColor(new Color(r,g,b));
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
            int value=getColor().getRGB();
            logger.info(fName+"value_RGB="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Color getColor() {
        String fName="[getColor]";
        try {
            return embedBuilder.build().getColor();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setThumbnail(@Nullable String url) {
        String fName="[setThumbnail]";
        try {
            if (url == null) {
                embedBuilder.setThumbnail(null);
            } else {
                this.urlCheck(url);
                embedBuilder.setThumbnail(url);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setThumbnail(@Nullable JSONObject jsonObject) {
        String fName="[setThumbnail]";
        try {
            embedBuilder.setThumbnail(null);
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
            return embedBuilder.build().getThumbnail();
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
            String url=getThumbnailAsString();
            if(url!=null)jsonObject.put("url",url);
            logger.info(fName + ".jsonObject="+jsonObject.toString());
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
            String value=getThumbnail().getUrl();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setImage(@Nullable String url) {
        String fName="[setImage]";
        try {
            if (url == null) {
               embedBuilder.setImage(null);
            } else {
                this.urlCheck(url);
                embedBuilder.setImage(url);
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setImage(@Nullable JSONObject jsonObject) {
        String fName="[setImage]";
        try {
           embedBuilder.setImage(null);
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
            return embedBuilder.build().getImage();
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
            String url=getImageAsString();
            if(url!=null)jsonObject.put("url",url);
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
            String value=getImage().getUrl();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setAuthor(@Nullable String name) {
        return this.setAuthor(name, (String)null, (String)null);
    }
    public lcEmbedBuilder setAuthor(@Nullable String name, @Nullable String url) {
        return this.setAuthor(name, url, (String)null);
    }
    public lcEmbedBuilder setAuthor(@Nullable String name, @Nullable String url, @Nullable String iconUrl) {
        String fName="[setAuthor]";
        try {
            if (name == null) {
               embedBuilder.setAuthor(null);
            } else {
                Checks.check(name.length() <= 256, "Name cannot be longer than %d characters.", 256);
                if(url==null||iconUrl==null){
                    embedBuilder.setAuthor(name);
                }
                else if(iconUrl==null){
                    this.urlCheck(url);
                    embedBuilder.setAuthor(name,url);
                }
                else if(url==null){
                    this.urlCheck(iconUrl);
                    embedBuilder.setAuthor(name,null,iconUrl);
                }else{
                    this.urlCheck(url);
                    this.urlCheck(iconUrl);
                    embedBuilder.setAuthor(name,url,iconUrl);
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setAuthor(@Nullable JSONObject jsonObject) {
        String fName="[setAuthor]";
        try {
            embedBuilder.setAuthor(null);
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
            return embedBuilder.build().getAuthor();
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
            String name=getAuthorName(),url=getAuthorUrl(),icon=getAuthorIconUrl();
            if(name!=null)jsonObject.put("name",name);
            if(url!=null)jsonObject.put("url",url);
            if(icon!=null)jsonObject.put("iconUrl",icon);
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
            String value=getAuthor().getName();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  String getAuthorUrl() {
        String fName="[getAuthorUrl]";
        try {
            String value=getAuthor().getUrl();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  String getAuthorIconUrl() {
        String fName="[getAuthorIconUrl]";
        try {
            String value=getAuthor().getIconUrl();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setFooter(@Nullable String text) {
        return this.setFooter(text, (String)null);
    }
    public lcEmbedBuilder setFooter(@Nullable String text, @Nullable String iconUrl) {
        String fName="[setFooter]";
        try {
            if (text == null) {
                embedBuilder.setFooter(null);
            } else {
                Checks.check(text.length() <= 2048, "Text cannot be longer than %d characters.", 2048);
                if(iconUrl==null){
                    embedBuilder.setFooter(text);
                }else{
                    this.urlCheck(iconUrl);
                    embedBuilder.setFooter(text,iconUrl);
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setFooter(@Nullable JSONObject jsonObject) {
        String fName="[setFooter]";
        try {
            embedBuilder.setFooter(null);
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
            return embedBuilder.build().getFooter();
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
            String text=getFooterText(),icon=getFooterIconUrl();
            if(text!=null)jsonObject.put("text",text);
            if(icon!=null)jsonObject.put("iconUrl",icon);
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
            String value=getFooter().getText();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getFooterIconUrl() {
        String fName="[getFooterIconUrl]";
        try {
            String value=getFooter().getIconUrl();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder addField(@Nullable MessageEmbed.Field field) {
        String fName="[addField]";
        try {
            return field == null ? this : this.addField(field.getName(), field.getValue(), field.isInline());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder addField(@Nullable String name, @Nullable String value, boolean inline) {
        String fName="[addField]";
        try {
            if (name == null && value == null) {
                return this;
            } else {
                embedBuilder.addField(new MessageEmbed.Field(name, value, inline));
                return this;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public lcEmbedBuilder setField(int index, @Nullable MessageEmbed.Field field) {
        String fName="[setField]";
        try {
            if (field==null) {
                return null;
            } else {
                List<MessageEmbed.Field>fields=getFields();
                if(fields.isEmpty()){
                    throw  new Exception("Fields is empty");
                }
                if(index<0){
                    throw  new Exception("Index is bellow 0");
                }
                if(index>=fields.size()){
                    throw  new Exception("Index is equal or above fields size");
                }
                embedBuilder.clearFields();
                if(index>0){
                    for(int i=0;i<index-1;i++){
                        embedBuilder.addField(fields.get(i));
                    }
                }
                embedBuilder.addField(field);
                if(index<fields.size()-1){
                    for(int j=index+1;j<fields.size();j++){
                        embedBuilder.addField(fields.get(j));
                    }
                }
                return this;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setField(int index, @Nullable String name, @Nullable String value, boolean inline) {
        String fName="[setField]";
        try {
            if (name == null && value == null) {
                return this;
            } else {
                MessageEmbed.Field newfild=new MessageEmbed.Field(name, value, inline);
                if(newfild==null){
                    throw  new Exception("New field cant be null");
                }
                return setField(index,newfild);
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setFields(@Nullable JSONArray jsonArray) {
        String fName="[setFields]";
        try {
            embedBuilder.clearFields();
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
    public lcEmbedBuilder addBlankField(boolean inline) {
        String fName="[addBlankField]";
        try {
            addField(new MessageEmbed.Field("\u200e", "\u200e", inline));
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setBlankField(int index, boolean inline) {
        String fName="[setBlankField]";
        try {
            setField(index,new MessageEmbed.Field("\u200e", "\u200e", inline));
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
            List<MessageEmbed.Field>fields=getFields();
            if(fields.isEmpty()){
                throw  new Exception("Fields is empty");
            }
            if(index<0){
                throw  new Exception("Index is bellow 0");
            }
            if(index>=fields.size()){
                throw  new Exception("Index is equal or above fields size");
            }
            MessageEmbed.Field value=fields.get(index);
            embedBuilder.clearFields();
            if(index>0){
                for(int i=0;i<index-1;i++){
                    embedBuilder.addField(fields.get(i));
                }
            }
            if(index<fields.size()-1){
                for(int j=index+1;j<fields.size();j++){
                    embedBuilder.addField(fields.get(j));
                }
            }
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder clearFields() {
        String fName="[clearFields]";
        try {
            embedBuilder.clearFields();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcEmbedBuilder setFieldInline(int index, boolean inline) {
        String fName="[setFieldInline]";
        try {
            MessageEmbed.Field fieldOld=getField(index);
            MessageEmbed.Field fieldNew=new MessageEmbed.Field(fieldOld.getName(),fieldOld.getValue(),inline);
            setField(index,fieldNew);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<MessageEmbed.Field> getFields() {
        String fName="[getFields]";
        try {
           return embedBuilder.getFields();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int sizeFields() {
        String fName="[sizeFields]";
        try {
            int value= embedBuilder.getFields().size();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyFields() {
        String fName="[isEmptyFields]";
        try {
            boolean value=embedBuilder.getFields().isEmpty();
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public JSONArray getFieldsAsJson(){
        String fName="[getFieldsAsJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<sizeFields();i++){
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
            return getFields().get(index);
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

    public static final Pattern URL_PATTERN = Pattern.compile("\\s*(https?|attachment)://\\S+\\s*", 2);
    private void urlCheck(@Nullable String url) {
        String fName="[urlCheck]";
        if (url != null) {
            Checks.check(url.length() <= 2000, "URL cannot be longer than %d characters.", 2000);
            Checks.check(URL_PATTERN.matcher(url).matches(), "URL must be a valid http(s) or attachment url.");
        }
    }

}
