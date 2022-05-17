package models.lc;

import kong.unirest.json.JSONArray;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llv2_GuildsSettings;

public class lcBasicFeatureControl {
    Logger logger = Logger.getLogger(getClass());
    String gProfileName="";
    Guild gGuild;
    lcGlobalHelper gGlobal;
    public lcBasicFeatureControl(){
        String fName="[constructor]";
        try {
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcBasicFeatureControl(Guild guild,String profileName){
        String fName="[constructor]";
        try {
            gGuild=guild;
            gProfileName=profileName;
            logger.info(fName + ".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName + ".profileName="+profileName);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcBasicFeatureControl(Guild guild,String profileName,lcGlobalHelper global){
        String fName="[constructor]";
        try {
            gGuild=guild;
            gProfileName=profileName;
            logger.info(fName + ".guild="+guild.getId()+"|"+guild.getName());
            logger.info(fName + ".profileName="+profileName);
            gGlobal=global;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcJSONGuildProfile gProfile;
    String gDb=llv2_GuildsSettings;
    public void initProfile(){
        String fName="[initProfile]";
        logger.info(fName+".safety check");
        try{
            if(gGlobal==null)throw  new Exception("global is null");
            if(gGuild==null)throw  new Exception("guild is null");
            if(gProfileName==null)throw  new Exception("profileName is null");
            gProfile =gGlobal.getGuildSettings(gGuild, gProfileName);
            if(gProfile ==null||!gProfile.isExistent()|| gProfile.jsonObject.isEmpty()){
                gProfile =new lcJSONGuildProfile(gGlobal, gProfileName,gGuild,gDb);
                gProfile.getProfile(llv2_GuildsSettings);
                if(!gProfile.jsonObject.isEmpty()){
                    gGlobal.putGuildSettings(gGuild, gProfile);
                }
            }
            safetyProfile();
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
    public static String keyEnabled="enabled",keyAllowedChannels="allowedchannels",keyDeniedChannels="deniedChannels",keyAllowedRoles="allowedRoles",keyDeniedRoles="deniedRoles",keyAllowedUsers="allowedUsers",keyDeniedUsers="deniedUsers";
    public boolean isAutoSave=true;
    public void safetyProfile(){
        String fName="[safetyProfile]";
        try {
            gProfile.safetyPutFieldEntry(keyEnabled, true);
            gProfile.safetyPutFieldEntry(keyAllowedChannels,new JSONArray());
            gProfile.safetyPutFieldEntry(keyDeniedChannels,new JSONArray());
            gProfile.safetyPutFieldEntry(keyAllowedRoles,new JSONArray());
            gProfile.safetyPutFieldEntry(keyDeniedRoles,new JSONArray());
            gProfile.safetyPutFieldEntry(keyAllowedUsers,new JSONArray());
            gProfile.safetyPutFieldEntry(keyDeniedUsers,new JSONArray());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean saveProfile(){
        String fName="[saveProfile]";
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

    public boolean setEnable(boolean enabled){
        String fName="[setEnable]";
        try {
            gProfile.putFieldEntry(keyEnabled, enabled);
            if(isAutoSave){
                return saveProfile();
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
            return gProfile.jsonObject.getBoolean(keyEnabled);
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
                return saveProfile();
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
            logger.info(fName + ".jsonArray=" + jsonArray.toString());
            gProfile.putFieldEntry(name, jsonArray);
            if(isAutoSave){
                return saveProfile();
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
                return saveProfile();
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
                return saveProfile();
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
                        return saveProfile();
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
                        return saveProfile();
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
            String key=keyAllowedChannels;
            return  clearBasicArray(key);
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
            String key=keyDeniedChannels;
            return  clearBasicArray(key);
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
            String key=keyAllowedRoles;
            return  clearBasicArray(key);
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
            String key=keyDeniedRoles;
            return  clearBasicArray(key);
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
            String key=keyAllowedUsers;
            return  clearBasicArray(key);
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
            String key=keyDeniedUsers;
            return  clearBasicArray(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public List<TextChannel> getAllowedChannels(){
        String fName="[getAllowedChannels]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedChannels;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<TextChannel>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getTextChannelById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<TextChannel> getDeniedChannels(){
        String fName="[getDeniedChannels]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedChannels;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<TextChannel>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getTextChannelById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Role> getAllowedRoles(){
        String fName="[getAllowedRoles]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedRoles;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Role>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getRoleById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Role> getDeniedRoles(){
        String fName="[getDeniedRoles]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedRoles;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Role>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getRoleById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<User> getAllowedUsers(){
        String fName="[getAllowedUsers]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedUsers;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<User>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getJDA().getUserById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<User> getDeniedUsers(){
        String fName="[getDeniedUsers]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedUsers;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<User>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getJDA().getUserById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Member> getAllowedMembers(){
        String fName="[getAllowedMembers]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedUsers;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Member>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getMemberById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Member> getDeniedMembers(){
        String fName="[getDeniedMembers]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedUsers;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Member>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getMemberById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }

    public List<Long> getAllowedChannelsAsLong(){
        String fName="[getAllowedChannelsAsLong]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedChannels;
            return getBasicArrayAsListLong(key);
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
            String key=keyDeniedChannels;
            return getBasicArrayAsListLong(key);
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
            String key=keyAllowedRoles;
            return getBasicArrayAsListLong(key);
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
            String key=keyDeniedRoles;
            return getBasicArrayAsListLong(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Long> getAllowedUserAsLongs(){
        String fName="[getAllowedUsersAsLong]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedUsers;
            return getBasicArrayAsListLong(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Long> getDeniedUsersAsLong(){
        String fName="[getDeniedUsersAsLong]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedUsers;
            return getBasicArrayAsListLong(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Long> getAllowedMembersAsLong(){
        String fName="[getAllowedMembersAsLong]";
        try {
            logger.info(fName + "do");
            String key=keyAllowedUsers;
            return getBasicArrayAsListLong(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Long> getDeniedMembersAsLong(){
        String fName="[getDeniedMembersAsLong]";
        try {
            logger.info(fName + "do");
            String key=keyDeniedUsers;
            return getBasicArrayAsListLong(key);
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
            String key=keyAllowedChannels;
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
            String key=keyDeniedChannels;
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
            String key=keyAllowedRoles;
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
            String key=keyDeniedRoles;
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
            String key=keyAllowedUsers;
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
            String key=keyDeniedUsers;
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

    public boolean remAllowedChannel(TextChannel textChannel){
        String fName="[remAllowedChannel]";
        try {
            logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
            String key=keyAllowedChannels;
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
            String key=keyDeniedChannels;
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
            String key=keyAllowedRoles;
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
            String key=keyDeniedRoles;
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
            String key=keyAllowedUsers;
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
            String key=keyDeniedUsers;
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

    public boolean isAllowedChannel(TextChannel textChannel){
        String fName="[isAllowedChannel]";
        try {
            logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
            String key=keyAllowedChannels;
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
            String key=keyDeniedChannels;
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
            String key=keyAllowedRoles;
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
            String key=keyDeniedRoles;
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
            String key=keyAllowedUsers;
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
            String key=keyDeniedUsers;
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
                for(int i=0;i<roles.size();i++){
                    try {
                        Role role=roles.get(i);
                        if(!isRoleAllowed&&isAllowedRole(role)){
                            isRoleAllowed=true;
                            positionAllowedRole=role.getPosition();
                        }
                        if(!isRoleDenied&&isDeniedRole(role)){
                            isRoleDenied=true;
                            positionDeniedRole=role.getPosition();
                        }
                    }catch (Exception e){
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
                for(int i=0;i<roles.size();i++){
                    try {
                        Role role=roles.get(i);
                        if(!isRoleAllowed&&isAllowedRole(role)){
                            isRoleAllowed=true;
                            positionAllowedRole=role.getPosition();
                        }
                        if(!isRoleDenied&&isDeniedRole(role)){
                            isRoleDenied=true;
                            positionDeniedRole=role.getPosition();
                        }
                    }catch (Exception e){
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

    public boolean clearEntities(String name){
        String fName="[clearEntitys]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            return  clearBasicArray(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<Long> getEntitiesAsLong(String name){
        String fName="[getEntitysAsLong]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            return getBasicArrayAsListLong(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public boolean addEntity(String name,TextChannel textChannel){
        String fName="[addEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
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
    public boolean remEntity(String name,TextChannel textChannel){
        String fName="[remEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".textChannel.id=" + textChannel.getIdLong());
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
    public boolean addEntity(String name,Role role){
        String fName="[addEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".role.id=" + role.getIdLong());
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
    public boolean remEntity(String name,Role role){
        String fName="[remEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".role.id=" + role.getIdLong());
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
    public boolean addEntity(String name,User user){
        String fName="[addEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".user.id=" + user.getIdLong());
            if(hasBasicArray(key,user.getIdLong())){
                logger.info(fName + ".already has");
                return true;
            }
            return  addBasicArray(key,user.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remEntity(String name,User user){
        String fName="[remEntity]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            logger.info(fName + ".user.id=" + user.getIdLong());
            if(!hasBasicArray(key,user.getIdLong())){
                logger.info(fName + ".does not has");
                return true;
            }
            return  remBasicArray(key,user.getIdLong());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<TextChannel> getEntitiesAsChannel(String name){
        String fName="[getEntitiesAsChannel]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<TextChannel>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getTextChannelById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Role> getEntitiesAsRole(String name){
        String fName="[getEntitiesAsRole]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Role>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add(gGuild.getRoleById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<User> getEntitiesAsUser(String name){
        String fName="[getEntitiesAsUser]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<User>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getJDA().getUserById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<Member> getEntitiesAsMember(String name){
        String fName="[getEntitiesAsMember]";
        try {
            logger.info(fName + "name="+name);
            String key=name;
            List<Long>ids=getBasicArrayAsListLong(key);
            logger.info(fName + "ids.size="+ids.size());
            List<Member>list=new ArrayList<>();
            for(long id:ids){
                try {
                    logger.info(fName + "id="+id);
                    list.add( gGuild.getMemberById(id));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName + "list.size="+list.size());
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }

}
