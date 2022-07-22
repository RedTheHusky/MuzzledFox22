package forRemoval.textadventure;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PLAYER {
    Logger logger = Logger.getLogger(getClass());
    public PLAYER(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public PLAYER(JSONObject jsonObject){
        String fName="[constructor]";
        try {
            set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  JSONObject jsonObject=new JSONObject();
    private String getString(String key){
        String fName="[getString]";
        try {
            logger.info(fName+"key="+key);
            String value=jsonObject.getString(key);
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private boolean getBoolean(String key){
        String fName="[getBoolean]";
        try {
            logger.info(fName+"key="+key);
            boolean value=jsonObject.getBoolean(key);
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean hasKey(String key){
        String fName="[hasKey]";
        try {
            logger.info(fName+"key="+key);
            boolean value=jsonObject.has(key);
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private int getInt(String key){
        String fName="[getInt]";
        try {
            logger.info(fName+"key="+key);
            int value=jsonObject.getInt(key);
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            jsonObject=new JSONObject();
           return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            this.jsonObject=jsonObject;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            JSONArray jsonAttributes=new JSONArray();
            for(ATTRIBUTE attribute:attributes){
                jsonAttributes.put(attribute.getJSON());
            }
            jsonObject.put(KEYS.attributes,jsonAttributes);
            jsonObject.put(KEYS.group,getGroupIndex());
            jsonObject.put(KEYS.pageIndex,getPageIndex());
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public interface KEYS{
        String attributes="attributes",
            group ="group_index",
            subgroup="subgroup_index",
            pageIndex="page_index";
        public interface ATTRIBUTE{
            String name= "name",
                    display="display",
                    description="description",
                    type="type",
                    value="value";
        }
    }
    public int getGroupIndex(){
        String fName="[getGroupIndex]";
        try {
            logger.info(fName);
            return getInt(KEYS.group);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getSubGroupIndex(){
        String fName="[getSubGroupIndex]";
        try {
            logger.info(fName);
            return getInt(KEYS.subgroup);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getPageIndex(){
        String fName="[getPageIndex]";
        try {
            logger.info(fName);
            return getInt(KEYS.pageIndex);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean hasGroupIndex(){
        String fName="[hasGroupIndex]";
        try {
            logger.info(fName);
            return hasKey(KEYS.group);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasSubGroupIndex(){
        String fName="[hasSubGroupIndex]";
        try {
            logger.info(fName);
            return hasKey(KEYS.subgroup);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasPageIndex(){
        String fName="[hasPageIndex]";
        try {
            logger.info(fName);
            return hasKey(KEYS.pageIndex);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public PLAYER setGroupIndex(int index){
        String fName="[setGroupIndex]";
        try {
            logger.info(fName+"index="+index);
            if(index<0){
                throw  new Exception("Index can't be bellow 0");
            }
            jsonObject.put(KEYS.group,index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PLAYER setSubGroupIndex(int index){
        String fName="[setSubGroupIndex]";
        try {
            logger.info(fName+"index="+index);
            if(index<0){
                throw  new Exception("Index can't be bellow 0");
            }
            jsonObject.put(KEYS.subgroup,index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PLAYER setPageIndex(int index){
        String fName="[setPageIndex]";
        try {
            logger.info(fName+"index="+index);
            if(index<0){
                throw  new Exception("Index can't be bellow 0");
            }
            jsonObject.put(KEYS.pageIndex,index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private List<ATTRIBUTE> attributes=new ArrayList<>();
    public List<ATTRIBUTE> getAttributes(){
        String fName="[getCAttributes]";
        try {
            logger.info(fName+"size="+ attributes.size());
            return  attributes;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int sizeAttributes(){
        String fName="[sizeAttributes]";
        try {
            int i=attributes.size();
            logger.info(fName+"size="+ i);
            return  i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyAttributes(){
        String fName="[isEmptyAttributes]";
        try {
            boolean i=attributes.isEmpty();
            logger.info(fName+"size="+ i);
            return  i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public ATTRIBUTE getAttribute(int index){
        String fName="[getAttribute]";
        try {
            logger.info(fName+"index="+ index);
            return  attributes.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ATTRIBUTE getAttribute(String name){
        String fName="[getAttribute]";
        try {
            logger.info(fName+"name="+ name);
            if(name==null||name.isBlank()){
                throw  new Exception("Name cant be null or blank");
            }
            for(ATTRIBUTE attribute:attributes){
                if(attribute.getName().equalsIgnoreCase(name)){
                    logger.info(fName+"found");
                    return attribute;
                }
            }
            logger.info(fName+"not found");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getAttributeIndex(String name){
        String fName="[getAttributeIndex]";
        try {
            logger.info(fName+"name="+ name);
            if(name==null||name.isBlank()){
                throw  new Exception("Name cant be null or blank");
            }
            int i=0;
            for(ATTRIBUTE attribute:attributes){
                if(attribute.getName().equalsIgnoreCase(name)){
                    logger.info(fName+"found");
                    return i;
                }
                i++;
            }
            logger.info(fName+"not found");
            return  -1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public PLAYER addAttribute(ATTRIBUTE attribute){
        String fName="[addAttribute]";
        try {
            if(attribute ==null){
                throw new Exception("Input can't be null");
            }
            this.attributes.add(attribute);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PLAYER clearAttributes( ){
        String fName="[clearAttributes]";
        try {
            if(attributes.isEmpty()){
                logger.info(fName+"Groups is empty, cant clear");
                return this;
            }
            attributes.clear();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ATTRIBUTE remAttribute(int index){
        String fName="[remAttribute]";
        try {
            logger.info(fName+"index="+ index);
            if(attributes.isEmpty()){
                throw new Exception("Pages is empty, cant remove");
            }
            if(index<0){
                throw new Exception("Index is less than 0");
            }
            if(index>=attributes.size()){
                throw new Exception("Index is equal or bigger to pages size");
            }
            return attributes.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PLAYER setAttribute(int index, ATTRIBUTE attribute){
        String fName="[setAttribute]";
        try {
            logger.info(fName+"index="+ index);
            if(this.attributes.isEmpty()){
                throw new Exception("Attributes is empty, cant set");
            }
            if(index<0){
                throw new Exception("Index is less than 0");
            }
            if(index>=attributes.size()){
                throw new Exception("Index is equal or bigger to pages size");
            }
            if(attribute==null){
                throw new Exception("Input can't be null");
            }
            attributes.set(index,attribute);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public static class ATTRIBUTE {
        Logger logger = Logger.getLogger(getClass());
        String cName="ATTRIBUTES@";
        private  JSONObject jsonObject=new JSONObject();
        public ATTRIBUTE(){
            String fName="[constructor]";
            logger.info(fName);
        }
        public ATTRIBUTE(JSONObject jsonObject){
            String fName="[constructor]";
            try {
                set(jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public boolean clear(){
            String fName="[clear]";
            try {
                jsonObject=new JSONObject();
                return true;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(cName+fName+"jsonObject is null");
                    return false;
                }
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                return true;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                String value=jsonObject.getString(KEYS.ATTRIBUTE.name);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getDisplay(){
            String fName="[getDisplay]";
            try {
                String value=jsonObject.getString(KEYS.ATTRIBUTE.display);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getDescription(){
            String fName="[getDescription]";
            try {
                String value=jsonObject.getString(KEYS.ATTRIBUTE.description);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

        public int getTypeAsInt(){
            String fName="[getTypeAsInt]";
            try {
                int value=jsonObject.getInt(KEYS.ATTRIBUTE.type);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public TEXTADVENTURE.TYPE_Attribute getType(){
            String fName="[getType]";
            try {
                int i=jsonObject.getInt(KEYS.ATTRIBUTE.type);
                logger.info(cName+fName+"i="+i);
                TEXTADVENTURE.TYPE_Attribute value= TEXTADVENTURE.TYPE_Attribute.valueByCode(i);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return TEXTADVENTURE.TYPE_Attribute.NotApplicable;
            }
        }
        public String getTypeAsString(){
            String fName="[getTypeAsString]";
            try {
                int i=jsonObject.getInt(KEYS.ATTRIBUTE.type);
                logger.info(cName+fName+"i="+i);
                TEXTADVENTURE.TYPE_Attribute value= TEXTADVENTURE.TYPE_Attribute.valueByCode(i);
                return value.getName();
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return TEXTADVENTURE.TYPE_Attribute.NotApplicable.getName();
            }
        }
    }
}
