package models.lc.interaction.messagecomponent;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsNumbersUsefullFunctions;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class lcMessageBuildComponent implements lcMessageComponent.CommonKeys{
    Logger logger = Logger.getLogger(getClass());
    public lcMessageBuildComponent(){
        String fName="[build]";
        logger.info(fName+".blank");
    }
    public lcMessageBuildComponent(JSONObject jsonObject){
        String fName="[build]";
        try {
            logger.info(fName+".creating");
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageBuildComponent clear(){
        String fName="[clear]";
        try {
            type=Component.Type.UNKNOWN;
            actionRow =new ActionRow();
            button=new Button();
            selectMenu=new SelectMenu();
            logger.info(fName+".cleared");
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private Component.Type type=Component.Type.UNKNOWN; int maxPerRow=0;
    private ActionRow actionRow =new ActionRow();
    private Button button=new Button();
    private SelectMenu selectMenu=new SelectMenu();
    public lcMessageBuildComponent set( JSONObject jsonObject){
        String fName="[set]";
        try {
            clear();
            logger.info(fName+".jsonObject="+jsonObject.toString());
            if(jsonObject.has(keyType))type=getComponentType(jsonObject.optInt(keyType));
            if(isButton()){
                button=new Button(jsonObject);
            }else
            if(isContainer()){
                actionRow =new ActionRow(jsonObject);
            }else
            if(isSelectMenu()){
                selectMenu=new SelectMenu(jsonObject);
            }
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Component.Type getType(){
        String fName="[getType]";
        try {
            logger.info(fName+".value="+type.name());
            return type;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return Component.Type.UNKNOWN;
        }
    }
    public int getTypeAsInt(){
        String fName="[getTypeAsInt]";
        try {
            logger.info(fName+".value="+type);
            return  getComponentType(type);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isContainer(){
        String fName="[isContainer]";
        try {
            if(getType()== Component.Type.ACTION_ROW){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isButton(){
        String fName="[isButton]";
        try {
            if(getType()== Component.Type.BUTTON){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isSelectMenu(){
        String fName="[isSelectMenu]";
        try {
            if(getType()== Component.Type.SELECTION_MENU){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isInvalid(){
        String fName="[isInvalid]";
        try {
            if(getType()== Component.Type.UNKNOWN){
                return true;
            }
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public static class ActionRow {
        Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
        String nName="ActionRow@";
        protected ActionRow(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        List<lcMessageBuildComponent>components=new ArrayList<>();Component.Type componentType=Component.Type.UNKNOWN;
        public ActionRow(JSONObject jsonObject){
            String fName="[build]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=1;
                if(jsonObject.has(lcMessageComponent.CommonKeys.keyType))type=jsonObject.optInt(lcMessageComponent.CommonKeys.keyType);
                if(type==1){
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyComponents)){
                        setComponents(jsonObject.optJSONArray(lcMessageComponent.CommonKeys.keyComponents));
                    }
                }else{
                    throw new Exception(nName+fName+".json is not a container>failed to build");
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public ActionRow(JSONArray jsonArray){
            String fName="[build]";
            try {
                setComponents(jsonArray);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean setComponents( JSONArray jsonArray){
            String fName="[setComponents]";
            try {
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                components.clear();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    lcMessageBuildComponent component=new lcMessageBuildComponent(jsonObject);
                    if(componentType==Component.Type.UNKNOWN){
                        componentType=component.getType();
                    }else if(componentType!=component.getType()){
                        throw new Exception("Error in component type");
                    }
                    component.getType();
                    components.add(component);
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public List<lcMessageBuildComponent> getComponents(){
            String fName="[getComponents]";
            try {
                logger.info(nName+fName+".list.size="+components.size());
                return  components;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public JSONArray getComponentsAsJson(){
            String fName="[getComponentsAsJson]";
            try {
                logger.info(nName+fName+".list.size="+components.size());
                JSONArray jsonArray=new JSONArray();
                int limit=getMaxPerRow();
                for(lcMessageBuildComponent component: components){
                    if(jsonArray.length()>=limit){
                        logger.warn(nName+fName+".reached components limit>break");
                        break;
                    }
                    if(!component.isIgnored()){
                        JSONObject jsonObject=component.getJson();
                        if(jsonObject!=null&&!jsonObject.isEmpty()){
                            jsonArray.put(jsonObject);
                        }
                    }

                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        public boolean isEmpty(){
            String fName="[iEmpty]";
            try {
                boolean result=components.isEmpty();
                logger.info(nName+fName+".isEmpty="+result);
                return result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int size(){
            String fName="[size]";
            try {
                int result=components.size();
                logger.info(nName+fName+".isEmpty="+result);
                return result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public ActionRow clear(){
            String fName="[clear]";
            try {
                components=new ArrayList<>();
                logger.info(nName+fName+".cleared");
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyType,1);
                if(components!=null)jsonObject.put(keyComponents,getComponentsAsJson());
                if(isIgnored()){
                    return new JSONObject();
                }
                logger.info(nName+fName+".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public DataObject getAsDataObject(){
            String fName="[getAsDataObject]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                DataObject dataObject=DataObject.fromJson(getJson().toString());
                logger.info(nName+fName+".dataObject="+dataObject.toString());
                return  dataObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public net.dv8tion.jda.api.interactions.components.ActionRow getAsActionRow(){
            String fName="[getAsActionRow]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                return net.dv8tion.jda.api.interactions.components.ActionRow.fromData(getAsDataObject());
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getMaxPerRow(){
            String fName="[getMaxPerRow]";
            try {
                int value=componentType.getMaxPerRow();
                logger.info(nName+fName+".value="+value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getComponentsTypeAsInt(){
            String fName="[getComponentsTypeAsInt]";
            try {
                int value= getComponentType(componentType);
                logger.info(nName+fName+".value="+value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public Component.Type getComponentsType(){
            String fName="[getComponentsType]";
            try {
                return  componentType;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return Component.Type.UNKNOWN;
            }
        }
        public ActionRow setComponentsViaMessageBuildComponent(List<lcMessageBuildComponent> components){
            String fName="[setComponentsViaMessageBuildComponent]";
            try {
                if(components==null){
                    logger.warn(nName+fName+".invalid list");
                    return null;
                }
                this.components=components;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow setComponentsViaMessageGetComponent(List<lcMessageComponent> components){
            String fName="[setComponentsViaMessageGetComponent]";
            try {
                if(components==null){
                    logger.warn(nName+fName+".invalid list");
                    return null;
                }
                this.components=new ArrayList<>();
                for(lcMessageComponent component:components){
                    this.components.add(new lcMessageBuildComponent(component.getJson()));
                }
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent getComponent(int index){
            String fName="[getComponent]";
            try {
                logger.info(nName+fName+".index="+index);
                return  components.get(index);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow addComponent(lcMessageBuildComponent component){
            String fName="[addComponent]";
            try {
                if(component==null){
                    logger.warn(nName+fName+".input can't be null");
                    return null;
                }
                if(size()>getMaxPerRow()){
                    logger.warn(nName+fName+".max component count reached");
                }
                this.components.add(component);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow addComponent(lcMessageComponent component){
            String fName="[addComponent]";
            try {
                if(component==null){
                    logger.warn(nName+fName+".input can't be null");
                    return null;
                }
                if(size()>getMaxPerRow()){
                    logger.warn(nName+fName+".max component count reached");
                }
                this.components.add(new lcMessageBuildComponent(component.getJson()));
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow setComponent(int index, lcMessageBuildComponent component){
            String fName="[setComponent]";
            try {
                if(component==null){
                    logger.warn(nName+fName+".invalid list");
                    return null;
                }
                logger.info(nName+fName+".index="+index);
                this.components.set(index,component);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow setComponent(int index, lcMessageComponent component){
            String fName="[setComponent]";
            try {
                if(component==null){
                    logger.warn(nName+fName+".invalid list");
                    return null;
                }
                logger.info(nName+fName+".index="+index);
                this.components.set(index,new lcMessageBuildComponent(component.getJson()));
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent remComponent(int index){
            String fName="[remComponent]";
            try {
                logger.info(nName+fName+".index="+index);
                return this.components.remove(index);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ActionRow clearComponents(){
            String fName="[clearComponents]";
            try {
                this.components=new ArrayList<>();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent getAsMessageBuildComponent(){
            String fName="[getAsMessageBuildComponent]";
            try {
                return  new lcMessageBuildComponent(getJson());
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        protected boolean ignore=false;
        public boolean isIgnored() {
            String fName="[isIgnored]";
            return this.ignore;
        }
        public void setIgnored() {
            String fName="[setIgnored]";
            try {
                this.ignore=true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void setIgnored(boolean value) {
            String fName="[setIgnored]";
            try {
                this.ignore=value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
    public static class Button {
        Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
        String nName="Button@";
        public final int LABEL_MAX_LENGTH = net.dv8tion.jda.api.interactions.components.Button.LABEL_MAX_LENGTH;
        public final int ID_MAX_LENGTH = net.dv8tion.jda.api.interactions.components.Button.ID_MAX_LENGTH;
        public final int URL_MAX_LENGTH = net.dv8tion.jda.api.interactions.components.Button.URL_MAX_LENGTH;
        protected Button(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        public Button(String customId,String label){
            String fName="[build]";
            logger.info(nName+fName+".blank");
            style=ButtonStyle.PRIMARY;
            setCustomId(customId);setLabel(label);
        }
        public Button(String customId,String label,ButtonStyle buttonStyles){
            String fName="[build]";
            logger.info(nName+fName+".blank");
            style=ButtonStyle.PRIMARY;
            setCustomId(customId);setLabel(label);setStyle(buttonStyles);
        }
        protected String customId="",label="",url="";
        protected ButtonStyle style=ButtonStyle.UNKNOWN;
        protected JSONObject emoji=new JSONObject();
        boolean disabled=false;
        public Button(JSONObject jsonObject){
            String fName="[build]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=2;
                if(jsonObject.has(lcMessageComponent.CommonKeys.keyType))type=jsonObject.optInt(lcMessageComponent.CommonKeys.keyType);
                if(type==2){
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyStyle))style=getButtonStyle(jsonObject.optInt(lcMessageComponent.CommonKeys.keyStyle));
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyLabel))label=jsonObject.optString(lcMessageComponent.CommonKeys.keyLabel);
                    if(isCommand()){
                        if(jsonObject.has(lcMessageComponent.CommonKeys.keyCustomId))customId=jsonObject.optString(lcMessageComponent.CommonKeys.keyCustomId);
                    }else
                    if(isUrl()){
                        if(jsonObject.has(lcMessageComponent.CommonKeys.keyUrl))url=jsonObject.optString(lcMessageComponent.CommonKeys.keyUrl);
                    }
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyEmoji))emoji=jsonObject.optJSONObject(lcMessageComponent.CommonKeys.keyEmoji);
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyDisabled))disabled=jsonObject.optBoolean(lcMessageComponent.CommonKeys.keyDisabled);
                }else{
                    throw new Exception(nName+fName+".component is not a button>failed to build");
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isInvalid(){
            String fName="[isInvalid]";
            try {
                boolean result=false;
                if(style==ButtonStyle.UNKNOWN){
                    result=true;
                    logger.warn(nName+fName+".failed 1");
                }
                if(!result&&!isCommand()&&!isUrl()){
                    result=true;
                    logger.warn(nName+fName+".failed 2");
                }
                if(!result&&isCommand()){
                    if(customId==null||customId.isBlank()){
                        logger.warn(nName+fName+".failed 3");
                        result=true;
                    }
                }
                if(!result&&isUrl()){
                    if(url==null||url.isBlank()){
                        logger.warn(nName+fName+".failed 4");
                        result=true;
                    }
                }
                logger.info(nName+fName+".result="+result);
                return result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int getStyleAsInt(){
            String fName="[getStyleAsInt]";
            try{
                int style=getButtonStyle(getStyle());
                logger.info(nName+fName+".value="+style);
                return  style;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public ButtonStyle getStyle(){
            String fName="[getStyle]";
            try {
                logger.info(nName+fName+".value="+style);
                return  style;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return ButtonStyle.UNKNOWN;
            }
        }
        public boolean isCommand(){
            String fName="[isCommand]";
            try {
                if(style==ButtonStyle.PRIMARY||style==ButtonStyle.SECONDARY||style==ButtonStyle.SUCCESS||style==ButtonStyle.DANGER){
                    logger.info(nName+fName+".true");
                    return  true;
                }
                logger.info(nName+fName+".false");
                return  false;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isUrl(){
            String fName="[isUrl]";
            try {
                if(style==ButtonStyle.LINK){
                    logger.info(nName+fName+".true");
                    return  true;
                }
                logger.info(nName+fName+".false");
                return  false;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getLabel(){
            String fName="[getLabel]";
            try {
                logger.info(nName+fName+".value="+label);
                return  label;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean hasLabel(){
            String fName="[hasLabel]";
            try {
                boolean value=false;
                if(label!=null&&!label.isEmpty()){
                    value=true;
                }
                logger.info(nName+fName+".value="+value);
                return value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getCustomId(){
            String fName="[getCustomId]";
            try {
                if(!isCommand()){
                    logger.warn(nName+fName+".not a command");
                    return  "";
                }
                logger.info(nName+fName+".value="+customId);
                return  customId;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getUrl(){
            String fName="[getUrl]";
            try {
                if(!isUrl()){
                    logger.warn(nName+fName+".not a url");
                    return  "";
                }
                logger.info(nName+fName+".value="+url);
                return  url;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getIdOrUrl(){
            String fName="[getIdOrUrl]";
            try {
                String value="";
                if(isCommand()){
                    value=getCustomId();
                }else
                if(isUrl()){
                    value=getUrl();
                }
                logger.info(nName+fName+".value="+value);
                return  value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean isDisabled(){
            String fName="[isDisabled]";
            try {
                logger.info(nName+fName+".value="+disabled);
                return  disabled;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getEmojiAsJson(){
            String fName="[getEmojiAsJson]";
            try {
                logger.info(nName+fName+".value="+emoji.toString());
                return  emoji;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public Button setStyle(int opt){
            String fName="[setStyle]";
            try {
                logger.info(nName+fName+".opt="+opt);
                style=getButtonStyle(opt);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setStyle(ButtonStyle buttonStyles){
            String fName="[setStyle]";
            try {
                if(buttonStyles==null)return null;
                style=buttonStyles;
                return  this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setLabel(String str){
            String fName="[setLabel]";
            try {
                if(str==null){
                    throw new Exception( fName+".str cant be null");
                }
                if(str.length()>LABEL_MAX_LENGTH){
                    throw new Exception(nName+fName+"str too big, limit "+LABEL_MAX_LENGTH+" characters");
                }
                label=str;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setCustomId(String str){
            String fName="[setCustomId]";
            try {
                if(!isCommand()){
                    throw new Exception(nName+fName+".invalid component type");
                }
                if(str==null){
                    throw new Exception( fName+".str cant be null");
                }
                if(str.length()>ID_MAX_LENGTH){
                    throw new Exception(nName+fName+".str too big, limit "+ID_MAX_LENGTH+" characters");
                }
                customId=str;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setUrl(String str){
            String fName="[setUrl";
            try {
                if(!isUrl()){
                    throw new Exception(nName+fName+".invalid component type");
                }
                if(str==null){
                    throw new Exception(nName+fName+".str cant be null");
                }
                if(str.length()>URL_MAX_LENGTH){
                    throw new Exception(nName+fName+".str too big, limit "+URL_MAX_LENGTH+" characters");
                }
                url=str;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button clear(){
            String fName="[clear]";
            try {
                style=ButtonStyle.UNKNOWN;
                customId=null;label=null;url=null;
                emoji=new JSONObject();
                disabled=false;
                logger.info(nName+fName+".cleared");
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEmoji(){
            String fName="[isEmoji]";
            try {
                if(emoji==null){
                    logger.warn(nName+fName+".emoji cant be null");return false;
                }
                if(emoji.isEmpty()){
                    logger.info(nName+fName+".emoji is empty");return false;
                }
                if(!emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)){
                    logger.warn(nName+fName+".key id missing");return false;
                }
                if(!emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName)){
                    logger.warn(nName+fName+".key name missing");return false;
                }
                if(emoji.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)<=0){
                    logger.warn(nName+fName+".id equal or bellow 0");return false;
                }
                if(emoji.optString(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName).isBlank()){
                    logger.warn(nName+fName+"name is blank");return false;
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Button setEmoji(Emoji emoji){
            String fName="[setEmoji]";
            try {
                if(emoji==null){
                    logger.warn(nName+fName+".emoji cant be null");return null;
                }
                this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId, emoji.getId());
                this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName, emoji.getName());
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setEmoji(long id,String name){
            String fName="[setEmoji]";
            try {
                if(id<=0){
                    logger.warn(nName+fName+".id csnt be bellow 0 or 0");return null;
                }
                if(name==null){
                    logger.warn(nName+fName+".name cant be null");return null;
                }
                if(name.isBlank()){
                    logger.warn(nName+fName+".name cant be blank");return null;
                }
                this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId, id);
                this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName, name);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button setEmoji(JSONObject jsonObject){
            String fName="[setEmoji]";
            try {
                if(jsonObject==null){
                    logger.warn(nName+fName+".jsonObject cant be null");return null;
                }
                long id=0;String name="";
                if(jsonObject.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId))id=jsonObject.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId);
                if(jsonObject.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName))id=jsonObject.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName);
                return setEmoji(id,name);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Button clearEmoji(){
            String fName="[clearEmoji]";
            try {
                this.emoji=new JSONObject();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyType,2);
                if(style!=ButtonStyle.UNKNOWN)jsonObject.put(keyStyle,getStyleAsInt());
                if(label!=null&&!label.isBlank())jsonObject.put(keyLabel,lsStringUsefullFunctions.truncateStringIfOver(label,LABEL_MAX_LENGTH,"..."));
                if(isCommand()){
                    if(customId!=null&&!customId.isBlank())jsonObject.put(keyCustomId,lsStringUsefullFunctions.truncateStringIfOver(customId,ID_MAX_LENGTH,"..."));
                }else
                if(isUrl()){
                    if(url!=null&&!url.isBlank())jsonObject.put(keyUrl,lsStringUsefullFunctions.truncateStringIfOver(url,URL_MAX_LENGTH,"..."));
                }
                if(disabled)jsonObject.put(lcMessageComponent.CommonKeys.keyDisabled,true);
                if(isEmoji())jsonObject.put(lcMessageComponent.CommonKeys.keyEmoji,emoji);
                if(isIgnored()){
                    return new JSONObject();
                }
                logger.info(nName+fName+".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public net.dv8tion.jda.api.interactions.components.Button getAsButton(){
            String fName="[getAsButton]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                net.dv8tion.jda.api.interactions.components.Button button=null;
                if(isEmoji()){
                    if(hasLabel()){
                        button=net.dv8tion.jda.api.interactions.components.Button.of(getStyle(),getIdOrUrl(),getLabel(),lsUsefullFunctions.getEmojifromMarkdown(emoji));
                    }else{
                        button=net.dv8tion.jda.api.interactions.components.Button.of(getStyle(),getIdOrUrl(), lsUsefullFunctions.getEmojifromMarkdown(emoji));
                    }
                }else{
                    button=net.dv8tion.jda.api.interactions.components.Button.of(getStyle(),getIdOrUrl(),getLabel());
                }
                return  button;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public DataObject getAsDataObject(){
            String fName="[getAsDataObject]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                DataObject dataObject=DataObject.fromJson(getJson().toString());
                logger.info(nName+fName+".dataObject="+dataObject.toString());
                return  dataObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent toComponent(){
            String fName="[toComponent]";
            try {
                lcMessageBuildComponent component=new lcMessageBuildComponent(getJson());
                logger.info(nName+fName+"component.json="+component.getJson().toString());
                return  component;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent getAsMessageBuildComponent(){
            String fName="[getAsMessageBuildComponent]";
            try {
                return  new lcMessageBuildComponent(getJson());
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public void setDisable() {
            String fName="[setDisable]";
            try {
                this.disabled=true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void setDisable(boolean value) {
            String fName="[setDisable]";
            try {
                this.disabled=value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        protected boolean ignore=false;
        public boolean isIgnored() {
            String fName="[isIgnored]";
            return this.ignore;
        }
        public void setIgnored() {
            String fName="[setIgnored]";
            try {
                this.ignore=true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void setIgnored(boolean value) {
            String fName="[setIgnored]";
            try {
                this.ignore=value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

    }
    public static class SelectMenu {
        Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
        String nName="SelectMenu@";
        public final int ID_MAX_LENGTH = 100;
        public final int PLACEHOLDER_MAX_LENGTH = 100;
        public final int OPTIONS_MAX_SIZE = 25;
        public final int MAX_MINVALUES = 25;
        public final int MAX_MAXVALUES = 25;
        public final int MIN_MINVALUES = 1;
        public final int MIN_MAXVALUES = 1;
        public SelectMenu(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected String customId="",placeholder="",url="";
        protected int min_values=MIN_MINVALUES,max_values=MIN_MAXVALUES;
        protected  boolean disabled=false;
        List<Option>options=new ArrayList<>();
        Message message=null;
        public SelectMenu(JSONObject jsonObject){
            String fName="[build]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public SelectMenu(JSONObject jsonObject,Message message){
            String fName="[build]";
            try {
                if(!set(jsonObject)){return;}
                this.message=message;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=3;
                if(jsonObject.has(lcMessageComponent.CommonKeys.keyType))type=jsonObject.optInt(lcMessageComponent.CommonKeys.keyType);
                if(type== 3){
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyCustomId))customId=jsonObject.optString(lcMessageComponent.SelectMenuKeys.keyCustomId);
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyPlaceholder))placeholder=jsonObject.optString(lcMessageComponent.SelectMenuKeys.keyPlaceholder);
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyMinValues))min_values=jsonObject.optInt(lcMessageComponent.SelectMenuKeys.keyMinValues);
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyMaxValues))max_values=jsonObject.optInt(lcMessageComponent.SelectMenuKeys.keyMaxValues);
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyDisabled))disabled=jsonObject.optBoolean(lcMessageComponent.SelectMenuKeys.keyDisabled);
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyOptions)){
                        JSONArray array=jsonObject.optJSONArray(lcMessageComponent.SelectMenuKeys.keyOptions);
                        for(int i=0;i<array.length();i++){
                            JSONObject item=array.getJSONObject(i);
                            Option option=new Option(item);
                            options.add(option);
                        }
                    }
                }else{
                    throw new Exception(nName+fName+".component is not a selectmenu>failed to build");
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public String getCustomId(){
            String fName="[getCustomId]";
            try {
                logger.info(nName+fName+".value="+customId);
                return  customId;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getPlaceholder(){
            String fName="[getPlaceholder]";
            try {
                logger.info(nName+fName+".value="+placeholder);
                return  placeholder;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public int getMinValues(){
            String fName="[getMinValues]";
            try {
                logger.info(nName+fName+".value="+min_values);
                return  min_values;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getMaxValues(){
            String fName="[getMaxValues]";
            try {
                logger.info(nName+fName+".value="+max_values);
                return  max_values;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isDisabled(){
            String fName="[isDisabled]";
            try {
                logger.info(nName+fName+".value="+disabled);
                return  disabled;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(lcMessageComponent.CommonKeys.keyType,3);
                if(customId!=null)jsonObject.put(lcMessageComponent.CommonKeys.keyCustomId,lsStringUsefullFunctions.truncateStringIfOver(customId,ID_MAX_LENGTH,"..."));
                if(placeholder!=null)jsonObject.put(lcMessageComponent.SelectMenuKeys.keyPlaceholder,lsStringUsefullFunctions.truncateStringIfOver(placeholder,PLACEHOLDER_MAX_LENGTH,"..."));
                if(min_values>0)jsonObject.put(lcMessageComponent.SelectMenuKeys.keyMinValues, lsNumbersUsefullFunctions.correctNumberIfNotBetween(min_values,MIN_MINVALUES,MAX_MINVALUES,1));
                if(max_values>0)jsonObject.put(lcMessageComponent.SelectMenuKeys.keyMaxValues,lsNumbersUsefullFunctions.correctNumberIfNotBetween(max_values,MIN_MAXVALUES,MAX_MAXVALUES,1));
                if(disabled)jsonObject.put(lcMessageComponent.SelectMenuKeys.keyDisabled,true);
                jsonObject.put(lcMessageComponent.SelectMenuKeys.keyOptions,getOptionsAsJson());
                if(isIgnored()){
                    return new JSONObject();
                }
                logger.info(nName+fName+".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public net.dv8tion.jda.api.interactions.components.selections.SelectionMenu getAsSelectSelectionMenu(){
            String fName="[getAsSelectionMenu]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                SelectionMenu.Builder selectionMenu=net.dv8tion.jda.api.interactions.components.selections.SelectionMenu.create(getCustomId());
                selectionMenu.addOptions(getOptionsAsSelectOption());
                if(isDisabled())selectionMenu.setDisabled(true);
                return  selectionMenu.build();
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public DataObject getAsDataObject(){
            String fName="[getAsDataObject]";
            try {
                if(isIgnored()){
                    logger.warn(nName+fName+".is ignored");
                    return null;
                }
                DataObject dataObject=DataObject.fromJson(getJson().toString());
                logger.info(nName+fName+".dataObject="+dataObject.toString());
                return  dataObject;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent toComponent(){
            String fName="[toComponent]";
            try {
                return  new lcMessageBuildComponent(getJson());
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setCustomId(String str){
            String fName="[setCustomId]";
            try {
                if(str==null){
                    throw new Exception( fName+".str cant be null");
                }
                if(str.length()>ID_MAX_LENGTH){
                    throw new Exception(nName+fName+"str too big, limit "+ID_MAX_LENGTH+" characters");
                }
                this.customId=str;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setPlaceholder(String str){
            String fName="[setPlaceholder]";
            try {
                if(str==null){
                    throw new Exception( fName+".str cant be null");
                }
                if(str.length()>PLACEHOLDER_MAX_LENGTH){
                    throw new Exception(nName+fName+"str too big, limit "+PLACEHOLDER_MAX_LENGTH+" characters");
                }
                this.placeholder=str;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isPlaceholderEmpty(){
            String fName="[getMaxValues]";
            try {
                boolean result=placeholder.isBlank();
                logger.info(nName+fName+".result="+result);
                return  result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public SelectMenu setMinValue(int value){
            String fName="[setMinValue]";
            try {
                if(value>MIN_MINVALUES){
                    throw new Exception(nName+fName+".value too small, can't be bellow "+MIN_MINVALUES);
                }
                if(value>MAX_MINVALUES){
                    throw new Exception(nName+fName+".value too big, can't be above "+MAX_MINVALUES);
                }
                if(max_values<min_values){
                    throw new Exception(nName+fName+".min values can't be above max values");
                }
                min_values=value;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setMaxValue(int value){
            String fName="[setMaxValue]";
            try {
                if(value>MIN_MAXVALUES){
                    throw new Exception(nName+fName+".value too small, can't be bellow "+MIN_MAXVALUES);
                }
                if(value>MAX_MAXVALUES){
                    throw new Exception(nName+fName+".value too big, can't be above "+MAX_MAXVALUES);
                }
                if(max_values<min_values){
                    throw new Exception(nName+fName+".max values can't be bellow min values");
                }
                max_values=value;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setDisabled(boolean value){
            String fName="[setDisabled]";
            try {
                disabled=value;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setDisabled(){
            String fName="[setDisabled]";
            try {
                disabled=true;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu clearDisabled(){
            String fName="[clearDisabled]";
            try {
                disabled=false;
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int size(){
            String fName="[getOptionsSize]";
            try {
                int size=options.size();
                logger.info(nName+fName+".list.size="+size);
                return  size;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public boolean isEmpty(){
            String fName="[iEmpty]";
            try {
                boolean result=false;
                if(options==null||options.isEmpty()){
                    result=true;
                }
                logger.info(nName+fName+".isEmpty="+result);
                return result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isSizeOver(){
            String fName="[isSizeOver]";
            try {
                boolean result=false;
                if(size()>OPTIONS_MAX_SIZE){
                    result=true;
                }
                logger.info(nName+fName+".result="+result);
                return result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public List<Option> getOptions(){
            String fName="[getOptions]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                return  options;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<SelectOption> getOptionsAsSelectOption(){
            String fName="[getOptionsAsSelectOption]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                List<SelectOption>selectOptions=new ArrayList<>();
                for(Option option:options){
                    selectOptions.add(option.getAsSelectOption());
                }
                return  selectOptions;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public JSONArray getOptionsAsJson(){
            String fName="[getOptionsAsJson]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                JSONArray jsonArray=new JSONArray();
                for(Option option: options){
                    if(!option.isIgnored()){
                        if(jsonArray.length()>=OPTIONS_MAX_SIZE){
                            logger.warn(nName+fName+".max size reached>break");
                            break;
                        }
                        jsonArray.put(option.getJson());
                    }
                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        public JSONArray getOptionsAsJsonIgnoreSizeLimit(){
            String fName="[getOptionsAsJsonIgnoreSizeLimit]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                JSONArray jsonArray=new JSONArray();
                for(Option option: options){
                    if(!option.isIgnored()){
                        jsonArray.put(option.getJson());
                    }
                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        public SelectMenu addOption(JSONObject jsonObject){
            String fName="[addOption]";
            try {
                if(jsonObject==null){
                    throw new Exception(nName+fName+".input cant be null");
                }
                if(size()>OPTIONS_MAX_SIZE){
                    logger.warn(nName+fName+".reached max option size, cant be above "+OPTIONS_MAX_SIZE);
                }
                this.options.add(new Option(jsonObject));
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu addOption(Option option){
            String fName="[addOption]";
            try {
                if(option==null){
                    throw new Exception(nName+fName+".input cant be null");
                }
                if(size()>OPTIONS_MAX_SIZE){
                    logger.warn(nName+fName+".reached max option size, cant be above "+OPTIONS_MAX_SIZE);
                }
                this.options.add(option);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setOption(int index,JSONObject jsonObject){
            String fName="[setOption]";
            try {
                if(jsonObject==null){
                    logger.warn(nName+fName+".arg cant be null");
                    return null;
                }
                logger.info(nName+fName+".index="+index);
                this.options.set(index,new Option(jsonObject));
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu setOption(int index, Option option){
            String fName="[setOption]";
            try {
                if(option==null){
                    logger.warn(nName+fName+".arg cant be null");
                    return null;
                }
                logger.info(nName+fName+".index="+index);
                this.options.set(index,option);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu remOption(int index){
            String fName="[remOption]";
            try {
                logger.info(nName+fName+".index="+index);
                this.options.remove(index);
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SelectMenu clearOptions(){
            String fName="[clearOptions]";
            try {
                this.options=new ArrayList<>();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isOptionsEmpty(){
            String fName="[getMaxValues]";
            try {
                boolean result=options.isEmpty();
                logger.info(nName+fName+".result="+result);
                return  result;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public static class Option {
            Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
            String nName="SelectMenu_Option@";
            public final int LABEL_MAX_LENGTH =SelectOption.LABEL_MAX_LENGTH ;
            public final int VALUE_MAX_LENGTH =SelectOption.VALUE_MAX_LENGTH;
            public final int DESCRIPTION_MAX_LENGTH = SelectOption.DESCRIPTION_MAX_LENGTH;
            protected Option(){
                String fName="[build]";
                logger.info(nName+fName+".blank");
            }
            protected String label="",value="",description="";
            protected JSONObject emoji=new JSONObject();
            boolean isdefault=false;
            Message message=null;
            public Option(String label,String value){
                String fName="[build]";
                try {
                    if(label!=null)this.label=label;
                    if(value!=null)this.value=value;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(String label,String value,String description){
                String fName="[build]";
                try {
                    if(label!=null)this.label=label;
                    if(value!=null)this.value=value;
                    if(description!=null)this.description=description;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(String label,String value,String description,boolean isdefault){
                String fName="[build]";
                try {
                    if(label!=null)this.label=label;
                    if(value!=null)this.value=value;
                    if(description!=null)this.description=description;
                    this.isdefault=isdefault;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(String label,String value,String description,JSONObject emoji,boolean isdefault){
                String fName="[build]";
                try {
                    if(label!=null)this.label=label;
                    if(value!=null)this.value=value;
                    if(description!=null)this.description=description;
                    if(emoji!=null)this.emoji=emoji;
                    this.isdefault=isdefault;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(JSONObject jsonObject){
                String fName="[build]";
                try {
                    set(jsonObject);
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(JSONObject jsonObject,Message message){
                String fName="[build]";
                try {
                    if(!set(jsonObject)){return;}
                    this.message=message;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            private boolean set( JSONObject jsonObject){
                String fName="[set]";
                try {
                    logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyLabel))label=jsonObject.optString(lcMessageComponent.SelectOptionKeys.keyLabel);
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyValue))value=jsonObject.optString(lcMessageComponent.SelectOptionKeys.keyValue);
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyDescription))description=jsonObject.optString(lcMessageComponent.SelectOptionKeys.keyDescription);
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyEmoji))emoji=jsonObject.optJSONObject(lcMessageComponent.SelectOptionKeys.keyEmoji);
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyDefault))isdefault=jsonObject.optBoolean(lcMessageComponent.SelectOptionKeys.keyDefault);
                    return true;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isEmpty(){
                String fName="[iEmpty]";
                try {
                    boolean result=false;
                    if(label==null||label.isBlank()){
                        result=true;
                    }
                    logger.info(nName+fName+".isEmpty="+result);
                    return result;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public String getLabel(){
                String fName="[getLabel]";
                try {
                    logger.info(nName+fName+".value="+label);
                    return  label;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public String getValue(){
                String fName="[getValue]";
                try {
                    logger.info(nName+fName+".value="+value);
                    return  value;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public String getDescription(){
                String fName="[getDescription]";
                try {
                    logger.info(nName+fName+".value="+description);
                    return  description;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public JSONObject getEmojiAsJson(){
                String fName="[getEmojiAsJson]";
                try {
                    logger.info(nName+fName+".value="+emoji.toString());
                    return  emoji;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            public JSONObject getJson(){
                String fName="[getJson]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    if(label!=null&&!label.isBlank()) jsonObject.put(lcMessageComponent.SelectOptionKeys.keyLabel, lsStringUsefullFunctions.truncateStringIfOver(label,LABEL_MAX_LENGTH,"..."));
                    if(value!=null&&!value.isBlank())jsonObject.put(lcMessageComponent.SelectOptionKeys.keyValue,lsStringUsefullFunctions.truncateStringIfOver(value,VALUE_MAX_LENGTH,"..."));
                    if(description!=null&&!description.isBlank())jsonObject.put(lcMessageComponent.SelectOptionKeys.keyDescription,lsStringUsefullFunctions.truncateStringIfOver(description,DESCRIPTION_MAX_LENGTH,"..."));
                    if(isdefault)jsonObject.put(lcMessageComponent.SelectOptionKeys.keyDefault,true);
                    if(isEmoji())jsonObject.put(lcMessageComponent.CommonKeys.keyEmoji,emoji);
                    if(isIgnored()){
                        return new JSONObject();
                    }
                    logger.info(nName+fName+".jsonObject=" + jsonObject.toString());
                    return jsonObject;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            public net.dv8tion.jda.api.interactions.components.selections.SelectOption getAsSelectOption(){
                String fName="[getAsSelectOption]";
                try {
                    if(isIgnored()){
                        logger.warn(nName+fName+".is ignored");
                        return null;
                    }
                    net.dv8tion.jda.api.interactions.components.selections.SelectOption selectOption=net.dv8tion.jda.api.interactions.components.selections.SelectOption.of(getLabel(),getValue());
                    if(isdefault)selectOption=selectOption.withDefault(true);
                    if(isEmoji())selectOption=selectOption.withEmoji(lsUsefullFunctions.getEmojifromMarkdown(emoji));
                    return  selectOption;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public DataObject getAsDataObject(){
                String fName="[getAsDataObject]";
                try {
                    if(isIgnored()){
                        logger.warn(nName+fName+".is ignored");
                        return null;
                    }
                    DataObject dataObject=DataObject.fromJson(getJson().toString());
                    logger.info(nName+fName+".dataObject="+dataObject.toString());
                    return  dataObject;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isEmoji(){
                String fName="[isEmoji]";
                try {
                    if(emoji==null){
                        logger.info(nName+fName+".emoji cant be null");return false;
                    }
                    if(emoji.isEmpty()){
                        logger.info(nName+fName+".emoji is empty");return false;
                    }
                    if(!emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)){
                        logger.info(nName+fName+".key id missing");return false;
                    }
                    if(!emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName)){
                        logger.info(nName+fName+".key name missing");return false;
                    }
                    if(emoji.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)<=0){
                        logger.info(nName+fName+".id equal or bellow 0");return false;
                    }
                    if(emoji.optString(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName).isBlank()){
                        logger.info(nName+fName+"name is blank");return false;
                    }
                    return true;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public Option setEmoji(Emoji emoji){
                String fName="[setEmoji]";
                try {
                    if(emoji==null){
                        logger.warn(nName+fName+".emoji cant be null");return null;
                    }
                    this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId, emoji.getId());
                    this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName, emoji.getName());
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setEmoji(long id,String name){
                String fName="[setEmoji]";
                try {
                    if(id<=0){
                        logger.warn(nName+fName+".id csnt be bellow 0 or 0");return null;
                    }
                    if(name==null){
                        logger.warn(nName+fName+".name cant be null");return null;
                    }
                    if(name.isBlank()){
                        logger.warn(nName+fName+".name cant be blank");return null;
                    }
                    this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId, id);
                    this.emoji.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName, name);
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setEmoji(JSONObject jsonObject){
                String fName="[setEmoji]";
                try {
                    if(jsonObject==null){
                        logger.warn(nName+fName+".jsonObject cant be null");return null;
                    }
                    long id=0;String name="";
                    if(jsonObject.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId))id=jsonObject.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId);
                    if(jsonObject.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName))id=jsonObject.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName);
                    return setEmoji(id,name);
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option clearEmoji(){
                String fName="[clearEmoji]";
                try {
                    this.emoji=new JSONObject();
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setLabel(String str){
                String fName="[setLabel]";
                try {
                    if(str==null){
                        throw new Exception( fName+".str cant be null");
                    }
                    if(str.length()>LABEL_MAX_LENGTH){
                        throw new Exception(nName+fName+".str too big, limit "+LABEL_MAX_LENGTH+" characters");
                    }
                    label=str;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setDescription(String str){
                String fName="[setDescription]";
                try {
                    if(str==null){
                        throw new Exception(nName+fName+".str cant be null");
                    }
                    if(str.length()>DESCRIPTION_MAX_LENGTH){
                        throw new Exception(nName+fName+".str too big, limit "+DESCRIPTION_MAX_LENGTH+" characters");
                    }
                    description=str;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setValue(String str){
                String fName="[setValue]";
                try {
                    if(str==null){
                        throw new Exception(nName+fName+".str cant be null");
                    }
                    if(str.length()>VALUE_MAX_LENGTH){
                        throw new Exception(nName+fName+".str too big, limit "+VALUE_MAX_LENGTH+" characters");
                    }
                    value=str;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setDefault(boolean isdefault){
                String fName="[setDefault]";
                try {
                    this.isdefault=isdefault;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option setDefault(){
                String fName="[setDefault]";
                try {
                    this.isdefault=true;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option clearDefault(){
                String fName="[clearDefault]";
                try {
                    this.isdefault=false;
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Option clear(){
                String fName="[clear]";
                try {
                    label=null;value=null;description=null;
                    emoji=new JSONObject();
                    isdefault=false;
                    logger.info(nName+fName+".cleared");
                    return this;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            protected boolean ignore=false;
            public boolean isIgnored() {
                String fName="[isIgnored]";
                return this.ignore;
            }
            public void setIgnored() {
                String fName="[setIgnored]";
                try {
                    this.ignore=true;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public void setIgnored(boolean value) {
                String fName="[setIgnored]";
                try {
                    this.ignore=value;
                }catch (Exception e){
                    logger.error(nName+fName+".exception=" + e);
                    logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
        }

        public lcMessageBuildComponent getAsMessageBuildComponent(){
            String fName="[getAsMessageBuildComponent]";
            try {
                return  new lcMessageBuildComponent(getJson());
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent.SelectMenu.Option getOptionAt(int index){
            String fName="[getOptionAt]";
            try {
                return options.get(index);
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu.Option getOptionByValue(String value){
            String fName="[getOptionByValue]";
            try {
                for(Option option:options){
                    if(option.getValue().equals(value))  {
                        return option;
                    }
                }
                return null;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu.Option getOptionByLabel(String label){
            String fName="[getOptionByLabel]";
            try {
                for(Option option:options){
                    if(option.getLabel().equals(label))  {
                        return option;
                    }
                }
                return null;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu.Option remOptionByValue(String value){
            String fName="[remtOptionByValue]";
            try {
                for(int i=0;i<options.size();i++){
                    Option option=options.get(i);
                    if(option.getValue().equals(value))  {
                        return options.remove(i);
                    }
                }
                return null;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu.Option remOptionByLabel(String label){
            String fName="[remOptionByLabel]]";
            try {
                for(int i=0;i<options.size();i++){
                    Option option=options.get(i);
                    if(option.getLabel().equals(label))  {
                        return options.remove(i);
                    }
                }
                return null;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu setDefaultOptionByValue(String value){
            String fName="[setDefaultOptionByValue]";
            try {
                getOptionByValue(value).setDefault();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu setDefaultOptionByLabel(String label){
            String fName="[setDefaultOptionByLabel]";
            try {
                getOptionByLabel(label).setDefault();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.SelectMenu setDefaultOptionAt(int index){
            String fName="[setDefaultOptionAt]";
            try {
                getOptionAt(index).setDefault();
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        protected boolean ignore=false;
        public boolean isIgnored() {
            String fName="[isIgnored]";
            return this.ignore;
        }
        public void setIgnored() {
            String fName="[setIgnored]";
            try {
                this.ignore=true;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void setIgnored(boolean value) {
            String fName="[setIgnored]";
            try {
                this.ignore=value;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
    public JSONObject getJson(){
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();

            if(isButton()){
                if(!button.isIgnored()){
                    jsonObject=button.getJson();
                }else{
                    setIgnored();
                }
            }else
            if(isContainer()){
                if(!actionRow.isIgnored()){
                    jsonObject.put(lcMessageComponent.CommonKeys.keyComponents, actionRow.getComponentsAsJson());
                }else{
                    setIgnored();
                }
            }else
            if(isSelectMenu()){
                if(!selectMenu.isIgnored()){
                    jsonObject=selectMenu.getJson();
                }else{
                    setIgnored();
                }
            }
            if(isIgnored()){
                return new JSONObject();
            }
            jsonObject.put(lcMessageComponent.CommonKeys.keyType,getComponentType(type));
            logger.info(fName+".jsonObject=" + jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public DataObject getAsDataObject(){
        String fName="[getAsDataObject]";
        try {
            DataObject dataObject=DataObject.fromJson(getJson().toString());
            logger.info(fName+".dataObject="+dataObject.toString());
            return  dataObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public net.dv8tion.jda.api.interactions.components.ActionRow getAsActionRow(){
        String fName="[getAsActionRow]";
        try {
            if(isIgnored()){
                logger.warn(fName+".is ignored");
                return null;
            }
            return net.dv8tion.jda.api.interactions.components.ActionRow.fromData(getAsDataObject());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected boolean ignore=false;
    public boolean isIgnored() {
        String fName="[isIgnored]";
        return this.ignore;
    }
    public void setIgnored() {
        String fName="[setIgnored]";
        try {
            this.ignore=true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void setIgnored(boolean value) {
        String fName="[setIgnored]";
        try {
            this.ignore=value;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageBuildComponent setType(int type){
        String fName="[setType]";
        try {
            this.type=getComponentType(type);
            logger.info(fName+".value="+this.type);
            return  this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent setType(Component.Type type){
        String fName="[setType]";
        try {
            if(type==null){
                logger.warn(fName+".vvalue cant be null");
                type=Component.Type.UNKNOWN;
            }
            this.type=type;
            logger.info(fName+".value="+this.type);
            return  this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ActionRow toContainer(){
        String fName="[toContainer]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            return actionRow;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public Button toButton(){
        String fName="[toButton]";
        try {
            if(!isButton()){
                logger.warn(fName+".not a button");
                return  null;
            }
            return button;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public SelectMenu toSelectMenu(){
        String fName="[toSelectMenu]";
        try {
            if(!isSelectMenu()){
                logger.warn(fName+".not a selectMenu");
                return  null;
            }
            return selectMenu;

        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcMessageBuildComponent addButton(Button button){
        String fName="[addButton]";
        try {
            this.selectMenu=new SelectMenu();this.button=new Button();
            setType(Component.Type.ACTION_ROW);
            if(actionRow ==null) actionRow =new ActionRow();
            actionRow.addComponent(button.toComponent());
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcMessageBuildComponent addSelect(SelectMenu selectMenu){
        String fName="[addSelect]";
        try {
            this.selectMenu=new SelectMenu();this.button=new Button();
            setType(Component.Type.ACTION_ROW);
            if(actionRow ==null) actionRow =new ActionRow();
            actionRow.addComponent(selectMenu.toComponent());
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public lcMessageBuildComponent setContainer(ActionRow actionRow){
        String fName="[setContainer";
        try {
            this.selectMenu=new SelectMenu();this.button=new Button();
            setType(Component.Type.ACTION_ROW);
            this.actionRow = actionRow;
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    public lcMessageBuildComponents.Buttons getButtonsContainer(){
        String fName="[getButtonsContainer]";
        try {
            return new lcMessageBuildComponents.Buttons(getButtons(),actionRow);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public List<lcMessageBuildComponent.Button> getButtons(){
        return  getButtons(false);
    }
    public lcMessageBuildComponent.Button getButtonAt4(int index){
        return  getButtonAt4(index,false);
    }
    public lcMessageBuildComponent.Button getButtonById(String id){
            return  getButtonById(id,false);
    }
    public lcMessageBuildComponent.Button getButtonByLabel(String label){
            return  getButtonByLabel(label,false);
    }
    public lcMessageBuildComponent.Button delButtonById(String id){
            return  delButtonById(id,false);
    }
    public lcMessageBuildComponent.Button delButtonByLabel(String label){
            return delButtonByLabel(label,false);
    }
    public lcMessageBuildComponent.SelectMenu getSelect(){
        return getSelect(false);
    }
    public lcMessageBuildComponent.SelectMenu getSelectAt(int index){
        return getSelectAt(index,false);
    }
    public lcMessageBuildComponent.SelectMenu getSelectById(String id){
            return getSelectById(id,false);
    }
    public lcMessageBuildComponent.SelectMenu delSelectById(String id){
            return delSelectById(id,false);
    }

    public List<lcMessageBuildComponent.Button> getButtons(boolean allowexc){
        String fName="[getButtons]";
        try {
            List<lcMessageBuildComponent>components=getComponents(allowexc);
            List<lcMessageBuildComponent.Button>buttons=new ArrayList<>();
            for(lcMessageBuildComponent component:components){
                buttons.add(component.toButton());
            }
            return buttons;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new ArrayList<>();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.Button getButtonAt4(int index, boolean allowexc){
        String fName="[getButtonAt4Container]";
        try {
            return  getComponentAt(index,allowexc).toButton();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new Button();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.Button getButtonById(String id, boolean allowexc){
        String fName="[getButtonById4Container]";
        try {
            return  getButtonById4ContainerAsComponent(id,allowexc).toButton();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new Button();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.Button getButtonByLabel(String label, boolean allowexc){
        String fName="[getButtonByLabel4Container]";
        try {
            return  getButtonByLabel4ContainerAsComponent(label,allowexc).toButton();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new Button();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.Button delButtonById(String id, boolean allowexc){
        String fName="[delButtonById4Container]";
        try {
            return  delButtonById4ContainerAsComponent(id,allowexc).toButton();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new Button();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.Button delButtonByLabel(String label, boolean allowexc){
        String fName="[delButtonByLabel4Container]";
        try {
            return delButtonByLabel4ContainerAsComponent(label,allowexc).toButton();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new Button();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.SelectMenu getSelect(boolean allowexc){
        String fName="[getSelect4Container]";
        try {
            return getComponentAt(0,allowexc).toSelectMenu();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new SelectMenu();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.SelectMenu getSelectAt(int index, boolean allowexc){
        String fName="[getSelectAt4Container]";
        try {
            return getComponentAt(index,allowexc).toSelectMenu();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new SelectMenu();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.SelectMenu getSelectById(String id, boolean allowexc){
        String fName="[getSelectById4Container]";
        try {
            return getSelectById4ContainerAsComponent(id,allowexc).toSelectMenu();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new SelectMenu();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent.SelectMenu delSelectById(String id, boolean allowexc){
        String fName="[delButtonById4Container]";
        try {
            return delSelectById4ContainerAsComponent(id,allowexc).toSelectMenu();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new SelectMenu();
            }
            return  null;
        }
    }

    public List<lcMessageBuildComponent> getComponents(){
        return getComponents(false);
    }
    public lcMessageBuildComponent getComponentAt(int index){
       return getComponentAt(index,false);
    }
    public lcMessageBuildComponent getButtonById4ContainerAsComponent(String id){
        return getButtonById4ContainerAsComponent(id,false);
    }
    public lcMessageBuildComponent getButtonByLabel4ContainerAsComponent(String label){
       return getButtonByLabel4ContainerAsComponent(label,false);
    }
    public lcMessageBuildComponent delButtonById4ContainerAsComponent(String id){
       return delButtonById4ContainerAsComponent(id,false);
    }
    public lcMessageBuildComponent delButtonByLabel4ContainerAsComponent(String label){
      return delButtonByLabel4ContainerAsComponent(label,false);
    }
    public lcMessageBuildComponent ignoreButtonById4ContainerAsComponent(String id){
        return ignoreButtonById4ContainerAsComponent(id,false);
    }
    public lcMessageBuildComponent ignoreButtonByLabel4ContainerAsComponent(String label){
        return ignoreButtonByLabel4ContainerAsComponent(label,false);
    }
    public lcMessageBuildComponent getSelectById4ContainerAsComponent(String id){
        return getSelectById4ContainerAsComponent(id,false);
    }
    public lcMessageBuildComponent delSelectById4ContainerAsComponent(String id){
        return  delSelectById4ContainerAsComponent(id,false);
    }

    public List<lcMessageBuildComponent> getComponents(boolean allowexc){
        String fName="[getComponentAt]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            /*lcMessageBuildComponent actionRow1=actionRow.getComponent(index);
            if(actionRow1==null&&allowexc)return new lcMessageBuildComponent();
            return  actionRow1;*/
            return actionRow.getComponents();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new ArrayList<>();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent getComponentAt(int index,boolean allowexc){
        String fName="[getComponentAt]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            lcMessageBuildComponent actionRow1=actionRow.getComponent(index);
            if(actionRow1==null&&allowexc)return new lcMessageBuildComponent();
            return  actionRow1;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent getButtonById4ContainerAsComponent(String id,boolean allowexc){
        String fName="[getButtonById4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(lcMessageBuildComponent component: actionRow.getComponents()){
                if(component.isButton()&&component.toButton().getCustomId().equals(id)){
                    return  component;
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent getButtonByLabel4ContainerAsComponent(String label,boolean allowexc){
        String fName="[getButtonByLabel4ContainerAsComponentAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(lcMessageBuildComponent component: actionRow.getComponents()){
                if(component.isButton()&&component.toButton().getLabel().equals(label)){
                    return  component;
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent delButtonById4ContainerAsComponent(String id,boolean allowexc){
        String fName="[delButtonById4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(int i = 0; i< actionRow.getComponents().size(); i++){
                lcMessageBuildComponent component= actionRow.getComponent(i);
                if(component.isButton()&&component.toButton().getCustomId().equals(id)){
                    actionRow.remComponent(i);
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent delButtonByLabel4ContainerAsComponent(String label,boolean allowexc){
        String fName="[delButtonByLabel4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(int i = 0; i< actionRow.getComponents().size(); i++){
                lcMessageBuildComponent component= actionRow.getComponent(i);
                if(component.isButton()&&component.toButton().getLabel().equals(label)){
                    actionRow.remComponent(i);
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent ignoreButtonById4ContainerAsComponent(String id,boolean allowexc){
        String fName="[ignoreButtonById4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(int i = 0; i< actionRow.getComponents().size(); i++){
                lcMessageBuildComponent component= actionRow.getComponent(i);
                component.setIgnored();
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent ignoreButtonByLabel4ContainerAsComponent(String label,boolean allowexc){
        String fName="[ignoreButtonByLabel4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(int i = 0; i< actionRow.getComponents().size(); i++){
                lcMessageBuildComponent component= actionRow.getComponent(i);
                if(component.isButton()&&component.toButton().getLabel().equals(label)){
                   component.setIgnored();
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent getSelectById4ContainerAsComponent(String id,boolean allowexc){
        String fName="[getSelectById4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(lcMessageBuildComponent component: actionRow.getComponents()){
                if(component.isSelectMenu()&&component.toSelectMenu().getCustomId().equals(id)){
                    return  component;
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }
    public lcMessageBuildComponent delSelectById4ContainerAsComponent(String id,boolean allowexc){
        String fName="[delButtonById4ContainerAsComponent]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            for(int i = 0; i< actionRow.getComponents().size(); i++){
                lcMessageBuildComponent component= actionRow.getComponent(i);
                if(component.isSelectMenu()&&component.toSelectMenu().getCustomId().equals(id)){
                    actionRow.remComponent(i);
                }
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return  new lcMessageBuildComponent();
            }
            return  null;
        }
    }

    public static Component.Type getComponentType(int type){
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String fName="[getComponentType]";
        try {
            logger.info(fName+".type="+type);
            Component.Type type2=Component.Type.UNKNOWN;
            switch (type){
                case 1:type2= Component.Type.ACTION_ROW;break;
                case 2:type2= Component.Type.BUTTON;break;
                case 3:type2= Component.Type.SELECTION_MENU;break;
            }
            return  type2;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return Component.Type.UNKNOWN;
        }
    }
    public static int getComponentType(Component.Type type){
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String fName="[getComponentType]";
        try {
            logger.info(fName+".type="+type.name());
            int type2=0;
            switch (type){
                case ACTION_ROW:type2= 1;break;
                case BUTTON:type2=2;break;
                case SELECTION_MENU:type2= 3;break;
                case UNKNOWN:type2= -1;break;
                default:type2=0;
            }
            return  type2;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    public static ButtonStyle getButtonStyle(int type){
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String fName="[getComponentType]";
        try {
            logger.info(fName+".type="+type);
            ButtonStyle type2=ButtonStyle.UNKNOWN;
            switch (type){
                case 1:type2= ButtonStyle.PRIMARY;break;
                case 2:type2= ButtonStyle.SECONDARY;break;
                case 3:type2= ButtonStyle.SUCCESS;break;
                case 4:type2= ButtonStyle.DANGER;break;
                case 5:type2= ButtonStyle.LINK;break;
            }
            return  type2;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return ButtonStyle.UNKNOWN;
        }
    }
    public static int getButtonStyle(ButtonStyle type){
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String fName="[getComponentType]";
        try {
            logger.info(fName+".type="+type.name());
            int type2=0;
            switch (type){
                case PRIMARY:type2= 1;break;
                case SECONDARY:type2=2;break;
                case SUCCESS:type2= 3;break;
                case DANGER:type2= 4;break;
                case LINK:type2= 5;break;
                case UNKNOWN:type2= -1;break;
                default:type2=0;
            }
            return  type2;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
}
