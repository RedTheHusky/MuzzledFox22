package holidays.naughtypresents;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ls.lsPatreon;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;


public class entityUserProfile4NaughtyPresents {

    Logger logger = Logger.getLogger(getClass());

     public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
     lcGlobalHelper gGlobal;
     Member gMember;
     Guild gGuild;
     public boolean flagGet4Cache=false,flagPut2Cache=false;
    public entityUserProfile4NaughtyPresents(){ }
    public entityUserProfile4NaughtyPresents(lcGlobalHelper global, Member member){ build(global, member); }
    public boolean build(lcGlobalHelper global, Member member){
        String fName="[build]";
        try {
            logger.info(fName+printUser());
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            flagGet4Cache=true;flagPut2Cache=true;
            if(!getProfile()){
                logger.warn(fName+"failed to get profile>new profile");
                if(!newProfile())throw  new Exception("failed to create new profile");
            }else{
                logger.info(fName+"got profile");
            }
            if(setDefaultProfileVariables()){
                logger.info(fName+"setDefaultProfileVariables updated>save");
                if(!saveProfile()){
                    logger.warn(fName+"failed to save>ignore");
                }
            }
            if(flagPut2Cache){
                if(putProfile()){
                    logger.info(fName+"successfully put profile to cache");
                }else{
                    logger.warn(fName+"failed put profile to cache");
                }
            }else{
                logger.info(fName+"disabled to put profile to cache");
            }
            logger.info(fName+"json="+gUserProfile.jsonObject.toString());
            checkPatreon();
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(fName+printUser());
            if(flagGet4Cache)getCacheProfile();
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName+printUser() + ".is locally cached");
                return true;
            }else{
                logger.info(fName+printUser() + ".need to get or create");
                return getSQLProfile();
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=gGlobal.getUserProfile(iNaughtyPresents.profileName,gMember,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName+printUser() + ".is locally cached");
                return true;
            }else{
                logger.info(fName+printUser() + ".not cached");
               return false;
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild,iNaughtyPresents.profileName,iNaughtyPresents.table);
            if(gUserProfile.getProfile()){
                logger.info(fName+printUser() + ".has sql entry");
                return true;
            }else{
                logger.info(fName+printUser() + ".no sql entry");
                return false;
            }
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null)throw  new Exception(printUser()+"global can't be null>false");
            gGlobal=global;
            logger.info(fName+printUser()+"set");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMember(Member member){
        String fName="[setMember]";
        try {
            if(member==null)throw  new Exception(printUser()+"member can't be null>false");
            logger.info(fName+printUser()+"member="+member.getId());
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null)throw  new Exception("guild can't be null>false");
            gGuild=guild;
            logger.info(fName+printUser()+"guild="+guild.getId());
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(lcGlobalHelper global,Member member){
        String fName="[getProfile]";
        try {
            if(!setGlobal(global))throw  new Exception(printUser()+"global can't be null>false");
            if(!setMember(member))throw  new Exception(printUser()+"member can't be null>false");
            if(!setGuild(member.getGuild()))throw  new Exception(printUser()+"guild can't be null>false");
            if(!getProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(lcGlobalHelper global,Member member){
        String fName="[getCacheProfile]";
        try {
            if(!setGlobal(global))throw  new Exception(printUser()+"global can't be null>false");
            if(!setMember(member))throw  new Exception(printUser()+"member can't be null>false");
            if(!setGuild(member.getGuild()))throw  new Exception(printUser()+"guild can't be null>false");
            if(!getCacheProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(lcGlobalHelper global,Member member){
        String fName="[getSQLProfile]";
        try {
            if(!setGlobal(global))throw  new Exception(printUser()+"global can't be null>false");
            if(!setMember(member))throw  new Exception(printUser()+"member can't be null>false");
            if(!setGuild(member.getGuild()))throw  new Exception(printUser()+"guild can't be null>false");
            if(!getSQLProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild,iNaughtyPresents.profileName,iNaughtyPresents.table);
            logger.info(fName+printUser() + ".created");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDefaultProfileVariables(){
        String fName="[setDefaultProfileVariables]";
        try {
            logger.info(fName+printUser());
            gUserProfile.safetyPutFieldEntry(iNaughtyPresents.KEYS_User.presents,new JSONArray());
            gUserProfile.safetyPutFieldEntry(iNaughtyPresents.KEYS_User.events,new JSONArray());
            gUserProfile.safetyPutFieldEntry(iNaughtyPresents.KEYS_User.timestamp_call,0L);
            if(!gUserProfile.isUpdated){
                logger.info(fName+printUser() + ".no update>ignore");return false;
            }
            logger.info(fName+printUser() + ".updated");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putProfile(){
        String fName="[putProfile]";
        try {
            logger.info(fName+printUser());
            if(gGlobal.putUserProfile(gUserProfile,iNaughtyPresents.profileName)){
                logger.info(fName+printUser() + ".success");return true;
            }
            logger.info(fName+printUser() + ".failed");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveProfile(){
        String fName="[saveProfile]";
        try {
            logger.info(fName+printUser() + "json="+gUserProfile.jsonObject.toString());
            if(flagPut2Cache)gGlobal.putUserProfile(gUserProfile,iNaughtyPresents.profileName);
            if(gUserProfile.saveProfile(iNaughtyPresents.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveJSON(){
        String fName="[saveJSON]";
        try {
            if(gUserProfile.saveProfile(iNaughtyPresents.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean save2Cache(){
        String fName="[save2Cache]";
        try {
            gGlobal.putUserProfile(gUserProfile,iNaughtyPresents.profileName);
            if(gUserProfile.saveProfile(iNaughtyPresents.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Member getMember() {
        String fName = "[getMember]";
        try {
            if(gMember==null){
                logger.info(fName+printUser()+"isNull");
                return  null;
            }
            logger.info(fName+printUser()+"member="+gMember.getId());
            return  gMember;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            if(gGuild==null){
                logger.info(fName+printUser()+"isNull");
                return  null;
            }
            logger.info(fName+printUser()+"guild="+gGuild.getId());
            return  gGuild;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private String printUser(){
        String f2Name="[printUser]";
        try {
            //logger.info(f2Name);
            if(gMember==null){
                logger.info(f2Name+"gMember:isNull");
                return "[]";
            }
            String str="[u:"+gMember.getId()+",g:"+gMember.getGuild().getId()+"]";
            str="";
            //logger.info(f2Name+"str="+str);
            return str;
        }catch (Exception e){
            logger.error(f2Name + ".exception=" + e);
            logger.error(f2Name + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isProfile(){
        String fName="[isProfile]";
        try {
            logger.info(fName+printUser());
            return gUserProfile.isProfile();
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean reset(){
        String fName="[reset]";
        try {
            logger.info(fName+printUser());
            gUserProfile.putFieldEntry(iNaughtyPresents.KEYS_User.presents,new JSONArray());
            gUserProfile.putFieldEntry(iNaughtyPresents.KEYS_User.events,new JSONArray());
            gUserProfile.putFieldEntry(iNaughtyPresents.KEYS_User.timestamp_call,0L);
            logger.info(fName+printUser() + ".please update");
            return true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean isPatreon=false;
    public boolean checkPatreon(){
        String fName="[checkPatreon]";
        try {
            logger.info(fName+printUser());
            boolean value=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
            logger.info(fName+printUser() + ".value="+value);
            isPatreon=value;
            return value;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPatreon(){
        String fName="[isPatreon]";
        try {
            logger.info(fName+printUser() + ".value="+isPatreon);
            return isPatreon;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public long getTimestampCall(){
        String fName="[getTimestampCall]";
        try {
            String key= iNaughtyPresents.KEYS_User.timestamp_call;
            long value=gUserProfile.jsonObject.optLong(key,0);
            logger.info(fName+"jsonUser["+key+"].="+value);
            return value;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public entityUserProfile4NaughtyPresents setTimestampCall(long timestamp){
        String fName="[setTimestampCall]";
        try {
            if(timestamp<0)throw  new Exception("input is negative!");
            String key= iNaughtyPresents.KEYS_User.timestamp_call;
            gUserProfile.jsonObject.put(key,timestamp);
            long value=gUserProfile.jsonObject.optLong(key,0);
            logger.info(fName+"jsonUser["+key+"].="+value);
            return this;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public JSONArray getEventsJson(){
        String fName="[getEventsJson]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=gUserProfile.jsonObject.getJSONArray(iNaughtyPresents.KEYS_User.events);
            logger.info(fName+printUser()+".jsonArray.size="+jsonArray.length());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEventsEmpty(){
        String fName="[isEventsEmpty]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".events is null");
                return true;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".events is empty");
                return true;
            }
            logger.info(fName+printUser()+".events isn't empty");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int eventsSize(){
        String fName="[eventsSize]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".events is null");
                return 0;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".events is empty");
                return 0;
            }
            int size=jsonArray.length();
            logger.info(fName+printUser()+".events.size="+size);
            return size;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean remEvent(int index){
        String fName="[remEvent]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                throw  new Exception("events is null");
            }
            if(jsonArray.isEmpty()){
                throw  new Exception("events is empty");
            }
            if(index<0) throw  new Exception("index is bellow zero");
            int size=jsonArray.length();
            logger.info(fName+"events.size="+size+", index="+index);
            if(index>=size) throw  new Exception("index is above or equal to size");
            jsonArray.remove(index);
            logger.info(fName+"events["+index+"].entities["+index+"] removed");
            return  true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public class Event{
        private String cName="@Event";
        private JSONArray jsonParent=new JSONArray();
        private int index=-1;
        public Event(){

        }
        public Event(int index,JSONArray jsonArray){
            _setIndex(index,jsonArray);
        }
        private Event _setIndex(int index){
            String fName="[_setIndex]";
            try {
                logger.info(cName+fName+"index="+index);
                if(index<0)throw  new Exception("Index can't be bellow 0!");
                if(index>= _getJsonParent().length())throw  new Exception("Index can't equal or above array.size!");
                JSONObject jsonObject= _getJsonParent().getJSONObject(index);
                this.index=index;
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private Event _setIndex(int index, JSONArray jsonArray){
            String fName="[_setIndex]";
            try {
                if(_setJsonParent(jsonArray)==null)throw  new Exception("Failed to set parent!");
                if(_setIndex(index)==null)throw  new Exception("Failed to set index!");
                logger.info(cName+fName+"done both");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getIndex(){
            return index;
        }
        private Event _setJsonParent(JSONArray jsonArray){
            String fName="[_setJsonParent]";
            try {
                if(jsonArray==null)throw  new Exception("JsonArray is null!");
                jsonParent=jsonArray;
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private JSONArray _getJsonParent(){
            return jsonParent;
        }
        private JSONObject _getJson(){
            String fName="[_getJson]";
            try {
                return _getJsonParent().getJSONObject(index);
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
                logger.info(cName+fName+"events["+index+"]="+jsonObject.toString());
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
                logger.info(cName+fName+"events["+index+"]="+_getJson().toString());
                _getJsonParent().remove(index);
                index=-1;
                logger.info(cName+fName+"done");
                return true;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Event set(JSONObject jsonObject){
            String fName="[delete]";
            try {
                if(jsonObject==null)throw  new Exception(cName+fName+"input can't be null");
                logger.info(cName+fName+"input="+jsonObject.toString());
                logger.info(cName+fName+"events["+index+"]_old="+_getJson().toString());
                _getJsonParent().put(index,jsonObject);
                logger.info(cName+fName+"events["+index+"]_new="+_getJson().toString());
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getRefKey(){
            String fName="[getRefKey]";
            try {
                String key= iNaughtyPresents.KEYS_User.Event.refKey;
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"events["+index+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getTimestamp(){
            String fName="[getTimestamp]";
            try {
                String key= iNaughtyPresents.KEYS_User.Event.timestamp;
                long value=_getJson().optLong(key,0);
                logger.info(cName+fName+"events["+index+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0L;
            }
        }
        public Event setRefKey(String refKey){
            String fName="[setRefKey]";
            try {
                if(refKey==null)throw  new Exception("input is null!");
                if(refKey.isBlank())throw  new Exception("input is blank!");
                String key= iNaughtyPresents.KEYS_User.Event.refKey;
                _getJson().put(key,refKey);
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"events["+index+"]["+key+"].="+value);
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Event setTimestamp(long timestamp){
            String fName="[setTimestamp]";
            try {
                if(timestamp<0)throw  new Exception("input is negative!");
                String key= iNaughtyPresents.KEYS_User.Event.timestamp;
                _getJson().put(key,timestamp);
                long value=_getJson().optLong(key,0);
                logger.info(cName+fName+"events["+index+"]["+key+"].="+value);
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public List<Event>getEvents(){
        String fName="[getEvents]";
        try {
            logger.info(fName+printUser());
            List<Event>list=new ArrayList<>();
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".events is null");
                return list;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".events is empty");
                return list;
            }
            logger.info(fName+printUser()+".events.size="+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                list.add(new Event(i,jsonArray));
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Event>getEventsByRefKey(String refKey){
        String fName="[etEventsByRefKey]";
        try {
            logger.info(fName+printUser());
            if(refKey==null)throw  new Exception("refKey is null!");
            if(refKey.isBlank())throw  new Exception("irefKey is blank!");
            logger.info(fName+printUser()+".refKey="+refKey);
            List<Event>list=new ArrayList<>();
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".events is null");
                return list;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".events is empty");
                return list;
            }
            logger.info(fName+printUser()+".events.size="+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                Event event=new Event(i,jsonArray);
                if(event.getRefKey().equalsIgnoreCase(refKey)){
                    list.add(event);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Event getEvent(int index){
        String fName="[getEvent]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                throw  new Exception("events is null");
            }
            if(jsonArray.isEmpty()){
                throw  new Exception("events is empty");
            }
            if(index<0) throw  new Exception("index is bellow zero");
            int size=jsonArray.length();
            logger.info(fName+printUser()+".events.size="+size+", index="+index);
            if(index>=size) throw  new Exception("index is above or equal to events.size");
            Event event=new Event();
            if(event._setIndex(index,jsonArray)==null){
                throw  new Exception("invalid events[index]");
            }
            return event;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Event addEvent( entity4NaughtyPresents.Event event){
        String fName="[addEvent]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getEventsJson();
            if(jsonArray==null){
                throw  new Exception("events is null");
            }
            logger.info(fName+".events.size="+jsonArray.length());
            JSONObject jsonObject=new JSONObject();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if(event==null)throw  new Exception("input event is null!");
            logger.info(fName+".timestamp="+timestamp.getTime());
            jsonObject.put(iNaughtyPresents.KEYS_User.Event.refKey,event.getKey());
            jsonObject.put(iNaughtyPresents.KEYS_User.Event.timestamp,timestamp);
            jsonArray.put(jsonObject);
            logger.info(fName+".event added");
            Event event2=new Event();
            event2._setIndex(jsonArray.length()-1,jsonArray);
            return  event2;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public JSONArray getPresentsJson(){
        String fName="[getPresentsJson]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=gUserProfile.jsonObject.getJSONArray(iNaughtyPresents.KEYS_User.presents);
            logger.info(fName+printUser()+".jsonArray.isEmpty="+jsonArray.length());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isPresentsEmpty(){
        String fName="[isPresentsEmpty]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonObject=getPresentsJson();
            if(jsonObject==null){
                logger.info(fName+printUser()+".presents is null");
                return true;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+printUser()+".presents is empty");
                return true;
            }
            logger.info(fName+printUser()+".presents isn't empty");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int presentsSize(){
        String fName="[presentsSize]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonArray=getPresentsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".presents is null");
                return 0;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".presents is empty");
                return 0;
            }
            int size=jsonArray.length();
            logger.info(fName+printUser()+".presents.size="+size);
            return size;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    public entityUserProfile4NaughtyPresents.Present addPresent(entity4NaughtyPresents.Present present){
        String fName="[addPresent]";
        try {
            if(present==null)throw  new Exception(fName+"input present cant be null!");
            JSONArray jsonArray=getPresentsJson();
            if(jsonArray==null){
                throw  new Exception("presents is null!");
            }
            logger.info(fName+printUser()+" present.json="+present.getJson());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+printUser()+".timestamp="+timestamp.getTime());
            JSONObject jsonObject=iNaughtyPresents.newUserGift();
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.refKey,present.getKey());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.hp,present.getHp());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.timestamp_got,timestamp.getTime());
            logger.info(fName+printUser()+".jsonObject="+jsonObject.toString());
            jsonArray.put(jsonObject);
            entityUserProfile4NaughtyPresents.Present present1=new Present(jsonArray.length()-1,jsonArray);
            return  present1;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityUserProfile4NaughtyPresents.Present addPresent(entity4NaughtyPresents.Present present,entity4NaughtyPresents.Event event){
        String fName="[addPresent]";
        try {
            if(present==null)throw  new Exception(fName+"input present cant be null!");
            JSONArray jsonArray=getPresentsJson();
            if(jsonArray==null){
                throw  new Exception("presents is null!");
            }
            logger.info(fName+printUser()+" present.json="+present.getJson());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+printUser()+".timestamp="+timestamp.getTime());
            JSONObject jsonObject=iNaughtyPresents.newUserGift();
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.refKey,present.getKey());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.hp,present.getHp());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.timestamp_got,timestamp.getTime());
            jsonObject.getJSONObject(iNaughtyPresents.KEYS_User.Present.event).put(iNaughtyPresents.KEYS_User.Event.refKey,event.getKey());
            jsonObject.getJSONObject(iNaughtyPresents.KEYS_User.Present.event).put(iNaughtyPresents.KEYS_User.Event.timestamp,timestamp.getTime());
            logger.info(fName+printUser()+".jsonObject="+jsonObject.toString());
            jsonArray.put(jsonObject);
            entityUserProfile4NaughtyPresents.Present present1=new Present(jsonArray.length()-1,jsonArray);
            return  present1;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityUserProfile4NaughtyPresents.Present addPresentAsGift(entityUserProfile4NaughtyPresents.Present present,User user){
        String fName="[addPresentAsGift]";
        try {
            if(present==null)throw  new Exception(fName+"input present cant be null!");
            JSONArray jsonArray=getPresentsJson();
            if(jsonArray==null){
                throw  new Exception("presents is null!");
            }
            logger.info(fName+printUser()+" present.json="+present.getJson());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+printUser()+".timestamp="+timestamp.getTime());
            JSONObject jsonObject=iNaughtyPresents.newUserGift();
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.refKey,present.getRefKey());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.hp,present.getHp());
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.timestamp_got,timestamp.getTime());
            jsonObject.getJSONObject(iNaughtyPresents.KEYS_User.Present.gift).put(iNaughtyPresents.KEYS_User.Present.Gift.mode,2);
            jsonObject.getJSONObject(iNaughtyPresents.KEYS_User.Present.gift).put(iNaughtyPresents.KEYS_User.Present.Gift.timestamp,timestamp.getTime());
            jsonObject.getJSONObject(iNaughtyPresents.KEYS_User.Present.gift).put(iNaughtyPresents.KEYS_User.Present.Gift.target,user.getId());
            logger.info(fName+printUser()+".jsonObject="+jsonObject.toString());
            jsonArray.put(jsonObject);
            entityUserProfile4NaughtyPresents.Present present1=new Present(jsonArray.length()-1,jsonArray);
            return  present1;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public class Present{
        private String cName="@Present";
        private int index=-1;
        private  JSONArray jsonParent=new JSONArray();
        public Present(){

        }
        public Present(int index,JSONArray jsonArray){
            _setIndex(index,jsonArray);
        }
        private Present _setIndex(int index){
            String fName="[_setIndex]";
            try {
                logger.info(cName+fName+"index="+index);
                if(index<0)throw  new Exception(cName+fName+" index cant be bellow 0");
                if(index>=_getJsonParent().length())throw  new Exception(cName+fName+" index cant be above or eual to array");
                this.index=index;
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private Present _setIndex(int index, JSONArray jsonArray){
            String fName="[_setIndex]";
            try {
                if(_setJsonParent(jsonArray)==null)throw  new Exception("Failed to set parent!");
                if(_setIndex(index)==null)throw  new Exception("Failed to set index!");
                logger.info(cName+fName+"done both");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getIndex(){
            return index;
        }
        private Present _setJsonParent(JSONArray jsonArray){
            String fName="[_setJsonParent]";
            try {
                if(jsonArray==null)throw  new Exception("jsonArray is null!");
                jsonParent=jsonArray;
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray _getJsonParent(){
            return jsonParent;
        }
        public JSONObject _getJson(){
            String fName="[_getJson]";
            try {
                return _getJsonParent().getJSONObject(index);
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
                logger.info(cName+fName+"presents["+index+"]="+jsonObject.toString());
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
                logger.info(cName+fName+"present["+index+"]="+_getJson().toString());
                _getJsonParent().remove(index);
                index=-1;
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
                logger.info(cName+fName+"present["+index+"]_old="+_getJson().toString());
                _getJsonParent().put(index,jsonObject);
                logger.info(cName+fName+"present["+index+"]_new="+_getJson().toString());
                logger.info(cName+fName+"done");
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getRefKey(){
            String fName="[getRefKey]";
            try {
                String key= iNaughtyPresents.KEYS_User.Present.refKey;
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"presents["+this.index+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Present setRefKey(String refKey){
            String fName="[setRefKey]";
            try {
                if(refKey==null)throw  new Exception("input is null!");
                if(refKey.isBlank())throw  new Exception("input is blank!");
                String key= iNaughtyPresents.KEYS_User.Present.refKey;
                _getJson().put(key,refKey);
                String value=_getJson().optString(key,"");
                logger.info(cName+fName+"presents["+this.index+"]["+key+"].="+value);
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Present tickUse(){
            String fName="[tickUse]";
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(cName+fName+".timestamp="+timestamp.getTime());
                _getJson().put(iNaughtyPresents.KEYS_User.Present.hp,getHp()-1);
                _getJson().put(iNaughtyPresents.KEYS_User.Present.timestamp_used,timestamp);
                logger.info(cName+fName+"done presents["+index+"]="+_getJson().toString());
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isUsedUp(){
            String fName="[isUsedUp]";
            try {
                int value=_getJson().optInt(iNaughtyPresents.KEYS_User.Present.hp,0);
                boolean result=value<=0;
                logger.info(cName+fName+"presents["+index+"]="+value+", result="+result);
                return result;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject _getGiftJson(){
            String fName="[_getGiftJson]";
            try {
                if(!_getJson().has(iNaughtyPresents.KEYS_User.Present.gift)){
                    logger.info(cName+fName+"presents["+index+"] has no gift key>false");
                    return null;
                }
                if(_getJson().isNull(iNaughtyPresents.KEYS_User.Present.gift)){
                    logger.info(cName+fName+"presents["+index+"] gift is null>false");
                    return null;
                }
                JSONObject jsonObject=_getJson().getJSONObject(iNaughtyPresents.KEYS_User.Present.gift);
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject _getEventJson(){
            String fName="[_getEventJson]";
            try {
                logger.info(cName+fName+"presents["+index+"] _getJson()="+_getJson().toString());
                if(!_getJson().has(iNaughtyPresents.KEYS_User.Present.event)){
                    logger.info(cName+fName+"presents["+index+"] has no event key>false");
                    return null;
                }
                if(_getJson().isNull(iNaughtyPresents.KEYS_User.Present.event)){
                    logger.info(cName+fName+"presents["+index+"] event is null>false");
                    return null;
                }
                JSONObject jsonObject=_getJson().getJSONObject(iNaughtyPresents.KEYS_User.Present.event);
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isGifted(){
            String fName="[isGifted]";
            try {
                JSONObject jsonObject=_getGiftJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                int value=jsonObject.optInt(iNaughtyPresents.KEYS_User.Present.Gift.mode,0);
                boolean response=value==1;
                logger.info(cName+fName+"presents["+index+"]="+value+", response="+response);
                return response;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Present setGifted(User user){
            String fName="[setGifted]";
            try {
                JSONObject jsonObject=_getGiftJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                int value=jsonObject.optInt(iNaughtyPresents.KEYS_User.Present.Gift.mode,0);
                if(value==1){
                    logger.warn(cName+fName+"presents["+index+"] already gifted");
                    return  null;
                }
                if(value==2){
                    logger.warn(cName+fName+"presents["+index+"] is a gift");
                    return  null;
                }
                jsonObject.put(iNaughtyPresents.KEYS_User.Present.Gift.mode,1);
                jsonObject.put(iNaughtyPresents.KEYS_User.Present.Gift.target,user.getId());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                jsonObject.put(iNaughtyPresents.KEYS_User.Present.Gift.timestamp,timestamp.getTime());
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isGift(){
            String fName="[isGift]";
            try {
                JSONObject jsonObject=_getGiftJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                int value=jsonObject.optInt(iNaughtyPresents.KEYS_User.Present.Gift.mode,0);
                boolean response=value==2;
                logger.info(cName+fName+"presents["+index+"]="+value+", response="+response);
                return response;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getTarget(){
            String fName="[getTarget]";
            try {
                JSONObject jsonObject=_getGiftJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                String value=jsonObject.optString(iNaughtyPresents.KEYS_User.Present.Gift.target,"");
                logger.info(cName+fName+"presents["+index+"]="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getTimestampGot(){
            String fName="[getTimestampGot]";
            try {
                long value=_getJson().optLong(iNaughtyPresents.KEYS_User.Present.timestamp_got,0);
                logger.info(cName+fName+"presents["+index+"]="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0L;
            }
        }
        public long getTimestampUsed(){
            String fName="[getTimestampUsed]";
            try {
                long value=_getJson().optLong(iNaughtyPresents.KEYS_User.Present.timestamp_used,0);
                logger.info(cName+fName+"presents["+index+"]="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0L;
            }
        }
        public long getTimestampGift(){
            String fName="[getTimestampGift]";
            try {
                JSONObject jsonObject=_getGiftJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                long value=jsonObject.optLong(iNaughtyPresents.KEYS_User.Present.Gift.timestamp,0);
                logger.info(cName+fName+"presents["+index+"]="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return 0L;
            }
        }
        public int getHp(){
            String fName="[getHp]";
            try {
                String key= iNaughtyPresents.KEYS_Store.Present.hp;
                int value=_getJson().optInt(key,0);
                logger.info(cName+fName+"presents["+index+"]["+key+"].="+value);
                return value;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return -1;
            }
        }
        public Present decHp(){
            String fName="[decHp]";
            try {
                String key= iNaughtyPresents.KEYS_Store.Present.hp;
                int value=_getJson().optInt(key,0);
                logger.info(cName+fName+"presents["+index+"]["+key+"].old="+value);
                if(value<0)value=0;
                _getJson().put(key,value-1);
                logger.info(cName+fName+"presents["+index+"]["+key+"].new="+value);
                return this;
            }catch (Exception e){
                logger.error(cName+fName+".exception=" + e);
                logger.error(cName+fName+".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getEventKey(){
            String fName="[getEventKey]";
            try {
                JSONObject jsonObject=_getEventJson();
                logger.info(cName+fName+"presents["+index+"]jsonObject="+jsonObject.toString());
                String value=jsonObject.optString(iNaughtyPresents.KEYS_User.Event.refKey,"");
                logger.info(cName+fName+"presents["+index+"]="+value);
                return value;
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
            logger.info(fName+printUser());
            List<Present>list=new ArrayList<>();
            JSONArray jsonObject=getPresentsJson();
            if(jsonObject==null){
                logger.info(fName+printUser()+".presents is null");
                return list;
            }
            if(jsonObject.isEmpty()){
                logger.info(fName+printUser()+".presents is empty");
                return list;
            }
            int size=jsonObject.length();
            logger.info(fName+printUser()+".presents.size="+size);
            for(int i=0;i<size;i++){
                list.add(new Present(i,jsonObject));
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Present>getPresentsByRefKey(String refKey){
        String fName="[getPresentsByRefKey]";
        try {
            logger.info(fName+printUser());
            if(refKey==null)throw  new Exception("refKey is null!");
            if(refKey.isBlank())throw  new Exception("irefKey is blank!");
            logger.info(fName+printUser()+".refKey="+refKey);
            List<Present>list=new ArrayList<>();
            JSONArray jsonArray=getPresentsJson();
            if(jsonArray==null){
                logger.info(fName+printUser()+".presents is null");
                return list;
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+printUser()+".presents is empty");
                return list;
            }
            int size=jsonArray.length();
            logger.info(fName+printUser()+".presents.size="+size);
            for(int i=0;i<size;i++){
                Present present=new Present(i,jsonArray);
                if(present.getRefKey().equalsIgnoreCase(refKey)){
                    list.add(present);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Present getPresent(int index){
        String fName="[getPresent]";
        try {
            logger.info(fName+printUser());
            JSONArray jsonObject=getPresentsJson();
            if(jsonObject==null){
                throw  new Exception("presents is null!");
            }
            if(jsonObject.isEmpty()){
                throw  new Exception("presents is empty!");
            }
            int size=jsonObject.length();
            logger.info(fName+printUser()+".presents.size="+size+", index="+index);
            if(index<0){
                throw  new Exception("index can't be bellow 0!");
            }
            if(index>=size){
                throw  new Exception("index can't be bigger or equal to presents.size!");
            }
            Present present=new Present();
            if(present._setIndex(index,jsonObject)==null)throw  new Exception(fName+" faild to set");
            return  present;
        }catch (Exception e){
            logger.error(fName+".exception=" + e+", StackTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
