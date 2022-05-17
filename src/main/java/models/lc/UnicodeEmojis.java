package models.lc;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.ls.lsStringUsefullFunctions;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnicodeEmojis {
    protected Logger logger = Logger.getLogger(getClass());
    protected lcText2Json text2Json;
    public UnicodeEmojis(){
        build();
    }
    public UnicodeEmojis(boolean auto){
        if(auto){
            build();
        }
    }
    String path="resources/json/others/emojis.json";
    public boolean build(){
        String fName="[build]";
        try {
            if(!load())return false;
            if(!buildEmojiList())return false;
            if(!buildAliasList())return false;
            logger.info(fName+".built");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            text2Json.jsonArray=new JSONArray();
            emojis=new ArrayList<>();
            firstAliases=new ArrayList<>();
            logger.info(fName+".cleared");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    protected boolean load(){
        String fName="[load]";
        try {
            text2Json=new lcText2Json();
            text2Json.isFile2JsonArray(path);
            logger.info(fName+".loaded");return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }

    public interface CommonKeys {
        //https://discord.com/developers/docs/interactions/message-components#buttons-button-object
        String EmojiChar="emojiChar", Emoji="emoji",Description="description",Aliases="aliases",Tags="tags";

    }
    public class UnicodeEmoji {
        protected Logger logger = Logger.getLogger(UnicodeEmojis.class);
        protected String nName="UnicodeEmoji@";
        protected String emojiChar=null,emoji=null,description=null;
        protected List<String>aliases=new ArrayList<>(),tags=new ArrayList<>();
        protected UnicodeEmoji(){
            String fName="[build]";
            logger.info(fName+".blank");
        }
        public UnicodeEmoji(JSONObject jsonObject){
            String fName="[build]";
            logger.info(fName+".blank");
            set(jsonObject);
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                if(jsonObject.isEmpty())return false;
                String key="";
                key=CommonKeys.Emoji;if(jsonObject.has(key))emoji=jsonObject.optString(key,"");
                key=CommonKeys.EmojiChar;if(jsonObject.has(key))emojiChar=jsonObject.optString(key,"");
                key=CommonKeys.Description;if(jsonObject.has(key))description=jsonObject.optString(key,"");
                key=CommonKeys.Aliases;if(jsonObject.has(key))aliases=lsStringUsefullFunctions.Json2String(jsonObject.optJSONArray(key));
                key=CommonKeys.Tags;if(jsonObject.has(key)) tags=lsStringUsefullFunctions.Json2String(jsonObject.optJSONArray(key));
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasAliasFirst(String alias){
            String fName="[hasAliasFirst]";
            try {
                logger.info(nName+fName+".aliases="+aliases.toString());
                logger.info(nName+fName+".alias="+alias);
                if(aliases.isEmpty()){ logger.warn(nName+fName + "is empty>false");return false;}
                String item=aliases.get(0);
                logger.info(nName+fName + "item["+0+"]="+item);
                if(item.equalsIgnoreCase(alias)){
                    logger.info(nName+fName + "found");
                    return true;
                }
                logger.info(nName+fName+".not found");
                return false;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasAlias(String alias){
            String fName="[hasAlias]";
            try {
                logger.info(nName+fName+".aliases="+aliases.toString());
                logger.info(nName+fName+".alias="+alias);
                if(aliases.isEmpty()){ logger.warn(nName+fName + "is empty>false");return false;}
                for(String item:aliases){
                    if(item.equalsIgnoreCase(alias)){
                        logger.info(nName+fName+".found");
                        return true;
                    }
                }
                logger.info(nName+fName+".not found");
                return false;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasAlias(String alias,int from, int to){
            String fName="[hasAlias]";
            try {
                logger.info(nName+fName+".aliass="+aliases.toString());
                logger.info(nName+fName+".alias="+alias+", from="+from+", to="+to);

                if(aliases.isEmpty()){ logger.warn(nName+fName + "is empty>false");return false;}
                if(from>to){logger.warn(nName+fName + "from is bigger than to>false");return false;}
                if(from<0){logger.warn(nName+fName + "from is less than zero>false");return false;}
                int i=from,l=aliases.size();
                if(to>0){
                    while(i<l&&i<=to){
                        String item=aliases.get(i);
                        logger.info(nName+fName + "item["+i+"]="+item);
                        if(item.equalsIgnoreCase(alias)){
                            logger.info(nName+fName + "found");
                            return true;
                        }
                        i++;
                    }
                }else{
                    while(i<l){
                        String item=aliases.get(i);
                        logger.info(nName+fName + "item["+i+"]="+item);
                        if(item.equalsIgnoreCase(alias)){
                            logger.info(nName+fName + "found");
                            return true;
                        }
                        i++;
                    }
                }

                logger.info(nName+fName+".not found");
                return false;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getEmoji(){
            String fName="[getEmoji]";
            try {
                String value=emoji;
                logger.info(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getEmojiChar(){
            String fName="[getEmojiChar]";
            try {
                String value=emojiChar;
                logger.info(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getDescription(){
            String fName="[getDescription]";
            try {
                String value=description;
                logger.error(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public List<String> getAliases(){
            String fName="[getAliases]";
            try {
                List<String> value=aliases;
                logger.error(nName+fName + ".value=" + value.toString());
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<String> getTags(){
            String fName="[getTags]";
            try {
                List<String> value=tags;
                logger.error(nName+fName + ".value=" + value.toString());
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }

        public String getFirstAlias() {
            String fName="[getFirstAlias]";
            try {
                String value=aliases.get(0);
                logger.info(nName+fName + ".value=" + value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
    }
    List<UnicodeEmoji> emojis=new ArrayList<>();
    List<String>firstAliases=new ArrayList<>();
    public boolean isEmpty(){
        String fName="[isEmpty]";
        try {
            boolean result= emojis.isEmpty();;
            logger.info(fName + ".result" + result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    protected boolean buildEmojiList(){
        String fName="[buildEmojiList]";
        try {
            if(text2Json.jsonArray.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName+".text2Json.jsonArray.length="+text2Json.jsonArray.length());
            for(int i=0;i<text2Json.jsonArray.length();i++){
                UnicodeEmoji emoji=new UnicodeEmoji(text2Json.jsonArray.optJSONObject(i));
                emojis.add(emoji);
            }
            logger.info(fName + ".done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    protected boolean buildAliasList(){
        String fName="[buildEmojiList]";
        try {
            for(UnicodeEmoji emoji:emojis){
                String alias=emoji.getFirstAlias();
                if(alias==null||alias.isBlank())alias="<null>";
                firstAliases.add(alias);
            }
            logger.info(fName + ".done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;

        }
    }
    public UnicodeEmoji getUnicodeEmojiByFirstAlias(String alias){
        String fName="[getUnicodeEmojiByFirstAlias]";
        try {
            logger.info(fName + ".alias=" + alias);
            /*for(UnicodeEmoji emoji: emojis){
                if(emoji.hasAliasFirst(alias)){
                    logger.info(fName+".found");
                    return emoji;
                }
            }*/
            int i=0;
            for(String firstAlias: firstAliases){
                if(firstAlias.equalsIgnoreCase(alias)){
                    logger.info(fName+".found at "+i);
                    return emojis.get(i);
                }
                i++;
            }
            logger.info(fName+".not found");
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return null;

        }
    }

    public String getEmoji(String alias){
        String fName="[getEmoji]";
        try {
            logger.info(fName+".alias="+alias);
            UnicodeEmoji emoji=getUnicodeEmojiByFirstAlias(alias);
            String value=emoji.getEmoji();
            logger.info(fName+".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";

        }
    }
    public String getEmojiChar(String alias){
        String fName="[getEmojiChar]";
        try {
            logger.info(fName+".alias="+alias);
            UnicodeEmoji emoji=getUnicodeEmojiByFirstAlias(alias);
            String value=emoji.getEmojiChar();
            logger.info(fName+".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";

        }
    }
}
