package models.lc.interaction.applicationcommand;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llCommonVariables;
import models.llGlobalHelper;
import models.ls.lsDiscordApi;
import models.ls.lsGuildHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcApplicationCCommonEditor {
    Logger logger = Logger.getLogger(getClass());
    public lcApplicationCCommonEditor(){

    }
    public lcApplicationCCommonEditor(lcGlobalHelper global){
        this.global=global;
    }
    public lcApplicationCCommonEditor(JDA jda){
        this.jda=jda;
    }
    public lcGlobalHelper global;
    public  JDA jda;
    protected liApplicationCommand.lcApplicationTypes type= liApplicationCommand.lcApplicationTypes.INVALID;
    protected Guild guild=null;
    protected String name="";
    protected String id="", guild_id,version="",application_id="";
    boolean default_permission=true;
    public final  int MAX_NAME_LENGHT= OptionData.MAX_NAME_LENGTH,MAX_DESCRIPTION_LENGTH= OptionData.MAX_DESCRIPTION_LENGTH;

    public lcApplicationCCommonEditor(JSONObject jsonObject) {
        String fName = "[build]";
        try {
            logger.info(fName + ".creating");
            setAll(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcApplicationCCommonEditor(JSONObject jsonObject, Guild guild) {
        String fName = "[build]";
        try {
            logger.info(fName + ".creating");
            setAll(jsonObject);
            this.guild=guild;
            this.jda=guild.getJDA();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcApplicationCCommonEditor setAll(JSONObject jsonObject) {
        String fName = "[setAll]";
        try {
            if(jsonObject==null)throw  new Exception("Json is null!");
            if(jsonObject.isEmpty())throw  new Exception("Json is empty!");
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(setIdentify(jsonObject)==null)throw  new Exception("setIdentify failed!");
            if(setData(jsonObject)==null)throw  new Exception("setData failed!");
            logger.info(fName+"none failed");
            return  this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor setIdentify(JSONObject jsonObject) {
        String fName = "[setIdentify]";
        try {
            if(jsonObject==null)throw  new Exception("Json is null!");
            if(jsonObject.isEmpty())throw  new Exception("Json is empty!");
            logger.info(fName+"jsonObject="+jsonObject.toString());
            String key="";
            key=llCommonKeys.ApplicationCommandStructure.id;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                OTHER.setId(jsonObject.optString(key,""));
                key=llCommonKeys.ApplicationCommandStructure.application_id;
                if(jsonObject.has(key)&&!jsonObject.isNull(key))OTHER.setApplicationId(jsonObject.optString(key,""));
                key=llCommonKeys.ApplicationCommandStructure.version;
                if(jsonObject.has(key)&&!jsonObject.isNull(key))OTHER.setVersion(jsonObject.optString(key,""));
                key=llCommonKeys.ApplicationCommandStructure.guild_id;
                if(jsonObject.has(key)&&!jsonObject.isNull(key))OTHER.setGuild(jsonObject.optString(key,""));
            }else{
                throw  new Exception("No command id provided!");
            }
            logger.info(fName+"none failed");
            return  this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor setData(JSONObject jsonObject) {
        String fName = "[setData]";
        try {
            if(jsonObject==null)throw  new Exception("Json is null!");
            if(jsonObject.isEmpty())throw  new Exception("Json is empty!");
            logger.info(fName+"jsonObject="+jsonObject.toString());
            String key="";
            key= llCommonKeys.ApplicationCommandStructure.Type;if(jsonObject.has(key)&&!jsonObject.isNull(key))OTHER.setType(jsonObject.optInt(key,-1));else{
                throw  new Exception("No type provided!");
            }
            key= llCommonKeys.ApplicationCommandStructure.Name;if(jsonObject.has(key)&&!jsonObject.isNull(key))setName(jsonObject.optString(key,""));else{
                throw  new Exception("No name provided!");
            }
            key= llCommonKeys.ApplicationCommandStructure.DefaultPermission;if(jsonObject.has(key)&&!jsonObject.isNull(key))setDefaultPermission(jsonObject.optBoolean(key));
            if (getType() == liApplicationCommand.lcApplicationTypes.CHAT_INPUT) {
                SLASH.cName=SLASH.cName.replaceFirst("!name",name);
                SLASH.set(jsonObject);
            }
            logger.info(fName+"none failed");
            return  this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public liApplicationCommand.lcApplicationTypes getType() {
        String fName = "[getType]";
        try {
            if(type==null){
                logger.info(fName+"type is null");
                return liApplicationCommand.lcApplicationTypes.INVALID;
            }
            return type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getTypeAsInt() {
        String fName = "[getTypeAsInt]";
        try {
            if(type==null){
                logger.info(fName+"type is null");
                return liApplicationCommand.lcApplicationTypes.INVALID.getCode();
            }
            return type.getCode();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -2;
        }
    }
    private  _OTHER OTHER=new _OTHER();
    protected  class _OTHER{
        public _OTHER(){}
        public _OTHER setType(liApplicationCommand.lcApplicationTypes input) {
            String fName = "[setType]";
            try {
                if(input==null)throw new Exception("isNull!");
                logger.info(fName + ".input=" + input.getCode());
                type=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _OTHER setType(int input) {
            String fName = "[setType]";
            try {
                logger.info(fName + ".input=" + input);
                return  setType(liApplicationCommand.lcApplicationTypes.valueByCode(input));
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _OTHER setId(String input) {
            String fName = "[setId]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                id=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _OTHER setApplicationId(String input) {
            String fName = "[setApplicationId]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                application_id=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _OTHER setGuild(String input) {
            String fName = "[setGuild]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                guild_id=input;
                if(guild!=null){
                    logger.info(fName + ".ignore as it has a guild");
                    return this;
                }
                Guild guild0=null;
                if(jda!=null){
                    guild0=lsGuildHelper.getGuild(jda,guild_id);
                    if(guild0!=null){
                        guild=guild0;
                        logger.info(fName + ".retrieved from jda");
                        return this;
                    }
                }
                if(global!=null){
                    guild0=global.getGuild(guild_id);
                    if(guild0!=null){
                        guild=guild0;
                        logger.info(fName + ".retrieved from global");
                        return this;
                    }
                }
                logger.warn(fName + ".no guild managed to retrieve");
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _OTHER setVersion(String input) {
            String fName = "[setVersion]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                version=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public _GENERAL GENERAL=new _GENERAL();
    protected  class _GENERAL{
        public _GENERAL(){}
        public JSONObject getJson() {
            String fName = "[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
                logger.info(fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public JSONObject getJson4Build() {
            String fName = "[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                if(!isDefaultPermission())jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
                logger.info(fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
    }
    public _SLASH SLASH=new _SLASH();
    protected  class _SLASH{
        public _SLASH(){}
        String cName="SLASH[!name]@";
        public final int MAX_OPTIONS_SIZE=25;public final int MAX_CHOICES_SIZE=25;
        List<_SLASH.GENERALOPTION> options=new ArrayList<>();
        private String description="";
        public class GENERALOPTION {
            String nName="GENERALOPTION[!name]@";
            OptionType optionType=OptionType.UNKNOWN;
            private String name="",description="";
            private  boolean  required=false,autocomplete=false;
            private  int min_value_integer,max_value_integer;
            private  double min_value_number,max_value_number;
            private boolean min_value=false,max_value=false;
            public GENERALOPTION() {

            }
            public GENERALOPTION(JSONObject jsonObject) {
                set(jsonObject);
            }
            public GENERALOPTION set(JSONObject jsonObject) {
                String fName="[set]";
                try {
                    logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                    String key;
                    key=llCommonKeys.ApplicationCommandOptionStructure.Type;if (jsonObject.has(key)) optionType=OptionType.fromKey(jsonObject.optInt(key,0));
                    key=llCommonKeys.ApplicationCommandOptionStructure.Name;if (jsonObject.has(key)) name=jsonObject.optString(key,"");
                    nName=nName.replaceFirst("!name",name);
                    key=llCommonKeys.ApplicationCommandOptionStructure.Description;if (jsonObject.has(key)) description=jsonObject.optString(key,"");
                    key=llCommonKeys.ApplicationCommandOptionStructure.Required;if (jsonObject.has(key)) required=jsonObject.optBoolean(key,false);
                    key=llCommonKeys.ApplicationCommandOptionStructure.Autocomplete;if (jsonObject.has(key)) autocomplete=jsonObject.optBoolean(key,false);

                    switch (optionType){
                        case NUMBER:case INTEGER: case STRING:
                            key=llCommonKeys.ApplicationCommandOptionStructure.Choices;
                            if (jsonObject.has(key)) {
                                JSONArray jsonChoices = jsonObject.getJSONArray(key);
                                for(int i=0;i<jsonChoices.length();i++){
                                    choices.add(new _SLASH.GENERALOPTION.CHOICE(optionType,jsonChoices.getJSONObject(i)));
                                }
                            }
                            break;
                        case SUB_COMMAND: case SUB_COMMAND_GROUP:
                            key=llCommonKeys.ApplicationCommandOptionStructure.Options;
                            if (jsonObject.has(key)) {
                                JSONArray jsonChoices = jsonObject.getJSONArray(key);
                                for(int i=0;i<jsonChoices.length();i++){
                                    options.add(new _SLASH.GENERALOPTION(jsonChoices.getJSONObject(i)));
                                }
                            }
                            break;
                    }


                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public int getTypeAsInt() {
                String fName = "[getTypeAsInt]";
                try {
                    logger.info(nName+fName + ".value=" + optionType.name());
                    return optionType.getKey();
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return OptionType.UNKNOWN.getKey();
                }
            }
            public OptionType getType() {
                String fName = "[getType]";
                try {
                    logger.info(nName+fName + ".value=" + optionType.name());
                    return optionType;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return OptionType.UNKNOWN;
                }
            }
            public _SLASH.GENERALOPTION setType(OptionType input) {
                String fName = "[setType]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".input=" + input.name());
                    optionType=input;
                    clearChoices();clearOptions();
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setType(int input) {
                String fName = "[setType]";
                try {
                    logger.info(nName+fName + ".input=" + input);
                    OptionType optionType=OptionType.fromKey(input);
                    if(optionType==null)throw  new Exception("Input can be null");
                    this.optionType=optionType;
                    clearChoices();clearOptions();
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            public String getName() {
                String fName = "[getName]";
                try {
                    logger.info(nName+fName + ".value=" + name);
                    if(name==null){
                        logger.info(nName+fName+"isNull so send blank");
                        return "";
                    }
                    return name;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setName(String input) {
                String fName = "[setName]";
                try {
                    logger.info(nName+fName + ".input=" + input);
                    if(input==null)throw  new Exception("Input can be null");
                    name=input;
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getDescription() {
                String fName = "[getDescription]";
                try {
                    logger.info(nName+fName + ".value=" + description);
                    if(description==null){
                        logger.info(nName+fName+"isNull so send blank");
                        return "";
                    }
                    return description;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setDescription(String input) {
                String fName = "[setDescription]";
                try {
                    logger.info(nName+fName + ".input=" + input);
                    if(input==null)throw  new Exception("Input can be null");
                    description=input;
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isRequired() {
                String fName = "[isRequired]";
                try {
                    logger.info(nName+fName + ".value=" + required);
                    return required;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public _SLASH.GENERALOPTION setRequired(boolean input) {
                String fName = "[setRequired]";
                try {
                    logger.info(nName+fName + ".input=" + input);
                    required=input;
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public double getMinValue() {
                String fName = "[getMinValue]";
                try {
                    logger.info(nName+fName + ".min_value_integer=" + min_value_integer+", min_value_number="+min_value_number+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:  return  min_value_number;
                        case INTEGER:return  min_value_integer;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            public double getMaxValue() {
                String fName = "[getMaxValue]";
                try {
                    logger.info(nName+fName + ".max_value_integer=" + max_value_integer+", max_value_number="+max_value_number+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:  return  max_value_number;
                        case INTEGER:return  max_value_integer;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            public boolean hasMinValue() {
                String fName = "[hasMinValue]";
                try {
                    logger.info(nName+fName + ".value=" + min_value);
                    return min_value;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean hasMaxValue() {
                String fName = "[hasMaxValue]";
                try {
                    logger.info(nName+fName + ".value=" + max_value);
                    return max_value;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public _SLASH.GENERALOPTION clearValues() {
                String fName = "[clearChoices]";
                try {
                    min_value_integer=0;max_value_integer=0;
                    min_value_number=0;max_value_number=0;
                    min_value=false;max_value=false;
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setMinValue(int input) {
                String fName = "[setMinValue]";
                try {
                    logger.info(nName+fName + ".input="+input+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:    min_value_number=input;break;
                        case INTEGER:  min_value_integer=input;break;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setMaxValue(int input) {
                String fName = "[setMaxValue]";
                try {
                    logger.info(nName+fName + ".input="+input+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:    max_value_number=input;break;
                        case INTEGER:  max_value_integer=input;break;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setMinValue(double input) {
                String fName = "[setMinValue]";
                try {
                    logger.info(nName+fName + ".input="+input+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:    min_value_number=input;break;
                        case INTEGER:  min_value_integer=(int)input;break;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setMaxValue(double input) {
                String fName = "[setMaxValue]";
                try {
                    logger.info(nName+fName + ".input="+input+", type="+getTypeAsInt());
                    switch (getType()){
                        case NUMBER:    max_value_number=input;break;
                        case INTEGER:  max_value_integer=(int)input;break;
                        default:throw new Exception("Option is not INTEGER or NUMBER");
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isAutocomplete() {
                String fName = "[isAutocomplete]";
                try {
                    logger.info(nName+fName + ".value=" + autocomplete);
                    return autocomplete;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public _SLASH.GENERALOPTION setAutocomplete(boolean input) {
                String fName = "[setAutocomplete]";
                try {
                    logger.info(nName+fName + ".input=" + input);
                    autocomplete=input;
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            private List<_SLASH.GENERALOPTION> options =new ArrayList<>();
            public List<_SLASH.GENERALOPTION> getOptions() {
                String fName = "[getOptions]";
                try {
                    return  options;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isEmptyOptions() {
                String fName = "[isEmptyOptions]";
                try {
                    boolean result=options.isEmpty();
                    logger.info(nName+fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public _SLASH.GENERALOPTION clearOptions() {
                String fName = "[clearOptions]";
                try {
                    options =new ArrayList<>();
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION getOptionAt(int index) {
                String fName = "[getOptionAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    _SLASH.GENERALOPTION choice=options.get(index);
                    logger.info(nName+fName + ".option=" + choice.getName());
                    return choice;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION remOptionAt(int index) {
                String fName = "[remOptionAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    _SLASH.GENERALOPTION choice=options.remove(index);
                    logger.info(nName+fName + ".option=" + choice.getName());
                    return choice;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addOption(_SLASH.GENERALOPTION input) {
                String fName = "[addOption]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".input=" + input.getName());
                    options.add(input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addOption(int index, _SLASH.GENERALOPTION input) {
                String fName = "[addOption]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getName());
                    options.add(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setOption(int index, _SLASH.GENERALOPTION input) {
                String fName = "[setOption]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getName());
                    options.set(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONArray getOptionsJson() {
                String fName = "[getOptionsJson]";
                try {
                    JSONArray jsonArray=new JSONArray();
                    for(_SLASH.GENERALOPTION option:options){
                        jsonArray.put(option.getJson());
                    }
                    logger.info(nName+fName + ".jsonArray=" + jsonArray.toString());
                    return jsonArray;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONArray getOptionsJson4Build() {
                String fName = "[getOptionsJson4Build]";
                try {
                    JSONArray jsonArray=new JSONArray();
                    for(GENERALOPTION option:options){
                        if(jsonArray.length()==MAX_OPTIONS_SIZE){
                            logger.warn(nName+fName + ".Reached options max count");
                            break;
                        }
                        jsonArray.put(option.getJson4Build());
                    }
                    logger.info(nName+fName + ".jsonArray=" + jsonArray.toString());
                    return jsonArray;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public List<Command.Option> getListToOptions() {
                String fName = "[getListToOptions]";
                try {
                    List<Command.Option>list=new ArrayList<>();
                    for(int i=0;i<options.size()&&i<MAX_CHOICES_SIZE;i++){
                        list.add(options.get(0).toOption());
                    }
                    logger.info(nName+fName + ".list=" + list.size());
                    return list;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            private List<_SLASH.GENERALOPTION.CHOICE> choices =new ArrayList<>();
            public List<_SLASH.GENERALOPTION.CHOICE> getChoices() {
                String fName = "[getChoices]";
                try {
                    return choices;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isEmptyChoices() {
                String fName = "[isEmptyChoices]";
                try {
                    boolean result=choices.isEmpty();
                    logger.info(nName+fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public _SLASH.GENERALOPTION clearChoices() {
                String fName = "[clearChoices]";
                try {
                    choices =new ArrayList<>();
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION.CHOICE getChoiceAt(int index) {
                String fName = "[getChoiceAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    _SLASH.GENERALOPTION.CHOICE choice=choices.get(index);
                    logger.info(nName+fName + ".choice=" + choice.getName());
                    return choice;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION.CHOICE remChoiceAt(int index) {
                String fName = "[remChoiceAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    _SLASH.GENERALOPTION.CHOICE choice=choices.remove(index);
                    logger.info(nName+fName + ".choice=" + choice.getName());
                    return choice;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addChoice(_SLASH.GENERALOPTION.CHOICE input) {
                String fName = "[addChoice]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".input=" + input.getName());
                    choices.add(input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addChoice(int index, _SLASH.GENERALOPTION.CHOICE input) {
                String fName = "[addChoice]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getName());
                    choices.add(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setChoice(int index, _SLASH.GENERALOPTION.CHOICE input) {
                String fName = "[setChoice]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getName());
                    choices.set(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONArray getChoicesJson() {
                String fName = "[getChoicesJson]";
                try {
                    JSONArray jsonArray=new JSONArray();
                    for(_SLASH.GENERALOPTION.CHOICE choice:choices){
                        if(jsonArray.length()==MAX_CHOICES_SIZE){
                            logger.warn(fName + ".Reached choices max count");
                            break;
                        }
                        jsonArray.put(choice.getJson());
                    }
                    logger.info(nName+fName + ".jsonArray=" + jsonArray.toString());
                    return jsonArray;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONArray getChoicesJson4Build() {
                String fName = "[getChoicesJson4Build]";
                try {
                    JSONArray jsonArray=new JSONArray();
                    for(_SLASH.GENERALOPTION.CHOICE choice:choices){
                        if(jsonArray.length()==MAX_OPTIONS_SIZE){
                            logger.warn(fName + ".Reached options max count");
                            break;
                        }
                        jsonArray.put(choice.getJson4Build());
                    }
                    logger.info(nName+fName + ".jsonArray=" + jsonArray.toString());
                    return jsonArray;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public List<Command.Choice> getListToChoices() {
                String fName = "[getListToChoices]";
                try {
                    List<Command.Choice>list=new ArrayList<>();
                    for(int i=0;i<choices.size()&&i<MAX_CHOICES_SIZE;i++){
                        list.add(choices.get(0).toChoice());
                    }
                    logger.info(nName+fName + ".list=" + list.size());
                    return list;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Command.Choice getCommandChoiceAt(int index) {
                String fName = "[getCommandChoiceAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    _SLASH.GENERALOPTION.CHOICE choice=choices.get(index);
                    logger.info(nName+fName + ".choice=" + choice.getName());
                    return choice.toChoice();
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            private List<ChannelType> channelTypes =new ArrayList<>();
            public List<ChannelType> getChannelTypes() {
                String fName = "[getChannelTypes]";
                try {
                    return channelTypes;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONArray getChannelTypesJson() {
                String fName = "[getChannelTypesJson]";
                try {
                    JSONArray jsonArray=new JSONArray();
                    for(ChannelType channelType:channelTypes){
                        jsonArray.put(channelType.getId());
                    }
                    logger.info(nName+fName + ".jsonArray=" + jsonArray.toString());
                    return jsonArray;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isEmptyChannelTypes() {
                String fName = "[isEmptyChannelTypes]";
                try {
                    boolean result=channelTypes.isEmpty();
                    logger.info(nName+fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public _SLASH.GENERALOPTION clearChannelTypes() {
                String fName = "[clearChannelTypes]";
                try {
                    channelTypes =new ArrayList<>();
                    logger.info(nName+fName + "done");
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setChannelTypes(List<ChannelType> input) {
                String fName = "[setChannelTypes]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    channelTypes=input;
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public ChannelType getChannelTypeAt(int index) {
                String fName = "[getChannelTypeAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    ChannelType channelType=channelTypes.get(index);
                    logger.info(nName+fName + ".channelType=" + channelType.getId());
                    return channelType;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public ChannelType remChannelTypeAt(int index) {
                String fName = "[remChannelTypeAt]";
                try {
                    logger.info(nName+fName + ".index=" + index);
                    ChannelType channelType=channelTypes.remove(index);
                    logger.info(nName+fName + ".channelType=" + channelType.getId());
                    return channelType;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addChannelType(ChannelType input) {
                String fName = "[addChannelType]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".input=" + input.getId());
                    channelTypes.add(input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION addChannelType(int index, ChannelType input) {
                String fName = "[setChannelType]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getId());
                    channelTypes.add(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _SLASH.GENERALOPTION setChannelType(int index, ChannelType input) {
                String fName = "[setChannelType]";
                try {
                    if(input==null)throw  new Exception("Input can be null");
                    logger.info(nName+fName + ".index="+index+", input=" + input.getId());
                    channelTypes.set(index,input);
                    return this;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            public class CHOICE {
                Logger logger = Logger.getLogger(lcApplicationCommandBuild.class);
                String nName="CHOICE@";
                OptionType optionType=OptionType.UNKNOWN;
                private String name="";
                private String valueStr="";
                private double valueDouble=0;
                private long valueLong=0;
                public CHOICE() {
                }
                public CHOICE(OptionType type, JSONObject jsonObject) {
                    set(type,jsonObject);
                }
                public _SLASH.GENERALOPTION.CHOICE set(OptionType type, JSONObject jsonObject) {
                    String fName="[set]";
                    try {
                        logger.info(fName+".jsonObject="+jsonObject.toString());
                        optionType=type;
                        String key;
                        key=llCommonKeys.ApplicationCommandOptionChoiceStructure.Name;if (jsonObject.has(key)) name=jsonObject.optString(key,"");
                        key=llCommonKeys.ApplicationCommandOptionChoiceStructure.Value;
                        if (jsonObject.has(key)){
                            switch (type){
                                case INTEGER:
                                    valueLong=jsonObject.optLong(key,0);
                                    valueDouble=jsonObject.optDouble(key,0);
                                    break;
                                case STRING:
                                    valueStr=jsonObject.optString(key,"");
                                    break;
                            }
                        }

                        return this;
                    }catch (Exception e){
                        logger.error(fName+".exception=" + e);
                        logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getName() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName + ".value=" + name);
                        if(name==null){
                            logger.info(nName+fName+"isNull so send blank");
                            return "";
                        }
                        return name;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getValueStr() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName + ".value=" + valueStr);
                        if(valueStr==null){
                            logger.info(nName+fName+"isNull so send blank");
                            return "";
                        }
                        return valueStr;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public long getValueLong() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName + ".value=" + valueLong);
                        return valueLong;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return 0;
                    }
                }
                public double getValueDouble() {
                    String fName = "[getValueDouble]";
                    try {
                        logger.info(nName+fName + ".value=" + valueDouble);
                        return valueDouble;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return 0;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE setName(String input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName + ".input=" + input);
                        if(input==null)throw  new Exception("Input can be null");
                        name=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE clearValue() {
                    String fName = "[clearValue]";
                    try {
                        valueStr="";
                        valueDouble=0;valueLong=0;
                        return this;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE setValueStr(String input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName + ".input=" + input);
                        if(input==null)throw  new Exception("Input can be null");
                        clearValue();
                        valueStr=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE setValueLong(long input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName + ".input=" + input);
                        clearValue();
                        valueLong=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE setValueDouble(double input) {
                    String fName = "[setValueDouble]";
                    try {
                        logger.info(nName+fName + ".input=" + input);
                        clearValue();
                        valueDouble=input;
                        return this;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public JSONObject getJson() {
                    String fName = "[getJson]";
                    try {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Name,getName());
                        switch (optionType){
                            case INTEGER:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueLong());
                                break;
                            case NUMBER:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueDouble());
                                break;
                            case STRING:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueStr());
                                break;
                        }
                        logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                        return jsonObject;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public JSONObject getJson4Build() {
                    String fName = "[getJson4Build]";
                    try {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Name,getName());
                        switch (optionType){
                            case INTEGER:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueLong());
                                break;
                            case NUMBER:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueDouble());
                                break;
                            case STRING:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionChoiceStructure.Value,getValueStr());
                                break;
                        }
                        logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                        return jsonObject;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public Command.Choice toChoice() {
                    String fName = "[toChoice]";
                    try {
                        Command.Choice choice=null;
                        switch (optionType){
                            case INTEGER:
                                choice=new Command.Choice(getName(),getValueLong());
                                break;
                            case NUMBER:
                                choice=new Command.Choice(getName(),getValueDouble());
                                break;
                            case STRING:
                                choice=new Command.Choice(getName(),getValueStr());
                                break;
                        }
                        logger.info(nName+fName + ".choice.name=" + choice.getName());
                        return choice;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            public JSONObject getJson() {
                String fName = "[getJson]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Type,getTypeAsInt());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Name,getName());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Description,getDescription());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Required,isRequired());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.ChannelTypes,getChannelTypesJson());
                    switch (getType()){
                        case STRING:
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,getChoicesJson());
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,isAutocomplete());
                            break;
                        case NUMBER: case INTEGER:
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,getChoicesJson());
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,isAutocomplete());
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MaxValue,getMaxValue());
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MinValue,getMinValue());
                            break;
                        case SUB_COMMAND:case SUB_COMMAND_GROUP:
                            jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Options,getOptionsJson());
                            break;
                    }
                    logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONObject getJson4Build() {
                String fName = "[getJson4Build]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Type,getTypeAsInt());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Name,getName());
                    jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Description,getDescription());
                    if(isRequired())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Required,isRequired());
                    if(!isEmptyChannelTypes())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.ChannelTypes,getChannelTypesJson());
                    switch (getType()){
                        case STRING:
                            if(!isEmptyChoices())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,getChoicesJson4Build());
                            else  if(isAutocomplete())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,isAutocomplete());
                            break;
                        case NUMBER: case INTEGER:
                            if(!isEmptyChoices())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,getChoicesJson4Build());
                            else if(isAutocomplete())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,isAutocomplete());
                            if(hasMaxValue())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MaxValue,getMaxValue());
                            if(hasMinValue())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MinValue,getMinValue());
                            break;
                        case SUB_COMMAND:case SUB_COMMAND_GROUP:
                            if(!isEmptyOptions())jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Options,getOptionsJson4Build());
                            break;
                    }
                    logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isOption() {
                String fName = "[isOption]";
                try {
                    boolean result=false;
                    switch (optionType){
                        case NUMBER:case INTEGER:case STRING: case ROLE: case USER: case BOOLEAN: case CHANNEL: case MENTIONABLE:
                            result=true;
                            break;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            private _SLASH.GENERALOPTION getThis() {
                String fName = "[getThis]";
                try {
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

            //OPTION Option=new OPTION();
            protected class OPTION{
                String nName="OPTION@";
                private  OPTION(){

                }
                _SLASH.GENERALOPTION generaloption=null;
                private  OPTION(_SLASH.GENERALOPTION generaloption){
                    this.generaloption=generaloption;
                }
                public _SLASH.GENERALOPTION getGeneralOption() {
                    String fName = "[getGeneralOption]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getThis();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getName() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName+" do");
                        return  generaloption.getName();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getDescription() {
                    String fName = "[getDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getDescription();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setName(String input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setName(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setDescription(String input) {
                    String fName = "[setDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setDescription(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public int getTypeAsInt() {
                    String fName = "[getTypeAsInt]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getType().getKey();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return OptionType.UNKNOWN.getKey();
                    }
                }
                public OptionType getType() {
                    String fName = "[getType]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getType();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return OptionType.UNKNOWN;
                    }
                }
                public boolean isRequired() {
                    String fName = "[isRequired]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.isRequired();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setRequired(boolean input) {
                    String fName = "[setRequired]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setRequired(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public double getMinValue() {
                    String fName = "[getMinValue]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getMinValue();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public double getMaxValue() {
                    String fName = "[getMaxValue]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getMaxValue();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return -1;
                    }
                }
                public boolean hasMinValue() {
                    String fName = "[hasMinValue]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.hasMinValue();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public boolean hasMaxValue() {
                    String fName = "[hasMaxValue]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.hasMaxValue();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION clearValues() {
                    String fName = "[clearChoices]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.clearValues()!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setMinValue(int input) {
                    String fName = "[setMinValue]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setMinValue(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setMaxValue(int input) {
                    String fName = "[setMaxValue]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setMaxValue(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setMinValue(double input) {
                    String fName = "[setMinValue]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setMinValue(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setMaxValue(double input) {
                    String fName = "[setMaxValue]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setMaxValue(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public boolean isAutocomplete() {
                    String fName = "[isAutocomplete]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.isAutocomplete();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setAutocomplete(boolean input) {
                    String fName = "[setAutocomplete]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setAutocomplete(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public boolean isEmptyChoices() {
                    String fName = "[isEmptyChoices]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.isEmptyChoices();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return true;
                    }
                }
                public List<_SLASH.GENERALOPTION.CHOICE> getChoices() {
                    String fName = "[getChoices]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getChoices();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE getChoiceAt(int index) {
                    String fName = "[getChoiceAt]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getChoiceAt(index);
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.CHOICE remChoiceAt(int index) {
                    String fName = "[remChoiceAt]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.remChoiceAt(index);
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION addChoice(_SLASH.GENERALOPTION.CHOICE input) {
                    String fName = "[addChoice]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.addChoice(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION addChoice(int index, _SLASH.GENERALOPTION.CHOICE input) {
                    String fName = "[addChoice]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.addChoice(index,input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setChoice(int index, _SLASH.GENERALOPTION.CHOICE input) {
                    String fName = "[setChoice]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setChoice(index,input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION clearChoices() {
                    String fName = "[clearChoices]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.clearChoices()!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public List<ChannelType> getChannelTypes() {
                    String fName = "[getChannelTypes]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getChannelTypes();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public boolean isEmptyChannelTypes() {
                    String fName = "[isEmptyChannelTypes]";
                    try {
                        logger.info(nName+fName+" do");
                        return  generaloption.isEmptyChannelTypes();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return true;
                    }
                }
                public _SLASH.GENERALOPTION.OPTION setChannelTypes(List<ChannelType> input) {
                    String fName = "[setChannelTypes]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setChannelTypes(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public JSONObject getJson() {
                    String fName = "[getJson]";
                    try {
                        logger.info(nName+fName+" do");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Type,generaloption.getTypeAsInt());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Name,generaloption.getName());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Description,generaloption.getDescription());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Required,generaloption.isRequired());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.ChannelTypes,generaloption.getChannelTypesJson());
                        switch (getType()){
                            case STRING:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,generaloption.getChoicesJson());
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,generaloption.isAutocomplete());
                                break;
                            case NUMBER: case INTEGER:
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Choices,generaloption.getChoicesJson());
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Autocomplete,generaloption.isAutocomplete());
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MaxValue,generaloption.getMaxValue());
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.MinValue,generaloption.getMinValue());
                                break;
                        }
                        logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                        return jsonObject;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public Command.Option toOption() {
                    String fName = "[toOption]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.toOption();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            public GENERALOPTION.OPTION Option() {
                String fName = "[SubCommand]";
                try {
                    logger.info(nName+fName+" do");
                    return  new _SLASH.GENERALOPTION.OPTION(this);
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            //SUBCOMMANDGROUP SubcommandGroup=new SUBCOMMANDGROUP();
            protected class SUBCOMMANDGROUP{
                String nName="SUBCOMMANDGROUP@";
                private  SUBCOMMANDGROUP(){

                }
                _SLASH.GENERALOPTION generaloption=null;
                private   SUBCOMMANDGROUP(_SLASH.GENERALOPTION generaloption){
                    this.generaloption=generaloption;
                }
                public _SLASH.GENERALOPTION getGeneralOption() {
                    String fName = "[getGeneralOption]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getThis();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public List<_SLASH.GENERALOPTION> getGeneralOptions() {
                    String fName = "[getGeneralOptions]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.getType()!=OptionType.SUB_COMMAND_GROUP){
                            logger.warn(nName+fName + ".invalid type");
                            return  new ArrayList<>();
                        }
                        return generaloption.getOptions();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public List<_SLASH.GENERALOPTION.SUBCOMMAND> getOptions() {
                    String fName = "[getOptions]";
                    try {
                        logger.info(nName+fName+" do");
                        logger.info(nName+fName + ".list=" + options.size());
                        List<_SLASH.GENERALOPTION.SUBCOMMAND>list=new ArrayList<>();
                        List<_SLASH.GENERALOPTION>generaloptionList=getGeneralOptions();
                        for(_SLASH.GENERALOPTION generaloption:generaloptionList){
                            if(generaloption.getType()==OptionType.SUB_COMMAND){
                                list.add(generaloption.SubCommand());
                            }
                        }
                        return list;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getName() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName+" do");
                        return  generaloption.getName();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getDescription() {
                    String fName = "[getDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getDescription();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.SUBCOMMANDGROUP setName(String input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setName(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.SUBCOMMANDGROUP setDescription(String input) {
                    String fName = "[setDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setDescription(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public JSONObject getJson() {
                    String fName = "[getJson]";
                    try {
                        logger.info(nName+fName+" do");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Type,getTypeAsInt());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Name,getName());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Description,getDescription());
                        if (getType() == OptionType.SUB_COMMAND_GROUP) {
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Options, getOptionsJson());
                        }
                        logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                        return jsonObject;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public Command.SubcommandGroup toSubcommandGroup() {
                    String fName = "[toSubcommandGroup]";
                    try {
                        logger.info(nName+fName+" do");
                        return  generaloption.toSubcommandGroup();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            public GENERALOPTION.SUBCOMMANDGROUP SubCommandGroup() {
                String fName = "[SubCommandGroup]";
                try {
                    logger.info(nName+fName+" do");
                    return  new _SLASH.GENERALOPTION.SUBCOMMANDGROUP(this);
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            //SUBCOMMAND Subcommand=new SUBCOMMAND();
            protected class SUBCOMMAND{
                String nName="SUBCOMMAND@";
                private   SUBCOMMAND(){

                }
                _SLASH.GENERALOPTION generaloption=null;
                private   SUBCOMMAND(_SLASH.GENERALOPTION generaloption){
                    this.generaloption=generaloption;
                }
                public _SLASH.GENERALOPTION getGeneralOption() {
                    String fName = "[getGeneralOption]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getThis();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public List<_SLASH.GENERALOPTION> getGeneralOptions() {
                    String fName = "[getGeneralOptions]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getOptions();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public List<_SLASH.GENERALOPTION.OPTION> getOptions() {
                    String fName = "[getOptions]";
                    try {
                        logger.info(nName+fName+" do");
                        logger.info(nName+fName + ".list=" + options.size());
                        List<_SLASH.GENERALOPTION.OPTION>list=new ArrayList<>();
                        List<_SLASH.GENERALOPTION>generaloptionList=getGeneralOptions();
                        for(_SLASH.GENERALOPTION generaloption:generaloptionList){
                            switch (generaloption.getType()){
                                case STRING: case INTEGER: case NUMBER: case BOOLEAN: case USER: case ROLE: case CHANNEL: case MENTIONABLE:
                                    list.add(generaloption.Option());
                            }
                            if(generaloption.getType()==OptionType.SUB_COMMAND){

                            }
                        }
                        return list;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getName() {
                    String fName = "[getName]";
                    try {
                        logger.info(nName+fName+" do");
                        return  generaloption.getName();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public String getDescription() {
                    String fName = "[getDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.getDescription();
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.SUBCOMMAND setName(String input) {
                    String fName = "[setName]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setName(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public _SLASH.GENERALOPTION.SUBCOMMAND setDescription(String input) {
                    String fName = "[setDescription]";
                    try {
                        logger.info(nName+fName+" do");
                        if(generaloption.setDescription(input)!=null){
                            return this;
                        }else{
                            return null;
                        }
                    } catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public JSONObject getJson() {
                    String fName = "[getJson]";
                    try {
                        logger.info(nName+fName+" do");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Type,getTypeAsInt());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Name,getName());
                        jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Description,getDescription());
                        if (getType() == OptionType.SUB_COMMAND) {
                                jsonObject.put(llCommonKeys.ApplicationCommandOptionStructure.Options, getOptionsJson());
                        }
                        logger.info(nName+fName + ".jsonObject=" + jsonObject.toString());
                        return jsonObject;
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
                public Command.Subcommand toSubcommand() {
                    String fName = "[toSubcommand]";
                    try {
                        logger.info(nName+fName+" do");
                        return generaloption.toSubcommand();
                    } catch (Exception e) {
                        logger.error(nName+fName + ".exception=" + e);
                        logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            public GENERALOPTION.SUBCOMMAND SubCommand() {
                String fName = "[SubCommand]";
                try {
                    logger.info(nName+fName+" do");
                    return  new _SLASH.GENERALOPTION.SUBCOMMAND(this);
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isOptionWithMention() {
                String fName = "[isOptionWithMention]";
                try {
                    boolean result=false;
                    switch (optionType){
                        case ROLE: case USER: case CHANNEL: case MENTIONABLE:
                            result=true;
                            break;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isOptionWithChoices() {
                String fName = "[isOptionWithChoices]";
                try {
                    boolean result=false;
                    switch (optionType){
                        case NUMBER:case INTEGER:case STRING:
                            result=true;
                            break;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isSubOption() {
                String fName = "[isSubOption]";
                try {
                    boolean result=false;
                    switch (optionType){
                        case SUB_COMMAND:case SUB_COMMAND_GROUP:
                            result=true;
                            break;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isSubCommandGroupOption() {
                String fName = "[isSubCommandGroupOption]";
                try {
                    boolean result=false;
                    if(optionType==OptionType.SUB_COMMAND_GROUP){
                        result=true;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isSubCommandOption() {
                String fName = "[isSubCommandOption]";
                try {
                    boolean result=false;
                    if(optionType==OptionType.SUB_COMMAND){
                        result=true;
                    }
                    logger.info(nName+fName + ".result="+result);
                    return result;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public Command.SubcommandGroup toSubcommandGroup() {
                String fName = "[toSubcommandGroup]";
                try {
                    Command.SubcommandGroup option=null;
                    if (optionType == OptionType.SUB_COMMAND_GROUP) {
                        option = new Command.SubcommandGroup(DataObject.fromJson(getJson4Build().toString()));
                    } else {
                        logger.info(nName + fName + ".invalid type =" + optionType.name());
                    }
                    logger.info(nName+fName + ".option.name=" + option.getName());
                    return option;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Command.Subcommand toSubcommand() {
                String fName = "[toSubcommand]";
                try {
                    Command.Subcommand option=null;
                    if (optionType == OptionType.SUB_COMMAND) {
                        option = new Command.Subcommand(DataObject.fromJson(getJson4Build().toString()));
                    } else {
                        logger.info(nName + fName + ".invalid type =" + optionType.name());
                    }
                    logger.info(nName+fName + ".option.name=" + option.getName());
                    return option;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Command.Option toOption() {
                String fName = "[toOption]";
                try {
                    Command.Option option=null;
                    switch (optionType){
                        case NUMBER:case STRING: case INTEGER:
                            option= new Command.Option(DataObject.fromJson(getJson4Build().toString()));
                            break;
                        default:
                            logger.info(nName+fName + ".invalid type ="+optionType.name());
                    }
                    logger.info(nName+fName + ".option.name=" + option.getName());
                    return option;
                } catch (Exception e) {
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }


        }
        public _SLASH set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if(jsonObject==null)throw  new Exception("Json is null!");
                if(jsonObject.isEmpty())throw  new Exception("Json is empty!");
                logger.info(cName+fName + ".jsonObject=" + jsonObject.toString());
                if(!isEmptyOptions()){
                    options.clear();
                    logger.info(cName+fName + ".cleared old options");
                }
                String key="";
                key= llCommonKeys.ApplicationCommandStructure.Description;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    setDescription(jsonObject.optString(key,""));
                }
                key= llCommonKeys.ApplicationCommandStructure.Options;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    if(setOptions(jsonObject.getJSONArray(key))==null){
                        throw  new Exception("No options provided!");
                    }
                }
                return  this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public String getDescription() {
            String fName = "[getDescription]";
            try {
                logger.info(fName + ".value=" + description);
                if(description==null){
                    logger.info(fName+"isNull so send blank");
                    return "";
                }
                return description;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH setDescription(String input) {
            String fName = "[setDescription]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                if(input.length()>MAX_DESCRIPTION_LENGTH)throw  new Exception("Str.length above "+MAX_NAME_LENGHT+"!");
                description=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH setOptions(JSONArray jsonArray) {
            String fName = "[setOptions]";
            try {
                if(jsonArray==null)throw  new Exception("JsonOptions is null!");
                if(jsonArray.isEmpty())throw  new Exception("JsonOptions is empty!");
                logger.info(cName+fName+"jsonArray.size="+jsonArray.length());
                logger.info(cName+fName+"jsonArray="+jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    this.options.add(new _SLASH.GENERALOPTION(jsonArray.getJSONObject(i)));
                }
                return  this;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<_SLASH.GENERALOPTION> getOptions() {
            String fName = "[getOptions]";
            try {
                return  options;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEmptyOptions() {
            String fName = "[isEmptyOptions]";
            try {
                boolean result=options.isEmpty();
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public _SLASH.GENERALOPTION getOptionAt(int index) {
            String fName = "[getOptionAt]";
            try {
                logger.info(fName + ".index=" + index);
                _SLASH.GENERALOPTION choice=options.get(index);
                logger.info(fName + ".option=" + choice.getName());
                return choice;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH.GENERALOPTION remOptionAt(int index) {
            String fName = "[remOptionAt]";
            try {
                logger.info(fName + ".index=" + index);
                _SLASH.GENERALOPTION choice=options.remove(index);
                logger.info(fName + ".option=" + choice.getName());
                return choice;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH addOption(_SLASH.GENERALOPTION input) {
            String fName = "[addOption]";
            try {
                if(input==null)throw  new Exception("Input can be null");
                logger.info(fName + ".input=" + input.getName());
                options.add(input);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH addOption(int index, _SLASH.GENERALOPTION input) {
            String fName = "[addOption]";
            try {
                if(input==null)throw  new Exception("Input can be null");
                logger.info(fName + ".index="+index+", input=" + input.getName());
                options.add(index,input);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _SLASH setOption(int index, _SLASH.GENERALOPTION input) {
            String fName = "[setOption]";
            try {
                if(input==null)throw  new Exception("Input can be null");
                logger.info(fName + ".index="+index+", input=" + input.getName());
                options.set(index,input);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray getOptionsJson() {
            String fName = "[getOptionsJson]";
            try {
                JSONArray jsonArray=new JSONArray();
                for(_SLASH.GENERALOPTION option:options){
                    jsonArray.put(option.getJson());
                }
                logger.info(fName + ".jsonArray=" + jsonArray.toString());
                return jsonArray;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray getOptionsJson4Build() {
            String fName = "[getOptionsJson4Build]";
            try {
                logger.info(cName+fName + ".init");
                JSONArray jsonArray=new JSONArray();
                for(_SLASH.GENERALOPTION option:options){
                    if(jsonArray.length()==MAX_OPTIONS_SIZE){
                        logger.warn(cName+fName + ".Reached options max count");
                        break;
                    }
                    jsonArray.put(option.getJson4Build());
                }
                logger.info(cName+fName + ".jsonArray=" + jsonArray.toString());
                return jsonArray;
            } catch (Exception e) {
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<Command.Option> getListToOptions() {
            String fName = "[getListToOptions]";
            try {
                List<Command.Option>list=new ArrayList<>();
                for(int i=0;i<options.size()&&i<MAX_OPTIONS_SIZE;i++){
                    list.add(options.get(0).toOption());
                }
                logger.info(fName + ".list=" + list.size());
                return list;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName = "[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Description,getDescription());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Options,getOptionsJson());
                logger.info(fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public JSONObject getJson4Build() {
            String fName = "[getJson4Build]";
            try {
                logger.info(cName+fName+".init");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Description,getDescription());
                if(!isEmptyOptions())jsonObject.put(llCommonKeys.ApplicationCommandStructure.Options,getOptionsJson4Build());
                if(!isDefaultPermission())jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
                logger.info(cName+fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
    }

    protected  class PERMISSION{
        public PERMISSION(){}
        public PERMISSION(JSONObject jsonObject){set(jsonObject);}
        String cName="PERMISSION@";
        String id="";
        llCommonVariables.ApplicationCommandPermissionType type=llCommonVariables.ApplicationCommandPermissionType.Unknown;
        boolean permission= false;
        public PERMISSION set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if(jsonObject==null)throw  new Exception("Json is null!");
                if(jsonObject.isEmpty())throw  new Exception("Json is empty!");
                String key="";
                key=llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Id;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    setId(jsonObject.optString(key,""));
                }else{
                    throw  new Exception("No permision id provided!");
                }
                key=llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Type;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    setType(jsonObject.optInt(key,0));
                }else{
                    throw  new Exception("No permision id provided!");
                }
                key=llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Permission;if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    setFlag(jsonObject.optBoolean(key,false));
                }else{
                    throw  new Exception("No permision id provided!");
                }
                logger.info(fName+"none failed");
                return  this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName = "[getId]";
            try {
                String value=id;
                logger.info(fName + ".value=" + value);
                if(value==null){
                    logger.info(fName+"isNull so send blank");
                    return "";
                }
                return value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public PERMISSION setId(String input) {
            String fName = "[setId]";
            try {
                logger.info(fName + ".input=" + input);
                if(input==null)throw  new Exception("Cant be null!");
                if(input.length()<1)throw  new Exception("Str.length bellow 1!");
                id=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean getFlag() {
            String fName = "[getFlag]";
            try {
                boolean value=permission;
                logger.info(fName + ".value=" + value);
                return value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public PERMISSION setFlag(boolean input) {
            String fName = "[setFLag]";
            try {
                logger.info(fName + ".input=" + input);
                permission=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public llCommonVariables.ApplicationCommandPermissionType getType() {
            String fName = "[getType]";
            try {
                if(type==null){
                    logger.info(fName+"isNull so send blank");
                    return llCommonVariables.ApplicationCommandPermissionType.Unknown;
                }
                logger.info(fName + ".value=" +type.name());
                return type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return llCommonVariables.ApplicationCommandPermissionType.Unknown;
            }
        }
        public int getTypeAsInt() {
            String fName = "[getType]";
            try {
                int value=getType().getValue();
                logger.info(fName + ".value=" +value);
                return  value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public PERMISSION setType(int input) {
            String fName = "[setType]";
            try {
                logger.info(fName + ".input=" + input);
                llCommonVariables.ApplicationCommandPermissionType per= llCommonVariables.ApplicationCommandPermissionType.valueByCode(input);
                if(per==null)per= llCommonVariables.ApplicationCommandPermissionType.Unknown;
                type=per;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public PERMISSION setType( llCommonVariables.ApplicationCommandPermissionType input) {
            String fName = "[setType]";
            try {
                if(input==null)throw  new Exception("Cant be null!");
                logger.info(fName + ".input=" + input.getValue());
                type=input;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName = "[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Id,getId());
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Type,getType());
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Permission,getFlag());
                logger.info(fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public JSONObject getJson4Build() {
            String fName = "[getJson4Build]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Id,getId());
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Type,getType());
                jsonObject.put(llCommonKeys.GuildApplicationCommandPermissionsStructure.ApplicationCommandPermissionsStructure.Permission,getFlag());
                logger.info(fName+".jsonObject="+  jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
    }

    public String getName() {
        String fName = "[getName]";
        try {
            logger.info(fName + ".value=" + name);
            if(name==null){
                logger.info(fName+"isNull so send blank");
                return "";
            }
            return name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isDefaultPermission() {
        String fName = "[isDefaultPermission]";
        try {
            logger.info(fName + ".value=" + default_permission);
            return default_permission;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationCCommonEditor setName(String input) {
        String fName = "[setName]";
        try {
            logger.info(fName + ".input=" + input);
            if(input==null)throw  new Exception("Cant be null!");
            if(input.length()<1)throw  new Exception("Str.length bellow 1!");
            if(input.length()>MAX_NAME_LENGHT)throw  new Exception("Str.length above "+MAX_NAME_LENGHT+"!");
            name=input;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor setDefaultPermission(boolean input) {
        String fName = "[setDefaultPermission]";
        try {
            logger.info(fName + ".input=" + input);
            default_permission=input;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            logger.info(fName + ".guild=" + guild.getId());
            return guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isGuild() {
        String fName = "[isGuild]";
        try {
            if(guild!=null){
                logger.info(fName + ".return 1 true");
                return  true;
            }
            String str=getGuildId();
            if(str!=null&&!str.isBlank()){
                logger.info(fName + ".return 2 true");
                return  true;
            }
            logger.info(fName + ".return 3 false");
            return  false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationCCommonEditor setGuild(Guild guild) {
        String fName = "[setGuild]";
        try {
            if(guild==null)throw  new Exception("Input cant be NULL");
            this.guild=guild;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor clearGuild() {
        String fName = "[clearGuild]";
        try {
            this.guild=null;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJson() {
        String fName = "[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
            jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
            jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
            logger.info(fName+".jsonObject="+  jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getJson4Build() {
        String fName = "[getJson4Build]";
        try {
            logger.info(fName+".init");
            JSONObject jsonObject=new JSONObject();
            if(getType()== liApplicationCommand.lcApplicationTypes.CHAT_INPUT){
               jsonObject=SLASH.getJson4Build();
            }else{
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                if(!isDefaultPermission())jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
            }
            logger.info(fName+".jsonObject="+  jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    
    public String getId() {
        String fName = "[getId]";
        try {
            String value=id;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public Long getIdAsLong() {
        String fName = "[getIdAsLong]";
        try {
            long value=Long.valueOf(id);
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getApplicationId() {
        String fName = "[getApplicationId]";
        try {
            String value=application_id;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getGuildId() {
        String fName = "[getGuildId]";
        try {
            String value=guild_id;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getVersion() {
        String fName = "[getVersion]";
        try {
            String value=version;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    private HttpResponse<JsonNode> postResponse=new FailedResponse<>(new Exception("{'error':'Nothing provided'"));
    public HttpResponse<JsonNode> getPostResponse() {
        String fName = "[getPostResponse]";
        try {
            return postResponse;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private boolean patchGuild(JSONObject jsonObject){
        String fName="[patchGuild]";
        try {
            String guild_id="";
            if(getGuild()!=null){
                Guild guild=getGuild();
                logger.info(fName+".guild="+  guild.getName()+"("+guild.getId()+")");
                guild_id=guild.getId();
            }
            else if(getGuildId()!=null&&!getGuildId().isBlank()){
                guild_id=getGuildId();
            }

            String url = lsDiscordApi.ApplicationCommand.Guild.lsGetOrEditCommand(global.configfile.getBot().getId(),guild_id,getId());
            return patch(jsonObject,url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean patchGlobal(JSONObject jsonObject){
        String fName="[patchGlobal]";
        try {
            String url = lsDiscordApi.ApplicationCommand.Global.lsGetOrEditCommand(global.configfile.getBot().getId(),getId());
            return patch(jsonObject,url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean patch(JSONObject jsonObject,String url){
        String fName="[patch:Url]";
        try {
            logger.info(fName+".url="+url+"\njson="+jsonObject.toString());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.patch(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject)
                    .asJson();
            logger.info(fName+".status ="+postResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=postResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(postResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".updated");
                return setAll(postResponse.getBody().getObject())!=null;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean patch(){
        String fName="[patch]";
        try {
            logger.info(fName+".default");
            JSONObject jsonObject=getJson4Build();
            return patch(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean patch(JSONObject jsonObject){
        String fName="[patch]";
        try {
            logger.info(fName+".selection");
            if(getGuild()!=null){
                return patchGuild(jsonObject);
            }
            else if(getGuildId()!=null&&!getGuildId().isBlank()){
                return patchGuild(jsonObject);
            }else{
                return patchGlobal(jsonObject);
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean deleteGuild(){
        String fName="[deleteGuild]";
        try {
            String guild_id="";
            if(getGuild()!=null){
                Guild guild=getGuild();
                logger.info(fName+".guild="+  guild.getName()+"("+guild.getId()+")");
                guild_id=guild.getId();
            }
            else if(getGuildId()!=null&&!getGuildId().isBlank()){
                guild_id=getGuildId();
            }

            String url = lsDiscordApi.ApplicationCommand.Guild.lsGetOrEditCommand(global.configfile.getBot().getId(),guild_id,getId());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.delete(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+postResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=postResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(postResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".created");
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean deleteGlobal(){
        String fName="[deleteGlobal]";
        try {
            String url = lsDiscordApi.ApplicationCommand.Global.lsGetOrEditCommand(global.configfile.getBot().getId(),getId());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.delete(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+postResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=postResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(postResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".created");
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean delete(){
        String fName="[delete]";
        try {
            logger.info(fName+".selection");
            if(getGuild()!=null){
                return deleteGuild();
            }
            else if(getGuildId()!=null&&!getGuildId().isBlank()){
                return deleteGuild();
            }else{
                return deleteGlobal();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Command retrieveCommand(){
        String fName="[retrieveCommand]";
        try {
            logger.info(fName+".selection");
            Command command=null;
            if(getGuild()!=null) {
                command = getGuild().retrieveCommandById(getId()).complete();
            }else{
                command = jda.retrieveCommandById(getId()).complete();
            }
            logger.info(fName+".command="+command.getName());
            return  command;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
