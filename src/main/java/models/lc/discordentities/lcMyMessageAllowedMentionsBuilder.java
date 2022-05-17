package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.internal.utils.Helpers;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class lcMyMessageAllowedMentionsBuilder {
    //https://discord.com/developers/docs/resources/channel#allowed-mentions-object-allowed-mention-types
    Logger logger = Logger.getLogger(getClass());

    public lcMyMessageAllowedMentionsBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageAllowedMentionsBuilder(JSONObject jsonObject){
        String fName="build";
        try {
            setJson(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageAllowedMentionsBuilder clear() {
        String fName="[clear]";
        try {
            parse=new JSONArray();roles=new JSONArray();users=new JSONArray();
            replied_user=false;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    JSONArray parse=new JSONArray(),roles=new JSONArray(),users=new JSONArray();
    boolean replied_user=false;
    public static  final String keyParse="parse",keyRoles="roles",keyUsers="users",keyRepliedUser="replied_user";
    public lcMyMessageAllowedMentionsBuilder setJson(JSONObject jsonObject) {
        String fName="[setJson]";
        try {
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(jsonObject.has(keyParse))parse=jsonObject.getJSONArray(keyParse);
            if(jsonObject.has(keyRepliedUser))replied_user=jsonObject.getBoolean(keyRepliedUser);
            if(jsonObject.has(keyUsers))users=jsonObject.getJSONArray(keyUsers);
            if(jsonObject.has(keyRoles))roles=jsonObject.getJSONArray(keyRoles);
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(parse!=null&&!parse.isEmpty())jsonObject.put(keyParse,parse);
            if(replied_user==true)jsonObject.put(keyRepliedUser,true);
            if(users!=null&&!users.isEmpty())jsonObject.put(keyUsers,users);
            if(roles!=null&&!roles.isEmpty())jsonObject.put(keyRoles,roles);
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            return  jsonObject;
        }catch (Exception e){
            return new JSONObject();
        }
    }

    public lcMyMessageAllowedMentionsBuilder setRepliedUser(boolean replied_user) {
        String fName="[setRepliedUser]";
        try {
            logger.info(fName + ".value="+replied_user);
            this.replied_user= replied_user;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public boolean getRepliedUser() {
        String fName="[getRepliedUser]";
        try {
            logger.info(fName + ".value="+this.replied_user);
            return this.replied_user;
        }catch (Exception e){
            return false;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionTypes(@Nullable Collection<Message.MentionType> mentionTypes) {
        String fName="[setMentionTypes]";
        try {
            logger.info(fName + ".size="+mentionTypes.size());
            EnumSet<Message.MentionType> allowedMentions = null;
            allowedMentions = mentionTypes == null ? MessageAction.getDefaultMentions() : Helpers.copyEnumSet(Message.MentionType.class, mentionTypes);
            logger.info(fName + ".allowedMentions="+allowedMentions.size());
            for (Message.MentionType type : allowedMentions) {
                parse.put(type.getParseKey());
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionTypes(@Nullable List<Message.MentionType> mentionTypes) {
        String fName="[setMentionTypes]";
        try {
            logger.info(fName + ".size="+mentionTypes.size());
            for (Message.MentionType type : mentionTypes) {
                parse.put(type.getParseKey());
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionTypes(@Nullable JSONArray mentionTypes) {
        String fName="[setMentionTypes]";
        try {
            logger.info(fName + ".size="+mentionTypes.length());
            for (int i=0;i<mentionTypes.length();i++) {
                parse.put(mentionTypes.getString(i));
            }
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public List<Message.MentionType> getMentionTypes() {
        String fName="[getMentionTypes]";
        try {
            logger.info(fName + ".size="+parse.length());
            List<Message.MentionType>list=new ArrayList<>();
            for (int i=0;i<parse.length();i++) {
                String parseKey=parse.getString(i);
                if(parseKey.equalsIgnoreCase(Message.MentionType.CHANNEL.getParseKey())){
                    list.add(Message.MentionType.CHANNEL);
                }
                if(parseKey.equalsIgnoreCase(Message.MentionType.EMOTE.getParseKey())){
                    list.add(Message.MentionType.EMOTE);
                }
                if(parseKey.equalsIgnoreCase(Message.MentionType.EVERYONE.getParseKey())){
                    list.add(Message.MentionType.EVERYONE);
                }
                if(parseKey.equalsIgnoreCase(Message.MentionType.HERE.getParseKey())){
                    list.add(Message.MentionType.HERE);
                }
                if(parseKey.equalsIgnoreCase(Message.MentionType.ROLE.getParseKey())){
                    list.add(Message.MentionType.ROLE);
                }
                if(parseKey.equalsIgnoreCase(Message.MentionType.USER.getParseKey())){
                    list.add(Message.MentionType.USER);
                }
            }
            return list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<String> getMentionTypesAsString() {
        String fName="[getMentionTypesAsString]";
        try {
            logger.info(fName + ".size="+parse.length());
            List<String>list=new ArrayList<>();
            for (int i=0;i<parse.length();i++) {
                String parseKey=parse.getString(i);
                list.add(parseKey);
            }
            return list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public JSONArray getMentionTypesAsArray() {
        String fName="[getMentionTypesAsArray]";
        try {
            logger.info(fName + ".size="+parse.length());
            return parse;
        }catch (Exception e){
            return new JSONArray();
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionUsers(@Nonnull String... users) {
        String fName="[setMentionUsers]";
        try {
            logger.info(fName + ".size="+users.length);
            Checks.noneNull(users, "Users");
            for(int i=0;i< users.length;i++){
                this.users.put(users[i]);
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionRoles(@Nonnull String... roles) {
        String fName="[setMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length);
            Checks.noneNull(roles, "Roles");
            for(int i=0;i< roles.length;i++){
                this.roles.put(roles[i]);
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionUsers(@Nonnull long... users) {
        String fName="[setMentionUsers]";
        try {
            logger.info(fName + ".size="+users.length);
            Checks.notNull(users, "Users");
            for(int i=0;i< users.length;i++){
                this.users.put(String.valueOf(users[i]));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionRoles(@Nonnull long... roles) {
        String fName="[setMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length);
            Checks.notNull(roles, "Roles");
            for(int i=0;i< roles.length;i++){
                this.roles.put(String.valueOf(roles[i]));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionUsers(JSONArray users) {
        String fName="[setMentionUsers]";
        try {
            logger.info(fName + ".size="+users.length());
            Checks.notNull(users, "Users");
            for(int i=0;i< users.length();i++){
                this.users.put(users.getString(i));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionRoles(JSONArray roles) {
        String fName="[setMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length());
            Checks.notNull(roles, "Roles");
            for(int i=0;i< roles.length();i++){
                this.roles.put(roles.getString(i));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionStringUsers(List<String> users) {
        String fName="[setMentionUsers]";
        try {
            logger.info(fName + ".size="+users.size());
            Checks.notNull(users, "Users");
            for(int i=0;i< users.size();i++){
                this.users.put(users.get(i));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionStringRoles(List<String> roles) {
        String fName="[setMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.size());
            Checks.notNull(roles, "Roles");
            for(int i=0;i< roles.size();i++){
                this.roles.put(roles.get(i));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionLongUsers(List<Long> users) {
        String fName="[setMentionUsers]";
        try {
            logger.info(fName + ".size="+users.size());
            Checks.notNull(users, "Users");
            for(int i=0;i< users.size();i++){
                this.users.put(String.valueOf(users.get(i)));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public lcMyMessageAllowedMentionsBuilder setMentionLongRoles(List<Long> roles) {
        String fName="[setMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.size());
            Checks.notNull(roles, "Roles");
            for(int i=0;i< roles.size();i++){
                this.roles.put(String.valueOf(roles.get(i)));
            }
            return  this;
        }catch (Exception e){
            return null;
        }
    }

    public List<String> getMentionUsers() {
        String fName="[getMentionUsers]";
        try {
            logger.info(fName + ".size="+users.length());
            List<String>list=new ArrayList<>();
            for(int i=0;i< users.length();i++){
                list.add(users.getString(i));
            }
            return  list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<String> getMentionRoles() {
        String fName="[getMentionRoles]";
        try {
            logger.info(fName + ".size="+roles.length());
            List<String>list=new ArrayList<>();
            for(int i=0;i< roles.length();i++){
               list.add(roles.getString(i));
            }
            return  list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<Long> getMentionUsersAsLong() {
        String fName="[getMentionUsersAsLong]";
        try {
            logger.info(fName + ".size="+users.length());
            List<Long>list=new ArrayList<>();
            for(int i=0;i< users.length();i++){
                list.add(Long.parseLong(users.getString(i)));
            }
            return  list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<Long> getMentionRolesAsLong() {
        String fName="[getMentionRolesAsLong]";
        try {
            logger.info(fName + ".size="+roles.length());
            List<Long>list=new ArrayList<>();
            for(int i=0;i< roles.length();i++){
                list.add(Long.parseLong(roles.getString(i)));
            }
            return  list;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public JSONArray getMentionUsersAsArray() {
        String fName="[getMentionUsersAsArray]";
        try {
            logger.info(fName + ".size="+users.length());
            return  users;
        }catch (Exception e){
            return new JSONArray();
        }
    }
    public JSONArray getMentionRolesAsArray() {
        String fName="[getMentionRolesAsArray]";
        try {
            logger.info(fName + ".size="+roles.length());
            return  roles;
        }catch (Exception e){
            return new JSONArray();
        }
    }
    public int getMentionUsersSize() {
        String fName="[getMentionUsersSize]";
        try {
            logger.info(fName + ".size="+users.length());
            return  users.length();
        }catch (Exception e){
            return 0;
        }
    }
    public int getMentionRolesSize() {
        String fName="[getMentionRolesSize]";
        try {
            logger.info(fName + ".size="+roles.length());
            return  roles.length();
        }catch (Exception e){
            return 0;
        }
    }
    public boolean isMentionUsersEmpty() {
        String fName="[isMentionUsersEmpty]";
        try {
            logger.info(fName + ".isEmpty()="+users.isEmpty());
            return  users.isEmpty();
        }catch (Exception e){
            return true;
        }
    }
    public boolean isMentionRolesEmpty() {
        String fName="[isMentionRolesEmpty]";
        try {
            logger.info(fName + ".isEmpty()="+roles.isEmpty());
            return  roles.isEmpty();
        }catch (Exception e){
            return true;
        }
    }

}
