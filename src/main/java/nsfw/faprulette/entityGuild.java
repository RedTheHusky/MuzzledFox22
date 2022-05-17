package nsfw.faprulette;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class entityGuild {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[entityGuild]";

    public entityGuild() {
        String fName = "[constructor]";
        logger.info(fName);
    }
    public interface KEYS{
        String games="games";
        public interface GAME{
            String name="name",
                    description="description",
                    options="options",
                    styles ="styles",
                    style ="style";
            public interface OPTION{
                String title="title",
                        choices="choices",
                        conditions="conditions";
                public interface CHOICE{
                    String description="description",
                            point="point",
                            image="image";
                }
                public interface CONDITION{
                    String dice="dice",
                            min="min",
                            max="max",
                            equal="equal",
                            notequal="notequal";

                }
            }
            public interface STYLE{
                String urlImage ="url_image",
                        urlThumbnail="url_thumbnail",
                        color="color";
                public interface COLOR{
                    String value ="value",
                            red="red",r="r",
                            green="green",g="g",
                            blue="blue",b="b";

                }

            }
        }
    }
    public  class GLOBAL{
        String cName = "[entityGuild/GLOBAL]";
        public GLOBAL(){}
        private JSONObject jsonObject=new JSONObject();
        private List<GAME> games=new ArrayList<>();
        public GLOBAL set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if (jsonObject == null) {
                    logger.info(cName+fName + "jsonObject is null");
                    return null;
                }
                logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                this.jsonObject=jsonObject;
                String key="";
                JSONArray jsonArray;
                key=KEYS.games; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    games.clear();
                    jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        games.add(new GAME(jsonArray.getJSONObject(i)));
                    }
                }
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJSON() {
            String fName = "[getJSON]";
            try {
                logger.info(cName+"jsonObject=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public boolean hasKey(String key) {
            String fName = "[hasKey]";
            try {
                logger.info(cName+fName + "key=" + key);
                boolean value = jsonObject.has(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private GLOBAL createIfNotExists(JSONObject jsonObject, String key, Object object) {
            String fName = "[createIfNotExists]";
            try {
                logger.info(cName+fName + "key=" + key);
                if (hasKey(key)) {
                    return null;
                }
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private GLOBAL createIfNotExists(String key, Object object) {
            String fName = "[createIfNotExists]";
            try {
                logger.info(cName+fName + "key=" + key);
                if (hasKey(key)) {
                    return null;
                }
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private GLOBAL putTo(JSONObject jsonObject, String key, Object object) {
            String fName = "[putTo]";
            try {
                logger.info(cName+fName + "key=" + key);
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private GLOBAL putTo(String key, Object object) {
            String fName = "[putTo]";
            try {
                logger.info(cName+fName + "key=" + key);
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJSONObject(String key) {
            String fName = "[getJSONObject]";
            try {
                logger.info(cName+fName + "key=" + key);
                JSONObject value = jsonObject.getJSONObject(key);
                logger.info(cName+fName + "value=" + value.toString());
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getString(String key) {
            String fName = "[getString]";
            try {
                logger.info(cName+fName + "key=" + key);
                String value = jsonObject.getString(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getInt(String key) {
            String fName = "[getInt]";
            try {
                logger.info(fName + "key=" + key);
                int value = jsonObject.getInt(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getLong(String key) {
            String fName = "[getLong]";
            try {
                logger.info(cName+cName+fName + "key=" + key);
                long value = jsonObject.getLong(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean clear() {
            String fName = "[clear]";
            try {
                jsonObject=new JSONObject();
                return true;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public List<GAME> getGames() {
            String fName = "[getGame]";
            try {
                return this.games;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public GAME getGameAt(int index) {
            String fName = "[getGameAt]";
            try {
                logger.info(cName+fName + "index=" + index);
                return this.games.get(index);
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasGameAt(int index) {
            String fName = "[hasGameAt]";
            try {
                logger.info(cName+fName + "index=" + index);
                return this.games.get(index)!=null;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int gamesSize() {
            String fName = "[gamesSize]";
            try {
                int value=this.games.size();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isGamesEmpty() {
            String fName = "[isGamesEmpty]";
            try {
                boolean value=this.games.isEmpty();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
    }
    public  class CUSTOM{
        String cName = "[entityGuild/GLOBAL]";
        public CUSTOM(){}
        JSONObject jsonObject=new JSONObject();
        public List<GAME> games=new ArrayList<>();
        public CUSTOM set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if (jsonObject == null) {
                    logger.info(cName+fName + "jsonObject is null");
                    return null;
                }
                logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                this.jsonObject=jsonObject;
                String key="";
                JSONArray jsonArray;
                key=KEYS.games; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    games.clear();
                    jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        games.add(new GAME(jsonArray.getJSONObject(i)));
                    }
                }
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJSON() {
            String fName = "[getJSON]";
            try {
                logger.info(cName+"jsonObject=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public boolean hasKey(String key) {
            String fName = "[hasKey]";
            try {
                logger.info(cName+fName + "key=" + key);
                boolean value = jsonObject.has(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private CUSTOM createIfNotExists(JSONObject jsonObject, String key, Object object) {
            String fName = "[createIfNotExists]";
            try {
                logger.info(cName+fName + "key=" + key);
                if (hasKey(key)) {
                    return null;
                }
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private CUSTOM createIfNotExists(String key, Object object) {
            String fName = "[createIfNotExists]";
            try {
                logger.info(cName+fName + "key=" + key);
                if (hasKey(key)) {
                    return null;
                }
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private CUSTOM putTo(JSONObject jsonObject, String key, Object object) {
            String fName = "[putTo]";
            try {
                logger.info(cName+fName + "key=" + key);
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private CUSTOM putTo(String key, Object object) {
            String fName = "[putTo]";
            try {
                logger.info(cName+fName + "key=" + key);
                jsonObject.put(key, object);
                return this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJSONObject(String key) {
            String fName = "[getJSONObject]";
            try {
                logger.info(cName+fName + "key=" + key);
                JSONObject value = jsonObject.getJSONObject(key);
                logger.info(cName+fName + "value=" + value.toString());
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getString(String key) {
            String fName = "[getString]";
            try {
                logger.info(cName+fName + "key=" + key);
                String value = jsonObject.getString(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getInt(String key) {
            String fName = "[getInt]";
            try {
                logger.info(fName + "key=" + key);
                int value = jsonObject.getInt(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getLong(String key) {
            String fName = "[getLong]";
            try {
                logger.info(cName+cName+fName + "key=" + key);
                long value = jsonObject.getLong(key);
                logger.info(cName+fName + "value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean clear() {
            String fName = "[clear]";
            try {
                jsonObject=new JSONObject();
                return true;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
    }
    public  class GAME{
        String cName = "[entityGuild/GAME]";
        public GAME(){}
        int TITLE_MAX_LENGTH=MessageEmbed.TITLE_MAX_LENGTH;
        int VALUE_MAX_LENGTH=MessageEmbed.VALUE_MAX_LENGTH;
        int URL_MAX_LENGTH=MessageEmbed.URL_MAX_LENGTH;
        int MAX_OPTIONS=25;
        private String name="",description="";
        private List<OPTION> options=new ArrayList<>();
        private List<STYLE> styles =new ArrayList<>();
        private  STYLE defaultStyle=new STYLE();
        public GAME(JSONObject jsonObject){
            set(jsonObject);
        }
        public GAME set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if (jsonObject == null) {
                    logger.info(cName+fName + "jsonObject is null");
                    return null;
                }
                logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                String key="";
                key=KEYS.GAME.name; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    if(jsonObject.optString(key).length()<=TITLE_MAX_LENGTH)name=jsonObject.optString(key,"<#undefined>");
                    else name="<#MAX_LENGHT";
                }
                key=KEYS.GAME.description; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    if(jsonObject.optString(key).length()<=VALUE_MAX_LENGTH)description=jsonObject.optString(key,"<#undefined>");
                    else description="<#MAX_LENGHT";
                }
                key=KEYS.GAME.style; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    defaultStyle.set(jsonObject.getJSONObject(key));
                }
                key=KEYS.GAME.styles; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    styles.clear();
                    JSONArray jsonArray=jsonObject.getJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        styles.add(new STYLE(jsonArray.getJSONObject(i)));
                    }
                }
                key=KEYS.GAME.options; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    options.clear();
                    JSONArray jsonArray=jsonObject.getJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        if(options.size()>=MAX_OPTIONS)throw  new Exception("Reached max options size limit");
                        options.add(new OPTION(jsonArray.getJSONObject(i)));
                    }
                }

                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName() {
            String fName = "[getName]";
            try {
                String value=this.name;
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GAME setName(String input) {
            String fName = "[setName]";
            try {
                logger.info(cName+fName+"input="+input);
                if(input==null)input="";
                else if(input.length()>TITLE_MAX_LENGTH){
                    input= lsStringUsefullFunctions.truncateStringIfOver(input,VALUE_MAX_LENGTH);
                    logger.info(cName+fName+"input.truncated="+input);
                }
                this.name=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getDescription() {
            String fName = "[getDescription]";
            try {
                String value=this.description;
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GAME setDescription(String input) {
            String fName = "[setDescription]";
            try {
                logger.info(cName+fName+"input="+input);
                if(input==null)input="";
                else if(input.length()>VALUE_MAX_LENGTH){
                    input= lsStringUsefullFunctions.truncateStringIfOver(input,VALUE_MAX_LENGTH);
                    logger.info(cName+fName+"input.truncated="+input);
                }
                this.description=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public  class OPTION{
            String cName = "[entityGuild/GAME/OPTION]";
            public OPTION(){}
            int MAX_CHOICES=25;
            private String title="";
            private List<CHOICE> choices=new ArrayList<>();
            private List<CONDITION> conditions=new ArrayList<>();
            public OPTION(JSONObject jsonObject){
                set(jsonObject);
            }
            public OPTION set(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    if (jsonObject == null) {
                        logger.info(cName+fName + "jsonObject is null");
                        return null;
                    }
                    logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                    String key="";
                    key=KEYS.GAME.OPTION.title; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        if(jsonObject.optString(key).length()<=TITLE_MAX_LENGTH)title=jsonObject.optString(key,"<#undefined>");
                        else title="<#MAX_LENGHT";
                    }
                    key=KEYS.GAME.OPTION.choices; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        choices.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray(key);
                        for(int i=0;i<jsonArray.length();i++){
                            if(choices.size()>=MAX_CHOICES)throw  new Exception("Reached max choices size limit");
                            choices.add(new CHOICE(jsonArray.getJSONObject(i)));
                        }
                    }
                    key=KEYS.GAME.OPTION.conditions; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        conditions.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray(key);
                        for(int i=0;i<jsonArray.length();i++){
                            conditions.add(new CONDITION(jsonArray.getJSONObject(i)));
                        }
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getTitle() {
                String fName = "[getDescription]";
                try {
                    String value=this.title;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public OPTION setTitle(String input) {
                String fName = "[setDescription]";
                try {
                    logger.info(cName+fName+"input="+input);
                    if(input==null)input="";
                    else if(input.length()>TITLE_MAX_LENGTH){
                        input= lsStringUsefullFunctions.truncateStringIfOver(input,TITLE_MAX_LENGTH);
                        logger.info(cName+fName+"input.truncated="+input);
                    }
                    this.title=input;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private  boolean skip=false;
            public boolean isSkip() {
                String fName = "[isSkip]";
                try {
                    boolean value=this.skip;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public OPTION setSkip(boolean input) {
                String fName = "[setSkip]";
                try {
                    logger.info(cName+fName+"input="+input);
                    this.skip=input;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public  class CHOICE{
                String cName = "[entityGuild/GAME/OPTION/CHOICE]";
                public CHOICE(){}
                private String description="";
                private int point=0; //0= undefined, -1 ignore

                private STYLE style =new STYLE();
                public CHOICE(JSONObject jsonObject){
                    set(jsonObject);
                }
                public CHOICE set(JSONObject jsonObject) {
                    String fName = "[set]";
                    try {
                        if (jsonObject == null) {
                            logger.info(cName+fName + "jsonObject is null");
                            return null;
                        }
                        logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                        String key="";
                        key=KEYS.GAME.OPTION.CHOICE.description; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                            if(jsonObject.optString(key).length()<=VALUE_MAX_LENGTH)description=jsonObject.optString(key,"<#undefined>");
                            else description="<#MAX_LENGHT";
                        }
                        key=KEYS.GAME.OPTION.CHOICE.point; if(jsonObject.has(key)&&!jsonObject.isNull(key))point=jsonObject.optInt(key,0);
                        key=KEYS.GAME.OPTION.CHOICE.image;if(jsonObject.has(key)&&!jsonObject.isNull(key)) style =new STYLE(jsonObject.optJSONObject(key));
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getDescription() {
                    String fName = "[getDescription]";
                    try {
                        String value=this.description;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public CHOICE setDescription(String input) {
                    String fName = "[setDescription]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input==null)input="";
                        else if(input.length()>VALUE_MAX_LENGTH){
                            input= lsStringUsefullFunctions.truncateStringIfOver(input,VALUE_MAX_LENGTH);
                            logger.info(cName+fName+"input.truncated="+input);
                        }
                        this.description=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }

                public int getPoint() {
                    String fName = "[getPoint]";
                    try {
                        int value=this.point;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return 0;
                    }
                }
                public CHOICE setPoint(int input) {
                    String fName = "[setPoint]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        this.point=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public STYLE getStyle() {
                    String fName = "[getStyle]";
                    try {

                        return this.style;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public CHOICE setStyle(STYLE input) {
                    String fName = "[setStyle]";
                    try {
                        if(input==null)input=new STYLE();
                        this.style =input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            public  class CONDITION{
                String cName = "[entityGuild/GAME/OPTION/CONDITION]";
                public CONDITION(){}
                private int dice=-1,min=-1,max=-1,equal=-1, notequal=-1;
                private STYLE image=new STYLE();
                public CONDITION(JSONObject jsonObject){
                    set(jsonObject);
                }
                public CONDITION set(JSONObject jsonObject) {
                    String fName = "[set]";
                    try {
                        if (jsonObject == null) {
                            logger.info(cName+fName + "jsonObject is null");
                            return null;
                        }
                        logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                        String key="";
                        key=KEYS.GAME.OPTION.CONDITION.dice;
                        if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                            if(jsonObject.optString(key).length()<=VALUE_MAX_LENGTH)dice=jsonObject.optInt(key,-1);
                        }
                        key=KEYS.GAME.OPTION.CONDITION.min;  if(jsonObject.has(key)&&!jsonObject.isNull(key))min=jsonObject.optInt(key,-1);
                        key=KEYS.GAME.OPTION.CONDITION.max; if(jsonObject.has(key)&&!jsonObject.isNull(key))max=jsonObject.optInt(key,-1);
                        key=KEYS.GAME.OPTION.CONDITION.equal; if(jsonObject.has(key)&&!jsonObject.isNull(key))equal=jsonObject.optInt(key,-1);
                        key=KEYS.GAME.OPTION.CONDITION.notequal; if(jsonObject.has(key)&&!jsonObject.isNull(key))notequal=jsonObject.optInt(key,-1);
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getDice() {
                    String fName = "[getDice]";
                    try {
                        int value=this.dice;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public CONDITION setDice(int input) {
                    String fName = "[setDice]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input<0)input=-1;
                        this.dice=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getMin() {
                    String fName = "[getMin]";
                    try {
                        int value=this.min;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public CONDITION setMin(int input) {
                    String fName = "[setMin]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input<0)input=-1;
                        this.min=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getMax() {
                    String fName = "[getMax]";
                    try {
                        int value=this.max;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public CONDITION setMax(int input) {
                    String fName = "[setMax]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input<0)input=-1;
                        this.max=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getEqual() {
                    String fName = "[getEqual]";
                    try {
                        int value=this.equal;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public CONDITION setEqual(int input) {
                    String fName = "[setEqual]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input<0)input=-1;
                        this.equal=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getNotEqual() {
                    String fName = "[getNotEqual]";
                    try {
                        int value=this.notequal;
                        logger.info(cName+fName+"value="+value);
                        return value;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public CONDITION setNotEqual(int input) {
                    String fName = "[setNotEqual]";
                    try {
                        logger.info(cName+fName+"input="+input);
                        if(input<0)input=-1;
                        this.notequal=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            private int selected=-1;
            public CHOICE getSelectedChoice() {
                String fName = "[getSelectedChoice]";
                try {
                    CHOICE value=this.choices.get(getSelectedIndex());
                    logger.info(cName+fName+"got");
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getSelectedChoiceDescription() {
                String fName = "[getSelectedChoiceDescription]";
                try {
                    String value=getSelectedChoice().getDescription();
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public int getSelectedIndex() {
                String fName = "[getSelectedIndex]";
                try {
                    int value=this.selected;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public OPTION setSelected(int input) {
                String fName = "[setSelected]";
                try {
                    logger.info(cName+fName+"input="+input);
                    this.selected=input;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean checkCondition(List<Integer>rolls) {
                String fName = "[checkCondition]";
                try {
                    skip=false;
                    if(rolls==null){
                        logger.info(cName+fName+"rolls is null>true");
                        return true;
                    }
                    if(conditions.isEmpty()){
                        logger.info(cName+fName+"conditions isEmpty>true");
                        return true;
                    }
                    boolean isEqual=false;
                    boolean failedNotEqual=false;
                    boolean hasEqual=false,hasNotEqual=false;
                    for(int i=0;i<rolls.size();i++){
                        int roll=rolls.get(i);
                        logger.info(cName+fName+"roll["+i+"]="+roll);
                        for(CONDITION condition:conditions) {
                            int dice = condition.getDice();
                            if (dice != i) {
                                logger.info(cName + fName + "skip");
                            } else {
                                int max = condition.getMax();
                                int min = condition.getMin();
                                int equal = condition.getEqual();
                                int notequal = condition.getNotEqual();
                                logger.info(cName + fName + "max="+max+", min="+min+", equal="+equal+", notequal="+notequal);
                                if(min>-1){
                                    if(roll<min){
                                        logger.info(cName+fName+"roll is smaller than min>false");
                                        skip=true;
                                        return false;
                                    }
                                }
                                if(max>-1){
                                    if(roll>max){
                                        logger.info(cName+fName+"roll is bigger than max>false");
                                        skip=true;
                                        return false;
                                    }
                                }
                                if(equal>-1){
                                    hasEqual=true;
                                    if(roll==equal){
                                        logger.info(cName+fName+"roll is equal, flag that");
                                        isEqual=true;
                                    }
                                }
                                if(notequal>-1){
                                    hasNotEqual=true;
                                    if(roll==equal){
                                        logger.info(cName+fName+"roll faile notequal, flag that");
                                        failedNotEqual=true;
                                    }
                                }
                            }
                        }
                    }
                    if(hasEqual&&hasNotEqual&&isEqual&&failedNotEqual){
                        logger.info(cName+fName+"roll paradox, is equal and not equal>false");
                        skip=true;
                        return false;
                    }
                    if(hasEqual&&!isEqual){
                        logger.info(cName+fName+"roll has equal but is invalid>false");
                        skip=true;
                        return false;
                    }
                    if(hasNotEqual&&failedNotEqual){
                        logger.info(cName+fName+"roll has nonequal but is invalid>false");
                        skip=true;
                        return false;
                    }
                    logger.info(cName+fName+"default>true");
                    skip=false;
                    return true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public List<CHOICE> getChoices() {
                String fName = "[getChoices]";
                try {
                    return this.choices;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new ArrayList<>();
                }
            }
            public CHOICE getChoiceAt(int index) {
                String fName = "[getChoiceAt]";
                try {
                    logger.info(cName+fName + "index=" + index);
                    return this.choices.get(index);
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public List<CONDITION> getConditions() {
                String fName = "[getConditions]";
                try {
                    return this.conditions;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new ArrayList<>();
                }
            }
            public CONDITION getConditionAt(int index) {
                String fName = "[getConditionAt]";
                try {
                    logger.info(cName+fName + "index=" + index);
                    return this.conditions.get(index);
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public int choicesSize() {
                String fName = "[choicesSize]";
                try {
                    int value=this.choices.size();
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public int conditionsSize() {
                String fName = "[conditionsSize]";
                try {
                    int value=this.conditions.size();
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public boolean isChoicesEmpty() {
                String fName = "[isChoicesEmpty]";
                try {
                    boolean value=this.choices.isEmpty();
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public boolean isConditionsEmpty() {
                String fName = "[isConditionsEmpty]";
                try {
                    boolean value=this.conditions.isEmpty();
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
        }
        public  class STYLE {
            private String cName = "[entityGuild/GAME/OPTION/IMAGE]";
            public STYLE(){}
            private String url4Image="",url4Thumbnail="";
            private Color color=Color.BLACK;
            private int colorInt=0;
            public STYLE(JSONObject jsonObject){
                set(jsonObject);
            }
            public STYLE set(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    if (jsonObject == null) {
                        logger.info(cName+fName + "jsonObject is null");
                        return null;
                    }
                    logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                    String key="";
                    key=KEYS.GAME.STYLE.urlImage;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        if(jsonObject.optString(key).length()<=URL_MAX_LENGTH)url4Image=jsonObject.optString(key);
                    }
                    key=KEYS.GAME.STYLE.urlThumbnail; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        if(jsonObject.optString(key).length()<=URL_MAX_LENGTH)url4Thumbnail=jsonObject.optString(key);
                    }
                    key=KEYS.GAME.STYLE.color; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        setColor(jsonObject.getJSONObject(key));
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public STYLE setColor(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    if (jsonObject == null) {
                        logger.info(cName+fName + "jsonObject is null");
                        return null;
                    }
                    logger.info(cName+fName + "jsonObject=" + jsonObject.toString());
                    String key="";
                    key=KEYS.GAME.STYLE.COLOR.value;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        colorInt=jsonObject.optInt(key);
                        color=new Color(colorInt);
                        return  this;
                    }
                    int red=0,green=0,blue=0;
                    key=KEYS.GAME.STYLE.COLOR.red;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        red=jsonObject.optInt(key);
                    }else{
                        key=KEYS.GAME.STYLE.COLOR.r;
                        if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                            red=jsonObject.optInt(key);
                        }
                    }
                    key=KEYS.GAME.STYLE.COLOR.green;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        green=jsonObject.optInt(key);
                    }else{
                        key=KEYS.GAME.STYLE.COLOR.g;
                        if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                            green=jsonObject.optInt(key);
                        }
                    }
                    key=KEYS.GAME.STYLE.COLOR.blue;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        blue=jsonObject.optInt(key);
                    }else{
                        key=KEYS.GAME.STYLE.COLOR.b;
                        if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                            blue=jsonObject.optInt(key);
                        }
                    }
                    color=new Color(red,green,blue);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isImageUrlBlank() {
                String fName = "[isImageUrlBlank]";
                try {
                    String value=this.url4Image;
                    logger.info(cName+fName+"value="+value);
                    if(value==null){
                        logger.info(cName+fName+"isNull>true");
                        return true;
                    }
                    if(value.isBlank()){
                        logger.info(cName+fName+"isBlank>true");
                        return true;
                    }
                    logger.info(cName+fName+"default>false");
                    return false;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public String getImageUrl() {
                String fName = "[getImageUrl]";
                try {
                    String value=this.url4Image;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public STYLE setImageUrl(String input) {
                String fName = "[setImageUrl]";
                try {
                    logger.info(cName+fName+"input="+input);
                    if(input==null)input="";
                    else if(input.length()>URL_MAX_LENGTH){
                        logger.warn(cName+fName+"over the size limit");
                        return null;
                    }
                    this.url4Image=input;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isThumbnailUrlBlank() {
                String fName = "[isThumbnailUrlBlank]";
                try {
                    String value=this.url4Thumbnail;
                    logger.info(cName+fName+"value="+value);
                    if(value==null){
                        logger.info(cName+fName+"isNull>true");
                        return true;
                    }
                    if(value.isBlank()){
                        logger.info(cName+fName+"isBlank>true");
                        return true;
                    }
                    logger.info(cName+fName+"default>false");
                    return false;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public String getThumbnailUrl() {
                String fName = "[getThubnailUrl]";
                try {
                    String value=this.url4Thumbnail;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public STYLE setThumbnailUrl(String input) {
                String fName = "[setThumbnailUrl]";
                try {
                    logger.info(cName+fName+"input="+input);
                    if(input==null)input="";
                    else if(input.length()>URL_MAX_LENGTH){
                        logger.warn(cName+fName+"over the size limit");
                        return null;
                    }
                    this.url4Thumbnail=input;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public int getColorAsInt() {
                String fName = "[getColorAsInt]";
                try {
                    int value=this.colorInt;
                    logger.info(cName+fName+"value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public Color getColor() {
                String fName = "[getColor]";
                try {
                    if(color==null)color=Color.BLACK;
                    logger.info(cName+fName+"value="+color.getRGB());
                    return color;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return Color.BLACK;
                }
            }
            public STYLE setColor(int input) {
                String fName = "[setColor]";
                try {
                    logger.info(cName+fName+"input="+input);
                    this.colorInt=input;
                    this.color=new Color(input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public STYLE setColor(Color input) {
                String fName = "[setColor]";
                try {
                    if(input==null)input=Color.BLACK;
                    this.color=input;
                    int rgb=input.getRGB();
                    logger.info(cName+fName+"input="+rgb);
                    this.colorInt=rgb;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
        }
        private List<Integer>rolls=new ArrayList<>();
        public List<Integer> rollTheDice() {
            String fName = "[rollTheDice]";
            try {
                List<Integer>rolls=new ArrayList<>();
                int size0=optionsSize();
                logger.info(cName+fName + "optionsSize=" + size0);
                for(int i=0;i<size0;i++){
                    OPTION option=getOptionAt(i);
                    int size1=option.choicesSize();
                    logger.info(cName+fName + "options["+i+"].choicesSize="+size1);
                    int random=lsUsefullFunctions.getRandom(size1);
                    logger.info(cName+fName + "options["+i+"].random="+random);
                    option.setSelected(random);
                    rolls.add(random);
                }
                logger.info(cName+fName + "rolls.Size=" + rolls.size());
                logger.info(cName+fName + "do condition");
                for(int i=0;i<size0;i++){
                    OPTION option=getOptionAt(i);
                    int size1=option.conditionsSize();
                    logger.info(cName+fName + "options["+i+"].cconditionsSize="+size1);
                    boolean condition=option.checkCondition(rolls);
                    logger.info(cName+fName + "options["+i+"].condition="+condition);
                }
                logger.info(cName+fName + "done");
                this.rolls=rolls;
                return  rolls;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<Integer> getRolls() {
            String fName = "[getRolls]";
            try {
                return this.rolls;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public String getRollLetterAt(int index) {
            String fName = "[getRollLetterAt]";
            try {
                String value="";
                logger.info(cName+fName+"index="+index);
                int REDIX=16;//redix 16 is for Hexadecimal value
                int chara=10;
                int newchar=chara+index;
                char c=Character.forDigit(newchar,REDIX);
                value=String.valueOf(c);
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public List<String> getRollsPrint() {
           return getRollsPrint(false);
        }
        public List<String> getRollsPrint(boolean isUpperCase) {
            String fName = "[getRollsPrint]";
            try {
                List<String>list=new ArrayList<>();
                for(int i=0;i<this.rolls.size();i++){
                    if(isUpperCase){
                        list.add(getRollLetterAt(i).toUpperCase()+":"+rolls.get(i));
                    }else{
                        list.add(getRollLetterAt(i)+":"+rolls.get(i));
                    }
                }
                logger.info(cName+fName+"list="+list.toString());
                return list;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public int rollsSize() {
            String fName = "[rollsSize]";
            try {
                int value=this.rolls.size();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isRollsEmpty() {
            String fName = "[isRollsEmpty]";
            try {
                boolean value=this.rolls.isEmpty();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int getRollAt(int index) {
            String fName = "[getRollAt]";
            try {
                int value=this.rolls.get(index);
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public List<MessageEmbed.Field> getEmbedFields() {
           return getEmbedFields(false);
        }
        public List<MessageEmbed.Field> getEmbedFields(boolean addSkip) {
            String fName = "[getEmbedFields]";
            try {
                logger.info(cName+fName + "addSkip=" + addSkip);
                List<MessageEmbed.Field>fields=new ArrayList<>();
                for(OPTION option:options){
                    String title="",desc="";
                    if(option.isSkip()){
                        if(addSkip){
                            title=option.getTitle();
                            desc=option.getSelectedChoiceDescription();
                            fields.add(new MessageEmbed.Field(title,desc,false));
                        }
                    }else{
                        title=option.getTitle();
                        desc=option.getSelectedChoiceDescription();
                        fields.add(new MessageEmbed.Field(title,desc,false));
                    }
                }
                return fields;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<OPTION> getOptions() {
            String fName = "[getOptions]";
            try {
                return this.options;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public OPTION getOptionAt(int index) {
            String fName = "[getOptionAt]";
            try {
                logger.info(cName+fName + "index=" + index);
                return this.options.get(index);
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<STYLE> getStyles() {
            String fName = "[getStyles]";
            try {
                return this.styles;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public STYLE getDefaultStyle() {
            String fName = "[getDefaultStyle]";
            try {
                if(this.defaultStyle==null)this.defaultStyle=new STYLE();
                return this.defaultStyle;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int optionsSize() {
            String fName = "[optionsSize]";
            try {
                int value=this.options.size();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int stylesSize() {
            String fName = "[stylesSize]";
            try {
                int value=this.styles.size();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isOptionsEmpty() {
            String fName = "[isOptionsEmpty]";
            try {
                boolean value=this.options.isEmpty();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isStylesEmpty() {
            String fName = "[isStylesEmpty]";
            try {
                boolean value=this.styles.isEmpty();
                logger.info(cName+fName+"value="+value);
                return value;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
    }
    public GAME getNullGame() {
        String fName = "[getNullGame]";
        try {
            return new GAME();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  GLOBAL global=new GLOBAL();
    public CUSTOM custom=new CUSTOM();

}
