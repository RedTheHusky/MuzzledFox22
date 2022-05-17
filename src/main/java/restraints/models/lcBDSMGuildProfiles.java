package restraints.models;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ls.lsChannelHelper;
import models.ls.lsCustomGuilds;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.ACCESSLEVELS;

import java.util.*;

import static models.llGlobalHelper.llv2_GuildsSettings;

public class lcBDSMGuildProfiles implements iRestraints {
    Logger logger = Logger.getLogger(getClass());
    public lcBDSMGuildProfiles(){
        String fName="[constructor]";
        logger.info(fName+".init");
    }
    lcGlobalHelper gGlobal;
    Guild gGuild;
    public lcBDSMGuildProfiles(lcGlobalHelper global, Guild guild){
        String fName="[constructor]";
        logger.info(fName+".init");
        gGlobal=global;gGuild=guild;
        diaper=new DIAPER();
        timeout=new TIMEOUT();
        restraints=new RESTRAINTS();
        restrictions=new RESTRICTIONS();
        muzzle=new MUZZLE();
        collar=new COLLAR();
    }


    public RESTRAINTS restraints;
    static public String gRestrainsProfileName ="rdRestrains";
    public class RESTRAINTS {
        public lcJSONGuildProfile gProfile;
        public lcJSONGuildProfile getProfile(){
            String fName="[getProfile]";
            logger.info(fName+".safety check");
            try{
                if(gProfile ==null){
                    init();
                }
                return gProfile;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public void init(){
            String fName="[initRestrainsProfile]";
            logger.info(fName+".safety check");
            try{
                gProfile =gGlobal.getGuildSettings(gGuild, gRestrainsProfileName);
                if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gRestrainsProfileName,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(!gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if(gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if(isAutoSave){
                        if(gProfile.saveProfile()){
                            logger.info(fName + ".success save to db");
                        }else{
                            logger.error(fName + ".error save to db");
                        }
                    }
                }

            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public void iSafety(){
            String fName="iSafety";
            try {
                String field= fieldMode;
                gProfile.safetyPutFieldEntry(field, valueWhite);
                field= fieldEnabled;
                gProfile.safetyPutFieldEntry(field,true);
                field= fieldAlert;
                gProfile.safetyPutFieldEntry(field, valueChannel);
                field= fieldAllowedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldAllowedUsers;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedUsers;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldAllowedRoles;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedRoles;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBDSMRestrictions;
                gProfile.safetyPutFieldEntry(field,0);
                field= fieldCommandAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());
                field= fieldTargetAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());
                field= fieldDefaultAccessMode;
                gProfile.safetyPutFieldEntry(field, ACCESSLEVELS.Ask.getName());
                field= fieldServerNotificationDisabled;
                gProfile.safetyPutFieldEntry(field,false);
                field= fieldServerNotificationRedirect;
                gProfile.safetyPutFieldEntry(field,0L);

                field= fieldDomRoles;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                /*field= fieldDomBypassAsk;
                gProfile.safetyPutFieldEntry(field, true);
                field= fieldDomBypassProtected;
                gProfile.safetyPutFieldEntry(field, false);
                field= fieldSubRoles;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldSubRestrieted4Restraints;
                gProfile.safetyPutFieldEntry(field, true);
                field= fieldSubRestrieted4Extra;
                gProfile.safetyPutFieldEntry(field, true);*/

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public boolean isAutoSave =true;
        public boolean save(){
            String fName="[saveMuzzleProfile]";
            logger.info(fName);
            try{
                gGlobal.putGuildSettings(gGuild, gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public RESTRAINTS(){

        }

        public boolean setEnable(boolean enabled){
            String fName="[setEnable]";
            try {
                gProfile.putFieldEntry(fieldEnabled, enabled);
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean getEnable(){
            String fName="[getEnable]";
            try {
                logger.info(fName+"gProfile="+gProfile.jsonObject.toString());
                return gProfile.jsonObject.getBoolean(fieldEnabled);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean isBasicArrayInvalid(String name){
            String fName="[hasBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                if(!gProfile.jsonObject.has(name)){
                    logger.info(fName + ".does not have>true");
                    return  true;
                }
                if(gProfile.jsonObject.isNull(name)){
                    logger.info(fName + ".does not have>true");
                    return  true;
                }
                if(gProfile.jsonObject.getJSONArray(name).isEmpty()){
                    logger.info(fName + ".array is empty>true");
                    return  true;
                }
                logger.info(fName + ".has>false");
                return  false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean clearBasicArray(String name){
            String fName="[setBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                gProfile.putFieldEntry(name, new JSONArray());
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public List getBasicArrayAsList(String name){
            String fName="[getBasicArrayAsLong]";
            try {
                logger.info(fName + ".name=" + name);
                JSONArray jsonArray=gProfile.getFieldEntryAsJSONArray(name);
                return  jsonArray.toList();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<String> getBasicArrayAsListString(String name){
            String fName="[getBasicArrayAsListString]";
            try {
                logger.info(fName + ".name=" + name);
                List<String>list=new ArrayList<>();
                JSONArray jsonArray=gProfile.getFieldEntryAsJSONArray(name);
               for(int i=0;i<jsonArray.length();i++){
                   try {
                       list.add(jsonArray.getString(i));
                   }catch (Exception e){
                       logger.error(fName + ".exception=" + e);
                       logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                   }
               }
               return  list;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<Long> getBasicArrayAsListLong(String name){
            String fName="[getBasicArrayAsListLong]";
            try {
                logger.info(fName + ".name=" + name);
                List<Long>list=new ArrayList<>();
                JSONArray jsonArray=gProfile.getFieldEntryAsJSONArray(name);
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        list.add(jsonArray.getLong(i));
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                return  list;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public boolean setBasicArray(String name,JSONArray jsonArray){
            String fName="[setBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".jsonArray=" + jsonArray.toString());
                gProfile.putFieldEntry(name, jsonArray);
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasBasicArray(String name,long id){
            String fName="[hasBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".id=" + id);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getLong(i)==id){
                        logger.info(fName + ".has");
                        return true;
                    }
                }
                logger.info(fName + ".does not");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasBasicArray(String name,String str){
            String fName="[hasBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".str=" + str);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getString(i).equals(str)){
                        logger.info(fName + ".has");
                        return true;
                    }
                }
                logger.info(fName + ".does not");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addBasicArray(String name,long id){
            String fName="[addBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".id=" + id);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getLong(i)==id){
                        logger.info(fName + ".already added");
                        return false;
                    }
                }
                jsonArray.put(id);
                gProfile.putFieldEntry(name,jsonArray);
                logger.info(fName + ".added");
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addBasicArray(String name,String str){
            String fName="[addBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".str=" + str);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getString(i).equals(str)){
                        logger.info(fName + ".already added");
                        return false;
                    }
                }
                jsonArray.put(str);
                gProfile.putFieldEntry(name,jsonArray);
                logger.info(fName + ".added");
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remBasicArray(String name,long id){
            String fName="[remBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".id=" + id);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getLong(i)==id){
                        logger.info(fName + ".exist");
                        jsonArray.remove(i);
                        gProfile.putFieldEntry(name,jsonArray);
                        logger.info(fName + ".removed");
                        if(isAutoSave){
                            return save();
                        }else{
                            return true;
                        }
                    }
                }
                logger.info(fName + ".does not exists");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remBasicArray(String name,String str){
            String fName="[remBasicArray]";
            try {
                logger.info(fName + ".name=" + name);
                logger.info(fName + ".str=" + str);
                JSONArray jsonArray=new JSONArray();
                if(gProfile.jsonObject.has(name)&&!gProfile.jsonObject.isNull(name)){
                    jsonArray=gProfile.jsonObject.getJSONArray(name);
                }
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getString(i).equals(str)){
                        logger.info(fName + ".exist");
                        jsonArray.remove(i);
                        gProfile.putFieldEntry(name,jsonArray);
                        logger.info(fName + ".removed");
                        if(isAutoSave){
                            return save();
                        }else{
                            return true;
                        }
                    }
                }
                logger.info(fName + ".does not exists");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean clearAllowedChannels(){
            String fName="[clearAllowedChannels]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldAllowedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearDeniedChannels(){
            String fName="[clearDeniedChannels]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldBannedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearAllowedRoles(){
            String fName="[clearAllowedRoles]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldAllowedRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearDeniedRoles(){
            String fName="[clearDeniedRoles]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldBannedRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearAllowedUsers(){
            String fName="[clearAllowedUsers]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldAllowedUsers);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearDeniedUsers(){
            String fName="[clearDeniedUsers]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldBannedUsers);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearDomRoles(){
            String fName="[clearDomRoles]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldDomRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearSubRoles(){
            String fName="[clearSubRoles]";
            try {
                logger.info(fName + "do");
                return  clearBasicArray(fieldSubRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public List<Long> getAllowedChannelsAsLong(){
            String fName="[getAllowedChannelsAsLong]";
            try {
                logger.info(fName + "do");
                return getBasicArrayAsListLong(fieldAllowedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<Long> getDeniedChannelsAsLong(){
            String fName="[getDeniedChannelsAsLong]";
            try {
                logger.info(fName + "do");
                return getBasicArrayAsListLong(fieldBannedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<Long> getAllowedRolesAsLong(){
            String fName="[getAllowedRolesAsLong]";
            try {
                logger.info(fName + "do");
                return getBasicArrayAsListLong(fieldAllowedRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
        public List<Long> getDeniedRolesAsLong(){
            String fName="[getDeniedRolesAsLong]";
            try {
                logger.info(fName + "do");
                return getBasicArrayAsListLong(fieldBannedRoles);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }

        public boolean addAllowedChannel(TextChannel textChannel){
            String fName="[addAllowedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldAllowedChannels;
                if(hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,textChannel.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addDeniedChannel(TextChannel textChannel){
            String fName="[addDeniedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldBannedChannels;
                if(hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,textChannel.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addAllowedRole(Role role){
            String fName="[addAllowedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key=fieldAllowedRoles;
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addDeniedRole(Role role){
            String fName="[addDeniedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldBannedRoles;
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addAllowedUser(Member member){
            String fName="[addAllowedUser]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldAllowedUsers;
                if(hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,member.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addDeniedUse(Member member){
            String fName="[addDeniedUse]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldBannedUsers;
                if(hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,member.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addDomRole(Role role){
            String fName="[addDomRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldDomRoles;
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean addSubRole(Role role){
            String fName="[addSubRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldSubRoles;
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".already has");
                    return true;
                }
                return  addBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean remAllowedChannel(TextChannel textChannel){
            String fName="[remAllowedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldAllowedChannels;
                if(!hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,textChannel.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remDeniedChannel(TextChannel textChannel){
            String fName="[remDeniedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldBannedChannels;
                if(!hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,textChannel.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remAllowedRole(Role role){
            String fName="[remAllowedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key=fieldAllowedRoles;
                if(!hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remDeniedRole(Role role){
            String fName="[remDeniedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldBannedRoles;
                if(!hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remAllowedUser(Member member){
            String fName="[remAllowedUser]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldAllowedUsers;
                if(!hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,member.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remDeniedUse(Member member){
            String fName="[remDeniedUse]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldBannedUsers;
                if(!hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,member.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean reDomRole(Role role){
            String fName="[remDomRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldDomRoles;
                if(!hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean remSubRole(Role role){
            String fName="[remSubRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldSubRoles;
                if(!hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".does not has");
                    return true;
                }
                return  remBasicArray(key,role.getIdLong());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean isAllowedChannel(TextChannel textChannel){
            String fName="[isAllowedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldAllowedChannels;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>true");
                    return true;
                }
                if(hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isDeniedChannel(TextChannel textChannel){
            String fName="[isDeniedChannel]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                String key=fieldBannedChannels;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>false");
                    return false;
                }
                if(hasBasicArray(key,textChannel.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isAllowedRole(Role role){
            String fName="[isAllowedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key=fieldAllowedRoles;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>true");
                    return true;
                }
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isDeniedRole(Role role){
            String fName="[isDeniedRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key= fieldBannedRoles;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>false");
                    return false;
                }
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isAllowedUser(Member member){
            String fName="[isAllowedUser]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldAllowedUsers;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>true");
                    return true;
                }
                if(hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isDeniedUse(Member member){
            String fName="[isDeniedUse]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                String key=fieldBannedUsers;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>false");
                    return false;
                }
                if(hasBasicArray(key,member.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isDomRole(Role role){
            String fName="[isDomRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key=fieldDomRoles;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>true");
                    return true;
                }
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isSubRole(Role role){
            String fName="[isSubRole]";
            try {
                logger.info(fName + ".role.id=" + role.getIdLong());
                String key=fieldSubRoles;
                if(isBasicArrayInvalid(key)){
                    logger.info(fName + ".invalid the array>true");
                    return true;
                }
                if(hasBasicArray(key,role.getIdLong())){
                    logger.info(fName + ".found in array>true");
                    return true;
                }
                logger.info(fName + ".not found in array>false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean isAllowed(Member member,TextChannel textChannel){
            String fName="[isAllowed]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                boolean isChannelAllowed=false,isChannelDenied=false,isMemberAllowed=false,isMemberDenied=false,isRoleAllowed=false,isRoleDenied=false;
                int positionAllowedRole=-1,positionDeniedRole=-1;
                isChannelAllowed=isAllowedChannel(textChannel);
                isChannelDenied=isDeniedChannel(textChannel);
                isMemberAllowed=isAllowedUser(member);
                isMemberDenied=isDeniedUse(member);
                try {
                    List<Role> roles=member.getRoles();
                    for (Role value : roles) {
                        try {
                            if (!isRoleAllowed && isAllowedRole(value)) {
                                isRoleAllowed = true;
                                positionAllowedRole = value.getPosition();
                            }
                            if (!isRoleDenied && isDeniedRole(value)) {
                                isRoleDenied = true;
                                positionDeniedRole = value.getPosition();
                            }
                        } catch (Exception e) {
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName + ".isChannelAllowed=" + isChannelAllowed+", isChannelDenied="+isChannelDenied+", isMemberAllowed="+isMemberAllowed+", isMemberDenied="+isMemberDenied);
                logger.info(fName+".isRoleAllowed="+isRoleAllowed+"|positionAllowedRole="+positionAllowedRole+", isRoleDenied="+isRoleDenied+"|positionDeniedRole="+positionDeniedRole);
                if(isChannelDenied||!isChannelAllowed){
                    logger.info(fName + ".channel not allowed");
                    return  false;
                }
                if(isMemberDenied||!isMemberAllowed){
                    logger.info(fName + ".member not allowed");
                    return  false;
                }
                if(isRoleDenied&&!isRoleAllowed){
                    logger.info(fName + ".role is denied and no allowed role");
                    return  false;
                }
                else if(isRoleDenied&&positionDeniedRole>positionAllowedRole){
                    logger.info(fName + ".positionDeniedRole is bigger than positionAllowedRole");
                    return  false;
                }
                logger.info(fName + ".defualt true");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isChannelAllowed(TextChannel textChannel){
            String fName="[isChannelAllowed]";
            try {
                logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
                boolean isChannelAllowed=false,isChannelDenied=false;
                int positionAllowedRole=-1,positionDeniedRole=-1;
                isChannelAllowed=isAllowedChannel(textChannel);
                isChannelDenied=isDeniedChannel(textChannel);
                logger.info(fName + ".isChannelAllowed=" + isChannelAllowed+", isChannelDenied="+isChannelDenied);
                if(isChannelDenied||!isChannelAllowed){
                    logger.info(fName + ".channel not allowed");
                    return  false;
                }
                logger.info(fName + ".defualt true");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isRoleAllowed(Member member){
            String fName="[isRoleAllowed]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                boolean isRoleAllowed=false,isRoleDenied=false;
                int positionAllowedRole=-1,positionDeniedRole=-1;
                try {
                    List<Role> roles=member.getRoles();
                    for (Role value : roles) {
                        try {
                            if (!isRoleAllowed && isAllowedRole(value)) {
                                isRoleAllowed = true;
                                positionAllowedRole = value.getPosition();
                            }
                            if (!isRoleDenied && isDeniedRole(value)) {
                                isRoleDenied = true;
                                positionDeniedRole = value.getPosition();
                            }
                        } catch (Exception e) {
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+".isRoleAllowed="+isRoleAllowed+"|positionAllowedRole="+positionAllowedRole+", isRoleDenied="+isRoleDenied+"|positionDeniedRole="+positionDeniedRole);
                if(isRoleDenied&&!isRoleAllowed){
                    logger.info(fName + ".role is denied and no allowed role");
                    return  false;
                }
                else if(isRoleDenied&&positionDeniedRole>positionAllowedRole){
                    logger.info(fName + ".positionDeniedRole is bigger than positionAllowedRole");
                    return  false;
                }
                logger.info(fName + ".defualt true");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isRoleDom(Member member){
            String fName="[isRoleDom]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                boolean isRoleDom=false;
                int positionDomRole=-1;
                try {
                    List<Role> roles=member.getRoles();
                    for (Role value : roles) {
                        try {
                            if (!isRoleDom && isDomRole(value)) {
                                isRoleDom = true;
                                positionDomRole = value.getPosition();
                            }

                        } catch (Exception e) {
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+".isRoleDom="+isRoleDom+"|positionDomRole="+positionDomRole);
                if(!isRoleDom){
                    logger.info(fName + ".role is dom");
                    return  true;
                }
                logger.info(fName + ".defualt false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isRoleSub(Member member){
            String fName="[isRoleSub]";
            try {
                logger.info(fName + ".member.id=" + member.getIdLong());
                boolean isRoleDom=false;
                int positionDomRole=-1;
                try {
                    List<Role> roles=member.getRoles();
                    for (Role value : roles) {
                        try {
                            if (!isRoleDom && isSubRole(value)) {
                                isRoleDom = true;
                                positionDomRole = value.getPosition();
                            }

                        } catch (Exception e) {
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+".isRoleDom="+isRoleDom+"|positionDomRole="+positionDomRole);
                if(!isRoleDom){
                    logger.info(fName + ".role is dom");
                    return  true;
                }
                logger.info(fName + ".defualt false");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }

        public boolean isNotificationDisabled(){
            String fName="[isNotificationDisabled]";
            try {
                boolean result=gProfile.jsonObject.getBoolean(fieldServerNotificationDisabled);
                logger.info(fName + "result=" +result);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public long getRedirectNotificationChanelAsLong(){
            String fName="[getRedirectNotificationChanelAsLong]";
            try {
                if(!gProfile.jsonObject.has(fieldServerNotificationRedirect)){
                    logger.info(fName + "has no such key > original");
                    return  0L;
                }
                if(gProfile.jsonObject.isNull(fieldServerNotificationRedirect)){
                    logger.info(fName + "key is null > original");
                    return  0L;
                }
                long redirectID=gProfile.jsonObject.getLong(fieldServerNotificationRedirect);
                logger.info(fName + "redirectID=" +redirectID);
                return  redirectID;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0L;
            }
        }
        public TextChannel getRedirectNotificationChanel(Guild guild){
            String fName="[getRedirectNotificationChanel]";
            try {
                long redirectID=getRedirectNotificationChanelAsLong();
                if(redirectID==0L){
                    logger.info(fName + "redirectID is zero");
                    return  null;
                }
                logger.info(fName + "redirectID=" +redirectID);
                logger.info(fName + "guild=" +guild.getId());
                TextChannel redirect=lsChannelHelper.lsGetTextChannelById(guild,redirectID);
                if(redirect==null){
                    logger.info(fName + "redirect is null");
                    return  null;
                }
                logger.info(fName + "redirect=" +redirect.getName()+"("+redirect.getId()+")");
                return  redirect;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public TextChannel doRedirectNotificationChanel(TextChannel original){
            String fName="[doRedirectNotificationChanel]";
            try {
                TextChannel redirect=getRedirectNotificationChanel(original.getGuild());
                if(redirect==null){
                    logger.info(fName + "backup=" +original.getName()+"("+original.getId()+")");
                    return  original;
                }
                logger.info(fName + "redirect=" +redirect.getName()+"("+redirect.getId()+")");
                return  redirect;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return original;
            }
        }
        public boolean setNotificationChannel(TextChannel textChannel){
            String fName="[setNotificationChannel]";
            try {
                logger.info(fName + ".textChannel=" + textChannel.getId());
                gProfile.jsonObject.put(fieldServerNotificationRedirect,textChannel.getIdLong());
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean clearNotificationChannel(){
            String fName="[clearNotificationChannel]";
            try {
                logger.info(fName + ".");
                gProfile.jsonObject.put(fieldServerNotificationRedirect,0L);
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean setDisableNotification(boolean enabled){
            String fName="[setDisableNotification]";
            try {
                logger.info(fName + ".enabled=" + enabled);
                gProfile.jsonObject.put(fieldServerNotificationDisabled,enabled);
                if(isAutoSave){
                    return save();
                }else{
                    return true;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean getNotificationDisabled(){
            String fName="[getNotificationDisabled]";
            try {
                boolean result=gProfile.jsonObject.getBoolean(fieldServerNotificationDisabled);
                logger.info(fName+"result="+result);
                return result;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public String getDefaultAccess(){
            String fName="[getDefaultAccess]";
            try {
                logger.info(fName + "do");
                return gProfile.jsonObject.getString(fieldDefaultAccessMode).replaceAll("2","");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean setDefaultAccess(String value){
            String fName="[setDefaultAccess]";
            try {
                logger.info(fName + "value="+value);
               if(!ACCESSLEVELS.Ask.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.AskPlus.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.Protected.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.Exposed.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.ExposedOld.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.Key.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.Pet.getName().equalsIgnoreCase(value)
                       &&!ACCESSLEVELS.Public.getName().equalsIgnoreCase(value)){
                   logger.warn(fName + "invalid value");
                   return false;
               }
                gProfile.jsonObject.put(fieldDefaultAccessMode,value);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

    }
    public lcJSONGuildProfile getRestrainsProfile(){
        String fName="[getRestrainsProfile]";
        try{
            return restraints.getProfile();
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public RESTRICTIONS restrictions;
    public static String gNameRestrictions ="rdBDSMCommands";
    public class RESTRICTIONS{
        public  RESTRICTIONS(){

        }
        public lcJSONGuildProfile getProfile(){
            String fName="[getProfile]";
            logger.info(fName+".safety check");
            try{
                if(gProfile ==null){
                    init();
                }
                return gProfile;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  null;
            }
        }
        public lcJSONGuildProfile gProfile;
        public void init(){
            String fName="[initRestrictionProfile]";
            logger.info(fName+".safety check");
            try {
                gProfile =gGlobal.getGuildSettings(gGuild, gNameRestrictions);
                if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gNameRestrictions,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(!gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if(gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if(gProfile.saveProfile()){
                        logger.info( fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        String keyGuilds="guilds",keyChannels="channels";
        public void iSafety(){
            String fName="iSafetyRestrictionsProfile";
            try {
                String field= fieldEnabled2;
                gProfile.safetyPutFieldEntry(field,false);
                field= fieldBannedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedUsers;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= keyGuilds;
                gProfile.safetyPutFieldEntry(field, new JSONObject());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
    public MUZZLE muzzle;
    static public String gMuzzleProfileName ="rdMuzzle";
    public class MUZZLE{
        public lcJSONGuildProfile gProfile;
        public void init(){
            String fName="[initMuzzleProfile]";
            logger.info(fName+".safety check");
            try{
                gProfile =gGlobal.getGuildSettings(gGuild, gMuzzleProfileName);
                if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gMuzzleProfileName,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(!gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if(gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if(gProfile.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public String fieldClearedOldWebhookTimeStamp="clearedOldWebhookTimeStamp";
        public void iSafety(){
            String fName="iSafetyCreate";
            try {
                String field= fieldMode;
                gProfile.safetyPutFieldEntry(field, valueWhite);
                field= fieldEnabled;
                gProfile.safetyPutFieldEntry(field,true);
                field= fieldAlert;
                gProfile.safetyPutFieldEntry(field, valueChannel);
                field= fieldAllowedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedUsers;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBDSMRestrictions;
                gProfile.safetyPutFieldEntry(field,0);
                field= fieldGagCommandAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());
                field= fieldGagTargetAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());
                field= fieldClearedOldWebhookTimeStamp;
                gProfile.safetyPutFieldEntry(field,0);
                gProfile.safetyPutFieldEntry(nCustomGagText,new JSONObject());
                gProfile.safetyPutFieldEntry(flagEnableCustom,false);
                gProfile.safetyPutFieldEntry(flagOnlyUseCustom,false);
                gProfile.safetyPutFieldEntry(flagOnlyGuildCustom,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public boolean save(){
            String fName="[saveMuzzleProfile]";
            logger.info(fName);
            try{
                gGlobal.putGuildSettings(gGuild, gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasPermission2UseGagCommand(Member member){
            String fName="[hasPermission2UseGagCommand]";
            logger.info(fName);
            try{
                if(gProfile==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has( fieldGagCommandAllowedRoles)||gProfile.jsonObject.isNull(fieldGagCommandAllowedRoles)){
                    logger.info(fName);
                    return true;
                }
                JSONArray array=gProfile.jsonObject.getJSONArray( fieldGagCommandAllowedRoles);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return true;
                }
                List<Role> memberRoles=member.getRoles();
                for(Role role:memberRoles){
                    for(int i=0;i<array.length();i++){
                        logger.info(fName+"item: "+role.getId()+" =?= "+array.getString(i));
                        if(role.getId().equalsIgnoreCase(array.getString(i))){
                            logger.info(fName+"equal");
                            return true;
                        }
                    }
                }
                return  false;
            }
            catch(Exception e){
                return true;
            }
        }
        public boolean hasPermission2TargetGagCommand(Member member){
            String fName="[hasPermission2TargetGagCommand]";
            logger.info(fName);
            try{
                if(gProfile==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has( fieldGagTargetAllowedRoles)||gProfile.jsonObject.isNull( fieldGagTargetAllowedRoles)){
                    logger.info(fName);
                    return true;
                }
                JSONArray array=gProfile.jsonObject.getJSONArray(  fieldGagTargetAllowedRoles);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return true;
                }
                List<Role> memberRoles=member.getRoles();
                for(Role role:memberRoles){
                    for(int i=0;i<array.length();i++){
                        logger.info(fName+"item: "+role.getId()+" =?= "+array.getString(i));
                        if(role.getId().equalsIgnoreCase(array.getString(i))){
                            logger.info(fName+"equal");
                            return true;
                        }
                    }
                }
                return  false;
            }
            catch(Exception e){
                return true;
            }
        }
    }

    public int getBDSMRestriction(){
        String fName="[getBDSMRestriction]";
        try{
            int result=0;
            String id=gGuild.getId();
            if(id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBdsm)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyChastity)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeySnuff)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyBlackGazza)
                    ||id.equalsIgnoreCase(lsCustomGuilds.lsGuildKeyLax)
            ){
                return 0;
            }
            lcJSONGuildProfile entrie=gGlobal.getGuildSettings(gGuild, gGlobal.keyServer);
            if(entrie!=null&&!entrie.jsonObject.isEmpty()){
                if(entrie.jsonObject.has(gGlobal.keyBDSMRestrictions)&&!entrie.jsonObject.isNull(gGlobal.keyBDSMRestrictions)){
                    result= entrie.jsonObject.getInt(gGlobal.keyBDSMRestrictions);
                }
            }
            return result;
        }
        catch(Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }



    public DIAPER diaper;
    public class DIAPER{
        public lcJSONGuildProfile gProfile;
        String gTableDiaperInteractive ="diaperInteractive";
        void iSafety(){
            String fName="iSafetyCreate";
            try {
                String field;
                field= fieldEnabled;
                gProfile.safetyPutFieldEntry(field,true);
                field= fieldAllowedChannels;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldBannedUsers;
                gProfile.safetyPutFieldEntry(field, new JSONArray());
                field= fieldCommandAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());
                field= fieldTargetAllowedRoles;
                gProfile.safetyPutFieldEntry(field,new JSONArray());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public void init(){
            String fName="[initDiaperInteractive]";
            logger.info(fName+".safety check");
            try{
                gProfile =gGlobal.getGuildSettings(gGuild, gTableDiaperInteractive);
                if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gTableDiaperInteractive,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(!gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if(gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if(gProfile.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public boolean save(){
            String fName="[saveDiaperInteractive]";
            logger.info(fName);
            try{
                gGlobal.putGuildSettings(gGuild, gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isMemberBanned2UseCommand(Member member){
            String fName="[isMemberBanned2UseDiaperCommand]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has( fieldBannedUsers)|| gProfile.jsonObject.isNull(fieldBannedUsers)){
                    logger.info(fName);
                    return false;
                }
                JSONArray array= gProfile.jsonObject.getJSONArray( fieldBannedUsers);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return false;
                }
                for(int i=0;i<array.length();i++){
                    logger.info(fName+"item: "+member.getId()+" =?= "+array.getString(i));
                    if(member.getId().equalsIgnoreCase(array.getString(i))){
                        logger.info(fName+"equal");
                        return true;
                    }
                }
                return  false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasPermission2UseCommand(Member member){
            String fName="[hasPermission2UseDiaperCommand]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has( fieldCommandAllowedRoles)|| gProfile.jsonObject.isNull(fieldCommandAllowedRoles)){
                    logger.info(fName);
                    return true;
                }
                JSONArray array= gProfile.jsonObject.getJSONArray( fieldCommandAllowedRoles);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return true;
                }
                List<Role> memberRoles=member.getRoles();
                for(Role role:memberRoles){
                    for(int i=0;i<array.length();i++){
                        logger.info(fName+"item: "+role.getId()+" =?= "+array.getString(i));
                        if(role.getId().equalsIgnoreCase(array.getString(i))){
                            logger.info(fName+"equal");
                            return true;
                        }
                    }
                }
                return  false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean hasPermission2TargetCommand(Member member){
            String fName="[hasPermission2TargetDiaperCommand]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has( fieldTargetAllowedRoles)|| gProfile.jsonObject.isNull( fieldTargetAllowedRoles)){
                    logger.info(fName);
                    return true;
                }
                JSONArray array= gProfile.jsonObject.getJSONArray(  fieldTargetAllowedRoles);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return true;
                }
                List<Role> memberRoles=member.getRoles();
                for(Role role:memberRoles){
                    for(int i=0;i<array.length();i++){
                        logger.info(fName+"item: "+role.getId()+" =?= "+array.getString(i));
                        if(role.getId().equalsIgnoreCase(array.getString(i))){
                            logger.info(fName+"equal");
                            return true;
                        }
                    }
                }
                return  false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isAllowedChannel4Command(TextChannel textchannel){
            String fName="[isAllowedChannel4DiaperCommand]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                if(!gProfile.jsonObject.has(fieldAllowedChannels)|| gProfile.jsonObject.isNull(fieldAllowedChannels)){
                    logger.info(fName);
                    return true;
                }
                JSONArray array= gProfile.jsonObject.getJSONArray(fieldAllowedChannels);
                if(array.isEmpty()){
                    logger.info(fName+"list is isEmpty");
                    return true;
                }
                for(int i=0;i<array.length();i++){
                    logger.info(fName+"item: "+textchannel.getId()+" =?= "+array.getString(i));
                    if(textchannel.getId().equalsIgnoreCase(array.getString(i))){
                        logger.info(fName+"equal");
                        return true;
                    }
                }
                return  false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
    }

    public COLLAR collar;
    static public String gProfileNameCollar ="rdCollar";
    public class COLLAR{
        public lcJSONGuildProfile gProfile;

        public String keyRoles="roles";
        public void iSafety(){
            String fName="iSafetyCreate";
            try {
                String field;
                field= fieldEnabled;
                gProfile.safetyPutFieldEntry(field,false);
                field= fieldGuild;
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(nBadWords,new JSONArray());
                jsonObject.put(nEnforcedWords,new JSONArray());
                jsonObject.put(fieldEnabled,false);
                gProfile.safetyPutFieldEntry(field,jsonObject);
                field= keyRoles;
                gProfile.safetyPutFieldEntry(field, new JSONObject());
                field= fieldLeashed;
                gProfile.safetyPutFieldEntry(field,new JSONObject());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public void init(){
            String fName="[initCollar]";
            logger.info(fName+".safety check");
            try{
                gProfile =gGlobal.getGuildSettings(gGuild, gProfileNameCollar);
                if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gProfileNameCollar,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(!gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if(gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if(gProfile.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public boolean save(){
            String fName="[saveCollar]";
            logger.info(fName);
            try{
                logger.info(fName + ".json="+ gProfile.jsonObject.toString());
                gGlobal.putGuildSettings(gGuild, gProfile);
                if(gProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String fieldLeashed="leashed",keyMemberWearer="memberWearer",keyMemberTarget="memberTarget",keyTextChannelTarget="textChannelTarget";
        public boolean addLeash(Member wearer,Member target,TextChannel targetLastPost){
            String fName="[addLeash]";
            logger.info(fName);
            try{
                return addLeash(wearer.getUser(), target.getUser(), targetLastPost);
            }
            catch(Exception e){
                return true;
            }
        }
        public boolean remLeash(Member wearer){
            String fName="[remLeash]";
            logger.info(fName);
            try{
                return  remLeash(wearer.getUser());
            }
            catch(Exception e){
                return true;
            }
        }
        public boolean addLeash(User wearer, User target, TextChannel targetLastPost){
            String fName="[addLeash]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                logger.info(fName+"wearer="+wearer.getName()+"("+wearer.getIdLong()+")");
                logger.info(fName+"target="+target.getName()+"("+target.getIdLong()+")");
                logger.info(fName+"TextChannel="+targetLastPost.getName()+"("+targetLastPost.getIdLong()+")");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(keyMemberWearer,wearer.getIdLong());
                jsonObject.put(keyMemberTarget,target.getIdLong());
                jsonObject.put(keyTextChannelTarget,targetLastPost.getIdLong());
                logger.info(fName+"jsonObject="+jsonObject.toString());
                gProfile.jsonObject.getJSONObject(fieldLeashed).put(wearer.getId(),jsonObject);
                save();
                return  true;
            }
            catch(Exception e){
                return true;
            }
        }
        public boolean remLeash(User wearer){
            String fName="[remLeash]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                logger.info(fName+"wearer="+wearer.getName()+"("+wearer.getIdLong()+")");
                gProfile.jsonObject.getJSONObject(fieldLeashed).remove(wearer.getId());
                save();
                return  true;
            }
            catch(Exception e){
                return true;
            }
        }
        public JSONObject getLeashByWearer(User wearer){
            String fName="[getLeashByWearer]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                logger.info(fName+"wearer="+wearer.getName()+"("+wearer.getIdLong()+")");
                if(gProfile.jsonObject.isNull(fieldLeashed)){
                    logger.info(fName+"isNull");
                    return new JSONObject();
                }
                logger.info(fName+"leashjsons="+ gProfile.jsonObject.getJSONObject(fieldLeashed).toString());
                if(!gProfile.jsonObject.getJSONObject(fieldLeashed).has(wearer.getId())){
                    logger.info(fName+"does not exists");
                    return new JSONObject();
                }
                return  gProfile.jsonObject.getJSONObject(fieldLeashed).getJSONObject(wearer.getId());
            }
            catch(Exception e){
                return new JSONObject();
            }
        }

        public List<JSONObject> getLeashesByTarget(User target){
            String fName="[getLeashesByTarget]";
            logger.info(fName);
            try{
                if(gProfile ==null||!gProfile.isExistent()){
                    init();
                }
                logger.info(fName+"target="+target.getName()+"("+target.getIdLong()+")");
                if(gProfile.jsonObject.isNull(fieldLeashed)){
                    logger.info(fName+"isNull");
                    return new ArrayList<>();
                }
                logger.info(fName+"leashjsons="+ gProfile.jsonObject.getJSONObject(fieldLeashed).toString());
                Set<String> setKeys= gProfile.jsonObject.getJSONObject(fieldLeashed).keySet();
                if(setKeys.isEmpty()){
                    logger.info(fName+"isEmpty");
                    return new ArrayList<>();
                }
                List<JSONObject>list=new ArrayList<>();
                Iterator<String>iteratorKeys=setKeys.iterator();
                while(iteratorKeys.hasNext()){
                    try {
                        String key=iteratorKeys.next();
                        JSONObject jsonObject= gProfile.jsonObject.getJSONObject(fieldLeashed).getJSONObject(key);
                        logger.info(fName+"json["+key+"]="+jsonObject.toString());
                        if(jsonObject.getString(keyMemberTarget).equalsIgnoreCase(target.getId())){
                            logger.info(fName+"found");
                            list.add(jsonObject);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+"return");
                return list;
            }
            catch(Exception e){
                return new ArrayList<>();
            }
        }
    }
    public TIMEOUT timeout;
    static public String gProfileNameTimeout="rdTimeout";
    public class TIMEOUT{
        public lcJSONGuildProfile gProfile;

        public String keyChannel="channel";
        public void iSafety(){
            String fName="iSafetyCreate";
            try {
                String field;
                field= keyChannel;
                gProfile.safetyPutFieldEntry(field,0);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        public void init(){
            String fName="[initCollar]";
            logger.info(fName+".safety check");
            try{
                gProfile =gGlobal.getGuildSettings(gGuild, gProfileNameTimeout);
                if( gProfile ==null||! gProfile.isExistent()||  gProfile.jsonObject.isEmpty()){
                    gProfile =new lcJSONGuildProfile(gGlobal, gProfileNameTimeout,gGuild,llv2_GuildsSettings);
                    gProfile.getProfile(llv2_GuildsSettings);
                    if(! gProfile.jsonObject.isEmpty()){
                        gGlobal.putGuildSettings(gGuild, gProfile);
                    }
                }
                iSafety();
                if( gProfile.isUpdated){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                    if( gProfile.saveProfile()){
                        logger.info(fName + ".success save to db");
                    }else{
                        logger.error(fName + ".error save to db");
                    }
                }
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public boolean save(){
            String fName="[saveCollar]";
            logger.info(fName);
            try{
                gGlobal.putGuildSettings(gGuild, gProfile);
                if( gProfile.saveProfile()){
                    logger.info(fName + ".success");return  true;
                }
                logger.warn(fName + ".failed");return false;
            }
            catch(Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
    }
    static public String gMuzzleChannelsName ="rdMuzzleChannels";


}
