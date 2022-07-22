package holidays.naughtypresents;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.lcText2Json;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class entity4NaughtyPresents {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal=null;
    public lcText2Json text2Json=new lcText2Json();
    public JSONObject jsonMain=new JSONObject();
    public entity4NaughtyPresents(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entity4NaughtyPresents(lcGlobalHelper global){
        gGlobal=global;
    }
    public boolean readFile(){
        String fName="[readFile]";
        try {
            if(!text2Json.isFile2Json(iNaughtyPresents.gMainFile)){
                throw  new Exception("Can't readr file!");
            }
            if(text2Json.jsonObject==null){
                throw  new Exception("jsonObject is null!");
            }
            if(text2Json.jsonObject.isEmpty()){
                throw  new Exception("jsonObject is empty!");
            }
            jsonMain=text2Json.jsonObject;
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setCache(){
        String fName="[setCache]";
        try {
            gGlobal.jsonObject.put(iNaughtyPresents.profileName,jsonMain);
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCache(){
        String fName="[getCache]";
        try {
            if(gGlobal.jsonObject.has(iNaughtyPresents.profileName)){
                throw  new Exception("Does not exists in cache json");
            }
            if(gGlobal.jsonObject.isNull(iNaughtyPresents.profileName)){
                throw  new Exception("Cache jsonObject is null");
            }
            JSONObject jsonObject=gGlobal.jsonObject.getJSONObject(iNaughtyPresents.profileName);
            if(jsonObject.isEmpty()){
                throw  new Exception("Cache jsonObject is empty");
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public JSONObject getEventsJson(){
        String fName="[getEventsJson]";
        try {
            JSONObject jsonObject=jsonMain.getJSONObject(iNaughtyPresents.KEYS_Store.events);
            logger.info(fName+".jsonObject.size="+jsonObject.length());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEventsEmpty(){
        String fName="[isEventsEmpty]";
        try {

            JSONObject jsonObject=getEventsJson();
            if(jsonObject==null){
                logger.info(fName+".events is null");
                return true;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+".events is empty");
                return true;
            }
            logger.info(fName+".events isn't empty");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int eventsSize(){
        String fName="[eventsSize]";
        JSONObject jsonObject=getEventsJson();
        if(jsonObject==null){
            logger.info(fName+".events is null");
            return 0;
        }
        int size=jsonObject.length();
        logger.info(fName+".events.size="+size);
        return size;
    }

    public class Event{
        final private String cName="@Event";
        private String key="";
        private  JSONObject jsonParent=new JSONObject();
        public Event(){

        }
        public Event(String key,JSONObject jsonObject){
            _setKey(key,jsonObject);
        }
        private Event _setKey(String key){
            String fName="[_setKey]";
            try {
                logger.info(cName+fName+"key="+key);
                JSONObject jsonObject= _getJsonParent().getJSONObject(key);
                this.key=key;
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private Event _setKey(String key, JSONObject jsonObject){
            String fName="[_setKey]";
            try {
                if(_setJsonParent(jsonObject)==null)throw  new Exception("Failed to set parent!");
                if(_setKey(key)==null)throw  new Exception("Failed to set index!");
                logger.info(cName+fName+"done both");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getKey(){
            String fName="[getKey]";
            logger.info(cName+fName+"key="+key);
            return key;
        }
        private Event _setJsonParent(JSONObject jsonObject){
            String fName="[_setJsonParent]";
            try {
                if(jsonObject==null)throw  new Exception("jsonObject is null!");
                jsonParent=jsonObject;
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private JSONObject _getJsonParent(){
            return jsonParent;
        }
        private JSONObject _getJson(){
            String fName="[_getJson]";
            try {
                return _getJsonParent().getJSONObject(key);
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=_getJson();
                logger.info(cName+fName+"events["+key+"]="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean delete(){
            String fName="[delete]";
            try {
                logger.info(cName+fName+"events["+key+"]="+_getJson().toString());
                _getJsonParent().remove(key);
                key="";
                logger.info(cName+fName+"done");
                return true;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Event set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null)throw  new Exception(cName+fName+"input can't be null");
                logger.info(cName+fName+"input="+jsonObject.toString());
                logger.info(cName+fName+"events["+key+"]_old="+_getJson().toString());
                _getJsonParent().put(key,jsonObject);
                logger.info(cName+fName+"events["+key+"]_new="+_getJson().toString());
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                String key= iNaughtyPresents.KEYS_Store.Present.name;
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"events["+this.key+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EmbedBuilder getEmbed(){
            String fName="[getEmbed]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Event.embed;
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject jsonObject=_getJson().getJSONObject(keyEmbed);
                if(jsonObject==null){
                   throw new Exception("jsonEmbed is null!");
                }
                if(jsonObject.isEmpty()){
                    logger.warn(cName+fName+"events["+this.key+"]["+keyEmbed+"]. is empty!");
                    return embedBuilder;
                }
                logger.info(cName+fName+"events["+this.key+"]["+keyEmbed+"].="+jsonObject.toString());
                embedBuilder=iNaughtyPresents.buildEmbed(jsonObject);
                return embedBuilder;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getEmbedJson(){
            String fName="[getEmbedJson]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Event.embed;
                JSONObject jsonObject=_getJson().getJSONObject(keyEmbed);
                if(jsonObject==null){
                    throw new Exception("jsonEmbed is null!");
                }
                logger.info(cName+fName+"events["+this.key+"]["+keyEmbed+"]. jsonObject="+jsonObject.toString());
                return  jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public JSONArray getPresentsJson4Event(){
            String fName="[getPresentsJson4Event]";
            try {
                return _getJson().getJSONArray(iNaughtyPresents.KEYS_Store.Event.presents);
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isPresentsEmpty4Event(){
            String fName="[isPresentsEmpty4Event]";
            try {
                JSONArray jsonArray=_getJson().getJSONArray(iNaughtyPresents.KEYS_Store.Event.presents);
                if(jsonArray==null){
                    logger.info(fName+"events["+this.key+"].presents is null");
                    return true;
                }
                if(jsonArray.isEmpty()){
                    logger.info(fName+"events["+this.key+"].presents is empty");
                    return true;
                }
                logger.info(fName+"events["+this.key+"].presents isn't empty");
                return false;
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int presentsSize4Event(){
            String fName="[presentsSize4Event]";
            try {
                JSONArray jsonArray=_getJson().getJSONArray(iNaughtyPresents.KEYS_Store.Event.presents);
                if(jsonArray==null){
                    logger.info(fName+"events["+this.key+"].presents is null");
                    return 0;
                }
                int size=jsonArray.length();
                logger.info(fName+"events["+this.key+"].presents.size="+size);
                return size;
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public List<Present>getPresents4Event(){
            String fName="[getPresents4Event]";
            try {
                List<Present>list=new ArrayList<>();
                JSONArray jsonArray=getPresentsJson4Event();
                if(jsonArray==null){
                    logger.info(fName+"events["+this.key+"] is null");
                    return list;
                }
                if(jsonArray.isEmpty()){
                    logger.info(fName+"events["+this.key+"] is empty");
                    return list;
                }
                int size=jsonArray.length();
                logger.info(fName+"events["+this.key+"]["+iNaughtyPresents.KEYS_Store.Event.presents+"].size="+size);
                JSONObject jsonPresents=getPresentsJson();
                for(int i=0;i<size;i++){
                    String key=jsonArray.optString(i,"");
                    if(jsonPresents.has(key)){
                        logger.info(fName+"add="+key);
                        Present present=new Present(key,jsonPresents);
                        list.add(present);
                    }
                }
                logger.info(fName+"list.size="+list.size());
                return list;
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public JSONObject getRestraintsJson(){
            String fName="[getRestraintsJson]";
            try {
                JSONObject jsonObject=_getJson().getJSONObject(iNaughtyPresents.KEYS_Store.Event.restraints);
                logger.info(cName+fName+".jsonObject.isEmpty="+jsonObject.isEmpty());
                logger.info(fName+".jsonObject.="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isRestraintsEmpty(){
            String fName="[isRestraintsEmpty]";
            try {
                JSONObject jsonObject=getRestraintsJson();
                if(jsonObject==null){
                    logger.info(cName+fName+".restraints is null");
                    return true;
                }
                if(jsonObject.isEmpty()){
                    logger.info(cName+fName+".restraints is empty");
                    return true;
                }
                logger.info(cName+fName+".restraints isn't empty");
                return false;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public int restraintsSize(){
            String fName="[restraintsSize]";
            try {

                JSONObject jsonObject=getRestraintsJson();
                if(jsonObject==null){
                    logger.info(cName+fName+".restraints is null");
                    return 0;
                }
                if(jsonObject.isEmpty()){
                    logger.info(cName+fName+".restraints is empty");
                    return 0;
                }
                Set<String> keys=jsonObject.keySet();
                int size=keys.size();
                logger.info(cName+fName+".restraints.size="+size);
                return size;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public class Restraint{
            final private String cName="@Restraint";
            private String key="";
            private Event parent=null;
            private  JSONObject jsonParent=new JSONObject();
            public Restraint(){

            }
            public Restraint(String key,JSONObject jsonObject,Event event){
                String fName="[build]";
                try {
                    _setKey(key,jsonObject,event);
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            private Restraint _setKey(String key){
                String fName="[_setKey]";
                try {
                    logger.info(cName+fName+"key="+key);
                    JSONObject jsonObject= _getJsonParent().getJSONObject(key);
                    this.key=key;
                    logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private Restraint _setKey(String key, JSONObject jsonObject,Event event){
                String fName="[_setKey]";
                try {
                    if(_setParent(event)==null)throw  new Exception("Failed to set parent!");
                    if(_setJsonParent(jsonObject)==null)throw  new Exception("Failed to set parent json!");
                    if(_setKey(key)==null)throw  new Exception("Failed to set index!");
                    logger.info(cName+fName+"done both");
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getKey(){
                return key;
            }
            private Restraint _setParent(Event event){
                String fName="[_setParent]";
                try {
                    if(event==null)throw  new Exception("event is null!");
                    parent=event;
                    logger.info(cName+fName+"done");
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private Restraint _setJsonParent(JSONObject jsonObject){
                String fName="[_setJsonParent]";
                try {
                    if(jsonObject==null)throw  new Exception("jsonObject is null!");
                    jsonParent=jsonObject;
                    logger.info(cName+fName+"done");
                    return this;
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            private Event _getParent(){
                return parent;
            }
            public Event getParent(){
                return _getParent();
            }
            private JSONObject _getJsonParent(){
                return jsonParent;
            }
            private JSONObject _getJson(){
                String fName="[_getJson]";
                try {
                    return _getJsonParent().getJSONObject(key);
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public String getValue(String key){
                String fName="[getValue]";
                try {
                    if(key==null)throw  new Exception("Key is null");
                    if(key.isBlank())throw  new Exception("Key is blank");
                    String value=_getJson().optString(key,"");
                    logger.info(cName+fName+"restraints["+this.key+"]["+key+"].="+value);
                    return value;
                }catch (Exception e){
                    logger.error(cName+fName+".exception=" + e);
                    logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

        }
        public List<Restraint>getRestraints(){
            String fName="[getRestraints]";
            try {

                List<Restraint>list=new ArrayList<>();
                JSONObject jsonObject=getRestraintsJson();
                if(jsonObject==null){
                    logger.info(fName+".restraints is null");
                    return list;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+".restraints is empty");
                    return list;
                }
                Set<String> keys=jsonObject.keySet();
                int size=keys.size();
                logger.info(fName+".restraints.size="+size);
                for(String key: keys){
                    list.add(new Restraint(key,jsonObject,this));
                }
                return list;
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Restraint getRestraint(String key){
            String fName="[getRestraint]";
            try {
                JSONObject jsonObject=getRestraintsJson();
                if(jsonObject==null){
                    throw  new Exception("restraints is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("restraints is empty!");
                }
                if(key==null){
                    throw  new Exception("key is null!");
                }
                if(key.isBlank()){
                    throw  new Exception("key is blank!");
                }
                if(!jsonObject.has(key)){
                    throw  new Exception("no such key exists!");
                }
                if(jsonObject.isNull(key)){
                    throw  new Exception("key entity is null!");
                }
                Restraint present=new Restraint();
                if(present._setKey(key,jsonObject,this)==null){
                    throw  new Exception("failed to set presents[key]!");
                }
                return present;
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Restraint getRestraint(int index){
            String fName="[getRestraint]";
            try {JSONObject jsonObject=getRestraintsJson();
                if(jsonObject==null){
                    throw  new Exception("restraints is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("restraints is empty!");
                }
                Set<String> keys=jsonObject.keySet();
                int size=keys.size();
                logger.info(fName+".restraints.size="+size+", index="+index);
                if(index<0){
                    throw  new Exception("index can't be bellow 0!");
                }
                if(index>=size){
                    throw  new Exception("index can't be bigger or equal to presents.size!");
                }
                int i=0;
                Restraint restraint=new Restraint();
                for(String key: keys){
                    if(i==index){
                        if(restraint._setKey(key,jsonObject,this)==null){
                            throw  new Exception("failed to set presents[key]!");
                        }
                        return restraint;
                    }
                    i++;
                }
                throw  new Exception("unsupported situation");
            }catch (Exception e){
                logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public List<Event>getEvents(){
        String fName="[getEvents]";
        try {

            List<Event>list=new ArrayList<>();
            JSONObject jsonObject=getEventsJson();
            if(jsonObject==null){
                logger.info(fName+".events is null");
                return list;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+".events is empty");
                return list;
            }
            Set<String> keys=jsonObject.keySet();
            int size=keys.size();
            logger.info(fName+".presents.size="+size);
            for(String key: keys){
                list.add(new Event(key,jsonObject));
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Event getEvent(String key){
        String fName="[getEvent]";
        try {

            JSONObject jsonObject=getEventsJson();
            if(jsonObject==null){
                throw  new Exception("events is null!");
            }
            if(jsonObject.isEmpty()){
                throw  new Exception("events is empty!");
            }
            if(key==null){
                throw  new Exception("key is null!");
            }
            if(key.isBlank()){
                throw  new Exception("key is blank!");
            }
            if(!jsonObject.has(key)){
                throw  new Exception("no such key exists!");
            }
            if(jsonObject.isNull(key)){
                throw  new Exception("key entity is null!");
            }
            Event event=new Event();
            if(event._setKey(key,jsonObject)==null){
                throw  new Exception("failed to set presents[key]!");
            }
            return event;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Event getEvent(int index){
        String fName="[getEvent]";
        try {
            JSONObject jsonObject=getEventsJson();
            if(jsonObject==null){
                throw  new Exception("events is null!");
            }
            if(jsonObject.isEmpty()){
                throw  new Exception("events is empty!");
            }
            Set<String> keys=jsonObject.keySet();
            int size=keys.size();
            logger.info(fName+".presents.size="+size+", index="+index);
            if(index<0){
                throw  new Exception("index can't be bellow 0!");
            }
            if(index>=size){
                throw  new Exception("index can't be bigger or equal to presents.size!");
            }
            int i=0;
            Event event=new Event();
            for(String key: keys){
                if(i==index){
                    if(event._setKey(key,jsonObject)==null){
                        throw  new Exception("failed to set presents[key]!");
                    }
                    return event;
                }
                i++;
            }
            throw  new Exception("unsupported situation");
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    public JSONObject getPresentsJson(){
        String fName="[getPresentsJson]";
        try {
            
            JSONObject jsonObject=jsonMain.getJSONObject(iNaughtyPresents.KEYS_Store.presents);
            logger.info(fName+".jsonObject.isEmpty="+jsonObject.isEmpty());
            logger.info(fName+".jsonObject.="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isPresentsEmpty(){
        String fName="[isPresentsEmpty]";
        try {
            
            JSONObject jsonObject=getPresentsJson();
            if(jsonObject==null){
                logger.info(fName+".presents is null");
                return true;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+".presents is empty");
                return true;
            }
            logger.info(fName+".presents isn't empty");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int presentsSize(){
        String fName="[presentsSize]";
        try {
            
            JSONObject jsonObject=getPresentsJson();
            if(jsonObject==null){
                logger.info(fName+".presents is null");
                return 0;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+".presents is empty");
                return 0;
            }
            Set<String> keys=jsonObject.keySet();
            int size=keys.size();
            logger.info(fName+".presents.size="+size);
            return size;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    
    public class Present{
        final private String cName="@Present";
        private String key="";
        private  JSONObject jsonParent=new JSONObject();
        public Present(){

        }
        public Present(String key,JSONObject jsonObject){
            _setKey(key,jsonObject);
        }
        private Present _setKey(String key){
            String fName="[_setKey]";
            try {
                logger.info(cName+fName+"key="+key);
                JSONObject jsonObject= _getJsonParent().getJSONObject(key);
                this.key=key;
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private Present _setKey(String key, JSONObject jsonObject){
            String fName="[_setKey]";
            try {
                if(_setJsonParent(jsonObject)==null)throw  new Exception("Failed to set parent!");
                if(_setKey(key)==null)throw  new Exception("Failed to set index!");
                logger.info(cName+fName+"done both");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getKey(){
            return key;
        }
        private Present _setJsonParent(JSONObject jsonObject){
            String fName="[_setJsonParent]";
            try {
                if(jsonObject==null)throw  new Exception("jsonObject is null!");
                jsonParent=jsonObject;
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private JSONObject _getJsonParent(){
            return jsonParent;
        }
        private JSONObject _getJson(){
            String fName="[_getJson]";
            try {
                return _getJsonParent().getJSONObject(key);
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {
                JSONObject jsonObject=_getJson();
                logger.info(cName+fName+"present["+key+"]="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean delete(){
            String fName="[delete]";
            try {
                logger.info(cName+fName+"present["+key+"]="+_getJson().toString());
                _getJsonParent().remove(key);
                key="";
                logger.info(cName+fName+"done");
                return true;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Present set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null)throw  new Exception(cName+fName+"input can't be null");
                logger.info(cName+fName+"input="+jsonObject.toString());
                logger.info(cName+fName+"present["+key+"]_old="+_getJson().toString());
                _getJsonParent().put(key,jsonObject);
                logger.info(cName+fName+"present["+key+"]_new="+_getJson().toString());
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                String key= iNaughtyPresents.KEYS_Store.Present.name;
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"presents["+this.key+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getHp(){
            String fName="[getHp]";
            try {
                String key= iNaughtyPresents.KEYS_Store.Present.hp;
                int value=_getJson().optInt(key,0);
                logger.info(cName+fName+"presents["+this.key+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public JSONObject getEmbedShowJson(){
            String fName="[getEmbedShowJson]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Present.embed_show;
                JSONObject jsonObject=_getJson().getJSONObject(keyEmbed);
                if(jsonObject==null){
                    throw new Exception("jsonEmbed is null!");
                }
                logger.info(cName+fName+"presents["+this.key+"]["+keyEmbed+"]. jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getEmbedHiddenJson(){
            String fName="[getEmbedHiddenJson]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Present.embed_show;
                JSONObject jsonObject=_getJson().getJSONObject(keyEmbed);
                if(jsonObject==null){
                    throw new Exception("jsonEmbed is null!");
                }
                logger.info(cName+fName+"presents["+this.key+"]["+keyEmbed+"]. jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EmbedBuilder getEmbedHidden(){
            String fName="[getEmbedHidden]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Present.embed_hidden;
                EmbedBuilder embedBuilder=new EmbedBuilder();
                JSONObject jsonObject=_getJson().getJSONObject(keyEmbed);
                if(jsonObject==null){
                    throw new Exception("jsonEmbed is null!");
                }
                if(jsonObject.isEmpty()){
                    logger.warn(cName+fName+"presents["+this.key+"]["+keyEmbed+"]. is empty!");
                    return embedBuilder;
                }
                logger.info(cName+fName+"presents["+this.key+"]["+keyEmbed+"].="+jsonObject.toString());
                embedBuilder=iNaughtyPresents.buildEmbed(jsonObject);
                return embedBuilder;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray getEmbedUseSelfJsons(){
            String fName="[getEmbedUseSelfJsons]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Present.embeds_use_self;
                JSONArray jsonArray=_getJson().getJSONArray(keyEmbed);
                if(jsonArray==null){
                    throw new Exception("jsonEmbed is null!");
                }
                logger.info(cName+fName+"presents["+this.key+"]["+keyEmbed+"]. jsonArray="+jsonArray.toString());
                return jsonArray;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray getEmbedUseOtherJsons(){
            String fName="[getEmbedUseOtherJsons]";
            try {
                String keyEmbed= iNaughtyPresents.KEYS_Store.Present.embeds_use_others;
                JSONArray jsonArray=_getJson().getJSONArray(keyEmbed);
                if(jsonArray==null){
                    throw new Exception("jsonEmbed is null!");
                }
                logger.info(cName+fName+"presents["+this.key+"]["+keyEmbed+"]. jsonArray="+jsonArray.toString());
                return jsonArray;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public List<Present>getPresents(){
        String fName="[getPresents]";
        try {
            
            List<Present>list=new ArrayList<>();
            JSONObject jsonObject=getPresentsJson();
            if(jsonObject==null){
                logger.info(fName+".presents is null");
                return list;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+".presents is empty");
                return list;
            }
            Set<String> keys=jsonObject.keySet();
            int size=keys.size();
            logger.info(fName+".presents.size="+size);
            for(String key: keys){
                list.add(new Present(key,jsonObject));
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Present getPresent(String key){
        String fName="[getPresent]";
        try {
            
            JSONObject jsonObject=getPresentsJson();
            if(jsonObject==null){
                throw  new Exception("presents is null!");
            }
            if(jsonObject.isEmpty()){
                throw  new Exception("presents is empty!");
            }
            if(key==null){
                throw  new Exception("key is null!");
            }
            if(key.isBlank()){
                throw  new Exception("key is blank!");
            }
            if(!jsonObject.has(key)){
                throw  new Exception("no such key exists!");
            }
            if(jsonObject.isNull(key)){
                throw  new Exception("key entity is null!");
            }
            Present present=new Present();
            if(present._setKey(key,jsonObject)==null){
                throw  new Exception("failed to set presents[key]!");
            }
            return present;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Present getPresent(int index){
        String fName="[getPresent]";
        try {
            
            JSONObject jsonObject=getPresentsJson();
            if(jsonObject==null){
                throw  new Exception("presents is null!");
            }
            if(jsonObject.isEmpty()){
                throw  new Exception("presents is empty!");
            }
            Set<String> keys=jsonObject.keySet();
            int size=keys.size();
            logger.info(fName+".presents.size="+size+", index="+index);
            if(index<0){
                throw  new Exception("index can't be bellow 0!");
            }
            if(index>=size){
                throw  new Exception("index can't be bigger or equal to presents.size!");
            }
            int i=0;
            Present present=new Present();
            for(String key: keys){
                if(i==index){
                    if(present._setKey(key,jsonObject)==null){
                        throw  new Exception("failed to set presents[key]!");
                    }
                    return present;
                }
                i++;
            }
            throw  new Exception("unsupported situation");
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }




}
