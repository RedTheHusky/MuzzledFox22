package models.lc.interaction.applicationcommand;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class lcApplicationCommandBuild {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper global;

    public lcApplicationCommandBuild(lcGlobalHelper global) {
        String fName = "[build]";
        logger.info(fName + ".blank");
        this.global=global;
    }

    public lcApplicationCommandBuild(lcGlobalHelper global,JSONObject jsonObject) {
        String fName = "[build]";
        try {
            logger.info(fName + ".creating");
            this.global=global;
            set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcApplicationCommandBuild(lcGlobalHelper global,JSONObject jsonObject, Guild guild) {
        String fName = "[build]";
        try {
            logger.info(fName + ".creating");
            this.global=global;
            set(jsonObject);
            this.guild=guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    Guild guild=null;
    public lcApplicationCommandBuild clear() {
        String fName = "[clear]";
        try {
            type = lcApplicationTypes.INVALID;
            default_permission = true;guild=null;
            String name = "", description = "";
            logger.info(fName + ".cleared");
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public enum lcApplicationTypes {
        INVALID(-1, ""),
        CHAT_INPUT(1, "chat_input"),
        USER(2, "user"),
        MESSAGE(3, "message");
        private String string;
        private int code;

        private lcApplicationTypes(int code, String string) {
            this.code = code;
            this.string = string;
        }

        public static lcApplicationTypes valueByCode(int code) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }

        public static lcApplicationTypes valueByString(String string) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.string.equals(string)) {
                    return status;
                }
            }
            return null;
        }

        public String getString() {
            return this.string;
        }

        public int getCode() {
            return this.code;
        }

        static {
            lcApplicationTypes[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                lcApplicationTypes s = var0[var2];
            }

        }

        public static String getString(iDiaperInteractive.DIAPERTYPE level) {
            String fName = "[getString]";
            Logger logger = Logger.getLogger(iDiaperInteractive.class);
            try {
                return level.getString();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }


    }
    private lcApplicationTypes type = lcApplicationTypes.INVALID;
    private String name = "", description = "";
    boolean default_permission = true;

    private boolean set(JSONObject jsonObject) {
        String fName = "[set]";
        try {
            if(!setApplication(jsonObject)){
                logger.info(fName+"setApplication:failed");
                return false;
            }
            if(!setSubclass(jsonObject)){
                logger.info(fName+"setSubclass:failed");
                return false;
            }
            logger.info(fName+"none failed");
            return  true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setSubclass(JSONObject jsonObject) {
        String fName = "[setSubclass]";
        try {
            switch (type){
                case CHAT_INPUT:
                    SLASH.set(jsonObject);
                case MESSAGE:

                case USER:

            }
            return  true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setApplication(JSONObject jsonObject) {
        String fName = "[setApplication]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            String key="";
            key=llCommonKeys.ApplicationCommandStructure.Type;if (jsonObject.has(key)) type = lcApplicationTypes.valueByCode(jsonObject.optInt(key,-1));
            key=llCommonKeys.ApplicationCommandStructure.Name;if (jsonObject.has(key)) name = jsonObject.optString(key,"");
            key=llCommonKeys.ApplicationCommandStructure.Description;if (jsonObject.has(key)) description = jsonObject.optString(key,"");
            key=llCommonKeys.ApplicationCommandStructure.DefaultPermission;if (jsonObject.has(key)) default_permission = jsonObject.optBoolean(key,false);
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public lcApplicationTypes getType() {
        String fName = "[getType]";
        try {
            logger.info(fName + ".value=" + type.name());
            return type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return lcApplicationTypes.INVALID;
        }
    }
    public int getTypeAsInt() {
        String fName = "[getTypeAsInt]";
        try {
            logger.info(fName + ".value=" + type);
            return type.getCode();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
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
    public lcApplicationCommandBuild setName(String input) {
        String fName = "[setName]";
        try {
            logger.info(fName + ".input=" + input);
            if(input==null)throw  new Exception("Cant be null!");
            if(input.length()<1)throw  new Exception("Str.length bellow 1!");
            if(input.length()>32)throw  new Exception("Str.length bellow 32!");
            name=input;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCommandBuild setDescription(String input) {
        String fName = "[setDescription]";
        try {
            logger.info(fName + ".input=" + input);
            if(input==null)throw  new Exception("Cant be null!");
            if(input.length()<1)throw  new Exception("Str.length bellow 1!");
            if(input.length()>100)throw  new Exception("Str.length bellow 32!");
            description=input;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCommandBuild setDefaultPermission(boolean input) {
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
            return guild!=null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationCommandBuild setGuild(Guild guild) {
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
    public lcApplicationCommandBuild clearGuild() {
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

    public boolean isSlashCommand() {
        String fName = "[isSlashCommand]";
        try {
            if (getType() == lcApplicationTypes.CHAT_INPUT) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isUserCommand() {
        String fName = "[isUserCommand]";
        try {
            if (getType() == lcApplicationTypes.USER) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isMessageCommand() {
        String fName = "[isMessageCommand]";
        try {
            if (getType() == lcApplicationTypes.MESSAGE) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcApplicationCommandBuild getApplication() {
        String fName="[getApplication]";
        try {
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public final  int MAX_NAME_LENGHT= OptionData.MAX_NAME_LENGTH,MAX_DESCRIPTION_LENGTH= OptionData.MAX_DESCRIPTION_LENGTH;

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
            public _SLASH.GENERALOPTION.OPTION Option() {
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
            public _SLASH.GENERALOPTION.SUBCOMMANDGROUP SubCommandGroup() {
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
            public _SLASH.GENERALOPTION.SUBCOMMAND SubCommand() {
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

    private HttpResponse<JsonNode> postResponse=null;
    public JSONObject getJson() {
        String fName = "[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(getType()== lcApplicationTypes.CHAT_INPUT){
                jsonObject=SLASH.getJson();
            }else{
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
            }
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
            if(getType()== lcApplicationTypes.CHAT_INPUT){
                jsonObject=SLASH.getJson4Build();
            }else{
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
                jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
                if(!isDefaultPermission())jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
            }
            jsonObject.put(llCommonKeys.ApplicationCommandStructure.Type,getTypeAsInt());
            jsonObject.put(llCommonKeys.ApplicationCommandStructure.Name,getName());
            if(!getDescription().isBlank())jsonObject.put(llCommonKeys.ApplicationCommandStructure.Description,getDescription());
            if(!isDefaultPermission())jsonObject.put(llCommonKeys.ApplicationCommandStructure.DefaultPermission,isDefaultPermission());
            logger.info(fName+".jsonObject="+  jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    private boolean postGuild(){
        String fName="[postGuild]";
        try {
            logger.info(fName+".guild="+  guild.getName()+"("+guild.getId()+")");
            String url = "https://discord.com/api/v8/applications/"+ global.configfile.getBot().getId()+"/guilds/"+ guild.getId()+"/commands";
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.post(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(getJson4Build())
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
    private boolean postGlobal(){
        String fName="[postGlobal]";
        try {
            String url = "https://discord.com/api/v8/applications/"+ global.configfile.getBot().getId()+"/commands";
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.post(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(getJson4Build())
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
    public boolean post(){
        String fName="[post]";
        try {
            return postGuild();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}

