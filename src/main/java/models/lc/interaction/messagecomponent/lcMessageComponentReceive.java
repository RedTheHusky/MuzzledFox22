package models.lc.interaction.messagecomponent;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.basicobjects.lcMemberObject;
import models.lc.discordentities.basicobjects.lcUserObject;
import models.lc.interaction.lcInteractionReceive;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.interactions.components.Component;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcMessageComponentReceive extends lcInteractionReceive {
    Logger logger = Logger.getLogger(getClass());
    public lcMessageComponentReceive(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcMessageComponentReceive(RawGatewayEvent event){
        String fName="build";
        logger.info(fName+".creating");
        set(event);
        setComponent();
    }
    public JSONObject getMessageJson(){
        String fName="getMessageJson";
        try {
            return jsonPayload.getJSONObject(llCommonKeys.lsMessageJsonKeys.keyMessage);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getMessageId(){
        String fName="getMessageId";
        try {
            JSONObject jsonObject=getMessageJson();
            return jsonObject.getString(llCommonKeys.lsMessageJsonKeys.keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getMessageIdAsLong(){
        String fName="getMessageIdAsLong";
        try {
            return Long.parseLong(getMessageId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public Message getMessage(){
        String fName="getMessage";
        try {
            TextChannel textChannel=getTextChannel();
            if(textChannel!=null){
                return textChannel.retrieveMessageById(getMessageIdAsLong()).complete();
            }
            PrivateChannel privateChannel=getPrivateChannel();
            if(privateChannel!=null){
                return privateChannel.retrieveMessageById(getMessageIdAsLong()).complete();
            }
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getMessageChannelId(){
        String fName="getMessageChannelId";
        try {
            JSONObject jsonObject=getMessageJson();
            return jsonObject.getString(llCommonKeys.lsMessageJsonKeys.keyChannelId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getMessageChannelIdAsLong(){
        String fName="getMessageChannelIdAsLong";
        try {
            return Long.parseLong(getMessageChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    private int type=0;
    private Button button=new Button();
    private  SelectMenu selectMenu=new SelectMenu();
    protected boolean setComponent(){
        String fName="setComponent";
        try {
            if(jsonData.has(llCommonKeys.lsComponentMessageInteraction.keyComponentType)){
                type=jsonData.optInt(llCommonKeys.lsComponentMessageInteraction.keyComponentType);
            }
            logger.info(fName+".type="+type);
            if(getComponentType()==Component.Type.BUTTON){
                button=new Button(jsonData);
            }
            if(getComponentType()==Component.Type.SELECTION_MENU){
                selectMenu=new SelectMenu(jsonData);
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Component.Type getComponentType(){
        String fName="[getComponentType]";
        try {
            logger.info(fName+".value="+type);
            return  lcMessageBuildComponent. getComponentType(type);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return Component.Type.UNKNOWN;
        }
    }
    public int getComponentTypeAsInt(){
        String fName="[getComponentTypeAsInt]";
        try {
            logger.info(fName+".value="+type);
            return  type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isButton(){
        String fName="[isButton]";
        try {
            if(getComponentType()== Component.Type.BUTTON){
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
            if(getComponentType()== Component.Type.SELECTION_MENU){
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
    public static class Button {
        Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
        String nName="Button@";
        protected Button(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected String customId="";
        protected  Message message=null;
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
                int type=0;
                if(jsonObject.has(lcMessageComponent.CommonKeys.keyType))type=jsonObject.optInt(lcMessageComponent.CommonKeys.keyType);
                if(jsonObject.has(llCommonKeys.lsComponentMessageInteraction.keyComponentType))type=jsonObject.optInt(llCommonKeys.lsComponentMessageInteraction.keyComponentType);
                if(type==2) {
                    if (jsonObject.has(lcMessageComponent.CommonKeys.keyCustomId))
                        customId = jsonObject.optString(lcMessageComponent.CommonKeys.keyCustomId);
                    return true;
                }
                return false;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
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
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.lsComponentMessageInteraction.keyComponentType,2);
                if(customId!=null)jsonObject.put(lcMessageComponent.CommonKeys.keyCustomId,customId);
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
    }
    public static class SelectMenu {
        Logger logger = Logger.getLogger(lcMessageBuildComponent.class);
        String nName="SelectMenu@";
        protected SelectMenu(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected String customId="";
        List<String> values=new ArrayList<>();
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
                int type=0;
                if(jsonObject.has(lcMessageComponent.CommonKeys.keyType))type=jsonObject.optInt(lcMessageComponent.CommonKeys.keyType);
                if(type== 3){
                    if(jsonObject.has(lcMessageComponent.SelectMenuKeys.keyCustomId))customId=jsonObject.optString(lcMessageComponent.SelectMenuKeys.keyCustomId);
                    if(jsonObject.has(llCommonKeys.lsComponentMessageInteraction.keyValues)){
                        JSONArray array=jsonObject.optJSONArray(llCommonKeys.lsComponentMessageInteraction.keyValues);
                        for(int i=0;i<array.length();i++){
                           values.add(array.getString(i));
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
                if(values==null||values.isEmpty()){
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
        public int size(){
            String fName="[size]";
            try {
                int size=values.size();
                logger.info(nName+fName+".size="+size);
                return size;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getValue(int inxed){
            String fName="[getValue]";
            try {
                logger.info(nName+fName+".index="+inxed);
                return  values.get(inxed);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public List<String>getValues(){
            String fName="[getValues]";
            try {
                logger.info(nName+fName+".values="+values.toString());
                return  values;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public JSONArray getValuesAsJson(){
            String fName="[getValuesAsJson]";
            try {
                logger.info(nName+fName+".list.size="+values.size());
                JSONArray jsonArray=new JSONArray();
                for(String value: values){
                    jsonArray.put(value);
                }
                logger.info(nName+fName+".jsonArray="+jsonArray.toString());
                return  jsonArray;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
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
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(lcMessageComponent.CommonKeys.keyType,3);
                if(customId!=null)jsonObject.put(llCommonKeys.lsComponentMessageInteraction.keyCustomId,customId);
                if(values!=null)jsonObject.put(llCommonKeys.lsComponentMessageInteraction.keyValues,getValuesAsJson());
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
            protected String custom_id="",value="";
            Message message=null;
            public Option(String custom_id,String value){
                String fName="[build]";
                try {
                    if(custom_id!=null)this.custom_id=custom_id;
                    if(value!=null)this.value=value;
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
                    if(jsonObject.has(lcMessageComponent.CommonKeys.keyCustomId))custom_id=jsonObject.optString(lcMessageComponent.CommonKeys.keyCustomId);
                    if(jsonObject.has(lcMessageComponent.SelectOptionKeys.keyValue))value=jsonObject.optString(lcMessageComponent.SelectOptionKeys.keyValue);
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
                    if(value==null||value.isBlank()){
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
                    logger.info(nName+fName+".value="+custom_id);
                    return  custom_id;
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
            public JSONObject getJson(){
                String fName="[getJson]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    if(custom_id!=null)jsonObject.put(lcMessageComponent.CommonKeys.keyCustomId,custom_id);
                    if(value!=null)jsonObject.put(lcMessageComponent.SelectOptionKeys.keyValue,value);
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
                logger.info(nName+fName+".list.size="+values.size());
                List<Option>options=new ArrayList<>();
                for(String value:values){
                    Option option=new Option(customId,value);
                    options.add(option);
                }
                return  options;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
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


}
