package models.lc.interaction.applicationcommand;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llCommonVariables;
import models.llGlobalHelper;
import models.ls.lsDiscordApi;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcApplicationsCCommonManager {
    Logger logger = Logger.getLogger(getClass());
    public lcApplicationsCCommonManager(){

    }
    public lcApplicationsCCommonManager(lcGlobalHelper global){
        this.global=global;
    }
    public lcGlobalHelper global;
    private Guild guild=null;
    public boolean isGuild() {
        String fName = "[isGuild]";
        try {
            if(guild!=null){
                logger.info(fName + ".return 1 true");
                return  true;
            }
            logger.info(fName + ".return 3 false");
            return  false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            logger.info(fName + ".guild=" + guild.getId());
            return guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcApplicationsCCommonManager setGuild(Guild guild) {
        String fName = "[setGuild]";
        try {
            if(guild==null)throw  new Exception("Cant be null!");
            logger.info(fName + ".input=" + guild.getId());
            this.guild=guild;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcApplicationsCCommonManager clearGuild() {
        String fName = "[clearGuild]";
        try {
            this.guild=null;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcApplicationsCCommonManager clearList() {
        String fName = "[clearList]";
        try {
            if(this.commands==null)this.commands=new ArrayList<>();
            else this.commands.clear();
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private HttpResponse<JsonNode> postResponse=new FailedResponse<>(new Exception("{'error':'Nothing provided'"));
    private boolean getRequestGuildCommands(){
        String fName="[getRequestGuildCommands]";
        try {
            Guild guild=getGuild();
            logger.info(fName+".guild="+  guild.getName()+"("+guild.getId()+")");
            String url = lsDiscordApi.ApplicationCommand.Guild.lsGetCommands_Or_RegisteringCommand(global.configfile.getBot().getId(),guild.getId());
            return getRequestCommands(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getRequestGlobalCommands(){
        String fName="[getRequestGlobalCommands]";
        try {
            String url = lsDiscordApi.ApplicationCommand.Global.lsGetCommands_Or_RegisteringCommand(global.configfile.getBot().getId());
            return getRequestCommands(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getRequestCommands(String url){
        String fName="[getRequestCommands:Url]";
        try {
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.get(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+postResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=postResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(postResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got successfully");
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getRequestGuildCommand(String id){
        String fName="[getRequestGuildCommand]";
        try {
            Guild guild=getGuild();
            logger.info(fName+".guild="+  guild.getName()+"("+guild.getId()+")");
            String url = lsDiscordApi.ApplicationCommand.Guild.lsGetOrEditCommand(global.configfile.getBot().getId(),guild.getId(),id);
            return getRequestCommand(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getRequestGlobalCommand(String id){
        String fName="[getRequestGlobalCommand]";
        try {
            String url = lsDiscordApi.ApplicationCommand.Global.lsGetOrEditCommand(global.configfile.getBot().getId(),id);
            return getRequestCommand(url);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getRequestCommand(String url){
        String fName="[getRequestCommand:Url]";
        try {
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            postResponse =a.get(url)
                    .header("Authorization", "Bot "+llGlobalHelper.llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+postResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=postResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(postResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got successfully");
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private List<lcApplicationCCommonEditor>commands=new ArrayList<>();
    public lcApplicationsCCommonManager request(Guild guild){
        String fName="[request:guild]";
        try {
            clearGuild();
            if(setGuild(guild)==null){
                throw  new Exception("No guild provided!");
            }
            if(!getRequestGuildCommands()){
                throw  new Exception("Failed to request get!");
            }
            JSONArray jsonArray=postResponse.getBody().getArray();
            if(jsonArray==null){
                throw  new Exception("jsonArray==null");
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+".jsonArray.isEmpty");
                return this;
            }
            logger.info(fName+".jsonArray.size="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                commands.add(new lcApplicationCCommonEditor(jsonArray.getJSONObject(i),guild));
            }
            if(commands.isEmpty()){
                throw  new Exception("No commands!");
            }
            logger.info(fName+".done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationsCCommonManager request(){
        String fName="[request:global]";
        try {
            clearGuild();
            if(!getRequestGlobalCommands()){
                throw  new Exception("Failed to request get!");
            }
            JSONArray jsonArray=postResponse.getBody().getArray();
            if(jsonArray==null){
                throw  new Exception("jsonArray==null");
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+".jsonArray.isEmpty");
                return this;
            }
            logger.info(fName+".jsonArray.size="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                commands.add(new lcApplicationCCommonEditor(jsonArray.getJSONObject(i)));
            }
            if(commands.isEmpty()){
                throw  new Exception("No commands!");
            }
            logger.info(fName+".done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor requestById(Guild guild,long id){
        String fName="[requestById:guild]";
        try {
            clearGuild();
            if(setGuild(guild)==null){
                throw  new Exception("No guild provided!");
            }
            if(!getRequestGuildCommand(String.valueOf(id))){
                throw  new Exception("Failed to request get!");
            }
            JSONArray jsonArray=postResponse.getBody().getArray();
            if(jsonArray==null){
                throw  new Exception("jsonArray==null");
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+".jsonArray.isEmpty");
                return null;
            }
            logger.info(fName+".jsonArray.size="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                commands.add(new lcApplicationCCommonEditor(jsonArray.getJSONObject(i),guild));
            }
            if(commands.isEmpty()){
                throw  new Exception("No commands!");
            }
            if(commands.size()>1){
                throw  new Exception("Too many for id request!");
            }
            logger.info(fName+".done");
            return commands.get(0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor requestById(long id){
        String fName="[requestById:global]";
        try {
            clearGuild();
            if(!getRequestGlobalCommand(String.valueOf(id))){
                throw  new Exception("Failed to request get!");
            }
            JSONArray jsonArray=postResponse.getBody().getArray();
            if(jsonArray==null){
                throw  new Exception("jsonArray==null");
            }
            if(jsonArray.isEmpty()){
                logger.info(fName+".jsonArray.isEmpty");
                return null;
            }
            logger.info(fName+".jsonArray.size="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                commands.add(new lcApplicationCCommonEditor(jsonArray.getJSONObject(i)));
            }
            if(commands.isEmpty()){
                throw  new Exception("No commands!");
            }
            if(commands.size()>1){
                throw  new Exception("Too many for id request!");
            }
            logger.info(fName+".done");
            return commands.get(0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            boolean result=commands.isEmpty();
            logger.info(fName + ".result=" + result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int size() {
        String fName = "[size]";
        try {
            int result=commands.size();
            logger.info(fName + ".result=" + result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<lcApplicationCCommonEditor> get() {
        String fName = "[get]";
        try {
            logger.info(fName + ".commands=" + commands.size());
            return commands;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor getAt(int index) {
        String fName = "[getAt]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", index="+index);
            lcApplicationCCommonEditor command=commands.get(index);
            logger.info(fName + ".command=" + command.getId());
            return command;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor getById(String id) {
        String fName = "[getById]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", id="+id);
            for (lcApplicationCCommonEditor command : commands) {
                String id1 = command.getId();
                logger.info(fName + ".command: " + command.getName() + " (" + id1 + ")");
                if (id1.equals(id)) {
                    logger.info(fName + "found");
                    return command;
                }
            }
            logger.warn(fName + "not found command with id "+id);
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor getById(long id) {
        String fName = "[getById]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", id="+id);
            for (lcApplicationCCommonEditor command : commands) {
                long id1 = command.getIdAsLong();
                logger.info(fName + ".command: " + command.getName() + " (" + id1 + ")");
                if (id1 == id) {
                    logger.info(fName + "found");
                    return command;
                }
            }
            logger.warn(fName + "not found command with id "+id);
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor getByName(String name) {
        String fName = "[getByName]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", name="+name);
            for (lcApplicationCCommonEditor command : commands) {
                String name1 = command.getName();
                logger.info(fName + ".command: " + name1 + " (" + command.getId() + ")");
                if (name1.equals(name)) {
                    logger.info(fName + "found");
                    return command;
                }
            }
            logger.warn(fName + "not found command with name "+name);
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationCCommonEditor getByNameIgnoreCase(String name) {
        String fName = "[getByNameIgnoreCase]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", name="+name);
            for (lcApplicationCCommonEditor command : commands) {
                String name1 = command.getName();
                logger.info(fName + ".command: " + name1 + " (" + command.getId() + ")");
                if (name1.equalsIgnoreCase(name)) {
                    logger.info(fName + "found");
                    return command;
                }
            }
            logger.warn(fName + "not found command with name "+name);
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasAt(int index) {
        String fName = "[hasAt]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", index="+index);
            lcApplicationCCommonEditor command=getAt(index);
            boolean value=command!=null;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasById(String id) {
        String fName = "[hasById]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", id="+id);
            lcApplicationCCommonEditor command=getById(id);
            boolean value=command!=null;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasById(long id) {
        String fName = "[hasById]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", id="+id);
            lcApplicationCCommonEditor command=getById(id);
            boolean value=command!=null;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasByName(String name) {
        String fName = "[hasByName]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", name="+name);
            lcApplicationCCommonEditor command=getByName(name);
            boolean value=command!=null;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasByNameIgnoreCase(String name) {
        String fName = "[hasByNameIgnoreCase]";
        try {
            logger.info(fName + ".commands=" + commands.size()+", name="+name);
            lcApplicationCCommonEditor command=getByNameIgnoreCase(name);
            boolean value=command!=null;
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
