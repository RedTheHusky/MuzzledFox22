package models.lc.interaction.messagecomponent;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.Component;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcMessageComponent {
    Logger logger = Logger.getLogger(getClass());
    protected lcMessageComponent(){
        String fName="[build]";
        logger.info(fName+".blank");
    }
    public lcMessageComponent(JSONObject jsonObject){
        String fName="[build]";
        try {
            logger.info(fName+".creating");
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageComponent(JSONObject jsonObject, Message message){
        String fName="[build]";
        try {
            logger.info(fName+".creating");
            set(jsonObject,message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    private Message message=null;
    private Component.Type type=Component.Type.UNKNOWN;
    private Container container=new Container();
    private Button button=new Button();
    private SelectMenu selectMenu=new SelectMenu();
    private boolean set( JSONObject jsonObject){
        String fName="[set]";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            if(jsonObject.has(CommonKeys.keyType))type=lcMessageBuildComponent.getComponentType(jsonObject.optInt(CommonKeys.keyType));
            if(message!=null){
                if(isButton()){
                    button=new Button(jsonObject,message);
                }else
                if(isContainer()){
                    container=new Container(jsonObject,message);
                }else
                if(isSelectMenu()){
                    selectMenu=new SelectMenu(jsonObject,message);
                }
            }else{
                if(isButton()){
                    button=new Button(jsonObject);
                }else
                if(isContainer()){
                    container=new Container(jsonObject);
                }else
                if(isSelectMenu()){
                    selectMenu=new SelectMenu(jsonObject);
                }
            }

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean set( JSONObject jsonObject,Message message){
        String fName="[set]";
        try {
            if(!set(jsonObject)){logger.warn(fName + ".failed to set object");return false;}
            this.message=message;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Message getMessage(){
        String fName="[getMessage]";
        try {
            logger.info(fName+".value="+message.getId());
            return  message;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Component.Type getType(){
        String fName="[getType]";
        try {
            logger.info(fName+".value="+type);
            return type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return Component.Type.UNKNOWN;
        }
    }
    public int getTypeAsInt(){
        String fName="[getTypeAsInt]";
        try {
            int type=lcMessageBuildComponent.getComponentType(getType());
            logger.info(fName+".value="+type);
            return  type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isContainer(){
        String fName="[isContainer]";
        try {
            if(getType()==Component.Type.ACTION_ROW){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isButton(){
        String fName="[isButton]";
        try {
            if(getType()==Component.Type.BUTTON){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isSelectMenu(){
        String fName="[isSelectMenu]";
        try {
            if(getType()==Component.Type.SELECTION_MENU){
                logger.info(fName+".true");
                return  true;
            }else{
                logger.info(fName+".false");
                return  false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJson(){
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(CommonKeys.keyType,getTypeAsInt());
            if(isButton()){
                jsonObject.put(CommonKeys.keyStyle,button.getStyleAsInt());
                jsonObject.put(CommonKeys.keyLabel,button.getLabel());
                if(!button.isInvalid())jsonObject.put(CommonKeys.keyEmoji,button.getEmojiAsJson());
                if(button.isCommand()){
                    jsonObject.put(CommonKeys.keyCustomId,button.getCustomId());
                }else
                if(button.isUrl()){
                    jsonObject.put(CommonKeys.keyUrl,button.getUrl());
                }
            }else
            if(isContainer()){
                jsonObject.put(CommonKeys.keyComponents,container.getComponentsAsJson());
            }else
            if(isSelectMenu()){
                jsonObject.put(CommonKeys.keyComponents,selectMenu.getOptionsAsJson());
            }
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public Container toContainer(){
        String fName="[toContainer]";
        try {
            if(!isContainer()){
                logger.warn(fName+".not a container");
                return  null;
            }
            return container;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public static class Container{
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String nName="Container@";
        protected Container(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        List<lcMessageComponent>components=new ArrayList<>();
        private Message message=null;
        public Container(JSONObject jsonObject){
            String fName="[build]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public Container(JSONObject jsonObject,Message message){
            String fName="[build]";
            try {
                if(!set(jsonObject)){return;}
                this.message=message;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=1;
                if(jsonObject.has(CommonKeys.keyType))type=jsonObject.optInt(CommonKeys.keyType);
                if(type==1){
                    if(jsonObject.has(CommonKeys.keyComponents)){
                        set(jsonObject.optJSONArray(CommonKeys.keyComponents));
                    }
                }else{
                    logger.warn(nName+fName + ".json is not a container>failed to build");
                    return false;
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Container(JSONArray jsonArray){
            String fName="[build]";
            try {
               set(jsonArray);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public Container(JSONArray jsonArray,Message message){
            String fName="[build]";
            try {
                if(!set(jsonArray)){return;}
                this.message=message;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean set( JSONArray jsonArray){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    lcMessageComponent component=new lcMessageComponent(jsonObject);
                    components.add(component);
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public List<lcMessageComponent> getComponents(){
            String fName="[getComponents]";
            try {
                logger.info(nName+fName+".list.size="+components.size());
                return  components;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public JSONArray getComponentsAsJson(){
            String fName="[getComponentsAsJson]";
            try {
                logger.info(nName+fName+".list.size="+components.size());
                JSONArray jsonArray=new JSONArray();
                for(lcMessageComponent component: components){
                    jsonArray.put(component.getJson());
                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public Message getMessage(){
            String fName="[getMessage]";
            try {
                logger.info(nName+fName+".value="+message.getId());
                return  message;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(CommonKeys.keyType,1);
                if(components!=null)jsonObject.put(CommonKeys.keyComponents,getComponentsAsJson());
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public lcMessageComponent getAsMessageComponent(){
            String fName="[getAsMessageComponent]";
            try {
                return  new lcMessageComponent(getJson());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public static class Button {
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String nName="Button@";
        protected Button(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected String customId="",label="",url="";
        protected ButtonStyle style=ButtonStyle.UNKNOWN;
        protected JSONObject emoji=new JSONObject();
        boolean disabled=false;
        Message message=null;
        public Button(JSONObject jsonObject){
            String fName="[build]";
            try {
               set(jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public Button(JSONObject jsonObject,Message message){
            String fName="[build]";
            try {
                if(!set(jsonObject)){return;}
                this.message=message;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=2;
                if(jsonObject.has(CommonKeys.keyType))type=jsonObject.optInt(CommonKeys.keyType);
                if(type== 2){
                    if(jsonObject.has(CommonKeys.keyStyle))style=lcMessageBuildComponent.getButtonStyle(jsonObject.optInt(CommonKeys.keyStyle));
                    if(jsonObject.has(CommonKeys.keyLabel))label=jsonObject.optString(CommonKeys.keyLabel);
                    if(isCommand()){
                        if(jsonObject.has(CommonKeys.keyCustomId))customId=jsonObject.optString(CommonKeys.keyCustomId);
                    }else
                    if(isUrl()){
                        if(jsonObject.has(CommonKeys.keyUrl))label=jsonObject.optString(CommonKeys.keyUrl);
                    }
                    if(jsonObject.has(CommonKeys.keyEmoji))emoji=jsonObject.optJSONObject(CommonKeys.keyEmoji);
                    if(jsonObject.has(CommonKeys.keyDisabled))disabled=jsonObject.optBoolean(CommonKeys.keyDisabled);
                }else{
                    logger.warn(nName+fName + ".component is not a button>failed to build");
                    return false;
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int getStyleAsInt(){
            String fName="[getStyleAsInt]";
            try {
                logger.info(nName+fName+".value="+style);
                return  lcMessageBuildComponent.getButtonStyle(style);
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public ButtonStyle getStyle(){
            String fName="[getStyle]";
            try {
                return  style;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return ButtonStyle.UNKNOWN;
            }
        }
        public boolean isCommand(){
            String fName="[isCommand]";
            try {
                if(style==ButtonStyle.PRIMARY||style==ButtonStyle.SECONDARY||style==ButtonStyle.SUCCESS||style==ButtonStyle.DANGER){
                    logger.info(nName+fName+".true");
                    return  true;
                }else{
                    logger.info(nName+fName+".false");
                    return  false;
                }
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isUrl(){
            String fName="[isUrl]";
            try {
                if(style==ButtonStyle.LINK){
                    logger.info(nName+fName+".true");
                    return  true;
                }else{
                    logger.info(nName+fName+".false");
                    return  false;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getLabel(){
            String fName="[getLabel]";
            try {
                logger.info(nName+fName+".value="+label);
                return  label;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getCustomId(){
            String fName="[getCustomId]";
            try {
                logger.info(nName+fName+".value="+customId);
                return  customId;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getUrl(){
            String fName="[getUrl]";
            try {
                if(!isUrl()){
                    logger.warn(nName+fName+".not a button");
                    return  "";
                }
                logger.info(nName+fName+".value="+url);
                return  url;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean isDisabled(){
            String fName="[isDisabled]";
            try {
                logger.info(nName+fName+".value="+disabled);
                return  disabled;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isEmoji(){
            String fName="[isEmoji]";
            try {
                if(emoji==null){
                    logger.warn(fName + ".emoji cant be null");return false;
                }
                if(emoji.isEmpty()){
                    logger.warn(fName + ".emoji is empty");return false;
                }
                if(emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)){
                    logger.warn(fName + ".key id missing");return false;
                }
                if(emoji.has(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName)){
                    logger.warn(fName + ".key name missing");return false;
                }
                if(emoji.optLong(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)<=0){
                    logger.warn(fName + ".id equal or bellow 0");return false;
                }
                if(emoji.optString(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName).isBlank()){
                    logger.warn(fName + "name is blank");return false;
                }
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getEmojiAsJson(){
            String fName="[getEmojiAsJson]";
            try {
                logger.info(nName+fName+".value="+emoji.toString());
                return  emoji;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(CommonKeys.keyType,2);
                if(style!=ButtonStyle.UNKNOWN)jsonObject.put(CommonKeys.keyStyle,getStyleAsInt());
                if(label!=null)jsonObject.put(CommonKeys.keyLabel,label);
                if(isCommand()){
                    if(customId!=null)jsonObject.put(CommonKeys.keyCustomId,customId);
                }else
                if(isUrl()){
                    if(url!=null)jsonObject.put(CommonKeys.keyUrl,url);
                }
                if(disabled)jsonObject.put(CommonKeys.keyDisabled,disabled);
                if(emoji!=null&&!emoji.isEmpty())jsonObject.put(CommonKeys.keyEmoji,emoji);
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public lcMessageComponent getAsMessageComponent(){
            String fName="[getAsMessageComponent]";
            try {
                return  new lcMessageComponent(getJson());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public static class SelectMenu {
        Logger logger = Logger.getLogger(lcMessageComponent.class);
        String nName="SelectMenu@";
        protected SelectMenu(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected String customId="",placeholder="";
        int min_values=1,max_values=1;
        List<Option>options=new ArrayList<>();
        Message message=null;
        public SelectMenu(JSONObject jsonObject){
            String fName="[build]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public SelectMenu(JSONObject jsonObject,Message message){
            String fName="[build]";
            try {
                if(!set(jsonObject)){return;}
                this.message=message;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean set( JSONObject jsonObject){
            String fName="[set]";
            try {
                logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                int type=3;
                if(jsonObject.has(CommonKeys.keyType))type=jsonObject.optInt(CommonKeys.keyType);
                if(type==3){
                    if(jsonObject.has(SelectMenuKeys.keyCustomId))customId=jsonObject.optString(SelectMenuKeys.keyCustomId);
                    if(jsonObject.has(SelectMenuKeys.keyPlaceholder))placeholder=jsonObject.optString(SelectMenuKeys.keyPlaceholder);
                    if(jsonObject.has(SelectMenuKeys.keyMinValues))min_values=jsonObject.optInt(SelectMenuKeys.keyMinValues);
                    if(jsonObject.has(SelectMenuKeys.keyMaxValues))max_values=jsonObject.optInt(SelectMenuKeys.keyMaxValues);
                    if(jsonObject.has(SelectMenuKeys.keyOptions)){
                        JSONArray array=jsonObject.optJSONArray(SelectMenuKeys.keyOptions);
                        for(int i=0;i<array.length();i++){
                            JSONObject item=array.getJSONObject(i);
                            Option option=new Option(item);
                            options.add(option);
                        }
                    }
                }else{
                    logger.warn(nName+fName + ".component is not a selectmenu>failed to build");
                    return false;
                }
                return true;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
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
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public String getCustomId(){
            String fName="[getCustomId]";
            try {
                logger.info(nName+fName+".value="+customId);
                return  customId;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getPlaceholder(){
            String fName="[getPlaceholder]";
            try {
                logger.info(nName+fName+".value="+placeholder);
                return  customId;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public int getMinValues(){
            String fName="[getMinValues]";
            try {
                logger.info(nName+fName+".value="+min_values);
                return  min_values;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getMaxValues(){
            String fName="[getMaxValues]";
            try {
                logger.info(nName+fName+".value="+max_values);
                return  max_values;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(CommonKeys.keyType,3);
                if(customId!=null)jsonObject.put(CommonKeys.keyCustomId,customId);
                if(placeholder!=null)jsonObject.put(SelectMenuKeys.keyPlaceholder,placeholder);
                if(min_values>-1)jsonObject.put(SelectMenuKeys.keyMinValues,min_values);
                if(max_values>0)jsonObject.put(SelectMenuKeys.keyMaxValues,max_values);
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public static class Option {
            Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
            String nName="SelectMenu_Option@";
            protected Option(){
                String fName="[build]";
                logger.info(nName+fName+".blank");
            }
            protected String label="",value="",description="";
            protected JSONObject emoji=new JSONObject();
            boolean isdefault=false;
            Message message=null;
            public Option(String label,String value,String description,JSONObject emoji,boolean isdefault){
                String fName="[build]";
                try {
                   if(label!=null)this.label=label;
                   if(value!=null)this.value=value;
                   if(description!=null)this.description=description;
                   if(emoji!=null)this.emoji=emoji;
                   this.isdefault=isdefault;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(JSONObject jsonObject){
                String fName="[build]";
                try {
                    set(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Option(JSONObject jsonObject,Message message){
                String fName="[build]";
                try {
                    if(!set(jsonObject)){return;}
                    this.message=message;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            private boolean set( JSONObject jsonObject){
                String fName="[set]";
                try {
                    logger.info(nName+fName+".jsonObject="+jsonObject.toString());
                    if(jsonObject.has(SelectOptionKeys.keyLabel))label=jsonObject.optString(SelectOptionKeys.keyLabel);
                    if(jsonObject.has(SelectOptionKeys.keyValue))value=jsonObject.optString(SelectOptionKeys.keyValue);
                    if(jsonObject.has(SelectOptionKeys.keyDescription))description=jsonObject.optString(SelectOptionKeys.keyDescription);
                    if(jsonObject.has(SelectOptionKeys.keyEmoji))emoji=jsonObject.optJSONObject(SelectOptionKeys.keyEmoji);
                    if(jsonObject.has(SelectOptionKeys.keyDefault))isdefault=jsonObject.optBoolean(SelectOptionKeys.keyDefault);
                    return true;
                }catch (Exception e){
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public String getLabel(){
                String fName="[getLabel]";
                try {
                    logger.info(nName+fName+".value="+label);
                    return  label;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public String getValue(){
                String fName="[getValue]";
                try {
                    logger.info(nName+fName+".value="+value);
                    return  value;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public String getDescription(){
                String fName="[getDescription]";
                try {
                    logger.info(nName+fName+".value="+description);
                    return  description;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return "";
                }
            }
            public JSONObject getEmojiAsJson(){
                String fName="[getEmojiAsJson]";
                try {
                    logger.info(nName+fName+".value="+emoji.toString());
                    return  emoji;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            public JSONObject getJson(){
                String fName="[getJson]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    if(label!=null)jsonObject.put(SelectOptionKeys.keyLabel,label);
                    if(value!=null)jsonObject.put(SelectOptionKeys.keyValue,value);
                    if(description!=null)jsonObject.put(SelectOptionKeys.keyDescription,description);
                    if(isdefault)jsonObject.put(SelectOptionKeys.keyDefault,true);
                    if(emoji!=null&&!emoji.isEmpty())jsonObject.put(CommonKeys.keyEmoji,emoji);
                    logger.info(fName + ".jsonObject=" + jsonObject.toString());
                    return jsonObject;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
        }
        public List<Option> getOptions(){
            String fName="[getOptions]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                return  options;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public JSONArray getOptionsAsJson(){
            String fName="[getOptionsAsJson]";
            try {
                logger.info(nName+fName+".list.size="+options.size());
                JSONArray jsonArray=new JSONArray();
                for(Option option: options){
                    jsonArray.put(option.getJson());
                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        public lcMessageComponent getAsMessageComponent(){
            String fName="[getAsMessageComponent]";
            try {
                return  new lcMessageComponent(getJson());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }


    /*public enum ButtonStyles {
        INVALID(0,"","",false,false),
        Primary(1,"primary","blurple",true,false),
        Secondary(2,"secondary","grey",true,false),
        Success(3,"success","green",true,false),
        Danger(4,"danger","red",true,false),
        Link(5,"link","grey",false,true);

        private String name="",color="";
        boolean isCustom=false,isUrl=false;
        private int code=0;

        private ButtonStyles(int code, String name, String color,boolean isCustom,boolean isUrl) {
            this.code = code;
            this.name = name;
            this.color=color;
            this.isCustom=isCustom;
            this.isUrl=isUrl;
        }
        public static ButtonStyles valueByCode(int code) {
            ButtonStyles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ButtonStyles status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static ButtonStyles valueByName(String name) {
            ButtonStyles[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ButtonStyles status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getName() {
            return this.name;
        }
        public String getColor() {
            return this.color;
        }
        public boolean isCustom() { return this.isCustom;}
        public boolean isUrl() { return this.isUrl;}
        public int getCode() {
            return this.code;
        }
        static {
            ButtonStyles[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                ButtonStyles s = var0[var2];
            }

        }
        public static String getName(ButtonStyles gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(ButtonStyles.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }*/

    /*public enum ComponentType {
        INVALID(0,""),
        ActionRow(1,"Action Row"),
        Button(2,"Button"),
        SelectMenu(3,"Select Menu");


        private String name="";
        private int code=0;

        private ComponentType(int code, String name) {
            this.code = code;
            this.name = name;
        }
        public static ComponentType valueByCode(int code) {
            ComponentType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ComponentType status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
        public static ComponentType valueByName(String name) {
            ComponentType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ComponentType status = var1[var3];
                if (status.name.equals(name)) {
                    return status;
                }
            }
            return null;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            ComponentType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                ComponentType s = var0[var2];
            }

        }
        public static String getName(ComponentType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(ComponentType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }*/
    public interface CommonKeys {
        //https://discord.com/developers/docs/interactions/message-components#buttons-button-object
        String keyCustomId="custom_id",
                keyType="type",
                keyStyle="style",
                keyLabel="label",
                keyUrl="url",
                keyComponents="components",
                keyEmoji="emoji",
                keyDisabled="disabled";

    }
    public interface SelectMenuKeys {
        //https://discord.com/developers/docs/interactions/message-components#select-menu-object-select-menu-structure
        String keyCustomId="custom_id",
                keyOptions="options",
                keyPlaceholder="placeholder",
                keyMinValues="min_values",
                keyMaxValues="max_values",
                keyDisabled="disabled";

    }
    public interface SelectOptionKeys {
        //https://discord.com/developers/docs/interactions/message-components#buttons-button-object
        String keyValue="value",
                keyDescription="description",
                keyDefault="default",
                keyLabel="label",
                keyEmoji="emoji";

    }
}
