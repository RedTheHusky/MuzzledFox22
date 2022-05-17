package models.lc.interaction.messagecomponent;

import dev.cerus.jdasc.components.ComponentType;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class lcMessageBuildComponents implements lcMessageComponent.CommonKeys{
    Logger logger = Logger.getLogger(getClass());
    public lcMessageBuildComponents(){
        String fName="[build]";
        logger.info(fName+".blank");
    }
    public lcMessageBuildComponents(JSONArray jsonArray){
        String fName="[build]";
        try {
            logger.info(fName+".creating");
            set(jsonArray);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected List<lcMessageBuildComponent>components=new ArrayList<>();
    public lcMessageBuildComponents set( JSONArray jsonArray){
        String fName="[set]";
        try {
            clear();
            logger.info(fName+".jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                components.add(new lcMessageBuildComponent(jsonArray.getJSONObject(i)));
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponents clear(){
        String fName="[clear]";
        try {
            components.clear();
            logger.info(fName+".cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray getJson(){
        String fName="[getJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            for(lcMessageBuildComponent component:components){
                jsonArray.put(component.getJson());
            }
            logger.info(fName + ".jsonArray=" + jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public int componentsSize(){
        String fName="[componentsSize]";
        try {
            return components.size();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<lcMessageBuildComponent> getComponents(){
        String fName="[getComponents]";
        try {
            return components;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent getComponent(int index){
        String fName="[getComponent]";
        try {
            logger.info(fName+"index="+index);
            return components.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponent getComponent(int index, boolean allowexc){
        String fName="[getComponent]";
        try {
            logger.info(fName+"index="+index);
            return components.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            if(allowexc){
                return new lcMessageBuildComponent();
            }else{
                return null;
            }
        }
    }
    public lcMessageBuildComponent removeComponent(int index){
        String fName="[removeComponent]";
        try {
            logger.info(fName+"index="+index);
            return components.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponents setComponent(int index,lcMessageBuildComponent component){
        String fName="[setComponent]";
        try {
            logger.info(fName+"index="+index);
            if(component==null){
                return null;
            }
            components.set(index,component);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcMessageBuildComponents addComponent(lcMessageBuildComponent component){
        String fName="[addComponent]";
        try {
            if(component==null){
                return null;
            }
            components.add(component);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public DataObject getAsDataObject(int index){
        String fName="[getAsDataObject]";
        try {
            DataObject dataObject=DataObject.fromJson(getComponent(index).getJson().toString());
            logger.info(fName+".dataObject="+dataObject.toString());
            return  dataObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<DataObject> getAsDataObjects(){
        String fName="[getAsDataObjects]";
        try {
            List<DataObject>dataObjects=new ArrayList<>();
            for(int i=0;i<components.size();i++){
                dataObjects.add(getAsDataObject(i));
            }
            return  dataObjects;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<net.dv8tion.jda.api.interactions.components.ActionRow> getAsActionRows(){
        String fName="[getAsActionRows]";
        try {
             List<net.dv8tion.jda.api.interactions.components.ActionRow>rows=new ArrayList<>();
             for(lcMessageBuildComponent component:components){
                 if(rows.size()>=5){
                     logger.warn("reached action row limit");
                     break;
                 }
                 ActionRow row=component.getAsActionRow();
                 if(!component.isIgnored()&&row!=null) rows.add(row);
             }
             return rows;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public static class Buttons {
        protected Logger logger = Logger.getLogger(lcMessageBuildComponents.class);
        protected String nName="Buttons@";
        public final int MAX_SIZE = Component.Type.BUTTON.getMaxPerRow();
        protected Buttons(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected Buttons(List<lcMessageBuildComponent.Button>buttons,lcMessageBuildComponent.ActionRow container){
            String fName="[build]";
            try {
                this.container=container;
                this.buttons=buttons;
                logger.info(nName+fName+".buttons.size="+buttons.size());
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        protected lcMessageBuildComponent.ActionRow container=null;
        protected List<lcMessageBuildComponent.Button>buttons=new ArrayList<>();
        public boolean isEmpty(){
            String fName="[isEmpty]";
            try {
                if(this.buttons==null)return true;
                boolean value=buttons.isEmpty();
                logger.info(nName+fName+".buttons.isEmpty="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  true;
            }
        }
        public int size(){
            String fName="[size]";
            try {
                if(this.buttons==null)return 0;
                int value=buttons.size();
                logger.info(nName+fName+".buttons.size="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  0;
            }
        }
        public boolean isSizeOver(){
            String fName="[isSizeOver]";
            try {
                boolean result=false;
                if(size()>MAX_SIZE){
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
        public List<lcMessageBuildComponent.Button> get(){
            String fName="[get]";
            try {
                if(this.buttons==null)return new ArrayList<>();
                logger.info(nName+fName+".buttons.size="+buttons.size());
                return buttons;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcMessageBuildComponent.Button getAt(int index){
            return getAt(index,false);
        }
        public lcMessageBuildComponent.Button getAt(int index,boolean allowexc){
            String fName="[getButtons]";
            try {
                logger.info(nName+fName+".buttons.size="+buttons.size());
                return buttons.get(index);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                if(allowexc){
                    return  new lcMessageBuildComponent.Button();
                }
                return  null;
            }
        }
        public lcMessageBuildComponents.Buttons add(lcMessageBuildComponent.Button button){
            String fName="[add]";
            try {
                if(isSizeOver()){
                    logger.warn(nName+fName+".size is over the max");
                }
                buttons.add(button);
                container.addComponent(button.toComponent());
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponents.Buttons set(int index, lcMessageBuildComponent.Button button){
            String fName="[set]";
            try {
                if(index<0||index>=size()){
                    throw  new Exception("Index invalid");
                }
                buttons.set(index,button);
                container.setComponent(index,button.toComponent());
                return this;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent.Button remove(int index){
            String fName="[remove]";
            try {
                if(index<0||index>=size()){
                    throw  new Exception("Index invalid");
                }
                lcMessageBuildComponent.Button button=buttons.remove(index);
                container.remComponent(index);
                return button;
            }catch (Exception e){
                logger.error(nName+fName+".exception=" + e);
                logger.error(nName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent.Button getById(String id){
            return  getById(id,false);
        }
        public lcMessageBuildComponent.Button getByLabel(String label){
            return  getByLabel(label,false);
        }
        public lcMessageBuildComponent.Button getById(String id, boolean allowexc){
            String fName="[getById]";
            try {
                for(lcMessageBuildComponent.Button button:buttons){
                    if(button.getCustomId().equals(id)){
                        logger.info("found");
                        return button;
                    }
                }
                logger.info("not found");
                if(allowexc){
                    return  new lcMessageBuildComponent.Button();
                }
                return  null;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                if(allowexc){
                    return  new lcMessageBuildComponent.Button();
                }
                return  null;
            }
        }
        public lcMessageBuildComponent.Button getByLabel(String label, boolean allowexc){
            String fName="[getByLabel]";
            try {
                for(lcMessageBuildComponent.Button button:buttons){
                    if(button.getLabel().equals(label)){
                        logger.info("found");
                        return button;
                    }
                }
                logger.info("not found");
                if(allowexc){
                    return  new lcMessageBuildComponent.Button();
                }
                return  null;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                if(allowexc){
                    return  new lcMessageBuildComponent.Button();
                }
                return  null;
            }
        }
        public Buttons setDisable() {
            String fName="[getByLabel]";
            try {
                for(lcMessageBuildComponent.Button button:buttons){
                    button.setDisable();
                }
                return  this;
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
    }
    public lcMessageBuildComponent.SelectMenu getSelectMenuAt(int index){
        String fName="[getSelectMenuAt]";
        try {
            logger.info(fName+"index="+index);
            lcMessageBuildComponent.SelectMenu selectMenu=components.get(index).getSelect();
            logger.info(fName+"selectMenu="+selectMenu.getCustomId());
            return selectMenu;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcMessageBuildComponent.SelectMenu.Option> getSelectMenuOptionsAt(int index){
        String fName="[getSelectMenuOptionsAt]";
        try {
            logger.info(fName+"index="+index);
            lcMessageBuildComponent.SelectMenu selectMenu=getSelectMenuAt(index);
            List <lcMessageBuildComponent.SelectMenu.Option>options=selectMenu.getOptions();
            logger.info(fName+"options="+options.size());
            return options;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public lcMessageBuildComponent.SelectMenu.Option getSelectMenuOptionsAt(int index_component,int index_option){
        String fName="[getSelectMenuOptionsAt]";
        try {
            logger.info(fName+"index_component="+index_component+" ,index_option="+index_option);
            List <lcMessageBuildComponent.SelectMenu.Option>options=getSelectMenuOptionsAt(index_component);
            lcMessageBuildComponent.SelectMenu.Option option=options.get(index_option);
            logger.info(fName+"option="+option.getLabel());
            return option;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcMessageBuildComponent.Button> getButtonsAt(int index){
        String fName="[getButtonAt]";
        try {
            logger.info(fName+"index="+index);
            lcMessageBuildComponent component=components.get(index);
            List<lcMessageBuildComponent.Button>buttons=component.getButtons();
            logger.info(fName+"buttons="+buttons.size());
            return buttons;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public lcMessageBuildComponent.Button getButtonAt(int index_component,int index_button){
        String fName="[getButtonAt]";
        try {
            logger.info(fName+"index_component="+index_component+", index_button="+index_button);
            List<lcMessageBuildComponent.Button>buttons=getButtonsAt(index_component);
            lcMessageBuildComponent.Button button=buttons.get(index_button);
            logger.info(fName+"button"+button.getLabel());
            return button;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isIgnored(int index) {
        String fName="[isIgnored]";
        try {
            logger.info(fName+"index="+index);
            boolean result=components.get(index).isIgnored();
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public void setIgnored(int index) {
        String fName="[setIgnored]";
        try {
            logger.info(fName+"index="+index);
            components.get(index).setIgnored();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public void setIgnored(int index,boolean value) {
        String fName="[setIgnored]";
        try {
            logger.info(fName+"index="+index+", value="+value);
            components.get(index).setIgnored(value);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
}
