package forRemoval.textadventure;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcEmbedBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponents;
import models.ll.llCommonKeys;
import nsfw.lovense.iLovense;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TEXTADVENTURE {
    Logger logger = Logger.getLogger(getClass());
    public TEXTADVENTURE(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public TEXTADVENTURE(JSONObject jsonObject){
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
            String key="";
            key= KEYS.groups;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                JSONArray jsonArray=jsonObject.optJSONArray(key);
                for(int i=0;i<jsonArray.length();i++){
                    groups.add(new GROUP(jsonArray.getJSONObject(i)));
                }
            }
            key= KEYS.attributes;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                JSONArray jsonArray=jsonObject.optJSONArray(key);
                for(int i=0;i<jsonArray.length();i++){
                    attributes.add(new ATTRIBUTE(jsonArray.getJSONObject(i)));
                }
            }
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
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public interface KEYS{
        String title= "title",
            nsfw="nsfw",
            groups="groups",
            attributes="attributes";
    }
    public interface KEYS_Attribute{
        String name= "name",
                display="display",
                description="description",
                command="command",
                type="type",
                value="value";
    }
    public enum TYPE_Attribute {
        NotApplicable(0,"Unknown"),
        String(1,"str"),
        Integer(2,"int"),
        Long(3,"long"),
        Double(4,"double"),
        Boolean(5,"boolean");
        private String name="";
        private int code=-1;
        private TYPE_Attribute(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static TYPE_Attribute valueByCode(int code) {
            TYPE_Attribute[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                TYPE_Attribute status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public static TYPE_Attribute valueByName(String name) {
            TYPE_Attribute[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                TYPE_Attribute status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            TYPE_Attribute[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                TYPE_Attribute s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum COMMAND_Attribute {
        NotApplicable(0,"Unknown"),
        Set(1,"set"),
        Add(2,"add"),
        Sub(3,"sub");
        private String name="";
        private int code=-1;
        private COMMAND_Attribute(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static COMMAND_Attribute valueByCode(int code) {
            COMMAND_Attribute[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                COMMAND_Attribute status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public static COMMAND_Attribute valueByName(String name) {
            COMMAND_Attribute[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                COMMAND_Attribute status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            COMMAND_Attribute[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                COMMAND_Attribute s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    public enum COMPONENT_COMMAND {
        NotApplicable(0,"Unknown"),
        GroupJump(1,"group_jump"),
        GroupNext(2,"group_next"),
        GroupBack(3,"group_back"),
        SubGroupJump(4,"subgroup_jump"),
        SubGroupNext(5,"subgroup_next"),
        SubGroupBack(6,"subgroup_back"),
        PageJump(7,"page_jump"),
        PageNext(8,"page_next"),
        PageBack(9,"page_back"),
        New(10,"new");
        private String name="";
        private int code=-1;
        private COMPONENT_COMMAND(int code, String name) {
            this.code = code;
            this.name = name;

        }
        public static COMPONENT_COMMAND valueByCode(int code) {
            COMPONENT_COMMAND[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                COMPONENT_COMMAND status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public static COMPONENT_COMMAND valueByName(String name) {
            COMPONENT_COMMAND[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                COMPONENT_COMMAND status = var1[var3];
                if (status.name.equalsIgnoreCase(name)) {
                    return status;
                }
            }
            return NotApplicable;
        }
        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
        static {
            COMPONENT_COMMAND[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                COMPONENT_COMMAND s = var0[var2];
            }

        }
        public static String getName(iLovense.ToyType gag){
            String fName="[getName]";
            Logger logger = Logger.getLogger(iLovense.ToyType.class);
            try {
                return gag.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

    }
    private List<GROUP> groups =new ArrayList<>();
    private List<ATTRIBUTE> attributes=new ArrayList<>();
    public String getTitle(){
        String fName="[getTitle]";
        try {
            logger.info(fName);
            return getString(KEYS.title);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isNsfw(){
        String fName="[isNsfw]";
        try {
            logger.info(fName);
            return getBoolean(KEYS.nsfw);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public TEXTADVENTURE setTitle(String title){
        String fName="[setTitle]";
        try {
            logger.info(fName+"input="+ title);
            if(title==null){
                throw new Exception("Input can't be null");
            }
            jsonObject.put(KEYS.title,title);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TEXTADVENTURE setNsfw(boolean nsfw){
        String fName="[setNsfw]";
        try {
            logger.info(fName+"input="+ nsfw);
            jsonObject.put(KEYS.nsfw,nsfw);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<GROUP> getChapters(){
        String fName="[getChapters]";
        try {
            logger.info(fName+"size="+ groups.size());
            return groups;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int sizeChapters(){
        String fName="[sizeChapters]";
        try {
            int i= groups.size();
            logger.info(fName+"size="+ i);
            return  i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyChapters(){
        String fName="[isEmptyChapters]";
        try {
            boolean i= groups.isEmpty();
            logger.info(fName+"size="+ i);
            return  i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public GROUP getChapter(int index){
        String fName="[getChapter]";
        try {
            logger.info(fName+"index="+ index);
            return  groups.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TEXTADVENTURE addChapter( GROUP group){
        String fName="[addChapter]";
        try {
            if(group ==null){
                throw new Exception("Input can't be null");
            }
            groups.add(group);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TEXTADVENTURE clearChapters( ){
        String fName="[clearChapters]";
        try {
            if(groups.isEmpty()){
                logger.info(fName+"Chapters is empty, cant clear");
                return this;
            }
            groups.clear();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GROUP remChapter(int index){
        String fName="[remChapter]";
        try {
            logger.info(fName+"index="+ index);
            if(groups.isEmpty()){
                throw new Exception("Pages is empty, cant remove");
            }
            if(index<0){
                throw new Exception("Index is less than 0");
            }
            if(index>= groups.size()){
                throw new Exception("Index is equal or bigger to pages size");
            }
            return groups.remove(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TEXTADVENTURE setChapter(int index, GROUP group){
        String fName="[setChapter]";
        try {
            logger.info(fName+"index="+ index);
            if(groups.isEmpty()){
                throw new Exception("Pages is empty, cant set");
            }
            if(index<0){
                throw new Exception("Index is less than 0");
            }
            if(index>= groups.size()){
                throw new Exception("Index is equal or bigger to pages size");
            }
            if(group ==null){
                throw new Exception("Input can't be null");
            }
            groups.set(index, group);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public static class GROUP {
        Logger logger = Logger.getLogger(getClass());
        String cName="GROUP@";
        public interface KEYS{
            String pages= "pages",
                    attributes="attributes",
                    title="title",
                    subgroup = "subgroup";
        }
        public GROUP(){
            String fName="[constructor]";
            logger.info(fName);
        }
        public GROUP(JSONObject jsonObject){
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
        private List<PAGE> pages=new ArrayList<>();
        private List<SUBGROUP> subgroups=new ArrayList<>();
        public String getTitle(){
            String fName="[getTitle]";
            try {
                logger.info(cName+fName);
                return getString(KEYS.title);
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<PAGE> getPages(){
            String fName="[getPages]";
            try {
                logger.info(cName+fName+"size="+ pages.size());
                return  pages;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int sizePages(){
            String fName="[sizePages]";
            try {
                int i=pages.size();
                logger.info(cName+fName+"size="+ i);
                return  i;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isEmptyPages(){
            String fName="[isEmptyPages]";
            try {
                boolean i=pages.isEmpty();
                logger.info(cName+fName+"size="+ i);
                return  i;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public PAGE getPage(int index){
            String fName="[getPage]";
            try {
                logger.info(cName+fName+"index="+ index);
                return  pages.get(index);
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP addPage(PAGE page){
            String fName="[addPage]";
            try {
                if(page==null){
                    throw new Exception("Input can't be null");
                }
                pages.add(page);
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP clearPages( ){
            String fName="[clearPages]";
            try {
                if(pages.isEmpty()){
                    logger.info(cName+fName+"Pages is empty, cant clear");
                    return this;
                }
                pages.clear();
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public PAGE remPage(int index){
            String fName="[remPage]";
            try {
                logger.info(cName+fName+"index="+ index);
                if(pages.isEmpty()){
                    throw new Exception("Pages is empty, cant remove");
                }
                if(index<0){
                    throw new Exception("Index is less than 0");
                }
                if(index>=pages.size()){
                    throw new Exception("Index is equal or bigger to pages size");
                }
                return pages.remove(index);
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP setPage(int index, PAGE page){
            String fName="[setPage]";
            try {
                logger.info(cName+fName+"index="+ index);
                if(pages.isEmpty()){
                    throw new Exception("Pages is empty, cant set");
                }
                if(index<0){
                    throw new Exception("Index is less than 0");
                }
                if(index>=pages.size()){
                    throw new Exception("Index is equal or bigger to pages size");
                }
                if(page==null){
                    throw new Exception("Input can't be null");
                }
                pages.set(index,page);
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int sizeSubGroup(){
            String fName="[sizePages]";
            try {
                int i=pages.size();
                logger.info(cName+fName+"size="+ i);
                return  i;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isEmptySubGroup(){
            String fName="[isEmptySubGroup]";
            try {
                boolean i=subgroups.isEmpty();
                logger.info(cName+fName+"size="+ i);
                return  i;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public SUBGROUP getSubGroup(int index){
            String fName="[getSubGroup]";
            try {
                logger.info(cName+fName+"index="+ index);
                return  subgroups.get(index);
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP addSubGroup(SUBGROUP subgroup){
            String fName="[addSubGroup]";
            try {
                if(subgroup==null){
                    throw new Exception("Input can't be null");
                }
                subgroups.add(subgroup);
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP clearSubGroup( ){
            String fName="[clearSubGroup]";
            try {
                if(subgroups.isEmpty()){
                    logger.info(cName+fName+"Pages is empty, cant clear");
                    return this;
                }
                subgroups.clear();
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SUBGROUP remSubGroup(int index){
            String fName="[remSubGroup]";
            try {
                logger.info(cName+fName+"index="+ index);
                if(subgroups.isEmpty()){
                    throw new Exception("Pages is empty, cant remove");
                }
                if(index<0){
                    throw new Exception("Index is less than 0");
                }
                if(index>=subgroups.size()){
                    throw new Exception("Index is equal or bigger to pages size");
                }
                return subgroups.remove(index);
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public GROUP setSubGroup(int index, SUBGROUP subgroup){
            String fName="[setSubGroup]";
            try {
                logger.info(cName+fName+"index="+ index);
                if(subgroups.isEmpty()){
                    throw new Exception("Pages is empty, cant set");
                }
                if(index<0){
                    throw new Exception("Index is less than 0");
                }
                if(index>=subgroups.size()){
                    throw new Exception("Index is equal or bigger to pages size");
                }
                if(subgroup==null){
                    throw new Exception("Input can't be null");
                }
                subgroups.set(index,subgroup);
                return this;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean clear(){
            String fName="[clear]";
            try {
                jsonObject=new JSONObject();
                if(pages==null)pages=new ArrayList<>();
                else pages.clear();
                if(subgroups==null)subgroups=new ArrayList<>();
                else subgroups.clear();
                if(attributes==null)attributes=new ArrayList<>();
                else attributes.clear();
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
                String key="";
                key=KEYS.pages;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                    JSONArray jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        pages.add(new PAGE(jsonArray.getJSONObject(i)));
                    }
                }
                key=KEYS.subgroup;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                    JSONArray jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        subgroups.add(new SUBGROUP(jsonArray.getJSONObject(i)));
                    }
                }
                key= KEYS.attributes;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                    JSONArray jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        attributes.add(new ATTRIBUTE(jsonArray.getJSONObject(i)));
                    }
                }
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
                logger.info(cName+"jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public static class PAGE {
            Logger logger = Logger.getLogger(getClass());
            String cName="PAGE@";
            public  JSONObject jsonObject=new JSONObject();
            public lcEmbedBuilder embedBuilder=new lcEmbedBuilder();
            public lcMessageBuildComponents messageBuildComponents=new lcMessageBuildComponents();
            public interface KEYS{
                String components= llCommonKeys.lsMessageJsonKeys.keyComponents,
                        content= llCommonKeys.lsMessageJsonKeys.keyContent,
                        embeds= llCommonKeys.lsMessageJsonKeys.keyEmbeds,
                        embed= "embed";
            }
            public PAGE(){
                String fName="[constructor]";
                logger.info(fName);
            }
            public PAGE(JSONObject jsonObject){
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
                    messageBuildComponents=new lcMessageBuildComponents();
                    embedBuilder=new lcEmbedBuilder();
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
                    String key="";
                    key= KEYS.components;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        messageBuildComponents.set(jsonObject.optJSONArray(key));
                    }
                    key= KEYS.embed;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        embedBuilder.set(jsonObject.optJSONObject(key));
                    }

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

        }
        public static class SUBGROUP {
            Logger logger = Logger.getLogger(getClass());
            String cName="SUBGROUP@";
            public interface KEYS{
                String pages= "pages",
                        attributes="attributes",
                        title="title";
            }
            public SUBGROUP(){
                String fName="[constructor]";
                logger.info(fName);
            }
            public SUBGROUP(JSONObject jsonObject){
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
            private List<PAGE> pages=new ArrayList<>();
            public String getTitle(){
                String fName="[getTitle]";
                try {
                    logger.info(cName);
                    return getString(KEYS.title);
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public List<PAGE> getPages(){
                String fName="[getPages]";
                try {
                    logger.info(cName+fName+"size="+ pages.size());
                    return  pages;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public int sizePages(){
                String fName="[sizePages]";
                try {
                    int i=pages.size();
                    logger.info(cName+fName+"size="+ i);
                    return  i;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public boolean isEmptyPages(){
                String fName="[isEmptyPages]";
                try {
                    boolean i=pages.isEmpty();
                    logger.info(cName+fName+"size="+ i);
                    return  i;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return true;
                }
            }
            public PAGE getPage(int index){
                String fName="[getPage]";
                try {
                    logger.info(cName+fName+"index="+ index);
                    return  pages.get(index);
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public SUBGROUP addPage(PAGE page){
                String fName="[addPage]";
                try {
                    if(page==null){
                        throw new Exception("Input can't be null");
                    }
                    pages.add(page);
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public SUBGROUP clearPages( ){
                String fName="[clearPages]";
                try {
                    if(pages.isEmpty()){
                        logger.info(cName+fName+"Pages is empty, cant clear");
                        return this;
                    }
                    pages.clear();
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public PAGE remPage(int index){
                String fName="[remPage]";
                try {
                    logger.info(cName+fName+"index="+ index);
                    if(pages.isEmpty()){
                        throw new Exception("Pages is empty, cant remove");
                    }
                    if(index<0){
                        throw new Exception("Index is less than 0");
                    }
                    if(index>=pages.size()){
                        throw new Exception("Index is equal or bigger to pages size");
                    }
                    return pages.remove(index);
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public SUBGROUP setPage(int index, PAGE page){
                String fName="[setPage]";
                try {
                    logger.info(cName+fName+"index="+ index);
                    if(pages.isEmpty()){
                        throw new Exception("Pages is empty, cant set");
                    }
                    if(index<0){
                        throw new Exception("Index is less than 0");
                    }
                    if(index>=pages.size()){
                        throw new Exception("Index is equal or bigger to pages size");
                    }
                    if(page==null){
                        throw new Exception("Input can't be null");
                    }
                    pages.set(index,page);
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean clear(){
                String fName="[clear]";
                try {
                    jsonObject=new JSONObject();
                    if(pages==null)pages=new ArrayList<>();
                    else pages.clear();
                    if(attributes==null)attributes=new ArrayList<>();
                    else attributes.clear();
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
                    String key="";
                    key= TEXTADVENTURE.GROUP.KEYS.pages;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                        JSONArray jsonArray=jsonObject.optJSONArray(key);
                        for(int i=0;i<jsonArray.length();i++){
                            pages.add(new PAGE(jsonArray.getJSONObject(i)));
                        }
                    }
                    key= TEXTADVENTURE.GROUP.KEYS.attributes;
                    if(jsonObject.has(key)&&!jsonObject.isNull(key)) {
                        JSONArray jsonArray=jsonObject.optJSONArray(key);
                        for(int i=0;i<jsonArray.length();i++){
                            attributes.add(new ATTRIBUTE(jsonArray.getJSONObject(i)));
                        }
                    }
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
                    logger.info(cName+"jsonObject="+jsonObject.toString());
                    return jsonObject;
                }catch (Exception e){
                    logger.error(cName+fName + ".exception=" + e);
                    logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
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
            public SUBGROUP addAttribute(ATTRIBUTE attribute){
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
            public SUBGROUP clearAttributes( ){
                String fName="[clearAttributes]";
                try {
                    if(attributes.isEmpty()){
                        logger.info(fName+"Chapters is empty, cant clear");
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
            public SUBGROUP setAttribute(int index, ATTRIBUTE attribute){
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
        public GROUP addAttribute(ATTRIBUTE attribute){
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
        public GROUP clearAttributes( ){
            String fName="[clearAttributes]";
            try {
                if(attributes.isEmpty()){
                    logger.info(fName+"Chapters is empty, cant clear");
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
        public GROUP setAttribute(int index, ATTRIBUTE attribute){
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
    }
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
    public TEXTADVENTURE addAttribute( ATTRIBUTE attribute){
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
    public TEXTADVENTURE clearAttributes( ){
        String fName="[clearAttributes]";
        try {
            if(attributes.isEmpty()){
                logger.info(fName+"Chapters is empty, cant clear");
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
    public TEXTADVENTURE setAttribute(int index, ATTRIBUTE attribute){
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
                String value=jsonObject.getString(KEYS_Attribute.name);
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
                String value=jsonObject.getString(KEYS_Attribute.display);
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
                String value=jsonObject.getString(KEYS_Attribute.description);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getCommandAsString(){
            String fName="[getCommandAsString]";
            try {
                String value=jsonObject.getString(KEYS_Attribute.command);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public COMMAND_Attribute getCommand(){
            String fName="[getCommand]";
            try {
                String str=jsonObject.getString(KEYS_Attribute.command);
                logger.info(cName+fName+"str="+str);
                COMMAND_Attribute value=COMMAND_Attribute.valueByName(str);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return COMMAND_Attribute.NotApplicable;
            }
        }
        public int getTypeAsInt(){
            String fName="[getTypeAsInt]";
            try {
                int value=jsonObject.getInt(KEYS_Attribute.type);
                logger.info(cName+fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public TYPE_Attribute getType(){
            String fName="[getType]";
            try {
                int i=jsonObject.getInt(KEYS_Attribute.type);
                logger.info(cName+fName+"i="+i);
                TYPE_Attribute value=TYPE_Attribute.valueByCode(i);
                return value;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return TYPE_Attribute.NotApplicable;
            }
        }
        public String getTypeAsString(){
            String fName="[getTypeAsString]";
            try {
                int i=jsonObject.getInt(KEYS_Attribute.type);
                logger.info(cName+fName+"i="+i);
                TYPE_Attribute value=TYPE_Attribute.valueByCode(i);
                return value.getName();
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return TYPE_Attribute.NotApplicable.getName();
            }
        }
    }
}
